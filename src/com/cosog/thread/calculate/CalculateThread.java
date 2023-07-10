package com.cosog.thread.calculate;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.PCPCalculateResponseData;
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.RPCCalculateResponseData;
import com.cosog.model.calculate.TotalAnalysisRequestData;
import com.cosog.model.calculate.TotalAnalysisResponseData;
import com.cosog.service.datainterface.CalculateDataService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.CalculateUtils;
import com.cosog.utils.Config;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CalculateThread extends Thread{

	private int threadId;
	private int wellNo;
	private String acqDate;
	private int deviceType;
	private CalculateDataService<?> calculateDataService=null;

	public CalculateThread(int threadId, int wellNo,String acqDate,int deviceType, CalculateDataService<?> calculateManagerService) {
		super();
		this.threadId = threadId;
		this.wellNo = wellNo;
		this.acqDate = acqDate;
		this.deviceType = deviceType;
		this.calculateDataService = calculateManagerService;
	}

	public void run(){
		System.out.println("线程"+threadId+"开始计算"+(deviceType==0?"抽油机井":"螺杆泵井")+"编号"+wellNo+"井");
		long startTime=new Date().getTime();
		int count=0;
		int totalCount=0;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String currentDate=StringManagerUtils.getCurrentTime("yyyy-MM-dd");
		if(deviceType==0){
//			String acqDateSql="select distinct(to_char(t.fesdiagramacqtime,'yyyy-mm-dd')) as acqdate"
//					+ " from tbl_rpcacqdata_hist t "
//					+ " where 1=1  "
//					+ " and t.productiondata is not null"
//					+ " and t.fesdiagramacqtime is not null "
//					+ " and t.wellid="+wellNo+""
//					+ " and t.resultstatus =2 "
//					+ " order by acqdate";
//			List<?> acqDateList = calculateDataService.findCallSql(acqDateSql);

			String minAcqTime="";
			String sql="select t2.wellname,decode(t2.applicationscenarios,0,'cbm','oil') as applicationscenarios,"
					+ " to_char(t.fesdiagramacqTime,'yyyy-mm-dd hh24:mi:ss') as fesdiagramacqTime,t.fesdiagramSrc,"
					+ " t.stroke,t.spm,"
					+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
					+ " t.productiondata,"
					+ " t3.id as pumpingmodelid,t3.manufacturer,t3.model,t3.crankrotationdirection,t3.offsetangleofcrank,t3.crankgravityradius,t3.singlecrankweight,t3.singlecrankpinweight,t3.structuralunbalance,"
					+ " t.balanceinfo,"
					+ " t.id"
					+ " from tbl_rpcacqdata_hist t"
					+ " left outer join tbl_rpcdevice t2 on t.wellid=t2.id"
					+ " left outer join tbl_pumpingmodel t3 on t3.id=t.pumpingmodelid"
					+ " where 1=1  "
					+ " and t.fesdiagramacqtime between to_date('"+acqDate+"','yyyy-mm-dd') and to_date('"+acqDate+"','yyyy-mm-dd')+1 "
					+ " and t.productiondata is not null"
					+ " and t.fesdiagramacqtime is not null "
					+ " and t.wellid="+wellNo+""
					+ " and t.resultstatus =2  "
					+ " order by t.fesdiagramacqTime ";
			String fesDiagramSql="select t2.id as wellid, to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss'),t.resultcode,"
					+ "t.stroke,t.spm,t.fmax,t.fmin,t.fullnesscoefficient,"
					+ "t.theoreticalproduction,"
					+ "t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
					+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
					+ "t.productiondata,"
					+ "t.pumpeff,t.pumpeff1,t.pumpeff2,t.pumpeff3,t.pumpeff4,"
					+ "t.wattdegreebalance,t.idegreebalance,t.deltaradius,"
					+ "t.surfacesystemefficiency,t.welldownsystemefficiency,t.systemefficiency,t.energyper100mlift,"
					+ "t.calcProducingfluidLevel,t.levelDifferenceValue,"//28~39
					+ "t.submergence,"//30
					+ "t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,"
					+ "t.runstatus,t.runtime,t.runtimeefficiency,t.runrange,"
					+ "t.id as recordId"
					+ " from tbl_rpcacqdata_hist t,tbl_rpcdevice t2 "
					+ " where t.wellid=t2.id "
					+ " and t.resultstatus=1 "
					+ " and t.fesdiagramacqtime between to_date('"+acqDate+"','yyyy-mm-dd') and to_date('"+acqDate+"','yyyy-mm-dd')+1 "
					+ " and t.wellid="+wellNo+""
					+ " order by t.fesdiagramacqTime ";
			List<?> list = calculateDataService.findCallSql(sql);
			count=list.size();
			for(int j=0;j<list.size();j++){
				try{
					Object[] obj=(Object[])list.get(j);
					String requestData=calculateDataService.getObjectToRPCCalculateRequestData(obj);
					type = new TypeToken<RPCCalculateRequestData>() {}.getType();
					RPCCalculateRequestData calculateRequestData=gson.fromJson(requestData, type);
					if(calculateRequestData!=null){
						RPCCalculateResponseData calculateResponseData=CalculateUtils.fesDiagramCalculate(requestData);
						int recordId=Integer.parseInt(obj[obj.length-1].toString());
						if(calculateResponseData==null){
							System.out.println("记录:"+recordId+"计算无数据返回");
						}
						calculateDataService.getBaseDao().saveFESDiagramCalculateResult(recordId,calculateResponseData);
						
						if(j==0){
							minAcqTime=calculateResponseData.getFESDiagram().getAcqTime();
						}
					}
				}catch(Exception e){
					continue;
				}
			}
			//计算完以后汇总
			List<?> fesDiagramList = calculateDataService.findCallSql(fesDiagramSql);
			boolean commStatus=false;
			float commTime=0;
			float commTimeEfficiency=0;
			String commRange="";
			
			boolean runStatus=false;
			float runTime=0;
			float runTimeEfficiency=0;
			String runRange="";
			
			List<String> acqTimeList=new ArrayList<String>();
			List<Integer> commStatusList=new ArrayList<Integer>();
			List<Integer> runStatusList=new ArrayList<Integer>();
			
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
				Object[] resuleObj=(Object[]) fesDiagramList.get(j);
				int recordId=StringManagerUtils.stringToInteger(resuleObj[resuleObj.length-1]+"");
				String fesdiagramAcqtime=resuleObj[1]+"";
				String productionData=resuleObj[15].toString();
				type = new TypeToken<RPCCalculateRequestData>() {}.getType();
				RPCCalculateRequestData rpcProductionData=gson.fromJson(productionData, type);
				
				commStatus=StringManagerUtils.stringToInteger(resuleObj[31]+"")==1;
				commTime=StringManagerUtils.stringToFloat(resuleObj[32]+"");
				commTimeEfficiency=StringManagerUtils.stringToFloat(resuleObj[33]+"");
				commRange=StringManagerUtils.CLOBObjectToString(resuleObj[34]);
				
				runStatus=StringManagerUtils.stringToInteger(resuleObj[35]+"")==1;
				runTime=StringManagerUtils.stringToFloat(resuleObj[36]+"");
				runTimeEfficiency=StringManagerUtils.stringToFloat(resuleObj[37]+"");
				runRange=StringManagerUtils.CLOBObjectToString(resuleObj[38]);
				
				acqTimeList.add(fesdiagramAcqtime);
				commStatusList.add(commStatus?1:0);
				runStatusList.add(runStatus?1:0);
				ResultCodeList.add(StringManagerUtils.stringToInteger(resuleObj[2]+""));
				strokeList.add(StringManagerUtils.stringToFloat(resuleObj[3]+""));
				spmList.add(StringManagerUtils.stringToFloat(resuleObj[4]+""));
				FMaxList.add(StringManagerUtils.stringToFloat(resuleObj[5]+""));
				FMinList.add(StringManagerUtils.stringToFloat(resuleObj[6]+""));
				fullnessCoefficientList.add(StringManagerUtils.stringToFloat(resuleObj[7]+""));
				
				theoreticalProductionList.add(StringManagerUtils.stringToFloat(resuleObj[8]+""));
				liquidVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[9]+""));
				oilVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[10]+""));
				waterVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[11]+""));
				if(rpcProductionData!=null&&rpcProductionData.getProduction()!=null){
					volumeWaterCutList.add(rpcProductionData.getProduction().getWaterCut());
				}else{
					volumeWaterCutList.add(0.0f);
				}
				
				liquidWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[12]+""));
				oilWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[13]+""));
				waterWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[14]+""));
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
				
				pumpEffList.add(StringManagerUtils.stringToFloat(resuleObj[16]+""));
				pumpEff1List.add(StringManagerUtils.stringToFloat(resuleObj[17]+""));
				pumpEff2List.add(StringManagerUtils.stringToFloat(resuleObj[18]+""));
				pumpEff3List.add(StringManagerUtils.stringToFloat(resuleObj[19]+""));
				pumpEff4List.add(StringManagerUtils.stringToFloat(resuleObj[20]+""));
				
				wattDegreeBalanceList.add(StringManagerUtils.stringToFloat(resuleObj[21]+""));
				iDegreeBalanceList.add(StringManagerUtils.stringToFloat(resuleObj[22]+""));
				deltaRadiusList.add(StringManagerUtils.stringToFloat(resuleObj[23]+""));
				
				surfaceSystemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[24]+""));
				wellDownSystemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[25]+""));
				systemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[26]+""));
				energyPer100mLiftList.add(StringManagerUtils.stringToFloat(resuleObj[27]+""));

				calcProducingfluidLevelList.add(StringManagerUtils.stringToFloat(resuleObj[28]+""));
				levelDifferenceValueList.add(StringManagerUtils.stringToFloat(resuleObj[29]+""));
				submergenceList.add(StringManagerUtils.stringToFloat(resuleObj[30]+""));
				
				long timeDifference=StringManagerUtils.getTimeDifference(minAcqTime, fesdiagramAcqtime+"", "yyyy-MM-dd HH:mm:ss");
				if(timeDifference>=0){
					StringBuffer dataSbf = new StringBuffer();
					dataSbf.append("{\"AKString\":\"\",");
					dataSbf.append("\"WellName\":\""+wellNo+"\",");
					dataSbf.append("\"Date\":\""+acqDate+"\",");
					dataSbf.append("\"OffsetHour\":0,");
					dataSbf.append("\"AcqTime\":["+StringManagerUtils.joinStringArr(acqTimeList, ",")+"],");
					dataSbf.append("\"CommStatus\":["+StringUtils.join(commStatusList, ",")+"],");
					dataSbf.append("\"CommTime\":"+commTime+",");
					dataSbf.append("\"CommTimeEfficiency\":"+commTimeEfficiency+",");
					dataSbf.append("\"CommRange\":\""+commRange+"\",");
					dataSbf.append("\"RunStatus\":["+StringUtils.join(runStatusList, ",")+"],");
					dataSbf.append("\"RunTime\":"+runTime+",");
					dataSbf.append("\"RunTimeEfficiency\":"+runTimeEfficiency+",");
					dataSbf.append("\"RunRange\":\""+runRange+"\",");
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
					dataSbf.append("\"CasingPressure\":["+StringUtils.join(casingPressureList, ",")+"]");
					
					dataSbf.append("}");
					
					type = new TypeToken<TotalAnalysisRequestData>() {}.getType();
					TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(dataSbf.toString(), type);
					TotalAnalysisResponseData totalAnalysisResponseData=CalculateUtils.totalCalculate(dataSbf.toString());
					totalCount++;
					
					if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
						try {
							String updateSql="update tbl_rpcacqdata_hist t "
									+ " set t.liquidvolumetricproduction_l="+totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+","
									+ " t.liquidweightproduction_l="+totalAnalysisResponseData.getLiquidWeightProduction().getValue()
									+ " where t.id="+recordId;
							calculateDataService.getBaseDao().updateOrDeleteBySql(updateSql);
							
							if(currentDate.equalsIgnoreCase(acqDate)){//如果是当天数据
								if(j==fesDiagramList.size()-1){//最后一条记录汇总后，更新实时表及汇总表数据
									updateSql="update tbl_rpcacqdata_latest t "
											+ " set t.liquidvolumetricproduction_l="+totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+","
											+ " t.liquidweightproduction_l="+totalAnalysisResponseData.getLiquidWeightProduction().getValue()
											+ " where t.wellid="+wellNo+" and t.fesdiagramAcqtime=to_date('"+fesdiagramAcqtime+"','yyyy-mm-dd hh24:mi:ss')";
									calculateDataService.getBaseDao().updateOrDeleteBySql(updateSql);
									calculateDataService.saveFSDiagramDailyCalculationData(totalAnalysisResponseData,totalAnalysisRequestData,acqDate);
								}
							}
						} catch (SQLException | ParseException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			if(currentDate.equalsIgnoreCase(acqDate)){//如果是当天数据
				List<String> wellList=new ArrayList<String>();
				wellList.add(wellNo+"");
				MemoryDataManagerTask.loadTodayFESDiagram(wellList,0);
			}
		}else{
//			String acqDateSql="select distinct(to_char(t.acqtime,'yyyy-mm-dd')) as acqdate"
//					+ " from tbl_pcpacqdata_hist t "
//					+ " where 1=1  "
//					+ " and t.productiondata is not null and t.rpm is not null "
//					+ " and t.wellid="+wellNo+""
//					+ " and t.resultstatus =2 "
//					+ " order by acqdate";
//			List<?> acqDateList = calculateDataService.findCallSql(acqDateSql);
//			for(int i=0;i<acqDateList.size();i++){
//				if(acqDateList.get(i)!=null){}
//			}
			

//			String acqDate=acqDateList.get(i).toString();
			String minAcqTime="";
			String sql="select t2.wellname,decode(t2.applicationscenarios,0,'cbm','oil') as applicationscenarios,"
					+ " to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss'),"
					+ " t.rpm,t.productiondata,"
					+ " t.id"
					+ " from tbl_pcpacqdata_hist t"
					+ " left outer join tbl_pcpdevice t2 on t.wellid=t2.id"
					+ " where 1=1  "
					+ " and t.acqTime between to_date('"+acqDate+"','yyyy-mm-dd') and to_date('"+acqDate+"','yyyy-mm-dd')+1 "
					+ " and t.productiondata is not null and t.rpm is not null "
					+ " and t.wellid="+wellNo+""
					+ " and t.resultstatus =2 "
					+ " order by t.acqTime ";
			String singleRecordSql="select t2.id as wellId, "
					+ "to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss'),t.rpm,"
					+ "t.theoreticalproduction,t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
					+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
					+ "t.productiondata,"
					+ "t.pumpeff,t.pumpeff1,t.pumpeff2,"
					+ "t.systemefficiency,t.energyper100mlift,"
					+ "t.submergence,"//16
					+ "t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,"
					+ "t.runstatus,t.runtime,t.runtimeefficiency,t.runrange,"
					+ "t.id as recordId"
					+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2 "
					+ " where t.wellid=t2.id "
					+ " and t.resultstatus=1 "
					+ " and t.acqtime between to_date('"+acqDate+"','yyyy-mm-dd') and to_date('"+acqDate+"','yyyy-mm-dd')+1 "
					+ " and t.wellid="+wellNo+""
					+ " order by t.acqTime ";
			List<?> list = calculateDataService.findCallSql(sql);
			count=list.size();
			for(int j=0;j<list.size();j++){
				try{
					Object[] obj=(Object[])list.get(j);
					String requestData=calculateDataService.getObjectToRPMCalculateRequestData(obj);
					type = new TypeToken<PCPCalculateRequestData>() {}.getType();
					PCPCalculateRequestData calculateRequestData=gson.fromJson(requestData, type);
					if(calculateRequestData!=null){
						PCPCalculateResponseData calculateResponseData=CalculateUtils.rpmCalculate(requestData);
						int recordId=Integer.parseInt(obj[obj.length-1].toString());
						if(calculateResponseData==null){
							System.out.println("记录:"+recordId+"计算无数据返回");
						}
						calculateDataService.getBaseDao().saveRPMCalculateResult(recordId,calculateResponseData);
						
						if(j==0){
							minAcqTime=calculateResponseData.getAcqTime();
						}
					}
				}catch(Exception e){
					continue;
				}
			}
			//计算完以后汇总
			List<?> singleRecordList = calculateDataService.findCallSql(singleRecordSql);
			boolean commStatus=false;
			float commTime=0;
			float commTimeEfficiency=0;
			String commRange="";
			
			boolean runStatus=false;
			float runTime=0;
			float runTimeEfficiency=0;
			String runRange="";
			
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
			
			for(int j=0;j<singleRecordList.size();j++){
				Object[] resuleObj=(Object[]) singleRecordList.get(j);
				int recordId=StringManagerUtils.stringToInteger(resuleObj[resuleObj.length-1]+"");
				String acqtime=resuleObj[1]+"";
				String productionData=resuleObj[10].toString();
				type = new TypeToken<PCPCalculateRequestData>() {}.getType();
				PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
				
				commStatus=StringManagerUtils.stringToInteger(resuleObj[17]+"")==1;
				commTime=StringManagerUtils.stringToFloat(resuleObj[18]+"");
				commTimeEfficiency=StringManagerUtils.stringToFloat(resuleObj[19]+"");
				commRange=StringManagerUtils.CLOBObjectToString(resuleObj[20]);
				
				runStatus=StringManagerUtils.stringToInteger(resuleObj[21]+"")==1;
				runTime=StringManagerUtils.stringToFloat(resuleObj[22]+"");
				runTimeEfficiency=StringManagerUtils.stringToFloat(resuleObj[23]+"");
				runRange=StringManagerUtils.CLOBObjectToString(resuleObj[24]);
				
				acqTimeList.add(acqtime);
				commStatusList.add(commStatus?1:0);
				runStatusList.add(runStatus?1:0);
				rpmList.add(StringManagerUtils.stringToFloat(resuleObj[2]+""));
				
				theoreticalProductionList.add(StringManagerUtils.stringToFloat(resuleObj[3]+""));
				liquidVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[4]+""));
				oilVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[5]+""));
				waterVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[6]+""));
				
				if(pcpProductionData!=null&&pcpProductionData.getProduction()!=null){
					volumeWaterCutList.add(pcpProductionData.getProduction().getWaterCut());
				}else{
					volumeWaterCutList.add(0.0f);
				}
				
				
				liquidWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[7]+""));
				oilWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[8]+""));
				waterWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[9]+""));
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
				
				pumpEffList.add(StringManagerUtils.stringToFloat(resuleObj[11]+""));
				pumpEff1List.add(StringManagerUtils.stringToFloat(resuleObj[12]+""));
				pumpEff2List.add(StringManagerUtils.stringToFloat(resuleObj[13]+""));
				
				systemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[14]+""));
				energyPer100mLiftList.add(StringManagerUtils.stringToFloat(resuleObj[15]+""));
				
				submergenceList.add(StringManagerUtils.stringToFloat(resuleObj[16]+""));
				
				long timeDifference=StringManagerUtils.getTimeDifference(minAcqTime, acqtime+"", "yyyy-MM-dd HH:mm:ss");
				if(timeDifference>=0){
					StringBuffer dataSbf = new StringBuffer();
					dataSbf.append("{\"AKString\":\"\",");
					dataSbf.append("\"WellName\":\""+wellNo+"\",");
					dataSbf.append("\"Date\":\""+acqDate+"\",");
					dataSbf.append("\"OffsetHour\":0,");
					dataSbf.append("\"AcqTime\":["+StringManagerUtils.joinStringArr(acqTimeList, ",")+"],");
					dataSbf.append("\"CommStatus\":["+StringUtils.join(commStatusList, ",")+"],");
					dataSbf.append("\"CommTime\":"+commTime+",");
					dataSbf.append("\"CommTimeEfficiency\":"+commTimeEfficiency+",");
					dataSbf.append("\"CommRange\":\""+commRange+"\",");
					dataSbf.append("\"RunStatus\":["+StringUtils.join(runStatusList, ",")+"],");
					dataSbf.append("\"RunTime\":"+runTime+",");
					dataSbf.append("\"RunTimeEfficiency\":"+runTimeEfficiency+",");
					dataSbf.append("\"RunRange\":\""+runRange+"\",");
					dataSbf.append("\"RPM\":["+StringUtils.join(rpmList, ",")+"],");
					
					dataSbf.append("\"PumpSettingDepth\":["+StringUtils.join(pumpSettingDepthList, ",")+"],");
					dataSbf.append("\"ProducingfluidLevel\":["+StringUtils.join(producingfluidLevelList, ",")+"],");
					dataSbf.append("\"Submergence\":["+StringUtils.join(submergenceList, ",")+"],");
					
					dataSbf.append("\"TubingPressure\":["+StringUtils.join(tubingPressureList, ",")+"],");
					dataSbf.append("\"CasingPressure\":["+StringUtils.join(casingPressureList, ",")+"],");
					
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
					dataSbf.append("\"PumpEff2\":["+StringUtils.join(pumpEff2List, ",")+"]");
					dataSbf.append("}");
					
					type = new TypeToken<TotalAnalysisRequestData>() {}.getType();
					TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(dataSbf.toString(), type);
					TotalAnalysisResponseData totalAnalysisResponseData=CalculateUtils.totalCalculate(dataSbf.toString());
					totalCount++;
					
					if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
						try {
							String updateSql="update tbl_pcpacqdata_hist t "
									+ " set t.liquidvolumetricproduction_l="+totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+","
									+ " t.liquidweightproduction_l="+totalAnalysisResponseData.getLiquidWeightProduction().getValue()
									+ " where t.id="+recordId;
							calculateDataService.getBaseDao().updateOrDeleteBySql(updateSql);
							
							if(currentDate.equalsIgnoreCase(acqDate)){//如果是当天数据
								if(j==singleRecordList.size()-1){//最后一条记录汇总后，更新实时表及汇总表数据
									updateSql="update tbl_pcpacqdata_latest t "
											+ " set t.liquidvolumetricproduction_l="+totalAnalysisResponseData.getLiquidVolumetricProduction().getValue()+","
											+ " t.liquidweightproduction_l="+totalAnalysisResponseData.getLiquidWeightProduction().getValue()
											+ " where t.wellid="+wellNo+" and t.acqtime=to_date('"+acqtime+"','yyyy-mm-dd hh24:mi:ss')";
									calculateDataService.getBaseDao().updateOrDeleteBySql(updateSql);
									calculateDataService.saveRPMTotalCalculateData(totalAnalysisResponseData,totalAnalysisRequestData,acqDate);
								}
							}
						} catch (SQLException | ParseException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			if(currentDate.equalsIgnoreCase(acqDate)){//如果是当天数据
				List<String> wellList=new ArrayList<String>();
				wellList.add(wellNo+"");
				MemoryDataManagerTask.loadTodayRPMData(wellList,0);
			}
		}
		long endTime=new Date().getTime();
		System.out.println("计算单条记录数:"+count+",汇总记录数:"+totalCount+",共用时:"+(endTime-startTime)+"ms");
	}
	


	public int getThreadId() {
		return threadId;
	}


	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}


	public int getWellNo() {
		return wellNo;
	}


	public void setWellNo(int wellNo) {
		this.wellNo = wellNo;
	}


	public CalculateDataService<?> getCalculateDataService() {
		return calculateDataService;
	}


	public void setCalculateDataService(CalculateDataService<?> calculateDataService) {
		this.calculateDataService = calculateDataService;
	}
}
