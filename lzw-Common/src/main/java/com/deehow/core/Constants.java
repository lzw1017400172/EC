package com.deehow.core;

import java.util.Map;

import com.deehow.core.util.InstanceUtil;
import com.deehow.core.util.PropertiesUtil;

/**
 * 常量表
 * 
 * @author ShenHuaJie
 * @version $Id: Constants.java, v 0.1 2014-2-28 上午11:18:28 ShenHuaJie Exp $
 */
public interface Constants {
    /**
     * 异常信息统一头信息<br>
     * 非常遗憾的通知您,程序发生了异常
     */
    public static final String Exception_Head = "OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :";
    /** 缓存键值 */
    public static final Map<Class<?>, String> cacheKeyMap = InstanceUtil.newHashMap();
    /** 操作名称 */
    public static final String OPERATION_NAME = "OPERATION_NAME";
    /** 客户端语言 */
    public static final String USERLANGUAGE = "userLanguage";
    /** 客户端主题 */
    public static final String WEBTHEME = "webTheme";
    /** 当前用户 */
    public static final String CURRENT_USER = "CURRENT_USER";
    /** 当前用户类型 */
    public static final String CURRENT_USER_TYPE = "CURRENT_USER_TYPE";
    /** 当前租户 */
    public static final String CURRENT_TENANT = "CURRENT_TENANT";
    /** 上次请求地址 */
    public static final String PREREQUEST = "PREREQUEST";
    /** 上次请求时间 */
    public static final String PREREQUEST_TIME = "PREREQUEST_TIME";
    /** 登录地址 */
    public static final String LOGIN_URL = "/login.html";
    /** 非法请求次数 */
    public static final String MALICIOUS_REQUEST_TIMES = "MALICIOUS_REQUEST_TIMES";
    /** 缓存命名空间 */
    public static final String CACHE_NAMESPACE = "deehow:";
    /** 缓存命名空间token */
    public static final String WWW_NAMESPACE = "www:";
    /** 在线用户数量 */
    public static final String ALLUSER_NUMBER = "SYSTEM:" + CACHE_NAMESPACE + "ALLUSER_NUMBER";
    /** TOKEN */
    public static final String TOKEN_KEY = WWW_NAMESPACE + "TOKEN_KEY";
    
    public static final String AW_TYPE = "aw_type";
    
    public static final String FIRST_VISIT = "first_visit";
    /** 请求头 携带 TOKEN */
    public static final String TOKEN = "token";
    /** 请求头 携带 C-TOKEN */
    public static final String C_TOKEN = "c-token";
    /** 登录方式C-TOKEN */
    public static final String LOGIN_MODE_C_TOKEN = "LOGIN_MODE_C_TOKEN";
    /** 登录方式TOKEN */
    public static final String LOGIN_MODE_TOKEN = "LOGIN_MODE_TOKEN";
    /** 登录方式JSESSIONID */
    public static final String LOGIN_MODE_JSESSIONID = "LOGIN_MODE_JSESSIONID";
    /** 首页消息列表 */
    public static final String REMIND = CACHE_NAMESPACE + "REMIND";
    /** 密码登录 */
    public static final String LOGINTYPE_PASSWORD = "PASSWORD";
    /** 免密码登录 */
    public static final String LOGINTYPE_NOPASSWD = "NOPASSWD";
    /** regin.code */
    public static final String REGIN_CODE = CACHE_NAMESPACE + "REGIN_CODE";
    /** dynamic.web_code */
    public static final String DYNAMIC_WEB_CODE = CACHE_NAMESPACE + "DYNAMIC_WEB_CODE";
    /** dynamic.app_code */
    public static final String DYNAMIC_APP_CODE = CACHE_NAMESPACE + "DYNAMIC_APP_CODE";
    /** retrieve.code */
    public static final String RETRIEVE_CODE = CACHE_NAMESPACE + "RETRIEVE_CODE";
    /**
	 * 公司
	 */
	public final static String MESSAGE_TYPE_COMPANCY= "compancy";
	/**
	 * 部门
	 */
	public final static String MESSAGE_TYPE_DEPT= "dept";
	
	/**
	 * 人员
	 */
	public final static String MESSAGE_TYPE_PERSON= "person";
    /** 日志表状态 */
    public interface JOBSTATE {
        /**
         * 日志表状态，初始状态，插入
         */
        public static final String INIT_STATS = "I";
        /**
         * 日志表状态，成功
         */
        public static final String SUCCESS_STATS = "S";
        /**
         * 日志表状态，失败
         */
        public static final String ERROR_STATS = "E";
        /**
         * 日志表状态，未执行
         */
        public static final String UN_STATS = "N";
    }
    
    /**
     * 系统管理员
     */
    public final static int ADMINISTRATOR = 2;
    
	/**
	 * 【253云通讯】地址
	 */
	public final static String URL = "http://sms.253.com/msg/";
	
	/**
	 * 【253云通讯】账号
	 */
	public final static String UN = "N8554448";
	
	/**
	 * 【253云通讯】密码
	 */
	public final static String PW = "wJ8kyiMPmc5f76";
	
	/**
	 * 【253云通讯】msg	regin
	 */
	public final static String MSG_REGIN = "【"+ PropertiesUtil.getString("deehow.platform")+"】您正在注册验证，请在5分钟内按页面提示输入验证码，切勿将验证码泄露于他人。验证码:";
	/**
	 * 【253云通讯】msg	dynamic
	 */
	public final static String MSG_DYNAMIC = "【"+PropertiesUtil.getString("deehow.platform")+"】您正在获取动态密码，请在5分钟内按页面提示输入密码，切勿将密码泄露于他人。当前密码:";
	/**
	 * 【253云通讯】msg	retrieve
	 */
	public final static String MSG_RETRIEVE = "【"+PropertiesUtil.getString("deehow.platform")+"】您正在重置验证，请在5分钟内按页面提示输入验证码，切勿将验证码泄露于他人。验证码:";
	
	/**
	 * 【253云通讯】msg
	 */
	public final static String RESET_PWD = "【"+PropertiesUtil.getString("deehow.platform")+"】您好，你的密码重置为:";
	
	/**
	 * 【253云通讯】rd
	 */
	public final static String RD = "1";
	
	/**
	 * 【253云通讯】rd
	 */
	public final static String DBURL = "jdbc:mysql://deehow.vicp.cc:8612/";
	
    /** WebSocket 类型 */
    public interface WSTYPE {
    	/**
    	 *  WebSocket 类型  键
    	 */
    	public final static String WS_TYPE = "WS_TYPE";
    	/**
    	 * 	二维码登录长链接
    	 */
    	public final static String WS_TYPE_QRCODE = "WS_TYPE_QRCODE";
    }

}
