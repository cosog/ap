package com.cosog.model;

import java.io.Serializable;

public class DataMapping  implements Serializable{
	private static final long serialVersionUID = 1L;
	public Integer id;
	public String name;
	public String mappingColumn;
	public Integer protocolType;
	public Integer mappingMode;
	public Integer repetitionTimes;
	public String calColumn;
	public Integer calculateEnable;
	public DataMapping(Integer id, String name, String mappingColumn, Integer protocolType, Integer mappingMode,
			Integer repetitionTimes, String calColumn, Integer calculateEnable) {
		super();
		this.id = id;
		this.name = name;
		this.mappingColumn = mappingColumn;
		this.protocolType = protocolType;
		this.mappingMode = mappingMode;
		this.repetitionTimes = repetitionTimes;
		this.calColumn = calColumn;
		this.calculateEnable = calculateEnable;
	}
	public DataMapping() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMappingColumn() {
		return mappingColumn;
	}
	public void setMappingColumn(String mappingColumn) {
		this.mappingColumn = mappingColumn;
	}
	public Integer getProtocolType() {
		return protocolType;
	}
	public void setProtocolType(Integer protocolType) {
		this.protocolType = protocolType;
	}
	public Integer getMappingMode() {
		return mappingMode;
	}
	public void setMappingMode(Integer mappingMode) {
		this.mappingMode = mappingMode;
	}
	public Integer getRepetitionTimes() {
		return repetitionTimes;
	}
	public void setRepetitionTimes(Integer repetitionTimes) {
		this.repetitionTimes = repetitionTimes;
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
