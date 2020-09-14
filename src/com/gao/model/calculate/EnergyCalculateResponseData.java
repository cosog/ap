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
	    private float KWattH;

	    private float PKWattH;

	    private float NKWattH;

	    private float KVarH;

	    private float PKVarH;

	    private float NKVarH;

	    private float KVAH;

		public float getKWattH() {
			return KWattH;
		}

		public void setKWattH(float kWattH) {
			KWattH = kWattH;
		}

		public float getPKWattH() {
			return PKWattH;
		}

		public void setPKWattH(float pKWattH) {
			PKWattH = pKWattH;
		}

		public float getNKWattH() {
			return NKWattH;
		}

		public void setNKWattH(float nKWattH) {
			NKWattH = nKWattH;
		}

		public float getKVarH() {
			return KVarH;
		}

		public void setKVarH(float kVarH) {
			KVarH = kVarH;
		}

		public float getPKVarH() {
			return PKVarH;
		}

		public void setPKVarH(float pKVarH) {
			PKVarH = pKVarH;
		}

		public float getNKVarH() {
			return NKVarH;
		}

		public void setNKVarH(float nKVarH) {
			NKVarH = nKVarH;
		}

		public float getKVAH() {
			return KVAH;
		}

		public void setKVAH(float kVAH) {
			KVAH = kVAH;
		}
	}
	
	public static class Energy2
	{
	    private String AcqTime;

	    private Energy Total;

	    private Energy Today;

	    public void setAcqTime(String AcqTime){
	        this.AcqTime = AcqTime;
	    }
	    public String getAcqTime(){
	        return this.AcqTime;
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

	    private float KWattH;

	    private float PKWattH;

	    private float NKWattH;

	    private float KVarH;

	    private float PKVarH;

	    private float NKVarH;

	    private float KVAH;

		public String getDate() {
			return Date;
		}

		public void setDate(String date) {
			Date = date;
		}

		public float getKWattH() {
			return KWattH;
		}

		public void setKWattH(float kWattH) {
			KWattH = kWattH;
		}

		public float getPKWattH() {
			return PKWattH;
		}

		public void setPKWattH(float pKWattH) {
			PKWattH = pKWattH;
		}

		public float getNKWattH() {
			return NKWattH;
		}

		public void setNKWattH(float nKWattH) {
			NKWattH = nKWattH;
		}

		public float getKVarH() {
			return KVarH;
		}

		public void setKVarH(float kVarH) {
			KVarH = kVarH;
		}

		public float getPKVarH() {
			return PKVarH;
		}

		public void setPKVarH(float pKVarH) {
			PKVarH = pKVarH;
		}

		public float getNKVarH() {
			return NKVarH;
		}

		public void setNKVarH(float nKVarH) {
			NKVarH = nKVarH;
		}

		public float getKVAH() {
			return KVAH;
		}

		public void setKVAH(float kVAH) {
			KVAH = kVAH;
		}

	    
	}
}
