package com.deehow.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deehow.core.base.BaseService;
import com.deehow.core.exception.BusinessException;
import com.deehow.mapper.SysModelMapper;
import com.deehow.mapper.SysTenantModelMapper;
import com.deehow.model.SysMidMvDTO;
import com.deehow.model.SysTenantModel;

/**
 * <p>
 * 租户模块表 服务实现类
 * </p>
 *
 * @author hzy
 * @since 2017-07-27
 */
@Service
public class SysTenantModelService extends BaseService<SysTenantModel>{
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysModelMenuService sysModelMenuService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysUserMenuService sysUserMenuService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysModelMapper sysModelMapper;
	@Autowired
	private SysTenantModelMapper sysTenantModelMapper;
	
//	/** 添加模块 */
//	@Transactional
//	public void updateModel(SysTenantModel sysTenantModel) {
//		update(sysTenantModel);
//		//模块
//		Map<String, Object> paramModel = new HashMap<String, Object>();
//		paramModel.put("modelId", sysTenantModel.getModelId());
//		List<SysModelMenu> listModel = sysModelMenuService.queryList(paramModel);
//		//角色
//		SysRole sysRole= new SysRole();
//		sysRole.setEnable(1);
//		sysRole.setModelId(sysTenantModel.getModelId());
//		sysRole.setRoleType(2);
//		sysRole.setTenantId(sysTenantModel.getTenantId());
//		sysRole.setCreateBy(sysTenantModel.getCreateBy());
//		sysRole.setCreateTime(new Date());
//		sysRole.setUpdateBy(sysTenantModel.getCreateBy());
//		sysRole.setUpdateTime(new Date());
//		SysRole sysRoleNew = sysRoleService.update(sysRole);
//		String[] array={"add","delete","read","update"};
//		for (int i = 0; i < listModel.size(); i++) {
//			for (int j = 0; j < array.length; j++) {
//				SysRoleMenu sysRoleMenu = new SysRoleMenu();
//				sysRoleMenu.setMenuId(listModel.get(i).getId());
//				sysRoleMenu.setRoleId(sysRoleNew.getId());
//				sysRoleMenu.setPermission(array[j]);
//				sysRoleMenu.setEnable(1);
//				sysRoleMenu.setTenantId(sysTenantModel.getTenantId());
//				sysRoleMenu.setCreateBy(sysTenantModel.getCreateBy());
//				sysRoleMenu.setCreateTime(new Date());
//				sysRoleMenu.setUpdateBy(sysTenantModel.getCreateBy());
//				sysRoleMenu.setUpdateTime(new Date());
//				sysRoleMenuService.update(sysRoleMenu);
//			}
//			SysUserMenu sysUserMenu =new SysUserMenu();
//			sysUserMenu.setMenuId(listModel.get(i).getId());
//			sysUserMenu.setUserId(sysTenantModel.getCreateBy());
//			sysUserMenu.setEnable(1);
//			sysUserMenu.setTenantId(sysTenantModel.getTenantId());
//			sysUserMenu.setCreateBy(sysTenantModel.getCreateBy());
//			sysUserMenu.setCreateTime(new Date());
//			sysUserMenu.setUpdateBy(sysTenantModel.getCreateBy());
//			sysUserMenu.setUpdateTime(new Date());
//			sysUserMenuService.update(sysUserMenu);
//		}
//		SysUserRole sysUserRole=new SysUserRole();
//		sysUserRole.setUserId(sysTenantModel.getCreateBy());
//		sysUserRole.setRoleId(sysRoleNew.getId());
//		sysUserRole.setEnable(1);
//		sysUserRole.setTenantId(sysTenantModel.getTenantId());
//		sysUserRole.setCreateBy(sysTenantModel.getCreateBy());
//		sysUserRole.setCreateTime(new Date());
//		sysUserRole.setUpdateBy(sysTenantModel.getCreateBy());
//		sysUserRole.setUpdateTime(new Date());
//		sysUserRoleService.update(sysUserRole);
//	}
//	
//	@Transactional
//	public List<SysTenantModel> readListNotice(Map<String,Object> param){
//		List<Long> ids = ((SysTenantModelMapper)mapper).readIdPage(param);
//		List<SysTenantModel> list = InstanceUtil.newArrayList();
//		if (ids != null) {
//			for (int i = 0; i < ids.size(); i++) {
//				list.add(null);
//			}
//			ExecutorService executorService = Executors.newFixedThreadPool(10);
//			for (int i = 0; i < ids.size(); i++) {
//				final int index = i;
//				executorService.execute(new Runnable() {
//					public void run() {
//						list.set(index, readById(ids.get(index)));
//					}
//				});
//			}
//			executorService.shutdown();
//			try {
//				executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
//			} catch (InterruptedException e) {
//				logger.error("awaitTermination", "", e);
//			}
//		}
//		
//		return list;
//	}
//	@Transactional
//	public SysTenantModel readById(Long id){
//		String key = getCacheKey(id);
//		SysTenantModel record = null;
//		try {
//			record = (SysTenantModel) CacheUtil.getCache().get(key);
//		} catch (Exception e) {
//			logger.error(Constants.Exception_Head, e);
//		}
//		if (record == null) {
//			String lockKey = getLockKey(id);
//			if (CacheUtil.getLock(lockKey)) {
//				try {
//					record = mapper.selectById(id);
//					try {
//						CacheUtil.getCache().set(key, record);
//					} catch (Exception e) {
//						logger.error(Constants.Exception_Head, e);
//					}
//				} finally {
//					CacheUtil.unlock(lockKey);
//				}
//			} else {
//				logger.debug(getClass().getSimpleName() + ":" + id + " retry queryById.");
//				sleep(20);
//				return queryById(id);
//			}
//		}
//		
//		//模块名称
//		String keyModel = getCacheKey(id+"sysModel");
//		SysModel sysModel = null;
//		try {
//			sysModel = (SysModel) CacheUtil.getCache().get(keyModel);
//		} catch (Exception e) {
//			logger.error(Constants.Exception_Head, e);
//		}
//		if (sysModel == null) {
//			String lockKey = getLockKey(id+"sysModel");
//			if (CacheUtil.getLock(lockKey)) {
//				try {
//					sysModel = sysModelMapper.selectById(record.getModelId());
//					try {
//						CacheUtil.getCache().set(keyModel, sysModel);
//					} catch (Exception e) {
//						logger.error(Constants.Exception_Head, e);
//					}
//				} finally {
//					CacheUtil.unlock(lockKey);
//				}
//			} else {
//				logger.debug(getClass().getSimpleName() + ":" + id + "sysModel retry queryById.");
//				sleep(20);
//				return queryById(id);
//			}
//		}
//		record.setApplicationName(sysModel.getModelName());
//		return record;
//	}
//	
//	public List<Long> queryListByModelType(Map<String,Object> param){
//		return sysTenantModelMapper.queryListByModelType(param);
//	}
//	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 查询指定租户所拥有的 模块id+到期时间
	 * @param param
	 * @return
	 */
	public List<SysMidMvDTO> queryMidMvByTenantId(Map<String,Object> param){
		return sysTenantModelMapper.queryMidMvByTenantId(param);
	}
	
}
