package com.cosog.thread.calculate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cosog.model.DataMapping;
import com.cosog.model.KeyValue;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.task.SyncHCDataTast;
import com.cosog.utils.HeiChaoCounterUtils;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

public class HCHistoryDataSyncThread implements Runnable{
	private int deviceId;
	private String acqTime;
	public HCHistoryDataSyncThread(int deviceId, String acqTime) {
		super();
		this.deviceId = deviceId;
		this.acqTime = acqTime;
	}
	public void run(){
		try{
			int queryDeviceId=deviceId;
			if(deviceId>10000){
				queryDeviceId=deviceId-10000;
			}
			String deviceTable="tbl_rpcdevice";
			String historyTable="tbl_rpcacqdata_hist";
			String realtimeTable="tbl_rpcacqdata_latest";
			if(deviceId>10000){
				deviceTable="tbl_pcpdevice";
				historyTable="tbl_pcpacqdata_hist";
				realtimeTable="tbl_pcpacqdata_latest";
			}
			int days=60;
			
			Gson gson = new Gson();
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
			Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
			Map<String,String> hcMappingColumnMap=SyncHCDataTast.getHCMappingData();
			
			String acqItemsSql="select t7.name,t7.mappingcolumn from tbl_datamapping t7 where t7.name in("
					+ " select t6.itemname"
					+ " from "+deviceTable+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3,tbl_acq_group2unit_conf t4,tbl_acq_group_conf t5,tbl_acq_item2group_conf t6"
					+ " where t.instancecode=t2.code and t2.unitid=t3.id and t3.id=t4.unitid and t4.groupid=t5.id and t5.id=t6.groupid"
					+ " and t.id="+queryDeviceId+" "
					+ " and t5.type=0"
					+ " )"
					+ " order by t7.id";
			List<Object[]> acqItemsList=OracleJdbcUtis.query(acqItemsSql,"oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@127.0.0.1:1521/orclpdb", "ap_hc", "Ap201#");
			List<String> queryTableColumnList=new ArrayList<>();
			for(Object[] obj:acqItemsList){
				if(!StringManagerUtils.existOrNot(queryTableColumnList, obj[1]+"",false)){
					queryTableColumnList.add(obj[1]+"");
				}
			}
			
			if(queryTableColumnList.size()>0){
				String sql="select t.id,t.wellname,"
						+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,"
						+ "t2.commstatus,t2.commtime,t2.commtimeefficiency,t2.commrange,"
						+ "t2.runstatus,t2.runtimeefficiency,t2.runtime,t2.runrange";
				for(String columm:queryTableColumnList){
					sql+=",t2."+columm;
				}
				sql+=" from "+deviceTable+" t, "+historyTable+" t2 "
					+ " where t.id=t2.wellid ";
				int limit=100;
				if(StringManagerUtils.isNotNull(acqTime)){
					sql+= " and t2.acqtime>to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')";
				}else{
					sql+= " and t2.acqtime>( select max(t3.acqtime)-"+days+" from "+realtimeTable+" t3 where t3.wellid="+queryDeviceId+" )";
					limit=100;
				}
				sql+=" and t2.wellid="+queryDeviceId;
				sql+= " order by t2.acqtime";
				
//				sql="select v2.* from( select v1.*,rownum as rn from ("+sql+") v1 ) v2 where v2.rn<="+limit+"";
				
				
//				if(deviceId>10000){
//					System.out.println("查询sql:"+sql);
//				}
				long t1=System.nanoTime();
				List<Object[]> queryDataList=OracleJdbcUtis.query(sql,"oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@127.0.0.1:1521/orclpdb", "ap_hc", "Ap201#");
				long t2=System.nanoTime();
				System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":设备"+deviceId+"历史数据查询耗时:"+StringManagerUtils.getTimeDiff(t1, t2)+",记录数:"+queryDataList.size());
				
				String insertSql="";
				String insertColumns="";
				String insertValues="";
				List<String> clobDataList=null;
				for(Object[] obj:queryDataList){
					try{
						List<KeyValue> dataList=new ArrayList<>();
						int saveDeviceId=deviceId;
						String deviceName=obj[1]+"";
						String acqtime=obj[2]+"";
						int commStatus=StringManagerUtils.stringToInteger(obj[3]+"");
						String commTime=obj[4]+"";
						String commTimeEfficiency=obj[5]+"";
						String commRange=(obj[6]+"").replaceAll("null", "");
						int runStatus=StringManagerUtils.stringToInteger(obj[7]+"");
						String runTime=obj[8]+"";
						String runTimeEfficiency=obj[9]+"";
						String runRange=(obj[10]+"").replaceAll("null", "");
						String acqData="";
						for(int i=0;i<queryTableColumnList.size();i++){
							String column_hc=queryTableColumnList.get(i);
							if(hcMappingColumnMap.containsKey(column_hc)){
								String value=obj[i+11]+"";
								String name=hcMappingColumnMap.get(column_hc);
								if(loadProtocolMappingColumnByTitleMap.containsKey(name) && StringManagerUtils.isNotNull(value)){
									String mappingColumn=loadProtocolMappingColumnByTitleMap.get(name).getMappingColumn();
									KeyValue keyValue=new KeyValue(mappingColumn,value);
									dataList.add(keyValue);
								}
							}
						}
						if(dataList.size()>0){
							acqData=gson.toJson(dataList);
						}
						insertSql="";
						insertColumns="deviceId,acqtime,commStatus,commTime,commTimeEfficiency,runStatus,runTime,runTimeEfficiency,commRange,runRange,acqData";
						insertValues=saveDeviceId+",to_date('"+acqtime+"','yyyy-mm-dd hh24:mi:ss'),"+commStatus+","+commTime+","+commTimeEfficiency+","+runStatus+","+runTime+","+runTimeEfficiency+",?,?,?";
						
						insertSql= "insert into tbl_acqdata_hist ("+insertColumns+") values ("+insertValues+")";
						
						clobDataList=new ArrayList<>();
						clobDataList.add(commRange);
						clobDataList.add(runRange);
						clobDataList.add(acqData);
						OracleJdbcUtis.executeSqlUpdateClob(insertSql, clobDataList);
						HeiChaoCounterUtils.incr();//计数器加一
					}catch(Exception e){
						e.printStackTrace();
						continue;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		HeiChaoCounterUtils.countDown();
		System.out.println("设备id:"+deviceId+"执行完毕！");
	}
}
