package com.deehow.service;

import com.deehow.core.Constants;
import com.deehow.core.base.BaseService;
import com.deehow.core.util.CacheUtil;
import com.deehow.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 单位--注册申请表 服务实现类
 * </p>
 *
 * @author WangShengChao
 * @since 2018-07-18
 */
@Service
@CacheConfig(cacheNames = "unitRegisterApply")
public class UnitRegisterApplyService extends BaseService<UnitRegisterApply> {

	@Autowired
	private SysUnitService sysUnitSerice;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	/**
	 * 待审核任务---审核
	 * 
	 * @param unitRegisterApply
	 */
	public void updateUnitRegisterApply(UnitRegisterApply unitRegisterApply) {

		try {
			String lockKey = getLockKey("U" + unitRegisterApply.getId());
			// 更新单位申请表
			if (CacheUtil.getLock(lockKey)) {
				try {
					mapper.updateById(unitRegisterApply);
					unitRegisterApply = mapper.selectById(unitRegisterApply.getId());
				} finally {
					CacheUtil.unlock(lockKey);
				}
			}
			// 获取单位申请表的全部信息----1、存储到缓存 2、如果是通过审核将单位申请表数据信息插入到租户表
			if (unitRegisterApply.getStatus() == 1) {// 审核通过
				SysUnit sysUnit = new SysUnit();
				sysUnit.setApplyId(unitRegisterApply.getId());
				sysUnit.setAddress(unitRegisterApply.getAddress());
				sysUnit.setEnable(1);
				sysUnit.setPhone(unitRegisterApply.getPhone());
				sysUnit.setPrincipal("");
				sysUnit.setUnitName(unitRegisterApply.getUnitName());
				sysUnit.setCreateBy(unitRegisterApply.getUpdateBy());
				sysUnit.setCreateTime(new Date());
				sysUnit.setUpdateBy(unitRegisterApply.getUpdateBy());
				sysUnit.setUpdateTime(new Date());
				// 生成单位编号
				sysUnit.setUnitCode(createUnitCode());
				// 将数据插入到租户表
				sysUnitSerice.update(sysUnit);
				// 创建用户并分配模块
				createSysUser(unitRegisterApply, sysUnit);
			}
			CacheUtil.getCache().set(getCacheKey(unitRegisterApply.getId()), unitRegisterApply);
		} catch (Exception e) {
			// 若触发单位编码唯一约束，则递归重新获取单位编码
			if (e.getMessage().contains("for key 'uk_unitCode'")) {
				updateUnitRegisterApply(unitRegisterApply);
			} else {
				logger.error(Constants.Exception_Head, e);
			}
		}
	}

