package com.gao.model;

import java.io.Serializable;
import java.util.Map;

public class TabModel implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private Map<String, String> wellType;
	private Map<String, TabPojo> liftType;
	private Map<String, TabPojo> injectionType;

	public TabModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Map<String, String> getWellType() {
		return wellType;
	}

	public void setWellType(Map<String, String> wellType) {
		this.wellType = wellType;
	}

	public Map<String, TabPojo> getLiftType() {
		return liftType;
	}

	public void setLiftType(Map<String, TabPojo> liftType) {
		this.liftType = liftType;
	}

	public Map<String, TabPojo> getInjectionType() {
		return injectionType;
	}

	public void setInjectionType(Map<String, TabPojo> injectionType) {
		this.injectionType = injectionType;
	}

}
