package com.gao.service.mobile;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gao.dao.BaseDao;
import com.gao.model.Org;
import com.gao.model.data.DataDictionary;
import com.gao.service.base.BaseService;
import com.gao.service.base.CommonDataService;
import com.gao.service.data.DataitemsInfoService;
import com.gao.utils.Config;
import com.gao.utils.ConfigFile;
import com.gao.utils.Page;
import com.gao.utils.StringManagerUtils;

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
			queryString = "SELECT {Org.*} FROM tbl_org Org   "
					+ " start with Org.org_id=( select t2.user_orgid from tbl_user t2 where t2.user_id='"+userAccount+"' )   "
					+ " connect by Org.org_parent= prior Org.org_id "
					+ " order by Org.org_code  ";
		}else{
			queryString = "SELECT {Org.*} FROM tbl_org Org   "
					+ " start with Org.org_id=1  "
					+ " connect by Org.org_parent= prior Org.org_id "
					+ " order by Org.org_code  ";
		}
		
		return getBaseDao().getSqlToHqlOrgObjects(queryString);
	}
	
	public String getPumpingRealtimeStatisticsDataByWellList(String data){
		StringBuffer wells= new StringBuffer();
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
		int liftingType=jsonObject.getInt("LiftingType");
		int type=jsonObject.getInt("StatType");
		JSONArray jsonArray = jsonObject.getJSONArray("WellList");
		for(int i=0;jsonArray!=null&&i<jsonArray.size();i++){
			wells.append("'"+jsonArray.getString(i)+"',");
		}
		if(wells.toString().endsWith(",")){
			wells.deleteCharAt(wells.length() - 1);
		}
		
		String sql="";
		String statType="workingConditionName";
		String tableName="viw_rpc_comprehensive_latest";
		if(type==1||type==0){
			if(liftingType!=2){
				statType="workingConditionName";
			}else{
				statType="workingConditionName_E";
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
		result_json.append("{ \"Success\":true,");
		result_json.append("\"TotalRoot\":[");
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
		JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
		int liftingType=jsonObject.getInt("LiftingType");
		int type=jsonObject.getInt("StatType");
		String statValue=jsonObject.getString("StatValue");
		JSONArray jsonArray = jsonObject.getJSONArray("WellList");
		for(int i=0;jsonArray!=null&&i<jsonArray.size();i++){
			wells.append("'"+jsonArray.getString(i)+"',");
		}
		if(wells.toString().endsWith(",")){
			wells.deleteCharAt(wells.length() - 1);
		}
		
		DataDictionary ddic = null;
		String columns= "";
		String sql="";
		String finalSql="";
		String sqlAll="";
		String ddicName="";
		String tableName_latest="viw_rpc_comprehensive_latest";
		String tableName_hist="viw_rpc_comprehensive_hist";
		String typeColumnName="workingConditionName";
		if(type==1){
			if(liftingType==2){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeFSDiagram";
			}
			typeColumnName="workingConditionName";
		}else if(type==2){
			if(liftingType==2){//螺杆泵井
				ddicName="screwPumpRealtimeProdDist";
			}else{//默认为抽油机
				ddicName="realtimeProdDist";
			}
			typeColumnName="liquidWeightProductionlevel";
			if(configFile.getOthers().getProductionUnit()!=0){
				typeColumnName="liquidVolumeProductionlevel";
			}
		}else if(type==3){
			if(liftingType==2){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimePowerBalance";
			}
			typeColumnName="wattDegreeBalanceName";
		}else if(type==4){
			if(liftingType==2){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeCurrentBalance";
			}
			typeColumnName="iDegreeBalanceName";
		}else if(type==5){
			if(liftingType==2){//螺杆泵井
				ddicName="screwPumpRealtimeRunStatus";
			}else{//默认为抽油机
				ddicName="realtimeRunStatus";
			}
			typeColumnName="runStatusName";
		}else if(type==6){
			if(liftingType==2){//螺杆泵井
				ddicName="screwPumpRealtimeTimeDist";
			}else{//默认为抽油机
				ddicName="realtimeTimeDist";
			}
			typeColumnName="runtimeEfficiencyLevel";
		}else if(type==7){
			if(liftingType==2){//螺杆泵井
				ddicName="screwPumpRealtimeSystemEff";
			}else{//默认为抽油机
				ddicName="realtimeSystemEff";
			}
			typeColumnName="systemEfficiencyLevel";
		}else if(type==8){
			if(liftingType==2){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeSurfaceEff";
			}
			typeColumnName="surfaceSystemEfficiencyLevel";
		}else if(type==9){
			if(liftingType==2){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeDownholeEff";
			}
			typeColumnName="wellDownSystemEfficiencyLevel";
		}else if(type==10){
			if(liftingType==2){//螺杆泵井
				ddicName="screwPumpRealtimePowerDist";
			}else{//默认为抽油机
				ddicName="realtimePowerDist";
			}
			typeColumnName="todayKWattHLevel";
		}else if(type==11){
			if(liftingType==2){//螺杆泵井
				ddicName="screwPumpRealtimeCommStatus";
			}else{//默认为抽油机
				ddicName="realtimeCommStatus";
			}
			typeColumnName="commStatusName";
		}else if(type==12){
			if(liftingType==2){//螺杆泵井
				ddicName="screwPumpRealtimeCommDist";
			}else{//默认为抽油机
				ddicName="realtimeCommDist";
			}
			typeColumnName="commtimeefficiencyLevel";
		}else{
			if(liftingType==2){//螺杆泵井
				ddicName="screwPumpRealtimeETValue";
			}else{//默认为抽油机
				ddicName="realtimeFSDiagram";
			}
			typeColumnName="workingConditionName";
		}
		
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		
		columns = ddic.getTableHeader();
		
		if(liftingType==2){//螺杆泵井
			tableName_latest="viw_pcp_comprehensive_latest";
			tableName_hist="viw_pcp_comprehensive_hist";
			sql=ddic.getSql()+",workingConditionString_E,videourl,workingConditionAlarmLevel_E,"
					+ " commStatus,runStatus,commAlarmLevel,runAlarmLevel ";
		}else{//默认为抽油机
			tableName_latest="viw_rpc_comprehensive_latest";
			tableName_hist="viw_rpc_comprehensive_hist";
			sql=ddic.getSql()+",workingConditionString_E,videourl,workingConditionAlarmLevel,workingConditionAlarmLevel_E,"
					+ " commStatus,runStatus,commAlarmLevel,runAlarmLevel,iDegreeBalanceAlarmLevel,wattDegreeBalanceAlarmLevel ";
		}
		
		
		sql+= " from "+tableName_latest+" t where t.wellName in("+wells.toString()+")";
		
		
		if(StringManagerUtils.isNotNull(statValue)){
			sql+=" and "+typeColumnName+"='"+statValue+"' ";
		}
		sql+=" order by t.sortNum, t.wellName";
		sqlAll=sql;
		
//		int maxvalue=pager.getLimit()+pager.getStart();
//		finalSql="select * from   ( select a.*,rownum as rn from ("+sqlAll+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart();
		finalSql=sqlAll;
		String getResult = this.findCustomPageBySqlEntity(sqlAll,finalSql, columns, 20 + "", pager);
		return getResult;
	}
	
	public String getPumpingRealtimeWellStatusData(String orgId)throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql="select t.jh,t.gkmc,t.bjbz,t.egkmc,t.ebjbz,phzt,phbjbz,glphzt,glphbjbz from v_analysisrealtime t where t.org_id in ("+orgId+")";
		
		
		List<?> list = this.findCallSql(sql);
		
		result_json.append("{\"Amount\":"+list.size()+",");
		result_json.append("\"List\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"WellName\":\""+obj[0]+"\",");
			result_json.append("\"FSWorkingCondition\":\""+obj[1]+"\",");
			result_json.append("\"FSWorkingConditionStatus\":\""+obj[2]+"\",");
			result_json.append("\"ETWorkingCondition\":\""+obj[3]+"\",");
			result_json.append("\"ETWorkingConditionStatus\":\""+obj[4]+"\",");
			result_json.append("\"AStatusOfBalance\":\""+obj[5]+"\",");
			result_json.append("\"AStatusOfBalanceStatus\":\""+obj[6]+"\",");
			result_json.append("\"PStatusOfBalance\":\""+obj[7]+"\",");
			result_json.append("\"PStatusOfBalanceStatus\":\""+obj[8]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	
	public String getPumpingRealtimeWellAnalysisData(String wellName) throws SQLException, IOException{
        String bgt="";
        StringBuffer dataSbf = new StringBuffer();
        StringBuffer bgtStr = new StringBuffer();
        String sql="select t.jslxcode,t.jslx,"
        		+ " to_char(t.gtcjsj,'yyyy-mm-dd hh24:mi:ss') as cjsj,"
        		+ " t.yxztname,"
        		+ " t.txqj,t.txsl,"
        		+ " t.yxqj,t.yxsl,"
        		+ " t.gkmc,t.egkmc,t.yhjy,"
        		+ " t.jsdjrcyl,t.jsdjrcyl1,t.hsl,"
        		+ " t.bj,t.bg,t.dym,"
        		+ " t.glphzt,t.pdegreeofbalance,t.upstrokepmax,t.downstrokepmax,"
        		+ " t.phzt,t.adegreeofbalance,t.upstrokeamax,t.downstrokeamax,"
        		+ " t.rydl,t.xtxl,t.jxxtxl,t.dmxtxl,"
        		+ " t.llszh,t.llxzh,t.gtcc,t.gtcc1,t.zdzh,t.zxzh,"
        		+ " t.tubingpressure,t.casingpressure,t.wellheadfluidtemperature,t.currenta,t.currentb,t.currentc,t.voltagea,t.voltageb,t.voltagec,"
        		+ " t.activepowerconsumption,t.reactivepowerconsumption,t.activepower,t.reactivepower,t.reversepower,t.powerfactor,t.gtcjzq,"
        		+ " t.videourl,"
        		+ " decode(t.gklx,1232,t010.gtsj,t033.bgt) as bgt,"
        		+ " t010.position_curve,t010.load_curve,t010.power_curve,t010.current_curve,"
        		+ " t.gcpl,t.rpm,"
        		+ " t.yjylbfb,t.ejylbfb,t.sjylbfb,t.sijylbfb,t.ccssxs,t.cmxs*100,t.lsxs,t.ytssxs "
        		+ " from v_analysisrealtime t "
        		+ " left outer join t_outputwellrealtime t033 on t.id=t033.jlbh "
        		+ " left outer join t_indicatordiagram t010  on t.gtbh=t010.jlbh "
        		+ " where t.jh='"+wellName+"'";
		List<?> list=null;
		if(StringManagerUtils.isNotNull(wellName)){
			list=this.findCallSql(sql);
		}
		if(list!=null&&list.size()>0){
			Object[] obj=(Object[])list.get(0);
			SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[52]);
			CLOB realClob = (CLOB) proxy.getWrappedClob(); 
			bgt=StringManagerUtils.CLOBtoString(realClob);
			String SString="",FString="",StressRatio="",PumpEfficiency="";
			int jslxcode=StringManagerUtils.stringToInteger(obj[0]+"");
			
			if(StringManagerUtils.stringToFloat(obj[59]+"")>0){
				StressRatio+=obj[59]+",";
			}
			if(StringManagerUtils.stringToFloat(obj[60]+"")>0){
				StressRatio+=obj[60]+",";
			}
			if(StringManagerUtils.stringToFloat(obj[61]+"")>0){
				StressRatio+=obj[61]+",";
			}
			if(StringManagerUtils.stringToFloat(obj[62]+"")>0){
				StressRatio+=obj[62]+",";
			}
			if(StringManagerUtils.isNotNull(StressRatio)&&StressRatio.lastIndexOf(",")==StressRatio.length()-1){
				StressRatio=StressRatio.substring(0, StressRatio.length()-1);
			}
			
			PumpEfficiency=obj[63]+","+obj[64]+","+obj[65]+","+obj[66];
			
			if(jslxcode>=200&&jslxcode<300){
				if("采集异常".equals(obj[8]+"")){//采集异常
					String arrbgt[]=bgt.replaceAll("\r\n", "\n").split("\n");
					for(int i=5;i<arrbgt.length;i+=2){
						SString+=arrbgt[i]+",";
						FString+=arrbgt[i+1]+",";
					}
					if(SString.lastIndexOf(",")==SString.length()-1){
						SString=SString.substring(0, SString.length()-1);
					}
					if(FString.lastIndexOf(",")==FString.length()-1){
						FString=FString.substring(0, FString.length()-1);
					}
					bgtStr.append("{\"S\":["+SString+"],\"F\":["+FString+"]}");
					
				}else{
					String arrbgt[]=bgt.split(";");  // 以;为界放入数组
			        for(int i=2;i<(arrbgt.length);i++){
			        	String S="",F="";
			        	String arrbgtdata[]=arrbgt[i].split(",");  // 以,为界放入数组
			        	
			        	for(int j=0;j<arrbgtdata.length;j+=2){
			        		S+=arrbgtdata[j]+",";
							F+=arrbgtdata[j+1]+",";
				        }
			        	
			        	if(S.lastIndexOf(",")==S.length()-1){
							S=S.substring(0, S.length()-1);
						}
						if(F.lastIndexOf(",")==F.length()-1){
							F=F.substring(0, F.length()-1);
						}
						if(i==2){//将泵功图第一条曲线数据给到地面功图
		        			SString=S;
							FString=F;
		        		}
						bgtStr.append("{\"S\":["+S+"],\"F\":["+F+"]},");
			        }
				}
				if(bgtStr.toString().endsWith(",")){
		        	bgtStr.deleteCharAt(bgtStr.length() - 1);
				}
			}
			
	        dataSbf.append("{\"WellName\":\""+wellName+"\",");
	        dataSbf.append("\"LiftingType\":\""+obj[1]+"\",");
	        dataSbf.append("\"acqTime\":\""+obj[2]+"\",");
	        dataSbf.append("\"Status\":\""+obj[3]+"\",");
	        dataSbf.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[4])+"\",");
	        dataSbf.append("\"CommTimeEfficiency\":\""+obj[5]+"\",");
	        dataSbf.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[6])+"\","); 
	        dataSbf.append("\"RunTimeEfficiency\":\""+obj[7]+"\",");
	        if(jslxcode>=200&&jslxcode<300){
	        	dataSbf.append("\"FSWorkingCondition\":\""+obj[8]+"\",");
	        	dataSbf.append("\"Suggestion\":\""+obj[10]+"\","); 
	        	dataSbf.append("\"PStatusOfBalance\":\""+obj[17]+"\","); 
		        dataSbf.append("\"PDegreeOfBalance\":\""+obj[18]+"\",");  
		        dataSbf.append("\"UpStrokePMax\":\""+obj[19]+"\",");
		        dataSbf.append("\"DownStrokePMax\":\""+obj[20]+"\",");  
		        dataSbf.append("\"AStatusOfBalance\":\""+obj[21]+"\","); 
		        dataSbf.append("\"ADegreeOfBalance\":\""+obj[22]+"\",");  
		        dataSbf.append("\"UpStrokeAMax\":\""+obj[23]+"\",");
		        dataSbf.append("\"DownStrokeAMax\":\""+obj[24]+"\",");  
		        dataSbf.append("\"WellDownSystemEfficiency\":\""+obj[27]+"\",");
		        dataSbf.append("\"SurfaceSystemEfficiency\":\""+obj[28]+"\",");  
		        dataSbf.append("\"UpperLoadLine\":\""+obj[29]+"\",");
		        dataSbf.append("\"LowerLoadLine\":\""+obj[30]+"\",");  
		        dataSbf.append("\"Stroke\":\""+obj[31]+"\",");
		        dataSbf.append("\"SPM\":\""+obj[32]+"\",");
		        dataSbf.append("\"FMax\":\""+obj[33]+"\",");
		        dataSbf.append("\"FMin\":\""+obj[34]+"\",");
		        dataSbf.append("\"AcquisitionCycle\":\""+obj[50]+"\",");
		        dataSbf.append("\"StressRatio\":["+StressRatio+"],");
		        dataSbf.append("\"PumpEfficiency\":["+PumpEfficiency+"],");
		        dataSbf.append("\"S\":["+SString+"],");
		        dataSbf.append("\"F\":["+FString+"],");
		        dataSbf.append("\"P\":["+obj[55]+"],");
		        dataSbf.append("\"A\":["+obj[56]+"],");
		        dataSbf.append("\"PumpCardData\":["+bgtStr.toString()+"],");
		        
	        }else if(jslxcode>=400&&jslxcode<500){//螺杆泵参数
	        	dataSbf.append("\"QPR\":\""+obj[57]+"\",");
	        	dataSbf.append("\"RPM\":\""+obj[58]+"\","); 
	        }
	        
	        dataSbf.append("\"ETWorkingCondition\":\""+obj[9]+"\",");
	        dataSbf.append("\"LiquidProduction\":\""+obj[11]+"\",");
	        dataSbf.append("\"OilProduction\":\""+obj[12]+"\",");
	        dataSbf.append("\"WaterCut\":\""+obj[13]+"\",");
	        dataSbf.append("\"PumpBoreDiameter\":\""+obj[14]+"\",");
	        dataSbf.append("\"PumpSettingDepth\":\""+obj[15]+"\",");
	        dataSbf.append("\"ProducingfluidLevel\":\""+obj[16]+"\",");
	        dataSbf.append("\"DailyAPC\":\""+obj[25]+"\",");
	        dataSbf.append("\"SystemEfficiency\":\""+obj[26]+"\",");
	        dataSbf.append("\"Tubingpressure\":\""+obj[35]+"\",");
	        dataSbf.append("\"Casingpressure\":\""+obj[36]+"\",");
	        dataSbf.append("\"Wellheadfluidtemperature\":\""+obj[37]+"\",");
	        dataSbf.append("\"Currenta\":\""+obj[38]+"\",");
	        dataSbf.append("\"Currentb\":\""+obj[39]+"\",");
	        dataSbf.append("\"Currentc\":\""+obj[40]+"\",");
	        dataSbf.append("\"Voltagea\":\""+obj[41]+"\",");
	        dataSbf.append("\"Voltageb\":\""+obj[42]+"\",");
	        dataSbf.append("\"Voltagec\":\""+obj[43]+"\",");
	        dataSbf.append("\"Activepowerconsumption\":\""+obj[44]+"\",");
	        dataSbf.append("\"Reactivepowerconsumption\":\""+obj[45]+"\",");
	        dataSbf.append("\"Activepower\":\""+obj[46]+"\",");
	        dataSbf.append("\"Reactivepower\":\""+obj[47]+"\",");
	        dataSbf.append("\"Reversepower\":\""+obj[48]+"\",");
	        dataSbf.append("\"Powerfactor\":\""+obj[49]+"\",");
	        dataSbf.append("\"VideoURL\":\""+obj[51]+"\"");
	        dataSbf.append("}");
		}else{
			dataSbf.append("{\"WellName\":\""+wellName+"\",");
			dataSbf.append("\"LiftingType\":\"\",");
	        dataSbf.append("\"acqTime\":\"\",");
	        dataSbf.append("\"Status\":\"\",");  
	        dataSbf.append("\"CommRange\":\"\","); 
	        dataSbf.append("\"CommTimeEfficiency\":\"\",");  
	        dataSbf.append("\"RunRange\":\"\","); 
	        dataSbf.append("\"RunTimeEfficiency\":\"\",");  
	        dataSbf.append("\"FSWorkingCondition\":\"\",");
	        dataSbf.append("\"ETWorkingCondition\":\"\",");  
	        dataSbf.append("\"Suggestion\":\"\","); 
	        dataSbf.append("\"LiquidProduction\":\"\",");  
	        dataSbf.append("\"OilProduction\":\"\",");
	        dataSbf.append("\"WaterCut\":\"\",");  
	        dataSbf.append("\"PumpBoreDiameter\":\"\",");  
	        dataSbf.append("\"PumpSettingDepth\":\"\",");  
	        dataSbf.append("\"ProducingfluidLevel\":\"\",");  
	        dataSbf.append("\"PStatusOfBalance\":\"\","); 
	        dataSbf.append("\"PDegreeOfBalance\":\"\",");  
	        dataSbf.append("\"UpStrokePMax\":\"\",");
	        dataSbf.append("\"DownStrokePMax\":\"\",");  
	        dataSbf.append("\"AStatusOfBalance\":\"\","); 
	        dataSbf.append("\"ADegreeOfBalance\":\"\",");  
	        dataSbf.append("\"UpStrokeAMax\":\"\",");
	        dataSbf.append("\"DownStrokeAMax\":\"\",");  
	        dataSbf.append("\"DailyAPC\":\"\","); 
	        dataSbf.append("\"SystemEfficiency\":\"\",");  
	        dataSbf.append("\"WellDownSystemEfficiency\":\"\",");
	        dataSbf.append("\"SurfaceSystemEfficiency\":\"\",");  
	        dataSbf.append("\"UpperLoadLine\":\"\",");
	        dataSbf.append("\"LowerLoadLine\":\"\",");  
	        dataSbf.append("\"Stroke\":\"\",");
	        dataSbf.append("\"SPM\":\"\",");
	        dataSbf.append("\"FMax\":\"\",");
	        dataSbf.append("\"FMin\":\"\",");
	        dataSbf.append("\"Tubingpressure\":\"\",");
	        dataSbf.append("\"Casingpressure\":\"\",");
	        dataSbf.append("\"Wellheadfluidtemperature\":\"\",");
	        dataSbf.append("\"Currenta\":\"\",");
	        dataSbf.append("\"Currentb\":\"\",");
	        dataSbf.append("\"Currentc\":\"\",");
	        dataSbf.append("\"Voltagea\":\"\",");
	        dataSbf.append("\"Voltageb\":\"\",");
	        dataSbf.append("\"Voltagec\":\"\",");
	        dataSbf.append("\"Activepowerconsumption\":\"\",");
	        dataSbf.append("\"Reactivepowerconsumption\":\"\",");
	        dataSbf.append("\"Activepower\":\"\",");
	        dataSbf.append("\"Reactivepower\":\"\",");
	        dataSbf.append("\"Reversepower\":\"\",");
	        dataSbf.append("\"Powerfactor\":\"\",");
	        dataSbf.append("\"AcquisitionCycle\":\"\",");
	        dataSbf.append("\"VideoURL\":\"\",");
	        dataSbf.append("\"S\":[],");
	        dataSbf.append("\"F\":[],");
	        dataSbf.append("\"P\":[],");
	        dataSbf.append("\"A\":[],");
	        dataSbf.append("\"PumpCardData\":[]");
	        dataSbf.append("}");
		}
		return dataSbf.toString().replaceAll("null", "");
	}
	
	
}
