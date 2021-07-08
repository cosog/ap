package com.cosog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import oracle.sql.BLOB;

/**
 *  <p>描述：工况类型 实体类  tbl_rpc_worktype</p>
 *  
 * @author gao  2014-06-10
 *
 */
@Entity
@Table(name = "tbl_rpc_worktype")
public class Workstatus implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private Integer gklx;
	private String gkmc;
	private String gksm;
	private BLOB gkmb;
	private String yhjy;
	private String bz;
	private String yhjylgb;
	private int    total;
	// Constructors

	/** default constructor */
	public Workstatus() {
	}

	/** minimal constructor */
	public Workstatus(Integer jlbh, Integer gklx, String gkmc) {
		this.jlbh = jlbh;
		this.gklx = gklx;
		this.gkmc = gkmc;
	}

	/** full constructor */
	public Workstatus(Integer jlbh, Integer gklx, String gkmc, String gksm,
			BLOB gkmb, String yhjy, String bz,String yhjylgb) {
		this.jlbh = jlbh;
		this.gklx = gklx;
		this.gkmc = gkmc;
		this.gksm = gksm;
		this.gkmb = gkmb;
		this.yhjy = yhjy;
		this.yhjylgb=yhjylgb;
		this.bz = bz;
	}

	// Property accessors
	@Id
	@Column(name = "JLBH", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getJlbh() {
		return this.jlbh;
	}

	public void setJlbh(Integer jlbh) {
		this.jlbh = jlbh;
	}

	@Column(name = "GKLX", nullable = false, precision = 22, scale = 0)
	public Integer getGklx() {
		return this.gklx;
	}

	public void setGklx(Integer gklx) {
		this.gklx = gklx;
	}

	@Column(name = "GKMC", nullable = false, length = 200)
	public String getGkmc() {
		return this.gkmc;
	}

	public void setGkmc(String gkmc) {
		this.gkmc = gkmc;
	}

	@Column(name = "GKSM", length = 200)
	public String getGksm() {
		return this.gksm;
	}

	public void setGksm(String gksm) {
		this.gksm = gksm;
	}

	@Column(name = "GKMB")
	public BLOB getGkmb() {
		return this.gkmb;
	}

	public void setGkmb(BLOB gkmb) {
		this.gkmb = gkmb;
	}

	@Column(name = "YHJY", length = 200)
	public String getYhjy() {
		return this.yhjy;
	}

	public void setYhjy(String yhjy) {
		this.yhjy = yhjy;
	}
	
	@Column(name = "YHJYLGB", length = 200)
	public String getYhjylgb() {
		return this.yhjylgb;
	}

	public void setYhjylgb(String yhjylgb) {
		this.yhjylgb = yhjylgb;
	}

	@Column(name = "BZ", length = 200)
	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Transient
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}