package com.cosog.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cosog.model.calculate.AppRunStatusProbeResonanceData;
import com.cosog.model.calculate.CommResponseData;
import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.DiskProbeResponseData;
import com.cosog.model.calculate.EnergyCalculateResponseData;
import com.cosog.model.calculate.MemoryProbeResponseData;
import com.cosog.model.calculate.PCPCalculateResponseData;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.PCPDeviceTodayData;
import com.cosog.model.calculate.SRPCalculateResponseData;
import com.cosog.model.calculate.SRPDeviceInfo;
import com.cosog.model.calculate.SRPDeviceTodayData;
import com.cosog.model.calculate.TimeEffResponseData;
import com.cosog.model.calculate.TotalAnalysisResponseData;
import com.cosog.model.drive.ModbusProtocolConfig.Items;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CalculateUtils {
	
	private final static String commUrl=Config.getInstance().configFile.getAc().getCommunication();
	private final static String runUrl=Config.getInstance().configFile.getAc().getRun();
	private final static String energyUrl=Config.getInstance().configFile.getAc().getEnergy();
	
	private final static String FESDiagramUrl=Config.getInstance().configFile.getAc().getFESDiagram();
	private final static String rpmUrl=Config.getInstance().configFile.getAc().getRPM();
	
	private final static String totalUrl=Config.getInstance().configFile.getAc().getTotalCalculation();
	
	private final static String appProbe=Config.getInstance().configFile.getAc().getProbe().getApp();
	private final static String memProbe=Config.getInstance().configFile.getAc().getProbe().getMem();
	private final static String diskProbe=Config.getInstance().configFile.getAc().getProbe().getDisk();
	private final static String hostProbe=Config.getInstance().configFile.getAc().getProbe().getHost();
	private final static String cpuProbe=Config.getInstance().configFile.getAc().getProbe().getCpu();
	
	
	public static CommResponseData commCalculate(String requestDataStr){
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		String responseDataStr=StringManagerUtils.sendPostMethod(commUrl, requestDataStr,"utf-8",0,0);
		type = new TypeToken<CommResponseData>() {}.getType();
		CommResponseData responseData=gson.fromJson(responseDataStr, type);
		try{
			//区间处理
			if(Config.getInstance().configFile.getAp().getOthers().getRangeLimit()>0 
					&& responseData!=null 
					&& responseData.getResultStatus()==1 
					&& responseData.getCurrent()!=null
					&& responseData.getCurrent().getCommEfficiency()!=null
					&& responseData.getCurrent().getCommEfficiency().getRange()!=null
					&& responseData.getCurrent().getCommEfficiency().getRange().size()>Config.getInstance().configFile.getAp().getOthers().getRangeLimit()){
				Iterator<CommResponseData.Range> it = responseData.getCurrent().getCommEfficiency().getRange().iterator();
				while(it.hasNext()){
					CommResponseData.Range range=(CommResponseData.Range)it.next();
					if(responseData.getCurrent().getCommEfficiency().getRange().size()>Config.getInstance().configFile.getAp().getOthers().getRangeLimit()){
						it.remove();
					}else{
						break;
					}
				}
				String[] rangeArr=responseData.getCurrent().getCommEfficiency().getRangeString().split(";");
				ArrayList<String> rangeList = new ArrayList<>(Arrays.asList(rangeArr));
				Iterator<String> rangeStrIt = rangeList.iterator();
				while(it.hasNext()){
					String range=(String)rangeStrIt.next();
					if(rangeList.size()>Config.getInstance().configFile.getAp().getOthers().getRangeLimit()){
						rangeStrIt.remove();
					}else{
						break;
					}
				}
				responseData.getCurrent().getCommEfficiency().setRangeString(StringUtils.join(rangeList, ";"));
			}
		}catch(Exception e){
			e.printStackTrace();
			StringManagerUtils.printLog(responseDataStr,2);
		}
		
		
		return responseData;
	}
	
	public static TimeEffResponseData runCalculate(String requestDataStr){
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		String responseDataStr=StringManagerUtils.sendPostMethod(runUrl, requestDataStr,"utf-8",0,0);
		type = new TypeToken<TimeEffResponseData>() {}.getType();
		TimeEffResponseData responseData=gson.fromJson(responseDataStr, type);
		try{
			//区间处理
			if(Config.getInstance().configFile.getAp().getOthers().getRangeLimit()>0 
					&& responseData!=null 
					&& responseData.getResultStatus()==1 
					&& responseData.getCurrent()!=null
					&& responseData.getCurrent().getRunEfficiency()!=null
					&& responseData.getCurrent().getRunEfficiency().getRange()!=null
					&& responseData.getCurrent().getRunEfficiency().getRange().size()>Config.getInstance().configFile.getAp().getOthers().getRangeLimit()){
				Iterator<TimeEffResponseData.Range> it = responseData.getCurrent().getRunEfficiency().getRange().iterator();
				while(it.hasNext()){
					TimeEffResponseData.Range range=(TimeEffResponseData.Range)it.next();
					if(responseData.getCurrent().getRunEfficiency().getRange().size()>Config.getInstance().configFile.getAp().getOthers().getRangeLimit()){
						it.remove();
					}else{
						break;
					}
				}
				
				String[] rangeArr=responseData.getCurrent().getRunEfficiency().getRangeString().split(";");
				ArrayList<String> rangeList = new ArrayList<>(Arrays.asList(rangeArr));
				Iterator<String> rangeStrIt = rangeList.iterator();
				while(it.hasNext()){
					String range=(String)rangeStrIt.next();
					if(rangeList.size()>Config.getInstance().configFile.getAp().getOthers().getRangeLimit()){
						rangeStrIt.remove();
					}else{
						break;
					}
				}
				responseData.getCurrent().getRunEfficiency().setRangeString(StringUtils.join(rangeList, ";"));
			}
		}catch(Exception e){
			e.printStackTrace();
			StringManagerUtils.printLog(responseDataStr,2);
		}
		return responseData;
	}
	
	public static EnergyCalculateResponseData energyCalculate(String requestDataStr){
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		String responseDataStr=StringManagerUtils.sendPostMethod(energyUrl, requestDataStr,"utf-8",0,0);
		type = new TypeToken<EnergyCalculateResponseData>() {}.getType();
		EnergyCalculateResponseData responseData=gson.fromJson(responseDataStr, type);
		return responseData;
	}
	
	public static SRPCalculateResponseData fesDiagramCalculate(String requestDataStr){
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		String responseDataStr=StringManagerUtils.sendPostMethod(FESDiagramUrl, requestDataStr,"utf-8",0,0);
		type = new TypeToken<SRPCalculateResponseData>() {}.getType();
		SRPCalculateResponseData responseData=gson.fromJson(responseDataStr, type);
		return responseData;
	}
	
	public static PCPCalculateResponseData rpmCalculate(String requestDataStr){
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		String responseDataStr=StringManagerUtils.sendPostMethod(rpmUrl, requestDataStr,"utf-8",0,0);
		type = new TypeToken<PCPCalculateResponseData>() {}.getType();
		PCPCalculateResponseData responseData=gson.fromJson(responseDataStr, type);
		return responseData;
	}
	
	public static TotalAnalysisResponseData totalCalculate(String requestDataStr){
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		String responseDataStr=StringManagerUtils.sendPostMethod(totalUrl, requestDataStr,"utf-8",0,0);
		type = new TypeToken<TotalAnalysisResponseData>() {}.getType();
		TotalAnalysisResponseData responseData=gson.fromJson(responseDataStr, type);
		
		//通信区间处理
		if(Config.getInstance().configFile.getAp().getOthers().getRangeLimit()>0 
				&& responseData!=null && responseData.getResultStatus()==1 
				&& responseData.getCommRange().split(";").length>Config.getInstance().configFile.getAp().getOthers().getRangeLimit()){
			String[] rangeArr=responseData.getCommRange().split(";");
			ArrayList<String> rangeList = new ArrayList<>(Arrays.asList(rangeArr));
			
			Iterator<String> it = rangeList.iterator();
			while(it.hasNext()){
				String range=(String)it.next();
				if(rangeList.size()>Config.getInstance().configFile.getAp().getOthers().getRangeLimit()){
					it.remove();
				}else{
					break;
				}
			}
			responseData.setCommRange(StringUtils.join(rangeList, ";"));
		}
		
		//运行区间处理
		if(Config.getInstance().configFile.getAp().getOthers().getRangeLimit()>0 
				&& responseData!=null && responseData.getResultStatus()==1 
				&& responseData.getRunRange().split(";").length>Config.getInstance().configFile.getAp().getOthers().getRangeLimit()){
			String[] rangeArr=responseData.getRunRange().split(";");
			ArrayList<String> rangeList = new ArrayList<>(Arrays.asList(rangeArr));
			
			Iterator<String> it = rangeList.iterator();
			while(it.hasNext()){
				String range=(String)it.next();
				if(rangeList.size()>Config.getInstance().configFile.getAp().getOthers().getRangeLimit()){
					it.remove();
				}else{
					break;
				}
			}
			responseData.setRunRange(StringUtils.join(rangeList, ";"));
		}
		
		return responseData;
	}
	
	public static AppRunStatusProbeResonanceData appProbe(String requestDataStr,int timeOut){
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		String responseDataStr=StringManagerUtils.sendPostMethod(appProbe, requestDataStr,"utf-8",timeOut,0);
		type = new TypeToken<AppRunStatusProbeResonanceData>() {}.getType();
		AppRunStatusProbeResonanceData responseData=gson.fromJson(responseDataStr, type);
		return responseData;
	}
	
	public static MemoryProbeResponseData memProbe(String requestDataStr){
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		String responseDataStr=StringManagerUtils.sendPostMethod(memProbe, requestDataStr,"utf-8",0,0);
		type = new TypeToken<MemoryProbeResponseData>() {}.getType();
		MemoryProbeResponseData responseData=gson.fromJson(responseDataStr, type);
		return responseData;
	}
	
	public static DiskProbeResponseData diskProbe(String requestDataStr){
		Gson gson=new Gson();
		java.lang.reflect.Type type=null;
		String responseDataStr=StringManagerUtils.sendPostMethod(diskProbe, requestDataStr,"utf-8",0,0);
		type = new TypeToken<DiskProbeResponseData>() {}.getType();
		DiskProbeResponseData responseData=gson.fromJson(responseDataStr, type);
		return responseData;
	}
	
	public static String getFESDiagramTotalRequestData(String date,DeviceInfo deviceInfo,SRPDeviceTodayData deviceTodayData){
		StringBuffer dataSbf= new StringBuffer();
		
		List<String> acqTimeList=new ArrayList<String>();
		List<Integer> commStatusList=new ArrayList<Integer>();
		List<Integer> runStatusList=new ArrayList<Integer>();
		
		List<Float> rpmList=new ArrayList<Float>();
		
		List<Integer> ResultCodeList=new ArrayList<Integer>();
		List<Float> strokeList=new ArrayList<Float>();
		List<Float> spmList=new ArrayList<Float>();
		
		List<Float> FMaxList=new ArrayList<Float>();
		List<Float> FMinList=new ArrayList<Float>();
		
		List<Float> fullnessCoefficientList=new ArrayList<Float>();
		
		List<Float> theoreticalProductionList=new ArrayList<Float>();
		List<Float> liquidVolumetricProductionList=new ArrayList<Float>();
		List<Float> oilVolumetricProductionList=new ArrayList<Float>();
		List<Float> waterVolumetricProductionList=new ArrayList<Float>();
		List<Float> volumeWaterCutList=new ArrayList<Float>();
		
		List<Float> liquidWeightProductionList=new ArrayList<Float>();
		List<Float> oilWeightProductionList=new ArrayList<Float>();
		List<Float> waterWeightProductionList=new ArrayList<Float>();
		List<Float> weightWaterCutList=new ArrayList<Float>();
		
		List<Float> pumpEffList=new ArrayList<Float>();
		List<Float> pumpEff1List=new ArrayList<Float>();
		List<Float> pumpEff2List=new ArrayList<Float>();
		List<Float> pumpEff3List=new ArrayList<Float>();
		List<Float> pumpEff4List=new ArrayList<Float>();
		
		
		List<Float> wattDegreeBalanceList=new ArrayList<Float>();
		List<Float> iDegreeBalanceList=new ArrayList<Float>();
		List<Float> deltaRadiusList=new ArrayList<Float>();
		
		List<Float> surfaceSystemEfficiencyList=new ArrayList<Float>();
		List<Float> wellDownSystemEfficiencyList=new ArrayList<Float>();
		List<Float> systemEfficiencyList=new ArrayList<Float>();
		List<Float> energyPer100mLiftList=new ArrayList<Float>();
		
		List<Float> pumpSettingDepthList=new ArrayList<Float>();
		List<Float> producingfluidLevelList=new ArrayList<Float>();
		List<Float> calcProducingfluidLevelList=new ArrayList<Float>();
		List<Float> levelDifferenceValueList=new ArrayList<Float>();
		List<Float> submergenceList=new ArrayList<Float>();
		
		List<Float> tubingPressureList=new ArrayList<Float>();
		List<Float> casingPressureList=new ArrayList<Float>();
		
		for(SRPCalculateResponseData responseData:deviceTodayData.getSRPCalculateList()){
			if(StringManagerUtils.timeMatchDate(responseData.getFESDiagram().getAcqTime(), date, Config.getInstance().configFile.getAp().getReport().getOffsetHour()) && !StringManagerUtils.existOrNot(acqTimeList, responseData.getFESDiagram().getAcqTime(), false)){
				acqTimeList.add(responseData.getFESDiagram().getAcqTime());
				commStatusList.add(deviceInfo.getCommStatus());
				runStatusList.add(deviceInfo.getRunStatus());
				
				rpmList.add(responseData.getRPM());
				
				ResultCodeList.add(responseData.getCalculationStatus().getResultCode());
				strokeList.add(responseData.getFESDiagram().getStroke());
				spmList.add(responseData.getFESDiagram().getSPM());
				
				if(responseData.getFESDiagram().getFMax()!=null&&responseData.getFESDiagram().getFMax().size()>0){
					FMaxList.add(responseData.getFESDiagram().getFMax().get(0));
				}else{
					FMaxList.add(0.0f);
				}
				
				if(responseData.getFESDiagram().getFMin()!=null&&responseData.getFESDiagram().getFMin().size()>0){
					FMinList.add(responseData.getFESDiagram().getFMin().get(0));
				}else{
					FMinList.add(0.0f);
				}
				
				fullnessCoefficientList.add(responseData.getFESDiagram().getFullnessCoefficient());
				
				theoreticalProductionList.add(responseData.getProduction().getTheoreticalProduction());
				liquidVolumetricProductionList.add(responseData.getProduction().getLiquidVolumetricProduction());
				oilVolumetricProductionList.add(responseData.getProduction().getOilVolumetricProduction());
				waterVolumetricProductionList.add(responseData.getProduction().getWaterVolumetricProduction());
				volumeWaterCutList.add(responseData.getProduction().getWaterCut());
				
				liquidWeightProductionList.add(responseData.getProduction().getLiquidWeightProduction());
				oilWeightProductionList.add(responseData.getProduction().getOilWeightProduction());
				waterWeightProductionList.add(responseData.getProduction().getWaterWeightProduction());
				weightWaterCutList.add(responseData.getProduction().getWeightWaterCut());
				
				
				pumpSettingDepthList.add(responseData.getProduction().getPumpSettingDepth());
				producingfluidLevelList.add(responseData.getProduction().getProducingfluidLevel());
				calcProducingfluidLevelList.add(responseData.getProduction().getCalcProducingfluidLevel());
				levelDifferenceValueList.add(responseData.getProduction().getLevelDifferenceValue());
				submergenceList.add(responseData.getProduction().getSubmergence());
				tubingPressureList.add(responseData.getProduction().getTubingPressure());
				casingPressureList.add(responseData.getProduction().getCasingPressure());
				
				pumpEffList.add(responseData.getPumpEfficiency().getPumpEff());
				pumpEff1List.add(responseData.getPumpEfficiency().getPumpEff1());
				pumpEff2List.add(responseData.getPumpEfficiency().getPumpEff2());
				pumpEff3List.add(responseData.getPumpEfficiency().getPumpEff3());
				pumpEff4List.add(responseData.getPumpEfficiency().getPumpEff4());
				
				wattDegreeBalanceList.add(responseData.getFESDiagram().getWattDegreeBalance());
				iDegreeBalanceList.add(responseData.getFESDiagram().getIDegreeBalance());
				deltaRadiusList.add(responseData.getFESDiagram().getDeltaRadius());
				
				surfaceSystemEfficiencyList.add(responseData.getSystemEfficiency().getSurfaceSystemEfficiency());
				wellDownSystemEfficiencyList.add(responseData.getSystemEfficiency().getWellDownSystemEfficiency());
				systemEfficiencyList.add(responseData.getSystemEfficiency().getSystemEfficiency());
				energyPer100mLiftList.add(responseData.getSystemEfficiency().getEnergyPer100mLift());
			}
		}
		try{
			dataSbf.append("{\"AKString\":\"\",");
			dataSbf.append("\"WellName\":\""+deviceInfo.getDeviceName()+"\",");
			dataSbf.append("\"Date\":\""+date+"\",");
			dataSbf.append("\"OffsetHour\":"+Config.getInstance().configFile.getAp().getReport().getOffsetHour()+",");
			dataSbf.append("\"AcqTime\":["+StringManagerUtils.joinStringArr(acqTimeList, ",")+"],");
			dataSbf.append("\"CommStatus\":["+StringUtils.join(commStatusList, ",")+"],");
			dataSbf.append("\"CurrentCommTime\":"+deviceInfo.getCommStatus()+",");
			dataSbf.append("\"CommTime\":"+deviceInfo.getCommTime()+",");
			dataSbf.append("\"CommTimeEfficiency\":"+deviceInfo.getCommEff()+",");
			dataSbf.append("\"CommRange\":\""+deviceInfo.getCommRange()+"\",");
			dataSbf.append("\"RunStatus\":["+StringUtils.join(runStatusList, ",")+"],");
			dataSbf.append("\"CurrentRunTime\":"+deviceInfo.getRunStatus()+",");
			dataSbf.append("\"RunTime\":"+deviceInfo.getRunTime()+",");
			dataSbf.append("\"RunTimeEfficiency\":"+deviceInfo.getRunEff()+",");
			dataSbf.append("\"RunRange\":\""+deviceInfo.getRunRange()+"\",");
			dataSbf.append("\"ResultCode\":["+StringUtils.join(ResultCodeList, ",")+"],");
			dataSbf.append("\"TheoreticalProduction\":["+StringUtils.join(theoreticalProductionList, ",")+"],");
			dataSbf.append("\"LiquidVolumetricProduction\":["+StringUtils.join(liquidVolumetricProductionList, ",")+"],");
			dataSbf.append("\"OilVolumetricProduction\":["+StringUtils.join(oilVolumetricProductionList, ",")+"],");
			dataSbf.append("\"WaterVolumetricProduction\":["+StringUtils.join(waterVolumetricProductionList, ",")+"],");
			dataSbf.append("\"VolumeWaterCut\":["+StringUtils.join(volumeWaterCutList, ",")+"],");
			dataSbf.append("\"LiquidWeightProduction\":["+StringUtils.join(liquidWeightProductionList, ",")+"],");
			dataSbf.append("\"OilWeightProduction\":["+StringUtils.join(oilWeightProductionList, ",")+"],");
			dataSbf.append("\"WaterWeightProduction\":["+StringUtils.join(waterWeightProductionList, ",")+"],");
			dataSbf.append("\"WeightWaterCut\":["+StringUtils.join(weightWaterCutList, ",")+"],");
			dataSbf.append("\"SurfaceSystemEfficiency\":["+StringUtils.join(surfaceSystemEfficiencyList, ",")+"],");
			dataSbf.append("\"WellDownSystemEfficiency\":["+StringUtils.join(wellDownSystemEfficiencyList, ",")+"],");
			dataSbf.append("\"SystemEfficiency\":["+StringUtils.join(systemEfficiencyList, ",")+"],");
			dataSbf.append("\"EnergyPer100mLift\":["+StringUtils.join(energyPer100mLiftList, ",")+"],");
			dataSbf.append("\"Stroke\":["+StringUtils.join(strokeList, ",")+"],");
			dataSbf.append("\"SPM\":["+StringUtils.join(spmList, ",")+"],");
			dataSbf.append("\"FMax\":["+StringUtils.join(FMaxList, ",")+"],");
			dataSbf.append("\"FMin\":["+StringUtils.join(FMinList, ",")+"],");
			dataSbf.append("\"FullnessCoefficient\":["+StringUtils.join(fullnessCoefficientList, ",")+"],");
			dataSbf.append("\"PumpEff\":["+StringUtils.join(pumpEffList, ",")+"],");
			dataSbf.append("\"PumpEff1\":["+StringUtils.join(pumpEff1List, ",")+"],");
			dataSbf.append("\"PumpEff2\":["+StringUtils.join(pumpEff2List, ",")+"],");
			dataSbf.append("\"PumpEff3\":["+StringUtils.join(pumpEff3List, ",")+"],");
			dataSbf.append("\"PumpEff4\":["+StringUtils.join(pumpEff4List, ",")+"],");
			dataSbf.append("\"WattDegreeBalance\":["+StringUtils.join(wattDegreeBalanceList, ",")+"],");
			dataSbf.append("\"IDegreeBalance\":["+StringUtils.join(iDegreeBalanceList, ",")+"],");
			dataSbf.append("\"DeltaRadius\":["+StringUtils.join(deltaRadiusList, ",")+"],");
			dataSbf.append("\"PumpSettingDepth\":["+StringUtils.join(pumpSettingDepthList, ",")+"],");
			dataSbf.append("\"ProducingfluidLevel\":["+StringUtils.join(producingfluidLevelList, ",")+"],");
			dataSbf.append("\"CalcProducingfluidLevel\":["+StringUtils.join(calcProducingfluidLevelList, ",")+"],");
			dataSbf.append("\"LevelDifferenceValue\":["+StringUtils.join(levelDifferenceValueList, ",")+"],");
			dataSbf.append("\"Submergence\":["+StringUtils.join(submergenceList, ",")+"],");
			dataSbf.append("\"TubingPressure\":["+StringUtils.join(tubingPressureList, ",")+"],");
			dataSbf.append("\"CasingPressure\":["+StringUtils.join(casingPressureList, ",")+"],");
			dataSbf.append("\"RPM\":["+StringUtils.join(rpmList, ",")+"]");
			dataSbf.append("}");
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataSbf.toString();
	}
	
	public static String getRPMTotalRequestData(String date,DeviceInfo deviceInfo,PCPDeviceTodayData deviceTodayData){
		StringBuffer dataSbf= new StringBuffer();
		
		List<String> acqTimeList=new ArrayList<String>();
		List<Integer> commStatusList=new ArrayList<Integer>();
		List<Integer> runStatusList=new ArrayList<Integer>();
		
		List<Float> rpmList=new ArrayList<Float>();
		
		List<Float> theoreticalProductionList=new ArrayList<Float>();
		List<Float> liquidVolumetricProductionList=new ArrayList<Float>();
		List<Float> oilVolumetricProductionList=new ArrayList<Float>();
		List<Float> waterVolumetricProductionList=new ArrayList<Float>();
		List<Float> volumeWaterCutList=new ArrayList<Float>();
		
		List<Float> liquidWeightProductionList=new ArrayList<Float>();
		List<Float> oilWeightProductionList=new ArrayList<Float>();
		List<Float> waterWeightProductionList=new ArrayList<Float>();
		List<Float> weightWaterCutList=new ArrayList<Float>();
		
		List<Float> pumpEffList=new ArrayList<Float>();
		List<Float> pumpEff1List=new ArrayList<Float>();
		List<Float> pumpEff2List=new ArrayList<Float>();
		
		List<Float> systemEfficiencyList=new ArrayList<Float>();
		List<Float> energyPer100mLiftList=new ArrayList<Float>();
		
		List<Float> pumpSettingDepthList=new ArrayList<Float>();
		List<Float> producingfluidLevelList=new ArrayList<Float>();
		List<Float> submergenceList=new ArrayList<Float>();
		
		List<Float> tubingPressureList=new ArrayList<Float>();
		List<Float> casingPressureList=new ArrayList<Float>();
		
		for(PCPCalculateResponseData responseData:deviceTodayData.getPCPCalculateList()){
			if(StringManagerUtils.timeMatchDate(responseData.getAcqTime(), date, Config.getInstance().configFile.getAp().getReport().getOffsetHour()) && !StringManagerUtils.existOrNot(acqTimeList, responseData.getAcqTime(), false)){
				acqTimeList.add(responseData.getAcqTime());
				commStatusList.add(deviceInfo.getCommStatus());
				runStatusList.add(deviceInfo.getRunStatus());
				
				rpmList.add(responseData.getRPM());
				
				theoreticalProductionList.add(responseData.getProduction().getTheoreticalProduction());
				liquidVolumetricProductionList.add(responseData.getProduction().getLiquidVolumetricProduction());
				oilVolumetricProductionList.add(responseData.getProduction().getOilVolumetricProduction());
				waterVolumetricProductionList.add(responseData.getProduction().getWaterVolumetricProduction());
				volumeWaterCutList.add(responseData.getProduction().getWaterCut());
				
				liquidWeightProductionList.add(responseData.getProduction().getLiquidWeightProduction());
				oilWeightProductionList.add(responseData.getProduction().getOilWeightProduction());
				waterWeightProductionList.add(responseData.getProduction().getWaterWeightProduction());
				weightWaterCutList.add(responseData.getProduction().getWeightWaterCut());
				
				pumpSettingDepthList.add(responseData.getProduction().getPumpSettingDepth());
				producingfluidLevelList.add(responseData.getProduction().getProducingfluidLevel());
				submergenceList.add(responseData.getProduction().getSubmergence());
				tubingPressureList.add(responseData.getProduction().getTubingPressure());
				casingPressureList.add(responseData.getProduction().getCasingPressure());
				
				pumpEffList.add(responseData.getPumpEfficiency().getPumpEff());
				pumpEff1List.add(responseData.getPumpEfficiency().getPumpEff1());
				pumpEff2List.add(responseData.getPumpEfficiency().getPumpEff2());
				
				systemEfficiencyList.add(responseData.getSystemEfficiency().getSystemEfficiency());
				energyPer100mLiftList.add(responseData.getSystemEfficiency().getEnergyPer100mLift());
			}
		}
		
		dataSbf.append("{\"AKString\":\"\",");
		dataSbf.append("\"WellName\":\""+deviceInfo.getDeviceName()+"\",");
		dataSbf.append("\"Date\":\""+date+"\",");
		dataSbf.append("\"OffsetHour\":"+Config.getInstance().configFile.getAp().getReport().getOffsetHour()+",");
		dataSbf.append("\"AcqTime\":["+StringManagerUtils.joinStringArr(acqTimeList, ",")+"],");
		dataSbf.append("\"CommStatus\":["+StringUtils.join(commStatusList, ",")+"],");
		dataSbf.append("\"CurrentCommTime\":"+deviceInfo.getCommStatus()+",");
		dataSbf.append("\"CommTime\":"+deviceInfo.getCommTime()+",");
		dataSbf.append("\"CommTimeEfficiency\":"+deviceInfo.getCommEff()+",");
		dataSbf.append("\"CommRange\":\""+deviceInfo.getCommRange()+"\",");
		dataSbf.append("\"RunStatus\":["+StringUtils.join(runStatusList, ",")+"],");
		dataSbf.append("\"CurrentRunTime\":"+deviceInfo.getRunStatus()+",");
		dataSbf.append("\"RunTime\":"+deviceInfo.getRunTime()+",");
		dataSbf.append("\"RunTimeEfficiency\":"+deviceInfo.getRunEff()+",");
		dataSbf.append("\"RunRange\":\""+deviceInfo.getRunRange()+"\",");
		dataSbf.append("\"RPM\":["+StringUtils.join(rpmList, ",")+"],");
		dataSbf.append("\"TheoreticalProduction\":["+StringUtils.join(theoreticalProductionList, ",")+"],");
		dataSbf.append("\"LiquidVolumetricProduction\":["+StringUtils.join(liquidVolumetricProductionList, ",")+"],");
		dataSbf.append("\"OilVolumetricProduction\":["+StringUtils.join(oilVolumetricProductionList, ",")+"],");
		dataSbf.append("\"WaterVolumetricProduction\":["+StringUtils.join(waterVolumetricProductionList, ",")+"],");
		dataSbf.append("\"VolumeWaterCut\":["+StringUtils.join(volumeWaterCutList, ",")+"],");
		dataSbf.append("\"LiquidWeightProduction\":["+StringUtils.join(liquidWeightProductionList, ",")+"],");
		dataSbf.append("\"OilWeightProduction\":["+StringUtils.join(oilWeightProductionList, ",")+"],");
		dataSbf.append("\"WaterWeightProduction\":["+StringUtils.join(waterWeightProductionList, ",")+"],");
		dataSbf.append("\"WeightWaterCut\":["+StringUtils.join(weightWaterCutList, ",")+"],");
		dataSbf.append("\"SystemEfficiency\":["+StringUtils.join(systemEfficiencyList, ",")+"],");
		dataSbf.append("\"EnergyPer100mLift\":["+StringUtils.join(energyPer100mLiftList, ",")+"],");
		dataSbf.append("\"PumpEff\":["+StringUtils.join(pumpEffList, ",")+"],");
		dataSbf.append("\"PumpEff1\":["+StringUtils.join(pumpEff1List, ",")+"],");
		dataSbf.append("\"PumpEff2\":["+StringUtils.join(pumpEff2List, ",")+"],");
		dataSbf.append("\"PumpSettingDepth\":["+StringUtils.join(pumpSettingDepthList, ",")+"],");
		dataSbf.append("\"ProducingfluidLevel\":["+StringUtils.join(producingfluidLevelList, ",")+"],");
		dataSbf.append("\"Submergence\":["+StringUtils.join(submergenceList, ",")+"],");
		dataSbf.append("\"TubingPressure\":["+StringUtils.join(tubingPressureList, ",")+"],");
		dataSbf.append("\"CasingPressure\":["+StringUtils.join(casingPressureList, ",")+"]");
		dataSbf.append("}");
		
		return dataSbf.toString();
	}
	
	public static String getRangeJson(String rangeStr) {
        String result = "";
        StringBuffer dynSbf = new StringBuffer();
        if (StringManagerUtils.isNotNull(rangeStr)) {
            dynSbf.append("[");
            String[] wellRunRimeArr = rangeStr.split(";");
            for (int i = 0; i < wellRunRimeArr.length; i++) {
                if ("00:00-24:00".equals(wellRunRimeArr[i]) || "00:00-00:00".equals(wellRunRimeArr[i])) {
                    dynSbf.append("{\"startTime\":\"00:00\",\"endTime\":\"00:00\"}");
                    break;
                } else {
                    String[] tempArr = wellRunRimeArr[i].split("-");
                    dynSbf.append("{\"startTime\":\"" + tempArr + "\",\"endTime\":\"" + tempArr[1] + "\"}");
                }

                if (i < wellRunRimeArr.length - 1) {
                    dynSbf.append(",");
                }
            }
            dynSbf.append("]");
            result = dynSbf.toString();
        } else {
            result = "[{\"startTime\":\"\",\"endTime\":\"\"}]";
        }
        return result;
    }
	
	public static float volumeWaterCutToWeightWaterCut(float volumeWaterCut,float crudeOilDensity,float waterDensity){
		float weightWaterCut=0;
		if(crudeOilDensity!=0 || waterDensity!=0){
			weightWaterCut=100*waterDensity*volumeWaterCut/( waterDensity*volumeWaterCut+(100-volumeWaterCut)*crudeOilDensity );
			weightWaterCut = Math.round(weightWaterCut * 100) / 100f;
		}
		return weightWaterCut;
	}
	
	public static float weightWaterCutToVolumeWaterCut(float weightWaterCut,float crudeOilDensity,float waterDensity){
		float volumeWaterCut=0;
		if(crudeOilDensity!=0 || waterDensity!=0){
			volumeWaterCut=100*crudeOilDensity*weightWaterCut/(crudeOilDensity*weightWaterCut+(100-weightWaterCut)*waterDensity );
			volumeWaterCut = Math.round(volumeWaterCut * 100) / 100f;
		}
		return volumeWaterCut;
	}
}
