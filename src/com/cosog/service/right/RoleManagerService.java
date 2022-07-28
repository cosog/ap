package com.cosog.service.right;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.Role;
import com.cosog.model.User;
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
		String roleName = (String) map.get("roleName");
		StringBuffer result_json = new StringBuffer();
		String currentId="";
		String currentLevel="";
		String currentShowLevel="";
		String currentFlag="";
		String currentRoleLevel="select t3.role_id,t3.role_level,t3.showLevel,t3.role_flag from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo();
		String sql="select role_id as roleId,role_name as roleName,role_level as roleLevel,role_flag as roleFlag,decode(t.role_flag,1,'是','否') as roleFlagName,showLevel,remark"
				+ " from  tbl_role t"
				+ " where t.role_level>(select t3.role_level from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")"
						+ " or t.role_id=(select t3.role_id from tbl_user t2,tbl_role t3 where t2.user_type=t3.role_id and t2.user_no="+user.getUserNo()+")";
		if (StringManagerUtils.isNotNull(roleName)) {
			sql+=" and t.role_Name like '%" + roleName + "%' ";
		}
		sql+=" order by t.role_id ";
		String columns=service.showTableHeadersColumns("roleManage");
		List<?> list = this.findCallSql(sql);
		List<?> currentUserLevelList = this.findCallSql(currentRoleLevel);
		if(currentUserLevelList.size()>0){
			Object[] obj = (Object[]) currentUserLevelList.get(0);
			currentId=obj[0]+"";
			currentLevel=obj[1]+"";
			currentShowLevel=obj[2]+"";
			currentFlag=obj[3]+"";
		}
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()
		+",\"currentId\":"+currentId
		+",\"currentLevel\":"+currentLevel
		+",\"currentShowLevel\":"+currentShowLevel
		+",\"currentFlag\":"+currentFlag
		+",\"columns\":"+columns+",\"totalRoot\":[");
		
		for (Object o : list) {
			Object[] obj = (Object[]) o;
			result_json.append("{\"roleId\":"+obj[0]+",");
			result_json.append("\"roleName\":\""+obj[1]+"\",");
			result_json.append("\"roleLevel\":\""+obj[2]+"\",");
			result_json.append("\"roleFlag\":\""+obj[3]+"\",");
			result_json.append("\"roleFlagName\":"+(StringManagerUtils.stringToInteger(obj[3]+"")==1)+",");
			result_json.append("\"showLevel\":\""+obj[5]+"\",");
			result_json.append("\"remark\":\""+obj[6]+"\"},");
		}
		if (result_json.toString().endsWith(",")) {
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
		
		
//		try {
//			json=this.findPageBySqlEntity(sql,columns , pager );
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return json;
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
	
	public int updateRoleInfo(Role role,boolean isLoginedUserRole) throws Exception {
		int r=0;
		boolean flag = this.judgeRoleExistsOrNot(role.getRoleName(),role.getRoleId()+"");
		if(flag){
			r=2;
		}else{
			String sql = "update tbl_role t set ";
			if(!isLoginedUserRole){//当前登录用户不可修改账号、角色、使能状态
				sql+= " t.role_level="+role.getRoleLevel()+", "
						+ " t.role_flag="+role.getRoleFlag()+", "
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
}
