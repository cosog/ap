package com.gao.model;

public class AcquisitionUnitData {

	private String AcquisitionUnitName;

    private String AcquisitionUnitCode;

    private int RunStatus=0;
    
    private int BalaceControlStatus=0;
    
    private int BalanceControlMode=0;
    
    private int BalanceCalculateMode=0;
    
    private int BalanceAwayTimePerBeat=0;
    
    private int BalanceCloseTimePerBeat=0;
    
    private int BalanceStrokeCount=0;
    
    private int BalanceOperationUpLimit=0;
    
    private int BalanceOperationDownLimit=0;
    
    private int BalanceAwayTime=0;
    
    private int BalanceCloseTime=0;

    private int RunControl=0;

    private int CurrentA=0;

    private int CurrentB=0;

    private int CurrentC=0;

    private int VoltageA=0;

    private int VoltageB=0;

    private int VoltageC=0;

    private int ActivePowerConsumption=0;

    private int ReactivePowerConsumption=0;

    private int ActivePower=0;

    private int ReactivePower=0;

    private int ReversePower=0;

    private int PowerFactor=0;

    private int TubingPressure=0;

    private int CasingPressure=0;

    private int BackPressure=0;

    private int WellHeadFluidTemperature=0;

    private int ProducingfluidLevel=0;

    private int WaterCut=0;

    private int SetFrequency=0;

    private int RunFrequency=0;

    private int RPM=0;

    private int Torque=0;

    private int FSDiagramAcquisitionInterval=0;

    private int FSDiagramSetPointCount=0;

    private int FSDiagramPointCount=0;

    private int AcquisitionTime=0;

    private int SPM=0;

    private int Stroke=0;

    private int SDiagram=0;

    private int FDiagram=0;

    private int ADiagram=0;

    private int PDiagram=0;
    
    public void init(){
    	this.RunStatus=0;

        this.RunControl=0;

        this.CurrentA=0;

        this.CurrentB=0;

        this.CurrentC=0;

        this.VoltageA=0;

        this.VoltageB=0;

        this.VoltageC=0;

        this.ActivePowerConsumption=0;

        this.ReactivePowerConsumption=0;

        this.ActivePower=0;

        this.ReactivePower=0;

        this.ReversePower=0;

        this.PowerFactor=0;

        this.TubingPressure=0;

        this.CasingPressure=0;

        this.BackPressure=0;

        this.WellHeadFluidTemperature=0;

        this.ProducingfluidLevel=0;

        this.WaterCut=0;

        this.SetFrequency=0;

        this.RunFrequency=0;

        this.RPM=0;

        this.Torque=0;

        this.FSDiagramAcquisitionInterval=0;

        this.FSDiagramSetPointCount=0;

        this.FSDiagramPointCount=0;

        this.AcquisitionTime=0;

        this.SPM=0;

        this.Stroke=0;

        this.SDiagram=0;

        this.FDiagram=0;

        this.ADiagram=0;

        this.PDiagram=0;
    }

