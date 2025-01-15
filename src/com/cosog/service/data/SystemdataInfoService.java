package com.cosog.service.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.User;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.data.DataitemsInfo;
import com.cosog.model.data.SystemdataInfo;
import com.cosog.service.base.BaseService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.DataModelMap;
import com.cosog.utils.DateUtils;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;
import com.cosog.utils.UUIDGenerator;
import com.google.gson.Gson;

/**
 * 系统数据字典表
 * 
 * @author 钱邓水
 * @data 2014-4-10
 */
@Service
public class SystemdataInfoService extends BaseService<SystemdataInfo> {

	// 数据项值service
	@Autowired
	private DataitemsInfoService dataitemsInfoService;

	/**
	 * 查询系统数据字典所有信息
	 * 
	 * @author 钱邓水 2012-3-8
	 * @throws Exception
	 * @parem typeName 类型 name 字典名称
	 */
	public List<SystemdataInfo> findSystemdataInfoPageListById(Page pager, User userInfo, String typeName, String name) throws Exception {

		pager.setWhere("status=0  and  status  is not null ");
		// 检索类型
		if (StringUtils.isNotBlank(typeName)) {
			// 中文类型检索
			if (typeName.equals("0") && StringUtils.isNotBlank(name)) {
				pager.setWhere("name_"+userInfo.getLanguageName()+" like '%" + name + "%'");
			}
			// 英文类型检索
			if (typeName.equals("1") && StringUtils.isNotBlank(name)) {
				pager.setWhere("code like '%" + name + "%'");
			}
		}

		pager.setSort(" sorts asc");

		List<SystemdataInfo> list = findAllPageByEntity(pager);
		return list;
	}
	
	public String findSystemdataInfo(User user,String typeName,String findName,Page pager){
		StringBuffer result_json = new StringBuffer();
		String ddicCode="dictionary_DataDictionaryManage";
		DataDictionary ddic= dataitemsInfoService.findTableSqlWhereByListFaceId(ddicCode,user.getLanguageName());
		String columns = ddic.getTableHeader();
		
		String sql="select t.sysdataid,t.name_"+user.getLanguageName()+" as name,t.code,t.sorts,t.status,t.creator,t.updateuser,"
				+ "t.moduleid,t2.md_name_"+user.getLanguageName()+" as moduleName,"
				+ "to_char(t.updatetime,'yyyy-mm-dd hh24:mi:ss') as updatetime,"
				+ "to_char(t.createdate,'yyyy-mm-dd hh24:mi:ss') as createdate,"
				+ " t.name_zh_CN,t.name_en,t.name_ru"
				+ " from TBL_DIST_NAME t,tbl_module t2,tbl_module2role t3,tbl_role t4,tbl_user t5"
				+ " where t.moduleid=t2.md_id and t2.md_id=t3.rm_moduleid and t3.rm_roleid=t4.role_id and t4.role_id=t5.user_type"
				+ " and t5.user_no="+user.getUserNo();
		if (StringUtils.isNotBlank(typeName)) {
			if (typeName.equals("0") && StringUtils.isNotBlank(findName)) {
				sql+=" and name_"+user.getLanguageName()+" like '%" + findName + "%'";
			}
			if (typeName.equals("1") && StringUtils.isNotBlank(findName)) {
				sql+=" and code like '%" + findName + "%'";
			}
		}
		sql+= " order by t.sorts";
		
		int totals=this.getTotalCountRows(sql);
		List<?> list = this.findCallSql(sql);
		
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"sysdataid\":\""+obj[0]+"\",");
			result_json.append("\"name\":\""+obj[1]+"\",");
			result_json.append("\"code\":\""+obj[2]+"\",");
			result_json.append("\"sorts\":"+obj[3]+",");
			result_json.append("\"status\":"+obj[4]+",");
			result_json.append("\"creator\":\""+obj[5]+"\",");
			result_json.append("\"updateuser\":\""+obj[6]+"\",");
			result_json.append("\"moduleId\":"+obj[7]+",");
			result_json.append("\"moduleName\":\""+obj[8]+"\",");
			result_json.append("\"updatetime\":\""+obj[9]+"\",");
			result_json.append("\"createdate\":\""+obj[10]+"\",");
			result_json.append("\"name_zh_CN\":\""+obj[11]+"\",");
			result_json.append("\"name_en\":\""+obj[12]+"\",");
			result_json.append("\"name_ru\":\""+obj[13]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString().replaceAll("null", "");
	}

