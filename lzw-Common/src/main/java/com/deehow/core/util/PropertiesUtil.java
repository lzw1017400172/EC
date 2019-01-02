package com.deehow.core.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Parsing The Configuration File
 * @author ShenHuaJie
 * @version 2016年7月30日 下午11:41:53
 */
public final class PropertiesUtil extends PropertyPlaceholderConfigurer {
    private static final byte[] KEY = {9, -1, 0, 5, 39, 8, 6, 19};
    private static Map<String, String> ctxPropertiesMap;
    private List<String> decryptProperties;

    @Override
    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);
        ctxPropertiesMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            if (decryptProperties != null && decryptProperties.contains(keyStr)) {
                value = SecurityUtil.decryptDes(value, KEY);
                props.setProperty(keyStr, value);
                //TODO 注釋
//                if(keyStr.contains("password")){
//                    props.setProperty(keyStr, "root");
//                }
            }
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    /**
     * @param decryptPropertiesMap the decryptPropertiesMap to set
     */
    public void setDecryptProperties(List<String> decryptProperties) {
        this.decryptProperties = decryptProperties;
    }

    /**
     * Get a value based on key , if key does not exist , null is returned
     * 
     * @param key
     * @return
     */
    public static String getString(String key) {
        try {
            return ctxPropertiesMap.get(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * 根据key获取值
     * 
     * @param key
     * @return
     */
    public static int getInt(String key) {
        return Integer.parseInt(ctxPropertiesMap.get(key));
    }

    /**
     * 根据key获取值
     * 
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(String key, int defaultValue) {
        String value = ctxPropertiesMap.get(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    /**
     * 根据key获取值
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = ctxPropertiesMap.get(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return new Boolean(value);
    }

    public static void main(String[] args) {
//    	for (Integer i = 0; i < ctxPropertiesMap.keySet().size(); i++) {
//    		System.out.println(ctxPropertiesMap.get(i.toString()));
//		}
//     String encrypt = SecurityUtil.encryptDes("buzhidao", KEY);
    	String encrypt = SecurityUtil.encryptDes("deehow.vicp.cc", KEY);
//        String encrypt = SecurityUtil.encryptDes("root", KEY);
        System.out.println(encrypt);
        System.out.println(SecurityUtil.decryptDes(encrypt, KEY));
    }
    
	/**
	 * 实体类转map
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map ConvertObjToMap(Object obj) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (obj == null)
			return null;
		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				try {
					Field f = obj.getClass().getDeclaredField(fields[i].getName());
					f.setAccessible(true);
					Object o = f.get(obj);
					reMap.put(fields[i].getName(), o);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reMap;
	}
}
