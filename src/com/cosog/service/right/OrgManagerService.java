package com.cosog.service.right;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import com.cosog.model.Org;
import com.cosog.model.User;
import com.cosog.service.base.BaseService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.RedisUtil;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

import redis.clients.jedis.Jedis;

/**
 * <p>
 * 描述：组织维护模块的Service
 * </p>
 * 
 * @author gao 2014-05-08
 * @param <T>
 */
@Service("orgManagerService")
public class OrgManagerService<T> extends BaseService<T> {
	public Long findMaxNum(int orgLevel) throws Exception {
		Long count = (long) 0.0;
		String queryString = " Org o where o.orgLevel =" + orgLevel;
		count = this.getBaseDao().getMaxCountValue(queryString);
		return count;
	}

	public List<Org> findByPrimary(Integer parentId) {
		String queryString = "SELECT u.orgLevel FROM Org u where u.orgParent=" + parentId + " order by u.orgId ";
		return this.getBaseDao().find(queryString);
	}

	public List<Org> findCurrentOrgCodeIsNotExist(String orgCode) {
		String queryString = "SELECT u.orgCode FROM Org u where u.orgCode='" + orgCode + "' order by u.orgId ";
		return this.getBaseDao().find(queryString);
	}

	public List<T> findChildOrg(Integer parentId) {
		String queryString = "SELECT u FROM Org u where u.orgParent=" + parentId + " order by u.orgId ";
		return this.getBaseDao().find(queryString);
	}

	public List<T> findOrgChildrenByparentId(Integer parentId) {
		String queryString = "SELECT u FROM Org u where u.orgParent=" + parentId + " order by u.orgId ";
		return this.getBaseDao().find(queryString);
	}

	public List<T> findCurrentOrgCodeByparentId(Integer parentId) {
		String queryString = "SELECT u FROM Org u where u.orgId=" + parentId + " order by u.orgId ";
		return this.getBaseDao().find(queryString);
	}

	@SuppressWarnings("unchecked")
	public List<T> loadOrgs(Class<T> clazz,String orgName,String orgId,String currentOrgId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT u FROM Org u where 1=1");
		if(StringManagerUtils.isNotNull(orgId)){
			sqlBuffer.append(" and u.orgId in ("+orgId+")");
		}
		if(StringManagerUtils.isNotNull(currentOrgId)){
			String queryString="select org_id from tbl_org t start with org_id="+currentOrgId+" connect by prior  org_id=org_parent";
			List<?> list=getBaseDao().findCallSql(queryString);
			if(list.size()>0){
				StringBuffer orgIdString = new StringBuffer();
				for(int i=0;i<list.size();i++){
					orgIdString.append(list.get(i)+",");
				}
				if (orgIdString.toString().endsWith(",")) {
					orgIdString.deleteCharAt(orgIdString.length() - 1);
				}
				sqlBuffer.append(" and u.orgId not in ("+orgIdString.toString()+")");
			}
		}
		sqlBuffer.append(" order by u.orgSeq,u.orgId ");
		return getBaseDao().find(sqlBuffer.toString());
	}

	public List<T> loadWellInfoOrgs(Class<T> clazz) {
		String queryString = "SELECT u.orgCode,u.orgName FROM Org u order by u.orgId ";
		return getBaseDao().find(queryString);
	}

	public List<T> loadOrgTreeOrgs(Class<T> clazz, Integer orgId, int userType) {
		String orgIds = this.getBaseDao().getUserOrgIds(orgId);
		String queryString = "";
		if (userType != 1) {
			queryString = "SELECT u FROM Org u  where u.orgId in(" + orgIds + ")order by u.orgId  ";
		} else {
			queryString = "SELECT u FROM Org u";
		}
		return getBaseDao().find(queryString);
	}

