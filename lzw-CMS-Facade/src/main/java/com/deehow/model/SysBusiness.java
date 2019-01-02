package com.deehow.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 行业表
 * </p>
 *
 * @author wz
 * @since 2017-11-02
 */
@TableName("sys_business")
@SuppressWarnings("serial")
public class SysBusiness extends BaseModel {

    /**
     * 租户id
     */
	@TableField("tenant_id")
	private Long tenantId;
    /**
     * 名称
     */
	private String name;
    /**
     * 排序号
     */
	private Integer sort;


	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}