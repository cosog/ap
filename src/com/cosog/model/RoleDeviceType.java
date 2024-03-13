package com.cosog.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_devicetype2role")
public class RoleDeviceType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer rdId;
	private String rdMatrix;

	private Integer rdDeviceTypeId;

	private Integer rdRoleId;

	public RoleDeviceType() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** full constructor */
	public RoleDeviceType(Integer rdRoleId, Integer rdDeviceTypeId, String rdMatrix) {
		this.rdRoleId = rdRoleId;
		this.rdDeviceTypeId = rdDeviceTypeId;
		this.rdMatrix = rdMatrix;
	}

	@Id
	@GeneratedValue
	@Column(name = "RD_ID", nullable = false, precision = 22, scale = 0)
	public Integer getRdId() {
		return rdId;
	}
	
	public void setRdId(Integer rdId) {
		this.rdId = rdId;
	}

	@Column(name = "RD_MATRIX", nullable = false, length = 8)
	public String getRdMatrix() {
		return this.rdMatrix;
	}
	
	public void setRdMatrix(String rdMatrix) {
		this.rdMatrix = rdMatrix;
	}

	@Column(name = "RD_DEVICETYPEID", nullable = false, precision = 22, scale = 0)
	public Integer getRdDeviceTypeId() {
		return this.rdDeviceTypeId;
	}
	
	public void setRdDeviceTypeId(Integer rdDeviceTypeId) {
		this.rdDeviceTypeId = rdDeviceTypeId;
	}

	@Column(name = "RD_ROLEID", nullable = false, precision = 22, scale = 0)
	public Integer getRdRoleId() {
		return this.rdRoleId;
	}

	public void setRdRoleId(Integer rdRoleId) {
		this.rdRoleId = rdRoleId;
	}
}