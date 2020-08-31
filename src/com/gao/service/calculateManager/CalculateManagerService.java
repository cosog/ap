package com.gao.service.calculateManager;

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

import com.gao.dao.BaseDao;
import com.gao.model.AlarmShowStyle;
import com.gao.model.DiagnosisAnalysisStatistics;
import com.gao.model.calculate.InversioneFSdiagramResponseData;
import com.gao.model.data.DataDictionary;
import com.gao.model.gridmodel.CalculateManagerHandsontableChangedData;
import com.gao.model.gridmodel.ElecInverCalculateManagerHandsontableChangedData;
import com.gao.model.gridmodel.WellHandsontableChangedData;
import com.gao.service.base.BaseService;
import com.gao.service.base.CommonDataService;
import com.gao.service.data.DataitemsInfoService;
import com.gao.service.datainterface.CalculateDataService;
import com.gao.tast.EquipmentDriverServerTast;
import com.gao.utils.Config;
import com.gao.utils.ConfigFile;
import com.gao.utils.DataModelMap;
import com.gao.utils.Page;
import com.gao.utils.StringManagerUtils;
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
	public String getCalculateResultData(String orgId, String wellName, Page pager,String wellType,String startDate,String endDate,String calculateSign,String calculateType)
			throws Exception {
		String json="";
		if("1".equals(calculateType)||"2".equals(calculateType)){
			json=this.getDiagnoseAndProdCalculateResultData(orgId, wellName, pager, wellType, startDate, endDate, calculateSign, calculateType);
		}else if("5".equals(calculateType)){//电参反演地面功图
			json=this.getElecInverCalculateResultData(orgId, wellName, pager, wellType, startDate, endDate, calculateSign, calculateType);
		}
		
		return json;
	}
	
	public String getDiagnoseAndProdCalculateResultData(String orgId, String wellName, Page pager,String wellType,String startDate,String endDate,String calculateSign,String calculateType)
			throws Exception {
		DataDictionary ddic = null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicName="calculateManager";
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		if("1".equals(calculateType)){
			ddicName="calculateManager";
		}else if("2".equals(calculateType)){
			ddicName="screwPumpCalculateManager";
		}
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		columns = ddic.getTableHeader();
		
		String prodCol=" t.liquidWeightProduction,t.oilWeightProduction,";
		if(configFile.getOthers().getProductionUnit()!=0){
			prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,";
		}
		
		sql="select t.id,t.wellName,to_char(t.acquisitionTime,'yyyy-mm-dd hh24:mi:ss'),t.workingConditionName,"
			+ prodCol
			+ "t.crudeoilDensity,t.waterDensity,t.naturalGasRelativeDensity,"
			+ "t.saturationPressure,t.reservoirDepth,t.reservoirTemperature,"
			+ "t.tubingPressure,t.casingPressure,t.wellHeadFluidTemperature,t.waterCut,t.productionGasOilRatio,t.producingFluidLevel,"
			+ "t.pumpSettingDepth,t.pumpGrade,t.pumpboreDiameter,t.plungerLength,"
			+ "t.tubingStringInsideDiameter,t.casingStringInsideDiameter,"
			+ "t.anchoringStateName,t.netGrossRatio,t.resultStatus,"
			+ "t.rodstring"
			+ " from viw_rpc_calculatemain t where t.orgid in("+orgId+") "
			+ " and t.acquisitionTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		
		
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
		sql+=" order by t.acquisitionTime desc, t.wellName";
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		
		result_json.append("{\"success\":true,\"totalCount\":"+totals+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String rodString=obj[27]+"";
			String[] rodStringArr={};
			if(StringManagerUtils.isNotNull(rodString)){
				rodStringArr=rodString.split(";");
			}
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"acquisitionTime\":\""+obj[2]+"\",");
			result_json.append("\"workingConditionName\":\""+obj[3]+"\",");
			if(configFile.getOthers().getProductionUnit()==0){
				result_json.append("\"liquidWeightProduction\":\""+obj[4]+"\",");
				result_json.append("\"oilWeightProduction\":\""+obj[5]+"\",");
			}else{
				result_json.append("\"liquidVolumetricProduction\":\""+obj[4]+"\",");
				result_json.append("\"oilVolumetricProduction\":\""+obj[5]+"\",");
			}
			result_json.append("\"crudeoilDensity\":\""+obj[6]+"\",");
			result_json.append("\"waterDensity\":\""+obj[7]+"\",");
			result_json.append("\"naturalGasRelativeDensity\":\""+obj[8]+"\",");
			result_json.append("\"saturationPressure\":\""+obj[9]+"\",");
			result_json.append("\"reservoirDepth\":\""+obj[10]+"\",");
			result_json.append("\"reservoirTemperature\":\""+obj[11]+"\",");
			result_json.append("\"tubingPressure\":\""+obj[12]+"\",");
			result_json.append("\"casingPressure\":\""+obj[13]+"\",");
			result_json.append("\"wellHeadFluidTemperature\":\""+obj[14]+"\",");
			result_json.append("\"waterCut\":\""+obj[15]+"\",");
			result_json.append("\"productionGasOilRatio\":\""+obj[16]+"\",");
			result_json.append("\"producingFluidLevel\":\""+obj[17]+"\",");
			result_json.append("\"pumpSettingDepth\":\""+obj[18]+"\",");
			result_json.append("\"pumpGrade\":\""+obj[19]+"\",");
			result_json.append("\"pumpboreDiameter\":\""+obj[20]+"\",");
			result_json.append("\"plungerLength\":\""+obj[21]+"\",");
			result_json.append("\"tubingStringInsideDiameter\":\""+obj[22]+"\",");
			result_json.append("\"casingStringInsideDiameter\":\""+obj[23]+"\",");
			
			for(int j=0;j<rodStringArr.length;j++){
				String[] everyRod=rodStringArr[j].split(",");
				int arrLength=everyRod.length;
				result_json.append("\"rodGrade"+(j+1)+"\":\""+(arrLength>=1?everyRod[0]:"")+"\",");
				result_json.append("\"rodOutsideDiameter"+(j+1)+"\":\""+(arrLength>=2?everyRod[1]:"")+"\",");
				result_json.append("\"rodInsideDiameter"+(j+1)+"\":\""+(arrLength>=3?everyRod[2]:"")+"\",");
				result_json.append("\"rodLength"+(j+1)+"\":\""+(arrLength>=4?everyRod[3]:"")+"\",");
			}
			
			result_json.append("\"anchoringStateName\":\""+obj[24]+"\",");
			result_json.append("\"netGrossRatio\":\""+obj[25]+"\",");
			result_json.append("\"resultStatus\":\""+obj[26]+"\"},");
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
		
		
		
		sql="select t.id,t.wellName,to_char(t.acquisitionTime,'yyyy-mm-dd hh24:mi:ss'),t.resultStatus,"
			+ "t.manufacturer,t.model,t.stroke,"
			+ "t.crankRotationDirection,t.offsetAngleOfCrank,t.crankGravityRadius,"
			+ "t.singleCrankWeight,t.structuralUnbalance,t.balancePosition,t.balanceWeight,"
			
			+ "t.offsetAngleOfCrankPS,t.surfaceSystemEfficiency,t.FS_LeftPercent,t.FS_RightPercent,wattAngle,"
			+ "t.filterTime_Watt,t.filterTime_I,filterTime_RPM,"
			+ "t.filterTime_FSDiagram,t.filterTime_FSDiagram_L,t.filterTime_FSDiagram_R"
			+ " from viw_rpc_calculatemain_elec t where t.orgid in("+orgId+") "
			+ " and t.acquisitionTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		
		
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
		sql+=" order by t.acquisitionTime desc, t.wellName";
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalSql);
		
		
		String columns = "[{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"wellName\" ,children:[] },"
				+ "{ \"header\":\"采集时间\",\"dataIndex\":\"acquisitionTime\" ,children:[] },"
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
			result_json.append("\"acquisitionTime\":\""+obj[2]+"\",");
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
	
	public void saveRecalculateData(CalculateManagerHandsontableChangedData calculateManagerHandsontableChangedData) throws Exception {
		getBaseDao().saveRecalculateData(calculateManagerHandsontableChangedData);
	}
	
	public void saveElecInverPumpingUnitData(ElecInverCalculateManagerHandsontableChangedData elecInverCalculateManagerHandsontableChangedData) throws Exception {
		getBaseDao().saveElecInverPumpingUnitData(elecInverCalculateManagerHandsontableChangedData);
	}
	public void saveElecInverOptimizeHandsontableData(ElecInverCalculateManagerHandsontableChangedData elecInverCalculateManagerHandsontableChangedData,String orgid) throws Exception {
		getBaseDao().saveElecInverOptimizeHandsontableData(elecInverCalculateManagerHandsontableChangedData,orgid);
	}
	public boolean reInverDiagram(String recordId,InversioneFSdiagramResponseData inversioneFSdiagramResponseData) throws SQLException, ParseException{
		String SStr="",FStr="",PStr="",AStr="",RPMStr="";
		String F360Str="",S360Str="",A360Str="";
		if(inversioneFSdiagramResponseData.getResultStatus()==1){
			SStr=StringUtils.join(inversioneFSdiagramResponseData.getS(), ",");
			FStr=StringUtils.join(inversioneFSdiagramResponseData.getF(), ",");
		}
		PStr=StringUtils.join(inversioneFSdiagramResponseData.getWatt(), ",");
		AStr=StringUtils.join(inversioneFSdiagramResponseData.getI(), ",");
		RPMStr=StringUtils.join(inversioneFSdiagramResponseData.getRPM(), ",");
		
		
		if(inversioneFSdiagramResponseData.getF360()!=null){
			F360Str=StringUtils.join(inversioneFSdiagramResponseData.getF360(), ",");
		}
		if(inversioneFSdiagramResponseData.getS360()!=null){
			S360Str=StringUtils.join(inversioneFSdiagramResponseData.getS360(), ",");
		}
		if(inversioneFSdiagramResponseData.getA360()!=null){
			A360Str=StringUtils.join(inversioneFSdiagramResponseData.getA360(), ",");
		}
		
		return this.getBaseDao().reInverDiagram(recordId,inversioneFSdiagramResponseData.getAcquisitionTime(),
				inversioneFSdiagramResponseData.getCNT(),inversioneFSdiagramResponseData.getStroke(),inversioneFSdiagramResponseData.getSPM(),
				inversioneFSdiagramResponseData.getMaxF(),inversioneFSdiagramResponseData.getMinF(),
				SStr,FStr,
				S360Str,A360Str,F360Str,
				AStr,PStr,RPMStr,
				inversioneFSdiagramResponseData.getUpstrokeIMax(),inversioneFSdiagramResponseData.getDownstrokeIMax(),
				inversioneFSdiagramResponseData.getUpstrokeWattMax(),inversioneFSdiagramResponseData.getDownstrokeWattMax(),
				inversioneFSdiagramResponseData.getIDegreeBalance(),inversioneFSdiagramResponseData.getWattDegreeBalance(),
				inversioneFSdiagramResponseData.getResultStatus());
	}
	public String getCalculateStatusList(String orgId, String wellName, String wellType,String startDate,String endDate)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql="";
		String tableName="";
		if("200".equals(wellType)){
			tableName="tbl_rpc_diagram_hist";
		}
		sql="select distinct(decode(t.resultstatus,2,0,t.resultstatus)),t2.itemname "
				+ " from "+tableName+" t,tbl_code t2,tbl_wellinformation t3 "
				+ " where t.wellid=t3.id and decode(t.resultstatus,2,0,t.resultstatus)=t2.itemvalue and t2.itemcode='JSBZ'"
				+ " and t3.orgid in("+orgId+") "
				+ " and t.acquisitionTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		
		if(StringManagerUtils.isNotNull(wellType)){
			sql+=" and t3.liftingtype>="+wellType+" and t3.liftingtype<("+wellType+"+100) ";
		}
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
	
	public int recalculateByProductionData(String orgId, String wellName, String wellType,String startDate,String endDate,String calculateSign)throws Exception {
		String updateSql="update tbl_rpc_diagram_hist t "
				+ " set (productiondataid,resultstatus)=(select t2.id,2 from tbl_rpc_productiondata_hist t2,tbl_rpc_productiondata_latest t3 where t2.wellid=t3.wellid and t2.acquisitiontime=t3.acquisitiontime and t2.wellid=t.wellid) "
				+ " where t.acquisitiontime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		if(StringManagerUtils.isNotNull(calculateSign)){
			updateSql+=" and t.resultstatus in ("+calculateSign+")";
		}
		updateSql+=" and t.wellid in (select well.id from tbl_wellinformation well where well.orgid in("+orgId+")";
		if(StringManagerUtils.isNotNull(wellName)){
			updateSql+=" and well.wellname='"+wellName+"'";
		}
		updateSql+=")";
		return getBaseDao().executeSqlUpdate(updateSql);
	}
	
	public String getCalculateRequestData(String wellName,String acquisitionTime,String calculateType) throws SQLException, IOException, ParseException{
		String requestData="{}";
		if("1".equals(calculateType)){
			requestData=this.getFSDiagramCalculateRequestData(wellName,acquisitionTime);
		}else if("5".equals(calculateType)){
			requestData=this.getElecInverCalculateRequestData(wellName,acquisitionTime);
		}
		return requestData;
	}
	
	public String getFSDiagramCalculateRequestData(String wellName,String acquisitionTime) throws SQLException, IOException, ParseException{
		String requestData="{}";
		String sql="select t3.wellname,t3.liftingtype,to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss'),"
				+ " t2.crudeOilDensity,t2.waterDensity,t2.naturalGasRelativeDensity,t2.saturationPressure,t2.reservoirdepth,t2.reservoirtemperature,"
				+ " t2.rodstring,"
				+ " t2.tubingstringinsidediameter,"
				+ " t2.pumptype,t2.pumpgrade,t2.plungerlength,t2.pumpborediameter,"
				+ " t2.casingstringinsidediameter,"
				+ " t2.watercut,t2.productiongasoilratio,t2.tubingpressure,t2.casingpressure,t2.wellheadfluidtemperature,"
				+ " t2.producingfluidlevel,t2.pumpsettingdepth,"
				+ " decode(t.resultstatus,2,t.levelcorrectvalue,t3.levelcorrectvalue) as levelcorrectvalue,"
				+ " t2.netgrossratio,"
				+ " t.stroke,t.spm,"
				+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
				+ " decode(t.datasource,1,1,0) as datasource,"
				+ " 0 as manualInterventionCode,"
				+ " t.resultstatus,t.id"
				+ " from tbl_rpc_diagram_hist t,tbl_rpc_productiondata_hist t2,tbl_wellinformation t3"
				+ " where t.wellid=t3.id and t.productiondataid=t2.id  "
				+ " and t3.wellname='"+wellName+"'"
				+ " and t.acquisitiontime=to_date('"+acquisitionTime+"','yyyy-mm-dd hh24:mi:ss')";
		List<?> list = this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			requestData=calculateDataService.getObjectToRPCCalculateRequestData(obj);
		}
		return requestData;
	}
	
	public String getElecInverCalculateRequestData(String wellName,String acquisitionTime) throws SQLException, IOException, ParseException{
		String requestData="{}";
		StringBuffer result_json = new StringBuffer();
		String sql="select t.wellname,t2.id as diagramid,to_char(t2.acquisitionTime,'yyyy-mm-dd hh24:mi:ss') as acquisitionTime,"
				+ " t2.spm,t2.rawpower_curve,t2.rawcurrent_curve,t2.rawrpm_curve, "
				+ " t4.manufacturer,t4.model,t4.stroke,decode(t4.crankrotationdirection,'顺时针','Clockwise','Anticlockwise'),"
				+ " t4.offsetangleofcrank,t5.offsetangleofcrankps,t4.crankgravityradius,t4.singlecrankweight,t4.structuralunbalance,"
				+ " t4.gearreducerratio,t4.gearreducerbeltpulleydiameter, t4.balanceposition,t4.balanceweight,"
				+ " t5.surfacesystemefficiency,t5.fs_leftpercent,t5.fs_rightpercent,"
				+ " t5.wattangle,t5.filtertime_watt,t5.filtertime_i,t5.filtertime_rpm,t5.filtertime_fsdiagram,t5.filtertime_fsdiagram_l,t5.filtertime_fsdiagram_r,"
				+ " t4.prtf "
				+ " from tbl_wellinformation t,tbl_rpc_diagram_hist t2,tbl_rpcinformation t4,tbl_rpc_inver_opt t5 "
				+ " where t.id=t2.wellid and t.id=t4.wellid and t.id=t5.wellid "
				+ " and t.wellname='"+wellName+"' and t2.acquisitionTime=to_date('"+acquisitionTime+"','yyyy-mm-dd hh24:mi:ss')";
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
			result_json.append("\"AcquisitionTime\":\""+obj[2]+"\",");
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
	
	public BaseDao getDao() {
		return dao;
	}

	@Resource
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
}
