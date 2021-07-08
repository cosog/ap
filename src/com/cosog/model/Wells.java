package com.cosog.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：井名信息 实体类  tbl_wellinformation</p>
 *  
 * @author zhao  2020-02-17
 *
 */
@Entity
@Table(name = "tbl_wellinformation")
public class Wells implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private String dwbh;
	private String yqcbh;
	private String jz;
	private String jc;
	private String jhh;
	private String jh;
	private BigDecimal jlx;
	private BigDecimal ssjw;
	private BigDecimal sszcdy;
	private String rgzsjd;
	private Double dmx;
	private Double dmy;
	private Double dmz;
	private Double bxx;
	private Double bxy;
	private Double bxz;
	private String dmjd;
	private String dmwd;
	private Double dmgc;
	private String bxjd;
	private String bxwd;
	private Double bxgc;
	private Long ccjqtlx;
	private Long ccjzt;
	private Long jslx;
	private String rgzsj;
	private Long sfpfcl;

	public String getDwbh() {
		return dwbh;
	}

	@Id
	@GeneratedValue
	public Integer getJlbh() {
		return jlbh;
	}

	public void setJlbh(Integer jlbh) {
		this.jlbh = jlbh;
	}

	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}

	public String getYqcbh() {
		return yqcbh;
	}

	public void setYqcbh(String yqcbh) {
		this.yqcbh = yqcbh;
	}

	public String getJz() {
		return jz;
	}

	public void setJz(String jz) {
		this.jz = jz;
	}

	public String getJc() {
		return jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	public String getJhh() {
		return jhh;
	}

	public void setJhh(String jhh) {
		this.jhh = jhh;
	}

	public String getJh() {
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public BigDecimal getJlx() {
		return jlx;
	}

	public void setJlx(BigDecimal jlx) {
		this.jlx = jlx;
	}

	public BigDecimal getSsjw() {
		return ssjw;
	}

	public void setSsjw(BigDecimal ssjw) {
		this.ssjw = ssjw;
	}

	public BigDecimal getSszcdy() {
		return sszcdy;
	}

	public void setSszcdy(BigDecimal sszcdy) {
		this.sszcdy = sszcdy;
	}

	public String getRgzsjd() {
		return rgzsjd;
	}

	public void setRgzsjd(String rgzsjd) {
		this.rgzsjd = rgzsjd;
	}

	public Double getDmx() {
		return dmx;
	}

	public void setDmx(Double dmx) {
		this.dmx = dmx;
	}

	public Double getDmy() {
		return dmy;
	}

	public void setDmy(Double dmy) {
		this.dmy = dmy;
	}

	public Double getDmz() {
		return dmz;
	}

	public void setDmz(Double dmz) {
		this.dmz = dmz;
	}

	public Double getBxx() {
		return bxx;
	}

	public void setBxx(Double bxx) {
		this.bxx = bxx;
	}

	public Double getBxy() {
		return bxy;
	}

	public void setBxy(Double bxy) {
		this.bxy = bxy;
	}

	public Double getBxz() {
		return bxz;
	}

	public void setBxz(Double bxz) {
		this.bxz = bxz;
	}

	public String getDmjd() {
		return dmjd;
	}

	public void setDmjd(String dmjd) {
		this.dmjd = dmjd;
	}

	public String getDmwd() {
		return dmwd;
	}

	public void setDmwd(String dmwd) {
		this.dmwd = dmwd;
	}

	public Double getDmgc() {
		return dmgc;
	}

	public void setDmgc(Double dmgc) {
		this.dmgc = dmgc;
	}

	public String getBxjd() {
		return bxjd;
	}

	public void setBxjd(String bxjd) {
		this.bxjd = bxjd;
	}

	public String getBxwd() {
		return bxwd;
	}

	public void setBxwd(String bxwd) {
		this.bxwd = bxwd;
	}

	@Column(name = "bxgc")
	public Double getBxgc() {
		return bxgc;
	}

	public void setBxgc(Double bxgc) {
		this.bxgc = bxgc;
	}

	public Long getCcjqtlx() {
		return ccjqtlx;
	}

	public void setCcjqtlx(Long ccjqtlx) {
		this.ccjqtlx = ccjqtlx;
	}

	public Long getCcjzt() {
		return ccjzt;
	}

	public void setCcjzt(Long ccjzt) {
		this.ccjzt = ccjzt;
	}

	public Long getJslx() {
		return jslx;
	}

	public void setJslx(Long jslx) {
		this.jslx = jslx;
	}

	public String getRgzsj() {
		return rgzsj;
	}

	public void setRgzsj(String rgzsj) {
		this.rgzsj = rgzsj;
	}

	public Long getSfpfcl() {
		return sfpfcl;
	}

	public void setSfpfcl(Long sfpfcl) {
		this.sfpfcl = sfpfcl;
	}
}
