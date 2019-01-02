package com.deehow.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;


/**
 * <p>
 * cms-協同设计模型
 * </p>
 *
 * @author liuzw
 * @since 2018-12-26
 */
@TableName("ds_diagram_item")
@SuppressWarnings("serial")
public class DsDiagramItem extends BaseModel {

    /**
     * dsn名称
     */
	@TableField("name_")
	private String name;
    /**
     * 任务id
     */
	@TableField("task_id")
	private Long taskId;

	@TableField("file_path")
	private String filePath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}