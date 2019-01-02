package com.deehow.service;

import com.deehow.model.SysModel;
import com.deehow.model.SysUnit;
import com.deehow.model.ModuleOrder;
import com.deehow.model.ModuleOrderSon;
import com.deehow.model.Preferential;
import com.deehow.core.Constants;
import com.deehow.core.base.BaseService;
import com.deehow.core.util.CacheUtil;
import com.deehow.core.util.DataUtil;
import com.deehow.mapper.SysModelMapper;
import com.deehow.mapper.SysUnitMapper;
import com.deehow.mapper.ModuleOrderMapper;
import com.deehow.mapper.ModuleOrderSonMapper;
import com.deehow.mapper.PreferentialMapper;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 模块订单表--- 服务实现类
 * </p>
 *
 * @author renzh
 * @since 2018-07-18
 */
@Service
public class ModuleOrderService extends BaseService<ModuleOrder> {
	@Autowired
	private ModuleOrderSonMapper moduleOrderSonMapper;
	@Autowired
	private PreferentialMapper preferentialMapper;
	@Autowired
	private SysModelMapper sysModelMapper;
	@Autowired
	private SysUnitMapper sysUnitMapper;
	@Autowired
	private SysModelService sysModelService;

	/**
	 * 在订单公海的认领功能，只修改操作人
	 */
	public ModuleOrder updateOperatingPersonnel(ModuleOrder sysModuleOrder) {
		ModuleOrder nowsysModuleOrder = this.queryById(sysModuleOrder.getId());
		//认领前先判断当前登录人是否是此企业的专员
		ModuleOrder o=getOperatingPersonnel(nowsysModuleOrder.getUnitId());
		if(o!=null&&!o.getOperatingPersonnel().equals(sysModuleOrder.getOperatingPersonnel())){
			return null;
		}
		String lockKey = getLockKey("U" + sysModuleOrder.getId());
		
		if (CacheUtil.getLock(lockKey)) {
			try {
				nowsysModuleOrder.setOperatingPersonnel(sysModuleOrder.getOperatingPersonnel());
				nowsysModuleOrder.setUpdateTime(new Date());
				nowsysModuleOrder.setUpdateBy(sysModuleOrder.getUpdateBy());
				mapper.updateById(nowsysModuleOrder);
			} finally {
				CacheUtil.unlock(lockKey);
			}
		} else {
			throw new RuntimeException("数据不一致!请刷新页面重新编辑!");
		}
		try {
			CacheUtil.getCache().set(getCacheKey(nowsysModuleOrder.getId()), nowsysModuleOrder);
		} catch (Exception e) {
			logger.error(Constants.Exception_Head, e);
		}
		return nowsysModuleOrder;

	}

	/**
	 * 删除订单及其子表信息
	 */
	public void deleteOrderAndSon(Map<String, Object> param) {
		// 根据Id删除订单模块子表信息并删除缓存
		@SuppressWarnings("unchecked")
		List<String> ids = (List<String>) param.get("ids");
		for (int i = 0; i < ids.size(); i++) {
			String id = ids.get(i);
			Map<String, Object> columnMap = new HashMap<String, Object>();
			columnMap.put("order_id", Long.valueOf(id));
			List<ModuleOrderSon> smoList = moduleOrderSonMapper.selectByMap(columnMap);
			for (int j = 0; j < smoList.size(); j++) {// 删除缓存中的订单子表信息
				ModuleOrderSon mos=smoList.get(j);
				Long sId = smoList.get(j).getId();
				mos.setEnable(0);
				moduleOrderSonMapper.updateById(mos);
				CacheUtil.getCache().del(getCacheKey(sId));
			}
			// 删除主表信息并删除缓存
			ModuleOrder mo=this.queryById(Long.valueOf(id));
			mo.setEnable(0);
			mo.setUpdateTime(new Date());
			mo.setUpdateBy((Long)param.get("user"));
			this.update(mo);
			CacheUtil.getCache().del(getCacheKey(id));
		}

	}

