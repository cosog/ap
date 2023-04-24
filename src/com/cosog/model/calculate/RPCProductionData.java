package com.cosog.model.calculate;

import java.util.List;

public class RPCProductionData {
	
	private String WellName;

    private FluidPVT FluidPVT;

    private Reservoir Reservoir;

    private RodString RodString;

    private TubingString TubingString;

    private Pump Pump;

    private CasingString CasingString;

    private PumpingUnit PumpingUnit;

    private Production Production;

    private ManualIntervention ManualIntervention;

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
    
    public void setManualIntervention(ManualIntervention ManualIntervention){
        this.ManualIntervention = ManualIntervention;
    }
    public ManualIntervention getManualIntervention(){
        return this.ManualIntervention;
    }

	public static class FluidPVT
	{
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
	
	public static class Reservoir
	{
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
	
	public static class EveryRod
	{
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
	
	public static class RodString
	{
	    private List<EveryRod> EveryRod;

	    public void setEveryRod(List<EveryRod> EveryRod){
	        this.EveryRod = EveryRod;
	    }
	    public List<EveryRod> getEveryRod(){
	        return this.EveryRod;
	    }
	}
	
	public static class EveryTubing
	{
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
	
	public static class TubingString
	{
	    private List<EveryTubing> EveryTubing;

	    public void setEveryTubing(List<EveryTubing> EveryTubing){
	        this.EveryTubing = EveryTubing;
	    }
	    public List<EveryTubing> getEveryTubing(){
	        return this.EveryTubing;
	    }
	}
	
	public static class Pump
	{
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
	
	public static class EveryCasing
	{
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
	
	public static class CasingString
	{
	    private List<EveryCasing> EveryCasing;

	    public void setEveryCasing(List<EveryCasing> EveryCasing){
	        this.EveryCasing = EveryCasing;
	    }
	    public List<EveryCasing> getEveryCasing(){
	        return this.EveryCasing;
	    }
	}
	
	public static class EveryBalance
	{
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
	
	public static class Motor
	{
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

	    private Motor Motor;

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
	}

	public static class Production
	{
	    private float WaterCut;
	    
	    private float WeightWaterCut;

	    private float ProductionGasOilRatio;

	    private float TubingPressure;

	    private float CasingPressure;

	    private float WellHeadTemperature;

	    private float ProducingfluidLevel;

	    private float PumpSettingDepth;

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
		public float getWeightWaterCut() {
			return WeightWaterCut;
		}
		public void setWeightWaterCut(float weightWaterCut) {
			WeightWaterCut = weightWaterCut;
		}
	}

	public static class ManualIntervention
	{
	    private int Code;

	    private float NetGrossRatio;
	    
	    private float NetGrossValue;
	    
	    private float LevelCorrectValue;

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
		public float getLevelCorrectValue() {
			return LevelCorrectValue;
		}
		public void setLevelCorrectValue(float levelCorrectValue) {
			LevelCorrectValue = levelCorrectValue;
		}
	}

}
