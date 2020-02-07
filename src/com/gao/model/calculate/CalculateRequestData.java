package com.gao.model.calculate;

import java.util.ArrayList;
import java.util.List;
public class CalculateRequestData {
	
	private String AKString;                                              //秘钥
	private String WellName;                                              //井名
	private int LiftingType;											  //举升类型
	private String AcquisitionTime;                                       //采集时间
	private float RPM;                                                    //螺杆泵转速
	private FluidPVT FluidPVT;                                            //流体PVT物性
	private Reservoir Reservoir;                                          //油藏物性
	private WellboreTrajectory WellboreTrajectory;                        //井深轨迹
	private RodString RodString;                                          //抽油杆参数
	private TubingString TubingString;                                    //油管参数
	private Pump Pump;                                                    //抽油泵参数
	private TailTubingString TailTubingString;		                      //尾管参数
	private CasingString CasingString;                                    //套管参数
	private ProductionParameter ProductionParameter;                      //生产数据
	private FSDiagram FSDiagram;                                          //功图数据
	private SystemEfficiency SystemEfficiency;                            //系统效率
	private ManualIntervention ManualIntervention;                        //人工干预
	
	public CalculateRequestData(String aKString, String WellName,
			com.gao.model.calculate.CalculateRequestData.FluidPVT fluidPVT,
			com.gao.model.calculate.CalculateRequestData.Reservoir reservoir,
			com.gao.model.calculate.CalculateRequestData.WellboreTrajectory wellboreTrajectory,
			com.gao.model.calculate.CalculateRequestData.RodString rodString,
			com.gao.model.calculate.CalculateRequestData.TubingString tubingString,
			com.gao.model.calculate.CalculateRequestData.Pump pump,
			com.gao.model.calculate.CalculateRequestData.TailTubingString tailTubingString,
			com.gao.model.calculate.CalculateRequestData.CasingString casingString,
			com.gao.model.calculate.CalculateRequestData.ProductionParameter productionParameter,
			com.gao.model.calculate.CalculateRequestData.FSDiagram fSDiagram,
			com.gao.model.calculate.CalculateRequestData.SystemEfficiency systemEfficiency,
			com.gao.model.calculate.CalculateRequestData.ManualIntervention manualIntervention) {
		super();
		AKString = aKString;
		this.WellName = WellName;
		FluidPVT = fluidPVT;
		Reservoir = reservoir;
		WellboreTrajectory = wellboreTrajectory;
		RodString = rodString;
		TubingString = tubingString;
		Pump = pump;
		TailTubingString = tailTubingString;
		CasingString = casingString;
		ProductionParameter = productionParameter;
		FSDiagram = fSDiagram;
		SystemEfficiency=systemEfficiency;
		ManualIntervention = manualIntervention;
	}


	public String getAKString() {
		return AKString;
	}


	public void setAKString(String aKString) {
		AKString = aKString;
	}


	public CalculateRequestData() {
		super();
	}


	public String getWellName() {
		return WellName;
	}


	public void setWellName(String WellName) {
		this.WellName = WellName;
	}


	public FluidPVT getFluidPVT() {
		return FluidPVT;
	}


	public void setFluidPVT(FluidPVT fluidPVT) {
		FluidPVT = fluidPVT;
	}


	public Reservoir getReservoir() {
		return Reservoir;
	}


	public void setReservoir(Reservoir reservoir) {
		Reservoir = reservoir;
	}


	public WellboreTrajectory getWellboreTrajectory() {
		return WellboreTrajectory;
	}


	public void setWellboreTrajectory(WellboreTrajectory wellboreTrajectory) {
		WellboreTrajectory = wellboreTrajectory;
	}


	public RodString getRodString() {
		return RodString;
	}


	public void setRodString(RodString rodString) {
		RodString = rodString;
	}


	public TubingString getTubingString() {
		return TubingString;
	}


	public void setTubingString(TubingString tubingString) {
		TubingString = tubingString;
	}


	public Pump getPump() {
		return Pump;
	}


	public void setPump(Pump pump) {
		Pump = pump;
	}


