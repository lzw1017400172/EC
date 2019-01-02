package com.deehow.core.support.jpush.model;

import java.io.Serializable;
import java.util.Date;

public class MessageEntry implements Serializable {

	private static final long serialVersionUID = 1L;
	/*token*/
	private String token;
	/*模块类型*/
	private String msgModel;
	/*模块类型id*/
	private Long modelId;
	/*模块类型id*/
	private String modelState;
	/*发送人*/
	private Long sendUserId;
	/*发送人名字*/
	private String sendUserName;
	/*接受人*/
	private String receiveUserId;
	/*接受部门*/
	private String receiveDeptId;
	/*租户*/
	private Long tenantId;
	/*消息标题*/
	private String msgTitle;
	/*发送时间*/
	private Date msgSendTime;
	/*消息内容*/
	private String msgContent;
	/*消息类型*/
	private String msgType;
	/*ip*/
	private String msgIp;
	
	
	public String getModelState() {
		return modelState;
	}
	public void setModelState(String modelState) {
		this.modelState = modelState;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMsgModel() {
		return msgModel;
	}
	public void setMsgModel(String msgModel) {
		this.msgModel = msgModel;
	}
	public Long getModelId() {
		return modelId;
	}
	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}
	public Long getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(Long sendUserId) {
		this.sendUserId = sendUserId;
	}
	public String getSendUserName() {
		return sendUserName;
	}
	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	public String getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	public String getReceiveDeptId() {
		return receiveDeptId;
	}
	public void setReceiveDeptId(String receiveDeptId) {
		this.receiveDeptId = receiveDeptId;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public Date getMsgSendTime() {
		return msgSendTime;
	}
	public void setMsgSendTime(Date msgSendTime) {
		this.msgSendTime = msgSendTime;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgIp() {
		return msgIp;
	}
	public void setMsgIp(String msgIp) {
		this.msgIp = msgIp;
	}

	
}
