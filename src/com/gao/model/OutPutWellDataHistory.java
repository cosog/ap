package com.gao.model;



import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_rpc_diagram_hist")
public abstract class OutPutWellDataHistory implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer jlbh;
	private Integer jbh;
	private Date jssj;
	private Double yymd;
	private Double smd;
	private Double trqxdmd;
	private Double ysrjqyb;
	private Double bhyl;
	private Double dmtqyynd;
	private Double yqcyl;
	private Double yqczbsd;
	private Double yqczbwd;
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
	private Integer dymbh;
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
	private Integer gtbh;
	private Date gtcjsj;
	private Double gtcmxs;
	private String bgt;
	private String gklx;
	private Integer dcsbh;
	private Double jsdjrcyl;
	private Double jsdjrcyl1;
	private Double gtcc;
	private Double gtcc1;
	private Double zdzh;
	private Double zxzh;
	private Double byxccjscl;
	private Double bjxlsl;
	private Double yytjxs;
	private Double ydfelsl;
	private Double gdfelsl;
	private Double qyx;
	private Double gggl;
	private Double sgl;
	private Double dmxtxl;
	private Double jxxtxl;
	private Double xtxl;
	private Double ccssxs;
	private Double cygssl;
	private Double ygssl;
	private Double gxzl;
	private Double ytssxs;
	private Double lsxs;
	private Double cmxs;
	private Double zbx;
	private Double bpckyl;
	private Double bxrkyl;
	private Double brkjdqyb;
	private Double bckjdqyb;
	private Double zsszh;
	private Double zsxzhscc;
	private Double zsxzhxcc;
	private Double bpckwd;
	private Double bpckytnd;
	private Double bpckyytjxs;
	private Double yjzdzh;
	private Double yjzxzh;
	private Double yjzdyl;
	private Double yjzxyl;
	private Double yjxyyl;
	private Double yjylbfb;
	private Double ejzdzh;
	private Double ejzxzh;
	private Double ejzdyl;
	private Double ejzxyl;
	private Double ejxyyl;
	private Double ejylbfb;
	private Double sjzdzh;
	private Double sjzxzh;
	private Double sjzdyl;
	private Double sjzxyl;
	private Double sjxyyl;
	private Double sjylbfb;
	private Integer jsbz;
	private String bz;
	private Integer rfidkh;
	private Integer rfidhfbz;
	private Date rcsj;
	private Date lcsj;

	// Constructors
	@Id
	@GeneratedValue
	@Column(name = "JLBH", unique = true, nullable = false, precision = 22, scale = 0)
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

	@Temporal(TemporalType.DATE)
	@Column(name = "JSSJ", length = 7)
	public Date getJssj() {
		return this.jssj;
	}

	public void setJssj(Date jssj) {
		this.jssj = jssj;
	}

	@Column(name = "YYMD", precision = 8)
	public Double getYymd() {
		return this.yymd;
	}

	public void setYymd(Double yymd) {
		this.yymd = yymd;
	}

	@Column(name = "SMD", precision = 8)
	public Double getSmd() {
		return this.smd;
	}

	public void setSmd(Double smd) {
		this.smd = smd;
	}

	@Column(name = "TRQXDMD", precision = 8)
	public Double getTrqxdmd() {
		return this.trqxdmd;
	}

	public void setTrqxdmd(Double trqxdmd) {
		this.trqxdmd = trqxdmd;
	}

	@Column(name = "YSRJQYB", precision = 8)
	public Double getYsrjqyb() {
		return this.ysrjqyb;
	}

	public void setYsrjqyb(Double ysrjqyb) {
		this.ysrjqyb = ysrjqyb;
	}

	@Column(name = "BHYL", precision = 8)
	public Double getBhyl() {
		return this.bhyl;
	}

	public void setBhyl(Double bhyl) {
		this.bhyl = bhyl;
	}

	@Column(name = "DMTQYYND", precision = 8)
	public Double getDmtqyynd() {
		return this.dmtqyynd;
	}

	public void setDmtqyynd(Double dmtqyynd) {
		this.dmtqyynd = dmtqyynd;
	}

	@Column(name = "YQCYL", precision = 8)
	public Double getYqcyl() {
		return this.yqcyl;
	}

	public void setYqcyl(Double yqcyl) {
		this.yqcyl = yqcyl;
	}

	@Column(name = "YQCZBSD", precision = 8)
	public Double getYqczbsd() {
		return this.yqczbsd;
	}

	public void setYqczbsd(Double yqczbsd) {
		this.yqczbsd = yqczbsd;
	}

	@Column(name = "YQCZBWD", precision = 8)
	public Double getYqczbwd() {
		return this.yqczbwd;
	}

	public void setYqczbwd(Double yqczbwd) {
		this.yqczbwd = yqczbwd;
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

	@Column(name = "DYMBH", precision = 22, scale = 0)
	public Integer getDymbh() {
		return this.dymbh;
	}

	public void setDymbh(Integer dymbh) {
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
	public Double getYctgnj() {
		return this.yctgnj;
	}

	public void setYctgnj(Double yctgnj) {
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

	@Column(name = "GTBH", nullable = false, precision = 22, scale = 0)
	public Integer getGtbh() {
		return this.gtbh;
	}

	public void setGtbh(Integer gtbh) {
		this.gtbh = gtbh;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "GTCJSJ", length = 7)
	public Date getGtcjsj() {
		return this.gtcjsj;
	}

	public void setGtcjsj(Date gtcjsj) {
		this.gtcjsj = gtcjsj;
	}

	@Column(name = "GTCMXS", precision = 8)
	public Double getGtcmxs() {
		return this.gtcmxs;
	}

	public void setGtcmxs(Double gtcmxs) {
		this.gtcmxs = gtcmxs;
	}

	@Column(name = "BGT")
	public String getBgt() {
		return this.bgt;
	}

	public void setBgt(String bgt) {
		this.bgt = bgt;
	}

	@Column(name = "GKLX", length = 200)
	public String getGklx() {
		return this.gklx;
	}

	public void setGklx(String gklx) {
		this.gklx = gklx;
	}

	@Column(name = "DCSBH", nullable = false, precision = 22, scale = 0)
	public Integer getDcsbh() {
		return this.dcsbh;
	}

	public void setDcsbh(Integer dcsbh) {
		this.dcsbh = dcsbh;
	}

	@Column(name = "JSDJRCYL", precision = 8)
	public Double getJsdjrcyl() {
		return this.jsdjrcyl;
	}

	public void setJsdjrcyl(Double jsdjrcyl) {
		this.jsdjrcyl = jsdjrcyl;
	}

	@Column(name = "JSDJRCYL1", precision = 8)
	public Double getJsdjrcyl1() {
		return this.jsdjrcyl1;
	}

	public void setJsdjrcyl1(Double jsdjrcyl1) {
		this.jsdjrcyl1 = jsdjrcyl1;
	}

	@Column(name = "GTCC", precision = 8)
	public Double getGtcc() {
		return this.gtcc;
	}

	public void setGtcc(Double gtcc) {
		this.gtcc = gtcc;
	}

	@Column(name = "GTCC1", precision = 8)
	public Double getGtcc1() {
		return this.gtcc1;
	}

	public void setGtcc1(Double gtcc1) {
		this.gtcc1 = gtcc1;
	}

	@Column(name = "ZDZH", precision = 8)
	public Double getZdzh() {
		return this.zdzh;
	}

	public void setZdzh(Double zdzh) {
		this.zdzh = zdzh;
	}

	@Column(name = "ZXZH", precision = 8)
	public Double getZxzh() {
		return this.zxzh;
	}

	public void setZxzh(Double zxzh) {
		this.zxzh = zxzh;
	}

	@Column(name = "BYXCCJSCL", precision = 8)
	public Double getByxccjscl() {
		return this.byxccjscl;
	}

	public void setByxccjscl(Double byxccjscl) {
		this.byxccjscl = byxccjscl;
	}

	@Column(name = "BJXLSL", precision = 8)
	public Double getBjxlsl() {
		return this.bjxlsl;
	}

	public void setBjxlsl(Double bjxlsl) {
		this.bjxlsl = bjxlsl;
	}

	@Column(name = "YYTJXS", precision = 8)
	public Double getYytjxs() {
		return this.yytjxs;
	}

	public void setYytjxs(Double yytjxs) {
		this.yytjxs = yytjxs;
	}

	@Column(name = "YDFELSL", precision = 8)
	public Double getYdfelsl() {
		return this.ydfelsl;
	}

	public void setYdfelsl(Double ydfelsl) {
		this.ydfelsl = ydfelsl;
	}

	@Column(name = "GDFELSL", precision = 8)
	public Double getGdfelsl() {
		return this.gdfelsl;
	}

	public void setGdfelsl(Double gdfelsl) {
		this.gdfelsl = gdfelsl;
	}

	@Column(name = "QYX", precision = 8)
	public Double getQyx() {
		return this.qyx;
	}

	public void setQyx(Double qyx) {
		this.qyx = qyx;
	}

	@Column(name = "GGGL", precision = 8)
	public Double getGggl() {
		return this.gggl;
	}

	public void setGggl(Double gggl) {
		this.gggl = gggl;
	}

	@Column(name = "SGL", precision = 8)
	public Double getSgl() {
		return this.sgl;
	}

	public void setSgl(Double sgl) {
		this.sgl = sgl;
	}

	@Column(name = "DMXTXL", precision = 8)
	public Double getDmxtxl() {
		return this.dmxtxl;
	}

	public void setDmxtxl(Double dmxtxl) {
		this.dmxtxl = dmxtxl;
	}

	@Column(name = "JXXTXL", precision = 8)
	public Double getJxxtxl() {
		return this.jxxtxl;
	}

	public void setJxxtxl(Double jxxtxl) {
		this.jxxtxl = jxxtxl;
	}

	@Column(name = "XTXL", precision = 8)
	public Double getXtxl() {
		return this.xtxl;
	}

	public void setXtxl(Double xtxl) {
		this.xtxl = xtxl;
	}

	@Column(name = "CCSSXS", precision = 8)
	public Double getCcssxs() {
		return this.ccssxs;
	}

	public void setCcssxs(Double ccssxs) {
		this.ccssxs = ccssxs;
	}

	@Column(name = "CYGSSL", precision = 8)
	public Double getCygssl() {
		return this.cygssl;
	}

	public void setCygssl(Double cygssl) {
		this.cygssl = cygssl;
	}

	@Column(name = "YGSSL", precision = 8)
	public Double getYgssl() {
		return this.ygssl;
	}

	public void setYgssl(Double ygssl) {
		this.ygssl = ygssl;
	}

	@Column(name = "GXZL", precision = 8)
	public Double getGxzl() {
		return this.gxzl;
	}

	public void setGxzl(Double gxzl) {
		this.gxzl = gxzl;
	}

	@Column(name = "YTSSXS", precision = 8)
	public Double getYtssxs() {
		return this.ytssxs;
	}

	public void setYtssxs(Double ytssxs) {
		this.ytssxs = ytssxs;
	}

	@Column(name = "LSXS", precision = 8)
	public Double getLsxs() {
		return this.lsxs;
	}

	public void setLsxs(Double lsxs) {
		this.lsxs = lsxs;
	}

	@Column(name = "CMXS", precision = 8)
	public Double getCmxs() {
		return this.cmxs;
	}

	public void setCmxs(Double cmxs) {
		this.cmxs = cmxs;
	}

	@Column(name = "ZBX", precision = 8)
	public Double getZbx() {
		return this.zbx;
	}

	public void setZbx(Double zbx) {
		this.zbx = zbx;
	}

	@Column(name = "BPCKYL", precision = 8)
	public Double getBpckyl() {
		return this.bpckyl;
	}

	public void setBpckyl(Double bpckyl) {
		this.bpckyl = bpckyl;
	}

	@Column(name = "BXRKYL", precision = 8)
	public Double getBxrkyl() {
		return this.bxrkyl;
	}

	public void setBxrkyl(Double bxrkyl) {
		this.bxrkyl = bxrkyl;
	}

	@Column(name = "BRKJDQYB", precision = 8)
	public Double getBrkjdqyb() {
		return this.brkjdqyb;
	}

	public void setBrkjdqyb(Double brkjdqyb) {
		this.brkjdqyb = brkjdqyb;
	}

	@Column(name = "BCKJDQYB", precision = 8)
	public Double getBckjdqyb() {
		return this.bckjdqyb;
	}

	public void setBckjdqyb(Double bckjdqyb) {
		this.bckjdqyb = bckjdqyb;
	}

	@Column(name = "ZSSZH", precision = 8)
	public Double getZsszh() {
		return this.zsszh;
	}

	public void setZsszh(Double zsszh) {
		this.zsszh = zsszh;
	}

	@Column(name = "ZSXZHSCC", precision = 8)
	public Double getZsxzhscc() {
		return this.zsxzhscc;
	}

	public void setZsxzhscc(Double zsxzhscc) {
		this.zsxzhscc = zsxzhscc;
	}

	@Column(name = "ZSXZHXCC", precision = 8)
	public Double getZsxzhxcc() {
		return this.zsxzhxcc;
	}

	public void setZsxzhxcc(Double zsxzhxcc) {
		this.zsxzhxcc = zsxzhxcc;
	}

	@Column(name = "BPCKWD", precision = 8)
	public Double getBpckwd() {
		return this.bpckwd;
	}

	public void setBpckwd(Double bpckwd) {
		this.bpckwd = bpckwd;
	}

	@Column(name = "BPCKYTND", precision = 8)
	public Double getBpckytnd() {
		return this.bpckytnd;
	}

	public void setBpckytnd(Double bpckytnd) {
		this.bpckytnd = bpckytnd;
	}

	@Column(name = "BPCKYYTJXS", precision = 8)
	public Double getBpckyytjxs() {
		return this.bpckyytjxs;
	}

	public void setBpckyytjxs(Double bpckyytjxs) {
		this.bpckyytjxs = bpckyytjxs;
	}

	@Column(name = "YJZDZH", precision = 8)
	public Double getYjzdzh() {
		return this.yjzdzh;
	}

	public void setYjzdzh(Double yjzdzh) {
		this.yjzdzh = yjzdzh;
	}

	@Column(name = "YJZXZH", precision = 8)
	public Double getYjzxzh() {
		return this.yjzxzh;
	}

	public void setYjzxzh(Double yjzxzh) {
		this.yjzxzh = yjzxzh;
	}

	@Column(name = "YJZDYL", precision = 8)
	public Double getYjzdyl() {
		return this.yjzdyl;
	}

	public void setYjzdyl(Double yjzdyl) {
		this.yjzdyl = yjzdyl;
	}

	@Column(name = "YJZXYL", precision = 8)
	public Double getYjzxyl() {
		return this.yjzxyl;
	}

	public void setYjzxyl(Double yjzxyl) {
		this.yjzxyl = yjzxyl;
	}

	@Column(name = "YJXYYL", precision = 8)
	public Double getYjxyyl() {
		return this.yjxyyl;
	}

	public void setYjxyyl(Double yjxyyl) {
		this.yjxyyl = yjxyyl;
	}

	@Column(name = "YJYLBFB", precision = 8)
	public Double getYjylbfb() {
		return this.yjylbfb;
	}

	public void setYjylbfb(Double yjylbfb) {
		this.yjylbfb = yjylbfb;
	}

	@Column(name = "EJZDZH", precision = 8)
	public Double getEjzdzh() {
		return this.ejzdzh;
	}

	public void setEjzdzh(Double ejzdzh) {
		this.ejzdzh = ejzdzh;
	}

	@Column(name = "EJZXZH", precision = 8)
	public Double getEjzxzh() {
		return this.ejzxzh;
	}

	public void setEjzxzh(Double ejzxzh) {
		this.ejzxzh = ejzxzh;
	}

	@Column(name = "EJZDYL", precision = 8)
	public Double getEjzdyl() {
		return this.ejzdyl;
	}

	public void setEjzdyl(Double ejzdyl) {
		this.ejzdyl = ejzdyl;
	}

	@Column(name = "EJZXYL", precision = 8)
	public Double getEjzxyl() {
		return this.ejzxyl;
	}

	public void setEjzxyl(Double ejzxyl) {
		this.ejzxyl = ejzxyl;
	}

	@Column(name = "EJXYYL", precision = 8)
	public Double getEjxyyl() {
		return this.ejxyyl;
	}

	public void setEjxyyl(Double ejxyyl) {
		this.ejxyyl = ejxyyl;
	}

	@Column(name = "EJYLBFB", precision = 8)
	public Double getEjylbfb() {
		return this.ejylbfb;
	}

	public void setEjylbfb(Double ejylbfb) {
		this.ejylbfb = ejylbfb;
	}

	@Column(name = "SJZDZH", precision = 8)
	public Double getSjzdzh() {
		return this.sjzdzh;
	}

	public void setSjzdzh(Double sjzdzh) {
		this.sjzdzh = sjzdzh;
	}

	@Column(name = "SJZXZH", precision = 8)
	public Double getSjzxzh() {
		return this.sjzxzh;
	}

	public void setSjzxzh(Double sjzxzh) {
		this.sjzxzh = sjzxzh;
	}

	@Column(name = "SJZDYL", precision = 8)
	public Double getSjzdyl() {
		return this.sjzdyl;
	}

	public void setSjzdyl(Double sjzdyl) {
		this.sjzdyl = sjzdyl;
	}

	@Column(name = "SJZXYL", precision = 8)
	public Double getSjzxyl() {
		return this.sjzxyl;
	}

	public void setSjzxyl(Double sjzxyl) {
		this.sjzxyl = sjzxyl;
	}

	@Column(name = "SJXYYL", precision = 8)
	public Double getSjxyyl() {
		return this.sjxyyl;
	}

	public void setSjxyyl(Double sjxyyl) {
		this.sjxyyl = sjxyyl;
	}

	@Column(name = "SJYLBFB", precision = 8)
	public Double getSjylbfb() {
		return this.sjylbfb;
	}

	public void setSjylbfb(Double sjylbfb) {
		this.sjylbfb = sjylbfb;
	}

	@Column(name = "JSBZ", precision = 22, scale = 0)
	public Integer getJsbz() {
		return this.jsbz;
	}

	public void setJsbz(Integer jsbz) {
		this.jsbz = jsbz;
	}

	@Column(name = "BZ", length = 200)
	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Column(name = "RFIDKH", precision = 22, scale = 0)
	public Integer getRfidkh() {
		return this.rfidkh;
	}

	public void setRfidkh(Integer rfidkh) {
		this.rfidkh = rfidkh;
	}

	@Column(name = "RFIDHFBZ", precision = 22, scale = 0)
	public Integer getRfidhfbz() {
		return this.rfidhfbz;
	}

	public void setRfidhfbz(Integer rfidhfbz) {
		this.rfidhfbz = rfidhfbz;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RCSJ", length = 7)
	public Date getRcsj() {
		return this.rcsj;
	}

	public void setRcsj(Date rcsj) {
		this.rcsj = rcsj;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LCSJ", length = 7)
	public Date getLcsj() {
		return this.lcsj;
	}

	public void setLcsj(Date lcsj) {
		this.lcsj = lcsj;
	}

}