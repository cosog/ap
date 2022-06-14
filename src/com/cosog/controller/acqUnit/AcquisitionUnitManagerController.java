package com.cosog.controller.acqUnit;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.cosog.model.AcquisitionGroup;
import com.cosog.model.AcquisitionGroupItem;
import com.cosog.model.AcquisitionUnit;
import com.cosog.model.AcquisitionUnitGroup;
import com.cosog.model.AlarmUnit;
import com.cosog.model.AlarmUnitItem;
import com.cosog.model.DisplayUnit;
import com.cosog.model.DisplayUnitItem;
import com.cosog.model.Module;
import com.cosog.model.ProtocolAlarmInstance;
import com.cosog.model.ProtocolDisplayInstance;
import com.cosog.model.ProtocolInstance;
import com.cosog.model.ProtocolSMSInstance;
import com.cosog.model.Role;
import com.cosog.model.RoleModule;
import com.cosog.model.User;
import com.cosog.model.drive.KafkaConfig;
import com.cosog.model.drive.ModbusDriverSaveData;
import com.cosog.model.drive.ModbusProtocolAlarmUnitSaveData;
import com.cosog.model.drive.ModbusProtocolAlarmInstanceSaveData;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.drive.ModbusProtocolConfig.Items;
import com.cosog.model.drive.ModbusProtocolConfig.ItemsMeaning;
import com.cosog.model.drive.ModbusProtocolDisplayInstanceSaveData;
import com.cosog.model.drive.ModbusProtocolInstanceSaveData;
import com.cosog.model.gridmodel.AcquisitionGroupHandsontableChangeData;
import com.cosog.model.gridmodel.AcquisitionUnitHandsontableChangeData;
import com.cosog.model.gridmodel.DatabaseMappingProHandsontableChangedData;
import com.cosog.model.gridmodel.DisplayUnitHandsontableChangeData;
import com.cosog.service.acqUnit.AcquisitionUnitManagerService;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.right.RoleManagerService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.AcquisitionItemColumnsMap;
import com.cosog.utils.BackModuleRecursion;
import com.cosog.utils.Config;
import com.cosog.utils.Constants;
import com.cosog.utils.DataSourceConfig;
import com.cosog.utils.DataSourceConfigSaveData;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.Page;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.TcpServerConfigMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
	private AcquisitionUnitManagerService<ProtocolInstance> protocolInstanceManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<ProtocolDisplayInstance> protocolDisplayInstanceManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<ProtocolAlarmInstance> protocolAlarmInstanceManagerService;
	
	@Autowired
	private AcquisitionUnitManagerService<ProtocolSMSInstance> protocolSMSInstanceManagerService;
	
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
	public String doModbusProtocolAdd() throws IOException {
		String result = "";
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		Gson gson = new Gson();
		String fileName="ModbusProtocolConfig.json";
		String path=stringManagerUtils.getFilePath(fileName,"protocolConfig/");
		PrintWriter out = response.getWriter();
		try {
			String name = ParamUtils.getParameter(request, "modbusProtocol.name");
			String deviceType = ParamUtils.getParameter(request, "modbusProtocol.deviceType");
			String sort = ParamUtils.getParameter(request, "modbusProtocol.sort");
			
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			boolean isAdd=true;
			if(modbusProtocolConfig!=null){
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					if(name.equals(modbusProtocolConfig.getProtocol().get(i).getName())){
						modbusProtocolConfig.getProtocol().get(i).setSort(StringManagerUtils.stringToInteger(sort));
						modbusProtocolConfig.getProtocol().get(i).setDeviceType(StringManagerUtils.stringToInteger(deviceType));
						isAdd=false;
						break;
					}
				}
			}
			if(isAdd){
				ModbusProtocolConfig.Protocol protocol=new ModbusProtocolConfig.Protocol();
				String protocolCode=name;
				protocol.setName(name);
				protocol.setCode(protocolCode);
				protocol.setDeviceType(StringManagerUtils.stringToInteger(deviceType));
				protocol.setSort(StringManagerUtils.stringToInteger(sort));
				protocol.setItems(new ArrayList<ModbusProtocolConfig.Items>());
				modbusProtocolConfig.getProtocol().add(protocol);
			}
			StringManagerUtils.writeFile(path,StringManagerUtils.jsonStringFormat(gson.toJson(modbusProtocolConfig)));
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
				if(acquisitionGroup.getAcqCycle()==null || acquisitionGroup.getAcqCycle()==0){
					acquisitionGroup.setAcqCycle(60);
				}
				if(acquisitionGroup.getSaveCycle()==null || acquisitionGroup.getSaveCycle()==0){
					acquisitionGroup.setSaveCycle(300);
				}
			}
			this.acquisitionGroupManagerService.doAcquisitionGroupAdd(acquisitionGroup);
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
			if(StringManagerUtils.isNotNull(groupId)){
				EquipmentDriverServerTask.initInstanceConfigByAcqGroupId(groupId, "update");
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
	
	@RequestMapping("/doAcquisitionGroupEdit")
	public String doAcquisitionGroupEdit(@ModelAttribute AcquisitionGroup acquisitionGroup) throws IOException {
		String result ="{success:true,msg:false}";
		PrintWriter out = response.getWriter();
		try {
			this.acquisitionUnitManagerService.doAcquisitionGroupEdit(acquisitionGroup);
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
			this.acquisitionUnitManagerService.doAcquisitionUnitBulkDelete(ids,deviceType);
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
			String groupIdSql="select t.id from tbl_acq_group_conf t where t.group_code='"+groupCode+"' ";
			List list = this.service.findCallSql(groupIdSql);
			if(list.size()>0){
				groupId=list.get(0).toString();
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
				EquipmentDriverServerTask.initInstanceConfigByAcqGroupId(groupId+"","update");
				EquipmentDriverServerTask.initPumpDriverAcquisitionInfoConfigByAcqGroupId(groupId+"","update");
				this.acquisitionUnitItemManagerService.doAcquisitionGroupOwnItemChange(groupId);
				MemoryDataManagerTask.loadAcqInstanceOwnItemByGroupId(groupId,"update");
				MemoryDataManagerTask.loadDisplayInstanceOwnItemByAcqGroupId(groupId,"update");
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
		int dataSaveMode=Config.getInstance().configFile.getOthers().getDataSaveMode();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
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
			
			if (paramsArr.length > 0 && StringManagerUtils.isNotNull(unitId) && protocol!=null) {
				this.displayUnitItemManagerService.deleteCurrentDisplayUnitOwnItems(unitId,itemType);
				int protocolType=protocol.getDeviceType();
				String columnsKey="rpcDeviceAcquisitionItemColumns";
				if(protocolType==1){
					columnsKey="pcpDeviceAcquisitionItemColumns";
				}
				Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
				if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
					EquipmentDriverServerTask.loadAcquisitionItemColumns(protocolType);
				}
				Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
				
				if (matrixCodes != "" || matrixCodes != null) {
					String module_matrix[] = matrixCodes.split("\\|");
					List<String> itemsList=new ArrayList<String>();
					for (int i = 0; i < module_matrix.length; i++) {
						String module_[] = module_matrix[i].split("\\:");
						String itemName=module_[0];
						int itemAddr=StringManagerUtils.stringTransferInteger(module_[8]);
						String resolutionMode=module_[7];
						String bitIndexStr=module_[9];
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
						if(StringManagerUtils.isNotNull(module_[4])){
							StringManagerUtils.printLog("#"+module_[4]+"isColor16:"+StringManagerUtils.isColor16("#"+module_[4]));
						}
						
						displayUnitItem = new DisplayUnitItem();
						displayUnitItem.setUnitId(StringManagerUtils.stringToInteger(unitId));
						displayUnitItem.setItemName(itemName);
						displayUnitItem.setItemCode(dataSaveMode==0?("addr"+itemAddr):(loadedAcquisitionItemColumnsMap.get(itemName)));
						displayUnitItem.setType(StringManagerUtils.stringToInteger(itemType));
						displayUnitItem.setSort(StringManagerUtils.isNumber(module_[1])?StringManagerUtils.stringTransferInteger(module_[1]):null);
						displayUnitItem.setBitIndex(bitIndex>=0?bitIndex:null);
						displayUnitItem.setShowLevel(StringManagerUtils.isNumber(module_[2])?StringManagerUtils.stringTransferInteger(module_[2]):null);
						displayUnitItem.setRealtimeCurve((StringManagerUtils.isNumber(module_[3]) && !"开关量".equalsIgnoreCase(resolutionMode))?StringManagerUtils.stringTransferInteger(module_[3]):null);
						displayUnitItem.setRealtimeCurveColor((!"开关量".equalsIgnoreCase(resolutionMode))&&StringManagerUtils.isColor16("#"+module_[4])?module_[4]:"");
						displayUnitItem.setHistoryCurve((StringManagerUtils.isNumber(module_[5]) && !"开关量".equalsIgnoreCase(resolutionMode))?StringManagerUtils.stringTransferInteger(module_[5]):null);
						displayUnitItem.setHistoryCurveColor((!"开关量".equalsIgnoreCase(resolutionMode))&&StringManagerUtils.isColor16("#"+module_[6])?module_[6]:"");
						displayUnitItem.setMatrix(module_[10]);
						this.displayUnitItemManagerService.grantDisplayItemsPermission(displayUnitItem);
					}
					MemoryDataManagerTask.loadDisplayInstanceOwnItemByUnitId(unitId,"update");
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
		int dataSaveMode=Config.getInstance().configFile.getOthers().getDataSaveMode();
		ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
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
			
			if (paramsArr.length > 0 && StringManagerUtils.isNotNull(unitId) && protocol!=null) {
				this.displayUnitItemManagerService.deleteCurrentDisplayUnitOwnItems(unitId,itemType);
				int protocolType=protocol.getDeviceType();
				String columnsKey="rpcDeviceAcquisitionItemColumns";
				if(protocolType==1){
					columnsKey="pcpDeviceAcquisitionItemColumns";
				}
				Map<String, Map<String,String>> acquisitionItemColumnsMap=AcquisitionItemColumnsMap.getMapObject();
				if(acquisitionItemColumnsMap==null||acquisitionItemColumnsMap.size()==0||acquisitionItemColumnsMap.get(columnsKey)==null){
					EquipmentDriverServerTask.loadAcquisitionItemColumns(protocolType);
				}
				Map<String,String> loadedAcquisitionItemColumnsMap=acquisitionItemColumnsMap.get(columnsKey);
				if (matrixCodes != "" || matrixCodes != null) {
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
						
						displayUnitItem = new DisplayUnitItem();
						displayUnitItem.setUnitId(StringManagerUtils.stringToInteger(unitId));
						displayUnitItem.setItemName(itemName);
						displayUnitItem.setItemCode(dataSaveMode==0?("addr"+itemAddr):(loadedAcquisitionItemColumnsMap.get(itemName)));
						displayUnitItem.setType(StringManagerUtils.stringToInteger(itemType));
						displayUnitItem.setSort(StringManagerUtils.isNumber(module_[1])?StringManagerUtils.stringTransferInteger(module_[1]):null);
						displayUnitItem.setBitIndex(bitIndex>=0?bitIndex:null);
						displayUnitItem.setMatrix(module_[6]);
						this.displayUnitItemManagerService.grantDisplayItemsPermission(displayUnitItem);
					}
					MemoryDataManagerTask.loadDisplayInstanceOwnItemByUnitId(unitId,"update");
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
	 * 显示单元安排计算项
	 */
	@RequestMapping("/grantCalItemsToDisplayUnitPermission")
	public String grantCalItemsToDisplayUnitPermission() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		DisplayUnitItem displayUnitItem = null;
		try {
			String params = ParamUtils.getParameter(request, "params");
			String matrixCodes = ParamUtils.getParameter(request, "matrixCodes");
			String unitId = ParamUtils.getParameter(request, "unitId");
			String protocolName = ParamUtils.getParameter(request, "protocol");
			String itemType = ParamUtils.getParameter(request, "itemType");
			log.debug("grantAcquisitionItemsPermission params==" + params);
			String paramsArr[] = StringManagerUtils.split(params, ",");

			this.displayUnitItemManagerService.deleteCurrentDisplayUnitOwnItems(unitId,itemType);
			if (matrixCodes != "" || matrixCodes != null) {
				String module_matrix[] = matrixCodes.split("\\|");
				List<String> itemsList=new ArrayList<String>();
				for (int i = 0; i < module_matrix.length; i++) {
					String module_[] = module_matrix[i].split("\\:");
					
					if(StringManagerUtils.isNotNull(module_[4])){
						StringManagerUtils.printLog("#"+module_[4]+"isColor16:"+StringManagerUtils.isColor16("#"+module_[4]));
					}
					String itemName=module_[0];
					displayUnitItem = new DisplayUnitItem();
					displayUnitItem.setUnitId(StringManagerUtils.stringToInteger(unitId));
					displayUnitItem.setItemName(itemName);
					displayUnitItem.setItemCode(module_[1]);
					displayUnitItem.setType(StringManagerUtils.stringToInteger(itemType));
					displayUnitItem.setSort(StringManagerUtils.isNumber(module_[2])?StringManagerUtils.stringTransferInteger(module_[2]):null);
					displayUnitItem.setShowLevel(StringManagerUtils.isNumber(module_[3])?StringManagerUtils.stringTransferInteger(module_[3]):null);
					displayUnitItem.setRealtimeCurve((StringManagerUtils.isNumber(module_[4]))?StringManagerUtils.stringTransferInteger(module_[4]):null);
					displayUnitItem.setRealtimeCurveColor(StringManagerUtils.isColor16("#"+module_[5])?module_[5]:"");
					displayUnitItem.setHistoryCurve((StringManagerUtils.isNumber(module_[6]))?StringManagerUtils.stringTransferInteger(module_[6]):null);
					displayUnitItem.setHistoryCurveColor(StringManagerUtils.isColor16("#"+module_[7])?module_[7]:"");
					displayUnitItem.setMatrix(module_[8]);
					this.displayUnitItemManagerService.grantDisplayItemsPermission(displayUnitItem);
				}
				MemoryDataManagerTask.loadDisplayInstanceOwnItemByUnitId(unitId,"update");
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
	
	@RequestMapping("/acquisitionUnitTreeData")
	public String acquisitionUnitTreeData() throws IOException {
		String json = acquisitionUnitItemManagerService.getAcquisitionUnitTreeData();
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
		String json = acquisitionUnitItemManagerService.getDisplayUnitTreeData();
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
		String json = acquisitionUnitItemManagerService.modbusProtocolAddrMappingTreeData();
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
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		String protocol=ParamUtils.getParameter(request, "protocol");
		String json = acquisitionUnitItemManagerService.modbusProtocolAndAcqUnitTreeData(deviceType,protocol);
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
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		String json = acquisitionUnitItemManagerService.modbusProtocolAndDisplayUnitTreeData(deviceType);
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
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		String json = acquisitionUnitItemManagerService.modbusProtocolAndAlarmUnitTreeData(deviceType);
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
		String json = acquisitionUnitItemManagerService.modbusProtocolAlarmUnitTreeData();
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
		String json = acquisitionUnitItemManagerService.getModbusProtocolInstanceConfigTreeData();
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
		String json = acquisitionUnitItemManagerService.getModbusDisplayProtocolInstanceConfigTreeData();
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
		String json = acquisitionUnitItemManagerService.getModbusAlarmProtocolInstanceConfigTreeData();
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
		String json=acquisitionUnitItemManagerService.getModbusProtoclCombList();
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
		String json=acquisitionUnitItemManagerService.getAcquisitionUnitCombList(protocol);
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
		String fileName="ModbusProtocolConfig.json";
		String data = ParamUtils.getParameter(request, "data");
//		StringManagerUtils.printLog(data);
		java.lang.reflect.Type type = new TypeToken<ModbusDriverSaveData>() {}.getType();
		ModbusDriverSaveData modbusDriverSaveData=gson.fromJson(data, type);
		if(modbusDriverSaveData!=null){
			modbusDriverSaveData.dataFiltering();
			String path=stringManagerUtils.getFilePath(fileName,"protocolConfig/");
			
			ModbusProtocolConfig modbusProtocolConfig=MemoryDataManagerTask.getModbusProtocolConfig();
			if(modbusProtocolConfig==null){
				modbusProtocolConfig=new ModbusProtocolConfig();
				modbusProtocolConfig.setProtocol(new ArrayList<ModbusProtocolConfig.Protocol>());
			}else if(modbusProtocolConfig.getProtocol()==null){
				modbusProtocolConfig.setProtocol(new ArrayList<ModbusProtocolConfig.Protocol>());
			}
			boolean isAdd=true;
			
			//删除协议
			for(int i=0;modbusDriverSaveData.getDelidslist()!=null&&i<modbusDriverSaveData.getDelidslist().size();i++){
				for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
					if(modbusDriverSaveData.getDelidslist().get(i).equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(j).getName())){
						EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByProtocolName(modbusProtocolConfig.getProtocol().get(j).getName(),modbusProtocolConfig.getProtocol().get(j).getDeviceType(),"delete");
						EquipmentDriverServerTask.initInstanceConfigByProtocolName(modbusProtocolConfig.getProtocol().get(j).getName(),"delete");
						EquipmentDriverServerTask.initProtocolConfig(modbusProtocolConfig.getProtocol().get(j).getName(),"delete");
						this.acquisitionUnitManagerService.doDeleteProtocolAssociation(modbusProtocolConfig.getProtocol().get(j).getDeviceType(),modbusProtocolConfig.getProtocol().get(j).getName());
						modbusProtocolConfig.getProtocol().remove(j);
						break;
					}
				}
			}
			
			if(StringManagerUtils.isNotNull(modbusDriverSaveData.getProtocolName())){
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					if(modbusDriverSaveData.getProtocolCode().equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getCode())){
						isAdd=false;
						String oldName=modbusProtocolConfig.getProtocol().get(i).getName();
						modbusProtocolConfig.getProtocol().get(i).setName(modbusDriverSaveData.getProtocolName());
						modbusProtocolConfig.getProtocol().get(i).setCode(modbusDriverSaveData.getProtocolName());
						modbusProtocolConfig.getProtocol().get(i).setDeviceType(modbusDriverSaveData.getDeviceType());
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
							service.updateSql(unitSql);
							service.updateSql(groupSql);
							service.updateSql(alatmUnitSql);
						}
						if(delItemList.size()>0){
							String delSql="delete from tbl_acq_item2group_conf t5 "
									+ " where t5.id in "
									+ "(select t4.id from tbl_acq_unit_conf t,tbl_acq_group2unit_conf t2,tbl_acq_group_conf t3,tbl_acq_item2group_conf t4 "
									+ " where t.id=t2.unitid and t2.groupid=t3.id and t3.id=t4.groupid "
									+ "and t.protocol='"+modbusDriverSaveData.getProtocolName()+"' "
									+ " and t4.itemname in ("+StringManagerUtils.joinStringArr2(delItemList, ",")+"))";
							service.updateSql(delSql);
						}
						Collections.sort(modbusProtocolConfig.getProtocol().get(i).getItems());
						break;
					}
				}
				if(isAdd){
					ModbusProtocolConfig.Protocol protocol=new ModbusProtocolConfig.Protocol();
					String protocolCode=modbusDriverSaveData.getProtocolName();
					protocol.setName(modbusDriverSaveData.getProtocolName());
					protocol.setCode(protocolCode);
					modbusDriverSaveData.setProtocolCode(protocolCode);
					protocol.setSort(modbusDriverSaveData.getSort());
					protocol.setDeviceType(modbusDriverSaveData.getDeviceType());
					protocol.setItems(new ArrayList<ModbusProtocolConfig.Items>());
					for(int i=0;i<modbusDriverSaveData.getDataConfig().size();i++){
						String acqMode="passive";
						if("主动上传".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getAcqMode())){
							acqMode="active";
						}else if("被动响应".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getAcqMode())){
							acqMode="passive";
						}
						
						String RWType="r";
						if("读写".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getRWType())){
							RWType="rw";
						}else if("只读".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getRWType())){
							RWType="r";
						}
						
						ModbusProtocolConfig.Items item=new ModbusProtocolConfig.Items();
						item.setTitle(modbusDriverSaveData.getDataConfig().get(i).getTitle());
						item.setAddr(modbusDriverSaveData.getDataConfig().get(i).getAddr());
						item.setQuantity(modbusDriverSaveData.getDataConfig().get(i).getQuantity());
						item.setUnit(modbusDriverSaveData.getDataConfig().get(i).getUnit());
						item.setRatio(modbusDriverSaveData.getDataConfig().get(i).getRatio());
						item.setStoreDataType(modbusDriverSaveData.getDataConfig().get(i).getStoreDataType());
						item.setIFDataType(modbusDriverSaveData.getDataConfig().get(i).getIFDataType());
						item.setRWType(RWType);
						item.setAcqMode(acqMode);
						if(modbusDriverSaveData.getDataConfig().get(i).getMeaning()!=null && modbusDriverSaveData.getDataConfig().get(i).getMeaning().size()>0){
							item.setMeaning(new ArrayList<ItemsMeaning>());
							for(int m=0;m<modbusDriverSaveData.getDataConfig().get(i).getMeaning().size();m++){
								ItemsMeaning itemsMeaning=new ItemsMeaning();
								itemsMeaning.setValue(modbusDriverSaveData.getDataConfig().get(i).getMeaning().get(m).getValue());
								itemsMeaning.setMeaning(modbusDriverSaveData.getDataConfig().get(i).getMeaning().get(m).getMeaning());
								item.getMeaning().add(itemsMeaning);
							}
						}
						protocol.getItems().add(item);
					}
					Collections.sort(protocol.getItems());
					modbusProtocolConfig.getProtocol().add(protocol);
				}
			}
			StringManagerUtils.writeFile(path,StringManagerUtils.jsonStringFormat(gson.toJson(modbusProtocolConfig)));
			Jedis jedis=null;
			try{
				jedis = new Jedis();
				jedis.set("modbusProtocolConfig".getBytes(), SerializeObjectUnils.serialize(modbusProtocolConfig));
			}catch(Exception e){
				e.printStackTrace();
			}
			if(jedis!=null){
				jedis.disconnect();
				jedis.close();
			}
			if(StringManagerUtils.isNotNull(modbusDriverSaveData.getProtocolName())){
				EquipmentDriverServerTask.initProtocolConfig(modbusDriverSaveData.getProtocolName(),"update");
				EquipmentDriverServerTask.initInstanceConfigByProtocolName(modbusDriverSaveData.getProtocolName(),"update");
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
		java.lang.reflect.Type type = new TypeToken<AcquisitionUnitHandsontableChangeData>() {}.getType();
		AcquisitionUnitHandsontableChangeData acquisitionUnitHandsontableChangeData=gson.fromJson(data, type);
		if(acquisitionUnitHandsontableChangeData!=null){
			if(acquisitionUnitHandsontableChangeData.getDelidslist()!=null){
				for(int i=0;i<acquisitionUnitHandsontableChangeData.getDelidslist().size();i++){
					EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByAcqUnitId(acquisitionUnitHandsontableChangeData.getDelidslist().get(i), "delete");
					EquipmentDriverServerTask.initInstanceConfigByAcqUnitId(acquisitionUnitHandsontableChangeData.getDelidslist().get(i), "delete");
					MemoryDataManagerTask.loadAcqInstanceOwnItemByUnitId(acquisitionUnitHandsontableChangeData.getDelidslist().get(i), "delete");
					MemoryDataManagerTask.loadDisplayInstanceOwnItemByUnitId(acquisitionUnitHandsontableChangeData.getDelidslist().get(i), "delete");
					this.acquisitionUnitManagerService.doAcquisitionUnitBulkDelete(acquisitionUnitHandsontableChangeData.getDelidslist().get(i),deviceType);
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
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String protocol = ParamUtils.getParameter(request, "protocol");
		String unitId = ParamUtils.getParameter(request, "unitId");
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<AcquisitionGroupHandsontableChangeData>() {}.getType();
		AcquisitionGroupHandsontableChangeData acquisitionGroupHandsontableChangeData=gson.fromJson(data, type);
		if(acquisitionGroupHandsontableChangeData!=null){
			if(acquisitionGroupHandsontableChangeData.getDelidslist()!=null){
				for(int i=0;i<acquisitionGroupHandsontableChangeData.getDelidslist().size();i++){
					this.acquisitionUnitManagerService.doAcquisitionGroupBulkDelete(acquisitionGroupHandsontableChangeData.getDelidslist().get(i));
					EquipmentDriverServerTask.initInstanceConfigByAcqUnitId(unitId, "update");
					MemoryDataManagerTask.loadAcqInstanceOwnItemByUnitId(unitId,"update");
					MemoryDataManagerTask.loadDisplayInstanceOwnItemByUnitId(unitId,"update");
				}
			}
			if(acquisitionGroupHandsontableChangeData.getUpdatelist()!=null){
				for(int i=0;i<acquisitionGroupHandsontableChangeData.getUpdatelist().size();i++){
					AcquisitionGroup acquisitionGroup=new AcquisitionGroup();
					acquisitionGroup.setId(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getId()));
					acquisitionGroup.setGroupCode(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getGroupCode());
					acquisitionGroup.setGroupName(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getGroupName());
					acquisitionGroup.setType("采集组".equalsIgnoreCase(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getTypeName())?0:1);
					acquisitionGroup.setAcqCycle(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getAcqCycle()));
					acquisitionGroup.setSaveCycle(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getSaveCycle()));
					acquisitionGroup.setRemark(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getRemark());
					acquisitionGroup.setProtocol(protocol);
					this.acquisitionUnitManagerService.doAcquisitionGroupEdit(acquisitionGroup);
//					EquipmentDriverServerTask.initInstanceConfigByAcqGroupId(acquisitionGroup.getId()+"", "update");
				}
			}
			
			if(acquisitionGroupHandsontableChangeData.getInsertlist()!=null){
				for(int i=0;i<acquisitionGroupHandsontableChangeData.getInsertlist().size();i++){
					AcquisitionGroup acquisitionGroup=new AcquisitionGroup();
					acquisitionGroup.setId(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getId()));
					acquisitionGroup.setGroupCode(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getGroupCode());
					acquisitionGroup.setGroupName(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getGroupName());
					acquisitionGroup.setType("采集组".equalsIgnoreCase(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getTypeName())?0:1);
					acquisitionGroup.setAcqCycle(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getAcqCycle()));
					acquisitionGroup.setSaveCycle(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getSaveCycle()));
					acquisitionGroup.setRemark(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getRemark());
					acquisitionGroup.setProtocol(protocol);
					this.acquisitionUnitManagerService.doAcquisitionGroupAdd(acquisitionGroup);
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
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String protocol = ParamUtils.getParameter(request, "protocol");
		String deviceType=ParamUtils.getParameter(request, "deviceType");
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<DisplayUnitHandsontableChangeData>() {}.getType();
		DisplayUnitHandsontableChangeData displayUnitHandsontableChangeData=gson.fromJson(data, type);
		if(displayUnitHandsontableChangeData!=null){
			if(displayUnitHandsontableChangeData.getDelidslist()!=null){
				for(int i=0;i<displayUnitHandsontableChangeData.getDelidslist().size();i++){
					this.displayUnitManagerService.doDisplayUnitBulkDelete(displayUnitHandsontableChangeData.getDelidslist().get(i),deviceType);
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
				}
			}
			
			if(displayUnitHandsontableChangeData.getInsertlist()!=null){
				for(int i=0;i<displayUnitHandsontableChangeData.getInsertlist().size();i++){
					DisplayUnit displayUnit=new DisplayUnit();
					acquisitionUnit.setProtocol(protocol);
					displayUnit.setUnitCode(displayUnitHandsontableChangeData.getInsertlist().get(i).getUnitCode());
					displayUnit.setUnitName(displayUnitHandsontableChangeData.getInsertlist().get(i).getUnitName());
					displayUnit.setRemark(displayUnitHandsontableChangeData.getInsertlist().get(i).getRemark());
					displayUnit.setProtocol(protocol);
					displayUnit.setAcqUnitId(StringManagerUtils.stringToInteger(displayUnitHandsontableChangeData.getUpdatelist().get(i).getAcqUnitId()));
					this.displayUnitManagerService.doDisplayUnitAdd(displayUnit);
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
			this.protocolInstanceManagerService.doModbusProtocolInstanceAdd(protocolInstance);
			List<String> instanceList=new ArrayList<String>();
			instanceList.add(protocolInstance.getName());
			MemoryDataManagerTask.loadAcqInstanceOwnItemByCode(protocolInstance.getCode(),"update");
			EquipmentDriverServerTask.initInstanceConfig(instanceList, "update");
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
		
		if(modbusProtocolAlarmUnitSaveData!=null){
			if(modbusProtocolAlarmUnitSaveData.getDelidslist()!=null){
				for(int i=0;i<modbusProtocolAlarmUnitSaveData.getDelidslist().size();i++){
					MemoryDataManagerTask.loadAlarmInstanceOwnItemByUnitId(modbusProtocolAlarmUnitSaveData.getDelidslist().get(i),"delete");
					this.acquisitionUnitManagerService.doModbusProtocolAlarmUnitDelete(modbusProtocolAlarmUnitSaveData.getDelidslist().get(i));
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
						
						MemoryDataManagerTask.loadAlarmInstanceOwnItemByUnitId(modbusProtocolAlarmUnitSaveData.getId()+"","update");
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
		Gson gson=new Gson();
		String json ="{success:true}";
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<ModbusProtocolInstanceSaveData>() {}.getType();
		ModbusProtocolInstanceSaveData modbusProtocolInstanceSaveData=gson.fromJson(data, type);
		
		if(modbusProtocolInstanceSaveData!=null){
			if(modbusProtocolInstanceSaveData.getDelidslist()!=null){
				for(int i=0;i<modbusProtocolInstanceSaveData.getDelidslist().size();i++){
					List<String> deleteInstanceList=new ArrayList<String>();
					deleteInstanceList.add(modbusProtocolInstanceSaveData.getName());
					EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByProtocolInstanceId(modbusProtocolInstanceSaveData.getDelidslist().get(i), "delete");
					EquipmentDriverServerTask.initInstanceConfig(deleteInstanceList, "delete");
					this.acquisitionUnitManagerService.doModbusProtocolInstanceBulkDelete(modbusProtocolInstanceSaveData.getDelidslist().get(i),modbusProtocolInstanceSaveData.getDeviceType());
				}
			}
			
			if(StringManagerUtils.isNotNull(modbusProtocolInstanceSaveData.getName())){
				String sql="select t.id from tbl_acq_unit_conf t where t.unit_name='"+modbusProtocolInstanceSaveData.getUnitName()+"' and rownum=1";
				String unitId="";
				List list = this.service.findCallSql(sql);
				if(list.size()>0){
					unitId=list.get(0).toString();
					ProtocolInstance protocolInstance=new ProtocolInstance();
					protocolInstance.setId(modbusProtocolInstanceSaveData.getId());
					protocolInstance.setCode(modbusProtocolInstanceSaveData.getCode());
					protocolInstance.setName(modbusProtocolInstanceSaveData.getName());
					protocolInstance.setDeviceType(modbusProtocolInstanceSaveData.getDeviceType());
					protocolInstance.setUnitId(StringManagerUtils.stringToInteger(unitId));
					protocolInstance.setAcqProtocolType(modbusProtocolInstanceSaveData.getAcqProtocolType());
					protocolInstance.setCtrlProtocolType(modbusProtocolInstanceSaveData.getCtrlProtocolType());
					protocolInstance.setSignInPrefix(modbusProtocolInstanceSaveData.getSignInPrefix());
					protocolInstance.setSignInSuffix(modbusProtocolInstanceSaveData.getSignInSuffix());
					protocolInstance.setHeartbeatPrefix(modbusProtocolInstanceSaveData.getHeartbeatPrefix());
					protocolInstance.setHeartbeatSuffix(modbusProtocolInstanceSaveData.getHeartbeatSuffix());
					
					if(StringManagerUtils.isNum(modbusProtocolInstanceSaveData.getSort())){
						protocolInstance.setSort(StringManagerUtils.stringToInteger(modbusProtocolInstanceSaveData.getSort()));
					}else{
						protocolInstance.setSort(null);
					}
					
					try {
						this.protocolInstanceManagerService.doModbusProtocolInstanceEdit(protocolInstance);
						//实例名称是否改变
						if(modbusProtocolInstanceSaveData.getName().equals(modbusProtocolInstanceSaveData.getOldName())){
							List<String> instanceList=new ArrayList<String>();
							instanceList.add(protocolInstance.getName());
							EquipmentDriverServerTask.initInstanceConfig(instanceList, "update");
						}else{
							List<String> instanceList=new ArrayList<String>();
							instanceList.add(modbusProtocolInstanceSaveData.getOldName());
							EquipmentDriverServerTask.initInstanceConfig(instanceList, "delete");
							
							instanceList=new ArrayList<String>();
							instanceList.add(modbusProtocolInstanceSaveData.getName());
							EquipmentDriverServerTask.initInstanceConfig(instanceList, "update");
						}
						EquipmentDriverServerTask.initDriverAcquisitionInfoConfigByProtocolInstanceId(modbusProtocolInstanceSaveData.getId()+"", "update");
						json = "{success:true,msg:true}";
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						json = "{success:false,msg:false}";
					}
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
			MemoryDataManagerTask.loadDisplayInstanceOwnItemByCode(protocolDisplayInstance.getCode(),"update");
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
		Gson gson=new Gson();
		String json ="{success:true}";
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<ModbusProtocolDisplayInstanceSaveData>() {}.getType();
		ModbusProtocolDisplayInstanceSaveData modbusProtocolDisplayInstanceSaveData=gson.fromJson(data, type);
		
		if(modbusProtocolDisplayInstanceSaveData!=null){
			if(modbusProtocolDisplayInstanceSaveData.getDelidslist()!=null){
				for(int i=0;i<modbusProtocolDisplayInstanceSaveData.getDelidslist().size();i++){
					this.protocolDisplayInstanceManagerService.doModbusProtocolDisplayInstanceBulkDelete(modbusProtocolDisplayInstanceSaveData.getDelidslist().get(i));
					MemoryDataManagerTask.loadDisplayInstanceOwnItemById(modbusProtocolDisplayInstanceSaveData.getDelidslist().get(i),"delete");
				}
			}
			
			if(StringManagerUtils.isNotNull(modbusProtocolDisplayInstanceSaveData.getName())){
				ProtocolDisplayInstance protocolDisplayInstance=new ProtocolDisplayInstance();
				protocolDisplayInstance.setId(modbusProtocolDisplayInstanceSaveData.getId());
				protocolDisplayInstance.setCode(modbusProtocolDisplayInstanceSaveData.getCode());
				protocolDisplayInstance.setName(modbusProtocolDisplayInstanceSaveData.getName());
				protocolDisplayInstance.setDeviceType(modbusProtocolDisplayInstanceSaveData.getDeviceType());
				protocolDisplayInstance.setDisplayUnitId(modbusProtocolDisplayInstanceSaveData.getDisplayUnitId());
				if(StringManagerUtils.isNum(modbusProtocolDisplayInstanceSaveData.getSort())){
					protocolDisplayInstance.setSort(StringManagerUtils.stringToInteger(modbusProtocolDisplayInstanceSaveData.getSort()));
				}else{
					protocolDisplayInstance.setSort(null);
				}
				try {
					this.protocolDisplayInstanceManagerService.doModbusProtocolDisplayInstanceEdit(protocolDisplayInstance);
					MemoryDataManagerTask.loadDisplayInstanceOwnItemById(protocolDisplayInstance.getId()+"","update");
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
	
	@RequestMapping("/doModbusProtocolAlarmInstanceAdd")
	public String doModbusProtocolAlarmInstanceAdd(@ModelAttribute ProtocolAlarmInstance protocolAlarmInstance) throws IOException {
		String result = "";
		try {
			this.protocolAlarmInstanceManagerService.doModbusProtocolAlarmInstanceAdd(protocolAlarmInstance);
			MemoryDataManagerTask.loadAlarmInstanceOwnItemByCode(protocolAlarmInstance.getCode(),"update");
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
		Gson gson=new Gson();
		String json ="{success:true}";
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<ModbusProtocolAlarmInstanceSaveData>() {}.getType();
		ModbusProtocolAlarmInstanceSaveData modbusProtocolAlarmInstanceSaveData=gson.fromJson(data, type);
		
		if(modbusProtocolAlarmInstanceSaveData!=null){
			if(modbusProtocolAlarmInstanceSaveData.getDelidslist()!=null){
				for(int i=0;i<modbusProtocolAlarmInstanceSaveData.getDelidslist().size();i++){
					MemoryDataManagerTask.loadAlarmInstanceOwnItemById(modbusProtocolAlarmInstanceSaveData.getDelidslist().get(i),"delete");
					this.protocolAlarmInstanceManagerService.doModbusProtocolAlarmInstanceBulkDelete(modbusProtocolAlarmInstanceSaveData.getDelidslist().get(i));
				}
			}
			
			if(StringManagerUtils.isNotNull(modbusProtocolAlarmInstanceSaveData.getName())){
				ProtocolAlarmInstance protocolAlarmInstance=new ProtocolAlarmInstance();
				protocolAlarmInstance.setId(modbusProtocolAlarmInstanceSaveData.getId());
				protocolAlarmInstance.setCode(modbusProtocolAlarmInstanceSaveData.getCode());
				protocolAlarmInstance.setName(modbusProtocolAlarmInstanceSaveData.getName());
				protocolAlarmInstance.setDeviceType(modbusProtocolAlarmInstanceSaveData.getDeviceType());
				protocolAlarmInstance.setAlarmUnitId(modbusProtocolAlarmInstanceSaveData.getAlarmUnitId());
				if(StringManagerUtils.isNum(modbusProtocolAlarmInstanceSaveData.getSort())){
					protocolAlarmInstance.setSort(StringManagerUtils.stringToInteger(modbusProtocolAlarmInstanceSaveData.getSort()));
				}else{
					protocolAlarmInstance.setSort(null);
				}
				try {
					this.protocolAlarmInstanceManagerService.doModbusProtocolAlarmInstanceEdit(protocolAlarmInstance);
					MemoryDataManagerTask.loadAlarmInstanceOwnItemById(protocolAlarmInstance.getId()+"","update");
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
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String json = this.acquisitionUnitManagerService.getDatabaseColumnMappingTable(deviceType);
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
		boolean flag = this.acquisitionUnitManagerService.judgeProtocolExistOrNot(StringManagerUtils.stringToInteger(deviceType),protocolName);
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
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String instanceName = ParamUtils.getParameter(request, "instanceName");
		boolean flag = this.acquisitionUnitManagerService.judgeInstanceExistOrNot(StringManagerUtils.stringToInteger(deviceType),instanceName);
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
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String instanceName = ParamUtils.getParameter(request, "instanceName");
		boolean flag = this.acquisitionUnitManagerService.judgeAlarmInstanceExistOrNot(StringManagerUtils.stringToInteger(deviceType),instanceName);
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
		String deviceType = ParamUtils.getParameter(request, "deviceType");
		String instanceName = ParamUtils.getParameter(request, "instanceName");
		boolean flag = this.acquisitionUnitManagerService.judgeDisplayInstanceExistOrNot(StringManagerUtils.stringToInteger(deviceType),instanceName);
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
