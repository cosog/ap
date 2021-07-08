package com.cosog.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_acq_group2unit_conf")
public class AcquisitionUnitGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer groupId;
	private Integer unitId;
	private String matrix;

	

	

	public AcquisitionUnitGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** full constructor */
	public AcquisitionUnitGroup(Integer groupId, Integer unitId, String matrix) {
		this.groupId = groupId;
		this.unitId = unitId;
		this.matrix = matrix;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "matrix", nullable = false, length = 8)
	public String getMatrix() {
		return this.matrix;
	}
	
	public void setMatrix(String matrix) {
		this.matrix = matrix;
	}

	@Column(name = "groupid", nullable = false, precision = 22, scale = 0)
	public Integer getGroupId() {
		return this.groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
	@Column(name = "unitid", nullable = false, precision = 22, scale = 0)
	public Integer getUnitId() {
		return this.unitId;
	}
	
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	
}