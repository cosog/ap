package com.gao.controller.dataSource;



import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.User;
import com.gao.service.base.CommonDataService;
import com.gao.service.dataSource.DataSourceConfigService;
import com.gao.utils.Constants;
import com.gao.utils.Page;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;


@Controller
@RequestMapping("/dataSourceConfigController")
@Scope("prototype")
public class DataSourceConfigController extends BaseController {
	private static Log log = LogFactory.getLog(DataSourceConfigController.class);
	private static final long serialVersionUID = 1L;
	@Autowired
	private CommonDataService commonDataService;
	@Autowired
	private DataSourceConfigService<?> dataSourceConfigService;
	
	@RequestMapping("/getDataSourceConfigData")
	public String getDataSourceConfigData() throws Exception {
		String json = "";
		
		json = dataSourceConfigService.getKafkaConfigWellList();
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
}
