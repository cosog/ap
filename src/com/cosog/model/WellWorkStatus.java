package com.cosog.model;

public class WellWorkStatus {
	private int id;
	private String gkmc;
	private int total;
	public WellWorkStatus(int id, String gkmc, int total) {
		super();
		this.id = id;
		this.gkmc = gkmc;
		this.total = total;
	}
	public WellWorkStatus() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGkmc() {
		return gkmc;
	}
	public void setGkmc(String gkmc) {
		this.gkmc = gkmc;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}
