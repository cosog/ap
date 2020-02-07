package com.gao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_002_21_DYNMONITOR_CN_PARAM")
public class MonitorWaterInjectionWellParams implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String jh;
	private Integer dm;
	private String bjlx;
	private String dwbh;

	// Constructors
	@Column(name = "dwbh", nullable = false, length = 200)
	public String getDwbh() {
		return dwbh;
	}

	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}

	/** default constructor */
	public MonitorWaterInjectionWellParams() {
	}

	/** minimal constructor */
	public MonitorWaterInjectionWellParams(Integer id, String jh) {
		this.id = id;
		this.jh = jh;
	}

	/** full constructor */
	public MonitorWaterInjectionWellParams(Integer id, String jh, Integer dm,
			String bjlx) {
		this.id = id;
		this.jh = jh;
		this.dm = dm;
		this.bjlx = bjlx;
	}

	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "JH", nullable = false, length = 200)
	public String getJh() {
		return this.jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	@Column(name = "DM", precision = 22, scale = 0)
	public Integer getDm() {
		return this.dm;
	}

	public void setDm(Integer dm) {
		this.dm = dm;
	}

	@Column(name = "BJLX", length = 200)
	public String getBjlx() {
		return this.bjlx;
	}

	public void setBjlx(String bjlx) {
		this.bjlx = bjlx;
	}

}