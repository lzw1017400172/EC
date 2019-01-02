package com.deehow.mapper;

import java.util.List;
import java.util.Map;

import com.deehow.core.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.deehow.model.SysUnit;

/**
 * @author ShenHuaJie
 *
 */
public interface SysUnitMapper extends BaseMapper<SysUnit> {

	/**
	 * 租户表与单位申请表级联查询
	 * 
	 * @param param
	 * @return
	 */
	List<Map<String,Object>> selectSysUnit(@Param("cm") Map<String, Object> param);
	/**
	 *查询没有专员的租户和自己负责的租户
	 * 
	 * @param param
	 * @return
	 */
	List<Long> selectOperatingId(@Param("cm") Map<String, Object> param);
}