	public TailTubingString getTailTubingString() {
		return TailTubingString;
	}


	public void setTailTubingString(TailTubingString tailTubingString) {
		TailTubingString = tailTubingString;
	}


	public CasingString getCasingString() {
		return CasingString;
	}


	public void setCasingString(CasingString casingString) {
		CasingString = casingString;
	}


	public ProductionParameter getProductionParameter() {
		return ProductionParameter;
	}


	public void setProductionParameter(ProductionParameter productionParameter) {
		ProductionParameter = productionParameter;
	}


	public FSDiagram getFSDiagram() {
		return FSDiagram;
	}


	public void setFSDiagram(FSDiagram fSDiagram) {
		FSDiagram = fSDiagram;
	}
	
	public SystemEfficiency getSystemEfficiency() {
		return SystemEfficiency;
	}


	public void setSystemEfficiency(SystemEfficiency systemEfficiency) {
		SystemEfficiency = systemEfficiency;
	}

	public ManualIntervention getManualIntervention() {
		return ManualIntervention;
	}

	public void setManualIntervention(ManualIntervention manualIntervention) {
		ManualIntervention = manualIntervention;
	}



			public static class FluidPVT
			{
			    private float CrudeOilDensity;                             //原油密度

			    private float WaterDensity;                                //水密度

			    private float NaturalGasRelativeDensity;                   //天然气相对密度

			    private float SaturationPressure;                          //饱和压力

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
			    private float Pressure;      //油藏压力
			    private float Depth;         //油藏深度
			    private float Temperature;   //油藏温度

			    public void setPressure(float Pressure){
			        this.Pressure = Pressure;
			    }
			    public float getPressure(){
			        return this.Pressure;
			    }
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

			
			public static class WellboreTrajectory
			{
			    private List<Float> MeasuringDepth;    //测量深度
			    private List<Float> VerticalDepth;     //垂直深度
			    private List<Float> DeviationAngle;    //井斜角
			    private List<Float> AzimuthAngle;      //方位角
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
			}

			
			public static class EveryRod
			{
			    private int Type;                 //抽油杆类型
			    private String Grade;                //杆级别 A，B，C，K，D，KD，HL，HY（直接输入杆级别）
			    private float Length;            //杆长
			    private float OutsideDiameter;   //杆外径
			    private float InsideDiameter;    //杆内径
			    private float Density;           //杆密度
			    private float WeightPerMeter;    //每米杆重
			    
			    public float getWeightPerMeter() {
					return WeightPerMeter;
				}
				public void setWeightPerMeter(float weightPerMeter) {
					WeightPerMeter = weightPerMeter;
				}
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
			    private ArrayList<EveryRod> EveryRod;       //抽油杆数据

			    public void setEveryRod(ArrayList<EveryRod> EveryRod){
			        this.EveryRod = EveryRod;
			    }
			    public List<EveryRod> getEveryRod(){
			        return this.EveryRod;
			    }
			}

			
			public static class EveryTubing
			{
				private String Grade;//H，J，K，N，L，C，T，P，Q
			    private float length;//油管长度
			    private float OutsideDiameter;//油管外径
			    private float InsideDiameter;//油管内径
			    private float Density;//油管密度
			    private float WeightPerMeter;//每米管重

			    public String getGrade() {
					return Grade;
				}
				public void setGrade(String grade) {
					Grade = grade;
				}
				public float getWeightPerMeter() {
					return WeightPerMeter;
				}
				public void setWeightPerMeter(float weightPerMeter) {
					WeightPerMeter = weightPerMeter;
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
			}

			
			public static class TubingString
			{
			    private ArrayList<EveryTubing> EveryTubing;

			    public void setEveryTubing(ArrayList<EveryTubing> EveryTubing){
			        this.EveryTubing = EveryTubing;
			    }
			    public List<EveryTubing> getEveryTubing(){
			        return this.EveryTubing;
			    }
			}

			
			public static class Pump
			{
			    private String PumpType;//泵类型 R－杆式泵  T—管式泵
			    
