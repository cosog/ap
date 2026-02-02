package com.cosog.dao;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.internal.OracleClob;
import oracle.sql.BLOB;
import oracle.sql.CLOB;
import redis.clients.jedis.Jedis;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cosog.model.AlarmShowStyle;
import com.cosog.model.DisplayUnit;
import com.cosog.model.KeyParameter;
import com.cosog.model.Org;
import com.cosog.model.User;
import com.cosog.model.WorkType;
import com.cosog.model.calculate.AppRunStatusProbeResonanceData;
import com.cosog.model.calculate.CommResponseData;
import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.PCPCalculateResponseData;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.SRPCalculateRequestData;
import com.cosog.model.calculate.SRPCalculateResponseData;
import com.cosog.model.calculate.SRPDeviceInfo;
import com.cosog.model.calculate.TimeEffResponseData;
import com.cosog.model.calculate.TimeEffTotalResponseData;
import com.cosog.model.calculate.TotalAnalysisRequestData;
import com.cosog.model.calculate.TotalAnalysisResponseData;
import com.cosog.model.calculate.WellAcquisitionData;
import com.cosog.model.calculate.SRPCalculateRequestData.EveryBalance;
import com.cosog.model.drive.AcquisitionGroupResolutionData;
import com.cosog.model.drive.AcquisitionItemInfo;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.gridmodel.PumpingModelHandsontableChangedData;
import com.cosog.model.gridmodel.AuxiliaryDeviceHandsontableChangedData;
import com.cosog.model.gridmodel.CalculateManagerHandsontableChangedData;
import com.cosog.model.gridmodel.ElecInverCalculateManagerHandsontableChangedData;
import com.cosog.model.gridmodel.InverOptimizeHandsontableChangedData;
import com.cosog.model.gridmodel.ProductionOutGridPanelData;
import com.cosog.model.gridmodel.ResProHandsontableChangedData;
import com.cosog.model.gridmodel.ReservoirPropertyGridPanelData;
import com.cosog.model.gridmodel.WellHandsontableChangedData;
import com.cosog.model.gridmodel.WellProHandsontableChangedData;
import com.cosog.model.gridmodel.WellringGridPanelData;
import com.cosog.service.back.WellInformationManagerService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.thread.calculate.DataSynchronizationThread;
import com.cosog.thread.calculate.ThreadPool;
import com.cosog.utils.CalculateUtils;
import com.cosog.utils.Config;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.LicenseMap;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.Page;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.cosog.utils.LicenseMap.License;
/**
 * <p>
 * 描述：核心服务dao处理接口类
 * </p>
 * 
 * @author gao 2014-06-04
 * @since 2013-08-08
 * @version 1.0
 * 
 */
@SuppressWarnings({ "unused", "unchecked", "rawtypes", "deprecation" })
@Repository("baseDao")
public class BaseDao extends HibernateDaoSupport {
	private static Log log = LogFactory.getLog(BaseDao.class);
	private Session session = null;
	public static String ConvertBLOBtoString(Blob BlobContent) {
		byte[] msgContent = null;
		try {
			msgContent = BlobContent.getBytes(1, (int) BlobContent.length());
		} catch (SQLException e1) {
			e1.printStackTrace();
		} // BLOB转换为字节数组
		String newStr = ""; // 返回字符串
		long BlobLength; // BLOB字段长度
		try {
			BlobLength = BlobContent.length(); // 获取BLOB长度
			if (msgContent == null || BlobLength == 0) // 如果为空，返回空值
			{
				return "";
			} else // 处理BLOB为字符串
			{
				newStr = new String(BlobContent.getBytes(1, 900), "gb2312"); // 简化处理，只取前900字节
				return newStr;
			}
		} catch (Exception e) // oracle异常捕获
		{
			e.printStackTrace();
		}
		return newStr;
	}

	/**
	 * @param dataSheet
	 * @param col
	 * @param row
	 * @param width
	 * @param height
	 * @param imgFile
	 */
	public static void insertImg(WritableSheet dataSheet, int col, int row, int width, int height, File imgFile) {
		WritableImage img = new WritableImage(col, row, width, height, imgFile);
		dataSheet.addImage(img);
	}

	@Transactional
	public <T> Serializable addObject(T clazz) {
		return this.save(clazz);
	}
	
	@Transactional
	public <T> Serializable addObjectFlush(T clazz) {
		return this.saveFlush(clazz);
	}
	
	@Transactional
	public Integer saveEntity(Org entity) {
        // 调用 HibernateTemplate 的 save() 方法
        return (Integer) getHibernateTemplate().save(entity);
    }

	/**
	 * <p>
	 * 描述：批量删除对象信息
	 * </p>
	 * 
	 * @param hql
	 * @throws Exception
	 * 
	 * @author gao 2014-06-06
	 * 
	 */
	@Transactional
	public void bulkObjectDelete(final String hql) throws Exception {
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}

