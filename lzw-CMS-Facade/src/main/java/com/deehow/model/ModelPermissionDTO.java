package com.deehow.model;

import java.io.Serializable;

public class ModelPermissionDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String permission;
	
	private Long modelId;

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	@Override
	public String toString() {
		return "ModelPermissionDTO [permission=" + permission + ", modelId=" + modelId + "]";
	}
	
}
