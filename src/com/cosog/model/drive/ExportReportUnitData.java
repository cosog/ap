package com.cosog.model.drive;

import java.util.List;

public class ExportReportUnitData {
	
	private int Id;

    private String UnitCode;

    private String UnitName;

    private int CalculateType;

    private String SingleWellDailyReportTemplate;

    private String SingleWellRangeReportTemplate;

    private String ProductionReportTemplate;

    private String Sort;

    private List<DisplayItem> ItemList;

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
    public void setCalculateType(int CalculateType){
        this.CalculateType = CalculateType;
    }
    public int getCalculateType(){
        return this.CalculateType;
    }
    public void setSingleWellDailyReportTemplate(String SingleWellDailyReportTemplate){
        this.SingleWellDailyReportTemplate = SingleWellDailyReportTemplate;
    }
    public String getSingleWellDailyReportTemplate(){
        return this.SingleWellDailyReportTemplate;
    }
    public void setSingleWellRangeReportTemplate(String SingleWellRangeReportTemplate){
        this.SingleWellRangeReportTemplate = SingleWellRangeReportTemplate;
    }
    public String getSingleWellRangeReportTemplate(){
        return this.SingleWellRangeReportTemplate;
    }
    public void setProductionReportTemplate(String ProductionReportTemplate){
        this.ProductionReportTemplate = ProductionReportTemplate;
    }
    public String getProductionReportTemplate(){
        return this.ProductionReportTemplate;
    }
    public void setSort(String Sort){
        this.Sort = Sort;
    }
    public String getSort(){
        return this.Sort;
    }
    public void setItemList(List<DisplayItem> ItemList){
        this.ItemList = ItemList;
    }
    public List<DisplayItem> getItemList(){
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
	
	public class DisplayItem
	{
	    private int Id;

	    private String ItemId;

	    private String ItemName;

	    private String ItemCode;

	    private String ShowLevel;

	    private String SumSign;

	    private String AverageSign;

	    private CurveConf ReportCurveConf;

	    private String CurveStatType;

	    private String DataType;

	    private String ReportType;

	    private String Prec;

	    private String TotalType;

	    private String DataSource;

	    private String Sort;

	    private String Matrix;

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
	    public void setShowLevel(String ShowLevel){
	        this.ShowLevel = ShowLevel;
	    }
	    public String getShowLevel(){
	        return this.ShowLevel;
	    }
	    public void setSumSign(String SumSign){
	        this.SumSign = SumSign;
	    }
	    public String getSumSign(){
	        return this.SumSign;
	    }
	    public void setAverageSign(String AverageSign){
	        this.AverageSign = AverageSign;
	    }
	    public String getAverageSign(){
	        return this.AverageSign;
	    }
	    public void setReportCurveConf(CurveConf ReportCurveConf){
	        this.ReportCurveConf = ReportCurveConf;
	    }
	    public CurveConf getReportCurveConf(){
	        return this.ReportCurveConf;
	    }
	    public void setCurveStatType(String CurveStatType){
	        this.CurveStatType = CurveStatType;
	    }
	    public String getCurveStatType(){
	        return this.CurveStatType;
	    }
	    public void setDataType(String DataType){
	        this.DataType = DataType;
	    }
	    public String getDataType(){
	        return this.DataType;
	    }
	    public void setReportType(String ReportType){
	        this.ReportType = ReportType;
	    }
	    public String getReportType(){
	        return this.ReportType;
	    }
	    public void setPrec(String Prec){
	        this.Prec = Prec;
	    }
	    public String getPrec(){
	        return this.Prec;
	    }
	    public void setTotalType(String TotalType){
	        this.TotalType = TotalType;
	    }
	    public String getTotalType(){
	        return this.TotalType;
	    }
	    public void setDataSource(String DataSource){
	        this.DataSource = DataSource;
	    }
	    public String getDataSource(){
	        return this.DataSource;
	    }
	    public void setSort(String Sort){
	        this.Sort = Sort;
	    }
	    public String getSort(){
	        return this.Sort;
	    }
	    public void setMatrix(String Matrix){
	        this.Matrix = Matrix;
	    }
	    public String getMatrix(){
	        return this.Matrix;
	    }
	}
}
