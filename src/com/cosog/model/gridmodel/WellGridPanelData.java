package com.cosog.model.gridmodel;

public class WellGridPanelData {
	private Integer jlbh;
	private String dwbh="";
	private String jc="";
	private String jhh="";
	private String jh="";
    private Integer jlx;
    private Integer ssjw;
    private Integer sszcdy;
    private String rgzsjd="";
    private float dmx=-9999;
    private float dmy=-9999;
    private Integer showLevel=1;
    private String orgName="";
    private String resName="";
    private String yqcbh="";
    private float rgzsj;
    private float mpcch;
    private float qbc;
    private String jlxName="";
    private String ssjwName="";
    private String sszcdyName="";
    private Integer jslx;
    private Integer pxbh;
    private String jslxName="";
	
	public WellGridPanelData() {
		super();
	}
	
	public WellGridPanelData(Integer jlbh, String dwbh, String jc, String jhh, String jh, Integer jlx, Integer ssjw,
			Integer sszcdy, String rgzsjd, float dmx, float dmy, int showLevel, String orgName, String resName,
			String yqcbh, float rgzsj,float mpcch,float qbc, String jlxName, String ssjwName, String sszcdyName,
			int jslx, String jslxName,int pxbh) {
		super();
		this.jlbh = jlbh;
		this.dwbh = dwbh;
		this.jc = jc;
		this.jhh = jhh;
		this.jh = jh;
		this.jlx = jlx;
		this.ssjw = ssjw;
		this.sszcdy = sszcdy;
		this.rgzsjd = rgzsjd;
		this.dmx = dmx;
		this.dmy = dmy;
		this.showLevel = showLevel;
		this.orgName = orgName;
		this.resName = resName;
		this.yqcbh = yqcbh;
		this.rgzsj = rgzsj;
		this.mpcch=mpcch;
		this.qbc=qbc;
		this.jlxName = jlxName;
		this.ssjwName = ssjwName;
		this.sszcdyName = sszcdyName;
		this.jslx = jslx;
		this.jslxName = jslxName;
		this.pxbh=pxbh;
	}

	public Integer getJlbh() {
		return jlbh;
	}
	public void setJlbh(Integer jlbh) {
		this.jlbh = jlbh;
	}
	public String getDwbh() {
		return dwbh;
	}
	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
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
	public Integer getJlx() {
		return jlx;
	}
	public void setJlx(Integer jlx) {
		this.jlx = jlx;
	}
	public Integer getSsjw() {
		return ssjw;
	}
	public void setSsjw(Integer ssjw) {
		this.ssjw = ssjw;
	}
	public Integer getSszcdy() {
		return sszcdy;
	}
	public void setSszcdy(Integer sszcdy) {
		this.sszcdy = sszcdy;
	}
	public String getRgzsjd() {
		return rgzsjd;
	}
	public void setRgzsjd(String rgzsjd) {
		this.rgzsjd = rgzsjd;
	}
	public float getDmx() {
		return dmx;
	}
	public void setDmx(float dmx) {
		this.dmx = dmx;
	}
	public float getDmy() {
		return dmy;
	}
	public void setDmy(float dmy) {
		this.dmy = dmy;
	}
	public int getShowLevel() {
		return showLevel;
	}
	public void setShowLevel(int showLevel) {
		this.showLevel = showLevel;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getYqcbh() {
		return yqcbh;
	}
	public void setYqcbh(String yqcbh) {
		this.yqcbh = yqcbh;
	}
	public float getRgzsj() {
		return rgzsj;
	}
	public void setRgzsj(float rgzsj) {
		this.rgzsj = rgzsj;
	}
	public String getJlxName() {
		return jlxName;
	}
	public void setJlxName(String jlxName) {
		this.jlxName = jlxName;
	}
	public String getSsjwName() {
		return ssjwName;
	}
	public void setSsjwName(String ssjwName) {
		this.ssjwName = ssjwName;
	}
	public String getSszcdyName() {
		return sszcdyName;
	}
	public void setSszcdyName(String sszcdyName) {
		this.sszcdyName = sszcdyName;
	}

	public int getJslx() {
		return jslx;
	}

	public void setJslx(int jslx) {
		this.jslx = jslx;
	}

	public String getJslxName() {
		return jslxName;
	}

	public void setJslxName(String jslxName) {
		this.jslxName = jslxName;
	}

	public Integer getPxbh() {
		return pxbh;
	}

	public void setPxbh(Integer pxbh) {
		this.pxbh = pxbh;
	}

	public void setShowLevel(Integer showLevel) {
		this.showLevel = showLevel;
	}

	public void setJslx(Integer jslx) {
		this.jslx = jslx;
	}

	public float getMpcch() {
		return mpcch;
	}

	public void setMpcch(float mpcch) {
		this.mpcch = mpcch;
	}

	public float getQbc() {
		return qbc;
	}

	public void setQbc(float qbc) {
		this.qbc = qbc;
	}
}
