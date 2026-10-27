package com.cosog.controller.alarmQuery;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cosog.controller.base.BaseController;
import com.cosog.controller.realTimeMonitoring.RealTimeMonitoringController;
import com.cosog.model.User;
import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.UserInfo;
import com.cosog.service.alarmQuery.AlarmQueryService;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.Constants;
import com.cosog.utils.Page;
import com.cosog.utils.ParamUtils;
import com.cosog.utils.StringManagerUtils;
import com.cosog.websocket.config.WebSocketByJavax;
import com.google.gson.Gson;

@Controller
@RequestMapping("/alarmQueryController")
@Scope("prototype")
public class AlarmQueryController extends BaseController{

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(RealTimeMonitoringController.class);
	private static final Gson gson = new Gson();
	@Autowired
	private AlarmQueryService<?> alarmQueryService;
	@Autowired
	private CommonDataService service;
	
	private String limit;
	private String msg = "";
	private String deviceType;
	private String operationType;
	private String page;
	private String orgId;
	private String startDate;
	private String endDate;
	private String alarmType;
	private String alarmLevel;
	private String isSendMessage;
	private int totals;
	
	@Bean
	public static WebSocketByJavax infoHandler() {
	    return new WebSocketByJavax();
	}
	
	@RequestMapping("/getAlarmStatData")
	public String getAlarmStatData() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String statType = ParamUtils.getParameter(request, "statType");
		String alarmQueryStatRangeType=ParamUtils.getParameter(request, "alarmQueryStatRangeType");
		
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		this.pager = new Page("pagerForm", request);
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		
		if(!StringManagerUtils.isNotNull(orgId)){
			if (user != null) {
				orgId=user.getUserOrgIds();
			}
		}
//		if(!StringManagerUtils.isNotNull(endDate)){
//			String sql = "select to_char(max(t.alarmtime)+1/(24*60),'yyyy-mm-dd hh24:mi:ss') as alarmtime  "
//					+ " from VIW_ALARMINFO_LATEST t "
//					+ " where t.orgid in ("+orgId+") "
//					+ " and t.devicetype in ("+deviceType+")";
//			List<?> list = this.service.reportDateJssj(sql);
//			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
//				endDate = list.get(0).toString();
//			} else {
//				endDate = StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
//			}
//			if(!StringManagerUtils.isNotNull(startDate)){
//				startDate=endDate.split(" ")[0]+" 00:00:00";
//			}
//		}
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		json = alarmQueryService.getAlarmStatData(orgId,deviceType,statType,alarmQueryStatRangeType,pager,language);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/getAlarmData")
	public String getAlarmData() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		alarmType = ParamUtils.getParameter(request, "alarmType");
		alarmLevel = ParamUtils.getParameter(request, "alarmLevel");
		isSendMessage = ParamUtils.getParameter(request, "isSendMessage");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
		this.pager = new Page("pagerForm", request);
		
