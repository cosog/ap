package com.cosog.service.calculateManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cosog.dao.BaseDao;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.RPCDeviceTodayData;
import com.cosog.model.calculate.RPCProductionData;
import com.cosog.model.calculate.TotalAnalysisResponseData;
import com.cosog.model.calculate.RPCCalculateRequestData.EveryCasing;
import com.cosog.model.calculate.RPCCalculateRequestData.EveryRod;
import com.cosog.model.calculate.RPCCalculateRequestData.EveryTubing;
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
	public String getCalculateResultData(String orgId, String deviceName,String deviceId,String applicationScenarios, Page pager,String deviceType,String startDate,String endDate,String calculateSign,String calculateType)
			throws Exception {
		String json="";
		if("1".equals(calculateType)){
			json=this.getFESDiagramCalculateResultData(orgId, deviceName,deviceId,applicationScenarios, pager, deviceType, startDate, endDate, calculateSign, calculateType);
		}else if("2".equals(calculateType)){
			json=this.getRPMCalculateResultData(orgId, deviceName,deviceId,applicationScenarios, pager, deviceType, startDate, endDate, calculateSign, calculateType);
		}else if("5".equals(calculateType)){//电参反演地面功图
			json=this.getElecInverCalculateResultData(orgId, deviceName, pager, deviceType, startDate, endDate, calculateSign, calculateType);
		}
		
		return json;
	}
	
	public String getWellList(String orgId, String deviceName, Page pager,String deviceType,String calculateSign,String calculateType)
			throws Exception {
		String json="";
		if("1".equals(calculateType)||"2".equals(calculateType)||"3".equals(calculateType)||"4".equals(calculateType)){
			json=this.getDiagnoseAndProdCalculateWellListData(orgId, deviceName, pager, deviceType,calculateSign, calculateType);
		}else if("5".equals(calculateType)){//电参反演地面功图
			json=this.getElecInverCalculateWellListData(orgId, deviceName, pager, deviceType,calculateSign, calculateType);
		}
		
		return json;
	}
	
	public String getFESDiagramCalculateResultData(String orgId, String deviceName,String deviceId,String applicationScenarios, Page pager,String deviceType,String startDate,String endDate,String calculateSign,String calculateType)
			throws Exception {
		DataDictionary ddic = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicName="calculateManager_RPCSingleRecord";
		StringBuffer result_json = new StringBuffer();
		StringBuffer resultNameBuff = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		Jedis jedis=null;
		try{
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists("RPCWorkType".getBytes())){
					MemoryDataManagerTask.loadRPCWorkType();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			columns = ddic.getTableHeader();
			
			String prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,";
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				prodCol=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,";
			}
			
			sql="select t.id,t.deviceId,t.deviceName,to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss'),"
				+ "decode(t.resultStatus,-1,'无效功图',1,'计算成功',0,'未计算',2,'未计算','计算失败'),"
				+ "t.resultName,"
				+ prodCol
				+ "t.productiondata"
				+ " from viw_rpc_calculatemain t "
				+ " where t.orgid in("+orgId+") "
				+ " and t.resultStatus<>-1"
				+ " and t.fesdiagramacqtime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss')";
			if(StringManagerUtils.isNotNull(deviceName)){
				sql+=" and  t.deviceName = '" + deviceName.trim() + "' ";
			}
			if(StringManagerUtils.isNotNull(calculateSign)){
				if("0".equals(calculateSign)){
					sql+=" and  t.resultstatus in(0,2) ";
				}else{
					sql+=" and  t.resultstatus = " + calculateSign + " ";
				}
			}
			int totals=this.getTotalCountRows(sql);
			
			sql+=" order by t.fesdiagramacqtime desc";
			int maxvalue=pager.getLimit()+pager.getStart();
			finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
			
			String resultSql="select t.resultname from tbl_rpc_worktype t order by t.resultcode";
			resultNameBuff.append("[\"不干预\"");
			List<?> resultList = this.findCallSql(resultSql);
			for(int i=0;i<resultList.size();i++){
				resultNameBuff.append(",\""+resultList.get(i).toString()+"\"");
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
				type = new TypeToken<RPCCalculateRequestData>() {}.getType();
				RPCCalculateRequestData rpcProductionData=gson.fromJson(productionData, type);
				
				result_json.append("{\"id\":\""+obj[0]+"\",");
				result_json.append("\"deviceId\":\""+obj[1]+"\",");
				result_json.append("\"deviceName\":\""+obj[2]+"\",");
				result_json.append("\"acqTime\":\""+obj[3]+"\",");
				result_json.append("\"resultStatus\":\""+obj[4]+"\",");
				result_json.append("\"resultName\":\""+obj[5]+"\",");
				
				if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
					result_json.append("\"liquidWeightProduction\":\""+obj[6]+"\",");
					result_json.append("\"oilWeightProduction\":\""+obj[7]+"\",");
					result_json.append("\"waterWeightProduction\":\""+obj[8]+"\",");
				}else{
					result_json.append("\"liquidVolumetricProduction\":\""+obj[6]+"\",");
					result_json.append("\"oilVolumetricProduction\":\""+obj[7]+"\",");
					result_json.append("\"waterVolumetricProduction\":\""+obj[8]+"\",");
				}
				
				if(rpcProductionData!=null){
					if(rpcProductionData.getFluidPVT()!=null){
						result_json.append("\"crudeoilDensity\":\""+rpcProductionData.getFluidPVT().getCrudeOilDensity()+"\",");
						result_json.append("\"waterDensity\":\""+rpcProductionData.getFluidPVT().getWaterDensity()+"\",");
						result_json.append("\"naturalGasRelativeDensity\":\""+rpcProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"\",");
						result_json.append("\"saturationPressure\":\""+rpcProductionData.getFluidPVT().getSaturationPressure()+"\",");
					}
					if(rpcProductionData.getReservoir()!=null){
						result_json.append("\"reservoirDepth\":\""+rpcProductionData.getReservoir().getDepth()+"\",");
						result_json.append("\"reservoirTemperature\":\""+rpcProductionData.getReservoir().getTemperature()+"\",");
					}
					if(rpcProductionData.getProduction()!=null){
						result_json.append("\"tubingPressure\":\""+rpcProductionData.getProduction().getTubingPressure()+"\",");
						result_json.append("\"casingPressure\":\""+rpcProductionData.getProduction().getCasingPressure()+"\",");
						result_json.append("\"wellHeadFluidTemperature\":\""+rpcProductionData.getProduction().getWellHeadTemperature()+"\",");
						result_json.append("\"weightWaterCut\":\""+rpcProductionData.getProduction().getWaterCut()+"\",");
						result_json.append("\"productionGasOilRatio\":\""+rpcProductionData.getProduction().getProductionGasOilRatio()+"\",");
						result_json.append("\"producingFluidLevel\":\""+rpcProductionData.getProduction().getProducingfluidLevel()+"\",");
						result_json.append("\"pumpSettingDepth\":\""+rpcProductionData.getProduction().getPumpSettingDepth()+"\",");
					}
					if(rpcProductionData.getPump()!=null){
						String barrelType="";
						if(rpcProductionData.getPump()!=null&&rpcProductionData.getPump().getBarrelType()!=null){
							if("L".equalsIgnoreCase(rpcProductionData.getPump().getBarrelType())){
								barrelType="组合泵";
							}else if("H".equalsIgnoreCase(rpcProductionData.getPump().getBarrelType())){
								barrelType="整筒泵";
							}
						}
						result_json.append("\"barrelTypeName\":\""+barrelType+"\",");
						result_json.append("\"pumpGrade\":\""+rpcProductionData.getPump().getPumpGrade()+"\",");
						result_json.append("\"pumpboreDiameter\":\""+rpcProductionData.getPump().getPumpBoreDiameter()*1000+"\",");
						result_json.append("\"plungerLength\":\""+rpcProductionData.getPump().getPlungerLength()+"\",");
					}
					if(rpcProductionData.getTubingString()!=null&&rpcProductionData.getTubingString().getEveryTubing()!=null&&rpcProductionData.getTubingString().getEveryTubing().size()>0){
						result_json.append("\"tubingStringInsideDiameter\":\""+rpcProductionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000+"\",");
					}
					if(rpcProductionData.getCasingString()!=null&&rpcProductionData.getCasingString().getEveryCasing()!=null&&rpcProductionData.getCasingString().getEveryCasing().size()>0){
						result_json.append("\"casingStringInsideDiameter\":\""+rpcProductionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000+"\",");
					}
					
					if(rpcProductionData.getRodString()!=null && rpcProductionData.getRodString().getEveryRod()!=null){
						for(int j=0;j<rpcProductionData.getRodString().getEveryRod().size();j++){
							String rodType="钢杆";
							if(rpcProductionData.getRodString().getEveryRod().get(j).getType()==1){
								rodType="钢杆";
							}else if(rpcProductionData.getRodString().getEveryRod().get(j).getType()==2){
								rodType="玻璃钢杆";
							}else if(rpcProductionData.getRodString().getEveryRod().get(j).getType()==3){
								rodType="空心抽油杆";
							}
							result_json.append("\"rodTypeName"+(j+1)+"\":\""+rodType+"\",");
							result_json.append("\"rodGrade"+(j+1)+"\":\""+rpcProductionData.getRodString().getEveryRod().get(j).getGrade()+"\",");
							result_json.append("\"rodOutsideDiameter"+(j+1)+"\":\""+rpcProductionData.getRodString().getEveryRod().get(j).getOutsideDiameter()*1000+"\",");
							result_json.append("\"rodInsideDiameter"+(j+1)+"\":\""+rpcProductionData.getRodString().getEveryRod().get(j).getInsideDiameter()*1000+"\",");
							result_json.append("\"rodLength"+(j+1)+"\":\""+rpcProductionData.getRodString().getEveryRod().get(j).getLength()+"\",");
						}
					}
					
					if(rpcProductionData.getManualIntervention()!=null){
						String manualInterventionResultName="";
						if(rpcProductionData.getManualIntervention().getCode()==0){
							manualInterventionResultName="不干预";
						}else{
							if(jedis!=null && jedis.hexists("RPCWorkType".getBytes(), (rpcProductionData.getManualIntervention().getCode()+"").getBytes())){
								WorkType workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("RPCWorkType".getBytes(), (rpcProductionData.getManualIntervention().getCode()+"").getBytes()));
								manualInterventionResultName=workType.getResultName();
							}else{
								String resultNameSql="select t.resultname from tbl_rpc_worktype t where t.resultcode="+rpcProductionData.getManualIntervention().getCode();
								List<?> resultNameList = this.findCallSql(resultNameSql);
								if(resultNameList.size()>0){
									manualInterventionResultName=resultNameList.get(0).toString();
								}
							}
						}
						result_json.append("\"manualInterventionResult\":\""+manualInterventionResultName+"\",");
						result_json.append("\"netGrossRatio\":\""+rpcProductionData.getManualIntervention().getNetGrossRatio()+"\",");
						result_json.append("\"netGrossValue\":\""+rpcProductionData.getManualIntervention().getNetGrossValue()+"\",");
						result_json.append("\"levelCorrectValue\":\""+rpcProductionData.getManualIntervention().getLevelCorrectValue()+"\",");
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
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getRPMCalculateResultData(String orgId, String deviceName,String deviceId,String applicationScenarios, Page pager,String deviceType,String startDate,String endDate,String calculateSign,String calculateType)
			throws Exception {
		DataDictionary ddic = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicName="calculateManager_PCPSingleRecord";
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		columns = ddic.getTableHeader();
		
		String prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			prodCol=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,";
		}
		
		sql="select t.id,t.deviceId,t.deviceName,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss'),"
			+ "decode(t.resultStatus,1,'计算成功',0,'未计算',2,'未计算','计算失败'),"
			+ prodCol
			+ "t.rpm,"
			+ "t.productiondata"
			+ " from viw_pcp_calculatemain t where t.orgid in("+orgId+") "
			+ " and t.acqtime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss')";
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and  t.deviceName = '" + deviceName.trim() + "' ";
		}
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
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
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
						String rodType="钢杆";
						if(calculateRequestData.getRodString().getEveryRod().get(j).getType()==1){
							rodType="钢杆";
						}else if(calculateRequestData.getRodString().getEveryRod().get(j).getType()==2){
							rodType="玻璃钢杆";
						}else if(calculateRequestData.getRodString().getEveryRod().get(j).getType()==3){
							rodType="空心抽油杆";
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
	
	public String getDiagnoseAndProdCalculateWellListData(String orgId, String deviceName, Page pager,String deviceType,String calculateSign,String calculateType)
			throws Exception {
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String tableName="tbl_rpcacqdata_latest";
		String deviceTableName="tbl_device";
		StringBuffer result_json = new StringBuffer();
		if("1".equals(calculateType) || "3".equals(calculateType)){
			tableName="tbl_rpcacqdata_latest";
		}else if("2".equals(calculateType) || "4".equals(calculateType)){
			tableName="tbl_pcpacqdata_latest";
		}
		columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"deviceName\",flex:3 ,children:[] },"
				+ "{ \"header\":\"应用场景\",\"dataIndex\":\"applicationScenariosName\",flex:3 ,children:[] },"
				+ "{ \"header\":\"采集时间\",\"dataIndex\":\"acqTime\",flex:5,width:150,children:[] }"
				+ "]";
		sql="select well.id,well.deviceName,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,t.resultstatus,well.applicationscenarios,c1.itemname as applicationScenariosName "
				+ " from "+tableName+" t,"+deviceTableName+" well,tbl_code c1 "
				+ " where t.deviceId=well.id "
				+ " and c1.itemcode='APPLICATIONSCENARIOS' and well.applicationscenarios=c1.itemvalue  "
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
			result_json.append("\"applicationScenariosName\":\""+obj[5]+"\"},");
			
		}
		if(result_json.toString().endsWith(",")){
			result_json = result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		
//		String getResult = this.findCustomPageBySqlEntity(sql,finalSql, columns, 20 + "", pager);
		String json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getElecInverCalculateResultData(String orgId, String deviceName, Page pager,String wellType,String startDate,String endDate,String calculateSign,String calculateType)
			throws Exception {
		
		String sql="";
		String finalSql="";
		String sqlAll="";
		
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		sql="select t.id,t.deviceName,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss'),t.resultStatus,"
			+ "t.manufacturer,t.model,t.stroke,"
			+ "t.crankRotationDirection,t.offsetAngleOfCrank,t.crankGravityRadius,"
			+ "t.singleCrankWeight,t.structuralUnbalance,t.balancePosition,t.balanceWeight,"
			
			+ "t.offsetAngleOfCrankPS,t.surfaceSystemEfficiency,t.FS_LeftPercent,t.FS_RightPercent,wattAngle,"
			+ "t.filterTime_Watt,t.filterTime_I,filterTime_RPM,"
			+ "t.filterTime_FSDiagram,t.filterTime_FSDiagram_L,t.filterTime_FSDiagram_R"
			+ " from viw_rpc_calculatemain_elec t where t.orgid in("+orgId+") "
			+ " and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and  t.deviceName = '" + deviceName.trim() + "' ";
		}
		if(StringManagerUtils.isNotNull(calculateSign)){
			if("0".equals(calculateSign)){
				sql+=" and  t.resultstatus in(0,2) ";
			}else{
				sql+=" and  t.resultstatus = " + calculateSign + " ";
			}
		}
		sql+=" order by t.acqTime desc, t.deviceName";
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		
		String columns = "[{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"deviceName\" ,children:[] },"
				+ "{ \"header\":\"采集时间\",\"dataIndex\":\"acqTime\" ,children:[] },"
				+ "{ \"header\":\"计算状态\",\"dataIndex\":\"resultStatus\" ,children:[] },"
				
				+ "{ \"header\":\"抽油机厂家\",\"dataIndex\":\"manufacturer\" ,children:[] },"
				+ "{ \"header\":\"抽油机型号\",\"dataIndex\":\"model\" ,children:[] },"
				+ "{ \"header\":\"冲程(m)\",\"dataIndex\":\"stroke\" ,children:[] },"
				+ "{ \"header\":\"旋转方向\",\"dataIndex\":\"crankRotationDirection\" ,children:[] },"
				+ "{ \"header\":\"曲柄偏置角(°)\",\"dataIndex\":\"offsetAngleOfCrank\" ,children:[] },"
				+ "{ \"header\":\"曲柄重心半径(m)\",\"dataIndex\":\"crankGravityRadius\" ,children:[] },"
				+ "{ \"header\":\"单块曲柄重量(kN)\",\"dataIndex\":\"singleCrankWeight\" ,children:[] },"
				+ "{ \"header\":\"结构不平衡重(kN)\",\"dataIndex\":\"structuralUnbalance\" ,children:[] },"
				+ "{ \"header\":\"平衡块位置(m)\",\"dataIndex\":\"balancePosition\" ,children:[] },"
				+ "{ \"header\":\"平衡块重量(kN)\",\"dataIndex\":\"balanceWeight\" ,children:[] },"
				
				+ "{ \"header\":\"曲柄位置开关偏置角(°)\",\"dataIndex\":\"offsetAngleOfCrankPS\" ,children:[] },"
				+ "{ \"header\":\"地面效率\",\"dataIndex\":\"surfaceSystemEfficiency\" ,children:[] },"
				+ "{ \"header\":\"左侧截取百分比\",\"dataIndex\":\"FS_LeftPercent\" ,children:[] },"
				+ "{ \"header\":\"右侧截取百分比\",\"dataIndex\":\"FS_RightPercent\" ,children:[] },"
				+ "{ \"header\":\"功率滤波角度(°)\",\"dataIndex\":\"wattAngle\" ,children:[] },"
				+ "{ \"header\":\"功率滤波次数\",\"dataIndex\":\"filterTime_Watt\" ,children:[] },"
				+ "{ \"header\":\"电流滤波次数\",\"dataIndex\":\"filterTime_I\" ,children:[] },"
				+ "{ \"header\":\"转速滤波次数\",\"dataIndex\":\"filterTime_RPM\" ,children:[] },"
				+ "{ \"header\":\"功图滤波次数\",\"dataIndex\":\"filterTime_FSDiagram\" ,children:[] },"
				+ "{ \"header\":\"功图左侧滤波次数\",\"dataIndex\":\"filterTime_FSDiagram_L\" ,children:[] },"
				+ "{ \"header\":\"功图右侧滤波次数\",\"dataIndex\":\"filterTime_FSDiagram_R\" ,children:[] }"
				+ "]";
		
		result_json.append("{\"success\":true,\"totalCount\":"+totals+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"deviceName\":\""+obj[1]+"\",");
			result_json.append("\"acqTime\":\""+obj[2]+"\",");
			result_json.append("\"resultStatus\":\""+obj[3]+"\",");
			
			result_json.append("\"manufacturer\":\""+obj[4]+"\",");
			result_json.append("\"model\":\""+obj[5]+"\",");
			result_json.append("\"stroke\":\""+obj[6]+"\",");
			result_json.append("\"crankRotationDirection\":\""+obj[7]+"\",");
			result_json.append("\"offsetAngleOfCrank\":\""+obj[8]+"\",");
			result_json.append("\"crankGravityRadius\":\""+obj[9]+"\",");
			result_json.append("\"singleCrankWeight\":\""+obj[10]+"\",");
			result_json.append("\"structuralUnbalance\":\""+obj[11]+"\",");
			result_json.append("\"balancePosition\":\""+obj[12]+"\",");
			result_json.append("\"balanceWeight\":\""+obj[13]+"\",");
			
			result_json.append("\"offsetAngleOfCrankPS\":\""+obj[14]+"\",");
			result_json.append("\"surfaceSystemEfficiency\":\""+obj[15]+"\",");
			result_json.append("\"FS_LeftPercent\":\""+obj[16]+"\",");
			result_json.append("\"FS_RightPercent\":\""+obj[17]+"\",");
			result_json.append("\"wattAngle\":\""+obj[18]+"\",");
			result_json.append("\"filterTime_Watt\":\""+obj[19]+"\",");
			result_json.append("\"filterTime_I\":\""+obj[20]+"\",");
			result_json.append("\"filterTime_RPM\":\""+obj[21]+"\",");
			result_json.append("\"filterTime_FSDiagram\":\""+obj[22]+"\",");
			result_json.append("\"filterTime_FSDiagram_L\":\""+obj[23]+"\",");
			result_json.append("\"filterTime_FSDiagram_R\":\""+obj[24]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json = result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		
//		String getResult = this.findCustomPageBySqlEntity(sql,finalSql, columns, 20 + "", pager);
		String json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getElecInverCalculateWellListData(String orgId, String deviceName, Page pager,String wellType,String calculateSign,String calculateType)
			throws Exception {
		
		String sql="";
		String finalSql="";
		String sqlAll="";
		
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		sql="select t.id,t.deviceName,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss'),t.resultStatus,"
			+ "t.manufacturer,t.model,t.stroke,"
			+ "t.crankRotationDirection,t.offsetAngleOfCrank,t.crankGravityRadius,"
			+ "t.singleCrankWeight,t.structuralUnbalance,t.balancePosition,t.balanceWeight,"
			+ "t.offsetAngleOfCrankPS,t.surfaceSystemEfficiency,t.FS_LeftPercent,t.FS_RightPercent,wattAngle,"
			+ "t.filterTime_Watt,t.filterTime_I,filterTime_RPM,"
			+ "t.filterTime_FSDiagram,t.filterTime_FSDiagram_L,t.filterTime_FSDiagram_R"
			+ " from viw_rpc_calculatemain_elec t where t.orgid in("+orgId+") ";
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and  t.deviceName = '" + deviceName.trim() + "' ";
		}
		if(StringManagerUtils.isNotNull(calculateSign)){
			if("0".equals(calculateSign)){
				sql+=" and  t.resultstatus in(0,2) ";
			}else{
				sql+=" and  t.resultstatus = " + calculateSign + " ";
			}
		}
		sql+=" order by t.acqTime desc, t.deviceName";
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		String columns = "[{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"deviceName\" ,children:[] },"
				+ "{ \"header\":\"采集时间\",\"dataIndex\":\"acqTime\" ,children:[] },"
				+ "{ \"header\":\"计算状态\",\"dataIndex\":\"resultStatus\" ,children:[] },"
				
				+ "{ \"header\":\"抽油机厂家\",\"dataIndex\":\"manufacturer\" ,children:[] },"
				+ "{ \"header\":\"抽油机型号\",\"dataIndex\":\"model\" ,children:[] },"
				+ "{ \"header\":\"冲程(m)\",\"dataIndex\":\"stroke\" ,children:[] },"
				+ "{ \"header\":\"旋转方向\",\"dataIndex\":\"crankRotationDirection\" ,children:[] },"
				+ "{ \"header\":\"曲柄偏置角(°)\",\"dataIndex\":\"offsetAngleOfCrank\" ,children:[] },"
				+ "{ \"header\":\"曲柄重心半径(m)\",\"dataIndex\":\"crankGravityRadius\" ,children:[] },"
				+ "{ \"header\":\"单块曲柄重量(kN)\",\"dataIndex\":\"singleCrankWeight\" ,children:[] },"
				+ "{ \"header\":\"结构不平衡重(kN)\",\"dataIndex\":\"structuralUnbalance\" ,children:[] },"
				+ "{ \"header\":\"平衡块位置(m)\",\"dataIndex\":\"balancePosition\" ,children:[] },"
				+ "{ \"header\":\"平衡块重量(kN)\",\"dataIndex\":\"balanceWeight\" ,children:[] },"
				
				+ "{ \"header\":\"曲柄位置开关偏置角(°)\",\"dataIndex\":\"offsetAngleOfCrankPS\" ,children:[] },"
				+ "{ \"header\":\"地面效率\",\"dataIndex\":\"surfaceSystemEfficiency\" ,children:[] },"
				+ "{ \"header\":\"左侧截取百分比\",\"dataIndex\":\"FS_LeftPercent\" ,children:[] },"
				+ "{ \"header\":\"右侧截取百分比\",\"dataIndex\":\"FS_RightPercent\" ,children:[] },"
				+ "{ \"header\":\"功率滤波角度(°)\",\"dataIndex\":\"wattAngle\" ,children:[] },"
				+ "{ \"header\":\"功率滤波次数\",\"dataIndex\":\"filterTime_Watt\" ,children:[] },"
				+ "{ \"header\":\"电流滤波次数\",\"dataIndex\":\"filterTime_I\" ,children:[] },"
				+ "{ \"header\":\"转速滤波次数\",\"dataIndex\":\"filterTime_RPM\" ,children:[] },"
				+ "{ \"header\":\"功图滤波次数\",\"dataIndex\":\"filterTime_FSDiagram\" ,children:[] },"
				+ "{ \"header\":\"功图左侧滤波次数\",\"dataIndex\":\"filterTime_FSDiagram_L\" ,children:[] },"
				+ "{ \"header\":\"功图右侧滤波次数\",\"dataIndex\":\"filterTime_FSDiagram_R\" ,children:[] }"
				+ "]";
		result_json.append("{\"success\":true,\"totalCount\":"+totals+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"deviceName\":\""+obj[1]+"\",");
			result_json.append("\"acqTime\":\""+obj[2]+"\",");
			result_json.append("\"resultStatus\":\""+obj[3]+"\",");
			
			result_json.append("\"manufacturer\":\""+obj[4]+"\",");
			result_json.append("\"model\":\""+obj[5]+"\",");
			result_json.append("\"stroke\":\""+obj[6]+"\",");
			result_json.append("\"crankRotationDirection\":\""+obj[7]+"\",");
			result_json.append("\"offsetAngleOfCrank\":\""+obj[8]+"\",");
			result_json.append("\"crankGravityRadius\":\""+obj[9]+"\",");
			result_json.append("\"singleCrankWeight\":\""+obj[10]+"\",");
			result_json.append("\"structuralUnbalance\":\""+obj[11]+"\",");
			result_json.append("\"balancePosition\":\""+obj[12]+"\",");
			result_json.append("\"balanceWeight\":\""+obj[13]+"\",");
			
			result_json.append("\"offsetAngleOfCrankPS\":\""+obj[14]+"\",");
			result_json.append("\"surfaceSystemEfficiency\":\""+obj[15]+"\",");
			result_json.append("\"FS_LeftPercent\":\""+obj[16]+"\",");
			result_json.append("\"FS_RightPercent\":\""+obj[17]+"\",");
			result_json.append("\"wattAngle\":\""+obj[18]+"\",");
			result_json.append("\"filterTime_Watt\":\""+obj[19]+"\",");
			result_json.append("\"filterTime_I\":\""+obj[20]+"\",");
			result_json.append("\"filterTime_RPM\":\""+obj[21]+"\",");
			result_json.append("\"filterTime_FSDiagram\":\""+obj[22]+"\",");
			result_json.append("\"filterTime_FSDiagram_L\":\""+obj[23]+"\",");
			result_json.append("\"filterTime_FSDiagram_R\":\""+obj[24]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json = result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		String json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public void saveReCalculateData(CalculateManagerHandsontableChangedData calculateManagerHandsontableChangedData,int applicationScenarios) throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		if(calculateManagerHandsontableChangedData.getUpdatelist()!=null){
			Jedis jedis=null;
			try{
				try{
					jedis = RedisUtil.jedisPool.getResource();
					if(!jedis.exists("RPCWorkTypeByName".getBytes())){
						MemoryDataManagerTask.loadRPCWorkType();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				for(int i=0;i<calculateManagerHandsontableChangedData.getUpdatelist().size();i++){
					StringBuffer productionDataBuff = new StringBuffer();
					
					RPCCalculateRequestData.FluidPVT fluidPVT=new RPCCalculateRequestData.FluidPVT();
					RPCCalculateRequestData.Reservoir reservoir=new RPCCalculateRequestData.Reservoir();
					
					RPCCalculateRequestData.TubingString tubingString=new RPCCalculateRequestData.TubingString();
					tubingString.setEveryTubing(new ArrayList<RPCCalculateRequestData.EveryTubing>());
					tubingString.getEveryTubing().add(new RPCCalculateRequestData.EveryTubing());
					
					RPCCalculateRequestData.CasingString  casingString=new RPCCalculateRequestData.CasingString();
					casingString.setEveryCasing(new ArrayList<RPCCalculateRequestData.EveryCasing>());
					casingString.getEveryCasing().add(new RPCCalculateRequestData.EveryCasing());
					
					RPCCalculateRequestData.RodString rodString=new RPCCalculateRequestData.RodString();
					rodString.setEveryRod(new ArrayList<RPCCalculateRequestData.EveryRod>());
					
					RPCCalculateRequestData.Production production=new RPCCalculateRequestData.Production();
					
					RPCCalculateRequestData.Pump pump =new RPCCalculateRequestData.Pump();
					
					RPCCalculateRequestData.ManualIntervention manualIntervention=new RPCCalculateRequestData.ManualIntervention();
					
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
					if("组合泵".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getBarrelTypeName())){
						barrelType="L";
					}else if("整筒泵".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getBarrelTypeName())){
						barrelType="H";
					}
					
					pump.setBarrelType(barrelType);
					pump.setPumpGrade(StringManagerUtils.stringToInteger(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPumpGrade()));
					pump.setPumpBoreDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPumpboreDiameter())*0.001));
					pump.setPlungerLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPlungerLength()));
					
					
					if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength1())>0){
						RPCCalculateRequestData.EveryRod everyRod=new RPCCalculateRequestData.EveryRod();
						int rodType=0;
						if("钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName1())){
							rodType=1;
						}else if("玻璃钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName1())){
							rodType=2;
						}else if("空心抽油杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName1())){
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
						RPCCalculateRequestData.EveryRod everyRod=new RPCCalculateRequestData.EveryRod();
						int rodType=0;
						if("钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName2())){
							rodType=1;
						}else if("玻璃钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName2())){
							rodType=2;
						}else if("空心抽油杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName2())){
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
						RPCCalculateRequestData.EveryRod everyRod=new RPCCalculateRequestData.EveryRod();
						int rodType=0;
						if("钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName3())){
							rodType=1;
						}else if("玻璃钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName3())){
							rodType=2;
						}else if("空心抽油杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName3())){
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
						RPCCalculateRequestData.EveryRod everyRod=new RPCCalculateRequestData.EveryRod();
						int rodType=0;
						if("钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName4())){
							rodType=1;
						}else if("玻璃钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName4())){
							rodType=2;
						}else if("空心抽油杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName4())){
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
					if(!"不干预".equalsIgnoreCase(manualInterventionResultName)){
						if(jedis!=null && jedis.hexists("RPCWorkTypeByName".getBytes(), (manualInterventionResultName).getBytes())){
							WorkType workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("RPCWorkTypeByName".getBytes(), (manualInterventionResultName).getBytes()));
							manualInterventionResultCode=workType.getResultCode();
						}else{
							String resultNameSql="select t.resultcode from tbl_rpc_worktype t where t.resultname='"+manualInterventionResultName+"'";
							List<?> resultList = this.findCallSql(resultNameSql);
							if(resultList.size()>0){
								manualInterventionResultCode=StringManagerUtils.stringToInteger(resultList.get(0).toString());
							}
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
					productionDataBuff.append("\"Pump\":"+(pump!=null?gson.toJson(pump):"{}")+",");
					productionDataBuff.append("\"Production\":"+(production!=null?gson.toJson(production):"{}")+",");
					productionDataBuff.append("\"ManualIntervention\":"+(manualIntervention!=null?gson.toJson(manualIntervention):"{}"));
					productionDataBuff.append("}");
					
					String updateSql="update tbl_rpcacqdata_hist t set t.resultstatus=2,t.productiondata='"+productionDataBuff.toString()+"' where t.id="+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getId();
					
					this.getBaseDao().updateOrDeleteBySql(updateSql);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(jedis!=null&&jedis.isConnected()){
					jedis.close();
				}
			}
		}
	}
	
	public void saveRPMReCalculateData(CalculateManagerHandsontableChangedData calculateManagerHandsontableChangedData,int applicationScenarios) throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
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
					if("钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName1())){
						rodType=1;
					}else if("玻璃钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName1())){
						rodType=2;
					}else if("空心抽油杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName1())){
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
					if("钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName2())){
						rodType=1;
					}else if("玻璃钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName2())){
						rodType=2;
					}else if("空心抽油杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName2())){
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
					if("钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName3())){
						rodType=1;
					}else if("玻璃钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName3())){
						rodType=2;
					}else if("空心抽油杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName3())){
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
					if("钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName4())){
						rodType=1;
					}else if("玻璃钢杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName4())){
						rodType=2;
					}else if("空心抽油杆".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodTypeName4())){
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
				
				String updateSql="update tbl_pcpacqdata_hist t set t.resultstatus=2,t.productiondata='"+productionDataBuff.toString()+"' where t.id="+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getId();
				
				this.getBaseDao().updateOrDeleteBySql(updateSql);
			}
		}
	}
//	
//	public void saveElecInverPumpingUnitData(ElecInverCalculateManagerHandsontableChangedData elecInverCalculateManagerHandsontableChangedData) throws Exception {
//		getBaseDao().saveElecInverPumpingUnitData(elecInverCalculateManagerHandsontableChangedData);
//	}
//	public void saveElecInverOptimizeHandsontableData(ElecInverCalculateManagerHandsontableChangedData elecInverCalculateManagerHandsontableChangedData,String orgid) throws Exception {
//		getBaseDao().saveElecInverOptimizeHandsontableData(elecInverCalculateManagerHandsontableChangedData,orgid);
//	}
//	public boolean reInverDiagram(String recordId,InversioneFSdiagramResponseData inversioneFSdiagramResponseData) throws SQLException, ParseException{
//		String SStr="",FStr="",PStr="",AStr="",RPMStr="";
//		String F360Str="",S360Str="",A360Str="";
//		if(inversioneFSdiagramResponseData.getResultStatus()==1){
//			SStr=StringUtils.join(inversioneFSdiagramResponseData.getS(), ",");
//			FStr=StringUtils.join(inversioneFSdiagramResponseData.getF(), ",");
//		}
//		PStr=StringUtils.join(inversioneFSdiagramResponseData.getWatt(), ",");
//		AStr=StringUtils.join(inversioneFSdiagramResponseData.getI(), ",");
//		RPMStr=StringUtils.join(inversioneFSdiagramResponseData.getRPM(), ",");
//		
//		
//		if(inversioneFSdiagramResponseData.getF360()!=null){
//			F360Str=StringUtils.join(inversioneFSdiagramResponseData.getF360(), ",");
//		}
//		if(inversioneFSdiagramResponseData.getS360()!=null){
//			S360Str=StringUtils.join(inversioneFSdiagramResponseData.getS360(), ",");
//		}
//		if(inversioneFSdiagramResponseData.getA360()!=null){
//			A360Str=StringUtils.join(inversioneFSdiagramResponseData.getA360(), ",");
//		}
//		
//		return this.getBaseDao().reInverDiagram(recordId,inversioneFSdiagramResponseData.getAcquisitionTime(),
//				inversioneFSdiagramResponseData.getCNT(),inversioneFSdiagramResponseData.getStroke(),inversioneFSdiagramResponseData.getSPM(),
//				inversioneFSdiagramResponseData.getMaxF(),inversioneFSdiagramResponseData.getMinF(),
//				SStr,FStr,
//				S360Str,A360Str,F360Str,
//				AStr,PStr,RPMStr,
//				inversioneFSdiagramResponseData.getUpstrokeIMax(),inversioneFSdiagramResponseData.getDownstrokeIMax(),
//				inversioneFSdiagramResponseData.getUpstrokeWattMax(),inversioneFSdiagramResponseData.getDownstrokeWattMax(),
//				inversioneFSdiagramResponseData.getIDegreeBalance(),inversioneFSdiagramResponseData.getWattDegreeBalance(),
//				inversioneFSdiagramResponseData.getResultStatus());
//	}
	public String getCalculateStatusList(String orgId, String deviceName, String calculateType,String startDate,String endDate)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql="";
		String tableName="tbl_rpcacqdata_latest";
		String deviceTableName="tbl_device";
		if(StringManagerUtils.stringToInteger(calculateType)==2){
			tableName="tbl_pcpacqdata_latest";
		}
		sql="select distinct(decode(t.resultstatus,2,0,t.resultstatus)),t2.itemname "
				+ " from "+tableName+" t,tbl_code t2,"+deviceTableName+" t3 "
				+ " where t.deviceId=t3.id and decode(t.resultstatus,2,0,t.resultstatus)=t2.itemvalue and t2.itemcode='JSBZ'"
				+ " and t3.orgid in("+orgId+") "
				+ " and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and  t3.deviceName = '" + deviceName.trim() + "' ";
		}
		
		sql+=" order by decode(t.resultstatus,2,0,t.resultstatus)";
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(sql);
			result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\"选择全部\"}");
			String get_key = "";
			String get_val = "";
			if (null != list && list.size() > 0) {
				for (Object o : list) {
					Object[] obj = (Object[]) o;
					get_key = obj[0] + "";
					get_val = (String) obj[1];
					result_json.append(",{boxkey:\"" + get_key + "\",");
					result_json.append("boxval:\"" + get_val + "\"}");
				}
			}
			result_json.append("]}");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}
	
	public int recalculateByProductionData(String orgId, String deviceName, String deviceType,String startDate,String endDate,String calculateSign,String calculateType)throws Exception {
		String tableName="tbl_rpcacqdata_hist";
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
	
	public String getCalculateRequestData(String recordId,String deviceName,String acqTime,String calculateType) throws SQLException, IOException, ParseException{
		String requestData="{}";
		if("1".equals(calculateType)){
			requestData=this.getFSDiagramCalculateRequestData(recordId,deviceName,acqTime);
		}if("2".equals(calculateType)){
			requestData=this.getRPMCalculateRequestData(recordId,deviceName,acqTime);
		}else if("5".equals(calculateType)){
			requestData=this.getElecInverCalculateRequestData(deviceName,acqTime);
		}
		return requestData;
	}
	
	public String getFSDiagramCalculateRequestData(String recordId,String deviceName,String acqTime) throws SQLException, IOException, ParseException{
		String requestData="{}";
		String sql=""
				+ " select t2.deviceName,decode(t2.applicationscenarios,0,'cbm','oil') as applicationscenarios,"
				+ " to_char(t.fesdiagramacqTime,'yyyy-mm-dd hh24:mi:ss') as fesdiagramacqTime,t.fesdiagramSrc,"
				+ " t.stroke,t.spm,"
				+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
				+ " t.productiondata,"
				+ " t3.id as pumpingmodelid,t3.manufacturer,t3.model,t3.crankrotationdirection,t3.offsetangleofcrank,t3.crankgravityradius,t3.singlecrankweight,t3.singlecrankpinweight,t3.structuralunbalance,"
				+ " t.balanceinfo"
				+ " from tbl_rpcacqdata_hist t"
				+ " left outer join tbl_device t2 on t.deviceId=t2.id"
				+ " left outer join tbl_pumpingmodel t3 on t3.id=t.pumpingmodelid"
				+ " where 1=1  "
				+ " and t.id="+recordId;
		List<?> list = this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			requestData=calculateDataService.getObjectToRPCCalculateRequestData(obj);
		}
		return requestData;
	}
	
	public String getRPMCalculateRequestData(String recordId,String deviceName,String acqTime) throws SQLException, IOException, ParseException{
		String requestData="{}";
		String sql=""
				+ " select t2.deviceName,decode(t2.applicationscenarios,0,'cbm','oil') as applicationscenarios,"
				+ " to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss'),"
				+ " t.rpm,t.productiondata"
				+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2"
				+ " where t.deviceId=t2.id  "
				+ " and t.id="+recordId;
		List<?> list = this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			requestData=calculateDataService.getObjectToRPMCalculateRequestData(obj);
		}
		return requestData;
	}
	
	public String getElecInverCalculateRequestData(String deviceName,String acqTime) throws SQLException, IOException, ParseException{
		String requestData="{}";
		StringBuffer result_json = new StringBuffer();
		String sql="select t.deviceName,t2.id as diagramid,to_char(t2.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ " t2.spm,t2.rawpower_curve,t2.rawcurrent_curve,t2.rawrpm_curve, "
				+ " t4.manufacturer,t4.model,t4.stroke,decode(t4.crankrotationdirection,'顺时针','Clockwise','Anticlockwise'),"
				+ " t4.offsetangleofcrank,t5.offsetangleofcrankps,t4.crankgravityradius,t4.singlecrankweight,t4.structuralunbalance,"
				+ " t4.gearreducerratio,t4.gearreducerbeltpulleydiameter, t4.balanceposition,t4.balanceweight,"
				+ " t5.surfacesystemefficiency,t5.fs_leftpercent,t5.fs_rightpercent,"
				+ " t5.wattangle,t5.filtertime_watt,t5.filtertime_i,t5.filtertime_rpm,t5.filtertime_fsdiagram,t5.filtertime_fsdiagram_l,t5.filtertime_fsdiagram_r,"
				+ " t4.prtf "
				+ " from tbl_wellinformation t,tbl_rpc_diagram_hist t2,tbl_rpcinformation t4,tbl_rpc_inver_opt t5 "
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
	
	public String getTotalCalculateResultData(String orgId, String deviceName, Page pager,String deviceType,String startDate,String endDate,String calculateType)
			throws Exception {
		String json="";
		if("3".equals(calculateType)){
			json=this.getFESDiagramTotalCalculateResultData(orgId, deviceName, pager, deviceType, startDate, endDate,  calculateType);
		}else if("4".equals(calculateType)){
			json=this.getRPMTotalCalculateResultData(orgId, deviceName, pager, deviceType, startDate, endDate, calculateType);
		}
		
		return json;
	}
	
	public String getFESDiagramTotalCalculateResultData(String orgId, String deviceName, Page pager,String deviceType,String startDate,String endDate,String calculateType)
			throws Exception {
		DataDictionary ddic = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicName="calculateManager_RPCTotalRecord";
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		columns = ddic.getTableHeader();
		
		String prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			prodCol=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,";
		}
		
		sql="select t.id,t.deviceId,t.deviceName,to_char(t.caldate,'yyyy-mm-dd'),"
			+ "t.resultname,t.resultString,"
			+ prodCol
			+ " t.pumpeff,t.systemefficiency,t.wattDegreeBalance,t.iDegreeBalance,t.todayKWattH"
			+ " from viw_rpcdailycalculationdata t where t.org_id in("+orgId+") "
			+ " and t.caldate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and  t.deviceName = '" + deviceName.trim() + "' ";
		}
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
			result_json.append("\"resultName\":\""+obj[4]+"\",");
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
	
	public String getRPMTotalCalculateResultData(String orgId, String deviceName, Page pager,String deviceType,String startDate,String endDate,String calculateType)
			throws Exception {
		DataDictionary ddic = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicName="calculateManager_PCPTotalRecord";
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		columns = ddic.getTableHeader();
		
		String prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			prodCol=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,";
		}
		
		sql="select t.id,t.deviceId,t.deviceName,to_char(t.caldate,'yyyy-mm-dd'),"
			+ prodCol
			+ " t.pumpeff,t.systemefficiency,t.todayKWattH"
			+ " from viw_pcpdailycalculationdata t where t.org_id in("+orgId+") "
			+ " and t.caldate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		if(StringManagerUtils.isNotNull(deviceName)){
			sql+=" and  t.deviceName = '" + deviceName.trim() + "' ";
		}
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
			ThreadPool executor = new ThreadPool("RPCReTotalCalculate",
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
				+ " from tbl_rpcdailycalculationdata t,tbl_device t2 "
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
				+ " from tbl_rpcacqdata_hist t "
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
				+ " from tbl_pcpdailycalculationdata t,tbl_pcpdevice t2 "
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
