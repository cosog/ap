package com.cosog.model.calculate;

import java.util.List;

public class FSDiagramConstructionRequestData {

	private String AKString;

    private String WellName;

    private float CrankDIInitAngle;

    private int CrankDIStartTimestamp;

    private int CrankDIEndTimestamp;

    private List<Integer> MotorDITimestamp;

    private List<Float> Watt;

    private List<Float> I;

    private PumpingUnit PumpingUnit;

    private int InterpolationCNT;

    private float SurfaceSystemEfficiency;

    private float PositiveXWatt;

    private float NegativeXWatt;

    private int WattTimes;

    private int ITimes;

    private int FSDiagramTimes;

    private int FSDiagramLeftTimes;

    private int FSDiagramRightTimes;

    private float LeftPercent;

    private float RightPercent;
    
    private int boardDataSource;
    
    private int PRTFSrc;
	
	public static class EveryBalance
	{
	    private float Position;

	    private float Weight;

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
	
	public static class Balance
	{
	    private List<EveryBalance> EveryBalance;

	    public void setEveryBalance(List<EveryBalance> EveryBalance){
	        this.EveryBalance = EveryBalance;
	    }
	    public List<EveryBalance> getEveryBalance(){
	        return this.EveryBalance;
	    }
	}
	
	public static class PRTF
	{
	    private List<Integer> CrankAngle;

	    private List<Float> PR;

	    private List<Float> TF;

	    public void setCrankAngle(List<Integer> CrankAngle){
	        this.CrankAngle = CrankAngle;
	    }
	    public List<Integer> getCrankAngle(){
	        return this.CrankAngle;
	    }
	    public void setPR(List<Float> PR){
	        this.PR = PR;
	    }
	    public List<Float> getPR(){
	        return this.PR;
	    }
	    public void setTF(List<Float> TF){
	        this.TF = TF;
	    }
	    public List<Float> getTF(){
	        return this.TF;
	    }
	}
	
	public static class PumpingUnit
	{
	    private String Manufacturer;

	    private String Model;

	    private float Stroke;

	    private String CrankRotationDirection;

	    private float OffsetAngleOfCrank;

	    private float CrankGravityRadius;

	    private float SingleCrankWeight;

	    private float SingleCrankPinWeight;

	    private float StructuralUnbalance;

	    private Balance Balance;

	    private PRTF PRTF;

	    private int CrankAngleInterval;

	    public void setManufacturer(String Manufacturer){
	        this.Manufacturer = Manufacturer;
	    }
	    public String getManufacturer(){
	        return this.Manufacturer;
	    }
	    public void setModel(String Model){
	        this.Model = Model;
	    }
	    public String getModel(){
	        return this.Model;
	    }
	    public void setStroke(float Stroke){
	        this.Stroke = Stroke;
	    }
	    public float getStroke(){
	        return this.Stroke;
	    }
	    public void setCrankRotationDirection(String CrankRotationDirection){
	        this.CrankRotationDirection = CrankRotationDirection;
	    }
	    public String getCrankRotationDirection(){
	        return this.CrankRotationDirection;
	    }
	    public void setOffsetAngleOfCrank(float OffsetAngleOfCrank){
	        this.OffsetAngleOfCrank = OffsetAngleOfCrank;
	    }
	    public float getOffsetAngleOfCrank(){
	        return this.OffsetAngleOfCrank;
	    }
	    public void setCrankGravityRadius(float CrankGravityRadius){
	        this.CrankGravityRadius = CrankGravityRadius;
	    }
	    public float getCrankGravityRadius(){
	        return this.CrankGravityRadius;
	    }
	    public void setSingleCrankWeight(float SingleCrankWeight){
	        this.SingleCrankWeight = SingleCrankWeight;
	    }
	    public float getSingleCrankWeight(){
	        return this.SingleCrankWeight;
	    }
	    public void setSingleCrankPinWeight(float SingleCrankPinWeight){
	        this.SingleCrankPinWeight = SingleCrankPinWeight;
	    }
	    public float getSingleCrankPinWeight(){
	        return this.SingleCrankPinWeight;
	    }
	    public void setStructuralUnbalance(float StructuralUnbalance){
	        this.StructuralUnbalance = StructuralUnbalance;
	    }
	    public float getStructuralUnbalance(){
	        return this.StructuralUnbalance;
	    }
	    public void setBalance(Balance Balance){
	        this.Balance = Balance;
	    }
	    public Balance getBalance(){
	        return this.Balance;
	    }
	    public void setPRTF(PRTF PRTF){
	        this.PRTF = PRTF;
	    }
	    public PRTF getPRTF(){
	        return this.PRTF;
	    }
	    public void setCrankAngleInterval(int CrankAngleInterval){
	        this.CrankAngleInterval = CrankAngleInterval;
	    }
	    public int getCrankAngleInterval(){
	        return this.CrankAngleInterval;
	    }
	}

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

