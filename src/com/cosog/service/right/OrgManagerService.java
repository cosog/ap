package com.cosog.service.right;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import com.cosog.model.Org;
import com.cosog.service.base.BaseService;
import com.cosog.utils.PagingConstants;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;

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

	/**
	 * <p>
	 * 描述：加载组织类型的下拉菜单数据信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loadOrgType(String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		sql = " select t.itemvalue,t.itemname from tbl_code t where  itemcode='ORG_TYPE'";
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
		
		public String findChildNames(int orgid){
			String childNames="";
			StringBuffer orgNameString = new StringBuffer();
			List<?> list;
			//递归查询子节点所有父节点sql语句
			String queryString="select org_name from tbl_org t start with org_id="+orgid+" connect by prior  org_id=org_parent";
			if(orgid==0){
				queryString="select org_name from tbl_org t ";
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

	public List<T> loadParentOrgs(Class<T> clazz) {
		String queryString = "SELECT orgId,orgName FROM Org u order by u.orgId ";
		return getBaseDao().find(queryString);
	}

	public List<?> queryOrgs(Class<T> clazz, String orgName,String orgId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT org_id,org_parent,org_name,org_memo,org_seq "
				+ " FROM tbl_org t "
				+ " WHERE 1=1");
//				+ " and t.org_id not in( select o2.org_id from tbl_org o2 where o2.org_name='组织根节点' and o2.org_parent=0 )");
		if(StringManagerUtils.isNotNull(orgId)){
			sqlBuffer.append(" and t.org_id in ("+orgId+")");
		}
		if(StringManagerUtils.isNotNull(orgName)){
			sqlBuffer.append(" and t.org_Name like '%" + orgName + "%' ");
		}
		sqlBuffer.append(" order by t.org_seq,t.org_id");
		return this.findCallSql(sqlBuffer.toString());
	}
	
	public List<?> queryOrgsSyn(Class<T> clazz, String orgName,String tid,int orgid,String parentNodeIds,String childNodeIds,String orgIds,String treeSelectedOrgId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("SELECT org_id,org_code,org_name,org_memo,org_parent,org_level,org_type,c.itemname as orgTypeName ,org_coordx,org_coordy,show_level as showLevel "
				+ "FROM tbl_org u,tbl_code c "
				+ "WHERE c.itemcode='ORG_TYPE' and c.itemvalue=u.org_type  and u.org_id in("+orgIds+")");
		
		if(!"0".equals(tid)){
			sqlBuffer.append(" and u.org_parent = "+ tid);
		}else{
			sqlBuffer.append(" and u.org_parent = ( select t.org_parent from tbl_org t where t.org_id="+treeSelectedOrgId+" ) ");
		}
//		if (StringManagerUtils.isNotNull(parentNodeIds)) {
//			sqlBuffer.append(","+parentNodeIds);
//		}
//		if (StringManagerUtils.isNotNull(childNodeIds)) {
//			sqlBuffer.append(","+childNodeIds);
//		}
//		sqlBuffer.append(") ");
		if (!orgName.isEmpty() && orgName != null &&! "".equals(orgName)) {
			sqlBuffer.append(" and u.org_Name like '%" + orgName + "%' ");
		}
		sqlBuffer.append(" order by u.org_Id  asc");
		return this.findCallSql(sqlBuffer.toString());
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
		String deleteRPCDeviceSql="delete from tbl_rpcdevice t where t.orgid in(select t2.org_id from tbl_org t2 start with t2.org_id = "+ids+" connect by t2.org_parent = prior t2.org_id)";
		String deletePCPDeviceSql="delete from tbl_pcpdevice t where t.orgid in(select t2.org_id from tbl_org t2 start with t2.org_id = "+ids+" connect by t2.org_parent = prior t2.org_id)";
		String deleteOrgSql="delete from tbl_org t where t.org_id in(select t2.org_id from tbl_org t2 start with t2.org_id = "+ids+" connect by t2.org_parent = prior t2.org_id)";
		getBaseDao().updateOrDeleteBySql(deleteUserSql);
		getBaseDao().updateOrDeleteBySql(deleteRPCDeviceSql);
		getBaseDao().updateOrDeleteBySql(deletePCPDeviceSql);
		return getBaseDao().updateOrDeleteBySql(deleteOrgSql);
		
//		final String hql = "DELETE Org u where u.orgId in (" + ids + ") or u.orgParent in(" + ids + ")";
//		final String delUserHql = "DELETE User u where u.userOrgid in (" + ids + ")";
//		getBaseDao().bulkObjectDelete(hql);
//		this.getBaseDao().bulkObjectDelete(delUserHql);
	}

	public T getOrg(Class<T> clazz, int id) {
		return getBaseDao().getObject(clazz, id);
	}

}
