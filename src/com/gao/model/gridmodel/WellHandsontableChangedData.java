package com.gao.model.gridmodel;

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
	    
	    private String resName="";

	    private String wellName="";

	    private String liftingTypeName="抽油机";

	    private String protocolName="";
	    
	    private String protocol="";

	    private String acquisitionUnit="";

	    private String deviceAddr="";

	    private String deviceId="";
	    
	    private String runtimeEfficiencySource="";
	    
	    private String videoUrl="";
	    
	    private String sortNum="";

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

		public String getResName() {
			return resName;
		}

		public void setResName(String resName) {
			this.resName = resName;
		}

		public String getWellName() {
			return wellName;
		}

		public void setWellName(String wellName) {
			this.wellName = wellName;
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

		public String getRuntimeEfficiencySource() {
			return runtimeEfficiencySource;
		}

		public void setRuntimeEfficiencySource(String runtimeEfficiencySource) {
			this.runtimeEfficiencySource = runtimeEfficiencySource;
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

		public String getProtocol() {
			return protocol;
		}

		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}

		public String getProtocolName() {
			return protocolName;
		}

		public void setProtocolName(String protocolName) {
			this.protocolName = protocolName;
		}

		public String getDeviceAddr() {
			return deviceAddr;
		}

		public void setDeviceAddr(String deviceAddr) {
			this.deviceAddr = deviceAddr;
		}

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
	    
	}
}
