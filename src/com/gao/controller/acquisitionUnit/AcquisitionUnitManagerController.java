package com.gao.controller.acquisitionUnit;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.gao.model.drive.KafkaConfig;
import com.gao.model.drive.ModbusDriverSaveData;
import com.gao.model.drive.RTUDriveConfig;
import com.gao.model.drive.TcpServerConfig;
import com.gao.model.drive.RTUDriveConfig.Item;
import com.gao.service.acquisitionUnit.AcquisitionUnitManagerService;
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
		unitName = ParamUtils.getParameter(request, "unitName");
		int intPage = Integer.parseInt((page == null || page == "0") ? "1": page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "10": limit);
		int offset = (intPage - 1) * pageSize;
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
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
	
	
	
	@RequestMapping("/doAcquisitionGroupAdd")
	public String doAcquisitionGroupAdd(@ModelAttribute AcquisitionGroup acquisitionGroup) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			this.acquisitionGroupManagerService.doAcquisitionGroupAdd(acquisitionGroup);
			EquipmentDriverServerTask.initAcquisitionUnit();
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
			EquipmentDriverServerTask.initAcquisitionUnit();
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
		int intPage = Integer.parseInt((page == null || page == "0") ? "1": page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "10": limit);
		int offset = (intPage - 1) * pageSize;
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("groupName", groupName);
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
//				break;
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
		// Gson g = new Gson();
		String groupId = ParamUtils.getParameter(request, "groupId");
		Gson g = new Gson();
		List<AcquisitionGroupItem> list = acquisitionUnitItemManagerService.showAcquisitionGroupOwnItems(AcquisitionGroupItem.class, groupId);
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
		// Gson g = new Gson();
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
		AcquisitionGroupItem r = null;
		try {
			String paramsIds = ParamUtils.getParameter(request, "paramsId");
			String matrixCodes = ParamUtils.getParameter(request, "matrixCodes");
			String groupId = ParamUtils.getParameter(request, "groupId");
			log.debug("grantAcquisitionItemsPermission paramsIds==" + paramsIds);
			
			String groupIds[] = StringManagerUtils.split(paramsIds, ",");
			//String oldModuleId[] = StringManagerUtils.split(oldModuleIds, ",");
			if (groupIds.length > 0 && groupId != null) {
				this.acquisitionUnitItemManagerService.deleteCurrentAcquisitionGroupOwnItems(groupId);
				if (matrixCodes != "" || matrixCodes != null) {
					String module_matrix[] = matrixCodes.split("\\|");
					for (int i = 0; i < module_matrix.length; i++) {
						String module_[] = module_matrix[i].split("\\:");
						r = new AcquisitionGroupItem();
						r.setGroupId(Integer.parseInt(groupId));
						log.debug("unitId==" + groupId);
						r.setItemId(StringManagerUtils.stringTransferInteger(module_[0]));
						r.setMatrix(module_[1]);
						this.acquisitionUnitItemManagerService.grantAcquisitionItemsPermission(r);

					}
					EquipmentDriverServerTask.initAcquisitionUnit();
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
			//String oldModuleId[] = StringManagerUtils.split(oldModuleIds, ",");
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
					EquipmentDriverServerTask.initAcquisitionUnit();
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
	
	@RequestMapping("/getDriverConfigData")
	public String getDriverConfigData() throws Exception {
		String json = "";
		
		json = acquisitionUnitItemManagerService.getDriverConfigData();
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
	
	@RequestMapping("/getTcpServerConfigData")
	public String getTcpServerConfigData() throws Exception {
		String json = "";
		
		json = acquisitionUnitItemManagerService.getTcpServerConfigData();
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
	
	@RequestMapping("/saveModbusDriverConfigData")
	public String saveModbusDriverConfigData() throws Exception {
		String json = "";
		Gson gson = new Gson();
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String fileName="";
		String driverCode="";
		String data = ParamUtils.getParameter(request, "data");
		java.lang.reflect.Type type = new TypeToken<ModbusDriverSaveData>() {}.getType();
		ModbusDriverSaveData modbusDriverSaveData=gson.fromJson(data, type);
		if(modbusDriverSaveData!=null){
			String path=stringManagerUtils.getFilePath("TcpServerConfig.json","dirverConfig/");
			Map<String, Object> tcpServerConfigMap = TcpServerConfigMap.getMapObject();
			if(tcpServerConfigMap==null || tcpServerConfigMap.get("TcpServerConfig")==null){
				String TcpServerConfigData="";
				TcpServerConfigData=stringManagerUtils.readFile(path,"utf-8");
				type = new TypeToken<TcpServerConfig>() {}.getType();
				TcpServerConfig tcpServerConfig=gson.fromJson(TcpServerConfigData, type);
				tcpServerConfigMap.put("TcpServerConfig", tcpServerConfig);
			}
			TcpServerConfig tcpServerConfig=(TcpServerConfig) tcpServerConfigMap.get("TcpServerConfig");
			if(tcpServerConfig.getPort()!=modbusDriverSaveData.getTcpServerPort() || !tcpServerConfig.getHeartbeatPacket().equals(modbusDriverSaveData.getTcpServerHeartbeatPacket())){
				tcpServerConfig.setPort(modbusDriverSaveData.getTcpServerPort());
				tcpServerConfig.setHeartbeatPacket(modbusDriverSaveData.getTcpServerHeartbeatPacket());
				StringManagerUtils.writeFile(path,StringManagerUtils.jsonStringFormat(gson.toJson(tcpServerConfig)));
				tcpServerConfigMap.put("TcpServerConfig", tcpServerConfig);
				
				if(EquipmentDriverServerTask.serverSocket!=null && !EquipmentDriverServerTask.serverSocket.isClosed()){
					EquipmentDriverServerTask.serverSocket.close();
				}
			}
			
			
			Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
			if(equipmentDriveMap.size()==0){
				EquipmentDriverServerTask.initDriverConfig();
				equipmentDriveMap = EquipmentDriveMap.getMapObject();
			}
			switch (modbusDriverSaveData.getDriverName()){
			case "A11协议_安控":
				fileName="EtrolDriverConfig.json";
				driverCode="EtrolDrive";
				break;
			case "A11协议_必创":
				fileName="BeeTechDriverConfig.json";
				driverCode="BeeTech";
				break;
			case "A11协议_日月":
				fileName="SunMoonDriverConfig.json";
				driverCode="SunMoonStandardDrive";
				break;
			case "A11协议_中科奥维":
				fileName="ZKAWDriverConfig.json";
				driverCode="ZKAWDrive";
				break;
			case "A11协议":
				fileName="CNPCStandardDriverConfig.json";
				driverCode="CNPCStandardDrive";
				break;
			case "四化协议":
				fileName="SinoepcStandardDriverConfig.json";
				driverCode="SinoepcStandardDrive";
				break;
			}
			
			if(StringManagerUtils.isNotNull(fileName)&&StringManagerUtils.isNotNull(driverCode)){
				RTUDriveConfig driveConfig=(RTUDriveConfig)equipmentDriveMap.get(driverCode);
				path=stringManagerUtils.getFilePath(fileName,"dirverConfig/");
				if(driveConfig==null){
					String driverConfigData=stringManagerUtils.readFile(path,"utf-8");
					type = new TypeToken<RTUDriveConfig>() {}.getType();
					driveConfig=gson.fromJson(driverConfigData, type);
				}
				
				for(int i=0;i<modbusDriverSaveData.getDataConfig().size();i++){
					int dataType=1;
					if("整型".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getDataType())){
						dataType=1;
					}else if("实型".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getDataType())){
						dataType=2;
					}else if("BCD码".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getDataType())){
						dataType=3;
					}
					RTUDriveConfig.Item item=new Item();
					item.setAddress(modbusDriverSaveData.getDataConfig().get(i).getAddress());
					item.setLength(modbusDriverSaveData.getDataConfig().get(i).getLength());
					item.setZoom(modbusDriverSaveData.getDataConfig().get(i).getZoom());
					item.setDataType(dataType);
					if("运行状态".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setRunStatus(item);
					}else if("启停控制".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setRunControl(item);
					}else if("A相电流".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setCurrentA(item);
					}else if("B相电流".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setCurrentB(item);
					}else if("C相电流".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setCurrentC(item);
					}else if("A相电压".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setVoltageA(item);
					}else if("B相电压".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setVoltageB(item);
					}else if("C相电压".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setVoltageC(item);
					}else if("有功功耗".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setActivePowerConsumption(item);
					}else if("无功功耗".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setReactivePowerConsumption(item);
					}else if("有功功率".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setActivePower(item);
					}else if("无功功率".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setReactivePower(item);
					}else if("反向功率".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setReversePower(item);
					}else if("功率因数".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setPowerFactor(item);
					}else if("油压".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setTubingPressure(item);
					}else if("套压".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setCasingPressure(item);
					}else if("回压".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setBackPressure(item);
					}else if("井口流温".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setWellHeadFluidTemperature(item);
					}else if("动液面".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setProducingfluidLevel(item);
					}else if("含水率".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setWaterCut(item);
					}else if("变频设置频率".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setSetFrequency(item);
					}else if("变频运行频率".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setRunFrequency(item);
					}else if("螺杆泵转速".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setRPM(item);
					}else if("螺杆泵扭矩".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setTorque(item);
					}else if("功图采集间隔".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setFSDiagramAcquisitionInterval(item);
					}else if("功图设置点数".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setFSDiagramSetPointCount(item);
					}else if("功图点数".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setFSDiagramPointCount(item);
					}else if("功图采集时间".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setAcqTime(item);
					}else if("冲次".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setSPM(item);
					}else if("冲程".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setStroke(item);
					}else if("功图数据-位移".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setSDiagram(item);
					}else if("功图数据-载荷".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setFDiagram(item);
					}else if("电流曲线".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setADiagram(item);
					}else if("功率曲线".equalsIgnoreCase(modbusDriverSaveData.getDataConfig().get(i).getName())){
						driveConfig.getDataConfig().setPDiagram(item);
					}
				}
				StringManagerUtils.writeFile(path,StringManagerUtils.jsonStringFormat(gson.toJson(driveConfig)));
				equipmentDriveMap.put(driveConfig.getDriverCode(), driveConfig);
			}
			
			
		}
		
		json ="{success:true}";
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
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
				EquipmentDriverServerTask.initDriverConfig();
				equipmentDriveMap = EquipmentDriveMap.getMapObject();
			}
			KafkaConfig driveConfig=(KafkaConfig)equipmentDriveMap.get("KafkaDrive");
			
			String path=stringManagerUtils.getFilePath("KafkaDriverConfig.json","dirverConfig/");
			if(driveConfig==null){
				String driverConfigData=stringManagerUtils.readFile(path,"utf-8");
				type = new TypeToken<KafkaConfig>() {}.getType();
				driveConfig=gson.fromJson(driverConfigData, type);
			}
			driveConfig.setServer(KafkaConfigSaveData.getServer());
			driveConfig.setTopic(KafkaConfigSaveData.getTopic());
			
			StringManagerUtils.writeFile(path,StringManagerUtils.jsonStringFormat(gson.toJson(driveConfig)));
			equipmentDriveMap.put(driveConfig.getDriverCode(), driveConfig);

//			System.out.println(((KafkaConfig)equipmentDriveMap.get("KafkaDrive")).getServer().getPort());
		}
		
		json ="{success:true}";
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getKafkaDriverConfigData")
	public String getKafkaDriverConfigData() throws Exception {
		String json = "";
		
		json = acquisitionUnitItemManagerService.getKafkaDriverConfigData();
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
