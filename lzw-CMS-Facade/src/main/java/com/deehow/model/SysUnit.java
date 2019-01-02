package com.deehow.model;

import com.deehow.core.base.BaseModel;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.util.Date;

@TableName("sys_unit")
@SuppressWarnings("serial")
public class SysUnit extends BaseModel {
	
	@TableField("unit_code")
	private String unitCode;
	
	private String unitName;
	
	@TableField("principal_")
	private String principal;
	
	@TableField("phone_")
	private String phone;
	
	@TableField("address_")
	private String address;
	/**
	 * 公司主页
	 */
	@TableField("home_page")
	private String homePage;
	/**
	 * 年营业额
	 */
	private BigDecimal turnover;
	/**
	 * 员工数量
	 */
	@TableField("staff_num")
	private String staffNum;
	/**
	 * 邮政编码
	 */
	@TableField("postal_code")
	private String postalCode;
	/**
	 * 公司介绍
	 */
	@TableField("company_introduction")
	private String companyIntroduction;
	/**
	 * 排序号
	 */
	@TableField("sort_")
	private Integer sort;
	/**
	 * 临时租户申请表id
	 */
	@TableField("apply_id")
	private Long applyId;
	/**
	 * 行业
	 */
	private String business;
	
	@TableField(exist = false)
	private SysUnitDTO sysUnitDTO;
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public BigDecimal getTurnover() {
		return turnover;
	}

	public void setTurnover(BigDecimal turnover) {
		this.turnover = turnover;
	}

	public String getStaffNum() {
		return staffNum;
	}

	public void setStaffNum(String staffNum) {
		this.staffNum = staffNum;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCompanyIntroduction() {
		return companyIntroduction;
	}

	public void setCompanyIntroduction(String companyIntroduction) {
		this.companyIntroduction = companyIntroduction;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public SysUnitDTO getSysUnitDTO() {
		return sysUnitDTO;
	}

	public void setSysUnitDTO(SysUnitDTO sysUnitDTO) {
		this.sysUnitDTO = sysUnitDTO;
	}
}
