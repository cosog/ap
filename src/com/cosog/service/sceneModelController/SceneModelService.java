package com.cosog.service.sceneModelController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cosog.model.Code;
import com.cosog.model.ExportModuleData;
import com.cosog.model.Module;
import com.cosog.model.User;
import com.cosog.service.base.BaseService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Config;
import com.cosog.utils.LicenseMap;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;


@Service("sceneModelService")
public class SceneModelService<T> extends BaseService<T> {
	
	public String getSceneModelList(String orgId,User user) throws IOException, SQLException {
		StringBuffer result_json = new StringBuffer();
		StringBuffer language_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		
		String columns=	"[]";
		
		String sql="select t.id,t.name_zh_CN,t.name_en,t.name_ru,t.sort from tbl_scenemodel t where t.orgid in ("+orgId+")  order by t.sort";


		
		List<?> list = this.findCallSql(sql);
		language_json.append("[");
		result_json.append("{\"success\":true,"
				+ "\"totalCount\":"+list.size()+","
				+ "\"showChineseName\":"+StringManagerUtils.existOrNot(user.getLanguageList(), 1)+","
				+ "\"showEnglishName\":"+StringManagerUtils.existOrNot(user.getLanguageList(), 2)+","
				+ "\"showRussianName\":"+StringManagerUtils.existOrNot(user.getLanguageList(), 3)+","
				+ "\"columns\":"+columns+",");
		
		result_json.append("\"totalRoot\":[");
		for (Object o : list) {
			Object[] obj = (Object[]) o;
			result_json.append("{\"modelId\":"+obj[0]+",");
			result_json.append("\"name_zh_CN\":\""+(obj[1]==null?"":obj[1])+"\",");
			result_json.append("\"name_en\":\""+(obj[2]==null?"":obj[2])+"\",");
			result_json.append("\"name_ru\":\""+(obj[3]==null?"":obj[3])+"\",");
			result_json.append("\"sort\":"+(obj[4]==null?null:obj[4])+"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
}
