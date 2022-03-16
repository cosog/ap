package com.cosog.model.gridmodel;

import java.util.List;

public class WellHandsontableChangedData {

	private List<String> delidslist;

    private List<Updatelist> updatelist;

    private List<Updatelist> insertlist;

    public void setDelidslist(List<String> delidslist){
        this.delidslist = delidslist;
    }
    public List<String> getDelidslist(){
        return this.delidslist;
    }
    public void setUpdatelist(List<Updatelist> updatelist){
        this.updatelist = updatelist;
    }
    public List<Updatelist> getUpdatelist(){
        return this.updatelist;
    }
    public void setInsertlist(List<Updatelist> insertlist){
        this.insertlist = insertlist;
    }
    public List<Updatelist> getInsertlist(){
        return this.insertlist;
    }
	
	public static class Updatelist
	{
	    private String id;

	    private String orgName="";

	    private String wellName="";
	    
	    private String protocolName="";
	    
	    private String resName="";
	    
	    private String liftingTypeName="";
	    
	    private String acquisitionUnit="";

	    private String instanceName="";
	    
	    private String alarmInstanceName="";

	    private String signInId="";

	    private String slave="";
	    
	    private String videoUrl="";
	    
	    private String statusName="";
	    
	    private String sortNum="";
	    
	    private int saveSign;
	    
	    private String saveStr;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getOrgName() {
			return orgName;
		}

		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}

		public String getWellName() {
			return wellName;
		}

		public void setWellName(String wellName) {
			this.wellName = wellName;
		}

		public String getVideoUrl() {
			return videoUrl;
		}

		public void setVideoUrl(String videoUrl) {
			this.videoUrl = videoUrl;
		}

		public String getSortNum() {
			return sortNum;
		}

		public void setSortNum(String sortNum) {
			this.sortNum = sortNum;
		}

		public String getSignInId() {
			return signInId;
		}

		public void setSignInId(String signInId) {
			this.signInId = signInId;
		}

		public String getSlave() {
			return slave;
		}

		public void setSlave(String slave) {
			this.slave = slave;
		}

		public String getInstanceName() {
			return instanceName;
		}

		public void setInstanceName(String instanceName) {
			this.instanceName = instanceName;
		}

		public String getAlarmInstanceName() {
			return alarmInstanceName;
		}

		public void setAlarmInstanceName(String alarmInstanceName) {
			this.alarmInstanceName = alarmInstanceName;
		}

		public int getSaveSign() {
			return saveSign;
		}

		public void setSaveSign(int saveSign) {
			this.saveSign = saveSign;
		}

		public String getSaveStr() {
			return saveStr;
		}

		public void setSaveStr(String saveStr) {
			this.saveStr = saveStr;
		}

		public String getStatusName() {
			return statusName;
		}

		public void setStatusName(String statusName) {
			this.statusName = statusName;
		}

		public String getProtocolName() {
			return protocolName;
		}

		public void setProtocolName(String protocolName) {
			this.protocolName = protocolName;
		}

		public String getResName() {
			return resName;
		}

		public void setResName(String resName) {
			this.resName = resName;
		}

		public String getLiftingTypeName() {
			return liftingTypeName;
		}

		public void setLiftingTypeName(String liftingTypeName) {
			this.liftingTypeName = liftingTypeName;
		}

		public String getAcquisitionUnit() {
			return acquisitionUnit;
		}

		public void setAcquisitionUnit(String acquisitionUnit) {
			this.acquisitionUnit = acquisitionUnit;
		}
	    
	}
}
