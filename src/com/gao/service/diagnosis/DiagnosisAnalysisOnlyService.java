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
		String typeColumnName="workingConditionName";
		
		if("1".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeFSDiagram";
			}
			typeColumnName="workingConditionName";
		}else if("2".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimePeodDist";
			}else{//默认为抽油机
				ddicName="realtimeProdDist";
			}
			typeColumnName="liquidWeightProductionlevel";
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
		sql=ddic.getSql()+",workingConditionString_E,videourl,workingConditionAlarmLevel,workingConditionAlarmLevel_E,"
				+ "commStatus,runStatus,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel from viw_rpc_comprehensive_latest t where t.org_id in("+orgId+")";
		sqlHis=ddic.getSql()+",workingConditionString_E,videourl,workingConditionAlarmLevel,workingConditionAlarmLevel_E,"
				+ "commStatus,runStatus,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel from viw_rpc_comprehensive_hist t where t.org_id in("+orgId+")";
		
		if(StringManagerUtils.isNotNull(wellType)){
			sql+=" and liftingType>="+wellType+" and liftingType<("+wellType+"+100) ";
		}
		
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
		String typeColumnName="workingConditionName";
		
		if("1".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeFSDiagram";
			}
			typeColumnName="workingConditionName";
		}else if("2".equalsIgnoreCase(type)){
			if("400".equals(wellType)){//螺杆泵井
				ddicName="screwPumpRealtimePeodDist";
			}else{//默认为抽油机
				ddicName="realtimeProdDist";
			}
			typeColumnName="liquidWeightProductionlevel";
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
		sql=ddic.getSql()+",workingConditionString_E,videourl,workingConditionAlarmLevel,workingConditionAlarmLevel_E,"
				+ "commStatus,runStatus,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel from viw_rpc_comprehensive_latest t where t.org_id in("+orgId+")";
		sqlHis=ddic.getSql()+",workingConditionString_E,videourl,workingConditionAlarmLevel,workingConditionAlarmLevel_E,"
				+ "commStatus,runStatus,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel from viw_rpc_comprehensive_hist t where t.org_id in("+orgId+")";
		
		if(StringManagerUtils.isNotNull(wellType)){
			sql+=" and liftingType>="+wellType+" and liftingType<("+wellType+"+100) ";
		}
		
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
		return this.getBaseDao().getObjects(hql);

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

	/*
	 * 查询单井工况诊断的泵功图数据
	 * */
	public String queryPumpCard(int id,String wellName,String selectedWellName) throws SQLException, IOException{
		byte[] bytes; 
		BufferedInputStream bis = null;
        StringBuffer dataSbf = new StringBuffer();
        StringBuffer pumpFSDiagramStrBuff = new StringBuffer();
        String tableName="tbl_rpc_diagram_latest";
        if(StringManagerUtils.isNotNull(selectedWellName)){
        	tableName="tbl_rpc_diagram_hist";
        }else{
        	tableName="tbl_rpc_diagram_latest";
        }
        String sql="select well.wellName as wellName, to_char(t.acquisitiontime,'yyyy-mm-dd hh24:mi:ss') as acquisitiontime,"
        		+ " t.pumpfsdiagram,"
        		+ " t.upperloadline,t.lowerloadline, t.fmax,t.fmin,t.stroke,t.spm, t.liquidweightproduction,status.workingconditionname,status.workingconditioncode, "
        		+ " t.rodstring,"
        		+ " t.pumpeff1*100 as pumpeff1, t.pumpeff2*100 as pumpeff2, t.pumpeff3*100 as pumpeff3, t.pumpeff4*100 as pumpeff4,"
        		+ " t.upstrokewattmax,t.downstrokewattmax,t.wattdegreebalance,t.upstrokeimax,t.downstrokeimax,t.idegreebalance,"
        		+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve "
        		+ " from "+tableName+" t, tbl_rpc_worktype status,tbl_wellinformation well  "
        		+ " where t.wellid=well.id and t.workingconditioncode=status.workingconditioncode ";
        if(StringManagerUtils.isNotNull(selectedWellName)){
        	sql+=" and t.id="+id;
        }else{
        	sql+=" and well.wellName='"+wellName+"'";
        }
		List<?> list=this.GetGtData(sql);
		if(list.size()>0){
			Object[] obj=(Object[])list.get(0);
			String positionCurveData="";
			String loadCurveData="";
			String wattCurveData="";
			String iCurveData="";
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
			}
	        if(pumpFSDiagramStrBuff.toString().endsWith(",")){
	        	pumpFSDiagramStrBuff=pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
	        }
	        String pumpFSDiagramData = pumpFSDiagramStrBuff.toString();
	        
	        String rodStressRatio1="0",rodStressRatio2="0",rodStressRatio3="0",rodStressRatio4="0";
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
	        
	        dataSbf.append("{success:true,");
	        dataSbf.append("wellName:\""+wellName+"\",");           // 井名
	        dataSbf.append("acquisitionTime:\""+obj[1]+"\",");         // 时间
	        dataSbf.append("upperLoadLine:\""+obj[3]+"\",");         // 理论上载荷
	        dataSbf.append("lowerLoadLine:\""+obj[4]+"\",");         // 理论下载荷
	        dataSbf.append("fmax:\""+obj[5]+"\",");         // 最大载荷
	        dataSbf.append("fmin:\""+obj[6]+"\",");         // 最小载荷
	        dataSbf.append("stroke:\""+obj[7]+"\",");         // 冲程
	        dataSbf.append("spm:\""+obj[8]+"\",");         // 冲次
	        dataSbf.append("liquidWeightProduction:\""+obj[9]+"\",");         // 日产液量
	        dataSbf.append("workingConditionName:\""+obj[10]+"\",");         // 工况类型
	        dataSbf.append("workingConditionCode:\""+obj[11]+"\",");         // 工况代码
	        
	        dataSbf.append("rodStressRatio1:"+rodStressRatio1+",");       // 一级应力百分比
	        dataSbf.append("rodStressRatio2:"+rodStressRatio2+",");       // 二级应力百分比 
	        dataSbf.append("rodStressRatio3:"+rodStressRatio3+",");           // 三级应力百分比
	        dataSbf.append("rodStressRatio4:"+rodStressRatio4+",");           // 四级应力百分比
	        
	        dataSbf.append("pumpEff1:"+StringManagerUtils.stringToFloat(obj[13].toString(),1)+",");       // 冲程损失系数
	        dataSbf.append("pumpEff2:"+StringManagerUtils.stringToFloat(obj[14].toString(),1)+",");       // 充满系数
	        dataSbf.append("pumpEff3:"+StringManagerUtils.stringToFloat(obj[15].toString(),1)+",");           // 漏失系数
	        dataSbf.append("pumpEff4:"+StringManagerUtils.stringToFloat(obj[16].toString(),1)+",");           // 液体收缩系数
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
	        dataSbf.append("currentCurveData:\""+iCurveData+"\"");         // 工况代码
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
	        dataSbf.append("liquidWeightProduction:\"\",");  
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
	        dataSbf.append("currentCurveData:\"\"");
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
		List<?> list=this.GetGtData(sql);
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
		List<?> list=this.GetGtData(sql);
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
		String sql="";
		String statType="workingConditionName";
		if("1".equalsIgnoreCase(type)){
			statType="workingConditionName";
		}else if("2".equalsIgnoreCase(type)){
			statType="liquidWeightProductionlevel";
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
		}
		
		sql="select "+statType+",count(id) from viw_rpc_comprehensive_latest t where  t.org_id in("+orgId+")";
		
		if(StringManagerUtils.isNotNull(wellType)){
			sql+=" and liftingType>="+wellType+" and liftingType<("+wellType+"+100) ";
		}
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
		String sql="select wattDegreeBalance,iDegreeBalance,"
				+ " liquidWeightProduction,oilWeightProduction,waterCut,"
				+ " theoreticalProduction,availablePlungerstrokeProd,pumpClearanceLeakProd,tvleakWeightProduction,svleakWeightProduction,gasInfluenceProd,"
				+ " pumpBoreDiameter,pumpSettingDepth,producingFluidLevel,submergence,"
				+ " stroke,spm,motorInputActivePower,polishrodPower,waterPower,surfaceSystemEfficiency,welldownSystemEfficiency,systemEfficiency,powerConsumptionPerthm,"
				+ " pumpEff1,pumpEff2,pumpEff3,pumpEff4,pumpEff,"
				+ " rodFlexLength,tubingFlexLength,inertiaLength,"
				+ " pumpintakep,pumpintaket,pumpintakegol,pumpinletvisl,pumpinletbo,"
				+ " pumpoutletp,pumpoutlett,pumpOutletGol,pumpoutletvisl,pumpoutletbo,"
				+ " rodString,"
				+ " upperLoadLineOfExact,"
				+ " tubingPressure,casingPressure,wellheadFluidTemperature,"
				+ " commStatus,runStatus,"
				+ " Ia,Ib,Ic,Va,Vb,Vc,"
				+ " totalWattEnergy,totalVarEnergy,wattSum,varSum,reversePower,pfSum,"
				+ " frequencySetValue,frequencyRunValue,"
				+ " balanceControlMode,balanceCalculateMode,"
				+ " balanceAwayTime,balanceCloseTime,"
				+ " balanceAwayTimePerBeat,balanceCloseTimePerBeat,"
				+ " balanceStrokeCount,"
				+ " balanceOperationUpLimit,balanceOperationDownLimit,"
				+ " balanceAutoControl,spmAutoControl,balanceFrontLimit,balanceAfterLimit,"
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
			result_json.append("\"iDegreeBalance\":\""+obj[1]+"\",");
			result_json.append("\"liquidWeightProduction\":\""+obj[2]+"\",");
			result_json.append("\"oilWeightProduction\":\""+obj[3]+"\",");
			result_json.append("\"waterCut\":\""+obj[4]+"\",");
			result_json.append("\"theoreticalProduction\":\""+obj[5]+"\",");
			result_json.append("\"availablePlungerstrokeProd\":\""+obj[6]+"\",");
			result_json.append("\"pumpClearanceLeakProd\":\""+obj[7]+"\",");
			result_json.append("\"tvleakWeightProduction\":\""+obj[8]+"\",");
			result_json.append("\"svleakWeightProduction\":\""+obj[9]+"\",");
			result_json.append("\"gasInfluenceProd\":\""+obj[10]+"\",");
			result_json.append("\"pumpBoreDiameter\":\""+obj[11]+"\",");
			result_json.append("\"pumpSettingDepth\":\""+obj[12]+"\",");
			result_json.append("\"producingFluidLevel\":\""+obj[13]+"\",");
			result_json.append("\"submergence\":\""+obj[14]+"\",");
			result_json.append("\"stroke\":\""+obj[15]+"\",");
			result_json.append("\"spm\":\""+obj[16]+"\",");
			result_json.append("\"motorInputActivePower\":\""+obj[17]+"\",");
			result_json.append("\"polishrodPower\":\""+obj[18]+"\",");
			result_json.append("\"waterPower\":\""+obj[19]+"\",");
			result_json.append("\"surfaceSystemEfficiency\":\""+obj[20]+"\",");
			result_json.append("\"welldownSystemEfficiency\":\""+obj[21]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[22]+"\",");
			result_json.append("\"powerConsumptionPerthm\":\""+obj[23]+"\",");
			result_json.append("\"pumpEff1\":\""+obj[24]+"\",");
			result_json.append("\"pumpEff2\":\""+obj[25]+"\",");
			result_json.append("\"pumpEff3\":\""+obj[26]+"\",");
			result_json.append("\"pumpEff4\":\""+obj[27]+"\",");
			result_json.append("\"pumpEff\":\""+obj[28]+"\",");
			result_json.append("\"rodFlexLength\":\""+obj[29]+"\",");
			result_json.append("\"tubingFlexLength\":\""+obj[30]+"\",");
			result_json.append("\"inertiaLength\":\""+obj[31]+"\",");
			
			result_json.append("\"pumpintakep\":\""+obj[32]+"\",");
			result_json.append("\"pumpintaket\":\""+obj[33]+"\",");
			result_json.append("\"pumpintakegol\":\""+obj[34]+"\",");
			result_json.append("\"pumpinletvisl\":\""+obj[35]+"\",");
			result_json.append("\"pumpinletbo\":\""+obj[36]+"\",");
			
			result_json.append("\"pumpoutletp\":\""+obj[37]+"\",");
			result_json.append("\"pumpoutlett\":\""+obj[38]+"\",");
			result_json.append("\"pumpOutletGol\":\""+obj[39]+"\",");
			result_json.append("\"pumpoutletvisl\":\""+obj[40]+"\",");
			result_json.append("\"pumpoutletbo\":\""+obj[41]+"\",");
			
			result_json.append("\"rodString\":\""+obj[42]+"\",");
			
			result_json.append("\"upperLoadLineOfExact\":\""+obj[43]+"\",");
			
			result_json.append("\"tubingPressure\":\""+obj[44]+"\",");
			result_json.append("\"casingPressure\":\""+obj[45]+"\",");
			result_json.append("\"wellheadFluidTemperature\":\""+obj[46]+"\",");
			result_json.append("\"commStatus\":\""+obj[47]+"\",");
			result_json.append("\"runStatus\":\""+obj[48]+"\",");
			result_json.append("\"Ia\":\""+obj[49]+"\",");
			result_json.append("\"Ib\":\""+obj[50]+"\",");
			result_json.append("\"Ic\":\""+obj[51]+"\",");
			result_json.append("\"Va\":\""+obj[52]+"\",");
			result_json.append("\"Vb\":\""+obj[53]+"\",");
			result_json.append("\"Vc\":\""+obj[54]+"\",");
			result_json.append("\"totalWattEnergy\":\""+obj[55]+"\",");
			result_json.append("\"totalVarEnergy\":\""+obj[56]+"\",");
			result_json.append("\"wattSum\":\""+obj[57]+"\",");
			result_json.append("\"varSum\":\""+obj[58]+"\",");
			result_json.append("\"reversePower\":\""+obj[59]+"\",");
			result_json.append("\"pfSum\":\""+obj[60]+"\",");
			result_json.append("\"frequencySetValue\":\""+obj[61]+"\",");
			result_json.append("\"frequencyRunValue\":\""+obj[62]+"\",");
			
			result_json.append("\"balanceControlMode\":\""+obj[63]+"\",");
			result_json.append("\"balanceCalculateMode\":\""+obj[64]+"\",");
			result_json.append("\"balanceAwayTime\":\""+obj[65]+"\",");
			result_json.append("\"balanceCloseTime\":\""+obj[66]+"\",");
			
			result_json.append("\"balanceAwayTimePerBeat\":\""+obj[67]+"\",");
			result_json.append("\"balanceCloseTimePerBeat\":\""+obj[68]+"\",");
			
			result_json.append("\"balanceStrokeCount\":\""+obj[69]+"\",");
			result_json.append("\"balanceOperationUpLimit\":\""+obj[70]+"\",");
			result_json.append("\"balanceOperationDownLimit\":\""+obj[71]+"\",");
			result_json.append("\"balanceAutoControl\":\""+obj[72]+"\",");
			result_json.append("\"spmAutoControl\":\""+obj[73]+"\",");
			result_json.append("\"balanceFrontLimit\":\""+obj[74]+"\",");
			result_json.append("\"balanceAfterLimit\":\""+obj[75]+"\",");
			
			result_json.append("\"videourl\":\""+obj[76]+"\",");
			result_json.append("\"runRange\":\""+obj[77]+"\"");
		}
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getDiagnosisDataCurveData(String wellName,String startDate,String endDate,String itemName,String itemCode) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		String uplimit="";
		String downlimit="";
		String sql="select to_char(t.acquisitionTime,'yyyy-mm-dd hh24:mi:ss'),t."+itemCode+" from viw_rpc_diagram_hist t "
				+ " where t.wellName='"+wellName+"' and to_date(to_char(t.acquisitionTime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') order by t.acquisitionTime";
		if("Ia".equalsIgnoreCase(itemCode)||"Ib".equalsIgnoreCase(itemCode)||"Ic".equalsIgnoreCase(itemCode)
				||"Va".equalsIgnoreCase(itemCode)||"Vb".equalsIgnoreCase(itemCode)||"Vc".equalsIgnoreCase(itemCode)){
			itemCode="t."+itemCode+",t."+itemCode+"uplimit,t."+itemCode+"downlimit";
			sql="select to_char(t.acquisitionTime,'yyyy-mm-dd hh24:mi:ss'),"+itemCode+" from viw_rpc_discrete_hist t "
					+ " where t.wellName='"+wellName+"' and to_date(to_char(t.acquisitionTime,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') order by t.acquisitionTime";
		}else if("commStatus".equalsIgnoreCase(itemCode)||"runStatus".equalsIgnoreCase(itemCode)||"tubingpressure".equalsIgnoreCase(itemCode)
				||"casingpressure".equalsIgnoreCase(itemCode)||"backpressure".equalsIgnoreCase(itemCode)||"wellheadfluidtemperature".equalsIgnoreCase(itemCode)
				||"totalWattEnergy".equalsIgnoreCase(itemCode)||"totalVarEnergy".equalsIgnoreCase(itemCode)
				||"wattSum".equalsIgnoreCase(itemCode)||"varSum".equalsIgnoreCase(itemCode)||"reversepower".equalsIgnoreCase(itemCode)||"pfSum".equalsIgnoreCase(itemCode)
				||"frequencyRunValue".equalsIgnoreCase(itemCode)){
			sql="select to_char(t.acquisitionTime,'yyyy-mm-dd hh24:mi:ss'),t."+itemCode+" from viw_rpc_discrete_hist t "
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
	
	public String getScrewPumpRTAnalysiCurveData(String cjsj,String jh) throws SQLException, IOException {
		StringBuffer dynSbf = new StringBuffer();
		float currentauplimit=0;
		float currentadownlimit=0;
		float currentbuplimit=0;
		float currentbdownlimit=0;
		float currentcuplimit=0;
		float currentcdownlimit=0;
		float voltageauplimit=0;
		float voltageadownlimit=0;
		float voltagebuplimit=0;
		float voltagebdownlimit=0;
		float voltagecuplimit=0;
		float voltagecdownlimit=0;
		String sql="select "
				+ " to_char(t.gtcjsj,'yyyy-mm-dd hh24:mi:ss'),t.rpm,t.currenta,t.currentb,t.currentc,t.voltagea,t.voltageb,t.voltagec, "
				+ " t.currentauplimit,t.currentadownlimit,t.currentbuplimit,t.currentbdownlimit,t.currentcuplimit,t.currentcdownlimit, "
				+ " t.voltageauplimit,t.voltageadownlimit,t.voltagebuplimit,t.voltagebdownlimit,t.voltagecuplimit,t.voltagecdownlimit "
				+ " from tbl_rpc_diagram_hist t,tbl_wellinformation t007 "
				+ " where t.jbh=t007.jlbh and t007.jh='"+jh+"' "
				+ " and t.gtcjsj between to_date(to_char(to_date('"+cjsj+"','yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd'),'yyyy-mm-dd') "
				+ " and to_date('"+cjsj+"','yyyy-mm-dd hh24:mi:ss') "
				+ " order by t.gtcjsj";
		
		List<?> list=this.findCallSql(sql);
		
		dynSbf.append("{\"success\":true,\"totalCount\":" + list.size() + ",\"jh\":\""+jh+"\",\"cjsj\":\""+cjsj+"\",\"totalRoot\":[");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				dynSbf.append("{ \"cjsj\":\"" + obj[0] + "\",");
				dynSbf.append("\"rpm\":"+obj[1]+",");
				dynSbf.append("\"currenta\":"+obj[2]+",");
				dynSbf.append("\"currentb\":"+obj[3]+",");
				dynSbf.append("\"currentc\":"+obj[4]+",");
				dynSbf.append("\"voltagea\":"+obj[5]+",");
				dynSbf.append("\"voltageb\":"+obj[6]+",");
				dynSbf.append("\"voltagec\":"+obj[7]+"},");
				if(i==list.size()-1){
					currentauplimit=StringManagerUtils.stringToFloat(obj[8]+"");
					currentadownlimit=StringManagerUtils.stringToFloat(obj[9]+"");
					currentbuplimit=StringManagerUtils.stringToFloat(obj[10]+"");
					currentbdownlimit=StringManagerUtils.stringToFloat(obj[11]+"");
					currentcuplimit=StringManagerUtils.stringToFloat(obj[12]+"");
					currentcdownlimit=StringManagerUtils.stringToFloat(obj[13]+"");
					voltageauplimit=StringManagerUtils.stringToFloat(obj[14]+"");
					voltageadownlimit=StringManagerUtils.stringToFloat(obj[15]+"");
					voltagebuplimit=StringManagerUtils.stringToFloat(obj[16]+"");
					voltagebdownlimit=StringManagerUtils.stringToFloat(obj[17]+"");
					voltagecuplimit=StringManagerUtils.stringToFloat(obj[18]+"");
					voltagecdownlimit=StringManagerUtils.stringToFloat(obj[19]+"");
				}
			}
			dynSbf.deleteCharAt(dynSbf.length() - 1);
		}
		dynSbf.append("],");
		dynSbf.append("\"currentauplimit\":"+currentauplimit+",\"currentadownlimit\":"+currentadownlimit+",");
		dynSbf.append("\"currentbuplimit\":"+currentbuplimit+",\"currentbdownlimit\":"+currentbdownlimit+",");
		dynSbf.append("\"currentcuplimit\":"+currentcuplimit+",\"currentcdownlimit\":"+currentcdownlimit+",");
		dynSbf.append("\"voltageauplimit\":"+voltageauplimit+",\"voltageadownlimit\":"+voltageadownlimit+",");
		dynSbf.append("\"voltagebuplimit\":"+voltagebuplimit+",\"voltagebdownlimit\":"+voltagebdownlimit+",");
		dynSbf.append("\"voltagecuplimit\":"+voltagecuplimit+",\"voltagecdownlimit\":"+voltagecdownlimit+"");
		dynSbf.append("}");
		return dynSbf.toString();
	}
	
	public BaseDao getDao() {
		return dao;
	}

	@Resource
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
}
