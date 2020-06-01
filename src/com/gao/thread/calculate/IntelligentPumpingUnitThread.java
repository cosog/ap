package com.gao.thread.calculate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

public class IntelligentPumpingUnitThread extends Thread{

	private int threadId;
	private ClientUnit clientUnit;
	private String saveDiagramurl=Config.getInstance().configFile.getServer().getAccessPath()+"/graphicalUploadController/saveRTUAcquisitionData";
	private String tiemEffUrl=Config.getInstance().configFile.getAgileCalculate().getRun()[0];
	private String commUrl=Config.getInstance().configFile.getAgileCalculate().getCommunication()[0];
	private String energyUrl=Config.getInstance().configFile.getAgileCalculate().getEnergy()[0];
	private RTUDriveConfig driveConfig;
	private boolean isExit=false;
	public IntelligentPumpingUnitThread(int threadId, ClientUnit clientUnit,RTUDriveConfig driveConfig) {
		super();
		this.threadId = threadId;
		this.clientUnit = clientUnit;
		this.driveConfig = driveConfig;
	}
	public void run(){
		clientUnit.setSign(1);
        int rc=0;
        InputStream is=null;
        OutputStream os=null;
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
        while(!isExit){
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
    						byte[] macByte=new byte[11];
        					for(int i=0;i<11;i++){
        						macByte[i]=recByte[i+2];
        					}
        					revMacStr=new String(macByte);
    					}
    				}
    				
