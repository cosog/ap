package com.cosog.model.calculate;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.cosog.utils.StringManagerUtils;

public class RPCCalculateResponseData implements Serializable, Comparable<RPCCalculateResponseData>{
	
	private static final long serialVersionUID = 1L;
	
	 private String WellName;
	 
	 private String Scene;
	 
	 private float RPM;

	 private CalculationStatus CalculationStatus;

	 private Verification Verification;

	 private WellboreTrajectory WellboreTrajectory;

	 private RodString RodString;

	 private Production Production;

	 private FESDiagram FESDiagram;

	 private PumpEfficiency PumpEfficiency;

	 private WellboreSlice WellboreSlice;

	 private SystemEfficiency SystemEfficiency;
	 
	 public void init(){
		 this.setCalculationStatus(new CalculationStatus());
		 this.setVerification(new Verification());
	    	
		 this.setRodString(new RodString());
		 this.getRodString().setEveryRod(new ArrayList<EveryRod>());
		 
		 this.setProduction(new Production());
		 
		 this.setFESDiagram(new FESDiagram());
		 this.getFESDiagram().setF(new ArrayList<>());
		 this.getFESDiagram().getF().add(new ArrayList<>());
		 
		 this.getFESDiagram().setS(new ArrayList<>());
		 this.getFESDiagram().getS().add(new ArrayList<>());
		 
		 this.getFESDiagram().setWatt(new ArrayList<>());
		 this.getFESDiagram().setI(new ArrayList<Float>());
		 
		 this.getFESDiagram().setFMax(new ArrayList<Float>());
		 this.getFESDiagram().setFMin(new ArrayList<Float>());
		 
		 this.getFESDiagram().setDeltaF(new ArrayList<Float>());
		 
		 this.getFESDiagram().setCrankAngle(new ArrayList<Float>());
		 this.getFESDiagram().setV(new ArrayList<Float>());
		 this.getFESDiagram().setA(new ArrayList<Float>());
		 this.getFESDiagram().setPR(new ArrayList<Float>());
		 this.getFESDiagram().setTF(new ArrayList<Float>());
		 this.getFESDiagram().setLoadTorque(new ArrayList<Float>());
		 this.getFESDiagram().setCrankTorque(new ArrayList<Float>());
		 this.getFESDiagram().setCurrentBalanceTorque(new ArrayList<Float>());
		 this.getFESDiagram().setCurrentNetTorque(new ArrayList<Float>());
		 this.getFESDiagram().setExpectedBalanceTorque(new ArrayList<Float>());
		 this.getFESDiagram().setExpectedNetTorque(new ArrayList<Float>());
		 
		 this.setPumpEfficiency(new PumpEfficiency());
		 
		 this.setSystemEfficiency(new SystemEfficiency());
	 }

	 public void setWellName(String WellName){
	     this.WellName = WellName;
	 }
	 public String getWellName(){
	        return this.WellName;
	 }
	 public void setCalculationStatus(CalculationStatus CalculationStatus){
		 this.CalculationStatus = CalculationStatus;
	 }
	 public CalculationStatus getCalculationStatus(){
	     return this.CalculationStatus;
	 }
	 public void setVerification(Verification Verification){
	     this.Verification = Verification;
	 }
	 public Verification getVerification(){
	     return this.Verification;
	 }
	 public void setWellboreTrajectory(WellboreTrajectory WellboreTrajectory){
	     this.WellboreTrajectory = WellboreTrajectory;
	 }
	 public WellboreTrajectory getWellboreTrajectory(){
	     return this.WellboreTrajectory;
	 }
	 public void setRodString(RodString RodString){
	     this.RodString = RodString;
	 }
	 public RodString getRodString(){
	     return this.RodString;
	 }
	 public void setProduction(Production Production){
	     this.Production = Production;
	 }
	 public Production getProduction(){
	     return this.Production;
	 }
	 public void setFESDiagram(FESDiagram FESDiagram){
	     this.FESDiagram = FESDiagram;
	 }
	 public FESDiagram getFESDiagram(){
	     return this.FESDiagram;
	 }
	 public void setPumpEfficiency(PumpEfficiency PumpEfficiency){
	     this.PumpEfficiency = PumpEfficiency;
	 }
	 public PumpEfficiency getPumpEfficiency(){
	     return this.PumpEfficiency;
	 }
	 public void setWellboreSlice(WellboreSlice WellboreSlice){
	     this.WellboreSlice = WellboreSlice;
	 }
	 public WellboreSlice getWellboreSlice(){
	     return this.WellboreSlice;
	 }
	 public void setSystemEfficiency(SystemEfficiency SystemEfficiency){
	     this.SystemEfficiency = SystemEfficiency;
	 }
	 public SystemEfficiency getSystemEfficiency(){
	     return this.SystemEfficiency;
	 }
	 