	/**
	 * 查询用户组织树orgCode信息
	 * 
	 * @param orgId
	 * @return String
	 */
	public String findOrgById(Integer orgId) {
		String result = "";
		String queryString = "SELECT u.orgCode FROM Org u  where u.orgId in (" + orgId + " ) order by u.orgId  ";
		if(orgId==0){
			queryString = "SELECT u.orgCode FROM Org u order by u.orgId  ";
			return result;
		}
		List<?> getOrgList;
		try {
			getOrgList = this.find(queryString);

			if (null != getOrgList && getOrgList.size() > 0) {
				result = (String) getOrgList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询用户所属组织List集合
	 * 
	 * @param clazz
	 * @param orgTreeId
	 *            当前用户的组织编码
	 * @return
	 */
	public List<T> findloadOrgTreeListById(Class<T> clazz, String orgTreeId) {
		String queryString = "SELECT u FROM Org u  where u.orgId in  (" + orgTreeId + ")  order by u.orgCode  ";
		return getBaseDao().find(queryString);
	}
	
	public List<T> loadOrgAndChildTreeListById(Class<T> clazz, int orgId) {
		String queryString = "select u from Org u  ";
		if(orgId!=0){
			String orgIds=findChildIds(orgId);
			queryString+=" where u.orgId in  ("+orgIds+")";
		}
		queryString+= "  order by u.orgSeq,u.orgId  ";
		return getBaseDao().find(queryString);
	}
	
	//递归查询一个节点的父节点（oracle sql实现）
	public String findParentIds(int orgid){
		String parentIds="0";
		StringBuffer orgIdString = new StringBuffer();
		List<?> list;
		//递归查询子节点所有父节点sql语句
		String queryString="select org_parent from tbl_org t start with org_id="+orgid+"  connect by prior  org_parent=org_id";
		if(orgid==0){
			queryString="select org_parent from tbl_org t ";
		}
		list=getBaseDao().findCallSql(queryString);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				orgIdString.append(list.get(i)+",");
			}
			orgIdString.deleteCharAt(orgIdString.length() - 1);
			parentIds=orgIdString.toString();
		}
		return parentIds;
	}
	
	//递归查询一个节点及其子节点
	public String findChildIds(int orgid){
		String childIds="0";
		StringBuffer orgIdString = new StringBuffer();
		List<?> list;
		String queryString="select org_id from tbl_org t start with org_id="+orgid+" connect by prior  org_id=org_parent";
		if(orgid==0){
			queryString="select org_id from tbl_org t ";
		}
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
		
		public String findChildNames(int orgid,String language){
			String childNames="";
			StringBuffer orgNameString = new StringBuffer();
			List<?> list;
			//递归查询子节点所有父节点sql语句
			String queryString="select org_name_"+language+" from tbl_org t start with org_id="+orgid+" connect by prior  org_id=org_parent";
			if(orgid==0){
				queryString="select org_name_"+language+" from tbl_org t ";
			}
			list=getBaseDao().findCallSql(queryString);
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					orgNameString.append("'"+list.get(i)+"',");
				}
				orgNameString.deleteCharAt(orgNameString.length() - 1);
				childNames=orgNameString.toString();
			}
			return childNames;
		}
		
