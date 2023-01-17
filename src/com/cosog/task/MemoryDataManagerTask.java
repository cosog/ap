package com.cosog.task;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cosog.model.AccessToken;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.DataMapping;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.AcqInstanceOwnItem;
import com.cosog.model.calculate.AcqInstanceOwnItem.AcqItem;
import com.cosog.model.calculate.AlarmInstanceOwnItem;
import com.cosog.model.calculate.AlarmInstanceOwnItem.AlarmItem;
import com.cosog.model.calculate.DisplayInstanceOwnItem;
import com.cosog.model.calculate.DisplayInstanceOwnItem.DisplayItem;
import com.cosog.model.calculate.RPCCalculateRequestData.Balance;
import com.cosog.model.calculate.PCPCalculateResponseData;
import com.cosog.model.drive.AcquisitionItemInfo;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.PCPDeviceTodayData;
import com.cosog.model.calculate.PCPProductionData;
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.RPCCalculateResponseData;
import com.cosog.model.calculate.RPCDeviceInfo;
import com.cosog.model.calculate.RPCDeviceTodayData;
import com.cosog.model.calculate.RPCProductionData;
import com.cosog.model.calculate.UserInfo;
import com.cosog.utils.Config;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import redis.clients.jedis.Jedis;

@Component("LoadingMemoryData")  
public class MemoryDataManagerTask {

	private static MemoryDataManagerTask instance=new MemoryDataManagerTask();
	
	public static MemoryDataManagerTask getInstance(){
		return instance;
	}
	
//	@Scheduled(fixedRate = 1000*60*60*24*365*100)
	public static void loadMemoryData(){
		cleanData();
		
		loadProtocolConfig();
		
		loadAcqInstanceOwnItemById("","update");
		loadAlarmInstanceOwnItemById("","update");
		loadDisplayInstanceOwnItemById("","update");
		
		loadRPCDeviceInfo(null,0,"update");
		loadPCPDeviceInfo(null,0,"update");
		
		loadTodayFESDiagram(null,0);
		loadTodayRPMData(null,0);
	}
	
	public static void cleanData(){
		Jedis jedis=null;
		try{
			jedis = RedisUtil.jedisPool.getResource();
			jedis.del("modbusProtocolConfig".getBytes());
			jedis.del("ProtocolMappingColumn".getBytes());
			jedis.del("RPCDeviceInfo".getBytes());
			jedis.del("RPCDeviceTodayData".getBytes());
			jedis.del("PCPDeviceInfo".getBytes());
			jedis.del("PCPDeviceTodayData".getBytes());
			jedis.del("AcqInstanceOwnItem".getBytes());
			jedis.del("DisplayInstanceOwnItem".getBytes());
			jedis.del("AlarmInstanceOwnItem".getBytes());
			jedis.del("rpcCalItemList".getBytes());
			jedis.del("pcpCalItemList".getBytes());
			jedis.del("UserInfo".getBytes());
			jedis.del("RPCWorkType".getBytes());
			jedis.del("RPCWorkTypeByName".getBytes());
			jedis.del("AlarmShowStyle".getBytes());
			jedis.del("UIKitAccessToken".getBytes());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null && jedis.isConnected() ){
				jedis.close();
			}
		}
	}
	
	
	@SuppressWarnings("static-access")
	public static void loadProtocolConfig(){
		StringManagerUtils.printLog("驱动加载开始");
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		Gson gson = new Gson();
		String path="";
		String protocolConfigData="";
		java.lang.reflect.Type type=null;
		//添加Modbus协议配置
		path=stringManagerUtils.getFilePath("modbus.json","protocol/");
		protocolConfigData=stringManagerUtils.readFile(path,"utf-8");
		type = new TypeToken<ModbusProtocolConfig>() {}.getType();
		ModbusProtocolConfig modbusProtocolConfig=gson.fromJson(protocolConfigData, type);
		if(modbusProtocolConfig==null){
			modbusProtocolConfig=new ModbusProtocolConfig();
			modbusProtocolConfig.setProtocol(new ArrayList<ModbusProtocolConfig.Protocol>());
		}else if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()==null){
			modbusProtocolConfig.setProtocol(new ArrayList<ModbusProtocolConfig.Protocol>());
		}else if(modbusProtocolConfig!=null&&modbusProtocolConfig.getProtocol()!=null&&modbusProtocolConfig.getProtocol().size()>0){
			Collections.sort(modbusProtocolConfig.getProtocol());
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				Collections.sort(modbusProtocolConfig.getProtocol().get(i).getItems());
			}
		}
		
		
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			jedis.set("modbusProtocolConfig".getBytes(), SerializeObjectUnils.serialize(modbusProtocolConfig));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		StringManagerUtils.printLog("驱动加载结束");
	}
	
	public static void loadProtocolMappingColumn(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String sql="select t.id,t.name,t.mappingcolumn,t.calcolumn,t.protocoltype,t.mappingmode,t.repetitiontimes from TBL_DATAMAPPING t order by t.protocoltype,t.id";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				DataMapping dataMapping=new DataMapping();
				dataMapping.setId(rs.getInt(1));
				dataMapping.setName(rs.getString(2));
				dataMapping.setMappingColumn(rs.getString(3));
				dataMapping.setCalColumn(rs.getString(4));
				dataMapping.setProtocolType(rs.getInt(5));
				dataMapping.setMappingMode(rs.getInt(6));
				dataMapping.setRepetitionTimes(rs.getInt(7));
				String key=dataMapping.getProtocolType()+"_"+dataMapping.getMappingColumn();
				jedis.hset("ProtocolMappingColumn".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(dataMapping));//哈希(Hash)
			}
