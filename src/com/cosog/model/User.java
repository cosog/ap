package com.cosog.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;


/**
 *  <p>描述：用户信息 实体类  tbl_user</p>
 *  
 * @author gao  2014-06-10
 *
 */
@Entity
@Table(name = "tbl_user")
public class User implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String orgName;
	private String userId;
	private String userInEmail;
	private String userName;
	private Integer userNo;
	private Integer userOrgid;
	private String userPhone;
	private String userPwd;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date userRegtime;
	private String picUrl;
	private String pageSize;
	private Integer userType;
	private String userTypeName;
	private Integer userQuickLogin;
	private Integer userEnable;
	private Integer receiveSMS;
	private Integer receiveMail;
	private Integer language;
	private String languageName;
	private String userOrgids;
	private String userOrgNames;
	private String userParentOrgids;
	private String allOrgPatentNodeIds;//组织表中所有父节点
	private String allModParentNodeIds;//模块表中所有父节点
	private String syncOrAsync;
	private String defaultComboxSize;
	private String defaultGraghSize;
	
	private String loginIp;
	
	private String loginTime;
	
	private Integer roleLevel;
	private Integer roleShowLevel;
	private Integer roleVideoKeyEdit;
	private Integer roleLanguageEdit;
	
	private String deviceTypeIds;

	/**
	 * 组织节点orgCode集合
	 */
	private String orgtreeid;
	/**
	 * 组织节点ID集合
	 */
	private String userorgids;
	
	private String moduleList;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue
	@Column(name = "user_No", nullable = false, insertable = true, updatable = true, length = 32)
	public Integer getUserNo() {
		return this.userNo;
	}

	@Transient
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Transient
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	@Transient
	public String getOrgName() {
		return orgName;
	}

	@Column(name = "USER_ID", unique = true, nullable = false, length = 20)
	public String getUserId() {
		return this.userId;
	}

	@Column(name = "USER_IN_EMAIL", length = 40)
	public String getUserInEmail() {
		return this.userInEmail;
	}

	@Column(name = "USER_NAME", nullable = false, length = 40)
	public String getUserName() {
		return this.userName;
	}

	@Column(name = "USER_ORGID", nullable = false, precision = 22, scale = 0)
	public Integer getUserOrgid() {
		return this.userOrgid;
	}

	@Column(name = "USER_PHONE", length = 40)
	public String getUserPhone() {
		return this.userPhone;
	}

	@Column(name = "USER_PWD", length = 20)
	public String getUserPwd() {
		return this.userPwd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "USER_REGTIME", length = 7)
	public Date getUserRegtime() {
		return this.userRegtime;
	}

	@Column(name = "USER_TYPE", precision = 38, scale = 0)
	public Integer getUserType() {
		return this.userType;
	}
	
	@Column(name = "USER_QUICKLOGIN", precision = 38, scale = 0)
	public Integer getUserQuickLogin() {
		return this.userQuickLogin;
	}
	
	public void setUserQuickLogin(Integer userQuickLogin) {
		this.userQuickLogin = userQuickLogin;
	}
	


	@Column(name = "USER_ENABLE", precision = 38, scale = 0)
	public Integer getUserEnable() {
		return userEnable;
	}

	public void setUserEnable(Integer userEnable) {
		this.userEnable = userEnable;
	}

	@Column(name = "USER_RECEIVESMS", precision = 38, scale = 0)
	public Integer getReceiveSMS() {
		return receiveSMS;
	}

	public void setReceiveSMS(Integer receiveSMS) {
		this.receiveSMS = receiveSMS;
	}

	@Column(name = "USER_RECEIVEMAIL", precision = 38, scale = 0)
	public Integer getReceiveMail() {
		return receiveMail;
	}

	public void setReceiveMail(Integer receiveMail) {
		this.receiveMail = receiveMail;
	}

	@Column(name = "USER_LANGUAGE", precision = 38, scale = 0)
	public Integer getLanguage() {
		return language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserInEmail(String userInEmail) {
		this.userInEmail = userInEmail;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserNo(Integer userNo) {
		this.userNo = userNo;
	}

	public void setUserOrgid(Integer userOrgid) {
		this.userOrgid = userOrgid;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public void setUserRegtime(Date userRegtime) {
		this.userRegtime = userRegtime;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/**
	 * 组织节点ID集合
	 */
	@Transient
	public String getOrgtreeid() {
		return orgtreeid;
	}

	public void setOrgtreeid(String orgtreeid) {
		this.orgtreeid = orgtreeid;
	}

	@Transient
	public String getUserorgids() {
		return userorgids;
	}

	public void setUserorgids(String userorgids) {
		this.userorgids = userorgids;
	}

	@Transient
	public String getUserOrgids() {
		return userOrgids;
	}

	public void setUserOrgids(String userOrgids) {
		this.userOrgids = userOrgids;
	}

	@Transient
	public String getUserParentOrgids() {
		return userParentOrgids;
	}

	public void setUserParentOrgids(String userParentOrgids) {
		this.userParentOrgids = userParentOrgids;
	}

	@Transient
	public String getAllOrgPatentNodeIds() {
		return allOrgPatentNodeIds;
	}

	public void setAllOrgPatentNodeIds(String allOrgPatentNodeIds) {
		this.allOrgPatentNodeIds = allOrgPatentNodeIds;
	}

	@Transient
	public String getAllModParentNodeIds() {
		return allModParentNodeIds;
	}

	public void setAllModParentNodeIds(String allModParentNodeIds) {
		this.allModParentNodeIds = allModParentNodeIds;
	}

	@Transient
	public String getSyncOrAsync() {
		return syncOrAsync;
	}

	public void setSyncOrAsync(String syncOrAsync) {
		this.syncOrAsync = syncOrAsync;
	}

	@Transient
	public String getDefaultComboxSize() {
		return defaultComboxSize;
	}

	public void setDefaultComboxSize(String defaultComboxSize) {
		this.defaultComboxSize = defaultComboxSize;
	}

	@Transient
	public String getDefaultGraghSize() {
		return defaultGraghSize;
	}

	public void setDefaultGraghSize(String defaultGraghSize) {
		this.defaultGraghSize = defaultGraghSize;
	}

	@Transient
	public String getUserOrgNames() {
		return userOrgNames;
	}

	public void setUserOrgNames(String userOrgNames) {
		this.userOrgNames = userOrgNames;
	}

	@Transient
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Transient
	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	@Transient
	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	@Transient
	public Integer getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel = roleLevel;
	}

	@Transient
	public Integer getRoleShowLevel() {
		return roleShowLevel;
	}

	public void setRoleShowLevel(Integer roleShowLevel) {
		this.roleShowLevel = roleShowLevel;
	}

	@Transient
	public Integer getRoleVideoKeyEdit() {
		return roleVideoKeyEdit;
	}

	public void setRoleVideoKeyEdit(Integer roleVideoKeyEdit) {
		this.roleVideoKeyEdit = roleVideoKeyEdit;
	}

	@Transient
	public String getDeviceTypeIds() {
		return deviceTypeIds;
	}

	public void setDeviceTypeIds(String deviceTypeIds) {
		this.deviceTypeIds = deviceTypeIds;
	}

	@Transient
	public String getModuleList() {
		return moduleList;
	}

	public void setModuleList(String moduleList) {
		this.moduleList = moduleList;
	}

	@Transient
	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	@Transient
	public Integer getRoleLanguageEdit() {
		return roleLanguageEdit;
	}

	public void setRoleLanguageEdit(Integer roleLanguageEdit) {
		this.roleLanguageEdit = roleLanguageEdit;
	}
}