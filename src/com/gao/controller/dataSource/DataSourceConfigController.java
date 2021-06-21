package com.gao.controller.dataSource;



import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gao.controller.base.BaseController;
import com.gao.model.User;
import com.gao.model.drive.KafkaConfig;
import com.gao.service.base.CommonDataService;
import com.gao.service.dataSource.DataSourceConfigService;
import com.gao.task.EquipmentDriverServerTask;
import com.gao.utils.Constants;
import com.gao.utils.DataSourceConfig;
import com.gao.utils.DataSourceConfigSaveData;
import com.gao.utils.EquipmentDriveMap;
import com.gao.utils.Page;
import com.gao.utils.ParamUtils;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


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
	
	@RequestMapping("/saveDataSourceConfigData")
	public String SaveDataSourceConfigData() throws Exception {
		String json = "";
		Gson gson = new Gson();
		StringManagerUtils stringManagerUtils=new StringManagerUtils();
		String dataSourceConfigData = ParamUtils.getParameter(request, "dataSourceConfigData");
		java.lang.reflect.Type type = new TypeToken<DataSourceConfigSaveData>() {}.getType();
		DataSourceConfigSaveData dataSourceConfigSaveData=gson.fromJson(dataSourceConfigData, type);
		if(dataSourceConfigSaveData!=null){
			String path=stringManagerUtils.getFilePath("config.json","dataSource/");
			DataSourceConfig dataSourceConfig=DataSourceConfig.getInstance();
			
			dataSourceConfig.setIP(dataSourceConfigSaveData.getIP());
			dataSourceConfig.setPort(StringManagerUtils.stringToInteger(dataSourceConfigSaveData.getPort()));
			dataSourceConfig.setType((dataSourceConfigSaveData.getType().toLowerCase().indexOf("oracle")>=0)?0:1);
			dataSourceConfig.setVersion(dataSourceConfigSaveData.getVersion());
			dataSourceConfig.setInstanceName(dataSourceConfigSaveData.getInstanceName());
			dataSourceConfig.setUser(dataSourceConfigSaveData.getUser());
			dataSourceConfig.setPassword(dataSourceConfigSaveData.getPassword());
			
			dataSourceConfig.setDiagramTable(dataSourceConfigSaveData.getDiagramTable());
			dataSourceConfig.setReservoirTable(dataSourceConfigSaveData.getReservoirTable());
			dataSourceConfig.setRodStringTable(dataSourceConfigSaveData.getRodStringTable());
			dataSourceConfig.setTubingStringTable(dataSourceConfigSaveData.getTubingStringTable());
			dataSourceConfig.setCasingStringTable(dataSourceConfigSaveData.getCasingStringTable());
			dataSourceConfig.setPumpTable(dataSourceConfigSaveData.getPumpTable());
			dataSourceConfig.setProductionTable(dataSourceConfigSaveData.getProductionTable());
			
			StringManagerUtils.writeFile(path,StringManagerUtils.jsonStringFormat(gson.toJson(dataSourceConfig)));
		}
		json ="{success:true}";
		response.setContentType("application/json;charset="+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
}
