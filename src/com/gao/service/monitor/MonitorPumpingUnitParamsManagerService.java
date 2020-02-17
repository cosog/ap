package com.gao.service.monitor;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gao.dao.BaseDao;
import com.gao.model.MonitorPumpingUnitParams;
import com.gao.service.base.BaseService;
import com.gao.utils.Page;
import com.gao.utils.StringManagerUtils;

/**<p>描述：采出井实时监测下拉菜单数据信息</p>
 * @author gao 2014-06-06
 *
 * @param <T>
 */
@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
@Service("monitorPumpingUnitParamsManagerService")
public class MonitorPumpingUnitParamsManagerService<T> extends BaseService<T> {

	public static void removeDuplicateWithOrder(List menuslist) {
		// 合并list,过滤重复
		Set hashSetMenu = new HashSet();
		hashSetMenu.addAll(menuslist);
		menuslist = new ArrayList<MonitorPumpingUnitParams>(hashSetMenu);
	}
	
	/**<p>描述：实现采出井下拉菜单多级联动方法</p>
	 * 
	 * @param orgId
	 * @param jhh
	 * @param jh
	 * @param bjlx 报警类型
	 * @param type 下拉菜单参数类别 即现在需要查询的是井环号，还是井名等
	 * @return
	 * @throws Exception
	 */
	public String loadMonitorHistoryParams(String orgId, String jhh, String jh,
			String bjlx, String type) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();

		String sql = "";
		if (type.equalsIgnoreCase("jhh")) {
			sql = " select  v.jhh as jhh,v.jhh as dm from  tbl_wellinformation v  ,tbl_org  g,t_018_wellringorder t where 1=1 and v.jhh=t.jhh and v.jlx=101 and  v.dwbh=g.org_code  and g.org_id in ("
					+ orgId + ")";
		} else if (type.equalsIgnoreCase("jh")) {
			sql = " select  v.jh as jh,v.jh as dm from  tbl_wellinformation v  ,tbl_org  g,t_wellorder t where 1=1 and v.jh=t.jh and v.jlx=101 and  v.dwbh=g.org_code  and g.org_id in ("
					+ orgId + ")";
		} else if (type.equalsIgnoreCase("bjlx")) {
			sql = " select distinct v.bjbz as dm,v.bjztmc as mc from v_002_12_dynamicmonitorhis_cn v  where 1=1 and v.bjbz is not null  and v.org_id in("
					+ orgId + ") ";
		}
		if (StringUtils.isNotBlank(jhh)) {
			jhh=new String(jhh.getBytes("iso-8859-1"),"utf-8");
			sql += " and v.jhh like '%" + jhh + "%'";
		}
		if (StringUtils.isNotBlank(jh)) {
			 jh=new String(jh.getBytes("iso-8859-1"),"utf-8");
			sql += " and v.jh like '%" + jh + "%'";
		}
		if (StringUtils.isNotBlank(bjlx)) {
			sql += " and v.bjztdm = " + bjlx ;
		}
		if (type.equalsIgnoreCase("jhh")) {
			sql += " order by t.pxbh, v.jhh";
		} else if (type.equalsIgnoreCase("jh")) {
			sql += " order by t.pxbh, v.jh";
		} else if (type.equalsIgnoreCase("bjlx")) {
			sql += " order by v.bjbz asc";
		}
		try {
			List<?> list = this.findCallSql(sql);
			removeDuplicateWithOrder(list);
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
				if (result_json.toString().length() > 1) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
			result_json.append("]");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}
}
