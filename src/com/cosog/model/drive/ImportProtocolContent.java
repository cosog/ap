package com.cosog.model.drive;

import java.util.List;

public class ImportProtocolContent {

	private List<AcqUnit> acqUnitList;
	
	private List<Integer> displayUnitList;
	
	private List<Integer> alarmUnitList;
	
	private List<Integer> acqInstanceList;
	
	private List<Integer> displayInstanceList;
	
	private List<Integer> alarmInstanceList;
	
	public static class AcqUnit{
		private int id;
		
		private List<Integer> acqGroupList;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public List<Integer> getAcqGroupList() {
			return acqGroupList;
		}
		public void setAcqGroupList(List<Integer> acqGroupList) {
			this.acqGroupList = acqGroupList;
		}
	}

	public List<AcqUnit> getAcqUnitList() {
		return acqUnitList;
	}

	public void setAcqUnitList(List<AcqUnit> acqUnitList) {
		this.acqUnitList = acqUnitList;
	}

	public List<Integer> getDisplayUnitList() {
		return displayUnitList;
	}

	public void setDisplayUnitList(List<Integer> displayUnitList) {
		this.displayUnitList = displayUnitList;
	}

	public List<Integer> getAlarmUnitList() {
		return alarmUnitList;
	}

	public void setAlarmUnitList(List<Integer> alarmUnitList) {
		this.alarmUnitList = alarmUnitList;
	}

	public List<Integer> getAcqInstanceList() {
		return acqInstanceList;
	}

	public void setAcqInstanceList(List<Integer> acqInstanceList) {
		this.acqInstanceList = acqInstanceList;
	}

	public List<Integer> getDisplayInstanceList() {
		return displayInstanceList;
	}

	public void setDisplayInstanceList(List<Integer> displayInstanceList) {
		this.displayInstanceList = displayInstanceList;
	}

	public List<Integer> getAlarmInstanceList() {
		return alarmInstanceList;
	}

	public void setAlarmInstanceList(List<Integer> alarmInstanceList) {
		this.alarmInstanceList = alarmInstanceList;
	}
}
