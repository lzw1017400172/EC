package com.deehow.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deehow.core.base.BaseMapper;
import com.deehow.model.SysModelMenu;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author hzy
 * @since 2017-07-27
 */
public interface SysModelMenuMapper extends BaseMapper<SysModelMenu> {
	List<Long> selectIdByModelIds(@Param("list")List<Long> list);
	
//    List<SysModelMenu> selectMenuByModelIdsAndUserId(Map<Object,Object> map);
//
//	List<SysModelMenu> selectMenuByRole(Map<Object, Object> map);
//
//	List<Long> selectIdByMap(@Param("cm") Map<Object, Object> map);
}