package com.gao.service.diagnosis;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gao.dao.BaseDao;
import com.gao.model.AlarmShowStyle;
import com.gao.model.DiagnosisAnalysisStatistics;
import com.gao.model.data.DataDictionary;
import com.gao.service.base.BaseService;
import com.gao.service.base.CommonDataService;
import com.gao.service.data.DataitemsInfoService;
import com.gao.tast.EquipmentDriverServerTast;
import com.gao.utils.Config;
import com.gao.utils.ConfigFile;
import com.gao.utils.DataModelMap;
import com.gao.utils.Page;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;

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
@Component("diagnosisAnalysisOnlyService")
public class DiagnosisAnalysisOnlyService<T> extends BaseService<T> {

	private BaseDao dao;
	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	
	/**
	 * <p>描述：采出井实时评价井列表</p>
	 * @param orgId
	 * @param jh
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	public String getProductionWellRTAnalysisWellList(String orgId, String wellName, Page pager,String type,String wellType,String startDate,String endDate,String statValue)
			throws Exception {
		DataDictionary ddic = null;
		String columns= "";
		String sql="";
		String sqlHis="";
		String finalSql="";
		String sqlAll="";
		String ddicName="";
		String tableName_latest="viw_rpc_comprehensive_latest";
		String tableName_hist="viw_rpc_comprehensive_hist";
		String typeColumnName="workingConditionName";
		ConfigFile configFile=Config.getInstance().configFile;
		if("1".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeFSDiagram";
			}
			typeColumnName="workingConditionName";
		}else if("2".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeProdDist";
			}else{//默认为抽油机
				ddicName="realtimeProdDist";
			}
			typeColumnName="liquidWeightProductionlevel";
			if(configFile.getOthers().getProductionUnit()!=0){
				typeColumnName="liquidVolumeProductionlevel";
			}
		}else if("3".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimePowerBalance";
			}
			typeColumnName="wattDegreeBalanceName";
		}else if("4".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeCurrentBalance";
			}
			typeColumnName="iDegreeBalanceName";
		}else if("5".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeRunStatus";
			}else{//默认为抽油机
				ddicName="realtimeRunStatus";
			}
			typeColumnName="runStatusName";
		}else if("6".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeTimeDist";
			}else{//默认为抽油机
				ddicName="realtimeTimeDist";
			}
			typeColumnName="runtimeEfficiencyLevel";
		}else if("7".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeSystemEff";
			}else{//默认为抽油机
				ddicName="realtimeSystemEff";
			}
			typeColumnName="systemEfficiencyLevel";
		}else if("8".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeSurfaceEff";
			}
			typeColumnName="surfaceSystemEfficiencyLevel";
		}else if("9".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeDownholeEff";
			}
			typeColumnName="wellDownSystemEfficiencyLevel";
		}else if("10".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimePowerDist";
			}else{//默认为抽油机
				ddicName="realtimePowerDist";
			}
			typeColumnName="todayWattEnergyLevel";
		}else if("11".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeCommStatus";
			}else{//默认为抽油机
				ddicName="realtimeCommStatus";
			}
			typeColumnName="commStatusName";
		}else if("12".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeCommDist";
			}else{//默认为抽油机
				ddicName="realtimeCommDist";
			}
			typeColumnName="commtimeefficiencyLevel";
		}else if("13".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeETValue";
			}
			typeColumnName="workingConditionName_E";
		}else{
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeFSDiagram";
			}
			typeColumnName="workingConditionName";
		}
		
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		
		columns = ddic.getTableHeader();
		
		if("400".equals(wellType)){//螺杆泵井
			tableName_latest="viw_pcp_comprehensive_latest";
			tableName_hist="viw_pcp_comprehensive_hist";
			sql=ddic.getSql()+",workingConditionString_E,videourl,workingConditionAlarmLevel_E,"
					+ " commStatus,runStatus,commAlarmLevel,runAlarmLevel ";
			sqlHis=ddic.getSql()+",workingConditionString_E,videourl,workingConditionAlarmLevel_E,"
					+ " commStatus,runStatus,commAlarmLevel,runAlarmLevel ";
		}else{//默认为抽油机
			tableName_latest="viw_rpc_comprehensive_latest";
			tableName_hist="viw_rpc_comprehensive_hist";
			sql=ddic.getSql()+",workingConditionString_E,videourl,workingConditionAlarmLevel,workingConditionAlarmLevel_E,"
					+ " commStatus,runStatus,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel ";
			sqlHis=ddic.getSql()+",workingConditionString_E,videourl,workingConditionAlarmLevel,workingConditionAlarmLevel_E,"
					+ " commStatus,runStatus,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel ";
		}
		
		
		sql+= " from "+tableName_latest+" t where t.org_id in("+orgId+")";
		sqlHis+= " from "+tableName_hist+" t where t.org_id in("+orgId+")";
		
		
		if(StringManagerUtils.isNotNull(statValue)){
			sql+=" and "+typeColumnName+"='"+statValue+"' ";
		}
		sql+=" order by t.sortNum, t.wellName";
		sqlHis+=" and to_date(to_char(t.acquisitionTime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') "
				+ "and  t.wellName = '" + wellName.trim() + "' order by t.acquisitionTime desc";
		
		if(StringManagerUtils.isNotNull(wellName.trim())){
			sqlAll=sqlHis;
		}else{
			sqlAll=sql;
		}
		
		int maxvalue=pager.getLimit()+pager.getStart();
		finalSql="select * from   ( select a.*,rownum as rn from ("+sqlAll+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		String getResult = this.findCustomPageBySqlEntity(sqlAll,finalSql, columns, 20 + "", pager);
		return getResult;
	}
	
	
	public String exportProductionWellRTAnalysisDataExcel(String orgId, String wellName, Page pager,String type,String wellType,String startDate,String endDate,String statValue)
			throws Exception {
		DataDictionary ddic = null;
		String columns= "";
		String sql="";
		String sqlHis="";
		String finalSql="";
		String sqlAll="";
		String ddicName="";
		String tableName_latest="viw_rpc_comprehensive_latest";
		String tableName_hist="viw_rpc_comprehensive_hist";
		String typeColumnName="workingConditionName";
		ConfigFile configFile=Config.getInstance().configFile;
		if("1".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeFSDiagram";
			}
			typeColumnName="workingConditionName";
		}else if("2".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeProdDist";
			}else{//默认为抽油机
				ddicName="realtimeProdDist";
			}
			typeColumnName="liquidWeightProductionlevel";
			if(configFile.getOthers().getProductionUnit()!=0){
				typeColumnName="liquidVolumeProductionlevel";
			}
		}else if("3".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimePowerBalance";
			}
			typeColumnName="wattDegreeBalanceName";
		}else if("4".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeCurrentBalance";
			}
			typeColumnName="iDegreeBalanceName";
		}else if("5".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeRunStatus";
			}else{//默认为抽油机
				ddicName="realtimeRunStatus";
			}
			typeColumnName="runStatusName";
		}else if("6".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeTimeDist";
			}else{//默认为抽油机
				ddicName="realtimeTimeDist";
			}
			typeColumnName="runtimeEfficiencyLevel";
		}else if("7".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeSystemEff";
			}else{//默认为抽油机
				ddicName="realtimeSystemEff";
			}
			typeColumnName="systemEfficiencyLevel";
		}else if("8".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeSurfaceEff";
			}
			typeColumnName="surfaceSystemEfficiencyLevel";
		}else if("9".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeDownholeEff";
			}
			typeColumnName="wellDownSystemEfficiencyLevel";
		}else if("10".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimePowerDist";
			}else{//默认为抽油机
				ddicName="realtimePowerDist";
			}
			typeColumnName="todayWattEnergyLevel";
		}else if("11".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeCommStatus";
			}else{//默认为抽油机
				ddicName="realtimeCommStatus";
			}
			typeColumnName="commStatusName";
		}else if("12".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeCommDist";
			}else{//默认为抽油机
				ddicName="realtimeCommDist";
			}
			typeColumnName="commtimeefficiencyLevel";
		}else if("13".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeETValue";
			}
			typeColumnName="workingConditionName_E";
		}else{
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeFSDiagram";
			}
			typeColumnName="workingConditionName";
		}
		
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		
		columns = ddic.getTableHeader();
		
		if("400".equals(wellType)){//螺杆泵井
			tableName_latest="viw_pcp_comprehensive_latest";
			tableName_hist="viw_pcp_comprehensive_hist";
			sql=ddic.getSql()+",workingConditionString_E,videourl,workingConditionAlarmLevel_E,"
					+ " commStatus,runStatus,commAlarmLevel,runAlarmLevel ";
			sqlHis=ddic.getSql()+",workingConditionString_E,videourl,workingConditionAlarmLevel_E,"
					+ " commStatus,runStatus,commAlarmLevel,runAlarmLevel ";
		}else{//默认为抽油机
			tableName_latest="viw_rpc_comprehensive_latest";
			tableName_hist="viw_rpc_comprehensive_hist";
			sql=ddic.getSql()+",workingConditionString_E,videourl,workingConditionAlarmLevel,workingConditionAlarmLevel_E,"
					+ " commStatus,runStatus,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel ";
			sqlHis=ddic.getSql()+",workingConditionString_E,videourl,workingConditionAlarmLevel,workingConditionAlarmLevel_E,"
					+ " commStatus,runStatus,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel ";
		}
		
		
		sql+= " from "+tableName_latest+" t where t.org_id in("+orgId+")";
		sqlHis+= " from "+tableName_hist+" t where t.org_id in("+orgId+")";
		
		
		if(StringManagerUtils.isNotNull(statValue)){
			sql+=" and "+typeColumnName+"='"+statValue+"' ";
		}
		sql+=" order by t.sortNum, t.wellName";
		sqlHis+=" and to_date(to_char(t.acquisitionTime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') "
				+ "and  t.wellName = '" + wellName.trim() + "' order by t.acquisitionTime desc";
		
		if(StringManagerUtils.isNotNull(wellName.trim())){
			sqlAll=sqlHis;
		}else{
			sqlAll=sql;
		}
		finalSql=sqlAll;
		String getResult = this.findExportDataBySqlEntity(sqlAll,finalSql, columns, 20 + "", pager);
		
		return getResult;
	}

	public String queryStatisticsAmountJh(Page pager,String orgId,String jh) throws Exception {
		StringBuffer sqlCuswhere = new StringBuffer();
		StringBuffer result_json = new StringBuffer();
		String sql = "select  v.jh as jh From tbl_wellinformation   v, tbl_org  o,t_wellorder t where 1=1 and v.jh=t.jh and v.jlx=101  and v.dwbh=o.org_Code and o.org_Id in ("
				+ orgId + "  ) ";//order by t.pxbh, v.jh";
		if (StringUtils.isNotBlank(jh)) {
			//jh=new String(jh.getBytes("iso-8859-1"),"utf-8");
			sql += " and v.jh like '%" + jh + "%'";
		} 
		sql+=" order by t.pxbh, v.jh";
		sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
		sqlCuswhere.append(""+sql);
		int maxvalue=pager.getLimit()+pager.getStart();
		sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
		sqlCuswhere.append(" where rn >"+pager.getStart());
		String finalsql=sqlCuswhere.toString();
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(finalsql);
		result_json.append("{\"totals\":"+totals+",\"list\":[");
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] obj = (Object[]) list.get(i);
				result_json.append("{\"jh\":\"" + obj[0].toString()+ "\"}");
				if (i != list.size() - 1) {
					result_json.append(",");
				}
			}
		}
		result_json.append("]}");
		String json = result_json.toString();
		return json;
	}

	public List<T> getbyIdPage(String orgId, String jh) throws Exception {
		// String orgIds = this.getBaseDao().getUserOrgIds(orgId);
		String tempHql = " select v from DiagnosisAnalysisOnly v  ,Org as o where 1=1 and v.dwbh=o.orgCode and o.orgId in("
				+ orgId + ") ";
		if (null != jh && !("".equals(jh))) {
			tempHql += "  and  v.jh like  '%" + jh + "%' ";
		}
		String hql = tempHql;
		return this.getBaseDao().find(hql);

	}

	public int getTotalRowns(String orgId, String jh) {
		// String orgIds = this.getBaseDao().getUserOrgIds(orgId);
		String tempHql = " select v from DiagnosisAnalysisOnly v  ,Org as o where 1=1 and v.dwbh=o.orgCode and o.orgId in("
				+ orgId + ") ";
		if (null != jh && !("".equals(jh))) {
			tempHql += "  and  v.jh like  '%" + jh + "%' ";
		}
		String hql = tempHql;
		return this.getBaseDao().getRecordCountRows(hql);
	}
	
	public String querySingleDetailsWellBoreChartsData(int id,String wellName,String selectedWellName) throws SQLException, IOException{
		byte[] bytes; 
		ConfigFile configFile=Config.getInstance().configFile;
		BufferedInputStream bis = null;
        StringBuffer dataSbf = new StringBuffer();
        StringBuffer pumpFSDiagramStrBuff = new StringBuffer();
        String tableName="tbl_rpc_diagram_latest";
        if(StringManagerUtils.isNotNull(selectedWellName)){
        	tableName="tbl_rpc_diagram_hist";
        }else{
        	tableName="tbl_rpc_diagram_latest";
        }
        String prodCol=" liquidweightproduction";
		if(configFile.getOthers().getProductionUnit()!=0){
			prodCol=" liquidVolumetricProduction";
		}
        String sql="select well.wellName as wellName, to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss') as acquisitiontime,"
        		+ " t.pumpfsdiagram,"
        		+ " t.upperloadline,t.lowerloadline, t.fmax,t.fmin,t.stroke,t.spm, "
        		+ " t."+prodCol+","
        		+ " status.workingconditionname,status.workingconditioncode, "
        		+ " t.rodstring,"
        		+ " t.pumpeff1*100 as pumpeff1, t.pumpeff2*100 as pumpeff2, t.pumpeff3*100 as pumpeff3, t.pumpeff4*100 as pumpeff4,"
        		+ " t.position_curve,t.load_curve,t.wellboreslice,"
        		+ " t2.x,t2.y,t2.z,"
        		+ " decode(t.productiondataid,0,prod2.reservoirdepth,null,prod2.reservoirdepth,prod.reservoirdepth) "
        		+ " from "+tableName+" t"
        		+ " left outer join tbl_wellinformation well on t.wellid=well.id"
        		+ " left outer join tbl_wellboretrajectory t2 on t.wellid=t2.wellid"
        		+ " left outer join tbl_rpc_worktype status on t.workingconditioncode=status.workingconditioncode"
        		+ " left outer join tbl_rpc_productiondata_hist prod on t.productiondataid=prod.id"
        		+ " left outer join tbl_rpc_productiondata_latest prod2 on prod2.wellid=t.wellid"
        		+ " where 1=1 ";
        if(StringManagerUtils.isNotNull(selectedWellName)){
        	sql+=" and t.id="+id;
        }else{
        	sql+=" and well.wellName='"+wellName+"'";
        }
		List<?> list=this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			String positionCurveData="";
			String loadCurveData="";
			String pumpFSDiagram="";
			String wellboreSlice="";
			String wellboreSlice_MeasuringDepth="",wellboreSlice_P="",wellboreSlice_Bo="",wellboreSlice_GLRis="";
			String wellboreTrajectoryX="",wellboreTrajectoryY="",wellboreTrajectoryZ="";
			SerializableClobProxy   proxy=null;
			CLOB realClob=null;
			if(obj[2]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[2]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				pumpFSDiagram=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[17]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[17]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				positionCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[18]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[18]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				loadCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[19]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[19]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				wellboreSlice=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[20]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[20]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				wellboreTrajectoryX=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[21]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[21]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				wellboreTrajectoryY=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[22]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[22]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				wellboreTrajectoryZ=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(StringManagerUtils.isNotNull(wellboreSlice)){
				String wellboreSliceArr[]=wellboreSlice.split(";");
				wellboreSlice_MeasuringDepth=wellboreSliceArr[1];
				wellboreSlice_P=wellboreSliceArr[5];
				wellboreSlice_Bo=wellboreSliceArr[6];
				wellboreSlice_GLRis=wellboreSliceArr[7];
			}
			
			String rodStressRatio1="0",rodStressRatio2="0",rodStressRatio3="0",rodStressRatio4="0";
			
			if("1232".equals(obj[11]+"") || !StringManagerUtils.isNotNull(pumpFSDiagram)){//采集异常
				String positionCurveDataArr[]=positionCurveData.split(",");
				String loadCurveDataArr[]=loadCurveData.split(",");
				for(int i=0;i<positionCurveDataArr.length;i++){
					pumpFSDiagramStrBuff.append(positionCurveDataArr[i]+",").append(loadCurveDataArr[i]+",");
				}
			}else{
				String arrbgt[]=pumpFSDiagram.split(";");  // 以;为界放入数组
		        for(int i=2;i<(arrbgt.length);i++){
		        	String arrbgtdata[]=arrbgt[i].split(",");  // 以,为界放入数组
		        	for(int j=0;j<arrbgtdata.length;j+=2){
		        		pumpFSDiagramStrBuff.append(arrbgtdata[j] + ",");
		        		pumpFSDiagramStrBuff.append(arrbgtdata[j+1] + ",");
			        }
		        	pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
		        	pumpFSDiagramStrBuff.append("#");
		        }
		        if(obj[12]!=null){
		        	String rodDataArr[]=obj[12].toString().split(";");
			        for(int i=1;i<rodDataArr.length;i++){
			        	if(i==1&&rodDataArr[i].split(",").length==6){
			        		rodStressRatio1=rodDataArr[i].split(",")[5];
			        	}else if(i==2&&rodDataArr[i].split(",").length==6){
			        		rodStressRatio2=rodDataArr[i].split(",")[5];
			        	}if(i==3&&rodDataArr[i].split(",").length==6){
			        		rodStressRatio3=rodDataArr[i].split(",")[5];
			        	}if(i==4&&rodDataArr[i].split(",").length==6){
			        		rodStressRatio4=rodDataArr[i].split(",")[5];
			        	}
			        }
		        }
			}
	        if(pumpFSDiagramStrBuff.toString().endsWith(",")){
	        	pumpFSDiagramStrBuff=pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
	        }
	        String pumpFSDiagramData = pumpFSDiagramStrBuff.toString();
	        
	        dataSbf.append("{success:true,");
	        dataSbf.append("wellName:\""+wellName+"\",");           // 井名
	        dataSbf.append("acquisitionTime:\""+obj[1]+"\",");         // 时间
	        dataSbf.append("upperLoadLine:\""+obj[3]+"\",");         // 理论上载荷
	        dataSbf.append("lowerLoadLine:\""+obj[4]+"\",");         // 理论下载荷
	        dataSbf.append("fmax:\""+obj[5]+"\",");         // 最大载荷
	        dataSbf.append("fmin:\""+obj[6]+"\",");         // 最小载荷
	        dataSbf.append("stroke:\""+obj[7]+"\",");         // 冲程
	        dataSbf.append("spm:\""+obj[8]+"\",");         // 冲次
	        dataSbf.append("liquidProduction:\""+obj[9]+"\",");         // 日产液量
	        dataSbf.append("workingConditionName:\""+obj[10]+"\",");         // 工况类型
	        dataSbf.append("workingConditionCode:\""+obj[11]+"\",");         // 工况代码
	        
	        dataSbf.append("rodStressRatio1:"+rodStressRatio1+",");       // 一级应力百分比
	        dataSbf.append("rodStressRatio2:"+rodStressRatio2+",");       // 二级应力百分比 
	        dataSbf.append("rodStressRatio3:"+rodStressRatio3+",");           // 三级应力百分比
	        dataSbf.append("rodStressRatio4:"+rodStressRatio4+",");           // 四级应力百分比
	        
	        dataSbf.append("pumpEff1:"+StringManagerUtils.stringToFloat(obj[13]==null?"":obj[13].toString(),1)+",");       // 冲程损失系数
	        dataSbf.append("pumpEff2:"+StringManagerUtils.stringToFloat(obj[14]==null?"":obj[14].toString().toString(),1)+",");       // 充满系数
	        dataSbf.append("pumpEff3:"+StringManagerUtils.stringToFloat(obj[15]==null?"":obj[15].toString().toString(),1)+",");           // 漏失系数
	        dataSbf.append("pumpEff4:"+StringManagerUtils.stringToFloat(obj[16]==null?"":obj[16].toString().toString(),1)+",");           // 液体收缩系数
	        dataSbf.append("pumpFSDiagramData:\""+pumpFSDiagramData+"\",");         // 泵功图数据
	        dataSbf.append("positionCurveData:\""+positionCurveData+"\",");         
	        dataSbf.append("loadCurveData:\""+loadCurveData+"\","); 
	        //井身切片
	        dataSbf.append("wellboreSlice_MeasuringDepth:\""+wellboreSlice_MeasuringDepth+"\",");        
	        dataSbf.append("wellboreSlice_P:\""+wellboreSlice_P+"\",");        
	        dataSbf.append("wellboreSlice_Bo:\""+wellboreSlice_Bo+"\","); 
	        dataSbf.append("wellboreSlice_GLRis:\""+wellboreSlice_GLRis+"\","); 
	        //井身轨迹
	        dataSbf.append("wellboreTrajectoryX:\""+wellboreTrajectoryX+"\","); 
	        dataSbf.append("wellboreTrajectoryY:\""+wellboreTrajectoryY+"\",");
	        dataSbf.append("wellboreTrajectoryZ:\""+wellboreTrajectoryZ+"\","); 
	        dataSbf.append("reservoirDepth:\""+obj[23]+"\""); 
	        dataSbf.append("}");
		}else{
			dataSbf.append("{success:true,");
			dataSbf.append("wellName:\""+wellName+"\",");
	        dataSbf.append("acquisitionTime:\"\",");
	        dataSbf.append("upperLoadLine:\"\",");  
	        dataSbf.append("lowerLoadLine:\"\","); 
	        dataSbf.append("fmax:\"\",");  
	        dataSbf.append("fmin:\"\",");
	        dataSbf.append("stroke:\"\",");  
	        dataSbf.append("spm:\"\","); 
	        dataSbf.append("liquidProduction:\"\",");  
	        dataSbf.append("workingConditionName:\"\",");
	        dataSbf.append("workingConditionCode:\"\",");  
	        dataSbf.append("rodStressRatio1:\"\","); 
	        dataSbf.append("rodStressRatio2:\"\",");  
	        dataSbf.append("rodStressRatio3:\"\",");
	        dataSbf.append("rodStressRatio4:\"\",");  
	        dataSbf.append("pumpEff1:\"\","); 
	        dataSbf.append("pumpEff2:\"\",");  
	        dataSbf.append("pumpEff3:\"\",");
	        dataSbf.append("pumpEff4:\"\",");  
	        dataSbf.append("pumpFSDiagramData:\"\",");
	        dataSbf.append("positionCurveData:\"\",");
	        dataSbf.append("loadCurveData:\"\",");
	        dataSbf.append("wellboreSlice_MeasuringDepth:\"\",");        
	        dataSbf.append("wellboreSlice_P:\"\",");        
	        dataSbf.append("wellboreSlice_Bo:\"\","); 
	        dataSbf.append("wellboreSlice_GLRis:\"\","); 
	        dataSbf.append("wellboreTrajectoryX:\"\","); 
	        dataSbf.append("wellboreTrajectoryY:\"\","); 
	        dataSbf.append("wellboreTrajectoryZ:\"\""); 
	        dataSbf.append("}");
		}
		return dataSbf.toString().replaceAll("null", "");
	}

	public String querySingleDetailsSurfaceData(int id,String wellName,String selectedWellName) throws SQLException, IOException{
		byte[] bytes; 
		ConfigFile configFile=Config.getInstance().configFile;
		BufferedInputStream bis = null;
        StringBuffer dataSbf = new StringBuffer();
        StringBuffer pumpFSDiagramStrBuff = new StringBuffer();
        String tableName="tbl_rpc_diagram_latest";
        if(StringManagerUtils.isNotNull(selectedWellName)){
        	tableName="tbl_rpc_diagram_hist";
        }else{
        	tableName="tbl_rpc_diagram_latest";
        }
        
        String sql="select well.wellName, to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss') as acquisitiontime,"
        		+ " t.upstrokewattmax,t.downstrokewattmax,t.wattdegreebalance,t.upstrokeimax,t.downstrokeimax,t.idegreebalance,t.deltaRadius*100,"
        		+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
        		+ " t.ia_curve,t.ib_curve,t.ic_curve,"
        		+ " t.crankangle,t.loadtorque,t.cranktorque,t.currentbalancetorque,t.currentnettorque,"
        		+ " t.expectedbalancetorque,t.expectednettorque,"
        		+ " t.polishrodV,t.polishrodA "
        		+ " from "+tableName+" t"
        		+ " left outer join tbl_wellinformation well on t.wellid=well.id"
        		+ " where 1=1 ";
        if(StringManagerUtils.isNotNull(selectedWellName)){
        	sql+=" and t.id="+id;
        }else{
        	sql+=" and well.wellName='"+wellName+"'";
        }
		List<?> list=this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			String positionCurveData="";
			String loadCurveData="";
			String wattCurveData="";
			String iCurveData="";
			String IaCurveData="";
			String IbCurveData="";
			String IcCurveData="";
			String crankAngle="",loadRorque="",crankTorque="",currentBalanceTorque="",currentNetTorque="",expectedBalanceTorque="",expectedNetTorque="";
			String polishrodV="",polishrodA="";
			SerializableClobProxy   proxy=null;
			CLOB realClob=null;
			
			if(obj[9]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[9]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				positionCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[10]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[10]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				loadCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[11]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[11]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				wattCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[12]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[12]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				iCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[13]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[13]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				IaCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[14]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[14]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				IbCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[15]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[15]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				IcCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[16]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[16]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				crankAngle=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[17]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[17]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				loadRorque=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[18]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[18]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				crankTorque=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[19]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[19]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				currentBalanceTorque=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[20]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[20]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				currentNetTorque=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[21]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[21]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				expectedBalanceTorque=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[22]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[22]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				expectedNetTorque=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[23]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[23]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				polishrodV=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[24]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[24]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				polishrodA=StringManagerUtils.CLOBtoString(realClob);
			}
			
	        dataSbf.append("{success:true,");
	        dataSbf.append("wellName:\""+wellName+"\",");           // 井名
	        dataSbf.append("acquisitionTime:\""+obj[1]+"\",");         // 时间
	        dataSbf.append("upStrokeWattMax:\""+obj[2]+"\",");         
	        dataSbf.append("downStrokeWattMax:\""+obj[3]+"\",");
	        dataSbf.append("wattDegreeBalance:\""+obj[4]+"\",");
	        dataSbf.append("upStrokeIMax:\""+obj[5]+"\",");
	        dataSbf.append("downStrokeIMax:\""+obj[6]+"\",");
	        dataSbf.append("iDegreeBalance:\""+obj[7]+"\",");
	        dataSbf.append("deltaRadius:\""+obj[8]+"\",");
	        dataSbf.append("positionCurveData:\""+positionCurveData+"\",");
	        dataSbf.append("loadCurveData:\""+loadCurveData+"\",");
	        dataSbf.append("powerCurveData:\""+wattCurveData+"\",");
	        dataSbf.append("currentCurveData:\""+iCurveData+"\",");
	        dataSbf.append("IaCurveData:\""+IaCurveData+"\","); 
	        dataSbf.append("IbCurveData:\""+IbCurveData+"\","); 
	        dataSbf.append("IcCurveData:\""+IcCurveData+"\","); 
	        dataSbf.append("crankAngle:\""+crankAngle+"\","); 
	        dataSbf.append("loadRorque:\""+loadRorque+"\","); 
	        dataSbf.append("crankTorque:\""+crankTorque+"\","); 
	        dataSbf.append("currentBalanceTorque:\""+currentBalanceTorque+"\","); 
	        dataSbf.append("currentNetTorque:\""+currentNetTorque+"\","); 
	        dataSbf.append("expectedBalanceTorque:\""+expectedBalanceTorque+"\","); 
	        dataSbf.append("expectedNetTorque:\""+expectedNetTorque+"\","); 
	        dataSbf.append("polishrodV:\""+polishrodV+"\","); 
	        dataSbf.append("polishrodA:\""+polishrodA+"\""); 
	        dataSbf.append("}");
	        
		}else{
			dataSbf.append("{success:true,");
	        dataSbf.append("wellName:\""+wellName+"\",");           // 井名
	        dataSbf.append("acquisitionTime:\"\",");         // 时间
	        dataSbf.append("upStrokeWattMax:\"\",");         
	        dataSbf.append("downStrokeWattMax:\"\",");
	        dataSbf.append("wattDegreeBalance:\"\",");
	        dataSbf.append("upStrokeIMax:\"\",");
	        dataSbf.append("downStrokeIMax:\"\",");
	        dataSbf.append("iDegreeBalance:\"\",");
	        dataSbf.append("deltaRadius:\"\",");
	        dataSbf.append("positionCurveData:\"\",");
	        dataSbf.append("loadCurveData:\"\",");
	        dataSbf.append("powerCurveData:\"\",");
	        dataSbf.append("currentCurveData:\"\",");
	        dataSbf.append("IaCurveData:\"\","); 
	        dataSbf.append("IbCurveData:\"\","); 
	        dataSbf.append("IcCurveData:\"\","); 
	        dataSbf.append("crankAngle:\"\","); 
	        dataSbf.append("loadRorque:\"\","); 
	        dataSbf.append("crankTorque:\"\","); 
	        dataSbf.append("currentBalanceTorque:\"\","); 
	        dataSbf.append("currentNetTorque:\"\","); 
	        dataSbf.append("expectedBalanceTorque:\"\","); 
	        dataSbf.append("expectedNetTorque:\"\","); 
	        dataSbf.append("polishrodV:\"\","); 
	        dataSbf.append("polishrodA:\"\""); 
		}
		return dataSbf.toString().replaceAll("null", "");
	}
	
	/*
	 * 查询单井工况诊断的泵功图数据
	 * */
	public String queryPumpCard(int id,String wellName,String selectedWellName) throws SQLException, IOException{
		byte[] bytes; 
		ConfigFile configFile=Config.getInstance().configFile;
		BufferedInputStream bis = null;
        StringBuffer dataSbf = new StringBuffer();
        StringBuffer pumpFSDiagramStrBuff = new StringBuffer();
        String tableName="tbl_rpc_diagram_latest";
        if(StringManagerUtils.isNotNull(selectedWellName)){
        	tableName="tbl_rpc_diagram_hist";
        }else{
        	tableName="tbl_rpc_diagram_latest";
        }
        String prodCol=" liquidweightproduction";
		if(configFile.getOthers().getProductionUnit()!=0){
			prodCol=" liquidVolumetricProduction";
		}
        String sql="select well.wellName as wellName, to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss') as acquisitiontime,"
        		+ " t.pumpfsdiagram,"
        		+ " t.upperloadline,t.lowerloadline, t.fmax,t.fmin,t.stroke,t.spm, "
        		+ " t."+prodCol+","
        		+ " status.workingconditionname,status.workingconditioncode, "
        		+ " t.rodstring,"
        		+ " t.pumpeff1*100 as pumpeff1, t.pumpeff2*100 as pumpeff2, t.pumpeff3*100 as pumpeff3, t.pumpeff4*100 as pumpeff4,"
        		+ " t.upstrokewattmax,t.downstrokewattmax,t.wattdegreebalance,t.upstrokeimax,t.downstrokeimax,t.idegreebalance,"
        		+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
        		+ " t.ia_curve,t.ib_curve,t.ic_curve "
        		+ " from "+tableName+" t, tbl_rpc_worktype status,tbl_wellinformation well  "
        		+ " where t.wellid=well.id and t.workingconditioncode=status.workingconditioncode ";
        if(StringManagerUtils.isNotNull(selectedWellName)){
        	sql+=" and t.id="+id;
        }else{
        	sql+=" and well.wellName='"+wellName+"'";
        }
		List<?> list=this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			String positionCurveData="";
			String loadCurveData="";
			String wattCurveData="";
			String iCurveData="";
			String IaCurveData="";
			String IbCurveData="";
			String IcCurveData="";
			String pumpFSDiagram="";
			SerializableClobProxy   proxy=null;
			CLOB realClob=null;
			if(obj[2]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[2]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				pumpFSDiagram=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[23]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[23]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				positionCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[24]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[24]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				loadCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[25]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[25]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				wattCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[26]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[26]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				iCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			if(obj[27]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[27]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				IaCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[28]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[27]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				IbCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			if(obj[29]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[27]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				IcCurveData=StringManagerUtils.CLOBtoString(realClob);
			}
			
			String rodStressRatio1="0",rodStressRatio2="0",rodStressRatio3="0",rodStressRatio4="0";
			
			if("1232".equals(obj[11]+"")){//采集异常
				String positionCurveDataArr[]=positionCurveData.split(",");
				String loadCurveDataArr[]=loadCurveData.split(",");
				for(int i=0;i<positionCurveDataArr.length;i++){
					pumpFSDiagramStrBuff.append(positionCurveDataArr[i]+",").append(loadCurveDataArr[i]+",");
				}
			}else{
				String arrbgt[]=pumpFSDiagram.split(";");  // 以;为界放入数组
		        for(int i=2;i<(arrbgt.length);i++){
		        	String arrbgtdata[]=arrbgt[i].split(",");  // 以,为界放入数组
		        	for(int j=0;j<arrbgtdata.length;j+=2){
		        		pumpFSDiagramStrBuff.append(arrbgtdata[j] + ",");
		        		pumpFSDiagramStrBuff.append(arrbgtdata[j+1] + ",");
			        }
		        	pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
		        	pumpFSDiagramStrBuff.append("#");
		        }
		        
		        String rodDataArr[]=obj[12].toString().split(";");
		        for(int i=1;i<rodDataArr.length;i++){
		        	if(i==1&&rodDataArr[i].split(",").length==6){
		        		rodStressRatio1=rodDataArr[i].split(",")[5];
		        	}else if(i==2&&rodDataArr[i].split(",").length==6){
		        		rodStressRatio2=rodDataArr[i].split(",")[5];
		        	}if(i==3&&rodDataArr[i].split(",").length==6){
		        		rodStressRatio3=rodDataArr[i].split(",")[5];
		        	}if(i==4&&rodDataArr[i].split(",").length==6){
		        		rodStressRatio4=rodDataArr[i].split(",")[5];
		        	}
		        }
			}
	        if(pumpFSDiagramStrBuff.toString().endsWith(",")){
	        	pumpFSDiagramStrBuff=pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
	        }
	        String pumpFSDiagramData = pumpFSDiagramStrBuff.toString();
	        
	        dataSbf.append("{success:true,");
	        dataSbf.append("wellName:\""+wellName+"\",");           // 井名
	        dataSbf.append("acquisitionTime:\""+obj[1]+"\",");         // 时间
	        dataSbf.append("upperLoadLine:\""+obj[3]+"\",");         // 理论上载荷
	        dataSbf.append("lowerLoadLine:\""+obj[4]+"\",");         // 理论下载荷
	        dataSbf.append("fmax:\""+obj[5]+"\",");         // 最大载荷
	        dataSbf.append("fmin:\""+obj[6]+"\",");         // 最小载荷
	        dataSbf.append("stroke:\""+obj[7]+"\",");         // 冲程
	        dataSbf.append("spm:\""+obj[8]+"\",");         // 冲次
	        dataSbf.append("liquidProduction:\""+obj[9]+"\",");         // 日产液量
	        dataSbf.append("workingConditionName:\""+obj[10]+"\",");         // 工况类型
	        dataSbf.append("workingConditionCode:\""+obj[11]+"\",");         // 工况代码
	        
	        dataSbf.append("rodStressRatio1:"+rodStressRatio1+",");       // 一级应力百分比
	        dataSbf.append("rodStressRatio2:"+rodStressRatio2+",");       // 二级应力百分比 
	        dataSbf.append("rodStressRatio3:"+rodStressRatio3+",");           // 三级应力百分比
	        dataSbf.append("rodStressRatio4:"+rodStressRatio4+",");           // 四级应力百分比
	        
	        dataSbf.append("pumpEff1:"+StringManagerUtils.stringToFloat(obj[13]==null?"":obj[13].toString(),1)+",");       // 冲程损失系数
	        dataSbf.append("pumpEff2:"+StringManagerUtils.stringToFloat(obj[14]==null?"":obj[14].toString().toString(),1)+",");       // 充满系数
	        dataSbf.append("pumpEff3:"+StringManagerUtils.stringToFloat(obj[15]==null?"":obj[15].toString().toString(),1)+",");           // 漏失系数
	        dataSbf.append("pumpEff4:"+StringManagerUtils.stringToFloat(obj[16]==null?"":obj[16].toString().toString(),1)+",");           // 液体收缩系数
	        dataSbf.append("upStrokeWattMax:\""+obj[17]+"\",");         // 工况代码
	        dataSbf.append("downStrokeWattMax:\""+obj[18]+"\",");         // 工况代码
	        dataSbf.append("wattDegreeBalance:\""+obj[19]+"\",");         // 工况代码
	        dataSbf.append("upStrokeIMax:\""+obj[20]+"\",");         // 工况代码
	        dataSbf.append("downStrokeIMax:\""+obj[21]+"\",");         // 工况代码
	        dataSbf.append("iDegreeBalance:\""+obj[22]+"\",");         // 工况代码
	        dataSbf.append("pumpFSDiagramData:\""+pumpFSDiagramData+"\",");         // 泵功图数据
	        dataSbf.append("positionCurveData:\""+positionCurveData+"\",");         // 工况代码
	        dataSbf.append("loadCurveData:\""+loadCurveData+"\",");         // 工况代码
	        dataSbf.append("powerCurveData:\""+wattCurveData+"\",");         // 工况代码
	        dataSbf.append("currentCurveData:\""+iCurveData+"\",");         // 工况代码
	        dataSbf.append("IaCurveData:\""+IaCurveData+"\","); 
	        dataSbf.append("IbCurveData:\""+IbCurveData+"\","); 
	        dataSbf.append("IcCurveData:\""+IcCurveData+"\""); 
	        dataSbf.append("}");
		}else{
			dataSbf.append("{success:true,");
			dataSbf.append("wellName:\""+wellName+"\",");
	        dataSbf.append("acquisitionTime:\"\",");
	        dataSbf.append("upperLoadLine:\"\",");  
	        dataSbf.append("lowerLoadLine:\"\","); 
	        dataSbf.append("fmax:\"\",");  
	        dataSbf.append("fmin:\"\",");
	        dataSbf.append("stroke:\"\",");  
	        dataSbf.append("spm:\"\","); 
	        dataSbf.append("liquidProduction:\"\",");  
	        dataSbf.append("workingConditionName:\"\",");
	        dataSbf.append("workingConditionCode:\"\",");  
	        dataSbf.append("rodStressRatio1:\"\","); 
	        dataSbf.append("rodStressRatio2:\"\",");  
	        dataSbf.append("rodStressRatio3:\"\",");
	        dataSbf.append("rodStressRatio4:\"\",");  
	        dataSbf.append("pumpEff1:\"\","); 
	        dataSbf.append("pumpEff2:\"\",");  
	        dataSbf.append("pumpEff3:\"\",");
	        dataSbf.append("pumpEff4:\"\",");  
	        dataSbf.append("upStrokeWattMax:\"\","); 
	        dataSbf.append("downStrokeWattMax:\"\",");  
	        dataSbf.append("wattDegreeBalance:\"\",");
	        dataSbf.append("upStrokeIMax:\"\",");  
	        dataSbf.append("downStrokeIMax:\"\",");
	        dataSbf.append("iDegreeBalance:\"\",");  
	        dataSbf.append("pumpFSDiagramData:\"\",");
	        dataSbf.append("positionCurveData:\"\",");
	        dataSbf.append("loadCurveData:\"\",");
	        dataSbf.append("powerCurveData:\"\",");
	        dataSbf.append("currentCurveData:\"\",");
	        dataSbf.append("IaCurveData:\"\",");
	        dataSbf.append("IbCurveData:\"\",");
	        dataSbf.append("IcCurveData:\"\"");
	        dataSbf.append("}");
		}
		return dataSbf.toString().replaceAll("null", "");
	}
	
	/*
	 * 查询单井工况诊断的杆柱应力数据
	 * */
	public String queryRodPress(int id) throws SQLException, IOException{
		String wellName="";
		String acquisitionTime="";
		String rodStressRatio1="0",rodStressRatio2="0",rodStressRatio3="0",rodStressRatio4="0";
		StringBuffer dataSbf = new StringBuffer();
		String sql="select t.wellName as wellName, to_char(t.acquisitionTime,'yyyy-mm-dd hh24:mi:ss') as acquisitionTime, "
				  + "t.rodstring from "
		          +"viw_rpc_diagram_hist t where t.id in (" + id + ") ";
		List<?> list=this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			wellName=obj[0].toString();
			acquisitionTime=obj[1].toString();
	        String rodDataArr[]=obj[2].toString().split(";");
	        for(int i=1;i<rodDataArr.length;i++){
	        	if(i==1&&rodDataArr[i].split(",").length==6){
	        		rodStressRatio1=rodDataArr[i].split(",")[5];
	        	}else if(i==2&&rodDataArr[i].split(",").length==6){
	        		rodStressRatio2=rodDataArr[i].split(",")[5];
	        	}if(i==3&&rodDataArr[i].split(",").length==6){
	        		rodStressRatio3=rodDataArr[i].split(",")[5];
	        	}if(i==4&&rodDataArr[i].split(",").length==6){
	        		rodStressRatio4=rodDataArr[i].split(",")[5];
	        	}
	        }
	    }
		dataSbf.append("{success:true,columns:[{\"header\":\"X轴\",\"dataIndex\":\"XData\",children:[]},{\"header\":\"Y轴\",\"dataIndex\":\"YData\",children:[]}],");
        dataSbf.append("wellName"+wellName+"\",");             // 井名
        dataSbf.append("acquisitionTime:\""+acquisitionTime+"\",");         // 时间
        dataSbf.append("rodStressRatio1:"+rodStressRatio1+",");       // 一级应力百分比
        dataSbf.append("rodStressRatio2:"+rodStressRatio2+",");       // 二级应力百分比 
        dataSbf.append("rodStressRatio3:"+rodStressRatio3+",");           // 三级应力百分比
        dataSbf.append("rodStressRatio4:"+rodStressRatio4);           // 四级应力百分比
        dataSbf.append("}");
		return dataSbf.toString();
	}
	
