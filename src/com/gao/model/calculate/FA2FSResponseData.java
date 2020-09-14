package com.gao.model.calculate;

import java.util.List;
public  class FA2FSResponseData {
	
	private String WellName;

    private CalculationStatus CalculationStatus;

    private Verification Verification;

    private float NameplateStroke;

    private float ForearmLength;

    private int OptimizedInterval;

    private int OptimizedCount;

    private FSDiagram FSDiagram;

    public void setWellName(String WellName){
        this.WellName = WellName;
    }
    public String getWellName(){
        return this.WellName;
    }
    public void setCalculationStatus(CalculationStatus CalculationStatus){
        this.CalculationStatus = CalculationStatus;
    }
    public CalculationStatus getCalculationStatus(){
        return this.CalculationStatus;
    }
    public void setVerification(Verification Verification){
        this.Verification = Verification;
    }
    public Verification getVerification(){
        return this.Verification;
    }
    public void setNameplateStroke(float NameplateStroke){
        this.NameplateStroke = NameplateStroke;
    }
    public float getNameplateStroke(){
        return this.NameplateStroke;
    }
    public void setForearmLength(float ForearmLength){
        this.ForearmLength = ForearmLength;
    }
    public float getForearmLength(){
        return this.ForearmLength;
    }
    public void setOptimizedInterval(int OptimizedInterval){
        this.OptimizedInterval = OptimizedInterval;
    }
    public int getOptimizedInterval(){
        return this.OptimizedInterval;
    }
    public void setOptimizedCount(int OptimizedCount){
        this.OptimizedCount = OptimizedCount;
    }
    public int getOptimizedCount(){
        return this.OptimizedCount;
    }
    public void setFSDiagram(FSDiagram FSDiagram){
        this.FSDiagram = FSDiagram;
    }
    public FSDiagram getFSDiagram(){
        return this.FSDiagram;
    }
	
	public static class CalculationStatus
	{
	    private int ResultStatus;

	    private int ResultCode;

	    public void setResultStatus(int ResultStatus){
	        this.ResultStatus = ResultStatus;
	    }
	    public int getResultStatus(){
	        return this.ResultStatus;
	    }
	    public void setResultCode(int ResultCode){
	        this.ResultCode = ResultCode;
	    }
	    public int getResultCode(){
	        return this.ResultCode;
	    }
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
	
	public static class FSDiagram
	{
	    private String AcqTime;

	    private float Stroke;

	    private float SPM;

	    private int CNT;

	    private List<Float> F;

	    private List<Float> S;

	    public void setAcqTime(String AcqTime){
	        this.AcqTime = AcqTime;
	    }
	    public String getAcqTime(){
	        return this.AcqTime;
	    }
	    public void setStroke(float Stroke){
	        this.Stroke = Stroke;
	    }
	    public float getStroke(){
	        return this.Stroke;
	    }
	    public void setSPM(float SPM){
	        this.SPM = SPM;
	    }
	    public float getSPM(){
	        return this.SPM;
	    }
	    public void setCNT(int CNT){
	        this.CNT = CNT;
	    }
	    public int getCNT(){
	        return this.CNT;
	    }
	    public void setF(List<Float> F){
	        this.F = F;
	    }
	    public List<Float> getF(){
	        return this.F;
	    }
	    public void setS(List<Float> S){
	        this.S = S;
	    }
	    public List<Float> getS(){
	        return this.S;
	    }
	}

}
