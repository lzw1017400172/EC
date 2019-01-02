package com.deehow.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.deehow.model.SysPosition;
import com.deehow.core.base.BaseMapper;

/**
 * <p>
  * 职位表 Mapper 接口
 * </p>
 *
 * @author wz
 * @since 2017-07-08
 */
public interface SysPositionMapper extends BaseMapper<SysPosition> {
	 List<Long> listByIds(@Param("cm") Map<String, Object> params);
}