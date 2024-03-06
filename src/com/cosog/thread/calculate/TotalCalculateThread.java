package com.cosog.thread.calculate;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.TotalAnalysisResponseData;
import com.cosog.service.base.CommonDataService;
import com.cosog.utils.CalculateUtils;
import com.cosog.utils.Config;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TotalCalculateThread extends Thread{

	private int recordId;
	private int wellId;
	private String deviceName;
	private String calDate;
	private int deviceType;
	private CommonDataService commonDataService=null;

	public TotalCalculateThread(int recordId, int wellId, String deviceName, String calDate, int deviceType,
			CommonDataService commonDataService) {
		super();
		this.recordId = recordId;
		this.wellId = wellId;
		this.deviceName = deviceName;
		this.calDate = calDate;
		this.deviceType = deviceType;
		this.commonDataService = commonDataService;
	}

	public void run(){
		long startTime=new Date().getTime();
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		int offsetHour=Config.getInstance().configFile.getAp().getReport().getOffsetHour();
		if(deviceType==0){
			String sql="select t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,t.runstatus,t.runtime,t.runtimeefficiency,t.runrange "
					+ " from tbl_rpcdailycalculationdata t,tbl_device t2 "
					+ " where t.wellid=t2.id "
					+ " and t.id="+recordId;
			String fesDiagramSql="select to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss'),t.resultcode,"
					+ "t.stroke,t.spm,t.fmax,t.fmin,t.fullnesscoefficient,"
					+ "t.theoreticalproduction,t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
					+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
					+ "t.productiondata,"
					+ "t.pumpeff,t.pumpeff1,t.pumpeff2,t.pumpeff3,t.pumpeff4,"
					+ "t.wattdegreebalance,t.idegreebalance,t.deltaradius,"
					+ "t.surfacesystemefficiency,t.welldownsystemefficiency,t.systemefficiency,t.energyper100mlift,"
					+ "t.calcProducingfluidLevel,t.levelDifferenceValue,"
					+ "t.submergence,"
					+ "t.rpm "
					+ " from tbl_rpcacqdata_hist t "
					+ " where t.wellid="+wellId+" "
					+ " and t.fesdiagramacqtime between to_date('"+calDate+"','yyyy-mm-dd') +"+offsetHour+"/24 and to_date('"+calDate+"','yyyy-mm-dd')+"+offsetHour+"/24+1 "
					+ " and t.resultstatus=1 "
					+ " order by t.fesdiagramacqtime";
			List<?> list = commonDataService.findCallSql(sql);
			if(list.size()>0){
				StringBuffer dataSbf= new StringBuffer();
				Object[] totalObj=(Object[])list.get(0);
				List<?> fesDiagramList = commonDataService.findCallSql(fesDiagramSql);
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
				
				for(int j=0;j<fesDiagramList.size();j++){
					Object[] obj=(Object[])fesDiagramList.get(j);
					
					String productionData=obj[14].toString();
					type = new TypeToken<RPCCalculateRequestData>() {}.getType();
					RPCCalculateRequestData rpcProductionData=gson.fromJson(productionData, type);
					
					acqTimeList.add(obj[0]+"");
					commStatusList.add(StringManagerUtils.stringToInteger(totalObj[0]+""));
					runStatusList.add(StringManagerUtils.stringToInteger(totalObj[4]+""));
					
					ResultCodeList.add(StringManagerUtils.stringToInteger(obj[1]+""));
					strokeList.add(StringManagerUtils.stringToFloat(obj[2]+""));
					spmList.add(StringManagerUtils.stringToFloat(obj[3]+""));
					FMaxList.add(StringManagerUtils.stringToFloat(obj[4]+""));
					FMinList.add(StringManagerUtils.stringToFloat(obj[5]+""));
					fullnessCoefficientList.add(StringManagerUtils.stringToFloat(obj[6]+""));
					
					theoreticalProductionList.add(StringManagerUtils.stringToFloat(obj[7]+""));
					liquidVolumetricProductionList.add(StringManagerUtils.stringToFloat(obj[8]+""));
					oilVolumetricProductionList.add(StringManagerUtils.stringToFloat(obj[9]+""));
					waterVolumetricProductionList.add(StringManagerUtils.stringToFloat(obj[10]+""));
					
					if(rpcProductionData!=null&&rpcProductionData.getProduction()!=null){
						volumeWaterCutList.add(rpcProductionData.getProduction().getWaterCut());
					}else{
						volumeWaterCutList.add(0.0f);
					}
					
					
					liquidWeightProductionList.add(StringManagerUtils.stringToFloat(obj[11]+""));
					oilWeightProductionList.add(StringManagerUtils.stringToFloat(obj[12]+""));
					waterWeightProductionList.add(StringManagerUtils.stringToFloat(obj[13]+""));
					
					if(rpcProductionData!=null&&rpcProductionData.getProduction()!=null){
						weightWaterCutList.add(rpcProductionData.getProduction().getWeightWaterCut());
					}else{
						weightWaterCutList.add(0.0f);
					}
					
					if(rpcProductionData!=null&&rpcProductionData.getProduction()!=null){
						tubingPressureList.add(rpcProductionData.getProduction().getTubingPressure());
						casingPressureList.add(rpcProductionData.getProduction().getCasingPressure());
						pumpSettingDepthList.add(rpcProductionData.getProduction().getPumpSettingDepth());
						producingfluidLevelList.add(rpcProductionData.getProduction().getProducingfluidLevel());
					}else{
						tubingPressureList.add(0.0f);
						casingPressureList.add(0.0f);
						pumpSettingDepthList.add(0.0f);
						producingfluidLevelList.add(0.0f);
					}
					
					pumpEffList.add(StringManagerUtils.stringToFloat(obj[15]+""));
					pumpEff1List.add(StringManagerUtils.stringToFloat(obj[16]+""));
					pumpEff2List.add(StringManagerUtils.stringToFloat(obj[17]+""));
					pumpEff3List.add(StringManagerUtils.stringToFloat(obj[18]+""));
					pumpEff4List.add(StringManagerUtils.stringToFloat(obj[19]+""));
					
					wattDegreeBalanceList.add(StringManagerUtils.stringToFloat(obj[20]+""));
					iDegreeBalanceList.add(StringManagerUtils.stringToFloat(obj[21]+""));
					deltaRadiusList.add(StringManagerUtils.stringToFloat(obj[22]+""));
					
					surfaceSystemEfficiencyList.add(StringManagerUtils.stringToFloat(obj[23]+""));
					wellDownSystemEfficiencyList.add(StringManagerUtils.stringToFloat(obj[24]+""));
					systemEfficiencyList.add(StringManagerUtils.stringToFloat(obj[25]+""));
					energyPer100mLiftList.add(StringManagerUtils.stringToFloat(obj[26]+""));
					
					calcProducingfluidLevelList.add(StringManagerUtils.stringToFloat(obj[27]+""));
					levelDifferenceValueList.add(StringManagerUtils.stringToFloat(obj[28]+""));
					submergenceList.add(StringManagerUtils.stringToFloat(obj[29]+""));
					
					rpmList.add(StringManagerUtils.stringToFloat(obj[30]+""));
				}
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"DeviceName\":\""+deviceName+"\",");
				dataSbf.append("\"Date\":\""+calDate+"\",");
				dataSbf.append("\"OffsetHour\":"+Config.getInstance().configFile.getAp().getReport().getOffsetHour()+",");
				dataSbf.append("\"AcqTime\":["+StringManagerUtils.joinStringArr(acqTimeList, ",")+"],");
				dataSbf.append("\"CommStatus\":["+StringUtils.join(commStatusList, ",")+"],");
				dataSbf.append("\"CommTime\":"+totalObj[1]+",");
				dataSbf.append("\"CommTimeEfficiency\":"+totalObj[2]+",");
				dataSbf.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(totalObj[3])+"\",");
				dataSbf.append("\"RunStatus\":["+StringUtils.join(runStatusList, ",")+"],");
				dataSbf.append("\"RunTime\":"+totalObj[5]+",");
				dataSbf.append("\"RunTimeEfficiency\":"+totalObj[6]+",");
				dataSbf.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(totalObj[7])+"\",");
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
				
				TotalAnalysisResponseData totalAnalysisResponseData=CalculateUtils.totalCalculate(dataSbf.toString());
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					int recordCount=acqTimeList.size();
					try {
						commonDataService.getBaseDao().saveFESDiagramReTotalData(recordId+"",totalAnalysisResponseData,recordCount);
					} catch (SQLException | ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}else{
			String sql="select t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,t.runstatus,t.runtime,t.runtimeefficiency,t.runrange "
					+ " from tbl_pcpdailycalculationdata t,tbl_pcpdevice t2 "
					+ " where t.wellid=t2.id "
					+ " and t.id="+recordId;
			String rpmSql="select "
					+ "to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss'),t.rpm,"
					+ "t.theoreticalproduction,t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
					+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
					+ "t.productiondata,"
					+ "t.pumpeff,t.pumpeff1,t.pumpeff2,"
					+ "t.systemefficiency,t.energyper100mlift,"
					+ "t.submergence "
					+ " from tbl_pcpacqdata_hist t "
					+ " where t.wellid="+wellId+" "
					+ " and t.acqtime between to_date('"+calDate+"','yyyy-mm-dd')+"+offsetHour+"/24 and to_date('"+calDate+"','yyyy-mm-dd')+"+offsetHour+"/24+1 "
					+ " and t.resultstatus=1 "
					+ " order by t.acqtime";
			List<?> list = commonDataService.findCallSql(sql);
			if(list.size()>0){
				StringBuffer dataSbf= new StringBuffer();
				Object[] totalObj=(Object[])list.get(0);
				List<?> rpmResultList = commonDataService.findCallSql(rpmSql);
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
				
				for(int j=0;j<rpmResultList.size();j++){
					Object[] obj=(Object[])rpmResultList.get(j);
					
					String productionData=obj[9].toString();
					type = new TypeToken<PCPCalculateRequestData>() {}.getType();
					PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
					
					acqTimeList.add(obj[0]+"");
					commStatusList.add(StringManagerUtils.stringToInteger(totalObj[0]+""));
					runStatusList.add(StringManagerUtils.stringToInteger(totalObj[4]+""));
					
					rpmList.add(StringManagerUtils.stringToFloat(obj[1]+""));
					
					
					theoreticalProductionList.add(StringManagerUtils.stringToFloat(obj[2]+""));
					liquidVolumetricProductionList.add(StringManagerUtils.stringToFloat(obj[3]+""));
					oilVolumetricProductionList.add(StringManagerUtils.stringToFloat(obj[4]+""));
					waterVolumetricProductionList.add(StringManagerUtils.stringToFloat(obj[5]+""));
					
					if(pcpProductionData!=null&&pcpProductionData.getProduction()!=null){
						volumeWaterCutList.add(pcpProductionData.getProduction().getWaterCut());
					}else{
						volumeWaterCutList.add(0.0f);
					}
					
					
					liquidWeightProductionList.add(StringManagerUtils.stringToFloat(obj[6]+""));
					oilWeightProductionList.add(StringManagerUtils.stringToFloat(obj[7]+""));
					waterWeightProductionList.add(StringManagerUtils.stringToFloat(obj[8]+""));
					if(pcpProductionData!=null&&pcpProductionData.getProduction()!=null){
						weightWaterCutList.add(pcpProductionData.getProduction().getWeightWaterCut());
					}else{
						weightWaterCutList.add(0.0f);
					}
					
					if(pcpProductionData!=null&&pcpProductionData.getProduction()!=null){
						tubingPressureList.add(pcpProductionData.getProduction().getTubingPressure());
						casingPressureList.add(pcpProductionData.getProduction().getCasingPressure());
						pumpSettingDepthList.add(pcpProductionData.getProduction().getPumpSettingDepth());
						producingfluidLevelList.add(pcpProductionData.getProduction().getProducingfluidLevel());
					}else{
						tubingPressureList.add(0.0f);
						casingPressureList.add(0.0f);
						pumpSettingDepthList.add(0.0f);
						producingfluidLevelList.add(0.0f);
					}
					
					pumpEffList.add(StringManagerUtils.stringToFloat(obj[10]+""));
					pumpEff1List.add(StringManagerUtils.stringToFloat(obj[11]+""));
					pumpEff2List.add(StringManagerUtils.stringToFloat(obj[12]+""));
					
					systemEfficiencyList.add(StringManagerUtils.stringToFloat(obj[13]+""));
					energyPer100mLiftList.add(StringManagerUtils.stringToFloat(obj[14]+""));
					submergenceList.add(StringManagerUtils.stringToFloat(obj[15]+""));
				}
				dataSbf.append("{\"AKString\":\"\",");
				dataSbf.append("\"DeviceName\":\""+deviceName+"\",");
				dataSbf.append("\"Date\":\""+calDate+"\",");
				dataSbf.append("\"OffsetHour\":"+Config.getInstance().configFile.getAp().getReport().getOffsetHour()+",");
				dataSbf.append("\"AcqTime\":["+StringManagerUtils.joinStringArr(acqTimeList, ",")+"],");
				dataSbf.append("\"CommStatus\":["+StringUtils.join(commStatusList, ",")+"],");
				dataSbf.append("\"CommTime\":"+totalObj[1]+",");
				dataSbf.append("\"CommTimeEfficiency\":"+totalObj[2]+",");
				dataSbf.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(totalObj[3])+"\",");
				dataSbf.append("\"RunStatus\":["+StringUtils.join(runStatusList, ",")+"],");
				dataSbf.append("\"RunTime\":"+totalObj[5]+",");
				dataSbf.append("\"RunTimeEfficiency\":"+totalObj[6]+",");
				dataSbf.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(totalObj[7])+"\",");
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
				
				TotalAnalysisResponseData totalAnalysisResponseData=CalculateUtils.totalCalculate(dataSbf.toString());
				if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
					int recordCount=acqTimeList.size();
					try {
						commonDataService.getBaseDao().saveRPMReTotalData(recordId+"",totalAnalysisResponseData,recordCount);
					} catch (SQLException | ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
		long endTime=new Date().getTime();
		System.out.println((deviceType==0?"抽油机井":"螺杆泵井")+ deviceName+"，日期："+calDate+"，数据汇总完成,共用时:"+(endTime-startTime)+"ms");
	}

	

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public int getWellId() {
		return wellId;
	}

	public void setWellId(int wellId) {
		this.wellId = wellId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getCalDate() {
		return calDate;
	}

	public void setCalDate(String calDate) {
		this.calDate = calDate;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public CommonDataService getCommonDataService() {
		return commonDataService;
	}

	public void setCommonDataService(CommonDataService commonDataService) {
		this.commonDataService = commonDataService;
	}
}
