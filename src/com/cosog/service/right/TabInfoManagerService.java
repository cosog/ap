package com.cosog.service.right;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import com.cosog.model.Org;
import com.cosog.model.User;
import com.cosog.service.base.BaseService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Page;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

import redis.clients.jedis.Jedis;

@Service("tabInfoManagerService")
public class TabInfoManagerService<T> extends BaseService<T> {
	public List<?> queryTabs(Class<T> clazz, User user) {
		String queryString = "select t.id,t.parentid,t.tabname,t.sortnum from tbl_tabinfo t where 1=1 ";
		if(user!=null){
			queryString+= " and t.id in "
					+ " ("
					+ " select distinct(rt.rt_tabid) "
					+ " from tbl_user u,"
					+ " tbl_role r,"
					+ " tbl_tab2role rt "
					+ " where r.role_id= rt.rt_roleid and r.role_id=u.user_type "
					+ " and u.user_no="+user.getUserNo()
					+" ) ";
		}
		queryString+= " order by t.sortnum";
		return this.findCallSql(queryString);
	}
	
	public String getArrayTojsonPage(String data) {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{success:true");
		if (null != data) {
			String jsonStr = "";
			try {
				jsonStr = data;
			} catch (Exception e) {
				e.printStackTrace();
			}
			strBuf.append(",\"children\":" + jsonStr + "}");
		} else {
			strBuf.append(",\"children\":[]}");
		}
		return strBuf.toString();
	}
}
