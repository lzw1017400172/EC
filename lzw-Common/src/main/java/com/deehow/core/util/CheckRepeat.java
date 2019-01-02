package com.deehow.core.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * 查重类
 * @author zhuang
 *
 * @param <T>
 */
public class CheckRepeat<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 返回是否重复
	 * @param entity 类型
	 * @param field 字段
	 * @return
	 */
	public  boolean checked(List<T> entity, String field) {
		boolean checked=false;
	    PropertyDescriptor pd;
	    if(entity!=null&&entity.size()>0){
	    	Set<Object> mySet=new HashSet<Object>();
	    	try {
	    		if(field!=null&&!field.equals("")){
	    			pd = new PropertyDescriptor(field, entity.get(0).getClass());
	    			Method getMethod = pd.getReadMethod();//获得get方法  
	    			if (pd != null) { 
	    				for (int i = 0; i < entity.size(); i++) {
	    					Object o = getMethod.invoke(entity.get(i));//执行get方法返回一个Object  
	    					if (!mySet.add(o)) {  
	    						return checked;
	    					}
	    				}
	    			}
	    			
	    		}else{
	    			for (int i = 0; i < entity.size(); i++) {
    					if (!mySet.add(entity.get(i))) {  
    						return checked;
    					}
    				}
	    		}
	    		if(entity.size()==mySet.size()){
    				checked=true;
    			}
	    	} catch (IntrospectionException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	} catch (IllegalAccessException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	} catch (IllegalArgumentException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	} catch (InvocationTargetException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    }
		return checked;
	}
	/**
	 * 返回重复位置
	 * @param entity 类型
	 * @param field 字段
	 * @return
	 */
	public  Integer checkedIndex(List<T> entity, String field) {
		int checked=0;
	    PropertyDescriptor pd;
	    if(entity!=null&&entity.size()>0){
	    	Set<Object> mySet=new HashSet<Object>();
	    	try {
	    		if(field!=null&&!field.equals("")){
	    			pd = new PropertyDescriptor(field, entity.get(0).getClass());
	    			Method getMethod = pd.getReadMethod();//获得get方法  
	    			if (pd != null) { 
	    				for (int i = 0; i < entity.size(); i++) {
	    					Object o = getMethod.invoke(entity.get(i));//执行get方法返回一个Object  
	    					if (!mySet.add(o)) {  
	    						return i;
	    					}
	    				}
	    			}
	    			
	    		}else{
	    			for (int i = 0; i < entity.size(); i++) {
    					if (!mySet.add(entity.get(i))) {  
    						return i;
    					}
    				}
	    		}
	    	} catch (IntrospectionException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	} catch (IllegalAccessException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	} catch (IllegalArgumentException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	} catch (InvocationTargetException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    }
		return checked;
	}
	/**
	 * 返回去重后的list<T>
	 * @param entity 类型
	 * @param field 字段
	 * @return
	 */
	public List<T> checkList(List<T> entity, String field) {
		List<T> list=new ArrayList<>();
	    PropertyDescriptor pd;
	    if(entity!=null&&entity.size()>0){
	    	Set<Object> mySet=new HashSet<Object>();
	    	try {
	    		if(field!=null&&!field.equals("")){
	    			pd = new PropertyDescriptor(field, entity.get(0).getClass());
	    			Method getMethod = pd.getReadMethod();//获得get方法  
	    			if (pd != null) { 
	    				for (int i = 0; i < entity.size(); i++) {
	    					Object o = getMethod.invoke(entity.get(i));//执行get方法返回一个Object  
	    					if (mySet.add(o)) {  
	    						list.add(entity.get(i));
	    					}
	    				}
	    			}
	    			
	    		}else{
	    			for (int i = 0; i < entity.size(); i++) {
    					if (mySet.add(entity.get(i))) {  
    						list.add(entity.get(i));
    					}
    				}
	    		}
	    	} catch (IntrospectionException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	} catch (IllegalAccessException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	} catch (IllegalArgumentException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	} catch (InvocationTargetException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    }
		return list;
	}
}
