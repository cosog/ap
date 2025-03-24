package com.cosog.model.calculate;

import java.util.List;

public class FSDiagramConstructionRequestData {

	private String AKString;

    private String WellName;

    private int CrankDIInitAngle;

    private int CrankDIStartTimestamp;

    private int CrankDIEndTimestamp;

    private List<Integer> MotorDITimestamp;

    private List<Float> Watt;

    private List<Float> I;

    private PumpingUnit PumpingUnit;

    private int InterpolationCNT;

    private int SurfaceSystemEfficiency;

    private int PositiveXWatt;

    private int NegativeXWatt;

    private int WattTimes;

    private int ITimes;

    private int FSDiagramTimes;

    private int FSDiagramLeftTimes;

    private int FSDiagramRightTimes;

    private double LeftPercent;

    private double RightPercent;

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
    public void setCrankDIInitAngle(int CrankDIInitAngle){
        this.CrankDIInitAngle = CrankDIInitAngle;
    }
    public int getCrankDIInitAngle(){
        return this.CrankDIInitAngle;
    }
    public void setCrankDIStartTimestamp(int CrankDIStartTimestamp){
        this.CrankDIStartTimestamp = CrankDIStartTimestamp;
    }
    public int getCrankDIStartTimestamp(){
        return this.CrankDIStartTimestamp;
    }
    public void setCrankDIEndTimestamp(int CrankDIEndTimestamp){
        this.CrankDIEndTimestamp = CrankDIEndTimestamp;
    }
    public int getCrankDIEndTimestamp(){
        return this.CrankDIEndTimestamp;
    }
    public void setMotorDITimestamp(List<Integer> MotorDITimestamp){
        this.MotorDITimestamp = MotorDITimestamp;
    }
    public List<Integer> getMotorDITimestamp(){
        return this.MotorDITimestamp;
    }
    public void setWatt(List<Float> Watt){
        this.Watt = Watt;
    }
    public List<Float> getWatt(){
        return this.Watt;
    }
    public void setI(List<Float> I){
        this.I = I;
    }
    public List<Float> getI(){
        return this.I;
    }
    public void setPumpingUnit(PumpingUnit PumpingUnit){
        this.PumpingUnit = PumpingUnit;
    }
    public PumpingUnit getPumpingUnit(){
        return this.PumpingUnit;
    }
    public void setInterpolationCNT(int InterpolationCNT){
        this.InterpolationCNT = InterpolationCNT;
    }
    public int getInterpolationCNT(){
        return this.InterpolationCNT;
    }
    public void setSurfaceSystemEfficiency(int SurfaceSystemEfficiency){
        this.SurfaceSystemEfficiency = SurfaceSystemEfficiency;
    }
    public int getSurfaceSystemEfficiency(){
        return this.SurfaceSystemEfficiency;
    }
    public void setPositiveXWatt(int PositiveXWatt){
        this.PositiveXWatt = PositiveXWatt;
    }
    public int getPositiveXWatt(){
        return this.PositiveXWatt;
    }
    public void setNegativeXWatt(int NegativeXWatt){
        this.NegativeXWatt = NegativeXWatt;
    }
    public int getNegativeXWatt(){
        return this.NegativeXWatt;
    }
    public void setWattTimes(int WattTimes){
        this.WattTimes = WattTimes;
    }
    public int getWattTimes(){
        return this.WattTimes;
    }
    public void setITimes(int ITimes){
        this.ITimes = ITimes;
    }
    public int getITimes(){
        return this.ITimes;
    }
    public void setFSDiagramTimes(int FSDiagramTimes){
        this.FSDiagramTimes = FSDiagramTimes;
    }
    public int getFSDiagramTimes(){
        return this.FSDiagramTimes;
    }
    public void setFSDiagramLeftTimes(int FSDiagramLeftTimes){
        this.FSDiagramLeftTimes = FSDiagramLeftTimes;
    }
    public int getFSDiagramLeftTimes(){
        return this.FSDiagramLeftTimes;
    }
    public void setFSDiagramRightTimes(int FSDiagramRightTimes){
        this.FSDiagramRightTimes = FSDiagramRightTimes;
    }
    public int getFSDiagramRightTimes(){
        return this.FSDiagramRightTimes;
    }
    public void setLeftPercent(double LeftPercent){
        this.LeftPercent = LeftPercent;
    }
    public double getLeftPercent(){
        return this.LeftPercent;
    }
    public void setRightPercent(double RightPercent){
        this.RightPercent = RightPercent;
    }
    public double getRightPercent(){
        return this.RightPercent;
    }
	
	
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

	    private int OffsetAngleOfCrank;

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
	    public void setOffsetAngleOfCrank(int OffsetAngleOfCrank){
	        this.OffsetAngleOfCrank = OffsetAngleOfCrank;
	    }
	    public int getOffsetAngleOfCrank(){
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
	
	
}
