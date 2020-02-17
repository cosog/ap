package com.gao.service.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gao.model.User;
import com.gao.model.data.DataDictionary;
import com.gao.model.data.DataitemsInfo;
import com.gao.model.data.SystemdataInfo;
import com.gao.service.base.BaseService;
import com.gao.utils.DataModelMap;
import com.gao.utils.DateUtils;
import com.gao.utils.Page;
import com.gao.utils.StringManagerUtils;
import com.gao.utils.UUIDGenerator;

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
				pager.setWhere("cname like '%" + name + "%'");
			}
			// 英文类型检索
			if (typeName.equals("1") && StringUtils.isNotBlank(name)) {
				pager.setWhere("ename like '%" + name + "%'");
			}
		}

		pager.setSort(" sorts asc");

		List<SystemdataInfo> list = findAllPageByEntity(pager);
		return list;
	}

	/**
	 * 检索是否有重复的英文名称
	 * 
	 * @param userInfo
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public boolean findResetSysDataCodeListById(User userInfo, String objId, String ename) throws Exception {
		boolean result = false;
		String sql = "select sys.sysdataid from tbl_dist_name sys where sys.ename=? and sys.status=0 and sys.tenantid=?";
		List<?> esObjList = this.findExecuteSqlQuery(sql, new Object[] { ename, userInfo.getUserId() });
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
		boolean sysBooEname = this.findResetSysDataCodeListById(userInfo, "", systemdataInfo.getEname());
		if (sysBooEname) {
			String uuIDD = UUIDGenerator.randomUUID();
			systemdataInfo.setSysdataid(uuIDD);
			systemdataInfo.setTenantid(userInfo.getUserId());
			systemdataInfo.setStatus(0);
			systemdataInfo.setUpdateuser(userInfo.getUserName());
			systemdataInfo.setCreator(userInfo.getUserName());
			systemdataInfo.setUpdatetime(DateUtils.getTime());
			systemdataInfo.setCreatedate(DateUtils.getTime());
			this.save(systemdataInfo);
			if (StringUtils.isNotBlank(paramsdtblstringId)) {
				String[] k_paramt = paramsdtblstringId.split("\\|");
				for (int k = 0; k < k_paramt.length; k++) {
					String add_paramobj = "" + k_paramt[k];
					DataitemsInfo dinfo = new DataitemsInfo();
					String[] param = add_paramobj.split("\\&");
					if(param.length>0){
						String i = param[3];
						Integer sorts = 0;
						if (StringUtils.isNotBlank(i)) {
							sorts = StringManagerUtils.stringToInteger(i);
						}
						String ij = param[4];
						Integer status = 0;
						if (StringUtils.isNotBlank(ij)) {
							if("false".equalsIgnoreCase(ij)){
								ij="0";
							}else if("true".equalsIgnoreCase(ij)){
								ij="1";
							}
							status = StringManagerUtils.stringToInteger(ij);
						}
						dinfo.setSysdataid(uuIDD);
						dinfo.setCname("" + param[0]);
						dinfo.setEname("" + param[1]);
						dinfo.setSorts(sorts);
						dinfo.setDatavalue("" + param[2]);
						dinfo.setStatus(status);
						dinfo.setTenantid(userInfo.getUserId());
						dinfo.setCreator(userInfo.getUserId());
						dinfo.setCreatedate(DateUtils.getTime());
						dinfo.setUpdateuser(userInfo.getUserName());
						dinfo.setUpdatetime(DateUtils.getTime());
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
	@SuppressWarnings("deprecation")
	public Map<String, Object> initDataDictionaryPutInCache() {
		String sqlData = " from SystemdataInfo  sys  where  sys.status=0 ";
		Map<String, Object> map = DataModelMap.getMapObject();
		DataDictionary ddicDataDictionary = null;
		try {
			List<SystemdataInfo> syseNameList = this.find(sqlData.toString());
			if (null != syseNameList && syseNameList.size() > 0) {
				for (SystemdataInfo sysInfo : syseNameList) {
					String ename = sysInfo.getEname();//模块字典的英文名称
					ddicDataDictionary = dataitemsInfoService.findTableSqlWhereByListFaceId(ename);
					map.put(ename, "");
					map.put(ename, ddicDataDictionary);//将数据存储 在map对象中
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * <p>递归获取当前组织id信息</p>
	 * 
	 * @author  gao 2014-05-14
	 * @param orgId
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String findCurrentUserOrgIdInfo(String orgId) {
		return this.getCurrentUserOrgIds(Integer.parseInt(orgId));

	}
}
