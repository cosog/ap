package com.cosog.service.base;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;





import oracle.sql.BLOB;
import oracle.sql.CLOB;

//import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cosog.dao.BaseDao;
import com.cosog.model.AlarmShowStyle;
import com.cosog.model.scada.CallbackDataItems;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.GenericsUtils;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

/**
 * <p>
 * 核心服务类,其他的服务Service继承该服务类，获取到baseDao接口类
 * </p>
 * 
 * @author gao 2014-06-06
 * 
 * @param <T>
 */

@SuppressWarnings({ "unchecked", "hiding", "rawtypes" })
@Component("baseService")
public class BaseService<T> {
	/**
	 * 处理SQL特别字符串
	 * 
	 * @param str
	 * @return String
	 * @author qiands
	 */
	public static String indexOfString(String str) {
		String rresult = "";
		int result = str.indexOf("distinct");
		if (result >= 0) {
			String res = str.trim();
			String rlt = res.substring(8, res.length());
			rresult = rlt;
		} else {
			rresult = str;
		}
		return rresult;
	}

	/**
	 * 注入baseDao
	 */
	private BaseDao baseDao = new BaseDao();

	/**
	 * 注入返回类型对象
	 */
	protected Class<T> entityClass;

	/**
	 * Logger 日志
	 */
	protected Logger log = LoggerFactory.getLogger(getClass());

	public BaseService() {
		entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	}

	public <T> void addObject(T clazz) {
		this.baseDao.addObject(clazz);
	}

	/**
	 * 存储过程调用删除 传入数组String[] ->删除(带占位符的)
	 * 
	 * @param callSql
	 * @param values
	 * @return
	 */
	public void deleteCallParameter(final String callSql, final Object... values) {
		baseDao.deleteCallParameter(callSql, values);
	}

	/**
	 * 批量删除对象[数组] 带占位符的
	 * 
	 * @param Object
	 *            [] parametValue
	 * @param parametName
	 * @param parametValue
	 */
	public void deleteHqlQueryParameter(String queryString, String parametName, Object[] parametValue, Object... values) {
		baseDao.deleteQueryParameter(queryString, parametName, parametValue, values).executeUpdate();
	}

	/**
	 * 修改对象
	 * 
	 * @param object
	 */
	public void edit(Object object) throws Exception {
		baseDao.edit(object);
	}

	/**
	 * <p>
	 * 描述：HQL查询 根据传入的hql语句查询
	 * </p>
	 * 
	 * @param queryString
	 *            HQL语句
	 * @param values
	 *            HQL参数
	 * @return
	 */
	public List<T> find(String hql, Object... values) throws Exception {
		List<T> result = baseDao.find(hql, values);
		return result;
	}

	/**
	 * @描述: 得到分页查询当前页的业务对象列表信息
	 * @参数：ecPage,页面参数传递对象，为查询提供过滤、分页、排序的参数
	 */

	public List<T> findAllPageByEntity(Page page) {
		List<T> result = null;
		StringBuffer where = new StringBuffer();
		StringBuffer hql = new StringBuffer();
		String sort = "";
		List whereSet = null;
		if (null == entityClass) {
			return null;
		}
		if (null != page) {

			// 根据参数拼写HQL查询条件串
			whereSet = page.getWhere();
			if (null != whereSet) {
				for (int i = 0; i < whereSet.size(); i++) {
					where.append(whereSet.get(i).toString());
					if (i != (whereSet.size() - 1))
						where.append(" and ");
				}
			}

			// 排序条件
			sort = page.getSort();
		}

		// 只取列表需要显示和处理的字段信息
		hql.append("  from  ");
		hql.append(entityClass.getName());

		// 增加过滤条件
		if (StringManagerUtils.isNotNull(where.toString())) {
			hql.append("  where ");
			hql.append(where.toString());
		}
		// 增加排序条件
		if (StringManagerUtils.isNotNull(sort)) {
			hql.append(" order by ");
			hql.append(sort);
		}

		result = baseDao.getAllPageByHql(hql.toString(), page);

		return result;
	}

	/**
	 * 根据ID获取一个对象，如果查不到返回null
	 * 
	 * @param id
	 * @return
	 */
	public T findById(Serializable id) throws Exception {
		return baseDao.get(entityClass, id);
	}

	public List<?> findBySqlRiverJHJMEntity(String sql, Page pager, Object... values) {
		List<?> pageList = baseDao.getAllPageBySql(sql.toString(), pager, values);
		return pageList;
	}

	/**
	 * 存储过程调用查询
	 * 
	 * @param queryString
	 *            callSql语句
	 * @param values
	 *            callSql参数
	 * @author qiands
	 * @return List<?>
	 */
	public List<?> findCallSql(final String callSql, final Object... values) {
		return baseDao.findCallSql(callSql, values);
	}

	public String findPageBySqlEntity(String sql, Page pager, Object... values) {
		List<?> pageList = baseDao.getAllPageBySql(sql.toString(), pager, values);
		// sql 转化为json格式
		String json = jsonPageSql(pageList, sql, pager);
		return json;
	}

