package com.cosog.model.balance;

import java.util.List;


public class CycleEvaluaion {
	private String WellName;

    private int ExtendedDays;

    private float DeltaRadius;

    private int DeltaBlock;

    private float CurrentDegreeOfBalance;
    
    private CalculationStatus CalculationStatus;

    private DataVerificationCounter DataVerificationCounter;

    private CurrentBalance CurrentBalance;

    private EvaluationBalance EvaluationBalance;
    
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
    
    public static class DataVerificationCounter
	{
	    private int ErrorCounter;

	    private String ErrorString;

	    private int WarningCounter;

	    private String WarningString;

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
	}
	
	public static class EveryBalance
	{
	    private String Name;

	    private float Position;

	    private float Weight;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setPosition(float Position){
	        this.Position = Position;
	    }
	    public float getPosition(){
	        return this.Position;
	    }
	    public void setWeight(float Weight){
	        this.Weight = Weight;
	    }
	    public float getWeight(){
	        return this.Weight;
	    }
	}
	
	public static class CurrentBalance
	{
		private int ValidCNT;
		
	    private List<EveryBalance> EveryBalance;

	    public void setEveryBalance(List<EveryBalance> EveryBalance){
	        this.EveryBalance = EveryBalance;
	    }
	    public List<EveryBalance> getEveryBalance(){
	        return this.EveryBalance;
	    }
		public int getValidCNT() {
			return ValidCNT;
		}
		public void setValidCNT(int validCNT) {
			ValidCNT = validCNT;
		}
	}
	
	public static class EvaluationBalance
	{
		private int ValidCNT;
		
	    private List<EveryBalance> EveryBalance;

	    public void setEveryBalance(List<EveryBalance> EveryBalance){
	        this.EveryBalance = EveryBalance;
	    }
	    public List<EveryBalance> getEveryBalance(){
	        return this.EveryBalance;
	    }
		public int getValidCNT() {
			return ValidCNT;
		}
		public void setValidCNT(int validCNT) {
			ValidCNT = validCNT;
		}
	}

	public String getWellName() {
		return WellName;
	}

	public void setWellName(String wellName) {
		WellName = wellName;
	}

	public int getExtendedDays() {
		return ExtendedDays;
	}

	public void setExtendedDays(int extendedDays) {
		ExtendedDays = extendedDays;
	}

	public float getDeltaRadius() {
		return DeltaRadius;
	}

	public void setDeltaRadius(float deltaRadius) {
		DeltaRadius = deltaRadius;
	}

	public int getDeltaBlock() {
		return DeltaBlock;
	}

	public void setDeltaBlock(int deltaBlock) {
		DeltaBlock = deltaBlock;
	}

	public float getCurrentDegreeOfBalance() {
		return CurrentDegreeOfBalance;
	}

	public void setCurrentDegreeOfBalance(float currentDegreeOfBalance) {
		CurrentDegreeOfBalance = currentDegreeOfBalance;
	}

	public CurrentBalance getCurrentBalance() {
		return CurrentBalance;
	}

	public void setCurrentBalance(CurrentBalance currentBalance) {
		CurrentBalance = currentBalance;
	}

	public EvaluationBalance getEvaluationBalance() {
		return EvaluationBalance;
	}

	public void setEvaluationBalance(EvaluationBalance evaluationBalance) {
		EvaluationBalance = evaluationBalance;
	}

	public CalculationStatus getCalculationStatus() {
		return CalculationStatus;
	}

	public void setCalculationStatus(CalculationStatus calculationStatus) {
		CalculationStatus = calculationStatus;
	}

	public DataVerificationCounter getDataVerificationCounter() {
		return DataVerificationCounter;
	}

	public void setDataVerificationCounter(DataVerificationCounter dataVerificationCounter) {
		DataVerificationCounter = dataVerificationCounter;
	}
}
