package com.deehow.core.util;

import com.deehow.core.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemindUtil {
	 private static Logger logger = LogManager.getLogger();
	 
	public static void setRemindAdd(String model,Long user) {
		int num = 0;
		try {
			num= (int) CacheUtil.getCache().get(Constants.REMIND+model+user.toString());
		} catch (Exception e) {
			num=0;
		}
		num++;
		try {
			CacheUtil.getCache().setForEver(Constants.REMIND+model+user.toString(),num);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	public static void setRemindUserAdd(String model,Long other) {
		try {
			CacheUtil.getCache().getSetKeyAll(Constants.REMIND+model+other.toString()+ "*");
		} catch (Exception e) {
			logger.error(e);
		}
	}
	public static int getRemindNum(String model,Long user) {
		int num = 0;
		try {
			num= (int) CacheUtil.getCache().get(Constants.REMIND+model+user.toString());
		} catch (Exception e) {
			num=0;
			logger.error(e);
		}
		return num;
	}
	public static int getRemindNum(String model,Long user,Long...longs) {
		int num = 0;
		try {
			num= (int) CacheUtil.getCache().get(Constants.REMIND+model+user.toString());
		} catch (Exception e) {
			num=0;
			logger.error(e);
		}
		try {
			for (int i = 0; i < longs.length; i++) {
					num +=CacheUtil.getCache().getSetKeyOne(Constants.REMIND+model+longs[i]+user.toString());
			}
		} catch (Exception e) {
			num+=0;
			logger.error(e);
		}
		return num;
	}
	public static void setRemindZero(String model,Long user) {
		try {
			CacheUtil.getCache().setZero(Constants.REMIND+model,user.toString());
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
