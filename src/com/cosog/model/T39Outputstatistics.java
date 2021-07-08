package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_39_OUTPUTSTATISTICS")
public abstract class T39Outputstatistics implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private String SLevel;
	private Integer SCode;
	private Integer SMin;
	private Integer SMax;
	/** default constructor */
	public T39Outputstatistics() {
	}

	/** minimal constructor */
	public T39Outputstatistics(Integer jlbh) {
		this.jlbh = jlbh;
	}

	/** full constructor */
	public T39Outputstatistics(Integer jlbh, String SLevel, Integer SCode,
			Integer SMin, Integer SMax) {
		this.jlbh = jlbh;
		this.SLevel = SLevel;
		this.SCode = SCode;
		this.SMin = SMin;
		this.SMax = SMax;
	}

	@Id
	@GeneratedValue
	@Column(name = "JLBH", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getJlbh() {
		return this.jlbh;
	}

	public void setJlbh(Integer jlbh) {
		this.jlbh = jlbh;
	}

	@Column(name = "S_LEVEL", length = 50)
	public String getSLevel() {
		return this.SLevel;
	}

	public void setSLevel(String SLevel) {
		this.SLevel = SLevel;
	}

	@Column(name = "S_CODE", precision = 22, scale = 0)
	public Integer getSCode() {
		return this.SCode;
	}

	public void setSCode(Integer SCode) {
		this.SCode = SCode;
	}

	@Column(name = "S_MIN", precision = 22, scale = 0)
	public Integer getSMin() {
		return this.SMin;
	}

	public void setSMin(Integer SMin) {
		this.SMin = SMin;
	}

	@Column(name = "S_MAX", precision = 22, scale = 0)
	public Integer getSMax() {
		return this.SMax;
	}

	public void setSMax(Integer SMax) {
		this.SMax = SMax;
	}

}