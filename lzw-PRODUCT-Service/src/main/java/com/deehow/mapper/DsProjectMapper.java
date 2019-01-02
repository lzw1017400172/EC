package com.deehow.mapper;

import com.deehow.core.base.BaseMapper;
import com.deehow.model.DsProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
public interface DsProjectMapper extends BaseMapper<DsProject> {

    Integer selectCountByPageCondition(@Param("cm") Map<String, Object> params);

    List<DsProject> selectModelPage(@Param("cm") Map<String, Object> params);

}