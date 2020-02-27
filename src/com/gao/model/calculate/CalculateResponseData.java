package com.gao.model.calculate;

import java.util.List;
public  class CalculateResponseData {

	private String WellName;
	private String AcquisitionTime;
	private float RPM;
	private int RunStatus;
	
	private RodString RodString;
	private ProductionParameter ProductionParameter;
	private FSDiagram FSDiagram;
	private PumpEfficiency PumpEfficiency;
	private SystemEfficiency SystemEfficiency;
	private Verification Verification;
	private CalculationStatus CalculationStatus;
	
	public String getWellName() {
		return WellName;
	}

	public String getRodCalData(){
		StringBuffer result=new StringBuffer();
		if(this!=null&&this.getCalculationStatus().getResultStatus()==1){
			result.append(this.getRodString().CNT+","+this.getRodString().getLengthAll()+","+this.getRodString().getWeightAll()+","+this.getRodString().getBuoyancyForceAll());
			for(int i=0;i<this.getRodString().getEveryRod().size();i++){
				result.append(";");
				result.append(this.getFSDiagram().getFMax().get(i)+",");
				result.append(this.getFSDiagram().getFMin().get(i)+",");
				result.append(this.getRodString().getEveryRod().get(i).getMaxStress()+",");
				result.append(this.getRodString().getEveryRod().get(i).getMinStress()+",");
				result.append(this.getRodString().getEveryRod().get(i).getAllowableStress()+",");
				result.append(this.getRodString().getEveryRod().get(i).getStressRatio());
			}
		}
		return result.toString();
	}

	public void setWellName(String wellName) {
		WellName = wellName;
	}


	public RodString getRodString() {
		return RodString;
	}


