package com.gao.thread.calculate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.gao.model.calculate.PCPCalculateResponseData;
import com.gao.model.calculate.CommResponseData;
import com.gao.model.calculate.ElectricCalculateResponseData;
import com.gao.model.calculate.EnergyCalculateResponseData;
import com.gao.model.calculate.TimeEffResponseData;
import com.gao.model.drive.RTUDriveConfig;
import com.gao.tast.EquipmentDriverServerTast;
import com.gao.tast.EquipmentDriverServerTast.AcquisitionData;
import com.gao.tast.EquipmentDriverServerTast.ClientUnit;
import com.gao.tast.EquipmentDriverServerTast.UnitData;
import com.gao.utils.Config;
import com.gao.utils.Config2;
import com.gao.utils.OracleJdbcUtis;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ProtocolModbusThread extends ProtocolBasicThread{
	private ClientUnit clientUnit;
	private String url=Config.getInstance().configFile.getServer().getAccessPath()+"/graphicalUploadController/saveRTUAcquisitionData";
	private String tiemEffUrl=Config.getInstance().configFile.getAgileCalculate().getRun()[0];
	private String commUrl=Config.getInstance().configFile.getAgileCalculate().getCommunication()[0];
	private String energyUrl=Config.getInstance().configFile.getAgileCalculate().getEnergy()[0];
	private RTUDriveConfig driveConfig;
	private InputStream is=null;
	private OutputStream os=null;
	private boolean releaseResourceSign=false;
	public ProtocolModbusThread(int threadId, ClientUnit clientUnit,RTUDriveConfig driveConfig) {
		super();
		this.threadId = threadId;
		this.clientUnit = clientUnit;
		this.driveConfig = driveConfig;
		init(clientUnit.socket);
	}
	private void init(Socket socket){
		try {
			is=socket.getInputStream();
			os=socket.getOutputStream();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	@SuppressWarnings("static-access")
	public void run(){
		clientUnit.setSign(1);
        int rc=0;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer recvBuff=new StringBuffer();
        EquipmentDriverServerTast beeTechDriverServerTast=EquipmentDriverServerTast.getInstance();
        int readTimeout=1000*60;//socket read超时时间
        Gson gson = new Gson();
        byte[] writeCommand={0x00,0x03,0x00,0x00,0x00,0x06,0x01,0x06,0x00,0x03,(byte) 0x88,(byte) 0x88};
        if(driveConfig.getProtocol()==1){
        	writeCommand=new byte[]{0x00,0x03,0x00,0x00,0x00,0x06,0x01,0x06,0x00,0x03,(byte) 0x88,(byte) 0x88};
        }else if(driveConfig.getProtocol()==2){
        	writeCommand=new byte[]{0x01,0x06,0x00,0x03,(byte) 0x88,(byte) 0x88,0x1F,(byte) 0xAC};
        }
        while(!(isExit||this.interrupted()||is==null||os==null)){
        	//获取输入流，并读取客户端信息
            try {
    			byte[] recByte=new byte[256];
    			byte[] readByte=new byte[12];
    			is=clientUnit.socket.getInputStream();
    			os=clientUnit.socket.getOutputStream();
    			boolean wellReaded=false;
    			if(clientUnit.unitDataList.size()==0){//未注册过，读取注册包进行注册
    				rc=this.readSocketConnReg(clientUnit.socket, readTimeout*10, recByte,is);
    				if(rc==-1){//断开连接
        				System.out.println("第一次读取心跳失败，断开连接,释放资源");
        				this.releaseResource(is,os);
        				break;
        			}
    				String revMacStr="";
    				if("BeeTech".equalsIgnoreCase(driveConfig.getDriverCode())){
    					if((recByte[0]&0xFF)==0xAA&&(recByte[1]&0xFF)==0x01){
    						byte[] macByte=new byte[10];
        					for(int i=0;i<10;i++){
        						macByte[i]=recByte[i+2];
        					}
        					revMacStr=new String(macByte);
    					}
    				}else if("SunMoonStandardDrive".equalsIgnoreCase(driveConfig.getDriverCode())){
    					if((recByte[0]&0xFF)==0xAA&&(recByte[1]&0xFF)==0x01){
    						byte[] macByte=new byte[11];
        					for(int i=0;i<11;i++){
        						macByte[i]=recByte[i+2];
        					}
        					revMacStr=new String(macByte);
    					}
    				}else {
    					if((recByte[0]&0xFF)==0xAA&&(recByte[1]&0xFF)==0x01){
    						int end=0;
    						for(int i=0;i<recByte.length;i++){
    							if((recByte[i]&0xFF)==0x0D){
    								end=i+1;
    								break;
    							}
    						}
    						int l=rc-3;
    						if(rc>end){
    							l=end-3;
    						}
    						byte[] macByte=new byte[l];
        					for(int i=0;i<macByte.length;i++){
        						macByte[i]=recByte[i+2];
        					}
        					revMacStr=new String(macByte);
    					}
    				}
    				
    				if(StringManagerUtils.isNotNull(revMacStr)){//接收到注册包
    					boolean isRun=false;
						for(int j=0;j<EquipmentDriverServerTast.clientUnitList.size();j++){//遍历已连接的客户端
							if(EquipmentDriverServerTast.clientUnitList.get(j).socket!=null){//如果已连接
								for(int k=0;k<EquipmentDriverServerTast.clientUnitList.get(j).unitDataList.size();k++){
									if(revMacStr.equals(EquipmentDriverServerTast.clientUnitList.get(j).unitDataList.get(k).driverAddr)){//查询原有设备地址和新地址的连接，如存在断开资源，释放资源
										if(EquipmentDriverServerTast.clientUnitList.get(j).thread!=null){
											EquipmentDriverServerTast.clientUnitList.get(j).thread.interrupt();
											EquipmentDriverServerTast.clientUnitList.get(j).thread.isExit=true;
											isRun=true;
											break;
										}
									}
								}
							}
							if(isRun){
								break;
							}
						}
    					
    					for(int i=0;i<EquipmentDriverServerTast.units.size();i++){
    						if(revMacStr.equalsIgnoreCase(beeTechDriverServerTast.units.get(i).driverAddr) 
    								&& beeTechDriverServerTast.units.get(i).getRtuDriveConfig()!=null
    								&& !(beeTechDriverServerTast.units.get(i).getRtuDriveConfig().getDriverCode().toUpperCase().contains("KAFKA"))
    								){
    							System.out.println(beeTechDriverServerTast.units.get(i).wellName+"上线");
    							clientUnit.unitDataList.add(beeTechDriverServerTast.units.get(i));
    							clientUnit.unitDataList.get(clientUnit.unitDataList.size()-1).setCommStatus(1);
    							clientUnit.unitDataList.get(clientUnit.unitDataList.size()-1).recvPackageCount+=1;
    							clientUnit.unitDataList.get(clientUnit.unitDataList.size()-1).recvPackageSize+=(64+14);
    							if(!StringManagerUtils.getCurrentTime().equals(clientUnit.unitDataList.get(clientUnit.unitDataList.size()-1).currentDate)){//如果跨天保存数据
    		    	    			saveCommLog(clientUnit.unitDataList.get(clientUnit.unitDataList.size()-1));
    							}
    						}
    					}
    					if(clientUnit.unitDataList.size()==0){//未找到匹配的井
    						System.out.println("线程"+this.threadId+"未找到匹配的井，断开连接,释放资源:"+StringManagerUtils.bytesToHexString(recByte,revMacStr.length()+3)+":"+revMacStr);
            				this.releaseResource(is,os);
            				wellReaded=false;
            				break;
    					}else{
    						String AcqTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
							String updateDiscreteComm="update tbl_rpc_discrete_latest t set t.commstatus=1,t.acqTime=to_date('"+AcqTime+"','yyyy-mm-dd hh24:mi:ss')  "
									+ " where t.wellId in (select well.id from tbl_wellinformation well where upper(well.drivercode) not like '%KAFKA%' and well.driveraddr='"+revMacStr+"') ";
							Connection conn=OracleJdbcUtis.getConnection();
							Statement stmt=null;
							try {
								stmt = conn.createStatement();
								int result=stmt.executeUpdate(updateDiscreteComm);
    							conn.close();
    							stmt.close();
							} catch (SQLException e) {
								try {
									conn.close();
									if(stmt!=null){
										stmt.close();
									}
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								e.printStackTrace();
							}
    					}
        			}
    				continue;
    			}else{
    				//循环读取功图点数、采集时间、冲次、冲程数据 
    				
//    				if(isConnectionClose(clientUnit.socket)){
//    					System.out.println("线程"+this.threadId+"通信关闭断开连接");
//    					this.releaseResource(is,os);
//        				wellReaded=false;
//        				break;
//    				}
    				for(int i=0;i<clientUnit.unitDataList.size();i++){
    					String AcqTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
    					//启停井控制
    					if(clientUnit.unitDataList.get(i).runStatusControl!=0
    							&&clientUnit.unitDataList.get(i).runStatusControl!=1//不执行启抽操作
    							&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getRunControl()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getRunControl().getAddress(), clientUnit.unitDataList.get(i).runStatusControl,driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setRunStatusControl(0);
							
							//写操作口令验证
							rc=this.writeSocketData(clientUnit.socket,writeCommand ,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证发送失败:"+StringManagerUtils.bytesToHexString(readByte,12));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							
							
							rc=this.writeSocketData(clientUnit.socket, readByte,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"启停井指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,12));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取启停控制返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    					}
    					//功图采集周期控制
    					if(clientUnit.unitDataList.get(i).FSDiagramIntervalControl>0&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFSDiagramAcquisitionInterval()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFSDiagramAcquisitionInterval().getAddress(), clientUnit.unitDataList.get(i).FSDiagramIntervalControl,driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setFSDiagramIntervalControl(0);;
							
							//写操作口令验证
							rc=this.writeSocketData(clientUnit.socket,writeCommand ,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证发送失败:"+StringManagerUtils.bytesToHexString(readByte,12));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							
							rc=this.writeSocketData(clientUnit.socket, readByte,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"功图采集周期设置指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,12));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图采集周期设置返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
    					}
    					//变频频率控制
    					if(clientUnit.unitDataList.get(i).FrequencyControl>0&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSetFrequency()!=null){
    						wellReaded=true;
							readByte=this.getWriteFloatData(clientUnit.unitDataList.get(i).UnitId, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSetFrequency().getAddress(), clientUnit.unitDataList.get(i).FrequencyControl,driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setFrequencyControl(0);
							
							//写操作口令验证
							System.out.println("变频控制密码："+StringManagerUtils.bytesToHexString(writeCommand,writeCommand.length));
							rc=this.writeSocketData(clientUnit.socket,writeCommand ,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证发送失败:"+StringManagerUtils.bytesToHexString(readByte,12));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
							System.out.println("变频控制密码回应："+StringManagerUtils.bytesToHexString(recByte,recByte.length));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							System.out.println("发送变频控制指令："+StringManagerUtils.bytesToHexString(readByte,readByte.length));
							rc=this.writeSocketData(clientUnit.socket, readByte,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"变频频率控制指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,14));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							clientUnit.unitDataList.get(i).sendPackageCount+=1;
							clientUnit.unitDataList.get(i).sendPackageSize+=17;
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取变频频率控制返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					System.out.println("发送变频控制指令回应："+StringManagerUtils.bytesToHexString(recByte,recByte.length));
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    					}
    					
    					//平衡调节远程触发控制
    					if(clientUnit.unitDataList.get(i).balanceControlModeControl>0&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceControlMode()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceControlMode().getAddress(), clientUnit.unitDataList.get(i).getBalanceControlModeControl(),driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setBalanceControlModeControl(0);
							
							//写操作口令验证
							rc=this.writeSocketData(clientUnit.socket,writeCommand ,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证发送失败:"+StringManagerUtils.bytesToHexString(readByte,12));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							
							rc=this.writeSocketData(clientUnit.socket, readByte,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"平衡远程调节触发状态控制指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,14));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							clientUnit.unitDataList.get(i).sendPackageCount+=1;
							clientUnit.unitDataList.get(i).sendPackageSize+=17;
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡远程调节触发状态控制返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    					}
    					
    					//平衡调节计算方式控制
    					if(clientUnit.unitDataList.get(i).balanceCalculateModeControl>0&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceCalculateMode()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceCalculateMode().getAddress(), clientUnit.unitDataList.get(i).getBalanceCalculateModeControl(),driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setBalanceCalculateModeControl(0);
							
							//写操作口令验证
							rc=this.writeSocketData(clientUnit.socket,writeCommand ,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证发送失败:"+StringManagerUtils.bytesToHexString(readByte,12));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							
							rc=this.writeSocketData(clientUnit.socket, readByte,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"平衡计算方式控制指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,14));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							clientUnit.unitDataList.get(i).sendPackageCount+=1;
							clientUnit.unitDataList.get(i).sendPackageSize+=17;
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡计算方式控制返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    					}
    					
    					//重心远离支点每拍调节时间控制
    					if(clientUnit.unitDataList.get(i).balanceAwayTimeControl>0&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceAwayTime()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceAwayTime().getAddress(), clientUnit.unitDataList.get(i).getBalanceAwayTimeControl(),driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setBalanceAwayTimeControl(0);
							
							//写操作口令验证
							rc=this.writeSocketData(clientUnit.socket,writeCommand ,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证发送失败:"+StringManagerUtils.bytesToHexString(readByte,12));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							
							rc=this.writeSocketData(clientUnit.socket, readByte,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"重心远离支点每拍调节时间控制指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,14));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							clientUnit.unitDataList.get(i).sendPackageCount+=1;
							clientUnit.unitDataList.get(i).sendPackageSize+=17;
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取重心远离支点每拍调节时间控制返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    					}
    					
    					//重心接近支点每拍调节时间控制
    					if(clientUnit.unitDataList.get(i).balanceCloseTimeControl>0&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceCloseTime()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceCloseTime().getAddress(), clientUnit.unitDataList.get(i).getBalanceCloseTimeControl(),driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setBalanceCloseTimeControl(0);
							
							//写操作口令验证
							rc=this.writeSocketData(clientUnit.socket,writeCommand ,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证发送失败:"+StringManagerUtils.bytesToHexString(readByte,12));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							
							rc=this.writeSocketData(clientUnit.socket, readByte,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"重心接近支点每拍调节时间控制指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,14));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							clientUnit.unitDataList.get(i).sendPackageCount+=1;
							clientUnit.unitDataList.get(i).sendPackageSize+=17;
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取重心接近支点每拍调节时间控制返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    					}
    					
    					//参与平衡计算冲程次数控制
    					if(clientUnit.unitDataList.get(i).balanceStrokeCountControl>0&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceStrokeCount()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceStrokeCount().getAddress(), clientUnit.unitDataList.get(i).getBalanceStrokeCountControl(),driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setBalanceStrokeCountControl(0);
							
							//写操作口令验证
							rc=this.writeSocketData(clientUnit.socket,writeCommand ,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证发送失败:"+StringManagerUtils.bytesToHexString(readByte,12));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							
							rc=this.writeSocketData(clientUnit.socket, readByte,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"参与平衡计算冲程次数控制指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,14));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							clientUnit.unitDataList.get(i).sendPackageCount+=1;
							clientUnit.unitDataList.get(i).sendPackageSize+=17;
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取参与平衡计算冲程次数控制返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    					}
    					
    					//平衡调节上限控制
    					if(clientUnit.unitDataList.get(i).balanceOperationUpLimitControl>0&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceOperationUpLimit()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceOperationUpLimit().getAddress(), clientUnit.unitDataList.get(i).getBalanceOperationUpLimitControl(),driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setBalanceOperationUpLimitControl(0);
							
							//写操作口令验证
							rc=this.writeSocketData(clientUnit.socket,writeCommand ,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证发送失败:"+StringManagerUtils.bytesToHexString(readByte,12));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							
							rc=this.writeSocketData(clientUnit.socket, readByte,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"平衡调节上限控制指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,14));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							clientUnit.unitDataList.get(i).sendPackageCount+=1;
							clientUnit.unitDataList.get(i).sendPackageSize+=17;
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡调节上限控制返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    					}
    					
    					//平衡调节下限控制
    					if(clientUnit.unitDataList.get(i).balanceOperationDownLimitControl>0&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceOperationDownLimit()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceOperationDownLimit().getAddress(), clientUnit.unitDataList.get(i).getBalanceOperationDownLimitControl(),driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setBalanceOperationDownLimitControl(0);
							
							//写操作口令验证
							rc=this.writeSocketData(clientUnit.socket,writeCommand ,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证发送失败:"+StringManagerUtils.bytesToHexString(readByte,12));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"写操作口令验证返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							
							rc=this.writeSocketData(clientUnit.socket, readByte,os,clientUnit.unitDataList.get(i));
							if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"平衡调节下限控制指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,14));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							clientUnit.unitDataList.get(i).sendPackageCount+=1;
							clientUnit.unitDataList.get(i).sendPackageSize+=17;
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡调节下限控制返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    					}
    					
    					long readTime=0;
						if(StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).getAcquisitionData().getReadTime())){
							readTime=format.parse(clientUnit.unitDataList.get(i).getAcquisitionData().getReadTime()).getTime();
						}
						//当前采集时间与上次读取时间差值大于离散数据采集周期时，读取离散数据
//						if("长庆现场智能油田测试井".equals(clientUnit.unitDataList.get(i).getWellName())){
//							System.out.println("当前时间戳："+AcqTime+","+format.parse(AcqTime).getTime());
//							System.out.println("上次时间戳："+clientUnit.unitDataList.get(i).getAcquisitionData().getReadTime()+","+readTime);
//							System.out.println("时间差："+(format.parse(AcqTime).getTime()-readTime));
//							System.out.println("采集周期："+clientUnit.unitDataList.get(i).getAcqCycle_Discrete());
//						}
						
    					if(format.parse(AcqTime).getTime()-readTime>=clientUnit.unitDataList.get(i).getAcqCycle_Discrete()){
    						clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime(AcqTime);
    						int runSatus=0;
        					float TubingPressure=0;
        					float CasingPressure=0;
        					float BackPressure=0;
        					float WellHeadFluidTemperature=0;
        					float ProducingfluidLevel=0;//动液面
        					float WaterCut=0;//含水率
        					float CurrentA=0;
        					float CurrentB=0;
        					float CurrentC=0;
        					float VoltageA=0;
        					float VoltageB=0;
        					float VoltageC=0;
        					float ActivePowerConsumption=0;
        					float ReactivePowerConsumption=0;
        					float ActivePower=0;
        					float ReactivePower=0;
        					float ReversePower=0;
        					float PowerFactor=0;
        					int acquisitionCycle=0;
        					int point=0;
        					int FSDiagramSetPointCount=0;
        					float SPM=0;
        					float Stroke=0;
        					float RPM=0;
        					float Torque=0;
        					float SetFrequency=0;
        					float RunFrequency=0;
        					
        					int balanceAutoControl=0;
        					int spmAutoControl=0;
        					int balanceFrontLimit=0;
        					int balanceAfterLimit=0;
        					int balanceControlMode=0;
        					int balanceCalculateMode=0;
        					int balanceAwayTime=0;
        					int balanceCloseTime=0;
        					int balanceAwayTimePerBeat=0;
        					int balanceCloseTimePerBeat=0;
        					int balanceStrokeCount=0;
        					int balanceOperationUpLimit=0;
        					int balanceOperationDownLimit=0;
        					
        					String diagramAcqTime="1970-01-01 08:00:00";
        					
        					String prodTableName="tbl_rpc_productiondata_latest";
    						String discreteTableName="tbl_rpc_discrete_latest";
    						if(clientUnit.unitDataList.get(i).getLiftingType()>=400&&clientUnit.unitDataList.get(i).getLiftingType()<500){//螺杆泵井
    							prodTableName="tbl_pcp_productiondata_latest";
        						discreteTableName="tbl_pcp_discrete_latest";
    						}
    						boolean hasProData=false;
    						String updateProdData="update "+prodTableName+" t set t.acqTime=to_date('"+AcqTime+"','yyyy-mm-dd hh24:mi:ss')";
    						String updateDailyData="";
    						String updateDiscreteData="update "+discreteTableName+" t set t.commStatus=1,t.acqTime=to_date('"+AcqTime+"','yyyy-mm-dd hh24:mi:ss')";
    						
        					clientUnit.unitDataList.get(i).getAcquisitionData().setAcqTime(AcqTime);
        					//如果是抽油机读取功图点数、采集时间、冲次、冲程数据 
        					if(clientUnit.unitDataList.get(i).getLiftingType()>=200&&clientUnit.unitDataList.get(i).getLiftingType()<300){
        						//读取功图采集间隔
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getFSDiagramAcquisitionInterval()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFSDiagramAcquisitionInterval().getAddress()>40000){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFSDiagramAcquisitionInterval().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFSDiagramAcquisitionInterval().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图采集间隔发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图采集间隔数据异常,rc="+rc);
        								break;
        							}else{
        								acquisitionCycle=getUnsignedShort(recByte,0, driveConfig.getProtocol());
        								clientUnit.unitDataList.get(i).getAcquisitionData().setAcquisitionCycle(acquisitionCycle);
        								updateDiscreteData+=",t.acqCycle_Diagram="+acquisitionCycle;
        							}
        						}
        						//读取功图设置点数
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getFSDiagramSetPointCount()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFSDiagramSetPointCount().getAddress()>40000){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFSDiagramSetPointCount().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFSDiagramSetPointCount().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图点数发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图点数数据异常,rc="+rc);
        								break;
        							}else{
        								FSDiagramSetPointCount=getUnsignedShort(recByte,0, driveConfig.getProtocol());
        								clientUnit.unitDataList.get(i).getAcquisitionData().setFSDiagramSetPointCount(FSDiagramSetPointCount);
        							}
        						}
        						
        						//读取功图实际点数
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getFSDiagramPointCount()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFSDiagramPointCount().getAddress()>40000){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFSDiagramPointCount().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFSDiagramPointCount().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图点数发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图点数数据异常,rc="+rc);
        								break;
        							}else{
        								point=getUnsignedShort(recByte,0, driveConfig.getProtocol());
        								clientUnit.unitDataList.get(i).getAcquisitionData().setPoint(point);
        							}
        						}
        						//读取功图采集时间
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getAcqTime()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getAcqTime().getAddress()>40000){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getAcqTime().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getAcqTime().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图采集时间发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图采集时间数据异常,rc="+rc);
        								break;
        							}else{
        								diagramAcqTime=BCD2TimeString(recByte, driveConfig.getProtocol());
        								clientUnit.unitDataList.get(i).getAcquisitionData().setGtcjsj(diagramAcqTime);
        							}
        						}
        						//读取功图冲次
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getSPM()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSPM().getAddress()>40000){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSPM().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSPM().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图冲次发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图冲次数据异常,rc="+rc);
        								break;
        							}else{
        								SPM=getFloat(recByte,0, driveConfig.getProtocol());
        								clientUnit.unitDataList.get(i).getAcquisitionData().setSPM(SPM);
        							}
        						}
        						//读取功图冲程
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getStroke()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getStroke().getAddress()>40000){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getStroke().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getStroke().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图冲程发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图冲程数据异常,rc="+rc);
        								break;
        							}else{
        								Stroke=getFloat(recByte,0, driveConfig.getProtocol());;
                    					clientUnit.unitDataList.get(i).getAcquisitionData().setStroke(Stroke);
        							}
        						}
        						
        						
        						
        						//平衡调节远程状态
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getBalaceControlStatus()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalaceControlStatus()!=null){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalaceControlStatus().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalaceControlStatus().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡调节远程状态发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡调节远程状态数据异常,rc="+rc);
        								break;
        							}else{
        								int index=0;
        								if(clientUnit.unitDataList.get(i).getRtuDriveConfig().getProtocol()==1){
        									index=10;
        								}else{
        									index=4;
        								}
        								balanceAutoControl=(short) (0x0000 | (0x01 & recByte[index]));  
        								spmAutoControl=(short) (0x0000 | (0x02 & recByte[index])>>1);  
        								balanceFrontLimit=(short) (0x0000 | (0x04 & recByte[index])>>2);  
        								balanceAfterLimit=(short) (0x0000 | (0x08 & recByte[index])>>3);  
                    					clientUnit.unitDataList.get(i).getAcquisitionData().setBalanceAutoControl(balanceAutoControl);
                    					clientUnit.unitDataList.get(i).getAcquisitionData().setSpmAutoControl(spmAutoControl);
                    					clientUnit.unitDataList.get(i).getAcquisitionData().setBalanceFrontLimit(balanceFrontLimit);
                    					clientUnit.unitDataList.get(i).getAcquisitionData().setBalanceAfterLimit(balanceAfterLimit);
                    					updateDiscreteData+=",t.balanceAutoControl="+balanceAutoControl;
                    					updateDiscreteData+=",t.spmAutoControl="+spmAutoControl;
                    					updateDiscreteData+=",t.balanceFrontLimit="+balanceFrontLimit;
                    					updateDiscreteData+=",t.balanceAfterLimit="+balanceAfterLimit;
        							}
        						}
        						
        						//平衡调节远程触发控制
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getBalanceControlMode()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceControlMode()!=null){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceControlMode().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceControlMode().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡调节远程触发控制发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡调节远程触发控制数据异常,rc="+rc);
        								break;
        							}else{
        								balanceControlMode=getUnsignedShort(recByte,0, driveConfig.getProtocol());
                    					clientUnit.unitDataList.get(i).getAcquisitionData().setBalanceControlMode(balanceControlMode);
                    					updateDiscreteData+=",t.balanceControlMode="+balanceControlMode;
        							}
        						}
        						
        						//平衡计算方式
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getBalanceCalculateMode()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceCalculateMode()!=null){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceCalculateMode().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceCalculateMode().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡计算方式发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡计算方式数据异常,rc="+rc);
        								break;
        							}else{
        								balanceCalculateMode=getUnsignedShort(recByte,0, driveConfig.getProtocol());
                    					clientUnit.unitDataList.get(i).getAcquisitionData().setBalanceCalculateMode(balanceCalculateMode);
                    					updateDiscreteData+=",t.balanceCalculateMode="+balanceCalculateMode;
        							}
        						}
        						
        						//重心远离支点调节时间
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getBalanceAwayTime()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceAwayTime()!=null){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceAwayTime().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceAwayTime().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取重心远离支点每拍调节时间发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取重心远离支点每拍调节时间数据异常,rc="+rc);
        								break;
        							}else{
        								balanceAwayTime=getUnsignedShort(recByte,0, driveConfig.getProtocol());
                    					clientUnit.unitDataList.get(i).getAcquisitionData().setBalanceAwayTime(balanceAwayTime);
                    					updateDiscreteData+=",t.balanceAwayTime="+balanceAwayTime;
        							}
        						}
        						
        						//重心接近支点调节时间
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getBalanceCloseTime()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceCloseTime()!=null){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceCloseTime().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceCloseTime().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取重心接近支点每拍调节时间发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取重心接近支点每拍调节时间数据异常,rc="+rc);
        								break;
        							}else{
        								balanceCloseTime=getUnsignedShort(recByte,0, driveConfig.getProtocol());
                    					clientUnit.unitDataList.get(i).getAcquisitionData().setBalanceCloseTime(balanceCloseTime);
                    					updateDiscreteData+=",t.balanceCloseTime="+balanceCloseTime;
        							}
        						}
        						
        						//重心远离支点每拍调节时间
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getBalanceAwayTimePerBeat()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceAwayTimePerBeat()!=null){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceAwayTimePerBeat().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceAwayTimePerBeat().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取重心远离支点每拍调节时间发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取重心远离支点每拍调节时间数据异常,rc="+rc);
        								break;
        							}else{
        								balanceAwayTimePerBeat=getUnsignedShort(recByte,0, driveConfig.getProtocol());
                    					clientUnit.unitDataList.get(i).getAcquisitionData().setBalanceAwayTimePerBeat(balanceAwayTimePerBeat);
                    					updateDiscreteData+=",t.balanceAwayTimePerBeat="+balanceAwayTimePerBeat;
        							}
        						}
        						
        						//重心接近支点每拍调节时间
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getBalanceCloseTimePerBeat()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceCloseTimePerBeat()!=null){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceCloseTimePerBeat().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceCloseTimePerBeat().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取重心接近支点每拍调节时间发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取重心接近支点每拍调节时间数据异常,rc="+rc);
        								break;
        							}else{
        								balanceCloseTimePerBeat=getUnsignedShort(recByte,0, driveConfig.getProtocol());
                    					clientUnit.unitDataList.get(i).getAcquisitionData().setBalanceCloseTimePerBeat(balanceCloseTimePerBeat);
                    					updateDiscreteData+=",t.balanceCloseTimePerBeat="+balanceCloseTimePerBeat;
        							}
        						}
        						
        						//参与平衡度计算的冲程次数
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getBalanceStrokeCount()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceStrokeCount()!=null){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceStrokeCount().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceStrokeCount().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取参与平衡度计算的冲程次数发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取参与平衡度计算的冲程次数数据异常,rc="+rc);
        								break;
        							}else{
        								balanceStrokeCount=getUnsignedShort(recByte,0, driveConfig.getProtocol());
                    					clientUnit.unitDataList.get(i).getAcquisitionData().setBalanceStrokeCount(balanceStrokeCount);
                    					updateDiscreteData+=",t.balanceStrokeCount="+balanceStrokeCount;
        							}
        						}
        						
        						//平衡调节上限
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getBalanceOperationUpLimit()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceOperationUpLimit()!=null){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceOperationUpLimit().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceOperationUpLimit().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡调节上限发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡调节上限数据异常,rc="+rc);
        								break;
        							}else{
        								balanceOperationUpLimit=getUnsignedShort(recByte,0, driveConfig.getProtocol());
                    					clientUnit.unitDataList.get(i).getAcquisitionData().setBalanceOperationUpLimit(balanceOperationUpLimit);
                    					updateDiscreteData+=",t.balanceOperationUpLimit="+balanceOperationUpLimit;
        							}
        						}
        						
        						//平衡调节下限
        						if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getBalanceOperationDownLimit()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceOperationDownLimit()!=null){
        							wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceOperationDownLimit().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceOperationDownLimit().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡调节下限发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡调节下限数据异常,rc="+rc);
        								break;
        							}else{
        								balanceOperationDownLimit=getUnsignedShort(recByte,0, driveConfig.getProtocol());
                    					clientUnit.unitDataList.get(i).getAcquisitionData().setBalanceOperationDownLimit(balanceOperationDownLimit);
                    					updateDiscreteData+=",t.balanceOperationDownLimit="+balanceOperationDownLimit;
        							}
        						}
        					}	
        					
        					//读取油井运行状态
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getRunStatus()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getRunStatus().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getRunStatus().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getRunStatus().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取运行状态发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取运行状态数据异常,rc="+rc);
    								break;
    							}else{
    								runSatus= getUnsignedShort(recByte,0, driveConfig.getProtocol());
    							}
        					}
        					
        					//读取油压
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getTubingPressure()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getTubingPressure().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getTubingPressure().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getTubingPressure().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取油压发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取油压数据异常,rc="+rc);
    								break;
    							}else{
    								TubingPressure=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setTubingPressure(TubingPressure);
    								updateDiscreteData+=",t.TubingPressure="+TubingPressure;
    								updateProdData+=",t.tubingPressure="+TubingPressure;
    								hasProData=true;
    							}
        					}
        					//读取套压
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getCasingPressure()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCasingPressure().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCasingPressure().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCasingPressure().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取套压发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取套压数据异常,rc="+rc);
    								break;
    							}else{
    								CasingPressure=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setCasingPressure(CasingPressure);
    								updateDiscreteData+=",t.CasingPressure="+CasingPressure;
    								updateProdData+=",t.casingPressure="+CasingPressure;
    								hasProData=true;
    							}
        					}
        					//读取回压
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getBackPressure()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBackPressure().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBackPressure().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBackPressure().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取回压发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取回压数据异常,rc="+rc);
    								break;
    							}else{
    								BackPressure=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setBackPressure(BackPressure);
    								updateDiscreteData+=",t.BackPressure="+BackPressure;
    								updateProdData+=",t.backPressure="+BackPressure;
    								hasProData=true;
    							}
        					}
        					//读取井口油温
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getWellHeadFluidTemperature()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getWellHeadFluidTemperature().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getWellHeadFluidTemperature().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getWellHeadFluidTemperature().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取井口油温发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取井口油温数据异常,rc="+rc);
    								break;
    							}else{
    								WellHeadFluidTemperature=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setWellHeadFluidTemperature(WellHeadFluidTemperature);
    								updateDiscreteData+=",t.WellHeadFluidTemperature="+WellHeadFluidTemperature;
    								updateProdData+=",t.wellHeadFluidTemperature="+WellHeadFluidTemperature;
    								hasProData=true;
    							}
        					}
        					//读取动液面
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getProducingfluidLevel()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getProducingfluidLevel().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getProducingfluidLevel().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getProducingfluidLevel().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取动液面发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取动液面数据异常,rc="+rc);
    								break;
    							}else{
    								ProducingfluidLevel=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setProducingfluidLevel(ProducingfluidLevel);
    								updateProdData+=",t.producingfluidLevel="+ProducingfluidLevel;
    								hasProData=true;
    							}
        					}
        					//读取含水率
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getWaterCut()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getWaterCut().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getWaterCut().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getWaterCut().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取含水率发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取含水率数据异常,rc="+rc);
    								break;
    							}else{
    								WaterCut=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setWaterCut(WaterCut);
    								updateProdData+=",t.waterCut="+WaterCut;
    								hasProData=true;
    							}
        					}
        					
        					//读取电参数据
        					//读取A相电流
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getCurrentA()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCurrentA().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCurrentA().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCurrentA().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取A相电流发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取A相电流数据异常,rc="+rc);
    								break;
    							}else{
    								CurrentA=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setCurrentA(CurrentA);
    								updateDiscreteData+=",t.Ia="+CurrentA;
    							}
        					}
        					//读取B相电流
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getCurrentB()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCurrentB().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCurrentB().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCurrentB().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取B相电流发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取B相电流数据异常,rc="+rc);
    								break;
    							}else{
    								CurrentB=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setCurrentB(CurrentB);
    								updateDiscreteData+=",t.Ib="+CurrentB;
    							}
        					}
        					//读取C相电流
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getCurrentC()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCurrentC().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCurrentC().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCurrentC().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取C相电流发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取C相电流数据异常,rc="+rc);
    								break;
    							}else{
    								CurrentC=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setCurrentC(CurrentC);
    								updateDiscreteData+=",t.Ic="+CurrentC;
    							}
        					}
        					//读取A相电压
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getVoltageA()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getVoltageA().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getVoltageA().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getVoltageA().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取A相电压发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取A相电压数据异常,rc="+rc);
    								break;
    							}else{
    								
    								VoltageA=getFloat(recByte,0, driveConfig.getProtocol());
    								if(VoltageA<0){
    									VoltageA=0;
    								}else if(VoltageA>500){
    									VoltageA=500;
    								}
//    								System.out.println(clientUnit.unitDataList.get(i).getWellName()+"井读取A相电压返回数据："+StringManagerUtils.bytesToHexString(recByte,recByte.length));
//    								System.out.println(clientUnit.unitDataList.get(i).getWellName()+"井读取A相电压："+VoltageA);
    								clientUnit.unitDataList.get(i).getAcquisitionData().setVoltageA(VoltageA);
    								updateDiscreteData+=",t.Va="+VoltageA;
    							}
        					}
        					//读取B相电压
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getVoltageB()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getVoltageB().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getVoltageB().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getVoltageB().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取B相电压发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取B相电压数据异常,rc="+rc);
    								break;
    							}else{
    								VoltageB=getFloat(recByte,0, driveConfig.getProtocol());
    								if(VoltageB<0){
    									VoltageB=0;
    								}else if(VoltageB>500){
    									VoltageB=500;
    								}
    								clientUnit.unitDataList.get(i).getAcquisitionData().setVoltageB(VoltageB);
    								updateDiscreteData+=",t.Vb="+VoltageB;
    							}
        					}
        					//读取C相电压
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getVoltageC()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getVoltageC().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getVoltageC().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getVoltageC().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取C相电压发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取C相电压数据异常,rc="+rc);
    								break;
    							}else{
    								VoltageC=getFloat(recByte,0, driveConfig.getProtocol());
    								if(VoltageC<0){
    									VoltageC=0;
    								}else if(VoltageC>500){
    									VoltageC=500;
    								}
    								clientUnit.unitDataList.get(i).getAcquisitionData().setVoltageC(VoltageC);
    								updateDiscreteData+=",t.Vc="+VoltageC;
    							}
        					}
        					//读取有功功耗
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getActivePowerConsumption()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getActivePowerConsumption().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getActivePowerConsumption().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getActivePowerConsumption().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取有功功耗发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取有功功耗数据异常,rc="+rc);
    								break;
    							}else{
    								ActivePowerConsumption=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setActivePowerConsumption(ActivePowerConsumption);
    							}
        					}
        					//读取无功功耗
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getReactivePowerConsumption()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getReactivePowerConsumption().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getReactivePowerConsumption().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getReactivePowerConsumption().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取无功功耗发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取无功功耗数据异常,rc="+rc);
    								break;
    							}else{
    								ReactivePowerConsumption=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setReactivePowerConsumption(ReactivePowerConsumption);
    							}
        					}
        					//读取有功功率
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getActivePower()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getActivePower().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getActivePower().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getActivePower().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取有功功率发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取有功功率数据异常,rc="+rc);
    								break;
    							}else{
    								ActivePower=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setActivePower(ActivePower);
    								updateDiscreteData+=",t.wattSum="+ActivePower;
    							}
        					}
        					//读取无功功率
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getReactivePower()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getReactivePower().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getReactivePower().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getReactivePower().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取无功功率发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取无功功率数据异常,rc="+rc);
    								break;
    							}else{
    								ReactivePower=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setReactivePower(ReactivePower);
    								updateDiscreteData+=",t.varSum="+ReactivePower;
    							}
        					}
        					//读取反向功率
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getReversePower()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getReversePower().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getReversePower().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getReversePower().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取反向功率发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取反向功率数据异常,rc="+rc);
    								break;
    							}else{
    								ReversePower=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setReversePower(ReversePower);
    								updateDiscreteData+=",t.ReversePower="+ReversePower;
    							}
        					}
        					//读取功率因数
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getPowerFactor()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getPowerFactor().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getPowerFactor().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getPowerFactor().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功率因数发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功率因数数据异常,rc="+rc);
    								break;
    							}else{
    								PowerFactor=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setPowerFactor(PowerFactor);
    								updateDiscreteData+=",t.pfSum="+PowerFactor;
    							}
        					}
        					
        					//读取变频频率
        					//读取变频设置频率
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getSetFrequency()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSetFrequency().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSetFrequency().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSetFrequency().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取变频设置频率发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取变频设置频率据异常,rc="+rc);
    								break;
    							}else{
    								SetFrequency=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setSetFrequency(SetFrequency);
    								updateDiscreteData+=",t.frequencySetValue="+SetFrequency;
    							}
        					}
        					//读取变频运行频率
        					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getRunFrequency()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getRunFrequency().getAddress()>40000){
        						wellReaded=true;
    							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getRunFrequency().getAddress(),
        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getRunFrequency().getLength(),
        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
    							if(rc==-1||rc==-2){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取变频设置频率发送或接收失败,rc="+rc);
    								this.releaseResource(is,os);
                    				wellReaded=false;
                    				break;
    							}else if(rc==-3){
    								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取变频设置频率据异常,rc="+rc);
    								break;
    							}else{
    								RunFrequency=getFloat(recByte,0, driveConfig.getProtocol());
    								clientUnit.unitDataList.get(i).getAcquisitionData().setRunFrequency(RunFrequency);
    								updateDiscreteData+=",t.frequencyRunValue="+RunFrequency;
    							}
        					}
        					
        					//进行通信计算
        					String commRequest="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+clientUnit.unitDataList.get(i).getWellName()+"\",";
        					if(StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).lastDisAcqTime)&&StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).lastCommRange)){
        						commRequest+= "\"Last\":{"
    									+ "\"AcqTime\": \""+clientUnit.unitDataList.get(i).lastDisAcqTime+"\","
    									+ "\"CommStatus\": "+(clientUnit.unitDataList.get(i).lastCommStatus==1?true:false)+","
    									+ "\"CommEfficiency\": {"
    									+ "\"Efficiency\": "+clientUnit.unitDataList.get(i).lastCommTimeEfficiency+","
    									+ "\"Time\": "+clientUnit.unitDataList.get(i).lastCommTime+","
    									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(clientUnit.unitDataList.get(i).lastCommRange+"")+""
    									+ "}"
    									+ "},";
        					}	
        					commRequest+= "\"Current\": {"
									+ "\"AcqTime\":\""+AcqTime+"\","
									+ "\"CommStatus\":true"
									+ "}"
									+ "}";
        					String commResponse=StringManagerUtils.sendPostMethod(commUrl, commRequest,"utf-8");
        					java.lang.reflect.Type type = new TypeToken<CommResponseData>() {}.getType();
        					CommResponseData commResponseData=gson.fromJson(commResponse, type);
        					if(commResponseData!=null&&commResponseData.getResultStatus()==1){
        						if(commResponseData.getCurrent().getCommEfficiency().getRangeString().indexOf("-;")>=0){
        							System.out.println("通信返回数据出现：-;");
        							System.out.println("通信请求数据："+commRequest);
        							System.out.println("通信返回数据："+commResponse);
        							commResponseData.getCurrent().getCommEfficiency().setRangeString(commResponseData.getCurrent().getCommEfficiency().getRangeString().replaceAll("-;", ""));
        						}
        						if(commResponseData.getCurrent().getCommEfficiency().getRangeString().length()>2000){
        							commResponseData.getCurrent().getCommEfficiency().setRangeString("");
        						}
        						clientUnit.unitDataList.get(i).getAcquisitionData().setRealTimeCommTime(commResponseData.getCurrent().getCommEfficiency().getTime());
        						clientUnit.unitDataList.get(i).getAcquisitionData().setRealTimeCommTimeEfficiency(commResponseData.getCurrent().getCommEfficiency().getEfficiency());
        						clientUnit.unitDataList.get(i).getAcquisitionData().setRealTimeCommRangeString(commResponseData.getCurrent().getCommEfficiency().getRangeString());
        						
//        						clientUnit.unitDataList.get(i).lastDisAcqTime=AcqTime;
        						clientUnit.unitDataList.get(i).lastCommStatus=commResponseData.getCurrent().getCommStatus()?1:0;
        						clientUnit.unitDataList.get(i).lastCommTime=commResponseData.getCurrent().getCommEfficiency().getTime();
        						clientUnit.unitDataList.get(i).lastCommTimeEfficiency=commResponseData.getCurrent().getCommEfficiency().getEfficiency();
        						clientUnit.unitDataList.get(i).lastCommRange=commResponseData.getCurrent().getCommEfficiency().getRangeString();
        					}else{
        						System.out.println("comm error");
        						System.out.println("通信请求数据："+commRequest);
    							System.out.println("通信返回数据："+commResponse);
        					}
        					//进行时率计算
        					TimeEffResponseData timeEffResponseData=null;
        					int RunStatus=runSatus;
        					//时率来源为电参计算时，运行状态取电参计算结果
        					if(clientUnit.unitDataList.get(i).getRunTimeEfficiencySource()==2){//时率来源为电参时
        						if(CurrentA<2&&CurrentB<2&&CurrentB<2){
        							RunStatus=0;
        						}else{
        							RunStatus=1;
        						}
        					}else if(clientUnit.unitDataList.get(i).getRunTimeEfficiencySource()==3){//时率来源为转速计算时
        						if (clientUnit.unitDataList.get(i).lastRPM>0){
        							RunStatus=1;
        						}else{
        							RunStatus=0;
        						}
        					}
        					String tiemEffRequest="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+clientUnit.unitDataList.get(i).getWellName()+"\",";
        					if(StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).lastDisAcqTime)&&StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).lastRunRange)){
        						tiemEffRequest+= "\"Last\":{"
    									+ "\"AcqTime\": \""+clientUnit.unitDataList.get(i).lastDisAcqTime+"\","
    									+ "\"RunStatus\": "+(clientUnit.unitDataList.get(i).lastRunStatus==1?true:false)+","
    									+ "\"RunEfficiency\": {"
    									+ "\"Efficiency\": "+clientUnit.unitDataList.get(i).lastRunTimeEfficiency+","
    									+ "\"Time\": "+clientUnit.unitDataList.get(i).lastRunTime+","
    									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(clientUnit.unitDataList.get(i).lastRunRange+"")+""
    									+ "}"
    									+ "},";
        					}	
        					tiemEffRequest+= "\"Current\": {"
									+ "\"AcqTime\":\""+AcqTime+"\","
									+ "\"RunStatus\":"+(RunStatus==1?true:false)+""
									+ "}"
									+ "}";
        					//时率来源非人工录入时时，此时进行时率计算
        					String timeEffResponse="";
        					if(clientUnit.unitDataList.get(i).getRunTimeEfficiencySource()!=0){
        						timeEffResponse=StringManagerUtils.sendPostMethod(tiemEffUrl, tiemEffRequest,"utf-8");
            					type = new TypeToken<TimeEffResponseData>() {}.getType();
            					timeEffResponseData=gson.fromJson(timeEffResponse, type);
        					}
        					if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
        						if(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString().indexOf("-;")>=0){
        							System.out.println("时率返回数据出现：-;");
        							System.out.println("时率请求数据："+tiemEffRequest);
        							System.out.println("时率返回数据："+timeEffResponse);
        							timeEffResponseData.getCurrent().getRunEfficiency().setRangeString(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString().replaceAll("-;", ""));
        						}
        						clientUnit.unitDataList.get(i).getAcquisitionData().setRealTimeRunTime(timeEffResponseData.getCurrent().getRunEfficiency().getTime());
        						clientUnit.unitDataList.get(i).getAcquisitionData().setRealTimeRunTimeEfficiency(timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency());
        						clientUnit.unitDataList.get(i).getAcquisitionData().setRealTimeRunRangeString(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString());
        						
