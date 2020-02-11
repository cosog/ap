package com.gao.controller.right;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

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

import com.gao.common.exception.ActionException;
import com.gao.controller.base.BaseController;
import com.gao.model.Module;
import com.gao.model.Org;
import com.gao.model.User;
import com.gao.service.data.SystemdataInfoService;
import com.gao.service.right.ModuleManagerService;
import com.gao.service.right.OrgManagerService;
import com.gao.service.right.UserManagerService;
import com.gao.utils.Constants;
import com.gao.utils.Message;
import com.gao.utils.Page;
import com.gao.utils.PagingConstants;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.gao.utils.UnixPwdCrypt;
import com.google.gson.Gson;

/**
 * <p>
 * 描述：用户管理模块的相关操作
 * </p>
 * 
 * @author gao 2014-05-08
 * 
 */
@Controller
@RequestMapping("/userManagerController")
@Scope("prototype")
public class UserManagerController extends BaseController {
	private static Log log = LogFactory.getLog(UserManagerController.class);
	private static final long serialVersionUID = -281275682819237996L;
	private String limit;
	private String orgId;
	private String page;
	private User user;
	private String[] userNos;
	private List<User> users;
	@Autowired
	private UserManagerService<User> userService;
	@Autowired
	private OrgManagerService<?> orgManagerService;
	@Autowired
	private OrgManagerService<Org> orgService;
	@Autowired
	private ModuleManagerService<Module> modService;

	
	//添加绑定前缀 
	@InitBinder("user")
	public void initBinderByUser(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("user.");
	}
		
	/**
	 * <p>根据传入的orgId 显示某个组织下的用户信息</p>
	 * 
	 * @return Null
	 * @throws IOException
	 * @author gao  2014-05-08
	 * 
	 * 
	 */
	@RequestMapping("/doShowRightUsers")
	public String doShowRightUsers() throws IOException {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		orgId = findCurrentUserOrgIdInfo(orgId);
		Gson g = new Gson();
		users = userService.queryUsersByOrgId(orgId, User.class);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(PagingConstants.TOTAL, 300000);
		jsonMap.put(PagingConstants.LIST, users);
		json = g.toJson(jsonMap);
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}

