package com.cosog.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cosog.model.DataSourceConfig;
import com.cosog.model.DataWriteBackConfig;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.RPCCalculateResponseData;
import com.cosog.model.drive.AcqGroup;
import com.cosog.thread.calculate.InitIdAndIPPortThread;
import com.cosog.thread.calculate.ThreadPool;
import com.cosog.utils.Config;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

import redis.clients.jedis.Jedis;

@Component("OuterDatabaseSyncTask")  
public class OuterDatabaseSyncTask {
private static OuterDatabaseSyncTask instance=new OuterDatabaseSyncTask();
	
	public static OuterDatabaseSyncTask getInstance(){
		return instance;
	}
	
	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public static void RPCWellDataSync(){
		DataSourceConfig dataSourceConfig=MemoryDataManagerTask.getDataSourceConfig();
		if(dataSourceConfig!=null && dataSourceConfig.isEnable()){

			String sql="select t.wellname,to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqtime,to_char(t2.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss') as fesdiagramacqtime "
					+ " from tbl_rpcdevice t"
					+ " left outer join tbl_rpcacqdata_latest t2 on t.id=t2.wellid  "
					+ " where t.status=1"
					+ " ";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			conn=OracleJdbcUtis.getConnection();
			if(conn!=null){
				try {
					do{
						pstmt = conn.prepareStatement(sql);
						rs=pstmt.executeQuery();
						ThreadPool executor = new ThreadPool("RPCWellDataSyncThreadPool",
								Config.getInstance().configFile.getAp().getThreadPool().getOuterDatabaseSync().getCorePoolSize(), 
								Config.getInstance().configFile.getAp().getThreadPool().getOuterDatabaseSync().getMaximumPoolSize(), 
								Config.getInstance().configFile.getAp().getThreadPool().getOuterDatabaseSync().getKeepAliveTime(), 
								TimeUnit.SECONDS, 
								Config.getInstance().configFile.getAp().getThreadPool().getOuterDatabaseSync().getWattingCount());
						while(rs.next()){
							String wellName=rs.getString(1);
							String acqtime=rs.getString(2);
							String fesdiagramacqtime=rs.getString(3);
							executor.execute(new RPCWellDataSyncThread(wellName, acqtime, fesdiagramacqtime));
						}
						while (!executor.isCompletedByTaskCount()) {
							try {
								Thread.sleep(1000*1);
							}catch (Exception e) {
								e.printStackTrace();
							}
					    }
//						System.out.println("抽油机井数据同步完成-"+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
						try {
							Thread.sleep(1000*1);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}while(true);
				}catch (Exception e) {
					e.printStackTrace();
				} finally{
					OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
				}
	        }
		
		}
	}
	
	public static class RPCWellDataSyncThread extends Thread{
		private String wellName;
		private String acqtime;
		private String fesdiagramacqtime;
		public RPCWellDataSyncThread(String wellName, String acqtime, String fesdiagramacqtime) {
			super();
			this.wellName = wellName;
			this.acqtime = acqtime;
			this.fesdiagramacqtime = fesdiagramacqtime;
		}
		
		public void run(){
			DataSourceConfig dataSourceConfig=MemoryDataManagerTask.getDataSourceConfig();
			if(dataSourceConfig!=null && dataSourceConfig.isEnable()){
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					conn=OracleJdbcUtis.getOuterConnection();
					if(conn!=null){
						if(dataSourceConfig.getDiagramTable().getEnable()){
							String pushUrl=Config.getInstance().configFile.getAd().getInit().getServer().getContent().getIdAcqGroupDataPushURL();
							String wellNameColumn=dataSourceConfig.getDiagramTable().getColumns().getWellName().getColumn();
							String acqTimeColumn=dataSourceConfig.getDiagramTable().getColumns().getAcqTime().getColumn();
							String strokeColumn=dataSourceConfig.getDiagramTable().getColumns().getStroke().getColumn();
							String spmColumn=dataSourceConfig.getDiagramTable().getColumns().getSPM().getColumn();
							String pointCountColumn=dataSourceConfig.getDiagramTable().getColumns().getPointCount().getColumn();
							String sColumn=dataSourceConfig.getDiagramTable().getColumns().getS().getColumn();
							String fColumn=dataSourceConfig.getDiagramTable().getColumns().getF().getColumn();
							String iColumn=dataSourceConfig.getDiagramTable().getColumns().getI().getColumn();
							String KWattColumn=dataSourceConfig.getDiagramTable().getColumns().getKWatt().getColumn();
							String sql="";
							String finalSql="";
							Gson gson = new Gson();

							sql="select "+wellNameColumn+","
									+ "to_char("+acqTimeColumn+",'yyyy-mm-dd hh24:mi:ss') as "+acqTimeColumn+", "
									+ strokeColumn+", "
									+ spmColumn+", "
									+ pointCountColumn+", "
									+ sColumn+", "
									+ fColumn+", "
									+ iColumn+", "
									+ KWattColumn+" "
									+ " from "+dataSourceConfig.getDiagramTable().getName()+" t "
									+ " where t."+dataSourceConfig.getDiagramTable().getColumns().getWellName().getColumn()+"='"+this.wellName+"'";
							if(StringManagerUtils.isNotNull(this.fesdiagramacqtime)){
								sql+=" and "+acqTimeColumn+" > to_date('"+fesdiagramacqtime+"','yyyy-mm-dd hh24:mi:ss') order by "+acqTimeColumn;
							}else{
								sql+="order by "+acqTimeColumn+" desc";
							}
							
							finalSql="select "+wellNameColumn+","
									+ acqTimeColumn+", "
									+ strokeColumn+", "
									+ spmColumn+", "
									+ pointCountColumn+", "
									+ sColumn+", "
									+ fColumn+", "
									+ iColumn+", "
									+ KWattColumn+" "
									+ " from ("+sql+") v";
							if(StringManagerUtils.isNotNull(this.fesdiagramacqtime)){
								finalSql+=" where rownum<=100";
							}else{
								finalSql+=" where rownum<=1";//取最新数据
							}
							
							pstmt = conn.prepareStatement(finalSql);
							rs=pstmt.executeQuery();
							while(rs.next()){
								AcqGroup acqGroup=new AcqGroup();
								acqGroup.setID(this.wellName);
								acqGroup.setSlave((byte) 1);
								acqGroup.setGroupSN(0);
								acqGroup.setAddr(new ArrayList<Integer>());
								acqGroup.setValue(new ArrayList<List<Object>>());
								
								String fesdiagramAcqtimeStr=rs.getString(2);
								float stroke=rs.getFloat(3);
								float spm=rs.getFloat(4);
								int pointCount=rs.getInt(5);
								String sStr=rs.getString(6);
								String fStr=rs.getString(7);
								String iStr=rs.getString(8);
								String KWattStr=rs.getString(9);
								this.fesdiagramacqtime=fesdiagramAcqtimeStr;
								
								//运行状态
								acqGroup.getAddr().add(40317);
								List<Object> runStatusValueList=new ArrayList<>();
								runStatusValueList.add(1);
								acqGroup.getValue().add(runStatusValueList);
								
								//功图点数
								acqGroup.getAddr().add(40984);
								List<Object> pointCountValueList=new ArrayList<>();
								pointCountValueList.add(pointCount);
								acqGroup.getValue().add(pointCountValueList);
								
								//功图采集时间
								acqGroup.getAddr().add(40985);
								List<Object> fesdiagramAcqtimeValueList=new ArrayList<>();
								fesdiagramAcqtimeValueList.add(fesdiagramAcqtimeStr.replaceAll("-", "00").replaceAll(" ", "00").replaceAll(":", "00"));
								acqGroup.getValue().add(fesdiagramAcqtimeValueList);
								
								//冲次
								acqGroup.getAddr().add(40991);
								List<Object> spmValueList=new ArrayList<>();
								spmValueList.add(spm);
								acqGroup.getValue().add(spmValueList);
								
								//冲程
								acqGroup.getAddr().add(40993);
								List<Object> strokeValueList=new ArrayList<>();
								strokeValueList.add(stroke);
								acqGroup.getValue().add(strokeValueList);
								
								//功图位移
								acqGroup.getAddr().add(41001);
								List<Object> sValueList=new ArrayList<>();
								if(StringManagerUtils.isNotNull(sStr)){
									String[] sArr=sStr.replaceAll("；", ";").replaceAll(";", ",").split(",");
									for(int i=0;i<sArr.length;i++){
										sValueList.add(StringManagerUtils.stringToFloat(sArr[i]));
									}
								}
								acqGroup.getValue().add(sValueList);
								
								//功图载荷
								acqGroup.getAddr().add(41251);
								List<Object> fValueList=new ArrayList<>();
								if(StringManagerUtils.isNotNull(sStr)){
									String[] fArr=fStr.replaceAll("；", ";").replaceAll(";", ",").split(",");
									for(int i=0;i<fArr.length;i++){
										fValueList.add(StringManagerUtils.stringToFloat(fArr[i]));
									}
								}
								acqGroup.getValue().add(fValueList);
								
								//功图电流
								acqGroup.getAddr().add(41501);
								List<Object> iValueList=new ArrayList<>();
								if(StringManagerUtils.isNotNull(iStr)){
									String[] iArr=iStr.replaceAll("；", ";").replaceAll(";", ",").split(",");
									for(int i=0;i<iArr.length;i++){
										iValueList.add(StringManagerUtils.stringToFloat(iArr[i]));
									}
								}
								acqGroup.getValue().add(iValueList);
								
								//功图功率
								acqGroup.getAddr().add(41751);
								List<Object> kwattValueList=new ArrayList<>();
								if(StringManagerUtils.isNotNull(KWattStr)){
									String[] KWattArr=KWattStr.replaceAll("；", ";").replaceAll(";", ",").split(",");
									for(int i=0;i<KWattArr.length;i++){
										kwattValueList.add(StringManagerUtils.stringToFloat(KWattArr[i]));
									}
								}
								acqGroup.getValue().add(kwattValueList);
								
								String pushData=gson.toJson(acqGroup);
								System.out.println(pushData);
								if(StringManagerUtils.isNotNull(pushData)){
									StringManagerUtils.sendPostMethod(pushUrl, pushData,"utf-8",0,0);
								}
							}
						}
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
				}
			}
		}
		
		public String getWellName() {
			return wellName;
		}
		public void setWellName(String wellName) {
			this.wellName = wellName;
		}
		public String getAcqtime() {
			return acqtime;
		}
		public void setAcqtime(String acqtime) {
			this.acqtime = acqtime;
		}
		public String getFesdiagramacqtime() {
			return fesdiagramacqtime;
		}
		public void setFesdiagramacqtime(String fesdiagramacqtime) {
			this.fesdiagramacqtime = fesdiagramacqtime;
		}
	}
	
	public static class DiagramDataWriteBackThread extends Thread{
		private RPCCalculateResponseData rpcCalculateResponseData;
		public DiagramDataWriteBackThread(RPCCalculateResponseData rpcCalculateResponseData) {
			super();
			this.rpcCalculateResponseData = rpcCalculateResponseData;
		}
		
		public void run(){
			DataWriteBackConfig dataWriteBackConfig=MemoryDataManagerTask.getDataWriteBackConfig();
			if(dataWriteBackConfig!=null && dataWriteBackConfig.isEnable() && this.rpcCalculateResponseData!=null){
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					conn=OracleJdbcUtis.getDataWriteBackConnection();
					if(conn!=null){
						if(dataWriteBackConfig.getDiagramResult().getEnable()){
							String updateSql="";
							String insertSql="";
							String insertColumns="";
							String insertValues="";
							int itemCount=0;
							
							String wellNameValue="";
							String acqTimeValue="";
							
							updateSql="update "+dataWriteBackConfig.getDiagramResult().getTableName()+" t set ";
							insertSql="insert into "+dataWriteBackConfig.getDiagramResult().getTableName()+" (";
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getWellName().getEnable()){
								String value=getOperaValue(this.rpcCalculateResponseData.getWellName(),dataWriteBackConfig.getDiagramResult().getColumns().getWellName().getType());
								
//								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getWellName().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getWellName().getColumn()+",";
								insertValues+=value+",";
								
								wellNameValue=value;
//								itemCount++;
							}
							if(dataWriteBackConfig.getDiagramResult().getColumns().getAcqTime().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData.getFESDiagram()!=null){
									value=getOperaValue(this.rpcCalculateResponseData.getFESDiagram().getAcqTime(),dataWriteBackConfig.getDiagramResult().getColumns().getAcqTime().getType());
								}	
//								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getAcqTime().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getAcqTime().getColumn()+",";
								insertValues+=value+",";
								
								acqTimeValue=value;
//								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getFMax().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getFESDiagram().getFMax()!=null&&this.rpcCalculateResponseData.getFESDiagram().getFMax().size()>0){
										value=getOperaValue(rpcCalculateResponseData.getFESDiagram().getFMax().get(0)+"",dataWriteBackConfig.getDiagramResult().getColumns().getFMax().getType());
									}
								}	
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getFMax().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getFMax().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getFMin().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getFESDiagram().getFMin()!=null&&this.rpcCalculateResponseData.getFESDiagram().getFMin().size()>0){
										value=getOperaValue(rpcCalculateResponseData.getFESDiagram().getFMin().get(0)+"",dataWriteBackConfig.getDiagramResult().getColumns().getFMin().getType());
									}
								}	
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getFMin().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getFMin().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getUpperLoadLine().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getFESDiagram()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getFESDiagram().getUpperLoadLine()+"",dataWriteBackConfig.getDiagramResult().getColumns().getUpperLoadLine().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getUpperLoadLine().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getUpperLoadLine().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getLowerLoadLine().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getFESDiagram()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getFESDiagram().getLowerLoadLine()+"",dataWriteBackConfig.getDiagramResult().getColumns().getLowerLoadLine().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getLowerLoadLine().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getLowerLoadLine().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getFullnessCoefficient().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getFESDiagram()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getFESDiagram().getFullnessCoefficient()+"",dataWriteBackConfig.getDiagramResult().getColumns().getFullnessCoefficient().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getFullnessCoefficient().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getFullnessCoefficient().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getNoLiquidFullnessCoefficient().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getFESDiagram()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getFESDiagram().getNoLiquidFullnessCoefficient()+"",dataWriteBackConfig.getDiagramResult().getColumns().getNoLiquidFullnessCoefficient().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getNoLiquidFullnessCoefficient().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getNoLiquidFullnessCoefficient().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getResultCode().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									value=getOperaValue(this.rpcCalculateResponseData.getCalculationStatus().getResultCode()+"",dataWriteBackConfig.getDiagramResult().getColumns().getResultCode().getType());
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getResultCode().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getResultCode().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							//工况名称和优化建议
							String resultName="";
							String optimizationSuggestion="";
							
							Jedis jedis=null;
							try{
								jedis = RedisUtil.jedisPool.getResource();
								if(!jedis.exists("RPCWorkType".getBytes())){
									MemoryDataManagerTask.loadRPCWorkType();
								}
								WorkType workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("RPCWorkType".getBytes(), (rpcCalculateResponseData.getCalculationStatus().getResultCode()+"").getBytes()));
								if(workType!=null){
									resultName=workType.getResultName();
									optimizationSuggestion=workType.getOptimizationSuggestion();
								}
							}catch(Exception e){
								e.printStackTrace();
							}finally{
								if(jedis!=null&&jedis.isConnected()){
									jedis.close();
								}
							}
							resultName=getOperaValue(resultName,dataWriteBackConfig.getDiagramResult().getColumns().getResultName().getType());
							optimizationSuggestion=getOperaValue(optimizationSuggestion,dataWriteBackConfig.getDiagramResult().getColumns().getOptimizationSuggestion().getType());
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getResultName().getEnable()){
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getResultName().getColumn()+ "="+resultName+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getResultName().getColumn()+",";
								insertValues+=resultName+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getOptimizationSuggestion().getEnable()){
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getOptimizationSuggestion().getColumn()+ "="+optimizationSuggestion+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getOptimizationSuggestion().getColumn()+",";
								insertValues+=optimizationSuggestion+",";
								itemCount++;
							}
							
							//产量
							if(dataWriteBackConfig.getDiagramResult().getColumns().getLiquidVolumetricProduction().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getProduction()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getProduction().getLiquidVolumetricProduction()+"",dataWriteBackConfig.getDiagramResult().getColumns().getLiquidVolumetricProduction().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getLiquidVolumetricProduction().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getLiquidVolumetricProduction().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							if(dataWriteBackConfig.getDiagramResult().getColumns().getOilVolumetricProduction().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getProduction()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getProduction().getOilVolumetricProduction()+"",dataWriteBackConfig.getDiagramResult().getColumns().getOilVolumetricProduction().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getOilVolumetricProduction().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getOilVolumetricProduction().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							if(dataWriteBackConfig.getDiagramResult().getColumns().getWaterVolumetricProduction().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getProduction()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getProduction().getWaterVolumetricProduction()+"",dataWriteBackConfig.getDiagramResult().getColumns().getWaterVolumetricProduction().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getWaterVolumetricProduction().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getWaterVolumetricProduction().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getLiquidWeightProduction().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getProduction()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getProduction().getLiquidWeightProduction()+"",dataWriteBackConfig.getDiagramResult().getColumns().getLiquidWeightProduction().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getLiquidWeightProduction().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getLiquidWeightProduction().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							if(dataWriteBackConfig.getDiagramResult().getColumns().getOilWeightProduction().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getProduction()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getProduction().getOilWeightProduction()+"",dataWriteBackConfig.getDiagramResult().getColumns().getOilWeightProduction().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getOilWeightProduction().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getOilWeightProduction().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							if(dataWriteBackConfig.getDiagramResult().getColumns().getWaterWeightProduction().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getProduction()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getProduction().getWaterWeightProduction()+"",dataWriteBackConfig.getDiagramResult().getColumns().getWaterWeightProduction().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getWaterWeightProduction().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getWaterWeightProduction().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getTheoreticalProduction().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getProduction()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getProduction().getTheoreticalProduction()+"",dataWriteBackConfig.getDiagramResult().getColumns().getTheoreticalProduction().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getTheoreticalProduction().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getTheoreticalProduction().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getPumpEff().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getPumpEfficiency()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getPumpEfficiency().getPumpEff()+"",dataWriteBackConfig.getDiagramResult().getColumns().getPumpEff().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getPumpEff().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getPumpEff().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getCalcProducingfluidLevel().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getProduction()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getProduction().getCalcProducingfluidLevel()+"",dataWriteBackConfig.getDiagramResult().getColumns().getCalcProducingfluidLevel().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getCalcProducingfluidLevel().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getCalcProducingfluidLevel().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getLevelDifferenceValue().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getProduction()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getProduction().getLevelDifferenceValue()+"",dataWriteBackConfig.getDiagramResult().getColumns().getLevelDifferenceValue().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getLevelDifferenceValue().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getLevelDifferenceValue().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getSubmergence().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getProduction()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getProduction().getSubmergence()+"",dataWriteBackConfig.getDiagramResult().getColumns().getSubmergence().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getSubmergence().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getSubmergence().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getUpStrokeIMax().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getFESDiagram()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getFESDiagram().getUpStrokeIMax()+"",dataWriteBackConfig.getDiagramResult().getColumns().getUpStrokeIMax().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getUpStrokeIMax().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getUpStrokeIMax().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getDownStrokeIMax().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getFESDiagram()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getFESDiagram().getDownStrokeIMax()+"",dataWriteBackConfig.getDiagramResult().getColumns().getDownStrokeIMax().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getDownStrokeIMax().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getDownStrokeIMax().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getIDegreeBalance().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getFESDiagram()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getFESDiagram().getIDegreeBalance()+"",dataWriteBackConfig.getDiagramResult().getColumns().getIDegreeBalance().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getIDegreeBalance().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getIDegreeBalance().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getUpStrokeWattMax().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getFESDiagram()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getFESDiagram().getUpStrokeWattMax()+"",dataWriteBackConfig.getDiagramResult().getColumns().getUpStrokeWattMax().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getUpStrokeWattMax().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getUpStrokeWattMax().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getDownStrokeWattMax().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getFESDiagram()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getFESDiagram().getDownStrokeWattMax()+"",dataWriteBackConfig.getDiagramResult().getColumns().getDownStrokeWattMax().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getDownStrokeWattMax().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getDownStrokeWattMax().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getWattDegreeBalance().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getFESDiagram()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getFESDiagram().getWattDegreeBalance()+"",dataWriteBackConfig.getDiagramResult().getColumns().getWattDegreeBalance().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getWattDegreeBalance().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getWattDegreeBalance().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getDeltaRadius().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getFESDiagram()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getFESDiagram().getDeltaRadius()+"",dataWriteBackConfig.getDiagramResult().getColumns().getDeltaRadius().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getDeltaRadius().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getDeltaRadius().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}

							if(dataWriteBackConfig.getDiagramResult().getColumns().getWellDownSystemEfficiency().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getSystemEfficiency()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getSystemEfficiency().getWellDownSystemEfficiency()+"",dataWriteBackConfig.getDiagramResult().getColumns().getWellDownSystemEfficiency().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getWellDownSystemEfficiency().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getWellDownSystemEfficiency().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getSurfaceSystemEfficiency().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getSystemEfficiency()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getSystemEfficiency().getSurfaceSystemEfficiency()+"",dataWriteBackConfig.getDiagramResult().getColumns().getSurfaceSystemEfficiency().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getSurfaceSystemEfficiency().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getSurfaceSystemEfficiency().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getSystemEfficiency().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getSystemEfficiency()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getSystemEfficiency().getSystemEfficiency()+"",dataWriteBackConfig.getDiagramResult().getColumns().getSystemEfficiency().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getSystemEfficiency().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getSystemEfficiency().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(dataWriteBackConfig.getDiagramResult().getColumns().getEnergyPer100mLift().getEnable()){
								String value="null";
								if(this.rpcCalculateResponseData!=null&&(this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1||this.rpcCalculateResponseData.getCalculationStatus().getResultStatus()==-99)){
									if(this.rpcCalculateResponseData.getSystemEfficiency()!=null){
										value=getOperaValue(this.rpcCalculateResponseData.getSystemEfficiency().getEnergyPer100mLift()+"",dataWriteBackConfig.getDiagramResult().getColumns().getEnergyPer100mLift().getType());
									}	
								}
								updateSql+= dataWriteBackConfig.getDiagramResult().getColumns().getEnergyPer100mLift().getColumn()+ "="+value+",";
								insertColumns+=dataWriteBackConfig.getDiagramResult().getColumns().getEnergyPer100mLift().getColumn()+",";
								insertValues+=value+",";
								itemCount++;
							}
							
							if(itemCount>0){
								if(updateSql.endsWith(",")){
									updateSql=updateSql.substring(0,updateSql.length()-1);
								}
								if(insertColumns.endsWith(",")){
									insertColumns=insertColumns.substring(0,insertColumns.length()-1);
								}
								if(insertValues.endsWith(",")){
									insertValues=insertValues.substring(0,insertValues.length()-1);
								}
								
								updateSql+=" where "+dataWriteBackConfig.getDiagramResult().getColumns().getWellName().getColumn()+"="+wellNameValue+" "
										+ " and "+dataWriteBackConfig.getDiagramResult().getColumns().getAcqTime().getColumn()+"="+acqTimeValue;
								insertSql+=insertColumns+") values ("+insertValues+")";
								
								String sql=updateSql;
								
								if("insert".equalsIgnoreCase(dataWriteBackConfig.getWriteType())){
									sql=insertSql;
								}
								
								try{  
						            pstmt=conn.prepareStatement(sql);
						            int iNum=pstmt.executeUpdate();
						        }catch(RuntimeException re){  
						        	re.printStackTrace();
						        }
							}
						}
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
				}
			}
		}
		
		public String getOperaValue(String value,String type){
			if("date".equalsIgnoreCase(type)){
				value="to_date('"+value+"','yyyy-mm-dd hh24:mi:ss')";
			}else if(!"number".equalsIgnoreCase(type)){
				value="'"+value+"'";
			}
			return value;
		}

		public RPCCalculateResponseData getRpcCalculateResponseData() {
			return rpcCalculateResponseData;
		}

		public void setRpcCalculateResponseData(RPCCalculateResponseData rpcCalculateResponseData) {
			this.rpcCalculateResponseData = rpcCalculateResponseData;
		}
	}
}