	/**
	 * @描述: 得到分页查询当前页的业务对象列表信息,-------根据sql语句
	 * @参数：entityClass, 一个业务对象实体类
	 * @参数：ecPage,页面参数传递对象，为查询提供过滤、分页、排序的参数
	 * @参数：sql
	 * @author qiands
	 */
	public String findPageBySqlEntity(String sql, Page pager, Vector<String> v) {
		List<?> pageList = baseDao.getAllPageBySql(sql.toString(), pager, v);
		// sql 转化为json格式
		String json = jsonPageSql(pageList, sql, pager);
		return json;
	}

	/**
	 * <p>
	 * 描述： 调用getAllPageBySql（）查询当前分页的数据信息，然后调用jsonPageSql()动态拼接json
	 * </p>
	 * 
	 * @param sql
	 *            传入的sql
	 * 
	 * @author gao 2014-05-08
	 * @param columns
	 *            构建前台columns表头的数据 信息
	 * @param pager
	 *            分页工具类
	 * @param values
	 *            动态参数集合
	 * @return json 数据信息
	 */
	public String findPageBySqlEntity(String sql, String columns, Page pager, Object... values) {
		int index = sql.indexOf("@");
		String oldsqlString = sql;
		if (index > -1) {
			sql = getSqlReplace(sql.toString());
		}
		List<?> pageList = baseDao.getAllPageBySql(sql, pager, values);
		// sql 转化为json格式
		String json = jsonPageSql(pageList, oldsqlString, columns, pager);
		return json;
	}
	
	public String findExportExcelData(String sql, String columns, Page pager, Object... values) {
		int index = sql.indexOf("@");
		String oldsqlString = sql;
		if (index > -1) {
			sql = getSqlReplace(sql.toString());
		}
		List<?> pageList = baseDao.getSQLObjects(sql);
		// sql 转化为json格式
		String json = exportExcelJson(pageList, oldsqlString, columns, pager);
		return json;
	}
	
	/**
	 * 拼接有空记录的电子表格数据
	 */
	public String findPageBySqlEntity(int recordCount,String sql, String columns, Page pager, Object... values) {
		int index = sql.indexOf("@");
		String oldsqlString = sql;
		if (index > -1) {
			sql = getSqlReplace(sql.toString());
		}
		List<?> pageList = baseDao.getAllPageBySql(sql, pager, values);
		// sql 转化为json格式
		String json = jsonPageSql2(recordCount,pageList, oldsqlString, columns, pager);
		return json;
	}
	
	/**
	 * <p>
	 * 描述： 调用getAllPageBySql（）查询当前分页的数据信息，然后调用jsonPageSql()动态拼接json
	 * </p>
	 * 
	 * @param sql
	 *            传入的sql
	 * 
	 * @author gao 2014-05-08
	 * @param columns
	 *            构建前台columns表头的数据 信息
	 * @param pager
	 *            分页工具类
	 * @param values
	 *            动态参数集合
	 * @return json 数据信息
	 */
	public String findPageBySqlEntityEditer(String sql, String columns, Page pager, Object... values) {
		int index = sql.indexOf("@");
		String oldsqlString = sql;
		if (index > -1) {
			sql = getSqlReplace(sql.toString());
		}
		List<?> pageList = baseDao.getAllPageBySql(sql, pager, values);
		// sql 转化为json格式
		String json = jsonPageSql(pageList, oldsqlString, columns, pager);
		return json;
	}

