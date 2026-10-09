package com.cosog.controller.sceneModelController;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.model.User;
import com.cosog.service.base.CommonDataService;
import com.cosog.service.sceneModelController.SceneModelService;
import com.cosog.utils.Constants;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;

@Controller
@RequestMapping("/sceneModelController")
@Scope("prototype")
public class SceneModelController extends BaseController {
	private static Log log = LogFactory.getLog(SceneModelController.class);
	private static final long serialVersionUID = -281275682819237996L;
	@Autowired
	private SceneModelService<?> sceneModelService;
	@Autowired
	private CommonDataService service;
	
	@RequestMapping("/getSceneModelList")
	public String getSceneModelList() throws IOException, SQLException {
		HttpSession session=request.getSession();
		String orgId=ParamUtils.getParameter(request, "orgId");
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if (user != null) {
			language = "" + user.getLanguageName();
		}
		if (!StringManagerUtils.isNotNull(orgId)) {
			if (user != null) {
				orgId = "" + user.getUserOrgIds();
			}
		}
		
		String json = sceneModelService.getSceneModelList(orgId,user);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
}
