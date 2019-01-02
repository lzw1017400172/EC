package com.deehow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.deehow.core.base.BaseMapper;
import com.deehow.model.SysSession;

public interface SysSessionMapper extends BaseMapper<SysSession> {

    Long queryBySessionId(String sessionId);

    List<String> querySessionIdByAccount(@Param("cm") SysSession sysSession);

}