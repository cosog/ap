package com.cosog.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cosog.controller.base.BaseController;
import com.cosog.model.AcquisitionUnit;
import com.cosog.model.PumpingModelInformation;
import com.cosog.model.MasterAndAuxiliaryDevice;
import com.cosog.model.Org;
import com.cosog.model.PCPDeviceAddInfo;
import com.cosog.model.PcpDeviceInformation;
import com.cosog.model.RPCDeviceAddInfo;
import com.cosog.model.RpcDeviceInformation;
import com.cosog.model.SmsDeviceInformation;
import com.cosog.model.User;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.AppRunStatusProbeResonanceData;
import com.cosog.model.calculate.PCPProductionData;
import com.cosog.model.calculate.RPCProductionData;
import com.cosog.model.drive.RPCInteractionResponseData;
import com.cosog.model.drive.WaterCutRawData;
import com.cosog.model.gridmodel.AuxiliaryDeviceConfig;
import com.cosog.model.gridmodel.PumpingModelHandsontableChangedData;
import com.cosog.model.gridmodel.WellHandsontableChangedData;
import com.cosog.service.back.WellInformationManagerService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.task.ResourceMonitoringTask;
import com.cosog.thread.calculate.DataSynchronizationThread;
import com.cosog.thread.calculate.ThreadPool;
import com.cosog.utils.AdInitThreadPoolConfig;
import com.cosog.utils.CalculateUtils;
import com.cosog.utils.Config;
import com.cosog.utils.Constants;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.MarkDown2HtmlWrapper;
import com.cosog.utils.MarkdownEntity;
import com.cosog.utils.Page;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.excel.ExcelUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/wellInformationManagerController")
@Scope("prototype")
public class WellInformationManagerController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(WellInformationManagerController.class);
	@Autowired
	private WellInformationManagerService<?> wellInformationManagerService;
	@Autowired
	private WellInformationManagerService<RpcDeviceInformation> rpcDeviceManagerService;
	@Autowired
	private WellInformationManagerService<PcpDeviceInformation> pcpDeviceManagerService;
	@Autowired
	private WellInformationManagerService<SmsDeviceInformation> smsDeviceManagerService;
	@Autowired
	private WellInformationManagerService<PumpingModelInformation> pumpingModelManagerService;
	@Autowired
	private CommonDataService service;
	private String limit;
	private String msg = "";
	private String wellInformationName;
	private String liftingType;
	private String deviceType;
	private String orgCode;
	private String resCode;
	private String page;
	private String orgId;
	private int totals;
	
	//添加绑定前缀 
	@InitBinder("rpcDeviceInformation")
	public void initBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("rpcDeviceInformation.");
	}
	
	@InitBinder("pcpDeviceInformation")
	public void initBinder2(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("pcpDeviceInformation.");
	}
	
	@InitBinder("pumpingModelInformation")
	public void initBinder3(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("pumpingModelInformation.");
	}
	
	@InitBinder("smsDeviceInformation")
	public void initBinder4(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("smsDeviceInformation.");
	}
	
	/**
	 * <p>
	 * 描述：实现采出井下拉菜单多级联动方法
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadWellComboxList")
	public String loadWellComboxList() throws Exception {
		this.pager=new Page("pageForm",request);
		String wellName = ParamUtils.getParameter(request, "wellName");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		orgId=ParamUtils.getParameter(request, "orgId");
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String json = this.wellInformationManagerService.loadWellComboxList(pager,orgId, wellName,deviceType);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/loadRPCDeviceComboxList")
	public String loadRPCDeviceComboxList() throws Exception {
		this.pager=new Page("pageForm",request);
		deviceType= ParamUtils.getParameter(request, "deviceType");
		String wellName = ParamUtils.getParameter(request, "wellName");
		orgId=ParamUtils.getParameter(request, "orgId");
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String json = this.wellInformationManagerService.loadRPCDeviceComboxList(pager,orgId, wellName,deviceType);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceOrgChangeDeviceList")
	public String getDeviceOrgChangeDeviceList() throws Exception {
		this.pager=new Page("pageForm",request);
		String wellName = ParamUtils.getParameter(request, "wellName");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		orgId=ParamUtils.getParameter(request, "orgId");
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String json = this.wellInformationManagerService.getDeviceOrgChangeDeviceList(pager,orgId, wellName,deviceType);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/changeDeviceOrg")
	public String changeDeviceOrg() throws Exception {
		this.pager=new Page("pageForm",request);
		String selectedDeviceId = ParamUtils.getParameter(request, "selectedDeviceId");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		String selectedOrgId=ParamUtils.getParameter(request, "selectedOrgId");
		String selectedOrgName=ParamUtils.getParameter(request, "selectedOrgName");
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		this.wellInformationManagerService.changeDeviceOrg(selectedDeviceId,selectedOrgId,selectedOrgName,deviceType);
		String json = "{\"success\":true}";
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getAcqInstanceCombList")
	public String getAcqInstanceCombList() throws IOException {
		deviceType= ParamUtils.getParameter(request, "deviceType");
		String json=wellInformationManagerService.getAcqInstanceCombList(deviceType);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDisplayInstanceCombList")
	public String getDisplayInstanceCombList() throws IOException {
		deviceType= ParamUtils.getParameter(request, "deviceType");
		String json=wellInformationManagerService.getDisplayInstanceCombList(deviceType);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getAlarmInstanceCombList")
	public String getAlarmInstanceCombList() throws IOException {
		deviceType= ParamUtils.getParameter(request, "deviceType");
		String json=wellInformationManagerService.getAlarmInstanceCombList(deviceType);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getSMSInstanceCombList")
	public String getSMSInstanceCombList() throws IOException {
		String json=wellInformationManagerService.getSMSInstanceCombList();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/loadDeviceTypeComboxList")
	public String loadDeviceTypeComboxList() throws Exception {
		this.pager=new Page("pageForm",request);
		String json = this.wellInformationManagerService.loadDeviceTypeComboxList();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/loadDataDictionaryComboxList")
	public String loadDataDictionaryComboxList() throws Exception {
		this.pager=new Page("pageForm",request);
		String itemCode = ParamUtils.getParameter(request, "itemCode");
		String json = this.wellInformationManagerService.loadDataDictionaryComboxList(itemCode);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	@RequestMapping("/doWellInformationShow")
	public String doWellInformationShow() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount"));
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "20" : limit);
		int offset = (intPage - 1) * pageSize + 1;
		wellInformationName = ParamUtils.getParameter(request, "wellInformationName");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		orgId=ParamUtils.getParameter(request, "orgId");
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		
		orgCode = ParamUtils.getParameter(request, "orgCode");
		resCode = ParamUtils.getParameter(request, "resCode");
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("wellInformationName", wellInformationName);
		map.put("deviceType", deviceType);
		map.put("orgCode", orgCode);
		map.put("resCode", resCode);
		map.put("orgId", orgId);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);
		String json="";
		if(StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200){
			json = this.wellInformationManagerService.getRPCDeviceInfoList(map, pager,recordCount);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=200&&StringManagerUtils.stringToInteger(deviceType)<300){
			json = this.wellInformationManagerService.getPCPDeviceInfoList(map, pager,recordCount);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=300){
			json = this.wellInformationManagerService.getSMSDeviceInfoList(map, pager,recordCount);
		}
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getBatchAddDeviceTableInfo")
	public String getBatchAddDeviceTableInfo() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount"));
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "20" : limit);
		int offset = (intPage - 1) * pageSize + 1;
		deviceType= ParamUtils.getParameter(request, "deviceType");
		orgId=ParamUtils.getParameter(request, "orgId");
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("wellInformationName", wellInformationName);
		map.put("deviceType", deviceType);
		map.put("orgId", orgId);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);
		String json = this.wellInformationManagerService.getBatchAddDeviceTableInfo(deviceType,recordCount);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceInformationData")
	public String getDeviceInformationData() throws IOException {
		String recordId = ParamUtils.getParameter(request, "recordId");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		String json="";
		if(StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200){
			json = this.wellInformationManagerService.getRPCDeviceInformationData(recordId);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=200&&StringManagerUtils.stringToInteger(deviceType)<300){
//			json = this.wellInformationManagerService.getPipeDeviceInfoList(map, pager,recordCount);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=300){
//			json = this.wellInformationManagerService.getSMSDeviceInfoList(map, pager,recordCount);
		}
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/doPumpingModelShow")
	public String doPumpingModelShow() throws IOException, SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount"));
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "20" : limit);
		int offset = (intPage - 1) * pageSize + 1;
		deviceType= ParamUtils.getParameter(request, "deviceType");
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("deviceType", deviceType);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);
		String json = this.wellInformationManagerService.doPumpingModelShow(map, pager,deviceType,recordCount);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getPumpingPRTFData")
	public String getPumpingPRTFData() throws IOException, SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		String recordId= ParamUtils.getParameter(request, "recordId");
		String stroke= ParamUtils.getParameter(request, "stroke");
		
		String json = this.wellInformationManagerService.getPumpingPRTFData(recordId,stroke);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getBatchAddPumpingModelTableInfo")
	public String getBatchAddPumpingModelTableInfo() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount"));
		this.pager = new Page("pagerForm", request);
		String json = this.wellInformationManagerService.getBatchAddPumpingModelTableInfo(recordCount);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportPumpingModelData")
	public String exportPumpingModelData() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount"));
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "20" : limit);
		int offset = (intPage - 1) * pageSize + 1;
		deviceType= ParamUtils.getParameter(request, "deviceType");
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("deviceType", deviceType);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);
		boolean bool = this.wellInformationManagerService.exportPumpingModelData(response,fileName,title, heads, fields,map, pager,deviceType,recordCount);
		return null;
	}
	
	@RequestMapping("/getRPCPumpingModelList")
	public String getRPCPumpingModelList() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String deviceId= ParamUtils.getParameter(request, "deviceId");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		String json = this.wellInformationManagerService.getRPCPumpingModelList(deviceId,deviceType);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceProductionDataInfo")
	public String getDeviceProductionDataInfo() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String deviceId= ParamUtils.getParameter(request, "deviceId");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		String json = this.wellInformationManagerService.getDeviceProductionDataInfo(deviceId,deviceType);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceVideoInfo")
	public String getDeviceVideoInfo() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String deviceId= ParamUtils.getParameter(request, "deviceId");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		String json = this.wellInformationManagerService.getDeviceVideoInfo(deviceId,deviceType);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDevicePumpingInfo")
	public String getDevicePumpingInfo() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String deviceId= ParamUtils.getParameter(request, "deviceId");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		String json = this.wellInformationManagerService.getDevicePumpingInfo(deviceId,deviceType);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getAcquisitionUnitList")
	public String getAcquisitionUnitList() throws Exception{
		String protocol = ParamUtils.getParameter(request, "protocol");
		String json = this.wellInformationManagerService.getAcquisitionUnitList(protocol);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportWellInformationData")
	public String exportWellInformationData() throws Exception {
		boolean bool=false;
		Map<String, Object> map = new HashMap<String, Object>();
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount"));
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "20" : limit);
		int offset = (intPage - 1) * pageSize + 1;
		//wellInformationName = new String(wellInformationName.getBytes("iso-8859-1"), "utf-8");
//		String orgId=this.findCurrentUserOrgIdInfo("");
		wellInformationName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "wellInformationName"),"utf-8");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		orgId=ParamUtils.getParameter(request, "orgId");
		User user=null;
		if (!StringManagerUtils.isNotNull(orgId)) {
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		
		orgCode = ParamUtils.getParameter(request, "orgCode");
		resCode = ParamUtils.getParameter(request, "resCode");
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("wellInformationName", wellInformationName);
		map.put("deviceType", deviceType);
		map.put("orgCode", orgCode);
		map.put("resCode", resCode);
		map.put("orgId", orgId);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);// 新疆分页Page 工具类
		if(StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200){
			bool = this.wellInformationManagerService.exportRPCDeviceInfoData(response,fileName,title, heads, fields,map, pager,recordCount);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=200&&StringManagerUtils.stringToInteger(deviceType)<300){
			bool = this.wellInformationManagerService.exportPipeDeviceInfoData(response,fileName,title, heads, fields,map, pager,recordCount);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=300){
			bool = this.wellInformationManagerService.exportSMSDeviceInfoData(response,fileName,title, heads, fields,map, pager,recordCount);
		}
		return null;
	}

	@RequestMapping("/loadWellOrgInfo")
	public String loadWellOrgInfo() throws Exception {
		List<?> list = this.wellInformationManagerService.loadWellOrgInfo();
		log.debug("loadWellOrgInfo list==" + list.size());
		Org op = null;
		List<Org> olist = new ArrayList<Org>();
		for (int i = 0; i < list.size(); i++) {
			// 使用对象数组
			Object[] objArray = (Object[]) list.get(i);
			// 最后使用forEach迭代obj对象
			op = new Org();
			op.setOrgCode(objArray[0].toString());
			op.setOrgName(objArray[1].toString());
			olist.add(op);
		}
		Gson g = new Gson();
		String json = g.toJson(olist);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	@RequestMapping("/showWellTypeTree")
	public String showWellTypeTree() throws Exception {
		String json = this.wellInformationManagerService.showWellTypeTree();
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	
	/**
	 * <p>
	 * 描述：设备基本信息Handsontable表格编辑数据保存
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@RequestMapping("/saveWellHandsontableData")
	public String saveWellHandsontableData() throws Exception {
		HttpSession session=request.getSession();
		String json ="{success:true}";
		User user = (User) session.getAttribute("userLogin");
		int deviceId = StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "deviceId"));
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll("null", "");
		String pumpingModelId = ParamUtils.getParameter(request, "pumpingModelId");
		String stroke = ParamUtils.getParameter(request, "stroke");
		String balanceInfo = ParamUtils.getParameter(request, "balanceInfo").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String deviceProductionData = ParamUtils.getParameter(request, "deviceProductionData").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String manualInterventionResultName = ParamUtils.getParameter(request, "manualInterventionResultName");
		String orgId = ParamUtils.getParameter(request, "orgId");
		String videoUrl = ParamUtils.getParameter(request, "videoUrl");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		Gson gson = new Gson();
		String deviceTableName="tbl_rpcdevice";
		java.lang.reflect.Type type = new TypeToken<WellHandsontableChangedData>() {}.getType();
		WellHandsontableChangedData wellHandsontableChangedData=gson.fromJson(data, type);
		if(StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200){
			deviceTableName="tbl_rpcdevice";
			json=this.wellInformationManagerService.saveRPCDeviceData(wellInformationManagerService,wellHandsontableChangedData,orgId,StringManagerUtils.stringToInteger(deviceType),user);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=200&&StringManagerUtils.stringToInteger(deviceType)<300){
			deviceTableName="tbl_pcpdevice";
			json=this.wellInformationManagerService.savePCPDeviceData(wellInformationManagerService,wellHandsontableChangedData,orgId,StringManagerUtils.stringToInteger(deviceType),user);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=300){
			this.wellInformationManagerService.saveSMSDeviceData(wellHandsontableChangedData,orgId,StringManagerUtils.stringToInteger(deviceType),user);
		}
		
		List<String> initWellList=new ArrayList<String>();
		initWellList.add(deviceId+"");
		//处理生产数据
		
		String deviceProductionDataSaveStr=deviceProductionData;
		
		if(StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200){
			type = new TypeToken<RPCProductionData>() {}.getType();
			RPCProductionData rpcProductionData=gson.fromJson(deviceProductionData, type);
			if(rpcProductionData!=null){
				if(rpcProductionData.getProduction()!=null && rpcProductionData.getFluidPVT()!=null){
					float weightWaterCut=CalculateUtils.volumeWaterCutToWeightWaterCut(rpcProductionData.getProduction().getWaterCut(), rpcProductionData.getFluidPVT().getCrudeOilDensity(), rpcProductionData.getFluidPVT().getWaterDensity());
					rpcProductionData.getProduction().setWeightWaterCut(weightWaterCut);
				}
				if(rpcProductionData.getManualIntervention()!=null){
					int manualInterventionResultCode=0;
					Jedis jedis=null;
					try{
						jedis = RedisUtil.jedisPool.getResource();
						if(!jedis.exists("RPCWorkTypeByName".getBytes())){
							MemoryDataManagerTask.loadRPCWorkType();
						}
						if(!"不干预".equalsIgnoreCase(manualInterventionResultName)){
							if(jedis.hexists("RPCWorkTypeByName".getBytes(), (manualInterventionResultName).getBytes())){
								WorkType workType=(WorkType) SerializeObjectUnils.unserizlize(jedis.hget("RPCWorkTypeByName".getBytes(), (manualInterventionResultName).getBytes()));
								manualInterventionResultCode=workType.getResultCode();
							}
						}
					}catch(Exception e){
						String resultNameSql="select t.resultcode from tbl_rpc_worktype t where t.resultname='"+manualInterventionResultName+"'";
						List<?> resultList = this.wellInformationManagerService.findCallSql(resultNameSql);
						if(resultList.size()>0){
							manualInterventionResultCode=StringManagerUtils.stringToInteger(resultList.get(0).toString());
						}
					}finally{
						if(jedis!=null&&jedis.isConnected()){
							jedis.close();
						}
					}
					rpcProductionData.getManualIntervention().setCode(manualInterventionResultCode);
					
					
					if(!"不干预".equalsIgnoreCase(manualInterventionResultName)){
						String sql="select t.resultcode from tbl_rpc_worktype t where t.resultname='"+manualInterventionResultName+"'";
						List<?> resultList = this.wellInformationManagerService.findCallSql(sql);
						if(resultList.size()>0){
							int manualInterventionCode=StringManagerUtils.stringToInteger(resultList.get(0).toString());
							rpcProductionData.getManualIntervention().setCode(manualInterventionCode);
						}
					}else{
						rpcProductionData.getManualIntervention().setCode(0);
					}
				}
				deviceProductionDataSaveStr=gson.toJson(rpcProductionData);
			}
		}else if(StringManagerUtils.stringToInteger(deviceType)>=200&&StringManagerUtils.stringToInteger(deviceType)<300){
			type = new TypeToken<PCPProductionData>() {}.getType();
			PCPProductionData productionData=gson.fromJson(deviceProductionData, type);
			if(productionData!=null){
				if(productionData.getProduction()!=null && productionData.getFluidPVT()!=null){
					float weightWaterCut=CalculateUtils.volumeWaterCutToWeightWaterCut(productionData.getProduction().getWaterCut(), productionData.getFluidPVT().getCrudeOilDensity(), productionData.getFluidPVT().getWaterDensity());
					productionData.getProduction().setWeightWaterCut(weightWaterCut);
				}
				deviceProductionDataSaveStr=gson.toJson(productionData);
			}
		}
		
		
		
		
		this.wellInformationManagerService.saveProductionData(StringManagerUtils.stringToInteger(deviceType),deviceId,deviceProductionDataSaveStr);
		if(StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200){
			//处理抽油机型号
			this.wellInformationManagerService.saveRPCPumpingModel(deviceId,pumpingModelId);
			//处理抽油机详情
			this.wellInformationManagerService.saveRPCPumpingInfo(deviceId,stroke,balanceInfo);
		}
		this.wellInformationManagerService.saveVideiData(StringManagerUtils.stringToInteger(deviceType),deviceId,videoUrl);
		
		
		if(StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200){
			MemoryDataManagerTask.loadRPCDeviceInfo(initWellList,0,"update");
		}else if(StringManagerUtils.stringToInteger(deviceType)>=200&&StringManagerUtils.stringToInteger(deviceType)<300){
			MemoryDataManagerTask.loadPCPDeviceInfo(initWellList,0,"update");
		}
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping("/batchAddDevice")
	public String batchAddDevice() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll("null", "");
		String orgId = ParamUtils.getParameter(request, "orgId");
		String isCheckout = ParamUtils.getParameter(request, "isCheckout");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String json="";
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<WellHandsontableChangedData>() {}.getType();
		WellHandsontableChangedData wellHandsontableChangedData=gson.fromJson(data, type);
		if(StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200){
			json=this.wellInformationManagerService.batchAddRPCDevice(wellInformationManagerService,wellHandsontableChangedData,orgId,StringManagerUtils.stringToInteger(deviceType),isCheckout,user);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=200&&StringManagerUtils.stringToInteger(deviceType)<300){
			json=this.wellInformationManagerService.batchAddPCPDevice(wellInformationManagerService,wellHandsontableChangedData,orgId,StringManagerUtils.stringToInteger(deviceType),isCheckout,user);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=300){
			this.wellInformationManagerService.saveSMSDeviceData(wellHandsontableChangedData,orgId,StringManagerUtils.stringToInteger(deviceType),user);
		}
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportedDeviceFile")
	public String getImportedDeviceFile(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		if(StringManagerUtils.stringToInteger(deviceType)>=200&&StringManagerUtils.stringToInteger(deviceType)<300){
			getImportedPCPDeviceFile(files);
		}else{
			getImportedRPCDeviceFile(files);
		}
		return null;
	}
	
	@SuppressWarnings({"unchecked"})
	@RequestMapping("/getImportedRPCDeviceFile")
	public String getImportedRPCDeviceFile(CommonsMultipartFile[] files) throws Exception {
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		Map<String,String> importedDeviceFileMap=(Map<String, String>) map.get("importedDeviceFileMap");
		if(importedDeviceFileMap!=null){
			map.remove("importedDeviceFileMap",importedDeviceFileMap);
		}
		importedDeviceFileMap=new HashMap<String,String>();
		String json = "";
		String tablecolumns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50},"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"wellName\"},"
				+ "{ \"header\":\"应用场景\",\"dataIndex\":\"applicationScenariosName\"},"
				+ "{ \"header\":\"采控实例\",\"dataIndex\":\"instanceName\"},"
				+ "{ \"header\":\"显示实例\",\"dataIndex\":\"displayInstanceName\"},"
				+ "{ \"header\":\"报警实例\",\"dataIndex\":\"alarmInstanceName\"},"
				+ "{ \"header\":\"注册包ID\",\"dataIndex\":\"signInId\"},"
				+ "{ \"header\":\"设备从地址\",\"dataIndex\":\"slave\"},"
				+ "{ \"header\":\"状态\",\"dataIndex\":\"statusName\"},"
				+ "{ \"header\":\"排序编号\",\"dataIndex\":\"sortNum\"},"
				+ "{ \"header\":\"原油密度(g/cm^3)\",\"dataIndex\":\"crudeOilDensity\"},"
				+ "{ \"header\":\"水密度(g/cm^3)\",\"dataIndex\":\"waterDensity\"},"
				+ "{ \"header\":\"天然气相对密度\",\"dataIndex\":\"naturalGasRelativeDensity\"},"
				+ "{ \"header\":\"饱和压力(MPa)\",\"dataIndex\":\"saturationPressure\"},"
				+ "{ \"header\":\"油层中部深度(m)\",\"dataIndex\":\"reservoirDepth\"},"
				+ "{ \"header\":\"油层中部温度(℃)\",\"dataIndex\":\"reservoirTemperature\"},"
				+ "{ \"header\":\"油压(MPa)\",\"dataIndex\":\"tubingPressure\"},"
				+ "{ \"header\":\"套压(MPa)\",\"dataIndex\":\"casingPressure\"},"
				+ "{ \"header\":\"井口温度(℃)\",\"dataIndex\":\"wellHeadTemperature\"},"
				+ "{ \"header\":\"含水率(%)\",\"dataIndex\":\"waterCut\"},"
				+ "{ \"header\":\"生产气油比(m^3/t)\",\"dataIndex\":\"productionGasOilRatio\"},"
				+ "{ \"header\":\"动液面(m)\",\"dataIndex\":\"producingfluidLevel\"},"
				+ "{ \"header\":\"泵挂(m)\",\"dataIndex\":\"pumpSettingDepth\"},"
				+ "{ \"header\":\"泵类型\",\"dataIndex\":\"pumpType\"},"
				+ "{ \"header\":\"泵筒类型\",\"dataIndex\":\"barrelType\"},"
				+ "{ \"header\":\"泵级别\",\"dataIndex\":\"pumpGrade\"},"
				+ "{ \"header\":\"泵径(mm)\",\"dataIndex\":\"pumpBoreDiameter\"},"
				+ "{ \"header\":\"柱塞长(m)\",\"dataIndex\":\"plungerLength\"},"
				+ "{ \"header\":\"油管内径(mm)\",\"dataIndex\":\"tubingStringInsideDiameter\"},"
				+ "{ \"header\":\"套管内径(mm)\",\"dataIndex\":\"casingStringInsideDiameter\"},"
				+ "{ \"header\":\"一级杆级别\",\"dataIndex\":\"rodGrade1\"},"
				+ "{ \"header\":\"一级杆外径(mm)\",\"dataIndex\":\"rodOutsideDiameter1\"},"
				+ "{ \"header\":\"一级杆内径(mm)\",\"dataIndex\":\"rodInsideDiameter1\"},"
				+ "{ \"header\":\"一级杆长度(m)\",\"dataIndex\":\"rodLength1\"},"
				+ "{ \"header\":\"二级杆级别\",\"dataIndex\":\"rodGrade2\"},"
				+ "{ \"header\":\"二级杆外径(mm)\",\"dataIndex\":\"rodOutsideDiameter2\"},"
				+ "{ \"header\":\"二级杆内径(mm)\",\"dataIndex\":\"rodInsideDiameter2\"},"
				+ "{ \"header\":\"二级杆长度(m)\",\"dataIndex\":\"rodLength2\"},"
				+ "{ \"header\":\"三级杆级别\",\"dataIndex\":\"rodGrade3\"},"
				+ "{ \"header\":\"三级杆外径(mm)\",\"dataIndex\":\"rodOutsideDiameter3\"},"
				+ "{ \"header\":\"三级杆内径(mm)\",\"dataIndex\":\"rodInsideDiameter3\"},"
				+ "{ \"header\":\"三级杆长度(m)\",\"dataIndex\":\"rodLength3\"},"
				+ "{ \"header\":\"四级杆级别\",\"dataIndex\":\"rodGrade4\"},"
				+ "{ \"header\":\"四级杆外径(mm)\",\"dataIndex\":\"rodOutsideDiameter4\"},"
				+ "{ \"header\":\"四级杆内径(mm)\",\"dataIndex\":\"rodInsideDiameter4\"},"
				+ "{ \"header\":\"四级杆长度(m)\",\"dataIndex\":\"rodLength4\"},"
				+ "{ \"header\":\"净毛比(小数)\",\"dataIndex\":\"netGrossRatio\"},"
				+ "{ \"header\":\"抽油机厂家\",\"dataIndex\":\"manufacturer\"},"
				+ "{ \"header\":\"抽油机型号\",\"dataIndex\":\"model\"},"
				+ "{ \"header\":\"铭牌冲程\",\"dataIndex\":\"stroke\"},"
				+ "{ \"header\":\"曲柄旋转方向\",\"dataIndex\":\"crankRotationDirection\"},"
				+ "{ \"header\":\"曲柄偏置角(°)\",\"dataIndex\":\"offsetAngleOfCrank\"},"
				+ "{ \"header\":\"曲柄重心半径(m)\",\"dataIndex\":\"crankGravityRadius\"},"
				+ "{ \"header\":\"单块曲柄重量(kN)\",\"dataIndex\":\"singleCrankWeight\"},"
				+ "{ \"header\":\"单块曲柄销重量(kN)\",\"dataIndex\":\"singleCrankPinWeight\"},"
				+ "{ \"header\":\"结构不平衡重(kN)\",\"dataIndex\":\"structuralUnbalance\"},"
				+ "{ \"header\":\"平衡块重量(kN)\",\"dataIndex\":\"balanceWeight\"},"
				+ "{ \"header\":\"平衡块位置(m)\",\"dataIndex\":\"balancePosition\"}]";
		result_json.append("{ \"success\":true,\"flag\":true,\"columns\":"+tablecolumns+",");
		result_json.append("\"totalCount\":"+files.length+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<files.length;i++){
			if(!files[i].isEmpty()){
				try{
					Workbook rwb=Workbook.getWorkbook(files[i].getInputStream());
					rwb.getNumberOfSheets();
					Sheet oFirstSheet = rwb.getSheet(0);// 使用索引形式获取第一个工作表，也可以使用rwb.getSheet(sheetName);其中sheetName表示的是工作表的名称  
			        int rows = oFirstSheet.getRows();//获取工作表中的总行数  
			        int columns = oFirstSheet.getColumns();//获取工作表中的总列数  
			        for (int j = 3; j < rows; j++) {
			        	try{
			        		String id=oFirstSheet.getCell(0,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String wellName=oFirstSheet.getCell(1,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String applicationScenariosName=oFirstSheet.getCell(2,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String instanceName=oFirstSheet.getCell(3,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String displayInstanceName=oFirstSheet.getCell(4,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String alarmInstanceName=oFirstSheet.getCell(5,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String signInId=oFirstSheet.getCell(6,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String slave=oFirstSheet.getCell(7,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String statusName=oFirstSheet.getCell(8,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String sortNum=oFirstSheet.getCell(9,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String crudeOilDensity=oFirstSheet.getCell(10,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String waterDensity=oFirstSheet.getCell(11,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String naturalGasRelativeDensity=oFirstSheet.getCell(12,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String saturationPressure=oFirstSheet.getCell(13,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String reservoirDepth=oFirstSheet.getCell(14,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String reservoirTemperature=oFirstSheet.getCell(15,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String tubingPressure=oFirstSheet.getCell(16,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String casingPressure=oFirstSheet.getCell(17,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String wellHeadTemperature=oFirstSheet.getCell(18,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String waterCut=oFirstSheet.getCell(19,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String productionGasOilRatio=oFirstSheet.getCell(20,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String producingfluidLevel=oFirstSheet.getCell(21,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String pumpSettingDepth=oFirstSheet.getCell(22,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String pumpType=oFirstSheet.getCell(23,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String barrelType=oFirstSheet.getCell(24,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String pumpGrade=oFirstSheet.getCell(25,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String pumpBoreDiameter=oFirstSheet.getCell(26,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String plungerLength=oFirstSheet.getCell(27,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String tubingStringInsideDiameter=oFirstSheet.getCell(28,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String casingStringInsideDiameter=oFirstSheet.getCell(29,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodGrade1=oFirstSheet.getCell(30,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodOutsideDiameter1=oFirstSheet.getCell(31,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodInsideDiameter1=oFirstSheet.getCell(32,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodLength1=oFirstSheet.getCell(33,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodGrade2=oFirstSheet.getCell(34,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodOutsideDiameter2=oFirstSheet.getCell(35,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodInsideDiameter2=oFirstSheet.getCell(36,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodLength2=oFirstSheet.getCell(37,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodGrade3=oFirstSheet.getCell(38,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodOutsideDiameter3=oFirstSheet.getCell(39,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodInsideDiameter3=oFirstSheet.getCell(40,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodLength3=oFirstSheet.getCell(41,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodGrade4=oFirstSheet.getCell(42,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodOutsideDiameter4=oFirstSheet.getCell(43,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodInsideDiameter4=oFirstSheet.getCell(44,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodLength4=oFirstSheet.getCell(45,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String netGrossRatio=oFirstSheet.getCell(46,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String manufacturer=oFirstSheet.getCell(47,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String model=oFirstSheet.getCell(48,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String stroke=oFirstSheet.getCell(49,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String crankRotationDirection=oFirstSheet.getCell(50,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String offsetAngleOfCrank=oFirstSheet.getCell(51,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String crankGravityRadius=oFirstSheet.getCell(52,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String singleCrankWeight=oFirstSheet.getCell(53,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String singleCrankPinWeight=oFirstSheet.getCell(54,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String structuralUnbalance=oFirstSheet.getCell(55,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String balanceWeight=oFirstSheet.getCell(56,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String balancePosition=oFirstSheet.getCell(57,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		
			        		result_json.append("{");
				        	result_json.append("\"id\":\""+id+"\",");
				        	result_json.append("\"wellName\":\""+wellName+"\",");
				        	result_json.append("\"applicationScenariosName\":\""+applicationScenariosName+"\",");
				        	result_json.append("\"instanceName\":\""+instanceName+"\",");
				        	result_json.append("\"displayInstanceName\":\""+displayInstanceName+"\",");
				        	result_json.append("\"alarmInstanceName\":\""+alarmInstanceName+"\",");
				        	result_json.append("\"signInId\":\""+signInId+"\",");
				        	result_json.append("\"slave\":\""+slave+"\",");
				        	result_json.append("\"statusName\":\""+statusName+"\",");
				        	result_json.append("\"sortNum\":\""+sortNum+"\",");
				        	result_json.append("\"crudeOilDensity\":\""+crudeOilDensity+"\",");
				        	result_json.append("\"waterDensity\":\""+waterDensity+"\",");
				        	result_json.append("\"naturalGasRelativeDensity\":\""+naturalGasRelativeDensity+"\",");
				        	result_json.append("\"saturationPressure\":\""+saturationPressure+"\",");
				        	result_json.append("\"reservoirDepth\":\""+reservoirDepth+"\",");
				        	result_json.append("\"reservoirTemperature\":\""+reservoirTemperature+"\",");
				        	result_json.append("\"tubingPressure\":\""+tubingPressure+"\",");
				        	result_json.append("\"casingPressure\":\""+casingPressure+"\",");
				        	result_json.append("\"wellHeadTemperature\":\""+wellHeadTemperature+"\",");
				        	result_json.append("\"waterCut\":\""+waterCut+"\",");
				        	result_json.append("\"productionGasOilRatio\":\""+productionGasOilRatio+"\",");
				        	result_json.append("\"producingfluidLevel\":\""+producingfluidLevel+"\",");
				        	result_json.append("\"pumpSettingDepth\":\""+pumpSettingDepth+"\",");
				        	result_json.append("\"pumpType\":\""+pumpType+"\",");
				        	result_json.append("\"barrelType\":\""+barrelType+"\",");
				        	result_json.append("\"pumpGrade\":\""+pumpGrade+"\",");
				        	result_json.append("\"pumpBoreDiameter\":\""+pumpBoreDiameter+"\",");
				        	result_json.append("\"plungerLength\":\""+plungerLength+"\",");
				        	result_json.append("\"tubingStringInsideDiameter\":\""+tubingStringInsideDiameter+"\",");
				        	result_json.append("\"casingStringInsideDiameter\":\""+casingStringInsideDiameter+"\",");
				        	result_json.append("\"rodGrade1\":\""+rodGrade1+"\",");
				        	result_json.append("\"rodOutsideDiameter1\":\""+rodOutsideDiameter1+"\",");
				        	result_json.append("\"rodInsideDiameter1\":\""+rodInsideDiameter1+"\",");
				        	result_json.append("\"rodLength1\":\""+rodLength1+"\",");
				        	result_json.append("\"rodGrade2\":\""+rodGrade2+"\",");
				        	result_json.append("\"rodOutsideDiameter2\":\""+rodOutsideDiameter2+"\",");
				        	result_json.append("\"rodInsideDiameter2\":\""+rodInsideDiameter2+"\",");
				        	result_json.append("\"rodLength2\":\""+rodLength2+"\",");
				        	result_json.append("\"rodGrade3\":\""+rodGrade3+"\",");
				        	result_json.append("\"rodOutsideDiameter3\":\""+rodOutsideDiameter3+"\",");
				        	result_json.append("\"rodInsideDiameter3\":\""+rodInsideDiameter3+"\",");
				        	result_json.append("\"rodLength3\":\""+rodLength3+"\",");
				        	result_json.append("\"rodGrade4\":\""+rodGrade4+"\",");
				        	result_json.append("\"rodOutsideDiameter4\":\""+rodOutsideDiameter4+"\",");
				        	result_json.append("\"rodInsideDiameter4\":\""+rodInsideDiameter4+"\",");
				        	result_json.append("\"rodLength4\":\""+rodLength4+"\",");
				        	result_json.append("\"netGrossRatio\":\""+netGrossRatio+"\",");
				        	result_json.append("\"manufacturer\":\""+manufacturer+"\",");
				        	result_json.append("\"model\":\""+model+"\",");
				        	result_json.append("\"stroke\":\""+stroke+"\",");
				        	result_json.append("\"crankRotationDirection\":\""+crankRotationDirection+"\",");
				        	result_json.append("\"offsetAngleOfCrank\":\""+offsetAngleOfCrank+"\",");
				        	result_json.append("\"crankGravityRadius\":\""+crankGravityRadius+"\",");
				        	result_json.append("\"singleCrankWeight\":\""+singleCrankWeight+"\",");
				        	result_json.append("\"singleCrankPinWeight\":\""+singleCrankPinWeight+"\",");
				        	result_json.append("\"structuralUnbalance\":\""+structuralUnbalance+"\",");
				        	result_json.append("\"balanceWeight\":\""+balanceWeight+"\",");
				        	result_json.append("\"balancePosition\":\""+balancePosition+"\"},");
			        	}catch(Exception e){
							continue;
						}
			        }
				}catch(Exception e){
					continue;
				}
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString();
		System.out.println(json);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@SuppressWarnings({"unchecked"})
	@RequestMapping("/getImportedPCPDeviceFile")
	public String getImportedPCPDeviceFile(CommonsMultipartFile[] files) throws Exception {
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		Map<String,String> importedDeviceFileMap=(Map<String, String>) map.get("importedDeviceFileMap");
		if(importedDeviceFileMap!=null){
			map.remove("importedDeviceFileMap",importedDeviceFileMap);
		}
		importedDeviceFileMap=new HashMap<String,String>();
		String json = "";
		String tablecolumns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50},"
				+ "{ \"header\":\"井名\",\"dataIndex\":\"wellName\"},"
				+ "{ \"header\":\"应用场景\",\"dataIndex\":\"applicationScenariosName\"},"
				+ "{ \"header\":\"采控实例\",\"dataIndex\":\"instanceName\"},"
				+ "{ \"header\":\"显示实例\",\"dataIndex\":\"displayInstanceName\"},"
				+ "{ \"header\":\"报警实例\",\"dataIndex\":\"alarmInstanceName\"},"
				+ "{ \"header\":\"注册包ID\",\"dataIndex\":\"signInId\"},"
				+ "{ \"header\":\"设备从地址\",\"dataIndex\":\"slave\"},"
				+ "{ \"header\":\"状态\",\"dataIndex\":\"statusName\"},"
				+ "{ \"header\":\"排序编号\",\"dataIndex\":\"sortNum\"},"
				+ "{ \"header\":\"原油密度(g/cm^3)\",\"dataIndex\":\"crudeOilDensity\"},"
				+ "{ \"header\":\"水密度(g/cm^3)\",\"dataIndex\":\"waterDensity\"},"
				+ "{ \"header\":\"天然气相对密度\",\"dataIndex\":\"naturalGasRelativeDensity\"},"
				+ "{ \"header\":\"饱和压力(MPa)\",\"dataIndex\":\"saturationPressure\"},"
				+ "{ \"header\":\"油层中部深度(m)\",\"dataIndex\":\"reservoirDepth\"},"
				+ "{ \"header\":\"油层中部温度(℃)\",\"dataIndex\":\"reservoirTemperature\"},"
				+ "{ \"header\":\"油压(MPa)\",\"dataIndex\":\"tubingPressure\"},"
				+ "{ \"header\":\"套压(MPa)\",\"dataIndex\":\"casingPressure\"},"
				+ "{ \"header\":\"井口温度(℃)\",\"dataIndex\":\"wellHeadTemperature\"},"
				+ "{ \"header\":\"含水率(%)\",\"dataIndex\":\"waterCut\"},"
				+ "{ \"header\":\"生产气油比(m^3/t)\",\"dataIndex\":\"productionGasOilRatio\"},"
				+ "{ \"header\":\"动液面(m)\",\"dataIndex\":\"producingfluidLevel\"},"
				+ "{ \"header\":\"泵挂(m)\",\"dataIndex\":\"pumpSettingDepth\"},"
				+ "{ \"header\":\"泵类型\",\"dataIndex\":\"pumpType\"},"
				+ "{ \"header\":\"泵筒类型\",\"dataIndex\":\"barrelType\"},"
				+ "{ \"header\":\"泵级别\",\"dataIndex\":\"pumpGrade\"},"
				+ "{ \"header\":\"泵径(mm)\",\"dataIndex\":\"pumpBoreDiameter\"},"
				+ "{ \"header\":\"柱塞长(m)\",\"dataIndex\":\"plungerLength\"},"
				+ "{ \"header\":\"油管内径(mm)\",\"dataIndex\":\"tubingStringInsideDiameter\"},"
				+ "{ \"header\":\"套管内径(mm)\",\"dataIndex\":\"casingStringInsideDiameter\"},"
				+ "{ \"header\":\"一级杆级别\",\"dataIndex\":\"rodGrade1\"},"
				+ "{ \"header\":\"一级杆外径(mm)\",\"dataIndex\":\"rodOutsideDiameter1\"},"
				+ "{ \"header\":\"一级杆内径(mm)\",\"dataIndex\":\"rodInsideDiameter1\"},"
				+ "{ \"header\":\"一级杆长度(m)\",\"dataIndex\":\"rodLength1\"},"
				+ "{ \"header\":\"二级杆级别\",\"dataIndex\":\"rodGrade2\"},"
				+ "{ \"header\":\"二级杆外径(mm)\",\"dataIndex\":\"rodOutsideDiameter2\"},"
				+ "{ \"header\":\"二级杆内径(mm)\",\"dataIndex\":\"rodInsideDiameter2\"},"
				+ "{ \"header\":\"二级杆长度(m)\",\"dataIndex\":\"rodLength2\"},"
				+ "{ \"header\":\"三级杆级别\",\"dataIndex\":\"rodGrade3\"},"
				+ "{ \"header\":\"三级杆外径(mm)\",\"dataIndex\":\"rodOutsideDiameter3\"},"
				+ "{ \"header\":\"三级杆内径(mm)\",\"dataIndex\":\"rodInsideDiameter3\"},"
				+ "{ \"header\":\"三级杆长度(m)\",\"dataIndex\":\"rodLength3\"},"
				+ "{ \"header\":\"四级杆级别\",\"dataIndex\":\"rodGrade4\"},"
				+ "{ \"header\":\"四级杆外径(mm)\",\"dataIndex\":\"rodOutsideDiameter4\"},"
				+ "{ \"header\":\"四级杆内径(mm)\",\"dataIndex\":\"rodInsideDiameter4\"},"
				+ "{ \"header\":\"四级杆长度(m)\",\"dataIndex\":\"rodLength4\"},"
				+ "{ \"header\":\"净毛比(小数)\",\"dataIndex\":\"netGrossRatio\"},"
				+ "{ \"header\":\"抽油机厂家\",\"dataIndex\":\"manufacturer\"},"
				+ "{ \"header\":\"抽油机型号\",\"dataIndex\":\"model\"},"
				+ "{ \"header\":\"铭牌冲程\",\"dataIndex\":\"stroke\"},"
				+ "{ \"header\":\"曲柄旋转方向\",\"dataIndex\":\"crankRotationDirection\"},"
				+ "{ \"header\":\"曲柄偏置角(°)\",\"dataIndex\":\"offsetAngleOfCrank\"},"
				+ "{ \"header\":\"曲柄重心半径(m)\",\"dataIndex\":\"crankGravityRadius\"},"
				+ "{ \"header\":\"单块曲柄重量(kN)\",\"dataIndex\":\"singleCrankWeight\"},"
				+ "{ \"header\":\"单块曲柄销重量(kN)\",\"dataIndex\":\"singleCrankPinWeight\"},"
				+ "{ \"header\":\"结构不平衡重(kN)\",\"dataIndex\":\"structuralUnbalance\"},"
				+ "{ \"header\":\"平衡块重量(kN)\",\"dataIndex\":\"balanceWeight\"},"
				+ "{ \"header\":\"平衡块位置(m)\",\"dataIndex\":\"balancePosition\"}]";
		result_json.append("{ \"success\":true,\"flag\":true,\"columns\":"+tablecolumns+",");
		result_json.append("\"totalCount\":"+files.length+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<files.length;i++){
			if(!files[i].isEmpty()){
				try{
					Workbook rwb=Workbook.getWorkbook(files[i].getInputStream());
					rwb.getNumberOfSheets();
					Sheet oFirstSheet = rwb.getSheet(0);// 使用索引形式获取第一个工作表，也可以使用rwb.getSheet(sheetName);其中sheetName表示的是工作表的名称  
			        int rows = oFirstSheet.getRows();//获取工作表中的总行数  
			        int columns = oFirstSheet.getColumns();//获取工作表中的总列数  
			        for (int j = 3; j < rows; j++) {
			        	try{
			        		String id=oFirstSheet.getCell(0,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String wellName=oFirstSheet.getCell(1,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String applicationScenariosName=oFirstSheet.getCell(2,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String instanceName=oFirstSheet.getCell(3,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String displayInstanceName=oFirstSheet.getCell(4,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String alarmInstanceName=oFirstSheet.getCell(5,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String signInId=oFirstSheet.getCell(6,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String slave=oFirstSheet.getCell(7,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String statusName=oFirstSheet.getCell(8,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String sortNum=oFirstSheet.getCell(9,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String crudeOilDensity=oFirstSheet.getCell(10,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String waterDensity=oFirstSheet.getCell(11,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String naturalGasRelativeDensity=oFirstSheet.getCell(12,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String saturationPressure=oFirstSheet.getCell(13,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String reservoirDepth=oFirstSheet.getCell(14,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String reservoirTemperature=oFirstSheet.getCell(15,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String tubingPressure=oFirstSheet.getCell(16,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String casingPressure=oFirstSheet.getCell(17,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String wellHeadTemperature=oFirstSheet.getCell(18,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String waterCut=oFirstSheet.getCell(19,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String productionGasOilRatio=oFirstSheet.getCell(20,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String producingfluidLevel=oFirstSheet.getCell(21,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String pumpSettingDepth=oFirstSheet.getCell(22,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String pumpType=oFirstSheet.getCell(23,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String barrelType=oFirstSheet.getCell(24,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String pumpGrade=oFirstSheet.getCell(25,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String pumpBoreDiameter=oFirstSheet.getCell(26,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String plungerLength=oFirstSheet.getCell(27,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String tubingStringInsideDiameter=oFirstSheet.getCell(28,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String casingStringInsideDiameter=oFirstSheet.getCell(29,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodGrade1=oFirstSheet.getCell(30,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodOutsideDiameter1=oFirstSheet.getCell(31,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodInsideDiameter1=oFirstSheet.getCell(32,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodLength1=oFirstSheet.getCell(33,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodGrade2=oFirstSheet.getCell(34,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodOutsideDiameter2=oFirstSheet.getCell(35,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodInsideDiameter2=oFirstSheet.getCell(36,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodLength2=oFirstSheet.getCell(37,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodGrade3=oFirstSheet.getCell(38,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodOutsideDiameter3=oFirstSheet.getCell(39,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodInsideDiameter3=oFirstSheet.getCell(40,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodLength3=oFirstSheet.getCell(41,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodGrade4=oFirstSheet.getCell(42,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodOutsideDiameter4=oFirstSheet.getCell(43,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodInsideDiameter4=oFirstSheet.getCell(44,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodLength4=oFirstSheet.getCell(45,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String netGrossRatio=oFirstSheet.getCell(46,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String manufacturer=oFirstSheet.getCell(47,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String model=oFirstSheet.getCell(48,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String stroke=oFirstSheet.getCell(49,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String crankRotationDirection=oFirstSheet.getCell(50,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String offsetAngleOfCrank=oFirstSheet.getCell(51,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String crankGravityRadius=oFirstSheet.getCell(52,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String singleCrankWeight=oFirstSheet.getCell(53,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String singleCrankPinWeight=oFirstSheet.getCell(54,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String structuralUnbalance=oFirstSheet.getCell(55,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String balanceWeight=oFirstSheet.getCell(56,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String balancePosition=oFirstSheet.getCell(57,j).getContents().replaceAll(" ", "").replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		
			        		result_json.append("{");
				        	result_json.append("\"id\":\""+id+"\",");
				        	result_json.append("\"wellName\":\""+wellName+"\",");
				        	result_json.append("\"applicationScenariosName\":\""+applicationScenariosName+"\",");
				        	result_json.append("\"instanceName\":\""+instanceName+"\",");
				        	result_json.append("\"displayInstanceName\":\""+displayInstanceName+"\",");
				        	result_json.append("\"alarmInstanceName\":\""+alarmInstanceName+"\",");
				        	result_json.append("\"signInId\":\""+signInId+"\",");
				        	result_json.append("\"slave\":\""+slave+"\",");
				        	result_json.append("\"statusName\":\""+statusName+"\",");
				        	result_json.append("\"sortNum\":\""+sortNum+"\",");
				        	result_json.append("\"crudeOilDensity\":\""+crudeOilDensity+"\",");
				        	result_json.append("\"waterDensity\":\""+waterDensity+"\",");
				        	result_json.append("\"naturalGasRelativeDensity\":\""+naturalGasRelativeDensity+"\",");
				        	result_json.append("\"saturationPressure\":\""+saturationPressure+"\",");
				        	result_json.append("\"reservoirDepth\":\""+reservoirDepth+"\",");
				        	result_json.append("\"reservoirTemperature\":\""+reservoirTemperature+"\",");
				        	result_json.append("\"tubingPressure\":\""+tubingPressure+"\",");
				        	result_json.append("\"casingPressure\":\""+casingPressure+"\",");
				        	result_json.append("\"wellHeadTemperature\":\""+wellHeadTemperature+"\",");
				        	result_json.append("\"waterCut\":\""+waterCut+"\",");
				        	result_json.append("\"productionGasOilRatio\":\""+productionGasOilRatio+"\",");
				        	result_json.append("\"producingfluidLevel\":\""+producingfluidLevel+"\",");
				        	result_json.append("\"pumpSettingDepth\":\""+pumpSettingDepth+"\",");
				        	result_json.append("\"pumpType\":\""+pumpType+"\",");
				        	result_json.append("\"barrelType\":\""+barrelType+"\",");
				        	result_json.append("\"pumpGrade\":\""+pumpGrade+"\",");
				        	result_json.append("\"pumpBoreDiameter\":\""+pumpBoreDiameter+"\",");
				        	result_json.append("\"plungerLength\":\""+plungerLength+"\",");
				        	result_json.append("\"tubingStringInsideDiameter\":\""+tubingStringInsideDiameter+"\",");
				        	result_json.append("\"casingStringInsideDiameter\":\""+casingStringInsideDiameter+"\",");
				        	result_json.append("\"rodGrade1\":\""+rodGrade1+"\",");
				        	result_json.append("\"rodOutsideDiameter1\":\""+rodOutsideDiameter1+"\",");
				        	result_json.append("\"rodInsideDiameter1\":\""+rodInsideDiameter1+"\",");
				        	result_json.append("\"rodLength1\":\""+rodLength1+"\",");
				        	result_json.append("\"rodGrade2\":\""+rodGrade2+"\",");
				        	result_json.append("\"rodOutsideDiameter2\":\""+rodOutsideDiameter2+"\",");
				        	result_json.append("\"rodInsideDiameter2\":\""+rodInsideDiameter2+"\",");
				        	result_json.append("\"rodLength2\":\""+rodLength2+"\",");
				        	result_json.append("\"rodGrade3\":\""+rodGrade3+"\",");
				        	result_json.append("\"rodOutsideDiameter3\":\""+rodOutsideDiameter3+"\",");
				        	result_json.append("\"rodInsideDiameter3\":\""+rodInsideDiameter3+"\",");
				        	result_json.append("\"rodLength3\":\""+rodLength3+"\",");
				        	result_json.append("\"rodGrade4\":\""+rodGrade4+"\",");
				        	result_json.append("\"rodOutsideDiameter4\":\""+rodOutsideDiameter4+"\",");
				        	result_json.append("\"rodInsideDiameter4\":\""+rodInsideDiameter4+"\",");
				        	result_json.append("\"rodLength4\":\""+rodLength4+"\",");
				        	result_json.append("\"netGrossRatio\":\""+netGrossRatio+"\",");
				        	result_json.append("\"manufacturer\":\""+manufacturer+"\",");
				        	result_json.append("\"model\":\""+model+"\",");
				        	result_json.append("\"stroke\":\""+stroke+"\",");
				        	result_json.append("\"crankRotationDirection\":\""+crankRotationDirection+"\",");
				        	result_json.append("\"offsetAngleOfCrank\":\""+offsetAngleOfCrank+"\",");
				        	result_json.append("\"crankGravityRadius\":\""+crankGravityRadius+"\",");
				        	result_json.append("\"singleCrankWeight\":\""+singleCrankWeight+"\",");
				        	result_json.append("\"singleCrankPinWeight\":\""+singleCrankPinWeight+"\",");
				        	result_json.append("\"structuralUnbalance\":\""+structuralUnbalance+"\",");
				        	result_json.append("\"balanceWeight\":\""+balanceWeight+"\",");
				        	result_json.append("\"balancePosition\":\""+balancePosition+"\"},");
			        	}catch(Exception e){
							continue;
						}
			        }
				}catch(Exception e){
					continue;
				}
			}
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString();
		System.out.println(json);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * <p>
	 * 描述：辅件设备基本信息Handsontable表格编辑数据保存
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/savePumpingModelHandsontableData")
	public String savePumpingModelHandsontableData() throws Exception {
		HttpSession session=request.getSession();
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String selectedRecordId = ParamUtils.getParameter(request, "selectedRecordId");
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<PumpingModelHandsontableChangedData>() {}.getType();
		PumpingModelHandsontableChangedData pumpingModelHandsontableChangedData=gson.fromJson(data, type);
		String json=this.wellInformationManagerService.savePumpingModelHandsontableData(pumpingModelHandsontableChangedData,selectedRecordId);
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/savePumpingPRTFData")
	public String savePumpingPRTFData() throws Exception {
		HttpSession session=request.getSession();
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String recordId = ParamUtils.getParameter(request, "recordId");
		String json=this.wellInformationManagerService.savePumpingPRTFData(recordId,data);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/batchAddPumpingModel")
	public String batchAddPumpingModel() throws Exception {
		HttpSession session=request.getSession();
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String isCheckout = ParamUtils.getParameter(request, "isCheckout");
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<PumpingModelHandsontableChangedData>() {}.getType();
		PumpingModelHandsontableChangedData pumpingModelHandsontableChangedData=gson.fromJson(data, type);
		String json=this.wellInformationManagerService.batchAddPumpingModel(pumpingModelHandsontableChangedData,isCheckout);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/doRPCDeviceAdd")
	public String doRPCDeviceAdd(@ModelAttribute RpcDeviceInformation rpcDeviceInformation) throws IOException {
		String result = "";
		HttpSession session=request.getSession();
		try {
			User user = (User) session.getAttribute("userLogin");
			if(rpcDeviceInformation.getOrgId()==null){
				rpcDeviceInformation.setOrgId(user.getUserOrgid());
			}
			String videoUrl1 = ParamUtils.getParameter(request, "rpcDeviceInformation.videoUrl1");
			String videoUrl2 = ParamUtils.getParameter(request, "rpcDeviceInformation.videoUrl2");
			rpcDeviceInformation.setVideoUrl(videoUrl1+";"+videoUrl2);
			
			int deviceAmount=ResourceMonitoringTask.getDeviceAmount();
			int license=0;
			AppRunStatusProbeResonanceData acStatusProbeResonanceData=CalculateUtils.appProbe("");
			if(acStatusProbeResonanceData!=null){
				license=acStatusProbeResonanceData.getLicenseNumber();
			}
			if(deviceAmount<license){
				this.rpcDeviceManagerService.doRPCDeviceAdd(rpcDeviceInformation);
				List<String> wells=new ArrayList<String>();
				wells.add(rpcDeviceInformation.getWellName());
				
				DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
				dataSynchronizationThread.setSign(101);
				dataSynchronizationThread.setDeviceType(0);
				dataSynchronizationThread.setInitWellList(wells);
				dataSynchronizationThread.setUpdateList(null);
				dataSynchronizationThread.setAddList(wells);
				dataSynchronizationThread.setDeleteList(null);
				dataSynchronizationThread.setCondition(1);
				dataSynchronizationThread.setMethod("update");
				dataSynchronizationThread.setRpcDeviceInformation(rpcDeviceInformation);
				dataSynchronizationThread.setUser(user);
				dataSynchronizationThread.setRpcDeviceManagerService(rpcDeviceManagerService);
				ThreadPool executor = new ThreadPool("dataSynchronization",AdInitThreadPoolConfig.getInstance().adInitThreadPoolConfigFile.getDataSynchronization().getCorePoolSize(), 
						AdInitThreadPoolConfig.getInstance().adInitThreadPoolConfigFile.getDataSynchronization().getMaximumPoolSize(), 
						AdInitThreadPoolConfig.getInstance().adInitThreadPoolConfigFile.getDataSynchronization().getKeepAliveTime(), 
						TimeUnit.SECONDS, 
						AdInitThreadPoolConfig.getInstance().adInitThreadPoolConfigFile.getDataSynchronization().getWattingCount());
				executor.execute(dataSynchronizationThread);
				result = "{success:true,msg:true,resultCode:1}";
			}else{
				result = "{success:true,msg:true,resultCode:-66}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "{success:false,msg:false}";
		}
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(result);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/doPCPDeviceAdd")
	public String doPCPDeviceAdd(@ModelAttribute PcpDeviceInformation pcpDeviceInformation) throws IOException {
		String result = "";
		HttpSession session=request.getSession();
		try {
			User user = (User) session.getAttribute("userLogin");
			String videoUrl1 = ParamUtils.getParameter(request, "pcpDeviceInformation.videoUrl1");
			String videoUrl2 = ParamUtils.getParameter(request, "pcpDeviceInformation.videoUrl2");
			pcpDeviceInformation.setVideoUrl(videoUrl1+";"+videoUrl2);
			int deviceAmount=ResourceMonitoringTask.getDeviceAmount();
			int license=0;
			AppRunStatusProbeResonanceData acStatusProbeResonanceData=CalculateUtils.appProbe("");
			if(acStatusProbeResonanceData!=null){
				license=acStatusProbeResonanceData.getLicenseNumber();
			}
			if(deviceAmount<license){
				this.pcpDeviceManagerService.doPCPDeviceAdd(pcpDeviceInformation);
				
				List<String> wells=new ArrayList<String>();
				wells.add(pcpDeviceInformation.getWellName());
				
				DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
				dataSynchronizationThread.setSign(201);
				dataSynchronizationThread.setDeviceType(1);
				dataSynchronizationThread.setInitWellList(wells);
				dataSynchronizationThread.setUpdateList(null);
				dataSynchronizationThread.setAddList(wells);
				dataSynchronizationThread.setDeleteList(null);
				dataSynchronizationThread.setCondition(1);
				dataSynchronizationThread.setMethod("update");
				dataSynchronizationThread.setPcpDeviceInformation(pcpDeviceInformation);
				dataSynchronizationThread.setUser(user);
				dataSynchronizationThread.setPcpDeviceManagerService(pcpDeviceManagerService);
				ThreadPool executor = new ThreadPool("dataSynchronization",AdInitThreadPoolConfig.getInstance().adInitThreadPoolConfigFile.getDataSynchronization().getCorePoolSize(), 
						AdInitThreadPoolConfig.getInstance().adInitThreadPoolConfigFile.getDataSynchronization().getMaximumPoolSize(), 
						AdInitThreadPoolConfig.getInstance().adInitThreadPoolConfigFile.getDataSynchronization().getKeepAliveTime(), 
						TimeUnit.SECONDS, 
						AdInitThreadPoolConfig.getInstance().adInitThreadPoolConfigFile.getDataSynchronization().getWattingCount());
				executor.execute(dataSynchronizationThread);
				
				result = "{success:true,msg:true,resultCode:1}";
			}else{
				result = "{success:true,msg:true,resultCode:-66}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "{success:false,msg:false}";
		}
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(result);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/doSMSDeviceAdd")
	public String doSMSDeviceAdd(@ModelAttribute SmsDeviceInformation smsDeviceInformation) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession();
		try {
			User user = (User) session.getAttribute("userLogin");
			this.smsDeviceManagerService.doSMSDeviceAdd(smsDeviceInformation);
			List<String> addWellList=new ArrayList<String>();
			addWellList.add(smsDeviceInformation.getWellName());
			EquipmentDriverServerTask.initSMSDevice(addWellList,"update");
			pcpDeviceManagerService.getBaseDao().saveDeviceOperationLog(null, addWellList, null, 300, user);
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			out.print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
			out.print(result);
		}
		return null;
	}
	
	@RequestMapping("/doPumpingModelAdd")
	public String doPumpingModelAdd(@ModelAttribute PumpingModelInformation pumpingModelInformation) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			this.pumpingModelManagerService.doPumpingModelAdd(pumpingModelInformation);
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			out.print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
			out.print(result);
		}
		return null;
	}
	
	
	/**
	 * <p>
	 * 描述：获取角色类型的下拉菜单数据信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadSszcdyType")
	public String loadSszcdyType() throws Exception {

		String type = ParamUtils.getParameter(request, "type");
		String json = this.wellInformationManagerService.loadSszcdyType(type);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}

	@RequestMapping("/judgeDeviceExistOrNot")
	public String judgeDeviceExistOrNot() throws IOException {
		orgId=ParamUtils.getParameter(request, "orgId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		boolean flag = this.wellInformationManagerService.judgeDeviceExistOrNot(orgId,deviceName,deviceType);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		String json = "";
		if (flag) {
			json = "{success:true,msg:'1'}";
		} else {
			json = "{success:true,msg:'0'}";
		}
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/judgeDeviceExistOrNotBySigninIdAndSlave")
	public String judgeDeviceExistOrNotBySigninIdAndSlave() throws IOException {
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String signinId = ParamUtils.getParameter(request, "signinId");
		String slave = ParamUtils.getParameter(request, "slave");
		boolean flag = this.wellInformationManagerService.judgeDeviceExistOrNotBySigninIdAndSlave(deviceType,signinId,slave);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		String json = "";
		if (flag) {
			json = "{success:true,msg:'1'}";
		} else {
			json = "{success:true,msg:'0'}";
		}
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/judgePumpingModelExistOrNot")
	public String judgePumpingModelExistOrNot() throws IOException {
		orgId=ParamUtils.getParameter(request, "orgId");
		String manufacturer = ParamUtils.getParameter(request, "manufacturer");
		String model = ParamUtils.getParameter(request, "model");
		boolean flag = this.wellInformationManagerService.judgePumpingModelExistOrNot(manufacturer,model);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		String json = "";
		if (flag) {
			json = "{success:true,msg:'1'}";
		} else {
			json = "{success:true,msg:'0'}";
		}
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getUpstreamAndDownstreamInteractionDeviceList")
	public String getUpstreamAndDownstreamInteractionDeviceList() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
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
		json = wellInformationManagerService.getUpstreamAndDownstreamInteractionDeviceList(orgId,deviceName,deviceType,pager);
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceModelData")
	public String getDeviceModelData() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String wellId= ParamUtils.getParameter(request, "wellId");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		String json = this.wellInformationManagerService.getDeviceModelData(wellId);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/downstreamRPCData")
	public String downstreamRPCData() throws Exception {
		StringBuffer downstreamBuff = new StringBuffer();
		String type = ParamUtils.getParameter(request, "type");
		String wellId = ParamUtils.getParameter(request, "wellId");
		String signinId = ParamUtils.getParameter(request, "signinId");
		String slave = ParamUtils.getParameter(request, "slave");
		String data = ParamUtils.getParameter(request, "data");
		String url="";
		String key="Model";
		if(StringManagerUtils.stringToInteger(type)==1){
			url=Config.getInstance().configFile.getAd().getRpc().getWriteTopicModel();
			key="Model";
		}else if(StringManagerUtils.stringToInteger(type)==2){
			url=Config.getInstance().configFile.getAd().getRpc().getWriteTopicConf();
			key="Conf";
		}else if(StringManagerUtils.stringToInteger(type)==3){
			url=Config.getInstance().configFile.getAd().getRpc().getWriteTopicRtc();
			key="Time";
		}else if(StringManagerUtils.stringToInteger(type)==4){
			url=Config.getInstance().configFile.getAd().getRpc().getWriteTopicDog();
			key="Timeout";
		}else if(StringManagerUtils.stringToInteger(type)==5 || StringManagerUtils.stringToInteger(type)==6 || StringManagerUtils.stringToInteger(type)==7){
			url=Config.getInstance().configFile.getAd().getRpc().getWriteTopicStopRpc();
			key="Position";
		}
		
		if(StringManagerUtils.stringToInteger(type)<=2){
			data=data.replaceAll("\r\n", "\n").replaceAll("\n", "").replaceAll(" ", "");
		}else if(StringManagerUtils.stringToInteger(type)==3){
//			data=data.replaceAll("\"", "").replaceAll("\r\n", "").replaceAll("\n", "");
			data=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		}else if(StringManagerUtils.stringToInteger(type)==5){
			data="top";
		}else if(StringManagerUtils.stringToInteger(type)==6){
			data="bottom";
		}else if(StringManagerUtils.stringToInteger(type)==7){
			data="";
		}else {
			
		}
		
		
		if(StringManagerUtils.stringToInteger(type)<=2){
			downstreamBuff.append("{\"ID\":\""+signinId+"\",");
			downstreamBuff.append("\""+key+"\":"+data+"}");
		}else if(StringManagerUtils.stringToInteger(type)==3){
			downstreamBuff.append("{\"ID\":\""+signinId+"\",");
			downstreamBuff.append("\""+key+"\":\""+data+"\"}");
		}else if(StringManagerUtils.stringToInteger(type)==4){
			downstreamBuff.append("{\"ID\":\""+signinId+"\"}");
//			downstreamBuff.append("\""+key+"\":\""+data+"\"}");
		}else if(StringManagerUtils.stringToInteger(type)==5 || StringManagerUtils.stringToInteger(type)==6 || StringManagerUtils.stringToInteger(type)==7){
			downstreamBuff.append("{\"ID\":\""+signinId+"\",");
			downstreamBuff.append("\""+key+"\":\""+data+"\"}");
		}
		
		
		System.out.println(downstreamBuff);
		
		String result="";
		String json="{success:false,msg:0}";
		if(StringManagerUtils.isNotNull(url)){
			if((StringManagerUtils.stringToInteger(type)<=2&&StringManagerUtils.isNotNull(data)) || StringManagerUtils.stringToInteger(type)>=3){
				result=StringManagerUtils.sendPostMethod(url, downstreamBuff.toString(),"utf-8",0,0);
			}
		}
		if(StringManagerUtils.isNotNull(result)){
			Gson gson = new Gson();
			java.lang.reflect.Type reflectType = new TypeToken<RPCInteractionResponseData>() {}.getType();
			RPCInteractionResponseData rpcInteractionResponseData=gson.fromJson(result, reflectType);
			if(rpcInteractionResponseData!=null){
				if(rpcInteractionResponseData.getResultStatus()==1){
					json = "{success:true,msg:1}";
				}else if(rpcInteractionResponseData.getResultStatus()==0){
					json = "{success:true,msg:0}";
				}
			}
		}else{
			json = "{success:false,msg:0}";
		}
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/requestConfigData")
	public String requestConfigData() throws Exception {
		StringBuffer requestBuff = new StringBuffer();
		String result="";
		String type = ParamUtils.getParameter(request, "type");
		String wellId = ParamUtils.getParameter(request, "wellId");
		String signinId = ParamUtils.getParameter(request, "signinId");
		String slave = ParamUtils.getParameter(request, "slave");
		String url=Config.getInstance().configFile.getAd().getRpc().getReadTopicReq();
		String topic="";
		if(StringManagerUtils.stringToInteger(type)==1){
			topic="model";
		}else if(StringManagerUtils.stringToInteger(type)==2){
			topic="conf";
		}else if(StringManagerUtils.stringToInteger(type)==3){
			topic="rtc";
		}
		requestBuff.append("{\"ID\":\""+signinId+"\",");
		requestBuff.append("\"Topic\":\""+topic+"\"}");
		if(StringManagerUtils.stringToInteger(type)<=3){
			result=StringManagerUtils.sendPostMethod(url, requestBuff.toString(),"utf-8",0,0);
			if(StringManagerUtils.isNotNull(result)){
				result=StringManagerUtils.toPrettyFormat(result);
			}
		}
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(result);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getWaterCutRawData")
	public String getWaterCutRawData() throws Exception {
		String json = "";
		String signinId = ParamUtils.getParameter(request, "signinId");
		String slave = ParamUtils.getParameter(request, "slave");
		this.pager = new Page("pagerForm", request);
		
		json = wellInformationManagerService.getWaterCutRawData(signinId,slave);
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getWaterCutRawData2")
	public String getWaterCutRawData2() throws Exception {
		String json = "";
		String signinId = ParamUtils.getParameter(request, "signinId");
		String slave = ParamUtils.getParameter(request, "slave");
		this.pager = new Page("pagerForm", request);
		
		json = wellInformationManagerService.getWaterCutRawData2(signinId,slave);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportWaterCutRawData")
	public String exportWaterCutRawData() throws IOException {
		String json="";
		String signinId = ParamUtils.getParameter(request, "signinId");
		String slave = ParamUtils.getParameter(request, "slave");
		WaterCutRawData waterCutRawData=null;
		String acqTime="";
		String title="含水仪数据";
		String sheetName="含水仪数据";
		// 表头数据
	    List<Object> head = Arrays.asList("序号","采样时间","采样间隔(ms)","含水率(%)","压力(MPa)","位置");
	    List<List<Object>> sheetDataList = new ArrayList<>();
	    sheetDataList.add(head);
		if(StringManagerUtils.isNotNull(signinId) && StringManagerUtils.isNotNull(slave)){
			String url=Config.getInstance().configFile.getAd().getRpc().getReadTopicReq();
			String topic="rawwatercut";
			StringBuffer requestBuff = new StringBuffer();
			requestBuff.append("{\"ID\":\""+signinId+"\",");
			requestBuff.append("\"Topic\":\""+topic+"\"}");
			String responseData=StringManagerUtils.sendPostMethod(url, requestBuff.toString(),"utf-8",5,180);
			
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			type = new TypeToken<WaterCutRawData>() {}.getType();
			waterCutRawData=gson.fromJson(responseData, type);
		}
		
		if(waterCutRawData!=null && waterCutRawData.getResultStatus()==1 && waterCutRawData.getMessage()!=null && waterCutRawData.getMessage().getWaterCut()!=null){
			acqTime=waterCutRawData.getMessage().getAcqTime();
			String startTime=acqTime.split("~")[0];
			long timeStamp=StringManagerUtils.getTimeStamp(startTime, "yyyy-MM-dd HH:mm:ss");
			int size=waterCutRawData.getMessage().getWaterCut().size();
			for(int i=0;i<size;i++){
				if(i>0){
					timeStamp+=waterCutRawData.getMessage().getInterval().get(i);
				} 
				String pointAcqTime=StringManagerUtils.timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss.SSS");
				List<Object> record = new ArrayList<>();
				 record.add(i+1);
				 record.add(pointAcqTime);
				 record.add(waterCutRawData.getMessage().getInterval().get(i));
				 record.add(waterCutRawData.getMessage().getWaterCut().get(i));
				 record.add(waterCutRawData.getMessage().getTubingPressure().get(i));
				 record.add(waterCutRawData.getMessage().getPosition().get(i));
				 sheetDataList.add(record);
			}
		}
		if(StringManagerUtils.isNotNull(acqTime)){
			title+="-"+acqTime.replaceAll(" ", "_").replaceAll("-", "").replaceAll(":", "");
		}
	    // 导出数据
	    ExcelUtils.export(response,title,sheetName, sheetDataList);
		return null;
	}
	
	@RequestMapping("/getHelpDocHtml")
	public String getHelpDocHtml() throws Exception {
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String path=stringManagerUtils.getFilePath("RTUConfig.md","doc/");
		MarkdownEntity html = MarkDown2HtmlWrapper.ofFile(path);
		String fileContent=html.toString();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print(fileContent);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public String getWellInformationName() {
		return wellInformationName;
	}

	public void setWellInformationName(String wellInformationName) {
		this.wellInformationName = wellInformationName;
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

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getLiftingType() {
		return liftingType;
	}

	public void setLiftingType(String liftingType) {
		this.liftingType = liftingType;
	}

	public String getDevicetype() {
		return deviceType;
	}

	public void setDevicetype(String deviceType) {
		this.deviceType = deviceType;
	}

}