	public void setRodString(RodString rodString) {
		RodString = rodString;
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


	public PumpEfficiency getPumpEfficiency() {
		return PumpEfficiency;
	}


	public void setPumpEfficiency(PumpEfficiency pumpEfficiency) {
		PumpEfficiency = pumpEfficiency;
	}


	public SystemEfficiency getSystemEfficiency() {
		return SystemEfficiency;
	}


	public void setSystemEfficiency(SystemEfficiency systemEfficiency) {
		SystemEfficiency = systemEfficiency;
	}


	public Verification getVerification() {
		return Verification;
	}


	public void setVerification(Verification verification) {
		Verification = verification;
	}


	public CalculationStatus getCalculationStatus() {
		return CalculationStatus;
	}


	public void setCalculationStatus(CalculationStatus calculationStatus) {
		CalculationStatus = calculationStatus;
	}


			public  static class EveryRod
			{
			    private int Type;

			    private String Grade;

			    private float Length;

			    private float OutsideDiameter;

			    private float InsideDiameter;

			    private float Area;

			    private float Weight;

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

			
			public static class RodString
			{
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
			    public void setEveryRod(List<EveryRod> EveryRod){
			        this.EveryRod = EveryRod;
			    }
			    public List<EveryRod> getEveryRod(){
			        return this.EveryRod;
			    }
				public String getLengthString() {
					return LengthString;
				}
				public void setLengthString(String lengthString) {
					LengthString = lengthString;
				}
				public String getGradeString() {
					return GradeString;
				}
				public void setGradeString(String gradeString) {
					GradeString = gradeString;
				}
				public String getOutsideDiameterString() {
					return OutsideDiameterString;
				}
				public void setOutsideDiameterString(String outsideDiameterString) {
					OutsideDiameterString = outsideDiameterString;
				}
				public String getInsideDiameterString() {
					return InsideDiameterString;
				}
				public void setInsideDiameterString(String insideDiameterString) {
					InsideDiameterString = insideDiameterString;
				}
			}

			
			public static class ProductionParameter
			{
			    private float WaterCut;

			    private float ProductionGasOilRatio;

			    private float TubingPressure;

			    private float CasingPressure;
			    
			    private float BackPressure;

			    private float WellHeadFluidTemperature;

			    private float ProducingfluidLevel;

			    private float PumpSettingDepth;

			    private float NetGrossRatio;
			    
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
			    public void setNetGrossRatio(float NetGrossRatio){
			        this.NetGrossRatio = NetGrossRatio;
			    }
			    public float getNetGrossRatio(){
			        return this.NetGrossRatio;
			    }
			    public float getSubmergence() {
					return Submergence;
				}
				public void setSubmergence(float submergence) {
					Submergence = submergence;
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
				public float getTheoreticalProduction() {
					return TheoreticalProduction;
				}
				public void setTheoreticalProduction(float theoreticalProduction) {
					TheoreticalProduction = theoreticalProduction;
				}
				public float getBackPressure() {
					return BackPressure;
				}
				public void setBackPressure(float backPressure) {
					BackPressure = backPressure;
				}
			}

			
			public static class FSDiagram
			{
			    private String AcquisitionTime;

			    private float Stroke;

			    private float SPM;
			    
			    private int CNT;

				private int SCNT;
			    
			    private int FCNT;
			    
			    private int WattCNT;
			    
			    private int ICNT;
			    
			    private float Area;

			    private float UpperLoadLine;
			    
			    private float UpperLoadLineOfExact;

			    private float LowerLoadLine;

			    private float FullnessCoefficient;

			    private float PlungerStroke;

			    private float AvailablePlungerStroke;
			    
			    private float UpStrokeWattMax;
			    
			    private float DownStrokeWattMax;
			    
			    private float WattDegreeBalance;
			    
			    private String WattMaxRatioString;
			    
			    private float AverageWatt;
			    
			    private float UpStrokeIMax;
			    
			    private float DownStrokeIMax;
			    
			    private float IDegreeBalance;
			    
			    private String IMaxRatioString;

			    private List<List<Float>> F;

			    private List<List<Float>> S;

			    private List<Float> FMax;

			    private List<Float> FMin;
			    
			    private List<Float> Watt;

			    private List<Float> I;

				public String getAcquisitionTime() {
					return AcquisitionTime;
				}

				public void setAcquisitionTime(String acquisitionTime) {
					AcquisitionTime = acquisitionTime;
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
				
				public int getCNT() {
					return CNT;
				}

				public void setCNT(int cNT) {
					CNT = cNT;
				}

				public int getSCNT() {
					return SCNT;
				}

				public void setSCNT(int sCNT) {
					SCNT = sCNT;
				}

				public int getFCNT() {
					return FCNT;
				}

				public void setFCNT(int fCNT) {
					FCNT = fCNT;
				}

				public int getWattCNT() {
					return WattCNT;
				}

				public void setWattCNT(int wattCNT) {
					WattCNT = wattCNT;
				}

				public int getICNT() {
					return ICNT;
				}

				public void setICNT(int iCNT) {
					ICNT = iCNT;
				}

				public float getArea() {
					return Area;
				}

				public void setArea(float area) {
					Area = area;
				}

				public float getUpperLoadLine() {
					return UpperLoadLine;
				}

				public void setUpperLoadLine(float upperLoadLine) {
					UpperLoadLine = upperLoadLine;
				}

				public float getUpperLoadLineOfExact() {
					return UpperLoadLineOfExact;
				}

				public void setUpperLoadLineOfExact(float upperLoadLineOfExact) {
					UpperLoadLineOfExact = upperLoadLineOfExact;
				}

				public float getLowerLoadLine() {
					return LowerLoadLine;
				}

				public void setLowerLoadLine(float lowerLoadLine) {
					LowerLoadLine = lowerLoadLine;
				}

				public float getFullnessCoefficient() {
					return FullnessCoefficient;
				}

				public void setFullnessCoefficient(float fullnessCoefficient) {
					FullnessCoefficient = fullnessCoefficient;
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

				public String getWattMaxRatioString() {
					return WattMaxRatioString;
				}

				public void setWattMaxRatioString(String wattMaxRatioString) {
					WattMaxRatioString = wattMaxRatioString;
				}

				public float getAverageWatt() {
					return AverageWatt;
				}

				public void setAverageWatt(float averageWatt) {
					AverageWatt = averageWatt;
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

				public String getIMaxRatioString() {
					return IMaxRatioString;
				}

				public void setIMaxRatioString(String iMaxRatioString) {
					IMaxRatioString = iMaxRatioString;
				}

				public List<List<Float>> getF() {
					return F;
				}

				public void setF(List<List<Float>> f) {
					F = f;
				}

				public List<List<Float>> getS() {
					return S;
				}

				public void setS(List<List<Float>> s) {
					S = s;
				}

				public List<Float> getFMax() {
					return FMax;
				}

				public void setFMax(List<Float> fMax) {
					FMax = fMax;
				}

				public List<Float> getFMin() {
					return FMin;
				}

				public void setFMin(List<Float> fMin) {
					FMin = fMin;
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
			}

			
			public static class PumpEfficiency
			{
			    private float RodFlexLength;

			    private float TubingFlexLength;

			    private float InertiaLength;

			    private float PumpEff1;

			    private float PumpEff2;

			    private float PumpEff3;

			    private float PumpEff4;

			    private float PumpEff;

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
			    public void setPumpEff1(float PumpEff1){
			        this.PumpEff1 = PumpEff1;
			    }
			    public float getPumpEff1(){
			        return this.PumpEff1;
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

			
			public static class SystemEfficiency
			{
			    private float SurfaceSystemEfficiency;

			    private float WellDownSystemEfficiency;

			    private float SystemEfficiency;

			    private float MotorInputActivePower;

			    private float PolishRodPower;

			    private float WaterPower;
			    
			    private float PowerConsumptionPerTHM;

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
				public float getPowerConsumptionPerTHM() {
					return PowerConsumptionPerTHM;
				}
				public void setPowerConsumptionPerTHM(float powerConsumptionPerTHM) {
					PowerConsumptionPerTHM = powerConsumptionPerTHM;
				}
			}

			
			public static class Verification
			{
			    private int WarningCounter;

			    private String WarningString;

			    private int ErrorCounter;

			    private String ErrorString;

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
			}

			
			public static class CalculationStatus
			{
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


			public int getRunStatus() {
				return RunStatus;
			}


			public void setRunStatus(int runStatus) {
				RunStatus = runStatus;
			}

}
