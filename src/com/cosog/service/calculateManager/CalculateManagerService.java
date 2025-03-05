package com.cosog.service.calculateManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cosog.dao.BaseDao;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.AuxiliaryDeviceAddInfo;
import com.cosog.model.Code;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.SRPCalculateRequestData;
import com.cosog.model.calculate.SRPDeviceTodayData;
import com.cosog.model.calculate.SRPProductionData;
import com.cosog.model.calculate.TotalAnalysisResponseData;
import com.cosog.model.calculate.SRPCalculateRequestData.EveryCasing;
import com.cosog.model.calculate.SRPCalculateRequestData.EveryRod;
import com.cosog.model.calculate.SRPCalculateRequestData.EveryTubing;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.gridmodel.CalculateManagerHandsontableChangedData;
import com.cosog.model.gridmodel.ElecInverCalculateManagerHandsontableChangedData;
import com.cosog.model.gridmodel.WellHandsontableChangedData;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.service.datainterface.CalculateDataService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.thread.calculate.CalculateThread;
import com.cosog.thread.calculate.ThreadPool;
import com.cosog.thread.calculate.TotalCalculateThread;
import com.cosog.utils.CalculateUtils;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.Page;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.BLOB;
import oracle.sql.CLOB;
import redis.clients.jedis.Jedis;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.hibernate.engine.jdbc.SerializableClobProxy;

/**
 * <p>工况诊断（单张） --service层</p>
 * 
 * @author gao 2014-06-04
 * 
 */
@Component("calculateManagerService")
public class CalculateManagerService<T> extends BaseService<T> {

	private BaseDao dao;
	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	@Autowired
	private CalculateDataService calculateDataService;
	public String getCalculateResultData(String orgId, String deviceName,String deviceId,String applicationScenarios, Page pager,String deviceType,String startDate,String endDate,String calculateSign,String resultCode,String calculateType,String language)
			throws Exception {
		String json="";
		if("1".equals(calculateType)){
			json=this.getFESDiagramCalculateResultData(orgId, deviceName,deviceId,applicationScenarios, pager, deviceType, startDate, endDate, calculateSign, resultCode, calculateType,language);
		}else if("2".equals(calculateType)){
			json=this.getRPMCalculateResultData(orgId, deviceName,deviceId,applicationScenarios, pager, deviceType, startDate, endDate, calculateSign, calculateType,language);
		}else if("5".equals(calculateType)){//电参反演地面功图
			
		}
		
		return json;
	}
	
	public String getWellList(String orgId, String deviceName, Page pager,String deviceType,String calculateSign,String calculateType,String language)
			throws Exception {
		String json="";
		if("1".equals(calculateType)||"2".equals(calculateType)||"3".equals(calculateType)||"4".equals(calculateType)){
			json=this.getDiagnoseAndProdCalculateWellListData(orgId, deviceName, pager, deviceType,calculateSign, calculateType,language);
		}else if("5".equals(calculateType)){//电参反演地面功图
			
		}
		
		return json;
	}
	
	public String getFESDiagramCalculateResultData(String orgId, String deviceName,String deviceId,String applicationScenarios, Page pager,String deviceType,String startDate,String endDate,String calculateSign,String resultCodeStr,String calculateType,String language)
			throws Exception {
		DataDictionary ddic = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicCode="calculateManager_SRPSingleRecord";
		StringBuffer result_json = new StringBuffer();
		StringBuffer resultNameBuff = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Map<String,WorkType> workTypeMap=MemoryDataManagerTask.getWorkTypeMap(language);
		try{
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicCode,language);
			columns = ddic.getTableHeader();
			
			String prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				prodCol=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,";
			}
			
			sql="select t.id,t.deviceId,t.deviceName,to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss'),"
				+ "decode(t.resultStatus,-1,'"+languageResourceMap.get("calculateDataException")+"',1,'"+languageResourceMap.get("calculateSuccessfully")+"',0,'"+languageResourceMap.get("notCalculate")+"',2,'"+languageResourceMap.get("notCalculate")+"','"+languageResourceMap.get("calculateFailure")+"'),"
				+ "t.resultcode,"
				+ prodCol
				+ "t.productiondata"
				+ " from viw_srp_calculatemain t "
				+ " where t.deviceid="+deviceId
				+ " and t.resultStatus<>-1"
				+ " and t.fesdiagramacqtime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss')";
			if(StringManagerUtils.isNotNull(calculateSign)){
				if("0".equals(calculateSign)){
					sql+=" and  t.resultstatus in(0,2) ";
				}else{
					sql+=" and  t.resultstatus = " + calculateSign + " ";
				}
			}
			
			if(StringManagerUtils.isNotNull(resultCodeStr)){
				sql+=" and t.resultcode="+resultCodeStr;
			}
			
			int totals=this.getTotalCountRows(sql);
			
			sql+=" order by t.fesdiagramacqtime desc";
			int maxvalue=pager.getLimit()+pager.getStart();
			finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
			
