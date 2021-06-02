package com.gao.controller.acquisitionUnit;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.gao.controller.base.BaseController;
import com.gao.model.AcquisitionGroup;
import com.gao.model.AcquisitionItem;
import com.gao.model.AcquisitionUnit;
import com.gao.model.AcquisitionUnitGroup;
import com.gao.model.AcquisitionGroupItem;
import com.gao.model.Module;
import com.gao.model.Role;
import com.gao.model.RoleModule;
import com.gao.model.User;
import com.gao.model.drive.KafkaConfig;
import com.gao.model.drive.ModbusDriverSaveData;
import com.gao.model.drive.ModbusProtocolConfig;
import com.gao.model.gridmodel.AcquisitionGroupHandsontableChangeData;
import com.gao.model.gridmodel.AcquisitionUnitHandsontableChangeData;
import com.gao.service.acquisitionUnit.AcquisitionUnitManagerService;
import com.gao.service.base.CommonDataService;
import com.gao.service.right.RoleManagerService;
import com.gao.task.EquipmentDriverServerTask;
import com.gao.utils.AcquisitionItemRecursion;
import com.gao.utils.BackModuleRecursion;
import com.gao.utils.Constants;
import com.gao.utils.EquipmentDriveMap;
import com.gao.utils.Page;
import com.gao.utils.PagingConstants;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.gao.utils.TcpServerConfigMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gao.task.EquipmentDriverServerTask;

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
	private AcquisitionUnitManagerService<AcquisitionItem> acquisitionItemManagerService;
	@Autowired
	private AcquisitionUnitManagerService<AcquisitionGroupItem> acquisitionUnitItemManagerService;
	@Autowired
	private CommonDataService service;
	private List<AcquisitionUnit> list;
	private AcquisitionUnit acquisitionUnit;
	private AcquisitionItem acquisitionItem;
	private AcquisitionGroup acquisitionGroup;
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

	/**<p>描述：采集类型数据显示方法</p>
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/doAcquisitionUnitShow")
	public String doAcquisitionUnitShow() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String protocolName = ParamUtils.getParameter(request, "protocolName");
		int intPage = Integer.parseInt((page == null || page == "0") ? "1": page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "10": limit);
		int offset = (intPage - 1) * pageSize;
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("protocolName", protocolName);
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
	
	@RequestMapping("/doAcquisitionGroupAdd")
	public String doAcquisitionGroupAdd(@ModelAttribute AcquisitionGroup acquisitionGroup) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			this.acquisitionGroupManagerService.doAcquisitionGroupAdd(acquisitionGroup);
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
	
	@RequestMapping("/doAcquisitionGroupEdit")
	public String doAcquisitionGroupEdit(@ModelAttribute AcquisitionGroup acquisitionGroup) {
		String result ="{success:true,msg:false}";
		try {
			this.acquisitionUnitManagerService.doAcquisitionGroupEdit(acquisitionGroup);
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
			out.print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "{success:false,msg:false}";
			out.print(result);
		}
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
			this.acquisitionUnitManagerService.doAcquisitionUnitBulkDelete(ids);
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
	 * <p>描述： 采集项分配时，构建模块树</p>
	 * 
	 * @author zhao 2018-11-07
	 * @return
	 * @throws IOException
	 *          
	 */
	@RequestMapping("/constructAcquisitionItemsTreeData")
	public String constructAcquisitionItemsTreeData() throws IOException {
		String json = "";
		AcquisitionItemRecursion r = new AcquisitionItemRecursion();
		List<AcquisitionItem> acquisitionItemlist = this.acquisitionItemManagerService.queryAcquisitionItemsData(AcquisitionItem.class);
		boolean flag = false;
		for (AcquisitionItem acquisitionItem : acquisitionItemlist) {
			if (acquisitionItem.getParentid() == 0) {
				flag = true;
				json = r.recursionAcquisitionItemTreeFn(acquisitionItemlist, acquisitionItem);
			}
		}
		json = r.modifyStr(json);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.debug("constructRightModuleTreeGridTree json==" + json);
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
			String groupCode = ParamUtils.getParameter(request, "groupCode");
			log.debug("grantAcquisitionItemsPermission params==" + params);
			String paramsArr[] = StringManagerUtils.split(params, ",");
			String groupId="";
			String groupIdSql="select t.id from tbl_acq_group_conf t where t.group_code='"+groupCode+"' ";
			List list = this.service.findCallSql(groupIdSql);
			if(list.size()>0){
				groupId=list.get(0).toString();
			}
			if (paramsArr.length > 0 && StringManagerUtils.isNotNull(groupId)) {
				this.acquisitionUnitItemManagerService.deleteCurrentAcquisitionGroupOwnItems(groupId);
				if (matrixCodes != "" || matrixCodes != null) {
					String module_matrix[] = matrixCodes.split("\\|");
					for (int i = 0; i < module_matrix.length; i++) {
						String module_[] = module_matrix[i].split("\\:");
						acquisitionGroupItem = new AcquisitionGroupItem();
						acquisitionGroupItem.setGroupId(Integer.parseInt(groupId));
						log.debug("groupCode==" + groupCode);
						acquisitionGroupItem.setItemName(module_[0]);
						acquisitionGroupItem.setMatrix(module_[1]);
						this.acquisitionUnitItemManagerService.grantAcquisitionItemsPermission(acquisitionGroupItem);
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
	 *             为当前采集单元安排采集组
	 */
	@RequestMapping("/grantAcquisitionGroupsPermission")
	public String grantAcquisitionGroupsPermission() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		AcquisitionUnitGroup r = null;
		try {
			String paramsIds = ParamUtils.getParameter(request, "paramsId");
			String matrixCodes = ParamUtils.getParameter(request, "matrixCodes");
			String unitId = ParamUtils.getParameter(request, "unitId");
			log.debug("grantAcquisitionItemsPermission paramsIds==" + paramsIds);
			String groupIds[] = StringManagerUtils.split(paramsIds, ",");
			if (groupIds.length > 0 && unitId != null) {
				this.acquisitionUnitItemManagerService.deleteCurrentAcquisitionUnitOwnGroups(unitId);
				if (matrixCodes != "" || matrixCodes != null) {
					String module_matrix[] = matrixCodes.split("\\|");
					for (int i = 0; i < module_matrix.length; i++) {
						String module_[] = module_matrix[i].split("\\:");
						r = new AcquisitionUnitGroup();
						r.setUnitId(Integer.parseInt(unitId));
						log.debug("unitId==" + unitId);
						r.setGroupId(StringManagerUtils.stringTransferInteger(module_[0]));
						r.setMatrix(module_[1]);
						this.acquisitionUnitItemManagerService.grantAcquisitionGroupsPermission(r);
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
	
	@RequestMapping("/getProtocolConfigData")
	public String getProtocolConfigData() throws Exception {
		String json = "";
		json = acquisitionUnitItemManagerService.getDriverConfigData();
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/saveModbusProtocolConfigData")
	public String SaveModbusProtocolConfigData() throws Exception {
		String json = "";
		Gson gson = new Gson();
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String fileName="ModbusProtocolConfig.json";
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<ModbusDriverSaveData>() {}.getType();
		ModbusDriverSaveData modbusDriverSaveData=gson.fromJson(data, type);
		if(modbusDriverSaveData!=null){
			String path=stringManagerUtils.getFilePath(fileName,"protocolConfig/");
			Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
			if(equipmentDriveMap.size()==0){
				EquipmentDriverServerTask.loadProtocolConfig();
				equipmentDriveMap = EquipmentDriveMap.getMapObject();
			}
			ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
			for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
				if(modbusDriverSaveData.getProtocolName().equalsIgnoreCase(modbusProtocolConfig.getProtocol().get(i).getName())){
					modbusDriverSaveData.setProtocolCode(modbusProtocolConfig.getProtocol().get(i).getCode());
					if(modbusDriverSaveData.getProtocolType().equalsIgnoreCase("modbus-tcp")){
						modbusProtocolConfig.getProtocol().get(i).setType(0);
					}else{
						modbusProtocolConfig.getProtocol().get(i).setType(1);
					}
					
					if(modbusDriverSaveData.getStoreMode().equalsIgnoreCase("大端")){
						modbusProtocolConfig.getProtocol().get(i).setStoreMode(0);;
					}else{
						modbusProtocolConfig.getProtocol().get(i).setStoreMode(1);
					}
					modbusProtocolConfig.getProtocol().get(i).setSignInPrefix(modbusDriverSaveData.getSignInPrefix());
					modbusProtocolConfig.getProtocol().get(i).setSignInSuffix(modbusDriverSaveData.getSignInSuffix());
					modbusProtocolConfig.getProtocol().get(i).setHeartbeatPrefix(modbusDriverSaveData.getHeartbeatPrefix());
					modbusProtocolConfig.getProtocol().get(i).setHeartbeatSuffix(modbusDriverSaveData.getHeartbeatSuffix());
					
					for(int j=0;j<modbusDriverSaveData.getDataConfig().size();j++){
						for(int k=0;k<modbusProtocolConfig.getProtocol().get(i).getItems().size();k++){
							if(modbusProtocolConfig.getProtocol().get(i).getItems().get(k).getName().equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getName())){
								int dataType=1;
								if("整型".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getDataType())){
									dataType=1;
								}else if("实型".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getDataType())){
									dataType=2;
								}else if("BCD码".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getDataType())){
									dataType=3;
								}
								boolean initiative=true;
								if("主动轮询".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getInitiative())){
									initiative=true;
								}else if("被动接收".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getInitiative())){
									initiative=false;
								}
								boolean readonly=true;
								if("读写".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getReadonly())){
									readonly=false;
								}else if("只读".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(j).getReadonly())){
									readonly=true;
								}
								modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setAddr(modbusDriverSaveData.getDataConfig().get(j).getAddress());
								modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setQuantity(modbusDriverSaveData.getDataConfig().get(j).getLength());
								modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setUnit(modbusDriverSaveData.getDataConfig().get(j).getUnit());
								modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setRatio(modbusDriverSaveData.getDataConfig().get(j).getZoom());
								modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setDataType(dataType);
								modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setRWType(readonly);
								modbusProtocolConfig.getProtocol().get(i).getItems().get(k).setAcqMode(initiative);
								break;
							}
						}
					}
					break;
				}
			}
//			System.out.println(gson.toJson(modbusProtocolConfig));
			StringManagerUtils.writeFile(path,StringManagerUtils.jsonStringFormat(gson.toJson(modbusProtocolConfig)));
			equipmentDriveMap.put("modbusProtocolConfig", modbusProtocolConfig);
			EquipmentDriverServerTask.initProtocolConfig(modbusDriverSaveData.getProtocolCode());
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
	
	@RequestMapping("/saveKafkaDriverConfigData")
	public String saveKafkaDriverConfigData() throws Exception {
		String json = "";
		Gson gson = new Gson();
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String KafkaData = ParamUtils.getParameter(request, "KafkaData");
		java.lang.reflect.Type type = new TypeToken<KafkaConfig>() {}.getType();
		KafkaConfig KafkaConfigSaveData=gson.fromJson(KafkaData, type);
		if(KafkaConfigSaveData!=null){
			Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
			if(equipmentDriveMap.size()==0){
				EquipmentDriverServerTask.loadProtocolConfig();
				equipmentDriveMap = EquipmentDriveMap.getMapObject();
			}
			KafkaConfig driveConfig=(KafkaConfig)equipmentDriveMap.get("KafkaDrive");
			
			String path=stringManagerUtils.getFilePath("KafkaDriverConfig.json","protocolConfig/");
			if(driveConfig==null){
				String driverConfigData=stringManagerUtils.readFile(path,"utf-8");
				type = new TypeToken<KafkaConfig>() {}.getType();
				driveConfig=gson.fromJson(driverConfigData, type);
			}
			driveConfig.setServer(KafkaConfigSaveData.getServer());
			driveConfig.setTopic(KafkaConfigSaveData.getTopic());
			StringManagerUtils.writeFile(path,StringManagerUtils.jsonStringFormat(gson.toJson(driveConfig)));
			equipmentDriveMap.put(driveConfig.getProtocolCode(), driveConfig);
		}
		json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping("/saveAcquisitionUnitHandsontableData")
	public String saveAcquisitionUnitHandsontableData() throws Exception {
		HttpSession session=request.getSession();
		String data = ParamUtils.getParameter(request, "data").replaceAll("&nbsp;", "").replaceAll(" ", "").replaceAll("null", "");
		String protocol = ParamUtils.getParameter(request, "protocol");
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<AcquisitionUnitHandsontableChangeData>() {}.getType();
		AcquisitionUnitHandsontableChangeData acquisitionUnitHandsontableChangeData=gson.fromJson(data, type);
//		this.acquisitionUnitItemManagerService.saveAcquisitionUnitHandsontableData(acquisitionUnitHandsontableChangeData);
		if(acquisitionUnitHandsontableChangeData!=null){
			if(acquisitionUnitHandsontableChangeData.getDelidslist()!=null){
				for(int i=0;i<acquisitionUnitHandsontableChangeData.getDelidslist().size();i++){
					this.acquisitionUnitManagerService.doAcquisitionUnitBulkDelete(acquisitionUnitHandsontableChangeData.getDelidslist().get(i));
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
//		EquipmentDriverServerTask beeTechDriverServerTast=EquipmentDriverServerTask.getInstance();
//		beeTechDriverServerTast.updateWellConfif(wellHandsontableChangedData);
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
				}
			}
			if(acquisitionGroupHandsontableChangeData.getUpdatelist()!=null){
				for(int i=0;i<acquisitionGroupHandsontableChangeData.getUpdatelist().size();i++){
					AcquisitionGroup acquisitionGroup=new AcquisitionGroup();
					acquisitionGroup.setId(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getId()));
					acquisitionGroup.setGroupCode(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getGroupCode());
					acquisitionGroup.setGroupName(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getGroupName());
					acquisitionGroup.setAcqCycle(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getAcqCycle()));
					acquisitionGroup.setSaveCycle(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getSaveCycle()));
					acquisitionGroup.setRemark(acquisitionGroupHandsontableChangeData.getUpdatelist().get(i).getRemark());
					acquisitionGroup.setProtocol(protocol);
					this.acquisitionUnitManagerService.doAcquisitionGroupEdit(acquisitionGroup);
				}
			}
			
			if(acquisitionGroupHandsontableChangeData.getInsertlist()!=null){
				for(int i=0;i<acquisitionGroupHandsontableChangeData.getInsertlist().size();i++){
					AcquisitionGroup acquisitionGroup=new AcquisitionGroup();
					acquisitionGroup.setId(StringManagerUtils.stringToInteger(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getId()));
					acquisitionGroup.setGroupCode(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getGroupCode());
					acquisitionGroup.setGroupName(acquisitionGroupHandsontableChangeData.getInsertlist().get(i).getGroupName());
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
	
	
	
	@RequestMapping("/getKafkaDriverConfigData")
	public String getKafkaDriverConfigData() throws Exception {
		String json = "";
		json = acquisitionUnitItemManagerService.getKafkaDriverConfigData();
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

	public List<AcquisitionUnit> getList() {
		return list;
	}

	public void setList(List<AcquisitionUnit> list) {
		this.list = list;
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

	public AcquisitionItem getAcquisitionItem() {
		return acquisitionItem;
	}

	public void setAcquisitionItem(AcquisitionItem acquisitionItem) {
		this.acquisitionItem = acquisitionItem;
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
}