	/*
	 * 查询单井工况诊断的泵效组成数据
	 * */
	public String queryPumpEfficiency(int id) throws SQLException, IOException{
		String wellName="";
		String acquisitionTime="";
		Float pumpeff1=0.0f,pumpeff2=0.0f,pumpeff3=0.0f,pumpeff4=0.0f;
		StringBuffer dataSbf = new StringBuffer();
		String sql="select t.wellName as wellName, to_char(t.acquisitionTime,'yyyy-mm-dd hh24:mi:ss') as acquisitionTime, "
				  + "t.pumpeff1*100, t.pumpeff2*100, t.pumpeff3*100, t.pumpeff4*100 from "
		          +"viw_rpc_diagram_hist t where t.id in (" + id + ") ";
		List<?> list=this.findCallSql(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			wellName=obj[0].toString();
			acquisitionTime=obj[1].toString();
			pumpeff1=StringManagerUtils.stringToFloat(obj[2].toString() ,1);
			pumpeff2=StringManagerUtils.stringToFloat(obj[3].toString() ,1);
			pumpeff3=StringManagerUtils.stringToFloat(obj[4].toString() ,1);
			pumpeff4=StringManagerUtils.stringToFloat(obj[5].toString() ,1);
	    }
		dataSbf.append("{success:true,");
        dataSbf.append("wellName:\""+wellName+"\",");             // 井名
        dataSbf.append("acquisitionTime:\""+acquisitionTime+"\",");         // 时间
        dataSbf.append("pumpeff1:\""+pumpeff1+"\",");     // 冲程损失系数
        dataSbf.append("pumpeff2:\""+pumpeff2+"\",");         // 充满系数
        dataSbf.append("pumpeff3:"+pumpeff3+",");             // 漏失系数
        dataSbf.append("pumpeff4:\""+pumpeff4+"\"");      // 液体收缩系数
        dataSbf.append("}");
		return dataSbf.toString();
	}
	
