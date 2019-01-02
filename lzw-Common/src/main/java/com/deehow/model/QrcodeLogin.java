package com.deehow.model;

import java.io.Serializable;

public class QrcodeLogin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5285951068010948131L;

	private String account;
	private String password;
	private Long tenantId;
	private Integer status;
	private String uid;//用来接收页面参数
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
