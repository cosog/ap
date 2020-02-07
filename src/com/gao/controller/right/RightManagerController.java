package com.gao.controller.right;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.Right;
import com.gao.service.right.RightManagerService;
import com.gao.utils.Constants;
import com.gao.utils.PagingConstants;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;

/**<p>描述：权限管理Action</p>
 * 
 * @author gao 2014-06-04
 *@version 1.0
 */
@Controller
@RequestMapping("/rightManagerController")
@Scope("prototype")
public class RightManagerController extends BaseController {

	private static final long serialVersionUID = -281275682819237996L;
	@Autowired
	private RightManagerService<Right> rightService;
	private List<Right> list;
	private Right Right;
	private String limit;
	private String msg = "";
	private String rightName;

	
	/**
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/doRightAdd")
	public String doRightAdd() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		try {
			this.rightService.addRight(Right);
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

	/**<p>描述：为当前用户分配角色</p>
	 * 
	 * @return NUll
	 * @throws IOException
	 *             
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/doRightSaveOrUpdate")
	public String doRightSaveOrUpdate() throws IOException {
		String result = "";
		PrintWriter out = response.getWriter();
		Right r = null;
		try {
			String rightCodes = ParamUtils.getParameter(request, "paramsId");
			String oldCodes = ParamUtils.getParameter(request, "oldCodes");
			Integer userNo = ParamUtils.getIntParameter(request, "userNo", 615);
			String rightCode[] = StringManagerUtils.split(rightCodes, ",");
			String oldCode[] = StringManagerUtils.split(oldCodes, ",");
			if (userNo != null && rightCode.length > 0) {
				this.rightService.deleteCurrentRoleByUserNo(userNo);
			}
			for (String code : rightCode) {
				r = new Right();
				r.setRtRolecode(code);
				r.setRtUserNo(userNo);
				this.rightService.saveOrUpdateRight(r);
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

	@RequestMapping("/doRightBulkDelete")
	public String doRightBulkDelete() {
		try {
			String RightIds = ParamUtils.getParameter(request, "paramsId");
			this.rightService.bulkDelete(RightIds);
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			String result = "{success:true,flag:true}";
			response.getWriter().print(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/doRightEdit")
	public String doRightEdit() {
		try {

			this.rightService.modifyRight(Right);
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

	/** <p>描述：显示权限配置里的角色列表信息</p>
	 * 
	 *  @author gao 2014-06-04
	 * @return Null
	 * @throws IOException
	 */
	@RequestMapping("/doShowRightRights")
	public String doShowRightRights() throws IOException {
		String json = "";
		Gson g = new Gson();
		// int orgId = ParamUtils.getIntParameter(request, "orgId", 303);
		list = rightService.loadRights(Right.class);
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
	 * @return Null
	 * @throws IOException
	 * @author 
	 * 
	 */
	@RequestMapping("/doShowRightCurrentUsersOwnRights")
	public String doShowRightCurrentUsersOwnRights() throws IOException {
		String json = "";
		Gson g = new Gson();
		int userNo = ParamUtils.getIntParameter(request, "userNo", 615);
		list = rightService.queryCurrentUserRights(Right.class, userNo);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		// Map<String, Object> jsonMap = new HashMap<String, Object>();
		// jsonMap.put(PagingConstants.TOTAL, 300000);
		// jsonMap.put(PagingConstants.LIST, list);
		json = g.toJson(list);
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	public String getrightName() {
		return rightName;
	}

	public void setrightName(String rightName) {
		this.rightName = rightName;
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

	private String page;

	public List<Right> getList() {
		return list;
	}

	public void setList(List<Right> list) {
		this.list = list;
	}

	public Right getRight() {
		return Right;
	}

	public void setRight(Right Right) {
		this.Right = Right;
	}

}
