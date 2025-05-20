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
import com.cosog.model.Module;
import com.cosog.model.User;
import com.cosog.service.right.ModuleManagerService;
import com.cosog.utils.BackModuleRecursion;
import com.cosog.utils.BackModuleTreePanelRecursion;
import com.cosog.utils.Constants;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.ParamUtils;
import com.google.gson.Gson;

/**
 * <p>描述：模块维护action</p>
 * 
 * @author gao 2014-05-09
 *@version 1.0
 */
@Controller
@RequestMapping("/moduleManagerController")
@Scope("prototype")
public class ModuleManagerController extends BaseController {
	private static Log log = LogFactory.getLog(ModuleManagerController.class);
	private static final long serialVersionUID = -281275682819237996L;
	private List<Module> list;
	private Module module;
	@Autowired
	private ModuleManagerService<Module> moduleService;
	
	//添加绑定前缀 
	@InitBinder("module")
	public void initBinder2(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("module.");
	}

	
	/**
	 * <p>描述： 构建模块树的treePanel json</p>
	 * 
	 * @author gao 2014-05-09
	 * @return
	 * @throws IOException
	 *          
	 */
	@RequestMapping("/constructModuleTreeGridTree")
	public String constructModuleTreeGridTree() throws IOException {
		String moduleName = ParamUtils.getParameter(request, "moduleName");
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		List<?>list = this.moduleService.queryModules(Module.class, moduleName,user);
		String json = "";
		BackModuleTreePanelRecursion r = new BackModuleTreePanelRecursion();
		if (list != null) {
			for (Object org : list) {
				Object[] obj=(Object[])org;
				if (!r.hasParent(list, obj)) {
					json = r.recursionModuleTreeFn(list, obj,user.getLanguageName());
				}
			}
		}
		json = r.modifyStr(json);
		json=	this.getArrayTojsonPage(json,"module_ModuleManage",dictDeviceType,user.getLanguageName());
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.debug("constructModuleTreeGridTree json==" + json);
		pw.flush();
		pw.close();
		return null;
	}

