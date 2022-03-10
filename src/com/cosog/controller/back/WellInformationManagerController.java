package com.cosog.controller.back;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.cosog.controller.base.BaseController;
import com.cosog.model.AcquisitionUnit;
import com.cosog.model.Org;
import com.cosog.model.PCPDeviceInformation;
import com.cosog.model.RPCDeviceInformation;
import com.cosog.model.SmsDeviceInformation;
import com.cosog.model.User;
import com.cosog.model.gridmodel.AuxiliaryDeviceConfig;
import com.cosog.model.gridmodel.AuxiliaryDeviceHandsontableChangedData;
import com.cosog.model.gridmodel.WellHandsontableChangedData;
import com.cosog.service.back.WellInformationManagerService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.utils.Constants;
import com.cosog.utils.Page;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wellInformationManagerController")
@Scope("prototype")
public class WellInformationManagerController extends BaseController {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(WellInformationManagerController.class);
	@Autowired
	private WellInformationManagerService<?> wellInformationManagerService;
	@Autowired
	private WellInformationManagerService<RPCDeviceInformation> rpcDeviceManagerService;
	@Autowired
	private WellInformationManagerService<PCPDeviceInformation> pcpDeviceManagerService;
	@Autowired
	private WellInformationManagerService<SmsDeviceInformation> smsDeviceManagerService;
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
		User user = null;
		HttpSession session=request.getSession();
		user = (User) session.getAttribute("userLogin");
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserorgids();
			}
		}
		this.wellInformationManagerService.changeDeviceOrg(selectedDeviceId,selectedOrgId,deviceType);
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
			json = this.wellInformationManagerService.getPipeDeviceInfoList(map, pager,recordCount);
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
	
	@RequestMapping("/doAuxiliaryDeviceShow")
	public String doAuxiliaryDeviceShow() throws IOException {
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
		String json = this.wellInformationManagerService.doAuxiliaryDeviceShow(map, pager,deviceType,recordCount);
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
		String json = this.wellInformationManagerService.getBatchAddAuxiliaryDeviceTableInfo(recordCount);
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
		String json = this.wellInformationManagerService.getAuxiliaryDevice(deviceId,deviceType);
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
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
		String json = this.wellInformationManagerService.getDeviceAdditionalInfo(deviceId,deviceType);
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
		String json="[]";
		if(StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200){
			json = this.wellInformationManagerService.getRPCDeviceInfoExportData(map, pager,recordCount);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=200&&StringManagerUtils.stringToInteger(deviceType)<300){
			json = this.wellInformationManagerService.getPipeDeviceInfoExportData(map, pager,recordCount);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=300){
			json = this.wellInformationManagerService.getSMSDeviceInfoExportData(map, pager,recordCount);
		}
		
		
		this.service.exportGridPanelData(response,fileName,title, heads, fields,json);
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
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String deviceAuxiliaryData = ParamUtils.getParameter(request, "deviceAuxiliaryData").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		Gson gson = new Gson();
		String deviceTableName="tbl_rpcdevice";
		java.lang.reflect.Type type = new TypeToken<WellHandsontableChangedData>() {}.getType();
		WellHandsontableChangedData wellHandsontableChangedData=gson.fromJson(data, type);
		if(StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200){
			deviceTableName="tbl_rpcdevice";
			json=this.wellInformationManagerService.saveRPCDeviceData(wellHandsontableChangedData,orgId,StringManagerUtils.stringToInteger(deviceType),user);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=200&&StringManagerUtils.stringToInteger(deviceType)<300){
			deviceTableName="tbl_pcpdevice";
			json=this.wellInformationManagerService.savePCPDeviceData(wellHandsontableChangedData,orgId,StringManagerUtils.stringToInteger(deviceType),user);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=300){
			this.wellInformationManagerService.saveSMSDeviceData(wellHandsontableChangedData,orgId,StringManagerUtils.stringToInteger(deviceType),user);
		}
		
		EquipmentDriverServerTask.LoadDeviceCommStatus();
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
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String orgId = ParamUtils.getParameter(request, "orgId");
		String isCheckout = ParamUtils.getParameter(request, "isCheckout");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String json="";
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<WellHandsontableChangedData>() {}.getType();
		WellHandsontableChangedData wellHandsontableChangedData=gson.fromJson(data, type);
		if(StringManagerUtils.stringToInteger(deviceType)>=100&&StringManagerUtils.stringToInteger(deviceType)<200){
			json=this.wellInformationManagerService.batchAddRPCDevice(wellHandsontableChangedData,orgId,StringManagerUtils.stringToInteger(deviceType),isCheckout,user);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=200&&StringManagerUtils.stringToInteger(deviceType)<300){
			json=this.wellInformationManagerService.batchAddPCPDevice(wellHandsontableChangedData,orgId,StringManagerUtils.stringToInteger(deviceType),isCheckout,user);
		}else if(StringManagerUtils.stringToInteger(deviceType)>=300){
			this.wellInformationManagerService.saveSMSDeviceData(wellHandsontableChangedData,orgId,StringManagerUtils.stringToInteger(deviceType),user);
		}
		
		EquipmentDriverServerTask.LoadDeviceCommStatus();
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
	 * 描述：辅件设备基本信息Handsontable表格编辑数据保存
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveAuxiliaryDeviceHandsontableData")
	public String saveAuxiliaryDeviceHandsontableData() throws Exception {
		HttpSession session=request.getSession();
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<AuxiliaryDeviceHandsontableChangedData>() {}.getType();
		AuxiliaryDeviceHandsontableChangedData auxiliaryDeviceHandsontableChangedData=gson.fromJson(data, type);
		String json=this.wellInformationManagerService.saveAuxiliaryDeviceHandsontableData(auxiliaryDeviceHandsontableChangedData);
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
		HttpSession session=request.getSession();
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String isCheckout = ParamUtils.getParameter(request, "isCheckout");
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<AuxiliaryDeviceHandsontableChangedData>() {}.getType();
		AuxiliaryDeviceHandsontableChangedData auxiliaryDeviceHandsontableChangedData=gson.fromJson(data, type);
		String json=this.wellInformationManagerService.batchAddAuxiliaryDevice(auxiliaryDeviceHandsontableChangedData,isCheckout);
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
	public String doRPCDeviceAdd(@ModelAttribute RPCDeviceInformation rpcDeviceInformation) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession();
		try {
			User user = (User) session.getAttribute("userLogin");
			if(rpcDeviceInformation.getOrgId()==null){
				rpcDeviceInformation.setOrgId(user.getUserOrgid());
			}
			EquipmentDriverServerTask.LoadDeviceCommStatus();
			List<String> addWellList=new ArrayList<String>();
			addWellList.add(rpcDeviceInformation.getWellName());
			if(rpcDeviceInformation.getStatus()==1){
				EquipmentDriverServerTask.initRPCDriverAcquisitionInfoConfig(addWellList,"update");
			}
			rpcDeviceManagerService.getBaseDao().saveDeviceOperationLog(null, addWellList, null, rpcDeviceInformation.getDeviceType(), user);
			result = "{success:true,msg:true,resultCode:1}";
			
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
	
	@RequestMapping("/doPumpDeviceEdit")
	public String doPumpDeviceEdit(@ModelAttribute RPCDeviceInformation rpcDeviceInformation) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession();
		try {
			User user = (User) session.getAttribute("userLogin");
			if(rpcDeviceInformation.getOrgId()==null){
				rpcDeviceInformation.setOrgId(user.getUserOrgid());
			}
			this.rpcDeviceManagerService.doRPCDeviceEdit(rpcDeviceInformation);
			List<String> addWellList=new ArrayList<String>();
			addWellList.add(rpcDeviceInformation.getWellName());
			EquipmentDriverServerTask.initRPCDriverAcquisitionInfoConfig(addWellList,"update");
			rpcDeviceManagerService.getBaseDao().saveDeviceOperationLog(null, addWellList, null, rpcDeviceInformation.getDeviceType(), user);
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
	
	@RequestMapping("/doPipelineDeviceAdd")
	public String doPipelineDeviceAdd(@ModelAttribute PCPDeviceInformation pcpDeviceInformation) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession();
		try {
			User user = (User) session.getAttribute("userLogin");
			this.pcpDeviceManagerService.doPCPDeviceAdd(pcpDeviceInformation);
			EquipmentDriverServerTask.LoadDeviceCommStatus();
			List<String> addWellList=new ArrayList<String>();
			addWellList.add(pcpDeviceInformation.getWellName());
			if(pcpDeviceInformation.getStatus()==1){
				EquipmentDriverServerTask.initPCPDriverAcquisitionInfoConfig(addWellList,"update");
			}
			pcpDeviceManagerService.getBaseDao().saveDeviceOperationLog(null, addWellList, null, pcpDeviceInformation.getDeviceType(), user);
			result = "{success:true,msg:true,resultCode:1}";
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
	
	@RequestMapping("/judgeAuxiliaryDeviceExistOrNot")
	public String judgeAuxiliaryDeviceExistOrNot() throws IOException {
		orgId=ParamUtils.getParameter(request, "orgId");
		String name = ParamUtils.getParameter(request, "name");
		String type = ParamUtils.getParameter(request, "type");
		String model = ParamUtils.getParameter(request, "model");
		boolean flag = this.wellInformationManagerService.judgeAuxiliaryDeviceExistOrNot(name,type,model);
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
