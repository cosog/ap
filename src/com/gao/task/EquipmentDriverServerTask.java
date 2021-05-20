package com.gao.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.gao.model.AcquisitionUnitData;
import com.gao.model.AlarmShowStyle;
import com.gao.model.calculate.FA2FSResponseData;
import com.gao.model.drive.KafkaConfig;
import com.gao.model.drive.RTUDriveConfig;
import com.gao.model.drive.TcpServerConfig;
import com.gao.model.gridmodel.WellHandsontableChangedData;
import com.gao.thread.calculate.ProtocolModbusThread;
import com.gao.thread.calculate.IntelligentPumpingUnitThread;
import com.gao.thread.calculate.ProtocolBasicThread;
import com.gao.utils.AcquisitionUnitMap;
import com.gao.utils.Config;
import com.gao.utils.Config2;
import com.gao.utils.ConfigFile;
import com.gao.utils.DataModelMap;
import com.gao.utils.EquipmentDriveMap;
import com.gao.utils.MapSortUtil;
import com.gao.utils.OracleJdbcUtis;
import com.gao.utils.SerialPortUtils;
import com.gao.utils.StringManagerUtils;
import com.gao.utils.TcpServerConfigMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.CLOB;

@Component("EquipmentDriverServerTask")  
public class EquipmentDriverServerTask {
	public static Connection conn = null;   
	public static PreparedStatement pstmt = null;  
	public static Statement stmt = null;  
	public static ResultSet rs = null; 
	public static List<UnitData> units=null;
	public static List<ClientUnit> clientUnitList=null;
	public static ServerSocket beeTechServerSocket;
	public static ServerSocket sunMoonServerSocket;
	public static boolean exit=false;
	public static TcpServerConfig tcpServerConfig=null;
	public static ServerSocket serverSocket=null;
	private ExecutorService pool = Executors.newCachedThreadPool();
	
	private static EquipmentDriverServerTask instance=new EquipmentDriverServerTask();
	
