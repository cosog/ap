package com.cosog.controller.right;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.RoleModule;
import com.cosog.model.RoleDeviceType;
import com.cosog.model.DeviceTypeInfo;
import com.cosog.service.right.ModuleManagerService;
import com.cosog.utils.Constants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

/**
 * <p>描述：权限管理Action</p>
 * 
 * @author gao 2014-05-09
 *
 */
@Controller
@RequestMapping("/moduleShowRightManagerController")
@Scope("prototype")
public class ModuleShowRightManagerController extends BaseController implements Serializable {
	private static Log log = LogFactory.getLog(ModuleShowRightManagerController.class);
	private static final long serialVersionUID = -281275682819237996L;
	private List<RoleModule> list;
	private ModuleManagerService<RoleModule> moduleService;
	private ModuleManagerService<RoleDeviceType> roleTabService;
	private RoleModule roleModule;

	/**
	 * @return NUll
	 * @throws IOException
	 *             为当前角色分配权限
	 */
	@RequestMapping("/doModuleSaveOrUpdate")
	public String doModuleSaveOrUpdate() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		RoleModule r = null;
		try {
			String moduleIds = ParamUtils.getParameter(request, "paramsId");
			String matrixCodes = ParamUtils.getParameter(request, "matrixCodes");
			log.debug("doModuleSaveOrUpdate moduleIds==" + moduleIds);
			String roleId = ParamUtils.getParameter(request, "roleId");
			String moduleId[] = StringManagerUtils.split(moduleIds, ",");
			if (roleId != null) {
				this.moduleService.deleteCurrentModuleByRoleCode(roleId);
				if (moduleId.length > 0 && matrixCodes != "" && matrixCodes != null) {
					String module_matrix[] = matrixCodes.split("\\|");
					for (int i = 0; i < module_matrix.length; i++) {
						String module_[] = module_matrix[i].split("\\:");
						r = new RoleModule();
						r.setRmRoleId(Integer.parseInt(roleId));
						log.debug("roleCode==" + roleId);
						r.setRmModuleid(StringManagerUtils.stringTransferInteger(module_[0]));
						r.setRmMatrix(module_[1]);
						this.moduleService.saveOrUpdateModule(r);
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
	 *             为当前角色分配权限
	 */
	@RequestMapping("/doRoleDeviceTypeSaveOrUpdate")
	public String doRoleDeviceTypeSaveOrUpdate() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		RoleDeviceType r = null;
		try {
			String deviceTypeIds = ParamUtils.getParameter(request, "paramsId");
			String matrixCodes = ParamUtils.getParameter(request, "matrixCodes");
			log.debug("doModuleSaveOrUpdate moduleIds==" + deviceTypeIds);
			String roleId = ParamUtils.getParameter(request, "roleId");
			String deviceTypeId[] = StringManagerUtils.split(deviceTypeIds, ",");
			if (roleId != null) {
				this.moduleService.deleteCurrentTabByRoleCode(roleId);
				if (deviceTypeId.length > 0 && matrixCodes != "" && matrixCodes != null) {
					String tab_matrix[] = matrixCodes.split("\\|");
					for (int i = 0; i < tab_matrix.length; i++) {
						String deviceType_[] = tab_matrix[i].split("\\:");
						r = new RoleDeviceType();
						r.setRdRoleId(Integer.parseInt(roleId));
						log.debug("roleCode==" + roleId);
						r.setRdDeviceTypeId(StringManagerUtils.stringTransferInteger(deviceType_[0]));
						r.setRdMatrix(deviceType_[1]);
						this.roleTabService.saveOrUpdateRoleDeviceType(r);
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
	 * @return Null
	 * @throws IOException
	 * @author 显示权限配置里的当前角色拥有的模块信息
	 * 
	 */
	@RequestMapping("/doShowCurrentRoleOwnModulesMatrix")
	public String doShowCurrentRoleOwnModulesMatrix() throws IOException {
		// Gson g = new Gson();
		String roleCode = ParamUtils.getParameter(request, "roleCode");

		list = moduleService.queryCurrentRoleMatrixModules(RoleModule.class,
				roleCode);
		String json = "";
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("[");
		for (int i = 0; i < list.size(); i++) {
			strBuf.append("{\"rmMatrix\":" + list.get(i)).append("}");
			if (i != list.size() - 1) {
				strBuf.append(",");
			}
		}
		strBuf.append("]");
		json = strBuf.toString();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.debug("doShowCurrentRoleOwnModulesMatrix ***json==****" + json);
		pw.flush();
		pw.close();
		return null;
	}

	/**
	 * @return Null
	 * @throws IOException
	 * @author 显示权限配置里的当前角色拥有的模块信息
	 * 
	 */
	@RequestMapping("/doShowRightCurrentRoleOwnModules")
	public String doShowRightCurrentRoleOwnModules() throws IOException {
		// Gson g = new Gson();
		String roleId = ParamUtils.getParameter(request, "roleId");
		Gson g = new Gson();
		list = moduleService.queryCurrentRoleModules(RoleModule.class, roleId);
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
	 * @author 显示权限配置里的当前角色拥有的标签信息
	 * 
	 */
	@RequestMapping("/doShowRightCurrentRoleOwnTabs")
	public String doShowRightCurrentRoleOwnTabs() throws IOException {
		// Gson g = new Gson();
		String roleId = ParamUtils.getParameter(request, "roleId");
		Gson g = new Gson();
		List<RoleDeviceType> list = roleTabService.queryCurrentRoleDeviceTypes(RoleDeviceType.class, roleId);
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

	public List<RoleModule> getList() {
		return list;
	}

	public ModuleManagerService<RoleModule> getModuleService() {
		return moduleService;
	}

	public RoleModule getRoleModule() {
		return roleModule;
	}

	public void setList(List<RoleModule> list) {
		this.list = list;
	}

	@Resource
	public void setModuleService(ModuleManagerService<RoleModule> moduleService) {
		this.moduleService = moduleService;
	}

	public void setRoleModule(RoleModule roleModule) {
		this.roleModule = roleModule;
	}

	public ModuleManagerService<RoleDeviceType> getRoleDeviceTypeService() {
		return roleTabService;
	}

	@Resource
	public void setRoleDeviceTypeService(ModuleManagerService<RoleDeviceType> roleTabService) {
		this.roleTabService = roleTabService;
	}
}
