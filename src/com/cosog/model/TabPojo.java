package com.cosog.model;

import java.io.Serializable;

public class TabPojo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tabCode;
	private String tabValue;
	private String tabUrl;
	private boolean hidden;

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public TabPojo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TabPojo(String tabCode, String tabValue, String tabUrl) {
		super();
		this.tabCode = tabCode;
		this.tabValue = tabValue;
		this.tabUrl = tabUrl;
	}

	public TabPojo(String tabCode, String tabValue, boolean hidden) {
		super();
		this.tabCode = tabCode;
		this.tabValue = tabValue;
	//	this.tabUrl = tabUrl;
		this.hidden = hidden;
	}

	public String getTabCode() {
		return tabCode;
	}

	public void setTabCode(String tabCode) {
		this.tabCode = tabCode;
	}

	public String getTabValue() {
		return tabValue;
	}

	public void setTabValue(String tabValue) {
		this.tabValue = tabValue;
	}

	public String getTabUrl() {
		return tabUrl;
	}

	public void setTabUrl(String tabUrl) {
		this.tabUrl = tabUrl;
	}

	public String toString() {

		return "tabCode==" + tabCode + "===tabValue==" + tabValue
				+ "===tabUrl===" + tabUrl;
	}
}
