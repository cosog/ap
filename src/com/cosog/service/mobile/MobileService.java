package com.cosog.service.mobile;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cosog.dao.BaseDao;
import com.cosog.model.Org;
import com.cosog.model.data.DataDictionary;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.CLOB;

import org.hibernate.engine.jdbc.SerializableClobProxy;


@SuppressWarnings("deprecation")
@Component("mobileService")
public class MobileService<T> extends BaseService<T> {
	@SuppressWarnings("unused")
	private BaseDao dao;
	@SuppressWarnings("unused")
	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	
	public List<T> getOrganizationData(Class<Org> class1, String userAccount) {
		String queryString="";
		if(StringManagerUtils.isNotNull(userAccount)){
			String sql="select t.user_orgid from tbl_user t where t.user_id='"+userAccount+"'";
			List<?> list = this.findCallSql(sql);
			String orgId="";
			if(list.size()>0){
				orgId=list.get(0).toString();
			}
			if("0".equals(orgId)){
				queryString = "SELECT {Org.*} FROM tbl_org Org   "
//						+ " start with Org.org_id=1  "
//						+ " connect by Org.org_parent= prior Org.org_id "
						+ " order by Org.org_code  ";
			}else{
				queryString = "SELECT {Org.*} FROM tbl_org Org   "
						+ " start with Org.org_id=( select t2.user_orgid from tbl_user t2 where t2.user_id='"+userAccount+"' )   "
						+ " connect by Org.org_parent= prior Org.org_id "
						+ " order by Org.org_code  ";
			}
		}else{
			queryString = "SELECT {Org.*} FROM tbl_org Org   "
//					+ " start with Org.org_id=1  "
//					+ " connect by Org.org_parent= prior Org.org_id "
					+ " order by Org.org_code  ";
		}
		
		return getBaseDao().getSqlToHqlOrgObjects(queryString);
	}
	