			    private String BarrelType;//泵筒类型  H-厚壁筒，用于金属柱塞；W-薄壁筒，用于金属柱塞；L-组合泵筒，用于金属柱塞；P-厚壁筒，用于软密封柱塞 ；S-薄壁筒，用于软密封柱塞;X-厚壁筒，用于金属柱塞，薄壁形螺纹构形； 一般为组合泵筒

			    private int PumpGrade;//泵级别

			    private float PlungerLength; //柱塞长

			    private float PumpBoreDiameter;//泵径
			    
			    private float Clearance;//柱塞与缸套配合单边间隙  组合泵间隙，默认按1级间隙 ;整筒泵间隙，默认按2级间隙

			    private float AntiImpactStroke;//防冲距  默认值取0.1
			    
			    private float BarrelLength;//泵筒长
			    
			    private int BarrelSeries;//螺杆泵泵级数
			    
			    private float RotorDiameter;//螺杆泵转子直径
			    
			    private float QPR;//螺杆泵公称排量

			    public String getBarrelType() {
					return BarrelType;
				}
				public void setBarrelType(String barrelType) {
					BarrelType = barrelType;
				}
				public float getBarrelLength() {
					return BarrelLength;
				}
				public void setBarrelLength(float barrelLength) {
					BarrelLength = barrelLength;
				}
				public float getClearance() {
					return Clearance;
				}
				public void setClearance(float clearance) {
					Clearance = clearance;
				}
				public void setPumpType(String PumpType){
			        this.PumpType = PumpType;
			    }
			    public String getPumpType(){
			        return this.PumpType;
			    }
			    public void setPumpGrade(int PumpGrade){
			        this.PumpGrade = PumpGrade;
			    }
			    public int getPumpGrade(){
			        return this.PumpGrade;
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
			    public void setAntiImpactStroke(float AntiImpactStroke){
			        this.AntiImpactStroke = AntiImpactStroke;
			    }
			    public float getAntiImpactStroke(){
			        return this.AntiImpactStroke;
			    }
				public int getBarrelSeries() {
					return BarrelSeries;
				}
				public void setBarrelSeries(int barrelSeries) {
					BarrelSeries = barrelSeries;
				}
				public float getRotorDiameter() {
					return RotorDiameter;
				}
				public void setRotorDiameter(float rotorDiameter) {
					RotorDiameter = rotorDiameter;
				}
				public float getQPR() {
					return QPR;
				}
				public void setQPR(float qPR) {
					QPR = qPR;
				}
			}

			
			public static class EveryEquipment
			{
			    private int EquipmentType; //设备类型 1-尾管，2-滤管，3-锚定器，4-油气分离器

			    private int Grade;//级别  1-H，2-J，3-K，4-N，5-L，6-C，7-T，8-P，9-Q

			    private float Length;//尾管长度

			    private float OutsideDiameter;//尾管外径

			    private float InsideDiameter;//尾管内径

			    private float Density;//尾管密度
			    
			    private float WeightPerMeter;//每米管重

			    private float GasAnchorEfficiency;//气锚效率  无气锚填0

			    public float getWeightPerMeter() {
					return WeightPerMeter;
				}
				public void setWeightPerMeter(float weightPerMeter) {
					WeightPerMeter = weightPerMeter;
				}
				public void setEquipmentType(int EquipmentType){
			        this.EquipmentType = EquipmentType;
			    }
			    public int getEquipmentType(){
			        return this.EquipmentType;
			    }
			    public void setGrade(int Grade){
			        this.Grade = Grade;
			    }
			    public int getGrade(){
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
			    public void setGasAnchorEfficiency(float GasAnchorEfficiency){
			        this.GasAnchorEfficiency = GasAnchorEfficiency;
			    }
			    public float getGasAnchorEfficiency(){
			        return this.GasAnchorEfficiency;
			    }
			}

			
			public static class TailTubingString
			{
			    private ArrayList<EveryEquipment> EveryEquipment;