	/**
	 * 生成单位编码 规则：ZH+日期(yyyyMMddHH)+四位随机数
	 * 
	 * @return
	 */
	public String createUnitCode() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		StringBuffer unitCode = new StringBuffer();
		unitCode.append("ZH");
		unitCode.append(sdf.format(new Date()));
		unitCode.append(String.valueOf((int) ((Math.random() * 9 + 1) * 1000)));
		return unitCode.toString();
	}

	/**
	 * 审核通过--用户表、用户菜单表、用户角色表以及用户模块表数据更新
	 * 
	 * @param unitRegisterApply
	 * @param sysUnit
	 */
	public void createSysUser(UnitRegisterApply unitRegisterApply, SysUnit sysUnit) {
		Date date = new Date();
		// 系统用户添加
		SysUser sysUser = new SysUser();
		sysUser.setAccount(unitRegisterApply.getPhone());
		sysUser.setUnitName(unitRegisterApply.getUnitName());
		sysUser.setPassword(unitRegisterApply.getPassword());
		sysUser.setUserName(unitRegisterApply.getUserName());
		sysUser.setUserType(2);
		sysUser.setEmail(unitRegisterApply.getEmail());
		sysUser.setEnable(1);
		sysUser.setPhone(sysUnit.getPhone());
		sysUser.setTenantId(sysUnit.getId());
		sysUser.setCreateBy(sysUnit.getCreateBy());
		sysUser.setCreateTime(date);
		sysUser.setUpdateBy(sysUnit.getUpdateBy());
		sysUser.setUpdateTime(date);
		sysUser.setEntryTime(date);
		sysUser.setLogin(1);
		sysUserService.update(sysUser);
//		// 菜单
//		Map<String, Object> paramMenu = new HashMap<String, Object>();
//		paramMenu.put("enable", "1");
//		// paramMenu.put("parentId", 0);
//		paramMenu.put("menuType", "1");
//		List<SysMenu> listMenu = sysMenuService.queryList(paramMenu);
//		for (int i = 0; i < listMenu.size(); i++) {
//			SysUserMenu sysUserMenu = new SysUserMenu();
//			sysUserMenu.setMenuId(listMenu.get(i).getId());
//			sysUserMenu.setUserId(sysUser.getId());
//			sysUserMenu.setPermission("");
//			sysUserMenu.setEnable(1);
//			sysUserMenu.setCreateBy(sysUser.getId());
//			sysUserMenu.setCreateTime(new Date());
//			sysUserMenu.setUpdateBy(sysUser.getId());
//			sysUserMenu.setUpdateTime(new Date());
//			sysUserMenu.setTenantId(sysUnit.getId());
//			sysUserMenuService.update(sysUserMenu);
//		}
//		// 模块
//		Map<String, Object> paramModel = new HashMap<String, Object>();
//		paramModel.put("enabled", "Y");
//		paramModel.put("tenantId", sysUnit.getId());
//		paramModel.put("isBasic", "1");
//		List<SysModel> listModel = sysModelService.queryList(paramModel);
//		Calendar curr = Calendar.getInstance();
//		curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) + 10);
//		Date date = curr.getTime();
//		for (int i = 0; i < listModel.size(); i++) {
//			SysTenantModel sysTenantModel = new SysTenantModel();
//			sysTenantModel.setModelId(listModel.get(i).getId());
//			sysTenantModel.setEnable(1);
//			sysTenantModel.setModelValidate(date);
//			sysTenantModel.setTenantId(sysUnit.getId());
//			sysTenantModel.setCreateBy(sysUser.getId());
//			sysTenantModel.setCreateTime(new Date());
//			sysTenantModel.setUpdateBy(sysUser.getId());
//			sysTenantModel.setUpdateTime(new Date());
//			sysTenantModelService.update(sysTenantModel);
//		}
		// 添加内置角色
		SysRole sysRole = new SysRole();
		sysRole.setTenantId(sysUnit.getId());
		sysRole.setRoleName("Administrators");
		sysRole.setRoleType(3);
		sysRole.setEnable(1);
		sysRole.setCreateBy(unitRegisterApply.getUpdateBy());
		sysRole.setCreateTime(date);
		sysRole.setUpdateBy(unitRegisterApply.getUpdateBy());
		sysRole.setUpdateTime(date);
		sysRoleService.update(sysRole);
		//用户角色
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setEnable(1);
		sysUserRole.setTenantId(sysUnit.getId());
		sysUserRole.setUserId(sysUser.getId());
		sysUserRole.setRoleId(sysRole.getId());
		sysUserRole.setCreateBy(sysUser.getId());
		sysUserRole.setCreateTime(date);
		sysUserRole.setUpdateBy(sysUser.getId());
		sysUserRole.setUpdateTime(date);
		sysUserRoleService.update(sysUserRole);

		SysRole sysRole1 = new SysRole();
		sysRole.setTenantId(sysUnit.getId());
		sysRole.setRoleName("General User");
		sysRole.setRoleType(3);
		sysRole.setEnable(1);
		sysRole.setCreateBy(unitRegisterApply.getUpdateBy());
		sysRole.setCreateTime(date);
		sysRole.setUpdateBy(unitRegisterApply.getUpdateBy());
		sysRole.setUpdateTime(date);
		sysRoleService.update(sysRole1);

		SysRole sysRole2 = new SysRole();
		sysRole.setTenantId(sysUnit.getId());
		sysRole.setRoleName("Project Managers");
		sysRole.setRoleType(3);
		sysRole.setEnable(1);
		sysRole.setCreateBy(unitRegisterApply.getUpdateBy());
		sysRole.setCreateTime(date);
		sysRole.setUpdateBy(unitRegisterApply.getUpdateBy());
		sysRole.setUpdateTime(date);
		sysRoleService.update(sysRole2);

	}
}
