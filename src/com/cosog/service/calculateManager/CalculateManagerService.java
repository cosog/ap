package com.cosog.service.calculateManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cosog.dao.BaseDao;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.RPCCalculateRequestData;
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
import com.cosog.utils.CalculateUtils;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.BLOB;
import oracle.sql.CLOB;

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
	public String getCalculateResultData(String orgId, String wellName, Page pager,String deviceType,String startDate,String endDate,String calculateSign,String calculateType)
			throws Exception {
		String json="";
		if("1".equals(calculateType)){
			json=this.getFESDiagramCalculateResultData(orgId, wellName, pager, deviceType, startDate, endDate, calculateSign, calculateType);
		}else if("2".equals(calculateType)){
			json=this.getRPMCalculateResultData(orgId, wellName, pager, deviceType, startDate, endDate, calculateSign, calculateType);
		}else if("5".equals(calculateType)){//电参反演地面功图
			json=this.getElecInverCalculateResultData(orgId, wellName, pager, deviceType, startDate, endDate, calculateSign, calculateType);
		}
		
		return json;
	}
	
	public String getWellList(String orgId, String wellName, Page pager,String wellType,String startDate,String endDate,String calculateSign,String calculateType)
			throws Exception {
		String json="";
		if("1".equals(calculateType)||"2".equals(calculateType)||"3".equals(calculateType)||"4".equals(calculateType)){
			json=this.getDiagnoseAndProdCalculateWellListData(orgId, wellName, pager, wellType, startDate, endDate, calculateSign, calculateType);
		}else if("5".equals(calculateType)){//电参反演地面功图
			json=this.getElecInverCalculateWellListData(orgId, wellName, pager, wellType, startDate, endDate, calculateSign, calculateType);
		}
		
		return json;
	}
	
	public String getFESDiagramCalculateResultData(String orgId, String wellName, Page pager,String deviceType,String startDate,String endDate,String calculateSign,String calculateType)
			throws Exception {
		DataDictionary ddic = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicName="calculateManager";
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		columns = ddic.getTableHeader();
		
		String prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			prodCol=" t.liquidWeightProduction,t.oilWeightProduction,";
		}
		
		sql="select t.id,t.wellId,t.wellName,to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss'),"
			+ "decode(t.resultStatus,-1,'无效功图',1,'计算成功',0,'未计算',2,'未计算','计算失败'),"
			+ "t.resultName,"
			+ prodCol
			+ "t.productiondata"
			+ " from viw_rpc_calculatemain t "
			+ " where t.orgid in("+orgId+") "
			+ " and t.resultStatus<>-1"
			+ " and t.fesdiagramacqtime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss')";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName = '" + wellName.trim() + "' ";
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
		List<?> list = this.findCallSql(finalSql);
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"start_date\":\""+startDate+"\",");
		result_json.append("\"end_date\":\""+endDate+"\",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String productionData=obj[8].toString();
			type = new TypeToken<RPCCalculateRequestData>() {}.getType();
			RPCCalculateRequestData rpcProductionData=gson.fromJson(productionData, type);
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"wellId\":\""+obj[1]+"\",");
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"acqTime\":\""+obj[3]+"\",");
			result_json.append("\"resultStatus\":\""+obj[4]+"\",");
			result_json.append("\"resultName\":\""+obj[5]+"\",");
			
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				result_json.append("\"liquidWeightProduction\":\""+obj[6]+"\",");
				result_json.append("\"oilWeightProduction\":\""+obj[7]+"\",");
			}else{
				result_json.append("\"liquidVolumetricProduction\":\""+obj[6]+"\",");
				result_json.append("\"oilVolumetricProduction\":\""+obj[7]+"\",");
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
					String pumpType="";
					String barrelType="";
					if(rpcProductionData.getPump()!=null&&rpcProductionData.getPump().getPumpType()!=null){
						if("R".equalsIgnoreCase(rpcProductionData.getPump().getPumpType())){
							pumpType="杆式泵";
						}else if("T".equalsIgnoreCase(rpcProductionData.getPump().getPumpType())){
							pumpType="管式泵";
						}
					}
					if(rpcProductionData.getPump()!=null&&rpcProductionData.getPump().getBarrelType()!=null){
						if("L".equalsIgnoreCase(rpcProductionData.getPump().getBarrelType())){
							barrelType="组合泵";
						}else if("H".equalsIgnoreCase(rpcProductionData.getPump().getBarrelType())){
							barrelType="整筒泵";
						}
					}
					result_json.append("\"pumpTypeName\":\""+pumpType+"\",");
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
						result_json.append("\"rodGrade"+(j+1)+"\":\""+rpcProductionData.getRodString().getEveryRod().get(j).getGrade()+"\",");
						result_json.append("\"rodOutsideDiameter"+(j+1)+"\":\""+rpcProductionData.getRodString().getEveryRod().get(j).getOutsideDiameter()*1000+"\",");
						result_json.append("\"rodInsideDiameter"+(j+1)+"\":\""+rpcProductionData.getRodString().getEveryRod().get(j).getInsideDiameter()*1000+"\",");
						result_json.append("\"rodLength"+(j+1)+"\":\""+rpcProductionData.getRodString().getEveryRod().get(j).getLength()+"\",");
					}
				}
				
				if(rpcProductionData.getManualIntervention()!=null){
					result_json.append("\"netGrossRatio\":\""+rpcProductionData.getManualIntervention().getNetGrossRatio()+"\",");
					result_json.append("\"netGrossValue\":\""+rpcProductionData.getManualIntervention().getNetGrossValue()+"\",");
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
	
	public String getRPMCalculateResultData(String orgId, String wellName, Page pager,String deviceType,String startDate,String endDate,String calculateSign,String calculateType)
			throws Exception {
		DataDictionary ddic = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicName="screwPumpCalculateManager";
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		columns = ddic.getTableHeader();
		
		String prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			prodCol=" t.liquidWeightProduction,t.oilWeightProduction,";
		}
		
		sql="select t.id,t.wellId,t.wellName,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss'),"
			+ "decode(t.resultStatus,1,'计算成功',0,'未计算',2,'未计算','计算失败'),"
			+ prodCol
			+ "t.productiondata"
			+ " from viw_pcp_calculatemain t where t.orgid in("+orgId+") "
			+ " and t.acqtime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss')";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName = '" + wellName.trim() + "' ";
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
		result_json.append("\"start_date\":\""+startDate+"\",");
		result_json.append("\"end_date\":\""+endDate+"\",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String productionData=obj[7].toString();
			type = new TypeToken<PCPCalculateRequestData>() {}.getType();
			PCPCalculateRequestData calculateRequestData=gson.fromJson(productionData, type);
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"wellId\":\""+obj[1]+"\",");
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"acqTime\":\""+obj[3]+"\",");
			result_json.append("\"resultStatus\":\""+obj[4]+"\",");
			
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				result_json.append("\"liquidWeightProduction\":\""+obj[5]+"\",");
				result_json.append("\"oilWeightProduction\":\""+obj[6]+"\",");
			}else{
				result_json.append("\"liquidVolumetricProduction\":\""+obj[5]+"\",");
				result_json.append("\"oilVolumetricProduction\":\""+obj[6]+"\",");
			}
			
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
					result_json.append("\"rotorDiameter\":\""+calculateRequestData.getPump().getRotorDiameter()+"\",");
					result_json.append("\"qpr\":\""+calculateRequestData.getPump().getQPR()+"\",");
				}
				if(calculateRequestData.getTubingString()!=null&&calculateRequestData.getTubingString().getEveryTubing()!=null&&calculateRequestData.getTubingString().getEveryTubing().size()>0){
					result_json.append("\"tubingStringInsideDiameter\":\""+calculateRequestData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000+"\",");
				}
				if(calculateRequestData.getCasingString()!=null&&calculateRequestData.getCasingString().getEveryCasing()!=null&&calculateRequestData.getCasingString().getEveryCasing().size()>0){
					result_json.append("\"casingStringInsideDiameter\":\""+calculateRequestData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000+"\",");
				}
				
				if(calculateRequestData.getRodString()!=null && calculateRequestData.getRodString().getEveryRod()!=null){
					for(int j=0;j<calculateRequestData.getRodString().getEveryRod().size();j++){
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
	
	public String getDiagnoseAndProdCalculateWellListData(String orgId, String wellName, Page pager,String wellType,String startDate,String endDate,String calculateSign,String calculateType)
			throws Exception {
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String tableName="tbl_rpc_diagram_latest";
		String deviceTableName="tbl_rpcdevice";
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		if("1".equals(calculateType)){
			tableName="tbl_rpcacqdata_latest";
			deviceTableName="tbl_rpcdevice";
		}else if("2".equals(calculateType)){
			tableName="tbl_pcpacqdata_latest";
			deviceTableName="tbl_pcpdevice";
		}
		
		columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"wellName\",flex:3 ,children:[] },"
				+ "{ \"header\":\"采集时间\",\"dataIndex\":\"acqTime\",flex:5,width:150,children:[] }"
				+ "]";
		
		
		
		sql="select t.id,well.wellname,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,t.resultstatus "
				+ " from "+tableName+" t,"+deviceTableName+" well "
				+ " where t.wellid=well.id  and well.orgid in("+orgId+") ";
		
		
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  well.wellName = '" + wellName.trim() + "' ";
		}
		if(StringManagerUtils.isNotNull(calculateSign)){
			if("0".equals(calculateSign)){
				sql+=" and  t.resultstatus in(0,2) ";
			}else{
				sql+=" and  t.resultstatus = " + calculateSign + " ";
			}
		}
		sql+=" order by well.sortnum,well.wellName";
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		
		result_json.append("{\"success\":true,\"totalCount\":"+totals+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"acqTime\":\""+obj[2]+"\"},");
			
		}
		if(result_json.toString().endsWith(",")){
			result_json = result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		
//		String getResult = this.findCustomPageBySqlEntity(sql,finalSql, columns, 20 + "", pager);
		String json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getElecInverCalculateResultData(String orgId, String wellName, Page pager,String wellType,String startDate,String endDate,String calculateSign,String calculateType)
			throws Exception {
		
		String sql="";
		String finalSql="";
		String sqlAll="";
		
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		sql="select t.id,t.wellName,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss'),t.resultStatus,"
			+ "t.manufacturer,t.model,t.stroke,"
			+ "t.crankRotationDirection,t.offsetAngleOfCrank,t.crankGravityRadius,"
			+ "t.singleCrankWeight,t.structuralUnbalance,t.balancePosition,t.balanceWeight,"
			
			+ "t.offsetAngleOfCrankPS,t.surfaceSystemEfficiency,t.FS_LeftPercent,t.FS_RightPercent,wattAngle,"
			+ "t.filterTime_Watt,t.filterTime_I,filterTime_RPM,"
			+ "t.filterTime_FSDiagram,t.filterTime_FSDiagram_L,t.filterTime_FSDiagram_R"
			+ " from viw_rpc_calculatemain_elec t where t.orgid in("+orgId+") "
			+ " and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName = '" + wellName.trim() + "' ";
		}
		if(StringManagerUtils.isNotNull(calculateSign)){
			if("0".equals(calculateSign)){
				sql+=" and  t.resultstatus in(0,2) ";
			}else{
				sql+=" and  t.resultstatus = " + calculateSign + " ";
			}
		}
		sql+=" order by t.acqTime desc, t.wellName";
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		
		String columns = "[{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"wellName\" ,children:[] },"
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
			result_json.append("\"wellName\":\""+obj[1]+"\",");
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
	
	public String getElecInverCalculateWellListData(String orgId, String wellName, Page pager,String wellType,String startDate,String endDate,String calculateSign,String calculateType)
			throws Exception {
		
		String sql="";
		String finalSql="";
		String sqlAll="";
		
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		sql="select t.id,t.wellName,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss'),t.resultStatus,"
			+ "t.manufacturer,t.model,t.stroke,"
			+ "t.crankRotationDirection,t.offsetAngleOfCrank,t.crankGravityRadius,"
			+ "t.singleCrankWeight,t.structuralUnbalance,t.balancePosition,t.balanceWeight,"
			+ "t.offsetAngleOfCrankPS,t.surfaceSystemEfficiency,t.FS_LeftPercent,t.FS_RightPercent,wattAngle,"
			+ "t.filterTime_Watt,t.filterTime_I,filterTime_RPM,"
			+ "t.filterTime_FSDiagram,t.filterTime_FSDiagram_L,t.filterTime_FSDiagram_R"
			+ " from viw_rpc_calculatemain_elec t where t.orgid in("+orgId+") "
			+ " and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName = '" + wellName.trim() + "' ";
		}
		if(StringManagerUtils.isNotNull(calculateSign)){
			if("0".equals(calculateSign)){
				sql+=" and  t.resultstatus in(0,2) ";
			}else{
				sql+=" and  t.resultstatus = " + calculateSign + " ";
			}
		}
		sql+=" order by t.acqTime desc, t.wellName";
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		String columns = "[{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"wellName\" ,children:[] },"
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
			result_json.append("\"wellName\":\""+obj[1]+"\",");
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
	
	public void saveReCalculateData(CalculateManagerHandsontableChangedData calculateManagerHandsontableChangedData) throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		if(calculateManagerHandsontableChangedData.getUpdatelist()!=null){
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
				
				fluidPVT.setCrudeOilDensity(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getCrudeoilDensity()));
				fluidPVT.setWaterDensity(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWaterDensity()));
				fluidPVT.setNaturalGasRelativeDensity(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getNaturalGasRelativeDensity()));
				fluidPVT.setSaturationPressure(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getSaturationPressure()));
				
				reservoir.setDepth(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getReservoirDepth()));
				reservoir.setTemperature(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getReservoirTemperature()));
				
				tubingString.getEveryTubing().get(0).setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getTubingStringInsideDiameter())*0.001));
				casingString.getEveryCasing().get(0).setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getCasingStringInsideDiameter())*0.001));
				
				production.setTubingPressure(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getTubingPressure()));
				production.setCasingPressure(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getCasingPressure()));
				production.setWellHeadTemperature(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWellHeadFluidTemperature()));
				production.setWaterCut(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWeightWaterCut()));
				production.setProductionGasOilRatio(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getProductionGasOilRatio()));
				production.setProducingfluidLevel(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getProducingFluidLevel()));
				production.setPumpSettingDepth(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPumpSettingDepth()));
				
				
				String pumpType="";
				String barrelType="";
				if("杆式泵".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPumpTypeName())){
					pumpType="R";
				}else if("管式泵".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPumpTypeName())){
					pumpType="T";
				}
				if("组合泵".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getBarrelTypeName())){
					barrelType="L";
				}else if("整筒泵".equalsIgnoreCase(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getBarrelTypeName())){
					barrelType="H";
				}
				
				pump.setPumpType(pumpType);
				pump.setBarrelType(barrelType);
				pump.setPumpGrade(StringManagerUtils.stringToInteger(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPumpGrade()));
				pump.setPumpBoreDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPumpboreDiameter())*0.001));
				pump.setPlungerLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPlungerLength()));
				
				
				if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength1())>0){
					RPCCalculateRequestData.EveryRod everyRod=new RPCCalculateRequestData.EveryRod();
					everyRod.setGrade(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade1());
					everyRod.setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter1())*0.001));
					everyRod.setOutsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter1())*0.001));
					everyRod.setLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength1()));
					rodString.getEveryRod().add(everyRod);
				}
				
				if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength2())>0){
					RPCCalculateRequestData.EveryRod everyRod=new RPCCalculateRequestData.EveryRod();
					everyRod.setGrade(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade2());
					everyRod.setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter2())*0.001));
					everyRod.setOutsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter2())*0.001));
					everyRod.setLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength2()));
					rodString.getEveryRod().add(everyRod);
				}
				
				if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength3())>0){
					RPCCalculateRequestData.EveryRod everyRod=new RPCCalculateRequestData.EveryRod();
					everyRod.setGrade(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade3());
					everyRod.setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter3())*0.001));
					everyRod.setOutsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter3())*0.001));
					everyRod.setLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength3()));
					rodString.getEveryRod().add(everyRod);
				}
				
				if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength4())>0){
					RPCCalculateRequestData.EveryRod everyRod=new RPCCalculateRequestData.EveryRod();
					everyRod.setGrade(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade4());
					everyRod.setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter4())*0.001));
					everyRod.setOutsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter4())*0.001));
					everyRod.setLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength4()));
					rodString.getEveryRod().add(everyRod);
				}
				
				manualIntervention.setNetGrossRatio(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getNetGrossRatio()));
				manualIntervention.setNetGrossValue(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getNetGrossValue()));
				
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
		}
	}
	
	public void saveRPMReCalculateData(CalculateManagerHandsontableChangedData calculateManagerHandsontableChangedData) throws Exception {
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
				
				fluidPVT.setCrudeOilDensity(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getCrudeoilDensity()));
				fluidPVT.setWaterDensity(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWaterDensity()));
				fluidPVT.setNaturalGasRelativeDensity(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getNaturalGasRelativeDensity()));
				fluidPVT.setSaturationPressure(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getSaturationPressure()));
				
				reservoir.setDepth(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getReservoirDepth()));
				reservoir.setTemperature(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getReservoirTemperature()));
				
				tubingString.getEveryTubing().get(0).setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getTubingStringInsideDiameter())*0.001));
				casingString.getEveryCasing().get(0).setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getCasingStringInsideDiameter())*0.001));
				
				production.setTubingPressure(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getTubingPressure()));
				production.setCasingPressure(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getCasingPressure()));
				production.setWellHeadTemperature(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWellHeadFluidTemperature()));
				production.setWaterCut(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWeightWaterCut()));
				production.setProductionGasOilRatio(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getProductionGasOilRatio()));
				production.setProducingfluidLevel(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getProducingFluidLevel()));
				production.setPumpSettingDepth(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPumpSettingDepth()));
				
				pump.setBarrelLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getBarrelLength()));
				pump.setBarrelSeries(StringManagerUtils.stringToInteger(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getBarrelSeries()));
				pump.setRotorDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRotorDiameter())*0.001));
				pump.setQPR(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getQPR()));
				
				if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength1())>0){
					PCPCalculateRequestData.EveryRod everyRod=new PCPCalculateRequestData.EveryRod();
					everyRod.setGrade(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade1());
					everyRod.setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter1())*0.001));
					everyRod.setOutsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter1())*0.001));
					everyRod.setLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength1()));
					rodString.getEveryRod().add(everyRod);
				}
				
				if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength2())>0){
					PCPCalculateRequestData.EveryRod everyRod=new PCPCalculateRequestData.EveryRod();
					everyRod.setGrade(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade2());
					everyRod.setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter2())*0.001));
					everyRod.setOutsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter2())*0.001));
					everyRod.setLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength2()));
					rodString.getEveryRod().add(everyRod);
				}
				
				if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength3())>0){
					PCPCalculateRequestData.EveryRod everyRod=new PCPCalculateRequestData.EveryRod();
					everyRod.setGrade(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade3());
					everyRod.setInsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter3())*0.001));
					everyRod.setOutsideDiameter((float) (StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter3())*0.001));
					everyRod.setLength(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength3()));
					rodString.getEveryRod().add(everyRod);
				}
				
				if(StringManagerUtils.stringToFloat(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength4())>0){
					PCPCalculateRequestData.EveryRod everyRod=new PCPCalculateRequestData.EveryRod();
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
	public String getCalculateStatusList(String orgId, String wellName, String deviceType,String startDate,String endDate)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql="";
		String tableName="tbl_rpcacqdata_latest";
		String deviceTableName="tbl_rpcdevice";
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			tableName="tbl_pcpacqdata_latest";
			deviceTableName="tbl_pcpdevice";
		}
		sql="select distinct(decode(t.resultstatus,2,0,t.resultstatus)),t2.itemname "
				+ " from "+tableName+" t,tbl_code t2,"+deviceTableName+" t3 "
				+ " where t.wellid=t3.id and decode(t.resultstatus,2,0,t.resultstatus)=t2.itemvalue and t2.itemcode='JSBZ'"
				+ " and t3.orgid in("+orgId+") "
				+ " and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t3.wellName = '" + wellName.trim() + "' ";
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
	
	public int recalculateByProductionData(String orgId, String wellName, String deviceType,String startDate,String endDate,String calculateSign)throws Exception {
		String tableName="tbl_rpcacqdata_hist";
		String deviceTableName="tbl_rpcdevice";
		String acqTimeColumn="fesdiagramacqtime";
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			tableName="tbl_pcpacqdata_hist";
			deviceTableName="tbl_pcpdevice";
			acqTimeColumn="acqtime";
		}
		
		String updateSql="update "+tableName+" t "
				+ " set (productiondata,resultstatus)"
				+ "=(select t2.productiondata,2 from "+deviceTableName+" t2 where t2.id=t.wellid) "
				+ " where t."+acqTimeColumn+" between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss')";
		if(StringManagerUtils.isNotNull(calculateSign)){
			updateSql+=" and t.resultstatus in ("+calculateSign+")";
		}
		updateSql+=" and t.wellid in (select well.id from "+deviceTableName+" well where well.orgid in("+orgId+")";
		if(StringManagerUtils.isNotNull(wellName)){
			updateSql+=" and well.wellname='"+wellName+"'";
		}
		updateSql+=")";
		return getBaseDao().executeSqlUpdate(updateSql);
	}
	
	public String getCalculateRequestData(String recordId,String wellName,String acqTime,String calculateType) throws SQLException, IOException, ParseException{
		String requestData="{}";
		if("1".equals(calculateType)){
			requestData=this.getFSDiagramCalculateRequestData(recordId,wellName,acqTime);
		}if("2".equals(calculateType)){
			requestData=this.getRPMCalculateRequestData(recordId,wellName,acqTime);
		}else if("5".equals(calculateType)){
			requestData=this.getElecInverCalculateRequestData(wellName,acqTime);
		}
		return requestData;
	}
	
	public String getFSDiagramCalculateRequestData(String recordId,String wellName,String acqTime) throws SQLException, IOException, ParseException{
		String requestData="{}";
		String sql=""
				+ " select t2.wellname,to_char(t.fesdiagramacqTime,'yyyy-mm-dd hh24:mi:ss'),"
				+ " t.stroke,t.spm,"
				+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
				+ " t.levelcorrectvalue,"
				+ " t.productiondata,"
				+ " t3.id as pumpingmodelid,t3.manufacturer,t3.model,t3.crankrotationdirection,t3.offsetangleofcrank,t3.crankgravityradius,t3.singlecrankweight,t3.singlecrankpinweight,t3.structuralunbalance,"
				+ " t.balanceinfo"
				+ " from tbl_rpcacqdata_hist t"
				+ " left outer join tbl_rpcdevice t2 on t.wellid=t2.id"
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
	
	public String getRPMCalculateRequestData(String recordId,String wellName,String acqTime) throws SQLException, IOException, ParseException{
		String requestData="{}";
		String sql=""
				+ " select t2.wellname,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss'),"
				+ " t.rpm,t.productiondata"
				+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2"
				+ " where t.wellid=t2.id  "
				+ " and t.id="+recordId;
		List<?> list = this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			requestData=calculateDataService.getObjectToRPMCalculateRequestData(obj);
		}
		return requestData;
	}
	
	public String getElecInverCalculateRequestData(String wellName,String acqTime) throws SQLException, IOException, ParseException{
		String requestData="{}";
		StringBuffer result_json = new StringBuffer();
		String sql="select t.wellname,t2.id as diagramid,to_char(t2.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
				+ " t2.spm,t2.rawpower_curve,t2.rawcurrent_curve,t2.rawrpm_curve, "
				+ " t4.manufacturer,t4.model,t4.stroke,decode(t4.crankrotationdirection,'顺时针','Clockwise','Anticlockwise'),"
				+ " t4.offsetangleofcrank,t5.offsetangleofcrankps,t4.crankgravityradius,t4.singlecrankweight,t4.structuralunbalance,"
				+ " t4.gearreducerratio,t4.gearreducerbeltpulleydiameter, t4.balanceposition,t4.balanceweight,"
				+ " t5.surfacesystemefficiency,t5.fs_leftpercent,t5.fs_rightpercent,"
				+ " t5.wattangle,t5.filtertime_watt,t5.filtertime_i,t5.filtertime_rpm,t5.filtertime_fsdiagram,t5.filtertime_fsdiagram_l,t5.filtertime_fsdiagram_r,"
				+ " t4.prtf "
				+ " from tbl_wellinformation t,tbl_rpc_diagram_hist t2,tbl_rpcinformation t4,tbl_rpc_inver_opt t5 "
				+ " where t.id=t2.wellid and t.id=t4.wellid and t.id=t5.wellid "
				+ " and t.wellname='"+wellName+"' and t2.acqTime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')";
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
	
	public String getTotalCalculateResultData(String orgId, String wellName, Page pager,String deviceType,String startDate,String endDate,String calculateType)
			throws Exception {
		String json="";
		if("3".equals(calculateType)){
			json=this.getFESDiagramTotalCalculateResultData(orgId, wellName, pager, deviceType, startDate, endDate,  calculateType);
		}else if("4".equals(calculateType)){
			json=this.getRPMTotalCalculateResultData(orgId, wellName, pager, deviceType, startDate, endDate, calculateType);
		}
		
		return json;
	}
	
	public String getFESDiagramTotalCalculateResultData(String orgId, String wellName, Page pager,String deviceType,String startDate,String endDate,String calculateType)
			throws Exception {
		DataDictionary ddic = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicName="totalCalculateManager";
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		columns = ddic.getTableHeader();
		
		String prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			prodCol=" t.liquidWeightProduction,t.oilWeightProduction,";
		}
		
		sql="select t.id,t.wellId,t.wellName,to_char(t.caldate,'yyyy-mm-dd'),"
			+ "t.resultname,t.resultString,"
			+ prodCol
			+ " t.pumpeff,t.systemefficiency,t.wattDegreeBalance,t.iDegreeBalance,t.todayKWattH"
			+ " from viw_rpcdailycalculationdata t where t.org_id in("+orgId+") "
			+ " and t.caldate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName = '" + wellName.trim() + "' ";
		}
		sql+=" order by t.caldate desc";
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		
		result_json.append("{\"success\":true,\"totalCount\":"+totals+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"wellId\":\""+obj[1]+"\",");
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"calDate\":\""+obj[3]+"\",");
			result_json.append("\"resultName\":\""+obj[4]+"\",");
			result_json.append("\"resultString\":\""+obj[5]+"\",");
			
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				result_json.append("\"liquidWeightProduction\":\""+obj[6]+"\",");
				result_json.append("\"oilWeightProduction\":\""+obj[7]+"\",");
			}else{
				result_json.append("\"liquidVolumetricProduction\":\""+obj[6]+"\",");
				result_json.append("\"oilVolumetricProduction\":\""+obj[7]+"\",");
			}
			
			result_json.append("\"pumpEff\":\""+obj[8]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[9]+"\",");
			result_json.append("\"wattDegreeBalance\":\""+obj[10]+"\",");
			result_json.append("\"iDegreeBalance\":\""+obj[11]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[12]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json = result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		String json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String getRPMTotalCalculateResultData(String orgId, String wellName, Page pager,String deviceType,String startDate,String endDate,String calculateType)
			throws Exception {
		DataDictionary ddic = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicName="RPMTotalCalculateManager";
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		columns = ddic.getTableHeader();
		
		String prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
			prodCol=" t.liquidWeightProduction,t.oilWeightProduction,";
		}
		
		sql="select t.id,t.wellId,t.wellName,to_char(t.caldate,'yyyy-mm-dd'),"
			+ prodCol
			+ " t.pumpeff,t.systemefficiency,t.todayKWattH"
			+ " from viw_pcpdailycalculationdata t where t.org_id in("+orgId+") "
			+ " and t.caldate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName = '" + wellName.trim() + "' ";
		}
		sql+=" order by t.caldate desc";
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		
		result_json.append("{\"success\":true,\"totalCount\":"+totals+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"wellId\":\""+obj[1]+"\",");
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"calDate\":\""+obj[3]+"\",");
			
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")){
				result_json.append("\"liquidWeightProduction\":\""+obj[4]+"\",");
				result_json.append("\"oilWeightProduction\":\""+obj[5]+"\",");
			}else{
				result_json.append("\"liquidVolumetricProduction\":\""+obj[4]+"\",");
				result_json.append("\"oilVolumetricProduction\":\""+obj[5]+"\",");
			}
			
			result_json.append("\"pumpEff\":\""+obj[6]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[7]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[8]+"\"},");
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
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		if(StringManagerUtils.isNotNull(reCalculateDate)){
			String[] calInfoArr=reCalculateDate.split(";");
			for(int i=0;i<calInfoArr.length;i++){
				String recordId=calInfoArr[i].split(",")[0];
				String wellId=calInfoArr[i].split(",")[1];
				String wellName=calInfoArr[i].split(",")[2];
				String calDate=calInfoArr[i].split(",")[3];
				String sql="select t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,t.runstatus,t.runtime,t.runtimeefficiency,t.runrange "
						+ " from tbl_rpcdailycalculationdata t,tbl_rpcdevice t2 "
						+ " where t.wellid=t2.id "
						+ " and t.id="+recordId;
				String fesDiagramSql="select to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh:mi:ss'),t.resultcode,"
						+ "t.stroke,t.spm,t.fmax,t.fmin,t.fullnesscoefficient,"
						+ "t.theoreticalproduction,t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
						+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
						+ "t.productiondata,"
						+ "t.pumpeff,t.pumpeff1,t.pumpeff2,t.pumpeff3,t.pumpeff4,"
						+ "t.wattdegreebalance,t.idegreebalance,t.deltaradius,"
						+ "t.surfacesystemefficiency,t.welldownsystemefficiency,t.systemefficiency,t.energyper100mlift "
						+ " from tbl_rpcacqdata_hist t "
						+ " where t.wellid="+wellId+" "
						+ " and t.fesdiagramacqtime between to_date('"+calDate+"','yyyy-mm-dd') and to_date('"+calDate+"','yyyy-mm-dd')+1 "
						+ " and t.resultstatus=1 "
						+ " order by t.fesdiagramacqtime";
				List<?> list = this.findCallSql(sql);
				if(list.size()>0){
					StringBuffer dataSbf= new StringBuffer();
					Object[] totalObj=(Object[])list.get(0);
					List<?> fesDiagramList = this.findCallSql(fesDiagramSql);
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
//						weightWaterCutList.add(responseData.getProduction().getLiquidVolumetricProduction());
						
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
					}
					dataSbf.append("{\"AKString\":\"\",");
					dataSbf.append("\"WellName\":\""+wellName+"\",");
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
//					dataSbf.append("\"WeightWaterCut\":["+StringUtils.join(weightWaterCutList, ",")+"],");
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
					dataSbf.append("\"DeltaRadius\":["+StringUtils.join(deltaRadiusList, ",")+"]");
					dataSbf.append("}");
					
					TotalAnalysisResponseData totalAnalysisResponseData=CalculateUtils.totalCalculate(dataSbf.toString());
					if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
						getBaseDao().saveFESDiagramReTotalData(recordId,totalAnalysisResponseData);
					}
				}
			}
		}
		return json;
	}
	
	public String reTotalCalculateRPMData(String recaCalculateDate)throws Exception {
		String json="";
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		if(StringManagerUtils.isNotNull(recaCalculateDate)){
			String[] calInfoArr=recaCalculateDate.split(";");
			for(int i=0;i<calInfoArr.length;i++){
				String recordId=calInfoArr[i].split(",")[0];
				String wellId=calInfoArr[i].split(",")[1];
				String wellName=calInfoArr[i].split(",")[2];
				String calDate=calInfoArr[i].split(",")[3];
				String sql="select t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,t.runstatus,t.runtime,t.runtimeefficiency,t.runrange "
						+ " from tbl_pcpdailycalculationdata t,pcp_rpcdevice t2 "
						+ " where t.wellid=t2.id "
						+ " and t.id="+recordId;
				String rpmSql="select "
						+ "to_char(t.acqtime,'yyyy-mm-dd hh:mi:ss'),t.rpm,"
						+ "t.theoreticalproduction,t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
						+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
						+ "t.productiondata,"
						+ "t.pumpeff,t.pumpeff1,t.pumpeff2,"
						+ "t.systemefficiency,t.energyper100mlift "
						+ " from tbl_pcpacqdata_hist t "
						+ " where t.wellid="+wellId+" "
						+ " and t.acqtime between to_date('"+calDate+"','yyyy-mm-dd') and to_date('"+calDate+"','yyyy-mm-dd')+1 "
						+ " and t.resultstatus=1 "
						+ " order by t.acqtime";
				List<?> list = this.findCallSql(sql);
				if(list.size()>0){
					StringBuffer dataSbf= new StringBuffer();
					Object[] totalObj=(Object[])list.get(0);
					List<?> rpmResultList = this.findCallSql(rpmSql);
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
//						weightWaterCutList.add(responseData.getProduction().getLiquidVolumetricProduction());
						
						pumpEffList.add(StringManagerUtils.stringToFloat(obj[10]+""));
						pumpEff1List.add(StringManagerUtils.stringToFloat(obj[11]+""));
						pumpEff2List.add(StringManagerUtils.stringToFloat(obj[12]+""));
						
						systemEfficiencyList.add(StringManagerUtils.stringToFloat(obj[13]+""));
						energyPer100mLiftList.add(StringManagerUtils.stringToFloat(obj[14]+""));
					}
					dataSbf.append("{\"AKString\":\"\",");
					dataSbf.append("\"WellName\":\""+wellName+"\",");
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
					dataSbf.append("\"TheoreticalProduction\":["+StringUtils.join(theoreticalProductionList, ",")+"],");
					dataSbf.append("\"LiquidVolumetricProduction\":["+StringUtils.join(liquidVolumetricProductionList, ",")+"],");
					dataSbf.append("\"OilVolumetricProduction\":["+StringUtils.join(oilVolumetricProductionList, ",")+"],");
					dataSbf.append("\"WaterVolumetricProduction\":["+StringUtils.join(waterVolumetricProductionList, ",")+"],");
					dataSbf.append("\"VolumeWaterCut\":["+StringUtils.join(volumeWaterCutList, ",")+"],");
					dataSbf.append("\"LiquidWeightProduction\":["+StringUtils.join(liquidWeightProductionList, ",")+"],");
					dataSbf.append("\"OilWeightProduction\":["+StringUtils.join(oilWeightProductionList, ",")+"],");
					dataSbf.append("\"WaterWeightProduction\":["+StringUtils.join(waterWeightProductionList, ",")+"],");
//					dataSbf.append("\"WeightWaterCut\":["+StringUtils.join(weightWaterCutList, ",")+"],");
					dataSbf.append("\"SystemEfficiency\":["+StringUtils.join(systemEfficiencyList, ",")+"],");
					dataSbf.append("\"EnergyPer100mLift\":["+StringUtils.join(energyPer100mLiftList, ",")+"],");
					dataSbf.append("\"PumpEff\":["+StringUtils.join(pumpEffList, ",")+"],");
					dataSbf.append("\"PumpEff1\":["+StringUtils.join(pumpEff1List, ",")+"],");
					dataSbf.append("\"PumpEff2\":["+StringUtils.join(pumpEff2List, ",")+"]");
					dataSbf.append("}");
					
					TotalAnalysisResponseData totalAnalysisResponseData=CalculateUtils.totalCalculate(dataSbf.toString());
					if(totalAnalysisResponseData!=null&&totalAnalysisResponseData.getResultStatus()==1){
						getBaseDao().saveRPMReTotalData(recordId,totalAnalysisResponseData);
					}
				}
			}
		}
		return json;
	}
	
	
	public String exportTotalCalculateRequestData(String deviceType,String recordId,String wellId,String wellName,String calDate)throws Exception {
		String json="";
		if("0".equals(deviceType)){
			json=this.exportFESDiagramTotalCalculateRequestData(recordId,wellId,wellName,calDate);
		}else if("1".equals(deviceType)){
			json=this.exportRPMTotalCalculateRequestData(recordId,wellId,wellName,calDate);
		}
		
		return json;
	}
	
	public String exportFESDiagramTotalCalculateRequestData(String recordId,String wellId,String wellName,String calDate)throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer dataSbf= new StringBuffer();
		String sql="select t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,t.runstatus,t.runtime,t.runtimeefficiency,t.runrange "
				+ " from tbl_rpcdailycalculationdata t,tbl_rpcdevice t2 "
				+ " where t.wellid=t2.id "
				+ " and t.id="+recordId;
		String fesDiagramSql="select to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh:mi:ss'),t.resultcode,"
				+ "t.stroke,t.spm,t.fmax,t.fmin,t.fullnesscoefficient,"
				+ "t.theoreticalproduction,t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
				+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
				+ "t.productiondata,"
				+ "t.pumpeff,t.pumpeff1,t.pumpeff2,t.pumpeff3,t.pumpeff4,"
				+ "t.wattdegreebalance,t.idegreebalance,t.deltaradius,"
				+ "t.surfacesystemefficiency,t.welldownsystemefficiency,t.systemefficiency,t.energyper100mlift "
				+ " from tbl_rpcacqdata_hist t "
				+ " where t.wellid="+wellId+" "
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
//				weightWaterCutList.add(responseData.getProduction().getLiquidVolumetricProduction());
				
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
			}
			dataSbf.append("{\"AKString\":\"\",");
			dataSbf.append("\"WellName\":\""+wellName+"\",");
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
//			dataSbf.append("\"WeightWaterCut\":["+StringUtils.join(weightWaterCutList, ",")+"],");
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
			dataSbf.append("\"DeltaRadius\":["+StringUtils.join(deltaRadiusList, ",")+"]");
			dataSbf.append("}");
		}
	
		return dataSbf.toString();
	}
	
	public String exportRPMTotalCalculateRequestData(String recordId,String wellId,String wellName,String calDate)throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer dataSbf= new StringBuffer();
		String sql="select t.commstatus,t.commtime,t.commtimeefficiency,t.commrange,t.runstatus,t.runtime,t.runtimeefficiency,t.runrange "
				+ " from tbl_pcpdailycalculationdata t,pcp_rpcdevice t2 "
				+ " where t.wellid=t2.id "
				+ " and t.id="+recordId;
		String fesDiagramSql="select to_char(t.acqtime,'yyyy-mm-dd hh:mi:ss'),t.rpm,"
				+ "t.theoreticalproduction,t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
				+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
				+ "t.productiondata,"
				+ "t.pumpeff,t.pumpeff1,t.pumpeff2,"
				+ "t.systemefficiency,t.energyper100mlift "
				+ " from tbl_pcpacqdata_hist t "
				+ " where t.wellid="+wellId+" "
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
//				weightWaterCutList.add(responseData.getProduction().getLiquidVolumetricProduction());
				
				pumpEffList.add(StringManagerUtils.stringToFloat(obj[10]+""));
				pumpEff1List.add(StringManagerUtils.stringToFloat(obj[11]+""));
				pumpEff2List.add(StringManagerUtils.stringToFloat(obj[12]+""));
				
				systemEfficiencyList.add(StringManagerUtils.stringToFloat(obj[13]+""));
				energyPer100mLiftList.add(StringManagerUtils.stringToFloat(obj[14]+""));
			}
			dataSbf.append("{\"AKString\":\"\",");
			dataSbf.append("\"WellName\":\""+wellName+"\",");
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
			dataSbf.append("\"TheoreticalProduction\":["+StringUtils.join(theoreticalProductionList, ",")+"],");
			dataSbf.append("\"LiquidVolumetricProduction\":["+StringUtils.join(liquidVolumetricProductionList, ",")+"],");
			dataSbf.append("\"OilVolumetricProduction\":["+StringUtils.join(oilVolumetricProductionList, ",")+"],");
			dataSbf.append("\"WaterVolumetricProduction\":["+StringUtils.join(waterVolumetricProductionList, ",")+"],");
			dataSbf.append("\"VolumeWaterCut\":["+StringUtils.join(volumeWaterCutList, ",")+"],");
			dataSbf.append("\"LiquidWeightProduction\":["+StringUtils.join(liquidWeightProductionList, ",")+"],");
			dataSbf.append("\"OilWeightProduction\":["+StringUtils.join(oilWeightProductionList, ",")+"],");
			dataSbf.append("\"WaterWeightProduction\":["+StringUtils.join(waterWeightProductionList, ",")+"],");
//			dataSbf.append("\"WeightWaterCut\":["+StringUtils.join(weightWaterCutList, ",")+"],");
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
