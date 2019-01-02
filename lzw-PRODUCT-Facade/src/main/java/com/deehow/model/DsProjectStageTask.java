package com.deehow.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * cms-協同设计阶段任务-任务关联
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@TableName("ds_project_stage_task")
@SuppressWarnings("serial")
public class DsProjectStageTask extends BaseModel {

    /**
     * 项目id
     */
	@TableField("project_id")
	private Long projectId;
    /**
     * 阶段id
     */
	@TableField("stage_id")
	private Long stageId;
    /**
     * 任务id
     */
	@TableField("task_id")
	private Long taskId;

	/**
	 * 排序
	 */
	@TableField("sort_")
	private Integer sort;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}