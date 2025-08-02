package com.cosog.model;

public class ExportModuleData {
	
	private int ModuleId;
	
	private int ModuleParentId;
	
	private String ModuleName_zh_CN;
	
	private String ModuleShowName_zh_CN;
	
	private String ModuleName_en;
	
	private String ModuleShowName_en;
	
	private String ModuleName_ru;
	
	private String ModuleShowName_ru;
	
	private String ModuleUrl;
	
	private String ModuleCode;
	
	private int ModuleSeq;
	
	private String ModuleIcon;
	
	private int ModuleType;
	
	private String ModuleControl;
	
	private int saveSign=0;
	
	private String msg="";
	
	private int saveId=0;

	public int getModuleId() {
		return ModuleId;
	}

	public void setModuleId(int moduleId) {
		ModuleId = moduleId;
	}

	public int getModuleParentId() {
		return ModuleParentId;
	}

	public void setModuleParentId(int moduleParentId) {
		ModuleParentId = moduleParentId;
	}

	public String getModuleName_zh_CN() {
		return ModuleName_zh_CN;
	}

	public void setModuleName_zh_CN(String moduleName_zh_CN) {
		ModuleName_zh_CN = moduleName_zh_CN;
	}

	public String getModuleShowName_zh_CN() {
		return ModuleShowName_zh_CN;
	}

	public void setModuleShowName_zh_CN(String moduleShowName_zh_CN) {
		ModuleShowName_zh_CN = moduleShowName_zh_CN;
	}

	public String getModuleName_en() {
		return ModuleName_en;
	}

	public void setModuleName_en(String moduleName_en) {
		ModuleName_en = moduleName_en;
	}

	public String getModuleShowName_en() {
		return ModuleShowName_en;
	}

	public void setModuleShowName_en(String moduleShowName_en) {
		ModuleShowName_en = moduleShowName_en;
	}

	public String getModuleName_ru() {
		return ModuleName_ru;
	}

	public void setModuleName_ru(String moduleName_ru) {
		ModuleName_ru = moduleName_ru;
	}

	public String getModuleShowName_ru() {
		return ModuleShowName_ru;
	}

	public void setModuleShowName_ru(String moduleShowName_ru) {
		ModuleShowName_ru = moduleShowName_ru;
	}

	public String getModuleUrl() {
		return ModuleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		ModuleUrl = moduleUrl;
	}

	public String getModuleCode() {
		return ModuleCode;
	}

	public void setModuleCode(String moduleCode) {
		ModuleCode = moduleCode;
	}

	public int getModuleSeq() {
		return ModuleSeq;
	}

	public void setModuleSeq(int moduleSeq) {
		ModuleSeq = moduleSeq;
	}

	public String getModuleIcon() {
		return ModuleIcon;
	}

	public void setModuleIcon(String moduleIcon) {
		ModuleIcon = moduleIcon;
	}

	public int getModuleType() {
		return ModuleType;
	}

	public void setModuleType(int moduleType) {
		ModuleType = moduleType;
	}

	public String getModuleControl() {
		return ModuleControl;
	}

	public void setModuleControl(String moduleControl) {
		ModuleControl = moduleControl;
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
