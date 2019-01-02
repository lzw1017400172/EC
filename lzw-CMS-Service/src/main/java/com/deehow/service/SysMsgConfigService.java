package com.deehow.service;

import com.deehow.model.SysMsgConfig;
import com.deehow.core.base.BaseService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * <p>
 *   服务实现类
 * </p>
 *
 * @author ShenHuaJie
 * @since 2017-03-12
 */
@Service
@CacheConfig(cacheNames = "sysMsgConfig")
public class SysMsgConfigService extends BaseService<SysMsgConfig> {
	
}