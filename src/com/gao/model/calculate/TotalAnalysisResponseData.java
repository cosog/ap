package com.gao.model.calculate;

public class TotalAnalysisResponseData {
	
	private String WellName;

    private int ResultStatus;

    private Verification Verification;
    
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
    
    private Item TubingPressure;

    private Item CasingPressure;

    private Item WellHeadFluidTemperature;

    private Item ProductionGasOilRatio;

    private int FSResultCode;

    private String FSResultString;

    private int ExtendedDays;

    private Item Stroke;

    private Item SPM;

    private Item FullnessCoefficient;

    private Item LiquidVolumetricProduction;

    private Item OilVolumetricProduction;

    private Item WaterVolumetricProduction;

    private Item LiquidWeightProduction;

    private Item OilWeightProduction;

    private Item WaterWeightProduction;

    private Item VolumeWaterCut;

    private Item WeightWaterCut;

    private Item PumpEff;

    private Item PumpBoreDiameter;

    private Item PumpSettingDepth;

    private Item ProducingfluidLevel;

    private Item Submergence;

    private int ETResultCode;

    private String ETResultString;

    private Item WattDegreeBalance;

    private Item IDegreeBalance;

    private Item DeltaRadius;

    private Item SurfaceSystemEfficiency;

    private Item WellDownSystemEfficiency;

    private Item SystemEfficiency;

    private Item PowerConsumptionPerTHM;

    private Item IA;

    private Item IB;

    private Item IC;
    
    private String IMaxString;
    
    private String IMinString;

    private Item VA;

    private Item VB;

    private Item VC;
    
    private String VMaxString;
    
    private String VMinString;

    private Item RunFrequency;
    
    private Item RPM;

    
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
	
	public static class Item
	{
	    private float Value;

	    private float Max;

	    private float Min;

	    public void setValue(float Value){
	        this.Value = Value;
	    }
	    public float getValue(){
	        return this.Value;
	    }
	    public void setMax(float Max){
	        this.Max = Max;
	    }
	    public float getMax(){
	        return this.Max;
	    }
	    public void setMin(float Min){
	        this.Min = Min;
	    }
	    public float getMin(){
	        return this.Min;
	    }
	}

	public String getWellName() {
		return WellName;
	}

	public void setWellName(String wellName) {
		WellName = wellName;
	}

	public int getResultStatus() {
		return ResultStatus;
	}

	public void setResultStatus(int resultStatus) {
		ResultStatus = resultStatus;
	}

	public Verification getVerification() {
		return Verification;
	}

