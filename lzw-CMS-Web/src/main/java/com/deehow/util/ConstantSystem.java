package com.deehow.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deehow.model.SysDept;
import com.deehow.model.SysModelMenu;

public class ConstantSystem {
	
	/**
	 * 0
	 */
	public final static int PUBLIC_ZERO = 0;
	
	/**
	 * 1
	 */
	public final static int PUBLIC_ONE = 1;

	
	public static JSONArray treeMenuList(JSONArray menuList, String parentId) {
        JSONArray childMenu = new JSONArray(); 
        for (Object object : menuList) { 
            JSONObject jsonMenu = JSONObject.parseObject(JSONObject.toJSON(object).toString());
            String menuId = jsonMenu.getString("id"); 
            jsonMenu.put("id", menuId);
            String pid = jsonMenu.getString("parentId");
            jsonMenu.put("parentId", pid);
            if(jsonMenu.containsKey("deptId")){
            	String deptId = jsonMenu.getString("deptId"); 
            	jsonMenu.put("deptId", deptId);
            }
            if(jsonMenu.containsKey("managerId")){
            	String managerId = jsonMenu.getString("managerId"); 
            	jsonMenu.put("managerId", managerId);
            }
            if(jsonMenu.containsKey("otherDeptId")){
            	String otherDeptId = jsonMenu.getString("otherDeptId"); 
            	jsonMenu.put("otherDeptId", otherDeptId);
            }
            if(jsonMenu.containsKey("positionId")){
            	String positionId = jsonMenu.getString("positionId"); 
            	jsonMenu.put("positionId", positionId);
            }
            if(!StringUtils.isNotBlank(pid)){
            	pid = "0";
            }
            if (parentId.equals(pid)) { 
                JSONArray c_node = treeMenuList(menuList, menuId); 
                jsonMenu.put("children", c_node); 
                childMenu.add(jsonMenu); 
            } 
        } 
        return childMenu; 
    }
	
	public static List<SysModelMenu> treeMenuList(List<SysModelMenu> menuList, Long parentId) {
		List<SysModelMenu> childMenu = new ArrayList(); 
        for (SysModelMenu object : menuList) { 
            if (object.getParentId().longValue() == parentId.longValue()) { 
            	List<SysModelMenu> c_node = treeMenuList(menuList, object.getId()); 
            	object.setChildren(c_node);
                childMenu.add(object); 
            } 
        } 
        return childMenu; 
    }
	
	public static List<SysDept> treeDeptList(List<SysDept> deptList, Long parentId) {
		List<SysDept> childMenu = new ArrayList(); 
        for (SysDept object : deptList) { 
            if (object.getParentId().longValue() == parentId.longValue()) { 
            	List<SysDept> c_node = treeDeptList(deptList, object.getId()); 
            	object.setChildren(c_node);
                childMenu.add(object); 
            } 
        } 
        return childMenu; 
    }
}
	
	
