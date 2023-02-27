package com.cosog.model.calculate;

import java.util.List;

public class TotalAnalysisRequestData {
	private String AKString;

    private String WellName;
    
    private int CurrentCommStatus;
    
    private int CurrentRunStatus;

    private String Date;

    private int OffsetHour;

    private List<String> AcqTime;

    private List<Integer> CommStatus;

    private float CommTime;

    private float CommTimeEfficiency;

    private String CommRange;

    private List<Integer> RunStatus;

    private float RunTime;

    private float RunTimeEfficiency;

    private String RunRange;

    private List<Integer> ResultCode;

    private List<Float> TheoreticalProduction;

    private List<Float> LiquidVolumetricProduction;

    private List<Float> OilVolumetricProduction;

    private List<Float> WaterVolumetricProduction;

    private List<Float> VolumeWaterCut;

    private List<Float> AvailablePlungerStrokeVolumetricProduction;

    private List<Float> PumpClearanceLeakVolumetricProduction;

    private List<Float> TVLeakVolumetricProduction;

    private List<Float> SVLeakVolumetricProduction;

    private List<Float> GasInfluenceVolumetricProduction;

    private List<Float> LiquidWeightProduction;

    private List<Float> OilWeightProduction;

    private List<Float> WaterWeightProduction;

    private List<Float> WeightWaterCut;

    private List<Float> AvailablePlungerStrokeWeightProduction;

    private List<Float> PumpClearanceLeakWeightProduction;

    private List<Float> TVLeakWeightProduction;

    private List<Float> SVLeakWeightProduction;

    private List<Float> GasInfluenceWeightProduction;

    private List<Float> SurfaceSystemEfficiency;

    private List<Float> WellDownSystemEfficiency;

    private List<Float> SystemEfficiency;

    private List<Float> EnergyPer100mLift;

    private List<Float> AvgWatt;

    private List<Float> PolishRodPower;

    private List<Float> WaterPower;

    private List<Float> Stroke;

    private List<Float> SPM;

    private List<Float> UpperLoadLine;

    private List<Float> LowerLoadLine;

    private List<Float> UpperLoadLineOfExact;

    private List<Float> DeltaLoadLine;

    private List<Float> DeltaLoadLineOfExact;

    private List<Float> FMax;

    private List<Float> FMin;

    private List<Float> DeltaF;

    private List<Float> Area;

    private List<Float> PlungerStroke;

    private List<Float> AvailablePlungerStroke;

    private List<Float> NoLiquidAvailablePlungerStroke;

    private List<Float> FullnessCoefficient;

    private List<Float> NoLiquidFullnessCoefficient;

    private List<Float> PumpBoreDiameter;

    private List<Float> ProducingfluidLevel;

    private List<Float> PumpSettingDepth;

    private List<Float> Submergence;

    private List<Float> LevelCorrectValue;

    private List<Float> PumpIntakeP;

    private List<Float> PumpIntakeT;

    private List<Float> PumpIntakeGOL;

    private List<Float> PumpIntakeVisl;

    private List<Float> PumpIntakeBo;

    private List<Float> PumpOutletP;

    private List<Float> PumpOutletT;

    private List<Float> PumpOutletGOL;

    private List<Float> PumpOutletVisl;

    private List<Float> PumpOutletBo;

    private List<Float> PumpEff;

    private List<Float> PumpEff1;

    private List<Float> PumpEff2;

    private List<Float> PumpEff3;

    private List<Float> PumpEff4;

    private List<Float> RodFlexLength;

    private List<Float> TubingFlexLength;

    private List<Float> InertiaLength;

    private List<Float> WattDegreeBalance;

    private List<Float> UpStrokeWattMax;

    private List<Float> DownStrokeWattMax;

    private List<Float> IDegreeBalance;

    private List<Float> UpStrokeIMax;

    private List<Float> DownStrokeIMax;

    private List<Float> DeltaRadius;

    private List<Float> TubingPressure;

    private List<Float> CasingPressure;
    
    private List<Float> BottomHolePressure;
    
    private List<Float> BottomHoleTemperature;

    private List<Float> WellHeadTemperature;

    private List<Float> ProductionGasOilRatio;

    private List<Float> IA;

    private List<Float> IB;

    private List<Float> IC;

    private List<Float> VA;

    private List<Float> VB;

    private List<Float> VC;

