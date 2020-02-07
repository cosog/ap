package com.cosog.model.scada;

public class CurvePointData {
	private Float XData;
	private Float YData;
	public CurvePointData() {
		super();
	}
	public CurvePointData(Float xData, Float yData) {
		super();
		XData = xData;
		YData = yData;
	}
	public Float getXData() {
		return XData;
	}
	public void setXData(Float xData) {
		XData = xData;
	}
	public Float getYData() {
		return YData;
	}
	public void setYData(Float yData) {
		YData = yData;
	}
	
}
