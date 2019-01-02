package com.deehow.service;

import com.deehow.core.base.BaseService;
import com.deehow.model.SysEmailConfig;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * @author ShenHuaJie
 *
 */
@Service
@CacheConfig(cacheNames = "sysEmailConfig")
public class SysEmailConfigService extends BaseService<SysEmailConfig> {

}
