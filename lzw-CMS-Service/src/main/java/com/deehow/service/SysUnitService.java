package com.deehow.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.deehow.core.Constants;
import com.deehow.core.base.BaseService;
import com.deehow.core.util.CacheUtil;
import com.deehow.core.util.InstanceUtil;
import com.deehow.mapper.SysUnitMapper;
import com.deehow.model.SysUnit;
import com.deehow.model.SysUnitDTO;
import com.deehow.model.UnitRegisterApply;

/**
 * @author ShenHuaJie
 *
 */
@Service
public class SysUnitService extends BaseService<SysUnit> {
	@Autowired
	private UnitRegisterApplyService unitRegisterApplyService;
	@Autowired
	private SysUnitMapper sysUnitMapper;
	/**
	 * 租户表信息分页查询
	 * 
	 * @param param
	 * @return
	 */
	public Page<SysUnit> queryUnitByPage(Map<String, Object> params) {
		Page<Long> page = getPage(params);
		page.setRecords(mapper.selectIdPage(page, params));
		return getPageByIds(page, params);
	}

	/**
	 * 租户表信息查询
	 * 
	 * @param param
	 * @return
	 */
	public List<SysUnit> queryAllList(Map<String, Object> params) {
		List<Long> ids = mapper.selectIdPage(params);
		List<SysUnit> list = getListByIds(ids, params);
		return list;
	}
	/**
	 * 企业专员对应的租户查询
	 * 
	 * @param param
	 * @return
	 */
	public List<SysUnit> queryOperatingList(Map<String, Object> params) {
		List<Long> ids = sysUnitMapper.selectOperatingId(params);
		List<SysUnit> list = getListByIds(ids, params);
		return list;
	}
	/** 根据Id查询--分页 */
	public Page<SysUnit> getPageByIds(Page<Long> ids, Map<String, Object> params) {
		if (ids != null) {
			Page<SysUnit> page = new Page<SysUnit>(ids.getCurrent(), ids.getSize());
			page.setTotal(ids.getTotal());
			List<SysUnit> records = InstanceUtil.newArrayList();
			for (int i = 0; i < ids.getRecords().size(); i++) {
				records.add(null);
			}
			ExecutorService executorService = Executors.newFixedThreadPool(5);
			for (int i = 0; i < ids.getRecords().size(); i++) {
				final int index = i;
				executorService.execute(new Runnable() {
					public void run() {
						records.set(index, saveSysUnitDTO(ids.getRecords().get(index), params));
					}
				});
			}
			executorService.shutdown();
			try {
				executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				logger.error("awaitTermination", "", e);
			}
			page.setRecords(records);
			return page;
		}
		return new Page<SysUnit>();
	}

	/** 根据Id查询 */
	public List<SysUnit> getListByIds(List<Long> ids, Map<String, Object> params) {
		List<SysUnit> list = InstanceUtil.newArrayList();
		if (ids != null) {
			for (int i = 0; i < ids.size(); i++) {
				list.add(null);
			}
			ExecutorService executorService = Executors.newFixedThreadPool(10);
			for (int i = 0; i < ids.size(); i++) {
				final int index = i;
				executorService.execute(new Runnable() {
					public void run() {
						list.set(index, saveSysUnitDTO(ids.get(index), params));
					}
				});
			}
			executorService.shutdown();
			try {
				executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				logger.error("awaitTermination", "", e);
			}
		}
		return list;
	}

	/** 存储SysUnitFTO **/
	@Transactional
	public SysUnit saveSysUnitDTO(Long id, Map<String, Object> paramw) {
		SysUnit record = queryById(id);
		String keyPro = getCacheKey(id + paramw.get("TENANTID").toString() + "sysUnitDTO");
		SysUnitDTO sysUnitDTO = null;
		try {
			sysUnitDTO = (SysUnitDTO) CacheUtil.getCache().get(keyPro);
		} catch (Exception e) {
			logger.error(Constants.Exception_Head, e);
		}
		if (sysUnitDTO == null) {
			UnitRegisterApply unitRegisterApply = unitRegisterApplyService.queryById(record.getApplyId());
			if (null != unitRegisterApply) {
				String lockKey = getLockKey(id + paramw.get("TENANTID").toString() + "sysUnitDTO");
				if (CacheUtil.getLock(lockKey)) {
					try {
						sysUnitDTO = new SysUnitDTO();
						sysUnitDTO.setAuditOpinion(unitRegisterApply.getRemark());
						sysUnitDTO.setAuditPerson(unitRegisterApply.getPrincipal());
						sysUnitDTO.setAuditTime(unitRegisterApply.getAuditTime());
						sysUnitDTO.setRegisterTime(unitRegisterApply.getCreateTime());
						sysUnitDTO.setUserName(unitRegisterApply.getUserName());
						try {
							CacheUtil.getCache().set(keyPro, sysUnitDTO);
						} catch (Exception e) {
							logger.error(Constants.Exception_Head, e);
						}
					} finally {
						CacheUtil.unlock(lockKey);
					}
				} else {
					logger.debug(getClass().getSimpleName() + ":" + id + "SysUnitDTO" + " retry queryById.");
					sleep(20);
					return queryById(id);
				}
			}
		}
		record.setSysUnitDTO(sysUnitDTO);
		return record;
	}
}
