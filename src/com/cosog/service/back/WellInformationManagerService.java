package com.cosog.service.back;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.WellInformation;
import com.cosog.model.drive.KafkaConfig;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.model.gridmodel.WellGridPanelData;
import com.cosog.model.gridmodel.WellHandsontableChangedData;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.EquipmentDriverServerTask;
import com.cosog.utils.EquipmentDriveMap;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;

@Service("wellInformationManagerService")
public class WellInformationManagerService<T> extends BaseService<T> {
	@Autowired
	private CommonDataService service;
	
	public String loadWellComboxList(Page pager,String orgId,String wellName,String wellType) throws Exception {
		//String orgIds = this.getUserOrgIds(orgId);
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		String sql = " select  t.wellName as wellName,t.wellName as dm from  tbl_wellinformation t  ,tbl_org  g where 1=1 and  t.orgId=g.org_id  and g.org_id in ("
				+ orgId + ")";
		if (wellType.trim().equalsIgnoreCase("200")) {
			sql += " and t.liftingtype like '2%'";
		}else if (wellType.trim().equalsIgnoreCase("400")) {
			sql += " and t.liftingtype like '4%'";
		}
		
		if (StringManagerUtils.isNotNull(wellName)) {
			sql += " and t.wellName like '%" + wellName + "%'";
		}
		sql += " order by t.sortNum, t.wellName";
		sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
		sqlCuswhere.append(""+sql);
		int maxvalue=pager.getLimit()+pager.getStart();
		sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
		sqlCuswhere.append(" where rn >"+pager.getStart());
		String finalsql=sqlCuswhere.toString();
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(finalsql);
			result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\"选择全部\"},");
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
			result_json.append("]}");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}
	
	public void saveWellEditerGridData(WellHandsontableChangedData wellHandsontableChangedData,String orgIds,String orgId) throws Exception {
		getBaseDao().saveWellEditerGridData(wellHandsontableChangedData, orgIds,orgId);
	}
	
	public void editWellName(String oldWellName,String newWellName,String orgid) throws Exception {
		getBaseDao().editWellName(oldWellName,newWellName,orgid);
	}

	public List<T> loadWellInformationID(Class<T> clazz) {
		String queryString = "SELECT u.jlbh,u.jh FROM WellInformation u order by u.jlbh ";
		return getBaseDao().find(queryString);
	}

	public boolean judgeWellExistsOrNot(String jh) {
		boolean flag = false;
		if (StringUtils.isNotBlank(jh)) {
			String queryString = "SELECT u.jh FROM WellInformation u where u.jh='"
					+ jh + "' order by u.jlbh ";
			List<WellInformation> list = getBaseDao().find(queryString);
			if (list.size() > 0) {
				flag = true;
			}
		}
		return flag;
	}

	public List<T> loadWellOrgInfo() {
		String queryString = "SELECT distinct(o.orgName) as orgName ,o.orgCode FROM WellInformation u ,Org o where u.dwbh=o.org_code  order by o.orgCode";
		return getBaseDao().find(queryString);
	}

	public String showWellTypeTree() throws Exception {
		String sql = "select t.dm as id,t.itemname as text from tbl_code t where t.itemcode='JLX'";
		List<?> list = this.findCallSql(sql);
		StringBuffer result_json = new StringBuffer();
		String get_key = "";
		String get_val = "";
		result_json.append("[");
		if (null != list && list.size() > 0) {
			for (Object o : list) {
				Object[] obj = (Object[]) o;
				get_key = obj[0] + "";
				get_val = obj[1] + "";
				if (get_key.equalsIgnoreCase("100") || get_key.equalsIgnoreCase("200")) {
					 if(get_key.equalsIgnoreCase( "200")){
						result_json.deleteCharAt(result_json.length() - 1);
						result_json.append("]}]} ,");
					}
					result_json.append("{");
					result_json.append("id:'" + get_key + "',");
					result_json.append("text:'" + get_val + "',");
					result_json.append("expanded:true,");
					result_json.append("children:[");
				} else if (get_key.equalsIgnoreCase( "101") || get_key .equalsIgnoreCase("111")) {
					if(get_key .equalsIgnoreCase( "111")){
						result_json.deleteCharAt(result_json.length() - 1);
						result_json.append("]},");
					}
					result_json.append("{");
					if(get_key.equalsIgnoreCase( "101")||get_key.equalsIgnoreCase( "111")){
						result_json.append("id:'" + get_key + "_p',");
					}else{
					    result_json.append("id:'" + get_key + "',");
					}
					result_json.append("text:'" + get_val + "',");
					result_json.append("expanded:true,");
					result_json.append("children:[");
					if(get_key .equalsIgnoreCase( "101")){
						result_json.append("{id:'" + get_key + "',");
						result_json.append("text:'" + get_val + "',");
						result_json.append("leaf:true },");
					}else  if(get_key .equalsIgnoreCase( "111")){
						result_json.append("{id:'" + get_key + "',");
						result_json.append("text:'" + get_val + "',");
						result_json.append("leaf:true },");
					}
				} else if (get_key.startsWith("10") || get_key.startsWith("11")
						|| get_key.startsWith("20")) {
					result_json.append("{id:'" + get_key + "',");
					result_json.append("text:'" + get_val + "',");
					result_json.append("leaf:true },");
				}
			}
			if (result_json.toString().length() > 1) {
				result_json.deleteCharAt(result_json.length() - 1);
			}
		}
		result_json.append("]}]");
		String da="100_p";
		da.substring(0, 3);
			
		return result_json.toString();

	}

	public String queryWellInfoParams(Page pager,String orgid,String orgCode, String resCode,String type,String jc,String jhh,String jh,String jtype) throws Exception {
		StringBuffer result_json = new StringBuffer();
		StringBuffer sqlCuswhere = new StringBuffer();
		String sql = "";
		if (type.equalsIgnoreCase("res")) {
			sql = " select  distinct p.yqcbh,r.res_name  from tbl_wellinformation p,sc_res r where p.yqcbh=r.res_code ";
		} else if (type.equalsIgnoreCase("jh")) {
			sql = " select  p.jh as jh ,p.jh as dm from tbl_wellinformation p,tbl_org org,t_wellorder t where 1=1 and p.jh=t.jh and p.dwbh=org.org_code and org.org_id in ("+orgid+") ";
		}else if (type.equalsIgnoreCase("jhh")) {
			sql = " select distinct p.jhh as jhh,p.jhh as dm,t.pxbh from  tbl_wellinformation p  ,tbl_org  g,t_018_wellringorder t where 1=1 and p.jhh=t.jhh and p.dwbh=g.org_code  and g.org_id in ("
					+ orgid + ")";
		}else if (type.equalsIgnoreCase("jc")) {
			sql = " select distinct p.jc as jc,p.jc as dm,t.pxbh from  tbl_wellinformation p  ,tbl_org  g,t_017_wellsiteorder t where 1=1 and p.jc=t.jc and p.dwbh=g.org_code  and g.org_id in ("
					+ orgid + ")";
		}
		if (jtype.equalsIgnoreCase("in")) {
			sql += " and p.jlx like '2%' ";
		} else {
			sql += " and p.jlx like '1%' ";
		}
		if (StringUtils.isNotBlank(orgCode)) {
			sql += " and p.dwbh like '%" + orgCode + "%'";
		}
		if (StringUtils.isNotBlank(resCode)) {
			sql += " and p.yqcbh like '%" + resCode + "%'";
		}
		if (StringUtils.isNotBlank(jh)) {
			sql += " and p.jh like '%" + jh + "%'";
		}
		if (StringUtils.isNotBlank(jhh)) {
			sql += " and p.jhh like '%" + jhh + "%'";
		}
		if (StringUtils.isNotBlank(jc)) {
			sql += " and p.jc like '%" + jc + "%'";
		}
		if (type.equalsIgnoreCase("res")) {
			sql += " order by p.yqcbh ";
		} else if (type.equalsIgnoreCase("jh")) {
			sql += " order by t.pxbh, p.jh";
		} else if (type.equalsIgnoreCase("jhh")) {
			sql += " order by t.pxbh, p.jhh";
		}else if (type.equalsIgnoreCase("jc")) {
			sql += " order by t.pxbh, p.jc";
		}
		sqlCuswhere.append("select * from   ( select a.*,rownum as rn from (");
		sqlCuswhere.append(""+sql);
		int maxvalue=pager.getLimit()+pager.getStart();
		sqlCuswhere.append(" ) a where  rownum <="+maxvalue+") b");
		sqlCuswhere.append(" where rn >"+pager.getStart());
		String finalsql=sqlCuswhere.toString();
		try {
			int totals=this.getTotalCountRows(sql);
			List<?> list = this.findCallSql(finalsql);
			result_json.append("{\"totals\":"+totals+",\"list\":[{boxkey:\"\",boxval:\"选择全部\"},");
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
			result_json.append("]}");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_json.toString();
	}
	/**
	 * <p>
	 * 描述：加载所属井网的下拉菜单数据信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loadSsjwType(String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		sql = " select t.itemvalue,t.itemname from tbl_code t where  itemcode='SSJW'";
		try {
			List<?> list = this.find(sql);
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

	/**
	 * <p>
	 * 描述：加载组织类型的下拉菜单数据信息
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loadSszcdyType(String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		sql = " select t.itemvalue,t.itemname from tbl_code t where  itemcode='SSZCDY'";
		try {
			List<?> list = this.find(sql);
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

	public List<T> fingWellByJhList() throws Exception {
		String sql = " select  distinct (wellName) from tbl_wellinformation w  order by sortNum ";
		return this.getBaseDao().find(sql);
	}

	@SuppressWarnings("rawtypes")
	public String getWellInformationProList(Map map,Page pager,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		StringBuffer unitDropdownData = new StringBuffer();
		StringBuffer driverDropdownData = new StringBuffer();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		String wellInformationName = (String) map.get("wellInformationName");
		String liftingType = (String) map.get("liftingType");
		String orgId = (String) map.get("orgId");
		String WellInformation_Str = "";
		String liftingType_Str = "";
		if (StringManagerUtils.isNotNull(wellInformationName)) {
			WellInformation_Str = " and t.wellname like '%" + wellInformationName+ "%'";
		}
		if (StringManagerUtils.isNotNull(liftingType)) {
			liftingType_Str = " and t.liftingtype like '%" + liftingType.substring(0, 1)+ "%'";
		}
		String sql = "select t.id,t.orgname,t.resname,t.wellname,t.liftingtype,t.liftingtypename,"
				+ " t.protocolcode,t.acquisitionunit,t.signinid,t.slave,"
				+ " t.videourl,t.sortnum"
				+ " from viw_wellinformation t where 1=1"
				+ WellInformation_Str
				+ liftingType_Str
				+ "  and t.orgid in ("+orgId+" )  "
			    + " order by t.sortnum,t.wellname ";
		String unitSql="select t.unit_name from tbl_acq_unit_conf t order by t.id";
		List<?> unitList = this.findCallSql(unitSql);
		unitDropdownData.append("[");
		for(int i=0;i<unitList.size();i++){
			unitDropdownData.append("'"+unitList.get(i)+"',");
		}
		if(unitDropdownData.toString().endsWith(",")){
			unitDropdownData.deleteCharAt(unitDropdownData.length() - 1);
		}
		unitDropdownData.append("]");
		driverDropdownData.append("[");
		
		Map<Integer,Object> equipmentDriveSortMap=new TreeMap<Integer,Object>();
		for(Entry<String, Object> entry:equipmentDriveMap.entrySet()){
			if( ( entry.getValue() instanceof KafkaConfig ) ){
				KafkaConfig driveConfig=(KafkaConfig)equipmentDriveMap.get("KafkaDrive");
				equipmentDriveSortMap.put(driveConfig.getSort(), driveConfig);
			}else if( ( entry.getValue() instanceof ModbusProtocolConfig ) ){
				ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
				for(int i=0;i<modbusProtocolConfig.getProtocol().size();i++){
					ModbusProtocolConfig.Protocol protocolConfig=(ModbusProtocolConfig.Protocol)modbusProtocolConfig.getProtocol().get(i);
					equipmentDriveSortMap.put(protocolConfig.getSort(), protocolConfig);
				}
			}
		}
		for(Entry<Integer, Object> entry:equipmentDriveSortMap.entrySet()){
			if( ( entry.getValue() instanceof ModbusProtocolConfig.Protocol ) ){
				ModbusProtocolConfig.Protocol protocolConfig=(ModbusProtocolConfig.Protocol)entry.getValue();
				driverDropdownData.append("'"+protocolConfig.getName()+"',");
			}else if( ( entry.getValue() instanceof KafkaConfig ) ){
				KafkaConfig driveConfig=(KafkaConfig)entry.getValue();
				driverDropdownData.append("'"+driveConfig.getProtocolName()+"',");
			}
		}
		if(driverDropdownData.toString().endsWith(",")){
			driverDropdownData.deleteCharAt(driverDropdownData.length() - 1);
		}
		driverDropdownData.append("]");
		
		
		String json = "";
		
		List<?> list = this.findCallSql(sql);
		String columns=service.showTableHeadersColumns("wellInfo");
		result_json.append("{\"success\":true,\"totalCount\":"+list.size()+",\"driverDropdownData\":"+driverDropdownData.toString()+",\"unitDropdownData\":"+unitDropdownData.toString()+",\"columns\":"+columns+",\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String protocolName="";
			String driverCode=obj[6]+"";
			for(Entry<Integer, Object> entry:equipmentDriveSortMap.entrySet()){
				if( ( entry.getValue() instanceof ModbusProtocolConfig.Protocol ) ){
					ModbusProtocolConfig.Protocol protocolConfig=(ModbusProtocolConfig.Protocol)entry.getValue();
					if(driverCode.equals(protocolConfig.getCode())){
						protocolName=protocolConfig.getName();
						break;
					}
				}else if( ( entry.getValue() instanceof KafkaConfig ) ){
					KafkaConfig driveConfig=(KafkaConfig)entry.getValue();
					if(driverCode.equals(driveConfig.getProtocolCode())){
						protocolName=driveConfig.getProtocolName();
						break;
					}
				}
			}
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"orgName\":\""+obj[1]+"\",");
			result_json.append("\"resName\":\""+obj[2]+"\",");
			result_json.append("\"wellName\":\""+obj[3]+"\",");
			result_json.append("\"liftingType\":\""+obj[4]+"\",");
			result_json.append("\"liftingTypeName\":\""+obj[5]+"\",");
			result_json.append("\"protocolCode\":\""+obj[6]+"\",");
			result_json.append("\"protocolName\":\""+protocolName+"\",");
			result_json.append("\"acquisitionUnit\":\""+obj[7]+"\",");
			result_json.append("\"signInId\":\""+obj[8]+"\",");
			result_json.append("\"slave\":\""+obj[9]+"\",");
			result_json.append("\"videoUrl\":\""+obj[10]+"\",");
			result_json.append("\"sortNum\":\""+obj[11]+"\"},");
		}
		for(int i=1;i<=recordCount-list.size();i++){
			result_json.append("{\"jlbh\":\"-99999\",\"id\":\"-99999\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		json=result_json.toString().replaceAll("null", "");
		return json;
	}
	
	public String exportWellInformationData(Map map,Page pager,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		Map<String, Object> equipmentDriveMap = EquipmentDriveMap.getMapObject();
		if(equipmentDriveMap.size()==0){
			EquipmentDriverServerTask.loadProtocolConfig();
			equipmentDriveMap = EquipmentDriveMap.getMapObject();
		}
		String wellInformationName = (String) map.get("wellInformationName");
		String liftingType = (String) map.get("liftingType");
		String orgCode = (String) map.get("orgCode");
		String resCode = (String) map.get("resCode");
		String orgId = (String) map.get("orgId");
		String WellInformation_Str = "";
		String liftingType_Str = "";
		if (StringManagerUtils.isNotNull(wellInformationName)) {
			WellInformation_Str = " and t.wellname like '%" + wellInformationName+ "%'";
		}
		if (StringManagerUtils.isNotNull(liftingType)) {
			liftingType_Str = " and t.liftingtype like '%" + liftingType.substring(0, 1)+ "%'";
		}
		String sql = "select t.id,t.orgname,t.resname,t.wellname,t.liftingtype,t.liftingtypename,"
				+ " t.protocolcode,t.acquisitionunit,t.signinid,t.slave,"
				+ " t.videourl,t.sortnum"
				+ " from viw_wellinformation t where 1=1"
				+ WellInformation_Str
				+ liftingType_Str
				+ "  and t.orgid in ("+orgId+" )  "
			    + " order by t.sortnum,t.wellname ";
		String unitSql="select t.unit_name from tbl_acq_unit_conf t order by t.id";
		List<?> list = this.findCallSql(sql);
		result_json.append("[");
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String protocolName="";
			String driverCode=obj[6]+"";
			for(Entry<String, Object> entry:equipmentDriveMap.entrySet()){
				if(entry.getKey().toUpperCase().contains("KAFKA".toUpperCase())){
					KafkaConfig driveConfig=(KafkaConfig)equipmentDriveMap.get("KafkaDrive");
					if(driverCode.equals(driveConfig.getProtocolCode())){
						protocolName=driveConfig.getProtocolName();
						break;
					}
				}else if(entry.getKey().toUpperCase().contains("modbusProtocolConfig".toUpperCase())){
					ModbusProtocolConfig modbusProtocolConfig=(ModbusProtocolConfig) equipmentDriveMap.get("modbusProtocolConfig");
					for(int j=0;j<modbusProtocolConfig.getProtocol().size();j++){
						ModbusProtocolConfig.Protocol protocolConfig=(ModbusProtocolConfig.Protocol)modbusProtocolConfig.getProtocol().get(j);
						if(driverCode.equals(protocolConfig.getCode())){
							protocolName=protocolConfig.getName();
							break;
						}
					}
				}
			}
			result_json.append("{\"id\":\""+obj[0]+"\",");
			result_json.append("\"orgName\":\""+obj[1]+"\",");
			result_json.append("\"resName\":\""+obj[2]+"\",");
			result_json.append("\"wellName\":\""+obj[3]+"\",");
			result_json.append("\"liftingType\":\""+obj[4]+"\",");
			result_json.append("\"liftingTypeName\":\""+obj[5]+"\",");
			result_json.append("\"protocolCode\":\""+obj[6]+"\",");
			result_json.append("\"protocolName\":\""+protocolName+"\",");
			result_json.append("\"acquisitionUnit\":\""+obj[7]+"\",");
			result_json.append("\"signInId\":\""+obj[8]+"\",");
			result_json.append("\"slave\":\""+obj[9]+"\",");
			result_json.append("\"videoUrl\":\""+obj[10]+"\",");
			result_json.append("\"sortNum\":\""+obj[11]+"\"},");
		}
		if(result_json.toString().endsWith(",")){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]");
		return result_json.toString().replaceAll("null", "");
	}

}
