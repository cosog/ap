package com.cosog.model;

import java.io.Serializable;

public class DataMappingColumnCalculateConfig implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public Integer id;
	
	public String protocol;
	
	public String itemName;
	
	public String itemMappingColumn;
	
	public String calColumn;
	
	public Integer calculateEnable;

	public DataMappingColumnCalculateConfig() {
		super();
	}

	public DataMappingColumnCalculateConfig(Integer id, String protocol, String itemName, String itemMappingColumn,
			String calColumn, Integer calculateEnable) {
		super();
		this.id = id;
		this.protocol = protocol;
		this.itemName = itemName;
		this.itemMappingColumn = itemMappingColumn;
		this.calColumn = calColumn;
		this.calculateEnable = calculateEnable;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemMappingColumn() {
		return itemMappingColumn;
	}

	public void setItemMappingColumn(String itemMappingColumn) {
		this.itemMappingColumn = itemMappingColumn;
	}

	public String getCalColumn() {
		return calColumn;
	}

	public void setCalColumn(String calColumn) {
		this.calColumn = calColumn;
	}

	public Integer getCalculateEnable() {
		return calculateEnable;
	}

	public void setCalculateEnable(Integer calculateEnable) {
		this.calculateEnable = calculateEnable;
	}
}