	public void setVerification(Verification verification) {
		Verification = verification;
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

	public Item getTubingPressure() {
		return TubingPressure;
	}

	public void setTubingPressure(Item tubingPressure) {
		TubingPressure = tubingPressure;
	}

	public Item getCasingPressure() {
		return CasingPressure;
	}

	public void setCasingPressure(Item casingPressure) {
		CasingPressure = casingPressure;
	}

	public Item getWellHeadFluidTemperature() {
		return WellHeadFluidTemperature;
	}

	public void setWellHeadFluidTemperature(Item wellHeadFluidTemperature) {
		WellHeadFluidTemperature = wellHeadFluidTemperature;
	}

	public Item getProductionGasOilRatio() {
		return ProductionGasOilRatio;
	}

	public void setProductionGasOilRatio(Item productionGasOilRatio) {
		ProductionGasOilRatio = productionGasOilRatio;
	}

	public int getFSResultCode() {
		return FSResultCode;
	}

	public void setFSResultCode(int fSResultCode) {
		FSResultCode = fSResultCode;
	}

	public String getFSResultString() {
		return FSResultString;
	}

	public void setFSResultString(String fSResultString) {
		FSResultString = fSResultString;
	}

	public int getExtendedDays() {
		return ExtendedDays;
	}

	public void setExtendedDays(int extendedDays) {
		ExtendedDays = extendedDays;
	}

	public Item getStroke() {
		return Stroke;
	}

	public void setStroke(Item stroke) {
		Stroke = stroke;
	}

	public Item getSPM() {
		return SPM;
	}

	public void setSPM(Item sPM) {
		SPM = sPM;
	}

	public Item getFullnessCoefficient() {
		return FullnessCoefficient;
	}

	public void setFullnessCoefficient(Item fullnessCoefficient) {
		FullnessCoefficient = fullnessCoefficient;
	}

	public Item getLiquidVolumetricProduction() {
		return LiquidVolumetricProduction;
	}

	public void setLiquidVolumetricProduction(Item liquidVolumetricProduction) {
		LiquidVolumetricProduction = liquidVolumetricProduction;
	}

	public Item getOilVolumetricProduction() {
		return OilVolumetricProduction;
	}

	public void setOilVolumetricProduction(Item oilVolumetricProduction) {
		OilVolumetricProduction = oilVolumetricProduction;
	}

	public Item getWaterVolumetricProduction() {
		return WaterVolumetricProduction;
	}

	public void setWaterVolumetricProduction(Item waterVolumetricProduction) {
		WaterVolumetricProduction = waterVolumetricProduction;
	}

	public Item getLiquidWeightProduction() {
		return LiquidWeightProduction;
	}

	public void setLiquidWeightProduction(Item liquidWeightProduction) {
		LiquidWeightProduction = liquidWeightProduction;
	}

	public Item getOilWeightProduction() {
		return OilWeightProduction;
	}

	public void setOilWeightProduction(Item oilWeightProduction) {
		OilWeightProduction = oilWeightProduction;
	}

	public Item getWaterWeightProduction() {
		return WaterWeightProduction;
	}

	public void setWaterWeightProduction(Item waterWeightProduction) {
		WaterWeightProduction = waterWeightProduction;
	}

	public Item getVolumeWaterCut() {
		return VolumeWaterCut;
	}

	public void setVolumeWaterCut(Item volumeWaterCut) {
		VolumeWaterCut = volumeWaterCut;
	}

	public Item getWeightWaterCut() {
		return WeightWaterCut;
	}

	public void setWeightWaterCut(Item weightWaterCut) {
		WeightWaterCut = weightWaterCut;
	}

	public Item getPumpEff() {
		return PumpEff;
	}

	public void setPumpEff(Item pumpEff) {
		PumpEff = pumpEff;
	}

	public Item getPumpBoreDiameter() {
		return PumpBoreDiameter;
	}

	public void setPumpBoreDiameter(Item pumpBoreDiameter) {
		PumpBoreDiameter = pumpBoreDiameter;
	}

	public Item getPumpSettingDepth() {
		return PumpSettingDepth;
	}

	public void setPumpSettingDepth(Item pumpSettingDepth) {
		PumpSettingDepth = pumpSettingDepth;
	}

	public Item getProducingfluidLevel() {
		return ProducingfluidLevel;
	}

	public void setProducingfluidLevel(Item producingfluidLevel) {
		ProducingfluidLevel = producingfluidLevel;
	}

	public Item getSubmergence() {
		return Submergence;
	}

	public void setSubmergence(Item submergence) {
		Submergence = submergence;
	}

	public int getETResultCode() {
		return ETResultCode;
	}

	public void setETResultCode(int eTResultCode) {
		ETResultCode = eTResultCode;
	}

	public String getETResultString() {
		return ETResultString;
	}

	public void setETResultString(String eTResultString) {
		ETResultString = eTResultString;
	}

	public Item getWattDegreeBalance() {
		return WattDegreeBalance;
	}

	public void setWattDegreeBalance(Item wattDegreeBalance) {
		WattDegreeBalance = wattDegreeBalance;
	}

	public Item getIDegreeBalance() {
		return IDegreeBalance;
	}

	public void setIDegreeBalance(Item iDegreeBalance) {
		IDegreeBalance = iDegreeBalance;
	}

	public Item getDeltaRadius() {
		return DeltaRadius;
	}

	public void setDeltaRadius(Item deltaRadius) {
		DeltaRadius = deltaRadius;
	}

	public Item getSurfaceSystemEfficiency() {
		return SurfaceSystemEfficiency;
	}

	public void setSurfaceSystemEfficiency(Item surfaceSystemEfficiency) {
		SurfaceSystemEfficiency = surfaceSystemEfficiency;
	}

	public Item getWellDownSystemEfficiency() {
		return WellDownSystemEfficiency;
	}

	public void setWellDownSystemEfficiency(Item wellDownSystemEfficiency) {
		WellDownSystemEfficiency = wellDownSystemEfficiency;
	}

	public Item getSystemEfficiency() {
		return SystemEfficiency;
	}

	public void setSystemEfficiency(Item systemEfficiency) {
		SystemEfficiency = systemEfficiency;
	}

	public Item getPowerConsumptionPerTHM() {
		return PowerConsumptionPerTHM;
	}

	public void setPowerConsumptionPerTHM(Item powerConsumptionPerTHM) {
		PowerConsumptionPerTHM = powerConsumptionPerTHM;
	}

	public Item getIA() {
		return IA;
	}

	public void setIA(Item iA) {
		IA = iA;
	}

	public Item getIB() {
		return IB;
	}

	public void setIB(Item iB) {
		IB = iB;
	}

	public Item getIC() {
		return IC;
	}

	public void setIC(Item iC) {
		IC = iC;
	}

	public String getIMaxString() {
		return IMaxString;
	}

	public void setIMaxString(String iMaxString) {
		IMaxString = iMaxString;
	}

	public String getIMinString() {
		return IMinString;
	}

	public void setIMinString(String iMinString) {
		IMinString = iMinString;
	}

	public Item getVA() {
		return VA;
	}

	public void setVA(Item vA) {
		VA = vA;
	}

	public Item getVB() {
		return VB;
	}

	public void setVB(Item vB) {
		VB = vB;
	}

	public Item getVC() {
		return VC;
	}

	public void setVC(Item vC) {
		VC = vC;
	}

	public String getVMaxString() {
		return VMaxString;
	}

	public void setVMaxString(String vMaxString) {
		VMaxString = vMaxString;
	}

	public String getVMinString() {
		return VMinString;
	}

	public void setVMinString(String vMinString) {
		VMinString = vMinString;
	}

	public Item getRunFrequency() {
		return RunFrequency;
	}

	public void setRunFrequency(Item runFrequency) {
		RunFrequency = runFrequency;
	}

	public Item getRPM() {
		return RPM;
	}

	public void setRPM(Item rPM) {
		RPM = rPM;
	}
}