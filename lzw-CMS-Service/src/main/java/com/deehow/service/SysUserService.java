package com.deehow.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.plugins.Page;
import com.deehow.core.Constants;
import com.deehow.core.base.BaseService;
import com.deehow.core.support.login.ThirdPartyUser;
import com.deehow.core.util.CacheUtil;
import com.deehow.core.util.InstanceUtil;
import com.deehow.core.util.SecurityUtil;
import com.deehow.mapper.SysUserMapper;
import com.deehow.mapper.SysUserThirdpartyMapper;
import com.deehow.model.SysDept;
import com.deehow.model.SysMenu;
import com.deehow.model.SysModel;
import com.deehow.model.SysRole;
import com.deehow.model.SysTenantModel;
import com.deehow.model.SysUnit;
import com.deehow.model.SysUser;
import com.deehow.model.SysUserMenu;
import com.deehow.model.SysUserRole;
import com.deehow.model.SysUserThirdparty;

/**
 * SysUser服务实现类
 * 
 * @author ShenHuaJie
 * @version 2016-08-27 22:39:42
 */
@Service
@CacheConfig(cacheNames = "sysUser")
public class SysUserService extends BaseService<SysUser> {
	@Autowired
	private SysUserThirdpartyMapper thirdpartyMapper;
	@Autowired
	private SysUnitService sysUnitService;
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysUserMenuService sysUserMenuService;
	@Autowired
	private SysModelService sysModelService;
	@Autowired
	private SysTenantModelService sysTenantModelService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysDeptService sysDeptService;
	
	/** 查询第三方帐号用户Id */
	@Cacheable
	public Long queryUserIdByThirdParty(ThirdPartyUser param) {
		return thirdpartyMapper.queryUserIdByThirdParty(param.getProvider(), param.getOpenid());
	}

	/** 保存第三方帐号 */
	@Transactional
	public SysUser insertThirdPartyUser(ThirdPartyUser thirdPartyUser) {
		SysUser sysUser = new SysUser();
		sysUser.setSex(0);
		sysUser.setUserType(1);
		sysUser.setPassword(SecurityUtil.encryptPassword("123456"));
		sysUser.setUserName(thirdPartyUser.getUserName());
		sysUser.setAvatar(thirdPartyUser.getAvatarUrl());
		// 初始化第三方信息
		SysUserThirdparty thirdparty = new SysUserThirdparty();
		thirdparty.setProvider(thirdPartyUser.getProvider());
		thirdparty.setOpenId(thirdPartyUser.getOpenid());
		thirdparty.setCreateTime(new Date());

		this.update(sysUser);
		sysUser.setAccount(thirdparty.getProvider() + sysUser.getId());
		this.update(sysUser);
		thirdparty.setUserId(sysUser.getId());
		thirdpartyMapper.insert(thirdparty);
		return sysUser;
	}
	
	public void init() {
		List<Long> list = ((SysUserMapper) mapper).selectIdPage(Collections.<String, Object>emptyMap());
		for (Long id : list) {
			CacheUtil.getCache().set(getCacheKey(id), mapper.selectById(id));
		}
	}
	