			resultNameBuff.append("[\""+languageResourceMap.get("noIntervention")+"\"");
			Iterator<Map.Entry<String, WorkType>> it = workTypeMap.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<String, WorkType> entry = it.next();
				String resultCode=new String(entry.getKey());
				WorkType w=entry.getValue();
				resultNameBuff.append(",\""+w.getResultName()+"\"");
			}
			resultNameBuff.append("]");
			
			List<?> list = this.findCallSql(finalSql);
			
			result_json.append("{ \"success\":true,\"columns\":"+columns+",");
			result_json.append("\"applicationScenarios\":"+applicationScenarios+",");
			result_json.append("\"start_date\":\""+startDate+"\",");
			result_json.append("\"end_date\":\""+endDate+"\",");
			result_json.append("\"totalCount\":"+totals+",");
			result_json.append("\"resultNameList\":"+resultNameBuff+",");
			result_json.append("\"totalRoot\":[");
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				String productionData=obj[9].toString();
				type = new TypeToken<SRPCalculateRequestData>() {}.getType();
				SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
				
				result_json.append("{\"recordId\":\""+obj[0]+"\",");
				result_json.append("\"id\":"+(i+1)+",");
				result_json.append("\"checked\":false,");
				result_json.append("\"deviceId\":\""+obj[1]+"\",");
				result_json.append("\"deviceName\":\""+obj[2]+"\",");
				result_json.append("\"acqTime\":\""+obj[3]+"\",");
				result_json.append("\"resultStatus\":\""+obj[4]+"\",");
				result_json.append("\"resultName\":\""+(workTypeMap.get(obj[5]+"")!=null?workTypeMap.get(obj[5]+"").getResultName():"")+"\",");
				
				if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
					result_json.append("\"liquidWeightProduction\":\""+obj[6]+"\",");
					result_json.append("\"oilWeightProduction\":\""+obj[7]+"\",");
					result_json.append("\"waterWeightProduction\":\""+obj[8]+"\",");
				}else{
					result_json.append("\"liquidVolumetricProduction\":\""+obj[6]+"\",");
					result_json.append("\"oilVolumetricProduction\":\""+obj[7]+"\",");
					result_json.append("\"waterVolumetricProduction\":\""+obj[8]+"\",");
				}
				
				if(srpProductionData!=null){
					if(srpProductionData.getFluidPVT()!=null){
						result_json.append("\"crudeoilDensity\":\""+srpProductionData.getFluidPVT().getCrudeOilDensity()+"\",");
						result_json.append("\"waterDensity\":\""+srpProductionData.getFluidPVT().getWaterDensity()+"\",");
						result_json.append("\"naturalGasRelativeDensity\":\""+srpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"\",");
						result_json.append("\"saturationPressure\":\""+srpProductionData.getFluidPVT().getSaturationPressure()+"\",");
					}
					if(srpProductionData.getReservoir()!=null){
						result_json.append("\"reservoirDepth\":\""+srpProductionData.getReservoir().getDepth()+"\",");
						result_json.append("\"reservoirTemperature\":\""+srpProductionData.getReservoir().getTemperature()+"\",");
					}
					if(srpProductionData.getProduction()!=null){
						result_json.append("\"tubingPressure\":\""+srpProductionData.getProduction().getTubingPressure()+"\",");
						result_json.append("\"casingPressure\":\""+srpProductionData.getProduction().getCasingPressure()+"\",");
						result_json.append("\"wellHeadFluidTemperature\":\""+srpProductionData.getProduction().getWellHeadTemperature()+"\",");
						result_json.append("\"weightWaterCut\":\""+srpProductionData.getProduction().getWaterCut()+"\",");
						result_json.append("\"productionGasOilRatio\":\""+srpProductionData.getProduction().getProductionGasOilRatio()+"\",");
						result_json.append("\"producingFluidLevel\":\""+srpProductionData.getProduction().getProducingfluidLevel()+"\",");
						result_json.append("\"pumpSettingDepth\":\""+srpProductionData.getProduction().getPumpSettingDepth()+"\",");
					}
					if(srpProductionData.getPump()!=null){
						String barrelType="";
						if(srpProductionData.getPump()!=null&&srpProductionData.getPump().getBarrelType()!=null){
							if("L".equalsIgnoreCase(srpProductionData.getPump().getBarrelType())){
								barrelType=languageResourceMap.get("barrelType_L");
							}else if("H".equalsIgnoreCase(srpProductionData.getPump().getBarrelType())){
								barrelType=languageResourceMap.get("barrelType_H");
							}
						}
						result_json.append("\"barrelTypeName\":\""+barrelType+"\",");
						result_json.append("\"pumpGrade\":\""+srpProductionData.getPump().getPumpGrade()+"\",");
						result_json.append("\"pumpboreDiameter\":\""+srpProductionData.getPump().getPumpBoreDiameter()*1000+"\",");
						result_json.append("\"plungerLength\":\""+srpProductionData.getPump().getPlungerLength()+"\",");
					}
					if(srpProductionData.getTubingString()!=null&&srpProductionData.getTubingString().getEveryTubing()!=null&&srpProductionData.getTubingString().getEveryTubing().size()>0){
						result_json.append("\"tubingStringInsideDiameter\":\""+srpProductionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000+"\",");
					}
					if(srpProductionData.getCasingString()!=null&&srpProductionData.getCasingString().getEveryCasing()!=null&&srpProductionData.getCasingString().getEveryCasing().size()>0){
						result_json.append("\"casingStringInsideDiameter\":\""+srpProductionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000+"\",");
					}
					
					if(srpProductionData.getRodString()!=null && srpProductionData.getRodString().getEveryRod()!=null){
						for(int j=0;j<srpProductionData.getRodString().getEveryRod().size();j++){
							String rodType=languageResourceMap.get("rodStringTypeValue1");
							if(srpProductionData.getRodString().getEveryRod().get(j).getType()==1){
								rodType=languageResourceMap.get("rodStringTypeValue1");
							}else if(srpProductionData.getRodString().getEveryRod().get(j).getType()==2){
								rodType=languageResourceMap.get("rodStringTypeValue2");
							}else if(srpProductionData.getRodString().getEveryRod().get(j).getType()==3){
								rodType=languageResourceMap.get("rodStringTypeValue3");
							}
							result_json.append("\"rodTypeName"+(j+1)+"\":\""+rodType+"\",");
							result_json.append("\"rodGrade"+(j+1)+"\":\""+srpProductionData.getRodString().getEveryRod().get(j).getGrade()+"\",");
							result_json.append("\"rodOutsideDiameter"+(j+1)+"\":\""+srpProductionData.getRodString().getEveryRod().get(j).getOutsideDiameter()*1000+"\",");
							result_json.append("\"rodInsideDiameter"+(j+1)+"\":\""+srpProductionData.getRodString().getEveryRod().get(j).getInsideDiameter()*1000+"\",");
							result_json.append("\"rodLength"+(j+1)+"\":\""+srpProductionData.getRodString().getEveryRod().get(j).getLength()+"\",");
						}
					}
					
					if(srpProductionData.getManualIntervention()!=null){
						String manualInterventionResultName="";
						if(srpProductionData.getManualIntervention().getCode()==0){
							manualInterventionResultName=languageResourceMap.get("noIntervention");
						}else{
							WorkType workType=MemoryDataManagerTask.getWorkTypeByCode(srpProductionData.getManualIntervention().getCode()+"",language);
							if(workType!=null){
								manualInterventionResultName=workType.getResultName();
							}
						}
						result_json.append("\"manualInterventionResult\":\""+manualInterventionResultName+"\",");
						result_json.append("\"netGrossRatio\":\""+srpProductionData.getManualIntervention().getNetGrossRatio()+"\",");
						result_json.append("\"netGrossValue\":\""+srpProductionData.getManualIntervention().getNetGrossValue()+"\",");
						result_json.append("\"levelCorrectValue\":\""+srpProductionData.getManualIntervention().getLevelCorrectValue()+"\",");
					}
				}else{
					
				}
				
				if(result_json.toString().endsWith(",")){
					result_json = result_json.deleteCharAt(result_json.length() - 1);
				}
				result_json.append("},");
				
				
			}
			if(result_json.toString().endsWith(",")){
				result_json = result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]}");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getRPMCalculateResultData(String orgId, String deviceName,String deviceId,String applicationScenarios, Page pager,String deviceType,String startDate,String endDate,String calculateSign,String calculateType,String language)
			throws Exception {
		DataDictionary ddic = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicCode="calculateManager_PCPSingleRecord";
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicCode,language);
		columns = ddic.getTableHeader();
		
		String prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			prodCol=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,";
		}
		
		sql="select t.id,t.deviceId,t.deviceName,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss'),"
			+ "decode(t.resultStatus,1,'"+languageResourceMap.get("calculateSuccessfully")+"',0,'"+languageResourceMap.get("notCalculate")+"',2,'"+languageResourceMap.get("notCalculate")+"','"+languageResourceMap.get("calculateFailure")+"'),"
			+ prodCol
			+ "t.rpm,"
			+ "t.productiondata"
			+ " from viw_pcp_calculatemain t "
			+ " where t.deviceid="+deviceId
			+ " and t.acqtime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss')";
		
		if(StringManagerUtils.isNotNull(calculateSign)){
			if("0".equals(calculateSign)){
				sql+=" and  t.resultstatus in(0,2) ";
			}else{
				sql+=" and  t.resultstatus = " + calculateSign + " ";
			}
		}
		sql+=" order by t.acqtime desc";
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"applicationScenarios\":"+applicationScenarios+",");
		result_json.append("\"start_date\":\""+startDate+"\",");
		result_json.append("\"end_date\":\""+endDate+"\",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String productionData=obj[9].toString();
			type = new TypeToken<PCPCalculateRequestData>() {}.getType();
			PCPCalculateRequestData calculateRequestData=gson.fromJson(productionData, type);
			
			result_json.append("{\"recordId\":\""+obj[0]+"\",");
			result_json.append("\"id\":"+(i+1)+",");
			result_json.append("\"checked\":false,");
			result_json.append("\"deviceId\":\""+obj[1]+"\",");
			result_json.append("\"deviceName\":\""+obj[2]+"\",");
			result_json.append("\"acqTime\":\""+obj[3]+"\",");
			result_json.append("\"resultStatus\":\""+obj[4]+"\",");
			
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				result_json.append("\"liquidWeightProduction\":\""+obj[5]+"\",");
				result_json.append("\"oilWeightProduction\":\""+obj[6]+"\",");
				result_json.append("\"waterWeightProduction\":\""+obj[7]+"\",");
			}else{
				result_json.append("\"liquidVolumetricProduction\":\""+obj[5]+"\",");
				result_json.append("\"oilVolumetricProduction\":\""+obj[6]+"\",");
				result_json.append("\"waterVolumetricProduction\":\""+obj[7]+"\",");
			}
			result_json.append("\"rpm\":\""+obj[8]+"\",");
			if(calculateRequestData!=null){
				if(calculateRequestData.getFluidPVT()!=null){
					result_json.append("\"crudeoilDensity\":\""+calculateRequestData.getFluidPVT().getCrudeOilDensity()+"\",");
					result_json.append("\"waterDensity\":\""+calculateRequestData.getFluidPVT().getWaterDensity()+"\",");
					result_json.append("\"naturalGasRelativeDensity\":\""+calculateRequestData.getFluidPVT().getNaturalGasRelativeDensity()+"\",");
					result_json.append("\"saturationPressure\":\""+calculateRequestData.getFluidPVT().getSaturationPressure()+"\",");
				}
				if(calculateRequestData.getReservoir()!=null){
					result_json.append("\"reservoirDepth\":\""+calculateRequestData.getReservoir().getDepth()+"\",");
					result_json.append("\"reservoirTemperature\":\""+calculateRequestData.getReservoir().getTemperature()+"\",");
				}
				if(calculateRequestData.getProduction()!=null){
					result_json.append("\"tubingPressure\":\""+calculateRequestData.getProduction().getTubingPressure()+"\",");
					result_json.append("\"casingPressure\":\""+calculateRequestData.getProduction().getCasingPressure()+"\",");
					result_json.append("\"wellHeadFluidTemperature\":\""+calculateRequestData.getProduction().getWellHeadTemperature()+"\",");
					result_json.append("\"weightWaterCut\":\""+calculateRequestData.getProduction().getWaterCut()+"\",");
					result_json.append("\"productionGasOilRatio\":\""+calculateRequestData.getProduction().getProductionGasOilRatio()+"\",");
					result_json.append("\"producingFluidLevel\":\""+calculateRequestData.getProduction().getProducingfluidLevel()+"\",");
					result_json.append("\"pumpSettingDepth\":\""+calculateRequestData.getProduction().getPumpSettingDepth()+"\",");
				}
				if(calculateRequestData.getPump()!=null){
					
					result_json.append("\"barrelLength\":\""+calculateRequestData.getPump().getBarrelLength()+"\",");
					result_json.append("\"barrelSeries\":\""+calculateRequestData.getPump().getBarrelSeries()+"\",");
					result_json.append("\"rotorDiameter\":\""+calculateRequestData.getPump().getRotorDiameter()*1000+"\",");
					result_json.append("\"qpr\":\""+calculateRequestData.getPump().getQPR()*1000*1000+"\",");
				}
				if(calculateRequestData.getTubingString()!=null&&calculateRequestData.getTubingString().getEveryTubing()!=null&&calculateRequestData.getTubingString().getEveryTubing().size()>0){
					result_json.append("\"tubingStringInsideDiameter\":\""+calculateRequestData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000+"\",");
				}
				if(calculateRequestData.getCasingString()!=null&&calculateRequestData.getCasingString().getEveryCasing()!=null&&calculateRequestData.getCasingString().getEveryCasing().size()>0){
					result_json.append("\"casingStringInsideDiameter\":\""+calculateRequestData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000+"\",");
				}
				
				if(calculateRequestData.getRodString()!=null && calculateRequestData.getRodString().getEveryRod()!=null){
					for(int j=0;j<calculateRequestData.getRodString().getEveryRod().size();j++){
						String rodType=languageResourceMap.get("rodStringTypeValue1");
						if(calculateRequestData.getRodString().getEveryRod().get(j).getType()==1){
							rodType=languageResourceMap.get("rodStringTypeValue1");
						}else if(calculateRequestData.getRodString().getEveryRod().get(j).getType()==2){
							rodType=languageResourceMap.get("rodStringTypeValue2");
						}else if(calculateRequestData.getRodString().getEveryRod().get(j).getType()==3){
							rodType=languageResourceMap.get("rodStringTypeValue3");
						}
						result_json.append("\"rodTypeName"+(j+1)+"\":\""+rodType+"\",");
						result_json.append("\"rodGrade"+(j+1)+"\":\""+calculateRequestData.getRodString().getEveryRod().get(j).getGrade()+"\",");
						result_json.append("\"rodOutsideDiameter"+(j+1)+"\":\""+calculateRequestData.getRodString().getEveryRod().get(j).getOutsideDiameter()*1000+"\",");
						result_json.append("\"rodInsideDiameter"+(j+1)+"\":\""+calculateRequestData.getRodString().getEveryRod().get(j).getInsideDiameter()*1000+"\",");
						result_json.append("\"rodLength"+(j+1)+"\":\""+calculateRequestData.getRodString().getEveryRod().get(j).getLength()+"\",");
					}
				}
				
				if(calculateRequestData.getManualIntervention()!=null){
					result_json.append("\"netGrossRatio\":\""+calculateRequestData.getManualIntervention().getNetGrossRatio()+"\",");
					result_json.append("\"netGrossValue\":\""+calculateRequestData.getManualIntervention().getNetGrossValue()+"\",");
				}
			}else{
				
			}
			
			if(result_json.toString().endsWith(",")){
				result_json = result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("},");
			
			
		}
		if(result_json.toString().endsWith(",")){
			result_json = result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		
//		String getResult = this.findCustomPageBySqlEntity(sql,finalSql, columns, 20 + "", pager);
		String json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getDiagnoseAndProdCalculateWellListData(String orgId, String deviceName, Page pager,String deviceType,String calculateSign,String calculateType,String language)
			throws Exception {
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String tableName="tbl_srpacqdata_latest";
		String deviceTableName="tbl_device";
		StringBuffer result_json = new StringBuffer();
		if("1".equals(calculateType) || "3".equals(calculateType)){
			tableName="tbl_srpacqdata_latest";
		}else if("2".equals(calculateType) || "4".equals(calculateType)){
			tableName="tbl_pcpacqdata_latest";
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("deviceName")+"\",\"dataIndex\":\"deviceName\",flex:3 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("applicationScenarios")+"\",\"dataIndex\":\"applicationScenariosName\",flex:3 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("acqTime")+"\",\"dataIndex\":\"acqTime\",flex:5,width:150,children:[] }"
				+ "]";
		sql="select well.id,well.deviceName,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,t.resultstatus,"
				+ " well.applicationscenarios "
				+ " from "+tableName+" t,"+deviceTableName+" well "
				+ " where t.deviceId=well.id "
				+ " and well.orgid in("+orgId+") ";
		if(StringManagerUtils.isNum(deviceType)){
			sql+= " and well.devicetype="+deviceType;
		}else{
			sql+= " and well.devicetype in ("+deviceType+")";
		}
		if("1".equals(calculateType) || "3".equals(calculateType)){
			sql+= " and well.calculateType in (1,3)";
		}else if("2".equals(calculateType) || "4".equals(calculateType)){
			sql+= " and well.calculateType in (2,4)";
		}
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and  well.deviceName = '" + deviceName.trim() + "' ";
		}
		if(StringManagerUtils.isNotNull(calculateSign)){
			if("0".equals(calculateSign)){
				sql+=" and  t.resultstatus in(0,2) ";
			}else{
				sql+=" and  t.resultstatus = " + calculateSign + " ";
			}
		}
		sql+=" order by well.sortnum,well.deviceName";
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		
		result_json.append("{\"success\":true,\"totalCount\":"+totals+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"deviceName\":\""+obj[1]+"\",");
			result_json.append("\"acqTime\":\""+obj[2]+"\",");
			result_json.append("\"applicationScenarios\":\""+obj[4]+"\",");
			result_json.append("\"applicationScenariosName\":\""+MemoryDataManagerTask.getCodeName("APPLICATIONSCENARIOS",obj[4]+"", language)+"\"},");
			
		}
		if(result_json.toString().endsWith(",")){
			result_json = result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		
		String json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public void saveReCalculateData(CalculateManagerHandsontableChangedData calculateManagerHandsontableChangedData,int applicationScenarios,String language) throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		if(calculateManagerHandsontableChangedData.getUpdatelist()!=null){
			try{
				for(int i=0;i<calculateManagerHandsontableChangedData.getUpdatelist().size();i++){
					StringBuffer productionDataBuff = new StringBuffer();
					
					String productionSql="select t.productiondata from tbl_srpacqdata_hist t where t.id="+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRecordId();
					List<?> list = this.findCallSql(productionSql);
					String productionData="";
					if(list.size()>0){
						productionData=list.get(0)+"";
					}
					type = new TypeToken<SRPCalculateRequestData>() {}.getType();
					SRPCalculateRequestData calculateRequestData=gson.fromJson(productionData, type);
					if(calculateRequestData==null){
						calculateRequestData=new SRPCalculateRequestData();
						calculateRequestData.init();
					}
					
					SRPCalculateRequestData.FluidPVT fluidPVT=new SRPCalculateRequestData.FluidPVT();
					SRPCalculateRequestData.Reservoir reservoir=new SRPCalculateRequestData.Reservoir();
					
					SRPCalculateRequestData.TubingString tubingString=new SRPCalculateRequestData.TubingString();
					tubingString.setEveryTubing(new ArrayList<SRPCalculateRequestData.EveryTubing>());
					tubingString.getEveryTubing().add(new SRPCalculateRequestData.EveryTubing());
					
					SRPCalculateRequestData.CasingString  casingString=new SRPCalculateRequestData.CasingString();
					casingString.setEveryCasing(new ArrayList<SRPCalculateRequestData.EveryCasing>());
					casingString.getEveryCasing().add(new SRPCalculateRequestData.EveryCasing());
					
					SRPCalculateRequestData.RodString rodString=new SRPCalculateRequestData.RodString();
					rodString.setEveryRod(new ArrayList<SRPCalculateRequestData.EveryRod>());
					
					SRPCalculateRequestData.Production production=new SRPCalculateRequestData.Production();
					
					SRPCalculateRequestData.Pump pump =new SRPCalculateRequestData.Pump();
					
					SRPCalculateRequestData.ManualIntervention manualIntervention=new SRPCalculateRequestData.ManualIntervention();
					
					if(applicationScenarios!=0){
						fluidPVT.setCrudeOilDensity(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getCrudeoilDensity()));
						fluidPVT.setSaturationPressure(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getSaturationPressure()));
					}
					fluidPVT.setWaterDensity(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWaterDensity()));
					fluidPVT.setNaturalGasRelativeDensity(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getNaturalGasRelativeDensity()));
					
					
					reservoir.setDepth(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getReservoirDepth()));
					reservoir.setTemperature(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getReservoirTemperature()));
					
					tubingString.getEveryTubing().get(0).setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getTubingStringInsideDiameter())*0.001));
					casingString.getEveryCasing().get(0).setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getCasingStringInsideDiameter())*0.001));
					
					production.setTubingPressure(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getTubingPressure()));
					production.setCasingPressure(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getCasingPressure()));
					production.setWellHeadTemperature(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWellHeadFluidTemperature()));
					if(applicationScenarios!=0){
						production.setWaterCut(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWeightWaterCut()));
						production.setProductionGasOilRatio(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getProductionGasOilRatio()));
					}else{
						production.setWaterCut(100);
					}
					
					production.setProducingfluidLevel(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getProducingFluidLevel()));
					production.setPumpSettingDepth(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPumpSettingDepth()));
					
					if(applicationScenarios!=0){
						float weightWaterCut=CalculateUtils.volumeWaterCutToWeightWaterCut(production.getWaterCut(), fluidPVT.getCrudeOilDensity(), fluidPVT.getWaterDensity());
						production.setWeightWaterCut(weightWaterCut);
					}else{
						production.setWeightWaterCut(100);
					}
					
					
					String barrelType="";
					if(languageResourceMap.get("barrelType_L").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getBarrelTypeName())){
						barrelType="L";
					}else if(languageResourceMap.get("barrelType_H").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getBarrelTypeName())){
						barrelType="H";
					}
					
					pump.setBarrelType(barrelType);
					pump.setPumpGrade(StringManagerUtils.stringToInteger(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPumpGrade()));
					pump.setPumpBoreDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPumpboreDiameter())*0.001));
					pump.setPlungerLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPlungerLength()));
					
					
					if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength1())>0){
						SRPCalculateRequestData.EveryRod everyRod=new SRPCalculateRequestData.EveryRod();
						int rodType=0;
						if(languageResourceMap.get("rodStringTypeValue1").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName1())){
							rodType=1;
						}else if(languageResourceMap.get("rodStringTypeValue2").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName1())){
							rodType=2;
						}else if(languageResourceMap.get("rodStringTypeValue3").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName1())){
							rodType=3;
						}
						everyRod.setType(rodType);
						everyRod.setGrade(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade1());
						everyRod.setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter1())*0.001));
						everyRod.setOutsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter1())*0.001));
						everyRod.setLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength1()));
						rodString.getEveryRod().add(everyRod);
					}
					
					if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength2())>0){
						SRPCalculateRequestData.EveryRod everyRod=new SRPCalculateRequestData.EveryRod();
						int rodType=0;
						if(languageResourceMap.get("rodStringTypeValue1").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName2())){
							rodType=1;
						}else if(languageResourceMap.get("rodStringTypeValue2").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName2())){
							rodType=2;
						}else if(languageResourceMap.get("rodStringTypeValue3").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName2())){
							rodType=3;
						}
						everyRod.setType(rodType);
						everyRod.setGrade(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade2());
						everyRod.setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter2())*0.001));
						everyRod.setOutsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter2())*0.001));
						everyRod.setLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength2()));
						rodString.getEveryRod().add(everyRod);
					}
					
					if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength3())>0){
						SRPCalculateRequestData.EveryRod everyRod=new SRPCalculateRequestData.EveryRod();
						int rodType=0;
						if(languageResourceMap.get("rodStringTypeValue1").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName3())){
							rodType=1;
						}else if(languageResourceMap.get("rodStringTypeValue2").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName3())){
							rodType=2;
						}else if(languageResourceMap.get("rodStringTypeValue3").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName3())){
							rodType=3;
						}
						everyRod.setType(rodType);
						everyRod.setGrade(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade3());
						everyRod.setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter3())*0.001));
						everyRod.setOutsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter3())*0.001));
						everyRod.setLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength3()));
						rodString.getEveryRod().add(everyRod);
					}
					
					if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength4())>0){
						SRPCalculateRequestData.EveryRod everyRod=new SRPCalculateRequestData.EveryRod();
						int rodType=0;
						if(languageResourceMap.get("rodStringTypeValue1").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName4())){
							rodType=1;
						}else if(languageResourceMap.get("rodStringTypeValue2").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName4())){
							rodType=2;
						}else if(languageResourceMap.get("rodStringTypeValue3").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName4())){
							rodType=3;
						}
						everyRod.setType(rodType);
						everyRod.setGrade(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade4());
						everyRod.setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter4())*0.001));
						everyRod.setOutsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter4())*0.001));
						everyRod.setLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength4()));
						rodString.getEveryRod().add(everyRod);
					}
					
					String manualInterventionResultName=calculateManagerHandsontableChangedData.getUpdatelist().get(i).getManualInterventionResult();
					int manualInterventionResultCode=0;
					if(!languageResourceMap.get("noIntervention").equalsIgnoreCase(manualInterventionResultName)){
						WorkType workType=MemoryDataManagerTask.getWorkTypeByName(manualInterventionResultName,language);
						if(workType!=null){
							manualInterventionResultCode=workType.getResultCode();
						}
					}
					manualIntervention.setCode(manualInterventionResultCode);
					
					manualIntervention.setNetGrossRatio(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getNetGrossRatio()));
					manualIntervention.setNetGrossValue(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getNetGrossValue()));
					manualIntervention.setLevelCorrectValue(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getLevelCorrectValue()));
					
					
					productionDataBuff.append("{");
					productionDataBuff.append("\"FluidPVT\":"+(fluidPVT!=null?gson.toJson(fluidPVT):"{}")+",");
					productionDataBuff.append("\"Reservoir\":"+(reservoir!=null?gson.toJson(reservoir):"{}")+",");
					productionDataBuff.append("\"RodString\":"+(rodString!=null?gson.toJson(rodString):"{}")+",");
					productionDataBuff.append("\"TubingString\":"+(tubingString!=null?gson.toJson(tubingString):"{}")+",");
					productionDataBuff.append("\"CasingString\":"+(casingString!=null?gson.toJson(casingString):"{}")+",");
					productionDataBuff.append("\"PumpingUnit\":"+(calculateRequestData!=null && calculateRequestData.getPumpingUnit()!=null?gson.toJson(calculateRequestData.getPumpingUnit()):"{}")+",");
					productionDataBuff.append("\"Pump\":"+(pump!=null?gson.toJson(pump):"{}")+",");
					productionDataBuff.append("\"Production\":"+(production!=null?gson.toJson(production):"{}")+",");
					productionDataBuff.append("\"ManualIntervention\":"+(manualIntervention!=null?gson.toJson(manualIntervention):"{}"));
					productionDataBuff.append("}");
					
					String updateSql="update tbl_srpacqdata_hist t set t.resultstatus=2,t.productiondata='"+productionDataBuff.toString()+"' where t.id="+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRecordId();
					
					this.getBaseDao().updateOrDeleteBySql(updateSql);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				
			}
		}
	}
	
	public void saveRPMReCalculateData(CalculateManagerHandsontableChangedData calculateManagerHandsontableChangedData,int applicationScenarios,String language) throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		if(calculateManagerHandsontableChangedData.getUpdatelist()!=null){
			for(int i=0;i<calculateManagerHandsontableChangedData.getUpdatelist().size();i++){
				StringBuffer productionDataBuff = new StringBuffer();
				
				PCPCalculateRequestData.FluidPVT fluidPVT=new PCPCalculateRequestData.FluidPVT();
				PCPCalculateRequestData.Reservoir reservoir=new PCPCalculateRequestData.Reservoir();
				
				PCPCalculateRequestData.TubingString tubingString=new PCPCalculateRequestData.TubingString();
				tubingString.setEveryTubing(new ArrayList<PCPCalculateRequestData.EveryTubing>());
				tubingString.getEveryTubing().add(new PCPCalculateRequestData.EveryTubing());
				
				PCPCalculateRequestData.CasingString  casingString=new PCPCalculateRequestData.CasingString();
				casingString.setEveryCasing(new ArrayList<PCPCalculateRequestData.EveryCasing>());
				casingString.getEveryCasing().add(new PCPCalculateRequestData.EveryCasing());
				
				PCPCalculateRequestData.RodString rodString=new PCPCalculateRequestData.RodString();
				rodString.setEveryRod(new ArrayList<PCPCalculateRequestData.EveryRod>());
				
				PCPCalculateRequestData.Production production=new PCPCalculateRequestData.Production();
				
				PCPCalculateRequestData.Pump pump =new PCPCalculateRequestData.Pump();
				
				PCPCalculateRequestData.ManualIntervention manualIntervention=new PCPCalculateRequestData.ManualIntervention();
				
				if(applicationScenarios!=0){
					fluidPVT.setCrudeOilDensity(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getCrudeoilDensity()));
					fluidPVT.setSaturationPressure(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getSaturationPressure()));
				}
				fluidPVT.setWaterDensity(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWaterDensity()));
				fluidPVT.setNaturalGasRelativeDensity(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getNaturalGasRelativeDensity()));
				
				
				reservoir.setDepth(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getReservoirDepth()));
				reservoir.setTemperature(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getReservoirTemperature()));
				
				tubingString.getEveryTubing().get(0).setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getTubingStringInsideDiameter())*0.001));
				casingString.getEveryCasing().get(0).setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getCasingStringInsideDiameter())*0.001));
				
				production.setTubingPressure(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getTubingPressure()));
				production.setCasingPressure(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getCasingPressure()));
				production.setWellHeadTemperature(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWellHeadFluidTemperature()));
				if(applicationScenarios!=0){
					production.setWaterCut(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWeightWaterCut()));
					production.setProductionGasOilRatio(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getProductionGasOilRatio()));
				}else{
					production.setWaterCut(100);
				}
				
				production.setProducingfluidLevel(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getProducingFluidLevel()));
				production.setPumpSettingDepth(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPumpSettingDepth()));
				
				if(applicationScenarios!=0){
					float weightWaterCut=CalculateUtils.volumeWaterCutToWeightWaterCut(production.getWaterCut(), fluidPVT.getCrudeOilDensity(), fluidPVT.getWaterDensity());
					production.setWeightWaterCut(weightWaterCut);
				}else{
					production.setWeightWaterCut(100);
				}
				
				pump.setBarrelLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getBarrelLength()));
				pump.setBarrelSeries(StringManagerUtils.stringToInteger(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getBarrelSeries()));
				pump.setRotorDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRotorDiameter())*0.001));
				pump.setQPR((float)(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getQPR())*0.001*0.001));
				
				if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength1())>0){
					PCPCalculateRequestData.EveryRod everyRod=new PCPCalculateRequestData.EveryRod();
					int rodType=0;
					if(languageResourceMap.get("rodStringTypeValue1").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName1())){
						rodType=1;
					}else if(languageResourceMap.get("rodStringTypeValue2").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName1())){
						rodType=2;
					}else if(languageResourceMap.get("rodStringTypeValue3").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName1())){
						rodType=3;
					}
					everyRod.setType(rodType);
					everyRod.setGrade(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade1());
					everyRod.setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter1())*0.001));
					everyRod.setOutsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter1())*0.001));
					everyRod.setLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength1()));
					rodString.getEveryRod().add(everyRod);
				}
				
				if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength2())>0){
					PCPCalculateRequestData.EveryRod everyRod=new PCPCalculateRequestData.EveryRod();
					int rodType=0;
					if(languageResourceMap.get("rodStringTypeValue1").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName2())){
						rodType=1;
					}else if(languageResourceMap.get("rodStringTypeValue2").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName2())){
						rodType=2;
					}else if(languageResourceMap.get("rodStringTypeValue3").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName2())){
						rodType=3;
					}
					everyRod.setType(rodType);
					everyRod.setGrade(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade2());
					everyRod.setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter2())*0.001));
					everyRod.setOutsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter2())*0.001));
					everyRod.setLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength2()));
					rodString.getEveryRod().add(everyRod);
				}
				
				if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength3())>0){
					PCPCalculateRequestData.EveryRod everyRod=new PCPCalculateRequestData.EveryRod();
					int rodType=0;
					if(languageResourceMap.get("rodStringTypeValue1").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName3())){
						rodType=1;
					}else if(languageResourceMap.get("rodStringTypeValue2").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName3())){
						rodType=2;
					}else if(languageResourceMap.get("rodStringTypeValue3").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName3())){
						rodType=3;
					}
					everyRod.setType(rodType);
					everyRod.setGrade(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade3());
					everyRod.setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter3())*0.001));
					everyRod.setOutsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter3())*0.001));
					everyRod.setLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength3()));
					rodString.getEveryRod().add(everyRod);
				}
				
				if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength4())>0){
					PCPCalculateRequestData.EveryRod everyRod=new PCPCalculateRequestData.EveryRod();
					int rodType=0;
					if(languageResourceMap.get("rodStringTypeValue1").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName4())){
						rodType=1;
					}else if(languageResourceMap.get("rodStringTypeValue2").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName4())){
						rodType=2;
					}else if(languageResourceMap.get("rodStringTypeValue3").equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName4())){
						rodType=3;
					}
					everyRod.setType(rodType);
					everyRod.setGrade(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade4());
					everyRod.setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter4())*0.001));
					everyRod.setOutsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter4())*0.001));
					everyRod.setLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength4()));
					rodString.getEveryRod().add(everyRod);
				}
				
				manualIntervention.setNetGrossRatio(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getNetGrossRatio()));
				
				productionDataBuff.append("{");
				productionDataBuff.append("\"FluidPVT\":"+(fluidPVT!=null?gson.toJson(fluidPVT):"{}")+",");
				productionDataBuff.append("\"Reservoir\":"+(reservoir!=null?gson.toJson(reservoir):"{}")+",");
				productionDataBuff.append("\"RodString\":"+(rodString!=null?gson.toJson(rodString):"{}")+",");
				productionDataBuff.append("\"TubingString\":"+(tubingString!=null?gson.toJson(tubingString):"{}")+",");
				productionDataBuff.append("\"CasingString\":"+(casingString!=null?gson.toJson(casingString):"{}")+",");
				productionDataBuff.append("\"Pump\":"+(pump!=null?gson.toJson(pump):"{}")+",");
				productionDataBuff.append("\"Production\":"+(production!=null?gson.toJson(production):"{}")+",");
				productionDataBuff.append("\"ManualIntervention\":"+(manualIntervention!=null?gson.toJson(manualIntervention):"{}"));
				productionDataBuff.append("}");
				
				String updateSql="update tbl_pcpacqdata_hist t set t.resultstatus=2,t.productiondata='"+productionDataBuff.toString()+"' where t.id="+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRecordId();
				
				this.getBaseDao().updateOrDeleteBySql(updateSql);
			}
		}
	}
	
	public String getCalculateStatusList(String orgId, String deviceName, String calculateType,String startDate,String endDate,String language)throws Exception {
		StringBuffer result_json = new StringBuffer();
		Map<String,Code> codeMap=MemoryDataManagerTask.getCodeMap("RESULTSTATUS",language);
		result_json.append("{\"totals\":"+(codeMap.size()+1)+",\"list\":[{boxkey:\"\",boxval:\""+MemoryDataManagerTask.getLanguageResourceItem(language,"selectAll")+"\"}");
		Iterator<Map.Entry<String,Code>> it = codeMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Code> entry = it.next();
			Code c=entry.getValue();
			result_json.append(",{boxkey:\"" + c.getItemvalue() + "\",");
			result_json.append("boxval:\"" + c.getItemname() + "\"}");
		}
		result_json.append("]}");
		
		return result_json.toString();
	}
	
	public String getResultNameList(String orgId, String deviceId, String calculateType,String startDate,String endDate,String language)throws Exception {
		StringBuffer result_json = new StringBuffer();
		List<WorkType> workTypeList=new ArrayList<>();
		Map<String,WorkType> workTypeMap=MemoryDataManagerTask.getWorkTypeMap(language);
		if(StringManagerUtils.stringToInteger(calculateType)==1){
			String sql="select distinct(t.resultcode) as resultcode "
					+ " from TBL_SRPACQDATA_HIST t "
					+ " where t.fesdiagramacqtime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss') "
					+ " and t.deviceid="+deviceId;
			List<?> list = this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				workTypeList.add(workTypeMap.get(list.get(i)+""));
			}
		}
		result_json.append("{\"totals\":"+(workTypeList.size()+1)+",\"list\":[{boxkey:\"\",boxval:\""+MemoryDataManagerTask.getLanguageResourceItem(language,"selectAll")+"\"}");		
		for(WorkType w:workTypeList){
			result_json.append(",{boxkey:\"" + w.getResultCode() + "\",");
			result_json.append("boxval:\"" + w.getResultName() + "\"}");
		}
		result_json.append("]}");
		return result_json.toString();
		
	}
	
	public int recalculateByProductionData(String orgId, String deviceName, String deviceType,String startDate,String endDate,String calculateSign,String calculateType)throws Exception {
		String tableName="tbl_srpacqdata_hist";
		String deviceTableName="tbl_device";
		String acqTimeColumn="fesdiagramacqtime";
		if(StringManagerUtils.stringToInteger(calculateType)==2){
			tableName="tbl_pcpacqdata_hist";
			acqTimeColumn="acqtime";
		}
		
		String updateSql="update "+tableName+" t "
				+ " set (productiondata,resultstatus)"
				+ "=(select t2.productiondata,2 from "+deviceTableName+" t2 where t2.id=t.deviceId) "
				+ " where t."+acqTimeColumn+" between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss')";
		if(StringManagerUtils.isNotNull(calculateSign)){
			updateSql+=" and t.resultstatus in ("+calculateSign+")";
		}
		updateSql+=" and t.deviceId in (select well.id from "+deviceTableName+" well where well.orgid in("+orgId+")";
		if(StringManagerUtils.isNotNull(deviceName)){
			updateSql+=" and well.deviceName='"+deviceName+"'";
		}
		updateSql+=")";
		return getBaseDao().executeSqlUpdate(updateSql);
	}
	
	public String getCalculateRequestData(String recordId,String deviceName,String acqTime,String calculateType,String language) throws SQLException, IOException, ParseException{
		String requestData="{}";
		if("1".equals(calculateType)){
			requestData=this.getFSDiagramCalculateRequestData(recordId,deviceName,acqTime);
		}else if("2".equals(calculateType)){
			requestData=this.getRPMCalculateRequestData(recordId,deviceName,acqTime);
		}else if("5".equals(calculateType)){
			requestData=this.getElecInverCalculateRequestData(deviceName,acqTime,language);
		}
		return requestData;
	}
	
	public String getFSDiagramCalculateRequestData(String recordId,String deviceName,String acqTime) throws SQLException, IOException, ParseException{
		String requestData="{}";
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String sql=""
				+ " select t2.deviceName,decode(t2.applicationscenarios,0,'cbm','oil') as applicationscenarios,"
				+ " to_char(t.fesdiagramacqTime,'yyyy-mm-dd hh24:mi:ss') as fesdiagramacqTime,t.fesdiagramSrc,"
				+ " t.stroke,t.spm,"
				+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
				+ " t.productiondata"
//				+ " t.balanceinfo,"
//				+ " t2.stroke as inputStroke"
				+ " from tbl_srpacqdata_hist t"
				+ " left outer join tbl_device t2 on t.deviceId=t2.id"
				+ " where 1=1  "
				+ " and t.id="+recordId;
		
//		String auxiliaryDeviceSql="select t.id,t3.id as auxiliarydeviceid,t3.manufacturer,t3.model,t4.itemcode,t4.itemvalue "
//				+ " from tbl_device t,tbl_auxiliary2master t2,tbl_auxiliarydevice t3,tbl_auxiliarydeviceaddinfo t4,"
//				+ " tbl_srpacqdata_hist t5"
//				+ " where t.id=t2.masterid and t2.auxiliaryid=t3.id and t3.id=t4.deviceid"
//				+ " and t5.deviceId=t.id "
//				+ " and t3.specifictype=1"
//				+ " and t5.id="+recordId;
		
		List<?> list = this.findCallSql(sql);
//		List<?> auxiliaryDeviceList = this.findCallSql(auxiliaryDeviceSql);
		
		
		if(list.size()>0){
			Object[] object=(Object[])list.get(0);
//			requestData=calculateDataService.getObjectToSRPCalculateRequestData(obj);

			String productionData=object[10].toString();
			
			type = new TypeToken<SRPCalculateRequestData>() {}.getType();
			SRPCalculateRequestData calculateRequestData=gson.fromJson(productionData, type);
			if(calculateRequestData==null){
				calculateRequestData=new SRPCalculateRequestData();
				calculateRequestData.init();
			}
			
			calculateRequestData.setWellName(object[0]+"");
			calculateRequestData.setScene(object[1]+"");

			//功图数据
			calculateRequestData.setFESDiagram(new SRPCalculateRequestData.FESDiagram());
	        calculateRequestData.getFESDiagram().setAcqTime(object[2]+"");
	        calculateRequestData.getFESDiagram().setSrc(StringManagerUtils.stringToInteger(object[3]+""));
	        calculateRequestData.getFESDiagram().setStroke(StringManagerUtils.stringToFloat(object[4]+""));
	        calculateRequestData.getFESDiagram().setSPM(StringManagerUtils.stringToFloat(object[5]+""));
			
	        List<Float> F=new ArrayList<Float>();
	        List<Float> S=new ArrayList<Float>();
	        List<Float> Watt=new ArrayList<Float>();
	        List<Float> I=new ArrayList<Float>();
	        
	        int count =Integer.MAX_VALUE;
//	        if(StringManagerUtils.isNum(object[21]+"") || StringManagerUtils.isNumber(object[21]+"")){
//	        	count=StringManagerUtils.stringToInteger(object[21]+"");
//	        }
	        
	        SerializableClobProxy proxy=null;
	        CLOB realClob =null;
	        String clobStr="";
	        String[] curveData=null;
	        if(object[6]!=null){//位移曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[6]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				curveData=clobStr.split(",");
				for(int i=0;i<curveData.length && i<count;i++){
					S.add(StringManagerUtils.stringToFloat(curveData[i]));
				}
	        }
	        if(object[7]!=null){//载荷曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[7]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				curveData=clobStr.split(",");
				for(int i=0;i<curveData.length && i<count;i++){
					F.add(StringManagerUtils.stringToFloat(curveData[i]));
				}
	        }
	        if(object[8]!=null){//功率曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[8]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				if(StringManagerUtils.isNotNull(clobStr)){
					curveData=clobStr.split(",");
					for(int i=0;i<curveData.length && i<count;i++){
						Watt.add(StringManagerUtils.stringToFloat(curveData[i]));
					}
				}
	        }
	        if(object[9]!=null){//电流曲线
	        	proxy = (SerializableClobProxy)Proxy.getInvocationHandler(object[9]);
				realClob = (CLOB) proxy.getWrappedClob();
				clobStr=StringManagerUtils.CLOBtoString(realClob);
				if(StringManagerUtils.isNotNull(clobStr)){
					curveData=clobStr.split(",");
					for(int i=0;i<curveData.length && i<count;i++){
						I.add(StringManagerUtils.stringToFloat(curveData[i]));
					}
				}
	        }
	        calculateRequestData.getFESDiagram().setF(F);
	        calculateRequestData.getFESDiagram().setS(S);
	        calculateRequestData.getFESDiagram().setWatt(Watt);
	        calculateRequestData.getFESDiagram().setI(I);
	        
	        
//	        if(auxiliaryDeviceList.size()>0){
//	        	List<AuxiliaryDeviceAddInfo> auxiliaryDeviceAddInfoList=new ArrayList<>();
//	        	for(int i=0;i<auxiliaryDeviceList.size();i++){
//	        		Object[] obj=(Object[])auxiliaryDeviceList.get(i);
//	        		
//	        		AuxiliaryDeviceAddInfo auxiliaryDeviceAddInfo=new AuxiliaryDeviceAddInfo();
//					auxiliaryDeviceAddInfo.setMasterId(StringManagerUtils.stringToInteger(obj[0]+""));
//					auxiliaryDeviceAddInfo.setDeviceId(StringManagerUtils.stringToInteger(obj[1]+""));
//					auxiliaryDeviceAddInfo.setManufacturer(obj[2]+"");
//					auxiliaryDeviceAddInfo.setModel(obj[3]+"");
//					auxiliaryDeviceAddInfo.setItemCode(obj[4]+"");
//					auxiliaryDeviceAddInfo.setItemValue(obj[5]+"");
//					auxiliaryDeviceAddInfoList.add(auxiliaryDeviceAddInfo);
//				}
//	        	
//	        	
//	        	if(auxiliaryDeviceAddInfoList.size()>0){
//	        		String balanceInfo=object[11]+"";
//					String inputStroke=object[12]+"";
//	        		calculateRequestData.setPumpingUnit(new SRPCalculateRequestData.PumpingUnit());
//	        		String manufacturer="";
//					String model="";
//					for(int i=0;i<auxiliaryDeviceAddInfoList.size();i++ ){
//						manufacturer=auxiliaryDeviceAddInfoList.get(i).getManufacturer();
//						model=auxiliaryDeviceAddInfoList.get(i).getModel();
//						if("crankRotationDirection".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
//							calculateRequestData.getPumpingUnit().setCrankRotationDirection(auxiliaryDeviceAddInfoList.get(i).getItemValue());
//						}else if("offsetAngleOfCrank".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
//							calculateRequestData.getPumpingUnit().setOffsetAngleOfCrank(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
//						}else if("crankGravityRadius".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
//							calculateRequestData.getPumpingUnit().setCrankGravityRadius(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
//						}else if("singleCrankWeight".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
//							calculateRequestData.getPumpingUnit().setSingleCrankWeight(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
//						}else if("singleCrankPinWeight".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
//							calculateRequestData.getPumpingUnit().setSingleCrankPinWeight(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
//						}else if("structuralUnbalance".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
//							calculateRequestData.getPumpingUnit().setStructuralUnbalance(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
//						}
//					}
//					
//					
//					calculateRequestData.getPumpingUnit().setManufacturer(manufacturer);
//					calculateRequestData.getPumpingUnit().setModel(model);
//					calculateRequestData.getPumpingUnit().setStroke(StringManagerUtils.stringToFloat(inputStroke,2));
//					
//					
//					type = new TypeToken<SRPCalculateRequestData.Balance>() {}.getType();
//					SRPCalculateRequestData.Balance balance=gson.fromJson(balanceInfo, type);
//					if(balance!=null){
//						calculateRequestData.getPumpingUnit().setBalance(balance);
//					}
//	        	}else{
//	        		calculateRequestData.setPumpingUnit(null);
//	        	}
//	        }else{
//        		calculateRequestData.setPumpingUnit(null);
//        	}
	        requestData=calculateRequestData.toString();
		}
		return requestData;
	}
	
	public String getRPMCalculateRequestData(String recordId,String deviceName,String acqTime) throws SQLException, IOException, ParseException{
		String requestData="{}";
		String sql=""
				+ " select t2.deviceName,decode(t2.applicationscenarios,0,'cbm','oil') as applicationscenarios,"
				+ " to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss'),"
				+ " t.rpm,t.productiondata"
				+ " from tbl_pcpacqdata_hist t,tbl_device t2"
				+ " where t.deviceId=t2.id  "
				+ " and t.id="+recordId;
		List<?> list = this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			requestData=calculateDataService.getObjectToRPMCalculateRequestData(obj);
		}
		return requestData;
	}
	
	public String getElecInverCalculateRequestData(String deviceName,String acqTime,String language) throws SQLException, IOException, ParseException{
		String requestData="{}";
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String sql="select t.deviceName,t2.id as diagramid,to_char(t2.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ " t2.spm,t2.rawpower_curve,t2.rawcurrent_curve,t2.rawrpm_curve, "
				+ " t4.manufacturer,t4.model,t4.stroke,decode(t4.crankrotationdirection,'"+languageResourceMap.get("clockwise")+"','Clockwise','Anticlockwise'),"
				+ " t4.offsetangleofcrank,t5.offsetangleofcrankps,t4.crankgravityradius,t4.singlecrankweight,t4.structuralunbalance,"
				+ " t4.gearreducerratio,t4.gearreducerbeltpulleydiameter, t4.balanceposition,t4.balanceweight,"
				+ " t5.surfacesystemefficiency,t5.fs_leftpercent,t5.fs_rightpercent,"
				+ " t5.wattangle,t5.filtertime_watt,t5.filtertime_i,t5.filtertime_rpm,t5.filtertime_fsdiagram,t5.filtertime_fsdiagram_l,t5.filtertime_fsdiagram_r,"
				+ " t4.prtf "
				+ " from tbl_wellinformation t,tbl_srp_diagram_hist t2,tbl_srpinformation t4,tbl_srp_inver_opt t5 "
				+ " where t.id=t2.deviceId and t.id=t4.deviceId and t.id=t5.deviceId "
				+ " and t.deviceName='"+deviceName+"' and t2.acqTime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')";
		List<?> list = this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			String WattString="";
			String IString="";
			String RPMString="";
			SerializableClobProxy   proxy=null;
	        CLOB realClob=null;
			if(obj[4]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[4]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				WattString=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[5]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[5]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				IString=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[6]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[6]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				RPMString=StringManagerUtils.CLOBtoString(realClob);
			}
			result_json.append("{\"AKString\":\"\",");
			result_json.append("\"WellName\":\""+obj[0]+"\",");
			result_json.append("\"AcqTime\":\""+obj[2]+"\",");
			result_json.append("\"SPM\":"+obj[3]+",");
			result_json.append("\"Watt\":["+WattString+"],");
			result_json.append("\"I\":["+IString+"],");
			result_json.append("\"RPM\":["+RPMString+"],");
			result_json.append("\"SurfaceSystemEfficiency\":"+obj[20]+",");
			
			result_json.append("\"LeftPercent\":"+obj[21]+",");
			result_json.append("\"RightPercent\":"+obj[22]+",");
			result_json.append("\"WattAngle\":"+obj[23]+",");
			result_json.append("\"WattTimes\":"+obj[24]+",");
			result_json.append("\"ITimes\":"+obj[25]+",");
			result_json.append("\"RPMTimes\":"+obj[26]+",");
			result_json.append("\"FSDiagramTimes\":"+obj[27]+",");
			result_json.append("\"FSDiagramLeftTimes\":"+obj[28]+",");
			result_json.append("\"FSDiagramRightTimes\":"+obj[29]+",");
			
			//抽油机数据
			result_json.append("\"PumpingUnit\":{");
			
			//位置扭矩因数
			String prtf="";
			if(obj[30]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[30]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				prtf=StringManagerUtils.CLOBtoString(realClob);
			}
			
			
			result_json.append("\"Manufacturer\":\""+obj[7]+"\",");
			result_json.append("\"Model\":\""+obj[8]+"\",");
			result_json.append("\"Stroke\":"+obj[9]+",");
			result_json.append("\"CrankRotationDirection\":\""+obj[10]+"\",");
			result_json.append("\"OffsetAngleOfCrank\":"+obj[11]+",");
			result_json.append("\"OffsetAngleOfCrankPS\":"+obj[12]+",");
			result_json.append("\"CrankGravityRadius\":"+obj[13]+",");
			result_json.append("\"SingleCrankWeight\":"+obj[14]+",");
			result_json.append("\"StructuralUnbalance\":"+obj[15]+",");
			result_json.append("\"GearReducerRatio\":"+obj[16]+",");
			result_json.append("\"GearReducerBeltPulleyDiameter\":"+obj[17]+",");
			result_json.append("\"Balance\":{");
			result_json.append("\"EveryBalance\":[");
			
			//拼接抽油机平衡块数据
			String[] BalancePositionArr=(obj[18]+"").split(",");
			String[] BalanceWeightArr=(obj[19]+"").split(",");
			for(int j=0;j<BalancePositionArr.length&&j<BalanceWeightArr.length;j++){
				result_json.append("{\"Position\":"+BalancePositionArr[j]+",");
				result_json.append("\"Weight\":"+BalanceWeightArr[j]+"}");
				if(j<BalancePositionArr.length-1&&j<BalanceWeightArr.length-1){
					result_json.append(",");
				}
			}
			result_json.append("]},");
			//拼接抽油机位置扭矩因数曲线数据
			result_json.append("\"PRTF\":{");
			String CrankAngle="[";
			String PR="[";
			String TF="[";
			
			if(StringManagerUtils.isNotNull(prtf)){
				JSONObject prtfJsonObject = JSONObject.fromObject("{\"data\":"+prtf+"}");//解析数据
				JSONArray prtfJsonArray = prtfJsonObject.getJSONArray("data");
				for(int j=0;j<prtfJsonArray.size();j++){
					JSONObject everydata = JSONObject.fromObject(prtfJsonArray.getString(j));
					CrankAngle+=everydata.getString("CrankAngle");
					PR+=everydata.getString("PR");
					TF+=everydata.getString("TF");
					if(j<prtfJsonArray.size()-1){
						CrankAngle+=",";
						PR+=",";
						TF+=",";
					}
				}
			}
			
			CrankAngle+="]";
			PR+="]";
			TF+="]";
			result_json.append("\"CrankAngle\":"+CrankAngle+",");
			result_json.append("\"PR\":"+PR+",");
			result_json.append("\"TF\":"+TF+"}");
			
			result_json.append("}");
			result_json.append("}");
			
			requestData=result_json.toString();
		}
		
		return requestData;
	}
	
	public String getTotalCalculateResultData(String orgId,String deviceId, String deviceName, Page pager,String deviceType,String startDate,String endDate,String calculateType,String language)
			throws Exception {
		String json="";
		if("3".equals(calculateType)){
			json=this.getFESDiagramTotalCalculateResultData(orgId,deviceId, deviceName, pager, deviceType, startDate, endDate,  calculateType,language);
		}else if("4".equals(calculateType)){
			json=this.getRPMTotalCalculateResultData(orgId,deviceId, deviceName, pager, deviceType, startDate, endDate, calculateType,language);
		}
		
		return json;
	}
	
	public String getFESDiagramTotalCalculateResultData(String orgId,String deviceId, String deviceName, Page pager,String deviceType,String startDate,String endDate,String calculateType,String language)
			throws Exception {
		DataDictionary ddic = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicCode="calculateManager_SRPTotalRecord";
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicCode,language);
		columns = ddic.getTableHeader();
		
		String prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			prodCol=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,";
		}
		
		sql="select t.id,t.deviceId,t.deviceName,to_char(t.caldate,'yyyy-mm-dd'),"
			+ "t.resultcode,t.resultString,"
			+ prodCol
			+ " t.pumpeff,t.systemefficiency,t.wattDegreeBalance,t.iDegreeBalance,t.todayKWattH"
			+ " from viw_srpdailycalculationdata t "
			+ " where t.org_id in("+orgId+") "
			+ " and t.deviceid="+deviceId
			+ " and t.caldate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		sql+=" order by t.caldate desc";
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		
		result_json.append("{\"success\":true,\"totalCount\":"+totals+",\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"deviceId\":\""+obj[1]+"\",");
			result_json.append("\"deviceName\":\""+obj[2]+"\",");
			result_json.append("\"calDate\":\""+obj[3]+"\",");
			result_json.append("\"resultName\":\""+MemoryDataManagerTask.getWorkTypeByCode(obj[4]+"",language).getResultName()+"\",");
			result_json.append("\"resultString\":\""+obj[5]+"\",");
			
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				result_json.append("\"liquidWeightProduction\":\""+obj[6]+"\",");
				result_json.append("\"oilWeightProduction\":\""+obj[7]+"\",");
				result_json.append("\"waterWeightProduction\":\""+obj[8]+"\",");
			}else{
				result_json.append("\"liquidVolumetricProduction\":\""+obj[6]+"\",");
				result_json.append("\"oilVolumetricProduction\":\""+obj[7]+"\",");
				result_json.append("\"waterVolumetricProduction\":\""+obj[8]+"\",");
			}
			
			result_json.append("\"pumpEff\":\""+obj[9]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[10]+"\",");
			result_json.append("\"wattDegreeBalance\":\""+obj[11]+"\",");
			result_json.append("\"iDegreeBalance\":\""+obj[12]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[13]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json = result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		String json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getRPMTotalCalculateResultData(String orgId,String deviceId, String deviceName, Page pager,String deviceType,String startDate,String endDate,String calculateType,String language)
			throws Exception {
		DataDictionary ddic = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicCode="calculateManager_PCPTotalRecord";
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicCode,language);
		columns = ddic.getTableHeader();
		
		String prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			prodCol=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,";
		}
		
		sql="select t.id,t.deviceId,t.deviceName,to_char(t.caldate,'yyyy-mm-dd'),"
			+ prodCol
			+ " t.pumpeff,t.systemefficiency,t.todayKWattH"
			+ " from viw_pcpdailycalculationdata t "
			+ " where t.org_id in("+orgId+") "
			+ " and t.deviceid="+deviceId
			+ " and t.caldate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
