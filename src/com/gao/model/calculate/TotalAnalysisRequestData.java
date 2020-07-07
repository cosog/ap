package com.gao.model.calculate;

import java.util.List;

public class TotalAnalysisRequestData {
	
	private String AKString;

    private String WellName;
    
    private String Date;

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
	    private float UpperLoadLine; //理论上载荷线				kN
	    private float LowerLoadLine; //理论下载荷线				kN
	    private float UpperLoadLineOfExact; //考虑沉没压力的上载荷线		kN
	    private float DeltaLoadLine; //理论液柱载荷				kN
	    private float DeltaLoadLineOfExact; //考虑沉没压力的理论液柱载荷	kN
	    private float FMax; //最大载荷					kN
	    private float FMin; //最小载荷					kN
	    private float DeltaF; //载荷差					kN
	    private float Area; //功图面积					kN·m
	    private float PlungerStroke; //柱塞冲程					m
	    private float AvailablePlungerStroke; //柱塞有效冲程
	    private float FullnessCoefficient;
	    private float TheoreticalProduction; //理论排量
	    private float LiquidVolumetricProduction;
	    private float OilVolumetricProduction;
	    private float WaterVolumetricProduction;
	    private float AvailablePlungerStrokeVolumetricProduction; //柱塞有效冲程计算产量		m^3/d
	    private float PumpClearanceLeakVolumetricProduction; //泵间隙漏失量				m^3/d
	    private float TVLeakVolumetricProduction; //游动凡尔漏失量			m^3/d
	    private float SVLeakVolumetricProduction; //固定凡尔漏失量			m^3/d
	    private float GasInfluenceVolumetricProduction; //气影响					m^3/d
	    private float LiquidWeightProduction;
	    private float OilWeightProduction;
	    private float WaterWeightProduction;
	    private float AvailablePlungerStrokeWeightProduction; //柱塞有效冲程计算产量		t/d
	    private float PumpClearanceLeakWeightProduction; //泵间隙漏失量				t/d
	    private float TVLeakWeightProduction; //游动凡尔漏失量			t/d
	    private float SVLeakWeightProduction; //固定凡尔漏失量			t/d
	    private float GasInfluenceWeightProduction; //气影响					t/d
	    private float VolumeWaterCut;
	    private float WeightWaterCut;
	    private float PumpEff;
	    private float PumpEff1; //冲程损失系数				小数
	    private float RodFlexLength; //抽油杆伸长量				m
	    private float TubingFlexLength; //计算油管伸缩值			m
	    private float InertiaLength; //惯性载荷下冲程增量			m
	    private float PumpEff2; //充满系数					小数
	    private float PumpEff3; //间隙漏失系数				小数
	    private float PumpEff4; //液体收缩系数				小数
	    private float PumpBoreDiameter;
	    private float PumpSettingDepth;
	    private float ProducingfluidLevel;
	    private float Submergence;
	    private float PumpIntakeP; //泵入口压力				MPa
	    private float PumpIntakeT; //泵入口温度				℃
	    private float PumpIntakeGOL; //泵入口就地气液比
	    private float PumpIntakeVisl; //泵入口粘度				mPa·s
	    private float PumpIntakeBo; //泵入口原油体积系数
	    private float PumpOutletP; //泵出口压力				MPa
	    private float PumpOutletT; //泵出口温度				℃
	    private float PumpOutletGOL; //泵出口就地气液比
	    private float PumpOutletVisl; //泵出口粘度				mPa·s
	    private float PumpOutletBo; //泵出口原油体积系数
	    private int ETResultCode;
	    private float WattDegreeBalance;
	    private float UpStrokeWattMax; //上冲程功率最大值			kW
	    private float DownStrokeWattMax; //下冲程功率最大值			kW
	    private float IDegreeBalance;
	    private float UpStrokeIMax; //上冲程电流最大值			A
	    private float DownStrokeIMax; //下冲程电流最大值			A
	    private float DeltaRadius;
	    private float SurfaceSystemEfficiency;
	    private float WellDownSystemEfficiency;
	    private float SystemEfficiency;
	    private float PowerConsumptionPerTHM;
	    private float AvgWatt; //平均有功功率        		kW
	    private float PolishRodPower; //光杆功率              	kW
	    private float WaterPower; //水功率             		kW
	    private float IA;
	    private float IB;
	    private float IC;
	    private float VA;
	    private float VB;
	    private float VC;
	    private float RunFrequency;
	    private float RPM;
	    private float Signal; //信号强度
	    private float WattSum; //有功功率					kW
	    private float VarSum; //无功功率					kVar
	    private float VASum; //视在功率					kVA
	    private float PFSum; //功率因数					小数
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

		public float getRPM() {
			return RPM;
		}

		public void setRPM(float rPM) {
			RPM = rPM;
		}

		public float getUpperLoadLine() {
			return UpperLoadLine;
		}

		public void setUpperLoadLine(float upperLoadLine) {
			UpperLoadLine = upperLoadLine;
		}

		public float getLowerLoadLine() {
			return LowerLoadLine;
		}

		public void setLowerLoadLine(float lowerLoadLine) {
			LowerLoadLine = lowerLoadLine;
		}

