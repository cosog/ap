package com.cosog.model.scada;

public class RealtimeDataBean {
	private String dataitem1;
	private Float value1;
	private String dataitem2;
	private Float value2;
	private String dataitem3;
	private Float value3;
	private String dataitem4;
	private Float value4;
	
	public RealtimeDataBean() {
		super();
	}
	
	public RealtimeDataBean(String dataitem1, Float value1, String dataitem2,
			Float value2, String dataitem3, Float value3, String dataitem4,
			Float value4) {
		super();
		this.dataitem1 = dataitem1;
		this.value1 = value1;
		this.dataitem2 = dataitem2;
		this.value2 = value2;
		this.dataitem3 = dataitem3;
		this.value3 = value3;
		this.dataitem4 = dataitem4;
		this.value4 = value4;
	}

	public String getDataitem1() {
		return dataitem1;
	}
	public void setDataitem1(String dataitem1) {
		this.dataitem1 = dataitem1;
	}
	public float getValue1() {
		return value1;
	}
	public void setValue1(float value1) {
		this.value1 = value1;
	}
	public String getDataitem2() {
		return dataitem2;
	}
	public void setDataitem2(String dataitem2) {
		this.dataitem2 = dataitem2;
	}
	public float getValue2() {
		return value2;
	}
	public void setValue2(float value2) {
		this.value2 = value2;
	}
	public String getDataitem3() {
		return dataitem3;
	}
	public void setDataitem3(String dataitem3) {
		this.dataitem3 = dataitem3;
	}
	public float getValue3() {
		return value3;
	}
	public void setValue3(float value3) {
		this.value3 = value3;
	}
	public String getDataitem4() {
		return dataitem4;
	}
	public void setDataitem4(String dataitem4) {
		this.dataitem4 = dataitem4;
	}
	public float getValue4() {
		return value4;
	}
	public void setValue4(float value4) {
		this.value4 = value4;
	}
}
