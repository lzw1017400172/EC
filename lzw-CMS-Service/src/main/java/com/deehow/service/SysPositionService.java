package com.deehow.service;

import java.util.List;
import java.util.Map;

import com.deehow.core.base.BaseService;
import com.deehow.mapper.SysPositionMapper;
import com.deehow.mapper.SysUserMapper;
import com.deehow.model.SysPosition;
import com.deehow.model.SysUser;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 职位表 服务实现类
 * </p>
 *
 * @author wz
 * @since 2017-07-08
 */
@Service
@CacheConfig(cacheNames = "sysPosition")
public class SysPositionService  extends BaseService<SysPosition> {
	public List<SysPosition> listByIds(Map<String,Object> params){
		List<Long> userLong= ((SysPositionMapper)mapper).listByIds(params);
		return super.getList(userLong);
		
	}
}