    				if(StringManagerUtils.isNotNull(revMacStr)){//接收到注册包
    					for(int i=0;i<EquipmentDriverServerTast.units.size();i++){
    						if(revMacStr.equalsIgnoreCase(beeTechDriverServerTast.units.get(i).driverAddr)){
    							System.out.println(beeTechDriverServerTast.units.get(i).wellName+"上线");
    							
    							for(int j=0;j<EquipmentDriverServerTast.clientUnitList.size();j++){//遍历已连接的客户端
    								if(EquipmentDriverServerTast.clientUnitList.get(j).socket!=null){//如果已连接
    									for(int k=0;k<EquipmentDriverServerTast.clientUnitList.get(j).unitDataList.size();k++){
    										if(revMacStr.equals(EquipmentDriverServerTast.clientUnitList.get(j).unitDataList.get(k).driverAddr)){//查询原有设备地址和新地址的连接，如存在断开资源，释放资源
    											if(EquipmentDriverServerTast.clientUnitList.get(j).thread!=null){
    												EquipmentDriverServerTast.clientUnitList.get(j).thread.interrupt();
    												break;
    											}
    										}
    									}
    								}
    							}
    							
    							
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
    						System.out.println("线程"+this.threadId+"未找到匹配的井，断开连接,释放资源:"+StringManagerUtils.bytesToHexString(recByte,12)+":"+revMacStr);
            				this.releaseResource(is,os);
            				wellReaded=false;
            				break;
    					}else{
    						String AcquisitionTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
							String updateDiscreteComm="update tbl_rpc_discrete_latest t set t.commstatus=1,t.acquisitiontime=to_date('"+AcquisitionTime+"','yyyy-mm-dd hh24:mi:ss')  "
									+ " where t.wellId in (select well.id from tbl_wellinformation well where well.driveraddr='"+revMacStr+"') ";
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
    				//循环读取数据 
    				String AcquisitionTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
    				for(int i=0;i<clientUnit.unitDataList.size();i++){
    					//接收工况报警信息
    					rc=this.readElectricAlarmInfo(clientUnit.socket, 5000, recByte,is);
    					if(rc!=-1){
    						if((recByte[0]&0xFF)==0x01&&(recByte[1]&0xFF)==0x00&&(recByte[2]&0xFF)==0x06){
    							System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"收到报警信息:"+StringManagerUtils.bytesToHexString(recByte,9));
    							clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
    	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
    	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    						}
    					}
    					
    					
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
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    					}
    					
    					//离散数据采集周期控制
    					if(clientUnit.unitDataList.get(i).DiscreteIntervalControl>0&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getDiscreteAcquisitionInterval()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getDiscreteAcquisitionInterval().getAddress(), clientUnit.unitDataList.get(i).DiscreteIntervalControl,driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setDiscreteIntervalControl(0);;
							
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
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"离散数据采集周期设置指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,12));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取离散数据采集周期设置返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
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
    					
    					//平衡调节手自动控制
    					if(clientUnit.unitDataList.get(i).balanceControlModeControl!=-999999999&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceControlMode()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceControlMode().getAddress(), clientUnit.unitDataList.get(i).getBalanceControlModeControl(),driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setBalanceControlModeControl(-999999999);
							
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
    					if(clientUnit.unitDataList.get(i).balanceCalculateModeControl!=-999999999&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceCalculateMode()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getBalanceCalculateMode().getAddress(), clientUnit.unitDataList.get(i).getBalanceCalculateModeControl(),driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setBalanceCalculateModeControl(-999999999);
							
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
    					
    					//重心远离支点调节时间控制
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
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"重心远离支点调节时间控制指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,14));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							clientUnit.unitDataList.get(i).sendPackageCount+=1;
							clientUnit.unitDataList.get(i).sendPackageSize+=17;
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取重心远离支点调节时间控制返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    					}
    					
    					//重心接近支点调节时间控制
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
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"重心接近支点调节时间控制指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,14));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							clientUnit.unitDataList.get(i).sendPackageCount+=1;
							clientUnit.unitDataList.get(i).sendPackageSize+=17;
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取重心接近支点调节时间控制返回数据读取失败，断开连接,释放资源");
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
    					
    					
    					//电流上限控制
    					if(clientUnit.unitDataList.get(i).CurrentUpLimitControl!=-999999999&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCurrentUpLimit()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCurrentUpLimit().getAddress(), clientUnit.unitDataList.get(i).CurrentUpLimitControl,driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setCurrentUpLimitControl(-999999999);
							
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
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"电流上限控制指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,14));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							clientUnit.unitDataList.get(i).sendPackageCount+=1;
							clientUnit.unitDataList.get(i).sendPackageSize+=17;
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取电流上限控制返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    					}
    					
    					//电流下限控制
    					if(clientUnit.unitDataList.get(i).CurrentDownLimitControl!=-999999999&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCurrentDownLimit()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getCurrentDownLimit().getAddress(), clientUnit.unitDataList.get(i).CurrentDownLimitControl,driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setCurrentDownLimitControl(-999999999);
							
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
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"电流下限控制指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,14));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							clientUnit.unitDataList.get(i).sendPackageCount+=1;
							clientUnit.unitDataList.get(i).sendPackageSize+=17;
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取电流下限控制返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    					}
    					
    					//功率上限控制
    					if(clientUnit.unitDataList.get(i).PowerUpLimitControl!=-999999999&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getPowerUpLimit()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getPowerUpLimit().getAddress(), clientUnit.unitDataList.get(i).PowerUpLimitControl,driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setPowerUpLimitControl(-999999999);
							
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
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"功率上限控制指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,14));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							clientUnit.unitDataList.get(i).sendPackageCount+=1;
							clientUnit.unitDataList.get(i).sendPackageSize+=17;
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功率上限控制返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    					}
    					
    					//功率下限控制
    					if(clientUnit.unitDataList.get(i).PowerDownLimitControl!=-999999999&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getPowerDownLimit()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getPowerDownLimit().getAddress(), clientUnit.unitDataList.get(i).PowerDownLimitControl,driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setPowerDownLimitControl(-999999999);
							
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
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"功率下限控制指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,14));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							clientUnit.unitDataList.get(i).sendPackageCount+=1;
							clientUnit.unitDataList.get(i).sendPackageSize+=17;
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功率下限控制返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
    					}
    					
    					//离散数据即时采集控制
    					if(clientUnit.unitDataList.get(i).ImmediatelyAcquisitionControl>0&&clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getImmediatelyAcquisition()!=null){
    						wellReaded=true;
							readByte=this.getWriteSingleRegisterByteData(clientUnit.unitDataList.get(i).UnitId,6, clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getImmediatelyAcquisition().getAddress(), clientUnit.unitDataList.get(i).ImmediatelyAcquisitionControl,driveConfig.getProtocol());
							clientUnit.unitDataList.get(i).setImmediatelyAcquisitionControl(0);
							
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
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"离散数据即时采集控制指令发送失败:"+StringManagerUtils.bytesToHexString(readByte,14));
	        					this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
							clientUnit.unitDataList.get(i).sendPackageCount+=1;
							clientUnit.unitDataList.get(i).sendPackageSize+=17;
							rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,clientUnit.unitDataList.get(i));
	    					if(rc==-1){//断开连接
	    						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取离散数据即时采集控制返回数据读取失败，断开连接,释放资源");
	            				this.releaseResource(is,os);
	            				wellReaded=false;
	            				break;
	            			}
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime("");//控制指令发出后，将离散数据上一次读取时间清空，执行离散数据读取
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime("");//控制指令发出后，将离散数据上一次保存时间清空，执行离散数据保存
	    					clientUnit.unitDataList.get(i).getAcquisitionData().setScrewPumpSaveTime("");;//控制指令发出后，将螺杆泵数据上一次保存时间清空，执行离散数据保存
	    					
//	    					Thread.sleep(1000*60*2);
    					}
    					
    					
    					long readTime=0;
						if(StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).getAcquisitionData().getReadTime())){
							readTime=format.parse(clientUnit.unitDataList.get(i).getAcquisitionData().getReadTime()).getTime();
						}
						//当前采集时间与上次读取时间差值大于离散数据采集周期时，读取离散数据
    					if(format.parse(AcquisitionTime).getTime()-readTime>=clientUnit.unitDataList.get(i).getAcqCycle_Discrete()){
    						clientUnit.unitDataList.get(i).getAcquisitionData().setReadTime(AcquisitionTime);
    						int runSatus=0;
    						int DiscreteAcquisitionInterval=0;
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
        					
        					float CurrentUpLimit=0;
        					float CurrentDownLimit=0;
        					float PowerUpLimit=0;
        					float PowerDownLimit=0;
        					int ImmediatelyAcquisition=0;
        					
        					float UpStrokeIMax=0;
        					float DownStrokeIMax=0;
        					float IDegreeBalance=0;
        					float UpStrokeWattMax=0;
        					float DownStrokeWattMax=0;
        					float WattDegreeBalance=0;
        					float CurrentAMax=0;
        					float CurrentAMin=0;
        					float CurrentBMax=0;
        					float CurrentBMin=0;
        					float CurrentCMax=0;
        					float CurrentCMin=0;
        					int ElectricDiagnosisResult=0;
        					
        					
        					
        					String diagramAcquisitionTime="1970-01-01 08:00:00";
        					
        					String prodTableName="tbl_rpc_productiondata_latest";
    						String discreteTableName="tbl_rpc_discrete_latest";
    						if(clientUnit.unitDataList.get(i).getLiftingType()>=400&&clientUnit.unitDataList.get(i).getLiftingType()<500){//螺杆泵井
    							prodTableName="tbl_pcp_productiondata_latest";
        						discreteTableName="tbl_pcp_discrete_latest";
    						}
    						boolean hasProData=false;
    						String updateProdData="update "+prodTableName+" t set t.acquisitionTime=to_date('"+AcquisitionTime+"','yyyy-mm-dd hh24:mi:ss')";
    						String updateDailyData="";
    						String updateDiscreteData="update "+discreteTableName+" t set t.commStatus=1,t.acquisitionTime=to_date('"+AcquisitionTime+"','yyyy-mm-dd hh24:mi:ss')";
    						
        					clientUnit.unitDataList.get(i).getAcquisitionData().setAcquisitionTime(AcquisitionTime);
        					//读取离散数据
        					//读取电参工况
        					rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,40449,1,recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        					if(rc==-1||rc==-2){
								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取电参工况发送或接收失败,rc="+rc);
								this.releaseResource(is,os);
                				wellReaded=false;
                				break;
							}else if(rc==-3){
								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取电参工况频率数据异常,rc="+rc);
								break;
							}else{ 
								ElectricDiagnosisResult=getUnsignedShort(recByte,0, driveConfig.getProtocol());
								updateDiscreteData+=",t.workingConditionCode="+ElectricDiagnosisResult;
							}
        					//读取离散采集周期
        					rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,40327,1,recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        					if(rc==-1||rc==-2){
								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取离散采集周期发送或接收失败,rc="+rc);
								this.releaseResource(is,os);
                				wellReaded=false;
                				break;
							}else if(rc==-3){
								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取离散采集周期异常,rc="+rc);
								break;
							}else{
								DiscreteAcquisitionInterval=getUnsignedShort(recByte,0, driveConfig.getProtocol());
								updateDiscreteData+=",t.acqCycle_Discrete="+DiscreteAcquisitionInterval;
							}
        					//读取运行状态和平衡DI状态
        					rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,40317,2,recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        					if(rc==-1||rc==-2){
								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取运行状态和平衡DI状态发送或接收失败,rc="+rc);
								this.releaseResource(is,os);
                				wellReaded=false;
                				break;
							}else if(rc==-3){
								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取运行状态和平衡DI状态数据异常,rc="+rc);
								break;
							}else{
								runSatus= getUnsignedShort(recByte,0, driveConfig.getProtocol());
								balanceAutoControl=(short) (0x0000 | (0x01 & recByte[6]));  
								spmAutoControl=(short) (0x0000 | (0x02 & recByte[6])>>1);  
								balanceFrontLimit=(short) (0x0000 | (0x04 & recByte[6])>>2);  
								balanceAfterLimit=(short) (0x0000 | (0x08 & recByte[6])>>3);  
            					clientUnit.unitDataList.get(i).getAcquisitionData().setBalanceAutoControl(balanceAutoControl);
            					clientUnit.unitDataList.get(i).getAcquisitionData().setSpmAutoControl(spmAutoControl);
            					clientUnit.unitDataList.get(i).getAcquisitionData().setBalanceFrontLimit(balanceFrontLimit);
            					clientUnit.unitDataList.get(i).getAcquisitionData().setBalanceAfterLimit(balanceAfterLimit);
            					updateDiscreteData+=",t.balanceAutoControl="+balanceAutoControl;
            					updateDiscreteData+=",t.spmAutoControl="+spmAutoControl;
            					updateDiscreteData+=",t.balanceFrontLimit="+balanceFrontLimit;
            					updateDiscreteData+=",t.balanceAfterLimit="+balanceAfterLimit;
							}
        					//读取平衡计算设置项和电参设置限值
        					rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,40120,14,recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        					if(rc==-1||rc==-2){
								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡计算设置项和电参设置限值发送或接收失败,rc="+rc);
								this.releaseResource(is,os);
                				wellReaded=false;
                				break;
							}else if(rc==-3){
								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取平衡计算设置项和电参设置限值数据异常,rc="+rc);
								break;
							}else{
								balanceControlMode=getUnsignedShort(recByte,0, driveConfig.getProtocol());
        						updateDiscreteData+=",t.balanceControlMode="+balanceControlMode;
        						
        						balanceCalculateMode=getUnsignedShort(recByte,2, driveConfig.getProtocol());
            					updateDiscreteData+=",t.balanceCalculateMode="+balanceCalculateMode;
            					
            					balanceAwayTimePerBeat=getUnsignedShort(recByte,4, driveConfig.getProtocol());
            					updateDiscreteData+=",t.balanceAwayTimePerBeat="+balanceAwayTimePerBeat;
            					
            					balanceCloseTimePerBeat=getUnsignedShort(recByte,6, driveConfig.getProtocol());
            					updateDiscreteData+=",t.balanceCloseTimePerBeat="+balanceCloseTimePerBeat;
            					
            					balanceStrokeCount=getUnsignedShort(recByte,8, driveConfig.getProtocol());
            					updateDiscreteData+=",t.balanceStrokeCount="+balanceStrokeCount;
            					
            					balanceOperationUpLimit=getUnsignedShort(recByte,10, driveConfig.getProtocol());
            					updateDiscreteData+=",t.balanceOperationUpLimit="+balanceOperationUpLimit;
            					
            					balanceOperationDownLimit=getUnsignedShort(recByte,12, driveConfig.getProtocol());
            					updateDiscreteData+=",t.balanceOperationDownLimit="+balanceOperationDownLimit;
            					
            					balanceAwayTime=getUnsignedShort(recByte,14, driveConfig.getProtocol());
            					updateDiscreteData+=",t.balanceAwayTime="+balanceAwayTime;
            					
            					balanceCloseTime=getUnsignedShort(recByte,16, driveConfig.getProtocol());
            					updateDiscreteData+=",t.balanceCloseTime="+balanceCloseTime;
            					
            					CurrentUpLimit=(float) (getShort(recByte,18, driveConfig.getProtocol())*0.01);
            					updateDiscreteData+=",t.IaUpLimit="+CurrentUpLimit+",t.IbUpLimit="+CurrentUpLimit+",t.IcUpLimit="+CurrentUpLimit;
            					
            					CurrentDownLimit=(float) (getShort(recByte,20, driveConfig.getProtocol())*0.01);
            					updateDiscreteData+=",t.IaDownLimit="+CurrentDownLimit+",t.IbDownLimit="+CurrentDownLimit+",t.IcDownLimit="+CurrentDownLimit;
            					
            					PowerUpLimit=(float) (getShort(recByte,22, driveConfig.getProtocol())*0.01);
            					updateDiscreteData+=",t.wattUpLimit="+PowerUpLimit;
            					
            					PowerDownLimit=(float) (getShort(recByte,24, driveConfig.getProtocol())*0.01);
            					updateDiscreteData+=",t.wattDownLimit="+PowerDownLimit;
            					
							}
        					//读取电参和平衡度
        					rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,40351,36,recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        					if(rc==-1||rc==-2){
								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取电参和平衡度发送或接收失败,rc="+rc);
								this.releaseResource(is,os);
                				wellReaded=false;
                				break;
							}else if(rc==-3){
								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取电参和平衡度数据异常,rc="+rc);
								break;
							}else{
								CurrentA=getFloat(recByte,0, driveConfig.getProtocol());
								updateDiscreteData+=",t.Ia="+CurrentA;
								
								CurrentB=getFloat(recByte,4, driveConfig.getProtocol());
								updateDiscreteData+=",t.Ib="+CurrentB;
								
								CurrentC=getFloat(recByte,8, driveConfig.getProtocol());
								updateDiscreteData+=",t.Ic="+CurrentC;
								
								VoltageA=getFloat(recByte,12, driveConfig.getProtocol());
								updateDiscreteData+=",t.Va="+VoltageA;
								
								VoltageB=getFloat(recByte,16, driveConfig.getProtocol());
								updateDiscreteData+=",t.Vb="+VoltageB;
								
								VoltageC=getFloat(recByte,20, driveConfig.getProtocol());
								updateDiscreteData+=",t.Vc="+VoltageC;
								
								ActivePowerConsumption=getFloat(recByte,24, driveConfig.getProtocol());
								ReactivePowerConsumption=getFloat(recByte,28, driveConfig.getProtocol());
								
								ActivePower=getFloat(recByte,32, driveConfig.getProtocol());
								updateDiscreteData+=",t.wattSum="+ActivePower;
								
								ReactivePower=getFloat(recByte,36, driveConfig.getProtocol());
								updateDiscreteData+=",t.varSum="+ReactivePower;
								
								ReversePower=getFloat(recByte,40, driveConfig.getProtocol());
								updateDiscreteData+=",t.ReversePower="+ReversePower;
								
								PowerFactor=getFloat(recByte,44, driveConfig.getProtocol());
								updateDiscreteData+=",t.pfSum="+PowerFactor;
								
								UpStrokeIMax=(float) (getShort(recByte,48, driveConfig.getProtocol())*0.01);
								DownStrokeIMax=(float) (getShort(recByte,50, driveConfig.getProtocol())*0.01);
								IDegreeBalance=(float) (getShort(recByte,52, driveConfig.getProtocol())*0.1);
								UpStrokeWattMax=(float) (getShort(recByte,54, driveConfig.getProtocol())*0.01);
								DownStrokeWattMax=(float) (getShort(recByte,56, driveConfig.getProtocol())*0.01);
								WattDegreeBalance=(float) (getShort(recByte,58, driveConfig.getProtocol())*0.1);
								
	        					
	        					CurrentAMax=(float) (getShort(recByte,60, driveConfig.getProtocol())*0.01);
	        					CurrentAMin=(float) (getShort(recByte,62, driveConfig.getProtocol())*0.01);
	        					CurrentBMax=(float) (getShort(recByte,64, driveConfig.getProtocol())*0.01);
	        					CurrentBMin=(float) (getShort(recByte,66, driveConfig.getProtocol())*0.01);
	        					CurrentCMax=(float) (getShort(recByte,68, driveConfig.getProtocol())*0.01);
	        					CurrentCMin=(float) (getShort(recByte,70, driveConfig.getProtocol())*0.01);
	        					updateDiscreteData+=",t.IaMax="+CurrentAMax+",t.IaMin="+CurrentAMin
	        							+",t.IbMax="+CurrentBMax+",t.IbMin="+CurrentBMin
	        							+",t.IcMax="+CurrentCMax+",t.IcMin="+CurrentCMin;
							}
        					//读取设置频率和运行频率
        					rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,40402,4,recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        					if(rc==-1||rc==-2){
								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取设置频率和运行频率发送或接收失败,rc="+rc);
								this.releaseResource(is,os);
                				wellReaded=false;
                				break;
							}else if(rc==-3){
								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取设置频率和运行频率数据异常,rc="+rc);
								break;
							}else{
								SetFrequency=getFloat(recByte,0, driveConfig.getProtocol());
								RunFrequency=getFloat(recByte,4, driveConfig.getProtocol());
								updateDiscreteData+=",t.frequencySetValue="+SetFrequency+
										",t.frequencyRunValue="+RunFrequency;
							}
        					//读取曲线数据
        					//读取功图点数、采集时间、冲次、冲程数据 
        					rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,40981,14,recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
        					if(rc==-1||rc==-2){
								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图点数、采集时间、冲次、冲程数据发送或接收失败,rc="+rc);
								this.releaseResource(is,os);
                				wellReaded=false;
                				break;
							}else if(rc==-3){
								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取功图点数、采集时间、冲次、冲程数据异常,rc="+rc);
								break;
							}else{
								acquisitionCycle=getUnsignedShort(recByte,0, driveConfig.getProtocol());
								updateDiscreteData+=",t.acqCycle_Diagram="+acquisitionCycle;
								
								FSDiagramSetPointCount=getUnsignedShort(recByte,4, driveConfig.getProtocol());
								point=getUnsignedShort(recByte,6, driveConfig.getProtocol());
								
								diagramAcquisitionTime=BCD2TimeString(recByte,8, driveConfig.getProtocol());
								
								SPM=getFloat(recByte,20, driveConfig.getProtocol());
								Stroke=getFloat(recByte,24, driveConfig.getProtocol());
							}
        					
        					//进行通信计算
        					String commRequest="{"
									+ "\"AKString\":\"\","
									+ "\"WellName\":\""+clientUnit.unitDataList.get(i).getWellName()+"\",";
        					if(StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).lastDisAcquisitionTime)&&StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).lastCommRange)){
        						commRequest+= "\"Last\":{"
    									+ "\"AcquisitionTime\": \""+clientUnit.unitDataList.get(i).lastDisAcquisitionTime+"\","
    									+ "\"CommStatus\": "+(clientUnit.unitDataList.get(i).lastCommStatus==1?true:false)+","
    									+ "\"CommEfficiency\": {"
    									+ "\"Efficiency\": "+clientUnit.unitDataList.get(i).lastCommTimeEfficiency+","
    									+ "\"Time\": "+clientUnit.unitDataList.get(i).lastCommTime+","
    									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(clientUnit.unitDataList.get(i).lastCommRange+"")+""
    									+ "}"
    									+ "},";
        					}	
        					commRequest+= "\"Current\": {"
									+ "\"AcquisitionTime\":\""+AcquisitionTime+"\","
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
        						
