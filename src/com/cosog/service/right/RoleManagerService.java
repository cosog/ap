package com.cosog.service.right;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.Code;
import com.cosog.model.Role;
import com.cosog.model.User;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.MemoryDataManagerTask;
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

	public List<T> loadRoles(Class<T> clazz) {
		String queryString = "SELECT u FROM Role u order by u.roleId ";
		return getBaseDao().find(queryString);
	}
	
	public List<T> loadRolesById(Class<T> clazz,int roleId) {
		String queryString = "SELECT u FROM Role u where roleId= "+roleId;
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

	
	public String getRoleList(Map map,Page pager,User user) {
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		String roleName = (String) map.get("roleName");
		StringBuffer result_json = new StringBuffer();
		String currentTabs=user.getDeviceTypeIds();
		String currentId="";
		String currentLevel="";
		String currentShowLevel="";
		String currentVideoKeyEdit="";
		String currentLanguageEdit="";
		String currentRoleLevel="select t3.role_id,t3.role_level,t3.showLevel,t3.role_videokeyedit,t3.role_languageedit "
				+ "from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo();
		String sql="select role_id as roleId,role_name as roleName,role_level as roleLevel,"
				+ " role_videokeyedit as roleVideoKeyEdit,decode(t.role_videokeyedit,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as roleVideoKeyEditName,"
				+ " role_languageedit as roleLanguageEdit,decode(t.role_languageedit,1,'"+languageResourceMap.get("yes")+"','"+languageResourceMap.get("no")+"') as roleLanguageEditName,"
				+ " showLevel,remark"
				+ " from  viw_role t"
				+ " where 1=1 "
				+ " and t.role_id not in( select distinct(t5.rd_roleid) from TBL_DEVICETYPE2ROLE t5 where t5.rd_devicetypeid not in("+currentTabs+") )"
				+ " and ( t.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
						+ " or t.role_id=(select t3.role_id from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+") )";
		if (StringManagerUtils.isNotNull(roleName)) {
			sql+=" and t.role_Name like '%" + roleName + "%' ";
		}
		sql+=" order by t.role_id ";
		String columns="[]";
		List<?> list = this.findCallSql(sql);
		List<?> currentUserLevelList = this.findCallSql(currentRoleLevel);
		if(currentUserLevelList.size()>0){
			Object[] obj = (Object[]) currentUserLevelList.get(0);
			currentId=obj[0]+"";
			currentLevel=obj[1]+"";
			currentShowLevel=obj[2]+"";
			currentVideoKeyEdit=obj[3]+"";
			currentLanguageEdit=obj[4]+"";
		}
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+","
		+ "\"currentId\":"+currentId+","
		+ "\"currentLevel\":"+currentLevel+","
		+ "\"currentShowLevel\":"+currentShowLevel+","
		+ "\"currentVideoKeyEdit\":"+currentVideoKeyEdit+","
		+ "\"currentLanguageEdit\":"+currentLanguageEdit+","
		+ "\"columns\":"+columns+","
		+ "\"totalRoot\":[");
		
		for (Object o : list) {
			Object[] obj = (Object[]) o;
			result_json.append("{\"roleId\":"+obj[0]+",");
			result_json.append("\"roleName\":\""+obj[1]+"\",");
			result_json.append("\"roleLevel\":\""+obj[2]+"\",");
			result_json.append("\"roleVideoKeyEdit\":\""+obj[3]+"\",");
			result_json.append("\"roleVideoKeyEditName\":"+(StringManagerUtils.stringToInteger(obj[3]+"")==1)+",");
			result_json.append("\"roleLanguageEdit\":\""+obj[5]+"\",");
			result_json.append("\"roleLanguageEditName\":"+(StringManagerUtils.stringToInteger(obj[5]+"")==1)+",");
			result_json.append("\"showLevel\":\""+obj[7]+"\",");
			result_json.append("\"remark\":\""+obj[8]+"\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
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
	
	public void saveOrUpdateRoleModule(T roleModule) throws Exception {
		getBaseDao().saveOrUpdateObject(roleModule);
	}
	
	public void saveOrUpdateRoleDeviceType(T roleLanguage) throws Exception {
		getBaseDao().saveOrUpdateObject(roleLanguage);
	}
	
	public void saveOrUpdateRoleLanguage(T roleTab) throws Exception {
		getBaseDao().saveOrUpdateObject(roleTab);
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
	
	public int updateRoleInfo(Role role,boolean isLoginedUserRole) throws Exception {
		int r=0;
		boolean flag = this.judgeRoleExistsOrNot(role.getRoleName(),role.getRoleId()+"");
		if(flag){
			r=2;
		}else{
			String sql = "update tbl_role t set ";
			if(!isLoginedUserRole){//当前登录用户不可修改账号、角色、使能状态
				sql+= " t.role_level="+role.getRoleLevel()+", "
						+ " t.role_videokeyedit="+role.getRoleVideoKeyEdit()+", "
						+ " t.role_languageedit="+role.getRoleLanguageEdit()+", "
						+ " t.showlevel="+role.getShowLevel()+", ";
			}	
			sql+= " t.role_name='"+role.getRoleName()+"', "
				+ " t.remark='"+role.getRemark()+"' "
				+ " where t.role_id = "+role.getRoleId();
			int result=this.getBaseDao().updateOrDeleteBySql(sql);
			if(result>0){
				r=1;
			}
		}
		return r;
	}
	
	public boolean judgeRoleExistsOrNot(String roleName,String roleId) {
		boolean flag = false;
		if (StringManagerUtils.isNotNull(roleName)) {
			String queryString = "SELECT r.roleName FROM Role r where r.roleName='"+ roleName + "' ";
			if(StringManagerUtils.isNotNull(roleId)){//当前是更新用户
				queryString+=" and r.roleId<>"+roleId;
			}
			queryString+= "order by r.roleId ";
			List<User> list = getBaseDao().find(queryString);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public List<T> queryRightTabs(Class<T> clazz, User user) throws Exception {
		
		String queryString = "SELECT tab FROM DeviceTypeInfo tab where tab.id in " 
				+ "( select distinct rt.rdDeviceTypeId from User u ,Role role,RoleDeviceType rt "
				+ "where  role.roleId =rt.rdRoleId   " 
				+ " and role.roleId = u.userType   and u.userNo="
				+ user.getUserNo() + ") order by tab.sortNum, tab.id";
		return find(queryString);
	}
	
	public List<T> queryRightTabsWithoutRoot(Class<T> clazz, User user) throws Exception {
		String queryString = "SELECT tab FROM DeviceTypeInfo tab where tab.parentId <>0 and tab.id in " 
				+ "( select distinct rt.rdDeviceTypeId from User u ,Role role,RoleDeviceType rt "
				+ "where  role.roleId =rt.rdRoleId   " 
				+ " and role.roleId = u.userType   and u.userNo="
				+ user.getUserNo() + ") order by tab.sortNum, tab.id";
		return find(queryString);
	}
	
	public String getRoleModuleRight(User user,String moduleCode){
		StringBuffer result_json = new StringBuffer();
		int viewFlag=0,editFlag=0,controlFlag=0; 
		
		String matrix=getUserRoleModuleMatrix(user!=null?user.getUserNo():0,moduleCode);
		
		viewFlag=StringManagerUtils.getModuleRightFlagFromMatrix(matrix,0);
		editFlag=StringManagerUtils.getModuleRightFlagFromMatrix(matrix,1);
		controlFlag=StringManagerUtils.getModuleRightFlagFromMatrix(matrix,2);
		
		result_json.append("{\"moduleCode\":\""+moduleCode+"\",");
		result_json.append("\"viewFlag\":"+viewFlag+",");
		result_json.append("\"editFlag\":"+editFlag+",");
		result_json.append("\"controlFlag\":"+controlFlag+"}");
		return result_json.toString();
	}
	
	public String getRoleLanguageList(int userNo,String language){
		List<String> roleLanguageList=new ArrayList<>();
		String sql="select t.language "
				+ " from TBL_LANGUAGE2ROLE t,tbl_user u,tbl_role r "
				+ " where u.user_type=r.role_id and r.role_id=t.roleid "
				+ " and u.user_no= "+userNo
				+ " order by t.language";
		List<?> list = this.findCallSql(sql);
		for(int i=0;i<list.size();i++){
			roleLanguageList.add(list.get(i).toString());
		}
		
		StringBuffer json = new StringBuffer();
		Map<String,Code> languageCodeMap=MemoryDataManagerTask.getCodeMap("LANGUAGE",language);
		json.append("[");
		
		Iterator<Map.Entry<String,Code>> it = languageCodeMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Code> entry = it.next();
			Code c=entry.getValue();
			if(StringManagerUtils.existOrNot(roleLanguageList, c.getItemvalue()+"", false)){
				json.append("{");
				json.append("\"languageId\":\""+c.getItemvalue()+"\",");
				json.append("\"text\":\""+c.getItemname()+"\",");
				json.append("\"value\":\""+c.getItemvalue()+"\",");
				json.append("\"checked\":false,");
				json.append("\"leaf\":true");
				json.append("},");
			}
		}
		if (json.toString().endsWith(",")) {
			json.deleteCharAt(json.length() - 1);
		}
		
		json.append("]");
		
		return json.toString();
	}
}
