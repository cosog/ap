package com.cosog.model;

public class CurvePumpingUnit implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String jh;
	private String jssj;
	private double jsdjrcyl;
	private double jsdjrcyl1;
	private double jsdjrcyld;
	private double hsl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJh() {
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public String getJssj() {
		return jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}

	public double getJsdjrcyl() {
		return jsdjrcyl;
	}

	public void setJsdjrcyl(double jsdjrcyl) {
		this.jsdjrcyl = jsdjrcyl;
	}

	public double getJsdjrcyl1() {
		return jsdjrcyl1;
	}

	public void setJsdjrcyl1(double jsdjrcyl1) {
		this.jsdjrcyl1 = jsdjrcyl1;
	}

	public double getJsdjrcyld() {
		return jsdjrcyld;
	}

	public void setJsdjrcyld(double jsdjrcyld) {
		this.jsdjrcyld = jsdjrcyld;
	}

	public double getHsl() {
		return hsl;
	}

	public void setHsl(double hsl) {
		this.hsl = hsl;
	}

}