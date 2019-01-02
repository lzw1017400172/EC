package com.deehow.core.util;

import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.deehow.core.Constants;

public class CodeUtil{
	protected static Logger logger = LogManager.getLogger(CodeUtil.class);
	
	public static Integer getCode(String keySign){
		Integer code = null;
		String key = getCacheKey(keySign);
		try {
			code = (Integer) CacheUtil.getRedisHelper().get(key);
		} catch (Exception e) {
			logger.error(Constants.Exception_Head, e);
		}
		if(code != null){
			String lockKey = getLockKey(keySign);
			if (CacheUtil.getLock(lockKey)) {
				try {
					code = code+1;
					return code;
				} finally {
					CacheUtil.unlock(lockKey);
				}
			} else {
				logger.debug(CodeUtil.class.getSimpleName() + ":" + keySign + " retry getCode.");
				sleep(20);
				return getCode(keySign);
			}
			
		}
		return null;
	}
	
	public static void setCode(String keySign,int codeNum){
		String key = getCacheKey(keySign);
		String lockKey = getLockKey(keySign);
		if (CacheUtil.getLock(lockKey)) {
			try {
				try {
					Integer num = (Integer) CacheUtil.getRedisHelper().get(key);
					if(num == null || codeNum > (int)num){
						CacheUtil.getRedisHelper().set(key, codeNum);
					}
				} catch (Exception e) {
					logger.error(Constants.Exception_Head, e);
				}
			} finally {
				CacheUtil.unlock(lockKey);
			}
		} else {
			logger.debug(CodeUtil.class.getSimpleName() + ":" + keySign + " retry setCode.");
			sleep(20);
			setCode(keySign, codeNum);
		}
	}
	
	
	/** 获取缓存键值 */
	protected static String getCacheKey(String id) {
		String cacheName = "com.deehow.core.util.CodeUtil";
		return new StringBuilder(Constants.CACHE_NAMESPACE).append(cacheName).append(":").append(id).toString();
	}
	
	/** 获取缓存键值 */
	protected static String getLockKey(String id) {
		String cacheName = "com.deehow.core.util.CodeUtil";
		return new StringBuilder(Constants.CACHE_NAMESPACE).append(cacheName).append(":LOCK:").append(id).toString();
	}
	
	
	protected static void sleep(int millis) {
		try {
			Thread.sleep(RandomUtils.nextLong(10, millis));
		} catch (InterruptedException e) {
			logger.error("", e);
		}
	}

}
