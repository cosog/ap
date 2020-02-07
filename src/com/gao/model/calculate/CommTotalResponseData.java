package com.gao.model.calculate;

import java.util.List;

public  class CommTotalResponseData {
	
	private String WellName;

    private int ResultStatus;

    private Verification Verification;

    private Current Current;

    private Daily Daily;

    public void setWellName(String WellName){
        this.WellName = WellName;
    }
    public String getWellName(){
        return this.WellName;
    }
    public void setResultStatus(int ResultStatus){
        this.ResultStatus = ResultStatus;
    }
    public int getResultStatus(){
        return this.ResultStatus;
    }
    public void setVerification(Verification Verification){
        this.Verification = Verification;
    }
    public Verification getVerification(){
        return this.Verification;
    }
    public void setCurrent(Current Current){
        this.Current = Current;
    }
    public Current getCurrent(){
        return this.Current;
    }
    public void setDaily(Daily Daily){
        this.Daily = Daily;
    }
    public Daily getDaily(){
        return this.Daily;
    }
	
	public static class Verification
	{
	    private int ErrorCounter;

	    private String ErrorString;

	    private int WarningCounter;

	    private String WarningString;

	    private int SDKPlusCounter;

	    private String SDKPlusString;

	    public void setErrorCounter(int ErrorCounter){
	        this.ErrorCounter = ErrorCounter;
	    }
	    public int getErrorCounter(){
	        return this.ErrorCounter;
	    }
	    public void setErrorString(String ErrorString){
	        this.ErrorString = ErrorString;
	    }
	    public String getErrorString(){
	        return this.ErrorString;
	    }
	    public void setWarningCounter(int WarningCounter){
	        this.WarningCounter = WarningCounter;
	    }
	    public int getWarningCounter(){
	        return this.WarningCounter;
	    }
	    public void setWarningString(String WarningString){
	        this.WarningString = WarningString;
	    }
	    public String getWarningString(){
	        return this.WarningString;
	    }
	    public void setSDKPlusCounter(int SDKPlusCounter){
	        this.SDKPlusCounter = SDKPlusCounter;
	    }
	    public int getSDKPlusCounter(){
	        return this.SDKPlusCounter;
	    }
	    public void setSDKPlusString(String SDKPlusString){
	        this.SDKPlusString = SDKPlusString;
	    }
	    public String getSDKPlusString(){
	        return this.SDKPlusString;
	    }
	}
	
	public static class Range
	{
	    private String StartTime;

	    private String EndTime;

	    public void setStartTime(String StartTime){
	        this.StartTime = StartTime;
	    }
	    public String getStartTime(){
	        return this.StartTime;
	    }
	    public void setEndTime(String EndTime){
	        this.EndTime = EndTime;
	    }
	    public String getEndTime(){
	        return this.EndTime;
	    }
	}
	
	public static class CommEfficiency
	{
	    private List<Range> Range;

	    private float Time;

	    private float Efficiency;

	    private String RangeString;

	    public void setRange(List<Range> Range){
	        this.Range = Range;
	    }
	    public List<Range> getRange(){
	        return this.Range;
	    }
	    public void setTime(float Time){
	        this.Time = Time;
	    }
	    public float getTime(){
	        return this.Time;
	    }
	    public void setEfficiency(float Efficiency){
	        this.Efficiency = Efficiency;
	    }
	    public float getEfficiency(){
	        return this.Efficiency;
	    }
	    public void setRangeString(String RangeString){
	        this.RangeString = RangeString;
	    }
	    public String getRangeString(){
	        return this.RangeString;
	    }
	}
	
	public static class Current
	{
	    private String AcquisitionTime;

	    private boolean CommStatus;

	    private CommEfficiency CommEfficiency;

	    public void setAcquisitionTime(String AcquisitionTime){
	        this.AcquisitionTime = AcquisitionTime;
	    }
	    public String getAcquisitionTime(){
	        return this.AcquisitionTime;
	    }
	    public void setCommStatus(boolean CommStatus){
	        this.CommStatus = CommStatus;
	    }
	    public boolean getCommStatus(){
	        return this.CommStatus;
	    }
	    public void setCommEfficiency(CommEfficiency CommEfficiency){
	        this.CommEfficiency = CommEfficiency;
	    }
	    public CommEfficiency getCommEfficiency(){
	        return this.CommEfficiency;
	    }
	}
	
	public static class Daily
	{
	    private String Date;

	    private boolean CommStatus;

	    private CommEfficiency CommEfficiency;

	    public void setDate(String Date){
	        this.Date = Date;
	    }
	    public String getDate(){
	        return this.Date;
	    }
	    public void setCommStatus(boolean CommStatus){
	        this.CommStatus = CommStatus;
	    }
	    public boolean getCommStatus(){
	        return this.CommStatus;
	    }
	    public void setCommEfficiency(CommEfficiency CommEfficiency){
	        this.CommEfficiency = CommEfficiency;
	    }
	    public CommEfficiency getCommEfficiency(){
	        return this.CommEfficiency;
	    }
	}
}
