package com.cosog.controller.realTimeMonitoring;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import com.cosog.model.AccessToken;
import com.cosog.model.DataMapping;
import com.cosog.model.Org;
import com.cosog.model.User;
import com.cosog.model.calculate.ResultStatusData;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.drive.AcqAddrData;
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
import com.cosog.utils.DataModelMap;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.Page;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.RedisUtil;
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
	
	@RequestMapping("/getRealTimeMonitoringFESDiagramResultStatData")
	public String getRealTimeMonitoringFESDiagramResultStatData() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		commStatusStatValue = ParamUtils.getParameter(request, "commStatusStatValue");
		deviceTypeStatValue = ParamUtils.getParameter(request, "deviceTypeStatValue");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserOrgIds();
			}
		}
		json = realTimeMonitoringService.getRealTimeMonitoringFESDiagramResultStatData(orgId,deviceType,commStatusStatValue,deviceTypeStatValue,language);
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
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserOrgIds();
			}
		}
		json = realTimeMonitoringService.getRealTimeMonitoringCommStatusStatData(orgId,deviceType,deviceTypeStatValue,language);
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
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserOrgIds();
			}
		}
		json = realTimeMonitoringService.getRealTimeMonitoringRunStatusStatData(orgId,deviceType,deviceTypeStatValue,language);
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
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserOrgIds();
			}
		}
		json = realTimeMonitoringService.getRealTimeMonitoringDeviceTypeStatData(orgId,deviceType,commStatusStatValue,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
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
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
		FESdiagramResultStatValue = ParamUtils.getParameter(request, "FESdiagramResultStatValue");
		commStatusStatValue = ParamUtils.getParameter(request, "commStatusStatValue");
		runStatusStatValue = ParamUtils.getParameter(request, "runStatusStatValue");
		deviceTypeStatValue = ParamUtils.getParameter(request, "deviceTypeStatValue");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserOrgIds();
			}
		}

		json = realTimeMonitoringService.getDeviceOverview(orgId,deviceName,deviceType,dictDeviceType,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,pager,user);
	
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceRealTimeOverviewDataPage")
	public String getDeviceRealTimeOverviewDataPage() throws Exception {
		String json = "";
		int dataPage=1;
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		deviceName = ParamUtils.getParameter(request, "deviceName");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		FESdiagramResultStatValue = ParamUtils.getParameter(request, "FESdiagramResultStatValue");
		commStatusStatValue = ParamUtils.getParameter(request, "commStatusStatValue");
		runStatusStatValue = ParamUtils.getParameter(request, "runStatusStatValue");
		deviceTypeStatValue = ParamUtils.getParameter(request, "deviceTypeStatValue");
		limit = ParamUtils.getParameter(request, "limit");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserOrgIds();
			}
		}
		dataPage = realTimeMonitoringService.getDeviceRealTimeOverviewDataPage(orgId,deviceId,deviceName,deviceType,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,limit,language);
		json="{\"success\":true,\"dataPage\":"+dataPage+"}";
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
		boolean bool=false;
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
		FESdiagramResultStatValue = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "FESdiagramResultStatValue"),"utf-8");
		commStatusStatValue = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "commStatusStatValue"),"utf-8");
		runStatusStatValue = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "runStatusStatValue"),"utf-8");
		deviceTypeStatValue = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceTypeStatValue"),"utf-8");
		
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if(session!=null){
			session.removeAttribute(key);
			session.setAttribute(key, 0);
		}
		
		DataDictionary ddic = null;
		String ddicCode="realTimeMonitoring_Overview";
		ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicCode,dictDeviceType,language);
		heads=StringUtils.join(ddic.getHeaders(), ",");
		fields=StringUtils.join(ddic.getFields(), ",");
		this.pager = new Page("pagerForm", request);
		
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserOrgIds();
			}
		}

		bool = realTimeMonitoringService.exportDeviceOverviewData(user,response,fileName,title, heads, fields,orgId,deviceName,deviceType,dictDeviceType,FESdiagramResultStatValue,commStatusStatValue,runStatusStatValue,deviceTypeStatValue,pager,user!=null?user.getUserNo():0,language);
	
		if(session!=null){
			session.setAttribute(key, 1);
		}
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
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		this.pager = new Page("pagerForm", request);
		User user = (User) session.getAttribute("userLogin");
		if(user!=null){
			json = realTimeMonitoringService.getDeviceRealTimeMonitoringData(deviceId,deviceName,deviceType,calculateType,user.getUserNo(),user.getLanguageName());
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
	
	@RequestMapping("/exportDeviceRealTimeMonitoringData")
	public String exportDeviceRealTimeMonitoringData() throws Exception {
		boolean bool=false;
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		if(session!=null){
			session.removeAttribute(key);
			session.setAttribute(key, 0);
			user = (User) session.getAttribute("userLogin");
		}
		bool = realTimeMonitoringService.exportDeviceRealTimeMonitoringData(user,response,deviceId,deviceName,calculateType);
		if(session!=null){
			session.setAttribute(key, 1);
		}
		return null;
	}
	
	@RequestMapping("/getDeviceControlData")
	public String getDeviceControlData() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String deviceId=ParamUtils.getParameter(request, "deviceId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		json = realTimeMonitoringService.getDeviceControlData(deviceId,deviceName,deviceType,user);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceAddInfoData")
	public String getDeviceAddInfoData() throws Exception {
		String json = "";
		String deviceId=ParamUtils.getParameter(request, "deviceId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		json = realTimeMonitoringService.getDeviceAddInfoData(deviceId,deviceName,deviceType,calculateType,user.getUserNo(),language);
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
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		this.pager = new Page("pagerForm", request);
		if(user!=null){
			json = realTimeMonitoringService.getRealTimeMonitoringCurveDataFromMemory(deviceId,deviceName,deviceType,calculateType,user.getUserNo(),user.getLanguageName());
		}
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	public int DeviceControlOperation_Mdubus(String protocolCode,String deviceId,String deviceName,String tcpType,String ID,String ipPort,String Slave,String itemCode,String controlValue,String bitIndex){
		int result=-1;
		try {
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String url=Config.getInstance().configFile.getAd().getRw().getWriteAddr();
			String readUrl=Config.getInstance().configFile.getAd().getRw().getReadAddr();
			
			ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByCode(protocolCode);
			int addr=-99;
			String dataType="";
			String title="";
			float ratio=1;
			int quantity=1;
			int resolutionMode=2;
			String IFDataType="";
			String storeDataType="";
			ModbusProtocolConfig.Items item=null;
			for(int i=0;i<protocol.getItems().size();i++){
				String col="";
				if(loadProtocolMappingColumnByTitleMap.containsKey(protocol.getItems().get(i).getTitle())){
					col=loadProtocolMappingColumnByTitleMap.get(protocol.getItems().get(i).getTitle()).getMappingColumn();
				}
				if(itemCode.equalsIgnoreCase(col)){
					item=protocol.getItems().get(i);
					addr=protocol.getItems().get(i).getAddr();
					dataType=protocol.getItems().get(i).getIFDataType();
					title=protocol.getItems().get(i).getTitle();
					ratio=protocol.getItems().get(i).getRatio();
					quantity=protocol.getItems().get(i).getQuantity();
					resolutionMode=protocol.getItems().get(i).getResolutionMode();
					IFDataType=protocol.getItems().get(i).getIFDataType();
					storeDataType=protocol.getItems().get(i).getStoreDataType();
					break;
				}
			}
			String IDOrIPPortKey="ID";
			String IDOrIPPort=ID;
			if("TCPServer".equalsIgnoreCase(tcpType.replaceAll(" ", ""))){
				IDOrIPPortKey="IPPort";
				IDOrIPPort=ipPort;
				url=Config.getInstance().configFile.getAd().getRw().getWriteAddr_ipPort();
				readUrl=Config.getInstance().configFile.getAd().getRw().getReadAddr_ipPort();
			}
			String writeValue="";
			
			if(dataType.equalsIgnoreCase("bcd") || dataType.equalsIgnoreCase("string") ){
				if(quantity==1){
					writeValue="\""+controlValue+"\"";
				}else if(quantity>1){
					String [] writeValueArr=controlValue.split(",");
					for(int i=0;i<writeValueArr.length;i++){
						writeValue+="\""+writeValueArr[i]+"\"";
						if(i!=writeValueArr.length-1){
							writeValue+=",";
						}
					}
				}
			}else{
				if(quantity==1){
					writeValue=StringManagerUtils.objectToString(controlValue, dataType)+"";
				}else if(quantity>1){
					String [] writeValueArr=controlValue.split(",");
					for(int i=0;i<writeValueArr.length;i++){
						writeValue+=StringManagerUtils.objectToString(writeValueArr[i], dataType)+"";
						if(i!=writeValueArr.length-1){
							writeValue+=",";
						}
					}
				}
			}
			
			if(StringManagerUtils.isNotNull(title) && addr!=-99 && item!=null){
				String ctrlJson="{"
						+ "\""+IDOrIPPortKey+"\":\""+IDOrIPPort+"\","
						+ "\"Slave\":"+Slave+","
						+ "\"Addr\":"+addr+","
						+ "\"Value\":["+writeValue+"]"
						+ "}";
				String readJson="{"
						+ "\""+IDOrIPPortKey+"\":\""+IDOrIPPort+"\","
						+ "\"Slave\":"+Slave+","
						+ "\"Addr\":"+addr+""
						+ "}";
				
				//开关量控制
				String responseStr="";
				if(resolutionMode==0){
					//先读取寄存器值
					responseStr=StringManagerUtils.sendPostMethod(url, readJson,"utf-8",0,0);
					
//					responseStr="{\"ResultStatus\":1,\"Value\":[5]}";
					
					System.out.println("发送读地址命令:"+readJson+";返回数据:"+responseStr);
					type = new TypeToken<AcqAddrData>() {}.getType();
					AcqAddrData acqAddrData=gson.fromJson(responseStr, type);
					int readResult=acqAddrData.getResultStatus();
					if(readResult==1 
							&& acqAddrData.getValue()!=null 
							&& (acqAddrData.getValue().size()==1)   
							){//读取成功后，再写地址
						String readValueStr=acqAddrData.getValue().get(0).toString();
						if(StringManagerUtils.isNotNull(readValueStr) && StringManagerUtils.isInteger(readValueStr) && StringManagerUtils.isInteger(controlValue)){
							int readValue=StringManagerUtils.stringToInteger(readValueStr);
							int w=StringManagerUtils.editIntDataBit(readValue,StringManagerUtils.stringToInteger(bitIndex),(byte)StringManagerUtils.stringToInteger(controlValue));
							ctrlJson="{"
									+ "\""+IDOrIPPortKey+"\":\""+IDOrIPPort+"\","
									+ "\"Slave\":"+Slave+","
									+ "\"Addr\":"+addr+","
									+ "\"Value\":["+w+"]"
									+ "}";
							
							responseStr=StringManagerUtils.sendPostMethod(url, ctrlJson,"utf-8",0,0);
							System.out.println("发送控制命令:"+ctrlJson+";返回数据:"+responseStr);
							if(StringManagerUtils.isNotNull(responseStr)){
								type = new TypeToken<ResultStatusData>() {}.getType();
								ResultStatusData resultStatusData=gson.fromJson(responseStr, type);
								result=resultStatusData.getResultStatus();
							}
							realTimeMonitoringService.saveDeviceControlLog(deviceId,deviceName,deviceType,title,StringManagerUtils.objectToString(controlValue, dataType),user);
						}
					}
				}else{
					responseStr=StringManagerUtils.sendPostMethod(url, ctrlJson,"utf-8",0,0);
					System.out.println("发送控制命令:"+ctrlJson+";返回数据:"+responseStr);
					if(StringManagerUtils.isNotNull(responseStr)){
						type = new TypeToken<ResultStatusData>() {}.getType();
						ResultStatusData resultStatusData=gson.fromJson(responseStr, type);
						result=resultStatusData.getResultStatus();
					}
					realTimeMonitoringService.saveDeviceControlLog(deviceId,deviceName,deviceType,title,StringManagerUtils.objectToString(controlValue, dataType),user);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("/deviceControlOperation")
	public String DeviceControlOperation() throws Exception {
		String deviceName = request.getParameter("deviceName");
		String deviceId = request.getParameter("deviceId");
		String deviceType = request.getParameter("deviceType");
		String password = request.getParameter("password");
		String controlType = request.getParameter("controlType");
		String controlValue = request.getParameter("controlValue");
		String bitIndex = request.getParameter("bitIndex");
		
		String jsonLogin = "";
		String clientIP=StringManagerUtils.getIpAddr(request);
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		
		String deviceTableName="tbl_device";
		
		
		// 用户不存在
		if (null != userInfo) {
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo.getLanguageName());
			String getUpwd = userInfo.getUserPwd();
//			String getOld = UnixPwdCrypt.crypt("dogVSgod", password);
			String getOld = StringManagerUtils.stringToMD5(password);
			if (getOld.equals(getUpwd)&&StringManagerUtils.isNumber(controlValue)) {
				String sql="select t3.protocol,t.tcpType, t.signinid,t.ipport,to_number(t.slave),t.deviceType "
						+ " from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 "
						+ " where t.instancecode=t2.code and t2.unitid=t3.id"
						+ " and t.id="+deviceId;
				List<?> list = this.service.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[]) list.get(0);
					String protocolCode=obj[0]+"";
					String tcpType=obj[1]+"";
					String signinid=obj[2]+"";
					String ipPort=obj[3]+"";
					String slave=obj[4]+"";
					String realDeviceType=obj[5]+"";
					if(StringManagerUtils.isNotNull(protocolCode) && StringManagerUtils.isNotNull(tcpType) && StringManagerUtils.isNotNull(signinid)){
						if(StringManagerUtils.isNotNull(slave)){
							int reslut=DeviceControlOperation_Mdubus(protocolCode,deviceId,deviceName,tcpType,signinid,ipPort,slave,controlType,controlValue,bitIndex);
							if(reslut==1){
								jsonLogin = "{success:true,flag:true,error:true,msg:'<font color=blue>"+languageResourceMap.get("commandExecutedSuccessfully")+"</font>'}";
							}else if(reslut==0){
								jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>"+languageResourceMap.get("commandExecutedFailure")+"</font>'}";
							}else if(reslut==-1){
								jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>"+languageResourceMap.get("commandSendFailure")+"</font>'}";
							}else{
								jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>"+languageResourceMap.get("commandSendException")+"</font>'}";
							}
						}
					}else{
						jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>"+languageResourceMap.get("protocolConfigurationError")+"</font>'}";
					}
				}else{
					jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>"+languageResourceMap.get("deviceNotExist")+"</font>'}";
				}
			}else if(getOld.equals(getUpwd) && !StringManagerUtils.isNumber(controlValue)){
				jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>"+languageResourceMap.get("inputDataError")+"</font>'}";
			} else {
				jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>"+languageResourceMap.get("inputPasswordError")+"</font>'}";
			}

		} else {
			jsonLogin = "{success:true,flag:false}";
		}
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(jsonLogin);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/deviceControlOperationWhitoutPass")
	public String deviceControlOperationWhitoutPass() throws Exception {
		String deviceId = request.getParameter("deviceId");
		String deviceName = request.getParameter("deviceName");
		String controlType = request.getParameter("controlType");
		String controlValue = request.getParameter("controlValue");
		String bitIndex = request.getParameter("bitIndex");
		int quantity = StringManagerUtils.stringToInteger(request.getParameter("quantity"));
		
		String jsonLogin = "";
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		
		String deviceTableName="tbl_device";
		// 用户不存在
		if (null != userInfo) {
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo.getLanguageName());
			if (StringManagerUtils.isNotNull(controlValue) && 
					( (quantity==1 && StringManagerUtils.isNumber(controlValue)) || (quantity>1 && StringManagerUtils.isNumberArr(controlValue,",")) )
					) {
				String sql="select t3.protocol,t.tcpType, t.signinid,t.ipport,to_number(t.slave),t.deviceType from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 "
						+ " where t.instancecode=t2.code and t2.unitid=t3.id"
						+ " and t.id="+deviceId;
				List<?> list = this.service.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[]) list.get(0);
					String protocol=obj[0]+"";
					String tcpType=obj[1]+"";
					String signinid=obj[2]+"";
					String ipPort=obj[3]+"";
					String slave=obj[4]+"";
					String realDeviceType=obj[5]+"";
					if(StringManagerUtils.isNotNull(protocol) && StringManagerUtils.isNotNull(tcpType) && StringManagerUtils.isNotNull(signinid)){
						if(StringManagerUtils.isNotNull(slave)){
							int reslut=DeviceControlOperation_Mdubus(protocol,deviceId,deviceName,tcpType,signinid,ipPort,slave,controlType,controlValue,bitIndex);
							if(reslut==1){
								jsonLogin = "{success:true,flag:true,error:true,msg:'<font color=blue>"+languageResourceMap.get("commandExecutedSuccessfully")+"</font>'}";
							}else if(reslut==0){
								jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>"+languageResourceMap.get("commandExecutedFailure")+"</font>'}";
							}else if(reslut==-1){
								jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>"+languageResourceMap.get("commandSendFailure")+"</font>'}";
							}else{
								jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>"+languageResourceMap.get("commandSendException")+"</font>'}";
							}
						}
					}else{
						jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>"+languageResourceMap.get("protocolConfigurationError")+"</font>'}";
					}
				}else{
					jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>"+languageResourceMap.get("deviceNotExist")+"</font>'}";
				}
			}else {
				jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>"+languageResourceMap.get("inputDataError")+"</font>'}";
			}

		} else {
			jsonLogin = "{success:true,flag:false}";
		}
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(jsonLogin);
		pw.flush();
		pw.close();
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
			String tableName="tbl_resourcemonitoring";
			if("tableSpaceSize".equalsIgnoreCase(itemCode)){
				tableName="tbl_dbmonitoring";
			}
			
			String sql = " select to_char(max(t.acqTime),'yyyy-mm-dd hh24:mi:ss') from "+tableName+" t ";
			List list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			}
		}
		
		if(!StringManagerUtils.isNotNull(startDate)){
			if("tableSpaceSize".equalsIgnoreCase(itemCode)){
				startDate=StringManagerUtils.addDay(StringManagerUtils.stringToDate(endDate,"yyyy-MM-dd"),-10)+" 00:00:00";
			}else{
				startDate=StringManagerUtils.addHour(StringManagerUtils.stringToDate(endDate,"yyyy-MM-dd HH:mm:ss"),-1);
			}
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
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String startDate = ParamUtils.getParameter(request, "startDate");
		String endDate = ParamUtils.getParameter(request, "endDate");
		String type = ParamUtils.getParameter(request, "type");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		if("1".equals(type)){
			json = this.realTimeMonitoringService.querySingleDetailsWellBoreChartsData(id,deviceName,language);
		}else if("2".equals(type)){
			json = this.realTimeMonitoringService.querySingleDetailsSurfaceData(id,deviceName,language);
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
	
	@RequestMapping("/getUIKitAccessToken")
	public String getUIKitAccessToken()throws Exception{
		String videoKeyId = ParamUtils.getParameter(request, "videoKeyId");
		String json=this.realTimeMonitoringService.getUIKitAccessToken(videoKeyId);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getCalculateTypeDeviceCount")
	public String getCalculateTypeDeviceCount()throws Exception{
		String orgId = ParamUtils.getParameter(request, "orgId");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		String json=this.realTimeMonitoringService.getCalculateTypeDeviceCount(orgId,deviceType,calculateType);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceAddInfoAndControlInfo")
	public String getDeviceAddInfoAndControlInfo() throws Exception {
		String json = "";
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		json = realTimeMonitoringService.getDeviceAddInfoAndControlInfo(deviceId,deviceType);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceControlValueList")
	public String getDeviceControlValueList() throws Exception {
		String deviceId = request.getParameter("deviceId");
		String deviceName = request.getParameter("deviceName");
		String deviceType = request.getParameter("deviceType");
		String controlType = request.getParameter("controlType");
		String controlValue = request.getParameter("controlValue");
		String jsonLogin = "";
		String clientIP=StringManagerUtils.getIpAddr(request);
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
		
		String deviceTableName="tbl_device";
		
		int quantity=0;
		// 用户不存在
		if (null != userInfo) {
			String sql="select t3.protocol,t.tcpType, t.signinid,t.ipport,to_number(t.slave),t.deviceType "
					+ " from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 "
					+ " where t.instancecode=t2.code and t2.unitid=t3.id"
					+ " and t.id="+deviceId;
			List<?> list = this.service.findCallSql(sql);
			if(list.size()>0){
				Object[] obj=(Object[]) list.get(0);
				String protocolCode=obj[0]+"";
				String tcpType=obj[1]+"";
				String signinid=obj[2]+"";
				String ipPort=obj[3]+"";
				String slave=obj[4]+"";
				String realDeviceType=obj[5]+"";
				if(StringManagerUtils.isNotNull(protocolCode) && StringManagerUtils.isNotNull(tcpType) && StringManagerUtils.isNotNull(signinid)){
					ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByCode(protocolCode);
					if(protocol!=null){
						for(int i=0;i<protocol.getItems().size();i++){
							String col="";
							if(loadProtocolMappingColumnByTitleMap.containsKey(protocol.getItems().get(i).getTitle())){
								col=loadProtocolMappingColumnByTitleMap.get(protocol.getItems().get(i).getTitle()).getMappingColumn();
							}
							if(controlType.equalsIgnoreCase(col)){
								quantity=protocol.getItems().get(i).getQuantity();
								break;
							}
						}
					}
				}
			}
		}
		StringBuffer result_json = new StringBuffer();
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalCount\":"+quantity+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<quantity;i++){
			result_json.append("{\"index\":"+(i+1)+",");
			result_json.append("\"value\":\"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(result_json.toString());
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceEnumValueControlData")
	public String getDeviceEnumValueControlData() throws Exception {
		String deviceId = request.getParameter("deviceId");
		String deviceName = request.getParameter("deviceName");
		String deviceType = request.getParameter("deviceType");
		String controlType = request.getParameter("controlType");
		String controlValue = request.getParameter("controlValue");
		String jsonLogin = "";
		String clientIP=StringManagerUtils.getIpAddr(request);
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
		
		String deviceTableName="tbl_device";
		
		List<ModbusProtocolConfig.ItemsMeaning> itemsMeaningList=new ArrayList<>();
		
		// 用户不存在
		if (null != userInfo) {
			String sql="select t3.protocol,t.tcpType, t.signinid,t.ipport,to_number(t.slave),t.deviceType "
					+ " from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 "
					+ " where t.instancecode=t2.code and t2.unitid=t3.id"
					+ " and t.id="+deviceId;
			List<?> list = this.service.findCallSql(sql);
			if(list.size()>0){
				Object[] obj=(Object[]) list.get(0);
				String protocolCode=obj[0]+"";
				String tcpType=obj[1]+"";
				String signinid=obj[2]+"";
				String ipPort=obj[3]+"";
				String slave=obj[4]+"";
				String realDeviceType=obj[5]+"";
				if(StringManagerUtils.isNotNull(protocolCode) && StringManagerUtils.isNotNull(tcpType) && StringManagerUtils.isNotNull(signinid)){
					ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByCode(protocolCode);
					if(protocol!=null){
						for(int i=0;i<protocol.getItems().size();i++){
							String col="";
							if(loadProtocolMappingColumnByTitleMap.containsKey(protocol.getItems().get(i).getTitle())){
								col=loadProtocolMappingColumnByTitleMap.get(protocol.getItems().get(i).getTitle()).getMappingColumn();
							}
							if(controlType.equalsIgnoreCase(col)){
								itemsMeaningList=protocol.getItems().get(i).getMeaning();
								break;
							}
						}
					}
				}
			}
		}
		StringBuffer result_json = new StringBuffer();
		result_json.append("{ \"success\":true,");
		result_json.append("\"totalCount\":"+itemsMeaningList.size()+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<itemsMeaningList.size();i++){
			result_json.append("{\"index\":"+(i+1)+",");
			result_json.append("\"meaning\":\""+itemsMeaningList.get(i).getMeaning()+"\",");
			result_json.append("\"value\":\""+itemsMeaningList.get(i).getValue()+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(result_json.toString());
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