	/**
	 * <p>描述： 权限分配时，构建模块树</p>
	 * 
	 * @author gao 2014-05-09
	 * @return
	 * @throws Exception 
	 *          
	 */
	@RequestMapping("/constructRightModuleTreeGridTree")
	public String constructRightModuleTreeGridTree() throws Exception {
		String moduleName = ParamUtils.getParameter(request, "moduleName");
		String json = "";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		BackModuleRecursion r = new BackModuleRecursion();
		list = this.moduleService.queryRightModules(Module.class, moduleName,user);
		boolean flag = false;
		for (Module module : list) {
			if (!r.hasParent(list, module)) {
				flag = true;
				json = r.recursionRightModuleTreeFn(list, module,language);
				break;
			}

		}
		if (flag == false && list.size() > 0) {
			for (Module module : list) {
				json = r.recursionRightModuleTreeFn(list, module,language);
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
	 * <p>描述：模块维护——模块新增</p>
	 * 
	 * @author   gao 2014-05-09
	 * @return
	 */
	@RequestMapping("/doModuleAdd")
	public String doModuleAdd(@ModelAttribute Module module) throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			if (module.getMdParentid() == null) {
				module.setMdParentid(0);
			}
			this.moduleService.addModule(module);
			result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			// 当前登录用户
			Map<String, Object> map = DataModelMap.getMapObject();
			User userInfo = this.findCurrentUserInfo();
			list = this.moduleService.queryFunctionModuleList(Module.class, userInfo);
			map.put("functionUser", "");
			map.put("functionUser", userInfo);
			map.put("functionModule", list);
			list = this.moduleService.queryModuleList(Module.class, userInfo);
			map.put("backModuleUser", "");
			map.put("backModuleUser", userInfo);
			map.put("backModule", list);
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
	 * <p>描述：模块维护——模块的批量删除</p>
	 * 
	 * @author   gao 2014-05-09
	 * @return
	 */
	@RequestMapping("/doModuleBulkDelete")
	public String doModuleBulkDelete() {
		try {
			String moduleIds = ParamUtils.getParameter(request, "paramsId");
			this.moduleService.bulkDelete(moduleIds);
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			String result = "{success:true,flag:true}";
			// 当前登录用户
				Map<String, Object> map = DataModelMap.getMapObject();
				User userInfo = this.findCurrentUserInfo();
				list = this.moduleService.queryFunctionModuleList(Module.class, userInfo);
				map.put("functionUser", "");
				map.put("functionUser", userInfo);
				map.put("functionModule", list);
				list = this.moduleService.queryModuleList(Module.class, userInfo);
				map.put("backModuleUser", "");
				map.put("backModuleUser", userInfo);
				map.put("backModule", list);
			response.getWriter().print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>描述：模块维护——模块的编辑</p>
	 * 
	 * @author   gao 2014-05-09
	 * @return
	 */
	@RequestMapping("/doModuleEdit")
	public String doModuleEdit(@ModelAttribute Module module) {
		try {
			if (module.getMdParentid() == null) {
				module.setMdParentid(0);
			}
			this.moduleService.modifyModule(module);
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			String result = "{success:true,msg:true}";
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			// 当前登录用户
			Map<String, Object> map = DataModelMap.getMapObject();
			User userInfo = this.findCurrentUserInfo();
			list = this.moduleService.queryFunctionModuleList(Module.class, userInfo);
			map.put("functionUser", "");
			map.put("functionUser", userInfo);
			map.put("functionModule", list);
			list = this.moduleService.queryModuleList(Module.class, userInfo);
			map.put("backModuleUser", "");
			map.put("backModuleUser", userInfo);
			map.put("backModule", list);
			response.getWriter().print(result);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>描述：显示权限配置里的当前角色拥有的模块信息</p>
	 * 
	 * @author   gao 2014-05-09
	 * @return Null
	 * @throws IOException
	 */
	@RequestMapping("/doShowRightCurrentRoleOwnModules")
	public String doShowRightCurrentRoleOwnModules() throws IOException {
		// Gson g = new Gson();
		String roleId = ParamUtils.getParameter(request, "roleId");
		list = moduleService.queryCurrentRoleModules(Module.class, roleId);
		String json = "";
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("[");
		for (int i = 0; i < list.size(); i++) {
			strBuf.append("{\"mdId\":" + list.get(i)).append("}");
			if (i != list.size() - 1) {
				strBuf.append(",");
			}
		}
		strBuf.append("]");
		json = strBuf.toString();
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
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
	 * @author 显示权限配置里的模块列表信息
	 * 
	 */
	@RequestMapping("/doShowRightModules")
	public String doShowRightModules() throws IOException {
		String json = "";
		Gson g = new Gson();
		// int orgId = ParamUtils.getIntParameter(request, "orgId", 303);
		list = moduleService.loadModules(Module.class);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(PagingConstants.TOTAL, 300000);
		jsonMap.put(PagingConstants.LIST, list);
		json = g.toJson(jsonMap);
		pw.print(json);
		log.debug("doShowRightModules ***json==****" + json);
		pw.flush();
		pw.close();
		return null;
	}

	@RequestMapping("/findMaxNum")
	public String findMaxNum() {
		try {
			Integer moduleType = ParamUtils.getIntAttribute(request, "moduleType", 1);
			int maxId = this.moduleService.findMaxNum(moduleType).intValue();
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			String result = "{success:true,msg:true,maxId:" + (maxId) + "}";
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
	
	@RequestMapping("/queryModules")
	public String queryModules() {
		try {
			Gson g = new Gson();
			String json = "";
			list = this.moduleService.loadModules(Module.class);
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("totals", 1000);
			jsonMap.put("list", list);
			json = g.toJson(jsonMap);
			response.setContentType("application/json;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			pw.print(json);
			log.debug("queryModules json==" + json);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/loadModuleType")
	public String loadModuleType() throws Exception {
		String type = ParamUtils.getParameter(request, "type");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		String json = this.moduleService.loadModuleType(language);
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
	public List<Module> getList() {
		return list;
	}

	public Module getModule() {
		return module;
	}

	

	public void setList(List<Module> list) {
		this.list = list;
	}

	public void setModule(Module module) {
		this.module = module;
	}
}
