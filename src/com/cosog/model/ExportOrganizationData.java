package com.cosog.model;

public class ExportOrganizationData {
	
	private int OrgId;
	
	private String OrgCode;
	
	private String OrgName_zh_CN;
	
	private String OrgName_en;
	
	private String OrgName_ru;
	
	private String OrgMemo;
	
	private int OrgParentId;
	
	private String OrgSeq;
	
	private int saveSign=0;
	
	private String msg="";
	
	private int saveId=0;

	public int getOrgId() {
		return OrgId;
	}

	public void setOrgId(int orgId) {
		OrgId = orgId;
	}

	public String getOrgCode() {
		return OrgCode;
	}

	public void setOrgCode(String orgCode) {
		OrgCode = orgCode;
	}

	public String getOrgName_zh_CN() {
		return OrgName_zh_CN;
	}

	public void setOrgName_zh_CN(String orgName_zh_CN) {
		OrgName_zh_CN = orgName_zh_CN;
	}

	public String getOrgName_en() {
		return OrgName_en;
	}

	public void setOrgName_en(String orgName_en) {
		OrgName_en = orgName_en;
	}

	public String getOrgName_ru() {
		return OrgName_ru;
	}

	public void setOrgName_ru(String orgName_ru) {
		OrgName_ru = orgName_ru;
	}

	public String getOrgMemo() {
		return OrgMemo;
	}

	public void setOrgMemo(String orgMemo) {
		OrgMemo = orgMemo;
	}

	public int getOrgParentId() {
		return OrgParentId;
	}

	public void setOrgParentId(int orgParentId) {
		OrgParentId = orgParentId;
	}

	public String getOrgSeq() {
		return OrgSeq;
	}

	public void setOrgSeq(String orgSeq) {
		OrgSeq = orgSeq;
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
