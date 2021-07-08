package com.cosog.utils;

import java.util.ResourceBundle;

/**
 * <p>描述：报警配置表</p>
 * @author 吕磊  2014-06-05
 *
 */
public class AlarmConfig{

	private static String CONFIG_FILE="config.alarmconfig";
	private static String csmc;
	private static String bjz;
	private static String jh;
	private static String ssz;
	private static String bjxx;
	private static String bjnr;
	private static String bjsj;
	private static String qrsj;
	private static String qrr;
	private static String bjzt;
	private static String jbsj;
	private static String yy;
	private static String ty;
	private static String jklw;
	private static String dym;
	private static String gtcs;
	private static String cc;
	private static String cc1;
	private static String zdzh;
	private static String zxzh;
	private static String zdjg;
	private static String cmxs;
	private static String dcs;
	private static String scczddl;
	private static String xcczddl;
	private static String phd;
    private static String axdl;
    private static String bxdl;    private static String cxdl;    private static String axdy;    private static String bxdy;    private static String cxdy;    private static String pjyggl;    private static String pjwggl;    private static String pjglys;    private static String ljhdl;    private static String ljfdl;    private static String rfid;    private static String rfidkh;    private static String hfbz;    private static String rcsj;    private static String lcsj;    private static String xm;
	private static String bjjb;
	private static String jc;
    
	static {
		getconfiguration();
	}

	public AlarmConfig() {
		
	}
	
	static void getconfiguration() {
		ResourceBundle resources = ResourceBundle.getBundle(AlarmConfig.CONFIG_FILE);
		
		csmc = resources.getString("csmc");
		bjz = resources.getString("bjz");
		jh = resources.getString("jh");
		ssz = resources.getString("ssz");
		bjxx = resources.getString("bjxx");
		bjnr = resources.getString("bjnr");
		bjsj = resources.getString("bjsj");
		qrsj = resources.getString("qrsj");
		qrr = resources.getString("qrr");
		bjzt = resources.getString("bjzt");
		jbsj = resources.getString("jbsj");
		yy = resources.getString("yy");
		ty = resources.getString("ty");
		jklw = resources.getString("jklw");
		dym = resources.getString("dym");
		gtcs = resources.getString("gtcs");
		cc = resources.getString("cc");
		cc1 = resources.getString("cc1");
		zdzh = resources.getString("zdzh");
		zxzh = resources.getString("zxzh");
		zdjg = resources.getString("zdjg");
		cmxs = resources.getString("cmxs");
		dcs = resources.getString("dcs");
		scczddl = resources.getString("scczddl");
		xcczddl = resources.getString("xcczddl");
		phd = resources.getString("phd");
		axdl = resources.getString("axdl");
		bxdl = resources.getString("bxdl");
		cxdl = resources.getString("cxdl");
		axdy = resources.getString("axdy");
		bxdy = resources.getString("bxdy");
		cxdy = resources.getString("cxdy");
		pjyggl = resources.getString("pjyggl");
		pjwggl = resources.getString("pjwggl");
		pjglys = resources.getString("pjglys");
		ljhdl = resources.getString("ljhdl");
		ljfdl = resources.getString("ljfdl");
		rfid = resources.getString("rfid");
		rfidkh = resources.getString("rfidkh");
		hfbz = resources.getString("hfbz");
		rcsj = resources.getString("rcsj");
		lcsj = resources.getString("lcsj");
		xm = resources.getString("xm");
		bjjb = resources.getString("bjjb");
		jc = resources.getString("jc");
		
		
		
		
	
	}
    
