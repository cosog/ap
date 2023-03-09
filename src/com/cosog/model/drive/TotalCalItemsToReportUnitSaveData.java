package com.cosog.model.drive;

import java.util.List;

public class TotalCalItemsToReportUnitSaveData {

	private List<Item> itemList;
	
	public static class Item
	{
	    private String matrix;

	    private String itemName;

	    private String itemShowLevel;

	    private String itemSort;

	    private String sumSign;

	    private String averageSign;

	    private String reportCurve;

	    private String reportCurveColor;

	    private String curveStatType;

	    private String itemCode;

	    private String dataType;

	    public void setMatrix(String matrix){
	        this.matrix = matrix;
	    }
	    public String getMatrix(){
	        return this.matrix;
	    }
	    public void setItemName(String itemName){
	        this.itemName = itemName;
	    }
	    public String getItemName(){
	        return this.itemName;
	    }
	    public void setItemShowLevel(String itemShowLevel){
	        this.itemShowLevel = itemShowLevel;
	    }
	    public String getItemShowLevel(){
	        return this.itemShowLevel;
	    }
	    public void setItemSort(String itemSort){
	        this.itemSort = itemSort;
	    }
	    public String getItemSort(){
	        return this.itemSort;
	    }
	    public void setSumSign(String sumSign){
	        this.sumSign = sumSign;
	    }
	    public String getSumSign(){
	        return this.sumSign;
	    }
	    public void setAverageSign(String averageSign){
	        this.averageSign = averageSign;
	    }
	    public String getAverageSign(){
	        return this.averageSign;
	    }
	    public void setReportCurve(String reportCurve){
	        this.reportCurve = reportCurve;
	    }
	    public String getReportCurve(){
	        return this.reportCurve;
	    }
	    public void setReportCurveColor(String reportCurveColor){
	        this.reportCurveColor = reportCurveColor;
	    }
	    public String getReportCurveColor(){
	        return this.reportCurveColor;
	    }
	    public void setCurveStatType(String curveStatType){
	        this.curveStatType = curveStatType;
	    }
	    public String getCurveStatType(){
	        return this.curveStatType;
	    }
	    public void setItemCode(String itemCode){
	        this.itemCode = itemCode;
	    }
	    public String getItemCode(){
	        return this.itemCode;
	    }
	    public void setDataType(String dataType){
	        this.dataType = dataType;
	    }
	    public String getDataType(){
	        return this.dataType;
	    }
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
}