    public void setAcquisitionUnitName(String AcquisitionUnitName){
        this.AcquisitionUnitName = AcquisitionUnitName;
    }
    public String getAcquisitionUnitName(){
        return this.AcquisitionUnitName;
    }
    public void setAcquisitionUnitCode(String AcquisitionUnitCode){
        this.AcquisitionUnitCode = AcquisitionUnitCode;
    }
    public String getAcquisitionUnitCode(){
        return this.AcquisitionUnitCode;
    }
    public void setRunStatus(int RunStatus){
        this.RunStatus = RunStatus;
    }
    public int getRunStatus(){
        return this.RunStatus;
    }
    public void setRunControl(int RunControl){
        this.RunControl = RunControl;
    }
    public int getRunControl(){
        return this.RunControl;
    }
    public void setCurrentA(int CurrentA){
        this.CurrentA = CurrentA;
    }
    public int getCurrentA(){
        return this.CurrentA;
    }
    public void setCurrentB(int CurrentB){
        this.CurrentB = CurrentB;
    }
    public int getCurrentB(){
        return this.CurrentB;
    }
    public void setCurrentC(int CurrentC){
        this.CurrentC = CurrentC;
    }
    public int getCurrentC(){
        return this.CurrentC;
    }
    public void setVoltageA(int VoltageA){
        this.VoltageA = VoltageA;
    }
    public int getVoltageA(){
        return this.VoltageA;
    }
    public void setVoltageB(int VoltageB){
        this.VoltageB = VoltageB;
    }
    public int getVoltageB(){
        return this.VoltageB;
    }
    public void setVoltageC(int VoltageC){
        this.VoltageC = VoltageC;
    }
    public int getVoltageC(){
        return this.VoltageC;
    }
    public void setActivePowerConsumption(int ActivePowerConsumption){
        this.ActivePowerConsumption = ActivePowerConsumption;
    }
    public int getActivePowerConsumption(){
        return this.ActivePowerConsumption;
    }
    public void setReactivePowerConsumption(int ReactivePowerConsumption){
        this.ReactivePowerConsumption = ReactivePowerConsumption;
    }
    public int getReactivePowerConsumption(){
        return this.ReactivePowerConsumption;
    }
    public void setActivePower(int ActivePower){
        this.ActivePower = ActivePower;
    }
    public int getActivePower(){
        return this.ActivePower;
    }
    public void setReactivePower(int ReactivePower){
        this.ReactivePower = ReactivePower;
    }
    public int getReactivePower(){
        return this.ReactivePower;
    }
    public void setReversePower(int ReversePower){
        this.ReversePower = ReversePower;
    }
    public int getReversePower(){
        return this.ReversePower;
    }
    public void setPowerFactor(int PowerFactor){
        this.PowerFactor = PowerFactor;
    }
    public int getPowerFactor(){
        return this.PowerFactor;
    }
    public void setTubingPressure(int TubingPressure){
        this.TubingPressure = TubingPressure;
    }
    public int getTubingPressure(){
        return this.TubingPressure;
    }
    public void setCasingPressure(int CasingPressure){
        this.CasingPressure = CasingPressure;
    }
    public int getCasingPressure(){
        return this.CasingPressure;
    }
    public void setBackPressure(int BackPressure){
        this.BackPressure = BackPressure;
    }
    public int getBackPressure(){
        return this.BackPressure;
    }
    public void setWellHeadFluidTemperature(int WellHeadFluidTemperature){
        this.WellHeadFluidTemperature = WellHeadFluidTemperature;
    }
    public int getWellHeadFluidTemperature(){
        return this.WellHeadFluidTemperature;
    }
    public void setProducingfluidLevel(int ProducingfluidLevel){
        this.ProducingfluidLevel = ProducingfluidLevel;
    }
    public int getProducingfluidLevel(){
        return this.ProducingfluidLevel;
    }
    public void setWaterCut(int WaterCut){
        this.WaterCut = WaterCut;
    }
    public int getWaterCut(){
        return this.WaterCut;
    }
    public void setSetFrequency(int SetFrequency){
        this.SetFrequency = SetFrequency;
    }
    public int getSetFrequency(){
        return this.SetFrequency;
    }
    public void setRunFrequency(int RunFrequency){
        this.RunFrequency = RunFrequency;
    }
    public int getRunFrequency(){
        return this.RunFrequency;
    }
    public void setRPM(int RPM){
        this.RPM = RPM;
    }
    public int getRPM(){
        return this.RPM;
    }
    public void setTorque(int Torque){
        this.Torque = Torque;
    }
    public int getTorque(){
        return this.Torque;
    }
    public void setFSDiagramAcquisitionInterval(int FSDiagramAcquisitionInterval){
        this.FSDiagramAcquisitionInterval = FSDiagramAcquisitionInterval;
    }
    public int getFSDiagramAcquisitionInterval(){
        return this.FSDiagramAcquisitionInterval;
    }
    public void setFSDiagramSetPointCount(int FSDiagramSetPointCount){
        this.FSDiagramSetPointCount = FSDiagramSetPointCount;
    }
    public int getFSDiagramSetPointCount(){
        return this.FSDiagramSetPointCount;
    }
    public void setFSDiagramPointCount(int FSDiagramPointCount){
        this.FSDiagramPointCount = FSDiagramPointCount;
    }
    public int getFSDiagramPointCount(){
        return this.FSDiagramPointCount;
    }
    public void setAcquisitionTime(int AcquisitionTime){
        this.AcquisitionTime = AcquisitionTime;
    }
    public int getAcquisitionTime(){
        return this.AcquisitionTime;
    }
    public void setSPM(int SPM){
        this.SPM = SPM;
    }
    public int getSPM(){
        return this.SPM;
    }
    public void setStroke(int Stroke){
        this.Stroke = Stroke;
    }
    public int getStroke(){
        return this.Stroke;
    }
    public void setSDiagram(int SDiagram){
        this.SDiagram = SDiagram;
    }
    public int getSDiagram(){
        return this.SDiagram;
    }
    public void setFDiagram(int FDiagram){
        this.FDiagram = FDiagram;
    }
    public int getFDiagram(){
        return this.FDiagram;
    }
    public void setADiagram(int ADiagram){
        this.ADiagram = ADiagram;
    }
    public int getADiagram(){
        return this.ADiagram;
    }
    public void setPDiagram(int PDiagram){
        this.PDiagram = PDiagram;
    }
    public int getPDiagram(){
        return this.PDiagram;
    }

