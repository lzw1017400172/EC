package com.deehow.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * cms-協同设计任务组人员
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@TableName("ds_task_user")
@SuppressWarnings("serial")
public class DsTaskUser extends BaseModel {

    /**
     * 项目id
     */
	@TableField("task_id")
	private Long taskId;
	@TableField("user_id")
	private Long userId;


	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}