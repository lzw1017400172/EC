package com.deehow.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 租户模块表
 * </p>
 *
 * @author hzy
 * @since 2017-07-27
 */
@TableName("sys_tenant_model")
@SuppressWarnings("serial")
public class SysTenantModel extends BaseModel {

    /**
     * 租户名称
     */
	@TableField("unit_name")
	private String unitName;
    /**
     * 租户id
     */
	@TableField("tenant_id")
	private Long tenantId;
    /**
     * 模块名称
     */
	@TableField("model_name")
	private String modelName;
    /**
     * 支付方式
     */
	@TableField("payment_")
	private String payment;
    /**
     * 模块id
     */
	@TableField("model_id")
	private Long modelId;
    /**
     * 应用有效期
     */
	@TableField("model_validate")
	private Date modelValidate;
	private int buy;
	
    /**
     * 创建人
     */
	@TableField("create_name")
	private String createName;
	

	public int getBuy() {
		return buy;
	}

	public void setBuy(int buy) {
		this.buy = buy;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

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

}