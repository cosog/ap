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
    private Item UpperLoadLine; //理论上载荷线				kN
    private Item LowerLoadLine; //理论下载荷线				kN
    private Item UpperLoadLineOfExact; //考虑沉没压力的上载荷线		kN
    private Item DeltaLoadLine; //理论液柱载荷				kN
    private Item DeltaLoadLineOfExact; //考虑沉没压力的理论液柱载荷	kN
    private Item FMax; //最大载荷					kN
    private Item FMin; //最小载荷					kN
    private Item DeltaF; //载荷差					kN
    private Item Area; //功图面积					kN·m
    private Item PlungerStroke; //柱塞冲程					m
    private Item AvailablePlungerStroke; //柱塞有效冲程
    private Item NoLiquidAvailablePlungerStroke; //柱塞有效冲程
    private Item FullnessCoefficient;
    private Item NoLiquidFullnessCoefficient ;
    private Item TheoreticalProduction; //理论排量
    private Item LiquidVolumetricProduction;
    private Item OilVolumetricProduction;
    private Item WaterVolumetricProduction;
    private Item AvailablePlungerStrokeVolumetricProduction; //柱塞有效冲程计算产量		m^3/d
    private Item PumpClearanceLeakVolumetricProduction; //泵间隙漏失量				m^3/d
    private Item TVLeakVolumetricProduction; //游动凡尔漏失量			m^3/d
    private Item SVLeakVolumetricProduction; //固定凡尔漏失量			m^3/d
    private Item GasInfluenceVolumetricProduction; //气影响					m^3/d
    private Item LiquidWeightProduction;
    private Item OilWeightProduction;
    private Item WaterWeightProduction;
    private Item AvailablePlungerStrokeWeightProduction; //柱塞有效冲程计算产量		t/d
    private Item PumpClearanceLeakWeightProduction; //泵间隙漏失量				t/d
    private Item TVLeakWeightProduction; //游动凡尔漏失量			t/d
    private Item SVLeakWeightProduction; //固定凡尔漏失量			t/d
    private Item GasInfluenceWeightProduction; //气影响					t/d
    private Item VolumeWaterCut;
    private Item WeightWaterCut;
    private Item PumpEff;
    private Item PumpEff1; //冲程损失系数				小数
    private Item RodFlexLength; //抽油杆伸长量				m
    private Item TubingFlexLength; //计算油管伸缩值			m
    private Item InertiaLength; //惯性载荷下冲程增量			m
    private Item PumpEff2; //充满系数					小数
    private Item PumpEff3; //间隙漏失系数				小数
    private Item PumpEff4; //液体收缩系数				小数
    private Item PumpBoreDiameter;
    private Item PumpSettingDepth;
    private Item ProducingfluidLevel;
    private Item Submergence;
    private Item LevelCorrectValue;
    private Item PumpIntakeP; //泵入口压力				MPa
    private Item PumpIntakeT; //泵入口温度				℃
    private Item PumpIntakeGOL; //泵入口就地气液比
    private Item PumpIntakeVisl; //泵入口粘度				mPa·s
    private Item PumpIntakeBo; //泵入口原油体积系数
    private Item PumpOutletP; //泵出口压力				MPa
    private Item PumpOutletT; //泵出口温度				℃
    private Item PumpOutletGOL; //泵出口就地气液比
    private Item PumpOutletVisl; //泵出口粘度				mPa·s
    private Item PumpOutletBo; //泵出口原油体积系数
    private int ETResultCode;
    private String ETResultString;
    private Item WattDegreeBalance;
    private Item UpStrokeWattMax; //上冲程功率最大值			kW
    private Item DownStrokeWattMax; //下冲程功率最大值			kW
    private Item IDegreeBalance;
    private Item UpStrokeIMax; //上冲程电流最大值			A
    private Item DownStrokeIMax; //下冲程电流最大值			A
    private Item DeltaRadius;
    private Item SurfaceSystemEfficiency;
    private Item WellDownSystemEfficiency;
    private Item SystemEfficiency;
    private Item PowerConsumptionPerTHM;
    private Item AvgWatt; //平均有功功率        		kW
    private Item PolishRodPower; //光杆功率              	kW
    private Item WaterPower; //水功率             		kW
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
    private Item Signal; //信号强度
    private Item WattSum; //有功功率					kW
    private Item VarSum; //无功功率					kVar
    private Item VASum; //视在功率					kVA
    private Item PFSum; //功率因数					小数

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

	public Item getUpperLoadLine() {
		return UpperLoadLine;
	}

	public void setUpperLoadLine(Item upperLoadLine) {
		UpperLoadLine = upperLoadLine;
	}

	public Item getLowerLoadLine() {
		return LowerLoadLine;
	}

	public void setLowerLoadLine(Item lowerLoadLine) {
		LowerLoadLine = lowerLoadLine;
	}

	public Item getUpperLoadLineOfExact() {
		return UpperLoadLineOfExact;
	}

	public void setUpperLoadLineOfExact(Item upperLoadLineOfExact) {
		UpperLoadLineOfExact = upperLoadLineOfExact;
	}

	public Item getDeltaLoadLine() {
		return DeltaLoadLine;
	}

	public void setDeltaLoadLine(Item deltaLoadLine) {
		DeltaLoadLine = deltaLoadLine;
	}

	public Item getDeltaLoadLineOfExact() {
		return DeltaLoadLineOfExact;
	}

	public void setDeltaLoadLineOfExact(Item deltaLoadLineOfExact) {
		DeltaLoadLineOfExact = deltaLoadLineOfExact;
	}

	public Item getFMax() {
		return FMax;
	}

	public void setFMax(Item fMax) {
		FMax = fMax;
	}

	public Item getFMin() {
		return FMin;
	}

	public void setFMin(Item fMin) {
		FMin = fMin;
	}

	public Item getDeltaF() {
		return DeltaF;
	}

	public void setDeltaF(Item deltaF) {
		DeltaF = deltaF;
	}

	public Item getArea() {
		return Area;
	}

	public void setArea(Item area) {
		Area = area;
	}

	public Item getPlungerStroke() {
		return PlungerStroke;
	}

	public void setPlungerStroke(Item plungerStroke) {
		PlungerStroke = plungerStroke;
	}

	public Item getAvailablePlungerStroke() {
		return AvailablePlungerStroke;
	}

	public void setAvailablePlungerStroke(Item availablePlungerStroke) {
		AvailablePlungerStroke = availablePlungerStroke;
	}

	public Item getTheoreticalProduction() {
		return TheoreticalProduction;
	}

	public void setTheoreticalProduction(Item theoreticalProduction) {
		TheoreticalProduction = theoreticalProduction;
	}

	public Item getAvailablePlungerStrokeVolumetricProduction() {
		return AvailablePlungerStrokeVolumetricProduction;
	}

	public void setAvailablePlungerStrokeVolumetricProduction(Item availablePlungerStrokeVolumetricProduction) {
		AvailablePlungerStrokeVolumetricProduction = availablePlungerStrokeVolumetricProduction;
	}

	public Item getPumpClearanceLeakVolumetricProduction() {
		return PumpClearanceLeakVolumetricProduction;
	}

	public void setPumpClearanceLeakVolumetricProduction(Item pumpClearanceLeakVolumetricProduction) {
		PumpClearanceLeakVolumetricProduction = pumpClearanceLeakVolumetricProduction;
	}

	public Item getTVLeakVolumetricProduction() {
		return TVLeakVolumetricProduction;
	}

	public void setTVLeakVolumetricProduction(Item tVLeakVolumetricProduction) {
		TVLeakVolumetricProduction = tVLeakVolumetricProduction;
	}

	public Item getSVLeakVolumetricProduction() {
		return SVLeakVolumetricProduction;
	}

	public void setSVLeakVolumetricProduction(Item sVLeakVolumetricProduction) {
		SVLeakVolumetricProduction = sVLeakVolumetricProduction;
	}

	public Item getGasInfluenceVolumetricProduction() {
		return GasInfluenceVolumetricProduction;
	}

	public void setGasInfluenceVolumetricProduction(Item gasInfluenceVolumetricProduction) {
		GasInfluenceVolumetricProduction = gasInfluenceVolumetricProduction;
	}

	public Item getAvailablePlungerStrokeWeightProduction() {
		return AvailablePlungerStrokeWeightProduction;
	}

	public void setAvailablePlungerStrokeWeightProduction(Item availablePlungerStrokeWeightProduction) {
		AvailablePlungerStrokeWeightProduction = availablePlungerStrokeWeightProduction;
	}

	public Item getPumpClearanceLeakWeightProduction() {
		return PumpClearanceLeakWeightProduction;
	}

	public void setPumpClearanceLeakWeightProduction(Item pumpClearanceLeakWeightProduction) {
		PumpClearanceLeakWeightProduction = pumpClearanceLeakWeightProduction;
	}

	public Item getTVLeakWeightProduction() {
		return TVLeakWeightProduction;
	}

	public void setTVLeakWeightProduction(Item tVLeakWeightProduction) {
		TVLeakWeightProduction = tVLeakWeightProduction;
	}

	public Item getSVLeakWeightProduction() {
		return SVLeakWeightProduction;
	}

	public void setSVLeakWeightProduction(Item sVLeakWeightProduction) {
		SVLeakWeightProduction = sVLeakWeightProduction;
	}

	public Item getGasInfluenceWeightProduction() {
		return GasInfluenceWeightProduction;
	}

	public void setGasInfluenceWeightProduction(Item gasInfluenceWeightProduction) {
		GasInfluenceWeightProduction = gasInfluenceWeightProduction;
	}

	public Item getPumpEff1() {
		return PumpEff1;
	}

	public void setPumpEff1(Item pumpEff1) {
		PumpEff1 = pumpEff1;
	}

	public Item getRodFlexLength() {
		return RodFlexLength;
	}

	public void setRodFlexLength(Item rodFlexLength) {
		RodFlexLength = rodFlexLength;
	}

	public Item getTubingFlexLength() {
		return TubingFlexLength;
	}

	public void setTubingFlexLength(Item tubingFlexLength) {
		TubingFlexLength = tubingFlexLength;
	}

	public Item getInertiaLength() {
		return InertiaLength;
	}

	public void setInertiaLength(Item inertiaLength) {
		InertiaLength = inertiaLength;
	}

	public Item getPumpEff2() {
		return PumpEff2;
	}

	public void setPumpEff2(Item pumpEff2) {
		PumpEff2 = pumpEff2;
	}

	public Item getPumpEff3() {
		return PumpEff3;
	}

	public void setPumpEff3(Item pumpEff3) {
		PumpEff3 = pumpEff3;
	}

	public Item getPumpEff4() {
		return PumpEff4;
	}

	public void setPumpEff4(Item pumpEff4) {
		PumpEff4 = pumpEff4;
	}

	public Item getPumpIntakeP() {
		return PumpIntakeP;
	}

	public void setPumpIntakeP(Item pumpIntakeP) {
		PumpIntakeP = pumpIntakeP;
	}

	public Item getPumpIntakeT() {
		return PumpIntakeT;
	}

	public void setPumpIntakeT(Item pumpIntakeT) {
		PumpIntakeT = pumpIntakeT;
	}

	public Item getPumpIntakeGOL() {
		return PumpIntakeGOL;
	}

	public void setPumpIntakeGOL(Item pumpIntakeGOL) {
		PumpIntakeGOL = pumpIntakeGOL;
	}

	public Item getPumpIntakeVisl() {
		return PumpIntakeVisl;
	}

	public void setPumpIntakeVisl(Item pumpIntakeVisl) {
		PumpIntakeVisl = pumpIntakeVisl;
	}

	public Item getPumpIntakeBo() {
		return PumpIntakeBo;
	}

	public void setPumpIntakeBo(Item pumpIntakeBo) {
		PumpIntakeBo = pumpIntakeBo;
	}

	public Item getPumpOutletP() {
		return PumpOutletP;
	}

	public void setPumpOutletP(Item pumpOutletP) {
		PumpOutletP = pumpOutletP;
	}

	public Item getPumpOutletT() {
		return PumpOutletT;
	}

	public void setPumpOutletT(Item pumpOutletT) {
		PumpOutletT = pumpOutletT;
	}

	public Item getPumpOutletGOL() {
		return PumpOutletGOL;
	}

	public void setPumpOutletGOL(Item pumpOutletGOL) {
		PumpOutletGOL = pumpOutletGOL;
	}

	public Item getPumpOutletVisl() {
		return PumpOutletVisl;
	}

	public void setPumpOutletVisl(Item pumpOutletVisl) {
		PumpOutletVisl = pumpOutletVisl;
	}

	public Item getPumpOutletBo() {
		return PumpOutletBo;
	}

	public void setPumpOutletBo(Item pumpOutletBo) {
		PumpOutletBo = pumpOutletBo;
	}

	public Item getUpStrokeWattMax() {
		return UpStrokeWattMax;
	}

	public void setUpStrokeWattMax(Item upStrokeWattMax) {
		UpStrokeWattMax = upStrokeWattMax;
	}

	public Item getDownStrokeWattMax() {
		return DownStrokeWattMax;
	}

	public void setDownStrokeWattMax(Item downStrokeWattMax) {
		DownStrokeWattMax = downStrokeWattMax;
	}

	public Item getUpStrokeIMax() {
		return UpStrokeIMax;
	}

	public void setUpStrokeIMax(Item upStrokeIMax) {
		UpStrokeIMax = upStrokeIMax;
	}

	public Item getDownStrokeIMax() {
		return DownStrokeIMax;
	}

	public void setDownStrokeIMax(Item downStrokeIMax) {
		DownStrokeIMax = downStrokeIMax;
	}

	public Item getAvgWatt() {
		return AvgWatt;
	}

	public void setAvgWatt(Item avgWatt) {
		AvgWatt = avgWatt;
	}

	public Item getPolishRodPower() {
		return PolishRodPower;
	}

	public void setPolishRodPower(Item polishRodPower) {
		PolishRodPower = polishRodPower;
	}

	public Item getWaterPower() {
		return WaterPower;
	}

	public void setWaterPower(Item waterPower) {
		WaterPower = waterPower;
	}

	public Item getSignal() {
		return Signal;
	}

	public void setSignal(Item signal) {
		Signal = signal;
	}

	public Item getWattSum() {
		return WattSum;
	}

	public void setWattSum(Item wattSum) {
		WattSum = wattSum;
	}

	public Item getVarSum() {
		return VarSum;
	}

	public void setVarSum(Item varSum) {
		VarSum = varSum;
	}

	public Item getVASum() {
		return VASum;
	}

	public void setVASum(Item vASum) {
		VASum = vASum;
	}

	public Item getPFSum() {
		return PFSum;
	}

	public void setPFSum(Item pFSum) {
		PFSum = pFSum;
	}

	public Item getNoLiquidAvailablePlungerStroke() {
		return NoLiquidAvailablePlungerStroke;
	}

	public void setNoLiquidAvailablePlungerStroke(Item noLiquidAvailablePlungerStroke) {
		NoLiquidAvailablePlungerStroke = noLiquidAvailablePlungerStroke;
	}

	public Item getNoLiquidFullnessCoefficient() {
		return NoLiquidFullnessCoefficient;
	}

	public void setNoLiquidFullnessCoefficient(Item noLiquidFullnessCoefficient) {
		NoLiquidFullnessCoefficient = noLiquidFullnessCoefficient;
	}

	public Item getLevelCorrectValue() {
		return LevelCorrectValue;
	}

	public void setLevelCorrectValue(Item levelCorrectValue) {
		LevelCorrectValue = levelCorrectValue;
	}
	
	public boolean stringLengthManage(){
		if(this.FSResultString.length()>2000){
			this.setFSResultString(FSResultString.substring(0, 1996)+"...");
		}
		if(this.CommRange.length()>2000){
			this.setCommRange(CommRange.substring(0, 1996)+"...");
		}
		if(this.RunRange.length()>2000){
			this.setRunRange(RunRange.substring(0, 1996)+"...");
		}
		return true;
	}
}