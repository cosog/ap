package com.gao.controller.datainterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.TextMessage;

import com.gao.controller.base.BaseController;
import com.gao.model.calculate.PCPCalculateRequestData;
import com.gao.model.calculate.PCPCalculateResponseData;
import com.gao.model.calculate.RPCCalculateResponseData;
import com.gao.model.calculate.CommResponseData;
import com.gao.model.calculate.ElectricCalculateResponseData;
import com.gao.model.calculate.EnergyCalculateResponseData;
import com.gao.model.calculate.TimeEffResponseData;
import com.gao.model.calculate.TimeEffTotalResponseData;
import com.gao.model.calculate.TotalAnalysisRequestData;
import com.gao.model.calculate.TotalAnalysisResponseData;
import com.gao.model.calculate.TotalCalculateRequestData;
import com.gao.model.calculate.TotalCalculateResponseData;
import com.gao.model.calculate.WellAcquisitionData;
import com.gao.model.drive.AcqGroup;
import com.gao.model.drive.AcqOnline;
import com.gao.model.drive.AcquisitionGroupResolutionData;
import com.gao.model.drive.ModbusProtocolConfig;
import com.gao.service.base.CommonDataService;
import com.gao.service.datainterface.CalculateDataService;
import com.gao.task.EquipmentDriverServerTask;
import com.gao.task.KafkaServerTask.KafkaUpData;
import com.gao.thread.calculate.CalculateThread;
import com.gao.thread.calculate.TotalCalculateThread;
import com.gao.utils.Config;
import com.gao.utils.Config2;
import com.gao.utils.Constants;
import com.gao.utils.EquipmentDriveMap;
import com.gao.utils.OracleJdbcUtis;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.gao.websocket.config.WebSocketByJavax;
import com.gao.websocket.handler.SpringWebSocketHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