//		if(StringManagerUtils.isNotNull(deviceName)){
//			sql+=" and  t.deviceName = '" + deviceName.trim() + "' ";
//		}
		sql+=" order by t.caldate desc";
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		
		result_json.append("{\"success\":true,\"totalCount\":"+totals+",\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"deviceId\":\""+obj[1]+"\",");
			result_json.append("\"deviceName\":\""+obj[2]+"\",");
			result_json.append("\"calDate\":\""+obj[3]+"\",");
			
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				result_json.append("\"liquidWeightProduction\":\""+obj[4]+"\",");
				result_json.append("\"oilWeightProduction\":\""+obj[5]+"\",");
				result_json.append("\"waterWeightProduction\":\""+obj[6]+"\",");
			}else{
				result_json.append("\"liquidVolumetricProduction\":\""+obj[4]+"\",");
				result_json.append("\"oilVolumetricProduction\":\""+obj[5]+"\",");
				result_json.append("\"waterVolumetricProduction\":\""+obj[6]+"\",");
			}
			
			result_json.append("\"pumpEff\":\""+obj[7]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[8]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[9]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json = result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		String json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String deleteCalculateData(String deviceId,String calculateType,String recordIds) {
		String json="";
		int result=0;
		boolean success=true;
		try {
			boolean updateRealtimeData=false;
			String calHistoryDataTable="tbl_srpacqdata_hist";
			String calRealtimeDataTable="tbl_srpacqdata_latest";
			String timeColumn="fesdiagramacqtime";
			if("1".equals(calculateType)){
				
			}else if("2".equals(calculateType)){
				calHistoryDataTable="tbl_pcpacqdata_hist";
				calRealtimeDataTable="tbl_pcpacqdata_latest";
				timeColumn="acqtime";
			}
			String sql="select t.deviceid "
					+ " from "+calRealtimeDataTable+" t "
					+ " where t.deviceid="+deviceId
					+ " and t."+timeColumn+" in ( select t2."+timeColumn+" from "+calHistoryDataTable+" t2 where t2.id in("+recordIds+") )";
			List<?> list = this.findCallSql(sql);
			if(list.size()>0){
				updateRealtimeData=true;
			}
			String deleteHistorySql="delete from "+calHistoryDataTable+" t where t.id in("+recordIds+")";
			result=this.getBaseDao().updateOrDeleteBySql(deleteHistorySql);
			
			if(updateRealtimeData){
				if("1".equals(calculateType)){
					this.getBaseDao().uodateSRPRealtimeDiagramData(StringManagerUtils.stringToInteger(deviceId));
				}else if("2".equals(calculateType)){
					this.getBaseDao().uodatePCPRealtimeRPMData(StringManagerUtils.stringToInteger(deviceId));
				}
			}
		}catch (Exception e) {
			success=false;
		}
		json="{\"success\":"+success+",\"count\":"+result+"}";
		return json;
	}
	
	public String reTotalCalculate(String deviceType,String reCalculateDate)throws Exception {
		String json="";
		if("0".equals(deviceType)){
			json=this.reTotalCalculateFESDiagramData(reCalculateDate);
		}else if("1".equals(deviceType)){
			json=this.reTotalCalculateRPMData(reCalculateDate);
		}
		
		return json;
	}
	
	public String reTotalCalculateFESDiagramData(String reCalculateDate)throws Exception {
		String json="";
		int deviceType=0;
		if(StringManagerUtils.isNotNull(reCalculateDate)){
			ThreadPool executor = new ThreadPool("SRPReTotalCalculate",
					Config.getInstance().configFile.getAp().getThreadPool().getTotalCalculateMaintaining().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getTotalCalculateMaintaining().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getTotalCalculateMaintaining().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getTotalCalculateMaintaining().getWattingCount());
			
			String[] calInfoArr=reCalculateDate.split(";");
			for(int i=0;i<calInfoArr.length;i++){
				String recordId=calInfoArr[i].split(",")[0];
				String deviceId=calInfoArr[i].split(",")[1];
				String deviceName=calInfoArr[i].split(",")[2];
				String calDate=calInfoArr[i].split(",")[3];
				executor.execute(new TotalCalculateThread(StringManagerUtils.stringToInteger(recordId),StringManagerUtils.stringToInteger(deviceId),deviceName,calDate,deviceType,service));
			}
			while (!executor.isCompletedByTaskCount()) {
				Thread.sleep(1000*1);
		    }
		}
		return json;
	}
	
	public String reTotalCalculateRPMData(String reCalculateDate)throws Exception {
		String json="";
		int deviceType=1;
		if(StringManagerUtils.isNotNull(reCalculateDate)){
			ThreadPool executor = new ThreadPool("PCPReTotalCalculate",
					Config.getInstance().configFile.getAp().getThreadPool().getTotalCalculateMaintaining().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getTotalCalculateMaintaining().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getTotalCalculateMaintaining().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getTotalCalculateMaintaining().getWattingCount());
			
			String[] calInfoArr=reCalculateDate.split(";");
			for(int i=0;i<calInfoArr.length;i++){
				String recordId=calInfoArr[i].split(",")[0];
				String deviceId=calInfoArr[i].split(",")[1];
				String deviceName=calInfoArr[i].split(",")[2];
				String calDate=calInfoArr[i].split(",")[3];
				executor.execute(new TotalCalculateThread(StringManagerUtils.stringToInteger(recordId),StringManagerUtils.stringToInteger(deviceId),deviceName,calDate,deviceType,service));
			}
			while (!executor.isCompletedByTaskCount()) {
				Thread.sleep(1000*1);
		    }
		}
		return json;
	}
	
	
	public String exportTotalCalculateRequestData(String deviceType,String recordId,String deviceId,String deviceName,String calDate)throws Exception {
		String json="";
		if("0".equals(deviceType)){
			json=this.exportFESDiagramTotalCalculateRequestData(recordId,deviceId,deviceName,calDate);
		}else if("1".equals(deviceType)){
			json=this.exportRPMTotalCalculateRequestData(recordId,deviceId,deviceName,calDate);
		}
		
		return json;
	}
	
	public String exportFESDiagramTotalCalculateRequestData(String recordId,String deviceId,String deviceName,String calDate)throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer dataSbf= new StringBuffer();
		String sql="select t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,t.runstatus,t.runtime,t.runtimeefficiency,t.runrange "
				+ " from tbl_srpdailycalculationdata t,tbl_device t2 "
				+ " where t.deviceId=t2.id "
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
				+ " from tbl_srpacqdata_hist t "
				+ " where t.deviceId="+deviceId+" "
				+ " and t.fesdiagramacqtime between to_date('"+calDate+"','yyyy-mm-dd') and to_date('"+calDate+"','yyyy-mm-dd')+1 "
				+ " and t.resultstatus=1 "
				+ " order by t.fesdiagramacqtime";
		List<?> list = this.findCallSql(sql);
		if(list.size()>0){
			Object[] totalObj=(Object[])list.get(0);
			List<?> fesDiagramList = this.findCallSql(fesDiagramSql);
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
				type = new TypeToken<SRPCalculateRequestData>() {}.getType();
				SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
				
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
				
				if(srpProductionData!=null&&srpProductionData.getProduction()!=null){
					volumeWaterCutList.add(srpProductionData.getProduction().getWaterCut());
				}else{
					volumeWaterCutList.add(0.0f);
				}
				
				
				liquidWeightProductionList.add(StringManagerUtils.stringToFloat(obj[11]+""));
				oilWeightProductionList.add(StringManagerUtils.stringToFloat(obj[12]+""));
				waterWeightProductionList.add(StringManagerUtils.stringToFloat(obj[13]+""));
				
				if(srpProductionData!=null&&srpProductionData.getProduction()!=null){
					weightWaterCutList.add(srpProductionData.getProduction().getWeightWaterCut());
				}else{
					weightWaterCutList.add(0.0f);
				}
				
				if(srpProductionData!=null&&srpProductionData.getProduction()!=null){
					tubingPressureList.add(srpProductionData.getProduction().getTubingPressure());
					casingPressureList.add(srpProductionData.getProduction().getCasingPressure());
					pumpSettingDepthList.add(srpProductionData.getProduction().getPumpSettingDepth());
					producingfluidLevelList.add(srpProductionData.getProduction().getProducingfluidLevel());
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
			dataSbf.append("\"WellName\":\""+deviceName+"\",");
			dataSbf.append("\"Date\":\""+calDate+"\",");
			dataSbf.append("\"OffsetHour\":0,");
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
		}
	
		return dataSbf.toString();
	}
	
	public String exportRPMTotalCalculateRequestData(String recordId,String deviceId,String deviceName,String calDate)throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer dataSbf= new StringBuffer();
		String sql="select t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,t.runstatus,t.runtime,t.runtimeefficiency,t.runrange "
				+ " from tbl_pcpdailycalculationdata t,tbl_device t2 "
				+ " where t.deviceId=t2.id "
				+ " and t.id="+recordId;
		String fesDiagramSql="select to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss'),t.rpm,"
				+ "t.theoreticalproduction,t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
				+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
				+ "t.productiondata,"
				+ "t.pumpeff,t.pumpeff1,t.pumpeff2,"
				+ "t.systemefficiency,t.energyper100mlift,"
				+ "t.submergence "
				+ " from tbl_pcpacqdata_hist t "
				+ " where t.deviceId="+deviceId+" "
				+ " and t.acqtime between to_date('"+calDate+"','yyyy-mm-dd') and to_date('"+calDate+"','yyyy-mm-dd')+1 "
				+ " and t.resultstatus=1 "
				+ " order by t.acqtime";
		List<?> list = this.findCallSql(sql);
		if(list.size()>0){
			
			Object[] totalObj=(Object[])list.get(0);
			List<?> fesDiagramList = this.findCallSql(fesDiagramSql);
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
			
			for(int j=0;j<fesDiagramList.size();j++){
				Object[] obj=(Object[])fesDiagramList.get(j);
				
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
				
				pumpEffList.add(StringManagerUtils.stringToFloat(obj[10]+""));
				pumpEff1List.add(StringManagerUtils.stringToFloat(obj[11]+""));
				pumpEff2List.add(StringManagerUtils.stringToFloat(obj[12]+""));
				
				systemEfficiencyList.add(StringManagerUtils.stringToFloat(obj[13]+""));
				energyPer100mLiftList.add(StringManagerUtils.stringToFloat(obj[14]+""));
				
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
				submergenceList.add(StringManagerUtils.stringToFloat(obj[15]+""));
			}
			dataSbf.append("{\"AKString\":\"\",");
			dataSbf.append("\"WellName\":\""+deviceName+"\",");
			dataSbf.append("\"Date\":\""+calDate+"\",");
			dataSbf.append("\"OffsetHour\":0,");
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
			
		}
		return dataSbf.toString();
	}
	
	public BaseDao getDao() {
		return dao;
	}

	@Resource
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
}
