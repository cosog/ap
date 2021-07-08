package com.cosog.model.gridmodel;

public class WellringGridPanelData {
	private int jlbh;
    private String jhh;
    private float jljhcsrcyl;
    private float jhcsl;
    private float jljhrcyl;
    private float jljhrcyld;
    private String gxrq;
    private String threadid;
	public WellringGridPanelData() {
		super();
	}
	public WellringGridPanelData(int jlbh, String jhh, float jljhcsrcyl, float jhcsl, float jljhrcyl, float jljhrcyld,
			String gxrq, String threadid) {
		super();
		this.jlbh = jlbh;
		this.jhh = jhh;
		this.jljhcsrcyl = jljhcsrcyl;
		this.jhcsl = jhcsl;
		this.jljhrcyl = jljhrcyl;
		this.jljhrcyld = jljhrcyld;
		this.gxrq = gxrq;
		this.threadid = threadid;
	}
	public int getJlbh() {
		return jlbh;
	}
	public void setJlbh(int jlbh) {
		this.jlbh = jlbh;
	}
	public String getJhh() {
		return jhh;
	}
	public void setJhh(String jhh) {
		this.jhh = jhh;
	}
	public float getJljhcsrcyl() {
		return jljhcsrcyl;
	}
	public void setJljhcsrcyl(float jljhcsrcyl) {
		this.jljhcsrcyl = jljhcsrcyl;
	}
	public float getJhcsl() {
		return jhcsl;
	}
	public void setJhcsl(float jhcsl) {
		this.jhcsl = jhcsl;
	}
	public float getJljhrcyl() {
		return jljhrcyl;
	}
	public void setJljhrcyl(float jljhrcyl) {
		this.jljhrcyl = jljhrcyl;
	}
	public float getJljhrcyld() {
		return jljhrcyld;
	}
	public void setJljhrcyld(float jljhrcyld) {
		this.jljhrcyld = jljhrcyld;
	}
	public String getGxrq() {
		return gxrq;
	}
	public void setGxrq(String gxrq) {
		this.gxrq = gxrq;
	}
	public String getThreadid() {
		return threadid;
	}
	public void setThreadid(String threadid) {
		this.threadid = threadid;
	}
    
    
}
