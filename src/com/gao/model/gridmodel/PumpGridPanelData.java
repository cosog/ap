package com.gao.model.gridmodel;

public class PumpGridPanelData {
	private int jlbh;
    private String sccj;
    private String cybxh;
    private String blx;
    private int bjb;
    private float bj;
    private float zsc;
    private String blxName;
    private String bjbName;
    private String btlxName;
    private String id;
	public PumpGridPanelData() {
		super();
	}
	public PumpGridPanelData(int jlbh, String sccj, String cybxh, String blx, int bjb, float bj, float zsc,
			String blxName, String bjbName, String btlxName, String id) {
		super();
		this.jlbh = jlbh;
		this.sccj = sccj;
		this.cybxh = cybxh;
		this.blx = blx;
		this.bjb = bjb;
		this.bj = bj;
		this.zsc = zsc;
		this.blxName = blxName;
		this.bjbName = bjbName;
		this.btlxName = btlxName;
		this.id = id;
	}
	public int getJlbh() {
		return jlbh;
	}
	public void setJlbh(int jlbh) {
		this.jlbh = jlbh;
	}
	public String getSccj() {
		return sccj;
	}
	public void setSccj(String sccj) {
		this.sccj = sccj;
	}
	public String getCybxh() {
		return cybxh;
	}
	public void setCybxh(String cybxh) {
		this.cybxh = cybxh;
	}
	public String getBlx() {
		return blx;
	}
	public void setBlx(String blx) {
		this.blx = blx;
	}
	public int getBjb() {
		return bjb;
	}
	public void setBjb(int bjb) {
		this.bjb = bjb;
	}
	public float getBj() {
		return bj;
	}
	public void setBj(float bj) {
		this.bj = bj;
	}
	public float getZsc() {
		return zsc;
	}
	public void setZsc(float zsc) {
		this.zsc = zsc;
	}
	public String getBlxName() {
		return blxName;
	}
	public void setBlxName(String blxName) {
		this.blxName = blxName;
	}
	public String getBjbName() {
		return bjbName;
	}
	public void setBjbName(String bjbName) {
		this.bjbName = bjbName;
	}
	public String getBtlxName() {
		return btlxName;
	}
	public void setBtlxName(String btlxName) {
		this.btlxName = btlxName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    
}