@Controller
@RequestMapping("/api")
@Scope("prototype")
public class DriverAPIController extends BaseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	CalculateDataService<?> calculateDataService;
	@Autowired
	private CommonDataService commonDataService;
	
	@RequestMapping("/acq/online")
	public String acqOnlineData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		Gson gson=new Gson();
		String commUrl=Config.getInstance().configFile.getAgileCalculate().getCommunication()[0];
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8");
		java.lang.reflect.Type type = new TypeToken<AcqOnline>() {}.getType();
		AcqOnline acqOnline=gson.fromJson(data, type);
		if(acqOnline!=null){
			String sql="select t.wellname ,to_char(t2.acqTime,'yyyy-mm-dd hh24:mi:ss'),"
					+ " t2.commstatus,t2.commtime,t2.commtimeefficiency,t2.commrange"
					+ " from TBL_WELLINFORMATION t,tbl_rpc_discrete_latest  t2 "
					+ " where t.id=t2.wellid"
					+ " and t.deviceaddr='"+acqOnline.getID()+"' and to_number(t.deviceid)="+acqOnline.getSlave();
			List list = this.commonDataService.findCallSql(sql);
			if(list.size()>0){
				Object[] obj=(Object[]) list.get(0);
				String currentTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
				CommResponseData commResponseData=null;
				String commRequest="{"
						+ "\"AKString\":\"\","
						+ "\"WellName\":\""+obj[0]+"\",";
				if(StringManagerUtils.isNotNull(obj[1]+"")&&StringManagerUtils.isNotNull(obj[5]+"")){
					commRequest+= "\"Last\":{"
							+ "\"AcqTime\": \""+obj[1]+"\","
							+ "\"CommStatus\": "+("1".equals(obj[2]+"")?true:false)+","
							+ "\"CommEfficiency\": {"
							+ "\"Efficiency\": "+obj[4]+","
							+ "\"Time\": "+obj[3]+","
							+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(obj[5]))+""
							+ "}"
							+ "},";
				}	
				commRequest+= "\"Current\": {"
						+ "\"AcqTime\":\""+currentTime+"\","
						+ "\"CommStatus\":"+acqOnline.getStatus()+""
						+ "}"
						+ "}";
				String commResponse="";
				commResponse=StringManagerUtils.sendPostMethod(commUrl, commRequest,"utf-8");
				type = new TypeToken<CommResponseData>() {}.getType();
				commResponseData=gson.fromJson(commResponse, type);
				if(commResponseData!=null&&commResponseData.getResultStatus()==1){
					String updateDiscreteData="update tbl_rpc_discrete_latest t set t.CommStatus="+(commResponseData.getCurrent().getCommStatus()?1:0)+",";
					updateDiscreteData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
							+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
					updateDiscreteData+=" where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+commResponseData.getWellName()+"') ";
					commonDataService.getBaseDao().updateOrDeleteBySql(updateDiscreteData);
					
					String updateRunRangeClobSql="update tbl_rpc_discrete_latest t set t.commrange=?0 where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+commResponseData.getWellName()+"') ";
					List<String> clobCont=new ArrayList<String>();
					clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
					int result=commonDataService.getBaseDao().executeSqlUpdateClob(updateRunRangeClobSql,clobCont);
				}
			}
		}
		String json = "{success:true,flag:true}";
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/acq/group")
	public String acqGroupData() throws Exception {
		ServletInputStream ss = request.getInputStream();
		Gson gson=new Gson();
		String tiemEffUrl=Config.getInstance().configFile.getAgileCalculate().getRun()[0];
		String commUrl=Config.getInstance().configFile.getAgileCalculate().getCommunication()[0];
		String energyUrl=Config.getInstance().configFile.getAgileCalculate().getEnergy()[0];
		String FESdiagramCalculateHttpServerURL=Config.getInstance().configFile.getAgileCalculate().getFESDiagram()[0];
		String data=StringManagerUtils.convertStreamToString(ss,"utf-8");
		java.lang.reflect.Type type = new TypeToken<AcqGroup>() {}.getType();
		AcqGroup acqGroup=gson.fromJson(data, type);
		AcquisitionGroupResolutionData acquisitionGroupResolutionData=new AcquisitionGroupResolutionData();
		acquisitionGroupResolutionData.setSDiagram(new ArrayList<Float>());
		acquisitionGroupResolutionData.setFDiagram(new ArrayList<Float>());
		acquisitionGroupResolutionData.setIDiagram(new ArrayList<Float>());
		acquisitionGroupResolutionData.setWattDiagram(new ArrayList<Float>());
		if(acqGroup!=null){
			boolean isRunStatusData=false;
			boolean isDistreteData=false;
			boolean isEnergyData=false;
			boolean isProductionData=false;
			boolean isFESDiagramData=false;
			boolean isScrewPumpDataData=false;
			boolean ifAddDay=false;
			String sql="select t.wellname ,to_char(t2.acqTime,'yyyy-mm-dd hh24:mi:ss'),"
					+ " t2.commstatus,t2.commtime,t2.commtimeefficiency,t2.commrange,"
					+ " t2.runstatus,t2.runtime,t2.runtimeefficiency,t2.runrange,"
					+ " t2.totalKWattH,t2.totalPKWattH,t2.totalNKWattH,t2.totalKVarH,t2.totalpKVarH,t2.totalNKVarH,t2.totalKVAH,"
					+ " t2.todayKWattH,t2.todayPKWattH,t2.todayNKWattH,t2.todayKVarH,t2.todaypKVarH,t2.todayNKVarH,t2.todayKVAH,"
					+ " t.id,t3.protocol"
					+ " from TBL_WELLINFORMATION t,tbl_rpc_discrete_latest  t2,tbl_acq_unit_conf t3  "
					+ " where t.id=t2.wellid and t.unitcode=t3.unit_code"
					+ " and t.deviceaddr='"+acqGroup.getID()+"' and to_number(t.deviceid)="+acqGroup.getSlave();
			List list = this.commonDataService.findCallSql(sql);
			if(list.size()>0){
				Object[] obj=(Object[]) list.get(0);
				String wellName=obj[0]+"";
				acquisitionGroupResolutionData.setAcqTime(StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				acquisitionGroupResolutionData.setWellName(wellName);
				acquisitionGroupResolutionData.setWellId(StringManagerUtils.stringToInteger(obj[24]+""));
				String protocolName=obj[obj.length-1]+"";
				
				Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
				if(equipmentDriveMap.size()==0){
					EquipmentDriverServerTask.loadProtocolConfig();
					equipmentDriveMap = EquipmentDriveMap.getMapObject();
				}
				ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
				
				ModbusProtocolConfig.Protocol protocol=null;
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
						protocol=modbusProtocolConfig.getProtocol().get(i);
						break;
					}
				}
				if(protocol!=null){
					//通信计算
					CommResponseData commResponseData=null;
					String commRequest="{"
							+ "\"AKString\":\"\","
							+ "\"WellName\":\""+acquisitionGroupResolutionData.getWellName()+"\",";
					if(StringManagerUtils.isNotNull(obj[1]+"")&&StringManagerUtils.isNotNull(obj[5]+"")){
						commRequest+= "\"Last\":{"
								+ "\"AcqTime\": \""+obj[1]+"\","
								+ "\"CommStatus\": "+("1".equals(obj[2]+"")?true:false)+","
								+ "\"CommEfficiency\": {"
								+ "\"Efficiency\": "+obj[4]+","
								+ "\"Time\": "+obj[3]+","
								+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(obj[5]))+""
								+ "}"
								+ "},";
					}	
					commRequest+= "\"Current\": {"
							+ "\"AcqTime\":\""+acquisitionGroupResolutionData.getAcqTime()+"\","
							+ "\"CommStatus\":true"
							+ "}"
							+ "}";
					String commResponse="";
					commResponse=StringManagerUtils.sendPostMethod(commUrl, commRequest,"utf-8");
					type = new TypeToken<CommResponseData>() {}.getType();
					commResponseData=gson.fromJson(commResponse, type);
					
					String updateDailyData="";
					String updateProdData="update tbl_rpc_productiondata_latest t set t.acqTime=to_date('"+acquisitionGroupResolutionData.getAcqTime()+"','yyyy-mm-dd hh24:mi:ss')";
					String updateDiscreteData="update tbl_rpc_discrete_latest t set t.CommStatus=1,t.acqTime=to_date('"+acquisitionGroupResolutionData.getAcqTime()+"','yyyy-mm-dd hh24:mi:ss')";
					if(commResponseData!=null&&commResponseData.getResultStatus()==1){
						updateDiscreteData+=",t.commTimeEfficiency= "+commResponseData.getCurrent().getCommEfficiency().getEfficiency()
								+ " ,t.commTime= "+commResponseData.getCurrent().getCommEfficiency().getTime();
					}
					
					
					for(int i=0;acqGroup.getAddr()!=null&&i<acqGroup.getAddr().size();i++){
						for(int j=0;j<protocol.getItems().size();j++){
							if(acqGroup.getAddr().get(i)==protocol.getItems().get(j).getAddr()){
								String itemCode=protocol.getItems().get(j).getCode();
								if("RunStatus".equalsIgnoreCase(itemCode)){//运行状态
									acquisitionGroupResolutionData.setRunStatus((int)acqGroup.getValue().get(i).get(0));
									isRunStatusData=true;
								}else if("IA".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setIA((float)acqGroup.getValue().get(i).get(0));
									updateDiscreteData+=",t.Ia= "+acquisitionGroupResolutionData.getIA();
									isDistreteData=true;
								}else if("IB".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setIB((float)acqGroup.getValue().get(i).get(0));
									updateDiscreteData+=",t.Ib= "+acquisitionGroupResolutionData.getIB();
									isDistreteData=true;
								}else if("IC".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setIC((float)acqGroup.getValue().get(i).get(0));
									updateDiscreteData+=",t.Ic= "+acquisitionGroupResolutionData.getIC();
									isDistreteData=true;
								}else if("VA".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setVA((float)acqGroup.getValue().get(i).get(0));
									updateDiscreteData+=",t.Va= "+acquisitionGroupResolutionData.getVA();
									isDistreteData=true;
								}else if("VB".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setVB((float)acqGroup.getValue().get(i).get(0));
									updateDiscreteData+=",t.Vb= "+acquisitionGroupResolutionData.getVB();
									isDistreteData=true;
								}else if("VC".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setVC((float)acqGroup.getValue().get(i).get(0));
									updateDiscreteData+=",t.Vc= "+acquisitionGroupResolutionData.getVC();
									isDistreteData=true;
								}else if("KWattH".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setKWattH((float)acqGroup.getValue().get(i).get(0));
									isEnergyData=true;
								}else if("KVarH".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setKVarH((float)acqGroup.getValue().get(i).get(0));
									isEnergyData=true;
								}else if("Watt3".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setWatt3((float)acqGroup.getValue().get(i).get(0));
									updateDiscreteData+=",t.WattSum= "+acquisitionGroupResolutionData.getWatt3();
									isDistreteData=true;
								}else if("Var3".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setVar3((float)acqGroup.getValue().get(i).get(0));
									updateDiscreteData+=",t.VarSum= "+acquisitionGroupResolutionData.getVar3();
									isDistreteData=true;
								}else if("ReversePower".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setReversePower((float)acqGroup.getValue().get(i).get(0));
									isDistreteData=true;
								}else if("PF3".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setPF3((float)acqGroup.getValue().get(i).get(0));
									updateDiscreteData+=",t.PFSum= "+acquisitionGroupResolutionData.getPF3();
									isDistreteData=true;
								}else if("RunFrequency".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setRunFrequency((float)acqGroup.getValue().get(i).get(0));
									updateDiscreteData+=",t.RunFrequency= "+acquisitionGroupResolutionData.getRunFrequency();
									isDistreteData=true;
								}else if("SetFrequency".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setSetFrequency((float)acqGroup.getValue().get(i).get(0));
									updateDiscreteData+=",t.SetFrequency= "+acquisitionGroupResolutionData.getSetFrequency();
									isDistreteData=true;
								}else if("TubingPressure".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setTubingPressure((float)acqGroup.getValue().get(i).get(0));
									updateProdData+=",t.TubingPressure="+acquisitionGroupResolutionData.getTubingPressure();
									isProductionData=true;
								}else if("CasingPressure".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setCasingPressure((float)acqGroup.getValue().get(i).get(0));
									updateProdData+=",t.CasingPressure="+acquisitionGroupResolutionData.getCasingPressure();
									isProductionData=true;
								}else if("BackPressure".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setBackPressure((float)acqGroup.getValue().get(i).get(0));
									isProductionData=true;
								}else if("WellHeadFluidTemperature".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setWellHeadFluidTemperature((float)acqGroup.getValue().get(i).get(0));
									updateProdData+=",t.WellHeadFluidTemperature="+acquisitionGroupResolutionData.getWellHeadFluidTemperature();
									isProductionData=true;
								}else if("ProducingfluidLevel".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setProducingfluidLevel((float)acqGroup.getValue().get(i).get(0));
									updateProdData+=",t.ProducingfluidLevel="+acquisitionGroupResolutionData.getProducingfluidLevel();
									isProductionData=true;
								}else if("WaterCut".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setWaterCut((float)acqGroup.getValue().get(i).get(0));
									updateProdData+=",t.waterCut="+acquisitionGroupResolutionData.getWaterCut();
									isProductionData=true;
								}else if("RPM".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setRPM((float)acqGroup.getValue().get(i).get(0));
									isScrewPumpDataData=true;
								}else if("Torque".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setTorque((float)acqGroup.getValue().get(i).get(0));
									isScrewPumpDataData=true;
								}else if("FESDiagramAcquisitionInterval".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setFESDiagramAcquisitionInterval((int)acqGroup.getValue().get(i).get(0));
									isFESDiagramData=true;
								}else if("FESDiagramSetPointCount".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setFESDiagramSetPointCount((int)acqGroup.getValue().get(i).get(0));
									isFESDiagramData=true;
								}else if("FESDiagramPointCount".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setFESDiagramPointCount((int)acqGroup.getValue().get(i).get(0));
									isFESDiagramData=true;
								}else if("FESDiagramAcqTime".equalsIgnoreCase(itemCode)){
									String FESDiagramAcqTime=StringManagerUtils.BCD2TimeString(StringManagerUtils.hexStringToBytes(acqGroup.getValue().get(i).get(0)+""), 0);
									acquisitionGroupResolutionData.setFESDiagramAcqTime(FESDiagramAcqTime);
									isFESDiagramData=true;
								}else if("SPM".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setSPM((float)acqGroup.getValue().get(i).get(0));
									isFESDiagramData=true;
								}else if("Stroke".equalsIgnoreCase(itemCode)){
									acquisitionGroupResolutionData.setStroke((float)acqGroup.getValue().get(i).get(0));
									isFESDiagramData=true;
								}else if("SDiagram".equalsIgnoreCase(itemCode)){
									for(int k=0;k<acqGroup.getValue().get(i).size();k++){
										acquisitionGroupResolutionData.getSDiagram().add((float)acqGroup.getValue().get(i).get(k));
									}
									isFESDiagramData=true;
								}else if("FDiagram".equalsIgnoreCase(itemCode)){
									for(int k=0;k<acqGroup.getValue().get(i).size();k++){
										acquisitionGroupResolutionData.getFDiagram().add((float)acqGroup.getValue().get(i).get(k));
									}
									isFESDiagramData=true;
								}else if("IDiagram".equalsIgnoreCase(itemCode)){
									for(int k=0;k<acqGroup.getValue().get(i).size();k++){
										acquisitionGroupResolutionData.getIDiagram().add((float)acqGroup.getValue().get(i).get(k));
									}
									isFESDiagramData=true;
								}else if("WattDiagram".equalsIgnoreCase(itemCode)){
									for(int k=0;k<acqGroup.getValue().get(i).size();k++){
										acquisitionGroupResolutionData.getWattDiagram().add((float)acqGroup.getValue().get(i).get(k));
									}
									isFESDiagramData=true;
								}
								break;
							}
						}
					}
					
					
					
					//判断是否采集了运行状态，如采集则进行时率计算
					TimeEffResponseData timeEffResponseData=null;
					if(isRunStatusData){
						String tiemEffRequest="{"
								+ "\"AKString\":\"\","
								+ "\"WellName\":\""+acquisitionGroupResolutionData.getWellName()+"\",";
						if(StringManagerUtils.isNotNull(obj[1]+"")&&StringManagerUtils.isNotNull(obj[9]+"")){
							tiemEffRequest+= "\"Last\":{"
									+ "\"AcqTime\": \""+obj[1]+"\","
									+ "\"RunStatus\": "+("1".equals(obj[6]+"")?true:false)+","
									+ "\"RunEfficiency\": {"
									+ "\"Efficiency\": "+obj[8]+","
									+ "\"Time\": "+obj[7]+","
									+ "\"Range\": "+StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(obj[9]))+""
									+ "}"
									+ "},";
						}	
						tiemEffRequest+= "\"Current\": {"
								+ "\"AcqTime\":\""+acquisitionGroupResolutionData.getAcqTime()+"\","
								+ "\"RunStatus\":"+acquisitionGroupResolutionData.getRunStatus()+""
								+ "}"
								+ "}";
						String timeEffResponse="";
						timeEffResponse=StringManagerUtils.sendPostMethod(tiemEffUrl, tiemEffRequest,"utf-8");
						type = new TypeToken<TimeEffResponseData>() {}.getType();
						timeEffResponseData=gson.fromJson(timeEffResponse, type);
					}
					
					//判断是否采集了电量，如采集则进行电量计算
					EnergyCalculateResponseData energyCalculateResponseData=null;
					if(isEnergyData){
						String energyRequest="{"
								+ "\"AKString\":\"\","
								+ "\"WellName\":\""+acquisitionGroupResolutionData.getWellName()+"\",";
						if(StringManagerUtils.isNotNull(obj[1]+"")){
							energyRequest+= "\"Last\":{"
									+ "\"AcqTime\": \""+obj[1]+"\","
									+ "\"Total\":{"
									+ "\"KWattH\":"+obj[10]+","
									+ "\"PKWattH\":"+obj[11]+","
									+ "\"NKWattH\":"+obj[12]+","
									+ "\"KVarH\":"+obj[13]+","
									+ "\"PKVarH\":"+obj[14]+","
									+ "\"NKVarH\":"+obj[15]+","
									+ "\"KVAH\":"+obj[16]+""
									+ "},\"Today\":{"
									+ "\"KWattH\":"+obj[17]+","
									+ "\"PKWattH\":"+obj[18]+","
									+ "\"NKWattH\":"+obj[19]+","
									+ "\"KVarH\":"+obj[20]+","
									+ "\"PKVarH\":"+obj[21]+","
									+ "\"NKVarH\":"+obj[22]+","
									+ "\"KVAH\":"+obj[23]+""
									+ "}"
									+ "},";
						}	
						energyRequest+= "\"Current\": {"
								+ "\"AcqTime\":\""+acquisitionGroupResolutionData.getAcqTime()+"\","
								+ "\"Total\":{"
								+ "\"KWattH\":"+acquisitionGroupResolutionData.getKWattH()+","
								+ "\"KVarH\":"+acquisitionGroupResolutionData.getKVarH()+","
								+ "}"
								+ "}"
								+ "}";
						String energyResponse=StringManagerUtils.sendPostMethod(energyUrl, energyRequest,"utf-8");
						type = new TypeToken<EnergyCalculateResponseData>() {}.getType();
						energyCalculateResponseData=gson.fromJson(energyResponse, type);
					}
					
					updateProdData+=" where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+acquisitionGroupResolutionData.getWellName()+"') ";
					
					if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
						updateDiscreteData+=",t.runTimeEfficiency= "+timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency()
							+ " ,t.runTime= "+timeEffResponseData.getCurrent().getRunEfficiency().getTime();
						if(timeEffResponseData.getDaily()!=null&&StringManagerUtils.isNotNull(timeEffResponseData.getDaily().getDate())){
							ifAddDay=true;
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
							updateDailyData="update tbl_rpc_total_day t set t.TotalKWattH= "+energyCalculateResponseData.getDaily().getKWattH()
									+ ",t.TotalPKWattH= "+energyCalculateResponseData.getDaily().getPKWattH()
									+ ",t.TotalNKWattH= "+energyCalculateResponseData.getDaily().getNKWattH()
									+ ",t.TotalKVarH= "+energyCalculateResponseData.getDaily().getKVarH()
									+ ",t.TotalPKVarH= "+energyCalculateResponseData.getDaily().getPKVarH()
									+ ",t.TotalNKVarH= "+energyCalculateResponseData.getDaily().getNKVarH()
									+ ",t.TotalKVAH= "+energyCalculateResponseData.getDaily().getKVAH()
									
									+ ",t.TodayKWattH= "+energyCalculateResponseData.getDaily().getKWattH()
									+ ",t.TodayPKWattH= "+energyCalculateResponseData.getDaily().getPKWattH()
									+ ",t.TodayNKWattH= "+energyCalculateResponseData.getDaily().getNKWattH()
									+ ",t.TodayKVarH= "+energyCalculateResponseData.getDaily().getKVarH()
									+ ",t.TodayPKVarH= "+energyCalculateResponseData.getDaily().getPKVarH()
									+ ",t.TodayNKVarH= "+energyCalculateResponseData.getDaily().getNKVarH()
									+ ",t.TodayKVAH= "+energyCalculateResponseData.getDaily().getKVAH()
									+ " where t.calculatedate=to_date('"+energyCalculateResponseData.getDaily().getDate()+"','yyyy-mm-dd') "
							         +" and t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+acquisitionGroupResolutionData.getWellName()+"') ";
						}
					}else{
						updateDiscreteData+= " ,t.totalKWattH= "+acquisitionGroupResolutionData.getKWattH()
								+ " ,t.totalKVarH= "+acquisitionGroupResolutionData.getKVarH();
					}
					updateDiscreteData+=" where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+acquisitionGroupResolutionData.getWellName()+"') ";
					if(isProductionData){
						commonDataService.getBaseDao().updateOrDeleteBySql(updateProdData);
					}
					if(isDistreteData){
						commonDataService.getBaseDao().updateOrDeleteBySql(updateDiscreteData);
						//更新clob类型数据  运行区间
						if(commResponseData!=null&&commResponseData.getResultStatus()==1){
							List<String> clobCont=new ArrayList<String>();
							String updateRunRangeClobSql="update tbl_rpc_discrete_latest t set t.commrange=?0";
							clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
							if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){
								updateRunRangeClobSql+=", t.runrange=?1";
								clobCont.add(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString());
							}
							updateRunRangeClobSql+= " where t.wellId= (select t2.id from tbl_wellinformation t2 where t2.wellName='"+acquisitionGroupResolutionData.getWellName()+"') ";
							int result=commonDataService.getBaseDao().executeSqlUpdateClob(updateRunRangeClobSql,clobCont);
						}
						if(StringManagerUtils.isNotNull(updateDailyData)){
							commonDataService.getBaseDao().updateOrDeleteBySql(updateDailyData);
						}
					}
					
					//处理曲线数据
					if(isFESDiagramData){
						String requestData=calculateDataService.getFESdiagramCalculateRequestData(acquisitionGroupResolutionData);
						String responseData=StringManagerUtils.sendPostMethod(FESdiagramCalculateHttpServerURL, requestData,"utf-8");
						type = new TypeToken<RPCCalculateResponseData>() {}.getType();
						RPCCalculateResponseData rpcCalculateResponseData=gson.fromJson(responseData, type);
						calculateDataService.saveFESDiagramAndCalculateData(acquisitionGroupResolutionData,rpcCalculateResponseData);
						//汇总
						String totalDate = StringManagerUtils.getCurrentTime();
						String totalUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/FSDiagramDailyCalculation?wellId="+acquisitionGroupResolutionData.getWellId();
//						String totalUrl=Config.getInstance().configFile.getServer().getAccessPath()+"/calculateDataController/FSDiagramDailyCalculation?date="+totalDate;
						if((rpcCalculateResponseData!=null&&rpcCalculateResponseData.getCalculationStatus().getResultStatus()==1)){
							if(ifAddDay){//如果跨天，汇总前一天数据
								StringManagerUtils.sendPostMethod(totalUrl, "","utf-8");
							}
							totalUrl+="&date="+totalDate;
							totalUrl+="&endAcqTime="+java.net.URLEncoder.encode(acquisitionGroupResolutionData.getFESDiagramAcqTime(), "UTF-8");
							StringManagerUtils.sendPostMethod(totalUrl, "","utf-8");
						}
					}
				}
			}
		}
		String json = "{success:true,flag:true}";
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
}