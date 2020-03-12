package com.gao.dao;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.CallableStatement;
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
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import javax.annotation.Resource;

import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.internal.OracleClob;
import oracle.sql.BLOB;
import oracle.sql.CLOB;

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

import com.cosog.model.scada.CallbackDataItems;
import com.gao.model.DistreteAlarmLimit;
import com.gao.model.KeyParameter;
import com.gao.model.MonitorPumpingUnitParams;
import com.gao.model.Org;
import com.gao.model.Outputwellproduction;
import com.gao.model.ProductionOutWellInfo;
import com.gao.model.Pump;
import com.gao.model.ReservoirProperty;
import com.gao.model.gridmodel.CalculateManagerHandsontableChangedData;
import com.gao.model.gridmodel.InverOptimizeHandsontableChangedData;
import com.gao.model.gridmodel.ProductionOutGridPanelData;
import com.gao.model.gridmodel.PumpGridPanelData;
import com.gao.model.gridmodel.ResProHandsontableChangedData;
import com.gao.model.gridmodel.ReservoirPropertyGridPanelData;
import com.gao.model.gridmodel.WellGridPanelData;
import com.gao.model.gridmodel.WellHandsontableChangedData;
import com.gao.model.gridmodel.WellProHandsontableChangedData;
import com.gao.model.gridmodel.WellringGridPanelData;
import com.gao.tast.EquipmentDriverServerTast;
import com.gao.tast.MQTTServerTast.TransferDaily;
import com.gao.tast.MQTTServerTast.TransferDiagram;
import com.gao.model.WellInformation;
import com.gao.model.WellTrajectory;
import com.gao.model.Wellorder;
import com.gao.model.Wells;
import com.gao.model.WellsiteGridPanelData;
import com.gao.model.balance.BalanceResponseData;
import com.gao.model.balance.CycleEvaluaion;
import com.gao.model.balance.TorqueBalanceResponseData;
import com.gao.model.calculate.CalculateResponseData;
import com.gao.model.calculate.CommResponseData;
import com.gao.model.calculate.ElectricCalculateResponseData;
import com.gao.model.calculate.FA2FSResponseData;
import com.gao.model.calculate.FSDiagramModel;
import com.gao.model.calculate.TimeEffResponseData;
import com.gao.model.calculate.TimeEffTotalResponseData;
import com.gao.model.calculate.TotalAnalysisRequestData;
import com.gao.model.calculate.TotalAnalysisResponseData;
import com.gao.model.calculate.TotalCalculateRequestData;
import com.gao.model.calculate.TotalCalculateResponseData;
import com.gao.model.calculate.WellAcquisitionData;
import com.gao.model.drive.RTUDriveConfig;
import com.gao.utils.DataModelMap;
import com.gao.utils.EquipmentDriveMap;
import com.gao.utils.OracleJdbcUtis;
import com.gao.utils.Page;
import com.gao.utils.StringManagerUtils;
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
	public <T> void addObject(T clazz) {

		this.save(clazz);
	}

	public boolean addOrUpdateWellTrajectoryByJbh(WellTrajectory wtvo, String jh) throws Exception {
		ByteArrayInputStream bais = null;
		boolean flag = false;
		String jsgj = null;
		int count = -1;
		int ok = -1;
		int jbh = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql1 = "", sql2 = "", sql3 = "", sql4 = "";
		conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		sql1 = "select count(*) num from t_welltrajectory j,tbl_wellinformation w where j.jbh=w.jlbh and w.jh= ?";
		sql2 = "update t_welltrajectory  set jsgj=? where jbh=(select w.jlbh from  tbl_wellinformation w where w.jh=?) ";
		sql3 = "insert into t_welltrajectory(jbh,jsgj) values(?,?)";
		sql4 = "select w.jlbh  from tbl_wellinformation w where  w.jh= ?";
		jsgj = wtvo.getClsd() + "," + wtvo.getCzsd() + "," + wtvo.getJxj() + "," + wtvo.getFwj() + ";";
		ps = conn.prepareStatement(sql4, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ps.setString(1, jh);
		rs = ps.executeQuery();
		while (rs.next()) {
			jbh = rs.getInt("jlbh");
		}
		try {
			ps = conn.prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, jh);
			rs = ps.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
				if (count > 0) {
					byte[] buffer = (wtvo.getJsgj() + jsgj).getBytes();
					bais = new ByteArrayInputStream(buffer);
					ps = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setBinaryStream(1, bais, bais.available()); // 第二个参数为文件的内容
					ps.setString(2, jh);
					ok = ps.executeUpdate();
					if (ok > 0) {
						flag = true;
					}
				} else {
					ps = conn.prepareStatement(sql3, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps.setInt(1, jbh);
					byte[] buffer = jsgj.getBytes();
					bais = new ByteArrayInputStream(buffer);
					ps.setBinaryStream(2, bais, bais.available()); // 第二个参数为文件的内容
					ok = ps.executeUpdate();
					if (ok > 0) {
						flag = true;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 关闭所打开的对像//
			ps.close();
			bais.close();
		}
		return flag;
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

	public boolean deleteWellTrajectoryInfoById(String track, int line, int jbh) throws SQLException {
		boolean flag = false;
		int ok = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		String sql = "update t_welltrajectory set jsgj=? where jbh=?";
		StringBuffer sb = new StringBuffer();
		ByteArrayInputStream bais = null;
		try {
			String[] tracks = track.split(";");
			for (int i = 0; i < tracks.length; i++) {
				if ((i + 1) == line) {
					continue;
				} else {
					sb.append(tracks[i] + ";");
				}
			}
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			byte[] buffer = (sb.toString()).getBytes();
			bais = new ByteArrayInputStream(buffer);
			ps.setBinaryStream(1, bais, bais.available()); // 第二个参数为文件的内容
			ps.setInt(2, jbh);
			ok = ps.executeUpdate();
			if (ok > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				bais.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return flag;
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
		try {
			stat=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection().createStatement();
			n = stat.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stat != null) {
					stat.close();
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
		Session session=getSessionFactory().getCurrentSession();
				SQLQuery query = session.createSQLQuery(callSql);
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
				return query.list();
	}
	
	/*
	 * 查询曲线数据
	 * */
	public List<?> findGtData(final String sql){
		Session session=getSessionFactory().getCurrentSession();
				SQLQuery query = session.createSQLQuery(sql);
				return query.list();
	}
	 
	public <T> List<T> findgObjectByIdSql(final String sql ){
		Session session=getSessionFactory().getCurrentSession();
				Query query = session.createSQLQuery(sql);
				List listQuery = query.list();
				return listQuery;
	}
	
	public <T> List<T> findChartsList(final String hql) throws Exception {
		Session session=getSessionFactory().getCurrentSession();
					Query query = session.createQuery(hql);
					List<T> list = query.list();
					return list;
	}

	public List<Org> findChildOrg(Integer parentId) {
		String queryString = "SELECT u FROM Org u where u.orgParent=" + parentId + " order by u.orgId ";
		return getObjects(queryString);
	}

	/**
	 * SQL查询
	 * 
	 * @param queryString
	 *            HQL语句
	 * @param values
	 *            HQL参数
	 * @author qiands
	 * @return list
	 */

	public List<?> findSql(final String sql, final Object... values) {
		Session session=getSessionFactory().getCurrentSession();
				SQLQuery query = session.createSQLQuery(sql);
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
				return query.list();
	}
	public <T> List<T> findLeakageSql(final String sql, final Object... values) {
		Session session=getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query.list();
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
				/****
				 * 为query对象参数赋值操作
				 * */
				for (int i = 0; i < values.length; i++) {
					values[i] = values[i].toString().replace("@", ",");
					query.setParameter(i, values[i]);
				}
				int totals = getTotalCountRows(sqlAll, values);//设置数据表中的总记录数
				pager.setTotalCount(totals);
				return query.list();
	}
	//漏失分析获取平均值
	public <T> List<T> getAverageBySql(final String sqlAll,final String[] col, final Object... values) {
		String sql = "select ";
		for(int i = 0; i < col.length;i++){
			String[] attr = col[i].split(" as ");
			if (null != attr && attr.length > 1) {
				col[i] = attr[attr.length-1];
			}
			if( col[i].equals("id") ){
				sql += "(max(" + col[i] + ")+1) as id,";
			}else if( col[i].equals("jssj") ){
				sql += "'平均值' as jssj,";
			}else if( col[i].contains("pf") || col[i].contains("jljh")){
				sql += "avg(" + col[i] + "),";
			}else if( col[i].contains("js" ) && !col[i].equals("jssj") ){
				sql += "avg(" + col[i] + "),";
			}else if( col[i].contains("lsxs") ){
				sql += "decode(avg(" + col[i-1] + "),0,null,null,null," + "avg(" + col[i-2] + ")/avg(" + col[i-1] + ")) as lsxs,";
			}else{
				sql += "null as " + col[i] +",";
			}
		}
		sql = sql.substring(0, sql.length()-1);
		sql += " from (" + sqlAll + ")";
		Session session=getSessionFactory().getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		/**为query对象参数赋值操作 **/
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
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

	public <T> List<T> getfindByIdList(final String hql) throws Exception {
		Session session=getSessionFactory().getCurrentSession();
					Query query = session.createQuery(hql);
					List<T> list = query.list();
					return list;
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

	public List<Wells> getObjectList(String sql) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		List<Wells> list = null;
		try {
			Transaction tran = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			list = query.list();
			tran.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
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
	public <T> List<T> getObjects(String hql) {
		Session session=getSessionFactory().getCurrentSession();
		return (List<T>) this.getHibernateTemplate().find(hql);
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
	
	public List<MonitorPumpingUnitParams> getObjectSqlList(String sql) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		List<MonitorPumpingUnitParams> list = null;
		try {
			Transaction tran = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			list = query.list();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
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
		Integer rows =0;
			SQLQuery query = getSessionFactory().getCurrentSession().createSQLQuery(allsql);
			for (int i = 0; i < values.length; i++) {
				values[i] = values[i].toString().replace("@", ",");
				query.setParameter(i, values[i]);
			}
			rows= Integer.parseInt(query.uniqueResult() + "");
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

	public List<WellTrajectory> getWellJhList() {
		List<WellTrajectory> trackList = new ArrayList<WellTrajectory>();
		WellTrajectory wtvo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select w.jh  from  tbl_wellinformation  w  order by w.jh";
		try {
			conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while (rs.next()) {
				wtvo = new WellTrajectory();
				wtvo.setJh(rs.getString("jh"));
				trackList.add(wtvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}

		return trackList;
	}

	public List<Wells> getWellList(int orgId) {
		String queryString = "select w from Wells as w,Org as o where w.dwbh = o.orgCode and o.orgId=" + orgId;
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		List<Wells> list = null;
		try {
			Transaction tran = session.beginTransaction();

			log.debug("queryString" + queryString);
			list = session.createQuery(queryString).list();
			tran.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
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

	public List<WellTrajectory> getWellTrajectoryJhList() {
		List<WellTrajectory> trackList = new ArrayList<WellTrajectory>();
		WellTrajectory wtvo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select w.jh  from  t_welltrajectory j,tbl_wellinformation  w " + "  left outer join  t_wellorder wo  on w.jh  =wo.jh   where  w.jlbh=j.jbh order by wo.pxbh ";
		try {
			conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			while (rs.next()) {
				wtvo = new WellTrajectory();
				wtvo.setJh(rs.getString("jh"));
				trackList.add(wtvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}

		return trackList;
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


	public List<Outputwellproduction> queryOutputwellproductionDatas(String sql) throws SQLException {
		List<Outputwellproduction> list = new ArrayList<Outputwellproduction>();
		ResultSet rs = null;
		Outputwellproduction well = null;
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs;
		try {
			cs = conn.prepareCall("{ call PRO_QUERY_OBJECTDATA(?,?) }");
			cs.setString(1, sql);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet) cs.getObject(2);
			while (rs.next()) {
				well = new Outputwellproduction();
				well.setJlbh(rs.getInt(1));
				well.setJbh(rs.getInt(2));
				well.setRcyl(rs.getDouble(3));
				well.setCjsj(rs.getDate(4));
				list.add(well);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return list;
	}

	/**
	 * @param sql
	 * @return list 采出井生产数据
	 * @throws SQLException 
	 */
	public List<ProductionOutWellInfo> queryProductionOutDatas(String sql) throws SQLException {
		List<ProductionOutWellInfo> list = new ArrayList<ProductionOutWellInfo>();
		ResultSet rs = null;
		ProductionOutWellInfo well = null;
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs;
		try {
			cs = conn.prepareCall("{ call PRO_QUERY_OBJECTDATA(?,?) }");
			cs.setString(1, sql);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet) cs.getObject(2);
			while (rs.next()) {
				well = new ProductionOutWellInfo();
				well.setJlbh(rs.getInt("jlbh"));
				well.setJbh(rs.getInt("jbh"));
				well.setJh(rs.getString("jh"));
				well.setJslx(rs.getInt("jslx"));
				well.setQtlx(rs.getInt("qtlx"));
				well.setSfpfcl(rs.getInt("sfpfcl"));
				well.setCcjzt(rs.getInt("ccjzt"));
				well.setHsl(rs.getDouble("hsl"));
				well.setYy(rs.getDouble("yy"));
				well.setTy(rs.getDouble("ty"));
				well.setHy(rs.getDouble("hy"));
				well.setDym(rs.getDouble("dym"));
				well.setBg(rs.getDouble("bg"));
				well.setJklw(rs.getDouble("jklw"));
				well.setScqyb(rs.getDouble("scqyb"));
				well.setSccj(rs.getString("sccj"));
				well.setCybxh(rs.getString("cybxh"));
				well.setBj(rs.getInt("bj"));
				well.setBjb(rs.getInt("bjb"));
				well.setZsc(rs.getDouble("zsc"));
				well.setYgnj(rs.getDouble("ygnj"));
				well.setYctgnj(rs.getDouble("yctgnj"));
				well.setYjgj(rs.getDouble("yjgj"));
				well.setYjgjb(rs.getString("yjgjb"));
				well.setYjgcd(rs.getDouble("yjgcd"));
				well.setEjgj(rs.getDouble("ejgj"));
				well.setEjgjb(rs.getString("ejgjb"));
				well.setEjgcd(rs.getDouble("ejgcd"));
				well.setSjgj(rs.getDouble("sjgj"));
				well.setSjgjb(rs.getString("sjgjb"));
				well.setSjgcd(rs.getDouble("sjgcd"));
				well.setRcql(rs.getDouble("rcql"));
				well.setMdzt(rs.getInt("mdzt"));
				well.setJmb(rs.getDouble("jmb"));
				well.setBzgtbh(rs.getInt("bzgtbh"));
				well.setBzdntbh(rs.getInt("bzdntbh"));
				list.add(well);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			conn.close();
		}
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
		}
		return total;
	}

	public List<ReservoirProperty> queryProReservoirPropertyDatas(String sql) throws SQLException {
		List<ReservoirProperty> list = new ArrayList<ReservoirProperty>();
		ResultSet rs = null;
		ReservoirProperty o = null;
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs;
		try {
			cs = conn.prepareCall("{ call PRO_QUERY_OBJECTDATA(?,?) }");
			cs.setString(1, sql);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet) cs.getObject(2);
			while (rs.next()) {
				o = new ReservoirProperty();
				o.setJlbh(rs.getInt(1));
				o.setYymd(rs.getDouble(2));
				o.setSmd(rs.getDouble(3));
				o.setTrqxdmd(rs.getDouble(4));
				o.setBhyl(rs.getDouble(5));
				o.setYqcyl(rs.getDouble(6));
				o.setYqczbsd(rs.getDouble(7));
				o.setYqczbwd(rs.getDouble(8));
				o.setResName(rs.getString(9));
				list.add(o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			rs.close();
			conn.close();
		}
		return list;
	}

	public <T> List<T> queryProReservoirPropertyDatasHql(int offset, int pageSize, String sql) {
		List<T> list = null;
		try {
			Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			SQLQuery query = session.createSQLQuery("{call PRO_QUERY_OBJECTDATA(?,?) }");
			query.setFirstResult(offset);
			query.setMaxResults(pageSize);
			query.setString(1, sql);
			query.setParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			query.executeUpdate();
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<WellInformation> queryWellInformationDatas(String sql) throws SQLException {
		List<WellInformation> list = new ArrayList<WellInformation>();
		ResultSet rs = null;
		WellInformation well = null;
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs;
		try {
			cs = conn.prepareCall("{ call PRO_QUERY_OBJECTDATA(?,?) }");
			cs.setString(1, sql);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			cs.execute();
			rs = (ResultSet) cs.getObject(2);
			while (rs.next()) {
				well = new WellInformation();
				well.setJlbh(rs.getInt(1));
				well.setDwbh(rs.getString(2));
				well.setJc(rs.getString(3));
				well.setJhh(rs.getString(4));
				well.setJh(rs.getString(5));
				well.setJlx(rs.getInt(6));
				well.setSsjw(rs.getInt(7));
				well.setSszcdy(rs.getInt(8));
				well.setRgzsjd(rs.getString(9));
				well.setOrgName(rs.getString(10));
				well.setResName(rs.getString(11));
				well.setYqcbh(rs.getString(12));
				well.setRgzsj(rs.getDouble("rgzsj"));
				list.add(well);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			rs.close();
			conn.close();
		}
		return list;
	}


	/**
	 * 新增一个对象
	 * 
	 * @param object
	 */
	@Transactional
	public void save(Object object) {
		getSessionFactory().getCurrentSession().save(object);
	}

	public <T> void saveOrUpdateObject(T clazz) {
		this.getHibernateTemplate().saveOrUpdate(clazz);
	}

	public Boolean saveProductionDataEditerGridData(WellProHandsontableChangedData wellProHandsontableChangedData,String wellType, String ids) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs;
		PreparedStatement ps=null;
		String currentTime=StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
		String tableName="tbl_rpc_productiondata_latest";
		String prdName="prd_save_rpc_productiondata";
		if("200".equalsIgnoreCase(wellType)){
			tableName="tbl_rpc_productiondata_latest";
			prdName="prd_save_rpc_productiondata";
		}else if("400".equalsIgnoreCase(wellType)){
			tableName="tbl_pcp_productiondata_latest";
			prdName="prd_save_pcp_productiondata";
		}
		try {
			cs = conn.prepareCall("{call "+prdName+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			if(wellProHandsontableChangedData.getUpdatelist()!=null){
				for(int i=0;i<wellProHandsontableChangedData.getUpdatelist().size();i++){
					if(StringManagerUtils.isNotNull(wellProHandsontableChangedData.getUpdatelist().get(i).getWellName())){
						
						cs.setString(1, wellProHandsontableChangedData.getUpdatelist().get(i).getWellName());
						cs.setString(2, wellProHandsontableChangedData.getUpdatelist().get(i).getRunTime());
						
						cs.setString(3, wellProHandsontableChangedData.getUpdatelist().get(i).getCrudeOilDensity());
						cs.setString(4, wellProHandsontableChangedData.getUpdatelist().get(i).getWaterDensity());
						cs.setString(5, wellProHandsontableChangedData.getUpdatelist().get(i).getNaturalGasRelativeDensity());
						cs.setString(6, wellProHandsontableChangedData.getUpdatelist().get(i).getSaturationPressure());
						cs.setString(7, wellProHandsontableChangedData.getUpdatelist().get(i).getReservoirDepth());
						cs.setString(8, wellProHandsontableChangedData.getUpdatelist().get(i).getReservoirTemperature());
						
						cs.setString(9, wellProHandsontableChangedData.getUpdatelist().get(i).getTubingPressure());
						cs.setString(10, wellProHandsontableChangedData.getUpdatelist().get(i).getCasingPressure());
						cs.setString(11, wellProHandsontableChangedData.getUpdatelist().get(i).getWellHeadFluidTemperature());
						cs.setString(12, wellProHandsontableChangedData.getUpdatelist().get(i).getWaterCut_W());
						cs.setString(13, wellProHandsontableChangedData.getUpdatelist().get(i).getProductionGasOilRatio());
						cs.setString(14, wellProHandsontableChangedData.getUpdatelist().get(i).getProducingfluidLevel());
						cs.setString(15, wellProHandsontableChangedData.getUpdatelist().get(i).getPumpSettingDepth());
						
						cs.setString(16, wellProHandsontableChangedData.getUpdatelist().get(i).getPumpGrade());
						cs.setString(17, wellProHandsontableChangedData.getUpdatelist().get(i).getPumpBoreDiameter());
						cs.setString(18, wellProHandsontableChangedData.getUpdatelist().get(i).getPlungerLength());
						cs.setString(19, wellProHandsontableChangedData.getUpdatelist().get(i).getBarrelLength());
						cs.setString(20, wellProHandsontableChangedData.getUpdatelist().get(i).getBarrelSeries());
						cs.setString(21, wellProHandsontableChangedData.getUpdatelist().get(i).getRotorDiameter());
						cs.setString(22, wellProHandsontableChangedData.getUpdatelist().get(i).getQPR());
						
						cs.setString(23, wellProHandsontableChangedData.getUpdatelist().get(i).getTubingStringInsideDiameter());
						cs.setString(24, wellProHandsontableChangedData.getUpdatelist().get(i).getCasingStringInsideDiameter());
						
						String rodString="";
						rodString+=wellProHandsontableChangedData.getUpdatelist().get(i).getRodGrade1()+","
									+wellProHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter1()+","
									+wellProHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter1()+","
									+wellProHandsontableChangedData.getUpdatelist().get(i).getRodLength1()+";"
									+wellProHandsontableChangedData.getUpdatelist().get(i).getRodGrade2()+","
									+wellProHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter2()+","
									+wellProHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter2()+","
									+wellProHandsontableChangedData.getUpdatelist().get(i).getRodLength2()+";"
									+wellProHandsontableChangedData.getUpdatelist().get(i).getRodGrade3()+","
									+wellProHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter3()+","
									+wellProHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter3()+","
									+wellProHandsontableChangedData.getUpdatelist().get(i).getRodLength3()+";"
									+wellProHandsontableChangedData.getUpdatelist().get(i).getRodGrade4()+","
									+wellProHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter4()+","
									+wellProHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter4()+","
									+wellProHandsontableChangedData.getUpdatelist().get(i).getRodLength4();
						cs.setString(25, rodString);
						
						cs.setString(26, wellProHandsontableChangedData.getUpdatelist().get(i).getAnchoringStateName());
						cs.setString(27, wellProHandsontableChangedData.getUpdatelist().get(i).getNetGrossRatio());
						cs.setString(28, currentTime);
						cs.setString(29, ids);
						cs.executeUpdate();
					}
				}
			}
			if(wellProHandsontableChangedData.getInsertlist()!=null){
				for(int i=0;i<wellProHandsontableChangedData.getInsertlist().size();i++){
					if(StringManagerUtils.isNotNull(wellProHandsontableChangedData.getInsertlist().get(i).getWellName())){
						
						cs.setString(1, wellProHandsontableChangedData.getInsertlist().get(i).getWellName());
						cs.setString(2, wellProHandsontableChangedData.getInsertlist().get(i).getRunTime());
						
						cs.setString(3, wellProHandsontableChangedData.getInsertlist().get(i).getCrudeOilDensity());
						cs.setString(4, wellProHandsontableChangedData.getInsertlist().get(i).getWaterDensity());
						cs.setString(5, wellProHandsontableChangedData.getInsertlist().get(i).getNaturalGasRelativeDensity());
						cs.setString(6, wellProHandsontableChangedData.getInsertlist().get(i).getSaturationPressure());
						cs.setString(7, wellProHandsontableChangedData.getInsertlist().get(i).getReservoirDepth());
						cs.setString(8, wellProHandsontableChangedData.getInsertlist().get(i).getReservoirTemperature());
						
						cs.setString(9, wellProHandsontableChangedData.getInsertlist().get(i).getTubingPressure());
						cs.setString(10, wellProHandsontableChangedData.getInsertlist().get(i).getCasingPressure());
						cs.setString(11, wellProHandsontableChangedData.getInsertlist().get(i).getWellHeadFluidTemperature());
						cs.setString(12, wellProHandsontableChangedData.getInsertlist().get(i).getWaterCut_W());
						cs.setString(13, wellProHandsontableChangedData.getInsertlist().get(i).getProductionGasOilRatio());
						cs.setString(14, wellProHandsontableChangedData.getInsertlist().get(i).getProducingfluidLevel());
						cs.setString(15, wellProHandsontableChangedData.getInsertlist().get(i).getPumpSettingDepth());
						
						cs.setString(16, wellProHandsontableChangedData.getInsertlist().get(i).getPumpGrade());
						cs.setString(17, wellProHandsontableChangedData.getInsertlist().get(i).getPumpBoreDiameter());
						cs.setString(18, wellProHandsontableChangedData.getInsertlist().get(i).getPlungerLength());
						cs.setString(19, wellProHandsontableChangedData.getInsertlist().get(i).getBarrelLength());
						cs.setString(20, wellProHandsontableChangedData.getInsertlist().get(i).getBarrelSeries());
						cs.setString(21, wellProHandsontableChangedData.getInsertlist().get(i).getRotorDiameter());
						cs.setString(22, wellProHandsontableChangedData.getInsertlist().get(i).getQPR());
						
						cs.setString(23, wellProHandsontableChangedData.getInsertlist().get(i).getTubingStringInsideDiameter());
						cs.setString(24, wellProHandsontableChangedData.getInsertlist().get(i).getCasingStringInsideDiameter());
						
						String rodString="";
						rodString+=wellProHandsontableChangedData.getInsertlist().get(i).getRodGrade1()+","
									+wellProHandsontableChangedData.getInsertlist().get(i).getRodOutsideDiameter1()+","
									+wellProHandsontableChangedData.getInsertlist().get(i).getRodInsideDiameter1()+","
									+wellProHandsontableChangedData.getInsertlist().get(i).getRodLength1()+";"
									+wellProHandsontableChangedData.getInsertlist().get(i).getRodGrade2()+","
									+wellProHandsontableChangedData.getInsertlist().get(i).getRodOutsideDiameter2()+","
									+wellProHandsontableChangedData.getInsertlist().get(i).getRodInsideDiameter2()+","
									+wellProHandsontableChangedData.getInsertlist().get(i).getRodLength2()+";"
									+wellProHandsontableChangedData.getInsertlist().get(i).getRodGrade3()+","
									+wellProHandsontableChangedData.getInsertlist().get(i).getRodOutsideDiameter3()+","
									+wellProHandsontableChangedData.getInsertlist().get(i).getRodInsideDiameter3()+","
									+wellProHandsontableChangedData.getInsertlist().get(i).getRodLength3()+";"
									+wellProHandsontableChangedData.getInsertlist().get(i).getRodGrade4()+","
									+wellProHandsontableChangedData.getInsertlist().get(i).getRodOutsideDiameter4()+","
									+wellProHandsontableChangedData.getInsertlist().get(i).getRodInsideDiameter4()+","
									+wellProHandsontableChangedData.getInsertlist().get(i).getRodLength4();
						cs.setString(25, rodString);
						
						cs.setString(26, wellProHandsontableChangedData.getInsertlist().get(i).getAnchoringStateName());
						cs.setString(27, wellProHandsontableChangedData.getInsertlist().get(i).getNetGrossRatio());
						cs.setString(28, currentTime);
						cs.setString(29, ids);
						cs.executeUpdate();
					}
				}
			}
			if(wellProHandsontableChangedData.getDelidslist()!=null){
				String delIds="";
				String delSql="";
				for(int i=0;i<wellProHandsontableChangedData.getDelidslist().size();i++){
					delIds+=wellProHandsontableChangedData.getDelidslist().get(i);
					if(i<wellProHandsontableChangedData.getDelidslist().size()-1){
						delIds+=",";
					}
				}
				delSql="delete from "+tableName+" t where t.wellId in ("+delIds+")";
				ps=conn.prepareStatement(delSql);
				int result=ps.executeUpdate();
//				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(ps!=null)
				ps.close();
			conn.close();
		}
		return true;
	}
	
	public int updateOrDeleteBySql(String sql) throws SQLException{
		Connection conn=null;
		PreparedStatement ps=null;
		int result=0;
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			ps=conn.prepareStatement(sql);
			result=ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(ps!=null){
				ps.close();
			}
			if(conn!=null){
				conn.close();
			}
		}
		
		return result;
	}
	
	@SuppressWarnings("resource")
	public Boolean saveWellEditerGridData(WellHandsontableChangedData wellHandsontableChangedData,String orgIds,String orgId) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		PreparedStatement ps=null;
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTast.initDriverConfig();
		}
		try {
			cs = conn.prepareCall("{call prd_save_wellinformation(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			if(wellHandsontableChangedData.getUpdatelist()!=null){
				for(int i=0;i<wellHandsontableChangedData.getUpdatelist().size();i++){
					if(StringManagerUtils.isNotNull(wellHandsontableChangedData.getUpdatelist().get(i).getWellName())){
						String driverName=wellHandsontableChangedData.getUpdatelist().get(i).getDriverName();
						String driverCode="";
						for(Entry<String, Object> entry:equipmentDriveMap.entrySet()){
							RTUDriveConfig driveConfig=(RTUDriveConfig)entry.getValue();
							if(driverName.equals(driveConfig.getDriverName())){
								driverCode=driveConfig.getDriverCode();
								break;
							}
						}
						
						cs.setString(1, wellHandsontableChangedData.getUpdatelist().get(i).getOrgName());
						cs.setString(2, wellHandsontableChangedData.getUpdatelist().get(i).getResName());
						cs.setString(3, wellHandsontableChangedData.getUpdatelist().get(i).getWellName());
						cs.setString(4, wellHandsontableChangedData.getUpdatelist().get(i).getLiftingTypeName());
						cs.setString(5, driverCode);
						cs.setString(6, wellHandsontableChangedData.getUpdatelist().get(i).getAcquisitionUnit());
						cs.setString(7, wellHandsontableChangedData.getUpdatelist().get(i).getDriverAddr());
						cs.setString(8, wellHandsontableChangedData.getUpdatelist().get(i).getDriverId());
						cs.setString(9, wellHandsontableChangedData.getUpdatelist().get(i).getAcqcycle_diagram());
						cs.setString(10, wellHandsontableChangedData.getUpdatelist().get(i).getAcqcycle_discrete());
						cs.setString(11, wellHandsontableChangedData.getUpdatelist().get(i).getSavecycle_discrete());
						cs.setString(12, wellHandsontableChangedData.getUpdatelist().get(i).getRuntimeEfficiencySource());
						cs.setString(13, wellHandsontableChangedData.getUpdatelist().get(i).getVideoUrl());
						cs.setString(14, wellHandsontableChangedData.getUpdatelist().get(i).getSortNum());
						cs.setString(15, orgIds);
						cs.setString(16, orgId);
						cs.executeUpdate();
					}
				}
			}
			if(wellHandsontableChangedData.getInsertlist()!=null){
				for(int i=0;i<wellHandsontableChangedData.getInsertlist().size();i++){
					if(StringManagerUtils.isNotNull(wellHandsontableChangedData.getInsertlist().get(i).getWellName())){
						String driverName=wellHandsontableChangedData.getInsertlist().get(i).getDriverName();
						String driverCode="";
						for(Entry<String, Object> entry:equipmentDriveMap.entrySet()){
							RTUDriveConfig driveConfig=(RTUDriveConfig)entry.getValue();
							if(driverName.equals(driveConfig.getDriverName())){
								driverCode=driveConfig.getDriverCode();
								break;
							}
						}
						
						cs.setString(1, wellHandsontableChangedData.getInsertlist().get(i).getOrgName());
						cs.setString(2, wellHandsontableChangedData.getInsertlist().get(i).getResName());
						cs.setString(3, wellHandsontableChangedData.getInsertlist().get(i).getWellName());
						cs.setString(4, wellHandsontableChangedData.getInsertlist().get(i).getLiftingTypeName());
						cs.setString(5, driverCode);
						cs.setString(6, wellHandsontableChangedData.getInsertlist().get(i).getAcquisitionUnit());
						cs.setString(7, wellHandsontableChangedData.getInsertlist().get(i).getDriverAddr());
						cs.setString(8, wellHandsontableChangedData.getInsertlist().get(i).getDriverId());
						cs.setString(9, wellHandsontableChangedData.getInsertlist().get(i).getAcqcycle_diagram());
						cs.setString(10, wellHandsontableChangedData.getInsertlist().get(i).getAcqcycle_discrete());
						cs.setString(11, wellHandsontableChangedData.getInsertlist().get(i).getSavecycle_discrete());
						cs.setString(12, wellHandsontableChangedData.getInsertlist().get(i).getRuntimeEfficiencySource());
						cs.setString(13, wellHandsontableChangedData.getInsertlist().get(i).getVideoUrl());
						cs.setString(14, wellHandsontableChangedData.getInsertlist().get(i).getSortNum());
						cs.setString(15, orgIds);
						cs.setString(16, orgId);
						cs.executeUpdate();
					}
				}
			}
			if(wellHandsontableChangedData.getDelidslist()!=null){
				String delIds="";
				String delSql="";
				for(int i=0;i<wellHandsontableChangedData.getDelidslist().size();i++){
					delIds+=wellHandsontableChangedData.getDelidslist().get(i);
					if(i<wellHandsontableChangedData.getDelidslist().size()-1){
						delIds+=",";
					}
				}
				delSql="delete from tbl_wellinformation t where t.id in ("+delIds+")";
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
		return true;
	}
	
	
	@SuppressWarnings("resource")
	public Boolean saveRecalculateData(CalculateManagerHandsontableChangedData calculateManagerHandsontableChangedData) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		PreparedStatement ps=null;
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTast.initDriverConfig();
		}
		try {
			cs = conn.prepareCall("{call pro_save_rpc_recalculateparam(?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,"
					+ "?,?,?,"
					+ "?,?,"
					+ "?,"
					+ "?,?)}");
			if(calculateManagerHandsontableChangedData.getUpdatelist()!=null){
				for(int i=0;i<calculateManagerHandsontableChangedData.getUpdatelist().size();i++){
					if(StringManagerUtils.isNotNull(calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWellName())){
						String rodString="";
						rodString+=calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade1()+","
									+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter1()+","
									+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter1()+","
									+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength1()+";"
									+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade2()+","
									+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter2()+","
									+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter2()+","
									+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength2()+";"
									+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade3()+","
									+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter3()+","
									+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter3()+","
									+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength3()+";"
									+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodGrade4()+","
									+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodOutsideDiameter4()+","
									+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodInsideDiameter4()+","
									+calculateManagerHandsontableChangedData.getUpdatelist().get(i).getRodLength4();
						
						cs.setString(1, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getId());
						
						cs.setString(2, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getCrudeoilDensity());
						cs.setString(3, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWaterDensity());
						cs.setString(4, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getNaturalGasRelativeDensity());
						cs.setString(5, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getSaturationPressure());
						cs.setString(6, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getReservoirDepth());
						cs.setString(7, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getReservoirTemperature());
						
						cs.setString(8, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getTubingPressure());
						cs.setString(9, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getCasingPressure());
						cs.setString(10, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWellHeadFluidTemperature());
						cs.setString(11, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getWaterCut());
						cs.setString(12, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getProductionGasOilRatio());
						cs.setString(13, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getProducingFluidLevel());
						cs.setString(14, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPumpSettingDepth());
						
						cs.setString(15, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPumpGrade());
						cs.setString(16, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPumpboreDiameter());
						cs.setString(17, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getPlungerLength());
						
						cs.setString(18, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getTubingStringInsideDiameter());
						cs.setString(19, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getCasingStringInsideDiameter());
						
						cs.setString(20, rodString);
						
						cs.setString(21, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getAnchoringStateName());
						cs.setString(22, calculateManagerHandsontableChangedData.getUpdatelist().get(i).getNetGrossRatio());
						cs.executeUpdate();
					}
				}
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
	
	public Boolean editWellName(String oldWellName,String newWellName,String orgid) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call prd_change_wellname(?,?,?)}");
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
	
	public Boolean saveOrUpdateorDeletePump(Pump p, String ids, String comandType) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call PRO_T_023_UPDATE_ADD_DELETE(?,?,?,?,?,?,?,?)}");
			cs.setString(1, p.getSccj());
			cs.setString(2, p.getCybxh());
			cs.setInt(3, p.getBlx());
			cs.setInt(4, p.getBlx());
			cs.setDouble(5, p.getBj());
			cs.setDouble(6, p.getZsc());
			if (comandType.equalsIgnoreCase("add")) {
				ids = "no";
				cs.setInt(7, 0);
				cs.setString(8, ids);
			} else if (comandType.equalsIgnoreCase("modify")) {
				ids = "no";
				cs.setInt(7, p.getJlbh());
				cs.setString(8, ids);
			} else if (comandType.equalsIgnoreCase("delete")) {
				cs.setInt(7, 0);
				cs.setString(8, ids);
			}
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
	
	public Boolean savePumpEditerGridData(PumpGridPanelData p, String ids, String comandType) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call PRO_savePumpData(?,?,?,?,?,?,?)}");
			cs.setString(1, p.getSccj());
			cs.setString(2, p.getCybxh());
			cs.setString(3, p.getBlxName());
			cs.setString(4, p.getBjbName());
			cs.setString(5, p.getBtlxName());
			cs.setDouble(6, p.getBj());
			cs.setDouble(7, p.getZsc());
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
	
	public Boolean savePumpingUnitEditerGridData(JSONObject jsonObject) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call PRO_savepumpingunitdata(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setInt(1, StringManagerUtils.getJSONObjectInt(jsonObject,"id"));
			cs.setString(2, StringManagerUtils.getJSONObjectString(jsonObject, "sccj"));
			cs.setString(3, StringManagerUtils.getJSONObjectString(jsonObject, "cyjxh"));
			cs.setDouble(4, StringManagerUtils.getJSONObjectDouble(jsonObject, "xdedzh"));
			cs.setDouble(5, StringManagerUtils.getJSONObjectDouble(jsonObject, "jsqednj"));
			cs.setDouble(6, StringManagerUtils.getJSONObjectDouble(jsonObject, "dkqbzl"));
			cs.setDouble(7, StringManagerUtils.getJSONObjectDouble(jsonObject, "qbzxbj"));
			cs.setDouble(8, StringManagerUtils.getJSONObjectDouble(jsonObject, "jgbphz"));
			cs.setString(9, StringManagerUtils.getJSONObjectString(jsonObject, "dkphkzl"));
			cs.setDouble(10, StringManagerUtils.getJSONObjectDouble(jsonObject, "qbpzj"));
			cs.setString(11, StringManagerUtils.getJSONObjectString(jsonObject, "xzfxmc"));
			cs.setDouble(12, StringManagerUtils.getJSONObjectDouble(jsonObject, "zdtzjl"));
			cs.setInt(13, StringManagerUtils.getJSONObjectInt(jsonObject,"bpphks"));
			cs.setString(14, StringManagerUtils.getJSONObjectString(jsonObject, "cyjlxmc"));
			cs.setDouble(15, StringManagerUtils.getJSONObjectDouble(jsonObject, "pdxl"));
			cs.setDouble(16, StringManagerUtils.getJSONObjectDouble(jsonObject, "jsxxl"));
			cs.setDouble(17, StringManagerUtils.getJSONObjectDouble(jsonObject, "slgxl"));
			cs.setDouble(18, StringManagerUtils.getJSONObjectDouble(jsonObject, "djxl"));
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
	
	
	public Boolean saveBalanceAlarmStatusGridData(JSONObject jsonObject) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call PRO_T_205_SAVEBALANCELIMITDATA(?,?,?)}");
			cs.setInt(1, StringManagerUtils.getJSONObjectInt(jsonObject,"id"));
			cs.setDouble(2, StringManagerUtils.getJSONObjectDouble(jsonObject, "minvalue"));
			cs.setDouble(3, StringManagerUtils.getJSONObjectDouble(jsonObject, "maxvalue"));
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
	
	public Boolean doStatItemsSetSave(String statType,String data) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		Statement st=null; 
		JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+data+"}");//解析数据
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		
		try {
			st=conn.createStatement(); 
			if(!"GLPHD".equalsIgnoreCase(statType)&&!"PHD".equalsIgnoreCase(statType)){
				String sql="delete from tbl_rpc_statistics_conf t where t.s_type='"+statType+"'";
				int updatecount=st.executeUpdate(sql);
			}
			
			for(int i=0;i<jsonArray.size();i++){
				JSONObject everydata = JSONObject.fromObject(jsonArray.getString(i));
				String statitem=everydata.getString("statitem");
				String downlimit=everydata.getString("downlimit");
				String uplimit=everydata.getString("uplimit");
				String sql="insert into tbl_rpc_statistics_conf(s_level,s_min,s_max,s_type) values('"+statitem+"',"+downlimit+","+uplimit+",'"+statType+"')";
				if("GLPHD".equalsIgnoreCase(statType)||"PHD".equalsIgnoreCase(statType)){
					sql="update tbl_rpc_statistics_conf set s_min="+downlimit+",s_max="+uplimit+" where s_type='"+statType+"' and s_level='"+statitem+"'";
				}
				int updatecount=st.executeUpdate(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(st!=null)
				st.close();
			conn.close();
		}
		return true;
	}

	/******************************************
	 * **********************
	 * 
	 * 井身轨迹操作方法/ /** 向数据库中插入一个新的BLOB对象(图片)
	 * 
	 * @param infile
	 *            - 要输入的数据文件
	 * @throws SQLException 
	 * @throws java.lang.Exception
	 * 
	 */
	public List<WellTrajectory> scanWellTrajectoryInfo(String orgCode, String resCode, String jh) throws SQLException {
		List<WellTrajectory> trackList = new ArrayList<WellTrajectory>();
		WellTrajectory wtvo = null;
		String alltrack = "";
		int id = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Blob blob = null;
		String jh_Str = "";
		String orgCode_Str = "";
		if (StringUtils.isNotBlank(orgCode)) {
			orgCode_Str = " and w.dwbh like ?";
		}
		String resCode_Str = "";
		if (StringUtils.isNotBlank(resCode)) {
			resCode_Str = " and w.yqcbh like ?";
		}
		String sql = "select j.jlbh,j.jbh ,j.jsgj  from  t_welltrajectory j  where j.jbh in (select w.jlbh from tbl_wellinformation  w where w.jh=? ";
		if (orgCode_Str != "") {
			sql += orgCode_Str;
		}
		if (resCode_Str != "") {
			sql += resCode_Str;
		}
		sql += ") order by j.jlbh";
		conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		try {
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, jh);
			if (StringUtils.isNotBlank(orgCode)) {
				ps.setString(2, orgCode);
			}
			if (StringUtils.isNotBlank(resCode)) {
				ps.setString(2, resCode);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				blob = rs.getBlob("jsgj");
				if (blob != null) {
					alltrack = ConvertBLOBtoString(blob);
				}
				String[] tracks = alltrack.split(";");
				for (String track : tracks) {
					wtvo = new WellTrajectory();
					String[] trackInfo = track.split(",");
					if (trackInfo.length == 4) {
						wtvo.setJlbh(id++);
						wtvo.setJbh(rs.getInt("jbh"));
						wtvo.setJh(jh);
						wtvo.setJsgj(track);
						wtvo.setClsd(trackInfo[0]);
						wtvo.setCzsd(trackInfo[1]);
						wtvo.setJxj(trackInfo[2]);
						wtvo.setFwj(trackInfo[3]);
						trackList.add(wtvo);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			conn.close();
		}

		return trackList;
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

	public int updateWellorder(final String hql) {
		Session session=getSessionFactory().getCurrentSession();
				SQLQuery query = session.createSQLQuery(hql);
				return query.executeUpdate();
	}
	
	public boolean updateWellorder(JSONObject everydata,String orgid) throws SQLException {
		Connection conn=null;
		try {
			conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			CallableStatement cs;
			cs = conn.prepareCall("{call PRO_SAVEWELLORDER(?,?,?)}");
			cs.setString(1, everydata.getString("jh"));
			cs.setInt(2, everydata.getInt("pxbh"));
			cs.setString(3, orgid);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(conn!=null){
				conn.close();
			}
		}
		return true;
	}
	

	public boolean updateWellTrajectoryById(WellTrajectory wtvo, int line, int jbh) throws SQLException {
		boolean flag = false;
		int ok = -1;
		ByteArrayInputStream bais = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		StringBuffer sb = new StringBuffer();
		String jsgj = wtvo.getClsd() + "," + wtvo.getCzsd() + "," + wtvo.getJxj() + "," + wtvo.getFwj() + ";";
		String sql = "update t_welltrajectory set jsgj=? where jbh=?";
		if (wtvo != null) {
			try {
				String[] tracks = wtvo.getJsgj().split(";");
				for (int i = 0; i < tracks.length; i++) {
					if ((i + 1) == line) {
						tracks[i] = jsgj;
						sb.append(tracks[i]);
					} else {
						sb.append(tracks[i] + ";");
					}

				}
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				byte[] buffer = (sb.toString()).getBytes();
				bais = new ByteArrayInputStream(buffer);
				ps.setBinaryStream(1, bais, bais.available()); // 第二个参数为文件的内容
				ps.setInt(2, jbh);
				ok = ps.executeUpdate();
				if (ok > 0) {
					flag = true;
				}
			} catch (Exception e) {

				e.printStackTrace();
			} finally {
				try {
					bais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return flag;
	}

	public void callProcedureByCallName() {
		String callName = "{Call proc_test(?,?)}";
		ResultSet rs = null;
		CallableStatement call=null;
		try {
			call=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection().prepareCall(callName);
			call.setString(1, "");
			call.registerOutParameter(2, Types.VARCHAR);
			rs = call.executeQuery();
		}catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 调用存储过程 删除流程相关记录
	public boolean deleteByCallPro(String procinstid) throws SQLException {
		String procdure = "{Call sp_deleteInstByRootID(?)}";
		CallableStatement cs = null;
		try {
			cs = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection().prepareCall(procdure);
			cs.setString(1, procinstid);
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cs.execute();
	}
	
	public void OnGetDate(final List<CallbackDataItems> list,final String RTUName) throws HibernateException,SQLException{
//				int jbh=0;
//				Connection conn=null;
//				PreparedStatement ps=null;
//				ResultSet rs = null;
//				conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
//				conn.setAutoCommit(false);
//				List<CallbackDataItems> GtList=new ArrayList<CallbackDataItems>();
//				String sql="select jlbh from tbl_wellinformation where cjdybm=?";
//				String sql2="insert into tbl_rpc_diagram_hist (jbh,cjsj,gtsj) values (?,to_date(?,'yyyy-mm-dd hh24:mi:ss'),empty_blob())";
//				String sql3="select gtsj from tbl_rpc_diagram_hist where jbh=? and cjsj=to_date(?,'yyyy-mm-dd hh24:mi:ss')";
//				for(int i=0;i<list.size();i++){
//					if(list.get(i).TableName.equalsIgnoreCase("tbl_rpc_diagram_hist")){
//						GtList.add(list.get(i));
//					}
//				}
//				try {
//					ps=conn.prepareStatement(sql);
//					ps.setString(1, RTUName);
//					rs = ps.executeQuery();
//					while(rs.next()){
//						jbh=rs.getInt(1);
//					}
//					
//					ps=conn.prepareStatement(sql2);
//					ps.setInt(1, jbh);
//					ps.setString(2, GtList.get(0).Value);
//					int result=ps.executeUpdate();
//					conn.commit();
//					
//					ps=conn.prepareStatement(sql3);
//					ps.setInt(1, jbh);
//					ps.setString(2, GtList.get(0).Value);
//					rs=ps.executeQuery();
//					while(rs.next()){
//						oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob(1);
//						OutputStream out=blob.getBinaryOutputStream();
//						out.write(GtList.get(1).Value.getBytes());
//						out.close();
//						conn.commit();
//					}
//					
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}finally{
//					if(ps!=null){
//						ps.close();
//					}
//					if(rs!=null){
//						rs.close();
//					}
//					if(conn!=null){
//						conn.close();
//					}
//				}
	}

	public Boolean setAlarmLevelColor(String alarmLevelBackgroundColor0,String alarmLevelBackgroundColor1,String alarmLevelBackgroundColor2,String alarmLevelBackgroundColor3,
			String alarmLevelColor0,String alarmLevelColor1,String alarmLevelColor2,String alarmLevelColor3,
			String alarmLevelOpacity0,String alarmLevelOpacity1,String alarmLevelOpacity2,String alarmLevelOpacity3) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call prd_save_alarmcolor(?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, alarmLevelBackgroundColor0);
			cs.setString(2, alarmLevelBackgroundColor1);
			cs.setString(3, alarmLevelBackgroundColor2);
			cs.setString(4, alarmLevelBackgroundColor3);
			cs.setString(5, alarmLevelColor0);
			cs.setString(6, alarmLevelColor1);
			cs.setString(7, alarmLevelColor2);
			cs.setString(8, alarmLevelColor3);
			
			cs.setString(9, alarmLevelOpacity0);
			cs.setString(10, alarmLevelOpacity1);
			cs.setString(11, alarmLevelOpacity2);
			cs.setString(12, alarmLevelOpacity3);
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
	
	public Boolean saveOrUpdateorDeleteDiscreteAlarmSet(DistreteAlarmLimit w, String ids, String comandType) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		try {
			cs = conn.prepareCall("{call PRO_T_112_UPDATE_ADD_DELETE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setInt(1, w.getJbh());
			cs.setDouble(2, w.getCurrentamax());
			cs.setDouble(3, w.getCurrentamin());
			cs.setDouble(4, w.getVoltageamax());
			cs.setDouble(5, w.getVoltageamin());
			cs.setDouble(6, w.getActivepoweramax());
			cs.setDouble(7, w.getActivepoweramin());
			
			cs.setDouble(8, w.getCurrentbmax());
			cs.setDouble(9, w.getCurrentbmin());
			cs.setDouble(10, w.getVoltagebmax());
			cs.setDouble(11, w.getVoltagebmin());
			cs.setDouble(12, w.getActivepowerbmax());
			cs.setDouble(13, w.getActivepowerbmin());
			
			cs.setDouble(14, w.getCurrentcmax());
			cs.setDouble(15, w.getCurrentcmin());
			cs.setDouble(16, w.getVoltagecmax());
			cs.setDouble(17, w.getVoltagecmin());
			cs.setDouble(18, w.getActivepowercmax());
			cs.setDouble(19, w.getActivepowercmin());
			
			cs.setDouble(20, w.getCurrentavgmax());
			cs.setDouble(21, w.getCurrentavgmin());
			cs.setDouble(22, w.getVoltageavgmax());
			cs.setDouble(23, w.getVoltageavgmin());
			cs.setDouble(24, w.getActivepowersummax());
			cs.setDouble(25, w.getActivepowersummin());
			
			if (comandType.equalsIgnoreCase("add")) {
			} else if (comandType.equalsIgnoreCase("modify")) {
				ids = "no";
				cs.setInt(26, w.getJlbh());
				cs.setString(27, ids);
			} else if (comandType.equalsIgnoreCase("delete")) {
			}
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
	
	public Boolean saveSurfaceCard(FSDiagramModel FSDiagramModel) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		CLOB sClob=new CLOB((OracleConnection) conn);
		sClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		sClob.putString(1, StringUtils.join(FSDiagramModel.getS(), ","));
		
		CLOB fClob=new CLOB((OracleConnection) conn);
		fClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		fClob.putString(1, StringUtils.join(FSDiagramModel.getF(), ","));
		
		CLOB wattClob=new CLOB((OracleConnection) conn);
		wattClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		wattClob.putString(1, StringUtils.join(FSDiagramModel.getWatt(), ","));
		
		CLOB iClob=new CLOB((OracleConnection) conn);
		iClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		iClob.putString(1, StringUtils.join(FSDiagramModel.getI(), ","));
		
		try {
			cs = conn.prepareCall("{call prd_save_rpc_uploaddiagram(?,?,?,?,?,?,?,?)}");
			cs.setString(1, FSDiagramModel.getWellName());
			cs.setString(2, FSDiagramModel.getAcquisitionTime());
			cs.setFloat(3, FSDiagramModel.getStroke());
			cs.setFloat(4, FSDiagramModel.getSpm());
			cs.setClob(5, sClob);
			cs.setClob(6, fClob);
			cs.setClob(7, wattClob);
			cs.setClob(8, iClob);
			
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
	
	
	public Boolean saveSurfaceCard(WellAcquisitionData wellAcquisitionData) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		StringBuffer gtsjBuff=new StringBuffer();
		SimpleDateFormat dateFormat1=new SimpleDateFormat("yyyyMMdd_HHmmss");
        SimpleDateFormat dateFormat2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String wellname=wellAcquisitionData.getWellName();
		String cjsjstr=wellAcquisitionData.getAcquisitionTime();
		
		Date date=dateFormat2.parse(wellAcquisitionData.getAcquisitionTime());
		String cjsjstr2=dateFormat1.format(date);
		CLOB gtClob=null;
		gtClob=new CLOB((OracleConnection) conn);
		gtClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		if(wellAcquisitionData.getDiagram()!=null){
			gtsjBuff.append(cjsjstr2.split("_")[0]).append("\r\n").append(cjsjstr2.split("_")[1]).append("\r\n");
			gtsjBuff.append(wellAcquisitionData.getDiagram().getS().size()+"\r\n");
			gtsjBuff.append(wellAcquisitionData.getDiagram().getSPM()+"\r\n");
			gtsjBuff.append(wellAcquisitionData.getDiagram().getStroke()+"\r\n");
			for(int i=0;i<wellAcquisitionData.getDiagram().getS().size();i++){
				gtsjBuff.append(wellAcquisitionData.getDiagram().getS().get(i)+"\r\n"+wellAcquisitionData.getDiagram().getF().get(i));
				if(i<wellAcquisitionData.getDiagram().getS().size()-1){
					gtsjBuff.append("\r\n");
				}
			}
			gtClob.putString(1, gtsjBuff.toString());
		}else{
			gtClob.putString(1, "");
		}
		try {
			cs = conn.prepareCall("{call PRO_SAVEACQFSDiagram(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, wellname);
			cs.setString(2,cjsjstr);
			if(wellAcquisitionData.getDiagram()!=null){
				cs.setInt(3, wellAcquisitionData.getDiagram().getAcquisitionCycle());
				cs.setClob(4, gtClob);
				cs.setFloat(5, wellAcquisitionData.getDiagram().getStroke());
				cs.setFloat(6, wellAcquisitionData.getDiagram().getSPM());
				cs.setString(7, null);
				cs.setString(8, null);
				cs.setString(9, StringUtils.join(wellAcquisitionData.getDiagram().getS(), ","));
				cs.setString(10, StringUtils.join(wellAcquisitionData.getDiagram().getF(), ","));
				cs.setString(11, StringUtils.join(wellAcquisitionData.getDiagram().getP(), ","));
				cs.setString(12, StringUtils.join(wellAcquisitionData.getDiagram().getA(), ","));
			}else{
				cs.setInt(3, 0);
				cs.setClob(4, gtClob);
				cs.setFloat(5, 0);
				cs.setFloat(6, 0);
				cs.setString(7, null);
				cs.setString(8, null);
				cs.setString(9, null);
				cs.setString(10, null);
				cs.setString(11, null);
				cs.setString(12, null);
			}
			cs.setString(13, null);
			cs.setString(14, null);
			cs.setString(15, null);
			cs.setString(16, null);
			
			cs.setFloat(17, wellAcquisitionData.getElectric().getCurrentA());
			cs.setFloat(18, wellAcquisitionData.getElectric().getCurrentB());
			cs.setFloat(19, wellAcquisitionData.getElectric().getCurrentC());
			cs.setFloat(20, wellAcquisitionData.getElectric().getVoltageA());
			cs.setFloat(21, wellAcquisitionData.getElectric().getVoltageB());
			cs.setFloat(22, wellAcquisitionData.getElectric().getVoltageC());
			cs.setFloat(23, wellAcquisitionData.getElectric().getActivePowerConsumption());
			cs.setFloat(24, wellAcquisitionData.getElectric().getReactivePowerConsumption());
			cs.setFloat(25, wellAcquisitionData.getElectric().getActivePower());
			cs.setFloat(26, wellAcquisitionData.getElectric().getReactivePower());
			cs.setFloat(27, wellAcquisitionData.getElectric().getReversePower());
			cs.setFloat(28, wellAcquisitionData.getElectric().getPowerFactor());
			cs.setFloat(29, wellAcquisitionData.getProductionParameter().getTubingPressure());
			cs.setFloat(30, wellAcquisitionData.getProductionParameter().getCasingPressure());
			cs.setFloat(31, wellAcquisitionData.getProductionParameter().getBackPressure());
			cs.setFloat(32, wellAcquisitionData.getProductionParameter().getWellHeadFluidTemperature());
			cs.setFloat(33, wellAcquisitionData.getProductionParameter().getBpszpl());
			cs.setFloat(34, wellAcquisitionData.getProductionParameter().getBpyxpl());
			if(wellAcquisitionData.getScrewPump()!=null){
				cs.setFloat(35, wellAcquisitionData.getScrewPump().getRPM());
				cs.setFloat(36, wellAcquisitionData.getScrewPump().getTorque());
			}else{
				cs.setFloat(35, 0);
				cs.setFloat(36, 0);
			}
			
			cs.setString(37, null);
			cs.setString(38, null);
			cs.setString(39, null);
			cs.setString(40, null);
			cs.setString(41, null);
			cs.setString(42, null);
			
			cs.setString(43, null);
			cs.setString(44, null);
			cs.setString(45, null);
			cs.setString(46, null);
			cs.setString(47, null);
			cs.setString(48, null);
			cs.setString(49, null);
			cs.setString(50, null);
			cs.setInt(51, 0);
			
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
	
	
	public Boolean saveSurfaceCard(String wellname,String acquisitionTime,int point,float stroke,float spm,String sStr,String fStr,String iStr,String wattStr) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		CLOB sClob=new CLOB((OracleConnection) conn);
		sClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		sClob.putString(1, sStr);
		
		CLOB fClob=new CLOB((OracleConnection) conn);
		fClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		fClob.putString(1, fStr);
		
		CLOB wattClob=new CLOB((OracleConnection) conn);
		wattClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		wattClob.putString(1, wattStr);
		
		CLOB iClob=new CLOB((OracleConnection) conn);
		iClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		iClob.putString(1, iStr);
		
		try {
			cs = conn.prepareCall("{call prd_save_rpc_uploaddiagram(?,?,?,?,?,?,?,?)}");
			cs.setString(1, wellname);
			cs.setString(2, acquisitionTime);
			cs.setFloat(3, stroke);
			cs.setFloat(4, spm);
			cs.setClob(5, sClob);
			cs.setClob(6, fClob);
			cs.setClob(7, wattClob);
			cs.setClob(8, iClob);
			
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
	
	public Boolean saveSurfaceCard(String wellname,String acquisitionTime,int point,float stroke,float SPM,
			float F_Max,float F_Min,
			String SStr,String FStr,
			String S360Str,String A360Str,String F360Str,
			String AStr,String PStr,String RPMStr,
			float UpstrokeIMax,float DownstrokeIMax,float UpstrokeWattMax,float DownstrokeWattMax,float IDegreeBalance,float WattDegreeBalance,
			String AStr_RAW,String PStr_RAW,String RPMStr_RAW,
			float UpstrokeIMax_RAW,float DownstrokeIMax_RAW,float UpstrokeWattMax_RAW,float DownstrokeWattMax_RAW,float IDegreeBalance_RAW,float WattDegreeBalance_RAW,
			float MotorInputAvgWatt,
			int ResultStatus,
			float Signal,int Interval,String Ver) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		CLOB diagramClob_S=new CLOB((OracleConnection) conn);
		diagramClob_S = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_S.putString(1, SStr);
		
		CLOB diagramClob_F=new CLOB((OracleConnection) conn);
		diagramClob_F = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_F.putString(1, FStr);
		
		CLOB diagramClob_S360=new CLOB((OracleConnection) conn);
		diagramClob_S360 = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_S360.putString(1, S360Str);
		
		CLOB diagramClob_F360=new CLOB((OracleConnection) conn);
		diagramClob_F360 = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_F360.putString(1, F360Str);
		
		CLOB diagramClob_A360=new CLOB((OracleConnection) conn);
		diagramClob_A360 = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_A360.putString(1, A360Str);
		
		CLOB diagramClob_P=new CLOB((OracleConnection) conn);
		diagramClob_P = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_P.putString(1, PStr);
		
		CLOB diagramClob_I=new CLOB((OracleConnection) conn);
		diagramClob_I = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_I.putString(1, AStr);
		
		CLOB diagramClob_RPM=new CLOB((OracleConnection) conn);
		diagramClob_RPM = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_RPM.putString(1, RPMStr);
		
		CLOB diagramClob_P_RAW=new CLOB((OracleConnection) conn);
		diagramClob_P_RAW = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_P_RAW.putString(1, PStr_RAW);
		
		CLOB diagramClob_I_RAW=new CLOB((OracleConnection) conn);
		diagramClob_I_RAW = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_I_RAW.putString(1, AStr_RAW);
		
		CLOB diagramClob_RPM_RAW=new CLOB((OracleConnection) conn);
		diagramClob_RPM_RAW = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_RPM_RAW.putString(1, RPMStr_RAW);
		
		CLOB nullClob=new CLOB((OracleConnection) conn);
		nullClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		nullClob.putString(1, "");
		
		
		try {
			cs = conn.prepareCall("{call prd_save_rpc_diagram("
					+ "?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,"
					+ "?,"
					+ "?,?,?,"
					+ "?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,"
					+ "?)}");
			cs.setString(1, wellname);
			cs.setString(2,acquisitionTime);
			cs.setFloat(3,stroke);
			cs.setFloat(4,SPM);
			
			cs.setClob(5,diagramClob_S);
			cs.setClob(6,diagramClob_F);
			cs.setClob(7,diagramClob_S360);
			cs.setClob(8,diagramClob_A360);
			cs.setClob(9,diagramClob_F360);
			cs.setClob(10,diagramClob_P);
			cs.setClob(11,diagramClob_I);
			cs.setClob(12,diagramClob_RPM);
			cs.setClob(13,diagramClob_P_RAW);
			cs.setClob(14,diagramClob_I_RAW);
			cs.setClob(15,diagramClob_RPM_RAW);
			
			cs.setInt(16,1);//功图来源 0-采集 1-电参反演 2-人工上传
			cs.setInt(17,0);//生产数据Id
			cs.setInt(18,0);//计算标志
			cs.setInt(19, ResultStatus);
			
			//最大最小载荷
			cs.setFloat(20,F_Max);
			cs.setFloat(21,F_Min);
			//平衡
			cs.setFloat(22,UpstrokeIMax);
			cs.setFloat(23,DownstrokeIMax);
			cs.setFloat(24,UpstrokeWattMax);
			cs.setFloat(25,DownstrokeWattMax);
			cs.setFloat(26,IDegreeBalance);
			cs.setFloat(27,WattDegreeBalance);
			
			cs.setString(28,"");
			cs.setString(29,"");
			cs.setString(30,"");
			cs.setString(31,"");
			cs.setString(32,"");
			cs.setClob(33,nullClob);//泵功图
			//产量
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
			cs.setString(44,"");
			cs.setString(45,"");
			cs.setString(46,"");
			cs.setString(47,"");
			cs.setString(48,"");
			cs.setString(49,"");
			cs.setString(50,"");
			//系统效率
			cs.setString(51,"");
			cs.setString(52,"");
			cs.setString(53,"");
			cs.setString(54,"");
			cs.setString(55,"");
			cs.setString(56,"");
			cs.setString(57,"");
			cs.setString(58,"");
			//泵效
			cs.setString(59,"");
			cs.setString(60,"");
			cs.setString(61,"");
			cs.setString(62,"");
			cs.setString(63,"");
			cs.setString(64,"");
			cs.setString(65,"");
			cs.setString(66,"");
			//泵入口出口参数
			cs.setString(67,"");
			cs.setString(68,"");
			cs.setString(69,"");
			cs.setString(70,"");
			cs.setString(71,"");
			cs.setString(72,"");
			cs.setString(73,"");
			cs.setString(74,"");
			cs.setString(75,"");
			cs.setString(76,"");
			//杆参数
			cs.setString(77,"");
			
			cs.setFloat(78,Signal);
			cs.setInt(79,Interval);
			cs.setString(80,Ver);
			
			
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
	
	public Boolean saveFSDiagramAndCalculateData(WellAcquisitionData wellAcquisitionData,CalculateResponseData calculateResponseData) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		CLOB diagramClob_S=new CLOB((OracleConnection) conn);
		diagramClob_S = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_S.putString(1, StringUtils.join(wellAcquisitionData.getDiagram().getS(), ","));
		
		CLOB diagramClob_F=new CLOB((OracleConnection) conn);
		diagramClob_F = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_F.putString(1, StringUtils.join(wellAcquisitionData.getDiagram().getF(), ","));
		
		CLOB diagramClob_P=new CLOB((OracleConnection) conn);
		diagramClob_P = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_P.putString(1, StringUtils.join(wellAcquisitionData.getDiagram().getP(), ","));
		
		CLOB diagramClob_I=new CLOB((OracleConnection) conn);
		diagramClob_I = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_I.putString(1, StringUtils.join(wellAcquisitionData.getDiagram().getA(), ","));
		
		CLOB nullClob=new CLOB((OracleConnection) conn);
		nullClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		nullClob.putString(1, "");
		
		StringBuffer bgtStrBuff = new StringBuffer();
		if(calculateResponseData!=null&&calculateResponseData.getCalculationStatus().getResultStatus()==1&&calculateResponseData.getFSDiagram()!=null){
			int curvecount=calculateResponseData.getFSDiagram().getS().get(0).size();
			int pointcount=calculateResponseData.getFSDiagram().getS().size();
			bgtStrBuff.append(curvecount+";"+pointcount+";");
			for(int i=0;i<curvecount;i++){
				for(int j=0;j<pointcount;j++){
					bgtStrBuff.append(calculateResponseData.getFSDiagram().getS().get(j).get(i)+",");//位移
					bgtStrBuff.append(calculateResponseData.getFSDiagram().getF().get(j).get(i)+",");//载荷
				}
				if(pointcount>0){
					bgtStrBuff.deleteCharAt(bgtStrBuff.length() - 1);
				}
				bgtStrBuff.append(";");
			}
			if(curvecount>0){
				bgtStrBuff.deleteCharAt(bgtStrBuff.length() - 1);
			}
		}
		CLOB pumpFSDiagramClob=new CLOB((OracleConnection) conn);
		pumpFSDiagramClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		pumpFSDiagramClob.putString(1, bgtStrBuff.toString());
		
		try {
			cs = conn.prepareCall("{call prd_save_rpc_diagram("
					+ "?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,"
					+ "?,"
					+ "?,?,?,"
					+ "?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,"
					+ "?)}");
			cs.setString(1, wellAcquisitionData.getWellName());
			cs.setString(2,wellAcquisitionData.getAcquisitionTime());
			cs.setString(3,wellAcquisitionData.getDiagram().getStroke()+"");
			cs.setString(4,wellAcquisitionData.getDiagram().getSPM()+"");
			
			cs.setClob(5,diagramClob_S);
			cs.setClob(6,diagramClob_F);
			cs.setClob(7,nullClob);
			cs.setClob(8,nullClob);
			cs.setClob(9,nullClob);
			cs.setClob(10,diagramClob_P);
			cs.setClob(11,diagramClob_I);
			cs.setClob(12,nullClob);
			cs.setClob(13,nullClob);
			cs.setClob(14,nullClob);
			cs.setClob(15,nullClob);
			
			cs.setInt(16,0);//功图来源 0-采集 1-电参反演 2-人工上传
			cs.setInt(17,wellAcquisitionData.getProdDataId());//生产数据Id
			cs.setInt(18,calculateResponseData==null?0:calculateResponseData.getCalculationStatus().getResultStatus());//计算标志
			cs.setInt(19, 0);//功图反演标志
			if(calculateResponseData!=null&&calculateResponseData.getCalculationStatus().getResultStatus()==1){//如果计算成功
				//最大最小载荷
				cs.setFloat(20,calculateResponseData.getFSDiagram().getFMax().get(0));
				cs.setFloat(21,calculateResponseData.getFSDiagram().getFMin().get(0));
				//平衡
				cs.setFloat(22,calculateResponseData.getFSDiagram().getUpStrokeIMax());
				cs.setFloat(23,calculateResponseData.getFSDiagram().getDownStrokeIMax());
				cs.setFloat(24,calculateResponseData.getFSDiagram().getUpStrokeWattMax());
				cs.setFloat(25,calculateResponseData.getFSDiagram().getDownStrokeWattMax());
				cs.setFloat(26,calculateResponseData.getFSDiagram().getIDegreeBalance());
				cs.setFloat(27,calculateResponseData.getFSDiagram().getWattDegreeBalance());
				//工况代码
				cs.setInt(28,calculateResponseData.getCalculationStatus().getResultCode());
				//充满系数
				cs.setFloat(29,calculateResponseData.getFSDiagram().getFullnessCoefficient());
				//上下理论载荷线
				cs.setFloat(30,calculateResponseData.getFSDiagram().getUpperLoadLine());
				cs.setFloat(31,calculateResponseData.getFSDiagram().getUpperLoadLineOfExact());
				cs.setFloat(32,calculateResponseData.getFSDiagram().getLowerLoadLine());
				//泵功图
				cs.setClob(33,pumpFSDiagramClob);
				//产量
				cs.setFloat(34,calculateResponseData.getProductionParameter().getTheoreticalProduction());
				cs.setFloat(35,calculateResponseData.getProductionParameter().getLiquidVolumetricProduction());
				cs.setFloat(36,calculateResponseData.getProductionParameter().getOilVolumetricProduction());
				cs.setFloat(37,calculateResponseData.getProductionParameter().getWaterVolumetricProduction());
				cs.setFloat(38,calculateResponseData.getProductionParameter().getAvailablePlungerStrokeVolumetricProduction());
				cs.setFloat(39,calculateResponseData.getProductionParameter().getPumpClearanceLeakVolumetricProduction());
				cs.setFloat(40,calculateResponseData.getProductionParameter().getTVLeakVolumetricProduction());
				cs.setFloat(41,calculateResponseData.getProductionParameter().getSVLeakVolumetricProduction());
				cs.setFloat(42,calculateResponseData.getProductionParameter().getGasInfluenceVolumetricProduction());
				cs.setFloat(43,calculateResponseData.getProductionParameter().getLiquidWeightProduction());
				cs.setFloat(44,calculateResponseData.getProductionParameter().getOilWeightProduction());
				cs.setFloat(45,calculateResponseData.getProductionParameter().getWaterWeightProduction());
				cs.setFloat(46,calculateResponseData.getProductionParameter().getAvailablePlungerStrokeWeightProduction());
				cs.setFloat(47,calculateResponseData.getProductionParameter().getPumpClearanceLeakWeightProduction());
				cs.setFloat(48,calculateResponseData.getProductionParameter().getTVLeakWeightProduction());
				cs.setFloat(49,calculateResponseData.getProductionParameter().getSVLeakWeightProduction());
				cs.setFloat(50,calculateResponseData.getProductionParameter().getGasInfluenceWeightProduction());
				//系统效率
				cs.setFloat(51,calculateResponseData.getSystemEfficiency().getMotorInputActivePower());
				cs.setFloat(52,calculateResponseData.getSystemEfficiency().getPolishRodPower());
				cs.setFloat(53,calculateResponseData.getSystemEfficiency().getWaterPower());
				cs.setFloat(54,calculateResponseData.getSystemEfficiency().getSurfaceSystemEfficiency());
				cs.setFloat(55,calculateResponseData.getSystemEfficiency().getWellDownSystemEfficiency());
				cs.setFloat(56,calculateResponseData.getSystemEfficiency().getSystemEfficiency());
				cs.setFloat(57,calculateResponseData.getSystemEfficiency().getPowerConsumptionPerTHM());
				cs.setFloat(58,calculateResponseData.getFSDiagram().getArea());
				//泵效
				cs.setFloat(59,calculateResponseData.getPumpEfficiency().getRodFlexLength());
				cs.setFloat(60,calculateResponseData.getPumpEfficiency().getTubingFlexLength());
				cs.setFloat(61,calculateResponseData.getPumpEfficiency().getInertiaLength());
				cs.setFloat(62,calculateResponseData.getPumpEfficiency().getPumpEff1());
				cs.setFloat(63,calculateResponseData.getPumpEfficiency().getPumpEff2());
				cs.setFloat(64,calculateResponseData.getPumpEfficiency().getPumpEff3());
				cs.setFloat(65,calculateResponseData.getPumpEfficiency().getPumpEff4());
				cs.setFloat(66,calculateResponseData.getPumpEfficiency().getPumpEff());
				//泵入口出口参数
				cs.setFloat(67,calculateResponseData.getProductionParameter().getPumpIntakeP());
				cs.setFloat(68,calculateResponseData.getProductionParameter().getPumpIntakeT());
				cs.setFloat(69,calculateResponseData.getProductionParameter().getPumpIntakeGOL());
				cs.setFloat(70,calculateResponseData.getProductionParameter().getPumpIntakeVisl());
				cs.setFloat(71,calculateResponseData.getProductionParameter().getPumpIntakeBo());
				cs.setFloat(72,calculateResponseData.getProductionParameter().getPumpOutletP());
				cs.setFloat(73,calculateResponseData.getProductionParameter().getPumpOutletT());
				cs.setFloat(74,calculateResponseData.getProductionParameter().getPumpOutletGOL());
				cs.setFloat(75,calculateResponseData.getProductionParameter().getPumpOutletVisl());
				cs.setFloat(76,calculateResponseData.getProductionParameter().getPumpOutletBo());
				//杆参数
				cs.setString(77,calculateResponseData.getRodCalData());
			}else{
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
				cs.setClob(33,nullClob);//泵功图
				//产量
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
				cs.setString(44,"");
				cs.setString(45,"");
				cs.setString(46,"");
				cs.setString(47,"");
				cs.setString(48,"");
				cs.setString(49,"");
				cs.setString(50,"");
				//系统效率
				cs.setString(51,"");
				cs.setString(52,"");
				cs.setString(53,"");
				cs.setString(54,"");
				cs.setString(55,"");
				cs.setString(56,"");
				cs.setString(57,"");
				cs.setString(58,"");
				//泵效、
				cs.setString(59,"");
				cs.setString(60,"");
				cs.setString(61,"");
				cs.setString(62,"");
				cs.setString(63,"");
				cs.setString(64,"");
				cs.setString(65,"");
				cs.setString(66,"");
				//泵入口出口参数
				cs.setString(67,"");
				cs.setString(68,"");
				cs.setString(69,"");
				cs.setString(70,"");
				cs.setString(71,"");
				cs.setString(72,"");
				cs.setString(73,"");
				cs.setString(74,"");
				cs.setString(75,"");
				cs.setString(76,"");
				//杆参数
				cs.setString(77,"");
			}
			cs.setString(78,"");
			cs.setString(79,"");
			cs.setString(80,"");
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
	
	public Boolean saveScrewPumpRPMAndCalculateData(WellAcquisitionData wellAcquisitionData,CalculateResponseData calculateResponseData) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		try {
			cs = conn.prepareCall("{call prd_save_pcp_rpm("
					+ "?,?,"
					+ "?,?,"
					+ "?,?"
					+ "?,"
					+ "?,?,?,?,?,?,?,"
					+ "?,?,?,?"
					+ "?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?)}");
			cs.setString(1, wellAcquisitionData.getWellName());
			cs.setString(2,wellAcquisitionData.getAcquisitionTime());
			
			cs.setFloat(3,wellAcquisitionData.getScrewPump().getRPM());
			cs.setFloat(4,wellAcquisitionData.getScrewPump().getTorque());
			
			cs.setInt(5,wellAcquisitionData.getProdDataId());//生产数据Id
			cs.setInt(6,calculateResponseData==null?0:calculateResponseData.getCalculationStatus().getResultStatus());//计算标志
			if(calculateResponseData!=null&&calculateResponseData.getCalculationStatus().getResultStatus()==1){//如果计算成功
				//工况代码
				cs.setInt(7,calculateResponseData.getCalculationStatus().getResultCode());
				//产量
				cs.setFloat(8,calculateResponseData.getProductionParameter().getTheoreticalProduction());
				cs.setFloat(9,calculateResponseData.getProductionParameter().getLiquidVolumetricProduction());
				cs.setFloat(10,calculateResponseData.getProductionParameter().getOilVolumetricProduction());
				cs.setFloat(11,calculateResponseData.getProductionParameter().getWaterVolumetricProduction());
				cs.setFloat(12,calculateResponseData.getProductionParameter().getLiquidWeightProduction());
				cs.setFloat(13,calculateResponseData.getProductionParameter().getOilWeightProduction());
				cs.setFloat(14,calculateResponseData.getProductionParameter().getWaterWeightProduction());
				//系统效率
				cs.setFloat(15,calculateResponseData.getSystemEfficiency().getMotorInputActivePower());
				cs.setFloat(16,calculateResponseData.getSystemEfficiency().getWaterPower());
				cs.setFloat(17,calculateResponseData.getSystemEfficiency().getSystemEfficiency());
				cs.setFloat(18,calculateResponseData.getSystemEfficiency().getPowerConsumptionPerTHM());
				//泵效
				cs.setFloat(19,calculateResponseData.getPumpEfficiency().getPumpEff1());
				cs.setFloat(20,calculateResponseData.getPumpEfficiency().getPumpEff2());
				cs.setFloat(21,calculateResponseData.getPumpEfficiency().getPumpEff());
				//泵入口出口参数
				cs.setFloat(22,calculateResponseData.getProductionParameter().getPumpIntakeP());
				cs.setFloat(23,calculateResponseData.getProductionParameter().getPumpIntakeT());
				cs.setFloat(24,calculateResponseData.getProductionParameter().getPumpIntakeGOL());
				cs.setFloat(25,calculateResponseData.getProductionParameter().getPumpIntakeVisl());
				cs.setFloat(26,calculateResponseData.getProductionParameter().getPumpIntakeBo());
				cs.setFloat(27,calculateResponseData.getProductionParameter().getPumpOutletP());
				cs.setFloat(28,calculateResponseData.getProductionParameter().getPumpOutletT());
				cs.setFloat(29,calculateResponseData.getProductionParameter().getPumpOutletGOL());
				cs.setFloat(30,calculateResponseData.getProductionParameter().getPumpOutletVisl());
				cs.setFloat(31,calculateResponseData.getProductionParameter().getPumpOutletBo());
				//杆参数
				cs.setString(32,calculateResponseData.getRodCalData());
			}else{
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
	
	public Boolean reInverDiagram(String recordId,String cjsjstr,int point,float stroke,float frequency,
			float F_Max,float F_Min,
			String SStr,String FStr,
			String S360Str,String A360Str,String F360Str,
			String IStr,String PStr,String RPMStr,
			float UpstrokeIMax,float DownstrokeIMax,float UpstrokeWattMax,float DownstrokeWattMax,float IDegreeBalance,float WattDegreeBalance,
			int ResultStatus) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		CLOB diagramClob_S=new CLOB((OracleConnection) conn);
		diagramClob_S = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_S.putString(1, SStr);
		
		CLOB diagramClob_F=new CLOB((OracleConnection) conn);
		diagramClob_F = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_F.putString(1, FStr);
		
		CLOB diagramClob_S360=new CLOB((OracleConnection) conn);
		diagramClob_S360 = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_S360.putString(1, S360Str);
		
		CLOB diagramClob_F360=new CLOB((OracleConnection) conn);
		diagramClob_F360 = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_F360.putString(1, F360Str);
		
		CLOB diagramClob_A360=new CLOB((OracleConnection) conn);
		diagramClob_A360 = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_A360.putString(1, A360Str);
		
		CLOB diagramClob_P=new CLOB((OracleConnection) conn);
		diagramClob_P = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_P.putString(1, PStr);
		
		CLOB diagramClob_I=new CLOB((OracleConnection) conn);
		diagramClob_I = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_I.putString(1, IStr);
		
		CLOB diagramClob_RPM=new CLOB((OracleConnection) conn);
		diagramClob_RPM = oracle.sql.CLOB.createTemporary(conn,false,1);
		diagramClob_RPM.putString(1, RPMStr);
		
		try {
			cs = conn.prepareCall("{call prd_save_rpc_reinverdiagram(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, recordId);
			cs.setFloat(2, F_Max);
			cs.setFloat(3, F_Min);
			
			cs.setClob(4, diagramClob_S);
			cs.setClob(5, diagramClob_F);
			cs.setClob(6, diagramClob_S360);
			cs.setClob(7, diagramClob_A360);
			cs.setClob(8, diagramClob_F360);
			cs.setClob(9, diagramClob_P);
			cs.setClob(10, diagramClob_I);
			cs.setClob(11, diagramClob_RPM);


			cs.setFloat(12, UpstrokeIMax);
			cs.setFloat(13, DownstrokeIMax);
			cs.setFloat(14, UpstrokeWattMax);
			cs.setFloat(15, DownstrokeWattMax);
			cs.setFloat(16, IDegreeBalance);
			cs.setFloat(17, WattDegreeBalance);
			
			cs.setInt(18, ResultStatus);
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
	
	public Boolean saveCalculateResult(int id,CalculateResponseData calculateResponseData) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		StringBuffer pumpFSDiagramStrBuff = new StringBuffer();
		if(calculateResponseData.getCalculationStatus().getResultStatus()==1){
			int curvecount=calculateResponseData.getFSDiagram().getS().get(0).size();
			int pointcount=calculateResponseData.getFSDiagram().getS().size();
			pumpFSDiagramStrBuff.append(curvecount+";"+pointcount+";");
			for(int i=0;i<curvecount;i++){
				for(int j=0;j<pointcount;j++){
					pumpFSDiagramStrBuff.append(calculateResponseData.getFSDiagram().getS().get(j).get(i)+",");//位移
					pumpFSDiagramStrBuff.append(calculateResponseData.getFSDiagram().getF().get(j).get(i)+",");//载荷
				}
				if(pumpFSDiagramStrBuff.toString().endsWith(",")){
					pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
				}
				pumpFSDiagramStrBuff.append(";");
			}
			if(pumpFSDiagramStrBuff.toString().endsWith(";")){
				pumpFSDiagramStrBuff.deleteCharAt(pumpFSDiagramStrBuff.length() - 1);
			}
		}
		CLOB pumpFSDiagramClob=new CLOB((OracleConnection) conn);
		pumpFSDiagramClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		pumpFSDiagramClob.putString(1, pumpFSDiagramStrBuff.toString());
		
		CLOB nullClob=new CLOB((OracleConnection) conn);
		nullClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		nullClob.putString(1, "");
		
		try {
			cs = conn.prepareCall("{call prd_save_rpc_diagramresult("
					+ "?,"
					+ "?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,"
					+ "?,"
					+ "?,?,?,"
					+ "?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,"
					+ "?)}");
			
			cs.setInt(1,calculateResponseData==null?0:calculateResponseData.getCalculationStatus().getResultStatus());//计算标志
			if(calculateResponseData!=null&&calculateResponseData.getCalculationStatus().getResultStatus()==1){//如果计算成功
				//最大最小载荷
				cs.setFloat(2,calculateResponseData.getFSDiagram().getFMax().get(0));
				cs.setFloat(3,calculateResponseData.getFSDiagram().getFMin().get(0));
				//平衡
				cs.setFloat(4,calculateResponseData.getFSDiagram().getUpStrokeIMax());
				cs.setFloat(5,calculateResponseData.getFSDiagram().getDownStrokeIMax());
				cs.setFloat(6,calculateResponseData.getFSDiagram().getUpStrokeWattMax());
				cs.setFloat(7,calculateResponseData.getFSDiagram().getDownStrokeWattMax());
				cs.setFloat(8,calculateResponseData.getFSDiagram().getIDegreeBalance());
				cs.setFloat(9,calculateResponseData.getFSDiagram().getWattDegreeBalance());
				//工况代码
				cs.setInt(10,calculateResponseData.getCalculationStatus().getResultCode());
				//充满系数
				cs.setFloat(11,calculateResponseData.getFSDiagram().getFullnessCoefficient());
				//上下理论载荷线
				cs.setFloat(12,calculateResponseData.getFSDiagram().getUpperLoadLine());
				cs.setFloat(13,calculateResponseData.getFSDiagram().getUpperLoadLineOfExact());
				cs.setFloat(14,calculateResponseData.getFSDiagram().getLowerLoadLine());
				//泵功图
				cs.setClob(15,pumpFSDiagramClob);
				//产量
				cs.setFloat(16,calculateResponseData.getProductionParameter().getTheoreticalProduction());
				cs.setFloat(17,calculateResponseData.getProductionParameter().getLiquidVolumetricProduction());
				cs.setFloat(18,calculateResponseData.getProductionParameter().getOilVolumetricProduction());
				cs.setFloat(19,calculateResponseData.getProductionParameter().getWaterVolumetricProduction());
				cs.setFloat(20,calculateResponseData.getProductionParameter().getAvailablePlungerStrokeVolumetricProduction());
				cs.setFloat(21,calculateResponseData.getProductionParameter().getPumpClearanceLeakVolumetricProduction());
				cs.setFloat(22,calculateResponseData.getProductionParameter().getTVLeakVolumetricProduction());
				cs.setFloat(23,calculateResponseData.getProductionParameter().getSVLeakVolumetricProduction());
				cs.setFloat(24,calculateResponseData.getProductionParameter().getGasInfluenceVolumetricProduction());
				cs.setFloat(25,calculateResponseData.getProductionParameter().getLiquidWeightProduction());
				cs.setFloat(26,calculateResponseData.getProductionParameter().getOilWeightProduction());
				cs.setFloat(27,calculateResponseData.getProductionParameter().getWaterWeightProduction());
				cs.setFloat(28,calculateResponseData.getProductionParameter().getAvailablePlungerStrokeWeightProduction());
				cs.setFloat(29,calculateResponseData.getProductionParameter().getPumpClearanceLeakWeightProduction());
				cs.setFloat(30,calculateResponseData.getProductionParameter().getTVLeakWeightProduction());
				cs.setFloat(31,calculateResponseData.getProductionParameter().getSVLeakWeightProduction());
				cs.setFloat(32,calculateResponseData.getProductionParameter().getGasInfluenceWeightProduction());
				//系统效率
				cs.setFloat(33,calculateResponseData.getSystemEfficiency().getMotorInputActivePower());
				cs.setFloat(34,calculateResponseData.getSystemEfficiency().getPolishRodPower());
				cs.setFloat(35,calculateResponseData.getSystemEfficiency().getWaterPower());
				cs.setFloat(36,calculateResponseData.getSystemEfficiency().getSurfaceSystemEfficiency());
				cs.setFloat(37,calculateResponseData.getSystemEfficiency().getWellDownSystemEfficiency());
				cs.setFloat(38,calculateResponseData.getSystemEfficiency().getSystemEfficiency());
				cs.setFloat(39,calculateResponseData.getSystemEfficiency().getPowerConsumptionPerTHM());
				cs.setFloat(40,calculateResponseData.getFSDiagram().getArea());
				//泵效
				cs.setFloat(41,calculateResponseData.getPumpEfficiency().getRodFlexLength());
				cs.setFloat(42,calculateResponseData.getPumpEfficiency().getTubingFlexLength());
				cs.setFloat(43,calculateResponseData.getPumpEfficiency().getInertiaLength());
				cs.setFloat(44,calculateResponseData.getPumpEfficiency().getPumpEff1());
				cs.setFloat(45,calculateResponseData.getPumpEfficiency().getPumpEff2());
				cs.setFloat(46,calculateResponseData.getPumpEfficiency().getPumpEff3());
				cs.setFloat(47,calculateResponseData.getPumpEfficiency().getPumpEff4());
				cs.setFloat(48,calculateResponseData.getPumpEfficiency().getPumpEff());
				//泵入口出口参数
				cs.setFloat(49,calculateResponseData.getProductionParameter().getPumpIntakeP());
				cs.setFloat(50,calculateResponseData.getProductionParameter().getPumpIntakeT());
				cs.setFloat(51,calculateResponseData.getProductionParameter().getPumpIntakeGOL());
				cs.setFloat(52,calculateResponseData.getProductionParameter().getPumpIntakeVisl());
				cs.setFloat(53,calculateResponseData.getProductionParameter().getPumpIntakeBo());
				cs.setFloat(54,calculateResponseData.getProductionParameter().getPumpOutletP());
				cs.setFloat(55,calculateResponseData.getProductionParameter().getPumpOutletT());
				cs.setFloat(56,calculateResponseData.getProductionParameter().getPumpOutletGOL());
				cs.setFloat(57,calculateResponseData.getProductionParameter().getPumpOutletVisl());
				cs.setFloat(58,calculateResponseData.getProductionParameter().getPumpOutletBo());
				//杆参数
				cs.setString(59,calculateResponseData.getRodCalData());
			}else{
				cs.setString(2,"");
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
				cs.setClob(15,nullClob);//泵功图
				//产量
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
				//系统效率
				cs.setString(33,"");
				cs.setString(34,"");
				cs.setString(35,"");
				cs.setString(36,"");
				cs.setString(37,"");
				cs.setString(38,"");
				cs.setString(39,"");
				cs.setString(40,"");
				//泵效、
				cs.setString(41,"");
				cs.setString(42,"");
				cs.setString(43,"");
				cs.setString(44,"");
				cs.setString(45,"");
				cs.setString(46,"");
				cs.setString(47,"");
				cs.setString(48,"");
				//泵入口出口参数
				cs.setString(49,"");
				cs.setString(50,"");
				cs.setString(51,"");
				cs.setString(52,"");
				cs.setString(53,"");
				cs.setString(54,"");
				cs.setString(55,"");
				cs.setString(56,"");
				cs.setString(57,"");
				cs.setString(58,"");
				//杆参数
				cs.setString(59,"");
			}
			cs.setInt(60,id);
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
	
	public Boolean saveCalculateResult(int jlbh,CalculateResponseData calculateResponseData,
			ElectricCalculateResponseData electricCalculateResponseData,TimeEffResponseData timeEffResponseData,CommResponseData commResponseData,
			String bgtStr,String jh) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		CLOB bgtClob=new CLOB((OracleConnection) conn);
		bgtClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		bgtClob.putString(1, bgtStr);
		try {
			cs = conn.prepareCall("{call PRO_SAVECALCULATEDATA(?,"//1
					+ "?,?,?,?,?,?,?,?,?,"                              //9
					+ "?,?,"                                            //2
					+ "?,?,?,?,?,?,?,?,?,"                              //9
					+ "?,?,?,?,?,?,?,?,"                                //8
					+ "?,?,?,?,?,?,?,?,"                                //8
					+ "?,?,?,?,?,?,?,?,"                                //8
					+ "?,?,?,?,?,?,?,"                                  //7
					+ "?,?,?,?,?,?,?,?,?,"                              //9
					+ "?,?,?,?,?,?,"                                    //6
					+ "?,?,?,?,?,?,"                                    //6
					+ "?,?,?,?,?,?,"                                    //6
					+ "?,?,?,?,"                                        //4
					+ "?,?,?,?,?,?,?,?,?,"                              //9
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"    //22
					+ "?,?,?,?,?,"                                      //4
					+ "?,?,?,"                                          //3
					+ "?)}");                                           //1
			cs.setInt(1, jlbh);
			
			cs.setFloat(2, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getUpperLoadLine());
			cs.setFloat(3, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getUpperLoadLineOfExact());
			cs.setFloat(4, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getLowerLoadLine());
			cs.setFloat(5, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getStroke());
			cs.setFloat(6, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getSPM());
			if(calculateResponseData.getCalculationStatus().getResultStatus()==1&&calculateResponseData.getFSDiagram()!=null&&calculateResponseData.getFSDiagram().getFMax()!=null&&calculateResponseData.getFSDiagram().getFMax().get(0)!=null ){
				cs.setFloat(7, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getFMax().get(0));
				cs.setFloat(8, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getFMin().get(0));
			}else{
				cs.setFloat(7, 0);
				cs.setFloat(8, 0);
			}
			cs.setFloat(9, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getFullnessCoefficient());
			cs.setFloat(10, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getArea());
			
			cs.setClob(11,bgtClob );
			cs.setInt(12, calculateResponseData.getCalculationStatus().getResultCode());
			
			cs.setFloat(13, calculateResponseData.getProductionParameter().getTheoreticalProduction());
			cs.setFloat(14, calculateResponseData.getProductionParameter().getLiquidVolumetricProduction());
			cs.setFloat(15, calculateResponseData.getProductionParameter().getLiquidWeightProduction());
			cs.setFloat(16, calculateResponseData.getProductionParameter().getOilVolumetricProduction());
			cs.setFloat(17, calculateResponseData.getProductionParameter().getOilWeightProduction());
			cs.setFloat(18, calculateResponseData.getProductionParameter().getWaterVolumetricProduction());
			cs.setFloat(19, calculateResponseData.getProductionParameter().getWaterWeightProduction());
			cs.setFloat(20, calculateResponseData.getProductionParameter().getAvailablePlungerStrokeVolumetricProduction());
			cs.setFloat(21, calculateResponseData.getProductionParameter().getAvailablePlungerStrokeWeightProduction());
			
			cs.setFloat(22, calculateResponseData.getProductionParameter().getPumpClearanceLeakVolumetricProduction());
			cs.setFloat(23, calculateResponseData.getProductionParameter().getPumpClearanceLeakWeightProduction());
			cs.setFloat(24, calculateResponseData.getProductionParameter().getTVLeakVolumetricProduction());
			cs.setFloat(25, calculateResponseData.getProductionParameter().getTVLeakWeightProduction());
			cs.setFloat(26, calculateResponseData.getProductionParameter().getSVLeakVolumetricProduction());
			cs.setFloat(27, calculateResponseData.getProductionParameter().getSVLeakWeightProduction());
			cs.setFloat(28, calculateResponseData.getProductionParameter().getGasInfluenceVolumetricProduction());
			cs.setFloat(29, calculateResponseData.getProductionParameter().getGasInfluenceWeightProduction());
			
			cs.setFloat(30, calculateResponseData.getProductionParameter().getPumpOutletBo());
			cs.setFloat(31, calculateResponseData.getSystemEfficiency().getMotorInputActivePower());
			cs.setFloat(32, calculateResponseData.getSystemEfficiency().getPolishRodPower());
			cs.setFloat(33, calculateResponseData.getSystemEfficiency().getWaterPower());
			cs.setFloat(34, calculateResponseData.getSystemEfficiency().getSurfaceSystemEfficiency());
			cs.setFloat(35, calculateResponseData.getSystemEfficiency().getWellDownSystemEfficiency());
			cs.setFloat(36, calculateResponseData.getSystemEfficiency().getSystemEfficiency());
			cs.setFloat(37, calculateResponseData.getSystemEfficiency().getPowerConsumptionPerTHM());
			
			cs.setFloat(38, calculateResponseData.getPumpEfficiency().getPumpEff1());
			cs.setFloat(39, calculateResponseData.getPumpEfficiency().getRodFlexLength());
			cs.setFloat(40, calculateResponseData.getPumpEfficiency().getTubingFlexLength());
			cs.setFloat(41, calculateResponseData.getPumpEfficiency().getInertiaLength());
			cs.setFloat(42, calculateResponseData.getPumpEfficiency().getPumpEff4());
			cs.setFloat(43, calculateResponseData.getPumpEfficiency().getPumpEff3());
			cs.setFloat(44, calculateResponseData.getPumpEfficiency().getPumpEff2());
			cs.setFloat(45, calculateResponseData.getPumpEfficiency().getPumpEff());
			

			cs.setFloat(46, calculateResponseData.getProductionParameter().getPumpIntakeP());
			cs.setFloat(47, calculateResponseData.getProductionParameter().getPumpIntakeT());
			cs.setFloat(48, calculateResponseData.getProductionParameter().getPumpIntakeGOL());
			cs.setFloat(49, calculateResponseData.getProductionParameter().getPumpIntakeVisl());
			cs.setFloat(50, calculateResponseData.getProductionParameter().getPumpIntakeBo());
			cs.setFloat(51, calculateResponseData.getProductionParameter().getPumpOutletP());
			cs.setFloat(52, calculateResponseData.getProductionParameter().getPumpOutletT());
			cs.setFloat(53, calculateResponseData.getProductionParameter().getPumpOutletGOL());
			cs.setFloat(54, calculateResponseData.getProductionParameter().getPumpOutletVisl());
			cs.setFloat(55, calculateResponseData.getProductionParameter().getPumpOutletBo());
			
			if(calculateResponseData.getRodString().getCNT()>=1){//有一级杆
				cs.setFloat(56, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getFMax().get(0));
				cs.setFloat(57, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getFMin().get(0));
				cs.setFloat(58, calculateResponseData.getRodString().getEveryRod().get(0).getMaxStress());
				cs.setFloat(59, calculateResponseData.getRodString().getEveryRod().get(0).getMinStress());
				cs.setFloat(60, calculateResponseData.getRodString().getEveryRod().get(0).getAllowableStress());
				cs.setFloat(61, calculateResponseData.getRodString().getEveryRod().get(0).getStressRatio());
			}else{
				cs.setFloat(56, 0);
				cs.setFloat(57, 0);
				cs.setFloat(58, 0);
				cs.setFloat(59, 0);
				cs.setFloat(60, 0);
				cs.setFloat(61, 0);
			}
			
			if(calculateResponseData.getRodString().getCNT()>=2){//有二级杆
				cs.setFloat(62, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getFMax().get(1));
				cs.setFloat(63, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getFMin().get(1));
				cs.setFloat(64, calculateResponseData.getRodString().getEveryRod().get(1).getMaxStress());
				cs.setFloat(65, calculateResponseData.getRodString().getEveryRod().get(1).getMinStress());
				cs.setFloat(66, calculateResponseData.getRodString().getEveryRod().get(1).getAllowableStress());
				cs.setFloat(67, calculateResponseData.getRodString().getEveryRod().get(1).getStressRatio());
			}else{
				cs.setFloat(62, 0);
				cs.setFloat(63, 0);
				cs.setFloat(64, 0);
				cs.setFloat(65, 0);
				cs.setFloat(66, 0);
				cs.setFloat(67, 0);
			}
			
			if(calculateResponseData.getRodString().getCNT()>=3){//有三级杆
				cs.setFloat(68, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getFMax().get(2));
				cs.setFloat(69, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getFMin().get(2));
				cs.setFloat(70, calculateResponseData.getRodString().getEveryRod().get(2).getMaxStress());
				cs.setFloat(71, calculateResponseData.getRodString().getEveryRod().get(2).getMinStress());
				cs.setFloat(72, calculateResponseData.getRodString().getEveryRod().get(2).getAllowableStress());
				cs.setFloat(73, calculateResponseData.getRodString().getEveryRod().get(2).getStressRatio());
			}else{
				cs.setFloat(68, 0);
				cs.setFloat(69, 0);
				cs.setFloat(70, 0);
				cs.setFloat(71, 0);
				cs.setFloat(72, 0);
				cs.setFloat(73, 0);
			}
			
			if(calculateResponseData.getRodString().getCNT()>=4){//有四级杆
				cs.setFloat(74, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getFMax().get(3));
				cs.setFloat(75, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getFMin().get(3));
				cs.setFloat(76, calculateResponseData.getRodString().getEveryRod().get(3).getMaxStress());
				cs.setFloat(77, calculateResponseData.getRodString().getEveryRod().get(3).getMinStress());
				cs.setFloat(78, calculateResponseData.getRodString().getEveryRod().get(3).getAllowableStress());
				cs.setFloat(79, calculateResponseData.getRodString().getEveryRod().get(3).getStressRatio());
			}else{
				cs.setFloat(74, 0);
				cs.setFloat(75, 0);
				cs.setFloat(76, 0);
				cs.setFloat(77, 0);
				cs.setFloat(78, 0);
				cs.setFloat(79, 0);
			}
			cs.setString(80, calculateResponseData.getRodString().getLengthString());
			cs.setString(81, calculateResponseData.getRodString().getGradeString());
			
			//处理杆内外径字符串
			String rodInsideDiameterString="";
			String rodOutsideDiameterString="";
			String rodInsideDiameterArr[]=calculateResponseData.getRodString().getInsideDiameterString().split("/");
			String rodOutsideDiameterArr[]=calculateResponseData.getRodString().getOutsideDiameterString().split("/");
			for(int i=0;i<rodInsideDiameterArr.length;i++){
				rodInsideDiameterString+=((int)(StringManagerUtils.stringToFloat(rodInsideDiameterArr[i], 3)*1000)+"").split("\\.")[0];
				if(i<rodInsideDiameterArr.length-1){
					rodInsideDiameterString+="/";
				}
			}
			for(int i=0;i<rodOutsideDiameterArr.length;i++){
				rodOutsideDiameterString+=((int)(StringManagerUtils.stringToFloat(rodOutsideDiameterArr[i], 3)*1000)+"").split("\\.")[0];
				if(i<rodOutsideDiameterArr.length-1){
					rodOutsideDiameterString+="/";
				}
			}
			cs.setString(82, rodInsideDiameterString);
			cs.setString(83, rodOutsideDiameterString);
			
			//平衡数据保存
			cs.setFloat(84, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getUpStrokeWattMax());
			cs.setFloat(85, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getDownStrokeWattMax());
			cs.setFloat(86, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getWattDegreeBalance());
			cs.setString(87, calculateResponseData.getFSDiagram()==null?null:calculateResponseData.getFSDiagram().getWattMaxRatioString());
			cs.setFloat(88, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getAverageWatt());
			cs.setFloat(89, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getUpStrokeIMax());
			cs.setFloat(90, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getDownStrokeIMax());
			cs.setFloat(91, calculateResponseData.getFSDiagram()==null?0:calculateResponseData.getFSDiagram().getIDegreeBalance());
			cs.setString(92, calculateResponseData.getFSDiagram()==null?null:calculateResponseData.getFSDiagram().getIMaxRatioString());
			
			
			if(electricCalculateResponseData!=null&&electricCalculateResponseData.getResultStatus()==1){//保存电参诊断结果
				if(electricCalculateResponseData.getETResultString()!=null){
					String  ETResultStringArr[]= electricCalculateResponseData.getETResultString().split("\u003cbr/\u003e");
					if(ETResultStringArr.length>50){
						String newETResultString="";
						for(int j=0;j<50;j++){
							newETResultString+=ETResultStringArr[j];
							if(j<49){
								newETResultString+="\u003cbr/\u003e";
							}
						}
						electricCalculateResponseData.setETResultString(newETResultString);
					}
				}else{
					electricCalculateResponseData.setETResultString("");
				}
				
				String currentaalarm=electricCalculateResponseData.getAlarmItems().getCurrentA().getMaxValueStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getCurrentA().getMinValueStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getCurrentA().getZeroLevelStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getCurrentA().getBalacneStatus();
				String currentbalarm=electricCalculateResponseData.getAlarmItems().getCurrentB().getMaxValueStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getCurrentB().getMinValueStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getCurrentB().getZeroLevelStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getCurrentB().getBalacneStatus();
				String currentcalarm=electricCalculateResponseData.getAlarmItems().getCurrentC().getMaxValueStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getCurrentC().getMinValueStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getCurrentC().getZeroLevelStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getCurrentC().getBalacneStatus();
				String voltageaalarm=electricCalculateResponseData.getAlarmItems().getVoltageA().getMaxValueStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getVoltageA().getMinValueStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getVoltageA().getZeroLevelStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getVoltageA().getBalacneStatus();
				String voltagebalarm=electricCalculateResponseData.getAlarmItems().getVoltageB().getMaxValueStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getVoltageB().getMinValueStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getVoltageB().getZeroLevelStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getVoltageB().getBalacneStatus();
				String voltagecalarm=electricCalculateResponseData.getAlarmItems().getVoltageC().getMaxValueStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getVoltageC().getMinValueStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getVoltageC().getZeroLevelStatus()+"/"
						+ electricCalculateResponseData.getAlarmItems().getVoltageC().getBalacneStatus();
				cs.setInt(93, electricCalculateResponseData.getResultCode());
				cs.setString(94, currentaalarm);
				cs.setString(95, currentbalarm);
				cs.setString(96, currentcalarm);
				cs.setString(97, voltageaalarm);
				cs.setString(98, voltagebalarm);
				cs.setString(99, voltagecalarm);
				cs.setString(100, electricCalculateResponseData.getETResultString());
				cs.setString(101, electricCalculateResponseData.getCurrentString());
				cs.setString(102, electricCalculateResponseData.getVoltageString());
				
				cs.setFloat(103, electricCalculateResponseData.getElectricLimit().getCurrentA().getMax());
				cs.setFloat(104, electricCalculateResponseData.getElectricLimit().getCurrentA().getMin());
				cs.setFloat(105, electricCalculateResponseData.getElectricLimit().getCurrentB().getMax());
				cs.setFloat(106, electricCalculateResponseData.getElectricLimit().getCurrentB().getMin());
				cs.setFloat(107, electricCalculateResponseData.getElectricLimit().getCurrentC().getMax());
				cs.setFloat(108, electricCalculateResponseData.getElectricLimit().getCurrentC().getMin());
				
				cs.setFloat(109, electricCalculateResponseData.getElectricLimit().getVoltageA().getMax());
				cs.setFloat(110, electricCalculateResponseData.getElectricLimit().getVoltageA().getMin());
				cs.setFloat(111, electricCalculateResponseData.getElectricLimit().getVoltageB().getMax());
				cs.setFloat(112, electricCalculateResponseData.getElectricLimit().getVoltageB().getMin());
				cs.setFloat(113, electricCalculateResponseData.getElectricLimit().getVoltageC().getMax());
				cs.setFloat(114, electricCalculateResponseData.getElectricLimit().getVoltageC().getMin());
			}else{
				cs.setString(93,"");
				cs.setString(94, "");
				cs.setString(95, "");
				cs.setString(96, "");
				cs.setString(97, "");
				cs.setString(98, "");
				cs.setString(99, "");
				cs.setString(100, "");
				cs.setString(101, "");
				cs.setString(102, "");
				
				cs.setFloat(103,0);
				cs.setFloat(104, 0);
				cs.setFloat(105, 0);
				cs.setFloat(106, 0);
				cs.setFloat(107, 0);
				cs.setFloat(108, 0);
				
				cs.setFloat(109, 0);
				cs.setFloat(110, 0);
				cs.setFloat(111, 0);
				cs.setFloat(112, 0);
				cs.setFloat(113, 0);
				cs.setFloat(114, 0);
			}
			if(timeEffResponseData!=null&&timeEffResponseData.getResultStatus()==1){//保存时率计算结果
				cs.setInt(115, timeEffResponseData.getCurrent().getRunStatus()?1:0);
				cs.setFloat(116, timeEffResponseData.getCurrent().getRunEfficiency().getTime());//运行时间
				cs.setString(117, timeEffResponseData.getCurrent().getRunEfficiency().getRangeString());//运行区间
				cs.setFloat(118, timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency());//运行时率
				cs.setFloat(119, 0);//日用电量
			}else{
				cs.setInt(115, 0);
				cs.setFloat(116, 0);//运行时间
				cs.setString(117, "");//运行区间
				cs.setFloat(118, 0);//运行时率
				cs.setFloat(119, 0);//日用电量
			}
			if(commResponseData!=null&&commResponseData.getResultStatus()==1){//保存通信计算结果
				cs.setFloat(120, commResponseData.getCurrent().getCommEfficiency().getTime());//在线时间
				cs.setString(121, commResponseData.getCurrent().getCommEfficiency().getRangeString());//在线区间
				cs.setFloat(122, commResponseData.getCurrent().getCommEfficiency().getEfficiency());//在线时率
			}else{
				cs.setFloat(120, 0);//在线时间
				cs.setString(121, "");//在线区间
				cs.setFloat(122, 0);//在线时率
			}
			cs.setInt(123, calculateResponseData.getCalculationStatus().getResultStatus());
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
	
	public Boolean saveHYTXCalculateResult_Resp(CalculateResponseData calculateResponseData,String jh) throws SQLException, ParseException {
		String fs="",fs1="",fs2="",fs3="",fspump="";
		for(int i=0;i<=calculateResponseData.getRodString().getCNT();i++){
			for(int j=0;j<calculateResponseData.getFSDiagram().getSCNT();j++){
				if(i==0){
					fs+=calculateResponseData.getFSDiagram().getF().get(j).get(i)+","+calculateResponseData.getFSDiagram().getS().get(j).get(i)+";";
				}
				if(i==1){
					fs1+=calculateResponseData.getFSDiagram().getF().get(j).get(i)+","+calculateResponseData.getFSDiagram().getS().get(j).get(i)+";";
				}
				if(i==2){
					fs2+=calculateResponseData.getFSDiagram().getF().get(j).get(i)+","+calculateResponseData.getFSDiagram().getS().get(j).get(i)+";";
				}
				if(i==3){
					fs3+=calculateResponseData.getFSDiagram().getF().get(j).get(i)+","+calculateResponseData.getFSDiagram().getS().get(j).get(i)+";";
				}
				if(i==calculateResponseData.getRodString().getCNT()){
					fspump+=calculateResponseData.getFSDiagram().getF().get(j).get(i)+","+calculateResponseData.getFSDiagram().getS().get(j).get(i)+";";
				}
			}
			
		}
		Connection conn=null;
		int i=0;
		do{
			conn=OracleJdbcUtis.getOuterConnection();
			i++;
		}while(conn==null&&i<=5);
		CallableStatement cs=null;
		Statement st=null; 
		if(conn!=null){
			try {
				cs = conn.prepareCall("{call PRO_WELL_GT_DATA_RESP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");     
				cs.setString(1, jh);
				cs.setString(2, calculateResponseData.getCalculationStatus().getResultStatus()+"");
				cs.setString(3, calculateResponseData.getCalculationStatus().getResultCode()+"");
				cs.setString(4, calculateResponseData.getVerification().toString());
				cs.setString(5, calculateResponseData.getRodString().toString());
				cs.setString(6, calculateResponseData.getProductionParameter().toString());
				cs.setString(7, calculateResponseData.getFSDiagram().getAcquisitionTime());
				cs.setString(8, calculateResponseData.getFSDiagram().getStroke()+"");
				cs.setString(9, calculateResponseData.getFSDiagram().getSPM()+"");
				cs.setString(10, calculateResponseData.getFSDiagram().getSCNT()+"");
				cs.setString(11, calculateResponseData.getFSDiagram().getUpperLoadLine()+"");
				cs.setString(12, calculateResponseData.getFSDiagram().getLowerLoadLine()+"");
				cs.setString(13, calculateResponseData.getFSDiagram().getFullnessCoefficient()+"");
				cs.setString(14, calculateResponseData.getFSDiagram().getPlungerStroke()+"");
				cs.setString(15, calculateResponseData.getFSDiagram().getAvailablePlungerStroke()+"");
				cs.setString(16, "");
				cs.setString(17, "");
				cs.setString(18, "");
				cs.setString(19, "");
				cs.setString(20, "");
				cs.setString(21, calculateResponseData.getFSDiagram().getFMax().toString());
				cs.setString(22, calculateResponseData.getFSDiagram().getFMin().toString());
				cs.setString(23, calculateResponseData.getPumpEfficiency().toString());
				cs.setString(24, calculateResponseData.getSystemEfficiency().toString());
				cs.setString(25, calculateResponseData.getFSDiagram().getAcquisitionTime());
				cs.setString(26, calculateResponseData.getFSDiagram().getArea()+"");
				cs.executeUpdate();
				
				String sql="update well_gt_data_resp t set t.fs='"+fs+"',t.fs1='"+fs1+"',t.fs2='"+fs2+"',t.fs3='"+fs3+"',t.fspump='"+fspump+"' "
						+ " where t.well_name='"+calculateResponseData.getWellName()+"' and to_date(t.gt_time,'yyyy-mm-dd hh24:mi:ss')=to_date('"+calculateResponseData.getFSDiagram().getAcquisitionTime()+"','yyyy-mm-dd hh24:mi:ss')";
				st=conn.createStatement(); 
				int updatecount=st.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}finally{
				if(cs!=null)
					cs.close();
				if(st!=null)
					st.close();
				conn.close();
			}
		}else{
		}
		
		return true;
	}
	
	public Boolean saveHYTXCalculateResult_Prod(CalculateResponseData calculateResponseData,String jh) throws SQLException, ParseException {
		Connection conn=null;
		int i=0;
		do{
			conn=OracleJdbcUtis.getOuterConnection();
			i++;
		}while(conn==null&&i<=5);
		CallableStatement cs=null;
		if(conn!=null){
			try {
				cs = conn.prepareCall("{call PRO_WELL_GT_DATA_PROD(?,?,?,?,?,"
						+ "?,?,?,?,?,"
						+ "?,?,?,?,?,"
						+ "?,?,?,?,?,"
						+ "?,?,?,?,?,"
						+ "?,?,?,?,?,"
						+ "?,?,?,?,?)}");     
				cs.setString(1, jh);
				cs.setFloat(2, calculateResponseData.getProductionParameter().getWaterCut());
				cs.setFloat(3, calculateResponseData.getProductionParameter().getProductionGasOilRatio());
				cs.setFloat(4, calculateResponseData.getProductionParameter().getTubingPressure());
				cs.setFloat(5, calculateResponseData.getProductionParameter().getCasingPressure());
				cs.setFloat(6, calculateResponseData.getProductionParameter().getWellHeadFluidTemperature());
				cs.setFloat(7, calculateResponseData.getProductionParameter().getProducingfluidLevel());
				cs.setFloat(8, calculateResponseData.getProductionParameter().getPumpSettingDepth());
				cs.setFloat(9, calculateResponseData.getProductionParameter().getPumpSettingDepth()-calculateResponseData.getProductionParameter().getProducingfluidLevel());
				cs.setFloat(10, calculateResponseData.getProductionParameter().getPumpIntakeP());
				cs.setFloat(11, calculateResponseData.getProductionParameter().getPumpIntakeT());
				cs.setFloat(12, calculateResponseData.getProductionParameter().getPumpIntakeGOL());
				cs.setFloat(13, calculateResponseData.getProductionParameter().getPumpOutletP());
				cs.setFloat(14, calculateResponseData.getProductionParameter().getPumpOutletT());
				cs.setFloat(15, calculateResponseData.getProductionParameter().getPumpOutletGOL());
				cs.setFloat(16, calculateResponseData.getProductionParameter().getPumpOutletVisl());
				cs.setFloat(17, calculateResponseData.getProductionParameter().getPumpOutletBo());
				cs.setFloat(18, calculateResponseData.getProductionParameter().getNetGrossRatio());
				cs.setFloat(19, calculateResponseData.getProductionParameter().getLiquidVolumetricProduction());
				cs.setFloat(20, calculateResponseData.getProductionParameter().getOilVolumetricProduction());
				cs.setFloat(21, calculateResponseData.getProductionParameter().getWaterVolumetricProduction());
				cs.setFloat(22, calculateResponseData.getProductionParameter().getAvailablePlungerStrokeVolumetricProduction());
				cs.setFloat(23, calculateResponseData.getProductionParameter().getPumpClearanceLeakVolumetricProduction());
				cs.setFloat(24, calculateResponseData.getProductionParameter().getTVLeakVolumetricProduction());
				cs.setFloat(25, calculateResponseData.getProductionParameter().getSVLeakVolumetricProduction());
				cs.setFloat(26, calculateResponseData.getProductionParameter().getGasInfluenceVolumetricProduction());
				cs.setFloat(27, calculateResponseData.getProductionParameter().getLiquidWeightProduction());
				cs.setFloat(28, calculateResponseData.getProductionParameter().getOilWeightProduction());
				cs.setFloat(29, calculateResponseData.getProductionParameter().getWaterWeightProduction());
				cs.setFloat(30, calculateResponseData.getProductionParameter().getAvailablePlungerStrokeWeightProduction());
				cs.setFloat(31, calculateResponseData.getProductionParameter().getPumpClearanceLeakWeightProduction());
				cs.setFloat(32, calculateResponseData.getProductionParameter().getTVLeakWeightProduction());
				cs.setFloat(33, calculateResponseData.getProductionParameter().getSVLeakWeightProduction());
				cs.setFloat(34, calculateResponseData.getProductionParameter().getGasInfluenceWeightProduction());
				cs.setString(35, calculateResponseData.getFSDiagram().getAcquisitionTime());
				cs.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}finally{
				if(cs!=null)
					cs.close();
				conn.close();
			}
		}else{
		}
		
		return true;
	}
	
	public Boolean saveHYTXCalculateResult_Eff(CalculateResponseData calculateResponseData,String jh) throws SQLException, ParseException {
		Connection conn=null;
		int i=0;
		do{
			conn=OracleJdbcUtis.getOuterConnection();
			i++;
		}while(conn==null&&i<=5);
		CallableStatement cs=null;
		if(conn!=null){
			try {
				cs = conn.prepareCall("{call PRO_WELL_GT_DATA_EFF(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");     
				cs.setString(1, jh);
				cs.setFloat(2, calculateResponseData.getPumpEfficiency().getRodFlexLength());
				cs.setFloat(3, calculateResponseData.getPumpEfficiency().getTubingFlexLength());
				cs.setFloat(4, calculateResponseData.getPumpEfficiency().getInertiaLength());
				cs.setFloat(5, calculateResponseData.getPumpEfficiency().getPumpEff1());
				cs.setFloat(6, calculateResponseData.getPumpEfficiency().getPumpEff2());
				cs.setFloat(7, calculateResponseData.getPumpEfficiency().getPumpEff3());
				cs.setFloat(8, calculateResponseData.getPumpEfficiency().getPumpEff4());
				cs.setFloat(9, calculateResponseData.getPumpEfficiency().getPumpEff());
				cs.setFloat(10, calculateResponseData.getSystemEfficiency().getSurfaceSystemEfficiency());
				cs.setFloat(11, calculateResponseData.getSystemEfficiency().getWellDownSystemEfficiency());
				cs.setFloat(12, calculateResponseData.getSystemEfficiency().getSystemEfficiency());
				cs.setFloat(13, calculateResponseData.getSystemEfficiency().getMotorInputActivePower());
				cs.setFloat(14, calculateResponseData.getSystemEfficiency().getPolishRodPower());
				cs.setFloat(15, calculateResponseData.getSystemEfficiency().getWaterPower());
				cs.setString(16, calculateResponseData.getFSDiagram().getAcquisitionTime());
				cs.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}finally{
				if(cs!=null)
					cs.close();
				conn.close();
			}
		}else{
		}
		return true;
	}
	
	public Boolean saveHYTXCalculateResult_Diag(CalculateResponseData calculateResponseData,String jh) throws SQLException, ParseException {
		Connection conn=null;
		int i=0;
		do{
			conn=OracleJdbcUtis.getOuterConnection();
			i++;
		}while(conn==null&&i<=5);
		CallableStatement cs=null;
		if(conn!=null){
			try {
				cs = conn.prepareCall("{call PRO_WELL_GT_DATA_DIAG(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");     
				cs.setString(1, jh);
				cs.setInt(2, calculateResponseData.getCalculationStatus().getResultStatus());
				cs.setInt(3, calculateResponseData.getCalculationStatus().getResultCode());
				cs.setInt(4, calculateResponseData.getVerification().getErrorCounter());
				cs.setString(5, calculateResponseData.getVerification().getErrorString());
				cs.setInt(6, calculateResponseData.getVerification().getWarningCounter());
				cs.setString(7, calculateResponseData.getVerification().getWarningString());
				cs.setInt(8, calculateResponseData.getRodString().getCNT());
				cs.setFloat(9, calculateResponseData.getRodString().getLengthAll());
				cs.setFloat(10, calculateResponseData.getRodString().getWeightAll());
				cs.setFloat(11, calculateResponseData.getRodString().getBuoyancyForceAll());
				
				cs.setFloat(12, calculateResponseData.getProductionParameter().getPumpClearanceLeakVolumetricProduction());
				cs.setFloat(13, calculateResponseData.getProductionParameter().getTVLeakVolumetricProduction());
				cs.setFloat(14, calculateResponseData.getProductionParameter().getSVLeakVolumetricProduction());
				cs.setFloat(15, calculateResponseData.getProductionParameter().getGasInfluenceVolumetricProduction());
				
				cs.setFloat(16, calculateResponseData.getProductionParameter().getPumpClearanceLeakWeightProduction());
				cs.setFloat(17, calculateResponseData.getProductionParameter().getTVLeakWeightProduction());
				cs.setFloat(18, calculateResponseData.getProductionParameter().getSVLeakWeightProduction());
				cs.setFloat(19, calculateResponseData.getProductionParameter().getGasInfluenceWeightProduction());
				cs.setString(20, calculateResponseData.getFSDiagram().getAcquisitionTime());
				cs.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}finally{
				if(cs!=null)
					cs.close();
				conn.close();
			}
		}else{
		}
		
		return true;
	}
	
	
	public Boolean saveTotalCalculationData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String tatalDate) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs = null;
		try{
			cs = conn.prepareCall("{call PRO_SAVEAggregationDATA(?,?,?,?,?,?,?,?,?,?,?,?,?,"          //13
					+ "?,?,?,?,?,?,?,?,?,"                                               //9
					+ "?,?,?,?,?,?,?,?,?,?,?,?,"                                         //12
					+ "?,?,?,?,?,?,?,?,?,?,?,?,"                                         //12
					+ "?,?,?,?,?,?,?,?,?,?,?,?,"                                         //12
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"                                   //15
					+ "?,?,?,?,?,?,?,?,?,?,?,?,"                                         //12
					+ "?,?,?,?,?,?,"                                                     //6
					+ "?,?,"                                                              //2
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"                      //22
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"                                    //15
					+ "?,?,"                                                             //2
					+ "?,?,"                                                             //2
					+ "?)}");                                                            //1
			cs.setString(1, totalAnalysisRequestData.getWellName());//井名
			cs.setInt(2, totalAnalysisResponseData.getResultStatus());//计算状态
			
			cs.setInt(3, totalAnalysisResponseData.getCommStatus());//通信状态
			cs.setFloat(4, totalAnalysisResponseData.getCommTime());//在线时间
			cs.setFloat(5, totalAnalysisResponseData.getCommTimeEfficiency());//在线时率
			cs.setString(6, totalAnalysisResponseData.getCommRange());//在线区间
			
			cs.setInt(7, totalAnalysisResponseData.getRunStatus());//运行状态
			cs.setFloat(8, totalAnalysisResponseData.getRunTime());//运行时间
			cs.setFloat(9, totalAnalysisResponseData.getRunTimeEfficiency());//运行时率
			cs.setString(10, totalAnalysisResponseData.getRunRange());//运行区间
			cs.setInt(11, totalAnalysisResponseData.getFSResultCode());//功图工况代码
			cs.setString(12, totalAnalysisResponseData.getFSResultString());//功图工况字符串
			cs.setInt(13, totalAnalysisResponseData.getExtendedDays());//功图延用天数
			
			//冲程、冲次、充满系数
			cs.setFloat(14, totalAnalysisResponseData.getStroke().getValue());//冲程
			cs.setFloat(15, totalAnalysisResponseData.getStroke().getMax());//冲程最大值
			cs.setFloat(16, totalAnalysisResponseData.getStroke().getMin());//冲程最小值
			cs.setFloat(17, totalAnalysisResponseData.getSPM().getValue());//冲次
			cs.setFloat(18, totalAnalysisResponseData.getSPM().getMax());//冲次最大值
			cs.setFloat(19, totalAnalysisResponseData.getSPM().getMin());//冲次最小值
			cs.setFloat(20, totalAnalysisResponseData.getFullnessCoefficient().getValue());//充满系数
			cs.setFloat(21, totalAnalysisResponseData.getFullnessCoefficient().getMax());//充满系数最大值
			cs.setFloat(22, totalAnalysisResponseData.getFullnessCoefficient().getMin());//充满系数最小值
			
			//油压、套压、井口油温、生产气油比
			cs.setFloat(23, totalAnalysisResponseData.getTubingPressure().getValue());
			cs.setFloat(24, totalAnalysisResponseData.getTubingPressure().getMax());
			cs.setFloat(25, totalAnalysisResponseData.getTubingPressure().getMin());
			cs.setFloat(26, totalAnalysisResponseData.getCasingPressure().getValue());
			cs.setFloat(27, totalAnalysisResponseData.getCasingPressure().getMax());
			cs.setFloat(28, totalAnalysisResponseData.getCasingPressure().getMin());
			cs.setFloat(29, totalAnalysisResponseData.getWellHeadFluidTemperature().getValue());
			cs.setFloat(30, totalAnalysisResponseData.getWellHeadFluidTemperature().getMax());
			cs.setFloat(31, totalAnalysisResponseData.getWellHeadFluidTemperature().getMin());
			cs.setFloat(32, totalAnalysisResponseData.getProductionGasOilRatio().getValue());
			cs.setFloat(33, totalAnalysisResponseData.getProductionGasOilRatio().getMax());
			cs.setFloat(34, totalAnalysisResponseData.getProductionGasOilRatio().getMin());
			
			//体积-产液、产油、产水、含水
			cs.setFloat(35, totalAnalysisResponseData.getLiquidVolumetricProduction().getValue());
			cs.setFloat(36, totalAnalysisResponseData.getLiquidVolumetricProduction().getMax());
			cs.setFloat(37, totalAnalysisResponseData.getLiquidVolumetricProduction().getMin());
			cs.setFloat(38, totalAnalysisResponseData.getOilVolumetricProduction().getValue());
			cs.setFloat(39, totalAnalysisResponseData.getOilVolumetricProduction().getMax());
			cs.setFloat(40, totalAnalysisResponseData.getOilVolumetricProduction().getMin());
			cs.setFloat(41, totalAnalysisResponseData.getWaterVolumetricProduction().getValue());
			cs.setFloat(42, totalAnalysisResponseData.getWaterVolumetricProduction().getMax());
			cs.setFloat(43, totalAnalysisResponseData.getWaterVolumetricProduction().getMin());
			cs.setFloat(44, totalAnalysisResponseData.getVolumeWaterCut().getValue());
			cs.setFloat(45, totalAnalysisResponseData.getVolumeWaterCut().getMax());
			cs.setFloat(46, totalAnalysisResponseData.getVolumeWaterCut().getMin());
			
			//重量-产液、产油、产水、含水
			cs.setFloat(47, totalAnalysisResponseData.getLiquidWeightProduction().getValue());
			cs.setFloat(48, totalAnalysisResponseData.getLiquidWeightProduction().getMax());
			cs.setFloat(49, totalAnalysisResponseData.getLiquidWeightProduction().getMin());
			cs.setFloat(50, totalAnalysisResponseData.getOilWeightProduction().getValue());
			cs.setFloat(51, totalAnalysisResponseData.getOilWeightProduction().getMax());
			cs.setFloat(52, totalAnalysisResponseData.getOilWeightProduction().getMin());
			cs.setFloat(53, totalAnalysisResponseData.getWaterWeightProduction().getValue());
			cs.setFloat(54, totalAnalysisResponseData.getWaterWeightProduction().getMax());
			cs.setFloat(55, totalAnalysisResponseData.getWaterWeightProduction().getMin());
			cs.setFloat(56, totalAnalysisResponseData.getWeightWaterCut().getValue());
			cs.setFloat(57, totalAnalysisResponseData.getWeightWaterCut().getMax());
			cs.setFloat(58, totalAnalysisResponseData.getWeightWaterCut().getMin());
			
			//泵效、泵径、泵挂、动液面、沉没度
			cs.setFloat(59, totalAnalysisResponseData.getPumpEff().getValue());
			cs.setFloat(60, totalAnalysisResponseData.getPumpEff().getMax());
			cs.setFloat(61, totalAnalysisResponseData.getPumpEff().getMin());
			cs.setFloat(62, totalAnalysisResponseData.getPumpBoreDiameter().getValue()*1000);
			cs.setFloat(63, totalAnalysisResponseData.getPumpBoreDiameter().getMax()*1000);
			cs.setFloat(64, totalAnalysisResponseData.getPumpBoreDiameter().getMin()*1000);
			cs.setFloat(65, totalAnalysisResponseData.getPumpSettingDepth().getValue());
			cs.setFloat(66, totalAnalysisResponseData.getPumpSettingDepth().getMax());
			cs.setFloat(67, totalAnalysisResponseData.getPumpSettingDepth().getMin());
			cs.setFloat(68, totalAnalysisResponseData.getProducingfluidLevel().getValue());
			cs.setFloat(69, totalAnalysisResponseData.getProducingfluidLevel().getMax());
			cs.setFloat(70, totalAnalysisResponseData.getProducingfluidLevel().getMin());
			cs.setFloat(71, totalAnalysisResponseData.getSubmergence().getValue());
			cs.setFloat(72, totalAnalysisResponseData.getSubmergence().getMax());
			cs.setFloat(73, totalAnalysisResponseData.getSubmergence().getMin());
			
			//井下效率、地面效率、系统效率、吨液百米耗电量
			cs.setFloat(74, totalAnalysisResponseData.getWellDownSystemEfficiency().getValue());
			cs.setFloat(75, totalAnalysisResponseData.getWellDownSystemEfficiency().getMax());
			cs.setFloat(76, totalAnalysisResponseData.getWellDownSystemEfficiency().getMin());
			cs.setFloat(77, totalAnalysisResponseData.getSurfaceSystemEfficiency().getValue());
			cs.setFloat(78, totalAnalysisResponseData.getSurfaceSystemEfficiency().getMax());
			cs.setFloat(79, totalAnalysisResponseData.getSurfaceSystemEfficiency().getMin());
			cs.setFloat(80, totalAnalysisResponseData.getSystemEfficiency().getValue());
			cs.setFloat(81, totalAnalysisResponseData.getSystemEfficiency().getMax());
			cs.setFloat(82, totalAnalysisResponseData.getSystemEfficiency().getMin());
			cs.setFloat(83, totalAnalysisResponseData.getPowerConsumptionPerTHM().getValue());
			cs.setFloat(84, totalAnalysisResponseData.getPowerConsumptionPerTHM().getMax());
			cs.setFloat(85, totalAnalysisResponseData.getPowerConsumptionPerTHM().getMin());
			
			//电流平衡度、功率平衡度
			cs.setFloat(86, totalAnalysisResponseData.getIDegreeBalance().getValue());
			cs.setFloat(87, totalAnalysisResponseData.getIDegreeBalance().getMax());
			cs.setFloat(88, totalAnalysisResponseData.getIDegreeBalance().getMin());
			cs.setFloat(89, totalAnalysisResponseData.getIDegreeBalance().getValue());
			cs.setFloat(90, totalAnalysisResponseData.getIDegreeBalance().getMax());
			cs.setFloat(91, totalAnalysisResponseData.getIDegreeBalance().getMin());
			
			//电参
			cs.setInt(92, totalAnalysisResponseData.getETResultCode());//电参工况代码
			cs.setString(93, totalAnalysisResponseData.getETResultString());//电参工况综合
			
			//三相电流、三相电压
			cs.setFloat(94, totalAnalysisResponseData.getIA().getValue());
			cs.setFloat(95, totalAnalysisResponseData.getIA().getMax());
			cs.setFloat(96, totalAnalysisResponseData.getIA().getMin());
			cs.setFloat(97, totalAnalysisResponseData.getIB().getValue());
			cs.setFloat(98, totalAnalysisResponseData.getIB().getMax());
			cs.setFloat(99, totalAnalysisResponseData.getIB().getMin());
			cs.setFloat(100, totalAnalysisResponseData.getIC().getValue());
			cs.setFloat(101, totalAnalysisResponseData.getIC().getMax());
			cs.setFloat(102, totalAnalysisResponseData.getIC().getMin());
			cs.setString(103, totalAnalysisResponseData.getIMaxString());
			cs.setString(104, totalAnalysisResponseData.getVMinString());
			
			cs.setFloat(105, totalAnalysisResponseData.getVA().getValue());
			cs.setFloat(106, totalAnalysisResponseData.getVA().getMax());
			cs.setFloat(107, totalAnalysisResponseData.getVA().getMin());
			cs.setFloat(108, totalAnalysisResponseData.getVB().getValue());
			cs.setFloat(109, totalAnalysisResponseData.getVB().getMax());
			cs.setFloat(110, totalAnalysisResponseData.getVB().getMin());
			cs.setFloat(111, totalAnalysisResponseData.getVC().getValue());
			cs.setFloat(112, totalAnalysisResponseData.getVC().getMax());
			cs.setFloat(113, totalAnalysisResponseData.getVC().getMin());
			cs.setString(114, totalAnalysisResponseData.getVMaxString());
			cs.setString(115, totalAnalysisResponseData.getVMinString());
			
			cs.setFloat(125, totalAnalysisResponseData.getRunFrequency().getValue());
			cs.setFloat(126, totalAnalysisResponseData.getRunFrequency().getMax());
			cs.setFloat(127, totalAnalysisResponseData.getRunFrequency().getMin());
			cs.setFloat(128, totalAnalysisResponseData.getRPM().getValue());
			cs.setFloat(129, totalAnalysisResponseData.getRPM().getMax());
			cs.setFloat(130, totalAnalysisResponseData.getRPM().getMin());
			
			cs.setString(135, tatalDate);//汇总日期
			cs.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveDiscreteDailyCalculationData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String tatalDate) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs = null;
		try{
			cs = conn.prepareCall("{call prd_save_rpc_discretedaily(?,"          //1
					+ "?,?,?,?,?,?,?,?,?,"                                               //9
					+ "?,?,?,?,?,?,?,?,?,"                                               //9
					+ "?,?,?,"                                                           //3
					+ "?)}");                                                            //1
			cs.setString(1, totalAnalysisRequestData.getWellName());//井名
			//三相电流、三相电压
			cs.setFloat(2, totalAnalysisResponseData.getIA().getValue());
			cs.setFloat(3, totalAnalysisResponseData.getIA().getMax());
			cs.setFloat(4, totalAnalysisResponseData.getIA().getMin());
			cs.setFloat(5, totalAnalysisResponseData.getIB().getValue());
			cs.setFloat(6, totalAnalysisResponseData.getIB().getMax());
			cs.setFloat(7, totalAnalysisResponseData.getIB().getMin());
			cs.setFloat(8, totalAnalysisResponseData.getIC().getValue());
			cs.setFloat(9, totalAnalysisResponseData.getIC().getMax());
			cs.setFloat(10, totalAnalysisResponseData.getIC().getMin());
			
			cs.setFloat(11, totalAnalysisResponseData.getVA().getValue());
			cs.setFloat(12, totalAnalysisResponseData.getVA().getMax());
			cs.setFloat(13, totalAnalysisResponseData.getVA().getMin());
			cs.setFloat(14, totalAnalysisResponseData.getVB().getValue());
			cs.setFloat(15, totalAnalysisResponseData.getVB().getMax());
			cs.setFloat(16, totalAnalysisResponseData.getVB().getMin());
			cs.setFloat(17, totalAnalysisResponseData.getVC().getValue());
			cs.setFloat(18, totalAnalysisResponseData.getVC().getMax());
			cs.setFloat(19, totalAnalysisResponseData.getVC().getMin());
			
			cs.setFloat(20, totalAnalysisResponseData.getRunFrequency().getValue());
			cs.setFloat(21, totalAnalysisResponseData.getRunFrequency().getMax());
			cs.setFloat(22, totalAnalysisResponseData.getRunFrequency().getMin());
			
			cs.setString(23, tatalDate);//汇总日期
			cs.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean savePCPDiscreteDailyCalculationData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String tatalDate) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs = null;
		try{
			cs = conn.prepareCall("{call prd_save_pcp_discretedaily(?,"          //1
					+ "?,?,?,?,?,?,?,?,?,"                                               //9
					+ "?,?,?,?,?,?,?,?,?,"                                               //9
					+ "?,?,?,"                                                           //3
					+ "?)}");                                                            //1
			cs.setString(1, totalAnalysisRequestData.getWellName());//井名
			//三相电流、三相电压
			cs.setFloat(2, totalAnalysisResponseData.getIA().getValue());
			cs.setFloat(3, totalAnalysisResponseData.getIA().getMax());
			cs.setFloat(4, totalAnalysisResponseData.getIA().getMin());
			cs.setFloat(5, totalAnalysisResponseData.getIB().getValue());
			cs.setFloat(6, totalAnalysisResponseData.getIB().getMax());
			cs.setFloat(7, totalAnalysisResponseData.getIB().getMin());
			cs.setFloat(8, totalAnalysisResponseData.getIC().getValue());
			cs.setFloat(9, totalAnalysisResponseData.getIC().getMax());
			cs.setFloat(10, totalAnalysisResponseData.getIC().getMin());
			
			cs.setFloat(11, totalAnalysisResponseData.getVA().getValue());
			cs.setFloat(12, totalAnalysisResponseData.getVA().getMax());
			cs.setFloat(13, totalAnalysisResponseData.getVA().getMin());
			cs.setFloat(14, totalAnalysisResponseData.getVB().getValue());
			cs.setFloat(15, totalAnalysisResponseData.getVB().getMax());
			cs.setFloat(16, totalAnalysisResponseData.getVB().getMin());
			cs.setFloat(17, totalAnalysisResponseData.getVC().getValue());
			cs.setFloat(18, totalAnalysisResponseData.getVC().getMax());
			cs.setFloat(19, totalAnalysisResponseData.getVC().getMin());
			
			cs.setFloat(20, totalAnalysisResponseData.getRunFrequency().getValue());
			cs.setFloat(21, totalAnalysisResponseData.getRunFrequency().getMax());
			cs.setFloat(22, totalAnalysisResponseData.getRunFrequency().getMin());
			
			cs.setString(23, tatalDate);//汇总日期
			cs.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveFSDiagramDailyCalculationData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String tatalDate) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs = null;
		try{
			cs = conn.prepareCall("{call prd_save_rpc_diagramdaily(?,?,?,?,?,"          //5
					+ "?,?,?,?,?,?,?,?,?,"                                               //9
					+ "?,?,?,?,?,?,?,?,?,?,?,?,"                                         //12
					+ "?,?,?,?,?,?,?,?,?,?,?,?,"                                         //12
					+ "?,?,?,?,?,?,?,?,?,?,?,?,"                                         //12
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"                                   //15
					+ "?,?,?,?,?,?,?,?,?,?,?,?,"                                         //12
					+ "?,?,?,?,?,?,"                                                     //6
					+ "?,?,?,?,"                                                         //4
					+ "?,?,?,?,"                                                         //4
					+ "?)}");                                                            //1
			cs.setString(1, totalAnalysisRequestData.getWellName());//井名
			cs.setInt(2, totalAnalysisResponseData.getResultStatus());//计算状态
			
			
			cs.setInt(3, totalAnalysisResponseData.getFSResultCode());//功图工况代码
			cs.setString(4, totalAnalysisResponseData.getFSResultString());//功图工况字符串
			cs.setInt(5, totalAnalysisResponseData.getExtendedDays());//功图延用天数
			
			//冲程、冲次、充满系数
			cs.setFloat(6, totalAnalysisResponseData.getStroke().getValue());//冲程
			cs.setFloat(7, totalAnalysisResponseData.getStroke().getMax());//冲程最大值
			cs.setFloat(8, totalAnalysisResponseData.getStroke().getMin());//冲程最小值
			cs.setFloat(9, totalAnalysisResponseData.getSPM().getValue());//冲次
			cs.setFloat(10, totalAnalysisResponseData.getSPM().getMax());//冲次最大值
			cs.setFloat(11, totalAnalysisResponseData.getSPM().getMin());//冲次最小值
			cs.setFloat(12, totalAnalysisResponseData.getFullnessCoefficient().getValue());//充满系数
			cs.setFloat(13, totalAnalysisResponseData.getFullnessCoefficient().getMax());//充满系数最大值
			cs.setFloat(14, totalAnalysisResponseData.getFullnessCoefficient().getMin());//充满系数最小值
			
			//油压、套压、井口油温、生产气油比
			cs.setFloat(15, totalAnalysisResponseData.getTubingPressure().getValue());
			cs.setFloat(16, totalAnalysisResponseData.getTubingPressure().getMax());
			cs.setFloat(17, totalAnalysisResponseData.getTubingPressure().getMin());
			cs.setFloat(18, totalAnalysisResponseData.getCasingPressure().getValue());
			cs.setFloat(19, totalAnalysisResponseData.getCasingPressure().getMax());
			cs.setFloat(20, totalAnalysisResponseData.getCasingPressure().getMin());
			cs.setFloat(21, totalAnalysisResponseData.getWellHeadFluidTemperature().getValue());
			cs.setFloat(22, totalAnalysisResponseData.getWellHeadFluidTemperature().getMax());
			cs.setFloat(23, totalAnalysisResponseData.getWellHeadFluidTemperature().getMin());
			cs.setFloat(24, totalAnalysisResponseData.getProductionGasOilRatio().getValue());
			cs.setFloat(25, totalAnalysisResponseData.getProductionGasOilRatio().getMax());
			cs.setFloat(26, totalAnalysisResponseData.getProductionGasOilRatio().getMin());
			
			//体积-产液、产油、产水、含水
			cs.setFloat(27, totalAnalysisResponseData.getLiquidVolumetricProduction().getValue());
			cs.setFloat(28, totalAnalysisResponseData.getLiquidVolumetricProduction().getMax());
			cs.setFloat(29, totalAnalysisResponseData.getLiquidVolumetricProduction().getMin());
			cs.setFloat(30, totalAnalysisResponseData.getOilVolumetricProduction().getValue());
			cs.setFloat(31, totalAnalysisResponseData.getOilVolumetricProduction().getMax());
			cs.setFloat(32, totalAnalysisResponseData.getOilVolumetricProduction().getMin());
			cs.setFloat(33, totalAnalysisResponseData.getWaterVolumetricProduction().getValue());
			cs.setFloat(34, totalAnalysisResponseData.getWaterVolumetricProduction().getMax());
			cs.setFloat(35, totalAnalysisResponseData.getWaterVolumetricProduction().getMin());
			cs.setFloat(36, totalAnalysisResponseData.getVolumeWaterCut().getValue());
			cs.setFloat(37, totalAnalysisResponseData.getVolumeWaterCut().getMax());
			cs.setFloat(38, totalAnalysisResponseData.getVolumeWaterCut().getMin());
			
			//重量-产液、产油、产水、含水
			cs.setFloat(39, totalAnalysisResponseData.getLiquidWeightProduction().getValue());
			cs.setFloat(40, totalAnalysisResponseData.getLiquidWeightProduction().getMax());
			cs.setFloat(41, totalAnalysisResponseData.getLiquidWeightProduction().getMin());
			cs.setFloat(42, totalAnalysisResponseData.getOilWeightProduction().getValue());
			cs.setFloat(43, totalAnalysisResponseData.getOilWeightProduction().getMax());
			cs.setFloat(44, totalAnalysisResponseData.getOilWeightProduction().getMin());
			cs.setFloat(45, totalAnalysisResponseData.getWaterWeightProduction().getValue());
			cs.setFloat(46, totalAnalysisResponseData.getWaterWeightProduction().getMax());
			cs.setFloat(47, totalAnalysisResponseData.getWaterWeightProduction().getMin());
			cs.setFloat(48, totalAnalysisResponseData.getWeightWaterCut().getValue());
			cs.setFloat(49, totalAnalysisResponseData.getWeightWaterCut().getMax());
			cs.setFloat(50, totalAnalysisResponseData.getWeightWaterCut().getMin());
			
			//泵效、泵径、泵挂、动液面、沉没度
			cs.setFloat(51, totalAnalysisResponseData.getPumpEff().getValue());
			cs.setFloat(52, totalAnalysisResponseData.getPumpEff().getMax());
			cs.setFloat(53, totalAnalysisResponseData.getPumpEff().getMin());
			cs.setFloat(54, totalAnalysisResponseData.getPumpBoreDiameter().getValue()*1000);
			cs.setFloat(55, totalAnalysisResponseData.getPumpBoreDiameter().getMax()*1000);
			cs.setFloat(56, totalAnalysisResponseData.getPumpBoreDiameter().getMin()*1000);
			cs.setFloat(57, totalAnalysisResponseData.getPumpSettingDepth().getValue());
			cs.setFloat(58, totalAnalysisResponseData.getPumpSettingDepth().getMax());
			cs.setFloat(59, totalAnalysisResponseData.getPumpSettingDepth().getMin());
			cs.setFloat(60, totalAnalysisResponseData.getProducingfluidLevel().getValue());
			cs.setFloat(61, totalAnalysisResponseData.getProducingfluidLevel().getMax());
			cs.setFloat(62, totalAnalysisResponseData.getProducingfluidLevel().getMin());
			cs.setFloat(63, totalAnalysisResponseData.getSubmergence().getValue());
			cs.setFloat(64, totalAnalysisResponseData.getSubmergence().getMax());
			cs.setFloat(65, totalAnalysisResponseData.getSubmergence().getMin());
			
			//井下效率、地面效率、系统效率、吨液百米耗电量
			cs.setFloat(66, totalAnalysisResponseData.getWellDownSystemEfficiency().getValue());
			cs.setFloat(67, totalAnalysisResponseData.getWellDownSystemEfficiency().getMax());
			cs.setFloat(68, totalAnalysisResponseData.getWellDownSystemEfficiency().getMin());
			cs.setFloat(69, totalAnalysisResponseData.getSurfaceSystemEfficiency().getValue());
			cs.setFloat(70, totalAnalysisResponseData.getSurfaceSystemEfficiency().getMax());
			cs.setFloat(71, totalAnalysisResponseData.getSurfaceSystemEfficiency().getMin());
			cs.setFloat(72, totalAnalysisResponseData.getSystemEfficiency().getValue());
			cs.setFloat(73, totalAnalysisResponseData.getSystemEfficiency().getMax());
			cs.setFloat(74, totalAnalysisResponseData.getSystemEfficiency().getMin());
			cs.setFloat(75, totalAnalysisResponseData.getPowerConsumptionPerTHM().getValue());
			cs.setFloat(76, totalAnalysisResponseData.getPowerConsumptionPerTHM().getMax());
			cs.setFloat(77, totalAnalysisResponseData.getPowerConsumptionPerTHM().getMin());
			
			//电流平衡度、功率平衡度
			cs.setFloat(78, totalAnalysisResponseData.getIDegreeBalance().getValue());
			cs.setFloat(79, totalAnalysisResponseData.getIDegreeBalance().getMax());
			cs.setFloat(80, totalAnalysisResponseData.getIDegreeBalance().getMin());
			cs.setFloat(81, totalAnalysisResponseData.getWattDegreeBalance().getValue());
			cs.setFloat(82, totalAnalysisResponseData.getWattDegreeBalance().getMax());
			cs.setFloat(83, totalAnalysisResponseData.getWattDegreeBalance().getMin());
			
			cs.setInt(84, totalAnalysisResponseData.getCommStatus());//通信状态
			cs.setFloat(85, totalAnalysisResponseData.getCommTime());//在线时间
			cs.setFloat(86, totalAnalysisResponseData.getCommTimeEfficiency());//在线时率
			cs.setString(87, totalAnalysisResponseData.getCommRange());//在线区间
			
			cs.setInt(88, totalAnalysisResponseData.getRunStatus());//运行状态
			cs.setFloat(89, totalAnalysisResponseData.getRunTime());//运行时间
			cs.setFloat(90, totalAnalysisResponseData.getRunTimeEfficiency());//运行时率
			cs.setString(91, totalAnalysisResponseData.getRunRange());//运行区间
			
			cs.setString(92, tatalDate);//汇总日期
			cs.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean savePCPRPMDailyCalculationData(TotalAnalysisResponseData totalAnalysisResponseData,TotalAnalysisRequestData totalAnalysisRequestData,String tatalDate) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs = null;
		try{
			cs = conn.prepareCall("{call prd_save_pcp_rpmdaily(?,?,"          //2
					+ "?,?,?,?,?,?,?,?,?,?,?,?,"                                         //12
					+ "?,?,?,?,?,?,?,?,?,?,?,?,"                                         //12
					+ "?,?,?,?,?,?,?,?,?,?,?,?,"                                         //12
					+ "?,?,?,?,?,?,?,?,?,?,?,?,"                                         //12
					+ "?,?,?,?,?,?,"                                                     //6
					+ "?,?,?,?,"                                                         //4
					+ "?,?,?,?,"                                                         //4
					+ "?)}");                                                            //1
			cs.setString(1, totalAnalysisRequestData.getWellName());//井名
			cs.setInt(2, totalAnalysisResponseData.getResultStatus());//计算状态
			
			//油压、套压、井口油温、生产气油比
			cs.setFloat(3, totalAnalysisResponseData.getTubingPressure().getValue());
			cs.setFloat(4, totalAnalysisResponseData.getTubingPressure().getMax());
			cs.setFloat(5, totalAnalysisResponseData.getTubingPressure().getMin());
			cs.setFloat(6, totalAnalysisResponseData.getCasingPressure().getValue());
			cs.setFloat(7, totalAnalysisResponseData.getCasingPressure().getMax());
			cs.setFloat(8, totalAnalysisResponseData.getCasingPressure().getMin());
			cs.setFloat(9, totalAnalysisResponseData.getWellHeadFluidTemperature().getValue());
			cs.setFloat(10, totalAnalysisResponseData.getWellHeadFluidTemperature().getMax());
			cs.setFloat(11, totalAnalysisResponseData.getWellHeadFluidTemperature().getMin());
			cs.setFloat(12, totalAnalysisResponseData.getProductionGasOilRatio().getValue());
			cs.setFloat(13, totalAnalysisResponseData.getProductionGasOilRatio().getMax());
			cs.setFloat(14, totalAnalysisResponseData.getProductionGasOilRatio().getMin());
			
			//体积-产液、产油、产水、含水
			cs.setFloat(15, totalAnalysisResponseData.getLiquidVolumetricProduction().getValue());
			cs.setFloat(16, totalAnalysisResponseData.getLiquidVolumetricProduction().getMax());
			cs.setFloat(17, totalAnalysisResponseData.getLiquidVolumetricProduction().getMin());
			cs.setFloat(18, totalAnalysisResponseData.getOilVolumetricProduction().getValue());
			cs.setFloat(19, totalAnalysisResponseData.getOilVolumetricProduction().getMax());
			cs.setFloat(20, totalAnalysisResponseData.getOilVolumetricProduction().getMin());
			cs.setFloat(21, totalAnalysisResponseData.getWaterVolumetricProduction().getValue());
			cs.setFloat(22, totalAnalysisResponseData.getWaterVolumetricProduction().getMax());
			cs.setFloat(23, totalAnalysisResponseData.getWaterVolumetricProduction().getMin());
			cs.setFloat(24, totalAnalysisResponseData.getVolumeWaterCut().getValue());
			cs.setFloat(25, totalAnalysisResponseData.getVolumeWaterCut().getMax());
			cs.setFloat(26, totalAnalysisResponseData.getVolumeWaterCut().getMin());
			
			//重量-产液、产油、产水、含水
			cs.setFloat(27, totalAnalysisResponseData.getLiquidWeightProduction().getValue());
			cs.setFloat(28, totalAnalysisResponseData.getLiquidWeightProduction().getMax());
			cs.setFloat(29, totalAnalysisResponseData.getLiquidWeightProduction().getMin());
			cs.setFloat(30, totalAnalysisResponseData.getOilWeightProduction().getValue());
			cs.setFloat(31, totalAnalysisResponseData.getOilWeightProduction().getMax());
			cs.setFloat(32, totalAnalysisResponseData.getOilWeightProduction().getMin());
			cs.setFloat(33, totalAnalysisResponseData.getWaterWeightProduction().getValue());
			cs.setFloat(34, totalAnalysisResponseData.getWaterWeightProduction().getMax());
			cs.setFloat(35, totalAnalysisResponseData.getWaterWeightProduction().getMin());
			cs.setFloat(36, totalAnalysisResponseData.getWeightWaterCut().getValue());
			cs.setFloat(37, totalAnalysisResponseData.getWeightWaterCut().getMax());
			cs.setFloat(38, totalAnalysisResponseData.getWeightWaterCut().getMin());
			
			//泵效、泵挂、动液面、沉没度
			cs.setFloat(39, totalAnalysisResponseData.getPumpEff().getValue());
			cs.setFloat(40, totalAnalysisResponseData.getPumpEff().getMax());
			cs.setFloat(41, totalAnalysisResponseData.getPumpEff().getMin());
			cs.setFloat(42, totalAnalysisResponseData.getPumpSettingDepth().getValue());
			cs.setFloat(43, totalAnalysisResponseData.getPumpSettingDepth().getMax());
			cs.setFloat(44, totalAnalysisResponseData.getPumpSettingDepth().getMin());
			cs.setFloat(45, totalAnalysisResponseData.getProducingfluidLevel().getValue());
			cs.setFloat(46, totalAnalysisResponseData.getProducingfluidLevel().getMax());
			cs.setFloat(47, totalAnalysisResponseData.getProducingfluidLevel().getMin());
			cs.setFloat(48, totalAnalysisResponseData.getSubmergence().getValue());
			cs.setFloat(49, totalAnalysisResponseData.getSubmergence().getMax());
			cs.setFloat(50, totalAnalysisResponseData.getSubmergence().getMin());
			
			//系统效率、吨液百米耗电量
			cs.setFloat(51, totalAnalysisResponseData.getSystemEfficiency().getValue());
			cs.setFloat(52, totalAnalysisResponseData.getSystemEfficiency().getMax());
			cs.setFloat(53, totalAnalysisResponseData.getSystemEfficiency().getMin());
			cs.setFloat(54, totalAnalysisResponseData.getPowerConsumptionPerTHM().getValue());
			cs.setFloat(55, totalAnalysisResponseData.getPowerConsumptionPerTHM().getMax());
			cs.setFloat(56, totalAnalysisResponseData.getPowerConsumptionPerTHM().getMin());
			
			cs.setInt(57, totalAnalysisResponseData.getCommStatus());//通信状态
			cs.setFloat(58, totalAnalysisResponseData.getCommTime());//在线时间
			cs.setFloat(59, totalAnalysisResponseData.getCommTimeEfficiency());//在线时率
			cs.setString(60, totalAnalysisResponseData.getCommRange());//在线区间
			
			cs.setInt(61, totalAnalysisResponseData.getRunStatus());//运行状态
			cs.setFloat(62, totalAnalysisResponseData.getRunTime());//运行时间
			cs.setFloat(63, totalAnalysisResponseData.getRunTimeEfficiency());//运行时率
			cs.setString(64, totalAnalysisResponseData.getRunRange());//运行区间
			
			cs.setString(65, tatalDate);//汇总日期
			cs.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveTotalCalculationData(TransferDaily transferDaily) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs = null;
		try{
			cs = conn.prepareCall("{call prd_save_rpc_inver_daily("
					+ "?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,?,?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,"
					+ "?,?,?,"
					+ "?)}");                                                            //1
			cs.setString(1, transferDaily.getWellName());//井名
			cs.setInt(2, transferDaily.getResultCode());//电参工况
			if(!StringManagerUtils.isNotNull(transferDaily.getResultStr())&&transferDaily.getResultCodes()!=null){
				cs.setString(3,  StringUtils.join(transferDaily.getResultCodes(), ","));
			}else{
				cs.setString(3, transferDaily.getResultStr());//电参工况字符串
			}
			cs.setInt(4, transferDaily.getCommStatus() ? 1 : 0);//通信状态
			if(transferDaily.getCommEfficiency()!=null){
				cs.setFloat(5, transferDaily.getCommEfficiency().getTime());//在线时间
				cs.setFloat(6, transferDaily.getCommEfficiency().getEfficiency());//在线时率
				cs.setString(7, transferDaily.getCommEfficiency().getRangeString());//在线区间
			}else{
				cs.setString(5, null);//在线时间
				cs.setString(6, null);//在线时率
				cs.setString(7, null);//在线区间
			}
			
			
			cs.setInt(8, transferDaily.getRunStatus() ? 1 : 0);//运行状态
			cs.setFloat(9, transferDaily.getRunEfficiency().getTime());//运行时间
			cs.setFloat(10, transferDaily.getRunEfficiency().getEfficiency());//运行时率
			cs.setString(11, transferDaily.getRunEfficiency().getRangeString());//运行区间
			
			cs.setFloat(12, transferDaily.getSignal().getAvg());
			cs.setFloat(13, transferDaily.getSignal().getMax());
			cs.setFloat(14, transferDaily.getSignal().getMin());
			
			cs.setFloat(15, transferDaily.getEnergy().getWatt());
			cs.setFloat(16, transferDaily.getEnergy().getPWatt());
			cs.setFloat(17, transferDaily.getEnergy().getNWatt());
			cs.setFloat(18, transferDaily.getEnergy().getVar());
			cs.setFloat(19, transferDaily.getEnergy().getPVar());
			cs.setFloat(20, transferDaily.getEnergy().getNVar());
			cs.setFloat(21, transferDaily.getEnergy().getVA());
			
			cs.setFloat(22, transferDaily.getI().getA().getAvg());
			cs.setFloat(23, transferDaily.getI().getA().getMax());
			cs.setFloat(24, transferDaily.getI().getA().getMin());
			
			cs.setFloat(25, transferDaily.getI().getB().getAvg());
			cs.setFloat(26, transferDaily.getI().getB().getMax());
			cs.setFloat(27, transferDaily.getI().getB().getMin());
			
			cs.setFloat(28, transferDaily.getI().getC().getAvg());
			cs.setFloat(29, transferDaily.getI().getC().getMax());
			cs.setFloat(30, transferDaily.getI().getC().getMin());
			
			cs.setFloat(31, transferDaily.getV().getA().getAvg());
			cs.setFloat(32, transferDaily.getV().getA().getMax());
			cs.setFloat(33, transferDaily.getV().getA().getMin());
			
			cs.setFloat(34, transferDaily.getV().getB().getAvg());
			cs.setFloat(35, transferDaily.getV().getB().getMax());
			cs.setFloat(36, transferDaily.getV().getB().getMin());
			
			cs.setFloat(37, transferDaily.getV().getC().getAvg());
			cs.setFloat(38, transferDaily.getV().getC().getMax());
			cs.setFloat(39, transferDaily.getV().getC().getMin());
			
			cs.setFloat(40, transferDaily.getF().getAvg());
			cs.setFloat(41, transferDaily.getF().getMax());
			cs.setFloat(42, transferDaily.getF().getMin());
			
			cs.setFloat(43, transferDaily.getSPM().getAvg());
			cs.setFloat(44, transferDaily.getSPM().getMax());
			cs.setFloat(45, transferDaily.getSPM().getMin());
			
			cs.setFloat(46, transferDaily.getIDegreeBalance().getAvg());
			cs.setFloat(47, transferDaily.getIDegreeBalance().getMax());
			cs.setFloat(48, transferDaily.getIDegreeBalance().getMin());
			
			cs.setFloat(49, transferDaily.getWattDegreeBalance().getAvg());
			cs.setFloat(50, transferDaily.getWattDegreeBalance().getMax());
			cs.setFloat(51, transferDaily.getWattDegreeBalance().getMin());
			
			cs.setString(52, transferDaily.getDate());//汇总日期
			cs.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveTorqueBalanceResponseData(int jlbh,TorqueBalanceResponseData torqueBalanceResponseData,
			String CurrentTorqueCurve,String OptimizitionTorqueCurve,String OptimizationBalance,
			String CurrentTorqueCurve2,String OptimizitionTorqueCurve2,String OptimizationBalance2,String MotionCurve) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs = null;
		CLOB CurrentTorqueCurveClob=new CLOB((OracleConnection) conn);
		CurrentTorqueCurveClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		CurrentTorqueCurveClob.putString(1, CurrentTorqueCurve);
		
		CLOB CurrentTorqueCurveClob2=new CLOB((OracleConnection) conn);
		CurrentTorqueCurveClob2 = oracle.sql.CLOB.createTemporary(conn,false,1);
		CurrentTorqueCurveClob2.putString(1, CurrentTorqueCurve2);
		
		CLOB OptimizitionTorqueCurveClob=new CLOB((OracleConnection) conn);
		OptimizitionTorqueCurveClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		OptimizitionTorqueCurveClob.putString(1, OptimizitionTorqueCurve);
		
		CLOB OptimizitionTorqueCurveClob2=new CLOB((OracleConnection) conn);
		OptimizitionTorqueCurveClob2 = oracle.sql.CLOB.createTemporary(conn,false,1);
		OptimizitionTorqueCurveClob2.putString(1,OptimizitionTorqueCurve2);
		
		CLOB MotionCurveClob=new CLOB((OracleConnection) conn);
		MotionCurveClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		MotionCurveClob.putString(1, MotionCurve);
		try{
			cs = conn.prepareCall("{call PRO_T_201_SAVEBALANCEDATA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			cs.setInt(1, jlbh);
			cs.setFloat(2, torqueBalanceResponseData.getStroke());
			cs.setFloat(3, torqueBalanceResponseData.getSPM());
			cs.setInt(4, torqueBalanceResponseData.getCalculationStatus().getResultStatus());
			
			cs.setFloat(5, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentDegreeOfBalance());
			cs.setFloat(6, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getAverageValue());
			cs.setFloat(7, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getMeanSquareRoot());
			cs.setClob(8, CurrentTorqueCurveClob);
			cs.setFloat(9, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getDeltaRadius());
			cs.setInt(10, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getDeltaBlock());
			cs.setFloat(11, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getDeltaDegreeOfBalance());
			cs.setFloat(12, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getDeltaBalanceTorque());
			cs.setFloat(13, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getDeltaTorqueMeanSquareRoot());
			cs.setFloat(14, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getSamePositionRadius());
			cs.setFloat(15, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionDegreeOfBalance());
			cs.setFloat(16, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getAverageValue());
			cs.setFloat(17, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getMeanSquareRoot());
			cs.setString(18, OptimizationBalance);
			cs.setClob(19, OptimizitionTorqueCurveClob);
			
			cs.setFloat(20, torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentDegreeOfBalance());
			cs.setFloat(21, torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getNetAnalysis().getAverageValue());
			cs.setFloat(22, torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getCurrentTorqueCurve().getNetAnalysis().getMeanSquareRoot());
			cs.setClob(23, CurrentTorqueCurveClob2);
			cs.setFloat(24, torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getDeltaRadius());
			cs.setInt(25, torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getDeltaBlock());
			cs.setFloat(26, torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getDeltaDegreeOfBalance());
			cs.setFloat(27, torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getDeltaBalanceTorque());
			cs.setFloat(28, torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getDeltaTorqueMeanSquareRoot());
			cs.setFloat(29, torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getSamePositionRadius());
			cs.setFloat(30, torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionDegreeOfBalance());
			cs.setFloat(31, torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getNetAnalysis().getAverageValue());
			cs.setFloat(32, torqueBalanceResponseData.getNetTorqueMeanSquareRootMethod().getOptimizitionTorqueCurve().getNetAnalysis().getMeanSquareRoot());
			cs.setString(33, OptimizationBalance2);
			cs.setClob(34, OptimizitionTorqueCurveClob2);
			
			cs.setFloat(35, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentDegreeOfBalance());
			cs.setFloat(36, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getAverageValue());
			cs.setFloat(37, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getCurrentTorqueCurve().getNetAnalysis().getMeanSquareRoot());
			cs.setClob(38, CurrentTorqueCurveClob);
			cs.setFloat(39, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getDeltaRadius());
			cs.setInt(40, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getDeltaBlock());
			cs.setFloat(41, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getDeltaDegreeOfBalance());
			cs.setFloat(42, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getDeltaBalanceTorque());
			cs.setFloat(43, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getDeltaTorqueMeanSquareRoot());
			cs.setFloat(44, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getSamePositionRadius());
			cs.setFloat(45, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionDegreeOfBalance());
			cs.setFloat(46, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getAverageValue());
			cs.setFloat(47, torqueBalanceResponseData.getNetTorqueMaxValueMethod().getOptimizitionTorqueCurve().getNetAnalysis().getMeanSquareRoot());
			cs.setString(48, OptimizationBalance);
			cs.setClob(49, OptimizitionTorqueCurveClob);
			
			cs.setClob(50, MotionCurveClob);
			cs.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveBalanceResponseData(int jlbh,float Stroke,float SPM,BalanceResponseData balanceResponseData,
			String CurrentTorqueCurve,
			String OptimizitionTorqueCurve,String OptimizationBalance,
			String OptimizitionTorqueCurve2,String OptimizationBalance2,
			String OptimizitionTorqueCurve3,String OptimizationBalance3,
			String MotionCurve) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs = null;
		CLOB CurrentTorqueCurveClob=new CLOB((OracleConnection) conn);
		CurrentTorqueCurveClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		CurrentTorqueCurveClob.putString(1, CurrentTorqueCurve);
		
		
		CLOB OptimizitionTorqueCurveClob=new CLOB((OracleConnection) conn);
		OptimizitionTorqueCurveClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		OptimizitionTorqueCurveClob.putString(1, OptimizitionTorqueCurve);
		
		CLOB OptimizitionTorqueCurveClob2=new CLOB((OracleConnection) conn);
		OptimizitionTorqueCurveClob2 = oracle.sql.CLOB.createTemporary(conn,false,1);
		OptimizitionTorqueCurveClob2.putString(1,OptimizitionTorqueCurve2);
		
		CLOB OptimizitionTorqueCurveClob3=new CLOB((OracleConnection) conn);
		OptimizitionTorqueCurveClob3 = oracle.sql.CLOB.createTemporary(conn,false,1);
		OptimizitionTorqueCurveClob3.putString(1,OptimizitionTorqueCurve3);
		
		CLOB MotionCurveClob=new CLOB((OracleConnection) conn);
		MotionCurveClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		MotionCurveClob.putString(1, MotionCurve);
		try{
			cs = conn.prepareCall("{call PRO_T_201_SAVEBALANCEDATA2(?,?,?,?,?,?,?," //7
					+ "?,?,?,?,?,?,?,?,?,"                                          //9
					+ "?,?,?,?,?,?,?,?,"                                            //8
					+ "?,?,?,?,?,?,?,?,?)}");                                       //9
			
			cs.setInt(1, jlbh);//记录编号
			cs.setFloat(2, Stroke);//冲程
			cs.setFloat(3, SPM);//冲次
			cs.setInt(4, balanceResponseData.getCalculationStatus().getResultStatus());//计算状态
			cs.setClob(5, CurrentTorqueCurveClob);//当前扭矩曲线
			cs.setClob(6, MotionCurveClob);//抽油机运动特性曲线
			cs.setFloat(7,  balanceResponseData.getCurrentTorqueCurve().getNetAnalysis().getMeanSquareRoot());//目前均方根
			
			cs.setFloat(8,  balanceResponseData.getCurrentTorqueCurve().getNetAnalysis().getMaxValueDegreeOfBalance());//最大值法目前平衡度
			cs.setFloat(9,  balanceResponseData.getMaxValueMethod().getDeltaRadius());//最大值法移动距离
			cs.setInt(10,   balanceResponseData.getMaxValueMethod().getDeltaBlock());//最大值法块数变化
			cs.setFloat(11, balanceResponseData.getMaxValueMethod().getDeltaMaxValueDOB());//最大值法预期平衡度变化
			cs.setFloat(12, balanceResponseData.getMaxValueMethod().getPercentageOfDifferenceMSR());//最大值法均方根预期变化
			cs.setFloat(13, balanceResponseData.getMaxValueMethod().getTorqueCurve().getNetAnalysis().getMeanSquareRoot());//最大值法净扭矩均方根
			cs.setFloat(14, balanceResponseData.getMaxValueMethod().getTorqueCurve().getNetAnalysis().getMaxValueDegreeOfBalance());//最大值法预期平衡度
			cs.setString(15, OptimizationBalance);//最大值法预期平衡块信息
			cs.setClob(16, OptimizitionTorqueCurveClob);//最大值法预期扭矩曲线
			
			cs.setFloat(17, balanceResponseData.getCurrentTorqueCurve().getNetAnalysis().getMeanSquareRootDegreeOfBalance());//均方根法目前平衡度
			cs.setFloat(18, balanceResponseData.getMeanSquareRootMethod().getDeltaRadius());//均方根法移动距离
			cs.setInt(19,   balanceResponseData.getMeanSquareRootMethod().getDeltaBlock());//均方根法块数变化
			cs.setFloat(20, balanceResponseData.getMeanSquareRootMethod().getPercentageOfDifferenceMSR());// 均方根预期变化率
			cs.setFloat(21, balanceResponseData.getMeanSquareRootMethod().getTorqueCurve().getNetAnalysis().getMeanSquareRoot());//均方根法净扭矩均方根
			cs.setFloat(22, balanceResponseData.getMeanSquareRootMethod().getTorqueCurve().getNetAnalysis().getMeanSquareRootDegreeOfBalance());//均方根法预期平衡度
			cs.setString(23, OptimizationBalance2);//均方根法预期平衡块信息
			cs.setClob(24, OptimizitionTorqueCurveClob2);//均方根法预期扭矩曲线
			
			cs.setFloat(25, balanceResponseData.getCurrentTorqueCurve().getNetAnalysis().getAveragePowerDegreeOfBalance());//平均功率法目前平衡度
			cs.setFloat(26, balanceResponseData.getAveragePowerMethod().getDeltaRadius());//平均功率法移动距离
			cs.setInt(27,   balanceResponseData.getAveragePowerMethod().getDeltaBlock());//平均功率法块数变化
			cs.setFloat(28, balanceResponseData.getAveragePowerMethod().getDeltaPowerDOB());//平均功率法预期平衡度变化
			cs.setFloat(29, balanceResponseData.getAveragePowerMethod().getPercentageOfDifferenceMSR());//平均功率法均方根预期变化
			cs.setFloat(30, balanceResponseData.getAveragePowerMethod().getTorqueCurve().getNetAnalysis().getMeanSquareRoot());//平均功率法净扭矩均方根
			cs.setFloat(31, balanceResponseData.getAveragePowerMethod().getTorqueCurve().getNetAnalysis().getAveragePowerDegreeOfBalance());//平均功率法预期平衡度
			cs.setString(32, OptimizationBalance3);//平均功率法预期平衡块信息
			cs.setClob(33, OptimizitionTorqueCurveClob3);//平均功率法预期扭矩曲线
			cs.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveBalanceTotalCalculationData(CycleEvaluaion cycleEvaluaion,String currentBalance,String optBalance,int type) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs = null;
		
		try{
			cs = conn.prepareCall("{call PRO_T_204_SAVEBALANCETOTALDATA(?,?,?,?,?,?,?,?)}");
			
			cs.setString(1, cycleEvaluaion.getWellName());
			cs.setFloat(2, cycleEvaluaion.getCurrentDegreeOfBalance());
			cs.setString(3, currentBalance);
			cs.setString(4, optBalance);
			
			cs.setInt(5, cycleEvaluaion.getDeltaBlock());
			cs.setFloat(6, cycleEvaluaion.getDeltaRadius());
			cs.setInt(7, cycleEvaluaion.getExtendedDays());
			cs.setInt(8, type);
			cs.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean saveBalanceCycleCalculationData(CycleEvaluaion cycleEvaluaion,String currentBalance,String optBalance,int type) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs = null;
		
		try{
			cs = conn.prepareCall("{call PRO_T_206_SAVEBALANCECYCLEDATA(?,?,?,?,?,?,?,?)}");
			
			cs.setString(1, cycleEvaluaion.getWellName());
			cs.setFloat(2, cycleEvaluaion.getCurrentDegreeOfBalance());
			cs.setString(3, currentBalance);
			cs.setString(4, optBalance);
			
			cs.setInt(5, cycleEvaluaion.getDeltaBlock());
			cs.setFloat(6, cycleEvaluaion.getDeltaRadius());
			cs.setInt(7, cycleEvaluaion.getExtendedDays());
			cs.setInt(8, type);
			cs.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean SaveEquipmentDataHYTXGridData(JSONObject jsonObject,String oraNames) throws SQLException {
		Connection conn=OracleJdbcUtis.getOuterConnection();
		CallableStatement cs=null;
		Connection conn2=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs2=null;
		try {
			cs = conn.prepareCall("{call PRO_SAVE_EQUIPMENT_INFO(?,?,?,?,?,?,?,?)}");
			cs.setString(1, StringManagerUtils.getJSONObjectString(jsonObject, "areaName"));
			cs.setString(2, StringManagerUtils.getJSONObjectString(jsonObject, "oilstationName"));
			cs.setString(3, StringManagerUtils.getJSONObjectString(jsonObject, "equipmentName"));
			cs.setString(4, StringManagerUtils.getJSONObjectString(jsonObject, "equipmentNumber"));
			cs.setString(5, StringManagerUtils.getJSONObjectString(jsonObject, "totalrunningbase"));
			cs.setString(6, StringManagerUtils.getJSONObjectString(jsonObject, "firstmaintainbase"));
			cs.setString(7, StringManagerUtils.getJSONObjectString(jsonObject, "secondmaintainbase"));
			cs.setString(8, oraNames);
			cs.executeUpdate();
			
			cs2 = conn2.prepareCall("{call PRO_UPDATEEQUIPDAILYREPORTDATA(?,?,?,?,?,?,?)}");
			cs2.setString(1, StringManagerUtils.getJSONObjectString(jsonObject, "areaName"));
			cs2.setString(2, StringManagerUtils.getJSONObjectString(jsonObject, "oilstationName"));
			cs2.setString(3, StringManagerUtils.getJSONObjectString(jsonObject, "equipmentName"));
			cs2.setString(4, StringManagerUtils.getJSONObjectString(jsonObject, "equipmentNumber"));
			cs2.setString(5, StringManagerUtils.getJSONObjectString(jsonObject, "totalrunningbase"));
			cs2.setString(6, StringManagerUtils.getJSONObjectString(jsonObject, "firstmaintainbase"));
			cs2.setString(7, StringManagerUtils.getJSONObjectString(jsonObject, "secondmaintainbase"));
			cs2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(cs!=null){
				cs.close();
			}
			if(cs2!=null){
				cs2.close();
			}
			conn.close();
			conn2.close();
		}
		return true;
	}
	
	public int SetWellProductionCycle(String sql) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		Statement st=null; 
		int result=0;
		try {
			st=conn.createStatement(); 
			result=st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(cs!=null)
				cs.close();
			if(st!=null)
				st.close();
			conn.close();
		}
		return result;
	}
	
	
	public Boolean saveFSDiagram(FA2FSResponseData FA2FSResponseData,String gtstr) throws SQLException, ParseException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		CLOB gtClob=new CLOB((OracleConnection) conn);
		gtClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		gtClob.putString(1, gtstr);
		try {
			cs = conn.prepareCall("{call SAVE_FSDIAGRSM(?,?,?,?,?)}");
			cs.setString(1, FA2FSResponseData.getWellName());
			cs.setString(2,FA2FSResponseData.getFSDiagram().getAcquisitionTime());
			cs.setClob(3, gtClob);
			cs.setFloat(4, FA2FSResponseData.getNameplateStroke());
			cs.setFloat(5, FA2FSResponseData.getForearmLength());
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
	
	public Boolean savePSToFSPumpingUnitData(String PumpingUnitData,String PumpingUnitPTRData,String wellName) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		Statement st=null; 
		CallableStatement cs=null;
		JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+PumpingUnitData+"}");//解析数据
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		
		CLOB PTFClob=new CLOB((OracleConnection) conn);
		PTFClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		PTFClob.putString(1, PumpingUnitPTRData);
		try {
			cs = conn.prepareCall("{call prd_save_rpcinformation(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			for(int i=0;i<jsonArray.size();i++){
				JSONObject everydata = JSONObject.fromObject(jsonArray.getString(i));
				cs.setString(1, everydata.getString("WellName"));
				cs.setString(2, everydata.getString("Manufacturer"));
				cs.setString(3, everydata.getString("Model"));
				cs.setString(4, everydata.getString("Stroke"));
				cs.setString(5, everydata.getString("CrankRotationDirection"));
				cs.setString(6, everydata.getString("OffsetAngleOfCrank"));
				cs.setString(7, everydata.getString("CrankGravityRadius"));
				cs.setString(8, everydata.getString("SingleCrankWeight"));
				cs.setString(9, everydata.getString("StructuralUnbalance"));
				cs.setString(10, everydata.getString("BalancePosition"));
				cs.setString(11, everydata.getString("BalanceWeight"));
				cs.setString(12, wellName);
				cs.setClob(13, PTFClob);
				cs.executeUpdate();
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
	
	
	public Boolean savePSToFSCalaulateResult(String wellName,String cjsj,String ElectricData,
			String StartPoint,String EndPoint,String FSDiagramId,
			String Stroke,String SPM,String CNT,String FSDiagram) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		
		
		CLOB ElectricDataClob=new CLOB((OracleConnection) conn);
		ElectricDataClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		ElectricDataClob.putString(1, ElectricData);
		
		CLOB FSDiagramClob=new CLOB((OracleConnection) conn);
		FSDiagramClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		FSDiagramClob.putString(1, FSDiagram);
		try {
			cs = conn.prepareCall("{call SAVE_T303InversionData(?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1, wellName);
			cs.setString(2, cjsj);
			cs.setClob(3, ElectricDataClob);
			cs.setString(4, StartPoint);
			cs.setString(5, EndPoint);
			cs.setString(6, FSDiagramId);
			cs.setString(7, Stroke);
			cs.setString(8, SPM);
			cs.setString(9, CNT);
			cs.setClob(10, FSDiagramClob);
			cs.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(cs!=null)
				cs.close();
			conn.close();
		}
		return true;
	}
	
	public Boolean savePSToFSMotorData(String MotorData,String MotorPerformanceCurverData,String wellName) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		Statement st=null; 
		CallableStatement cs=null;
		JSONObject jsonObject = JSONObject.fromObject("{\"data\":"+MotorData+"}");//解析数据
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		
		CLOB PerformanceCurverClob=new CLOB((OracleConnection) conn);
		PerformanceCurverClob = oracle.sql.CLOB.createTemporary(conn,false,1);
		PerformanceCurverClob.putString(1, MotorPerformanceCurverData);
		try {
			cs = conn.prepareCall("{call prd_save_rpc_motor(?,?,?,?,?,?,?)}");
			for(int i=0;i<jsonArray.size();i++){
				JSONObject everydata = JSONObject.fromObject(jsonArray.getString(i));
				cs.setString(1, everydata.getString("WellName"));
				cs.setString(2, everydata.getString("Manufacturer"));
				cs.setString(3, everydata.getString("Model"));
				cs.setString(4, everydata.getString("BeltPulleyDiameter"));
				cs.setString(5, everydata.getString("SynchroSpeed"));
				cs.setString(6, wellName);
				cs.setClob(7, PerformanceCurverClob);
				cs.executeUpdate();
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
	
	@SuppressWarnings("resource")
	public Boolean saveInverOptimizeHandsontableData(InverOptimizeHandsontableChangedData inverOptimizeHandsontableChangedData,String orgId,String orgCode) throws SQLException {
		Connection conn=SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		CallableStatement cs=null;
		PreparedStatement ps=null;
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTast.initDriverConfig();
		}
		try {
			cs = conn.prepareCall("{call prd_save_rpc_inver_opt(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			if(inverOptimizeHandsontableChangedData.getUpdatelist()!=null){
				for(int i=0;i<inverOptimizeHandsontableChangedData.getUpdatelist().size();i++){
					if(StringManagerUtils.isNotNull(inverOptimizeHandsontableChangedData.getUpdatelist().get(i).getWellName())){
						
						cs.setString(1, inverOptimizeHandsontableChangedData.getUpdatelist().get(i).getWellName());
						cs.setString(2, inverOptimizeHandsontableChangedData.getUpdatelist().get(i).getOffsetAngleOfCrankPS());
						cs.setString(3, inverOptimizeHandsontableChangedData.getUpdatelist().get(i).getSurfaceSystemEfficiency());
						cs.setString(4, inverOptimizeHandsontableChangedData.getUpdatelist().get(i).getFS_LeftPercent());
						cs.setString(5, inverOptimizeHandsontableChangedData.getUpdatelist().get(i).getFS_RightPercent());
						cs.setString(6, inverOptimizeHandsontableChangedData.getUpdatelist().get(i).getWattAngle());
						cs.setString(7, inverOptimizeHandsontableChangedData.getUpdatelist().get(i).getFilterTime_Watt());
						cs.setString(8, inverOptimizeHandsontableChangedData.getUpdatelist().get(i).getFilterTime_I());
						cs.setString(9, inverOptimizeHandsontableChangedData.getUpdatelist().get(i).getFilterTime_RPM());
						cs.setString(10, inverOptimizeHandsontableChangedData.getUpdatelist().get(i).getFilterTime_FSDiagram());
						cs.setString(11,inverOptimizeHandsontableChangedData.getUpdatelist().get(i).getFilterTime_FSDiagram_L());
						cs.setString(12, inverOptimizeHandsontableChangedData.getUpdatelist().get(i).getFilterTime_FSDiagram_R());
						cs.setString(13, orgId);
						cs.executeUpdate();
					}
				}
			}
			if(inverOptimizeHandsontableChangedData.getInsertlist()!=null){
				for(int i=0;i<inverOptimizeHandsontableChangedData.getInsertlist().size();i++){
					if(StringManagerUtils.isNotNull(inverOptimizeHandsontableChangedData.getInsertlist().get(i).getWellName())){
						
						cs.setString(1, inverOptimizeHandsontableChangedData.getInsertlist().get(i).getWellName());
						cs.setString(2, inverOptimizeHandsontableChangedData.getInsertlist().get(i).getOffsetAngleOfCrankPS());
						cs.setString(3, inverOptimizeHandsontableChangedData.getInsertlist().get(i).getSurfaceSystemEfficiency());
						cs.setString(4, inverOptimizeHandsontableChangedData.getInsertlist().get(i).getFS_LeftPercent());
						cs.setString(5, inverOptimizeHandsontableChangedData.getInsertlist().get(i).getFS_RightPercent());
						cs.setString(6, inverOptimizeHandsontableChangedData.getInsertlist().get(i).getWattAngle());
						cs.setString(7, inverOptimizeHandsontableChangedData.getInsertlist().get(i).getFilterTime_Watt());
						cs.setString(8, inverOptimizeHandsontableChangedData.getInsertlist().get(i).getFilterTime_I());
						cs.setString(9, inverOptimizeHandsontableChangedData.getInsertlist().get(i).getFilterTime_RPM());
						cs.setString(10, inverOptimizeHandsontableChangedData.getInsertlist().get(i).getFilterTime_FSDiagram());
						cs.setString(11,inverOptimizeHandsontableChangedData.getInsertlist().get(i).getFilterTime_FSDiagram_L());
						cs.setString(12, inverOptimizeHandsontableChangedData.getInsertlist().get(i).getFilterTime_FSDiagram_R());
						cs.setString(13, orgId);
						cs.executeUpdate();
					}
				}
			}
			if(inverOptimizeHandsontableChangedData.getDelidslist()!=null){
//				String delIds="";
//				String delSql="";
//				for(int i=0;i<inverOptimizeHandsontableChangedData.getDelidslist().size();i++){
//					delIds+=inverOptimizeHandsontableChangedData.getDelidslist().get(i);
//					if(i<inverOptimizeHandsontableChangedData.getDelidslist().size()-1){
//						delIds+=",";
//					}
//				}
//				delSql="delete from tbl_wellinformation t where t.jlbh in ("+delIds+")";
//				ps=conn.prepareStatement(delSql);
//				int result=ps.executeUpdate();
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
}
