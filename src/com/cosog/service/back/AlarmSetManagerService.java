package com.cosog.service.back;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.model.DistreteAlarmLimit;
import com.cosog.model.WellInformation;
import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;
import com.cosog.utils.Config;
import com.cosog.utils.ConfigFile;
import com.cosog.utils.Page;
import com.cosog.utils.StringManagerUtils;

import net.sf.json.JSONObject;

/**
 * <p>
 * 报警设置信息Service
 * </p>
 * 
 * @author gao 2014-06-06
 * 
 * @param <T>
 */
@Service("alarmSetManagerService")
public class AlarmSetManagerService<T> extends BaseService<T> {
	@Autowired
	private CommonDataService commonDataService;
	public List<T> querrAlarmSets(Page pager, Class<T> clazz) throws Exception {
		String hql = "SELECT u FROM WorkStatusAlarm u";
		return this.getBaseDao().getListForPage(pager, hql, "WorkStatusAlarm");
	}

	public List<T> loadAlarmSets(Class<T> clazz) {
		return this.getBaseDao().getAllObjects(clazz);
	}

	/**
	 * <p>
	 * 显示报警设置下拉菜单信息方法
	 * </p>
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String loadAlarmSetType(String type) throws Exception {
		StringBuffer result_json = new StringBuffer();
		String sql = "";
		if (type.equalsIgnoreCase("result")) {
			sql = " select distinct t.resultcode,t.resultname from tbl_rpc_worktype t,tbl_rpc_alarmtype_conf g where g.resultcode=t.resultcode";
		} else if (type.equalsIgnoreCase("alarmType")) {
			sql = " select  distinct t.itemvalue,t.itemname from tbl_code t,tbl_rpc_alarmtype_conf g where  t.itemcode='BJLX' and t.itemvalue=g.alarmtype";
		} else if (type.equalsIgnoreCase("alarmLevel")) {
			sql = " select t.itemvalue,t.itemname from tbl_code t where  t.itemcode='BJJB' and t.itemvalue<400 order by t.itemvalue";
		}
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
	
	public String getAlarmSetSingleWellList(String jh,String orgId,Page pager){
		StringBuffer result_json = new StringBuffer();
		String allsql="select t007.jlbh,t007.jh,"
				+ "t112.currentamax,t112.currentamin,t112.voltageamax,t112.voltageamin,t112.activepoweramax,t112.activepoweramin,"
				+ "t112.currentbmax,t112.currentbmin,t112.voltagebmax,t112.voltagebmin,t112.activepowerbmax,t112.activepowerbmin,"
				+ "t112.currentcmax,t112.currentcmin,t112.voltagecmax,t112.voltagecmin,t112.activepowercmax,t112.activepowercmin,"
				+ "t112.currentavgmax,t112.currentavgmin,t112.voltageavgmax,t112.voltageavgmin,t112.activepowersummax,t112.activepowersummin ,t112.jlbh as limitid "
				+ " from tbl_wellinformation t007 "
				+ " left join t_wellorder t019 on t007.jh=t019.jh "
				+ " left join tbl_org org on t007.dwbh=org.org_code "
				+ " left join t_112_distretealarmlimit t112 on t007.jlbh=t112.jbh "
				+ " where org.org_id in ("+orgId+")";
		if(StringManagerUtils.isNotNull(jh)){
			allsql+=" and t007.jh ='"+jh+"'";
		}
		String sql=allsql+" order by t019.pxbh";
		int totals=this.getTotalCountRows(allsql);
		List<?> list = this.findCallSql(sql);
		String columns = "[{ \"header\":\"序号\",\"dataIndex\":\"id\",width:50 ,children:[] },{ \"header\":\"井名\",\"dataIndex\":\"jh\" ,children:[] }]";
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+totals+",");
		result_json.append("\"jh\":\""+jh+"\",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"jh\":\""+obj[1]+"\",");
			result_json.append("\"currentamax\":\""+obj[2]+"\",");
			result_json.append("\"currentamin\":\""+obj[3]+"\",");
			result_json.append("\"voltageamax\":\""+obj[4]+"\",");
			result_json.append("\"voltageamin\":\""+obj[5]+"\",");
			result_json.append("\"activepoweramax\":\""+obj[6]+"\",");
			result_json.append("\"activepoweramin\":\""+obj[7]+"\",");
			
			result_json.append("\"currentbmax\":\""+obj[8]+"\",");
			result_json.append("\"currentbmin\":\""+obj[9]+"\",");
			result_json.append("\"voltagebmax\":\""+obj[10]+"\",");
			result_json.append("\"voltagebmin\":\""+obj[11]+"\",");
			result_json.append("\"activepowerbmax\":\""+obj[12]+"\",");
			result_json.append("\"activepowerbmin\":\""+obj[13]+"\",");
			
			result_json.append("\"currentcmax\":\""+obj[14]+"\",");
			result_json.append("\"currentcmin\":\""+obj[15]+"\",");
			result_json.append("\"voltagecmax\":\""+obj[16]+"\",");
			result_json.append("\"voltagecmin\":\""+obj[17]+"\",");
			result_json.append("\"activepowercmax\":\""+obj[18]+"\",");
			result_json.append("\"activepowercmin\":\""+obj[19]+"\",");
			
			result_json.append("\"currentavgmax\":\""+obj[20]+"\",");
			result_json.append("\"currentavgmin\":\""+obj[21]+"\",");
			result_json.append("\"voltageavgmax\":\""+obj[22]+"\",");
			result_json.append("\"voltageavgmin\":\""+obj[23]+"\",");
			result_json.append("\"activepowersummax\":\""+obj[24]+"\",");
			result_json.append("\"activepowersummin\":\""+obj[25]+"\",");
			result_json.append("\"alarmid\":\""+obj[26]+"\"},");
		}
		if(list.size()>0){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();
	}
	
	public String getBalanceAlarmStatusData(Page pager) {
		StringBuffer result_json = new StringBuffer();
		String sql = "select t.id,t.s_level,t.s_min,t.s_max from tbl_rpc_statistics_conf t where t.s_type='PHD' order by t.s_max";
		String columns = commonDataService.showTableHeadersColumns("balanceStatusAlarmSet");
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+list.size()+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"gkmc\":\""+obj[1]+"\",");
			result_json.append("\"minvalue\":"+obj[2]+",");
			result_json.append("\"maxvalue\":"+obj[3]+"},");
		}
		if(list.size()>0){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		result_json.append("]}");
		return result_json.toString();

	}
	
	public String doStatItemsSetShow(Page pager,String type,int recordCount) {
		StringBuffer result_json = new StringBuffer();
		String statType="CYL";
		ConfigFile configFile=Config.getInstance().configFile;
		if("prodDist".equalsIgnoreCase(type)){
			if(configFile.getOthers().getProductionUnit()==0){
				statType="CYL";
			}else{
				statType="CYLF";
			}
			
		}else if("prodFluc".equalsIgnoreCase(type)){
			statType="CYLBD";
		}
		else if("powerBalance".equalsIgnoreCase(type)){
			statType="GLPHD";
		}
		else if("currentBalance".equalsIgnoreCase(type)){
			statType="PHD";
		}
		else if("timeDist".equalsIgnoreCase(type)){
			statType="SCSL";
		}
		else if("systemEff".equalsIgnoreCase(type)){
			statType="XTXL";
		}
		else if("surfaceEff".equalsIgnoreCase(type)){
			statType="DMXL";
		}
		else if("downholeEff".equalsIgnoreCase(type)){
			statType="JXXL";
		}
		else if("powerDist".equalsIgnoreCase(type)){
			statType="RYDL";
		}
		else if("commDist".equalsIgnoreCase(type)){
			statType="TXSL";
		}
		String sql = "select t.id,t.s_level,t.s_min,t.s_max from tbl_rpc_statistics_conf t where t.s_type='"+statType+"' order by t.s_max";
		String columns = commonDataService.showTableHeadersColumns("statSet");
		List<?> list = this.findCallSql(sql);
		result_json.append("{ \"success\":true,\"columns\":"+columns+",");
		result_json.append("\"totalCount\":"+list.size()+",");
		result_json.append("\"totalRoot\":[");
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			result_json.append("{\"id\":"+obj[0]+",");
			result_json.append("\"statitem\":\""+obj[1]+"\",");
			result_json.append("\"downlimit\":"+obj[2]+",");
			result_json.append("\"uplimit\":"+obj[3]+"},");
		}
		if(list.size()>0){
			result_json.deleteCharAt(result_json.length() - 1);
		}
		for(int i=1;i<=recordCount-list.size();i++){
			result_json.append(",{\"jlbh\":\"-99999\",\"id\":\"-99999\"}");
		}
		result_json.append("]}");
		return result_json.toString();

	}
	
	public void doStatItemsSetSave(String type,String data) throws SQLException {
		String statType="CYL";
		ConfigFile configFile=Config.getInstance().configFile;
		if("prodDist".equalsIgnoreCase(type)){
			if(configFile.getOthers().getProductionUnit()==0){
				statType="CYL";
			}else{
				statType="CYLF";
			}
		}else if("prodFluc".equalsIgnoreCase(type)){
			statType="CYLBD";
		}
		else if("powerBalance".equalsIgnoreCase(type)){
			statType="GLPHD";
		}
		else if("currentBalance".equalsIgnoreCase(type)){
			statType="PHD";
		}
		else if("timeDist".equalsIgnoreCase(type)){
			statType="SCSL";
		}
		else if("systemEff".equalsIgnoreCase(type)){
			statType="XTXL";
		}
		else if("surfaceEff".equalsIgnoreCase(type)){
			statType="DMXL";
		}
		else if("downholeEff".equalsIgnoreCase(type)){
			statType="JXXL";
		}
		else if("powerDist".equalsIgnoreCase(type)){
			statType="RYDL";
		}
		else if("commDist".equalsIgnoreCase(type)){
			statType="TXSL";
		}
		
		this.getBaseDao().doStatItemsSetSave(statType,data);
	}
	
	public void saveOrUpdateorDeleteDiscreteAlarmSet(DistreteAlarmLimit w,
			String ids, String comandType) throws Exception {
		getBaseDao().saveOrUpdateorDeleteDiscreteAlarmSet(w, ids, comandType);
	}

	public void addAlarmSet(T AlarmSet) throws Exception {
		this.getBaseDao().addObject(AlarmSet);
	}

	public void modifyAlarmSet(T AlarmSet) throws Exception {
		this.getBaseDao().updateObject(AlarmSet);
	}

	public void deleteAlarmSet(int id, Class<T> clazz) throws Exception {
		T u = this.getBaseDao().getObject(clazz, id);
		this.getBaseDao().deleteObject(u);
	}

	public T getAlarmSet(Class<T> clazz, int id) {
		return this.getBaseDao().getObject(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	public String getAlarmLevelColor(){
		String backColorSql="select t.itemname from tbl_code t where itemcode='BJYS' order by itemvalue" ;
		String colorSql="select t.itemname from tbl_code t where itemcode='BJQJYS' order by itemvalue" ;
		String opacitySql="select t.itemname from tbl_code t where itemcode='BJYSTMD' order by itemvalue" ;
		String json="";
		StringBuffer strBuf = new StringBuffer();
		List<T> backColorList= (List<T>) this.getSQLObjects(backColorSql);
		List<T> colorList= (List<T>) this.getSQLObjects(colorSql);
		List<T> opacityList= (List<T>) this.getSQLObjects(opacitySql);
		strBuf.append("{\"success\":true,\"BackgroundColor\":{");
		if (null != backColorList && backColorList.size() > 0) {
			strBuf.append("\"Level0\":\""+backColorList.get(0)+"\",");
			strBuf.append("\"Level1\":\""+backColorList.get(1)+"\",");
			strBuf.append("\"Level2\":\""+backColorList.get(2)+"\",");
			strBuf.append("\"Level3\":\""+backColorList.get(3)+"\"");
		}else{
			strBuf.append("\"Level0\":\"\",");
			strBuf.append("\"Level1\":\"\",");
			strBuf.append("\"Level2\":\"\",");
			strBuf.append("\"Level3\":\"\"");
		}
		strBuf.append("},\"Color\":{");
		if (null != colorList && colorList.size() > 0) {
			strBuf.append("\"Level0\":\""+colorList.get(0)+"\",");
			strBuf.append("\"Level1\":\""+colorList.get(1)+"\",");
			strBuf.append("\"Level2\":\""+colorList.get(2)+"\",");
			strBuf.append("\"Level3\":\""+colorList.get(3)+"\"");
		}else{
			strBuf.append("\"Level0\":\"\",");
			strBuf.append("\"Level1\":\"\",");
			strBuf.append("\"Level2\":\"\",");
			strBuf.append("\"Level3\":\"\"");
		}
		strBuf.append("},\"Opacity\":{");
		if (null != opacityList && opacityList.size() > 0) {
			strBuf.append("\"Level0\":"+opacityList.get(0)+",");
			strBuf.append("\"Level1\":"+opacityList.get(1)+",");
			strBuf.append("\"Level2\":"+opacityList.get(2)+",");
			strBuf.append("\"Level3\":"+opacityList.get(3)+"");
		}else{
			strBuf.append("\"Level0\":1,");
			strBuf.append("\"Level1\":1,");
			strBuf.append("\"Level2\":1,");
			strBuf.append("\"Level3\":1");
		}
		strBuf.append("}}");
		json=strBuf.toString();
		return json;
	}
	
	public void setAlarmLevelColor(String alarmLevelBackgroundColor0,String alarmLevelBackgroundColor1,String alarmLevelBackgroundColor2,String alarmLevelBackgroundColor3,
			String alarmLevelColor0,String alarmLevelColor1,String alarmLevelColor2,String alarmLevelColor3,
			String alarmLevelOpacity0,String alarmLevelOpacity1,String alarmLevelOpacity2,String alarmLevelOpacity3){
		try {
			this.getBaseDao().setAlarmLevelColor(alarmLevelBackgroundColor0, alarmLevelBackgroundColor1, alarmLevelBackgroundColor2, alarmLevelBackgroundColor3,
					alarmLevelColor0,alarmLevelColor1,alarmLevelColor2,alarmLevelColor3,
					alarmLevelOpacity0,alarmLevelOpacity1,alarmLevelOpacity2,alarmLevelOpacity3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