	 public String getRodCalData(){
			StringBuffer result=new StringBuffer();
			if(this!=null&&this.getCalculationStatus().getResultStatus()==1){
				result.append(this.getRodString().CNT+","+this.getRodString().getLengthAll()+","+this.getRodString().getWeightAll()+","+this.getRodString().getBuoyancyForceAll());
				if(this.getRodString().getEveryRod()!=null){
					for(int i=0;i<this.getRodString().getEveryRod().size();i++){
						result.append(";");
						if(this.getFESDiagram().getFMax()!=null&&this.getFESDiagram().getFMax().size()>0){
							result.append(this.getFESDiagram().getFMax().get(i)+",");
							result.append(this.getFESDiagram().getFMin().get(i)+",");
						}else{
							result.append(0+",");
							result.append(0+",");
						}
						result.append(this.getRodString().getEveryRod().get(i).getMaxStress()+",");
						result.append(this.getRodString().getEveryRod().get(i).getMinStress()+",");
						result.append(this.getRodString().getEveryRod().get(i).getAllowableStress()+",");
						result.append(this.getRodString().getEveryRod().get(i).getStressRatio());
					}
				}
			}
			return result.toString();
		}

	public static class CalculationStatus implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
	    private int ResultStatus;

	    private int ResultCode;