	/**
	 * 检索是否有重复的英文名称
	 * 
	 * @param userInfo
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public boolean findResetSysDataCodeListById(User userInfo, String objId, String code) throws Exception {
		boolean result = false;
		String sql = "select sys.sysdataid from tbl_dist_name sys where sys.code=?0 and sys.status=0 and sys.tenantid=?1";
		List<?> esObjList = this.findCallSql(sql, new Object[] { code, userInfo.getUserId() });
		if (null != esObjList && esObjList.size() > 0) {
			String jtl = (String) esObjList.get(0);
			if (objId.equals(jtl)) {
				result = true;
			} else {
				result = false;
			}
		} else {
			result = true;
		}
		return result;
	}

	/**
	 * 创建字典信息
	 * 
	 * @throws Exception
	 * @parem Object
	 */
	public String saveSystemdataInfo(SystemdataInfo systemdataInfo, User userInfo, String paramsdtblstringId) throws Exception {
		String jsonaddstr = "";
		boolean sysBooEname = this.findResetSysDataCodeListById(userInfo, "", systemdataInfo.getCode());
		if (sysBooEname) {
			String uuIDD = UUIDGenerator.randomUUID();
			systemdataInfo.setSysdataid(uuIDD);
			systemdataInfo.setTenantid(userInfo.getUserId());
			systemdataInfo.setStatus(0);
			systemdataInfo.setUpdateuser(userInfo.getUserId());
			systemdataInfo.setCreator(userInfo.getUserId());
			systemdataInfo.setUpdatetime(DateUtils.getTime());
			systemdataInfo.setCreatedate(DateUtils.getTime());
			
			
			if(!StringManagerUtils.isNotNull(systemdataInfo.getName_zh_CN())){
				systemdataInfo.setName_zh_CN(MemoryDataManagerTask.getLanguageResourceItem("zh_CN","unnamed"));
			}
			if(!StringManagerUtils.isNotNull(systemdataInfo.getName_en())){
				systemdataInfo.setName_en(MemoryDataManagerTask.getLanguageResourceItem("en","unnamed"));
			}
			if(!StringManagerUtils.isNotNull(systemdataInfo.getName_ru())){
				systemdataInfo.setName_ru(MemoryDataManagerTask.getLanguageResourceItem("ru","unnamed"));
			}
//			addparamstr += "" + graw.name_zh_CN + "&" + graw.name_en+ "&" + graw.name_ru+ "&" + graw.code + "&" + graw.datavalue + "&" + graw.sorts + "&" + graw.status + "|";
			this.save(systemdataInfo);
			if (StringUtils.isNotBlank(paramsdtblstringId)) {
				String[] k_paramt = paramsdtblstringId.split("\\|");
				for (int k = 0; k < k_paramt.length; k++) {
					String add_paramobj = "" + k_paramt[k];
					DataitemsInfo dinfo = new DataitemsInfo();
					String[] param = add_paramobj.split("\\&");
					if(param.length>0){
						String name_zh_CN=param[0];
						String name_en=param[1];
						String name_ru=param[2];
						String code=param[3];
						String datavalue=param[4];
						String sorts=param[5];
						String statusStr=param[6];
						
						Integer sort = 0;
						if (StringUtils.isNotBlank(sorts)) {
							sort = StringManagerUtils.stringToInteger(sorts);
						}
						Integer status = 0;
						if (StringUtils.isNotBlank(statusStr)) {
							if("false".equalsIgnoreCase(statusStr)){
								statusStr="0";
							}else if("true".equalsIgnoreCase(statusStr)){
								statusStr="1";
							}
							status = StringManagerUtils.stringToInteger(statusStr);
						}
						dinfo.setSysdataid(uuIDD);
						dinfo.setName_zh_CN(name_zh_CN);
						dinfo.setName_en(name_en);
						dinfo.setName_ru(name_ru);
						dinfo.setCode(code);
						dinfo.setDatavalue(datavalue);
						dinfo.setSorts(sort);
						dinfo.setStatus(status);
						dinfo.setTenantid(userInfo.getUserId());
						dinfo.setCreator(userInfo.getUserId());
						dinfo.setCreatedate(DateUtils.getTime());
						dinfo.setUpdateuser(userInfo.getUserId());
						dinfo.setUpdatetime(DateUtils.getTime());
						
						if(!StringManagerUtils.isNotNull(dinfo.getName_zh_CN())){
							dinfo.setName_zh_CN(MemoryDataManagerTask.getLanguageResourceItem("zh_CN","unnamed"));
						}
						if(!StringManagerUtils.isNotNull(dinfo.getName_en())){
							dinfo.setName_en(MemoryDataManagerTask.getLanguageResourceItem("en","unnamed"));
						}
						if(!StringManagerUtils.isNotNull(dinfo.getName_ru())){
							dinfo.setName_ru(MemoryDataManagerTask.getLanguageResourceItem("ru","unnamed"));
						}
					}
					dataitemsInfoService.saveDataitemsInfo(dinfo);
				}
			}
			jsonaddstr = "{success:true,msg:true}";
		} else {
			jsonaddstr = "{success:true,msg:false,error:'此用户已创建了该英文名称！'}";
		}
		return jsonaddstr;
	}

