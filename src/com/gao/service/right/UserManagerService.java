package com.gao.service.right;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//import org.apache.commons.lang.xwork.StringUtils;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gao.model.Code;
import com.gao.model.Org;
import com.gao.model.User;
import com.gao.service.base.BaseService;
import com.gao.service.base.CommonDataService;
import com.gao.utils.Page;
import com.gao.utils.PagingConstants;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;

/**
 * <p>描述：用户管理模块服务类</p>
 * 
 * @author gao 2014-05-08
 * @param <T>
 */
@Service("userManagerService")
@SuppressWarnings("rawtypes")
public class UserManagerService<T> extends BaseService<T> {
	@Autowired
	private CommonDataService service;
	public T doLogin(String userName, String password) throws Exception {
		if (userName == null || password == null)
			return null;
		String queryString = "SELECT u FROM User u WHERE u.userId = '"
				+ userName + "' AND u.userPwd = '" + password + "'";
		List<T> users = getBaseDao().getObjects(queryString);
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}

	public List<T> queryUsers(String uname, Class<T> clazz) {
		if (uname == null || "".equals(uname))
			return this.getBaseDao().getAllObjects(clazz);
		String queryString = "SELECT u FROM User u WHERE u.uname like '"
				+ uname + "%'";
		return this.getBaseDao().getObjects(queryString);
	}

	public List<T> queryUsersByOrgId(String orgId, Class<T> clazz) {
		if (orgId == null || "".equals(orgId))
			return this.getBaseDao().getAllObjects(clazz);
		String queryString = "SELECT u FROM User u WHERE u.userOrgid  in  (" + orgId+")";
		return this.getBaseDao().getObjects(queryString);
	}

	public List<T> loadUsers(Class<T> clazz) {
		return this.getBaseDao().getAllObjects(clazz);
		// String queryString = "SELECT u FROM User u WHERE u.uname like '"+
		// uname + "%'";
		// return this.getBaseDao().getObjects(queryString);
	}

	/** 
	 * <P>描述：显示用户列表信息</p>
	 * @param pager  分页工具类
	 * @author gao 2014-05-08
	 * @param map 存储参数信息
	 * @return
	 * @throws SQLException 
	 * @throws IOException 
	 */
	
	public String doUserShow(Page pager, Map map,String orgIds) throws IOException, SQLException {
		String sql = "";
		StringBuffer sqlwhere = new StringBuffer();
		sqlwhere.append("select userNo,userName,userOrgid,userId,userPwd,userType,userTypeName,userPhone,userInEmail,userTitle,userTitleName,userRegtime,orgName,userQuickLogin,userQuickLoginName   from (");
		sqlwhere.append("select u.user_no as  userNo,u.user_name as userName,u.user_orgid as userOrgid,u.user_id as userId,");
		sqlwhere.append("u.user_pwd as userPwd,u.user_type as userType,r.role_name as userTypeName ,");
		sqlwhere.append("u.user_phone as userPhone,u.user_in_email as userInEmail,");
		sqlwhere.append("u.user_title as userTitle,c.itemname as userTitleName, to_char(u.user_regtime,'YYYY-MM-DD hh24:mi:ss') as userRegtime,");
		sqlwhere.append("u.user_quicklogin as userQuickLogin,decode(u.user_quicklogin,0,'否','是') as userQuickLoginName,");
		sqlwhere.append("o.org_name as orgName  from  tbl_code  c, tbl_user u left outer join  tbl_org o on u.user_orgid=o.org_id  left outer join tbl_role r on u.user_type=r.role_id ");
		sqlwhere.append("where  u.user_title=c.itemvalue  and c.itemcode='USER_TITLE' ");
		String userName = (String) map.get("userName");
		if (!"".equals(userName) && null != userName && userName.length() > 0) {
			sqlwhere.append(" and u.user_name like '%" + userName + "%'");
		}
		sqlwhere.append(" and u.user_orgid in (" + orgIds + ")");
		sqlwhere.append("   order by u.user_no )");
		String getResult = "";
		sql = sqlwhere.toString();
		String columnsString=	service.showTableHeadersColumns("userMange");
		getResult = this.findPageBySqlEntity(sql.toString(),columnsString,  "",pager);
		return getResult;

	}