	/** <P>描述：用户管理——新增用户</p>
	 * @author gao 2014-05-08
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/doUserAdd")
	public String doUserAdd(@ModelAttribute User user) throws IOException {
		String result = "{success:true,msg:false}";
		try {
			log.debug("userTitle" + user.getUserTitle());
			user.setUserPwd(UnixPwdCrypt.crypt("dogVSgod", user.getUserPwd()));
			this.userService.addUser(user);
			
//			HttpSession session=request.getSession();
//			User userLogin = (User) session.getAttribute("userLogin");
//			orgId = "" + userLogin.getUserorgids();
//			
//			userLogin.setOrgtreeid(orgManagerService.findOrgById(user.getUserOrgid()));
//			userLogin.setUserParentOrgids(orgService.findParentIds(user.getUserOrgid()));
//			userLogin.setUserorgids(orgService.findChildIds(user.getUserOrgid()));
//			userLogin.setUserOrgNames(orgService.findChildNames(user.getUserOrgid()));
//			userLogin.setAllOrgPatentNodeIds(orgService.fingAllOrgParentNodeIds());
//			userLogin.setAllModParentNodeIds(modService.fingAllModParentNodeIds());
//			
//			userLogin = (User) session.getAttribute("userLogin");
//			orgId = "" + userLogin.getUserorgids();
			
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.getWriter().print(result);
		} catch (Exception e) {
			response.getWriter().print(result);
			e.printStackTrace();
			new ActionException("新增用户失败！");
		}
		return null;
	}

	/** <P>描述：用户管理——批量删除用户信息</p>
	 * @return
	 */
	@RequestMapping("/doUserBulkDelete")
	public String doUserBulkDelete() {
		try {
			// log.debug("userNos==" + userNos.length);
			String userNos = ParamUtils.getParameter(request, "paramsId");
			this.userService.bulkDelete(userNos);
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			String result = "{success:true,flag:true}";
			response.getWriter().print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/doUserDelete")
	public String doUserDelete() {
		try {
			int myUserNo = ParamUtils.getIntParameter(request, "userNo", 0);
			log.debug("userNo==" + myUserNo);
			user.setUserName(ParamUtils.getParameter(request, "userName"));
			user.setUserId(ParamUtils.getParameter(request, "userId"));
			user.setUserPwd(ParamUtils.getParameter(request, "userPwd"));
			user.setUserPhone(ParamUtils.getParameter(request, "userPhone"));
			user.setUserInEmail(ParamUtils.getParameter(request, "userInEmail"));
			user.setUserTitle(ParamUtils.getParameter(request, "userTitle"));
			user.setUserOrgid(303);
			user.setUserRegtime(StringManagerUtils.stringToTimeStamp(ParamUtils.getParameter(request, "userRegtime")));
			this.userService.deleteUser(myUserNo, User.class);
			String result = "ok";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.getWriter().print(JSONObject.fromObject(new Message(result)));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <P>用户模块管理——编辑用户信息</p>
	 * @author  gao 2014-05-08
	 * @return
	 */
	@RequestMapping("/doUserEdit")
	public String doUserEdit(@ModelAttribute User user) {
		try {
			log.debug("edit user success==" + user.getUserNo());
			String userOldPass = ParamUtils.getParameter(request, "userPass");
			if (!userOldPass.equals(user.getUserPwd())) {
				String newPass = UnixPwdCrypt.crypt("dogVSgod", user.getUserPwd());
				user.setUserPwd(newPass);
			}
//			this.userService.modifyUser(user);
			HttpSession session=request.getSession();
			User prttentuser = (User) session.getAttribute("userLogin");
			this.userService.modifyUser(user);
			if(user.getUserName().equals(prttentuser.getUserName())&&user.getUserPwd().equals(prttentuser.getUserPwd())){
				prttentuser.setUserOrgid(user.getUserOrgid());
			}
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			String result = "{success:true,msg:true}";
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

	/** <P>用户模块管理——创建Ext需要的json数据信息</p>	 
	 * @author  gao 2014-05-08
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 */
	@RequestMapping("/doUserShow")
	public String doUserShow() throws IOException, SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpSession session=request.getSession();
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((limit == null || limit == "0") ? "20" : limit);
		String userName = ParamUtils.getParameter(request, "userName");
		orgId = ParamUtils.getParameter(request, "orgId");
		if(!StringManagerUtils.isNotNull(orgId)){
			User user = (User) session.getAttribute("userLogin");
			orgId = "" + user.getUserorgids();
		}
		int offset = (intPage - 1) * pageSize;
		map.put(PagingConstants.PAGE_NO, intPage);
		map.put(PagingConstants.PAGE_SIZE, pageSize);
		map.put(PagingConstants.OFFSET, offset);
		map.put("userName", userName);
		this.pager = new Page("pagerForm", request);
		log.debug("intPage==" + intPage + " pageSize===" + pageSize);
		String json = userService.doUserShow(pager, map,orgId);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	/** <P>判断在用户表中是否存在当前新加的用户账号</p>	 
	 * @author  gao 2014-05-08
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/judgeUserExistOrNot")
	public String judgeUserExistOrNot() throws IOException {
		String userId = ParamUtils.getParameter(request, "userId");
		boolean flag = this.userService.judgeUserExistsOrNot(userId);
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

	@RequestMapping("/loadUserTitleType")
	public String loadUserTitleType() throws Exception {
		String type = ParamUtils.getParameter(request, "type");
		String json = this.userService.loadUserTitleType(type);
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
	
	/**<p>描述：获取用户类型的下拉菜单数据信息</p>
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadUserType")
	public String loadUserType() throws Exception {

		String type = ParamUtils.getParameter(request, "type");
		String json = this.userService.loadUserType(type);
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
	
	/**<P>构建闭环报警里的专业班组的下拉菜单树数据信息</p>	 
	 * @author  gao 2014-5-08
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sendZYBZTitleType")
	public String sendZYBZTitleType() throws Exception {

		String type = ParamUtils.getParameter(request, "type");
		String json = this.userService.sendZYBZTitleType(type);
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
	public String getLimit() {
		return limit;
	}

	public String getOrgId() {
		return orgId;
	}

	public String getPage() {
		return page;
	}

	public User getUser() {
		return user;
	}

	public String[] getUserNos() {
		return userNos;
	}

	public List<User> getUsers() {
		return users;
	}



	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserNos(String[] userNos) {
		this.userNos = userNos;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}