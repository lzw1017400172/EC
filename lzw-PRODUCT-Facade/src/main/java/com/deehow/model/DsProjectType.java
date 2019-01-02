package com.deehow.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * cms-協同设计项目类型
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@TableName("ds_project_type")
@SuppressWarnings("serial")
public class DsProjectType extends BaseModel {

    /**
     * 类型名称
     */
	@TableField("name_")
	private String name;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}