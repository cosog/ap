package com.cosog.model.graphical;

public class ContrastCardData {
	private String time;
	private String djgtsj;
	private String gtid;
	private String bzgtid;
	public ContrastCardData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ContrastCardData(String time, String djgtsj, String gtid,
			String bzgtid) {
		super();
		this.time = time;
		this.djgtsj = djgtsj;
		this.gtid = gtid;
		this.bzgtid = bzgtid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDjgtsj() {
		return djgtsj;
	}
	public void setDjgtsj(String djgtsj) {
		this.djgtsj = djgtsj;
	}
	public String getGtid() {
		return gtid;
	}
	public void setGtid(String gtid) {
		this.gtid = gtid;
	}
	public String getBzgtid() {
		return bzgtid;
	}
	public void setBzgtid(String bzgtid) {
		this.bzgtid = bzgtid;
	}
}