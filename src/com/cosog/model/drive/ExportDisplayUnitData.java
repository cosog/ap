package com.cosog.model.drive;

import java.util.List;

public class ExportDisplayUnitData {

	private int Id;

    private String UnitCode;

    private String UnitName;

    private String Protocol;

    private String AcqUnit;

    private String Remark;

    private int CalculateType;

    private List<AlarmItem> ItemList;

    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }
    public void setUnitCode(String UnitCode){
        this.UnitCode = UnitCode;
    }
    public String getUnitCode(){
        return this.UnitCode;
    }
    public void setUnitName(String UnitName){
        this.UnitName = UnitName;
    }
    public String getUnitName(){
        return this.UnitName;
    }
    public void setProtocol(String Protocol){
        this.Protocol = Protocol;
    }
    public String getProtocol(){
        return this.Protocol;
    }
    public void setRemark(String Remark){
        this.Remark = Remark;
    }
    public String getRemark(){
        return this.Remark;
    }
    public void setCalculateType(int CalculateType){
        this.CalculateType = CalculateType;
    }
    public int getCalculateType(){
        return this.CalculateType;
    }
    public void setItemList(List<AlarmItem> ItemList){
        this.ItemList = ItemList;
    }
    public List<AlarmItem> getItemList(){
        return this.ItemList;
    }
	
	public static class CurveConf {
		private int sort;
		private String color;
		private int lineWidth;
		private String dashStyle;
		private boolean yAxisOpposite;
		public CurveConf() {
			super();
		}
		public CurveConf(int sort, String color, int lineWidth, String dashStyle, boolean yAxisOpposite) {
			super();
			this.sort = sort;
			this.color = color;
			this.lineWidth = lineWidth;
			this.dashStyle = dashStyle;
			this.yAxisOpposite = yAxisOpposite;
		}
		public int getSort() {
			return sort;
		}
		public void setSort(int sort) {
			this.sort = sort;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public int getLineWidth() {
			return lineWidth;
		}
		public void setLineWidth(int lineWidth) {
			this.lineWidth = lineWidth;
		}
		public String getDashStyle() {
			return dashStyle;
		}
		public void setDashStyle(String dashStyle) {
			this.dashStyle = dashStyle;
		}
		public boolean getYAxisOpposite() {
			return yAxisOpposite;
		}
		public void setYAxisOpposite(boolean yAxisOpposite) {
			this.yAxisOpposite = yAxisOpposite;
		}
	}
	
	public static class AlarmItem
	{
	    private int Id;

	    private String ItemId;

	    private String ItemName;

	    private String ItemCode;

	    private int RealtimeSort;

	    private int BitIndex;

	    private int ShowLevel;

	    private CurveConf RealtimeCurveConf;

	    private CurveConf HistoryCurveConf;

	    private int Type;

	    private String Matrix;

	    private int HistorySort;

	    public void setId(int Id){
	        this.Id = Id;
	    }
	    public int getId(){
	        return this.Id;
	    }
	    public void setItemId(String ItemId){
	        this.ItemId = ItemId;
	    }
	    public String getItemId(){
	        return this.ItemId;
	    }
	    public void setItemName(String ItemName){
	        this.ItemName = ItemName;
	    }
	    public String getItemName(){
	        return this.ItemName;
	    }
	    public void setItemCode(String ItemCode){
	        this.ItemCode = ItemCode;
	    }
	    public String getItemCode(){
	        return this.ItemCode;
	    }
	    public void setRealtimeSort(int RealtimeSort){
	        this.RealtimeSort = RealtimeSort;
	    }
	    public int getRealtimeSort(){
	        return this.RealtimeSort;
	    }
	    public void setBitIndex(int BitIndex){
	        this.BitIndex = BitIndex;
	    }
	    public int getBitIndex(){
	        return this.BitIndex;
	    }
	    public void setShowLevel(int ShowLevel){
	        this.ShowLevel = ShowLevel;
	    }
	    public int getShowLevel(){
	        return this.ShowLevel;
	    }
	    public void setRealtimeCurveConf(CurveConf RealtimeCurveConf){
	        this.RealtimeCurveConf = RealtimeCurveConf;
	    }
	    public CurveConf getRealtimeCurveConf(){
	        return this.RealtimeCurveConf;
	    }
	    public void setHistoryCurveConf(CurveConf HistoryCurveConf){
	        this.HistoryCurveConf = HistoryCurveConf;
	    }
	    public CurveConf getHistoryCurveConf(){
	        return this.HistoryCurveConf;
	    }
	    public void setType(int Type){
	        this.Type = Type;
	    }
	    public int getType(){
	        return this.Type;
	    }
	    public void setMatrix(String Matrix){
	        this.Matrix = Matrix;
	    }
	    public String getMatrix(){
	        return this.Matrix;
	    }
	    public void setHistorySort(int HistorySort){
	        this.HistorySort = HistorySort;
	    }
	    public int getHistorySort(){
	        return this.HistorySort;
	    }
	}

	public String getAcqUnit() {
		return AcqUnit;
	}
	public void setAcqUnit(String acqUnit) {
		AcqUnit = acqUnit;
	}
}
