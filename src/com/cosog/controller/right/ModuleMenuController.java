package com.cosog.controller.right;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.Module;
import com.cosog.model.User;
import com.cosog.service.right.ModuleManagerService;
import com.cosog.utils.BackModuleRecursion;
import com.cosog.utils.Config;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.MainModuleRecursion;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

/**
 * <p>
 * 描述：功能模块树形菜单
 * </p>
 * 
 * @author gao 2014-05-09
 * @version 1.0
 */
@Controller
@RequestMapping("/moduleMenuController")
@Scope("prototype")
@SuppressWarnings({ "unused", "unchecked" })
public class ModuleMenuController extends BaseController {

	private static Log log = LogFactory.getLog(ModuleMenuController.class);
	private static final long serialVersionUID = 1L;
	private List<Module> list = null;
	@Autowired
	private ModuleManagerService<Module> services;

	public List<Module> getList() {
		return list;
	}

	public void setList(List<Module> list) {
		this.list = list;
	}

	// 获取前台模块菜单树信息
	@RequestMapping("/obtainMainModuleList")
	public String obtainMainModuleList() throws Exception {
		Gson g = new Gson();
		Integer userNo = 1;
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		Map<String, Object> map = DataModelMap.getMapObject();
		if (user != null) {
			userNo = user.getUserNo();
		}
		User oldUser = (User) map.get("mainModuleUser");
		String curUserId = user.getUserId();
		String oldUserId = "";
		if (oldUser != null) {
			oldUserId = oldUser.getUserId();
		}
		if (user != null) {
			if (map.get("mainModule") != null && oldUserId.equalsIgnoreCase(curUserId)) {
				list = (List<Module>) map.get("mainModule");
			} else {
				list = this.services.queryMainModuleList(Module.class, userNo);
				map.put("mainModuleUser", "");
				map.put("mainModuleUser", user);
				map.put("mainModule", list);

			}
		}
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw;
		String json = g.toJson(list);
		log.debug("list=" + list + "\n" + json);
		try {
			pw = response.getWriter();
			pw.write(json);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * <p>
	 * 描述：用来动态创建后台左侧的功能模块树
	 * </p>
	 * 
	 * @author gao 2014-05-09
	 * @return null
	 * @throws Exception
	 * 
	 */
	@RequestMapping("/obtainBackModuleList")
	public String obtainBackModuleList() throws Exception {
		// TODO Auto-generated method stub
		Integer userNo = 1;
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		if (user != null) {
			boolean cache = Config.getInstance().configFile.getAp().getOthers().getCache();
			if (cache) {
				log.warn("后台左侧的功能模块树启用缓存...");
				Map<String, Object> map = DataModelMap.getMapObject();
				if (user != null) {
					userNo = user.getUserNo();
				}
				User oldUser = (User) map.get("backModuleUser");
				String curUserId = user.getUserId();
				String oldUserId = "";
				if (oldUser != null) {
					oldUserId = oldUser.getUserId();
				}
				if (map.get("backModule") != null && oldUserId.equalsIgnoreCase(curUserId)) {
					list = (List<Module>) map.get("backModule");
				} else {
					list = this.services.queryModuleList(Module.class, user);
					map.put("backModuleUser", "");
					map.put("backModuleUser", user);
					map.put("backModule", list);
				}
			}else{
				log.warn("后台左侧的功能模块树未启用缓存...");
				list = this.services.queryModuleList(Module.class, user);
			}
		}
		String json = "";
		BackModuleRecursion r = new BackModuleRecursion();
		if (user != null) {

			for (Module org : list) {

				if (!r.hasParent(list, org)) {
					json = r.recursionModuleFn(list, org);
				}
			}
		}
		json = r.modifyStr(json);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.debug("constructOrgTree json==" + json);
		pw.flush();
		pw.close();
		return null;
	}

	/**
	 * <p>
	 * 描述：用来动态创建前台左侧的功能模块树
	 * </p>
	 * 
	 * @author gao 2014-05-09
	 * @return null
	 * @throws Exception
	 */
	@RequestMapping("/obtainFunctionModuleList")
	public String obtainFunctionModuleList() throws Exception {
		// TODO Auto-generated method stub
		Integer userNo = 1;
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		if (user != null) {
			boolean cache = Config.getInstance().configFile.getAp().getOthers().getCache();
			if (cache) {
				log.warn("前台左侧的功能模块树启用缓存...");
				Map<String, Object> map = DataModelMap.getMapObject();
				if (user != null) {
					userNo = user.getUserNo();
				}
				User oldUser = (User) map.get("functionUser");
				String curUserId = user.getUserId();
				String oldUserId = "";
				if (oldUser != null) {
					oldUserId = oldUser.getUserId();
				}
				if (map.get("functionModule") != null && oldUserId.equalsIgnoreCase(curUserId)) {
					list = (List<Module>) map.get("functionModule");
				} else {
					list = this.services.queryFunctionModuleList(Module.class, user);
					map.put("functionUser", "");
					map.put("functionUser", user);
					map.put("functionModule", list);

				}
			} else {
				log.warn("前台左侧的功能模块树未启用缓存...");
				list = this.services.queryFunctionModuleList(Module.class, user);
			}
		}
		String json = "";
		MainModuleRecursion r = new MainModuleRecursion();
		if (user != null) {
			for (Module org : list) {
				if (!r.hasParent(list, org)) {
					json = r.recursionFuncModuleFn(list, org);
				}
			}
		}
		json = r.modifyStr(json);
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.debug("obtainFunctionModuleList json==" + json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/obtainFunctionModuleList2")
	public String obtainFunctionModuleList2() throws Exception {
		// TODO Auto-generated method stub
		Integer userNo = 1;
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String parentNodeId=ParamUtils.getParameter(request, "tid");
		if (user != null) {
			boolean cache = Config.getInstance().configFile.getAp().getOthers().getCache();
			if (cache) {
				log.warn("前台左侧的功能模块树启用缓存...");
				Map<String, Object> map = DataModelMap.getMapObject();
				if (user != null) {
					userNo = user.getUserNo();
				}
				User oldUser = (User) map.get("functionUser");
				String curUserId = user.getUserId();
				String oldUserId = "";
				if (oldUser != null) {
					oldUserId = oldUser.getUserId();
				}
				if (map.get("functionModule") != null && oldUserId.equalsIgnoreCase(curUserId)) {
					list = (List<Module>) map.get("functionModule");
				} else {
					list = this.services.queryFunctionModuleList2(Module.class, user,parentNodeId);
					map.put("functionUser", "");
					map.put("functionUser", user);
					map.put("functionModule", list);

				}
			} else {
				log.warn("前台左侧的功能模块树未启用缓存...");
				list = this.services.queryFunctionModuleList2(Module.class, user,parentNodeId);
			}
		}
		String json = "";
		StringBuffer strBuf = new StringBuffer();
		MainModuleRecursion r = new MainModuleRecursion();
		if (user != null) {
			boolean expandedAll=Config.getInstance().configFile.getAp().getOthers().getExpandedAll();
			strBuf.append("{list:[");
			for (Module org : list) {
				//if (r.hasChild(listAll, org)) {
				if (r.isModParentNode(user.getAllModParentNodeIds().split(","), org.getMdId())) {
					strBuf.append("{\"id\":\"");
					strBuf.append(StringManagerUtils.replaceAll(org.getMdCode()));
					strBuf.append("\",\"mdId\":\"");
					strBuf.append(org.getMdId());
					strBuf.append("\",\"text\":\"");
					strBuf.append(org.getMdName());
					strBuf.append("\",\"md_icon\":\"");
					strBuf.append(org.getMdIcon());
					strBuf.append("\",\"mdCode\":\"");
					strBuf.append(StringManagerUtils.replaceAll(org.getMdCode()));
					strBuf.append("\",\"viewsrc\":\"");
					strBuf.append(StringManagerUtils.replaceAll(org.getMdUrl()));
					strBuf.append("\",\"controlsrc\":\"");
					strBuf.append(org.getMdControl());
					strBuf.append("\",\"closable\":true");
					strBuf.append(",\"iconCls\":\"" + org.getMdIcon());
					if(expandedAll){//展开所有节点
						strBuf.append("\",\"expanded\":true},");
					}else{
						strBuf.append("\",\"expanded\":false},");
					}
				}else{
					strBuf.append("{\"id\":\"");
					strBuf.append(StringManagerUtils.replaceAll(org.getMdCode()));
					strBuf.append("\",\"mdId\":\"");
					strBuf.append(org.getMdId());
					strBuf.append("\",\"text\":\"");
					strBuf.append(org.getMdName());
					strBuf.append("\",\"md_icon\":\"");
					strBuf.append(org.getMdIcon());
					strBuf.append("\",\"mdCode\":\"");
					strBuf.append(StringManagerUtils.replaceAll(org.getMdCode()));
					strBuf.append("\",\"viewsrc\":\"");
					strBuf.append(StringManagerUtils.replaceAll(org.getMdUrl()));
					strBuf.append("\",\"controlsrc\":\"");
					strBuf.append(org.getMdControl());
					strBuf.append("\",\"closable\":true");
					strBuf.append(",\"iconCls\":\"" + org.getMdIcon());
					strBuf.append("\",\"leaf\":true},");
				}
			}
			if(list.size()>0){
				strBuf=strBuf.deleteCharAt(strBuf.length()-1);
			}
			strBuf.append("]}");
		}else{
			strBuf.append("{success:true,flag:true,\"msg\":\"用户会话已经过期!\"}");
		}
		json = strBuf.toString();
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.debug("obtainFunctionModuleList json==" + json);
		pw.flush();
		pw.close();
		return null;
	}

	/**
	 * <p>
	 * 描述： 用来动态创建模块维护的上层模块
	 * </p>
	 * 
	 * @author gao 2014-05-09
	 * @return null
	 * @throws Exception
	 * 
	 */
	@RequestMapping("/obtainAddModuleList")
	public String obtainAddModuleList() throws Exception {
		// TODO Auto-generated method stub
		String json = "";
		list = this.services.queryAddModuleList(Module.class, null);
		MainModuleRecursion r = new MainModuleRecursion();
		for (Module module : list) {
			if (module.getMdParentid() == 0) {
				json = r.recursionAddModuleFn(list, module);
			}
		}
		json = r.modifyStr(json);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		log.debug("obtainFunctionModuleList json==" + json);
		pw.flush();
		pw.close();
		return null;
	}

}
