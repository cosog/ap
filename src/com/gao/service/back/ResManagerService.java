package com.gao.service.back;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.gao.model.Res;
import com.gao.service.base.BaseService;
import com.gao.utils.PagingConstants;
import com.google.gson.Gson;

/**<p>描述：油气藏树形维护服务Service</p>
 * 
 * @author gao 2014-06-06
 *
 * @param <T>
 */
@Service("resManagerService")
public class ResManagerService<T> extends BaseService<T> {
	public Long findMaxNum(int resLevel) throws Exception {
		Long count = (long) 0.0;
		String queryString = " Res o where o.resLevel =" + resLevel;
		count = this.getBaseDao().getMaxCountValue(queryString);
		return count;
	}

	/** <p>描述：查询当前的父级油气藏的级别</p>
	 * @param parentId
	 * @return
	 */
	public List<Res> findByPrimary(Integer parentId) {
		String queryString = "SELECT u.resLevel FROM Res u where u.resParent="
				+ parentId + " order by u.resId ";
		return this.getBaseDao().getObjects(queryString);
	}

	/**
	 * @param parentId
	 * @return
	 */
	public List<T> findChildRes(Integer parentId) {
		String queryString = "SELECT u FROM Res u where u.resParent="
				+ parentId + " order by u.ResId ";
		return this.getBaseDao().getObjects(queryString);
	}

	public List<T> findResChildrenByparentId(Integer parentId) {
		String queryString = "SELECT u FROM Res u where u.resParent="
				+ parentId + " order by u.resId ";
		return this.getBaseDao().getObjects(queryString);
	}

	public List<T> findCurrentResCodeByparentId(Integer parentId) {
		String queryString = "SELECT u FROM Res u where u.resId=" + parentId
				+ " order by u.resId ";
		return this.getBaseDao().getObjects(queryString);
	}

	public List<T> loadRess(Class<T> clazz) {
		String queryString = "SELECT u FROM Res u order by u.resId ";
		return getBaseDao().getObjects(queryString);
	}

	public List<T> loadWellInfoRess(Class<T> clazz) {
		String queryString = "SELECT u.resCode,u.resName FROM Res u order by u.resId ";
		return getBaseDao().getObjects(queryString);
	}

	public List<T> loadResTreeRess(Class<T> clazz, Integer orgId, int userType) {
		String orgIds = this.getBaseDao().getUserOrgIds(orgId);
		String queryString = "";
		if (userType != 1) {
			queryString = "SELECT u FROM Res u  where u.resId in(" + orgIds
					+ ")order by u.resId  ";
		} else {
			queryString = "SELECT u FROM Res u";
		}
		return getBaseDao().getObjects(queryString);
	}

	public List<T> loadParentRess(Class<T> clazz) {
		String queryString = "SELECT resId,resName FROM Res u order by u.resId ";
		return getBaseDao().getObjects(queryString);
	}

	public List<T> queryRess(Class<T> clazz, String ResName) {
		if (ResName == null || "".equals(ResName))
			return loadRess(clazz);

		String queryString = "SELECT u FROM Res u where u.resName like '%" + ResName + "%' order by u.resSeq asc";
		return getBaseDao().getObjects(queryString);
	}

	@SuppressWarnings("rawtypes")
	public String getResList(Map map) {
		// String entity = (String) map.get("entity");
		String hql = "SELECT u FROM Res u order by u.resId asc";
		Gson g = new Gson();
		List<Res> Ress = null;
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String json = "";
		int total = this.getBaseDao().getRecordCountRows(hql);
		try {
			Ress = this.getBaseDao().getListForPage(
					(Integer) map.get("offset"),
					(Integer) map.get(PagingConstants.PAGE_SIZE), hql);
			jsonMap.put(PagingConstants.TOTAL, total);
			jsonMap.put(PagingConstants.LIST, Ress);
			json = g.toJson(jsonMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public void addRes(T Res) throws Exception {
		getBaseDao().addObject(Res);
	}

	/**<p>描述：油气藏修改Server方法</p>
	 * 
	 * @param Res： 当前修改的油气藏对象
	 * @throws Exception
	 */
	public void modifyRes(T Res) throws Exception {
		getBaseDao().updateObject(Res);
	}

	public void deleteRes(int id, Class<T> clazz) throws Exception {
		T u = getBaseDao().getObject(clazz, id);
		getBaseDao().deleteObject(u);
	}

	/**<p>描述：批量删除油气藏数据信息</p>
	 * @param ids
	 * @throws Exception
	 */
	public void bulkDelete(final String ids) throws Exception {
		final String hql = "DELETE Res u where u.resId in (" + ids + ")";
		 bulkObjectDelete(hql);
	}

	public T getRes(Class<T> clazz, int id) {
		return getBaseDao().getObject(clazz, id);
	}

}
