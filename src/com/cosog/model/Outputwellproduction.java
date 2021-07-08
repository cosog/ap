package com.cosog.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Outputwellproduction entity. @ding
 */
@Entity
@Table(name = "tbl_rpc_productiondata_hist")
public class Outputwellproduction implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private Integer jbh;
	//private String jh;
	private Date cjsj;
	private Integer jslx;
	private Integer qtlx;
	private Integer sfpfcl;
	private Integer ccjzt;
	private Double rcyl;
	private Double hsl;
	private Double rcql;
	private Double zyyzzj;
	private Double cyyzzj;
	private Double yy;
	private Double ty;
	private Double hy;
	private Double jxyljwz;
	private Double jxyljyl;
	private Double jxwdjwz;
	private Double jxwdjwd;
	private Double jxyzwz;
	private Double jxyzzj;
	private Double dymbh;
	private Double bg;
	private Double jklw;
	private Double scqyb;
	private Integer bbh;
	private Double ygnj;
	private Double yctgnj;
	private Double yjgj;
	private Integer yjgjb;
	private Double yjgcd;
	private Double ejgj;
	private Integer ejgjb;
	private Double ejgcd;
	private Double sjgj;
	private Integer sjgjb;
	private Double sjgcd;
	private Integer mdzt;
	private Double qmflxl;
	private Double qtssll;
	private Double qtljll;
	private Double qtyl;
	private Double qtwd;
	private Double csssll;
	private Double csljll;
	private Double csyl;
	private Double cswd;
	private Double jmb;
	private Integer bzgtbh;
	private Integer bzdntbh;
	private String bz;

	public Outputwellproduction() {
	}
	
	@GeneratedValue
	@Id
	@Column(name = "JLBH", nullable = false, precision = 22, scale = 0)
	public Integer getJlbh() {
		return this.jlbh;
	}

	public void setJlbh(Integer jlbh) {
		this.jlbh = jlbh;
	}

	@Column(name = "JBH", nullable = false, precision = 22, scale = 0)
	public Integer getJbh() {
		return this.jbh;
	}

	public void setJbh(Integer jbh) {
		this.jbh = jbh;
	}
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cjsj" ,length='7' )
	public Date getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	@Column(name = "JSLX", precision = 22, scale = 0)
	public Integer getJslx() {
		return this.jslx;
	}

	public void setJslx(Integer jslx) {
		this.jslx = jslx;
	}

	@Column(name = "QTLX", precision = 22, scale = 0)
	public Integer getQtlx() {
		return this.qtlx;
	}

	public void setQtlx(Integer qtlx) {
		this.qtlx = qtlx;
	}

	@Column(name = "SFPFCL", precision = 22, scale = 0)
	public Integer getSfpfcl() {
		return this.sfpfcl;
	}

	public void setSfpfcl(Integer sfpfcl) {
		this.sfpfcl = sfpfcl;
	}

	@Column(name = "CCJZT", precision = 22, scale = 0)
	public Integer getCcjzt() {
		return this.ccjzt;
	}

	public void setCcjzt(Integer ccjzt) {
		this.ccjzt = ccjzt;
	}

	@Column(name = "RCYL", precision = 8)
	public Double getRcyl() {
		return this.rcyl;
	}

	public void setRcyl(Double rcyl) {
		this.rcyl = rcyl;
	}

	@Column(name = "HSL", precision = 8)
	public Double getHsl() {
		return this.hsl;
	}

	public void setHsl(Double hsl) {
		this.hsl = hsl;
	}

	@Column(name = "RCQL", precision = 8)
	public Double getRcql() {
		return this.rcql;
	}

	public void setRcql(Double rcql) {
		this.rcql = rcql;
	}

	@Column(name = "ZYYZZJ", precision = 8)
	public Double getZyyzzj() {
		return this.zyyzzj;
	}

	public void setZyyzzj(Double zyyzzj) {
		this.zyyzzj = zyyzzj;
	}

	@Column(name = "CYYZZJ", precision = 8)
	public Double getCyyzzj() {
		return this.cyyzzj;
	}

	public void setCyyzzj(Double cyyzzj) {
		this.cyyzzj = cyyzzj;
	}

	@Column(name = "YY", precision = 8)
	public Double getYy() {
		return this.yy;
	}

	public void setYy(Double yy) {
		this.yy = yy;
	}

	@Column(name = "TY", precision = 8)
	public Double getTy() {
		return this.ty;
	}

	public void setTy(Double ty) {
		this.ty = ty;
	}

	@Column(name = "HY", precision = 8)
	public Double getHy() {
		return this.hy;
	}

	public void setHy(Double hy) {
		this.hy = hy;
	}

	@Column(name = "JXYLJWZ", precision = 8)
	public Double getJxyljwz() {
		return this.jxyljwz;
	}

	public void setJxyljwz(Double jxyljwz) {
		this.jxyljwz = jxyljwz;
	}

	@Column(name = "JXYLJYL", precision = 8)
	public Double getJxyljyl() {
		return this.jxyljyl;
	}

	public void setJxyljyl(Double jxyljyl) {
		this.jxyljyl = jxyljyl;
	}

	@Column(name = "JXWDJWZ", precision = 8)
	public Double getJxwdjwz() {
		return this.jxwdjwz;
	}

	public void setJxwdjwz(Double jxwdjwz) {
		this.jxwdjwz = jxwdjwz;
	}

	@Column(name = "JXWDJWD", precision = 8)
	public Double getJxwdjwd() {
		return this.jxwdjwd;
	}

	public void setJxwdjwd(Double jxwdjwd) {
		this.jxwdjwd = jxwdjwd;
	}

	@Column(name = "JXYZWZ", precision = 8)
	public Double getJxyzwz() {
		return this.jxyzwz;
	}

	public void setJxyzwz(Double jxyzwz) {
		this.jxyzwz = jxyzwz;
	}

	@Column(name = "JXYZZJ", precision = 8)
	public Double getJxyzzj() {
		return this.jxyzzj;
	}

	public void setJxyzzj(Double jxyzzj) {
		this.jxyzzj = jxyzzj;
	}

	@Column(name = "DYMBH", precision = 8)
	public Double getDymbh() {
		return this.dymbh;
	}

	public void setDymbh(Double dymbh) {
		this.dymbh = dymbh;
	}

	@Column(name = "BG", precision = 8)
	public Double getBg() {
		return this.bg;
	}

	public void setBg(Double bg) {
		this.bg = bg;
	}

	@Column(name = "JKLW", precision = 8)
	public Double getJklw() {
		return this.jklw;
	}

	public void setJklw(Double jklw) {
		this.jklw = jklw;
	}

	@Column(name = "SCQYB", precision = 8)
	public Double getScqyb() {
		return this.scqyb;
	}

	public void setScqyb(Double scqyb) {
		this.scqyb = scqyb;
	}

	@Column(name = "BBH", precision = 22, scale = 0)
	public Integer getBbh() {
		return this.bbh;
	}

	public void setBbh(Integer bbh) {
		this.bbh = bbh;
	}

	@Column(name = "YGNJ", precision = 8)
	public Double getYgnj() {
		return this.ygnj;
	}

	public void setYgnj(Double ygnj) {
		this.ygnj = ygnj;
	}

	@Column(name = "YCTGNJ", precision = 8)
	public Double getyctgnj() {
		return this.yctgnj;
	}

	public void setyctgnj(Double yctgnj) {
		this.yctgnj = yctgnj;
	}

	@Column(name = "YJGJ", precision = 8)
	public Double getYjgj() {
		return this.yjgj;
	}

	public void setYjgj(Double yjgj) {
		this.yjgj = yjgj;
	}

	@Column(name = "YJGJB", precision = 22, scale = 0)
	public Integer getYjgjb() {
		return this.yjgjb;
	}

	public void setYjgjb(Integer yjgjb) {
		this.yjgjb = yjgjb;
	}

	@Column(name = "YJGCD", precision = 8)
	public Double getYjgcd() {
		return this.yjgcd;
	}

	public void setYjgcd(Double yjgcd) {
		this.yjgcd = yjgcd;
	}

	@Column(name = "EJGJ", precision = 8)
	public Double getEjgj() {
		return this.ejgj;
	}

	public void setEjgj(Double ejgj) {
		this.ejgj = ejgj;
	}

	@Column(name = "EJGJB", precision = 22, scale = 0)
	public Integer getEjgjb() {
		return this.ejgjb;
	}

	public void setEjgjb(Integer ejgjb) {
		this.ejgjb = ejgjb;
	}

	@Column(name = "EJGCD", precision = 8)
	public Double getEjgcd() {
		return this.ejgcd;
	}

	public void setEjgcd(Double ejgcd) {
		this.ejgcd = ejgcd;
	}

	@Column(name = "SJGJ", precision = 8)
	public Double getSjgj() {
		return this.sjgj;
	}

	public void setSjgj(Double sjgj) {
		this.sjgj = sjgj;
	}

	@Column(name = "SJGJB", precision = 22, scale = 0)
	public Integer getSjgjb() {
		return this.sjgjb;
	}

	public void setSjgjb(Integer sjgjb) {
		this.sjgjb = sjgjb;
	}

	@Column(name = "SJGCD", precision = 8)
	public Double getSjgcd() {
		return this.sjgcd;
	}

	public void setSjgcd(Double sjgcd) {
		this.sjgcd = sjgcd;
	}

	@Column(name = "MDZT", precision = 22, scale = 0)
	public Integer getMdzt() {
		return this.mdzt;
	}

	public void setMdzt(Integer mdzt) {
		this.mdzt = mdzt;
	}

	@Column(name = "QMFLXL", precision = 8)
	public Double getQmflxl() {
		return this.qmflxl;
	}

	public void setQmflxl(Double qmflxl) {
		this.qmflxl = qmflxl;
	}

	@Column(name = "QTSSLL", precision = 8)
	public Double getQtssll() {
		return this.qtssll;
	}

	public void setQtssll(Double qtssll) {
		this.qtssll = qtssll;
	}

	@Column(name = "QTLJLL", precision = 8)
	public Double getQtljll() {
		return this.qtljll;
	}

	public void setQtljll(Double qtljll) {
		this.qtljll = qtljll;
	}

	@Column(name = "QTYL", precision = 8)
	public Double getQtyl() {
		return this.qtyl;
	}

	public void setQtyl(Double qtyl) {
		this.qtyl = qtyl;
	}

	@Column(name = "QTWD", precision = 8)
	public Double getQtwd() {
		return this.qtwd;
	}

	public void setQtwd(Double qtwd) {
		this.qtwd = qtwd;
	}

	@Column(name = "CSSSLL", precision = 8)
	public Double getCsssll() {
		return this.csssll;
	}

	public void setCsssll(Double csssll) {
		this.csssll = csssll;
	}

	@Column(name = "CSLJLL", precision = 8)
	public Double getCsljll() {
		return this.csljll;
	}

	public void setCsljll(Double csljll) {
		this.csljll = csljll;
	}

	@Column(name = "CSYL", precision = 8)
	public Double getCsyl() {
		return this.csyl;
	}

	public void setCsyl(Double csyl) {
		this.csyl = csyl;
	}

	@Column(name = "CSWD", precision = 8)
	public Double getCswd() {
		return this.cswd;
	}

	public void setCswd(Double cswd) {
		this.cswd = cswd;
	}

	@Column(name = "JMB", precision = 8)
	public Double getJmb() {
		return this.jmb;
	}

	public void setJmb(Double jmb) {
		this.jmb = jmb;
	}

	@Column(name = "BZGTBH", precision = 22, scale = 0)
	public Integer getBzgtbh() {
		return this.bzgtbh;
	}

	public void setBzgtbh(Integer bzgtbh) {
		this.bzgtbh = bzgtbh;
	}

	@Column(name = "BZDNTBH", precision = 22, scale = 0)
	public Integer getBzdntbh() {
		return this.bzdntbh;
	}

	public void setBzdntbh(Integer bzdntbh) {
		this.bzdntbh = bzdntbh;
	}

	@Column(name = "BZ", length = 200)
	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	

}