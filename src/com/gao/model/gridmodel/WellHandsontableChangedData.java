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

	    private String driverName="";
	    
	    private String protocol="";

	    private String acquisitionUnit="";

	    private String driverAddr="";

	    private String driverId="";

	    private String acqcycle_diagram="";

	    private String acqcycle_discrete="";
	    
	    private String savecycle_discrete="";
	    
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

		public String getDriverName() {
			return driverName;
		}

		public void setDriverName(String driverName) {
			this.driverName = driverName;
		}

		public String getAcquisitionUnit() {
			return acquisitionUnit;
		}

		public void setAcquisitionUnit(String acquisitionUnit) {
			this.acquisitionUnit = acquisitionUnit;
		}

		public String getDriverAddr() {
			return driverAddr;
		}

		public void setDriverAddr(String driverAddr) {
			this.driverAddr = driverAddr;
		}

		public String getDriverId() {
			return driverId;
		}

		public void setDriverId(String driverId) {
			this.driverId = driverId;
		}

		public String getAcqcycle_diagram() {
			return acqcycle_diagram;
		}

		public void setAcqcycle_diagram(String acqcycle_diagram) {
			this.acqcycle_diagram = acqcycle_diagram;
		}

		public String getAcqcycle_discrete() {
			return acqcycle_discrete;
		}

		public void setAcqcycle_discrete(String acqcycle_discrete) {
			this.acqcycle_discrete = acqcycle_discrete;
		}

		public String getSavecycle_discrete() {
			return savecycle_discrete;
		}

		public void setSavecycle_discrete(String savecycle_discrete) {
			this.savecycle_discrete = savecycle_discrete;
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
	    
	}
}
