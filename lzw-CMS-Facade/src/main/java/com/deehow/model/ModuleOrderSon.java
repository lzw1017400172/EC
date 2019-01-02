package com.deehow.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 模块订单子表
 * </p>
 *
 * @author renzh
 * @since 2018-07-18
 */
@TableName("module_order_son")
@SuppressWarnings("serial")
public class ModuleOrderSon extends BaseModel {

    /**
     * 模块名称
     */
	@TableField("model_name")
	private String modelName;
    /**
     * 模块id
     */
	@TableField("model_id")
	private Long modelId;
    /**
     * 数量
     */
	@TableField("number_")
	private Integer number;
    /**
     * 单价
     */
	@TableField("price_")
	private BigDecimal price;
    /**
     * 折扣
     */
	@TableField("rebate_")
	private Float rebate;
    /**
     * 付款方式
     */
	@TableField("payment_method")
	private String paymentMethod;
    /**
     * 最终价格
     */
	@TableField("final_price")
	private BigDecimal finalPrice;
    /**
     * 到期时间   yyyy-MM-dd HH:mm:ss
     */
	@TableField("expiry_time")
	private String expiryTime;
    /**
     * 订单主表id
     */
	@TableField("order_id")
	private Long orderId;


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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Float getRebate() {
		return rebate;
	}

	public void setRebate(Float rebate) {
		this.rebate = rebate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}