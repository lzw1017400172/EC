package com.deehow.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 单位--注册申请表
 * </p>
 *
 * @author WangShengChao
 * @since 2018-07-18
 */
@TableName("unit_register_apply")
@SuppressWarnings("serial")
public class UnitRegisterApply extends BaseModel {

    /**
     * 单位名称
     */
	@TableField("unit_name")
	private String unitName;
    /**
     * 负责人id
     */
	@TableField("principal_id")
	private Long principalId;
    /**
     * 负责人
     */
	@TableField("principal_")
	private String principal;
    /**
     * 联系电话
     */
	@TableField("phone_")
	private String phone;
    /**
     * 用户名称
     */
	@TableField("user_name")
	private String userName;
    /**
     * 电子邮箱
     */
	@TableField("email_")
	private String email;
    /**
     * 密码
     */
	@TableField("password_")
	private String password;
    /**
     * 地址
     */
	@TableField("address_")
	private String address;
    /**
     * 0 初始态，刚刚申请 ; 1 申请通过，插入到sys_user； 2 申请打回
     */
	@TableField("status_")
	private Integer status;
    /**
     * 审核时间
     */
	@TableField("audit_time")
	private Date auditTime;


	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Long getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(Long principalId) {
		this.principalId = principalId;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

}