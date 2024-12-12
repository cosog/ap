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
		AlarmShowStyle alarmShowStyle=null;
		String json="{}";
		try{
			alarmShowStyle=MemoryDataManagerTask.getAlarmShowStyle();
			if(alarmShowStyle!=null){
				json=new Gson().toJson(alarmShowStyle);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
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