	public String statisticsData(String orgId,String type,String wellType){
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String sql="";
		String statType="workingConditionName";
		String tableName="viw_rpc_comprehensive_latest";
		if("1".equalsIgnoreCase(type)){
			if("200".equals(wellType)){
				statType="workingConditionName";
			}else{
				statType="workingConditionName_E";
			}
		}else if("2".equalsIgnoreCase(type)){
			statType="liquidWeightProductionlevel";
			if(configFile.getOthers().getProductionUnit()!=0){
				statType="liquidVolumeProductionlevel";
			}
			
		}else if("3".equalsIgnoreCase(type)){
			statType="wattDegreeBalanceName";
		}else if("4".equalsIgnoreCase(type)){
			statType="iDegreeBalanceName";
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
		}else if("13".equalsIgnoreCase(type)){
			statType="workingConditionName_E";
		}
		if("200".equals(wellType)){
			tableName="viw_rpc_comprehensive_latest";
		}else{
			tableName="viw_pcp_comprehensive_latest";
		}
		sql="select "+statType+",count(1) from "+tableName+" t where  t.org_id in("+orgId+")";
		
		sql+=" group by "+statType;
		
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		result_json.append("\"List\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			if(StringManagerUtils.isNotNull(obj[0]+"")){
//				result_json.append("{\"id\":"+obj[0]+",");
				result_json.append("{\"item\":\""+obj[0]+"\",");
				result_json.append("\"count\":"+obj[1]+"},");
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getAnalysisAndAcqAndControlData(String recordId,String wellName,String selectedWellName,int userId)throws Exception {
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String prodCol=" liquidWeightProduction,oilWeightProduction,waterCut_W,"
				+ " availablePlungerstrokeProd_W,pumpClearanceLeakProd_W,tvleakWeightProduction,svleakWeightProduction,gasInfluenceProd_W,";
		if(configFile.getOthers().getProductionUnit()!=0){
			prodCol=" liquidVolumetricProduction,oilVolumetricProduction,waterCut,"
					+ " availablePlungerstrokeProd_V,pumpClearanceLeakProd_V,tvleakVolumetricProduction,svleakVolumetricProduction,gasInfluenceProd_V,";;
		}
		String tableName="viw_rpc_comprehensive_latest";
		if(StringManagerUtils.isNotNull(wellName)){
			tableName="viw_rpc_comprehensive_hist";
		}
		String isControlSql="select t2.role_flag from tbl_user t,tbl_role t2 where t.user_type=t2.role_id and t.user_no="+userId;
		String controlItemSql="select t.wellname,t3.itemname,t3.itemcode "
				+ " from tbl_wellinformation t,tbl_acq_group_conf t2,tbl_acq_item_conf t3,tbl_acq_item2group_conf t4 "
				+ " where t.unitcode=t2.unit_code and t2.id=t4.unitid and t4.itemid=t3.id "
				+ " and t.wellname='"+selectedWellName+"' and t3.operationtype=2 "
				+ " order by t3.seq";
		String sql="select wattDegreeBalance,wattRatio,iDegreeBalance,iRatio,deltaRadius,"
				+ prodCol
				+ " theoreticalProduction,"
				+ " plungerstroke,availableplungerstroke,"
				+ " pumpBoreDiameter,pumpSettingDepth,producingFluidLevel,submergence,"
				+ " stroke,spm,fmax,fmin,fDifference,expectedFDifference,fsDiagramArea,"
				+ " motorInputActivePower,polishrodPower,waterPower,surfaceSystemEfficiency,welldownSystemEfficiency,systemEfficiency,powerConsumptionPerthm,"
				+ " pumpEff1,pumpEff2,pumpEff3,pumpEff4,pumpEff,"
				+ " rodFlexLength,tubingFlexLength,inertiaLength,"
				+ " pumpintakep,pumpintaket,pumpintakegol,pumpinletvisl,pumpinletbo,"
				+ " pumpoutletp,pumpoutlett,pumpOutletGol,pumpoutletvisl,pumpoutletbo,"
				+ " rodString,"
				+ " upperLoadLineOfExact,"
				+ " tubingPressure,casingPressure,wellheadFluidTemperature,"
				+ " to_char(acquisitionTime_d,'yyyy-mm-dd hh24:mi:ss'),"
				+ " commStatus,runStatus,"
				+ " Ia,Ib,Ic,Va,Vb,Vc,"
				+ " totalWattEnergy,totalVarEnergy,totalVAEnergy,"
				+ " todayWattEnergy,todayVarEnergy,todayVAEnergy,"
				+ " wattSum,varSum,reversePower,pfSum,"
				+ " IaUpLimit,IaDownLimit,wattUpLimit,wattDownLimit,"
				+ " IaMax,IaMin,IbMax,IbMin,IcMax,IcMin,"
				+ " frequencySetValue,frequencyRunValue,"
				+ " balanceControlMode,balanceCalculateMode,"
				+ " balanceAwayTime,balanceCloseTime,"
				+ " balanceAwayTimePerBeat,balanceCloseTimePerBeat,"
				+ " balanceStrokeCount,"
				+ " balanceOperationUpLimit,balanceOperationDownLimit,"
				+ " balanceAutoControl,spmAutoControl,balanceFrontLimit,balanceAfterLimit,"
				+ " acqcycle_diagram,acqcycle_discrete,"
				+ " signal,deviceVer,"
				+ " videourl,"
				+ " runRange"
				+ " from "+tableName+" t where id="+recordId;
		List<?> isControlList = this.findCallSql(isControlSql);
		List<?> controlItemsList = this.findCallSql(controlItemSql);
		List<?> list = this.findCallSql(sql);
		String isControl=isControlList.size()>0?isControlList.get(0).toString():"0";
		result_json.append("{ \"success\":true,\"isControl\":"+isControl+",");
		result_json.append("\"controlItems\":[");
		for(int i=0;i<controlItemsList.size();i++){
			Object[] obj=(Object[]) controlItemsList.get(i);
			result_json.append("{\"tiem\":\""+obj[2]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("],");
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			result_json.append("\"wattDegreeBalance\":\""+obj[0]+"\",");
			result_json.append("\"wattRatio\":\""+obj[1]+"\",");
			result_json.append("\"iDegreeBalance\":\""+obj[2]+"\",");
			result_json.append("\"iRatio\":\""+obj[3]+"\",");
			result_json.append("\"deltaRadius\":\""+obj[4]+"\",");
			result_json.append("\"liquidProduction\":\""+obj[5]+"\",");
			result_json.append("\"oilProduction\":\""+obj[6]+"\",");
			result_json.append("\"waterCut\":\""+obj[7]+"\",");
			
			result_json.append("\"availablePlungerstrokeProd\":\""+obj[8]+"\",");
			result_json.append("\"pumpClearanceLeakProd\":\""+obj[9]+"\",");
			result_json.append("\"tvleakProduction\":\""+obj[10]+"\",");
			result_json.append("\"svleakProduction\":\""+obj[11]+"\",");
			result_json.append("\"gasInfluenceProd\":\""+obj[12]+"\",");
			
			result_json.append("\"theoreticalProduction\":\""+obj[13]+"\",");
			result_json.append("\"plungerStroke\":\""+obj[14]+"\",");
			result_json.append("\"availablePlungerStroke\":\""+obj[15]+"\",");
			
			result_json.append("\"pumpBoreDiameter\":\""+obj[16]+"\",");
			result_json.append("\"pumpSettingDepth\":\""+obj[17]+"\",");
			result_json.append("\"producingFluidLevel\":\""+obj[18]+"\",");
			result_json.append("\"submergence\":\""+obj[19]+"\",");
			
			result_json.append("\"stroke\":\""+obj[20]+"\",");
			result_json.append("\"spm\":\""+obj[21]+"\",");
			result_json.append("\"fmax\":\""+obj[22]+"\",");
			result_json.append("\"fmin\":\""+obj[23]+"\",");
			result_json.append("\"fDifference\":\""+obj[24]+"\",");
			result_json.append("\"expectedFDifference\":\""+obj[25]+"\",");
			result_json.append("\"fsDiagramArea\":\""+obj[26]+"\",");
			
			result_json.append("\"motorInputActivePower\":\""+obj[27]+"\",");
			result_json.append("\"polishrodPower\":\""+obj[28]+"\",");
			result_json.append("\"waterPower\":\""+obj[29]+"\",");
			result_json.append("\"surfaceSystemEfficiency\":\""+obj[30]+"\",");
			result_json.append("\"welldownSystemEfficiency\":\""+obj[31]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[32]+"\",");
			result_json.append("\"powerConsumptionPerthm\":\""+obj[33]+"\",");
			result_json.append("\"pumpEff1\":\""+obj[34]+"\",");
			result_json.append("\"pumpEff2\":\""+obj[35]+"\",");
			result_json.append("\"pumpEff3\":\""+obj[36]+"\",");
			result_json.append("\"pumpEff4\":\""+obj[37]+"\",");
			result_json.append("\"pumpEff\":\""+obj[38]+"\",");
			result_json.append("\"rodFlexLength\":\""+obj[39]+"\",");
			result_json.append("\"tubingFlexLength\":\""+obj[40]+"\",");
			result_json.append("\"inertiaLength\":\""+obj[41]+"\",");
			
			result_json.append("\"pumpintakep\":\""+obj[42]+"\",");
			result_json.append("\"pumpintaket\":\""+obj[43]+"\",");
			result_json.append("\"pumpintakegol\":\""+obj[44]+"\",");
			result_json.append("\"pumpinletvisl\":\""+obj[45]+"\",");
			result_json.append("\"pumpinletbo\":\""+obj[46]+"\",");
			
			result_json.append("\"pumpoutletp\":\""+obj[47]+"\",");
			result_json.append("\"pumpoutlett\":\""+obj[48]+"\",");
			result_json.append("\"pumpOutletGol\":\""+obj[49]+"\",");
			result_json.append("\"pumpoutletvisl\":\""+obj[50]+"\",");
			result_json.append("\"pumpoutletbo\":\""+obj[51]+"\",");
			
			result_json.append("\"rodString\":\""+obj[52]+"\",");
			
			result_json.append("\"upperLoadLineOfExact\":\""+obj[53]+"\",");
			
			result_json.append("\"tubingPressure\":\""+obj[54]+"\",");
			result_json.append("\"casingPressure\":\""+obj[55]+"\",");
			result_json.append("\"wellheadFluidTemperature\":\""+obj[56]+"\",");
			result_json.append("\"acquisitionTime_d\":\""+obj[57]+"\",");
			result_json.append("\"commStatus\":\""+obj[58]+"\",");
			result_json.append("\"runStatus\":\""+obj[59]+"\",");
			result_json.append("\"Ia\":\""+obj[60]+"\",");
			result_json.append("\"Ib\":\""+obj[61]+"\",");
			result_json.append("\"Ic\":\""+obj[62]+"\",");
			result_json.append("\"Va\":\""+obj[63]+"\",");
			result_json.append("\"Vb\":\""+obj[64]+"\",");
			result_json.append("\"Vc\":\""+obj[65]+"\",");
			result_json.append("\"totalWattEnergy\":\""+obj[66]+"\",");
			result_json.append("\"totalVarEnergy\":\""+obj[67]+"\",");
			result_json.append("\"totalVAEnergy\":\""+obj[68]+"\",");
			result_json.append("\"todayWattEnergy\":\""+obj[69]+"\",");
			result_json.append("\"todayVarEnergy\":\""+obj[70]+"\",");
			result_json.append("\"todayVAEnergy\":\""+obj[71]+"\",");
			
			result_json.append("\"wattSum\":\""+obj[72]+"\",");
			result_json.append("\"varSum\":\""+obj[73]+"\",");
			result_json.append("\"reversePower\":\""+obj[74]+"\",");
			result_json.append("\"pfSum\":\""+obj[75]+"\",");
			
			result_json.append("\"IaUpLimit\":\""+obj[76]+"\",");
			result_json.append("\"IaDownLimit\":\""+obj[77]+"\",");
			result_json.append("\"wattUpLimit\":\""+obj[78]+"\",");
			result_json.append("\"wattDownLimit\":\""+obj[79]+"\",");
			result_json.append("\"IaMax\":\""+obj[80]+"\",");
			result_json.append("\"IaMin\":\""+obj[81]+"\",");
			result_json.append("\"IbMax\":\""+obj[82]+"\",");
			result_json.append("\"IbMin\":\""+obj[83]+"\",");
			result_json.append("\"IcMax\":\""+obj[84]+"\",");
			result_json.append("\"IcMin\":\""+obj[85]+"\",");
			
			result_json.append("\"frequencySetValue\":\""+obj[86]+"\",");
			result_json.append("\"frequencyRunValue\":\""+obj[87]+"\",");
			
			result_json.append("\"balanceControlMode\":\""+obj[88]+"\",");
			result_json.append("\"balanceCalculateMode\":\""+obj[89]+"\",");
			
			int balanceAwayTime=StringManagerUtils.stringToInteger(obj[90]+"");
			int deltaRadius1=(int)(balanceAwayTime/1000*3.6/10+0.5);
			
			int balanceCloseTime=StringManagerUtils.stringToInteger(obj[91]+"");
			int deltaRadius2=(int)(balanceCloseTime/1000*3.6/10+0.5);
			
			result_json.append("\"balanceAwayTime\":\""+deltaRadius1+"\",");
			result_json.append("\"balanceCloseTime\":\""+deltaRadius2+"\",");
			
			result_json.append("\"balanceAwayTimePerBeat\":\""+obj[92]+"\",");
			result_json.append("\"balanceCloseTimePerBeat\":\""+obj[93]+"\",");
			
			result_json.append("\"balanceStrokeCount\":\""+obj[94]+"\",");
			result_json.append("\"balanceOperationUpLimit\":\""+obj[95]+"\",");
			result_json.append("\"balanceOperationDownLimit\":\""+obj[96]+"\",");
			result_json.append("\"balanceAutoControl\":\""+obj[97]+"\",");
			result_json.append("\"spmAutoControl\":\""+obj[98]+"\",");
			result_json.append("\"balanceFrontLimit\":\""+obj[99]+"\",");
			result_json.append("\"balanceAfterLimit\":\""+obj[100]+"\",");
			
			result_json.append("\"acqcycle_diagram\":\""+obj[101]+"\",");
			
			int acqcycle_discrete1=StringManagerUtils.stringToInteger(obj[102]+"");
			float acqcycle_discrete=(float)acqcycle_discrete1/60;
			result_json.append("\"acqcycle_discrete\":\""+acqcycle_discrete+"\",");
			
			result_json.append("\"signal\":\""+obj[103]+"\",");
			result_json.append("\"deviceVer\":\""+obj[104]+"\",");
			
			result_json.append("\"videourl\":\""+obj[105]+"\",");
			result_json.append("\"runRange\":\""+obj[106]+"\"");
		}
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getPCPAnalysisAndAcqAndControlData(String recordId,String wellName,String selectedWellName,int userId)throws Exception {
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String prodCol=" liquidWeightProduction,oilWeightProduction,waterCut_W,";
		if(configFile.getOthers().getProductionUnit()!=0){
			prodCol=" liquidVolumetricProduction,oilVolumetricProduction,waterCut,";
		}
		String tableName="viw_pcp_comprehensive_latest";
		if(StringManagerUtils.isNotNull(wellName)){
			tableName="viw_pcp_comprehensive_hist";
		}
		String isControlSql="select t2.role_flag from tbl_user t,tbl_role t2 where t.user_type=t2.role_id and t.user_no="+userId;
		String controlItemSql="select t.wellname,t3.itemname,t3.itemcode "
				+ " from tbl_wellinformation t,tbl_acq_group_conf t2,tbl_acq_item_conf t3,tbl_acq_item2group_conf t4 "
				+ " where t.unitcode=t2.unit_code and t2.id=t4.unitid and t4.itemid=t3.id "
				+ " and t.wellname='"+selectedWellName+"' and t3.operationtype=2 "
				+ " order by t3.seq";
		String sql="select "+prodCol
				+ " qpr,"
				+ " motorInputActivePower,waterPower,systemEfficiency,powerConsumptionPerthm,"
				+ " pumpEff1,pumpEff2,pumpEff,"
				+ " pumpintakep,pumpintaket,pumpintakegol,pumpinletvisl,pumpinletbo,"
				+ " pumpoutletp,pumpoutlett,pumpOutletGol,pumpoutletvisl,pumpoutletbo,"
				+ " rodString,"
				+ " tubingPressure,casingPressure,wellheadFluidTemperature,"
				+ " to_char(acquisitionTime_d,'yyyy-mm-dd hh24:mi:ss'),"
				+ " commStatus,runStatus,"
				+ " Ia,Ib,Ic,Va,Vb,Vc,"
				+ " totalWattEnergy,totalVarEnergy,wattSum,varSum,reversePower,pfSum,"
				+ " frequencySetValue,frequencyRunValue,"
				+ " videourl,"
				+ " runRange"
				+ " from "+tableName+" t where id="+recordId;
		List<?> isControlList = this.findCallSql(isControlSql);
		List<?> controlItemsList = this.findCallSql(controlItemSql);
		List<?> list = this.findCallSql(sql);
		String isControl=isControlList.size()>0?isControlList.get(0).toString():"0";
		result_json.append("{ \"success\":true,\"isControl\":"+isControl+",");
		result_json.append("\"controlItems\":[");
		for(int i=0;i<controlItemsList.size();i++){
			Object[] obj=(Object[]) controlItemsList.get(i);
			result_json.append("{\"tiem\":\""+obj[2]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("],");
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			result_json.append("\"liquidProduction\":\""+obj[0]+"\",");
			result_json.append("\"oilProduction\":\""+obj[1]+"\",");
			result_json.append("\"waterCut\":\""+obj[2]+"\",");
			result_json.append("\"qpr\":\""+obj[3]+"\",");
			
			result_json.append("\"motorInputActivePower\":\""+obj[4]+"\",");
			result_json.append("\"waterPower\":\""+obj[5]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[6]+"\",");
			result_json.append("\"powerConsumptionPerthm\":\""+obj[7]+"\",");
			
			result_json.append("\"pumpEff1\":\""+obj[8]+"\",");
			result_json.append("\"pumpEff2\":\""+obj[9]+"\",");
			result_json.append("\"pumpEff\":\""+obj[10]+"\",");
			
			result_json.append("\"pumpintakep\":\""+obj[11]+"\",");
			result_json.append("\"pumpintaket\":\""+obj[12]+"\",");
			result_json.append("\"pumpintakegol\":\""+obj[13]+"\",");
			result_json.append("\"pumpinletvisl\":\""+obj[14]+"\",");
			result_json.append("\"pumpinletbo\":\""+obj[15]+"\",");
			
			result_json.append("\"pumpoutletp\":\""+obj[16]+"\",");
			result_json.append("\"pumpoutlett\":\""+obj[17]+"\",");
			result_json.append("\"pumpOutletGol\":\""+obj[18]+"\",");
			result_json.append("\"pumpoutletvisl\":\""+obj[19]+"\",");
			result_json.append("\"pumpoutletbo\":\""+obj[20]+"\",");
			
			result_json.append("\"rodString\":\""+obj[21]+"\",");
			
			result_json.append("\"tubingPressure\":\""+obj[22]+"\",");
			result_json.append("\"casingPressure\":\""+obj[23]+"\",");
			result_json.append("\"wellheadFluidTemperature\":\""+obj[24]+"\",");
			result_json.append("\"acquisitionTime_d\":\""+obj[25]+"\",");
			result_json.append("\"commStatus\":\""+obj[26]+"\",");
			result_json.append("\"runStatus\":\""+obj[27]+"\",");
			result_json.append("\"Ia\":\""+obj[28]+"\",");
			result_json.append("\"Ib\":\""+obj[29]+"\",");
			result_json.append("\"Ic\":\""+obj[30]+"\",");
			result_json.append("\"Va\":\""+obj[31]+"\",");
			result_json.append("\"Vb\":\""+obj[32]+"\",");
			result_json.append("\"Vc\":\""+obj[33]+"\",");
			result_json.append("\"totalWattEnergy\":\""+obj[34]+"\",");
			result_json.append("\"totalVarEnergy\":\""+obj[35]+"\",");
			result_json.append("\"wattSum\":\""+obj[36]+"\",");
			result_json.append("\"varSum\":\""+obj[37]+"\",");
			result_json.append("\"reversePower\":\""+obj[38]+"\",");
			result_json.append("\"pfSum\":\""+obj[39]+"\",");
			result_json.append("\"frequencySetValue\":\""+obj[40]+"\",");
			result_json.append("\"frequencyRunValue\":\""+obj[41]+"\",");
			
			result_json.append("\"videourl\":\""+obj[42]+"\",");
			result_json.append("\"runRange\":\""+obj[43]+"\"");
		}
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getRPCDiagnosisDataCurveData(String wellName,String startDate,String endDate,String itemName,String itemCode) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		String uplimit="";
		String downlimit="";
		String zero="";
		String tableName="";
		String sql="";
		String item=itemCode;
		if("Ia".equalsIgnoreCase(itemCode)||"Ib".equalsIgnoreCase(itemCode)||"Ic".equalsIgnoreCase(itemCode)
				||"Va".equalsIgnoreCase(itemCode)||"Vb".equalsIgnoreCase(itemCode)||"Vc".equalsIgnoreCase(itemCode)){
			item=itemCode+","+itemCode+"uplimit,"+itemCode+"downlimit,"+itemCode+"Zero ";
			tableName="viw_rpc_discrete_hist";
		}else if("commStatus".equalsIgnoreCase(itemCode)||"runStatus".equalsIgnoreCase(itemCode)||"tubingpressure".equalsIgnoreCase(itemCode)
				||"casingpressure".equalsIgnoreCase(itemCode)||"backpressure".equalsIgnoreCase(itemCode)||"wellheadfluidtemperature".equalsIgnoreCase(itemCode)
				||"totalWattEnergy".equalsIgnoreCase(itemCode)||"totalVarEnergy".equalsIgnoreCase(itemCode)||"totalVAEnergy".equalsIgnoreCase(itemCode)
				||"todayWattEnergy".equalsIgnoreCase(itemCode)||"todayVarEnergy".equalsIgnoreCase(itemCode)||"todayVAEnergy".equalsIgnoreCase(itemCode)
				||"wattSum".equalsIgnoreCase(itemCode)||"varSum".equalsIgnoreCase(itemCode)||"reversepower".equalsIgnoreCase(itemCode)||"pfSum".equalsIgnoreCase(itemCode)
				||"frequencyRunValue".equalsIgnoreCase(itemCode)
				||"IaMax".equalsIgnoreCase(itemCode)
				||"IaMin".equalsIgnoreCase(itemCode)
				||"IbMax".equalsIgnoreCase(itemCode)
				||"IbMin".equalsIgnoreCase(itemCode)
				||"IcMax".equalsIgnoreCase(itemCode)
				||"IcMin".equalsIgnoreCase(itemCode)
				||"signal".equalsIgnoreCase(itemCode)
				||"deviceVer".equalsIgnoreCase(itemCode)
				){
			tableName="viw_rpc_discrete_hist";
		}else if("iRatio".equalsIgnoreCase(itemCode)){
			item="downStrokeIMax,upStrokeIMax ";
			tableName="viw_rpc_diagram_hist";
		}else if("wattRatio".equalsIgnoreCase(itemCode)){
			item="downStrokeWattMax,upStrokeWattMax ";
			tableName="viw_rpc_diagram_hist";
		}else{
			tableName="viw_rpc_diagram_hist";
		}
		sql="select to_char(t.acquisitionTime,'yyyy-mm-dd hh24:mi:ss'),"+item+" from "+tableName+" t "
				+ " where t.wellName='"+wellName+"' and to_date(to_char(t.acquisitionTime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') order by t.acquisitionTime";
		int totals = getTotalCountRows(sql);//获取总记录数
		List<?> list=this.findCallSql(sql);
		dynSbf.append("{\"success\":true,\"totalCount\":" + totals + ",\"wellName\":\""+wellName+"\",\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				dynSbf.append("{ \"acquisitionTime\":\"" + obj[0] + "\",");
				dynSbf.append("\"value\":\""+obj[1]+"\"");
				if("iRatio".equalsIgnoreCase(itemCode)||"wattRatio".equalsIgnoreCase(itemCode)){
					dynSbf.append(",\"value2\":\""+obj[2]+"\"");
				}
				dynSbf.append("},");
				if(obj.length==5&&i==list.size()-1){
					uplimit=obj[2]+"";
					downlimit=obj[3]+"";
					zero=obj[4]+"";
				}
			}
			dynSbf.deleteCharAt(dynSbf.length() - 1);
		}
		dynSbf.append("],\"uplimit\":\""+uplimit+"\",\"downlimit\":\""+downlimit+"\",\"zero\":\""+zero+"\"}");
		return dynSbf.toString();
	}
	
	public String getPCPDiagnosisDataCurveData(String wellName,String startDate,String endDate,String itemName,String itemCode) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		String uplimit="";
		String downlimit="";
		String sql="select to_char(t.acquisitionTime,'yyyy-mm-dd hh24:mi:ss'),t."+itemCode+" from viw_pcp_rpm_hist t "
				+ " where t.wellName='"+wellName+"' and to_date(to_char(t.acquisitionTime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') order by t.acquisitionTime";
		if("Ia".equalsIgnoreCase(itemCode)||"Ib".equalsIgnoreCase(itemCode)||"Ic".equalsIgnoreCase(itemCode)
				||"Va".equalsIgnoreCase(itemCode)||"Vb".equalsIgnoreCase(itemCode)||"Vc".equalsIgnoreCase(itemCode)){
			itemCode="t."+itemCode+",t."+itemCode+"uplimit,t."+itemCode+"downlimit";
			sql="select to_char(t.acquisitionTime,'yyyy-mm-dd hh24:mi:ss'),"+itemCode+" from viw_pcp_discrete_hist t "
					+ " where t.wellName='"+wellName+"' and to_date(to_char(t.acquisitionTime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') order by t.acquisitionTime";
		}else if("commStatus".equalsIgnoreCase(itemCode)||"runStatus".equalsIgnoreCase(itemCode)||"tubingpressure".equalsIgnoreCase(itemCode)
				||"casingpressure".equalsIgnoreCase(itemCode)||"backpressure".equalsIgnoreCase(itemCode)||"wellheadfluidtemperature".equalsIgnoreCase(itemCode)
				||"totalWattEnergy".equalsIgnoreCase(itemCode)||"totalVarEnergy".equalsIgnoreCase(itemCode)
				||"wattSum".equalsIgnoreCase(itemCode)||"varSum".equalsIgnoreCase(itemCode)||"reversepower".equalsIgnoreCase(itemCode)||"pfSum".equalsIgnoreCase(itemCode)
				||"frequencyRunValue".equalsIgnoreCase(itemCode)){
			sql="select to_char(t.acquisitionTime,'yyyy-mm-dd hh24:mi:ss'),t."+itemCode+" from viw_pcp_discrete_hist t "
					+ " where t.wellName='"+wellName+"' and to_date(to_char(t.acquisitionTime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') order by t.acquisitionTime";
		}
		
		int totals = getTotalCountRows(sql);//获取总记录数
		List<?> list=this.findCallSql(sql);
		
		dynSbf.append("{\"success\":true,\"totalCount\":" + totals + ",\"wellName\":\""+wellName+"\",\"startDate\":\""+startDate+"\",\"endDate\":\""+endDate+"\",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				dynSbf.append("{ \"acquisitionTime\":\"" + obj[0] + "\",");
				dynSbf.append("\"value\":\""+obj[1]+"\"},");
				if(obj.length==4&&i==list.size()-1){
					uplimit=obj[2]+"";
					downlimit=obj[3]+"";
				}
			}
			dynSbf.deleteCharAt(dynSbf.length() - 1);
		}
		dynSbf.append("],\"uplimit\":\""+uplimit+"\",\"downlimit\":\""+downlimit+"\"}");
		return dynSbf.toString();
	}
	
	public String getScrewPumpRTAnalysiCurveData(String acquisitionTime,String wellName) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		float iauplimit=0;
		float iadownlimit=0;
		float ibuplimit=0;
		float ibdownlimit=0;
		float icuplimit=0;
		float icdownlimit=0;
		float vauplimit=0;
		float vadownlimit=0;
		float vbuplimit=0;
		float vbdownlimit=0;
		float vcuplimit=0;
		float vcdownlimit=0;
		
		String sql="select "
				+ " to_char(t.acquisitionTime,'yyyy-mm-dd hh24:mi:ss'),t.rpm,t.ia,t.ib,t.ic,t.va,t.vb,t.vc, "
				+ " t.iauplimit,t.iadownlimit,t.ibuplimit,t.ibdownlimit,t.icuplimit,t.icdownlimit, "
				+ " t.vauplimit,t.vadownlimit,t.vbuplimit,t.vbdownlimit,t.vcuplimit,t.vcdownlimit "
				+ " from viw_pcp_comprehensive_hist t "
				+ " where t.acquisitionTime between to_date(to_char(to_date('"+acquisitionTime+"','yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd'),'yyyy-mm-dd') "
				+ " and to_date('"+acquisitionTime+"','yyyy-mm-dd hh24:mi:ss') "
				+ " and t.wellName='"+wellName+"'"
				+ " order by t.acquisitionTime";
		List<?> list=this.findCallSql(sql);
		
		dynSbf.append("{\"success\":true,\"totalCount\":" + list.size() + ",\"wellName\":\""+wellName+"\",\"acquisitionTime\":\""+acquisitionTime+"\",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				dynSbf.append("{ \"acquisitionTime\":\"" + obj[0] + "\",");
				dynSbf.append("\"rpm\":"+obj[1]+",");
				dynSbf.append("\"ia\":"+obj[2]+",");
				dynSbf.append("\"ib\":"+obj[3]+",");
				dynSbf.append("\"ic\":"+obj[4]+",");
				dynSbf.append("\"va\":"+obj[5]+",");
				dynSbf.append("\"vb\":"+obj[6]+",");
				dynSbf.append("\"vc\":"+obj[7]+"},");
				if(i==list.size()-1){
					iauplimit=StringManagerUtils.stringToFloat(obj[8]+"");
					iadownlimit=StringManagerUtils.stringToFloat(obj[9]+"");
					ibuplimit=StringManagerUtils.stringToFloat(obj[10]+"");
					ibdownlimit=StringManagerUtils.stringToFloat(obj[11]+"");
					icuplimit=StringManagerUtils.stringToFloat(obj[12]+"");
					icdownlimit=StringManagerUtils.stringToFloat(obj[13]+"");
					vauplimit=StringManagerUtils.stringToFloat(obj[14]+"");
					vadownlimit=StringManagerUtils.stringToFloat(obj[15]+"");
					vbuplimit=StringManagerUtils.stringToFloat(obj[16]+"");
					vbdownlimit=StringManagerUtils.stringToFloat(obj[17]+"");
					vcuplimit=StringManagerUtils.stringToFloat(obj[18]+"");
					vcdownlimit=StringManagerUtils.stringToFloat(obj[19]+"");
				}
			}
			dynSbf.deleteCharAt(dynSbf.length() - 1);
		}
		dynSbf.append("],");
		dynSbf.append("\"iauplimit\":"+iauplimit+",\"iadownlimit\":"+iadownlimit+",");
		dynSbf.append("\"ibuplimit\":"+ibuplimit+",\"ibdownlimit\":"+ibdownlimit+",");
		dynSbf.append("\"icuplimit\":"+icuplimit+",\"icdownlimit\":"+icdownlimit+",");
		dynSbf.append("\"vauplimit\":"+vauplimit+",\"vadownlimit\":"+vadownlimit+",");
		dynSbf.append("\"vbuplimit\":"+vbuplimit+",\"vbdownlimit\":"+vbdownlimit+",");
		dynSbf.append("\"vcuplimit\":"+vcuplimit+",\"vcdownlimit\":"+vcdownlimit+"");
		dynSbf.append("}");
		return dynSbf.toString();
	}
	
