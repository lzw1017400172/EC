package com.deehow.model;

import com.deehow.core.base.BaseModel;

import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("sys_dept")
@SuppressWarnings("serial")
public class SysDept extends BaseModel {
	private String deptName;
	private Long managerId;
	private Long parentId;
	
	@TableField("leading_official")
	private Long leadingOfficial;
	
	private Integer sortNo;
	
	@TableField("leaf_")
	private Integer leaf;
	
	@TableField("icon_")
	private String icon;
	
	@TableField(exist = false)
	private String parentName;
	
	@TableField(exist = false)
	private Integer hasChild;
	/**
     * 租户id
     */
	@TableField("tenant_id")
	private Long tenantId;
	
	@TableField(exist = false)
	private Integer depth;
	
	@TableField(exist = false)
	private List<SysDept> children;
	/**
	 * @return the tenantId
	 */
	public Long getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getHasChild() {
		return hasChild;
	}

	public void setHasChild(Integer hasChild) {
		this.hasChild = hasChild;
	}

	/**
	 * @return the value of sys_dept.dept_name
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName
	 *            the value for sys_dept.dept_name
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName == null ? null : deptName.trim();
	}

	/**
	 * @return the value of sys_dept.parent_id
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the value for sys_dept.parent_id
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	/**
	 * @return the value of sys_dept.sort_no
	 */
	public Integer getSortNo() {
		return sortNo;
	}

	/**
	 * @param sortNo
	 *            the value for sys_dept.sort_no
	 */
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	/**
	 * @return the value of sys_dept.leaf_
	 */
	public Integer getLeaf() {
		return leaf;
	}

	/**
	 * @param leaf
	 *            the value for sys_dept.leaf_
	 */
	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}


	public List<SysDept> getChildren() {
		return children;
	}

	public void setChildren(List<SysDept> children) {
		this.children = children;
	}

	public Long getLeadingOfficial() {
		return leadingOfficial;
	}

	public void setLeadingOfficial(Long leadingOfficial) {
		this.leadingOfficial = leadingOfficial;
	}
	
	
}
