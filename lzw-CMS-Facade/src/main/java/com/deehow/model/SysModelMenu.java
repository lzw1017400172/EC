package com.deehow.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 系统模块菜单表
 * </p>
 *
 * @author hzy
 * @since 2017-07-27
 */
@TableName("sys_model_menu")
@SuppressWarnings("serial")
public class SysModelMenu extends BaseModel {

    /**
     * 所属模块ID
     */
	@TableField("model_id")
	private Long modelId;
    /**
     * 所属模块名称
     */
	@TableField("model_name")
	private String modelName;
    /**
     * 菜单名称
     */
	@TableField("menu_name")
	private String menuName;
    /**
     * 菜单排序
     */
	@TableField("menu_order")
	private Integer menuOrder;
    /**
     * 菜单地址
     */
	@TableField("menu_url")
	private String menuUrl;
    /**
     * 父菜单ID
     */
	@TableField("parent_id")
	private Long parentId;
    /**
     * 是否可用Y=可用，N=不可用
     */
	private String enabled;
    /**
     * 创建时间
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 菜单图标
     */
	@TableField("menu_icon")
	private String menuIcon;
    /**
     * 菜单权限序列，以";"分割
     */
	@TableField("access_str")
	private String accessStr;
	/**
     * 菜单权限
     */
	@TableField("permission_")
	private String permission;
	

	@TableField(exist = false)
	private String checked;

	@TableField(exist = false)
	private String permstring;
	
	@TableField(exist = false)
	private List<String> permsArray;
	
	@TableField(exist = false)
	private List<SysModelMenu> children;
	
	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getAccessStr() {
		return accessStr;
	}

	public void setAccessStr(String accessStr) {
		this.accessStr = accessStr;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPermstring() {
		return permstring;
	}

	public void setPermstring(String permstring) {
		this.permstring = permstring;
	}

	public List<String> getPermsArray() {
		return permsArray;
	}

	public void setPermsArray(List<String> permsArray) {
		this.permsArray = permsArray;
	}

	public List<SysModelMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SysModelMenu> children) {
		this.children = children;
	}

}