//        						clientUnit.unitDataList.get(i).lastDisAcqTime=AcqTime;
        						clientUnit.unitDataList.get(i).lastRunStatus=timeEffResponseData.getCurrent().getRunStatus()?1:0;
        						clientUnit.unitDataList.get(i).lastRunTime=timeEffResponseData.getCurrent().getRunEfficiency().getTime();
        						clientUnit.unitDataList.get(i).lastRunTimeEfficiency=timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency();
        						clientUnit.unitDataList.get(i).lastRunRange=timeEffResponseData.getCurrent().getRunEfficiency().getRangeString();
        					}else{
        						System.out.println("run error");
        						System.out.println("时率请求数据："+tiemEffRequest);
    							System.out.println("时率返回数据："+timeEffResponse);
        					}
        					
        					//进行电量计算
        					EnergyCalculateResponseData energyCalculateResponseData=null;
        					String energyRequest="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+clientUnit.unitDataList.get(i).getWellName()+"\",";
        					if(StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).lastDisAcqTime)){
        						energyRequest+= "\"Last\":{"
    									+ "\"AcqTime\": \""+clientUnit.unitDataList.get(i).lastDisAcqTime+"\","
    									+ "\"Total\":{"
    									+ "\"KWattH\":"+clientUnit.unitDataList.get(i).lastTotalKWattH+","
    									+ "\"PKWattH\":"+clientUnit.unitDataList.get(i).lastTotalPKWattH+","
    									+ "\"NKWattH\":"+clientUnit.unitDataList.get(i).lastTotalNKWattH+","
    									+ "\"KVarH\":"+clientUnit.unitDataList.get(i).lastTotalKVarH+","
    									+ "\"PKVarH\":"+clientUnit.unitDataList.get(i).lastTotalPKVarH+","
    									+ "\"NKVarH\":"+clientUnit.unitDataList.get(i).lastTotalNKVarH+","
    									+ "\"KVAH\":"+clientUnit.unitDataList.get(i).lastTotalKVAH+""
    									+ "},\"Today\":{"
    									+ "\"KWattH\":"+clientUnit.unitDataList.get(i).lastTodayKWattH+","
    									+ "\"PKWattH\":"+clientUnit.unitDataList.get(i).lastTodayPKWattH+","
    									+ "\"NKWattH\":"+clientUnit.unitDataList.get(i).lastTodayNKWattH+","
    									+ "\"KVarH\":"+clientUnit.unitDataList.get(i).lastTodayKVarH+","
    									+ "\"PKVarH\":"+clientUnit.unitDataList.get(i).lastTodayPKVarH+","
    									+ "\"NKVarH\":"+clientUnit.unitDataList.get(i).lastTodayNKVarH+","
    									+ "\"KVAH\":"+clientUnit.unitDataList.get(i).lastTodayKVAH+""
    									+ "}"
    									+ "},";
        					}	
        					energyRequest+= "\"Current\": {"
									+ "\"AcqTime\":\""+AcqTime+"\","
									+ "\"Total\":{"
									+ "\"KWattH\":"+ActivePowerConsumption+","
									+ "\"PKWattH\":"+0+","
									+ "\"NKWattH\":"+0+","
									+ "\"KVarH\":"+ReactivePowerConsumption+","
									+ "\"PKVarH\":"+0+","
									+ "\"NKVarH\":"+0+","
									+ "\"KVAH\":"+0+""
									+ "}"
									+ "}"
									+ "}";
        					String energyResponse=StringManagerUtils.sendPostMethod(energyUrl, energyRequest,"utf-8");
        					type = new TypeToken<EnergyCalculateResponseData>() {}.getType();
        					energyCalculateResponseData=gson.fromJson(energyResponse, type);
        					if(energyCalculateResponseData!=null&&energyCalculateResponseData.getResultStatus()==1){
//        						clientUnit.unitDataList.get(i).lastDisAcqTime=AcqTime;
        						clientUnit.unitDataList.get(i).lastTotalKWattH=energyCalculateResponseData.getCurrent().getTotal().getKWattH();
        						clientUnit.unitDataList.get(i).lastTotalPKWattH=energyCalculateResponseData.getCurrent().getTotal().getPKWattH();
        						clientUnit.unitDataList.get(i).lastTotalNKWattH=energyCalculateResponseData.getCurrent().getTotal().getNKWattH();
        						clientUnit.unitDataList.get(i).lastTotalKVarH=energyCalculateResponseData.getCurrent().getTotal().getKVarH();
        						clientUnit.unitDataList.get(i).lastTotalPKVarH=energyCalculateResponseData.getCurrent().getTotal().getPKVarH();
        						clientUnit.unitDataList.get(i).lastTotalNKVarH=energyCalculateResponseData.getCurrent().getTotal().getNKVarH();
        						clientUnit.unitDataList.get(i).lastTotalKVAH=energyCalculateResponseData.getCurrent().getTotal().getKVAH();
        						
        						clientUnit.unitDataList.get(i).lastTodayKWattH=energyCalculateResponseData.getCurrent().getToday().getKWattH();
        						clientUnit.unitDataList.get(i).lastTodayPKWattH=energyCalculateResponseData.getCurrent().getToday().getPKWattH();
        						clientUnit.unitDataList.get(i).lastTodayNKWattH=energyCalculateResponseData.getCurrent().getToday().getNKWattH();
        						clientUnit.unitDataList.get(i).lastTodayKVarH=energyCalculateResponseData.getCurrent().getToday().getKVarH();
        						clientUnit.unitDataList.get(i).lastTodayPKVarH=energyCalculateResponseData.getCurrent().getToday().getPKVarH();
        						clientUnit.unitDataList.get(i).lastTodayNKVarH=energyCalculateResponseData.getCurrent().getToday().getNKVarH();
        						clientUnit.unitDataList.get(i).lastTodayKVAH=energyCalculateResponseData.getCurrent().getToday().getKVAH();
        					}else{
        						System.out.println("energy error");
        						System.out.println("请求数据："+energyRequest);
    							System.out.println("返回数据："+energyResponse);
        					}
        					clientUnit.unitDataList.get(i).lastDisAcqTime=AcqTime;
        					long hisDataInterval=0;
    						if(StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).getAcquisitionData().getSaveTime())){
    							hisDataInterval=format.parse(clientUnit.unitDataList.get(i).getAcquisitionData().getSaveTime()).getTime();
    						}
    						if(commResponseData!=null&&timeEffResponseData!=null&&
        							(RunStatus!=clientUnit.unitDataList.get(i).acquisitionData.runStatus//运行状态发生改变
        							||format.parse(AcqTime).getTime()-hisDataInterval>=clientUnit.unitDataList.get(i).getSaveCycle_Discrete()//比上次保存时间大于5分钟
        							)
        						){
        						clientUnit.unitDataList.get(i).acquisitionData.setRunStatus(RunStatus);
        						Connection conn=OracleJdbcUtis.getConnection();
        						Statement stmt=null;
        						PreparedStatement ps=null;
            					updateProdData+=" where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+clientUnit.unitDataList.get(i).wellName+"') ";
            					
                						
        						if(commResponseData!=null&&commResponseData.getResultStatus()==1){
        							updateDiscreteData+=" ,t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
        								+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
//        								+ " ,t.commRange= '"+commResponseData.getCurrent().getCommEfficiency().getRangeString()+"'";
        							if(timeEffResponseData.getDaily()!=null&&StringManagerUtils.isNotNull(timeEffResponseData.getDaily().getDate())){
        								
        							}
            					}
        						if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
        							updateDiscreteData+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
        								+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
//        								+ " ,t.runRange= '"+timeEffResponseData.getCurrent().getRunEfficiency().getRangeString()+"'";
        							if(timeEffResponseData.getDaily()!=null&&StringManagerUtils.isNotNull(timeEffResponseData.getDaily().getDate())){
        								
        							}
            					}
        						if(energyCalculateResponseData!=null&&energyCalculateResponseData.getResultStatus()==1){
        							updateDiscreteData+=",t.TotalKWattH= "+energyCalculateResponseData.getCurrent().getTotal().getKWattH()
            								+ ",t.TotalPKWattH= "+energyCalculateResponseData.getCurrent().getTotal().getPKWattH()
            								+ ",t.TotalNKWattH= "+energyCalculateResponseData.getCurrent().getTotal().getNKWattH()
            								+ ",t.TotalKVarH= "+energyCalculateResponseData.getCurrent().getTotal().getKVarH()
            								+ ",t.TotalPKVarH= "+energyCalculateResponseData.getCurrent().getTotal().getPKVarH()
            								+ ",t.TotalNKVarH= "+energyCalculateResponseData.getCurrent().getTotal().getNKVarH()
            								+ ",t.TotalKVAH= "+energyCalculateResponseData.getCurrent().getTotal().getKVAH()
            								+ ",t.TodayKWattH= "+energyCalculateResponseData.getCurrent().getToday().getKWattH()
            								+ ",t.TodayPKWattH= "+energyCalculateResponseData.getCurrent().getToday().getPKWattH()
            								+ ",t.TodayNKWattH= "+energyCalculateResponseData.getCurrent().getToday().getNKWattH()
            								+ ",t.TodayKVarH= "+energyCalculateResponseData.getCurrent().getToday().getKVarH()
            								+ ",t.TodayPKVarH= "+energyCalculateResponseData.getCurrent().getToday().getPKVarH()
            								+ ",t.TodayNKVarH= "+energyCalculateResponseData.getCurrent().getToday().getNKVarH()
            								+ ",t.TodayKVAH= "+energyCalculateResponseData.getCurrent().getToday().getKVAH();
        							if(energyCalculateResponseData.getDaily()!=null&&StringManagerUtils.isNotNull(energyCalculateResponseData.getDaily().getDate())){
        								updateDailyData="update tbl_rpc_total_day t set t.todayKWattH="+energyCalculateResponseData.getDaily().getKWattH()
        										+ ",t.TodayPKWattH= "+energyCalculateResponseData.getDaily().getPKWattH()
                								+ ",t.TodayNKWattH= "+energyCalculateResponseData.getDaily().getNKWattH()
                								+ ",t.TodayKVarH= "+energyCalculateResponseData.getDaily().getKVarH()
                								+ ",t.TodayPKVarH= "+energyCalculateResponseData.getDaily().getPKVarH()
                								+ ",t.TodayNKVarH= "+energyCalculateResponseData.getDaily().getNKVarH()
                								+ ",t.TodayKVAH= "+energyCalculateResponseData.getDaily().getKVAH()
        										+ " where t.calculatedate=to_date('"+energyCalculateResponseData.getDaily().getDate()+"','yyyy-mm-dd') "
        								         +" and t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+clientUnit.unitDataList.get(i).wellName+"') ";
        							}
        						}else{
        							updateDiscreteData+= " ,t.totalKWattH= "+ActivePowerConsumption
        									+ " ,t.totalKVarH= "+ReactivePowerConsumption;
        						}
        						
        						//如果时率来源非人工录入且电参计算成功，更新运行状态
        						if(clientUnit.unitDataList.get(i).getRunTimeEfficiencySource()!=0){
        							updateDiscreteData+=",t.runStatus="+RunStatus;
        						}
        						updateDiscreteData+=" where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+clientUnit.unitDataList.get(i).wellName+"') ";
        						try {
    								stmt = conn.createStatement();
    								int result=stmt.executeUpdate(updateDiscreteData);
    								if(commResponseData!=null&&commResponseData.getResultStatus()==1&&timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
            							String updateCommAndRunRangeClobSql="update tbl_rpc_discrete_latest t set t.commrange=?,t.runrange=? where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+clientUnit.unitDataList.get(i).wellName+"') ";
            							List<String> clobCont=new ArrayList<String>();
            							clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
            							clobCont.add(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString());
            							
            							ps=conn.prepareStatement(updateCommAndRunRangeClobSql);
            							result=OracleJdbcUtis.executeSqlUpdateClob(conn, ps, updateCommAndRunRangeClobSql, clobCont);
            						}
    								if(hasProData)
    									result=stmt.executeUpdate(updateProdData);
    								if(StringManagerUtils.isNotNull(updateDailyData)){
    									result=stmt.executeUpdate(updateDailyData);
    								}
    								if(ps!=null){
    									ps.close();
    								}
    								if(stmt!=null){
										stmt.close();
									}
    								conn.close();
    								clientUnit.unitDataList.get(i).getAcquisitionData().setRunStatus(RunStatus);
    								clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime(AcqTime);
    							} catch (SQLException e) {
    								e.printStackTrace();
    								try {
    									if(stmt!=null){
    										stmt.close();
    									}
    									if(ps!=null){
        									ps.close();
        								}
    									conn.close();
    								} catch (SQLException e1) {
    									e1.printStackTrace();
    								}
    							}
        					}
        					if(clientUnit.unitDataList.get(i).getLiftingType()>=400&&clientUnit.unitDataList.get(i).getLiftingType()<500){//如果是螺杆泵读取转速和扭矩
        						//读取螺杆泵转速、扭矩
        						//读取螺杆泵转速
            					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getRPM()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getRPM().getAddress()>40000){
            						wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getRPM().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getRPM().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取变频设置频率发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取变频设置频率据异常,rc="+rc);
        								break;
        							}else{
        								RPM=getFloat(recByte,0, driveConfig.getProtocol());
        								clientUnit.unitDataList.get(i).getAcquisitionData().setRPM(RPM);
        								clientUnit.unitDataList.get(i).lastRPM=RPM;
        							}
            					}
            					//读取螺杆泵扭矩
            					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getTorque()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getTorque().getAddress()>40000){
            						wellReaded=true;
        							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getTorque().getAddress(),
            								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getTorque().getLength(),
            								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        							if(rc==-1||rc==-2){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取变频设置频率发送或接收失败,rc="+rc);
        								this.releaseResource(is,os);
                        				wellReaded=false;
                        				break;
        							}else if(rc==-3){
        								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取变频设置频率据异常,rc="+rc);
        								break;
        							}else{
        								Torque=getFloat(recByte,0, driveConfig.getProtocol());
        								clientUnit.unitDataList.get(i).getAcquisitionData().setTorque(Torque);
        							}
            					}
            					long screwPumpLastSaveTime=0;
        						if(StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).getAcquisitionData().getScrewPumpSaveTime())){
        							screwPumpLastSaveTime=format.parse(clientUnit.unitDataList.get(i).getAcquisitionData().getScrewPumpSaveTime()).getTime();
        						}
        						//当前采集时间与上次保存时间差值大于保存时间时，保存螺杆泵数据
            					if(format.parse(AcqTime).getTime()-screwPumpLastSaveTime>=clientUnit.unitDataList.get(i).getScrewPumpDataSaveInterval()){
            						recvBuff=new StringBuffer();
            						StringBuffer proParamsBuff=new StringBuffer();
            						StringBuffer elecBuff=new StringBuffer();
            						proParamsBuff.append("\"ProductionParameter\": {");
            						elecBuff.append("\"Electric\": {");
            						recvBuff.append("{\"WellName\":\""+clientUnit.unitDataList.get(i).getWellName()+"\",\"LiftingType\":"+clientUnit.unitDataList.get(i).getLiftingType()+",\"AcqTime\":\""+AcqTime+"\",");
            						
            						
                					proParamsBuff.append("\"TubingPressure\":"+TubingPressure+",");
                					proParamsBuff.append("\"CasingPressure\":"+CasingPressure+",");
                					proParamsBuff.append("\"BackPressure\":"+BackPressure+",");
                					proParamsBuff.append("\"WellHeadFluidTemperature\":"+WellHeadFluidTemperature+",");
                					proParamsBuff.append("\"bpszpl\":"+SetFrequency+",");
                					proParamsBuff.append("\"bpyxpl\":"+RunFrequency+"}");
                					
                					recvBuff.append(proParamsBuff+",");
                					
                					elecBuff.append("\"CurrentA\":"+CurrentA+",");
                					elecBuff.append("\"CurrentB\":"+CurrentB+",");
                					elecBuff.append("\"CurrentC\":"+CurrentC+",");
                					elecBuff.append("\"VoltageA\":"+VoltageA+",");
                					elecBuff.append("\"VoltageB\":"+VoltageB+",");
                					elecBuff.append("\"VoltageC\":"+VoltageC+",");
                					elecBuff.append("\"ActivePowerConsumption\":"+ActivePowerConsumption+",");
                					elecBuff.append("\"ReactivePowerConsumption\":"+ReactivePowerConsumption+",");
                					elecBuff.append("\"ActivePower\":"+ActivePower+",");
                					elecBuff.append("\"ReactivePower\":"+ReactivePower+",");
                					elecBuff.append("\"ReversePower\":"+ReversePower+",");
                					elecBuff.append("\"PowerFactor\":"+PowerFactor+"}");
                					recvBuff.append(elecBuff+",");
                					recvBuff.append("\"ScrewPump\":{\"RPM\":"+RPM+",\"Torque\":"+Torque+"}");
                					recvBuff.append("}");
                					System.out.println("线程"+this.threadId+"解析读取数据:"+recvBuff.toString());
                					StringManagerUtils.sendPostMethod(url, recvBuff.toString(),"utf-8");
                					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime(AcqTime);
            					}
        					}else if(clientUnit.unitDataList.get(i).getLiftingType()>=200&&clientUnit.unitDataList.get(i).getLiftingType()<300){//如果是抽油机判断是否有新功图,如有则读取功图
        						long currentTimelong=0;
            					if(clientUnit.unitDataList.get(i).getDiagramAcqTime()!=null){
            						currentTimelong = format.parse(clientUnit.unitDataList.get(i).getDiagramAcqTime()).getTime();//数据库中最新功图采集时间
            					}
            					long newTimelong = format.parse(diagramAcqTime).getTime();
            					
//            					System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"RTU中功图采集时间："+diagramAcqTime+",当前功图采集时间："+clientUnit.unitDataList.get(i).getDiagramAcqTime());
            					
            					if(newTimelong>currentTimelong&&newTimelong-currentTimelong>=clientUnit.unitDataList.get(i).getAcqCycle_Diagram()){//发现新功图
            						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"发现新功图");
            						clientUnit.unitDataList.get(i).setDiagramAcqTime(diagramAcqTime);
            						recvBuff=new StringBuffer();
            						StringBuffer recvFBuff=new StringBuffer();
            						StringBuffer recvSBuff=new StringBuffer();
            						StringBuffer recvABuff=new StringBuffer();
            						StringBuffer recvPBuff=new StringBuffer();
            						StringBuffer proParamsBuff=new StringBuffer();
            						StringBuffer elecBuff=new StringBuffer();
            						recvFBuff.append("\"F\": [");
            						recvSBuff.append("\"S\": [");
            						recvABuff.append("\"A\": [");
            						recvPBuff.append("\"P\": [");
            						proParamsBuff.append("\"ProductionParameter\": {");
            						elecBuff.append("\"Electric\": {");
            						recvBuff.append("{\"WellName\":\""+clientUnit.unitDataList.get(i).getWellName()+"\",\"LiftingType\":"+clientUnit.unitDataList.get(i).getLiftingType()+",\"AcqTime\":\""+diagramAcqTime+"\",");
            						
            						
                					proParamsBuff.append("\"TubingPressure\":"+TubingPressure+",");
                					proParamsBuff.append("\"CasingPressure\":"+CasingPressure+",");
                					proParamsBuff.append("\"BackPressure\":"+BackPressure+",");
                					proParamsBuff.append("\"WellHeadFluidTemperature\":"+WellHeadFluidTemperature+",");
                					proParamsBuff.append("\"bpszpl\":"+SetFrequency+",");
                					proParamsBuff.append("\"bpyxpl\":"+RunFrequency+"}");
                					
                					recvBuff.append(proParamsBuff+",");
                					
                					elecBuff.append("\"CurrentA\":"+CurrentA+",");
                					elecBuff.append("\"CurrentB\":"+CurrentB+",");
                					elecBuff.append("\"CurrentC\":"+CurrentC+",");
                					elecBuff.append("\"VoltageA\":"+VoltageA+",");
                					elecBuff.append("\"VoltageB\":"+VoltageB+",");
                					elecBuff.append("\"VoltageC\":"+VoltageC+",");
                					elecBuff.append("\"ActivePowerConsumption\":"+ActivePowerConsumption+",");
                					elecBuff.append("\"ReactivePowerConsumption\":"+ReactivePowerConsumption+",");
                					elecBuff.append("\"ActivePower\":"+ActivePower+",");
                					elecBuff.append("\"ReactivePower\":"+ReactivePower+",");
                					elecBuff.append("\"ReversePower\":"+ReversePower+",");
                					elecBuff.append("\"PowerFactor\":"+PowerFactor+"}");
                					recvBuff.append(elecBuff+",");
            						
                					
                					recvBuff.append("\"Diagram\":{\"AcqTime\":\""+diagramAcqTime+"\",\"AcquisitionCycle\":"+acquisitionCycle+",\"SPM\":"+SPM+","+"\"Stroke\":"+Stroke+",");
                					
            						//读取功图位移数据
                					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getSDiagram()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSDiagram().getAddress()>40000){
                						if(point==0)
                							point=(short)clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSDiagram().getLength();
                						for(int j=0;j*100<point;j++){
                							int length=100;
                							if(j*100+length>point){
                								length=point-j*100;
                							}
                							wellReaded=true;
                							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
                    								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSDiagram().getAddress()+j*100,
                    								length,
                    								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
                							if(rc==-1||rc==-2){
                								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图位移数据发送或接收失败,rc="+rc);
                								this.releaseResource(is,os);
                                				wellReaded=false;
                                				break;
                							}else if(rc==-3){
                								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图位移数据异常,rc="+rc);
                								break;
                							}else{
                								for(int k=0;k<length;k++){
                            						Float recvS=(float) (getShort(recByte,k*2, driveConfig.getProtocol())*clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSDiagram().getZoom());
                            						recvSBuff.append(recvS+",");
                            					}
                							}
                						}
                						if(recvSBuff.toString().endsWith(",")){
                							recvSBuff.deleteCharAt(recvSBuff.length() - 1);
                						}
                					}
                					recvSBuff.append("]");
                					clientUnit.unitDataList.get(i).getAcquisitionData().setRecvSBuff(recvSBuff.toString());
                					
                					//读取功图载荷数据
                					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getFDiagram()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFDiagram().getAddress()>40000){
                						if(point==0)
                							point=(short)clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFDiagram().getLength();
                						for(int j=0;j*100<point;j++){
                							int length=100;
                							if(j*100+length>point){
                								length=point-j*100;
                							}
                							wellReaded=true;
                							if(point>250){
                								rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
                        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSDiagram().getAddress()+500+j*100,
                        								length,
                        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
                							}else{
                								rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
                        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFDiagram().getAddress()+j*100,
                        								length,
                        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
                							}
                							
                							if(rc==-1||rc==-2){
                								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图载荷数据发送或接收失败,rc="+rc);
                								this.releaseResource(is,os);
                                				wellReaded=false;
                                				break;
                							}else if(rc==-3){
                								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图载荷数据异常,rc="+rc);
                								break;
                							}else{
                								for(int k=0;k<length;k++){
                            						Float recvF=(float) (getShort(recByte,k*2, driveConfig.getProtocol())*clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFDiagram().getZoom());
                            						recvFBuff.append(recvF+",");
                            					}
                							}
                						}
                						if(recvFBuff.toString().endsWith(",")){
                							recvFBuff.deleteCharAt(recvFBuff.length() - 1);
                						}
                					}
                					recvFBuff.append("]");
                					clientUnit.unitDataList.get(i).getAcquisitionData().setRecvFBuff(recvFBuff.toString());
                					//读取功图电流数据
                					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getADiagram()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getADiagram().getAddress()>40000){
                						if(point==0)
                							point=(short)clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getADiagram().getLength();
                						for(int j=0;j*100<point;j++){
                							int length=100;
                							if(j*100+length>point){
                								length=point-j*100;
                							}
                							wellReaded=true;
                							if(point>250){
                								rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
                        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSDiagram().getAddress()+1000+j*100,
                        								length,
                        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
                							}else{
                								rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
                        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getADiagram().getAddress()+j*100,
                        								length,
                        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
                							}
                							
                							if(rc==-1||rc==-2){
                								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图电流数据发送或接收失败,rc="+rc);
                								this.releaseResource(is,os);
                                				wellReaded=false;
                                				break;
                							}else if(rc==-3){
                								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图电流数据异常,rc="+rc);
                								break;
                							}else{
                								for(int k=0;k<length;k++){
                            						Float recvA=(float) (getShort(recByte,k*2, driveConfig.getProtocol())*clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getADiagram().getZoom());
                            						recvABuff.append(recvA+",");
                            					}
                							}
                						}
                						if(recvABuff.toString().endsWith(",")){
                							recvABuff.deleteCharAt(recvABuff.length() - 1);
                						}
                					}
                					recvABuff.append("]");
                					clientUnit.unitDataList.get(i).getAcquisitionData().setRecvABuff(recvABuff.toString());
                					//读取功图功率数据
                					if(clientUnit.unitDataList.get(i).getAcquisitionUnitData().getPDiagram()==1&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getPDiagram().getAddress()>40000){
                						if(point==0)
                							point=(short)clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getPDiagram().getLength();
                						for(int j=0;j*100<point;j++){
                							int length=100;
                							if(j*100+length>point){
                								length=point-j*100;
                							}
                							wellReaded=true;
                							if(point>250){
                								rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
                        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSDiagram().getAddress()+1500+j*100,
                        								length,
                        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
                							}else{
                								rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
                        								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getPDiagram().getAddress()+j*100,
                        								length,
                        								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
                							}
                							
                							if(rc==-1||rc==-2){
                								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图功率数据发送或接收失败,rc="+rc);
                								this.releaseResource(is,os);
                                				wellReaded=false;
                                				break;
                							}else if(rc==-3){
                								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图功率数据异常,rc="+rc);
                								break;
                							}else{
                								for(int k=0;k<length;k++){
                            						Float recvP=(float) (getShort(recByte,k*2, driveConfig.getProtocol())*clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getPDiagram().getZoom());
                            						recvPBuff.append(recvP+",");
                            					}
                							}
                						}
                						if(recvPBuff.toString().endsWith(",")){
                							recvPBuff.deleteCharAt(recvPBuff.length() - 1);
                						}
                					}
                					recvPBuff.append("]");
                					clientUnit.unitDataList.get(i).getAcquisitionData().setRecvPBuff(recvPBuff.toString());
                					
                					recvBuff.append(recvFBuff+","+recvSBuff+","+recvABuff+","+recvPBuff+"}}");
