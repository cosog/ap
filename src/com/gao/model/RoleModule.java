package com.gao.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_module2role")
public class RoleModule implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer rmId;
	private String rmMatrix;

	private Integer rmModuleid;

	private Integer rmRoleId;

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

	@Column(name = "RM_MATRIX", nullable = false, length = 8)
	public String getRmMatrix() {
		return this.rmMatrix;
	}

	@Column(name = "RM_MODULEID", nullable = false, precision = 22, scale = 0)
	public Integer getRmModuleid() {
		return this.rmModuleid;
	}

	@Column(name = "RM_ROLEID", nullable = false, precision = 22, scale = 0)
	public Integer getRmRoleId() {
		return this.rmRoleId;
	}

	public void setRmId(Integer rmId) {
		this.rmId = rmId;
	}

	public void setRmMatrix(String rmMatrix) {
		this.rmMatrix = rmMatrix;
	}

	public void setRmModuleid(Integer rmModuleid) {
		this.rmModuleid = rmModuleid;
	}

	public void setRmRoleId(Integer rmRoleId) {
		this.rmRoleId = rmRoleId;
	}
}