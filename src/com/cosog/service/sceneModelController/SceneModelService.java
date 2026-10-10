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
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.service.base.BaseService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.task.MemoryDataManagerTask.CalItem;
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
		
		String sql="select t.id,t.name_zh_CN,t.name_en,t.name_ru,t.sort,t.orgid from tbl_scenemodel t where t.orgid in ("+orgId+")  order by t.sort";


		
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
			result_json.append("\"sort\":"+(obj[4]==null?null:obj[4])+",");
			result_json.append("\"orgId\":"+obj[5]+"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getDeviceList(String orgId,User user) throws IOException, SQLException {
		StringBuffer result_json = new StringBuffer();
		StringBuffer language_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		
		String columns=	"[]";
		
		String sql="select t.id,t.devicename,t2.calculatetype,t5.code "
				+ " from tbl_device t "
				+ " left outer join tbl_tabmanager_device t2 on t.calculatetype=t2.id "
				+ " left outer join tbl_protocolinstance t3 on t3.code=t.instancecode"
				+ " left outer join tbl_acq_unit_conf t4 on t3.unitid=t4.id"
				+ " left outer join tbl_protocol t5 on t5.code=t4.protocol"
				+ " where t.orgid in ("
				+ " select org_id from tbl_org start with org_id="+orgId+" connect by prior  org_id=org_parent"
				+ " ) "
				+ " order by t.sortnum";


		
		List<?> list = this.findCallSql(sql);
		language_json.append("[");
		result_json.append("{\"success\":true,"
				+ "\"totalCount\":"+list.size()+","
				+ "\"columns\":"+columns+",");
		
		result_json.append("\"totalRoot\":[");
		for (Object o : list) {
			Object[] obj = (Object[]) o;
			result_json.append("{\"deviceId\":"+obj[0]+",");
			result_json.append("\"deviceName\":\""+obj[1]+"\",");
			result_json.append("\"protocolCode\":\""+obj[3]+"\",");
			result_json.append("\"calculateType\":"+(obj[2]==null?0:obj[2])+"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getDeviceFields(String deviceId,String calculateType,String protocolCode,User user) throws IOException, SQLException {
		StringBuffer result_json = new StringBuffer();
		StringBuffer language_json = new StringBuffer();
		String language=user!=null?user.getLanguageName():"";
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		
		String columns=	"[]";
		
		ModbusProtocolConfig.Protocol protocol=MemoryDataManagerTask.getProtocolByCode(protocolCode);
		
		
		
		String sql="select t.id,t.itemname,t.itemcode,t.type,t.bitindex"
				+ " from TBL_DISPLAY_ITEMS2UNIT_CONF t,tbl_display_unit_conf t2,tbl_protocoldisplayinstance t3,tbl_device t4"
				+ " where t.unitid=t2.id and t2.id=t3.displayunitid and t3.code=t4.displayinstancecode"
				+ " and t.type<>2 and t.realtimedata=1 and t.itemenable=1 "
				+ " and decode(t.showlevel,null,9999,t.showlevel)>=( select r.showlevel from tbl_role r,tbl_user u where u.user_type=r.role_id and u.user_no="+user.getUserNo()+" )"
				+ " and t4.id="+deviceId
				+ " order by t.realtimesort,t.id";


		
		List<?> list = this.findCallSql(sql);
		language_json.append("[");
		result_json.append("{\"success\":true,"
				+ "\"totalCount\":"+list.size()+","
				+ "\"columns\":"+columns+",");
		
		result_json.append("\"totalRoot\":[");
		for (Object o : list) {
			Object[] obj = (Object[]) o;
			String unit="";
			String dataSource="";
			String itemName=obj[1]+"";
			String itemCode=obj[2]+"";
			String type=obj[3]+"";
			if("0".equalsIgnoreCase(type)){
				dataSource=MemoryDataManagerTask.getCodeName("DATASOURCE", "0", language);
				ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItemByMappingColumn(protocol, itemCode);
				if(item!=null){
					unit=item.getUnit();
				}
			}else if("5".equalsIgnoreCase(type)){
				dataSource=MemoryDataManagerTask.getCodeName("DATASOURCE", "5", language);
				ModbusProtocolConfig.ExtendedField item=MemoryDataManagerTask.getProtocolExtendedFieldByMappingColumn(protocol, itemCode);
				if(item!=null){
					unit=item.getUnit();
				}
			}else if("1".equalsIgnoreCase(type)){
				dataSource=MemoryDataManagerTask.getCodeName("DATASOURCE", "1", language);
				CalItem calItem=MemoryDataManagerTask.getCalItemByCode(itemCode, language);
				if(calItem!=null){
					unit=calItem.getUnit();
					itemName=calItem.getName();
				}
			}else if("3".equalsIgnoreCase(type)){
				dataSource=MemoryDataManagerTask.getCodeName("DATASOURCE", "2", language);
				CalItem calItem=MemoryDataManagerTask.getInputItemByCode(itemCode, language);
				if(calItem!=null){
					unit=calItem.getUnit();
					itemName=calItem.getName();
				}
			}
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"itemName\":\""+itemName+"\",");
			result_json.append("\"itemCode\":\""+itemCode+"\",");
			result_json.append("\"type\":\""+type+"\",");
			result_json.append("\"bitindex\":\""+obj[4]+"\",");
			result_json.append("\"dataSource\":\""+dataSource+"\",");
			result_json.append("\"unit\":\""+unit+"\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	/**
	 * 保存场景的标注配置（只更新 config 字段）
	 *
	 * @param id     场景主键
	 * @param config 标注 JSON 字符串（CLOB）
	 * @return 是否成功
	 */
	public boolean saveSceneModel(int id, String config) {
	    boolean success = false;
	    try {
	        // 参数化 SQL，防止注入
	        String sql = "update TBL_SCENEMODEL set config = ? where id = "+id;
	        List<String> clobCont=new ArrayList<String>();
	        clobCont.add(config);

	        int result=this.getBaseDao().executeSqlUpdateClob(sql,clobCont);
	        if(result>0){
	        	success=true;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        success = false;
	    }
	    return success;
	}
	
	/**
	 * 根据 id 从数据库获取单个场景的 config
	 *
	 * @param id 场景主键
	 * @return { id, config } 或 null
	 */
	public Map<String, Object> getSceneModelConfig(int id) {
	    Map<String, Object> result = null;

	    try {
	        String sql = "select t.id, t.config from TBL_SCENEMODEL t where t.id = " + id;
	        List<?> list = this.findCallSql(sql);

	        if (list != null && !list.isEmpty()) {
	            Object[] obj = (Object[]) list.get(0);
	            result = new HashMap<String, Object>();
	            result.put("id", obj[0]);
	            result.put("config", StringManagerUtils.CLOBObjectToString(obj[1]));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}
}