    private List<Float> Watt3;

    private List<Float> Var3;

    private List<Float> VA3;

    private List<Float> PF3;

    private List<Float> RunFrequency;

    private List<Float> Signal;

	public String getAKString() {
		return AKString;
	}

	public void setAKString(String aKString) {
		AKString = aKString;
	}

	public String getWellName() {
		return WellName;
	}

	public void setWellName(String wellName) {
		WellName = wellName;
	}

	public int getCurrentCommStatus() {
		return CurrentCommStatus;
	}

	public void setCurrentCommStatus(int currentCommStatus) {
		CurrentCommStatus = currentCommStatus;
	}

	public int getCurrentRunStatus() {
		return CurrentRunStatus;
	}

	public void setCurrentRunStatus(int currentRunStatus) {
		CurrentRunStatus = currentRunStatus;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public int getOffsetHour() {
		return OffsetHour;
	}

	public void setOffsetHour(int offsetHour) {
		OffsetHour = offsetHour;
	}

	public List<String> getAcqTime() {
		return AcqTime;
	}

	public void setAcqTime(List<String> acqTime) {
		AcqTime = acqTime;
	}

	public List<Integer> getCommStatus() {
		return CommStatus;
	}

	public void setCommStatus(List<Integer> commStatus) {
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

	public List<Integer> getRunStatus() {
		return RunStatus;
	}

	public void setRunStatus(List<Integer> runStatus) {
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

	public void setRunTimeEfficiency(int runTimeEfficiency) {
		RunTimeEfficiency = runTimeEfficiency;
	}

	public String getRunRange() {
		return RunRange;
	}

	public void setRunRange(String runRange) {
		RunRange = runRange;
	}

	public List<Integer> getResultCode() {
		return ResultCode;
	}

	public void setResultCode(List<Integer> resultCode) {
		ResultCode = resultCode;
	}

	public List<Float> getTheoreticalProduction() {
		return TheoreticalProduction;
	}

	public void setTheoreticalProduction(List<Float> theoreticalProduction) {
		TheoreticalProduction = theoreticalProduction;
	}

	public List<Float> getLiquidVolumetricProduction() {
		return LiquidVolumetricProduction;
	}

	public void setLiquidVolumetricProduction(List<Float> liquidVolumetricProduction) {
		LiquidVolumetricProduction = liquidVolumetricProduction;
	}

	public List<Float> getOilVolumetricProduction() {
		return OilVolumetricProduction;
	}

	public void setOilVolumetricProduction(List<Float> oilVolumetricProduction) {
		OilVolumetricProduction = oilVolumetricProduction;
	}

	public List<Float> getWaterVolumetricProduction() {
		return WaterVolumetricProduction;
	}

	public void setWaterVolumetricProduction(List<Float> waterVolumetricProduction) {
		WaterVolumetricProduction = waterVolumetricProduction;
	}

	public List<Float> getVolumeWaterCut() {
		return VolumeWaterCut;
	}

	public void setVolumeWaterCut(List<Float> volumeWaterCut) {
		VolumeWaterCut = volumeWaterCut;
	}

	public List<Float> getAvailablePlungerStrokeVolumetricProduction() {
		return AvailablePlungerStrokeVolumetricProduction;
	}

	public void setAvailablePlungerStrokeVolumetricProduction(List<Float> availablePlungerStrokeVolumetricProduction) {
		AvailablePlungerStrokeVolumetricProduction = availablePlungerStrokeVolumetricProduction;
	}

	public List<Float> getPumpClearanceLeakVolumetricProduction() {
		return PumpClearanceLeakVolumetricProduction;
	}

	public void setPumpClearanceLeakVolumetricProduction(List<Float> pumpClearanceLeakVolumetricProduction) {
		PumpClearanceLeakVolumetricProduction = pumpClearanceLeakVolumetricProduction;
	}

	public List<Float> getTVLeakVolumetricProduction() {
		return TVLeakVolumetricProduction;
	}

	public void setTVLeakVolumetricProduction(List<Float> tVLeakVolumetricProduction) {
		TVLeakVolumetricProduction = tVLeakVolumetricProduction;
	}

	public List<Float> getSVLeakVolumetricProduction() {
		return SVLeakVolumetricProduction;
	}

	public void setSVLeakVolumetricProduction(List<Float> sVLeakVolumetricProduction) {
		SVLeakVolumetricProduction = sVLeakVolumetricProduction;
	}

	public List<Float> getGasInfluenceVolumetricProduction() {
		return GasInfluenceVolumetricProduction;
	}

	public void setGasInfluenceVolumetricProduction(List<Float> gasInfluenceVolumetricProduction) {
		GasInfluenceVolumetricProduction = gasInfluenceVolumetricProduction;
	}

	public List<Float> getLiquidWeightProduction() {
		return LiquidWeightProduction;
	}

	public void setLiquidWeightProduction(List<Float> liquidWeightProduction) {
		LiquidWeightProduction = liquidWeightProduction;
	}

	public List<Float> getOilWeightProduction() {
		return OilWeightProduction;
	}

	public void setOilWeightProduction(List<Float> oilWeightProduction) {
		OilWeightProduction = oilWeightProduction;
	}

	public List<Float> getWaterWeightProduction() {
		return WaterWeightProduction;
	}

	public void setWaterWeightProduction(List<Float> waterWeightProduction) {
		WaterWeightProduction = waterWeightProduction;
	}

	public List<Float> getWeightWaterCut() {
		return WeightWaterCut;
	}

	public void setWeightWaterCut(List<Float> weightWaterCut) {
		WeightWaterCut = weightWaterCut;
	}

	public List<Float> getAvailablePlungerStrokeWeightProduction() {
		return AvailablePlungerStrokeWeightProduction;
	}

	public void setAvailablePlungerStrokeWeightProduction(List<Float> availablePlungerStrokeWeightProduction) {
		AvailablePlungerStrokeWeightProduction = availablePlungerStrokeWeightProduction;
	}

	public List<Float> getPumpClearanceLeakWeightProduction() {
		return PumpClearanceLeakWeightProduction;
	}

	public void setPumpClearanceLeakWeightProduction(List<Float> pumpClearanceLeakWeightProduction) {
		PumpClearanceLeakWeightProduction = pumpClearanceLeakWeightProduction;
	}

	public List<Float> getTVLeakWeightProduction() {
		return TVLeakWeightProduction;
	}

	public void setTVLeakWeightProduction(List<Float> tVLeakWeightProduction) {
		TVLeakWeightProduction = tVLeakWeightProduction;
	}

	public List<Float> getSVLeakWeightProduction() {
		return SVLeakWeightProduction;
	}

	public void setSVLeakWeightProduction(List<Float> sVLeakWeightProduction) {
		SVLeakWeightProduction = sVLeakWeightProduction;
	}

	public List<Float> getGasInfluenceWeightProduction() {
		return GasInfluenceWeightProduction;
	}

	public void setGasInfluenceWeightProduction(List<Float> gasInfluenceWeightProduction) {
		GasInfluenceWeightProduction = gasInfluenceWeightProduction;
	}

	public List<Float> getSurfaceSystemEfficiency() {
		return SurfaceSystemEfficiency;
	}

	public void setSurfaceSystemEfficiency(List<Float> surfaceSystemEfficiency) {
		SurfaceSystemEfficiency = surfaceSystemEfficiency;
	}

	public List<Float> getWellDownSystemEfficiency() {
		return WellDownSystemEfficiency;
	}

	public void setWellDownSystemEfficiency(List<Float> wellDownSystemEfficiency) {
		WellDownSystemEfficiency = wellDownSystemEfficiency;
	}

	public List<Float> getSystemEfficiency() {
		return SystemEfficiency;
	}

	public void setSystemEfficiency(List<Float> systemEfficiency) {
		SystemEfficiency = systemEfficiency;
	}

	public List<Float> getEnergyPer100mLift() {
		return EnergyPer100mLift;
	}

	public void setEnergyPer100mLift(List<Float> energyPer100mLift) {
		EnergyPer100mLift = energyPer100mLift;
	}

	public List<Float> getAvgWatt() {
		return AvgWatt;
	}

	public void setAvgWatt(List<Float> avgWatt) {
		AvgWatt = avgWatt;
	}

	public List<Float> getPolishRodPower() {
		return PolishRodPower;
	}

	public void setPolishRodPower(List<Float> polishRodPower) {
		PolishRodPower = polishRodPower;
	}

	public List<Float> getWaterPower() {
		return WaterPower;
	}

	public void setWaterPower(List<Float> waterPower) {
		WaterPower = waterPower;
	}

	public List<Float> getStroke() {
		return Stroke;
	}

	public void setStroke(List<Float> stroke) {
		Stroke = stroke;
	}

	public List<Float> getSPM() {
		return SPM;
	}

	public void setSPM(List<Float> sPM) {
		SPM = sPM;
	}

	public List<Float> getUpperLoadLine() {
		return UpperLoadLine;
	}

	public void setUpperLoadLine(List<Float> upperLoadLine) {
		UpperLoadLine = upperLoadLine;
	}

	public List<Float> getLowerLoadLine() {
		return LowerLoadLine;
	}

	public void setLowerLoadLine(List<Float> lowerLoadLine) {
		LowerLoadLine = lowerLoadLine;
	}

	public List<Float> getUpperLoadLineOfExact() {
		return UpperLoadLineOfExact;
	}

	public void setUpperLoadLineOfExact(List<Float> upperLoadLineOfExact) {
		UpperLoadLineOfExact = upperLoadLineOfExact;
	}

	public List<Float> getDeltaLoadLine() {
		return DeltaLoadLine;
	}

	public void setDeltaLoadLine(List<Float> deltaLoadLine) {
		DeltaLoadLine = deltaLoadLine;
	}

	public List<Float> getDeltaLoadLineOfExact() {
		return DeltaLoadLineOfExact;
	}

	public void setDeltaLoadLineOfExact(List<Float> deltaLoadLineOfExact) {
		DeltaLoadLineOfExact = deltaLoadLineOfExact;
	}

	public List<Float> getFMax() {
		return FMax;
	}

	public void setFMax(List<Float> fMax) {
		FMax = fMax;
	}

	public List<Float> getFMin() {
		return FMin;
	}

	public void setFMin(List<Float> fMin) {
		FMin = fMin;
	}

	public List<Float> getDeltaF() {
		return DeltaF;
	}

	public void setDeltaF(List<Float> deltaF) {
		DeltaF = deltaF;
	}

	public List<Float> getArea() {
		return Area;
	}

	public void setArea(List<Float> area) {
		Area = area;
	}

	public List<Float> getPlungerStroke() {
		return PlungerStroke;
	}

	public void setPlungerStroke(List<Float> plungerStroke) {
		PlungerStroke = plungerStroke;
	}

	public List<Float> getAvailablePlungerStroke() {
		return AvailablePlungerStroke;
	}

	public void setAvailablePlungerStroke(List<Float> availablePlungerStroke) {
		AvailablePlungerStroke = availablePlungerStroke;
	}

	public List<Float> getNoLiquidAvailablePlungerStroke() {
		return NoLiquidAvailablePlungerStroke;
	}

	public void setNoLiquidAvailablePlungerStroke(List<Float> noLiquidAvailablePlungerStroke) {
		NoLiquidAvailablePlungerStroke = noLiquidAvailablePlungerStroke;
	}

	public List<Float> getFullnessCoefficient() {
		return FullnessCoefficient;
	}

	public void setFullnessCoefficient(List<Float> fullnessCoefficient) {
		FullnessCoefficient = fullnessCoefficient;
	}

	public List<Float> getNoLiquidFullnessCoefficient() {
		return NoLiquidFullnessCoefficient;
	}

	public void setNoLiquidFullnessCoefficient(List<Float> noLiquidFullnessCoefficient) {
		NoLiquidFullnessCoefficient = noLiquidFullnessCoefficient;
	}

	public List<Float> getPumpBoreDiameter() {
		return PumpBoreDiameter;
	}

	public void setPumpBoreDiameter(List<Float> pumpBoreDiameter) {
		PumpBoreDiameter = pumpBoreDiameter;
	}

	public List<Float> getProducingfluidLevel() {
		return ProducingfluidLevel;
	}

	public void setProducingfluidLevel(List<Float> producingfluidLevel) {
		ProducingfluidLevel = producingfluidLevel;
	}

	public List<Float> getPumpSettingDepth() {
		return PumpSettingDepth;
	}

	public void setPumpSettingDepth(List<Float> pumpSettingDepth) {
		PumpSettingDepth = pumpSettingDepth;
	}

	public List<Float> getSubmergence() {
		return Submergence;
	}

	public void setSubmergence(List<Float> submergence) {
		Submergence = submergence;
	}

	public List<Float> getLevelCorrectValue() {
		return LevelCorrectValue;
	}

	public void setLevelCorrectValue(List<Float> levelCorrectValue) {
		LevelCorrectValue = levelCorrectValue;
	}

	public List<Float> getPumpIntakeP() {
		return PumpIntakeP;
	}

	public void setPumpIntakeP(List<Float> pumpIntakeP) {
		PumpIntakeP = pumpIntakeP;
	}

	public List<Float> getPumpIntakeT() {
		return PumpIntakeT;
	}

	public void setPumpIntakeT(List<Float> pumpIntakeT) {
		PumpIntakeT = pumpIntakeT;
	}

	public List<Float> getPumpIntakeGOL() {
		return PumpIntakeGOL;
	}

	public void setPumpIntakeGOL(List<Float> pumpIntakeGOL) {
		PumpIntakeGOL = pumpIntakeGOL;
	}

	public List<Float> getPumpIntakeVisl() {
		return PumpIntakeVisl;
	}

	public void setPumpIntakeVisl(List<Float> pumpIntakeVisl) {
		PumpIntakeVisl = pumpIntakeVisl;
	}

	public List<Float> getPumpIntakeBo() {
		return PumpIntakeBo;
	}

	public void setPumpIntakeBo(List<Float> pumpIntakeBo) {
		PumpIntakeBo = pumpIntakeBo;
	}

	public List<Float> getPumpOutletP() {
		return PumpOutletP;
	}

	public void setPumpOutletP(List<Float> pumpOutletP) {
		PumpOutletP = pumpOutletP;
	}

	public List<Float> getPumpOutletT() {
		return PumpOutletT;
	}

	public void setPumpOutletT(List<Float> pumpOutletT) {
		PumpOutletT = pumpOutletT;
	}

	public List<Float> getPumpOutletGOL() {
		return PumpOutletGOL;
	}

	public void setPumpOutletGOL(List<Float> pumpOutletGOL) {
		PumpOutletGOL = pumpOutletGOL;
	}

	public List<Float> getPumpOutletVisl() {
		return PumpOutletVisl;
	}

	public void setPumpOutletVisl(List<Float> pumpOutletVisl) {
		PumpOutletVisl = pumpOutletVisl;
	}

	public List<Float> getPumpOutletBo() {
		return PumpOutletBo;
	}

	public void setPumpOutletBo(List<Float> pumpOutletBo) {
		PumpOutletBo = pumpOutletBo;
	}

	public List<Float> getPumpEff() {
		return PumpEff;
	}

	public void setPumpEff(List<Float> pumpEff) {
		PumpEff = pumpEff;
	}

	public List<Float> getPumpEff1() {
		return PumpEff1;
	}

	public void setPumpEff1(List<Float> pumpEff1) {
		PumpEff1 = pumpEff1;
	}

	public List<Float> getPumpEff2() {
		return PumpEff2;
	}

	public void setPumpEff2(List<Float> pumpEff2) {
		PumpEff2 = pumpEff2;
	}

	public List<Float> getPumpEff3() {
		return PumpEff3;
	}

	public void setPumpEff3(List<Float> pumpEff3) {
		PumpEff3 = pumpEff3;
	}

	public List<Float> getPumpEff4() {
		return PumpEff4;
	}

	public void setPumpEff4(List<Float> pumpEff4) {
		PumpEff4 = pumpEff4;
	}

	public List<Float> getRodFlexLength() {
		return RodFlexLength;
	}

	public void setRodFlexLength(List<Float> rodFlexLength) {
		RodFlexLength = rodFlexLength;
	}

	public List<Float> getTubingFlexLength() {
		return TubingFlexLength;
	}

	public void setTubingFlexLength(List<Float> tubingFlexLength) {
		TubingFlexLength = tubingFlexLength;
	}

	public List<Float> getInertiaLength() {
		return InertiaLength;
	}

	public void setInertiaLength(List<Float> inertiaLength) {
		InertiaLength = inertiaLength;
	}

	public List<Float> getWattDegreeBalance() {
		return WattDegreeBalance;
	}

	public void setWattDegreeBalance(List<Float> wattDegreeBalance) {
		WattDegreeBalance = wattDegreeBalance;
	}

	public List<Float> getUpStrokeWattMax() {
		return UpStrokeWattMax;
	}

	public void setUpStrokeWattMax(List<Float> upStrokeWattMax) {
		UpStrokeWattMax = upStrokeWattMax;
	}

	public List<Float> getDownStrokeWattMax() {
		return DownStrokeWattMax;
	}

	public void setDownStrokeWattMax(List<Float> downStrokeWattMax) {
		DownStrokeWattMax = downStrokeWattMax;
	}

	public List<Float> getIDegreeBalance() {
		return IDegreeBalance;
	}

	public void setIDegreeBalance(List<Float> iDegreeBalance) {
		IDegreeBalance = iDegreeBalance;
	}

	public List<Float> getUpStrokeIMax() {
		return UpStrokeIMax;
	}

	public void setUpStrokeIMax(List<Float> upStrokeIMax) {
		UpStrokeIMax = upStrokeIMax;
	}

	public List<Float> getDownStrokeIMax() {
		return DownStrokeIMax;
	}

	public void setDownStrokeIMax(List<Float> downStrokeIMax) {
		DownStrokeIMax = downStrokeIMax;
	}

	public List<Float> getDeltaRadius() {
		return DeltaRadius;
	}

	public void setDeltaRadius(List<Float> deltaRadius) {
		DeltaRadius = deltaRadius;
	}

	public List<Float> getTubingPressure() {
		return TubingPressure;
	}

	public void setTubingPressure(List<Float> tubingPressure) {
		TubingPressure = tubingPressure;
	}

	public List<Float> getCasingPressure() {
		return CasingPressure;
	}

	public void setCasingPressure(List<Float> casingPressure) {
		CasingPressure = casingPressure;
	}

	public List<Float> getBottomHolePressure() {
		return BottomHolePressure;
	}

	public void setBottomHolePressure(List<Float> bottomHolePressure) {
		BottomHolePressure = bottomHolePressure;
	}

	public List<Float> getWellHeadTemperature() {
		return WellHeadTemperature;
	}

	public void setWellHeadTemperature(List<Float> wellHeadTemperature) {
		WellHeadTemperature = wellHeadTemperature;
	}

	public List<Float> getProductionGasOilRatio() {
		return ProductionGasOilRatio;
	}

	public void setProductionGasOilRatio(List<Float> productionGasOilRatio) {
		ProductionGasOilRatio = productionGasOilRatio;
	}

	public List<Float> getIA() {
		return IA;
	}

	public void setIA(List<Float> iA) {
		IA = iA;
	}

	public List<Float> getIB() {
		return IB;
	}

	public void setIB(List<Float> iB) {
		IB = iB;
	}

	public List<Float> getIC() {
		return IC;
	}

	public void setIC(List<Float> iC) {
		IC = iC;
	}

	public List<Float> getVA() {
		return VA;
	}

	public void setVA(List<Float> vA) {
		VA = vA;
	}

	public List<Float> getVB() {
		return VB;
	}

	public void setVB(List<Float> vB) {
		VB = vB;
	}

	public List<Float> getVC() {
		return VC;
	}

	public void setVC(List<Float> vC) {
		VC = vC;
	}

	public List<Float> getWatt3() {
		return Watt3;
	}

	public void setWatt3(List<Float> watt3) {
		Watt3 = watt3;
	}

	public List<Float> getVar3() {
		return Var3;
	}

	public void setVar3(List<Float> var3) {
		Var3 = var3;
	}

	public List<Float> getVA3() {
		return VA3;
	}

	public void setVA3(List<Float> vA3) {
		VA3 = vA3;
	}

	public List<Float> getPF3() {
		return PF3;
	}

	public void setPF3(List<Float> pF3) {
		PF3 = pF3;
	}

	public List<Float> getRunFrequency() {
		return RunFrequency;
	}

	public void setRunFrequency(List<Float> runFrequency) {
		RunFrequency = runFrequency;
	}

	public List<Float> getSignal() {
		return Signal;
	}

	public void setSignal(List<Float> signal) {
		Signal = signal;
	}

	public List<Float> getBottomHoleTemperature() {
		return BottomHoleTemperature;
	}

	public void setBottomHoleTemperature(List<Float> bottomHoleTemperature) {
		BottomHoleTemperature = bottomHoleTemperature;
	}

	public void setRunTimeEfficiency(float runTimeEfficiency) {
		RunTimeEfficiency = runTimeEfficiency;
	}

    
}
