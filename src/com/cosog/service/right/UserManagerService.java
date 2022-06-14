package com.cosog.service.right;

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

import com.cosog.model.Code;
import com.cosog.model.Org;
import com.cosog.model.User;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.RPCDeviceInfo;
import com.cosog.model.calculate.UserInfo;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Page;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.SerializeObjectUnils;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

import redis.clients.jedis.Jedis;

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
		List<T> users = getBaseDao().find(queryString);
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
		return this.getBaseDao().find(queryString);
	}

	public List<T> queryUsersByOrgId(String orgId, Class<T> clazz) {
		if (orgId == null || "".equals(orgId))
			return this.getBaseDao().getAllObjects(clazz);
		String queryString = "SELECT u FROM User u WHERE u.userOrgid  in  (" + orgId+")";
		return this.getBaseDao().find(queryString);
	}

	public List<T> loadUsers(Class<T> clazz) {
		return this.getBaseDao().getAllObjects(clazz);
		// String queryString = "SELECT u FROM User u WHERE u.uname like '"+
		// uname + "%'";
		// return this.getBaseDao().find(queryString);
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
	
	public String doUserShow(Page pager, Map map,String orgIds,User user) throws IOException, SQLException {
		StringBuffer sqlwhere = new StringBuffer();
		StringBuffer result_json = new StringBuffer();
		String columns=	service.showTableHeadersColumns("userMange");
		String userName = (String) map.get("userName");
		String sql="select u.user_no as  userNo,u.user_name as userName,u.user_orgid as userOrgid,o.org_name as orgName,u.user_id as userId,"
				+ " u.user_pwd as userPwd,u.user_type as userType,r.role_name as userTypeName,u.user_phone as userPhone,u.user_in_email as userInEmail,"
				+ " to_char(u.user_regtime,'YYYY-MM-DD hh24:mi:ss') as userRegtime,"
				+ " u.user_quicklogin as userQuickLogin,decode(u.user_quicklogin,0,'否','是') as userQuickLoginName,"
				+ " u.user_receivesms as receiveSMS,decode(u.user_receivesms,1,'是','否') as receiveSMSName,"
				+ " u.user_receivemail as receiveMail,decode(u.user_receivemail,1,'是','否') as receiveMailName,"
				+ " u.user_enable as userEnable,decode(u.user_enable,1,'使能','失效') as userEnableName"
				+ " from tbl_user u"
				+ " left outer join  tbl_org o on u.user_orgid=o.org_id"
				+ " left outer join tbl_role r on u.user_type=r.role_id"
				+ " where u.user_orgid in (" + orgIds + ")"
				+ " and ("
				+ " r.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
				+ " or u.user_no=(select t2.user_no from tbl_user t2 where  t2.user_no="+user.getUserNo()+")"
				+ ")";
		if (!"".equals(userName) && null != userName && userName.length() > 0) {
			sql+=" and u.user_name like '%" + userName + "%'";
		}
		sql+=" order by r.role_level,user_no,u.user_no";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()
//		+",\"currentId\":"+user.getUserNo()
//		+",\"currentLevel\":"+currentLevel
//		+",\"currentShowLevel\":"+currentShowLevel
//		+",\"currentFlag\":"+currentFlag
		+",\"columns\":"+columns+",\"totalRoot\":[");
		
		for (Object o : list) {
			Object[] obj = (Object[]) o;
			result_json.append("{\"userNo\":"+obj[0]+",");
			result_json.append("\"userName\":\""+obj[1]+"\",");
			result_json.append("\"userOrgid\":\""+obj[2]+"\",");
			result_json.append("\"orgName\":\""+obj[3]+"\",");
			result_json.append("\"userId\":\""+obj[4]+"\",");
			result_json.append("\"userPwd\":\""+obj[5]+"\",");
			result_json.append("\"userType\":\""+obj[6]+"\",");
			result_json.append("\"userTypeName\":\""+obj[7]+"\",");
			result_json.append("\"userPhone\":\""+obj[8]+"\",");
			result_json.append("\"userInEmail\":\""+obj[9]+"\",");
			result_json.append("\"userRegtime\":\""+obj[10]+"\",");
			result_json.append("\"userQuickLogin\":\""+obj[11]+"\",");
			result_json.append("\"userQuickLoginName\":\""+obj[12]+"\",");
			result_json.append("\"receiveSMS\":\""+obj[13]+"\",");
			result_json.append("\"receiveSMSName\":\""+obj[14]+"\",");
			result_json.append("\"receiveMail\":\""+obj[15]+"\",");
			result_json.append("\"receiveMailName\":\""+obj[16]+"\",");
			result_json.append("\"userEnable\":\""+obj[17]+"\",");
			result_json.append("\"userEnableName\":\""+obj[18]+"\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
		
//		return this.findPageBySqlEntity(sql.toString(),columnsString,  "",pager);

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
				u.setUserNo(users.get(i).getUserNo());
				u.setUserName(users.get(i).getUserName());
				u.setUserOrgid(users.get(i).getUserOrgid());
				u.setUserId(users.get(i).getUserId());
				u.setUserPwd(users.get(i).getUserPwd());
				u.setUserType(users.get(i).getUserType());
				u.setUserPhone(users.get(i).getUserPhone());
				u.setUserInEmail(users.get(i).getUserInEmail());
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
				if (result_json.toString().endsWith(",")) {
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
	public String loadUserType(User user) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		sql = " select t.role_id,t.role_name from tbl_role t"
				+ " where t.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
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
				if (result_json.toString().endsWith(",")) {
					result_json.deleteCharAt(result_json.length() - 1);
				}
			}
			result_json.append("]");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}
	public boolean judgeUserExistsOrNot(String userId,String userNo) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(userId)) {
			String queryString = "SELECT u.userId FROM User u where u.userId='"+ userId + "' ";
			if(StringManagerUtils.isNotNull(userNo)){//当前是更新用户
				queryString+=" and u.userNo<>"+userNo;
			}
			queryString+= "order by u.userNo ";
			List<User> list = getBaseDao().find(queryString);
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
			List<?> list = this.find(sql);
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
				if (result_json.toString().endsWith(",")) {
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
	
	public void saveSystemLog(User user) throws Exception {
		this.getBaseDao().saveSystemLog(user);
	}
	
	public String getUserOrgChangeUserList(Page pager,String orgIds,String userName,User user) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		
		String sql = "select t.user_no,t.user_name,t.user_id from tbl_user t,tbl_role r"
				+ " where t.user_type=r.role_id and t.user_orgid in (" + orgIds + ")"
				+ " and ("
				+ " r.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
				+ " or t.user_no=(select t2.user_no from tbl_user t2 where  t2.user_no="+user.getUserNo()+")"
				+ ")";
		if(StringManagerUtils.isNotNull(userName)){
			sql+=" and t.user_name like '%"+userName+"%'";
		}	
		sql+= " order by r.role_level,t.user_no";
		String columns = "["
				+ "{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\"用户名称\",\"dataIndex\":\"userName\",width:120 ,children:[] },"
				+ "{ \"header\":\"用户账号\",\"dataIndex\":\"userID\",width:120 ,children:[] }"
				+ "]";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");

		for (Object o : list) {
			Object[] obj = (Object[]) o;
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"userName\":\""+obj[1]+"\",");
			result_json.append("\"userID\":\""+obj[2]+"\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public void changeUserOrg(String selectedUserId,String selectedOrgId) throws Exception {
		StringBuffer result_json = new StringBuffer();
		if(StringManagerUtils.stringToInteger(selectedOrgId)>0 && StringManagerUtils.isNotNull(selectedUserId)){
			String sql = "update tbl_user t set t.user_orgid="+selectedOrgId+" where t.user_no in ("+selectedUserId+")";
			this.getBaseDao().updateOrDeleteBySql(sql);
			
			Jedis jedis=null;
			try{
				jedis = new Jedis();

				if(!jedis.exists("UserInfo".getBytes())){
					MemoryDataManagerTask.loadUserInfo(null);
				}
				List<byte[]> userInfoByteList =jedis.hvals("UserInfo".getBytes());
				for(int i=0;i<userInfoByteList.size();i++){
					Object obj = SerializeObjectUnils.unserizlize(userInfoByteList.get(i));
					if (obj instanceof UserInfo) {
						UserInfo userInfo=(UserInfo)obj;
						if(StringManagerUtils.existOrNot(selectedUserId.split(","), userInfo.getUserNo()+"", false)){
							userInfo.setUserOrgid(StringManagerUtils.stringToInteger(selectedOrgId));
							jedis.hset("UserInfo".getBytes(), userInfo.getUserId().getBytes(), SerializeObjectUnils.serialize(userInfo));
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				jedis=null;
			}
			if(jedis!=null){
				jedis.disconnect();
				jedis.close();
			}
		}
		
	}
}
