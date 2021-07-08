package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *  <p>描述：井身轨迹 实体类  T_WELLTRAJECTORY</p>
 *  
 * @author gao  2014-06-10
 *
 */
@Entity
@Table(name = "T_WELLTRAJECTORY")
public class WellTrajectory implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private Integer jbh;
	private String jsgj;
	private String jh;
	private String clsd;
	private String czsd;
	private String jxj;
	private String fwj;

	// Constructors

	/** default constructor */
	public WellTrajectory() {
	}

	/** full constructor */
	public WellTrajectory(Integer jbh, String jsgj) {
		this.jbh = jbh;
		this.jsgj = jsgj;
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

	@Column(name = "JBH", length = 200)
	public Integer getJbh() {
		return this.jbh;
	}

	public void setJbh(Integer jbh) {
		this.jbh = jbh;
	}

	@Column(name = "JSGJ")
	public String getJsgj() {
		return this.jsgj;
	}

	public void setJsgj(String jsgj) {
		this.jsgj = jsgj;
	}

	@Transient
	public String getJh() {
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	@Transient
	public String getClsd() {
		return clsd;
	}

	public void setClsd(String clsd) {
		this.clsd = clsd;
	}

	@Transient
	public String getCzsd() {
		return czsd;
	}

	public void setCzsd(String czsd) {
		this.czsd = czsd;
	}

	@Transient
	public String getJxj() {
		return jxj;
	}

	public void setJxj(String jxj) {
		this.jxj = jxj;
	}

	@Transient
	public String getFwj() {
		return fwj;
	}

	public void setFwj(String fwj) {
		this.fwj = fwj;
	}
}