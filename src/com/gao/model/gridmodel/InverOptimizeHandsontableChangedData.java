package com.gao.model.gridmodel;

import java.util.List;

public class InverOptimizeHandsontableChangedData {
	
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

	    private String WellName;

	    private String OffsetAngleOfCrankPS;
	    
	    private String SurfaceSystemEfficiency;
	    
	    private String FS_LeftPercent;
	    
	    private String FS_RightPercent;
	    
	    private String WattAngle;
	    
	    private String FilterTime_Watt;
	    
	    private String FilterTime_I;
	    
	    private String FilterTime_RPM;
	    
	    private String FilterTime_FSDiagram;

		private String FilterTime_FSDiagram_L;

	    private String FilterTime_FSDiagram_R;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getWellName() {
			return WellName;
		}

		public void setWellName(String wellName) {
			WellName = wellName;
		}

		public String getOffsetAngleOfCrankPS() {
			return OffsetAngleOfCrankPS;
		}

		public void setOffsetAngleOfCrankPS(String offsetAngleOfCrankPS) {
			OffsetAngleOfCrankPS = offsetAngleOfCrankPS;
		}

		public String getSurfaceSystemEfficiency() {
			return SurfaceSystemEfficiency;
		}

		public void setSurfaceSystemEfficiency(String surfaceSystemEfficiency) {
			SurfaceSystemEfficiency = surfaceSystemEfficiency;
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

		public String getFilterTime_Watt() {
			return FilterTime_Watt;
		}

		public void setFilterTime_Watt(String filterTime_Watt) {
			FilterTime_Watt = filterTime_Watt;
		}

		public String getFilterTime_I() {
			return FilterTime_I;
		}

		public void setFilterTime_I(String filterTime_I) {
			FilterTime_I = filterTime_I;
		}

		public String getFilterTime_RPM() {
			return FilterTime_RPM;
		}

		public void setFilterTime_RPM(String filterTime_RPM) {
			FilterTime_RPM = filterTime_RPM;
		}

		public String getFilterTime_FSDiagram() {
			return FilterTime_FSDiagram;
		}

		public void setFilterTime_FSDiagram(String filterTime_FSDiagram) {
			FilterTime_FSDiagram = filterTime_FSDiagram;
		}

		public String getFilterTime_FSDiagram_L() {
			return FilterTime_FSDiagram_L;
		}

		public void setFilterTime_FSDiagram_L(String filterTime_FSDiagram_L) {
			FilterTime_FSDiagram_L = filterTime_FSDiagram_L;
		}

		public String getFilterTime_FSDiagram_R() {
			return FilterTime_FSDiagram_R;
		}

		public void setFilterTime_FSDiagram_R(String filterTime_FSDiagram_R) {
			FilterTime_FSDiagram_R = filterTime_FSDiagram_R;
		}

		public String getWattAngle() {
			return WattAngle;
		}

		public void setWattAngle(String wattAngle) {
			WattAngle = wattAngle;
		}

	    
	}
}