	public String getNewestAcquisitionTime(String orgId,String FSDiagramMaxAcquisitionTime,String DiscreteMaxAcquisitionTime){
		StringBuffer result_json = new StringBuffer();
		String newestFSDiagramAcquisitionTime="";
		String newestDiscreteAcquisitionTime="";
		int diagramRecords=0;
		int discreteRecords=0;
		String diagramSql="select to_char(max(t.acquisitiontime),'yyyy-mm-dd hh24:mi:ss'),count(1) from TBL_RPC_DIAGRAM_HIST t,tbl_wellinformation well where t.wellid=well.id and well.orgid in("+orgId+")";
		String discreteSql="select to_char(max(t.acquisitiontime),'yyyy-mm-dd hh24:mi:ss'),count(1) from tbl_rpc_discrete_hist t,tbl_wellinformation well where t.wellid=well.id and well.orgid in("+orgId+")";
		
		if(StringManagerUtils.isNotNull(FSDiagramMaxAcquisitionTime)){
			diagramSql+="and t.acquisitiontime>to_date('"+FSDiagramMaxAcquisitionTime+"','yyyy-mm-dd hh24:mi:ss')";
		}
		if(StringManagerUtils.isNotNull(DiscreteMaxAcquisitionTime)){
			discreteSql+="and t.acquisitiontime>to_date('"+DiscreteMaxAcquisitionTime+"','yyyy-mm-dd hh24:mi:ss')";
		}
		
		List<?> diagramList=this.findCallSql(diagramSql);
		List<?> discreteList=this.findCallSql(discreteSql);
		
		if(diagramList.size()>0){
			Object[] obj = (Object[]) diagramList.get(0);
			if(obj[0]!=null){
				newestFSDiagramAcquisitionTime=obj[0]+"";
			}
			if(obj[1]!=null){
				diagramRecords=StringManagerUtils.stringToInteger(obj[1]+"");
			}
		}
		
		if(discreteList.size()>0){
			Object[] obj = (Object[]) discreteList.get(0);
			if(obj[0]!=null){
				newestDiscreteAcquisitionTime=obj[0]+"";
			}
			if(obj[1]!=null){
				discreteRecords=StringManagerUtils.stringToInteger(obj[1]+"");
			}
		}
		result_json.append("{");
		result_json.append("\"newestFSDiagramAcquisitionTime\":\""+newestFSDiagramAcquisitionTime+"\",");
		result_json.append("\"diagramRecords\":"+diagramRecords+",");
		result_json.append("\"newestDiscreteAcquisitionTime\":\""+newestDiscreteAcquisitionTime+"\",");
		result_json.append("\"discreteRecords\":"+discreteRecords+"");
		result_json.append("}");
		return result_json.toString();
	}
	
	public BaseDao getDao() {
		return dao;
	}

	@Resource
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
}
