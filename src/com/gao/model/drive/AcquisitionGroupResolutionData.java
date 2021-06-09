package com.gao.model.drive;

import java.util.List;

public class AcquisitionGroupResolutionData {

	public String WellName;
	
	public int wellId;
	
	public int LiftingType;
	
	public int ProdDataId;
	
	public String AcqTime;
	
	public int RunStatus;
	
	public float IA;

    public float IB;

    public float IC;

    public float VA;

    public float VB;

    public float VC;

    public float KWattH;

    public float KVarH;

    public float Watt3;

    public float Var3;

    public float ReversePower;

    public float PF3;
    
    public float TubingPressure;

    public float CasingPressure;

    public float BackPressure;

    public float WellHeadFluidTemperature;
    
    public float ProducingfluidLevel;

    public float WaterCut;
    
    public float SetFrequency;

    public float RunFrequency;
    
    public float RPM;

    public float Torque;

    public String FESDiagramAcqTime;
    
    public int FESDiagramAcquisitionInterval;
    
    public int FESDiagramSetPointCount;
    
    public int FESDiagramPointCount;

    public float SPM;

    public float Stroke;
    
    public float HaveBalanceData=0;
    
    public float UpStrokeIMax;
    
    public float DownStrokeIMax;
    
    public float IDegreeBalance;
    
    public float UpStrokeWattMax;
    
    public float DownStrokeWattMax;
    
    public float WattDegreeBalance;

    public List<Float> SDiagram;

    public List<Float> FDiagram;

    public List<Float> IDiagram;

    public List<Float> WattDiagram;

	public String getWellName() {
		return WellName;
	}

	public void setWellName(String wellName) {
		WellName = wellName;
	}

	public int getWellId() {
		return wellId;
	}

	public void setWellId(int wellId) {
		this.wellId = wellId;
	}

	public int getLiftingType() {
		return LiftingType;
	}

	public void setLiftingType(int liftingType) {
		LiftingType = liftingType;
	}

	public int getProdDataId() {
		return ProdDataId;
	}

	public void setProdDataId(int prodDataId) {
		ProdDataId = prodDataId;
	}

	public String getAcqTime() {
		return AcqTime;
	}

	public void setAcqTime(String acqTime) {
		AcqTime = acqTime;
	}

	public int getRunStatus() {
		return RunStatus;
	}

	public void setRunStatus(int runStatus) {
		RunStatus = runStatus;
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

	public float getKWattH() {
		return KWattH;
	}

	public void setKWattH(float kWattH) {
		KWattH = kWattH;
	}

	public float getKVarH() {
		return KVarH;
	}

	public void setKVarH(float kVarH) {
		KVarH = kVarH;
	}

	public float getWatt3() {
		return Watt3;
	}

	public void setWatt3(float watt3) {
		Watt3 = watt3;
	}

	public float getVar3() {
		return Var3;
	}

	public void setVar3(float var3) {
		Var3 = var3;
	}

	public float getReversePower() {
		return ReversePower;
	}

	public void setReversePower(float reversePower) {
		ReversePower = reversePower;
	}

	public float getPF3() {
		return PF3;
	}

	public void setPF3(float pF3) {
		PF3 = pF3;
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

	public float getBackPressure() {
		return BackPressure;
	}

	public void setBackPressure(float backPressure) {
		BackPressure = backPressure;
	}

	public float getWellHeadFluidTemperature() {
		return WellHeadFluidTemperature;
	}

	public void setWellHeadFluidTemperature(float wellHeadFluidTemperature) {
		WellHeadFluidTemperature = wellHeadFluidTemperature;
	}

	public float getProducingfluidLevel() {
		return ProducingfluidLevel;
	}

	public void setProducingfluidLevel(float producingfluidLevel) {
		ProducingfluidLevel = producingfluidLevel;
	}

	public float getWaterCut() {
		return WaterCut;
	}

	public void setWaterCut(float waterCut) {
		WaterCut = waterCut;
	}

	public float getSetFrequency() {
		return SetFrequency;
	}

	public void setSetFrequency(float setFrequency) {
		SetFrequency = setFrequency;
	}

	public float getRunFrequency() {
		return RunFrequency;
	}

	public void setRunFrequency(float runFrequency) {
		RunFrequency = runFrequency;
	}

	public float getRPM() {
		return RPM;
	}

	public void setRPM(float rPM) {
		RPM = rPM;
	}

	public float getTorque() {
		return Torque;
	}

	public void setTorque(float torque) {
		Torque = torque;
	}

	public float getSPM() {
		return SPM;
	}

	public void setSPM(float sPM) {
		SPM = sPM;
	}

	public float getStroke() {
		return Stroke;
	}

	public void setStroke(float stroke) {
		Stroke = stroke;
	}

	public float getHaveBalanceData() {
		return HaveBalanceData;
	}

	public void setHaveBalanceData(float haveBalanceData) {
		HaveBalanceData = haveBalanceData;
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

	public float getIDegreeBalance() {
		return IDegreeBalance;
	}

	public void setIDegreeBalance(float iDegreeBalance) {
		IDegreeBalance = iDegreeBalance;
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

	public float getWattDegreeBalance() {
		return WattDegreeBalance;
	}

	public void setWattDegreeBalance(float wattDegreeBalance) {
		WattDegreeBalance = wattDegreeBalance;
	}

	public List<Float> getSDiagram() {
		return SDiagram;
	}

	public void setSDiagram(List<Float> sDiagram) {
		SDiagram = sDiagram;
	}

	public List<Float> getFDiagram() {
		return FDiagram;
	}

	public void setFDiagram(List<Float> fDiagram) {
		FDiagram = fDiagram;
	}

	public List<Float> getIDiagram() {
		return IDiagram;
	}

	public void setIDiagram(List<Float> iDiagram) {
		IDiagram = iDiagram;
	}

	public List<Float> getWattDiagram() {
		return WattDiagram;
	}

	public void setWattDiagram(List<Float> wattDiagram) {
		WattDiagram = wattDiagram;
	}

	public String getFESDiagramAcqTime() {
		return FESDiagramAcqTime;
	}

	public void setFESDiagramAcqTime(String fESDiagramAcqTime) {
		FESDiagramAcqTime = fESDiagramAcqTime;
	}

	public int getFESDiagramAcquisitionInterval() {
		return FESDiagramAcquisitionInterval;
	}

	public void setFESDiagramAcquisitionInterval(int fESDiagramAcquisitionInterval) {
		FESDiagramAcquisitionInterval = fESDiagramAcquisitionInterval;
	}

	public int getFESDiagramSetPointCount() {
		return FESDiagramSetPointCount;
	}

	public void setFESDiagramSetPointCount(int fESDiagramSetPointCount) {
		FESDiagramSetPointCount = fESDiagramSetPointCount;
	}

	public int getFESDiagramPointCount() {
		return FESDiagramPointCount;
	}

	public void setFESDiagramPointCount(int fESDiagramPointCount) {
		FESDiagramPointCount = fESDiagramPointCount;
	}
}
