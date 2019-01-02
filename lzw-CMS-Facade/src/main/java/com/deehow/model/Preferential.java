package com.deehow.model;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;


/**
 * <p>
 * 优惠方案表
 * </p>
 *
 * @author renzh
 * @since 2018-07-18
 */
@TableName("preferential")
@SuppressWarnings("serial")
public class Preferential extends BaseModel {

    /**
     * 金额
     */
	@TableField("price_")
	private BigDecimal price;
    /**
     * 有效期
     */
	@TableField("validity_time")
	private String validityTime;
    /**
     * 优惠方案标题
     */
	@TableField("preferential_title")
	private String preferentialTitle;
    /**
     * 优惠方案内容
     */
	@TableField("preferential_content")
	private String preferentialContent;
    /**
     * 模块id 多个用逗号分隔
     */
	@TableField("model_id")
	private String modelId;
	/**
     * 模块集合
     */
	@TableField(exist = false)
	private List<SysModel> modelList;
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getValidityTime() {
		return validityTime;
	}

	public void setValidityTime(String validityTime) {
		this.validityTime = validityTime;
	}

	public String getPreferentialTitle() {
		return preferentialTitle;
	}

	public void setPreferentialTitle(String preferentialTitle) {
		this.preferentialTitle = preferentialTitle;
	}

	public String getPreferentialContent() {
		return preferentialContent;
	}

	public void setPreferentialContent(String preferentialContent) {
		this.preferentialContent = preferentialContent;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public List<SysModel> getModelList() {
		return modelList;
	}

	public void setModelList(List<SysModel> modelList) {
		this.modelList = modelList;
	}
	
}