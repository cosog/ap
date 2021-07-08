package com.cosog.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_002_11_DYNMONITOR_CN_PARAM")
public class MonitorPumpingUnitParams implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String bjlx;

	private Integer dm;

	private Integer id;

	private String jh;

	private String jhh;
	private String dwbh;

	// Constructors
	@Column(name = "dwbh", nullable = false, length = 200)
	public String getDwbh() {
		return dwbh;
	}

	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}

	public String getBjlx() {
		return bjlx;
	}

	public Integer getDm() {
		return dm;
	}

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public String getJh() {
		return jh;
	}

	public String getJhh() {
		return jhh;
	}

	public void setBjlx(String bjlx) {
		this.bjlx = bjlx;
	}

	public void setDm(Integer dm) {
		this.dm = dm;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public void setJhh(String jhh) {
		this.jhh = jhh;
	}
}
