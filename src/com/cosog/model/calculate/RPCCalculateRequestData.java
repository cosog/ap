package com.cosog.model.calculate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RPCCalculateRequestData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String WellName;

    private FluidPVT FluidPVT;

    private Reservoir Reservoir;

    private WellboreTrajectory WellboreTrajectory;

    private RodString RodString;

    private TubingString TubingString;

    private Pump Pump;

    private CasingString CasingString;

    private PumpingUnit PumpingUnit;

    private Production Production;

    private FESDiagram FESDiagram;

    private FeatureDB FeatureDB;

    private SystemEfficiency SystemEfficiency;

    private ManualIntervention ManualIntervention;

    public void init(){
    	this.setFluidPVT(new FluidPVT());
    	this.setReservoir(new Reservoir());
    	
    	this.setTubingString(new TubingString());
    	this.getTubingString().setEveryTubing(new ArrayList<EveryTubing>());
    	this.getTubingString().getEveryTubing().add(new EveryTubing());
    	
    	this.setCasingString(new CasingString());
    	this.getCasingString().setEveryCasing(new ArrayList<EveryCasing>());
    	this.getCasingString().getEveryCasing().add(new EveryCasing());
    	
    	this.setRodString(new RodString());
    	this.getRodString().setEveryRod(new ArrayList<EveryRod>());
    	
    	this.setPump(new Pump());
    	this.setProduction(new Production());
    	
    	this.setPumpingUnit(new PumpingUnit());
    	this.getPumpingUnit().setBalance(new Balance());
    	this.getPumpingUnit().getBalance().setEveryBalance(new ArrayList<EveryBalance>());
    	
    	
    	
    	this.setFESDiagram(new FESDiagram());
    	this.getFESDiagram().setS(new ArrayList<Float>());
    	this.getFESDiagram().setF(new ArrayList<Float>());
    	this.getFESDiagram().setWatt(new ArrayList<Float>());
    	this.getFESDiagram().setI(new ArrayList<Float>());
    	
    	this.setManualIntervention(new ManualIntervention());
    }
    
    
    
    public void setWellName(String WellName){
        this.WellName = WellName;
    }
    public String getWellName(){
        return this.WellName;
    }
    public void setFluidPVT(FluidPVT FluidPVT){
        this.FluidPVT = FluidPVT;
    }
    public FluidPVT getFluidPVT(){
        return this.FluidPVT;
    }
    public void setReservoir(Reservoir Reservoir){
        this.Reservoir = Reservoir;
    }
    public Reservoir getReservoir(){
        return this.Reservoir;
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
    public void setTubingString(TubingString TubingString){
        this.TubingString = TubingString;
    }
    public TubingString getTubingString(){
        return this.TubingString;
    }
    public void setPump(Pump Pump){
        this.Pump = Pump;
    }
    public Pump getPump(){
        return this.Pump;
    }
    public void setCasingString(CasingString CasingString){
        this.CasingString = CasingString;
    }
    public CasingString getCasingString(){
        return this.CasingString;
    }
    public void setPumpingUnit(PumpingUnit PumpingUnit){
        this.PumpingUnit = PumpingUnit;
    }
    public PumpingUnit getPumpingUnit(){
        return this.PumpingUnit;
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
    public void setFeatureDB(FeatureDB FeatureDB){
        this.FeatureDB = FeatureDB;
    }
    public FeatureDB getFeatureDB(){
        return this.FeatureDB;
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

	public static class FluidPVT  implements Serializable {
		private static final long serialVersionUID = 1L;
		
	    private float CrudeOilDensity;

	    private float WaterDensity;

	    private float NaturalGasRelativeDensity;

	    private float SaturationPressure;

	    public void setCrudeOilDensity(float CrudeOilDensity){
	        this.CrudeOilDensity = CrudeOilDensity;
	    }
	    public float getCrudeOilDensity(){
	        return this.CrudeOilDensity;
	    }
	    public void setWaterDensity(float WaterDensity){
	        this.WaterDensity = WaterDensity;
	    }
	    public float getWaterDensity(){
	        return this.WaterDensity;
	    }
	    public void setNaturalGasRelativeDensity(float NaturalGasRelativeDensity){
	        this.NaturalGasRelativeDensity = NaturalGasRelativeDensity;
	    }
	    public float getNaturalGasRelativeDensity(){
	        return this.NaturalGasRelativeDensity;
	    }
	    public void setSaturationPressure(float SaturationPressure){
	        this.SaturationPressure = SaturationPressure;
	    }
	    public float getSaturationPressure(){
	        return this.SaturationPressure;
	    }
	}
	
	public static class Reservoir implements Serializable {
		private static final long serialVersionUID = 1L;
	    private float Depth;

	    private float Temperature;

	    public void setDepth(float Depth){
	        this.Depth = Depth;
	    }
	    public float getDepth(){
	        return this.Depth;
	    }
	    public void setTemperature(float Temperature){
	        this.Temperature = Temperature;
	    }
	    public float getTemperature(){
	        return this.Temperature;
	    }
	}
	
	public static class WellboreTrajectory implements Serializable {
		private static final long serialVersionUID = 1L;
	    private List<Float> MeasuringDepth;

	    private List<Float> DeviationAngle;

	    private List<Float> AzimuthAngle;

	    public void setMeasuringDepth(List<Float> MeasuringDepth){
	        this.MeasuringDepth = MeasuringDepth;
	    }
	    public List<Float> getMeasuringDepth(){
	        return this.MeasuringDepth;
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
	}
	
	public static class EveryRod implements Serializable {
		private static final long serialVersionUID = 1L;
	    private int Type;

	    private String Grade;

	    private float Length;

	    private float OutsideDiameter;

	    private float InsideDiameter;

	    private float Density;

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
	    public void setDensity(float Density){
	        this.Density = Density;
	    }
	    public float getDensity(){
	        return this.Density;
	    }
	}
	
	public static class RodString implements Serializable {
		private static final long serialVersionUID = 1L;
	    private List<EveryRod> EveryRod;

	    public void setEveryRod(List<EveryRod> EveryRod){
	        this.EveryRod = EveryRod;
	    }
	    public List<EveryRod> getEveryRod(){
	        return this.EveryRod;
	    }
	}
	
	public static class EveryTubing implements Serializable {
		private static final long serialVersionUID = 1L;
	    private String Grade;

	    private float length;

	    private float OutsideDiameter;

	    private float InsideDiameter;

	    private float Density;

	    private float WeightPerMeter;

	    public void setGrade(String Grade){
	        this.Grade = Grade;
	    }
	    public String getGrade(){
	        return this.Grade;
	    }
	    public void setLength(float length){
	        this.length = length;
	    }
	    public float getLength(){
	        return this.length;
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
	    public void setDensity(float Density){
	        this.Density = Density;
	    }
	    public float getDensity(){
	        return this.Density;
	    }
	    public void setWeightPerMeter(float WeightPerMeter){
	        this.WeightPerMeter = WeightPerMeter;
	    }
	    public float getWeightPerMeter(){
	        return this.WeightPerMeter;
	    }
	}
	
	public static class TubingString implements Serializable {
		private static final long serialVersionUID = 1L;
	    private List<EveryTubing> EveryTubing;

	    public void setEveryTubing(List<EveryTubing> EveryTubing){
	        this.EveryTubing = EveryTubing;
	    }
	    public List<EveryTubing> getEveryTubing(){
	        return this.EveryTubing;
	    }
	}
	
	public static class Pump implements Serializable {
		private static final long serialVersionUID = 1L;
	    private String PumpType;

	    private String BarrelType;

	    private int PumpGrade;

	    private int BarrelLength;

	    private float PlungerLength;

	    private float PumpBoreDiameter;

	    private float Clearance;

	    private float AntiImpactStroke;

	    public void setPumpType(String PumpType){
	        this.PumpType = PumpType;
	    }
	    public String getPumpType(){
	        return this.PumpType;
	    }
	    public void setBarrelType(String BarrelType){
	        this.BarrelType = BarrelType;
	    }
	    public String getBarrelType(){
	        return this.BarrelType;
	    }
	    public void setPumpGrade(int PumpGrade){
	        this.PumpGrade = PumpGrade;
	    }
	    public int getPumpGrade(){
	        return this.PumpGrade;
	    }
	    public void setBarrelLength(int BarrelLength){
	        this.BarrelLength = BarrelLength;
	    }
	    public int getBarrelLength(){
	        return this.BarrelLength;
	    }
	    public void setPlungerLength(float PlungerLength){
	        this.PlungerLength = PlungerLength;
	    }
	    public float getPlungerLength(){
	        return this.PlungerLength;
	    }
	    public void setPumpBoreDiameter(float PumpBoreDiameter){
	        this.PumpBoreDiameter = PumpBoreDiameter;
	    }
	    public float getPumpBoreDiameter(){
	        return this.PumpBoreDiameter;
	    }
	    public void setClearance(float Clearance){
	        this.Clearance = Clearance;
	    }
	    public float getClearance(){
	        return this.Clearance;
	    }
	    public void setAntiImpactStroke(float AntiImpactStroke){
	        this.AntiImpactStroke = AntiImpactStroke;
	    }
	    public float getAntiImpactStroke(){
	        return this.AntiImpactStroke;
	    }
	}
	
	public static class EveryCasing implements Serializable {
		private static final long serialVersionUID = 1L;
	    private String Grade;

	    private float OutsideDiameter;

	    private float InsideDiameter;

	    private float Length;

	    private float Density;

	    private float WeightPerMeter;

	    public void setGrade(String Grade){
	        this.Grade = Grade;
	    }
	    public String getGrade(){
	        return this.Grade;
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
	    public void setLength(float Length){
	        this.Length = Length;
	    }
	    public float getLength(){
	        return this.Length;
	    }
	    public void setDensity(float Density){
	        this.Density = Density;
	    }
	    public float getDensity(){
	        return this.Density;
	    }
	    public void setWeightPerMeter(float WeightPerMeter){
	        this.WeightPerMeter = WeightPerMeter;
	    }
	    public float getWeightPerMeter(){
	        return this.WeightPerMeter;
	    }
	}
	
	public static class CasingString implements Serializable {
		private static final long serialVersionUID = 1L;
	    private List<EveryCasing> EveryCasing;

	    public void setEveryCasing(List<EveryCasing> EveryCasing){
	        this.EveryCasing = EveryCasing;
	    }
	    public List<EveryCasing> getEveryCasing(){
	        return this.EveryCasing;
	    }
	}
	
	public static class EveryBalance implements Serializable {
		private static final long serialVersionUID = 1L;
	    private float Weight;
	    private float Position;
	    public void setWeight(float Weight){
	        this.Weight = Weight;
	    }
	    public float getWeight(){
	        return this.Weight;
	    }
		public float getPosition() {
			return Position;
		}
		public void setPosition(float position) {
			Position = position;
		}
	}
	
	public static class Balance implements Serializable {
		private static final long serialVersionUID = 1L;
	    private List<EveryBalance> EveryBalance;

	    public void setEveryBalance(List<EveryBalance> EveryBalance){
	        this.EveryBalance = EveryBalance;
	    }
	    public List<EveryBalance> getEveryBalance(){
	        return this.EveryBalance;
	    }
	}
	
	public static class PRTF implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private List<Float> CrankAngle;
		
		private List<Float> PR;
		
		private List<Float> TF;

		public List<Float> getCrankAngle() {
			return CrankAngle;
		}

		public void setCrankAngle(List<Float> crankAngle) {
			CrankAngle = crankAngle;
		}

		public List<Float> getPR() {
			return PR;
		}

		public void setPR(List<Float> pR) {
			PR = pR;
		}

		public List<Float> getTF() {
			return TF;
		}

		public void setTF(List<Float> tF) {
			TF = tF;
		}
		
	}
	
	public static class Motor implements Serializable {
		private static final long serialVersionUID = 1L;
	    private String Manufacturer;

	    private String Model;

	    private float RatedPower;

	    private float NoloadCurrent;

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
	    public void setRatedPower(float RatedPower){
	        this.RatedPower = RatedPower;
	    }
	    public float getRatedPower(){
	        return this.RatedPower;
	    }
	    public void setNoloadCurrent(float NoloadCurrent){
	        this.NoloadCurrent = NoloadCurrent;
	    }
	    public float getNoloadCurrent(){
	        return this.NoloadCurrent;
	    }
	}
	
	public static class PumpingUnit implements Serializable {
		private static final long serialVersionUID = 1L;
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

	    private Motor Motor;
	    
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
	    public void setMotor(Motor Motor){
	        this.Motor = Motor;
	    }
	    public Motor getMotor(){
	        return this.Motor;
	    }
		public float getSingleCrankPinWeight() {
			return SingleCrankPinWeight;
		}
		public void setSingleCrankPinWeight(float singleCrankPinWeight) {
			SingleCrankPinWeight = singleCrankPinWeight;
		}
		public PRTF getPRTF() {
			return PRTF;
		}
		public void setPRTF(PRTF pRTF) {
			PRTF = pRTF;
		}
	}

	public static class Production implements Serializable {
		private static final long serialVersionUID = 1L;
	    private float WaterCut;
	    
	    private float WeightWaterCut;

	    private float ProductionGasOilRatio;

	    private float TubingPressure;

	    private float CasingPressure;

	    private float WellHeadTemperature;

	    private float ProducingfluidLevel;

	    private float PumpSettingDepth;
	    
	    private float LevelCorrectValue;

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
	    public void setWellHeadTemperature(float WellHeadTemperature){
	        this.WellHeadTemperature = WellHeadTemperature;
	    }
	    public float getWellHeadTemperature(){
	        return this.WellHeadTemperature;
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
		public float getLevelCorrectValue() {
			return LevelCorrectValue;
		}
		public void setLevelCorrectValue(float levelCorrectValue) {
			LevelCorrectValue = levelCorrectValue;
		}
		public float getWeightWaterCut() {
			return WeightWaterCut;
		}
		public void setWeightWaterCut(float weightWaterCut) {
			WeightWaterCut = weightWaterCut;
		}
	}
	
	public static class FESDiagram implements Serializable {
		private static final long serialVersionUID = 1L;
	    private int Src;
		
		private String AcqTime;

	    private float Stroke;

	    private float SPM;

	    private List<Float> F;

	    private List<Float> S;

	    private List<Float> Watt;

	    private List<Float> I;

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
		public int getSrc() {
			return Src;
		}
		public void setSrc(int src) {
			Src = src;
		}
	}
	
	public static class FeatureDB implements Serializable {
		private static final long serialVersionUID = 1L;
	    private float ClosedGraphNumber;

	    private float BandCoefficient;

	    private float ApproximationCoefficient;

	    private float MiddleWidthRatio;

	    private float XSplitNumber;

	    private float FlowingWellFluidLevel;

	    private float NarrowCoefficient;

	    private float BlockageCoefficient;

	    private float ClosedAreaRatio1;

	    private float ClosedAreaRatio2;

	    private float UpperLowerCoefficient;

	    private float SandParameter1;

	    private float SandParameter2;

	    private float TopLeakCoefficient;

	    private float BottomLeakCoefficient;

	    private float TopLeftK;

	    private float TopRightK;

	    private float BottomLeftK;

	    private float BottomRightK;

	    private float DeflectionDegree;

	    private float WaxCoefficient1;

	    private float WaxCoefficient2;

	    private float FullnessCoefficient1;

	    private float FullnessCoefficient2;

	    private float FullnessCoefficient3;

	    private float FullnessCoefficient4;

	    private float NormalSubmergence;

	    private float PumpOffSubmergence;

	    private float IntakeBlockingSubmergence;

	    private float ProlapseCoefficient;

	    private float CheckRangeCoefficient;

	    public void setClosedGraphNumber(float ClosedGraphNumber){
	        this.ClosedGraphNumber = ClosedGraphNumber;
	    }
	    public float getClosedGraphNumber(){
	        return this.ClosedGraphNumber;
	    }
	    public void setBandCoefficient(float BandCoefficient){
	        this.BandCoefficient = BandCoefficient;
	    }
	    public float getBandCoefficient(){
	        return this.BandCoefficient;
	    }
	    public void setApproximationCoefficient(float ApproximationCoefficient){
	        this.ApproximationCoefficient = ApproximationCoefficient;
	    }
	    public float getApproximationCoefficient(){
	        return this.ApproximationCoefficient;
	    }
	    public void setMiddleWidthRatio(float MiddleWidthRatio){
	        this.MiddleWidthRatio = MiddleWidthRatio;
	    }
	    public float getMiddleWidthRatio(){
	        return this.MiddleWidthRatio;
	    }
	    public void setXSplitNumber(float XSplitNumber){
	        this.XSplitNumber = XSplitNumber;
	    }
	    public float getXSplitNumber(){
	        return this.XSplitNumber;
	    }
	    public void setFlowingWellFluidLevel(float FlowingWellFluidLevel){
	        this.FlowingWellFluidLevel = FlowingWellFluidLevel;
	    }
	    public float getFlowingWellFluidLevel(){
	        return this.FlowingWellFluidLevel;
	    }
	    public void setNarrowCoefficient(float NarrowCoefficient){
	        this.NarrowCoefficient = NarrowCoefficient;
	    }
	    public float getNarrowCoefficient(){
	        return this.NarrowCoefficient;
	    }
	    public void setBlockageCoefficient(float BlockageCoefficient){
	        this.BlockageCoefficient = BlockageCoefficient;
	    }
	    public float getBlockageCoefficient(){
	        return this.BlockageCoefficient;
	    }
	    public void setClosedAreaRatio1(float ClosedAreaRatio1){
	        this.ClosedAreaRatio1 = ClosedAreaRatio1;
	    }
	    public float getClosedAreaRatio1(){
	        return this.ClosedAreaRatio1;
	    }
	    public void setClosedAreaRatio2(float ClosedAreaRatio2){
	        this.ClosedAreaRatio2 = ClosedAreaRatio2;
	    }
	    public float getClosedAreaRatio2(){
	        return this.ClosedAreaRatio2;
	    }
	    public void setUpperLowerCoefficient(float UpperLowerCoefficient){
	        this.UpperLowerCoefficient = UpperLowerCoefficient;
	    }
	    public float getUpperLowerCoefficient(){
	        return this.UpperLowerCoefficient;
	    }
	    public void setSandParameter1(float SandParameter1){
	        this.SandParameter1 = SandParameter1;
	    }
	    public float getSandParameter1(){
	        return this.SandParameter1;
	    }
	    public void setSandParameter2(float SandParameter2){
	        this.SandParameter2 = SandParameter2;
	    }
	    public float getSandParameter2(){
	        return this.SandParameter2;
	    }
	    public void setTopLeakCoefficient(float TopLeakCoefficient){
	        this.TopLeakCoefficient = TopLeakCoefficient;
	    }
	    public float getTopLeakCoefficient(){
	        return this.TopLeakCoefficient;
	    }
	    public void setBottomLeakCoefficient(float BottomLeakCoefficient){
	        this.BottomLeakCoefficient = BottomLeakCoefficient;
	    }
	    public float getBottomLeakCoefficient(){
	        return this.BottomLeakCoefficient;
	    }
	    public void setTopLeftK(float TopLeftK){
	        this.TopLeftK = TopLeftK;
	    }
	    public float getTopLeftK(){
	        return this.TopLeftK;
	    }
	    public void setTopRightK(float TopRightK){
	        this.TopRightK = TopRightK;
	    }
	    public float getTopRightK(){
	        return this.TopRightK;
	    }
	    public void setBottomLeftK(float BottomLeftK){
	        this.BottomLeftK = BottomLeftK;
	    }
	    public float getBottomLeftK(){
	        return this.BottomLeftK;
	    }
	    public void setBottomRightK(float BottomRightK){
	        this.BottomRightK = BottomRightK;
	    }
	    public float getBottomRightK(){
	        return this.BottomRightK;
	    }
	    public void setDeflectionDegree(float DeflectionDegree){
	        this.DeflectionDegree = DeflectionDegree;
	    }
	    public float getDeflectionDegree(){
	        return this.DeflectionDegree;
	    }
	    public void setWaxCoefficient1(float WaxCoefficient1){
	        this.WaxCoefficient1 = WaxCoefficient1;
	    }
	    public float getWaxCoefficient1(){
	        return this.WaxCoefficient1;
	    }
	    public void setWaxCoefficient2(float WaxCoefficient2){
	        this.WaxCoefficient2 = WaxCoefficient2;
	    }
	    public float getWaxCoefficient2(){
	        return this.WaxCoefficient2;
	    }
	    public void setFullnessCoefficient1(float FullnessCoefficient1){
	        this.FullnessCoefficient1 = FullnessCoefficient1;
	    }
	    public float getFullnessCoefficient1(){
	        return this.FullnessCoefficient1;
	    }
	    public void setFullnessCoefficient2(float FullnessCoefficient2){
	        this.FullnessCoefficient2 = FullnessCoefficient2;
	    }
	    public float getFullnessCoefficient2(){
	        return this.FullnessCoefficient2;
	    }
	    public void setFullnessCoefficient3(float FullnessCoefficient3){
	        this.FullnessCoefficient3 = FullnessCoefficient3;
	    }
	    public float getFullnessCoefficient3(){
	        return this.FullnessCoefficient3;
	    }
	    public void setFullnessCoefficient4(float FullnessCoefficient4){
	        this.FullnessCoefficient4 = FullnessCoefficient4;
	    }
	    public float getFullnessCoefficient4(){
	        return this.FullnessCoefficient4;
	    }
	    public void setNormalSubmergence(float NormalSubmergence){
	        this.NormalSubmergence = NormalSubmergence;
	    }
	    public float getNormalSubmergence(){
	        return this.NormalSubmergence;
	    }
	    public void setPumpOffSubmergence(float PumpOffSubmergence){
	        this.PumpOffSubmergence = PumpOffSubmergence;
	    }
	    public float getPumpOffSubmergence(){
	        return this.PumpOffSubmergence;
	    }
	    public void setIntakeBlockingSubmergence(float IntakeBlockingSubmergence){
	        this.IntakeBlockingSubmergence = IntakeBlockingSubmergence;
	    }
	    public float getIntakeBlockingSubmergence(){
	        return this.IntakeBlockingSubmergence;
	    }
	    public void setProlapseCoefficient(float ProlapseCoefficient){
	        this.ProlapseCoefficient = ProlapseCoefficient;
	    }
	    public float getProlapseCoefficient(){
	        return this.ProlapseCoefficient;
	    }
	    public void setCheckRangeCoefficient(float CheckRangeCoefficient){
	        this.CheckRangeCoefficient = CheckRangeCoefficient;
	    }
	    public float getCheckRangeCoefficient(){
	        return this.CheckRangeCoefficient;
	    }
	}
	
	public static class SystemEfficiency implements Serializable {
		private static final long serialVersionUID = 1L;
	    private float MotorEfficiency;

	    private float BeltEfficiency;

	    private float GearReducerEfficiency;

	    private float FourBarLinkageEfficiency;

	    private float MotorInputWatt;

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
	    public void setMotorInputWatt(float MotorInputWatt){
	        this.MotorInputWatt = MotorInputWatt;
	    }
	    public float getMotorInputWatt(){
	        return this.MotorInputWatt;
	    }
	}

	public static class ManualIntervention implements Serializable {
		private static final long serialVersionUID = 1L;
	    private int Code;
	    
	    private float NetGrossRatio;
	    
	    private float NetGrossValue;

	    public void setCode(int Code){
	        this.Code = Code;
	    }
	    public int getCode(){
	        return this.Code;
	    }
	    public void setNetGrossRatio(float NetGrossRatio){
	        this.NetGrossRatio = NetGrossRatio;
	    }
	    public float getNetGrossRatio(){
	        return this.NetGrossRatio;
	    }
		public float getNetGrossValue() {
			return NetGrossValue;
		}
		public void setNetGrossValue(float netGrossValue) {
			NetGrossValue = netGrossValue;
		}
	}

}
