package com.deehow.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.deehow.core.base.BaseMapper;
import com.deehow.model.SysUserMenu;

public interface SysUserMenuMapper extends BaseMapper<SysUserMenu> {

	List<Map<String, Object>> queryPermissions(@Param("userId") Long userId);
	List<String> queryPermission(@Param("userId") Long id);
	
	int delSysUserMenuByUserId(Long userId);
	int addSysUserMenu(Map<String,Object> map);

	
}