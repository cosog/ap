package com.gao.service.base;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gao.model.DataModels;
import com.gao.model.data.DataDictionary;
import com.gao.service.data.DataitemsInfoService;
import com.gao.utils.DataModelMap;
import com.gao.utils.Page;
import com.gao.utils.StringManagerUtils;
import com.gao.utils.XmlParseToolsHandler;

/**
 * <p>
 * 公用服务类类，每个模块可以根据实际情况自定义自己的方法
 * </p>
 * 
 * @author gao 2014-06-06
 * 
 */
@SuppressWarnings({ "unused", "rawtypes", "deprecation", "unchecked" })
@Service
public class CommonDataService extends BaseService {
	// 数据项值service
	@Autowired
	private DataitemsInfoService dataitemsInfoService;

	public String findMonitorDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";

		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = data.getPageSize();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append(" select " + data.getColumns() + " from ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i).trim() + ",");
					}
				}
			}
		}
		sqlwhere.append("order by " + data.getOrderBys());
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		return getResult;

	}

	/**
	 * <p>
	 * 描述：显示当期模块的表头数据信息
	 * </p>
	 * 
	 * @param ddicName
	 *            当前模块的字典编码
	 * 
	 * @author gao 2014-06-13
	 * @return columns 表头字符串
	 */
	public String showTableHeadersColumns(String ddicName) {
		Map<String, Object> map = DataModelMap.getMapObject();// 获取一个map对象
		DataDictionary ddic = null;
		try {
			ddic = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);// 从字典表里获取字典数据信息
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String columns = ddic.getTableHeader();
		return columns;
	}

	/**
	 * @param pager
	 *            分页信息
	 * @author gao 2014-05-08
	 * @param orgId
	 *            组织id信息
	 * @param ddicName
	 *            当前数据字典英文编码
	 * @param v
	 *            动态参数集合
	 * @return getResult前台Ext js 需要的json 数据
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String findCommonModuleDataById(Page pager, String orgId, String ddicName, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		String sqlAll = "";
		int limit = 25;
		StringBuffer sqlwhere = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();// 获取一个map对象
		DataDictionary ddic  = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);// 从字典表里获取字典数据信息
//		ddic = (DataDictionary) map.get(ddicName);// 从缓存中获取当前模块的字典信息,如果不存在，则从数据库中查询
//		if (ddic == null || "".equals(ddic)) {
//			ddic = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);// 从字典表里获取字典数据信息
//			map.put(ddicName, ddic);
//		}
		String columns = ddic.getTableHeader();
		/***
		 * 动态拼接sql 语句
		 * */
		sqlwhere.append(ddic.getSql());
		if(StringManagerUtils.isNotNull(orgId)){
			sqlwhere.append(" and org_id in (" + orgId + ")");
		}
		StringBuffer valueWhere = new StringBuffer();// 存放动态参数相应的值，拼接后的字符串格式为："s4-4,2014-08-08"
		List<String> dyns = ddic.getParams();
		/***
		 * 动态拼接sql 语句 中的动态参数信息 例如 w.jh=?
		 * */
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		if (StringManagerUtils.isNotNull(ddic.getGroup())) {
			sqlwhere.append(" group by " + ddic.getGroup());//加上分组信息
		}
		sqlAll=sqlwhere.toString();
		//在sqlAll的基础上加上自定义的分页参数信息,三层嵌套
		if (StringManagerUtils.isNotNull(ddic.getOrder())) {
			sqlwhere.append(" order by " + ddic.getOrder());//最内层语句加上排序信息
		}
		sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
		sqlCuswhere.append(""+sqlwhere);
		int maxvalue=pager.getLimit()+pager.getStart();
		sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
		sqlCuswhere.append(" where rn >"+pager.getStart());
		
		String getResult = "";
		sql = sqlCuswhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {// 当参数字符串不为空时，调用该方法获取 json 字符串
			getResult = this.findCustomPageBySqlEntity(sqlAll,sql, columns, 20 + "", pager, o);
		} else {
			getResult = this.findCustomPageBySqlEntity(sqlAll,sql, columns, 20 + "", pager);
		}
		return getResult;
	}
	
	public String findMonitorHistoryDataById(Page pager, String orgId, String ddicName, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		String sqlAll = "";
		Map<String, Object> map = DataModelMap.getMapObject();
		DataDictionary ddic = null;
		ddic = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
		String columns = ddic.getTableHeader();
		StringBuffer sqlwhere = new StringBuffer();
		//StringBuffer sqlCuswhere = new StringBuffer();
		sqlwhere.append(ddic.getSql());
		
		
		
		String table=sqlwhere.toString().substring(sqlwhere.toString().indexOf("from"), sqlwhere.toString().indexOf("where"));
		
		sqlwhere.append(" and org_id in (" + orgId + ")");
		List<String> dyns = ddic.getParams();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i).trim()+ ",");
					}
				}
			}
		}
//		sqlAll=sqlwhere.toString();
		String sqlAll1=sqlwhere.toString().substring(sqlwhere.toString().indexOf("from"));
		sqlAll="select count(1) "+sqlAll1;
		int maxvalue=pager.getLimit()+pager.getStart();
		String sql1="(select ID from  ( select a.*,rownum as rn from (select ID "+sqlAll1;//+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart()+")";
		if (StringManagerUtils.isNotNull(ddic.getOrder())) {
			sql1=sql1+" order by " + ddic.getOrder()+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart()+")";//最内层语句加上排序信息
		}else{
			sql1=sql1+" ) a where  rownum <="+maxvalue+") b where rn >"+pager.getStart()+")";
		}
		String sql2="select  "+sqlwhere.toString().substring(sqlwhere.toString().indexOf("select")+6, sqlwhere.toString().indexOf("from"))+table+" where id in "+sql1;
//		int aaa=sqlwhere.toString().indexOf("from");
//		String all=sqlwhere.toString().substring(sqlwhere.toString().indexOf("from"));
		//在sqlAll的基础上加上自定义的分页参数信息,三层嵌套
		if (StringManagerUtils.isNotNull(ddic.getOrder())) {
			sql2=sql2+" order by " + ddic.getOrder();//最内层语句加上排序信息
		}
