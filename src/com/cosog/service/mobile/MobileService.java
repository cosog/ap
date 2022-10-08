package com.cosog.service.mobile;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cosog.dao.BaseDao;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.Org;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.AcqInstanceOwnItem;
import com.cosog.model.calculate.AlarmInstanceOwnItem;
import com.cosog.model.calculate.DisplayInstanceOwnItem;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.RPCDeviceInfo;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.AcquisitionItemColumnsMap;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.Page;
import com.cosog.utils.PageHandler;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.CLOB;
import redis.clients.jedis.Jedis;

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
		String json="{\"success\":false,\"totalRoot\":[]}";
		int liftingType=1;
		int type=1;
		if(StringManagerUtils.isNotNull(data)){
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
					JSONArray jsonArray = jsonObject.getJSONArray("WellList");
					for(int i=0;jsonArray!=null&&i<jsonArray.size();i++){
						wells.append(""+jsonArray.getString(i)+",");
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
		}
		
		try{
			if(liftingType==1 && type==1){
				json=this.getRealTimeMonitoringFESDiagramResultStatData(wells.toString());
			}else if(type==2){
				json=this.getRealTimeMonitoringCommStatusStatData(wells.toString(),liftingType);
			}else if(type==3){
				json=this.getRealTimeMonitoringRunStatusStatData(wells.toString(),liftingType);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return json;
	}
	
	public String getRealTimeMonitoringFESDiagramResultStatData(String wells) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Jedis jedis = null;
		AlarmShowStyle alarmShowStyle=null;
		List<byte[]> deviceInfoByteList=null;
		String [] wellList=null;
		if(StringManagerUtils.isNotNull(wells)){
			wellList=wells.split(",");
		}
		try{
			jedis = RedisUtil.jedisPool.getResource();
			
			if(!jedis.exists("RPCDeviceInfo".getBytes())){
				MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
			}
			
			if(!jedis.exists("RPCWorkType".getBytes())){
				MemoryDataManagerTask.loadRPCWorkType();
			}
			deviceInfoByteList =jedis.hvals("RPCDeviceInfo".getBytes());
		}catch(Exception e){
			e.printStackTrace();
		}
		result_json.append("{ \"success\":true,");
		
		int totalCount=0;
		if(jedis==null){
			String tableName="tbl_rpcacqdata_latest";
			String deviceTableName="viw_rpcdevice";
			
			String sql="select decode(t2.resultcode,null,'无数据',t3.resultname) as resultname,t2.resultcode,count(1) "
					+ " from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on  t2.wellid=t.id"
					+ " left outer join tbl_rpc_worktype t3 on  t2.resultcode=t3.resultcode"
					+ " where 1=1 ";
			if(wellList!=null){
				sql+=" and t.wellname in ( "+StringManagerUtils.joinStringArr2(wellList, ",")+" )";
			}
			sql+=" group by t3.resultname,t2.resultcode "
					+ " order by t2.resultcode";
			
			List<?> list = this.findCallSql(sql);
			result_json.append("\"totalRoot\":[");
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				result_json.append("{\"Item\":\""+obj[0]+"\",");
				result_json.append("\"Count\":"+obj[2]+"},");
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]");
		}else{
			if(deviceInfoByteList!=null){
				Map<Integer,Integer> totalMap=new TreeMap<Integer,Integer>();
				for(int i=0;i<deviceInfoByteList.size();i++){
					Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
					if (obj instanceof RPCDeviceInfo) {
						RPCDeviceInfo rpcDeviceInfo=(RPCDeviceInfo)obj;
						if( StringManagerUtils.existOrNot(wellList, rpcDeviceInfo.getWellName()) ){
							int count=1;
							int resultCode=rpcDeviceInfo.getResultCode()==null?0:rpcDeviceInfo.getResultCode();
							if(totalMap.containsKey(resultCode)){
								count=totalMap.get(resultCode)+1;
							}
							totalMap.put(resultCode, count);
						}
					}
				}
				
				int index=1;
				result_json.append("\"totalRoot\":[");
				for(Integer key:totalMap.keySet()){
					String item="无数据";
					if(jedis.hexists("RPCWorkType".getBytes(), (key+"").getBytes())){
						WorkType workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("RPCWorkType".getBytes(), (key+"").getBytes()));
						item=workType.getResultName();
					}
					result_json.append("{\"Item\":\""+item+"\",");
					result_json.append("\"Count\":"+totalMap.get(key)+"},");
					index++;
				}
				
				if(result_json.toString().endsWith(",")){
					result_json.deleteCharAt(result_json.length() - 1);
				}
				result_json.append("]");
			}
		}
		
		result_json.append("}");
		if(jedis!=null){
			jedis.close();
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getRealTimeMonitoringCommStatusStatData(String wells,int deviceType) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Jedis jedis = null;
		AlarmShowStyle alarmShowStyle=null;
		List<byte[]> deviceInfoByteList=null;
		String [] wellList=null;
		if(StringManagerUtils.isNotNull(wells)){
			wellList=wells.split(",");
		}
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("AlarmShowStyle".getBytes())){
				MemoryDataManagerTask.initAlarmStyle();
			}
			alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
			
			if(deviceType==1){
				if(!jedis.exists("RPCDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
				}
				deviceInfoByteList =jedis.hvals("RPCDeviceInfo".getBytes());
			}else{
				if(!jedis.exists("PCPDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
				}
				deviceInfoByteList =jedis.hvals("PCPDeviceInfo".getBytes());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		result_json.append("{ \"success\":true,");
		int total=0,online=0,goOnline=0,offline=0;
		if(jedis==null){
			String tableName="tbl_rpcacqdata_latest";
			String deviceTableName="viw_rpcdevice";
			if(deviceType==1){
				tableName="tbl_pcpacqdata_latest";
				deviceTableName="viw_pcpdevice";
			}
			
			String sql="select t2.commstatus,count(1) from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on  t2.wellid=t.id "
					+ " where 1=1 ";
			if(wellList!=null){
				sql+=" and t.wellname in ( "+StringManagerUtils.joinStringArr2(wellList, ",")+" )";
			}
			sql+=" group by t2.commstatus";
			
			List<?> list = this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				if(StringManagerUtils.stringToInteger(obj[0]+"")==1){
					online=StringManagerUtils.stringToInteger(obj[1]+"");
				}else if(StringManagerUtils.stringToInteger(obj[0]+"")==2){
					goOnline=StringManagerUtils.stringToInteger(obj[1]+"");
				}else{
					offline=StringManagerUtils.stringToInteger(obj[1]+"");
				}
			}
		}else{
			if(deviceInfoByteList!=null){
				for(int i=0;i<deviceInfoByteList.size();i++){
					int commStatus=0;
					if(deviceType==1){
						Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
						if (obj instanceof RPCDeviceInfo) {
							RPCDeviceInfo rpcDeviceInfo=(RPCDeviceInfo)obj;
							if( StringManagerUtils.existOrNot(wellList, rpcDeviceInfo.getWellName()) ){
								commStatus=rpcDeviceInfo.getOnLineCommStatus();
								if(commStatus==1){
									online+=1;
								}else if(commStatus==2){
									goOnline+=1;
								}else{
									offline+=1;;
								}
							}
						}
					}else{
						Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
						if (obj instanceof PCPDeviceInfo) {
							PCPDeviceInfo pcpDeviceInfo=(PCPDeviceInfo)obj;
							if( StringManagerUtils.existOrNot(wellList, pcpDeviceInfo.getWellName()) ){
								commStatus=pcpDeviceInfo.getOnLineCommStatus();
								if(commStatus==1){
									online+=1;
								}else if(commStatus==2){
									goOnline+=1;
								}else{
									offline+=1;;
								}
							}
						}
					}
				}
			}
		}
		
		total=online+goOnline+offline;
		result_json.append("\"totalRoot\":[");
		
		result_json.append("{\"Item\":\"在线\",");
		result_json.append("\"Count\":"+online+"},");
		
		result_json.append("{\"Item\":\"上线\",");
		result_json.append("\"Count\":"+goOnline+"},");
		
		result_json.append("{\"Item\":\"离线\",");
		result_json.append("\"Count\":"+offline+"}");
		result_json.append("]}");
		if(jedis!=null){
			jedis.close();
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getRealTimeMonitoringRunStatusStatData(String wells,int deviceType) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Jedis jedis = null;
		AlarmShowStyle alarmShowStyle=null;
		List<byte[]> deviceInfoByteList=null;
		String [] wellList=null;
		if(StringManagerUtils.isNotNull(wells)){
			wellList=wells.split(",");
		}
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("AlarmShowStyle".getBytes())){
				MemoryDataManagerTask.initAlarmStyle();
			}
			alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
			
			if(deviceType==1){
				if(!jedis.exists("RPCDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
				}
				deviceInfoByteList =jedis.hvals("RPCDeviceInfo".getBytes());
			}else{
				if(!jedis.exists("PCPDeviceInfo".getBytes())){
					MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
				}
				deviceInfoByteList =jedis.hvals("PCPDeviceInfo".getBytes());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		result_json.append("{ \"success\":true,");
		int total=0,run=0,stop=0,offline=0;
		if(jedis==null){
			String tableName="tbl_rpcacqdata_latest";
			String deviceTableName="viw_rpcdevice";
			if(deviceType==1){
				tableName="tbl_pcpacqdata_latest";
				deviceTableName="viw_pcpdevice";
			}
			
			String sql="select decode(t2.commstatus,0,-1,t2.runstatus) as runstatus,count(1) from "+deviceTableName+" t "
					+ " left outer join "+tableName+" t2 on  t2.wellid=t.id "
					+ " where 1=1 ";
			if(wellList!=null){
				sql+=" and t.wellname in ( "+StringManagerUtils.joinStringArr2(wellList, ",")+" )";
			}
			sql+=" group by t2.commstatus,t2.runstatus";
			
			List<?> list = this.findCallSql(sql);
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				if(StringManagerUtils.stringToInteger(obj[0]+"")==1){
					run=StringManagerUtils.stringToInteger(obj[1]+"");
				}else if(StringManagerUtils.stringToInteger(obj[0]+"")==0){
					stop=StringManagerUtils.stringToInteger(obj[1]+"");
				}else{
					offline=StringManagerUtils.stringToInteger(obj[1]+"");
				}
			}
		}else{
			if(deviceInfoByteList!=null){
				for(int i=0;i<deviceInfoByteList.size();i++){
					int commStatus=0;
					int runStatus=0;
					if(deviceType==1){
						Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
						if (obj instanceof RPCDeviceInfo) {
							RPCDeviceInfo rpcDeviceInfo=(RPCDeviceInfo)obj;
							if( StringManagerUtils.existOrNot(wellList, rpcDeviceInfo.getWellName()) ){
								commStatus=rpcDeviceInfo.getOnLineCommStatus();
								runStatus=rpcDeviceInfo.getRunStatus();
								if(commStatus>0){
									if(runStatus==1){
										run+=1;
									}else{
										stop+=1;;
									}
								}else{
									offline+=1;;
								}
							}
						}
					}else{
						Object obj = SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
						if (obj instanceof PCPDeviceInfo) {
							PCPDeviceInfo pcpDeviceInfo=(PCPDeviceInfo)obj;
							if( StringManagerUtils.existOrNot(wellList, pcpDeviceInfo.getWellName()) ){
								commStatus=pcpDeviceInfo.getOnLineCommStatus();
								runStatus=pcpDeviceInfo.getRunStatus();
								if(commStatus>0){
									if(runStatus==1){
										run+=1;
									}else{
										stop+=1;;
									}
								}else{
									offline+=1;;
								}
							}
						}
					}
				}
			}
		}
		
		total=run+stop+offline;
		result_json.append("\"totalRoot\":[");
		
		result_json.append("{\"Item\":\"运行\",");
		result_json.append("\"Count\":"+run+"},");
		
		result_json.append("{\"Item\":\"停抽\",");
		result_json.append("\"Count\":"+stop+"},");
		
		result_json.append("{\"Item\":\"离线\",");
		result_json.append("\"Count\":"+offline+"}");
		result_json.append("]}");
		if(jedis!=null){
			jedis.close();
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getOilWellRealtimeWellListData(String data,Page pager)throws Exception {
		String json = "";
		StringBuffer wells= new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		int liftingType=1;
		int statType=1;
		String statValue="";
		if(StringManagerUtils.isNotNull(data)){
			try{
				JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
				try{
					liftingType=jsonObject.getInt("LiftingType");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					statType=jsonObject.getInt("StatType");
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
						wells.append(""+jsonArray.getString(i)+",");
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
		}
		if(liftingType==1){
			json=this.getDeviceRealTimeOverview(wells.toString(), statType, statValue);
		}else{
			json=this.getPCPDeviceRealTimeOverview(wells.toString(), statType, statValue);
		}
		return json;
	}
	
	public String getDeviceRealTimeOverview(String wells,int statType,String statValue) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		Jedis jedis=null;
		String [] wellList=null;
		if(StringManagerUtils.isNotNull(wells)){
			wellList=wells.split(",");
		}
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("RPCDeviceInfo".getBytes())){
				MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
			}
			if(!jedis.exists("AlarmShowStyle".getBytes())){
				MemoryDataManagerTask.initAlarmStyle();
			}
			
			if(!jedis.exists("RPCWorkType".getBytes())){
				MemoryDataManagerTask.loadRPCWorkType();
			}
			
			if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
				MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String tableName="tbl_rpcacqdata_latest";
		String deviceTableName="tbl_rpcdevice";
		
		
		String sql="select t.id,t.wellname,t.videourl,t.videoAccessToken,"
				+ "c1.itemname as devicetypename,"
				+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
				+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
				+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
				+ "t2.runstatus,decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽')) as runStatusName,"
				+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
				+ "t2.resultcode,decode(t2.resultcode,null,'无数据',t3.resultName) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
				+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,"
				+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,"
				+ "t2.FMax,t2.FMin,t2.fullnessCoefficient,"
				+ "t2.averageWatt,t2.polishrodPower,t2.waterPower,"
				+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
				+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
				+ "t2.systemEfficiency*100 as systemEfficiency,t2.energyper100mlift,"
				+ "t2.pumpEff*100 as pumpEff,"
				+ "t2.iDegreeBalance,t2.wattDegreeBalance,t2.deltaradius*100 as deltaradius,"
				+ "t2.levelCorrectValue,t2.inverProducingfluidLevel,t2.todayKWattH";
		sql+= " from "+deviceTableName+" t "
				+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
				+ " left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode "
				+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue "
				+ " where  1=1 ";
		if(wellList!=null){
			sql+=" and t.wellname in ( "+StringManagerUtils.joinStringArr2(wellList, ",")+" )";
		}
		if(statType==1 && StringManagerUtils.isNotNull(statValue)){
			sql+=" and decode(t2.resultcode,null,'无数据',t3.resultName)='"+statValue+"'";
		}else if(statType==2 && StringManagerUtils.isNotNull(statValue)){
			sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+statValue+"'";
		}else if(statType==3 && StringManagerUtils.isNotNull(statValue)){
			sql+=" and decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽'))='"+statValue+"'";
		}
		sql+=" order by t.sortnum,t.wellname";
		
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			StringBuffer alarmInfo = new StringBuffer();
			String deviceId=obj[0]+"";
			
			RPCDeviceInfo rpcDeviceInfo=null;
			if(jedis!=null&&jedis.hexists("RPCDeviceInfo".getBytes(), deviceId.getBytes())){
				rpcDeviceInfo=(RPCDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("RPCDeviceInfo".getBytes(), deviceId.getBytes()));
			}
			
			AlarmInstanceOwnItem alarmInstanceOwnItem=null;
			if(jedis!=null&&rpcDeviceInfo!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), rpcDeviceInfo.getAlarmInstanceCode().getBytes())){
				alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), rpcDeviceInfo.getAlarmInstanceCode().getBytes()));
			}
			
			int commAlarmLevel=0,resultAlarmLevel=0,runAlarmLevel=0;
			if(alarmInstanceOwnItem!=null){
				for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
					if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(obj[7]+"")){
						commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(obj[12]+"")){
						runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==4 && alarmInstanceOwnItem.getItemList().get(j).getItemCode().equalsIgnoreCase(obj[16]+"")){
						resultAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}
				}
			}
			
			result_json.append("{\"id\":"+deviceId+",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"videoUrl\":\""+obj[2]+"\",");
			result_json.append("\"videoAccessToken\":\""+obj[3]+"\",");
			result_json.append("\"deviceTypeName\":\""+obj[4]+"\",");
			result_json.append("\"acqTime\":\""+obj[5]+"\",");
			result_json.append("\"commStatus\":"+obj[6]+",");
			result_json.append("\"commStatusName\":\""+obj[7]+"\",");
			result_json.append("\"commTime\":\""+obj[8]+"\",");
			result_json.append("\"commTimeEfficiency\":\""+obj[9]+"\",");
			result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[10])+"\",");
			
			result_json.append("\"runStatus\":"+obj[11]+",");
			result_json.append("\"runStatusName\":\""+obj[12]+"\",");
			result_json.append("\"runTime\":\""+obj[13]+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[14]+"\",");
			result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[15])+"\",");
			result_json.append("\"resultCode\":\""+obj[16]+"\",");
			result_json.append("\"resultName\":\""+obj[17]+"\",");
			result_json.append("\"optimizationSuggestion\":\""+obj[18]+"\",");
			
			result_json.append("\"liquidWeightProduction\":\""+obj[19]+"\",");
			result_json.append("\"oilWeightProduction\":\""+obj[20]+"\",");
			result_json.append("\"waterWeightProduction\":\""+obj[21]+"\",");
			result_json.append("\"liquidWeightProduction_L\":\""+obj[22]+"\",");
			
			result_json.append("\"liquidVolumetricProduction\":\""+obj[23]+"\",");
			result_json.append("\"oilVolumetricProduction\":\""+obj[24]+"\",");
			result_json.append("\"waterVolumetricProduction\":\""+obj[25]+"\",");
			result_json.append("\"liquidVolumetricProduction_L\":\""+obj[26]+"\",");
			
			result_json.append("\"FMax\":\""+obj[27]+"\",");
			result_json.append("\"FMin\":\""+obj[28]+"\",");
			result_json.append("\"fullnessCoefficient\":\""+obj[29]+"\",");
			
			result_json.append("\"averageWatt\":\""+obj[30]+"\",");
			result_json.append("\"polishrodPower\":\""+obj[31]+"\",");
			result_json.append("\"waterPower\":\""+obj[32]+"\",");
			
			result_json.append("\"surfaceSystemEfficiency\":\""+obj[33]+"\",");
			result_json.append("\"welldownSystemEfficiency\":\""+obj[34]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[35]+"\",");
			result_json.append("\"energyper100mlift\":\""+obj[36]+"\",");
			result_json.append("\"pumpEff\":\""+obj[37]+"\",");
			
			result_json.append("\"iDegreeBalance\":\""+obj[38]+"\",");
			result_json.append("\"wattDegreeBalance\":\""+obj[39]+"\",");
			result_json.append("\"deltaradius\":\""+obj[40]+"\",");
			
			result_json.append("\"levelCorrectValue\":\""+obj[41]+"\",");
			result_json.append("\"inverProducingfluidLevel\":\""+obj[42]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[43]+"\",");
			

			result_json.append("\"resultAlarmLevel\":"+resultAlarmLevel+",");
			result_json.append("\"commAlarmLevel\":"+commAlarmLevel+",");
			result_json.append("\"runAlarmLevel\":"+runAlarmLevel+"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		if(jedis!=null){
			jedis.close();
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getPCPDeviceRealTimeOverview(String wells,int statType,String statValue) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		Jedis jedis=null;
		String [] wellList=null;
		if(StringManagerUtils.isNotNull(wells)){
			wellList=wells.split(",");
		}
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("PCPDeviceInfo".getBytes())){
				MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
			}
			
			if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
				MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		
		String tableName="tbl_pcpacqdata_latest";
		String deviceTableName="tbl_pcpdevice";
		
		
		
		String sql="select t.id,t.wellname,t.videourl,t.videoAccessToken,"
				+ "c1.itemname as devicetypename,"
				+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
				+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
				+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
				+ "t2.runstatus,decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽')) as runStatusName,"
				+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
				+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,"
				+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,"
				+ "averageWatt,waterPower,"
				+ "systemEfficiency*100 as systemEfficiency,energyper100mlift,pumpEff*100 as pumpEff,"
				+ "todayKWattH";
		sql+= " from "+deviceTableName+" t "
				+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
				+ " left outer join tbl_code c1 on c1.itemcode='DEVICETYPE' and t.devicetype=c1.itemvalue "
				+ " where  1=1";
		if(wellList!=null){
			sql+=" and t.wellname in ( "+StringManagerUtils.joinStringArr2(wellList, ",")+" )";
		}
		if(statType==2 && StringManagerUtils.isNotNull(statValue)){
			sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+statValue+"'";
		}else if(statType==3 && StringManagerUtils.isNotNull(statValue)){
			sql+=" and decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽'))='"+statValue+"'";
		}
		sql+=" order by t.sortnum,t.wellname";
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			StringBuffer alarmInfo = new StringBuffer();
			String deviceId=obj[0]+"";
			
			PCPDeviceInfo pcpDeviceInfo=null;
			if(jedis!=null&&jedis.hexists("PCPDeviceInfo".getBytes(), deviceId.getBytes())){
				pcpDeviceInfo=(PCPDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("PCPDeviceInfo".getBytes(), deviceId.getBytes()));
			}
			
			AlarmInstanceOwnItem alarmInstanceOwnItem=null;
			if(jedis!=null&&pcpDeviceInfo!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), pcpDeviceInfo.getAlarmInstanceCode().getBytes())){
				alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), pcpDeviceInfo.getAlarmInstanceCode().getBytes()));
			}
			
			
			int commAlarmLevel=0,runAlarmLevel=0;
			if(alarmInstanceOwnItem!=null){
				for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
					if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(obj[7]+"")){
						commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(obj[12]+"")){
						runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}
				}
			}
			
			result_json.append("{\"id\":"+deviceId+",");
			result_json.append("\"wellName\":\""+obj[1]+"\",");
			result_json.append("\"videoUrl\":\""+obj[2]+"\",");
			result_json.append("\"videoAccessToken\":\""+obj[3]+"\",");
			result_json.append("\"deviceTypeName\":\""+obj[4]+"\",");
			result_json.append("\"acqTime\":\""+obj[5]+"\",");
			result_json.append("\"commStatus\":"+obj[6]+",");
			result_json.append("\"commStatusName\":\""+obj[7]+"\",");
			result_json.append("\"commTime\":\""+obj[8]+"\",");
			result_json.append("\"commTimeEfficiency\":\""+obj[9]+"\",");
			result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[10])+"\",");
			
			result_json.append("\"runStatus\":"+obj[11]+",");
			result_json.append("\"runStatusName\":\""+obj[12]+"\",");
			result_json.append("\"runTime\":\""+obj[13]+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[14]+"\",");
			result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[15])+"\",");
			
			result_json.append("\"liquidWeightProduction\":\""+obj[16]+"\",");
			result_json.append("\"oilWeightProduction\":\""+obj[17]+"\",");
			result_json.append("\"waterWeightProduction\":\""+obj[18]+"\",");
			result_json.append("\"liquidWeightProduction_L\":\""+obj[19]+"\",");
			
			result_json.append("\"liquidVolumetricProduction\":\""+obj[20]+"\",");
			result_json.append("\"oilVolumetricProduction\":\""+obj[21]+"\",");
			result_json.append("\"waterVolumetricProduction\":\""+obj[22]+"\",");
			result_json.append("\"liquidVolumetricProduction_L\":\""+obj[23]+"\",");
			
			result_json.append("\"averageWatt\":\""+obj[24]+"\",");
			result_json.append("\"waterPower\":\""+obj[25]+"\",");
			
			result_json.append("\"systemEfficiency\":\""+obj[26]+"\",");
			result_json.append("\"energyper100mlift\":\""+obj[27]+"\",");
			result_json.append("\"pumpEff\":\""+obj[28]+"\",");
			
			result_json.append("\"todayKWattH\":\""+obj[29]+"\",");
			
			result_json.append("\"commAlarmLevel\":"+commAlarmLevel+",");
			result_json.append("\"runAlarmLevel\":"+runAlarmLevel+"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		if(jedis!=null){
			jedis.close();
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getOilWellHistoryData(String data,Page pager)throws Exception {
		String json = "";
		
		int liftingType=1;
		int statType=1;
		String statValue="";
		String wellName="";
		String startDate=StringManagerUtils.getCurrentTime();
		String endDate=StringManagerUtils.getCurrentTime();
		if(StringManagerUtils.isNotNull(data)){
			try{
				JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
				try{
					liftingType=jsonObject.getInt("LiftingType");
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					statType=jsonObject.getInt("StatType");
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
		}
		if(liftingType==1){
			json=this.getDeviceHistoryData(wellName, statType, statValue, startDate, endDate);
		}else{
			json=this.getPCPDeviceHistoryData(wellName, statType, statValue, startDate, endDate);
		}
		return json;
	}
	
	public String getDeviceHistoryData(String wellName,int statType,String statValue,String startDate,String endDate) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Jedis jedis=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("RPCDeviceInfo".getBytes())){
				MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
			}
			if(!jedis.exists("AlarmShowStyle".getBytes())){
				MemoryDataManagerTask.initAlarmStyle();
			}
			
			if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
				MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String hisTableName="tbl_rpcacqdata_hist";
		String deviceTableName="tbl_rpcdevice";
		
		String sql="select t2.id,t.id as wellId,t.wellname,"
				+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
				+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
				+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
				+ "t2.runstatus,decode(t2.commstatus,1,decode(t2.runstatus,1,'运行','停抽'),'') as runStatusName,"
				+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
				+ "t2.resultcode,decode(t2.commstatus,1,decode(t2.resultcode,null,'无数据',t3.resultName),'' ) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
				+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,"
				+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,,"
				+ "t2.FMax,t2.FMin,t2.fullnessCoefficient,"
				+ "t2.averageWatt,t2.polishrodPower,t2.waterPower,"
				+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
				+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
				+ "t2.systemEfficiency*100 as systemEfficiency,"
				+ "t2.energyper100mlift,t2.pumpEff*100 as pumpEff,"
				+ "t2.iDegreeBalance,t2.wattDegreeBalance,t2.deltaradius*100 as deltaradius,"
				+ "t2.levelCorrectValue,t2.inverProducingfluidLevel,t2.todayKWattH";
		sql+= " from "+deviceTableName+" t "
				+ " left outer join "+hisTableName+" t2 on t2.wellid=t.id"
				+ " left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode "
				+ " where  t2.acqTime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss') ";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+= "and t.wellname='"+wellName+"'";
		}	
		sql+= "  order by t2.acqtime desc";
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String deviceId=obj[1]+"";
			RPCDeviceInfo rpcDeviceInfo=null;
			if(jedis!=null&&jedis.hexists("RPCDeviceInfo".getBytes(), deviceId.getBytes())){
				rpcDeviceInfo=(RPCDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("RPCDeviceInfo".getBytes(), deviceId.getBytes()));
			}
			
			AlarmInstanceOwnItem alarmInstanceOwnItem=null;
			if(jedis!=null&&rpcDeviceInfo!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), rpcDeviceInfo.getAlarmInstanceCode().getBytes())){
				alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), rpcDeviceInfo.getAlarmInstanceCode().getBytes()));
			}
			
			int commAlarmLevel=0,resultAlarmLevel=0,runAlarmLevel=0;
			if(alarmInstanceOwnItem!=null){
				for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
					if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(obj[4]+"")){
						commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(obj[9]+"")){
						runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==4 && alarmInstanceOwnItem.getItemList().get(j).getItemCode().equalsIgnoreCase(obj[13]+"")){
						resultAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}
				}
			}
			
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"deviceId\":\""+deviceId+"\",");
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"acqTime\":\""+obj[3]+"\",");
			result_json.append("\"commStatus\":"+obj[4]+",");
			result_json.append("\"commStatusName\":\""+obj[5]+"\",");
			result_json.append("\"commTime\":\""+obj[6]+"\",");
			result_json.append("\"commTimeEfficiency\":\""+obj[7]+"\",");
			result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
			result_json.append("\"commAlarmLevel\":"+commAlarmLevel+",");
			result_json.append("\"runStatus\":"+obj[9]+",");
			result_json.append("\"runStatusName\":\""+obj[10]+"\",");
			result_json.append("\"runTime\":\""+obj[11]+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[12]+"\",");
			result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[13])+"\",");
			result_json.append("\"runAlarmLevel\":"+runAlarmLevel+",");
			result_json.append("\"resultCode\":\""+obj[14]+"\",");
			result_json.append("\"resultName\":\""+obj[15]+"\",");
			result_json.append("\"optimizationSuggestion\":\""+obj[16]+"\",");
			result_json.append("\"resultAlarmLevel\":"+resultAlarmLevel+",");
			
			result_json.append("\"liquidWeightProduction\":\""+obj[17]+"\",");
			result_json.append("\"oilWeightProduction\":\""+obj[18]+"\",");
			result_json.append("\"waterWeightProduction\":\""+obj[19]+"\",");
			result_json.append("\"liquidWeightProduction_L\":\""+obj[20]+"\",");
			
			result_json.append("\"liquidVolumetricProduction\":\""+obj[21]+"\",");
			result_json.append("\"oilVolumetricProduction\":\""+obj[22]+"\",");
			result_json.append("\"waterVolumetricProduction\":\""+obj[23]+"\",");
			result_json.append("\"liquidVolumetricProduction_L\":\""+obj[24]+"\",");
			
			result_json.append("\"FMax\":\""+obj[25]+"\",");
			result_json.append("\"FMin\":\""+obj[26]+"\",");
			result_json.append("\"fullnessCoefficient\":\""+obj[27]+"\",");
			
			result_json.append("\"averageWatt\":\""+obj[28]+"\",");
			result_json.append("\"polishrodPower\":\""+obj[29]+"\",");
			result_json.append("\"waterPower\":\""+obj[30]+"\",");
			
			result_json.append("\"surfaceSystemEfficiency\":\""+obj[31]+"\",");
			result_json.append("\"welldownSystemEfficiency\":\""+obj[32]+"\",");
			result_json.append("\"systemEfficiency\":\""+obj[33]+"\",");
			result_json.append("\"energyper100mlift\":\""+obj[34]+"\",");
			result_json.append("\"pumpEff\":\""+obj[35]+"\",");
			
			result_json.append("\"iDegreeBalance\":\""+obj[36]+"\",");
			result_json.append("\"wattDegreeBalance\":\""+obj[37]+"\",");
			result_json.append("\"deltaradius\":\""+obj[38]+"\",");
			result_json.append("\"levelCorrectValue\":\""+obj[39]+"\",");
			result_json.append("\"inverProducingfluidLevel\":\""+obj[40]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[41]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		if(jedis!=null){
			jedis.close();
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getPCPDeviceHistoryData(String wellName,int statType,String statValue,String startDate,String endDate) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Jedis jedis=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("PCPDeviceInfo".getBytes())){
				MemoryDataManagerTask.loadPCPDeviceInfo(null,0,"update");
			}
			
			if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
				MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String hisTableName="tbl_pcpacqdata_hist";
		String deviceTableName="tbl_pcpdevice";
		
		String sql="select t2.id,t.id as wellId,t.wellname,"
				+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
				+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
				+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
				+ "t2.runstatus,decode(t2.commstatus,1,decode(t2.runstatus,1,'运行','停抽'),'') as runStatusName,"
				+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
				+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,"
				+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,"
				+ "averageWatt,waterPower,"
				+ "systemEfficiency*100 as systemEfficiency,energyper100mlift,pumpEff*100 as pumpEff,"
				+ "todayKWattH";
		
		sql+= " from "+deviceTableName+" t "
				+ " left outer join "+hisTableName+" t2 on t2.wellid=t.id"
				+ " where  t2.acqTime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss') ";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+= "and t.wellname='"+wellName+"'";
		}	
		sql+= "  order by t2.acqtime desc";
		
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String deviceId=obj[1]+"";
			PCPDeviceInfo pcpDeviceInfo=null;
			if(jedis!=null&&jedis.hexists("PCPDeviceInfo".getBytes(), deviceId.getBytes())){
				pcpDeviceInfo=(PCPDeviceInfo)SerializeObjectUnils.unserizlize(jedis.hget("PCPDeviceInfo".getBytes(), deviceId.getBytes()));
			}
			
			AlarmInstanceOwnItem alarmInstanceOwnItem=null;
			if(jedis!=null&&pcpDeviceInfo!=null&&jedis.hexists("AlarmInstanceOwnItem".getBytes(), pcpDeviceInfo.getAlarmInstanceCode().getBytes())){
				alarmInstanceOwnItem=(AlarmInstanceOwnItem) SerializeObjectUnils.unserizlize(jedis.hget("AlarmInstanceOwnItem".getBytes(), pcpDeviceInfo.getAlarmInstanceCode().getBytes()));
			}
			
			int commAlarmLevel=0,runAlarmLevel=0;
			if(alarmInstanceOwnItem!=null){
				for(int j=0;j<alarmInstanceOwnItem.itemList.size();j++){
					if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(obj[4]+"")){
						commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(obj[9]+"")){
						runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}
				}
			}
			
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"deviceId\":\""+deviceId+"\",");
			result_json.append("\"wellName\":\""+obj[2]+"\",");
			result_json.append("\"acqTime\":\""+obj[3]+"\",");
			result_json.append("\"commStatus\":"+obj[4]+",");
			result_json.append("\"commStatusName\":\""+obj[5]+"\",");
			result_json.append("\"commTime\":\""+obj[6]+"\",");
			result_json.append("\"commTimeEfficiency\":\""+obj[7]+"\",");
			result_json.append("\"commRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
			result_json.append("\"commAlarmLevel\":"+commAlarmLevel+",");
			result_json.append("\"runStatus\":"+obj[9]+",");
			result_json.append("\"runStatusName\":\""+obj[10]+"\",");
			result_json.append("\"runTime\":\""+obj[11]+"\",");
			result_json.append("\"runTimeEfficiency\":\""+obj[12]+"\",");
			result_json.append("\"runRange\":\""+StringManagerUtils.CLOBObjectToString(obj[13])+"\",");
			result_json.append("\"runAlarmLevel\":"+runAlarmLevel+",");
			
			result_json.append("\"liquidWeightProduction\":\""+obj[14]+"\",");
			result_json.append("\"oilWeightProduction\":\""+obj[15]+"\",");
			result_json.append("\"waterWeightProduction\":\""+obj[16]+"\",");
			result_json.append("\"liquidWeightProduction_L\":\""+obj[17]+"\",");
			
			result_json.append("\"liquidVolumetricProduction\":\""+obj[18]+"\",");
			result_json.append("\"oilVolumetricProduction\":\""+obj[19]+"\",");
			result_json.append("\"waterVolumetricProduction\":\""+obj[20]+"\",");
			result_json.append("\"liquidVolumetricProduction_L\":\""+obj[21]+"\",");
			
			result_json.append("\"averageWatt\":\""+obj[22]+"\",");
			result_json.append("\"waterPower\":\""+obj[23]+"\",");
			
			result_json.append("\"systemEfficiency\":\""+obj[24]+"\",");
			result_json.append("\"energyper100mlift\":\""+obj[25]+"\",");
			result_json.append("\"pumpEff\":\""+obj[26]+"\",");
			result_json.append("\"todayKWattH\":\""+obj[27]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		if(jedis!=null){
			jedis.close();
		}
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getOilWellAnalysisData(String data) throws SQLException, IOException{
		String json="";
		if(StringManagerUtils.isNotNull(data)){
			try{
				JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
				if(jsonObject!=null){
					int liftingType=2;
					try{
						liftingType=jsonObject.getInt("LiftingType");
					}catch(Exception e){
						e.printStackTrace();
					}
					if(liftingType!=2){
						json=getPumpunitRealtimeWellAnalysisData(data);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return json;
	}
	
	public String singleFESDiagramData(String data) throws SQLException, IOException {
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String prodCol=" t.liquidWeightProduction";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("stere")){
			prodCol=" t.liquidVolumetricProduction";
		}
		String wellName="";
		String acqTime="";
		if(StringManagerUtils.isNotNull(data)){
			try{
				JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
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
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String sql="";
		String hisTableName="tbl_pcpacqdata_hist";
		String deviceTableName="tbl_pcpdevice";
		
		sql="select t.id, t.wellName, to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime, "
				+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
				+ " t.upperLoadline, t.lowerloadline, t.fmax, t.fmin, t.stroke, t.SPM, "+prodCol+", t3.resultName "
				+ " from "+hisTableName+" t, "+deviceTableName+" t2,tbl_rpc_worktype t3"
				+ " where t.wellid=t2.id and t.resultcode=t3.resultcode"
				+ " and t2.wellName='" + wellName + "' "
				+ " and t.acqTime = to_date('"+ acqTime +"','yyyy-MM-dd hh24:mi:ss')";
		
		List<?> list=this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalRoot\":[");
		
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			CLOB realClob=null;
			SerializableClobProxy   proxy=null;
			String sStr="";
	        String fStr="";
	        String wattStr="";
	        String aStr="";
	        String pointCount="";
	        if(obj[3]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[3]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				sStr=StringManagerUtils.CLOBtoString(realClob);
				if(StringManagerUtils.isNotNull(sStr)){
					pointCount=sStr.split(",").length+"";
				}
			}
	        if(obj[4]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[4]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				fStr=StringManagerUtils.CLOBtoString(realClob);
			}
	        if(obj[5]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[5]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				wattStr=StringManagerUtils.CLOBtoString(realClob);
			}
	        if(obj[6]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[6]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				aStr=StringManagerUtils.CLOBtoString(realClob);
			}
	        
			result_json.append("{ \"id\":\"" + obj[0] + "\",");
			result_json.append("\"wellName\":\"" + obj[1] + "\",");
			result_json.append("\"acqTime\":\"" + obj[2] + "\",");
			result_json.append("\"pointCount\":\""+pointCount+"\","); 
			result_json.append("\"upperLoadLine\":\"" + obj[7] + "\",");
			result_json.append("\"lowerLoadLine\":\"" + obj[8] + "\",");
			result_json.append("\"fmax\":\""+obj[9]+"\",");
			result_json.append("\"fmin\":\""+obj[10]+"\",");
			result_json.append("\"stroke\":\""+obj[11]+"\",");
			result_json.append("\"spm\":\""+obj[12]+"\",");
			result_json.append("\"liquidProduction\":\""+obj[13]+"\",");
			result_json.append("\"resultName\":\""+obj[14]+"\",");
			result_json.append("\"S\":["+sStr+"],"); 
			result_json.append("\"F\":["+fStr+"],"); 
			result_json.append("\"Watt\":["+wattStr+"],"); 
			result_json.append("\"A\":["+aStr+"]},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public String historyFESDiagramData(String data) throws SQLException, IOException {
		StringBuffer result_json = new StringBuffer();
		ConfigFile configFile=Config.getInstance().configFile;
		String prodCol=" t.liquidWeightProduction";
		if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("stere")){
			prodCol=" t.liquidVolumetricProduction";
		}
		String wellName="";
		String startDate=StringManagerUtils.getCurrentTime();
		String endDate=StringManagerUtils.getCurrentTime();
		if(StringManagerUtils.isNotNull(data)){
			try{
				JSONObject jsonObject = JSONObject.fromObject(data);//解析数据
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
		}
		String sql="";
		String hisTableName="tbl_pcpacqdata_hist";
		String deviceTableName="tbl_pcpdevice";
		
		sql="select t.id, t.wellName, to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime, "
				+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
				+ " t.upperLoadline, t.lowerloadline, t.fmax, t.fmin, t.stroke, t.SPM, "+prodCol+", t3.resultName "
				+ " from "+hisTableName+" t, "+deviceTableName+" t2,tbl_rpc_worktype t3"
				+ " where t.wellid=t2.id and t.resultcode=t3.resultcode";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+= " and t2.wellName='" + wellName + "' ";
		}
		sql+= " and t.acqTime between to_date('"+ startDate +"','yyyy-MM-dd') and to_date('"+ endDate +"','yyyy-MM-dd')+1";
		sql+=" order by t.acqTime desc";
		List<?> list=this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalRoot\":[");
		
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			CLOB realClob=null;
			SerializableClobProxy   proxy=null;
			String sStr="";
	        String fStr="";
	        String wattStr="";
	        String aStr="";
	        String pointCount="";
	        if(obj[3]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[3]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				sStr=StringManagerUtils.CLOBtoString(realClob);
				if(StringManagerUtils.isNotNull(sStr)){
					pointCount=sStr.split(",").length+"";
				}
			}
	        if(obj[4]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[4]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				fStr=StringManagerUtils.CLOBtoString(realClob);
			}
	        if(obj[5]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[5]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				wattStr=StringManagerUtils.CLOBtoString(realClob);
			}
	        if(obj[6]!=null){
				proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[6]);
				realClob = (CLOB) proxy.getWrappedClob(); 
				aStr=StringManagerUtils.CLOBtoString(realClob);
			}
	        
			result_json.append("{ \"id\":\"" + obj[0] + "\",");
			result_json.append("\"wellName\":\"" + obj[1] + "\",");
			result_json.append("\"acqTime\":\"" + obj[2] + "\",");
			result_json.append("\"pointCount\":\""+pointCount+"\","); 
			result_json.append("\"upperLoadLine\":\"" + obj[7] + "\",");
			result_json.append("\"lowerLoadLine\":\"" + obj[8] + "\",");
			result_json.append("\"fmax\":\""+obj[9]+"\",");
			result_json.append("\"fmin\":\""+obj[10]+"\",");
			result_json.append("\"stroke\":\""+obj[11]+"\",");
			result_json.append("\"spm\":\""+obj[12]+"\",");
			result_json.append("\"liquidProduction\":\""+obj[13]+"\",");
			result_json.append("\"resultName\":\""+obj[14]+"\",");
			result_json.append("\"S\":["+sStr+"],"); 
			result_json.append("\"F\":["+fStr+"],"); 
			result_json.append("\"Watt\":["+wattStr+"],"); 
			result_json.append("\"A\":["+aStr+"]},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
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
					String prodCol=" t.liquidWeightProduction,t.oilWeightProduction,t.waterWeightProduction,"
							+ " t.availablePlungerstrokeProd_W,t.pumpClearanceLeakProd_W,t.tvleakWeightProduction,t.svleakWeightProduction,t.gasInfluenceProd_W,";
					if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("stere")){
						prodCol=" t.liquidVolumetricProduction,t.oilVolumetricProduction,t.waterVolumetricProduction,"
								+ " t.availablePlungerstrokeProd_V,t.pumpClearanceLeakProd_V,t.tvleakVolumetricProduction,t.svleakVolumetricProduction,t.gasInfluenceProd_V,";;
					}
					String hisTableName="tbl_pcpacqdata_hist";
					String deviceTableName="tbl_pcpdevice";
					String sql="select t3.resultName,"
							+ " t.wattDegreeBalance,t.iDegreeBalance,t.deltaRadius,"
							+ prodCol
							+ " t.theoreticalProduction,"
							+ " t.plungerstroke,t.availableplungerstroke,"
							+ " t.pumpBoreDiameter,t.pumpSettingDepth,t.producingFluidLevel,t.submergence,"
							+ " t.stroke,t.spm,t.fmax,t.fmin,t.deltaF,t.deltaLoadLine,area,"
							+ " t.averageWatt,t.polishrodPower,t.waterPower,t.surfaceSystemEfficiency,t.welldownSystemEfficiency,t.systemEfficiency,t.energyPer100mLift,"
							+ " t.pumpEff1,t.pumpEff2,t.pumpEff3,t.pumpEff4,t.pumpEff,"
							+ " t.rodFlexLength,t.tubingFlexLength,t.inertiaLength,"
							+ " t.pumpintakep,t.pumpintaket,t.pumpintakegol,t.pumpintakevisl,t.pumpintakebo,"
							+ " t.pumpoutletp,t.pumpoutlett,t.pumpOutletGol,t.pumpoutletvisl,t.pumpoutletbo"
							+ " from "+hisTableName+" t, "+deviceTableName+" t2,tbl_rpc_worktype t3"
							+ " where t.wellid=t2.id and t.resultcode=t3.resultcode"
							+ " and  t2.wellname='"+wellName+"' and t.acqtime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')"; 
					List<?> list = this.findCallSql(sql);
					result_json.append("{ \"success\":true,");
					if(list.size()>0){
						Object[] obj=(Object[]) list.get(0);
						result_json.append("\"resultName\":\""+obj[0]+"\",");
						
						result_json.append("\"wattDegreeBalance\":\""+obj[1]+"\",");
						result_json.append("\"iDegreeBalance\":\""+obj[2]+"\",");
						result_json.append("\"deltaRadius\":\""+obj[3]+"\",");
						
						result_json.append("\"liquidProduction\":\""+obj[4]+"\",");
						result_json.append("\"oilProduction\":\""+obj[5]+"\",");
						result_json.append("\"waterProduction\":\""+obj[6]+"\",");
						
						result_json.append("\"availablePlungerstrokeProd\":\""+obj[7]+"\",");
						result_json.append("\"pumpClearanceLeakProd\":\""+obj[8]+"\",");
						result_json.append("\"tvleakProduction\":\""+obj[9]+"\",");
						result_json.append("\"svleakProduction\":\""+obj[10]+"\",");
						result_json.append("\"gasInfluenceProd\":\""+obj[11]+"\",");
						
						result_json.append("\"theoreticalProduction\":\""+obj[12]+"\",");
						result_json.append("\"plungerStroke\":\""+obj[13]+"\",");
						result_json.append("\"availablePlungerStroke\":\""+obj[14]+"\",");
						
						result_json.append("\"pumpBoreDiameter\":\""+obj[15]+"\",");
						result_json.append("\"pumpSettingDepth\":\""+obj[16]+"\",");
						result_json.append("\"producingFluidLevel\":\""+obj[17]+"\",");
						result_json.append("\"submergence\":\""+obj[18]+"\",");
						
						result_json.append("\"stroke\":\""+obj[19]+"\",");
						result_json.append("\"spm\":\""+obj[20]+"\",");
						result_json.append("\"fmax\":\""+obj[21]+"\",");
						result_json.append("\"fmin\":\""+obj[22]+"\",");
						result_json.append("\"deltaF\":\""+obj[23]+"\",");
						result_json.append("\"deltaLoadLine\":\""+obj[24]+"\",");
						result_json.append("\"area\":\""+obj[25]+"\",");
						
						result_json.append("\"averageWatt\":\""+obj[26]+"\",");
						result_json.append("\"polishrodPower\":\""+obj[27]+"\",");
						result_json.append("\"waterPower\":\""+obj[28]+"\",");
						result_json.append("\"surfaceSystemEfficiency\":\""+obj[29]+"\",");
						result_json.append("\"welldownSystemEfficiency\":\""+obj[30]+"\",");
						result_json.append("\"systemEfficiency\":\""+obj[31]+"\",");
						result_json.append("\"energyPer100mLift\":\""+obj[32]+"\",");
						result_json.append("\"pumpEff1\":\""+obj[33]+"\",");
						result_json.append("\"pumpEff2\":\""+obj[34]+"\",");
						result_json.append("\"pumpEff3\":\""+obj[35]+"\",");
						result_json.append("\"pumpEff4\":\""+obj[36]+"\",");
						result_json.append("\"pumpEff\":\""+obj[37]+"\",");
						result_json.append("\"rodFlexLength\":\""+obj[38]+"\",");
						result_json.append("\"tubingFlexLength\":\""+obj[39]+"\",");
						result_json.append("\"inertiaLength\":\""+obj[40]+"\",");
						
						result_json.append("\"pumpIntakeP\":\""+obj[41]+"\",");
						result_json.append("\"pumpIntakeT\":\""+obj[42]+"\",");
						result_json.append("\"pumpIntakeGOL\":\""+obj[43]+"\",");
						result_json.append("\"pumpIntakeVisl\":\""+obj[44]+"\",");
						result_json.append("\"pumpIntakeBo\":\""+obj[45]+"\",");
						
						result_json.append("\"pumpOutletP\":\""+obj[46]+"\",");
						result_json.append("\"pumpOutletT\":\""+obj[47]+"\",");
						result_json.append("\"pumpOutletGOL\":\""+obj[48]+"\",");
						result_json.append("\"pumpOutletVisl\":\""+obj[49]+"\",");
						result_json.append("\"pumpOutletBo\":\""+obj[50]+"\"");
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
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("stere")){
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
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("stere")){
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
			if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("stere")){
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
