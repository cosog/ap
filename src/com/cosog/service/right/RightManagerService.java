package com.cosog.service.right;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cosog.service.base.BaseService;

/**
 * <p>
 * 描述：权限管理服务类Service
 * </p>
 * 
 * @author gao 2014-06-06
 * 
 * @param <T>
 */
@Service("rightManagerService")
public class RightManagerService<T> extends BaseService<T> {

	public List<T> loadRights(Class<T> clazz) {
		String queryString = "SELECT u FROM Right u order by u.RightId ";
		return getBaseDao().find(queryString);
	}

	public List<T> queryRights(Class<T> clazz, String RightName) {
		if (RightName == null || "".equals(RightName))
			return loadRights(clazz);

		String queryString = "SELECT u FROM Right u WHERE u.RightName like '%" + RightName + "%' order by u.RightId asc";
		return getBaseDao().find(queryString);
	}

	public List<T> queryCurrentUserRights(Class<T> clazz, Integer userNo) {
		if (userNo == null || "".equals(userNo))
			return loadRights(clazz);
		String queryString = "select  r From " + "Right r ,Right rg where  rg.rtRightcode=r.RightCode and rg.rtUserNo=" + userNo + " order by r.RightCode asc";
		return getBaseDao().find(queryString);
	}

	public void addRight(T Right) throws Exception {
		getBaseDao().addObject(Right);
	}

	public void modifyRight(T Right) throws Exception {
		getBaseDao().updateObject(Right);
	}

	public void saveOrUpdateRight(T right) throws Exception {
		getBaseDao().saveOrUpdateObject(right);
	}

	public void deleteRight(int id, Class<T> clazz) throws Exception {
		T u = getBaseDao().getObject(clazz, id);
		getBaseDao().deleteObject(u);
	}

	public void bulkDelete(final String ids) throws Exception {
		final String hql = "DELETE Right u where u.RightId in (" + ids + ")";
		getBaseDao().bulkObjectDelete(hql);
	}

	public void deleteCurrentRole(final String roleCode) throws Exception {
		final String hql = "DELETE Right u where u.rtRolecode = '" + roleCode + "'";
		getBaseDao().bulkObjectDelete(hql);
	}

	public void deleteCurrentRoleByUserNo(final Integer userNo) throws Exception {
		final String hql = "DELETE Right u where u.rtUserNo = " + userNo + "";
		bulkObjectDelete(hql);
	}

	public T getRight(Class<T> clazz, int id) {
		return getBaseDao().getObject(clazz, id);
	}
}
