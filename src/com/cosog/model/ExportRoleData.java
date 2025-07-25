package com.cosog.model;

import java.util.List;

public class ExportRoleData {
	
	private int RoleId;
	
	private String RoleName;
	
	private int RoleLevel;
	
	private int RoleVideoKeyEdit;
	
	private int RoleLanguageEdit;
	
	private int ShowLevel;
	
	private String Remark;
	
	private List<RoleRight> ModuleRight;
	
	private List<RoleRight> DeviceTypeRight;
	
	private List<RoleRight> LanguageRight;
	
	private int saveSign=0;
	
	private String msg="";
	
	private int saveId=0;

	public static class RoleRight{
		
		private int Id;
		
		private String Matrix;

		public int getId() {
			return Id;
		}

		public void setId(int id) {
			Id = id;
		}

		public String getMatrix() {
			return Matrix;
		}

		public void setMatrix(String matrix) {
			Matrix = matrix;
		}
		
		
	}

	public int getRoleId() {
		return RoleId;
	}

	public void setRoleId(int roleId) {
		RoleId = roleId;
	}

	public String getRoleName() {
		return RoleName;
	}

	public void setRoleName(String roleName) {
		RoleName = roleName;
	}

	public int getRoleLevel() {
		return RoleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		RoleLevel = roleLevel;
	}

	public int getRoleVideoKeyEdit() {
		return RoleVideoKeyEdit;
	}

	public void setRoleVideoKeyEdit(int roleVideoKeyEdit) {
		RoleVideoKeyEdit = roleVideoKeyEdit;
	}

	public int getRoleLanguageEdit() {
		return RoleLanguageEdit;
	}

	public void setRoleLanguageEdit(int roleLanguageEdit) {
		RoleLanguageEdit = roleLanguageEdit;
	}

	public int getShowLevel() {
		return ShowLevel;
	}

	public void setShowLevel(int showLevel) {
		ShowLevel = showLevel;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public List<RoleRight> getModuleRight() {
		return ModuleRight;
	}

	public void setModuleRight(List<RoleRight> moduleRight) {
		ModuleRight = moduleRight;
	}

	public List<RoleRight> getDeviceTypeRight() {
		return DeviceTypeRight;
	}

	public void setDeviceTypeRight(List<RoleRight> deviceTypeRight) {
		DeviceTypeRight = deviceTypeRight;
	}

	public List<RoleRight> getLanguageRight() {
		return LanguageRight;
	}

	public void setLanguageRight(List<RoleRight> languageRight) {
		LanguageRight = languageRight;
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
}
