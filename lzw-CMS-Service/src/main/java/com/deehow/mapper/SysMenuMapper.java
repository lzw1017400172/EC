package com.deehow.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.deehow.core.base.BaseMapper;
import com.deehow.model.SysMenu;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
	/** 获取所有权限 */
	public List<Map<String, String>> getPermissions();

	public List<Long> selectIdPage(@Param("cm") Map<String, Object> params);
}