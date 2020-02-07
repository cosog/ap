package com.gao.model.calculate;

import java.util.List;

public class InversioneFSdiagramResponseData {
	
	private String WellName;

    private int ResultStatus;

    private Verification Verification;

    private String AcquisitionTime;

    private float Stroke;

    private float SPM;
    
    private float MaxF;
    
    private float MinF;

    private int CNT;
    
    private float UpstrokeIMax;
    
    private float DownstrokeIMax;
    
    private float UpstrokeWattMax;
    
    private float DownstrokeWattMax;
    
    private float IDegreeBalance;
    
    private float WattDegreeBalance;
    
    private float MotorInputAvgWatt;

    private List<Float> F;

    private List<Float> S;
    
    private List<Float> Watt;
    
    private List<Float> I;
    
    private List<Float> RPM;
    
    private List<Float> S360;
    
    private List<Float> A360;
    
    private List<Float> F360;

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
    public void setAcquisitionTime(String AcquisitionTime){
        this.AcquisitionTime = AcquisitionTime;
    }
    public String getAcquisitionTime(){
        return this.AcquisitionTime;
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

	public List<Float> getWatt() {
		return Watt;
	}
	public void setWatt(List<Float> watt) {
		Watt = watt;
	}
	public List<Float> getI() {
		return I;
	}
	public void setI(List<Float> i) {
		I = i;
	}
	public List<Float> getRPM() {
		return RPM;
	}
	public void setRPM(List<Float> rPM) {
		RPM = rPM;
	}
	public float getUpstrokeIMax() {
		return UpstrokeIMax;
	}
	public void setUpstrokeIMax(float upstrokeIMax) {
		UpstrokeIMax = upstrokeIMax;
	}
	public float getDownstrokeIMax() {
		return DownstrokeIMax;
	}
	public void setDownstrokeIMax(float downstrokeIMax) {
		DownstrokeIMax = downstrokeIMax;
	}
	public float getUpstrokeWattMax() {
		return UpstrokeWattMax;
	}
	public void setUpstrokeWattMax(float upstrokeWattMax) {
		UpstrokeWattMax = upstrokeWattMax;
	}
	public float getDownstrokeWattMax() {
		return DownstrokeWattMax;
	}
	public void setDownstrokeWattMax(float downstrokeWattMax) {
		DownstrokeWattMax = downstrokeWattMax;
	}
	public float getIDegreeBalance() {
		return IDegreeBalance;
	}
	public void setIDegreeBalance(float iDegreeBalance) {
		IDegreeBalance = iDegreeBalance;
	}
	public float getWattDegreeBalance() {
		return WattDegreeBalance;
	}
	public void setWattDegreeBalance(float wattDegreeBalance) {
		WattDegreeBalance = wattDegreeBalance;
	}
	public float getMotorInputAvgWatt() {
		return MotorInputAvgWatt;
	}
	public void setMotorInputAvgWatt(float motorInputAvgWatt) {
		MotorInputAvgWatt = motorInputAvgWatt;
	}
	public float getMaxF() {
		return MaxF;
	}
	public void setMaxF(float maxF) {
		MaxF = maxF;
	}
	public float getMinF() {
		return MinF;
	}
	public void setMinF(float minF) {
		MinF = minF;
	}
	public List<Float> getS360() {
		return S360;
	}
	public void setS360(List<Float> s360) {
		S360 = s360;
	}
	public List<Float> getA360() {
		return A360;
	}
	public void setA360(List<Float> a360) {
		A360 = a360;
	}
	public List<Float> getF360() {
		return F360;
	}
	public void setF360(List<Float> f360) {
		F360 = f360;
	}
}