	    public void setResultStatus(int ResultStatus){
	        this.ResultStatus = ResultStatus;
	    }
	    public int getResultStatus(){
	        return this.ResultStatus;
	    }
	    public void setResultCode(int ResultCode){
	        this.ResultCode = ResultCode;
	    }
	    public int getResultCode(){
	        return this.ResultCode;
	    }
	}
	
	public static class Verification implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
	    private int ErrorCounter;

	    private String ErrorString;

	    private int WarningCounter;

	    private String WarningString;

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
	}
	
	public static class WellboreTrajectory implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
	    private int CNT;

	    private List<Float> MeasuringDepth;

	    private List<Float> VerticalDepth;

	    private List<Float> DeviationAngle;

	    private List<Float> AzimuthAngle;

	    private List<Float> X;

	    private List<Float> Y;

	    private List<Float> Z;

	    public void setCNT(int CNT){
	        this.CNT = CNT;
	    }
	    public int getCNT(){
	        return this.CNT;
	    }
	    public void setMeasuringDepth(List<Float> MeasuringDepth){
	        this.MeasuringDepth = MeasuringDepth;
	    }
	    public List<Float> getMeasuringDepth(){
	        return this.MeasuringDepth;
	    }
	    public void setVerticalDepth(List<Float> VerticalDepth){
	        this.VerticalDepth = VerticalDepth;
	    }
	    public List<Float> getVerticalDepth(){
	        return this.VerticalDepth;
	    }
	    public void setDeviationAngle(List<Float> DeviationAngle){
	        this.DeviationAngle = DeviationAngle;
	    }
	    public List<Float> getDeviationAngle(){
	        return this.DeviationAngle;
	    }
	    public void setAzimuthAngle(List<Float> AzimuthAngle){
	        this.AzimuthAngle = AzimuthAngle;
	    }
	    public List<Float> getAzimuthAngle(){
	        return this.AzimuthAngle;
	    }
	    public void setX(List<Float> X){
	        this.X = X;
	    }
	    public List<Float> getX(){
	        return this.X;
	    }
	    public void setY(List<Float> Y){
	        this.Y = Y;
	    }
	    public List<Float> getY(){
	        return this.Y;
	    }
	    public void setZ(List<Float> Z){
	        this.Z = Z;
	    }
	    public List<Float> getZ(){
	        return this.Z;
	    }
	}

	public static class EveryRod implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
	    private int Type;

	    private String Grade;

	    private float Length;

	    private float OutsideDiameter;

	    private float InsideDiameter;

	    private float Area;

	    private float Weight;

	    private float WeightPerMeter;

	    private float BuoyancyForce;

	    private float Density;

	    private float TE;

	    private float SF;

	    private float DampingFactor;

	    private float MaxStress;

	    private float MinStress;

	    private float AllowableStress;

	    private float StressRatio;

	    public void setType(int Type){
	        this.Type = Type;
	    }
	    public int getType(){
	        return this.Type;
	    }
	    public void setGrade(String Grade){
	        this.Grade = Grade;
	    }
	    public String getGrade(){
	        return this.Grade;
	    }
	    public void setLength(float Length){
	        this.Length = Length;
	    }
	    public float getLength(){
	        return this.Length;
	    }
	    public void setOutsideDiameter(float OutsideDiameter){
	        this.OutsideDiameter = OutsideDiameter;
	    }
	    public float getOutsideDiameter(){
	        return this.OutsideDiameter;
	    }
	    public void setInsideDiameter(float InsideDiameter){
	        this.InsideDiameter = InsideDiameter;
	    }
	    public float getInsideDiameter(){
	        return this.InsideDiameter;
	    }
	    public void setArea(float Area){
	        this.Area = Area;
	    }
	    public float getArea(){
	        return this.Area;
	    }
	    public void setWeight(float Weight){
	        this.Weight = Weight;
	    }
	    public float getWeight(){
	        return this.Weight;
	    }
	    public void setWeightPerMeter(float WeightPerMeter){
	        this.WeightPerMeter = WeightPerMeter;
	    }
	    public float getWeightPerMeter(){
	        return this.WeightPerMeter;
	    }
	    public void setBuoyancyForce(float BuoyancyForce){
	        this.BuoyancyForce = BuoyancyForce;
	    }
	    public float getBuoyancyForce(){
	        return this.BuoyancyForce;
	    }
	    public void setDensity(float Density){
	        this.Density = Density;
	    }
	    public float getDensity(){
	        return this.Density;
	    }
	    public void setTE(float TE){
	        this.TE = TE;
	    }
	    public float getTE(){
	        return this.TE;
	    }
	    public void setSF(float SF){
	        this.SF = SF;
	    }
	    public float getSF(){
	        return this.SF;
	    }
	    public void setDampingFactor(float DampingFactor){
	        this.DampingFactor = DampingFactor;
	    }
	    public float getDampingFactor(){
	        return this.DampingFactor;
	    }
	    public void setMaxStress(float MaxStress){
	        this.MaxStress = MaxStress;
	    }
	    public float getMaxStress(){
	        return this.MaxStress;
	    }
	    public void setMinStress(float MinStress){
	        this.MinStress = MinStress;
	    }
	    public float getMinStress(){
	        return this.MinStress;
	    }
	    public void setAllowableStress(float AllowableStress){
	        this.AllowableStress = AllowableStress;
	    }
	    public float getAllowableStress(){
	        return this.AllowableStress;
	    }
	    public void setStressRatio(float StressRatio){
	        this.StressRatio = StressRatio;
	    }
	    public float getStressRatio(){
	        return this.StressRatio;
	    }
	}
	
	public static class RodString implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
	    private int CNT;

	    private float LengthAll;

	    private float WeightAll;

	    private float BuoyancyForceAll;

	    private String LengthString;

	    private String GradeString;

	    private String OutsideDiameterString;

	    private String InsideDiameterString;

	    private List<EveryRod> EveryRod;

	    public void setCNT(int CNT){
	        this.CNT = CNT;
	    }
	    public int getCNT(){
	        return this.CNT;
	    }
	    public void setLengthAll(float LengthAll){
	        this.LengthAll = LengthAll;
	    }
	    public float getLengthAll(){
	        return this.LengthAll;
	    }
	    public void setWeightAll(float WeightAll){
	        this.WeightAll = WeightAll;
	    }
	    public float getWeightAll(){
	        return this.WeightAll;
	    }
	    public void setBuoyancyForceAll(float BuoyancyForceAll){
	        this.BuoyancyForceAll = BuoyancyForceAll;
	    }
	    public float getBuoyancyForceAll(){
	        return this.BuoyancyForceAll;
	    }
	    public void setLengthString(String LengthString){
	        this.LengthString = LengthString;
	    }
	    public String getLengthString(){
	        return this.LengthString;
	    }
	    public void setGradeString(String GradeString){
	        this.GradeString = GradeString;
	    }
	    public String getGradeString(){
	        return this.GradeString;
	    }
	    public void setOutsideDiameterString(String OutsideDiameterString){
	        this.OutsideDiameterString = OutsideDiameterString;
	    }
	    public String getOutsideDiameterString(){
	        return this.OutsideDiameterString;
	    }
	    public void setInsideDiameterString(String InsideDiameterString){
	        this.InsideDiameterString = InsideDiameterString;
	    }
	    public String getInsideDiameterString(){
	        return this.InsideDiameterString;
	    }
	    public void setEveryRod(List<EveryRod> EveryRod){
	        this.EveryRod = EveryRod;
	    }
	    public List<EveryRod> getEveryRod(){
	        return this.EveryRod;
	    }
	}
	
	public static class Production implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
	    private float WaterCut;
	    
	    private float WeightWaterCut;

	    private float ProductionGasOilRatio;

	    private float TubingPressure;

	    private float CasingPressure;

	    private float BackPressure;

	    private float WellHeadFluidTemperature;

	    private float ProducingfluidLevel;
	    
	    private float CalcProducingfluidLevel;
	    
	    private float LevelDifferenceValue;

	    private float PumpSettingDepth;

	    private float Submergence;

	    private float PumpIntakeP;

	    private float PumpIntakeT;

	    private float PumpIntakeGOL;

	    private float PumpIntakeVisl;

	    private float PumpIntakeBo;

	    private float PumpOutletP;

	    private float PumpOutletT;

	    private float PumpOutletGOL;

	    private float PumpOutletVisl;

	    private float PumpOutletBo;

	    private float TheoreticalProduction;

	    private float LiquidVolumetricProduction;

	    private float OilVolumetricProduction;

	    private float WaterVolumetricProduction;

	    private float AvailablePlungerStrokeVolumetricProduction;

	    private float PumpClearanceLeakVolumetricProduction;

	    private float TVLeakVolumetricProduction;

	    private float SVLeakVolumetricProduction;

	    private float GasInfluenceVolumetricProduction;

	    private float LiquidWeightProduction;

	    private float OilWeightProduction;

	    private float WaterWeightProduction;

	    private float AvailablePlungerStrokeWeightProduction;

	    private float PumpClearanceLeakWeightProduction;

	    private float TVLeakWeightProduction;

	    private float SVLeakWeightProduction;

	    private float GasInfluenceWeightProduction;

	    public void setWaterCut(float WaterCut){
	        this.WaterCut = WaterCut;
	    }
	    public float getWaterCut(){
	        return this.WaterCut;
	    }
	    public void setProductionGasOilRatio(float ProductionGasOilRatio){
	        this.ProductionGasOilRatio = ProductionGasOilRatio;
	    }
	    public float getProductionGasOilRatio(){
	        return this.ProductionGasOilRatio;
	    }
	    public void setTubingPressure(float TubingPressure){
	        this.TubingPressure = TubingPressure;
	    }
	    public float getTubingPressure(){
	        return this.TubingPressure;
	    }
	    public void setCasingPressure(float CasingPressure){
	        this.CasingPressure = CasingPressure;
	    }
	    public float getCasingPressure(){
	        return this.CasingPressure;
	    }
	    public void setBackPressure(float BackPressure){
	        this.BackPressure = BackPressure;
	    }
	    public float getBackPressure(){
	        return this.BackPressure;
	    }
	    public void setWellHeadFluidTemperature(float WellHeadFluidTemperature){
	        this.WellHeadFluidTemperature = WellHeadFluidTemperature;
	    }
	    public float getWellHeadFluidTemperature(){
	        return this.WellHeadFluidTemperature;
	    }
	    public void setProducingfluidLevel(float ProducingfluidLevel){
	        this.ProducingfluidLevel = ProducingfluidLevel;
	    }
	    public float getProducingfluidLevel(){
	        return this.ProducingfluidLevel;
	    }
	    public void setPumpSettingDepth(float PumpSettingDepth){
	        this.PumpSettingDepth = PumpSettingDepth;
	    }
	    public float getPumpSettingDepth(){
	        return this.PumpSettingDepth;
	    }
	    public void setSubmergence(float Submergence){
	        this.Submergence = Submergence;
	    }
	    public float getSubmergence(){
	        return this.Submergence;
	    }
	    public void setPumpIntakeP(float PumpIntakeP){
	        this.PumpIntakeP = PumpIntakeP;
	    }
	    public float getPumpIntakeP(){
	        return this.PumpIntakeP;
	    }
	    public void setPumpIntakeT(float PumpIntakeT){
	        this.PumpIntakeT = PumpIntakeT;
	    }
	    public float getPumpIntakeT(){
	        return this.PumpIntakeT;
	    }
	    public void setPumpIntakeGOL(float PumpIntakeGOL){
	        this.PumpIntakeGOL = PumpIntakeGOL;
	    }
	    public float getPumpIntakeGOL(){
	        return this.PumpIntakeGOL;
	    }
	    public void setPumpIntakeVisl(float PumpIntakeVisl){
	        this.PumpIntakeVisl = PumpIntakeVisl;
	    }
	    public float getPumpIntakeVisl(){
	        return this.PumpIntakeVisl;
	    }
	    public void setPumpIntakeBo(float PumpIntakeBo){
	        this.PumpIntakeBo = PumpIntakeBo;
	    }
	    public float getPumpIntakeBo(){
	        return this.PumpIntakeBo;
	    }
	    public void setPumpOutletP(float PumpOutletP){
	        this.PumpOutletP = PumpOutletP;
	    }
	    public float getPumpOutletP(){
	        return this.PumpOutletP;
	    }
	    public void setPumpOutletT(float PumpOutletT){
	        this.PumpOutletT = PumpOutletT;
	    }
	    public float getPumpOutletT(){
	        return this.PumpOutletT;
	    }
	    public void setPumpOutletGOL(float PumpOutletGOL){
	        this.PumpOutletGOL = PumpOutletGOL;
	    }
	    public float getPumpOutletGOL(){
	        return this.PumpOutletGOL;
	    }
	    public void setPumpOutletVisl(float PumpOutletVisl){
	        this.PumpOutletVisl = PumpOutletVisl;
	    }
	    public float getPumpOutletVisl(){
	        return this.PumpOutletVisl;
	    }
	    public void setPumpOutletBo(float PumpOutletBo){
	        this.PumpOutletBo = PumpOutletBo;
	    }
	    public float getPumpOutletBo(){
	        return this.PumpOutletBo;
	    }
	    public void setTheoreticalProduction(float TheoreticalProduction){
	        this.TheoreticalProduction = TheoreticalProduction;
	    }
	    public float getTheoreticalProduction(){
	        return this.TheoreticalProduction;
	    }
	    public void setLiquidVolumetricProduction(float LiquidVolumetricProduction){
	        this.LiquidVolumetricProduction = LiquidVolumetricProduction;
	    }
	    public float getLiquidVolumetricProduction(){
	        return this.LiquidVolumetricProduction;
	    }
	    public void setOilVolumetricProduction(float OilVolumetricProduction){
	        this.OilVolumetricProduction = OilVolumetricProduction;
	    }
	    public float getOilVolumetricProduction(){
	        return this.OilVolumetricProduction;
	    }
	    public void setWaterVolumetricProduction(float WaterVolumetricProduction){
	        this.WaterVolumetricProduction = WaterVolumetricProduction;
	    }
	    public float getWaterVolumetricProduction(){
	        return this.WaterVolumetricProduction;
	    }
	    public void setAvailablePlungerStrokeVolumetricProduction(float AvailablePlungerStrokeVolumetricProduction){
	        this.AvailablePlungerStrokeVolumetricProduction = AvailablePlungerStrokeVolumetricProduction;
	    }
	    public float getAvailablePlungerStrokeVolumetricProduction(){
	        return this.AvailablePlungerStrokeVolumetricProduction;
	    }
	    public void setPumpClearanceLeakVolumetricProduction(float PumpClearanceLeakVolumetricProduction){
	        this.PumpClearanceLeakVolumetricProduction = PumpClearanceLeakVolumetricProduction;
	    }
	    public float getPumpClearanceLeakVolumetricProduction(){
	        return this.PumpClearanceLeakVolumetricProduction;
	    }
	    public void setTVLeakVolumetricProduction(float TVLeakVolumetricProduction){
	        this.TVLeakVolumetricProduction = TVLeakVolumetricProduction;
	    }
	    public float getTVLeakVolumetricProduction(){
	        return this.TVLeakVolumetricProduction;
	    }
	    public void setSVLeakVolumetricProduction(float SVLeakVolumetricProduction){
	        this.SVLeakVolumetricProduction = SVLeakVolumetricProduction;
	    }
	    public float getSVLeakVolumetricProduction(){
	        return this.SVLeakVolumetricProduction;
	    }
	    public void setGasInfluenceVolumetricProduction(float GasInfluenceVolumetricProduction){
	        this.GasInfluenceVolumetricProduction = GasInfluenceVolumetricProduction;
	    }
	    public float getGasInfluenceVolumetricProduction(){
	        return this.GasInfluenceVolumetricProduction;
	    }
	    public void setLiquidWeightProduction(float LiquidWeightProduction){
	        this.LiquidWeightProduction = LiquidWeightProduction;
	    }
	    public float getLiquidWeightProduction(){
	        return this.LiquidWeightProduction;
	    }
	    public void setOilWeightProduction(float OilWeightProduction){
	        this.OilWeightProduction = OilWeightProduction;
	    }
	    public float getOilWeightProduction(){
	        return this.OilWeightProduction;
	    }
	    public void setWaterWeightProduction(float WaterWeightProduction){
	        this.WaterWeightProduction = WaterWeightProduction;
	    }
	    public float getWaterWeightProduction(){
	        return this.WaterWeightProduction;
	    }
	    public void setAvailablePlungerStrokeWeightProduction(float AvailablePlungerStrokeWeightProduction){
	        this.AvailablePlungerStrokeWeightProduction = AvailablePlungerStrokeWeightProduction;
	    }
	    public float getAvailablePlungerStrokeWeightProduction(){
	        return this.AvailablePlungerStrokeWeightProduction;
	    }
	    public void setPumpClearanceLeakWeightProduction(float PumpClearanceLeakWeightProduction){
	        this.PumpClearanceLeakWeightProduction = PumpClearanceLeakWeightProduction;
	    }
	    public float getPumpClearanceLeakWeightProduction(){
	        return this.PumpClearanceLeakWeightProduction;
	    }
	    public void setTVLeakWeightProduction(float TVLeakWeightProduction){
	        this.TVLeakWeightProduction = TVLeakWeightProduction;
	    }
	    public float getTVLeakWeightProduction(){
	        return this.TVLeakWeightProduction;
	    }
	    public void setSVLeakWeightProduction(float SVLeakWeightProduction){
	        this.SVLeakWeightProduction = SVLeakWeightProduction;
	    }
	    public float getSVLeakWeightProduction(){
	        return this.SVLeakWeightProduction;
	    }
	    public void setGasInfluenceWeightProduction(float GasInfluenceWeightProduction){
	        this.GasInfluenceWeightProduction = GasInfluenceWeightProduction;
	    }
	    public float getGasInfluenceWeightProduction(){
	        return this.GasInfluenceWeightProduction;
	    }
		public float getWeightWaterCut() {
			return this.WeightWaterCut;
		}
		public void setWeightWaterCut(float WeightWaterCut) {
			this.WeightWaterCut = WeightWaterCut;
		}
		public float getCalcProducingfluidLevel() {
			return this.CalcProducingfluidLevel;
		}
		public void setCalcProducingfluidLevel(float CalcProducingfluidLevel) {
			this.CalcProducingfluidLevel = CalcProducingfluidLevel;
		}
		public float getLevelDifferenceValue() {
			return this.LevelDifferenceValue;
		}
		public void setLevelDifferenceValue(float LevelDifferenceValue) {
			this.LevelDifferenceValue = LevelDifferenceValue;
		}
	}

	public static class FESDiagram implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
	    private String AcqTime;

	    private float Stroke;

	    private float SPM;

	    private int CNT;

	    private float Area;

	    private float UpperLoadLine;

	    private float LowerLoadLine;

	    private float UpperLoadLineOfExact;
	    
	    private float DeltaLoadLine;
	    
	    private float DeltaLoadLineOfExact;

	    private float FullnessCoefficient;
	    
	    private float NoLiquidFullnessCoefficient;

	    private float PlungerStroke;

	    private float AvailablePlungerStroke;
	    
	    private float NoLiquidAvailablePlungerStroke;

	    private List<List<Float>> S;

	    private List<List<Float>> F;

	    private List<Float> Watt;

	    private List<Float> I;

	    private List<Float> FMax;

	    private List<Float> FMin;
	    
	    private List<Float> DeltaF;

	    private int SMaxIndex;

	    private int SMinIndex;

	    private float UpStrokeWattMax;

	    private float DownStrokeWattMax;

	    private float WattDegreeBalance;

	    private String WattMaxRatioString;

	    private float AvgWatt;

	    private float UpStrokeIMax;

	    private float DownStrokeIMax;

	    private float IDegreeBalance;

	    private String IMaxRatioString;

	    private List<Float> CrankAngle;

	    private List<Float> V;

	    private List<Float> A;

	    private List<Float> PR;

	    private List<Float> TF;

	    private List<Float> LoadTorque;

	    private List<Float> CrankTorque;

	    private List<Float> CurrentBalanceTorque;

	    private List<Float> CurrentNetTorque;

	    private List<Float> ExpectedBalanceTorque;

	    private List<Float> ExpectedNetTorque;

	    private float DeltaRadius;

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
	    public void setCNT(int CNT){
	        this.CNT = CNT;
	    }
	    public int getCNT(){
	        return this.CNT;
	    }
	    public void setArea(float Area){
	        this.Area = Area;
	    }
	    public float getArea(){
	        return this.Area;
	    }
	    public void setUpperLoadLine(float UpperLoadLine){
	        this.UpperLoadLine = UpperLoadLine;
	    }
	    public float getUpperLoadLine(){
	        return this.UpperLoadLine;
	    }
	    public void setLowerLoadLine(float LowerLoadLine){
	        this.LowerLoadLine = LowerLoadLine;
	    }
	    public float getLowerLoadLine(){
	        return this.LowerLoadLine;
	    }
	    public void setUpperLoadLineOfExact(float UpperLoadLineOfExact){
	        this.UpperLoadLineOfExact = UpperLoadLineOfExact;
	    }
	    public float getUpperLoadLineOfExact(){
	        return this.UpperLoadLineOfExact;
	    }
	    public void setFullnessCoefficient(float FullnessCoefficient){
	        this.FullnessCoefficient = FullnessCoefficient;
	    }
	    public float getFullnessCoefficient(){
	        return this.FullnessCoefficient;
	    }
	    public void setPlungerStroke(float PlungerStroke){
	        this.PlungerStroke = PlungerStroke;
	    }
	    public float getPlungerStroke(){
	        return this.PlungerStroke;
	    }
	    public void setAvailablePlungerStroke(float AvailablePlungerStroke){
	        this.AvailablePlungerStroke = AvailablePlungerStroke;
	    }
	    public float getAvailablePlungerStroke(){
	        return this.AvailablePlungerStroke;
	    }
	    public void setS(List<List<Float>> S){
	        this.S = S;
	    }
	    public List<List<Float>> getS(){
	        return this.S;
	    }
	    public void setF(List<List<Float>> F){
	        this.F = F;
	    }
	    public List<List<Float>> getF(){
	        return this.F;
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
	    public void setFMax(List<Float> FMax){
	        this.FMax = FMax;
	    }
	    public List<Float> getFMax(){
	        return this.FMax;
	    }
	    public void setFMin(List<Float> FMin){
	        this.FMin = FMin;
	    }
	    public List<Float> getFMin(){
	        return this.FMin;
	    }
	    public void setSMaxIndex(int SMaxIndex){
	        this.SMaxIndex = SMaxIndex;
	    }
	    public int getSMaxIndex(){
	        return this.SMaxIndex;
	    }
	    public void setSMinIndex(int SMinIndex){
	        this.SMinIndex = SMinIndex;
	    }
	    public int getSMinIndex(){
	        return this.SMinIndex;
	    }
	    public void setUpStrokeWattMax(float UpStrokeWattMax){
	        this.UpStrokeWattMax = UpStrokeWattMax;
	    }
	    public float getUpStrokeWattMax(){
	        return this.UpStrokeWattMax;
	    }
	    public void setDownStrokeWattMax(float DownStrokeWattMax){
	        this.DownStrokeWattMax = DownStrokeWattMax;
	    }
	    public float getDownStrokeWattMax(){
	        return this.DownStrokeWattMax;
	    }
	    public void setWattDegreeBalance(float WattDegreeBalance){
	        this.WattDegreeBalance = WattDegreeBalance;
	    }
	    public float getWattDegreeBalance(){
	        return this.WattDegreeBalance;
	    }
	    public void setWattMaxRatioString(String WattMaxRatioString){
	        this.WattMaxRatioString = WattMaxRatioString;
	    }
	    public String getWattMaxRatioString(){
	        return this.WattMaxRatioString;
	    }
	    public void setAvgWatt(float AvgWatt){
	        this.AvgWatt = AvgWatt;
	    }
	    public float getAvgWatt(){
	        return this.AvgWatt;
	    }
	    public void setUpStrokeIMax(float UpStrokeIMax){
	        this.UpStrokeIMax = UpStrokeIMax;
	    }
	    public float getUpStrokeIMax(){
	        return this.UpStrokeIMax;
	    }
	    public void setDownStrokeIMax(float DownStrokeIMax){
	        this.DownStrokeIMax = DownStrokeIMax;
	    }
	    public float getDownStrokeIMax(){
	        return this.DownStrokeIMax;
	    }
	    public void setIDegreeBalance(float IDegreeBalance){
	        this.IDegreeBalance = IDegreeBalance;
	    }
	    public float getIDegreeBalance(){
	        return this.IDegreeBalance;
	    }
	    public void setIMaxRatioString(String IMaxRatioString){
	        this.IMaxRatioString = IMaxRatioString;
	    }
	    public String getIMaxRatioString(){
	        return this.IMaxRatioString;
	    }
	    public void setCrankAngle(List<Float> CrankAngle){
	        this.CrankAngle = CrankAngle;
	    }
	    public List<Float> getCrankAngle(){
	        return this.CrankAngle;
	    }
	    public void setV(List<Float> V){
	        this.V = V;
	    }
	    public List<Float> getV(){
	        return this.V;
	    }
	    public void setA(List<Float> A){
	        this.A = A;
	    }
	    public List<Float> getA(){
	        return this.A;
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
	    public void setLoadTorque(List<Float> LoadTorque){
	        this.LoadTorque = LoadTorque;
	    }
	    public List<Float> getLoadTorque(){
	        return this.LoadTorque;
	    }
	    public void setCrankTorque(List<Float> CrankTorque){
	        this.CrankTorque = CrankTorque;
	    }
	    public List<Float> getCrankTorque(){
	        return this.CrankTorque;
	    }
	    public void setCurrentBalanceTorque(List<Float> CurrentBalanceTorque){
	        this.CurrentBalanceTorque = CurrentBalanceTorque;
	    }
	    public List<Float> getCurrentBalanceTorque(){
	        return this.CurrentBalanceTorque;
	    }
	    public void setCurrentNetTorque(List<Float> CurrentNetTorque){
	        this.CurrentNetTorque = CurrentNetTorque;
	    }
	    public List<Float> getCurrentNetTorque(){
	        return this.CurrentNetTorque;
	    }
	    public void setExpectedBalanceTorque(List<Float> ExpectedBalanceTorque){
	        this.ExpectedBalanceTorque = ExpectedBalanceTorque;
	    }
	    public List<Float> getExpectedBalanceTorque(){
	        return this.ExpectedBalanceTorque;
	    }
	    public void setExpectedNetTorque(List<Float> ExpectedNetTorque){
	        this.ExpectedNetTorque = ExpectedNetTorque;
	    }
	    public List<Float> getExpectedNetTorque(){
	        return this.ExpectedNetTorque;
	    }
	    public void setDeltaRadius(float DeltaRadius){
	        this.DeltaRadius = DeltaRadius;
	    }
	    public float getDeltaRadius(){
	        return this.DeltaRadius;
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
		public List<Float> getDeltaF() {
			return DeltaF;
		}
		public void setDeltaF(List<Float> deltaF) {
			DeltaF = deltaF;
		}
		public float getNoLiquidFullnessCoefficient() {
			return NoLiquidFullnessCoefficient;
		}
		public void setNoLiquidFullnessCoefficient(float noLiquidFullnessCoefficient) {
			NoLiquidFullnessCoefficient = noLiquidFullnessCoefficient;
		}
		public float getNoLiquidAvailablePlungerStroke() {
			return NoLiquidAvailablePlungerStroke;
		}
		public void setNoLiquidAvailablePlungerStroke(float noLiquidAvailablePlungerStroke) {
			NoLiquidAvailablePlungerStroke = noLiquidAvailablePlungerStroke;
		}
	}

	public static class PumpEfficiency implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
	    private float PumpEff1;

	    private float RodFlexLength;

	    private float TubingFlexLength;

	    private float InertiaLength;

	    private float PumpEff2;

	    private float PumpEff3;

	    private float PumpEff4;

	    private float PumpEff;

	    public void setPumpEff1(float PumpEff1){
	        this.PumpEff1 = PumpEff1;
	    }
	    public float getPumpEff1(){
	        return this.PumpEff1;
	    }
	    public void setRodFlexLength(float RodFlexLength){
	        this.RodFlexLength = RodFlexLength;
	    }
	    public float getRodFlexLength(){
	        return this.RodFlexLength;
	    }
	    public void setTubingFlexLength(float TubingFlexLength){
	        this.TubingFlexLength = TubingFlexLength;
	    }
	    public float getTubingFlexLength(){
	        return this.TubingFlexLength;
	    }
	    public void setInertiaLength(float InertiaLength){
	        this.InertiaLength = InertiaLength;
	    }
	    public float getInertiaLength(){
	        return this.InertiaLength;
	    }
	    public void setPumpEff2(float PumpEff2){
	        this.PumpEff2 = PumpEff2;
	    }
	    public float getPumpEff2(){
	        return this.PumpEff2;
	    }
	    public void setPumpEff3(float PumpEff3){
	        this.PumpEff3 = PumpEff3;
	    }
	    public float getPumpEff3(){
	        return this.PumpEff3;
	    }
	    public void setPumpEff4(float PumpEff4){
	        this.PumpEff4 = PumpEff4;
	    }
	    public float getPumpEff4(){
	        return this.PumpEff4;
	    }
	    public void setPumpEff(float PumpEff){
	        this.PumpEff = PumpEff;
	    }
	    public float getPumpEff(){
	        return this.PumpEff;
	    }
	}
	

	public static class WellboreSlice implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
	    private int CNT;

	    private List<Float> MeasuringDepth;

	    private List<Float> X;

	    private List<Float> Y;

	    private List<Float> Z;

	    private List<Float> P;

	    private List<Float> Bo;

	    private List<Float> GLRis;

	    public void setCNT(int CNT){
	        this.CNT = CNT;
	    }
	    public int getCNT(){
	        return this.CNT;
	    }
	    public void setMeasuringDepth(List<Float> MeasuringDepth){
	        this.MeasuringDepth = MeasuringDepth;
	    }
	    public List<Float> getMeasuringDepth(){
	        return this.MeasuringDepth;
	    }
	    public void setX(List<Float> X){
	        this.X = X;
	    }
	    public List<Float> getX(){
	        return this.X;
	    }
	    public void setY(List<Float> Y){
	        this.Y = Y;
	    }
	    public List<Float> getY(){
	        return this.Y;
	    }
	    public void setZ(List<Float> Z){
	        this.Z = Z;
	    }
	    public List<Float> getZ(){
	        return this.Z;
	    }
		public List<Float> getP() {
			return P;
		}
		public void setP(List<Float> p) {
			P = p;
		}
		public List<Float> getBo() {
			return Bo;
		}
		public void setBo(List<Float> bo) {
			Bo = bo;
		}
		public List<Float> getGLRis() {
			return GLRis;
		}
		public void setGLRis(List<Float> gLRis) {
			GLRis = gLRis;
		}
	}
	
	public static class SystemEfficiency implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
	    private float MotorEfficiency;

	    private float BeltEfficiency;

	    private float GearReducerEfficiency;

	    private float FourBarLinkageEfficiency;

	    private float SurfaceSystemEfficiency;

	    private float WellDownSystemEfficiency;

	    private float SystemEfficiency;

	    private float EnergyPer100mLift;

