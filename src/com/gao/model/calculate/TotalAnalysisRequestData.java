package com.gao.model.calculate;

import java.util.List;

public class TotalAnalysisRequestData {
	
	private String AKString;

    private String WellName;

    private List<EveryTime> EveryTime;

    public void setAKString(String AKString){
        this.AKString = AKString;
    }
    public String getAKString(){
        return this.AKString;
    }
    public void setWellName(String WellName){
        this.WellName = WellName;
    }
    public String getWellName(){
        return this.WellName;
    }
    public void setEveryTime(List<EveryTime> EveryTime){
        this.EveryTime = EveryTime;
    }
    public List<EveryTime> getEveryTime(){
        return this.EveryTime;
    }
	
	public static class EveryTime
	{
		private String AcquisitionTime;
		
		private int CommStatus;

	    private float CommTime;

	    private float CommTimeEfficiency;

	    private String CommRange;
		
		private int RunStatus;

	    private float RunTime;

	    private float RunTimeEfficiency;

	    private String RunRange;

	    private int StopReason;

	    private int StartReason;

	    private float TubingPressure;

	    private float CasingPressure;

	    private float WellHeadFluidTemperature;

	    private float ProductionGasOilRatio;

	    private int FSResultCode;

	    private float Stroke;

	    private float SPM;

	    private float FullnessCoefficient;

	    private float LiquidVolumetricProduction;

	    private float OilVolumetricProduction;

	    private float WaterVolumetricProduction;

	    private float LiquidWeightProduction;

	    private float OilWeightProduction;

	    private float WaterWeightProduction;

	    private float VolumeWaterCut;

	    private float WeightWaterCut;

	    private float PumpEff;

	    private float PumpBoreDiameter;

	    private float PumpSettingDepth;

	    private float ProducingfluidLevel;

	    private float Submergence;

	    private int ETResultCode;

	    private float WattDegreeBalance;

	    private float IDegreeBalance;

	    private float DeltaRadius;

	    private float SurfaceSystemEfficiency;

	    private float WellDownSystemEfficiency;

	    private float SystemEfficiency;

	    private float PowerConsumptionPerTHM;

	    private float IA;

	    private float IB;

	    private float IC;

	    private float VA;

	    private float VB;

	    private float VC;

	    private float RunningFrequency;

	    private float RPM;

		public String getAcquisitionTime() {
			return AcquisitionTime;
		}

		public void setAcquisitionTime(String acquisitionTime) {
			AcquisitionTime = acquisitionTime;
		}

		public int getCommStatus() {
			return CommStatus;
		}

		public void setCommStatus(int commStatus) {
			CommStatus = commStatus;
		}

		public float getCommTime() {
			return CommTime;
		}

		public void setCommTime(float commTime) {
			CommTime = commTime;
		}

		public float getCommTimeEfficiency() {
			return CommTimeEfficiency;
		}

		public void setCommTimeEfficiency(float commTimeEfficiency) {
			CommTimeEfficiency = commTimeEfficiency;
		}

		public String getCommRange() {
			return CommRange;
		}

		public void setCommRange(String commRange) {
			CommRange = commRange;
		}

		public int getRunStatus() {
			return RunStatus;
		}

		public void setRunStatus(int runStatus) {
			RunStatus = runStatus;
		}

		public float getRunTime() {
			return RunTime;
		}

		public void setRunTime(float runTime) {
			RunTime = runTime;
		}

		public float getRunTimeEfficiency() {
			return RunTimeEfficiency;
		}

		public void setRunTimeEfficiency(float runTimeEfficiency) {
			RunTimeEfficiency = runTimeEfficiency;
		}

		public String getRunRange() {
			return RunRange;
		}

		public void setRunRange(String runRange) {
			RunRange = runRange;
		}

		public int getStopReason() {
			return StopReason;
		}

		public void setStopReason(int stopReason) {
			StopReason = stopReason;
		}

		public int getStartReason() {
			return StartReason;
		}

		public void setStartReason(int startReason) {
			StartReason = startReason;
		}

		public float getTubingPressure() {
			return TubingPressure;
		}

		public void setTubingPressure(float tubingPressure) {
			TubingPressure = tubingPressure;
		}

		public float getCasingPressure() {
			return CasingPressure;
		}

		public void setCasingPressure(float casingPressure) {
			CasingPressure = casingPressure;
		}

		public float getWellHeadFluidTemperature() {
			return WellHeadFluidTemperature;
		}

		public void setWellHeadFluidTemperature(float wellHeadFluidTemperature) {
			WellHeadFluidTemperature = wellHeadFluidTemperature;
		}

		public float getProductionGasOilRatio() {
			return ProductionGasOilRatio;
		}

		public void setProductionGasOilRatio(float productionGasOilRatio) {
			ProductionGasOilRatio = productionGasOilRatio;
		}

		public int getFSResultCode() {
			return FSResultCode;
		}

		public void setFSResultCode(int fSResultCode) {
			FSResultCode = fSResultCode;
		}

		public float getStroke() {
			return Stroke;
		}

		public void setStroke(float stroke) {
			Stroke = stroke;
		}

		public float getSPM() {
			return SPM;
		}

		public void setSPM(float sPM) {
			SPM = sPM;
		}

		public float getFullnessCoefficient() {
			return FullnessCoefficient;
		}

		public void setFullnessCoefficient(float fullnessCoefficient) {
			FullnessCoefficient = fullnessCoefficient;
		}

