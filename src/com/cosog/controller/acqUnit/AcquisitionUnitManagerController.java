package com.cosog.controller.acqUnit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import com.cosog.model.AcquisitionGroup;
import com.cosog.model.AcquisitionGroupItem;
import com.cosog.model.AcquisitionUnit;
import com.cosog.model.AcquisitionUnitGroup;
import com.cosog.model.AlarmUnit;
import com.cosog.model.AlarmUnitItem;
import com.cosog.model.DataMapping;
import com.cosog.model.DisplayUnit;
import com.cosog.model.DisplayUnitItem;
import com.cosog.model.Module;
import com.cosog.model.ProtocolAlarmInstance;
import com.cosog.model.ProtocolDisplayInstance;
import com.cosog.model.ProtocolInstance;
import com.cosog.model.ProtocolModel;
import com.cosog.model.ProtocolReportInstance;
import com.cosog.model.ProtocolSMSInstance;
import com.cosog.model.ReportTemplate;
import com.cosog.model.ReportUnit;
import com.cosog.model.ReportUnitItem;
import com.cosog.model.Role;
import com.cosog.model.RoleModule;
import com.cosog.model.RunStatusConfig;
import com.cosog.model.User;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.PCPProductionData;
import com.cosog.model.calculate.SRPProductionData;
import com.cosog.model.drive.ExportAcqInstanceData;
import com.cosog.model.drive.ExportAcqUnitData;
import com.cosog.model.drive.ExportAlarmInstanceData;
import com.cosog.model.drive.ExportAlarmUnitData;
import com.cosog.model.drive.ExportDisplayInstanceData;
import com.cosog.model.drive.ExportDisplayUnitData;
import com.cosog.model.drive.ExportProtocolConfig;
import com.cosog.model.drive.ExportProtocolData;
import com.cosog.model.drive.ExportReportInstanceData;
import com.cosog.model.drive.ExportReportUnitData;
import com.cosog.model.drive.ModbusDriverSaveData;
import com.cosog.model.drive.ModbusProtocolAlarmUnitSaveData;
import com.cosog.model.drive.ModbusProtocolAlarmInstanceSaveData;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.drive.ModbusProtocolConfig.ExtendedField;
import com.cosog.model.drive.ModbusProtocolConfig.Items;
import com.cosog.model.drive.ModbusProtocolConfig.ItemsMeaning;
import com.cosog.model.drive.ModbusProtocolConfig.Protocol;
import com.cosog.model.drive.ModbusProtocolDisplayInstanceSaveData;
import com.cosog.model.drive.ModbusProtocolInstanceSaveData;
import com.cosog.model.drive.ModbusProtocolReportInstanceSaveData;
import com.cosog.model.drive.ModbusProtocolReportUnitSaveData;
import com.cosog.model.drive.TotalCalItemsToReportUnitSaveData;
import com.cosog.model.gridmodel.AcquisitionGroupHandsontableChangeData;
import com.cosog.model.gridmodel.AcquisitionUnitHandsontableChangeData;
import com.cosog.model.gridmodel.DatabaseMappingProHandsontableChangedData;
import com.cosog.model.gridmodel.DisplayUnitHandsontableChangeData;
import com.cosog.model.gridmodel.WellHandsontableChangedData;
import com.cosog.service.acqUnit.AcquisitionUnitManagerService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.right.RoleManagerService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.task.MemoryDataManagerTask.CalItem;
import com.cosog.thread.calculate.DataSynchronizationThread;
import com.cosog.thread.calculate.ThreadPool;
import com.cosog.utils.AcquisitionItemColumnsMap;
import com.cosog.utils.BackModuleRecursion;
import com.cosog.utils.CalculateUtils;
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
import com.cosog.utils.TcpServerConfigMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jxl.Sheet;
import jxl.Workbook;
import redis.clients.jedis.Jedis;

/** <p>描述：角色维护管理Action</p>
 * 
 * @author gao 2014-06-04
 *
 */
@Controller
@RequestMapping("/acquisitionUnitManagerController")
@Scope("prototype")
public class AcquisitionUnitManagerController extends BaseController {

	private static Log log = LogFactory.getLog(AcquisitionUnitManagerController.class);
	private static final long serialVersionUID = -281275682819237996L;
	@Autowired
	private AcquisitionUnitManagerService<AcquisitionUnit> acquisitionUnitManagerService;
	@Autowired
	private AcquisitionUnitManagerService<AcquisitionGroup> acquisitionGroupManagerService;
	@Autowired
	private AcquisitionUnitManagerService<AcquisitionGroupItem> acquisitionUnitItemManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<AlarmUnit> alarmUnitManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<AlarmUnitItem> alarmUnitItemManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<DisplayUnit> displayUnitManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<DisplayUnitItem> displayUnitItemManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<ReportUnit> reportUnitManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<ReportUnitItem> reportUnitItemManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<ProtocolInstance> protocolInstanceManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<ProtocolDisplayInstance> protocolDisplayInstanceManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<ProtocolReportInstance> protocolReportInstanceManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<ProtocolAlarmInstance> protocolAlarmInstanceManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<ProtocolSMSInstance> protocolSMSInstanceManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<RunStatusConfig> runStatusConfigManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<ProtocolModel> protocolModelManagerService;
	
	@Autowired
	private CommonDataService service;
	
	private AcquisitionUnit acquisitionUnit;
	private AcquisitionGroup acquisitionGroup;
	private AlarmUnit alarmUnit;
	private DisplayUnit displayUnit;
	
	private ProtocolInstance protocolInstance;
	private ProtocolAlarmInstance protocolAlarmInstance;
	
	private String limit;
	private String msg = "";
	private String unitName;
	private String groupName;
	private String page;
	
	//添加绑定前缀 
	@InitBinder("acquisitionUnit")
	public void initBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("acquisitionUnit.");
	}
	//添加绑定前缀 
	@InitBinder("acquisitionGroup")
	public void initBinder2(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("acquisitionGroup.");
	}
	
	//添加绑定前缀 
	@InitBinder("protocolInstance")
	public void initBinder3(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("protocolInstance.");
	}
	
	//添加绑定前缀 
	@InitBinder("alarmUnit")
	public void initBinder4(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("alarmUnit.");
	}
	
	//添加绑定前缀 
	@InitBinder("protocolAlarmInstance")
	public void initBinder5(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("protocolAlarmInstance.");
	}
	
	//添加绑定前缀 
	@InitBinder("protocolSMSInstance")
	public void initBinder6(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("protocolSMSInstance.");
	}
	
	//添加绑定前缀 
	@InitBinder("displayUnit")
	public void initBinder7(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("displayUnit.");
	}
	
	//添加绑定前缀 
	@InitBinder("protocolDisplayInstance")
	public void initBinder8(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("protocolDisplayInstance.");
	}
	
	//添加绑定前缀 
	@InitBinder("runStatusConfig")
	public void initBinder9(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("runStatusConfig.");
	}
	
	//添加绑定前缀 
	@InitBinder("reportUnit")
	public void initBinder10(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("reportUnit.");
	}
	
	//添加绑定前缀 
	@InitBinder("protocolReportInstance")
	public void initBinder11(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("protocolReportInstance.");
	}
	
	//添加绑定前缀 
	@InitBinder("protocolModel")
	public void initBinder12(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("protocolModel.");
	}
	
	@RequestMapping("/doModbusProtocolAdd")
	public String doModbusProtocolAdd(@ModelAttribute ProtocolModel protocolModel) throws IOException {
		String result = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		PrintWriter out = response.getWriter();
		try {
			protocolModel.setName(protocolModel.getName());
			protocolModel.setLanguage(user.getLanguage());
			this.protocolModelManagerService.doProtocolAdd(protocolModel);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("addProtocol")+":"+protocolModel.getName());
			}
			
			MemoryDataManagerTask.loadProtocolConfig(protocolModel.getName(),protocolModel.getDeviceType()+"");
			ThreadPool executor = new ThreadPool("dataSynchronization",
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
			
			DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
			dataSynchronizationThread.setSign(001);
			dataSynchronizationThread.setParam1(protocolModel.getName());
			dataSynchronizationThread.setMethod("update");
			executor.execute(dataSynchronizationThread);
		
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
			
		}
		out.print(result);
		out.flush();
		out.close();
		return null;
	}
	
	@RequestMapping("/doAcquisitionGroupAdd")
	public String doAcquisitionGroupAdd(@ModelAttribute AcquisitionGroup acquisitionGroup) throws IOException {
		String result = "";
		String acqUnit = ParamUtils.getParameter(request, "acquisitionGroup.acqUnit");
		PrintWriter out = response.getWriter();
		try {
			if(acquisitionGroup.getType()==0){
				if(acquisitionGroup.getGroupTimingInterval()==null || acquisitionGroup.getGroupTimingInterval()==0){
					acquisitionGroup.setGroupTimingInterval(60);
				}
				if(acquisitionGroup.getGroupSavingInterval()==null || acquisitionGroup.getGroupSavingInterval()==0){
					acquisitionGroup.setGroupSavingInterval(300);
				}
			}
			this.acquisitionGroupManagerService.doAcquisitionGroupAdd(acquisitionGroup);
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("addAcqGroup")+":"+acquisitionGroup.getGroupName());
			}
			String sql="select t.id from TBL_ACQ_GROUP_CONF t "
					+ " where t.group_name='"+acquisitionGroup.getGroupName()+"' and t.protocol='"+acquisitionGroup.getProtocol()+"'"
					+ " order by t.id desc";
			String groupId="";
			List list = this.service.findCallSql(sql);
			if(list.size()>0){
				groupId=list.get(0).toString();
				AcquisitionUnitGroup acquisitionUnitGroup = new AcquisitionUnitGroup();
				acquisitionUnitGroup.setUnitId(Integer.parseInt(acqUnit));
				acquisitionUnitGroup.setGroupId(StringManagerUtils.stringToInteger(groupId));
				acquisitionUnitGroup.setMatrix("0,0,0");
				this.acquisitionUnitItemManagerService.grantAcquisitionGroupsPermission(acquisitionUnitGroup);
			}
