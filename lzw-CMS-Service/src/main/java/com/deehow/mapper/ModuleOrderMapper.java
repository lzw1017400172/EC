package com.deehow.mapper;

import com.deehow.model.ModuleOrder;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deehow.core.base.BaseMapper;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author renzh
 * @since 2018-07-18
 */
public interface ModuleOrderMapper extends BaseMapper<ModuleOrder> {
	ModuleOrder getOperatingPersonnel(@Param("cm")Map<String,Object> param); 
}