	public String getUserList(Map map) {
		String userName = (String) map.get("userName");
		String user_Str = "";
		if (userName != null || !("".equals(userName))) {
			user_Str = " and u.userName like '%" + userName + "%'";
		}
		String hql1 = "SELECT u FROM User u,Org o where u.userOrgid=o.orgId "
				+ user_Str + " order by u.userNo asc";
		String hql2 = "SELECT o FROM User u,Org o where u.userOrgid=o.orgId "
				+ user_Str + " order by u.userNo asc";
		String hql3 = "SELECT o FROM User u,Code o,Org og where u.userOrgid=og.orgId and  u.userTitle=o.itemvalue  and o.itemcode='USER_TITLE' "
				+ user_Str + " order by u.userNo asc";
		Gson g = new Gson();
		List<User> users = null;
		List<Org> orgs = null;
		List<Code> titles = null;
		List<User> myusers = new ArrayList<User>();
		User u = null;
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String json = "";
		int total = this.getBaseDao().getRecordCountRows(hql1);
		try {
			users = this.getBaseDao().getListForPage(
					(Integer) map.get("offset"),
					(Integer) map.get(PagingConstants.PAGE_SIZE), hql1);
			orgs = this.getBaseDao().getListForPage(
					(Integer) map.get("offset"),
					(Integer) map.get(PagingConstants.PAGE_SIZE), hql2);
			titles = this.getBaseDao().getListForPage(
					(Integer) map.get("offset"),
					(Integer) map.get(PagingConstants.PAGE_SIZE), hql3);
			for (int i = 0; i < orgs.size(); i++) {
				u = new User();
				u.setOrgName(orgs.get(i).getOrgName());
				u.setUserTitleName(titles.get(i).getItemname());
				u.setUserNo(users.get(i).getUserNo());
				u.setUserName(users.get(i).getUserName());
				u.setUserOrgid(users.get(i).getUserOrgid());
				u.setUserId(users.get(i).getUserId());
				u.setUserPwd(users.get(i).getUserPwd());
				u.setUserType(users.get(i).getUserType());
				u.setUserPhone(users.get(i).getUserPhone());
				u.setUserInEmail(users.get(i).getUserInEmail());
				u.setUserTitle(users.get(i).getUserTitle());
				u.setUserRegtime(users.get(i).getUserRegtime());
				myusers.add(u);

			}
			jsonMap.put(PagingConstants.TOTAL, total);
			jsonMap.put(PagingConstants.LIST, myusers);
			json = g.toJson(jsonMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public String loadUserTitleType(String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		sql = " select t.itemvalue,t.itemname from tbl_code t where  itemcode='USER_TITLE'";
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
	
	/**<p>描述：获取用户类型的下拉菜单数据信息</p>
	 * 
	 * @param type 下拉菜单数据信息
	 * @return
	 * @throws Exception
	 */
	public String loadUserType(int userRoleId) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		sql = " select t.role_id,t.role_name from tbl_role t,(select * from tbl_role where role_id="+userRoleId+") t2 "
				+ " where t.role_code<>decode(t2.role_code,'sysAdmin',t.role_code||'0','sysAdmin') "
				+ " order by t.role_id";
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
	public boolean judgeUserExistsOrNot(String userId) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(userId)) {
			String queryString = "SELECT u.userId FROM User u where u.userId='"
					+ userId + "' order by u.userNo ";
			List<User> list = getBaseDao().getObjects(queryString);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public String sendZYBZTitleType(String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		sql = " select t.itemvalue,t.itemname from tbl_code t where  itemcode='USER_TITLE' and t.itemvalue not in(0,4)";
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

	public void addUser(T user) throws Exception {
		this.getBaseDao().addObject(user);
	}

	/** <p>用户信息修改方法</p>
	 * @param user 当前要修改的用户对象
	 * @throws Exception
	 */
	public void modifyUser(T user) throws Exception {
		this.getBaseDao().updateObject(user);
	}

	public void deleteUser(int id, Class<T> clazz) throws Exception {
		T u = this.getBaseDao().getObject(clazz, id);
		this.getBaseDao().deleteObject(u);
	}

	/**
	 * <p>用户管理——批量删除用户信息</p>
	 * @param ids
	 * @throws Exception
	 */
	public void bulkDelete(final String ids) throws Exception {
		Log.debug("bulkDelete" + ids);
		String hql = "DELETE User u where u.userNo in (" + ids + ")";
		this.getBaseDao().bulkObjectDelete(hql);
	}

	public T getUser(Class<T> clazz, int id) {
		return this.getBaseDao().getObject(clazz, id);
	}

	public String getUserList() throws Exception {
		StringBuffer result_json = new StringBuffer();
		String queryString = "select t.user_name,t.user_id,t.user_pwd,t.user_no "
				+ " from tbl_user t ,tbl_org org "
				+ " where (t.user_orgid=org.org_id or t.user_orgid=0 ) "
				+ " and t.user_quicklogin=1  "
				+ " group by t.user_name,t.user_id,t.user_pwd,t.user_no "
				+ " order by t.user_no";
		List<?> list = this.findCallSql(queryString);
		result_json.append("[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"username\":\""+obj[0]+"\",");
			result_json.append("\"useraccount\":\""+obj[1]+"\",");
			result_json.append("\"userpwd\":\""+obj[2]+"\"},");
		}
		if(list.size()>0){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString();
	}
}
