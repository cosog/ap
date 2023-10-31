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
	public List<RunStatusCondition> runConditionList;
	public List<RunStatusCondition> stopConditionList;
	public Integer protocolType;
	public Integer resolutionMode;
	
	public ProtocolRunStatusConfig() {
		super();
	}

	public ProtocolRunStatusConfig(Integer id, String protocol, String itemName, String itemMappingColumn,
			List<Integer> runValue, List<Integer> stopValue, 
			List<RunStatusCondition> runConditionList,List<RunStatusCondition> stopConditionList,
			Integer protocolType,Integer resolutionMode) {
		super();
		this.id = id;
		this.protocol = protocol;
		this.itemName = itemName;
		this.itemMappingColumn = itemMappingColumn;
		this.runValue = runValue;
		this.stopValue = stopValue;
		this.runConditionList = runConditionList;
		this.stopConditionList = stopConditionList;
		this.resolutionMode = resolutionMode;
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
	
	public static class RunStatusCondition implements Serializable{
		private static final long serialVersionUID = 1L;
		
		public int logic;
		public float value;
		public int getLogic() {
			return logic;
		}
		public void setLogic(int logic) {
			this.logic = logic;
		}
		public float getValue() {
			return value;
		}
		public void setValue(float value) {
			this.value = value;
		}
	}

	public List<RunStatusCondition> getRunConditionList() {
		return runConditionList;
	}

	public void setRunConditionList(List<RunStatusCondition> runConditionList) {
		this.runConditionList = runConditionList;
	}

	public List<RunStatusCondition> getStopConditionList() {
		return stopConditionList;
	}

	public void setStopConditionList(List<RunStatusCondition> stopConditionList) {
		this.stopConditionList = stopConditionList;
	}
}
