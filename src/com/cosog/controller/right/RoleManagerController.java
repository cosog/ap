package com.cosog.controller.right;

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
import com.cosog.model.Module;
import com.cosog.model.Role;
import com.cosog.model.RoleModule;
import com.cosog.model.RoleDeviceType;
import com.cosog.model.DeviceTypeInfo;
import com.cosog.model.User;
import com.cosog.service.right.RoleManagerService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.BackModuleRecursion;
import com.cosog.utils.Constants;
import com.cosog.utils.Page;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.SessionLockHelper;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.DeviceTypeInfoRecursion;
import com.google.gson.Gson;

/** <p>描述：角色维护管理Action</p>
 * 
 * @author gao 2014-06-04
 *
 */
@Controller
@RequestMapping("/roleManagerController")
@Scope("prototype")
public class RoleManagerController extends BaseController {

	private static Log log = LogFactory.getLog(RoleManagerController.class);
	private static final long serialVersionUID = -281275682819237996L;
	@Autowired
	private RoleManagerService<Role> roleService;
	@Autowired
	private RoleManagerService<DeviceTypeInfo> roleTabInfoService;
	@Autowired
	private RoleManagerService<RoleModule> roleModuleService;
	@Autowired
	private RoleManagerService<RoleDeviceType> roleTabService;
	private List<Role> list;
	private Role role;
	private String limit;
	private String msg = "";
	private String roleName;
	private String page;

	//添加绑定前缀 
	@InitBinder("role")
	public void initBinder2(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("role.");
	}

