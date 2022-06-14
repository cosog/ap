package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_rpcdeviceaddinfo")
public class RPCDeviceAddInfo implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer wellId;
	private String itemName;
	private String itemValue;
	private String itemUnit;
	
	public RPCDeviceAddInfo() {
		super();
	}

	public RPCDeviceAddInfo(Integer id, Integer wellId, String itemName, String itemValue, String itemUnit) {
		super();
		this.id = id;
		this.wellId = wellId;
		this.itemName = itemName;
		this.itemValue = itemValue;
		this.itemUnit = itemUnit;
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

	@Column(name = "wellId", nullable = false, precision = 22, scale = 0)
	public Integer getWellId() {
		return wellId;
	}

	public void setWellId(Integer wellId) {
		this.wellId = wellId;
	}

	@Column(name = "itemName", nullable = false, precision = 22, scale = 0)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "itemValue", nullable = true, precision = 22, scale = 0)
	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	@Column(name = "itemUnit", nullable = true, precision = 22, scale = 0)
	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	
}
