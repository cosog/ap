package com.cosog.model;

public class ExportUserData {
	
	private int UserNo;
	
	private String UserName;
	
	private int UserOrgid;
	
	private String UserId;
	
	private String UserPwd;
	
	private int UserType;
	
	private String UserPhone;
	
	private String UserInEmail;
	
	private String UserRegtime;
	
	private int UserQuickLogin;
	
	private int ReceiveSMS;

	private int ReceiveMail;
	
	private int UserEnable;
	
	private int UserLanguage;
	
	private int RoleLevel;
	
	private String RoleName;
	
	private int saveSign=0;
	
	private String msg="";
	
	private int saveId=0;

	public int getUserNo() {
		return UserNo;
	}

	public void setUserNo(int userNo) {
		UserNo = userNo;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public int getUserOrgid() {
		return UserOrgid;
	}

	public void setUserOrgid(int userOrgid) {
		UserOrgid = userOrgid;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getUserPwd() {
		return UserPwd;
	}

	public void setUserPwd(String userPwd) {
		UserPwd = userPwd;
	}

	public int getUserType() {
		return UserType;
	}

	public void setUserType(int userType) {
		UserType = userType;
	}

	public String getUserPhone() {
		return UserPhone;
	}

	public void setUserPhone(String userPhone) {
		UserPhone = userPhone;
	}

	public String getUserInEmail() {
		return UserInEmail;
	}

	public void setUserInEmail(String userInEmail) {
		UserInEmail = userInEmail;
	}

	public String getUserRegtime() {
		return UserRegtime;
	}

	public void setUserRegtime(String userRegtime) {
		UserRegtime = userRegtime;
	}

	public int getUserQuickLogin() {
		return UserQuickLogin;
	}

	public void setUserQuickLogin(int userQuickLogin) {
		UserQuickLogin = userQuickLogin;
	}

	public int getReceiveSMS() {
		return ReceiveSMS;
	}

	public void setReceiveSMS(int receiveSMS) {
		ReceiveSMS = receiveSMS;
	}

	public int getReceiveMail() {
		return ReceiveMail;
	}

	public void setReceiveMail(int receiveMail) {
		ReceiveMail = receiveMail;
	}

	public int getUserEnable() {
		return UserEnable;
	}

	public void setUserEnable(int userEnable) {
		UserEnable = userEnable;
	}

	public int getUserLanguage() {
		return UserLanguage;
	}

	public void setUserLanguage(int UserLanguage) {
		this.UserLanguage = UserLanguage;
	}

	public int getSaveSign() {
		return saveSign;
	}

	public void setSaveSign(int saveSign) {
		this.saveSign = saveSign;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getSaveId() {
		return saveId;
	}

	public void setSaveId(int saveId) {
		this.saveId = saveId;
	}

	public int getRoleLevel() {
		return RoleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		RoleLevel = roleLevel;
	}

	public String getRoleName() {
		return RoleName;
	}

	public void setRoleName(String roleName) {
		RoleName = roleName;
	}
}
