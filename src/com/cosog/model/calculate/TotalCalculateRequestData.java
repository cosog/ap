package com.cosog.model.calculate;

import java.util.List;

public class TotalCalculateRequestData {
	private String AKString;

    private WellRing WellRing;

    public void setAKString(String AKString){
        this.AKString = AKString;
    }
    public String getAKString(){
        return this.AKString;
    }
    public void setWellRing(WellRing WellRing){
        this.WellRing = WellRing;
    }
    public WellRing getWellRing(){
        return this.WellRing;
    }

	public static class Measure
	{
	    private float LiquidVolumetricProduction;

	    private float LiquidWeightProduction;

	    public void setLiquidVolumetricProduction(float LiquidVolumetricProduction){
	        this.LiquidVolumetricProduction = LiquidVolumetricProduction;
	    }
	    public float getLiquidVolumetricProduction(){
	        return this.LiquidVolumetricProduction;
	    }
	    public void setLiquidWeightProduction(float LiquidWeightProduction){
	        this.LiquidWeightProduction = LiquidWeightProduction;
	    }
	    public float getLiquidWeightProduction(){
	        return this.LiquidWeightProduction;
	    }
	}

	
	public static class RunningRange
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

	public static class EveryTime
	{
	    private String AcqTime;

	    private int ResultCode;

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

	    public void setAcqTime(String AcqTime){
	        this.AcqTime = AcqTime;
	    }
	    public String getAcqTime(){
	        return this.AcqTime;
	    }
	    public void setResultCode(int ResultCode){
	        this.ResultCode = ResultCode;
	    }
	    public int getResultCode(){
	        return this.ResultCode;
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

	    private List<RunningRange> RunningRange;

	    private List<EveryTime> EveryTime;

	    public void setName(String Name){
	        this.Name = Name;
	    }
	    public String getName(){
	        return this.Name;
	    }
	    public void setRunningRange(List<RunningRange> RunningRange){
	        this.RunningRange = RunningRange;
	    }
	    public List<RunningRange> getRunningRange(){
	        return this.RunningRange;
	    }
	    public void setEveryTime(List<EveryTime> EveryTime){
	        this.EveryTime = EveryTime;
	    }
	    public List<EveryTime> getEveryTime(){
	        return this.EveryTime;
	    }
	}

	public static class WellRing
	{
	    private String Name;

	    private Measure Measure;

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
	    public void setEveryWell(List<EveryWell> EveryWell){
	        this.EveryWell = EveryWell;
	    }
	    public List<EveryWell> getEveryWell(){
	        return this.EveryWell;
	    }
	}

}
