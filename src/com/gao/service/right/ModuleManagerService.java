package com.gao.service.right;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gao.model.Module;
import com.gao.model.User;
import com.gao.service.base.BaseService;
import com.gao.utils.LicenseMap;
import com.gao.utils.PagingConstants;
import com.gao.utils.StringManagerUtils;
import com.google.gson.Gson;

/**
 * <p>描述：模块维护及树形菜单的创建服务</p>
 * 
 * @author gao 2014-05-09
 * @param <T>
 */
@Service("moduleManagerService")
public class ModuleManagerService<T> extends BaseService<T> {

	public void addModule(T Module) throws Exception {
		getBaseDao().addObject(Module);
	}

	public void modifyModule(T Module) throws Exception {
		getBaseDao().updateObject(Module);
	}

	public void deleteModule(int id, Class<T> clazz) throws Exception {
		T u = getBaseDao().getObject(clazz, id);
		getBaseDao().deleteObject(u);
	}

	public void bulkDelete(final String ids) throws Exception {
		final String hql = "DELETE Module u where u.mdId in (" + ids + ")";
		getBaseDao().bulkObjectDelete(hql);
	}

	public T getModule(Class<T> clazz, int id) {
		return getBaseDao().getObject(clazz, id);
	}

	public List<T> queryModule(int parentId, Class<T> clazz) {
		String queryString = "SELECT u FROM Module u WHERE u.parent_id=" + parentId;
		return getBaseDao().find(queryString);
	}

	/**
	 * <p>描述：获取后台模块树的服务方法</p>
	 * 
	 * @author  gao 2014-05-09
	 * @param clazz
	 * @param user 当前用户对象
	 * @return List<T>
	 * @throws Exception 
	 */
	public List<T> queryModuleList(Class<T> clazz, User user) throws Exception {
		String queryString = "";
//		if (user.getUserType() == 1) {
//			queryString = "SELECT  m FROM Module m where 1=1  and m.mdId in " + "( select distinct m.mdId from  Module m  " + " where  m.mdType=1 ) order by m.mdSeq, m.mdId";
//		} else if (user.getUserType() == 2||user.getUserType() == 3) {
//			queryString = "SELECT  m FROM Module m where 1=1  and m.mdId in " + "( select distinct m.mdId from User u ,Role role, Right rt,RoleModule rm "
//					+ "where   u.userNo = rt.rtUserNo and rt.rtRolecode = role.roleCode and role.roleCode =rm.rmRoleCode   " + " and rm.rmModuleid = m.mdId  and m.mdType=1 and u.userNo="
//					+ user.getUserNo() + ") order by m.mdSeq, m.mdId";
//		}
		queryString = "SELECT  m FROM Module m where 1=1 and m.mdType=1  and m.mdId in " 
				+ "( select distinct rm.rmModuleid from User u ,Role role,RoleModule rm "
				+ "where  role.roleId =rm.rmRoleId   " 
				+ " and role.roleId = u.userType   and u.userNo="
				+ user.getUserNo() + ") order by m.mdSeq, m.mdId";
		return this.find(queryString);
	}



	/**
	 * <p>描述：获取前台模块树的服务方法</p>
	 * 
	 * @author  gao 2014-05-09
	 * @param clazz
	 * @param user 当前用户对象
	 * @return
	 * @throws Exception 
	 */
	public List<T> queryFunctionModuleList(Class<T> clazz, User user) throws Exception {
//		if("system".equalsIgnoreCase(user.getUserId())){
//			
//		}
		List<Integer> moduleList=LicenseMap.getModuleMapObject().get(LicenseMap.modulesSN);
		String queryString = "";
		queryString = "SELECT  m FROM Module m where 1=1 and m.mdType=0  and m.mdId in " 
					+ " ( select distinct rm.rmModuleid from User u ,Role role,RoleModule rm "
					+ " where  role.roleId =rm.rmRoleId " 
					+ " and role.roleId = u.userType "
					+ " and u.userNo="+ user.getUserNo() + "";
		if(moduleList!=null){
			queryString+=" and rm.rmModuleid in("+StringUtils.join(moduleList, ",")+") ";
		}
		queryString+= ") order by m.mdSeq, m.mdId";
		return this.find(queryString);
	}
	
