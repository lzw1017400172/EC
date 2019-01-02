package com.deehow.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;

/**
 * 根据属性 值 获取TableField的值（数据库字段名）
 * @author 四叶草
 *
 * @param <T>
 */
public class AnnotationUtil<T> {
	
	private Class<T> entity;
	
	public String getField(String field){  
		String name=field;
        entity = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];  
        if(this.entity!=null){
        	Field f;
			try {
				f = entity.getDeclaredField(field);
				if(f!=null){
					TableField meta = f.getAnnotation(TableField.class);  
					if(meta!=null){
						name = meta.value();
					}
				}
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }  
        return name;  
          
    }   
}