	/** 注册 */
	@Transactional
	public void regin(SysUser sysUser) {
		SysUnit sysUnit = new SysUnit();
		sysUnit.setUnitName(sysUser.getUnitName());
		sysUnit.setPhone(sysUser.getAccount());
		sysUnit.setPrincipal(sysUser.getUserName());
		sysUnit.setCreateTime(new Date());
		sysUnit.setUpdateTime(new Date());
		sysUnit.setBusiness(sysUser.getIndustryName());
		SysUnit unit = sysUnitService.update(sysUnit);
		sysUser.setPhone(sysUser.getAccount());
		sysUser.setTenantId(unit.getId());
		sysUser.setUserType(2);
		//b保存用户
			sysUser = update(sysUser);
			
			//菜单
			Map<String, Object> paramMenu = new HashMap<String, Object>();
			paramMenu.put("enable", "1");
//			paramMenu.put("parentId", 0);
			paramMenu.put("menuType", "1");
			List<SysMenu> listMenu = sysMenuService.queryList(paramMenu);
			for (int i = 0; i < listMenu.size(); i++) {
				SysUserMenu sysUserMenu =new SysUserMenu();
				sysUserMenu.setMenuId(listMenu.get(i).getId());
				sysUserMenu.setUserId(sysUser.getId());
				sysUserMenu.setPermission("");
				sysUserMenu.setEnable(1);
				sysUserMenu.setCreateBy(sysUser.getId());
				sysUserMenu.setCreateTime(new Date());
				sysUserMenu.setUpdateBy(sysUser.getId());
				sysUserMenu.setUpdateTime(new Date());
				sysUserMenu.setTenantId(unit.getId());
				sysUserMenuService.update(sysUserMenu);
			}
			//模块
			Map<String, Object> paramModel = new HashMap<String, Object>();
			paramModel.put("enabled", "Y");
			paramModel.put("tenantId", unit.getId());
			paramModel.put("isBasic", "1");
			List<SysModel> listModel = sysModelService.queryList(paramModel);
			Calendar curr = Calendar.getInstance();
			curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)+10);
			Date date=curr.getTime();
			for (int i = 0; i < listModel.size(); i++) {
				SysTenantModel sysTenantModel =new SysTenantModel();
				sysTenantModel.setModelId(listModel.get(i).getId());
				sysTenantModel.setEnable(1);
				sysTenantModel.setModelValidate(date);
				sysTenantModel.setTenantId(unit.getId());
				sysTenantModel.setCreateBy(sysUser.getId());
				sysTenantModel.setCreateTime(new Date());
				sysTenantModel.setUpdateBy(sysUser.getId());
				sysTenantModel.setUpdateTime(new Date());
				sysTenantModelService.update(sysTenantModel);
			}
			//角色
			Map<String, Object> paramRole = new HashMap<String, Object>();
			paramRole.put("enable", "1");
			paramRole.put("roleType", "2");
			paramRole.put("roleName", "管理员");
			List<SysRole> listRole = sysRoleService.queryList(paramRole);
			for (int i = 0; i < listRole.size(); i++) {
				SysUserRole sysUserRole =new SysUserRole();
				sysUserRole.setEnable(1);
				sysUserRole.setTenantId(unit.getId());
				sysUserRole.setUserId(sysUser.getId());
				sysUserRole.setRoleId(listRole.get(i).getId());
				sysUserRole.setCreateBy(sysUser.getId());
				sysUserRole.setCreateTime(new Date());
				sysUserRole.setUpdateBy(sysUser.getId());
				sysUserRole.setUpdateTime(new Date());
				sysUserRoleService.update(sysUserRole);
			}
		sysUnit.setCreateBy(sysUser.getId());
		sysUnit.setUpdateBy(sysUser.getId());
		sysUnit.setId(unit.getId());
		sysUnitService.update(sysUnit);
	}

	public List<Long> getUserIds(Map<String,Object> param){
		return  mapper.selectIdPage(param);
	}
	
	
	
	
	public Page<SysUser> queryLinked(Map<String, Object> params) {
		Page<Long> page = getPage(params);
		page.setRecords(mapper.selectIdPage(page, params));
		return getPageLinked(page);
	}
	
	/** 根据Id查询(默认类型T) */
	public Page<SysUser> getPageLinked(Page<Long> ids) {
		if (ids != null) {
			Page<SysUser> page = new Page<SysUser>(ids.getCurrent(), ids.getSize());
			page.setTotal(ids.getTotal());
			List<SysUser> records = InstanceUtil.newArrayList();
			for (int i = 0; i < ids.getRecords().size(); i++) {
				records.add(null);
			}
			ExecutorService executorService = Executors.newFixedThreadPool(5);
			for (int i = 0; i < ids.getRecords().size(); i++) {
				final int index = i;
				executorService.execute(new Runnable() {
					public void run() {
						records.set(index, queryByIdLinked(ids.getRecords().get(index)));
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
		return new Page<SysUser>();
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public SysUser queryByIdLinked(Long id) {
		String key = getCacheKey(id);
		SysUser record = null;
		try {
			record = (SysUser) CacheUtil.getCache().get(key);
		} catch (Exception e) {
			logger.error(Constants.Exception_Head, e);
		}
		if (record == null) {
			String lockKey = getLockKey(id);
			if (CacheUtil.getLock(lockKey)) {
				try {
					record = mapper.selectById(id);
					try {
						CacheUtil.getCache().set(key, record);
					} catch (Exception e) {
						logger.error(Constants.Exception_Head, e);
					}
				} finally {
					CacheUtil.unlock(lockKey);
				}
			} else {
				logger.debug(getClass().getSimpleName() + ":" + id + " retry queryById.");
				sleep(20);
				return queryByIdLinked(id);
			}
		}
		if(record != null){
			if(record.getOtherDeptId() != null && record.getOtherDeptId().longValue() > 0){
				SysDept dept = sysDeptService.queryById(record.getOtherDeptId());
				record.setOtherDeptName(dept == null ? "" :dept.getDeptName());
			}
		}
		
		return record;
	}
	
	/**
	 * 根据userIds获取部门集合信息
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> getDepartments(Map<String, Object> param) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String[] userIds = param.get("userIds").toString().split(",");
		StringBuffer deptNames = new StringBuffer();
		StringBuffer deptIds = new StringBuffer();
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		for (int index = 0; index < userIds.length; index++) {
			long id = Long.valueOf(userIds[index]);
			executorService.execute(new Runnable() {
				public void run() {
					SysUser user = queryById(id);
					SysDept dept = sysDeptService.queryById(user.getDeptId());
					deptNames.append(dept.getDeptName()+",");
					deptIds.append(dept.getId()+",");				}
			});
		}
		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			logger.error("awaitTermination", "", e);
		}
		resultMap.put("deptNames", StringUtils.isEmpty(deptNames)?"":deptNames.substring(0, deptNames.length()-1));
		resultMap.put("deptIds", StringUtils.isEmpty(deptIds)?"":deptIds.substring(0, deptIds.length()-1));
		return resultMap;
	}
}
