package com.cosog.service.back;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.AlarmShowStyle;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.Page;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

/**
 * <p>
 * 报警设置信息Service
 * </p>
 * 
 * @author gao 2014-06-06
 * 
 * @param <T>
 */
@Service("alarmSetManagerService")
public class AlarmSetManagerService<T> extends BaseService<T> {
	@Autowired
	private CommonDataService commonDataService;
	public List<T> querrAlarmSets(Page pager, Class<T> clazz) throws Exception {
		String hql = "SELECT u FROM WorkStatusAlarm u";
		return this.getBaseDao().getListForPage(pager, hql, "WorkStatusAlarm");
	}

	public List<T> loadAlarmSets(Class<T> clazz) {
		return this.getBaseDao().getAllObjects(clazz);
	}

	/**
	 * <p>
	 * 显示报警设置下拉菜单信息方法
	 * </p>
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String loadAlarmSetType(String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		if (type.equalsIgnoreCase("result")) {
			sql = " select distinct t.resultcode,t.resultname from tbl_rpc_worktype t,tbl_rpc_alarmtype_conf g where g.resultcode=t.resultcode";
		} else if (type.equalsIgnoreCase("alarmType")) {
			sql = " select  distinct t.itemvalue,t.itemname from tbl_code t,tbl_rpc_alarmtype_conf g where  t.itemcode='BJLX' and t.itemvalue=g.alarmtype";
		} else if (type.equalsIgnoreCase("alarmLevel")) {
			sql = " select t.itemvalue,t.itemname from tbl_code t where  t.itemcode='BJJB' and t.itemvalue<400 order by t.itemvalue";
		}
		try {
			List<?> list = this.getSQLObjects(sql);
			result_json.append("[");
			String get_key = "";
			String get_val = "";
			if (null != list && list.size() > 0) {
				for (Object o : list) {
					Object[] obj = (Object[]) o;
					get_key = obj[0] + "";
					get_val = (String) obj[1];
					result_json.append("{boxkey:\"" + get_key + "\",");
					result_json.append("boxval:\"" + get_val + "\"},");
				}
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
			result_json.append("]");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}

	public void addAlarmSet(T AlarmSet) throws Exception {
		this.getBaseDao().addObject(AlarmSet);
	}

	public void modifyAlarmSet(T AlarmSet) throws Exception {
		this.getBaseDao().updateObject(AlarmSet);
	}

	public void deleteAlarmSet(int id, Class<T> clazz) throws Exception {
		T u = this.getBaseDao().getObject(clazz, id);
		this.getBaseDao().deleteObject(u);
	}

	public T getAlarmSet(Class<T> clazz, int id) {
		return this.getBaseDao().getObject(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	public String getAlarmLevelColor(){
		Jedis jedis = null;
		AlarmShowStyle alarmShowStyle=null;
		String json="{}";
		try{
			jedis = RedisUtil.jedisPool.getResource();
			if(!jedis.exists("AlarmShowStyle".getBytes())){
				MemoryDataManagerTask.initAlarmStyle();
			}
			alarmShowStyle=(AlarmShowStyle) SerializeObjectUnils.unserizlize(jedis.get("AlarmShowStyle".getBytes()));
			if(alarmShowStyle!=null){
				json=new Gson().toJson(alarmShowStyle);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return json;
	}
	
	public void setAlarmLevelColor(AlarmShowStyle alarmShowStyle){
		try {
			this.getBaseDao().setAlarmColor(alarmShowStyle);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setAlarmColor(AlarmShowStyle alarmShowStyle){
		try {
			this.getBaseDao().setAlarmColor(alarmShowStyle);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
