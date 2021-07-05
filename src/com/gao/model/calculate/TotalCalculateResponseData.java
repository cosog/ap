package com.gao.model.calculate;

import java.util.List;

public class TotalCalculateResponseData {
	
	 	private CalculationStatus CalculationStatus;

	    private DataVerificationCounter DataVerificationCounter;

	    private WellRing WellRing;

	    public void setCalculationStatus(CalculationStatus CalculationStatus){
	        this.CalculationStatus = CalculationStatus;
	    }
	    public CalculationStatus getCalculationStatus(){
	        return this.CalculationStatus;
	    }
	    public void setDataVerificationCounter(DataVerificationCounter DataVerificationCounter){
	        this.DataVerificationCounter = DataVerificationCounter;
	    }
	    public DataVerificationCounter getDataVerificationCounter(){
	        return this.DataVerificationCounter;
	    }
	    public void setWellRing(WellRing WellRing){
	        this.WellRing = WellRing;
	    }
	    public WellRing getWellRing(){
	        return this.WellRing;
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
	
	public static class DataVerificationCounter
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
	
	public static class Measure
	{
	    private float LiquidVolumetricProduction;

	    private float OilVolumetricProduction;

	    private float WaterVolumetricProduction;

	    private float LiquidWeightProduction;

	    private float OilWeightProduction;

	    private float WaterWeightProduction;

	    public void setLiquidVolumetricProduction(float LiquidVolumetricProduction){
	        this.LiquidVolumetricProduction = LiquidVolumetricProduction;
	    }
	    public float getLiquidVolumetricProduction(){
	        return this.LiquidVolumetricProduction;
	    }
	    public void setOilVolumetricProduction(float OilVolumetricProduction){
	        this.OilVolumetricProduction = OilVolumetricProduction;
	    }
	    public float getOilVolumetricProduction(){
	        return this.OilVolumetricProduction;
	    }
	    public void setWaterVolumetricProduction(float WaterVolumetricProduction){
	        this.WaterVolumetricProduction = WaterVolumetricProduction;
	    }
	    public float getWaterVolumetricProduction(){
	        return this.WaterVolumetricProduction;
	    }
	    public void setLiquidWeightProduction(float LiquidWeightProduction){
	        this.LiquidWeightProduction = LiquidWeightProduction;
	    }
	    public float getLiquidWeightProduction(){
	        return this.LiquidWeightProduction;
	    }
	    public void setOilWeightProduction(float OilWeightProduction){
	        this.OilWeightProduction = OilWeightProduction;
	    }
	    public float getOilWeightProduction(){
	        return this.OilWeightProduction;
	    }
	    public void setWaterWeightProduction(float WaterWeightProduction){
	        this.WaterWeightProduction = WaterWeightProduction;
	    }
	    public float getWaterWeightProduction(){
	        return this.WaterWeightProduction;
	    }
	}
	
	public static class Calculation
	{
	    private float LiquidVolumetricProduction;

	    private float OilVolumetricProduction;

	    private float WaterVolumetricProduction;

	    private float VolumetricWaterCut;

	    private float LiquidWeightProduction;

	    private float OilWeightProduction;

	    private float WaterWeightProduction;

	    private float WeightWaterCut;

	    public void setLiquidVolumetricProduction(float LiquidVolumetricProduction){
	        this.LiquidVolumetricProduction = LiquidVolumetricProduction;
	    }
	    public float getLiquidVolumetricProduction(){
	        return this.LiquidVolumetricProduction;
	    }
	    public void setOilVolumetricProduction(float OilVolumetricProduction){
	        this.OilVolumetricProduction = OilVolumetricProduction;
	    }
	    public float getOilVolumetricProduction(){
	        return this.OilVolumetricProduction;
	    }
	    public void setWaterVolumetricProduction(float WaterVolumetricProduction){
	        this.WaterVolumetricProduction = WaterVolumetricProduction;
	    }
	    public float getWaterVolumetricProduction(){
	        return this.WaterVolumetricProduction;
	    }
	    public void setVolumetricWaterCut(float VolumetricWaterCut){
	        this.VolumetricWaterCut = VolumetricWaterCut;
	    }
	    public float getVolumetricWaterCut(){
	        return this.VolumetricWaterCut;
	    }
	    public void setLiquidWeightProduction(float LiquidWeightProduction){
	        this.LiquidWeightProduction = LiquidWeightProduction;
	    }
	    public float getLiquidWeightProduction(){
	        return this.LiquidWeightProduction;
	    }
	    public void setOilWeightProduction(float OilWeightProduction){
	        this.OilWeightProduction = OilWeightProduction;
	    }
	    public float getOilWeightProduction(){
	        return this.OilWeightProduction;
	    }
	    public void setWaterWeightProduction(float WaterWeightProduction){
	        this.WaterWeightProduction = WaterWeightProduction;
	    }
	    public float getWaterWeightProduction(){
	        return this.WaterWeightProduction;
	    }
	    public void setWeightWaterCut(float WeightWaterCut){
	        this.WeightWaterCut = WeightWaterCut;
	    }
	    public float getWeightWaterCut(){
	        return this.WeightWaterCut;
	    }
	}
	
	
	public static class Split
	{
	    private float ProductionSplitCoefficient;

	    private float LiquidVolumetricProduction;

	    private float OilVolumetricProduction;

	    private float WaterVolumetricProduction;

	    private float LiquidWeightProduction;

	    private float OilWeightProduction;

	    private float WaterWeightProduction;

	    public void setProductionSplitCoefficient(float ProductionSplitCoefficient){
	        this.ProductionSplitCoefficient = ProductionSplitCoefficient;
	    }
	    public float getProductionSplitCoefficient(){
	        return this.ProductionSplitCoefficient;
	    }
	    public void setLiquidVolumetricProduction(float LiquidVolumetricProduction){
	        this.LiquidVolumetricProduction = LiquidVolumetricProduction;
	    }
	    public float getLiquidVolumetricProduction(){
	        return this.LiquidVolumetricProduction;
	    }
	    public void setOilVolumetricProduction(float OilVolumetricProduction){
	        this.OilVolumetricProduction = OilVolumetricProduction;
	    }
	    public float getOilVolumetricProduction(){
	        return this.OilVolumetricProduction;
	    }
	    public void setWaterVolumetricProduction(float WaterVolumetricProduction){
	        this.WaterVolumetricProduction = WaterVolumetricProduction;
	    }
	    public float getWaterVolumetricProduction(){
	        return this.WaterVolumetricProduction;
	    }
	    public void setLiquidWeightProduction(float LiquidWeightProduction){
	        this.LiquidWeightProduction = LiquidWeightProduction;
	    }
	    public float getLiquidWeightProduction(){
	        return this.LiquidWeightProduction;
	    }
	    public void setOilWeightProduction(float OilWeightProduction){
	        this.OilWeightProduction = OilWeightProduction;
	    }
	    public float getOilWeightProduction(){
	        return this.OilWeightProduction;
	    }
	    public void setWaterWeightProduction(float WaterWeightProduction){
	        this.WaterWeightProduction = WaterWeightProduction;
	    }
	    public float getWaterWeightProduction(){
	        return this.WaterWeightProduction;
	    }
	}
	
	public static class CalculationWell
	{
	    private float LiquidVolumetricProduction;

	    private float OilVolumetricProduction;

	    private float WaterVolumetricProduction;

	    private float LiquidWeightProduction;

	    private float OilWeightProduction;

	    private float WaterWeightProduction;

	    private float SurfaceSystemEfficiency;

	    private float WellDownSystemEfficiency;
 
	    private float SystemEfficiency;
	    
	    private float EnergyPer100mLift;

	    public void setLiquidVolumetricProduction(float LiquidVolumetricProduction){
	        this.LiquidVolumetricProduction = LiquidVolumetricProduction;
	    }
	    public float getLiquidVolumetricProduction(){
	        return this.LiquidVolumetricProduction;
	    }
	    public void setOilVolumetricProduction(float OilVolumetricProduction){
	        this.OilVolumetricProduction = OilVolumetricProduction;
	    }
	    public float getOilVolumetricProduction(){
	        return this.OilVolumetricProduction;
	    }
	    public void setWaterVolumetricProduction(float WaterVolumetricProduction){
	        this.WaterVolumetricProduction = WaterVolumetricProduction;
	    }
	    public float getWaterVolumetricProduction(){
	        return this.WaterVolumetricProduction;
	    }
	    public void setLiquidWeightProduction(float LiquidWeightProduction){
	        this.LiquidWeightProduction = LiquidWeightProduction;
	    }
	    public float getLiquidWeightProduction(){
	        return this.LiquidWeightProduction;
	    }
	    public void setOilWeightProduction(float OilWeightProduction){
	        this.OilWeightProduction = OilWeightProduction;
	    }
	    public float getOilWeightProduction(){
	        return this.OilWeightProduction;
	    }
	    public void setWaterWeightProduction(float WaterWeightProduction){
	        this.WaterWeightProduction = WaterWeightProduction;
	    }
	    public float getWaterWeightProduction(){
	        return this.WaterWeightProduction;
	    }
	    public void setSurfaceSystemEfficiency(float SurfaceSystemEfficiency){
	        this.SurfaceSystemEfficiency = SurfaceSystemEfficiency;
	    }
	    public float getSurfaceSystemEfficiency(){
	        return this.SurfaceSystemEfficiency;
	    }
	    public void setWellDownSystemEfficiency(float WellDownSystemEfficiency){
	        this.WellDownSystemEfficiency = WellDownSystemEfficiency;
	    }
	    public float getWellDownSystemEfficiency(){
	        return this.WellDownSystemEfficiency;
	    }
	    public void setSystemEfficiency(float SystemEfficiency){
	        this.SystemEfficiency = SystemEfficiency;
	    }
	    public float getSystemEfficiency(){
	        return this.SystemEfficiency;
	    }
		public float getEnergyPer100mLift() {
			return EnergyPer100mLift;
		}
		public void setEnergyPer100mLift(float energyPer100mLift) {
			EnergyPer100mLift = energyPer100mLift;
		}
	}
	
	public static class EveryWell
	{
	    private String Name;

	    private float RunningTime;

	    private float RunningTimeEfficiency;

	    private int ResultCode;
	    
	    private float FullnessCoefficient;

	    private int ExtendedDays;
	    
	    private Flag Flag;

	    private Split Split;

	    private CalculationWell Calculation;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setRunningTime(float RunningTime){
	        this.RunningTime = RunningTime;
	    }
	    public float getRunningTime(){
	        return this.RunningTime;
	    }
	    public void setRunningTimeEfficiency(float RunningTimeEfficiency){
	        this.RunningTimeEfficiency = RunningTimeEfficiency;
	    }
	    public float getRunningTimeEfficiency(){
	        return this.RunningTimeEfficiency;
	    }
	    public void setResultCodeCode(int ResultCode){
	        this.ResultCode = ResultCode;
	    }
	    public int getResultCode(){
	        return this.ResultCode;
	    }
	    public void setExtendedDays(int ExtendedDays){
	        this.ExtendedDays = ExtendedDays;
	    }
	    public int getExtendedDays(){
	        return this.ExtendedDays;
	    }
	    public void setSplit(Split Split){
	        this.Split = Split;
	    }
	    public Split getSplit(){
	        return this.Split;
	    }
	    public void setCalculation(CalculationWell Calculation){
	        this.Calculation = Calculation;
	    }
	    public CalculationWell getCalculation(){
	        return this.Calculation;
	    }
		public float getFullnessCoefficient() {
			return FullnessCoefficient;
		}
		public void setFullnessCoefficient(float fullnessCoefficient) {
			FullnessCoefficient = fullnessCoefficient;
		}
		public Flag getFlag() {
			return Flag;
		}
		public void setFlag(Flag flag) {
			Flag = flag;
		}
	}
	
	public static class WellRing
	{
	    private String Name;
	    
	    private Flag Flag;

	    private Measure Measure;

	    private Calculation Calculation;

	    private List<EveryWell> EveryWell;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setMeasure(Measure Measure){
	        this.Measure = Measure;
	    }
	    public Measure getMeasure(){
	        return this.Measure;
	    }
	    public void setCalculation(Calculation Calculation){
	        this.Calculation = Calculation;
	    }
	    public Calculation getCalculation(){
	        return this.Calculation;
	    }
	    public void setEveryWell(List<EveryWell> EveryWell){
	        this.EveryWell = EveryWell;
	    }
	    public List<EveryWell> getEveryWell(){
	        return this.EveryWell;
	    }
		public Flag getFlag() {
			return Flag;
		}
		public void setFlag(Flag flag) {
			Flag = flag;
		}
	}
	
	public static class Flag
	{
	    private boolean SplitStatus;

	    private boolean Difficult;

	    private int DifficultCNT;

	    public void setSplitStatus(boolean SplitStatus){
	        this.SplitStatus = SplitStatus;
	    }
	    public boolean getSplitStatus(){
	        return this.SplitStatus;
	    }
	    public void setDifficult(boolean Difficult){
	        this.Difficult = Difficult;
	    }
	    public boolean getDifficult(){
	        return this.Difficult;
	    }
	    public void setDifficultCNT(int DifficultCNT){
	        this.DifficultCNT = DifficultCNT;
	    }
	    public int getDifficultCNT(){
	        return this.DifficultCNT;
	    }
	}
}