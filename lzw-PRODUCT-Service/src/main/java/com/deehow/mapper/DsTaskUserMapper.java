package com.deehow.mapper;

import com.deehow.core.base.BaseMapper;
import com.deehow.model.DsTaskUser;

import java.util.List;

/**
 * <p>
 * Mapper接口
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
public interface DsTaskUserMapper extends BaseMapper<DsTaskUser> {

    void insertAll(List<DsTaskUser> users);
}