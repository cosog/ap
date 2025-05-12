package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_deviceaddinfo")
public class DeviceAddInfo implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer deviceId;
	private String itemName;
	private String itemValue;
	private String itemUnit;
	private Integer overview;
	private Integer overviewSort;
	
	public DeviceAddInfo() {
		super();
	}

	public DeviceAddInfo(Integer id, Integer deviceId, String itemName, String itemValue, String itemUnit, Integer overview, Integer overviewSort) {
		super();
		this.id = id;
		this.deviceId = deviceId;
		this.itemName = itemName;
		this.itemValue = itemValue;
		this.itemUnit = itemUnit;
		this.overview = overview;
		this.overviewSort = overviewSort;
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

	@Column(name = "overview", nullable = true, precision = 22, scale = 0)
	public Integer getOverview() {
		return overview;
	}

	public void setOverview(Integer overview) {
		this.overview = overview;
	}

	@Column(name = "overviewSort", nullable = true, precision = 22, scale = 0)
	public Integer getOverviewSort() {
		return overviewSort;
	}

	public void setOverviewSort(Integer overviewSort) {
		this.overviewSort = overviewSort;
	}
	
}