//		sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
//		sqlCuswhere.append(""+sqlwhere);
//		int maxvalue=pager.getLimit()+pager.getStart();
//		sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
//		sqlCuswhere.append(" where rn >"+pager.getStart());
		
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findCustomPageBySqlEntity(sqlAll,sql2, columns, 20 + "", pager, o);
		} else {
			getResult = this.findCustomPageBySqlEntity(sqlAll,sql2, columns, 20 + "", pager);
		}

		return getResult;

	}
	public String findCommonMuchHeadModuleDataById(Page pager, String ddicName, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		int limit = 25;
		StringBuffer sqlwhere = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();// 获取一个map对象
		DataDictionary ddic = null;
		ddic = (DataDictionary) map.get(ddicName);// 从缓存中获取当前模块的字典信息,如果不存在，则从数据库中查询
		if (ddic == null || "".equals(ddic)) {
			ddic = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);// 从字典表里获取字典数据信息
			// map.put(ddicName, ddic);
		}
		String columns = ddic.getTableHeader();
		/***
		 * 动态拼接sql 语句
		 * */
		sqlwhere.append(ddic.getSql());
		// sqlwhere.append(" and o.org_id in (" + orgId + ")");
		StringBuffer valueWhere = new StringBuffer();// 存放动态参数相应的值，拼接后的字符串格式为："s4-4,2014-08-08"
		List<String> dyns = ddic.getParams();
		/***
		 * 动态拼接sql 语句 中的动态参数信息 例如 w.jh=?
		 * */
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		if (StringManagerUtils.isNotNull(ddic.getOrder())) {
			sqlwhere.append("order by " + ddic.getOrder());
		}
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			// 当参数字符串不为空时，调用该方法获取 json 字符串
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		return getResult;

	}

	/**
	 * <p>
	 * 描述：根据数据字典英文编码查询当前模块的数据信息且自动转化为需要的json数据
	 * </p>
	 * 
	 * @param pager
	 *            分页工具信息
	 * @param orgId
	 *            组织id
	 * @param ddicName
	 *            数据字典编码
	 * @param v
	 *            参数集合
	 * @return
	 */
	public String findGridJsonDataByDdicCode(Page pager, String orgId, String ddicName, Vector<String> v) {
		String sql = "";
		Map<String, Object> map = DataModelMap.getMapObject();
		DataDictionary ddic = null;
		ddic = (DataDictionary) map.get(ddicName);
		ddic = null;
		if (ddic == null || "".equals(ddic)) {
			ddic = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			map.put(ddicName, ddic);
		}
		String columns = ddic.getTableHeader();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append(ddic.getSql());
		if (!pager.getOrgExist().equalsIgnoreCase("notExist") && !orgId.equals("")) {
			sqlwhere.append(" and orgid in (" + orgId + ")");
		}
		List<String> dyns = ddic.getParams();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!v.get(i).equals("") && !v.get(i).equals("null") && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		if (StringManagerUtils.isNotNull(ddic.getOrder())) {
			sqlwhere.append(" order by " + ddic.getOrder());
		}
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params) && !params.equals("")) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, pager);
		}
		return getResult;

	}

	/**
	 * <p>
	 * 描述：根据数据字典英文编码查询当前模块的数据信息且自动转化为需要的json数据 <<不分页>>
	 * </p>
	 * 
	 * @param pager
	 * @param orgId
	 * @param ddicName
	 * @param v
	 * @return
	 */
	public String findGridJsonAllDataByDdicCode(Page pager, String orgId, String ddicName, Vector<String> v) {
		String sql = "";
		Map<String, Object> map = DataModelMap.getMapObject();
		DataDictionary ddic = null;
		ddic = (DataDictionary) map.get(ddicName);
		if (ddic == null || "".equals(ddic)) {
			ddic = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			map.put(ddicName, ddic);
		}
		String columns = ddic.getTableHeader();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append(ddic.getSql());
		if (!pager.getOrgExist().equalsIgnoreCase("notExist")) {
			sqlwhere.append(" and u.orgid in (" + orgId + ")");
		}
		List<String> dyns = ddic.getParams();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		if (StringManagerUtils.isNotNull(ddic.getOrder())) {
			sqlwhere.append(" order by " + ddic.getOrder());
		}
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findAllDataBySqlEntity(sql.toString(), columns, pager, o);
		} else {
			getResult = this.findAllDataBySqlEntity(sql.toString(), columns, pager);
		}

		return getResult;

	}



	public String findMonitorInDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		// String orgIds = this.getUserOrgIds(orgId);
		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = data.getPageSize();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append("select id, bjbz, jh, zrfs,cjsj, zdjg, yhjy, rpzrl, sjrzrl, ssll,ljll, jkzryl, ty, jklw from (  select  " + data.getColumns() + " from ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append(")");
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		return getResult;

	}

	public String findMonitorWaterInjectionHistoryDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = data.getPageSize();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append("select id, bjbz, jh, zrfs,cjsj, zdjg, yhjy, rpzrl, sjrzrl, ssll,ljll, jkzryl, ty, jklw from (  select  " + data.getColumns() + " from ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and u.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append("order by " + data.getOrderBys() + ")");
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		return getResult;

	}

	public String findComputeInstantDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		// String orgIds = this.getUserOrgIds(orgId);
		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = data.getPageSize();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append("select id, jhh, jh,  gtcjsj, jsdjrcyl, jsdjrcyl1, hsl, bg,bj, dym ,rgzsj ,cmd from (select " + data.getColumns() + " from ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append("order by " + data.getOrderBys() + ")");
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		return getResult;

	}

	public String findComputeAlldayDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		// String orgIds = this.getUserOrgIds(orgId);
		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = data.getPageSize();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append("select id, jhh, jh,  gtcjsj, jsdjrcyl, jsdjrcyl1, hsl, bg,bj, dym,rgzsj,cmd from (select " + data.getColumns() + " from ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append("order by " + data.getOrderBys() + ")");
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		return getResult;

	}

	public String findComputeAverageDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		// String orgIds = this.getUserOrgIds(orgId);
		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = data.getPageSize();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append(" select " + data.getColumns() + " from ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append("order by " + data.getOrderBys());
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		return getResult;

	}

	public String findReportPumpUnitDataById(Page pager, String orgId, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		int limit = 25;
		StringBuffer sqlwhere = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();
		DataDictionary ddic = null;
		ddic = (DataDictionary) map.get("pumpUnitDayReport");
		if (ddic == null || "".equals(ddic)) {
			ddic = dataitemsInfoService.findTableSqlWhereByListFaceId("pumpUnitDayReport");
			map.put("pumpUnitDayReport", ddic);
		}
		String columns = ddic.getTableHeader();
		sqlwhere.append(ddic.getSql());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		StringBuffer valueWhere = new StringBuffer();
		List<String> dyns = ddic.getParams();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append("order by " + ddic.getOrder());
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}

		return getResult;

	}

	public String findReportPumpUnitMonthDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		// String orgIds = this.getUserOrgIds(orgId);
		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = data.getPageSize();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append("select " + data.getColumns() + " from ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append(" order by " + data.getOrderBys());
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		return getResult;

	}

	public String findReportPumpUnitCustomDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		// String orgIds = this.getUserOrgIds(orgId);
		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = data.getPageSize();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append("select jh,pfdjrcyl,pfdjrcyl1,hsl,bz from (select " + data.getColumns() + " from ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append("order by " + data.getOrderBys());
		sqlwhere.append(")");
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		return getResult;

	}

	public String findReportWaterInjectionDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		// String orgIds = this.getUserOrgIds(orgId);
		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = data.getPageSize();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append("select " + data.getColumns() + " from ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append("order by " + data.getOrderBys());
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		return getResult;

	}

	public String findReportWaterInjectionMonthDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		// String orgIds = this.getUserOrgIds(orgId);
		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = data.getPageSize();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append("select " + data.getColumns() + " from ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append("order by " + data.getOrderBys());
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		return getResult;

	}

	public String findReportWaterInjectionCustomDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		// String orgIds = this.getUserOrgIds(orgId);
		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = data.getPageSize();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append("select id,jh,rpzsl,sjzsl,bz from (select " + data.getColumns() + " from ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append("order by " + data.getOrderBys());
		sqlwhere.append(")");
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		return getResult;

	}

	public String findCurvePumpUnitDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		// String orgIds = this.getUserOrgIds(orgId);
		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = 25;
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append("select " + data.getColumns() + " from ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append(" order by jssj");
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		
		return getResult;

	}

	public String findCurvePumpUnitLoadDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		// String orgIds = this.getUserOrgIds(orgId);
		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = data.getPageSize();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append("select " + data.getColumns() + " from ( select " + replaceStr(data.getColumns()) + " from  ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append(") order by gtcjsj");
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		
		return getResult;

	}

	public String findCurveWarterInjectionDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		// String orgIds = this.getUserOrgIds(orgId);
		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = data.getPageSize();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append("select " + data.getColumns() + " from ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		
		return getResult;

	}

	public String findMeasureOutDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		// String orgIds = this.getUserOrgIds(orgId);
		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = data.getPageSize();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append(" select id, jh,  gtcjsj, gtsj,gtcc,gtcc1,zdzh,zxzh,yy, ty, jklw,bppl,dlqx, " + "scczddl,xcczddl,ljdl,ljfdl, axpjdl, axzxdl, axzddl, bxpjdl, bxzxdl," + "bxzddl,cxpjdl,cxzxdl,cxzddl, axpjdy, axzxdy, axzddy, bxpjdy," + "bxzxdy, bxzddy,cxpjdy,cxzxdy,cxzddy, pjyggl, zxyggl,zdyggl, " + "pjwggl, zxwggl, zdwggl,pjglys,zxglys,zdglys " + "from ( select " + data.getColumns() + " from ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append(" order by pxbh,jh,gtcjsj desc )");

		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();

		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		
		return getResult;

	}

	public String findMeasureInDataById(Page pager, String orgId, DataModels data, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		// String orgIds = this.getUserOrgIds(orgId);
		String columns = XmlParseToolsHandler.montageColumns(data);
		int limit = data.getPageSize();
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append("select id, jh,cjsj, ssll, ljll, jkzryl, ty, jklw from (select " + data.getColumns() + " from ");
		sqlwhere.append(data.getTables() + " where 1=1 ");
		sqlwhere.append(data.getCondSqls());
		sqlwhere.append(" and o.org_id in (" + orgId + ")");
		List<String> dyns = data.getDynamics();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append("order by " + data.getOrderBys() + ")");
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, limit + "", pager);
		}
		
		return getResult;

	}

	public String replaceStr(String data) {
		StringBuffer aa = new StringBuffer();
		String str = data;
		String sarray[] = str.split(",");
		int len = sarray.length;
		for (int a = 0; a < len; a++) {
			String temp = sarray[a];
			if (temp.trim().equals("gtcjsj") || sarray[a].equals("cjsj")) {
				sarray[a] = "to_char(" + sarray[a] + ",'YYYY-MM-DD hh24:mi:ss') as " + sarray[a] + "";
			}
			aa.append(sarray[a]);
			aa.append(",");
		}
		aa.deleteCharAt(aa.length() - 1);
		String acsd = aa.toString();
		return acsd;
	}

	/********************************* 导出Excel报表处理核心方法 */
	/**
	 * @param response
	 *            HttpServletResponse对象
	 * 
	 * @param title
	 *            Excel文件中显示的标题
	 * @author gao 2014-05-08
	 * @param fileName
	 *            导出时Excel的文件名称
	 * @param orgId
	 *            用户组织id信息
	 * @param imageUrl
	 *            显示图片的url地址集合
	 * @param head
	 *            报表需要显示的中文列信息
	 * @param field
	 *            对应数据库中的列信息
	 * @return null
	 * @param ddicName
	 *            当前模块的数据字典英文名称编码
	 * @param v
	 *            d动态参数集合
	 * @throws Exception
	 */

	public String exportDataExcel(HttpServletResponse response, String fileName, String title, Vector<String> imageUrl, String head, String field, String orgId, String ddicName, Vector<String> v) throws Exception {

		OutputStream os = response.getOutputStream();//
		response.reset();
		response.setContentType("application/vnd.ms-excel");// 设置生成的文件类型
		imageUrl = null;
		response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + "-" + new Date().toLocaleString() + ".xls");// 设置浏览器的header
																																	// 信息

		Vector<File> files = new Vector<File>();
		WritableWorkbook wbook = Workbook.createWorkbook(os); // 创建一个WritableWorkbook对象，
		String tmptitle = title; //
		WritableSheet wsheet = wbook.createSheet(tmptitle, 0); // 通过wbook对象创建一个WritableSheet
																// 对象
		String sql = "";
		Map<String, Object> map = DataModelMap.getMapObject();
		DataDictionary ddic = null;
		String tabNameString = ddicName.trim();
		ddic = (DataDictionary) map.get(tabNameString);// 从数据字典缓存中获取字典数据信息
		if (ddic == null || "".equals(ddic)) {
			ddic = dataitemsInfoService.findTableSqlWhereByListFaceId(tabNameString);
			map.put(tabNameString, ddic);
		}
		head = this.fieldToHeaders(field, ddic);
		String heads[] = StringManagerUtils.splitString(head, ",");
		String columns[] = StringManagerUtils.splitString(field, ",");
		StringBuffer sqlwhere = new StringBuffer();
		/***
		 * 
		 * 动态拼接sql 信息
		 * 
		 * ***/
		String tables=ddic.getSql().substring(ddic.getSql().indexOf(" from ")+1);
		sqlwhere.append("select ");
		if(field.indexOf("gtcjsj")>0){
			field=field.replaceAll("gtcjsj","to_char(gtcjsj,'YYYY-MM-DD  hh24:mi:ss') as gtcjsj");
		}else if(field.indexOf("cjsj")>0){
			field=field.replaceAll("cjsj","to_char(cjsj,'YYYY-MM-DD  hh24:mi:ss') as cjsj");
		}
		sqlwhere.append(field);
		sqlwhere.append("  "+tables);
		sqlwhere.append(" and org_id in (" + orgId + ")");
		List<String> dyns = ddic.getParams();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (StringManagerUtils.isNotNull(v.get(i))) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		if (StringManagerUtils.isNotNull(ddic.getGroup())) {
			sqlwhere.append(" group by " + ddic.getGroup());//加上分组信息
		}
		if(StringManagerUtils.isNotNull( ddic.getOrder())){
			sqlwhere.append("   order by " + ddic.getOrder());
		}
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		List<?> datas = null;
		int index = sql.indexOf("@");// 判断sql是否存在“@”符号
		String oldsqlString = sql;
		if (index > -1) {
			sql = getSqlReplace(sql.toString());
		}

		if (StringManagerUtils.isNotNull(params)) {
			datas = this.findExecuteSqlQuery(sql, o);
		} else {
			datas = this.findExecuteSqlQuery(sql);
		}
		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat wcfFC = new WritableCellFormat(wfont);
		wcfFC.setBackground(Colour.WHITE);
		wsheet.addCell(new Label(heads.length / 2, 0, tmptitle, wcfFC));
		wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 6, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		wcfFC = new WritableCellFormat(wfont);// 设置Excel 列的属性 字体、颜色等

		File imgFile = null;
		while (imageUrl != null) {
			for (int i = 0; i < imageUrl.size(); i++) {
				imgFile = new File(imageUrl.get(i));
				files.add(imgFile);
			}
		}
		WritableFont font1 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);// 定义字体
		WritableCellFormat titleWritableFormat = new WritableCellFormat(font1);// 定义格式化对象
		titleWritableFormat.setAlignment(Alignment.CENTRE);// 水平居中显示

		// wsheet.setRowView(1,30);// 设置行高
		titleWritableFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
		for (int i = 0; i < heads.length; i++) {
			// wsheet.setColumnView(i, cellView);
			wsheet.setColumnView(i, 15);// 设置列宽
			Label excelTitle = new Label(i, 1, heads[i], titleWritableFormat);// 动态创建表头列
			wsheet.addCell(excelTitle);
			// wsheet.addCell(new Label(i, 2, heads[i], format));
		}
		int count = 0;
		/***
		 * 循环插入数据项信息
		 * ***/
		for (Object obj : datas) {
			count++;
			Object[] oo = (Object[]) obj;
			for (int i = 0; i < columns.length; i++) {
				String temp = columns[i];
				if (columns[i].equalsIgnoreCase("gtsj") || columns[i].equalsIgnoreCase("bgt") || columns[i].equalsIgnoreCase("dlqx") || columns[i].equalsIgnoreCase("bjbz")) {
					wsheet.setColumnView(i, 20);
					Label excelTitle = new Label(i, count + 1, "", titleWritableFormat);
					wsheet.addCell(excelTitle);
				} else if (columns[i].equalsIgnoreCase("cjsj") || columns[i].equalsIgnoreCase("gtcjsj")) {
					wsheet.setColumnView(i, 30);
					Label excelTitle = new Label(i, count + 1, oo[i] + "", titleWritableFormat);
					wsheet.addCell(excelTitle);
				} else if (columns[i].equalsIgnoreCase("jssj")) {
					wsheet.setColumnView(i, 20);
					Label excelTitle = new Label(i, count + 1, oo[i] + "", titleWritableFormat);
					wsheet.addCell(excelTitle);
				} else {
					if (columns[i].equalsIgnoreCase("id") || columns[i].contains("as id")) {
						wsheet.setColumnView(i, 10);
						Label excelTitle = new Label(i, count + 1, count + "", titleWritableFormat);
						wsheet.addCell(excelTitle);

					} else {
						wsheet.setColumnView(i, 16);
						String value = oo[i] + "";
						if (null == value || value.equals("null") || !StringManagerUtils.isNotNull(value)) {
							value = "";
						} else {
							if (!StringManagerUtils.stringDataFiter(temp)) {
								value = StringManagerUtils.formatReportPrecisionValue(value);
							}
						}

						Label excelTitle = new Label(i, count + 1, value, titleWritableFormat);
						wsheet.addCell(excelTitle);
					}
				}
			}
		}

		wbook.write();
		wbook.close();
		os.close();
		return null;
	}

	public String exportDataWellExcel(HttpServletResponse response, String fileName, String title, Vector<String> imageUrl, String head, String field, String orgId, String ddicName, Vector<String> v) throws Exception {
		OutputStream os = response.getOutputStream();//
		response.reset();
		response.setContentType("application/vnd.ms-excel");// 设置生成的文件类型
		imageUrl = null;
		response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + "-" + new Date().toLocaleString() + ".xls");//
		Vector<File> files = new Vector<File>();
		WritableWorkbook wbook = Workbook.createWorkbook(os); //
		String tmptitle = title; //
		WritableSheet wsheet = wbook.createSheet(tmptitle, 0); //
		String sql = "";
		Map<String, Object> map = DataModelMap.getMapObject();
		DataDictionary ddic = null;
		ddic = (DataDictionary) map.get(ddicName);
		if (ddic == null || "".equals(ddic)) {
			ddic = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);
			map.put(ddicName, ddic);
		}
		head = this.fieldToHeaders(field, ddic);
		String heads[] = StringManagerUtils.splitString(head, ",");
		String columns[] = StringManagerUtils.splitString(field, ",");
		StringBuffer sqlwhere = new StringBuffer();
		String tables=ddic.getSql().substring(ddic.getSql().indexOf(" from ")+1);
		sqlwhere.append("select ");
		if(field.indexOf("gtcjsj")>0){
			field=field.replaceAll("gtcjsj","to_char(gtcjsj,'YYYY-MM-DD  hh24:mi:ss') as gtcjsj");
		}else if(field.indexOf("cjsj")>0){
			field=field.replaceAll("cjsj","to_char(cjsj,'YYYY-MM-DD  hh24:mi:ss') as cjsj");
		}
		sqlwhere.append(field);
		sqlwhere.append("  "+tables);
		sqlwhere.append(" and org_id in (" + orgId + ")");
		List<String> dyns = ddic.getParams();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (StringManagerUtils.isNotBlank(v.get(i))) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		sqlwhere.append("order by  " + ddic.getOrder());
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		List<?> datas = null;
		sql = this.getSqlReplace(sql);
		if (StringManagerUtils.isNotNull(params)) {

			datas = this.findExecuteSqlQuery(sql, o);
		} else {
			datas = this.findExecuteSqlQuery(sql);
		}
		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat wcfFC = new WritableCellFormat(wfont);
		wcfFC.setBackground(Colour.WHITE);
		wsheet.addCell(new Label(heads.length / 2, 0, tmptitle, wcfFC));
		wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 6, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		wcfFC = new WritableCellFormat(wfont);

		File imgFile = null;
		while (imageUrl != null) {
			for (int i = 0; i < imageUrl.size(); i++) {
				imgFile = new File(imageUrl.get(i));
				files.add(imgFile);
			}
		}
		WritableFont font1 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);// 定义字体
		WritableCellFormat titleWritableFormat = new WritableCellFormat(font1);// 定义格式化对象
		titleWritableFormat.setAlignment(Alignment.CENTRE);// 水平居中显示

		// wsheet.setRowView(1,30);// 设置行高
		titleWritableFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
		for (int i = 0; i < heads.length; i++) {
			// wsheet.setColumnView(i, cellView);
			wsheet.setColumnView(i, 15);// 设置列宽
			Label excelTitle = new Label(i, 1, heads[i], titleWritableFormat);
			wsheet.addCell(excelTitle);
			// wsheet.addCell(new Label(i, 2, heads[i], format));
		}
		int count = 0;
		for (Object obj : datas) {
			count++;
			Object[] oo = (Object[]) obj;
			for (int i = 0; i < columns.length; i++) {
				String temp = columns[i];
				if (columns[i].equalsIgnoreCase("gtsj") || columns[i].equalsIgnoreCase("bgt") || columns[i].equalsIgnoreCase("dlqx") || columns[i].equalsIgnoreCase("bjbz")) {
					wsheet.setColumnView(i, 20);
					Label excelTitle = new Label(i, count + 1, "", titleWritableFormat);
					wsheet.addCell(excelTitle);
				} else if (columns[i].equalsIgnoreCase("cjsj") || columns[i].equalsIgnoreCase("gtcjsj")) {
					wsheet.setColumnView(i, 30);
					Label excelTitle = new Label(i, count + 1, oo[i] + "", titleWritableFormat);
					wsheet.addCell(excelTitle);
				} else if (columns[i].equalsIgnoreCase("jssj")) {
					wsheet.setColumnView(i, 20);
					Label excelTitle = new Label(i, count + 1, oo[i] + "", titleWritableFormat);
					wsheet.addCell(excelTitle);
				} else {
					if (columns[i].equalsIgnoreCase("id") || columns[i].equalsIgnoreCase("jh as id")) {
						wsheet.setColumnView(i, 10);
						Label excelTitle = new Label(i, count + 1, count + "", titleWritableFormat);
						wsheet.addCell(excelTitle);

					} else {
						wsheet.setColumnView(i, 16);
						String value = oo[i] + "";
						if (null == value || value.equals("null") || !StringManagerUtils.isNotNull(value)) {
							value = "";
						} else {
							if (!StringManagerUtils.stringDataFiter(temp)) {
								value = StringManagerUtils.formatReportPrecisionValue(value);
							}
						}

						Label excelTitle = new Label(i, count + 1, value, titleWritableFormat);
						wsheet.addCell(excelTitle);
					}
				}
			}
		}

		wbook.write();
		wbook.close();
		os.close();
		return null;
	}

	public boolean exportReportPumpunitCustomDataExcel(HttpServletResponse response, String fileName, String title, Vector<String> imageUrl, String head, String field, String orgId, DataModels data, Vector<String> v) {
		WritableWorkbook wbook = null;
		try {
			OutputStream os = response.getOutputStream();//
			response.reset();//
			imageUrl = null;
			response.setHeader("Content-disposition", "attachment; filename=" + fileName + "-" + new Date().toLocaleString() + ".xls");//
			response.setContentType("application/msexcel");//
			Vector<File> files = new Vector<File>();
			wbook = Workbook.createWorkbook(os); //
			String tmptitle = title; //
			WritableSheet wsheet = wbook.createSheet(tmptitle, 0); //
			String sql = "";
			Map<String, Object> map = DataModelMap.getMapObject();
			DataDictionary ddic = null;
			ddic = (DataDictionary) map.get("pumpUnitDayReport");
			if (ddic == null || "".equals(ddic)) {
				ddic = dataitemsInfoService.findTableSqlWhereByListFaceId("pumpUnitDayReport");
				map.put("pumpUnitDayReport", ddic);
			}
			head = this.fieldToHeaders(field, ddic);
			String heads[] = StringManagerUtils.splitString(head, ",");
			// String columns[] = StringManagerUtils.splitString(field, ",");
			String columns[] = StringManagerUtils.splitString("id,jh,pfdjrcyl,pfdjrcyl1,hsl,bz", ",");

			StringBuffer sqlwhere = new StringBuffer();
			sqlwhere.append("select id,jh,pfdjrcyl,pfdjrcyl1,hsl,bz from (select " + field + " from ");
			sqlwhere.append(data.getTables() + " where 1=1 ");
			sqlwhere.append(data.getCondSqls());
			sqlwhere.append(" and o.org_id in (" + orgId + ")");
			List<String> dyns = data.getDynamics();
			StringBuffer valueWhere = new StringBuffer();
			if (dyns.size() != 0) {
				for (int i = 0; i < dyns.size(); i++) {
					if (!v.get(i).equalsIgnoreCase("null") && !"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
						if (!v.get(i).equals("new")) {
							sqlwhere.append("  " + dyns.get(i) + " ");
							valueWhere.append(v.get(i) + ",");
						}
					}
				}
			}
			sqlwhere.append(")");
			String getResult = "";
			sql = sqlwhere.toString();
			String params = valueWhere.toString();
			Object[] o = params.split(",");
			List<?> datas = null;
			if (StringManagerUtils.isNotNull(params)) {
				datas = this.findExecuteSqlQuery(sql, o);
			} else {
				datas = this.findExecuteSqlQuery(sql);
			}
			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat wcfFC = new WritableCellFormat(wfont);
			wcfFC.setBackground(Colour.WHITE);
			wsheet.addCell(new Label(heads.length / 2, 0, tmptitle, wcfFC));
			wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 6, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			wcfFC = new WritableCellFormat(wfont);

			File imgFile = null;
			while (imageUrl != null) {
				for (int i = 0; i < imageUrl.size(); i++) {
					imgFile = new File(imageUrl.get(i));
					files.add(imgFile);
				}
			}
			WritableFont font1 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);// 定义字体
			WritableCellFormat titleWritableFormat = new WritableCellFormat(font1);// 定义格式化对象
			titleWritableFormat.setAlignment(Alignment.CENTRE);// 水平居中显示

			// wsheet.setRowView(1,30);// 设置行高
			titleWritableFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
			for (int i = 0; i < heads.length; i++) {
				// wsheet.setColumnView(i, cellView);
				wsheet.setColumnView(i, 15);// 设置列宽
				Label excelTitle = new Label(i, 3, heads[i], titleWritableFormat);
				wsheet.addCell(excelTitle);
				// wsheet.addCell(new Label(i, 2, heads[i], format));
			}
			int count = 0;
			for (Object obj : datas) {
				count++;
				Object[] oo = (Object[]) obj;
				for (int i = 0; i < columns.length; i++) {
//					String temp = columns[i];
					if (columns[i].equalsIgnoreCase("gtsj") || columns[i].equalsIgnoreCase("bgt") || columns[i].equalsIgnoreCase("dlqx")) {
						wsheet.setColumnView(i, 20);
						Label excelTitle = new Label(i, count, "", titleWritableFormat);
						wsheet.addCell(excelTitle);
					} else if (columns[i].equalsIgnoreCase("cjsj") || columns[i].equalsIgnoreCase("gtcjsj")) {
						wsheet.setColumnView(i, 30);
						Label excelTitle = new Label(i, count, oo[i] + "", titleWritableFormat);
						wsheet.addCell(excelTitle);
					} else if (columns[i].equalsIgnoreCase("jssj")) {
						wsheet.setColumnView(i, 20);
						Label excelTitle = new Label(i, count, oo[i] + "", titleWritableFormat);
						wsheet.addCell(excelTitle);
					} else {
						if (columns[i].equalsIgnoreCase("id") || columns[i].equalsIgnoreCase("jh as id")) {
							wsheet.setColumnView(i, 10);
							Label excelTitle = new Label(i, count, count + "", titleWritableFormat);
							wsheet.addCell(excelTitle);

						} else {
							wsheet.setColumnView(i, 16);
							String value = oo[i] + "";
							if (null == value || "null".equals(value) || !StringManagerUtils.isNotNull(value)) {
								value = "";
							} else {
								value = StringManagerUtils.formatReportPrecisionValue(value);
							}
							Label excelTitle = new Label(i, count, value, titleWritableFormat);
							wsheet.addCell(excelTitle);
						}
					}
				}
			}
			wbook.write();
			wbook.close();
			os.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	// 获取日期
	public List reportDateJssj(String sql) {
		return this.getBaseDao().MonthJssj(sql);
	}


	public boolean currentWellExist(String jh, List<String> list) {
		boolean flag = false;
		for (String s : list) {
			if (jh.equalsIgnoreCase(s)) {
				flag = true;
			}
		}
		return flag;

	}

	
	public String findReportRiverMonthDataById(Page pager, String orgId, String jh, String jssj) {
		String sql = "";
		StringBuffer sqlwhere = new StringBuffer();
		StringBuffer nsqlwhere = new StringBuffer();
		sqlwhere.append("select  jh, yf, ypjyl, ypjyl1, yzhhs, cc, cc1, bj, bs," + " dym, cmd, phd, xtxl,zbx, cjwd, ylyl, ylyl1, zlyl, zlyl1,bz from ");
		sqlwhere.append(" v_005_13_reportrivermonth_cn u,tbl_org o where 1=1 and u.dwbh=o.org_code ");
		sqlwhere.append(" and o.org_id in (" + orgId + ")");

		nsqlwhere.append("select  jh, yf, ypjyl, ypjyl1, yzhhs,cc,cc1,  bj, bs," + " dym, cmd,zbx,cjwd,  ylyl, ylyl1, zlyl, zlyl1,bz from (select t.jh,substr(t.rq,0,7) as yf,avg(t.yl) ypjyl,avg(yl1) as ypjyl1," + "avg(t.hs) as yzhhs,avg(cc) as cc,avg(cc1) as cc1," + "avg(t.bj) as bj,avg(t.bs) as bs ,avg(t.dym) as dym ,avg(t.cmd) as cmd,avg(zbx) as zbx,avg(cjwd) as cjwd ,sum(t.yl) ylyl ,sum(yl1) as ylyl1," + " avg(l.ljyl) as zlyl,avg(l.ljyl1) as zlyl1,t.bz  ");
		nsqlwhere.append(" from   VIEW_DAYREPORT t,T_100_LHJM l ,tbl_org o  where l.jbh=t.jbh  and t.dwbh=o.org_code ");
		nsqlwhere.append(" and o.org_id in (" + orgId + ") ");
		StringBuffer valueWhere = new StringBuffer();
		StringBuffer nvalueWhere = new StringBuffer();
		if (!"".equals(jh) && null != jh && jh.length() > 0) {
			sqlwhere.append(" and u.jh like ? ");
			valueWhere.append(jh + ",");
			nsqlwhere.append(" and jh like ? ");
			nvalueWhere.append(jh + ",");
		}

		if (!"".equals(jssj) && null != jssj && jssj.length() > 0) {
			if (!jssj.equals("new")) {
				sqlwhere.append(" and u.yf like ?  ");
				valueWhere.append(jssj + ",");
				nsqlwhere.append("  and substr(t.rq,0,7) like  ?  ");
				nvalueWhere.append(jssj + ",");
			}
		}
		nsqlwhere.append("group by substr(t.rq,0,7) ,jh,t.bz order by jh )");

		// valueWhere.deleteCharAt(valueWhere.length() - 1);
		String getResult = "";
		List<?> list1;
		List<?> list2;
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		String params2 = nvalueWhere.toString();
		Object[] o2 = params2.split(",");
		if (StringManagerUtils.isNotNull(params)) {
			list1 = this.findBySqlRiverJHJMEntity(sql.toString(), pager, o);
		} else {
			list1 = this.findBySqlRiverJHJMEntity(sql.toString(), pager);
		}
		if (StringManagerUtils.isNotNull(params)) {
			list2 = this.findBySqlRiverJHJMEntity(nsqlwhere.toString(), pager, o2);
		} else {
			list2 = this.findBySqlRiverJHJMEntity(nsqlwhere.toString(), pager);
		}
		StringBuffer jsonBuf = new StringBuffer();
		String[] str = splitPageSql(sql);
		String[] str2 = splitPageSql(nsqlwhere.toString());
		String strs = "";
		List<String> wells = new ArrayList<String>();
		if (list2.size() == list1.size()) {
			getResult = this.jsonPageRiverSql(list1, sql, pager);
		} else if (list2.size() > list1.size()) {
			jsonBuf.append("{success:true,start:" + pager.getStart());
			jsonBuf.append(",totalCount:" + pager.getTotalCount());
			jsonBuf.append(",totalRoot:[");
			if (null != list1 && list1.size() > 0) {
				for (int i = 0; i < list1.size(); i++) {
					Object[] obj = (Object[]) list1.get(i);
					strs += "{";
					for (int j = 0; j < str.length; j++) {
						String attrstring = str[j].toString().trim();
						String[] attr = attrstring.split(" as ");
						int attr_length = attr.length;
						wells.add(obj[0] + "");
						if (attr_length > 1) {
							strs += "\"" + attr[1].trim() + "\":\"" + obj[j] + "\",";
						} else {
							String attr_left = attr[0];
							// String[] left_val=attr_left.split("\\.");
							String value = obj[j] + "";
							if (null == value || value.equals("null") || !StringManagerUtils.isNotNull(value)) {
								value = "";
							} else {
								value = StringManagerUtils.formatReportPrecisionValue(value);
							}
							strs += "\"" + attr_left.trim() + "\":\"" + value + "\",";
						}
					}
					strs = strs.substring(0, strs.length() - 1);
					strs += "},";
				}

			}
			if (null != list2 && list2.size() > 0) {
				for (int i = 0; i < list2.size(); i++) {
					Object[] obj = (Object[]) list2.get(i);
					String curjh = obj[0] + "";
					if (!this.currentWellExist(curjh, wells)) {
						strs += "{";
						for (int j = 0; j < str2.length; j++) {
							String attrstring = str2[j].toString().trim();
							String[] attr = attrstring.split(" as ");
							int attr_length = attr.length;
							if (attr_length > 1) {
								strs += "\"" + attr[1].trim() + "\":\"" + obj[j] + "\",";
							} else {
								String attr_left = attr[0];
								// String[] left_val=attr_left.split("\\.");
								String value = obj[j] + "";
								if (null == value || value.equals("null") || !StringManagerUtils.isNotNull(value)) {
									value = "";
								} else {
									value = StringManagerUtils.formatReportPrecisionValue(value);
								}
								strs += "\"" + attr_left.trim() + "\":\"" + value + "\",";
							}
						}
						strs = strs.substring(0, strs.length() - 1);
						strs += "},";
					}

				}
			}
			strs = strs.substring(0, strs.length() - 1);
			strs += "]}";
			jsonBuf.append(strs);
			getResult = jsonBuf.toString();
		}

		
		return getResult;

	}

	
	/**
	 * @param pager
	 * @param orgId
	 * @param jh
	 * @param jssj
	 * @return 高青综合日报底部统计表
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String findReportGaoQinStatisticsDataById(Page pager, String orgId, String jh, String jssj, String minusDate) throws IOException, SQLException {
		String sql = "";
		StringBuffer sqlwhere = new StringBuffer();
		StringBuffer nsqlwhere = new StringBuffer();
		// String yf = StringManagerUtils.getCurrentDayMonth(jssj);
		sqlwhere.append(" select count(jh) as hj,count(jh) as zjs,sum(yl) as zrcyl,sum(yl1) as zrcyl1,sum(yly) as zyljyl, ");
		sqlwhere.append(" sum(yly1) as zyljyl1,sum(nly) as znljyl,sum(nly1) as znljyl1  from v_005_13_reportgaoqin t  ");
		sqlwhere.append(" where   to_char(t.tjsj,'yyyy-MM-dd') like '%" + jssj + "%'");
		StringBuffer valueWhere = new StringBuffer();
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		String getResult = "''";
		String columns = "''";
		if (StringManagerUtils.isNotNull(params)) {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, " ", pager, o);
		} else {
			getResult = this.findPageBySqlEntity(sql.toString(), columns, " ", pager);
		}
		StringBuffer jsonBuf = new StringBuffer();
		String[] str = splitPageSql(sql);
		
		return getResult;
	}

	
	// 到出辽河金马月报表
	public boolean exportReportRiverMonthExcel(HttpServletResponse response, String fileName, String title, String head, String field, String orgId, Vector<String> v) {
		try {
			OutputStream os = response.getOutputStream();//
			response.reset();//
			response.setHeader("Content-disposition", "attachment; filename=" + fileName + "-" + new Date().toLocaleString() + ".xls");//
			response.setContentType("application/msexcel");//
			Vector<File> files = new Vector<File>();
			WritableWorkbook wbook = Workbook.createWorkbook(os); //
			String tmptitle = title; //
			WritableSheet wsheet = wbook.createSheet(tmptitle, 0); //
			String sql = "";
			String heads[] = StringManagerUtils.splitString(head, ",");
			String columns[] = StringManagerUtils.splitString(field, ",");
			StringBuffer sqlwhere = new StringBuffer();
			StringBuffer nsqlwhere = new StringBuffer();
			sqlwhere.append("select jh as id, jh, yf, ypjyl, ypjyl1, yzhhs, cc, cc1, bj, bs," + " dym, cmd, phd, xtxl,zbx, cjwd, ylyl, ylyl1, zlyl, zlyl1,bz from ");
			sqlwhere.append(" v_005_13_reportrivermonth_cn u,tbl_org o where 1=1 and u.dwbh=o.org_code ");
			sqlwhere.append(" and o.org_id in (" + orgId + ")");

			nsqlwhere.append("select   jh as id, jh, yf, ypjyl, ypjyl1, yzhhs,cc,cc1,  bj, bs," + " dym, cmd,phd,xtxl,zbx,cjwd,  ylyl, ylyl1, zlyl, zlyl1,bz from (select t.jh,substr(t.rq,0,7) as yf,avg(t.yl) ypjyl,avg(yl1) as ypjyl1," + "avg(t.hs) as yzhhs,avg(cc) as cc,avg(cc1) as cc1," + "avg(t.bj) as bj,avg(t.bs) as bs ,avg(t.dym) as dym ,avg(t.cmd) as cmd,avg(zbx) as phd,avg(zbx) as xtxl,avg(zbx) as zbx,avg(cjwd) as cjwd ,sum(t.yl) ylyl ,sum(yl1) as ylyl1,"
					+ " avg(l.ljyl) as zlyl,avg(l.ljyl1) as zlyl1,t.bz  ");
			nsqlwhere.append(" from   VIEW_DAYREPORT t,T_100_LHJM l ,tbl_org o  where l.jbh=t.jbh  and t.dwbh=o.org_code ");
			nsqlwhere.append(" and o.org_id in (" + orgId + ") ");
			StringBuffer valueWhere = new StringBuffer();
			StringBuffer nvalueWhere = new StringBuffer();
			if (!"".equals(v.get(0)) && null != v.get(0) && v.get(0).length() > 0) {
				sqlwhere.append(" and u.jh like ? ");
				valueWhere.append(v.get(0) + ",");
				nsqlwhere.append(" and jh like ? ");
				nvalueWhere.append(v.get(0) + ",");
			}

			if (!"".equals(v.get(1)) && null != v.get(1) && v.get(1).length() > 0) {
				if (!v.get(1).equals("new")) {
					sqlwhere.append(" and u.yf like ?  ");
					valueWhere.append(v.get(1) + ",");
					nsqlwhere.append("  and substr(t.rq,0,7) like ?  ");
					nvalueWhere.append(v.get(1) + ",");
				}
			}
			nsqlwhere.append("group by substr(t.rq,0,7) ,jh,t.bz order by jh )");
			String getResult = "";
			sql = sqlwhere.toString();
			List<?> list1;
			List<?> list2;
			sql = sqlwhere.toString();
			String params = valueWhere.toString();
			Object[] o = params.split(",");
			String params2 = nvalueWhere.toString();
			Object[] o2 = params2.split(",");
			if (StringManagerUtils.isNotNull(params)) {
				list1 = this.findExecuteSqlQuery(sql, o);
			} else {
				list1 = this.findExecuteSqlQuery(sql);
			}
			if (StringManagerUtils.isNotNull(params2)) {
				list2 = this.findExecuteSqlQuery(nsqlwhere.toString(), o2);
			} else {
				list2 = this.findExecuteSqlQuery(nsqlwhere.toString());
			}
			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat wcfFC = new WritableCellFormat(wfont);
			wcfFC.setBackground(Colour.WHITE);
			wsheet.addCell(new Label(heads.length / 2, 0, tmptitle, wcfFC));
			wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 6, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			wcfFC = new WritableCellFormat(wfont);

			File imgFile = null;
			WritableFont font1 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);// 定义字体
			WritableCellFormat titleWritableFormat = new WritableCellFormat(font1);// 定义格式化对象
			titleWritableFormat.setAlignment(Alignment.CENTRE);// 水平居中显示
			titleWritableFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
			for (int i = 0; i < heads.length; i++) {
				wsheet.setColumnView(i, 15);// 设置列宽
				Label excelTitle = new Label(i, 3, heads[i], titleWritableFormat);
				wsheet.addCell(excelTitle);
			}
			int count = 0;
			if (list1.size() == list2.size()) {
				for (Object obj : list1) {
					count++;
					Object[] oo = (Object[]) obj;
					for (int i = 0; i < columns.length; i++) {
						String temp = columns[i];
						if (columns[i].equalsIgnoreCase("rq")) {
							wsheet.setColumnView(i, 20);
							Label excelTitle = new Label(i, count + 3, oo[i] + "", titleWritableFormat);
							wsheet.addCell(excelTitle);
						} else {
							if (columns[i].equalsIgnoreCase("id") || columns[i].equalsIgnoreCase("jh as id")) {
								wsheet.setColumnView(i, 10);
								Label excelTitle = new Label(i, count + 3, count + "", titleWritableFormat);
								wsheet.addCell(excelTitle);

							} else {
								wsheet.setColumnView(i, 16);
								String value = oo[i] + "";
								if (null == value || value.equals("null") || !StringManagerUtils.isNotNull(value)) {
									value = "";
								} else {
									if (!(columns[i].equalsIgnoreCase("bj"))) {
										value = StringManagerUtils.formatReportPrecisionValue(value);
									}
								}
								Label excelTitle = new Label(i, count + 3, value, titleWritableFormat);
								wsheet.addCell(excelTitle);
							}
						}
					}
				}
			} else if (list2.size() > list1.size()) {
				List<String> wells = new ArrayList<String>();
				// 生产正常list1的数据
				for (Object obj : list1) {
					count++;
					Object[] oo = (Object[]) obj;
					String curjh = oo[0] + "";
					wells.add(curjh);
					for (int i = 0; i < columns.length; i++) {
						String temp = columns[i];
						if (columns[i].equalsIgnoreCase("yf")) {
							wsheet.setColumnView(i, 20);
							Label excelTitle = new Label(i, count + 3, oo[i] + "", titleWritableFormat);
							wsheet.addCell(excelTitle);
						} else {
							if (columns[i].equalsIgnoreCase("id") || columns[i].equalsIgnoreCase("jh as id")) {
								wsheet.setColumnView(i, 10);
								Label excelTitle = new Label(i, count + 3, count + "", titleWritableFormat);
								wsheet.addCell(excelTitle);

							} else {
								wsheet.setColumnView(i, 16);
								String value = oo[i] + "";
								if (null == value || value.equals("null") || !StringManagerUtils.isNotNull(value)) {
									value = "";
								} else {
									if (!(columns[i].equalsIgnoreCase("bj"))) {
										value = StringManagerUtils.formatReportPrecisionValue(value);
									}
								}
								Label excelTitle = new Label(i, count + 3, value, titleWritableFormat);
								wsheet.addCell(excelTitle);
							}
						}
					}
				}

				// count-=1;
				// 循环产生数据异常的信息
				for (Object obj : list2) {

					Object[] oo = (Object[]) obj;
					String jh = oo[0] + "";
					if (!this.currentWellExist(jh, wells)) {// 去掉上面已经有的井名信息
						count++;
						for (int i = 0; i < columns.length; i++) {
							String temp = columns[i];
							if (columns[i].equalsIgnoreCase("yf")) {
								wsheet.setColumnView(i, 20);
								Label excelTitle = new Label(i, count + 3, oo[i] + "", titleWritableFormat);
								wsheet.addCell(excelTitle);
							} else {
								if (columns[i].equalsIgnoreCase("id") || columns[i].equalsIgnoreCase("jh as id")) {
									wsheet.setColumnView(i, 10);
									Label excelTitle = new Label(i, count + 3, count + "", titleWritableFormat);
									wsheet.addCell(excelTitle);
								} else if (columns[i].equalsIgnoreCase("phd") || columns[i].equalsIgnoreCase("xtxl")) {
									wsheet.setColumnView(i, 10);
									Label excelTitle = new Label(i, count + 3, "", titleWritableFormat);
									wsheet.addCell(excelTitle);

								} else {
									wsheet.setColumnView(i, 16);
									String value = "";
									if (i <= 19) {
										value = oo[i] + "";
									}
									if (null == value || value.equals("null") || !StringManagerUtils.isNotNull(value)) {
										value = "";
									} else {
										if (!(columns[i].equalsIgnoreCase("bj"))) {
											value = StringManagerUtils.formatReportPrecisionValue(value);
										}
									}
									Label excelTitle = new Label(i, count + 3, value, titleWritableFormat);
									wsheet.addCell(excelTitle);
								}
							}
						}
					}
				}
			}
			wbook.write();
			wbook.close();
			os.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public String loadReportRiverWellInfoByJh(String orgId, String jh, String type) throws Exception {
		// String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();

		String sql = "";

		sql = " select distinct w.jh as jh, w.jh as dm from tbl_wellinformation w,tbl_rpc_total_day t ,tbl_org o where 1=1 and w.dwbh=o.org_code and o.org_id in(" + orgId + ")";
		if (StringManagerUtils.isNotNull(jh)) {
			sql += " and w.jh like '%" + jh + "%'";
		}
		if (type.equalsIgnoreCase("jh")) {
			sql += " order by w.jh";
		}
		try {
			List<?> list = this.findCallSql(sql);
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

	public String queryReportGaoqinByJh(String orgId, String jh, String type) throws Exception {
		// String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();

		String sql = "";

		sql = " select distinct w.jh as jh, w.jh as dm from tbl_wellinformation w,tbl_rpc_total_day t ,tbl_org o where 1=1 and w.dwbh=o.org_code and o.org_id in(" + orgId + ")";
		if (StringManagerUtils.isNotNull(jh)) {
			sql += " and w.jh like '%" + jh + "%'";
		}
		if (type.equalsIgnoreCase("jh")) {
			sql += " order by w.jh";
		}
		try {
			List<?> list = this.findCallSql(sql);
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

	public String findAlarmSetDataById(Page pager) {
		String sql = "";
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append("select t.id, w.workingconditioncode,w.workingconditionname, t.alarmtype,code1.itemname as alarmtypename, t.alarmlevel,code2.itemname as alarmlevelname,t.alarmsign,t.remark");
		sqlwhere.append(" from tbl_rpc_worktype w   ");
		sqlwhere.append(" left outer join tbl_rpc_alarmtype_conf  t  on t.workingconditioncode = w.workingconditioncode  ");
		sqlwhere.append(" left outer join tbl_code   code1  on t.alarmtype=code1.itemvalue  and code1.itemcode='BJLX'");
		sqlwhere.append(" left outer join tbl_code  code2   on t.alarmlevel=code2.itemvalue and code2.itemcode='BJJB'");
		sqlwhere.append(" where w.workingconditioncode>=1100");
		sqlwhere.append(" order by w.workingconditioncode");
		String getResult = "";
		sql = sqlwhere.toString();
		String columns = this.showTableHeadersColumns("alarmSet");
		getResult = this.findPageBySqlEntity(sql, columns, pager);
		
		return getResult;

	}

	/**
	 * <p>
	 * 描述：前台报警信息查询模块
	 * </p>
	 * 
	 * @author lvlei 2014-05-26
	 * @param pager
	 * @param JH
	 *            井名
	 * @param BJJB
	 *            报警级别
	 * @param BJLX
	 *            报警类型
	 * @param orgId
	 *            组织ID
	 * @return
	 */
	public String alarmSelectinfo(Page pager, String JH, String BJJB, String BJLX, String orgId) {
		String Sql = "";
		Sql = "Select jh,bjsj,bjsm,bjz,bjlx,bjjb from (select jh,to_char(bjsj,'YYYY-MM-DD hh24:mi:ss') as bjsj ,bjsm,bjz,t2.itemname as bjlx,t3.itemname as bjjb " + "from T_040_ALARMINFORMATION t,tbl_wellinformation t1," + "tbl_code t2 ,tbl_code t3,tbl_org o " + "where  o.ORG_CODE=t1.dwbh  and o.ORG_ID  in (" + orgId + ") and  t1.jlbh=t.jbh and t2.itemcode='BJLX' and t3.itemcode='BJJB' and t.bjjb=t3.itemvalue and t.bjlx=t2.itemvalue ";
		if (StringManagerUtils.isNotNull(JH)) {
			Sql += " and t1.jlbh =" + JH + "";
		}
		if (StringManagerUtils.isNotNull(BJJB)) {
			Sql += " and t.BJJB like '%" + BJJB + "%'";
		}
		if (StringManagerUtils.isNotNull(BJLX)) {
			Sql += " and t.BJLX like '%" + BJLX + "%'";
		}
		Sql += " order by bjsj desc)";
		Sql += " order  by bjsj desc";
		String getResult = "";
		getResult = this.findPageBySqlEntity(Sql, pager);
		
		return getResult;

	}

	/**
	 * <p>
	 * 描述：前台RFID按条件查询调用的方法
	 * </p>
	 * 
	 * @author 吕磊 2014-06-11
	 * @param pager
	 * @param JC
	 *            井场
	 * @param XM
	 *            姓名
	 * @param HFBZ
	 *            合法标志
	 * @param orgId
	 *            组织ID
	 * @return
	 */
	public String rfidFrontObtaininfo(Page pager, String jc, String xm, String hfbz, String orgId) {
		String Sql = "";
		Sql = "Select jlbh,rfidkh,ygbh,ygxm,xb,dwbh,dwmc,ddjc,rcsj,lcsj,hfbz " + "from (select distinct to_char(r.rcsj,'YYYY-MM-DD hh24:mi:ss') as rcsj,t.jlbh,t.rfidkh,t.ygbh,t.ygxm,t.xb,t.bmbh as dwbh,t.bmmc as dwmc,t1.jc as ddjc," + "to_char(r.lcsj,'YYYY-MM-DD hh24:mi:ss') as lcsj," + "r.hfbz from t_042_rfidinformation t,t_043_rfidrecord r,tbl_org o ,tbl_wellinformation t1 " + "where  o.ORG_CODE=t1.dwbh  and o.ORG_ID  in (" + orgId + ")  and t.rfidkh=r.rfidkh and r.ddjc=t1.jc ";
		if (StringManagerUtils.isNotNull(jc)) {
			Sql += " and r.ddjc = '" + jc + "'";
		}
		if (StringManagerUtils.isNotNull(hfbz)) {
			Sql += " and r.hfbz = " + hfbz;
		}
		if (StringManagerUtils.isNotNull(xm)) {
			Sql += " and t.ygxm ='" + xm + "'";
		}
		Sql += " order by rcsj desc)";
		String getResult = "";
		getResult = this.findPageBySqlEntity(Sql, pager);
		return getResult;
	}

	/**
	 * <p>
	 * 描述：后台RFID编辑模块初始话调用方法
	 * </p>
	 * 
	 * @author 吕磊 2014-06-09
	 * @param pager
	 * @return
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String rfidSelectinfo(Page pager) throws IOException, SQLException {
		String Sql = "select jlbh as id,rfidkh,ygxm,ygbh,xb,bmbh,bmmc from t_042_rfidinformation";
		String getResult = "";
		String columns = this.showTableHeadersColumns("rfidInfo");
		String limit = "";
		getResult = this.findPageBySqlEntity(Sql, columns, limit, pager);
		
		return getResult;

	}

	/**
	 * <p>
	 * 描述：报警流程管理
	 * </p>
	 * 
	 * @author lvlei 2014-05-26
	 * @param fields
	 * @param data
	 * @return 返回相应列对应的表头
	 * @throws Exception
	 *             导出excel时需要调用
	 */
	public String fieldToHeaders(String fields, DataDictionary ddic) throws Exception {
		List<String> headers = ddic.getHeaders();
		StringBuffer stringBuffer = new StringBuffer();
		List<String> columns = ddic.getFields();
		String[] field = fields.split(",");
		for (int i = 0; i < columns.size(); i++) {
			for (int s = 0; s < field.length; s++) {
				String[] attr = field[s].trim().split(" as ");
				String curField = "";
				if (attr.length > 1) {
					curField = attr[1].trim();
				} else {
					curField = attr[0].trim();
				}
				if (columns.get(i).trim().equalsIgnoreCase(curField)) {
					stringBuffer.append(headers.get(i) + ",");
				}
			}

		}
		if (stringBuffer.length() > 0) {
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		}
		return stringBuffer.toString();
	}

	/**
	 * <p>
	 * 描述：从码表中加载下拉菜单数据信息
	 * </p>
	 * 
	 * @parm type 当前参数类型
	 * @return
	 * @throws Exception
	 */
	public String loadMenuTypeData(String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		sql = " select t.itemvalue,t.itemname from tbl_code t where  itemcode='" + type + "'";
		try {
			List<?> list = this.getfindByIdList(sql);
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
	
	//漏失分析
	public String findLeakageAnalysisDataById(Page pager, String orgId, String ddicName, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		String sqlAll = "";
		int limit = 25;
		StringBuffer sqlwhere = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();// 获取一个map对象
		DataDictionary ddic = null;
		ddic = (DataDictionary) map.get(ddicName);// 从缓存中获取当前模块的字典信息,如果不存在，则从数据库中查询
		if (ddic == null || "".equals(ddic)) {
			ddic = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);// 从字典表里获取字典数据信息
			map.put(ddicName, ddic);
		}
		String columns = ddic.getTableHeader();
		/***
		 * 动态拼接sql 语句
		 * */
		sqlwhere.append(ddic.getSql());
		sqlwhere.append(" and org_id in (" + orgId + ")");
		StringBuffer valueWhere = new StringBuffer();// 存放动态参数相应的值，拼接后的字符串格式为："s4-4,2014-08-08"
		List<String> dyns = ddic.getParams();
		/***
		 * 动态拼接sql 语句 中的动态参数信息 例如 w.jh=?
		 * */
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		if (StringManagerUtils.isNotNull(ddic.getGroup())) {
			sqlwhere.append(" group by " + ddic.getGroup());//加上分组信息
		}
		sqlAll=sqlwhere.toString();
		//在sqlAll的基础上加上自定义的分页参数信息,三层嵌套
		if (StringManagerUtils.isNotNull(ddic.getOrder())) {
			sqlwhere.append(" order by " + ddic.getOrder());//最内层语句加上排序信息
		}
		sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
		sqlCuswhere.append(""+sqlwhere);
		int maxvalue=pager.getLimit()+pager.getStart();
		sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
		sqlCuswhere.append(" where rn >"+pager.getStart());
		
		String getResult = "";
		sql = sqlCuswhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {// 当参数字符串不为空时，调用该方法获取 json 字符串
			getResult = this.findLeakageAnalysisBySqlEntity(sqlAll,sql, columns, 20 + "", pager, o);
		} else {
			getResult = this.findLeakageAnalysisBySqlEntity(sqlAll,sql, columns, 20 + "", pager);
		}
		return getResult;
	}
	
	//多井漏失系数
	public String findMultiWellLeakageDataById(Page pager, String jh, String orgId, String ddicName, Vector<String> v) throws IOException, SQLException {
		String sql = "";
		String sqlAll = "";
		int limit = 25;
		StringBuffer sqlwhere = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		Map<String, Object> map = DataModelMap.getMapObject();// 获取一个map对象
		DataDictionary ddic = null;
		ddic = (DataDictionary) map.get(ddicName);// 从缓存中获取当前模块的字典信息,如果不存在，则从数据库中查询
		if (ddic == null || "".equals(ddic)) {
			ddic = dataitemsInfoService.findTableSqlWhereByListFaceId(ddicName);// 从字典表里获取字典数据信息
			map.put(ddicName, ddic);
		}
		String columns = ddic.getTableHeader();
		/***
		 * 动态拼接sql 语句
		 * */
		sqlwhere.append(ddic.getSql());
		sqlwhere.append(" and org_id in (" + orgId + ")");
		sqlwhere.append(" and jh in (" + jh + ")");
		StringBuffer valueWhere = new StringBuffer();// 存放动态参数相应的值，拼接后的字符串格式为："s4-4,2014-08-08"
		List<String> dyns = ddic.getParams();
		/***
		 * 动态拼接sql 语句 中的动态参数信息 例如 w.jh=?
		 * */
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		if (StringManagerUtils.isNotNull(ddic.getGroup())) {
			sqlwhere.append(" group by " + ddic.getGroup());//加上分组信息
		}
		sqlAll=sqlwhere.toString();
		//在sqlAll的基础上加上自定义的分页参数信息,三层嵌套
		if (StringManagerUtils.isNotNull(ddic.getOrder())) {
			sqlwhere.append(" order by " + ddic.getOrder());//最内层语句加上排序信息
		}
		sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
		sqlCuswhere.append(""+sqlwhere);
		int maxvalue=pager.getLimit()+pager.getStart();
		sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
		sqlCuswhere.append(" where rn >"+pager.getStart());
		
		String getResult = "";
		sql = sqlCuswhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		if (StringManagerUtils.isNotNull(params)) {// 当参数字符串不为空时，调用该方法获取 json 字符串
			getResult = this.findLeakageAnalysisBySqlEntity(sqlAll,sql, columns, 20 + "", pager, o);
		} else {
			getResult = this.findLeakageAnalysisBySqlEntity(sqlAll,sql, columns, 20 + "", pager);
		}
		return getResult;
	}
	
	public <T> String exportLeakageAnalysisExcel(HttpServletResponse response, String fileName, String title, Vector<String> imageUrl, String head, String field, String orgId, String ddicName, Vector<String> v) throws Exception {

		OutputStream os = response.getOutputStream();//
		response.reset();
		response.setContentType("application/vnd.ms-excel");// 设置生成的文件类型
		imageUrl = null;
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + "-" + new Date().toLocaleString() + ".xls");// 设置浏览器的header
																																	// 信息

		Vector<File> files = new Vector<File>();
		WritableWorkbook wbook = Workbook.createWorkbook(os); // 创建一个WritableWorkbook对象，
		String tmptitle = title; //
		WritableSheet wsheet = wbook.createSheet(tmptitle, 0); // 通过wbook对象创建一个WritableSheet
																// 对象
		String sql = "";
		Map<String, Object> map = DataModelMap.getMapObject();
		DataDictionary ddic = null;
		String tabNameString = ddicName.trim();
		ddic = (DataDictionary) map.get(tabNameString);// 从数据字典缓存中获取字典数据信息
		if (ddic == null || "".equals(ddic)) {
			ddic = dataitemsInfoService.findTableSqlWhereByListFaceId(tabNameString);
			map.put(tabNameString, ddic);
		}
		head = this.fieldToHeaders(field, ddic);
		String heads[] = StringManagerUtils.splitString(head, ",");
		String columns[] = StringManagerUtils.splitString(field, ",");
		StringBuffer sqlwhere = new StringBuffer();
		/***
		 * 
		 * 动态拼接sql 信息
		 * 
		 * ***/
		String tables=ddic.getSql().substring(ddic.getSql().indexOf(" from ")+1);
		sqlwhere.append("select ");
		if(field.indexOf("gtcjsj")>0){
			field=field.replaceAll("gtcjsj","to_char(gtcjsj,'YYYY-MM-DD  hh24:mi:ss') as gtcjsj");
		}else if(field.indexOf("cjsj")>0){
			field=field.replaceAll("cjsj","to_char(cjsj,'YYYY-MM-DD  hh24:mi:ss') as cjsj");
		}
		sqlwhere.append(field);
		sqlwhere.append("  "+tables);
		sqlwhere.append(" and org_id in (" + orgId + ")");
		List<String> dyns = ddic.getParams();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!v.get(i).equalsIgnoreCase("null") && !"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		if (StringManagerUtils.isNotNull(ddic.getGroup())) {
			sqlwhere.append(" group by " + ddic.getGroup());//加上分组信息
		}
		if(StringManagerUtils.isNotNull( ddic.getOrder())){
			sqlwhere.append("   order by " + ddic.getOrder());
		}
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		List<T> datas = null;
		List<T> avgs = null;
		int index = sql.indexOf("@");// 判断sql是否存在“@”符号
		String oldsqlString = sql;
		if (index > -1) {
			sql = getSqlReplace(sql.toString());
		}
		
		String sqlAvg = "select ";
		for(int i = 0; i < columns.length;i++){
//			String[] attr = columns[i].split(" as ");
//			if (null != attr && attr.length > 1) {
//				col[i] = attr[attr.length-1];
//			}
			if( columns[i].equals("id") ){
				sqlAvg += "(max(" + columns[i] + ")+1) as id,";
			}else if( columns[i].equals("jssj") ){
				sqlAvg += "'平均值' as jssj,";
			}else if( columns[i].contains("pf") || columns[i].contains("jljh")){
				sqlAvg += "avg(" + columns[i] + "),";
			}else if( columns[i].contains("js" ) && !columns[i].equals("jssj") ){
				sqlAvg += "avg(" + columns[i] + "),";
			}else if( columns[i].contains("lsxs") ){
				sqlAvg += "decode(avg(" + columns[i-1] + "),0,null,null,null," + "avg(" + columns[i-2] + ")/avg(" + columns[i-1] + ")) as lsxs,";
			}else{
				sqlAvg += "null as " + columns[i] +",";
			}
		}
		sqlAvg = sqlAvg.substring(0, sqlAvg.length()-1);
		sqlAvg += " from (" + sql + ")";
		if (StringManagerUtils.isNotNull(params)) {
			datas = this.findLeakageSqlQuery(sql, o);
			avgs = this.findLeakageSqlQuery(sqlAvg, o);
		} else {
			datas = this.findLeakageSqlQuery(sql);
			avgs = this.findLeakageSqlQuery(sqlAvg, o);
		}
		if (null != avgs && avgs.size() > 0) {
			datas.add(avgs.get(0));
		}

		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat wcfFC = new WritableCellFormat(wfont);
		wcfFC.setBackground(Colour.WHITE);
		wsheet.addCell(new Label(heads.length / 2, 0, tmptitle, wcfFC));
		wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 6, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		wcfFC = new WritableCellFormat(wfont);// 设置Excel 列的属性 字体、颜色等

		File imgFile = null;
		while (imageUrl != null) {
			for (int i = 0; i < imageUrl.size(); i++) {
				imgFile = new File(imageUrl.get(i));
				files.add(imgFile);
			}
		}
		WritableFont font1 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);// 定义字体
		WritableCellFormat titleWritableFormat = new WritableCellFormat(font1);// 定义格式化对象
		titleWritableFormat.setAlignment(Alignment.CENTRE);// 水平居中显示

		// wsheet.setRowView(1,30);// 设置行高
		titleWritableFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
		for (int i = 0; i < heads.length; i++) {
			// wsheet.setColumnView(i, cellView);
			wsheet.setColumnView(i, 15);// 设置列宽
			Label excelTitle = new Label(i, 1, heads[i], titleWritableFormat);// 动态创建表头列
			wsheet.addCell(excelTitle);
			// wsheet.addCell(new Label(i, 2, heads[i], format));
		}
		int count = 0;
		/***
		 * 循环插入数据项信息
		 * ***/
		for (Object obj : datas) {
			count++;
			Object[] oo = (Object[]) obj;
			for (int i = 0; i < columns.length; i++) {
				String temp = columns[i];
				if (columns[i].equalsIgnoreCase("gtsj") || columns[i].equalsIgnoreCase("bgt") || columns[i].equalsIgnoreCase("dlqx") || columns[i].equalsIgnoreCase("bjbz")) {
					wsheet.setColumnView(i, 20);
					Label excelTitle = new Label(i, count + 1, "", titleWritableFormat);
					wsheet.addCell(excelTitle);
				} else if (columns[i].equalsIgnoreCase("cjsj") || columns[i].equalsIgnoreCase("gtcjsj")) {
					wsheet.setColumnView(i, 30);
					Label excelTitle = new Label(i, count + 1, oo[i] + "", titleWritableFormat);
					wsheet.addCell(excelTitle);
				} else if (columns[i].equalsIgnoreCase("jssj")) {
					wsheet.setColumnView(i, 20);
					Label excelTitle = new Label(i, count + 1, oo[i] + "", titleWritableFormat);
					wsheet.addCell(excelTitle);
				} else {
					if (columns[i].equalsIgnoreCase("id") || columns[i].contains("as id")) {
						wsheet.setColumnView(i, 10);
						Label excelTitle = new Label(i, count + 1, count + "", titleWritableFormat);
						wsheet.addCell(excelTitle);

					} else {
						wsheet.setColumnView(i, 16);
						String value = oo[i] + "";
						if (null == value || value.equals("null") || !StringManagerUtils.isNotNull(value)) {
							value = "";
						} else if (sql.contains("v_012_") && temp.contains("lsxs")) {
							float value2 = StringManagerUtils.stringToFloat(value,4); // 将漏失分析中的漏失系数保留4位小数
							value = value2 + "";
						} else if (!StringManagerUtils.stringDataFiter(temp)) {
							value = StringManagerUtils.formatReportPrecisionValue(value);
						}
						Label excelTitle = new Label(i, count + 1, value, titleWritableFormat);
						wsheet.addCell(excelTitle);
					}
				}
			}
		}
		wbook.write();
		wbook.close();
		os.close();
		return null;
	}
	
	// 多井漏失导出excel
	public <T> String exportMultiWellLeakageExcel(HttpServletResponse response, String fileName, String title, Vector<String> imageUrl, String head, String field, String orgId, String ddicName, Vector<String> v, String jh) throws Exception {

		OutputStream os = response.getOutputStream();//
		response.reset();
		response.setContentType("application/vnd.ms-excel");// 设置生成的文件类型
		imageUrl = null;
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + "-" + new Date().toLocaleString() + ".xls");// 设置浏览器的header
																																	// 信息

		Vector<File> files = new Vector<File>();
		WritableWorkbook wbook = Workbook.createWorkbook(os); // 创建一个WritableWorkbook对象，
		String tmptitle = title; //
		WritableSheet wsheet = wbook.createSheet(tmptitle, 0); // 通过wbook对象创建一个WritableSheet
																// 对象
		String sql = "";
		Map<String, Object> map = DataModelMap.getMapObject();
		DataDictionary ddic = null;
		String tabNameString = ddicName.trim();
		ddic = (DataDictionary) map.get(tabNameString);// 从数据字典缓存中获取字典数据信息
		if (ddic == null || "".equals(ddic)) {
			ddic = dataitemsInfoService.findTableSqlWhereByListFaceId(tabNameString);
			map.put(tabNameString, ddic);
		}
		head = this.fieldToHeaders(field, ddic);
		String heads[] = StringManagerUtils.splitString(head, ",");
		String columns[] = StringManagerUtils.splitString(field, ",");
		StringBuffer sqlwhere = new StringBuffer();
		/***
		 * 
		 * 动态拼接sql 信息
		 * 
		 * ***/
		String tables=ddic.getSql().substring(ddic.getSql().indexOf(" from ")+1);
		sqlwhere.append("select ");
		if(field.indexOf("gtcjsj")>0){
			field=field.replaceAll("gtcjsj","to_char(gtcjsj,'YYYY-MM-DD  hh24:mi:ss') as gtcjsj");
		}else if(field.indexOf("cjsj")>0){
			field=field.replaceAll("cjsj","to_char(cjsj,'YYYY-MM-DD  hh24:mi:ss') as cjsj");
		}
		sqlwhere.append(field);
		sqlwhere.append("  "+tables);
		sqlwhere.append(" and org_id in (" + orgId + ")");
		sqlwhere.append(" and jh in (" + jh + ")");
		List<String> dyns = ddic.getParams();
		StringBuffer valueWhere = new StringBuffer();
		if (dyns.size() != 0) {
			for (int i = 0; i < dyns.size(); i++) {
				if (!v.get(i).equalsIgnoreCase("null") && !"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
					if (!v.get(i).equals("new")) {
						sqlwhere.append("  " + dyns.get(i) + " ");
						valueWhere.append(v.get(i) + ",");
					}
				}
			}
		}
		if (StringManagerUtils.isNotNull(ddic.getGroup())) {
			sqlwhere.append(" group by " + ddic.getGroup());//加上分组信息
		}
		if(StringManagerUtils.isNotNull( ddic.getOrder())){
			sqlwhere.append("   order by " + ddic.getOrder());
		}
		String getResult = "";
		sql = sqlwhere.toString();
		String params = valueWhere.toString();
		Object[] o = params.split(",");
		List<T> datas = null;
		List<T> avgs = null;
		int index = sql.indexOf("@");// 判断sql是否存在“@”符号
		String oldsqlString = sql;
		if (index > -1) {
			sql = getSqlReplace(sql.toString());
		}
		String sqlAvg = "select ";
		for(int i = 0; i < columns.length;i++){
			String[] attr = columns[i].split(" as ");
			if (null != attr && attr.length > 1) {
				columns[i] = attr[attr.length-1];
			}
			if( columns[i].equals("id") ){
				sqlAvg += "(max(" + columns[i] + ")+1) as id,";
			}else if( columns[i].equals("jssj") ){
				sqlAvg += "'平均值' as jssj,";
			}else if( columns[i].contains("pf") || columns[i].contains("jljh")){
				sqlAvg += "avg(" + columns[i] + "),";
			}else if( columns[i].contains("js" ) && !columns[i].equals("jssj") ){
				sqlAvg += "avg(" + columns[i] + "),";
			}else if( columns[i].contains("lsxs") ){
				sqlAvg += "decode(avg(" + columns[i-1] + "),0,null,null,null," + "avg(" + columns[i-2] + ")/avg(" + columns[i-1] + ")) as lsxs,";
			}else{
				sqlAvg += "null as " + columns[i] +",";
			}
		}
		sqlAvg = sqlAvg.substring(0, sqlAvg.length()-1);
		sqlAvg += " from (" + sql + ")";
		if (StringManagerUtils.isNotNull(params)) {
			datas = this.findLeakageSqlQuery(sql, o);
			avgs = this.findLeakageSqlQuery(sqlAvg, o);
		} else {
			datas = this.findLeakageSqlQuery(sql);
			avgs = this.findLeakageSqlQuery(sqlAvg, o);
		}
		if (null != avgs && avgs.size() > 0) {
			datas.add(avgs.get(0));
		}
		
		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat wcfFC = new WritableCellFormat(wfont);
		wcfFC.setBackground(Colour.WHITE);
		wsheet.addCell(new Label(heads.length / 2, 0, tmptitle, wcfFC));
		wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 6, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		wcfFC = new WritableCellFormat(wfont);// 设置Excel 列的属性 字体、颜色等

		File imgFile = null;
		while (imageUrl != null) {
			for (int i = 0; i < imageUrl.size(); i++) {
				imgFile = new File(imageUrl.get(i));
				files.add(imgFile);
			}
		}
		WritableFont font1 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);// 定义字体
		WritableCellFormat titleWritableFormat = new WritableCellFormat(font1);// 定义格式化对象
		titleWritableFormat.setAlignment(Alignment.CENTRE);// 水平居中显示

		// wsheet.setRowView(1,30);// 设置行高
		titleWritableFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
		for (int i = 0; i < heads.length; i++) {
			// wsheet.setColumnView(i, cellView);
			wsheet.setColumnView(i, 15);// 设置列宽
			Label excelTitle = new Label(i, 1, heads[i], titleWritableFormat);// 动态创建表头列
			wsheet.addCell(excelTitle);
			// wsheet.addCell(new Label(i, 2, heads[i], format));
		}
		int count = 0;
		/***
		 * 循环插入数据项信息
		 * ***/
		for (Object obj : datas) {
			count++;
			Object[] oo = (Object[]) obj;
			for (int i = 0; i < columns.length; i++) {
				String temp = columns[i];
				if (columns[i].equalsIgnoreCase("gtsj") || columns[i].equalsIgnoreCase("bgt") || columns[i].equalsIgnoreCase("dlqx") || columns[i].equalsIgnoreCase("bjbz")) {
					wsheet.setColumnView(i, 20);
					Label excelTitle = new Label(i, count + 1, "", titleWritableFormat);
					wsheet.addCell(excelTitle);
				} else if (columns[i].equalsIgnoreCase("cjsj") || columns[i].equalsIgnoreCase("gtcjsj")) {
					wsheet.setColumnView(i, 30);
					Label excelTitle = new Label(i, count + 1, oo[i] + "", titleWritableFormat);
					wsheet.addCell(excelTitle);
				} else if (columns[i].equalsIgnoreCase("jssj")) {
					wsheet.setColumnView(i, 20);
					Label excelTitle = new Label(i, count + 1, oo[i] + "", titleWritableFormat);
					wsheet.addCell(excelTitle);
				} else {
					if (columns[i].equalsIgnoreCase("id") || columns[i].contains("as id")) {
						wsheet.setColumnView(i, 10);
						Label excelTitle = new Label(i, count + 1, count + "", titleWritableFormat);
						wsheet.addCell(excelTitle);

					} else {
						wsheet.setColumnView(i, 16);
						String value = oo[i] + "";
						if (null == value || value.equals("null") || !StringManagerUtils.isNotNull(value)) {
							value = "";
						} else if (sql.contains("v_012_") && temp.contains("lsxs")) {
							float value2 = StringManagerUtils.stringToFloat(value,4); // 将漏失分析中的漏失系数保留4位小数
							value = value2 + "";
						} else if (!StringManagerUtils.stringDataFiter(temp)) {
							value = StringManagerUtils.formatReportPrecisionValue(value);
						}
						Label excelTitle = new Label(i, count + 1, value, titleWritableFormat);
						wsheet.addCell(excelTitle);
					}
				}
			}
		}

		wbook.write();
		wbook.close();
		os.close();
		return null;
	}
	
	public boolean exportGridPanelData(HttpServletResponse response,String fileName,String title,String head,String field,String data) {
		try{
		//生成excel文件
			OutputStream os = response.getOutputStream();//
			response.reset();
			response.setContentType("application/vnd.ms-excel");// 设置生成的文件类型
			fileName += "-" + StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss") + ".xls";
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));//
			Vector<File> files = new Vector<File>();
			WritableWorkbook wbook = Workbook.createWorkbook(os);
			WritableSheet wsheet = wbook.createSheet(title, 0); //
			String heads[]=head.split(",");
			String columns[]=field.split(",");
			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat wcfFC = new WritableCellFormat(wfont);
			wcfFC.setBackground(Colour.WHITE);
			wsheet.addCell(new Label(heads.length / 2, 0, title, wcfFC));
			wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 6, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			wcfFC = new WritableCellFormat(wfont);
			WritableFont font1 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);// 定义字体
			WritableCellFormat titleWritableFormat = new WritableCellFormat(font1);// 定义格式化对象
			titleWritableFormat.setAlignment(Alignment.CENTRE);// 水平居中显示

			// wsheet.setRowView(1,30);// 设置行高
			titleWritableFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
			for (int i = 0; i < heads.length; i++) {
				wsheet.setColumnView(i, 15);// 设置列宽
				Label excelTitle = new Label(i, 1, heads[i], titleWritableFormat);
				wsheet.addCell(excelTitle);
			}
			JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+data+"}");//解析数据
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			int count = 0;
			for (int i=0;i<jsonArray.size();i++) {
				JSONObject everydata = JSONObject.fromObject(jsonArray.getString(i));
				count++;
				for (int j = 0; j < columns.length; j++) {
					Label excelTitle=null;
					if (columns[j].equalsIgnoreCase("id") || columns[j].equalsIgnoreCase("jlbh")) {
						wsheet.setColumnView(j, 10);
						excelTitle = new Label(j, count+1, count + "", titleWritableFormat);
					}else {
						if(columns[j].equalsIgnoreCase("jssj")||columns[j].equalsIgnoreCase("cjsj")||columns[j].equalsIgnoreCase("gtcjsj")
								||columns[j].indexOf("time")>0||columns[j].indexOf("date")>0||columns[j].indexOf("Date")>0){
							wsheet.setColumnView(j, 30);
						}else{
							wsheet.setColumnView(j, 16);
						}
						if(everydata.has(columns[j])){
							excelTitle = new Label(j, count+1,everydata.getString(columns[j]),titleWritableFormat);
						}else{
							excelTitle = new Label(j, count+1,"",titleWritableFormat);
						}
						
					}
					wsheet.addCell(excelTitle);
				}
			}
			wbook.write();
			wbook.close();
			os.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	
	public boolean exportGridPanelDataWhithOutTime(HttpServletResponse response,String fileName,String title,String head,String field,String data) {
		try{
		//生成excel文件
			OutputStream os = response.getOutputStream();//
			response.reset();
			response.setContentType("application/vnd.ms-excel");// 设置生成的文件类型
			fileName +=".xls";
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));//
			Vector<File> files = new Vector<File>();
			WritableWorkbook wbook = Workbook.createWorkbook(os);
			WritableSheet wsheet = wbook.createSheet(title, 0); //
			String heads[]=head.split(",");
			String columns[]=field.split(",");
			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat wcfFC = new WritableCellFormat(wfont);
			wcfFC.setBackground(Colour.WHITE);