	public int getBalaceControlStatus() {
		return BalaceControlStatus;
	}

	public void setBalaceControlStatus(int balaceControlStatus) {
		BalaceControlStatus = balaceControlStatus;
	}

	public int getBalanceControlMode() {
		return BalanceControlMode;
	}

	public void setBalanceControlMode(int balanceControlMode) {
		BalanceControlMode = balanceControlMode;
	}

	public int getBalanceCalculateMode() {
		return BalanceCalculateMode;
	}

	public void setBalanceCalculateMode(int balanceCalculateMode) {
		BalanceCalculateMode = balanceCalculateMode;
	}

	public int getBalanceAwayTime() {
		return BalanceAwayTime;
	}

	public void setBalanceAwayTime(int balanceAwayTime) {
		BalanceAwayTime = balanceAwayTime;
	}

	public int getBalanceCloseTime() {
		return BalanceCloseTime;
	}

	public void setBalanceCloseTime(int balanceCloseTime) {
		BalanceCloseTime = balanceCloseTime;
	}

	public int getBalanceStrokeCount() {
		return BalanceStrokeCount;
	}

	public void setBalanceStrokeCount(int balanceStrokeCount) {
		BalanceStrokeCount = balanceStrokeCount;
	}

	public int getBalanceOperationUpLimit() {
		return BalanceOperationUpLimit;
	}

	public void setBalanceOperationUpLimit(int balanceOperationUpLimit) {
		BalanceOperationUpLimit = balanceOperationUpLimit;
	}

	public int getBalanceOperationDownLimit() {
		return BalanceOperationDownLimit;
	}

	public void setBalanceOperationDownLimit(int balanceOperationDownLimit) {
		BalanceOperationDownLimit = balanceOperationDownLimit;
	}

	public int getBalanceAwayTimePerBeat() {
		return BalanceAwayTimePerBeat;
	}

	public void setBalanceAwayTimePerBeat(int balanceAwayTimePerBeat) {
		BalanceAwayTimePerBeat = balanceAwayTimePerBeat;
	}

	public int getBalanceCloseTimePerBeat() {
		return BalanceCloseTimePerBeat;
	}

	public void setBalanceCloseTimePerBeat(int balanceCloseTimePerBeat) {
		BalanceCloseTimePerBeat = balanceCloseTimePerBeat;
	}
}