//	    private float MotorInputWatt;

	    private float PolishRodPower;

	    private float WaterPower;

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
	    public void setFourBarLinkageEfficiency(float FourBarLinkageEfficiency){
	        this.FourBarLinkageEfficiency = FourBarLinkageEfficiency;
	    }
	    public float getFourBarLinkageEfficiency(){
	        return this.FourBarLinkageEfficiency;
	    }
	    public void setSurfaceSystemEfficiency(float SurfaceSystemEfficiency){
	        this.SurfaceSystemEfficiency = SurfaceSystemEfficiency;
	    }
	    public float getSurfaceSystemEfficiency(){
	        return this.SurfaceSystemEfficiency;
	    }
	    public void setWellDownSystemEfficiency(float WellDownSystemEfficiency){
	        this.WellDownSystemEfficiency = WellDownSystemEfficiency;
	    }
	    public float getWellDownSystemEfficiency(){
	        return this.WellDownSystemEfficiency;
	    }
	    public void setSystemEfficiency(float SystemEfficiency){
	        this.SystemEfficiency = SystemEfficiency;
	    }
	    public float getSystemEfficiency(){
	        return this.SystemEfficiency;
	    }
	    public void setEnergyPer100mLift(float EnergyPer100mLift){
	        this.EnergyPer100mLift = EnergyPer100mLift;
	    }
	    public float getEnergyPer100mLift(){
	        return this.EnergyPer100mLift;
	    }
