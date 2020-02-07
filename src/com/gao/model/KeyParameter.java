package com.gao.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class KeyParameter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date dateParamValue;
	private Integer IntParamValue;
	private String paramType;
	private String strParamValue;
	private Timestamp timeParamValue;

	public KeyParameter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public KeyParameter(String strParamValue, Integer intParamValue,
			Date dateParamValue, Timestamp timeParamValue, String paramType) {
		super();
		this.strParamValue = strParamValue;
		IntParamValue = intParamValue;
		this.dateParamValue = dateParamValue;
		this.timeParamValue = timeParamValue;
		this.paramType = paramType;
	}

	public Date getDateParamValue() {
		return dateParamValue;
	}

	public Integer getIntParamValue() {
		return IntParamValue;
	}

	public String getParamType() {
		return paramType;
	}

	public String getStrParamValue() {
		return strParamValue;
	}

	public Timestamp getTimeParamValue() {
		return timeParamValue;
	}

	public void setDateParamValue(Date dateParamValue) {
		this.dateParamValue = dateParamValue;
	}

	public void setIntParamValue(Integer intParamValue) {
		IntParamValue = intParamValue;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public void setStrParamValue(String strParamValue) {
		this.strParamValue = strParamValue;
	}

	public void setTimeParamValue(Timestamp timeParamValue) {
		this.timeParamValue = timeParamValue;
	}

}
