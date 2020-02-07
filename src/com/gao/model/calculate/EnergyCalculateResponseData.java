package com.gao.model.calculate;

public class EnergyCalculateResponseData {

	 private String WellName;

	    private int ResultStatus;

	    private Verification Verification;

	    private Energy2 Current;

	    private DailyEnergy Daily;

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
	    public void setCurrent(Energy2 Current){
	        this.Current = Current;
	    }
	    public Energy2 getCurrent(){
	        return this.Current;
	    }
	    public void setDaily(DailyEnergy Daily){
	        this.Daily = Daily;
	    }
	    public DailyEnergy getDaily(){
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

	public static class Energy
	{
	    private float Watt;

	    private float PWatt;

	    private float NWatt;

	    private float Var;

	    private float PVar;

	    private float NVar;

	    private float VA;

	    public void setWatt(float Watt){
	        this.Watt = Watt;
	    }
	    public float getWatt(){
	        return this.Watt;
	    }
	    public void setPWatt(float PWatt){
	        this.PWatt = PWatt;
	    }
	    public float getPWatt(){
	        return this.PWatt;
	    }
	    public void setNWatt(float NWatt){
	        this.NWatt = NWatt;
	    }
	    public float getNWatt(){
	        return this.NWatt;
	    }
	    public void setVar(float Var){
	        this.Var = Var;
	    }
	    public float getVar(){
	        return this.Var;
	    }
	    public void setPVar(float PVar){
	        this.PVar = PVar;
	    }
	    public float getPVar(){
	        return this.PVar;
	    }
	    public void setNVar(float NVar){
	        this.NVar = NVar;
	    }
	    public float getNVar(){
	        return this.NVar;
	    }
	    public void setVA(float VA){
	        this.VA = VA;
	    }
	    public float getVA(){
	        return this.VA;
	    }
	}
	
	public static class Energy2
	{
	    private String AcquisitionTime;

	    private Energy Total;

	    private Energy Today;

	    public void setAcquisitionTime(String AcquisitionTime){
	        this.AcquisitionTime = AcquisitionTime;
	    }
	    public String getAcquisitionTime(){
	        return this.AcquisitionTime;
	    }
	    public void setTotal(Energy Total){
	        this.Total = Total;
	    }
	    public Energy getTotal(){
	        return this.Total;
	    }
	    public void setToday(Energy Today){
	        this.Today = Today;
	    }
	    public Energy getToday(){
	        return this.Today;
	    }
	}
	
	public class DailyEnergy
	{
	    private String Date;

	    private float Watt;

	    private float PWatt;

	    private float NWatt;

	    private float Var;

	    private float PVar;

	    private float NVar;

	    private float VA;

	    public void setDate(String Date){
	        this.Date = Date;
	    }
	    public String getDate(){
	        return this.Date;
	    }
	    public void setWatt(float Watt){
	        this.Watt = Watt;
	    }
	    public float getWatt(){
	        return this.Watt;
	    }
	    public void setPWatt(float PWatt){
	        this.PWatt = PWatt;
	    }
	    public float getPWatt(){
	        return this.PWatt;
	    }
	    public void setNWatt(float NWatt){
	        this.NWatt = NWatt;
	    }
	    public float getNWatt(){
	        return this.NWatt;
	    }
	    public void setVar(float Var){
	        this.Var = Var;
	    }
	    public float getVar(){
	        return this.Var;
	    }
	    public void setPVar(float PVar){
	        this.PVar = PVar;
	    }
	    public float getPVar(){
	        return this.PVar;
	    }
	    public void setNVar(float NVar){
	        this.NVar = NVar;
	    }
	    public float getNVar(){
	        return this.NVar;
	    }
	    public void setVA(float VA){
	        this.VA = VA;
	    }
	    public float getVA(){
	        return this.VA;
	    }
	}
}
