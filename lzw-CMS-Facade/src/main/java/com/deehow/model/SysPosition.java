package com.deehow.model;

import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;

/**
 * <p>
 * 职位表
 * </p>
 *
 * @author wz
 * @since 2017-07-08
 */
@TableName("sys_position")
public class SysPosition extends BaseModel {

    private static final long serialVersionUID = 1L;

	/**
	 * 租户id
	 */
	@TableField("tenant_id")
	private Long tenantId;

	/**
	 * 上级职位id
	 */
	private Long parentId;
    /**
     * 职位名称
     */
	@TableField("position_title")
	private String positionTitle;
    /**
     * 职位编号
     */
	@TableField("position_no")
	private String positionNo;
    /**
     * 部门id
     */
	@TableField("dept_id")
	private Long deptId;
	
	@TableField(exist = false)
	private List<SysUser> userList;
	
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

	public String getPositionTitle() {
		return positionTitle;
	}

	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	public String getPositionNo() {
		return positionNo;
	}

	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public List<SysUser> getUserList() {
		return userList;
	}

	public void setUserList(List<SysUser> userList) {
		this.userList = userList;
	}

}
