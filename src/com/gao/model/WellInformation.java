package com.gao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 *  <p>描述：井名信息 实体类  tbl_wellinformation</p>
 *  
 * @author zhao  2020-02-17
 *
 */
@Entity
@Table(name = "tbl_wellinformation")
public class WellInformation implements java.io.Serializable {
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
	private String orgName;
	private String resName;
	private String jlxName;
	private String ssjwName;
	private String sszcdyName;
	private String threadid;

	private String jh;
	private Integer jlx;
	private Integer ssjw;
	private Integer sszcdy;
	private String rgzsjd;
	private Double rgzsj;
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
	private Integer ccjqtlx;
	private Integer ccjzt;
	private Integer jslx;
	private Integer sfpfcl;
	private Integer showlevel;

	// Constructors
	/** default constructor */
	public WellInformation() {
	}

	/** minimal constructor */
	public WellInformation(Integer jlbh, String jh) {
		this.jlbh = jlbh;
		this.jh = jh;
	}

	/** full constructor */
	public WellInformation(Integer jlbh, String dwbh, String yqcbh, String jz,
			String jc, String jhh, String jh, Integer jlx, Integer ssjw,
			Integer sszcdy, String rgzsjd, Double dmx, Double dmy, Double dmz,
			Double bxx, Double bxy, Double bxz, String dmjd, String dmwd,
			Double dmgc, String bxjd, String bxwd, Double bxgc,
			Integer ccjqtlx, Integer ccjzt, Integer jslx, String rgzsj,
			Integer sfpfcl) {
		this.jlbh = jlbh;
		this.dwbh = dwbh;
		this.yqcbh = yqcbh;
		this.jz = jz;
		this.jc = jc;
		this.jhh = jhh;
		this.jh = jh;
		this.jlx = jlx;
		this.ssjw = ssjw;
		this.sszcdy = sszcdy;
		this.rgzsjd = rgzsjd;
		this.dmx = dmx;
		this.dmy = dmy;
		this.dmz = dmz;
		this.bxx = bxx;
		this.bxy = bxy;
		this.bxz = bxz;
		this.dmjd = dmjd;
		this.dmwd = dmwd;
		this.dmgc = dmgc;
		this.bxjd = bxjd;
		this.bxwd = bxwd;
		this.bxgc = bxgc;
		this.ccjqtlx = ccjqtlx;
		this.ccjzt = ccjzt;
		this.jslx = jslx;
		this.sfpfcl = sfpfcl;
	}

	@Transient
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Transient
	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
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

