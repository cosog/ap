package com.gao.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_outputwellproduction")
public class ProductionOutWellInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private Integer jbh;
	private String jh="";
	private String sccj="";
	private String cybxh="";
	private Date cjsj;
	private Integer jslx;
	private Integer qtlx;
	private Integer sfpfcl = 0;
	private Integer ccjzt = 0;
	private Double rcyl;
	private Double hsl = 0.0;
	private Double rcql;
	private Double zyyzzj;
	private Double cyyzzj;
	private Double yy = 0.0;
	private Double ty = 0.0;
	private Double hy = 0.0;
	private Double jxyljwz;
	private Double jxyljyl;
	private Double jxwdjwz;
	private Double jxwdjwd;
	private Double jxyzwz;
	private Double jxyzzj;
	private Double dymbh;
	private Double dym=0.0;
	private Double bg = 0.0;
	private Double jklw = 0.0;
	private Double scqyb = 0.0;
	private Integer bj = 0;
	private Integer bjb = 0;
	private Double zsc = 0.0;
	private Double ygnj = 0.0;
	private Double yctgnj = 0.0;
	private Double yjgj = 0.0;
	private String yjgjb = "";
	private Double yjgcd = 0.0;
	private Double ejgj = 0.0;
	private String ejgjb = "";
	private Double ejgcd = 0.0;
	private Double sjgj = 0.0;
	private String sjgjb = "";
	private Double sjgcd = 0.0;
	private Integer mdzt = 0;
	private Double qmflxl;
	private Double qtssll;
	private Double qtljll;
	private Double qtyl;
	private Double qtwd;
	private Double csssll;
	private Double csljll;
	private Double csyl=0.0;
	private Double cswd=0.0;
	private Double jmb = 0.0;
	private Integer bzgtbh = 0;
	private Integer bzdntbh = 0;
	private String bz;

	public ProductionOutWellInfo() {
	}

	@Id
	@GeneratedValue
	public Integer getJlbh() {
		return jlbh;
	}

	public void setJlbh(Integer jlbh) {
		this.jlbh = jlbh;
	}

	public Integer getBjb() {
		return bjb;
	}

	public void setBjb(Integer bjb) {
		this.bjb = bjb;
	}

	public Double getZsc() {
		return zsc;
	}

	public void setZsc(Double zsc) {
		this.zsc = zsc;
	}

	@Transient
	public String getSccj() {
		return sccj;
	}

	public void setSccj(String sccj) {
		this.sccj = sccj;
	}

	@Transient
	public String getCybxh() {
		return cybxh;
	}

	public void setCybxh(String cybxh) {
		this.cybxh = cybxh;
	}

	public Integer getJbh() {
		return jbh;
	}
	public void setJbh(Integer jbh) {
		this.jbh = jbh;
	}

	@Transient
	public String getJh() {
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public Integer getJslx() {
		return jslx;
	}

	public void setJslx(Integer jslx) {
		this.jslx = jslx;
	}

	public Integer getQtlx() {
		return qtlx;
	}

	public void setQtlx(Integer qtlx) {
		this.qtlx = qtlx;
	}

	public Integer getSfpfcl() {
		return sfpfcl;
	}

	public void setSfpfcl(Integer sfpfcl) {
		this.sfpfcl = sfpfcl;
	}

	public Integer getCcjzt() {
		return ccjzt;
	}

	public void setCcjzt(Integer ccjzt) {
		this.ccjzt = ccjzt;
	}

	public Double getRcyl() {
		return rcyl;
	}

	public void setRcyl(Double rcyl) {
		this.rcyl = rcyl;
	}

	public Double getHsl() {
		return hsl;
	}

	public void setHsl(Double hsl) {
		this.hsl = hsl;
	}

	public Double getRcql() {
		return rcql;
	}

	public void setRcql(Double rcql) {
		this.rcql = rcql;
	}

	public Double getZyyzzj() {
		return zyyzzj;
	}

	public void setZyyzzj(Double zyyzzj) {
		this.zyyzzj = zyyzzj;
	}

	public Double getCyyzzj() {
		return cyyzzj;
	}

	public void setCyyzzj(Double cyyzzj) {
		this.cyyzzj = cyyzzj;
	}

	public Double getYy() {
		return yy;
	}

	public void setYy(Double yy) {
		this.yy = yy;
	}

	public Double getTy() {
		return ty;
	}

	public void setTy(Double ty) {
		this.ty = ty;
	}

	public Double getHy() {
		return hy;
	}

	public void setHy(Double hy) {
		this.hy = hy;
	}

	public Double getJxyljwz() {
		return jxyljwz;
	}

	public void setJxyljwz(Double jxyljwz) {
		this.jxyljwz = jxyljwz;
	}

	public Double getJxyljyl() {
		return jxyljyl;
	}

	public void setJxyljyl(Double jxyljyl) {
		this.jxyljyl = jxyljyl;
	}

	public Double getJxwdjwz() {
		return jxwdjwz;
	}

	public void setJxwdjwz(Double jxwdjwz) {
		this.jxwdjwz = jxwdjwz;
	}

	public Double getJxwdjwd() {
		return jxwdjwd;
	}

	public void setJxwdjwd(Double jxwdjwd) {
		this.jxwdjwd = jxwdjwd;
	}

	public Double getJxyzwz() {
		return jxyzwz;
	}

	public void setJxyzwz(Double jxyzwz) {
		this.jxyzwz = jxyzwz;
	}

	public Double getJxyzzj() {
		return jxyzzj;
	}

	public void setJxyzzj(Double jxyzzj) {
		this.jxyzzj = jxyzzj;
	}

	public Double getDymbh() {
		return dymbh;
	}

	public void setDymbh(Double dymbh) {
		this.dymbh = dymbh;
	}

	@Transient
	public Double getDym() {
		return dym;
	}

	public void setDym(Double dym) {
		this.dym = dym;
	}

	public Double getBg() {
		return bg;
	}

	public void setBg(Double bg) {
		this.bg = bg;
	}

	public Double getJklw() {
		return jklw;
	}

	public void setJklw(Double jklw) {
		this.jklw = jklw;
	}

	public Double getScqyb() {
		return scqyb;
	}

	public void setScqyb(Double scqyb) {
		this.scqyb = scqyb;
	}

	@Transient
	public Integer getBj() {
		return bj;
	}

	public void setBj(Integer bj) {
		this.bj = bj;
	}

	public Double getYgnj() {
		return ygnj;
	}

	public void setYgnj(Double ygnj) {
		this.ygnj = ygnj;
	}

	public Double getYctgnj() {
		return yctgnj;
	}

	public void setYctgnj(Double yctgnj) {
		this.yctgnj = yctgnj;
	}

	public Double getYjgj() {
		return yjgj;
	}

	public void setYjgj(Double yjgj) {
		this.yjgj = yjgj;
	}

	public String getYjgjb() {
		return yjgjb;
	}

	public void setYjgjb(String yjgjb) {
		this.yjgjb = yjgjb;
	}

	public Double getYjgcd() {
		return yjgcd;
	}

	public void setYjgcd(Double yjgcd) {
		this.yjgcd = yjgcd;
	}

	public Double getEjgj() {
		return ejgj;
	}

	public void setEjgj(Double ejgj) {
		this.ejgj = ejgj;
	}

	public String getEjgjb() {
		return ejgjb;
	}

	public void setEjgjb(String ejgjb) {
		this.ejgjb = ejgjb;
	}

	public Double getEjgcd() {
		return ejgcd;
	}

	public void setEjgcd(Double ejgcd) {
		this.ejgcd = ejgcd;
	}

	public Double getSjgj() {
		return sjgj;
	}

	public void setSjgj(Double sjgj) {
		this.sjgj = sjgj;
	}

	public String getSjgjb() {
		return sjgjb;
	}

	public void setSjgjb(String sjgjb) {
		this.sjgjb = sjgjb;
	}

	public Double getSjgcd() {
		return sjgcd;
	}

	public void setSjgcd(Double sjgcd) {
		this.sjgcd = sjgcd;
	}

	public Integer getMdzt() {
		return mdzt;
	}

	public void setMdzt(Integer mdzt) {
		this.mdzt = mdzt;
	}

	public Double getQmflxl() {
		return qmflxl;
	}

	public void setQmflxl(Double qmflxl) {
		this.qmflxl = qmflxl;
	}

	public Double getQtssll() {
		return qtssll;
	}

	public void setQtssll(Double qtssll) {
		this.qtssll = qtssll;
	}

	public Double getQtljll() {
		return qtljll;
	}

	public void setQtljll(Double qtljll) {
		this.qtljll = qtljll;
	}

	public Double getQtyl() {
		return qtyl;
	}

	public void setQtyl(Double qtyl) {
		this.qtyl = qtyl;
	}

	public Double getQtwd() {
		return qtwd;
	}

	public void setQtwd(Double qtwd) {
		this.qtwd = qtwd;
	}

	public Double getCsssll() {
		return csssll;
	}

	public void setCsssll(Double csssll) {
		this.csssll = csssll;
	}

	public Double getCsljll() {
		return csljll;
	}

	public void setCsljll(Double csljll) {
		this.csljll = csljll;
	}

	public Double getCsyl() {
		return csyl;
	}

	public void setCsyl(Double csyl) {
		this.csyl = csyl;
	}

	public Double getCswd() {
		return cswd;
	}

	public void setCswd(Double cswd) {
		this.cswd = cswd;
	}

	public Double getJmb() {
		return jmb;
	}

	public void setJmb(Double jmb) {
		this.jmb = jmb;
	}

	public Integer getBzgtbh() {
		return bzgtbh;
	}

	public void setBzgtbh(Integer bzgtbh) {
		this.bzgtbh = bzgtbh;
	}

	public Integer getBzdntbh() {
		return bzdntbh;
	}

	public void setBzdntbh(Integer bzdntbh) {
		this.bzdntbh = bzdntbh;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}