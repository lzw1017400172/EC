package com.deehow.model;

import java.util.List;

import com.deehow.core.base.BaseModel;


public class OrganizeVO extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Long userId;
	private Long tenantId;
	private Long roleId;
	
	private List<SysUserRole> sysUserRoleList;
	private List<SysRoleMenu> sysRoleMenuList;
	private List<Long> sysUserIdList;
	
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public List<SysUserRole> getSysUserRoleList() {
		return sysUserRoleList;
	}


	public void setSysUserRoleList(List<SysUserRole> sysUserRoleList) {
		this.sysUserRoleList = sysUserRoleList;
	}


	public Long getTenantId() {
		return tenantId;
	}


	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}


	public Long getRoleId() {
		return roleId;
	}


	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public List<SysRoleMenu> getSysRoleMenuList() {
		return sysRoleMenuList;
	}


	public void setSysRoleMenuList(List<SysRoleMenu> sysRoleMenuList) {
		this.sysRoleMenuList = sysRoleMenuList;
	}


	public List<Long> getSysUserIdList() {
		return sysUserIdList;
	}


	public void setSysUserIdList(List<Long> sysUserIdList) {
		this.sysUserIdList = sysUserIdList;
	}
	

}