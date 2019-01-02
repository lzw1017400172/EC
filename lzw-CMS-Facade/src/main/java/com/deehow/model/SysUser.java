package com.deehow.model;

import java.util.Date;

import com.deehow.core.base.BaseModel;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @author ShenHuaJie
 * @since 2017-02-15
 */
@TableName("sys_user")
@SuppressWarnings("serial")
public class SysUser extends BaseModel {

	/**
	 * 登陆帐户
	 */
	@TableField("account_")
	private String account;
	/**
	 * 密码
	 */
	@TableField("password_")
	private String password;
	/**
	 * 用户类型(1普通用户2管理员3系统用户)
	 */
	@TableField("user_type")
	private Integer userType;
	/**
	 * 姓名
	 */
	@TableField("user_name")
	private String userName;
	/**
	 * 姓名拼音
	 */
	@TableField("name_pinyin")
	private String namePinyin;
	/**
	 * 性别(0:未知;1:男;2:女)
	 */
	@TableField("sex_")
	private Integer sex;
	/**
	 * 头像
	 */
	@TableField("avatar_")
	private String avatar;
	/**
	 * 电话
	 */
	@TableField("phone_")
	private String phone;
	/**
	 * 邮箱
	 */
	@TableField("email_")
	private String email;
	/**
	 * 身份证号码
	 */
	@TableField("id_card")
	private String idCard;
	/**
	 * 微信
	 */
	@TableField("wei_xin")
	private String weiXin;
	/**
	 * 微博
	 */
	@TableField("wei_bo")
	private String weiBo;
	/**
	 * QQ
	 */
	@TableField("qq_")
	private String qq;
	/**
	 * 出生日期
	 */
	@TableField("birth_day")
	private Date birthDay;
	
	/**
	 * 出生日期
	 */
	@TableField("entry_time")
	private Date entryTime;
	/**
	 * 职位编号
	 */
	@TableField("position_id")
	private Long positionId;
	/**
	 * 附属部门编号
	 */
	@TableField("other_dept_id")
	private Long otherDeptId;
	/**
	 * 部门编号
	 */
	@TableField("dept_id")
	private Long deptId;
	/**
	 * 主管人编号
	 */
	@TableField("user_superior")
	private Long userSuperior;
	/**
	 * 职位
	 */
	@TableField("position_")
	private String position;
	/**
	 * 详细地址
	 */
	@TableField("address_")
	private String address;
	/**
	 * 工号
	 */
	@TableField("staff_no")
	private String staffNo;
	
	/**
	 * 1 允许登陆，2 禁止登陆
	 */
	@TableField("login_")
	private Integer login;

	@TableField(exist = false)
	private String oldPassword;
	@TableField(exist = false)
	private String deptName;
	@TableField(exist = false)
	private String otherDeptName;
	@TableField(exist = false)
	private String userTypeText;
	@TableField(exist = false)
	private String permission;
	@TableField(exist = false)
	private Integer recordCount;
	@TableField(exist = false)
	private String unitName;
	@TableField(exist = false)
	private String industryName;
	/**
     * 租户id
     */
	@TableField("tenant_id")
	private Long tenantId;
	
	/**
	 * @return the tenantId
	 */
	public Long getTenantId() {
		return tenantId;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNamePinyin() {
		return namePinyin;
	}

	public void setNamePinyin(String namePinyin) {
		this.namePinyin = namePinyin;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getWeiXin() {
		return weiXin;
	}

	public void setWeiXin(String weiXin) {
		this.weiXin = weiXin;
	}

	public String getWeiBo() {
		return weiBo;
	}

	public void setWeiBo(String weiBo) {
		this.weiBo = weiBo;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getOtherDeptName() {
		return otherDeptName;
	}

	public void setOtherDeptName(String otherDeptName) {
		this.otherDeptName = otherDeptName;
	}

	public String getUserTypeText() {
		return userTypeText;
	}

	public void setUserTypeText(String userTypeText) {
		this.userTypeText = userTypeText;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getOtherDeptId() {
		return otherDeptId;
	}

	public void setOtherDeptId(Long otherDeptId) {
		this.otherDeptId = otherDeptId;
	}

	public Long getUserSuperior() {
		return userSuperior;
	}

	public void setUserSuperior(Long userSuperior) {
		this.userSuperior = userSuperior;
	}

	public Integer getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public Integer getLogin() {
		return login;
	}

	public void setLogin(Integer login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "SysUser [account=" + account + ", password=" + password
				+ ", userType=" + userType + ", userName=" + userName
				+ ", namePinyin=" + namePinyin + ", sex=" + sex + ", avatar="
				+ avatar + ", phone=" + phone + ", email=" + email
				+ ", idCard=" + idCard + ", weiXin=" + weiXin + ", weiBo="
				+ weiBo + ", qq=" + qq + ", birthDay=" + birthDay
				+ ", entryTime=" + entryTime + ", positionId=" + positionId
				+ ", otherDeptId=" + otherDeptId + ", deptId=" + deptId
				+ ", userSuperior=" + userSuperior + ", position=" + position
				+ ", address=" + address + ", staffNo=" + staffNo
				+ ", oldPassword=" + oldPassword + ", deptName=" + deptName
				+ ", userTypeText=" + userTypeText + ", permission="
				+ permission + ", recordCount=" + recordCount + ", unitName="
				+ unitName + ", tenantId=" + tenantId + "]";
	}

	

}