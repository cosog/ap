package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import oracle.sql.BLOB;

@Entity
@Table(name = "tbl_datamapping")
public class DataMapping implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String mappingColumn;
	private Integer protocolType;
	private Integer repetitionTimes;
	private Integer mappingMode;
	private String saveTable;
	private String    saveColumn;
	// Constructors

	/** default constructor */
	public DataMapping() {
	}

	public DataMapping(Integer id, String name, String mappingColumn, Integer protocolType, Integer repetitionTimes,
			Integer mappingMode, String saveTable, String saveColumn) {
		super();
		this.id = id;
		this.name = name;
		this.mappingColumn = mappingColumn;
		this.protocolType = protocolType;
		this.repetitionTimes = repetitionTimes;
		this.mappingMode = mappingMode;
		this.saveTable = saveTable;
		this.saveColumn = saveColumn;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "mappingColumn", nullable = false, length = 30)
	public String getMappingColumn() {
		return mappingColumn;
	}

	public void setMappingColumn(String mappingColumn) {
		this.mappingColumn = mappingColumn;
	}

	@Column(name = "protocolType", nullable = false, precision = 22, scale = 0)
	public Integer getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(Integer protocolType) {
		this.protocolType = protocolType;
	}

	@Column(name = "repetitionTimes", nullable = false, precision = 22, scale = 0)
	public Integer getRepetitionTimes() {
		return repetitionTimes;
	}

	public void setRepetitionTimes(Integer repetitionTimes) {
		this.repetitionTimes = repetitionTimes;
	}

	@Column(name = "mappingMode", nullable = false, precision = 22, scale = 0)
	public Integer getMappingMode() {
		return mappingMode;
	}

	public void setMappingMode(Integer mappingMode) {
		this.mappingMode = mappingMode;
	}

	@Column(name = "saveTable", nullable = false, length = 30)
	public String getSaveTable() {
		return saveTable;
	}

	public void setSaveTable(String saveTable) {
		this.saveTable = saveTable;
	}

	@Column(name = "saveColumn", nullable = false, length = 30)
	public String getSaveColumn() {
		return saveColumn;
	}

	public void setSaveColumn(String saveColumn) {
		this.saveColumn = saveColumn;
	}
}