package com.deehow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.deehow.model.ModelPermissionDTO;
import com.deehow.model.SysMenu;

public interface SysAuthorizeMapper {

	void deleteUserMenu(@Param("userId") Long userId, @Param("permission") String permission);

	void deleteUserRole(@Param("userId") Long userId);

	void deleteRoleMenu(@Param("roleId") Long roleId, @Param("permission") String permission);

	List<Long> getAuthorize(@Param("userId") Long userId);

	List<String> queryPermissionByUserId(@Param("userId") Long userId);

	List<SysMenu> queryMenusPermission();
	
	
	
	
	
	
	/**			分割线		分割线		分割线		分割线		分割线		分割线		分割线		分割线		分割线			*/
	
	
	List<Long> getNewMenuByUser(@Param("userId") Long userId);

	List<ModelPermissionDTO> getNewPermissionsByUser(@Param("userId") Long userId);
	
	void deleteUserRoleByRoleId(@Param("roleId") Long roleId);
}
