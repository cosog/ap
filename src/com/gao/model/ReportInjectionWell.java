package com.gao.model;

import java.lang.Integer;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 生产报表-注入井
 * V_005_21_ProductionReport_CN entity. 
 * @author ding 
 */
@Entity
@Table(name= "V_005_21_ProductionReport_CN")
public class ReportInjectionWell implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String jh;
	private Date jssj;
	private Double rpzsl;
	private Double sjzsl;
	private String bz;
	private String dwbh;

	// Constructors

	/** default constructor */
	public ReportInjectionWell() {
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

	@Temporal(TemporalType.DATE)
	@Column(name = "JSSJ", length = 7)
	public Date getJssj() {
		return this.jssj;
	}

	public void setJssj(Date jssj) {
		this.jssj = jssj;
	}

	@Column(name = "RPZSL", precision = 8)
	public Double getRpzsl() {
		return this.rpzsl;
	}

	public void setRpzsl(Double rpzsl) {
		this.rpzsl = rpzsl;
	}

	@Column(name = "SJZSL", precision = 8)
	public Double getSjzsl() {
		return this.sjzsl;
	}

	public void setSjzsl(Double sjzsl) {
		this.sjzsl = sjzsl;
	}

	@Column(name = "BZ", length = 200)
	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	@Column(name = "DWBH", length = 200)
	public String getDwbh() {
		return dwbh;
	}

	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}

	
}