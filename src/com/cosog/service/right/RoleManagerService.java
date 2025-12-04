package com.cosog.service.right;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.Code;
import com.cosog.model.ExportRoleData;
import com.cosog.model.ExportUserData;
import com.cosog.model.Role;
import com.cosog.model.RoleDeviceType;
import com.cosog.model.RoleLanguage;
import com.cosog.model.RoleModule;
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
	@Autowired
	private ModuleManagerService<RoleModule> moduleService;
	@Autowired
	private ModuleManagerService<RoleDeviceType> roleTabService;
	@Autowired
	private ModuleManagerService<RoleLanguage> roleLanguageService;

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
				+ " and t.role_id not in ( select distinct(t5.rd_roleid) from TBL_DEVICETYPE2ROLE t5 where t5.rd_devicetypeid not in("+currentTabs+") )"
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
	
	
	public String exportRoleCompleteData(User user) {
		StringBuffer result_json = new StringBuffer();
		result_json.append("{\"Code\":\"Role\",");
		String sql="select role_id as roleId,role_name as roleName,role_level as roleLevel,"
				+ " role_videokeyedit as roleVideoKeyEdit,"
				+ " role_languageedit as roleLanguageEdit,"
				+ " showLevel,remark"
				+ " from  viw_role t"
				+ " where 1=1 "
				+ " and t.role_id not in( select distinct(t5.rd_roleid) from TBL_DEVICETYPE2ROLE t5 where t5.rd_devicetypeid not in("+user.getDeviceTypeIds()+") )"
				+ " and ( t.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
						+ " or t.role_id=(select t3.role_id from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+") )"
				+ " order by t.role_id ";
		
		String moduleSql="select t.rm_roleid,t.rm_moduleid,t.rm_matrix "
				+ " from tbl_module2role t "
				+ " order by t.rm_roleid,t.rm_id";
		String deviceTypeSql="select t.rd_roleid,t.rd_devicetypeid,t.rd_matrix "
				+ " from tbl_devicetype2role t "
				+ " order by t.rd_roleid,t.rd_devicetypeid";
		String languageSql="select t.roleid,t.language,t.matrix "
				+ " from tbl_language2role t "
				+ " order by t.roleid,t.language";
		
		List<?> list=this.findCallSql(sql);
		List<?> moduleRightList=this.findCallSql(moduleSql);
		List<?> deviceTypeRightList=this.findCallSql(deviceTypeSql);
		List<?> languageRightList=this.findCallSql(languageSql);
		result_json.append("\"List\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String roleId=obj[0]+"";
			result_json.append("{\"RoleId\":"+roleId+",");
			result_json.append("\"RoleName\":\""+obj[1]+"\",");
			result_json.append("\"RoleLevel\":"+obj[2]+",");
			result_json.append("\"RoleVideoKeyEdit\":"+obj[3]+",");
			result_json.append("\"RoleLanguageEdit\":"+obj[4]+",");
			result_json.append("\"ShowLevel\":"+obj[5]+",");
			result_json.append("\"Remark\":\""+obj[6]+"\",");
			result_json.append("\"ModuleRight\":[");
			for(int j=0;j<moduleRightList.size();j++){
				Object[] moduleRightObj = (Object[]) moduleRightList.get(j);
				if(roleId.equalsIgnoreCase(moduleRightObj[0]+"")){
					result_json.append("{\"Id\":"+moduleRightObj[1]+",");
					result_json.append("\"Matrix\":\""+moduleRightObj[2]+"\"},");
				}
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("],");
			
			result_json.append("\"DeviceTypeRight\":[");
			for(int j=0;j<deviceTypeRightList.size();j++){
				Object[] deviceTypeRightObj = (Object[]) deviceTypeRightList.get(j);
				if(roleId.equalsIgnoreCase(deviceTypeRightObj[0]+"")){
					result_json.append("{\"Id\":"+deviceTypeRightObj[1]+",");
					result_json.append("\"Matrix\":\""+deviceTypeRightObj[2]+"\"},");
				}
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("],");
			
			result_json.append("\"LanguageRight\":[");
			for(int j=0;j<languageRightList.size();j++){
				Object[] languageRightObj = (Object[]) languageRightList.get(j);
				if(roleId.equalsIgnoreCase(languageRightObj[0]+"")){
					result_json.append("{\"Id\":"+languageRightObj[1]+",");
					result_json.append("\"Matrix\":\""+languageRightObj[2]+"\"},");
				}
			}
			if(result_json.toString().endsWith(",")){
				result_json.deleteCharAt(result_json.length() - 1);
			}
			result_json.append("]");
			result_json.append("},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		result_json.append("}");
		return result_json.toString().replaceAll("null", "");
	}

	public void addRole(T role) throws Exception {
		getBaseDao().addObject(role);
	}
	
	public void addRoleInfo(Role role) throws Exception {
		getBaseDao().addObjectFlush(role);
	}

	public void modifyRole(T role) throws Exception {
		getBaseDao().updateObject(role);
	}
	
	public void modifyRoleInfo(Role role) throws Exception {
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
	
	public String getRoleAssociatedInformation(String roleId){
		int userCount=0;
		
		String sql="select count(1) from tbl_user t where t.user_type="+roleId;
		userCount=this.getTotalCountRows(sql);
		
		return "{\"roleId\":"+roleId+",\"userCount\":"+userCount+"}";
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
	
	public String getUploadedRoleTreeData(List<ExportRoleData> uploadRoleList,User user) {
		StringBuffer result_json = new StringBuffer();
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		String currentTabs=user.getDeviceTypeIds();
		String[] currentTabArr=currentTabs.split(",");
		String currentId="";
		String currentLevel="";
		String currentShowLevel="";
		String currentVideoKeyEdit="";
		String currentLanguageEdit="";
		String currentRoleLevel="select t3.role_id,t3.role_level,t3.showLevel,t3.role_videokeyedit,t3.role_languageedit "
				+ "from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo();
		List<?> currentUserLevelList = this.findCallSql(currentRoleLevel);
		if(currentUserLevelList.size()>0){
			Object[] obj = (Object[]) currentUserLevelList.get(0);
			currentId=obj[0]+"";
			currentLevel=obj[1]+"";
			currentShowLevel=obj[2]+"";
			currentVideoKeyEdit=obj[3]+"";
			currentLanguageEdit=obj[4]+"";
		}
		
		
		String overlaySql="select role_id"
				+ " from  viw_role t"
				+ " where 1=1 "
				+ " and t.role_id not in ( select distinct(t5.rd_roleid) from TBL_DEVICETYPE2ROLE t5 where t5.rd_devicetypeid not in("+currentTabs+") )"
				+ " and ( t.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
						+ " or t.role_id=(select t3.role_id from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+") )";
		
		String collisionSql="select role_id "
				+ " from viw_role "
				+ " where role_name not in("
				+ " select role_name"
				+ " from  viw_role t"
				+ " where 1=1 "
				+ " and t.role_id not in ( select distinct(t5.rd_roleid) from TBL_DEVICETYPE2ROLE t5 where t5.rd_devicetypeid not in("+currentTabs+") )"
				+ " and ( t.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
						+ " or t.role_id=(select t3.role_id from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+") )"
				+ ")";
		
		
		List<ExportRoleData> roleList=new ArrayList<>();
		if(uploadRoleList!=null){
			for(ExportRoleData exportRoleData:uploadRoleList){
				boolean deviceTypeRight=true;
				for(int i=0;i<exportRoleData.getDeviceTypeRight().size();i++){
					if(!StringManagerUtils.existOrNot(currentTabArr, exportRoleData.getDeviceTypeRight().get(i).getId()+"", false)){
						deviceTypeRight=false;
						break;
					}
				}
				
				if( deviceTypeRight && ( (exportRoleData.getRoleLevel()>StringManagerUtils.stringToInteger(currentRoleLevel)) || (exportRoleData.getRoleId()==StringManagerUtils.stringToInteger(currentId)) )  ){
					roleList.add(exportRoleData);
				}
			}
		}
		
		List<String> overlayRoleList=new ArrayList<>();
		List<String> collisionRoleList=new ArrayList<>();
		List<?> overlayList = this.findCallSql(overlaySql);
		List<?> collisionList = this.findCallSql(collisionSql);
		for(int i=0;i<overlayList.size();i++){
			overlayRoleList.add(overlayList.get(i).toString());
		}
		for(int i=0;i<collisionList.size();i++){
			collisionRoleList.add(collisionList.get(i).toString());
		}
		
		String columns="[]";
//		List<?> list = this.findCallSql(sql);
		
		result_json.append("{\"success\":true,\"totalCount\":"+roleList.size()+","
		+ "\"columns\":"+columns+","
		+ "\"totalRoot\":[");
		if(uploadRoleList!=null){
			for(ExportRoleData exportRoleData:roleList){
				
				if(StringManagerUtils.existOrNot(overlayRoleList, exportRoleData.getRoleId()+"", true)){
					exportRoleData.setSaveSign(1);
					exportRoleData.setMsg(exportRoleData.getRoleName()+languageResourceMap.get("uploadCollisionInfo1"));
				}else if(StringManagerUtils.existOrNot(collisionRoleList, exportRoleData.getRoleId()+"", true)){
					exportRoleData.setSaveSign(2);
					exportRoleData.setMsg(exportRoleData.getRoleName()+languageResourceMap.get("uploadCollisionInfo2"));
				}
				
				
				result_json.append("{\"roleId\":"+exportRoleData.getRoleId()+",");
				result_json.append("\"roleName\":\""+exportRoleData.getRoleName()+"\",");
				result_json.append("\"roleLevel\":\""+exportRoleData.getRoleLevel()+"\",");
				result_json.append("\"roleVideoKeyEdit\":\""+exportRoleData.getRoleVideoKeyEdit()+"\",");
				result_json.append("\"roleVideoKeyEditName\":"+(exportRoleData.getRoleVideoKeyEdit()==1)+",");
				result_json.append("\"roleLanguageEdit\":\""+exportRoleData.getRoleLanguageEdit()+"\",");
				result_json.append("\"roleLanguageEditName\":"+(exportRoleData.getRoleLanguageEdit()==1)+",");
				result_json.append("\"showLevel\":\""+exportRoleData.getShowLevel()+"\",");
				result_json.append("\"remark\":\""+exportRoleData.getRemark()+"\",");
				result_json.append("\"msg\":\""+exportRoleData.getMsg()+"\",");
				result_json.append("\"saveSign\":\""+exportRoleData.getSaveSign()+"\"},");
			}
		}
		
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}
	
	public int saveAllImportedRole(List<ExportRoleData> uploadRoleList,User user) {
		int result=0;
		Map<String,String> languageResourceMap=MemoryDataManagerTask.getLanguageResource(user.getLanguageName());
		String currentTabs=user.getDeviceTypeIds();
		String[] currentTabArr=currentTabs.split(",");
		String currentId="";
		String currentLevel="";
		String currentShowLevel="";
		String currentVideoKeyEdit="";
		String currentLanguageEdit="";
		String currentRoleLevel="select t3.role_id,t3.role_level,t3.showLevel,t3.role_videokeyedit,t3.role_languageedit "
				+ "from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo();
		List<?> currentUserLevelList = this.findCallSql(currentRoleLevel);
		if(currentUserLevelList.size()>0){
			Object[] obj = (Object[]) currentUserLevelList.get(0);
			currentId=obj[0]+"";
			currentLevel=obj[1]+"";
			currentShowLevel=obj[2]+"";
			currentVideoKeyEdit=obj[3]+"";
			currentLanguageEdit=obj[4]+"";
		}
		
		Map<String,Integer> roleMap=new HashMap<>();
		String roleSql="select t.role_name,t.role_id from TBL_ROLE t ";
		List<?> currentRoleList = this.findCallSql(roleSql);
		for(int i=0;i<currentRoleList.size();i++){
			Object[] obj = (Object[]) currentRoleList.get(i);
			roleMap.put(obj[0]+"", StringManagerUtils.stringToInteger(obj[1]+""));
		}
		
		this.getBaseDao().triggerDisabledOrEnabled("TBL_ROLE", false);
		
		for(ExportRoleData exportRoleData:uploadRoleList){
			boolean deviceTypeRight=true;
			for(int i=0;i<exportRoleData.getDeviceTypeRight().size();i++){
				if(!StringManagerUtils.existOrNot(currentTabArr, exportRoleData.getDeviceTypeRight().get(i).getId()+"", false)){
					deviceTypeRight=false;
					break;
				}
			}
			
			if( deviceTypeRight && ( (exportRoleData.getRoleLevel()>StringManagerUtils.stringToInteger(currentRoleLevel)) || (exportRoleData.getRoleId()==StringManagerUtils.stringToInteger(currentId)) )  ){
				if(exportRoleData.getSaveSign()!=2){
					Role role=new Role();
					role.setRoleName(exportRoleData.getRoleName());
					role.setRoleLevel(exportRoleData.getRoleLevel());
					role.setShowLevel(exportRoleData.getShowLevel());
					role.setRoleVideoKeyEdit(exportRoleData.getRoleVideoKeyEdit());
					role.setRoleLanguageEdit(exportRoleData.getRoleLanguageEdit());
					role.setRemark(exportRoleData.getRemark());
					role.setRoleId(exportRoleData.getRoleId());
					
					if(exportRoleData.getSaveSign()==0){
						try {
							this.addRoleInfo(role);
							exportRoleData.setMsg(languageResourceMap.get("addSuccessfully"));
						} catch (Exception e) {
							exportRoleData.setMsg(languageResourceMap.get("addFailure"));
							e.printStackTrace();
						}
					}else if(exportRoleData.getSaveSign()==1){
						try {
							this.modifyRoleInfo(role);
							
							this.moduleService.deleteCurrentModuleByRoleCode(role.getRoleId()+"");
							this.moduleService.deleteCurrentTabByRoleCode(role.getRoleId()+"");
							this.moduleService.deleteCurrentLanguageByRoleCode(role.getRoleId()+"");
							exportRoleData.setMsg(languageResourceMap.get("updateSuccessfully"));
							exportRoleData.setSaveSign(0);
						} catch (Exception e) {
							exportRoleData.setMsg(languageResourceMap.get("updateFailure"));
							e.printStackTrace();
						}
					}
					for (int i = 0; i < exportRoleData.getModuleRight().size(); i++) {
						RoleModule r = new RoleModule();
						r.setRmRoleId(role.getRoleId());
						r.setRmModuleid(exportRoleData.getModuleRight().get(i).getId());
						r.setRmMatrix(exportRoleData.getModuleRight().get(i).getMatrix());
						try {
							this.moduleService.saveOrUpdateModule(r);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					for (int i = 0; i < exportRoleData.getDeviceTypeRight().size(); i++) {
						RoleDeviceType r = new RoleDeviceType();
						r.setRdRoleId(role.getRoleId());
						r.setRdDeviceTypeId(exportRoleData.getDeviceTypeRight().get(i).getId());
						r.setRdMatrix(exportRoleData.getDeviceTypeRight().get(i).getMatrix());
						try {
							this.roleTabService.saveOrUpdateRoleDeviceType(r);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					for (int i = 0; i < exportRoleData.getLanguageRight().size(); i++) {
						RoleLanguage r = new RoleLanguage();
						r.setRoleId(role.getRoleId());
						r.setLanguage(exportRoleData.getLanguageRight().get(i).getId());
						r.setMatrix(exportRoleData.getLanguageRight().get(i).getMatrix());
						try {
							this.roleLanguageService.saveOrUpdateRoleLanguage(r);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					if(exportRoleData.getRoleName().equals(user.getUserTypeName())){
						MemoryDataManagerTask.loadUserInfoByRoleId(role.getRoleId()+"", "update");
					}
				}
			}
		}
		this.getBaseDao().triggerDisabledOrEnabled("TBL_ROLE", true);
		this.getBaseDao().resetSequence("TBL_ROLE", "role_id", "SEQ_ROLE");
		
		
		return result;
	}
}
