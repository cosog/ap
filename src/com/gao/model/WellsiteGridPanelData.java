package com.gao.model;
/**
 * Gson解析前台井场GridPanel数据所用到的类
 * 
 * */
public class WellsiteGridPanelData {

	private Integer jlbh;
    private String orgName;
    private String dwbh;
    private String resName;
    private String yqcbh;
    private String jc;
    private String jclxName;
    private Integer jclx;
    private float lng;
    private float lat;
    private Integer showLevel;
    private Integer pxbh;
    private String  threadid;
	public WellsiteGridPanelData() {
		super();
	}
	public WellsiteGridPanelData(Integer jlbh, String orgName, String dwbh, String resName, String yqcbh, String jc,
			String jclxName, Integer jclx, float lng, float lat, Integer showLevel, Integer pxbh, String threadid) {
		super();
		this.jlbh = jlbh;
		this.orgName = orgName;
		this.dwbh = dwbh;
		this.resName = resName;
		this.yqcbh = yqcbh;
		this.jc = jc;
		this.jclxName = jclxName;
		this.jclx = jclx;
		this.lng = lng;
		this.lat = lat;
		this.showLevel = showLevel;
		this.pxbh = pxbh;
		this.threadid = threadid;
	}
	public Integer getJlbh() {
		return jlbh;
	}
	public void setJlbh(Integer jlbh) {
		this.jlbh = jlbh;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getDwbh() {
		return dwbh;
	}
	public void setDwbh(String dwbh) {
		this.dwbh = dwbh;
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
	public String getJc() {
		return jc;
	}
	public void setJc(String jc) {
		this.jc = jc;
	}
	public String getJclxName() {
		return jclxName;
	}
	public void setJclxName(String jclxName) {
		this.jclxName = jclxName;
	}
	public Integer getJclx() {
		return jclx;
	}
	public void setJclx(Integer jclx) {
		this.jclx = jclx;
	}
	public float getLng() {
		return lng;
	}
	public void setLng(float lng) {
		this.lng = lng;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public Integer getShowLevel() {
		return showLevel;
	}
	public void setShowLevel(Integer showLevel) {
		this.showLevel = showLevel;
	}
	public Integer getPxbh() {
		return pxbh;
	}
	public void setPxbh(Integer pxbh) {
		this.pxbh = pxbh;
	}
	public String getThreadid() {
		return threadid;
	}
	public void setThreadid(String threadid) {
		this.threadid = threadid;
	}
	
}
