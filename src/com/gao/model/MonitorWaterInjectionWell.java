package com.gao.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * V00221DynamicmonitoringCnId entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "V_002_21_DYNAMICMONITORING_CN")
public class MonitorWaterInjectionWell implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal id;
	private String bjlx;
	private String jh;
	private String zrfs;
	private Date cjsj;
	private String zdjg;
	private String yhjy;
	private Double rpzrl;
	private Double sjrzrl;
	private Double ssll;
	private Double ljll;
	private Double jkzryl;
	private Double ty;
	private Double jklw;
	private String dwbh;

	// Constructors

	/** default constructor */
	public MonitorWaterInjectionWell() {
	}

	/** minimal constructor */
	public MonitorWaterInjectionWell(BigDecimal id, String jh, String zdjg) {
		this.id = id;
		this.jh = jh;
		this.zdjg = zdjg;
	}

	/** full constructor */
	public MonitorWaterInjectionWell(BigDecimal id, String bjlx, String jh,
			String zrfs, Date cjsj, String zdjg, String yhjy, Double rpzrl,
			Double sjrzrl, Double ssll, Double ljll, Double jkzryl, Double ty,
			Double jklw, String dwbh) {
		this.id = id;
		this.bjlx = bjlx;
		this.jh = jh;
		this.zrfs = zrfs;
		this.cjsj = cjsj;
		this.zdjg = zdjg;
		this.yhjy = yhjy;
		this.rpzrl = rpzrl;
		this.sjrzrl = sjrzrl;
		this.ssll = ssll;
		this.ljll = ljll;
		this.jkzryl = jkzryl;
		this.ty = ty;
		this.jklw = jklw;
		this.dwbh = dwbh;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false, precision = 22, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@Column(name = "BJLX", length = 200)
	public String getBjlx() {
		return this.bjlx;
	}

	public void setBjlx(String bjlx) {
		this.bjlx = bjlx;
	}

	@Column(name = "JH", nullable = false, length = 200)
	public String getJh() {
		return this.jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	@Column(name = "ZRFS", length = 200)
	public String getZrfs() {
		return this.zrfs;
	}

	public void setZrfs(String zrfs) {
		this.zrfs = zrfs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CJSJ", length = 7)
	public Date getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	@Column(name = "ZDJG", nullable = false, length = 200)
	public String getZdjg() {
		return this.zdjg;
	}

	public void setZdjg(String zdjg) {
		this.zdjg = zdjg;
	}

	@Column(name = "YHJY", length = 200)
	public String getYhjy() {
		return this.yhjy;
	}

	public void setYhjy(String yhjy) {
		this.yhjy = yhjy;
	}

	@Column(name = "RPZRL", precision = 8)
	public Double getRpzrl() {
		return this.rpzrl;
	}

	public void setRpzrl(Double rpzrl) {
		this.rpzrl = rpzrl;
	}

	@Column(name = "SJRZRL", precision = 8)
	public Double getSjrzrl() {
		return this.sjrzrl;
	}

	public void setSjrzrl(Double sjrzrl) {
		this.sjrzrl = sjrzrl;
	}

	@Column(name = "SSLL", precision = 8)
	public Double getSsll() {
		return this.ssll;
	}

	public void setSsll(Double ssll) {
		this.ssll = ssll;
	}

	@Column(name = "LJLL", precision = 8)
	public Double getLjll() {
		return this.ljll;
	}

	public void setLjll(Double ljll) {
		this.ljll = ljll;
	}

	@Column(name = "JKZRYL", precision = 8)
	public Double getJkzryl() {
		return this.jkzryl;
	}

	public void setJkzryl(Double jkzryl) {
		this.jkzryl = jkzryl;
	}

	@Column(name = "TY", precision = 8)
	public Double getTy() {
		return this.ty;
	}

	public void setTy(Double ty) {
		this.ty = ty;
	}

	@Column(name = "JKLW", precision = 8)
	public Double getJklw() {
		return this.jklw;
	}

	public void setJklw(Double jklw) {
		this.jklw = jklw;
	}

	@Column(name = "DWBH", length = 200)
	public String getDwbh() {
		return this.dwbh;
	}

	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MonitorWaterInjectionWell))
			return false;
		MonitorWaterInjectionWell castOther = (MonitorWaterInjectionWell) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getBjlx() == castOther.getBjlx()) || (this.getBjlx() != null
						&& castOther.getBjlx() != null && this.getBjlx()
						.equals(castOther.getBjlx())))
				&& ((this.getJh() == castOther.getJh()) || (this.getJh() != null
						&& castOther.getJh() != null && this.getJh().equals(
						castOther.getJh())))
				&& ((this.getZrfs() == castOther.getZrfs()) || (this.getZrfs() != null
						&& castOther.getZrfs() != null && this.getZrfs()
						.equals(castOther.getZrfs())))
				&& ((this.getCjsj() == castOther.getCjsj()) || (this.getCjsj() != null
						&& castOther.getCjsj() != null && this.getCjsj()
						.equals(castOther.getCjsj())))
				&& ((this.getZdjg() == castOther.getZdjg()) || (this.getZdjg() != null
						&& castOther.getZdjg() != null && this.getZdjg()
						.equals(castOther.getZdjg())))
				&& ((this.getYhjy() == castOther.getYhjy()) || (this.getYhjy() != null
						&& castOther.getYhjy() != null && this.getYhjy()
						.equals(castOther.getYhjy())))
				&& ((this.getRpzrl() == castOther.getRpzrl()) || (this
						.getRpzrl() != null && castOther.getRpzrl() != null && this
						.getRpzrl().equals(castOther.getRpzrl())))
				&& ((this.getSjrzrl() == castOther.getSjrzrl()) || (this
						.getSjrzrl() != null && castOther.getSjrzrl() != null && this
						.getSjrzrl().equals(castOther.getSjrzrl())))
				&& ((this.getSsll() == castOther.getSsll()) || (this.getSsll() != null
						&& castOther.getSsll() != null && this.getSsll()
						.equals(castOther.getSsll())))
				&& ((this.getLjll() == castOther.getLjll()) || (this.getLjll() != null
						&& castOther.getLjll() != null && this.getLjll()
						.equals(castOther.getLjll())))
				&& ((this.getJkzryl() == castOther.getJkzryl()) || (this
						.getJkzryl() != null && castOther.getJkzryl() != null && this
						.getJkzryl().equals(castOther.getJkzryl())))
				&& ((this.getTy() == castOther.getTy()) || (this.getTy() != null
						&& castOther.getTy() != null && this.getTy().equals(
						castOther.getTy())))
				&& ((this.getJklw() == castOther.getJklw()) || (this.getJklw() != null
						&& castOther.getJklw() != null && this.getJklw()
						.equals(castOther.getJklw())))
				&& ((this.getDwbh() == castOther.getDwbh()) || (this.getDwbh() != null
						&& castOther.getDwbh() != null && this.getDwbh()
						.equals(castOther.getDwbh())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getBjlx() == null ? 0 : this.getBjlx().hashCode());
		result = 37 * result + (getJh() == null ? 0 : this.getJh().hashCode());
		result = 37 * result
				+ (getZrfs() == null ? 0 : this.getZrfs().hashCode());
		result = 37 * result
				+ (getCjsj() == null ? 0 : this.getCjsj().hashCode());
		result = 37 * result
				+ (getZdjg() == null ? 0 : this.getZdjg().hashCode());
		result = 37 * result
				+ (getYhjy() == null ? 0 : this.getYhjy().hashCode());
		result = 37 * result
				+ (getRpzrl() == null ? 0 : this.getRpzrl().hashCode());
		result = 37 * result
				+ (getSjrzrl() == null ? 0 : this.getSjrzrl().hashCode());
		result = 37 * result
				+ (getSsll() == null ? 0 : this.getSsll().hashCode());
		result = 37 * result
				+ (getLjll() == null ? 0 : this.getLjll().hashCode());
		result = 37 * result
				+ (getJkzryl() == null ? 0 : this.getJkzryl().hashCode());
		result = 37 * result + (getTy() == null ? 0 : this.getTy().hashCode());
		result = 37 * result
				+ (getJklw() == null ? 0 : this.getJklw().hashCode());
		result = 37 * result
				+ (getDwbh() == null ? 0 : this.getDwbh().hashCode());
		return result;
	}

}