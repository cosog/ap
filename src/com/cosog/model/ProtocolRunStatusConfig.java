package com.cosog.model;

import java.io.Serializable;
import java.util.List;

public class ProtocolRunStatusConfig   implements Serializable{
	private static final long serialVersionUID = 1L;
	public Integer id;
	public String protocol;
	public String itemName;
	public String itemMappingColumn;
	public List<Integer> runValue;
	public List<Integer> stopValue;
	public Integer protocolType;
	public Integer resolutionMode;
	
	public ProtocolRunStatusConfig() {
		super();
	}

	public ProtocolRunStatusConfig(Integer id, String protocol, String itemName, String itemMappingColumn,
			List<Integer> runValue, List<Integer> stopValue, Integer protocolType) {
		super();
		this.id = id;
		this.protocol = protocol;
		this.itemName = itemName;
		this.itemMappingColumn = itemMappingColumn;
		this.runValue = runValue;
		this.stopValue = stopValue;
		this.protocolType = protocolType;
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

	public List<Integer> getRunValue() {
		return runValue;
	}

	public void setRunValue(List<Integer> runValue) {
		this.runValue = runValue;
	}

	public List<Integer> getStopValue() {
		return stopValue;
	}

	public void setStopValue(List<Integer> stopValue) {
		this.stopValue = stopValue;
	}

	public Integer getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(Integer protocolType) {
		this.protocolType = protocolType;
	}

	public Integer getResolutionMode() {
		return resolutionMode;
	}

	public void setResolutionMode(Integer resolutionMode) {
		this.resolutionMode = resolutionMode;
	}
}
