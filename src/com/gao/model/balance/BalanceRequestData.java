package com.gao.model.balance;

import java.util.List;

public class BalanceRequestData {
	
	private String AKString;

    private String WellName;

    private PumpingUnit PumpingUnit;

    private FSDiagram FSDiagram;

    private PSDiagram PSDiagram;

    private SystemEfficiency SystemEfficiency;

    private ManualIntervention ManualIntervention;

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
    public void setPumpingUnit(PumpingUnit PumpingUnit){
        this.PumpingUnit = PumpingUnit;
    }
    public PumpingUnit getPumpingUnit(){
        return this.PumpingUnit;
    }
    public void setFSDiagram(FSDiagram FSDiagram){
        this.FSDiagram = FSDiagram;
    }
    public FSDiagram getFSDiagram(){
        return this.FSDiagram;
    }
    public void setPSDiagram(PSDiagram PSDiagram){
        this.PSDiagram = PSDiagram;
    }
    public PSDiagram getPSDiagram(){
        return this.PSDiagram;
    }
    public void setSystemEfficiency(SystemEfficiency SystemEfficiency){
        this.SystemEfficiency = SystemEfficiency;
    }
    public SystemEfficiency getSystemEfficiency(){
        return this.SystemEfficiency;
    }
    public void setManualIntervention(ManualIntervention ManualIntervention){
        this.ManualIntervention = ManualIntervention;
    }
    public ManualIntervention getManualIntervention(){
        return this.ManualIntervention;
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
	    private int MaxCNT;

	    private List<EveryBalance> EveryBalance;

	    public void setMaxCNT(int MaxCNT){
	        this.MaxCNT = MaxCNT;
	    }
	    public int getMaxCNT(){
	        return this.MaxCNT;
	    }
	    public void setEveryBalance(List<EveryBalance> EveryBalance){
	        this.EveryBalance = EveryBalance;
	    }
	    public List<EveryBalance> getEveryBalance(){
	        return this.EveryBalance;
	    }
	}
	
	public static class PRTF
	{
	    private List<Float> CrankAngle;

	    private List<Float> PR;

	    private List<Float> TF;

	    public void setCrankAngle(List<Float> CrankAngle){
	        this.CrankAngle = CrankAngle;
	    }
	    public List<Float> getCrankAngle(){
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

	    private int Type;

	    private String CrankRotationDirection;

	    private float OffsetAngleOfCrank;

	    private int InitialAngleOfCrank;

	    private float CrankGravityRadius;

	    private float SingleCrankWeight;

	    private float BalanceMaxMoveSpace;

	    private float StructuralUnbalance;

	    private Balance Balance;

	    private PRTF PRTF;

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
	    public void setType(int Type){
	        this.Type = Type;
	    }
	    public int getType(){
	        return this.Type;
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
	    public void setInitialAngleOfCrank(int InitialAngleOfCrank){
	        this.InitialAngleOfCrank = InitialAngleOfCrank;
	    }
	    public int getInitialAngleOfCrank(){
	        return this.InitialAngleOfCrank;
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
	    public void setBalanceMaxMoveSpace(float BalanceMaxMoveSpace){
	        this.BalanceMaxMoveSpace = BalanceMaxMoveSpace;
	    }
	    public float getBalanceMaxMoveSpace(){
	        return this.BalanceMaxMoveSpace;
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
	}
	
	public static class FSDiagram
	{
	    private String AcqTime;

	    private float Stroke;

	    private float SPM;

	    private List<Float> F;

	    private List<Float> S;

	    public void setAcqTime(String AcqTime){
	        this.AcqTime = AcqTime;
	    }
	    public String getAcqTime(){
	        return this.AcqTime;
	    }
	    public void setStroke(float Stroke){
	        this.Stroke = Stroke;
	    }
	    public float getStroke(){
	        return this.Stroke;
	    }
	    public void setSPM(float SPM){
	        this.SPM = SPM;
	    }
	    public float getSPM(){
	        return this.SPM;
	    }
	    public void setF(List<Float> F){
	        this.F = F;
	    }
	    public List<Float> getF(){
	        return this.F;
	    }
	    public void setS(List<Float> S){
	        this.S = S;
	    }
	    public List<Float> getS(){
	        return this.S;
	    }
	}
	
	public static class PSDiagram
	{
	    private String AcqTime;

	    private float Stroke;

	    private float SPM;
	    
	    private List<Float> P;

	    private List<Float> S;

	    public void setAcqTime(String AcqTime){
	        this.AcqTime = AcqTime;
	    }
	    public String getAcqTime(){
	        return this.AcqTime;
	    }
	    public void setStroke(float Stroke){
	        this.Stroke = Stroke;
	    }
	    public float getStroke(){
	        return this.Stroke;
	    }
	    public void setSPM(float SPM){
	        this.SPM = SPM;
	    }
	    public float getSPM(){
	        return this.SPM;
	    }
	    public void setS(List<Float> S){
	        this.S = S;
	    }
	    public List<Float> getS(){
	        return this.S;
	    }
	    public void setP(List<Float> P){
	        this.P = P;
	    }
	    public List<Float> getP(){
	        return this.P;
	    }
	}
	
	public static class SystemEfficiency
	{
	    private float FourBarLinkageEfficiency;

	    private float MotorEfficiency;

	    private float BeltEfficiency;

	    private float GearReducerEfficiency;

	    public void setFourBarLinkageEfficiency(float FourBarLinkageEfficiency){
	        this.FourBarLinkageEfficiency = FourBarLinkageEfficiency;
	    }
	    public float getFourBarLinkageEfficiency(){
	        return this.FourBarLinkageEfficiency;
	    }
	    public void setMotorEfficiency(float MotorEfficiency){
	        this.MotorEfficiency = MotorEfficiency;
	    }
	    public float getMotorEfficiency(){
	        return this.MotorEfficiency;
	    }
	    public void setBeltEfficiency(float BeltEfficiency){
	        this.BeltEfficiency = BeltEfficiency;
	    }
	    public float getBeltEfficiency(){
	        return this.BeltEfficiency;
	    }
	    public void setGearReducerEfficiency(float GearReducerEfficiency){
	        this.GearReducerEfficiency = GearReducerEfficiency;
	    }
	    public float getGearReducerEfficiency(){
	        return this.GearReducerEfficiency;
	    }
	}
	
	public static class ManualIntervention
	{
	    private boolean NoNegative;

	    private boolean NoPositive;

	    public void setNoNegative(boolean NoNegative){
	        this.NoNegative = NoNegative;
	    }
	    public boolean getNoNegative(){
	        return this.NoNegative;
	    }
	    public void setNoPositive(boolean NoPositive){
	        this.NoPositive = NoPositive;
	    }
	    public boolean getNoPositive(){
	        return this.NoPositive;
	    }
	}
	
	

}
