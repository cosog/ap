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
import com.cosog.model.calculate.RPCProductionData;
import com.cosog.model.drive.ExportProtocolConfig;
import com.cosog.model.drive.ModbusDriverSaveData;
import com.cosog.model.drive.ModbusProtocolAlarmUnitSaveData;
import com.cosog.model.drive.ModbusProtocolAlarmInstanceSaveData;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.drive.ModbusProtocolConfig.Items;
import com.cosog.model.drive.ModbusProtocolConfig.ItemsMeaning;
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

	/**<p>描述：采集类型数据显示方法</p>
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/doAcquisitionUnitShow")
	public String doAcquisitionUnitShow() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		unitName = ParamUtils.getParameter(request, "unitName");
		int intPage = Integer.parseInt((page == null || page == "0") ? "1": page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "10": limit);
		int offset = (intPage - 1) * pageSize;
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("protocolName", protocolName);
		map.put("unitName", unitName);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);
		String json = this.acquisitionUnitManagerService.getAcquisitionUnitList(map,pager);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/doModbusProtocolAdd")
	public String doModbusProtocolAdd(@ModelAttribute ProtocolModel protocolModel) throws IOException {
		String result = "";
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		Gson gson = new Gson();
//		String fileName="modbus.json";
//		String path=stringManagerUtils.getFilePath(fileName,"protocol/");
		PrintWriter out = response.getWriter();
		try {
//			String name = ParamUtils.getParameter(request, "modbusProtocol.name");
//			String deviceType = ParamUtils.getParameter(request, "modbusProtocol.deviceType");
//			String sort = ParamUtils.getParameter(request, "modbusProtocol.sort");
			
			protocolModel.setName(protocolModel.getName().replaceAll(" ", ""));
			this.protocolModelManagerService.doProtocolAdd(protocolModel);
			
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			if(user!=null){
				this.service.saveSystemLog(user,2,"添加协议:"+protocolModel.getName());
			}
			
			MemoryDataManagerTask.loadProtocolConfig(protocolModel.getName());
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
			if(user!=null){
				this.service.saveSystemLog(user,2,"添加采集组:"+acquisitionGroup.getGroupName());
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
				acquisitionUnitGroup.setGroupId(StringManagerUtils.stringTransferInteger(groupId));
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
			if(user!=null){
				this.service.saveSystemLog(user,2,"编辑采集组:"+acquisitionGroup.getGroupName());
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
			if(user!=null){
				this.service.saveSystemLog(user,2,"删除采集组");
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
			this.acquisitionUnitManagerService.doAcquisitionUnitAdd(acquisitionUnit);
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			if(user!=null){
				this.service.saveSystemLog(user,2,"添加采集单元:"+acquisitionUnit.getUnitName());
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
			if(user!=null){
				this.service.saveSystemLog(user,2,"编辑采集单元:"+acquisitionUnit.getUnitName());
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
			this.acquisitionUnitManagerService.doAcquisitionUnitBulkDelete(ids);
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			if(user!=null){
				this.service.saveSystemLog(user,2,"删除采集单元");
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
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("groupName", groupName);
		map.put("protocolName", protocolName);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);
		String json = this.acquisitionUnitManagerService.doAcquisitionGroupShow(map,pager);
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
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		try {
			String params = ParamUtils.getParameter(request, "params");
			String matrixCodes = ParamUtils.getParameter(request, "matrixCodes");
			String groupCode = ParamUtils.getParameter(request, "groupCode");
			String protocolName = ParamUtils.getParameter(request, "protocol");
			log.debug("grantAcquisitionItemsPermission params==" + params);
			String paramsArr[] = StringManagerUtils.split(params, ",");
			String groupId="";
			String groupName="";
			String groupIdSql="select t.id,t.group_name from tbl_acq_group_conf t where t.group_code='"+groupCode+"' ";
			List list = this.service.findCallSql(groupIdSql);
			if(list.size()>0){
				Object[] obj=(Object[]) list.get(0);
				groupId=obj[0]+"";
				groupName=obj[1]+"";
			}
			
			ModbusProtocolConfig.Protocol protocol=null;
			for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
				if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
					protocol=modbusProtocolConfig.getProtocol().get(j);
					break;
				}
			}
			
			if (paramsArr.length > 0 && StringManagerUtils.isNotNull(groupId) && protocol!=null) {
				this.acquisitionUnitItemManagerService.deleteCurrentAcquisitionGroupOwnItems(groupId);
				if (matrixCodes != "" || matrixCodes != null) {
					String module_matrix[] = matrixCodes.split("\\|");
					List<String> itemsList=new ArrayList<String>();
					for (int i = 0; i < module_matrix.length; i++) {
						String module_[] = module_matrix[i].split("\\:");
						String itemName=module_[0];
						int itemAddr=StringManagerUtils.stringTransferInteger(module_[1]);
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
						log.debug("groupCode==" + groupCode);
						acquisitionGroupItem.setItemName(itemName);
//						acquisitionGroupItem.setBitIndex(bitIndex>=0?bitIndex:null);
						acquisitionGroupItem.setMatrix(module_[4]);
						this.acquisitionUnitItemManagerService.grantAcquisitionItemsPermission(acquisitionGroupItem);
					}
				}
//				HttpSession session=request.getSession();
//				User user = (User) session.getAttribute("userLogin");
//				if(user!=null){
//					this.service.saveSystemLog(user,2,"为采控组安排采控项,采集组名称:"+groupName);
//				}
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
			
//			EquipmentDriverServerTask.initInstanceConfigByAcqGroupId(groupId,"update");
//			EquipmentDriverServerTask.initPumpDriverAcquisitionInfoConfigByAcqGroupId(groupId,"update");
//			this.acquisitionUnitManagerService.doAcquisitionGroupOwnItemChange(groupId);
//			MemoryDataManagerTask.loadAcqInstanceOwnItemByGroupId(groupId,"update");
//			MemoryDataManagerTask.loadDisplayInstanceOwnItemByAcqGroupId(groupId,"update");
			
			
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
				if (matrixCodes != "" || matrixCodes != null) {
					String module_matrix[] = matrixCodes.split("\\|");
					for (int i = 0; i < module_matrix.length; i++) {
						String module_[] = module_matrix[i].split("\\:");
						acquisitionUnitGroup = new AcquisitionUnitGroup();
						acquisitionUnitGroup.setUnitId(Integer.parseInt(unitId));
						log.debug("unitId==" + unitId);
						acquisitionUnitGroup.setGroupId(StringManagerUtils.stringTransferInteger(module_[0]));
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
		int dataSaveMode=1;
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
		try {
			String matrixCodes = ParamUtils.getParameter(request, "matrixCodes");
			String unitId = ParamUtils.getParameter(request, "unitId");
			String protocolName = ParamUtils.getParameter(request, "protocol");
			String itemType = ParamUtils.getParameter(request, "itemType");
			
			ModbusProtocolConfig.Protocol protocol=null;
			for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
				if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
					protocol=modbusProtocolConfig.getProtocol().get(j);
					break;
				}
			}
			
			if (StringManagerUtils.isNotNull(unitId) && protocol!=null) {
				this.displayUnitItemManagerService.deleteCurrentDisplayUnitOwnItems(unitId,itemType);
				
				if (StringManagerUtils.isNotNull(matrixCodes)) {
					String module_matrix[] = matrixCodes.split("\\|");
					for (int i = 0; i < module_matrix.length; i++) {
						String module_[] = module_matrix[i].split("##");
						String itemName=module_[0];
						int itemAddr=StringManagerUtils.stringTransferInteger(module_[6]);
						String resolutionMode=module_[5];
						String bitIndexStr=module_[7];
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
						displayUnitItem.setSort(StringManagerUtils.isNumber(module_[1])?StringManagerUtils.stringTransferInteger(module_[1]):null);
						displayUnitItem.setBitIndex(bitIndex>=0?bitIndex:null);
						displayUnitItem.setShowLevel(StringManagerUtils.isNumber(module_[2])?StringManagerUtils.stringTransferInteger(module_[2]):null);
						displayUnitItem.setRealtimeCurveConf(!"开关量".equalsIgnoreCase(resolutionMode)?module_[3]:"");
						displayUnitItem.setHistoryCurveConf(!"开关量".equalsIgnoreCase(resolutionMode)?module_[4]:"");
						displayUnitItem.setMatrix(module_[8]);
						this.displayUnitItemManagerService.grantDisplayItemsPermission(displayUnitItem);
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
		int dataSaveMode=1;
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		Map<String, Object> dataModelMap=DataModelMap.getMapObject();
		Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=(Map<String, DataMapping>) dataModelMap.get("ProtocolMappingColumnByTitle");
		try {
			String params = ParamUtils.getParameter(request, "params");
			String matrixCodes = ParamUtils.getParameter(request, "matrixCodes");
			String unitId = ParamUtils.getParameter(request, "unitId");
			String protocolName = ParamUtils.getParameter(request, "protocol");
			String itemType = ParamUtils.getParameter(request, "itemType");
			log.debug("grantAcquisitionItemsPermission params==" + params);
			String paramsArr[] = StringManagerUtils.split(params, ",");
			
			ModbusProtocolConfig.Protocol protocol=null;
			for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
				if(protocolName.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
					protocol=modbusProtocolConfig.getProtocol().get(j);
					break;
				}
			}
			
			if (StringManagerUtils.isNotNull(unitId) && protocol!=null) {
				this.displayUnitItemManagerService.deleteCurrentDisplayUnitOwnItems(unitId,itemType);
				if (StringManagerUtils.isNotNull(matrixCodes)) {
					String module_matrix[] = matrixCodes.split("\\|");
					List<String> itemsList=new ArrayList<String>();
					for (int i = 0; i < module_matrix.length; i++) {
						String module_[] = module_matrix[i].split("\\:");
						String itemName=module_[0];
						int itemAddr=StringManagerUtils.stringTransferInteger(module_[4]);
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
						displayUnitItem.setSort(StringManagerUtils.isNumber(module_[1])?StringManagerUtils.stringTransferInteger(module_[1]):null);
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
//			MemoryDataManagerTask.loadDisplayInstanceOwnItemByUnitId(unitId,"update");
			
			
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
					displayUnitItem.setSort(StringManagerUtils.isNumber(module_[2])?StringManagerUtils.stringTransferInteger(module_[2]):null);
					displayUnitItem.setShowLevel(StringManagerUtils.isNumber(module_[3])?StringManagerUtils.stringTransferInteger(module_[3]):null);
					displayUnitItem.setRealtimeCurveConf(module_[4]);
					displayUnitItem.setHistoryCurveConf(module_[5]);
					displayUnitItem.setMatrix(module_[6]);
					this.displayUnitItemManagerService.grantDisplayItemsPermission(displayUnitItem);
				}
				
			}
//			MemoryDataManagerTask.loadDisplayInstanceOwnItemByUnitId(unitId,"update");
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
					displayUnitItem.setSort(StringManagerUtils.isNumber(module_[2])?StringManagerUtils.stringTransferInteger(module_[2]):null);
					displayUnitItem.setShowLevel(StringManagerUtils.isNumber(module_[3])?StringManagerUtils.stringTransferInteger(module_[3]):null);
					displayUnitItem.setRealtimeCurveConf(module_[4]);
					displayUnitItem.setHistoryCurveConf(module_[5]);
					displayUnitItem.setMatrix(module_[6]);
					this.displayUnitItemManagerService.grantDisplayItemsPermission(displayUnitItem);
				}
				
			}
//			MemoryDataManagerTask.loadDisplayInstanceOwnItemByUnitId(unitId,"update");
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
					key="rpcTimingTotalCalItemList";
				}else if("2".equalsIgnoreCase(calculateType)){
					key="pcpTimingTotalCalItemList";
				}
			}else{
				key="acqTotalCalItemList";
				if("1".equalsIgnoreCase(calculateType)){
					key="rpcTotalCalItemList";
				}else if("2".equalsIgnoreCase(calculateType)){
					key="pcpTotalCalItemList";
				}
			}
			
			Jedis jedis=null;
			List<byte[]> calItemSet=null;
			try{
				jedis = RedisUtil.jedisPool.getResource();
				if(!jedis.exists(key.getBytes())){
					if("1".equalsIgnoreCase(calculateType)){
						MemoryDataManagerTask.loadRPCTotalCalculateItem();
					}else if("2".equalsIgnoreCase(calculateType)){
						MemoryDataManagerTask.loadPCPTotalCalculateItem();
					}else{
						MemoryDataManagerTask.loadAcqTotalCalculateItem();
					}
				}
				calItemSet= jedis.zrange(key.getBytes(), 0, -1);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(jedis!=null){
					jedis.close();
				}
			}
			
			for(byte[] calItemByteArr:calItemSet){
				CalItem calItem=(CalItem) SerializeObjectUnils.unserizlize(calItemByteArr);
				calItemName.add(calItem.getName());
			}
			
			type = new TypeToken<TotalCalItemsToReportUnitSaveData>() {}.getType();
			TotalCalItemsToReportUnitSaveData totalCalItemsToReportUnitSaveData=gson.fromJson(saveData, type);
			
			this.displayUnitItemManagerService.deleteCurrentReportUnitOwnItems(unitId,reportType);
			
			
			if (totalCalItemsToReportUnitSaveData!=null && totalCalItemsToReportUnitSaveData.getItemList()!=null && totalCalItemsToReportUnitSaveData.getItemList().size()>0) {
				
				for (int i = 0; i < totalCalItemsToReportUnitSaveData.getItemList().size(); i++) {
					boolean save=true;
					if("计算".equalsIgnoreCase(totalCalItemsToReportUnitSaveData.getItemList().get(i).getDataSource())){
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
						
						reportUnitItem.setTotalType( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getTotalType()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getTotalType()) )?StringManagerUtils.stringTransferInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getTotalType()):null);
						
						reportUnitItem.setSort( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemSort()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemSort()) )?StringManagerUtils.stringTransferInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemSort()):null);
						reportUnitItem.setShowLevel( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemShowLevel()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemShowLevel()) )?StringManagerUtils.stringTransferInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemShowLevel()):null);
						
						reportUnitItem.setPrec( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemPrec()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemPrec()) )?StringManagerUtils.stringTransferInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getItemPrec()):null);
						
						reportUnitItem.setSumSign((totalCalItemsToReportUnitSaveData.getItemList().get(i).getSumSign()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getSumSign()) )?StringManagerUtils.stringTransferInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getSumSign()):null);
						reportUnitItem.setAverageSign( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getAverageSign()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getAverageSign()) )?StringManagerUtils.stringTransferInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getAverageSign()):null);
						
						reportUnitItem.setReportCurveConf(totalCalItemsToReportUnitSaveData.getItemList().get(i).getReportCurveConf());
						
						reportUnitItem.setCurveStatType( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getCurveStatType()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getCurveStatType()) )?StringManagerUtils.stringTransferInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getCurveStatType()):null);
						
						reportUnitItem.setDataType( (totalCalItemsToReportUnitSaveData.getItemList().get(i).getDataType()!=null && StringManagerUtils.isNumber(totalCalItemsToReportUnitSaveData.getItemList().get(i).getDataType()) )?StringManagerUtils.stringTransferInteger(totalCalItemsToReportUnitSaveData.getItemList().get(i).getDataType()):null);
						
						reportUnitItem.setDataSource(totalCalItemsToReportUnitSaveData.getItemList().get(i).getDataSource());
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
	
	@RequestMapping("/getProtocolEnumOrSwitchItemsConfigData")
	public String getProtocolEnumOrSwitchItemsConfigData() throws Exception {
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String resolutionMode = ParamUtils.getParameter(request, "resolutionMode");
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolEnumOrSwitchItemsConfigData(protocolCode,resolutionMode);
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
		json = acquisitionUnitItemManagerService.getProtocolItemMeaningConfigData(protocolCode,itemAddr);
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
		json = acquisitionUnitItemManagerService.getProtocolItemsConfigData(protocolName,classes,code);
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
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		String type = ParamUtils.getParameter(request, "type");
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolAcqUnitItemsConfigData(protocolName,classes,code,type);
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
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		String json = "";
		json = acquisitionUnitItemManagerService.getModbusProtocolNumAlarmItemsConfigData(protocolName,classes,code);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getModbusProtocolCalNumAlarmItemsConfigData(deviceType,classes,code);
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
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String classes = ParamUtils.getParameter(request, "classes");
		String unitCode = ParamUtils.getParameter(request, "unitCode");
		String itemAddr = ParamUtils.getParameter(request, "itemAddr");
		String itemResolutionMode = ParamUtils.getParameter(request, "itemResolutionMode");
		
		String json = "";
		if("1".equals(itemResolutionMode)){
			json = acquisitionUnitItemManagerService.getModbusProtocolEnumAlarmItemsConfigData(protocolName,classes,unitCode,itemAddr,itemResolutionMode);
		}else if("0".equals(itemResolutionMode)){
			json = acquisitionUnitItemManagerService.getModbusProtocolSwitchAlarmItemsConfigData(protocolName,classes,unitCode,itemAddr,itemResolutionMode);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getModbusProtocolFESDiagramConditionsAlarmItemsConfigData(protocolName,classes,code);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getModbusProtocolCommStatusAlarmItemsConfigData(protocolName,classes,code);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getModbusProtocolRunStatusAlarmItemsConfigData(protocolName,classes,code);
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
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		String unitId = ParamUtils.getParameter(request, "unitId");
		String acqUnitId = ParamUtils.getParameter(request, "acqUnitId");
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolDisplayUnitAcqItemsConfigData(protocolName,classes,code,unitId,acqUnitId);
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
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		String unitId = ParamUtils.getParameter(request, "unitId");
		String acqUnitId = ParamUtils.getParameter(request, "acqUnitId");
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolDisplayUnitCtrlItemsConfigData(protocolName,classes,code,unitId,acqUnitId);
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
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String classes = ParamUtils.getParameter(request, "classes");
		String unitId = ParamUtils.getParameter(request, "unitId");
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolDisplayUnitCalItemsConfigData(deviceType,classes,unitId);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolDisplayUnitInputItemsConfigData(deviceType,classes,unitId);
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
		String unitId = ParamUtils.getParameter(request, "unitId");
		String classes = ParamUtils.getParameter(request, "classes");
		String json = "";
		json = acquisitionUnitItemManagerService.getReportUnitTotalCalItemsConfigData(calculateType,reportType,unitId,classes);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getReportUnitTimingTotalCalItemsConfigData")
	public String getReportUnitTimingTotalCalItemsConfigData() throws Exception {
		String calculateType = ParamUtils.getParameter(request, "calculateType");
		String reportType = ParamUtils.getParameter(request, "reportType");
		String unitId = ParamUtils.getParameter(request, "unitId");
		String classes = ParamUtils.getParameter(request, "classes");
		String json = "";
		json = acquisitionUnitItemManagerService.getReportUnitTimingTotalCalItemsConfigData(calculateType,reportType,unitId,classes);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getReportInstanceTotalCalItemsConfigData(calculateType,unitId,reportType);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getReportInstanceTimingTotalCalItemsConfigData(calculateType,unitId,reportType);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolInstanceItemsConfigData")
	public String getProtocolInstanceItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String classes = ParamUtils.getParameter(request, "classes");
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolInstanceItemsConfigData(id,classes);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolDisplayInstanceAcqItemsConfigData")
	public String getProtocolDisplayInstanceAcqItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String classes = ParamUtils.getParameter(request, "classes");
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolDisplayInstanceAcqItemsConfigData(id,classes);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolDisplayInstanceAcqItemsConfigData(id,type);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolDisplayInstanceCtrlItemsConfigData")
	public String getProtocolDisplayInstanceCtrlItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String classes = ParamUtils.getParameter(request, "classes");
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolDisplayInstanceCtrlItemsConfigData(id,classes);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolDisplayInstanceCtrlItemsConfigData(id,type);
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
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolDisplayInstanceCalItemsConfigData(id,classes,deviceType);
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
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolDisplayInstanceInputItemsConfigData(id,classes,deviceType);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolDisplayInstanceCalItemsConfigData(id,type);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolAlarmInstanceNumItemsConfigData(id,classes,resolutionMode);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolAlarmContentNumItemsConfigData(id,type);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolAlarmInstanceCalNumItemsConfigData(id,classes,resolutionMode,deviceType);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolAlarmContentCalNumItemsConfigData(id,type);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolAlarmInstanceSwitchItemsConfigData")
	public String getProtocolAlarmInstanceSwitchItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String resolutionMode = ParamUtils.getParameter(request, "resolutionMode");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolAlarmInstanceSwitchItemsConfigData(id,classes,resolutionMode);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolAlarmContentSwitchItemsConfigData(id,type);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getProtocolAlarmInstanceEnumItemsConfigData")
	public String getProtocolAlarmInstanceEnumItemsConfigData() throws Exception {
		String id = ParamUtils.getParameter(request, "id");
		String resolutionMode = ParamUtils.getParameter(request, "resolutionMode");
		String classes = ParamUtils.getParameter(request, "classes");
		String code = ParamUtils.getParameter(request, "code");
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolAlarmInstanceEnumItemsConfigData(id,classes,resolutionMode);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolAlarmContentEnumItemsConfigData(id,type);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolAlarmInstanceFESDiagramResultItemsConfigData(id,classes,resolutionMode);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolAlarmContentFESDiagramResultItemsConfigData(id,type);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolAlarmInstanceRunStatusItemsConfigData(id,classes,resolutionMode);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolAlarmContentRunStatusItemsConfigData(id,type);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getProtocolAlarmInstanceCommStatusItemsConfigData(id,classes,resolutionMode);
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
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolAlarmContentCommStatusItemsConfigData(id,type);
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
		String deviceTypeIds = ParamUtils.getParameter(request, "deviceTypeIds");
		String json = acquisitionUnitItemManagerService.getAcquisitionUnitTreeData(deviceTypeIds);
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
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String json = acquisitionUnitItemManagerService.exportAcqUnitTreeData(deviceType,protocolName,protocolCode);
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
		String deviceTypeIds = ParamUtils.getParameter(request, "deviceTypeIds");
		String json = acquisitionUnitItemManagerService.getDisplayUnitTreeData(deviceTypeIds);
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
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String json = acquisitionUnitItemManagerService.exportDisplayUnitTreeData(deviceType,protocolName,protocolCode);
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
		String json = acquisitionUnitItemManagerService.modbusProtocolAddrMappingTreeData(deviceTypeIds);
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
		String json = acquisitionUnitItemManagerService.modbusProtocolAndAcqUnitTreeData(deviceTypeIds);
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
		String json = acquisitionUnitItemManagerService.modbusProtocolAndDisplayUnitTreeData(deviceTypeIds);
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
		String json = acquisitionUnitItemManagerService.repoerUnitTreeData();
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
		String json = acquisitionUnitItemManagerService.modbusProtocolAndAlarmUnitTreeData(deviceTypeIds);
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
		String deviceTypeIds=ParamUtils.getParameter(request, "deviceTypeIds");
		String json = acquisitionUnitItemManagerService.modbusProtocolAlarmUnitTreeData(deviceTypeIds);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportProtocolAlarmUnitTreeData")
	public String exportProtocolAlarmUnitTreeData() throws IOException {
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String json = acquisitionUnitItemManagerService.exportProtocolAlarmUnitTreeData(deviceType,protocolName,protocolCode);
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
		String deviceTypeIds = ParamUtils.getParameter(request, "deviceTypeIds");
		String json = acquisitionUnitItemManagerService.getModbusProtocolInstanceConfigTreeData(deviceTypeIds);
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
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String json = acquisitionUnitItemManagerService.exportProtocolAcqInstanceTreeData(deviceType,protocolName,protocolCode);
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
		String json = acquisitionUnitItemManagerService.getAcqUnitList();
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
		String deviceTypeIds = ParamUtils.getParameter(request, "deviceTypeIds");
		String json = acquisitionUnitItemManagerService.getModbusDisplayProtocolInstanceConfigTreeData(deviceTypeIds);
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
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String json = acquisitionUnitItemManagerService.exportProtocolDisplayInstanceTreeData(deviceType,protocolName,protocolCode);
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
		String json = protocolReportInstanceManagerService.modbusReportInstanceConfigTreeData();
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
		String deviceTypeIds = ParamUtils.getParameter(request, "deviceTypeIds");
		String json = acquisitionUnitItemManagerService.getModbusAlarmProtocolInstanceConfigTreeData(deviceTypeIds);
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
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		String protocolCode = ParamUtils.getParameter(request, "protocolCode");
		String json = acquisitionUnitItemManagerService.exportProtocolAlarmInstanceTreeData(deviceType,protocolName,protocolCode);
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
		json = acquisitionUnitItemManagerService.getSMSInstanceList(name,pager);
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
		String json=acquisitionUnitItemManagerService.getModbusProtoclCombList(deviceTypeIds);
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
		String json=acquisitionUnitItemManagerService.getAcquisitionUnitCombList(protocol,deviceTypeIds);
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
		data = StringManagerUtils.superscriptToNormal(data);
		java.lang.reflect.Type type = new TypeToken<ModbusDriverSaveData>() {}.getType();
		ModbusDriverSaveData modbusDriverSaveData=gson.fromJson(data, type);
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		if(modbusDriverSaveData!=null){
			modbusDriverSaveData.dataFiltering();
			
			ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
			
			//删除协议
			for(int i=0;modbusDriverSaveData.getDelidslist()!=null&&i<modbusDriverSaveData.getDelidslist().size();i++){
				for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
					if(modbusDriverSaveData.getDelidslist().get(i).equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
						DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
						dataSynchronizationThread.setSign(002);
						dataSynchronizationThread.setParam1(modbusProtocolConfig.getProtocol().get(j).getName());
						dataSynchronizationThread.setMethod("delete");
						dataSynchronizationThread.setAcquisitionUnitManagerService(acquisitionUnitManagerService);
						executor.execute(dataSynchronizationThread);
						
						modbusProtocolConfig.getProtocol().remove(j);
						String delSql="delete from TBL_PROTOCOL t where t.name='"+modbusDriverSaveData.getDelidslist().get(i)+"'";
						acquisitionUnitManagerService.getBaseDao().updateOrDeleteBySql(delSql);
						
						if(user!=null){
							this.service.saveSystemLog(user,2,"删除协议:"+modbusDriverSaveData.getDelidslist().get(i));
						}
						break;
					}
				}
			}
			
			if(StringManagerUtils.isNotNull(modbusDriverSaveData.getProtocolName())){
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					if(modbusDriverSaveData.getProtocolCode().equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getCode())){
						String oldName=modbusProtocolConfig.getProtocol().get(i).getName();
						modbusProtocolConfig.getProtocol().get(i).setName(modbusDriverSaveData.getProtocolName());
						modbusProtocolConfig.getProtocol().get(i).setSort(modbusDriverSaveData.getSort());
						
						List<String> delItemList=new ArrayList<String>();
						
						for(int j=0;j<modbusDriverSaveData.getDataConfig().size();j++){
							boolean isAddItem=true;
							String acqMode="passive";
							if("主动上传".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getAcqMode())){
								acqMode="active";
							}else if("被动响应".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getAcqMode())){
								acqMode="passive";
							}
							String RWType="r";
							if("读写".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getRWType())){
								RWType="rw";
							}else if("只读".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getRWType())){
								RWType="r";
							}else if("只写".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getRWType())){
								RWType="w";
							}
							int resolutionMode=2;
							if("开关量".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getResolutionMode())){
								resolutionMode=0;
							}else if("枚举量".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getResolutionMode())){
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
									if(!RWType.equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getRWType())){
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
						//如果协议名称改变，更新数据库
						if(!oldName.equals(modbusDriverSaveData.getProtocolName())){
							EquipmentDriverServerTask.initProtocolConfig(oldName,"delete");
							String unitSql="update tbl_acq_unit_conf t set t.protocol='"+modbusDriverSaveData.getProtocolName()+"' where t.protocol='"+oldName+"'";
							String groupSql="update tbl_acq_group_conf t set t.protocol='"+modbusDriverSaveData.getProtocolName()+"' where t.protocol='"+oldName+"'";
							String alatmUnitSql="update tbl_alarm_unit_conf t set t.protocol='"+modbusDriverSaveData.getProtocolName()+"' where t.protocol='"+oldName+"'";
							String displayUnitSql="update tbl_display_unit_conf t set t.protocol='"+modbusDriverSaveData.getProtocolName()+"' where t.protocol='"+oldName+"'";
							service.updateSql(unitSql);
							service.updateSql(groupSql);
							service.updateSql(alatmUnitSql);
							service.updateSql(displayUnitSql);
						}
						if(delItemList.size()>0){
							String delSql="delete from tbl_acq_item2group_conf t5 "
									+ " where t5.id in "
									+ "(select t4.id from tbl_acq_unit_conf t,tbl_acq_group2unit_conf t2,tbl_acq_group_conf t3,tbl_acq_item2group_conf t4 "
									+ " where t.id=t2.unitid and t2.groupid=t3.id and t3.id=t4.groupid "
									+ "and t.protocol='"+modbusDriverSaveData.getProtocolName()+"' "
									+ " and t4.itemname in ("+StringManagerUtils.joinStringArr2(delItemList, ",")+"))";
							String delDisplayItemSql="delete from tbl_display_items2unit_conf t "
									+ "where t.type<>1 "
									+ "and t.unitid in ( select t2.id from tbl_display_unit_conf t2 ,tbl_acq_unit_conf t3 where t2.acqunitid=t3.id and t3.protocol='"+modbusDriverSaveData.getProtocolName()+"'  )"
									+ " and t.itemname in ("+StringManagerUtils.joinStringArr2(delItemList, ",")+")";
							service.updateSql(delSql);
							service.updateSql(delDisplayItemSql);
						}
						Collections.sort(modbusProtocolConfig.getProtocol().get(i).getItems());
						String updateSql="update TBL_PROTOCOL t set t.name='"+modbusProtocolConfig.getProtocol().get(i).getName()+"',"
								+ " t.sort="+modbusProtocolConfig.getProtocol().get(i).getSort()
								+" where t.code='"+modbusProtocolConfig.getProtocol().get(i).getCode()+"'";
						service.updateSql(updateSql);
						String updateProtocolItemsClobSql="update TBL_PROTOCOL t set t.items=? where t.code='"+modbusProtocolConfig.getProtocol().get(i).getCode()+"'";
						List<String> clobCont=new ArrayList<String>();
						clobCont.add(gson.toJson(modbusProtocolConfig.getProtocol().get(i).getItems()));
						service.getBaseDao().executeSqlUpdateClob(updateProtocolItemsClobSql,clobCont);
						
						if(user!=null){
							this.service.saveSystemLog(user,2,"修改协议:"+modbusProtocolConfig.getProtocol().get(i).getName());
						}
						break;
					}
				}
				Collections.sort(modbusProtocolConfig.getProtocol());
			}
			Jedis jedis=null;
			try{
				jedis = RedisUtil.jedisPool.getResource();
				jedis.set("modbusProtocolConfig".getBytes(), SerializeObjectUnils.serialize(modbusProtocolConfig));
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(jedis!=null){
					jedis.close();
				}
			}
			if(StringManagerUtils.isNotNull(modbusDriverSaveData.getProtocolName())){
				DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
				dataSynchronizationThread.setSign(003);
				dataSynchronizationThread.setParam1(modbusDriverSaveData.getProtocolName());
				dataSynchronizationThread.setMethod("update");
				executor.execute(dataSynchronizationThread);
				
//				EquipmentDriverServerTask.initProtocolConfig(modbusDriverSaveData.getProtocolName(),"update");
//				EquipmentDriverServerTask.initInstanceConfigByProtocolName(modbusDriverSaveData.getProtocolName(),"update");
			}
		}
		json ="{success:true}";
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
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String protocol = ParamUtils.getParameter(request, "protocol");
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		Gson gson = new Gson();
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
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
						this.service.saveSystemLog(user,2,"删除采控单元");
					}
				}
			}
			if(acquisitionUnitHandsontableChangeData.getUpdatelist()!=null){
				for(int i=0;i<acquisitionUnitHandsontableChangeData.getUpdatelist().size();i++){
					AcquisitionUnit acquisitionUnit=new AcquisitionUnit();
					acquisitionUnit.setId(StringManagerUtils.stringToInteger(acquisitionUnitHandsontableChangeData.getUpdatelist().get(i).getId()));
					acquisitionUnit.setUnitCode(acquisitionUnitHandsontableChangeData.getUpdatelist().get(i).getUnitCode());
					acquisitionUnit.setUnitName(acquisitionUnitHandsontableChangeData.getUpdatelist().get(i).getUnitName());
					acquisitionUnit.setRemark(acquisitionUnitHandsontableChangeData.getUpdatelist().get(i).getRemark());
					acquisitionUnit.setProtocol(protocol);
					this.acquisitionUnitManagerService.doAcquisitionUnitEdit(acquisitionUnit);
					if(user!=null){
						this.service.saveSystemLog(user,2,"编辑采控单元:"+acquisitionUnitHandsontableChangeData.getUpdatelist().get(i).getUnitName());
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
						this.service.saveSystemLog(user,2,"编辑采控单元:"+acquisitionUnitHandsontableChangeData.getInsertlist().get(i).getUnitName());
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
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String protocol = ParamUtils.getParameter(request, "protocol");
		String unitId = ParamUtils.getParameter(request, "unitId");
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<AcquisitionGroupHandsontableChangeData>() {}.getType();
		AcquisitionGroupHandsontableChangeData acquisitionGroupHandsontableChangeData=gson.fromJson(data, type);
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
					
//					EquipmentDriverServerTask.initInstanceConfigByAcqUnitId(unitId, "update");
//					MemoryDataManagerTask.loadAcqInstanceOwnItemByUnitId(unitId,"update");
//					MemoryDataManagerTask.loadDisplayInstanceOwnItemByUnitId(unitId,"update");
					
					if(user!=null){
						this.service.saveSystemLog(user,2,"删除采控组");
					}
				}
			}
			if(acquisitionGroupHandsontableChangeData.getUpdatelist()!=null){
				for(int i=0;i<acquisitionGroupHandsontableChangeData.getUpdatelist().size();i++){
					AcquisitionGroup acquisitionGroup=new AcquisitionGroup();
					acquisitionGroup.setId(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getId()));
					acquisitionGroup.setGroupCode(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getGroupCode());
					acquisitionGroup.setGroupName(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getGroupName());
					acquisitionGroup.setType("采集组".equalsIgnoreCase(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getTypeName())?0:1);
					acquisitionGroup.setGroupTimingInterval(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getGroupTimingInterval()));
					acquisitionGroup.setGroupSavingInterval(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getGroupSavingInterval()));
					acquisitionGroup.setRemark(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getRemark());
					acquisitionGroup.setProtocol(protocol);
					this.acquisitionUnitManagerService.doAcquisitionGroupEdit(acquisitionGroup);
//					EquipmentDriverServerTask.initInstanceConfigByAcqGroupId(acquisitionGroup.getId()+"", "update");
					
					if(user!=null){
						this.service.saveSystemLog(user,2,"编辑采控组:"+acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getGroupName());
					}
				}
			}
			
			if(acquisitionGroupHandsontableChangeData.getInsertlist()!=null){
				for(int i=0;i<acquisitionGroupHandsontableChangeData.getInsertlist().size();i++){
					AcquisitionGroup acquisitionGroup=new AcquisitionGroup();
					acquisitionGroup.setId(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getId()));
					acquisitionGroup.setGroupCode(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getGroupCode());
					acquisitionGroup.setGroupName(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getGroupName());
					acquisitionGroup.setType("采集组".equalsIgnoreCase(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getTypeName())?0:1);
					acquisitionGroup.setGroupTimingInterval(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getGroupTimingInterval()));
					acquisitionGroup.setGroupSavingInterval(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getGroupSavingInterval()));
					acquisitionGroup.setRemark(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getRemark());
					acquisitionGroup.setProtocol(protocol);
					this.acquisitionUnitManagerService.doAcquisitionGroupAdd(acquisitionGroup);
					if(user!=null){
						this.service.saveSystemLog(user,2,"编辑采控组:"+acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getGroupName());
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
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String protocol = ParamUtils.getParameter(request, "protocol");
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
					
//					MemoryDataManagerTask.loadDisplayInstanceOwnItemByUnitId(displayUnitHandsontableChangeData.getDelidslist().get(i), "delete");
//					this.displayUnitManagerService.doDisplayUnitBulkDelete(displayUnitHandsontableChangeData.getDelidslist().get(i),deviceType);
					
					if(user!=null){
						this.service.saveSystemLog(user,2,"删除显示单元");
					}
				}
			}
			if(displayUnitHandsontableChangeData.getUpdatelist()!=null){
				for(int i=0;i<displayUnitHandsontableChangeData.getUpdatelist().size();i++){
					DisplayUnit displayUnit=new DisplayUnit();
					displayUnit.setId(StringManagerUtils.stringToInteger(displayUnitHandsontableChangeData.getUpdatelist().get(i).getId()));
					displayUnit.setUnitCode(displayUnitHandsontableChangeData.getUpdatelist().get(i).getUnitCode());
					displayUnit.setUnitName(displayUnitHandsontableChangeData.getUpdatelist().get(i).getUnitName());
					displayUnit.setRemark(displayUnitHandsontableChangeData.getUpdatelist().get(i).getRemark());
					displayUnit.setProtocol(protocol);
					displayUnit.setAcqUnitId(StringManagerUtils.stringToInteger(displayUnitHandsontableChangeData.getUpdatelist().get(i).getAcqUnitId()));
					this.displayUnitManagerService.doDisplayUnitEdit(displayUnit);
					
					if(user!=null){
						this.service.saveSystemLog(user,2,"编辑显示单元:"+displayUnitHandsontableChangeData.getUpdatelist().get(i).getUnitName());
					}
				}
			}
			
			if(displayUnitHandsontableChangeData.getInsertlist()!=null){
				for(int i=0;i<displayUnitHandsontableChangeData.getInsertlist().size();i++){
					DisplayUnit displayUnit=new DisplayUnit();
					displayUnit.setProtocol(protocol);
					displayUnit.setUnitCode(displayUnitHandsontableChangeData.getInsertlist().get(i).getUnitCode());
					displayUnit.setUnitName(displayUnitHandsontableChangeData.getInsertlist().get(i).getUnitName());
					displayUnit.setRemark(displayUnitHandsontableChangeData.getInsertlist().get(i).getRemark());
					displayUnit.setProtocol(protocol);
					displayUnit.setAcqUnitId(StringManagerUtils.stringToInteger(displayUnitHandsontableChangeData.getUpdatelist().get(i).getAcqUnitId()));
					this.displayUnitManagerService.doDisplayUnitAdd(displayUnit);
					
					if(user!=null){
						this.service.saveSystemLog(user,2,"编辑显示单元:"+displayUnitHandsontableChangeData.getInsertlist().get(i).getUnitName());
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
		try {
			if(protocolInstance.getPacketSendInterval()==null){
				protocolInstance.setPacketSendInterval(100);
			}
			this.protocolInstanceManagerService.doModbusProtocolInstanceAdd(protocolInstance);
			
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			if(user!=null){
				this.service.saveSystemLog(user,2,"添加采控实例:"+protocolInstance.getName());
			}
			
			List<String> instanceList=new ArrayList<String>();
			instanceList.add(protocolInstance.getName());
			DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
			dataSynchronizationThread.setSign(051);
			dataSynchronizationThread.setParam1(protocolInstance.getName());
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
			if(user!=null){
				this.service.saveSystemLog(user,2,"添加报警单元:"+alarmUnit.getUnitName());
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
			if(user!=null){
				this.service.saveSystemLog(user,2,"添加显示单元:"+displayUnit.getUnitName());
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
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		Gson gson=new Gson();
		String json ="{success:true}";
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<ModbusProtocolAlarmUnitSaveData>() {}.getType();
		ModbusProtocolAlarmUnitSaveData modbusProtocolAlarmUnitSaveData=gson.fromJson(data, type);
		
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
					
//					MemoryDataManagerTask.loadAlarmInstanceOwnItemByUnitId(modbusProtocolAlarmUnitSaveData.getDelidslist().get(i),"delete");
//					this.acquisitionUnitManagerService.doModbusProtocolAlarmUnitDelete(modbusProtocolAlarmUnitSaveData.getDelidslist().get(i));
					
					if(user!=null){
						this.service.saveSystemLog(user,2,"删除报警单元");
					}
				}
			}
			
			if(StringManagerUtils.isNotNull(modbusProtocolAlarmUnitSaveData.getUnitName())){
				AlarmUnit alarmUnit=new AlarmUnit();
				alarmUnit.setId(modbusProtocolAlarmUnitSaveData.getId());
				alarmUnit.setUnitCode(modbusProtocolAlarmUnitSaveData.getUnitCode());
				alarmUnit.setUnitName(modbusProtocolAlarmUnitSaveData.getUnitName());
				alarmUnit.setProtocol(modbusProtocolAlarmUnitSaveData.getProtocol());
				alarmUnit.setRemark(modbusProtocolAlarmUnitSaveData.getRemark());
				try {
					this.alarmUnitManagerService.doAlarmUnitEdit(alarmUnit);
					if(user!=null){
						this.service.saveSystemLog(user,2,"编辑报警单元:"+modbusProtocolAlarmUnitSaveData.getUnitName());
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
								
								int alarmLevel=0;
								int alarmSign=0;
								if("正常".equals(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getAlarmLevel())){
									alarmLevel=0;
								}else if("一级报警".equals(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getAlarmLevel())){
									alarmLevel=100;
								}else if("二级报警".equals(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getAlarmLevel())){
									alarmLevel=200;
								}else if("三级报警".equals(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getAlarmLevel())){
									alarmLevel=300;
								}
								
								if("使能".equalsIgnoreCase(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getAlarmSign())){
									alarmSign=1;
								}else if("失效".equalsIgnoreCase(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getAlarmSign())){
									alarmSign=0;
								}
								
								alarmUnitItem.setAlarmLevel(alarmLevel);
								alarmUnitItem.setAlarmSign(alarmSign);
								
								int isSendMessage=0;
								int isSendMail=0;
								if("是".equalsIgnoreCase(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getIsSendMessage())){
									isSendMessage=1;
								}else if("否".equalsIgnoreCase(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getIsSendMessage())){
									isSendMessage=0;
								}
								if("是".equalsIgnoreCase(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getIsSendMail())){
									isSendMail=1;
								}else if("否".equalsIgnoreCase(modbusProtocolAlarmUnitSaveData.getAlarmItems().get(i).getIsSendMail())){
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
		Gson gson=new Gson();
		String json ="{success:true}";
		String data = ParamUtils.getParameter(request, "data");
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
						this.service.saveSystemLog(user,2,"删除采控实例");
					}
					
//					EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByProtocolInstanceId(modbusProtocolInstanceSaveData.getDelidslist().get(i), "delete");
//					EquipmentDriverServerTask.initInstanceConfig(deleteInstanceList, "delete");
//					this.acquisitionUnitManagerService.doModbusProtocolInstanceBulkDelete(modbusProtocolInstanceSaveData.getDelidslist().get(i),modbusProtocolInstanceSaveData.getDeviceType());
				}
			}
			
			if(StringManagerUtils.isNotNull(modbusProtocolInstanceSaveData.getName()) && StringManagerUtils.isNotNull(modbusProtocolInstanceSaveData.getCode()) && modbusProtocolInstanceSaveData.getId()>0){
				String sql="select t.id from tbl_acq_unit_conf t where t.unit_name='"+modbusProtocolInstanceSaveData.getUnitName()+"' and rownum=1";
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
						this.service.saveSystemLog(user,2,"编辑采控实例:"+protocolInstance.getName());
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
			this.protocolDisplayInstanceManagerService.doModbusProtocolDisplayInstanceAdd(protocolDisplayInstance);
			DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
			dataSynchronizationThread.setSign(061);
			dataSynchronizationThread.setParam1(protocolDisplayInstance.getName());
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
			if(user!=null){
				this.service.saveSystemLog(user,2,"添加显示实例:"+protocolDisplayInstance.getName());
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
			if(user!=null){
				this.service.saveSystemLog(user,2,"添加报表单元:"+reportUnit.getUnitName());
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
			this.protocolReportInstanceManagerService.doModbusProtocolReportInstanceAdd(protocolReportInstance);
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			if(user!=null){
				this.service.saveSystemLog(user,2,"添加报表实例:"+protocolReportInstance.getName());
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
					
//					MemoryDataManagerTask.loadDisplayInstanceOwnItemById(modbusProtocolDisplayInstanceSaveData.getDelidslist().get(i),"delete");
//					this.protocolDisplayInstanceManagerService.doModbusProtocolDisplayInstanceBulkDelete(modbusProtocolDisplayInstanceSaveData.getDelidslist().get(i));
					
					if(user!=null){
						this.service.saveSystemLog(user,2,"删除显示实例");
					}
				}
			}
			
			if(StringManagerUtils.isNotNull(modbusProtocolDisplayInstanceSaveData.getName())){
				ProtocolDisplayInstance protocolDisplayInstance=new ProtocolDisplayInstance();
				protocolDisplayInstance.setId(modbusProtocolDisplayInstanceSaveData.getId());
				protocolDisplayInstance.setCode(modbusProtocolDisplayInstanceSaveData.getCode());
				protocolDisplayInstance.setName(modbusProtocolDisplayInstanceSaveData.getName());
				protocolDisplayInstance.setDisplayUnitId(modbusProtocolDisplayInstanceSaveData.getDisplayUnitId());
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
						this.service.saveSystemLog(user,2,"编辑显示实例:"+protocolDisplayInstance.getName());
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
						this.service.saveSystemLog(user,2,"删除报表单元");
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
						this.service.saveSystemLog(user,2,"编辑报表单元:"+reportUnit.getUnitName());
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
						this.service.saveSystemLog(user,2,"删除报表实例");
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
						this.service.saveSystemLog(user,2,"编辑报表实例:"+protocolReportInstance.getName());
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
			this.protocolAlarmInstanceManagerService.doModbusProtocolAlarmInstanceAdd(protocolAlarmInstance);
			DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
			dataSynchronizationThread.setSign(071);
			dataSynchronizationThread.setParam1(protocolAlarmInstance.getName());
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
			if(user!=null){
				this.service.saveSystemLog(user,2,"添加报警实例:"+protocolAlarmInstance.getName());
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
		User user = (User) session.getAttribute("userLogin");
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
						this.service.saveSystemLog(user,2,"删除报警实例");
					}
				}
			}
			
			if(StringManagerUtils.isNotNull(modbusProtocolAlarmInstanceSaveData.getName())){
				ProtocolAlarmInstance protocolAlarmInstance=new ProtocolAlarmInstance();
				protocolAlarmInstance.setId(modbusProtocolAlarmInstanceSaveData.getId());
				protocolAlarmInstance.setCode(modbusProtocolAlarmInstanceSaveData.getCode());
				protocolAlarmInstance.setName(modbusProtocolAlarmInstanceSaveData.getName());
				protocolAlarmInstance.setAlarmUnitId(modbusProtocolAlarmInstanceSaveData.getAlarmUnitId());
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
						this.service.saveSystemLog(user,2,"编辑报警实例:"+protocolAlarmInstance.getName());
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
			if(user!=null){
				this.service.saveSystemLog(user,2,"添加短信实例:"+protocolSMSInstance.getName());
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
			if(user!=null){
				this.service.saveSystemLog(user,2,"编辑短信实例:"+protocolSMSInstance.getName());
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
			if(user!=null){
				this.service.saveSystemLog(user,2,"删除短信实例");
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
		String json = this.acquisitionUnitManagerService.getDatabaseColumnMappingTable(classes,deviceType,protocolCode);
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
		String json = this.acquisitionUnitManagerService.getProtocolRunStatusItems(classes,deviceType,protocolCode);
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
		String json = this.acquisitionUnitManagerService.getProtocolRunStatusItemsMeaning(status,deviceType,protocolCode,itemName,itemColumn,resolutionMode);
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
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String protocolType = ParamUtils.getParameter(request, "protocolType");
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<DatabaseMappingProHandsontableChangedData>() {}.getType();
		DatabaseMappingProHandsontableChangedData databaseMappingProHandsontableChangedData=gson.fromJson(data, type);
		if(databaseMappingProHandsontableChangedData!=null){
			this.acquisitionUnitManagerService.saveDatabaseColumnMappingTable(databaseMappingProHandsontableChangedData,protocolType);
			EquipmentDriverServerTask.syncDataMappingTable();
			
			HttpSession session=request.getSession();
			User user = (User) session.getAttribute("userLogin");
			if(user!=null){
				this.service.saveSystemLog(user,2,"修改协议字段映射");
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
		this.acquisitionUnitManagerService.saveProtocolRunStatusConfig(protocolCode,resolutionMode,itemName,itemColumn,deviceType,runValue,stopValue,runCondition,stopCondition);
		MemoryDataManagerTask.loadProtocolRunStatusConfig();
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		if(user!=null){
			this.service.saveSystemLog(user,2,"修改协议"+protocolName+"运行状态运算逻辑");
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
		String protocolName = ParamUtils.getParameter(request, "protocolName").replaceAll(" ", "");
		boolean flag = this.acquisitionUnitManagerService.judgeProtocolExistOrNot(protocolName);
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
		boolean flag = this.acquisitionUnitManagerService.judgeInstanceExistOrNot(instanceName);
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
			if(user!=null){
				this.service.saveSystemLog(user,2,"导出协议"+protocolName+"数据");
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
//		System.out.println(fileContent);
		
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
							acqUnit_json.append("\"typeName\":\""+(exportProtocolConfig.getAcqUnitList().get(i).getAcqGroupList().get(j).getType()==0?"采集组":"控制组")+"\",");
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
		String data = StringManagerUtils.delSpace(ParamUtils.getParameter(request, "data"));
		String check=ParamUtils.getParameter(request, "check");
		System.out.println("协议导入内容"+data);
		
		if("1".equals(check)){
			json = acquisitionUnitItemManagerService.importProtocolCheck(data);
		}else{
			json = acquisitionUnitItemManagerService.saveImportProtocolData(data);
		}
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		if(user!=null){
			this.service.saveSystemLog(user,2,"导入协议");
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
		String json = "";
		json = acquisitionUnitItemManagerService.getImportProtocolContentData(id,classes,type);
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
		if (!StringManagerUtils.isNotNull(deviceTypeIds)) {
			User user = null;
			HttpSession session=request.getSession();
			user = (User) session.getAttribute("userLogin");
			if (user != null) {
				deviceTypeIds = "" + user.getDeviceTypeIds();
			}
		}
		String json = this.acquisitionUnitItemManagerService.getProtocolDeviceTypeChangeProtocolList(deviceTypeIds);
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
