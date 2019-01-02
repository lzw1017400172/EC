package com.deehow.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;
import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * cms-協同设计阶段
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@TableName("ds_stage")
@SuppressWarnings("serial")
public class DsStage extends BaseModel {

	public DsStage() {
	}

	public DsStage(Long id,String name, Integer progressBar, Long leader, Long projectId) {
	    super.setId(id);
		this.name = name;
		this.progressBar = progressBar;
		this.leader = leader;
		this.projectId = projectId;
	}

	/**
     * 阶段名称
     */
	@TableField("name_")
	private String name;
    /**
     * 完成进度0-100
     */
	@TableField("progress_bar")
	private Integer progressBar;
    /**
     * 负责人
     */
	@TableField("leader_")
	private Long leader;

	@TableField(exist = false)
	private Long projectId;

    @TableField(exist = false)
    private String leaderName;

	@TableField(exist = false)
	private Integer sort;

    @TableField(exist = false)
    private Long pstId;

    @TableField(exist = false)
    private List<DsTask> taskList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(Integer progressBar) {
		this.progressBar = progressBar;
	}

	public Long getLeader() {
		return leader;
	}

	public void setLeader(Long leader) {
		this.leader = leader;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

    public List<DsTask> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<DsTask> taskList) {
        this.taskList = taskList;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

    public Long getPstId() {
        return pstId;
    }

    public void setPstId(Long pstId) {
        this.pstId = pstId;
    }
}