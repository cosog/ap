package com.cosog.service.right;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;

/**
 * <p>描述：角色维护服务</p>
 * 
 * @author gao 2014-06-06
 *
 * @param <T>
 */
@Service("roleManagerService")
@SuppressWarnings("rawtypes")
public class RoleManagerService<T> extends BaseService<T> {
	@Autowired
private CommonDataService service;
	/**
	 * <p>
	 * 描述：加载组织类型的下拉菜单数据信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loadRoleType(String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		sql = " select t.itemvalue,t.itemname from tbl_code t where  itemcode='ROLE_FLAG'";
		try {
			List<?> list = this.getSQLObjects(sql);
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

	public List<T> loadRoles(Class<T> clazz) {
		String queryString = "SELECT u FROM Role u order by u.roleId ";
		return getBaseDao().find(queryString);
	}

	public List<T> queryRoles(Class<T> clazz, String roleName) {
		if (roleName == null || "".equals(roleName))
			return loadRoles(clazz);

		String queryString = "SELECT u FROM Role u WHERE u.roleName like '%"
				+ roleName + "%' order by u.roleId asc";
		return getBaseDao().find(queryString);
	}

	public List<T> queryCurrentUserRoles(Class<T> clazz, Integer userNo) {
		if (userNo == null || "".equals(userNo))
			return loadRoles(clazz);
		String queryString = "select  distinct r.roleCode  From "
				+ "Role r ,Right rg where  rg.rtRolecode=r.roleCode and rg.rtUserNo="
				+ userNo + " order by r.roleCode asc";
		return getBaseDao().find(queryString);
	}

	
	public String getRoleList(Map map,Page pager) {
		String roleName = (String) map.get("roleName");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select roleCode,roleName,roleFlag,roleFlagName,roleId,remark from ( ");
		sqlBuffer.append(" select role_code as roleCode,role_name as roleName,role_flag as roleFlag,decode(u.role_flag,1,'是','否') as roleFlagName,role_id as roleId,remark from  tbl_role u where 1=1");
		if (StringManagerUtils.isNotNull(roleName)) {
			sqlBuffer.append(" and u.role_Name like '%" + roleName + "%' ");
		}
		sqlBuffer.append(" order by u.role_id  asc ");
		sqlBuffer.append(" ) ");
		String json = "";
		String columns=service.showTableHeadersColumns("roleManage");
		try {
			json=this.findPageBySqlEntity(sqlBuffer.toString(),columns , pager );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public void addRole(T role) throws Exception {
		getBaseDao().addObject(role);
	}

	public void modifyRole(T role) throws Exception {
		getBaseDao().updateObject(role);
	}

	public void saveOrUpdateRole(T role) throws Exception {
		getBaseDao().saveOrUpdateObject(role);
	}

	public void deleteRole(int id, Class<T> clazz) throws Exception {
		T u = getBaseDao().getObject(clazz, id);
		getBaseDao().deleteObject(u);
	}

	/**<p>批量删除角色信息</p>
	 * 
	 * @param ids 需要删除的角色id信息用","分割的字符串
	 * @throws Exception
	 */
	public void bulkDelete(final String ids) throws Exception {
		final String hql = "DELETE Role u where u.roleId in (" + ids + ")";
		super.bulkObjectDelete(hql);
	}

	public T getRole(Class<T> clazz, int id) {
		return getBaseDao().getObject(clazz, id);
	}
}