//	    public void setMotorInputWatt(float MotorInputWatt){
//	        this.MotorInputWatt = MotorInputWatt;
//	    }
//	    public float getMotorInputWatt(){
//	        return this.MotorInputWatt;
//	    }
	    public void setPolishRodPower(float PolishRodPower){
	        this.PolishRodPower = PolishRodPower;
	    }
	    public float getPolishRodPower(){
	        return this.PolishRodPower;
	    }
	    public void setWaterPower(float WaterPower){
	        this.WaterPower = WaterPower;
	    }
	    public float getWaterPower(){
	        return this.WaterPower;
	    }
	}
	
	@Override
	public int compareTo(RPCCalculateResponseData responseData) {     //重写Comparable接口的compareTo方法
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long from = 0;
		long to = 0;
		try {
			if(this.getFESDiagram()!=null&&StringManagerUtils.isNotNull(this.getFESDiagram().getAcqTime())){
				to=simpleDateFormat.parse(this.getFESDiagram().getAcqTime()).getTime();
			}
			if(responseData.getFESDiagram()!=null&&StringManagerUtils.isNotNull(responseData.getFESDiagram().getAcqTime())){
				from=simpleDateFormat.parse(responseData.getFESDiagram().getAcqTime()).getTime();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int)(to-from);   // 根据值或者位升序排列，降序修改相减顺序即可
	}

	public String getScene() {
		return Scene;
	}

	public void setScene(String scene) {
		Scene = scene;
	}

	public float getRPM() {
		return RPM;
	}

	public void setRPM(float RPM) {
		this.RPM = RPM;
	}
}