	/**<p>描述：角儿添加</p>
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/doRoleAdd")
	public String doRoleAdd(@ModelAttribute Role role) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			String addModuleIds = ParamUtils.getParameter(request, "addModuleIds");
			String matrixCodes = ParamUtils.getParameter(request, "matrixCodes");
			String addDeviceTypeIds = ParamUtils.getParameter(request, "addDeviceTypeIds");
			this.roleService.addRole(role);
			
			if(StringManagerUtils.isNotNull(addModuleIds) || StringManagerUtils.isNotNull(addDeviceTypeIds)){
				String sql="select t.role_id from TBL_ROLE t where t.role_name='"+role.getRoleName()+"'";
				List<?> list=this.roleService.findCallSql(sql);
				if(list.size()>0){
					int addRoleId=StringManagerUtils.stringToInteger(list.get(0)+"");
					if(addRoleId>0){
						if(StringManagerUtils.isNotNull(addModuleIds)){
							String[] moduleIdArr=addModuleIds.split(",");
							if (moduleIdArr.length > 0){
								String module_matrix[] = matrixCodes.split("\\|");
								RoleModule r = null;
								for (int i = 0; i < module_matrix.length; i++) {
									String module_[] = module_matrix[i].split("\\:");
									if(module_.length==2){
										r=new RoleModule();
										r = new RoleModule();
										r.setRmRoleId(addRoleId);
										r.setRmModuleid(StringManagerUtils.stringToInteger(module_[0]));
										r.setRmMatrix(module_[1]);
										this.roleModuleService.saveOrUpdateRoleModule(r);
									}
								}
							}
						}
						
						if(StringManagerUtils.isNotNull(addDeviceTypeIds)){
							RoleDeviceType r=null;
							String[] deviceTypeIdArr=addDeviceTypeIds.split(",");
							for(int i=0;i<deviceTypeIdArr.length;i++){
								int deviceTypeId=StringManagerUtils.stringToInteger(deviceTypeIdArr[i]);
								if(deviceTypeId>0){
									r=new RoleDeviceType();
									r.setRdRoleId(addRoleId);
									r.setRdDeviceTypeId(deviceTypeId);
									r.setRdMatrix("0,0,0");
									this.roleTabService.saveOrUpdateRoleDeviceType(r);
								}
							}
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

	/**<p>描述：角色批量产量删除</p>
	 * 
	 * @return
	 */
	@RequestMapping("/doRoleBulkDelete")
	public String doRoleBulkDelete() {
		try {
			String RoleIds = ParamUtils.getParameter(request, "paramsId");
			this.roleService.bulkDelete(RoleIds);
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			String result = "{success:true,flag:true}";
			response.getWriter().print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**<p>描述：角色编辑</p>
	 * 
	 * @return
	 */
	@RequestMapping("/doRoleEdit")
	public String doRoleEdit(@ModelAttribute Role role) {
		String result ="{success:true,msg:false}";
		try {
			if(role.getShowLevel()==null||role.getRoleLevel()==null){
				String sql="select t.role_level,t.showlevel from tbl_role t where t.role_id="+role.getRoleId();
				List<?> list=this.roleService.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[])list.get(0);
					if(role.getRoleLevel()==null&&list.size()>0){
						role.setRoleLevel(StringManagerUtils.stringToInteger(obj[0]+""));
					}
					if(role.getShowLevel()==null&&list.size()>0){
						role.setShowLevel(StringManagerUtils.stringToInteger(obj[1]+""));
					}
				}
			}
			
			this.roleService.modifyRole(role);
			MemoryDataManagerTask.loadUserInfoByRoleId(role.getRoleId()+"", "update");
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
	
	@RequestMapping("/updateRoleInfo")
	public String updateRoleInfo() throws IOException {
		String result = "{success:true,flag:true}";
		try {
			boolean isLoginedUserRole=false;
			String roleId = ParamUtils.getParameter(request, "roleId");
			String roleName = ParamUtils.getParameter(request, "roleName");
			String roleLevel = ParamUtils.getParameter(request, "roleLevel");
			String roleVideoKeyEditName = ParamUtils.getParameter(request, "roleVideoKeyEditName");
			String showLevel = ParamUtils.getParameter(request, "showLevel");
			String remark = ParamUtils.getParameter(request, "remark");
			
			Role role=new Role();
			role.setRoleId(StringManagerUtils.stringToInteger(roleId));
			role.setRoleName(roleName);
			role.setRoleLevel(StringManagerUtils.stringToInteger(roleLevel));
			role.setRoleVideoKeyEdit("true".equalsIgnoreCase(roleVideoKeyEditName)?1:0);;
			role.setShowLevel(StringManagerUtils.stringToInteger(showLevel));
			role.setRemark(remark);
			
			log.debug("edit role ==" + role.getRoleId());
			
			
			
			HttpSession session=request.getSession();
			User prttentuser = (User) session.getAttribute("userLogin");
			//如果是当前登录用户角色
			if(prttentuser.getUserType()==role.getRoleId()){
				isLoginedUserRole=true;
			}
			boolean userIdChange=false;
			int r=this.roleService.updateRoleInfo(role,isLoginedUserRole);
			if(r==1){
				MemoryDataManagerTask.loadUserInfoByRoleId(role.getRoleId()+"", "update");
				result = "{success:true,flag:true}";
			}else if(r==2){
				result = "{success:true,flag:false}";
			}else{
				result = "{success:false,flag:false}";
			}
		} catch (Exception e) {
			result = "{success:false,flag:false}";
			e.printStackTrace();
		}
		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		response.getWriter().print(result);
		pw.flush();
		pw.close();
		return null;
	}

	/**<p>描述：角色模块显示方法</p>
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/doRoleShow")
	public String doRoleShow() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		roleName = ParamUtils.getParameter(request, "roleName");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		int intPage = Integer.parseInt((page == null || page == "0") ? "1": page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "10": limit);
		int offset = (intPage - 1) * pageSize;
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("roleName", roleName);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		this.pager = new Page("pagerForm", request);
		String json = this.roleService.getRoleList(map,pager,user);
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	/**<p>描述：显示权限配置里的角色列表信息</p>
	 * 
	 *  @author  gao 2014-06-04
	 * @return Null
	 * @throws IOException
	 *
	 * 
	 */
	@RequestMapping("/doShowRightRoles")
	public String doShowRightRoles() throws IOException {
		String json = "";
		Gson g = new Gson();
		// int orgId = ParamUtils.getIntParameter(request, "orgId", 303);
		list = roleService.loadRoles(Role.class);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(PagingConstants.TOTAL, 300000);
		jsonMap.put(PagingConstants.LIST, list);
		json = g.toJson(jsonMap);
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	/**<p>描述：显示权限配置里的当前用户拥有的角色信息</p>
	 * 
	 * @author gao 2014-06-04
	 * @return Null
	 * @throws IOException
	 * 
	 * 
	 */
	@RequestMapping("/doShowRightCurrentUsersOwnRoles")
	public String doShowRightCurrentUsersOwnRoles() throws IOException {

		int userNo = ParamUtils.getIntParameter(request, "userNo", -1);
		list = roleService.queryCurrentUserRoles(Role.class, userNo);

		String json = "";
		StringBuffer strBuf = new StringBuffer();
		if (list.size() > 0) {
			strBuf.append("[");
			for (int i = 0; i < list.size(); i++) {
				strBuf.append("{\"roleCode\":\"" + list.get(i)).append("\"}");
				if (i != list.size() - 1) {
					strBuf.append(",");
				}
			}
			strBuf.append("]");
		} else {
			strBuf.append("[]");
		}
		json = strBuf.toString();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/judgeRoleExistsOrNot")
	public String judgeRoleExistsOrNot() throws IOException {
		roleName = ParamUtils.getParameter(request, "roleName").replaceAll(" ", "");
		boolean flag = this.roleService.judgeRoleExistsOrNot(roleName,"");
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
	
	
	/**<p>描述：获取角色类型的下拉菜单数据信息</p>
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadRoleType")
	public String loadRoleType() throws Exception {

		String type = ParamUtils.getParameter(request, "type");
		String json = this.roleService.loadRoleType(type);
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
	
	@RequestMapping("/constructRightTabTreeGridTree")
	public String constructRightTabTreeGridTree() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		DeviceTypeInfoRecursion r = new DeviceTypeInfoRecursion();
		List<DeviceTypeInfo> list = this.roleTabInfoService.queryRightTabs(DeviceTypeInfo.class,user);
		boolean flag = false;
		for (DeviceTypeInfo tabInfo : list) {
			if (!r.hasParent(list, tabInfo)) {
				flag = true;
				json = r.recursionRightTabTreeFn(list, tabInfo);
//				break;
			}

		}
		if (flag == false && list.size() > 0) {
			for (DeviceTypeInfo tabInfo : list) {
				json = r.recursionRightTabTreeFn(list, tabInfo);
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
	
	@RequestMapping("/constructProtocolConfigTabTreeGridTree")
	public String constructProtocolConfigTabTreeGridTree() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		DeviceTypeInfoRecursion r = new DeviceTypeInfoRecursion();
		List<DeviceTypeInfo> list = this.roleTabInfoService.queryRightTabs(DeviceTypeInfo.class,user);
		boolean flag = false;
		for (DeviceTypeInfo tabInfo : list) {
			if (!r.hasParent(list, tabInfo)) {
				flag = true;
				json = r.recursionProtocolConfigTabTreeFn(list, tabInfo);
//				break;
			}
		}
		if (flag == false && list.size() > 0) {
			for (DeviceTypeInfo tabInfo : list) {
				json = r.recursionProtocolConfigTabTreeFn(list, tabInfo);
			}

		}
		json = r.modifyStr(json);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.debug("constructProtocolConfigTabTreeGridTree json==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/constructProtocolConfigTabTreeGridTreeWithoutRoot")
	public String constructProtocolConfigTabTreeGridTreeWithoutRoot() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		DeviceTypeInfoRecursion r = new DeviceTypeInfoRecursion();
		List<DeviceTypeInfo> list = this.roleTabInfoService.queryRightTabsWithoutRoot(DeviceTypeInfo.class,user);
		boolean flag = false;
		for (DeviceTypeInfo tabInfo : list) {
			if (!r.hasParent(list, tabInfo)) {
				flag = true;
				json = r.recursionProtocolConfigTabTreeFn(list, tabInfo);
//				break;
			}

		}
		if (flag == false && list.size() > 0) {
			for (DeviceTypeInfo tabInfo : list) {
				json = r.recursionProtocolConfigTabTreeFn(list, tabInfo);
			}

		}
		json = r.modifyStr(json);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.debug("constructProtocolConfigTabTreeGridTree json==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getRoleModuleRight")
	public String getRoleModuleRight() throws Exception {
		String json = "";
		HttpSession session=request.getSession();
		String moduleCode = ParamUtils.getParameter(request, "moduleCode");
		User user = (User) session.getAttribute("userLogin");
		
		json = this.roleTabInfoService.getRoleModuleRight(user,moduleCode);
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.debug("constructProtocolConfigTabTreeGridTree json==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getLoginUserRoleModules")
	public String getLoginUserRoleModules() throws Exception {
		String json = "[]";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		if(user!=null){
			json = user.getModuleList();
		}
		
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.debug("constructProtocolConfigTabTreeGridTree json==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public List<Role> getList() {
		return list;
	}

	public void setList(List<Role> list) {
		this.list = list;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