	/**
	 * <p>
	 * 描述： 调用getAllPageBySql（）查询当前分页的数据信息，然后调用jsonPageSql()动态拼接json 我的分页查询核心方法
	 * </p>
	 * 
	 * @param sql
	 *            传入的sql
	 * @author gao 2014-05-08
	 * @param columns
	 *            构建前台columns 的数据信息
	 * @param limit
	 *            分页大小 可以传一个空值
	 * @param pager
	 *            分页工具类
	 * @param values
	 *            动态参数集合
	 * @return json 数据信息
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String findPageBySqlEntity(String sql, String columns, String limit, Page pager, Object... values) throws IOException, SQLException {
		int index = sql.indexOf("@");
		String oldsqlString = sql;
		if (index > -1) {
			sql = getSqlReplace(sql.toString());
		}
		List<?> pageList = baseDao.getAllPageBySql(sql, pager, values);
		// sql 转化为json格式
		String json = jsonPageSql(pageList, oldsqlString, columns, "10000", pager);// 该方法中存在处理数据保留2为小数的方法
		return json.replaceAll("null", "\"\"");
	}

	public String findCustomPageBySqlEntity(String sqlAll, String sql, String columns, String limit, Page pager, Object... values) throws IOException, SQLException {
		int index = sql.indexOf("@");
		String oldsqlString = sql;
		if (index > -1) {
			sql = getSqlReplace(sql.toString());
		}
		int indexall = sqlAll.indexOf("@");
		if (indexall > -1) {
			sqlAll = getSqlReplace(sqlAll.toString());
		}
		List<?> pageList = baseDao.getMyCustomPageBySql(sqlAll, sql, pager, values);
		// sql 转化为json格式
		String json = jsonPageSql(pageList, oldsqlString, columns, "10000", pager);// 该方法中存在处理数据保留2为小数的方法
		return json;
	}
	
	public String findExportDataBySqlEntity(String sqlAll, String sql, String columns, String limit, Page pager, Object... values) {
		int index = sql.indexOf("@");
		String oldsqlString = sql;
		if (index > -1) {
			sql = getSqlReplace(sql.toString());
		}
		int indexall = sqlAll.indexOf("@");
		if (indexall > -1) {
			sqlAll = getSqlReplace(sqlAll.toString());
		}
		List<?> pageList = baseDao.getMyCustomPageBySql(sqlAll, sql, pager, values);
		// sql 转化为json格式
		String json = exportDataList(pageList, oldsqlString, columns, "10000", pager);// 该方法中存在处理数据保留2为小数的方法
		return json;
	}
	
	/**
	 * <p>
	 * 描述：漏失分析json，含平均值
	 * </p>
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String findLeakageAnalysisBySqlEntity(String sqlAll, String sql, String columns, String limit, Page pager, Object... values) throws IOException, SQLException {
		String[] str = splitPageSql(sqlAll);
		int index = sql.indexOf("@");
		String oldsqlString = sql;
		if (index > -1) {
			sql = getSqlReplace(sql.toString());
		}
		int indexall = sqlAll.indexOf("@");
		if (indexall > -1) {
			sqlAll = getSqlReplace(sqlAll.toString());
		}
		List<T> pageList = baseDao.getMyCustomPageBySql(sqlAll, sql, pager, values);
		List<T> aveList = baseDao.getAverageBySql(sqlAll, str, values);
		if (null != aveList && aveList.size() > 0) {
			pageList.add(aveList.get(0));
		}
		// sql 转化为json格式
		String json = jsonPageSql(pageList, oldsqlString, columns, "10000", pager);// 该方法中存在处理数据保留2为小数的方法
		return json;
	}
	
	public String findAllDataBySqlEntity(String sql, String columns, Page pager, Object... values) {
		int index = sql.indexOf("@");
		String oldsqlString = sql;
		if (index > -1) {
			sql = getSqlReplace(sql.toString());
		}
		List<?> pageList = baseDao.getAllPageBySql(sql, pager, values);
		// sql 转化为json格式
		String json = jsonPageSql(pageList, oldsqlString, columns, pager);
		return json;
	}

	/**
	 * 动态Grid列头Server
	 * 
	 * @param sql
	 * @param columns
	 * @param limit
	 * @param pager
	 * @return
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String findPageBySqlEntity(String sql, String columns, String limit, Page pager, Vector<String> v) throws IOException, SQLException {

		List<?> pageList = baseDao.getAllPageBySql(sql.toString(), pager, v);
		// sql 转化为json格式
		String json = jsonPageSql(pageList, sql, columns, limit, pager);

		return json;
	}

	public String findPageBySqlRiverEntity(String sql, Page pager, Object... values) {
		List<?> pageList = baseDao.getAllPageBySql(sql.toString(), pager, values);
		// sql 转化为json格式
		String json = jsonPageRiverSql(pageList, sql, pager);
		return json;

	}

	public BaseDao getBaseDao() {
		return baseDao;
	}

	

//	public <T> List<T> getObjects(String queryString) {
//		return this.baseDao.getObjects(queryString);
//	}

	public <T> List<T> getSQLObjects(String queryString) {
		return this.baseDao.getSQLObjects(queryString);
	}

	public String getSqlReplace(String sql_) {
		String resultString = "";
		int index_ = sql_.indexOf("@");
		if (index_ > -1) {
			resultString = sql_.replace("@", ",");
		}
		return resultString;
	}

	/**
	 * @param orgId
	 * @return 递归取出当前组织Id下的所有Id字符串集合
	 */
	public String getUserOrgIds(int orgId) {
		return this.baseDao.getUserOrgIds(orgId);
	}

