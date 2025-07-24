package com.cosog.controller.back;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.net.URLEncoder;
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.engine.jdbc.SerializableClobProxy;
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
import com.cosog.model.calculate.FSDiagramConstructionRequestData;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.PCPProductionData;
import com.cosog.model.calculate.PumpingPRTFData;
import com.cosog.model.calculate.ResultStatusData;
import com.cosog.model.calculate.SRPCalculateRequestData;
import com.cosog.model.calculate.SRPProductionData;
import com.cosog.model.drive.AcqAddrData;
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
import oracle.sql.CLOB;
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
				orgId = "" + user.getUserOrgIds();
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
				orgId = "" + user.getUserOrgIds();
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
				orgId = "" + user.getUserOrgIds();
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
				orgId = "" + user.getUserOrgIds();
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
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		String json=wellInformationManagerService.getAcqInstanceCombList(deviceType,user);
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
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		String json=wellInformationManagerService.getDisplayInstanceCombList(deviceType,user);
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
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
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
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		deviceType= ParamUtils.getParameter(request, "deviceType");
		String json=wellInformationManagerService.getAlarmInstanceCombList(deviceType,user);
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
	
	@RequestMapping("/loadCodeComboxListWithoutAll")
	public String loadCodeComboxListWithoutAll() throws Exception {
		this.pager=new Page("pageForm",request);
		String itemCode = ParamUtils.getParameter(request, "itemCode");
		String values = ParamUtils.getParameter(request, "values");
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		String json = this.wellInformationManagerService.loadCodeComboxListWithoutAll(itemCode,values,language);
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
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
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
				orgId = "" + user.getUserOrgIds();
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
			json = this.wellInformationManagerService.getDeviceInfoList(map, pager,recordCount,dictDeviceType,user);
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
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
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
		String json = this.wellInformationManagerService.getBatchAddDeviceTableInfo(deviceType,recordCount,dictDeviceType,user);
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
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
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
		String json = this.wellInformationManagerService.doAuxiliaryDeviceShow(map, pager,deviceType,recordCount,dictDeviceType,language);
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
	
	@RequestMapping("/getPumpingUnitDetailsInfo")
	public String getPumpingUnitDetailsInfo() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String manufacturer= ParamUtils.getParameter(request, "manufacturer");
		String model= ParamUtils.getParameter(request, "model");
		String auxiliaryDeviceSpecificType= ParamUtils.getParameter(request, "auxiliaryDeviceSpecificType");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.wellInformationManagerService.getPumpingUnitDetailsInfo(manufacturer,model,language);
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
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.wellInformationManagerService.getBatchAddAuxiliaryDeviceTableInfo(recordCount,dictDeviceType,language);
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
	
	@RequestMapping("/exportAuxiliaryDeviceCompleteData")
	public String exportAuxiliaryDeviceCompleteData() throws Exception {
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount"));
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "20" : limit);
		int offset = (intPage - 1) * pageSize + 1;
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
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
		
		String json=this.wellInformationManagerService.exportAuxiliaryDeviceCompleteData(user);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		fileName+=".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(user!=null){
				this.service.saveSystemLog(user,4,languageResourceMap.get("exportFile")+":"+fileName);
			}
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
            in = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer,0,len);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
        	if(in!=null){
        		in.close();
        	}
        	if(out!=null){
        		out.close();
        	}
        	if(session!=null){
    			session.setAttribute(key, 1);
    		}
        }
		StringManagerUtils.deleteFile(path);
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
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
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
		String json=this.wellInformationManagerService.batchAddAuxiliaryDevice(auxiliaryDeviceHandsontableChangedData,isCheckout,dictDeviceType,language);
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
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
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
		String json = this.wellInformationManagerService.doPumpingModelShow(manufacturer,model,dictDeviceType,language);
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
		String deviceId= ParamUtils.getParameter(request, "deviceId");
		String stroke= ParamUtils.getParameter(request, "stroke");
		
		String json = this.wellInformationManagerService.getPumpingPRTFData(deviceId,stroke);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDevicePumpingPRTFData")
	public String getDevicePumpingPRTFData() throws IOException, SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		String manufacturer= ParamUtils.getParameter(request, "manufacturer");
		String model= ParamUtils.getParameter(request, "model");
		String stroke= ParamUtils.getParameter(request, "stroke");
		
		String json = this.wellInformationManagerService.getDevicePumpingPRTFData(manufacturer,model,stroke);
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
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		this.pager = new Page("pagerForm", request);
		String json = this.wellInformationManagerService.getBatchAddPumpingModelTableInfo(recordCount,dictDeviceType,language);
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
	
	@RequestMapping("/getFSDiagramConstructionDataInfo")
	public String getFSDiagramConstructionDataInfo() throws IOException {
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
		String json = this.wellInformationManagerService.getFSDiagramConstructionDataInfo(deviceId,language);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getSystemParameterInfo")
	public String getSystemParameterInfo() throws IOException {
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
		String json = this.wellInformationManagerService.getSystemParameterInfo(deviceId,language);
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
		String auxiliaryDeviceType= ParamUtils.getParameter(request, "auxiliaryDeviceType");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.wellInformationManagerService.getDevicePumpingInfo(deviceId,deviceType,auxiliaryDeviceType,language);
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
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
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
				orgId = "" + user.getUserOrgIds();
			}
		}
		
		orgCode = ParamUtils.getParameter(request, "orgCode");
		resCode = ParamUtils.getParameter(request, "resCode");
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("deviceName", deviceName);
		map.put("deviceType", deviceType);
		map.put("dictDeviceType", dictDeviceType);
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
	
	@RequestMapping("/exportDeviceCompleteData")
	public String exportDeviceCompleteData() throws Exception {
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		int recordCount =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "recordCount"));
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String key = ParamUtils.getParameter(request, "key");
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
			orgId = "" + user.getUserOrgIds();
		}
		
		if(session!=null){
			session.removeAttribute(key);
			session.setAttribute(key, 0);
		}
		
		String json=this.wellInformationManagerService.exportDeviceCompleteData(orgId,deviceName,user);
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		fileName+=".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(user!=null){
				this.service.saveSystemLog(user,4,languageResourceMap.get("exportFile")+":"+fileName);
			}
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
            in = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer,0,len);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
        	if(in!=null){
        		in.close();
        	}
        	if(out!=null){
        		out.close();
        	}
        	if(session!=null){
    			session.setAttribute(key, 1);
    		}
        }
		StringManagerUtils.deleteFile(path);
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
						deviceAddInfo.setOverview(StringManagerUtils.isNum(additionalInfoList.get(i).getOverview())?StringManagerUtils.stringToInteger(additionalInfoList.get(i).getOverview()):0);
						deviceAddInfo.setOverviewSort(StringManagerUtils.isNum(additionalInfoList.get(i).getOverviewSort())?StringManagerUtils.stringToInteger(additionalInfoList.get(i).getOverviewSort()):null);
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
			}else if(additionalInformationSaveData.getType()==31){
				type = new TypeToken<List<String>>() {}.getType();
				List<String> productionDataInfoList=gson.fromJson(additionalInformationSaveData.getData(), type);
				
				if(productionDataInfoList!=null && productionDataInfoList.size()>=1){
					String deviceCalculateDataType=productionDataInfoList.get(0);
					if(StringManagerUtils.stringToInteger(deviceCalculateDataType)==1){
						String deviceProductionData = productionDataInfoList.get(1);
						String manualInterventionResultName = productionDataInfoList.get(2);
						String applicationScenarios=productionDataInfoList.get(3);
						String FESDiagramSrcName=productionDataInfoList.get(4);
						
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
						this.wellInformationManagerService.saveProductionData(deviceId,deviceProductionDataSaveStr,StringManagerUtils.stringToInteger(deviceCalculateDataType),StringManagerUtils.stringToInteger(applicationScenarios));
					}else if(StringManagerUtils.stringToInteger(deviceCalculateDataType)==2){
						String deviceProductionData = productionDataInfoList.get(1);
						String applicationScenarios=productionDataInfoList.get(2);
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
							this.wellInformationManagerService.saveProductionData(deviceId,deviceProductionDataSaveStr,StringManagerUtils.stringToInteger(deviceCalculateDataType),StringManagerUtils.stringToInteger(applicationScenarios));
							this.wellInformationManagerService.saveSRPPumpingModel(deviceId,"","");
							this.wellInformationManagerService.savePumpingInfo(deviceId,"null","");
						}
					}else{
						String applicationScenarios=productionDataInfoList.get(1);
						this.wellInformationManagerService.saveProductionData(deviceId,"",StringManagerUtils.stringToInteger(deviceCalculateDataType),StringManagerUtils.stringToInteger(applicationScenarios));
					}
				}
			}else if(additionalInformationSaveData.getType()==32){
				type = new TypeToken<List<String>>() {}.getType();
				List<String> productionDataInfoList=gson.fromJson(additionalInformationSaveData.getData(), type);
				if(productionDataInfoList!=null && productionDataInfoList.size()==4){
					String manufacturer = productionDataInfoList.get(0);
					String model = productionDataInfoList.get(1);
					String stroke = productionDataInfoList.get(2);
					String balanceInfo = productionDataInfoList.get(3);
					//处理抽油机型号
					this.wellInformationManagerService.saveSRPPumpingModel(deviceId,manufacturer,model);
					//处理抽油机详情
					this.wellInformationManagerService.savePumpingInfo(deviceId,stroke,balanceInfo);
				}else{
					this.wellInformationManagerService.saveSRPPumpingModel(deviceId,"","");
					this.wellInformationManagerService.savePumpingInfo(deviceId,"null","");
				}
			}else if(additionalInformationSaveData.getType()==4){
				this.wellInformationManagerService.saveFSDiagramConstructionData(deviceId,additionalInformationSaveData.getData());
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
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
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
			json=this.wellInformationManagerService.batchAddDevice(wellInformationManagerService,wellHandsontableChangedData,orgId,deviceType,isCheckout,dictDeviceType,user);
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
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String json=this.wellInformationManagerService.savePumpingPRTFData(deviceId,data);
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
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<PumpingModelHandsontableChangedData>() {}.getType();
		PumpingModelHandsontableChangedData pumpingModelHandsontableChangedData=gson.fromJson(data, type);
		String json=this.wellInformationManagerService.batchAddPumpingModel(pumpingModelHandsontableChangedData,isCheckout,dictDeviceType,language);
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
				orgId = "" + user.getUserOrgIds();
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
		String pumpingUnitInfo = request.getParameter("pumpingUnitInfo");
		String manualInterventionResultName=request.getParameter("manualInterventionResultName");
		String applicationScenarios=request.getParameter("applicationScenarios");
		String jsonLogin = "";
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		// 用户不存在
		if (null != userInfo) {
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo.getLanguageName());
			if (StringManagerUtils.isNotNull(deviceId)) {
				String sql="select t3.protocol,t.tcpType, t.signinid,t.ipport,to_number(t.slave),t.deviceType,t4.commstatus"
						+ " from tbl_device t"
						+ " left outer join tbl_protocolinstance t2 on t.instancecode=t2.code"
						+ " left outer join tbl_acq_unit_conf t3 on t2.unitid=t3.id"
						+ " left outer join tbl_acqdata_latest t4 on t4.deviceid=t.id"
						+ " where t.id="+deviceId;
				List<?> list = this.service.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[]) list.get(0);
					String protocolName=obj[0]+"";
					String tcpType=obj[1]+"";
					String signinid=obj[2]+"";
					String ipPort=obj[3]+"";
					String slave=obj[4]+"";
					String deviceType=obj[5]+"";
					int commStatus=StringManagerUtils.stringToInteger(obj[6]+"");
					ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
					if(protocol!=null && StringManagerUtils.isNotNull(tcpType) && (StringManagerUtils.isNotNull(signinid)||StringManagerUtils.isNotNull(ipPort) )  && StringManagerUtils.isNotNull(slave)  ){
						if(commStatus>0){
							Gson gson = new Gson();
							java.lang.reflect.Type type=null;
							Map<String,String> downStatusMap=new LinkedHashMap<>();
							if(StringManagerUtils.stringToInteger(deviceCalculateDataType)==1){
								type = new TypeToken<SRPCalculateRequestData>() {}.getType();
								SRPCalculateRequestData srpProductionData=gson.fromJson(productionData, type);
								if(srpProductionData!=null){
									
									String auxiliaryDeviceSql="select t.id,t3.id as auxiliarydeviceid,t3.manufacturer,t3.model,t4.itemcode,t4.itemvalue,"
											+ " t.stroke,t.balanceinfo "
											+ " from tbl_device t,tbl_auxiliary2master t2,tbl_auxiliarydevice t3,tbl_auxiliarydeviceaddinfo t4 "
											+ " where t.id=t2.masterid and t2.auxiliaryid=t3.id and t3.id=t4.deviceid "
											+ " and t3.specifictype=1"
											+ " and t.id="+deviceId;
									List<?> auxiliaryDeviceList = this.service.findCallSql(auxiliaryDeviceSql);
									List<AuxiliaryDeviceAddInfo> auxiliaryDeviceAddInfoList=new ArrayList<>();
									String balanceInfo="";
									float stroke=0;
									
									for(int i=0;i<auxiliaryDeviceList.size();i++){
										Object[] auxiliaryDeviceObj=(Object[]) auxiliaryDeviceList.get(i);
										AuxiliaryDeviceAddInfo auxiliaryDeviceAddInfo=new AuxiliaryDeviceAddInfo();
										auxiliaryDeviceAddInfo.setMasterId(StringManagerUtils.stringToInteger(auxiliaryDeviceObj[0]+""));
										auxiliaryDeviceAddInfo.setDeviceId(StringManagerUtils.stringToInteger(auxiliaryDeviceObj[1]+""));
										auxiliaryDeviceAddInfo.setManufacturer(auxiliaryDeviceObj[2]+"");
										auxiliaryDeviceAddInfo.setModel(auxiliaryDeviceObj[3]+"");
										auxiliaryDeviceAddInfo.setItemCode(auxiliaryDeviceObj[4]+"");
										auxiliaryDeviceAddInfo.setItemValue(auxiliaryDeviceObj[5]+"");
										auxiliaryDeviceAddInfoList.add(auxiliaryDeviceAddInfo);
										
										stroke=StringManagerUtils.stringToFloat(auxiliaryDeviceObj[6]+"");
										balanceInfo=auxiliaryDeviceObj[7]+"";
									}
									
									if(auxiliaryDeviceAddInfoList.size()>0){
										srpProductionData.setPumpingUnit(new SRPCalculateRequestData.PumpingUnit());
										String manufacturer="";
										String model="";
										for(int i=0;i<auxiliaryDeviceAddInfoList.size();i++ ){
											manufacturer=auxiliaryDeviceAddInfoList.get(i).getManufacturer();
											model=auxiliaryDeviceAddInfoList.get(i).getModel();
											if("structureType".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
												srpProductionData.getPumpingUnit().setStructureType(StringManagerUtils.stringToInteger(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
											}else if("crankRotationDirection".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
												srpProductionData.getPumpingUnit().setCrankRotationDirection(auxiliaryDeviceAddInfoList.get(i).getItemValue());
											}else if("offsetAngleOfCrank".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
												srpProductionData.getPumpingUnit().setOffsetAngleOfCrank(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
											}else if("crankGravityRadius".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
												srpProductionData.getPumpingUnit().setCrankGravityRadius(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
											}else if("singleCrankWeight".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
												srpProductionData.getPumpingUnit().setSingleCrankWeight(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
											}else if("singleCrankPinWeight".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
												srpProductionData.getPumpingUnit().setSingleCrankPinWeight(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
											}else if("structuralUnbalance".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
												srpProductionData.getPumpingUnit().setStructuralUnbalance(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
											}
										}
										srpProductionData.getPumpingUnit().setManufacturer(manufacturer);
										srpProductionData.getPumpingUnit().setModel(model);
										srpProductionData.getPumpingUnit().setStroke(stroke);
										
										type = new TypeToken<SRPCalculateRequestData.Balance>() {}.getType();
										SRPCalculateRequestData.Balance balance=gson.fromJson(balanceInfo, type);
										if(balance!=null){
											srpProductionData.getPumpingUnit().setBalance(balance);
										}
									}
									
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
									
									int barrelType=0;
									if("L".equalsIgnoreCase(srpProductionData.getPump().getBarrelType())){
										barrelType=2;
									}else if("H".equalsIgnoreCase(srpProductionData.getPump().getBarrelType())){
										barrelType=1;
									}
									downStatusMap.put("BarrelType", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_BarrelType",barrelType+"",userInfo.getLanguageName()));
									
									downStatusMap.put("PumpGrade", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PumpGrade",srpProductionData.getPump().getPumpGrade()+"",userInfo.getLanguageName()));
									downStatusMap.put("PumpBoreDiameter", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PumpBoreDiameter",srpProductionData.getPump().getPumpBoreDiameter()*1000+"",userInfo.getLanguageName()));
									downStatusMap.put("PlungerLength", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PlungerLength",srpProductionData.getPump().getPlungerLength()+"",userInfo.getLanguageName()));
									
									
									downStatusMap.put("TubingStringInsideDiameter", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_TubingStringInsideDiameter",srpProductionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000+"",userInfo.getLanguageName()));
									downStatusMap.put("CasingStringOutsideDiameter", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_CasingStringOutsideDiameter",srpProductionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000+"",userInfo.getLanguageName()));
									
									for(int i=0;i<srpProductionData.getRodString().getEveryRod().size();i++){
										downStatusMap.put("RodStringType"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringType"+(i+1),srpProductionData.getRodString().getEveryRod().get(i).getType()+"",userInfo.getLanguageName()));
										
										int rodGrade=0;
										if("A".equalsIgnoreCase(srpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=1;
										}if("B".equalsIgnoreCase(srpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=2;
										}if("C".equalsIgnoreCase(srpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=3;
										}if("K".equalsIgnoreCase(srpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=4;
										}if("D".equalsIgnoreCase(srpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=5;
										}if("KD".equalsIgnoreCase(srpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=6;
										}if("HL".equalsIgnoreCase(srpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=7;
										}if("HY".equalsIgnoreCase(srpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=8;
										}
										downStatusMap.put("RodGrade"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RodGrade"+(i+1),rodGrade+"",userInfo.getLanguageName()));
										downStatusMap.put("RodStringOutsideDiameter"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringOutsideDiameter"+(i+1),srpProductionData.getRodString().getEveryRod().get(i).getOutsideDiameter()*1000+"",userInfo.getLanguageName()));
										downStatusMap.put("RodStringInsideDiameter"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringInsideDiameter"+(i+1),srpProductionData.getRodString().getEveryRod().get(i).getInsideDiameter()*1000+"",userInfo.getLanguageName()));
										downStatusMap.put("RodStringLength"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringLength"+(i+1),srpProductionData.getRodString().getEveryRod().get(i).getLength()+"",userInfo.getLanguageName()));
									}
									
									WorkType w=MemoryDataManagerTask.getWorkTypeByName(manualInterventionResultName, userInfo.getLanguageName());
									downStatusMap.put("ManualInterventionCode", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_ManualInterventionCode",(w!=null?w.getResultCode():0)+"",userInfo.getLanguageName()));
									downStatusMap.put("NetGrossRatio", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_NetGrossRatio",srpProductionData.getManualIntervention().getNetGrossRatio()+"",userInfo.getLanguageName()));
									downStatusMap.put("NetGrossValue", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_NetGrossValue",srpProductionData.getManualIntervention().getNetGrossValue()+"",userInfo.getLanguageName()));
									downStatusMap.put("LevelCorrectValue", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_LevelCorrectValue",srpProductionData.getManualIntervention().getLevelCorrectValue()+"",userInfo.getLanguageName()));
									srpProductionData.getManualIntervention().setCode(w!=null?w.getResultCode():0);
									
									if(srpProductionData.getPumpingUnit()!=null){
										downStatusMap.put("CrankRotationDirection", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_CrankRotationDirection","Clockwise".equalsIgnoreCase(srpProductionData.getPumpingUnit().getCrankRotationDirection())?"1":"0",userInfo.getLanguageName()));
										downStatusMap.put("OffsetAngleOfCrank", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_OffsetAngleOfCrank",srpProductionData.getPumpingUnit().getOffsetAngleOfCrank()+"",userInfo.getLanguageName()));
										
										if(srpProductionData.getPumpingUnit().getBalance()!=null && srpProductionData.getPumpingUnit().getBalance().getEveryBalance()!=null){
											for(int i=0;i<srpProductionData.getPumpingUnit().getBalance().getEveryBalance().size();i++){
												downStatusMap.put("BalanceWeight"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_BalanceWeight"+(i+1),srpProductionData.getPumpingUnit().getBalance().getEveryBalance().get(i).getWeight()+"",userInfo.getLanguageName()));
											}
										}
									}
									this.wellInformationManagerService.saveProductionData(StringManagerUtils.stringToInteger(deviceId),gson.toJson(srpProductionData),StringManagerUtils.stringToInteger(deviceCalculateDataType),StringManagerUtils.stringToInteger(applicationScenarios));
								}
							}else if(StringManagerUtils.stringToInteger(deviceCalculateDataType)==2){
								type = new TypeToken<PCPCalculateRequestData>() {}.getType();
								PCPCalculateRequestData pcpProductionData=gson.fromJson(productionData, type);
								if(pcpProductionData!=null){
									downStatusMap.put("CrudeOilDensity", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_CrudeOilDensity",pcpProductionData.getFluidPVT().getCrudeOilDensity()+"",userInfo.getLanguageName()));
									downStatusMap.put("WaterDensity", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_WaterDensity",pcpProductionData.getFluidPVT().getWaterDensity()+"",userInfo.getLanguageName()));
									downStatusMap.put("NaturalGasRelativeDensity", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_NaturalGasRelativeDensity",pcpProductionData.getFluidPVT().getNaturalGasRelativeDensity()+"",userInfo.getLanguageName()));
									downStatusMap.put("SaturationPressure", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_SaturationPressure",pcpProductionData.getFluidPVT().getSaturationPressure()+"",userInfo.getLanguageName()));
									
									downStatusMap.put("ReservoirDepth", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirDepth",pcpProductionData.getReservoir().getDepth()+"",userInfo.getLanguageName()));
									downStatusMap.put("ReservoirTemperature", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirTemperature",pcpProductionData.getReservoir().getTemperature()+"",userInfo.getLanguageName()));
									downStatusMap.put("ReservoirDepth_cbm", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirDepth_cbm",pcpProductionData.getReservoir().getDepth()+"",userInfo.getLanguageName()));
									downStatusMap.put("ReservoirTemperature_cbm", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirTemperature_cbm",pcpProductionData.getReservoir().getTemperature()+"",userInfo.getLanguageName()));
									
									downStatusMap.put("TubingPressure", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_TubingPressure",pcpProductionData.getProduction().getTubingPressure()+"",userInfo.getLanguageName()));
									downStatusMap.put("TubingPressure_cbm", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_TubingPressure_cbm",pcpProductionData.getProduction().getTubingPressure()+"",userInfo.getLanguageName()));
									downStatusMap.put("CasingPressure", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_CasingPressure",pcpProductionData.getProduction().getCasingPressure()+"",userInfo.getLanguageName()));
									downStatusMap.put("WellHeadTemperature", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_WellHeadTemperature",pcpProductionData.getProduction().getWellHeadTemperature()+"",userInfo.getLanguageName()));
									downStatusMap.put("WaterCut", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_WaterCut",pcpProductionData.getProduction().getWaterCut()+"",userInfo.getLanguageName()));
									downStatusMap.put("ProductionGasOilRatio", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_ProductionGasOilRatio",pcpProductionData.getProduction().getProductionGasOilRatio()+"",userInfo.getLanguageName()));
									downStatusMap.put("ProducingfluidLevel", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_ProducingfluidLevel",pcpProductionData.getProduction().getProducingfluidLevel()+"",userInfo.getLanguageName()));
									downStatusMap.put("PumpSettingDepth", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PumpSettingDepth",pcpProductionData.getProduction().getPumpSettingDepth()+"",userInfo.getLanguageName()));
							
									
									downStatusMap.put("TubingStringInsideDiameter", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_TubingStringInsideDiameter",pcpProductionData.getTubingString().getEveryTubing().get(0).getInsideDiameter()*1000+"",userInfo.getLanguageName()));
									downStatusMap.put("CasingStringOutsideDiameter", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_CasingStringOutsideDiameter",pcpProductionData.getCasingString().getEveryCasing().get(0).getInsideDiameter()*1000+"",userInfo.getLanguageName()));
									
									for(int i=0;i<pcpProductionData.getRodString().getEveryRod().size();i++){
										downStatusMap.put("RodStringType"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringType"+(i+1),pcpProductionData.getRodString().getEveryRod().get(i).getType()+"",userInfo.getLanguageName()));
										int rodGrade=0;
										if("A".equalsIgnoreCase(pcpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=1;
										}if("B".equalsIgnoreCase(pcpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=2;
										}if("C".equalsIgnoreCase(pcpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=3;
										}if("K".equalsIgnoreCase(pcpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=4;
										}if("D".equalsIgnoreCase(pcpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=5;
										}if("KD".equalsIgnoreCase(pcpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=6;
										}if("HL".equalsIgnoreCase(pcpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=7;
										}if("HY".equalsIgnoreCase(pcpProductionData.getRodString().getEveryRod().get(i).getGrade())){
											rodGrade=8;
										}
										downStatusMap.put("RodGrade"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RodGrade"+(i+1),rodGrade+"",userInfo.getLanguageName()));
										downStatusMap.put("RodStringOutsideDiameter"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringOutsideDiameter"+(i+1),pcpProductionData.getRodString().getEveryRod().get(i).getOutsideDiameter()*1000+"",userInfo.getLanguageName()));
										downStatusMap.put("RodStringInsideDiameter"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringInsideDiameter"+(i+1),pcpProductionData.getRodString().getEveryRod().get(i).getInsideDiameter()*1000+"",userInfo.getLanguageName()));
										downStatusMap.put("RodStringLength"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringLength"+(i+1),pcpProductionData.getRodString().getEveryRod().get(i).getLength()+"",userInfo.getLanguageName()));
									}
									
									downStatusMap.put("NetGrossRatio", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_NetGrossRatio",pcpProductionData.getManualIntervention().getNetGrossRatio()+"",userInfo.getLanguageName()));
									downStatusMap.put("NetGrossValue", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_NetGrossValue",pcpProductionData.getManualIntervention().getNetGrossValue()+"",userInfo.getLanguageName()));
									
									this.wellInformationManagerService.saveProductionData(StringManagerUtils.stringToInteger(deviceId),gson.toJson(pcpProductionData),StringManagerUtils.stringToInteger(deviceCalculateDataType),StringManagerUtils.stringToInteger(applicationScenarios));
								}
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
						}else{
							jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>"+languageResourceMap.get("deviceOffline")+"</font>'}";
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
	
	@RequestMapping("/devicePumpingUnitDataDownlink")
	public String devicePumpingUnitDataDownlink() throws Exception {
		String deviceId = request.getParameter("deviceId");
		String manufacturer = request.getParameter("manufacturer");
		String model = request.getParameter("model");
		String stroke = request.getParameter("stroke");
		String balanceInfo = request.getParameter("balanceInfo");
		
		String jsonLogin = "";
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		// 用户不存在
		if (null != userInfo) {
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo.getLanguageName());
			if (StringManagerUtils.isNotNull(deviceId)) {
				this.wellInformationManagerService.saveSRPPumpingModel(StringManagerUtils.stringToInteger(deviceId),manufacturer,model);
				this.wellInformationManagerService.savePumpingInfo(StringManagerUtils.stringToInteger(deviceId),stroke,balanceInfo);
				String sql = "select t3.protocol,t.tcpType, t.signinid,t.ipport,to_number(t.slave),t.deviceType,t4.commstatus" +
					    " from tbl_device t" +
					    " left outer join tbl_protocolinstance t2 on t.instancecode=t2.code" +
					    " left outer join tbl_acq_unit_conf t3 on t2.unitid=t3.id" +
					    " left outer join tbl_acqdata_latest t4 on t4.deviceid=t.id" +
					    " where t.id=" + deviceId;
				List<?> list = this.service.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[]) list.get(0);
					String protocolName=obj[0]+"";
					String tcpType=obj[1]+"";
					String signinid=obj[2]+"";
					String ipPort=obj[3]+"";
					String slave=obj[4]+"";
					String deviceType=obj[5]+"";
					int commStatus = StringManagerUtils.stringToInteger(obj[6] + "");
					ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
					if(protocol!=null && StringManagerUtils.isNotNull(tcpType) && (StringManagerUtils.isNotNull(signinid)||StringManagerUtils.isNotNull(ipPort) )  &&StringManagerUtils.isNotNull(slave)){
						if (commStatus > 0) {
							Gson gson = new Gson();
							java.lang.reflect.Type type=null;
							Map<String,String> downStatusMap=new LinkedHashMap<>();
							
							FSDiagramConstructionRequestData requestData=new FSDiagramConstructionRequestData();
							if(requestData!=null){
								String auxiliaryDeviceSql="select t.id,t3.id as auxiliarydeviceid,t3.manufacturer,t3.model,t4.itemcode,t4.itemvalue,"
										+ " t.stroke,t.balanceinfo,"
										+ " t3.prtf "
										+ " from tbl_device t,tbl_auxiliary2master t2,tbl_auxiliarydevice t3,tbl_auxiliarydeviceaddinfo t4 "
										+ " where t.id=t2.masterid and t2.auxiliaryid=t3.id and t3.id=t4.deviceid "
										+ " and t3.specifictype=1"
										+ " and t.id="+deviceId;
								List<?> auxiliaryDeviceList = this.service.findCallSql(auxiliaryDeviceSql);
								List<AuxiliaryDeviceAddInfo> auxiliaryDeviceAddInfoList=new ArrayList<>();
								String prtfStr="";
								PumpingPRTFData pumpingPRTFData=null;
								int PRTFPointCount=0;
								List<String> crankAngleList=new ArrayList<>();
								List<String> PRList=new ArrayList<>();
								List<String> TFList=new ArrayList<>();
								
								
								for(int i=0;i<auxiliaryDeviceList.size();i++){
									Object[] auxiliaryDeviceObj=(Object[]) auxiliaryDeviceList.get(i);
									AuxiliaryDeviceAddInfo auxiliaryDeviceAddInfo=new AuxiliaryDeviceAddInfo();
									auxiliaryDeviceAddInfo.setMasterId(StringManagerUtils.stringToInteger(auxiliaryDeviceObj[0]+""));
									auxiliaryDeviceAddInfo.setDeviceId(StringManagerUtils.stringToInteger(auxiliaryDeviceObj[1]+""));
									auxiliaryDeviceAddInfo.setManufacturer(auxiliaryDeviceObj[2]+"");
									auxiliaryDeviceAddInfo.setModel(auxiliaryDeviceObj[3]+"");
									auxiliaryDeviceAddInfo.setItemCode(auxiliaryDeviceObj[4]+"");
									auxiliaryDeviceAddInfo.setItemValue(auxiliaryDeviceObj[5]+"");
									auxiliaryDeviceAddInfoList.add(auxiliaryDeviceAddInfo);
									
									
									if(auxiliaryDeviceObj[8]!=null){
										SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(auxiliaryDeviceObj[8]);
										CLOB realClob = (CLOB) proxy.getWrappedClob(); 
										prtfStr=StringManagerUtils.CLOBtoString(realClob);
										
									}
								}
								
								type = new TypeToken<PumpingPRTFData>() {}.getType();
								pumpingPRTFData=gson.fromJson(prtfStr, type);
								
								if(pumpingPRTFData!=null && pumpingPRTFData.getList()!=null){
									for(int i=0;i<pumpingPRTFData.getList().size();i++){
										if(StringManagerUtils.stringToFloat(stroke)==pumpingPRTFData.getList().get(i).getStroke()){
											if(pumpingPRTFData.getList().get(i).getPRTF()!=null){
												PRTFPointCount=pumpingPRTFData.getList().get(i).getPRTF().size();
												for(PumpingPRTFData.PRTF prtf:pumpingPRTFData.getList().get(i).getPRTF()){
													crankAngleList.add(prtf.getCrankAngle()+"");
													PRList.add(prtf.getPR()+"");
													TFList.add(prtf.getTF()+"");
												}
											}
											break;
										}
									}
								}
								
								if(auxiliaryDeviceAddInfoList.size()>0){
									requestData.setPumpingUnit(new FSDiagramConstructionRequestData.PumpingUnit());
									String manufacturerStr="";
									String modelStr="";
									for(int i=0;i<auxiliaryDeviceAddInfoList.size();i++ ){
										manufacturerStr=auxiliaryDeviceAddInfoList.get(i).getManufacturer();
										modelStr=auxiliaryDeviceAddInfoList.get(i).getModel();
										if("structureType".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
											requestData.getPumpingUnit().setStructureType(StringManagerUtils.stringToInteger(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
										}else if("crankRotationDirection".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
											requestData.getPumpingUnit().setCrankRotationDirection(auxiliaryDeviceAddInfoList.get(i).getItemValue());
										}else if("offsetAngleOfCrank".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
											requestData.getPumpingUnit().setOffsetAngleOfCrank(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
										}else if("crankGravityRadius".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
											requestData.getPumpingUnit().setCrankGravityRadius(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
										}else if("singleCrankWeight".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
											requestData.getPumpingUnit().setSingleCrankWeight(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
										}else if("singleCrankPinWeight".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
											requestData.getPumpingUnit().setSingleCrankPinWeight(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
										}else if("structuralUnbalance".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
											requestData.getPumpingUnit().setStructuralUnbalance(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
										}
									}
									requestData.getPumpingUnit().setManufacturer(manufacturerStr);
									requestData.getPumpingUnit().setModel(modelStr);
									requestData.getPumpingUnit().setStroke(StringManagerUtils.stringToFloat(stroke));
									
									type = new TypeToken<FSDiagramConstructionRequestData.Balance>() {}.getType();
									FSDiagramConstructionRequestData.Balance balance=gson.fromJson(balanceInfo, type);
									if(balance!=null){
										requestData.getPumpingUnit().setBalance(balance);
									}
								}
								
								if(requestData.getPumpingUnit()!=null){
									downStatusMap.put("Stroke", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_Stroke",requestData.getPumpingUnit().getStroke()+"",userInfo.getLanguageName()));
									
									downStatusMap.put("structureType", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PumpingUnitStructure",requestData.getPumpingUnit().getStructureType()+"",userInfo.getLanguageName()));
									
									downStatusMap.put("CrankRotationDirection", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_CrankRotationDirection","Clockwise".equalsIgnoreCase(requestData.getPumpingUnit().getCrankRotationDirection())?"1":"0",userInfo.getLanguageName()));
									downStatusMap.put("OffsetAngleOfCrank", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_OffsetAngleOfCrank",requestData.getPumpingUnit().getOffsetAngleOfCrank()+"",userInfo.getLanguageName()));
									
									downStatusMap.put("CrankGravityRadius", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_CrankGravityRadius",requestData.getPumpingUnit().getCrankGravityRadius()+"",userInfo.getLanguageName()));
									downStatusMap.put("SingleCrankWeight", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_SingleCrankWeight",requestData.getPumpingUnit().getSingleCrankWeight()+"",userInfo.getLanguageName()));
									downStatusMap.put("SingleCrankPinWeight", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_SingleCrankPinWeight",requestData.getPumpingUnit().getSingleCrankPinWeight()+"",userInfo.getLanguageName()));
									downStatusMap.put("StructuralUnbalance", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_StructuralUnbalance",requestData.getPumpingUnit().getStructuralUnbalance()+"",userInfo.getLanguageName()));
									
									if(requestData.getPumpingUnit().getBalance()!=null && requestData.getPumpingUnit().getBalance().getEveryBalance()!=null){
										for(int i=0;i<requestData.getPumpingUnit().getBalance().getEveryBalance().size();i++){
											downStatusMap.put("positionAndWeight"+(i+1), 
													dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_BalancePosition"+(i+1),requestData.getPumpingUnit().getBalance().getEveryBalance().get(i).getPosition()+"",userInfo.getLanguageName())
													+"/"
													+dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_BalanceWeight"+(i+1),requestData.getPumpingUnit().getBalance().getEveryBalance().get(i).getWeight()+"",userInfo.getLanguageName())
													);
										}
									}
									
									downStatusMap.put("PRTFPointCount", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PRTFPointCount",PRTFPointCount+"",userInfo.getLanguageName()));
									
									downStatusMap.put("CrankAngle", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_CrankAngle",StringUtils.join(crankAngleList, ","),userInfo.getLanguageName()));
									downStatusMap.put("PR", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PR",StringUtils.join(PRList, ","),userInfo.getLanguageName()));
									downStatusMap.put("TF", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_TF",StringUtils.join(TFList, ","),userInfo.getLanguageName()));
								}
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
						} else {
						    jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>" + languageResourceMap.get("deviceOffline") + "</font>'}";
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
	
	@RequestMapping("/deviceFSDiagramConstructionDataDownlink")
	public String deviceFSDiagramConstructionDataDownlink() throws Exception {
		String deviceId = request.getParameter("deviceId");
		String data = request.getParameter("data");
		
		String jsonLogin = "";
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		// 用户不存在
		if (null != userInfo) {
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo.getLanguageName());
			if (StringManagerUtils.isNotNull(deviceId)) {
				String sql = "select t3.protocol,t.tcpType, t.signinid,t.ipport,to_number(t.slave),t.deviceType,t4.commstatus" +
					    " from tbl_device t" +
					    " left outer join tbl_protocolinstance t2 on t.instancecode=t2.code" +
					    " left outer join tbl_acq_unit_conf t3 on t2.unitid=t3.id" +
					    " left outer join tbl_acqdata_latest t4 on t4.deviceid=t.id" +
					    " where t.id=" + deviceId;
				List<?> list = this.service.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[]) list.get(0);
					String protocolName=obj[0]+"";
					String tcpType=obj[1]+"";
					String signinid=obj[2]+"";
					String ipPort=obj[3]+"";
					String slave=obj[4]+"";
					String deviceType=obj[5]+"";
					int commStatus = StringManagerUtils.stringToInteger(obj[6] + "");
					ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
					if(protocol!=null && StringManagerUtils.isNotNull(tcpType) && (StringManagerUtils.isNotNull(signinid)||StringManagerUtils.isNotNull(ipPort) )  &&StringManagerUtils.isNotNull(slave)){
						if (commStatus > 0) {
							Gson gson = new Gson();
							java.lang.reflect.Type type=null;
							Map<String,String> downStatusMap=new LinkedHashMap<>();
							type = new TypeToken<FSDiagramConstructionRequestData>() {}.getType();
							FSDiagramConstructionRequestData requestData=gson.fromJson(data, type);
							if(requestData!=null){
								
								String auxiliaryDeviceSql="select t.id,t3.id as auxiliarydeviceid,t3.manufacturer,t3.model,t4.itemcode,t4.itemvalue,"
										+ " t.stroke,t.balanceinfo,"
										+ " t3.prtf "
										+ " from tbl_device t,tbl_auxiliary2master t2,tbl_auxiliarydevice t3,tbl_auxiliarydeviceaddinfo t4 "
										+ " where t.id=t2.masterid and t2.auxiliaryid=t3.id and t3.id=t4.deviceid "
										+ " and t3.specifictype=1"
										+ " and t.id="+deviceId;
								List<?> auxiliaryDeviceList = this.service.findCallSql(auxiliaryDeviceSql);
								List<AuxiliaryDeviceAddInfo> auxiliaryDeviceAddInfoList=new ArrayList<>();
								String balanceInfo="";
								float stroke=0;
								String prtfStr="";
								PumpingPRTFData pumpingPRTFData=null;
								int PRTFPointCount=0;
								List<String> crankAngleList=new ArrayList<>();
								List<String> PRList=new ArrayList<>();
								List<String> TFList=new ArrayList<>();
								
								
								for(int i=0;i<auxiliaryDeviceList.size();i++){
									Object[] auxiliaryDeviceObj=(Object[]) auxiliaryDeviceList.get(i);
									AuxiliaryDeviceAddInfo auxiliaryDeviceAddInfo=new AuxiliaryDeviceAddInfo();
									auxiliaryDeviceAddInfo.setMasterId(StringManagerUtils.stringToInteger(auxiliaryDeviceObj[0]+""));
									auxiliaryDeviceAddInfo.setDeviceId(StringManagerUtils.stringToInteger(auxiliaryDeviceObj[1]+""));
									auxiliaryDeviceAddInfo.setManufacturer(auxiliaryDeviceObj[2]+"");
									auxiliaryDeviceAddInfo.setModel(auxiliaryDeviceObj[3]+"");
									auxiliaryDeviceAddInfo.setItemCode(auxiliaryDeviceObj[4]+"");
									auxiliaryDeviceAddInfo.setItemValue(auxiliaryDeviceObj[5]+"");
									auxiliaryDeviceAddInfoList.add(auxiliaryDeviceAddInfo);
									
									stroke=StringManagerUtils.stringToFloat(auxiliaryDeviceObj[6]+"");
									balanceInfo=auxiliaryDeviceObj[7]+"";
									
									if(auxiliaryDeviceObj[8]!=null){
										SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(auxiliaryDeviceObj[8]);
										CLOB realClob = (CLOB) proxy.getWrappedClob(); 
										prtfStr=StringManagerUtils.CLOBtoString(realClob);
										
									}
								}
								
								type = new TypeToken<PumpingPRTFData>() {}.getType();
								pumpingPRTFData=gson.fromJson(prtfStr, type);
								
								if(pumpingPRTFData!=null && pumpingPRTFData.getList()!=null){
									for(int i=0;i<pumpingPRTFData.getList().size();i++){
										if(stroke==pumpingPRTFData.getList().get(i).getStroke()){
											if(pumpingPRTFData.getList().get(i).getPRTF()!=null){
												PRTFPointCount=pumpingPRTFData.getList().get(i).getPRTF().size();
												for(PumpingPRTFData.PRTF prtf:pumpingPRTFData.getList().get(i).getPRTF()){
													crankAngleList.add(prtf.getCrankAngle()+"");
													PRList.add(prtf.getPR()+"");
													TFList.add(prtf.getTF()+"");
												}
											}
											break;
										}
									}
								}
								
								if(auxiliaryDeviceAddInfoList.size()>0){
									requestData.setPumpingUnit(new FSDiagramConstructionRequestData.PumpingUnit());
									String manufacturer="";
									String model="";
									for(int i=0;i<auxiliaryDeviceAddInfoList.size();i++ ){
										manufacturer=auxiliaryDeviceAddInfoList.get(i).getManufacturer();
										model=auxiliaryDeviceAddInfoList.get(i).getModel();
										if("structureType".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
											requestData.getPumpingUnit().setStructureType(StringManagerUtils.stringToInteger(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
										}else if("crankRotationDirection".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
											requestData.getPumpingUnit().setCrankRotationDirection(auxiliaryDeviceAddInfoList.get(i).getItemValue());
										}else if("offsetAngleOfCrank".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
											requestData.getPumpingUnit().setOffsetAngleOfCrank(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
										}else if("crankGravityRadius".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
											requestData.getPumpingUnit().setCrankGravityRadius(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
										}else if("singleCrankWeight".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
											requestData.getPumpingUnit().setSingleCrankWeight(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
										}else if("singleCrankPinWeight".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
											requestData.getPumpingUnit().setSingleCrankPinWeight(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
										}else if("structuralUnbalance".equalsIgnoreCase(auxiliaryDeviceAddInfoList.get(i).getItemCode())){
											requestData.getPumpingUnit().setStructuralUnbalance(StringManagerUtils.stringToFloat(auxiliaryDeviceAddInfoList.get(i).getItemValue()));
										}
									}
									requestData.getPumpingUnit().setManufacturer(manufacturer);
									requestData.getPumpingUnit().setModel(model);
									requestData.getPumpingUnit().setStroke(stroke);
									
									type = new TypeToken<FSDiagramConstructionRequestData.Balance>() {}.getType();
									FSDiagramConstructionRequestData.Balance balance=gson.fromJson(balanceInfo, type);
									if(balance!=null){
										requestData.getPumpingUnit().setBalance(balance);
									}
								}
								
								downStatusMap.put("crankDIInitAngle", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_CrankDIInitAngle",requestData.getCrankDIInitAngle()+"",userInfo.getLanguageName()));
								downStatusMap.put("interpolationCNT", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_InterpolationCNT",requestData.getInterpolationCNT()+"",userInfo.getLanguageName()));
								downStatusMap.put("surfaceSystemEfficiency", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_SurfaceSystemEfficiency",requestData.getSurfaceSystemEfficiency()+"",userInfo.getLanguageName()));
								downStatusMap.put("wattTimes", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_WattTimes",requestData.getWattTimes()+"",userInfo.getLanguageName()));
								downStatusMap.put("iTimes", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_ITimes",requestData.getITimes()+"",userInfo.getLanguageName()));
								downStatusMap.put("fsDiagramTimes", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_FSDiagramTimes",requestData.getFSDiagramTimes()+"",userInfo.getLanguageName()));
								downStatusMap.put("fsDiagramLeftTimes", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_FSDiagramLeftTimes",requestData.getFSDiagramLeftTimes()+"",userInfo.getLanguageName()));
								downStatusMap.put("fsDiagramRightTimes", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_FSDiagramRightTimes",requestData.getFSDiagramRightTimes()+"",userInfo.getLanguageName()));
								
								downStatusMap.put("leftPercent", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_LeftPercent",requestData.getLeftPercent()+"",userInfo.getLanguageName()));
								downStatusMap.put("rightPercent", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_RightPercent",requestData.getRightPercent()+"",userInfo.getLanguageName()));
								downStatusMap.put("positiveXWatt", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PositiveXWatt",requestData.getPositiveXWatt()+"",userInfo.getLanguageName()));
								downStatusMap.put("negativeXWatt", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_NegativeXWatt",requestData.getNegativeXWatt()+"",userInfo.getLanguageName()));
								
								
								if(requestData.getPumpingUnit()!=null){
									downStatusMap.put("Stroke", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_Stroke",requestData.getPumpingUnit().getStroke()+"",userInfo.getLanguageName()));
									
									downStatusMap.put("PumpingUnitStructure", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PumpingUnitStructure",requestData.getPumpingUnit().getStructureType()+"",userInfo.getLanguageName()));
									
									downStatusMap.put("CrankRotationDirection", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_CrankRotationDirection","Clockwise".equalsIgnoreCase(requestData.getPumpingUnit().getCrankRotationDirection())?"1":"0",userInfo.getLanguageName()));
									downStatusMap.put("OffsetAngleOfCrank", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_OffsetAngleOfCrank",requestData.getPumpingUnit().getOffsetAngleOfCrank()+"",userInfo.getLanguageName()));
									
									downStatusMap.put("CrankGravityRadius", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_CrankGravityRadius",requestData.getPumpingUnit().getCrankGravityRadius()+"",userInfo.getLanguageName()));
									downStatusMap.put("SingleCrankWeight", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_SingleCrankWeight",requestData.getPumpingUnit().getSingleCrankWeight()+"",userInfo.getLanguageName()));
									downStatusMap.put("SingleCrankPinWeight", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_SingleCrankPinWeight",requestData.getPumpingUnit().getSingleCrankPinWeight()+"",userInfo.getLanguageName()));
									downStatusMap.put("StructuralUnbalance", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_StructuralUnbalance",requestData.getPumpingUnit().getStructuralUnbalance()+"",userInfo.getLanguageName()));
									
									if(requestData.getPumpingUnit().getBalance()!=null && requestData.getPumpingUnit().getBalance().getEveryBalance()!=null){
										for(int i=0;i<requestData.getPumpingUnit().getBalance().getEveryBalance().size();i++){
											downStatusMap.put("BalanceWeight"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_BalanceWeight"+(i+1),requestData.getPumpingUnit().getBalance().getEveryBalance().get(i).getWeight()+"",userInfo.getLanguageName()));
											downStatusMap.put("BalancePosition"+(i+1), dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_BalancePosition"+(i+1),requestData.getPumpingUnit().getBalance().getEveryBalance().get(i).getPosition()+"",userInfo.getLanguageName()));
										}
									}
									
									downStatusMap.put("PRTFPointCount", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PRTFPointCount",PRTFPointCount+"",userInfo.getLanguageName()));
									
									downStatusMap.put("CrankAngle", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_CrankAngle",StringUtils.join(crankAngleList, ","),userInfo.getLanguageName()));
									downStatusMap.put("PR", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PR",StringUtils.join(PRList, ","),userInfo.getLanguageName()));
									downStatusMap.put("TF", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_TF",StringUtils.join(TFList, ","),userInfo.getLanguageName()));
								}
								
								downStatusMap.put("PRTFSrc", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_PRTFSrc",requestData.getPRTFSrc()+"",userInfo.getLanguageName()));
								downStatusMap.put("BoardDataSource", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_BoardDataSource",requestData.getBoardDataSource()+"",userInfo.getLanguageName()));
								
								this.wellInformationManagerService.saveFSDiagramConstructionData(StringManagerUtils.stringToInteger(deviceId), data);
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
						} else {
						    jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>" + languageResourceMap.get("deviceOffline") + "</font>'}";
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
	
	@RequestMapping("/deviceSystemParameterDataDownlink")
	public String deviceSystemParameterDataDownlink() throws Exception {
		String deviceId = request.getParameter("deviceId");
		
		String jsonLogin = "";
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		// 用户不存在
		if (null != userInfo) {
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo.getLanguageName());
			if (StringManagerUtils.isNotNull(deviceId)) {
				String sql = "select t3.protocol,t.tcpType, t.signinid,t.ipport,to_number(t.slave),t.deviceType,t4.commstatus" +
					    " from tbl_device t" +
					    " left outer join tbl_protocolinstance t2 on t.instancecode=t2.code" +
					    " left outer join tbl_acq_unit_conf t3 on t2.unitid=t3.id" +
					    " left outer join tbl_acqdata_latest t4 on t4.deviceid=t.id" +
					    " where t.id=" + deviceId;
				List<?> list = this.service.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[]) list.get(0);
					String protocolName=obj[0]+"";
					String tcpType=obj[1]+"";
					String signinid=obj[2]+"";
					String ipPort=obj[3]+"";
					String slave=obj[4]+"";
					String deviceType=obj[5]+"";
					int commStatus = StringManagerUtils.stringToInteger(obj[6] + "");
					ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
					if(protocol!=null && StringManagerUtils.isNotNull(tcpType) && (StringManagerUtils.isNotNull(signinid)||StringManagerUtils.isNotNull(ipPort) )  &&StringManagerUtils.isNotNull(slave)){
						if (commStatus > 0) {
							Map<String,String> downStatusMap=new LinkedHashMap<>();
							downStatusMap.put("systemDate", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_SystemDate",StringManagerUtils.getCurrentTime("yyyy00MM00dd"),userInfo.getLanguageName()));
							downStatusMap.put("systemTime", dataDownlink(protocolName,tcpType,signinid,ipPort,slave,"write_SystemTime",StringManagerUtils.getCurrentTime("00HH00mm00ss"),userInfo.getLanguageName()));
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
						} else {
						    jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>" + languageResourceMap.get("deviceOffline") + "</font>'}";
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
	
	public String dataDownlink(String protocolName,String tcpType,String signinid,String ipPort,String Slave,String calColumn,String writeValue,String language){
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		DataMapping dataMapping=MemoryDataManagerTask.getDataMappingByCalColumn(calColumn);
		String status=languageResourceMap.get("noDownlink");
		int reslut=0;
		if(dataMapping!=null && dataMapping.getCalculateEnable()==1){
			reslut=deviceControlOperation_Mdubus(protocolName,tcpType,signinid,ipPort,Slave,dataMapping.getMappingColumn(),writeValue);
			if(reslut==1){
				status=languageResourceMap.get("downlinkSuccessfully");
			}else{
				status=languageResourceMap.get("downlinkFailed");
			}
		}
		return status;
	}
	
	public int deviceControlOperation_Mdubus(String protocolName,String tcpType,String ID,String ipPort,String Slave,String itemCode,String controlValue){
		int result=-1;
		try {
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
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
			String itemName="";
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
					
					itemName=protocol.getItems().get(i).getTitle();
					
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
				StringManagerUtils.printLog(itemName+":"+ctrlJson);
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
			e.printStackTrace();
		}
		return result;
	}
	
	
	@RequestMapping("/deviceProductionDataUplink")
	public String deviceProductionDataUplink() throws Exception {
		String deviceId = request.getParameter("deviceId");
		String deviceCalculateDataType = request.getParameter("deviceCalculateDataType");
		
		String jsonLogin = "";
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		// 用户不存在
		if (null != userInfo) {
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo.getLanguageName());
			if (StringManagerUtils.isNotNull(deviceId)) {
				String sql = "select t3.protocol,t.tcpType, t.signinid,t.ipport,to_number(t.slave),t.deviceType,t4.commstatus" +
					    " from tbl_device t" +
					    " left outer join tbl_protocolinstance t2 on t.instancecode=t2.code" +
					    " left outer join tbl_acq_unit_conf t3 on t2.unitid=t3.id" +
					    " left outer join tbl_acqdata_latest t4 on t4.deviceid=t.id" +
					    " where t.id=" + deviceId;
				List<?> list = this.service.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[]) list.get(0);
					String protocolName=obj[0]+"";
					String tcpType=obj[1]+"";
					String signinid=obj[2]+"";
					String ipPort=obj[3]+"";
					String slave=obj[4]+"";
					int commStatus = StringManagerUtils.stringToInteger(obj[6] + "");
					ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
					if(protocol!=null && StringManagerUtils.isNotNull(tcpType) && (StringManagerUtils.isNotNull(signinid)||StringManagerUtils.isNotNull(ipPort) )  &&StringManagerUtils.isNotNull(slave)){
						if (commStatus > 0) {

							Map<String,String> statusMap=new LinkedHashMap<>();
							if(StringManagerUtils.stringToInteger(deviceCalculateDataType)==1){
								statusMap.put("CrudeOilDensity", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_CrudeOilDensity",userInfo.getLanguageName()));
								statusMap.put("WaterDensity", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_WaterDensity",userInfo.getLanguageName()));
								statusMap.put("NaturalGasRelativeDensity", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_NaturalGasRelativeDensity",userInfo.getLanguageName()));
								statusMap.put("SaturationPressure", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_SaturationPressure",userInfo.getLanguageName()));
								
								statusMap.put("ReservoirDepth", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirDepth",userInfo.getLanguageName()));
								statusMap.put("ReservoirTemperature", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirTemperature",userInfo.getLanguageName()));
								statusMap.put("ReservoirDepth_cbm", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirDepth_cbm",userInfo.getLanguageName()));
								statusMap.put("ReservoirTemperature_cbm", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirTemperature_cbm",userInfo.getLanguageName()));
								
								statusMap.put("TubingPressure", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_TubingPressure",userInfo.getLanguageName()));
								statusMap.put("TubingPressure_cbm", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_TubingPressure_cbm",userInfo.getLanguageName()));
								statusMap.put("CasingPressure", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_CasingPressure",userInfo.getLanguageName()));
								statusMap.put("WellHeadTemperature", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_WellHeadTemperature",userInfo.getLanguageName()));
								statusMap.put("WaterCut", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_WaterCut",userInfo.getLanguageName()));
								statusMap.put("ProductionGasOilRatio", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_ProductionGasOilRatio",userInfo.getLanguageName()));
								statusMap.put("ProducingfluidLevel", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_ProducingfluidLevel",userInfo.getLanguageName()));
								statusMap.put("PumpSettingDepth", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_PumpSettingDepth",userInfo.getLanguageName()));
								
								
								String barrelType=dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_BarrelType",userInfo.getLanguageName());
								if("0".equalsIgnoreCase(barrelType)){
									barrelType="L";
								}else if("1".equalsIgnoreCase(barrelType)){
									barrelType="H";
								}
								statusMap.put("BarrelType", barrelType);
								
								statusMap.put("PumpGrade", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_PumpGrade",userInfo.getLanguageName()));
								statusMap.put("PumpBoreDiameter", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_PumpBoreDiameter",userInfo.getLanguageName()));
								statusMap.put("PlungerLength", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_PlungerLength",userInfo.getLanguageName()));
								
								
								statusMap.put("TubingStringInsideDiameter", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_TubingStringInsideDiameter",userInfo.getLanguageName()));
								statusMap.put("CasingStringOutsideDiameter", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_CasingStringOutsideDiameter",userInfo.getLanguageName()));
								
								for(int i=0;i<4;i++){
									statusMap.put("RodStringType"+(i+1), dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringType"+(i+1),userInfo.getLanguageName()));
									statusMap.put("RodGrade"+(i+1), dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_RodGrade"+(i+1),userInfo.getLanguageName()));
									statusMap.put("RodStringOutsideDiameter"+(i+1), dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringOutsideDiameter"+(i+1),userInfo.getLanguageName()));
									statusMap.put("RodStringInsideDiameter"+(i+1), dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringInsideDiameter"+(i+1),userInfo.getLanguageName()));
									statusMap.put("RodStringLength"+(i+1), dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringLength"+(i+1),userInfo.getLanguageName()));
								}
								
								
								String ManualInterventionCode=dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_ManualInterventionCode",userInfo.getLanguageName());
								WorkType w=MemoryDataManagerTask.getWorkTypeByCode(ManualInterventionCode, userInfo.getLanguageName());
								statusMap.put("ManualInterventionCode", w!=null?w.getResultName():ManualInterventionCode);
								
								statusMap.put("NetGrossRatio", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_NetGrossRatio",userInfo.getLanguageName()));
								statusMap.put("NetGrossValue", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_NetGrossValue",userInfo.getLanguageName()));
								statusMap.put("LevelCorrectValue", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_LevelCorrectValue",userInfo.getLanguageName()));
							}else if(StringManagerUtils.stringToInteger(deviceCalculateDataType)==2){
								statusMap.put("CrudeOilDensity", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_CrudeOilDensity",userInfo.getLanguageName()));
								statusMap.put("WaterDensity", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_WaterDensity",userInfo.getLanguageName()));
								statusMap.put("NaturalGasRelativeDensity", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_NaturalGasRelativeDensity",userInfo.getLanguageName()));
								statusMap.put("SaturationPressure", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_SaturationPressure",userInfo.getLanguageName()));
								
								statusMap.put("ReservoirDepth", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirDepth",userInfo.getLanguageName()));
								statusMap.put("ReservoirTemperature", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirTemperature",userInfo.getLanguageName()));
								statusMap.put("ReservoirDepth_cbm", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirDepth_cbm",userInfo.getLanguageName()));
								statusMap.put("ReservoirTemperature_cbm", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_ReservoirTemperature_cbm",userInfo.getLanguageName()));
								
								statusMap.put("TubingPressure", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_TubingPressure",userInfo.getLanguageName()));
								statusMap.put("TubingPressure_cbm", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_TubingPressure_cbm",userInfo.getLanguageName()));
								statusMap.put("CasingPressure", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_CasingPressure",userInfo.getLanguageName()));
								statusMap.put("WellHeadTemperature", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_WellHeadTemperature",userInfo.getLanguageName()));
								statusMap.put("WaterCut", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_WaterCut",userInfo.getLanguageName()));
								statusMap.put("ProductionGasOilRatio", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_ProductionGasOilRatio",userInfo.getLanguageName()));
								statusMap.put("ProducingfluidLevel", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_ProducingfluidLevel",userInfo.getLanguageName()));
								statusMap.put("PumpSettingDepth", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_PumpSettingDepth",userInfo.getLanguageName()));
								
								statusMap.put("TubingStringInsideDiameter", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_TubingStringInsideDiameter",userInfo.getLanguageName()));
								statusMap.put("CasingStringOutsideDiameter", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_CasingStringOutsideDiameter",userInfo.getLanguageName()));
								
								for(int i=0;i<4;i++){
									statusMap.put("RodStringType"+(i+1), dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringType"+(i+1),userInfo.getLanguageName()));
									statusMap.put("RodGrade"+(i+1), dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_RodGrade"+(i+1),userInfo.getLanguageName()));
									statusMap.put("RodStringOutsideDiameter"+(i+1), dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringOutsideDiameter"+(i+1),userInfo.getLanguageName()));
									statusMap.put("RodStringInsideDiameter"+(i+1), dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringInsideDiameter"+(i+1),userInfo.getLanguageName()));
									statusMap.put("RodStringLength"+(i+1), dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_RodStringLength"+(i+1),userInfo.getLanguageName()));
								}
								
								statusMap.put("NetGrossRatio", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_NetGrossRatio",userInfo.getLanguageName()));
								statusMap.put("NetGrossValue", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_NetGrossValue",userInfo.getLanguageName()));
							}
							
							StringBuffer result_json = new StringBuffer();
							result_json.append("{\"success\":true,\"flag\":true,\"error\":true,\"msg\":\"<font color=blue>"+languageResourceMap.get("commandExecutedSuccessfully")+"</font>\",\"downStatusList\":[");
							for (String key : statusMap.keySet()) {
								result_json.append("{\"key\":\""+key+"\",\"status\":\""+statusMap.get(key)+"\"},");
					        }
							if(result_json.toString().endsWith(",")){
								result_json.deleteCharAt(result_json.length() - 1);
							}
							result_json.append("]}");
							jsonLogin=result_json.toString();
						} else {
						    jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>" + languageResourceMap.get("deviceOffline") + "</font>'}";
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
	
	@RequestMapping("/devicePumpingUnitDataUplink")
	public String devicePumpingUnitDataUplink() throws Exception {
		String deviceId = request.getParameter("deviceId");
		
		String jsonLogin = "";
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		// 用户不存在
		if (null != userInfo) {
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo.getLanguageName());
			if (StringManagerUtils.isNotNull(deviceId)) {
				String sql = "select t3.protocol,t.tcpType, t.signinid,t.ipport,to_number(t.slave),t.deviceType,t4.commstatus" +
					    " from tbl_device t" +
					    " left outer join tbl_protocolinstance t2 on t.instancecode=t2.code" +
					    " left outer join tbl_acq_unit_conf t3 on t2.unitid=t3.id" +
					    " left outer join tbl_acqdata_latest t4 on t4.deviceid=t.id" +
					    " where t.id=" + deviceId;
				List<?> list = this.service.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[]) list.get(0);
					String protocolName=obj[0]+"";
					String tcpType=obj[1]+"";
					String signinid=obj[2]+"";
					String ipPort=obj[3]+"";
					String slave=obj[4]+"";
					String deviceType=obj[5]+"";
					int commStatus = StringManagerUtils.stringToInteger(obj[6] + "");
					ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
					if(protocol!=null && StringManagerUtils.isNotNull(tcpType) && (StringManagerUtils.isNotNull(signinid)||StringManagerUtils.isNotNull(ipPort) )  &&StringManagerUtils.isNotNull(slave)){
						if (commStatus > 0) {
							Map<String,String> statusMap=new LinkedHashMap<>();
							statusMap.put("Stroke", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_Stroke",userInfo.getLanguageName()));
							statusMap.put("structureType", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_PumpingUnitStructure",userInfo.getLanguageName()));
							statusMap.put("CrankRotationDirection", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_CrankRotationDirection",userInfo.getLanguageName()));
							statusMap.put("OffsetAngleOfCrank", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_OffsetAngleOfCrank",userInfo.getLanguageName()));
							
							statusMap.put("CrankGravityRadius", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_CrankGravityRadius",userInfo.getLanguageName()));
							statusMap.put("SingleCrankWeight", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_SingleCrankWeight",userInfo.getLanguageName()));
							statusMap.put("SingleCrankPinWeight", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_SingleCrankPinWeight",userInfo.getLanguageName()));
							statusMap.put("StructuralUnbalance", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_StructuralUnbalance",userInfo.getLanguageName()));
							
							for(int i=0;i<8;i++){
								statusMap.put("positionAndWeight"+(i+1), 
										dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_BalancePosition"+(i+1),userInfo.getLanguageName())
										+"/"
										+dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_BalanceWeight"+(i+1),userInfo.getLanguageName()));
							}
							
							statusMap.put("PRTFPointCount", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_PRTFPointCount",userInfo.getLanguageName()));
							
							statusMap.put("CrankAngle", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_CrankAngle",userInfo.getLanguageName()));
							statusMap.put("PR", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_PR",userInfo.getLanguageName()));
							statusMap.put("TF", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_TF",userInfo.getLanguageName()));
							
							
							StringBuffer result_json = new StringBuffer();
							result_json.append("{\"success\":true,\"flag\":true,\"error\":true,\"msg\":\"<font color=blue>"+languageResourceMap.get("commandExecutedSuccessfully")+"</font>\",\"downStatusList\":[");
							for (String key : statusMap.keySet()) {
								result_json.append("{\"key\":\""+key+"\",\"status\":\""+statusMap.get(key)+"\"},");
					        }
							if(result_json.toString().endsWith(",")){
								result_json.deleteCharAt(result_json.length() - 1);
							}
							result_json.append("]}");
							jsonLogin=result_json.toString();
						} else {
						    jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>" + languageResourceMap.get("deviceOffline") + "</font>'}";
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
	
	
	@RequestMapping("/deviceFSDiagramConstructionDataUplink")
	public String deviceFSDiagramConstructionDataUplink() throws Exception {
		String deviceId = request.getParameter("deviceId");
		
		String jsonLogin = "";
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		// 用户不存在
		if (null != userInfo) {
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo.getLanguageName());
			if (StringManagerUtils.isNotNull(deviceId)) {
				String sql = "select t3.protocol,t.tcpType, t.signinid,t.ipport,to_number(t.slave),t.deviceType,t4.commstatus" +
					    " from tbl_device t" +
					    " left outer join tbl_protocolinstance t2 on t.instancecode=t2.code" +
					    " left outer join tbl_acq_unit_conf t3 on t2.unitid=t3.id" +
					    " left outer join tbl_acqdata_latest t4 on t4.deviceid=t.id" +
					    " where t.id=" + deviceId;
				List<?> list = this.service.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[]) list.get(0);
					String protocolName=obj[0]+"";
					String tcpType=obj[1]+"";
					String signinid=obj[2]+"";
					String ipPort=obj[3]+"";
					String slave=obj[4]+"";
					String deviceType=obj[5]+"";
					int commStatus = StringManagerUtils.stringToInteger(obj[6] + "");
					ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
					if(protocol!=null && StringManagerUtils.isNotNull(tcpType) && (StringManagerUtils.isNotNull(signinid)||StringManagerUtils.isNotNull(ipPort) )  &&StringManagerUtils.isNotNull(slave)){
						if (commStatus > 0) {
							Map<String,String> statusMap=new LinkedHashMap<>();
							statusMap.put("crankDIInitAngle", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_CrankDIInitAngle",userInfo.getLanguageName()));
							statusMap.put("interpolationCNT", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_InterpolationCNT",userInfo.getLanguageName()));
							statusMap.put("surfaceSystemEfficiency", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_SurfaceSystemEfficiency",userInfo.getLanguageName()));
							statusMap.put("wattTimes", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_WattTimes",userInfo.getLanguageName()));
							statusMap.put("iTimes", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_ITimes",userInfo.getLanguageName()));
							statusMap.put("fsDiagramTimes", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_FSDiagramTimes",userInfo.getLanguageName()));
							statusMap.put("fsDiagramLeftTimes", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_FSDiagramLeftTimes",userInfo.getLanguageName()));
							statusMap.put("fsDiagramRightTimes", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_FSDiagramRightTimes",userInfo.getLanguageName()));
							
							statusMap.put("leftPercent", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_LeftPercent",userInfo.getLanguageName()));
							statusMap.put("rightPercent", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_RightPercent",userInfo.getLanguageName()));
							statusMap.put("positiveXWatt", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_PositiveXWatt",userInfo.getLanguageName()));
							statusMap.put("negativeXWatt", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_NegativeXWatt",userInfo.getLanguageName()));
						
							statusMap.put("PRTFSrc", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_PRTFSrc",userInfo.getLanguageName()));
							statusMap.put("BoardDataSource", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_BoardDataSource",userInfo.getLanguageName()));
						
							
							StringBuffer result_json = new StringBuffer();
							result_json.append("{\"success\":true,\"flag\":true,\"error\":true,\"msg\":\"<font color=blue>"+languageResourceMap.get("commandExecutedSuccessfully")+"</font>\",\"downStatusList\":[");
							for (String key : statusMap.keySet()) {
								result_json.append("{\"key\":\""+key+"\",\"status\":\""+statusMap.get(key)+"\"},");
					        }
							if(result_json.toString().endsWith(",")){
								result_json.deleteCharAt(result_json.length() - 1);
							}
							result_json.append("]}");
							jsonLogin=result_json.toString();
						} else {
						    jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>" + languageResourceMap.get("deviceOffline") + "</font>'}";
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
	
	@RequestMapping("/deviceSystemParameterDataUplink")
	public String deviceSystemParameterDataUplink() throws Exception {
		String deviceId = request.getParameter("deviceId");
		
		String jsonLogin = "";
		User userInfo = (User) request.getSession().getAttribute("userLogin");
		
		String deviceTableName="tbl_device";
		// 用户不存在
		if (null != userInfo) {
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(userInfo.getLanguageName());
			if (StringManagerUtils.isNotNull(deviceId)) {
				String sql = "select t3.protocol,t.tcpType, t.signinid,t.ipport,to_number(t.slave),t.deviceType,t4.commstatus" +
					    " from tbl_device t" +
					    " left outer join tbl_protocolinstance t2 on t.instancecode=t2.code" +
					    " left outer join tbl_acq_unit_conf t3 on t2.unitid=t3.id" +
					    " left outer join tbl_acqdata_latest t4 on t4.deviceid=t.id" +
					    " where t.id=" + deviceId;
				List<?> list = this.service.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[]) list.get(0);
					String protocolName=obj[0]+"";
					String tcpType=obj[1]+"";
					String signinid=obj[2]+"";
					String ipPort=obj[3]+"";
					String slave=obj[4]+"";
					String deviceType=obj[5]+"";
					int commStatus = StringManagerUtils.stringToInteger(obj[6] + "");
					ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
					if(protocol!=null && StringManagerUtils.isNotNull(tcpType) && (StringManagerUtils.isNotNull(signinid)||StringManagerUtils.isNotNull(ipPort) )  &&StringManagerUtils.isNotNull(slave)){
						if (commStatus > 0) {
							Map<String,String> statusMap=new LinkedHashMap<>();
							statusMap.put("systemDate", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_SystemDate",userInfo.getLanguageName()));
							statusMap.put("systemTime", dataUplink(protocolName,tcpType,signinid,ipPort,slave,"write_SystemTime",userInfo.getLanguageName()));
							StringBuffer result_json = new StringBuffer();
							result_json.append("{\"success\":true,\"flag\":true,\"error\":true,\"msg\":\"<font color=blue>"+languageResourceMap.get("commandExecutedSuccessfully")+"</font>\",\"downStatusList\":[");
							for (String key : statusMap.keySet()) {
								result_json.append("{\"key\":\""+key+"\",\"status\":\""+statusMap.get(key)+"\"},");
					        }
							if(result_json.toString().endsWith(",")){
								result_json.deleteCharAt(result_json.length() - 1);
							}
							result_json.append("]}");
							jsonLogin=result_json.toString();
						} else {
						    jsonLogin = "{success:true,flag:true,error:false,msg:'<font color=red>" + languageResourceMap.get("deviceOffline") + "</font>'}";
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
	
	public String dataUplink(String protocolName,String tcpType,String signinid,String ipPort,String Slave,String calColumn,String language){
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		DataMapping dataMapping=MemoryDataManagerTask.getDataMappingByCalColumn(calColumn);
		String result=languageResourceMap.get("noUplink");
		if(dataMapping!=null && dataMapping.getCalculateEnable()==1){
			result=readAddr(protocolName,tcpType,signinid,ipPort,Slave,dataMapping.getMappingColumn(),language);
			
		}
		return result;
	}
	
	public String readAddr(String protocolName,String tcpType,String ID,String ipPort,String Slave,String itemCode,String language){
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String result=languageResourceMap.get("noUplink");
		try {
			Gson gson = new Gson();
			java.lang.reflect.Type type=null;
			
			DataMapping dataMapping=null;
			String col="";
			Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String readUrl=Config.getInstance().configFile.getAd().getRw().getReadAddr();
			
			ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByName(protocolName);
			ModbusProtocolConfig.Items item=null;
			for(int i=0;i<protocol.getItems().size();i++){
				if(loadProtocolMappingColumnByTitleMap.containsKey(protocol.getItems().get(i).getTitle())){
					dataMapping=loadProtocolMappingColumnByTitleMap.get(protocol.getItems().get(i).getTitle());
					col=dataMapping.getMappingColumn();
					
				}
				if(itemCode.equalsIgnoreCase(col)){
					item=protocol.getItems().get(i);
					
					break;
				}
			}
			String calColumn=dataMapping!=null?dataMapping.getCalColumn():"";
			String IDOrIPPortKey="ID";
			String IDOrIPPort=ID;
			if("TCPServer".equalsIgnoreCase(tcpType.replaceAll(" ", ""))){
				IDOrIPPortKey="IPPort";
				IDOrIPPort=ipPort;
				readUrl=Config.getInstance().configFile.getAd().getRw().getReadAddr_ipPort();
			}
			
			if(item!=null){
				String readJson="{"
						+ "\""+IDOrIPPortKey+"\":\""+IDOrIPPort+"\","
						+ "\"Slave\":"+Slave+","
						+ "\"Addr\":"+item.getAddr()+""
						+ "}";
				StringManagerUtils.printLog(item.getTitle()+":"+readJson);
				String responseStr="";
				responseStr=StringManagerUtils.sendPostMethod(readUrl, readJson,"utf-8",0,0);
				if(StringManagerUtils.isNotNull(responseStr)){
					type = new TypeToken<AcqAddrData>() {}.getType();
					AcqAddrData acqAddrData=gson.fromJson(responseStr, type);
					if(acqAddrData.getResultStatus()==1){
						result=StringManagerUtils.objectListToString(acqAddrData.getValue(), item);
						if(!"write_PumpGrade".equalsIgnoreCase(calColumn)){
							if(item.getResolutionMode()==1 && item.getMeaning()!=null && item.getMeaning().size()>0){//枚举量显示转换
								for(int i=0;i<item.getMeaning().size();i++){
									if(result.equalsIgnoreCase(item.getMeaning().get(i).getValue()+"")){
										result=item.getMeaning().get(i).getMeaning();
										break;
									}
								}
							}
						}
						
					}else{
						result=languageResourceMap.get("uplinkFailed");
					}
				}
			}
		} catch (Exception e) {
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
