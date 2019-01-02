package com.deehow.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.deehow.core.base.BaseMapper;
import com.deehow.model.SysDic;

public interface SysDicMapper extends BaseMapper<SysDic> {
    List<Long> selectIdPage(@Param("cm") Map<String, Object> params);
}