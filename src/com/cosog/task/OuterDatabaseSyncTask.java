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
import com.cosog.model.drive.AcqGroup;
import com.cosog.thread.calculate.InitIdAndIPPortThread;
import com.cosog.thread.calculate.ThreadPool;
import com.cosog.utils.Config;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

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
					+ " from tbl_rpcdevice t,tbl_rpcacqdata_latest t2 "
					+ " where t.id=t2.wellid "
//					+ " and t.wellname='test_01'"
					+ " and t.status=1";
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
}
