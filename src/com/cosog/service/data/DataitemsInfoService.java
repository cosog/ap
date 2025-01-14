package com.cosog.service.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cosog.model.User;
import com.cosog.model.data.DataDictionary;
import com.cosog.model.data.DataitemsInfo;
import com.cosog.service.base.BaseService;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.DataDicUtils;
import com.cosog.utils.DateUtils;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;

/**
 * 系统数据字典数据项值管理
 * 
 * @author 钱邓水
 * @data 2014-4-10
 */
@Service
public class DataitemsInfoService extends BaseService<DataitemsInfo> {
	/**
	 * 根据字典ID查询数据项值信息
	 * 
	 * @author 钱邓水
	 * @throws Exception
	 * @parem typeName 类型 name 字典名称
	 */
	public List<DataitemsInfo> findDataitemsInfoPageListById(Page pager, User userInfo, String sysId, String tab, String dataName) throws Exception {
		List<DataitemsInfo> list = null;

		// 检索条件
		if (StringUtils.isNotBlank(sysId)) {
			pager.setWhere("sysdataid='" + sysId + "'");
		}
		if (StringUtils.isNotBlank(tab)) {
			if (StringUtils.isNotBlank(dataName) && tab.equals("0")) {
				pager.setWhere("code  like'%" + dataName + "%'");
			}
			if (StringUtils.isNotBlank(dataName) && tab.equals("1")) {
				pager.setWhere("name_"+userInfo.getLanguageName()+"  like'%" + dataName + "%'");
			}
		}

		pager.setSort(" sorts  asc");
		list = findAllPageByEntity(pager);

		return list;
	}
	
	public List<DataitemsInfo> getDataDictionaryItemList(Page pager, User userInfo, String dictionaryId, String type, String value) throws Exception {
		List<DataitemsInfo> list = null;
		if (StringUtils.isNotBlank(dictionaryId)) {
			pager.setWhere("sysdataid='" + dictionaryId + "'");
		}
		if (StringUtils.isNotBlank(value)) {
			if ("0".equals(type)) {
				pager.setWhere("code  like'%" + value + "%'");
			}else if ("1".equals(type)) {
				pager.setWhere("name_"+userInfo.getLanguageName()+"  like'%" + value + "%'");
			}
		}
		pager.setSort(" sorts  asc");
		list = findAllPageByEntity(pager);

		return list;
	}

	/**
	 * 创建数据项值信息
	 * 
	 * @throws Exception
	 * @parem Object
	 */
	public boolean saveDataitemsInfo(DataitemsInfo obj) throws Exception {
		boolean result = false;

		if (null != obj && !"".equals(obj)) {
			this.save(obj);
			result = true;
		}

		return result;
	}

