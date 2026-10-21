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
import com.cosog.model.DeviceTabManager;
import com.cosog.model.ExportModuleData;
import com.cosog.model.Module;
import com.cosog.model.SceneModel;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


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
			String dataSourceValue="";
			String itemName=obj[1]+"";
			String itemCode=obj[2]+"";
			String type=obj[3]+"";
			if("0".equalsIgnoreCase(type)){
				dataSourceValue="0";
				dataSource=MemoryDataManagerTask.getCodeName("DATASOURCE", dataSourceValue, language);
				ModbusProtocolConfig.Items item=MemoryDataManagerTask.getProtocolItemByMappingColumn(protocol, itemCode);
				if(item!=null){
					unit=item.getUnit();
				}
			}else if("5".equalsIgnoreCase(type)){
				dataSourceValue="5";
				dataSource=MemoryDataManagerTask.getCodeName("DATASOURCE", dataSourceValue, language);
				ModbusProtocolConfig.ExtendedField item=MemoryDataManagerTask.getProtocolExtendedFieldByMappingColumn(protocol, itemCode);
				if(item!=null){
					unit=item.getUnit();
				}
			}else if("1".equalsIgnoreCase(type)){
				dataSourceValue="1";
				dataSource=MemoryDataManagerTask.getCodeName("DATASOURCE", dataSourceValue, language);
				CalItem calItem=MemoryDataManagerTask.getCalItemByCode(itemCode, language);
				if(calItem!=null){
					unit=calItem.getUnit();
					itemName=calItem.getName();
				}
			}else if("3".equalsIgnoreCase(type)){
				dataSourceValue="2";
				dataSource=MemoryDataManagerTask.getCodeName("DATASOURCE", dataSourceValue, language);
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
			result_json.append("\"dataSourceValue\":\""+dataSourceValue+"\",");
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
//	public Map<String, Object> getSceneModelConfig(int id,User user) {
//	    Map<String, Object> result = null;
//
//	    try {
//	        String sql = "select t.id, t.config from TBL_SCENEMODEL t where t.id = " + id;
//	        List<?> list = this.findCallSql(sql);
//
//	        if (list != null && !list.isEmpty()) {
//	            Object[] obj = (Object[]) list.get(0);
//	            result = new HashMap<String, Object>();
//	            result.put("id", obj[0]);
//	            result.put("config", StringManagerUtils.CLOBObjectToString(obj[1]));
//	        }
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//
//	    return result;
//	}
	
	/**
	 * 根据 id 从数据库获取单个场景的 config
	 *
	 * @param id 场景主键
	 * @param language 语言
	 * @return { id, config } 或 null
	 */
	public Map<String, Object> getSceneModelConfig(int id, String language) {
	    Map<String, Object> result = null;
	    try {
	        String sql = "select t.id, t.config from TBL_SCENEMODEL t where t.id = " + id;
	        List<?> list = this.findCallSql(sql);

	        if (list != null && !list.isEmpty()) {
	            Object[] obj = (Object[]) list.get(0);
	            result = new HashMap<String, Object>();
	            result.put("id", obj[0]);
	            
	            String cfgStr = StringManagerUtils.CLOBObjectToString(obj[1]);
	            
	            if (StringManagerUtils.isNotNull(cfgStr)) {
	                try {
	                    Gson gson = new Gson();
	                    JsonParser jsonParser = new JsonParser();
	                    JsonObject cfgObj = jsonParser.parse(cfgStr).getAsJsonObject();
	                    
	                    // ★ 调用公共方法转换
	                    processCalculateItemLabelAndUnit(cfgObj, language);
	                    
	                    cfgStr = gson.toJson(cfgObj);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	            result.put("config", cfgStr);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	// ★ 保留原有方法签名作为重载，兼容其它可能未传语言的调用
	public Map<String, Object> getSceneModelConfig(int id) {
	    return getSceneModelConfig(id, "");
	}
	
	/**
	 * 根据设备 id 获取关联的场景模型 config
	 *  - 查 tbl_device.scenemodel
	 *  - scenemodel 有值 → 查 tbl_scenemodel 返回 config
	 *  - scenemodel 为空 / 场景不存在 → 返回 {imageUrl:"", shapes:[]}
	 */
	public Map<String, Object> getSceneModelConfigByDeviceId(int deviceId) {
	    Map<String, Object> result = new HashMap<String, Object>();

	    try {
	        // 1. 查设备表拿到 scenemodel
	        String sql1 = "select t.scenemodel from TBL_DEVICE t where t.id = " + deviceId;
	        List<?> list1 = this.findCallSql(sql1);

	        int sceneModelId = 0;
	        if (list1 != null && !list1.isEmpty()) {
	            Object obj = list1.get(0);
	            if (obj != null) {
	                try { sceneModelId = Integer.parseInt(obj.toString()); } catch (Exception e) {}
	            }
	        }

	        // 2. 没有关联场景 → 返回默认空 config
	        if (sceneModelId <= 0) {
	            Map<String, Object> cfg = new HashMap<String, Object>();
	            cfg.put("imageUrl", "");
	            cfg.put("shapes", new java.util.ArrayList<Object>());
	            result.put("id", 0);
	            result.put("config", new com.google.gson.Gson().toJson(cfg));
	            return result;
	        }

	        // 3. 有关联场景 → 查场景 config
	        String sql2 = "select t.id, t.config from TBL_SCENEMODEL t where t.id = " + sceneModelId;
	        List<?> list2 = this.findCallSql(sql2);

	        if (list2 != null && !list2.isEmpty()) {
	            Object[] obj = (Object[]) list2.get(0);
	            result.put("id", obj[0]);
	            String cfgStr = StringManagerUtils.CLOBObjectToString(obj[1]);
	            if (!StringManagerUtils.isNotNull(cfgStr)) {
	                // config 为空 → 返回默认空 config
	                Map<String, Object> cfg = new HashMap<String, Object>();
	                cfg.put("imageUrl", "");
	                cfg.put("shapes", new java.util.ArrayList<Object>());
	                cfgStr = new com.google.gson.Gson().toJson(cfg);
	            }
	            result.put("config", cfgStr);
	        } else {
	            // 场景 id 存在但记录被删了 → 默认空 config
	            Map<String, Object> cfg = new HashMap<String, Object>();
	            cfg.put("imageUrl", "");
	            cfg.put("shapes", new java.util.ArrayList<Object>());
	            result.put("id", 0);
	            result.put("config", new com.google.gson.Gson().toJson(cfg));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}
	
	public int saveSceneModelInfo(SceneModel sceneModel) throws Exception {
		String sql="update tbl_scenemodel t set "
				+ " t.name_zh_CN='"+sceneModel.getName_zh_CN()+"',"
				+ " t.name_en='"+sceneModel.getName_en()+"',"
				+ " t.name_ru='"+sceneModel.getName_ru()+"',"
				+ " t.sort="+sceneModel.getSort()+" "
				+ " where t.id="+sceneModel.getId();
		int r=getBaseDao().updateOrDeleteBySql(sql);
		return r;
	}
	
	public void addSceneModel(T sceneModel) throws Exception {
		getBaseDao().addObject(sceneModel);
	}
	
	public int deleteSceneModel(String ids) throws IOException, SQLException {
		String sql="delete from tbl_scenemodel t where t.id in ("+ids+")";
		int r=getBaseDao().updateOrDeleteBySql(sql);
		return r;
	}
}