	public float getCrankDIInitAngle() {
		return CrankDIInitAngle;
	}

	public void setCrankDIInitAngle(float crankDIInitAngle) {
		CrankDIInitAngle = crankDIInitAngle;
	}

	public int getCrankDIStartTimestamp() {
		return CrankDIStartTimestamp;
	}

	public void setCrankDIStartTimestamp(int crankDIStartTimestamp) {
		CrankDIStartTimestamp = crankDIStartTimestamp;
	}

	public int getCrankDIEndTimestamp() {
		return CrankDIEndTimestamp;
	}

	public void setCrankDIEndTimestamp(int crankDIEndTimestamp) {
		CrankDIEndTimestamp = crankDIEndTimestamp;
	}

	public List<Integer> getMotorDITimestamp() {
		return MotorDITimestamp;
	}

	public void setMotorDITimestamp(List<Integer> motorDITimestamp) {
		MotorDITimestamp = motorDITimestamp;
	}

	public List<Float> getWatt() {
		return Watt;
	}

	public void setWatt(List<Float> watt) {
		Watt = watt;
	}

	public List<Float> getI() {
		return I;
	}

	public void setI(List<Float> i) {
		I = i;
	}

	public PumpingUnit getPumpingUnit() {
		return PumpingUnit;
	}

	public void setPumpingUnit(PumpingUnit pumpingUnit) {
		PumpingUnit = pumpingUnit;
	}

	public int getInterpolationCNT() {
		return InterpolationCNT;
	}

	public void setInterpolationCNT(int interpolationCNT) {
		InterpolationCNT = interpolationCNT;
	}

	public float getSurfaceSystemEfficiency() {
		return SurfaceSystemEfficiency;
	}

	public void setSurfaceSystemEfficiency(float surfaceSystemEfficiency) {
		SurfaceSystemEfficiency = surfaceSystemEfficiency;
	}

	public float getPositiveXWatt() {
		return PositiveXWatt;
	}

	public void setPositiveXWatt(float positiveXWatt) {
		PositiveXWatt = positiveXWatt;
	}

	public float getNegativeXWatt() {
		return NegativeXWatt;
	}

	public void setNegativeXWatt(float negativeXWatt) {
		NegativeXWatt = negativeXWatt;
	}

	public int getWattTimes() {
		return WattTimes;
	}

	public void setWattTimes(int wattTimes) {
		WattTimes = wattTimes;
	}

	public int getITimes() {
		return ITimes;
	}

	public void setITimes(int iTimes) {
		ITimes = iTimes;
	}

	public int getFSDiagramTimes() {
		return FSDiagramTimes;
	}

	public void setFSDiagramTimes(int fSDiagramTimes) {
		FSDiagramTimes = fSDiagramTimes;
	}

	public int getFSDiagramLeftTimes() {
		return FSDiagramLeftTimes;
	}

	public void setFSDiagramLeftTimes(int fSDiagramLeftTimes) {
		FSDiagramLeftTimes = fSDiagramLeftTimes;
	}

	public int getFSDiagramRightTimes() {
		return FSDiagramRightTimes;
	}

	public void setFSDiagramRightTimes(int fSDiagramRightTimes) {
		FSDiagramRightTimes = fSDiagramRightTimes;
	}

	public float getLeftPercent() {
		return LeftPercent;
	}

	public void setLeftPercent(float leftPercent) {
		LeftPercent = leftPercent;
	}

	public float getRightPercent() {
		return RightPercent;
	}

	public void setRightPercent(float rightPercent) {
		RightPercent = rightPercent;
	}

	public int getBoardDataSource() {
		return boardDataSource;
	}

	public void setBoardDataSource(int boardDataSource) {
		this.boardDataSource = boardDataSource;
	}

	public int getPRTFSrc() {
		return PRTFSrc;
	}

	public void setPRTFSrc(int pRTFSrc) {
		PRTFSrc = pRTFSrc;
	}
	
	
}