			    public void setEveryEquipment(ArrayList<EveryEquipment> EveryEquipment){
			        this.EveryEquipment = EveryEquipment;
			    }
			    public List<EveryEquipment> getEveryEquipment(){
			        return this.EveryEquipment;
			    }
			}

			
			public static class EveryCasing
			{
			    private float OutsideDiameter;//套管外径

			    private float InsideDiameter;//套管内径

			    private float Length;//套管长度

			    private float Density;//套管密度
			    
			    private float WeightPerMeter;//每米管重

			    public float getWeightPerMeter() {
					return WeightPerMeter;
				}
				public void setWeightPerMeter(float weightPerMeter) {
					WeightPerMeter = weightPerMeter;
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
			}

			
			public static class CasingString
			{
			    private ArrayList<EveryCasing> EveryCasing;

			    public void setEveryCasing(ArrayList<EveryCasing> EveryCasing){
			        this.EveryCasing = EveryCasing;
			    }
			    public List<EveryCasing> getEveryCasing(){
			        return this.EveryCasing;
			    }
			}

			
			public static class ProductionParameter
			{
			    private float WaterCut;//体积含水率

			    private float ProductionGasOilRatio;//生产气油比

			    private float TubingPressure;//油压

			    private float CasingPressure;//套压

			    private float WellHeadFluidTemperature;//井口流温

			    private float ProducingfluidLevel;//动液面

			    private float PumpSettingDepth;//泵挂
			    
			    private float Submergence;//沉没度

			    public float getSubmergence() {
					return Submergence;
				}
				public void setSubmergence(float submergence) {
					Submergence = submergence;
				}
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
			}

			
			public static class ManualIntervention
			{
			    private int Code;

			    private float NetGrossRatio;

				public int getDCode() {
					return Code;
				}

				public void setCode(int code) {
					Code = code;
				}

				public float getNetGrossRatio() {
					return NetGrossRatio;
				}

				public void setNetGrossRatio(float netGrossRatio) {
					NetGrossRatio = netGrossRatio;
				}

			   
			}

			
			public static class FSDiagram
			{
			    private String AcquisitionTime;

			    private float Stroke;

			    private float SPM;

			    private List<List<Float>> F;

			    private List<List<Float>> S;
			    
			    private List<Float> P;

			    private List<Float> A;
			    
			    public Analysis getAnalysis() {
					return Analysis;
				}
				public void setAnalysis(Analysis analysis) {
					Analysis = analysis;
				}
				private Analysis Analysis;        //诊断计算分析参数

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
			    public void setF(List<List<Float>> F){
			        this.F = F;
			    }
			    public List<List<Float>> getF(){
			        return this.F;
			    }
			    public void setS(List<List<Float>> S){
			        this.S = S;
			    }
			    public List<List<Float>> getS(){
			        return this.S;
			    }
				public String getAcquisitionTime() {
					return AcquisitionTime;
				}
				public void setAcquisitionTime(String acquisitionTime) {
					AcquisitionTime = acquisitionTime;
				}
				public List<Float> getP() {
					return P;
				}
				public void setP(List<Float> p) {
					P = p;
				}
				public List<Float> getA() {
					return A;
				}
				public void setA(List<Float> a) {
					A = a;
				}
			}

			
			public static class AnalysisParameter
			{
			    private float ClosedGraphNumber;

			    private float BandCoefficient;

			    private float ApproximationCoefficient;

			    private float MiddleWidthRatio;

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
			
			public static class Analysis{
				private AnalysisParameter AnalysisParameter;

				public AnalysisParameter getAnalysisParameter() {
					return AnalysisParameter;
				}

				public void setAnalysisParameter(AnalysisParameter analysisParameter) {
					AnalysisParameter = analysisParameter;
				}
				
			}
			
			public static class SystemEfficiency
			{
			    private float MotorEfficiency;

			    private float BeltEfficiency;

			    private float GearReducerEfficiency;

			    private float FourBarLinkageEfficiency;

			    private float K;

			    private float WellheadEfficiency;

			    private float RodEfficiency;

			    private float TubingEfficiency;

			    private float SurfaceSystemEfficiency;

