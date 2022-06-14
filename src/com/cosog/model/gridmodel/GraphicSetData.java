package com.cosog.model.gridmodel;

import java.util.List;

public class GraphicSetData {

	public List<Graphic> History;
	
	public static class  Graphic{
		private String Type;
		private String yAxisMaxValue;
		private String yAxisMinValue;
		public String getType() {
			return Type;
		}
		public void setType(String type) {
			Type = type;
		}
		public String getyAxisMaxValue() {
			return yAxisMaxValue;
		}
		public void setyAxisMaxValue(String yAxisMaxValue) {
			this.yAxisMaxValue = yAxisMaxValue;
		}
		public String getyAxisMinValue() {
			return yAxisMinValue;
		}
		public void setyAxisMinValue(String yAxisMinValue) {
			this.yAxisMinValue = yAxisMinValue;
		}
		
	}

	public List<Graphic> getHistory() {
		return History;
	}

	public void setHistory(List<Graphic> history) {
		History = history;
	}
}
