package com.cosog.model;

import java.util.Date;

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
	private String userTitleName;
	private String userAddress;
	private String userId;
	private String userInEmail;
	private String userIsleader;
	private String userMobile;
	private String userName;
	private Integer userNo;
	private Integer userOrgid;
	private String userOutEmail;
	private String userPhone;
	private String userPostcode;
	private String userPwd;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date userRegtime;
	private String userStyle;
	private String userTitle;
	private String picUrl;
	private String pageSize;
	private Integer userType;
	private Integer userQuickLogin;
	private String userOrgids;
	private String userOrgNames;
	private String userParentOrgids;
	private String allOrgPatentNodeIds;//组织表中所有父节点
	private String allModParentNodeIds;//模块表中所有父节点
	private String syncOrAsync;
	private String defaultComboxSize;
	private String defaultGraghSize;

	/**
	 * 组织节点orgCode集合
	 */
	private String orgtreeid;
	/**
	 * 组织节点ID集合
	 */
	private String userorgids;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue
	// @GeneratedValue(generator = "paymentableGenerator")
	// @GenericGenerator(name = "paymentableGenerator", strategy =
	// "com.gao.utils.UUIDGenerator")
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
	public String getUserTitleName() {
		return userTitleName;
	}

	public void setUserTitleName(String userTitleName) {
		this.userTitleName = userTitleName;
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

	@Column(name = "USER_ADDRESS", length = 200)
	public String getUserAddress() {
		return this.userAddress;
	}

	@Column(name = "USER_ID", unique = true, nullable = false, length = 20)
	public String getUserId() {
		return this.userId;
	}

	@Column(name = "USER_IN_EMAIL", length = 40)
	public String getUserInEmail() {
		return this.userInEmail;
	}

	@Column(name = "USER_ISLEADER", length = 1)
	public String getUserIsleader() {
		return this.userIsleader;
	}

	@Column(name = "USER_MOBILE", length = 40)
	public String getUserMobile() {
		return this.userMobile;
	}

	@Column(name = "USER_NAME", nullable = false, length = 40)
	public String getUserName() {
		return this.userName;
	}

	@Column(name = "USER_ORGID", nullable = false, precision = 22, scale = 0)
	public Integer getUserOrgid() {
		return this.userOrgid;
	}

	@Column(name = "USER_OUT_EMAIL", length = 100)
	public String getUserOutEmail() {
		return this.userOutEmail;
	}

	@Column(name = "USER_PHONE", length = 40)
	public String getUserPhone() {
		return this.userPhone;
	}

	@Column(name = "USER_POSTCODE", length = 6)
	public String getUserPostcode() {
		return this.userPostcode;
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

	@Column(name = "USER_STYLE", length = 20)
	public String getUserStyle() {
		return this.userStyle;
	}

	@Column(name = "USER_TITLE", length = 100)
	public String getUserTitle() {
		return this.userTitle;
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

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserInEmail(String userInEmail) {
		this.userInEmail = userInEmail;
	}

	public void setUserIsleader(String userIsleader) {
		this.userIsleader = userIsleader;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
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

	public void setUserOutEmail(String userOutEmail) {
		this.userOutEmail = userOutEmail;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public void setUserPostcode(String userPostcode) {
		this.userPostcode = userPostcode;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public void setUserRegtime(Date userRegtime) {
		this.userRegtime = userRegtime;
	}

	public void setUserStyle(String userStyle) {
		this.userStyle = userStyle;
	}

	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
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

	
}