	//查找组织结构中所有有子节点的节点
	public String fingAllOrgParentNodeIds(){
		String result="";
		StringBuffer orgIdString = new StringBuffer();
		List<?> list;
		String queryString="select org_id from tbl_org where org_id in(select distinct org_parent from tbl_org)";
		list=getBaseDao().findCallSql(queryString);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				orgIdString.append(list.get(i)+",");
			}
			orgIdString.deleteCharAt(orgIdString.length() - 1);
			result=orgIdString.toString();
		}
		return result;
	}

	public List<T> loadParentOrgs(Class<T> clazz,String language) {
		String queryString = "SELECT orgId,orgName_"+language+" FROM Org u order by u.orgId ";
		return getBaseDao().find(queryString);
	}

	public List<?> queryOrgs(Class<T> clazz, String orgName,String orgId,String language) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT org_id,org_parent,org_name_"+language+" as showName,org_memo,org_seq,"
				+ " org_name_zh_CN,org_name_en,org_name_ru"
				+ " FROM tbl_org t "
				+ " WHERE 1=1");
		if(StringManagerUtils.isNotNull(orgId)){
			sqlBuffer.append(" and t.org_id in ("+orgId+")");
		}
		if(StringManagerUtils.isNotNull(orgName)){
			sqlBuffer.append(" and t.org_Name_"+language+" like '%" + orgName + "%' ");
		}
		sqlBuffer.append(" order by t.org_seq,t.org_id");
		return this.findCallSql(sqlBuffer.toString());
	}
	
	public String exportOrganizationCompleteData(User user) {
		StringBuffer result_json = new StringBuffer();
		String sql="select t.org_id,t.org_code,t.org_name_zh_cn,t.org_name_en,t.org_name_ru,t.org_memo,t.org_parent,t.org_seq "
				+ " from tbl_org t"
				+ " where t.org_id in ("+(user!=null?user.getUserorgids():"0")+")"
				+ " order by t.org_id ";
		List<?> list=this.findCallSql(sql);
		result_json.append("[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			result_json.append("{\"OrgId\":"+obj[0]+",");
			result_json.append("\"OrgCode\":\""+obj[1]+"\",");
			result_json.append("\"OrgName_zh_CN\":\""+obj[2]+"\",");
			result_json.append("\"OrgName_en\":\""+obj[3]+"\",");
			result_json.append("\"OrgName_ru\":\""+obj[4]+"\",");
			result_json.append("\"OrgMemo\":\""+obj[5]+"\",");
			result_json.append("\"OrgParentId\":"+obj[6]+",");
			result_json.append("\"OrgSeq\":\""+obj[7]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}
	
	public List<T> findloadOrgTreeListById2(Class<T> clazz, String tid,int orgid,String parentNodeIds,String childNodeIds) {
		String selectsql="SELECT u FROM Org u  where u.orgParent = "+ tid + "and u.orgId in("+orgid;
		if (StringManagerUtils.isNotNull(parentNodeIds)) {
			selectsql+=","+parentNodeIds;
		}
		if (StringManagerUtils.isNotNull(childNodeIds)) {
			selectsql+=","+childNodeIds;
		}
		selectsql+=")  order by u.orgId  ";
		return getBaseDao().find(selectsql);
	}

	@SuppressWarnings("rawtypes")
	public String getOrgList(Map map) {
		// String entity = (String) map.get("entity");
		String hql = "SELECT u FROM Org u order by u.orgId asc";
		Gson g = new Gson();
		List<Org> Orgs = null;
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String json = "";
		int total = this.getBaseDao().getRecordCountRows(hql);
		try {
			Orgs = this.getBaseDao().getListForPage((Integer) map.get("offset"), (Integer) map.get(PagingConstants.PAGE_SIZE), hql);
			jsonMap.put(PagingConstants.TOTAL, total);
			jsonMap.put(PagingConstants.LIST, Orgs);
			json = g.toJson(jsonMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * <p>
	 * 描述：新增组织信息
	 * </p>
	 * 
	 * @author gao 2014-05-08
	 * @param Org
	 *            传入当前要修改的组织对象
	 * @throws Exception
	 */
	public void addOrg(T Org) throws Exception {
		getBaseDao().addObject(Org);
	}

	/**
	 * <p>
	 * 描述：修改当前组织信息
	 * </p>
	 * 
	 * @author gao 2014-05-08
	 * @param Org
	 *            传入当前要修改的组织对象
	 * @throws Exception
	 */
	public void modifyOrg(T Org) throws Exception {
		getBaseDao().updateObject(Org);
	}

	public void deleteOrg(int id, Class<T> clazz) throws Exception {
		T u = getBaseDao().getObject(clazz, id);
		getBaseDao().deleteObject(u);
	}

	/**
	 * <p>
	 * 描述：批量删除组织信息
	 * </p>
	 * 
	 * @author gao 2014-05-08
	 * @param ids
	 *            传入要删除的组织id集合 格式为：1,2
	 * @throws Exception
	 */

	public int  bulkDelete(final String ids) throws Exception {
		Log.debug("bulkDelete" + ids);
		String deleteUserSql="delete from tbl_user t where t.user_orgid in(select t2.org_id from tbl_org t2 start with t2.org_id = "+ids+" connect by t2.org_parent = prior t2.org_id)";
		String deleteDeviceSql="delete from tbl_device t where t.orgid in(select t2.org_id from tbl_org t2 start with t2.org_id = "+ids+" connect by t2.org_parent = prior t2.org_id)";
		String deleteOrgSql="delete from tbl_org t where t.org_id in(select t2.org_id from tbl_org t2 start with t2.org_id = "+ids+" connect by t2.org_parent = prior t2.org_id)";
		getBaseDao().updateOrDeleteBySql(deleteUserSql);
		getBaseDao().updateOrDeleteBySql(deleteDeviceSql);
		return getBaseDao().updateOrDeleteBySql(deleteOrgSql);
		
//		final String hql = "DELETE Org u where u.orgId in (" + ids + ") or u.orgParent in(" + ids + ")";
//		final String delUserHql = "DELETE User u where u.userOrgid in (" + ids + ")";
//		getBaseDao().bulkObjectDelete(hql);
//		this.getBaseDao().bulkObjectDelete(delUserHql);
	}

	public T getOrg(Class<T> clazz, int id) {
		return getBaseDao().getObject(clazz, id);
	}
	
	public int changeOrgParent(String selectedCurrentOrgId,String selectedDestinationOrgId) throws Exception {
		int result=-99;
		if(StringManagerUtils.stringToInteger(selectedCurrentOrgId)>0 && StringManagerUtils.isNotNull(selectedDestinationOrgId)){
			String queryString="select org_id from tbl_org t start with org_id="+selectedCurrentOrgId+" connect by prior  org_id=org_parent";
			List<?> list=getBaseDao().findCallSql(queryString);
			StringBuffer orgChildNodesString = new StringBuffer();
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					orgChildNodesString.append(list.get(i)+",");
				}
				if (orgChildNodesString.toString().endsWith(",")) {
					orgChildNodesString.deleteCharAt(orgChildNodesString.length() - 1);
				}
			}
			if(orgChildNodesString.length()>0){
				String[] orgChildNodesArr=orgChildNodesString.toString().split(",");
				for(String id:orgChildNodesArr){
					if(StringManagerUtils.stringToInteger(selectedDestinationOrgId)==StringManagerUtils.stringToInteger(id)){
						result=-1;
						break;
					}
				}
			}
			if(result!=-1){
				String sql = "update tbl_org t set t.org_parent="+selectedDestinationOrgId+" where t.org_id="+selectedCurrentOrgId;
				result=this.getBaseDao().updateOrDeleteBySql(sql);
				MemoryDataManagerTask.loadUserInfoByOrgId(selectedDestinationOrgId,"update");
			}
		}
		return result;
	}
}