	/**
	 * 修改数据字典的数据名称信息
	 * 
	 * @throws Exception
	 * @parem Object
	 */
	public String editSystemdataInfo(SystemdataInfo systemdataInfo, User userInfo, String paramsId) throws Exception {
		// aaa:0,bbbb:1
		String jsonaddstr = "";
		if (systemdataInfo != null) {
			systemdataInfo.setUpdateuser(userInfo.getUserName());
			systemdataInfo.setCreator(userInfo.getUserName());
			systemdataInfo.setUpdatetime(DateUtils.getTime());
			systemdataInfo.setCreatedate(DateUtils.getTime());
			this.edit(systemdataInfo);
			// 处理字典域值信息
			if (StringUtils.isNotBlank(paramsId)) {
				String[] getLiSpl = paramsId.split(",");
				if (getLiSpl.length > 0) {
					for (String strobj : getLiSpl) {
						String getLnode = strobj;
						if (StringUtils.isNotBlank(getLnode)) {
							String[] splitString = getLnode.split("\\:");
							String getUp_Id = splitString[0].trim();
							String getUp_Val = splitString[1].trim();
							dataitemsInfoService.updateDataitemsInfoById(getUp_Id, getUp_Val);
						}

					}

				}

			}
			jsonaddstr = "{success:true,msg:true}";
		} else {
			jsonaddstr = "{success:true,msg:false,error:'此数据字典信息修改失败!'}";
		}
		return jsonaddstr;
	}
	
	public int updateDataDictionaryInfo(String sysdataid,String name,String code,String sorts,String moduleName,String language){
		int r=0;
		try {
			String sql = "update TBL_DIST_NAME t "
					+ "set t.name_"+language+"='"+name+"',"
					+ "t.code='"+code+"',"
					+ "t.sorts= "+sorts
					+ "where t.sysdataid='"+sysdataid+"' ";;
			r=this.getBaseDao().updateOrDeleteBySql(sql);
		} catch (Exception e) {
			r=0;
		}
		return r;
	}

	/**
	 * 删除字典信息
	 * 
	 * @author 钱邓水 2012-3-8
	 * @throws Exception
	 * @parem String
	 */
	public boolean deleteSystemdataInfoById(User usersInfo, String sysid) throws Exception {
		boolean result = false;
		if (!StringUtils.isBlank(sysid)) {
			String[] delstr_list = sysid.split(",");
			for (int i = 0; i < delstr_list.length; i++) {
				SystemdataInfo systemdataInfo = this.findById(delstr_list[i]);
				// 删除标志设定
				if(systemdataInfo!=null){
					systemdataInfo.setStatus(1);
					systemdataInfo.setTenantid(usersInfo.getUserId());
					// 更新情报设置:ID
					systemdataInfo.setUpdateuser(usersInfo.getUserId());
					// 执行修改对象
					// edit(systemdataInfo);
					String syssql = "DELETE SystemdataInfo u where u.sysdataid  ='" + delstr_list[i] + "'";
					String sql = "DELETE DataitemsInfo dt  where  dt.sysdataid  ='" + delstr_list[i] + "' ";
					getBaseDao().bulkObjectDelete(syssql);
					getBaseDao().bulkObjectDelete(sql);
				}
			}
			result = true;
		}
		return result;
	}

	/**
	 * <P>描述：查询数据库的字典表，将数据字典信息存放在数据缓存中</p>
	 * 
	 * @author gao 2014-05-08
	 * @return  Map<String, Object> 
	 */
//	@SuppressWarnings("deprecation")
//	public Map<String, Object> initDataDictionaryPutInCache() {
//		String sqlData = " from SystemdataInfo  sys  where  sys.status=0 ";
//		Map<String, Object> map = DataModelMap.getMapObject();
//		DataDictionary ddicDataDictionary = null;
//		try {
//			List<SystemdataInfo> syseNameList = this.find(sqlData.toString());
//			if (null != syseNameList && syseNameList.size() > 0) {
//				for (SystemdataInfo sysInfo : syseNameList) {
//					String code = sysInfo.getCode();//模块字典的英文名称
//					ddicDataDictionary = dataitemsInfoService.findTableSqlWhereByListFaceId(code);
//					map.put(code, "");
//					map.put(code, ddicDataDictionary);//将数据存储 在map对象中
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return map;
//	}

	/**
	 * <p>递归获取当前组织id信息</p>
	 * 
	 * @author  gao 2014-05-14
	 * @param orgId
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String findCurrentUserOrgIdInfo(String orgId,String language) {
		return this.getCurrentUserOrgIds(Integer.parseInt(orgId),language);

	}
}
