package com.gao.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *  <p>描述：单井诊断信息 实体类  sc_org</p>
 *  
 * @author gao  2014-06-10
 *
 */
@Entity
@Table(name = "v_analysishistory")
public class DiagnosisAnalysisHistoryOnly implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String jh;
	private String jslx;
	private Integer jbh;
	private Integer txzt;
	private Double txsj=0.0;//在线时间
	private Double txsl=0.0;//在线时率
	private String txqj;//在线区间
	private Integer yxzt;
	private Double yxsj=0.0;//运行时间
	private Double yxsl=0.0;//运行时率
	private String yxqj;//运行区间
	private Date gtcjsj;
	private Integer gtbh;
	private Integer gtszzq;
	private Integer gtcjzq;
	private Integer gklx;
	private String gkmc;
	private String yhjy="";
	private String egkmc;
	private String egklxstr;
	private String egkxq="";
	private Integer bjbz;
	private Integer ebjbz;
	private String gkjgly;
	private Double rcylbd=0.0;//产液量波动
	private Double jsdjrcyl=0.0;// 产液量
	private Double jsdjrcyl1=0.0;// 产油量
	private Double jsdjrcsl=0.0;// 产水量
	private Double hsl=0.0; // 含水率(%)
	private Double scqyb=0.0; // 生产汽油比(%)
	private Double yy=0.0; // 油压
	private Double ty=0.0; // 套压
	private Double jklw=0.0; // 井口流温
	private Double dym=0.0; // 动液面
	private Double bg=0.0; // 泵挂
	private Double cmd=0.0; // 沉没度
	private Double bj=0.0; // 泵径
	private Double yymd=0.0; // 原油密度
	private Double jmb=1.0; // 产量系数
	private Double byxccjscl=0.0;// 泵有效冲程计算产量(m^3/d)
	private Double bjxlsl=0.0;// 泵间隙漏失量(m^3/d)
	private Double yytjxs=0.0;// 原油体积系数
	private Double ydfelsl=0.0;// 游动凡尔漏失量(m^3/d)
	private Double gdfelsl=0.0;// 固定凡尔漏失量(m^3/d)
	private Double qyx=0.0; // 气影响(m^3/d)
	private Double yjzdzh=0.0;// 一级最大载荷(kN)
	private Double yjzxzh=0.0;// 一级最小载荷(kN)
	private Double yjzdyl=0.0;// 一级最大应力(MPa)
	private Double yjzxyl=0.0;// 一级最小应力(MPa)
	private Double yjxyyl=0.0;// 一级许用应力(MPa)
	private Double yjylbfb=0.0;// 一级应力百分比(%)
	private Double ejzdzh=0.0;// 二级最大载荷(kN)
	private Double ejzxzh=0.0;// 二级最小载荷(kN)
	private Double ejzdyl=0.0;// 二级最大应力(MPa)
	private Double ejzxyl=0.0;// 二级最小应力(MPa)
	private Double ejxyyl=0.0;// 二级许用应力(MPa)
	private Double ejylbfb=0.0;// 二级应力百分比(%)
	private Double sjzdzh=0.0;// 三级最大载荷(kN)
	private Double sjzxzh=0.0;// 三级最小载荷(kN)
	private Double sjzdyl=0.0;// 三级最大应力(MPa)
	private Double sjzxyl=0.0;// 三级最小应力(MPa)
	private Double sjxyyl=0.0;// 三级许用应力(MPa)
	private Double sjylbfb=0.0;// 三级应力百分比(%)
	private String gcdstr;
	private String gjbstr;
	private String gnjstr;
	private String gwjstr;
	private Double gtcc=0.0;// 功图冲程(m)
	private Double gtcc1=0.0;// 功图冲次(1/min)
	private Double yggl=0.0;// 有功功率(kW)
	private Double gggl=0.0;// 光杆功率(kW)
	private Double sgl=0.0;// 水功率(kW)
	private Double dmxtxl=0.0;// 地面系统效率(%)
	private Double jxxtxl=0.0;// 井下系统效率(%)
	private Double xtxl=0.0;// 系统效率
	private Double dybmhdl=0.0;// 吨液百米耗电量(kW·h/100m·t)
	private Double rydl=0.0;// 日用电量
	private Double ccssxs=0.0;// 冲程损失系数
	private Double cygssl=0.0;// 抽油杆伸缩量(m)
	private Double ygssl=0.0;// 油管伸缩量(m)
	private Double gxzl=0.0;// 惯性增量(m)
	private Double ytssxs=0.0;// 液体收缩系数
	private Double lsxs=0.0;// 漏失系数
	private Double cmxs=0.0;// 理论充满系数
	private Double zbx=0.0;// 总泵效
	private Double bpckyl=0.0;// 泵排出口压力(MPa)
	private Double bxrkyl=0.0;// 泵吸入口压力(MPa)
	private Double brkjdqyb=0.0;// 泵入口就地气液比(m^3/t)
	private Double bckjdqyb=0.0;// 泵出口就地气液比(m^3/t)
	private Double zsszh=0.0;// 柱塞上载荷(kN)
	private Double zsxzhscc=0.0;// 柱塞下载荷(上冲程)(kN)
	private Double zsxzhxcc=0.0;// 柱塞下载荷(下冲程)(kN
	private Double bpckwd=0.0;// 泵排出口温度(℃)
	private Double bpckytnd=0.0;// 泵排出口液体粘度(mPaS)
	private Double bpckyytjxs=0.0;// 泵排出口原油体积系数
	
	private Double tubingpressure=0.0;//油压
	private Double casingpressure=0.0;//套压
	private Double backpressure=0.0;//回压
	private Double wellheadfluidtemperature=0.0;//井口油温
	private Double currenta=0.0;//A相电流
	private Double currentb=0.0;//B相电流
	private Double currentc=0.0;//C相电流
	private String currentstr;
	private Double currentauplimit=0.0;//A相电流上限
	private Double currentadownlimit=0.0;//A相电流下限
	private Double currentbuplimit=0.0;//B相电流上限
	private Double currentbdownlimit=0.0;//B相电流下限
	private Double currentcuplimit=0.0;//C相电流上限
	private Double currentcdownlimit=0.0;//c相电流下限
	private Double voltagea=0.0;//A相电压
	private Double voltageb=0.0;//B相电压
	private Double voltagec=0.0;//C相电压
	private String voltagestr;
	private Double voltageauplimit=0.0;//A相电压上限
	private Double voltageadownlimit=0.0;//A相电压下限
	private Double voltagebuplimit=0.0;//B相电压上限
	private Double voltagebdownlimit=0.0;//B相电压下限
	private Double voltagecuplimit=0.0;//C相电压上限
	private Double voltagecdownlimit=0.0;//C相电压下限
	private Double activepowerconsumption=0.0;//有功功耗
	private Double reactivepowerconsumption=0.0;//无功功耗
	private Double activepower=0.0;//有功功率
	private Double reactivepower=0.0;//无功功率
	private Double reversepower=0.0;//反向功率
	private Double powerfactor=0.0;//功率因数
	
	private Double bpszpl=0.0;//变频设置频率
	private Double bpyxpl=0.0;//变频运行频率
	
	private Double adegreeofbalance=0.0;//电流平衡度
	private String phzt;//平衡状态
	private String dlb;//电流比
	private Double pdegreeofbalance=0.0;//功率平衡度
	private String glphzt;//功率平衡状态
	private String glb;//功率比
	
	
	private String dwbh;
	private Integer orgId;
	private String videourl;//视频路径


	// Constructors
	/** default constructor */
	public DiagnosisAnalysisHistoryOnly() {
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
	@Column(name = "dwbh", nullable = false, length = 200)
	public String getDwbh() {
		return dwbh;
	}

	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
	}
	@Column(name = "org_id" , precision = 22, scale = 0)
	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	@Column(name = "videourl", nullable = false, length = 200)
	public String getVideourl() {
		return videourl;
	}

	public void setVideourl(String videourl) {
		this.videourl = videourl;
	}
	@Column(name = "JH", nullable = false, length = 200)
	public String getJh() {
		return this.jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}
	
	@Column(name = "JBH", precision = 22, scale = 0)
	public Integer getJbh() {
		return this.jbh;
	}

	public void setJbh(Integer jbh) {
		this.jbh = jbh;
	}
	
	@Column(name = "TXZT", precision = 22, scale = 0)
	public Integer getTxzt() {
		return this.txzt;
	}

	public void setTxzt(Integer txzt) {
		this.txzt = txzt;
	}
	
	@Column(name = "YXZT", precision = 22, scale = 0)
	public Integer getYxzt() {
		return this.yxzt;
	}

	public void setYxzt(Integer yxzt) {
		this.yxzt = yxzt;
	}

	@Column(name = "gtcjsj", length = 7)
	public Date getGtcjsj() {
		return this.gtcjsj;
	}

	public void setGtcjsj(Date gtcjsj) {
		this.gtcjsj = gtcjsj;
	}
	
	@Column(name = "GTBH", precision = 22, scale = 0)
	public Integer getGtbh() {
		return this.gtbh;
	}

	public void setGtbh(Integer gtbh) {
		this.gtbh = gtbh;
	}
	
	@Column(name = "GTSZZQ", precision = 22, scale = 0)
	public Integer getGtszzq() {
		return this.gtszzq;
	}

	public void setGtszzq(Integer gtszzq) {
		this.gtszzq = gtszzq;
	}
	
	@Column(name = "GTCJZQ", precision = 22, scale = 0)
	public Integer getGtcjzq() {
		return this.gtcjzq;
	}

	public void setGtcjzq(Integer gtcjzq) {
		this.gtcjzq = gtcjzq;
	}
	
	@Column(name = "GKLX", precision = 22, scale = 0)
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
	
	@Column(name = "YHJY", nullable = false, length = 200)
	public String getYhjy() {
		return this.yhjy;
	}

	public void setYhjy(String yhjy) {
		this.yhjy = yhjy;
	}
	
	@Column(name = "GKJGLY", nullable = false, length = 200)
	public String getGkjgly() {
		return this.gkjgly;
	}

	public void setGkjgly(String gkjgly) {
		this.gkjgly = gkjgly;
	}
	
	@Column(name = "BJBZ", precision = 22, scale = 0)
	public Integer getBjbz() {
		return this.bjbz;
	}

	public void setBjbz(Integer bjbz) {
		this.bjbz = bjbz;
	}

	@Column(name = "JSDJRCYL", precision = 8)
	public Double getJsdjrcyl() {
		return this.jsdjrcyl;
	}

	public void setJsdjrcyl(Double jsdjrcyl) {
		this.jsdjrcyl = jsdjrcyl;
	}
	
	@Column(name = "RCYLBD", precision = 8)
	public Double getRcylbd() {
		return this.rcylbd;
	}

	public void setRcylbd(Double rcylbd) {
		this.rcylbd = rcylbd;
	}

	@Column(name = "JSDJRCYL1", precision = 8)
	public Double getJsdjrcyl1() {
		return this.jsdjrcyl1;
	}

	public void setJsdjrcyl1(Double jsdjrcyl1) {
		this.jsdjrcyl1 = jsdjrcyl1;
	}
	
	@Column(name = "JSDJRCSL", precision = 8)
	public Double getJsdjrcsl() {
		return this.jsdjrcsl;
	}

	public void setJsdjrcsl(Double jsdjrcsl) {
		this.jsdjrcsl = jsdjrcsl;
	}

	
	@Column(name = "YDFELSL", precision = 8)
	public Double getYdfelsl() {
		return ydfelsl;
	}

	public void setYdfelsl(Double ydfelsl) {
		this.ydfelsl = ydfelsl;
	}

	

	@Column(name = "HSL", precision = 8)
	public Double getHsl() {
		return this.hsl;
	}

	public void setHsl(Double hsl) {
		this.hsl = hsl;
	}
	
	@Column(name = "YY", precision = 8)
	public Double getYy() {
		return yy;
	}

	public void setYy(Double yy) {
		this.yy = yy;
	}

	@Column(name = "TY", precision = 8)
	public Double getTy() {
		return ty;
	}

	public void setTy(Double ty) {
		this.ty = ty;
	}

	@Column(name = "JKLW", precision = 8)
	public Double getJklw() {
		return jklw;
	}

	public void setJklw(Double jklw) {
		this.jklw = jklw;
	}

	@Column(name = "DYM", precision = 8)
	public Double getDym() {
		return dym;
	}

	public void setDym(Double dym) {
		this.dym = dym;
	}

	@Column(name = "BG", precision = 8)
	public Double getBg() {
		return bg;
	}

	public void setBg(Double bg) {
		this.bg = bg;
	}

	@Column(name = "YYMD", precision = 8)
	public Double getYymd() {
		return yymd;
	}

	public void setYymd(Double yymd) {
		this.yymd = yymd;
	}

	@Column(name = "CMD", precision = 8)
	public Double getCmd() {
		return cmd;
	}

	public void setCmd(Double cmd) {
		this.cmd = cmd;
	}

	@Column(name = "BJ", precision = 8)
	public Double getBj() {
		return bj;
	}

	public void setBj(Double bj) {
		this.bj = bj;
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
	
	@Column(name = "YGGL", precision = 8)
	public Double getyggl() {
		return this.yggl;
	}

	public void setYggl(Double yggl) {
		this.yggl = yggl;
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
	
	@Column(name = "DYBMHDL", precision = 8)
	public Double getDybmhdl() {
		return this.dybmhdl;
	}

	public void setDybmhdl(Double dybmhdl) {
		this.dybmhdl = dybmhdl;
	}
	
	@Column(name = "RYDL", precision = 8)
	public Double getRydl() {
		return this.rydl;
	}

	public void setRydl(Double rydl) {
		this.rydl = rydl;
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

	public Double getJmb() {
		return jmb;
	}

	public void setJmb(Double jmb) {
		this.jmb = jmb;
	}
	
	@Column(name = "TUBINGPRESSURE", precision = 8)
	public Double getTubingpressure() {
		return tubingpressure;
	}

	public void setTubingpressure(Double tubingpressure) {
		this.tubingpressure = tubingpressure;
	}

	@Column(name = "CASINGPRESSURE", precision = 8)
	public Double getCasingpressure() {
		return casingpressure;
	}

	public void setCasingpressure(Double casingpressure) {
		this.casingpressure = casingpressure;
	}

	@Column(name = "BACKPRESSURE", precision = 8)
	public Double getBackpressure() {
		return backpressure;
	}

	public void setBackpressure(Double backpressure) {
		this.backpressure = backpressure;
	}

	@Column(name = "WELLHEADFLUIDTEMPERATURE", precision = 8)
	public Double getWellheadfluidtemperature() {
		return wellheadfluidtemperature;
	}

	public void setWellheadfluidtemperature(Double wellheadfluidtemperature) {
		this.wellheadfluidtemperature = wellheadfluidtemperature;
	}

	@Column(name = "CURRENTA", precision = 8)
	public Double getCurrenta() {
		return currenta;
	}

	public void setCurrenta(Double currenta) {
		this.currenta = currenta;
	}

	@Column(name = "CURRENTB", precision = 8)
	public Double getCurrentb() {
		return currentb;
	}

	public void setCurrentb(Double currentb) {
		this.currentb = currentb;
	}

	@Column(name = "CURRENTC", precision = 8)
	public Double getCurrentc() {
		return currentc;
	}

	public void setCurrentc(Double currentc) {
		this.currentc = currentc;
	}
	
	@Column(name = "CURRENTSTR",  length = 200)
	public String getCurrentstr() {
		return currentstr;
	}

	public void setCurrentstr(String currentstr) {
		this.currentstr = currentstr;
	}

	@Column(name = "VOLTAGEA", precision = 8)
	public Double getVoltagea() {
		return voltagea;
	}

	public void setVoltagea(Double voltagea) {
		this.voltagea = voltagea;
	}

	@Column(name = "VOLTAGEB", precision = 8)
	public Double getVoltageb() {
		return voltageb;
	}

	public void setVoltageb(Double voltageb) {
		this.voltageb = voltageb;
	}

	@Column(name = "VOLTAGEC", precision = 8)
	public Double getVoltagec() {
		return voltagec;
	}

	public void setVoltagec(Double voltagec) {
		this.voltagec = voltagec;
	}
	
	@Column(name = "VOLTAGESTR",  length = 200)
	public String getVoltagestr() {
		return voltagestr;
	}

	public void setVoltagestr(String voltagestr) {
		this.voltagestr = voltagestr;
	}

	@Column(name = "ACTIVEPOWERCONSUMPTION", precision = 8)
	public Double getActivepowerconsumption() {
		return activepowerconsumption;
	}

	public void setActivepowerconsumption(Double activepowerconsumption) {
		this.activepowerconsumption = activepowerconsumption;
	}

	@Column(name = "REACTIVEPOWERCONSUMPTION", precision = 8)
	public Double getReactivepowerconsumption() {
		return reactivepowerconsumption;
	}

	public void setReactivepowerconsumption(Double reactivepowerconsumption) {
		this.reactivepowerconsumption = reactivepowerconsumption;
	}

	@Column(name = "ACTIVEPOWER", precision = 8)
	public Double getActivepower() {
		return activepower;
	}

	public void setActivepower(Double activepower) {
		this.activepower = activepower;
	}

	@Column(name = "REACTIVEPOWER", precision = 8)
	public Double getReactivepower() {
		return reactivepower;
	}

	public void setReactivepower(Double reactivepower) {
		this.reactivepower = reactivepower;
	}

	@Column(name = "REVERSEPOWER", precision = 8)
	public Double getReversepower() {
		return reversepower;
	}

	public void setReversepower(Double reversepower) {
		this.reversepower = reversepower;
	}

	@Column(name = "POWERFACTOR", precision = 8)
	public Double getPowerfactor() {
		return powerfactor;
	}

	public void setPowerfactor(Double powerfactor) {
		this.powerfactor = powerfactor;
	}
	
	@Column(name = "ADEGREEOFBALANCE", precision = 8)
	public Double getAdegreeofbalance() {
		return adegreeofbalance;
	}

	public void setAdegreeofbalance(Double adegreeofbalance) {
		this.adegreeofbalance = adegreeofbalance;
	}

	@Column(name = "PHZT", nullable = false, length = 200)
	public String getPhzt() {
		return phzt;
	}

	public void setPhzt(String phzt) {
		this.phzt = phzt;
	}
	
	@Column(name = "PDEGREEOFBALANCE", precision = 8)
	public Double getPdegreeofbalance() {
		return pdegreeofbalance;
	}

	public void setPdegreeofbalance(Double pdegreeofbalance) {
		this.pdegreeofbalance = pdegreeofbalance;
	}

	@Column(name = "GLPHZT", nullable = false, length = 200)
	public String getGlphzt() {
		return glphzt;
	}

	public void setGlphzt(String glphzt) {
		this.glphzt = glphzt;
	}
	
	@Column(name = "YXSJ", precision = 8)
	public Double getYxsj() {
		return yxsj;
	}

	public void setYxsj(Double yxsj) {
		this.yxsj = yxsj;
	}

	@Column(name = "YXSL", precision = 8)
	public Double getYxsl() {
		return yxsl;
	}

	public void setYxsl(Double yxsl) {
		this.yxsl = yxsl;
	}

	@Column(name = "YXQJ", length = 2000)
	public String getYxqj() {
		return yxqj;
	}

	public void setYxqj(String yxqj) {
		this.yxqj = yxqj;
	}
	
	@Column(name = "TXSJ", precision = 8)
	public Double getTxsj() {
		return txsj;
	}

	public void setTxsj(Double txsj) {
		this.txsj = txsj;
	}

	@Column(name = "TXSL", precision = 8)
	public Double getTxsl() {
		return txsl;
	}

	public void setTxsl(Double txsl) {
		this.txsl = txsl;
	}

	@Column(name = "TXQJ", length = 2000)
	public String getTxqj() {
		return txqj;
	}

	public void setTxqj(String txqj) {
		this.txqj = txqj;
	}
	
	@Column(name = "EGKMC", nullable = false, length = 200)
	public String getEgkmc() {
		return egkmc;
	}

	public void setEgkmc(String egkmc) {
		this.egkmc = egkmc;
	}
	
	@Column(name = "EGKLXSTR",  length = 200)
	public String getEgklxstr() {
		return egklxstr;
	}

	public void setEgklxstr(String egklxstr) {
		this.egklxstr = egklxstr;
	}

	@Column(name = "EGKXQ", length = 200)
	public String getEgkxq() {
		return egkxq;
	}

	public void setEgkxq(String egkxq) {
		this.egkxq = egkxq;
	}

	@Column(name = "EBJBZ", precision = 22, scale = 0)
	public Integer getEbjbz() {
		return ebjbz;
	}

	public void setEbjbz(Integer ebjbz) {
		this.ebjbz = ebjbz;
	}
	
	@Column(name = "DLB", length = 200)
	public String getDlb() {
		return dlb;
	}

	public void setDlb(String dlb) {
		this.dlb = dlb;
	}

	@Column(name = "GLB", length = 200)
	public String getGlb() {
		return glb;
	}

	public void setGlb(String glb) {
		this.glb = glb;
	}
	
	@Column(name = "JSLX", length = 200)
	public String getJslx() {
		return jslx;
	}

	public void setJslx(String jslx) {
		this.jslx = jslx;
	}

	@Column(name = "SCQYB", precision = 8)
	public Double getScqyb() {
		return scqyb;
	}

	public void setScqyb(Double scqyb) {
		this.scqyb = scqyb;
	}

	@Column(name = "GCDSTR", length = 200)
	public String getGcdstr() {
		return gcdstr;
	}

	public void setGcdstr(String gcdstr) {
		this.gcdstr = gcdstr;
	}

	@Column(name = "GJBSTR", length = 200)
	public String getGjbstr() {
		return gjbstr;
	}

	public void setGjbstr(String gjbstr) {
		this.gjbstr = gjbstr;
	}

	@Column(name = "GNJSTR", length = 200)
	public String getGnjstr() {
		return gnjstr;
	}

	public void setGnjstr(String gnjstr) {
		this.gnjstr = gnjstr;
	}

	@Column(name = "GWJSTR", length = 200)
	public String getGwjstr() {
		return gwjstr;
	}

	public void setGwjstr(String gwjstr) {
		this.gwjstr = gwjstr;
	}
	
	@Column(name = "currentauplimit", precision = 8)
	public Double getCurrentauplimit() {
		return currentauplimit;
	}

	public void setCurrentauplimit(Double currentauplimit) {
		this.currentauplimit = currentauplimit;
	}

	@Column(name = "currentadownlimit", precision = 8)
	public Double getCurrentadownlimit() {
		return currentadownlimit;
	}

	public void setCurrentadownlimit(Double currentadownlimit) {
		this.currentadownlimit = currentadownlimit;
	}

	@Column(name = "currentbuplimit", precision = 8)
	public Double getCurrentbuplimit() {
		return currentbuplimit;
	}

	public void setCurrentbuplimit(Double currentbuplimit) {
		this.currentbuplimit = currentbuplimit;
	}

	@Column(name = "currentbdownlimit", precision = 8)
	public Double getCurrentbdownlimit() {
		return currentbdownlimit;
	}

	public void setCurrentbdownlimit(Double currentbdownlimit) {
		this.currentbdownlimit = currentbdownlimit;
	}

	@Column(name = "currentcuplimit", precision = 8)
	public Double getCurrentcuplimit() {
		return currentcuplimit;
	}

	public void setCurrentcuplimit(Double currentcuplimit) {
		this.currentcuplimit = currentcuplimit;
	}

	@Column(name = "currentcdownlimit", precision = 8)
	public Double getCurrentcdownlimit() {
		return currentcdownlimit;
	}

	public void setCurrentcdownlimit(Double currentcdownlimit) {
		this.currentcdownlimit = currentcdownlimit;
	}

	@Column(name = "voltageauplimit", precision = 8)
	public Double getVoltageauplimit() {
		return voltageauplimit;
	}

	public void setVoltageauplimit(Double voltageauplimit) {
		this.voltageauplimit = voltageauplimit;
	}

	@Column(name = "voltageadownlimit", precision = 8)
	public Double getVoltageadownlimit() {
		return voltageadownlimit;
	}

	public void setVoltageadownlimit(Double voltageadownlimit) {
		this.voltageadownlimit = voltageadownlimit;
	}

	@Column(name = "voltagebuplimit", precision = 8)
	public Double getVoltagebuplimit() {
		return voltagebuplimit;
	}

	public void setVoltagebuplimit(Double voltagebuplimit) {
		this.voltagebuplimit = voltagebuplimit;
	}

	@Column(name = "voltagebdownlimit", precision = 8)
	public Double getVoltagebdownlimit() {
		return voltagebdownlimit;
	}

	public void setVoltagebdownlimit(Double voltagebdownlimit) {
		this.voltagebdownlimit = voltagebdownlimit;
	}

	@Column(name = "voltagecuplimit", precision = 8)
	public Double getVoltagecuplimit() {
		return voltagecuplimit;
	}

	public void setVoltagecuplimit(Double voltagecuplimit) {
		this.voltagecuplimit = voltagecuplimit;
	}

	@Column(name = "voltagecdownlimit", precision = 8)
	public Double getVoltagecdownlimit() {
		return voltagecdownlimit;
	}

	public void setVoltagecdownlimit(Double voltagecdownlimit) {
		this.voltagecdownlimit = voltagecdownlimit;
	}
	
	@Column(name = "BPSZPL", precision = 8)
	public Double getBpszpl() {
		return bpszpl;
	}

	public void setBpszpl(Double bpszpl) {
		this.bpszpl = bpszpl;
	}

	@Column(name = "BPYXPL", precision = 8)
	public Double getBpyxpl() {
		return bpyxpl;
	}

	public void setBpyxpl(Double bpyxpl) {
		this.bpyxpl = bpyxpl;
	}
}