	/**
	 * <p>
	 * 描述：根据传入的对象类型，删除该对象的一条记录
	 * </p>
	 * 
	 * @param obj
	 *            传入的对象
	 * @return
	 */
	public Serializable delectObject(Object obj) {
		Session session = getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(obj);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 存储过程调用删除 传入数组String[] ->删除(带占位符的)
	 * 
	 * @param callSql
	 * @param values
	 * @return
	 */
	public void deleteCallParameter(final String callSql, final Object... values) {
		Query query = getSessionFactory().getCurrentSession().createQuery(callSql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		query.executeUpdate();
	}

	public Object deleteCallSql(final String sql, final Object... values) {
		Session session = getSessionFactory().getCurrentSession();
				SQLQuery query = session.createSQLQuery(sql);
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
				return query.executeUpdate();
	}

	public int deleteObject(final String hql) {
		Session session = getSessionFactory().getCurrentSession();
				SQLQuery query = session.createSQLQuery(hql);
				return query.executeUpdate();
	}

	public <T> void deleteObject(T clazz) {
		this.getHibernateTemplate().delete(clazz);
	}

	/**
	 * 传入数组String[] ->删除(带占位符的) (批量)
	 * 
	 * @param queryString
	 * @param parametName
	 * @param parametValue
	 * @return
	 */
	public Query deleteQueryParameter(String queryString, String parametName, Object[] parametValue, Object... values) {
		Query query = getSessionFactory().getCurrentSession().createQuery(queryString);
		query.setParameterList(parametName, parametValue);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	/**
	 * 修改一个对象
	 * 
	 * @param object
	 */
	@Transactional
	public void edit(Object object) {
		getSessionFactory().getCurrentSession().update(object);
	}

	/**
	 * 执行一个SQL，update或insert
	 * 
	 * @param sql
	 * @return update或insert的记录数
	 */
	public int executeSqlUpdate(String sql) {
		int n = 0;
		Statement stat = null;
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			ps=conn.prepareStatement(sql);
			n=ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stat != null) {
					stat.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return n;
	}
	
	/**
	 * 执行一个SQL，update或insert
	 * 
	 * @param sql
	 * @return update或insert的记录数
	 */
	public int executeSqlUpdateClob(String sql,List<String> values) {
		int n = 0;
		Connection conn=null;
		PreparedStatement ps=null;
		java.io.StringReader reader=null;
		try {
			conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			ps=conn.prepareStatement(sql);
			for(int i=0;i<values.size();i++){
				reader=new java.io.StringReader(values.get(i));
				ps.setCharacterStream(i+1, reader, values.get(i).length());
			}
			n=ps.executeUpdate();
			java.io.StringReader aa=new java.io.StringReader("");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(reader!=null){
					reader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return n;
	}

	/**
	 * HQL查询
	 * 
	 * @param queryString
	 *            HQL语句
	 * @param values
	 *            HQL参数
	 * @return
	 */
	public List find(String queryString, Object... values) {
		Query query = getSessionFactory().getCurrentSession().createQuery(queryString);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query.list();
	}
	
	/**
	 * <p>
	 * 描述：根据传入的hql语句返回一个List数据集合
	 * </p>
	 * 
	 * @author gao 2014-06-04
	 * @param hql
	 *            传入的hql语句
	 * @return List<T>
	 */
//	public <T> List<T> getObjects(String hql) {
//		Session session=getSessionFactory().getCurrentSession();
//		return (List<T>) this.getHibernateTemplate().find(hql);
//	}

	/**
	 * sql调用查询
	 * 
	 * @param queryString
	 *            callSql语句
	 * @param values
	 *            callSql参数
	 * @author qiands
	 * @return List<?>
	 */
	public List<?> findCallSql(final String callSql, final Object... values) {
		try{
			Session session=getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery(callSql);
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
//			StringManagerUtils.printLog(callSql);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			StringManagerUtils.printLog("sql执行失败-"+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":"+callSql,2);
			return new ArrayList<>();
		}
		
	}

	public List<Org> findChildOrg(Integer parentId) {
		String queryString = "SELECT u FROM Org u where u.orgParent=" + parentId + " order by u.orgId ";
		return find(queryString);
	}

	/**
	 * 根据ID获取一个对象，如果查不到返回null
	 * 
	 * @param entityClass
	 * @param id
	 *            :查询对象的id
	 * @return <T>
	 */
	public <T> T get(Class<T> entityClass, Serializable id) {
		return (T) getSessionFactory().getCurrentSession().get(entityClass, id);
	}

	public <T> List<T> getAllObjects(Class<T> clazz) {
		return this.getHibernateTemplate().loadAll(clazz);
	}

	/**
	 * HQL Hibernate分页
	 * 
	 * @param hql
	 *            HSQL 查询语句
	 * @param page
	 *            分页条件信息
	 * @return List<T> 查询结果集
	 */
	public <T> List<T> getAllPageByHql(final String hql, final Page page) {
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		ScrollableResults scrollableResults = query.scroll(ScrollMode.SCROLL_SENSITIVE);
		scrollableResults.last();
		query.setFirstResult(page.getStart());
		query.setMaxResults(page.getLimit());
		page.setTotalCount(scrollableResults.getRowNumber() + 1);
		return query.list();
	}

	/**
	 * 返回当前页的数据信息,执行的是sql查询操作
	 * 
	 * @author gao 2014-05-08
	 * @param sql
	 *            查询的sql语句
	 * @param pager
	 *            分页信息
	 * @param values动态参数
	 * @return list 数据结果集合
	 */
	public <T> List<T> getAllPageBySql(final String sql, final Page pager, final Object... values) {
		Session session=getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		/****
		* 为query对象参数赋值操作
		 * */
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		query.setFirstResult(pager.getStart());// 设置起始位置
		query.setMaxResults(pager.getLimit());// 设置分页条数
		int totals = getTotalCountRows(sql, values);//设置数据表中的总记录数
		pager.setTotalCount(totals);
		return query.list();
	}
	public <T> List<T> getMyCustomPageBySql(final String sqlAll,final String sql, final Page pager, final Object... values) {
		Session session=getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		for (int i = 0; i < values.length; i++) {
			values[i] = values[i].toString().replace("@", ",");
			query.setParameter(i, values[i]);
		}
		int totals = getTotalCountRows(sqlAll, values);//设置数据表中的总记录数
		pager.setTotalCount(totals);
		return query.list();
	}

	/***
	 * *************************************begin
	 * 
	 * @author qiands
	 */
	/**
	 * 分页方法
	 * 
	 * @param sql
	 * @param pager
	 * @return
	 * @author qiands
	 */
	public <T> List<T> getAllPageBySql(final String sql, final Page pager, final Vector<String> v) {
		Session session=getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		int index = -1;
		String data = "";
		for (int i = 0; i < v.size(); i++) {
			if (!"".equals(v.get(i)) && null != v.get(i) && v.get(i).length() > 0) {
				index += 1;
				data = v.get(i);
				query.setParameter(index, data);
			}
		}
		ScrollableResults scrollableResults = query.scroll(ScrollMode.SCROLL_SENSITIVE);
		scrollableResults.last();
		query.setFirstResult(pager.getStart());
		query.setMaxResults(pager.getLimit());
		pager.setTotalCount(scrollableResults.getRowNumber() + 1);
		return query.list();
	}

	public void getChildrenList(List parentitem, Integer orgid) {
		List childlist = null;
		try {
			childlist = findChildOrg(orgid.intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (childlist != null && childlist.size() > 0) {
			for (int i = 0; i < childlist.size(); i++) {
				Org bean = (Org) childlist.get(i);
				parentitem.add(bean.getOrgId());
				getChildrenList(parentitem, bean.getOrgId());
			}
		}
	}

	/**
	 * 
	 * @param sql
	 * @return
	 */
	public Integer getCountRows(String sql) {
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		Integer rows = (Integer) query.list().size();
		return rows;
	}

	/**
	 * <p>
	 * 描述：查询数据库中的记录总数
	 * </p>
	 * 
	 * @param sql
	 * @return
	 */
	public Integer getCountSQLRows(String sql) {
		Session session=getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		BigDecimal obj = (BigDecimal) query.uniqueResult();
		return obj.intValue();
	}
	
	public <T> List<T> getListAndTotalCountForPage(final Page pager, final String hql, final String allhql) throws Exception {
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		ScrollableResults scrollableResults = query.scroll(ScrollMode.SCROLL_SENSITIVE);
		scrollableResults.last();
		int totals = scrollableResults.getRowNumber()+1;
		pager.setTotalCount(totals);
		query.setFirstResult(pager.getStart());
		query.setMaxResults(pager.getLimit());
		List<T> list = query.list();
		return list;
	}

	public <T> List<T> getListAndTotalForPage(final Page pager, final String hql) throws Exception {
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		int total = query.list().size();
		pager.setTotalCount(total);
		query.setFirstResult(pager.getStart());
		query.setMaxResults(pager.getLimit());
		List<T> list = query.list();
		return list;
	}

	/**
	 * <p>
	 * 描述：获取记录数据库中的总记录数
	 * </p>
	 * 
	 * @param o
	 * @return
	 */
	public Long getListCountRows(final String o) {
		final String hql = "select count(*) from  " + o;
		Long result = null;
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		return (Long) query.uniqueResult();
	}

	protected <T> List<T> getListForPage(final Class<T> clazz, final Criterion[] criterions, final int offset, final int length) {
		Session session=getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(clazz);
		for (int i = 0; i < criterions.length; i++) {
			criteria.add(criterions[i]);
		}
		criteria.setFirstResult(offset);
		criteria.setMaxResults(length);
		return criteria.list();
	}
	
	public <T> List<T> getListForPage(final int offset, final int pageSize,final String hql) throws Exception {
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		List<T> list = query.list();
		return list;

	}

	public <T> List<T> getListForPage(final int offset, final int pageSize, final String hql, final List<KeyParameter> params) throws Exception {
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		for (int i = 0; i < params.size(); i++) {
			KeyParameter p = params.get(i);
			if (p.getParamType().equalsIgnoreCase("String")) {
				query.setString(i, "%" + p.getStrParamValue() + "%");
			} else if (p.getParamType().equalsIgnoreCase("Integer")) {
				query.setInteger(i, p.getIntParamValue());
			} else if (p.getParamType().equalsIgnoreCase("Date")) {
				query.setDate(i, p.getDateParamValue());
			} else if (p.getParamType().equalsIgnoreCase("Timestamp")) {
				query.setTimestamp(i, p.getTimeParamValue());
			}
		}
		query.setFirstResult(offset - 1);
		query.setMaxResults(pageSize);
		List<T> list = query.list();
		return list;
	}

	/**
	 * @param offset
	 * @param pageSize
	 * @param pager
	 * @param hql
	 * @param o
	 *            出入当前的 实体类对象
	 * @return 返回分页后的数据集合
	 * @throws Exception
	 */
	public <T> List<T> getListForPage(final Page pager, final String hql, final String o) throws Exception {
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		pager.setTotalCount(Integer.parseInt(getMaxCountValue(o) + ""));
		query.setFirstResult(pager.getStart());
		query.setMaxResults(pager.getLimit());
		List<T> list = query.list();
		return list;
	}

	public <T> List<T> getListForReportPage(final int offset, final int pageSize, final String hql) throws Exception {
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(hql);
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		List<T> list = query.list();
		return list;
	}

	/**
	 * <p>
	 * 描述：hql查询分页方法
	 * </p>
	 * 
	 * @param offset
	 *            数据偏移量
	 * @param pageSize
	 *            分页大小
	 * @param hql
	 *            查询语句
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> getListPage(final int offset, final int pageSize, final String hql) throws Exception {
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(hql);
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		List<T> list = query.list();
		return list;
	}

	public Long getMaxCountValue(final String o) {
		Session session=getSessionFactory().getCurrentSession();
		final String hql = "select count(*) from " + o;
		Query query = session.createQuery(hql);
		return (Long) query.uniqueResult();
	}

	public <T> T getObject(Class<T> clazz, Serializable id) {
		return this.getHibernateTemplate().get(clazz, id);
	}

	
	
	public <T> List<T> getSqlToHqlOrgObjects(String sql) {
		Session session=getSessionFactory().getCurrentSession();
		return (List<T>) session.createSQLQuery(sql).addEntity("Org", Org.class).list();
	}

	
	public <T> List<T> getSQLObjects(final String sql) {
		Session session=getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		List list = query.list();
		return (List<T>) list;
	}

	public Integer getRecordCountRows(String hql) {
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		Integer rows = (Integer) query.list().size();
		return rows;
	}

	/**
	 * <p>根据传入的SQL语句来分页查询List集合</p>
	 * 
	 * @param offset
	 * @param pageSize
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> getSQLListForPage(final int offset, final int pageSize, final String hql) throws Exception {
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(hql);
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		List<T> list = query.list();
		return list;
	}

	/**
	 * <p>
	 * 描述：根据普通的sql来查询一个结果List集合
	 * </p>
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> getSQLList(final String sql) throws Exception {
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createSQLQuery(sql);
		List<T> list = query.list();
		return list;
	}

	public Integer getTotalCountRows(String sql, final Object... values) {
		String allsql="";
		if(sql.trim().indexOf("count(1)")>0||sql.trim().indexOf("count(*)")>0){
			allsql=sql;
		}else{
			if(sql.indexOf("distinct")>0||sql.indexOf("group")>0){
				allsql = "select count(1)  from  (" + sql + ")";
			}else{
				String strarr[]=sql.split("from");
				if(strarr.length>1){
					allsql="select count(1) ";
					for(int i=1;i<strarr.length;i++){
						allsql+="from "+strarr[i];
					}
				}else{
					allsql = "select count(1)  from  (" + sql + ")";
				}
			}
		}
		
		if(allsql.split("order").length==2 && allsql.lastIndexOf("order")>allsql.lastIndexOf(")")){
			allsql=allsql.split("order")[0];
		}
		
		
		Integer rows =0;
		SQLQuery query = getSessionFactory().getCurrentSession().createSQLQuery(allsql);
//		StringManagerUtils.printLog(allsql);
		for (int i = 0; i < values.length; i++) {
			values[i] = values[i].toString().replace("@", ",");
			query.setParameter(i, values[i]);
		}
		try{
			rows= Integer.parseInt(query.uniqueResult() + "");
		} catch (Exception e) {
			e.printStackTrace();
			StringManagerUtils.printLog("sql执行失败-"+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":"+allsql,2);
		}
		
		return rows;
	}

	public Long getTotalCountValue(final String o) {
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createQuery(o);
		return (Long) query.uniqueResult();
	}

	public Integer getTotalSqlCountRows(String sql, final Object... values) {
		String allsql = "select count(*)  from  (" + sql + ")";
		Query query = getSessionFactory().getCurrentSession().createSQLQuery(allsql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		List<BigDecimal> list = query.list();
		int count = list.get(0).intValue();
		return count;
	}

	/**
	 * @param orgId
	 * @return 递归取出当前组织Id下的所有Id字符串集合
	 */
	public String getUserOrgIds(int orgId) {
		List childOrgList = new ArrayList();
		String orgIds = orgId + ",";
		getChildrenList(childOrgList, orgId);
		for (int i = 0; i < childOrgList.size(); i++) {
			orgIds = orgIds + childOrgList.get(i) + ",";
		}
		orgIds = orgIds.substring(0, orgIds.length() - 1);
		return orgIds;
	}

	public <T> List<T> getWellListForPage(final int offset, final int pageSize, final String hql, final int orgId) throws Exception {
		Session session=getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql);
		query.setInteger("orgId", orgId);
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		List<T> list = query.list();
		return list;
	}

	public Serializable insertObject(Object obj) {
		Session session=getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(obj);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		return null;
	}

	public Serializable modifyByObject(String hql) {
		Session session=getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		return null;
	}

	public Serializable modifyObject(Object obj) {
		Session session=getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(obj);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		return null;
	}

	public List MonthJssj(final String sql) {
		Session session=getSessionFactory().getCurrentSession();
				Query query = session.createSQLQuery(sql);
				List list = query.list();
				return list;
	}

	public Integer queryProObjectTotals(String sql) throws SQLException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		int total = 0;
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		try {
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs!=null){
					rs.close();
				}
				if(ps!=null){
					ps.close();
				}
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return total;
	}

	/**
	 * 新增一个对象
	 * 
	 * @param object
	 */
	@Transactional
	public Serializable save(Object object) {
		return getSessionFactory().getCurrentSession().save(object);
	}
	
	public Serializable saveFlush(Object object) {
//		Session session=getSessionFactory().getCurrentSession();
		Session session = getSessionFactory().openSession(); 
		Transaction transaction=null;
		Serializable serializable=null;
		try {
			transaction = session.beginTransaction(); // 开启新事务
			serializable=session.save(object);
	        session.flush();            // 强制同步到数据库
	        transaction.commit();       // 提交事务（立即生效）
	        session.clear();            // 清除缓存，避免内存溢出
	    } catch (Exception e) {
	        if (transaction != null) transaction.rollback();
	        throw e;
	    } finally {
	        session.close(); // 必须关闭 Session
	    }
		return serializable;
	}

	public <T> void saveOrUpdateObject(T clazz) {
		this.getHibernateTemplate().saveOrUpdate(clazz);
	}

	public int updateOrDeleteBySql(String sql){
		Connection conn=null;
		PreparedStatement ps=null;
		int result=0;
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			ps=conn.prepareStatement(sql);
			result=ps.executeUpdate();
//			StringManagerUtils.printLog(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringManagerUtils.printLog("sql执行失败-"+StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss")+":"+sql,2);
		} finally{
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	@SuppressWarnings("resource")
	public List<WellHandsontableChangedData.Updatelist> saveDeviceData(
			WellInformationManagerService<?> wellInformationManagerService,
			WellHandsontableChangedData wellHandsontableChangedData,
			String orgId,String deviceType,User user,int isCheckout) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		CallableStatement cs=null;
		PreparedStatement ps=null;
		String currentTiem=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		List<String> initWellList=new ArrayList<String>();
		List<String> deleteWellList=new ArrayList<String>();
		List<String> deleteWellNameList=new ArrayList<String>();
		List<String> disableWellIdList=new ArrayList<String>();
		List<WellHandsontableChangedData.Updatelist> collisionList=new ArrayList<WellHandsontableChangedData.Updatelist>();
		
		License license=LicenseMap.getMapObject().get(LicenseMap.SN);
		
		try {
			cs = conn.prepareCall("{call prd_update_device(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			Map<Integer,WellHandsontableChangedData.Updatelist> updateDeviceMap=new HashMap<>();
			if(wellHandsontableChangedData.getUpdatelist()!=null){
				for(int i=0;i<wellHandsontableChangedData.getUpdatelist().size();i++){
					if(StringManagerUtils.isNotNull(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName())){
						updateDeviceMap.put(StringManagerUtils.stringToInteger(wellHandsontableChangedData.getUpdatelist().get(i).getId()), wellHandsontableChangedData.getUpdatelist().get(i));
					}
				}
				
				for(int i=0;i<wellHandsontableChangedData.getUpdatelist().size();i++){
					try{
						if(StringManagerUtils.isNotNull(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName())){
							DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(wellHandsontableChangedData.getUpdatelist().get(i).getId());
							
							int status=1;
							if(languageResourceMap.get("disable").equalsIgnoreCase(wellHandsontableChangedData.getUpdatelist().get(i).getStatusName())){
								status=0;
								disableWellIdList.add(wellHandsontableChangedData.getUpdatelist().get(i).getId());
							}
							cs.setString(1, wellHandsontableChangedData.getUpdatelist().get(i).getId());
							cs.setString(2, wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName());
							
							String applicationScenariosValueStr=MemoryDataManagerTask.getCodeValue("APPLICATIONSCENARIOS", wellHandsontableChangedData.getUpdatelist().get(i).getApplicationScenariosName(), user.getLanguageName());
							cs.setInt(3, StringManagerUtils.stringToInteger(applicationScenariosValueStr));
							
							cs.setString(4, wellHandsontableChangedData.getUpdatelist().get(i).getDeviceTabInstance());
							
							cs.setString(5, wellHandsontableChangedData.getUpdatelist().get(i).getInstanceName());
							cs.setString(6, wellHandsontableChangedData.getUpdatelist().get(i).getDisplayInstanceName());
							cs.setString(7, wellHandsontableChangedData.getUpdatelist().get(i).getReportInstanceName());
							cs.setString(8, wellHandsontableChangedData.getUpdatelist().get(i).getAlarmInstanceName());
							cs.setString(9, wellHandsontableChangedData.getUpdatelist().get(i).getTcpType().toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client"));
							cs.setString(10, wellHandsontableChangedData.getUpdatelist().get(i).getSignInId());
							cs.setString(11, wellHandsontableChangedData.getUpdatelist().get(i).getIpPort().replaceAll("：", ":"));
							cs.setString(12, wellHandsontableChangedData.getUpdatelist().get(i).getSlave());
							cs.setString(13, StringManagerUtils.isNum(wellHandsontableChangedData.getUpdatelist().get(i).getPeakDelay())?wellHandsontableChangedData.getUpdatelist().get(i).getPeakDelay():"");
							
							cs.setInt(14, status);
							cs.setString(15, wellHandsontableChangedData.getUpdatelist().get(i).getCommissioningDate().replaceAll("null", ""));
							cs.setString(16, wellHandsontableChangedData.getUpdatelist().get(i).getSortNum());
							cs.registerOutParameter(17, Types.INTEGER);
							cs.registerOutParameter(18,Types.VARCHAR);
							cs.registerOutParameter(19, Types.INTEGER);
							cs.setInt(20, isCheckout);
							cs.executeUpdate();
							
							int saveSign=cs.getInt(17);
							String saveResultStr=cs.getString(18);
							int collisionDeviceId=cs.getInt(19);
							wellHandsontableChangedData.getUpdatelist().get(i).setSaveSign(saveSign);
							wellHandsontableChangedData.getUpdatelist().get(i).setSaveStr(saveResultStr);
							wellHandsontableChangedData.getUpdatelist().get(i).setCollisionDeviceId(collisionDeviceId);
							
							if(saveSign==0||saveSign==1){//保存成功
								if(saveSign==0){//添加
									this.saveDeviceOperationLog(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName(),0,user,deviceInfo!=null?deviceInfo.getDeviceType():0,"",currentTiem);
								}else if(saveSign==1){//更新
									this.saveDeviceOperationLog(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName(),1,user,deviceInfo!=null?deviceInfo.getDeviceType():0,"",currentTiem);
								}
								initWellList.add(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName());
							}else if(saveSign==-33){
								wellHandsontableChangedData.getUpdatelist().get(i).setSaveStr(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName()+":"+languageResourceMap.get("collisionInfo3"));
							}else if(saveSign==-22){
								wellHandsontableChangedData.getUpdatelist().get(i).setSaveStr(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName()+":"+languageResourceMap.get("collisionInfo1")+","+saveResultStr);
							}
							collisionList.add(wellHandsontableChangedData.getUpdatelist().get(i));
						}
					}catch(Exception e){
						e.printStackTrace();
						continue;
					}
				}
			}
			
			
			
			
			if(wellHandsontableChangedData.getInsertlist()!=null){
				for(int i=0;i<wellHandsontableChangedData.getInsertlist().size();i++){
					if(StringManagerUtils.isNotNull(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName())){
						updateDeviceMap.put(StringManagerUtils.stringToInteger(wellHandsontableChangedData.getInsertlist().get(i).getId()), wellHandsontableChangedData.getInsertlist().get(i));
					}
				}
				
				for(int i=0;i<wellHandsontableChangedData.getInsertlist().size();i++){
					try{
						if(StringManagerUtils.isNotNull(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName())){
							DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(wellHandsontableChangedData.getInsertlist().get(i).getId());
							
							int status=1;
							if(languageResourceMap.get("disable").equalsIgnoreCase(wellHandsontableChangedData.getInsertlist().get(i).getStatusName())){
								status=0;
								disableWellIdList.add(wellHandsontableChangedData.getInsertlist().get(i).getId());
							}
							cs.setString(1, wellHandsontableChangedData.getInsertlist().get(i).getId());
							cs.setString(2, wellHandsontableChangedData.getInsertlist().get(i).getDeviceName());
							
							String applicationScenariosValueStr=MemoryDataManagerTask.getCodeValue("APPLICATIONSCENARIOS", wellHandsontableChangedData.getInsertlist().get(i).getApplicationScenariosName(), user.getLanguageName());
							cs.setInt(3, StringManagerUtils.stringToInteger(applicationScenariosValueStr));
							
							cs.setString(4, wellHandsontableChangedData.getInsertlist().get(i).getDeviceTabInstance());
							
							cs.setString(5, wellHandsontableChangedData.getInsertlist().get(i).getInstanceName());
							cs.setString(6, wellHandsontableChangedData.getInsertlist().get(i).getDisplayInstanceName());
							cs.setString(7, wellHandsontableChangedData.getInsertlist().get(i).getReportInstanceName());
							cs.setString(8, wellHandsontableChangedData.getInsertlist().get(i).getAlarmInstanceName());
							cs.setString(9, wellHandsontableChangedData.getInsertlist().get(i).getTcpType().toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client"));
							cs.setString(10, wellHandsontableChangedData.getInsertlist().get(i).getSignInId());
							cs.setString(11, wellHandsontableChangedData.getInsertlist().get(i).getIpPort().replaceAll("：", ":"));
							cs.setString(12, wellHandsontableChangedData.getInsertlist().get(i).getSlave());
							cs.setString(13, StringManagerUtils.isNum(wellHandsontableChangedData.getInsertlist().get(i).getPeakDelay())?wellHandsontableChangedData.getInsertlist().get(i).getPeakDelay():"");
							
							cs.setInt(14, status);
							cs.setString(15, wellHandsontableChangedData.getInsertlist().get(i).getCommissioningDate().replaceAll("null", ""));
							cs.setString(16, wellHandsontableChangedData.getInsertlist().get(i).getSortNum());
							cs.registerOutParameter(17, Types.INTEGER);
							cs.registerOutParameter(18,Types.VARCHAR);
							cs.registerOutParameter(19, Types.INTEGER);
							cs.setInt(20, isCheckout);
							cs.executeUpdate();
							
							int saveSign=cs.getInt(17);
							String saveResultStr=cs.getString(18);
							int collisionDeviceId=cs.getInt(19);
							wellHandsontableChangedData.getInsertlist().get(i).setSaveSign(saveSign);
							wellHandsontableChangedData.getInsertlist().get(i).setSaveStr(saveResultStr);
							wellHandsontableChangedData.getInsertlist().get(i).setCollisionDeviceId(collisionDeviceId);
							
							if(saveSign==0||saveSign==1){//保存成功
								if(saveSign==0){//添加
									this.saveDeviceOperationLog(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName(),0,user,deviceInfo!=null?deviceInfo.getDeviceType():0,"",currentTiem);
								}else if(saveSign==1){//更新
									this.saveDeviceOperationLog(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName(),1,user,deviceInfo!=null?deviceInfo.getDeviceType():0,"",currentTiem);
								}
								initWellList.add(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName());
							}else if(saveSign==-33){
								wellHandsontableChangedData.getInsertlist().get(i).setSaveStr(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName()+":"+languageResourceMap.get("collisionInfo3"));
							}else if(saveSign==-22){
								wellHandsontableChangedData.getInsertlist().get(i).setSaveStr(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName()+":"+languageResourceMap.get("collisionInfo1")+","+saveResultStr);
							}
							collisionList.add(wellHandsontableChangedData.getInsertlist().get(i));
						}
					}catch(Exception e){
						e.printStackTrace();
						continue;
					}
				}
			}
			
			List<WellHandsontableChangedData.Updatelist> noCheckoutSaveList=new ArrayList<WellHandsontableChangedData.Updatelist>();
			for(WellHandsontableChangedData.Updatelist updateDeviceInfo:collisionList){
				if(updateDeviceInfo.getSaveSign()==-22){
					int collisionDeviceId=updateDeviceInfo.getCollisionDeviceId();
					if(updateDeviceMap.containsKey(collisionDeviceId)){
						WellHandsontableChangedData.Updatelist collisionDevice=updateDeviceMap.get(collisionDeviceId);
						if(! (collisionDevice.getSignInId().equals(updateDeviceInfo.getSignInId())
								&& collisionDevice.getSlave().equals(updateDeviceInfo.getSlave())
								&& collisionDevice.getIpPort().equals(updateDeviceInfo.getIpPort())
								) ){
							noCheckoutSaveList.add(updateDeviceInfo);
							DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(updateDeviceInfo.getId());
							
							int status=1;
							if(languageResourceMap.get("disable").equalsIgnoreCase(updateDeviceInfo.getStatusName())){
								status=0;
								disableWellIdList.add(updateDeviceInfo.getId());
							}
							cs.setString(1, updateDeviceInfo.getId());
							cs.setString(2, updateDeviceInfo.getDeviceName());
							
							String applicationScenariosValueStr=MemoryDataManagerTask.getCodeValue("APPLICATIONSCENARIOS", updateDeviceInfo.getApplicationScenariosName(), user.getLanguageName());
							cs.setInt(3, StringManagerUtils.stringToInteger(applicationScenariosValueStr));
							
							cs.setString(4, updateDeviceInfo.getDeviceTabInstance());
							
							cs.setString(5, updateDeviceInfo.getInstanceName());
							cs.setString(6, updateDeviceInfo.getDisplayInstanceName());
							cs.setString(7, updateDeviceInfo.getReportInstanceName());
							cs.setString(8, updateDeviceInfo.getAlarmInstanceName());
							cs.setString(9, updateDeviceInfo.getTcpType().toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client"));
							cs.setString(10, updateDeviceInfo.getSignInId());
							cs.setString(11, updateDeviceInfo.getIpPort().replaceAll("：", ":"));
							cs.setString(12, updateDeviceInfo.getSlave());
							cs.setString(13, StringManagerUtils.isNum(updateDeviceInfo.getPeakDelay())?updateDeviceInfo.getPeakDelay():"");
							
							cs.setInt(14, status);
							cs.setString(15, updateDeviceInfo.getCommissioningDate().replaceAll("null", ""));
							cs.setString(16, updateDeviceInfo.getSortNum());
							cs.registerOutParameter(17, Types.INTEGER);
							cs.registerOutParameter(18,Types.VARCHAR);
							cs.registerOutParameter(19, Types.INTEGER);
							cs.setInt(20, 0);
							cs.executeUpdate();
							
							updateDeviceInfo.setSaveSign(cs.getInt(17));
							updateDeviceInfo.setSaveStr(cs.getString(18));
							updateDeviceInfo.setCollisionDeviceId(cs.getInt(19));
							
							if(updateDeviceInfo.getSaveSign()==0||updateDeviceInfo.getSaveSign()==1){//保存成功
								if(updateDeviceInfo.getSaveSign()==0){//添加
									this.saveDeviceOperationLog(updateDeviceInfo.getDeviceName(),0,user,deviceInfo!=null?deviceInfo.getDeviceType():0,"",currentTiem);
								}else if(updateDeviceInfo.getSaveSign()==1){//更新
									this.saveDeviceOperationLog(updateDeviceInfo.getDeviceName(),1,user,deviceInfo!=null?deviceInfo.getDeviceType():0,"",currentTiem);
								}
								initWellList.add(updateDeviceInfo.getDeviceName());
							}else if(updateDeviceInfo.getSaveSign()==-33){
								updateDeviceInfo.setSaveStr(updateDeviceInfo.getDeviceName()+":"+languageResourceMap.get("collisionInfo3"));
							}else if(updateDeviceInfo.getSaveSign()==-22){
								updateDeviceInfo.setSaveStr(updateDeviceInfo.getDeviceName()+":"+languageResourceMap.get("collisionInfo1")+","+updateDeviceInfo.getSaveStr());
							}
						}
					}
				}
			}
			
			for(WellHandsontableChangedData.Updatelist updateDeviceInfo:noCheckoutSaveList){
				deleteWellList.add(updateDeviceInfo.getId());
				deleteWellNameList.add(updateDeviceInfo.getDeviceName());
			}
			
			if(wellHandsontableChangedData.getDelidslist()!=null&&wellHandsontableChangedData.getDelidslist().size()>0){
				String delIds="";
				String delSql="";
				String queryDeleteWellSql="";
				for(int i=0;i<wellHandsontableChangedData.getDelidslist().size();i++){
					delIds+=wellHandsontableChangedData.getDelidslist().get(i);
					if(i<wellHandsontableChangedData.getDelidslist().size()-1){
						delIds+=",";
					}
				}
				queryDeleteWellSql="select id,t.devicename,t.devicetype,t.orgid from tbl_device t "
						+ " where t.id in ("+StringUtils.join(wellHandsontableChangedData.getDelidslist(), ",")+")"
						+ " and t.orgid in("+orgId+")";
				delSql="delete from tbl_device t "
						+ " where t.id in ("+StringUtils.join(wellHandsontableChangedData.getDelidslist(), ",")+") "
						+ " and t.orgid in("+orgId+")";
				List<?> list = this.findCallSql(queryDeleteWellSql);
				for(int i=0;i<list.size();i++){
					Object[] obj=(Object[]) list.get(i);
					deleteWellList.add(obj[0]+"");
					deleteWellNameList.add(obj[1]+"");
					this.saveDeviceOperationLog(obj[1]+"",2,user,StringManagerUtils.stringToInteger(obj[2]+""),obj[3]+"",currentTiem);
				}
				
				ps=conn.prepareStatement(delSql);
				int result=ps.executeUpdate();
			}
			ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
			
			if(deleteWellList.size()>0){
				DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
				dataSynchronizationThread.setSign(102);
				dataSynchronizationThread.setInitWellList(null);
				dataSynchronizationThread.setUpdateList(null);
				dataSynchronizationThread.setAddList(null);
				dataSynchronizationThread.setDeleteList(deleteWellList);
				dataSynchronizationThread.setDeleteNameList(deleteWellNameList);
				dataSynchronizationThread.setCondition(0);
				dataSynchronizationThread.setMethod("delete");
				dataSynchronizationThread.setUser(user);
				dataSynchronizationThread.setDeviceType(deviceType);
				dataSynchronizationThread.setWellInformationManagerService(wellInformationManagerService);
				executor.execute(dataSynchronizationThread);
			}
			if(initWellList.size()>0){
				DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
				dataSynchronizationThread.setSign(103);
				dataSynchronizationThread.setInitWellList(initWellList);
//				dataSynchronizationThread.setUpdateList(updateWellList);
//				dataSynchronizationThread.setAddList(addWellList);
				dataSynchronizationThread.setDeleteList(null);
				dataSynchronizationThread.setDeleteNameList(null);
				dataSynchronizationThread.setCondition(1);
				dataSynchronizationThread.setMethod("update");
				dataSynchronizationThread.setUser(user);
				dataSynchronizationThread.setWellInformationManagerService(wellInformationManagerService);
				dataSynchronizationThread.setDeviceType(deviceType);
				executor.execute(dataSynchronizationThread);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(ps!=null){
				ps.close();
			}
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
		return collisionList;
	}
	
	@SuppressWarnings("resource")
	public List<WellHandsontableChangedData.Updatelist> batchAddDevice(WellInformationManagerService<?> wellInformationManagerService,WellHandsontableChangedData wellHandsontableChangedData,
			String orgId,String deviceType,String isCheckout,User user) throws SQLException {
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		PreparedStatement ps=null;
		
		String currentTiem=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		List<String> initWellList=new ArrayList<String>();
		List<String> deleteWellList=new ArrayList<String>();
		List<String> deleteWellNameList=new ArrayList<String>();
		List<WellHandsontableChangedData.Updatelist> collisionList=new ArrayList<WellHandsontableChangedData.Updatelist>();
		int license=0;
		AppRunStatusProbeResonanceData acStatusProbeResonanceData=CalculateUtils.appProbe("",10);
		if(acStatusProbeResonanceData!=null){
			license=acStatusProbeResonanceData.getLicenseNumber();
		}
		
		Map<String,Integer> deviceTypeMap=new HashMap<>();
		if(!StringManagerUtils.isNum(deviceType)){
			String sql="select t.name_"+user.getLanguageName()+",t.id from tbl_devicetypeinfo t where t.id in("+user.getDeviceTypeIds()+") order by t.id";
			List<?> deviceTypeList=this.findCallSql(sql);
			for(int i=0;i<deviceTypeList.size();i++){
				Object[] obj=(Object[]) deviceTypeList.get(i);
				deviceTypeMap.put(obj[0]+"", StringManagerUtils.stringToInteger(obj[1]+""));
			}
		}
		try {
			cs = conn.prepareCall("{call prd_save_device(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			if(wellHandsontableChangedData.getUpdatelist()!=null){
				for(int i=0;i<wellHandsontableChangedData.getUpdatelist().size();i++){
					try{
						if(StringManagerUtils.isNotNull(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName())){
							int status=1;
							if(languageResourceMap.get("disable").equalsIgnoreCase(wellHandsontableChangedData.getUpdatelist().get(i).getStatusName())){
								status=0;
							}
							
							cs.setString(1, orgId);
							cs.setString(2, wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName());
							
							String saveDeviceType=deviceType;
							if(!StringManagerUtils.isNum(deviceType)){
								String deviceTypeName="";
								if("zh_CN".equalsIgnoreCase(user.getLanguageName())){
									deviceTypeName=wellHandsontableChangedData.getUpdatelist().get(i).getDeviceTypeName_zh_CN();
								}else if("en".equalsIgnoreCase(user.getLanguageName())){
									deviceTypeName=wellHandsontableChangedData.getUpdatelist().get(i).getDeviceTypeName_en();
								}else if("ru".equalsIgnoreCase(user.getLanguageName())){
									deviceTypeName=wellHandsontableChangedData.getUpdatelist().get(i).getDeviceTypeName_ru();
								}
								saveDeviceType=deviceTypeMap.get(deviceTypeName)+"";
							}
							cs.setString(3, saveDeviceType);
							
							String applicationScenariosValueStr=MemoryDataManagerTask.getCodeValue("APPLICATIONSCENARIOS", wellHandsontableChangedData.getUpdatelist().get(i).getApplicationScenariosName(), user.getLanguageName());
							cs.setInt(4, StringManagerUtils.stringToInteger(applicationScenariosValueStr));
							
							cs.setString(5, wellHandsontableChangedData.getUpdatelist().get(i).getInstanceName());
							cs.setString(6, wellHandsontableChangedData.getUpdatelist().get(i).getDisplayInstanceName());
							cs.setString(7, wellHandsontableChangedData.getUpdatelist().get(i).getReportInstanceName());
							cs.setString(8, wellHandsontableChangedData.getUpdatelist().get(i).getAlarmInstanceName());
							cs.setString(9, wellHandsontableChangedData.getUpdatelist().get(i).getTcpType().toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client"));
							cs.setString(10, wellHandsontableChangedData.getUpdatelist().get(i).getSignInId());
							cs.setString(11, wellHandsontableChangedData.getUpdatelist().get(i).getIpPort().replaceAll("：", ":"));
							cs.setString(12, wellHandsontableChangedData.getUpdatelist().get(i).getSlave());
							cs.setInt(13, StringManagerUtils.stringToInteger(wellHandsontableChangedData.getUpdatelist().get(i).getPeakDelay()));
							
							cs.setInt(14, status);
							
							cs.setString(15, wellHandsontableChangedData.getUpdatelist().get(i).getCommissioningDate().replaceAll("null", ""));
							
							cs.setString(16, wellHandsontableChangedData.getUpdatelist().get(i).getSortNum());
							
							cs.setInt(17, StringManagerUtils.stringToInteger(isCheckout));
							cs.setInt(18, license);
							cs.registerOutParameter(19, Types.INTEGER);
							cs.registerOutParameter(20,Types.VARCHAR);
							cs.executeUpdate();
							int saveSign=cs.getInt(19);
							String saveResultStr=cs.getString(20);
							if(saveSign==0||saveSign==1){//保存成功
								if(saveSign==0){//添加
									this.saveDeviceOperationLog(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName(),0,user,StringManagerUtils.stringToInteger(saveDeviceType),"",currentTiem);
//									addWellList.add(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName());
								}else if(saveSign==1){//更新
									this.saveDeviceOperationLog(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName(),1,user,StringManagerUtils.stringToInteger(saveDeviceType),"",currentTiem);
//									updateWellList.add(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName());
								}
								
								initWellList.add(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName());
							}else{//保存失败，数据冲突或者超出限制
								wellHandsontableChangedData.getUpdatelist().get(i).setSaveSign(saveSign);
								wellHandsontableChangedData.getUpdatelist().get(i).setSaveStr(saveResultStr);
								collisionList.add(wellHandsontableChangedData.getUpdatelist().get(i));
								
								if(saveSign==-33){
									wellHandsontableChangedData.getUpdatelist().get(i).setSaveStr(languageResourceMap.get("collisionInfo3"));
								}else if(saveSign==-22){
									wellHandsontableChangedData.getUpdatelist().get(i).setSaveStr(languageResourceMap.get("collisionInfo1"));
								}
							}
							
						}
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}
			}
			if(wellHandsontableChangedData.getInsertlist()!=null){
				for(int i=0;i<wellHandsontableChangedData.getInsertlist().size();i++){
					try{
						if(StringManagerUtils.isNotNull(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName())){
							if(StringManagerUtils.isNotNull(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName())){
								int status=1;
								if(languageResourceMap.get("disable").equalsIgnoreCase(wellHandsontableChangedData.getInsertlist().get(i).getStatusName())){
									status=0;
								}
								
								cs.setString(1, orgId);
								cs.setString(2, wellHandsontableChangedData.getInsertlist().get(i).getDeviceName());

								String saveDeviceType=deviceType;
								if(!StringManagerUtils.isNum(deviceType)){
									String deviceTypeName="";
									if("zh_CN".equalsIgnoreCase(user.getLanguageName())){
										deviceTypeName=wellHandsontableChangedData.getUpdatelist().get(i).getDeviceTypeName_zh_CN();
									}else if("en".equalsIgnoreCase(user.getLanguageName())){
										deviceTypeName=wellHandsontableChangedData.getUpdatelist().get(i).getDeviceTypeName_en();
									}else if("ru".equalsIgnoreCase(user.getLanguageName())){
										deviceTypeName=wellHandsontableChangedData.getUpdatelist().get(i).getDeviceTypeName_ru();
									}
									saveDeviceType=deviceTypeMap.get(deviceTypeName)+"";
								}
								cs.setString(3, saveDeviceType);
								
								String applicationScenariosValueStr=MemoryDataManagerTask.getCodeValue("APPLICATIONSCENARIOS", wellHandsontableChangedData.getInsertlist().get(i).getApplicationScenariosName(), user.getLanguageName());
								cs.setInt(4, StringManagerUtils.stringToInteger(applicationScenariosValueStr));
								
								cs.setString(5, wellHandsontableChangedData.getInsertlist().get(i).getInstanceName());
								cs.setString(6, wellHandsontableChangedData.getInsertlist().get(i).getDisplayInstanceName());
								cs.setString(7, wellHandsontableChangedData.getInsertlist().get(i).getReportInstanceName());
								cs.setString(8, wellHandsontableChangedData.getInsertlist().get(i).getAlarmInstanceName());
								cs.setString(9, wellHandsontableChangedData.getInsertlist().get(i).getTcpType().toLowerCase().replaceAll("tcpserver", "TCP Server").replaceAll("tcpclient", "TCP Client"));
								cs.setString(10, wellHandsontableChangedData.getInsertlist().get(i).getSignInId());
								cs.setString(11, wellHandsontableChangedData.getInsertlist().get(i).getIpPort().replaceAll("：", ":"));
								cs.setString(12, wellHandsontableChangedData.getInsertlist().get(i).getSlave());
								cs.setInt(13, StringManagerUtils.stringToInteger(wellHandsontableChangedData.getInsertlist().get(i).getPeakDelay()));
								
								cs.setInt(14, status);
								cs.setString(15, wellHandsontableChangedData.getInsertlist().get(i).getCommissioningDate().replaceAll("null", ""));
								cs.setString(16, wellHandsontableChangedData.getInsertlist().get(i).getSortNum());
								
								cs.setInt(17, StringManagerUtils.stringToInteger(isCheckout));
								cs.setInt(18, license);
								cs.registerOutParameter(19, Types.INTEGER);
								cs.registerOutParameter(20,Types.VARCHAR);
								cs.executeUpdate();
								int saveSign=cs.getInt(19);
								String saveResultStr=cs.getString(20);
								if(saveSign==0||saveSign==1){//保存成功
									if(saveSign==0){//添加
//										addWellList.add(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName());
										this.saveDeviceOperationLog(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName(),0,user,StringManagerUtils.stringToInteger(saveDeviceType),"",currentTiem);
									}else if(saveSign==1){//更新
//										updateWellList.add(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName());
										this.saveDeviceOperationLog(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName(),1,user,StringManagerUtils.stringToInteger(saveDeviceType),"",currentTiem);
									}
									initWellList.add(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName());
								}else{//保存失败，数据冲突或者超出限制
									wellHandsontableChangedData.getInsertlist().get(i).setSaveSign(saveSign);
									wellHandsontableChangedData.getInsertlist().get(i).setSaveStr(saveResultStr);
									collisionList.add(wellHandsontableChangedData.getInsertlist().get(i));
									
									if(saveSign==-33){
										wellHandsontableChangedData.getInsertlist().get(i).setSaveStr(languageResourceMap.get("collisionInfo3"));
									}else if(saveSign==-22){
										wellHandsontableChangedData.getInsertlist().get(i).setSaveStr(languageResourceMap.get("collisionInfo1"));
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}
			}
			if(wellHandsontableChangedData.getDelidslist()!=null&&wellHandsontableChangedData.getDelidslist().size()>0){
				String delIds="";
				String delSql="";
				String queryDeleteWellSql="";
				for(int i=0;i<wellHandsontableChangedData.getDelidslist().size();i++){
					delIds+=wellHandsontableChangedData.getDelidslist().get(i);
					if(i<wellHandsontableChangedData.getDelidslist().size()-1){
						delIds+=",";
					}
				}
				queryDeleteWellSql="select id,t.devicename,t.devicetype,t.orgid from tbl_device t "
						+ " where t.id in ("+StringUtils.join(wellHandsontableChangedData.getDelidslist(), ",")+")"
						+ " and t.orgid in("+orgId+")";
				delSql="delete from tbl_device t "
						+ " where t.id in ("+StringUtils.join(wellHandsontableChangedData.getDelidslist(), ",")+") "
						+ " and t.orgid in("+orgId+")";
				List<?> list = this.findCallSql(queryDeleteWellSql);
				for(int i=0;i<list.size();i++){
					Object[] obj=(Object[]) list.get(i);
					deleteWellList.add(obj[0]+"");
					deleteWellNameList.add(obj[1]+"");
					this.saveDeviceOperationLog(obj[1]+"",2,user,StringManagerUtils.stringToInteger(obj[2]+""),obj[3]+"",currentTiem);
				}
				ps=conn.prepareStatement(delSql);
				int result=ps.executeUpdate();
			}
			ThreadPool executor = new ThreadPool("dataSynchronization",Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getCorePoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getMaximumPoolSize(), 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getKeepAliveTime(), 
					TimeUnit.SECONDS, 
					Config.getInstance().configFile.getAp().getThreadPool().getDataSynchronization().getWattingCount());
			if(deleteWellList.size()>0){
				DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
				dataSynchronizationThread.setSign(102);
				dataSynchronizationThread.setInitWellList(null);
				dataSynchronizationThread.setUpdateList(null);
				dataSynchronizationThread.setAddList(null);
				dataSynchronizationThread.setDeleteList(deleteWellList);
				dataSynchronizationThread.setDeleteNameList(deleteWellNameList);
				dataSynchronizationThread.setCondition(0);
				dataSynchronizationThread.setMethod("delete");
				dataSynchronizationThread.setUser(user);
				dataSynchronizationThread.setDeviceType(deviceType);
				dataSynchronizationThread.setWellInformationManagerService(wellInformationManagerService);
				executor.execute(dataSynchronizationThread);
			}
			if(initWellList.size()>0){
				DataSynchronizationThread dataSynchronizationThread=new DataSynchronizationThread();
				dataSynchronizationThread.setSign(103);
				dataSynchronizationThread.setInitWellList(initWellList);
//				dataSynchronizationThread.setUpdateList(updateWellList);
//				dataSynchronizationThread.setAddList(addWellList);
				dataSynchronizationThread.setDeleteList(null);
				dataSynchronizationThread.setDeleteNameList(null);
				dataSynchronizationThread.setCondition(1);
				dataSynchronizationThread.setMethod("update");
				dataSynchronizationThread.setUser(user);
				dataSynchronizationThread.setDeviceType(deviceType);
				dataSynchronizationThread.setWellInformationManagerService(wellInformationManagerService);
				executor.execute(dataSynchronizationThread);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(ps!=null){
				ps.close();
			}
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
		return collisionList;
	}
	
	@SuppressWarnings("resource")
	public Boolean saveSMSDeviceData(WellHandsontableChangedData wellHandsontableChangedData,String orgId,int deviceType,User user) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		PreparedStatement ps=null;
		List<String> initWellList=new ArrayList<String>();
		List<String> updateWellList=new ArrayList<String>();
		List<String> addWellList=new ArrayList<String>();
		List<String> deleteWellList=new ArrayList<String>();
		try {
			cs = conn.prepareCall("{call prd_update_smsdevice(?,?,?,?,?)}");
			if(wellHandsontableChangedData.getUpdatelist()!=null){
				for(int i=0;i<wellHandsontableChangedData.getUpdatelist().size();i++){
					try{
						if(StringManagerUtils.isNotNull(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName())){
							cs.setString(1, wellHandsontableChangedData.getUpdatelist().get(i).getId());
							cs.setString(2, wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName());
							cs.setString(3, wellHandsontableChangedData.getUpdatelist().get(i).getInstanceName());
							cs.setString(4, wellHandsontableChangedData.getUpdatelist().get(i).getSignInId().replaceAll("：", ":"));
							cs.setString(5, wellHandsontableChangedData.getUpdatelist().get(i).getSortNum());
							cs.executeUpdate();
							updateWellList.add(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName());
							if(StringManagerUtils.isNotNull(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName())
									&&StringManagerUtils.isNotNull(wellHandsontableChangedData.getUpdatelist().get(i).getSignInId())
									&&StringManagerUtils.isNotNull(wellHandsontableChangedData.getUpdatelist().get(i).getInstanceName()) 
									){
								initWellList.add(wellHandsontableChangedData.getUpdatelist().get(i).getDeviceName());
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						continue;
					}
				}
			}
			if(wellHandsontableChangedData.getInsertlist()!=null){
				for(int i=0;i<wellHandsontableChangedData.getInsertlist().size();i++){
					try{
						if(StringManagerUtils.isNotNull(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName())){
							cs.setString(1, wellHandsontableChangedData.getInsertlist().get(i).getId());
							cs.setString(2, wellHandsontableChangedData.getInsertlist().get(i).getDeviceName());
							cs.setString(3, wellHandsontableChangedData.getInsertlist().get(i).getInstanceName());
							cs.setString(4, wellHandsontableChangedData.getInsertlist().get(i).getSignInId().replaceAll("：", ":"));
							cs.setString(5, wellHandsontableChangedData.getInsertlist().get(i).getSortNum());
							cs.executeUpdate();
							addWellList.add(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName());
							if(StringManagerUtils.isNotNull(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName())
									&&StringManagerUtils.isNotNull(wellHandsontableChangedData.getInsertlist().get(i).getSignInId()) 
									&&StringManagerUtils.isNotNull(wellHandsontableChangedData.getInsertlist().get(i).getInstanceName()) 
									){
								initWellList.add(wellHandsontableChangedData.getInsertlist().get(i).getDeviceName());
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						continue;
					}
				}
			}
			if(wellHandsontableChangedData.getDelidslist()!=null&&wellHandsontableChangedData.getDelidslist().size()>0){
				String delIds="";
				String delSql="";
				String queryDeleteWellSql="";
				for(int i=0;i<wellHandsontableChangedData.getDelidslist().size();i++){
					delIds+=wellHandsontableChangedData.getDelidslist().get(i);
					if(i<wellHandsontableChangedData.getDelidslist().size()-1){
						delIds+=",";
					}
				}
				queryDeleteWellSql="select devicename from tbl_smsdevice t "
						+ " where  t.id in ("+StringUtils.join(wellHandsontableChangedData.getDelidslist(), ",")+")"
						+ " and t.orgid in("+orgId+")";
				delSql="delete from tbl_smsdevice t "
						+ " where t.id in ("+StringUtils.join(wellHandsontableChangedData.getDelidslist(), ",")+") "
						+ " and t.orgid in("+orgId+")";
				List<?> list = this.findCallSql(queryDeleteWellSql);
				for(int i=0;i<list.size();i++){
					deleteWellList.add(list.get(i)+"");
				}
				
				
				ps=conn.prepareStatement(delSql);
				int result=ps.executeUpdate();
			}
			saveDeviceOperationLog(updateWellList,addWellList,deleteWellList,user,300+"");
			
			if(initWellList.size()>0){
				EquipmentDriverServerTask.initSMSDevice(initWellList,"update");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(ps!=null){
				ps.close();
			}
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
		return true;
	}
	
	@SuppressWarnings("resource")
	public List<PumpingModelHandsontableChangedData.Updatelist> savePumpingModelHandsontableData(PumpingModelHandsontableChangedData pumpingModelHandsontableChangedData,String selectedRecordId,String language) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		PreparedStatement ps=null;
		List<PumpingModelHandsontableChangedData.Updatelist> collisionList=new ArrayList<PumpingModelHandsontableChangedData.Updatelist>();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			cs = conn.prepareCall("{call prd_update_pumpingmodel(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			if(pumpingModelHandsontableChangedData.getUpdatelist()!=null){
				for(int i=0;i<pumpingModelHandsontableChangedData.getUpdatelist().size();i++){
					try{
						if(StringManagerUtils.isNotNull(pumpingModelHandsontableChangedData.getUpdatelist().get(i).getManufacturer())){
							cs.setString(1, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getId());
							cs.setString(2, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getManufacturer());
							cs.setString(3, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getModel());
							cs.setString(4, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getStroke());
							cs.setString(5, languageResourceMap.get("clockwise").equals(pumpingModelHandsontableChangedData.getUpdatelist().get(i).getCrankRotationDirection())?"Clockwise":"Anticlockwise");
							cs.setString(6, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getOffsetAngleOfCrank());
							cs.setString(7, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getCrankGravityRadius());
							cs.setString(8, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getSingleCrankWeight());
							cs.setString(9, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getSingleCrankPinWeight());
							cs.setString(10, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getStructuralUnbalance());
							cs.setString(11, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getBalanceWeight());
							cs.registerOutParameter(12, Types.INTEGER);
							cs.registerOutParameter(13,Types.VARCHAR);
							cs.executeUpdate();
							int saveSign=cs.getInt(12);
							String saveResultStr=cs.getString(13);
							pumpingModelHandsontableChangedData.getUpdatelist().get(i).setSaveSign(saveSign);
							pumpingModelHandsontableChangedData.getUpdatelist().get(i).setSaveStr(saveResultStr);
							collisionList.add(pumpingModelHandsontableChangedData.getUpdatelist().get(i));
							if(saveSign==1){
								MemoryDataManagerTask.loadDeviceInfoByPumpingId(pumpingModelHandsontableChangedData.getUpdatelist().get(i).getId(),"update");
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						continue;
					}
				}
			}
			if(pumpingModelHandsontableChangedData.getInsertlist()!=null){
				for(int i=0;i<pumpingModelHandsontableChangedData.getInsertlist().size();i++){
					try{
						if(StringManagerUtils.isNotNull(pumpingModelHandsontableChangedData.getInsertlist().get(i).getManufacturer())){
							cs.setString(1, pumpingModelHandsontableChangedData.getInsertlist().get(i).getId());
							cs.setString(2, pumpingModelHandsontableChangedData.getInsertlist().get(i).getManufacturer());
							cs.setString(3, pumpingModelHandsontableChangedData.getInsertlist().get(i).getModel());
							cs.setString(4, pumpingModelHandsontableChangedData.getInsertlist().get(i).getStroke());
							cs.setString(5, languageResourceMap.get("clockwise").equals(pumpingModelHandsontableChangedData.getInsertlist().get(i).getCrankRotationDirection())?"Clockwise":"Anticlockwise");
							cs.setString(6, pumpingModelHandsontableChangedData.getInsertlist().get(i).getOffsetAngleOfCrank());
							cs.setString(7, pumpingModelHandsontableChangedData.getInsertlist().get(i).getCrankGravityRadius());
							cs.setString(8, pumpingModelHandsontableChangedData.getInsertlist().get(i).getSingleCrankWeight());
							cs.setString(9, pumpingModelHandsontableChangedData.getInsertlist().get(i).getSingleCrankPinWeight());
							cs.setString(10, pumpingModelHandsontableChangedData.getInsertlist().get(i).getStructuralUnbalance());
							cs.setString(11, pumpingModelHandsontableChangedData.getInsertlist().get(i).getBalanceWeight());
							cs.registerOutParameter(12, Types.INTEGER);
							cs.registerOutParameter(13,Types.VARCHAR);
							cs.executeUpdate();
							int saveSign=cs.getInt(12);
							String saveResultStr=cs.getString(13);
							pumpingModelHandsontableChangedData.getInsertlist().get(i).setSaveSign(saveSign);
							pumpingModelHandsontableChangedData.getInsertlist().get(i).setSaveStr(saveResultStr);
							collisionList.add(pumpingModelHandsontableChangedData.getInsertlist().get(i));
						}
					}catch(Exception e){
						e.printStackTrace();
						continue;
					}
					
				}
			}
			if(pumpingModelHandsontableChangedData.getDelidslist()!=null){
				String delSql="delete from tbl_pumpingmodel t where t.id in ("+StringUtils.join(pumpingModelHandsontableChangedData.getDelidslist(), ",")+")";
				ps=conn.prepareStatement(delSql);
				int result=ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(ps!=null){
				ps.close();
			}
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
		return collisionList;
	}
	
	@SuppressWarnings("resource")
	public List<PumpingModelHandsontableChangedData.Updatelist> batchAddPumpingModel(PumpingModelHandsontableChangedData pumpingModelHandsontableChangedData,int isCheckout,String language) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		PreparedStatement ps=null;
		List<PumpingModelHandsontableChangedData.Updatelist> collisionList=new ArrayList<PumpingModelHandsontableChangedData.Updatelist>();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		try {
			cs = conn.prepareCall("{call prd_save_pumpingmodel(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			if(pumpingModelHandsontableChangedData.getUpdatelist()!=null){
				for(int i=0;i<pumpingModelHandsontableChangedData.getUpdatelist().size();i++){
					try{
						if(StringManagerUtils.isNotNull(pumpingModelHandsontableChangedData.getUpdatelist().get(i).getManufacturer())){
							cs.setString(1, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getManufacturer());
							cs.setString(2, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getModel());
							cs.setString(3, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getStroke());
							cs.setString(4, languageResourceMap.get("clockwise").equals(pumpingModelHandsontableChangedData.getUpdatelist().get(i).getCrankRotationDirection())?"Clockwise":"Anticlockwise");
							cs.setString(5, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getOffsetAngleOfCrank());
							cs.setString(6, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getCrankGravityRadius());
							cs.setString(7, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getSingleCrankWeight());
							cs.setString(8, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getSingleCrankPinWeight());
							cs.setString(9, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getStructuralUnbalance());
							cs.setString(10, pumpingModelHandsontableChangedData.getUpdatelist().get(i).getBalanceWeight());
							cs.setInt(11, isCheckout);
							cs.registerOutParameter(12, Types.INTEGER);
							cs.registerOutParameter(13,Types.VARCHAR);
							cs.executeUpdate();
							int saveSign=cs.getInt(12);
							String saveResultStr=cs.getString(13);
							if(saveSign!=0&&saveSign!=1){
								pumpingModelHandsontableChangedData.getUpdatelist().get(i).setSaveSign(saveSign);
								pumpingModelHandsontableChangedData.getUpdatelist().get(i).setSaveStr(saveResultStr);
								collisionList.add(pumpingModelHandsontableChangedData.getUpdatelist().get(i));
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						continue;
					}
				}
			}
			if(pumpingModelHandsontableChangedData.getInsertlist()!=null){
				for(int i=0;i<pumpingModelHandsontableChangedData.getInsertlist().size();i++){
					try{
						if(StringManagerUtils.isNotNull(pumpingModelHandsontableChangedData.getInsertlist().get(i).getManufacturer())){
							cs.setString(1, pumpingModelHandsontableChangedData.getInsertlist().get(i).getManufacturer());
							cs.setString(2, pumpingModelHandsontableChangedData.getInsertlist().get(i).getModel());
							cs.setString(3, pumpingModelHandsontableChangedData.getInsertlist().get(i).getStroke());
							cs.setString(4, languageResourceMap.get("clockwise").equals(pumpingModelHandsontableChangedData.getInsertlist().get(i).getCrankRotationDirection())?"Clockwise":"Anticlockwise");
							cs.setString(5, pumpingModelHandsontableChangedData.getInsertlist().get(i).getOffsetAngleOfCrank());
							cs.setString(6, pumpingModelHandsontableChangedData.getInsertlist().get(i).getCrankGravityRadius());
							cs.setString(7, pumpingModelHandsontableChangedData.getInsertlist().get(i).getSingleCrankWeight());
							cs.setString(8, pumpingModelHandsontableChangedData.getInsertlist().get(i).getSingleCrankPinWeight());
							cs.setString(9, pumpingModelHandsontableChangedData.getInsertlist().get(i).getStructuralUnbalance());
							cs.setString(10, pumpingModelHandsontableChangedData.getInsertlist().get(i).getBalanceWeight());
							cs.setInt(11, isCheckout);
							cs.registerOutParameter(12, Types.INTEGER);
							cs.registerOutParameter(13,Types.VARCHAR);
							cs.executeUpdate();
							int saveSign=cs.getInt(12);
							String saveResultStr=cs.getString(13);
							if(saveSign!=0&&saveSign!=1){
								pumpingModelHandsontableChangedData.getInsertlist().get(i).setSaveSign(saveSign);
								pumpingModelHandsontableChangedData.getInsertlist().get(i).setSaveStr(saveResultStr);
								collisionList.add(pumpingModelHandsontableChangedData.getInsertlist().get(i));
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						continue;
					}
				}
			}
			if(pumpingModelHandsontableChangedData.getDelidslist()!=null){
				String delSql="delete from tbl_pumpingmodel t where t.id in ("+StringUtils.join(pumpingModelHandsontableChangedData.getDelidslist(), ",")+")";
				ps=conn.prepareStatement(delSql);
				int result=ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(ps!=null){
				ps.close();
			}
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
		return collisionList;
	}
	
	public boolean saveDeviceOperationLog(String deviceName,int type,User user,int deviceType,String remark,String currentTiem) throws SQLException{
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call prd_save_deviceOperationLog(?,?,?,?,?,?,?)}");
			cs.setString(1, currentTiem);
			cs.setString(2, deviceName);
			cs.setInt(3, deviceType);
			cs.setInt(4, type);
			cs.setString(5, user.getUserId());
			cs.setString(6, user.getLoginIp());
			cs.setString(7, remark);
			cs.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
		return true;
	}
	
	public boolean saveDeviceOperationLog(List<String> updateWellList,List<String> addWellList,List<String> deleteWellList,User user,String deviceType) throws SQLException{
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call prd_save_deviceOperationLog(?,?,?,?,?,?,?)}");
			String currentTiem=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			for(int i=0;addWellList!=null && i<addWellList.size();i++){
				cs.setString(1, currentTiem);
				cs.setString(2, addWellList.get(i));
				cs.setInt(3, StringManagerUtils.stringToInteger(deviceType));
				cs.setInt(4, 0);
				cs.setString(5, user.getUserId());
				cs.setString(6, user.getLoginIp());
				cs.setString(7, "");
				cs.executeUpdate();
			}
			for(int i=0;updateWellList!=null && i<updateWellList.size();i++){
				cs.setString(1, currentTiem);
				cs.setString(2, updateWellList.get(i));
				cs.setInt(3, StringManagerUtils.stringToInteger(deviceType));
				cs.setInt(4, 1);
				cs.setString(5, user.getUserId());
				cs.setString(6, user.getLoginIp());
				cs.setString(7, "");
				cs.executeUpdate();
			}
			for(int i=0;deleteWellList!=null && i<deleteWellList.size();i++){
				cs.setString(1, currentTiem);
				cs.setString(2, deleteWellList.get(i));
				cs.setInt(3, StringManagerUtils.stringToInteger(deviceType));
				cs.setInt(4, 2);
				cs.setString(5, user.getUserId());
				cs.setString(6, user.getLoginIp());
				cs.setString(7, "");
				cs.executeUpdate();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
		return true;
	}
	
	
	public boolean saveDeviceControlLog(String deviceId,String wellName,String deviceType,String title,String value,User user) throws SQLException{
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		try {
			cs = conn.prepareCall("{call prd_save_deviceOperationLog(?,?,?,?,?,?,?)}");
			String currentTiem=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			cs.setString(1, currentTiem);
			cs.setString(2, wellName);
			cs.setInt(3, StringManagerUtils.stringToInteger(deviceType));
			cs.setInt(4, 3);
			cs.setString(5, user.getUserId());
			cs.setString(6, user.getLoginIp());
			cs.setString(7, languageResourceMap.get("controlItem")+":"+title+","+languageResourceMap.get("writeValue")+":"+value);
			cs.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
		return true;
	}
	
	public boolean saveSystemLog(User user,int action,String remark) throws SQLException{
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call prd_save_systemLog(?,?,?,?,?)}");
			String currentTiem=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			cs.setString(1, currentTiem);
			cs.setInt(2, action);
			cs.setString(3, user.getUserId());
			cs.setString(4, user.getLoginIp());
			cs.setString(5, remark);
			cs.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
		return true;
	}
	
	
	
	public Boolean editSRPDeviceName(String oldWellName,String newWellName,String orgid) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call prd_change_srpdevicename(?,?,?)}");
			cs.setString(1,oldWellName);
			cs.setString(2, newWellName);
			cs.setString(3, orgid);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
		return true;
	}
	
	public Boolean editPCPDeviceName(String oldWellName,String newWellName,String orgid) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call prd_change_pcpdevicename(?,?,?)}");
			cs.setString(1,oldWellName);
			cs.setString(2, newWellName);
			cs.setString(3, orgid);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
		return true;
	}
	
	public Boolean editSMSDeviceName(String oldWellName,String newWellName,String orgid) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call prd_change_smsdevicename(?,?,?)}");
			cs.setString(1,oldWellName);
			cs.setString(2, newWellName);
			cs.setString(3, orgid);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
		return true;
	}

	/**
	 * 注入sessionFactory
	 */
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public int updatealarmmessage(final String sql) throws Exception {
		Session session=getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		return query.executeUpdate();

	}

	public int updateBySQL(final String sql, final List pl) throws Exception {
		Session session=getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		if (pl != null && !pl.isEmpty()) {
			for (int i = 0; i < pl.size(); i++) {
				query.setParameter(i, pl.get(i));
			}
			return query.executeUpdate();
		}
		return 0;
	}

	/**
	 * 跟新当前传入的数据信息
	 * 
	 * @author ding
	 * @param sql
	 * @return 
	 */
	public Object updateObject(final String sql) {
		Session session=getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		return query.executeUpdate();
	}

	/**
	 * <p>
	 * 更新当前对象的数据信息
	 * </p>
	 * 
	 * @author gao 2014-06-04
	 * @param clazz
	 *            传入的对象
	 */
	public <T> void updateObject(T clazz) {
		this.getHibernateTemplate().update(clazz);
	}
	
	public <T> void updateObjectFlush(T clazz) {
		this.getHibernateTemplate().update(clazz);
	}

	public int updateWellorder(final String hql) {
		Session session=getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(hql);
		return query.executeUpdate();
	}

	public void callProcedureByCallName() {
		String callName = "{Call proc_test(?,?)}";
		ResultSet rs = null;
		CallableStatement call=null;
		Connection conn=null;
		try {
			conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			call=conn.prepareCall(callName);
			call.setString(1, "");
			call.registerOutParameter(2, Types.VARCHAR);
			rs = call.executeQuery();
		}catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null){
					rs.close();
				}
				if(call!=null){
					call.close();
				}
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Boolean setAlarmColor(AlarmShowStyle alarmShowStyle) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call prd_save_alarmcolor(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, alarmShowStyle.getData().getNormal().getBackgroundColor());
			cs.setString(2, alarmShowStyle.getData().getFirstLevel().getBackgroundColor());
			cs.setString(3, alarmShowStyle.getData().getSecondLevel().getBackgroundColor());
			cs.setString(4, alarmShowStyle.getData().getThirdLevel().getBackgroundColor());
			cs.setString(5, alarmShowStyle.getData().getNormal().getColor());
			cs.setString(6, alarmShowStyle.getData().getFirstLevel().getColor());
			cs.setString(7, alarmShowStyle.getData().getSecondLevel().getColor());
			cs.setString(8, alarmShowStyle.getData().getThirdLevel().getColor());
			cs.setString(9, alarmShowStyle.getData().getNormal().getOpacity());
			cs.setString(10, alarmShowStyle.getData().getFirstLevel().getOpacity());
			cs.setString(11, alarmShowStyle.getData().getSecondLevel().getOpacity());
			cs.setString(12, alarmShowStyle.getData().getThirdLevel().getOpacity());
			
			cs.setString(13, alarmShowStyle.getComm().getGoOnline().getBackgroundColor());
			cs.setString(14, alarmShowStyle.getComm().getOnline().getBackgroundColor());
			cs.setString(15, alarmShowStyle.getComm().getOffline().getBackgroundColor());
			cs.setString(16, alarmShowStyle.getComm().getGoOnline().getColor());
			cs.setString(17, alarmShowStyle.getComm().getOnline().getColor());
			cs.setString(18, alarmShowStyle.getComm().getOffline().getColor());
			cs.setString(19, alarmShowStyle.getComm().getGoOnline().getOpacity());
			cs.setString(20, alarmShowStyle.getComm().getOnline().getOpacity());
			cs.setString(21, alarmShowStyle.getComm().getOffline().getOpacity());
			
			cs.setString(22, alarmShowStyle.getRun().getRun().getBackgroundColor());
			cs.setString(23, alarmShowStyle.getRun().getStop().getBackgroundColor());
			cs.setString(24, alarmShowStyle.getRun().getNoData().getBackgroundColor());
			
			cs.setString(25, alarmShowStyle.getRun().getRun().getColor());
			cs.setString(26, alarmShowStyle.getRun().getStop().getColor());
			cs.setString(27, alarmShowStyle.getRun().getNoData().getColor());
			
			cs.setString(28, alarmShowStyle.getRun().getRun().getOpacity());
			cs.setString(29, alarmShowStyle.getRun().getStop().getOpacity());
			cs.setString(30, alarmShowStyle.getRun().getNoData().getOpacity());
			
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
		return true;
	}
	public Boolean saveAlarmInfo(int deviceId,String deviceType,String acqTime,List<AcquisitionItemInfo> acquisitionItemInfoList) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		try {
			cs = conn.prepareCall("{call prd_save_alarminfo(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			for(int i=0;i<acquisitionItemInfoList.size();i++){
				if(acquisitionItemInfoList.get(i).getAlarmLevel()>0){
					String title=acquisitionItemInfoList.get(i).getTitle();
					cs.setInt(1, deviceId);
					cs.setString(2, deviceType);
					cs.setString(3, acqTime);
					cs.setString(4, acqTime);
					
					if(acquisitionItemInfoList.get(i).getType()==0 && StringManagerUtils.stringToInteger(acquisitionItemInfoList.get(i).getResolutionMode())==0   ){
						title=acquisitionItemInfoList.get(i).getRawTitle()+"/"+title;
					}
					
					cs.setString(5, title);
					cs.setInt(6, acquisitionItemInfoList.get(i).getAlarmType());
					if(StringManagerUtils.isNum(acquisitionItemInfoList.get(i).getRawValue())){
						cs.setString(7, acquisitionItemInfoList.get(i).getRawValue());
					}else{
						cs.setString(7, null);
					}
					cs.setString(8, acquisitionItemInfoList.get(i).getAlarmInfo());
					if(StringManagerUtils.isNum(acquisitionItemInfoList.get(i).getAlarmLimit())){
						cs.setString(9, acquisitionItemInfoList.get(i).getAlarmLimit());
					}else{
						cs.setString(9, null);
					}
					if(StringManagerUtils.isNum(acquisitionItemInfoList.get(i).getHystersis())){
						cs.setString(10, acquisitionItemInfoList.get(i).getHystersis());
					}else{
						cs.setString(10, null);
					}
					cs.setInt(11, acquisitionItemInfoList.get(i).getAlarmLevel());
					
					if(StringManagerUtils.isNum(acquisitionItemInfoList.get(i).getAlarmDelay())){
						cs.setString(12, acquisitionItemInfoList.get(i).getAlarmDelay());
					}else{
						cs.setString(12, null);
					}
					
					if(StringManagerUtils.isNum(acquisitionItemInfoList.get(i).getRetriggerTime())){
						cs.setString(13, acquisitionItemInfoList.get(i).getRetriggerTime());
					}else{
						cs.setString(13, null);
					}
					
					cs.setInt(14, acquisitionItemInfoList.get(i).getIsSendMessage());
					cs.setInt(15, acquisitionItemInfoList.get(i).getIsSendMail());
					
					cs.setString(16, acquisitionItemInfoList.get(i).getColumn());
					cs.setString(17, acquisitionItemInfoList.get(i).getBitIndex());
					
					cs.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveAcqFESDiagramAndCalculateData(DeviceInfo srpDeviceInfo,
			SRPCalculateRequestData calculateRequestData,
			SRPCalculateResponseData calculateResponseData,
			boolean fesDiagramEnabled) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		StringBuffer pumpFSDiagramStrBuff = new StringBuffer();
		StringBuffer productionDataBuff = new StringBuffer();
		StringBuffer wellboreSliceStrBuff = new StringBuffer();
		Gson gson=new Gson();
		
		Clob diagramClob_S = conn.createClob();
		diagramClob_S.setString(1, StringUtils.join(calculateRequestData.getFESDiagram().getS(), ","));
		
		Clob diagramClob_F = conn.createClob();
		diagramClob_F.setString(1, StringUtils.join(calculateRequestData.getFESDiagram().getF(), ","));
		
		Clob diagramClob_P = conn.createClob();
		diagramClob_P.setString(1, StringUtils.join(calculateRequestData.getFESDiagram().getWatt(), ","));
		
		Clob diagramClob_I = conn.createClob();
		diagramClob_I.setString(1, StringUtils.join(calculateRequestData.getFESDiagram().getI(), ","));
		
		Clob nullClob = conn.createClob();
		nullClob.setString(1, "");
		
		Clob crankAngleClob = conn.createClob();
		Clob polishRodVClob = conn.createClob();
		Clob polishRodAClob = conn.createClob();
		Clob PRClob = conn.createClob();
		Clob TFClob = conn.createClob();
		Clob loadTorqueClob = conn.createClob();
		Clob crankTorqueClob = conn.createClob();
		Clob currentBalanceTorqueClob = conn.createClob();
		Clob currentNetTorqueClob = conn.createClob();
		Clob expectedBalanceTorqueClob = conn.createClob();
		Clob expectedNetTorqueClob = conn.createClob();
		Clob wellboreSliceClob = conn.createClob();
		
		if(calculateResponseData!=null
				&&calculateResponseData.getCalculationStatus().getResultStatus()==1
//				&&calculateResponseData.getCalculationStatus().getResultCode()!=1232
				&&calculateResponseData.getFESDiagram()!=null
				&&calculateResponseData.getFESDiagram().getS()!=null
				&&calculateResponseData.getFESDiagram().getS().size()>0
				){
			int curvecount=calculateResponseData.getFESDiagram().getS().get(0).size();
			int sPointCount=calculateResponseData.getFESDiagram().getS().size();
			int fPointCount=calculateResponseData.getFESDiagram().getF().size();
			int pointcount=sPointCount;
			if(fPointCount<sPointCount){
				pointcount=fPointCount;
			}
			pumpFSDiagramStrBuff.append(curvecount+";"+pointcount+";");
			for(int i=0;i<curvecount;i++){
				for(int j=0;j<pointcount;j++){
					pumpFSDiagramStrBuff.append(calculateResponseData.getFESDiagram().getS().get(j).get(i)+",");//位移
					pumpFSDiagramStrBuff.append(calculateResponseData.getFESDiagram().getF().get(j).get(i)+",");//载荷
				}
				if(pointcount>0){
					pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
				}
				pumpFSDiagramStrBuff.append(";");
			}
			if(curvecount>0){
				pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
			}
		}
		
		Clob pumpFSDiagramClob = conn.createClob();
		pumpFSDiagramClob.setString(1, pumpFSDiagramStrBuff.toString());
		
		if(calculateResponseData!=null&&calculateResponseData.getFESDiagram()!=null&&calculateResponseData.getFESDiagram().getCrankAngle()!=null&&calculateResponseData.getFESDiagram().getCrankAngle().size()>0){
			crankAngleClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getCrankAngle(), ","));
			polishRodVClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getV(), ","));
			polishRodAClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getA(), ","));
			PRClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getPR(), ","));
			TFClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getTF(), ","));
			
			loadTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getLoadTorque(), ","));
			crankTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getCrankTorque(), ","));
			currentBalanceTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getCurrentBalanceTorque(), ","));
			currentNetTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getCurrentNetTorque(), ","));
			expectedBalanceTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getExpectedBalanceTorque(), ","));
			expectedNetTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getExpectedNetTorque(), ","));
		}else{
			crankAngleClob.setString(1, "");
			polishRodVClob.setString(1, "");
			polishRodAClob.setString(1, "");
			PRClob.setString(1, "");
			TFClob.setString(1, "");
			
			loadTorqueClob.setString(1, "");
			crankTorqueClob.setString(1, "");
			currentBalanceTorqueClob.setString(1, "");
			currentNetTorqueClob.setString(1, "");
			expectedBalanceTorqueClob.setString(1, "");
			expectedNetTorqueClob.setString(1, "");
		}
		
		if(calculateResponseData!=null&&calculateResponseData.getWellboreSlice()!=null
				&&calculateResponseData.getWellboreSlice().getMeasuringDepth()!=null
				&&calculateResponseData.getWellboreSlice().getMeasuringDepth().size()>0){
			wellboreSliceStrBuff.append(calculateResponseData.getWellboreSlice().getCNT()+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getMeasuringDepth(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getX(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getY(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getZ(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getP(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getBo(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getGLRis(), ","));
		}
		wellboreSliceClob.setString(1, wellboreSliceStrBuff.toString());
		try {
			cs = conn.prepareCall("{call prd_save_srp_diagram("
					+ "?,?,"
					+ "?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,"
					+ "?,?,"
					+ "?,?,?,?,?,?,?,"
					+ "?,"
					+ "?,"
					+ "?,?,"
					+ "?,"
					+ "?,?,?,"
					+ "?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,?,"
					+ "?"
					+ ")}");
			cs.setString(1,srpDeviceInfo.getId()+"");
			cs.setString(2,srpDeviceInfo.getAcqTime());
			
			cs.setString(3,SRPProductionDataToString(calculateRequestData));
			cs.setString(4,srpDeviceInfo.getSrpCalculateRequestData().getPumpingUnit()!=null&&srpDeviceInfo.getSrpCalculateRequestData().getPumpingUnit().getBalance()!=null?gson.toJson(srpDeviceInfo.getSrpCalculateRequestData().getPumpingUnit().getBalance()):"{}");
			cs.setString(5,"");
			
			cs.setString(6,StringManagerUtils.dateTimeValidation(calculateRequestData.getFESDiagram().getAcqTime(), "yyyy-MM-dd HH:mm:ss")?calculateRequestData.getFESDiagram().getAcqTime():"");
			cs.setInt(7, calculateRequestData.getFESDiagram().getSrc());
			
			if(calculateResponseData!=null
					&&calculateResponseData.getCalculationStatus().getResultStatus()==1
					&&calculateResponseData.getCalculationStatus().getResultCode()!=1232
					&&calculateResponseData.getFESDiagram()!=null){
				cs.setString(8,calculateResponseData.getFESDiagram().getStroke()+"");
			}else{
				cs.setString(8,calculateRequestData.getFESDiagram().getStroke()+"");
			}
			
			cs.setString(9,calculateRequestData.getFESDiagram().getSPM()+"");
			
			cs.setClob(10,diagramClob_S);
			cs.setClob(11,diagramClob_F);
			cs.setClob(12,diagramClob_P);
			cs.setClob(13,diagramClob_I);
			
			cs.setInt(14,fesDiagramEnabled?(calculateResponseData==null?0:calculateResponseData.getCalculationStatus().getResultStatus()):-1);//计算标志
			
			if(calculateResponseData!=null&&(calculateResponseData.getCalculationStatus().getResultStatus()==1||calculateResponseData.getCalculationStatus().getResultStatus()==-99)){//如果计算成功
				//最大最小载荷
				if(calculateResponseData.getFESDiagram().getFMax()!=null&&calculateResponseData.getFESDiagram().getFMax().size()>0){
					cs.setFloat(15,calculateResponseData.getFESDiagram().getFMax().get(0));
				}else{
					cs.setString(15,"");
				}
				if(calculateResponseData.getFESDiagram().getFMin()!=null&&calculateResponseData.getFESDiagram().getFMin().size()>0){
					cs.setFloat(16,calculateResponseData.getFESDiagram().getFMin().get(0));
				}else{
					cs.setString(16,"");
				}
				
				//平衡
				cs.setFloat(17,calculateResponseData.getFESDiagram().getUpStrokeIMax());
				cs.setFloat(18,calculateResponseData.getFESDiagram().getDownStrokeIMax());
				cs.setFloat(19,calculateResponseData.getFESDiagram().getUpStrokeWattMax());
				cs.setFloat(20,calculateResponseData.getFESDiagram().getDownStrokeWattMax());
				cs.setFloat(21,calculateResponseData.getFESDiagram().getIDegreeBalance());
				cs.setFloat(22,calculateResponseData.getFESDiagram().getWattDegreeBalance());
				//移动距离
				cs.setFloat(23,calculateResponseData.getFESDiagram().getDeltaRadius());
				
				//工况代码
				cs.setInt(24,calculateResponseData.getCalculationStatus().getResultCode());
				//充满系数、抽空充满系数
				cs.setFloat(25,calculateResponseData.getFESDiagram().getFullnessCoefficient());
				cs.setFloat(26,calculateResponseData.getFESDiagram().getNoLiquidFullnessCoefficient());
				//柱塞冲程、柱塞有效冲程、抽空柱塞有效冲程
				cs.setFloat(27,calculateResponseData.getFESDiagram().getPlungerStroke());
				cs.setFloat(28,calculateResponseData.getFESDiagram().getAvailablePlungerStroke());
				cs.setFloat(29,calculateResponseData.getFESDiagram().getNoLiquidAvailablePlungerStroke());
				
				//上下理论载荷线
				cs.setFloat(30,calculateResponseData.getFESDiagram().getUpperLoadLine());
				cs.setFloat(31,calculateResponseData.getFESDiagram().getUpperLoadLineOfExact());
				cs.setFloat(32,calculateResponseData.getFESDiagram().getLowerLoadLine());
				//位移最大、最小值索引
				cs.setInt(33,calculateResponseData.getFESDiagram().getSMaxIndex());
				cs.setInt(34,calculateResponseData.getFESDiagram().getSMinIndex());
				//泵功图
				cs.setClob(35,pumpFSDiagramClob);
				//产量
				cs.setFloat(36,calculateResponseData.getProduction().getTheoreticalProduction());
				cs.setFloat(37,calculateResponseData.getProduction().getLiquidVolumetricProduction());
				cs.setFloat(38,calculateResponseData.getProduction().getOilVolumetricProduction());
				cs.setFloat(39,calculateResponseData.getProduction().getWaterVolumetricProduction());
				cs.setFloat(40,calculateResponseData.getProduction().getAvailablePlungerStrokeVolumetricProduction());
				cs.setFloat(41,calculateResponseData.getProduction().getPumpClearanceLeakVolumetricProduction());
				cs.setFloat(42,calculateResponseData.getProduction().getTVLeakVolumetricProduction());
				cs.setFloat(43,calculateResponseData.getProduction().getSVLeakVolumetricProduction());
				cs.setFloat(44,calculateResponseData.getProduction().getGasInfluenceVolumetricProduction());
				cs.setFloat(45,calculateResponseData.getProduction().getLiquidWeightProduction());
				cs.setFloat(46,calculateResponseData.getProduction().getOilWeightProduction());
				cs.setFloat(47,calculateResponseData.getProduction().getWaterWeightProduction());
				cs.setFloat(48,calculateResponseData.getProduction().getAvailablePlungerStrokeWeightProduction());
				cs.setFloat(49,calculateResponseData.getProduction().getPumpClearanceLeakWeightProduction());
				cs.setFloat(50,calculateResponseData.getProduction().getTVLeakWeightProduction());
				cs.setFloat(51,calculateResponseData.getProduction().getSVLeakWeightProduction());
				cs.setFloat(52,calculateResponseData.getProduction().getGasInfluenceWeightProduction());
				//液面反演校正值、反演液面
				cs.setFloat(53,calculateResponseData.getProduction().getLevelDifferenceValue());
				cs.setFloat(54,calculateResponseData.getProduction().getCalcProducingfluidLevel());
				
				//沉没度
				cs.setFloat(55,calculateResponseData.getProduction().getSubmergence());
				
				//系统效率
				cs.setFloat(56,calculateResponseData.getFESDiagram().getAvgWatt());
				cs.setFloat(57,calculateResponseData.getSystemEfficiency().getPolishRodPower());
				cs.setFloat(58,calculateResponseData.getSystemEfficiency().getWaterPower());
				cs.setFloat(59,calculateResponseData.getSystemEfficiency().getSurfaceSystemEfficiency());
				cs.setFloat(60,calculateResponseData.getSystemEfficiency().getWellDownSystemEfficiency());
				cs.setFloat(61,calculateResponseData.getSystemEfficiency().getSystemEfficiency());
				cs.setFloat(62,calculateResponseData.getSystemEfficiency().getEnergyPer100mLift());
				cs.setFloat(63,calculateResponseData.getFESDiagram().getArea());
				//泵效
				cs.setFloat(64,calculateResponseData.getPumpEfficiency().getRodFlexLength());
				cs.setFloat(65,calculateResponseData.getPumpEfficiency().getTubingFlexLength());               
				cs.setFloat(66,calculateResponseData.getPumpEfficiency().getInertiaLength());
				cs.setFloat(67,calculateResponseData.getPumpEfficiency().getPumpEff1());
				cs.setFloat(68,calculateResponseData.getPumpEfficiency().getPumpEff2());
				cs.setFloat(69,calculateResponseData.getPumpEfficiency().getPumpEff3());
				cs.setFloat(70,calculateResponseData.getPumpEfficiency().getPumpEff4());
				cs.setFloat(71,calculateResponseData.getPumpEfficiency().getPumpEff());
				//泵入口出口参数
				cs.setFloat(72,calculateResponseData.getProduction().getPumpIntakeP());
				cs.setFloat(73,calculateResponseData.getProduction().getPumpIntakeT());
				cs.setFloat(74,calculateResponseData.getProduction().getPumpIntakeGOL());
				cs.setFloat(75,calculateResponseData.getProduction().getPumpIntakeVisl());
				cs.setFloat(76,calculateResponseData.getProduction().getPumpIntakeBo());
				cs.setFloat(77,calculateResponseData.getProduction().getPumpOutletP());
				cs.setFloat(78,calculateResponseData.getProduction().getPumpOutletT());
				cs.setFloat(79,calculateResponseData.getProduction().getPumpOutletGOL());
				cs.setFloat(80,calculateResponseData.getProduction().getPumpOutletVisl());
				cs.setFloat(81,calculateResponseData.getProduction().getPumpOutletBo());
				//杆参数
				cs.setString(82,calculateResponseData.getRodCalData());
			}else{
				cs.setString(15,"");
				cs.setString(16,"");
				
				cs.setString(17,"");
				cs.setString(18,"");
				cs.setString(19,"");
				cs.setString(20,"");
				cs.setString(21,"");
				cs.setString(22,"");
				if(calculateResponseData!=null&&calculateResponseData.getFESDiagram()!=null){
					cs.setFloat(23,calculateResponseData.getFESDiagram().getDeltaRadius());
				}else{
					cs.setString(23,"");
				}
				
				if(calculateResponseData!=null){
					cs.setInt(24,calculateResponseData.getCalculationStatus().getResultCode());
				}else{
					cs.setString(24,"");
				}
				cs.setString(25,"");
				cs.setString(26,"");
				cs.setString(27,"");
				cs.setString(28,"");
				cs.setString(29,"");
				cs.setString(30,"");
				cs.setString(31,"");
				cs.setString(32,"");
				//位移最大、最小值索引
				cs.setString(33,"");
				cs.setString(34,"");
				cs.setClob(35,nullClob);//泵功图
				//产量
				cs.setString(36,"");
				cs.setString(37,"");
				cs.setString(38,"");
				cs.setString(39,"");
				cs.setString(40,"");
				cs.setString(41,"");
				cs.setString(42,"");
				cs.setString(43,"");
				cs.setString(44,"");
				cs.setString(45,"");
	            cs.setString(46,"");
				cs.setString(47,"");
				cs.setString(48,"");
				cs.setString(49,"");
				cs.setString(50,"");
				cs.setString(51,"");
				cs.setString(52,"");

				//液面反演校正值、反演液面
				cs.setString(53,"");
				cs.setString(54,"");
				
				//沉没度
				cs.setString(55,"");
				
				//系统效率
				cs.setString(56,"");
				cs.setString(57,"");
				cs.setString(58,"");
				cs.setString(59,"");
				cs.setString(60,"");
				cs.setString(61,"");
				cs.setString(62,"");
	            cs.setString(63,"");
				//泵效
				cs.setString(64,"");
				cs.setString(65,"");
				cs.setString(66,"");
				cs.setString(67,"");
				cs.setString(68,"");
				cs.setString(69,"");
				cs.setString(70,"");
	            cs.setString(71,"");
				//泵入口出口参数
				cs.setString(72,"");
	            cs.setString(73,"");
	            cs.setString(74,"");
	            cs.setString(75,"");
	            cs.setString(76,"");
	            cs.setString(77,"");
				cs.setString(78,"");
				cs.setString(79,"");
				cs.setString(80,"");
				cs.setString(81,"");
				//杆参数
				cs.setString(82,"");
			}
			//平衡曲线
			cs.setClob(83,crankAngleClob);
			cs.setClob(84,polishRodVClob);
			cs.setClob(85,polishRodAClob);
			cs.setClob(86,PRClob);
			cs.setClob(87,TFClob);
			cs.setClob(88,loadTorqueClob);
			cs.setClob(89,crankTorqueClob);
			cs.setClob(90,currentBalanceTorqueClob);
			cs.setClob(91,currentNetTorqueClob);
			cs.setClob(92,expectedBalanceTorqueClob);
			cs.setClob(93,expectedNetTorqueClob);
			//井深切片
			cs.setClob(94,wellboreSliceClob);
			
			if(calculateResponseData!=null){
				cs.setFloat(95, calculateResponseData.getRPM());
			}else{
				cs.setFloat(95, 0);
			}
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveVacuateFESDiagramAndCalculateData(DeviceInfo srpDeviceInfo,
			SRPCalculateRequestData calculateRequestData,
			SRPCalculateResponseData calculateResponseData,
			boolean fesDiagramEnabled) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		StringBuffer pumpFSDiagramStrBuff = new StringBuffer();
		StringBuffer productionDataBuff = new StringBuffer();
		StringBuffer wellboreSliceStrBuff = new StringBuffer();
		Gson gson=new Gson();
		
		Clob diagramClob_S = conn.createClob();
		diagramClob_S.setString(1, StringUtils.join(calculateRequestData.getFESDiagram().getS(), ","));
		
		Clob diagramClob_F = conn.createClob();
		diagramClob_F.setString(1, StringUtils.join(calculateRequestData.getFESDiagram().getF(), ","));
		
		Clob diagramClob_P = conn.createClob();
		diagramClob_P.setString(1, StringUtils.join(calculateRequestData.getFESDiagram().getWatt(), ","));
		
		Clob diagramClob_I = conn.createClob();
		diagramClob_I.setString(1, StringUtils.join(calculateRequestData.getFESDiagram().getI(), ","));
		
		Clob nullClob = conn.createClob();
		nullClob.setString(1, "");
		
		Clob crankAngleClob = conn.createClob();
		Clob polishRodVClob = conn.createClob();
		Clob polishRodAClob = conn.createClob();
		Clob PRClob = conn.createClob();
		Clob TFClob = conn.createClob();
		Clob loadTorqueClob = conn.createClob();
		Clob crankTorqueClob = conn.createClob();
		Clob currentBalanceTorqueClob = conn.createClob();
		Clob currentNetTorqueClob = conn.createClob();
		Clob expectedBalanceTorqueClob = conn.createClob();
		Clob expectedNetTorqueClob = conn.createClob();
		Clob wellboreSliceClob = conn.createClob();
		
		if(calculateResponseData!=null
				&&calculateResponseData.getCalculationStatus().getResultStatus()==1
//				&&calculateResponseData.getCalculationStatus().getResultCode()!=1232
				&&calculateResponseData.getFESDiagram()!=null
				&&calculateResponseData.getFESDiagram().getS()!=null
				&&calculateResponseData.getFESDiagram().getS().size()>0
				){
			int curvecount=calculateResponseData.getFESDiagram().getS().get(0).size();
			int sPointCount=calculateResponseData.getFESDiagram().getS().size();
			int fPointCount=calculateResponseData.getFESDiagram().getF().size();
			int pointcount=sPointCount;
			if(fPointCount<sPointCount){
				pointcount=fPointCount;
			}
			pumpFSDiagramStrBuff.append(curvecount+";"+pointcount+";");
			for(int i=0;i<curvecount;i++){
				for(int j=0;j<pointcount;j++){
					pumpFSDiagramStrBuff.append(calculateResponseData.getFESDiagram().getS().get(j).get(i)+",");//位移
					pumpFSDiagramStrBuff.append(calculateResponseData.getFESDiagram().getF().get(j).get(i)+",");//载荷
				}
				if(pointcount>0){
					pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
				}
				pumpFSDiagramStrBuff.append(";");
			}
			if(curvecount>0){
				pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
			}
		}
		
		Clob pumpFSDiagramClob = conn.createClob();
		pumpFSDiagramClob.setString(1, pumpFSDiagramStrBuff.toString());
		
		if(calculateResponseData!=null&&calculateResponseData.getFESDiagram()!=null&&calculateResponseData.getFESDiagram().getCrankAngle()!=null&&calculateResponseData.getFESDiagram().getCrankAngle().size()>0){
			crankAngleClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getCrankAngle(), ","));
			polishRodVClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getV(), ","));
			polishRodAClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getA(), ","));
			PRClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getPR(), ","));
			TFClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getTF(), ","));
			
			loadTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getLoadTorque(), ","));
			crankTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getCrankTorque(), ","));
			currentBalanceTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getCurrentBalanceTorque(), ","));
			currentNetTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getCurrentNetTorque(), ","));
			expectedBalanceTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getExpectedBalanceTorque(), ","));
			expectedNetTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getExpectedNetTorque(), ","));
		}else{
			crankAngleClob.setString(1, "");
			polishRodVClob.setString(1, "");
			polishRodAClob.setString(1, "");
			PRClob.setString(1, "");
			TFClob.setString(1, "");
			
			loadTorqueClob.setString(1, "");
			crankTorqueClob.setString(1, "");
			currentBalanceTorqueClob.setString(1, "");
			currentNetTorqueClob.setString(1, "");
			expectedBalanceTorqueClob.setString(1, "");
			expectedNetTorqueClob.setString(1, "");
		}
		
		if(calculateResponseData!=null&&calculateResponseData.getWellboreSlice()!=null
				&&calculateResponseData.getWellboreSlice().getMeasuringDepth()!=null
				&&calculateResponseData.getWellboreSlice().getMeasuringDepth().size()>0){
			wellboreSliceStrBuff.append(calculateResponseData.getWellboreSlice().getCNT()+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getMeasuringDepth(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getX(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getY(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getZ(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getP(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getBo(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getGLRis(), ","));
		}
		wellboreSliceClob.setString(1, wellboreSliceStrBuff.toString());
		try {
			cs = conn.prepareCall("{call prd_save_srp_diagram_vacuate("
					+ "?,?,"
					+ "?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,"
					+ "?,?,"
					+ "?,?,?,?,?,?,?,"
					+ "?,"
					+ "?,"
					+ "?,?,"
					+ "?,"
					+ "?,?,?,"
					+ "?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,?,"
					+ "?"
					+ ")}");
			cs.setString(1,srpDeviceInfo.getId()+"");
			cs.setString(2,srpDeviceInfo.getAcqTime());
			
			cs.setString(3,SRPProductionDataToString(calculateRequestData));
			cs.setString(4,srpDeviceInfo.getSrpCalculateRequestData().getPumpingUnit()!=null&&srpDeviceInfo.getSrpCalculateRequestData().getPumpingUnit().getBalance()!=null?gson.toJson(srpDeviceInfo.getSrpCalculateRequestData().getPumpingUnit().getBalance()):"{}");
			cs.setString(5,"");
			
			cs.setString(6,StringManagerUtils.dateTimeValidation(calculateRequestData.getFESDiagram().getAcqTime(), "yyyy-MM-dd HH:mm:ss")?calculateRequestData.getFESDiagram().getAcqTime():"");
			cs.setInt(7, calculateRequestData.getFESDiagram().getSrc());
			
			if(calculateResponseData!=null
					&&calculateResponseData.getCalculationStatus().getResultStatus()==1
					&&calculateResponseData.getCalculationStatus().getResultCode()!=1232
					&&calculateResponseData.getFESDiagram()!=null){
				cs.setString(8,calculateResponseData.getFESDiagram().getStroke()+"");
			}else{
				cs.setString(8,calculateRequestData.getFESDiagram().getStroke()+"");
			}
			
			cs.setString(9,calculateRequestData.getFESDiagram().getSPM()+"");
			
			cs.setClob(10,diagramClob_S);
			cs.setClob(11,diagramClob_F);
			cs.setClob(12,diagramClob_P);
			cs.setClob(13,diagramClob_I);
			
			cs.setInt(14,fesDiagramEnabled?(calculateResponseData==null?0:calculateResponseData.getCalculationStatus().getResultStatus()):-1);//计算标志
			
			if(calculateResponseData!=null&&(calculateResponseData.getCalculationStatus().getResultStatus()==1||calculateResponseData.getCalculationStatus().getResultStatus()==-99)){//如果计算成功
				//最大最小载荷
				if(calculateResponseData.getFESDiagram().getFMax()!=null&&calculateResponseData.getFESDiagram().getFMax().size()>0){
					cs.setFloat(15,calculateResponseData.getFESDiagram().getFMax().get(0));
				}else{
					cs.setString(15,"");
				}
				if(calculateResponseData.getFESDiagram().getFMin()!=null&&calculateResponseData.getFESDiagram().getFMin().size()>0){
					cs.setFloat(16,calculateResponseData.getFESDiagram().getFMin().get(0));
				}else{
					cs.setString(16,"");
				}
				
				//平衡
				cs.setFloat(17,calculateResponseData.getFESDiagram().getUpStrokeIMax());
				cs.setFloat(18,calculateResponseData.getFESDiagram().getDownStrokeIMax());
				cs.setFloat(19,calculateResponseData.getFESDiagram().getUpStrokeWattMax());
				cs.setFloat(20,calculateResponseData.getFESDiagram().getDownStrokeWattMax());
				cs.setFloat(21,calculateResponseData.getFESDiagram().getIDegreeBalance());
				cs.setFloat(22,calculateResponseData.getFESDiagram().getWattDegreeBalance());
				//移动距离
				cs.setFloat(23,calculateResponseData.getFESDiagram().getDeltaRadius());
				
				//工况代码
				cs.setInt(24,calculateResponseData.getCalculationStatus().getResultCode());
				//充满系数、抽空充满系数
				cs.setFloat(25,calculateResponseData.getFESDiagram().getFullnessCoefficient());
				cs.setFloat(26,calculateResponseData.getFESDiagram().getNoLiquidFullnessCoefficient());
				//柱塞冲程、柱塞有效冲程、抽空柱塞有效冲程
				cs.setFloat(27,calculateResponseData.getFESDiagram().getPlungerStroke());
				cs.setFloat(28,calculateResponseData.getFESDiagram().getAvailablePlungerStroke());
				cs.setFloat(29,calculateResponseData.getFESDiagram().getNoLiquidAvailablePlungerStroke());
				
				//上下理论载荷线
				cs.setFloat(30,calculateResponseData.getFESDiagram().getUpperLoadLine());
				cs.setFloat(31,calculateResponseData.getFESDiagram().getUpperLoadLineOfExact());
				cs.setFloat(32,calculateResponseData.getFESDiagram().getLowerLoadLine());
				//位移最大、最小值索引
				cs.setInt(33,calculateResponseData.getFESDiagram().getSMaxIndex());
				cs.setInt(34,calculateResponseData.getFESDiagram().getSMinIndex());
				//泵功图
				cs.setClob(35,pumpFSDiagramClob);
				//产量
				cs.setFloat(36,calculateResponseData.getProduction().getTheoreticalProduction());
				cs.setFloat(37,calculateResponseData.getProduction().getLiquidVolumetricProduction());
				cs.setFloat(38,calculateResponseData.getProduction().getOilVolumetricProduction());
				cs.setFloat(39,calculateResponseData.getProduction().getWaterVolumetricProduction());
				cs.setFloat(40,calculateResponseData.getProduction().getAvailablePlungerStrokeVolumetricProduction());
				cs.setFloat(41,calculateResponseData.getProduction().getPumpClearanceLeakVolumetricProduction());
				cs.setFloat(42,calculateResponseData.getProduction().getTVLeakVolumetricProduction());
				cs.setFloat(43,calculateResponseData.getProduction().getSVLeakVolumetricProduction());
				cs.setFloat(44,calculateResponseData.getProduction().getGasInfluenceVolumetricProduction());
				cs.setFloat(45,calculateResponseData.getProduction().getLiquidWeightProduction());
				cs.setFloat(46,calculateResponseData.getProduction().getOilWeightProduction());
				cs.setFloat(47,calculateResponseData.getProduction().getWaterWeightProduction());
				cs.setFloat(48,calculateResponseData.getProduction().getAvailablePlungerStrokeWeightProduction());
				cs.setFloat(49,calculateResponseData.getProduction().getPumpClearanceLeakWeightProduction());
				cs.setFloat(50,calculateResponseData.getProduction().getTVLeakWeightProduction());
				cs.setFloat(51,calculateResponseData.getProduction().getSVLeakWeightProduction());
				cs.setFloat(52,calculateResponseData.getProduction().getGasInfluenceWeightProduction());
				//液面反演校正值、反演液面
				cs.setFloat(53,calculateResponseData.getProduction().getLevelDifferenceValue());
				cs.setFloat(54,calculateResponseData.getProduction().getCalcProducingfluidLevel());
				
				//沉没度
				cs.setFloat(55,calculateResponseData.getProduction().getSubmergence());
				
				//系统效率
				cs.setFloat(56,calculateResponseData.getFESDiagram().getAvgWatt());
				cs.setFloat(57,calculateResponseData.getSystemEfficiency().getPolishRodPower());
				cs.setFloat(58,calculateResponseData.getSystemEfficiency().getWaterPower());
				cs.setFloat(59,calculateResponseData.getSystemEfficiency().getSurfaceSystemEfficiency());
				cs.setFloat(60,calculateResponseData.getSystemEfficiency().getWellDownSystemEfficiency());
				cs.setFloat(61,calculateResponseData.getSystemEfficiency().getSystemEfficiency());
				cs.setFloat(62,calculateResponseData.getSystemEfficiency().getEnergyPer100mLift());
				cs.setFloat(63,calculateResponseData.getFESDiagram().getArea());
				//泵效
				cs.setFloat(64,calculateResponseData.getPumpEfficiency().getRodFlexLength());
				cs.setFloat(65,calculateResponseData.getPumpEfficiency().getTubingFlexLength());               
				cs.setFloat(66,calculateResponseData.getPumpEfficiency().getInertiaLength());
				cs.setFloat(67,calculateResponseData.getPumpEfficiency().getPumpEff1());
				cs.setFloat(68,calculateResponseData.getPumpEfficiency().getPumpEff2());
				cs.setFloat(69,calculateResponseData.getPumpEfficiency().getPumpEff3());
				cs.setFloat(70,calculateResponseData.getPumpEfficiency().getPumpEff4());
				cs.setFloat(71,calculateResponseData.getPumpEfficiency().getPumpEff());
				//泵入口出口参数
				cs.setFloat(72,calculateResponseData.getProduction().getPumpIntakeP());
				cs.setFloat(73,calculateResponseData.getProduction().getPumpIntakeT());
				cs.setFloat(74,calculateResponseData.getProduction().getPumpIntakeGOL());
				cs.setFloat(75,calculateResponseData.getProduction().getPumpIntakeVisl());
				cs.setFloat(76,calculateResponseData.getProduction().getPumpIntakeBo());
				cs.setFloat(77,calculateResponseData.getProduction().getPumpOutletP());
				cs.setFloat(78,calculateResponseData.getProduction().getPumpOutletT());
				cs.setFloat(79,calculateResponseData.getProduction().getPumpOutletGOL());
				cs.setFloat(80,calculateResponseData.getProduction().getPumpOutletVisl());
				cs.setFloat(81,calculateResponseData.getProduction().getPumpOutletBo());
				//杆参数
				cs.setString(82,calculateResponseData.getRodCalData());
			}else{
				cs.setString(15,"");
				cs.setString(16,"");
				
				cs.setString(17,"");
				cs.setString(18,"");
				cs.setString(19,"");
				cs.setString(20,"");
				cs.setString(21,"");
				cs.setString(22,"");
				if(calculateResponseData!=null&&calculateResponseData.getFESDiagram()!=null){
					cs.setFloat(23,calculateResponseData.getFESDiagram().getDeltaRadius());
				}else{
					cs.setString(23,"");
				}
				
				if(calculateResponseData!=null){
					cs.setInt(24,calculateResponseData.getCalculationStatus().getResultCode());
				}else{
					cs.setString(24,"");
				}
				cs.setString(25,"");
				cs.setString(26,"");
				cs.setString(27,"");
				cs.setString(28,"");
				cs.setString(29,"");
				cs.setString(30,"");
				cs.setString(31,"");
				cs.setString(32,"");
				//位移最大、最小值索引
				cs.setString(33,"");
				cs.setString(34,"");
				cs.setClob(35,nullClob);//泵功图
				//产量
				cs.setString(36,"");
				cs.setString(37,"");
				cs.setString(38,"");
				cs.setString(39,"");
				cs.setString(40,"");
				cs.setString(41,"");
				cs.setString(42,"");
				cs.setString(43,"");
				cs.setString(44,"");
				cs.setString(45,"");
	            cs.setString(46,"");
				cs.setString(47,"");
				cs.setString(48,"");
				cs.setString(49,"");
				cs.setString(50,"");
				cs.setString(51,"");
				cs.setString(52,"");

				//液面反演校正值、反演液面
				cs.setString(53,"");
				cs.setString(54,"");
				
				//沉没度
				cs.setString(55,"");
				
				//系统效率
				cs.setString(56,"");
				cs.setString(57,"");
				cs.setString(58,"");
				cs.setString(59,"");
				cs.setString(60,"");
				cs.setString(61,"");
				cs.setString(62,"");
	            cs.setString(63,"");
				//泵效
				cs.setString(64,"");
				cs.setString(65,"");
				cs.setString(66,"");
				cs.setString(67,"");
				cs.setString(68,"");
				cs.setString(69,"");
				cs.setString(70,"");
	            cs.setString(71,"");
				//泵入口出口参数
				cs.setString(72,"");
	            cs.setString(73,"");
	            cs.setString(74,"");
	            cs.setString(75,"");
	            cs.setString(76,"");
	            cs.setString(77,"");
				cs.setString(78,"");
				cs.setString(79,"");
				cs.setString(80,"");
				cs.setString(81,"");
				//杆参数
				cs.setString(82,"");
			}
			//平衡曲线
			cs.setClob(83,crankAngleClob);
			cs.setClob(84,polishRodVClob);
			cs.setClob(85,polishRodAClob);
			cs.setClob(86,PRClob);
			cs.setClob(87,TFClob);
			cs.setClob(88,loadTorqueClob);
			cs.setClob(89,crankTorqueClob);
			cs.setClob(90,currentBalanceTorqueClob);
			cs.setClob(91,currentNetTorqueClob);
			cs.setClob(92,expectedBalanceTorqueClob);
			cs.setClob(93,expectedNetTorqueClob);
			//井深切片
			cs.setClob(94,wellboreSliceClob);
			
			if(calculateResponseData!=null){
				cs.setFloat(95, calculateResponseData.getRPM());
			}else{
				cs.setFloat(95, 0);
			}
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveFESDiagramCalculateResult(int recordId,SRPCalculateResponseData calculateResponseData) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		StringBuffer pumpFSDiagramStrBuff = new StringBuffer();
		StringBuffer wellboreSliceStrBuff = new StringBuffer();
		Gson gson=new Gson();
		
		Clob nullClob = conn.createClob();
		nullClob.setString(1, "");
		
		Clob crankAngleClob = conn.createClob();
		Clob polishRodVClob = conn.createClob();
		Clob polishRodAClob = conn.createClob();
		Clob PRClob = conn.createClob();
		Clob TFClob = conn.createClob();
		Clob loadTorqueClob = conn.createClob();
		Clob crankTorqueClob = conn.createClob();
		Clob currentBalanceTorqueClob = conn.createClob();
		Clob currentNetTorqueClob = conn.createClob();
		Clob expectedBalanceTorqueClob = conn.createClob();
		Clob expectedNetTorqueClob = conn.createClob();
		Clob wellboreSliceClob = conn.createClob();
		
		if(calculateResponseData!=null
				&&calculateResponseData.getCalculationStatus().getResultStatus()==1
				&&calculateResponseData.getCalculationStatus().getResultCode()!=1232
				&&calculateResponseData.getFESDiagram()!=null){
			int curvecount=calculateResponseData.getFESDiagram().getS().get(0).size();
			int sPointCount=calculateResponseData.getFESDiagram().getS().size();
			int fPointCount=calculateResponseData.getFESDiagram().getS().size();
			int pointcount=sPointCount;
			if(fPointCount<sPointCount){
				pointcount=fPointCount;
			}
			pumpFSDiagramStrBuff.append(curvecount+";"+pointcount+";");
			for(int i=0;i<curvecount;i++){
				for(int j=0;j<pointcount;j++){
					pumpFSDiagramStrBuff.append(calculateResponseData.getFESDiagram().getS().get(j).get(i)+",");//位移
					pumpFSDiagramStrBuff.append(calculateResponseData.getFESDiagram().getF().get(j).get(i)+",");//载荷
				}
				if(pointcount>0){
					pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
				}
				pumpFSDiagramStrBuff.append(";");
			}
			if(curvecount>0){
				pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
			}
		}
		
		Clob pumpFSDiagramClob = conn.createClob();
		pumpFSDiagramClob.setString(1, pumpFSDiagramStrBuff.toString());
		
		if(calculateResponseData!=null&&calculateResponseData.getFESDiagram()!=null&&calculateResponseData.getFESDiagram().getCrankAngle()!=null&&calculateResponseData.getFESDiagram().getCrankAngle().size()>0){
			crankAngleClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getCrankAngle(), ","));
			polishRodVClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getV(), ","));
			polishRodAClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getA(), ","));
			PRClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getPR(), ","));
			TFClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getTF(), ","));
			
			loadTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getLoadTorque(), ","));
			crankTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getCrankTorque(), ","));
			currentBalanceTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getCurrentBalanceTorque(), ","));
			currentNetTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getCurrentNetTorque(), ","));
			expectedBalanceTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getExpectedBalanceTorque(), ","));
			expectedNetTorqueClob.setString(1, StringUtils.join(calculateResponseData.getFESDiagram().getExpectedNetTorque(), ","));
		}else{
			crankAngleClob.setString(1, "");
			polishRodVClob.setString(1, "");
			polishRodAClob.setString(1, "");
			PRClob.setString(1, "");
			TFClob.setString(1, "");
			
			loadTorqueClob.setString(1, "");
			crankTorqueClob.setString(1, "");
			currentBalanceTorqueClob.setString(1, "");
			currentNetTorqueClob.setString(1, "");
			expectedBalanceTorqueClob.setString(1, "");
			expectedNetTorqueClob.setString(1, "");
		}
		
		if(calculateResponseData!=null&&calculateResponseData.getWellboreSlice()!=null){
			wellboreSliceStrBuff.append(calculateResponseData.getWellboreSlice().getCNT()+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getMeasuringDepth(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getX(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getY(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getZ(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getP(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getBo(), ",")+";");
			wellboreSliceStrBuff.append(StringUtils.join(calculateResponseData.getWellboreSlice().getGLRis(), ","));
		}
		wellboreSliceClob.setString(1, wellboreSliceStrBuff.toString());
		try {
			cs = conn.prepareCall("{call prd_save_srp_diagramcaldata("
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?"
					+ ")}");
			cs.setInt(1,recordId);
			cs.setInt(2,calculateResponseData==null?2:calculateResponseData.getCalculationStatus().getResultStatus());//计算标志
			
			if(calculateResponseData!=null&&(calculateResponseData.getCalculationStatus().getResultStatus()==1||calculateResponseData.getCalculationStatus().getResultStatus()==-99)){//如果计算成功
				//最大最小载荷
				if(calculateResponseData.getFESDiagram().getFMax()!=null&&calculateResponseData.getFESDiagram().getFMax().size()>0){
					cs.setFloat(3,calculateResponseData.getFESDiagram().getFMax().get(0));
				}else{
					cs.setString(3,"");
				}
				if(calculateResponseData.getFESDiagram().getFMin()!=null&&calculateResponseData.getFESDiagram().getFMin().size()>0){
					cs.setFloat(4,calculateResponseData.getFESDiagram().getFMin().get(0));
				}else{
					cs.setString(4,"");
				}
				
				//平衡
				cs.setFloat(5,calculateResponseData.getFESDiagram().getUpStrokeIMax());
				cs.setFloat(6,calculateResponseData.getFESDiagram().getDownStrokeIMax());
				cs.setFloat(7,calculateResponseData.getFESDiagram().getUpStrokeWattMax());
				cs.setFloat(8,calculateResponseData.getFESDiagram().getDownStrokeWattMax());
				cs.setFloat(9,calculateResponseData.getFESDiagram().getIDegreeBalance());
				cs.setFloat(10,calculateResponseData.getFESDiagram().getWattDegreeBalance());
				//移动距离
				cs.setFloat(11,calculateResponseData.getFESDiagram().getDeltaRadius());
				
				//工况代码
				cs.setInt(12,calculateResponseData.getCalculationStatus().getResultCode());
				//充满系数、抽空充满系数
				cs.setFloat(13,calculateResponseData.getFESDiagram().getFullnessCoefficient());
				cs.setFloat(14,calculateResponseData.getFESDiagram().getNoLiquidFullnessCoefficient());
				//柱塞冲程、柱塞有效冲程、抽空柱塞有效冲程
				cs.setFloat(15,calculateResponseData.getFESDiagram().getPlungerStroke());
				cs.setFloat(16,calculateResponseData.getFESDiagram().getAvailablePlungerStroke());
				cs.setFloat(17,calculateResponseData.getFESDiagram().getNoLiquidAvailablePlungerStroke());
				
				//上下理论载荷线
				cs.setFloat(18,calculateResponseData.getFESDiagram().getUpperLoadLine());
				cs.setFloat(19,calculateResponseData.getFESDiagram().getUpperLoadLineOfExact());
				cs.setFloat(20,calculateResponseData.getFESDiagram().getLowerLoadLine());
				//位移最大、最小值索引
				cs.setInt(21,calculateResponseData.getFESDiagram().getSMaxIndex());
				cs.setInt(22,calculateResponseData.getFESDiagram().getSMinIndex());
				//泵功图
				cs.setClob(23,pumpFSDiagramClob);
				//产量
				cs.setFloat(24,calculateResponseData.getProduction().getTheoreticalProduction());
				cs.setFloat(25,calculateResponseData.getProduction().getLiquidVolumetricProduction());
				cs.setFloat(26,calculateResponseData.getProduction().getOilVolumetricProduction());
				cs.setFloat(27,calculateResponseData.getProduction().getWaterVolumetricProduction());
				cs.setFloat(28,calculateResponseData.getProduction().getAvailablePlungerStrokeVolumetricProduction());
				cs.setFloat(29,calculateResponseData.getProduction().getPumpClearanceLeakVolumetricProduction());
				cs.setFloat(30,calculateResponseData.getProduction().getTVLeakVolumetricProduction());
				cs.setFloat(31,calculateResponseData.getProduction().getSVLeakVolumetricProduction());
				cs.setFloat(32,calculateResponseData.getProduction().getGasInfluenceVolumetricProduction());
				cs.setFloat(33,calculateResponseData.getProduction().getLiquidWeightProduction());
				cs.setFloat(34,calculateResponseData.getProduction().getOilWeightProduction());
				cs.setFloat(35,calculateResponseData.getProduction().getWaterWeightProduction());
				cs.setFloat(36,calculateResponseData.getProduction().getAvailablePlungerStrokeWeightProduction());
				cs.setFloat(37,calculateResponseData.getProduction().getPumpClearanceLeakWeightProduction());
				cs.setFloat(38,calculateResponseData.getProduction().getTVLeakWeightProduction());
				cs.setFloat(39,calculateResponseData.getProduction().getSVLeakWeightProduction());
				cs.setFloat(40,calculateResponseData.getProduction().getGasInfluenceWeightProduction());
				//液面反演校正值、反演液面
				cs.setFloat(41,calculateResponseData.getProduction().getLevelDifferenceValue());
				cs.setFloat(42,calculateResponseData.getProduction().getCalcProducingfluidLevel());
				//沉没度
				cs.setFloat(43,calculateResponseData.getProduction().getSubmergence());
				//系统效率
				cs.setFloat(44,calculateResponseData.getFESDiagram().getAvgWatt());
				cs.setFloat(45,calculateResponseData.getSystemEfficiency().getPolishRodPower());
				cs.setFloat(46,calculateResponseData.getSystemEfficiency().getWaterPower());
				cs.setFloat(47,calculateResponseData.getSystemEfficiency().getSurfaceSystemEfficiency());
				cs.setFloat(48,calculateResponseData.getSystemEfficiency().getWellDownSystemEfficiency());
				cs.setFloat(49,calculateResponseData.getSystemEfficiency().getSystemEfficiency());
				cs.setFloat(50,calculateResponseData.getSystemEfficiency().getEnergyPer100mLift());
				cs.setFloat(51,calculateResponseData.getFESDiagram().getArea());
				//泵效
				cs.setFloat(52,calculateResponseData.getPumpEfficiency().getRodFlexLength());
				cs.setFloat(53,calculateResponseData.getPumpEfficiency().getTubingFlexLength());
				cs.setFloat(54,calculateResponseData.getPumpEfficiency().getInertiaLength());
				cs.setFloat(55,calculateResponseData.getPumpEfficiency().getPumpEff1());
				cs.setFloat(56,calculateResponseData.getPumpEfficiency().getPumpEff2());
				cs.setFloat(57,calculateResponseData.getPumpEfficiency().getPumpEff3());
				cs.setFloat(58,calculateResponseData.getPumpEfficiency().getPumpEff4());
				cs.setFloat(59,calculateResponseData.getPumpEfficiency().getPumpEff());
				//泵入口出口参数
				cs.setFloat(60,calculateResponseData.getProduction().getPumpIntakeP());
				cs.setFloat(61,calculateResponseData.getProduction().getPumpIntakeT());
				cs.setFloat(62,calculateResponseData.getProduction().getPumpIntakeGOL());
				cs.setFloat(63,calculateResponseData.getProduction().getPumpIntakeVisl());
				cs.setFloat(64,calculateResponseData.getProduction().getPumpIntakeBo());
				cs.setFloat(65,calculateResponseData.getProduction().getPumpOutletP());
				cs.setFloat(66,calculateResponseData.getProduction().getPumpOutletT());
				cs.setFloat(67,calculateResponseData.getProduction().getPumpOutletGOL());
				cs.setFloat(68,calculateResponseData.getProduction().getPumpOutletVisl());
				cs.setFloat(69,calculateResponseData.getProduction().getPumpOutletBo());
				//杆参数
				cs.setString(70,calculateResponseData.getRodCalData());
			}else{
				cs.setString(3,"");
				cs.setString(4,"");
				
				cs.setString(5,"");
				cs.setString(6,"");
				cs.setString(7,"");
				cs.setString(8,"");
				cs.setString(9,"");
				cs.setString(10,"");
				if(calculateResponseData!=null&&calculateResponseData.getFESDiagram()!=null){
					cs.setFloat(11,calculateResponseData.getFESDiagram().getDeltaRadius());
				}else{
					cs.setString(11,"");
				}
				
				if(calculateResponseData!=null){
					cs.setInt(12,calculateResponseData.getCalculationStatus().getResultCode());
				}else{
					cs.setString(12,"");
				}
				cs.setString(13,"");
				cs.setString(14,"");
				cs.setString(15,"");
				cs.setString(16,"");
				cs.setString(17,"");
				cs.setString(18,"");
				cs.setString(19,"");
				cs.setString(20,"");
				//位移最大、最小值索引
				cs.setString(21,"");
				cs.setString(22,"");
				cs.setClob(23,nullClob);//泵功图
				//产量
				cs.setString(24,"");
				cs.setString(25,"");
				cs.setString(26,"");
				cs.setString(27,"");
				cs.setString(28,"");
				cs.setString(29,"");
				cs.setString(30,"");
				cs.setString(31,"");
				cs.setString(32,"");
				cs.setString(33,"");
				cs.setString(34,"");
	            cs.setString(35,"");
				cs.setString(36,"");
				cs.setString(37,"");
				cs.setString(38,"");
				cs.setString(39,"");
				cs.setString(40,"");

				cs.setString(41,"");
				cs.setString(42,"");
				cs.setString(43,"");
				//系统效率
				cs.setString(44,"");
				cs.setString(45,"");
				cs.setString(46,"");
				cs.setString(47,"");
				cs.setString(48,"");
				cs.setString(49,"");
				cs.setString(50,"");
				cs.setString(51,"");
				//泵效
				cs.setString(52,"");
				cs.setString(53,"");
				cs.setString(54,"");
				cs.setString(55,"");
				cs.setString(56,"");
				cs.setString(57,"");
				cs.setString(58,"");
				cs.setString(59,"");
				//泵入口出口参数
				cs.setString(60,"");
	            cs.setString(61,"");
	            cs.setString(62,"");
	            cs.setString(63,"");
	            cs.setString(64,"");
	            cs.setString(65,"");
				cs.setString(66,"");
				cs.setString(67,"");
				cs.setString(68,"");
				cs.setString(69,"");
				//杆参数
				cs.setString(70,"");
			}
			//平衡曲线
			cs.setClob(71,crankAngleClob);
			cs.setClob(72,polishRodVClob);
			cs.setClob(73,polishRodAClob);
			cs.setClob(74,PRClob);
			cs.setClob(75,TFClob);
			cs.setClob(76,loadTorqueClob);
			cs.setClob(77,crankTorqueClob);
			cs.setClob(78,currentBalanceTorqueClob);
			cs.setClob(79,currentNetTorqueClob);
			cs.setClob(80,expectedBalanceTorqueClob);
			cs.setClob(81,expectedNetTorqueClob);
			//井深切片
			cs.setClob(82,wellboreSliceClob);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveAcqRPMAndCalculateData(DeviceInfo deviceInfo,PCPCalculateRequestData calculateRequestData,PCPCalculateResponseData calculateResponseData) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		try {
			cs = conn.prepareCall("{call prd_save_pcp_rpm("
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, deviceInfo.getId()+"");
			cs.setString(2,deviceInfo.getAcqTime());
			
			cs.setFloat(3,calculateRequestData.getRPM());
			
			cs.setString(4,PCPProductionDataToString(calculateRequestData));
			cs.setInt(5,calculateResponseData==null?0:calculateResponseData.getCalculationStatus().getResultStatus());//计算标志
			if(calculateResponseData!=null&&(calculateResponseData.getCalculationStatus().getResultStatus()==1||calculateResponseData.getCalculationStatus().getResultStatus()==-99)){//如果计算成功
				//工况代码
				cs.setInt(6,calculateResponseData.getCalculationStatus().getResultCode());
				//产量
				cs.setFloat(7,calculateResponseData.getProduction().getTheoreticalProduction());
				cs.setFloat(8,calculateResponseData.getProduction().getLiquidVolumetricProduction());
				cs.setFloat(9,calculateResponseData.getProduction().getOilVolumetricProduction());
				cs.setFloat(10,calculateResponseData.getProduction().getWaterVolumetricProduction());
				cs.setFloat(11,calculateResponseData.getProduction().getLiquidWeightProduction());
				cs.setFloat(12,calculateResponseData.getProduction().getOilWeightProduction());
				cs.setFloat(13,calculateResponseData.getProduction().getWaterWeightProduction());
				cs.setFloat(14,calculateResponseData.getProduction().getSubmergence());
				//系统效率
				cs.setFloat(15,calculateResponseData.getSystemEfficiency().getMotorInputWatt());
				cs.setFloat(16,calculateResponseData.getSystemEfficiency().getWaterPower());
				cs.setFloat(17,calculateResponseData.getSystemEfficiency().getSystemEfficiency());
				cs.setFloat(18,calculateResponseData.getSystemEfficiency().getEnergyPer100mLift());
//				//泵效
				cs.setFloat(19,calculateResponseData.getPumpEfficiency().getPumpEff1());
				cs.setFloat(20,calculateResponseData.getPumpEfficiency().getPumpEff2());
				cs.setFloat(21,calculateResponseData.getPumpEfficiency().getPumpEff());
//				//泵入口出口参数
				cs.setFloat(22,calculateResponseData.getProduction().getPumpIntakeP());
				cs.setFloat(23,calculateResponseData.getProduction().getPumpIntakeT());
				cs.setFloat(24,calculateResponseData.getProduction().getPumpIntakeGOL());
				cs.setFloat(25,calculateResponseData.getProduction().getPumpIntakeVisl());
				cs.setFloat(26,calculateResponseData.getProduction().getPumpIntakeBo());
				cs.setFloat(27,calculateResponseData.getProduction().getPumpOutletP());
				cs.setFloat(28,calculateResponseData.getProduction().getPumpOutletT());
				cs.setFloat(29,calculateResponseData.getProduction().getPumpOutletGOL());
				cs.setFloat(30,calculateResponseData.getProduction().getPumpOutletVisl());
				cs.setFloat(31,calculateResponseData.getProduction().getPumpOutletBo());
//				//杆参数
				cs.setString(32,calculateResponseData.getRodCalData());
			}else{
				cs.setString(6,"");
				cs.setString(7,"");
				cs.setString(8,"");
				cs.setString(9,"");
				
				cs.setString(10,"");
				cs.setString(11,"");
				cs.setString(12,"");
				cs.setString(13,"");
				cs.setString(14,"");
				cs.setString(15,"");
				cs.setString(16,"");
				cs.setString(17,"");
				cs.setString(18,"");
				cs.setString(19,"");
				
				cs.setString(20,"");
				cs.setString(21,"");
				cs.setString(22,"");
				cs.setString(23,"");
				cs.setString(24,"");
				cs.setString(25,"");
				cs.setString(26,"");
				cs.setString(27,"");
				cs.setString(28,"");
				cs.setString(29,"");
				cs.setString(30,"");
				cs.setString(31,"");
				cs.setString(32,"");
			}
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveVacuateRPMAndCalculateData(DeviceInfo deviceInfo,PCPCalculateRequestData calculateRequestData,PCPCalculateResponseData calculateResponseData) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		try {
			cs = conn.prepareCall("{call prd_save_pcp_rpm_vacuate("
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, deviceInfo.getId()+"");
			cs.setString(2,deviceInfo.getAcqTime());
			
			cs.setFloat(3,calculateRequestData.getRPM());
			
			cs.setString(4,PCPProductionDataToString(calculateRequestData));
			cs.setInt(5,calculateResponseData==null?0:calculateResponseData.getCalculationStatus().getResultStatus());//计算标志
			if(calculateResponseData!=null&&(calculateResponseData.getCalculationStatus().getResultStatus()==1||calculateResponseData.getCalculationStatus().getResultStatus()==-99)){//如果计算成功
				//工况代码
				cs.setInt(6,calculateResponseData.getCalculationStatus().getResultCode());
				//产量
				cs.setFloat(7,calculateResponseData.getProduction().getTheoreticalProduction());
				cs.setFloat(8,calculateResponseData.getProduction().getLiquidVolumetricProduction());
				cs.setFloat(9,calculateResponseData.getProduction().getOilVolumetricProduction());
				cs.setFloat(10,calculateResponseData.getProduction().getWaterVolumetricProduction());
				cs.setFloat(11,calculateResponseData.getProduction().getLiquidWeightProduction());
				cs.setFloat(12,calculateResponseData.getProduction().getOilWeightProduction());
				cs.setFloat(13,calculateResponseData.getProduction().getWaterWeightProduction());
				cs.setFloat(14,calculateResponseData.getProduction().getSubmergence());
				//系统效率
				cs.setFloat(15,calculateResponseData.getSystemEfficiency().getMotorInputWatt());
				cs.setFloat(16,calculateResponseData.getSystemEfficiency().getWaterPower());
				cs.setFloat(17,calculateResponseData.getSystemEfficiency().getSystemEfficiency());
				cs.setFloat(18,calculateResponseData.getSystemEfficiency().getEnergyPer100mLift());
//				//泵效
				cs.setFloat(19,calculateResponseData.getPumpEfficiency().getPumpEff1());
				cs.setFloat(20,calculateResponseData.getPumpEfficiency().getPumpEff2());
				cs.setFloat(21,calculateResponseData.getPumpEfficiency().getPumpEff());
//				//泵入口出口参数
				cs.setFloat(22,calculateResponseData.getProduction().getPumpIntakeP());
				cs.setFloat(23,calculateResponseData.getProduction().getPumpIntakeT());
				cs.setFloat(24,calculateResponseData.getProduction().getPumpIntakeGOL());
				cs.setFloat(25,calculateResponseData.getProduction().getPumpIntakeVisl());
				cs.setFloat(26,calculateResponseData.getProduction().getPumpIntakeBo());
				cs.setFloat(27,calculateResponseData.getProduction().getPumpOutletP());
				cs.setFloat(28,calculateResponseData.getProduction().getPumpOutletT());
				cs.setFloat(29,calculateResponseData.getProduction().getPumpOutletGOL());
				cs.setFloat(30,calculateResponseData.getProduction().getPumpOutletVisl());
				cs.setFloat(31,calculateResponseData.getProduction().getPumpOutletBo());
//				//杆参数
				cs.setString(32,calculateResponseData.getRodCalData());
			}else{
				cs.setString(6,"");
				cs.setString(7,"");
				cs.setString(8,"");
				cs.setString(9,"");
				
				cs.setString(10,"");
				cs.setString(11,"");
				cs.setString(12,"");
				cs.setString(13,"");
				cs.setString(14,"");
				cs.setString(15,"");
				cs.setString(16,"");
				cs.setString(17,"");
				cs.setString(18,"");
				cs.setString(19,"");
				
				cs.setString(20,"");
				cs.setString(21,"");
				cs.setString(22,"");
				cs.setString(23,"");
				cs.setString(24,"");
				cs.setString(25,"");
				cs.setString(26,"");
				cs.setString(27,"");
				cs.setString(28,"");
				cs.setString(29,"");
				cs.setString(30,"");
				cs.setString(31,"");
				cs.setString(32,"");
			}
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveRPMCalculateResult(int recordId,PCPCalculateResponseData calculateResponseData) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		try {
			cs = conn.prepareCall("{call prd_save_pcp_rpmcaldata("
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?)}");
			cs.setInt(1, recordId);
			cs.setInt(2,calculateResponseData==null?0:calculateResponseData.getCalculationStatus().getResultStatus());//计算标志
			if(calculateResponseData!=null&&(calculateResponseData.getCalculationStatus().getResultStatus()==1||calculateResponseData.getCalculationStatus().getResultStatus()==-99)){//如果计算成功
				//工况代码
				cs.setInt(3,calculateResponseData.getCalculationStatus().getResultCode());
				//产量
				cs.setFloat(4,calculateResponseData.getProduction().getTheoreticalProduction());
				cs.setFloat(5,calculateResponseData.getProduction().getLiquidVolumetricProduction());
				cs.setFloat(6,calculateResponseData.getProduction().getOilVolumetricProduction());
				cs.setFloat(7,calculateResponseData.getProduction().getWaterVolumetricProduction());
				cs.setFloat(8,calculateResponseData.getProduction().getLiquidWeightProduction());
				cs.setFloat(9,calculateResponseData.getProduction().getOilWeightProduction());
				cs.setFloat(10,calculateResponseData.getProduction().getWaterWeightProduction());
				cs.setFloat(11,calculateResponseData.getProduction().getSubmergence());
				//系统效率
				cs.setFloat(12,calculateResponseData.getSystemEfficiency().getMotorInputWatt());
				cs.setFloat(13,calculateResponseData.getSystemEfficiency().getWaterPower());
				cs.setFloat(14,calculateResponseData.getSystemEfficiency().getSystemEfficiency());
				cs.setFloat(15,calculateResponseData.getSystemEfficiency().getEnergyPer100mLift());
//				//泵效
				cs.setFloat(16,calculateResponseData.getPumpEfficiency().getPumpEff1());
				cs.setFloat(17,calculateResponseData.getPumpEfficiency().getPumpEff2());
				cs.setFloat(18,calculateResponseData.getPumpEfficiency().getPumpEff());
//				//泵入口出口参数
				cs.setFloat(19,calculateResponseData.getProduction().getPumpIntakeP());
				cs.setFloat(20,calculateResponseData.getProduction().getPumpIntakeT());
				cs.setFloat(21,calculateResponseData.getProduction().getPumpIntakeGOL());
				cs.setFloat(22,calculateResponseData.getProduction().getPumpIntakeVisl());
				cs.setFloat(23,calculateResponseData.getProduction().getPumpIntakeBo());
				cs.setFloat(24,calculateResponseData.getProduction().getPumpOutletP());
				cs.setFloat(25,calculateResponseData.getProduction().getPumpOutletT());
				cs.setFloat(26,calculateResponseData.getProduction().getPumpOutletGOL());
				cs.setFloat(27,calculateResponseData.getProduction().getPumpOutletVisl());
				cs.setFloat(28,calculateResponseData.getProduction().getPumpOutletBo());
//				//杆参数
				cs.setString(29,calculateResponseData.getRodCalData());
			}else{
				cs.setString(3,"");
				cs.setString(4,"");
				cs.setString(5,"");
				cs.setString(6,"");
				cs.setString(7,"");
				cs.setString(8,"");
				cs.setString(9,"");
				cs.setString(10,"");
				cs.setString(11,"");
				cs.setString(12,"");
				cs.setString(13,"");
				cs.setString(14,"");
				cs.setString(15,"");
				cs.setString(16,"");
				cs.setString(17,"");
				cs.setString(18,"");
				cs.setString(19,"");
				cs.setString(20,"");
				cs.setString(21,"");
				cs.setString(22,"");
				cs.setString(23,"");
				cs.setString(24,"");
				cs.setString(25,"");
				cs.setString(26,"");
				cs.setString(27,"");
				cs.setString(28,"");
				cs.setString(29,"");
			}
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveFESDiagramTotalCalculateData(DeviceInfo srpDeviceInfo,
			TotalAnalysisResponseData totalAnalysisResponseData,
			TotalAnalysisRequestData totalAnalysisRequestData,
			String date,int recordCount) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		Clob resultStrClob = conn.createClob();
		resultStrClob.setString(1, totalAnalysisResponseData.getResultString());
		
		Clob commRanceClob = conn.createClob();
		commRanceClob.setString(1, totalAnalysisResponseData.getCommRange());
		
		Clob runRanceClob = conn.createClob();
		runRanceClob.setString(1, totalAnalysisResponseData.getRunRange());
		try {
			cs = conn.prepareCall("{call prd_save_srp_diagramdaily("
					+ "?,?,"
					+ "?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,?,?,?,?,"
					+ "?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,"
					+ "?"
					+ ")}");
			cs.setInt(1,srpDeviceInfo.getId());
			cs.setInt(2,totalAnalysisResponseData.getResultStatus());
			
			cs.setInt(3,totalAnalysisResponseData.getResultCode());
			cs.setClob(4,resultStrClob);
			cs.setInt(5,totalAnalysisResponseData.getExtendedDays());
			
			cs.setFloat(6, totalAnalysisResponseData.getStroke().getValue());
			cs.setFloat(7, totalAnalysisResponseData.getSPM().getValue());
			cs.setFloat(8, totalAnalysisResponseData.getFMax().getValue());
			cs.setFloat(9, totalAnalysisResponseData.getFMin().getValue());
			cs.setFloat(10, totalAnalysisResponseData.getFullnessCoefficient().getValue());
			
			cs.setFloat(11, totalAnalysisResponseData.getTheoreticalProduction().getValue());
			
			cs.setFloat(12, totalAnalysisResponseData.getLiquidVolumetricProduction().getValue());
			cs.setFloat(13, totalAnalysisResponseData.getOilVolumetricProduction().getValue());
			cs.setFloat(14, totalAnalysisResponseData.getWaterVolumetricProduction().getValue());
			cs.setFloat(15, totalAnalysisResponseData.getVolumeWaterCut().getValue());
			
			cs.setFloat(16, totalAnalysisResponseData.getLiquidWeightProduction().getValue());
			cs.setFloat(17, totalAnalysisResponseData.getOilWeightProduction().getValue());
			cs.setFloat(18, totalAnalysisResponseData.getWaterWeightProduction().getValue());
			cs.setFloat(19, totalAnalysisResponseData.getWeightWaterCut().getValue());
			
			cs.setFloat(20, totalAnalysisResponseData.getPumpEff().getValue());
			cs.setFloat(21, totalAnalysisResponseData.getPumpEff1().getValue());
			cs.setFloat(22, totalAnalysisResponseData.getPumpEff2().getValue());
			cs.setFloat(23, totalAnalysisResponseData.getPumpEff3().getValue());
			cs.setFloat(24, totalAnalysisResponseData.getPumpEff4().getValue());
			
			cs.setFloat(25, totalAnalysisResponseData.getWellDownSystemEfficiency().getValue());
			cs.setFloat(26, totalAnalysisResponseData.getSurfaceSystemEfficiency().getValue());
			cs.setFloat(27, totalAnalysisResponseData.getSystemEfficiency().getValue());
			cs.setFloat(28, totalAnalysisResponseData.getEnergyPer100mLift().getValue());
			
			cs.setFloat(29, totalAnalysisResponseData.getIDegreeBalance().getValue());
			cs.setFloat(30, totalAnalysisResponseData.getWattDegreeBalance().getValue());
			cs.setFloat(31, totalAnalysisResponseData.getDeltaRadius().getValue());
			
			cs.setFloat(32, totalAnalysisResponseData.getPumpSettingDepth().getValue());
			cs.setFloat(33, totalAnalysisResponseData.getProducingfluidLevel().getValue());
			cs.setFloat(34, totalAnalysisResponseData.getCalcProducingfluidLevel().getValue());
			cs.setFloat(35, totalAnalysisResponseData.getLevelDifferenceValue().getValue());
			cs.setFloat(36, totalAnalysisResponseData.getSubmergence().getValue());
			cs.setFloat(37, totalAnalysisResponseData.getCasingPressure().getValue());
			cs.setFloat(38, totalAnalysisResponseData.getTubingPressure().getValue());
			
			cs.setFloat(39, totalAnalysisResponseData.getRPM().getValue());
			
			cs.setInt(40,totalAnalysisRequestData.getAcqTime().size()>0?totalAnalysisResponseData.getCommStatus():totalAnalysisRequestData.getCurrentCommStatus());
			cs.setFloat(41, totalAnalysisResponseData.getCommTime());
			cs.setFloat(42, totalAnalysisResponseData.getCommTimeEfficiency());
			cs.setClob(43,commRanceClob);
			
			cs.setInt(44,totalAnalysisRequestData.getAcqTime().size()>0?totalAnalysisResponseData.getRunStatus():totalAnalysisRequestData.getCurrentRunStatus());
			cs.setFloat(45, totalAnalysisResponseData.getRunTime());
			cs.setFloat(46, totalAnalysisResponseData.getRunTimeEfficiency());
			cs.setClob(47,runRanceClob);
			
			cs.setString(48, date);
			cs.setInt(49, recordCount);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveFESDiagramTotalCalculateData(TotalAnalysisResponseData totalAnalysisResponseData,
			TotalAnalysisRequestData totalAnalysisRequestData,
			String date,int recordCount) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		Clob resultStrClob = conn.createClob();
		resultStrClob.setString(1, totalAnalysisResponseData.getResultString());
		
		Clob commRanceClob = conn.createClob();
		commRanceClob.setString(1, totalAnalysisResponseData.getCommRange());
		
		Clob runRanceClob = conn.createClob();
		runRanceClob.setString(1, totalAnalysisResponseData.getRunRange());
		
		
		try {
			cs = conn.prepareCall("{call prd_save_srp_diagramdaily("
					+ "?,?,"
					+ "?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,?,?,?,?,"
					+ "?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,"
					+ "?"
					+ ")}");
			cs.setInt(1,StringManagerUtils.stringToInteger(totalAnalysisRequestData.getWellName()));
			cs.setInt(2,totalAnalysisResponseData.getResultStatus());
			
			cs.setInt(3,totalAnalysisResponseData.getResultCode());
			cs.setClob(4,resultStrClob);
			cs.setInt(5,totalAnalysisResponseData.getExtendedDays());
			
			cs.setFloat(6, totalAnalysisResponseData.getStroke().getValue());
			cs.setFloat(7, totalAnalysisResponseData.getSPM().getValue());
			cs.setFloat(8, totalAnalysisResponseData.getFMax().getValue());
			cs.setFloat(9, totalAnalysisResponseData.getFMin().getValue());
			cs.setFloat(10, totalAnalysisResponseData.getFullnessCoefficient().getValue());
			
			cs.setFloat(11, totalAnalysisResponseData.getTheoreticalProduction().getValue());
			
			cs.setFloat(12, totalAnalysisResponseData.getLiquidVolumetricProduction().getValue());
			cs.setFloat(13, totalAnalysisResponseData.getOilVolumetricProduction().getValue());
			cs.setFloat(14, totalAnalysisResponseData.getWaterVolumetricProduction().getValue());
			cs.setFloat(15, totalAnalysisResponseData.getVolumeWaterCut().getValue());
			
			cs.setFloat(16, totalAnalysisResponseData.getLiquidWeightProduction().getValue());
			cs.setFloat(17, totalAnalysisResponseData.getOilWeightProduction().getValue());
			cs.setFloat(18, totalAnalysisResponseData.getWaterWeightProduction().getValue());
			cs.setFloat(19, totalAnalysisResponseData.getWeightWaterCut().getValue());
			
			cs.setFloat(20, totalAnalysisResponseData.getPumpEff().getValue());
			cs.setFloat(21, totalAnalysisResponseData.getPumpEff1().getValue());
			cs.setFloat(22, totalAnalysisResponseData.getPumpEff2().getValue());
			cs.setFloat(23, totalAnalysisResponseData.getPumpEff3().getValue());
			cs.setFloat(24, totalAnalysisResponseData.getPumpEff4().getValue());
			
			cs.setFloat(25, totalAnalysisResponseData.getWellDownSystemEfficiency().getValue());
			cs.setFloat(26, totalAnalysisResponseData.getSurfaceSystemEfficiency().getValue());
			cs.setFloat(27, totalAnalysisResponseData.getSystemEfficiency().getValue());
			cs.setFloat(28, totalAnalysisResponseData.getEnergyPer100mLift().getValue());
			
			cs.setFloat(29, totalAnalysisResponseData.getIDegreeBalance().getValue());
			cs.setFloat(30, totalAnalysisResponseData.getWattDegreeBalance().getValue());
			cs.setFloat(31, totalAnalysisResponseData.getDeltaRadius().getValue());
			
			cs.setFloat(32, totalAnalysisResponseData.getPumpSettingDepth().getValue());
			cs.setFloat(33, totalAnalysisResponseData.getProducingfluidLevel().getValue());
			cs.setFloat(34, totalAnalysisResponseData.getCalcProducingfluidLevel().getValue());
			cs.setFloat(35, totalAnalysisResponseData.getLevelDifferenceValue().getValue());
			cs.setFloat(36, totalAnalysisResponseData.getSubmergence().getValue());
			cs.setFloat(37, totalAnalysisResponseData.getCasingPressure().getValue());
			cs.setFloat(38, totalAnalysisResponseData.getTubingPressure().getValue());
			
			cs.setFloat(39, totalAnalysisResponseData.getRPM().getValue());
			
			cs.setInt(40,totalAnalysisRequestData.getAcqTime().size()>0?totalAnalysisResponseData.getCommStatus():totalAnalysisRequestData.getCurrentCommStatus());
			cs.setFloat(41, totalAnalysisResponseData.getCommTime());
			cs.setFloat(42, totalAnalysisResponseData.getCommTimeEfficiency());
			cs.setClob(43,commRanceClob);
			
			cs.setInt(44,totalAnalysisRequestData.getAcqTime().size()>0?totalAnalysisResponseData.getRunStatus():totalAnalysisRequestData.getCurrentRunStatus());
			cs.setFloat(45, totalAnalysisResponseData.getRunTime());
			cs.setFloat(46, totalAnalysisResponseData.getRunTimeEfficiency());
			cs.setClob(47,runRanceClob);
			
			cs.setString(48, date);
			cs.setInt(49, recordCount);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveFSDiagramTimingTotalCalculationData(TotalAnalysisResponseData totalAnalysisResponseData,
			TotalAnalysisRequestData totalAnalysisRequestData,
			String timeStr,int recordCount) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		Clob resultStrClob = conn.createClob();
		resultStrClob.setString(1, totalAnalysisResponseData.getResultString());
		
		Clob commRanceClob = conn.createClob();
		commRanceClob.setString(1, totalAnalysisResponseData.getCommRange());
		
		Clob runRanceClob = conn.createClob();
		runRanceClob.setString(1, totalAnalysisResponseData.getRunRange());
		
		
		try {
			cs = conn.prepareCall("{call prd_save_srp_diagramtimingtotal("
					+ "?,?,"
					+ "?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,?,?,?,?,"
					+ "?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,"
					+ "?"
					+ ")}");
			cs.setInt(1,StringManagerUtils.stringToInteger(totalAnalysisRequestData.getWellName()));
			cs.setInt(2,totalAnalysisResponseData.getResultStatus());
			
			cs.setInt(3,totalAnalysisResponseData.getResultCode());
			cs.setClob(4,resultStrClob);
			cs.setInt(5,totalAnalysisResponseData.getExtendedDays());
			
			cs.setFloat(6, totalAnalysisResponseData.getStroke().getValue());
			cs.setFloat(7, totalAnalysisResponseData.getSPM().getValue());
			cs.setFloat(8, totalAnalysisResponseData.getFMax().getValue());
			cs.setFloat(9, totalAnalysisResponseData.getFMin().getValue());
			cs.setFloat(10, totalAnalysisResponseData.getFullnessCoefficient().getValue());
			
			cs.setFloat(11, totalAnalysisResponseData.getTheoreticalProduction().getValue());
			
			cs.setFloat(12, totalAnalysisResponseData.getLiquidVolumetricProduction().getValue());
			cs.setFloat(13, totalAnalysisResponseData.getOilVolumetricProduction().getValue());
			cs.setFloat(14, totalAnalysisResponseData.getWaterVolumetricProduction().getValue());
			cs.setFloat(15, totalAnalysisResponseData.getVolumeWaterCut().getValue());
			
			cs.setFloat(16, totalAnalysisResponseData.getLiquidWeightProduction().getValue());
			cs.setFloat(17, totalAnalysisResponseData.getOilWeightProduction().getValue());
			cs.setFloat(18, totalAnalysisResponseData.getWaterWeightProduction().getValue());
			cs.setFloat(19, totalAnalysisResponseData.getWeightWaterCut().getValue());
			
			cs.setFloat(20, totalAnalysisResponseData.getPumpEff().getValue());
			cs.setFloat(21, totalAnalysisResponseData.getPumpEff1().getValue());
			cs.setFloat(22, totalAnalysisResponseData.getPumpEff2().getValue());
			cs.setFloat(23, totalAnalysisResponseData.getPumpEff3().getValue());
			cs.setFloat(24, totalAnalysisResponseData.getPumpEff4().getValue());
			
			cs.setFloat(25, totalAnalysisResponseData.getWellDownSystemEfficiency().getValue());
			cs.setFloat(26, totalAnalysisResponseData.getSurfaceSystemEfficiency().getValue());
			cs.setFloat(27, totalAnalysisResponseData.getSystemEfficiency().getValue());
			cs.setFloat(28, totalAnalysisResponseData.getEnergyPer100mLift().getValue());
			
			cs.setFloat(29, totalAnalysisResponseData.getIDegreeBalance().getValue());
			cs.setFloat(30, totalAnalysisResponseData.getWattDegreeBalance().getValue());
			cs.setFloat(31, totalAnalysisResponseData.getDeltaRadius().getValue());
			
			cs.setFloat(32, totalAnalysisResponseData.getPumpSettingDepth().getValue());
			cs.setFloat(33, totalAnalysisResponseData.getProducingfluidLevel().getValue());
			cs.setFloat(34, totalAnalysisResponseData.getCalcProducingfluidLevel().getValue());
			cs.setFloat(35, totalAnalysisResponseData.getLevelDifferenceValue().getValue());
			cs.setFloat(36, totalAnalysisResponseData.getSubmergence().getValue());
			cs.setFloat(37, totalAnalysisResponseData.getCasingPressure().getValue());
			cs.setFloat(38, totalAnalysisResponseData.getTubingPressure().getValue());
			
			cs.setFloat(39, totalAnalysisResponseData.getRPM().getValue());
			
			cs.setInt(40,totalAnalysisRequestData.getAcqTime().size()>0?totalAnalysisResponseData.getCommStatus():totalAnalysisRequestData.getCurrentCommStatus());
			cs.setFloat(41, totalAnalysisResponseData.getCommTime());
			cs.setFloat(42, totalAnalysisResponseData.getCommTimeEfficiency());
			cs.setClob(43,commRanceClob);
			
			cs.setInt(44,totalAnalysisRequestData.getAcqTime().size()>0?totalAnalysisResponseData.getRunStatus():totalAnalysisRequestData.getCurrentRunStatus());
			cs.setFloat(45, totalAnalysisResponseData.getRunTime());
			cs.setFloat(46, totalAnalysisResponseData.getRunTimeEfficiency());
			cs.setClob(47,runRanceClob);
			
			cs.setString(48, timeStr);
			cs.setInt(49, recordCount);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	
	public Boolean saveFESDiagramReTotalData(String recordId,TotalAnalysisResponseData totalAnalysisResponseData,int recordCount) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		Clob resultStrClob = conn.createClob();
		resultStrClob.setString(1, totalAnalysisResponseData.getResultString());
		try {
			cs = conn.prepareCall("{call prd_save_srp_diagramdailyrecal("
					+ "?,?,"
					+ "?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,?,"
					+ "?,"
					+ "?,?,?,?,?,?"
					+ ")}");
			cs.setInt(1,StringManagerUtils.stringToInteger(recordId));
			cs.setInt(2,totalAnalysisResponseData.getResultStatus());
			
			cs.setInt(3,totalAnalysisResponseData.getResultCode());
			cs.setClob(4,resultStrClob);
			cs.setInt(5,totalAnalysisResponseData.getExtendedDays());
			
			cs.setFloat(6, totalAnalysisResponseData.getStroke().getValue());
			cs.setFloat(7, totalAnalysisResponseData.getSPM().getValue());
			cs.setFloat(8, totalAnalysisResponseData.getFMax().getValue());
			cs.setFloat(9, totalAnalysisResponseData.getFMin().getValue());
			cs.setFloat(10, totalAnalysisResponseData.getFullnessCoefficient().getValue());
			
			cs.setFloat(11, totalAnalysisResponseData.getTheoreticalProduction().getValue());
			
			cs.setFloat(12, totalAnalysisResponseData.getLiquidVolumetricProduction().getValue());
			cs.setFloat(13, totalAnalysisResponseData.getOilVolumetricProduction().getValue());
			cs.setFloat(14, totalAnalysisResponseData.getWaterVolumetricProduction().getValue());
			cs.setFloat(15, totalAnalysisResponseData.getVolumeWaterCut().getValue());
			
			cs.setFloat(16, totalAnalysisResponseData.getLiquidWeightProduction().getValue());
			cs.setFloat(17, totalAnalysisResponseData.getOilWeightProduction().getValue());
			cs.setFloat(18, totalAnalysisResponseData.getWaterWeightProduction().getValue());
			cs.setFloat(19, totalAnalysisResponseData.getWeightWaterCut().getValue());
			
			cs.setFloat(20, totalAnalysisResponseData.getPumpEff().getValue());
			cs.setFloat(21, totalAnalysisResponseData.getPumpEff1().getValue());
			cs.setFloat(22, totalAnalysisResponseData.getPumpEff2().getValue());
			cs.setFloat(23, totalAnalysisResponseData.getPumpEff3().getValue());
			cs.setFloat(24, totalAnalysisResponseData.getPumpEff4().getValue());
			
			cs.setFloat(25, totalAnalysisResponseData.getWellDownSystemEfficiency().getValue());
			cs.setFloat(26, totalAnalysisResponseData.getSurfaceSystemEfficiency().getValue());
			cs.setFloat(27, totalAnalysisResponseData.getSystemEfficiency().getValue());
			cs.setFloat(28, totalAnalysisResponseData.getEnergyPer100mLift().getValue());
			
			cs.setFloat(29, totalAnalysisResponseData.getIDegreeBalance().getValue());
			cs.setFloat(30, totalAnalysisResponseData.getWattDegreeBalance().getValue());
			cs.setFloat(31, totalAnalysisResponseData.getDeltaRadius().getValue());
			
			cs.setFloat(32, totalAnalysisResponseData.getPumpSettingDepth().getValue());
			cs.setFloat(33, totalAnalysisResponseData.getProducingfluidLevel().getValue());
			cs.setFloat(34, totalAnalysisResponseData.getCalcProducingfluidLevel().getValue());
			cs.setFloat(35, totalAnalysisResponseData.getLevelDifferenceValue().getValue());
			cs.setFloat(36, totalAnalysisResponseData.getSubmergence().getValue());
			
			cs.setFloat(37, totalAnalysisResponseData.getTubingPressure().getValue());
			cs.setFloat(38, totalAnalysisResponseData.getCasingPressure().getValue());
			
			cs.setFloat(39, totalAnalysisResponseData.getRPM().getValue());
			
			cs.setInt(40, recordCount);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveRPMTotalCalculateData(DeviceInfo pcpDeviceInfo,
			TotalAnalysisResponseData totalAnalysisResponseData,
			TotalAnalysisRequestData totalAnalysisRequestData,
			String date,int recordCount) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		Clob commRanceClob = conn.createClob();
		commRanceClob.setString(1, totalAnalysisResponseData.getCommRange());
		
		Clob runRanceClob = conn.createClob();
		runRanceClob.setString(1, totalAnalysisResponseData.getRunRange());
		try {
			cs = conn.prepareCall("{call prd_save_pcp_rpmdaily("
					+ "?,?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,"
					+ "?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?"
					+ ")}");
			cs.setInt(1,pcpDeviceInfo.getId());
			cs.setInt(2,totalAnalysisResponseData.getResultStatus());
			
			cs.setInt(3,totalAnalysisResponseData.getExtendedDays());
			
			cs.setFloat(4, totalAnalysisResponseData.getRPM().getValue());
			
			cs.setFloat(5, totalAnalysisResponseData.getTheoreticalProduction().getValue());
			
			cs.setFloat(6, totalAnalysisResponseData.getLiquidVolumetricProduction().getValue());
			cs.setFloat(7, totalAnalysisResponseData.getOilVolumetricProduction().getValue());
			cs.setFloat(8, totalAnalysisResponseData.getWaterVolumetricProduction().getValue());
			cs.setFloat(9, totalAnalysisResponseData.getVolumeWaterCut().getValue());
			
			cs.setFloat(10, totalAnalysisResponseData.getLiquidWeightProduction().getValue());
			cs.setFloat(11, totalAnalysisResponseData.getOilWeightProduction().getValue());
			cs.setFloat(12, totalAnalysisResponseData.getWaterWeightProduction().getValue());
			cs.setFloat(13, totalAnalysisResponseData.getWeightWaterCut().getValue());
			
			cs.setFloat(14, totalAnalysisResponseData.getPumpEff().getValue());
			cs.setFloat(15, totalAnalysisResponseData.getPumpEff1().getValue());
			cs.setFloat(16, totalAnalysisResponseData.getPumpEff2().getValue());
			
			cs.setFloat(17, totalAnalysisResponseData.getSystemEfficiency().getValue());
			cs.setFloat(18, totalAnalysisResponseData.getEnergyPer100mLift().getValue());
			
			cs.setFloat(19, totalAnalysisResponseData.getPumpSettingDepth().getValue());
			cs.setFloat(20, totalAnalysisResponseData.getProducingfluidLevel().getValue());
			cs.setFloat(21, totalAnalysisResponseData.getSubmergence().getValue());
			cs.setFloat(22, totalAnalysisResponseData.getTubingPressure().getValue());
			cs.setFloat(23, totalAnalysisResponseData.getCasingPressure().getValue());
			
			cs.setInt(24,totalAnalysisRequestData.getAcqTime().size()>0?totalAnalysisResponseData.getCommStatus():totalAnalysisRequestData.getCurrentCommStatus());
			cs.setFloat(25, totalAnalysisResponseData.getCommTime());
			cs.setFloat(26, totalAnalysisResponseData.getCommTimeEfficiency());
			cs.setClob(27,commRanceClob);
			
			cs.setInt(28,totalAnalysisRequestData.getAcqTime().size()>0?totalAnalysisResponseData.getRunStatus():totalAnalysisRequestData.getCurrentRunStatus());
			cs.setFloat(29, totalAnalysisResponseData.getRunTime());
			cs.setFloat(30, totalAnalysisResponseData.getRunTimeEfficiency());
			cs.setClob(31,runRanceClob);
			
			cs.setString(32, date);
			cs.setInt(33, recordCount);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	
	public Boolean saveRPMTotalCalculateData(TotalAnalysisResponseData totalAnalysisResponseData,
			TotalAnalysisRequestData totalAnalysisRequestData,
			String date,int recordCount) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		Clob commRanceClob = conn.createClob();
		commRanceClob.setString(1, totalAnalysisResponseData.getCommRange());
		
		Clob runRanceClob = conn.createClob();
		runRanceClob.setString(1, totalAnalysisResponseData.getRunRange());
		
		try {
			cs = conn.prepareCall("{call prd_save_pcp_rpmdaily("
					+ "?,?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,"
					+ "?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?"
					+ ")}");
			cs.setInt(1,StringManagerUtils.stringToInteger(totalAnalysisRequestData.getWellName()));
			cs.setInt(2,totalAnalysisResponseData.getResultStatus());
			
			cs.setInt(3,totalAnalysisResponseData.getExtendedDays());
			
			cs.setFloat(4, totalAnalysisResponseData.getRPM().getValue());
			
			cs.setFloat(5, totalAnalysisResponseData.getTheoreticalProduction().getValue());
			
			cs.setFloat(6, totalAnalysisResponseData.getLiquidVolumetricProduction().getValue());
			cs.setFloat(7, totalAnalysisResponseData.getOilVolumetricProduction().getValue());
			cs.setFloat(8, totalAnalysisResponseData.getWaterVolumetricProduction().getValue());
			cs.setFloat(9, totalAnalysisResponseData.getVolumeWaterCut().getValue());
			
			cs.setFloat(10, totalAnalysisResponseData.getLiquidWeightProduction().getValue());
			cs.setFloat(11, totalAnalysisResponseData.getOilWeightProduction().getValue());
			cs.setFloat(12, totalAnalysisResponseData.getWaterWeightProduction().getValue());
			cs.setFloat(13, totalAnalysisResponseData.getWeightWaterCut().getValue());
			
			cs.setFloat(14, totalAnalysisResponseData.getPumpEff().getValue());
			cs.setFloat(15, totalAnalysisResponseData.getPumpEff1().getValue());
			cs.setFloat(16, totalAnalysisResponseData.getPumpEff2().getValue());
			
			cs.setFloat(17, totalAnalysisResponseData.getSystemEfficiency().getValue());
			cs.setFloat(18, totalAnalysisResponseData.getEnergyPer100mLift().getValue());
			
			cs.setFloat(19, totalAnalysisResponseData.getPumpSettingDepth().getValue());
			cs.setFloat(20, totalAnalysisResponseData.getProducingfluidLevel().getValue());
			cs.setFloat(21, totalAnalysisResponseData.getSubmergence().getValue());
			cs.setFloat(22, totalAnalysisResponseData.getTubingPressure().getValue());
			cs.setFloat(23, totalAnalysisResponseData.getCasingPressure().getValue());
			
			cs.setInt(24,totalAnalysisRequestData.getAcqTime().size()>0?totalAnalysisResponseData.getCommStatus():totalAnalysisRequestData.getCurrentCommStatus());
			cs.setFloat(25, totalAnalysisResponseData.getCommTime());
			cs.setFloat(26, totalAnalysisResponseData.getCommTimeEfficiency());
			cs.setClob(27,commRanceClob);
			
			cs.setInt(28,totalAnalysisRequestData.getAcqTime().size()>0?totalAnalysisResponseData.getRunStatus():totalAnalysisRequestData.getCurrentRunStatus());
			cs.setFloat(29, totalAnalysisResponseData.getRunTime());
			cs.setFloat(30, totalAnalysisResponseData.getRunTimeEfficiency());
			cs.setClob(31,runRanceClob);
			
			cs.setString(32, date);
			cs.setInt(33, recordCount);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveRPMTimingTotalCalculateData(TotalAnalysisResponseData totalAnalysisResponseData,
			TotalAnalysisRequestData totalAnalysisRequestData,
			String timeStr,int recordCount) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		Clob commRanceClob = conn.createClob();
		commRanceClob.setString(1, totalAnalysisResponseData.getCommRange());
		
		Clob runRanceClob = conn.createClob();
		runRanceClob.setString(1, totalAnalysisResponseData.getRunRange());
		
		try {
			cs = conn.prepareCall("{call prd_save_pcp_rpmtimingtotal("
					+ "?,?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,"
					+ "?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?"
					+ ")}");
			cs.setInt(1,StringManagerUtils.stringToInteger(totalAnalysisRequestData.getWellName()));
			cs.setInt(2,totalAnalysisResponseData.getResultStatus());
			
			cs.setInt(3,totalAnalysisResponseData.getExtendedDays());
			
			cs.setFloat(4, totalAnalysisResponseData.getRPM().getValue());
			
			cs.setFloat(5, totalAnalysisResponseData.getTheoreticalProduction().getValue());
			
			cs.setFloat(6, totalAnalysisResponseData.getLiquidVolumetricProduction().getValue());
			cs.setFloat(7, totalAnalysisResponseData.getOilVolumetricProduction().getValue());
			cs.setFloat(8, totalAnalysisResponseData.getWaterVolumetricProduction().getValue());
			cs.setFloat(9, totalAnalysisResponseData.getVolumeWaterCut().getValue());
			
			cs.setFloat(10, totalAnalysisResponseData.getLiquidWeightProduction().getValue());
			cs.setFloat(11, totalAnalysisResponseData.getOilWeightProduction().getValue());
			cs.setFloat(12, totalAnalysisResponseData.getWaterWeightProduction().getValue());
			cs.setFloat(13, totalAnalysisResponseData.getWeightWaterCut().getValue());
			
			cs.setFloat(14, totalAnalysisResponseData.getPumpEff().getValue());
			cs.setFloat(15, totalAnalysisResponseData.getPumpEff1().getValue());
			cs.setFloat(16, totalAnalysisResponseData.getPumpEff2().getValue());
			
			cs.setFloat(17, totalAnalysisResponseData.getSystemEfficiency().getValue());
			cs.setFloat(18, totalAnalysisResponseData.getEnergyPer100mLift().getValue());
			
			cs.setFloat(19, totalAnalysisResponseData.getPumpSettingDepth().getValue());
			cs.setFloat(20, totalAnalysisResponseData.getProducingfluidLevel().getValue());
			cs.setFloat(21, totalAnalysisResponseData.getSubmergence().getValue());
			cs.setFloat(22, totalAnalysisResponseData.getTubingPressure().getValue());
			cs.setFloat(23, totalAnalysisResponseData.getCasingPressure().getValue());
			
			cs.setInt(24,totalAnalysisRequestData.getAcqTime().size()>0?totalAnalysisResponseData.getCommStatus():totalAnalysisRequestData.getCurrentCommStatus());
			cs.setFloat(25, totalAnalysisResponseData.getCommTime());
			cs.setFloat(26, totalAnalysisResponseData.getCommTimeEfficiency());
			cs.setClob(27,commRanceClob);
			
			cs.setInt(28,totalAnalysisRequestData.getAcqTime().size()>0?totalAnalysisResponseData.getRunStatus():totalAnalysisRequestData.getCurrentRunStatus());
			cs.setFloat(29, totalAnalysisResponseData.getRunTime());
			cs.setFloat(30, totalAnalysisResponseData.getRunTimeEfficiency());
			cs.setClob(31,runRanceClob);
			
			cs.setString(32, timeStr);
			cs.setInt(33, recordCount);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveRPMReTotalData(String recordId,TotalAnalysisResponseData totalAnalysisResponseData,int recordCount) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		
		try {
			cs = conn.prepareCall("{call prd_save_pcp_rpmdailyrecal("
					+ "?,?,"
					+ "?,"
					+ "?,"
					+ "?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,"
					+ "?,?,"
					+ "?,?,?,?,?,?"
					+ ")}");
			cs.setInt(1,StringManagerUtils.stringToInteger(recordId));
			cs.setInt(2,totalAnalysisResponseData.getResultStatus());
			
			cs.setInt(3,totalAnalysisResponseData.getExtendedDays());
			
			cs.setFloat(4, totalAnalysisResponseData.getRPM().getValue());
			
			cs.setFloat(5, totalAnalysisResponseData.getTheoreticalProduction().getValue());
			
			cs.setFloat(6, totalAnalysisResponseData.getLiquidVolumetricProduction().getValue());
			cs.setFloat(7, totalAnalysisResponseData.getOilVolumetricProduction().getValue());
			cs.setFloat(8, totalAnalysisResponseData.getWaterVolumetricProduction().getValue());
			cs.setFloat(9, totalAnalysisResponseData.getVolumeWaterCut().getValue());
			
			cs.setFloat(10, totalAnalysisResponseData.getLiquidWeightProduction().getValue());
			cs.setFloat(11, totalAnalysisResponseData.getOilWeightProduction().getValue());
			cs.setFloat(12, totalAnalysisResponseData.getWaterWeightProduction().getValue());
			cs.setFloat(13, totalAnalysisResponseData.getWeightWaterCut().getValue());
			
			cs.setFloat(14, totalAnalysisResponseData.getPumpEff().getValue());
			cs.setFloat(15, totalAnalysisResponseData.getPumpEff1().getValue());
			cs.setFloat(16, totalAnalysisResponseData.getPumpEff2().getValue());
			
			cs.setFloat(17, totalAnalysisResponseData.getSystemEfficiency().getValue());
			cs.setFloat(18, totalAnalysisResponseData.getEnergyPer100mLift().getValue());
			
			cs.setFloat(19, totalAnalysisResponseData.getPumpSettingDepth().getValue());
			cs.setFloat(20, totalAnalysisResponseData.getProducingfluidLevel().getValue());
			cs.setFloat(21, totalAnalysisResponseData.getSubmergence().getValue());
			cs.setFloat(22, totalAnalysisResponseData.getTubingPressure().getValue());
			cs.setFloat(23, totalAnalysisResponseData.getCasingPressure().getValue());
			cs.setInt(24, recordCount);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean initDailyReportData(){
		Connection conn=null;
		CallableStatement cs=null;
		try {
			conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			cs=null;
			cs = conn.prepareCall("{call prd_init_device_daily()}");
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				try {
					cs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	public static String SRPProductionDataToString(SRPCalculateRequestData calculateRequestData){
		StringBuffer productionDataBuff = new StringBuffer();
		Gson gson=new Gson();
		productionDataBuff.append("{");
		productionDataBuff.append("\"FluidPVT\":"+(calculateRequestData.getFluidPVT()!=null?gson.toJson(calculateRequestData.getFluidPVT()):"{}")+",");
		productionDataBuff.append("\"Reservoir\":"+(calculateRequestData.getReservoir()!=null?gson.toJson(calculateRequestData.getReservoir()):"{}")+",");
		productionDataBuff.append("\"RodString\":"+(calculateRequestData.getRodString()!=null?gson.toJson(calculateRequestData.getRodString()):"{}")+",");
		productionDataBuff.append("\"TubingString\":"+(calculateRequestData.getTubingString()!=null?gson.toJson(calculateRequestData.getTubingString()):"{}")+",");
		productionDataBuff.append("\"CasingString\":"+(calculateRequestData.getCasingString()!=null?gson.toJson(calculateRequestData.getCasingString()):"{}")+",");
		productionDataBuff.append("\"PumpingUnit\":"+(calculateRequestData.getPumpingUnit()!=null?gson.toJson(calculateRequestData.getPumpingUnit()):"{}")+",");
		productionDataBuff.append("\"Pump\":"+(calculateRequestData.getPump()!=null?gson.toJson(calculateRequestData.getPump()):"{}")+",");
		productionDataBuff.append("\"Production\":"+(calculateRequestData.getProduction()!=null?gson.toJson(calculateRequestData.getProduction()):"{}")+",");
		productionDataBuff.append("\"ManualIntervention\":"+(calculateRequestData.getManualIntervention()!=null?gson.toJson(calculateRequestData.getManualIntervention()):"{}"));
		productionDataBuff.append("}");
		return productionDataBuff.toString();
	}
	
	public static String PCPProductionDataToString(PCPCalculateRequestData calculateRequestData){
		StringBuffer productionDataBuff = new StringBuffer();
		Gson gson=new Gson();
		productionDataBuff.append("{");
		productionDataBuff.append("\"FluidPVT\":"+(calculateRequestData.getFluidPVT()!=null?gson.toJson(calculateRequestData.getFluidPVT()):"{}")+",");
		productionDataBuff.append("\"Reservoir\":"+(calculateRequestData.getReservoir()!=null?gson.toJson(calculateRequestData.getReservoir()):"{}")+",");
		productionDataBuff.append("\"RodString\":"+(calculateRequestData.getRodString()!=null?gson.toJson(calculateRequestData.getRodString()):"{}")+",");
		productionDataBuff.append("\"TubingString\":"+(calculateRequestData.getTubingString()!=null?gson.toJson(calculateRequestData.getTubingString()):"{}")+",");
		productionDataBuff.append("\"CasingString\":"+(calculateRequestData.getCasingString()!=null?gson.toJson(calculateRequestData.getCasingString()):"{}")+",");
		productionDataBuff.append("\"Pump\":"+(calculateRequestData.getPump()!=null?gson.toJson(calculateRequestData.getPump()):"{}")+",");
		productionDataBuff.append("\"Production\":"+(calculateRequestData.getProduction()!=null?gson.toJson(calculateRequestData.getProduction()):"{}")+",");
		productionDataBuff.append("\"ManualIntervention\":"+(calculateRequestData.getManualIntervention()!=null?gson.toJson(calculateRequestData.getManualIntervention()):"{}"));
		productionDataBuff.append("}");
		return productionDataBuff.toString();
	}
	
	public Boolean initDeviceTimingReportDate(int deviceId,String timeStr,String dateStr,int calculateType) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call prd_init_device_timingreportdate(?,?,?,?)}");
			cs.setInt(1,deviceId);
			cs.setString(2,timeStr);
			cs.setString(3,dateStr);
			cs.setInt(4,calculateType);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	@SuppressWarnings("resource")
	public List<AuxiliaryDeviceHandsontableChangedData.Updatelist> saveAuxiliaryDeviceHandsontableData(
			AuxiliaryDeviceHandsontableChangedData auxiliaryDeviceHandsontableChangedData,
			int deviceType
			) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		PreparedStatement ps=null;
		List<AuxiliaryDeviceHandsontableChangedData.Updatelist> collisionList=new ArrayList<AuxiliaryDeviceHandsontableChangedData.Updatelist>();
		try {
			cs = conn.prepareCall("{call prd_update_auxiliarydevice(?,?,?,?,?,?,?,?)}");
			if(auxiliaryDeviceHandsontableChangedData.getUpdatelist()!=null){
				for(int i=0;i<auxiliaryDeviceHandsontableChangedData.getUpdatelist().size();i++){
					if(StringManagerUtils.isNotNull(auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).getName())){
						cs.setString(1, auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).getId());
						cs.setString(2, auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).getName());
						cs.setInt(3, deviceType);
						cs.setString(4, auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).getModel());
						cs.setString(5, auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).getRemark());
						cs.setString(6, StringManagerUtils.isInteger(auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).getSort())?auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).getSort():"9999");
						cs.registerOutParameter(7, Types.INTEGER);
						cs.registerOutParameter(8,Types.VARCHAR);
						cs.executeUpdate();
						int saveSign=cs.getInt(7);
						String saveResultStr=cs.getString(8);
						auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).setSaveSign(saveSign);
						auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).setSaveStr(saveResultStr);
						collisionList.add(auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i));
						
						if(saveSign==1){
							MemoryDataManagerTask.loadDeviceInfoByPumpingAuxiliaryId(auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).getId(),"update");
						}
					}
				}
			}
			if(auxiliaryDeviceHandsontableChangedData.getInsertlist()!=null){
				for(int i=0;i<auxiliaryDeviceHandsontableChangedData.getInsertlist().size();i++){
					if(StringManagerUtils.isNotNull(auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).getName())){
						cs.setString(1, auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).getId());
						cs.setString(2, auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).getName());
						cs.setInt(3, deviceType);
						cs.setString(4, auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).getModel());
						cs.setString(5, auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).getRemark());
						cs.setString(6, StringManagerUtils.isInteger(auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).getSort())?auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).getSort():"9999");
						cs.registerOutParameter(7, Types.INTEGER);
						cs.registerOutParameter(8,Types.VARCHAR);
						cs.executeUpdate();
						int saveSign=cs.getInt(7);
						String saveResultStr=cs.getString(8);
						auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).setSaveSign(saveSign);
						auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).setSaveStr(saveResultStr);
						collisionList.add(auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i));
					}
				}
			}
			if(auxiliaryDeviceHandsontableChangedData.getDelidslist()!=null && auxiliaryDeviceHandsontableChangedData.getDelidslist().size()>0){
				String delSql="delete from tbl_auxiliarydevice t where t.id in ("+StringUtils.join(auxiliaryDeviceHandsontableChangedData.getDelidslist(), ",")+")";
				ps=conn.prepareStatement(delSql);
				int result=ps.executeUpdate();
				delSql="delete from tbl_auxiliarydeviceaddinfo t where t.deviceid in("+StringUtils.join(auxiliaryDeviceHandsontableChangedData.getDelidslist(), ",")+")";
				ps=conn.prepareStatement(delSql);
				result=ps.executeUpdate();
				MemoryDataManagerTask.loadDeviceInfoByPumpingAuxiliaryId(StringUtils.join(auxiliaryDeviceHandsontableChangedData.getDelidslist(), ","),"update");
				delSql="delete from tbl_auxiliary2master t where t.auxiliaryid in("+StringUtils.join(auxiliaryDeviceHandsontableChangedData.getDelidslist(), ",")+")";
				ps=conn.prepareStatement(delSql);
				result=ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(ps!=null){
				ps.close();
			}
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
		return collisionList;
	}
	
	@SuppressWarnings("resource")
	public List<AuxiliaryDeviceHandsontableChangedData.Updatelist> batchAddAuxiliaryDevice(AuxiliaryDeviceHandsontableChangedData auxiliaryDeviceHandsontableChangedData,int isCheckout) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		PreparedStatement ps=null;
		List<AuxiliaryDeviceHandsontableChangedData.Updatelist> collisionList=new ArrayList<AuxiliaryDeviceHandsontableChangedData.Updatelist>();
		try {
			cs = conn.prepareCall("{call prd_save_auxiliarydevice(?,?,?,?,?,?,?,?)}");
			if(auxiliaryDeviceHandsontableChangedData.getUpdatelist()!=null){
				for(int i=0;i<auxiliaryDeviceHandsontableChangedData.getUpdatelist().size();i++){
					if(StringManagerUtils.isNotNull(auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).getName())){
						cs.setString(1, auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).getName());
						cs.setInt(2, "管辅件".equals(auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).getType())?1:0);
						cs.setString(3, auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).getModel());
						cs.setString(4, auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).getRemark());
						cs.setString(5, StringManagerUtils.isInteger(auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).getSort())?auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).getSort():"");
						cs.setInt(6, isCheckout);
						cs.registerOutParameter(7, Types.INTEGER);
						cs.registerOutParameter(8,Types.VARCHAR);
						cs.executeUpdate();
						int saveSign=cs.getInt(7);
						String saveResultStr=cs.getString(8);
						if(saveSign!=0&&saveSign!=1){
							auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).setSaveSign(saveSign);
							auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i).setSaveStr(saveResultStr);
							collisionList.add(auxiliaryDeviceHandsontableChangedData.getUpdatelist().get(i));
						}
					}
				}
			}
			if(auxiliaryDeviceHandsontableChangedData.getInsertlist()!=null){
				for(int i=0;i<auxiliaryDeviceHandsontableChangedData.getInsertlist().size();i++){
					if(StringManagerUtils.isNotNull(auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).getName())){
						cs.setString(1, auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).getName());
						cs.setInt(2, "管辅件".equals(auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).getType())?1:0);
						cs.setString(3, auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).getModel());
						cs.setString(4, auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).getRemark());
						cs.setString(5, StringManagerUtils.isInteger(auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).getSort())?auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).getSort():"");
						cs.setInt(6, isCheckout);
						cs.registerOutParameter(7, Types.INTEGER);
						cs.registerOutParameter(8,Types.VARCHAR);
						cs.executeUpdate();
						int saveSign=cs.getInt(7);
						String saveResultStr=cs.getString(8);
						if(saveSign!=0&&saveSign!=1){
							auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).setSaveSign(saveSign);
							auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i).setSaveStr(saveResultStr);
							collisionList.add(auxiliaryDeviceHandsontableChangedData.getInsertlist().get(i));
						}
					}
				}
			}
			if(auxiliaryDeviceHandsontableChangedData.getDelidslist()!=null){
				String delSql="delete from tbl_auxiliarydevice t where t.id in ("+StringUtils.join(auxiliaryDeviceHandsontableChangedData.getDelidslist(), ",")+")";
				ps=conn.prepareStatement(delSql);
				int result=ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(ps!=null){
				ps.close();
			}
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
		return collisionList;
	}
	
	public void saveDailyTotalData(int deviceId,DeviceInfo.DailyTotalItem dailyTotalItem) throws SQLException{
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call prd_save_dailytotalcalculate(?,?,?,?,?,?)}");
			cs.setInt(1, deviceId);
			cs.setString(2, dailyTotalItem.getItemColumn());
			cs.setString(3, dailyTotalItem.getItemName());
			cs.setString(4, dailyTotalItem.getAcqTime());
			cs.setFloat(5, dailyTotalItem.getTotalValue());
			cs.setFloat(6, dailyTotalItem.getTodayValue());
			cs.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
	}
	
	public void updateSRPRealtimeDiagramData(int deviceId) throws SQLException{
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call prd_update_srp_diagram_latest(?)}");
			cs.setInt(1, deviceId);
			cs.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
	}
	
	public void updatePCPRealtimeRPMData(int deviceId) throws SQLException{
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call prd_update_pcp_rpm_latest(?)}");
			cs.setInt(1, deviceId);
			cs.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(cs!=null){
				cs.close();
			}
			conn.close();
		}
	}
	
	public boolean resetSequence(String table,String column,String sequence){
		boolean r =true;
		try {
			table=table.toUpperCase();
			column=column.toUpperCase();
			sequence=sequence.toUpperCase();
			
			String sql="select max(t."+column+") from "+table+" t";
			List<?> list= findCallSql(sql);
			if(list!=null && list.size()>0 && list.get(0)!=null){
				long newValue=StringManagerUtils.stringToLong(list.get(0).toString());
				sql = "SELECT " + sequence + ".NEXTVAL FROM DUAL";
				List<?> currentValueList= findCallSql(sql);
				if(currentValueList!=null && currentValueList.size()>0){
					long currentValue=StringManagerUtils.stringToLong(currentValueList.get(0).toString())+1;
					long increment = newValue - currentValue +20;
		            if (increment > 0) {
		            	//临时修改序列步长
		                String alterSql = "ALTER SEQUENCE " + sequence + " INCREMENT BY " + increment;
		                updateOrDeleteBySql(alterSql);

		                //获取下一个值（使序列前进到目标值）
		                String nextValSql = "SELECT " + sequence + ".NEXTVAL FROM DUAL";
		                List<?> nextValueList= findCallSql(nextValSql);
		                

		                // 5. 恢复原始步长（通常为1）
		                String resetSql = "ALTER SEQUENCE " + sequence + " INCREMENT BY 1";
		                updateOrDeleteBySql(resetSql);
		            }
				}
			}
		}catch (Exception e) {
			r =false;
			e.printStackTrace();
		}finally{
			
		}
		return r;
	}
	
	public boolean triggerDisabledOrEnabled(String table,boolean enabled) {
		boolean r =true;
		try {
			String dbUser=Config.getInstance().configFile.getAp().getDatasource().getUser().toUpperCase();
			List<String> triggerNameList=new ArrayList<>();
			String triggerSql="select t.trigger_name from all_triggers t "
					+ " where t.OWNER='"+dbUser.toUpperCase()+"' "
					+ " and t.triggering_event='INSERT' "
					+ " and t.table_name='"+table.toUpperCase()+"'";
			List<?> triggerQueryList=this.findCallSql(triggerSql);
			for(int i=0;i<triggerQueryList.size();i++){
				triggerNameList.add(triggerQueryList.get(i).toString());
			}
			
			for(String triggerName:triggerNameList){
				String triggerDisableSql="ALTER TRIGGER "+triggerName+" "+(enabled?"ENABLE":"DISABLE")+"";
				this.updateOrDeleteBySql(triggerDisableSql);
			}
		}catch (Exception e) {
			r =false;
			e.printStackTrace();
		}finally{
			
		}
		return r;
	}
	
}
