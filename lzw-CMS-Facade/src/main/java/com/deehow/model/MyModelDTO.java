package com.deehow.model;

import java.io.Serializable;
import java.util.Date;


public class MyModelDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    /**
     * 模块id
     */
	private Long modelId;
	  /**
     * 模块-租户 id
     */
	private Long tenantModelId;
    /**
     * 模块名称
     */
	private String modelName;
    /**
     * 是否可用Y=可用，N=不可用
     */
	private String enabled;
    /**
     * WEB/PAD/APP/PC
     */
	private String type;
    /**
     * 开始日期
     */
	private Date startTime;
    /**
     * 到期日期
     */
	private Date endTime;
    /**
     * 支付方式
     */
	private String payment;
	   /**
     * 添加人
     */
	private String createName;
	
	   /**
	  * 图标
	  */
	private String icon;
	
	private String modelLable;
	
	private String modelDesc;
	
	private String remark;
	
	private String modelInfo;
	private String modelUrl;
	
	
	public MyModelDTO(){}
	
	
	public MyModelDTO(Long modelId,String modelName, String type) {
		super();
		this.modelId = modelId;
		this.modelName = modelName;
		this.type = type;
	}



	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Long getModelId() {
		return modelId;
	}
	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}
	public Long getTenantModelId() {
		return tenantModelId;
	}
	public void setTenantModelId(Long tenantModelId) {
		this.tenantModelId = tenantModelId;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}


	public String getModelLable() {
		return modelLable;
	}


	public void setModelLable(String modelLable) {
		this.modelLable = modelLable;
	}


	public String getModelDesc() {
		return modelDesc;
	}


	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getModelInfo() {
		return modelInfo;
	}


	public void setModelInfo(String modelInfo) {
		this.modelInfo = modelInfo;
	}


	public String getModelUrl() {
		return modelUrl;
	}


	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
	}
	

}