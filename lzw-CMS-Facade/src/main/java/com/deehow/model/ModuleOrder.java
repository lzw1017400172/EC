package com.deehow.model;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 模块订单表---
 * </p>
 *
 * @author renzh
 * @since 2018-07-18
 */
@TableName("module_order")
@SuppressWarnings("serial")
public class ModuleOrder extends BaseModel {

    /**
     * 租户id
     */
	@TableField("unit_id")
	private Long unitId;
    /**
     * 单位名称
     */
	@TableField("unit_name")
	private String unitName;
    /**
     * 单位编号
     */
	@TableField("unit_code")
	private String unitCode;
    /**
     * 订单编号
     */
	@TableField("order_no")
	private String orderNo;
    /**
     * 金额
     */
	@TableField("sum_")
	private BigDecimal sum;
	/**
     * 有效期
     */
	@TableField("validity_time")
	private String validityTime;
	/**
     * 使用人数
     */
	@TableField("person_num")
	private Long personNum;
	/**
     *填写订单时预留的接收短信的手机号（运维人员添加的订单不填）
     */
	@TableField("phone")
	private String phone;
    /**
     * 操作人
     */
	@TableField("operating_personnel")
	private String operatingPersonnel;
	 /**
     * 优惠方案id
     */
	@TableField(exist = false)
	private Long preferentialId;
	/**
     * 到期时间   yyyy-MM-dd HH:mm:ss
     */
	@TableField(exist = false)
	private String expiryTime;
	 /**
     * 模块id集合，通过模块添加订单时用到
     */
	@TableField(exist = false)
	private List<Long> modelIdList;
	 /**
     * 订单子表集合
     */
	@TableField(exist = false)
	private List<ModuleOrderSon> orderSonList;
	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
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

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	public String getOperatingPersonnel() {
		return operatingPersonnel;
	}

	public void setOperatingPersonnel(String operatingPersonnel) {
		this.operatingPersonnel = operatingPersonnel;
	}

	public Long getPreferentialId() {
		return preferentialId;
	}

	public void setPreferentialId(Long preferentialId) {
		this.preferentialId = preferentialId;
	}

	public List<ModuleOrderSon> getOrderSonList() {
		return orderSonList;
	}

	public void setOrderSonList(List<ModuleOrderSon> orderSonList) {
		this.orderSonList = orderSonList;
	}

	public String getValidityTime() {
		return validityTime;
	}

	public void setValidityTime(String validityTime) {
		this.validityTime = validityTime;
	}

	public Long getPersonNum() {
		return personNum;
	}

	public void setPersonNum(Long personNum) {
		this.personNum = personNum;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}

	public List<Long> getModelIdList() {
		return modelIdList;
	}

	public void setModelIdList(List<Long> modelIdList) {
		this.modelIdList = modelIdList;
	}

	
}