//			if(StringManagerUtils.isNotNull(groupId)){
//				EquipmentDriverServerTask.initInstanceConfigByAcqGroupId(groupId, "update");
//			}
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
		}
		out.print(result);
		out.flush();
		out.close();
		return null;
	}
	
	@RequestMapping("/doAcquisitionGroupEdit")
	public String doAcquisitionGroupEdit(@ModelAttribute AcquisitionGroup acquisitionGroup) throws IOException {
		String result ="{success:true,msg:false}";
		PrintWriter out = response.getWriter();
		try {
			this.acquisitionUnitManagerService.doAcquisitionGroupEdit(acquisitionGroup);
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("+editAcqGroup+")+":"+acquisitionGroup.getGroupName());
			}
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			result= "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print(result);
		out.flush();
		out.close();
		return null;
	}
	
	@RequestMapping("/doAcquisitionGroupBulkDelete")
	public String doAcquisitionGroupBulkDelete() {
		try {
			String ids = ParamUtils.getParameter(request, "paramsId");
			this.acquisitionUnitManagerService.doAcquisitionGroupBulkDelete(ids);
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("deleteAcqGroup"));
			}
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			String result = "{success:true,flag:true}";
			response.getWriter().print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/doAcquisitionUnitAdd")
	public String doAcquisitionUnitAdd(@ModelAttribute AcquisitionUnit acquisitionUnit) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			this.acquisitionUnitManagerService.doAcquisitionUnitAdd(acquisitionUnit);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("addAcqUnit")+":"+acquisitionUnit.getUnitName());
			}
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
		}
		out.print(result);
		out.flush();
		out.close();
		return null;
	}
	
	@RequestMapping("/doAcquisitionUnitEdit")
	public String doAcquisitionUnitEdit(@ModelAttribute AcquisitionUnit acquisitionUnit) {
		String result ="{success:true,msg:false}";
		try {
			this.acquisitionUnitManagerService.doAcquisitionUnitEdit(acquisitionUnit);
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("editAcqUnit")+":"+acquisitionUnit.getUnitName());
			}
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			result= "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.getWriter().print(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/doAcquisitionUnitBulkDelete")
	public String doAcquisitionUnitBulkDelete() {
		try {
			String ids = ParamUtils.getParameter(request, "paramsId");
			String deviceType = ParamUtils.getParameter(request, "deviceType");
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			this.acquisitionUnitManagerService.doAcquisitionUnitBulkDelete(ids);
			
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("deleteAcqUnit"));
			}
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			String result = "{success:true,flag:true}";
			response.getWriter().print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**<p>描述：采集组数据显示方法</p>
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/doAcquisitionGroupShow")
	public String doAcquisitionGroupShow() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		groupName = ParamUtils.getParameter(request, "groupName");
		String protocolName=ParamUtils.getParameter(request, "protocolName");
		int intPage = Integer.parseInt((page == null || page == "0") ? "1": page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "10": limit);
		int offset = (intPage - 1) * pageSize;
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("groupName", groupName);
		map.put("protocolName", protocolName);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);
		String json = this.acquisitionUnitManagerService.doAcquisitionGroupShow(map,pager,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * @return Null
	 * @throws IOException
	 * @author 显示当前采集组拥有的采集项信息
	 * 
	 */
	@RequestMapping("/showAcquisitionGroupOwnItems")
	public String showAcquisitionGroupOwnItems() throws IOException {
		String groupCode = ParamUtils.getParameter(request, "groupCode");
		Gson g = new Gson();
		List<AcquisitionGroupItem> list = acquisitionUnitItemManagerService.showAcquisitionGroupOwnItems(AcquisitionGroupItem.class, groupCode);
		String json = "";
		json = g.toJson(list);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.debug("doShowRightCurrentUsersOwnRoles ***json==****" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * @return Null
	 * @throws IOException
	 * @author 显示当前采集单元拥有的采集组信息
	 * 
	 */
	@RequestMapping("/showAcquisitionUnitOwnGroups")
	public String showAcquisitionUnitOwnGroups() throws IOException {
		String unitId = ParamUtils.getParameter(request, "unitId");
		Gson g = new Gson();
		List<AcquisitionGroupItem> list = acquisitionUnitItemManagerService.showAcquisitionUnitOwnGroups(AcquisitionUnitGroup.class, unitId);
		String json = "";
		json = g.toJson(list);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.debug("doShowRightCurrentUsersOwnRoles ***json==****" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	/**
	 * @return NUll
	 * @throws IOException
	 *             为当前采集组安排采集项
	 */
	@RequestMapping("/grantAcquisitionItemsPermission")
	public String grantAcquisitionItemsPermission() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		AcquisitionGroupItem acquisitionGroupItem = null;
		try {
			String params = ParamUtils.getParameter(request, "params");
			String matrixCodes = ParamUtils.getParameter(request, "matrixCodes");
			String groupId = ParamUtils.getParameter(request, "groupId");
//			String groupCode = ParamUtils.getParameter(request, "groupCode");
			String protocolCode = ParamUtils.getParameter(request, "protocol");
			log.debug("grantAcquisitionItemsPermission params==" + params);
			String paramsArr[] = StringManagerUtils.split(params, ",");
//			String groupName="";
//			String groupIdSql="select t.id,t.group_name from tbl_acq_group_conf t where t.group_code='"+groupCode+"' ";
//			List list = this.service.findCallSql(groupIdSql);
//			if(list.size()>0){
//				Object[] obj=(Object[]) list.get(0);
//				groupId=obj[0]+"";
//				groupName=obj[1]+"";
//			}
			
			ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByCode(protocolCode);
			if (StringManagerUtils.isNotNull(groupId) && protocol!=null) {
				this.acquisitionUnitItemManagerService.deleteCurrentAcquisitionGroupOwnItems(groupId);
				if (StringManagerUtils.isNotNull(matrixCodes)) {
					String module_matrix[] = matrixCodes.split("\\|");
					List<String> itemsList=new ArrayList<String>();
					for (int i = 0; i < module_matrix.length; i++) {
						String module_[] = module_matrix[i].split("\\:");
						String itemName=module_[0];
						int itemAddr=StringManagerUtils.stringToInteger(module_[1]);
						String resolutionMode=module_[2];
						int bitIndex=-99;
						if("开关量".equalsIgnoreCase(resolutionMode)){//如果是开关量
							for(int j=0;j<protocol.getItems().size();j++){
								if(itemAddr==protocol.getItems().get(j).getAddr()){
									for(int k=0;protocol.getItems().get(j).getMeaning()!=null&&k<protocol.getItems().get(j).getMeaning().size();k++){
										if(itemName.equalsIgnoreCase(protocol.getItems().get(j).getMeaning().get(k).getMeaning())
												&&(StringManagerUtils.isNotNull(module_[3])&&StringManagerUtils.stringToInteger(module_[3])==protocol.getItems().get(j).getMeaning().get(k).getValue())  ){
											itemName=protocol.getItems().get(j).getTitle();
											bitIndex=protocol.getItems().get(j).getMeaning().get(k).getValue();
											break;
										}
									}
									break;
								}
							}
						}
						
						acquisitionGroupItem = new AcquisitionGroupItem();
						acquisitionGroupItem.setGroupId(Integer.parseInt(groupId));
						acquisitionGroupItem.setItemName(itemName);
						if(bitIndex!=-99){
							acquisitionGroupItem.setBitIndex(bitIndex);
						}
						
						acquisitionGroupItem.setMatrix(module_[4]);
						acquisitionGroupItem.setDailyTotalCalculateName(module_[5]);
						acquisitionGroupItem.setDailyTotalCalculate(StringManagerUtils.stringToInteger(module_[6]));
						
						this.acquisitionUnitItemManagerService.grantAcquisitionItemsPermission(acquisitionGroupItem);
					}
				}
			}
			ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
			DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
			dataSynchronizationThread.setSign(021);
			dataSynchronizationThread.setParam1(groupId);
			dataSynchronizationThread.setMethod("update");
			dataSynchronizationThread.setAcquisitionUnitManagerService(acquisitionUnitManagerService);
			executor.execute(dataSynchronizationThread);
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
	 * @return NUll
	 * @throws IOException
	 *             为当前采集单元安排采集组
	 */
	@RequestMapping("/grantAcquisitionGroupsPermission")
	public String grantAcquisitionGroupsPermission() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		AcquisitionUnitGroup acquisitionUnitGroup = null;
		try {
			String paramsIds = ParamUtils.getParameter(request, "paramsId");
			String matrixCodes = ParamUtils.getParameter(request, "matrixCodes");
			String unitId = ParamUtils.getParameter(request, "unitId");
			log.debug("grantAcquisitionGroupsPermission paramsIds==" + paramsIds);
			String groupIds[] = StringManagerUtils.split(paramsIds, ",");
			if (groupIds.length > 0 && unitId != null) {
				this.acquisitionUnitItemManagerService.deleteCurrentAcquisitionUnitOwnGroups(unitId);
				if (StringManagerUtils.isNotNull(matrixCodes)) {
					String module_matrix[] = matrixCodes.split("\\|");
					for (int i = 0; i < module_matrix.length; i++) {
						String module_[] = module_matrix[i].split("\\:");
						acquisitionUnitGroup = new AcquisitionUnitGroup();
						acquisitionUnitGroup.setUnitId(Integer.parseInt(unitId));
						log.debug("unitId==" + unitId);
						acquisitionUnitGroup.setGroupId(StringManagerUtils.stringToInteger(module_[0]));
						acquisitionUnitGroup.setMatrix(module_[1]);
						this.acquisitionUnitItemManagerService.grantAcquisitionGroupsPermission(acquisitionUnitGroup);
					}
				}
				EquipmentDriverServerTask.initInstanceConfigByAcqUnitId(unitId,"update");
			}
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
	 * @return NUll
	 * @throws IOException
	 * 显示单元安排采集项
	 */
	@RequestMapping("/grantAcqItemsToDisplayUnitPermission")
	public String grantAcqItemsToDisplayUnitPermission() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		DisplayUnitItem displayUnitItem = null;
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
		try {
			String matrixCodes = ParamUtils.getParameter(request, "matrixCodes");
			String unitId = ParamUtils.getParameter(request, "unitId");
			String protocolCode = ParamUtils.getParameter(request, "protocol");
//			String itemType = ParamUtils.getParameter(request, "itemType");
			
			ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByCode(protocolCode);
			
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			
			if (StringManagerUtils.isNotNull(unitId) && protocol!=null) {
				this.displayUnitItemManagerService.deleteCurrentDisplayUnitOwnItems(unitId);
				
				if (StringManagerUtils.isNotNull(matrixCodes)) {
					String module_matrix[] = matrixCodes.split("\\|");
					for (int i = 0; i < module_matrix.length; i++) {
						String module_[] = module_matrix[i].split("##");
						String itemName=module_[0];
						
						String resolutionMode=module_[16];
						int itemAddr=StringManagerUtils.stringToInteger(module_[17]);
						String bitIndexStr=module_[18];
						int type=StringManagerUtils.stringToInteger(module_[19]);
						String itemCode=module_[20];
						
						if(type==0){
							int bitIndex=-99;
							if(languageResourceMap.get("switchingValue").equalsIgnoreCase(resolutionMode)){//如果是开关量
								for(int j=0;j<protocol.getItems().size();j++){
									if(itemAddr==protocol.getItems().get(j).getAddr()){
										for(int k=0;protocol.getItems().get(j).getMeaning()!=null&&k<protocol.getItems().get(j).getMeaning().size();k++){
											if(itemName.equalsIgnoreCase(protocol.getItems().get(j).getMeaning().get(k).getMeaning())
													&&(StringManagerUtils.isNotNull(bitIndexStr)&&StringManagerUtils.stringToInteger(bitIndexStr)==protocol.getItems().get(j).getMeaning().get(k).getValue())  ){
												itemName=protocol.getItems().get(j).getTitle();
												bitIndex=protocol.getItems().get(j).getMeaning().get(k).getValue();
												break;
											}
										}
										break;
									}
								}
							}
							if(loadProtocolMappingColumnByTitleMap.containsKey(itemName)){
								itemCode=loadProtocolMappingColumnByTitleMap.get(itemName).getMappingColumn();
							}
							
							displayUnitItem = new DisplayUnitItem();
							displayUnitItem.setUnitId(StringManagerUtils.stringToInteger(unitId));
							displayUnitItem.setItemName(itemName);
							displayUnitItem.setItemCode(itemCode);
							displayUnitItem.setType(type);
							displayUnitItem.setRealtimeOverview(StringManagerUtils.stringToInteger(module_[1]));
							displayUnitItem.setRealtimeOverviewSort(StringManagerUtils.isNumber(module_[2])?StringManagerUtils.stringToInteger(module_[2]):null);
							displayUnitItem.setRealtimeData(StringManagerUtils.stringToInteger(module_[3]));
							displayUnitItem.setRealtimeSort(StringManagerUtils.isNumber(module_[4])?StringManagerUtils.stringToInteger(module_[4]):null);
							displayUnitItem.setRealtimeColor(module_[5]);
							displayUnitItem.setRealtimeBgColor(module_[6]);
							
							displayUnitItem.setHistoryOverview(StringManagerUtils.stringToInteger(module_[7]));
							displayUnitItem.setHistoryOverviewSort(StringManagerUtils.isNumber(module_[8])?StringManagerUtils.stringToInteger(module_[8]):null);
							displayUnitItem.setHistoryData(StringManagerUtils.stringToInteger(module_[9]));
							displayUnitItem.setHistorySort(StringManagerUtils.isNumber(module_[10])?StringManagerUtils.stringToInteger(module_[10]):null);
							displayUnitItem.setHistoryColor(module_[11]);
							displayUnitItem.setHistoryBgColor(module_[12]);
							displayUnitItem.setBitIndex(bitIndex>=0?bitIndex:null);
							displayUnitItem.setShowLevel(StringManagerUtils.isNumber(module_[13])?StringManagerUtils.stringToInteger(module_[13]):null);
							
							
							displayUnitItem.setRealtimeCurveConf(!languageResourceMap.get("switchingValue").equalsIgnoreCase(resolutionMode)?module_[14]:"");
							displayUnitItem.setHistoryCurveConf(!languageResourceMap.get("switchingValue").equalsIgnoreCase(resolutionMode)?module_[15]:"");
							displayUnitItem.setMatrix(module_[21]);
							if(StringManagerUtils.isNotNull(displayUnitItem.getItemCode())){
								this.displayUnitItemManagerService.grantDisplayItemsPermission(displayUnitItem);
							}
						}else {
							displayUnitItem = new DisplayUnitItem();
							displayUnitItem.setUnitId(StringManagerUtils.stringToInteger(unitId));
							displayUnitItem.setItemName(itemName);
							displayUnitItem.setItemCode(itemCode);
							displayUnitItem.setType(type);
							
							displayUnitItem.setRealtimeOverview(StringManagerUtils.stringToInteger(module_[1]));
							displayUnitItem.setRealtimeOverviewSort(StringManagerUtils.isNumber(module_[2])?StringManagerUtils.stringToInteger(module_[2]):null);
							displayUnitItem.setRealtimeData(StringManagerUtils.stringToInteger(module_[3]));
							displayUnitItem.setRealtimeSort(StringManagerUtils.isNumber(module_[4])?StringManagerUtils.stringToInteger(module_[4]):null);
							displayUnitItem.setRealtimeColor(module_[5]);
							displayUnitItem.setRealtimeBgColor(module_[6]);
							
							displayUnitItem.setHistoryOverview(StringManagerUtils.stringToInteger(module_[7]));
							displayUnitItem.setHistoryOverviewSort(StringManagerUtils.isNumber(module_[8])?StringManagerUtils.stringToInteger(module_[8]):null);
							displayUnitItem.setHistoryData(StringManagerUtils.stringToInteger(module_[9]));
							displayUnitItem.setHistorySort(StringManagerUtils.isNumber(module_[10])?StringManagerUtils.stringToInteger(module_[10]):null);
							displayUnitItem.setHistoryColor(module_[11]);
							displayUnitItem.setHistoryBgColor(module_[12]);
							
							displayUnitItem.setShowLevel(StringManagerUtils.isNumber(module_[13])?StringManagerUtils.stringToInteger(module_[13]):null);
							displayUnitItem.setRealtimeCurveConf(module_[14]);
							displayUnitItem.setHistoryCurveConf(module_[15]);
							displayUnitItem.setMatrix(module_[21]);
							this.displayUnitItemManagerService.grantDisplayItemsPermission(displayUnitItem);
						}
					}
				}
			}
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
	 * @return NUll
	 * @throws IOException
	 * 显示单元安排控制项
	 */
	@RequestMapping("/grantCtrlItemsToDisplayUnitPermission")
	public String grantCtrlItemsToDisplayUnitPermission() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		DisplayUnitItem displayUnitItem = null;
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
		try {
			String params = ParamUtils.getParameter(request, "params");
			String matrixCodes = ParamUtils.getParameter(request, "matrixCodes");
			String unitId = ParamUtils.getParameter(request, "unitId");
			String protocolCode = ParamUtils.getParameter(request, "protocol");
			String itemType = ParamUtils.getParameter(request, "itemType");
			log.debug("grantAcquisitionItemsPermission params==" + params);
			String paramsArr[] = StringManagerUtils.split(params, ",");
			
			ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByCode(protocolCode);
			
			if (StringManagerUtils.isNotNull(unitId) && protocol!=null) {
				this.displayUnitItemManagerService.deleteCurrentDisplayUnitOwnItems(unitId,itemType);
				if (StringManagerUtils.isNotNull(matrixCodes)) {
					String module_matrix[] = matrixCodes.split("\\|");
					List<String> itemsList=new ArrayList<String>();
					for (int i = 0; i < module_matrix.length; i++) {
						String module_[] = module_matrix[i].split("\\:");
						String itemName=module_[0];
						int itemAddr=StringManagerUtils.stringToInteger(module_[4]);
						String resolutionMode=module_[3];
						String bitIndexStr=module_[5];
						int bitIndex=-99;
						if("开关量".equalsIgnoreCase(resolutionMode)){//如果是开关量
							for(int j=0;j<protocol.getItems().size();j++){
								if(itemAddr==protocol.getItems().get(j).getAddr()){
									for(int k=0;protocol.getItems().get(j).getMeaning()!=null&&k<protocol.getItems().get(j).getMeaning().size();k++){
										if(itemName.equalsIgnoreCase(protocol.getItems().get(j).getMeaning().get(k).getMeaning())
												&&(StringManagerUtils.isNotNull(bitIndexStr)&&StringManagerUtils.stringToInteger(bitIndexStr)==protocol.getItems().get(j).getMeaning().get(k).getValue())  ){
											itemName=protocol.getItems().get(j).getTitle();
											bitIndex=protocol.getItems().get(j).getMeaning().get(k).getValue();
											break;
										}
									}
									break;
								}
							}
						}
						String itemCode="";
						if(loadProtocolMappingColumnByTitleMap.containsKey(itemName)){
							itemCode=loadProtocolMappingColumnByTitleMap.get(itemName).getMappingColumn();
						}
						displayUnitItem = new DisplayUnitItem();
						displayUnitItem.setUnitId(StringManagerUtils.stringToInteger(unitId));
						displayUnitItem.setItemName(itemName);
						displayUnitItem.setItemCode(itemCode);
						displayUnitItem.setType(StringManagerUtils.stringToInteger(itemType));
						displayUnitItem.setRealtimeSort(StringManagerUtils.isNumber(module_[1])?StringManagerUtils.stringToInteger(module_[1]):null);
						displayUnitItem.setShowLevel(StringManagerUtils.isNumber(module_[2])?StringManagerUtils.stringToInteger(module_[2]):null);
						displayUnitItem.setBitIndex(bitIndex>=0?bitIndex:null);
						displayUnitItem.setMatrix(module_[6]);
						this.displayUnitItemManagerService.grantDisplayItemsPermission(displayUnitItem);
					}
					
				}
			}
			ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
			
			DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
			dataSynchronizationThread.setSign(042);
			dataSynchronizationThread.setParam1(unitId);
			dataSynchronizationThread.setMethod("update");
			executor.execute(dataSynchronizationThread);
			
			
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
	 * @return NUll
	 * @throws IOException
	 * 显示单元安排计算项
	 */
	@RequestMapping("/grantCalItemsToDisplayUnitPermission")
	public String grantCalItemsToDisplayUnitPermission() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		DisplayUnitItem displayUnitItem = null;
		try {
			String matrixCodes = ParamUtils.getParameter(request, "matrixCodes");
			String unitId = ParamUtils.getParameter(request, "unitId");
			String itemType = ParamUtils.getParameter(request, "itemType");

			this.displayUnitItemManagerService.deleteCurrentDisplayUnitOwnItems(unitId,itemType);
			if (StringManagerUtils.isNotNull(matrixCodes)) {
				String module_matrix[] = matrixCodes.split("\\|");
				for (int i = 0; i < module_matrix.length; i++) {
					String module_[] = module_matrix[i].split("##");
					
					
					String itemName=module_[0];
					displayUnitItem = new DisplayUnitItem();
					displayUnitItem.setUnitId(StringManagerUtils.stringToInteger(unitId));
					displayUnitItem.setItemName(itemName);
					displayUnitItem.setItemCode(module_[1]);
					displayUnitItem.setType(StringManagerUtils.stringToInteger(itemType));
					displayUnitItem.setRealtimeSort(StringManagerUtils.isNumber(module_[2])?StringManagerUtils.stringToInteger(module_[2]):null);
					displayUnitItem.setRealtimeColor(module_[3]);
					displayUnitItem.setRealtimeBgColor(module_[4]);
					
					displayUnitItem.setHistorySort(StringManagerUtils.isNumber(module_[5])?StringManagerUtils.stringToInteger(module_[5]):null);
					displayUnitItem.setHistoryColor(module_[6]);
					displayUnitItem.setHistoryBgColor(module_[7]);
					
					displayUnitItem.setShowLevel(StringManagerUtils.isNumber(module_[8])?StringManagerUtils.stringToInteger(module_[8]):null);
					displayUnitItem.setRealtimeCurveConf(module_[9]);
					displayUnitItem.setHistoryCurveConf(module_[10]);
					displayUnitItem.setMatrix(module_[11]);
					this.displayUnitItemManagerService.grantDisplayItemsPermission(displayUnitItem);
				}
				
			}
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
	 * @return NUll
	 * @throws IOException
	 * 显示单元安排录入项
	 */
	@RequestMapping("/grantInputItemsToDisplayUnitPermission")
	public String grantInputItemsToDisplayUnitPermission() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		DisplayUnitItem displayUnitItem = null;
		try {
			String matrixCodes = ParamUtils.getParameter(request, "matrixCodes");
			String unitId = ParamUtils.getParameter(request, "unitId");
			String itemType = ParamUtils.getParameter(request, "itemType");

			this.displayUnitItemManagerService.deleteCurrentDisplayUnitOwnItems(unitId,itemType);
			if (StringManagerUtils.isNotNull(matrixCodes)) {
				String module_matrix[] = matrixCodes.split("\\|");
				for (int i = 0; i < module_matrix.length; i++) {
					String module_[] = module_matrix[i].split("##");
					
					
					String itemName=module_[0];
					displayUnitItem = new DisplayUnitItem();
					displayUnitItem.setUnitId(StringManagerUtils.stringToInteger(unitId));
					displayUnitItem.setItemName(itemName);
					displayUnitItem.setItemCode(module_[1]);
					displayUnitItem.setType(StringManagerUtils.stringToInteger(itemType));
					displayUnitItem.setRealtimeSort(StringManagerUtils.isNumber(module_[2])?StringManagerUtils.stringToInteger(module_[2]):null);
					displayUnitItem.setRealtimeColor(module_[3]);
					displayUnitItem.setRealtimeBgColor(module_[4]);
					displayUnitItem.setHistorySort(StringManagerUtils.isNumber(module_[5])?StringManagerUtils.stringToInteger(module_[5]):null);
					displayUnitItem.setHistoryColor(module_[6]);
					displayUnitItem.setHistoryBgColor(module_[7]);
					
					displayUnitItem.setShowLevel(StringManagerUtils.isNumber(module_[8])?StringManagerUtils.stringToInteger(module_[8]):null);
					displayUnitItem.setRealtimeCurveConf(module_[9]);
					displayUnitItem.setHistoryCurveConf(module_[10]);
					displayUnitItem.setMatrix(module_[11]);
					this.displayUnitItemManagerService.grantDisplayItemsPermission(displayUnitItem);
				}
				
			}
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
	
	@RequestMapping("/grantTotalCalItemsToReportUnitPermission")
	public String grantTotalCalItemsToReportUnitPermission() throws IOException {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String result = "";
		PrintWriter out = response.getWriter();
		ReportUnitItem reportUnitItem = null;
		java.lang.reflect.Type type=null;
		Gson gson = new Gson();
		try {
			
			String unitId = ParamUtils.getParameter(request, "unitId");
			String reportType = ParamUtils.getParameter(request, "reportType");
			String saveData = ParamUtils.getParameter(request, "saveData");
			String calculateType = ParamUtils.getParameter(request, "calculateType");
			
			List<String> calItemName=new ArrayList<>();
			
			String key="acqTotalCalItemList";
			
			if(StringManagerUtils.stringToInteger(reportType)==2){
				key="acqTimingTotalCalItemList";
				if("1".equalsIgnoreCase(calculateType)){
					key="srpTimingTotalCalItemList";
				}else if("2".equalsIgnoreCase(calculateType)){
					key="pcpTimingTotalCalItemList";
				}
			}else{
				key="acqTotalCalItemList";
				if("1".equalsIgnoreCase(calculateType)){
					key="srpTotalCalItemList";
				}else if("2".equalsIgnoreCase(calculateType)){
					key="pcpTotalCalItemList";
				}
			}
			
			List<CalItem> calItemList=null;
			if("1".equalsIgnoreCase(calculateType)){
				calItemList=MemoryDataManagerTask.getSRPTotalCalculateItem(language);
			}else if("2".equalsIgnoreCase(calculateType)){
				calItemList=MemoryDataManagerTask.getPCPTotalCalculateItem(language);
			}else{
				calItemList=MemoryDataManagerTask.getAcqTotalCalculateItem(language);
			}
			
			for(CalItem calItem:calItemList){
				calItemName.add(calItem.getName());
			}
			
			type = new TypeToken<TotalCalItemsToReportUnitSaveData>() {}.getType();
			TotalCalItemsToReportUnitSaveData totalCalItemsToReportUnitSaveData=gson.fromJson(saveData, type);
			
			this.displayUnitItemManagerService.deleteCurrentReportUnitOwnItems(unitId,reportType);
			
			
			if (totalCalItemsToReportUnitSaveData!=null && totalCalItemsToReportUnitSaveData.getItemList()!=null && totalCalItemsToReportUnitSaveData.getItemList().size()>0) {
				
				for (int i = 0; i < totalCalItemsToReportUnitSaveData.getItemList().size(); i++) {
					boolean save=true;
					if(StringManagerUtils.stringToInteger(MemoryDataManagerTask.getCodeValue("DATASOURCE",totalCalItemsToReportUnitSaveData.getItemList().get(i).getDataSource()+"", language))==1){
						if(!StringManagerUtils.existOrNot(calItemName, totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemName(), false)){
							save=false;
						}
					}
					if(save){
						reportUnitItem = new ReportUnitItem();
						reportUnitItem.setUnitId(StringManagerUtils.stringToInteger(unitId));
						reportUnitItem.setReportType(StringManagerUtils.stringToInteger(reportType));
						reportUnitItem.setItemName(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemName());
						reportUnitItem.setItemCode(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemCode());
						
						reportUnitItem.setTotalType( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getTotalType()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getTotalType()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getTotalType()):null);
						
						reportUnitItem.setSort( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemSort()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemSort()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemSort()):null);
						reportUnitItem.setShowLevel( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemShowLevel()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemShowLevel()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemShowLevel()):null);
						
						reportUnitItem.setPrec( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemPrec()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemPrec()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemPrec()):null);
						
						reportUnitItem.setSumSign((totalCalItemsToReportUnitSaveData.getItemList().get(i).getSumSign()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getSumSign()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getSumSign()):null);
						reportUnitItem.setAverageSign( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getAverageSign()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getAverageSign()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getAverageSign()):null);
						
						reportUnitItem.setReportCurveConf(totalCalItemsToReportUnitSaveData.getItemList().get(i).getReportCurveConf());
						
						reportUnitItem.setCurveStatType( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getCurveStatType()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getCurveStatType()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getCurveStatType()):null);
						
						reportUnitItem.setDataType( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getDataType()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getDataType()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getDataType()):null);
						
						reportUnitItem.setDataSource(StringManagerUtils.stringToInteger(MemoryDataManagerTask.getCodeValue("DATASOURCE",totalCalItemsToReportUnitSaveData.getItemList().get(i).getDataSource()+"", language)));
						reportUnitItem.setMatrix(totalCalItemsToReportUnitSaveData.getItemList().get(i).getMatrix()!=null?totalCalItemsToReportUnitSaveData.getItemList().get(i).getMatrix():"");
						this.reportUnitItemManagerService.grantReportItemsPermission(reportUnitItem);
					}
				}
			}
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
	
	@RequestMapping("/grantReportUnitContentItemsPermission")
	public String grantReportUnitContentItemsPermission() throws IOException {
		String result = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		PrintWriter out = response.getWriter();
		ReportUnitItem reportUnitItem = null;
		java.lang.reflect.Type type=null;
		Gson gson = new Gson();
		try {
			
			String unitId = ParamUtils.getParameter(request, "unitId");
			String reportType = ParamUtils.getParameter(request, "reportType");
			String saveData = ParamUtils.getParameter(request, "saveData");
			String calculateType = ParamUtils.getParameter(request, "calculateType");
//			String sort = ParamUtils.getParameter(request, "sort");
			
			List<String> calItemName=new ArrayList<>();
			List<CalItem> calItemList=null;
			if(StringManagerUtils.stringToInteger(reportType)==2){
				if("1".equalsIgnoreCase(calculateType)){
					calItemList=MemoryDataManagerTask.getSRPTimingTotalCalculateItem(language);
				}else if("2".equalsIgnoreCase(calculateType)){
					calItemList=MemoryDataManagerTask.getPCPTimingTotalCalculateItem(language);
				}else{
					calItemList=MemoryDataManagerTask.getAcqTimingTotalCalculateItem(language);
				}
			}else{
				if("1".equalsIgnoreCase(calculateType)){
					calItemList=MemoryDataManagerTask.getSRPTotalCalculateItem(language);
				}else if("2".equalsIgnoreCase(calculateType)){
					calItemList=MemoryDataManagerTask.getPCPTotalCalculateItem(language);
				}else{
					calItemList=MemoryDataManagerTask.getAcqTotalCalculateItem(language);
				}
			}
			
			for(CalItem calItem:calItemList){
				calItemName.add(calItem.getName());
			}
			
			type = new TypeToken<TotalCalItemsToReportUnitSaveData>() {}.getType();
			TotalCalItemsToReportUnitSaveData totalCalItemsToReportUnitSaveData=gson.fromJson(saveData, type);
			
//			this.displayUnitItemManagerService.deleteCurrentReportUnitOwnItems(unitId,reportType,sort);
			
			
			if (totalCalItemsToReportUnitSaveData!=null && totalCalItemsToReportUnitSaveData.getItemList()!=null && totalCalItemsToReportUnitSaveData.getItemList().size()>0) {
				List<String> calculatelanguageList =MemoryDataManagerTask.getLanguageResourceValueList("calculate");
				for (int i = 0; i < totalCalItemsToReportUnitSaveData.getItemList().size(); i++) {
					boolean save=true;
					if(StringManagerUtils.existOrNot(calculatelanguageList,totalCalItemsToReportUnitSaveData.getItemList().get(i).getDataSource(),false)){
						if(!StringManagerUtils.existOrNot(calItemName, totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemName(), false)){
							save=false;
						}
					}
					if(save){
						reportUnitItem = new ReportUnitItem();
						reportUnitItem.setUnitId(StringManagerUtils.stringToInteger(unitId));
						reportUnitItem.setReportType(StringManagerUtils.stringToInteger(reportType));
						reportUnitItem.setItemName(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemName());
						reportUnitItem.setItemCode(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemCode());
						
						
						
						reportUnitItem.setSort( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemSort()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemSort()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemSort()):null);
						reportUnitItem.setShowLevel( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemShowLevel()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemShowLevel()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemShowLevel()):null);
						
						reportUnitItem.setPrec( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemPrec()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemPrec()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemPrec()):null);
						
						reportUnitItem.setSumSign((totalCalItemsToReportUnitSaveData.getItemList().get(i).getSumSign()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getSumSign()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getSumSign()):null);
						reportUnitItem.setAverageSign( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getAverageSign()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getAverageSign()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getAverageSign()):null);
						
						reportUnitItem.setReportCurveConf(totalCalItemsToReportUnitSaveData.getItemList().get(i).getReportCurveConf());
						
						reportUnitItem.setCurveStatType( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getCurveStatType()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getCurveStatType()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getCurveStatType()):null);
						
						reportUnitItem.setDataType( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getDataType()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getDataType()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getDataType()):null);
						reportUnitItem.setDataSource(StringManagerUtils.stringToInteger(MemoryDataManagerTask.getCodeValue("DATASOURCE",totalCalItemsToReportUnitSaveData.getItemList().get(i).getDataSource()+"", language)));
						
						if(reportUnitItem.getDataSource()==0){
							reportUnitItem.setTotalType( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getTotalType()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getTotalType()) )?StringManagerUtils.stringToInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getTotalType()):null);
						}else{
							reportUnitItem.setTotalType(null);
						}
						
						reportUnitItem.setMatrix(totalCalItemsToReportUnitSaveData.getItemList().get(i).getMatrix()!=null?totalCalItemsToReportUnitSaveData.getItemList().get(i).getMatrix():"");
						
						this.displayUnitItemManagerService.deleteCurrentReportUnitOwnItems(unitId,reportType,reportUnitItem.getSort()+"");
						if(StringManagerUtils.isNotNull(reportUnitItem.getItemName())){
							this.reportUnitItemManagerService.grantReportItemsPermission(reportUnitItem);
						}
					}
				}
			}
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
	
	@RequestMapping("/getProtocolEnumOrSwitchItemsConfigData")
	public String getProtocolEnumOrSwitchItemsConfigData() throws Exception {
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String resolutionMode = ParamUtils.getParameter(request, "resolutionMode");
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		json = acquisitionUnitItemManagerService.getProtocolEnumOrSwitchItemsConfigData(protocolCode,resolutionMode,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolItemMeaningConfigData")
	public String getProtocolItemMeaningConfigData() throws Exception {
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String itemAddr = ParamUtils.getParameter(request, "itemAddr");
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		
		
		json = acquisitionUnitItemManagerService.getProtocolItemMeaningConfigData(protocolCode,itemAddr,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolItemsConfigData")
	public String getProtocolItemsConfigData() throws Exception {
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		json = acquisitionUnitItemManagerService.getProtocolItemsConfigData(protocolName,classes,code,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolExtendedFieldsConfigData")
	public String getProtocolExtendedFieldsConfigData() throws Exception {
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		json = acquisitionUnitItemManagerService.getProtocolExtendedFieldsConfigData(protocolName,classes,code,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolAcqUnitItemsConfigData")
	public String getProtocolAcqUnitItemsConfigData() throws Exception {
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		String type = ParamUtils.getParameter(request, "type");
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		json = acquisitionUnitItemManagerService.getProtocolAcqUnitItemsConfigData(protocolCode,classes,code,type,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getReportTemplateData")
	public String getReportTemplateData() throws Exception {
		String reportType = ParamUtils.getParameter(request, "reportType");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		String code = ParamUtils.getParameter(request, "code");
		String json = "";
		json = acquisitionUnitItemManagerService.getReportTemplateData(reportType,code,calculateType);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getModbusProtocolNumAlarmItemsConfigData")
	public String getModbusProtocolNumAlarmItemsConfigData() throws Exception {
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		json = acquisitionUnitItemManagerService.getModbusProtocolNumAlarmItemsConfigData(protocolCode,classes,code,calculateType,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getModbusProtocolCalNumAlarmItemsConfigData")
	public String getModbusProtocolCalNumAlarmItemsConfigData() throws Exception {
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		json = acquisitionUnitItemManagerService.getModbusProtocolCalNumAlarmItemsConfigData(deviceType,classes,code,calculateType,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getModbusProtocolEnumAlarmItemsConfigData")
	public String getModbusProtocolEnumAlarmItemsConfigData() throws Exception {
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String classes = ParamUtils.getParameter(request, "classes");
		String unitCode = ParamUtils.getParameter(request, "unitCode");
		String itemAddr = ParamUtils.getParameter(request, "itemAddr");
		String itemResolutionMode = ParamUtils.getParameter(request, "itemResolutionMode");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		if("1".equals(itemResolutionMode)){
			json = acquisitionUnitItemManagerService.getModbusProtocolEnumAlarmItemsConfigData(protocolCode,classes,unitCode,itemAddr,itemResolutionMode,language);
		}else if("0".equals(itemResolutionMode)){
			json = acquisitionUnitItemManagerService.getModbusProtocolSwitchAlarmItemsConfigData(protocolCode,classes,unitCode,itemAddr,itemResolutionMode,language);
		}
		
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getModbusProtocolFESDiagramConditionsAlarmItemsConfigData")
	public String getModbusProtocolFESDiagramConditionsAlarmItemsConfigData() throws Exception {
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getModbusProtocolFESDiagramConditionsAlarmItemsConfigData(protocolName,classes,code,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getModbusProtocolCommStatusAlarmItemsConfigData")
	public String getModbusProtocolCommStatusAlarmItemsConfigData() throws Exception {
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getModbusProtocolCommStatusAlarmItemsConfigData(protocolName,classes,code,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getModbusProtocolRunStatusAlarmItemsConfigData")
	public String getModbusProtocolRunStatusAlarmItemsConfigData() throws Exception {
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getModbusProtocolRunStatusAlarmItemsConfigData(protocolName,classes,code,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolDisplayUnitAcqItemsConfigData")
	public String getProtocolDisplayUnitAcqItemsConfigData() throws Exception {
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		String unitId = ParamUtils.getParameter(request, "unitId");
		String acqUnitId = ParamUtils.getParameter(request, "acqUnitId");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolDisplayUnitAcqItemsConfigData(protocolCode,classes,code,unitId,acqUnitId,calculateType,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolDisplayUnitCtrlItemsConfigData")
	public String getProtocolDisplayUnitCtrlItemsConfigData() throws Exception {
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		String unitId = ParamUtils.getParameter(request, "unitId");
		String acqUnitId = ParamUtils.getParameter(request, "acqUnitId");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolDisplayUnitCtrlItemsConfigData(protocolCode,classes,code,unitId,acqUnitId,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolDisplayUnitCalItemsConfigData")
	public String getProtocolDisplayUnitCalItemsConfigData() throws Exception {
		String classes = ParamUtils.getParameter(request, "classes");
		String unitId = ParamUtils.getParameter(request, "unitId");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolDisplayUnitCalItemsConfigData(classes,unitId,calculateType,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolDisplayUnitInputItemsConfigData")
	public String getProtocolDisplayUnitInputItemsConfigData() throws Exception {
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String classes = ParamUtils.getParameter(request, "classes");
		String unitId = ParamUtils.getParameter(request, "unitId");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolDisplayUnitInputItemsConfigData(deviceType,classes,unitId,calculateType,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getReportUnitTotalCalItemsConfigData")
	public String getReportUnitTotalCalItemsConfigData() throws Exception {
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String templateCode = ParamUtils.getParameter(request, "templateCode");
		String unitId = ParamUtils.getParameter(request, "unitId");
		String classes = ParamUtils.getParameter(request, "classes");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getReportUnitTotalCalItemsConfigData(calculateType,reportType,templateCode,unitId,classes,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getReportUnitTotalItemsConfigColInfoData")
	public String getReportUnitTotalItemsConfigColInfoData() throws Exception {
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String templateCode = ParamUtils.getParameter(request, "templateCode");
		String unitId = ParamUtils.getParameter(request, "unitId");
		String classes = ParamUtils.getParameter(request, "classes");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getReportUnitTotalItemsConfigColInfoData(calculateType,reportType,templateCode,unitId,classes,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getReportUnitContentConfigItemsData")
	public String getReportUnitContentConfigItemsData() throws Exception {
		String unitId = ParamUtils.getParameter(request, "unitId");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String row = ParamUtils.getParameter(request, "row");
		String col = ParamUtils.getParameter(request, "col");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		int sort=(StringManagerUtils.isNum(row) && StringManagerUtils.isNumber(row) && StringManagerUtils.stringToInteger(row)>=0)?(StringManagerUtils.stringToInteger(row)+1):0;
		json = acquisitionUnitItemManagerService.getReportUnitContentConfigItemsData(unitId,calculateType,reportType,sort,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getReportInstanceTotalCalItemsConfigData")
	public String getReportInstanceTotalCalItemsConfigData() throws Exception {
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		String unitId = ParamUtils.getParameter(request, "unitId");
		String reportType = ParamUtils.getParameter(request, "reportType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getReportInstanceTotalCalItemsConfigData(calculateType,unitId,reportType,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getReportInstanceTimingTotalCalItemsConfigData")
	public String getReportInstanceTimingTotalCalItemsConfigData() throws Exception {
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		String unitId = ParamUtils.getParameter(request, "unitId");
		String reportType = ParamUtils.getParameter(request, "reportType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getReportInstanceTimingTotalCalItemsConfigData(calculateType,unitId,reportType,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportProtocolDisplayInstanceAcqItemsConfigData")
	public String getImportProtocolDisplayInstanceAcqItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String type = ParamUtils.getParameter(request, "type");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolDisplayInstanceAcqItemsConfigData(id,type,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportProtocolDisplayInstanceCtrlItemsConfigData")
	public String getImportProtocolDisplayInstanceCtrlItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String type = ParamUtils.getParameter(request, "type");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolDisplayInstanceCtrlItemsConfigData(id,type,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolDisplayInstanceCalItemsConfigData")
	public String getProtocolDisplayInstanceCalItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String classes = ParamUtils.getParameter(request, "classes");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolDisplayInstanceCalItemsConfigData(id,classes,calculateType,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolDisplayInstanceInputItemsConfigData")
	public String getProtocolDisplayInstanceInputItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String classes = ParamUtils.getParameter(request, "classes");
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolDisplayInstanceInputItemsConfigData(id,classes,calculateType,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportProtocolDisplayInstanceCalItemsConfigData")
	public String getImportProtocolDisplayInstanceCalItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String type = ParamUtils.getParameter(request, "type");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolDisplayInstanceCalItemsConfigData(id,type,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolAlarmInstanceNumItemsConfigData")
	public String getProtocolAlarmInstanceNumItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String resolutionMode = ParamUtils.getParameter(request, "resolutionMode");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolAlarmInstanceNumItemsConfigData(id,classes,resolutionMode,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportProtocolAlarmContentNumItemsConfigData")
	public String getImportProtocolAlarmContentNumItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String type = ParamUtils.getParameter(request, "type");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolAlarmContentNumItemsConfigData(id,type,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolAlarmInstanceCalNumItemsConfigData")
	public String getProtocolAlarmInstanceCalNumItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String resolutionMode = ParamUtils.getParameter(request, "resolutionMode");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolAlarmInstanceCalNumItemsConfigData(id,classes,resolutionMode,deviceType,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportProtocolAlarmContentCalNumItemsConfigData")
	public String getImportProtocolAlarmContentCalNumItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String type = ParamUtils.getParameter(request, "type");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolAlarmContentCalNumItemsConfigData(id,type,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportProtocolAlarmContentSwitchItemsConfigData")
	public String getImportProtocolAlarmContentSwitchItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String type = ParamUtils.getParameter(request, "type");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolAlarmContentSwitchItemsConfigData(id,type,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportProtocolAlarmContentEnumItemsConfigData")
	public String getImportProtocolAlarmContentEnumItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String type = ParamUtils.getParameter(request, "type");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolAlarmContentEnumItemsConfigData(id,type,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolAlarmInstanceFESDiagramResultItemsConfigData")
	public String getProtocolAlarmInstanceFESDiagramResultItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String resolutionMode = ParamUtils.getParameter(request, "resolutionMode");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolAlarmInstanceFESDiagramResultItemsConfigData(id,classes,resolutionMode,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportProtocolAlarmContentFESDiagramResultItemsConfigData")
	public String getImportProtocolAlarmContentFESDiagramResultItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String type = ParamUtils.getParameter(request, "type");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolAlarmContentFESDiagramResultItemsConfigData(id,type,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolAlarmInstanceRunStatusItemsConfigData")
	public String getProtocolAlarmInstanceRunStatusItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String resolutionMode = ParamUtils.getParameter(request, "resolutionMode");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolAlarmInstanceRunStatusItemsConfigData(id,classes,resolutionMode,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportProtocolAlarmContentRunStatusItemsConfigData")
	public String getImportProtocolAlarmContentRunStatusItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String type = ParamUtils.getParameter(request, "type");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolAlarmContentRunStatusItemsConfigData(id,type,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolAlarmInstanceCommStatusItemsConfigData")
	public String getProtocolAlarmInstanceCommStatusItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String resolutionMode = ParamUtils.getParameter(request, "resolutionMode");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolAlarmInstanceCommStatusItemsConfigData(id,classes,resolutionMode,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportProtocolAlarmContentCommStatusItemsConfigData")
	public String getImportProtocolAlarmContentCommStatusItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String type = ParamUtils.getParameter(request, "type");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolAlarmContentCommStatusItemsConfigData(id,type,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/acquisitionUnitTreeData")
	public String acquisitionUnitTreeData() throws IOException {
		String protocol = ParamUtils.getParameter(request, "protocol");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.getAcquisitionUnitTreeData(protocol,language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportAcqUnitTreeData")
	public String exportAcqUnitTreeData() throws IOException {
		String deviceTypeIds = ParamUtils.getParameter(request, "deviceTypeIds");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.exportAcqUnitTreeData(deviceTypeIds,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportAlarmUnitTreeData")
	public String exportAlarmUnitTreeData() throws IOException {
		String deviceTypeIds = ParamUtils.getParameter(request, "deviceTypeIds");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.exportAlarmUnitTreeData(deviceTypeIds,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/displayUnitTreeData")
	public String displayUnitTreeData() throws IOException {
		String protocol = ParamUtils.getParameter(request, "protocol");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.getDisplayUnitTreeData(protocol,language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportDisplayUnitTreeData")
	public String exportDisplayUnitTreeData() throws IOException {
		String deviceTypeIds = ParamUtils.getParameter(request, "deviceTypeIds");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.exportDisplayUnitTreeData(deviceTypeIds,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/modbusProtocolAddrMappingTreeData")
	public String modbusProtocolAddrMappingTreeData() throws IOException {
		String deviceTypeIds = ParamUtils.getParameter(request, "deviceTypeIds");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.modbusProtocolAddrMappingTreeData(deviceTypeIds,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportProtocolTreeData")
	public String exportProtocolTreeData() throws IOException {
		String deviceTypeIds = ParamUtils.getParameter(request, "deviceTypeIds");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.exportProtocolTreeData(deviceTypeIds,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/modbusProtocolAndAcqUnitTreeData")
	public String modbusProtocolAndAcqUnitTreeData() throws IOException {
		String deviceTypeIds=ParamUtils.getParameter(request, "deviceTypeIds");
		String protocol = ParamUtils.getParameter(request, "protocol");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.modbusProtocolAndAcqUnitTreeData(deviceTypeIds,protocol,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/modbusProtocolAndDisplayUnitTreeData")
	public String modbusProtocolAndDisplayUnitTreeData() throws IOException {
		String deviceTypeIds=ParamUtils.getParameter(request, "deviceTypeIds");
		String protocol = ParamUtils.getParameter(request, "protocol");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.modbusProtocolAndDisplayUnitTreeData(deviceTypeIds,protocol,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/repoerUnitTreeData")
	public String repoerUnitTreeData() throws IOException {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.repoerUnitTreeData(language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportReportUnitTreeData")
	public String exportReportUnitTreeData() throws IOException {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.exportReportUnitTreeData(language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getReportDataTemplateList")
	public String getReportDataTemplateList() throws IOException {
		String reportType=ParamUtils.getParameter(request, "reportType");
		String json = acquisitionUnitItemManagerService.getReportDataTemplateList(reportType);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/modbusProtocolAndAlarmUnitTreeData")
	public String modbusProtocolAndAlarmUnitTreeData() throws IOException {
		String deviceTypeIds=ParamUtils.getParameter(request, "deviceTypeIds");
		String protocol = ParamUtils.getParameter(request, "protocol");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.modbusProtocolAndAlarmUnitTreeData(deviceTypeIds,protocol,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/modbusProtocolAlarmUnitTreeData")
	public String modbusProtocolAlarmUnitTreeData() throws IOException {
		String protocol=ParamUtils.getParameter(request, "protocol");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.modbusProtocolAlarmUnitTreeData(protocol,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/modbusInstanceConfigTreeData")
	public String modbusInstanceConfigTreeData() throws IOException {
		String protocol = ParamUtils.getParameter(request, "protocol");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.getModbusProtocolInstanceConfigTreeData(protocol,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportProtocolAcqInstanceTreeData")
	public String exportProtocolAcqInstanceTreeData() throws IOException {
		String deviceTypeIds = ParamUtils.getParameter(request, "deviceTypeIds");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.exportProtocolAcqInstanceTreeData(deviceTypeIds,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getAcqUnitList")
	public String getAcqUnitList() throws IOException {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String protocol = ParamUtils.getParameter(request, "protocol");
		String json = acquisitionUnitItemManagerService.getAcqUnitList(user,protocol);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getDisplayUnitList")
	public String getDisplayUnitList() throws IOException {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String protocol = ParamUtils.getParameter(request, "protocol");
		String json = acquisitionUnitItemManagerService.getDisplayUnitList(user,protocol);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getAlarmUnitList")
	public String getAlarmUnitList() throws IOException {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String protocol = ParamUtils.getParameter(request, "protocol");
		String json = acquisitionUnitItemManagerService.getAlarmUnitList(user,protocol);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getReportUnitList")
	public String getReportUnitList() throws IOException {
		String json = acquisitionUnitItemManagerService.getReportUnitList();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/modbusDisplayInstanceConfigTreeData")
	public String modbusDisplayInstanceConfigTreeData() throws IOException {
		String protocol = ParamUtils.getParameter(request, "protocol");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.getModbusDisplayProtocolInstanceConfigTreeData(protocol,language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportProtocolDisplayInstanceTreeData")
	public String exportProtocolDisplayInstanceTreeData() throws IOException {
		String deviceTypeIds = ParamUtils.getParameter(request, "deviceTypeIds");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.exportProtocolDisplayInstanceTreeData(deviceTypeIds,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/modbusReportInstanceConfigTreeData")
	public String modbusReportInstanceConfigTreeData() throws IOException {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = protocolReportInstanceManagerService.modbusReportInstanceConfigTreeData(language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportProtocolReportInstanceTreeData")
	public String exportProtocolReportInstanceTreeData() throws IOException {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = protocolReportInstanceManagerService.getExportProtocolReportInstanceTreeData(language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/modbusAlarmInstanceConfigTreeData")
	public String modbusAlarmInstanceConfigTreeData() throws IOException {
		String protocol = ParamUtils.getParameter(request, "protocol");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.getModbusProtocolAlarmInstanceConfigTreeData(protocol,language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportProtocolAlarmInstanceTreeData")
	public String exportProtocolAlarmInstanceTreeData() throws IOException {
		String deviceTypeIds=ParamUtils.getParameter(request, "deviceTypeIds");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = acquisitionUnitItemManagerService.exportProtocolAlarmInstanceTreeData(deviceTypeIds,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	
	@RequestMapping("/getSMSInstanceList")
	public String getSMSInstanceList() throws Exception {
		String json = "";
		String name = ParamUtils.getParameter(request, "name");
		this.pager = new Page("pagerForm", request);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		json = acquisitionUnitItemManagerService.getSMSInstanceList(name,pager,language);
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
	
	
	@RequestMapping("/getModbusProtoclCombList")
	public String getModbusProtoclCombList() throws IOException {
		String deviceTypeIds=ParamUtils.getParameter(request, "deviceTypeIds");
		String protocol=ParamUtils.getParameter(request, "protocol");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json=acquisitionUnitItemManagerService.getModbusProtoclCombList(deviceTypeIds,protocol,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getReportTemplateCombList")
	public String getReportTemplateCombList() throws IOException {
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		String json=acquisitionUnitItemManagerService.getReportTemplateCombList(deviceType);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getReportUnitCombList")
	public String getReportUnitCombList() throws IOException {
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		String json=acquisitionUnitItemManagerService.getReportUnitCombList();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getAcquisitionUnitCombList")
	public String getAcquisitionUnitCombList() throws IOException {
		String protocol = ParamUtils.getParameter(request, "protocol");
		String deviceTypeIds = ParamUtils.getParameter(request, "deviceTypeIds");
		String selectedProtocol = ParamUtils.getParameter(request, "selectedProtocol");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json=acquisitionUnitItemManagerService.getAcquisitionUnitCombList(protocol,deviceTypeIds,selectedProtocol,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveModbusProtocolAddrMappingConfigData")
	public String SaveModbusProtocolAddrMappingConfigData() throws Exception {
		String json = "";
		Gson gson = new Gson();
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String data = ParamUtils.getParameter(request, "data");
		int saveType =StringManagerUtils.stringToInteger(ParamUtils.getParameter(request, "saveType"));
		data = StringManagerUtils.superscriptToNormal(data);
		try{
			java.lang.reflect.Type type = new TypeToken<ModbusDriverSaveData>() {}.getType();
			ModbusDriverSaveData modbusDriverSaveData=gson.fromJson(data, type);
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			if(modbusDriverSaveData!=null){
				modbusDriverSaveData.dataFiltering();
				
				ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
						Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
						Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
						TimeUnit.SECONDS, 
						Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
				
				//删除协议
				if(modbusDriverSaveData.getDelidslist()!=null && modbusDriverSaveData.getDelidslist().size()>0){
					for(int i=0;i<modbusDriverSaveData.getDelidslist().size();i++){
						for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
							if(modbusDriverSaveData.getDelidslist().get(i).equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getCode())){
								DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
								dataSynchronizationThread.setSign(002);
								dataSynchronizationThread.setParam1(modbusProtocolConfig.getProtocol().get(j).getName());
								dataSynchronizationThread.setParam2(modbusProtocolConfig.getProtocol().get(j).getDeviceType()+"");
								dataSynchronizationThread.setMethod("delete");
								dataSynchronizationThread.setAcquisitionUnitManagerService(acquisitionUnitManagerService);
								executor.execute(dataSynchronizationThread);
								
//								modbusProtocolConfig.getProtocol().remove(j);
								if(user!=null){
									this.service.saveSystemLog(user,2,languageResourceMap.get("deleteProtocol")+":"+modbusDriverSaveData.getDelidslist().get(i));
								}
								break;
							}
						}
					}
				}
				
				String oldDeviceType="";
				if(StringManagerUtils.isNotNull(modbusDriverSaveData.getProtocolName())){
					for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
						if(modbusDriverSaveData.getProtocolCode().equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getCode())){
							String oldName=modbusProtocolConfig.getProtocol().get(i).getName();
							oldDeviceType=modbusProtocolConfig.getProtocol().get(i).getDeviceType()+"";
							List<String> delItemList=new ArrayList<String>();
							if(saveType==0){
								modbusProtocolConfig.getProtocol().get(i).setName(modbusDriverSaveData.getProtocolName());
								modbusProtocolConfig.getProtocol().get(i).setSort(StringManagerUtils.isNumber(modbusDriverSaveData.getSort())?StringManagerUtils.stringToInteger(modbusDriverSaveData.getSort()):0);
							}else if(saveType==1){
								for(int j=0;j<modbusDriverSaveData.getDataConfig().size();j++){
									boolean isAddItem=true;
									String acqMode="passive";
									if(languageResourceMap.get("activeAcqModel").equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getAcqMode())){
										acqMode="active";
									}else if(languageResourceMap.get("passiveAcqModel").equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getAcqMode())){
										acqMode="passive";
									}
									String RWType="r";
									if(languageResourceMap.get("readWrite").equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getRWType())){
										RWType="rw";
									}else if(languageResourceMap.get("readOnly").equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getRWType())){
										RWType="r";
									}else if(languageResourceMap.get("writeOnly").equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getRWType())){
										RWType="w";
									}
									int resolutionMode=2;
									if(languageResourceMap.get("switchingValue").equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getResolutionMode())){
										resolutionMode=0;
									}else if(languageResourceMap.get("enumValue").equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getResolutionMode())){
										resolutionMode=1;
									}
									for(int k=0;k<modbusProtocolConfig.getProtocol().get(i).getItems().size();k++){
										if(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle().equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getTitle())){
											isAddItem=false;
											modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setAddr(modbusDriverSaveData.getDataConfig().get(j).getAddr());
											modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setQuantity(modbusDriverSaveData.getDataConfig().get(j).getQuantity());
											modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setUnit(modbusDriverSaveData.getDataConfig().get(j).getUnit());
											modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setRatio(modbusDriverSaveData.getDataConfig().get(j).getRatio());
											modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setStoreDataType(modbusDriverSaveData.getDataConfig().get(j).getStoreDataType());
											modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setIFDataType(modbusDriverSaveData.getDataConfig().get(j).getIFDataType());
											modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setPrec(modbusDriverSaveData.getDataConfig().get(j).getIFDataType().toLowerCase().indexOf("float")>=0?modbusDriverSaveData.getDataConfig().get(j).getPrec():0);
											//如果读写模式改变
											String oldRWType=modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getRWType();
											if(("r".equalsIgnoreCase(oldRWType) && "w".equalsIgnoreCase(RWType))||("w".equalsIgnoreCase(oldRWType) && "r".equalsIgnoreCase(RWType))){
												delItemList.add(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getTitle());
											}
											
											modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setRWType(RWType);
											modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setResolutionMode(resolutionMode);
											modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setAcqMode(acqMode);
											
											if(modbusDriverSaveData.getDataConfig().get(j).getMeaning()!=null){
												modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setMeaning(new ArrayList<ItemsMeaning>());
												for(int m=0;m<modbusDriverSaveData.getDataConfig().get(j).getMeaning().size();m++){
													ItemsMeaning itemsMeaning=new ItemsMeaning();
													itemsMeaning.setValue(modbusDriverSaveData.getDataConfig().get(j).getMeaning().get(m).getValue());
													itemsMeaning.setMeaning(modbusDriverSaveData.getDataConfig().get(j).getMeaning().get(m).getMeaning());
													modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getMeaning().add(itemsMeaning);
												}
												if(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getMeaning()!=null&&modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getMeaning().size()>0){
													Collections.sort(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getMeaning());
												}
											}
											break;
										}
									}
									if(isAddItem){
										ModbusProtocolConfig.Items item=new ModbusProtocolConfig.Items();
										item.setTitle(modbusDriverSaveData.getDataConfig().get(j).getTitle());
										item.setAddr(modbusDriverSaveData.getDataConfig().get(j).getAddr());
										item.setQuantity(modbusDriverSaveData.getDataConfig().get(j).getQuantity());
										item.setUnit(modbusDriverSaveData.getDataConfig().get(j).getUnit());
										item.setRatio(modbusDriverSaveData.getDataConfig().get(j).getRatio());
										item.setStoreDataType(modbusDriverSaveData.getDataConfig().get(j).getStoreDataType());
										item.setIFDataType(modbusDriverSaveData.getDataConfig().get(j).getIFDataType());
										item.setPrec(modbusDriverSaveData.getDataConfig().get(j).getIFDataType().toLowerCase().indexOf("float")>=0?modbusDriverSaveData.getDataConfig().get(j).getPrec():0);
										item.setRWType(RWType);
										item.setResolutionMode(resolutionMode);
										item.setAcqMode(acqMode);
										if(modbusDriverSaveData.getDataConfig().get(j).getMeaning()!=null && modbusDriverSaveData.getDataConfig().get(j).getMeaning().size()>0){
											item.setMeaning(new ArrayList<ItemsMeaning>());
											for(int m=0;m<modbusDriverSaveData.getDataConfig().get(j).getMeaning().size();m++){
												ItemsMeaning itemsMeaning=new ItemsMeaning();
												itemsMeaning.setValue(modbusDriverSaveData.getDataConfig().get(j).getMeaning().get(m).getValue());
												itemsMeaning.setMeaning(modbusDriverSaveData.getDataConfig().get(j).getMeaning().get(m).getMeaning());
												item.getMeaning().add(itemsMeaning);
											}
											if(item.getMeaning()!=null&&item.getMeaning().size()>0){
												Collections.sort(item.getMeaning());
											}
										}
										modbusProtocolConfig.getProtocol().get(i).getItems().add(item);
									}
								}
								
								//处理删除项
								Iterator<Items> it = modbusProtocolConfig.getProtocol().get(i).getItems().iterator();
								while(it.hasNext()){
									boolean isDel=true;
									Items item=(Items)it.next();
									for(int k=0;k<modbusDriverSaveData.getDataConfig().size();k++){
										if(item.getTitle().equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(k).getTitle())){
											isDel=false;
											break;
										}
									}
									if(isDel){
										delItemList.add(item.getTitle());
										it.remove();
									}
								}
							}else if(saveType==2){
								List<ModbusProtocolConfig.ExtendedField> extendedFieldList=new ArrayList<>();
								for(int j=0;j<modbusDriverSaveData.getExtendedFieldConfig().size();j++){
									ModbusProtocolConfig.ExtendedField extendedField=new ModbusProtocolConfig.ExtendedField();
									extendedField.setTitle(modbusDriverSaveData.getExtendedFieldConfig().get(j).getTitle());
									extendedField.setTitle1(modbusDriverSaveData.getExtendedFieldConfig().get(j).getTitle1());
									extendedField.setTitle2(modbusDriverSaveData.getExtendedFieldConfig().get(j).getTitle2());
									extendedField.setOperation(StringManagerUtils.stringToInteger(MemoryDataManagerTask.getCodeValue("FOUROPERATION", modbusDriverSaveData.getExtendedFieldConfig().get(j).getOperation(), language)));
									extendedField.setPrec(modbusDriverSaveData.getExtendedFieldConfig().get(j).getPrec());
									extendedField.setRatio(modbusDriverSaveData.getExtendedFieldConfig().get(j).getRatio());
									extendedField.setUnit(modbusDriverSaveData.getExtendedFieldConfig().get(j).getUnit());
									extendedField.setAdditionalConditions(StringManagerUtils.stringToInteger(MemoryDataManagerTask.getCodeValue("ADDITIONALCONDITIONS", modbusDriverSaveData.getExtendedFieldConfig().get(j).getAdditionalConditions(), language)));
									extendedFieldList.add(extendedField);
								}
								modbusProtocolConfig.getProtocol().get(i).setExtendedFields(extendedFieldList);
							}
							
							//如果协议名称改变，更新数据库
							if(!oldName.equals(modbusDriverSaveData.getProtocolName())){
								EquipmentDriverServerTask.deleteInitializedProtocolConfig(oldName,modbusProtocolConfig.getProtocol().get(i).getDeviceType()+"");
//								String unitSql="update tbl_acq_unit_conf t set t.protocol='"+modbusDriverSaveData.getProtocolName()+"' where t.protocol='"+oldName+"'";
//								String groupSql="update tbl_acq_group_conf t set t.protocol='"+modbusDriverSaveData.getProtocolName()+"' where t.protocol='"+oldName+"'";
//								String alatmUnitSql="update tbl_alarm_unit_conf t set t.protocol='"+modbusDriverSaveData.getProtocolName()+"' where t.protocol='"+oldName+"'";
//								String displayUnitSql="update tbl_display_unit_conf t set t.protocol='"+modbusDriverSaveData.getProtocolName()+"' where t.protocol='"+oldName+"'";
//								service.updateSql(unitSql);
//								service.updateSql(groupSql);
//								service.updateSql(alatmUnitSql);
//								service.updateSql(displayUnitSql);
							}
							if(delItemList.size()>0){
								String delSql="delete from tbl_acq_item2group_conf t5 "
										+ " where t5.id in "
										+ "(select t4.id from tbl_acq_unit_conf t,tbl_acq_group2unit_conf t2,tbl_acq_group_conf t3,tbl_acq_item2group_conf t4 "
										+ " where t.id=t2.unitid and t2.groupid=t3.id and t3.id=t4.groupid "
										+ "and t.protocol='"+modbusDriverSaveData.getProtocolCode()+"' "
										+ " and t4.itemname in ("+StringManagerUtils.joinStringArr2(delItemList, ",")+"))";
								String delDisplayItemSql="delete from tbl_display_items2unit_conf t "
										+ "where t.type<>1 "
										+ "and t.unitid in ( select t2.id from tbl_display_unit_conf t2 ,tbl_acq_unit_conf t3 where t2.acqunitid=t3.id and t3.protocol='"+modbusDriverSaveData.getProtocolCode()+"'  )"
										+ " and t.itemname in ("+StringManagerUtils.joinStringArr2(delItemList, ",")+")";
								service.updateSql(delSql);
								service.updateSql(delDisplayItemSql);
							}
							Collections.sort(modbusProtocolConfig.getProtocol().get(i).getItems());
							if(saveType==0){
								String updateSql="update TBL_PROTOCOL t set t.name='"+modbusProtocolConfig.getProtocol().get(i).getName()+"',"
										+ " t.sort="+modbusProtocolConfig.getProtocol().get(i).getSort()
										+" where t.code='"+modbusProtocolConfig.getProtocol().get(i).getCode()+"'";
								service.updateSql(updateSql);
							}else if(saveType==1){
								String updateProtocolItemsClobSql="update TBL_PROTOCOL t set t.items=? where t.code='"+modbusProtocolConfig.getProtocol().get(i).getCode()+"'";
								List<String> clobCont=new ArrayList<String>();
								clobCont.add(gson.toJson(modbusProtocolConfig.getProtocol().get(i).getItems()));
								service.getBaseDao().executeSqlUpdateClob(updateProtocolItemsClobSql,clobCont);
							}else if(saveType==2){
								String updateProtocolExtendedFieldClobSql="update TBL_PROTOCOL t set t.extendedfield=? where t.code='"+modbusProtocolConfig.getProtocol().get(i).getCode()+"'";
								List<String> clobCont=new ArrayList<String>();
								clobCont.add(gson.toJson(modbusProtocolConfig.getProtocol().get(i).getExtendedFields()));
								service.getBaseDao().executeSqlUpdateClob(updateProtocolExtendedFieldClobSql,clobCont);
							}
							
							
							if(user!=null){
								this.service.saveSystemLog(user,2,languageResourceMap.get("editProtocol")+":"+modbusProtocolConfig.getProtocol().get(i).getName());
							}
							break;
						}
					}
					Collections.sort(modbusProtocolConfig.getProtocol());
				}
				MemoryDataManagerTask.updateProtocolConfig(modbusProtocolConfig);
				if(StringManagerUtils.isNotNull(modbusDriverSaveData.getProtocolName())){
					DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
					dataSynchronizationThread.setSign(003);
					dataSynchronizationThread.setParam1(modbusDriverSaveData.getProtocolName());
					dataSynchronizationThread.setParam2(oldDeviceType);
					dataSynchronizationThread.setMethod("update");
					executor.execute(dataSynchronizationThread);
				}
			}
			json ="{success:true}";
		}catch(Exception e){
			e.printStackTrace();
			json ="{success:false}";
		}
		
		response.setContentType("application/json;charset="+Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAcquisitionUnitHandsontableData")
	public String saveAcquisitionUnitHandsontableData() throws Exception {
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll("null", "");
		String protocol = ParamUtils.getParameter(request, "protocol");
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		
		Gson gson = new Gson();
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		java.lang.reflect.Type type = new TypeToken<AcquisitionUnitHandsontableChangeData>() {}.getType();
		AcquisitionUnitHandsontableChangeData acquisitionUnitHandsontableChangeData=gson.fromJson(data, type);
		if(acquisitionUnitHandsontableChangeData!=null){
			if(acquisitionUnitHandsontableChangeData.getDelidslist()!=null){
				ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
						Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
						Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
						TimeUnit.SECONDS, 
						Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
				for(int i=0;i<acquisitionUnitHandsontableChangeData.getDelidslist().size();i++){
					DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
					dataSynchronizationThread.setSign(011);
					dataSynchronizationThread.setParam1(acquisitionUnitHandsontableChangeData.getDelidslist().get(i));
					dataSynchronizationThread.setMethod("delete");
					dataSynchronizationThread.setAcquisitionUnitManagerService(acquisitionUnitManagerService);
					executor.execute(dataSynchronizationThread);
					
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("deleteAcqUnit"));
					}
				}
			}
			if(acquisitionUnitHandsontableChangeData.getUpdatelist()!=null){
				for(int i=0;i<acquisitionUnitHandsontableChangeData.getUpdatelist().size();i++){
					AcquisitionUnit acquisitionUnit=new AcquisitionUnit();
					
					String sort=acquisitionUnitHandsontableChangeData.getUpdatelist().get(i).getSort();
					acquisitionUnit.setId(StringManagerUtils.stringToInteger(acquisitionUnitHandsontableChangeData.getUpdatelist().get(i).getId()));
					acquisitionUnit.setUnitCode(acquisitionUnitHandsontableChangeData.getUpdatelist().get(i).getUnitCode());
					acquisitionUnit.setUnitName(acquisitionUnitHandsontableChangeData.getUpdatelist().get(i).getUnitName());
					
					acquisitionUnit.setSort(StringManagerUtils.isNumber(sort)?StringManagerUtils.stringToInteger(sort):null);
					
					
					acquisitionUnit.setRemark(acquisitionUnitHandsontableChangeData.getUpdatelist().get(i).getRemark());
					acquisitionUnit.setProtocol(protocol);
					this.acquisitionUnitManagerService.doAcquisitionUnitEdit(acquisitionUnit);
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("editAcqUnit")+":"+acquisitionUnitHandsontableChangeData.getUpdatelist().get(i).getUnitName());
					}
				}
			}
			
			if(acquisitionUnitHandsontableChangeData.getInsertlist()!=null){
				for(int i=0;i<acquisitionUnitHandsontableChangeData.getInsertlist().size();i++){
					AcquisitionUnit acquisitionUnit=new AcquisitionUnit();
					acquisitionUnit.setId(StringManagerUtils.stringToInteger(acquisitionUnitHandsontableChangeData.getInsertlist().get(i).getId()));
					acquisitionUnit.setUnitCode(acquisitionUnitHandsontableChangeData.getInsertlist().get(i).getUnitCode());
					acquisitionUnit.setUnitName(acquisitionUnitHandsontableChangeData.getInsertlist().get(i).getUnitName());
					acquisitionUnit.setRemark(acquisitionUnitHandsontableChangeData.getInsertlist().get(i).getRemark());
					acquisitionUnit.setProtocol(protocol);
					this.acquisitionUnitManagerService.doAcquisitionUnitAdd(acquisitionUnit);
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("editAcqUnit")+":"+acquisitionUnitHandsontableChangeData.getInsertlist().get(i).getUnitName());
					}
				}
			}
			
		}
		String json ="{success:true}";
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
	@RequestMapping("/saveAcquisitionGroupHandsontableData")
	public String saveAcquisitionGroupHandsontableData() throws Exception {
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll("null", "");
		String protocol = ParamUtils.getParameter(request, "protocol");
		String unitId = ParamUtils.getParameter(request, "unitId");
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<AcquisitionGroupHandsontableChangeData>() {}.getType();
		AcquisitionGroupHandsontableChangeData acquisitionGroupHandsontableChangeData=gson.fromJson(data, type);
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		if(acquisitionGroupHandsontableChangeData!=null){
			if(acquisitionGroupHandsontableChangeData.getDelidslist()!=null){
				ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
						Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
						Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
						TimeUnit.SECONDS, 
						Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
				for(int i=0;i<acquisitionGroupHandsontableChangeData.getDelidslist().size();i++){
					this.acquisitionUnitManagerService.doAcquisitionGroupBulkDelete(acquisitionGroupHandsontableChangeData.getDelidslist().get(i));
					DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
					dataSynchronizationThread.setSign(022);
					dataSynchronizationThread.setParam1(unitId);
					dataSynchronizationThread.setMethod("update");
					executor.execute(dataSynchronizationThread);
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("deleteAcqGroup"));
					}
				}
			}
			if(acquisitionGroupHandsontableChangeData.getUpdatelist()!=null){
				for(int i=0;i<acquisitionGroupHandsontableChangeData.getUpdatelist().size();i++){
					AcquisitionGroup acquisitionGroup=new AcquisitionGroup();
					acquisitionGroup.setId(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getId()));
					acquisitionGroup.setGroupCode(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getGroupCode());
					acquisitionGroup.setGroupName(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getGroupName());
					acquisitionGroup.setType(languageResourceMap.get("acqGroup").equalsIgnoreCase(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getTypeName())?0:1);
					acquisitionGroup.setGroupTimingInterval(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getGroupTimingInterval()));
					acquisitionGroup.setGroupSavingInterval(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getGroupSavingInterval()));
					acquisitionGroup.setRemark(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getRemark());
					acquisitionGroup.setProtocol(protocol);
					this.acquisitionUnitManagerService.doAcquisitionGroupEdit(acquisitionGroup);
//					EquipmentDriverServerTask.initInstanceConfigByAcqGroupId(acquisitionGroup.getId()+"", "update");
					
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("editAcqGroup")+":"+acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getGroupName());
					}
				}
			}
			
			if(acquisitionGroupHandsontableChangeData.getInsertlist()!=null){
				for(int i=0;i<acquisitionGroupHandsontableChangeData.getInsertlist().size();i++){
					AcquisitionGroup acquisitionGroup=new AcquisitionGroup();
					acquisitionGroup.setId(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getId()));
					acquisitionGroup.setGroupCode(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getGroupCode());
					acquisitionGroup.setGroupName(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getGroupName());
					acquisitionGroup.setType(languageResourceMap.get("acqGroup").equalsIgnoreCase(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getTypeName())?0:1);
					acquisitionGroup.setGroupTimingInterval(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getGroupTimingInterval()));
					acquisitionGroup.setGroupSavingInterval(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getGroupSavingInterval()));
					acquisitionGroup.setRemark(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getRemark());
					acquisitionGroup.setProtocol(protocol);
					this.acquisitionUnitManagerService.doAcquisitionGroupAdd(acquisitionGroup);
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("editAcqGroup")+":"+acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getGroupName());
					}
				}
			}
			
		}
		String json ="{success:true}";
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
//		EquipmentDriverServerTask beeTechDriverServerTast=EquipmentDriverServerTask.getInstance();
//		beeTechDriverServerTast.updateWellConfif(wellHandsontableChangedData);
		return null;
	}
	
	@RequestMapping("/saveDisplayUnitHandsontableData")
	public String saveDisplayUnitHandsontableData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll("null", "");
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<DisplayUnitHandsontableChangeData>() {}.getType();
		DisplayUnitHandsontableChangeData displayUnitHandsontableChangeData=gson.fromJson(data, type);
		if(displayUnitHandsontableChangeData!=null){
			if(displayUnitHandsontableChangeData.getDelidslist()!=null){
				ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
						Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
						Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
						TimeUnit.SECONDS, 
						Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
				for(int i=0;i<displayUnitHandsontableChangeData.getDelidslist().size();i++){
					DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
					dataSynchronizationThread.setSign(041);
					dataSynchronizationThread.setParam1(displayUnitHandsontableChangeData.getDelidslist().get(i));
					dataSynchronizationThread.setMethod("delete");
					dataSynchronizationThread.setAcquisitionUnitManagerService(acquisitionUnitManagerService);
					executor.execute(dataSynchronizationThread);
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("deleteDisplayUnit"));
					}
				}
			}
			if(displayUnitHandsontableChangeData.getUpdatelist()!=null){
				for(int i=0;i<displayUnitHandsontableChangeData.getUpdatelist().size();i++){
					String acqUnitText=displayUnitHandsontableChangeData.getUpdatelist().get(i).getAcqUnitName();
					String protocol="";
					String acqUnitName="";
					if(acqUnitText.contains("/")){
						String[] textArr=acqUnitText.split("/");
						if(textArr.length==2){
							protocol=textArr[0];
							acqUnitName=textArr[1];
						}
					}
					
					String sql="select t.id from tbl_acq_unit_conf t,tbl_protocol t2 "
							+ " where t.protocol=t2.code"
							+ " and t.unit_name='"+acqUnitName+"' "
							+ " and t2.code='"+protocol+"' ";
					sql+= " and rownum=1";
					String acqUnitId="";
					List<?> list = this.service.findCallSql(sql);
					if(list.size()>0){
						acqUnitId=list.get(0).toString();
					}

					//如果采集单元发生改变，删除已配置显示项
					if(StringManagerUtils.stringToInteger(acqUnitId)!=StringManagerUtils.stringToInteger(displayUnitHandsontableChangeData.getUpdatelist().get(i).getAcqUnitId())){
						this.displayUnitItemManagerService.deleteCurrentDisplayUnitOwnItems(displayUnitHandsontableChangeData.getUpdatelist().get(i).getId());
					}
					
					DisplayUnit displayUnit=new DisplayUnit();
					String sort=displayUnitHandsontableChangeData.getUpdatelist().get(i).getSort();
					displayUnit.setId(StringManagerUtils.stringToInteger(displayUnitHandsontableChangeData.getUpdatelist().get(i).getId()));
					displayUnit.setUnitCode(displayUnitHandsontableChangeData.getUpdatelist().get(i).getUnitCode());
					displayUnit.setUnitName(displayUnitHandsontableChangeData.getUpdatelist().get(i).getUnitName());
					displayUnit.setCalculateType(StringManagerUtils.stringToInteger(displayUnitHandsontableChangeData.getUpdatelist().get(i).getCalculateType()));
					displayUnit.setSort(StringManagerUtils.isNumber(sort)?StringManagerUtils.stringToInteger(sort):null);
					displayUnit.setRemark(displayUnitHandsontableChangeData.getUpdatelist().get(i).getRemark());
					displayUnit.setProtocol(protocol);
					displayUnit.setAcqUnitId(StringManagerUtils.stringToInteger(acqUnitId));
					this.displayUnitManagerService.doDisplayUnitEdit(displayUnit);
					
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("editDisplayUnit")+":"+displayUnitHandsontableChangeData.getUpdatelist().get(i).getUnitName());
					}
				}
			}
			
			if(displayUnitHandsontableChangeData.getInsertlist()!=null){
				for(int i=0;i<displayUnitHandsontableChangeData.getInsertlist().size();i++){
					DisplayUnit displayUnit=new DisplayUnit();
					displayUnit.setUnitCode(displayUnitHandsontableChangeData.getInsertlist().get(i).getUnitCode());
					displayUnit.setUnitName(displayUnitHandsontableChangeData.getInsertlist().get(i).getUnitName());
					displayUnit.setRemark(displayUnitHandsontableChangeData.getInsertlist().get(i).getRemark());
					displayUnit.setAcqUnitId(StringManagerUtils.stringToInteger(displayUnitHandsontableChangeData.getUpdatelist().get(i).getAcqUnitId()));
					this.displayUnitManagerService.doDisplayUnitAdd(displayUnit);
					
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("editDisplayUnit")+":"+displayUnitHandsontableChangeData.getInsertlist().get(i).getUnitName());
					}
				}
			}
			
		}
		String json ="{success:true}";
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/doModbusProtocolInstanceAdd")
	public String doModbusProtocolInstanceAdd(@ModelAttribute ProtocolInstance protocolInstance) throws IOException {
		String result = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			if(protocolInstance.getPacketSendInterval()==null){
				protocolInstance.setPacketSendInterval(100);
			}
			if(protocolInstance.getId()==null){
				protocolInstance.setId(1);
			}
			this.protocolInstanceManagerService.doModbusProtocolInstanceAdd(protocolInstance);
			
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("addAcqInstance")+":"+protocolInstance.getName());
			}
			
			List<String> instanceList=new ArrayList<String>();
			instanceList.add(protocolInstance.getName());
			DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
			dataSynchronizationThread.setSign(051);
			dataSynchronizationThread.setParam1(protocolInstance.getName());
			dataSynchronizationThread.setParam2(protocolInstance.getUnitId()+"");
			dataSynchronizationThread.setMethod("update");
			dataSynchronizationThread.setInitWellList(instanceList);
			
			ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
			executor.execute(dataSynchronizationThread);
//			MemoryDataManagerTask.loadAcqInstanceOwnItemByCode(protocolInstance.getCode(),"update");
//			EquipmentDriverServerTask.initInstanceConfig(instanceList, "update");
			result = "{success:true,msg:true}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
		}
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(result);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/doAlarmUnitAdd")
	public String doAlarmUnitAdd(@ModelAttribute AlarmUnit alarmUnit) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			this.alarmUnitManagerService.doAlarmUnitAdd(alarmUnit);
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("addAlarmUnit")+":"+alarmUnit.getUnitName());
			}
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
		}
		out.print(result);
		out.flush();
		out.close();
		return null;
	}
	
	@RequestMapping("/doDisplayUnitAdd")
	public String doDisplayUnitAdd(@ModelAttribute DisplayUnit displayUnit) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			this.displayUnitManagerService.doDisplayUnitAdd(displayUnit);
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("addDisplayUnit")+":"+displayUnit.getUnitName());
			}
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
		}
		out.print(result);
		out.flush();
		out.close();
		return null;
	}
	
	@RequestMapping("/saveModbusProtocolAlarmUnitData")
	public String saveModbusProtocolAlarmUnitData() throws Exception {
		Gson gson=new Gson();
		String json ="{success:true}";
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<ModbusProtocolAlarmUnitSaveData>() {}.getType();
		ModbusProtocolAlarmUnitSaveData modbusProtocolAlarmUnitSaveData=gson.fromJson(data, type);
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		if(modbusProtocolAlarmUnitSaveData!=null){
			ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
			if(modbusProtocolAlarmUnitSaveData.getDelidslist()!=null){
				for(int i=0;i<modbusProtocolAlarmUnitSaveData.getDelidslist().size();i++){
					DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
					dataSynchronizationThread.setSign(031);
					dataSynchronizationThread.setParam1(modbusProtocolAlarmUnitSaveData.getDelidslist().get(i));
					dataSynchronizationThread.setMethod("delete");
					dataSynchronizationThread.setAcquisitionUnitManagerService(acquisitionUnitManagerService);
					executor.execute(dataSynchronizationThread);
					
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("deleteAlarmUnit"));
					}
				}
			}
			
			if(StringManagerUtils.isNotNull(modbusProtocolAlarmUnitSaveData.getUnitName())){
				AlarmUnit alarmUnit=new AlarmUnit();
				String sort=modbusProtocolAlarmUnitSaveData.getSort();
				alarmUnit.setId(modbusProtocolAlarmUnitSaveData.getId());
				alarmUnit.setUnitCode(modbusProtocolAlarmUnitSaveData.getUnitCode());
				alarmUnit.setUnitName(modbusProtocolAlarmUnitSaveData.getUnitName());
				alarmUnit.setProtocol(modbusProtocolAlarmUnitSaveData.getProtocol());
				alarmUnit.setCalculateType(modbusProtocolAlarmUnitSaveData.getCalculateType());
				alarmUnit.setSort(StringManagerUtils.isNumber(sort)?StringManagerUtils.stringToInteger(sort):null);
				alarmUnit.setRemark(modbusProtocolAlarmUnitSaveData.getRemark());
				try {
					this.alarmUnitManagerService.doAlarmUnitEdit(alarmUnit);
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("editAlarmUnit")+":"+modbusProtocolAlarmUnitSaveData.getUnitName());
					}
					json = "{success:true,msg:true}";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					json = "{success:false,msg:false}";
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
	
	@RequestMapping("/grantAlarmItemsToAlarmUnitPermission")
	public String grantAlarmItemsToAlarmUnitPermission() throws Exception {
		Gson gson=new Gson();
		String json ="{success:true}";
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<ModbusProtocolAlarmUnitSaveData>() {}.getType();
		ModbusProtocolAlarmUnitSaveData modbusProtocolAlarmUnitSaveData=gson.fromJson(data, type);
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		if(modbusProtocolAlarmUnitSaveData!=null){
			ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
			
			if(StringManagerUtils.isNotNull(modbusProtocolAlarmUnitSaveData.getUnitName())){
				try {
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("editAlarmUnit")+":"+modbusProtocolAlarmUnitSaveData.getUnitName());
					}
					this.alarmUnitManagerService.deleteCurrentAlarmUnitOwnItems(modbusProtocolAlarmUnitSaveData);
					if(modbusProtocolAlarmUnitSaveData.getAlarmItems()!=null){
						for(int i=0;i<modbusProtocolAlarmUnitSaveData.getAlarmItems().size();i++){
							if(StringManagerUtils.isNotNull(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getItemName())
									&&StringManagerUtils.isNotNull(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getItemAddr()+"")){
								AlarmUnitItem alarmUnitItem=new AlarmUnitItem();
								alarmUnitItem.setUnitId(modbusProtocolAlarmUnitSaveData.getId());
								alarmUnitItem.setItemName(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getItemName());
								alarmUnitItem.setItemCode(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getItemCode());
								alarmUnitItem.setItemAddr(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getItemAddr());
								alarmUnitItem.setType(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getType());
								
								alarmUnitItem.setBitIndex(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getBitIndex());
								alarmUnitItem.setValue(StringManagerUtils.stringToFloat(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getValue()));
								
								if(StringManagerUtils.isNotNull(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getUpperLimit())){
									alarmUnitItem.setUpperLimit(StringManagerUtils.stringToFloat(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getUpperLimit()));
								}
								if(StringManagerUtils.isNotNull(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getLowerLimit())){
									alarmUnitItem.setLowerLimit(StringManagerUtils.stringToFloat(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getLowerLimit()));
								}
								if(StringManagerUtils.isNotNull(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getHystersis())){
									alarmUnitItem.setHystersis(StringManagerUtils.stringToFloat(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getHystersis()));
								}
								
								if(StringManagerUtils.isNotNull(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getDelay())){
									alarmUnitItem.setDelay(StringManagerUtils.stringToInteger(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getDelay()));
								}
								
								if(StringManagerUtils.isNotNull(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getRetriggerTime())){
									alarmUnitItem.setRetriggerTime(StringManagerUtils.stringToInteger(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getRetriggerTime()));
								}
								
								int alarmLevel=0;
								int alarmSign=0;
								if(languageResourceMap.get("normal").equals(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getAlarmLevel())){
									alarmLevel=0;
								}else if(languageResourceMap.get("alarmLevel1").equals(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getAlarmLevel())){
									alarmLevel=100;
								}else if(languageResourceMap.get("alarmLevel2").equals(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getAlarmLevel())){
									alarmLevel=200;
								}else if(languageResourceMap.get("alarmLevel3").equals(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getAlarmLevel())){
									alarmLevel=300;
								}
								
								if(languageResourceMap.get("enable").equalsIgnoreCase(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getAlarmSign())){
									alarmSign=1;
								}else if(languageResourceMap.get("disable").equalsIgnoreCase(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getAlarmSign())){
									alarmSign=0;
								}
								
								alarmUnitItem.setAlarmLevel(alarmLevel);
								alarmUnitItem.setAlarmSign(alarmSign);
								
								int isSendMessage=0;
								int isSendMail=0;
								if(languageResourceMap.get("yes").equalsIgnoreCase(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getIsSendMessage())){
									isSendMessage=1;
								}else if(languageResourceMap.get("no").equalsIgnoreCase(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getIsSendMessage())){
									isSendMessage=0;
								}
								if(languageResourceMap.get("yes").equalsIgnoreCase(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getIsSendMail())){
									isSendMail=1;
								}else if(languageResourceMap.get("no").equalsIgnoreCase(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getIsSendMail())){
									isSendMail=0;
								}
								alarmUnitItem.setIsSendMessage(isSendMessage);
								alarmUnitItem.setIsSendMail(isSendMail);
								
								this.alarmUnitItemManagerService.grantAlarmItemsPermission(alarmUnitItem);
							}
						}
						DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
						dataSynchronizationThread.setSign(032);
						dataSynchronizationThread.setParam1(modbusProtocolAlarmUnitSaveData.getId()+"");
						dataSynchronizationThread.setMethod("update");
						executor.execute(dataSynchronizationThread);
//						MemoryDataManagerTask.loadAlarmInstanceOwnItemByUnitId(modbusProtocolAlarmUnitSaveData.getId()+"","update");
					}
					
					json = "{success:true,msg:true}";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					json = "{success:false,msg:false}";
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
	
	@RequestMapping("/saveProtocolInstanceData")
	public String saveProtocolInstanceData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Gson gson=new Gson();
		String json ="{success:true}";
		String data = ParamUtils.getParameter(request, "data");
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		java.lang.reflect.Type type = new TypeToken<ModbusProtocolInstanceSaveData>() {}.getType();
		ModbusProtocolInstanceSaveData modbusProtocolInstanceSaveData=gson.fromJson(data, type);
		
		if(modbusProtocolInstanceSaveData!=null){
			ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
			if(modbusProtocolInstanceSaveData.getDelidslist()!=null){
				for(int i=0;i<modbusProtocolInstanceSaveData.getDelidslist().size();i++){
					List<String> deleteInstanceList=new ArrayList<String>();
					deleteInstanceList.add(modbusProtocolInstanceSaveData.getName());
					
					DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
					dataSynchronizationThread.setSign(052);
					dataSynchronizationThread.setParam1(modbusProtocolInstanceSaveData.getDelidslist().get(i));
					dataSynchronizationThread.setMethod("delete");
					dataSynchronizationThread.setInitWellList(deleteInstanceList);
					dataSynchronizationThread.setAcquisitionUnitManagerService(acquisitionUnitManagerService);
					executor.execute(dataSynchronizationThread);
					
					
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("deleteAcqInstance"));
					}
					
//					EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByProtocolInstanceId(modbusProtocolInstanceSaveData.getDelidslist().get(i), "delete");
//					EquipmentDriverServerTask.initInstanceConfig(deleteInstanceList, "delete");
//					this.acquisitionUnitManagerService.doModbusProtocolInstanceBulkDelete(modbusProtocolInstanceSaveData.getDelidslist().get(i),modbusProtocolInstanceSaveData.getDeviceType());
				}
			}
			
			if(StringManagerUtils.isNotNull(modbusProtocolInstanceSaveData.getName()) && StringManagerUtils.isNotNull(modbusProtocolInstanceSaveData.getCode()) && modbusProtocolInstanceSaveData.getId()>0){
				String unitText=modbusProtocolInstanceSaveData.getUnitName();
				String protocol="";
				String unitName="";
				if(unitText.contains("/")){
					String[] textArr=unitText.split("/");
					if(textArr.length==2){
						protocol=textArr[0];
						unitName=textArr[1];
					}
				}
				
				String sql="select t.id from tbl_acq_unit_conf t,tbl_protocol t2 "
						+ " where t.protocol=t2.code"
						+ " and t.unit_name='"+unitName+"' "
						+ " and t2.code='"+protocolCode+"' ";
				sql+= " and rownum=1";
				String unitId="";
				List list = this.service.findCallSql(sql);
				if(list.size()>0){
					unitId=list.get(0).toString();
				}
				
				ProtocolInstance protocolInstance=new ProtocolInstance();
				protocolInstance.setId(modbusProtocolInstanceSaveData.getId());
				protocolInstance.setCode(modbusProtocolInstanceSaveData.getCode());
				protocolInstance.setName(modbusProtocolInstanceSaveData.getName());
				protocolInstance.setUnitId(StringManagerUtils.stringToInteger(unitId));
				protocolInstance.setAcqProtocolType(modbusProtocolInstanceSaveData.getAcqProtocolType());
				protocolInstance.setCtrlProtocolType(modbusProtocolInstanceSaveData.getCtrlProtocolType());
				
				protocolInstance.setSignInPrefixSuffixHex(modbusProtocolInstanceSaveData.getSignInPrefixSuffixHex());
				protocolInstance.setSignInPrefix(modbusProtocolInstanceSaveData.getSignInPrefix());
				protocolInstance.setSignInSuffix(modbusProtocolInstanceSaveData.getSignInSuffix());
				protocolInstance.setSignInIDHex(modbusProtocolInstanceSaveData.getSignInIDHex());
				
				protocolInstance.setHeartbeatPrefixSuffixHex(modbusProtocolInstanceSaveData.getHeartbeatPrefixSuffixHex());
				protocolInstance.setHeartbeatPrefix(modbusProtocolInstanceSaveData.getHeartbeatPrefix());
				protocolInstance.setHeartbeatSuffix(modbusProtocolInstanceSaveData.getHeartbeatSuffix());
				
				if(StringManagerUtils.isNum(modbusProtocolInstanceSaveData.getPacketSendInterval())){
					protocolInstance.setPacketSendInterval(StringManagerUtils.stringToInteger(modbusProtocolInstanceSaveData.getPacketSendInterval()));
				}else{
					protocolInstance.setPacketSendInterval(null);
				}
				
				if(StringManagerUtils.isNum(modbusProtocolInstanceSaveData.getSort())){
					protocolInstance.setSort(StringManagerUtils.stringToInteger(modbusProtocolInstanceSaveData.getSort()));
				}else{
					protocolInstance.setSort(null);
				}
				
				try {
					this.protocolInstanceManagerService.doModbusProtocolInstanceEdit(protocolInstance);
					
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("editAcqInstance")+":"+protocolInstance.getName());
					}
					
					//实例名称是否改变
					if(modbusProtocolInstanceSaveData.getName().equals(modbusProtocolInstanceSaveData.getOldName())){
						List<String> instanceList=new ArrayList<String>();
						instanceList.add(protocolInstance.getName());
						DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
						dataSynchronizationThread.setSign(053);
						dataSynchronizationThread.setParam1(modbusProtocolInstanceSaveData.getId()+"");
						dataSynchronizationThread.setInitWellList(instanceList);
						dataSynchronizationThread.setMethod("update");
						executor.execute(dataSynchronizationThread);
						
//						MemoryDataManagerTask.loadAcqInstanceOwnItemById(modbusProtocolInstanceSaveData.getId()+"", "update");
//						EquipmentDriverServerTask.initInstanceConfig(instanceList, "update");
//						EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByProtocolInstanceId(modbusProtocolInstanceSaveData.getId()+"", "update");
					}else{
						List<String> delInstanceList=new ArrayList<String>();
						delInstanceList.add(modbusProtocolInstanceSaveData.getOldName());
						List<String> instanceList=new ArrayList<String>();
						instanceList.add(modbusProtocolInstanceSaveData.getName());
						
						DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
						dataSynchronizationThread.setSign(054);
						dataSynchronizationThread.setDeleteList(delInstanceList);
						dataSynchronizationThread.setInitWellList(instanceList);
						dataSynchronizationThread.setParam1(modbusProtocolInstanceSaveData.getId()+"");
						executor.execute(dataSynchronizationThread);
						
//						EquipmentDriverServerTask.initInstanceConfig(delInstanceList, "delete");
//						MemoryDataManagerTask.loadAcqInstanceOwnItemById(modbusProtocolInstanceSaveData.getId()+"", "update");
//						EquipmentDriverServerTask.initInstanceConfig(instanceList, "update");
//						EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByProtocolInstanceId(modbusProtocolInstanceSaveData.getId()+"", "update");
					}
					
					json = "{success:true,msg:true}";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					json = "{success:false,msg:false}";
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
	
	@RequestMapping("/doModbusProtocolDisplayInstanceAdd")
	public String doModbusProtocolDisplayInstanceAdd(@ModelAttribute ProtocolDisplayInstance protocolDisplayInstance) throws IOException {
		String result = "";
		try {
			if(protocolDisplayInstance.getId()==null){
				protocolDisplayInstance.setId(1);
			}
			this.protocolDisplayInstanceManagerService.doModbusProtocolDisplayInstanceAdd(protocolDisplayInstance);
			DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
			dataSynchronizationThread.setSign(061);
			dataSynchronizationThread.setParam1(protocolDisplayInstance.getName());
			dataSynchronizationThread.setParam2(protocolDisplayInstance.getDisplayUnitId()+"");
			dataSynchronizationThread.setMethod("update");
			ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
			executor.execute(dataSynchronizationThread);
//			MemoryDataManagerTask.loadDisplayInstanceOwnItemByCode(protocolDisplayInstance.getCode(),"update");
			
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("addDisplayInstance")+":"+protocolDisplayInstance.getName());
			}
			
			result = "{success:true,msg:true}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
		}
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(result);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/doModbusProtocolReportUnitAdd")
	public String doModbusProtocolReportUnitAdd(@ModelAttribute ReportUnit reportUnit) throws IOException {
		String result = "";
		try {
			this.reportUnitManagerService.doModbusProtocolReportUnitAdd(reportUnit);
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("addReportUnit")+":"+reportUnit.getUnitName());
			}
			result = "{success:true,msg:true}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
		}
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(result);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/doModbusProtocolReportInstanceAdd")
	public String doModbusProtocolReportInstanceAdd(@ModelAttribute ProtocolReportInstance protocolReportInstance) throws IOException {
		String result = "";
		try {
			if(protocolReportInstance.getId()!=null){
				protocolReportInstance.setId(1);
			}
			this.protocolReportInstanceManagerService.doModbusProtocolReportInstanceAdd(protocolReportInstance);
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("addReportInstance")+":"+protocolReportInstance.getName());
			}
			result = "{success:true,msg:true}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
		}
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(result);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveProtocolDisplayInstanceData")
	public String saveProtocolDisplayInstanceData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Gson gson=new Gson();
		String json ="{success:true}";
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<ModbusProtocolDisplayInstanceSaveData>() {}.getType();
		ModbusProtocolDisplayInstanceSaveData modbusProtocolDisplayInstanceSaveData=gson.fromJson(data, type);
		
		if(modbusProtocolDisplayInstanceSaveData!=null){
			ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
			if(modbusProtocolDisplayInstanceSaveData.getDelidslist()!=null){
				for(int i=0;i<modbusProtocolDisplayInstanceSaveData.getDelidslist().size();i++){
					
					DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
					dataSynchronizationThread.setSign(062);
					dataSynchronizationThread.setParam1(modbusProtocolDisplayInstanceSaveData.getDelidslist().get(i));
					dataSynchronizationThread.setMethod("delete");
					dataSynchronizationThread.setAcquisitionUnitManagerService(acquisitionUnitManagerService);
					executor.execute(dataSynchronizationThread);
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("deleteDisplayInstance"));
					}
				}
			}
			
			if(StringManagerUtils.isNotNull(modbusProtocolDisplayInstanceSaveData.getName())){
				String unitText=modbusProtocolDisplayInstanceSaveData.getDisplayUnitName();
				String protocol="";
				String unitName="";
				if(unitText.contains("/")){
					String[] textArr=unitText.split("/");
					if(textArr.length==2){
						protocol=textArr[0];
						unitName=textArr[1];
					}
				}
				
				String sql="select t.id "
						+ " from tbl_display_unit_conf t,tbl_acq_unit_conf t2,tbl_protocol t3 "
						+ " where t.acqunitid=t2.id and t2.protocol=t3.code "
						+ " and t.unit_name='"+unitName+"' "
						+ " and t3.code='"+protocolCode+"'";
				
				String unitId="";
				List list = this.service.findCallSql(sql);
				if(list.size()>0){
					unitId=list.get(0).toString();
				}
				
				ProtocolDisplayInstance protocolDisplayInstance=new ProtocolDisplayInstance();
				protocolDisplayInstance.setId(modbusProtocolDisplayInstanceSaveData.getId());
				protocolDisplayInstance.setCode(modbusProtocolDisplayInstanceSaveData.getCode());
				protocolDisplayInstance.setName(modbusProtocolDisplayInstanceSaveData.getName());
				protocolDisplayInstance.setDisplayUnitId(StringManagerUtils.stringToInteger(unitId));
				if(StringManagerUtils.isNum(modbusProtocolDisplayInstanceSaveData.getSort())){
					protocolDisplayInstance.setSort(StringManagerUtils.stringToInteger(modbusProtocolDisplayInstanceSaveData.getSort()));
				}else{
					protocolDisplayInstance.setSort(null);
				}
				try {
					this.protocolDisplayInstanceManagerService.doModbusProtocolDisplayInstanceEdit(protocolDisplayInstance);
					
					DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
					dataSynchronizationThread.setSign(063);
					dataSynchronizationThread.setParam1(protocolDisplayInstance.getId()+"");
					dataSynchronizationThread.setMethod("update");
					executor.execute(dataSynchronizationThread);
					
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("editDisplayInstance")+":"+protocolDisplayInstance.getName());
					}
					
//					MemoryDataManagerTask.loadDisplayInstanceOwnItemById(protocolDisplayInstance.getId()+"","update");
					json = "{success:true,msg:true}";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					json = "{success:false,msg:false}";
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
	
	@RequestMapping("/saveProtocolReportUnitData")
	public String saveProtocolReportUnitData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Gson gson=new Gson();
		String json ="{success:true}";
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<ModbusProtocolReportUnitSaveData>() {}.getType();
		ModbusProtocolReportUnitSaveData modbusProtocolReportUnitSaveData=gson.fromJson(data, type);
		
		if(modbusProtocolReportUnitSaveData!=null){
			if(modbusProtocolReportUnitSaveData.getDelidslist()!=null){
				for(int i=0;i<modbusProtocolReportUnitSaveData.getDelidslist().size();i++){
					this.reportUnitManagerService.doModbusProtocolReportUnitBulkDelete(modbusProtocolReportUnitSaveData.getDelidslist().get(i));
					
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("deleteReportUnit"));
					}
				}
			}
			
			if(StringManagerUtils.isNotNull(modbusProtocolReportUnitSaveData.getUnitName())){
				ReportUnit reportUnit=new ReportUnit();
				reportUnit.setId(modbusProtocolReportUnitSaveData.getId());
				reportUnit.setUnitCode(modbusProtocolReportUnitSaveData.getUnitCode());
				reportUnit.setUnitName(modbusProtocolReportUnitSaveData.getUnitName());
				reportUnit.setSingleWellRangeReportTemplate(modbusProtocolReportUnitSaveData.getSingleWellRangeReportTemplate());
				reportUnit.setSingleWellDailyReportTemplate(modbusProtocolReportUnitSaveData.getSingleWellDailyReportTemplate());
				reportUnit.setProductionReportTemplate(modbusProtocolReportUnitSaveData.getProductionReportTemplate());
				reportUnit.setCalculateType(StringManagerUtils.stringToInteger(modbusProtocolReportUnitSaveData.getCalculateType()));
				if(StringManagerUtils.isNum(modbusProtocolReportUnitSaveData.getSort()) || StringManagerUtils.isNumber(modbusProtocolReportUnitSaveData.getSort())){
					reportUnit.setSort(StringManagerUtils.stringToInteger(modbusProtocolReportUnitSaveData.getSort()));
				}else{
					reportUnit.setSort(null);
				}
				try {
					this.reportUnitManagerService.doModbusProtocolReportUnitEdit(reportUnit);
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("editReportUnit")+":"+reportUnit.getUnitName());
					}
					json = "{success:true,msg:true}";
				} catch (Exception e) {
					e.printStackTrace();
					json = "{success:false,msg:false}";
				}
			}
		}
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveProtocolReportInstanceData")
	public String saveProtocolReportInstanceData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Gson gson=new Gson();
		String json ="{success:true}";
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<ModbusProtocolReportInstanceSaveData>() {}.getType();
		ModbusProtocolReportInstanceSaveData modbusProtocolReportInstanceSaveData=gson.fromJson(data, type);
		
		if(modbusProtocolReportInstanceSaveData!=null){
			if(modbusProtocolReportInstanceSaveData.getDelidslist()!=null){
				for(int i=0;i<modbusProtocolReportInstanceSaveData.getDelidslist().size();i++){
					this.protocolReportInstanceManagerService.doModbusProtocolReportInstanceBulkDelete(modbusProtocolReportInstanceSaveData.getDelidslist().get(i));
					
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("deleteReportInstance"));
					}
				}
			}
			
			if(StringManagerUtils.isNotNull(modbusProtocolReportInstanceSaveData.getName())){
				String sql="select t.id from tbl_report_unit_conf t where t.unit_name='"+modbusProtocolReportInstanceSaveData.getUnitName()+"' "
						+ " and rownum=1";
				String unitId="";
				List list = this.service.findCallSql(sql);
				if(list.size()>0){
					unitId=list.get(0).toString();
				}
				ProtocolReportInstance protocolReportInstance=new ProtocolReportInstance();
				protocolReportInstance.setId(modbusProtocolReportInstanceSaveData.getId());
				protocolReportInstance.setCode(modbusProtocolReportInstanceSaveData.getCode());
				protocolReportInstance.setName(modbusProtocolReportInstanceSaveData.getName());
				protocolReportInstance.setUnitId(StringManagerUtils.stringToInteger(unitId));
				
				
				if(StringManagerUtils.isNum(modbusProtocolReportInstanceSaveData.getSort())){
					protocolReportInstance.setSort(StringManagerUtils.stringToInteger(modbusProtocolReportInstanceSaveData.getSort()));
				}else{
					protocolReportInstance.setSort(null);
				}
				try {
					this.protocolReportInstanceManagerService.doModbusProtocolReportInstanceEdit(protocolReportInstance);
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("editReportInstance")+":"+protocolReportInstance.getName());
					}
					json = "{success:true,msg:true}";
				} catch (Exception e) {
					e.printStackTrace();
					json = "{success:false,msg:false}";
				}
			}
		}
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/doModbusProtocolAlarmInstanceAdd")
	public String doModbusProtocolAlarmInstanceAdd(@ModelAttribute ProtocolAlarmInstance protocolAlarmInstance) throws IOException {
		String result = "";
		try {
			if(protocolAlarmInstance.getId()==null){
				protocolAlarmInstance.setId(1);
			}
			this.protocolAlarmInstanceManagerService.doModbusProtocolAlarmInstanceAdd(protocolAlarmInstance);
			DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
			dataSynchronizationThread.setSign(071);
			dataSynchronizationThread.setParam1(protocolAlarmInstance.getName());
			dataSynchronizationThread.setParam2(protocolAlarmInstance.getAlarmUnitId()+"");
			dataSynchronizationThread.setMethod("update");
			ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
			executor.execute(dataSynchronizationThread);
//			MemoryDataManagerTask.loadAlarmInstanceOwnItemByCode(protocolAlarmInstance.getCode(),"update");
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("addAlarmInstance")+":"+protocolAlarmInstance.getName());
			}
			result = "{success:true,msg:true}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
		}
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(result);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveProtocolAlarmInstanceData")
	public String saveProtocolAlarmInstanceData() throws Exception {
		HttpSession session=request.getSession();
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Gson gson=new Gson();
		String json ="{success:true}";
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<ModbusProtocolAlarmInstanceSaveData>() {}.getType();
		ModbusProtocolAlarmInstanceSaveData modbusProtocolAlarmInstanceSaveData=gson.fromJson(data, type);
		
		if(modbusProtocolAlarmInstanceSaveData!=null){
			ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
			if(modbusProtocolAlarmInstanceSaveData.getDelidslist()!=null){
				for(int i=0;i<modbusProtocolAlarmInstanceSaveData.getDelidslist().size();i++){
					DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
					dataSynchronizationThread.setSign(072);
					dataSynchronizationThread.setParam1(modbusProtocolAlarmInstanceSaveData.getDelidslist().get(i));
					dataSynchronizationThread.setMethod("delete");
					dataSynchronizationThread.setAcquisitionUnitManagerService(acquisitionUnitManagerService);
					executor.execute(dataSynchronizationThread);
//					this.protocolAlarmInstanceManagerService.doModbusProtocolAlarmInstanceBulkDelete(modbusProtocolAlarmInstanceSaveData.getDelidslist().get(i));
					
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("deleteAlarmInstance"));
					}
				}
			}
			
			if(StringManagerUtils.isNotNull(modbusProtocolAlarmInstanceSaveData.getName())){
				String unitText=modbusProtocolAlarmInstanceSaveData.getAlarmUnitName();
				String protocol="";
				String unitName="";
				if(unitText.contains("/")){
					String[] textArr=unitText.split("/");
					if(textArr.length==2){
						protocol=textArr[0];
						unitName=textArr[1];
					}
				}
				
				String sql="select t.id "
						+ " from tbl_alarm_unit_conf t,tbl_protocol t2 "
						+ " where t.protocol=t2.code "
						+ " and t.unit_name='"+unitName+"' "
						+ " and t2.code='"+protocolCode+"'";
				String unitId="";
				List list = this.service.findCallSql(sql);
				if(list.size()>0){
					unitId=list.get(0).toString();
				}
				
				
				ProtocolAlarmInstance protocolAlarmInstance=new ProtocolAlarmInstance();
				protocolAlarmInstance.setId(modbusProtocolAlarmInstanceSaveData.getId());
				protocolAlarmInstance.setCode(modbusProtocolAlarmInstanceSaveData.getCode());
				protocolAlarmInstance.setName(modbusProtocolAlarmInstanceSaveData.getName());
				protocolAlarmInstance.setAlarmUnitId(StringManagerUtils.stringToInteger(unitId));
				if(StringManagerUtils.isNum(modbusProtocolAlarmInstanceSaveData.getSort())){
					protocolAlarmInstance.setSort(StringManagerUtils.stringToInteger(modbusProtocolAlarmInstanceSaveData.getSort()));
				}else{
					protocolAlarmInstance.setSort(null);
				}
				try {
					this.protocolAlarmInstanceManagerService.doModbusProtocolAlarmInstanceEdit(protocolAlarmInstance);
					DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
					dataSynchronizationThread.setSign(073);
					dataSynchronizationThread.setParam1(protocolAlarmInstance.getId()+"");
					dataSynchronizationThread.setMethod("update");
					executor.execute(dataSynchronizationThread);
//					MemoryDataManagerTask.loadAlarmInstanceOwnItemById(protocolAlarmInstance.getId()+"","update");
					if(user!=null){
						this.service.saveSystemLog(user,2,languageResourceMap.get("editAlarmInstance")+":"+protocolAlarmInstance.getName());
					}
					json = "{success:true,msg:true}";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					json = "{success:false,msg:false}";
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
	
	@RequestMapping("/doModbusProtocolSMSInstanceAdd")
	public String doModbusProtocolSMSInstanceAdd(@ModelAttribute ProtocolSMSInstance protocolSMSInstance) throws IOException {
		String result = "";
		try {
			this.protocolSMSInstanceManagerService.doModbusProtocolSMSInstanceAdd(protocolSMSInstance);
			List<String> instanceList=new ArrayList<String>();
			instanceList.add(protocolSMSInstance.getName());
			EquipmentDriverServerTask.initSMSInstanceConfig(instanceList, "update");
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("addSMSInstance")+":"+protocolSMSInstance.getName());
			}
			result = "{success:true,msg:true}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
		}
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(result);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/doModbusProtocolSMSInstanceEdit")
	public String doModbusProtocolSMSInstanceEdit(@ModelAttribute ProtocolSMSInstance protocolSMSInstance) {
		String result ="{success:true,msg:false}";
		try {
			this.protocolSMSInstanceManagerService.doModbusProtocolSMSInstanceEdit(protocolSMSInstance);
			List<String> instanceList=new ArrayList<String>();
			instanceList.add(protocolSMSInstance.getName());
			EquipmentDriverServerTask.initSMSInstanceConfig(instanceList, "update");
			EquipmentDriverServerTask.initSMSDeviceByInstanceId(protocolSMSInstance.getId()+"", "update");
			
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("editSMSInstance")+":"+protocolSMSInstance.getName());
			}
			
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			result= "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.getWriter().print(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/doModbusProtocolSMSInstanceDelete")
	public String doModbusProtocolSMSInstanceDelete() {
		try {
			String ids = ParamUtils.getParameter(request, "paramsId");
			this.acquisitionUnitManagerService.doModbusProtocolSMSInstanceDelete(ids);
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("deleteSMSInstance"));
			}
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			String result = "{success:true,flag:true}";
			response.getWriter().print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getDatabaseColumnMappingTable")
	public String getDatabaseColumnMappingTable() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String classes = ParamUtils.getParameter(request, "classes");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.acquisitionUnitManagerService.getDatabaseColumnMappingTable(classes,deviceType,protocolCode,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolRunStatusItems")
	public String getProtocolRunStatusItems() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String classes = ParamUtils.getParameter(request, "classes");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.acquisitionUnitManagerService.getProtocolRunStatusItems(classes,deviceType,protocolCode,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolRunStatusItemsMeaning")
	public String getProtocolRunStatusItemsMeaning() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String status = ParamUtils.getParameter(request, "status");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String itemName = ParamUtils.getParameter(request, "itemName");
		String itemColumn = ParamUtils.getParameter(request, "itemColumn");
		String resolutionMode = ParamUtils.getParameter(request, "resolutionMode");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.acquisitionUnitManagerService.getProtocolRunStatusItemsMeaning(status,deviceType,protocolCode,itemName,itemColumn,resolutionMode,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveDatabaseColumnMappingTable")
	public String saveDatabaseColumnMappingTable() throws Exception {
		String data = ParamUtils.getParameter(request, "data");
		String protocolType = ParamUtils.getParameter(request, "protocolType");
		protocolType="0";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<DatabaseMappingProHandsontableChangedData>() {}.getType();
		DatabaseMappingProHandsontableChangedData databaseMappingProHandsontableChangedData=gson.fromJson(data, type);
		if(databaseMappingProHandsontableChangedData!=null){
			this.acquisitionUnitManagerService.saveDatabaseColumnMappingTable(databaseMappingProHandsontableChangedData,protocolType,language);
			EquipmentDriverServerTask.syncDataMappingTable();
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("updateColumnMapping"));
			}
		}
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveProtocolRunStatusConfig")
	public String saveProtocolRunStatusConfig() throws Exception {
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String itemName = ParamUtils.getParameter(request, "itemName");
		String itemColumn = ParamUtils.getParameter(request, "itemColumn");
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String runValue = ParamUtils.getParameter(request, "runValue");
		String stopValue = ParamUtils.getParameter(request, "stopValue");
		String runCondition = ParamUtils.getParameter(request, "runCondition");
		String stopCondition = ParamUtils.getParameter(request, "stopCondition");
		String resolutionMode = ParamUtils.getParameter(request, "resolutionMode");
		this.acquisitionUnitManagerService.saveProtocolRunStatusConfig(protocolCode,resolutionMode,itemName,itemColumn,runValue,stopValue,runCondition,stopCondition);
		MemoryDataManagerTask.loadProtocolRunStatusConfig();
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		if(user!=null){
			this.service.saveSystemLog(user,2,languageResourceMap.get("updateRunStatusConfig"));
		}
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/judgeProtocolExistOrNot")
	public String judgeProtocolExistOrNot() throws IOException {
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		boolean flag = this.acquisitionUnitManagerService.judgeProtocolExistOrNot(protocolName,deviceType);
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
	
	@RequestMapping("/judgeAcqUnitExistOrNot")
	public String judgeAcqUnitExistOrNot() throws IOException {
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String unitName = ParamUtils.getParameter(request, "unitName");
		boolean flag = this.acquisitionUnitManagerService.judgeAcqUnitExistOrNot(protocolName,unitName);
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
	
	@RequestMapping("/judgeDisplayUnitExistOrNot")
	public String judgeDisplayUnitExistOrNot() throws IOException {
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String unitName = ParamUtils.getParameter(request, "unitName");
		boolean flag = this.acquisitionUnitManagerService.judgeDisplayUnitExistOrNot(protocolName,unitName);
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
	
	@RequestMapping("/judgeAcqGroupExistOrNot")
	public String judgeAcqGroupExistOrNot() throws IOException {
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String unitName = ParamUtils.getParameter(request, "unitName");
		String groupName = ParamUtils.getParameter(request, "groupName");
		boolean flag = this.acquisitionUnitManagerService.judgeAcqGroupExistOrNot(protocolName,unitName,groupName);
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
	
	@RequestMapping("/judgeAlarmUnitExistOrNot")
	public String judgeAlarmUnitExistOrNot() throws IOException {
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String unitName = ParamUtils.getParameter(request, "unitName");
		boolean flag = this.acquisitionUnitManagerService.judgeAlarmUnitExistOrNot(protocolName,unitName);
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
	
	@RequestMapping("/judgeInstanceExistOrNot")
	public String judgeInstanceExistOrNot() throws IOException {
		String instanceName = ParamUtils.getParameter(request, "instanceName");
		String acqUnitId=ParamUtils.getParameter(request, "acqUnitId");
		boolean flag = this.acquisitionUnitManagerService.judgeInstanceExistOrNot(instanceName,acqUnitId);
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
	
	@RequestMapping("/judgeAlarmInstanceExistOrNot")
	public String judgeAlarmInstanceExistOrNot() throws IOException {
		String instanceName = ParamUtils.getParameter(request, "instanceName");
		boolean flag = this.acquisitionUnitManagerService.judgeAlarmInstanceExistOrNot(instanceName);
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
	
	@RequestMapping("/judgeDisplayInstanceExistOrNot")
	public String judgeDisplayInstanceExistOrNot() throws IOException {
		String instanceName = ParamUtils.getParameter(request, "instanceName");
		boolean flag = this.acquisitionUnitManagerService.judgeDisplayInstanceExistOrNot(instanceName);
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
	
	@RequestMapping("/judgeReportUnitExistOrNot")
	public String judgeReportUnitExistOrNot() throws IOException {
		String unitName = ParamUtils.getParameter(request, "unitName");
		boolean flag = this.acquisitionUnitManagerService.judgeReportUnitExistOrNot(unitName);
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
	
	@RequestMapping("/judgeReportInstanceExistOrNot")
	public String judgeReportInstanceExistOrNot() throws IOException {
		String instanceName = ParamUtils.getParameter(request, "instanceName");
		boolean flag = this.acquisitionUnitManagerService.judgeReportInstanceExistOrNot(instanceName);
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
	
	/**
	 * <p>
	 * 描述：导出协议配置数据
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportProtocolConfigData")
	public String exportProtocolConfigData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd_HHmmss");//设置日期格式
		
		String protocolName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "protocolName"),"utf-8");
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		String protocolCode=ParamUtils.getParameter(request, "protocolCode");
		
		String acqUnit=ParamUtils.getParameter(request, "acqUnit");
		String acqGroup=ParamUtils.getParameter(request, "acqGroup");
		
		String displayUnit=ParamUtils.getParameter(request, "displayUnit");
		String alarmUnit=ParamUtils.getParameter(request, "alarmUnit");
		String acqInstance=ParamUtils.getParameter(request, "acqInstance");
		String displayInstance=ParamUtils.getParameter(request, "displayInstance");
		String alarmInstance=ParamUtils.getParameter(request, "alarmInstance");
		
		String json=acquisitionUnitManagerService.exportProtocolConfigData(protocolName,protocolCode,acqUnit,acqGroup,displayUnit,alarmUnit,acqInstance,displayInstance,alarmInstance);
		
		String fileName=protocolName;
		if(!fileName.endsWith("协议")){
			fileName+="协议";
		}
		
		fileName+="导出数据"+".json";
		
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		try {
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			String language="";
			if(user!=null){
				language=user.getLanguageName();
			}
			Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("exportProtocol")+":"+protocolName);
			}
			
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
            InputStream in = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer,0,len);
            }
            in.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		StringManagerUtils.deleteFile(path);
		return null;
	}
	
	/**
	 * <p>
	 * 描述：导出协议配置数据
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportProtocolData")
	public String exportProtocolData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String protocolListStr=ParamUtils.getParameter(request, "protocolList");
		String key = ParamUtils.getParameter(request, "key");
		String json=acquisitionUnitManagerService.getProtocolExportData(protocolListStr);
		String fileName="";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		fileName+=languageResourceMap.get("exportProtocol")+".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		
		File file=StringManagerUtils.createJsonFile(json, path);
		
		InputStream in=null;
		OutputStream out=null;
		try {
			
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("exportProtocol"));
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
	
	@RequestMapping("/exportAllProtocolData")
	public String exportAllProtocolData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String key = ParamUtils.getParameter(request, "key");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		fileName+=".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		
		String json=acquisitionUnitManagerService.exportAllProtocolData(user);
		
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
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
	
	@RequestMapping("/exportProtocolInitData")
	public String exportProtocolInitData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String protocolListStr=ParamUtils.getParameter(request, "protocolList");
		String key = ParamUtils.getParameter(request, "key");
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		String json=acquisitionUnitManagerService.exportProtocolInitData(protocolListStr);
		String fileName=languageResourceMap.get("exportProtocolInitData")+".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("exportProtocolInitData"));
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
	
	@RequestMapping("/getImportedProtocolFile")
	public String getImportedProtocolFile(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer result_json = new StringBuffer();
		boolean flag=false;
		String protocolName="";
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		if(exportProtocolConfig!=null){
			map.remove("importedProtocolFileMap");
			exportProtocolConfig=null;
		}
		String json = "";
		String fileContent="";
		if(files.length>0 && (!files[0].isEmpty())){
			try{
				byte[] buffer = files[0].getBytes();
				fileContent = new String(buffer, "UTF-8");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		type = new TypeToken<ExportProtocolConfig>() {}.getType();
		exportProtocolConfig=gson.fromJson(fileContent, type);
		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null && StringManagerUtils.isNotNull(exportProtocolConfig.getProtocol().getName())){
			flag=true;
			protocolName=exportProtocolConfig.getProtocol().getName();
			map.put("importedProtocolFileMap", exportProtocolConfig);
		}
		result_json.append("{ \"success\":true,\"flag\":"+flag+",\"protocolName\":\""+protocolName+"\"}");
		
		json=result_json.toString();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportedProtocolContentTreeData")
	public String getImportedProtocolContentTreeData() throws IOException {
		StringBuffer result_json = new StringBuffer();
		StringBuffer acqUnit_json = new StringBuffer();
		StringBuffer displayUnit_json = new StringBuffer();
		StringBuffer alarmUnit_json = new StringBuffer();
		StringBuffer acqInstance_json = new StringBuffer();
		StringBuffer displayInstance_json = new StringBuffer();
		StringBuffer alarmInstance_json = new StringBuffer();
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		result_json.append("[");
		acqUnit_json.append("[");
		displayUnit_json.append("[");
		alarmUnit_json.append("[");
		acqInstance_json.append("[");
		displayInstance_json.append("[");
		alarmInstance_json.append("[");
		Map<String, Object> map = DataModelMap.getMapObject();
		ExportProtocolConfig exportProtocolConfig=(ExportProtocolConfig) map.get("importedProtocolFileMap");
		if(exportProtocolConfig!=null && exportProtocolConfig.getProtocol()!=null && StringManagerUtils.isNotNull(exportProtocolConfig.getProtocol().getName())){
			if(exportProtocolConfig.getAcqUnitList()!=null && exportProtocolConfig.getAcqUnitList().size()>0){
				for(int i=0;i<exportProtocolConfig.getAcqUnitList().size();i++){
					acqUnit_json.append("{\"classes\":1,");
					acqUnit_json.append("\"id\":"+exportProtocolConfig.getAcqUnitList().get(i).getId()+",");
					acqUnit_json.append("\"code\":\""+exportProtocolConfig.getAcqUnitList().get(i).getUnitCode()+"\",");
					acqUnit_json.append("\"text\":\""+exportProtocolConfig.getAcqUnitList().get(i).getUnitName()+"\",");
					acqUnit_json.append("\"remark\":\""+exportProtocolConfig.getAcqUnitList().get(i).getRemark()+"\",");
					acqUnit_json.append("\"protocol\":\""+exportProtocolConfig.getAcqUnitList().get(i).getProtocol()+"\",");
					acqUnit_json.append("\"iconCls\": \"acqUnit\",");
					acqUnit_json.append("\"checked\": false,");
					acqUnit_json.append("\"expanded\": true,");
					acqUnit_json.append("\"children\": [");
					
					if(exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList()!=null && exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().size()>0){
						for(int j=0;j<exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().size();j++){
							acqUnit_json.append("{\"classes\":2,");
							acqUnit_json.append("\"id\":"+exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getId()+",");
							acqUnit_json.append("\"code\":\""+exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getGroupCode()+"\",");
							acqUnit_json.append("\"text\":\""+exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getGroupName()+"\",");
							acqUnit_json.append("\"groupTimingInterval\":\""+exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getGroupTimingInterval()+"\",");
							acqUnit_json.append("\"groupSavingInterval\":\""+exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getGroupSavingInterval()+"\",");
							acqUnit_json.append("\"remark\":\""+exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getRemark()+"\",");
							acqUnit_json.append("\"protocol\":\""+exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getProtocol()+"\",");
							acqUnit_json.append("\"type\":"+exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getType()+",");
							acqUnit_json.append("\"typeName\":\""+(exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getType()==0?languageResourceMap.get("acqGroup"):languageResourceMap.get("controlGroup"))+"\",");
							acqUnit_json.append("\"iconCls\": \"acqGroup\",");
							acqUnit_json.append("\"checked\": false,");
							acqUnit_json.append("\"leaf\": true");
							acqUnit_json.append("},");
						}
					}
					if(acqUnit_json.toString().endsWith(",")){
						acqUnit_json.deleteCharAt(acqUnit_json.length() - 1);
					}
					acqUnit_json.append("]},");
				}
			}
			
			if(exportProtocolConfig.getDisplayUnitList()!=null && exportProtocolConfig.getDisplayUnitList().size()>0){
				for(int i=0;i<exportProtocolConfig.getDisplayUnitList().size();i++){
					displayUnit_json.append("{\"classes\":1,");
					displayUnit_json.append("\"id\":"+exportProtocolConfig.getDisplayUnitList().get(i).getId()+",");
					displayUnit_json.append("\"code\":\""+exportProtocolConfig.getDisplayUnitList().get(i).getUnitCode()+"\",");
					displayUnit_json.append("\"text\":\""+exportProtocolConfig.getDisplayUnitList().get(i).getUnitName()+"\",");
					displayUnit_json.append("\"remark\":\""+exportProtocolConfig.getDisplayUnitList().get(i).getRemark()+"\",");
					displayUnit_json.append("\"protocol\":\""+exportProtocolConfig.getDisplayUnitList().get(i).getProtocol()+"\",");
					displayUnit_json.append("\"acqUnitId\":\""+exportProtocolConfig.getDisplayUnitList().get(i).getAcqUnitId()+"\",");
					displayUnit_json.append("\"iconCls\": \"acqUnit\",");
					displayUnit_json.append("\"checked\": false,");
					displayUnit_json.append("\"leaf\": true");
					displayUnit_json.append("},");
				}
			}
			
			if(exportProtocolConfig.getAlarmUnitList()!=null && exportProtocolConfig.getAlarmUnitList().size()>0){
				for(int i=0;i<exportProtocolConfig.getAlarmUnitList().size();i++){
					alarmUnit_json.append("{\"classes\":1,");
					alarmUnit_json.append("\"id\":"+exportProtocolConfig.getAlarmUnitList().get(i).getId()+",");
					alarmUnit_json.append("\"code\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getUnitCode()+"\",");
					alarmUnit_json.append("\"text\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getUnitName()+"\",");
					alarmUnit_json.append("\"remark\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getRemark()+"\",");
					alarmUnit_json.append("\"protocol\":\""+exportProtocolConfig.getAlarmUnitList().get(i).getProtocol()+"\",");
					alarmUnit_json.append("\"iconCls\": \"acqUnit\",");
					alarmUnit_json.append("\"checked\": false,");
					alarmUnit_json.append("\"leaf\": true");
					alarmUnit_json.append("},");
				}
			}
			
			if(exportProtocolConfig.getAcqInstanceList()!=null && exportProtocolConfig.getAcqInstanceList().size()>0){
				for(int i=0;i<exportProtocolConfig.getAcqInstanceList().size();i++){
					acqInstance_json.append("{\"classes\":1,");
					acqInstance_json.append("\"id\":"+exportProtocolConfig.getAcqInstanceList().get(i).getId()+",");
					acqInstance_json.append("\"text\":\""+exportProtocolConfig.getAcqInstanceList().get(i).getName()+"\",");
					acqInstance_json.append("\"code\":\""+exportProtocolConfig.getAcqInstanceList().get(i).getCode()+"\",");
					acqInstance_json.append("\"acqProtocolType\":\""+exportProtocolConfig.getAcqInstanceList().get(i).getAcqProtocolType()+"\",");
					acqInstance_json.append("\"ctrlProtocolType\":\""+exportProtocolConfig.getAcqInstanceList().get(i).getCtrlProtocolType()+"\",");
					
					acqInstance_json.append("\"signInPrefixSuffixHex\":\""+exportProtocolConfig.getAcqInstanceList().get(i).getSignInPrefixSuffixHex()+"\",");
					acqInstance_json.append("\"signInPrefix\":\""+exportProtocolConfig.getAcqInstanceList().get(i).getSignInPrefix()+"\",");
					acqInstance_json.append("\"signInSuffix\":\""+exportProtocolConfig.getAcqInstanceList().get(i).getSignInSuffix()+"\",");
					acqInstance_json.append("\"signInIDHex\":\""+exportProtocolConfig.getAcqInstanceList().get(i).getSignInIDHex()+"\",");
					
					acqInstance_json.append("\"heartbeatPrefixSuffixHex\":\""+exportProtocolConfig.getAcqInstanceList().get(i).getHeartbeatPrefixSuffixHex()+"\",");
					acqInstance_json.append("\"heartbeatPrefix\":\""+exportProtocolConfig.getAcqInstanceList().get(i).getHeartbeatPrefix()+"\",");
					acqInstance_json.append("\"heartbeatSuffix\":\""+exportProtocolConfig.getAcqInstanceList().get(i).getHeartbeatSuffix()+"\",");
					
					acqInstance_json.append("\"packetSendInterval\":\""+exportProtocolConfig.getAcqInstanceList().get(i).getPacketSendInterval()+"\",");
					acqInstance_json.append("\"sort\":\""+exportProtocolConfig.getAcqInstanceList().get(i).getSort()+"\",");
					acqInstance_json.append("\"iconCls\": \"acqUnit\",");
					acqInstance_json.append("\"checked\": false,");
					acqInstance_json.append("\"leaf\": true},");
				}
			}
			
			if(exportProtocolConfig.getDisplayInstanceList()!=null && exportProtocolConfig.getDisplayInstanceList().size()>0){
				for(int i=0;i<exportProtocolConfig.getDisplayInstanceList().size();i++){
					displayInstance_json.append("{\"classes\":1,");
					displayInstance_json.append("\"id\":"+exportProtocolConfig.getDisplayInstanceList().get(i).getId()+",");
					displayInstance_json.append("\"text\":\""+exportProtocolConfig.getDisplayInstanceList().get(i).getName()+"\",");
					displayInstance_json.append("\"code\":\""+exportProtocolConfig.getDisplayInstanceList().get(i).getCode()+"\",");
					displayInstance_json.append("\"displayUnitId\":"+exportProtocolConfig.getDisplayInstanceList().get(i).getDisplayUnitId()+",");
					displayInstance_json.append("\"sort\":\""+exportProtocolConfig.getDisplayInstanceList().get(i).getSort()+"\",");
					displayInstance_json.append("\"iconCls\": \"acqUnit\",");
					displayInstance_json.append("\"checked\": false,");
					displayInstance_json.append("\"leaf\": true},");
				}
			}
			
			if(exportProtocolConfig.getAlarmInstanceList()!=null && exportProtocolConfig.getAlarmInstanceList().size()>0){
				for(int i=0;i<exportProtocolConfig.getAlarmInstanceList().size();i++){
					alarmInstance_json.append("{\"classes\":1,");
					alarmInstance_json.append("\"id\":"+exportProtocolConfig.getAlarmInstanceList().get(i).getId()+",");
					alarmInstance_json.append("\"text\":\""+exportProtocolConfig.getAlarmInstanceList().get(i).getName()+"\",");
					alarmInstance_json.append("\"code\":\""+exportProtocolConfig.getAlarmInstanceList().get(i).getCode()+"\",");
					alarmInstance_json.append("\"alarmUnitId\":"+exportProtocolConfig.getAlarmInstanceList().get(i).getAlarmUnitId()+",");
					alarmInstance_json.append("\"sort\":\""+exportProtocolConfig.getAlarmInstanceList().get(i).getSort()+"\",");
					alarmInstance_json.append("\"iconCls\": \"acqUnit\",");
					alarmInstance_json.append("\"checked\": false,");
					alarmInstance_json.append("\"leaf\": true},");
				}
			}
		}
		if(acqUnit_json.toString().endsWith(",")){
			acqUnit_json.deleteCharAt(acqUnit_json.length() - 1);
		}
		acqUnit_json.append("]");
		
		if(displayUnit_json.toString().endsWith(",")){
			displayUnit_json.deleteCharAt(displayUnit_json.length() - 1);
		}
		displayUnit_json.append("]");
		
		if(alarmUnit_json.toString().endsWith(",")){
			alarmUnit_json.deleteCharAt(alarmUnit_json.length() - 1);
		}
		alarmUnit_json.append("]");
		
		if(acqInstance_json.toString().endsWith(",")){
			acqInstance_json.deleteCharAt(acqInstance_json.length() - 1);
		}
		acqInstance_json.append("]");
		
		if(displayInstance_json.toString().endsWith(",")){
			displayInstance_json.deleteCharAt(displayInstance_json.length() - 1);
		}
		displayInstance_json.append("]");
		
		if(alarmInstance_json.toString().endsWith(",")){
			alarmInstance_json.deleteCharAt(alarmInstance_json.length() - 1);
		}
		alarmInstance_json.append("]");
		
		result_json.append("{\"classes\":0,\"type\":0,\"text\":\"采控单元\",\"iconCls\": \"protocol\",\"checked\": false,\"expanded\": true,\"children\": "+acqUnit_json+"},");
		result_json.append("{\"classes\":0,\"type\":1,\"text\":\"显示单元\",\"iconCls\": \"protocol\",\"checked\": false,\"expanded\": true,\"children\": "+displayUnit_json+"},");
		result_json.append("{\"classes\":0,\"type\":2,\"text\":\"报警单元\",\"iconCls\": \"protocol\",\"checked\": false,\"expanded\": true,\"children\": "+alarmUnit_json+"},");
		result_json.append("{\"classes\":0,\"type\":3,\"text\":\"采控实例\",\"iconCls\": \"protocol\",\"checked\": false,\"expanded\": true,\"children\": "+acqInstance_json+"},");
		result_json.append("{\"classes\":0,\"type\":4,\"text\":\"显示实例\",\"iconCls\": \"protocol\",\"checked\": false,\"expanded\": true,\"children\": "+displayInstance_json+"},");
		result_json.append("{\"classes\":0,\"type\":5,\"text\":\"报警实例\",\"iconCls\": \"protocol\",\"checked\": false,\"expanded\": true,\"children\": "+alarmInstance_json+"}");
		
		result_json.append("]");
		
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		String json=result_json.toString();
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveImportProtocolData")
	public String saveImportProtocolData() throws Exception {
		String json ="{success:true}";
		String data = ParamUtils.getParameter(request, "data");
		String check=ParamUtils.getParameter(request, "check");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		if("1".equals(check)){
			json = acquisitionUnitItemManagerService.importProtocolCheck(data,language);
		}else{
			json = acquisitionUnitItemManagerService.saveImportProtocolData(data);
		}
		
		
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		if(user!=null){
			this.service.saveSystemLog(user,2,languageResourceMap.get("importProtocol"));
		}
		
//		json ="{success:true}";
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.warn("jh json is ==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportProtocolContentData")
	public String getImportProtocolContentData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String classes = ParamUtils.getParameter(request, "classes");
		String type = ParamUtils.getParameter(request, "type");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolContentData(id,classes,type,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolDeviceTypeChangeProtocolList")
	public String getProtocolDeviceTypeChangeProtocolList() throws Exception {
		this.pager=new Page("pageForm",request);
		String deviceTypeIds = ParamUtils.getParameter(request, "deviceTypeIds");
		deviceTypeIds="";
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		
		if (!StringManagerUtils.isNotNull(deviceTypeIds)) {
			if (user != null) {
				deviceTypeIds = "" + user.getDeviceTypeIds();
			}
		}
		String json = this.acquisitionUnitItemManagerService.getProtocolDeviceTypeChangeProtocolList(deviceTypeIds,language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	@RequestMapping("/changeProtocolDeviceType")
	public String changeProtocolDeviceType() throws Exception {
		this.pager=new Page("pageForm",request);
		String selectedProtocolId = ParamUtils.getParameter(request, "selectedProtocolId");
		String selectedDeviceTypeId=ParamUtils.getParameter(request, "selectedDeviceTypeId");
		this.acquisitionUnitItemManagerService.changeProtocolDeviceType(selectedProtocolId,selectedDeviceTypeId);
		String json = "{\"success\":true}";
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportProtocolAcqUnitData")
	public String exportProtocolAcqUnitData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String unitList=ParamUtils.getParameter(request, "unitList");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.getProtocolAcqUnitExportData(unitList);
		String fileName=languageResourceMap.get("exportAcqUnit")+".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("exportAcqUnit"));
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
	
	@RequestMapping("/exportAllProtocolAcqUnitData")
	public String exportAllProtocolAcqUnitData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String unitList=ParamUtils.getParameter(request, "unitList");
		String key = ParamUtils.getParameter(request, "key");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.getAllProtocolAcqUnitExportData(user);
		fileName+=".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
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
	
	@RequestMapping("/exportProtocolAlarmUnitData")
	public String exportProtocolAlarmUnitData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String unitList=ParamUtils.getParameter(request, "unitList");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.getProtocolAlarmUnitExportData(unitList);
		String fileName=languageResourceMap.get("exportAlarmUnit")+".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("exportAlarmUnit"));
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
	
	@RequestMapping("/exportAllProtocolAlarmUnitData")
	public String exportAllProtocolAlarmUnitData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String key = ParamUtils.getParameter(request, "key");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.exportAllProtocolAlarmUnitData(user);
		fileName+=".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
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
	
	@RequestMapping("/exportProtocolReportUnitData")
	public String exportProtocolReportUnitData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String unitList=ParamUtils.getParameter(request, "unitList");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.getProtocolReportUnitExportData(unitList,language);
		String fileName=languageResourceMap.get("exportReportUnit")+".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
			}
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("exportReportUnit"));
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
	
	@RequestMapping("/exportAllReportUnitData")
	public String exportAllReportUnitData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String unitList=ParamUtils.getParameter(request, "unitList");
		String key = ParamUtils.getParameter(request, "key");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.exportAllReportUnitData(user);
		fileName+=".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
			}
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
	
	@RequestMapping("/exportProtocolDisplayUnitData")
	public String exportProtocolDisplayUnitData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String unitList=ParamUtils.getParameter(request, "unitList");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.getProtocolDisplayUnitExportData(unitList);
		String fileName=languageResourceMap.get("exportDisplayUnit")+".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("exportDisplayUnit"));
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
	
	@RequestMapping("/exportAllProtocolDisplayUnitData")
	public String exportAllProtocolDisplayUnitData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.getAllProtocolDisplayUnitExportData(user);
		fileName+=".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
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
	
	@RequestMapping("/exportProtocolAcqInstanceData")
	public String exportProtocolAcqInstanceData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String instanceList=ParamUtils.getParameter(request, "instanceList");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.getProtocolAcqInstanceExportData(instanceList);
		String fileName=languageResourceMap.get("exportAcqInstance")+".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
			if(user!=null){
				this.service.saveSystemLog(user,2,languageResourceMap.get("exportAcqInstance"));
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
	
	@RequestMapping("/exportAllProtocolAcqInstanceData")
	public String exportAllProtocolAcqInstanceData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String instanceList=ParamUtils.getParameter(request, "instanceList");
		String key = ParamUtils.getParameter(request, "key");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.exportAllProtocolAcqInstanceData(user);
		fileName+=".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
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
	
	@RequestMapping("/exportProtocolAcqInstanceInitData")
	public String exportProtocolAcqInstanceInitData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String instanceList=ParamUtils.getParameter(request, "instanceList");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.getProtocolAcqInstanceInitExportData(instanceList);
		String fileName=languageResourceMap.get("exportAcqInstanceInitData")+".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
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
	
	@RequestMapping("/exportProtocolDisplayInstanceData")
	public String exportProtocolDisplayInstanceData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String instanceList=ParamUtils.getParameter(request, "instanceList");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.getProtocolDisplayInstanceExportData(instanceList);
		String fileName=languageResourceMap.get("exportDisplayInstance")+".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
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
	
	@RequestMapping("/exportAllProtocolDisplayInstanceData")
	public String exportAllProtocolDisplayInstanceData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String instanceList=ParamUtils.getParameter(request, "instanceList");
		String key = ParamUtils.getParameter(request, "key");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.exportAllProtocolDisplayInstanceData(user);
		fileName+=".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
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
	
	@RequestMapping("/exportProtocolAlarmInstanceData")
	public String exportProtocolAlarmInstanceData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String instanceList=ParamUtils.getParameter(request, "instanceList");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.getProtocolAlarmInstanceExportData(instanceList);
		String fileName=languageResourceMap.get("exportAlarmInstance")+".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
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
	
	@RequestMapping("/exportAllProtocolAlarmInstanceData")
	public String exportAllProtocolAlarmInstanceData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.exportAllProtocolAlarmInstanceData(user);
		fileName+=".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
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
	
	@RequestMapping("/exportProtocolReportInstanceData")
	public String exportProtocolReportInstanceData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String instanceList=ParamUtils.getParameter(request, "instanceList");
		String key = ParamUtils.getParameter(request, "key");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.getProtocolReportInstanceExportData(instanceList);
		String fileName=languageResourceMap.get("exportReportInstance")+".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
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
	
	@RequestMapping("/exportAllReportInstanceData")
	public String exportAllReportInstanceData() throws Exception{
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String key = ParamUtils.getParameter(request, "key");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		String json=acquisitionUnitManagerService.exportAllReportInstanceData(user);
		fileName+=".json";
		String path=stringManagerUtils.getFilePath(fileName,"download/");
		File file=StringManagerUtils.createJsonFile(json, path);
		InputStream in=null;
		OutputStream out=null;
		try {
			if(session!=null){
				session.removeAttribute(key);
				session.setAttribute(key, 0);
				user = (User) session.getAttribute("userLogin");
			}
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
	
	@RequestMapping("/uploadImportedProtocolFile")
	public String uploadImportedProtocolFile(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer result_json = new StringBuffer();
		boolean flag=false;
		String protocolName="";
		String key="uploadProtocolFile"+(user!=null?user.getUserNo():0);
		String uploadProtocolMappingColumnKey="uploadProtocolMappingColumn"+(user!=null?user.getUserNo():0);
		session.removeAttribute(key);
		session.removeAttribute(uploadProtocolMappingColumnKey);
		
		String json = "";
		String fileContent="";
		if(files.length>0 && (!files[0].isEmpty())){
			try{
				byte[] buffer = files[0].getBytes();
				fileContent = new String(buffer, "UTF-8");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String code="";
		try{
			Map<String, Object> result = gson.fromJson(fileContent, new TypeToken<Map<String, Object>>(){}.getType());
			code =result.containsKey("Code")?((String) result.get("Code")):"";
			if("Protocol".equalsIgnoreCase(code)){
				if (result.containsKey("List")) {
					List<ExportProtocolData> protocolList=new ArrayList<>();
					
					List<Map<String, Object>> listData = (List<Map<String, Object>>) result.get("List");
			        for (Map<String, Object> item : listData) {
			            String itemJson = gson.toJson(item);
			            ExportProtocolData protocol = gson.fromJson(itemJson, ExportProtocolData.class);
			            protocolList.add(protocol);
			        } 
			        
			        if(protocolList!=null){
						flag=true;
						session.setAttribute(key, protocolList);
					}
				}
				
				if(result.containsKey("DataMappingList")){
					List<ExportProtocolData.DataMapping> dataMappingList=new ArrayList<>();
					List<Map<String, Object>> listData = (List<Map<String, Object>>) result.get("DataMappingList");
			        for (Map<String, Object> item : listData) {
			            String itemJson = gson.toJson(item);
			            ExportProtocolData.DataMapping dataMapping = gson.fromJson(itemJson, ExportProtocolData.DataMapping.class);
			            dataMappingList.add(dataMapping);
			        } 
			        
			        if(dataMappingList!=null){
						flag=true;
						session.setAttribute(uploadProtocolMappingColumnKey, dataMappingList);
					}
				}
			}
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
		}
		result_json.append("{ \"success\":true,\"flag\":"+flag+"}");
		
		json=result_json.toString();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getUploadedProtocolTreeData")
	public String getUploadedProtocolTreeData() throws IOException {
		HttpSession session=request.getSession();
		List<ExportProtocolData> protocolList=null;
		User user = (User) session.getAttribute("userLogin");
		String key="uploadProtocolFile"+(user!=null?user.getUserNo():0);
		try{
			if(session.getAttribute(key)!=null){
				protocolList=(List<ExportProtocolData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = acquisitionUnitItemManagerService.getUploadedProtocolTreeData(protocolList,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getUploadedProtocolItemsConfigData")
	public String getUploadedProtocolItemsConfigData() throws Exception {
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String key="uploadProtocolFile"+(user!=null?user.getUserNo():0);
		
		List<ExportProtocolData> protocolList=null;
		try{
			if(session.getAttribute(key)!=null){
				protocolList=(List<ExportProtocolData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		String json = "";
		json = acquisitionUnitItemManagerService.getUploadedProtocolItemsConfigData(protocolName,classes,code,protocolList,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getUploadedProtocolExtendedFieldsConfigData")
	public String getUploadedProtocolExtendedFieldsConfigData() throws Exception {
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String key="uploadProtocolFile"+(user!=null?user.getUserNo():0);
		List<ExportProtocolData> protocolList=null;
		try{
			if(session.getAttribute(key)!=null){
				protocolList=(List<ExportProtocolData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		String json = "";
		json = acquisitionUnitItemManagerService.getUploadedProtocolExtendedFieldsConfigData(protocolName,classes,code,protocolList,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveSingelImportedProtocol")
	public String saveSingelImportedProtocol() throws Exception {
		HttpSession session=request.getSession();
		String protocolName=ParamUtils.getParameter(request, "protocolName");
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		User user = (User) session.getAttribute("userLogin");
		String key="uploadProtocolFile"+(user!=null?user.getUserNo():0);
		List<ExportProtocolData> protocolList=null;
		try{
			if(session.getAttribute(key)!=null){
				protocolList=(List<ExportProtocolData>) session.getAttribute(key);
				if(protocolList!=null && protocolList.size()>0){
					Iterator<ExportProtocolData> it = protocolList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportProtocolData protocol=(ExportProtocolData)it.next();
						if(protocolName.equalsIgnoreCase(protocol.getName())){
							protocol.setDeviceType(StringManagerUtils.stringToInteger(deviceType));
							acquisitionUnitItemManagerService.updateProtocol(protocol,user);
							it.remove();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAllImportedProtocol")
	public String saveAllImportedProtocol() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String protocolName=ParamUtils.getParameter(request, "protocolName");
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		String[] protocolNameList=protocolName.split(",");
		String key="uploadProtocolFile"+(user!=null?user.getUserNo():0);
		List<ExportProtocolData> protocolList=null;
		try{
			if(session.getAttribute(key)!=null){
				protocolList=(List<ExportProtocolData>) session.getAttribute(key);
				if(protocolList!=null && protocolList.size()>0){
					Iterator<ExportProtocolData> it = protocolList.iterator();
					while(it.hasNext()){
						ExportProtocolData protocol=(ExportProtocolData)it.next();
						if(StringManagerUtils.existOrNot(protocolNameList, protocol.getName(), false)){
							protocol.setDeviceType(StringManagerUtils.stringToInteger(deviceType));
							acquisitionUnitItemManagerService.updateProtocol(protocol,user);
							it.remove();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveProtocolBackupData")
	public String saveProtocolBackupData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String protocolName=ParamUtils.getParameter(request, "protocolName");
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		String[] protocolNameList=protocolName.split(",");
		String key="uploadProtocolFile"+(user!=null?user.getUserNo():0);
		String uploadProtocolMappingColumnKey="uploadProtocolMappingColumn"+(user!=null?user.getUserNo():0);
		List<ExportProtocolData> protocolList=null;
		List<ExportProtocolData.DataMapping> dataMappingList=null;
		
		try{
			if(session.getAttribute(key)!=null){
				protocolList=(List<ExportProtocolData>) session.getAttribute(key);
				if(session.getAttribute(uploadProtocolMappingColumnKey)!=null){
					dataMappingList=(List<ExportProtocolData.DataMapping>) session.getAttribute(uploadProtocolMappingColumnKey);
				}
				acquisitionUnitItemManagerService.saveProtocolBackupData(protocolList,dataMappingList,user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/uploadImportedAcqUnitFile")
	public String uploadImportedAcqUnitFile(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer result_json = new StringBuffer();
		boolean flag=false;
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAcqUnitFile"+(user!=null?user.getUserNo():0);
		
		session.removeAttribute(key);
		
		String json = "";
		String fileContent="";
		if(files.length>0 && (!files[0].isEmpty())){
			try{
				byte[] buffer = files[0].getBytes();
				fileContent = new String(buffer, "UTF-8");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String code="";
		try{
			Map<String, Object> result = gson.fromJson(fileContent, new TypeToken<Map<String, Object>>(){}.getType());
			code =result.containsKey("Code")?((String) result.get("Code")):"";
			if("AcqUnit".equalsIgnoreCase(code)){
				if (result.containsKey("List")) {
					List<ExportAcqUnitData> uploadAcqUnitList=new ArrayList<>();
					
					List<Map<String, Object>> listData = (List<Map<String, Object>>) result.get("List");
			        for (Map<String, Object> item : listData) {
			            String itemJson = gson.toJson(item);
			            ExportAcqUnitData exportAcqUnitData = gson.fromJson(itemJson, ExportAcqUnitData.class);
			            uploadAcqUnitList.add(exportAcqUnitData);
			        } 
			        
			        if(uploadAcqUnitList!=null){
						flag=true;
						session.setAttribute(key, uploadAcqUnitList);
					}
				}
			}
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
		}
		result_json.append("{ \"success\":true,\"flag\":"+flag+"}");
		
		json=result_json.toString();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	
	@RequestMapping("/getUploadedAcqUnitTreeData")
	public String getUploadedAcqUnitTreeData() throws IOException {
		HttpSession session=request.getSession();
		List<ExportAcqUnitData> uploadAcqUnitList=null;
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAcqUnitFile"+(user!=null?user.getUserNo():0);
		try{
			if(session.getAttribute(key)!=null){
				uploadAcqUnitList=(List<ExportAcqUnitData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = acquisitionUnitItemManagerService.getUploadedAcqUnitTreeData(uploadAcqUnitList,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	
	@RequestMapping("/getUploadedAcqUnitItemsConfigData")
	public String getUploadedAcqUnitItemsConfigData() throws Exception {
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String protocolDeviceType = ParamUtils.getParameter(request, "protocolDeviceType");
		String classes = ParamUtils.getParameter(request, "classes");
		String unitName = ParamUtils.getParameter(request, "unitName");
		String groupName = ParamUtils.getParameter(request, "groupName");
		String groupType = ParamUtils.getParameter(request, "groupType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String key="uploadAcqUnitFile"+(user!=null?user.getUserNo():0);
		String json = "";
		List<ExportAcqUnitData> uploadAcqUnitList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadAcqUnitList=(List<ExportAcqUnitData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		json = acquisitionUnitItemManagerService.getUploadedAcqUnitItemsConfigData(protocolName,protocolDeviceType,classes,unitName,groupName,groupType,uploadAcqUnitList,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveSingelImportedAcqUnit")
	public String saveSingelImportedAcqUnit() throws Exception {
		HttpSession session=request.getSession();
		String protocolName=ParamUtils.getParameter(request, "protocolName");
		String protocolDeviceType=ParamUtils.getParameter(request, "protocolDeviceType");
		String unitName=ParamUtils.getParameter(request, "unitName");
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAcqUnitFile"+(user!=null?user.getUserNo():0);
		List<ExportAcqUnitData> uploadAcqUnitList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadAcqUnitList=(List<ExportAcqUnitData>) session.getAttribute(key);
				if(uploadAcqUnitList!=null && uploadAcqUnitList.size()>0){
					Iterator<ExportAcqUnitData> it = uploadAcqUnitList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportAcqUnitData exportAcqUnitData=(ExportAcqUnitData)it.next();
						if(protocolName.equalsIgnoreCase(exportAcqUnitData.getProtocolName()) 
								&& protocolDeviceType.equalsIgnoreCase(exportAcqUnitData.getProtocolDeviceType()) 
								&& unitName.equalsIgnoreCase(exportAcqUnitData.getUnitName())){
							acquisitionUnitItemManagerService.importAcqUnit(exportAcqUnitData,user);
							it.remove();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAllImportedAcqUnit")
	public String saveAllImportedAcqUnit() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String unitName=ParamUtils.getParameter(request, "unitName");
		String[] unitNameList=unitName.split(",");
		String key="uploadAcqUnitFile"+(user!=null?user.getUserNo():0);
		List<ExportAcqUnitData> uploadAcqUnitList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadAcqUnitList=(List<ExportAcqUnitData>) session.getAttribute(key);
				if(uploadAcqUnitList!=null && uploadAcqUnitList.size()>0){
					Iterator<ExportAcqUnitData> it = uploadAcqUnitList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportAcqUnitData exportAcqUnitData=(ExportAcqUnitData)it.next();
						acquisitionUnitItemManagerService.importAcqUnit(exportAcqUnitData,user);
						it.remove();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAcqUnitBackupData")
	public String saveAcqUnitBackupData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String unitName=ParamUtils.getParameter(request, "unitName");
		String[] unitNameList=unitName.split(",");
		String key="uploadAcqUnitFile"+(user!=null?user.getUserNo():0);
		List<ExportAcqUnitData> uploadAcqUnitList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadAcqUnitList=(List<ExportAcqUnitData>) session.getAttribute(key);
				if(uploadAcqUnitList!=null && uploadAcqUnitList.size()>0){
					acquisitionUnitItemManagerService.saveAcqUnitBackupData(uploadAcqUnitList,user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/uploadImportedAlarmUnitFile")
	public String uploadImportedAlarmUnitFile(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer result_json = new StringBuffer();
		boolean flag=false;
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAlarmUnitFile"+(user!=null?user.getUserNo():0);
		session.removeAttribute(key);
		
		String json = "";
		String fileContent="";
		if(files.length>0 && (!files[0].isEmpty())){
			try{
				byte[] buffer = files[0].getBytes();
				fileContent = new String(buffer, "UTF-8");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String code="";
		try{
			Map<String, Object> result = gson.fromJson(fileContent, new TypeToken<Map<String, Object>>(){}.getType());
			code =result.containsKey("Code")?((String) result.get("Code")):"";
			if("AlarmUnit".equalsIgnoreCase(code)){
				if (result.containsKey("List")) {
					List<ExportAlarmUnitData> uploadUnitList=new ArrayList<>();
					
					List<Map<String, Object>> listData = (List<Map<String, Object>>) result.get("List");
			        for (Map<String, Object> item : listData) {
			            String itemJson = gson.toJson(item);
			            ExportAlarmUnitData exportAlarmUnitData = gson.fromJson(itemJson, ExportAlarmUnitData.class);
			            uploadUnitList.add(exportAlarmUnitData);
			        } 
			        
			        if(uploadUnitList!=null){
						flag=true;
						session.setAttribute(key, uploadUnitList);
					}
				}
			}
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
		}
		result_json.append("{ \"success\":true,\"flag\":"+flag+"}");
		
		json=result_json.toString();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getUploadedAlarmUnitTreeData")
	public String getUploadedAlarmUnitTreeData() throws IOException {
		HttpSession session=request.getSession();
		List<ExportAlarmUnitData> uploadUnitList=null;
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAlarmUnitFile"+(user!=null?user.getUserNo():0);
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportAlarmUnitData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = acquisitionUnitItemManagerService.getUploadedAlarmUnitTreeData(uploadUnitList,deviceType,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportAlarmUnitItemsData")
	public String getImportAlarmUnitItemsData() throws IOException {
		HttpSession session=request.getSession();
		String protocolName=ParamUtils.getParameter(request, "protocolName");
		String protocolDeviceType=ParamUtils.getParameter(request, "protocolDeviceType");
		String unitName=ParamUtils.getParameter(request, "unitName");
		String alarmType=ParamUtils.getParameter(request, "alarmType");
		String calculateType=ParamUtils.getParameter(request, "calculateType");
		
		List<ExportAlarmUnitData> uploadUnitList=null;
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAlarmUnitFile"+(user!=null?user.getUserNo():0);
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportAlarmUnitData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = acquisitionUnitItemManagerService.getImportAlarmUnitItemsData(uploadUnitList,protocolName,protocolDeviceType,unitName,alarmType,calculateType,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveSingelImportedAlarmUnit")
	public String saveSingelImportedAlarmUnit() throws Exception {
		HttpSession session=request.getSession();
		String protocolName=ParamUtils.getParameter(request, "protocolName");
		String protocolDeviceType=ParamUtils.getParameter(request, "protocolDeviceType");
		String unitName=ParamUtils.getParameter(request, "unitName");
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAlarmUnitFile"+(user!=null?user.getUserNo():0);
		List<ExportAlarmUnitData> uploadUnitList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportAlarmUnitData>) session.getAttribute(key);
				if(uploadUnitList!=null && uploadUnitList.size()>0){
					Iterator<ExportAlarmUnitData> it = uploadUnitList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportAlarmUnitData exportAlarmUnitData=(ExportAlarmUnitData)it.next();
						if(protocolName.equalsIgnoreCase(exportAlarmUnitData.getProtocolName()) 
								&& protocolDeviceType.equalsIgnoreCase(exportAlarmUnitData.getProtocolDeviceType()) 
								&& unitName.equalsIgnoreCase(exportAlarmUnitData.getUnitName())){
							acquisitionUnitItemManagerService.importAlarmUnit(exportAlarmUnitData,user);
							it.remove();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAllImportedAlarmUnit")
	public String saveAllImportedAlarmUnit() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String unitName=ParamUtils.getParameter(request, "unitName");
		String[] unitNameList=unitName.split(",");
		String key="uploadAlarmUnitFile"+(user!=null?user.getUserNo():0);
		List<ExportAlarmUnitData> uploadUnitList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportAlarmUnitData>) session.getAttribute(key);
				if(uploadUnitList!=null && uploadUnitList.size()>0){
					Iterator<ExportAlarmUnitData> it = uploadUnitList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportAlarmUnitData exportAlarmUnitData=(ExportAlarmUnitData)it.next();
						acquisitionUnitItemManagerService.importAlarmUnit(exportAlarmUnitData,user);
						it.remove();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAlarmUnitBackupData")
	public String saveAlarmUnitBackupData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String unitName=ParamUtils.getParameter(request, "unitName");
		String[] unitNameList=unitName.split(",");
		String key="uploadAlarmUnitFile"+(user!=null?user.getUserNo():0);
		List<ExportAlarmUnitData> uploadUnitList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportAlarmUnitData>) session.getAttribute(key);
				if(uploadUnitList!=null && uploadUnitList.size()>0){
					acquisitionUnitItemManagerService.saveAlarmUnitBackupData(uploadUnitList,user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/uploadImportedDisplayUnitFile")
	public String uploadImportedDisplayUnitFile(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer result_json = new StringBuffer();
		boolean flag=false;
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadDisplayUnitFile"+(user!=null?user.getUserNo():0);
		session.removeAttribute(key);
		
		String json = "";
		String fileContent="";
		if(files.length>0 && (!files[0].isEmpty())){
			try{
				byte[] buffer = files[0].getBytes();
				fileContent = new String(buffer, "UTF-8");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String code="";
		try{
			Map<String, Object> result = gson.fromJson(fileContent, new TypeToken<Map<String, Object>>(){}.getType());
			code =result.containsKey("Code")?((String) result.get("Code")):"";
			if("DisplayUnit".equalsIgnoreCase(code)){
				if (result.containsKey("List")) {
					List<ExportDisplayUnitData> uploadUnitList=new ArrayList<>();
					
					List<Map<String, Object>> listData = (List<Map<String, Object>>) result.get("List");
			        for (Map<String, Object> item : listData) {
			            String itemJson = gson.toJson(item);
			            ExportDisplayUnitData exportDisplayUnitData = gson.fromJson(itemJson, ExportDisplayUnitData.class);
			            uploadUnitList.add(exportDisplayUnitData);
			        } 
			        
			        if(uploadUnitList!=null){
						flag=true;
						session.setAttribute(key, uploadUnitList);
					}
				}
			}
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
		}
		
		result_json.append("{ \"success\":true,\"flag\":"+flag+"}");
		
		json=result_json.toString();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getUploadedDisplayUnitTreeData")
	public String getUploadedDisplayUnitTreeData() throws IOException {
		HttpSession session=request.getSession();
		List<ExportDisplayUnitData> uploadUnitList=null;
		User user = (User) session.getAttribute("userLogin");
		String key="uploadDisplayUnitFile"+(user!=null?user.getUserNo():0);
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportDisplayUnitData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = acquisitionUnitItemManagerService.getUploadedDisplayUnitTreeData(uploadUnitList,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportDisplayUnitItemsConfigData")
	public String getImportDisplayUnitItemsConfigData() throws IOException {
		String protocolName=ParamUtils.getParameter(request, "protocolName");
		String protocolDeviceType=ParamUtils.getParameter(request, "protocolDeviceType");
		
		String acqUnitName=ParamUtils.getParameter(request, "acqUnitName");
		String unitName=ParamUtils.getParameter(request, "unitName");
		String calculateType=ParamUtils.getParameter(request, "calculateType");
		String type=ParamUtils.getParameter(request, "type");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String key="uploadDisplayUnitFile"+(user!=null?user.getUserNo():0);
		List<ExportDisplayUnitData> uploadUnitList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportDisplayUnitData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = acquisitionUnitItemManagerService.getImportDisplayUnitItemsConfigData(uploadUnitList,protocolName,protocolDeviceType,acqUnitName,unitName,calculateType,type,language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveSingelImportedDisplayUnit")
	public String saveSingelImportedDisplayUnit() throws Exception {
		HttpSession session=request.getSession();
		String protocolName=ParamUtils.getParameter(request, "protocolName");
		String protocolDeviceType=ParamUtils.getParameter(request, "protocolDeviceType");
		
		String acqUnit=ParamUtils.getParameter(request, "acqUnit");
		String unitName=ParamUtils.getParameter(request, "unitName");
		User user = (User) session.getAttribute("userLogin");
		String key="uploadDisplayUnitFile"+(user!=null?user.getUserNo():0);
		List<ExportDisplayUnitData> uploadUnitList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportDisplayUnitData>) session.getAttribute(key);
				if(uploadUnitList!=null && uploadUnitList.size()>0){
					Iterator<ExportDisplayUnitData> it = uploadUnitList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportDisplayUnitData exportDisplayUnitData=(ExportDisplayUnitData)it.next();
						if(protocolName.equalsIgnoreCase(exportDisplayUnitData.getProtocolName()) 
								&& protocolDeviceType.equalsIgnoreCase(exportDisplayUnitData.getProtocolDeviceType()) 
								&& acqUnit.equalsIgnoreCase(exportDisplayUnitData.getAcqUnit())
								&& unitName.equalsIgnoreCase(exportDisplayUnitData.getUnitName())
								){
							acquisitionUnitItemManagerService.importDisplayUnit(exportDisplayUnitData,user);
							it.remove();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAllImportedDisplayUnit")
	public String saveAllImportedDisplayUnit() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadDisplayUnitFile"+(user!=null?user.getUserNo():0);
		List<ExportDisplayUnitData> uploadUnitList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportDisplayUnitData>) session.getAttribute(key);
				if(uploadUnitList!=null && uploadUnitList.size()>0){
					Iterator<ExportDisplayUnitData> it = uploadUnitList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportDisplayUnitData exportDisplayUnitData=(ExportDisplayUnitData)it.next();
						acquisitionUnitItemManagerService.importDisplayUnit(exportDisplayUnitData,user);
						it.remove();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveDisplayUnitBackupData")
	public String saveDisplayUnitBackupData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadDisplayUnitFile"+(user!=null?user.getUserNo():0);
		List<ExportDisplayUnitData> uploadUnitList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportDisplayUnitData>) session.getAttribute(key);
				if(uploadUnitList!=null && uploadUnitList.size()>0){
					acquisitionUnitItemManagerService.saveDisplayUnitBackupData(uploadUnitList,user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/uploadImportedReportUnitFile")
	public String uploadImportedReportUnitFile(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer result_json = new StringBuffer();
		boolean flag=false;
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadReportUnitFile"+(user!=null?user.getUserNo():0);
		session.removeAttribute(key);
		
		String json = "";
		String fileContent="";
		if(files.length>0 && (!files[0].isEmpty())){
			try{
				byte[] buffer = files[0].getBytes();
				fileContent = new String(buffer, "UTF-8");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String code="";
		try{
			Map<String, Object> result = gson.fromJson(fileContent, new TypeToken<Map<String, Object>>(){}.getType());
			code =result.containsKey("Code")?((String) result.get("Code")):"";
			if("ReportUnit".equalsIgnoreCase(code)){
				if (result.containsKey("List")) {
					List<ExportReportUnitData> uploadUnitList=new ArrayList<>();
					
					List<Map<String, Object>> listData = (List<Map<String, Object>>) result.get("List");
			        for (Map<String, Object> item : listData) {
			            String itemJson = gson.toJson(item);
			            ExportReportUnitData exportReportUnitData = gson.fromJson(itemJson, ExportReportUnitData.class);
			            uploadUnitList.add(exportReportUnitData);
			        } 
			        
			        if(uploadUnitList!=null){
						flag=true;
						session.setAttribute(key, uploadUnitList);
					}
				}
			}
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
		}
		result_json.append("{ \"success\":true,\"flag\":"+flag+"}");
		
		json=result_json.toString();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getUploadedReportUnitTreeData")
	public String getUploadedReportUnitTreeData() throws IOException {
		HttpSession session=request.getSession();
		List<ExportReportUnitData> uploadUnitList=null;
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		User user = (User) session.getAttribute("userLogin");
		String key="uploadReportUnitFile"+(user!=null?user.getUserNo():0);
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportReportUnitData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = acquisitionUnitItemManagerService.getUploadedReportUnitTreeData(uploadUnitList,deviceType,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportReportUnitTemplateData")
	public String getImportReportUnitTemplateData() throws IOException {
		HttpSession session=request.getSession();
		List<ExportReportUnitData> uploadUnitList=null;
		String reportType=ParamUtils.getParameter(request, "reportType");
		String unitName=ParamUtils.getParameter(request, "unitName");
		User user = (User) session.getAttribute("userLogin");
		String key="uploadReportUnitFile"+(user!=null?user.getUserNo():0);
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportReportUnitData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = acquisitionUnitItemManagerService.getImportReportUnitTemplateData(uploadUnitList,reportType,unitName);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportReportUnitItemsConfigData")
	public String getImportReportUnitItemsConfigData() throws IOException {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String key="uploadReportUnitFile"+(user!=null?user.getUserNo():0);
		List<ExportReportUnitData> uploadUnitList=null;
		String reportType=ParamUtils.getParameter(request, "reportType");
		String unitName=ParamUtils.getParameter(request, "unitName");
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportReportUnitData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = acquisitionUnitItemManagerService.getImportReportUnitItemsConfigData(uploadUnitList,reportType,unitName,language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveSingelImportedReportUnit")
	public String saveSingelImportedReportUnit() throws Exception {
		HttpSession session=request.getSession();
		String unitName=ParamUtils.getParameter(request, "unitName");
		User user = (User) session.getAttribute("userLogin");
		String key="uploadReportUnitFile"+(user!=null?user.getUserNo():0);
		List<ExportReportUnitData> uploadUnitList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportReportUnitData>) session.getAttribute(key);
				if(uploadUnitList!=null && uploadUnitList.size()>0){
					Iterator<ExportReportUnitData> it = uploadUnitList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportReportUnitData exportReportUnitData=(ExportReportUnitData)it.next();
						if(unitName.equalsIgnoreCase(exportReportUnitData.getUnitName())){
							acquisitionUnitItemManagerService.importReportUnit(exportReportUnitData,user);
							it.remove();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAllImportedReportUnit")
	public String saveAllImportedReportUnit() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadReportUnitFile"+(user!=null?user.getUserNo():0);
		List<ExportReportUnitData> uploadUnitList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportReportUnitData>) session.getAttribute(key);
				if(uploadUnitList!=null && uploadUnitList.size()>0){
					Iterator<ExportReportUnitData> it = uploadUnitList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportReportUnitData exportReportUnitData=(ExportReportUnitData)it.next();
						acquisitionUnitItemManagerService.importReportUnit(exportReportUnitData,user);
						it.remove();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveReportUnitBackupData")
	public String saveReportUnitBackupData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadReportUnitFile"+(user!=null?user.getUserNo():0);
		List<ExportReportUnitData> uploadUnitList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadUnitList=(List<ExportReportUnitData>) session.getAttribute(key);
				if(uploadUnitList!=null && uploadUnitList.size()>0){
					acquisitionUnitItemManagerService.saveReportUnitBackupData(uploadUnitList,user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/uploadImportedAcqInstanceFile")
	public String uploadImportedAcqInstanceFile(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer result_json = new StringBuffer();
		boolean flag=false;
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAcqInstanceFile"+(user!=null?user.getUserNo():0);
		session.removeAttribute(key);
		
		String json = "";
		String fileContent="";
		if(files.length>0 && (!files[0].isEmpty())){
			try{
				byte[] buffer = files[0].getBytes();
				fileContent = new String(buffer, "UTF-8");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String code="";
		try{
			Map<String, Object> result = gson.fromJson(fileContent, new TypeToken<Map<String, Object>>(){}.getType());
			code =result.containsKey("Code")?((String) result.get("Code")):"";
			if("AcqInstance".equalsIgnoreCase(code)){
				if (result.containsKey("List")) {
					List<ExportAcqInstanceData> uploadInstanceList=new ArrayList<>();
					List<Map<String, Object>> listData = (List<Map<String, Object>>) result.get("List");
			        for (Map<String, Object> item : listData) {
			            String itemJson = gson.toJson(item);
			            ExportAcqInstanceData exportAcqInstanceData = gson.fromJson(itemJson, ExportAcqInstanceData.class);
			            uploadInstanceList.add(exportAcqInstanceData);
			        } 
			        
			        if(uploadInstanceList!=null){
						flag=true;
						session.setAttribute(key, uploadInstanceList);
					}
				}
			}
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
		}
		
		result_json.append("{ \"success\":true,\"flag\":"+flag+"}");
		
		json=result_json.toString();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getUploadedAcqInstanceTreeData")
	public String getUploadedAcqInstanceTreeData() throws IOException {
		HttpSession session=request.getSession();
		List<ExportAcqInstanceData> uploadInstanceList=null;
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAcqInstanceFile"+(user!=null?user.getUserNo():0);
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportAcqInstanceData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = acquisitionUnitItemManagerService.getUploadedAcqInstanceTreeData(uploadInstanceList,deviceType,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	
	@RequestMapping("/saveSingelImportedAcqInstance")
	public String saveSingelImportedAcqInstance() throws Exception {
		HttpSession session=request.getSession();
		String protocolName=ParamUtils.getParameter(request, "protocolName");
		String protocolDeviceType=ParamUtils.getParameter(request, "protocolDeviceType");
		
		String unitName=ParamUtils.getParameter(request, "unitName");
		String instanceName=ParamUtils.getParameter(request, "instanceName");
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAcqInstanceFile"+(user!=null?user.getUserNo():0);
		List<ExportAcqInstanceData> uploadInstanceList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportAcqInstanceData>) session.getAttribute(key);
				if(uploadInstanceList!=null && uploadInstanceList.size()>0){
					Iterator<ExportAcqInstanceData> it = uploadInstanceList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportAcqInstanceData instanceData=(ExportAcqInstanceData)it.next();
						if(protocolName.equalsIgnoreCase(instanceData.getProtocolName()) 
								&& protocolDeviceType.equalsIgnoreCase(instanceData.getProtocolDeviceType()) 
								&& unitName.equalsIgnoreCase(instanceData.getUnitName())
								&& instanceName.equalsIgnoreCase(instanceData.getName())
								){
							acquisitionUnitItemManagerService.importAcqInstance(instanceData,user);
							it.remove();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAllImportedAcqInstance")
	public String saveAllImportedAcqInstance() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAcqInstanceFile"+(user!=null?user.getUserNo():0);
		List<ExportAcqInstanceData> uploadInstanceList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportAcqInstanceData>) session.getAttribute(key);
				if(uploadInstanceList!=null && uploadInstanceList.size()>0){
					Iterator<ExportAcqInstanceData> it = uploadInstanceList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportAcqInstanceData instanceData=(ExportAcqInstanceData)it.next();
						acquisitionUnitItemManagerService.importAcqInstance(instanceData,user);
						it.remove();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAcqInstanceBackupData")
	public String saveAcqInstanceBackupData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAcqInstanceFile"+(user!=null?user.getUserNo():0);
		List<ExportAcqInstanceData> uploadInstanceList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportAcqInstanceData>) session.getAttribute(key);
				if(uploadInstanceList!=null && uploadInstanceList.size()>0){
					acquisitionUnitItemManagerService.saveAcqInstanceBackupData(uploadInstanceList,user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/uploadImportedDisplayInstanceFile")
	public String uploadImportedDisplayInstanceFile(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer result_json = new StringBuffer();
		boolean flag=false;
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadDisplayInstanceFile"+(user!=null?user.getUserNo():0);
		session.removeAttribute(key);
		
		String json = "";
		String fileContent="";
		if(files.length>0 && (!files[0].isEmpty())){
			try{
				byte[] buffer = files[0].getBytes();
				fileContent = new String(buffer, "UTF-8");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String code="";
		try{
			Map<String, Object> result = gson.fromJson(fileContent, new TypeToken<Map<String, Object>>(){}.getType());
			code =result.containsKey("Code")?((String) result.get("Code")):"";
			if("DisplayInstance".equalsIgnoreCase(code)){
				if (result.containsKey("List")) {
					List<ExportDisplayInstanceData> uploadInstanceList=new ArrayList<>();
					
					List<Map<String, Object>> listData = (List<Map<String, Object>>) result.get("List");
			        for (Map<String, Object> item : listData) {
			            String itemJson = gson.toJson(item);
			            ExportDisplayInstanceData exportDisplayInstanceData = gson.fromJson(itemJson, ExportDisplayInstanceData.class);
			            uploadInstanceList.add(exportDisplayInstanceData);
			        } 
			        
			        if(uploadInstanceList!=null){
						flag=true;
						session.setAttribute(key, uploadInstanceList);
					}
				}
			}
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
		}
		result_json.append("{ \"success\":true,\"flag\":"+flag+"}");
		
		json=result_json.toString();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getUploadedDisplayInstanceTreeData")
	public String getUploadedDisplayInstanceTreeData() throws IOException {
		HttpSession session=request.getSession();
		List<ExportDisplayInstanceData> uploadInstanceList=null;
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		User user = (User) session.getAttribute("userLogin");
		String key="uploadDisplayInstanceFile"+(user!=null?user.getUserNo():0);
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportDisplayInstanceData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = acquisitionUnitItemManagerService.getUploadedDisplayInstanceTreeData(uploadInstanceList,deviceType,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportDisplayInstanceItemsConfigData")
	public String getImportDisplayInstanceItemsConfigData() throws IOException {
		List<ExportDisplayInstanceData> uploadInstanceList=null;
		String protocolName=ParamUtils.getParameter(request, "protocolName");
		String acqUnitName=ParamUtils.getParameter(request, "acqUnitName");
		String displayUnitName=ParamUtils.getParameter(request, "displayUnitName");
		String instanceName=ParamUtils.getParameter(request, "instanceName");
		String type=ParamUtils.getParameter(request, "type");
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String key="uploadDisplayInstanceFile"+(user!=null?user.getUserNo():0);
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportDisplayInstanceData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = acquisitionUnitItemManagerService.getImportDisplayInstanceItemsConfigData(uploadInstanceList,protocolName,acqUnitName,displayUnitName,instanceName,type,language);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveSingelImportedDisplayInstance")
	public String saveSingelImportedDisplayInstance() throws Exception {
		HttpSession session=request.getSession();
		String protocolName=ParamUtils.getParameter(request, "protocolName");
		String displayUnitName=ParamUtils.getParameter(request, "displayUnitName");
		String acqUnitName=ParamUtils.getParameter(request, "acqUnitName");
		String instanceName=ParamUtils.getParameter(request, "instanceName");
		User user = (User) session.getAttribute("userLogin");
		String key="uploadDisplayInstanceFile"+(user!=null?user.getUserNo():0);
		List<ExportDisplayInstanceData> uploadInstanceList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportDisplayInstanceData>) session.getAttribute(key);
				if(uploadInstanceList!=null && uploadInstanceList.size()>0){
					Iterator<ExportDisplayInstanceData> it = uploadInstanceList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportDisplayInstanceData instanceData=(ExportDisplayInstanceData)it.next();
						if(protocolName.equalsIgnoreCase(instanceData.getProtocol()) 
								&& displayUnitName.equalsIgnoreCase(instanceData.getDisplayUnitName())
								&& acqUnitName.equalsIgnoreCase(instanceData.getAcqUnitName())
								&& instanceName.equalsIgnoreCase(instanceData.getName())
								){
							acquisitionUnitItemManagerService.importDisplayInstance(instanceData,user);
							it.remove();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAllImportedDisplayInstance")
	public String saveAllImportedDisplayInstance() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadDisplayInstanceFile"+(user!=null?user.getUserNo():0);
		List<ExportDisplayInstanceData> uploadInstanceList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportDisplayInstanceData>) session.getAttribute(key);
				if(uploadInstanceList!=null && uploadInstanceList.size()>0){
					Iterator<ExportDisplayInstanceData> it = uploadInstanceList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportDisplayInstanceData instanceData=(ExportDisplayInstanceData)it.next();
						acquisitionUnitItemManagerService.importDisplayInstance(instanceData,user);
						it.remove();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveDisplayInstanceBackupData")
	public String saveDisplayInstanceBackupData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadDisplayInstanceFile"+(user!=null?user.getUserNo():0);
		List<ExportDisplayInstanceData> uploadInstanceList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportDisplayInstanceData>) session.getAttribute(key);
				if(uploadInstanceList!=null && uploadInstanceList.size()>0){
					acquisitionUnitItemManagerService.saveDisplayInstanceBackupData(uploadInstanceList,user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/uploadImportedAlarmInstanceFile")
	public String uploadImportedAlarmInstanceFile(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer result_json = new StringBuffer();
		boolean flag=false;
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAlarmInstanceFile"+(user!=null?user.getUserNo():0);
		session.removeAttribute(key);
		
		String json = "";
		String fileContent="";
		if(files.length>0 && (!files[0].isEmpty())){
			try{
				byte[] buffer = files[0].getBytes();
				fileContent = new String(buffer, "UTF-8");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String code="";
		try{
			Map<String, Object> result = gson.fromJson(fileContent, new TypeToken<Map<String, Object>>(){}.getType());
			code =result.containsKey("Code")?((String) result.get("Code")):"";
			if("AlarmInstance".equalsIgnoreCase(code)){
				if (result.containsKey("List")) {
					List<ExportAlarmInstanceData> uploadInstanceList=new ArrayList<>();
					
					List<Map<String, Object>> listData = (List<Map<String, Object>>) result.get("List");
			        for (Map<String, Object> item : listData) {
			            String itemJson = gson.toJson(item);
			            ExportAlarmInstanceData exportAlarmInstanceData = gson.fromJson(itemJson, ExportAlarmInstanceData.class);
			            uploadInstanceList.add(exportAlarmInstanceData);
			        } 
			        
			        if(uploadInstanceList!=null){
						flag=true;
						session.setAttribute(key, uploadInstanceList);
					}
				}
			}
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
		}
		result_json.append("{ \"success\":true,\"flag\":"+flag+"}");
		
		json=result_json.toString();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getUploadedAlarmInstanceTreeData")
	public String getUploadedAlarmInstanceTreeData() throws IOException {
		HttpSession session=request.getSession();
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		List<ExportAlarmInstanceData> uploadInstanceList=null;
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAlarmInstanceFile"+(user!=null?user.getUserNo():0);
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportAlarmInstanceData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = acquisitionUnitItemManagerService.getUploadedAlarmInstanceTreeData(uploadInstanceList,deviceType,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getImportAlarmInstanceItemsData")
	public String getImportAlarmInstanceItemsData() throws IOException {
		HttpSession session=request.getSession();
		String protocolName=ParamUtils.getParameter(request, "protocolName");
		String unitName=ParamUtils.getParameter(request, "unitName");
		String instanceName=ParamUtils.getParameter(request, "instanceName");
		String alarmType=ParamUtils.getParameter(request, "alarmType");
		
		
		List<ExportAlarmInstanceData> uploadInstanceList=null;
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAlarmInstanceFile"+(user!=null?user.getUserNo():0);
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportAlarmInstanceData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = acquisitionUnitItemManagerService.getImportAlarmInstanceItemsData(uploadInstanceList,protocolName,unitName,instanceName,alarmType,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveSingelImportedAlarmInstance")
	public String saveSingelImportedAlarmInstance() throws Exception {
		HttpSession session=request.getSession();
		String protocolName=ParamUtils.getParameter(request, "protocolName");
		String unitName=ParamUtils.getParameter(request, "unitName");
		String instanceName=ParamUtils.getParameter(request, "instanceName");
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAlarmInstanceFile"+(user!=null?user.getUserNo():0);
		List<ExportAlarmInstanceData> uploadInstanceList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportAlarmInstanceData>) session.getAttribute(key);
				if(uploadInstanceList!=null && uploadInstanceList.size()>0){
					Iterator<ExportAlarmInstanceData> it = uploadInstanceList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportAlarmInstanceData instanceData=(ExportAlarmInstanceData)it.next();
						if(protocolName.equalsIgnoreCase(instanceData.getProtocol()) 
								&& unitName.equalsIgnoreCase(instanceData.getUnitName())
								&& instanceName.equalsIgnoreCase(instanceData.getName())
								){
							acquisitionUnitItemManagerService.importAlarmInstance(instanceData,user);
							it.remove();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAllImportedAlarmInstance")
	public String saveAllImportedAlarmInstance() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAlarmInstanceFile"+(user!=null?user.getUserNo():0);
		List<ExportAlarmInstanceData> uploadInstanceList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportAlarmInstanceData>) session.getAttribute(key);
				if(uploadInstanceList!=null && uploadInstanceList.size()>0){
					Iterator<ExportAlarmInstanceData> it = uploadInstanceList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportAlarmInstanceData instanceData=(ExportAlarmInstanceData)it.next();
						acquisitionUnitItemManagerService.importAlarmInstance(instanceData,user);
						it.remove();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAlarmInstanceBackupData")
	public String saveAlarmInstanceBackupData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadAlarmInstanceFile"+(user!=null?user.getUserNo():0);
		List<ExportAlarmInstanceData> uploadInstanceList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportAlarmInstanceData>) session.getAttribute(key);
				if(uploadInstanceList!=null && uploadInstanceList.size()>0){
					acquisitionUnitItemManagerService.saveAlarmInstanceBackupData(uploadInstanceList,user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/uploadImportedReportInstanceFile")
	public String uploadImportedReportInstanceFile(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request) throws Exception {
		Gson gson = new Gson();
		java.lang.reflect.Type type=null;
		StringBuffer result_json = new StringBuffer();
		boolean flag=false;
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadReportInstanceFile"+(user!=null?user.getUserNo():0);
		session.removeAttribute(key);
		
		String json = "";
		String fileContent="";
		if(files.length>0 && (!files[0].isEmpty())){
			try{
				byte[] buffer = files[0].getBytes();
				fileContent = new String(buffer, "UTF-8");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		String code="";
		try{
			Map<String, Object> result = gson.fromJson(fileContent, new TypeToken<Map<String, Object>>(){}.getType());
			code =result.containsKey("Code")?((String) result.get("Code")):"";
			if("ReportInstance".equalsIgnoreCase(code)){
				if (result.containsKey("List")) {
					List<ExportReportInstanceData> uploadInstanceList=new ArrayList<>();
					
					List<Map<String, Object>> listData = (List<Map<String, Object>>) result.get("List");
			        for (Map<String, Object> item : listData) {
			            String itemJson = gson.toJson(item);
			            ExportReportInstanceData exportReportInstanceData = gson.fromJson(itemJson, ExportReportInstanceData.class);
			            uploadInstanceList.add(exportReportInstanceData);
			        } 
			        
			        if(uploadInstanceList!=null){
						flag=true;
						session.setAttribute(key, uploadInstanceList);
					}
				}
			}
		}catch(Exception e){
			flag=false;
			e.printStackTrace();
		}
		
		result_json.append("{ \"success\":true,\"flag\":"+flag+"}");
		
		json=result_json.toString();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getUploadedReportInstanceTreeData")
	public String getUploadedReportInstanceTreeData() throws IOException {
		HttpSession session=request.getSession();
		List<ExportReportInstanceData> uploadInstanceList=null;
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		User user = (User) session.getAttribute("userLogin");
		String key="uploadReportInstanceFile"+(user!=null?user.getUserNo():0);
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportReportInstanceData>) session.getAttribute(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		String json = acquisitionUnitItemManagerService.getUploadedReportInstanceTreeData(uploadInstanceList,deviceType,user);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveSingelImportedReportInstance")
	public String saveSingelImportedReportInstance() throws Exception {
		HttpSession session=request.getSession();
		String unitName=ParamUtils.getParameter(request, "unitName");
		String instanceName=ParamUtils.getParameter(request, "instanceName");
		User user = (User) session.getAttribute("userLogin");
		String key="uploadReportInstanceFile"+(user!=null?user.getUserNo():0);
		List<ExportReportInstanceData> uploadInstanceList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportReportInstanceData>) session.getAttribute(key);
				if(uploadInstanceList!=null && uploadInstanceList.size()>0){
					Iterator<ExportReportInstanceData> it = uploadInstanceList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportReportInstanceData instanceData=(ExportReportInstanceData)it.next();
						if(unitName.equalsIgnoreCase(instanceData.getUnitName())
								&& instanceName.equalsIgnoreCase(instanceData.getName())
								){
							acquisitionUnitItemManagerService.importReportInstance(instanceData,user);
							it.remove();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveAllImportedReportInstance")
	public String saveAllImportedReportInstance() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadReportInstanceFile"+(user!=null?user.getUserNo():0);
		List<ExportReportInstanceData> uploadInstanceList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportReportInstanceData>) session.getAttribute(key);
				if(uploadInstanceList!=null && uploadInstanceList.size()>0){
					Iterator<ExportReportInstanceData> it = uploadInstanceList.iterator();
					while(it.hasNext()){
						boolean isDel=true;
						ExportReportInstanceData instanceData=(ExportReportInstanceData)it.next();
						acquisitionUnitItemManagerService.importReportInstance(instanceData,user);
						it.remove();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveReportInstanceBackupData")
	public String saveReportInstanceBackupData() throws Exception {
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String key="uploadReportInstanceFile"+(user!=null?user.getUserNo():0);
		List<ExportReportInstanceData> uploadInstanceList=null;
		try{
			if(session.getAttribute(key)!=null){
				uploadInstanceList=(List<ExportReportInstanceData>) session.getAttribute(key);
				if(uploadInstanceList!=null && uploadInstanceList.size()>0){
					acquisitionUnitItemManagerService.saveReportInstanceBackupData(uploadInstanceList,user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolExtendedFieldItems")
	public String getProtocolExtendedFieldItems() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.acquisitionUnitManagerService.getProtocolExtendedFieldItems(protocolCode,language);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
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

	public AcquisitionUnit getAcquisitionUnit() {
		return acquisitionUnit;
	}

	public void setAcquisitionUnit(AcquisitionUnit acquisitionUnit) {
		this.acquisitionUnit = acquisitionUnit;
	}


	public String getUnitName() {
		return unitName;
	}


	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public AcquisitionGroup getAcquisitionGroup() {
		return acquisitionGroup;
	}

	public void setAcquisitionGroup(AcquisitionGroup acquisitionGroup) {
		this.acquisitionGroup = acquisitionGroup;
	}
	
	public ProtocolInstance getProtocolInstance() {
		return protocolInstance;
	}
	
	public void setProtocolInstance(ProtocolInstance protocolInstance) {
		this.protocolInstance = protocolInstance;
	}
	public AlarmUnit getAlarmUnit() {
		return alarmUnit;
	}
	public void setAlarmUnit(AlarmUnit alarmUnit) {
		this.alarmUnit = alarmUnit;
	}
	public ProtocolAlarmInstance getProtocolAlarmInstance() {
		return protocolAlarmInstance;
	}
	public void setProtocolAlarmInstance(ProtocolAlarmInstance protocolAlarmInstance) {
		this.protocolAlarmInstance = protocolAlarmInstance;
	}
	public DisplayUnit getDisplayUnit() {
		return displayUnit;
	}
	public void setDisplayUnit(DisplayUnit displayUnit) {
		this.displayUnit = displayUnit;
	}
}
