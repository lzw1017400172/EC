package com.deehow.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;
import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * cms-協同设计项目
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@TableName("ds_project")
@SuppressWarnings("serial")
public class DsProject extends BaseModel {

    /**
     * 项目名称
     */
	@TableField("name_")
	private String name;
    /**
     * 负责人
     */
	@TableField("leader_")
	private Long leader;
    /**
     * 完成进度0-100
     */
	@TableField("progress_bar")
	private Integer progressBar;
    /**
     * 项目类型id
     */
	@TableField("project_type_id")
	private Long projectTypeId;
	@TableField("start_time")
	private Date startTime;
	@TableField("end_time")
	private Date endTime;
    /**
     * 项目状态 1 待启动，2 执行中，3 待执行，4 已完成，5 已取消
     */
	@TableField("state_")
	private Integer state;

	@TableField(exist = false)
	private List<Long> participants;

	@TableField(exist = false)
	private List<String> participantsStr;

    @TableField(exist = false)
    private String leaderName;

    @TableField(exist = false)
    private String participantNames;

    @TableField(exist = false)
    private String projectTypeName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLeader() {
		return leader;
	}

	public void setLeader(Long leader) {
		this.leader = leader;
	}

	public Integer getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(Integer progressBar) {
		this.progressBar = progressBar;
	}

	public Long getProjectTypeId() {
		return projectTypeId;
	}

	public void setProjectTypeId(Long projectTypeId) {
		this.projectTypeId = projectTypeId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public List<Long> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Long> participants) {
		this.participants = participants;
	}

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getParticipantNames() {
        return participantNames;
    }

    public void setParticipantNames(String participantNames) {
        this.participantNames = participantNames;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

	public List<String> getParticipantsStr() {
		return participantsStr;
	}

	public void setParticipantsStr(List<String> participantsStr) {
		this.participantsStr = participantsStr;
	}
}