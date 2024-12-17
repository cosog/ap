package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TBL_AUXILIARYDEVICEADDINFO")
public class AuxiliaryDeviceAddInfo implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer deviceId;
	private String itemName;
	private String itemCode;
	private String itemValue;
	private String itemUnit;
	private Integer masterId;
	private String manufacturer;
	private String model;
	
	public AuxiliaryDeviceAddInfo() {
		super();
	}

	public AuxiliaryDeviceAddInfo(Integer id, Integer deviceId, String itemName,String itemCode, String itemValue, String itemUnit) {
		super();
		this.id = id;
		this.deviceId = deviceId;
		this.itemName = itemName;
		this.itemCode = itemCode;
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

	@Column(name = "deviceId", nullable = false, precision = 22, scale = 0)
	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name = "itemName", nullable = false, precision = 22, scale = 0)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "itemCode", nullable = false, precision = 22, scale = 0)
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	@Transient
	public Integer getMasterId() {
		return masterId;
	}

	public void setMasterId(Integer masterId) {
		this.masterId = masterId;
	}

	@Transient
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Transient
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
}
