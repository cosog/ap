package com.cosog.model;

import java.util.List;
import java.util.Map;

public class RealtimeTotalInfo implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer deviceId;
	
	private String deviceName;
	
	private String acqTime;
	
	private Integer commStatus;
	
	private float commTime;
	
	private float commEff;
	
	private String commRange;
	
	private Integer runStatus;
	
	private float runTime;
	
	private float runEff;
	
	private String runRange;
	
	private String onLineAcqTime;
	
	private Integer onLineCommStatus;
	
	private float onLineCommTime;
	
	private float onLineCommEff;
	
	private String onLineCommRange;
	
	private Map<String,TotalItem> totalItemMap;
	
	
	public static class TotalItem implements java.io.Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private String item;
		
		private ItemTotalStatus totalStatus;
		
		public String getItem() {
			return item;
		}


		public void setItem(String item) {
			this.item = item;
		}


		public ItemTotalStatus getTotalStatus() {
			return totalStatus;
		}


		public void setTotalStatus(ItemTotalStatus totalStatus) {
			this.totalStatus = totalStatus;
		}
	}
	
	
	
	public static class ItemTotalStatus implements java.io.Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private String calTime;
		
		private int count;
		
		private float sum;
		
		private float maxValue;
		
		private float minValue;
		
		private float avgValue;
		
		private float oldestValue;
		
		private float newestValue;
		
		private float dailyTotalValue;
		
		private boolean dailyTotalSign;//是否进行日汇总计算

		public ItemTotalStatus() {
			super();
		}

		public ItemTotalStatus(String calTime, int count, float sum, float maxValue, float minValue, float avgValue,
				float oldestValue, float newestValue, float dailyTotalValue) {
			super();
			this.calTime = calTime;
			this.count = count;
			this.sum = sum;
			this.maxValue = maxValue;
			this.minValue = minValue;
			this.avgValue = avgValue;
			this.oldestValue = oldestValue;
			this.newestValue = newestValue;
			this.dailyTotalValue = dailyTotalValue;
		}

		public String getCalTime() {
			return calTime;
		}

		public void setCalTime(String calTime) {
			this.calTime = calTime;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public float getSum() {
			return sum;
		}

		public void setSum(float sum) {
			this.sum = sum;
		}

		public float getMaxValue() {
			return maxValue;
		}

		public void setMaxValue(float maxValue) {
			this.maxValue = maxValue;
		}

		public float getMinValue() {
			return minValue;
		}

		public void setMinValue(float minValue) {
			this.minValue = minValue;
		}

		public float getAvgValue() {
			return avgValue;
		}

		public void setAvgValue(float avgValue) {
			this.avgValue = avgValue;
		}

		public float getOldestValue() {
			return oldestValue;
		}

		public void setOldestValue(float oldestValue) {
			this.oldestValue = oldestValue;
		}

		public float getNewestValue() {
			return newestValue;
		}

		public void setNewestValue(float newestValue) {
			this.newestValue = newestValue;
		}

		public float getDailyTotalValue() {
			return dailyTotalValue;
		}

		public void setDailyTotalValue(float dailyTotalValue) {
			this.dailyTotalValue = dailyTotalValue;
		}

		public boolean getDailyTotalSign() {
			return dailyTotalSign;
		}

		public void setDailyTotalSign(boolean dailyTotalSign) {
			this.dailyTotalSign = dailyTotalSign;
		}
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getAcqTime() {
		return acqTime;
	}

	public void setAcqTime(String acqTime) {
		this.acqTime = acqTime;
	}

	public Integer getCommStatus() {
		return commStatus;
	}

	public void setCommStatus(Integer commStatus) {
		this.commStatus = commStatus;
	}

	public float getCommTime() {
		return commTime;
	}

	public void setCommTime(float commTime) {
		this.commTime = commTime;
	}

	public float getCommEff() {
		return commEff;
	}

	public void setCommEff(float commEff) {
		this.commEff = commEff;
	}

	public String getCommRange() {
		return commRange;
	}

	public void setCommRange(String commRange) {
		this.commRange = commRange;
	}

	public Integer getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
	}

	public float getRunTime() {
		return runTime;
	}

	public void setRunTime(float runTime) {
		this.runTime = runTime;
	}

	public float getRunEff() {
		return runEff;
	}

	public void setRunEff(float runEff) {
		this.runEff = runEff;
	}
	
	public String getRunRange() {
		return runRange;
	}
	
	public void setRunRange(String runRange) {
		this.runRange = runRange;
	}

	public String getOnLineAcqTime() {
		return onLineAcqTime;
	}

	public void setOnLineAcqTime(String onLineAcqTime) {
		this.onLineAcqTime = onLineAcqTime;
	}

	public Integer getOnLineCommStatus() {
		return onLineCommStatus;
	}

	public void setOnLineCommStatus(Integer onLineCommStatus) {
		this.onLineCommStatus = onLineCommStatus;
	}

	public float getOnLineCommTime() {
		return onLineCommTime;
	}

	public void setOnLineCommTime(float onLineCommTime) {
		this.onLineCommTime = onLineCommTime;
	}

	public float getOnLineCommEff() {
		return onLineCommEff;
	}

	public void setOnLineCommEff(float onLineCommEff) {
		this.onLineCommEff = onLineCommEff;
	}

	public String getOnLineCommRange() {
		return onLineCommRange;
	}

	public void setOnLineCommRange(String onLineCommRange) {
		this.onLineCommRange = onLineCommRange;
	}

	public Map<String, TotalItem> getTotalItemMap() {
		return totalItemMap;
	}

	public void setTotalItemMap(Map<String, TotalItem> totalItemMap) {
		this.totalItemMap = totalItemMap;
	}
	
}
