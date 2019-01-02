package com.deehow.model;

import java.io.Serializable;

public class UserBelongedToUnit implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String unitName;
	
	private String unitCode;
	
	private Long userId;

	public UserBelongedToUnit(){}
	
	public UserBelongedToUnit(String unitName,String unitCode){
		this.unitName = unitName;
		this.unitCode = unitCode;
	}
	
	public UserBelongedToUnit(String unitName,String unitCode,Long userId){
		this.unitName = unitName;
		this.unitCode = unitCode;
		this.userId = userId;
	}
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
