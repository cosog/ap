package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 抽油机冲刺
 * Strokefrequency entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_STROKEFREQUENCY")
public class Strokefrequency implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private Integer cyjbh;
	private Double cc1;
	private Double djpdlzj;

	// Constructors

	/** default constructor */
	public Strokefrequency() {
	}

	/** minimal constructor */
	public Strokefrequency(Integer jlbh) {
		this.jlbh = jlbh;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "JLBH", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getJlbh() {
		return this.jlbh;
	}

	public void setJlbh(Integer jlbh) {
		this.jlbh = jlbh;
	}

	@Column(name = "CYJBH", precision = 22, scale = 0)
	public Integer getCyjbh() {
		return this.cyjbh;
	}

	public void setCyjbh(Integer cyjbh) {
		this.cyjbh = cyjbh;
	}

	@Column(name = "CC1", precision = 8)
	public Double getCc1() {
		return this.cc1;
	}

	public void setCc1(Double cc1) {
		this.cc1 = cc1;
	}

	@Column(name = "DJPDLZJ", precision = 8)
	public Double getDjpdlzj() {
		return this.djpdlzj;
	}

	public void setDjpdlzj(Double djpdlzj) {
		this.djpdlzj = djpdlzj;
	}

}