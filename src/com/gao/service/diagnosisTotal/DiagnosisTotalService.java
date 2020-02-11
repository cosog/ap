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
		
		String typeColumnName="workingConditionName";
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
			typeColumnName="todayWattEnergyLevel";
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
		}
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		columns = ddic.getTableHeader();
		String sql=ddic.getSql()+",workingConditionString_E,workingConditionString,"
				+ " workingConditionAlarmLevel,workingConditionAlarmLevel_E,"
				+ " commStatus,runStatus,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel "
				+ " from v_dailydata t where t.org_id in ("+orgId+") ";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and to_date(to_char(t.calculateDate,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') "
				+ " and  t.wellName='"+wellName+"' "
				+ " order by t.calculateDate desc";
		}else{
			sql+=" and t.calculateDate=to_date('"+totalDate+"','yyyy-mm-dd') ";
			if(StringManagerUtils.isNotNull(statValue)){
				sql+=" and "+typeColumnName+"='"+statValue+"'";
			}
			if(StringManagerUtils.isNotNull(wellType)){
				sql+=" and liftingType>="+wellType+" and liftingType<("+wellType+"+100) ";
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
		
		String typeColumnName="workingConditionName";
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
			typeColumnName="todayWattEnergyLevel";
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
		}
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		columns = ddic.getTableHeader();
		String sql=ddic.getSql()+",workingConditionString_E,workingConditionString,"
				+ " workingConditionAlarmLevel,workingConditionAlarmLevel_E,"
				+ " commStatus,runStatus,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel "
				+ " from v_dailydata t where t.org_id in ("+orgId+") ";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and to_date(to_char(t.calculateDate,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') "
				+ " and  t.wellName='"+wellName+"' "
				+ " order by t.calculateDate desc";
		}else{
			sql+=" and t.calculateDate=to_date('"+totalDate+"','yyyy-mm-dd') ";
			if(StringManagerUtils.isNotNull(statValue)){
				sql+=" and "+typeColumnName+"='"+statValue+"'";
			}
			if(StringManagerUtils.isNotNull(wellType)){
				sql+=" and liftingType>="+wellType+" and liftingType<("+wellType+"+100) ";
			}
			sql+=" order by t.sortnum, t.wellName";
		}
		String getResult = this.findExportDataBySqlEntity(sql,sql, columns, 20 + "", pager);
		return getResult.replaceAll("null", "").replaceAll("//", "");
	}
	
	public String GetDiagnosisTotalStatistics(String orgId,String type,String wellType,String totalDate){
		StringBuffer result_json = new StringBuffer();
		String sql="";
		String statType="workingConditionName";
		if("1".equalsIgnoreCase(type)){
			statType="workingConditionName";
		}else if("2".equalsIgnoreCase(type)){
			statType="liquidWeightProductionlevel";
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
			statType="todayWattEnergyLevel";
		}else if("11".equalsIgnoreCase(type)){
			statType="commStatusName";
		}else if("12".equalsIgnoreCase(type)){
			statType="commtimeefficiencyLevel";
		}
		sql="select "+statType+", count(*) from v_dailydata t where  org_id in ("+orgId+") and calculateDate=to_date('"+totalDate+"','yyyy-mm-dd') ";
		if(StringManagerUtils.isNotNull(wellType)){
			sql+=" and liftingtype>="+wellType+" and liftingtype<("+wellType+"+99) ";
		}
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
		String sql="select t.id,t.wellname,to_char(t.acquisitiontime,'hh24:mi:ss'),t.stroke,t.spm,t.fmax,t.fmin,"
				+ " t.iDegreeBalanceLevel,t.iDegreeBalance,t.iDegreeBalanceAlarmLevel,"
				+ " t.wattDegreeBalanceLevel,t.wattDegreeBalance,t.wattDegreeBalanceAlarmLevel,"
				+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve  "
				+ " from v_fsdiagram t "
				+ " where t.orgid in ("+orgId+") "
				+ " and t.acquisitiontime between to_date('"+calculateDate+"','yyyy-mm-dd') and to_date('"+calculateDate+"','yyyy-mm-dd')+1 "
				+ " and t.wellname='"+wellName+"' "
				+ " order by t.acquisitiontime";
		
		List<?> list=this.GetGtData(sql);
		
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50},"
				+ "{ \"header\":\"采集时间\",\"dataIndex\":\"calculateDate\"},"
				+ "{ \"header\":\"冲程(m)\",\"dataIndex\":\"stroke\"},"
				+ "{ \"header\":\"冲次(1/min)\",\"dataIndex\":\"spm\"},"
				+ "{ \"header\":\"最大载荷(kN)\",\"dataIndex\":\"fmax\"},"
				+ "{ \"header\":\"最小载荷(kN)\",\"dataIndex\":\"fmin\"},"
				+ "{ \"header\":\"电流平衡度(%)\",\"dataIndex\":\"iDegreeBalance\"},"
				+ "{ \"header\":\"电流平衡状态\",\"dataIndex\":\"iDegreeBalanceLevel\"},"
				+ "{ \"header\":\"功率平衡度(%)\",\"dataIndex\":\"wattDegreeBalance\"},"
				+ "{ \"header\":\"功率平衡状态\",\"dataIndex\":\"wattDegreeBalanceLevel\"}"
				+ "]";
		
		dynSbf.append("{\"success\":true,\"totalCount\":" + list.size() + ",\"wellName\":\""+wellName+"\",\"calculateDate\":\""+calculateDate+"\",\"columns\":"+columns+",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String positionCurveData="",loadCurveData="",powerCurveData="",currentCurveData="";
				SerializableClobProxy   proxy=null;
				CLOB realClob=null;
				Object[] obj = (Object[]) list.get(i);
				if(obj[13]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[13]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					positionCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				if(obj[14]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[14]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					loadCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				if(obj[15]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[15]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					powerCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				if(obj[16]!=null){
					proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[16]);
					realClob = (CLOB) proxy.getWrappedClob(); 
					currentCurveData=StringManagerUtils.CLOBtoString(realClob);
				}
				dynSbf.append("{ \"id\":\"" + obj[0] + "\",");
				dynSbf.append("\"wellName\":\"" + obj[1] + "\",");
				dynSbf.append("\"calculateDate\":\"" + obj[2] + "\",");
				dynSbf.append("\"stroke\":\""+obj[3]+"\",");
				dynSbf.append("\"spm\":\""+obj[4]+"\",");
				dynSbf.append("\"fmax\":\""+obj[5]+"\",");
				dynSbf.append("\"fmin\":\""+obj[6]+"\",");
				dynSbf.append("\"iDegreeBalanceLevel\":\"" + obj[7] + "\",");
				dynSbf.append("\"iDegreeBalance\":\"" + obj[8] + "\",");
				dynSbf.append("\"iDegreeBalanceAlarmLevel\":\"" + obj[9] + "\",");
				dynSbf.append("\"wattDegreeBalanceLevel\":\"" + obj[10] + "\",");
				dynSbf.append("\"wattDegreeBalance\":\"" + obj[11] + "\",");
				dynSbf.append("\"wattDegreeBalanceAlarmLevel\":\"" + obj[12] + "\",");
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
		return dynSbf.toString().replaceAll("null", "");
	}
	
	
	public String getDiagnosisTotalCurveData(Page pager,String orgId,String jh,String startDate,String endDate) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		String sql="select t.id, to_char(t.jssj,'yyyy-mm-dd'),t.jsdjrcyl,t.jsdjrcyl1,t.hsld,t.jsdjrcylbd "
				+ " from v_analysisaggregation t  "
				+ " where jh= '"+jh+"' and t.jssj between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') "
				+ " order by t.jssj";
		
		int totals = getTotalCountRows(sql);//获取总记录数
		List<?> list=this.GetGtData(sql);
		
		
		
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
	
	public String getAnalysisAndAcqAndControlData(String id)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql="select t.runTime,runTimeEfficiency,"
				+ " t.iDegreeBalance,t.iDegreeBalanceMax,t.iDegreeBalanceMin,t.wattDegreeBalance,t.wattDegreeBalanceMax,t.wattDegreeBalanceMin,"
				+ " t.liquidWeightProduction,t.liquidWeightProductionMax,t.liquidWeightProductionMin,"
				+ " t.oilWeightProduction,t.oilWeightProductionMax,t.oilWeightProductionMin,"
				+ " t.waterWeightProduction,t.waterWeightProductionMax,t.waterWeightProductionMin,"
				+ " t.waterCut_w,t.waterCutMax_w,t.waterCutMin_w,"
				+ " t.stroke,t.strokeMax,t.strokeMin,t.SPM,t.SPMMax,t.SPMMin,t.fullnesscoEfficient,t.fullnesscoEfficientMax,t.fullnesscoEfficientMin,"
				+ " t.pumpEff*100,t.pumpEffMax*100,t.pumpEffMin*100,"
				+ " t.systemEfficiency*100,t.systemEfficiencyMax*100,t.systemEfficiencyMin*100,"
				+ " t.surfaceSystemEfficiency*100,t.surfaceSystemEfficiencyMax*100,t.surfaceSystemEfficiencyMin*100,"
				+ " t.welldownSystemEfficiency*100,t.welldownSystemEfficiencyMax*100,t.welldownSystemEfficiencyMin*100,"
				+ " t.producingFluidLevel,t.producingFluidLevelMax,t.producingFluidLevelMin,t.pumpSettingDepth,t.pumpSettingDepthMax,t.pumpSettingDepthMin,t.submergence,t.submergenceMax,t.submergenceMin,"
				+ " t.productionGasOilRatio,t.productionGasOilRatioMax,t.productionGasOilRatioMin,"
				+ " t.Ia,t.IaMax,t.IaMin,t.Ib,t.IbMax,t.IbMin,t.Ic,t.IcMax,t.IcMin,"
				+ " t.Va,t.VaMax,t.VaMin,t.Vb,t.VbMax,t.VbMin,t.Vc,t.VcMax,t.VcMin,"
				+ " t.todayWattEnergy,"
				+ " t.wattSum,t.wattSumMax,t.wattSumMin,t.varSum,t.varSumMax,t.varSumMin,"
				+ " t.PFSum,t.PFSumMax,t.PFSumMin,"
				+ " t.rpm,t.rpmMax,t.rpMmin,"
				+ " t.runrange,t.workingconditionstring"
				+ " from t_outputwellaggregation t where id="+id;
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			result_json.append("\"runTime\":\""+obj[0]+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[1]+"\",");
			result_json.append("\"iDegreeBalance\":\""+obj[2]+"\",");
			result_json.append("\"iDegreeBalanceMax\":\""+obj[3]+"\",");
			result_json.append("\"iDegreeBalanceMin\":\""+obj[4]+"\",");
			result_json.append("\"wattDegreeBalance\":\""+obj[5]+"\",");
			result_json.append("\"wattDegreeBalanceMax\":\""+obj[6]+"\",");
			result_json.append("\"wattDegreeBalanceMin\":\""+obj[7]+"\",");
			result_json.append("\"liquidWeightProduction\":\""+obj[8]+"\",");
			result_json.append("\"liquidWeightProductionMax\":\""+obj[9]+"\",");
			result_json.append("\"liquidWeightProductionMin\":\""+obj[10]+"\",");
			result_json.append("\"oilWeightProduction\":\""+obj[11]+"\",");
			result_json.append("\"oilWeightProductionMax\":\""+obj[12]+"\",");
			result_json.append("\"oilWeightProductionMin\":\""+obj[13]+"\",");
			result_json.append("\"waterWeightProduction\":\""+obj[14]+"\",");
			result_json.append("\"waterWeightProductionMax\":\""+obj[15]+"\",");
			result_json.append("\"waterWeightProductionMin\":\""+obj[16]+"\",");
			result_json.append("\"waterCut\":\""+obj[17]+"\",");
			result_json.append("\"waterCutMax\":\""+obj[18]+"\",");
			result_json.append("\"waterCutMin\":\""+obj[19]+"\",");
			result_json.append("\"stroke\":\""+obj[20]+"\",");
			result_json.append("\"strokeMax\":\""+obj[21]+"\",");
			result_json.append("\"strokeMin\":\""+obj[22]+"\",");
			result_json.append("\"SPM\":\""+obj[23]+"\",");
			result_json.append("\"SPMMax\":\""+obj[24]+"\",");
			result_json.append("\"SPMMin\":\""+obj[25]+"\",");
			result_json.append("\"fullnesscoEfficient\":\""+obj[26]+"\",");
			result_json.append("\"fullnesscoEfficientMax\":\""+obj[27]+"\",");
			result_json.append("\"fullnesscoEfficientMin\":\""+obj[28]+"\",");
			result_json.append("\"pumpEff\":\""+obj[29]+"\",");
			result_json.append("\"pumpEffMax\":\""+obj[30]+"\",");
			result_json.append("\"pumpEffMin\":\""+obj[31]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[32]+"\",");
			result_json.append("\"systemEfficiencyMax\":\""+obj[33]+"\",");
			result_json.append("\"systemEfficiencyMin\":\""+obj[34]+"\",");
			result_json.append("\"surfaceSystemEfficiency\":\""+obj[35]+"\",");
			result_json.append("\"surfaceSystemEfficiencyMax\":\""+obj[36]+"\",");
			result_json.append("\"surfaceSystemEfficiencyMin\":\""+obj[37]+"\",");
			result_json.append("\"welldownSystemEfficiency\":\""+obj[38]+"\",");
			result_json.append("\"welldownSystemEfficiencyMax\":\""+obj[39]+"\",");
			result_json.append("\"welldownSystemEfficiencyMin\":\""+obj[40]+"\",");
			result_json.append("\"producingFluidLevel\":\""+obj[41]+"\",");
			result_json.append("\"producingFluidLevelMax\":\""+obj[42]+"\",");
			result_json.append("\"producingFluidLevelMin\":\""+obj[43]+"\",");
			result_json.append("\"pumpSettingDepth\":\""+obj[44]+"\",");
			result_json.append("\"pumpSettingDepthMax\":\""+obj[45]+"\",");
			result_json.append("\"pumpSettingDepthMin\":\""+obj[46]+"\",");
			result_json.append("\"submergence\":\""+obj[47]+"\",");
			result_json.append("\"submergenceMax\":\""+obj[48]+"\",");
			result_json.append("\"submergenceMin\":\""+obj[49]+"\",");
			result_json.append("\"productionGasOilRatio\":\""+obj[50]+"\",");
			result_json.append("\"productionGasOilRatioMax\":\""+obj[51]+"\",");
			result_json.append("\"productionGasOilRatioMin\":\""+obj[52]+"\",");
			
			result_json.append("\"Ia\":\""+obj[53]+"\",");
			result_json.append("\"IaMax\":\""+obj[54]+"\",");
			result_json.append("\"IaMin\":\""+obj[55]+"\",");
			result_json.append("\"Ib\":\""+obj[56]+"\",");
			result_json.append("\"IbMax\":\""+obj[57]+"\",");
			result_json.append("\"IbMin\":\""+obj[58]+"\",");
			result_json.append("\"Ic\":\""+obj[59]+"\",");
			result_json.append("\"IcMax\":\""+obj[60]+"\",");
			result_json.append("\"IcMin\":\""+obj[61]+"\",");
			result_json.append("\"Va\":\""+obj[62]+"\",");
			result_json.append("\"VaMax\":\""+obj[63]+"\",");
			result_json.append("\"VaMin\":\""+obj[64]+"\",");
			result_json.append("\"Vb\":\""+obj[65]+"\",");
			result_json.append("\"VbMax\":\""+obj[66]+"\",");
			result_json.append("\"VbMin\":\""+obj[67]+"\",");
			result_json.append("\"Vc\":\""+obj[68]+"\",");
			result_json.append("\"VcMax\":\""+obj[69]+"\",");
			result_json.append("\"VcMin\":\""+obj[70]+"\",");
			result_json.append("\"todayWattEnergy\":\""+obj[71]+"\",");
			result_json.append("\"wattSum\":\""+obj[72]+"\",");
			result_json.append("\"wattSumMax\":\""+obj[73]+"\",");
			result_json.append("\"wattSumMin\":\""+obj[74]+"\",");
			result_json.append("\"varSum\":\""+obj[75]+"\",");
			result_json.append("\"varSumMax\":\""+obj[76]+"\",");
			result_json.append("\"varSumMin\":\""+obj[77]+"\",");
			result_json.append("\"PFSum\":\""+obj[78]+"\",");
			result_json.append("\"PFSumMax\":\""+obj[79]+"\",");
			result_json.append("\"PFSumMin\":\""+obj[80]+"\",");
			result_json.append("\"rpm\":\""+obj[81]+"\",");
			result_json.append("\"rpmMax\":\""+obj[82]+"\",");
			result_json.append("\"rpMmin\":\""+obj[83]+"\",");
			result_json.append("\"runRange\":\""+obj[84]+"\",");
			result_json.append("\"workingConditionString\":\""+(obj[85]+"").replaceAll("<br/>", ";")+"\"");
			
		}
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getDiagnosisTotalDataCurveData(String wellName,String startDate,String endDate,String itemName,String itemCode) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		if(!"runTime".equalsIgnoreCase(itemCode)&&!"runTimeEfficiency".equalsIgnoreCase(itemCode)&&!"todayWattEnergy".equalsIgnoreCase(itemCode)){
			if("pumpEff".equalsIgnoreCase(itemCode)||"surfaceSystemEfficiency".equalsIgnoreCase(itemCode)||"welldownSystemEfficiency".equalsIgnoreCase(itemCode)||"systemEfficiency".equalsIgnoreCase(itemCode)){
				itemCode="t."+itemCode+"*100,t."+itemCode+"max*100,t."+itemCode+"min*100";
			}else{
				itemCode="t."+itemCode+",t."+itemCode+"Max,t."+itemCode+"Min";
			}
		}else{
			itemCode="t."+itemCode;
		}
		String sql="select to_char(t.calculateDate,'yyyy-mm-dd'),"+itemCode+" from t_outputwellaggregation t,t_wellinformation t007 "
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
	
	
	public String getScrewPumpDailyAnalysiCurveData(String jssj,String jh) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		
		String sql="select to_char(t.jssj,'yyyy-mm-dd'),t.rpm,t.currenta,t.currentb,t.currentc,t.voltagea,t.voltageb,t.voltagec "
				+ " from t_outputwellaggregation t,t_wellinformation t007 "
				+ " where t.jbh=t007.jlbh and t007.jh='"+jh+"' and t.jssj between to_date('"+jssj+"','yyyy-mm-dd')-30 and to_date('"+jssj+"','yyyy-mm-dd') "
				+ " order by t.jssj";
		
		List<?> list=this.findCallSql(sql);
		
		dynSbf.append("{\"success\":true,\"totalCount\":" + list.size() + ",\"jh\":\""+jh+"\",\"jssj\":\""+jssj+"\",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				dynSbf.append("{ \"jssj\":\"" + obj[0] + "\",");
				dynSbf.append("\"rpm\":\""+obj[1]+"\",");
				dynSbf.append("\"currenta\":\""+obj[2]+"\",");
				dynSbf.append("\"currentb\":\""+obj[3]+"\",");
				dynSbf.append("\"currentc\":\""+obj[4]+"\",");
				dynSbf.append("\"voltagea\":\""+obj[5]+"\",");
				dynSbf.append("\"voltageb\":\""+obj[6]+"\",");
				dynSbf.append("\"voltagec\":\""+obj[7]+"\"},");
			}
			dynSbf.deleteCharAt(dynSbf.length() - 1);
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
