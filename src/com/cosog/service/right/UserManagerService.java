package com.cosog.service.right;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


//import org.apache.commons.lang.xwork.StringUtils;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.Code;
import com.cosog.model.Org;
import com.cosog.model.RoleModule;
import com.cosog.model.User;
import com.cosog.model.calculate.PCPDeviceInfo;
import com.cosog.model.calculate.SRPDeviceInfo;
import com.cosog.model.calculate.UserInfo;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Config;
import com.cosog.utils.Page;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.RedisUtil;
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
	
	public int userCheck(String userName,String password){
		int result=0;
		if (!StringManagerUtils.isNotNull(userName)) {
			result=-1; //用户名不能为空
		} else if (!StringManagerUtils.isNotNull(password)) {
			result=-2; //用户密码不能为空
		} else {
			User user;
			try {
				user = (User) this.doLogin(userName, StringManagerUtils.stringToMD5(password));
				if (user != null&&user.getUserEnable()==1) {
					result=1;// 验证成功
				}else if(user != null && user.getUserEnable()!=1){
					result=-4;//用户被禁用
				} else {
					result=-3;//账号或密码错误
				}
			} catch (Exception e) {
				e.printStackTrace();
				result=-5;//其他
			}
		}
		return result;
	}
	
	public User getUser(String userName,String password){
		User user=null;
		try {
			user = (User) this.doLogin(userName, StringManagerUtils.stringToMD5(password));
			setUserLanguage(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public List<T> queryUsers(String uname, Class<T> clazz) {
		if (uname == null || "".equals(uname))
			return this.getBaseDao().getAllObjects(clazz);
		String queryString = "SELECT u FROM User u WHERE u.uname like '"
				+ uname + "%'";
		return this.getBaseDao().find(queryString);
	}
	
	public List<T> queryUsersByNo(int userNo, Class<T> clazz) {
		String queryString = "SELECT u FROM User u WHERE u.userNo = "+ userNo ;
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
		StringBuffer role_json = new StringBuffer();
		StringBuffer language_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		Map<String,Code> languageCodeMap=MemoryDataManagerTask.getCodeMap("LANGUAGE",user.getLanguageName());
		String columns=	service.showTableHeadersColumns("orgAndUser_UserManage",user.getLanguageName());
		String userName = (String) map.get("userName");
		String roleSql = " select t.role_id,t.role_name from tbl_role t"
				+ " where t.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
				+ " order by t.role_id";
		
		String sql="select u.user_no as  userNo,u.user_name as userName,u.user_orgid as userOrgid,o.org_name_"+user.getLanguageName()+" as orgName,u.user_id as userId,"
				+ " u.user_pwd as userPwd,"
				+ " u.user_type as userType,r.role_name as userTypeName,"
				+ " u.user_phone as userPhone,u.user_in_email as userInEmail,"
				+ " to_char(u.user_regtime,'YYYY-MM-DD hh24:mi:ss') as userRegtime,"
				+ " u.user_quicklogin as userQuickLogin,decode(u.user_quicklogin,0,'"+languageResourceMap.get("no")+"','"+languageResourceMap.get("yes")+"') as userQuickLoginName,"
				+ " u.user_receivesms as receiveSMS,decode(u.user_receivesms,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as receiveSMSName,"
				+ " u.user_receivemail as receiveMail,decode(u.user_receivemail,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as receiveMailName,"
				+ " u.user_enable as userEnable,decode(u.user_enable,1,'"+languageResourceMap.get("enable")+"','"+languageResourceMap.get("disable")+"') as userEnableName,"
				+ " u.user_language as userLanguage,"
				+ " o.allpath_"+user.getLanguageName()+""
				+ " from tbl_user u"
				+ " left outer join  VIW_ORG o on u.user_orgid=o.org_id"
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
		
		List<?> roleList = this.findCallSql(roleSql);
		List<?> list = this.findCallSql(sql);
		role_json.append("[");
		language_json.append("[");
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",");
		for (Object o : roleList) {
			Object[] obj = (Object[]) o;
			role_json.append("['"+obj[1]+"','"+obj[1]+"'],");
		}
		if (role_json.toString().endsWith(",")) {
			role_json.deleteCharAt(role_json.length() - 1);
		}
		role_json.append("]");
		
		Iterator<Map.Entry<String,Code>> it = languageCodeMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Code> entry = it.next();
			Code c=entry.getValue();
			language_json.append("['"+c.getItemname()+"','"+c.getItemname()+"'],");
		}
		if (language_json.toString().endsWith(",")) {
			language_json.deleteCharAt(language_json.length() - 1);
		}
		language_json.append("]");
		
		result_json.append("\"roleList\":"+role_json+",");
		result_json.append("\"languageList\":"+language_json+",");
		result_json.append("\"totalRoot\":[");
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
			result_json.append("\"userQuickLoginName\":"+(StringManagerUtils.stringToInteger(obj[11]+"")==1)+",");
			result_json.append("\"receiveSMS\":\""+obj[13]+"\",");
			result_json.append("\"receiveSMSName\":"+(StringManagerUtils.stringToInteger(obj[13]+"")==1)+",");
			result_json.append("\"receiveMail\":\""+obj[15]+"\",");
			result_json.append("\"receiveMailName\":"+(StringManagerUtils.stringToInteger(obj[15]+"")==1)+",");
			result_json.append("\"userEnable\":\""+obj[17]+"\",");
			result_json.append("\"userEnableName\":"+(StringManagerUtils.stringToInteger(obj[17]+"")==1)+",");
			result_json.append("\"userLanguage\":\""+obj[19]+"\",");
			result_json.append("\"userLanguageName\":\""+(languageCodeMap.get(obj[19]+"")!=null?languageCodeMap.get(obj[19]+"").getItemname():"")+"\",");
			result_json.append("\"allPath\":\""+obj[20]+"\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
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
	
	public String loadLanguageList(String language) throws Exception {
		StringBuffer result_json = new StringBuffer();
		Map<String,Code> codeMap=MemoryDataManagerTask.getCodeMap("LANGUAGE",language);
		result_json.append("[");
		Iterator<Map.Entry<String,Code>> it = codeMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Code> entry = it.next();
			Code c=entry.getValue();
			result_json.append("{boxkey:\"" + c.getItemvalue() + "\",");
			result_json.append("boxval:\"" + c.getItemname() + "\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString();
	}
	
	public String getUserRoleModules(User user){
		String userModuleSql="select rm.rm_id, rm.rm_moduleid,rm.rm_roleid,rm.rm_matrix,m.md_name_"+user.getLanguageName()+",m.md_code,r.role_name "
				+ " from tbl_module m,tbl_module2role rm,tbl_role r,tbl_user u "
				+ " where u.user_type=r.role_id and r.role_id=rm.rm_roleid and rm.rm_moduleid=m.md_id "
				+ " and u.user_no= "+user.getUserNo()
				+ " order by m.md_seq";
		List<?> userModuleList=getBaseDao().findCallSql(userModuleSql);
		List<RoleModule> roleModuleList= new ArrayList<>();
		if(userModuleList.size()>0){
			for(int i=0;i<userModuleList.size();i++){
				Object[] obj=(Object[]) userModuleList.get(i);
				RoleModule roleModule=new RoleModule();
				roleModule.setRmId(StringManagerUtils.stringToInteger(obj[0]+""));
				roleModule.setRmModuleid(StringManagerUtils.stringToInteger(obj[1]+""));
				roleModule.setRmRoleId(StringManagerUtils.stringToInteger(obj[2]+""));
				roleModule.setRmMatrix(obj[3]+"");
				roleModule.setMdName(obj[4]+"");
				roleModule.setMdCode(obj[5]+"");
				roleModule.setRoleName(obj[6]+"");
				if(StringManagerUtils.isNotNull(roleModule.getRmMatrix()) && roleModule.getRmMatrix().split(",").length==3 ){
					String[] matrixArr=roleModule.getRmMatrix().split(",");
					roleModule.setViewFlag(StringManagerUtils.stringToInteger(matrixArr[0]));
					roleModule.setEditFlag(StringManagerUtils.stringToInteger(matrixArr[1]));
					roleModule.setControlFlag(StringManagerUtils.stringToInteger(matrixArr[2]));
				}else{
					roleModule.setViewFlag(0);
					roleModule.setEditFlag(0);
					roleModule.setControlFlag(0);
				}
				roleModuleList.add(roleModule);
			}
		}
		
		StringBuffer roleModuleStringBuff = new StringBuffer();
		roleModuleStringBuff.append("[");
		for(int i=0;i<roleModuleList.size();i++){
			roleModuleStringBuff.append("{\"rmId\":"+roleModuleList.get(i).getRmId()+",");
			roleModuleStringBuff.append("\"rmModuleid\":"+roleModuleList.get(i).getRmModuleid()+",");
			roleModuleStringBuff.append("\"rmRoleId\":"+roleModuleList.get(i).getRmRoleId()+",");
			roleModuleStringBuff.append("\"rmMatrix\":\""+roleModuleList.get(i).getRmMatrix()+"\",");
			roleModuleStringBuff.append("\"mdName\":\""+roleModuleList.get(i).getMdName()+"\",");
			roleModuleStringBuff.append("\"mdCode\":\""+roleModuleList.get(i).getMdCode()+"\",");
			roleModuleStringBuff.append("\"roleName\":\""+roleModuleList.get(i).getRoleName()+"\",");
			roleModuleStringBuff.append("\"viewFlag\":"+roleModuleList.get(i).getViewFlag()+",");
			roleModuleStringBuff.append("\"editFlag\":"+roleModuleList.get(i).getEditFlag()+",");
			roleModuleStringBuff.append("\"controlFlag\":"+roleModuleList.get(i).getControlFlag()+"},");
		}
		if(roleModuleStringBuff.toString().endsWith(",")){
			roleModuleStringBuff.deleteCharAt(roleModuleStringBuff.length() - 1);
		}
		roleModuleStringBuff.append("]");
		return roleModuleStringBuff.toString();
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
	
	public String getUserOrgChangeUserList(Page pager,String orgIds,String userName,User user,String language) throws Exception {
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(language);
		StringBuffer result_json = new StringBuffer();
		
		String sql = "select t.user_no,t.user_name,t.user_id,o.allpath_"+language+" "
				+ " from tbl_user t,tbl_role r,viw_org o"
				+ " where t.user_type=r.role_id and t.user_orgid=o.org_id"
				+ " and t.user_orgid in (" + orgIds + ")"
				+ " and ("
				+ " r.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
				+ " or t.user_no=(select t2.user_no from tbl_user t2 where  t2.user_no="+user.getUserNo()+")"
				+ ")";
		if(StringManagerUtils.isNotNull(userName)){
			sql+=" and t.user_name like '%"+userName+"%'";
		}	
		sql+= " order by r.role_level,t.user_no";
		String columns = "["
				+ "{ \"header\":\""+languageResourceMap.get("idx")+"\",\"dataIndex\":\"id\",width:50 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("userName")+"\",\"dataIndex\":\"userName\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("userAccount")+"\",\"dataIndex\":\"userID\",width:120 ,children:[] },"
				+ "{ \"header\":\""+languageResourceMap.get("owningOrg")+"\",\"dataIndex\":\"orgName\",width:120 ,children:[] }"
				+ "]";
		List<?> list = this.findCallSql(sql);
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"columns\":"+columns+",\"totalRoot\":[");

		for (Object o : list) {
			Object[] obj = (Object[]) o;
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"userName\":\""+obj[1]+"\",");
			result_json.append("\"userID\":\""+obj[2]+"\",");
			result_json.append("\"orgName\":\""+obj[3]+"\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public void changeUserOrg(String selectedUserId,String selectedOrgId) throws Exception {
		if(StringManagerUtils.stringToInteger(selectedOrgId)>0 && StringManagerUtils.isNotNull(selectedUserId)){
			String sql = "update tbl_user t set t.user_orgid="+selectedOrgId+" where t.user_no in ("+selectedUserId+")";
			this.getBaseDao().updateOrDeleteBySql(sql);
			try{
				String [] userNoArr=selectedUserId.split(",");
				List<String> userNoList=new ArrayList<String>();
				for(int i=0;i<userNoArr.length;i++){
					userNoList.add(userNoArr[i]);
				}
				MemoryDataManagerTask.loadUserInfo(userNoList,0,"update");
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				
			}
		}
	}
	
	public int updateUserInfo(User user,boolean isLoginedUser) throws Exception {
		int r=0;
		boolean flag = this.judgeUserExistsOrNot(user.getUserId(),user.getUserNo()+"");
		
		if(flag){
			r=2;
		}else{
			String sql = "update tbl_user t set ";
			if(!isLoginedUser){//当前登录用户不可修改账号、角色、使能状态
				sql+= "t.user_id='"+user.getUserId()+"', "
						+ "t.user_type=(select r.role_id from tbl_role r where r.role_name='"+user.getUserTypeName()+"'), "
						+ "t.user_enable="+user.getUserEnable()+", ";
			}	
			sql+= "t.user_name='"+user.getUserName()+"', "
				+ "t.user_language="+MemoryDataManagerTask.getCodeValue("LANGUAGE", user.getLanguageName(), user.getLanguageName())+","
				+ "t.user_phone='"+user.getUserPhone()+"', "
				+ "t.user_in_email='"+user.getUserInEmail()+"', "
				+ "t.user_quicklogin="+user.getUserQuickLogin()+", "
				+ "t.user_receivesms="+user.getReceiveSMS()+", "
				+ "t.user_receivemail="+user.getReceiveMail()+" "
				+ "where t.user_no = "+user.getUserNo();
			int result=this.getBaseDao().updateOrDeleteBySql(sql);
			if(result>0){
				r=1;
			}
		}
		return r;
	}
	
	public int updateUserPassword(User user) throws Exception {
		int r=0;
		String sql = "update tbl_user t set t.user_pwd='"+user.getUserPwd()+"' where t.user_no = "+user.getUserNo();
		r=this.getBaseDao().updateOrDeleteBySql(sql);
		return r;
	}
	
	public String getUserEmail(User user) throws Exception {
		String email="";
		String sql="select t.user_in_email from tbl_user t where t.user_no = "+user.getUserNo();
		List<?> list = this.findCallSql(sql);
		if(list.size()>0 && list.get(0)!=null){
			email=list.get(0).toString();
		}
		return email;
	}
	
	//递归查询用户所在组织及其子节点
	public String findChildIds(String user){
		String childIds="0";
		StringBuffer orgIdString = new StringBuffer();
		List<?> list;
		String queryString="select org_id from tbl_org t start with org_id=(select u.user_orgid from tbl_user u where u.user_id='"+user+"' ) connect by prior  org_id=org_parent";
		list=getBaseDao().findCallSql(queryString);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				orgIdString.append(list.get(i)+",");
			}
			orgIdString.deleteCharAt(orgIdString.length() - 1);
			childIds=orgIdString.toString();
		}
		return childIds;
	}
	
	public void setUserLanguage(User user){
		String languageName=MemoryDataManagerTask.getCodeName("LANGUAGE",user.getLanguage()+"", Config.getInstance().configFile.getAp().getOthers().getLoginLanguage());
		user.setLanguageName(languageName);
	}
	
	public void setUserRoleRight(User user){
		String sql="select t.role_level,t.showlevel,t.role_videokeyedit,t.role_languageedit "
				+ " from tbl_role t,tbl_user t2 "
				+ " where t2.user_type=t.role_id "
				+ " and t2.user_no="+user.getUserNo();
		String userModuleSql="select rm.rm_id, rm.rm_moduleid,rm.rm_roleid,rm.rm_matrix,m.md_name_"+user.getLanguageName()+",m.md_code,r.role_name "
				+ " from tbl_module m,tbl_module2role rm,tbl_role r,tbl_user u "
				+ " where u.user_type=r.role_id and r.role_id=rm.rm_roleid and rm.rm_moduleid=m.md_id "
				+ " and u.user_no= "+user.getUserNo()
				+ " order by m.md_seq";
		List<?> list=getBaseDao().findCallSql(sql);
		List<?> userModuleList=getBaseDao().findCallSql(userModuleSql);
		if(list.size()>0){
			Object[] obj=(Object[]) list.get(0);
			user.setRoleLevel(StringManagerUtils.stringToInteger(obj[0]+""));
			user.setRoleShowLevel(StringManagerUtils.stringToInteger(obj[1]+""));
			user.setRoleVideoKeyEdit(StringManagerUtils.stringToInteger(obj[2]+""));
			user.setRoleLanguageEdit(StringManagerUtils.stringToInteger(obj[3]+""));
		}
		List<RoleModule> roleModuleList= new ArrayList<>();
		if(userModuleList.size()>0){
			for(int i=0;i<userModuleList.size();i++){
				Object[] obj=(Object[]) userModuleList.get(i);
				RoleModule roleModule=new RoleModule();
				roleModule.setRmId(StringManagerUtils.stringToInteger(obj[0]+""));
				roleModule.setRmModuleid(StringManagerUtils.stringToInteger(obj[1]+""));
				roleModule.setRmRoleId(StringManagerUtils.stringToInteger(obj[2]+""));
				roleModule.setRmMatrix(obj[3]+"");
				roleModule.setMdName(obj[4]+"");
				roleModule.setMdCode(obj[5]+"");
				roleModule.setRoleName(obj[6]+"");
				if(StringManagerUtils.isNotNull(roleModule.getRmMatrix()) && roleModule.getRmMatrix().split(",").length==3 ){
					String[] matrixArr=roleModule.getRmMatrix().split(",");
					roleModule.setViewFlag(StringManagerUtils.stringToInteger(matrixArr[0]));
					roleModule.setEditFlag(StringManagerUtils.stringToInteger(matrixArr[1]));
					roleModule.setControlFlag(StringManagerUtils.stringToInteger(matrixArr[2]));
				}else{
					roleModule.setViewFlag(0);
					roleModule.setEditFlag(0);
					roleModule.setControlFlag(0);
				}
				roleModuleList.add(roleModule);
			}
		}
		
		StringBuffer roleModuleStringBuff = new StringBuffer();
		roleModuleStringBuff.append("[");
		for(int i=0;i<roleModuleList.size();i++){
			roleModuleStringBuff.append("{\"rmId\":"+roleModuleList.get(i).getRmId()+",");
			roleModuleStringBuff.append("\"rmModuleid\":"+roleModuleList.get(i).getRmModuleid()+",");
			roleModuleStringBuff.append("\"rmRoleId\":"+roleModuleList.get(i).getRmRoleId()+",");
			roleModuleStringBuff.append("\"rmMatrix\":\""+roleModuleList.get(i).getRmMatrix()+"\",");
			roleModuleStringBuff.append("\"mdName\":\""+roleModuleList.get(i).getMdName()+"\",");
			roleModuleStringBuff.append("\"mdCode\":\""+roleModuleList.get(i).getMdCode()+"\",");
			roleModuleStringBuff.append("\"roleName\":\""+roleModuleList.get(i).getRoleName()+"\",");
			roleModuleStringBuff.append("\"viewFlag\":"+roleModuleList.get(i).getViewFlag()+",");
			roleModuleStringBuff.append("\"editFlag\":"+roleModuleList.get(i).getEditFlag()+",");
			roleModuleStringBuff.append("\"controlFlag\":"+roleModuleList.get(i).getControlFlag()+"},");
		}
		if(roleModuleStringBuff.toString().endsWith(",")){
			roleModuleStringBuff.deleteCharAt(roleModuleStringBuff.length() - 1);
		}
		roleModuleStringBuff.append("]");
		user.setModuleList(roleModuleStringBuff.toString());
	}
	
	public String loadUserComboxList(Page pager,String orgId,String userId,User user) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		String selectAll="";
		if(user!=null){
			selectAll=MemoryDataManagerTask.getLanguageResourceItem(user.getLanguageName(),"selectAll");
		}
		
		if(user!=null){
			String sql = "select t.user_id as boxkey,t.user_id as boxval"
					+ " from TBL_USER t,tbl_role r "
					+ " where t.user_type=r.role_id "
					+ " and t.user_orgid in("+orgId+") "
					+ " and (r.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+") "
					+ " or t.user_no="+user.getUserNo()+")";
			if (StringManagerUtils.isNotNull(userId)) {
				sql += " and t.user_id like '%" + userId + "%'";
			}
			sql += " order by r.role_level,t.user_id";
			sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
			sqlCuswhere.append(""+sql);
			int maxvalue=pager.getLimit()+pager.getStart();
			sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
			sqlCuswhere.append(" where rn >"+pager.getStart());
			String finalsql=sqlCuswhere.toString();
			try {
				int totals=this.getTotalCountRows(sql);
				List<?> list = this.findCallSql(finalsql);
				result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\""+selectAll+"\"},");
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
				result_json.append("]}");

			} catch (Exception e) {
				e.printStackTrace();
				result_json = new StringBuffer();
				result_json.append("{\"totals\":"+0+",\"list\":[{boxkey:\"\",boxval:\""+selectAll+"\"}]}");
			}
		}else{
			result_json = new StringBuffer();
			result_json.append("{\"totals\":"+0+",\"list\":[{boxkey:\"\",boxval:\""+selectAll+"\"}]}");
		}
		
		
		return result_json.toString();
	}
}