	public String getPumpingRealtimeStatisticsDataByWellList(String data){
		StringBuffer wells= new StringBuffer();
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int liftingType=1;
		int type=1;
		if(StringManagerUtils.isNotNull(data)){
			try{
				JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
				
				liftingType=jsonObject.getInt("LiftingType");
				type=jsonObject.getInt("StatType");
				JSONArray jsonArray = jsonObject.getJSONArray("WellList");
				for(int i=0;jsonArray!=null&&i<jsonArray.size();i++){
					wells.append("'"+jsonArray.getString(i)+"',");
				}
				if(wells.toString().endsWith(",")){
					wells.deleteCharAt(wells.length() - 1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		String sql="";
		String statType="resultName";
		String tableName="viw_rpc_comprehensive_latest";
		if(type==1||type==0){
			if(liftingType!=2){
				statType="resultName";
			}else{
				statType="resultName_E";
			}
		}else if(type==2){
			statType="liquidWeightProductionlevel";
			if(configFile.getOthers().getProductionUnit()!=0){
				statType="liquidVolumeProductionlevel";
			}
		}else if(type==3){
			statType="wattDegreeBalanceName";
		}else if(type==4){
			statType="iDegreeBalanceName";
		}else if(type==5){
			statType="systemEfficiencyLevel";
		}else if(type==6){
			statType="surfaceSystemEfficiencyLevel";
		}else if(type==7){
			statType="wellDownSystemEfficiencyLevel";
		}else if(type==8){
			statType="todayKWattHLevel";
		}else if(type==9){
			statType="commStatusName";
		}else if(type==10){
			statType="commtimeefficiencyLevel";
		}else if(type==11){
			statType="runStatusName";
		}else if(type==12){
			statType="runtimeEfficiencyLevel";
		}
		if(liftingType!=2){
			tableName="viw_rpc_comprehensive_latest";
		}else{
			tableName="viw_pcp_comprehensive_latest";
		}
		sql="select "+statType+",count(1) from "+tableName+" t where 1=1 ";
		if(StringManagerUtils.isNotNull(wells.toString())){
			sql+=" and  t.wellName in("+wells.toString()+")";
		}
		sql+=" group by "+statType;
		
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			if(StringManagerUtils.isNotNull(obj[0]+"")){
				result_json.append("{\"Item\":\""+obj[0]+"\",");
				result_json.append("\"Count\":"+obj[1]+"},");
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getOilWellRealtimeWellListData(String data,Page pager)throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer wells= new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int liftingType=1;
		int type=1;
		String statValue="";
		try{
			JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
			liftingType=jsonObject.getInt("LiftingType");
			type=jsonObject.getInt("StatType");
			statValue=jsonObject.getString("StatValue");
			JSONArray jsonArray = jsonObject.getJSONArray("WellList");
			for(int i=0;jsonArray!=null&&i<jsonArray.size();i++){
				wells.append("'"+jsonArray.getString(i)+"',");
			}
			if(wells.toString().endsWith(",")){
				wells.deleteCharAt(wells.length() - 1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String sql="";
		String finalSql="";
		String sqlAll="";
		String tableName_latest="viw_rpc_comprehensive_latest";
		String typeColumnName="resultName";
		if(type==1){
			typeColumnName="resultName";
		}else if(type==2){
			typeColumnName="liquidWeightProductionlevel";
			if(configFile.getOthers().getProductionUnit()!=0){
				typeColumnName="liquidVolumeProductionlevel";
			}
		}else if(type==3){
			typeColumnName="wattDegreeBalanceName";
		}else if(type==4){
			typeColumnName="iDegreeBalanceName";
		}else if(type==5){
			typeColumnName="systemEfficiencyLevel";
		}else if(type==6){
			typeColumnName="surfaceSystemEfficiencyLevel";
		}else if(type==7){
			typeColumnName="wellDownSystemEfficiencyLevel";
		}else if(type==8){
			typeColumnName="todayKWattHLevel";
		}else if(type==9){
			typeColumnName="runStatusName";
		}else if(type==10){
			typeColumnName="runtimeEfficiencyLevel";
		}else if(type==11){
			typeColumnName="commStatusName";
		}else if(type==12){
			typeColumnName="commtimeefficiencyLevel";
		}else{
			typeColumnName="resultName";
		}
		if(liftingType==2){//螺杆泵井
			tableName_latest="viw_pcp_comprehensive_latest";
			sql="select id,wellName,to_char(acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,resultName,optimizationSuggestion,"
					+ "commStatusName,commTime,commTimeEfficiency,commRange,"
					+ "runStatusName,runTime,runTimeEfficiency,runRange,"
					+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,weightWaterCut,liquidWeightProduction_L,oilWeightProduction_L,waterWeightProduction_L,"
					+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,volumeWaterCut,liquidVolumetricProduction_L,oilVolumetricProduction_L,waterVolumetricProduction_L,"
					+ "deltaRadius,wattDegreeBalanceName,wattDegreeBalance,wattRatio,iDegreeBalanceName,iDegreeBalance,iRatio,"
					+ "systemEfficiency,surfaceSystemEfficiency,welldownSystemEfficiency,energyPer100mLift,todayKWattH ,"
					+ "resultAlarmLevel,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel";
		}else{//默认为抽油机
			tableName_latest="viw_rpc_comprehensive_latest";
			sql="select id,wellName,to_char(acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,resultName,optimizationSuggestion,"
					+ "commStatusName,commTime,commTimeEfficiency,commRange,"
					+ "runStatusName,runTime,runTimeEfficiency,runRange,"
					+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,weightWaterCut,liquidWeightProduction_L,oilWeightProduction_L,waterWeightProduction_L,"
					+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,volumeWaterCut,liquidVolumetricProduction_L,oilVolumetricProduction_L,waterVolumetricProduction_L,"
					+ "deltaRadius,wattDegreeBalanceName,wattDegreeBalance,wattRatio,iDegreeBalanceName,iDegreeBalance,iRatio,"
					+ "systemEfficiency,surfaceSystemEfficiency,welldownSystemEfficiency,energyPer100mLift,todayKWattH ,"
					+ "resultAlarmLevel,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel";
		}
		sql+= " from "+tableName_latest+" t where 1=1 ";
		if(StringManagerUtils.isNotNull(wells.toString())){
			sql+= " and t.wellName in("+wells.toString()+")";
		}	
		if(StringManagerUtils.isNotNull(statValue)){
			sql+=" and "+typeColumnName+"='"+statValue+"' ";
		}
		sql+=" order by t.sortNum, t.wellName";
		
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			if(StringManagerUtils.isNotNull(obj[0]+"")){
				result_json.append("{\"id\":"+obj[0]+",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"acqTime\":\""+obj[2]+"\",");
				result_json.append("\"resultName\":\""+obj[3]+"\",");
				result_json.append("\"optimizationSuggestion\":\""+obj[4]+"\",");
				
				result_json.append("\"commStatus\":\""+obj[5]+"\",");
				result_json.append("\"commTime\":"+obj[6]+",");
				result_json.append("\"commTimeEfficiency\":"+obj[7]+",");
				result_json.append("\"commRange\":\""+obj[8]+"\",");
				
				result_json.append("\"runStatus\":\""+obj[9]+"\",");
				result_json.append("\"runTime\":"+obj[10]+",");
				result_json.append("\"runTimeEfficiency\":"+obj[11]+",");
				result_json.append("\"runRange\":\""+obj[12]+"\",");
				
				result_json.append("\"liquidWeightProduction\":"+obj[13]+",");
				result_json.append("\"oilWeightProduction\":"+obj[14]+",");
				result_json.append("\"waterWeightProduction\":"+obj[15]+",");
				result_json.append("\"weightWaterCut\":"+obj[16]+",");
				result_json.append("\"liquidWeightProduction_L\":"+obj[17]+",");
				result_json.append("\"oilWeightProduction_L\":"+obj[18]+",");
				result_json.append("\"waterWeightProduction_L\":"+obj[19]+",");
				
				result_json.append("\"liquidVolumetricProduction\":"+obj[20]+",");
				result_json.append("\"oilVolumetricProduction\":"+obj[21]+",");
				result_json.append("\"waterVolumetricProduction\":"+obj[22]+",");
				result_json.append("\"volumeWaterCut\":"+obj[23]+",");
				result_json.append("\"liquidVolumetricProduction_L\":"+obj[24]+",");
				result_json.append("\"oilVolumetricProduction_L\":"+obj[25]+",");
				result_json.append("\"waterVolumetricProduction_L\":"+obj[26]+",");
				
				result_json.append("\"deltaRadius\":"+obj[27]+",");
				result_json.append("\"wattDegreeBalanceName\":\""+obj[28]+"\",");
				result_json.append("\"wattDegreeBalance\":"+obj[29]+",");
				result_json.append("\"wattRatio\":\""+obj[30]+"\",");
				result_json.append("\"iDegreeBalanceName\":\""+obj[31]+"\",");
				result_json.append("\"iDegreeBalance\":"+obj[32]+",");
				result_json.append("\"iRatio\":\""+obj[33]+"\",");
				
				result_json.append("\"systemEfficiency\":"+obj[34]+",");
				result_json.append("\"surfaceSystemEfficiency\":"+obj[35]+",");
				result_json.append("\"welldownSystemEfficiency\":"+obj[36]+",");
				result_json.append("\"energyPer100mLift\":"+obj[37]+",");
				result_json.append("\"todayKWattH\":"+obj[38]+",");
				
				result_json.append("\"resultAlarmLevel\":"+obj[39]+",");
				result_json.append("\"commAlarmLevel\":"+obj[40]+",");
				result_json.append("\"runAlarmLevel\":"+obj[41]+",");
				result_json.append("\"iDegreeBalanceAlarmLevel\":"+obj[42]+",");
				result_json.append("\"wattDegreeBalanceAlarmLevel\":"+obj[43]+"},");
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getOilWellRealtimeWellHistoryData(String data,Page pager)throws Exception {
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		
		int liftingType=1;
		int type=1;
		String statValue="";
		String wellName="";
		String startDate=StringManagerUtils.getCurrentTime();
		String endDate=StringManagerUtils.getCurrentTime();
		try{
			JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
			try{
				liftingType=jsonObject.getInt("LiftingType");
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try{
				type=jsonObject.getInt("StatType");
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try{
				statValue=jsonObject.getString("StatValue");
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try{
				wellName=jsonObject.getString("WellName");
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try{
				startDate=jsonObject.getString("StartDate");
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try{
				endDate=jsonObject.getString("EndDate");
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String sql="";
		String tableName_hist="viw_rpc_comprehensive_hist";
		String typeColumnName="resultName";
		if(type==1){
			typeColumnName="resultName";
		}else if(type==2){
			typeColumnName="liquidWeightProductionlevel";
			if(configFile.getOthers().getProductionUnit()!=0){
				typeColumnName="liquidVolumeProductionlevel";
			}
		}else if(type==3){
			typeColumnName="wattDegreeBalanceName";
		}else if(type==4){
			typeColumnName="iDegreeBalanceName";
		}else if(type==5){
			typeColumnName="systemEfficiencyLevel";
		}else if(type==6){
			typeColumnName="surfaceSystemEfficiencyLevel";
		}else if(type==7){
			typeColumnName="wellDownSystemEfficiencyLevel";
		}else if(type==8){
			typeColumnName="todayKWattHLevel";
		}else if(type==9){
			typeColumnName="runStatusName";
		}else if(type==10){
			typeColumnName="runtimeEfficiencyLevel";
		}else if(type==11){
			typeColumnName="commStatusName";
		}else if(type==12){
			typeColumnName="commtimeefficiencyLevel";
		}else{
			typeColumnName="resultName";
		}
		if(liftingType==2){//螺杆泵井
			tableName_hist="viw_pcp_comprehensive_hist";
			sql="select id,wellName,to_char(acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,resultName,optimizationSuggestion,"
					+ "commStatusName,commTime,commTimeEfficiency,commRange,"
					+ "runStatusName,runTime,runTimeEfficiency,runRange,"
					+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,weightWaterCut,liquidWeightProduction_L,oilWeightProduction_L,waterWeightProduction_L,"
					+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,volumeWaterCut,liquidVolumetricProduction_L,oilVolumetricProduction_L,waterVolumetricProduction_L,"
					+ "deltaRadius,wattDegreeBalanceName,wattDegreeBalance,wattRatio,iDegreeBalanceName,iDegreeBalance,iRatio,"
					+ "systemEfficiency,surfaceSystemEfficiency,welldownSystemEfficiency,energyPer100mLift,todayKWattH ,"
					+ "resultAlarmLevel,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel";
		}else{//默认为抽油机
			tableName_hist="viw_rpc_comprehensive_hist";
			sql="select id,wellName,to_char(acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,resultName,optimizationSuggestion,"
					+ "commStatusName,commTime,commTimeEfficiency,commRange,"
					+ "runStatusName,runTime,runTimeEfficiency,runRange,"
					+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,weightWaterCut,liquidWeightProduction_L,oilWeightProduction_L,waterWeightProduction_L,"
					+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,volumeWaterCut,liquidVolumetricProduction_L,oilVolumetricProduction_L,waterVolumetricProduction_L,"
					+ "deltaRadius,wattDegreeBalanceName,wattDegreeBalance,wattRatio,iDegreeBalanceName,iDegreeBalance,iRatio,"
					+ "systemEfficiency,surfaceSystemEfficiency,welldownSystemEfficiency,energyPer100mLift,todayKWattH ,"
					+ "resultAlarmLevel,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel";
		}
		sql+= " from "+tableName_hist+" t where 1=1";
		sql+=" and t.acqTime between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') + 1 ";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+=" and  t.wellName = '" + wellName.trim() + "'";
		}
		if(StringManagerUtils.isNotNull(statValue)){
			sql+=" and "+typeColumnName+"='"+statValue+"' ";
		}
		sql+= " order by t.acqTime desc";
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			if(StringManagerUtils.isNotNull(obj[0]+"")){
				result_json.append("{\"id\":"+obj[0]+",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"acqTime\":\""+obj[2]+"\",");
				result_json.append("\"resultName\":\""+obj[3]+"\",");
				result_json.append("\"optimizationSuggestion\":\""+obj[4]+"\",");
				
				result_json.append("\"commStatus\":\""+obj[5]+"\",");
				result_json.append("\"commTime\":"+obj[6]+",");
				result_json.append("\"commTimeEfficiency\":"+obj[7]+",");
				result_json.append("\"commRange\":\""+obj[8]+"\",");
				
				result_json.append("\"runStatus\":\""+obj[9]+"\",");
				result_json.append("\"runTime\":"+obj[10]+",");
				result_json.append("\"runTimeEfficiency\":"+obj[11]+",");
				result_json.append("\"runRange\":\""+obj[12]+"\",");
				
				result_json.append("\"liquidWeightProduction\":"+obj[13]+",");
				result_json.append("\"oilWeightProduction\":"+obj[14]+",");
				result_json.append("\"waterWeightProduction\":"+obj[15]+",");
				result_json.append("\"weightWaterCut\":"+obj[16]+",");
				result_json.append("\"liquidWeightProduction_L\":"+obj[17]+",");
				result_json.append("\"oilWeightProduction_L\":"+obj[18]+",");
				result_json.append("\"waterWeightProduction_L\":"+obj[19]+",");
				
				result_json.append("\"liquidVolumetricProduction\":"+obj[20]+",");
				result_json.append("\"oilVolumetricProduction\":"+obj[21]+",");
				result_json.append("\"waterVolumetricProduction\":"+obj[22]+",");
				result_json.append("\"volumeWaterCut\":"+obj[23]+",");
				result_json.append("\"liquidVolumetricProduction_L\":"+obj[24]+",");
				result_json.append("\"oilVolumetricProduction_L\":"+obj[25]+",");
				result_json.append("\"waterVolumetricProduction_L\":"+obj[26]+",");
				
				result_json.append("\"deltaRadius\":"+obj[27]+",");
				result_json.append("\"wattDegreeBalanceName\":\""+obj[28]+"\",");
				result_json.append("\"wattDegreeBalance\":"+obj[29]+",");
				result_json.append("\"wattRatio\":\""+obj[30]+"\",");
				result_json.append("\"iDegreeBalanceName\":\""+obj[31]+"\",");
				result_json.append("\"iDegreeBalance\":"+obj[32]+",");
				result_json.append("\"iRatio\":\""+obj[33]+"\",");
				
				result_json.append("\"systemEfficiency\":"+obj[34]+",");
				result_json.append("\"surfaceSystemEfficiency\":"+obj[35]+",");
				result_json.append("\"welldownSystemEfficiency\":"+obj[36]+",");
				result_json.append("\"energyPer100mLift\":"+obj[37]+",");
				result_json.append("\"todayKWattH\":"+obj[38]+",");
				
				result_json.append("\"resultAlarmLevel\":"+obj[39]+",");
				result_json.append("\"commAlarmLevel\":"+obj[40]+",");
				result_json.append("\"runAlarmLevel\":"+obj[41]+",");
				result_json.append("\"iDegreeBalanceAlarmLevel\":"+obj[42]+",");
				result_json.append("\"wattDegreeBalanceAlarmLevel\":"+obj[43]+"},");
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getOilWellRealtimeWellAnalysisData(String data) throws SQLException, IOException{
		String json="";
		try{
			JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
			if(jsonObject!=null){
				int liftingType=jsonObject.getInt("LiftingType");
				if(liftingType!=2){
					json=getPumpunitRealtimeWellAnalysisData(data);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}
	
	public String getPumpunitRealtimeWellAnalysisData(String data) throws SQLException, IOException{
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String wellName="";
		String acqTime="";
		try{
			JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
			String json="";
			if(jsonObject!=null){
				
				try{
					wellName=jsonObject.getString("WellName");
				}catch(Exception e){
					e.printStackTrace();
				}
				try{
					acqTime=jsonObject.getString("AcqTime");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				if(StringManagerUtils.isNotNull(wellName) && StringManagerUtils.isNotNull(acqTime)){
					String prodCol=" liquidWeightProduction,oilWeightProduction,waterWeightProduction,weightWaterCut,"
							+ " availablePlungerstrokeProd_W,pumpClearanceLeakProd_W,tvleakWeightProduction,svleakWeightProduction,gasInfluenceProd_W,";
					if(configFile.getOthers().getProductionUnit()!=0){
						prodCol=" liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,volumeWaterCut,"
								+ " availablePlungerstrokeProd_V,pumpClearanceLeakProd_V,tvleakVolumetricProduction,svleakVolumetricProduction,gasInfluenceProd_V,";;
					}
					String tableName="viw_rpc_comprehensive_hist";
					String sql="select resultName,"
							+ " wattDegreeBalance,wattRatio,iDegreeBalance,iRatio,deltaRadius,"
							+ prodCol
							+ " theoreticalProduction,"
							+ " plungerstroke,availableplungerstroke,"
							+ " pumpBoreDiameter,pumpSettingDepth,producingFluidLevel,submergence,"
							+ " stroke,spm,fmax,fmin,deltaF,deltaLoadLine,area,"
							+ " averageWatt,polishrodPower,waterPower,surfaceSystemEfficiency,welldownSystemEfficiency,systemEfficiency,energyPer100mLift,"
							+ " pumpEff1,pumpEff2,pumpEff3,pumpEff4,pumpEff,"
							+ " rodFlexLength,tubingFlexLength,inertiaLength,"
							+ " pumpintakep,pumpintaket,pumpintakegol,pumpintakevisl,pumpintakebo,"
							+ " pumpoutletp,pumpoutlett,pumpOutletGol,pumpoutletvisl,pumpoutletbo"
							+ " from "+tableName+" t where wellname='"+wellName+"' and t.acqtime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')";
					List<?> list = this.findCallSql(sql);
					DataDictionary ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId("realtimeAnalysis");
					ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId("realtimeAcquisition");
					result_json.append("{ \"success\":true,");
					if(list.size()>0){
						Object[] obj=(Object[]) list.get(0);
						result_json.append("\"resultName\":\""+obj[0]+"\",");
						
						result_json.append("\"wattDegreeBalance\":\""+obj[1]+"\",");
						result_json.append("\"wattRatio\":\""+obj[2]+"\",");
						result_json.append("\"iDegreeBalance\":\""+obj[3]+"\",");
						result_json.append("\"iRatio\":\""+obj[4]+"\",");
						result_json.append("\"deltaRadius\":\""+obj[5]+"\",");
						
						result_json.append("\"liquidProduction\":\""+obj[6]+"\",");
						result_json.append("\"oilProduction\":\""+obj[7]+"\",");
						result_json.append("\"waterProduction\":\""+obj[8]+"\",");
						result_json.append("\"waterCut\":\""+obj[9]+"\",");
						
						result_json.append("\"availablePlungerstrokeProd\":\""+obj[10]+"\",");
						result_json.append("\"pumpClearanceLeakProd\":\""+obj[11]+"\",");
						result_json.append("\"tvleakProduction\":\""+obj[12]+"\",");
						result_json.append("\"svleakProduction\":\""+obj[13]+"\",");
						result_json.append("\"gasInfluenceProd\":\""+obj[14]+"\",");
						
						result_json.append("\"theoreticalProduction\":\""+obj[15]+"\",");
						result_json.append("\"plungerStroke\":\""+obj[16]+"\",");
						result_json.append("\"availablePlungerStroke\":\""+obj[17]+"\",");
						
						result_json.append("\"pumpBoreDiameter\":\""+obj[18]+"\",");
						result_json.append("\"pumpSettingDepth\":\""+obj[19]+"\",");
						result_json.append("\"producingFluidLevel\":\""+obj[20]+"\",");
						result_json.append("\"submergence\":\""+obj[21]+"\",");
						
						result_json.append("\"stroke\":\""+obj[22]+"\",");
						result_json.append("\"spm\":\""+obj[23]+"\",");
						result_json.append("\"fmax\":\""+obj[24]+"\",");
						result_json.append("\"fmin\":\""+obj[25]+"\",");
						result_json.append("\"deltaF\":\""+obj[26]+"\",");
						result_json.append("\"deltaLoadLine\":\""+obj[27]+"\",");
						result_json.append("\"area\":\""+obj[28]+"\",");
						
						result_json.append("\"averageWatt\":\""+obj[29]+"\",");
						result_json.append("\"polishrodPower\":\""+obj[30]+"\",");
						result_json.append("\"waterPower\":\""+obj[31]+"\",");
						result_json.append("\"surfaceSystemEfficiency\":\""+obj[32]+"\",");
						result_json.append("\"welldownSystemEfficiency\":\""+obj[33]+"\",");
						result_json.append("\"systemEfficiency\":\""+obj[34]+"\",");
						result_json.append("\"energyPer100mLift\":\""+obj[35]+"\",");
						result_json.append("\"pumpEff1\":\""+obj[36]+"\",");
						result_json.append("\"pumpEff2\":\""+obj[37]+"\",");
						result_json.append("\"pumpEff3\":\""+obj[38]+"\",");
						result_json.append("\"pumpEff4\":\""+obj[39]+"\",");
						result_json.append("\"pumpEff\":\""+obj[40]+"\",");
						result_json.append("\"rodFlexLength\":\""+obj[41]+"\",");
						result_json.append("\"tubingFlexLength\":\""+obj[42]+"\",");
						result_json.append("\"inertiaLength\":\""+obj[43]+"\",");
						
						result_json.append("\"pumpIntakeP\":\""+obj[44]+"\",");
						result_json.append("\"pumpIntakeT\":\""+obj[45]+"\",");
						result_json.append("\"pumpIntakeGOL\":\""+obj[46]+"\",");
						result_json.append("\"pumpIntakeVisl\":\""+obj[47]+"\",");
						result_json.append("\"pumpIntakeBo\":\""+obj[48]+"\",");
						
						result_json.append("\"pumpOutletP\":\""+obj[49]+"\",");
						result_json.append("\"pumpOutletT\":\""+obj[50]+"\",");
						result_json.append("\"pumpOutletGOL\":\""+obj[51]+"\",");
						result_json.append("\"pumpOutletVisl\":\""+obj[52]+"\",");
						result_json.append("\"pumpOutletBo\":\""+obj[53]+"\"");
					}
					result_json.append("}");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result_json.toString().replaceAll("null", "");
	}
	
	public String getOilWellTotalStatisticsData(String data){
		StringBuffer wells= new StringBuffer();
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int liftingType=1;
		int type=1;
		String date=StringManagerUtils.getCurrentTime();
		try{
			JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
			
			try{
				liftingType=jsonObject.getInt("LiftingType");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				type=jsonObject.getInt("StatType");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				date=jsonObject.getString("Date");
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try{
				JSONArray jsonArray = jsonObject.getJSONArray("WellList");
				for(int i=0;jsonArray!=null&&i<jsonArray.size();i++){
					wells.append("'"+jsonArray.getString(i)+"',");
				}
				if(wells.toString().endsWith(",")){
					wells.deleteCharAt(wells.length() - 1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		String sql="";
		String tableName="viw_rpc_total_day";
		String statType="resultName";
		if(type==1){
			statType="resultName";
		}else if(type==2){
			statType="liquidWeightProductionlevel";
			if(configFile.getOthers().getProductionUnit()!=0){
				statType="liquidVolumeProductionlevel";
			}
		}else if(type==3){
			statType="wattdegreebalanceLevel";
		}else if(type==4){
			statType="idegreebalanceLevel";
		}else if(type==5){
			statType="systemEfficiencyLevel";
		}else if(type==6){
			statType="surfaceSystemEfficiencyLevel";
		}else if(type==7){
			statType="wellDownSystemEfficiencyLevel";
		}else if(type==8){
			statType="todayKWattHLevel";
		}else if(type==9){
			statType="runStatusName";
		}else if(type==10){
			statType="runtimeEfficiencyLevel";
		}else if(type==11){
			statType="commStatusName";
		}else if(type==12){
			statType="commtimeefficiencyLevel";
		}
		if(liftingType!=2){
			tableName="viw_rpc_total_day";
		}else{
			tableName="viw_pcp_total_day";
		}
		sql="select "+statType+", count(1) from "+tableName+" t where calculateDate=to_date('"+date+"','yyyy-mm-dd') ";
		if(StringManagerUtils.isNotNull(wells.toString())){
			sql+= " and wellname in ("+wells.toString()+")";
		}
		sql+=" group by rollup("+statType+")";
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,\"date\":\""+date+"\",");
		result_json.append("\"totalRoot\":[");
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
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getOilWellTotalWellListData(String data,Page pager)throws Exception {
		StringBuffer wells= new StringBuffer();
		String getResult="";
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int liftingType=1;
		String date=StringManagerUtils.getCurrentTime();
		int type=1;
		String statValue="";
		try{
			JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
			
			try{
				liftingType=jsonObject.getInt("LiftingType");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				date=jsonObject.getString("Date");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				type=jsonObject.getInt("StatType");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				statValue=jsonObject.getString("StatValue");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				JSONArray jsonArray = jsonObject.getJSONArray("WellList");
				for(int i=0;jsonArray!=null&&i<jsonArray.size();i++){
					wells.append("'"+jsonArray.getString(i)+"',");
				}
				if(wells.toString().endsWith(",")){
					wells.deleteCharAt(wells.length() - 1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
			liftingType=1;
			date=StringManagerUtils.getCurrentTime();
			type=1;
			statValue="";
		}
		String tableName="viw_rpc_total_day";
		String typeColumnName="resultName";
		if(type==1){
			typeColumnName="resultName";
		}else if(type==2){
			typeColumnName="liquidWeightProductionlevel";
			if(configFile.getOthers().getProductionUnit()!=0){
				typeColumnName="liquidVolumeProductionlevel";
			}
		}else if(type==3){
			typeColumnName="wattdegreebalanceLevel";
		}else if(type==4){
			typeColumnName="idegreebalanceLevel";
		}else if(type==5){
			typeColumnName="systemEfficiencyLevel";
		}else if(type==6){
			typeColumnName="surfaceSystemEfficiencyLevel";
		}else if(type==7){
			typeColumnName="wellDownSystemEfficiencyLevel";
		}else if(type==8){
			typeColumnName="todayKWattHLevel";
		}else if(type==9){
			typeColumnName="commStatusName";
		}else if(type==10){
			typeColumnName="commtimeefficiencyLevel";
		}else if(type==11){
			typeColumnName="runStatusName";
		}else if(type==12){
			typeColumnName="runtimeEfficiencyLevel";
		}
		String sql="";
		if(liftingType!=2){
			sql= "select id,wellName,to_char(calculateDate,'yyyy-mm-dd') as calculateDate,to_char(acquisitionDate,'yyyy-mm-dd') as acquisitionDate,ExtendedDays,"
					+ "commStatusName,commTime,commTimeEfficiency,commRange,"
					+ "runStatusName,runTime,runTimeEfficiency,runRange,"
					+ "resultName,optimizationSuggestion,"
					+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,weightWaterCut,"
					+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,volumeWaterCut,"
					+ "wattDegreeBalanceLevel,wattDegreeBalance,iDegreeBalanceLevel,iDegreeBalance,deltaRadius,"
					+ "systemEfficiency,surfaceSystemEfficiency,welldownSystemEfficiency,energyPer100mLift,todayKWattH ,"
					+ "resultAlarmLevel,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel  ";
			tableName="viw_rpc_total_day";
		}else{
			sql= "select id,wellName,to_char(calculateDate,'yyyy-mm-dd') as calculateDate,to_char(acquisitionDate,'yyyy-mm-dd') as acquisitionDate,ExtendedDays,"
					+ "resultName,optimizationSuggestion,"
					+ "commStatusName,commTime,commTimeEfficiency,commRange,"
					+ "runStatusName,runTime,runTimeEfficiency,runRange,"
					+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,weightWaterCut,"
					+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,volumeWaterCut,"
					+ "wattDegreeBalanceLevel,wattDegreeBalance,iDegreeBalanceLevel,iDegreeBalance,deltaRadius,"
					+ "systemEfficiency,surfaceSystemEfficiency,welldownSystemEfficiency,energyPer100mLift,todayKWattH ,"
					+ "resultAlarmLevel,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel  ";
			tableName="viw_pcp_total_day";
		}
				
		sql+= " from "+tableName+" t where 1=1 ";
		if(StringManagerUtils.isNotNull(wells.toString())){
			sql+= " and t.wellname in ("+wells.toString()+") ";
		}
		
		sql+=" and t.calculateDate=to_date('"+date+"','yyyy-mm-dd') ";
		if(StringManagerUtils.isNotNull(statValue)){
			sql+=" and "+typeColumnName+"='"+statValue+"'";
		}
		sql+=" order by t.sortnum, t.wellName";
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			if(StringManagerUtils.isNotNull(obj[0]+"")){
				result_json.append("{\"id\":"+obj[0]+",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"calculateDate\":\""+obj[2]+"\",");
				result_json.append("\"acquisitionDate\":\""+obj[3]+"\",");
				result_json.append("\"extendedDays\":"+obj[4]+",");
				
				result_json.append("\"resultName\":\""+obj[5]+"\",");
				result_json.append("\"optimizationSuggestion\":\""+obj[6]+"\",");
				
				result_json.append("\"commStatus\":\""+obj[7]+"\",");
				result_json.append("\"commTime\":"+obj[5]+",");
				result_json.append("\"commTimeEfficiency\":"+obj[9]+",");
				result_json.append("\"commRange\":\""+obj[10]+"\",");
				
				result_json.append("\"runStatus\":\""+obj[11]+"\",");
				result_json.append("\"runTime\":"+obj[12]+",");
				result_json.append("\"runTimeEfficiency\":"+obj[13]+",");
				result_json.append("\"runRange\":\""+obj[14]+"\",");
				
				result_json.append("\"liquidWeightProduction\":"+obj[15]+",");
				result_json.append("\"oilWeightProduction\":"+obj[16]+",");
				result_json.append("\"waterWeightProduction\":"+obj[17]+",");
				result_json.append("\"weightWaterCut\":"+obj[18]+",");
				
				result_json.append("\"liquidVolumetricProduction\":"+obj[19]+",");
				result_json.append("\"oilVolumetricProduction\":"+obj[20]+",");
				result_json.append("\"waterVolumetricProduction\":"+obj[21]+",");
				result_json.append("\"volumeWaterCut\":"+obj[22]+",");
				
				result_json.append("\"wattDegreeBalanceName\":\""+obj[23]+"\",");
				result_json.append("\"wattDegreeBalance\":"+obj[24]+",");
				result_json.append("\"iDegreeBalanceName\":\""+obj[25]+"\",");
				result_json.append("\"iDegreeBalance\":"+obj[26]+",");
				result_json.append("\"deltaRadius\":"+obj[27]+",");
				
				result_json.append("\"systemEfficiency\":"+obj[28]+",");
				result_json.append("\"surfaceSystemEfficiency\":"+obj[29]+",");
				result_json.append("\"welldownSystemEfficiency\":"+obj[30]+",");
				result_json.append("\"energyPer100mLift\":"+obj[31]+",");
				result_json.append("\"todayKWattH\":"+obj[32]+",");
				
				result_json.append("\"resultAlarmLevel\":"+obj[33]+",");
				result_json.append("\"commAlarmLevel\":"+obj[34]+",");
				result_json.append("\"runAlarmLevel\":"+obj[35]+",");
				result_json.append("\"iDegreeBalanceAlarmLevel\":"+obj[36]+",");
				result_json.append("\"wattDegreeBalanceAlarmLevel\":"+obj[37]+"},");
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getOilWellTotalHistoryData(String data,Page pager)throws Exception {
		StringBuffer wells= new StringBuffer();
		String getResult="";
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int liftingType=1;
		String startDate=StringManagerUtils.getCurrentTime();
		String endDate=StringManagerUtils.getCurrentTime();
		String wellName="";
		int type=1;
		String statValue="";
		try{
			JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
			
			try{
				liftingType=jsonObject.getInt("LiftingType");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				startDate=jsonObject.getString("StartDate");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				endDate=jsonObject.getString("EndDate");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				wellName=jsonObject.getString("WellName");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				type=jsonObject.getInt("StatType");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				statValue=jsonObject.getString("StatValue");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				JSONArray jsonArray = jsonObject.getJSONArray("WellList");
				for(int i=0;jsonArray!=null&&i<jsonArray.size();i++){
					wells.append("'"+jsonArray.getString(i)+"',");
				}
				if(wells.toString().endsWith(",")){
					wells.deleteCharAt(wells.length() - 1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
			liftingType=1;
			startDate=StringManagerUtils.getCurrentTime();
			endDate=StringManagerUtils.getCurrentTime();
			wellName="";
			type=1;
			statValue="";
		}
		String tableName="viw_rpc_total_day";
		String typeColumnName="resultName";
		if(type==1){
			typeColumnName="resultName";
		}else if(type==2){
			typeColumnName="liquidWeightProductionlevel";
			if(configFile.getOthers().getProductionUnit()!=0){
				typeColumnName="liquidVolumeProductionlevel";
			}
		}else if(type==3){
			typeColumnName="wattdegreebalanceLevel";
		}else if(type==4){
			typeColumnName="idegreebalanceLevel";
		}else if(type==5){
			typeColumnName="systemEfficiencyLevel";
		}else if(type==6){
			typeColumnName="surfaceSystemEfficiencyLevel";
		}else if(type==7){
			typeColumnName="wellDownSystemEfficiencyLevel";
		}else if(type==8){
			typeColumnName="todayKWattHLevel";
		}else if(type==9){
			typeColumnName="commStatusName";
		}else if(type==10){
			typeColumnName="commtimeefficiencyLevel";
		}else if(type==11){
			typeColumnName="runStatusName";
		}else if(type==12){
			typeColumnName="runtimeEfficiencyLevel";
		}
		String sql="";
		if(liftingType!=2){
			sql= "select id,wellName,to_char(calculateDate,'yyyy-mm-dd') as calculateDate,to_char(acquisitionDate,'yyyy-mm-dd') as acquisitionDate,ExtendedDays,"
					+ "resultName,optimizationSuggestion,"
					+ "commStatusName,commTime,commTimeEfficiency,commRange,"
					+ "runStatusName,runTime,runTimeEfficiency,runRange,"
					+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,weightWaterCut,"
					+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,volumeWaterCut,"
					+ "wattDegreeBalanceLevel,wattDegreeBalance,iDegreeBalanceLevel,iDegreeBalance,deltaRadius,"
					+ "systemEfficiency,surfaceSystemEfficiency,welldownSystemEfficiency,energyPer100mLift,todayKWattH ,"
					+ "resultAlarmLevel,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel  ";
			tableName="viw_rpc_total_day";
		}else{
			sql= "select id,wellName,to_char(calculateDate,'yyyy-mm-dd') as calculateDate,to_char(acquisitionDate,'yyyy-mm-dd') as acquisitionDate,ExtendedDays,"
					+ "resultName,optimizationSuggestion,"
					+ "commStatusName,commTime,commTimeEfficiency,commRange,"
					+ "runStatusName,runTime,runTimeEfficiency,runRange,"
					+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,weightWaterCut,"
					+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,volumeWaterCut,"
					+ "wattDegreeBalanceLevel,wattDegreeBalance,iDegreeBalanceLevel,iDegreeBalance,deltaRadius,"
					+ "systemEfficiency,surfaceSystemEfficiency,welldownSystemEfficiency,energyPer100mLift,todayKWattH ,"
					+ "resultAlarmLevel,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel  ";
			tableName="viw_pcp_total_day";
		}
				
		sql+= " from "+tableName+" t where 1=1 ";
		if(StringManagerUtils.isNotNull(wells.toString())){
			sql+= " and t.wellname in ("+wells.toString()+") ";
		}
		
		if(StringManagerUtils.isNotNull(statValue)){
			sql+=" and "+typeColumnName+"='"+statValue+"'";
		}
		sql+=" and to_date(to_char(t.calculateDate,'yyyy-mm-dd'),'yyyy-mm-dd') between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd') ";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+= " and  t.wellName='"+wellName+"' ";
		}
		sql+= " order by t.calculateDate desc";
		
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			if(StringManagerUtils.isNotNull(obj[0]+"")){
				result_json.append("{\"id\":"+obj[0]+",");
				result_json.append("\"wellName\":\""+obj[1]+"\",");
				result_json.append("\"calculateDate\":\""+obj[2]+"\",");
				result_json.append("\"acquisitionDate\":\""+obj[3]+"\",");
				result_json.append("\"extendedDays\":"+obj[4]+",");
				
				result_json.append("\"resultName\":\""+obj[5]+"\",");
				result_json.append("\"optimizationSuggestion\":\""+obj[6]+"\",");
				
				result_json.append("\"commStatus\":\""+obj[7]+"\",");
				result_json.append("\"commTime\":"+obj[5]+",");
				result_json.append("\"commTimeEfficiency\":"+obj[9]+",");
				result_json.append("\"commRange\":\""+obj[10]+"\",");
				
				result_json.append("\"runStatus\":\""+obj[11]+"\",");
				result_json.append("\"runTime\":"+obj[12]+",");
				result_json.append("\"runTimeEfficiency\":"+obj[13]+",");
				result_json.append("\"runRange\":\""+obj[14]+"\",");
				
				result_json.append("\"liquidWeightProduction\":"+obj[15]+",");
				result_json.append("\"oilWeightProduction\":"+obj[16]+",");
				result_json.append("\"waterWeightProduction\":"+obj[17]+",");
				result_json.append("\"weightWaterCut\":"+obj[18]+",");
				
				result_json.append("\"liquidVolumetricProduction\":"+obj[19]+",");
				result_json.append("\"oilVolumetricProduction\":"+obj[20]+",");
				result_json.append("\"waterVolumetricProduction\":"+obj[21]+",");
				result_json.append("\"volumeWaterCut\":"+obj[22]+",");
				
				result_json.append("\"wattDegreeBalanceName\":\""+obj[23]+"\",");
				result_json.append("\"wattDegreeBalance\":"+obj[24]+",");
				result_json.append("\"iDegreeBalanceName\":\""+obj[25]+"\",");
				result_json.append("\"iDegreeBalance\":"+obj[26]+",");
				result_json.append("\"deltaRadius\":"+obj[27]+",");
				
				result_json.append("\"systemEfficiency\":"+obj[28]+",");
				result_json.append("\"surfaceSystemEfficiency\":"+obj[29]+",");
				result_json.append("\"welldownSystemEfficiency\":"+obj[30]+",");
				result_json.append("\"energyPer100mLift\":"+obj[31]+",");
				result_json.append("\"todayKWattH\":"+obj[32]+",");
				
				result_json.append("\"resultAlarmLevel\":"+obj[33]+",");
				result_json.append("\"commAlarmLevel\":"+obj[34]+",");
				result_json.append("\"runAlarmLevel\":"+obj[35]+",");
				result_json.append("\"iDegreeBalanceAlarmLevel\":"+obj[36]+",");
				result_json.append("\"wattDegreeBalanceAlarmLevel\":"+obj[37]+"},");
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
}
