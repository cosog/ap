package com.cosog.controller.right;

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

import com.cosog.controller.base.BaseController;
import com.cosog.model.Role;
import com.cosog.model.User;
import com.cosog.service.right.RoleManagerService;
import com.cosog.utils.Constants;
import com.cosog.utils.Page;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;
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
			this.roleService.addRole(role);
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
			if(role.getRoleFlag()==null||role.getShowLevel()==null||role.getRoleLevel()==null){
				String sql="select t.role_level,t.showlevel,t.role_flag from tbl_role t where t.role_id="+role.getRoleId();
				List<?> list=this.roleService.findCallSql(sql);
				if(list.size()>0){
					Object[] obj=(Object[])list.get(0);
					if(role.getRoleLevel()==null&&list.size()>0){
						role.setRoleLevel(StringManagerUtils.stringToInteger(obj[0]+""));
					}
					if(role.getShowLevel()==null&&list.size()>0){
						role.setShowLevel(StringManagerUtils.stringToInteger(obj[1]+""));
					}
					if(role.getRoleFlag()==null&&list.size()>0){
						
						role.setRoleFlag(StringManagerUtils.stringToInteger(obj[2]+""));
					}
				}
			}
			
			this.roleService.modifyRole(role);
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
