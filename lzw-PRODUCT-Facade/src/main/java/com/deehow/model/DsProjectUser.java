package com.deehow.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * cms-協同设计项目组人员
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@TableName("ds_project_user")
@SuppressWarnings("serial")
public class DsProjectUser extends BaseModel {

    /**
     * 项目id
     */
	@TableField("project_id")
	private Long projectId;
	@TableField("user_id")
	private Long userId;


	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}