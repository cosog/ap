package com.gao.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_acq_item2group_conf")
public class AcquisitionUnitItem implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer unitId;
	private Integer itemId;
	private String matrix;

	

	

	public AcquisitionUnitItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** full constructor */
	public AcquisitionUnitItem(Integer unitId, Integer itemId, String matrix) {
		this.unitId = unitId;
		this.itemId = itemId;
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

	@Column(name = "unitid", nullable = false, precision = 22, scale = 0)
	public Integer getUnitId() {
		return this.unitId;
	}
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	
	@Column(name = "itemid", nullable = false, precision = 22, scale = 0)
	public Integer getItemId() {
		return this.itemId;
	}
	
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	
}