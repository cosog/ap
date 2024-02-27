package com.cosog.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_tab2role")
public class RoleTab implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer rtId;
	private String rtMatrix;

	private Integer rtTabId;

	private Integer rtRoleId;

	public RoleTab() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** full constructor */
	public RoleTab(Integer rtRoleId, Integer rtTabId, String rtMatrix) {
		this.rtRoleId = rtRoleId;
		this.rtTabId = rtTabId;
		this.rtMatrix = rtMatrix;
	}

	@Id
	@GeneratedValue
	@Column(name = "RT_ID", nullable = false, precision = 22, scale = 0)
	public Integer getRtId() {
		return rtId;
	}
	
	public void setRtId(Integer rtId) {
		this.rtId = rtId;
	}

	@Column(name = "RT_MATRIX", nullable = false, length = 8)
	public String getRtMatrix() {
		return this.rtMatrix;
	}
	
	public void setRtMatrix(String rtMatrix) {
		this.rtMatrix = rtMatrix;
	}

	@Column(name = "RT_TABID", nullable = false, precision = 22, scale = 0)
	public Integer getRtTabId() {
		return this.rtTabId;
	}
	
	public void setRtTabId(Integer rtTabId) {
		this.rtTabId = rtTabId;
	}

	@Column(name = "RT_ROLEID", nullable = false, precision = 22, scale = 0)
	public Integer getRtRoleId() {
		return this.rtRoleId;
	}

	public void setRtRoleId(Integer rtRoleId) {
		this.rtRoleId = rtRoleId;
	}
}