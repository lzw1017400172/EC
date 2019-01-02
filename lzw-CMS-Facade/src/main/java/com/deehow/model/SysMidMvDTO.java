package com.deehow.model;

import java.io.Serializable;
import java.util.Date;

public class SysMidMvDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 模块id
	 */
	private Long modelId;
	
	/**
	 * 到期时间
	 */
	private Date modelValidate;
	
	/**
	 * 0 手动禁用/1启用
	 */
	private int enable;

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Date getModelValidate() {
		return modelValidate;
	}

	public void setModelValidate(Date modelValidate) {
		this.modelValidate = modelValidate;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}
	
}