	/**
	 * 动态Gridreport列头
	 * 
	 * @param oList
	 * @param sql
	 * @param columns
	 * @param limit
	 * @param pager
	 * @return
	 */
	public String jsonPageRiverSql(List<?> oList, String sql, Page pager) {
		String[] str = splitPageSql(sql);
		String jsonString = "{success:true,jssj:\"" + pager.getJssj() + "\",start_date:\"" + pager.getStart_date() + "\",end_date:\"" + pager.getEnd_date() + "\",jh:\"" + pager.getJh() + "\",totals:" + pager.getTotalCount() + ",start:" + pager.getStart() + ",totalCount:" + pager.getTotalCount();
		if (null != oList && oList.size() > 0) {
			try {
				String strs = "";
				for (int i = 0; i < oList.size(); i++) {
					Object[] obj = (Object[]) oList.get(i);
					strs += "{";
					for (int j = 0; j < str.length; j++) {
						String attrstring = str[j].toString().trim();
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
					// strs = strs.substring(0, strs.length() - 1);
					strs += "\"sxl\":'',\"jzwd\":''";
					strs += "},";
				}
				strs = strs.substring(0, strs.length() - 1);
				jsonString += ",totalRoot:[" + strs + "]}";

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			jsonString += "0,totalRoot:[]}";
		}
		return jsonString;

	}

	/**
	 * oList:获得查询分页的数据List sql: 执行的sql pager:页面参数传递对象，为查询提供过滤、分页、排序的参数 最终转为ext
	 * json
	 * 
	 * @author qiands
	 */
	public String jsonPageSql(List<?> oList, String sql, Page pager) {
		String[] str = splitPageSql(sql);
		String jsonString = "{start:" + pager.getStart() + ",limit:" + pager.getLimit() + ",totalCount:" + pager.getTotalCount();
		if (null != oList && oList.size() > 0) {
			try {
				String strs = "";
				for (int i = 0; i < oList.size(); i++) {
					Object[] obj = (Object[]) oList.get(i);
					strs += "{";
					for (int j = 0; j < str.length; j++) {
						String attrstring = str[j].toString().trim();
						String[] attr = attrstring.split(" as ");
						String value = obj[j] + "";
						if (value == null || value.equalsIgnoreCase("null")) {
							value = "";
						}
						int attr_length = attr.length;
						String key="";
						if (attr_length > 1) {
							key=attr[1].trim()+"";
						} else {
							key = attr[0];
						}
						if (StringManagerUtils.clobDataFiter(key)) {
							value = StringManagerUtils.CLOBObjectToString(obj[j]);
						}
						strs += "\"" + key+ "\":\"" + value + "\",";
					}
					strs = strs.substring(0, strs.length() - 1);
					strs += "},";
				}
				strs = strs.substring(0, strs.length() - 1);
				jsonString += ",totalRoot:[" + strs + "]}";

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			jsonString += "0,totalRoot:[]}";
		}
		return jsonString;

	}

	/**
	 * <P>
	 * 描述：拼接json数据信息，不需要把数据保留2位小数为煤层气报表服务
	 * </p>
	 * 
	 * @param oList
	 * @param sql
	 * @param columns
	 * @param pager
	 * @return
	 */
	public String jsonPageSql(List<?> oList, String sql, String columns, Page pager) {

		String[] str = splitPageSql(sql);
		StringBuffer jsonString = new StringBuffer();
		jsonString.append("{success:true,columns:" + columns + ",jssj:\"" + pager.getJssj() + "\",start_date:\"" + pager.getStart_date() + "\",end_date:\"" + pager.getEnd_date() + "\",jh:\"" + pager.getJh() + "\",totals:" + pager.getTotalCount() + ",start:" + pager.getStart() + ",totalCount:" + pager.getTotalCount());
		if (null != oList && !oList.equals("") && oList.size() > 0) {
			try {

				StringBuffer strs = new StringBuffer();
				for (int i = 0; i < oList.size(); i++) {
					Object[] obj = (Object[]) oList.get(i);
					strs.append("{");
					for (int j = 0; j < str.length; j++) {
						String attrstring = str[j].toString().trim();
						String[] attr = attrstring.split(" as ");// 当查询的sql字段中含有
																	// t.jh as
																	// JhName
																	// 时，把字段截取as
																	// 后的字符串作为key
						int attr_length = attr.length;
						if (attr_length > 1) {
							String key = attr[1].trim();
							String value = obj[j] + "";
							if (value.equals("null") || value == null) {
								value = "";
							}
							if (!key.equalsIgnoreCase("hj")) {
								if (StringManagerUtils.clobDataFiter(key)) {
									value = StringManagerUtils.CLOBObjectToString(obj[j]);
								}else if (!StringManagerUtils.stringDataFiter(key)) {
									value = StringManagerUtils.formatReportPrecisionValue(value);
								}
								strs.append("\"" + key + "\":\"" + value + "\",");
							} else {
								strs.append("\"" + key + "\":\"合计 \",");
							}
						} else {
							String key = attr[0].trim();
							if (key.indexOf(".") > 0) {// 当查询的sql字段中含有
																// t.jh
																// 时，把字段从“.”后截取字符串作为key
								key = key.substring(key.indexOf(".") + 1);
							}
							String value = obj[j] + "";
							if (null == value || value.equals("null") || !StringManagerUtils.isNotNull(value)) {
								value = "";
							}
							if (StringManagerUtils.clobDataFiter(key)) {
								value = StringManagerUtils.CLOBObjectToString(obj[j]);
							}
							strs.append("\"" + key + "\":\"" + value + "\",");
						}
					}
					strs = strs.deleteCharAt(strs.length() - 1);
					strs.append(" },");
				}
				
				strs = strs.deleteCharAt(strs.length() - 1);
				jsonString.append(",totalRoot:[" + strs + "]}");

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			jsonString.append("0,totalRoot:[]}");
		}
		return jsonString.toString();

	}
	
	public String exportExcelJson(List<?> oList, String sql, String columns, Page pager) {

		String[] str = splitPageSql(sql);
		StringBuffer jsonString = new StringBuffer();
		if (null != oList && !oList.equals("") && oList.size() > 0) {
			try {
				StringBuffer strs = new StringBuffer();
				for (int i = 0; i < oList.size(); i++) {
					Object[] obj = (Object[]) oList.get(i);
					strs.append("{");
					for (int j = 0; j < str.length; j++) {
						String attrstring = str[j].toString().trim();
						String[] attr = attrstring.split(" as ");
						int attr_length = attr.length;
						if (attr_length > 1) {
							String key = attr[1].trim();
							String value = obj[j] + "";
							if (value.equals("null") || value == null) {
								value = "";
							}
							if (!key.equalsIgnoreCase("hj")) {
								if (StringManagerUtils.clobDataFiter(key)) {
									value = StringManagerUtils.CLOBObjectToString(obj[j]);
								}else if (!StringManagerUtils.stringDataFiter(key)) {
									value = StringManagerUtils.formatReportPrecisionValue(value);
								}
								strs.append("\"" + key + "\":\"" + value + "\",");
							} else {
								strs.append("\"" + key + "\":\"合计 \",");
							}
						} else {
							String key = attr[0].trim();
							if (key.indexOf(".") > 0) {// 当查询的sql字段中含有
																// t.jh
																// 时，把字段从“.”后截取字符串作为key
								key = key.substring(key.indexOf(".") + 1);
							}
							String value = obj[j] + "";
							if (null == value || value.equals("null") || !StringManagerUtils.isNotNull(value)) {
								value = "";
							}
							if (StringManagerUtils.clobDataFiter(key)) {
								value = StringManagerUtils.CLOBObjectToString(obj[j]);
							}
							strs.append("\"" + key + "\":\"" + value + "\",");
						}
					}
					strs = strs.deleteCharAt(strs.length() - 1);
					strs.append(" },");
				}
				
				strs = strs.deleteCharAt(strs.length() - 1);
				jsonString.append("[" + strs + "]");

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			jsonString.append("[]");
		}
		return jsonString.toString();

	}
	
	public String jsonPageSql2(int recordCount,List<?> oList, String sql, String columns, Page pager) {

		String[] str = splitPageSql(sql);
		StringBuffer jsonString = new StringBuffer();
		jsonString.append("{success:true,columns:" + columns + ",jssj:\"" + pager.getJssj() + "\",start_date:\"" + pager.getStart_date() + "\",end_date:\"" + pager.getEnd_date() + "\",jh:\"" + pager.getJh() + "\",totals:" + pager.getTotalCount() + ",start:" + pager.getStart() + ",totalCount:" + pager.getTotalCount());
		if (null != oList && !oList.equals("")) {
			try {
				StringBuffer strs = new StringBuffer();
				for (int i = 0; i < oList.size(); i++) {
					Object[] obj = (Object[]) oList.get(i);
					strs.append("{");
					for (int j = 0; j < str.length; j++) {
						String attrstring = str[j].toString().trim();
						String[] attr = attrstring.split(" as ");// 当查询的sql字段中含有t.jh as JhName 时，把字段截取as后的字符串作为key
						int attr_length = attr.length;
						if (attr_length > 1) {
							String key = attr[1].trim();
							String value = obj[j] + "";
							if (value.equals("null") || value == null) {
								value = "";
							}
							if (!key.equalsIgnoreCase("hj")) {
								if (StringManagerUtils.clobDataFiter(key)) {
									value = StringManagerUtils.CLOBObjectToString(obj[j]);
								}else if (!StringManagerUtils.stringDataFiter(key)) {
									value = StringManagerUtils.formatReportPrecisionValue(value);
								}
								strs.append("\"" + key + "\":\"" + value + "\",");
							} else {
								strs.append("\"" + key + "\":\"合计 \",");
							}
						} else {
							String key = attr[0].trim();
							if (key.indexOf(".") > 0) {// 当查询的sql字段中含有t.jh时，把字段从“.”后截取字符串作为key
								key = key.substring(key.indexOf(".") + 1);
							}
							String value = obj[j] + "";
							if (null == value || value.equals("null") || !StringManagerUtils.isNotNull(value)) {
								value = "";
							}
							if (StringManagerUtils.clobDataFiter(key)) {
								value = StringManagerUtils.CLOBObjectToString(obj[j]);
							}
							strs.append("\"" + key + "\":\"" + value + "\",");
						}
					}
					strs = strs.deleteCharAt(strs.length() - 1);
					strs.append(" },");
				}
				for(int i=1;i<=recordCount-pager.getTotalCount();i++){
					strs.append("{\"jlbh\":\"-99999\",\"id\":\"-99999\"},");
				}
				strs = strs.deleteCharAt(strs.length() - 1);
				jsonString.append(",totalRoot:[" + strs + "]}");

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			jsonString.append("0,totalRoot:[]}");
		}
		return jsonString.toString();

	}
	
	
	
	

	/**
	 * 动态构建json数据信息
	 * 
	 * @param oList
	 *            传入的数据集合
	 * @author gao 2014-05-08
	 * @param sql
	 *            传入查询的sql语句
	 * @param columns
	 *            Ext js columns 信息
	 * @param limit
	 *            分页大小
	 * @param pager
	 *            分页工具类
	 * @return jsonString
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String jsonPageSql( List<?> oList, String sql, String columns, String limit, Page pager) throws IOException, SQLException {
		Map<String, Object> dataModelMap = DataModelMap.getMapObject();
		AlarmShowStyle alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		if(alarmShowStyle==null){
			EquipmentDriverServerTask.initAlarmStyle();
			alarmShowStyle=(AlarmShowStyle) dataModelMap.get("AlarmShowStyle");
		}
		String[] str = splitPageSql(sql);
		// long f=System.currentTimeMillis();
		StringBuffer jsonString = new StringBuffer();
		jsonString.append("{success:true,columns:" + columns + ",jssj:\"" + pager.getJssj() + "\",start_date:\"" + pager.getStart_date() + "\",end_date:\"" + pager.getEnd_date() + "\",jh:\"" + pager.getJh() + "\",totals:" + pager.getTotalCount() + ",start:" + pager.getStart() + ",totalCount:" + pager.getTotalCount());
		if (null != oList && oList.size() > 0) {
			try {

				StringBuffer strs = new StringBuffer();
				for (int i = 0; i < oList.size(); i++) {
					Object[] obj = (Object[]) oList.get(i);
					strs.append("{");
					for (int j = 0; j < str.length; j++) {
						String attrstring = str[j].toString().trim();
						String[] attr = attrstring.split(" as ");// 当查询的sql字段中含有t.jh asJhName时，把字段截取as后的字符串作为key
						int attr_length = attr.length;
						if (attr_length > 1) {
							String key = attr[1].trim();
							String value = obj[j]+"";
							if (!key.equalsIgnoreCase("hj")) {
								if (value.equals("null")) {
									value = "";
								} else if (sql.contains("v_012_") && key.contains("lsxs")) {
									float value2 = StringManagerUtils.stringToFloat(value,4); // 将漏失分析中的漏失系数保留4位小数
									value = value2 + "";
								}
								else if (StringManagerUtils.clobDataFiter(key)) {
									value = StringManagerUtils.CLOBObjectToString(obj[j]);
								} 
								else if (!StringManagerUtils.stringDataFiter(key)) {
									value = StringManagerUtils.formatReportPrecisionValue(value);
								}
								strs.append("\"" + key + "\":\"" + value + "\",");
							} else {
								strs.append("\"" + key + "\":\"合计 \",");
							}
						} else {
							String key = attr[0].trim();
							if (key.indexOf(".") > 0) {// 当查询的sql字段中含有t.jh时，把字段从“.”后截取字符串作为key
								key = key.substring(key.indexOf(".") + 1);
							}
							String value = obj[j] + "";
							if (null == value || value.equals("null") || !StringManagerUtils.isNotNull(value)) {
								value = "";
							} else if (key.equalsIgnoreCase("dl")) {
								value = StringManagerUtils.formatReportPrecisionValue(obj[j + 1] + "") + "/" + StringManagerUtils.formatReportPrecisionValue(obj[j + 2] + "");
							} else if ("gtsj".equalsIgnoreCase(key)) {
								byte[] bytes; 
								String gtsj="";
								BufferedInputStream bis = null;
						        StringBuffer gtsjStr = new StringBuffer();
								if(obj[j]!=null && (!obj[j].toString().equals("null"))&&obj[j].toString().indexOf("oracle.sql.BLOB")>-1){
									SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[j]);
									CLOB realClob = (CLOB) proxy.getWrappedClob(); 
									gtsj=StringManagerUtils.CLOBtoString(realClob);
							        String arrgtsj[]=gtsj.replaceAll("\r\n", "\n").split("\n");
							        gtsjStr.append(arrgtsj[2] + ","); // 功图点数
							        for(int k=5;k<arrgtsj.length;k++){
							        	gtsjStr.append(arrgtsj[k] + ",");
							        }
							        value = gtsjStr.deleteCharAt(gtsjStr.length() - 1) + ""; // 功图点数，位移1，载荷1，位移2，载荷2.。。
								}else{
									value = StringManagerUtils.formatReportPrecisionValue(value);
								}
							}
							else if (StringManagerUtils.clobDataFiter(key)) {
								value = StringManagerUtils.CLOBObjectToString(obj[j]);
							}
							else if (sql.contains("v_012_") && key.contains("lsxs")) {
								float value2 = StringManagerUtils.stringToFloat(value,4); // 将漏失分析中的漏失系数保留4位小数
								value = value2 + "";
							}else if (!StringManagerUtils.stringDataFiter(key)) {
								value = StringManagerUtils.formatReportPrecisionValue(value);
							}
							strs.append("\"" + key + "\":\"" + value + "\",");
						}
					}
					strs = strs.deleteCharAt(strs.length() - 1);
					strs.append(" },");
				}
				if(strs.toString().endsWith(",")){
					strs = strs.deleteCharAt(strs.length() - 1);
				}
				jsonString.append(",totalRoot:[" + strs + "]}");

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			jsonString.append("0,totalRoot:[]}");
		}
		String getResult =jsonString.toString();
		getResult=getResult.substring(0,getResult.length()-1);
		getResult+=",\"AlarmShowStyle\":"+new Gson().toJson(alarmShowStyle)+"}";
		return getResult;
	}
	
	public String exportDataList( List<?> oList, String sql, String columns, String limit, Page pager) {
		String[] str = splitPageSql(sql);
		StringBuffer jsonString = new StringBuffer();
		if (null != oList && oList.size() > 0) {
			try {
				StringBuffer strs = new StringBuffer();
				for (int i = 0; i < oList.size(); i++) {
					Object[] obj = (Object[]) oList.get(i);
					strs.append("{");
					for (int j = 0; j < str.length; j++) {
						String attrstring = str[j].toString().trim();
						String[] attr = attrstring.split(" as ");// 当查询的sql字段中含有t.jh asJhName时，把字段截取as后的字符串作为key
						int attr_length = attr.length;
						if (attr_length > 1) {
							String key = attr[1].trim();
							String value = obj[j]+"";
							if (!key.equalsIgnoreCase("hj")) {
								if (value.equals("null")) {
									value = "";
								} else if (sql.contains("v_012_") && key.contains("lsxs")) {
									float value2 = StringManagerUtils.stringToFloat(value,4); // 将漏失分析中的漏失系数保留4位小数
									value = value2 + "";
								}
								else if (StringManagerUtils.clobDataFiter(key)) {
									value = StringManagerUtils.CLOBObjectToString(obj[j]);
								} 
								else if (!StringManagerUtils.stringDataFiter(key)) {
									value = StringManagerUtils.formatReportPrecisionValue(value);
								}
								strs.append("\"" + key + "\":\"" + value + "\",");
							} else {
								strs.append("\"" + key + "\":\"合计 \",");
							}
						} else {
							String key = attr[0].trim();
							if (key.indexOf(".") > 0) {// 当查询的sql字段中含有t.jh时，把字段从“.”后截取字符串作为key
								key = key.substring(key.indexOf(".") + 1);
							}
							String value = obj[j] + "";
							if (null == value || value.equals("null") || !StringManagerUtils.isNotNull(value)) {
								value = "";
							} else if (key.equalsIgnoreCase("dl")) {
								value = StringManagerUtils.formatReportPrecisionValue(obj[j + 1] + "") + "/" + StringManagerUtils.formatReportPrecisionValue(obj[j + 2] + "");
							} else if ("gtsj".equalsIgnoreCase(key)) {
								byte[] bytes; 
								String gtsj="";
								BufferedInputStream bis = null;
						        StringBuffer gtsjStr = new StringBuffer();
								if(obj[j]!=null && (!obj[j].toString().equals("null"))&&obj[j].toString().indexOf("oracle.sql.BLOB")>-1){
									SerializableClobProxy   proxy = (SerializableClobProxy)Proxy.getInvocationHandler(obj[j]);
									CLOB realClob = (CLOB) proxy.getWrappedClob(); 
									gtsj=StringManagerUtils.CLOBtoString(realClob);
							        String arrgtsj[]=gtsj.replaceAll("\r\n", "\n").split("\n");
							        gtsjStr.append(arrgtsj[2] + ","); // 功图点数
							        for(int k=5;k<arrgtsj.length;k++){
							        	gtsjStr.append(arrgtsj[k] + ",");
							        }
							        value = gtsjStr.deleteCharAt(gtsjStr.length() - 1) + ""; // 功图点数，位移1，载荷1，位移2，载荷2.。。
								}else{
									value = StringManagerUtils.formatReportPrecisionValue(value);
								}
							}
							else if (StringManagerUtils.clobDataFiter(key)) {
								value = StringManagerUtils.CLOBObjectToString(obj[j]);
							}
							else if (sql.contains("v_012_") && key.contains("lsxs")) {
								float value2 = StringManagerUtils.stringToFloat(value,4); // 将漏失分析中的漏失系数保留4位小数
								value = value2 + "";
							}else if (!StringManagerUtils.stringDataFiter(key)) {
								value = StringManagerUtils.formatReportPrecisionValue(value);
							}
							strs.append("\"" + key + "\":\"" + value + "\",");
						}
					}
					strs = strs.deleteCharAt(strs.length() - 1);
					strs.append(" },");
				}
				strs = strs.deleteCharAt(strs.length() - 1);
				jsonString.append("[" + strs + "]");

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			jsonString.append("[]");
		}
		return jsonString.toString();

	}

	public String replace(String data) {
		StringBuffer aa = new StringBuffer();
		String sarray[] = data.split(",");
		for (int a = 0; a < sarray.length; a++) {
			aa.append(sarray[a]);
			aa.append(",");
		}
		aa.deleteCharAt(aa.length() - 1);
		return aa.toString();
	}

	/**
	 * 保存对象
	 * 
	 * @param object
	 */
	public void save(Object object) throws Exception {
		baseDao.save(object);
	}

	@Resource
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * sql-切割获得有效的字段
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String[] splitPageSql(String sql) {
		String[] tsl = null;
		if (StringManagerUtils.isNotNull(sql)) {
			String newsql = sql.trim();
			if(newsql.indexOf("exists")>=0){
				newsql=newsql.split("exists")[0];
			}
			String[] sqlplit = newsql.split("from");
			// 获得第一个select -from 之间信息
			String getPt="";
			String getsql="";
			if(sqlplit.length==4){
				getPt = sqlplit[sqlplit.length-2].toString().trim();
				getsql = getPt.substring(7, getPt.length()).trim();
			}else if(sqlplit.length==5){
				getPt = sqlplit[0].toString().trim();
				getsql = getPt.substring(6, getPt.length()).trim();
			}else{
				getPt = sqlplit[0].toString().trim();
				getsql = getPt.substring(6, getPt.length()).trim();
			}
			
		    
			// 处理SQL特别字符串
			String res = this.indexOfString(getsql);
			// 再次切割
			tsl = res.split(",");
		}
		return tsl;
	}

	/**
	 * 执行一个SQL，update或insert
	 * 
	 * @param sql
	 * @return update或insert的记录数
	 */
	public void updateSql(String sql) throws Exception {
		baseDao.executeSqlUpdate(sql);
	}

	/**
	 * <p>
	 * 描述：通过递归sql查询获取当前用户所属组织下的orgid
	 * </p>
	 * 
	 * @param user
	 * @return
	 */
	public String getCurrentUserOrgIds(int orgId) {
		StringBuffer orgIdSbuf = new StringBuffer();
		String sql = "select org_id,org_name  from tbl_org t  start with org_id = " + orgId + "  connect by prior org_id =org_parent";
		List<?> orgList = this.findCallSql(sql);
		for (Object o : orgList) {
			Object[] obj = (Object[]) o;
			orgIdSbuf.append(obj[0].toString() + ",");
		}
		if (orgIdSbuf.length() > 1) {
			orgIdSbuf.deleteCharAt(orgIdSbuf.length() - 1);
		}
		return orgIdSbuf.toString();

	}

	/**
	 * <p>
	 * 描述：批量删除对象数据信息
	 * </p>
	 * 
	 * @param hql
	 * @throws Exception
	 */
	public void bulkObjectDelete(final String hql) throws Exception {
		this.baseDao.bulkObjectDelete(hql);
	}
	
	public void OnGetDate(List<CallbackDataItems> list,String RTUName) throws HibernateException, SQLException{
			baseDao.OnGetDate(list,RTUName);
	}
	
	public Integer getTotalCountRows(String sql, final Object... values) {
		return baseDao.getTotalCountRows(sql, values);
	}
	
	public Integer getMaxRecordNumber(String sql){
		int result=0;
		//String sql="select max(jlbh) from t_wellorder t ";
		List<?> list = this.findCallSql(sql);
		if(list.size()>0&&list.get(0)!=null){
			result=Integer.parseInt(list.get(0).toString());
		}
		return result;
	}
	
	/*
	 * 获取油井某一天的运行时间
	 * 
	 * */
	public String getWellRuningTime(String cjsjobj,Object rgzsjobj,Object rgzsjdobj) throws ParseException{
		int rgzsj=0;
		String mimu="00";
		if(rgzsjobj==null){
			rgzsjobj="0";
		}
		if(rgzsjobj.toString().indexOf(".")>0){
			rgzsj=Integer.parseInt(rgzsjobj.toString().split("\\.")[0]);
			
			mimu=(int)(Float.parseFloat("0."+rgzsjobj.toString().split("\\.")[1])*60+0.5)+"";
		}else{
			rgzsj=Integer.parseInt(rgzsjobj.toString());
		}
	
		String rgzsjd="";
		
		if(rgzsjdobj==null){
			if(rgzsj==24){
				rgzsjd="00:00-00:00";
			}else if(rgzsj==0){
				rgzsjd="";
			}else{
				rgzsjd="00:00-"+rgzsj+":"+mimu;
			}
		}else{
			String tempstr=rgzsjdobj.toString();
			String arr[]=tempstr.split("\\|");
			for(int i=0;i<arr.length;i++){
				String arr2[]=arr[i].split(";");
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				//double timedev=(double) (d.getTime()-df.parse(arr2[0]).getTime());
				double timedev=(double) (df.parse(cjsjobj.toString()).getTime()-df.parse(arr2[0]).getTime());
				if(((int)(timedev/(24 * 60 * 60 * 1000)))%Integer.parseInt(arr2[1])==0){
					rgzsjd=arr2[2];
					break;
				}
			}
		}
		return rgzsjd;
	}
}