//        						clientUnit.unitDataList.get(i).lastDisAcquisitionTime=AcquisitionTime;
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
        					if(StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).lastDisAcquisitionTime)&&StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).lastRunRange)){
        						tiemEffRequest+= "\"Last\":{"
    									+ "\"AcquisitionTime\": \""+clientUnit.unitDataList.get(i).lastDisAcquisitionTime+"\","
    									+ "\"RunStatus\": "+(clientUnit.unitDataList.get(i).lastRunStatus==1?true:false)+","
    									+ "\"RunEfficiency\": {"
    									+ "\"Efficiency\": "+clientUnit.unitDataList.get(i).lastRunTimeEfficiency+","
    									+ "\"Time\": "+clientUnit.unitDataList.get(i).lastRunTime+","
    									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(clientUnit.unitDataList.get(i).lastRunRange+"")+""
    									+ "}"
    									+ "},";
        					}	
        					tiemEffRequest+= "\"Current\": {"
									+ "\"AcquisitionTime\":\""+AcquisitionTime+"\","
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
        						
//        						clientUnit.unitDataList.get(i).lastDisAcquisitionTime=AcquisitionTime;
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
        					if(StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).lastDisAcquisitionTime)){
        						energyRequest+= "\"Last\":{"
    									+ "\"AcquisitionTime\": \""+clientUnit.unitDataList.get(i).lastDisAcquisitionTime+"\","
    									+ "\"Total\":{"
    									+ "\"Watt\":"+clientUnit.unitDataList.get(i).lastTotalWattEnergy+","
    									+ "\"PWatt\":"+clientUnit.unitDataList.get(i).lastTotalPWattEnergy+","
    									+ "\"NWatt\":"+clientUnit.unitDataList.get(i).lastTotalNWattEnergy+","
    									+ "\"Var\":"+clientUnit.unitDataList.get(i).lastTotalVarEnergy+","
    									+ "\"PVar\":"+clientUnit.unitDataList.get(i).lastTotalPVarEnergy+","
    									+ "\"NVar\":"+clientUnit.unitDataList.get(i).lastTotalNVarEnergy+","
    									+ "\"VA\":"+clientUnit.unitDataList.get(i).lastTotalVAEnergy+""
    									+ "},\"Today\":{"
    									+ "\"Watt\":"+clientUnit.unitDataList.get(i).lastTodayWattEnergy+","
    									+ "\"PWatt\":"+clientUnit.unitDataList.get(i).lastTodayPWattEnergy+","
    									+ "\"NWatt\":"+clientUnit.unitDataList.get(i).lastTodayNWattEnergy+","
    									+ "\"Var\":"+clientUnit.unitDataList.get(i).lastTodayVarEnergy+","
    									+ "\"PVar\":"+clientUnit.unitDataList.get(i).lastTodayPVarEnergy+","
    									+ "\"NVar\":"+clientUnit.unitDataList.get(i).lastTodayNVarEnergy+","
    									+ "\"VA\":"+clientUnit.unitDataList.get(i).lastTodayVAEnergy+""
    									+ "}"
    									+ "},";
        					}	
        					energyRequest+= "\"Current\": {"
									+ "\"AcquisitionTime\":\""+AcquisitionTime+"\","
									+ "\"Total\":{"
									+ "\"Watt\":"+ActivePowerConsumption+","
									+ "\"PWatt\":"+0+","
									+ "\"NWatt\":"+0+","
									+ "\"Var\":"+ReactivePowerConsumption+","
									+ "\"PVar\":"+0+","
									+ "\"NVar\":"+0+","
									+ "\"VA\":"+0+""
									+ "}"
									+ "}"
									+ "}";
        					String energyResponse=StringManagerUtils.sendPostMethod(energyUrl, energyRequest,"utf-8");
        					type = new TypeToken<EnergyCalculateResponseData>() {}.getType();
        					energyCalculateResponseData=gson.fromJson(energyResponse, type);
        					if(energyCalculateResponseData!=null&&energyCalculateResponseData.getResultStatus()==1){
//        						clientUnit.unitDataList.get(i).lastDisAcquisitionTime=AcquisitionTime;
        						clientUnit.unitDataList.get(i).lastTotalWattEnergy=energyCalculateResponseData.getCurrent().getTotal().getWatt();
        						clientUnit.unitDataList.get(i).lastTotalPWattEnergy=energyCalculateResponseData.getCurrent().getTotal().getPWatt();
        						clientUnit.unitDataList.get(i).lastTotalNWattEnergy=energyCalculateResponseData.getCurrent().getTotal().getNWatt();
        						clientUnit.unitDataList.get(i).lastTotalVarEnergy=energyCalculateResponseData.getCurrent().getTotal().getVar();
        						clientUnit.unitDataList.get(i).lastTotalPVarEnergy=energyCalculateResponseData.getCurrent().getTotal().getPVar();
        						clientUnit.unitDataList.get(i).lastTotalNVarEnergy=energyCalculateResponseData.getCurrent().getTotal().getNVar();
        						clientUnit.unitDataList.get(i).lastTotalVAEnergy=energyCalculateResponseData.getCurrent().getTotal().getVA();
        						
        						clientUnit.unitDataList.get(i).lastTodayWattEnergy=energyCalculateResponseData.getCurrent().getToday().getWatt();
        						clientUnit.unitDataList.get(i).lastTodayPWattEnergy=energyCalculateResponseData.getCurrent().getToday().getPWatt();
        						clientUnit.unitDataList.get(i).lastTodayNWattEnergy=energyCalculateResponseData.getCurrent().getToday().getNWatt();
        						clientUnit.unitDataList.get(i).lastTodayVarEnergy=energyCalculateResponseData.getCurrent().getToday().getVar();
        						clientUnit.unitDataList.get(i).lastTodayPVarEnergy=energyCalculateResponseData.getCurrent().getToday().getPVar();
        						clientUnit.unitDataList.get(i).lastTodayNVarEnergy=energyCalculateResponseData.getCurrent().getToday().getNVar();
        						clientUnit.unitDataList.get(i).lastTodayVAEnergy=energyCalculateResponseData.getCurrent().getToday().getVA();
        					}else{
        						System.out.println("energy error");
        						System.out.println("请求数据："+energyRequest);
    							System.out.println("返回数据："+energyResponse);
        					}
        					clientUnit.unitDataList.get(i).lastDisAcquisitionTime=AcquisitionTime;
        					long hisDataInterval=0;
    						if(StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).getAcquisitionData().getSaveTime())){
    							hisDataInterval=format.parse(clientUnit.unitDataList.get(i).getAcquisitionData().getSaveTime()).getTime();
    						}
    						if(commResponseData!=null&&timeEffResponseData!=null&&
        							(RunStatus!=clientUnit.unitDataList.get(i).acquisitionData.runStatus//运行状态发生改变
        							||format.parse(AcquisitionTime).getTime()-hisDataInterval>=clientUnit.unitDataList.get(i).getSaveCycle_Discrete()//比上次保存时间大于5分钟
        							)
        						){
        						clientUnit.unitDataList.get(i).acquisitionData.setRunStatus(RunStatus);
        						Connection conn=OracleJdbcUtis.getConnection();
        						Statement stmt=null;
            					updateProdData+=" where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+clientUnit.unitDataList.get(i).wellName+"') ";
            					
                						
        						if(commResponseData!=null&&commResponseData.getResultStatus()==1){
        							updateDiscreteData+=" ,t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
        								+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime()
        								+ " ,t.commRange= '"+commResponseData.getCurrent().getCommEfficiency().getRangeString()+"'";
        							if(timeEffResponseData.getDaily()!=null&&StringManagerUtils.isNotNull(timeEffResponseData.getDaily().getDate())){
        								
        							}
            					}
        						if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
        							updateDiscreteData+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
        								+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime()
        								+ " ,t.runRange= '"+timeEffResponseData.getCurrent().getRunEfficiency().getRangeString()+"'";
        							if(timeEffResponseData.getDaily()!=null&&StringManagerUtils.isNotNull(timeEffResponseData.getDaily().getDate())){
        								
        							}
            					}
        						if(energyCalculateResponseData!=null&&energyCalculateResponseData.getResultStatus()==1){
        							updateDiscreteData+=",t.TotalWattEnergy= "+energyCalculateResponseData.getCurrent().getTotal().getWatt()
            								+ ",t.TotalPWattEnergy= "+energyCalculateResponseData.getCurrent().getTotal().getPWatt()
            								+ ",t.TotalNWattEnergy= "+energyCalculateResponseData.getCurrent().getTotal().getNWatt()
            								+ ",t.TotalVarEnergy= "+energyCalculateResponseData.getCurrent().getTotal().getVar()
            								+ ",t.TotalPVarEnergy= "+energyCalculateResponseData.getCurrent().getTotal().getPVar()
            								+ ",t.TotalNVarEnergy= "+energyCalculateResponseData.getCurrent().getTotal().getNVar()
            								+ ",t.TotalVAEnergy= "+energyCalculateResponseData.getCurrent().getTotal().getVA()
            								+ ",t.TodayWattEnergy= "+energyCalculateResponseData.getCurrent().getToday().getWatt()
            								+ ",t.TodayPWattEnergy= "+energyCalculateResponseData.getCurrent().getToday().getPWatt()
            								+ ",t.TodayNWattEnergy= "+energyCalculateResponseData.getCurrent().getToday().getNWatt()
            								+ ",t.TodayVarEnergy= "+energyCalculateResponseData.getCurrent().getToday().getVar()
            								+ ",t.TodayPVarEnergy= "+energyCalculateResponseData.getCurrent().getToday().getPVar()
            								+ ",t.TodayNVarEnergy= "+energyCalculateResponseData.getCurrent().getToday().getNVar()
            								+ ",t.TodayVAEnergy= "+energyCalculateResponseData.getCurrent().getToday().getVA();
        							if(energyCalculateResponseData.getDaily()!=null&&StringManagerUtils.isNotNull(energyCalculateResponseData.getDaily().getDate())){
        								updateDailyData="update tbl_rpc_total_day t set t.todaywattenergy="+energyCalculateResponseData.getDaily().getWatt()
        										+ ",t.TodayPWattEnergy= "+energyCalculateResponseData.getDaily().getPWatt()
                								+ ",t.TodayNWattEnergy= "+energyCalculateResponseData.getDaily().getNWatt()
                								+ ",t.TodayVarEnergy= "+energyCalculateResponseData.getDaily().getVar()
                								+ ",t.TodayPVarEnergy= "+energyCalculateResponseData.getDaily().getPVar()
                								+ ",t.TodayNVarEnergy= "+energyCalculateResponseData.getDaily().getNVar()
                								+ ",t.TodayVAEnergy= "+energyCalculateResponseData.getDaily().getVA()
        										+ " where t.calculatedate=to_date('"+energyCalculateResponseData.getDaily().getDate()+"','yyyy-mm-dd') "
        								         +" and t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+clientUnit.unitDataList.get(i).wellName+"') ";
        							}
        						}else{
        							updateDiscreteData+= " ,t.totalWattEnergy= "+ActivePowerConsumption
        									+ " ,t.totalVarEnergy= "+ReactivePowerConsumption;
        						}
        						
        						//如果时率来源非人工录入且电参计算成功，更新运行状态
        						if(clientUnit.unitDataList.get(i).getRunTimeEfficiencySource()!=0){
        							updateDiscreteData+=",t.runStatus="+RunStatus;
        						}
        						updateDiscreteData+=" where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+clientUnit.unitDataList.get(i).wellName+"') ";
        						try {
    								stmt = conn.createStatement();
    								int result=stmt.executeUpdate(updateDiscreteData);
    								if(hasProData)
    									result=stmt.executeUpdate(updateProdData);
    								if(StringManagerUtils.isNotNull(updateDailyData)){
    									result=stmt.executeUpdate(updateDailyData);
    								}
    								conn.close();
    								stmt.close();
    								clientUnit.unitDataList.get(i).getAcquisitionData().setRunStatus(RunStatus);
    								clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime(AcquisitionTime);
    							} catch (SQLException e) {
    								e.printStackTrace();
    								try {
    									conn.close();
    									if(stmt!=null){
    										stmt.close();
    									}
    								} catch (SQLException e1) {
    									e1.printStackTrace();
    								}
    							}
        					}


    						//读取功图

    						long currentTimelong=0;
        					if(clientUnit.unitDataList.get(i).getDiagranAcquisitionTime()!=null){
        						currentTimelong = format.parse(clientUnit.unitDataList.get(i).getDiagranAcquisitionTime()).getTime();//数据库中最新功图采集时间
        					}
        					long newTimelong = format.parse(diagramAcquisitionTime).getTime();
        					if(newTimelong>currentTimelong){//发现新功图
        						System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"发现新功图");
        						clientUnit.unitDataList.get(i).setDiagranAcquisitionTime(diagramAcquisitionTime);
        						recvBuff=new StringBuffer();
        						StringBuffer recvFBuff=new StringBuffer();
        						StringBuffer recvSBuff=new StringBuffer();
        						StringBuffer recvABuff=new StringBuffer();
        						StringBuffer recvPBuff=new StringBuffer();
        						StringBuffer proParamsBuff=new StringBuffer();
        						StringBuffer elecBuff=new StringBuffer();
        						StringBuffer recvIaBuff=new StringBuffer();
        						StringBuffer recvIbBuff=new StringBuffer();
        						StringBuffer recvIcBuff=new StringBuffer();
        						recvFBuff.append("\"F\": [");
        						recvSBuff.append("\"S\": [");
        						recvABuff.append("\"A\": [");
        						recvPBuff.append("\"P\": [");
        						
        						recvIaBuff.append("\"Ia\": [");
        						recvIbBuff.append("\"Ib\": [");
        						recvIcBuff.append("\"Ic\": [");
        						proParamsBuff.append("\"ProductionParameter\": {");
        						elecBuff.append("\"Electric\": {");
        						recvBuff.append("{\"WellName\":\""+clientUnit.unitDataList.get(i).getWellName()+"\",\"LiftingType\":"+clientUnit.unitDataList.get(i).getLiftingType()+",\"AcquisitionTime\":\""+diagramAcquisitionTime+"\",");
        						
        						
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
            					
            					recvBuff.append("\"Diagram\":{\"AcquisitionTime\":\""+diagramAcquisitionTime+"\","
            							+ "\"AcquisitionCycle\":"+acquisitionCycle+","
            							+ "\"SPM\":"+SPM+","
            							+"\"Stroke\":"+Stroke+","
            							+"\"HaveBalanceData\":1,"
            							+"\"UpStrokeIMax\":"+UpStrokeIMax+","
            							+"\"DownStrokeIMax\":"+DownStrokeIMax+","
            							+"\"IDegreeBalance\":"+IDegreeBalance+","
            							+"\"UpStrokeWattMax\":"+UpStrokeWattMax+","
            							+"\"DownStrokeWattMax\":"+DownStrokeWattMax+","
            							+"\"WattDegreeBalance\":"+WattDegreeBalance+","
            							);
            					
        						//读取功图位移数据
            					if(clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getSDiagram().getAddress()>40000){
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
            					if(clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFDiagram().getAddress()>40000){
            						if(point==0)
            							point=(short)clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFDiagram().getLength();
            						for(int j=0;j*100<point;j++){
            							int length=100;
            							if(j*100+length>point){
            								length=point-j*100;
            							}
            							wellReaded=true;
            							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
                								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getFDiagram().getAddress()+j*100,
                								length,
                								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
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
            					if(clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getADiagram().getAddress()>40000){
            						if(point==0)
            							point=(short)clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getADiagram().getLength();
            						for(int j=0;j*100<point;j++){
            							int length=100;
            							if(j*100+length>point){
            								length=point-j*100;
            							}
            							wellReaded=true;
            							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
                								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getADiagram().getAddress()+j*100,
                								length,
                								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
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
            					if(clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getPDiagram().getAddress()>40000){
            						if(point==0)
            							point=(short)clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getPDiagram().getLength();
            						for(int j=0;j*100<point;j++){
            							int length=100;
            							if(j*100+length>point){
            								length=point-j*100;
            							}
            							wellReaded=true;
            							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
                								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getPDiagram().getAddress()+j*100,
                								length,
                								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
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
            					
            					//读取A相电流曲线数据
            					if(clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getIaDiagram().getAddress()>40000){
            						if(point==0)
            							point=(short)clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getIaDiagram().getLength();
            						for(int j=0;j*100<point;j++){
            							int length=100;
            							if(j*100+length>point){
            								length=point-j*100;
            							}
            							wellReaded=true;
            							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
                								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getIaDiagram().getAddress()+j*100,
                								length,
                								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
            							if(rc==-1||rc==-2){
            								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取A相电流曲线数据发送或接收失败,rc="+rc);
            								this.releaseResource(is,os);
                            				wellReaded=false;
                            				break;
            							}else if(rc==-3){
            								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取A相电流曲线数据异常,rc="+rc);
            								break;
            							}else{
            								for(int k=0;k<length;k++){
                        						Float recvIa=(float) (getShort(recByte,k*2, driveConfig.getProtocol())*clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getIaDiagram().getZoom());
                        						recvIaBuff.append(recvIa+",");
                        					}
            							}
            						}
            						if(recvIaBuff.toString().endsWith(",")){
            							recvIaBuff.deleteCharAt(recvIaBuff.length() - 1);
            						}
            					}
            					recvIaBuff.append("]");
            					
            					//读取B相电流曲线数据
            					if(clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getIbDiagram().getAddress()>40000){
            						if(point==0)
            							point=(short)clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getIbDiagram().getLength();
            						for(int j=0;j*100<point;j++){
            							int length=100;
            							if(j*100+length>point){
            								length=point-j*100;
            							}
            							wellReaded=true;
            							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
                								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getIbDiagram().getAddress()+j*100,
                								length,
                								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
            							if(rc==-1||rc==-2){
            								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取B相电流曲线数据发送或接收失败,rc="+rc);
            								this.releaseResource(is,os);
                            				wellReaded=false;
                            				break;
            							}else if(rc==-3){
            								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取B相电流曲线数据异常,rc="+rc);
            								break;
            							}else{
            								for(int k=0;k<length;k++){
                        						Float recvIb=(float) (getShort(recByte,k*2, driveConfig.getProtocol())*clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getIbDiagram().getZoom());
                        						recvIbBuff.append(recvIb+",");
                        					}
            							}
            						}
            						if(recvIbBuff.toString().endsWith(",")){
            							recvIbBuff.deleteCharAt(recvIbBuff.length() - 1);
            						}
            					}
            					recvIbBuff.append("]");
            					
            					//读取C相电流曲线数据
            					if(clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getIcDiagram().getAddress()>40000){
            						if(point==0)
            							point=(short)clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getIcDiagram().getLength();
            						for(int j=0;j*100<point;j++){
            							int length=100;
            							if(j*100+length>point){
            								length=point-j*100;
            							}
            							wellReaded=true;
            							rc=sendAndReadData(is,os,readTimeout,clientUnit.unitDataList.get(i).UnitId,03,
                								clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getIcDiagram().getAddress()+j*100,
                								length,
                								recByte,clientUnit.unitDataList.get(i),driveConfig.getProtocol());
            							if(rc==-1||rc==-2){
            								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取C相电流曲线数据发送或接收失败,rc="+rc);
            								this.releaseResource(is,os);
                            				wellReaded=false;
                            				break;
            							}else if(rc==-3){
            								System.out.println("线程"+this.threadId+",井:"+clientUnit.unitDataList.get(i).getWellName()+"读取C相电流曲线数据异常,rc="+rc);
            								break;
            							}else{
            								for(int k=0;k<length;k++){
                        						Float recvIc=(float) (getShort(recByte,k*2, driveConfig.getProtocol())*clientUnit.unitDataList.get(i).getRtuDriveConfig().getDataConfig().getIcDiagram().getZoom());
                        						recvIcBuff.append(recvIc+",");
                        					}
            							}
            						}
            						if(recvIcBuff.toString().endsWith(",")){
            							recvIcBuff.deleteCharAt(recvIcBuff.length() - 1);
            						}
            					}
            					recvIcBuff.append("]");
            					
            					
            					clientUnit.unitDataList.get(i).getAcquisitionData().setRecvPBuff(recvPBuff.toString());
            					
            					recvBuff.append(recvFBuff+","+recvSBuff+","+recvABuff+","+recvPBuff+","+recvIaBuff+","+recvIbBuff+","+recvIcBuff+"}}");
            					System.out.println("线程"+this.threadId+"解析读取数据:"+recvBuff.toString());
            					StringManagerUtils.sendPostMethod(saveDiagramurl, recvBuff.toString(),"utf-8");
            					clientUnit.unitDataList.get(i).getAcquisitionData().setSaveTime(AcquisitionTime);
            					wellReaded=true;
        					}
    					}
    				}
    			}
    			if(this.interrupted()){
    				System.out.println("线程"+this.threadId+"退出！");
            		throw new InterruptedException();
            	}else{
            		Thread.sleep(1000);
            	}
    		} catch (Exception e) {
    			e.printStackTrace();
				this.releaseResource(is,os);
				break;
    		} 
            
        }
	}
	
	public  void releaseResource(InputStream is,OutputStream os){
		System.out.println("releaseResource");
		try {
			isExit=true;
			Connection conn=OracleJdbcUtis.getConnection();
			Statement stmt=null;
			stmt = conn.createStatement();
			Gson gson = new Gson();
			String AcquisitionTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			
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
				if(StringManagerUtils.isNotNull(clientUnit.unitDataList.get(i).lastDisAcquisitionTime)){
					commRequest+= "\"Last\":{"
							+ "\"AcquisitionTime\": \""+clientUnit.unitDataList.get(i).lastDisAcquisitionTime+"\","
							+ "\"CommStatus\": "+(clientUnit.unitDataList.get(i).lastCommStatus==1?true:false)+","
							+ "\"CommEfficiency\": {"
							+ "\"Efficiency\": "+clientUnit.unitDataList.get(i).lastCommTimeEfficiency+","
							+ "\"Time\": "+clientUnit.unitDataList.get(i).lastCommTime+","
							+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(clientUnit.unitDataList.get(i).lastCommRange+"")+""
							+ "}"
							+ "},";
				}	
				commRequest+= "\"Current\": {"
						+ "\"AcquisitionTime\":\""+AcquisitionTime+"\","
						+ "\"CommStatus\":false"
						+ "}"
						+ "}";
				String commResponse=StringManagerUtils.sendPostMethod(commUrl, commRequest,"utf-8");
				java.lang.reflect.Type type = new TypeToken<CommResponseData>() {}.getType();
				CommResponseData commResponseData=gson.fromJson(commResponse, type);
				String updateCommStatus="update tbl_rpc_discrete_latest t set t.commStatus=0,t.acquisitionTime=to_date('"+AcquisitionTime+"','yyyy-mm-dd hh24:mi:ss') ";
				if(commResponseData!=null&&commResponseData.getResultStatus()==1){
					updateCommStatus+=" ,t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
							+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime()
							+ " ,t.commRange= '"+commResponseData.getCurrent().getCommEfficiency().getRangeString()+"'";
					
					clientUnit.unitDataList.get(i).lastDisAcquisitionTime=AcquisitionTime;
					clientUnit.unitDataList.get(i).lastCommStatus=commResponseData.getCurrent().getCommStatus()?1:0;
					clientUnit.unitDataList.get(i).lastCommTime=commResponseData.getCurrent().getCommEfficiency().getTime();
					clientUnit.unitDataList.get(i).lastCommTimeEfficiency=commResponseData.getCurrent().getCommEfficiency().getEfficiency();
					clientUnit.unitDataList.get(i).lastCommRange=commResponseData.getCurrent().getCommEfficiency().getRangeString();
				}
				updateCommStatus+=" where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+clientUnit.unitDataList.get(i).wellName+"') ";
				int result=stmt.executeUpdate(updateCommStatus);
			}
			conn.close();
			if(stmt!=null){
				stmt.close();
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
    
  //读取报警数据
    public int readElectricAlarmInfo(Socket socket,int timeout,byte[] recByte,InputStream is){
    	int rc=0;
    	try {
			socket.setSoTimeout(timeout);
	    	rc=is.read(recByte);
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			return -1;
		}
    	return rc;
    }
    
    public int sendAndReadData(InputStream is,OutputStream os,int readTimeout,int id,int gnm,int startAddr,int lengthOrData,byte[] recByte,UnitData unit,int protocol){
		byte[] readByte=new byte[12];
		readByte=this.getSendByteData(id, gnm, startAddr, lengthOrData,protocol);
		int rc=this.writeSocketData(clientUnit.socket, readByte,os,unit);
		if(rc==-1){//断开连接
			return -1;//发送数据失败
		}
		rc=this.readSocketData(clientUnit.socket, readTimeout, recByte,is,unit);
		if(rc==-1){//断开连接
			return -2;//读取数据失败
		}
		if(recByte[7]==0x83){//读取异常
			return -3;//读取异常
		}
		return rc;
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
    
    public String BCD2TimeString(byte[] arr,int index, int protocol) {
        String result="";
        if(protocol==1){
    		result=StringManagerUtils.BCD2TimeString(arr, 9+index);
    	}else if(protocol==2){
    		result=StringManagerUtils.BCD2TimeString(arr, 3+index);
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
