package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_auxiliary2master")
public class MasterAndAuxiliaryDevice implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer masterid;
	private Integer auxiliaryid;
	private String matrix;
	
	public MasterAndAuxiliaryDevice() {
		super();
	}
	public MasterAndAuxiliaryDevice(Integer id, Integer masterid, Integer auxiliaryid, String matrix) {
		super();
		this.id = id;
		this.masterid = masterid;
		this.auxiliaryid = auxiliaryid;
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
	@Column(name = "masterid", nullable = false, precision = 22, scale = 0)
	public Integer getMasterid() {
		return masterid;
	}
	public void setMasterid(Integer masterid) {
		this.masterid = masterid;
	}
	@Column(name = "auxiliaryid", nullable = false, precision = 22, scale = 0)
	public Integer getAuxiliaryid() {
		return auxiliaryid;
	}
	public void setAuxiliaryid(Integer auxiliaryid) {
		this.auxiliaryid = auxiliaryid;
	}
	@Column(name = "matrix", nullable = false, length = 8)
	public String getMatrix() {
		return matrix;
	}
	public void setMatrix(String matrix) {
		this.matrix = matrix;
	}
	
	
}