	@Column(name = "DWBH", length = 200)
	public String getDwbh() {
		return this.dwbh;
	}

	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}

	@Column(name = "YQCBH", length = 200)
	public String getYqcbh() {
		return this.yqcbh;
	}

	public void setYqcbh(String yqcbh) {
		this.yqcbh = yqcbh;
	}

	@Column(name = "JZ", length = 200)
	public String getJz() {
		return this.jz;
	}

	public void setJz(String jz) {
		this.jz = jz;
	}

	@Column(name = "JC", length = 200)
	public String getJc() {
		return this.jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	@Column(name = "JHH", length = 200)
	public String getJhh() {
		return this.jhh;
	}

	public void setJhh(String jhh) {
		this.jhh = jhh;
	}

	@Column(name = "JH", nullable = false, length = 200)
	public String getJh() {
		return this.jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	@Column(name = "JLX", precision = 22, scale = 0)
	public Integer getJlx() {
		return this.jlx;
	}

	public void setJlx(Integer jlx) {
		this.jlx = jlx;
	}

	@Column(name = "SSJW", precision = 22, scale = 0)
	public Integer getSsjw() {
		return this.ssjw;
	}

	public void setSsjw(Integer ssjw) {
		this.ssjw = ssjw;
	}

	@Column(name = "SSZCDY", precision = 22, scale = 0)
	public Integer getSszcdy() {
		return this.sszcdy;
	}

	public void setSszcdy(Integer sszcdy) {
		this.sszcdy = sszcdy;
	}

	@Column(name = "RGZSJD", length = 200)
	public String getRgzsjd() {
		return this.rgzsjd;
	}

	public void setRgzsjd(String rgzsjd) {
		this.rgzsjd = rgzsjd;
	}

	@Column(name = "DMX", precision = 8)
	public Double getDmx() {
		return this.dmx;
	}

	public void setDmx(Double dmx) {
		this.dmx = dmx;
	}

	@Column(name = "DMY", precision = 8)
	public Double getDmy() {
		return this.dmy;
	}

	public void setDmy(Double dmy) {
		this.dmy = dmy;
	}

	@Column(name = "DMZ", precision = 8)
	public Double getDmz() {
		return this.dmz;
	}

	public void setDmz(Double dmz) {
		this.dmz = dmz;
	}

	@Column(name = "BXX", precision = 8)
	public Double getBxx() {
		return this.bxx;
	}

	public void setBxx(Double bxx) {
		this.bxx = bxx;
	}

	@Column(name = "BXY", precision = 8)
	public Double getBxy() {
		return this.bxy;
	}

	public void setBxy(Double bxy) {
		this.bxy = bxy;
	}

	@Column(name = "BXZ", precision = 8)
	public Double getBxz() {
		return this.bxz;
	}

	public void setBxz(Double bxz) {
		this.bxz = bxz;
	}

	@Column(name = "DMJD", length = 12)
	public String getDmjd() {
		return this.dmjd;
	}

	public void setDmjd(String dmjd) {
		this.dmjd = dmjd;
	}

	@Column(name = "DMWD", length = 12)
	public String getDmwd() {
		return this.dmwd;
	}

	public void setDmwd(String dmwd) {
		this.dmwd = dmwd;
	}

	@Column(name = "DMGC", precision = 8)
	public Double getDmgc() {
		return this.dmgc;
	}

	public void setDmgc(Double dmgc) {
		this.dmgc = dmgc;
	}

	@Column(name = "BXJD", length = 12)
	public String getBxjd() {
		return this.bxjd;
	}

	public void setBxjd(String bxjd) {
		this.bxjd = bxjd;
	}

	@Column(name = "BXWD", length = 12)
	public String getBxwd() {
		return this.bxwd;
	}

	public void setBxwd(String bxwd) {
		this.bxwd = bxwd;
	}

	@Column(name = "BXGC", precision = 8)
	public Double getBxgc() {
		return this.bxgc;
	}

	public void setBxgc(Double bxgc) {
		this.bxgc = bxgc;
	}

	@Column(name = "CCJQTLX", precision = 10, scale = 0)
	public Integer getCcjqtlx() {
		return this.ccjqtlx;
	}

	public void setCcjqtlx(Integer ccjqtlx) {
		this.ccjqtlx = ccjqtlx;
	}

	@Column(name = "CCJZT", precision = 10, scale = 0)
	public Integer getCcjzt() {
		return this.ccjzt;
	}

	public void setCcjzt(Integer ccjzt) {
		this.ccjzt = ccjzt;
	}

	@Column(name = "JSLX", precision = 10, scale = 0)
	public Integer getJslx() {
		return this.jslx;
	}

	public void setJslx(Integer jslx) {
		this.jslx = jslx;
	}

	public Double getRgzsj() {
		return rgzsj;
	}

	public void setRgzsj(Double rgzsj) {
		this.rgzsj = rgzsj;
	}

	@Column(name = "SFPFCL", precision = 10, scale = 0)
	public Integer getSfpfcl() {
		return this.sfpfcl;
	}

	public void setSfpfcl(Integer sfpfcl) {
		this.sfpfcl = sfpfcl;
	}

	@Column(name = "SHOW_LEVEL", precision = 10, scale = 0)
	public Integer getShowlevel() {
		return showlevel;
	}

	public void setShowlevel(Integer showlevel) {
		this.showlevel = showlevel;
	}

	@Transient
	public String getJlxName() {
		return jlxName;
	}

	public void setJlxName(String jlxName) {
		this.jlxName = jlxName;
	}

	@Transient
	public String getSsjwName() {
		return ssjwName;
	}

	public void setSsjwName(String ssjwName) {
		this.ssjwName = ssjwName;
	}

	@Transient
	public String getSszcdyName() {
		return sszcdyName;
	}

	public void setSszcdyName(String sszcdyName) {
		this.sszcdyName = sszcdyName;
	}

	@Transient
	public String getThreadid() {
		return threadid;
	}

	public void setThreadid(String threadid) {
		this.threadid = threadid;
	}

}