	public List<T> queryFunctionModuleList2(Class<T> clazz, User user,String parentId) throws Exception {
		String queryString = "";
		if (user.getUserType() == 1) {
			queryString = " select  m from Module m where  m.mdType=0 and m.mdParentid="+parentId+"  order by m.mdSeq, m.mdId";
		} else if (user.getUserType() == 0 || user.getUserType() == 2|| user.getUserType() == 3) {
			queryString="SELECT  m FROM Module m,User u ,Role role, Right rt,RoleModule rm where   u.userNo = rt.rtUserNo and rt.rtRolecode = role.roleCode and role.roleCode =rm.rmRoleCode"
					+" and rm.rmModuleid = m.mdId  and m.mdType=0 and m.mdParentid="+parentId+" and u.userNo="+ user.getUserNo() 
					+ " order by m.mdSeq, m.mdId";
		}
		return this.find(queryString);
	}
	
	//查询模块表中所有父节点
	public String fingAllModParentNodeIds(){
		String result="";
		StringBuffer modIdString = new StringBuffer();
		List<?> list;
		String queryString="select md_id from tbl_module where md_id in (select distinct md_parentid from tbl_module)";
		list=getBaseDao().findCallSql(queryString);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				modIdString.append(list.get(i)+",");
			}
			modIdString.deleteCharAt(modIdString.length() - 1);
			result=modIdString.toString();
		}
		return result;
	}	

	/**
	 * <p>描述：获取所有的模块信息服务方法</p>
	 * 
	 * @author  gao 2014-05-09
	 * @param clazz
	 * @param user 
	 * @return
	 */
	public List<T> queryAddModuleList(Class<T> clazz, User user) {
		String queryString = "";
		queryString = "SELECT  m FROM Module m";
		return getBaseDao().find(queryString);
	}

	public List<T> queryMainModuleList(Class<T> clazz, int userNo) {
		String queryString = "SELECT  m FROM Module m where 1=1  and m.mdId in " + "( select distinct m.mdId from User u ,Role role, Right rt,RoleModule rm "
				+ "where   u.userNo = rt.rtUserNo and rt.rtRolecode = role.roleCode and role.roleCode =rm.rmRoleCode   " + " and rm.rmModuleid = m.mdId  and m.mdType=0 and u.userNo=" + userNo
				+ " )   " + "order by m.mdSeq, m.mdId";
		return getBaseDao().find(queryString);
	}

	public Integer getTotalRows(int parentId) {
		String hql = "SELECT u FROM Module u WHERE u.parent_id=" + parentId;
		return this.getBaseDao().getRecordCountRows(hql);
	}

	public List<T> queryModule(int parentId, int offset, int pageSize, Class<T> clazz) {
		String queryString = "SELECT u FROM Module u WHERE u.parent_id=" + parentId;
		List<T> list = null;
		try {
			list = this.getBaseDao().getListForPage(offset, pageSize, queryString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<T> loadModules(Class<T> clazz) {
		String queryString = "SELECT u FROM Module u order by u.mdSeq ";
		return getBaseDao().find(queryString);
	}

	
	public List<T> loadRightModules(Class<T> clazz) {
		String queryString = "SELECT u FROM Module u where u.mdType in (0,1) order by u.mdSeq ";
		return getBaseDao().find(queryString);
	}

	public List<T> loadRoleModules(Class<T> clazz) {
		String queryString = "SELECT distinct rm.rmModuleid FROM Role r ,RoleModule rm where  rm.rmRoleId=r.roleId  order by rm.rmModuleid asc";
		return getBaseDao().find(queryString);
	}

	public List<T> queryCurrentRoleModules(Class<T> clazz, String roleId) {
		String queryString = "select   rm  From  Role r ,RoleModule rm where  rm.rmRoleId=r.roleId ";
		if(StringManagerUtils.isNotNull(roleId)){
			queryString+=" and rm.rmRoleId="+roleId;
		}
		queryString+=" order by rm.rmModuleid asc";
		return getBaseDao().find(queryString);
	}

	public List<T> queryCurrentRoleMatrixModules(Class<T> clazz, String roleId) {
		String queryString = "select    rm.rmMatrix  From " + "Role r ,RoleModule rm where  rm.rmRoleId=r.roleId and rm.rmRoleId=" + roleId + " order by rm.rmMatrix asc";
		return getBaseDao().find(queryString);
	}

	public void deleteCurrentModule(final Integer mdId) throws Exception {
		final String hql = "DELETE RoleModule u where u.rmModuleid = " + mdId;
		getBaseDao().bulkObjectDelete(hql);
	}

	public void deleteCurrentModuleByRoleCode(final String roleId) throws Exception {
		final String hql = "DELETE RoleModule u where u.rmRoleId = " + roleId + "";
		getBaseDao().bulkObjectDelete(hql);
	}

	public void saveOrUpdateModule(T roleModule) throws Exception {
		getBaseDao().saveOrUpdateObject(roleModule);
	}

	public List<?> queryModules(Class<T> clazz, String moduleName,User user) {
		StringBuffer sqlBuffer = new StringBuffer();
		String roleCodeSql="select role_code from tbl_role where role_id="+user.getUserType();
		List<?> list = this.findCallSql(roleCodeSql);
		String roleCode="";
		if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
			roleCode = list.get(0).toString();
		}
		sqlBuffer.append("select md_id,md_name,md_parentid,md_showname,md_url,md_code,md_seq ,md_icon,md_type,md_control,c.itemname as mdTypeName   ");
		sqlBuffer.append("from  tbl_module t,tbl_code c where c.itemcode='MD_TYPE' and c.itemvalue=t.md_type ");
		if (!"systemRole".equals(roleCode)){
			sqlBuffer.append("and  t.md_id in ( select distinct rm.rm_moduleid from tbl_user u ,tbl_role role,tbl_module2role rm where  role.role_Id =rm.rm_RoleId and role.role_Id = u.user_Type   and u.user_No="+user.getUserNo() + ")");
		}
		if(!moduleName.isEmpty()&&moduleName!=null&&!"".equals(moduleName)){
			sqlBuffer.append("and t.md_name like '%"+moduleName+"%' ");
		}
		sqlBuffer.append( " order by t.md_seq, t.md_id");
		return this.findCallSql(sqlBuffer.toString());
	}
	
	/**
	 * <p>描述：权限分配时，给角色分配权限时，创建的模块 treepanel 所需要的数据集合</p>
	 * 
	 * @author  gao 2014-05-09
	 * @param clazz
	 * @return
	 * @throws Exception 
	 * 
	 */
	public List<T> queryRightModules(Class<T> clazz, String moduleName,User user) throws Exception {
		String roleCodeSql="select role_code from tbl_role where role_id="+user.getUserType();
		List<?> list = this.findCallSql(roleCodeSql);
		String roleCode="";
		if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
			roleCode = list.get(0).toString();
		}
		if ("systemRole".equals(roleCode))
			return loadRightModules(clazz);
		String queryString = "SELECT  m FROM Module m where 1=1 and m.mdType in(0,1)  and m.mdId in " 
				+ "( select distinct rm.rmModuleid from User u ,Role role,RoleModule rm "
				+ "where  role.roleId =rm.rmRoleId   " 
				+ " and role.roleId = u.userType   and u.userNo="
				+ user.getUserNo() + ") order by m.mdSeq, m.mdId";
		return find(queryString);
	}

	@SuppressWarnings("rawtypes")
	public String getModuleList(Map map) {
		String hql = "SELECT u FROM Module u order by u.mdId asc";
		Gson g = new Gson();
		List<Module> Modules = null;
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String json = "";
		int total = this.getBaseDao().getRecordCountRows(hql);
		try {
			Modules = this.getBaseDao().getListForPage((Integer) map.get("offset"), (Integer) map.get(PagingConstants.PAGE_SIZE), hql);
			jsonMap.put(PagingConstants.TOTAL, total);
			jsonMap.put(PagingConstants.LIST, Modules);
			json = g.toJson(jsonMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public Long findMaxNum(int moduleType) throws Exception {
		Long count = (long) 0.0;
		String queryString = " Module o where o.mdType =" + moduleType;
		count = this.getBaseDao().getMaxCountValue(queryString);
		return count;
	}
	public String loadModuleType(String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		sql = " select t.itemvalue,t.itemname from tbl_code t where  itemcode='MD_TYPE'";
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
}