	/**
	 * 根据优惠方案添加订单添加订单
	 */
	public void insertOrder(ModuleOrder sysModuleOrder) {
		Long preferentialId = sysModuleOrder.getPreferentialId();
		Preferential sp = preferentialMapper.selectById(preferentialId);
		// 添加主表信息
		// 企业信息
		SysUnit sysUnit = sysUnitMapper.selectById(sysModuleOrder.getUnitId());
		sysModuleOrder.setUnitName(sysUnit.getUnitName());
		sysModuleOrder.setUnitCode(sysUnit.getUnitCode());
		
		sysModuleOrder.setOrderNo(getOrderNo());//订单编码
		mapper.insert(sysModuleOrder);
		// 根据优惠信息id查询模块信息
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", sp.getModelId());
		List<SysModel> smList = sysModelMapper.selectModelByIds(params);
		// 保存模块信息到订单子表
		for (int i = 0; i < smList.size(); i++) {
			SysModel model = smList.get(i);
			ModuleOrderSon sysModuleOrderSon = new ModuleOrderSon();
			sysModuleOrderSon.setCreateBy(sysModuleOrder.getCreateBy());
			sysModuleOrderSon.setCreateTime(new Date());
			sysModuleOrderSon.setModelId(model.getId());
			sysModuleOrderSon.setModelName(model.getModelName());
			sysModuleOrderSon.setOrderId(sysModuleOrder.getId());
			sysModuleOrderSon.setUpdateTime(new Date());
			sysModuleOrderSon.setUpdateBy(sysModuleOrder.getCreateBy());
			// 到期时间
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);// 设置起时间
			cal.add(Calendar.YEAR, Integer.valueOf(sysModuleOrder.getValidityTime()));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			sysModuleOrderSon.setExpiryTime(format.format(cal.getTime()));
			moduleOrderSonMapper.insert(sysModuleOrderSon);
		}
	}

	/**
	 * 查询订单详情 包括模块信息
	 */
	public ModuleOrder getOrderDetail(ModuleOrder param) {
		ModuleOrder sysModuleOrder = this.queryById(param.getId());
		Map<String, Object> columnMap = new HashMap<String, Object>();
		if (sysModuleOrder == null) {
			return new ModuleOrder();
		}
		columnMap.put("order_id", sysModuleOrder.getId());
		List<ModuleOrderSon> sosList = moduleOrderSonMapper.selectByMap(columnMap);
		sysModuleOrder.setOrderSonList(sosList);
		return sysModuleOrder;
	}
	/**
	 * 根据企业id获取某个企业的专员
	 */
	public ModuleOrder getOperatingPersonnel(Long unitId){
		Map<String,Object> columnMap=new HashMap<String,Object>();
		columnMap.put("unitId", unitId);
		columnMap.put("oPersonnel", "0");//operating_personnel 不等于0
		List<ModuleOrder> mList=(List<ModuleOrder>) this.queryList(columnMap);
		if(mList!=null&&mList.size()>0){
			return mList.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据单个模块信息添加订单
	 */
	public void insertOrderModel(ModuleOrder sysModuleOrder) {
		// 添加主表信息
		// 企业信息
		SysUnit sysUnit = sysUnitMapper.selectById(sysModuleOrder.getUnitId());
		sysModuleOrder.setUnitName(sysUnit.getUnitName());
		sysModuleOrder.setUnitCode(sysUnit.getUnitCode());
		sysModuleOrder.setOrderNo(getOrderNo());//订单编码
		mapper.insert(sysModuleOrder);
		//根据modelId查询模块信息
		List<Long> modelIds = sysModuleOrder.getModelIdList();
		// 保存模块信息到订单子表
		for (int i = 0; i < modelIds.size(); i++) {
			Long modelId=modelIds.get(i);
			SysModel model=sysModelService.queryById(modelId);
			ModuleOrderSon sysModuleOrderSon = new ModuleOrderSon();
			sysModuleOrderSon.setCreateBy(sysModuleOrder.getCreateBy());
			sysModuleOrderSon.setCreateTime(new Date());
			sysModuleOrderSon.setModelId(model.getId());
			sysModuleOrderSon.setModelName(model.getModelName());
			sysModuleOrderSon.setOrderId(sysModuleOrder.getId());
			sysModuleOrderSon.setUpdateTime(new Date());
			sysModuleOrderSon.setUpdateBy(sysModuleOrder.getCreateBy());
			// 到期时间
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);// 设置起时间
			cal.add(Calendar.YEAR, Integer.valueOf(sysModuleOrder.getValidityTime()));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sysModuleOrderSon.setExpiryTime(format.format(cal.getTime()));
			moduleOrderSonMapper.insert(sysModuleOrderSon);
		}
	}
	
	/** 订单编码 */
	public String getOrderNo(){
		// 3位随机数
		Random r = new Random();
		int xx = r.nextInt(999);
		while (xx < 100) {
			xx = r.nextInt(999);
		}
		// 精确到毫秒的时间
		Date timestamp = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmssSSS");
		String dateString = sdf.format(timestamp);
		return dateString+xx;
	}
}
