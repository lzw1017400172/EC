package com.deehow.model;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;


/**
 * <p>
 * 系统模块表
 * </p>
 *
 * @author hzy
 * @since 2017-07-27
 */
@TableName("sys_model")
public class SysModel extends BaseModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 模块编号
     */
	@TableField("model_no")
	private String modelNo;
    /**
     * 模块配图
     */
	@TableField("model_logo")
	private String modelLogo;
    /**
     * 模块名称
     */
	@TableField("model_name")
	private String modelName;
    /**
     * 模块路径
     */
	@TableField("model_url")
	private String modelUrl;
	
	@TableField("model_desc")
	private String modelDesc;
	
	@TableField("desc")
	private String desc;
	
	@TableField("model_lable")
	private String modelLable;
	
	@TableField("model_info")
	private String modelInfo;
    /**
     * 是否可用Y=可用，N=不可用
     */
	private String enabled;
    /**
     * 是否为基础应用：0:为否 1：是 2为全局
     */
	@TableField("is_basic")
	private Integer isBasic;
    /**
     * WEB/PAD/APP
     */
	@TableField("type")
	private String type;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 模块价格
     */
	private BigDecimal price;
    /**
     * 发布日期
     */
	@TableField("pub_time")
	private Date pubTime;
	
	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getModelLogo() {
		return modelLogo;
	}

	public void setModelLogo(String modelLogo) {
		this.modelLogo = modelLogo;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelUrl() {
		return modelUrl;
	}

	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public Integer getIsBasic() {
		return isBasic;
	}

	public void setIsBasic(Integer isBasic) {
		this.isBasic = isBasic;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModelDesc() {
		return modelDesc;
	}

	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}

	public String getModelLable() {
		return modelLable;
	}

	public void setModelLable(String modelLable) {
		this.modelLable = modelLable;
	}

	public String getModelInfo() {
		return modelInfo;
	}

	public void setModelInfo(String modelInfo) {
		this.modelInfo = modelInfo;
	}

}