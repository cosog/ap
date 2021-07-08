package com.cosog.model.gridmodel;

import java.util.List;

public class ElecInverCalculateManagerHandsontableChangedData {

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

	    private String wellName;

	    private String acqTime;
	    
	    private String resultStatus;
	    
	    private String manufacturer;
	    private String model;
	    private String stroke;
	    private String crankRotationDirection;
	    private String offsetAngleOfCrank;
	    private String crankGravityRadius;
	    private String singleCrankWeight;
	    private String structuralUnbalance;
	    private String balancePosition;
	    private String balanceWeight;

	    private String offsetAngleOfCrankPS;
	    private String surfaceSystemEfficiency;
	    private String FS_LeftPercent;
	    private String FS_RightPercent;
	    private String wattAngle;
	    private String filterTime_Watt;
	    private String filterTime_I;
	    private String filterTime_RPM;
	    private String filterTime_FSDiagram;
	    private String filterTime_FSDiagram_L;
	    private String filterTime_FSDiagram_R;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getWellName() {
			return wellName;
		}
		public void setWellName(String wellName) {
			this.wellName = wellName;
		}
		public String getAcqTime() {
			return acqTime;
		}
		public void setAcqTime(String acqTime) {
			this.acqTime = acqTime;
		}
		public String getResultStatus() {
			return resultStatus;
		}
		public void setResultStatus(String resultStatus) {
			this.resultStatus = resultStatus;
		}
		public String getManufacturer() {
			return manufacturer;
		}
		public void setManufacturer(String manufacturer) {
			this.manufacturer = manufacturer;
		}
		public String getModel() {
			return model;
		}
		public void setModel(String model) {
			this.model = model;
		}
		public String getStroke() {
			return stroke;
		}
		public void setStroke(String stroke) {
			this.stroke = stroke;
		}
		public String getCrankRotationDirection() {
			return crankRotationDirection;
		}
		public void setCrankRotationDirection(String crankRotationDirection) {
			this.crankRotationDirection = crankRotationDirection;
		}
		public String getOffsetAngleOfCrank() {
			return offsetAngleOfCrank;
		}
		public void setOffsetAngleOfCrank(String offsetAngleOfCrank) {
			this.offsetAngleOfCrank = offsetAngleOfCrank;
		}
		public String getCrankGravityRadius() {
			return crankGravityRadius;
		}
		public void setCrankGravityRadius(String crankGravityRadius) {
			this.crankGravityRadius = crankGravityRadius;
		}
		public String getSingleCrankWeight() {
			return singleCrankWeight;
		}
		public void setSingleCrankWeight(String singleCrankWeight) {
			this.singleCrankWeight = singleCrankWeight;
		}
		public String getStructuralUnbalance() {
			return structuralUnbalance;
		}
		public void setStructuralUnbalance(String structuralUnbalance) {
			this.structuralUnbalance = structuralUnbalance;
		}
		public String getBalancePosition() {
			return balancePosition;
		}
		public void setBalancePosition(String balancePosition) {
			this.balancePosition = balancePosition;
		}
		public String getBalanceWeight() {
			return balanceWeight;
		}
		public void setBalanceWeight(String balanceWeight) {
			this.balanceWeight = balanceWeight;
		}
		public String getOffsetAngleOfCrankPS() {
			return offsetAngleOfCrankPS;
		}
		public void setOffsetAngleOfCrankPS(String offsetAngleOfCrankPS) {
			this.offsetAngleOfCrankPS = offsetAngleOfCrankPS;
		}
		public String getSurfaceSystemEfficiency() {
			return surfaceSystemEfficiency;
		}
		public void setSurfaceSystemEfficiency(String surfaceSystemEfficiency) {
			this.surfaceSystemEfficiency = surfaceSystemEfficiency;
		}
		public String getFS_LeftPercent() {
			return FS_LeftPercent;
		}
		public void setFS_LeftPercent(String fS_LeftPercent) {
			FS_LeftPercent = fS_LeftPercent;
		}
		public String getFS_RightPercent() {
			return FS_RightPercent;
		}
		public void setFS_RightPercent(String fS_RightPercent) {
			FS_RightPercent = fS_RightPercent;
		}
		public String getWattAngle() {
			return wattAngle;
		}
		public void setWattAngle(String wattAngle) {
			this.wattAngle = wattAngle;
		}
		public String getFilterTime_Watt() {
			return filterTime_Watt;
		}
		public void setFilterTime_Watt(String filterTime_Watt) {
			this.filterTime_Watt = filterTime_Watt;
		}
		public String getFilterTime_I() {
			return filterTime_I;
		}
		public void setFilterTime_I(String filterTime_I) {
			this.filterTime_I = filterTime_I;
		}
		public String getFilterTime_RPM() {
			return filterTime_RPM;
		}
		public void setFilterTime_RPM(String filterTime_RPM) {
			this.filterTime_RPM = filterTime_RPM;
		}
		public String getFilterTime_FSDiagram() {
			return filterTime_FSDiagram;
		}
		public void setFilterTime_FSDiagram(String filterTime_FSDiagram) {
			this.filterTime_FSDiagram = filterTime_FSDiagram;
		}
		public String getFilterTime_FSDiagram_L() {
			return filterTime_FSDiagram_L;
		}
		public void setFilterTime_FSDiagram_L(String filterTime_FSDiagram_L) {
			this.filterTime_FSDiagram_L = filterTime_FSDiagram_L;
		}
		public String getFilterTime_FSDiagram_R() {
			return filterTime_FSDiagram_R;
		}
		public void setFilterTime_FSDiagram_R(String filterTime_FSDiagram_R) {
			this.filterTime_FSDiagram_R = filterTime_FSDiagram_R;
		}
	}
}