			    private float WellDownSystemEfficiency;

			    private float SystemEfficiency;

			    private float MotorInputActivePower;

			    private float MotorInputReactivePower;

			    private float MotorInputApparentPower;

			    private float MotorOutputPower;

			    private float GearReducerInputPower;

			    private float GearReducerOutputPower;

			    private float PolishRodPower;

			    private float RodOutputPower;

			    private float PumpPower;

			    private float TubingPower;

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
			    public void setK(float K){
			        this.K = K;
			    }
			    public float getK(){
			        return this.K;
			    }
			    public void setWellheadEfficiency(float WellheadEfficiency){
			        this.WellheadEfficiency = WellheadEfficiency;
			    }
			    public float getWellheadEfficiency(){
			        return this.WellheadEfficiency;
			    }
			    public void setRodEfficiency(float RodEfficiency){
			        this.RodEfficiency = RodEfficiency;
			    }
			    public float getRodEfficiency(){
			        return this.RodEfficiency;
			    }
			    public void setTubingEfficiency(float TubingEfficiency){
			        this.TubingEfficiency = TubingEfficiency;
			    }
			    public float getTubingEfficiency(){
			        return this.TubingEfficiency;
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
			    public void setMotorInputActivePower(float MotorInputActivePower){
			        this.MotorInputActivePower = MotorInputActivePower;
			    }
			    public float getMotorInputActivePower(){
			        return this.MotorInputActivePower;
			    }
			    public void setMotorInputReactivePower(float MotorInputReactivePower){
			        this.MotorInputReactivePower = MotorInputReactivePower;
			    }
			    public float getMotorInputReactivePower(){
			        return this.MotorInputReactivePower;
			    }
			    public void setMotorInputApparentPower(float MotorInputApparentPower){
			        this.MotorInputApparentPower = MotorInputApparentPower;
			    }
			    public float getMotorInputApparentPower(){
			        return this.MotorInputApparentPower;
			    }
			    public void setMotorOutputPower(float MotorOutputPower){
			        this.MotorOutputPower = MotorOutputPower;
			    }
			    public float getMotorOutputPower(){
			        return this.MotorOutputPower;
			    }
			    public void setGearReducerInputPower(float GearReducerInputPower){
			        this.GearReducerInputPower = GearReducerInputPower;
			    }
			    public float getGearReducerInputPower(){
			        return this.GearReducerInputPower;
			    }
			    public void setGearReducerOutputPower(float GearReducerOutputPower){
			        this.GearReducerOutputPower = GearReducerOutputPower;
			    }
			    public float getGearReducerOutputPower(){
			        return this.GearReducerOutputPower;
			    }
			    public void setPolishRodPower(float PolishRodPower){
			        this.PolishRodPower = PolishRodPower;
			    }
			    public float getPolishRodPower(){
			        return this.PolishRodPower;
			    }
			    public void setRodOutputPower(float RodOutputPower){
			        this.RodOutputPower = RodOutputPower;
			    }
			    public float getRodOutputPower(){
			        return this.RodOutputPower;
			    }
			    public void setPumpPower(float PumpPower){
			        this.PumpPower = PumpPower;
			    }
			    public float getPumpPower(){
			        return this.PumpPower;
			    }
			    public void setTubingPower(float TubingPower){
			        this.TubingPower = TubingPower;
			    }
			    public float getTubingPower(){
			        return this.TubingPower;
			    }
			    public void setWaterPower(float WaterPower){
			        this.WaterPower = WaterPower;
			    }
			    public float getWaterPower(){
			        return this.WaterPower;
			    }
			}

			public int getLiftingType() {
				return LiftingType;
			}


			public void setLiftingType(int liftingType) {
				LiftingType = liftingType;
			}


			public String getAcquisitionTime() {
				return AcquisitionTime;
			}


			public void setAcquisitionTime(String acquisitionTime) {
				AcquisitionTime = acquisitionTime;
			}


			public float getRPM() {
				return RPM;
			}


			public void setRPM(float rPM) {
				RPM = rPM;
			}
}
