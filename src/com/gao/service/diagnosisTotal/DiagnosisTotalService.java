package com.gao.service.diagnosisTotal;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gao.dao.BaseDao;
import com.gao.model.DiagnosisAnalysisStatistics;
import com.gao.model.data.DataDictionary;
import com.gao.service.base.BaseService;
import com.gao.service.base.CommonDataService;
import com.gao.service.data.DataitemsInfoService;
import com.gao.utils.Config;
import com.gao.utils.ConfigFile;
import com.gao.utils.Page;
import com.gao.utils.PageHandler;
import com.gao.utils.StringManagerUtils;
import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.hibernate.engine.jdbc.SerializableClobProxy;

/**
 * <p>诊断算产汇总 --service层</p>
 * 
 * @author zhao 2018-05-29
 * 
 */
@Component("diagnosisTotalService")
public class DiagnosisTotalService<T> extends BaseService<T> {

	private BaseDao dao;
	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	
	public String GetDiagnosisTotalData(String orgId, String wellName,String totalDate, Page pager,String wellType,String statValue,
			String startDate,String endDate,String type)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String columns= "";
		DataDictionary ddic = null;
		String ddicName="";
		String tableName="viw_rpc_total_day";
		String typeColumnName="workingConditionName";
		ConfigFile configFile=Config.getInstance().configFile;
		if("1".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyETValue";
			}else{//默认为抽油机
				ddicName="dailyFSDiagram";
			}
			typeColumnName="workingConditionName";
		}else if("2".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyProdDist";
			}else{//默认为抽油机
				ddicName="dailyProdDist";
			}
			typeColumnName="liquidWeightProductionlevel";
			if(configFile.getOthers().getProductionUnit()!=0){
				typeColumnName="liquidVolumeProductionlevel";
			}
		}else if("3".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyETValue";
			}else{//默认为抽油机
				ddicName="dailyPowerBalance";
			}
			typeColumnName="wattdegreebalanceLevel";
		}else if("4".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyETValue";
			}else{//默认为抽油机
				ddicName="dailyCurrentBalance";
			}
			typeColumnName="idegreebalanceLevel";
		}else if("5".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyRunStatus";
			}else{//默认为抽油机
				ddicName="dailyRunStatus";
			}
			typeColumnName="runStatusName";
		}else if("6".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyTimeDist";
			}else{//默认为抽油机
				ddicName="dailyTimeDist";
			}
			typeColumnName="runtimeEfficiencyLevel";
		}else if("7".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailySystemEff";
			}else{//默认为抽油机
				ddicName="dailySystemEff";
			}
			typeColumnName="systemEfficiencyLevel";
		}else if("8".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailySystemEff";
			}else{//默认为抽油机
				ddicName="dailySurfaceEff";
			}
			typeColumnName="surfaceSystemEfficiencyLevel";
		}else if("9".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailySystemEff";
			}else{//默认为抽油机
				ddicName="dailyDownholeEff";
			}
			typeColumnName="wellDownSystemEfficiencyLevel";
		}else if("10".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyPowerDist";
			}else{//默认为抽油机
				ddicName="dailyPowertDist";
			}
			typeColumnName="todayKWattHLevel";
		}else if("11".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyCommStatus";
			}else{//默认为抽油机
				ddicName="dailyCommStatus";
			}
			typeColumnName="commStatusName";
		}else if("12".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyCommDist";
			}else{//默认为抽油机
				ddicName="dailyCommDist";
			}
			typeColumnName="commtimeefficiencyLevel";
		}else if("13".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyETValue";
			}else{//默认为抽油机
				ddicName="dailyETValue";
			}
			typeColumnName="workingConditionName_E";
		}
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		columns = ddic.getTableHeader();
		String sql=ddic.getSql()+",workingConditionString,workingConditionAlarmLevel,"
				+ " commStatus,runStatus,commAlarmLevel,runAlarmLevel ";
		if("200".equals(wellType)){
			sql+= " ,workingConditionString_E,workingConditionAlarmLevel_E,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel ";
			tableName="viw_rpc_total_day";
		}else{
			tableName="viw_pcp_total_day";
		}
				
		sql+= " from "+tableName+" t where t.org_id in ("+orgId+") ";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and to_date(to_char(t.calculateDate,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') "
				+ " and  t.wellName='"+wellName+"' "
				+ " order by t.calculateDate desc";
		}else{
			sql+=" and t.calculateDate=to_date('"+totalDate+"','yyyy-mm-dd') ";
			if(StringManagerUtils.isNotNull(statValue)){
				sql+=" and "+typeColumnName+"='"+statValue+"'";
			}
			sql+=" order by t.sortnum, t.wellName";
		}
		int maxvalue=pager.getLimit()+pager.getStart();
		String finalSql="select * from   ( select a.*,rownum as rn from ("+sql+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		String getResult = this.findCustomPageBySqlEntity(sql,finalSql, columns, 20 + "", pager);
		return getResult.replaceAll("null", "").replaceAll("//", "");
	}
	
	public String exportDiagnosisTotalDataExcel(String orgId, String wellName,String totalDate, Page pager,String wellType,String statValue,
			String startDate,String endDate,String type)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String columns= "";
		DataDictionary ddic = null;
		String ddicName="";
		String tableName="viw_rpc_total_day";
		String typeColumnName="workingConditionName";
		ConfigFile configFile=Config.getInstance().configFile;
		if("1".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyETValue";
			}else{//默认为抽油机
				ddicName="dailyFSDiagram";
			}
			typeColumnName="workingConditionName";
		}else if("2".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyProdDist";
			}else{//默认为抽油机
				ddicName="dailyProdDist";
			}
			typeColumnName="liquidWeightProductionlevel";
			if(configFile.getOthers().getProductionUnit()!=0){
				typeColumnName="liquidVolumeProductionlevel";
			}
		}else if("3".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyETValue";
			}else{//默认为抽油机
				ddicName="dailyPowerBalance";
			}
			typeColumnName="wattdegreebalanceLevel";
		}else if("4".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyETValue";
			}else{//默认为抽油机
				ddicName="dailyCurrentBalance";
			}
			typeColumnName="idegreebalanceLevel";
		}else if("5".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyRunStatus";
			}else{//默认为抽油机
				ddicName="dailyRunStatus";
			}
			typeColumnName="runStatusName";
		}else if("6".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyTimeDist";
			}else{//默认为抽油机
				ddicName="dailyTimeDist";
			}
			typeColumnName="runtimeEfficiencyLevel";
		}else if("7".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailySystemEff";
			}else{//默认为抽油机
				ddicName="dailySystemEff";
			}
			typeColumnName="systemEfficiencyLevel";
		}else if("8".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailySystemEff";
			}else{//默认为抽油机
				ddicName="dailySurfaceEff";
			}
			typeColumnName="surfaceSystemEfficiencyLevel";
		}else if("9".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailySystemEff";
			}else{//默认为抽油机
				ddicName="dailyDownholeEff";
			}
			typeColumnName="wellDownSystemEfficiencyLevel";
		}else if("10".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyPowerDist";
			}else{//默认为抽油机
				ddicName="dailyPowertDist";
			}
			typeColumnName="todayKWattHLevel";
		}else if("11".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyCommStatus";
			}else{//默认为抽油机
				ddicName="dailyCommStatus";
			}
			typeColumnName="commStatusName";
		}else if("12".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyCommDist";
			}else{//默认为抽油机
				ddicName="dailyCommDist";
			}
			typeColumnName="commtimeefficiencyLevel";
		}else if("13".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpDailyETValue";
			}else{//默认为抽油机
				ddicName="dailyETValue";
			}
			typeColumnName="workingConditionName_E";
		}
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		columns = ddic.getTableHeader();
		String sql=ddic.getSql()+",workingConditionString,"
				+ " workingConditionAlarmLevel,"
				+ " commStatus,runStatus,commAlarmLevel,runAlarmLevel ";
		if("200".equals(wellType)){
			sql+= " ,workingConditionString_E,workingConditionAlarmLevel_E,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel ";
			tableName="viw_rpc_total_day";
		}else{
			tableName="viw_pcp_total_day";
		}
				
		sql+= " from "+tableName+" t where t.org_id in ("+orgId+") ";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and to_date(to_char(t.calculateDate,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') "
				+ " and  t.wellName='"+wellName+"' "
				+ " order by t.calculateDate desc";
		}else{
			sql+=" and t.calculateDate=to_date('"+totalDate+"','yyyy-mm-dd') ";
			if(StringManagerUtils.isNotNull(statValue)){
				sql+=" and "+typeColumnName+"='"+statValue+"'";
			}
			sql+=" order by t.sortnum, t.wellName";
		}
		String getResult = this.findExportDataBySqlEntity(sql,sql, columns, 20 + "", pager);
		return getResult.replaceAll("null", "").replaceAll("//", "");
	}
	
	public String GetDiagnosisTotalStatistics(String orgId,String type,String wellType,String totalDate){
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String sql="";
		String tableName="viw_rpc_total_day";
		String statType="workingConditionName";
		if("1".equalsIgnoreCase(type)){
			statType="workingConditionName";
		}else if("2".equalsIgnoreCase(type)){
			statType="liquidWeightProductionlevel";
			if(configFile.getOthers().getProductionUnit()!=0){
				statType="liquidVolumeProductionlevel";
			}
		}else if("3".equalsIgnoreCase(type)){
			statType="wattdegreebalanceLevel";
		}else if("4".equalsIgnoreCase(type)){
			statType="idegreebalanceLevel";
		}else if("5".equalsIgnoreCase(type)){
			statType="runStatusName";
		}else if("6".equalsIgnoreCase(type)){
			statType="runtimeEfficiencyLevel";
		}else if("7".equalsIgnoreCase(type)){
			statType="systemEfficiencyLevel";
		}else if("8".equalsIgnoreCase(type)){
			statType="surfaceSystemEfficiencyLevel";
		}else if("9".equalsIgnoreCase(type)){
			statType="wellDownSystemEfficiencyLevel";
		}else if("10".equalsIgnoreCase(type)){
			statType="todayKWattHLevel";
		}else if("11".equalsIgnoreCase(type)){
			statType="commStatusName";
		}else if("12".equalsIgnoreCase(type)){
			statType="commtimeefficiencyLevel";
		}else if("13".equalsIgnoreCase(type)){
			statType="workingConditionName_E";
		}
		if("200".equals(wellType)){
			tableName="viw_rpc_total_day";
		}else{
			tableName="viw_pcp_total_day";
		}
		sql="select "+statType+", count(1) from "+tableName+" t where  org_id in ("+orgId+") and calculateDate=to_date('"+totalDate+"','yyyy-mm-dd') ";
		sql+=" group by rollup("+statType+")";
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,\"totalDate\":\""+totalDate+"\",");
		result_json.append("\"List\":[");
		int totalCount=0;
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			if(StringManagerUtils.isNotNull(obj[0]+"")){
				
				result_json.append("{\"item\":\""+obj[0]+"\",");
				result_json.append("\"count\":"+obj[1]+"},");
				totalCount+=StringManagerUtils.stringToInteger(obj[1]+"");
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append(",\"totalCount\":"+totalCount);
		result_json.append("}");
		return result_json.toString();
	}
	
	@SuppressWarnings("deprecation")
	public String getFSDiagramOverlayData(Page pager,String orgId,String wellName,String calculateDate) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		DataDictionary ddic = null;
		String startDate=calculateDate;
		String startDateSql="select to_char(max(t.acqTime),'yyyy-mm-dd') "
				+ " from tbl_rpc_diagram_hist t,tbl_wellinformation well "
				+ " where t.wellid=well.id and  t.acqTime<to_date('"+calculateDate+"','yyyy-mm-dd')+1 "
				+ " and well.wellname='"+wellName+"'";
		List<?> startDateList = this.findCallSql(startDateSql);
		if(startDateList.size()>0&&startDateList.get(0)!=null){
			startDate=startDateList.get(0)+"";
		}
		String prodCol="liquidWeightProduction,liquidWeightProduction_L";
		if(configFile.getOthers().getProductionUnit()!=0){
			prodCol="liquidVolumetricProduction,liquidVolumetricProduction_L";
		}
		String sql="select t.id,t.wellname,to_char(t.acqTime,'hh24:mi:ss'),"
				+ " t.workingConditionName,t.workingConditionAlarmLevel,"+prodCol+","
				+ " t.stroke,t.spm,t.fmax,t.fmin,t.upperloadline,t.lowerloadline,"
				+ " t.iDegreeBalanceLevel,t.iDegreeBalance,t.iDegreeBalanceAlarmLevel,"
				+ " t.wattDegreeBalanceLevel,t.wattDegreeBalance,t.wattDegreeBalanceAlarmLevel,"
				+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve  "
				+ " from viw_rpc_diagramquery_hist t "
				+ " where t.orgid in ("+orgId+") "
				+ " and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+calculateDate+"','yyyy-mm-dd')+1 "
				+ " and t.wellname='"+wellName+"' "
				+ " order by t.acqTime desc";
		List<?> list=this.findCallSql(sql);
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId("dailyDiagramOverlay");
		String columns = ddic.getTableHeader();
		dynSbf.append("{\"success\":true,\"totalCount\":" + list.size() + ",\"wellName\":\""+wellName+"\",\"calculateDate\":\""+startDate+"\",\"columns\":"+columns+",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String positionCurveData="",loadCurveData="",powerCurveData="",currentCurveData="";
				SerializableClobProxy   proxy=null;
				CLOB realClob=null;
				Object[] obj = (Object[]) list.get(i);
				if(obj[18]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[19]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					positionCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				if(obj[19]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[20]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					loadCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				if(obj[20]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[21]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					powerCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				if(obj[21]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[22]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					currentCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				dynSbf.append("{ \"id\":\"" + obj[0] + "\",");
				dynSbf.append("\"wellName\":\"" + obj[1] + "\",");
				dynSbf.append("\"calculateDate\":\"" + obj[2] + "\",");
				dynSbf.append("\"workingConditionName\":\""+obj[3]+"\",");
				dynSbf.append("\"workingConditionAlarmLevel\":\""+obj[4]+"\",");
				dynSbf.append("\""+(prodCol.split(",")[0])+"\":\""+obj[5]+"\",");
				dynSbf.append("\""+(prodCol.split(",")[1])+"\":\""+obj[6]+"\",");
				dynSbf.append("\"stroke\":\""+obj[7]+"\",");
				dynSbf.append("\"spm\":\""+obj[8]+"\",");
				dynSbf.append("\"fmax\":\""+obj[9]+"\",");
				dynSbf.append("\"fmin\":\""+obj[10]+"\",");
				dynSbf.append("\"upperLoadLine\":\""+obj[11]+"\",");
				dynSbf.append("\"lowerLoadLine\":\""+obj[12]+"\",");
				dynSbf.append("\"iDegreeBalanceLevel\":\"" + obj[13] + "\",");
				dynSbf.append("\"iDegreeBalance\":\"" + obj[14] + "\",");
				dynSbf.append("\"iDegreeBalanceAlarmLevel\":\"" + obj[15] + "\",");
				dynSbf.append("\"wattDegreeBalanceLevel\":\"" + obj[16] + "\",");
				dynSbf.append("\"wattDegreeBalance\":\"" + obj[17] + "\",");
				dynSbf.append("\"wattDegreeBalanceAlarmLevel\":\"" + obj[18] + "\",");
				dynSbf.append("\"positionCurveData\":\"" + positionCurveData + "\",");
				dynSbf.append("\"loadCurveData\":\"" + loadCurveData + "\",");
				dynSbf.append("\"powerCurveData\":\"" + powerCurveData + "\",");
				dynSbf.append("\"currentCurveData\":\"" + currentCurveData + "\"},");
			}
			if(dynSbf.toString().endsWith(",")){
				dynSbf.deleteCharAt(dynSbf.length() - 1);
			}
		}
		dynSbf.append("]}");
		return dynSbf.toString().replaceAll("null", "").replaceAll("\r\n", "\n").replaceAll("\n", "");
	}
	
	public String getDiagnosisTotalCurveData(Page pager,String orgId,String jh,String startDate,String endDate) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		String sql="select t.id, to_char(t.jssj,'yyyy-mm-dd'),t.jsdjrcyl,t.jsdjrcyl1,t.hsld,t.jsdjrcylbd "
				+ " from v_analysisaggregation t  "
				+ " where jh= '"+jh+"' and t.jssj between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') "
				+ " order by t.jssj";
		
		int totals = getTotalCountRows(sql);//获取总记录数
		List<?> list=this.findCallSql(sql);
		dynSbf.append("{\"success\":true,\"totalCount\":" + totals + ",\"jh\":\""+jh+"\",\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				dynSbf.append("{ \"id\":\"" + obj[0] + "\",");
				dynSbf.append("\"jssj\":\"" + obj[1] + "\",");
				dynSbf.append("\"rcyl\":\"" + obj[2] + "\",");
				dynSbf.append("\"rcyl1\":\""+obj[3]+"\",");
				dynSbf.append("\"hsl\":\""+obj[4]+"\",");
				dynSbf.append("\"rcylbd\":\""+obj[5]+"\"},");
			}
			dynSbf.deleteCharAt(dynSbf.length() - 1);
		}
		dynSbf.append("]}");
		return dynSbf.toString();
	}
	
	public String getDiagnosisTotalDynamicCurveData(String wellName,String selectedWellName,String calculateDate, String startDate,String endDate) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String prodCol=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,t.waterCut_W,";
		if(configFile.getOthers().getProductionUnit()!=0){
			prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,t.waterCut,";;
		}
		String sql="select well.wellname,to_char(t.calculateDate,'yyyy-mm-dd') as calculateDate,"
				+ prodCol
				+ " t.stroke,t.spm,t.wellheadfluidtemperature,t.tubingpressure,t.casingpressure,"
				+ " t.producingfluidlevel,t.pumpsettingdepth,t.submergence "
				+ " from tbl_wellinformation well,tbl_rpc_total_day t "
				+ " where t.wellid=well.id ";
		if(StringManagerUtils.isNotNull(selectedWellName)){
			sql+="and t.calculateDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')";
		}else{
			sql+="and t.calculateDate between to_date('"+calculateDate+"','yyyy-mm-dd')-30 and to_date('"+calculateDate+"','yyyy-mm-dd')";
		}
		sql+= " and well.wellname='"+wellName+"' "+ " order by t.calculateDate";
		List<?> list=this.findCallSql(sql);
		dynSbf.append("{\"success\":true,\"totalCount\":" + list.size() + ",\"wellName\":\""+wellName+"\",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				dynSbf.append("{ \"calculateDate\":\"" + obj[1] + "\",");
				dynSbf.append("\"liquidProduction\":"+obj[2]+",");
				dynSbf.append("\"oilProduction\":"+obj[3]+",");
				dynSbf.append("\"waterProduction\":"+obj[4]+",");
				dynSbf.append("\"waterCut\":"+obj[5]+",");
				dynSbf.append("\"stroke\":"+obj[6]+",");
				dynSbf.append("\"spm\":"+obj[7]+",");
				dynSbf.append("\"wellheadFluidTemperature\":"+obj[8]+",");
				dynSbf.append("\"tubingPressure\":"+obj[9]+",");
				dynSbf.append("\"casingPressure\":"+obj[10]+",");
				dynSbf.append("\"producingFluidLevel\":"+obj[11]+",");
				dynSbf.append("\"pumpSettingDepth\":"+obj[12]+",");
				dynSbf.append("\"submergence\":"+obj[13]+"},");
			}
		}
		if(dynSbf.toString().endsWith(",")){
			dynSbf.deleteCharAt(dynSbf.length() - 1);
		}
		dynSbf.append("]}");
		return dynSbf.toString();
	}
	
	public String getAnalysisAndAcqAndControlData(String id)throws Exception {
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String prodCol=" t.liquidWeightProduction,t.liquidWeightProductionMax,t.liquidWeightProductionMin,"
				+ " t.oilWeightProduction,t.oilWeightProductionMax,t.oilWeightProductionMin,"
				+ " t.waterWeightProduction,t.waterWeightProductionMax,t.waterWeightProductionMin,"
				+ " t.waterCut_W,t.waterCutMax_W,t.waterCutMin_W,"
				+ " t.availablestrokeprod_w,t.availablestrokeprod_w_max,t.availablestrokeprod_w_min,"
				+ " t.pumpclearanceleakprod_w,t.pumpclearanceleakprod_w_max,t.pumpclearanceleakprod_w_min,"
				+ " t.tvleakweightproduction,t.tvleakweightproductionmax,t.tvleakweightproductionmin,"
				+ " t.svleakweightproduction,t.svleakweightproductionmax,t.svleakweightproductionmin,"
				+ " t.gasinfluenceprod_w,t.gasinfluenceprod_w_max,t.gasinfluenceprod_w_min,";
		if(configFile.getOthers().getProductionUnit()!=0){
			prodCol=" t.liquidVolumetricProduction,t.liquidVolumetricProductionMax,t.liquidVolumetricProductionMin,"
					+ " t.oilVolumetricProduction,t.oilVolumetricProductionMax,t.oilVolumetricProductionMin,"
					+ " t.waterVolumetricProduction,t.waterVolumetricProductionMax,t.waterVolumetricProductionMin,"
					+ " t.waterCut,t.waterCutMax,t.waterCutMin,"
					+ " t.availablestrokeprod_v,t.availablestrokeprod_v_max,t.availablestrokeprod_v_min,"
					+ " t.pumpclearanceleakprod_v,t.pumpclearanceleakprod_v_max,t.pumpclearanceleakprod_v_min,"
					+ " t.tvleakvolumetricproduction,t.tvleakvolumetricproductionmax,t.tvleakvolumetricproductionmin,"
					+ " t.svleakvolumetricproduction,t.svleakvolumetricproductionmax,t.svleakvolumetricproductionmin,"
					+ " t.gasinfluenceprod_v,t.gasinfluenceprod_v_max,t.gasinfluenceprod_v_min,";
		}
		String sql="select t.runTime,runTimeEfficiency,"
				+ " t.iDegreeBalance,t.iDegreeBalanceMax,t.iDegreeBalanceMin,"
				+ " t.UpStrokeIMax_Avg,t.UpStrokeIMax_Max,t.UpStrokeIMax_Min,"
				+ " t.DownStrokeIMax_Avg,t.DownStrokeIMax_Max,t.DownStrokeIMax_Min,"
				+ " t.wattDegreeBalance,t.wattDegreeBalanceMax,t.wattDegreeBalanceMin,"
				+ " t.UpStrokeWattMax_Avg,t.UpStrokeWattMax_Max,t.UpStrokeWattMax_Min,"
				+ " t.DownStrokeWattMax_Avg,t.DownStrokeWattMax_Max,t.DownStrokeWattMax_Min,"
				+ " t.deltaRadius*100,t.deltaRadiusMax*100,t.deltaRadiusMin*100, "
				+ " theoreticalproduction,theoreticalproductionmax,theoreticalproductionmin,"
				+ prodCol
				+ " t.stroke,t.strokeMax,t.strokeMin,t.SPM,t.SPMMax,t.SPMMin,"
				+ " upperloadline,upperloadlinemax,upperloadlinemin,"
				+ " lowerloadline,lowerloadlinemax,lowerloadlinemin,"
				+ " upperloadlineofexact,upperloadlineofexactmax,upperloadlineofexactmin,"
				+ " deltaloadline,deltaloadlinemax,deltaloadlinemin,"
				+ " deltaloadlineofexact,deltaloadlineofexactmax,deltaloadlineofexactmin,"
				+ " fmax_avg,fmax_max,fmax_min,"
				+ " fmin_avg,fmin_max,fmin_min,"
				+ " deltaf,deltafmax,deltafmin,"
				+ " area,areamax,areamin,"
				+ " plungerstroke,plungerstrokemax,plungerstrokemin,"
				+ " availableplungerstroke,availableplungerstrokemax,availableplungerstrokemin,"
				+ " t.fullnesscoEfficient,t.fullnesscoEfficientMax,t.fullnesscoEfficientMin,"
				+ " t.pumpEff*100,t.pumpEffMax*100,t.pumpEffMin*100,"
				+ " pumpEff1*100,pumpEff1max*100,pumpEff1min*100,"
				+ " pumpEff2*100,pumpEff2max*100,pumpEff2min*100,"
				+ " pumpEff3*100,pumpEff3max*100,pumpEff3min*100,"
				+ " pumpEff4*100,pumpEff4max*100,pumpEff4min*100,"
				+ " rodflexlength,rodflexlengthmax,rodflexlengthmin,"
				+ " tubingflexlength,tubingflexlengthmax,tubingflexlengthmin,"
				+ " inertialength,inertialengthmax,inertialengthmin,"
				+ " t.systemEfficiency*100,t.systemEfficiencyMax*100,t.systemEfficiencyMin*100,"
				+ " t.surfaceSystemEfficiency*100,t.surfaceSystemEfficiencyMax*100,t.surfaceSystemEfficiencyMin*100,"
				+ " t.welldownSystemEfficiency*100,t.welldownSystemEfficiencyMax*100,t.welldownSystemEfficiencyMin*100,"
				+ " powerConsumptionPerTHM,powerConsumptionPerTHMmax,powerConsumptionPerTHMmin,"
				+ " AvgWatt,AvgWattmax,AvgWattmin,"
				+ " PolishRodPower,PolishRodPowermax,PolishRodPowermin,"
				+ " WaterPower,WaterPowermax,WaterPowermin,"
				+ " PumpIntakeP,PumpIntakePmax,PumpIntakePmin,"
				+ " PumpIntakeT,PumpIntakeTmax,PumpIntakeTmin,"
				+ " PumpIntakeGOL,PumpIntakeGOLmax,PumpIntakeGOLmin,"
				+ " PumpIntakeVisl,PumpIntakeVislmax,PumpIntakeVislmin,"
				+ " PumpIntakeBo,PumpIntakeBomax,PumpIntakeBomin,"
				+ " PumpOutletP,PumpOutletPmax,PumpOutletPmin,"
				+ " PumpOutletT,PumpOutletTmax,PumpOutletTmin,"
				+ " PumpOutletGOL,PumpOutletGOLmax,PumpOutletGOLmin,"
				+ " PumpOutletVisl,PumpOutletVislmax,PumpOutletVislmin,"
				+ " PumpOutletBo,PumpOutletBomax,PumpOutletBomin,"
				+ " t.pumpBoreDiameter,t.pumpBoreDiametermax,t.pumpBoreDiametermin,"
				+ " t.pumpSettingDepth,t.pumpSettingDepthMax,t.pumpSettingDepthMin,"
				+ " t.producingFluidLevel,t.producingFluidLevelMax,t.producingFluidLevelMin,"
				+ " t.submergence,t.submergenceMax,t.submergenceMin,"
				+ " tubingPressure,tubingPressuremax,tubingPressuremin,"
				+ " casingPressure,casingPressuremax,casingPressuremin,"
				+ " wellHeadFluidTemperature,wellHeadFluidTemperaturemax,wellHeadFluidTemperaturemin,"
				+ " t.productionGasOilRatio,t.productionGasOilRatioMax,t.productionGasOilRatioMin,"
				+ " t.Ia,t.IaMax,t.IaMin,t.Ib,t.IbMax,t.IbMin,t.Ic,t.IcMax,t.IcMin,"
				+ " t.Va,t.VaMax,t.VaMin,t.Vb,t.VbMax,t.VbMin,t.Vc,t.VcMax,t.VcMin,"
				+ " t.wattSum,t.wattSumMax,t.wattSumMin,"
				+ " t.varSum,t.varSumMax,t.varSumMin,"
				+ " t.vaSum,t.vaSumMax,t.vaSumMin,"
				+ " t.PFSum,t.PFSumMax,t.PFSumMin,"
				+ " t.todayKWattH,t.todayKVarH,t.todayKVAH,"//加2
				+ " t.signal,t.signalmax,t.signalmin,"//加3
				+ " t.frequency,t.frequencymax,t.frequencymin,"//加3
				+ " t.runrange,t.workingconditionstring,"
				+ " t.levelCorrectValue,t.levelCorrectValueMax,t.levelCorrectValueMin,"
				+ " t.noLiquidAvailableStroke,t.noLiquidAvailableStrokeMax,t.noLiquidAvailableStrokeMin,"
				+ " t.noLiquidFullnessCoefficient,t.noLiquidFullnessCoefficientMax,t.noLiquidFullnessCoefficientMin"
				+ " from tbl_rpc_total_day t where id="+id;
		List<?> list = this.findCallSql(sql);
		DataDictionary ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId("dailyAnalysis");
		String analysisDataList = ddic.getTableHeader();
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId("dailyAcquisition");
		String acquisitionDataList = ddic.getTableHeader();
		result_json.append("{ \"success\":true,");
		result_json.append("\"analysisDataList\":"+analysisDataList+",");
		result_json.append("\"acquisitionDataList\":"+acquisitionDataList+",");
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			result_json.append("\"runTime\":\""+obj[0]+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[1]+"\",");
			result_json.append("\"iDegreeBalance\":\""+obj[2]+"\",");
			result_json.append("\"iDegreeBalanceMax\":\""+obj[3]+"\",");
			result_json.append("\"iDegreeBalanceMin\":\""+obj[4]+"\",");
			result_json.append("\"upStrokeIMax_Avg\":\""+obj[5]+"\",");
			result_json.append("\"upStrokeIMax_Max\":\""+obj[6]+"\",");
			result_json.append("\"upStrokeIMax_Min\":\""+obj[7]+"\",");
			result_json.append("\"downStrokeIMax_Avg\":\""+obj[8]+"\",");
			result_json.append("\"downStrokeIMax_Max\":\""+obj[9]+"\",");
			result_json.append("\"downStrokeIMax_Min\":\""+obj[10]+"\",");
			result_json.append("\"wattDegreeBalance\":\""+obj[11]+"\",");
			result_json.append("\"wattDegreeBalanceMax\":\""+obj[12]+"\",");
			result_json.append("\"wattDegreeBalanceMin\":\""+obj[13]+"\",");
			result_json.append("\"upStrokeWattMax_Avg\":\""+obj[14]+"\",");
			result_json.append("\"upStrokeWattMax_Max\":\""+obj[15]+"\",");
			result_json.append("\"upStrokeWattMax_Min\":\""+obj[16]+"\",");
			result_json.append("\"downStrokeWattMax_Avg\":\""+obj[17]+"\",");
			result_json.append("\"downStrokeWattMax_Max\":\""+obj[18]+"\",");
			result_json.append("\"downStrokeWattMax_Min\":\""+obj[19]+"\",");
			result_json.append("\"deltaRadius\":\""+obj[20]+"\",");
			result_json.append("\"deltaRadiusMax\":\""+obj[21]+"\",");
			result_json.append("\"deltaRadiusMin\":\""+obj[22]+"\",");
			result_json.append("\"theoreticalProduction\":\""+obj[23]+"\",");
			result_json.append("\"theoreticalProductionMax\":\""+obj[24]+"\",");
			result_json.append("\"theoreticalProductionMin\":\""+obj[25]+"\",");
			result_json.append("\"liquidProduction\":\""+obj[26]+"\",");
			result_json.append("\"liquidProductionMax\":\""+obj[27]+"\",");
			result_json.append("\"liquidProductionMin\":\""+obj[28]+"\",");
			result_json.append("\"oilProduction\":\""+obj[29]+"\",");
			result_json.append("\"oilProductionMax\":\""+obj[30]+"\",");
			result_json.append("\"oilProductionMin\":\""+obj[31]+"\",");
			result_json.append("\"waterProduction\":\""+obj[32]+"\",");
			result_json.append("\"waterProductionMax\":\""+obj[33]+"\",");
			result_json.append("\"waterProductionMin\":\""+obj[34]+"\",");
			result_json.append("\"waterCut\":\""+obj[35]+"\",");
			result_json.append("\"waterCutMax\":\""+obj[36]+"\",");
			result_json.append("\"waterCutMin\":\""+obj[37]+"\",");
			result_json.append("\"availableStrokeProd\":\""+obj[38]+"\",");
			result_json.append("\"availableStrokeProdMax\":\""+obj[39]+"\",");
			result_json.append("\"availableStrokeProdMin\":\""+obj[40]+"\",");
			result_json.append("\"pumpClearanceLeakProd\":\""+obj[41]+"\",");
			result_json.append("\"pumpClearanceLeakProdMax\":\""+obj[42]+"\",");
			result_json.append("\"pumpClearanceLeakProdMin\":\""+obj[43]+"\",");
			result_json.append("\"TVLeakProduction\":\""+obj[44]+"\",");
			result_json.append("\"TVLeakProductionMax\":\""+obj[45]+"\",");
			result_json.append("\"TVLeakProductionMin\":\""+obj[46]+"\",");
			result_json.append("\"SVLeakProduction\":\""+obj[47]+"\",");
			result_json.append("\"SVLeakProductionMax\":\""+obj[48]+"\",");
			result_json.append("\"SVLeakProductionMin\":\""+obj[49]+"\",");
			result_json.append("\"gasInfluenceProd\":\""+obj[50]+"\",");
			result_json.append("\"gasInfluenceProdMax\":\""+obj[51]+"\",");
			result_json.append("\"gasInfluenceProdMin\":\""+obj[52]+"\",");
			result_json.append("\"stroke\":\""+obj[53]+"\",");
			result_json.append("\"strokeMax\":\""+obj[54]+"\",");
			result_json.append("\"strokeMin\":\""+obj[55]+"\",");
			result_json.append("\"SPM\":\""+obj[56]+"\",");
			result_json.append("\"SPMMax\":\""+obj[57]+"\",");
			result_json.append("\"SPMMin\":\""+obj[58]+"\",");
			result_json.append("\"upperLoadLine\":\""+obj[59]+"\",");
			result_json.append("\"upperLoadLineMax\":\""+obj[60]+"\",");
			result_json.append("\"upperLoadLineMin\":\""+obj[61]+"\",");
			result_json.append("\"lowerLoadLine\":\""+obj[62]+"\",");
			result_json.append("\"lowerLoadLineMax\":\""+obj[63]+"\",");
			result_json.append("\"lowerLoadLineMin\":\""+obj[64]+"\",");
			result_json.append("\"upperLoadLineOfExact\":\""+obj[65]+"\",");
			result_json.append("\"upperLoadLineOfExactMax\":\""+obj[66]+"\",");
			result_json.append("\"upperLoadLineOfExactMin\":\""+obj[67]+"\",");
			result_json.append("\"deltaLoadLine\":\""+obj[68]+"\",");
			result_json.append("\"deltaLoadLineMax\":\""+obj[69]+"\",");
			result_json.append("\"deltaLoadLineMin\":\""+obj[70]+"\",");
			result_json.append("\"deltaLoadLinefExact\":\""+obj[71]+"\",");
			result_json.append("\"deltaLoadLinefExactMax\":\""+obj[72]+"\",");
			result_json.append("\"deltaLoadLinefExactMin\":\""+obj[73]+"\",");
			result_json.append("\"fMax_Avg\":\""+obj[74]+"\",");
			result_json.append("\"fMax_Max\":\""+obj[75]+"\",");
			result_json.append("\"fMax_Min\":\""+obj[76]+"\",");
			result_json.append("\"fMin_Avg\":\""+obj[77]+"\",");
			result_json.append("\"fMin_Max\":\""+obj[78]+"\",");
			result_json.append("\"fMin_Min\":\""+obj[79]+"\",");
			result_json.append("\"deltaF\":\""+obj[80]+"\",");
			result_json.append("\"deltaFMax\":\""+obj[81]+"\",");
			result_json.append("\"deltaFMin\":\""+obj[82]+"\",");
			result_json.append("\"area\":\""+obj[83]+"\",");
			result_json.append("\"areaMax\":\""+obj[84]+"\",");
			result_json.append("\"areaMin\":\""+obj[85]+"\",");
			result_json.append("\"plungerStroke\":\""+obj[86]+"\",");
			result_json.append("\"plungerStrokeMax\":\""+obj[87]+"\",");
			result_json.append("\"plungerStrokeMin\":\""+obj[88]+"\",");
			result_json.append("\"availablePlungerStroke\":\""+obj[89]+"\",");
			result_json.append("\"availablePlungerStrokeMax\":\""+obj[90]+"\",");
			result_json.append("\"availablePlungerStrokeMin\":\""+obj[91]+"\",");
			result_json.append("\"fullnesscoEfficient\":\""+obj[92]+"\",");
			result_json.append("\"fullnesscoEfficientMax\":\""+obj[93]+"\",");
			result_json.append("\"fullnesscoEfficientMin\":\""+obj[94]+"\",");
			result_json.append("\"pumpEff\":\""+obj[95]+"\",");
			result_json.append("\"pumpEffMax\":\""+obj[96]+"\",");
			result_json.append("\"pumpEffMin\":\""+obj[97]+"\",");
			result_json.append("\"pumpEff1\":\""+obj[98]+"\",");
			result_json.append("\"pumpEff1Max\":\""+obj[99]+"\",");
			result_json.append("\"pumpEff1Min\":\""+obj[100]+"\",");
			result_json.append("\"pumpEff2\":\""+obj[101]+"\",");
			result_json.append("\"pumpEff2Max\":\""+obj[102]+"\",");
			result_json.append("\"pumpEff2Min\":\""+obj[103]+"\",");
			result_json.append("\"pumpEff3\":\""+obj[104]+"\",");
			result_json.append("\"pumpEff3Max\":\""+obj[105]+"\",");
			result_json.append("\"pumpEff3Min\":\""+obj[106]+"\",");
			result_json.append("\"pumpEff4\":\""+obj[107]+"\",");
			result_json.append("\"pumpEff4Max\":\""+obj[108]+"\",");
			result_json.append("\"pumpEff4Min\":\""+obj[109]+"\",");
			result_json.append("\"rodFlexLength\":\""+obj[110]+"\",");
			result_json.append("\"rodFlexLengthMax\":\""+obj[111]+"\",");
			result_json.append("\"rodFlexLengthMin\":\""+obj[112]+"\",");
			result_json.append("\"tubingFlexLength\":\""+obj[113]+"\",");
			result_json.append("\"tubingFlexLengthMax\":\""+obj[114]+"\",");
			result_json.append("\"tubingFlexLengthMin\":\""+obj[115]+"\",");
			result_json.append("\"inertiaLength\":\""+obj[116]+"\",");
			result_json.append("\"inertiaLengthMax\":\""+obj[117]+"\",");
			result_json.append("\"inertiaLengthMin\":\""+obj[118]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[119]+"\",");
			result_json.append("\"systemEfficiencyMax\":\""+obj[120]+"\",");
			result_json.append("\"systemEfficiencyMin\":\""+obj[121]+"\",");
			result_json.append("\"surfaceSystemEfficiency\":\""+obj[122]+"\",");
			result_json.append("\"surfaceSystemEfficiencyMax\":\""+obj[123]+"\",");
			result_json.append("\"surfaceSystemEfficiencyMin\":\""+obj[124]+"\",");
			result_json.append("\"welldownSystemEfficiency\":\""+obj[125]+"\",");
			result_json.append("\"welldownSystemEfficiencyMax\":\""+obj[126]+"\",");
			result_json.append("\"welldownSystemEfficiencyMin\":\""+obj[127]+"\",");
			result_json.append("\"powerConsumptionPerTHM\":\""+obj[128]+"\",");
			result_json.append("\"powerConsumptionPerTHMMax\":\""+obj[129]+"\",");
			result_json.append("\"powerConsumptionPerTHMMin\":\""+obj[130]+"\",");
			result_json.append("\"avgWatt\":\""+obj[131]+"\",");
			result_json.append("\"avgWattMax\":\""+obj[132]+"\",");
			result_json.append("\"avgWattMin\":\""+obj[133]+"\",");
			result_json.append("\"polishRodPower\":\""+obj[134]+"\",");
			result_json.append("\"polishRodPowerMax\":\""+obj[135]+"\",");
			result_json.append("\"polishRodPowerMin\":\""+obj[136]+"\",");
			result_json.append("\"waterPower\":\""+obj[137]+"\",");
			result_json.append("\"waterPowerMax\":\""+obj[138]+"\",");
			result_json.append("\"waterPowerMin\":\""+obj[139]+"\",");
			result_json.append("\"pumpIntakeP\":\""+obj[140]+"\",");
			result_json.append("\"pumpIntakePMax\":\""+obj[141]+"\",");
			result_json.append("\"pumpIntakePMin\":\""+obj[142]+"\",");
			result_json.append("\"pumpIntakeT\":\""+obj[143]+"\",");
			result_json.append("\"pumpIntakeTMax\":\""+obj[144]+"\",");
			result_json.append("\"pumpIntakeTMin\":\""+obj[145]+"\",");
			result_json.append("\"pumpIntakeGOL\":\""+obj[146]+"\",");
			result_json.append("\"pumpIntakeGOLMax\":\""+obj[147]+"\",");
			result_json.append("\"pumpIntakeGOLMin\":\""+obj[148]+"\",");
			result_json.append("\"pumpIntakeVisl\":\""+obj[149]+"\",");
			result_json.append("\"pumpIntakeVislMax\":\""+obj[150]+"\",");
			result_json.append("\"pumpIntakeVislMin\":\""+obj[151]+"\",");
			result_json.append("\"pumpIntakeBo\":\""+obj[152]+"\",");
			result_json.append("\"pumpIntakeBoMax\":\""+obj[153]+"\",");
			result_json.append("\"pumpIntakeBoMin\":\""+obj[154]+"\",");
			result_json.append("\"pumpOutletP\":\""+obj[155]+"\",");
			result_json.append("\"pumpOutletPMax\":\""+obj[156]+"\",");
			result_json.append("\"pumpOutletPMin\":\""+obj[157]+"\",");
			result_json.append("\"pumpOutletT\":\""+obj[158]+"\",");
			result_json.append("\"pumpOutletTMax\":\""+obj[159]+"\",");
			result_json.append("\"pumpOutletTMin\":\""+obj[160]+"\",");
			result_json.append("\"pumpOutletGOL\":\""+obj[161]+"\",");
			result_json.append("\"pumpOutletGOLMax\":\""+obj[162]+"\",");
			result_json.append("\"pumpOutletGOLMin\":\""+obj[163]+"\",");
			result_json.append("\"pumpOutletVisl\":\""+obj[164]+"\",");
			result_json.append("\"pumpOutletVislMax\":\""+obj[165]+"\",");
			result_json.append("\"pumpOutletVislMin\":\""+obj[166]+"\",");
			result_json.append("\"pumpOutletBo\":\""+obj[167]+"\",");
			result_json.append("\"pumpOutletBoMax\":\""+obj[168]+"\",");
			result_json.append("\"pumpOutletBoMin\":\""+obj[169]+"\",");
			result_json.append("\"pumpBoreDiameter\":\""+obj[170]+"\",");
			result_json.append("\"pumpBoreDiameterMax\":\""+obj[171]+"\",");
			result_json.append("\"pumpBoreDiameterMin\":\""+obj[172]+"\",");
			result_json.append("\"pumpSettingDepth\":\""+obj[173]+"\",");
			result_json.append("\"pumpSettingDepthMax\":\""+obj[174]+"\",");
			result_json.append("\"pumpSettingDepthMin\":\""+obj[175]+"\",");
			result_json.append("\"producingFluidLevel\":\""+obj[176]+"\",");
			result_json.append("\"producingFluidLevelMax\":\""+obj[177]+"\",");
			result_json.append("\"producingFluidLevelMin\":\""+obj[178]+"\",");
			result_json.append("\"submergence\":\""+obj[179]+"\",");
			result_json.append("\"submergenceMax\":\""+obj[180]+"\",");
			result_json.append("\"submergenceMin\":\""+obj[181]+"\",");
			result_json.append("\"tubingPressure\":\""+obj[182]+"\",");
			result_json.append("\"tubingPressureMax\":\""+obj[183]+"\",");
			result_json.append("\"tubingPressureMin\":\""+obj[184]+"\",");
			result_json.append("\"casingPressure\":\""+obj[185]+"\",");
			result_json.append("\"casingPressureMax\":\""+obj[186]+"\",");
			result_json.append("\"casingPressureMin\":\""+obj[187]+"\",");
			result_json.append("\"wellHeadFluidTemperature\":\""+obj[188]+"\",");
			result_json.append("\"wellHeadFluidTemperatureMax\":\""+obj[189]+"\",");
			result_json.append("\"wellHeadFluidTemperatureMin\":\""+obj[190]+"\",");
			result_json.append("\"productionGasOilRatio\":\""+obj[191]+"\",");
			result_json.append("\"productionGasOilRatioMax\":\""+obj[192]+"\",");
			result_json.append("\"productionGasOilRatioMin\":\""+obj[193]+"\",");
			result_json.append("\"Ia\":\""+obj[194]+"\",");
			result_json.append("\"IaMax\":\""+obj[195]+"\",");
			result_json.append("\"IaMin\":\""+obj[196]+"\",");
			result_json.append("\"Ib\":\""+obj[197]+"\",");
			result_json.append("\"IbMax\":\""+obj[198]+"\",");
			result_json.append("\"IbMin\":\""+obj[199]+"\",");
			result_json.append("\"Ic\":\""+obj[200]+"\",");
			result_json.append("\"IcMax\":\""+obj[201]+"\",");
			result_json.append("\"IcMin\":\""+obj[202]+"\",");
			result_json.append("\"Va\":\""+obj[203]+"\",");
			result_json.append("\"VaMax\":\""+obj[204]+"\",");
			result_json.append("\"VaMin\":\""+obj[205]+"\",");
			result_json.append("\"Vb\":\""+obj[206]+"\",");
			result_json.append("\"VbMax\":\""+obj[207]+"\",");
			result_json.append("\"VbMin\":\""+obj[208]+"\",");
			result_json.append("\"Vc\":\""+obj[209]+"\",");
			result_json.append("\"VcMax\":\""+obj[210]+"\",");
			result_json.append("\"VcMin\":\""+obj[211]+"\",");
			result_json.append("\"wattSum\":\""+obj[212]+"\",");
			result_json.append("\"wattSumMax\":\""+obj[213]+"\",");
			result_json.append("\"wattSumMin\":\""+obj[214]+"\",");
			result_json.append("\"varSum\":\""+obj[215]+"\",");
			result_json.append("\"varSumMax\":\""+obj[216]+"\",");
			result_json.append("\"varSumMin\":\""+obj[217]+"\",");
			result_json.append("\"vaSum\":\""+obj[218]+"\",");
			result_json.append("\"vaSumMax\":\""+obj[219]+"\",");
			result_json.append("\"vaSumMin\":\""+obj[220]+"\",");
			result_json.append("\"PFSum\":\""+obj[221]+"\",");
			result_json.append("\"PFSumMax\":\""+obj[222]+"\",");
			result_json.append("\"PFSumMin\":\""+obj[223]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[224]+"\",");
			result_json.append("\"todayKVarH\":\""+obj[225]+"\",");
			result_json.append("\"todayKVAH\":\""+obj[226]+"\",");
			result_json.append("\"signal\":\""+obj[227]+"\",");
			result_json.append("\"signalMax\":\""+obj[228]+"\",");
			result_json.append("\"signalMin\":\""+obj[229]+"\",");
			result_json.append("\"frequency\":\""+obj[230]+"\",");
			result_json.append("\"frequencyMax\":\""+obj[231]+"\",");
			result_json.append("\"frequencyMin\":\""+obj[232]+"\",");
			result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[233])+"\",");
			result_json.append("\"workingConditionString\":\""+(StringManagerUtils.CLOBObjectToString(obj[234])+"").replaceAll("<br/>", ";")+"\",");
			result_json.append("\"levelCorrectValue\":\""+obj[235]+"\",");
			result_json.append("\"levelCorrectValueMax\":\""+obj[236]+"\",");
			result_json.append("\"levelCorrectValueMin\":\""+obj[237]+"\",");
			result_json.append("\"noLiquidAvailableStroke\":\""+obj[238]+"\",");
			result_json.append("\"noLiquidAvailableStrokeMax\":\""+obj[239]+"\",");
			result_json.append("\"noLiquidAvailableStrokeMin\":\""+obj[240]+"\",");
			result_json.append("\"noLiquidFullnessCoefficient\":\""+obj[241]+"\",");
			result_json.append("\"noLiquidFullnessCoefficientMax\":\""+obj[242]+"\",");
			result_json.append("\"noLiquidFullnessCoefficientMin\":\""+obj[243]+"\"");
		}
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getPCPAnalysisAndAcqAndControlData(String id)throws Exception {
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String prodCol=" t.liquidWeightProduction,t.liquidWeightProductionMax,t.liquidWeightProductionMin,"
				+ " t.oilWeightProduction,t.oilWeightProductionMax,t.oilWeightProductionMin,"
				+ " t.waterWeightProduction,t.waterWeightProductionMax,t.waterWeightProductionMin,"
				+ " t.waterCut_W,t.waterCutMax_W,t.waterCutMin_W,";
		if(configFile.getOthers().getProductionUnit()!=0){
			prodCol=" t.liquidVolumetricProduction,t.liquidVolumetricProductionMax,t.liquidVolumetricProductionMin,"
					+ " t.oilVolumetricProduction,t.oilVolumetricProductionMax,t.oilVolumetricProductionMin,"
					+ " t.waterVolumetricProduction,t.waterVolumetricProductionMax,t.waterVolumetricProductionMin,"
					+ " t.waterCut,t.waterCutMax,t.waterCutMin,";
		}
		String sql="select t.runTime,runTimeEfficiency,"
				+ " t.rpm,t.rpmMax,t.rpMmin,"
				+ " t.theoreticalProduction,t.theoreticalProductionMax,t.theoreticalProductionMin,"
				+ prodCol
				+ " t.pumpEff*100,t.pumpEffMax*100,t.pumpEffMin*100,"
				+ " t.pumpEff1*100,t.pumpEff1Max*100,t.pumpEff1Min*100,"
				+ " t.pumpEff2*100,t.pumpEff2Max*100,t.pumpEff2Min*100,"
				+ " t.systemEfficiency*100,t.systemEfficiencyMax*100,t.systemEfficiencyMin*100,"
				+ " t.powerConsumptionPerTHM,t.powerConsumptionPerTHMMax,t.powerConsumptionPerTHMMin,"
				+ " t.AvgWatt,t.AvgWattMax,t.AvgWattMin,"
				+ " t.WaterPower,t.WaterPowerMax,t.WaterPowerMin,"
				+ " t.pumpSettingDepth,t.pumpSettingDepthMax,t.pumpSettingDepthMin,"
				+ " t.producingFluidLevel,t.producingFluidLevelMax,t.producingFluidLevelMin,"
				+ " t.submergence,t.submergenceMax,t.submergenceMin,"
				+ " t.tubingPressure,t.tubingPressureMax,t.tubingPressureMin,"
				+ " t.casingPressure,t.casingPressureMax,t.casingPressureMin,"
				+ " t.wellHeadFluidTemperature,t.wellHeadFluidTemperatureMax,t.wellHeadFluidTemperatureMin,"
				+ " t.productionGasOilRatio,t.productionGasOilRatioMax,t.productionGasOilRatioMin,"
				+ " t.frequency,t.frequencyMax,t.frequencyMin,"
				+ " t.Ia,t.IaMax,t.IaMin,t.Ib,t.IbMax,t.IbMin,t.Ic,t.IcMax,t.IcMin,"
				+ " t.Va,t.VaMax,t.VaMin,t.Vb,t.VbMax,t.VbMin,t.Vc,t.VcMax,t.VcMin,"
				+ " t.todayKWattH,t.todayKVarH,t.todayKVAH,"
				+ " t.wattSum,t.wattSumMax,t.wattSumMin,"
				+ " t.varSum,t.varSumMax,t.varSumMin,"
				+ " t.vaSum,t.vaSumMax,t.vaSumMin,"
				+ " t.PFSum,t.PFSumMax,t.PFSumMin,"
				+ " t.runrange,t.workingconditionstring"
				+ " from tbl_pcp_total_day t where id="+id;
		List<?> list = this.findCallSql(sql);
		DataDictionary ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId("screwPumpDailyAnalysis");
		String analysisDataList = ddic.getTableHeader();
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId("screwPumpDailyAcquisition");
		String acquisitionDataList = ddic.getTableHeader();
		result_json.append("{ \"success\":true,");
		result_json.append("\"analysisDataList\":"+analysisDataList+",");
		result_json.append("\"acquisitionDataList\":"+acquisitionDataList+",");
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			result_json.append("\"runTime\":\""+obj[0]+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[1]+"\",");
			
			result_json.append("\"rpm\":\""+obj[2]+"\",");
			result_json.append("\"rpmMax\":\""+obj[3]+"\",");
			result_json.append("\"rpMmin\":\""+obj[4]+"\",");
			
			result_json.append("\"theoreticalProduction\":\""+obj[5]+"\",");
			result_json.append("\"theoreticalProductionMax\":\""+obj[6]+"\",");
			result_json.append("\"theoreticalProductionMin\":\""+obj[7]+"\",");
			
			result_json.append("\"liquidProduction\":\""+obj[8]+"\",");
			result_json.append("\"liquidProductionMax\":\""+obj[9]+"\",");
			result_json.append("\"liquidProductionMin\":\""+obj[10]+"\",");
			result_json.append("\"oilProduction\":\""+obj[11]+"\",");
			result_json.append("\"oilProductionMax\":\""+obj[12]+"\",");
			result_json.append("\"oilProductionMin\":\""+obj[13]+"\",");
			result_json.append("\"waterProduction\":\""+obj[14]+"\",");
			result_json.append("\"waterProductionMax\":\""+obj[15]+"\",");
			result_json.append("\"waterProductionMin\":\""+obj[16]+"\",");
			result_json.append("\"waterCut\":\""+obj[17]+"\",");
			result_json.append("\"waterCutMax\":\""+obj[18]+"\",");
			result_json.append("\"waterCutMin\":\""+obj[19]+"\",");
			
			result_json.append("\"pumpEff\":\""+obj[20]+"\",");
			result_json.append("\"pumpEffMax\":\""+obj[21]+"\",");
			result_json.append("\"pumpEffMin\":\""+obj[22]+"\",");
			result_json.append("\"pumpEff1\":\""+obj[23]+"\",");
			result_json.append("\"pumpEff1Max\":\""+obj[24]+"\",");
			result_json.append("\"pumpEff1Min\":\""+obj[25]+"\",");
			result_json.append("\"pumpEff2\":\""+obj[26]+"\",");
			result_json.append("\"pumpEff2Max\":\""+obj[27]+"\",");
			result_json.append("\"pumpEff2Min\":\""+obj[28]+"\",");
			
			result_json.append("\"systemEfficiency\":\""+obj[29]+"\",");
			result_json.append("\"systemEfficiencyMax\":\""+obj[30]+"\",");
			result_json.append("\"systemEfficiencyMin\":\""+obj[31]+"\",");
			result_json.append("\"powerConsumptionPerTHM\":\""+obj[32]+"\",");
			result_json.append("\"powerConsumptionPerTHMMax\":\""+obj[33]+"\",");
			result_json.append("\"powerConsumptionPerTHMMin\":\""+obj[34]+"\",");
			result_json.append("\"avgWatt\":\""+obj[35]+"\",");
			result_json.append("\"avgWattMax\":\""+obj[36]+"\",");
			result_json.append("\"avgWattMin\":\""+obj[37]+"\",");
			result_json.append("\"waterPower\":\""+obj[38]+"\",");
			result_json.append("\"waterPowerMax\":\""+obj[39]+"\",");
			result_json.append("\"waterPowerMin\":\""+obj[40]+"\",");
			
			result_json.append("\"pumpSettingDepth\":\""+obj[41]+"\",");
			result_json.append("\"pumpSettingDepthMax\":\""+obj[42]+"\",");
			result_json.append("\"pumpSettingDepthMin\":\""+obj[43]+"\",");
			result_json.append("\"producingFluidLevel\":\""+obj[44]+"\",");
			result_json.append("\"producingFluidLevelMax\":\""+obj[45]+"\",");
			result_json.append("\"producingFluidLevelMin\":\""+obj[46]+"\",");
			result_json.append("\"submergence\":\""+obj[47]+"\",");
			result_json.append("\"submergenceMax\":\""+obj[48]+"\",");
			result_json.append("\"submergenceMin\":\""+obj[49]+"\",");
			
			result_json.append("\"tubingPressure\":\""+obj[50]+"\",");
			result_json.append("\"tubingPressureMax\":\""+obj[51]+"\",");
			result_json.append("\"tubingPressureMin\":\""+obj[52]+"\",");
			result_json.append("\"casingPressure\":\""+obj[53]+"\",");
			result_json.append("\"casingPressureMax\":\""+obj[54]+"\",");
			result_json.append("\"casingPressureMin\":\""+obj[55]+"\",");
			result_json.append("\"wellHeadFluidTemperature\":\""+obj[56]+"\",");
			result_json.append("\"wellHeadFluidTemperatureMax\":\""+obj[57]+"\",");
			result_json.append("\"wellHeadFluidTemperatureMin\":\""+obj[58]+"\",");
			result_json.append("\"productionGasOilRatio\":\""+obj[59]+"\",");
			result_json.append("\"productionGasOilRatioMax\":\""+obj[60]+"\",");
			result_json.append("\"productionGasOilRatioMin\":\""+obj[61]+"\",");
			
			result_json.append("\"frequency\":\""+obj[62]+"\",");
			result_json.append("\"frequencyMax\":\""+obj[63]+"\",");
			result_json.append("\"frequencyMin\":\""+obj[64]+"\",");
			
			result_json.append("\"Ia\":\""+obj[65]+"\",");
			result_json.append("\"IaMax\":\""+obj[66]+"\",");
			result_json.append("\"IaMin\":\""+obj[67]+"\",");
			result_json.append("\"Ib\":\""+obj[68]+"\",");
			result_json.append("\"IbMax\":\""+obj[69]+"\",");
			result_json.append("\"IbMin\":\""+obj[70]+"\",");
			result_json.append("\"Ic\":\""+obj[71]+"\",");
			result_json.append("\"IcMax\":\""+obj[72]+"\",");
			result_json.append("\"IcMin\":\""+obj[73]+"\",");
			result_json.append("\"Va\":\""+obj[74]+"\",");
			result_json.append("\"VaMax\":\""+obj[75]+"\",");
			result_json.append("\"VaMin\":\""+obj[76]+"\",");
			result_json.append("\"Vb\":\""+obj[77]+"\",");
			result_json.append("\"VbMax\":\""+obj[78]+"\",");
			result_json.append("\"VbMin\":\""+obj[79]+"\",");
			result_json.append("\"Vc\":\""+obj[80]+"\",");
			result_json.append("\"VcMax\":\""+obj[81]+"\",");
			result_json.append("\"VcMin\":\""+obj[82]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[83]+"\",");
			result_json.append("\"todayKVarH\":\""+obj[84]+"\",");
			result_json.append("\"todayKVAH\":\""+obj[85]+"\",");
			result_json.append("\"wattSum\":\""+obj[86]+"\",");
			result_json.append("\"wattSumMax\":\""+obj[87]+"\",");
			result_json.append("\"wattSumMin\":\""+obj[88]+"\",");
			result_json.append("\"varSum\":\""+obj[89]+"\",");
			result_json.append("\"varSumMax\":\""+obj[90]+"\",");
			result_json.append("\"varSumMin\":\""+obj[91]+"\",");
			result_json.append("\"vaSum\":\""+obj[92]+"\",");
			result_json.append("\"vaSumMax\":\""+obj[93]+"\",");
			result_json.append("\"vaSumMin\":\""+obj[94]+"\",");
			result_json.append("\"PFSum\":\""+obj[95]+"\",");
			result_json.append("\"PFSumMax\":\""+obj[96]+"\",");
			result_json.append("\"PFSumMin\":\""+obj[97]+"\",");
			
			result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[98])+"\",");
			result_json.append("\"workingConditionString\":\""+(StringManagerUtils.CLOBObjectToString(obj[99])+"").replaceAll("<br/>", ";")+"\"");
		}
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getRPCDiagnosisDailyCurveData(String wellName,String startDate,String endDate,String itemName,String itemCode) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		if(!"runTime".equalsIgnoreCase(itemCode)&&!"runTimeEfficiency".equalsIgnoreCase(itemCode)
				&&!"todayKWattH".equalsIgnoreCase(itemCode)
				&&!"todayKVarH".equalsIgnoreCase(itemCode)
				&&!"todayKVAH".equalsIgnoreCase(itemCode)){
			if("pumpEff".equalsIgnoreCase(itemCode)||"pumpEff1".equalsIgnoreCase(itemCode)||"pumpEff2".equalsIgnoreCase(itemCode)||"pumpEff3".equalsIgnoreCase(itemCode)||"pumpEff4".equalsIgnoreCase(itemCode)
				||"surfaceSystemEfficiency".equalsIgnoreCase(itemCode)||"welldownSystemEfficiency".equalsIgnoreCase(itemCode)||"systemEfficiency".equalsIgnoreCase(itemCode)
				||"deltaRadius".equalsIgnoreCase(itemCode)){
				itemCode="t."+itemCode+"*100,t."+itemCode+"max*100,t."+itemCode+"min*100";
			}else if("WATERCUT_W".equalsIgnoreCase(itemCode)){
				itemCode="t."+itemCode+",t.waterCutMax_w,t.waterCutMin_w";
			}else if(itemCode.toUpperCase().endsWith("MAX")||itemCode.toUpperCase().endsWith("MIN")){
				itemCode="t."+itemCode+"_Avg,t."+itemCode+"_Max,t."+itemCode+"_Min";
			}else if(itemCode.toUpperCase().endsWith("_V")||itemCode.toUpperCase().endsWith("_W")){
				itemCode="t."+itemCode+",t."+itemCode+"_Max,t."+itemCode+"_Min";
			}else{
				itemCode="t."+itemCode+",t."+itemCode+"Max,t."+itemCode+"Min";
			}
		}else{
			itemCode="t."+itemCode;
		}
		String sql="select to_char(t.calculateDate,'yyyy-mm-dd'),"+itemCode+" from tbl_rpc_total_day t,tbl_wellinformation t007 "
				+ " where t.wellid=t007.id and  t007.wellName='"+wellName+"' and t.calculateDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') order by t.calculateDate";
		int totals = getTotalCountRows(sql);//获取总记录数
		List<?> list=this.findCallSql(sql);
		dynSbf.append("{\"success\":true,\"totalCount\":" + totals+",\"itemNum\":"+itemCode.split(",").length + ",\"wellName\":\""+wellName+"\",\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				dynSbf.append("{ \"calculateDate\":\"" + obj[0] + "\",");
				dynSbf.append("\"value\":\""+obj[1]+"\"");
				if(obj.length==4){
					dynSbf.append(",\"maxValue\":\""+obj[2]+"\"");
					dynSbf.append(",\"minValue\":\""+obj[3]+"\"");
				}
				dynSbf.append("},");
			}
			dynSbf.deleteCharAt(dynSbf.length() - 1);
		}
		dynSbf.append("]}");
		return dynSbf.toString();
	}
	
	public String getPCPDiagnosisDailyCurveData(String wellName,String startDate,String endDate,String itemName,String itemCode) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		if(!"runTime".equalsIgnoreCase(itemCode)&&!"runTimeEfficiency".equalsIgnoreCase(itemCode)
				&&!"todayKWattH".equalsIgnoreCase(itemCode)
				&&!"todayKVarH".equalsIgnoreCase(itemCode)
				&&!"todayKVAH".equalsIgnoreCase(itemCode)){
			if("pumpEff".equalsIgnoreCase(itemCode)||"pumpEff1".equalsIgnoreCase(itemCode)||"pumpEff2".equalsIgnoreCase(itemCode)||"pumpEff3".equalsIgnoreCase(itemCode)||"pumpEff4".equalsIgnoreCase(itemCode)
				||"surfaceSystemEfficiency".equalsIgnoreCase(itemCode)||"welldownSystemEfficiency".equalsIgnoreCase(itemCode)||"systemEfficiency".equalsIgnoreCase(itemCode)
				||"deltaRadius".equalsIgnoreCase(itemCode)){
				itemCode="t."+itemCode+"*100,t."+itemCode+"max*100,t."+itemCode+"min*100";
			}else if("WATERCUT_W".equalsIgnoreCase(itemCode)){
				itemCode="t."+itemCode+",t.waterCutMax_w,t.waterCutMin_w";
			}else if(itemCode.toUpperCase().endsWith("MAX")||itemCode.toUpperCase().endsWith("MIN")){
				itemCode="t."+itemCode+"_Avg,t."+itemCode+"_Max,t."+itemCode+"_Min";
			}else if(itemCode.toUpperCase().endsWith("_V")||itemCode.toUpperCase().endsWith("_W")){
				itemCode="t."+itemCode+",t."+itemCode+"_Max,t."+itemCode+"_Min";
			}else{
				itemCode="t."+itemCode+",t."+itemCode+"Max,t."+itemCode+"Min";
			}
		}else{
			itemCode="t."+itemCode;
		}
		String sql="select to_char(t.calculateDate,'yyyy-mm-dd'),"+itemCode+" from tbl_pcp_total_day t,tbl_wellinformation t007 "
				+ " where t.wellid=t007.id and  t007.wellName='"+wellName+"' and t.calculateDate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') order by t.calculateDate";
		int totals = getTotalCountRows(sql);//获取总记录数
		List<?> list=this.findCallSql(sql);
		dynSbf.append("{\"success\":true,\"totalCount\":" + totals+",\"itemNum\":"+itemCode.split(",").length + ",\"wellName\":\""+wellName+"\",\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				dynSbf.append("{ \"calculateDate\":\"" + obj[0] + "\",");
				dynSbf.append("\"value\":\""+obj[1]+"\"");
				if(obj.length==4){
					dynSbf.append(",\"maxValue\":\""+obj[2]+"\"");
					dynSbf.append(",\"minValue\":\""+obj[3]+"\"");
				}
				dynSbf.append("},");
			}
			dynSbf.deleteCharAt(dynSbf.length() - 1);
		}
		dynSbf.append("]}");
		return dynSbf.toString();
	}
	
	public String getScrewPumpDailyAnalysiCurveData(String calculateDate,String wellName) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		String sql="select to_char(t.calculateDate,'yyyy-mm-dd'),t.rpm,t.ia,t.ib,t.ic,t.va,t.vb,t.vc "
				+ " from viw_pcp_total_day t "
				+ " where t.wellName='"+wellName+"' and t.calculateDate between to_date('"+calculateDate+"','yyyy-mm-dd')-30 and to_date('"+calculateDate+"','yyyy-mm-dd') "
				+ " order by t.calculateDate";
		List<?> list=this.findCallSql(sql);
		dynSbf.append("{\"success\":true,\"totalCount\":" + list.size() + ",\"wellName\":\""+wellName+"\",\"calculateDate\":\""+calculateDate+"\",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				dynSbf.append("{ \"calculateDate\":\"" + obj[0] + "\",");
				dynSbf.append("\"rpm\":\""+obj[1]+"\",");
				dynSbf.append("\"ia\":\""+obj[2]+"\",");
				dynSbf.append("\"ib\":\""+obj[3]+"\",");
				dynSbf.append("\"ic\":\""+obj[4]+"\",");
				dynSbf.append("\"va\":\""+obj[5]+"\",");
				dynSbf.append("\"vb\":\""+obj[6]+"\",");
				dynSbf.append("\"vc\":\""+obj[7]+"\"},");
			}
			if(dynSbf.toString().endsWith(",")){
				dynSbf.deleteCharAt(dynSbf.length() - 1);
			}
		}
		dynSbf.append("]}");
		return dynSbf.toString().replaceAll("null", "");
	}
	
	public BaseDao getDao() {
		return dao;
	}

	@Resource
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
}