	public static EquipmentDriverServerTask getInstance(){
		return instance;
	}
	
	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public void driveServerTast() throws SQLException, ParseException,InterruptedException, IOException{
		byte[] aa={0x40,(byte) 0x99,(byte) 0xEB,(byte)0x85};
		byte[] bb={(byte)0x85,(byte) 0xEB,(byte) 0x99,0x40};
		
		float f1=StringManagerUtils.getFloat(aa, 0);
		float f2=StringManagerUtils.getFloatLittle(bb, 0);
		
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		initDriverConfig();//初始化驱动配置
		boolean reg=false;
		do{
			reg=initAcquisitionUnit();//初始化采集单元信息
			if(!reg){
				Thread.sleep(5*1000);
			}
		}while(!reg);
		do{
			reg=init();//初始化井信息
			if(!reg){
				Thread.sleep(5*1000);
			}
		}while(!reg);
		
		Map<String, Object> tcpServerConfigMap = TcpServerConfigMap.getMapObject();
		if(tcpServerConfigMap==null || tcpServerConfigMap.get("TcpServerConfig")==null){
			String path="";
			String TcpServerConfigData="";
			StringManagerUtils stringManagerUtils=new StringManagerUtils();
			path=stringManagerUtils.getFilePath("TcpServerConfig.json","protocolConfig/");
			TcpServerConfigData=stringManagerUtils.readFile(path,"utf-8");
			type = new TypeToken<TcpServerConfig>() {}.getType();
			TcpServerConfig tcpServerConfig=gson.fromJson(TcpServerConfigData, type);
			tcpServerConfigMap.put("TcpServerConfig", tcpServerConfig);
		}

		while(true){
			tcpServerConfig=(TcpServerConfig) tcpServerConfigMap.get("TcpServerConfig");
			if(tcpServerConfig!=null&&tcpServerConfig.getPort()>0){
				try {
					if(serverSocket==null || serverSocket.isClosed()){
						serverSocket = new ServerSocket(tcpServerConfig.getPort());
						System.out.println("启动TCPServer,端口:"+tcpServerConfig.getPort());
					}
					for(int i=0;i<EquipmentDriverServerTask.clientUnitList.size();i++){
						if(EquipmentDriverServerTask.clientUnitList.get(i).socket==null || EquipmentDriverServerTask.clientUnitList.get(i).socket.isClosed()){
							try {
								if(serverSocket==null || serverSocket.isClosed()){
									serverSocket = new ServerSocket(tcpServerConfig.getPort());
									System.out.println("启动TCPServer,端口:"+tcpServerConfig.getPort());
								}
								System.out.println("TcpServer等待客户端连接...");
								Socket socket=serverSocket.accept();
								if(EquipmentDriverServerTask.clientUnitList.get(i).socket.isClosed()){
									EquipmentDriverServerTask.clientUnitList.get(i).socket=null;
								}
//								EquipmentDriverServerTask.clientUnitList.get(i).socket=new Socket();
//								EquipmentDriverServerTask.clientUnitList.get(i).socket=socket;
								EquipmentDriverServerTask.clientUnitList.get(i).setSocket(socket);
								if(EquipmentDriverServerTask.clientUnitList.get(i).socket!=null){
									System.out.println("TCPServer接收到客户端连接,thread:"+i+",IP:"+EquipmentDriverServerTask.clientUnitList.get(i).socket.getInetAddress()+",端口:"+EquipmentDriverServerTask.clientUnitList.get(i).socket.getPort());
									EquipmentDriverServerTask.clientUnitList.get(i).thread=new ProtocolModbusThread(i,EquipmentDriverServerTask.clientUnitList.get(i));
									if(EquipmentDriverServerTask.clientUnitList.get(i).thread!=null){
										pool.submit(EquipmentDriverServerTask.clientUnitList.get(i).thread);
										System.out.println("线程池中当前线程数："+((ThreadPoolExecutor)pool).getPoolSize());
										System.out.println("线程池中当前活跃线程数："+((ThreadPoolExecutor)pool).getActiveCount());
										break;
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
								if(serverSocket!=null&& !serverSocket.isClosed()){
									serverSocket.close();
								}
								break;
							}
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static boolean init(){
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		Map<String, Object> acquisitionUnitMap = AcquisitionUnitMap.getMapObject();
		String sql="select t.wellName,t.liftingType,t.deviceAddr,t.deviceId,to_char(t2.acqTime,'yyyy-mm-dd hh24:mi:ss'),"
				+ " t4.runtimeefficiencysource,"
				+ " t.protocolcode,t.unitcode,"
				+ " to_char(t3.acqTime,'yyyy-mm-dd hh24:mi:ss') as disAcqTime,"
				+ " t3.commstatus,t3.commtime,t3.commtimeefficiency,t3.commrange,"
				+ " t3.runstatus,t3.runtime,t3.runtimeefficiency,t3.runrange,"
				+ " t3.totalKWattH,t3.totalPKWattH,t3.totalNKWattH,t3.totalKVarH,t3.totalPKVarH,t3.totalNKVarH,t3.totalKVAH,"
				+ " t3.todayKWattH,t3.todayPKWattH,t3.todayNKWattH,t3.todayKVarH,t3.todayPKVarH,t3.todayNKVarH,t3.todayKVAH,"
				+ " t.protocol "
				+ " from tbl_wellinformation t "
				+ " left outer join  tbl_rpc_diagram_latest t2 on t2.wellId=t.id"
				+ " left outer join  tbl_rpc_discrete_latest  t3 on t3.wellId=t.id"
				+ " left outer join  tbl_rpc_productiondata_latest  t4 on t4.wellId=t.id"
				+ " where t.liftingType>=200 and t.liftingType<300 "
				+ " order by t.sortNum";
		String pcpInitSql="select t.wellName,t.liftingType,t.deviceAddr,t.deviceId,to_char(t2.acqTime,'yyyy-mm-dd hh24:mi:ss'),"
				+ " t4.runtimeefficiencysource,"
				+ " t.protocolcode,t.unitcode,"
				+ " to_char(t3.acqTime,'yyyy-mm-dd hh24:mi:ss') as disAcqTime,"
				+ " t3.commstatus,t3.commtime,t3.commtimeefficiency,t3.commrange,"
				+ " t3.runstatus,t3.runtime,t3.runtimeefficiency,t3.runrange,"
				+ " t3.totalKWattH,t3.totalPKWattH,t3.totalNKWattH,t3.totalKVarH,t3.totalPKVarH,t3.totalNKVarH,t3.totalKVAH,"
				+ " t3.todayKWattH,t3.todayPKWattH,t3.todayNKWattH,t3.todayKVarH,t3.todayPKVarH,t3.todayNKVarH,t3.todayKVAH,"
				+ " t2.rpm,"
				+ " t.protocol "
				+ " from tbl_wellinformation t "
				+ " left outer join  tbl_pcp_rpm_latest t2 on t2.wellId=t.id"
				+ " left outer join  tbl_pcp_discrete_latest  t3 on t3.wellId=t.id"
				+ " left outer join  tbl_pcp_productiondata_latest  t4 on t4.wellId=t.id"
				+ " where t.liftingType>=400 and t.liftingType<500"
				+ " order by t.sortNum";
		String AcqTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String resetCommStatus="update tbl_rpc_discrete_latest t set t.commstatus=0  ";
		String resetPCPCommStatus="update tbl_pcp_discrete_latest t set t.commstatus=0  ";
		if(clientUnitList!=null){
			for(int i=0;i<clientUnitList.size();i++){
				if(clientUnitList.get(i).thread!=null){
					clientUnitList.get(i).thread.interrupt();
				}
				if(clientUnitList.get(i).socket!=null){
					try {
						clientUnitList.get(i).socket.close();
					} catch (IOException e) {
						e.printStackTrace();
						return false;
					}
				}
			}
			for(int i=0;i<clientUnitList.size();i++){
				clientUnitList.remove(i);
			}
		}
		units=new ArrayList<UnitData>();
		clientUnitList=new ArrayList<ClientUnit>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
			return false;
		}
		
		try {
			stmt=conn.createStatement();
			int result=stmt.executeUpdate(resetCommStatus);
			result=stmt.executeUpdate(resetPCPCommStatus);
			System.out.println("读取井初始化信息");
			pstmt = conn.prepareStatement(sql); 
			System.out.println("读取抽油机井初始化信息成功");
			rs=pstmt.executeQuery();
			while(rs.next()){
				UnitData unit=new UnitData();
				ClientUnit clientUnit=new ClientUnit();
				unit.wellName=rs.getString(1);
				unit.liftingType=rs.getInt(2);
				unit.deviceAddr=rs.getString(3)==null?"":rs.getString(3);
				unit.deviceId=rs.getString(4)==null?"":rs.getString(4);
				unit.diagramAcqTime=rs.getString(5);
				unit.UnitId=Integer.parseInt(rs.getString(4)==null?"0":rs.getString(4));
				unit.protocolName="";
				unit.commStatus=0;
				unit.acquisitionData=new AcquisitionData();
				unit.acquisitionData.setRunStatus(0);
				unit.runTimeEfficiencySource=rs.getInt(6);
				unit.runStatusControl=0;
				for(Entry<String, Object> entry:equipmentDriveMap.entrySet()){
					if(!(entry.getKey().toUpperCase().contains("KAFKA")||entry.getKey().toUpperCase().contains("MQTT"))){
						RTUDriveConfig driveConfig=(RTUDriveConfig)entry.getValue();
						if(driveConfig.getProtocolCode().equalsIgnoreCase(rs.getString(7))){
							unit.setRtuDriveConfig(driveConfig);
							unit.setProtocolName(driveConfig.getProtocolName());
							break;
						}
					}
				}
				for(Entry<String, Object> entry:acquisitionUnitMap.entrySet()){
					AcquisitionUnitData acquisitionUnitData=(AcquisitionUnitData)entry.getValue();
					if(acquisitionUnitData.getAcquisitionUnitCode().equalsIgnoreCase(rs.getString(8))){
						unit.setAcquisitionUnitData(acquisitionUnitData);
						break;
					}
				}
				unit.lastDisAcqTime=rs.getString(9);
				unit.lastCommStatus=rs.getInt(10);
				unit.lastCommTime=rs.getFloat(11);
				unit.lastCommTimeEfficiency=rs.getFloat(12);
				unit.lastCommRange=StringManagerUtils.CLOBtoString((CLOB) rs.getClob(13));
				unit.lastRunStatus=rs.getInt(14);
				unit.lastRunTime=rs.getFloat(15);
				unit.lastRunTimeEfficiency=rs.getFloat(16);
				unit.lastRunRange=StringManagerUtils.CLOBtoString((CLOB) rs.getClob(17));
				unit.lastTotalKWattH=rs.getFloat(18);
				unit.lastTotalPKWattH=rs.getFloat(19);
				unit.lastTotalNKWattH=rs.getFloat(20);
				unit.lastTotalKVarH=rs.getFloat(21);
				unit.lastTotalPKVarH=rs.getFloat(22);
				unit.lastTotalNKVarH=rs.getFloat(23);
				unit.lastTotalKVAH=rs.getFloat(24);
				unit.lastTodayKWattH=rs.getFloat(25);
				unit.lastTodayPKWattH=rs.getFloat(26);
				unit.lastTodayNKWattH=rs.getFloat(27);
				unit.lastTodayKVarH=rs.getFloat(28);
				unit.lastTodayPKVarH=rs.getFloat(29);
				unit.lastTodayNKVarH=rs.getFloat(30);
				unit.lastTodayKVAH=rs.getFloat(31);
				unit.protocol=rs.getInt(32);
				units.add(unit);
				clientUnitList.add(clientUnit);
			}
			pstmt = conn.prepareStatement(pcpInitSql); 
			System.out.println("读取螺杆泵井初始化信息成功");
			rs=pstmt.executeQuery();
			while(rs.next()){
				UnitData unit=new UnitData();
				ClientUnit clientUnit=new ClientUnit();
				unit.wellName=rs.getString(1);
				unit.liftingType=rs.getInt(2);
				unit.deviceAddr=rs.getString(3)==null?"":rs.getString(3);
				unit.deviceId=rs.getString(4)==null?"":rs.getString(4);
				unit.diagramAcqTime=rs.getString(5);
				unit.UnitId=Integer.parseInt(rs.getString(4)==null?"0":rs.getString(4));
				unit.protocolName="必创";
				unit.commStatus=0;
				unit.acquisitionData=new AcquisitionData();
				unit.acquisitionData.setRunStatus(0);
				unit.runTimeEfficiencySource=rs.getInt(6);
				unit.runStatusControl=0;
				for(Entry<String, Object> entry:equipmentDriveMap.entrySet()){
					RTUDriveConfig driveConfig=(RTUDriveConfig)entry.getValue();
					if(driveConfig.getProtocolCode().equalsIgnoreCase(rs.getString(7))){
						unit.setRtuDriveConfig(driveConfig);
						unit.setProtocolName(driveConfig.getProtocolName());
						break;
					}
				}
				for(Entry<String, Object> entry:acquisitionUnitMap.entrySet()){
					AcquisitionUnitData acquisitionUnitData=(AcquisitionUnitData)entry.getValue();
					if(acquisitionUnitData.getAcquisitionUnitCode().equalsIgnoreCase(rs.getString(8))){
						unit.setAcquisitionUnitData(acquisitionUnitData);
						break;
					}
				}
				unit.lastDisAcqTime=rs.getString(9);
				unit.lastCommStatus=rs.getInt(10);
				unit.lastCommTime=rs.getFloat(11);
				unit.lastCommTimeEfficiency=rs.getFloat(12);
				unit.lastCommRange=StringManagerUtils.CLOBtoString((CLOB) rs.getClob(13));
				unit.lastRunStatus=rs.getInt(14);
				unit.lastRunTime=rs.getFloat(15);
				unit.lastRunTimeEfficiency=rs.getFloat(16);
				unit.lastRunRange=StringManagerUtils.CLOBtoString((CLOB) rs.getClob(17));
				unit.lastTotalKWattH=rs.getFloat(18);
				unit.lastTotalPKWattH=rs.getFloat(19);
				unit.lastTotalNKWattH=rs.getFloat(20);
				unit.lastTotalKVarH=rs.getFloat(21);
				unit.lastTotalPKVarH=rs.getFloat(22);
				unit.lastTotalNKVarH=rs.getFloat(23);
				unit.lastTotalKVAH=rs.getFloat(24);
				unit.lastTodayKWattH=rs.getFloat(25);
				unit.lastTodayPKWattH=rs.getFloat(26);
				unit.lastTodayNKWattH=rs.getFloat(27);
				unit.lastTodayKVarH=rs.getFloat(28);
				unit.lastTodayPKVarH=rs.getFloat(29);
				unit.lastTodayNKVarH=rs.getFloat(30);
				unit.lastTodayKVAH=rs.getFloat(31);
				unit.lastRPM=rs.getFloat(32);
				unit.protocol=rs.getInt(33);
				units.add(unit);
				clientUnitList.add(clientUnit);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			OracleJdbcUtis.closeDBConnection(conn, stmt, pstmt, rs);
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("共配置井数:"+units.size()+",分配线程数量:"+clientUnitList.size());
		OracleJdbcUtis.closeDBConnection(conn, stmt, pstmt, rs);
		exit=true;
		try {
			if(beeTechServerSocket!=null){
				beeTechServerSocket.close();
				beeTechServerSocket=null;
			}
			if(sunMoonServerSocket!=null){
				sunMoonServerSocket.close();
				sunMoonServerSocket=null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		exit=true;
		return true;
	}
	
	public static int updateWellConfif(WellHandsontableChangedData wellHandsontableChangedData) throws SQLException, IOException{
		String wellList="";
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		Map<String, Object> acquisitionUnitMap = AcquisitionUnitMap.getMapObject();
		if(wellHandsontableChangedData!=null){
			if(wellHandsontableChangedData.getUpdatelist()!=null){
				for(int i=0;i<wellHandsontableChangedData.getUpdatelist().size();i++){
					wellList+="'"+wellHandsontableChangedData.getUpdatelist().get(i).getWellName()+"',";
				}
			}
			if(wellHandsontableChangedData.getInsertlist()!=null){
				for(int i=0;i<wellHandsontableChangedData.getInsertlist().size();i++){
					wellList+="'"+wellHandsontableChangedData.getInsertlist().get(i).getWellName()+"',";
				}
			}
		}
		if(wellList.endsWith(",")){
			wellList=wellList.substring(0, wellList.length()-1);
		}
		String sql="select t.wellName,t.liftingType,t.deviceaddr,t.deviceid,to_char(t2.acqTime,'yyyy-mm-dd hh24:mi:ss'),"
				+ " t3.runtimeefficiencysource,"
				+ " t.protocolcode,t.unitcode,t.protocol "
				+ " from tbl_wellinformation t "
				+ " left outer join tbl_rpc_diagram_latest t2 on t2.wellId=t.id "
				+ " left outer join  tbl_rpc_productiondata_latest  t3 on t3.wellId=t.id"
				+ " where 1=1  ";
		if(StringManagerUtils.isNotNull(wellList)){
			sql+=" and t.wellName in("+wellList+")";
		}
		sql+=" order by t.sortNum, t.wellName";
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
			return 0;
		}
		pstmt = conn.prepareStatement(sql); 
		rs=pstmt.executeQuery();
		while(units!=null&&rs.next()){
			boolean isAdd=true;
			for(int i=0;i<units.size();i++){
				if(units.get(i).wellName.equals(rs.getString(1))){//遍历目前内存中的井列表
					units.get(i).liftingType=rs.getInt(2);
					if(!units.get(i).deviceAddr.equals(rs.getString(3)==null?"":rs.getString(3))
							||!units.get(i).deviceId.equals(rs.getString(4)==null?"":rs.getString(4))){//驱动信息发生改变
						System.out.println("配置发生变化");
						for(int j=0;j<clientUnitList.size();j++){//遍历已连接的客户端
							boolean isExit=false;
							if(clientUnitList.get(j).socket!=null){//如果已连接
								for(int k=0;k<clientUnitList.get(j).unitDataList.size();k++){
									if(units.get(i).deviceAddr.equals(clientUnitList.get(j).unitDataList.get(k).deviceAddr)
											||(rs.getString(3)==null?"":rs.getString(3)).equals(clientUnitList.get(j).unitDataList.get(k).deviceAddr)){//查询原有设备地址和新地址的连接，如存在断开资源，释放资源
										if(clientUnitList.get(j).thread!=null){
											clientUnitList.get(j).thread.interrupt();
										}
										isExit=true;
									}
								}
							}
							if(isExit){
								break;
							}
						}
					}
					units.get(i).deviceAddr=rs.getString(3)==null?"":rs.getString(3);
					units.get(i).deviceId=rs.getString(4)==null?"":rs.getString(4);
					units.get(i).diagramAcqTime=rs.getString(5);
					units.get(i).UnitId=Integer.parseInt(rs.getString(4)==null?"01":rs.getString(4));
					units.get(i).commStatus=0;
					units.get(i).runTimeEfficiencySource=rs.getInt(6);
					units.get(i).runStatusControl=0;
					units.get(i).protocol=rs.getInt(9);
					for(Entry<String, Object> entry:equipmentDriveMap.entrySet()){
						if(!(entry.getKey().toUpperCase().contains("KAFKA")||entry.getKey().toUpperCase().contains("MQTT"))){
							RTUDriveConfig driveConfig=(RTUDriveConfig)entry.getValue();
							if(driveConfig.getProtocolCode().equals(rs.getString(7))){
								units.get(i).setRtuDriveConfig(driveConfig);
								units.get(i).setProtocolName(driveConfig.getProtocolName());
								break;
							}
						}
					}
					for(Entry<String, Object> entry:acquisitionUnitMap.entrySet()){
						AcquisitionUnitData acquisitionUnitData=(AcquisitionUnitData)entry.getValue();
						if(acquisitionUnitData.getAcquisitionUnitCode().equals(rs.getString(8))){
							units.get(i).setAcquisitionUnitData(acquisitionUnitData);
							break;
						}
					}
					isAdd=false;
					break;
				}
			}
			if(isAdd){//如果新加井
				UnitData unit=new UnitData();
				ClientUnit clientUnit=new ClientUnit();
				unit.wellName=rs.getString(1);
				unit.liftingType=rs.getInt(2);
				unit.deviceAddr=rs.getString(3);
				unit.deviceId=rs.getString(4);
				unit.diagramAcqTime=rs.getString(5);
				unit.UnitId=Integer.parseInt(unit.deviceId);
				unit.commStatus=0;
				unit.acquisitionData=new AcquisitionData();
				unit.acquisitionData.setRunStatus(0);
				unit.runTimeEfficiencySource=rs.getInt(6);
				unit.runStatusControl=0;
				unit.protocol=rs.getInt(9);
				for(Entry<String, Object> entry:equipmentDriveMap.entrySet()){
					RTUDriveConfig driveConfig=(RTUDriveConfig)entry.getValue();
					if(driveConfig.getProtocolCode().equals(rs.getString(7))){
						unit.setRtuDriveConfig(driveConfig);
						unit.setProtocolName(driveConfig.getProtocolName());
						break;
					}
				}
				for(Entry<String, Object> entry:acquisitionUnitMap.entrySet()){
					AcquisitionUnitData acquisitionUnitData=(AcquisitionUnitData)entry.getValue();
					if(acquisitionUnitData.getAcquisitionUnitCode().equals(rs.getString(8))){
						unit.setAcquisitionUnitData(acquisitionUnitData);
						break;
					}
				}
				units.add(unit);
				clientUnitList.add(clientUnit);
				for(int j=0;j<clientUnitList.size();j++){
					boolean isExit=false;
					if(clientUnitList.get(j).socket!=null){
						for(int k=0;k<clientUnitList.get(j).unitDataList.size();k++){
							if(unit.deviceAddr.equals(clientUnitList.get(j).unitDataList.get(k).deviceAddr)){
								if(clientUnitList.get(j).thread!=null){
									clientUnitList.get(j).thread.interrupt();
								}
								isExit=true;
								break;
							}
						}
					}
					if(isExit){
						break;
					}
				}
			}
		}
		OracleJdbcUtis.closeDBConnection(conn, stmt, pstmt, rs);
		return 1;
	}
	
	public static int updateWellName(String data) throws SQLException, IOException{
		JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+data+"}");//解析数据
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		for(int i=0;i<jsonArray.size();i++){
			JSONObject everydata = JSONObject.fromObject(jsonArray.getString(i));
			String oldWellName=everydata.getString("oldWellName");
			String newWellName=everydata.getString("newWellName");
			for(int j=0;units!=null&&j<units.size();j++){
				if(oldWellName.equals(units.get(j).wellName)){
					units.get(j).setWellName(newWellName);
				}
			}
		}
		return 1;
	}
	
	@SuppressWarnings("static-access")
	public static void initDriverConfig(){
		System.out.println("驱动初始化开始");
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		Gson gson = new Gson();
		String path="";
		String DriverConfigData="";
		java.lang.reflect.Type type=null;
		//添加安控驱动配置
		path=stringManagerUtils.getFilePath("EtrolDriverConfig.json","protocolConfig/");
		DriverConfigData=stringManagerUtils.readFile(path,"utf-8");
		type = new TypeToken<RTUDriveConfig>() {}.getType();
		RTUDriveConfig EtrolRTUDrive=gson.fromJson(DriverConfigData, type);
		equipmentDriveMap.put(EtrolRTUDrive.getProtocolCode(), EtrolRTUDrive);
		
		//添加必创驱动配置
		path=stringManagerUtils.getFilePath("BeeTechDriverConfig.json","protocolConfig/");
		DriverConfigData=stringManagerUtils.readFile(path,"utf-8");
		type = new TypeToken<RTUDriveConfig>() {}.getType();
		RTUDriveConfig BeeTechRTUDrive=gson.fromJson(DriverConfigData, type);
		equipmentDriveMap.put(BeeTechRTUDrive.getProtocolCode(), BeeTechRTUDrive);
		
		//添加蚌埠日月驱动配置
		path=stringManagerUtils.getFilePath("SunMoonDriverConfig.json","protocolConfig/");
		DriverConfigData=stringManagerUtils.readFile(path,"utf-8");
		type = new TypeToken<RTUDriveConfig>() {}.getType();
		RTUDriveConfig SunMoonStandardDriver=gson.fromJson(DriverConfigData, type);
		equipmentDriveMap.put(SunMoonStandardDriver.getProtocolCode(), SunMoonStandardDriver);
		
		//添加中科奥维驱动配置
		path=stringManagerUtils.getFilePath("ZKAWDriverConfig.json","protocolConfig/");
		DriverConfigData=stringManagerUtils.readFile(path,"utf-8");
		type = new TypeToken<RTUDriveConfig>() {}.getType();
		RTUDriveConfig ZKAWDRTUDrive=gson.fromJson(DriverConfigData, type);
		equipmentDriveMap.put(ZKAWDRTUDrive.getProtocolCode(), ZKAWDRTUDrive);
		
		//添加A11驱动配置
		path=stringManagerUtils.getFilePath("CNPCStandardDriverConfig.json","protocolConfig/");
		DriverConfigData=stringManagerUtils.readFile(path,"utf-8");
		type = new TypeToken<RTUDriveConfig>() {}.getType();
		RTUDriveConfig CNPCStandardDriver=gson.fromJson(DriverConfigData, type);
		equipmentDriveMap.put(CNPCStandardDriver.getProtocolCode(), CNPCStandardDriver);
		
		//添加四化驱动配置
		path=stringManagerUtils.getFilePath("SinoepcStandardDriverConfig.json","protocolConfig/");
		DriverConfigData=stringManagerUtils.readFile(path,"utf-8");
		type = new TypeToken<RTUDriveConfig>() {}.getType();
		RTUDriveConfig SinoepcStandardDriver=gson.fromJson(DriverConfigData, type);
		equipmentDriveMap.put(SinoepcStandardDriver.getProtocolCode(), SinoepcStandardDriver);
		
		//添加MQTT驱动配置
		path=stringManagerUtils.getFilePath("MqttDriverConfig.json","protocolConfig/");
		DriverConfigData=stringManagerUtils.readFile(path,"utf-8");
		type = new TypeToken<RTUDriveConfig>() {}.getType();
		RTUDriveConfig MqttDriver=gson.fromJson(DriverConfigData, type);
		equipmentDriveMap.put(MqttDriver.getProtocolCode(), MqttDriver);
		
		//添加Kafka
		path=stringManagerUtils.getFilePath("KafkaDriverConfig.json","protocolConfig/");
		DriverConfigData=stringManagerUtils.readFile(path,"utf-8");
		type = new TypeToken<KafkaConfig>() {}.getType();
		KafkaConfig kafkaConfig=gson.fromJson(DriverConfigData, type);
		equipmentDriveMap.put(kafkaConfig.getProtocolCode(), kafkaConfig);
		
		System.out.println("驱动初始化结束");
	}
	
	@SuppressWarnings("static-access")
	public static boolean  initAcquisitionUnit(){
		System.out.println("采集单元初始化开始");
		Map<String, Object> acquisitionUnitMap = AcquisitionUnitMap.getMapObject();
		String sql="select t.unit_code,t.unit_name from tbl_acq_unit_conf t order by id";
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
			return false;
		}
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			ResultSet itemRs = null; 
			while(rs.next()){
				AcquisitionUnitData acquisitionUnitData=null;
				if(acquisitionUnitMap.containsKey(rs.getString(1))){
					acquisitionUnitData=(AcquisitionUnitData)acquisitionUnitMap.get(rs.getString(1));
					acquisitionUnitData.init();
				}else{
					acquisitionUnitData=new AcquisitionUnitData();
				}
				acquisitionUnitData.setAcquisitionUnitCode(rs.getString(1));
				acquisitionUnitData.setAcquisitionUnitName(rs.getString(2));
				String itemsSql="select t.itemcode,t.itemname,t3.acq_cycle,t3.save_cycle "
						+ " from tbl_acq_item_conf t,tbl_acq_item2group_conf t2,tbl_acq_group_conf t3,tbl_acq_group2unit_conf t4,tbl_acq_unit_conf t5 "
						+ " where t.id=t2.itemid and t2.groupid=t3.id and t3.id=t4.groupid and t4.unitid=t5.id "
						+ " and t5.unit_code= '"+acquisitionUnitData.getAcquisitionUnitCode()+"' "
						+ " and t.id not in(select t6.parentid from tbl_acq_item_conf t6 )  order by t.id";
				pstmt = conn.prepareStatement(itemsSql); 
				itemRs=pstmt.executeQuery();
				while(itemRs.next()){
					if("SDiagram".equalsIgnoreCase(itemRs.getString(1)) || "FDiagram".equalsIgnoreCase(itemRs.getString(1)) || "ADiagram".equalsIgnoreCase(itemRs.getString(1)) || "PDiagram".equalsIgnoreCase(itemRs.getString(1))
							|| "FSDiagramAcquisitionInterval".equalsIgnoreCase(itemRs.getString(1)) || "FSDiagramSetPointCount".equalsIgnoreCase(itemRs.getString(1)) || "FSDiagramPointCount".equalsIgnoreCase(itemRs.getString(1)) || "AcqTime".equalsIgnoreCase(itemRs.getString(1))
							|| "SPM".equalsIgnoreCase(itemRs.getString(1)) || "Stroke".equalsIgnoreCase(itemRs.getString(1))){
						acquisitionUnitData.setAcqCycle_diagram(itemRs.getInt(3));
						acquisitionUnitData.setSaveCycle_diagram(itemRs.getInt(4));
					}else if("SPM".equalsIgnoreCase(itemRs.getString(1))){
						acquisitionUnitData.setAcqCycle_SPM(itemRs.getInt(3));
						acquisitionUnitData.setSaveCycle_SPM(itemRs.getInt(4));
					}else{
						acquisitionUnitData.setAcqCycle_discrete(itemRs.getInt(3));
						acquisitionUnitData.setSaveCycle_discrete(itemRs.getInt(4));
					}
					
					if("RunStatus".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setRunStatus(1);
					if("BalaceControlStatus".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setBalaceControlStatus(1);
					if("BalanceControlMode".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setBalanceControlMode(1);
					if("BalanceCalculateMode".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setBalanceCalculateMode(1);
					if("BalanceAwayTimePerBeat".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setBalanceAwayTimePerBeat(1);
					if("BalanceCloseTimePerBeat".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setBalanceCloseTimePerBeat(1);
					if("BalanceStrokeCount".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setBalanceStrokeCount(1);
					if("BalanceOperationUpLimit".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setBalanceOperationUpLimit(1);
					if("BalanceOperationDownLimit".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setBalanceOperationDownLimit(1);
					if("BalanceAwayTime".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setBalanceAwayTime(1);
					if("BalanceCloseTime".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setBalanceCloseTime(1);
					else if("CurrentA".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setCurrentA(1);
					else if("CurrentB".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setCurrentB(1);
					else if("CurrentC".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setCurrentC(1);
					else if("VoltageA".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setVoltageA(1);
					else if("VoltageB".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setVoltageB(1);
					else if("VoltageC".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setVoltageC(1);
					else if("ActivePowerConsumption".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setActivePowerConsumption(1);
					else if("ReactivePowerConsumption".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setReactivePowerConsumption(1);
					else if("ActivePower".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setActivePower(1);
					else if("ReactivePower".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setReactivePower(1);
					else if("ReversePower".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setReversePower(1);
					else if("PowerFactor".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setPowerFactor(1);
					else if("SetFrequency".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setSetFrequency(1);
					else if("RunFrequency".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setRunFrequency(1);
					else if("TubingPressure".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setTubingPressure(1);
					else if("CasingPressure".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setCasingPressure(1);
					else if("BackPressure".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setBackPressure(1);
					else if("WellHeadFluidTemperature".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setWellHeadFluidTemperature(1);
					else if("ProducingfluidLevel".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setProducingfluidLevel(1);
					else if("WaterCut".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setWaterCut(1);
					else if("RPM".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setRPM(1);
					else if("Torque".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setTorque(1);
					else if("FSDiagramAcquisitionInterval".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setFSDiagramAcquisitionInterval(1);
					else if("FSDiagramSetPointCount".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setFSDiagramSetPointCount(1);
					else if("FSDiagramPointCount".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setFSDiagramPointCount(1);
					else if("AcqTime".equalsIgnoreCase(itemRs.getString(1)) || "AcquisitionTime".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setAcqTime(1);
					else if("SPM".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setSPM(1);
					else if("Stroke".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setStroke(1);
					else if("SDiagram".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setSDiagram(1);
					else if("FDiagram".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setFDiagram(1);
					else if("ADiagram".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setADiagram(1);
					else if("PDiagram".equalsIgnoreCase(itemRs.getString(1)))
						acquisitionUnitData.setPDiagram(1);
				}
				if(!acquisitionUnitMap.containsKey(rs.getString(1))){
					acquisitionUnitMap.put(acquisitionUnitData.getAcquisitionUnitCode(), acquisitionUnitData);
				}
			}
			if(itemRs!=null){
				itemRs.close();
			}
		} catch (SQLException e) {
			OracleJdbcUtis.closeDBConnection(conn, stmt, pstmt, rs);
			e.printStackTrace();
			return false;
		}
		OracleJdbcUtis.closeDBConnection(conn, stmt, pstmt, rs);
		System.out.println("采集单元初始化结束");
		return true;
	}
	
	public static void initAlarmStyle() throws IOException, SQLException{
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		AlarmShowStyle alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		if(alarmShowStyle==null){
			alarmShowStyle=new AlarmShowStyle();
		}
		String sql="select v1.itemvalue,v1.itemname,v2.itemname,v3.itemname from "
				+ " (select * from tbl_code t where t.itemcode='BJYS' ) v1,"
				+ " (select * from tbl_code t where t.itemcode='BJQJYS' ) v2,"
				+ " (select * from tbl_code t where t.itemcode='BJYSTMD' ) v3 "
				+ " where v1.itemvalue=v2.itemvalue and v1.itemvalue=v3.itemvalue order by v1.itemvalue ";
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
			return ;
		}
		pstmt = conn.prepareStatement(sql); 
		rs=pstmt.executeQuery();
		while(rs.next()){
			if(rs.getInt(1)==0){
				alarmShowStyle.getNormal().setValue(rs.getInt(1));
				alarmShowStyle.getNormal().setBackgroundColor(rs.getString(2));
				alarmShowStyle.getNormal().setColor(rs.getString(3));
				alarmShowStyle.getNormal().setOpacity(rs.getString(4));
			}else if(rs.getInt(1)==100){
				alarmShowStyle.getFirstLevel().setValue(rs.getInt(1));
				alarmShowStyle.getFirstLevel().setBackgroundColor(rs.getString(2));
				alarmShowStyle.getFirstLevel().setColor(rs.getString(3));
				alarmShowStyle.getFirstLevel().setOpacity(rs.getString(4));
			}else if(rs.getInt(1)==200){
				alarmShowStyle.getSecondLevel().setValue(rs.getInt(1));
				alarmShowStyle.getSecondLevel().setBackgroundColor(rs.getString(2));
				alarmShowStyle.getSecondLevel().setColor(rs.getString(3));
				alarmShowStyle.getSecondLevel().setOpacity(rs.getString(4));
			}else if(rs.getInt(1)==300){
				alarmShowStyle.getThirdLevel().setValue(rs.getInt(1));
				alarmShowStyle.getThirdLevel().setBackgroundColor(rs.getString(2));
				alarmShowStyle.getThirdLevel().setColor(rs.getString(3));
				alarmShowStyle.getThirdLevel().setOpacity(rs.getString(4));
			}	
		}
		if(!dataModelMap.containsKey("AlarmShowStyle")){
			dataModelMap.put("AlarmShowStyle", alarmShowStyle);
		}
		OracleJdbcUtis.closeDBConnection(conn, stmt, pstmt, rs);
	}
	
	public static class AcquisitionData{
		public  String AcqTime="";
		public  String ReadTime="";//读取数据时间
		public  String SaveTime="";//离散数据保存时间
		public  String screwPumpSaveTime="";//螺杆泵数据保存时间
		public int runStatus;//运行状态
		public int egklx;//电参诊断结果
		public int acquisitionCycle;//功图采集周期
		public int FSDiagramSetPointCount;//功图设置点数
		public int point;//功图点数
		public float SPM;//冲次
		public float Stroke;//冲程
		public float RPM;//转速
		public float torque;//扭矩
		public String gtcjsj;//功图采集时间
		public float TubingPressure;//油压
		public float CasingPressure;//套压
		public float BackPressure;//回压
		public float WellHeadFluidTemperature;//井口油温
		public float ProducingfluidLevel;//动液面
		public float WaterCut;//含水率
		public float CurrentA;//A相电流
		public float CurrentB;//B相电流
		public float CurrentC;//C相电流
	    public float VoltageA;//A相电压
	    public float VoltageB;//B相电压
	    public float VoltageC;//C相电压
	    public float ActivePowerConsumption;//有功功耗
	    public float ReactivePowerConsumption;//无功功耗
	    public float ActivePower;//有功功率
	    public float ReactivePower;//无功功率
	    public float ReversePower;//反向功率
	    public float PowerFactor;//功率因数
	    public float SetFrequency;//变频设置频率
	    public float RunFrequency;//变频运行频率
	    
	    public int balanceAutoControl;
	    public int spmAutoControl;
	    public int balanceFrontLimit;
	    public int balanceAfterLimit;
	    public int balanceControlMode;
	    public int balanceCalculateMode;
	    public int balanceAwayTime;
	    public int balanceCloseTime;
	    public int balanceAwayTimePerBeat;
	    public int balanceCloseTimePerBeat;
		public int balanceStrokeCount;
		public int balanceOperationUpLimit;
		public int balanceOperationDownLimit;
	    
	    public String recvSBuff;
	    public String recvFBuff;
	    public String recvABuff;
	    public String recvPBuff;
	    
	    public String RealTimeRunRangeString;//运行区间字符串
	    public float RealTimeRunTime;//运行时间
	    public float RealTimeRunTimeEfficiency;//运行时率
	    public float DailyPowerConsumption;//日有功功耗
	    public float DailyReactivePowerConsumption;//日无功功耗
	    
	    public String RealTimeCommRangeString;//通信区间字符串
	    public float RealTimeCommTime;//在线时间
	    public float RealTimeCommTimeEfficiency;//在线时率
		public String getAcqTime() {
			return AcqTime;
		}
		public void setAcqTime(String acqTime) {
			AcqTime = acqTime;
		}
		public int getRunStatus() {
			return runStatus;
		}
		public void setRunStatus(int runStatus) {
			this.runStatus = runStatus;
		}
		public float getTubingPressure() {
			return TubingPressure;
		}
		public void setTubingPressure(float tubingPressure) {
			TubingPressure = tubingPressure;
		}
		public float getCasingPressure() {
			return CasingPressure;
		}
		public void setCasingPressure(float casingPressure) {
			CasingPressure = casingPressure;
		}
		public float getBackPressure() {
			return BackPressure;
		}
		public void setBackPressure(float backPressure) {
			BackPressure = backPressure;
		}
		public float getWellHeadFluidTemperature() {
			return WellHeadFluidTemperature;
		}
		public void setWellHeadFluidTemperature(float wellHeadFluidTemperature) {
			WellHeadFluidTemperature = wellHeadFluidTemperature;
		}
		public float getCurrentA() {
			return CurrentA;
		}
		public void setCurrentA(float currentA) {
			CurrentA = currentA;
		}
		public float getCurrentB() {
			return CurrentB;
		}
		public void setCurrentB(float currentB) {
			CurrentB = currentB;
		}
		public float getCurrentC() {
			return CurrentC;
		}
		public void setCurrentC(float currentC) {
			CurrentC = currentC;
		}
		public float getVoltageA() {
			return VoltageA;
		}
		public void setVoltageA(float voltageA) {
			VoltageA = voltageA;
		}
		public float getVoltageB() {
			return VoltageB;
		}
		public void setVoltageB(float voltageB) {
			VoltageB = voltageB;
		}
		public float getVoltageC() {
			return VoltageC;
		}
		public void setVoltageC(float voltageC) {
			VoltageC = voltageC;
		}
		public float getActivePowerConsumption() {
			return ActivePowerConsumption;
		}
		public void setActivePowerConsumption(float activePowerConsumption) {
			ActivePowerConsumption = activePowerConsumption;
		}
		public float getReactivePowerConsumption() {
			return ReactivePowerConsumption;
		}
		public void setReactivePowerConsumption(float reactivePowerConsumption) {
			ReactivePowerConsumption = reactivePowerConsumption;
		}
		public float getActivePower() {
			return ActivePower;
		}
		public void setActivePower(float activePower) {
			ActivePower = activePower;
		}
		public float getReactivePower() {
			return ReactivePower;
		}
		public void setReactivePower(float reactivePower) {
			ReactivePower = reactivePower;
		}
		public float getReversePower() {
			return ReversePower;
		}
		public void setReversePower(float reversePower) {
			ReversePower = reversePower;
		}
		public float getPowerFactor() {
			return PowerFactor;
		}
		public void setPowerFactor(float powerFactor) {
			PowerFactor = powerFactor;
		}
		public String getRecvSBuff() {
			return recvSBuff;
		}
		public void setRecvSBuff(String recvSBuff) {
			this.recvSBuff = recvSBuff;
		}
		public String getRecvFBuff() {
			return recvFBuff;
		}
		public void setRecvFBuff(String recvFBuff) {
			this.recvFBuff = recvFBuff;
		}
		public String getRecvABuff() {
			return recvABuff;
		}
		public void setRecvABuff(String recvABuff) {
			this.recvABuff = recvABuff;
		}
		public String getRecvPBuff() {
			return recvPBuff;
		}
		public void setRecvPBuff(String recvPBuff) {
			this.recvPBuff = recvPBuff;
		}
		public String getRealTimeRunRangeString() {
			return RealTimeRunRangeString;
		}
		public void setRealTimeRunRangeString(String realTimeRunRangeString) {
			RealTimeRunRangeString = realTimeRunRangeString;
		}
		public float getRealTimeRunTime() {
			return RealTimeRunTime;
		}
		public void setRealTimeRunTime(float realTimeRunTime) {
			RealTimeRunTime = realTimeRunTime;
		}
		public float getRealTimeRunTimeEfficiency() {
			return RealTimeRunTimeEfficiency;
		}
		public void setRealTimeRunTimeEfficiency(float realTimeRunTimeEfficiency) {
			RealTimeRunTimeEfficiency = realTimeRunTimeEfficiency;
		}
		public float getDailyPowerConsumption() {
			return DailyPowerConsumption;
		}
		public void setDailyPowerConsumption(float dailyPowerConsumption) {
			DailyPowerConsumption = dailyPowerConsumption;
		}
		public float getDailyReactivePowerConsumption() {
			return DailyReactivePowerConsumption;
		}
		public void setDailyReactivePowerConsumption(float dailyReactivePowerConsumption) {
			DailyReactivePowerConsumption = dailyReactivePowerConsumption;
		}
		public int getPoint() {
			return point;
		}
		public void setPoint(int point) {
			this.point = point;
		}
		public String getGtcjsj() {
			return gtcjsj;
		}
		public void setGtcjsj(String gtcjsj) {
			this.gtcjsj = gtcjsj;
		}
		public String getSaveTime() {
			return SaveTime;
		}
		public void setSaveTime(String saveTime) {
			SaveTime = saveTime;
		}
		public int getAcquisitionCycle() {
			return acquisitionCycle;
		}
		public void setAcquisitionCycle(int acquisitionCycle) {
			this.acquisitionCycle = acquisitionCycle;
		}
		public String getRealTimeCommRangeString() {
			return RealTimeCommRangeString;
		}
		public void setRealTimeCommRangeString(String realTimeCommRangeString) {
			RealTimeCommRangeString = realTimeCommRangeString;
		}
		public float getRealTimeCommTime() {
			return RealTimeCommTime;
		}
		public void setRealTimeCommTime(float realTimeCommTime) {
			RealTimeCommTime = realTimeCommTime;
		}
		public float getRealTimeCommTimeEfficiency() {
			return RealTimeCommTimeEfficiency;
		}
		public void setRealTimeCommTimeEfficiency(float realTimeCommTimeEfficiency) {
			RealTimeCommTimeEfficiency = realTimeCommTimeEfficiency;
		}
		public int getEgklx() {
			return egklx;
		}
		public void setEgklx(int egklx) {
			this.egklx = egklx;
		}
		public float getRPM() {
			return RPM;
		}
		public void setRPM(float rPM) {
			RPM = rPM;
		}
		public float getTorque() {
			return torque;
		}
		public void setTorque(float torque) {
			this.torque = torque;
		}
		public String getScrewPumpSaveTime() {
			return screwPumpSaveTime;
		}
		public void setScrewPumpSaveTime(String screwPumpSaveTime) {
			this.screwPumpSaveTime = screwPumpSaveTime;
		}
		public float getProducingfluidLevel() {
			return ProducingfluidLevel;
		}
		public void setProducingfluidLevel(float producingfluidLevel) {
			ProducingfluidLevel = producingfluidLevel;
		}
		public float getWaterCut() {
			return WaterCut;
		}
		public void setWaterCut(float waterCut) {
			WaterCut = waterCut;
		}
		public float getSetFrequency() {
			return SetFrequency;
		}
		public void setSetFrequency(float setFrequency) {
			SetFrequency = setFrequency;
		}
		public float getRunFrequency() {
			return RunFrequency;
		}
		public void setRunFrequency(float runFrequency) {
			RunFrequency = runFrequency;
		}
		public float getSPM() {
			return SPM;
		}
		public void setSPM(float sPM) {
			SPM = sPM;
		}
		public float getStroke() {
			return Stroke;
		}
		public void setStroke(float stroke) {
			Stroke = stroke;
		}
		public int getFSDiagramSetPointCount() {
			return FSDiagramSetPointCount;
		}
		public void setFSDiagramSetPointCount(int fSDiagramSetPointCount) {
			FSDiagramSetPointCount = fSDiagramSetPointCount;
		}
		public String getReadTime() {
			return ReadTime;
		}
		public void setReadTime(String readTime) {
			ReadTime = readTime;
		}
		public int getBalanceAutoControl() {
			return balanceAutoControl;
		}
		public void setBalanceAutoControl(int balanceAutoControl) {
			this.balanceAutoControl = balanceAutoControl;
		}
		public int getSpmAutoControl() {
			return spmAutoControl;
		}
		public void setSpmAutoControl(int spmAutoControl) {
			this.spmAutoControl = spmAutoControl;
		}
		public int getBalanceFrontLimit() {
			return balanceFrontLimit;
		}
		public void setBalanceFrontLimit(int balanceFrontLimit) {
			this.balanceFrontLimit = balanceFrontLimit;
		}
		public int getBalanceAfterLimit() {
			return balanceAfterLimit;
		}
		public void setBalanceAfterLimit(int balanceAfterLimit) {
			this.balanceAfterLimit = balanceAfterLimit;
		}
		public int getBalanceControlMode() {
			return balanceControlMode;
		}
		public void setBalanceControlMode(int balanceControlMode) {
			this.balanceControlMode = balanceControlMode;
		}
		public int getBalanceCalculateMode() {
			return balanceCalculateMode;
		}
		public void setBalanceCalculateMode(int balanceCalculateMode) {
			this.balanceCalculateMode = balanceCalculateMode;
		}
		public int getBalanceAwayTime() {
			return balanceAwayTime;
		}
		public void setBalanceAwayTime(int balanceAwayTime) {
			this.balanceAwayTime = balanceAwayTime;
		}
		public int getBalanceCloseTime() {
			return balanceCloseTime;
		}
		public void setBalanceCloseTime(int balanceCloseTime) {
			this.balanceCloseTime = balanceCloseTime;
		}
		public int getBalanceStrokeCount() {
			return balanceStrokeCount;
		}
		public void setBalanceStrokeCount(int balanceStrokeCount) {
			this.balanceStrokeCount = balanceStrokeCount;
		}
		public int getBalanceOperationUpLimit() {
			return balanceOperationUpLimit;
		}
		public void setBalanceOperationUpLimit(int balanceOperationUpLimit) {
			this.balanceOperationUpLimit = balanceOperationUpLimit;
		}
		public int getBalanceOperationDownLimit() {
			return balanceOperationDownLimit;
		}
		public void setBalanceOperationDownLimit(int balanceOperationDownLimit) {
			this.balanceOperationDownLimit = balanceOperationDownLimit;
		}
		public int getBalanceAwayTimePerBeat() {
			return balanceAwayTimePerBeat;
		}
		public void setBalanceAwayTimePerBeat(int balanceAwayTimePerBeat) {
			this.balanceAwayTimePerBeat = balanceAwayTimePerBeat;
		}
		public int getBalanceCloseTimePerBeat() {
			return balanceCloseTimePerBeat;
		}
		public void setBalanceCloseTimePerBeat(int balanceCloseTimePerBeat) {
			this.balanceCloseTimePerBeat = balanceCloseTimePerBeat;
		}
	}
	
	public static class UnitData{
		public  String wellName;
		public int liftingType;
		public int runStatusControl=0;//0-无操作,1-开机,2-关机
		public int FSDiagramIntervalControl=0;//设置功图采集控制
		public float FrequencyControl=0;//变频频率控制
		
		public int balanceControlModeControl=-999999999;//设置平衡调节远程触发状态
		public int balanceCalculateModeControl=-999999999;//设置平衡计算方式
		public int balanceAwayTimeControl=0;//设置重心远离支点调节时间
		public int balanceCloseTimeControl=0;//设置重心接近支点调节时间
		public int balanceStrokeCountControl=0;//设置功图采集控制
		public int balanceOperationUpLimitControl=0;//设置功图采集控制
		public int balanceOperationDownLimitControl=0;//设置功图采集控制
		public int balanceAwayTimePerBeatControl=0;//设置重心远离支点每拍调节时间
		public int balanceCloseTimePerBeatControl=0;//设置重心接近支点每拍调节时间
		
		public int DiscreteIntervalControl=0;//离散数据采集间隔设置
		
		public int CurrentUpLimitControl=-999999999;//电流上限设置
		public int CurrentDownLimitControl=-999999999;//电流下限设置
		public int PowerUpLimitControl=-999999999;//功率上限设置
		public int PowerDownLimitControl=-999999999;//功率下限设置
		public int ImmediatelyAcquisitionControl=-999999999;//离散数据即时采集设置
		
		public  String deviceAddr;
		public  String deviceId;
		public  String protocolName;
		public  int protocol;
		
		public  String diagramAcqTime;
		public int commStatus;
		public  String lastDisAcqTime;
		public int lastCommStatus;
		public float lastCommTime;
		public float lastCommTimeEfficiency;
		public String lastCommRange;
		public int lastRunStatus;
		public float lastRunTime;
		public float lastRunTimeEfficiency;
		public String lastRunRange;
		public float lastTotalKWattH;
		public float lastTotalPKWattH;
		public float lastTotalNKWattH;
		public float lastTotalKVarH;
		public float lastTotalPKVarH;
		public float lastTotalNKVarH;
		public float lastTotalKVAH;
		public float lastTodayKWattH;
		public float lastTodayPKWattH;
		public float lastTodayNKWattH;
		public float lastTodayKVarH;
		public float lastTodayPKVarH;
		public float lastTodayNKVarH;
		public float lastTodayKVAH;
		public float lastRPM=0.0f;
		
		public int runTimeEfficiencySource;
		public  int UnitId;
		public int recvPackageCount=0;
		public int recvPackageSize=0;
		public int sendPackageCount=0;
		public int sendPackageSize=0;
		public String currentDate=StringManagerUtils.getCurrentTime();//判断是否跨天
		public AcquisitionData acquisitionData=null;
		public RTUDriveConfig rtuDriveConfig;
		public AcquisitionUnitData acquisitionUnitData;
		
		public float getLastTotalKWattH() {
			return lastTotalKWattH;
		}
		public void setLastTotalKWattH(float lastTotalKWattH) {
			this.lastTotalKWattH = lastTotalKWattH;
		}
		public float getLastTotalPKWattH() {
			return lastTotalPKWattH;
		}
		public void setLastTotalPKWattH(float lastTotalPKWattH) {
			this.lastTotalPKWattH = lastTotalPKWattH;
		}
		public float getLastTotalNKWattH() {
			return lastTotalNKWattH;
		}
		public void setLastTotalNKWattH(float lastTotalNKWattH) {
			this.lastTotalNKWattH = lastTotalNKWattH;
		}
		public float getLastTotalKVarH() {
			return lastTotalKVarH;
		}
		public void setLastTotalKVarH(float lastTotalKVarH) {
			this.lastTotalKVarH = lastTotalKVarH;
		}
		public float getLastTotalPKVarH() {
			return lastTotalPKVarH;
		}
		public void setLastTotalPKVarH(float lastTotalPKVarH) {
			this.lastTotalPKVarH = lastTotalPKVarH;
		}
		public float getLastTotalNKVarH() {
			return lastTotalNKVarH;
		}
		public void setLastTotalNKVarH(float lastTotalNKVarH) {
			this.lastTotalNKVarH = lastTotalNKVarH;
		}
		public float getLastTotalKVAH() {
			return lastTotalKVAH;
		}
		public void setLastTotalKVAH(float lastTotalKVAH) {
			this.lastTotalKVAH = lastTotalKVAH;
		}
		public float getLastTodayKWattH() {
			return lastTodayKWattH;
		}
		public void setLastTodayKWattH(float lastTodayKWattH) {
			this.lastTodayKWattH = lastTodayKWattH;
		}
		public float getLastTodayPKWattH() {
			return lastTodayPKWattH;
		}
		public void setLastTodayPKWattH(float lastTodayPKWattH) {
			this.lastTodayPKWattH = lastTodayPKWattH;
		}
		public float getLastTodayNKWattH() {
			return lastTodayNKWattH;
		}
		public void setLastTodayNKWattH(float lastTodayNKWattH) {
			this.lastTodayNKWattH = lastTodayNKWattH;
		}
		public float getLastTodayKVarH() {
			return lastTodayKVarH;
		}
		public void setLastTodayKVarH(float lastTodayKVarH) {
			this.lastTodayKVarH = lastTodayKVarH;
		}
		public float getLastTodayPKVarH() {
			return lastTodayPKVarH;
		}
		public void setLastTodayPKVarH(float lastTodayPKVarH) {
			this.lastTodayPKVarH = lastTodayPKVarH;
		}
		public float getLastTodayNKVarH() {
			return lastTodayNKVarH;
		}
		public void setLastTodayNKVarH(float lastTodayNKVarH) {
			this.lastTodayNKVarH = lastTodayNKVarH;
		}
		public float getLastTodayKVAH() {
			return lastTodayKVAH;
		}
		public void setLastTodayKVAH(float lastTodayKVAH) {
			this.lastTodayKVAH = lastTodayKVAH;
		}
		public float getLastRPM() {
			return lastRPM;
		}
		public void setLastRPM(float lastRPM) {
			this.lastRPM = lastRPM;
		}
		public String getWellName() {
			return wellName;
		}
		public void setWellName(String wellName) {
			this.wellName = wellName;
		}
		public int getLiftingType() {
			return liftingType;
		}
		public void setLiftingType(int liftingType) {
			this.liftingType = liftingType;
		}
		public int getRunStatusControl() {
			return runStatusControl;
		}
		public void setRunStatusControl(int runStatusControl) {
			this.runStatusControl = runStatusControl;
		}
		public int getFSDiagramIntervalControl() {
			return FSDiagramIntervalControl;
		}
		public void setFSDiagramIntervalControl(int fSDiagramIntervalControl) {
			FSDiagramIntervalControl = fSDiagramIntervalControl;
		}
		public float getFrequencyControl() {
			return FrequencyControl;
		}
		public void setFrequencyControl(float frequencyControl) {
			FrequencyControl = frequencyControl;
		}
		
		public int getCommStatus() {
			return commStatus;
		}
		public void setCommStatus(int commStatus) {
			this.commStatus = commStatus;
		}
		public int getRunTimeEfficiencySource() {
			return runTimeEfficiencySource;
		}
		public void setRunTimeEfficiencySource(int runTimeEfficiencySource) {
			this.runTimeEfficiencySource = runTimeEfficiencySource;
		}
		public int getUnitId() {
			return UnitId;
		}
		public void setUnitId(int unitId) {
			UnitId = unitId;
		}
		public int getRecvPackageCount() {
			return recvPackageCount;
		}
		public void setRecvPackageCount(int recvPackageCount) {
			this.recvPackageCount = recvPackageCount;
		}
		public int getRecvPackageSize() {
			return recvPackageSize;
		}
		public void setRecvPackageSize(int recvPackageSize) {
			this.recvPackageSize = recvPackageSize;
		}
		public int getSendPackageCount() {
			return sendPackageCount;
		}
		public void setSendPackageCount(int sendPackageCount) {
			this.sendPackageCount = sendPackageCount;
		}
		public int getSendPackageSize() {
			return sendPackageSize;
		}
		public void setSendPackageSize(int sendPackageSize) {
			this.sendPackageSize = sendPackageSize;
		}
		public String getCurrentDate() {
			return currentDate;
		}
		public void setCurrentDate(String currentDate) {
			this.currentDate = currentDate;
		}
		public AcquisitionData getAcquisitionData() {
			return acquisitionData;
		}
		public void setAcquisitionData(AcquisitionData acquisitionData) {
			this.acquisitionData = acquisitionData;
		}
		public RTUDriveConfig getRtuDriveConfig() {
			return rtuDriveConfig;
		}
		public void setRtuDriveConfig(RTUDriveConfig rtuDriveConfig) {
			this.rtuDriveConfig = rtuDriveConfig;
		}
		public AcquisitionUnitData getAcquisitionUnitData() {
			return acquisitionUnitData;
		}
		public void setAcquisitionUnitData(AcquisitionUnitData acquisitionUnitData) {
			this.acquisitionUnitData = acquisitionUnitData;
		}
		public String getDiagramAcqTime() {
			return diagramAcqTime;
		}
		public void setDiagramAcqTime(String diagramAcqTime) {
			this.diagramAcqTime = diagramAcqTime;
		}
		public int getBalanceControlModeControl() {
			return balanceControlModeControl;
		}
		public void setBalanceControlModeControl(int balanceControlModeControl) {
			this.balanceControlModeControl = balanceControlModeControl;
		}
		public int getBalanceCalculateModeControl() {
			return balanceCalculateModeControl;
		}
		public void setBalanceCalculateModeControl(int balanceCalculateModeControl) {
			this.balanceCalculateModeControl = balanceCalculateModeControl;
		}
		public int getBalanceAwayTimeControl() {
			return balanceAwayTimeControl;
		}
		public void setBalanceAwayTimeControl(int balanceAwayTimeControl) {
			this.balanceAwayTimeControl = balanceAwayTimeControl;
		}
		public int getBalanceCloseTimeControl() {
			return balanceCloseTimeControl;
		}
		public void setBalanceCloseTimeControl(int balanceCloseTimeControl) {
			this.balanceCloseTimeControl = balanceCloseTimeControl;
		}
		public int getBalanceStrokeCountControl() {
			return balanceStrokeCountControl;
		}
		public void setBalanceStrokeCountControl(int balanceStrokeCountControl) {
			this.balanceStrokeCountControl = balanceStrokeCountControl;
		}
		public int getBalanceOperationUpLimitControl() {
			return balanceOperationUpLimitControl;
		}
		public void setBalanceOperationUpLimitControl(int balanceOperationUpLimitControl) {
			this.balanceOperationUpLimitControl = balanceOperationUpLimitControl;
		}
		public int getBalanceOperationDownLimitControl() {
			return balanceOperationDownLimitControl;
		}
		public void setBalanceOperationDownLimitControl(int balanceOperationDownLimitControl) {
			this.balanceOperationDownLimitControl = balanceOperationDownLimitControl;
		}
		public int getBalanceAwayTimePerBeatControl() {
			return balanceAwayTimePerBeatControl;
		}
		public void setBalanceAwayTimePerBeatControl(int balanceAwayTimePerBeatControl) {
			this.balanceAwayTimePerBeatControl = balanceAwayTimePerBeatControl;
		}
		public int getBalanceCloseTimePerBeatControl() {
			return balanceCloseTimePerBeatControl;
		}
		public void setBalanceCloseTimePerBeatControl(int balanceCloseTimePerBeatControl) {
			this.balanceCloseTimePerBeatControl = balanceCloseTimePerBeatControl;
		}
		public float getLastCommTime() {
			return lastCommTime;
		}
		public void setLastCommTime(float lastCommTime) {
			this.lastCommTime = lastCommTime;
		}
		public float getLastCommTimeEfficiency() {
			return lastCommTimeEfficiency;
		}
		public void setLastCommTimeEfficiency(float lastCommTimeEfficiency) {
			this.lastCommTimeEfficiency = lastCommTimeEfficiency;
		}
		public String getLastCommRange() {
			return lastCommRange;
		}
		public void setLastCommRange(String lastCommRange) {
			this.lastCommRange = lastCommRange;
		}
		public int getLastRunStatus() {
			return lastRunStatus;
		}
		public void setLastRunStatus(int lastRunStatus) {
			this.lastRunStatus = lastRunStatus;
		}
		public float getLastRunTime() {
			return lastRunTime;
		}
		public void setLastRunTime(float lastRunTime) {
			this.lastRunTime = lastRunTime;
		}
		public float getLastRunTimeEfficiency() {
			return lastRunTimeEfficiency;
		}
		public void setLastRunTimeEfficiency(float lastRunTimeEfficiency) {
			this.lastRunTimeEfficiency = lastRunTimeEfficiency;
		}
		public String getLastRunRange() {
			return lastRunRange;
		}
		public void setLastRunRange(String lastRunRange) {
			this.lastRunRange = lastRunRange;
		}
		public String getLastDisAcqTime() {
			return lastDisAcqTime;
		}
		public void setLastDisAcqTime(String lastDisAcqTime) {
			this.lastDisAcqTime = lastDisAcqTime;
		}
		public int getLastCommStatus() {
			return lastCommStatus;
		}
		public void setLastCommStatus(int lastCommStatus) {
			this.lastCommStatus = lastCommStatus;
		}
		public int getDiscreteIntervalControl() {
			return DiscreteIntervalControl;
		}
		public void setDiscreteIntervalControl(int discreteIntervalControl) {
			DiscreteIntervalControl = discreteIntervalControl;
		}
		public int getCurrentUpLimitControl() {
			return CurrentUpLimitControl;
		}
		public void setCurrentUpLimitControl(int currentUpLimitControl) {
			CurrentUpLimitControl = currentUpLimitControl;
		}
		public int getCurrentDownLimitControl() {
			return CurrentDownLimitControl;
		}
		public void setCurrentDownLimitControl(int currentDownLimitControl) {
			CurrentDownLimitControl = currentDownLimitControl;
		}
		public int getPowerUpLimitControl() {
			return PowerUpLimitControl;
		}
		public void setPowerUpLimitControl(int powerUpLimitControl) {
			PowerUpLimitControl = powerUpLimitControl;
		}
		public int getPowerDownLimitControl() {
			return PowerDownLimitControl;
		}
		public void setPowerDownLimitControl(int powerDownLimitControl) {
			PowerDownLimitControl = powerDownLimitControl;
		}
		public int getImmediatelyAcquisitionControl() {
			return ImmediatelyAcquisitionControl;
		}
		public void setImmediatelyAcquisitionControl(int immediatelyAcquisitionControl) {
			ImmediatelyAcquisitionControl = immediatelyAcquisitionControl;
		}
		public int getProtocol() {
			return protocol;
		}
		public void setProtocol(int protocol) {
			this.protocol = protocol;
		}
		public String getDeviceAddr() {
			return deviceAddr;
		}
		public void setDeviceAddr(String deviceAddr) {
			this.deviceAddr = deviceAddr;
		}
		public String getDeviceId() {
			return deviceId;
		}
		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		public String getProtocolName() {
			return protocolName;
		}
		public void setProtocolName(String protocolName) {
			this.protocolName = protocolName;
		}
	}
	
	public static class ClientUnit{
		public  List<UnitData> unitDataList=new ArrayList<UnitData>();
		public  Socket socket=null;
		public  int sign=0;//连接标志
		public  ProtocolBasicThread thread=null;
		public  String revData="";
		public List<UnitData> getUnitDataList() {
			return unitDataList;
		}
		public void setUnitDataList(List<UnitData> unitDataList) {
			this.unitDataList = unitDataList;
		}
		public Socket getSocket() {
			return socket;
		}
		public void setSocket(Socket socket) {
			this.socket = socket;
		}
		public int getSign() {
			return sign;
		}
		public void setSign(int sign) {
			this.sign = sign;
		}
		public Thread getThread() {
			return thread;
		}
		public void setThread(ProtocolBasicThread thread) {
			this.thread = thread;
		}
		public String getRevData() {
			return revData;
		}
		public void setRevData(String revData) {
			this.revData = revData;
		}
	}
}
