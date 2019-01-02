package com.deehow.mapper;

import com.deehow.model.SysModel;
import com.deehow.core.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author hzy
 * @since 2017-07-27
 */
public interface SysModelMapper extends BaseMapper<SysModel> {
//    List<Long> selectIdPhonePage(@Param("cm") Map<String, Object> params);
//
//    List<Long> selectIdPhonePage(RowBounds rowBounds, @Param("cm") Map<String, Object> params);
//
//    List<Long> selectIdMinePage(@Param("cm") Map<String, Object> params);
	List<SysModel> selectModelByIds(@Param("cm")Map<String,Object> params);
}