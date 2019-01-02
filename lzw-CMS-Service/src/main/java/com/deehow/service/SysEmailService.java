package com.deehow.service;

import com.deehow.core.base.BaseService;
import com.deehow.model.SysEmail;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * @author ShenHuaJie
 *
 */
@Service
@CacheConfig(cacheNames = "sysEmail")
public class SysEmailService extends BaseService<SysEmail> {

}
