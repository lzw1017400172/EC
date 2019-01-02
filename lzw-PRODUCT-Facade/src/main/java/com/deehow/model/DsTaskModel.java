package com.deehow.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * cms-協同设计任务-模型--关联
 * </p>
 *
 * @author liuzw
 * @since 2018-12-27
 */
@TableName("ds_task_model")
@SuppressWarnings("serial")
public class DsTaskModel extends BaseModel {

    /**
     * 任务id
     */
	@TableField("task_id")
	private Long taskId;
    /**
     * 模型id
     */
	@TableField("model_id")
	private Long modelId;


	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

}