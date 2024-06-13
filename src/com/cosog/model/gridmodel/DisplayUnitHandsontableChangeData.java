package com.cosog.model.gridmodel;

import java.util.List;

public class DisplayUnitHandsontableChangeData {

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

	    private String unitName;

	    private String unitCode;
	    
	    private String acqUnitId;
	    
	    private String acqUnitName;
	    
	    private String calculateType;

	    private String remark;

	    public void setId(String id){
	        this.id = id;
	    }
	    public String getId(){
	        return this.id;
	    }
	    public void setUnitName(String unitName){
	        this.unitName = unitName;
	    }
	    public String getUnitName(){
	        return this.unitName;
	    }
	    public void setUnitCode(String unitCode){
	        this.unitCode = unitCode;
	    }
	    public String getUnitCode(){
	        return this.unitCode;
	    }
	    public void setRemark(String remark){
	        this.remark = remark;
	    }
	    public String getRemark(){
	        return this.remark;
	    }
		public String getAcqUnitId() {
			return acqUnitId;
		}
		public void setAcqUnitId(String acqUnitId) {
			this.acqUnitId = acqUnitId;
		}
		public String getAcqUnitName() {
			return acqUnitName;
		}
		public void setAcqUnitName(String acqUnitName) {
			this.acqUnitName = acqUnitName;
		}
		public String getCalculateType() {
			return calculateType;
		}
		public void setCalculateType(String calculateType) {
			this.calculateType = calculateType;
		}
	}
}
