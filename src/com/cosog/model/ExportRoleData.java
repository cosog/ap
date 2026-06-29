package com.cosog.model;

import java.util.List;

public class ExportRoleData {
	
	private int RoleId;
	
	private String RoleName_zh_CN;
	private String RoleName_en;
	private String RoleName_ru;
	
	private int RoleLevel;
	
	private int RoleVideoKeyEdit;
	
	private int RoleLanguageEdit;
	
	private int ShowLevel;
	
	private String Remark_zh_CN;
	private String Remark_en;
	private String Remark_ru;
	
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

	public String getRoleName_zh_CN() {
		return RoleName_zh_CN;
	}

	public void setRoleName_zh_CN(String roleName_zh_CN) {
		RoleName_zh_CN = roleName_zh_CN;
	}

	public String getRoleName_en() {
		return RoleName_en;
	}

	public void setRoleName_en(String roleName_en) {
		RoleName_en = roleName_en;
	}

	public String getRoleName_ru() {
		return RoleName_ru;
	}

	public void setRoleName_ru(String roleName_ru) {
		RoleName_ru = roleName_ru;
	}

	public String getRemark_zh_CN() {
		return Remark_zh_CN;
	}

	public void setRemark_zh_CN(String remark_zh_CN) {
		Remark_zh_CN = remark_zh_CN;
	}

	public String getRemark_en() {
		return Remark_en;
	}

	public void setRemark_en(String remark_en) {
		Remark_en = remark_en;
	}

	public String getRemark_ru() {
		return Remark_ru;
	}

	public void setRemark_ru(String remark_ru) {
		Remark_ru = remark_ru;
	}
}