		public float getUpperLoadLineOfExact() {
			return UpperLoadLineOfExact;
		}

		public void setUpperLoadLineOfExact(float upperLoadLineOfExact) {
			UpperLoadLineOfExact = upperLoadLineOfExact;
		}

		public float getDeltaLoadLine() {
			return DeltaLoadLine;
		}

		public void setDeltaLoadLine(float deltaLoadLine) {
			DeltaLoadLine = deltaLoadLine;
		}

		public float getDeltaLoadLineOfExact() {
			return DeltaLoadLineOfExact;
		}

		public void setDeltaLoadLineOfExact(float deltaLoadLineOfExact) {
			DeltaLoadLineOfExact = deltaLoadLineOfExact;
		}

		public float getFMax() {
			return FMax;
		}

		public void setFMax(float fMax) {
			FMax = fMax;
		}

		public float getFMin() {
			return FMin;
		}

		public void setFMin(float fMin) {
			FMin = fMin;
		}

		public float getDeltaF() {
			return DeltaF;
		}

		public void setDeltaF(float deltaF) {
			DeltaF = deltaF;
		}

		public float getArea() {
			return Area;
		}

		public void setArea(float area) {
			Area = area;
		}

		public float getPlungerStroke() {
			return PlungerStroke;
		}

		public void setPlungerStroke(float plungerStroke) {
			PlungerStroke = plungerStroke;
		}

		public float getAvailablePlungerStroke() {
			return AvailablePlungerStroke;
		}

		public void setAvailablePlungerStroke(float availablePlungerStroke) {
			AvailablePlungerStroke = availablePlungerStroke;
		}

		public float getTheoreticalProduction() {
			return TheoreticalProduction;
		}

		public void setTheoreticalProduction(float theoreticalProduction) {
			TheoreticalProduction = theoreticalProduction;
		}

		public float getAvailablePlungerStrokeVolumetricProduction() {
			return AvailablePlungerStrokeVolumetricProduction;
		}

		public void setAvailablePlungerStrokeVolumetricProduction(float availablePlungerStrokeVolumetricProduction) {
			AvailablePlungerStrokeVolumetricProduction = availablePlungerStrokeVolumetricProduction;
		}

		public float getPumpClearanceLeakVolumetricProduction() {
			return PumpClearanceLeakVolumetricProduction;
		}

		public void setPumpClearanceLeakVolumetricProduction(float pumpClearanceLeakVolumetricProduction) {
			PumpClearanceLeakVolumetricProduction = pumpClearanceLeakVolumetricProduction;
		}

		public float getTVLeakVolumetricProduction() {
			return TVLeakVolumetricProduction;
		}

		public void setTVLeakVolumetricProduction(float tVLeakVolumetricProduction) {
			TVLeakVolumetricProduction = tVLeakVolumetricProduction;
		}

		public float getSVLeakVolumetricProduction() {
			return SVLeakVolumetricProduction;
		}

		public void setSVLeakVolumetricProduction(float sVLeakVolumetricProduction) {
			SVLeakVolumetricProduction = sVLeakVolumetricProduction;
		}

		public float getGasInfluenceVolumetricProduction() {
			return GasInfluenceVolumetricProduction;
		}

		public void setGasInfluenceVolumetricProduction(float gasInfluenceVolumetricProduction) {
			GasInfluenceVolumetricProduction = gasInfluenceVolumetricProduction;
		}

		public float getAvailablePlungerStrokeWeightProduction() {
			return AvailablePlungerStrokeWeightProduction;
		}

		public void setAvailablePlungerStrokeWeightProduction(float availablePlungerStrokeWeightProduction) {
			AvailablePlungerStrokeWeightProduction = availablePlungerStrokeWeightProduction;
		}

		public float getPumpClearanceLeakWeightProduction() {
			return PumpClearanceLeakWeightProduction;
		}

		public void setPumpClearanceLeakWeightProduction(float pumpClearanceLeakWeightProduction) {
			PumpClearanceLeakWeightProduction = pumpClearanceLeakWeightProduction;
		}

		public float getTVLeakWeightProduction() {
			return TVLeakWeightProduction;
		}

		public void setTVLeakWeightProduction(float tVLeakWeightProduction) {
			TVLeakWeightProduction = tVLeakWeightProduction;
		}

		public float getSVLeakWeightProduction() {
			return SVLeakWeightProduction;
		}

		public void setSVLeakWeightProduction(float sVLeakWeightProduction) {
			SVLeakWeightProduction = sVLeakWeightProduction;
		}

		public float getGasInfluenceWeightProduction() {
			return GasInfluenceWeightProduction;
		}

		public void setGasInfluenceWeightProduction(float gasInfluenceWeightProduction) {
			GasInfluenceWeightProduction = gasInfluenceWeightProduction;
		}

		public float getPumpEff1() {
			return PumpEff1;
		}

		public void setPumpEff1(float pumpEff1) {
			PumpEff1 = pumpEff1;
		}

		public float getRodFlexLength() {
			return RodFlexLength;
		}

