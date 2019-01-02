package com.deehow.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.deehow.core.base.BaseModel;
import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * cms-協同设计任务
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@TableName("ds_task")
@SuppressWarnings("serial")
public class DsTask extends BaseModel {

    public DsTask() {
    }

    public DsTask(Long id,String name, Long leader, Integer progressBar, Date startTime, Date endTime, Integer type, List<Long> participants) {
        super.setId(id);
        this.name = name;
        this.leader = leader;
        this.progressBar = progressBar;
        this.startTime = startTime;
        this.endTime = endTime;
		this.type = type;
        this.participants = participants;
    }

    /**
     * 任务名称
     */
	@TableField("name_")
	private String name;
    /**
     * 负责人
     */
	@TableField("leader_")
	private Long leader;
    /**
     * 完成情况0--100
     */
	@TableField("progress_bar")
	private Integer progressBar;
	@TableField("start_time")
	private Date startTime;
	@TableField("end_time")
	private Date endTime;
    /**
     * 任务类型； 1 普通任务；2 模型任务；
     */
	@TableField("type_")
	private Integer type;

	@TableField(exist = false)
	private String leaderName;

    @TableField(exist = false)
    private String participantNames;

    @TableField(exist = false)
    private List<Long> participants;

	@TableField(exist = false)
	private List<Long> models;
    
    @TableField(exist = false)
    private List<String> participantsStr;

    @TableField(exist = false)
    private Integer sort;

    @TableField(exist = false)
    private Long pstId;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

    public List<Long> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Long> participants) {
        this.participants = participants;
    }

	public List<String> getParticipantsStr() {
		return participantsStr;
	}

	public void setParticipantsStr(List<String> participantsStr) {
		this.participantsStr = participantsStr;
	}

	public List<Long> getModels() {
		return models;
	}

	public void setModels(List<Long> models) {
		this.models = models;
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