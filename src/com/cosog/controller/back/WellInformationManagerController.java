package com.cosog.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.cosog.model.AuxiliaryDeviceAddInfo;
import com.cosog.model.AuxiliaryDeviceDetailsSaveData;
import com.cosog.model.AuxiliaryDeviceInformation;
import com.cosog.model.DataMapping;
import com.cosog.model.DeviceInformation;
import com.cosog.model.PumpingModelInformation;
import com.cosog.model.MasterAndAuxiliaryDevice;
import com.cosog.model.Org;
import com.cosog.model.PcpDeviceInformation;
import com.cosog.model.DeviceAddInfo;
import com.cosog.model.SRPDeviceInformation;
import com.cosog.model.SmsDeviceInformation;
import com.cosog.model.User;
import com.cosog.model.VideoKey;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.AppRunStatusProbeResonanceData;
import com.cosog.model.calculate.PCPProductionData;
import com.cosog.model.calculate.ResultStatusData;
import com.cosog.model.calculate.SRPCalculateRequestData;
import com.cosog.model.calculate.SRPProductionData;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.drive.SRPInteractionResponseData;
import com.cosog.model.drive.WaterCutRawData;
import com.cosog.model.gridmodel.AdditionalInformationSaveData;
import com.cosog.model.gridmodel.AuxiliaryDeviceConfig;
import com.cosog.model.gridmodel.AuxiliaryDeviceHandsontableChangedData;
import com.cosog.model.gridmodel.PumpingModelHandsontableChangedData;
import com.cosog.model.gridmodel.VideoKeyHandsontableChangedData;
import com.cosog.model.gridmodel.WellHandsontableChangedData;
import com.cosog.service.back.WellInformationManagerService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.task.ResourceMonitoringTask;
import com.cosog.thread.calculate.DataSynchronizationThread;
import com.cosog.thread.calculate.ThreadPool;
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
	private WellInformationManagerService<DeviceInformation> deviceManagerService;
	@Autowired
	private WellInformationManagerService<SRPDeviceInformation> srpDeviceManagerService;
	@Autowired
	private WellInformationManagerService<PcpDeviceInformation> pcpDeviceManagerService;
	@Autowired
	private WellInformationManagerService<SmsDeviceInformation> smsDeviceManagerService;
	@Autowired
	private WellInformationManagerService<PumpingModelInformation> pumpingModelManagerService;
	@Autowired
	private WellInformationManagerService<AuxiliaryDeviceInformation> auxiliaryDeviceManagerService;
	@Autowired
	private WellInformationManagerService<VideoKey> videoKeyManagerService;
	@Autowired
	private CommonDataService service;
	private String limit;
	private String msg = "";
	private String wellInformationName;
	private String deviceType;
	private String orgCode;
	private String resCode;
	private String page;
	private String orgId;
	private int totals;
	
	//添加绑定前缀 
	@InitBinder("srpDeviceInformation")
	public void initBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("srpDeviceInformation.");
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
	
	@InitBinder("videoKey")
	public void initBinder5(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("videoKey.");
	}
	
	@InitBinder("auxiliaryDeviceInformation")
	public void initBinder6(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("auxiliaryDeviceInformation.");
	}
	
	@InitBinder("deviceInformation")
	public void initBinder7(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("deviceInformation.");
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
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		String calculateType= ParamUtils.getParameter(request, "calculateType");
		orgId=ParamUtils.getParameter(request, "orgId");
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String json = this.wellInformationManagerService.loadWellComboxList(pager,orgId, deviceName,deviceType,calculateType,language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/loadPumpingManufacturerComboxList")
	public String loadPumpingManufacturerComboxList() throws Exception {
		this.pager=new Page("pageForm",request);
		String manufacturer = ParamUtils.getParameter(request, "manufacturer");
		
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		
		String json = this.wellInformationManagerService.loadPumpingManufacturerComboxList(manufacturer,language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/loadPumpingManufacturerDropdownList")
	public String loadPumpingManufacturerDropdownList() throws Exception {
		this.pager=new Page("pageForm",request);
		String json = this.wellInformationManagerService.loadPumpingManufacturerDropdownList();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/loadPumpingModelDropdownList")
	public String loadPumpingModelDropdownList() throws Exception {
		this.pager=new Page("pageForm",request);
		String manufacturer = ParamUtils.getParameter(request, "manufacturer");
		String json = this.wellInformationManagerService.loadPumpingModelDropdownList(manufacturer);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getPumpingModelInfo")
	public String getPumpingModelInfo() throws Exception {
		this.pager=new Page("pageForm",request);
		String json = this.wellInformationManagerService.getPumpingModelInfo();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/loadPumpingModelComboxList")
	public String loadPumpingModelComboxList() throws Exception {
		this.pager=new Page("pageForm",request);
		String manufacturer = ParamUtils.getParameter(request, "manufacturer");
		String model = ParamUtils.getParameter(request, "model");
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		String json = this.wellInformationManagerService.loadPumpingModelComboxList(manufacturer,model,language);
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
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		orgId=ParamUtils.getParameter(request, "orgId");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		String json = this.wellInformationManagerService.getDeviceOrgChangeDeviceList(pager,orgId, deviceName,deviceType,language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getApplicationScenariosList")
	public String getApplicationScenariosList() throws Exception {
		this.pager=new Page("pageForm",request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.wellInformationManagerService.getApplicationScenariosList(language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getApplicationScenariosComb")
	public String getApplicationScenariosComb() throws Exception {
		this.pager=new Page("pageForm",request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.wellInformationManagerService.getApplicationScenariosComb(language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceTypeComb")
	public String getDeviceTypeComb() throws Exception {
		this.pager=new Page("pageForm",request);
		String deviceTypes = ParamUtils.getParameter(request, "deviceTypes");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		String json = this.wellInformationManagerService.getDeviceTypeComb(deviceTypes,language);
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
	
	@RequestMapping("/changeDeviceType")
	public String changeDeviceType() throws Exception {
		this.pager=new Page("pageForm",request);
		String selectedDeviceId = ParamUtils.getParameter(request, "selectedDeviceId");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		String selectedDeviceTypeId=ParamUtils.getParameter(request, "selectedDeviceTypeId");
		String selectedDeviceTypeName=ParamUtils.getParameter(request, "selectedDeviceTypeName");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		
		this.wellInformationManagerService.changeDeviceType(selectedDeviceId,selectedDeviceTypeId,selectedDeviceTypeName,deviceType,language);
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
	
	@RequestMapping("/getReportInstanceCombList")
	public String getReportInstanceCombList() throws IOException {
		deviceType= ParamUtils.getParameter(request, "deviceType");
		String json=wellInformationManagerService.getReportInstanceCombList(deviceType);
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
	
	@RequestMapping("/loadDeviceTypeComboxListFromTab")
	public String loadDeviceTypeComboxListFromTab() throws Exception {
		this.pager=new Page("pageForm",request);
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		String json = this.wellInformationManagerService.loadDeviceTypeComboxListFromTab(language);
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
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		String json = this.wellInformationManagerService.loadDataDictionaryComboxList(itemCode,language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/loadCodeComboxList")
	public String loadCodeComboxList() throws Exception {
		this.pager=new Page("pageForm",request);
		String itemCode = ParamUtils.getParameter(request, "itemCode");
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		String json = this.wellInformationManagerService.loadCodeComboxList(itemCode,language);
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
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		orgId=ParamUtils.getParameter(request, "orgId");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		
		orgCode = ParamUtils.getParameter(request, "orgCode");
		resCode = ParamUtils.getParameter(request, "resCode");
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("deviceName", deviceName);
		map.put("deviceType", deviceType);
		map.put("orgCode", orgCode);
		map.put("resCode", resCode);
		map.put("orgId", orgId);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);
		String json="";
		if(StringManagerUtils.stringToInteger(deviceType)>=300){
			json = this.wellInformationManagerService.getSMSDeviceInfoList(map, pager,recordCount,language);
		}else{
			json = this.wellInformationManagerService.getDeviceInfoList(map, pager,recordCount,language);
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
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("wellInformationName", wellInformationName);
		map.put("deviceType", deviceType);
		map.put("orgId", orgId);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);
		String json = this.wellInformationManagerService.getBatchAddDeviceTableInfo(deviceType,recordCount,language);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/doAuxiliaryDeviceShow")
	public String doAuxiliaryDeviceShow() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount"));
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "20" : limit);
		int offset = (intPage - 1) * pageSize + 1;
		deviceType= ParamUtils.getParameter(request, "deviceType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("deviceType", deviceType);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);
		String json = this.wellInformationManagerService.doAuxiliaryDeviceShow(map, pager,deviceType,recordCount,language);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getAuxiliaryDeviceDetailsInfo")
	public String getAuxiliaryDeviceDetailsInfo() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String deviceId= ParamUtils.getParameter(request, "deviceId");
		String auxiliaryDeviceSpecificType= ParamUtils.getParameter(request, "auxiliaryDeviceSpecificType");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.wellInformationManagerService.getAuxiliaryDeviceDetailsInfo(deviceId,auxiliaryDeviceSpecificType,language);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getBatchAddAuxiliaryDeviceTableInfo")
	public String getBatchAddAuxiliaryDeviceTableInfo() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount"));
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.wellInformationManagerService.getBatchAddAuxiliaryDeviceTableInfo(recordCount,language);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportAuxiliaryDeviceData")
	public String exportAuxiliaryDeviceData() throws IOException {
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
		String json = this.wellInformationManagerService.getAuxiliaryDeviceExportData(map, pager,deviceType,recordCount);
		this.service.exportGridPanelData(response,fileName,title, heads, fields,json);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getAuxiliaryDevice")
	public String getAuxiliaryDevice() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String deviceId= ParamUtils.getParameter(request, "deviceId");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.wellInformationManagerService.getAuxiliaryDevice(deviceId,deviceType,language);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/judgeAuxiliaryDeviceExistOrNot")
	public String judgeAuxiliaryDeviceExistOrNot() throws IOException {
		orgId=ParamUtils.getParameter(request, "orgId");
		String name = ParamUtils.getParameter(request, "name");
		String type = ParamUtils.getParameter(request, "type");
		String model = ParamUtils.getParameter(request, "model");
		String manufacturer = ParamUtils.getParameter(request, "manufacturer");
		boolean flag = this.wellInformationManagerService.judgeAuxiliaryDeviceExistOrNot(name,type,manufacturer,model);
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
	
	@RequestMapping("/doAuxiliaryDeviceAdd")
	public String doAuxiliaryDeviceAdd(@ModelAttribute AuxiliaryDeviceInformation auxiliaryDeviceInformation) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			this.auxiliaryDeviceManagerService.doAuxiliaryDeviceAdd(auxiliaryDeviceInformation);
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
	
	@RequestMapping("/saveAuxiliaryDeviceHandsontableData")
	public String saveAuxiliaryDeviceHandsontableData() throws Exception {
		HttpSession session=request.getSession();
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String auxiliaryDeviceSpecificType = ParamUtils.getParameter(request, "auxiliaryDeviceSpecificType");
		String auxiliaryDeviceDetailsSaveDataStr = ParamUtils.getParameter(request, "auxiliaryDeviceDetailsSaveData").replaceAll("&nbsp;", "").replaceAll("null", "");
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll("null", "");
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<AuxiliaryDeviceHandsontableChangedData>() {}.getType();
		AuxiliaryDeviceHandsontableChangedData auxiliaryDeviceHandsontableChangedData=gson.fromJson(data, type);
		String json=this.wellInformationManagerService.saveAuxiliaryDeviceHandsontableData(auxiliaryDeviceHandsontableChangedData,StringManagerUtils.stringToInteger(deviceType));
		int r=wellInformationManagerService.updateAuxiliaryDeviceSpecificType(deviceId,auxiliaryDeviceSpecificType);
		type = new TypeToken<AuxiliaryDeviceDetailsSaveData>() {}.getType();
		AuxiliaryDeviceDetailsSaveData auxiliaryDeviceDetailsSaveData=gson.fromJson(auxiliaryDeviceDetailsSaveDataStr, type);
		if(auxiliaryDeviceDetailsSaveData!=null){
			this.wellInformationManagerService.deleteAuxiliaryDeviceAdditionalInfo(auxiliaryDeviceDetailsSaveData.getDeviceId());
			
			if(auxiliaryDeviceDetailsSaveData.getAuxiliaryDeviceDetailsList()!=null&&auxiliaryDeviceDetailsSaveData.getAuxiliaryDeviceDetailsList().size()>0){
				for(int i=0;i<auxiliaryDeviceDetailsSaveData.getAuxiliaryDeviceDetailsList().size();i++){
					AuxiliaryDeviceAddInfo auxiliaryDeviceAddInfo=new AuxiliaryDeviceAddInfo();
					auxiliaryDeviceAddInfo.setDeviceId(auxiliaryDeviceDetailsSaveData.getAuxiliaryDeviceDetailsList().get(i).getDeviceId());
					auxiliaryDeviceAddInfo.setItemName(auxiliaryDeviceDetailsSaveData.getAuxiliaryDeviceDetailsList().get(i).getItemName());
					auxiliaryDeviceAddInfo.setItemValue(auxiliaryDeviceDetailsSaveData.getAuxiliaryDeviceDetailsList().get(i).getItemValue());
					auxiliaryDeviceAddInfo.setItemUnit(auxiliaryDeviceDetailsSaveData.getAuxiliaryDeviceDetailsList().get(i).getItemUnit());
					auxiliaryDeviceAddInfo.setItemCode(auxiliaryDeviceDetailsSaveData.getAuxiliaryDeviceDetailsList().get(i).getItemCode());
					this.wellInformationManagerService.saveAuxiliaryDeviceAddInfo(auxiliaryDeviceAddInfo);
				}
			}
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
	
	@RequestMapping("/batchAddAuxiliaryDevice")
	public String batchAddAuxiliaryDevice() throws Exception {
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll("null", "");
		String isCheckout = ParamUtils.getParameter(request, "isCheckout");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<AuxiliaryDeviceHandsontableChangedData>() {}.getType();
		AuxiliaryDeviceHandsontableChangedData auxiliaryDeviceHandsontableChangedData=gson.fromJson(data, type);
		String json=this.wellInformationManagerService.batchAddAuxiliaryDevice(auxiliaryDeviceHandsontableChangedData,isCheckout,language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDeviceAdditionalInfo")
	public String getDeviceAdditionalInfo() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String deviceId= ParamUtils.getParameter(request, "deviceId");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.wellInformationManagerService.getDeviceAdditionalInfo(deviceId,deviceType,language);
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
		String manufacturer= ParamUtils.getParameter(request, "manufacturer");
		String model= ParamUtils.getParameter(request, "model");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("deviceType", deviceType);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);
		String json = this.wellInformationManagerService.doPumpingModelShow(manufacturer,model,language);
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
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		this.pager = new Page("pagerForm", request);
		String json = this.wellInformationManagerService.getBatchAddPumpingModelTableInfo(recordCount,language);
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
		String manufacturer = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "manufacturer"),"utf-8");
		String model = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "model"),"utf-8");
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		if(session!=null){
			session.removeAttribute(key);
			session.setAttribute(key, 0);
		}
		
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("deviceType", deviceType);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);
		boolean bool = this.wellInformationManagerService.exportPumpingModelData(user,response,fileName,title, heads, fields,manufacturer,model);
		if(session!=null){
			session.setAttribute(key, 1);
		}
		return null;
	}
	
	@RequestMapping("/getPumpingModelList")
	public String getPumpingModelList() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String deviceId= ParamUtils.getParameter(request, "deviceId");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.wellInformationManagerService.getPumpingModelList(deviceId,deviceType,language);
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
		String deviceCalculateDataType=ParamUtils.getParameter(request, "deviceCalculateDataType");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.wellInformationManagerService.getDeviceProductionDataInfo(deviceId,deviceType,deviceCalculateDataType,language);
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
		String orgId= ParamUtils.getParameter(request, "orgId");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.wellInformationManagerService.getDeviceVideoInfo(deviceId,deviceType,orgId,language);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getVideoKeyData")
	public String getVideoKeyData() throws IOException {
		String orgId= ParamUtils.getParameter(request, "orgId");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.wellInformationManagerService.getVideoKeyData(orgId,language);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveVideoKeyHandsontableData")
	public String saveVideoKeyHandsontableData() throws Exception {
		HttpSession session=request.getSession();
		String json ="{success:true}";
		String data = ParamUtils.getParameter(request, "data");
		String orgId = ParamUtils.getParameter(request, "orgId");
		Gson gson = new Gson();
		
		java.lang.reflect.Type type = new TypeToken<VideoKeyHandsontableChangedData>() {}.getType();
		VideoKeyHandsontableChangedData videoKeyHandsontableChangedData=gson.fromJson(data, type);
		this.wellInformationManagerService.saveVideoKeyHandsontableData(videoKeyHandsontableChangedData,orgId);
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
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
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.wellInformationManagerService.getDevicePumpingInfo(deviceId,deviceType,language);
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
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		String key = ParamUtils.getParameter(request, "key");
		orgId=ParamUtils.getParameter(request, "orgId");
		
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
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		
		orgCode = ParamUtils.getParameter(request, "orgCode");
		resCode = ParamUtils.getParameter(request, "resCode");
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("deviceName", deviceName);
		map.put("deviceType", deviceType);
		map.put("orgCode", orgCode);
		map.put("resCode", resCode);
		map.put("orgId", orgId);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);// 新疆分页Page 工具类
		if(StringManagerUtils.stringToInteger(deviceType)>=300){
			bool = this.wellInformationManagerService.exportSMSDeviceInfoData(user,response,fileName,title, heads, fields,map, pager,recordCount);
		}else{
			bool = this.wellInformationManagerService.exportDeviceInfoData(user,response,fileName,title, heads, fields,map, pager,recordCount);
		}
		if(session!=null){
			session.setAttribute(key, 1);
		}
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
		String json ="{success:true}";
		int deviceId = StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "deviceId"));
		String data = ParamUtils.getParameter(request, "data");
		String deviceAdditionalInformationData = ParamUtils.getParameter(request, "deviceAdditionalInformationData");
		
		String orgId = ParamUtils.getParameter(request, "orgId");
		
		deviceType = ParamUtils.getParameter(request, "deviceType");
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		Gson gson = new Gson();
		String deviceTableName="tbl_device";
		java.lang.reflect.Type type = new TypeToken<WellHandsontableChangedData>() {}.getType();
		WellHandsontableChangedData wellHandsontableChangedData=gson.fromJson(data, type);
		
		if((!Config.getInstance().configFile.getAp().getOthers().getIot()) && StringManagerUtils.stringToInteger(deviceType)<300 ){
			String instanceNames=this.wellInformationManagerService.getDefaultInstanceName(deviceType);
			String[] instanceNameArr=instanceNames.split(";");
			if(instanceNameArr.length==4){
				if(wellHandsontableChangedData.getUpdatelist()!=null){
					for(int i=0;i<wellHandsontableChangedData.getUpdatelist().size();i++){
						wellHandsontableChangedData.getUpdatelist().get(i).setInstanceName(instanceNameArr[0]);
						wellHandsontableChangedData.getUpdatelist().get(i).setDisplayInstanceName(instanceNameArr[1]);
						wellHandsontableChangedData.getUpdatelist().get(i).setAlarmInstanceName(instanceNameArr[2]);
						wellHandsontableChangedData.getUpdatelist().get(i).setReportInstanceName(instanceNameArr[3]);
						wellHandsontableChangedData.getUpdatelist().get(i).setTcpType("TCP Client");
						wellHandsontableChangedData.getUpdatelist().get(i).setSignInId(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName());
						wellHandsontableChangedData.getUpdatelist().get(i).setSlave("01");
					}
				}
				if(wellHandsontableChangedData.getInsertlist()!=null){
					for(int i=0;i<wellHandsontableChangedData.getInsertlist().size();i++){
						wellHandsontableChangedData.getInsertlist().get(i).setInstanceName(instanceNameArr[0]);
						wellHandsontableChangedData.getInsertlist().get(i).setDisplayInstanceName(instanceNameArr[1]);
						wellHandsontableChangedData.getInsertlist().get(i).setAlarmInstanceName(instanceNameArr[2]);
						wellHandsontableChangedData.getInsertlist().get(i).setReportInstanceName(instanceNameArr[3]);
						wellHandsontableChangedData.getInsertlist().get(i).setTcpType("TCP Client");
						wellHandsontableChangedData.getInsertlist().get(i).setSignInId(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName());
						wellHandsontableChangedData.getInsertlist().get(i).setSlave("01");
					}
				}
			}
		}
			
		if(StringManagerUtils.stringToInteger(deviceType)>=300){
			this.wellInformationManagerService.saveSMSDeviceData(wellHandsontableChangedData,orgId,StringManagerUtils.stringToInteger(deviceType),user);
		}else{
			json=this.wellInformationManagerService.saveDeviceData(wellInformationManagerService,wellHandsontableChangedData,orgId,deviceType,user);
		}
		
		List<String> initWellList=new ArrayList<String>();
		initWellList.add(deviceId+"");
		
		type = new TypeToken<AdditionalInformationSaveData>() {}.getType();
		AdditionalInformationSaveData additionalInformationSaveData=gson.fromJson(deviceAdditionalInformationData, type);
		if(additionalInformationSaveData!=null){
			if(additionalInformationSaveData.getType()==0){
				type = new TypeToken<List<AuxiliaryDeviceConfig.AdditionalInfo>>() {}.getType();
				List<AuxiliaryDeviceConfig.AdditionalInfo> additionalInfoList=gson.fromJson(additionalInformationSaveData.getData(), type);
				this.wellInformationManagerService.deleteDeviceAdditionalInfo(additionalInformationSaveData.getDeviceId(),StringManagerUtils.stringToInteger(deviceType));
				if(additionalInfoList!=null&&additionalInfoList.size()>0){
					for(int i=0;i<additionalInfoList.size();i++){
						DeviceAddInfo deviceAddInfo=new DeviceAddInfo();
						deviceAddInfo.setDeviceId(additionalInformationSaveData.getDeviceId());
						deviceAddInfo.setItemName(additionalInfoList.get(i).getItemName());
						deviceAddInfo.setItemValue(additionalInfoList.get(i).getItemValue());
						deviceAddInfo.setItemUnit(additionalInfoList.get(i).getItemUnit());
						this.wellInformationManagerService.saveDeviceAdditionalInfo(deviceAddInfo);
					}
				}
			}else if(additionalInformationSaveData.getType()==1){
				type = new TypeToken<List<Integer>>() {}.getType();
				List<Integer> auxiliaryDeviceList=gson.fromJson(additionalInformationSaveData.getData(), type);
				this.wellInformationManagerService.deleteMasterAndAuxiliary(additionalInformationSaveData.getDeviceId());
				if(auxiliaryDeviceList!=null&&auxiliaryDeviceList.size()>0){
					for(int i=0;i<auxiliaryDeviceList.size();i++){
						MasterAndAuxiliaryDevice masterAndAuxiliaryDevice=new MasterAndAuxiliaryDevice();
						masterAndAuxiliaryDevice.setMasterid(deviceId);
						masterAndAuxiliaryDevice.setAuxiliaryid(auxiliaryDeviceList.get(i));
						masterAndAuxiliaryDevice.setMatrix("0,0,0");
						this.wellInformationManagerService.grantMasterAuxiliaryDevice(masterAndAuxiliaryDevice);
					}
				}
			}else if(additionalInformationSaveData.getType()==2){
				//处理视频数据
				String videoUrl1 = "";
				String videoKeyName1 = "";
				String videoUrl2 = "";
				String videoKeyName2 = ""; 
				
				type = new TypeToken<List<String>>() {}.getType();
				List<String> videoInfoList=gson.fromJson(additionalInformationSaveData.getData(), type);
				
				if(videoInfoList!=null && videoInfoList.size()==4){
					videoUrl1=videoInfoList.get(0);
					videoUrl2=videoInfoList.get(1);
					videoKeyName1=videoInfoList.get(2);
					videoKeyName2=videoInfoList.get(3);
				}
				this.wellInformationManagerService.saveVideiData(StringManagerUtils.stringToInteger(deviceType),deviceId,videoUrl1,videoKeyName1,videoUrl2,videoKeyName2);
			}else if(additionalInformationSaveData.getType()==3){
				type = new TypeToken<List<String>>() {}.getType();
				List<String> droductionDataInfoList=gson.fromJson(additionalInformationSaveData.getData(), type);
				
				if(droductionDataInfoList!=null && droductionDataInfoList.size()>=1){
					String deviceCalculateDataType=droductionDataInfoList.get(0);
					if(StringManagerUtils.stringToInteger(deviceCalculateDataType)==1){
						String deviceProductionData = droductionDataInfoList.get(1);
						String pumpingModelId = droductionDataInfoList.get(2);
						String stroke = droductionDataInfoList.get(3);
						String balanceInfo = droductionDataInfoList.get(4);
						String manualInterventionResultName = droductionDataInfoList.get(5);
						String applicationScenarios=droductionDataInfoList.get(6);
						String FESDiagramSrcName=droductionDataInfoList.get(7);
						
						//处理生产数据
						String deviceProductionDataSaveStr=deviceProductionData;
						type = new TypeToken<SRPProductionData>() {}.getType();
						SRPProductionData srpProductionData=gson.fromJson(deviceProductionData, type);
						if(srpProductionData!=null){
							if(srpProductionData.getProduction()!=null && srpProductionData.getFluidPVT()!=null){
								float weightWaterCut=CalculateUtils.volumeWaterCutToWeightWaterCut(srpProductionData.getProduction().getWaterCut(), srpProductionData.getFluidPVT().getCrudeOilDensity(), srpProductionData.getFluidPVT().getWaterDensity());
								srpProductionData.getProduction().setWeightWaterCut(weightWaterCut);
							}
							if(srpProductionData.getManualIntervention()!=null){
								int manualInterventionResultCode=0;
								if(!languageResourceMap.get("noIntervention").equalsIgnoreCase(manualInterventionResultName)){
									manualInterventionResultCode=MemoryDataManagerTask.getWorkTypeByName(manualInterventionResultName, language).getResultCode();
								}
								srpProductionData.getManualIntervention().setCode(manualInterventionResultCode);
							}
							if(srpProductionData.getFESDiagram()!=null){
								srpProductionData.getFESDiagram().setSrc(StringManagerUtils.stringToInteger(MemoryDataManagerTask.getCodeValue("FESDIAGRAMSRC", FESDiagramSrcName, language)));
							}
							deviceProductionDataSaveStr=gson.toJson(srpProductionData);
						}
						this.wellInformationManagerService.saveProductionData(StringManagerUtils.stringToInteger(deviceType),deviceId,deviceProductionDataSaveStr,StringManagerUtils.stringToInteger(deviceCalculateDataType),StringManagerUtils.stringToInteger(applicationScenarios));
						
						//处理抽油机数据
						//处理抽油机型号
						this.wellInformationManagerService.saveSRPPumpingModel(deviceId,pumpingModelId);
						//处理抽油机详情
						this.wellInformationManagerService.savePumpingInfo(deviceId,stroke,balanceInfo);
					}else if(StringManagerUtils.stringToInteger(deviceCalculateDataType)==2){
						String deviceProductionData = droductionDataInfoList.get(1);
						String applicationScenarios=droductionDataInfoList.get(2);
						//处理生产数据
						String deviceProductionDataSaveStr=deviceProductionData;
						
						type = new TypeToken<PCPProductionData>() {}.getType();
						PCPProductionData productionData=gson.fromJson(deviceProductionData, type);
						if(productionData!=null){
							if(productionData.getProduction()!=null && productionData.getFluidPVT()!=null){
								float weightWaterCut=CalculateUtils.volumeWaterCutToWeightWaterCut(productionData.getProduction().getWaterCut(), productionData.getFluidPVT().getCrudeOilDensity(), productionData.getFluidPVT().getWaterDensity());
								productionData.getProduction().setWeightWaterCut(weightWaterCut);
							}
							deviceProductionDataSaveStr=gson.toJson(productionData);
							this.wellInformationManagerService.saveProductionData(StringManagerUtils.stringToInteger(deviceType),deviceId,deviceProductionDataSaveStr,StringManagerUtils.stringToInteger(deviceCalculateDataType),StringManagerUtils.stringToInteger(applicationScenarios));
							this.wellInformationManagerService.saveSRPPumpingModel(deviceId,"");
							this.wellInformationManagerService.savePumpingInfo(deviceId,"null","");
						}
					}else{
						String applicationScenarios=droductionDataInfoList.get(1);
						this.wellInformationManagerService.saveProductionData(StringManagerUtils.stringToInteger(deviceType),deviceId,"",StringManagerUtils.stringToInteger(deviceCalculateDataType),StringManagerUtils.stringToInteger(applicationScenarios));
						this.wellInformationManagerService.saveSRPPumpingModel(deviceId,"");
						this.wellInformationManagerService.savePumpingInfo(deviceId,"null","");
					}
				}
			}
		}
		
		MemoryDataManagerTask.loadDeviceInfo(initWellList,0,"update");
		
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
		String data = ParamUtils.getParameter(request, "data");
		String orgId = ParamUtils.getParameter(request, "orgId");
		String isCheckout = ParamUtils.getParameter(request, "isCheckout");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String json="";
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<WellHandsontableChangedData>() {}.getType();
		WellHandsontableChangedData wellHandsontableChangedData=gson.fromJson(data, type);
		
		if((!Config.getInstance().configFile.getAp().getOthers().getIot()) && StringManagerUtils.stringToInteger(deviceType)<300 ){
			String[] instanceNameArr=this.wellInformationManagerService.getDefaultInstanceName(deviceType).split(";");
			if(instanceNameArr.length==4){
				if(wellHandsontableChangedData.getUpdatelist()!=null){
					for(int i=0;i<wellHandsontableChangedData.getUpdatelist().size();i++){
						wellHandsontableChangedData.getUpdatelist().get(i).setInstanceName(instanceNameArr[0]);
						wellHandsontableChangedData.getUpdatelist().get(i).setDisplayInstanceName(instanceNameArr[1]);
						wellHandsontableChangedData.getUpdatelist().get(i).setAlarmInstanceName(instanceNameArr[2]);
						wellHandsontableChangedData.getUpdatelist().get(i).setReportInstanceName(instanceNameArr[3]);
						wellHandsontableChangedData.getUpdatelist().get(i).setTcpType("TCP Client");
						wellHandsontableChangedData.getUpdatelist().get(i).setSignInId(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName());
						wellHandsontableChangedData.getUpdatelist().get(i).setSlave("01");
					}
				}
				if(wellHandsontableChangedData.getInsertlist()!=null){
					for(int i=0;i<wellHandsontableChangedData.getInsertlist().size();i++){
						wellHandsontableChangedData.getInsertlist().get(i).setInstanceName(instanceNameArr[0]);
						wellHandsontableChangedData.getInsertlist().get(i).setDisplayInstanceName(instanceNameArr[1]);
						wellHandsontableChangedData.getInsertlist().get(i).setAlarmInstanceName(instanceNameArr[2]);
						wellHandsontableChangedData.getInsertlist().get(i).setReportInstanceName(instanceNameArr[3]);
						wellHandsontableChangedData.getInsertlist().get(i).setTcpType("TCP Client");
						wellHandsontableChangedData.getInsertlist().get(i).setSignInId(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName());
						wellHandsontableChangedData.getInsertlist().get(i).setSlave("01");
					}
				}
			}
		}
		if(StringManagerUtils.stringToInteger(deviceType)>=300){
			this.wellInformationManagerService.saveSMSDeviceData(wellHandsontableChangedData,orgId,StringManagerUtils.stringToInteger(deviceType),user);
		}else{
			json=this.wellInformationManagerService.batchAddDevice(wellInformationManagerService,wellHandsontableChangedData,orgId,deviceType,isCheckout,user);
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
			getImportedSRPDeviceFile(files);
		}else{
			getImportedSRPDeviceFile(files);
		}
		return null;
	}
	
	
	@SuppressWarnings({"unchecked"})
	@RequestMapping("/getImportedSRPDeviceFile")
	public String getImportedSRPDeviceFile(CommonsMultipartFile[] files) throws Exception {
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		Map<String,String> importedDeviceFileMap=(Map<String, String>) map.get("importedDeviceFileMap");
		if(importedDeviceFileMap!=null){
			map.remove("importedDeviceFileMap",importedDeviceFileMap);
		}
		importedDeviceFileMap=new HashMap<String,String>();
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		String json = "";
		String tablecolumns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50},"
				+ "{ \"header\":\""+languageResourceMap.get("deviceName")+"\",\"dataIndex\":\"wellName\"},"
				+ "{ \"header\":\""+languageResourceMap.get("applicationScenarios")+"\",\"dataIndex\":\"applicationScenariosName\"},"
				+ "{ \"header\":\""+languageResourceMap.get("acqInstance")+"\",\"dataIndex\":\"instanceName\"},"
				+ "{ \"header\":\""+languageResourceMap.get("displayInstance")+"\",\"dataIndex\":\"displayInstanceName\"},"
				+ "{ \"header\":\""+languageResourceMap.get("alarmInstance")+"\",\"dataIndex\":\"alarmInstanceName\"},"
				+ "{ \"header\":\""+languageResourceMap.get("signInId")+"\",\"dataIndex\":\"signInId\"},"
				+ "{ \"header\":\""+languageResourceMap.get("slave")+"\",\"dataIndex\":\"slave\"},"
				+ "{ \"header\":\""+languageResourceMap.get("status")+"\",\"dataIndex\":\"statusName\"},"
				+ "{ \"header\":\""+languageResourceMap.get("sortNum")+"\",\"dataIndex\":\"sortNum\"},"
				+ "{ \"header\":\""+languageResourceMap.get("crudeOilDensity")+"(g/cm^3)\",\"dataIndex\":\"crudeOilDensity\"},"
				+ "{ \"header\":\""+languageResourceMap.get("waterDensity")+"(g/cm^3)\",\"dataIndex\":\"waterDensity\"},"
				+ "{ \"header\":\""+languageResourceMap.get("naturalGasRelativeDensity")+"\",\"dataIndex\":\"naturalGasRelativeDensity\"},"
				+ "{ \"header\":\""+languageResourceMap.get("saturationPressure")+"(MPa)\",\"dataIndex\":\"saturationPressure\"},"
				+ "{ \"header\":\""+languageResourceMap.get("reservoirDepth")+"(m)\",\"dataIndex\":\"reservoirDepth\"},"
				+ "{ \"header\":\""+languageResourceMap.get("reservoirTemperature")+"(℃)\",\"dataIndex\":\"reservoirTemperature\"},"
				+ "{ \"header\":\""+languageResourceMap.get("tubingPressure")+"(MPa)\",\"dataIndex\":\"tubingPressure\"},"
				+ "{ \"header\":\""+languageResourceMap.get("casingPressure")+"(MPa)\",\"dataIndex\":\"casingPressure\"},"
				+ "{ \"header\":\""+languageResourceMap.get("wellHeadTemperature")+"(℃)\",\"dataIndex\":\"wellHeadTemperature\"},"
				+ "{ \"header\":\""+languageResourceMap.get("waterCut")+"(%)\",\"dataIndex\":\"waterCut\"},"
				+ "{ \"header\":\""+languageResourceMap.get("productionGasOilRatio")+"(m^3/t)\",\"dataIndex\":\"productionGasOilRatio\"},"
				+ "{ \"header\":\""+languageResourceMap.get("producingfluidLevel")+"(m)\",\"dataIndex\":\"producingfluidLevel\"},"
				+ "{ \"header\":\""+languageResourceMap.get("pumpSettingDepth")+"(m)\",\"dataIndex\":\"pumpSettingDepth\"},"
				+ "{ \"header\":\""+languageResourceMap.get("pumpType")+"\",\"dataIndex\":\"pumpType\"},"
				+ "{ \"header\":\""+languageResourceMap.get("barrelType")+"\",\"dataIndex\":\"barrelType\"},"
				+ "{ \"header\":\""+languageResourceMap.get("pumpGrade")+"\",\"dataIndex\":\"pumpGrade\"},"
				+ "{ \"header\":\""+languageResourceMap.get("pumpBoreDiameter")+"(mm)\",\"dataIndex\":\"pumpBoreDiameter\"},"
				+ "{ \"header\":\""+languageResourceMap.get("plungerLength")+"(m)\",\"dataIndex\":\"plungerLength\"},"
				+ "{ \"header\":\""+languageResourceMap.get("tubingStringInsideDiameter")+"(mm)\",\"dataIndex\":\"tubingStringInsideDiameter\"},"
				+ "{ \"header\":\""+languageResourceMap.get("casingStringInsideDiameter")+"(mm)\",\"dataIndex\":\"casingStringInsideDiameter\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodGrade1")+"\",\"dataIndex\":\"rodGrade1\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodOutsideDiameter1")+"(mm)\",\"dataIndex\":\"rodOutsideDiameter1\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodInsideDiameter1")+"(mm)\",\"dataIndex\":\"rodInsideDiameter1\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodLength1")+"(m)\",\"dataIndex\":\"rodLength1\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodGrade2")+"\",\"dataIndex\":\"rodGrade2\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodOutsideDiameter2")+"(mm)\",\"dataIndex\":\"rodOutsideDiameter2\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodInsideDiameter2")+"(mm)\",\"dataIndex\":\"rodInsideDiameter2\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodLength2")+"(m)\",\"dataIndex\":\"rodLength2\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodGrade3")+"\",\"dataIndex\":\"rodGrade3\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodOutsideDiameter3")+"(mm)\",\"dataIndex\":\"rodOutsideDiameter3\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodInsideDiameter3")+"(mm)\",\"dataIndex\":\"rodInsideDiameter3\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodLength3")+"(m)\",\"dataIndex\":\"rodLength3\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodGrade4")+"\",\"dataIndex\":\"rodGrade4\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodOutsideDiameter4")+"(mm)\",\"dataIndex\":\"rodOutsideDiameter4\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodInsideDiameter4")+"(mm)\",\"dataIndex\":\"rodInsideDiameter4\"},"
				+ "{ \"header\":\""+languageResourceMap.get("rodLength4")+"(m)\",\"dataIndex\":\"rodLength4\"},"
				+ "{ \"header\":\""+languageResourceMap.get("netGrossRatio")+"(小数)\",\"dataIndex\":\"netGrossRatio\"},"
				+ "{ \"header\":\""+languageResourceMap.get("manufacturer")+"\",\"dataIndex\":\"manufacturer\"},"
				+ "{ \"header\":\""+languageResourceMap.get("model")+"\",\"dataIndex\":\"model\"},"
				+ "{ \"header\":\""+languageResourceMap.get("nameplateStroke")+"\",\"dataIndex\":\"stroke\"},"
				+ "{ \"header\":\""+languageResourceMap.get("crankRotationDirection")+"\",\"dataIndex\":\"crankRotationDirection\"},"
				+ "{ \"header\":\""+languageResourceMap.get("offsetAngleOfCrank")+"(°)\",\"dataIndex\":\"offsetAngleOfCrank\"},"
				+ "{ \"header\":\""+languageResourceMap.get("crankGravityRadius")+"(m)\",\"dataIndex\":\"crankGravityRadius\"},"
				+ "{ \"header\":\""+languageResourceMap.get("singleCrankWeight")+"(kN)\",\"dataIndex\":\"singleCrankWeight\"},"
				+ "{ \"header\":\""+languageResourceMap.get("singleCrankPinWeight")+"(kN)\",\"dataIndex\":\"singleCrankPinWeight\"},"
				+ "{ \"header\":\""+languageResourceMap.get("structuralUnbalance")+"(kN)\",\"dataIndex\":\"structuralUnbalance\"},"
				+ "{ \"header\":\""+languageResourceMap.get("balanceWeight")+"(kN)\",\"dataIndex\":\"balanceWeight\"},"
				+ "{ \"header\":\""+languageResourceMap.get("balancePosition")+"(m)\",\"dataIndex\":\"balancePosition\"}]";
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
			        		String id=oFirstSheet.getCell(0,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String wellName=oFirstSheet.getCell(1,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String applicationScenariosName=oFirstSheet.getCell(2,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String instanceName=oFirstSheet.getCell(3,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String displayInstanceName=oFirstSheet.getCell(4,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String alarmInstanceName=oFirstSheet.getCell(5,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String signInId=oFirstSheet.getCell(6,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String slave=oFirstSheet.getCell(7,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String statusName=oFirstSheet.getCell(8,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String sortNum=oFirstSheet.getCell(9,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String crudeOilDensity=oFirstSheet.getCell(10,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String waterDensity=oFirstSheet.getCell(11,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String naturalGasRelativeDensity=oFirstSheet.getCell(12,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String saturationPressure=oFirstSheet.getCell(13,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String reservoirDepth=oFirstSheet.getCell(14,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String reservoirTemperature=oFirstSheet.getCell(15,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String tubingPressure=oFirstSheet.getCell(16,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String casingPressure=oFirstSheet.getCell(17,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String wellHeadTemperature=oFirstSheet.getCell(18,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String waterCut=oFirstSheet.getCell(19,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String productionGasOilRatio=oFirstSheet.getCell(20,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String producingfluidLevel=oFirstSheet.getCell(21,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String pumpSettingDepth=oFirstSheet.getCell(22,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String pumpType=oFirstSheet.getCell(23,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String barrelType=oFirstSheet.getCell(24,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String pumpGrade=oFirstSheet.getCell(25,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String pumpBoreDiameter=oFirstSheet.getCell(26,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String plungerLength=oFirstSheet.getCell(27,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String tubingStringInsideDiameter=oFirstSheet.getCell(28,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String casingStringInsideDiameter=oFirstSheet.getCell(29,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodGrade1=oFirstSheet.getCell(30,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodOutsideDiameter1=oFirstSheet.getCell(31,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodInsideDiameter1=oFirstSheet.getCell(32,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodLength1=oFirstSheet.getCell(33,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodGrade2=oFirstSheet.getCell(34,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodOutsideDiameter2=oFirstSheet.getCell(35,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodInsideDiameter2=oFirstSheet.getCell(36,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodLength2=oFirstSheet.getCell(37,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodGrade3=oFirstSheet.getCell(38,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodOutsideDiameter3=oFirstSheet.getCell(39,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodInsideDiameter3=oFirstSheet.getCell(40,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodLength3=oFirstSheet.getCell(41,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodGrade4=oFirstSheet.getCell(42,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodOutsideDiameter4=oFirstSheet.getCell(43,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodInsideDiameter4=oFirstSheet.getCell(44,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String rodLength4=oFirstSheet.getCell(45,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String netGrossRatio=oFirstSheet.getCell(46,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String manufacturer=oFirstSheet.getCell(47,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String model=oFirstSheet.getCell(48,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String stroke=oFirstSheet.getCell(49,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String crankRotationDirection=oFirstSheet.getCell(50,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String offsetAngleOfCrank=oFirstSheet.getCell(51,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String crankGravityRadius=oFirstSheet.getCell(52,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String singleCrankWeight=oFirstSheet.getCell(53,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String singleCrankPinWeight=oFirstSheet.getCell(54,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String structuralUnbalance=oFirstSheet.getCell(55,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String balanceWeight=oFirstSheet.getCell(56,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		String balancePosition=oFirstSheet.getCell(57,j).getContents().replaceAll("；", ";").replaceAll("，", ",").replaceAll(";", ",");
			        		
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
		String data = ParamUtils.getParameter(request, "data");
		String selectedRecordId = ParamUtils.getParameter(request, "selectedRecordId");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<PumpingModelHandsontableChangedData>() {}.getType();
		PumpingModelHandsontableChangedData pumpingModelHandsontableChangedData=gson.fromJson(data, type);
		String json=this.wellInformationManagerService.savePumpingModelHandsontableData(pumpingModelHandsontableChangedData,selectedRecordId,language);
		
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
		String data = ParamUtils.getParameter(request, "data");
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
		String data = ParamUtils.getParameter(request, "data");
		String isCheckout = ParamUtils.getParameter(request, "isCheckout");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<PumpingModelHandsontableChangedData>() {}.getType();
		PumpingModelHandsontableChangedData pumpingModelHandsontableChangedData=gson.fromJson(data, type);
		String json=this.wellInformationManagerService.batchAddPumpingModel(pumpingModelHandsontableChangedData,isCheckout,language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/doDeviceAdd")
	public String doDeviceAdd(@ModelAttribute DeviceInformation deviceInformation) throws IOException {
		String result = "";
		HttpSession session=request.getSession();
		try {
			User user = (User) session.getAttribute("userLogin");
			if(deviceInformation.getOrgId()==null){
				deviceInformation.setOrgId(user.getUserOrgid());
			}
			
			int deviceAmount=ResourceMonitoringTask.getDeviceAmount();
			int license=0;
			AppRunStatusProbeResonanceData acStatusProbeResonanceData=CalculateUtils.appProbe("",10);
			if(acStatusProbeResonanceData!=null){
				license=acStatusProbeResonanceData.getLicenseNumber();
			}
			if(deviceAmount<license){
				if(!Config.getInstance().configFile.getAp().getOthers().getIot()){//如果不是物联网
					String[] instanceCodeArr=this.srpDeviceManagerService.getDefaultInstanceCode(0).split(";");
					if(instanceCodeArr.length==4){
						deviceInformation.setInstanceCode(instanceCodeArr[0]);
						deviceInformation.setDisplayInstanceCode(instanceCodeArr[1]);
						deviceInformation.setAlarmInstanceCode(instanceCodeArr[2]);
						deviceInformation.setReportInstanceCode(instanceCodeArr[3]);
						deviceInformation.setTcpType("TCP Client");
						deviceInformation.setSignInId(deviceInformation.getDeviceName());
						deviceInformation.setSlave("01");;
					}
				}
				
				this.deviceManagerService.doDeviceAdd(deviceInformation);
				List<String> wells=new ArrayList<String>();
				wells.add(deviceInformation.getDeviceName());
				
				DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
				dataSynchronizationThread.setSign(101);
				dataSynchronizationThread.setInitWellList(wells);
				dataSynchronizationThread.setUpdateList(null);
				dataSynchronizationThread.setAddList(wells);
				dataSynchronizationThread.setDeleteList(null);
				dataSynchronizationThread.setCondition(1);
				dataSynchronizationThread.setMethod("update");
				dataSynchronizationThread.setDeviceInformation(deviceInformation);
				dataSynchronizationThread.setUser(user);
				dataSynchronizationThread.setDeviceType(deviceInformation.getDeviceType()+"");
				dataSynchronizationThread.setDeviceManagerService(deviceManagerService);
				ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
						Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
						Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
						TimeUnit.SECONDS, 
						Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
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
			addWellList.add(smsDeviceInformation.getDeviceName());
			EquipmentDriverServerTask.initSMSDevice(addWellList,"update");
			pcpDeviceManagerService.getBaseDao().saveDeviceOperationLog(null, addWellList, null, user,300+"");
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
	
	@RequestMapping("/doVideoKeyAdd")
	public String doVideoKeyAdd(@ModelAttribute VideoKey videoKey) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			this.videoKeyManagerService.doVideoKeyAdd(videoKey);
			List<String> nameList=new ArrayList<>();
			nameList.add(videoKey.getAccount());
			MemoryDataManagerTask.loadUIKitAccessTokenByName(nameList,"update");
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
	
	@RequestMapping("/judgeDeviceExistOrNotByIpPortAndSlave")
	public String judgeDeviceExistOrNotByIpPortAndSlave() throws IOException {
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String ipPort = ParamUtils.getParameter(request, "ipPort");
		String slave = ParamUtils.getParameter(request, "slave");
		boolean flag = this.wellInformationManagerService.judgeDeviceExistOrNotByIpPortAndSlave(deviceType,ipPort,slave);
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
	
	@RequestMapping("/judgeVideoKeyExistOrNot")
	public String judgeVideoKeyExistOrNot() throws IOException {
		String account = ParamUtils.getParameter(request, "account");
		boolean flag = this.wellInformationManagerService.judgeVideoKeyExistOrNot(account);
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
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		json = wellInformationManagerService.getUpstreamAndDownstreamInteractionDeviceList(orgId,deviceName,deviceType,pager,language);
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
	
	@RequestMapping("/downstreamSRPData")
	public String downstreamSRPData() throws Exception {
		StringBuffer downstreamBuff = new StringBuffer();
		String type = ParamUtils.getParameter(request, "type");
		String wellId = ParamUtils.getParameter(request, "wellId");
		String signinId = ParamUtils.getParameter(request, "signinId");
		String slave = ParamUtils.getParameter(request, "slave");
		String data = ParamUtils.getParameter(request, "data");
		String url="";
		String key="Model";
		if(StringManagerUtils.stringToInteger(type)==1){
			url=Config.getInstance().configFile.getAd().getSrp().getWriteTopicModel();
			key="Model";
		}else if(StringManagerUtils.stringToInteger(type)==2){
			url=Config.getInstance().configFile.getAd().getSrp().getWriteTopicConf();
			key="Conf";
		}else if(StringManagerUtils.stringToInteger(type)==3){
			url=Config.getInstance().configFile.getAd().getSrp().getWriteTopicRtc();
			key="Time";
		}else if(StringManagerUtils.stringToInteger(type)==4){
			url=Config.getInstance().configFile.getAd().getSrp().getWriteTopicDog();
			key="Timeout";
		}else if(StringManagerUtils.stringToInteger(type)==5 || StringManagerUtils.stringToInteger(type)==6 || StringManagerUtils.stringToInteger(type)==7){
			url=Config.getInstance().configFile.getAd().getSrp().getWriteTopicStopRpc();
			key="Position";
		}
		
		if(StringManagerUtils.stringToInteger(type)<=2){
			data=data.replaceAll("\r\n", "\n").replaceAll("\n", "");
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
			java.lang.reflect.Type reflectType = new TypeToken<SRPInteractionResponseData>() {}.getType();
			SRPInteractionResponseData srpInteractionResponseData=gson.fromJson(result, reflectType);
			if(srpInteractionResponseData!=null){
				if(srpInteractionResponseData.getResultStatus()==1){
					json = "{success:true,msg:1}";
				}else if(srpInteractionResponseData.getResultStatus()==0){
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
		String url=Config.getInstance().configFile.getAd().getSrp().getReadTopicReq();
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
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		json = wellInformationManagerService.getWaterCutRawData(signinId,slave,language);
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
		HttpSession session=request.getSession();
		String json="";
		String signinId = ParamUtils.getParameter(request, "signinId");
		String slave = ParamUtils.getParameter(request, "slave");
		String key = ParamUtils.getParameter(request, "key");
		User user = null;
		String language="";
		if(session!=null){
			session.removeAttribute(key);
			session.setAttribute(key, 0);
			user = (User) session.getAttribute("userLogin");
			if(user!=null){
				language=user.getLanguageName();
			}
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		WaterCutRawData waterCutRawData=null;
		String acqTime="";
		String title="含水仪数据";
		String sheetName="含水仪数据";
		// 表头数据
	    List<Object> head = Arrays.asList(languageResourceMap.get("idx"),"采样时间","采样间隔(ms)",languageResourceMap.get("waterCut")+"(%)","压力(MPa)",languageResourceMap.get("position"));
	    List<List<Object>> sheetDataList = new ArrayList<>();
	    sheetDataList.add(head);
		if(StringManagerUtils.isNotNull(signinId) && StringManagerUtils.isNotNull(slave)){
			String url=Config.getInstance().configFile.getAd().getSrp().getReadTopicReq();
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
	    ExcelUtils.export(response,title,sheetName, sheetDataList,1);
	    if(session!=null){
			session.setAttribute(key, 1);
		}
	    if(user!=null){
	    	try {
				wellInformationManagerService.saveSystemLog(user,4,title);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@RequestMapping("/getHelpDocHtml")
	public String getHelpDocHtml() throws Exception {
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String path=stringManagerUtils.getFilePath("rtu.md","readme/");
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
	
	@RequestMapping("/getDeviceCalculateType")
	public String getDeviceCalculateType() throws Exception {
		String json = "";
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		int calculateType=wellInformationManagerService.getDeviceCalculateType(deviceId);
		json="{\"success\":true,\"calculateType\":"+calculateType+"}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getApplicationScenariosType")
	public String getApplicationScenariosType() throws Exception {
		String json = "";
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		int applicationScenarios=wellInformationManagerService.getApplicationScenariosType(deviceId);
		json="{\"success\":true,\"applicationScenarios\":"+applicationScenarios+"}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/deviceProductionDataDownlink")
	public String deviceProductionDataDownlink() throws Exception {
		String deviceId = request.getParameter("deviceId");
		String deviceName = request.getParameter("deviceName");
		String deviceCalculateDataType = request.getParameter("deviceCalculateDataType");
		String productionData = request.getParameter("productionData");
		String pumpingUnitInfo = request.getParameter("productionData");
		String manualInterventionResultName=request.getParameter("productionData");
		
		String jsonLogin = "";
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		
		String deviceTableName="tbl_device";
		// 用户不存在
		if (null != userInfo) {
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo.getLanguageName());
			if (StringManagerUtils.isNotNull(deviceId)) {
				String sql="select t3.protocol,t.tcpType, t.signinid,t.ipport,to_number(t.slave),t.deviceType from "+deviceTableName+" t,tbl_protocolinstance t2,tbl_acq_unit_conf t3 "
						+ " where t.instancecode=t2.code and t2.unitid=t3.id"
						+ " and t.id="+deviceId;
				List<?> list = this.service.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[]) list.get(0);
					String protocolName=obj[0]+"";
					String tcpType=obj[1]+"";
					String signinid=obj[2]+"";
					String ipPort=obj[3]+"";
					String slave=obj[4]+"";
					String deviceType=obj[5]+"";
					ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
					if(protocol!=null && StringManagerUtils.isNotNull(tcpType) && StringManagerUtils.isNotNull(signinid)){
						if(StringManagerUtils.isNotNull(slave)){
							Gson gson = new Gson();
							java.lang.reflect.Type type=null;
							Map<String,String> downStatusMap=new LinkedHashMap<>();
							if(StringManagerUtils.stringToInteger(deviceCalculateDataType)==1){
								type = new TypeToken<SRPCalculateRequestData>() {}.getType();
								SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
								if(srpProductionData!=null){
									downStatusMap.put("CrudeOilDensity", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_CrudeOilDensity",srpProductionData.getFluidPVT().getCrudeOilDensity()+"",userInfo.getLanguageName()));
									downStatusMap.put("WaterDensity", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_WaterDensity",srpProductionData.getFluidPVT().getWaterDensity()+"",userInfo.getLanguageName()));
									downStatusMap.put("NaturalGasRelativeDensity", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_NaturalGasRelativeDensity",srpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"",userInfo.getLanguageName()));
									downStatusMap.put("SaturationPressure", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_SaturationPressure",srpProductionData.getFluidPVT().getSaturationPressure()+"",userInfo.getLanguageName()));
									
									downStatusMap.put("ReservoirDepth", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirDepth",srpProductionData.getReservoir().getDepth()+"",userInfo.getLanguageName()));
									downStatusMap.put("ReservoirTemperature", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirTemperature",srpProductionData.getReservoir().getTemperature()+"",userInfo.getLanguageName()));
									downStatusMap.put("ReservoirDepth_cbm", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirDepth_cbm",srpProductionData.getReservoir().getDepth()+"",userInfo.getLanguageName()));
									downStatusMap.put("ReservoirTemperature_cbm", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirTemperature_cbm",srpProductionData.getReservoir().getTemperature()+"",userInfo.getLanguageName()));
									
									downStatusMap.put("TubingPressure", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_TubingPressure",srpProductionData.getProduction().getTubingPressure()+"",userInfo.getLanguageName()));
									downStatusMap.put("TubingPressure_cbm", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_TubingPressure_cbm",srpProductionData.getProduction().getTubingPressure()+"",userInfo.getLanguageName()));
									downStatusMap.put("CasingPressure", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_CasingPressure",srpProductionData.getProduction().getCasingPressure()+"",userInfo.getLanguageName()));
									downStatusMap.put("WellHeadTemperature", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_WellHeadTemperature",srpProductionData.getProduction().getWellHeadTemperature()+"",userInfo.getLanguageName()));
									downStatusMap.put("WaterCut", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_WaterCut",srpProductionData.getProduction().getWaterCut()+"",userInfo.getLanguageName()));
									downStatusMap.put("ProductionGasOilRatio", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_ProductionGasOilRatio",srpProductionData.getProduction().getProductionGasOilRatio()+"",userInfo.getLanguageName()));
									downStatusMap.put("ProducingfluidLevel", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_ProducingfluidLevel",srpProductionData.getProduction().getProducingfluidLevel()+"",userInfo.getLanguageName()));
									downStatusMap.put("PumpSettingDepth", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PumpSettingDepth",srpProductionData.getProduction().getPumpSettingDepth()+"",userInfo.getLanguageName()));
									
									
									downStatusMap.put("BarrelType", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_BarrelType","L".equalsIgnoreCase(srpProductionData.getPump().getBarrelType())?"2":"1",userInfo.getLanguageName()));
									downStatusMap.put("PumpGrade", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PumpGrade",srpProductionData.getPump().getPumpGrade()+"",userInfo.getLanguageName()));
									downStatusMap.put("PumpBoreDiameter", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PumpBoreDiameter",srpProductionData.getPump().getPumpBoreDiameter()+"",userInfo.getLanguageName()));
									downStatusMap.put("PlungerLength", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PlungerLength",srpProductionData.getPump().getPlungerLength()+"",userInfo.getLanguageName()));
									
									
									downStatusMap.put("TubingStringInsideDiameter", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_TubingStringInsideDiameter",srpProductionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()+"",userInfo.getLanguageName()));
									downStatusMap.put("CasingStringOutsideDiameter", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_CasingStringOutsideDiameter",srpProductionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()+"",userInfo.getLanguageName()));
									
									for(int i=0;i<srpProductionData.getRodString().getEveryRod().size();i++){
										downStatusMap.put("RodStringType"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringType"+(i+1),srpProductionData.getRodString().getEveryRod().get(i).getType()+"",userInfo.getLanguageName()));
										downStatusMap.put("RodGrade"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RodGrade"+(i+1),srpProductionData.getRodString().getEveryRod().get(i).getGrade()+"",userInfo.getLanguageName()));
										downStatusMap.put("RodStringOutsideDiameter"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringOutsideDiameter"+(i+1),srpProductionData.getRodString().getEveryRod().get(i).getOutsideDiameter()+"",userInfo.getLanguageName()));
										downStatusMap.put("RodStringInsideDiameter"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringInsideDiameter"+(i+1),srpProductionData.getRodString().getEveryRod().get(i).getInsideDiameter()+"",userInfo.getLanguageName()));
										downStatusMap.put("RodStringLength"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringLength"+(i+1),srpProductionData.getRodString().getEveryRod().get(i).getLength()+"",userInfo.getLanguageName()));
									}
									
									WorkType w=MemoryDataManagerTask.getWorkTypeByName(manualInterventionResultName, userInfo.getLanguageName());
									downStatusMap.put("ManualInterventionCode", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_ManualInterventionCode",(w!=null?w.getResultCode():0)+"",userInfo.getLanguageName()));
									downStatusMap.put("NetGrossRatio", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_NetGrossRatio",srpProductionData.getManualIntervention().getNetGrossRatio()+"",userInfo.getLanguageName()));
									downStatusMap.put("NetGrossValue", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_NetGrossValue",srpProductionData.getManualIntervention().getNetGrossValue()+"",userInfo.getLanguageName()));
									downStatusMap.put("LevelCorrectValue", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_LevelCorrectValue",srpProductionData.getManualIntervention().getLevelCorrectValue()+"",userInfo.getLanguageName()));
								}
							}else if(StringManagerUtils.stringToInteger(deviceCalculateDataType)==2){
								
							}
							
							StringBuffer result_json = new StringBuffer();
							result_json.append("{\"success\":true,\"flag\":true,\"error\":true,\"msg\":\"<font color=blue>"+languageResourceMap.get("commandExecutedSuccessfully")+"</font>\",\"downStatusList\":[");
							for (String key : downStatusMap.keySet()) {
								result_json.append("{\"key\":\""+key+"\",\"status\":\""+downStatusMap.get(key)+"\"},");
					        }
							if(result_json.toString().endsWith(",")){
								result_json.deleteCharAt(result_json.length() - 1);
							}
							result_json.append("]}");
							jsonLogin=result_json.toString();
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
		System.out.println(jsonLogin);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(jsonLogin);
		pw.flush();
		pw.close();
		return null;
	}
	
	public String dataDownlink(String protocolName,String tcpType,String signinid,String ipPort,String Slave,String calColumn,String writeValue,String language){
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		DataMapping dataMapping=MemoryDataManagerTask.getDataMappingByCalColumn(calColumn);
		String status=languageResourceMap.get("noDownlink");
		int reslut=0;
		if(dataMapping!=null){
			reslut=deviceControlOperation_Mdubus(protocolName,tcpType,signinid,ipPort,Slave,dataMapping.getMappingColumn(),writeValue);
			if(reslut==1){
				status=languageResourceMap.get("downlinkSuccessfully");
			}else{
				status=languageResourceMap.get("downlinkFailure");
			}
		}
		return status;
	}
	
	public int deviceControlOperation_Mdubus(String protocolName,String tcpType,String ID,String ipPort,String Slave,String itemCode,String controlValue){
		int result=-1;
		try {
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle();
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String url=Config.getInstance().configFile.getAd().getRw().getWriteAddr();
			String readUrl=Config.getInstance().configFile.getAd().getRw().getReadAddr();
			
			ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
			int addr=-99;
			String dataType="";
			String title="";
			float ratio=1;
			int quantity=1;
			for(int i=0;i<protocol.getItems().size();i++){
				String col="";
				if(loadProtocolMappingColumnByTitleMap.containsKey(protocol.getItems().get(i).getTitle())){
					col=loadProtocolMappingColumnByTitleMap.get(protocol.getItems().get(i).getTitle()).getMappingColumn();
				}
				if(itemCode.equalsIgnoreCase(col)){
					addr=protocol.getItems().get(i).getAddr();
					dataType=protocol.getItems().get(i).getIFDataType();
					title=protocol.getItems().get(i).getTitle();
					ratio=protocol.getItems().get(i).getRatio();
					quantity=protocol.getItems().get(i).getQuantity();
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
			
			if(StringManagerUtils.isNotNull(title) && addr!=-99){
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
				System.out.println(ctrlJson);
				String responseStr="";
				responseStr=StringManagerUtils.sendPostMethod(url, ctrlJson,"utf-8",0,0);
				if(StringManagerUtils.isNotNull(responseStr)){
					type = new TypeToken<ResultStatusData>() {}.getType();
					ResultStatusData resultStatusData=gson.fromJson(responseStr, type);
					result=resultStatusData.getResultStatus();
				}
//				realTimeMonitoringService.saveDeviceControlLog(deviceId,deviceName,deviceType,title,StringManagerUtils.objectToString(controlValue, dataType),user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
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

	public String getDevicetype() {
		return deviceType;
	}

	public void setDevicetype(String deviceType) {
		this.deviceType = deviceType;
	}

}