		public float getLiquidVolumetricProduction() {
			return LiquidVolumetricProduction;
		}

		public void setLiquidVolumetricProduction(float liquidVolumetricProduction) {
			LiquidVolumetricProduction = liquidVolumetricProduction;
		}

		public float getOilVolumetricProduction() {
			return OilVolumetricProduction;
		}

		public void setOilVolumetricProduction(float oilVolumetricProduction) {
			OilVolumetricProduction = oilVolumetricProduction;
		}

		public float getWaterVolumetricProduction() {
			return WaterVolumetricProduction;
		}

		public void setWaterVolumetricProduction(float waterVolumetricProduction) {
			WaterVolumetricProduction = waterVolumetricProduction;
		}

		public float getLiquidWeightProduction() {
			return LiquidWeightProduction;
		}

		public void setLiquidWeightProduction(float liquidWeightProduction) {
			LiquidWeightProduction = liquidWeightProduction;
		}

		public float getOilWeightProduction() {
			return OilWeightProduction;
		}

		public void setOilWeightProduction(float oilWeightProduction) {
			OilWeightProduction = oilWeightProduction;
		}

		public float getWaterWeightProduction() {
			return WaterWeightProduction;
		}

		public void setWaterWeightProduction(float waterWeightProduction) {
			WaterWeightProduction = waterWeightProduction;
		}

		public float getVolumeWaterCut() {
			return VolumeWaterCut;
		}

		public void setVolumeWaterCut(float volumeWaterCut) {
			VolumeWaterCut = volumeWaterCut;
		}

		public float getWeightWaterCut() {
			return WeightWaterCut;
		}

		public void setWeightWaterCut(float weightWaterCut) {
			WeightWaterCut = weightWaterCut;
		}

		public float getPumpEff() {
			return PumpEff;
		}

		public void setPumpEff(float pumpEff) {
			PumpEff = pumpEff;
		}

		public float getPumpBoreDiameter() {
			return PumpBoreDiameter;
		}

		public void setPumpBoreDiameter(float pumpBoreDiameter) {
			PumpBoreDiameter = pumpBoreDiameter;
		}

		public float getPumpSettingDepth() {
			return PumpSettingDepth;
		}

		public void setPumpSettingDepth(float pumpSettingDepth) {
			PumpSettingDepth = pumpSettingDepth;
		}

		public float getProducingfluidLevel() {
			return ProducingfluidLevel;
		}

		public void setProducingfluidLevel(float producingfluidLevel) {
			ProducingfluidLevel = producingfluidLevel;
		}

		public float getSubmergence() {
			return Submergence;
		}

		public void setSubmergence(float submergence) {
			Submergence = submergence;
		}

		public int getETResultCode() {
			return ETResultCode;
		}

		public void setETResultCode(int eTResultCode) {
			ETResultCode = eTResultCode;
		}

		public float getWattDegreeBalance() {
			return WattDegreeBalance;
		}

		public void setWattDegreeBalance(float wattDegreeBalance) {
			WattDegreeBalance = wattDegreeBalance;
		}

		public float getIDegreeBalance() {
			return IDegreeBalance;
		}

		public void setIDegreeBalance(float iDegreeBalance) {
			IDegreeBalance = iDegreeBalance;
		}

		public float getDeltaRadius() {
			return DeltaRadius;
		}

		public void setDeltaRadius(float deltaRadius) {
			DeltaRadius = deltaRadius;
		}

		public float getSurfaceSystemEfficiency() {
			return SurfaceSystemEfficiency;
		}

		public void setSurfaceSystemEfficiency(float surfaceSystemEfficiency) {
			SurfaceSystemEfficiency = surfaceSystemEfficiency;
		}

		public float getWellDownSystemEfficiency() {
			return WellDownSystemEfficiency;
		}

		public void setWellDownSystemEfficiency(float wellDownSystemEfficiency) {
			WellDownSystemEfficiency = wellDownSystemEfficiency;
		}

		public float getSystemEfficiency() {
			return SystemEfficiency;
		}

		public void setSystemEfficiency(float systemEfficiency) {
			SystemEfficiency = systemEfficiency;
		}

		public float getPowerConsumptionPerTHM() {
			return PowerConsumptionPerTHM;
		}

		public void setPowerConsumptionPerTHM(float powerConsumptionPerTHM) {
			PowerConsumptionPerTHM = powerConsumptionPerTHM;
		}

		public float getIA() {
			return IA;
		}

		public void setIA(float iA) {
			IA = iA;
		}

		public float getIB() {
			return IB;
		}

		public void setIB(float iB) {
			IB = iB;
		}

		public float getIC() {
			return IC;
		}

		public void setIC(float iC) {
			IC = iC;
		}

		public float getVA() {
			return VA;
		}

		public void setVA(float vA) {
			VA = vA;
		}

		public float getVB() {
			return VB;
		}

		public void setVB(float vB) {
			VB = vB;
		}

		public float getVC() {
			return VC;
		}

		public void setVC(float vC) {
			VC = vC;
		}

		public float getRunningFrequency() {
			return RunningFrequency;
		}

		public void setRunningFrequency(float runningFrequency) {
			RunningFrequency = runningFrequency;
		}

		public float getRPM() {
			return RPM;
		}

		public void setRPM(float rPM) {
			RPM = rPM;
		}
	}
}