//			wsheet.addCell(new Label(heads.length / 2, 0, title, wcfFC));
			wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 6, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			wcfFC = new WritableCellFormat(wfont);
			WritableFont font1 = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false, jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);// 定义字体
			WritableCellFormat titleWritableFormat = new WritableCellFormat(font1);// 定义格式化对象
			titleWritableFormat.setAlignment(Alignment.CENTRE);// 水平居中显示

			// wsheet.setRowView(1,30);// 设置行高
			titleWritableFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 设置边框线
			for (int i = 0; i < heads.length; i++) {//添加列名
				wsheet.setColumnView(i, 15);// 设置列宽
				Label excelTitle = new Label(i, 0, heads[i], titleWritableFormat);
				wsheet.addCell(excelTitle);
			}
			JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+data+"}");//解析数据
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			int count = 0;
			for (int i=0;i<jsonArray.size();i++) {
				JSONObject everydata = JSONObject.fromObject(jsonArray.getString(i));
				count++;
				for (int j = 0; j < columns.length; j++) {
					Label excelTitle=null;
					if (columns[j].equalsIgnoreCase("id") || columns[j].equalsIgnoreCase("jlbh")) {
						wsheet.setColumnView(j, 10);
						excelTitle = new Label(j, count, count + "", titleWritableFormat);
					}else {
						if(columns[j].equalsIgnoreCase("jssj")||columns[j].equalsIgnoreCase("cjsj")||columns[j].equalsIgnoreCase("gtcjsj")
								||columns[j].indexOf("time")>0||columns[j].indexOf("date")>0||columns[j].indexOf("Date")>0){
							wsheet.setColumnView(j, 30);
						}else{
							wsheet.setColumnView(j, 16);
						}
						if(everydata.has(columns[j])){
							excelTitle = new Label(j, count,everydata.getString(columns[j]),titleWritableFormat);
						}else{
							excelTitle = new Label(j, count,"",titleWritableFormat);
						}
						
					}
					wsheet.addCell(excelTitle);
				}
			}
			wbook.write();
			wbook.close();
			os.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