		String tableName="viw_alarminfo_hist";
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		
		if(!StringManagerUtils.isNotNull(orgId)){
			if (user != null) {
				orgId=user.getUserOrgIds();
			}
		}
		if(!StringManagerUtils.isNotNull(endDate)){
			String maxIdSql="select max(t2.id) from "+tableName+" t2 where t2.deviceId="+deviceId;
			if(StringManagerUtils.isNotNull(alarmType)){
				if(StringManagerUtils.stringToInteger(alarmType)==2){
					maxIdSql+=" and t2.alarmType in(2,5,7)";
				}else {
					maxIdSql+=" and t2.alarmType="+alarmType;
				}
			}
			String sql = " select to_char(t.alarmtime+1/(24*60),'yyyy-mm-dd hh24:mi:ss') from "+tableName+" t "
					+ " where t.id= ("+maxIdSql+") ";
			List<?> list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			}
			if(!StringManagerUtils.isNotNull(startDate)){
				startDate=endDate.split(" ")[0]+" 00:00:00";
			}
		}
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		json = alarmQueryService.getAlarmData(orgId,deviceType,deviceId,deviceName,dictDeviceType,alarmType,alarmLevel,isSendMessage,pager,language);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportAlarmData")
	public String exportAlarmData() throws Exception {
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String dictDeviceType=ParamUtils.getParameter(request, "dictDeviceType");
		String deviceId = ParamUtils.getParameter(request, "deviceId");
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		alarmType = ParamUtils.getParameter(request, "alarmType");
		alarmLevel = ParamUtils.getParameter(request, "alarmLevel");
		isSendMessage = ParamUtils.getParameter(request, "isSendMessage");
		startDate = ParamUtils.getParameter(request, "startDate");
		endDate = ParamUtils.getParameter(request, "endDate");
		
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		String key = ParamUtils.getParameter(request, "key");
		
		this.pager = new Page("pagerForm", request);
		String tableName="viw_alarminfo_hist";
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		
		session.removeAttribute(key);
		session.setAttribute(key, 0);
		
		if(!StringManagerUtils.isNotNull(orgId)){
			if (user != null) {
				orgId=user.getUserOrgIds();
			}
		}
		if(!StringManagerUtils.isNotNull(endDate)){
			String maxIdSql="select max(t2.id) from "+tableName+" t2 where t2.deviceId="+deviceId;
			if(StringManagerUtils.isNotNull(alarmType)){
				if(StringManagerUtils.stringToInteger(alarmType)==2){
					maxIdSql+=" and t2.alarmType in(2,5,7)";
				}else {
					maxIdSql+=" and t2.alarmType="+alarmType;
				}
			}
			String sql = " select to_char(t.alarmtime+1/(24*60),'yyyy-mm-dd hh24:mi:ss') from "+tableName+" t "
					+ " where t.id= ("+maxIdSql+") ";
			List<?> list = this.service.reportDateJssj(sql);
			if (list.size() > 0 &&list.get(0)!=null&&!list.get(0).toString().equals("null")) {
				endDate = list.get(0).toString();
			} else {
				endDate = StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			}
			if(!StringManagerUtils.isNotNull(startDate)){
				startDate=endDate.split(" ")[0]+" 00:00:00";
			}
		}
		pager.setStart_date(startDate);
		pager.setEnd_date(endDate);
		boolean bool = alarmQueryService.exportAlarmData(user,response,fileName,title, heads, fields,orgId,deviceType,dictDeviceType,deviceId,deviceName,alarmType,alarmLevel,isSendMessage,pager,language);
		session.setAttribute(key, 1);
		return null;
	}
	
	@RequestMapping("/getAlarmOverviewData")
	public String getAlarmOverviewData() throws Exception {
		String json = "";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String deviceName = ParamUtils.getParameter(request, "deviceName");
		alarmType = ParamUtils.getParameter(request, "alarmType");
		alarmLevel = ParamUtils.getParameter(request, "alarmLevel");
		String statType = ParamUtils.getParameter(request, "statType");
		String alarmQueryStatRangeType = ParamUtils.getParameter(request, "alarmQueryStatRangeType");
		isSendMessage = ParamUtils.getParameter(request, "isSendMessage");
		this.pager = new Page("pagerForm", request);
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		
		if(!StringManagerUtils.isNotNull(orgId)){
			if (user != null) {
				orgId=user.getUserOrgIds();
			}
		}
		json = alarmQueryService.getAlarmOverviewData(orgId,deviceType,statType,deviceName,alarmType,alarmLevel,alarmQueryStatRangeType,isSendMessage,pager,language);
		//HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset="
				+ Constants.ENCODING_UTF8);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return null;
	}
	
	@RequestMapping("/exportAlarmOverviewData")
	public String exportAlarmOverviewData() throws Exception {
		String json = "";
		String result="{\"success\":true}";
		orgId = ParamUtils.getParameter(request, "orgId");
		deviceType = ParamUtils.getParameter(request, "deviceType");
		String deviceName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "deviceName"),"utf-8");
		alarmType = ParamUtils.getParameter(request, "alarmType");
		alarmLevel = ParamUtils.getParameter(request, "alarmLevel");
		String statType = ParamUtils.getParameter(request, "statType");
		String alarmQueryStatRangeType = ParamUtils.getParameter(request, "alarmQueryStatRangeType");
		isSendMessage = ParamUtils.getParameter(request, "isSendMessage");
		
		String heads = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "heads"),"utf-8");
		String fields = ParamUtils.getParameter(request, "fields");
		String fileName = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "fileName"),"utf-8");
		String title = java.net.URLDecoder.decode(ParamUtils.getParameter(request, "title"),"utf-8");
		String key = ParamUtils.getParameter(request, "key");
		
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("userLogin");
		String language="";
		if(user!=null){
			language=user.getLanguageName();
		}
		session.removeAttribute(key);
		session.setAttribute(key, 0);
		this.pager = new Page("pagerForm", request);
		if(!StringManagerUtils.isNotNull(orgId)){
			if (user != null) {
				orgId=user.getUserOrgIds();
			}
		}
		boolean bool = alarmQueryService.exportAlarmOverviewData(user,response,fileName,title, heads, fields,orgId,deviceType,statType,deviceName,alarmType,alarmLevel,alarmQueryStatRangeType,isSendMessage,pager,language);
		session.setAttribute(key, 1);
		return null;
	}
	
    /**
     * 批量确认报警
     * 请求：POST /alarmQueryController/batchAck
     * Body：{"alertIds":[1001,1002,1003]}
     * 返回：{"success":true,"message":"","data":{"successIds":[...],"alreadyAckedIds":[],"failedIds":[]}}
     */
	@RequestMapping("/batchAck")
	public String batchAck() throws Exception {
	    String json;
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line;
	        BufferedReader reader = request.getReader();
	        while ((line = reader.readLine()) != null) sb.append(line);
	        String body = sb.toString();
	        log.info("batchAck body=" + body);

	        List<Number> alertIds = new ArrayList<Number>();
	        try {
	            @SuppressWarnings("unchecked")
	            Map<String, Object> req = gson.fromJson(body, Map.class);
	            if (req != null && req.get("alertIds") != null) {
	                alertIds = (List<Number>) req.get("alertIds");
	            }
	        } catch (Exception e) {
	            log.warn("batchAck parse body failed", e);
	        }

	        HttpSession session = request.getSession();
	        User user = (User) session.getAttribute("userLogin");
	        String userNo = user != null ? user.getUserNo() + "" : "";
	        log.info("batchAck userNo=" + userNo + ", size=" + alertIds.size());

	        // 1) 落库 + 内存同步（全部交给 AlarmQueryService）
	        Map<String, Object> ackResult = alarmQueryService.batchAckAlarm(alertIds, userNo);

	        // 2) 广播 alarmAcked
	        List<Integer> deviceIds = (List<Integer>) ackResult.get("deviceIds");
	        List<Long> successIds = (List<Long>) ackResult.get("successIds");
	        if (deviceIds != null && !deviceIds.isEmpty() && successIds != null && !successIds.isEmpty()) {
	            String ackTime = StringManagerUtils.getCurrentTime("yyyy-MM-dd HH:mm:ss");

	            // 手动拼 JSON（和 DataProcessing 里 getWebSocketSendData 风格一致）
	            StringBuffer webSocketSendData = new StringBuffer();
	            webSocketSendData.append("{\"functionCode\":\"alarmAcked\",");
	            webSocketSendData.append("\"alertIds\":[");
	            for (int i = 0; i < successIds.size(); i++) {
	                if (i > 0) webSocketSendData.append(",");
	                webSocketSendData.append(successIds.get(i));
	            }
	            webSocketSendData.append("],");
	            webSocketSendData.append("\"deviceIds\":[");
	            for (int i = 0; i < deviceIds.size(); i++) {
	                if (i > 0) webSocketSendData.append(",");
	                webSocketSendData.append(deviceIds.get(i));
	            }
	            webSocketSendData.append("],");
	            webSocketSendData.append("\"ackUser\":\"" + userNo + "\",");
	            webSocketSendData.append("\"ackTime\":\"" + ackTime + "\"");
	            webSocketSendData.append("}");

	            String wsJson = webSocketSendData.toString();

	            // 完全复刻 DataProcessing 的推送方式
	            for (String websocketClientUserId : WebSocketByJavax.clients.keySet()) {
	                try {
	                    String websocketClientUser = "";
	                    String[] clientInfo = websocketClientUserId.split("_");
	                    if (clientInfo != null && clientInfo.length == 3) {
	                        websocketClientUser = clientInfo[1];
	                    }
	                    UserInfo userInfo = MemoryDataManagerTask.getUserInfoByNo(websocketClientUser);
	                    if (userInfo == null) continue;

	                    // 只要对其中任一 deviceId 有权限就推
	                    boolean hasPermission = false;
	                    for (Integer did : deviceIds) {
	                        DeviceInfo deviceInfo = MemoryDataManagerTask.getDeviceInfo(did + "");
	                        if (deviceInfo == null) continue;
	                        if (StringManagerUtils.existOrNot(userInfo.getOrgChildrenNode(), deviceInfo.getOrgId())
	                                && StringManagerUtils.existOrNot(userInfo.getDeviceTypeChildrenNode(), deviceInfo.getDeviceType())) {
	                            hasPermission = true;
	                            break;
	                        }
	                    }
	                    if (!hasPermission) continue;

	                    infoHandler().sendMessageToUserId(websocketClientUserId, wsJson);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }

	        Map<String, Object> data = new HashMap<String, Object>();
	        data.put("successIds",      ackResult.get("successIds"));
	        data.put("alreadyAckedIds", ackResult.get("alreadyAckedIds"));
	        data.put("failedIds",       ackResult.get("failedIds"));

	        Map<String, Object> resp = new HashMap<String, Object>();
	        resp.put("success", true);
	        resp.put("message", "");
	        resp.put("data", data);
	        json = gson.toJson(resp);
	    } catch (Exception e) {
	        log.error("batchAck error", e);
	        Map<String, Object> resp = new HashMap<String, Object>();
	        resp.put("success", false);
	        resp.put("message", "系统异常");
	        resp.put("data", new HashMap<String, Object>());
	        json = gson.toJson(resp);
	    }

	    response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter pw = response.getWriter();
	    pw.print(json);
	    pw.flush();
	    pw.close();
	    return null;
	}

    /**
     * 待确认报警列表
     * 请求：GET /alarmQueryController/getPendingAlarms?orgId=3
     * 返回：{"success":true,"data":[{...},{...}]}
     */
	@RequestMapping("/getPendingAlarms")
	public String getPendingAlarms() throws Exception {
	    String json;
	    try {
	        String orgIdParam = ParamUtils.getParameter(request, "orgId");
	        String deviceTypeParam = ParamUtils.getParameter(request, "deviceType");
	        String limitParam = ParamUtils.getParameter(request, "limit");
	        int maxRows = StringManagerUtils.isNotNull(limitParam)
	                    ? StringManagerUtils.stringToInteger(limitParam) : 200;

	        HttpSession session = request.getSession();
	        User user = (User) session.getAttribute("userLogin");
	        String userNo = user != null ? user.getUserNo() + "" : "";
	        String orgIds = (user != null && !StringManagerUtils.isNotNull(orgIdParam))
	                      ? user.getUserOrgIds() : orgIdParam;

	        log.info("getPendingAlarms userNo=" + userNo + ", orgIds=" + orgIds);

	        List<Map<String, Object>> data = alarmQueryService.getPendingAlarms(orgIds, deviceTypeParam, maxRows,user!=null?user.getLanguageName():"");

	        Map<String, Object> resp = new HashMap<String, Object>();
	        resp.put("success", true);
	        resp.put("data", data);
	        json = gson.toJson(resp);
	    } catch (Exception e) {
	        log.error("getPendingAlarms error", e);
	        Map<String, Object> resp = new HashMap<String, Object>();
	        resp.put("success", false);
	        resp.put("data", new ArrayList<Map<String, Object>>());
	        json = gson.toJson(resp);
	    }

	    response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
	    response.setHeader("Cache-Control", "no-cache");
	    PrintWriter pw = response.getWriter();
	    pw.print(json);
	    pw.flush();
	    pw.close();
	    return null;
	}

    /**
     * 报警音配置列表（只读，上传/删除留到后续报警等级配置页）
     * 请求：GET /alarmQueryController/getAlarmSoundList
     * 返回：{"success":true,"data":[{"level":100,"name":"一级报警","url":"/ctx/resources/alarm/level1.mp3"}, ...]}
     */
    @RequestMapping("/getAlarmSoundList")
    public String getAlarmSoundList() throws Exception {
        String json;
        try {
            String ctx = request.getContextPath();

            // ============================================================
            // TODO: 真正查询时替换这里
            // SELECT alarm_level, file_name, file_path
            //   FROM t_alarm_sound
            //  WHERE enabled = 1
            // ============================================================
            List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

            Map<String, Object> l1 = new HashMap<String, Object>();
            l1.put("level", 100);
            l1.put("name",  "一级报警");
            l1.put("url",   ctx + "/resources/alarm/level1.mp3");
            data.add(l1);

            Map<String, Object> l2 = new HashMap<String, Object>();
            l2.put("level", 200);
            l2.put("name",  "二级报警");
            l2.put("url",   ctx + "/resources/alarm/level2.mp3");
            data.add(l2);

            Map<String, Object> l3 = new HashMap<String, Object>();
            l3.put("level", 300);
            l3.put("name",  "三级报警");
            l3.put("url",   ctx + "/resources/alarm/level3.mp3");
            data.add(l3);

            Map<String, Object> resp = new HashMap<String, Object>();
            resp.put("success", true);
            resp.put("data", data);
            json = gson.toJson(resp);
        } catch (Exception e) {
            log.error("getAlarmSoundList error", e);
            Map<String, Object> resp = new HashMap<String, Object>();
            resp.put("success", false);
            resp.put("data", new ArrayList<Map<String, Object>>());
            json = gson.toJson(resp);
        }

        response.setContentType("application/json;charset=" + Constants.ENCODING_UTF8);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter pw = response.getWriter();
        pw.print(json);
        pw.flush();
        pw.close();
        return null;
    }
	
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getTotals() {
		return totals;
	}
	public void setTotals(int totals) {
		this.totals = totals;
	}


	public String getOperationType() {
		return operationType;
	}


	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}



	public String getAlarmLevel() {
		return alarmLevel;
	}



	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public String getIsSendMessage() {
		return isSendMessage;
	}

	public void setIsSendMessage(String isSendMessage) {
		this.isSendMessage = isSendMessage;
	}
	
}