    public static String getCONFIG_FILE() {
		return CONFIG_FILE;
	}
	public static void setCONFIG_FILE(String cONFIG_FILE) {
		CONFIG_FILE = cONFIG_FILE;
	}
	public static String getCsmc() {
		return csmc;
	}
	public static void setCsmc(String csmc) {
		AlarmConfig.csmc = csmc;
	}
	public static String getBjz() {
		return bjz;
	}
	public static void setBjz(String bjz) {
		AlarmConfig.bjz = bjz;
	}
	public static String getJh() {
		return jh;
	}
	public static void setJh(String jh) {
		AlarmConfig.jh = jh;
	}
	public static String getSsz() {
		return ssz;
	}
	public static void setSsz(String ssz) {
		AlarmConfig.ssz = ssz;
	}
	public static String getBjxx() {
		return bjxx;
	}
	public static void setBjxx(String bjxx) {
		AlarmConfig.bjxx = bjxx;
	}
	public static String getBjnr() {
		return bjnr;
	}
	public static void setBjnr(String bjnr) {
		AlarmConfig.bjnr = bjnr;
	}
	public static String getBjsj() {
		return bjsj;
	}
	public static void setBjsj(String bjsj) {
		AlarmConfig.bjsj = bjsj;
	}
	public static String getQrsj() {
		return qrsj;
	}
	public static void setQrsj(String qrsj) {
		AlarmConfig.qrsj = qrsj;
	}
	public static String getQrr() {
		return qrr;
	}
	public static void setQrr(String qrr) {
		AlarmConfig.qrr = qrr;
	}
	public static String getBjzt() {
		return bjzt;
	}
	public static void setBjzt(String bjzt) {
		AlarmConfig.bjzt = bjzt;
	}
	public static String getJbsj() {
		return jbsj;
	}
	public static void setJbsj(String jbsj) {
		AlarmConfig.jbsj = jbsj;
	}
	public static String getYy() {
		return yy;
	}
	public static void setYy(String yy) {
		AlarmConfig.yy = yy;
	}
	public static String getTy() {
		return ty;
	}
	public static void setTy(String ty) {
		AlarmConfig.ty = ty;
	}
	public static String getJklw() {
		return jklw;
	}
	public static void setJklw(String jklw) {
		AlarmConfig.jklw = jklw;
	}
	public static String getDym() {
		return dym;
	}
	public static void setDym(String dym) {
		AlarmConfig.dym = dym;
	}
	public static String getGtcs() {
		return gtcs;
	}
	public static void setGtcs(String gtcs) {
		AlarmConfig.gtcs = gtcs;
	}
	public static String getCc() {
		return cc;
	}
	public static void setCc(String cc) {
		AlarmConfig.cc = cc;
	}
	public static String getCc1() {
		return cc1;
	}
	public static void setCc1(String cc1) {
		AlarmConfig.cc1 = cc1;
	}
	public static String getZdzh() {
		return zdzh;
	}
	public static void setZdzh(String zdzh) {
		AlarmConfig.zdzh = zdzh;
	}
	public static String getZxzh() {
		return zxzh;
	}
	public static void setZxzh(String zxzh) {
		AlarmConfig.zxzh = zxzh;
	}
	public static String getZdjg() {
		return zdjg;
	}
	public static void setZdjg(String zdjg) {
		AlarmConfig.zdjg = zdjg;
	}
	public static String getCmxs() {
		return cmxs;
	}
	public static void setCmxs(String cmxs) {
		AlarmConfig.cmxs = cmxs;
	}
	public static String getDcs() {
		return dcs;
	}
	public static void setDcs(String dcs) {
		AlarmConfig.dcs = dcs;
	}
	public static String getScczddl() {
		return scczddl;
	}
	public static void setScczddl(String scczddl) {
		AlarmConfig.scczddl = scczddl;
	}
	public static String getXcczddl() {
		return xcczddl;
	}
	public static void setXcczddl(String xcczddl) {
		AlarmConfig.xcczddl = xcczddl;
	}
	public static String getPhd() {
		return phd;
	}
	public static void setPhd(String phd) {
		AlarmConfig.phd = phd;
	}
	public static String getAxdl() {
		return axdl;
	}
	public static void setAxdl(String axdl) {
		AlarmConfig.axdl = axdl;
	}
	public static String getBxdl() {
		return bxdl;
	}
	public static void setBxdl(String bxdl) {
		AlarmConfig.bxdl = bxdl;
	}
	public static String getCxdl() {
		return cxdl;
	}
	public static void setCxdl(String cxdl) {
		AlarmConfig.cxdl = cxdl;
	}
	public static String getAxdy() {
		return axdy;
	}
	public static void setAxdy(String axdy) {
		AlarmConfig.axdy = axdy;
	}
	public static String getBxdy() {
		return bxdy;
	}
	public static void setBxdy(String bxdy) {
		AlarmConfig.bxdy = bxdy;
	}
	public static String getCxdy() {
		return cxdy;
	}
	public static void setCxdy(String cxdy) {
		AlarmConfig.cxdy = cxdy;
	}
	public static String getPjyggl() {
		return pjyggl;
	}
	public static void setPjyggl(String pjyggl) {
		AlarmConfig.pjyggl = pjyggl;
	}
	public static String getPjwggl() {
		return pjwggl;
	}
	public static void setPjwggl(String pjwggl) {
		AlarmConfig.pjwggl = pjwggl;
	}
	public static String getPjglys() {
		return pjglys;
	}
	public static void setPjglys(String pjglys) {
		AlarmConfig.pjglys = pjglys;
	}
	public static String getLjhdl() {
		return ljhdl;
	}
	public static void setLjhdl(String ljhdl) {
		AlarmConfig.ljhdl = ljhdl;
	}
	public static String getLjfdl() {
		return ljfdl;
	}
	public static void setLjfdl(String ljfdl) {
		AlarmConfig.ljfdl = ljfdl;
	}
	public static String getRfid() {
		return rfid;
	}
	public static void setRfid(String rfid) {
		AlarmConfig.rfid = rfid;
	}
	public static String getRfidkh() {
		return rfidkh;
	}
	public static void setRfidkh(String rfidkh) {
		AlarmConfig.rfidkh = rfidkh;
	}
	public static String getHfbz() {
		return hfbz;
	}
	public static void setHfbz(String hfbz) {
		AlarmConfig.hfbz = hfbz;
	}
	public static String getRcsj() {
		return rcsj;
	}
	public static void setRcsj(String rcsj) {
		AlarmConfig.rcsj = rcsj;
	}
	public static String getLcsj() {
		return lcsj;
	}
	public static void setLcsj(String lcsj) {
		AlarmConfig.lcsj = lcsj;
	}
	public static String getXm() {
		return xm;
	}
	public static void setXm(String xm) {
		AlarmConfig.xm = xm;
	}
	public static String getBjjb() {
		return bjjb;
		}

	public static void setBjjb(String bjjb) {
		AlarmConfig.bjjb = bjjb;
		}
	
	public static String getJc() {
		return jc;
		}

	public static void setJc(String jc) {
		AlarmConfig.jc = jc;
		}
	}                         