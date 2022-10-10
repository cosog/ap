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
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.RPCCalculateRequestData;
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
import com.google.gson.reflect.TypeToken;

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
						+ " order by Org.org_code  ";
			}else{
				queryString = "SELECT {Org.*} FROM tbl_org Org   "
						+ " start with Org.org_id=( select t2.user_orgid from tbl_user t2 where t2.user_id='"+userAccount+"' )   "
						+ " connect by Org.org_parent= prior Org.org_id "
						+ " order by Org.org_code  ";
			}
		}else{
			queryString = "SELECT {Org.*} FROM tbl_org Org   "
					+ " order by Org.org_code  ";
		}
		
		return getBaseDao().getSqlToHqlOrgObjects(queryString);
	}
	
	public String getPumpingRealtimeStatisticsDataByWellList(String data){
		StringBuffer wells= new StringBuffer();
		String json="{\"Success\":false,\"TotalRoot\":[]}";
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
		result_json.append("{ \"Success\":true,");
		
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
			result_json.append("\"TotalRoot\":[");
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
				
				result_json.append("\"TotalRoot\":[");
				for(Integer key:totalMap.keySet()){
					String item="无数据";
					if(jedis.hexists("RPCWorkType".getBytes(), (key+"").getBytes())){
						WorkType workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("RPCWorkType".getBytes(), (key+"").getBytes()));
						item=workType.getResultName();
					}
					result_json.append("{\"Item\":\""+item+"\",");
					result_json.append("\"Count\":"+totalMap.get(key)+"},");
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
		List<byte[]> deviceInfoByteList=null;
		String [] wellList=null;
		if(StringManagerUtils.isNotNull(wells)){
			wellList=wells.split(",");
		}
		try{
			jedis = RedisUtil.jedisPool.getResource();
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
		result_json.append("{ \"Success\":true,");
		int online=0,goOnline=0,offline=0;
		if(jedis==null){
			String tableName="tbl_rpcacqdata_latest";
			String deviceTableName="viw_rpcdevice";
			if(deviceType==2){
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
		
		result_json.append("\"TotalRoot\":[");
		
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
		List<byte[]> deviceInfoByteList=null;
		String [] wellList=null;
		if(StringManagerUtils.isNotNull(wells)){
			wellList=wells.split(",");
		}
		try{
			jedis = RedisUtil.jedisPool.getResource();
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
		result_json.append("{ \"Success\":true,");
		int run=0,stop=0,offline=0;
		if(jedis==null){
			String tableName="tbl_rpcacqdata_latest";
			String deviceTableName="viw_rpcdevice";
			if(deviceType==2){
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
		
		result_json.append("\"TotalRoot\":[");
		
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
		Jedis jedis=null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
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
				+ "to_char(t2.fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
				+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
				+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
				+ "t2.runstatus,decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽')) as runStatusName,"
				+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
				+ "t2.resultcode,decode(t2.resultcode,null,'无数据',t3.resultName) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
				+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,"
				+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,"
				
				+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
				+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
				+ "t2.systemEfficiency*100 as systemEfficiency,t2.energyper100mlift,"
				+ "t2.pumpEff*100 as pumpEff,"
				+ "t2.iDegreeBalance,t2.wattDegreeBalance,t2.deltaradius*100 as deltaradius,"
				+ "t2.todayKWattH,"
				+ "t2.productiondata";
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
		result_json.append("{ \"Success\":true,");
		result_json.append("\"TotalCount\":"+totals+",");
		result_json.append("\"TotalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String deviceId=obj[0]+"";
			String commStatusName=obj[7]+"";
			String runStatusName=obj[12]+"";
			String resultcode=obj[16]+"";
			String weightWaterCut="";
			String volumeWaterCut="";
			String productionData=obj[obj.length-1].toString();
			type = new TypeToken<RPCCalculateRequestData>() {}.getType();
			RPCCalculateRequestData rpcProductionData=gson.fromJson(productionData, type);
			if(rpcProductionData!=null&&rpcProductionData.getProduction()!=null){
				weightWaterCut=rpcProductionData.getProduction().getWeightWaterCut()+"";
				volumeWaterCut=rpcProductionData.getProduction().getWaterCut()+"";
			}
			
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
					if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
						commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
						runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==4 && alarmInstanceOwnItem.getItemList().get(j).getItemCode().equalsIgnoreCase(resultcode)){
						resultAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}
				}
			}
			
			result_json.append("{\"Id\":"+deviceId+",");
			result_json.append("\"WellName\":\""+obj[1]+"\",");
			result_json.append("\"VideoUrl\":\""+obj[2]+"\",");
			result_json.append("\"VideoAccessToken\":\""+obj[3]+"\",");
			result_json.append("\"DeviceTypeName\":\""+obj[4]+"\",");
			result_json.append("\"AcqTime\":\""+obj[5]+"\",");
			result_json.append("\"CommStatus\":"+obj[6]+",");
			result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
			result_json.append("\"CommTime\":\""+obj[8]+"\",");
			result_json.append("\"CommTimeEfficiency\":\""+obj[9]+"\",");
			result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[10])+"\",");
			
			result_json.append("\"RunStatus\":"+obj[11]+",");
			result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
			result_json.append("\"RunTime\":\""+obj[13]+"\",");
			result_json.append("\"RunTimeEfficiency\":\""+obj[14]+"\",");
			result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[15])+"\",");
			result_json.append("\"ResultCode\":\""+obj[16]+"\",");
			result_json.append("\"ResultName\":\""+obj[17]+"\",");
			result_json.append("\"OptimizationSuggestion\":\""+obj[18]+"\",");
			
			result_json.append("\"LiquidWeightProduction\":\""+obj[19]+"\",");
			result_json.append("\"OilWeightProduction\":\""+obj[20]+"\",");
			result_json.append("\"WaterWeightProduction\":\""+obj[21]+"\",");
			result_json.append("\"WeightWaterCut\":\""+weightWaterCut+"\",");
			result_json.append("\"LiquidWeightProduction_L\":\""+obj[22]+"\",");
			
			result_json.append("\"LiquidVolumetricProduction\":\""+obj[23]+"\",");
			result_json.append("\"OilVolumetricProduction\":\""+obj[24]+"\",");
			result_json.append("\"WaterVolumetricProduction\":\""+obj[25]+"\",");
			result_json.append("\"VolumeWaterCut\":\""+volumeWaterCut+"\",");
			result_json.append("\"LiquidVolumetricProduction_L\":\""+obj[26]+"\",");
			
			result_json.append("\"SurfaceSystemEfficiency\":\""+obj[27]+"\",");
			result_json.append("\"WelldownSystemEfficiency\":\""+obj[28]+"\",");
			result_json.append("\"SystemEfficiency\":\""+obj[29]+"\",");
			result_json.append("\"Energyper100mlift\":\""+obj[30]+"\",");
			result_json.append("\"PumpEff\":\""+obj[31]+"\",");
			
			result_json.append("\"IDegreeBalance\":\""+obj[32]+"\",");
			result_json.append("\"WattDegreeBalance\":\""+obj[33]+"\",");
			result_json.append("\"Deltaradius\":\""+obj[34]+"\",");
			
			result_json.append("\"TodayKWattH\":\""+obj[35]+"\",");
			

			result_json.append("\"ResultAlarmLevel\":"+resultAlarmLevel+",");
			result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
			result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
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
		Jedis jedis=null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
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
		
		String tableName="tbl_pcpacqdata_latest";
		String deviceTableName="tbl_pcpdevice";
		
		String sql="select t.id,t.wellname,t.videourl,t.videoAccessToken,"
				+ "c1.itemname as devicetypename,"
				+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
				+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
				+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
				+ "t2.runstatus,decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽')) as runStatusName,"
				+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
				+ "t2.liquidWeightProduction,t2.oilWeightProduction,t2.waterWeightProduction,t2.liquidWeightProduction_L,"
				+ "t2.liquidVolumetricProduction,t2.oilVolumetricProduction,t2.waterVolumetricProduction,t2.liquidVolumetricProduction_L,"
				+ "t2.systemEfficiency*100 as systemEfficiency,t2.energyper100mlift,t2.pumpEff*100 as pumpEff,"
				+ "t2.todayKWattH,"
				+ "t2.productiondata";
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
		result_json.append("{ \"Success\":true,");
		result_json.append("\"TotalCount\":"+totals+",");
		result_json.append("\"TotalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String deviceId=obj[0]+"";
			String commStatusName=obj[7]+"";
			String runStatusName=obj[12]+"";
			
			String weightWaterCut="";
			String volumeWaterCut="";
			String productionData=obj[obj.length-1].toString();
			type = new TypeToken<PCPCalculateRequestData>() {}.getType();
			PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
			if(pcpProductionData!=null&&pcpProductionData.getProduction()!=null){
				weightWaterCut=pcpProductionData.getProduction().getWeightWaterCut()+"";
				volumeWaterCut=pcpProductionData.getProduction().getWaterCut()+"";
			}
			
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
					if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
						commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
						runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}
				}
			}
			
			result_json.append("{\"Id\":"+deviceId+",");
			result_json.append("\"WellName\":\""+obj[1]+"\",");
			result_json.append("\"VideoUrl\":\""+obj[2]+"\",");
			result_json.append("\"VideoAccessToken\":\""+obj[3]+"\",");
			result_json.append("\"DeviceTypeName\":\""+obj[4]+"\",");
			result_json.append("\"AcqTime\":\""+obj[5]+"\",");
			result_json.append("\"CommStatus\":"+obj[6]+",");
			result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
			result_json.append("\"CommTime\":\""+obj[8]+"\",");
			result_json.append("\"CommTimeEfficiency\":\""+obj[9]+"\",");
			result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[10])+"\",");
			
			result_json.append("\"RunStatus\":"+obj[11]+",");
			result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
			result_json.append("\"RunTime\":\""+obj[13]+"\",");
			result_json.append("\"RunTimeEfficiency\":\""+obj[14]+"\",");
			result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[15])+"\",");
			
			result_json.append("\"LiquidWeightProduction\":\""+obj[16]+"\",");
			result_json.append("\"OilWeightProduction\":\""+obj[17]+"\",");
			result_json.append("\"WaterWeightProduction\":\""+obj[18]+"\",");
			result_json.append("\"WeightWaterCut\":\""+weightWaterCut+"\",");
			result_json.append("\"LiquidWeightProduction_L\":\""+obj[19]+"\",");
			
			result_json.append("\"LiquidVolumetricProduction\":\""+obj[20]+"\",");
			result_json.append("\"OilVolumetricProduction\":\""+obj[21]+"\",");
			result_json.append("\"WaterVolumetricProduction\":\""+obj[22]+"\",");
			result_json.append("\"VolumeWaterCut\":\""+volumeWaterCut+"\",");
			result_json.append("\"LiquidVolumetricProduction_L\":\""+obj[23]+"\",");
			
			result_json.append("\"SystemEfficiency\":\""+obj[24]+"\",");
			result_json.append("\"Energyper100mlift\":\""+obj[25]+"\",");
			result_json.append("\"PumpEff\":\""+obj[26]+"\",");
			
			result_json.append("\"TodayKWattH\":\""+obj[27]+"\",");
			
			result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
			result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
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
		String startDate=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String endDate=startDate;
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
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
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
				+ "to_char(t2.fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
				+ "t2.commstatus,decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
				+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
				+ "t2.runstatus,decode(t2.commstatus,1,decode(t2.runstatus,1,'运行','停抽'),'') as runStatusName,"
				+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
				+ "t2.resultcode,decode(t2.commstatus,1,decode(t2.resultcode,null,'无数据',t3.resultName),'' ) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
				+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,liquidWeightProduction_L,"
				+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,liquidVolumetricProduction_L,"
				+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
				+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
				+ "t2.systemEfficiency*100 as systemEfficiency,"
				+ "t2.energyper100mlift,t2.pumpEff*100 as pumpEff,"
				+ "t2.iDegreeBalance,t2.wattDegreeBalance,t2.deltaradius*100 as deltaradius,"
				+ "t2.todayKWattH,"
				+ "t2.productiondata";
		sql+= " from "+deviceTableName+" t "
				+ " left outer join "+hisTableName+" t2 on t2.wellid=t.id"
				+ " left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode "
				+ " where  t2.fesdiagramAcqTime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss') ";
		if(statType==1 && StringManagerUtils.isNotNull(statValue)){
			sql+=" and decode(t2.resultcode,null,'无数据',t3.resultName)='"+statValue+"'";
		}else if(statType==2 && StringManagerUtils.isNotNull(statValue)){
			sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+statValue+"'";
		}else if(statType==3 && StringManagerUtils.isNotNull(statValue)){
			sql+=" and decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽'))='"+statValue+"'";
		}
		
		
		if(StringManagerUtils.isNotNull(wellName)){
			sql+= "and t.wellname='"+wellName+"'";
		}	
		sql+= "  order by t2.fesdiagramAcqTime desc";
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"Success\":true,");
		result_json.append("\"TotalCount\":"+totals+",");
		result_json.append("\"TotalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String deviceId=obj[1]+"";
			String commStatusName=obj[5]+"";
			String runStatusName=obj[10]+"";
			String resultcode=obj[14]+"";
			
			String weightWaterCut="";
			String volumeWaterCut="";
			String productionData=obj[obj.length-1].toString();
			type = new TypeToken<RPCCalculateRequestData>() {}.getType();
			RPCCalculateRequestData rpcProductionData=gson.fromJson(productionData, type);
			if(rpcProductionData!=null&&rpcProductionData.getProduction()!=null){
				weightWaterCut=rpcProductionData.getProduction().getWeightWaterCut()+"";
				volumeWaterCut=rpcProductionData.getProduction().getWaterCut()+"";
			}
			
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
					if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
						commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
						runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==4 && alarmInstanceOwnItem.getItemList().get(j).getItemCode().equalsIgnoreCase(resultcode)){
						resultAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}
				}
			}
			
			result_json.append("{\"Id\":"+obj[0]+",");
			result_json.append("\"DeviceId\":\""+deviceId+"\",");
			result_json.append("\"WellName\":\""+obj[2]+"\",");
			result_json.append("\"AcqTime\":\""+obj[3]+"\",");
			result_json.append("\"CommStatus\":"+obj[4]+",");
			result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
			result_json.append("\"CommTime\":\""+obj[6]+"\",");
			result_json.append("\"CommTimeEfficiency\":\""+obj[7]+"\",");
			result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
			result_json.append("\"RunStatus\":"+obj[9]+",");
			result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
			result_json.append("\"RunTime\":\""+obj[11]+"\",");
			result_json.append("\"RunTimeEfficiency\":\""+obj[12]+"\",");
			result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[13])+"\",");
			result_json.append("\"ResultCode\":\""+resultcode+"\",");
			result_json.append("\"ResultName\":\""+obj[15]+"\",");
			result_json.append("\"OptimizationSuggestion\":\""+obj[16]+"\",");
			result_json.append("\"LiquidWeightProduction\":\""+obj[17]+"\",");
			result_json.append("\"OilWeightProduction\":\""+obj[18]+"\",");
			result_json.append("\"WaterWeightProduction\":\""+obj[19]+"\",");
			result_json.append("\"WeightWaterCut\":\""+weightWaterCut+"\",");
			result_json.append("\"LiquidWeightProduction_L\":\""+obj[20]+"\",");
			
			result_json.append("\"LiquidVolumetricProduction\":\""+obj[21]+"\",");
			result_json.append("\"OilVolumetricProduction\":\""+obj[22]+"\",");
			result_json.append("\"WaterVolumetricProduction\":\""+obj[23]+"\",");
			result_json.append("\"VolumeWaterCut\":\""+volumeWaterCut+"\",");
			result_json.append("\"LiquidVolumetricProduction_L\":\""+obj[24]+"\",");
			
			result_json.append("\"SurfaceSystemEfficiency\":\""+obj[25]+"\",");
			result_json.append("\"WelldownSystemEfficiency\":\""+obj[26]+"\",");
			result_json.append("\"SystemEfficiency\":\""+obj[27]+"\",");
			result_json.append("\"Energyper100mlift\":\""+obj[28]+"\",");
			result_json.append("\"PumpEff\":\""+obj[29]+"\",");
			
			result_json.append("\"IDegreeBalance\":\""+obj[30]+"\",");
			result_json.append("\"WattDegreeBalance\":\""+obj[31]+"\",");
			result_json.append("\"Deltaradius\":\""+obj[32]+"\",");
			result_json.append("\"TodayKWattH\":\""+obj[33]+"\",");
			
			result_json.append("\"ResultAlarmLevel\":"+resultAlarmLevel+",");
			result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
			result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
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
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
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
				+ "systemEfficiency*100 as systemEfficiency,energyper100mlift,pumpEff*100 as pumpEff,"
				+ "todayKWattH,"
				+ "t2.productiondata";
		
		sql+= " from "+deviceTableName+" t "
				+ " left outer join "+hisTableName+" t2 on t2.wellid=t.id"
				+ " where  t2.acqTime between to_date('"+startDate+"','yyyy-mm-dd hh24:mi:ss') and to_date('"+endDate+"','yyyy-mm-dd hh24:mi:ss') ";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+= "and t.wellname='"+wellName+"'";
		}	
		if(statType==2 && StringManagerUtils.isNotNull(statValue)){
			sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+statValue+"'";
		}else if(statType==3 && StringManagerUtils.isNotNull(statValue)){
			sql+=" and decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽'))='"+statValue+"'";
		}
		sql+= "  order by t2.acqtime desc";
		
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"Success\":true,");
		result_json.append("\"TotalCount\":"+totals+",");
		result_json.append("\"TotalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String deviceId=obj[1]+"";
			String commStatusName=obj[5]+"";
			String runStatusName=obj[10]+"";
			
			String weightWaterCut="";
			String volumeWaterCut="";
			String productionData=obj[obj.length-1].toString();
			type = new TypeToken<PCPCalculateRequestData>() {}.getType();
			PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
			if(pcpProductionData!=null&&pcpProductionData.getProduction()!=null){
				weightWaterCut=pcpProductionData.getProduction().getWeightWaterCut()+"";
				volumeWaterCut=pcpProductionData.getProduction().getWaterCut()+"";
			}
			
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
					if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
						commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
						runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}
				}
			}
			
			result_json.append("{\"Id\":"+obj[0]+",");
			result_json.append("\"DeviceId\":\""+deviceId+"\",");
			result_json.append("\"WellName\":\""+obj[2]+"\",");
			result_json.append("\"AcqTime\":\""+obj[3]+"\",");
			result_json.append("\"CommStatus\":"+obj[4]+",");
			result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
			result_json.append("\"CommTime\":\""+obj[6]+"\",");
			result_json.append("\"CommTimeEfficiency\":\""+obj[7]+"\",");
			result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
			result_json.append("\"RunStatus\":"+obj[9]+",");
			result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
			result_json.append("\"RunTime\":\""+obj[11]+"\",");
			result_json.append("\"RunTimeEfficiency\":\""+obj[12]+"\",");
			result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[13])+"\",");
			
			result_json.append("\"LiquidWeightProduction\":\""+obj[14]+"\",");
			result_json.append("\"OilWeightProduction\":\""+obj[15]+"\",");
			result_json.append("\"WaterWeightProduction\":\""+obj[16]+"\",");
			result_json.append("\"WeightWaterCut\":\""+weightWaterCut+"\",");
			result_json.append("\"LiquidWeightProduction_L\":\""+obj[17]+"\",");
			
			result_json.append("\"LiquidVolumetricProduction\":\""+obj[18]+"\",");
			result_json.append("\"OilVolumetricProduction\":\""+obj[19]+"\",");
			result_json.append("\"WaterVolumetricProduction\":\""+obj[20]+"\",");
			result_json.append("\"VolumeWaterCut\":\""+volumeWaterCut+"\",");
			result_json.append("\"LiquidVolumetricProduction_L\":\""+obj[21]+"\",");
			
			result_json.append("\"SystemEfficiency\":\""+obj[22]+"\",");
			result_json.append("\"Energyper100mlift\":\""+obj[23]+"\",");
			result_json.append("\"PumpEff\":\""+obj[24]+"\",");
			result_json.append("\"TodayKWattH\":\""+obj[25]+"\",");
			
			result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
			result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
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
					if(liftingType==1){
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
		String hisTableName="tbl_rpcacqdata_hist";
		String deviceTableName="tbl_rpcdevice";
		
		sql="select t.id, t2.wellName, to_char(t.fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime, "
				+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
				+ " t.upperLoadline, t.lowerloadline, t.fmax, t.fmin, t.stroke, t.SPM, "+prodCol+", t3.resultName "
				+ " from "+hisTableName+" t, "+deviceTableName+" t2,tbl_rpc_worktype t3"
				+ " where t.wellid=t2.id and t.resultcode=t3.resultcode"
				+ " and t2.wellName='" + wellName + "' "
				+ " and t.fesdiagramAcqTime = to_date('"+ acqTime +"','yyyy-MM-dd hh24:mi:ss')";
		
		List<?> list=this.findCallSql(sql);
		result_json.append("{\"Success\":true,\"TotalRoot\":[");
		
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
	        
			result_json.append("{ \"Id\":\"" + obj[0] + "\",");
			result_json.append("\"WellName\":\"" + obj[1] + "\",");
			result_json.append("\"AcqTime\":\"" + obj[2] + "\",");
			result_json.append("\"PointCount\":\""+pointCount+"\","); 
			result_json.append("\"UpperLoadLine\":\"" + obj[7] + "\",");
			result_json.append("\"LowerLoadLine\":\"" + obj[8] + "\",");
			result_json.append("\"Fmax\":\""+obj[9]+"\",");
			result_json.append("\"Fmin\":\""+obj[10]+"\",");
			result_json.append("\"Stroke\":\""+obj[11]+"\",");
			result_json.append("\"SPM\":\""+obj[12]+"\",");
			result_json.append("\"LiquidProduction\":\""+obj[13]+"\",");
			result_json.append("\"ResultName\":\""+obj[14]+"\",");
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
		String startDate=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String endDate=startDate;
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
		String hisTableName="tbl_rpcacqdata_hist";
		String deviceTableName="tbl_rpcdevice";
		
		sql="select t.id, t2.wellName, to_char(t.fesdiagramAcqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime, "
				+ " t.position_curve,t.load_curve,t.power_curve,t.current_curve,"
				+ " t.upperLoadline, t.lowerloadline, t.fmax, t.fmin, t.stroke, t.SPM, "+prodCol+", t3.resultName "
				+ " from "+hisTableName+" t, "+deviceTableName+" t2,tbl_rpc_worktype t3"
				+ " where t.wellid=t2.id and t.resultcode=t3.resultcode";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+= " and t2.wellName='" + wellName + "' ";
		}
		sql+= " and t.fesdiagramAcqTime between to_date('"+ startDate +"','yyyy-MM-dd hh24:mi:ss') and to_date('"+ endDate +"','yyyy-MM-dd hh24:mi:ss')";
		sql+=" order by t.fesdiagramAcqTime desc";
		List<?> list=this.findCallSql(sql);
		result_json.append("{\"Success\":true,\"TotalRoot\":[");
		
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
	        
			result_json.append("{ \"Id\":\"" + obj[0] + "\",");
			result_json.append("\"WellName\":\"" + obj[1] + "\",");
			result_json.append("\"AcqTime\":\"" + obj[2] + "\",");
			result_json.append("\"PointCount\":\""+pointCount+"\","); 
			result_json.append("\"UpperLoadLine\":\"" + obj[7] + "\",");
			result_json.append("\"LowerLoadLine\":\"" + obj[8] + "\",");
			result_json.append("\"Fmax\":\""+obj[9]+"\",");
			result_json.append("\"Fmin\":\""+obj[10]+"\",");
			result_json.append("\"Stroke\":\""+obj[11]+"\",");
			result_json.append("\"SPM\":\""+obj[12]+"\",");
			result_json.append("\"LiquidProduction\":\""+obj[13]+"\",");
			result_json.append("\"ResultName\":\""+obj[14]+"\",");
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
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
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
					String hisTableName="tbl_rpcacqdata_hist";
					String deviceTableName="tbl_rpcdevice";
					String sql="select t3.resultName,"
							+ " t.wattDegreeBalance,t.iDegreeBalance,t.deltaRadius,"
							+ prodCol
							+ " t.theoreticalProduction,"
							+ " t.plungerstroke,t.availableplungerstroke,"
							+ " t.submergence,"
							+ " t.stroke,t.spm,t.fmax,t.fmin,t.fmax-t.fmin as deltaF,t.upperloadline-t.lowerloadline as deltaLoadLine,area,"
							+ " t.averageWatt,t.polishrodPower,t.waterPower,t.surfaceSystemEfficiency*100,t.welldownSystemEfficiency*100,t.systemEfficiency*100,t.energyPer100mLift,"
							+ " t.pumpEff1*100,t.pumpEff2*100,t.pumpEff3*100,t.pumpEff4*100,t.pumpEff*100,"
							+ " t.rodFlexLength,t.tubingFlexLength,t.inertiaLength,"
							+ " t.pumpintakep,t.pumpintaket,t.pumpintakegol,t.pumpintakevisl,t.pumpintakebo,"
							+ " t.pumpoutletp,t.pumpoutlett,t.pumpOutletGol,t.pumpoutletvisl,t.pumpoutletbo,"
							+ " t.productiondata"
							+ " from "+hisTableName+" t, "+deviceTableName+" t2,tbl_rpc_worktype t3"
							+ " where t.wellid=t2.id and t.resultcode=t3.resultcode"
							+ " and  t2.wellname='"+wellName+"' and t.fesdiagramAcqTime=to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')"; 
					List<?> list = this.findCallSql(sql);
					result_json.append("{ \"Success\":true,");
					if(list.size()>0){
						Object[] obj=(Object[]) list.get(0);
						
						String waterCut="";
						String pumpBoreDiameter="";
						String pumpSettingDepth="";
						String producingFluidLevel="";
						String productionData=obj[obj.length-1].toString();
						type = new TypeToken<RPCCalculateRequestData>() {}.getType();
						RPCCalculateRequestData rpcProductionData=gson.fromJson(productionData, type);
						if(rpcProductionData!=null && rpcProductionData.getProduction()!=null){
							if(configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("stere")){
								waterCut=rpcProductionData.getProduction().getWaterCut()+"";
							}else{
								waterCut=rpcProductionData.getProduction().getWeightWaterCut()+"";
							}
							pumpSettingDepth=rpcProductionData.getProduction().getPumpSettingDepth()+"";
							producingFluidLevel=rpcProductionData.getProduction().getProducingfluidLevel()+"";
						}
						if(rpcProductionData!=null && rpcProductionData.getPump()!=null){
							pumpBoreDiameter=rpcProductionData.getPump().getPumpBoreDiameter()*1000+"";
						}
						
						result_json.append("\"ResultName\":\""+obj[0]+"\",");
						
						result_json.append("\"WattDegreeBalance\":\""+obj[1]+"\",");
						result_json.append("\"IDegreeBalance\":\""+obj[2]+"\",");
						result_json.append("\"DeltaRadius\":\""+obj[3]+"\",");
						
						result_json.append("\"LiquidProduction\":\""+obj[4]+"\",");
						result_json.append("\"OilProduction\":\""+obj[5]+"\",");
						result_json.append("\"WaterProduction\":\""+obj[6]+"\",");
						result_json.append("\"waterCut\":\""+waterCut+"\",");
						
						result_json.append("\"AvailablePlungerstrokeProd\":\""+obj[7]+"\",");
						result_json.append("\"PumpClearanceLeakProd\":\""+obj[8]+"\",");
						result_json.append("\"TvleakProduction\":\""+obj[9]+"\",");
						result_json.append("\"SvleakProduction\":\""+obj[10]+"\",");
						result_json.append("\"GasInfluenceProd\":\""+obj[11]+"\",");
						
						result_json.append("\"TheoreticalProduction\":\""+obj[12]+"\",");
						result_json.append("\"PlungerStroke\":\""+obj[13]+"\",");
						result_json.append("\"AvailablePlungerStroke\":\""+obj[14]+"\",");
						
						result_json.append("\"PumpBoreDiameter\":\""+pumpBoreDiameter+"\",");
						result_json.append("\"PumpSettingDepth\":\""+pumpSettingDepth+"\",");
						result_json.append("\"ProducingFluidLevel\":\""+producingFluidLevel+"\",");
						result_json.append("\"Submergence\":\""+obj[15]+"\",");
						
						result_json.append("\"Stroke\":\""+obj[16]+"\",");
						result_json.append("\"SPM\":\""+obj[17]+"\",");
						result_json.append("\"Fmax\":\""+obj[18]+"\",");
						result_json.append("\"Fmin\":\""+obj[19]+"\",");
						result_json.append("\"DeltaF\":\""+obj[20]+"\",");
						result_json.append("\"DeltaLoadLine\":\""+obj[21]+"\",");
						result_json.append("\"Area\":\""+obj[22]+"\",");
						
						result_json.append("\"AverageWatt\":\""+obj[23]+"\",");
						result_json.append("\"PolishrodPower\":\""+obj[24]+"\",");
						result_json.append("\"WaterPower\":\""+obj[25]+"\",");
						result_json.append("\"SurfaceSystemEfficiency\":\""+obj[26]+"\",");
						result_json.append("\"WelldownSystemEfficiency\":\""+obj[27]+"\",");
						result_json.append("\"SystemEfficiency\":\""+obj[28]+"\",");
						result_json.append("\"EnergyPer100mLift\":\""+obj[29]+"\",");
						result_json.append("\"PumpEff1\":\""+obj[30]+"\",");
						result_json.append("\"PumpEff2\":\""+obj[31]+"\",");
						result_json.append("\"PumpEff3\":\""+obj[32]+"\",");
						result_json.append("\"PumpEff4\":\""+obj[33]+"\",");
						result_json.append("\"PumpEff\":\""+obj[34]+"\",");
						result_json.append("\"RodFlexLength\":\""+obj[35]+"\",");
						result_json.append("\"TubingFlexLength\":\""+obj[36]+"\",");
						result_json.append("\"InertiaLength\":\""+obj[37]+"\",");
						
						result_json.append("\"PumpIntakeP\":\""+obj[38]+"\",");
						result_json.append("\"PumpIntakeT\":\""+obj[39]+"\",");
						result_json.append("\"PumpIntakeGOL\":\""+obj[40]+"\",");
						result_json.append("\"PumpIntakeVisl\":\""+obj[41]+"\",");
						result_json.append("\"PumpIntakeBo\":\""+obj[42]+"\",");
						
						result_json.append("\"PumpOutletP\":\""+obj[43]+"\",");
						result_json.append("\"PumpOutletT\":\""+obj[44]+"\",");
						result_json.append("\"PumpOutletGOL\":\""+obj[45]+"\",");
						result_json.append("\"PumpOutletVisl\":\""+obj[46]+"\",");
						result_json.append("\"PumpOutletBo\":\""+obj[47]+"\"");
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
		String json="{\"Success\":false,\"Date\":\"\",\"TotalRoot\":[]}";
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
		
		try{
			if(liftingType==1 && type==1){
				json=this.getTotalFESDiagramResultStatData(wells.toString(),date);
			}else if(type==2){
				json=this.getTotalCommStatusStatData(wells.toString(),liftingType,date);
			}else if(type==3){
				json=this.getTotalRunStatusStatData(wells.toString(),liftingType,date);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return json;
	}
	
	public String getTotalFESDiagramResultStatData(String wells,String date) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String [] wellList=null;
		if(StringManagerUtils.isNotNull(wells)){
			wellList=wells.split(",");
		}
		result_json.append("{ \"Success\":true,\"Date\":\""+date+"\",");
		
		String sql="select decode(t2.resultcode,null,'无数据',t3.resultname) as resultname,t2.resultcode,count(1) "
				+ " from tbl_rpcdevice t "
				+ " left outer join tbl_rpcdailycalculationdata t2 on t2.wellid=t.id "
				+ " left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode "
				+ " where t2.caldate=to_date('"+date+"','yyyy-mm-dd') ";
		if(wellList!=null){
			sql+=" and t.wellname in ( "+StringManagerUtils.joinStringArr2(wellList, ",")+" )";
		}
		sql+= "group by t3.resultname,t2.resultcode order by t2.resultcode";
		
		List<?> list = this.findCallSql(sql);
		result_json.append("\"TotalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"Item\":\""+obj[0]+"\",");
			result_json.append("\"Count\":"+obj[2]+"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
	
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getTotalCommStatusStatData(String wells,int deviceType,String date) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		
		String [] wellList=null;
		if(StringManagerUtils.isNotNull(wells)){
			wellList=wells.split(",");
		}
		
		result_json.append("{ \"Success\":true,\"Date\":\""+date+"\",");
		int online=0,goOnline=0,offline=0;

		String tableName="tbl_rpcdailycalculationdata";
		String deviceTableName="tbl_rpcdevice";
		if(deviceType!=1){
			tableName="tbl_pcpdailycalculationdata";
			deviceTableName="tbl_ocodevice";
		}
		
		String sql="select t2.commstatus,count(1) "
				+ " from "+tableName+" t "
				+ " left outer join "+deviceTableName+" t2 on t2.wellid=t.id"
				+ " where t2.caldate=to_date('"+date+"','yyyy-mm-dd') ";
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
	
		
		result_json.append("\"TotalRoot\":[");
		
		result_json.append("{\"Item\":\"在线\",");
		result_json.append("\"Count\":"+online+"},");
		
		result_json.append("{\"Item\":\"上线\",");
		result_json.append("\"Count\":"+goOnline+"},");
		
		result_json.append("{\"Item\":\"离线\",");
		result_json.append("\"Count\":"+offline+"}");
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getTotalRunStatusStatData(String wells,int deviceType,String date) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		String [] wellList=null;
		if(StringManagerUtils.isNotNull(wells)){
			wellList=wells.split(",");
		}
		result_json.append("{ \"Success\":true,\"Date\":\""+date+"\",");
		int run=0,stop=0,offline=0;

		String tableName="tbl_rpcdailycalculationdata";
		String deviceTableName="tbl_rpcdevice";
		if(deviceType!=1){
			tableName="tbl_pcpdailycalculationdata";
			deviceTableName="tbl_ocodevice";
		}
		
		String sql="select decode(t2.commstatus,0,-1,t2.runstatus) as runstatus,count(1) "
				+ " from "+tableName+" t "
				+ " left outer join "+deviceTableName+" t2 on t2.wellid=t.id"
				+ " where t2.caldate=to_date('"+date+"','yyyy-mm-dd') ";
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
		
		result_json.append("\"TotalRoot\":[");
		
		result_json.append("{\"Item\":\"运行\",");
		result_json.append("\"Count\":"+run+"},");
		
		result_json.append("{\"Item\":\"停抽\",");
		result_json.append("\"Count\":"+stop+"},");
		
		result_json.append("{\"Item\":\"离线\",");
		result_json.append("\"Count\":"+offline+"}");
		result_json.append("]}");
		return result_json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	public String getOilWellTotalWellListData(String data,Page pager)throws Exception {
		StringBuffer wells= new StringBuffer();
		String result = "";
		int liftingType=1;
		String date=StringManagerUtils.getCurrentTime();
		int statType=1;
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
		if(liftingType==1){
			result=this.getDeviceTotalOverview(wells.toString(), statType, statValue, date);
		}else{
			result=this.getPCPDeviceTotalOverview(wells.toString(), statType, statValue, date);
		}
		return result;
	}
	
	public String getDeviceTotalOverview(String wells,int statType,String statValue,String date) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
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
			
			if(!jedis.exists("RPCWorkType".getBytes())){
				MemoryDataManagerTask.loadRPCWorkType();
			}
			
			if(!jedis.exists("AlarmInstanceOwnItem".getBytes())){
				MemoryDataManagerTask.loadAlarmInstanceOwnItemById("","update");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String tableName="tbl_rpcdailycalculationdata";
		String deviceTableName="tbl_rpcdevice";
		
		String sql="select t2.id,t.id as wellId,t.wellName,to_char(t2.caldate,'yyyy-mm-dd') as caldate,t2.ExtendedDays,"
				+ "decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
				+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
				+ "decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽')) as runStatusName,"
				+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
				+ "t2.resultcode,decode(t2.resultcode,null,'无数据',t3.resultName) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
				+ "t2.liquidWeightProduction,t2.oilWeightProduction,t2.waterWeightProduction,t2.weightWaterCut,"
				+ "t2.liquidVolumetricProduction,t2.oilVolumetricProduction,t2.waterVolumetricProduction,t2.volumeWaterCut,"
				+ "t2.wattDegreeBalance,t2.iDegreeBalance,t2.deltaRadius,"
				+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
				+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
				+ "t2.systemEfficiency*100 as systemEfficiency,"
				+ "t2.energyper100mlift,t2.todayKWattH,"
				+ "t2.pumpEff*100 as pumpEff";
		sql+= " from "+deviceTableName+" t "
				+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
				+ " left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode "
				+ " where  t2.caldate= to_date('"+date+"','yyyy-mm-dd') ";
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
		result_json.append("{ \"Success\":true,");
		result_json.append("\"TotalCount\":"+totals+",");
		result_json.append("\"TotalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String deviceId=obj[1]+"";
			String commStatusName=obj[5]+"";
			String runStatusName=obj[9]+"";
			String resultcode=obj[13]+"";
			
			
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
					if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
						commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
						runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==4 && alarmInstanceOwnItem.getItemList().get(j).getItemCode().equalsIgnoreCase(resultcode)){
						resultAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}
				}
			}
			
			result_json.append("{\"Id\":"+obj[0]+",");
			result_json.append("\"WellName\":\""+obj[2]+"\",");
			result_json.append("\"Caldate\":\""+obj[3]+"\",");
			result_json.append("\"ExtendedDays\":\""+obj[4]+"\",");
			
			result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
			result_json.append("\"CommTime\":\""+obj[6]+"\",");
			result_json.append("\"CommTimeEfficiency\":\""+obj[7]+"\",");
			result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
			
			result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
			result_json.append("\"RunTime\":\""+obj[10]+"\",");
			result_json.append("\"RunTimeEfficiency\":\""+obj[11]+"\",");
			result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
			result_json.append("\"ResultName\":\""+obj[14]+"\",");
			result_json.append("\"OptimizationSuggestion\":\""+obj[15]+"\",");
			
			result_json.append("\"LiquidWeightProduction\":\""+obj[16]+"\",");
			result_json.append("\"OilWeightProduction\":\""+obj[17]+"\",");
			result_json.append("\"WaterWeightProduction\":\""+obj[18]+"\",");
			result_json.append("\"WeightWaterCut\":\""+obj[19]+"\",");
			
			result_json.append("\"LiquidVolumetricProduction\":\""+obj[20]+"\",");
			result_json.append("\"OilVolumetricProduction\":\""+obj[21]+"\",");
			result_json.append("\"WaterVolumetricProduction\":\""+obj[22]+"\",");
			result_json.append("\"VolumeWaterCut\":\""+obj[23]+"\",");
			
			result_json.append("\"IDegreeBalance\":\""+obj[24]+"\",");
			result_json.append("\"WattDegreeBalance\":\""+obj[25]+"\",");
			result_json.append("\"Deltaradius\":\""+obj[26]+"\",");
			
			result_json.append("\"SurfaceSystemEfficiency\":\""+obj[27]+"\",");
			result_json.append("\"WelldownSystemEfficiency\":\""+obj[28]+"\",");
			result_json.append("\"SystemEfficiency\":\""+obj[29]+"\",");
			result_json.append("\"Energyper100mlift\":\""+obj[30]+"\",");
			result_json.append("\"TodayKWattH\":\""+obj[31]+"\",");
			result_json.append("\"PumpEff\":\""+obj[32]+"\",");

			result_json.append("\"ResultAlarmLevel\":"+resultAlarmLevel+",");
			result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
			result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
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
	
	public String getPCPDeviceTotalOverview(String wells,int statType,String statValue,String date) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
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
		
		String tableName="tbl_pcpdailycalculationdata";
		String deviceTableName="tbl_pcpdevice";
		
		String sql="select t2.id,t.id as wellId,t.wellname,to_char(caldate,'yyyy-mm-dd') as caldate,extendedDays,"
				+ "decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
				+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
				+ "decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽')) as runStatusName,"
				+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
				+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,weightWaterCut,"
				+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,volumeWaterCut,"
				+ "t2.systemEfficiency*100 as systemEfficiency,"
				+ "t2.todayKWattH,"
				+ "t2.pumpEff*100 as pumpEff";
		sql+= " from "+deviceTableName+" t "
				+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
				+ " where  t2.caldate= to_date('"+date+"','yyyy-mm-dd') ";
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
		result_json.append("{ \"Success\":true,");
		result_json.append("\"TotalCount\":"+totals+",");
		result_json.append("\"TotalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String deviceId=obj[1]+"";
			String commStatusName=obj[5]+"";
			String runStatusName=obj[9]+"";
			
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
					if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
						commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
						runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}
				}
			}
			
			result_json.append("{\"Id\":"+obj[0]+",");
			result_json.append("\"WellName\":\""+obj[2]+"\",");
			result_json.append("\"Caldate\":\""+obj[3]+"\",");
			result_json.append("\"ExtendedDays\":\""+obj[4]+"\",");
			
			result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
			result_json.append("\"CommTime\":\""+obj[6]+"\",");
			result_json.append("\"CommTimeEfficiency\":\""+obj[7]+"\",");
			result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
			
			result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
			result_json.append("\"RunTime\":\""+obj[10]+"\",");
			result_json.append("\"RunTimeEfficiency\":\""+obj[11]+"\",");
			result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
			
			result_json.append("\"LiquidWeightProduction\":\""+obj[13]+"\",");
			result_json.append("\"OilWeightProduction\":\""+obj[14]+"\",");
			result_json.append("\"WaterWeightProduction\":\""+obj[15]+"\",");
			result_json.append("\"WeightWaterCut\":\""+obj[16]+"\",");
			
			result_json.append("\"LiquidVolumetricProduction\":\""+obj[17]+"\",");
			result_json.append("\"OilVolumetricProduction\":\""+obj[18]+"\",");
			result_json.append("\"WaterVolumetricProduction\":\""+obj[19]+"\",");
			result_json.append("\"VolumeWaterCut\":\""+obj[20]+"\",");
			
			result_json.append("\"SystemEfficiency\":\""+obj[21]+"\",");
			result_json.append("\"TodayKWattH\":\""+obj[22]+"\",");
			result_json.append("\"PumpEff\":\""+obj[23]+"\",");

			result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
			result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
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
	
	public String getOilWellTotalHistoryData(String data,Page pager)throws Exception {
		String result = "";
		ConfigFile configFile=Config.getInstance().configFile;
		int liftingType=1;
		String startDate=StringManagerUtils.getCurrentTime();
		String endDate=StringManagerUtils.getCurrentTime();
		String wellName="";
		int statType=1;
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
				statType=jsonObject.getInt("StatType");
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				statValue=jsonObject.getString("StatValue");
			}catch(Exception e){
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(liftingType==1){
			result=this.getDeviceTotalHistory(wellName.toString(), statType, statValue, startDate,endDate);
		}else{
			result=this.getPCPDeviceTotalHistory(wellName.toString(), statType, statValue, startDate,endDate);
		}
		return result;
	}
	
	public String getDeviceTotalHistory(String wellName,int statType,String statValue,String startDate,String endDate) throws IOException, SQLException{
		StringBuffer result_json = new StringBuffer();
		Jedis jedis=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("RPCDeviceInfo".getBytes())){
				MemoryDataManagerTask.loadRPCDeviceInfo(null,0,"update");
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
		
		String tableName="tbl_rpcdailycalculationdata";
		String deviceTableName="tbl_rpcdevice";
		
		String sql="select t2.id,t.id as wellId,t.wellName,to_char(t2.caldate,'yyyy-mm-dd') as caldate,t2.ExtendedDays,"
				+ "decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
				+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
				+ "decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽')) as runStatusName,"
				+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
				+ "t2.resultcode,decode(t2.resultcode,null,'无数据',t3.resultName) as resultName,t3.optimizationSuggestion as optimizationSuggestion,"
				+ "t2.liquidWeightProduction,t2.oilWeightProduction,t2.waterWeightProduction,t2.weightWaterCut,"
				+ "t2.liquidVolumetricProduction,t2.oilVolumetricProduction,t2.waterVolumetricProduction,t2.volumeWaterCut,"
				+ "t2.wattDegreeBalance,t2.iDegreeBalance,t2.deltaRadius,"
				+ "t2.surfaceSystemEfficiency*100 as surfaceSystemEfficiency,"
				+ "t2.welldownSystemEfficiency*100 as welldownSystemEfficiency,"
				+ "t2.systemEfficiency*100 as systemEfficiency,"
				+ "t2.energyper100mlift,t2.todayKWattH,"
				+ "t2.pumpEff*100 as pumpEff";
		sql+= " from "+deviceTableName+" t "
				+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
				+ " left outer join tbl_rpc_worktype t3 on t2.resultcode=t3.resultcode "
				+ " where  t2.caldate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+= "and t.wellname='"+wellName+"'";
		}
		if(statType==1 && StringManagerUtils.isNotNull(statValue)){
			sql+=" and decode(t2.resultcode,null,'无数据',t3.resultName)='"+statValue+"'";
		}else if(statType==2 && StringManagerUtils.isNotNull(statValue)){
			sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+statValue+"'";
		}else if(statType==3 && StringManagerUtils.isNotNull(statValue)){
			sql+=" and decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽'))='"+statValue+"'";
		}
		sql+=" order by t2.caldate,t.sortnum,t.wellname";
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"Success\":true,");
		result_json.append("\"TotalCount\":"+totals+",");
		result_json.append("\"TotalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String deviceId=obj[1]+"";
			String commStatusName=obj[5]+"";
			String runStatusName=obj[9]+"";
			String resultcode=obj[13]+"";
			
			
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
					if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
						commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
						runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==4 && alarmInstanceOwnItem.getItemList().get(j).getItemCode().equalsIgnoreCase(resultcode)){
						resultAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}
				}
			}
			
			result_json.append("{\"Id\":"+obj[0]+",");
			result_json.append("\"WellName\":\""+obj[2]+"\",");
			result_json.append("\"Caldate\":\""+obj[3]+"\",");
			result_json.append("\"ExtendedDays\":\""+obj[4]+"\",");
			
			result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
			result_json.append("\"CommTime\":\""+obj[6]+"\",");
			result_json.append("\"CommTimeEfficiency\":\""+obj[7]+"\",");
			result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
			
			result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
			result_json.append("\"RunTime\":\""+obj[10]+"\",");
			result_json.append("\"RunTimeEfficiency\":\""+obj[11]+"\",");
			result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
			result_json.append("\"ResultName\":\""+obj[14]+"\",");
			result_json.append("\"OptimizationSuggestion\":\""+obj[15]+"\",");
			
			result_json.append("\"LiquidWeightProduction\":\""+obj[16]+"\",");
			result_json.append("\"OilWeightProduction\":\""+obj[17]+"\",");
			result_json.append("\"WaterWeightProduction\":\""+obj[18]+"\",");
			result_json.append("\"WeightWaterCut\":\""+obj[19]+"\",");
			
			result_json.append("\"LiquidVolumetricProduction\":\""+obj[20]+"\",");
			result_json.append("\"OilVolumetricProduction\":\""+obj[21]+"\",");
			result_json.append("\"WaterVolumetricProduction\":\""+obj[22]+"\",");
			result_json.append("\"VolumeWaterCut\":\""+obj[23]+"\",");
			
			result_json.append("\"IDegreeBalance\":\""+obj[24]+"\",");
			result_json.append("\"WattDegreeBalance\":\""+obj[25]+"\",");
			result_json.append("\"Deltaradius\":\""+obj[26]+"\",");
			
			result_json.append("\"SurfaceSystemEfficiency\":\""+obj[27]+"\",");
			result_json.append("\"WelldownSystemEfficiency\":\""+obj[28]+"\",");
			result_json.append("\"SystemEfficiency\":\""+obj[29]+"\",");
			result_json.append("\"Energyper100mlift\":\""+obj[30]+"\",");
			result_json.append("\"TodayKWattH\":\""+obj[31]+"\",");
			result_json.append("\"PumpEff\":\""+obj[32]+"\",");

			result_json.append("\"ResultAlarmLevel\":"+resultAlarmLevel+",");
			result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
			result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
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
	
	public String getPCPDeviceTotalHistory(String wellName,int statType,String statValue,String startDate,String endDate) throws IOException, SQLException{
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
		
		String tableName="tbl_pcpdailycalculationdata";
		String deviceTableName="tbl_pcpdevice";
		
		String sql="select t2.id,t.id as wellId,t.wellname,to_char(caldate,'yyyy-mm-dd') as caldate,extendedDays,"
				+ "decode(t2.commstatus,1,'在线',2,'上线','离线') as commStatusName,"
				+ "t2.commtime,t2.commtimeefficiency,t2.commrange,"
				+ "decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽')) as runStatusName,"
				+ "t2.runtime,t2.runtimeefficiency,t2.runrange,"
				+ "liquidWeightProduction,oilWeightProduction,waterWeightProduction,weightWaterCut,"
				+ "liquidVolumetricProduction,oilVolumetricProduction,waterVolumetricProduction,volumeWaterCut,"
				+ "t2.systemEfficiency*100 as systemEfficiency,"
				+ "t2.todayKWattH,"
				+ "t2.pumpEff*100 as pumpEff";
		sql+= " from "+deviceTableName+" t "
				+ " left outer join "+tableName+" t2 on t2.wellid=t.id"
				+ " where  t2.caldate between to_date('"+startDate+"','yyyy-mm-dd') and to_date('"+endDate+"','yyyy-mm-dd')+1";
		if(StringManagerUtils.isNotNull(wellName)){
			sql+= "and t.wellname='"+wellName+"'";
		}
		if(statType==2 && StringManagerUtils.isNotNull(statValue)){
			sql+=" and decode(t2.commstatus,1,'在线',2,'上线','离线')='"+statValue+"'";
		}else if(statType==3 && StringManagerUtils.isNotNull(statValue)){
			sql+=" and decode(t2.commstatus,0,'离线',decode(t2.runstatus,1,'运行','停抽'))='"+statValue+"'";
		}
		sql+=" order by t2.caldate,t.sortnum,t.wellname";
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"Success\":true,");
		result_json.append("\"TotalCount\":"+totals+",");
		result_json.append("\"TotalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			String deviceId=obj[1]+"";
			String commStatusName=obj[5]+"";
			String runStatusName=obj[9]+"";
			
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
					if(alarmInstanceOwnItem.getItemList().get(j).getType()==3 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(commStatusName)){
						commAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}else if(alarmInstanceOwnItem.getItemList().get(j).getType()==6 && alarmInstanceOwnItem.getItemList().get(j).getItemName().equalsIgnoreCase(runStatusName)){
						runAlarmLevel=alarmInstanceOwnItem.getItemList().get(j).getAlarmLevel();
					}
				}
			}
			
			result_json.append("{\"Id\":"+obj[0]+",");
			result_json.append("\"WellName\":\""+obj[2]+"\",");
			result_json.append("\"Caldate\":\""+obj[3]+"\",");
			result_json.append("\"ExtendedDays\":\""+obj[4]+"\",");
			
			result_json.append("\"CommStatusName\":\""+commStatusName+"\",");
			result_json.append("\"CommTime\":\""+obj[6]+"\",");
			result_json.append("\"CommTimeEfficiency\":\""+obj[7]+"\",");
			result_json.append("\"CommRange\":\""+StringManagerUtils.CLOBObjectToString(obj[8])+"\",");
			
			result_json.append("\"RunStatusName\":\""+runStatusName+"\",");
			result_json.append("\"RunTime\":\""+obj[10]+"\",");
			result_json.append("\"RunTimeEfficiency\":\""+obj[11]+"\",");
			result_json.append("\"RunRange\":\""+StringManagerUtils.CLOBObjectToString(obj[12])+"\",");
			
			result_json.append("\"LiquidWeightProduction\":\""+obj[13]+"\",");
			result_json.append("\"OilWeightProduction\":\""+obj[14]+"\",");
			result_json.append("\"WaterWeightProduction\":\""+obj[15]+"\",");
			result_json.append("\"WeightWaterCut\":\""+obj[16]+"\",");
			
			result_json.append("\"LiquidVolumetricProduction\":\""+obj[17]+"\",");
			result_json.append("\"OilVolumetricProduction\":\""+obj[18]+"\",");
			result_json.append("\"WaterVolumetricProduction\":\""+obj[19]+"\",");
			result_json.append("\"VolumeWaterCut\":\""+obj[20]+"\",");
			
			result_json.append("\"SystemEfficiency\":\""+obj[21]+"\",");
			result_json.append("\"TodayKWattH\":\""+obj[22]+"\",");
			result_json.append("\"PumpEff\":\""+obj[23]+"\",");

			result_json.append("\"CommAlarmLevel\":"+commAlarmLevel+",");
			result_json.append("\"RunAlarmLevel\":"+runAlarmLevel+"},");
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
}
