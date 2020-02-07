/*
 * Created on 2006-8-27
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.gao.model;


/**
 * @author IBM
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class UserBean {
	private Integer userNo;
	private String userId;
	private String userName;
	private String userInEmail;
	private Integer userType;
	private Integer userOrgid;
	private boolean leaderFlag;
	private String userPhone;
	private String userMobile;
	private String userOrgName;
	private String userStyle;

	public String getUserStyle() {
		return userStyle;
	}

	public void setUserStyle(String userStyle) {
		this.userStyle = userStyle;
	}

	public Integer getUserNo() {
		return userNo;
	}

	public void setUserNo(Integer _userNo) {
		userNo = _userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String _userId) {
		userId = _userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String _userName) {
		userName = _userName;
	}

	public String getUserInEmail() {
		if (userInEmail == null)
			return "";
		return userInEmail;
	}

	public void setUserInEmail(String _userInEmail) {
		userInEmail = _userInEmail;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer _userType) {
		userType = _userType;
	}

	public Integer getUserOrgid() {
		return userOrgid;
	}

	public void setUserOrgid(Integer _userOrgid) {
		userOrgid = _userOrgid;
	}



	public boolean isLeader() {
		return leaderFlag;
	}

	public void setLeaderFlag(String value) {
		if (value.equals("0"))
			leaderFlag = false;
		else
			leaderFlag = true;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserOrgName() {
		return userOrgName;
	}

	public void setUserOrgName(String userOrgName) {
		this.userOrgName = userOrgName;
	}
}
