package com.cosog.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbl_module2role")
public class RoleModule implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer rmId;
	private String rmMatrix;

	private Integer rmModuleid;

	private Integer rmRoleId;
	
	private String mdCode;
	
	private String mdName;
	
	private String roleName;
	
	private Integer viewFlag=0;
	
	private Integer editFlag=0;
	
	private Integer controlFlag=0;

	public RoleModule() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** full constructor */
	public RoleModule(Integer rmRoleId, Integer rmModuleid, String rmMatrix) {
		this.rmRoleId = rmRoleId;
		this.rmModuleid = rmModuleid;
		this.rmMatrix = rmMatrix;
	}

	@Id
	@GeneratedValue
	@Column(name = "RM_ID", nullable = false, precision = 22, scale = 0)
	public Integer getRmId() {
		return rmId;
	}
	
	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

	@Column(name = "RM_MATRIX", nullable = false, length = 8)
	public String getRmMatrix() {
		return this.rmMatrix;
	}
	
	public void setRmMatrix(String rmMatrix) {
		this.rmMatrix = rmMatrix;
	}

	@Column(name = "RM_MODULEID", nullable = false, precision = 22, scale = 0)
	public Integer getRmModuleid() {
		return this.rmModuleid;
	}
	
	public void setRmModuleid(Integer rmModuleid) {
		this.rmModuleid = rmModuleid;
	}

	@Column(name = "RM_ROLEID", nullable = false, precision = 22, scale = 0)
	public Integer getRmRoleId() {
		return this.rmRoleId;
	}

	public void setRmRoleId(Integer rmRoleId) {
		this.rmRoleId = rmRoleId;
	}

	@Transient
	public String getMdCode() {
		return mdCode;
	}

	public void setMdCode(String mdCode) {
		this.mdCode = mdCode;
	}

	@Transient
	public String getMdName() {
		return mdName;
	}

	public void setMdName(String mdName) {
		this.mdName = mdName;
	}

	@Transient
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Transient
	public Integer getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(Integer viewFlag) {
		this.viewFlag = viewFlag;
	}

	@Transient
	public Integer getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(Integer editFlag) {
		this.editFlag = editFlag;
	}

	@Transient
	public Integer getControlFlag() {
		return controlFlag;
	}

	public void setControlFlag(Integer controlFlag) {
		this.controlFlag = controlFlag;
	}
}