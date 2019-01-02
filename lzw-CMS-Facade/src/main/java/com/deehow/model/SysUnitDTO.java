package com.deehow.model;

import java.io.Serializable;
import java.util.Date;

public class SysUnitDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 注册日期
	 */
	private Date registerTime;
	/**
	 * 审核日期
	 */
	private Date auditTime;
	/**
	 * 审核意见
	 */
	private String AuditOpinion;
	/**
	 * 审核人
	 */
	private String auditPerson;
	/**
	 * 用户名
	 */
	private String userName;

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditOpinion() {
		return AuditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		AuditOpinion = auditOpinion;
	}

	public String getAuditPerson() {
		return auditPerson;
	}

	public void setAuditPerson(String auditPerson) {
		this.auditPerson = auditPerson;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
