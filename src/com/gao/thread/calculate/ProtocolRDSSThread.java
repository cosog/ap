package com.gao.thread.calculate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;

import com.gao.model.calculate.CommResponseData;
import com.gao.model.calculate.ElectricCalculateResponseData;
import com.gao.model.calculate.TimeEffResponseData;
import com.gao.task.RSDDServerTask.AcquisitionData;
import com.gao.utils.BeidouTerminalMap;
import com.gao.utils.Config;
import com.gao.utils.Config2;
import com.gao.utils.OracleJdbcUtis;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import gnu.io.SerialPort;

public class ProtocolRDSSThread extends Thread{

	private String data;
	private SerialPort serialPort;
	public ProtocolRDSSThread(SerialPort serialPort,String data) {
		super();
		this.data = data;
		this.serialPort = serialPort;
	}

	public void run(){
		synchronized(this){
			if(data.startsWith("$")&&(data.endsWith("\r\n")||data.endsWith("\\r\\n"))){//只有完整包的时候才处理
				Connection conn = null;   
				PreparedStatement pstmt = null;  
				Statement stmt = null;  
				ResultSet rs = null;
				String saveFSDiagramUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/graphicalUploadController/saveRTUAcquisitionData";
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Gson gson = new Gson();
				try{
					
					String revDataArr[]=data.split(",");
					
					if("$BDICI".equalsIgnoreCase(revDataArr[0])){
						System.out.println("北斗--收到卡号信息");
					}else if("$BDBSI".equalsIgnoreCase(revDataArr[0])){
						System.out.println("北斗--收到信号状态");
					}else if("$BDFKI".equalsIgnoreCase(revDataArr[0])){
						System.out.println("北斗--收到数据发送状态");
					}else if("$BDDWR".equalsIgnoreCase(revDataArr[0])){
						System.out.println("北斗--收到定位数据");
					}else if("$BDTXR".equalsIgnoreCase(revDataArr[0])){
						String terminalNo=revDataArr[2];//卡号
						byte[] revData=StringManagerUtils.hexStringToBytes(revDataArr[revDataArr.length-1].split("\\*")[0]);//北斗中数据部分
						System.out.println("北斗--收到的数据-数据部分："+StringManagerUtils.bytesToHexString(revData, revData.length));
						Map<String, Object> beidouTerminalMap=BeidouTerminalMap.getMapObject();//获取内存数据
						AcquisitionData acquisitionData=(AcquisitionData) beidouTerminalMap.get(terminalNo);
						if(acquisitionData==null){
							acquisitionData=new AcquisitionData();
							acquisitionData.setTerminalNo(terminalNo);
							beidouTerminalMap.put(terminalNo, acquisitionData);
						}
						//记录采集时间
						String AcqTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
						if(!StringManagerUtils.isNotNull(acquisitionData.AcqTime)||format.parse(AcqTime).getTime()<format.parse(acquisitionData.AcqTime).getTime()){
							acquisitionData.setAcqTime(AcqTime);
						}
						short addr=StringManagerUtils.getShort(revData, 1);//地址
						short totalFrag=StringManagerUtils.getShort(revData[3]);//总片段
						short currentFrag=StringManagerUtils.getShort(revData[4]);//当前片段
						int dataLength=revData.length-6;
						if(revData[1]==(byte)0x01&&revData[2]==(byte)0x2b){//油压等生产数据
							System.arraycopy(revData, 6, acquisitionData.prodData, 0, dataLength);
							System.out.println("北斗--收到油井生产数据：卡号"+terminalNo+"数据"+StringManagerUtils.bytesToHexString(acquisitionData.prodData, acquisitionData.prodData.length));
						}else if(revData[1]==(byte)0x01&&revData[2]==(byte)0x5e){//电参数据
							System.arraycopy(revData, 6, acquisitionData.elecData, 0, dataLength);
							System.out.println("北斗--收到油井电参数据：卡号"+terminalNo+"数据"+StringManagerUtils.bytesToHexString(acquisitionData.elecData, acquisitionData.elecData.length));
						}else if(revData[1]==(byte)0x03&&revData[2]==(byte)0xD7){//功图点数、采集时间、冲次、冲程
							System.arraycopy(revData, 6, acquisitionData.gtInfo, 0, dataLength);
							System.out.println("北斗--收到功图信息：卡号"+terminalNo+"数据"+StringManagerUtils.bytesToHexString(acquisitionData.gtInfo, acquisitionData.gtInfo.length));
						}else if(addr>=1000&&addr<1250){//功图位移数据
							if(currentFrag==0&&acquisitionData.dataLength==0){//当是第一个片段时 确定每包数据的字节数
								acquisitionData.setDataLength(dataLength);
							}
							System.arraycopy(revData, 6, acquisitionData.gtSData, currentFrag*acquisitionData.dataLength+(addr-1000)*2, dataLength);
						}else if(addr>=1250&&addr<1500){//功图载荷数据
							if(currentFrag==0&&acquisitionData.dataLength==0){//当是第一个片段时 确定每包数据的字节数
								acquisitionData.setDataLength(dataLength);
							}
							System.arraycopy(revData, 6, acquisitionData.gtFData, currentFrag*acquisitionData.dataLength+(addr-1250)*2, dataLength);
						}else if(addr>=1500&&addr<1750){//功图电流数据
							if(currentFrag==0&&acquisitionData.dataLength==0){//当是第一个片段时 确定每包数据的字节数
								acquisitionData.setDataLength(dataLength);
							}
							System.arraycopy(revData, 6, acquisitionData.gtAData, currentFrag*acquisitionData.dataLength+(addr-1500)*2, dataLength);
						}else if(addr>=1750&&addr<2000){//功图功率数据
							if(currentFrag==0&&acquisitionData.dataLength==0){//当是第一个片段时 确定每包数据的字节数
								acquisitionData.setDataLength(dataLength);
							}
							System.arraycopy(revData, 6, acquisitionData.gtPData, currentFrag*acquisitionData.dataLength+(addr-1750)*2, dataLength);
						}
						
						if(revData[5]==0x01){//数据传输完成
							String querySql="select jh,jslx,slly from tbl_wellinformation t where t.driveraddr='"+terminalNo+"'";
							conn=OracleJdbcUtis.getConnection();
							if(conn!=null){
								pstmt = conn.prepareStatement(querySql);
								rs=pstmt.executeQuery();
								String wellName="";
								int jslx=200;
								int slly=0;
								while(rs.next()){
									wellName=rs.getString(1);
									jslx=rs.getInt(2);
									slly=rs.getInt(3);
								}
								if(StringManagerUtils.isNotNull(wellName)){
									short yxzt=StringManagerUtils.getShort(acquisitionData.runStatus,0);
									//解析生产数据
									float TubingPressure=StringManagerUtils.getFloat(acquisitionData.prodData, 0);
									float CasingPressure=StringManagerUtils.getFloat(acquisitionData.prodData, 4);
									float BackPressure=StringManagerUtils.getFloat(acquisitionData.prodData, 8);
									float WellHeadFluidTemperature=StringManagerUtils.getFloat(acquisitionData.prodData, 12);
									//解析电参数据
									float CurrentA=StringManagerUtils.getFloat(acquisitionData.elecData, 0);
									float CurrentB=StringManagerUtils.getFloat(acquisitionData.elecData, 4);
									float CurrentC=StringManagerUtils.getFloat(acquisitionData.elecData, 8);
									float VoltageA=StringManagerUtils.getFloat(acquisitionData.elecData, 12);
									float VoltageB=StringManagerUtils.getFloat(acquisitionData.elecData, 16);
									float VoltageC=StringManagerUtils.getFloat(acquisitionData.elecData, 20);
									float ActivePowerConsumption=StringManagerUtils.getFloat(acquisitionData.elecData, 24);
									float ReactivePowerConsumption=StringManagerUtils.getFloat(acquisitionData.elecData, 28);
									float ActivePower=StringManagerUtils.getFloat(acquisitionData.elecData, 32);
									float ReactivePower=StringManagerUtils.getFloat(acquisitionData.elecData, 36);
									float ReversePower=StringManagerUtils.getFloat(acquisitionData.elecData, 40);
									float PowerFactor=StringManagerUtils.getFloat(acquisitionData.elecData, 44);
									//解析变频数据
									float SetFrequency=StringManagerUtils.getFloat(acquisitionData.freqData, 4);
									float RunFrequency=StringManagerUtils.getFloat(acquisitionData.freqData, 8);
									//
									String gtcjsj=StringManagerUtils.BCD2TimeString(acquisitionData.gtInfo, 2);
									if("0000-00-00 00:00:00".equals(gtcjsj)){//无功图数据
										//进行通信计算
			        					String commRequest="{\"AKString\":\"\","
			        							+ "\"WellName\":\""+wellName+"\","
			        							+ "\"AcqTime\":\""+acquisitionData.AcqTime+"\","
			        							+ "\"CommStatus\":1"
			        							+ "}";
			        					String commResponse=StringManagerUtils.sendPostMethod(Config.getInstance().configFile.getAgileCalculate().getCommunication()[0], commRequest,"utf-8");
			        					java.lang.reflect.Type type = new TypeToken<CommResponseData>() {}.getType();
			        					CommResponseData commResponseData=gson.fromJson(commResponse, type);
			        					
			        					//进行电参计算
			        					String elecCalRequest="{\"AKString\":\"\","
			        							+ "\"WellName\":\""+wellName+"\","
			        							+ "\"AcqTime\":\""+AcqTime+"\","
			        							+ "\"CurrentA\":"+CurrentA+","
			        							+ "\"CurrentB\":"+CurrentB+","
			        							+ "\"CurrentC\":"+CurrentC+","
			        							+ "\"VoltageA\":"+VoltageA+","
			        							+ "\"VoltageB\":"+VoltageB+","
			        							+ "\"VoltageC\":"+VoltageC+","
			        							+ "\"ActivePowerSum\":"+ActivePower+","
			        							+ "\"ReactivePowerSum\":"+ReactivePower+","
			        							+ "\"CompositePowerFactor\":"+PowerFactor+""
			        							+ "}";
			        					String elecCalUrl="";
//			        					if(jslx>=200&&jslx<300){//抽油机
//			        						elecCalUrl=Config2.getPumpingunitElecCalculateHttpServerURL();
//			        					}else if(jslx>=400&&jslx<500){//螺杆泵
//			        						elecCalUrl=Config2.getScrewpumpElecCalculateHttpServerURL();
//			        					}else{//默认抽油机
//			        						elecCalUrl=Config2.getPumpingunitElecCalculateHttpServerURL();
//			        					}
			        					String elecCalResponse=StringManagerUtils.sendPostMethod(elecCalUrl, elecCalRequest,"utf-8");
			        					elecCalResponse=elecCalResponse.replaceAll("'", "''");
			        					type = new TypeToken<ElectricCalculateResponseData>() {}.getType();
			        					ElectricCalculateResponseData electricCalculateResponseData=gson.fromJson(elecCalResponse, type);
			        					
			        					//进行时率计算
			        					TimeEffResponseData timeEffResponseData=null;
			        					int RunStatus=yxzt;
			        					//时率来源为电参计算时，运行状态取电参计算结果
			        					if(slly==2&&electricCalculateResponseData!=null&&electricCalculateResponseData.getResultStatus()==1){//时率来源为电参时
			        						RunStatus=electricCalculateResponseData.getRunStatus();
			        					}
			        					String tiemEffRequest="{\"AKString\":\"\","
			        							+ "\"WellName\":\""+wellName+"\","
			        							+ "\"AcqTime\":\""+AcqTime+"\","
			        							+ "\"RunStatus\":"+RunStatus+","
			        							+ "\"TotalAPC\":"+ActivePowerConsumption+","
			        							+ "\"TotalRPC\":"+ReactivePowerConsumption+""
			        							+ "}";
			        					//时率来源为DI信号或电参计算时，此时进行时率计算
			        					if(slly==1||slly==2){
			        						String timeEffResponse=StringManagerUtils.sendPostMethod(Config.getInstance().configFile.getAgileCalculate().getRun()[0], tiemEffRequest,"utf-8");
			            					type = new TypeToken<TimeEffResponseData>() {}.getType();
			            					timeEffResponseData=gson.fromJson(timeEffResponse, type);
			        					}
			        					
			        					String updateTXZT="update tbl_wellinformation t set t.txzt=1 where t.jh='"+wellName+"'";
		        						
		        						String updateProdData="update tbl_rpc_productiondata_hist t set t.cjsj=to_date('"+AcqTime+"','yyyy-mm-dd hh24:mi:ss')";
		        						boolean hasProData=false;
		            					if(TubingPressure>0){
		            						hasProData=true;
		            						updateProdData+=",t.yy="+TubingPressure;
		            					}
		            					if(CasingPressure>0){
		            						hasProData=true;
		            						updateProdData+=",t.ty="+CasingPressure;
		            					}
		            					if(BackPressure>0){
		            						hasProData=true;
		            						updateProdData+=",t.hy="+BackPressure;
		            					}
		            					if(WellHeadFluidTemperature>0){
		            						hasProData=true;
		            						updateProdData+=",t.jklw="+WellHeadFluidTemperature;
		            					}
		            					updateProdData+=" where t.jbh= (select t007.jlbh from tbl_wellinformation t007 where t007.jh='"+wellName+"') ";
		            					
		        						
		        						String updateTXZT033="update t_outputwellhistory t set t.txzt=1,t.gtcjzq=0,t.bpszpl="+SetFrequency+",t.bpyxpl="+RunFrequency+","
		        								+ "t.TubingPressure="+TubingPressure+",t.CasingPressure="+CasingPressure+",t.BackPressure="+BackPressure+",t.WellHeadFluidTemperature="+WellHeadFluidTemperature+","
		        								+ "t.cjsj=to_date('"+AcqTime+"','yyyy-mm-dd hh24:mi:ss')";
		        						String updateTXZT033RT="";
		        						if(commResponseData!=null&&commResponseData.getResultStatus()==1){
		        							updateTXZT033+=" ,t.txsl= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
		        								+ " ,t.txsj= "+commResponseData.getCurrent().getCommEfficiency().getTime()
		        								+ " ,t.txqj= '"+commResponseData.getCurrent().getCommEfficiency().getRangeString()+"'";
		            					}
		        						
		        						if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
		        							updateTXZT033+=",t.yxsl= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
		        								+ " ,t.yxsj= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime()
		        								+ " ,t.yxsjd= '"+timeEffResponseData.getCurrent().getRunEfficiency().getRangeString()+"'"
		        								+ " ,t.rydl= 0"
		        								+ " ,t.activepowerconsumption= "+ActivePowerConsumption
		        								+ " ,t.reactivepowerconsumption= "+ReactivePowerConsumption;
		            					}
		        						//如果时率来源为电参计算且电参计算成功，更新运行状态
		        						if((slly==2&&electricCalculateResponseData!=null&&electricCalculateResponseData.getResultStatus()==1)
		        								||(slly!=2&&timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1)){
		        							updateTXZT033+=",t.yxzt="+RunStatus;
		        						}
		        						
		        						if(electricCalculateResponseData!=null&&electricCalculateResponseData.getResultStatus()==1){
		        							String currentaalarm=electricCalculateResponseData.getAlarmItems().getCurrentA().getMaxValueStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getCurrentA().getMinValueStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getCurrentA().getZeroLevelStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getCurrentA().getBalacneStatus();
		        							String currentbalarm=electricCalculateResponseData.getAlarmItems().getCurrentB().getMaxValueStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getCurrentB().getMinValueStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getCurrentB().getZeroLevelStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getCurrentB().getBalacneStatus();
		        							String currentcalarm=electricCalculateResponseData.getAlarmItems().getCurrentC().getMaxValueStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getCurrentC().getMinValueStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getCurrentC().getZeroLevelStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getCurrentC().getBalacneStatus();
		        							String voltageaalarm=electricCalculateResponseData.getAlarmItems().getVoltageA().getMaxValueStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getVoltageA().getMinValueStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getVoltageA().getZeroLevelStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getVoltageA().getBalacneStatus();
		        							String voltagebalarm=electricCalculateResponseData.getAlarmItems().getVoltageB().getMaxValueStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getVoltageB().getMinValueStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getVoltageB().getZeroLevelStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getVoltageB().getBalacneStatus();
		        							String voltagecalarm=electricCalculateResponseData.getAlarmItems().getVoltageC().getMaxValueStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getVoltageC().getMinValueStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getVoltageC().getZeroLevelStatus()+"/"
		        									+ electricCalculateResponseData.getAlarmItems().getVoltageC().getBalacneStatus();
		        							String  ETResultStringArr[]= electricCalculateResponseData.getETResultString().split("\u003cbr/\u003e");
		        							if(ETResultStringArr.length>50){
		        								String newETResultString="";
		        								for(int j=0;j<50;j++){
		        									newETResultString+=ETResultStringArr[j];
		        									if(j<49){
		        										newETResultString+="\u003cbr/\u003e";
		        									}
		        								}
		        								electricCalculateResponseData.setETResultString(newETResultString);
		        							}
		        							updateTXZT033+=" ,t.egklx= "+electricCalculateResponseData.getResultCode()
		        								+ " ,t.currentaalarm= '"+currentaalarm+"'"
		        								+ " ,t.currentbalarm= '"+currentbalarm+"'"
		        								+ " ,t.currentcalarm= '"+currentcalarm+"'"
		        								+ " ,t.voltageaalarm= '"+voltageaalarm+"'"
		        								+ " ,t.voltagebalarm= '"+voltagebalarm+"'"
		        								+ " ,t.voltagecalarm= '"+voltagecalarm+"'"
		        								+ " ,t.CurrentA= "+CurrentA+""
		        								+ " ,t.CurrentB= "+CurrentB+""
		        								+ " ,t.CurrentC= "+CurrentC+""
		        								+ " ,t.VoltageA= "+VoltageA+""
		        								+ " ,t.VoltageB= "+VoltageB+""
		        								+ " ,t.VoltageC= "+VoltageC+""
		        								+ " ,t.ActivePower= "+ActivePower+""
		        								+ " ,t.ReactivePower= "+ReactivePower+""
		        								+ " ,t.ReversePower= "+ReversePower+""
		        								+ " ,t.PowerFactor= "+PowerFactor+""
		        								+ " ,t.egklxstr= '"+electricCalculateResponseData.getETResultString()+"'"
		        								+ " ,t.currentstr= '"+electricCalculateResponseData.getCurrentString()+"'"
		        								+ " ,t.voltagestr= '"+electricCalculateResponseData.getVoltageString()+"'"
		        								+ " ,t.currentauplimit= "+electricCalculateResponseData.getElectricLimit().getCurrentA().getMax()+""
		        								+ " ,t.currentadownlimit= "+electricCalculateResponseData.getElectricLimit().getCurrentA().getMin()+""
		        								+ " ,t.currentbuplimit= "+electricCalculateResponseData.getElectricLimit().getCurrentB().getMax()+""
		                						+ " ,t.currentbdownlimit= "+electricCalculateResponseData.getElectricLimit().getCurrentB().getMin()+""
		                						+ " ,t.currentcuplimit= "+electricCalculateResponseData.getElectricLimit().getCurrentC().getMax()+""
		                						+ " ,t.currentcdownlimit= "+electricCalculateResponseData.getElectricLimit().getCurrentC().getMin()+""
		                						+ " ,t.voltageauplimit= "+electricCalculateResponseData.getElectricLimit().getVoltageA().getMax()+""
		                						+ " ,t.voltageadownlimit= "+electricCalculateResponseData.getElectricLimit().getVoltageA().getMin()+""
		                						+ " ,t.voltagebuplimit= "+electricCalculateResponseData.getElectricLimit().getVoltageB().getMax()+""
		                        				+ " ,t.voltagebdownlimit= "+electricCalculateResponseData.getElectricLimit().getVoltageB().getMin()+""
		                        				+ " ,t.voltagecuplimit= "+electricCalculateResponseData.getElectricLimit().getVoltageC().getMax()+""
		                                		+ " ,t.voltagecdownlimit= "+electricCalculateResponseData.getElectricLimit().getVoltageC().getMin()+"";
		            					}
		        						updateTXZT033+=" where t.jbh= (select t007.jlbh from tbl_wellinformation t007 where t007.jh='"+wellName+"') ";
		        						updateTXZT033RT=updateTXZT033.replaceAll("t_outputwellhistory", "t_outputwellrealtime");
		        						updateTXZT033+= " and t.gtcjsj=( select max(t2.gtcjsj) from t_outputwellhistory t2 where t2.jbh=t.jbh  )";
		        						
		        						stmt = conn.createStatement();
	    								int result=stmt.executeUpdate(updateTXZT);
	    								if(hasProData)
	    									result=stmt.executeUpdate(updateProdData);
	    								result=stmt.executeUpdate(updateTXZT033);
	    								result=stmt.executeUpdate(updateTXZT033RT);
									}else{
										short pointCount=StringManagerUtils.getShort(acquisitionData.gtInfo,0);
										float SPM=StringManagerUtils.getFloat(acquisitionData.gtInfo, 14);
										float Stroke=StringManagerUtils.getFloat(acquisitionData.gtInfo, 18);
										StringBuffer recvBuff=new StringBuffer();
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
										recvBuff.append("{\"WellName\":\""+wellName+"\",\"AcqTime\":\""+gtcjsj+"\",");
										
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
	                					
	                					recvBuff.append("\"Diagram\":{\"AcqTime\":\""+gtcjsj+"\",\"AcquisitionCycle\":0,\"SPM\":"+SPM+","+"\"Stroke\":"+Stroke+",");
	                					for(int i=0;i<pointCount;i++){
	                						recvSBuff.append(StringManagerUtils.getShort(acquisitionData.gtSData,i*2)*0.01);
	                						recvFBuff.append(StringManagerUtils.getShort(acquisitionData.gtFData,i*2)*0.01);
	                						recvABuff.append(StringManagerUtils.getShort(acquisitionData.gtAData,i*2)*0.01);
	                						recvPBuff.append(StringManagerUtils.getShort(acquisitionData.gtPData,i*2)*0.01);
	                						if(i<pointCount-1){
	                							recvSBuff.append(",");
	                    						recvFBuff.append(",");
	                    						recvABuff.append(",");
	                    						recvPBuff.append(",");
	                						}else{
	                							recvSBuff.append("]");
	                    						recvFBuff.append("]");
	                    						recvABuff.append("]");
	                    						recvPBuff.append("]");
	                						}
	                					}
	                					recvBuff.append(recvFBuff+","+recvSBuff+","+recvABuff+","+recvPBuff+"}}");
	                					System.out.println("解析读取数据:"+recvBuff.toString());
	                					StringManagerUtils.sendPostMethod(saveFSDiagramUrl, recvBuff.toString(),"utf-8");
									}
									beidouTerminalMap.remove(terminalNo);
								}
							}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					try {
						if(pstmt!=null)
							pstmt.close();
						if(stmt!=null)
							stmt.close(); 
						if(rs!=null)
							rs.close();
						if(conn!=null)
							conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public SerialPort getSerialPort() {
		return serialPort;
	}

	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}
	
}
