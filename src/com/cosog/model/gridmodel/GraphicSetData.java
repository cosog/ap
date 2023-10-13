package com.cosog.model.gridmodel;

import java.util.List;

public class GraphicSetData {

	public List<Graphic> History;
	public List<Graphic> Report;
	public List<Graphic> DailyReport;
	
	public static class  Graphic{
		private String itemCode;
		private String itemType;
		private String yAxisMaxValue;
		private String yAxisMinValue;
		
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
		public String getItemCode() {
			return itemCode;
		}
		public void setItemCode(String itemCode) {
			this.itemCode = itemCode;
		}
		public String getItemType() {
			return itemType;
		}
		public void setItemType(String itemType) {
			this.itemType = itemType;
		}
	}

	public List<Graphic> getHistory() {
		return History;
	}

	public void setHistory(List<Graphic> history) {
		History = history;
	}

	public List<Graphic> getReport() {
		return Report;
	}

	public void setReport(List<Graphic> report) {
		Report = report;
	}

	public List<Graphic> getDailyReport() {
		return DailyReport;
	}

	public void setDailyReport(List<Graphic> dailyReport) {
		DailyReport = dailyReport;
	}
}