//                					System.out.println("线程"+this.threadId+"解析读取数据:"+recvBuff.toString());
                					StringManagerUtils.sendPostMethod(url, recvBuff.toString(),"utf-8");
                					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime(AcqTime);
                					wellReaded=true;
            					}
        					}
    					}
    				}
    			}
    			if(this.interrupted()){
            		throw new InterruptedException();
            	}else{
            		Thread.sleep(1000);
            	}
    		} catch (Exception e) {
    			e.printStackTrace();
    			if(!releaseResourceSign){//如果未释放资源
    				this.releaseResource(is,os);
    			}
				break;
    		}
        }
        if(!releaseResourceSign){
        	this.releaseResource(is,os);
        }
	}
	
	public  void releaseResource(InputStream is,OutputStream os){
		System.out.println("releaseResource");
		try {
			System.out.println("线程ID："+threadId+",线程名称："+Thread.currentThread().getName()+"释放资源！");
			isExit=true;
			releaseResourceSign=true;
			Connection conn=OracleJdbcUtis.getConnection();
			Statement stmt=null;
			PreparedStatement ps=null;
			stmt = conn.createStatement();
			Gson gson = new Gson();
			String AcqTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			
			for(int i=0;i<this.clientUnit.unitDataList.size();i++){
				clientUnit.unitDataList.get(i).acquisitionData=new  AcquisitionData();
				clientUnit.unitDataList.get(i).commStatus=0;
				clientUnit.unitDataList.get(i).runStatusControl=0;
				clientUnit.unitDataList.get(i).FSDiagramIntervalControl=0;
				clientUnit.unitDataList.get(i).FrequencyControl=0;
				clientUnit.unitDataList.get(i).acquisitionData.runStatus=0;
				//进行通信计算
				String commRequest="{"
						+ "\"AKString\":\"\","
						+ "\"WellName\":\""+clientUnit.unitDataList.get(i).getWellName()+"\",";
				if(StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).lastDisAcqTime)){
					commRequest+= "\"Last\":{"
							+ "\"AcqTime\": \""+clientUnit.unitDataList.get(i).lastDisAcqTime+"\","
							+ "\"CommStatus\": "+(clientUnit.unitDataList.get(i).lastCommStatus==1?true:false)+","
							+ "\"CommEfficiency\": {"
							+ "\"Efficiency\": "+clientUnit.unitDataList.get(i).lastCommTimeEfficiency+","
							+ "\"Time\": "+clientUnit.unitDataList.get(i).lastCommTime+","
							+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(clientUnit.unitDataList.get(i).lastCommRange+"")+""
							+ "}"
							+ "},";
				}	
				commRequest+= "\"Current\": {"
						+ "\"AcqTime\":\""+AcqTime+"\","
						+ "\"CommStatus\":false"
						+ "}"
						+ "}";
				String commResponse=StringManagerUtils.sendPostMethod(commUrl, commRequest,"utf-8");
				java.lang.reflect.Type type = new TypeToken<CommResponseData>() {}.getType();
				CommResponseData commResponseData=gson.fromJson(commResponse, type);
				String updateCommStatus="update tbl_rpc_discrete_latest t set t.commStatus=0,t.acqTime=to_date('"+AcqTime+"','yyyy-mm-dd hh24:mi:ss') ";
				if(commResponseData!=null&&commResponseData.getResultStatus()==1){
					updateCommStatus+=" ,t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
							+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
//							+ " ,t.commRange= '"+commResponseData.getCurrent().getCommEfficiency().getRangeString()+"'";
					
					clientUnit.unitDataList.get(i).lastDisAcqTime=AcqTime;
					clientUnit.unitDataList.get(i).lastCommStatus=commResponseData.getCurrent().getCommStatus()?1:0;
					clientUnit.unitDataList.get(i).lastCommTime=commResponseData.getCurrent().getCommEfficiency().getTime();
					clientUnit.unitDataList.get(i).lastCommTimeEfficiency=commResponseData.getCurrent().getCommEfficiency().getEfficiency();
					clientUnit.unitDataList.get(i).lastCommRange=commResponseData.getCurrent().getCommEfficiency().getRangeString();
				}
				updateCommStatus+=" where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+clientUnit.unitDataList.get(i).wellName+"') ";
				int result=stmt.executeUpdate(updateCommStatus);
				if(commResponseData!=null&&commResponseData.getResultStatus()==1){
					String updateCommAndRunRangeClobSql="update tbl_rpc_discrete_latest t set t.commrange=? where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+clientUnit.unitDataList.get(i).wellName+"') ";
					List<String> clobCont=new ArrayList<String>();
					clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
					ps=conn.prepareStatement(updateCommAndRunRangeClobSql);
					result=OracleJdbcUtis.executeSqlUpdateClob(conn, ps, updateCommAndRunRangeClobSql, clobCont);
				}
			}
			conn.close();
			if(stmt!=null){
				stmt.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(is!=null)
				is.close();
			if(os!=null)
				os.close();
			if(clientUnit.socket!=null)
				clientUnit.socket.close();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} finally{
			EquipmentDriverServerTast beeTechDriverServerTast=EquipmentDriverServerTast.getInstance();
			if(beeTechDriverServerTast.clientUnitList.size()>threadId){
				beeTechDriverServerTast.clientUnitList.remove(threadId);
				clientUnit=null;
				clientUnit=new ClientUnit();
				beeTechDriverServerTast.clientUnitList.add(threadId,clientUnit);
			}
		}
		
	}
	
	public byte[] getSendByteData(int id,int gnm,int startAddr,int length,int protocol){
		byte startAddrArr[]=StringManagerUtils.getByteArray((short)(startAddr-40001));
		byte lengthArr[]=StringManagerUtils.getByteArray((short)length);
		byte[] readByte=null;
		if(protocol==1){//modubus-tcp
			readByte=new byte[12];
			readByte[0]=startAddrArr[0];
			readByte[1]=startAddrArr[1];
			readByte[2]=0x00;
			readByte[3]=0x00;
			readByte[4]=0x00;
			readByte[5]=0x06;
			readByte[6]=(byte)id;
			readByte[7]=(byte)gnm;
			readByte[8]=startAddrArr[0];
			readByte[9]=startAddrArr[1];
			readByte[10]=lengthArr[0];
			readByte[11]=lengthArr[1];
		}else if(protocol==2){//modubus-rtu
			byte[] dataByte=new byte[6];
			dataByte[0]=(byte)id;
			dataByte[1]=(byte)gnm;
			dataByte[2]=startAddrArr[0];
			dataByte[3]=startAddrArr[1];
			dataByte[4]=lengthArr[0];
			dataByte[5]=lengthArr[1];
			byte[] CRC16=StringManagerUtils.getCRC16(dataByte);
			readByte=StringManagerUtils.linlByteArray(dataByte,CRC16);
		}
		return readByte;
		
		
	}
	
	public byte[] getWriteSingleRegisterByteData(int id,int gnm,int startAddr,int data,int protocol){
		byte startAddrArr[]=StringManagerUtils.getByteArray((short)(startAddr-40001));
		byte dataArr[]=StringManagerUtils.getByteArray((short)data);
		byte[] readByte=null;
		if(protocol==1){
			readByte=new byte[12];
			readByte[0]=startAddrArr[0];
			readByte[1]=startAddrArr[1];
			readByte[2]=0x00;
			readByte[3]=0x00;
			readByte[4]=0x00;
			readByte[5]=0x06;
			readByte[6]=(byte)id;
			readByte[7]=(byte)gnm;
			readByte[8]=startAddrArr[0];
			readByte[9]=startAddrArr[1];
			readByte[10]=dataArr[0];
			readByte[11]=dataArr[1];
		}else if(protocol==2){
			byte[] dataByte=new byte[6];
			dataByte[0]=(byte)id;
			dataByte[1]=(byte)gnm;
			dataByte[2]=startAddrArr[0];
			dataByte[3]=startAddrArr[1];
			dataByte[4]=dataArr[0];
			dataByte[5]=dataArr[1];
			byte[] CRC16=StringManagerUtils.getCRC16(dataByte);
			readByte=StringManagerUtils.linlByteArray(dataByte,CRC16);
		}
		return readByte;
	}
	
	public byte[] getWriteFloatData(int id,int startAddr,float data,int protocol){
		byte startAddrArr[]=StringManagerUtils.getByteArray((short)(startAddr-40001));
		byte dataArr[]=StringManagerUtils.getByteArray(data);
		byte[] readByte=null;
		if(protocol==1){
			readByte=new byte[17];
			readByte[0]=startAddrArr[0];
			readByte[1]=startAddrArr[1];
			readByte[2]=0x00;
			readByte[3]=0x00;
			readByte[4]=0x00;
			readByte[5]=0x0B;
			readByte[6]=(byte)id;
			readByte[7]=0x10;
			readByte[8]=startAddrArr[0];
			readByte[9]=startAddrArr[1];
			readByte[10]=0x00;
			readByte[11]=0x02;
			readByte[12]=0x04;
			if("SunMoonStandardDrive".equalsIgnoreCase(driveConfig.getDriverCode())){
				readByte[13]=dataArr[3];
				readByte[14]=dataArr[2];
				readByte[15]=dataArr[1];
				readByte[16]=dataArr[0];
    		}else{
    			readByte[13]=dataArr[0];
    			readByte[14]=dataArr[1];
    			readByte[15]=dataArr[2];
    			readByte[16]=dataArr[3];
    		}
			
		}else if(protocol==2){
			byte[] dataByte=new byte[11];
			dataByte[0]=(byte)id;
			dataByte[1]=0x10;
			dataByte[2]=startAddrArr[0];
			dataByte[3]=startAddrArr[1];
			dataByte[4]=0x00;
			dataByte[5]=0x02;
			dataByte[6]=0x04;
			if("SunMoonStandardDrive".equalsIgnoreCase(driveConfig.getDriverCode())){
				dataByte[7]=dataArr[3];
				dataByte[8]=dataArr[2];
				dataByte[9]=dataArr[1];
				dataByte[10]=dataArr[0];
    		}else{
    			dataByte[7]=dataArr[0];
    			dataByte[8]=dataArr[1];
    			dataByte[9]=dataArr[2];
    			dataByte[10]=dataArr[3];
    		}
			
			byte[] CRC16=StringManagerUtils.getCRC16(dataByte);
			readByte=StringManagerUtils.linlByteArray(dataByte,CRC16);
		}
		return readByte;
	}
	
	//socket 读取数据
    public int readSocketData(Socket socket,int timeout,byte[] recByte,InputStream is,UnitData unit){
    	int rc=0;
    	int i=0;
    	do{
    		try {
    			socket.setSoTimeout(timeout);
    	    	rc=is.read(recByte);
    	    	while(rc!=-1&&(recByte[0]&0xFF)==0xAA&&(recByte[1]&0xFF)==0x01){
    	    		unit.recvPackageCount+=1;
    	    		unit.recvPackageSize+=(64+rc);
    	    		if(!StringManagerUtils.getCurrentTime().equals(unit.currentDate)){//如果跨天保存数据
    	    			saveCommLog(unit);
					}
    				rc=is.read(recByte);
    			}
    	    	unit.recvPackageCount+=1;
	    		unit.recvPackageSize+=(64+rc);
	    		if(!StringManagerUtils.getCurrentTime().equals(unit.currentDate)){//如果跨天保存数据
	    			saveCommLog(unit);
				}
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			rc=-1;
    		}
    		i++;
    	}while(rc<=0&&i<2);
    	
    	return rc;
    }
    
    //读取心跳
    public int readSocketConnReg(Socket socket,int timeout,byte[] recByte,InputStream is){
    	int rc=0;
    	try {
			socket.setSoTimeout(timeout);
	    	rc=is.read(recByte);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
    	return rc;
    }
    
    public int sendAndReadData(InputStream is,OutputStream os,int readTimeout,int id,int gnm,int startAddr,int lengthOrData,byte[] recByte,UnitData unit,int protocol){
		byte[] readByte=new byte[12];
		readByte=this.getSendByteData(id, gnm, startAddr, lengthOrData,protocol);
		int rc=this.writeSocketData(clientUnit.socket, readByte,os,unit);
		int result=0;
		if(rc==-1){//断开连接
			result= -1;//发送数据失败
		}else{
			rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,unit);
			if(rc==-1){//断开连接
				result= -2;//读取数据失败
			}else if(recByte[7]==0x83){//读取异常
				result= -3;//读取异常
			}
		}
		if(result<0){
			System.out.println(unit.getWellName()+"读取数据异常,rc="+rc+",readByte="+StringManagerUtils.bytesToHexString(readByte,readByte.length));
		}else{
			result=rc;
		}
		return result;
    }
    
    //socket写数据
    public int writeSocketData(Socket socket,byte[] readByte,OutputStream os,UnitData unit){
    	int rc=0;
    	int i=0;
    	do{
    		try {
        		os.write(readByte);
    			os.flush();
    			rc=1;
    			unit.sendPackageCount+=1;
    			unit.sendPackageSize+=readByte.length;
    			if(!StringManagerUtils.getCurrentTime().equals(unit.currentDate)){//如果跨天保存数据
	    			saveCommLog(unit);
				}
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			rc=-1;
    			System.out.println(unit.getWellName()+"发送指令失败,i="+i+",readByte="+StringManagerUtils.bytesToHexString(readByte,readByte.length));
    			try {
					Thread.sleep(1*1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    		i++;
    	}while(rc!=1&&i<5);
    	
    	return rc;
    }
    
    /**
    * 判断是否断开连接，断开返回true,没有返回false
    * @param socket
    * @return
    */ 
    public Boolean isConnectionClose(Socket socket){ 
       try{ 
        socket.sendUrgentData(0xFF);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信 
        return false; 
       }catch(Exception se){ 
        return true; 
       } 
    } 
    
    public short getShort(byte[] arr,int index, int protocol) {  
    	short result=0;
    	if(protocol==1){
    		result=StringManagerUtils.getShort(arr, 9+index);
    	}else if(protocol==2){
    		result=StringManagerUtils.getShort(arr, 3+index);
    	}
        return result;
    } 
    
    public int getUnsignedShort(byte[] arr,int index, int protocol) {  
    	int result=0;
    	if(protocol==1){
    		result=StringManagerUtils.getUnsignedShort(arr, 9+index);
    	}else if(protocol==2){
    		result=StringManagerUtils.getUnsignedShort(arr, 3+index);
    	}
        return result;
    }
    
    public float getFloat(byte[] arr,int index, int protocol){  
    	float result=0;
    	if(protocol==1){
    		if("SunMoonStandardDrive".equalsIgnoreCase(driveConfig.getDriverCode())){
    			result=StringManagerUtils.getFloatLittle(arr, 9+index);
    		}else{
    			result=StringManagerUtils.getFloat(arr, 9+index);
    		}
    	}else if(protocol==2){
    		if("SunMoonStandardDrive".equalsIgnoreCase(driveConfig.getDriverCode())){
    			result=StringManagerUtils.getFloatLittle(arr, 3+index);
    		}else{
    			result=StringManagerUtils.getFloat(arr, 3+index);
    		}
    		
    	}
        return result;
    }  
    
    public String BCD2TimeString(byte[] arr, int protocol) {
        String result="";
        if(protocol==1){
    		result=StringManagerUtils.BCD2TimeString(arr, 9);
    	}else if(protocol==2){
    		result=StringManagerUtils.BCD2TimeString(arr, 3);
    	}
        return result;
    }
    
    public boolean saveCommLog(UnitData unit){
//    	Connection conn=OracleJdbcUtis.getConnection();
//		CallableStatement cs=null;
//		try {
//			cs = conn.prepareCall("{call PRO_SAVECOMMLOG(?,?,?,?,?,?)}");
//			cs.setString(1, unit.wellName);
//			cs.setString(2, unit.currentDate);
//			cs.setInt(3, unit.recvPackageCount);
//			cs.setInt(4, unit.recvPackageSize);
//			cs.setInt(5, unit.sendPackageCount);
//			cs.setInt(6, unit.sendPackageSize);
//			cs.executeUpdate();
//		} catch (SQLException e) {
//			try {
//				conn.close();
//				if(cs!=null){
//					cs.close();
//				}
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//			return false;
//		}finally{
//			unit.setRecvPackageCount(0);
//			unit.setRecvPackageSize(0);
//			unit.setSendPackageCount(0);
//			unit.setSendPackageSize(0);
//			unit.setCurrentDate(StringManagerUtils.getCurrentTime());
//			try {
//				if(cs!=null){
//					cs.close();
//				}
//				conn.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//		}
    	return true;
    }


	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	public ClientUnit getClientUnit() {
		return clientUnit;
	}

	public void setClientUnit(ClientUnit clientUnit) {
		this.clientUnit = clientUnit;
	}


	public RTUDriveConfig getDriveConfig() {
		return driveConfig;
	}


	public void setDriveConfig(RTUDriveConfig driveConfig) {
		this.driveConfig = driveConfig;
	}
	public boolean isExit() {
		return isExit;
	}
	public void setExit(boolean isExit) {
		this.isExit = isExit;
	}
	
}
