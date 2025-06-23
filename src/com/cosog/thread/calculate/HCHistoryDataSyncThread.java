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
	private String instanceCode;
	private int type;
	public HCHistoryDataSyncThread(int deviceId, String acqTime,String instanceCode,int type) {
		super();
		this.deviceId = deviceId;
		this.acqTime = acqTime;
		this.instanceCode = instanceCode;
		this.type = type;
	}
	public void run(){
		try{
			if(type==0){//采集数据
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
					
//					sql="select v2.* from( select v1.*,rownum as rn from ("+sql+") v1 ) v2 where v2.rn<="+limit+"";
					
					
//					if(deviceId>10000){
//						System.out.println("查询sql:"+sql);
//					}
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
			}else if(type==1){//日汇总数据
				//采集数据
				int queryDeviceId=deviceId;
				int days=60;
				
				Gson gson = new Gson();
				Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
				Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
				Map<String,String> hcMappingColumnMap=SyncHCDataTast.getHCMappingData();
				
				String sql="select t.id,t.wellname,"
						+ "to_char(t2.caldate,'yyyy-mm-dd hh24:mi:ss') as caldate,"
						+ "t2.commstatus,t2.commtime,t2.commtimeefficiency,t2.commrange,"
						+ "t2.runstatus,t2.runtimeefficiency,t2.runtime,t2.runrange,"
						+ "headerlabelinfo ";
				if("instance1".equalsIgnoreCase(instanceCode) || "instance145".equalsIgnoreCase(instanceCode) || "instance125".equalsIgnoreCase(instanceCode)){
					sql+=",t2.spm,t2.casingpressure,t2.bottomholepressure,t2.producingfluidlevel,t2.gasvolumetricproduction,t2.totalgasvolumetricproduction,t2.watervolumetricproduction,t2.totalwatervolumetricproduction";
				}else if("instance5".equalsIgnoreCase(instanceCode)){
					sql+=",t2.theoreticalproduction,"//12
						+ "t2.liquidvolumetricproduction,t2.oilvolumetricproduction,t2.watervolumetricproduction,"//13~15
						+ "t2.liquidweightproduction,t2.oilweightproduction,t2.waterweightproduction,"//16~18
						+ "t2.volumewatercut,t2.weightwatercut,"//19、20
						+ "t2.systemefficiency,t2.energyper100mlift,"//21、22
						+ "t2.pumpeff,t2.pumpeff1,t2.pumpeff2,"//23~25
						+ "t2.resultstatus,t2.extendeddays,"//26、27
						+ "t2.tubingpressure,t2.casingpressure,"//28、29
						+ "t2.producingfluidlevel,t2.pumpsettingdepth,t2.submergence,"//30~32
						+ "t2.rpm";//33
				}
				sql+= " from tbl_rpcdevice t,TBL_RPCDAILYCALCULATIONDATA t2 "
						+ "where t.id=t2.wellid ";
				if(StringManagerUtils.isNotNull(acqTime)){
					sql+= " and t2.caldate>to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')";
				}else{
//					sql+= " and t2.caldate>( select max(t3.caldate)-"+days+" from TBL_RPCDAILYCALCULATIONDATA t3 where t3.wellid="+queryDeviceId+" )";
				}
				sql+=" and t.id="+queryDeviceId;
				sql+= " order by t2.caldate";
				
				
				long t1=System.nanoTime();
				List<Object[]> queryDataList=OracleJdbcUtis.query(sql,"oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@127.0.0.1:1521/orclpdb", "ap_hc", "Ap201#");
				long t2=System.nanoTime();
				System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":设备"+deviceId+"日汇总数据查询耗时:"+StringManagerUtils.getTimeDiff(t1, t2)+",记录数:"+queryDataList.size());
				
				String insertSql="";
				String insertColumns="";
				String insertValues="";
				List<String> clobDataList=null;
				for(Object[] obj:queryDataList){
					try{
						List<KeyValue> dataList=new ArrayList<>();
						int saveDeviceId=deviceId;
						String deviceName=obj[1]+"";
						String caldate=obj[2]+"";
						int commStatus=StringManagerUtils.stringToInteger(obj[3]+"");
						String commTime=obj[4]+"";
						String commTimeEfficiency=obj[5]+"";
						String commRange=(obj[6]+"").replaceAll("null", "");
						int runStatus=StringManagerUtils.stringToInteger(obj[7]+"");
						String runTime=obj[8]+"";
						String runTimeEfficiency=obj[9]+"";
						String runRange=(obj[10]+"").replaceAll("null", "");
						String headerlabelinfo=(obj[11]+"").replaceAll("null", "");
						
						if("instance1".equalsIgnoreCase(instanceCode) || "instance145".equalsIgnoreCase(instanceCode) || "instance125".equalsIgnoreCase(instanceCode)){
							String spm=obj[12]+"";
							String casingpressure=obj[13]+"";
							String bottomholepressure=obj[14]+"";
							String producingfluidlevel=obj[15]+"";
							String gasvolumetricproduction=obj[16]+"";
							String totalgasvolumetricproduction=obj[17]+"";
							String watervolumetricproduction=obj[18]+"";
							String totalwatervolumetricproduction=obj[19]+"";
							
							spm=spm+";"+spm+";"+spm+";"+spm+";"+spm+"; ";
							casingpressure=casingpressure+";"+casingpressure+";"+casingpressure+";"+casingpressure+";"+casingpressure+"; ";
							bottomholepressure=bottomholepressure+";"+bottomholepressure+";"+bottomholepressure+";"+bottomholepressure+";"+bottomholepressure+"; ";
							producingfluidlevel=producingfluidlevel+";"+producingfluidlevel+";"+producingfluidlevel+";"+producingfluidlevel+";"+producingfluidlevel+"; ";
							gasvolumetricproduction=totalgasvolumetricproduction+";"+totalgasvolumetricproduction+";"+totalgasvolumetricproduction+";"+totalgasvolumetricproduction+";"+totalgasvolumetricproduction+";"+gasvolumetricproduction;
							watervolumetricproduction=totalwatervolumetricproduction+";"+totalwatervolumetricproduction+";"+totalwatervolumetricproduction+";"+totalwatervolumetricproduction+";"+totalwatervolumetricproduction+";"+watervolumetricproduction;
							
							DataMapping dataMapping= loadProtocolMappingColumnByTitleMap.get("冲次");
							if(dataMapping!=null){
								dataList.add(new KeyValue(dataMapping.getMappingColumn(),spm));
							}
							
							dataMapping= loadProtocolMappingColumnByTitleMap.get("井口套压");
							if(dataMapping!=null){
								dataList.add(new KeyValue(dataMapping.getMappingColumn(),casingpressure));
							}
							
							dataMapping= loadProtocolMappingColumnByTitleMap.get("修正后井底流压");
							if(dataMapping!=null){
								dataList.add(new KeyValue(dataMapping.getMappingColumn(),bottomholepressure));
							}
							
							dataMapping= loadProtocolMappingColumnByTitleMap.get("动液面");
							if(dataMapping!=null){
								dataList.add(new KeyValue(dataMapping.getMappingColumn(),producingfluidlevel));
							}
							
							dataMapping= loadProtocolMappingColumnByTitleMap.get("产气量累计");
							if(dataMapping!=null){
								dataList.add(new KeyValue(dataMapping.getMappingColumn(),gasvolumetricproduction));
							}
							
							dataMapping= loadProtocolMappingColumnByTitleMap.get("产水量累计");
							if(dataMapping!=null){
								dataList.add(new KeyValue(dataMapping.getMappingColumn(),watervolumetricproduction));
							}
						}
						insertSql="";
						insertColumns="deviceId,caldate,commStatus,commTime,commTimeEfficiency,runStatus,runTime,runTimeEfficiency,headerlabelinfo,commRange,runRange,calData";
						insertValues=saveDeviceId+",to_date('"+caldate+"','yyyy-mm-dd hh24:mi:ss'),"+commStatus+","+commTime+","+commTimeEfficiency+","+runStatus+","+runTime+","+runTimeEfficiency+",'"+headerlabelinfo+"',?,?,?";
						
						insertSql= "insert into TBL_DAILYCALCULATIONDATA ("+insertColumns+") values ("+insertValues+")";
						
						clobDataList=new ArrayList<>();
						clobDataList.add(commRange);
						clobDataList.add(runRange);
						clobDataList.add(gson.toJson(dataList));
						OracleJdbcUtis.executeSqlUpdateClob(insertSql, clobDataList);
						
						
						if("instance5".equalsIgnoreCase(instanceCode)){
							String theoreticalproduction=obj[12]+"";
							
							String liquidvolumetricproduction=obj[13]+"";
							String oilvolumetricproduction=obj[14]+"";
							String watervolumetricproduction=obj[15]+"";
							
							String liquidweightproduction=obj[16]+"";
							String oilweightproduction=obj[17]+"";
							String waterweightproduction=obj[18]+"";
							
							String volumewatercut=obj[19]+"";
							String weightwatercut=obj[20]+"";
							
							String systemefficiency=obj[21]+"";
							String energyper100mlift=obj[22]+"";
							
							String pumpeff=obj[23]+"";
							String pumpeff1=obj[24]+"";
							String pumpeff2=obj[25]+"";
							
							String resultstatus=obj[26]+"";
							String extendeddays=obj[27]+"";
							
							String tubingpressure=obj[28]+"";
							String casingpressure=obj[29]+"";
							
							String producingfluidlevel=obj[30]+"";
							String pumpsettingdepth=obj[31]+"";
							String submergence=obj[32]+"";
							
							String rpm=obj[33]+"";
							
							insertColumns="deviceId,caldate,"
									+ "commStatus,commTime,commTimeEfficiency,"
									+ "runStatus,runTime,runTimeEfficiency,"
									+ "headerlabelinfo,"
									+ "commRange,runRange";
							insertValues=saveDeviceId+",to_date('"+caldate+"','yyyy-mm-dd hh24:mi:ss'),"
									+commStatus+","+commTime+","+commTimeEfficiency+","
									+runStatus+","+runTime+","+runTimeEfficiency+","
									+ "'"+headerlabelinfo+"',"
									+ "?,?";
							
							if(StringManagerUtils.stringToInteger(resultstatus)==1 && StringManagerUtils.isNotNull(rpm)){
								insertColumns+=",theoreticalproduction,"//12
										+ "liquidvolumetricproduction,oilvolumetricproduction,watervolumetricproduction,"//13~15
										+ "liquidweightproduction,oilweightproduction,waterweightproduction,"//16~18
										+ "volumewatercut,weightwatercut,"//19、20
										+ "systemefficiency,energyper100mlift,"//21、22
										+ "pumpeff,pumpeff1,pumpeff2,"//23~25
										+ "resultstatus,extendeddays,"//26、27
										+ "tubingpressure,casingpressure,"//28、29
										+ "producingfluidlevel,pumpsettingdepth,submergence,"//30~32
										+ "rpm";//33
								insertValues+=","+theoreticalproduction+","
										+liquidvolumetricproduction+","+oilvolumetricproduction+","+watervolumetricproduction+","
										+liquidweightproduction+","+oilweightproduction+","+waterweightproduction+","
										+volumewatercut+","+weightwatercut+","
										+systemefficiency+","+energyper100mlift+","
										+pumpeff+","+pumpeff1+","+pumpeff2+","
										+resultstatus+","+extendeddays+","
										+tubingpressure+","+casingpressure+","
										+producingfluidlevel+","+pumpsettingdepth+","+submergence+","
										+rpm;
							}
							
							insertSql= "insert into tbl_pcpdailycalculationdata ("+insertColumns+") values ("+insertValues+")";
							
							clobDataList=new ArrayList<>();
							clobDataList.add(commRange);
							clobDataList.add(runRange);
							OracleJdbcUtis.executeSqlUpdateClob(insertSql, clobDataList);
							
						} 
						
						HeiChaoCounterUtils.incr();//计数器加一
					}catch(Exception e){
						e.printStackTrace();
						continue;
					}
				}
				
				
				if(instanceCode.equalsIgnoreCase("instance5")){//螺杆泵
//					String sql=
				}
			}else if(type==2){//定时汇总数据
				//采集数据
				int queryDeviceId=deviceId;
				int days=60;
				
				Gson gson = new Gson();
				Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
				Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
				Map<String,String> hcMappingColumnMap=SyncHCDataTast.getHCMappingData();
				
				String sql="select t.id,t.wellname,"
						+ "to_char(t2.caltime,'yyyy-mm-dd hh24:mi:ss') as caltime,"
						+ "t2.commstatus,t2.commtime,t2.commtimeefficiency,t2.commrange,"
						+ "t2.runstatus,t2.runtimeefficiency,t2.runtime,t2.runrange,"
						+ "headerlabelinfo ";
				if("instance1".equalsIgnoreCase(instanceCode) || "instance145".equalsIgnoreCase(instanceCode) || "instance125".equalsIgnoreCase(instanceCode)){
					sql+=",t2.spm,t2.casingpressure,t2.bottomholepressure,t2.producingfluidlevel,"
							+ "t2.gasvolumetricproduction,t2.totalgasvolumetricproduction,t2.watervolumetricproduction,t2.totalwatervolumetricproduction,"
							+ "t2.realtimegasvolumetricproduction,t2.realtimewatervolumetricproduction";
				}else if("instance5".equalsIgnoreCase(instanceCode)){
					sql+=",t2.theoreticalproduction,"//12
						+ "t2.liquidvolumetricproduction,t2.oilvolumetricproduction,t2.watervolumetricproduction,"//13~15
						+ "t2.liquidweightproduction,t2.oilweightproduction,t2.waterweightproduction,"//16~18
						+ "t2.volumewatercut,t2.weightwatercut,"//19、20
						+ "t2.systemefficiency,t2.energyper100mlift,"//21、22
						+ "t2.pumpeff,t2.pumpeff1,t2.pumpeff2,"//23~25
						+ "t2.resultstatus,t2.extendeddays,"//26、27
						+ "t2.tubingpressure,t2.casingpressure,"//28、29
						+ "t2.producingfluidlevel,t2.pumpsettingdepth,t2.submergence,"//30~32
						+ "t2.rpm";//33
				}
				sql+= " from tbl_rpcdevice t,tbl_rpctimingcalculationdata t2 "
						+ "where t.id=t2.wellid ";
				if(StringManagerUtils.isNotNull(acqTime)){
					sql+= " and t2.caltime>to_date('"+acqTime+"','yyyy-mm-dd hh24:mi:ss')";
				}else{
//					sql+= " and t2.caltime>( select max(t3.caltime)-"+days+" from TBL_RPCDAILYCALCULATIONDATA t3 where t3.wellid="+queryDeviceId+" )";
				}
				sql+=" and t.id="+queryDeviceId;
				sql+= " order by t2.caltime";
				
				
				long t1=System.nanoTime();
				List<Object[]> queryDataList=OracleJdbcUtis.query(sql,"oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@127.0.0.1:1521/orclpdb", "ap_hc", "Ap201#");
				long t2=System.nanoTime();
				System.out.println(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":设备"+deviceId+"日汇总数据查询耗时:"+StringManagerUtils.getTimeDiff(t1, t2)+",记录数:"+queryDataList.size());
				
				String insertSql="";
				String insertColumns="";
				String insertValues="";
				List<String> clobDataList=null;
				for(Object[] obj:queryDataList){
					try{
						List<KeyValue> dataList=new ArrayList<>();
						int saveDeviceId=deviceId;
						String deviceName=obj[1]+"";
						String caltime=obj[2]+"";
						int commStatus=StringManagerUtils.stringToInteger(obj[3]+"");
						String commTime=obj[4]+"";
						String commTimeEfficiency=obj[5]+"";
						String commRange=(obj[6]+"").replaceAll("null", "");
						int runStatus=StringManagerUtils.stringToInteger(obj[7]+"");
						String runTime=obj[8]+"";
						String runTimeEfficiency=obj[9]+"";
						String runRange=(obj[10]+"").replaceAll("null", "");
						String headerlabelinfo=(obj[11]+"").replaceAll("null", "");
						
						if("instance1".equalsIgnoreCase(instanceCode) || "instance145".equalsIgnoreCase(instanceCode) || "instance125".equalsIgnoreCase(instanceCode)){
							String spm=(obj[12]+"").replaceAll("null", "");
							String casingpressure=(obj[13]+"").replaceAll("null", "");
							String bottomholepressure=(obj[14]+"").replaceAll("null", "");
							String producingfluidlevel=(obj[15]+"").replaceAll("null", "");
							String gasvolumetricproduction=(obj[16]+"").replaceAll("null", "");
							String totalgasvolumetricproduction=(obj[17]+"").replaceAll("null", "");
							String watervolumetricproduction=(obj[18]+"").replaceAll("null", "");
							String totalwatervolumetricproduction=(obj[19]+"").replaceAll("null", "");
							
							String realtimegasvolumetricproduction=(obj[20]+"").replaceAll("null", "");
							String realtimewatervolumetricproduction=(obj[21]+"").replaceAll("null", "");
							
							
							spm=spm+";"+spm+";"+spm+";"+spm+";"+spm+"; ";
							casingpressure=casingpressure+";"+casingpressure+";"+casingpressure+";"+casingpressure+";"+casingpressure+"; ";
							bottomholepressure=bottomholepressure+";"+bottomholepressure+";"+bottomholepressure+";"+bottomholepressure+";"+bottomholepressure+"; ";
							producingfluidlevel=producingfluidlevel+";"+producingfluidlevel+";"+producingfluidlevel+";"+producingfluidlevel+";"+producingfluidlevel+"; ";
							gasvolumetricproduction=totalgasvolumetricproduction+";"+totalgasvolumetricproduction+";"+totalgasvolumetricproduction+";"+totalgasvolumetricproduction+";"+totalgasvolumetricproduction+";"+gasvolumetricproduction;
							watervolumetricproduction=totalwatervolumetricproduction+";"+totalwatervolumetricproduction+";"+totalwatervolumetricproduction+";"+totalwatervolumetricproduction+";"+totalwatervolumetricproduction+";"+watervolumetricproduction;
							
							realtimegasvolumetricproduction=realtimegasvolumetricproduction+";"+realtimegasvolumetricproduction+";"+realtimegasvolumetricproduction+";"+realtimegasvolumetricproduction+";"+realtimegasvolumetricproduction+"; ";
							realtimewatervolumetricproduction=realtimewatervolumetricproduction+";"+realtimewatervolumetricproduction+";"+realtimewatervolumetricproduction+";"+realtimewatervolumetricproduction+";"+realtimewatervolumetricproduction+"; ";
							
							DataMapping dataMapping= loadProtocolMappingColumnByTitleMap.get("冲次");
							if(dataMapping!=null){
								dataList.add(new KeyValue(dataMapping.getMappingColumn(),spm));
							}
							
							dataMapping= loadProtocolMappingColumnByTitleMap.get("井口套压");
							if(dataMapping!=null){
								dataList.add(new KeyValue(dataMapping.getMappingColumn(),casingpressure));
							}
							
							dataMapping= loadProtocolMappingColumnByTitleMap.get("修正后井底流压");
							if(dataMapping!=null){
								dataList.add(new KeyValue(dataMapping.getMappingColumn(),bottomholepressure));
							}
							
							dataMapping= loadProtocolMappingColumnByTitleMap.get("动液面");
							if(dataMapping!=null){
								dataList.add(new KeyValue(dataMapping.getMappingColumn(),producingfluidlevel));
							}
							
							dataMapping= loadProtocolMappingColumnByTitleMap.get("产气量累计");
							if(dataMapping!=null){
								dataList.add(new KeyValue(dataMapping.getMappingColumn(),gasvolumetricproduction));
							}
							
							dataMapping= loadProtocolMappingColumnByTitleMap.get("产水量累计");
							if(dataMapping!=null){
								dataList.add(new KeyValue(dataMapping.getMappingColumn(),watervolumetricproduction));
							}
							
							dataMapping= loadProtocolMappingColumnByTitleMap.get("产气量瞬时");
							if(dataMapping!=null){
								dataList.add(new KeyValue(dataMapping.getMappingColumn(),realtimegasvolumetricproduction));
							}
							
							dataMapping= loadProtocolMappingColumnByTitleMap.get("产水量瞬时");
							if(dataMapping!=null){
								dataList.add(new KeyValue(dataMapping.getMappingColumn(),realtimewatervolumetricproduction));
							}
						}
						insertSql="";
						insertColumns="deviceId,caltime,commStatus,commTime,commTimeEfficiency,runStatus,runTime,runTimeEfficiency,headerlabelinfo,commRange,runRange,calData";
						insertValues=saveDeviceId+",to_date('"+caltime+"','yyyy-mm-dd hh24:mi:ss'),"+commStatus+","+commTime+","+commTimeEfficiency+","+runStatus+","+runTime+","+runTimeEfficiency+",'"+headerlabelinfo+"',?,?,?";
						
						insertSql= "insert into tbl_timingcalculationdata ("+insertColumns+") values ("+insertValues+")";
						
						clobDataList=new ArrayList<>();
						clobDataList.add(commRange);
						clobDataList.add(runRange);
						clobDataList.add(gson.toJson(dataList));
						OracleJdbcUtis.executeSqlUpdateClob(insertSql, clobDataList);
						
						
						if("instance5".equalsIgnoreCase(instanceCode)){
							String theoreticalproduction=obj[12]+"";
							
							String liquidvolumetricproduction=obj[13]+"";
							String oilvolumetricproduction=obj[14]+"";
							String watervolumetricproduction=obj[15]+"";
							
							String liquidweightproduction=obj[16]+"";
							String oilweightproduction=obj[17]+"";
							String waterweightproduction=obj[18]+"";
							
							String volumewatercut=obj[19]+"";
							String weightwatercut=obj[20]+"";
							
							String systemefficiency=obj[21]+"";
							String energyper100mlift=obj[22]+"";
							
							String pumpeff=obj[23]+"";
							String pumpeff1=obj[24]+"";
							String pumpeff2=obj[25]+"";
							
							String resultstatus=obj[26]+"";
							String extendeddays=obj[27]+"";
							
							String tubingpressure=obj[28]+"";
							String casingpressure=obj[29]+"";
							
							String producingfluidlevel=obj[30]+"";
							String pumpsettingdepth=obj[31]+"";
							String submergence=obj[32]+"";
							
							String rpm=obj[33]+"";
							
							insertColumns="deviceId,caltime,"
									+ "commStatus,commTime,commTimeEfficiency,"
									+ "runStatus,runTime,runTimeEfficiency,"
									+ "headerlabelinfo,"
									+ "commRange,runRange";
							insertValues=saveDeviceId+",to_date('"+caltime+"','yyyy-mm-dd hh24:mi:ss'),"
									+commStatus+","+commTime+","+commTimeEfficiency+","
									+runStatus+","+runTime+","+runTimeEfficiency+","
									+ "'"+headerlabelinfo+"',"
									+ "?,?";
							
							if(StringManagerUtils.stringToInteger(resultstatus)==1 && StringManagerUtils.isNotNull(rpm)){
								insertColumns+=",theoreticalproduction,"//12
										+ "liquidvolumetricproduction,oilvolumetricproduction,watervolumetricproduction,"//13~15
										+ "liquidweightproduction,oilweightproduction,waterweightproduction,"//16~18
										+ "volumewatercut,weightwatercut,"//19、20
										+ "systemefficiency,energyper100mlift,"//21、22
										+ "pumpeff,pumpeff1,pumpeff2,"//23~25
										+ "resultstatus,extendeddays,"//26、27
										+ "tubingpressure,casingpressure,"//28、29
										+ "producingfluidlevel,pumpsettingdepth,submergence,"//30~32
										+ "rpm";//33
								insertValues+=","+theoreticalproduction+","
										+liquidvolumetricproduction+","+oilvolumetricproduction+","+watervolumetricproduction+","
										+liquidweightproduction+","+oilweightproduction+","+waterweightproduction+","
										+volumewatercut+","+weightwatercut+","
										+systemefficiency+","+energyper100mlift+","
										+pumpeff+","+pumpeff1+","+pumpeff2+","
										+resultstatus+","+extendeddays+","
										+tubingpressure+","+casingpressure+","
										+producingfluidlevel+","+pumpsettingdepth+","+submergence+","
										+rpm;
							}
							
							insertSql= "insert into tbl_pcptimingcalculationdata ("+insertColumns+") values ("+insertValues+")";
							
							clobDataList=new ArrayList<>();
							clobDataList.add(commRange);
							clobDataList.add(runRange);
							OracleJdbcUtis.executeSqlUpdateClob(insertSql, clobDataList);
							
						} 
						
						HeiChaoCounterUtils.incr();//计数器加一
					}catch(Exception e){
						e.printStackTrace();
						continue;
					}
				}
				
				if(instanceCode.equalsIgnoreCase("instance5")){//螺杆泵
//					String sql=
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
