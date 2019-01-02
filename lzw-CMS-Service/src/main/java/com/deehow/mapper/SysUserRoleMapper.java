package com.deehow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.deehow.core.base.BaseMapper;
import com.deehow.model.SysUserRole;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

	void deleteRoleId(@Param("roleId") Long roleId);

	List<Long> queryRoleIdListByUserId(@Param("userId") Long userId);
	
	List<Long> queryUserIdListByRoleId(@Param("roleId") Long roleId);
	
	void insertAll(List<SysUserRole> list);

	List<Long> queryUserIdListByRoleIdList(List<Long> roleIds);

}