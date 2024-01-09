package com.cosog.service.right;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import com.cosog.model.Org;
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
	public List<?> queryTabs(Class<T> clazz) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT id,parentid,tabname,sortNum "
				+ " FROM tbl_tabinfo t ");
		sqlBuffer.append(" order by t.sortNum");
		return this.findCallSql(sqlBuffer.toString());
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