//			Set<String> aa=jedis.hkeys("ProtocolMappingColumn");
//			Set<byte[]> bb=jedis.hkeys("ProtocolMappingColumn".getBytes());
//			System.out.println("ProtocolMappingColumn中所有的key:"+aa);
//			System.out.println("ProtocolMappingColumn中所有的key:"+bb);
//			byte[] testKey=null;
//			for (byte[] str : bb) {
//				testKey=str;
//				break;
//			}
//			
//			System.out.println("ProtocolMappingColumn中所有的值:"+jedis.hvals("ProtocolMappingColumn".getBytes()));
//			System.out.println("ProtocolMappingColumn中所有的值:"+jedis.hvals("ProtocolMappingColumn"));
//			
//			
//			System.out.println("判断某个key是否存在:"+jedis.hexists("ProtocolMappingColumn".getBytes(), testKey));
//			System.out.println("判断某个key的值:"+jedis.hget("ProtocolMappingColumn".getBytes(), testKey));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadRPCDeviceInfo(List<String> wellList,int condition,String method){//condition 0 -设备ID 1-设备名称
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String wells="";
			if(condition==0){
				wells=StringUtils.join(wellList, ",");
			}else{
				wells=StringManagerUtils.joinStringArr2(wellList, ",");
			}
			if("delete".equalsIgnoreCase(method)){
				if(condition==0){
					for(int i=0;i<wellList.size();i++){
						jedis.hdel("RPCDeviceInfo".getBytes(), wellList.get(i).getBytes());
						jedis.hdel("RPCDeviceTodayData".getBytes(), wellList.get(i).getBytes());
					}
				}else if(condition==1){
					for(int i=0;i<wellList.size();i++){
						if(jedis.exists("RPCDeviceInfo".getBytes())){
							List<byte[]> rpcDeviceInfoByteList =jedis.hvals("RPCDeviceInfo".getBytes());
							for(int j=0;j<rpcDeviceInfoByteList.size();j++){
								RPCDeviceInfo rpcDeviceInfo=(RPCDeviceInfo)SerializeObjectUnils.unserizlize(rpcDeviceInfoByteList.get(i));
								if(wellList.get(i).equalsIgnoreCase(rpcDeviceInfo.getWellName())){
									jedis.hdel("RPCDeviceInfo".getBytes(), (rpcDeviceInfo.getId()+"").getBytes());
									jedis.hdel("RPCDeviceTodayData".getBytes(), (rpcDeviceInfo.getId()+"").getBytes());
									break;
								}
							}
						}
					}
				}
			}else{
				String sql="select t.id,t.orgid,t.orgName,t.wellname,t.devicetype,t.devicetypename,t.applicationscenarios,t.applicationScenariosName,"
						+ "t.tcptype,t.signinid,t.ipport,t.slave,t.peakdelay,"
						+ "t.videourl,"
						+ "t.instancecode,t.instancename,t.alarminstancecode,t.alarminstancename,t.displayinstancecode,t.displayinstancename,"
						+ "t.status,t.statusName,"
						+ "t.productiondata,t.balanceinfo,t.stroke,t.levelcorrectvalue,"
						+ "t.pumpingmodelid,"
						+ "t.manufacturer,t.model,t.crankrotationdirection,t.offsetangleofcrank,t.crankgravityradius,t.singlecrankweight,t.singlecrankpinweight,t.structuralunbalance,"
						+ "t.sortnum,"
						+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'),"
						+ "t2.commstatus,t2.commtime,t2.commtimeefficiency,t2.commrange,"
						+ "t2.runstatus,t2.runtime,t2.runtimeefficiency,t2.runrange,"
						+ "t2.totalkwatth,t2.todaykwatth,"
						+ " t2.resultstatus,decode(t2.resultcode,null,0,t2.resultcode) as resultcode"
						+ " from viw_rpcdevice t"
						+ " left outer join tbl_rpcacqdata_latest t2 on t2.wellid=t.id "
						+ " where 1=1 ";
				if(StringManagerUtils.isNotNull(wells)){
					if(condition==0){
						sql+=" and t.id in("+wells+")";
					}else{
						sql+=" and t.wellName in("+wells+")";
					}
				}
				sql+=" order by t.sortNum,t.wellName";
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					RPCDeviceInfo rpcDeviceInfo=new RPCDeviceInfo();
					rpcDeviceInfo.setId(rs.getInt(1));
					rpcDeviceInfo.setOrgId(rs.getInt(2));
					rpcDeviceInfo.setOrgName(rs.getString(3));
					rpcDeviceInfo.setWellName(rs.getString(4));
					rpcDeviceInfo.setDeviceType(rs.getInt(5));
					rpcDeviceInfo.setDeviceTypeName(rs.getString(6));
					rpcDeviceInfo.setApplicationScenarios(rs.getInt(7));
					rpcDeviceInfo.setApplicationScenariosName(rs.getString(8));
					rpcDeviceInfo.setTcpType(rs.getString(9)+"");
					rpcDeviceInfo.setSignInId(rs.getString(10)+"");
					rpcDeviceInfo.setIpPort(rs.getString(11)+"");
					rpcDeviceInfo.setSlave(rs.getString(12)+"");
					rpcDeviceInfo.setPeakDelay(rs.getInt(13));
					
					rpcDeviceInfo.setVideoUrl(rs.getString(14)+"");
					rpcDeviceInfo.setInstanceCode(rs.getString(15)+"");
					rpcDeviceInfo.setInstanceName(rs.getString(16)+"");
					rpcDeviceInfo.setAlarmInstanceCode(rs.getString(17)+"");
					rpcDeviceInfo.setAlarmInstanceName(rs.getString(18)+"");
					rpcDeviceInfo.setDisplayInstanceCode(rs.getString(19)+"");
					rpcDeviceInfo.setDisplayInstanceName(rs.getString(20)+"");
					rpcDeviceInfo.setStatus(rs.getInt(21));
					rpcDeviceInfo.setStatusName(rs.getString(22)+"");
					String productionData=rs.getString(23)+"";
					String balanceInfo=rs.getString(24)+"";
					float stroke=rs.getFloat(25);
					float levelCorrectValue=rs.getFloat(26);
					
					int pumpingModelId=rs.getInt(27);
					rpcDeviceInfo.setPumpingModelId(pumpingModelId);
					if(StringManagerUtils.isNotNull(productionData)){
						type = new TypeToken<RPCDeviceInfo>() {}.getType();
						RPCDeviceInfo rpcProductionData=gson.fromJson(productionData, type);
						rpcDeviceInfo.setFluidPVT(rpcProductionData.getFluidPVT());
						rpcDeviceInfo.setReservoir(rpcProductionData.getReservoir());
						rpcDeviceInfo.setTubingString(rpcProductionData.getTubingString());
						rpcDeviceInfo.setCasingString(rpcProductionData.getCasingString());
						rpcDeviceInfo.setRodString(rpcProductionData.getRodString());
						rpcDeviceInfo.setPump(rpcProductionData.getPump());
						rpcDeviceInfo.setProduction(rpcProductionData.getProduction());
						if(rpcDeviceInfo.getProduction()!=null){
							rpcDeviceInfo.getProduction().setLevelCorrectValue(levelCorrectValue);
						}
						rpcDeviceInfo.setManualIntervention(rpcProductionData.getManualIntervention());
						if(pumpingModelId>0){
							rpcDeviceInfo.setPumpingUnit(new RPCCalculateRequestData.PumpingUnit());
							rpcDeviceInfo.getPumpingUnit().setManufacturer(rs.getString(28)+"");
							rpcDeviceInfo.getPumpingUnit().setModel(rs.getString(29)+"");
							rpcDeviceInfo.getPumpingUnit().setStroke(stroke);
							rpcDeviceInfo.getPumpingUnit().setCrankRotationDirection(rs.getString(30)+"");
							rpcDeviceInfo.getPumpingUnit().setOffsetAngleOfCrank(rs.getFloat(31));
							rpcDeviceInfo.getPumpingUnit().setCrankGravityRadius(rs.getFloat(32));
							rpcDeviceInfo.getPumpingUnit().setSingleCrankWeight(rs.getFloat(33));
							rpcDeviceInfo.getPumpingUnit().setSingleCrankPinWeight(rs.getFloat(34));
							rpcDeviceInfo.getPumpingUnit().setStructuralUnbalance(rs.getFloat(35));
							type = new TypeToken<RPCCalculateRequestData.Balance>() {}.getType();
							RPCCalculateRequestData.Balance balance=gson.fromJson(balanceInfo, type);
							if(balance!=null){
								rpcDeviceInfo.getPumpingUnit().setBalance(balance);
							}
						}else{
							rpcDeviceInfo.setPumpingUnit(null);
						}
					}else{
						rpcDeviceInfo.setFluidPVT(null);
						rpcDeviceInfo.setReservoir(null);
						rpcDeviceInfo.setRodString(null);
						rpcDeviceInfo.setTubingString(null);
						rpcDeviceInfo.setCasingString(null);
						rpcDeviceInfo.setPump(null);
						rpcDeviceInfo.setProduction(null);
						rpcDeviceInfo.setPumpingUnit(null);
						rpcDeviceInfo.setManualIntervention(null);
					}
					rpcDeviceInfo.setSortNum(rs.getInt(36));
					
					rpcDeviceInfo.setAcqTime(rs.getString(37));
					rpcDeviceInfo.setSaveTime("");
					
					rpcDeviceInfo.setCommStatus(rs.getInt(38));
					rpcDeviceInfo.setCommTime(rs.getFloat(39));
					rpcDeviceInfo.setCommEff(rs.getFloat(40));
					rpcDeviceInfo.setCommRange(StringManagerUtils.CLOBtoString2(rs.getClob(41)));
					
					rpcDeviceInfo.setOnLineAcqTime(rs.getString(37));
					rpcDeviceInfo.setOnLineCommStatus(rs.getInt(38));
					rpcDeviceInfo.setOnLineCommTime(rs.getFloat(39));
					rpcDeviceInfo.setOnLineCommEff(rs.getFloat(40));
					rpcDeviceInfo.setOnLineCommRange(StringManagerUtils.CLOBtoString2(rs.getClob(41)));
					
					rpcDeviceInfo.setRunStatus(rs.getInt(42));
					rpcDeviceInfo.setRunTime(rs.getFloat(43));
					rpcDeviceInfo.setRunEff(rs.getFloat(44));
					rpcDeviceInfo.setRunRange(StringManagerUtils.CLOBtoString2(rs.getClob(45)));
					
					rpcDeviceInfo.setTotalKWattH(rs.getFloat(46));
					rpcDeviceInfo.setTodayKWattH(rs.getFloat(47));
					
					rpcDeviceInfo.setResultStatus(rs.getInt(48));
					rpcDeviceInfo.setResultCode(rs.getInt(49));
					
					String key=rpcDeviceInfo.getId()+"";
					jedis.hset("RPCDeviceInfo".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(rpcDeviceInfo));//哈希(Hash)
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadRPCDeviceInfoByPumpingId(String pumpingModelId,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_rpcdevice t,tbl_pumpingmodel t2 where t.pumpingmodelid=t2.id and t2.id= "+pumpingModelId;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadRPCDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadRPCDeviceInfoByInstanceId(String instanceId,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_rpcdevice t,tbl_protocolinstance t2 where t.instancecode=t2.code and t2.id= "+instanceId;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadRPCDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadRPCDeviceInfoByInstanceCode(String instanceCode,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_rpcdevice t where t.instancecode= '"+instanceCode+"'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadRPCDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadRPCDeviceInfoByDisplayInstanceId(String instanceId,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_rpcdevice t,tbl_protocoldisplayinstance t2 where t.displayinstancecode=t2.code and t2.id= "+instanceId;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadRPCDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadRPCDeviceInfoByDisplayInstanceCode(String instanceCode,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_rpcdevice t where t.displayinstancecode= '"+instanceCode+"'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadRPCDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadRPCDeviceInfoByAlarmInstanceId(String instanceId,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_rpcdevice t,tbl_protocolalarminstance t2 where t.alarminstancecode=t2.code and t2.id= "+instanceId;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadRPCDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadRPCDeviceInfoByAlarmInstanceCode(String instanceCode,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_rpcdevice t where t.alarminstancecode= '"+instanceCode+"'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadRPCDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadPCPDeviceInfo(List<String> wellList,int condition,String method){//condition 0 -设备ID 1-设备名称
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String wells="";
			if(condition==0){
				wells=StringUtils.join(wellList, ",");
			}else{
				wells=StringManagerUtils.joinStringArr2(wellList, ",");
			}
			
			if("delete".equalsIgnoreCase(method)&&jedis.exists("PCPDeviceInfo".getBytes())){
				if(condition==0){
					for(int i=0;i<wellList.size();i++){
						if(jedis.hexists("PCPDeviceInfo".getBytes(), wellList.get(i).getBytes())){
							jedis.hdel("PCPDeviceInfo".getBytes(), wellList.get(i).getBytes());
							jedis.hdel("PCPDeviceTodayData".getBytes(), wellList.get(i).getBytes());
						}
					}
				}else if(condition==1&&jedis.exists("PCPDeviceInfo".getBytes())){
					for(int i=0;i<wellList.size();i++){
						List<byte[]> deviceInfoByteList =jedis.hvals("PCPDeviceInfo".getBytes());
						for(int j=0;j<deviceInfoByteList.size();j++){
							PCPDeviceInfo deviceInfo=(PCPDeviceInfo)SerializeObjectUnils.unserizlize(deviceInfoByteList.get(i));
							if(wellList.get(i).equalsIgnoreCase(deviceInfo.getWellName()) && jedis.hexists("PCPDeviceInfo".getBytes(), (deviceInfo.getId()+"").getBytes())){
								jedis.hdel("PCPDeviceInfo".getBytes(), (deviceInfo.getId()+"").getBytes());
								jedis.hdel("PCPDeviceTodayData".getBytes(), (deviceInfo.getId()+"").getBytes());
								break;
							}
						}
					}
				}
				return;
			}
			
			String sql="select t.id,t.orgid,t.orgName,t.wellname,t.devicetype,t.devicetypename,t.applicationscenarios,t.applicationScenariosName,"
					+ "t.tcptype,t.signinid,t.ipport,t.slave,t.peakdelay,"
					+ "t.videourl,"
					+ "t.instancecode,t.instancename,t.alarminstancecode,t.alarminstancename,t.displayinstancecode,t.displayinstancename,"
					+ "t.status,t.statusName,"
					+ "t.productiondata,"
					+ "t.sortnum, "
					+ "to_char(t2.acqtime,'yyyy-mm-dd hh24:mi:ss'),"
					+ "t2.commstatus,t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ "t2.runstatus,t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ "t2.totalkwatth,t2.todaykwatth "
					+ " from viw_pcpdevice t"
					+ " left outer join tbl_pcpacqdata_latest t2 on t2.wellid=t.id "
					+ " where 1=1 ";
			if(StringManagerUtils.isNotNull(wells)){
				if(condition==0){
					sql+=" and t.id in("+wells+")";
				}else{
					sql+=" and t.wellName in("+wells+")";
				}
			}
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				PCPDeviceInfo pcpDeviceInfo=new PCPDeviceInfo();
				pcpDeviceInfo.setId(rs.getInt(1));
				pcpDeviceInfo.setOrgId(rs.getInt(2));
				pcpDeviceInfo.setOrgName(rs.getString(3));
				pcpDeviceInfo.setWellName(rs.getString(4));
				pcpDeviceInfo.setDeviceType(rs.getInt(5));
				pcpDeviceInfo.setDeviceTypeName(rs.getString(6)+"");
				pcpDeviceInfo.setApplicationScenarios(rs.getInt(7));
				pcpDeviceInfo.setApplicationScenariosName(rs.getString(8)+"");
				pcpDeviceInfo.setTcpType(rs.getString(9)+"");
				pcpDeviceInfo.setSignInId(rs.getString(10)+"");
				pcpDeviceInfo.setIpPort(rs.getString(11)+"");
				pcpDeviceInfo.setSlave(rs.getString(12)+"");
				pcpDeviceInfo.setPeakDelay(rs.getInt(13));
				
				pcpDeviceInfo.setVideoUrl(rs.getString(14)+"");
				pcpDeviceInfo.setInstanceCode(rs.getString(15)+"");
				pcpDeviceInfo.setInstanceName(rs.getString(16)+"");
				pcpDeviceInfo.setAlarmInstanceCode(rs.getString(17)+"");
				pcpDeviceInfo.setAlarmInstanceName(rs.getString(18)+"");
				pcpDeviceInfo.setDisplayInstanceCode(rs.getString(19)+"");
				pcpDeviceInfo.setDisplayInstanceName(rs.getString(20)+"");
				pcpDeviceInfo.setStatus(rs.getInt(21));
				pcpDeviceInfo.setStatusName(rs.getString(22)+"");
				String productionData=rs.getString(23);
				if(StringManagerUtils.isNotNull(productionData)){
					type = new TypeToken<PCPDeviceInfo>() {}.getType();
					PCPDeviceInfo pcpProductionData=gson.fromJson(productionData, type);
					if(pcpProductionData!=null){
						pcpDeviceInfo.setFluidPVT(pcpProductionData.getFluidPVT());
						pcpDeviceInfo.setReservoir(pcpProductionData.getReservoir());
						pcpDeviceInfo.setTubingString(pcpProductionData.getTubingString());
						pcpDeviceInfo.setCasingString(pcpProductionData.getCasingString());
						pcpDeviceInfo.setRodString(pcpProductionData.getRodString());
						pcpDeviceInfo.setPump(pcpProductionData.getPump());
						pcpDeviceInfo.setProduction(pcpProductionData.getProduction());
						pcpDeviceInfo.setManualIntervention(pcpProductionData.getManualIntervention());
					}
				}else{
					pcpDeviceInfo.setFluidPVT(null);
					pcpDeviceInfo.setReservoir(null);
					pcpDeviceInfo.setRodString(null);
					pcpDeviceInfo.setTubingString(null);
					pcpDeviceInfo.setCasingString(null);
					pcpDeviceInfo.setPump(null);
					pcpDeviceInfo.setProduction(null);
					pcpDeviceInfo.setManualIntervention(null);
				}
				pcpDeviceInfo.setSortNum(rs.getInt(24));
				
				pcpDeviceInfo.setAcqTime(rs.getString(25));
				pcpDeviceInfo.setSaveTime("");
				
				pcpDeviceInfo.setCommStatus(rs.getInt(26));
				pcpDeviceInfo.setCommTime(rs.getFloat(27));
				pcpDeviceInfo.setCommEff(rs.getFloat(28));
				pcpDeviceInfo.setCommRange(StringManagerUtils.CLOBtoString2(rs.getClob(29)));
				
				pcpDeviceInfo.setOnLineAcqTime(rs.getString(25));
				pcpDeviceInfo.setOnLineCommStatus(rs.getInt(26));
				pcpDeviceInfo.setOnLineCommTime(rs.getFloat(27));
				pcpDeviceInfo.setOnLineCommEff(rs.getFloat(28));
				pcpDeviceInfo.setOnLineCommRange(StringManagerUtils.CLOBtoString2(rs.getClob(29)));
				
				pcpDeviceInfo.setRunStatus(rs.getInt(30));
				pcpDeviceInfo.setRunTime(rs.getFloat(31));
				pcpDeviceInfo.setRunEff(rs.getFloat(32));
				pcpDeviceInfo.setRunRange(StringManagerUtils.CLOBtoString2(rs.getClob(33)));
				
				pcpDeviceInfo.setTotalKWattH(rs.getFloat(34));
				pcpDeviceInfo.setTodayKWattH(rs.getFloat(35));
				String key=pcpDeviceInfo.getId()+"";
				jedis.hset("PCPDeviceInfo".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(pcpDeviceInfo));//哈希(Hash)
//				jedis.hset("PCPDeviceCommStatusInfo", pcpDeviceInfo.getCommStatus()+"", key);//哈希(Hash)
//				jedis.hset("PCPDeviceRunStatusInfo", pcpDeviceInfo.getRunStatus()+"", key);//哈希(Hash)
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadPCPDeviceInfoByInstanceId(String instanceId,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_pcpdevice t,tbl_protocolinstance t2 where t.instancecode=t2.code and t2.id= "+instanceId;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadPCPDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadPCPDeviceInfoByInstanceCode(String instanceCode,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_pcpdevice t where t.instancecode= '"+instanceCode+"'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadPCPDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadPCPDeviceInfoByDisplayInstanceId(String instanceId,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_pcpdevice t,tbl_protocoldisplayinstance t2 where t.displayinstancecode=t2.code and t2.id= "+instanceId;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadPCPDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadPCPDeviceInfoByDisplayInstanceCode(String instanceCode,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_pcpdevice t where t.displayinstancecode= '"+instanceCode+"'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadPCPDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadPCPDeviceInfoByAlarmInstanceId(String instanceId,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_pcpdevice t,tbl_protocolalarminstance t2 where t.alarminstancecode=t2.code and t2.id= "+instanceId;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadPCPDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadPCPDeviceInfoByAlarmInstanceCode(String instanceCode,String method){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> wellList =new ArrayList<String>();
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		String sql="select t.id from tbl_pcpdevice t where t.alarminstancecode= '"+instanceCode+"'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				wellList.add(rs.getInt(1)+"");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		if(wellList.size()>0){
			loadPCPDeviceInfo(wellList,0,method);
		}
	}
	
	public static void loadAcqInstanceOwnItemById(String instanceId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String instanceSql="select t.code from tbl_protocolinstance t where 1=1 ";
			String sql="select t5.code as instanceCode,t5.deviceType,t5.acqprotocoltype,t5.ctrlprotocoltype,"
					+ "t4.protocol,t3.unitid,"
					+ "t2.grouptiminginterval,t2.groupsavinginterval,"
					+ "t.id as itemid,t.itemname,t.itemcode,t.bitindex,t.groupid "
					+ " from tbl_acq_item2group_conf t,tbl_acq_group_conf t2,tbl_acq_group2unit_conf t3,tbl_acq_unit_conf t4,tbl_protocolinstance t5 "
					+ " where t.groupid=t2.id and t2.id=t3.groupid and t3.unitid=t4.id and t4.id=t5.unitid and t2.type=0 ";
			if(StringManagerUtils.isNotNull(instanceId)){
				instanceSql+=" and t.id="+instanceId;
				sql+=" and t5.id ="+instanceId;
			}
			sql+=" order by t5.code, t.groupid,t.id";
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(jedis.exists("AcqInstanceOwnItem".getBytes())){
					if(jedis.hexists("AcqInstanceOwnItem".getBytes(), rs.getString(1).getBytes())){
						jedis.hdel("AcqInstanceOwnItem".getBytes(), rs.getString(1).getBytes());
					}
				}
			}
			if(pstmt!=null)
        		pstmt.close();  
        	if(rs!=null)
        		rs.close();
			if(!"delete".equalsIgnoreCase(method)){
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					AcqInstanceOwnItem acqInstanceOwnItem=null;
					if(jedis.hexists("AcqInstanceOwnItem".getBytes(), rs.getString(1).getBytes())){
						byte[]byt=  jedis.hget("AcqInstanceOwnItem".getBytes(), rs.getString(1).getBytes());
						Object obj = SerializeObjectUnils.unserizlize(byt);
						if (obj instanceof AcqInstanceOwnItem) {
							acqInstanceOwnItem=(AcqInstanceOwnItem) obj;
				         }
					}else{
						acqInstanceOwnItem=new AcqInstanceOwnItem();
					}
					
					acqInstanceOwnItem.setInstanceCode(rs.getString(1)+"");
					acqInstanceOwnItem.setDeviceType(rs.getInt(2));
					acqInstanceOwnItem.setAcqProtocolType(rs.getString(3)+"");
					acqInstanceOwnItem.setCtrlProtocolType(rs.getString(4)+"");
					
					acqInstanceOwnItem.setProtocol(rs.getString(5)+"");
					acqInstanceOwnItem.setUnitId(rs.getInt(6));
					acqInstanceOwnItem.setGroupTimingInterval(rs.getInt(7));
					acqInstanceOwnItem.setGroupSavingInterval(rs.getInt(8));
					
					if(acqInstanceOwnItem.getItemList()==null){
						acqInstanceOwnItem.setItemList(new ArrayList<AcqItem>());
					}
					AcqItem acqItem=new AcqItem();
					acqItem.setItemId(rs.getInt(9));
					acqItem.setItemName(rs.getString(10)+"");
					acqItem.setItemCode(rs.getString(11)+"");
					acqItem.setBitIndex(rs.getInt(12));
					acqItem.setGroupId(rs.getInt(13));
					
					int index=-1;
					for(int i=0;i<acqInstanceOwnItem.getItemList().size();i++){
						if(acqItem.getItemId()==acqInstanceOwnItem.getItemList().get(i).getItemId()){
							index=i;
							break;
						}
					}
					if(index>=0){
						acqInstanceOwnItem.getItemList().set(index, acqItem);
					}else{
						acqInstanceOwnItem.getItemList().add(acqItem);
					}
					
					String key=acqInstanceOwnItem.getInstanceCode();
					jedis.hset("AcqInstanceOwnItem".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(acqInstanceOwnItem));//哈希(Hash)
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAcqInstanceOwnItemByCode(String instanceCode,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceCode)){
				instanceSql+=" and t.code='"+instanceCode+"'";
			}
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAcqInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAcqInstanceOwnItemByName(String instanceName,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceName)){
				instanceSql+=" and t.name='"+instanceName+"'";
			}
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAcqInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAcqInstanceOwnItemByGroupId(String groupId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(groupId)){
				instanceSql+=" and t.unitid in (select t6.unitid from tbl_acq_group2unit_conf t6 where t6.groupid="+groupId+")";
			}
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAcqInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAcqInstanceOwnItemByUnitId(String unitId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(unitId)){
				instanceSql+=" and t.unitid ="+unitId;
			}
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAcqInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAcqInstanceOwnItemByProtocolName(String protocolName,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(protocolName)){
				instanceSql+=" and t.unitid in( select t2.id from tbl_acq_unit_conf t2 where t2.protocol='"+protocolName+"' )";
			}
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAcqInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadDisplayInstanceOwnItemById(String instanceId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String instanceSql="select t.code from tbl_protocoldisplayinstance t where 1=1 ";
			String sql="select t3.code as instanceCode,t3.deviceType,t2.protocol,t.unitid,t.id as itemid,t.itemname,t.itemcode,t.bitindex,"
					+ "decode(t.showlevel,null,9999,t.showlevel) as showlevel,decode(t.sort,null,9999,t.sort) as sort,t.realtimecurve,t.realtimecurvecolor,t.historycurve,t.historycurvecolor,t.type "
					+ " from tbl_display_items2unit_conf t,tbl_display_unit_conf t2,tbl_protocoldisplayinstance t3 "
					+ " where t.unitid=t2.id and t2.id=t3.displayunitid";
			if(StringManagerUtils.isNotNull(instanceId)){
				instanceSql+=" and t.id="+instanceId;
				sql+=" and t3.id="+instanceId;
			}
			sql+=" order by t3.code, t.unitid,t.id";
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(jedis.exists("DisplayInstanceOwnItem".getBytes())){
					if(jedis.hexists("DisplayInstanceOwnItem".getBytes(), rs.getString(1).getBytes())){
						jedis.hdel("DisplayInstanceOwnItem".getBytes(), rs.getString(1).getBytes());
					}
				}
			}
			if(pstmt!=null)
        		pstmt.close();  
        	if(rs!=null)
        		rs.close();
        	if(!"delete".equalsIgnoreCase(method)){
        		pstmt = conn.prepareStatement(sql);
    			rs=pstmt.executeQuery();
    			while(rs.next()){
    				DisplayInstanceOwnItem displayInstanceOwnItem=null;
    				if(jedis.hexists("DisplayInstanceOwnItem".getBytes(), rs.getString(1).getBytes())){
    					byte[]byt=  jedis.hget("DisplayInstanceOwnItem".getBytes(), rs.getString(1).getBytes());
    					Object obj = SerializeObjectUnils.unserizlize(byt);
    					if (obj instanceof DisplayInstanceOwnItem) {
    						displayInstanceOwnItem=(DisplayInstanceOwnItem) obj;
    			         }
    				}else{
    					displayInstanceOwnItem=new DisplayInstanceOwnItem();
    				}
    				
    				displayInstanceOwnItem.setInstanceCode(rs.getString(1));
    				displayInstanceOwnItem.setDeviceType(rs.getInt(2));
    				displayInstanceOwnItem.setProtocol(rs.getString(3));
    				displayInstanceOwnItem.setUnitId(rs.getInt(4));
    				
    				if(displayInstanceOwnItem.getItemList()==null){
    					displayInstanceOwnItem.setItemList(new ArrayList<DisplayItem>());
    				}
    				DisplayItem displayItem=new DisplayItem();
    				displayItem.setUnitId(rs.getInt(4));
    				displayItem.setItemId(rs.getInt(5));
    				displayItem.setItemName(rs.getString(6)+"");
    				displayItem.setItemCode(rs.getString(7)+"");
    				displayItem.setBitIndex(rs.getInt(8));
    				displayItem.setShowLevel(rs.getInt(9));
    				displayItem.setSort(rs.getInt(10));
    				displayItem.setRealtimeCurve(rs.getInt(11));
    				displayItem.setRealtimeCurveColor(rs.getString(12)+"");
    				displayItem.setHistoryCurve(rs.getInt(13));
    				displayItem.setHistoryCurveColor(rs.getString(14)+"");
    				displayItem.setType(rs.getInt(15));
    				int index=-1;
    				for(int i=0;i<displayInstanceOwnItem.getItemList().size();i++){
    					if(displayItem.getItemCode().equalsIgnoreCase(displayInstanceOwnItem.getItemList().get(i).getItemCode()) && displayItem.getType()==displayInstanceOwnItem.getItemList().get(i).getType()){
    						index=i;
    						break;
    					}
    				}
    				if(index>=0){
    					displayInstanceOwnItem.getItemList().set(index, displayItem);
    				}else{
    					displayInstanceOwnItem.getItemList().add(displayItem);
    				}
    				Collections.sort(displayInstanceOwnItem.getItemList());
    				String key=displayInstanceOwnItem.getInstanceCode();
    				jedis.hset("DisplayInstanceOwnItem".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(displayInstanceOwnItem));//哈希(Hash)
    			}
        	}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadDisplayInstanceOwnItemByUnitId(String unitId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocoldisplayinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(unitId)){
				instanceSql+=" and t.displayunitid="+unitId;
			}
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadDisplayInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadDisplayInstanceOwnItemByAcqGroupId(String groupId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String sql="select t4.id from tbl_acq_unit_conf t,tbl_acq_group2unit_conf t2,tbl_acq_group_conf t3,tbl_protocoldisplayinstance t4 "
					+ " where t.id=t2.unitid and t2.groupid=t3.id and t4.displayunitid=t.id"
					+ " and t3.id="+groupId+"";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadDisplayInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadDisplayInstanceOwnItemByCode(String instanceCode,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocoldisplayinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceCode)){
				instanceSql+=" and t.code='"+instanceCode+"'";
			}
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadDisplayInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadDisplayInstanceOwnItemByName(String instanceName,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocoldisplayinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceName)){
				instanceSql+=" and t.name='"+instanceName+"'";
			}
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadDisplayInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadDisplayInstanceOwnItemByProtocolName(String protocolName,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocoldisplayinstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(protocolName)){
				instanceSql+=" and t.displayunitid in( select t2.id from tbl_display_unit_conf t2,tbl_acq_unit_conf t3 where t2.acqunitid=t3.id and t3.protocol='"+protocolName+"' )";
			}
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadDisplayInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAlarmInstanceOwnItemById(String instanceId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String instanceSql="select t.code from tbl_protocolalarminstance t where 1=1 ";
			String sql="select t3.code as instanceCode,t3.deviceType,t.unitid,t2.protocol,"
					+ " t.id as itemId,t.itemname,t.itemcode,t.itemaddr,t.bitindex,"
					+ "t.value,t.upperlimit,t.lowerlimit,t.hystersis,t.delay,decode(t.alarmsign,0,0,t.alarmlevel) as alarmlevel,t.alarmsign,t.type,t.issendmessage,t.issendmail "
					+ " from tbl_alarm_item2unit_conf t,tbl_alarm_unit_conf t2,tbl_protocolalarminstance t3 "
					+ " where t.unitid=t2.id and t2.id=t3.alarmunitid";
			if(StringManagerUtils.isNotNull(instanceId)){
				instanceSql+=" and t.id="+instanceId;
				sql+=" and t3.id="+instanceId;
			}
			sql+=" order by t3.code, t.unitid,t.id";
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(jedis.exists("AlarmInstanceOwnItem".getBytes())){
					if(jedis.hexists("AlarmInstanceOwnItem".getBytes(), rs.getString(1).getBytes())){
						jedis.hdel("AlarmInstanceOwnItem".getBytes(), rs.getString(1).getBytes());
					}
				}
			}
			if(!"delete".equalsIgnoreCase(method)){
				if(pstmt!=null)
	        		pstmt.close();  
	        	if(rs!=null)
	        		rs.close();
				pstmt = conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while(rs.next()){
					AlarmInstanceOwnItem alarmInstanceOwnItem=null;
					if(jedis.hexists("AlarmInstanceOwnItem".getBytes(), rs.getString(1).getBytes())){
						byte[]byt=  jedis.hget("AlarmInstanceOwnItem".getBytes(), rs.getString(1).getBytes());
						Object obj = SerializeObjectUnils.unserizlize(byt);
						if (obj instanceof AlarmInstanceOwnItem) {
							alarmInstanceOwnItem=(AlarmInstanceOwnItem) obj;
				         }
					}else{
						alarmInstanceOwnItem=new AlarmInstanceOwnItem();
					}
					
					alarmInstanceOwnItem.setInstanceCode(rs.getString(1));
					alarmInstanceOwnItem.setDeviceType(rs.getInt(2));
					alarmInstanceOwnItem.setUnitId(rs.getInt(3));
					alarmInstanceOwnItem.setProtocol(rs.getString(4));
					
					if(alarmInstanceOwnItem.getItemList()==null){
						alarmInstanceOwnItem.setItemList(new ArrayList<AlarmItem>());
					}
					AlarmItem alarmItem=new AlarmItem();
					alarmItem.setUnitId(rs.getInt(3));
					alarmItem.setItemId(rs.getInt(5));
					alarmItem.setItemName(rs.getString(6));
					alarmItem.setItemCode(rs.getString(7));
					alarmItem.setItemAddr(rs.getInt(8));
					alarmItem.setBitIndex(rs.getInt(9));
					
					alarmItem.setValue(rs.getFloat(10));
					alarmItem.setUpperLimit(rs.getFloat(11));
					alarmItem.setLowerLimit(rs.getFloat(12));
					alarmItem.setHystersis(rs.getFloat(13));
					alarmItem.setDelay(rs.getInt(14));
					
					alarmItem.setAlarmLevel(rs.getInt(15));
					alarmItem.setAlarmSign(rs.getInt(16));
					
					alarmItem.setType(rs.getInt(17));

					alarmItem.setIsSendMessage(rs.getInt(18));
					alarmItem.setIsSendMail(rs.getInt(19));
					
					int index=-1;
					for(int i=0;i<alarmInstanceOwnItem.getItemList().size();i++){
						if(alarmItem.getItemId()==alarmInstanceOwnItem.getItemList().get(i).getItemId()){
							index=i;
							break;
						}
					}
					if(index>=0){
						alarmInstanceOwnItem.getItemList().set(index, alarmItem);
					}else{
						alarmInstanceOwnItem.getItemList().add(alarmItem);
					}
					
					String key=alarmInstanceOwnItem.getInstanceCode();
					jedis.hset("AlarmInstanceOwnItem".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(alarmInstanceOwnItem));//哈希(Hash)
					
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadAlarmInstanceOwnItemByCode(String instanceCode,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		int result=0;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolalarminstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceCode)){
				instanceSql+=" and t.code='"+instanceCode+"'";
			}
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAlarmInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAlarmInstanceOwnItemByName(String instanceName,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		int result=0;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolalarminstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(instanceName)){
				instanceSql+=" and t.name='"+instanceName+"'";
			}
			
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAlarmInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAlarmInstanceOwnItemByUnitId(String unitId,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolalarminstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(unitId)){
				instanceSql+=" and t.alarmunitid="+unitId;
			}
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAlarmInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadAlarmInstanceOwnItemByProtocolName(String protocolName,String method){
		Connection conn = null;   
		PreparedStatement pstmt = null;   
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		try {
			String instanceSql="select t.id from tbl_protocolalarminstance t where 1=1 ";
			if(StringManagerUtils.isNotNull(protocolName)){
				instanceSql+=" and t.alarmunitid in (select t2.id from tbl_alarm_unit_conf t2 where t2.protocol='"+protocolName+"')";
			}
			pstmt = conn.prepareStatement(instanceSql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				loadAlarmInstanceOwnItemById(rs.getInt(1)+"",method);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadRPCCalculateItem(){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd("rpcCalItemList".getBytes(),1, SerializeObjectUnils.serialize(new CalItem("在线时间","CommTime","h",2)));
			jedis.zadd("rpcCalItemList".getBytes(),2, SerializeObjectUnils.serialize(new CalItem("在线时率","CommTimeEfficiency","",2)));
			jedis.zadd("rpcCalItemList".getBytes(),3, SerializeObjectUnils.serialize(new CalItem("在线区间","CommRange","",1)));
			
			jedis.zadd("rpcCalItemList".getBytes(),4, SerializeObjectUnils.serialize(new CalItem("运行时间","RunTime","h",2)));
			jedis.zadd("rpcCalItemList".getBytes(),5, SerializeObjectUnils.serialize(new CalItem("运行时率","RunTimeEfficiency","",2)));
			jedis.zadd("rpcCalItemList".getBytes(),6, SerializeObjectUnils.serialize(new CalItem("运行区间","RunRange","",1)));
			
			
			jedis.zadd("rpcCalItemList".getBytes(),7, SerializeObjectUnils.serialize(new CalItem("工况","ResultName","",1)));
			jedis.zadd("rpcCalItemList".getBytes(),8, SerializeObjectUnils.serialize(new CalItem("最大载荷","FMax","kN",2)));
			jedis.zadd("rpcCalItemList".getBytes(),9, SerializeObjectUnils.serialize(new CalItem("最小载荷","FMin","kN",2)));
			jedis.zadd("rpcCalItemList".getBytes(),10, SerializeObjectUnils.serialize(new CalItem("充满系数","FullnessCoefficient","",2)));
			jedis.zadd("rpcCalItemList".getBytes(),11, SerializeObjectUnils.serialize(new CalItem("抽空充满系数","NoLiquidFullnessCoefficient","",2)));
			
			jedis.zadd("rpcCalItemList".getBytes(),12, SerializeObjectUnils.serialize(new CalItem("柱塞冲程","PlungerStroke","m",2)));
			jedis.zadd("rpcCalItemList".getBytes(),13, SerializeObjectUnils.serialize(new CalItem("柱塞有效冲程","AvailablePlungerStroke","m",2)));
			jedis.zadd("rpcCalItemList".getBytes(),14, SerializeObjectUnils.serialize(new CalItem("抽空柱塞有效冲程","NoLiquidAvailablePlungerStroke","m",2)));
			
			jedis.zadd("rpcCalItemList".getBytes(),15, SerializeObjectUnils.serialize(new CalItem("理论上载荷","UpperLoadLine","kN",2)));
			jedis.zadd("rpcCalItemList".getBytes(),16, SerializeObjectUnils.serialize(new CalItem("理论下载荷","LowerLoadLine","kN",2)));
			jedis.zadd("rpcCalItemList".getBytes(),17, SerializeObjectUnils.serialize(new CalItem("考虑沉没压力的理论上载荷","UpperLoadLineOfExact","kN",2)));
			
			
			jedis.zadd("rpcCalItemList".getBytes(),18, SerializeObjectUnils.serialize(new CalItem("理论排量","TheoreticalProduction","m^3/d",2)));
			
			jedis.zadd("rpcCalItemList".getBytes(),19, SerializeObjectUnils.serialize(new CalItem("产液量","LiquidVolumetricProduction","m^3/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),20, SerializeObjectUnils.serialize(new CalItem("产油量","OilVolumetricProduction","m^3/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),21, SerializeObjectUnils.serialize(new CalItem("产水量","WaterVolumetricProduction","m^3/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),22, SerializeObjectUnils.serialize(new CalItem("柱塞有效冲程计算产量","AvailablePlungerStrokeProd_v","m^3/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),23, SerializeObjectUnils.serialize(new CalItem("泵间隙漏失量","PumpClearanceleakProd_v","m^3/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),24, SerializeObjectUnils.serialize(new CalItem("游动凡尔漏失量","TVLeakVolumetricProduction","m^3/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),25, SerializeObjectUnils.serialize(new CalItem("固定凡尔漏失量","SVLeakVolumetricProduction","m^3/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),26, SerializeObjectUnils.serialize(new CalItem("气影响","GasInfluenceProd_v","m^3/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),27, SerializeObjectUnils.serialize(new CalItem("累计产液量","LiquidVolumetricProduction_l","m^3/d",2)));
			
			jedis.zadd("rpcCalItemList".getBytes(),28, SerializeObjectUnils.serialize(new CalItem("产液量","LiquidWeightProduction","t/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),29, SerializeObjectUnils.serialize(new CalItem("产油量","OilWeightProduction","t/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),30, SerializeObjectUnils.serialize(new CalItem("产水量","WaterWeightProduction","t/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),31, SerializeObjectUnils.serialize(new CalItem("柱塞有效冲程计算产量","AvailablePlungerStrokeProd_w","t/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),32, SerializeObjectUnils.serialize(new CalItem("泵间隙漏失量","PumpClearanceleakProd_w","t/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),33, SerializeObjectUnils.serialize(new CalItem("游动凡尔漏失量","TVLeakWeightProduction","t/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),34, SerializeObjectUnils.serialize(new CalItem("固定凡尔漏失量","SVLeakWeightProduction","t/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),35, SerializeObjectUnils.serialize(new CalItem("气影响","GasInfluenceProd_w","t/d",2)));
			jedis.zadd("rpcCalItemList".getBytes(),36, SerializeObjectUnils.serialize(new CalItem("累计产液量","LiquidWeightProduction_l","t/d",2)));
			
			jedis.zadd("rpcCalItemList".getBytes(),37, SerializeObjectUnils.serialize(new CalItem("有功功率","AverageWatt","kW",2)));
			jedis.zadd("rpcCalItemList".getBytes(),38, SerializeObjectUnils.serialize(new CalItem("光杆功率","PolishRodPower","kW",2)));
			jedis.zadd("rpcCalItemList".getBytes(),39, SerializeObjectUnils.serialize(new CalItem("水功率","WaterPower","kW",2)));
			
			jedis.zadd("rpcCalItemList".getBytes(),40, SerializeObjectUnils.serialize(new CalItem("地面效率","SurfaceSystemEfficiency","",2)));
			jedis.zadd("rpcCalItemList".getBytes(),41, SerializeObjectUnils.serialize(new CalItem("井下效率","WellDownSystemEfficiency","",2)));
			jedis.zadd("rpcCalItemList".getBytes(),42, SerializeObjectUnils.serialize(new CalItem("系统效率","SystemEfficiency","",2)));
			jedis.zadd("rpcCalItemList".getBytes(),43, SerializeObjectUnils.serialize(new CalItem("功图面积","Area","",2)));
			jedis.zadd("rpcCalItemList".getBytes(),44, SerializeObjectUnils.serialize(new CalItem("吨液百米耗电量","EnergyPer100mLift","kW· h/100m· t",2)));
			
			
			jedis.zadd("rpcCalItemList".getBytes(),45, SerializeObjectUnils.serialize(new CalItem("抽油杆伸长量","RodFlexLength","m",2)));
			jedis.zadd("rpcCalItemList".getBytes(),46, SerializeObjectUnils.serialize(new CalItem("油管伸缩量","TubingFlexLength","m",2)));
			jedis.zadd("rpcCalItemList".getBytes(),47, SerializeObjectUnils.serialize(new CalItem("惯性载荷增量","InertiaLength","m",2)));
			jedis.zadd("rpcCalItemList".getBytes(),48, SerializeObjectUnils.serialize(new CalItem("冲程损失系数","PumpEff1","",2)));
			jedis.zadd("rpcCalItemList".getBytes(),49, SerializeObjectUnils.serialize(new CalItem("充满系数","PumpEff2","",2)));
			jedis.zadd("rpcCalItemList".getBytes(),50, SerializeObjectUnils.serialize(new CalItem("间隙漏失系数","PumpEff3","",2)));
			jedis.zadd("rpcCalItemList".getBytes(),51, SerializeObjectUnils.serialize(new CalItem("液体收缩系数","PumpEff4","",2)));
			jedis.zadd("rpcCalItemList".getBytes(),52, SerializeObjectUnils.serialize(new CalItem("总泵效","PumpEff","",2)));
			
			jedis.zadd("rpcCalItemList".getBytes(),53, SerializeObjectUnils.serialize(new CalItem("泵入口压力","PumpIntakeP","MPa",2)));
			jedis.zadd("rpcCalItemList".getBytes(),54, SerializeObjectUnils.serialize(new CalItem("泵入口温度","PumpIntakeT","℃",2)));
			jedis.zadd("rpcCalItemList".getBytes(),55, SerializeObjectUnils.serialize(new CalItem("泵入口就地气液比","PumpIntakeGOL","m^3/m^3",2)));
			jedis.zadd("rpcCalItemList".getBytes(),56, SerializeObjectUnils.serialize(new CalItem("泵入口粘度","PumpIntakeVisl","mPa·s",2)));
			jedis.zadd("rpcCalItemList".getBytes(),57, SerializeObjectUnils.serialize(new CalItem("泵入口原油体积系数","PumpIntakeBo","",2)));
			
			jedis.zadd("rpcCalItemList".getBytes(),58, SerializeObjectUnils.serialize(new CalItem("泵出口压力","PumpOutletP","MPa",2)));
			jedis.zadd("rpcCalItemList".getBytes(),59, SerializeObjectUnils.serialize(new CalItem("泵出口温度","PumpOutletT","℃",2)));
			jedis.zadd("rpcCalItemList".getBytes(),60, SerializeObjectUnils.serialize(new CalItem("泵出口就地气液比","PumpOutletGOL","m^3/m^3",2)));
			jedis.zadd("rpcCalItemList".getBytes(),61, SerializeObjectUnils.serialize(new CalItem("泵出口粘度","PumpOutletVisl","mPa·s",2)));
			jedis.zadd("rpcCalItemList".getBytes(),62, SerializeObjectUnils.serialize(new CalItem("泵出口原油体积系数","PumpOutletBo","",2)));
			
			jedis.zadd("rpcCalItemList".getBytes(),63, SerializeObjectUnils.serialize(new CalItem("上冲程最大电流","UpStrokeIMax","A",2)));
			jedis.zadd("rpcCalItemList".getBytes(),64, SerializeObjectUnils.serialize(new CalItem("下冲程最大电流","DownStrokeIMax","A",2)));
			jedis.zadd("rpcCalItemList".getBytes(),65, SerializeObjectUnils.serialize(new CalItem("上冲程最大功率","UpStrokeWattMax","kW",2)));
			jedis.zadd("rpcCalItemList".getBytes(),66, SerializeObjectUnils.serialize(new CalItem("下冲程最大功率","DownStrokeWattMax","kW",2)));
			jedis.zadd("rpcCalItemList".getBytes(),67, SerializeObjectUnils.serialize(new CalItem("电流平衡度","IDegreeBalance","%",2)));
			jedis.zadd("rpcCalItemList".getBytes(),68, SerializeObjectUnils.serialize(new CalItem("功率平衡度","WattDegreeBalance","%",2)));
			jedis.zadd("rpcCalItemList".getBytes(),69, SerializeObjectUnils.serialize(new CalItem("移动距离","DeltaRadius","m",2)));
			
			jedis.zadd("rpcCalItemList".getBytes(),70, SerializeObjectUnils.serialize(new CalItem("反演液面校正值","LevelCorrectValue","MPa",2)));
			jedis.zadd("rpcCalItemList".getBytes(),71, SerializeObjectUnils.serialize(new CalItem("动液面","InverProducingfluidLevel","m",2)));
			
			jedis.zadd("rpcCalItemList".getBytes(),72, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2)));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadPCPCalculateItem(){
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			//有序集合
			jedis.zadd("pcpCalItemList".getBytes(),1, SerializeObjectUnils.serialize(new CalItem("在线时间","CommTime","h",2)));
			jedis.zadd("pcpCalItemList".getBytes(),2, SerializeObjectUnils.serialize(new CalItem("在线时率","CommTimeEfficiency","",2)));
			jedis.zadd("pcpCalItemList".getBytes(),3, SerializeObjectUnils.serialize(new CalItem("在线区间","CommRange","",1)));
			
			jedis.zadd("pcpCalItemList".getBytes(),4, SerializeObjectUnils.serialize(new CalItem("运行时间","RunTime","h",2)));
			jedis.zadd("pcpCalItemList".getBytes(),5, SerializeObjectUnils.serialize(new CalItem("运行时率","RunTimeEfficiency","",2)));
			jedis.zadd("pcpCalItemList".getBytes(),6, SerializeObjectUnils.serialize(new CalItem("运行区间","RunRange","",1)));
			
			jedis.zadd("pcpCalItemList".getBytes(),7, SerializeObjectUnils.serialize(new CalItem("理论排量","TheoreticalProduction","m^3/d",2)));
			
			jedis.zadd("pcpCalItemList".getBytes(),8, SerializeObjectUnils.serialize(new CalItem("产液量","LiquidVolumetricProduction","m^3/d",2)));
			jedis.zadd("pcpCalItemList".getBytes(),9, SerializeObjectUnils.serialize(new CalItem("产油量","OilVolumetricProduction","m^3/d",2)));
			jedis.zadd("pcpCalItemList".getBytes(),10, SerializeObjectUnils.serialize(new CalItem("产水量","WaterVolumetricProduction","m^3/d",2)));
			jedis.zadd("pcpCalItemList".getBytes(),11, SerializeObjectUnils.serialize(new CalItem("累计产液量","LiquidVolumetricProduction_l","m^3/d",2)));
			
			jedis.zadd("pcpCalItemList".getBytes(),12, SerializeObjectUnils.serialize(new CalItem("产液量","LiquidWeightProduction","t/d",2)));
			jedis.zadd("pcpCalItemList".getBytes(),13, SerializeObjectUnils.serialize(new CalItem("产油量","OilWeightProduction","t/d",2)));
			jedis.zadd("pcpCalItemList".getBytes(),14, SerializeObjectUnils.serialize(new CalItem("产水量","WaterWeightProduction","t/d",2)));
			jedis.zadd("pcpCalItemList".getBytes(),15, SerializeObjectUnils.serialize(new CalItem("累计产液量","LiquidWeightProduction_l","t/d",2)));
			
			jedis.zadd("pcpCalItemList".getBytes(),16, SerializeObjectUnils.serialize(new CalItem("有功功率","AverageWatt","kW",2)));
			jedis.zadd("pcpCalItemList".getBytes(),17, SerializeObjectUnils.serialize(new CalItem("水功率","WaterPower","kW",2)));
			jedis.zadd("pcpCalItemList".getBytes(),18, SerializeObjectUnils.serialize(new CalItem("系统效率","SystemEfficiency","",2)));
			
			jedis.zadd("pcpCalItemList".getBytes(),19, SerializeObjectUnils.serialize(new CalItem("容积效率","PumpEff1","",2)));
			jedis.zadd("pcpCalItemList".getBytes(),20, SerializeObjectUnils.serialize(new CalItem("液体收缩系数","PumpEff2","",2)));
			jedis.zadd("pcpCalItemList".getBytes(),21, SerializeObjectUnils.serialize(new CalItem("总泵效","PumpEff","",2)));
			
			jedis.zadd("pcpCalItemList".getBytes(),22, SerializeObjectUnils.serialize(new CalItem("泵入口压力","PumpIntakeP","MPa",2)));
			jedis.zadd("pcpCalItemList".getBytes(),23, SerializeObjectUnils.serialize(new CalItem("泵入口温度","PumpIntakeT","℃",2)));
			jedis.zadd("pcpCalItemList".getBytes(),24, SerializeObjectUnils.serialize(new CalItem("泵入口就地气液比","PumpIntakeGOL","m^3/m^3",2)));
			jedis.zadd("pcpCalItemList".getBytes(),25, SerializeObjectUnils.serialize(new CalItem("泵入口粘度","PumpIntakeVisl","mPa·s",2)));
			jedis.zadd("pcpCalItemList".getBytes(),26, SerializeObjectUnils.serialize(new CalItem("泵入口原油体积系数","PumpIntakeBo","",2)));
			
			jedis.zadd("pcpCalItemList".getBytes(),27, SerializeObjectUnils.serialize(new CalItem("泵出口压力","PumpOutletP","MPa",2)));
			jedis.zadd("pcpCalItemList".getBytes(),28, SerializeObjectUnils.serialize(new CalItem("泵出口温度","PumpOutletT","℃",2)));
			jedis.zadd("pcpCalItemList".getBytes(),29, SerializeObjectUnils.serialize(new CalItem("泵出口就地气液比","PumpOutletGOL","m^3/m^3",2)));
			jedis.zadd("pcpCalItemList".getBytes(),30, SerializeObjectUnils.serialize(new CalItem("泵出口粘度","PumpOutletVisl","mPa·s",2)));
			jedis.zadd("pcpCalItemList".getBytes(),31, SerializeObjectUnils.serialize(new CalItem("泵出口原油体积系数","PumpOutletBo","",2)));
			
			jedis.zadd("pcpCalItemList".getBytes(),32, SerializeObjectUnils.serialize(new CalItem("日用电量","TodayKWattH","kW·h",2)));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadUserInfo(List<String> userList,int condition,String method){//condition 0 -用户id 1-用户账号
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if("delete".equalsIgnoreCase(method)){
				if(userList!=null){
					if(condition==0){
						for(int i=0;i<userList.size();i++){
							jedis.hdel("UserInfo".getBytes(), userList.get(i).getBytes());
						}
					}else if(condition==1){
						for(int i=0;i<userList.size();i++){
							if(jedis.exists("UserInfo".getBytes())){
								List<byte[]> userByteList =jedis.hvals("UserInfo".getBytes());
								for(int j=0;j<userByteList.size();j++){
									UserInfo userInfo=(UserInfo)SerializeObjectUnils.unserizlize(userByteList.get(i));
									if(userList.get(i).equalsIgnoreCase(userInfo.getUserId())){
										jedis.hdel("UserInfo".getBytes(), (userInfo.getUserNo()+"").getBytes());
										break;
									}
								}
							}
						}
					}
				}
				return;
			}
			
			String users="";
			if(condition==0){
				users=StringUtils.join(userList, ",");
			}else{
				users=StringManagerUtils.joinStringArr2(userList, ",");
			}
			
			String sql="select t.user_no,t.user_id,t.user_name,t.user_pwd,t.user_orgid,"
					+ " t.user_in_email,t.user_phone,"
					+ " t.user_quicklogin,t.user_enable,t.user_receivesms,t.user_receivemail,"
					+ " t.user_type,t2.role_name,t2.role_level,t2.role_flag,t2.showlevel "
					+ " from tbl_user t,tbl_role t2 "
					+ " where t.user_type=t2.role_id";
			if(StringManagerUtils.isNotNull(users)){
				if(condition==0){
					sql+=" and t.user_no in("+users+")";
				}else{
					sql+=" and t.user_id in("+users+")";
				}
			}
			sql+= " order by t.user_no";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				UserInfo userInfo=new UserInfo();
				userInfo.setUserNo(rs.getInt(1));
				userInfo.setUserId(rs.getString(2));
				userInfo.setUserName(rs.getString(3));
				userInfo.setUserPwd(rs.getString(4));
				userInfo.setUserOrgid(rs.getInt(5));
				
				userInfo.setUserInEmail(rs.getString(6));
				userInfo.setUserPhone(rs.getString(7));
				
				userInfo.setUserQuickLogin(rs.getInt(8));
				userInfo.setUserEnable(rs.getInt(9));
				userInfo.setReceiveSMS(rs.getInt(10));
				userInfo.setReceiveMail(rs.getInt(11));
				
				userInfo.setUserType(rs.getInt(12));
				userInfo.setRoleName(rs.getString(13));
				userInfo.setRoleLevel(rs.getInt(14));
				userInfo.setRoleFlag(rs.getInt(15));
				userInfo.setRoleShowLevel(rs.getInt(16));
				
				String key=userInfo.getUserNo()+"";
				jedis.hset("UserInfo".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(userInfo));//哈希(Hash)
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadUserInfoByRoleId(String roleId,String method){//condition 0 -用户id 1-用户账号
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		List<String> roleList=new ArrayList<String>();
        String sql="select t.user_no from TBL_USER t where t.user_type="+roleId;
        try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				roleList.add(rs.getInt(1)+"");
			}
			if(roleList.size()>0){
				loadUserInfo(roleList,0,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadUserInfoByOrgId(String orgId,String method){//condition 0 -用户id 1-用户账号
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		List<String> roleList=new ArrayList<String>();
        String sql="select t.user_no from TBL_USER t where t.user_orgid="+orgId;
        try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				roleList.add(rs.getInt(1)+"");
			}
			if(roleList.size()>0){
				loadUserInfo(roleList,0,method);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	public static void loadRPCWorkType(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String sql="select t.id,t.resultcode,t.resultname,t.resultdescription,t.optimizationsuggestion,t.remark "
					+ " from TBL_RPC_WORKTYPE t order by t.resultcode";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				WorkType workType=new WorkType();
				workType.setId(rs.getInt(1));
				workType.setResultCode(rs.getInt(2));
				workType.setResultName(rs.getString(3));
				workType.setResultDescription(rs.getString(4));
				workType.setOptimizationSuggestion(rs.getString(5));
				workType.setRemark(rs.getString(6));
				String key=workType.getResultCode()+"";
				String keyByName=workType.getResultName()+"";
				
				jedis.hset("RPCWorkType".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(workType));//哈希(Hash)
				jedis.hset("RPCWorkTypeByName".getBytes(), keyByName.getBytes(), SerializeObjectUnils.serialize(workType));//哈希(Hash)
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
	}
	
	@SuppressWarnings("resource")
	public static AlarmShowStyle initAlarmStyle(){
		Connection conn = null;   
		PreparedStatement pstmt = null;  
		Statement stmt = null;  
		ResultSet rs = null;
		Jedis jedis=null;
		AlarmShowStyle alarmShowStyle=null;
		try {
			alarmShowStyle=new AlarmShowStyle();
			String sql="select v1.itemvalue as alarmLevel,v1.itemname as backgroundColor,v2.itemname as color,v3.itemname as opacity from "
					+ " (select * from tbl_code t where t.itemcode='BJYS' ) v1,"
					+ " (select * from tbl_code t where t.itemcode='BJQJYS' ) v2,"
					+ " (select * from tbl_code t where t.itemcode='BJYSTMD' ) v3 "
					+ " where v1.itemvalue=v2.itemvalue and v1.itemvalue=v3.itemvalue "
					+ " order by v1.itemvalue ";
			String commAlarmSql="select v1.itemvalue as alarmLevel,v1.itemname as backgroundColor,v2.itemname as color,v3.itemname as opacity from "
					+ " (select * from tbl_code t where t.itemcode='TXBJYS' ) v1,"
					+ " (select * from tbl_code t where t.itemcode='TXBJQJYS' ) v2,"
					+ " (select * from tbl_code t where t.itemcode='TXBJYSTMD' ) v3 "
					+ " where v1.itemvalue=v2.itemvalue and v1.itemvalue=v3.itemvalue "
					+ " order by v1.itemvalue ";
			String runAlarmSql="select v1.itemvalue as alarmLevel,v1.itemname as backgroundColor,v2.itemname as color,v3.itemname as opacity from "
					+ " (select * from tbl_code t where t.itemcode='YXBJYS' ) v1,"
					+ " (select * from tbl_code t where t.itemcode='YXBJQJYS' ) v2,"
					+ " (select * from tbl_code t where t.itemcode='YXBJYSTMD' ) v3 "
					+ " where v1.itemvalue=v2.itemvalue and v1.itemvalue=v3.itemvalue "
					+ " order by v1.itemvalue ";
			conn=OracleJdbcUtis.getConnection();
			if(conn==null){
				return null;
			}
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(rs.getInt(1)==0){
					alarmShowStyle.getData().getNormal().setValue(rs.getInt(1));
					alarmShowStyle.getData().getNormal().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getData().getNormal().setColor(rs.getString(3));
					alarmShowStyle.getData().getNormal().setOpacity(rs.getString(4));
				}else if(rs.getInt(1)==100){
					alarmShowStyle.getData().getFirstLevel().setValue(rs.getInt(1));
					alarmShowStyle.getData().getFirstLevel().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getData().getFirstLevel().setColor(rs.getString(3));
					alarmShowStyle.getData().getFirstLevel().setOpacity(rs.getString(4));
				}else if(rs.getInt(1)==200){
					alarmShowStyle.getData().getSecondLevel().setValue(rs.getInt(1));
					alarmShowStyle.getData().getSecondLevel().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getData().getSecondLevel().setColor(rs.getString(3));
					alarmShowStyle.getData().getSecondLevel().setOpacity(rs.getString(4));
				}else if(rs.getInt(1)==300){
					alarmShowStyle.getData().getThirdLevel().setValue(rs.getInt(1));
					alarmShowStyle.getData().getThirdLevel().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getData().getThirdLevel().setColor(rs.getString(3));
					alarmShowStyle.getData().getThirdLevel().setOpacity(rs.getString(4));
				}	
			}
			pstmt = conn.prepareStatement(commAlarmSql); 
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(rs.getInt(1)==0){
					alarmShowStyle.getComm().getOffline().setValue(rs.getInt(1));
					alarmShowStyle.getComm().getOffline().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getComm().getOffline().setColor(rs.getString(3));
					alarmShowStyle.getComm().getOffline().setOpacity(rs.getString(4));
				}else if(rs.getInt(1)==1){
					alarmShowStyle.getComm().getOnline().setValue(rs.getInt(1));
					alarmShowStyle.getComm().getOnline().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getComm().getOnline().setColor(rs.getString(3));
					alarmShowStyle.getComm().getOnline().setOpacity(rs.getString(4));
				}else if(rs.getInt(1)==2){
					alarmShowStyle.getComm().getGoOnline().setValue(rs.getInt(1));
					alarmShowStyle.getComm().getGoOnline().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getComm().getGoOnline().setColor(rs.getString(3));
					alarmShowStyle.getComm().getGoOnline().setOpacity(rs.getString(4));
				}
			}
			pstmt = conn.prepareStatement(runAlarmSql); 
			rs=pstmt.executeQuery();
			while(rs.next()){
				if(rs.getInt(1)==0){
					alarmShowStyle.getRun().getStop().setValue(rs.getInt(1));
					alarmShowStyle.getRun().getStop().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getRun().getStop().setColor(rs.getString(3));
					alarmShowStyle.getRun().getStop().setOpacity(rs.getString(4));
				}else if(rs.getInt(1)==1){
					alarmShowStyle.getRun().getRun().setValue(rs.getInt(1));
					alarmShowStyle.getRun().getRun().setBackgroundColor(rs.getString(2));
					alarmShowStyle.getRun().getRun().setColor(rs.getString(3));
					alarmShowStyle.getRun().getRun().setOpacity(rs.getString(4));
				}
			}
			
			jedis = RedisUtil.jedisPool.getResource();
			jedis.set("AlarmShowStyle".getBytes(), SerializeObjectUnils.serialize(alarmShowStyle));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
		}
		return alarmShowStyle;
	}
	
	public static ModbusProtocolConfig getModbusProtocolConfig(){
		Jedis jedis=null;
		ModbusProtocolConfig modbusProtocolConfig=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("modbusProtocolConfig".getBytes())){
				MemoryDataManagerTask.loadProtocolConfig();
			}
			modbusProtocolConfig=(ModbusProtocolConfig)SerializeObjectUnils.unserizlize(jedis.get("modbusProtocolConfig".getBytes()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return modbusProtocolConfig;
	}
	
	public static void loadTodayFESDiagram(List<String> wellList,int condition){//condition 0 -设备ID 1-设备名称
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String currentDate=StringManagerUtils.getCurrentTime("yyyy-MM-dd");
			String wells="";
			if(condition==0){
				wells=StringUtils.join(wellList, ",");
			}else{
				wells=StringManagerUtils.joinStringArr2(wellList, ",");
			}	
					
			String sql="select t2.id as wellId,t2.wellname,"//2
					+ "to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"//3
					+ "t.stroke,t.spm,t.fmax,t.fmin,t.fullnesscoefficient,t.resultcode,"//9
					+ "t.theoreticalproduction,"//10
					+ "t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"//13
					+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"//16
					+ "t.wattdegreebalance,t.idegreebalance,t.deltaradius,"//19
					+ "t.systemefficiency,t.surfacesystemefficiency,t.welldownsystemefficiency,t.energyper100mlift,"//23
					+ "t.pumpeff1,t.pumpeff2,t.pumpeff3,t.pumpeff4,t.pumpeff,"//28
					+ "t.productiondata "//29
					+ "from tbl_rpcacqdata_hist t,tbl_rpcdevice t2 "
					+ "where t.wellid=t2.id  "
					+ " and t.resultstatus=1"
					+ " and t.fesdiagramacqtime between to_date('"+currentDate+"','yyyy-mm-dd') and to_date('"+currentDate+"','yyyy-mm-dd')+1 ";
			if(StringManagerUtils.isNotNull(wells)){
				if(condition==0){
					sql+=" and t2.id in("+wells+")";
				}else{
					sql+=" and t2.wellName in("+wells+")";
				}
				
			}
			sql+= "order by t2.id, t.fesdiagramacqtime";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				int deviceId=rs.getInt(1);
				String key=deviceId+"";
				RPCCalculateResponseData responseData =new RPCCalculateResponseData(); 
				responseData.init();
				
				responseData.setWellName(rs.getString(2));
				responseData.getFESDiagram().setAcqTime(rs.getString(3));
				responseData.getFESDiagram().setStroke(rs.getFloat(4));
				responseData.getFESDiagram().setSPM(rs.getFloat(5));
				responseData.getFESDiagram().getFMax().add(rs.getFloat(6));
				responseData.getFESDiagram().getFMin().add(rs.getFloat(7));
				responseData.getFESDiagram().setFullnessCoefficient(rs.getFloat(8));
				responseData.getCalculationStatus().setResultCode(rs.getInt(9));
				
				responseData.getProduction().setTheoreticalProduction(rs.getFloat(10));
				responseData.getProduction().setLiquidVolumetricProduction(rs.getFloat(11));
				responseData.getProduction().setOilVolumetricProduction(rs.getFloat(12));
				responseData.getProduction().setWaterVolumetricProduction(rs.getFloat(13));
				responseData.getProduction().setLiquidWeightProduction(rs.getFloat(14));
				responseData.getProduction().setOilWeightProduction(rs.getFloat(15));
				responseData.getProduction().setWaterWeightProduction(rs.getFloat(16));
				
				responseData.getFESDiagram().setWattDegreeBalance(rs.getFloat(17));
				responseData.getFESDiagram().setIDegreeBalance(rs.getFloat(18));
				responseData.getFESDiagram().setDeltaRadius(rs.getFloat(19));
				
				responseData.getSystemEfficiency().setSystemEfficiency(rs.getFloat(20));
				responseData.getSystemEfficiency().setSurfaceSystemEfficiency(rs.getFloat(21));
				responseData.getSystemEfficiency().setWellDownSystemEfficiency(rs.getFloat(22));
				responseData.getSystemEfficiency().setEnergyPer100mLift(rs.getFloat(23));
				
				responseData.getPumpEfficiency().setPumpEff1(rs.getFloat(24));
				responseData.getPumpEfficiency().setPumpEff2(rs.getFloat(25));
				responseData.getPumpEfficiency().setPumpEff3(rs.getFloat(26));
				responseData.getPumpEfficiency().setPumpEff4(rs.getFloat(27));
				responseData.getPumpEfficiency().setPumpEff(rs.getFloat(28));
				
				String productionData=rs.getString(29)+"";
				if(StringManagerUtils.isNotNull(productionData)){
					type = new TypeToken<RPCDeviceInfo>() {}.getType();
					RPCDeviceInfo rpcProductionData=gson.fromJson(productionData, type);
					if(rpcProductionData!=null){
						responseData.getProduction().setWaterCut(rpcProductionData.getProduction().getWaterCut());
					}
				}
				if(jedis.hexists("RPCDeviceTodayData".getBytes(), key.getBytes())){
					RPCDeviceTodayData deviceTodayData =(RPCDeviceTodayData) SerializeObjectUnils.unserizlize(jedis.hget("RPCDeviceTodayData".getBytes(), key.getBytes()));
					deviceTodayData.getRPCCalculateList().add(responseData);
					jedis.hset("RPCDeviceTodayData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
				}else{
					RPCDeviceTodayData deviceTodayData=new RPCDeviceTodayData();
					deviceTodayData.setId(deviceId);
					deviceTodayData.setRPCCalculateList(new ArrayList<RPCCalculateResponseData>());
					deviceTodayData.setAcquisitionItemInfoList(new ArrayList<AcquisitionItemInfo>());
					deviceTodayData.getRPCCalculateList().add(responseData);
					jedis.hset("RPCDeviceTodayData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static void loadTodayRPMData(List<String> wellList,int condition){//condition 0 -设备ID 1-设备名称
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		conn=OracleJdbcUtis.getConnection();
		if(conn==null){
        	return;
        }
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			String currentDate=StringManagerUtils.getCurrentTime("yyyy-MM-dd");
			String wells="";
			if(condition==0){
				wells=StringUtils.join(wellList, ",");
			}else{
				wells=StringManagerUtils.joinStringArr2(wellList, ",");
			}	
					
			String sql="select t2.id,t2.wellname,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime,"
					+ "t.rpm,t.resultcode,"
					+ "t.theoreticalproduction,"
					+ "t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction,"
					+ "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction,"
					+ "t.systemefficiency,"
					+ "t.pumpeff1,t.pumpeff2,t.pumpeff,"
					+ "t.productiondata "
					+ " from tbl_pcpacqdata_hist t,tbl_pcpdevice t2 "
					+ " where t.wellid=t2.id and t.resultstatus=1 and t.acqtime between to_date('"+currentDate+"','yyyy-mm-dd') and to_date('"+currentDate+"','yyyy-mm-dd')+1";
			if(StringManagerUtils.isNotNull(wells)){
				if(condition==0){
					sql+=" and t2.id in("+wells+")";
				}else{
					sql+=" and t2.wellName in("+wells+")";
				}
				
			}
			sql+= "order by t2.id, t.acqtime";
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()){
				int deviceId=rs.getInt(1);
				String key=deviceId+"";
				
				PCPCalculateResponseData responseData =new PCPCalculateResponseData(); 
				responseData.init();
				
				responseData.setWellName(rs.getString(2));
				responseData.setAcqTime(rs.getString(3));
				
				responseData.setRPM(rs.getFloat(4));
				responseData.getCalculationStatus().setResultCode(rs.getInt(5));
				
				responseData.getProduction().setTheoreticalProduction(rs.getFloat(6));
				responseData.getProduction().setLiquidVolumetricProduction(rs.getFloat(7));
				responseData.getProduction().setOilVolumetricProduction(rs.getFloat(8));
				responseData.getProduction().setWaterVolumetricProduction(rs.getFloat(9));
				responseData.getProduction().setLiquidWeightProduction(rs.getFloat(10));
				responseData.getProduction().setOilWeightProduction(rs.getFloat(11));
				responseData.getProduction().setWaterWeightProduction(rs.getFloat(12));
				
				
				responseData.getSystemEfficiency().setSystemEfficiency(rs.getFloat(13));
				
				responseData.getPumpEfficiency().setPumpEff1(rs.getFloat(14));
				responseData.getPumpEfficiency().setPumpEff2(rs.getFloat(15));
				responseData.getPumpEfficiency().setPumpEff(rs.getFloat(16));
				
				String productionData=rs.getString(17)+"";
				if(StringManagerUtils.isNotNull(productionData)){
					type = new TypeToken<PCPDeviceInfo>() {}.getType();
					PCPDeviceInfo pcpProductionData=gson.fromJson(productionData, type);
					if(pcpProductionData!=null){
						responseData.getProduction().setWaterCut(pcpProductionData.getProduction().getWaterCut());
					}
				}
				
				if(jedis.hexists("PCPDeviceTodayData".getBytes(), key.getBytes())){
					PCPDeviceTodayData deviceTodayData =(PCPDeviceTodayData) SerializeObjectUnils.unserizlize(jedis.hget("PCPDeviceTodayData".getBytes(), key.getBytes()));
					deviceTodayData.getPCPCalculateList().add(responseData);
					jedis.hset("PCPDeviceTodayData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
				}else{
					PCPDeviceTodayData deviceTodayData=new PCPDeviceTodayData();
					deviceTodayData.setId(deviceId);
					deviceTodayData.setPCPCalculateList(new ArrayList<PCPCalculateResponseData>());
					deviceTodayData.setAcquisitionItemInfoList(new ArrayList<AcquisitionItemInfo>());
					deviceTodayData.getPCPCalculateList().add(responseData);
					jedis.hset("PCPDeviceTodayData".getBytes(), key.getBytes(), SerializeObjectUnils.serialize(deviceTodayData));
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			OracleJdbcUtis.closeDBConnection(conn, pstmt, rs);
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public static void loadUIKitAccessToken(){
		String appKey=Config.getInstance().configFile.getAp().getVideo().getAppKey();
		String appSecret=Config.getInstance().configFile.getAp().getVideo().getSecret();
		String url="https://open.ys7.com/api/lapp/token/get";
		String requestData="{\"appKey\":\""+appKey+"\",\"appSecret\":\""+appSecret+"\"}";
		String responseData=StringManagerUtils.sendPostMethod(url+"?appKey="+appKey+"&appSecret="+appSecret, requestData,"utf-8",0,0);
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<AccessToken>() {}.getType();
		AccessToken accessToken=gson.fromJson(responseData, type);
		
		Jedis jedis=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			jedis.set("UIKitAccessToken".getBytes(), SerializeObjectUnils.serialize(accessToken));
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
	}
	
	public static AccessToken getUIKitAccessToken(){
		Jedis jedis=null;
		AccessToken accessToken=null;
		try {
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("UIKitAccessToken".getBytes())){
				loadUIKitAccessToken();
			}else{
				accessToken=(AccessToken)SerializeObjectUnils.unserizlize(jedis.get("UIKitAccessToken".getBytes()));
				if(accessToken!=null&&"200".equalsIgnoreCase(accessToken.getCode())){
					long now=new Date().getTime();
					if(now>accessToken.getData().getExpireTime()){
						loadUIKitAccessToken();
					}
				}
			}
			accessToken=(AccessToken)SerializeObjectUnils.unserizlize(jedis.get("UIKitAccessToken".getBytes()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(jedis!=null&&jedis.isConnected()){
				jedis.close();
			}
		}
		return accessToken;
	}
	
	public static class CalItem implements Serializable {
		private static final long serialVersionUID = 1L;
		public String name;
		public String code;
		public String unit;
		public int dataType;
		
		public CalItem(String name,String code, String unit, int dataType) {
			super();
			this.name = name;
			this.code = code;
			this.unit = unit;
			this.dataType = dataType;
		}
		public CalItem(String name,String unit) {
			super();
			this.name = name;
			this.unit = unit;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUnit() {
			return unit;
		}
		public void setUnit(String unit) {
			this.unit = unit;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public int getDataType() {
			return dataType;
		}
		public void setDataType(int dataType) {
			this.dataType = dataType;
		}
	}
}
