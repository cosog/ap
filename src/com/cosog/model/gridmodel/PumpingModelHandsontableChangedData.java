package com.cosog.model.gridmodel;

import java.util.List;

public class PumpingModelHandsontableChangedData {

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

	    private String manufacturer="";

	    private String model="";

	    private String stroke="";
	    
	    private String crankRotationDirection="";
	    
	    private String offsetAngleOfCrank="";
	    
	    private String crankGravityRadius;
	    
	    private String singleCrankWeight;
	    
	    private String singleCrankPinWeight;
	    
	    private String structuralUnbalance;
	    
	    private String balanceWeight="";
	    
	    private int saveSign;
	    
	    private String saveStr;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
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

		public String getSingleCrankPinWeight() {
			return singleCrankPinWeight;
		}

		public void setSingleCrankPinWeight(String singleCrankPinWeight) {
			this.singleCrankPinWeight = singleCrankPinWeight;
		}

		public String getStructuralUnbalance() {
			return structuralUnbalance;
		}

		public void setStructuralUnbalance(String structuralUnbalance) {
			this.structuralUnbalance = structuralUnbalance;
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

		public String getBalanceWeight() {
			return balanceWeight;
		}

		public void setBalanceWeight(String balanceWeight) {
			this.balanceWeight = balanceWeight;
		}
	}
}
