package com.deehow.core.support.jpush.model;

import com.deehow.core.base.BaseModel;


/**
 * <p>
 * 消息表
 * </p>
 *
 * @author wz
 * @since 2018-01-23
 */
@SuppressWarnings("serial")
public class CommMessageCollect extends BaseModel {

    /**
     * 租户id
     */
	private Long tenantId;
    /**
     * 创建人名字
     */
	private String createName;
    /**
     * 模块编号
     */
	private String modelName;
    /**
     * 模块编号
     */
	private String modelNo;
    /**
     * 1通知2模块
     */
	private Integer modelType;
    /**
     * 模块id
     */
	private Long modelId;
    /**
     * 模块链接
     */
	private String modelUrl;
    /**
     * 消息标题
     */
	private String mesTitle;
    /**
     * 消息详情
     */
	private String mesDesc;
	private String mesDesc1;
	private String mesDesc2;
	private String mesDesc3;
    /**
     * 0未读1已读
     */
	private Integer read;
    /**
     * 1公司2部门3职位4角色
     */
	private Integer type;
    /**
     * 类型名称
     */
	private String typeName;
	
	private Long typeId;
    /**
     * 1所有2移动3网页
     */
	private Integer mesType;
    /**
     * 1广播2点对点
     */
	private Integer mesSend;
    /**
     * 推送租户
     */
	private Long mesTenantId;
    /**
     * 推送ip
     */
	private String mesIp;
    /**
     * 接受人
     */
	private Integer userIs;
	
	private Long receiveUserId;
    /**
     * 接受人名字
     */
	private String reveiveUserName;

	private String receiveUserIds;
    /**
     * 接受人名字
     */
	private String reveiveUserNames;
	
	
	
	public String getReceiveUserIds() {
		return receiveUserIds;
	}

	public void setReceiveUserIds(String receiveUserIds) {
		this.receiveUserIds = receiveUserIds;
	}

	public String getReveiveUserNames() {
		return reveiveUserNames;
	}

	public void setReveiveUserNames(String reveiveUserNames) {
		this.reveiveUserNames = reveiveUserNames;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public Integer getModelType() {
		return modelType;
	}

	public void setModelType(Integer modelType) {
		this.modelType = modelType;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getModelUrl() {
		return modelUrl;
	}

	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
	}

	public String getMesTitle() {
		return mesTitle;
	}

	public void setMesTitle(String mesTitle) {
		this.mesTitle = mesTitle;
	}

	public String getMesDesc() {
		return mesDesc;
	}

	public void setMesDesc(String mesDesc) {
		this.mesDesc = mesDesc;
	}

	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getMesType() {
		return mesType;
	}

	public void setMesType(Integer mesType) {
		this.mesType = mesType;
	}

	public Integer getMesSend() {
		return mesSend;
	}

	public void setMesSend(Integer mesSend) {
		this.mesSend = mesSend;
	}

	public Long getMesTenantId() {
		return mesTenantId;
	}

	public void setMesTenantId(Long mesTenantId) {
		this.mesTenantId = mesTenantId;
	}

	public String getMesIp() {
		return mesIp;
	}

	public void setMesIp(String mesIp) {
		this.mesIp = mesIp;
	}

	public Long getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(Long receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public String getReveiveUserName() {
		return reveiveUserName;
	}

	public void setReveiveUserName(String reveiveUserName) {
		this.reveiveUserName = reveiveUserName;
	}

	public Integer getUserIs() {
		return userIs;
	}

	public void setUserIs(Integer userIs) {
		this.userIs = userIs;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getMesDesc1() {
		return mesDesc1;
	}

	public void setMesDesc1(String mesDesc1) {
		this.mesDesc1 = mesDesc1;
	}

	public String getMesDesc2() {
		return mesDesc2;
	}

	public void setMesDesc2(String mesDesc2) {
		this.mesDesc2 = mesDesc2;
	}

	public String getMesDesc3() {
		return mesDesc3;
	}

	public void setMesDesc3(String mesDesc3) {
		this.mesDesc3 = mesDesc3;
	}

}