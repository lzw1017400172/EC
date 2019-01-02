package com.deehow.service;

import com.deehow.core.base.BaseService;
import com.deehow.model.SysNotice;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * @author ShenHuaJie
 *
 */
@Service
@CacheConfig(cacheNames = "sysNotice")
public class SysNoticeService extends BaseService<SysNotice> {

}
