package com.deehow.mapper;

import com.deehow.model.DsProjectUser;
import com.deehow.core.base.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
public interface DsProjectUserMapper extends BaseMapper<DsProjectUser> {

    void insertAll(List<DsProjectUser> users);
}