package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  <p>描述：运行状态配置 实体类  TBL_RUNSTATUSCONFIG</p>
 *  
 */
@Entity
@Table(name = "TBL_RUNSTATUSCONFIG")
public class RunStatusConfig {
	private Integer id;
	private String protocol;
	private String itemName;
	private String itemMappingColumn;
	private String runValue;
	private String stopValue;
	
	private Integer protocolType;
	private Integer resolutionMode;
	
	private String runCondition;
	private String stopCondition;
	
	private Integer bitIndex;
	
	public RunStatusConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RunStatusConfig(Integer id, String protocol, String itemName, String itemMappingColumn, String runValue,
			String stopValue, Integer protocolType, Integer resolutionMode, String runCondition, String stopCondition,
			Integer bitIndex) {
		super();
		this.id = id;
		this.protocol = protocol;
		this.itemName = itemName;
		this.itemMappingColumn = itemMappingColumn;
		this.runValue = runValue;
		this.stopValue = stopValue;
		this.protocolType = protocolType;
		this.resolutionMode = resolutionMode;
		this.runCondition = runCondition;
		this.stopCondition = stopCondition;
		this.bitIndex = bitIndex;
	}

	@Id
	@GeneratedValue
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "protocol")
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Column(name = "itemName" )
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "itemMappingColumn" )
	public String getItemMappingColumn() {
		return itemMappingColumn;
	}

	public void setItemMappingColumn(String itemMappingColumn) {
		this.itemMappingColumn = itemMappingColumn;
	}

	@Column(name = "runValue" )
	public String getRunValue() {
		return runValue;
	}

	public void setRunValue(String runValue) {
		this.runValue = runValue;
	}

	@Column(name = "stopValue" )
	public String getStopValue() {
		return stopValue;
	}

	public void setStopValue(String stopValue) {
		this.stopValue = stopValue;
	}

	@Column(name = "protocolType" )
	public Integer getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(Integer protocolType) {
		this.protocolType = protocolType;
	}

	@Column(name = "resolutionMode" )
	public Integer getResolutionMode() {
		return resolutionMode;
	}

	public void setResolutionMode(Integer resolutionMode) {
		this.resolutionMode = resolutionMode;
	}

	@Column(name = "runCondition" )
	public String getRunCondition() {
		return runCondition;
	}

	public void setRunCondition(String runCondition) {
		this.runCondition = runCondition;
	}

	@Column(name = "stopCondition" )
	public String getStopCondition() {
		return stopCondition;
	}

	public void setStopCondition(String stopCondition) {
		this.stopCondition = stopCondition;
	}

	@Column(name = "bitIndex" )
	public Integer getBitIndex() {
		return bitIndex;
	}

	public void setBitIndex(Integer bitIndex) {
		this.bitIndex = bitIndex;
	}
}