		public void setRodFlexLength(float rodFlexLength) {
			RodFlexLength = rodFlexLength;
		}

		public float getTubingFlexLength() {
			return TubingFlexLength;
		}

		public void setTubingFlexLength(float tubingFlexLength) {
			TubingFlexLength = tubingFlexLength;
		}

		public float getInertiaLength() {
			return InertiaLength;
		}

		public void setInertiaLength(float inertiaLength) {
			InertiaLength = inertiaLength;
		}

		public float getPumpEff2() {
			return PumpEff2;
		}

		public void setPumpEff2(float pumpEff2) {
			PumpEff2 = pumpEff2;
		}

		public float getPumpEff3() {
			return PumpEff3;
		}

		public void setPumpEff3(float pumpEff3) {
			PumpEff3 = pumpEff3;
		}

		public float getPumpEff4() {
			return PumpEff4;
		}

		public void setPumpEff4(float pumpEff4) {
			PumpEff4 = pumpEff4;
		}

		public float getPumpIntakeP() {
			return PumpIntakeP;
		}

		public void setPumpIntakeP(float pumpIntakeP) {
			PumpIntakeP = pumpIntakeP;
		}

		public float getPumpIntakeT() {
			return PumpIntakeT;
		}

		public void setPumpIntakeT(float pumpIntakeT) {
			PumpIntakeT = pumpIntakeT;
		}

		public float getPumpIntakeGOL() {
			return PumpIntakeGOL;
		}

		public void setPumpIntakeGOL(float pumpIntakeGOL) {
			PumpIntakeGOL = pumpIntakeGOL;
		}

		public float getPumpIntakeVisl() {
			return PumpIntakeVisl;
		}

		public void setPumpIntakeVisl(float pumpIntakeVisl) {
			PumpIntakeVisl = pumpIntakeVisl;
		}

		public float getPumpIntakeBo() {
			return PumpIntakeBo;
		}

		public void setPumpIntakeBo(float pumpIntakeBo) {
			PumpIntakeBo = pumpIntakeBo;
		}

		public float getPumpOutletP() {
			return PumpOutletP;
		}

		public void setPumpOutletP(float pumpOutletP) {
			PumpOutletP = pumpOutletP;
		}

		public float getPumpOutletT() {
			return PumpOutletT;
		}

		public void setPumpOutletT(float pumpOutletT) {
			PumpOutletT = pumpOutletT;
		}

		public float getPumpOutletGOL() {
			return PumpOutletGOL;
		}

		public void setPumpOutletGOL(float pumpOutletGOL) {
			PumpOutletGOL = pumpOutletGOL;
		}

		public float getPumpOutletVisl() {
			return PumpOutletVisl;
		}

		public void setPumpOutletVisl(float pumpOutletVisl) {
			PumpOutletVisl = pumpOutletVisl;
		}

		public float getPumpOutletBo() {
			return PumpOutletBo;
		}

		public void setPumpOutletBo(float pumpOutletBo) {
			PumpOutletBo = pumpOutletBo;
		}

		public float getUpStrokeWattMax() {
			return UpStrokeWattMax;
		}

		public void setUpStrokeWattMax(float upStrokeWattMax) {
			UpStrokeWattMax = upStrokeWattMax;
		}

		public float getDownStrokeWattMax() {
			return DownStrokeWattMax;
		}

		public void setDownStrokeWattMax(float downStrokeWattMax) {
			DownStrokeWattMax = downStrokeWattMax;
		}

		public float getUpStrokeIMax() {
			return UpStrokeIMax;
		}

		public void setUpStrokeIMax(float upStrokeIMax) {
			UpStrokeIMax = upStrokeIMax;
		}

		public float getDownStrokeIMax() {
			return DownStrokeIMax;
		}

		public void setDownStrokeIMax(float downStrokeIMax) {
			DownStrokeIMax = downStrokeIMax;
		}

		public float getAvgWatt() {
			return AvgWatt;
		}

		public void setAvgWatt(float avgWatt) {
			AvgWatt = avgWatt;
		}

		public float getPolishRodPower() {
			return PolishRodPower;
		}

		public void setPolishRodPower(float polishRodPower) {
			PolishRodPower = polishRodPower;
		}

		public float getWaterPower() {
			return WaterPower;
		}

		public void setWaterPower(float waterPower) {
			WaterPower = waterPower;
		}

		public float getRunFrequency() {
			return RunFrequency;
		}

		public void setRunFrequency(float runFrequency) {
			RunFrequency = runFrequency;
		}

		public float getSignal() {
			return Signal;
		}

		public void setSignal(float signal) {
			Signal = signal;
		}

		public float getWattSum() {
			return WattSum;
		}

		public void setWattSum(float wattSum) {
			WattSum = wattSum;
		}

		public float getVarSum() {
			return VarSum;
		}

		public void setVarSum(float varSum) {
			VarSum = varSum;
		}

		public float getVASum() {
			return VASum;
		}

		public void setVASum(float vASum) {
			VASum = vASum;
		}

		public float getPFSum() {
			return PFSum;
		}

		public void setPFSum(float pFSum) {
			PFSum = pFSum;
		}
	}

	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
}
