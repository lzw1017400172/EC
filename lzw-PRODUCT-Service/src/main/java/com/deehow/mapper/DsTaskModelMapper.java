package com.deehow.mapper;

import com.deehow.model.DsTaskModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author liuzw
 * @since 2018-12-27
 */
public interface DsTaskModelMapper extends com.baomidou.mybatisplus.mapper.BaseMapper<DsTaskModel> {

    List<Long> selectModelIdPage(@Param("cm") Map<String, Object> params);

    void insertAll(List<DsTaskModel> models);
}