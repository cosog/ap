package com.cosog.controller.realTimeMonitoring;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.Org;
import com.cosog.model.User;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.gridmodel.WellGridPanelData;
import com.cosog.model.gridmodel.WellHandsontableChangedData;
import com.cosog.service.back.WellInformationManagerService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.data.DataitemsInfoService;
import com.cosog.service.realTimeMonitoring.RealTimeMonitoringService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.AcquisitionItemColumnsMap;
import com.cosog.utils.Config;
import com.cosog.utils.Constants;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.Page;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.UnixPwdCrypt;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/realTimeMonitoringController")
@Scope("prototype")
public class RealTimeMonitoringController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(RealTimeMonitoringController.class);
	@Autowired
	private RealTimeMonitoringService<?> realTimeMonitoringService;
	@Autowired
	private CommonDataService service;
	@Autowired
	private DataitemsInfoService dataitemsInfoService;
	private String limit;
	private String msg = "";
	private String deviceName;
	private String deviceType;
	private String FESdiagramResultStatValue;
	private String commStatusStatValue;
	private String runStatusStatValue;
	private String deviceTypeStatValue;
	private String page;
	private String orgId;
	private int totals;
	
	@RequestMapping("/getDeviceRealTimeStat")
	public String getDeviceRealTimeStat() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		json = realTimeMonitoringService.getDeviceRealTimeStat(orgId,deviceType);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getRealTimeMonitoringFESDiagramResultStatData")
	public String getRealTimeMonitoringFESDiagramResultStatData() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		commStatusStatValue = ParamUtils.getParameter(request, "commStatusStatValue");
		deviceTypeStatValue = ParamUtils.getParameter(request, "deviceTypeStatValue");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		json = realTimeMonitoringService.getRealTimeMonitoringFESDiagramResultStatData(orgId,deviceType,commStatusStatValue,deviceTypeStatValue);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getRealTimeMonitoringCommStatusStatData")
	public String getRealTimeMonitoringCommStatusStatData() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		deviceTypeStatValue = ParamUtils.getParameter(request, "deviceTypeStatValue");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		json = realTimeMonitoringService.getRealTimeMonitoringCommStatusStatData(orgId,deviceType,deviceTypeStatValue);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getRealTimeMonitoringRunStatusStatData")
	public String getRealTimeMonitoringRunStatusStatData() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		deviceTypeStatValue = ParamUtils.getParameter(request, "deviceTypeStatValue");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		json = realTimeMonitoringService.getRealTimeMonitoringRunStatusStatData(orgId,deviceType,deviceTypeStatValue);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getRealTimeMonitoringDeviceTypeStatData")
	public String getRealTimeMonitoringDeviceTypeStatData() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		commStatusStatValue = ParamUtils.getParameter(request, "commStatusStatValue");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		json = realTimeMonitoringService.getRealTimeMonitoringDeviceTypeStatData(orgId,deviceType,commStatusStatValue);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceRealTimeCommStatusStat")
	public String getDeviceRealTimeCommStatusStat() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		json = realTimeMonitoringService.getDeviceRealTimeCommStatusStat(orgId,deviceType);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	//
	@RequestMapping("/getDeviceRealTimeOverview")
	public String getDeviceRealTimeOverview() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceName = ParamUtils.getParameter(request, "deviceName");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		FESdiagramResultStatValue = ParamUtils.getParameter(request, "FESdiagramResultStatValue");
		commStatusStatValue = ParamUtils.getParameter(request, "commStatusStatValue");
		runStatusStatValue = ParamUtils.getParameter(request, "runStatusStatValue");
		deviceTypeStatValue = ParamUtils.getParameter(request, "deviceTypeStatValue");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		if(StringManagerUtils.stringToInteger(deviceType)==0){
			json = realTimeMonitoringService.getDeviceRealTimeOverview(orgId,deviceName,deviceType,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,pager);
		}else{
			json = realTimeMonitoringService.getPCPDeviceRealTimeOverview(orgId,deviceName,deviceType,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,pager);
		}
		
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportDeviceRealTimeOverviewDataExcel")
	public String exportDeviceRealTimeOverviewDataExcel() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		FESdiagramResultStatValue = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "FESdiagramResultStatValue"),"utf-8");
		commStatusStatValue = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "commStatusStatValue"),"utf-8");
		runStatusStatValue = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "runStatusStatValue"),"utf-8");
		deviceTypeStatValue = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceTypeStatValue"),"utf-8");
		
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		
		DataDictionary ddic = null;
		String ddicName="rpcRealTimeOverview";
		if(StringManagerUtils.stringToInteger(deviceType)!=0){
			ddicName="pcpRealTimeOverview";
		}
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		heads=StringUtils.join(ddic.getHeaders(), ",");
		fields=StringUtils.join(ddic.getFields(), ",");
		this.pager = new Page("pagerForm", request);
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		if(StringManagerUtils.stringToInteger(deviceType)==0){
			json = realTimeMonitoringService.getDeviceRealTimeOverviewExportData(orgId,deviceName,deviceType,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,pager);
		}else{
			json = realTimeMonitoringService.getPCPDeviceRealTimeOverviewExportData(orgId,deviceName,deviceType,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,pager);
		}
		
		
		
		this.service.exportGridPanelData(response,fileName,title, heads, fields,json);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceRealTimeMonitoringData")
	public String getDeviceRealTimeMonitoringData() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		deviceName = ParamUtils.getParameter(request, "deviceName");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		User user = (User) session.getAttribute("userLogin");
		if(user!=null){
			json = realTimeMonitoringService.getDeviceRealTimeMonitoringData(deviceId,deviceName,deviceType,user.getUserId());
		}
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceControlandInfoData")
	public String getDeviceControlandInfoData() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String deviceId=ParamUtils.getParameter(request, "deviceId");
		String wellName = ParamUtils.getParameter(request, "wellName");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			json = realTimeMonitoringService.getPCPDeviceControlandInfoData(deviceId,wellName,deviceType,user);
		}else{
			json = realTimeMonitoringService.getRPCDeviceControlandInfoData(deviceId,wellName,deviceType,user);
		}
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}


	@RequestMapping("/loadCurveTypeComboxList")
	public String loadCurveTypeComboxList() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String wellName = ParamUtils.getParameter(request, "wellName");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		json = realTimeMonitoringService.loadCurveTypeComboxList(wellName,deviceType);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getRealTimeCurveData")
	public String getRealTimeCurveData() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String item = ParamUtils.getParameter(request, "item");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		json = realTimeMonitoringService.getRealTimeCurveData(deviceName,item,deviceType);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getRealTimeMonitoringCurveData")
	public String getRealTimeMonitoringCurveData() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		if(user!=null){
			json = realTimeMonitoringService.getRealTimeMonitoringCurveData(deviceId,deviceName,deviceType,user.getUserId());
		}
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	public boolean DeviceControlOperation_Mdubus(String protocolName,String deviceId,String wellName,String deviceType,String ID,String Slave,String itemCode,String controlValue){
		boolean result=true;
		try {
			int dataSaveMode=Config.getInstance().configFile.getOthers().getDataSaveMode();
			String columnsKey="rpcDeviceAcquisitionItemColumns";
			int DeviceType=0;
			if((StringManagerUtils.stringToInteger(deviceType)>=200&&StringManagerUtils.stringToInteger(deviceType)<300) || StringManagerUtils.stringToInteger(deviceType)==1){
				columnsKey="pcpDeviceAcquisitionItemColumns";
				DeviceType=1;
			}
			Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
			if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
				EquipmentDriverServerTask.loadAcquisitionItemColumns(DeviceType);
			}
			Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
			
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String url=Config.getInstance().configFile.getAd().getWriteAddr();
			String readUrl=Config.getInstance().configFile.getAd().getReadAddr();
			
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			
			ModbusProtocolConfig.Protocol protocol=null;
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
					protocol=modbusProtocolConfig.getProtocol().get(i);
					break;
				}
			}
			int addr=-99;
			String dataType="";
			String title="";
			float ratio=0;
			for(int i=0;i<protocol.getItems().size();i++){
				String col=dataSaveMode==0?("addr"+protocol.getItems().get(i).getAddr()):(loadedAcquisitionItemColumnsMap.get(protocol.getItems().get(i).getTitle()));
				if(itemCode.equalsIgnoreCase(col)){
					addr=protocol.getItems().get(i).getAddr();
					dataType=protocol.getItems().get(i).getIFDataType();
					title=protocol.getItems().get(i).getTitle();
					ratio=protocol.getItems().get(i).getRatio();
					break;
				}
			}
			if(StringManagerUtils.isNotNull(title) && addr!=-99){
				String ctrlJson="{"
						+ "\"ID\":\""+ID+"\","
						+ "\"Slave\":"+Slave+","
						+ "\"Addr\":"+addr+","
						+ "\"Value\":["+StringManagerUtils.objectToString(controlValue, dataType)+"]"
						+ "}";
				String readJson="{"
						+ "\"ID\":\""+ID+"\","
						+ "\"Slave\":"+Slave+","
						+ "\"Addr\":"+addr+""
						+ "}";
				String responseStr="";
				responseStr=StringManagerUtils.sendPostMethod(url, ctrlJson,"utf-8");
				if(!StringManagerUtils.isNotNull(responseStr)){
					result=false;
				}
				realTimeMonitoringService.saveDeviceControlLog(deviceId,wellName,deviceType,title,StringManagerUtils.objectToString(controlValue, dataType),user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=false;
		}
		return result;
	}
	
	@RequestMapping("/deviceControlOperation")
	public String DeviceControlOperation() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String wellName = request.getParameter("wellName");
		String deviceId = request.getParameter("deviceId");
		String deviceType = request.getParameter("deviceType");
		String password = request.getParameter("password");
		String controlType = request.getParameter("controlType");
		String controlValue = request.getParameter("controlValue");
		String jsonLogin = "";
		String clientIP=StringManagerUtils.getIpAddr(request);
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		
		String deviceTableName="tbl_rpcdevice";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			deviceTableName="tbl_pcpdevice";
		}
		
		
		// 用户不存在
		if (null != userInfo) {
			String getUpwd = userInfo.getUserPwd();
//			String getOld = UnixPwdCrypt.crypt("dogVSgod", password);
			String getOld = StringManagerUtils.stringToMD5(password);
			if (getOld.equals(getUpwd)&&StringManagerUtils.isNumber(controlValue)) {
				String sql="select t3.protocol, t.signinid,to_number(t.slave),t.deviceType from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 "
						+ " where t.instancecode=t2.code and t2.unitid=t3.id"
						+ " and t.wellname='"+wellName+"' ";
				List list = this.service.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[]) list.get(0);
					String protocol=obj[0]+"";
					String signinid=obj[1]+"";
					String slave=obj[2]+"";
					String realDeviceType=obj[3]+"";
					if(StringManagerUtils.isNotNull(protocol) && StringManagerUtils.isNotNull(signinid)){
						if(StringManagerUtils.isNotNull(slave)){
//							jsonLogin=
							if(DeviceControlOperation_Mdubus(protocol,deviceId,wellName,realDeviceType,signinid,slave,controlType,controlValue)){
								jsonLogin = "{success:true,flag:true,error:true,msg:'<font color=blue>命令发送成功。</font>'}";
							}else{
								jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>命令发送失败。</font>'}";
							}
						}
					}else{
						jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>协议配置有误，请核查！</font>'}";
					}
				}else{
					jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>该井不存在，请核查！</font>'}";
				}
			}else if(getOld.equals(getUpwd) && !StringManagerUtils.isNumber(controlValue)){
				jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>数据有误，请检查输入数据！</font>'}";
			} else {
				jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>您输入的密码有误！</font>'}";
			}

		} else {
			jsonLogin = "{success:true,flag:false}";
		}
		// 处理乱码。
		response.setCharacterEncoding("utf-8");
		// 输出json数据。
		out.print(jsonLogin);
		return null;
	}
	
	@RequestMapping("/deviceControlOperationWhitoutPass")
	public String deviceControlOperationWhitoutPass() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String deviceId = request.getParameter("deviceId");
		String wellName = request.getParameter("wellName");
		String deviceType = request.getParameter("deviceType");
		String controlType = request.getParameter("controlType");
		String controlValue = request.getParameter("controlValue");
		String jsonLogin = "";
		String clientIP=StringManagerUtils.getIpAddr(request);
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		
		String deviceTableName="tbl_rpcdevice";
		if(StringManagerUtils.stringToInteger(deviceType)==1){
			deviceTableName="tbl_pcpdevice";
		}
		// 用户不存在
		if (null != userInfo) {
			if (StringManagerUtils.isNumber(controlValue)) {
				String sql="select t3.protocol, t.signinid,to_number(t.slave),t.deviceType from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 "
						+ " where t.instancecode=t2.code and t2.unitid=t3.id"
						+ " and t.id="+deviceId;
				List list = this.service.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[]) list.get(0);
					String protocol=obj[0]+"";
					String signinid=obj[1]+"";
					String slave=obj[2]+"";
					String realDeviceType=obj[3]+"";
					if(StringManagerUtils.isNotNull(protocol) && StringManagerUtils.isNotNull(signinid)){
						if(StringManagerUtils.isNotNull(slave)){
//							jsonLogin=
							if(DeviceControlOperation_Mdubus(protocol,deviceId,wellName,realDeviceType,signinid,slave,controlType,controlValue)){
								jsonLogin = "{success:true,flag:true,error:true,msg:'<font color=blue>命令发送成功。</font>'}";
							}else{
								jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>命令发送失败。</font>'}";
							}
						}
					}else{
						jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>协议配置有误，请核查！</font>'}";
					}
				}else{
					jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>该井不存在，请核查！</font>'}";
				}
			}else {
				jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>数据有误，请检查输入数据！</font>'}";
			}

		} else {
			jsonLogin = "{success:true,flag:false}";
		}
		// 处理乱码。
		response.setCharacterEncoding("utf-8");
		// 输出json数据。
		out.print(jsonLogin);
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getResourceProbeHistoryCurveData")
	public String getResourceProbeHistoryCurveData() throws Exception {
		String json = "";
		String endDate = ParamUtils.getParameter(request, "endDate");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String itemName = ParamUtils.getParameter(request, "itemName");
		String itemCode = ParamUtils.getParameter(request, "itemCode");
		
		this.pager = new Page("pagerForm", request);
		if(!StringManagerUtils.isNotNull(endDate)){
			String sql = " select to_char(max(t.acqTime),'yyyy-mm-dd') from tbl_resourcemonitoring t ";
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime();
			}
		}
		
		if(!StringManagerUtils.isNotNull(startDate)){
			startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate),0);
		}
		
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		json =  this.realTimeMonitoringService.getResourceProbeHistoryCurveData(startDate,endDate,itemName,itemCode);
		
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 描述：查询功图分析详情数据
	 */
	@RequestMapping("/querySingleFESDiagramDetailsChartsData")
	public String querySingleFESDiagramDetailsChartsData()throws Exception{
		int id = Integer.parseInt(ParamUtils.getParameter(request, "id"));
		String wellName = ParamUtils.getParameter(request, "wellName");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String type = ParamUtils.getParameter(request, "type");
		String json = "";
		if("1".equals(type)){
			json = this.realTimeMonitoringService.querySingleDetailsWellBoreChartsData(id,wellName);
		}else if("2".equals(type)){
			json = this.realTimeMonitoringService.querySingleDetailsSurfaceData(id,wellName);
		}
		
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public int getTotals() {
		return totals;
	}

	public void setTotals(int totals) {
		this.totals = totals;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceTypeStatValue() {
		return deviceTypeStatValue;
	}

	public void setDeviceTypeStatValue(String deviceTypeStatValue) {
		this.deviceTypeStatValue = deviceTypeStatValue;
	}

	public String getFESdiagramResultStatValue() {
		return FESdiagramResultStatValue;
	}

	public void setFESdiagramResultStatValue(String fESdiagramResultStatValue) {
		FESdiagramResultStatValue = fESdiagramResultStatValue;
	}

	public String getCommStatusStatValue() {
		return commStatusStatValue;
	}

	public void setCommStatusStatValue(String commStatusStatValue) {
		this.commStatusStatValue = commStatusStatValue;
	}

	public String getRunStatusStatValue() {
		return runStatusStatValue;
	}

	public void setRunStatusStatValue(String runStatusStatValue) {
		this.runStatusStatValue = runStatusStatValue;
	}
}