	public void updateDataitemsInfoById(String dataId, String valStr) {
		String upSql = "update tbl_dist_item dup set dup.status=" + valStr + " where dup.dataitemid='" + dataId + "'";
		try {
			this.updateSql(upSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 增加某个模块字典的数据项值信息
	 * 
	 * @throws Exception
	 * @parem Object
	 */
	public String saveDataitemsInfo(DataitemsInfo dinfo, User userInfo, String sysId) throws Exception {
		String jsonaddstr = "";
		if (StringUtils.isNotBlank(sysId)) {
			dinfo.setSysdataid(sysId);
			this.saveDataitemsInfo(dinfo);
			jsonaddstr = "{success:true,msg:true}";
		} else {
			jsonaddstr = "{success:true,msg:false,error:'此用户已创建了该数据项！'}";
		}
		return jsonaddstr;
	}

	/**
	 * 增加某个模块字典的数据项值信息
	 * 
	 * @throws Exception
	 * @parem Object
	 */
	public String editDataitemsInfo(DataitemsInfo dinfo, User userInfo) throws Exception {
		String jsonaddstr = "";
		if (dinfo != null) {
			dinfo.setUpdateuser(userInfo.getUserName());
			dinfo.setCreatedate(DateUtils.getTime());
			dinfo.setUpdatetime(DateUtils.getTime());
			//this.edit(dinfo);
			getBaseDao().updateObject(dinfo);
			jsonaddstr = "{success:true,msg:true}";
		} else {
			jsonaddstr = "{success:true,msg:false,error:'此数据项修改失败!'}";
		}
		return jsonaddstr;
	}

	/**
	 * 删除数据项值信息
	 * 
	 * @author 钱邓水 2012-3-13
	 * @throws Exception
	 * @parem String
	 */
	public boolean deleteDataitemsInfoById(User userInfo, String dataid) throws Exception {
		boolean result = false;
		if (StringUtils.isNotBlank(dataid)) {
			String sql = "delete DataitemsInfo ditem   where ditem.dataitemid in(:val)";
			this.deleteHqlQueryParameter(sql, "val", dataid.split(","));
			result = true;
		}

		return result;
	}

	/**
	 * 根据字典的英文查询域值集合信息
	 * 
	 * @param dataCode
	 * @return
	 */
	public List<DataitemsInfo> findTableHeaderByListFaceId(String dataCode) {
		String sqlData = "from DataitemsInfo dtm where dtm.status=1 and dtm.sysdataid in (select sys.sysdataid from SystemdataInfo sys where sys.status=0 and sys.code=?0 ) ";
		List<DataitemsInfo> getDataitemsInfoList = null;
		try {
			getDataitemsInfoList = this.find(sqlData.toString(), new Object[] { dataCode });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getDataitemsInfoList;
	}

	/**
	 * 查询表头列信息集合
	 * 
	 * @param dataCode
	 * @return
	 */
	public String findTableHeaderByColumnsFaceId(String dataCode,String language) {
		String sqlData = "from DataitemsInfo dtm where dtm.status=1 and dtm.sysdataid in (select sys.sysdataid from SystemdataInfo sys where sys.status=0 and sys.code=?0 ) order by sorts ";

		StringBuffer strBuf = new StringBuffer();
		strBuf.append("[");
		try {
			List<DataitemsInfo> dataColumnsList = this.find(sqlData.toString(), new Object[] { dataCode });
			if (null != dataColumnsList && dataColumnsList.size() > 0) {
				for (DataitemsInfo dataInfo : dataColumnsList) {
					String code = dataInfo.getCode();
					if (!code.equals("table") && !code.equals("where") && !code.equals("order") && !code.equals("group") && !code.equals("params")) {
						String header="";
						if("zh_CN".equalsIgnoreCase(language)){
							header=dataInfo.getName_zh_CN();
						}else if("EN".equalsIgnoreCase(language)){
							header=dataInfo.getName_en();
						}else if("RU".equalsIgnoreCase(language)){
							header=dataInfo.getName_ru();
						}
						strBuf.append("{ ");
						strBuf.append("\"header\":\"" + header + "\",");
						strBuf.append("\"dataIndex\":\"" + dataInfo.getCode() + "\"");
						if (StringUtils.isNotBlank(dataInfo.getDatavalue())) {
							strBuf.append("," + dataInfo.getDatavalue());
						}
						strBuf.append("},");
					}
				}
				strBuf.deleteCharAt(strBuf.length() - 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		strBuf.append("]");
		return strBuf.toString();
	}

	/**
	 * <p>
	 * 描述：字典SQL查询语句,查询数据字典构建数据字典对象信息
	 * </p>
	 * 
	 * @param dataCode
	 *            数据字典英文名称
	 * @return 数据字典信息对象
	 */
	public DataDictionary findTableSqlWhereByListFaceId(String code,String language) {
		StringBuffer sqlColumn = new StringBuffer();
		StringBuffer strBuf = new StringBuffer();
		String sqlTable = "";
		String sqlWhere = "";
		String sqlOrderBy = "";
		String sqlGroupBy="";
		List<String> dynamics = new ArrayList<String>();
		List<String> headers = new ArrayList<String>();
		List<String> fields = new ArrayList<String>();
		DataDictionary ddic = new DataDictionary();
		strBuf.append("[");
		sqlColumn.append("select ");
		String sqlData = "from DataitemsInfo dtm where dtm.status=1 and dtm.sysdataid in (select sys.sysdataid from SystemdataInfo sys where sys.status=0 and sys.code=?0 ) order by dtm.sorts asc ";
		try {
			// 根据模块字典英文名称从数据库中找出该模块的字典数据信息
			List<DataitemsInfo> dataWhereList = this.find(sqlData.toString(), new Object[] { code });

			if (null != dataWhereList && dataWhereList.size() > 0) {
				Map<String, List<DataDictionary>> map = DataDicUtils.initData(dataWhereList,language);// 把集合中含有多表头信息的数据封装在Map
																							// 集合了里
			TreeMap<String, List<DataDictionary>> treemap = new TreeMap<String, List<DataDictionary>>(map);
				for (DataitemsInfo dataInfo : dataWhereList) {
					String dataCode = dataInfo.getCode();// 字典英文名称对应数据库中的字段信息
					
					String header="";
					if("zh_CN".equalsIgnoreCase(language)){
						header=dataInfo.getName_zh_CN();
					}else if("EN".equalsIgnoreCase(language)){
						header=dataInfo.getName_en();
					}else if("RU".equalsIgnoreCase(language)){
						header=dataInfo.getName_ru();
					}
					
					String enameField = dataCode.trim();
					String dataValueString = dataInfo.getDatavalue();
					if (!StringUtils.isNotBlank(dataValueString)) {
						dataValueString = "";
					}

					if (dataCode.indexOf("root") == -1) { // 过滤掉根节点
						if (dataCode.indexOf(" as ") > 0) {
							/* 当该字段中含有 as 时，截取as后的字符串作为字段数据 */
							enameField = dataCode.substring(dataCode.indexOf(" as ") + 3).trim();

						} else if (dataCode.indexOf("#") > 0) {
							enameField = dataCode.substring(dataCode.lastIndexOf("#") + 1);// 如果字段中含有“#”截取最后一段作为字段数据
						}
					}
					if (!dataCode.equals("table") && !dataCode.equals("where") && !dataCode.equals("order") && !dataCode.equals("group") && !dataCode.equals("params")) {
						int index = dataCode.indexOf("root");
						if (index >= 0) {
							String[] rootVal = dataCode.split("_");// 判断当前节点是根节点
							String key = rootVal[0];
							strBuf.append(" { header: \"" + header + "\",dataIndex:\"" + key + "\",children:[");
							DataDicUtils.emptyBuffer();
							String resultString = DataDicUtils.createChildHeader(key, treemap);// 调用递归函数创建子节点数据信息
							strBuf.append(resultString);
							strBuf.append(" ]},");
						} else {
							// 该节点不含有root字符串代表为普通的字段
							int nowIndex = dataCode.indexOf("#");// 不能存在“#”字符信息
							if (nowIndex == -1) {
								strBuf.append("{ ");
								strBuf.append("\"header\":\"" + header + "\",");
								strBuf.append("\"dataIndex\":\"" + enameField.trim() + "\"");
								if (StringUtils.isNotBlank(dataValueString) &&!"null".equals(dataValueString)) {
									strBuf.append("," + dataValueString);
								}
								strBuf.append(" ,children:[] },");
							}
						}

						if (dataCode.indexOf("root") == -1) {
							if (dataCode.indexOf("#") > 0) {
								dataCode = dataCode.substring(dataCode.lastIndexOf("#") + 1);
							}
							if (dataCode.indexOf("child") == -1) {
								sqlColumn.append(dataCode + ",");
								headers.add(header);
							}

						}
						if (dataCode.indexOf("root") == -1 && dataCode.indexOf("child") == -1) {
							fields.add(enameField.trim());
						}
					} else if (dataCode.equals("table")) {
						sqlTable = "  from  " + dataValueString;
					} else if (dataCode.equals("where")) {
						sqlWhere = " where  1=1 " + dataValueString;
					} else if (dataCode.equals("order")) {
						sqlOrderBy = "  " + dataValueString;
					} else if(dataCode.equals("group")){
						sqlGroupBy="  "+dataValueString;
					}
					else if (dataCode.equals("params")) {
						dynamics.add(dataValueString);
					}
				}
				strBuf.deleteCharAt(strBuf.length() - 1);//去掉最后一个逗号
				sqlColumn.deleteCharAt(sqlColumn.length() - 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		strBuf.append("]");
		String xWhere = sqlColumn.toString();
		if(StringManagerUtils.isNotNull(sqlTable)){
			xWhere+=" "+sqlTable;
		}
		if(StringManagerUtils.isNotNull(sqlWhere)){
			xWhere+=" "+sqlWhere;
		}
		ddic.setTableHeader(strBuf.toString());
		ddic.setHeaders(headers);
		ddic.setFields(fields);
		ddic.setSql(xWhere);
		ddic.setParams(dynamics);
		ddic.setOrder(sqlOrderBy);
		ddic.setGroup(sqlGroupBy);
		return ddic;
	}
	
	/**
	 * 根据配置的产量单位，初始化数据字典
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public String initProductionDataDictionary() throws SQLException {
		StringBuffer strBuf = new StringBuffer();
		StringBuffer weightItemIds= new StringBuffer();
		StringBuffer volumetricItemIds= new StringBuffer();
		int r=0;
		String weightItemSql="select t.dataitemid,t.sysdataid,t.name_zh_cn,t.code,t.status from TBL_DIST_ITEM t"
				+ " where t.name_zh_cn like '%t/d%'";
		String showWeightItemSql=weightItemSql+" and t.status=1";
		String volumetricItemSql="select t.dataitemid,t.sysdataid,t.name_zh_cn,t.code,t.status from TBL_DIST_ITEM t"
				+ " where t.name_zh_cn like '%m^3/d%' and t.name_zh_cn<>'理论排量(m^3/d)' and t.name_zh_cn<>'生产气油比(m^3/d)'";
		String showVolumetricItemSql=volumetricItemSql+" and t.status=1";
		
		int showWeightItemCount=this.getTotalCountRows(showWeightItemSql);
		int showVolumetricItemCount=this.getTotalCountRows(showVolumetricItemSql);
		
		if(Config.getInstance().configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")&&showVolumetricItemCount>showWeightItemCount){//如果配置的时重量
			List<?> weightItemList = this.findCallSql(weightItemSql);
			List<?> volumetricItemList = this.findCallSql(volumetricItemSql);
			for(int i=0;i<volumetricItemList.size();i++){
				Object[] volumetricItemObj = (Object[])volumetricItemList.get(i);
				if("1".equals(volumetricItemObj[4]+"")){
					volumetricItemIds.append(volumetricItemObj[0]+",");
					for(int j=0;j<weightItemList.size();j++){
						Object[] weightItemObj = (Object[])weightItemList.get(j);
						if((weightItemObj[1]+"").equalsIgnoreCase((volumetricItemObj[1]+"")) && 
								(weightItemObj[2]+"").split("\\(")[0].equals((volumetricItemObj[2]+"").split("\\(")[0])){
							weightItemIds.append(weightItemObj[0]+",");
						}
					}
				}
			}
		}else if(showWeightItemCount>showVolumetricItemCount){//如果配置体积
			List<?> weightItemList = this.findCallSql(weightItemSql);
			List<?> volumetricItemList = this.findCallSql(volumetricItemSql);
			for(int i=0;i<weightItemList.size();i++){
				Object[] weightItemObj = (Object[])weightItemList.get(i);
				if("1".equals(weightItemObj[4]+"")){
					weightItemIds.append(weightItemObj[0]+",");
					for(int j=0;j<volumetricItemList.size();j++){
						Object[] volumetricItemObj = (Object[])volumetricItemList.get(j);
						if((volumetricItemObj[1]+"").equalsIgnoreCase((weightItemObj[1]+"")) && 
								(volumetricItemObj[2]+"").split("\\(")[0].equals((weightItemObj[2]+"").split("\\(")[0])){
							volumetricItemIds.append(volumetricItemObj[0]+",");
						}
					}
				}
			}
		}
		if(weightItemIds.toString().endsWith(",")){
			weightItemIds.deleteCharAt(weightItemIds.length() - 1);
		}
		if(volumetricItemIds.toString().endsWith(",")){
			volumetricItemIds.deleteCharAt(volumetricItemIds.length() - 1);
		}
		
		String updateWeightItemsSql="";
		String updateVolumetricItemsSql="";
		String updateWeightCutSql="";
		String updateVolumetricCutSql="";
		if(Config.getInstance().configFile.getAp().getOthers().getProductionUnit().equalsIgnoreCase("ton")&&showVolumetricItemCount>showWeightItemCount&&StringManagerUtils.isNotNull(weightItemIds.toString())&&StringManagerUtils.isNotNull(volumetricItemIds.toString())){//如果配置的时重量
			updateWeightItemsSql="update TBL_DIST_ITEM t set t.status=1 where t.dataitemid in("+weightItemIds+")";
			updateVolumetricItemsSql="update TBL_DIST_ITEM t set t.status=0 where t.dataitemid in("+volumetricItemIds+")";
			
			updateWeightCutSql="update TBL_DIST_ITEM t set t.status=1 where lower(t.code)='weightwatercut'";
			updateVolumetricCutSql="update TBL_DIST_ITEM t set t.status=0 where lower(t.code)='volumewatercut'";
			
			
		}else if(showWeightItemCount>showVolumetricItemCount&&StringManagerUtils.isNotNull(weightItemIds.toString())&&StringManagerUtils.isNotNull(volumetricItemIds.toString())){//如果配置体积
			updateWeightItemsSql="update TBL_DIST_ITEM t set t.status=0 where t.dataitemid in("+weightItemIds+")";
			updateVolumetricItemsSql="update TBL_DIST_ITEM t set t.status=1 where t.dataitemid in("+volumetricItemIds+")";
			
			updateWeightCutSql="update TBL_DIST_ITEM t set t.status=0 where lower(t.code)='weightwatercut' and t.sysdataid not in('436802a1c0074a79aafd00ce539166f4','aad8b76fdaf84a1194de5ec0a4453631')";
			updateVolumetricCutSql="update TBL_DIST_ITEM t set t.status=1 where lower(t.code)='volumewatercut'";
		}
		if(StringManagerUtils.isNotNull(updateWeightItemsSql)){
			r=this.getBaseDao().updateOrDeleteBySql(updateWeightItemsSql);
			r=this.getBaseDao().updateOrDeleteBySql(updateVolumetricItemsSql);
			
			r=this.getBaseDao().updateOrDeleteBySql(updateWeightCutSql);
			r=this.getBaseDao().updateOrDeleteBySql(updateVolumetricCutSql);
		}
		
		return strBuf.toString();
	}
}
