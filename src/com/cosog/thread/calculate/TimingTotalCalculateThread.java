package com.cosog.thread.calculate;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cosog.model.DataMapping;
import com.cosog.model.KeyValue;
import com.cosog.model.ReportTemplate;
import com.cosog.model.ReportUnitItem;
import com.cosog.model.calculate.AcqInstanceOwnItem;
import com.cosog.model.calculate.CommResponseData;
import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.EnergyCalculateResponseData;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.RPCCalculateRequestData;
import com.cosog.model.calculate.TimeEffResponseData;
import com.cosog.model.calculate.TotalAnalysisRequestData;
import com.cosog.model.calculate.TotalAnalysisResponseData;
import com.cosog.model.drive.ModbusProtocolConfig;
import com.cosog.service.base.CommonDataService;
import com.cosog.task.MemoryDataManagerTask;
import com.cosog.utils.CalculateUtils;
import com.cosog.utils.Config;
import com.cosog.utils.OracleJdbcUtis;
import com.cosog.utils.StringManagerUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TimingTotalCalculateThread extends Thread {
    private int threadId;
    private int deviceId;
    private String deviceName;
    private String timeStr;
    private String templateCode;
    private String reportUnitId;
    private int calculateType;
    private CommonDataService commonDataService = null;


    public TimingTotalCalculateThread(int threadId, int deviceId, String deviceName, String timeStr, String templateCode,
        String reportUnitId, int calculateType, CommonDataService commonDataService) {
        super();
        this.threadId = threadId;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.timeStr = timeStr;
        this.templateCode = templateCode;
        this.reportUnitId = reportUnitId;
        this.calculateType = calculateType;
        this.commonDataService = commonDataService;
    }

    @SuppressWarnings({
        "static-access",
        "unused"
    })
    public void run() {
        if (deviceId == 1) {
            System.out.println("");
        }
        long calculateStartTime = System.nanoTime();
        long time1 = 0, time2 = 0;

        int offsetHour = Config.getInstance().configFile.getAp().getReport().getOffsetHour();
        String date = timeStr.split(" ")[0];
        if (!StringManagerUtils.timeMatchDate(timeStr, date, offsetHour)) {
            date = StringManagerUtils.addDay(StringManagerUtils.stringToDate(date), -1);
        }
        CommResponseData.Range range = StringManagerUtils.getTimeRange(date, offsetHour);
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken < TotalAnalysisRequestData > () {}.getType();

        if (calculateType == 1) {
            String fesDiagramSql = "select t.id, to_char(t.fesdiagramacqtime,'yyyy-mm-dd hh24:mi:ss'),t.resultcode," +
                "t.stroke,t.spm,t.fmax,t.fmin,t.fullnesscoefficient," +
                "t.theoreticalproduction,t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction," +
                "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction," +
                "t.productiondata," +
                "t.pumpeff,t.pumpeff1,t.pumpeff2,t.pumpeff3,t.pumpeff4," +
                "t.wattdegreebalance,t.idegreebalance,t.deltaradius," +
                "t.surfacesystemefficiency,t.welldownsystemefficiency,t.systemefficiency,t.energyper100mlift," +
                "t.calcProducingfluidLevel,t.levelDifferenceValue," +
                "t.submergence," +
                "t.rpm " +
                " from tbl_rpcacqdata_hist t " +
                " where t.fesdiagramacqtime between to_date('" + date + "','yyyy-mm-dd')+" + offsetHour + "/24 and to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss') " +
                " and t.resultstatus=1 " +
                " and t.deviceId=" + deviceId +
                " order by t.fesdiagramacqtime";
            String labelInfoSql = "select t.deviceId, t.headerlabelinfo from tbl_rpctimingcalculationdata t " +
                " where t.id=(" +
                " select v2.id from " +
                " ( select v.id,rownum r from " +
                " (select t2.id from tbl_rpctimingcalculationdata t2 " +
                "  where t2.deviceId=" + deviceId + " and t2.headerLabelInfo is not null order by t2.caltime desc) v ) v2" +
                " where r=1)";
            String historyCommStatusSql = "select t.id,t.deviceId,t2.deviceName,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime," +
                "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange" +
                " from tbl_rpcacqdata_hist t,tbl_device t2 " +
                " where t.deviceId=t2.id " +
                " and t.id=(" +
                " select max(t3.id) from  tbl_rpcacqdata_hist t3   " +
                " where t3.acqtime >= to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')-1 " +
                " and t3.acqtime < to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss') " +
                " and t3.deviceId=" + deviceId +
                " )";
            String historyRunStatusSql = "select t.id,t.deviceId,t2.deviceName,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime," +
                " t.runstatus,t.runtimeefficiency,t.runtime,t.runrange" +
                " from tbl_rpcacqdata_hist t,tbl_device t2 " +
                " where t.deviceId=t2.id " +
                " and t.id=(" +
                " select max(t3.id) from  tbl_rpcacqdata_hist t3  " +
                " where t3.commstatus=1 and (t3.runstatus =0 or t3.runstatus =1) " +
                " and t3.acqtime >= to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')-1 " +
                " and t3.acqtime < to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss') " +
                " and t3.deviceId=" + deviceId +
                " )";

            TimeEffResponseData timeEffResponseData = null;
            CommResponseData commResponseData = null;

            String lastRunTime = "";
            String lastCommTime = "";

            int commStatus = 0;
            float commTime = 0;
            float commTimeEfficiency = 0;
            String commRange = "";

            int runStatus = 0;
            float runTime = 0;
            float runTimeEfficiency = 0;
            String runRange = "";


            time1 = System.nanoTime();
            List < ? > labelInfoQueryList = commonDataService.findCallSql(labelInfoSql);
            time2 = System.nanoTime();
            //			StringManagerUtils.printLog("定时汇总计算："+"抽油机井"+deviceName+",timeStr="+timeStr+",threadId="+threadId+",labelInfoSql执行耗时:"+StringManagerUtils.getTimeDiff(time1, time2));

            String labelInfo = "";
            ReportTemplate.Template template = null;

            //继承表头信息
            for (int j = 0; j < labelInfoQueryList.size(); j++) {
                Object[] labelInfoObj = (Object[]) labelInfoQueryList.get(j);
                if (deviceId == StringManagerUtils.stringToInteger(labelInfoObj[0].toString())) {
                    labelInfo = labelInfoObj[1] + "";
                    break;
                }
            }

            String updateSql = "update tbl_rpctimingcalculationdata t set t.headerlabelinfo='" + labelInfo + "'";

            time1 = System.nanoTime();
            try {
                commonDataService.getBaseDao().initDeviceTimingReportDate(deviceId, timeStr, date, calculateType);
            } catch (Exception e) {
                e.printStackTrace();
            }
            time2 = System.nanoTime();
            //			StringManagerUtils.printLog("定时汇总计算："+"抽油机井"+deviceName+",timeStr="+timeStr+",threadId="+threadId+",initDeviceTimingReportDate执行耗时:"+StringManagerUtils.getTimeDiff(time1, time2));


            //报表继承可编辑数据
            if (StringManagerUtils.isNotNull(templateCode)) {
                template = MemoryDataManagerTask.getSingleWellDailyReportTemplateByCode(templateCode);
            }
            if (template != null) {
                if (template.getEditable() != null && template.getEditable().size() > 0) {
                    String reportItemSql = "select t.itemname,t.itemcode,t.sort,t.datatype " +
                        " from TBL_REPORT_ITEMS2UNIT_CONF t " +
                        " where t.unitid=" + reportUnitId +
                        " and t.datasource='计算'" +
                        " and t.sort>=0" +
                        " and t.reporttype=2" +
                        " order by t.sort";
                    List < ReportUnitItem > reportItemList = new ArrayList < ReportUnitItem > ();
                    time1 = System.nanoTime();
                    List < ? > reportItemQuertList = commonDataService.findCallSql(reportItemSql);
                    time2 = System.nanoTime();
                    //					StringManagerUtils.printLog("定时汇总计算："+"抽油机井"+deviceName+",timeStr="+timeStr+",threadId="+threadId+",reportItemSql执行耗时:"+StringManagerUtils.getTimeDiff(time1, time2));

                    for (int k = 0; reportItemQuertList != null && k < reportItemQuertList.size(); k++) {
                        Object[] reportItemObj = (Object[]) reportItemQuertList.get(k);
                        ReportUnitItem reportUnitItem = new ReportUnitItem();
                        reportUnitItem.setItemName(reportItemObj[0] + "");
                        reportUnitItem.setItemCode(reportItemObj[1] + "");
                        reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2] + ""));
                        reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3] + ""));


                        for (int l = 0; l < template.getEditable().size(); l++) {
                            ReportTemplate.Editable editable = template.getEditable().get(l);
                            if (editable.getStartRow() >= template.getHeader().size() && reportUnitItem.getSort() - 1 >= editable.getStartColumn() && reportUnitItem.getSort() - 1 <= editable.getEndColumn()) { //索引起始不同
                                reportItemList.add(reportUnitItem);
                                break;
                            }
                        }
                    }
                    if (reportItemList.size() > 0) {
                        StringBuffer updateColBuff = new StringBuffer();
                        for (int m = 0; m < reportItemList.size(); m++) {
                            updateColBuff.append(reportItemList.get(m).getItemCode() + ",");
                        }
                        if (updateColBuff.toString().endsWith(",")) {
                            updateColBuff.deleteCharAt(updateColBuff.length() - 1);
                        }

                        String updateEditDataSql = "update tbl_rpctimingcalculationdata t set (" + updateColBuff + ")=" +
                            " (select " + updateColBuff + " from tbl_rpctimingcalculationdata t2 " +
                            " where t2.deviceId= " + deviceId +
                            " and t2.id=" +
                            " (select v2.id from" +
                            " (select v.id,rownum r from " +
                            " (select t3.id from tbl_rpctimingcalculationdata t3 " +
                            " where t3.deviceId=" + deviceId + " and t3.caltime<to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss') " +
                            " order by t3.caltime desc) v " +
                            " ) v2" +
                            " where r=1)" +
                            ") " +
                            " where t.deviceId=" + deviceId +
                            " and t.caltime=to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss') ";
                        try {
                            time1 = System.nanoTime();
                            int r = commonDataService.getBaseDao().updateOrDeleteBySql(updateEditDataSql);
                            time2 = System.nanoTime();
                            //							StringManagerUtils.printLog("定时汇总计算："+"抽油机井"+deviceName+",timeStr="+timeStr+",threadId="+threadId+",updateEditDataSql执行耗时:"+StringManagerUtils.getTimeDiff(time1, time2));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            time1 = System.nanoTime();
            List < ? > historyCommStatusQueryList = commonDataService.findCallSql(historyCommStatusSql);
            time2 = System.nanoTime();
            //			StringManagerUtils.printLog("定时汇总计算："+"抽油机井"+deviceName+",timeStr="+timeStr+",threadId="+threadId+",historyCommStatusSql执行耗时:"+StringManagerUtils.getTimeDiff(time1, time2));
            if (historyCommStatusQueryList.size() > 0) {
                Object[] historyCommStatusObj = (Object[]) historyCommStatusQueryList.get(0);
                lastCommTime = historyCommStatusObj[3] + "";
                commStatus = StringManagerUtils.stringToInteger(historyCommStatusObj[4] + "");
                commTimeEfficiency = StringManagerUtils.stringToFloat(historyCommStatusObj[5] + "");
                commTime = StringManagerUtils.stringToFloat(historyCommStatusObj[6] + "");
                commRange = StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(historyCommStatusObj[7]));
            }
            String commTotalRequestData = "{" +
                "\"AKString\":\"\"," +
                "\"WellName\":\"" + deviceName + "\"," +
                "\"OffsetHour\":" + offsetHour + "," +
                "\"Last\":{" +
                "\"AcqTime\": \"" + lastCommTime + "\"," +
                "\"CommStatus\": " + (commStatus >= 1) + "," +
                "\"CommEfficiency\": {" +
                "\"Efficiency\": " + commTimeEfficiency + "," +
                "\"Time\": " + commTime + "," +
                "\"Range\": " + commRange + "" +
                "}" +
                "}," +
                "\"Current\": {" +
                "\"AcqTime\":\"" + timeStr + "\"," +
                "\"CommStatus\":" + (commStatus >= 1) + "" +
                "}" +
                "}";
            time1 = System.nanoTime();
            commResponseData = CalculateUtils.commCalculate(commTotalRequestData);
            time2 = System.nanoTime();
            //			StringManagerUtils.printLog("定时汇总计算："+"抽油机井"+deviceName+",timeStr="+timeStr+",threadId="+threadId+",commCalculate执行耗时:"+StringManagerUtils.getTimeDiff(time1, time2));

            updateSql += ",CommStatus=" + commStatus;
            if (commResponseData != null && commResponseData.getResultStatus() == 1) {
                if (timeStr.equalsIgnoreCase(range.getEndTime()) && commResponseData.getDaily() != null && StringManagerUtils.isNotNull(commResponseData.getDaily().getDate())) {
                    commTime = commResponseData.getDaily().getCommEfficiency().getTime();
                    commTimeEfficiency = commResponseData.getDaily().getCommEfficiency().getEfficiency();
                    commRange = commResponseData.getDaily().getCommEfficiency().getRangeString();
                } else {
                    commTime = commResponseData.getCurrent().getCommEfficiency().getTime();
                    commTimeEfficiency = commResponseData.getCurrent().getCommEfficiency().getEfficiency();
                    commRange = commResponseData.getCurrent().getCommEfficiency().getRangeString();
                }
                updateSql += ",commTimeEfficiency=" + commTimeEfficiency + ",commTime=" + commTime;
            }

            time1 = System.nanoTime();
            List < ? > historyRunStatusQueryList = commonDataService.findCallSql(historyRunStatusSql);
            time2 = System.nanoTime();
            //			StringManagerUtils.printLog("定时汇总计算："+"抽油机井"+deviceName+",timeStr="+timeStr+",threadId="+threadId+",historyRunStatusQueryList执行耗时:"+StringManagerUtils.getTimeDiff(time1, time2));
            if (historyRunStatusQueryList.size() > 0) {
                Object[] historyRunStatuObj = (Object[]) historyRunStatusQueryList.get(0);
                lastRunTime = historyRunStatuObj[3] + "";
                runStatus = StringManagerUtils.stringToInteger(historyRunStatuObj[4] + "");
                runTimeEfficiency = StringManagerUtils.stringToFloat(historyRunStatuObj[5] + "");
                runTime = StringManagerUtils.stringToFloat(historyRunStatuObj[6] + "");
                runRange = StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(historyRunStatuObj[7]));
            }
            String runTotalRequestData = "{" +
                "\"AKString\":\"\"," +
                "\"WellName\":\"" + deviceName + "\"," +
                "\"OffsetHour\":" + offsetHour + "," +
                "\"Last\":{" +
                "\"AcqTime\": \"" + lastRunTime + "\"," +
                "\"RunStatus\": " + (runStatus >= 1) + "," +
                "\"RunEfficiency\": {" +
                "\"Efficiency\": " + runTimeEfficiency + "," +
                "\"Time\": " + runTime + "," +
                "\"Range\": " + runRange + "" +
                "}" +
                "}," +
                "\"Current\": {" +
                "\"AcqTime\":\"" + timeStr + "\"," +
                "\"RunStatus\":" + (runStatus >= 1) + "" +
                "}" +
                "}";
            time1 = System.nanoTime();
            timeEffResponseData = CalculateUtils.runCalculate(runTotalRequestData);
            time2 = System.nanoTime();
            //			StringManagerUtils.printLog("定时汇总计算："+"抽油机井"+deviceName+",timeStr="+timeStr+",threadId="+threadId+",runCalculate执行耗时:"+StringManagerUtils.getTimeDiff(time1, time2));

            updateSql += ",runStatus=" + runStatus;
            if (timeEffResponseData != null && timeEffResponseData.getResultStatus() == 1) {
                if (timeStr.equalsIgnoreCase(range.getEndTime()) && timeEffResponseData.getDaily() != null && StringManagerUtils.isNotNull(timeEffResponseData.getDaily().getDate())) {
                    runTime = timeEffResponseData.getDaily().getRunEfficiency().getTime();
                    runTimeEfficiency = timeEffResponseData.getDaily().getRunEfficiency().getEfficiency();
                    runRange = timeEffResponseData.getDaily().getRunEfficiency().getRangeString();
                } else {
                    runTime = timeEffResponseData.getCurrent().getRunEfficiency().getTime();
                    runTimeEfficiency = timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency();
                    runRange = timeEffResponseData.getCurrent().getRunEfficiency().getRangeString();
                }
                updateSql += ",runTimeEfficiency=" + runTimeEfficiency + ",runTime=" + runTime;
            }

            List < String > acqTimeList = new ArrayList < String > ();
            List < Integer > commStatusList = new ArrayList < Integer > ();
            List < Integer > runStatusList = new ArrayList < Integer > ();

            List < Float > rpmList = new ArrayList < Float > ();

            List < Integer > ResultCodeList = new ArrayList < Integer > ();
            List < Float > strokeList = new ArrayList < Float > ();
            List < Float > spmList = new ArrayList < Float > ();

            List < Float > FMaxList = new ArrayList < Float > ();
            List < Float > FMinList = new ArrayList < Float > ();

            List < Float > fullnessCoefficientList = new ArrayList < Float > ();

            List < Float > theoreticalProductionList = new ArrayList < Float > ();
            List < Float > liquidVolumetricProductionList = new ArrayList < Float > ();
            List < Float > oilVolumetricProductionList = new ArrayList < Float > ();
            List < Float > waterVolumetricProductionList = new ArrayList < Float > ();
            List < Float > volumeWaterCutList = new ArrayList < Float > ();

            List < Float > liquidWeightProductionList = new ArrayList < Float > ();
            List < Float > oilWeightProductionList = new ArrayList < Float > ();
            List < Float > waterWeightProductionList = new ArrayList < Float > ();
            List < Float > weightWaterCutList = new ArrayList < Float > ();

            List < Float > pumpEffList = new ArrayList < Float > ();
            List < Float > pumpEff1List = new ArrayList < Float > ();
            List < Float > pumpEff2List = new ArrayList < Float > ();
            List < Float > pumpEff3List = new ArrayList < Float > ();
            List < Float > pumpEff4List = new ArrayList < Float > ();


            List < Float > wattDegreeBalanceList = new ArrayList < Float > ();
            List < Float > iDegreeBalanceList = new ArrayList < Float > ();
            List < Float > deltaRadiusList = new ArrayList < Float > ();

            List < Float > surfaceSystemEfficiencyList = new ArrayList < Float > ();
            List < Float > wellDownSystemEfficiencyList = new ArrayList < Float > ();
            List < Float > systemEfficiencyList = new ArrayList < Float > ();
            List < Float > energyPer100mLiftList = new ArrayList < Float > ();

            List < Float > pumpSettingDepthList = new ArrayList < Float > ();
            List < Float > producingfluidLevelList = new ArrayList < Float > ();
            List < Float > calcProducingfluidLevelList = new ArrayList < Float > ();
            List < Float > levelDifferenceValueList = new ArrayList < Float > ();
            List < Float > submergenceList = new ArrayList < Float > ();

            List < Float > tubingPressureList = new ArrayList < Float > ();
            List < Float > casingPressureList = new ArrayList < Float > ();

            time1 = System.nanoTime();
            List < ? > singleresultlist = commonDataService.findCallSql(fesDiagramSql);
            time2 = System.nanoTime();
            //			StringManagerUtils.printLog("定时汇总计算："+"抽油机井"+deviceName+",timeStr="+timeStr+",threadId="+threadId+",fesDiagramSql执行耗时:"+StringManagerUtils.getTimeDiff(time1, time2));
            for (int j = 0; j < singleresultlist.size(); j++) {
                Object[] resuleObj = (Object[]) singleresultlist.get(j);
                String productionData = resuleObj[15].toString();
                type = new TypeToken < RPCCalculateRequestData > () {}.getType();
                RPCCalculateRequestData rpcProductionData = gson.fromJson(productionData, type);

                acqTimeList.add(resuleObj[1] + "");
                commStatusList.add(commStatus >= 1 ? 1 : 0);
                runStatusList.add(runStatus >= 1 ? 1 : 0);

                ResultCodeList.add(StringManagerUtils.stringToInteger(resuleObj[2] + ""));
                strokeList.add(StringManagerUtils.stringToFloat(resuleObj[3] + ""));
                spmList.add(StringManagerUtils.stringToFloat(resuleObj[4] + ""));
                FMaxList.add(StringManagerUtils.stringToFloat(resuleObj[5] + ""));
                FMinList.add(StringManagerUtils.stringToFloat(resuleObj[6] + ""));
                fullnessCoefficientList.add(StringManagerUtils.stringToFloat(resuleObj[7] + ""));

                theoreticalProductionList.add(StringManagerUtils.stringToFloat(resuleObj[8] + ""));
                liquidVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[9] + ""));
                oilVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[10] + ""));
                waterVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[11] + ""));

                if (rpcProductionData != null && rpcProductionData.getProduction() != null) {
                    volumeWaterCutList.add(rpcProductionData.getProduction().getWaterCut());
                } else {
                    volumeWaterCutList.add(0.0f);
                }

                liquidWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[12] + ""));
                oilWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[13] + ""));
                waterWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[14] + ""));
                if (rpcProductionData != null && rpcProductionData.getProduction() != null) {
                    weightWaterCutList.add(rpcProductionData.getProduction().getWeightWaterCut());
                } else {
                    weightWaterCutList.add(0.0f);
                }

                if (rpcProductionData != null && rpcProductionData.getProduction() != null) {
                    tubingPressureList.add(rpcProductionData.getProduction().getTubingPressure());
                    casingPressureList.add(rpcProductionData.getProduction().getCasingPressure());
                    pumpSettingDepthList.add(rpcProductionData.getProduction().getPumpSettingDepth());
                    producingfluidLevelList.add(rpcProductionData.getProduction().getProducingfluidLevel());
                } else {
                    tubingPressureList.add(0.0f);
                    casingPressureList.add(0.0f);
                    pumpSettingDepthList.add(0.0f);
                    producingfluidLevelList.add(0.0f);
                }


                pumpEffList.add(StringManagerUtils.stringToFloat(resuleObj[16] + ""));
                pumpEff1List.add(StringManagerUtils.stringToFloat(resuleObj[17] + ""));
                pumpEff2List.add(StringManagerUtils.stringToFloat(resuleObj[18] + ""));
                pumpEff3List.add(StringManagerUtils.stringToFloat(resuleObj[19] + ""));
                pumpEff4List.add(StringManagerUtils.stringToFloat(resuleObj[20] + ""));

                wattDegreeBalanceList.add(StringManagerUtils.stringToFloat(resuleObj[21] + ""));
                iDegreeBalanceList.add(StringManagerUtils.stringToFloat(resuleObj[22] + ""));
                deltaRadiusList.add(StringManagerUtils.stringToFloat(resuleObj[23] + ""));

                surfaceSystemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[24] + ""));
                wellDownSystemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[25] + ""));
                systemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[26] + ""));
                energyPer100mLiftList.add(StringManagerUtils.stringToFloat(resuleObj[27] + ""));

                calcProducingfluidLevelList.add(StringManagerUtils.stringToFloat(resuleObj[28] + ""));
                levelDifferenceValueList.add(StringManagerUtils.stringToFloat(resuleObj[29] + ""));
                submergenceList.add(StringManagerUtils.stringToFloat(resuleObj[30] + ""));

                rpmList.add(StringManagerUtils.stringToFloat(resuleObj[31] + ""));
            }

            StringBuffer dataSbf = new StringBuffer();
            dataSbf.append("{\"AKString\":\"\",");
            dataSbf.append("\"WellName\":\"" + deviceId + "\",");
            dataSbf.append("\"CurrentCommStatus\":" + (commStatus >= 1 ? 1 : 0) + ",");
            dataSbf.append("\"CurrentRunStatus\":" + (runStatus >= 1 ? 1 : 0) + ",");
            dataSbf.append("\"Date\":\"" + date + "\",");
            dataSbf.append("\"OffsetHour\":" + offsetHour + ",");
            dataSbf.append("\"AcqTime\":[" + StringManagerUtils.joinStringArr(acqTimeList, ",") + "],");
            dataSbf.append("\"CommStatus\":[" + StringUtils.join(commStatusList, ",") + "],");
            dataSbf.append("\"CommTime\":" + commTime + ",");
            dataSbf.append("\"CommTimeEfficiency\":" + commTimeEfficiency + ",");
            dataSbf.append("\"CommRange\":\"" + commRange + "\",");
            dataSbf.append("\"RunStatus\":[" + StringUtils.join(runStatusList, ",") + "],");
            dataSbf.append("\"RunTime\":" + runTime + ",");
            dataSbf.append("\"RunTimeEfficiency\":" + runTimeEfficiency + ",");
            dataSbf.append("\"RunRange\":\"" + runRange + "\",");
            dataSbf.append("\"ResultCode\":[" + StringUtils.join(ResultCodeList, ",") + "],");
            dataSbf.append("\"TheoreticalProduction\":[" + StringUtils.join(theoreticalProductionList, ",") + "],");
            dataSbf.append("\"LiquidVolumetricProduction\":[" + StringUtils.join(liquidVolumetricProductionList, ",") + "],");
            dataSbf.append("\"OilVolumetricProduction\":[" + StringUtils.join(oilVolumetricProductionList, ",") + "],");
            dataSbf.append("\"WaterVolumetricProduction\":[" + StringUtils.join(waterVolumetricProductionList, ",") + "],");
            dataSbf.append("\"VolumeWaterCut\":[" + StringUtils.join(volumeWaterCutList, ",") + "],");
            dataSbf.append("\"LiquidWeightProduction\":[" + StringUtils.join(liquidWeightProductionList, ",") + "],");
            dataSbf.append("\"OilWeightProduction\":[" + StringUtils.join(oilWeightProductionList, ",") + "],");
            dataSbf.append("\"WaterWeightProduction\":[" + StringUtils.join(waterWeightProductionList, ",") + "],");
            dataSbf.append("\"WeightWaterCut\":[" + StringUtils.join(weightWaterCutList, ",") + "],");
            dataSbf.append("\"SurfaceSystemEfficiency\":[" + StringUtils.join(surfaceSystemEfficiencyList, ",") + "],");
            dataSbf.append("\"WellDownSystemEfficiency\":[" + StringUtils.join(wellDownSystemEfficiencyList, ",") + "],");
            dataSbf.append("\"SystemEfficiency\":[" + StringUtils.join(systemEfficiencyList, ",") + "],");
            dataSbf.append("\"EnergyPer100mLift\":[" + StringUtils.join(energyPer100mLiftList, ",") + "],");
            dataSbf.append("\"Stroke\":[" + StringUtils.join(strokeList, ",") + "],");
            dataSbf.append("\"SPM\":[" + StringUtils.join(spmList, ",") + "],");
            dataSbf.append("\"FMax\":[" + StringUtils.join(FMaxList, ",") + "],");
            dataSbf.append("\"FMin\":[" + StringUtils.join(FMinList, ",") + "],");
            dataSbf.append("\"FullnessCoefficient\":[" + StringUtils.join(fullnessCoefficientList, ",") + "],");
            dataSbf.append("\"PumpEff\":[" + StringUtils.join(pumpEffList, ",") + "],");
            dataSbf.append("\"PumpEff1\":[" + StringUtils.join(pumpEff1List, ",") + "],");
            dataSbf.append("\"PumpEff2\":[" + StringUtils.join(pumpEff2List, ",") + "],");
            dataSbf.append("\"PumpEff3\":[" + StringUtils.join(pumpEff3List, ",") + "],");
            dataSbf.append("\"PumpEff4\":[" + StringUtils.join(pumpEff4List, ",") + "],");
            dataSbf.append("\"WattDegreeBalance\":[" + StringUtils.join(wattDegreeBalanceList, ",") + "],");
            dataSbf.append("\"IDegreeBalance\":[" + StringUtils.join(iDegreeBalanceList, ",") + "],");
            dataSbf.append("\"DeltaRadius\":[" + StringUtils.join(deltaRadiusList, ",") + "],");
            dataSbf.append("\"PumpSettingDepth\":[" + StringUtils.join(pumpSettingDepthList, ",") + "],");
            dataSbf.append("\"ProducingfluidLevel\":[" + StringUtils.join(producingfluidLevelList, ",") + "],");
            dataSbf.append("\"CalcProducingfluidLevel\":[" + StringUtils.join(calcProducingfluidLevelList, ",") + "],");
            dataSbf.append("\"LevelDifferenceValue\":[" + StringUtils.join(levelDifferenceValueList, ",") + "],");
            dataSbf.append("\"Submergence\":[" + StringUtils.join(submergenceList, ",") + "],");
            dataSbf.append("\"TubingPressure\":[" + StringUtils.join(tubingPressureList, ",") + "],");
            dataSbf.append("\"CasingPressure\":[" + StringUtils.join(casingPressureList, ",") + "],");
            dataSbf.append("\"RPM\":[" + StringUtils.join(rpmList, ",") + "]");
            dataSbf.append("}");

            TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(dataSbf.toString(), new TypeToken < TotalAnalysisRequestData > () {}.getType());
            time1 = System.nanoTime();
            TotalAnalysisResponseData totalAnalysisResponseData = CalculateUtils.totalCalculate(dataSbf.toString());
            time2 = System.nanoTime();
            //			StringManagerUtils.printLog("定时汇总计算："+"抽油机井"+deviceName+",timeStr="+timeStr+",threadId="+threadId+",totalCalculate执行耗时:"+StringManagerUtils.getTimeDiff(time1, time2));

            updateSql += " where t.deviceId=" + deviceId + " and t.caltime=to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')";
            try {
                time1 = System.nanoTime();
                int r = commonDataService.getBaseDao().updateOrDeleteBySql(updateSql);
                time2 = System.nanoTime();
                //				StringManagerUtils.printLog("定时汇总计算："+"抽油机井"+deviceName+",timeStr="+timeStr+",threadId="+threadId+",updateSql执行耗时:"+StringManagerUtils.getTimeDiff(time1, time2));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (commResponseData != null && commResponseData.getResultStatus() == 1) {
                List < String > clobCont = new ArrayList < String > ();
                String updateHisRangeClobSql = "update tbl_rpctimingcalculationdata t set t.commrange=?";
                clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
                if (timeEffResponseData != null && timeEffResponseData.getResultStatus() == 1) {
                    updateHisRangeClobSql += ", t.runrange=?";
                    clobCont.add(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString());
                }
                updateHisRangeClobSql += " where t.deviceId=" + deviceId + " and t.caltime=" + "to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')";
                try {
                    time1 = System.nanoTime();
                    int r = commonDataService.getBaseDao().executeSqlUpdateClob(updateHisRangeClobSql, clobCont);
                    time2 = System.nanoTime();
                    //					StringManagerUtils.printLog("定时汇总计算："+"抽油机井"+deviceName+",timeStr="+timeStr+",threadId="+threadId+",updateHisRangeClobSql执行耗时:"+StringManagerUtils.getTimeDiff(time1, time2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (totalAnalysisResponseData != null && totalAnalysisResponseData.getResultStatus() == 1) {
                int recordCount = totalAnalysisRequestData.getAcqTime() != null ? totalAnalysisRequestData.getAcqTime().size() : 0;
                try {
                    time1 = System.nanoTime();
                    commonDataService.getBaseDao().saveFSDiagramTimingTotalCalculationData(totalAnalysisResponseData, totalAnalysisRequestData, timeStr, recordCount);
                    time2 = System.nanoTime();
                    //					StringManagerUtils.printLog("定时汇总计算："+"抽油机井"+deviceName+",timeStr="+timeStr+",threadId="+threadId+",saveFSDiagramTimingTotalCalculationData执行耗时:"+StringManagerUtils.getTimeDiff(time1, time2));
                } catch (SQLException | ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else if (calculateType == 2) {
            String rpmSql = "select t.id, " +
                "to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss'),t.rpm," +
                "t.theoreticalproduction,t.liquidvolumetricproduction,t.oilvolumetricproduction,t.watervolumetricproduction," +
                "t.liquidweightproduction,t.oilweightproduction,t.waterweightproduction," +
                "t.productiondata," +
                "t.pumpeff,t.pumpeff1,t.pumpeff2," +
                "t.systemefficiency,t.energyper100mlift," +
                "t.submergence " +
                " from tbl_pcpacqdata_hist t " +
                " where t.acqtime between to_date('" + date + "','yyyy-mm-dd') +" + offsetHour + "/24 and to_date('" + date + "','yyyy-mm-dd')+" + offsetHour + "/24+1 " +
                " and t.resultstatus=1 " +
                " and t.deviceId=" + deviceId +
                " order by t.acqtime";
            String labelInfoSql = "select t.deviceId, t.headerlabelinfo from tbl_pcptimingcalculationdata t " +
                " where t.id=(" +
                " select v2.id from " +
                " ( select v.id,rownum r from " +
                " (select t2.id from tbl_pcptimingcalculationdata t2 " +
                "  where t2.deviceId=" + deviceId + " and t2.headerLabelInfo is not null order by t2.caltime desc) v ) v2" +
                " where r=1)";
            String historyCommStatusSql = "select t.id,t.deviceId,t2.deviceName,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime," +
                "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange" +
                " from tbl_pcpacqdata_hist t,tbl_device t2 " +
                " where t.deviceId=t2.id " +
                " and t.id=(" +
                " select max(t3.id) from  tbl_pcpacqdata_hist t3  " +
                " where t3.acqtime between to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')-1 and to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')" +
                " and t3.deviceId=" + deviceId +
                " )";

            String historyRunStatusSql = "select t.id,t.deviceId,t2.deviceName,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime," +
                " t.runstatus,t.runtimeefficiency,t.runtime,t.runrange" +
                " from tbl_pcpacqdata_hist t,tbl_device t2 " +
                " where t.deviceId=t2.id " +
                " and t.id=(" +
                " select max(t3.id) from  tbl_pcpacqdata_hist t3  " +
                " where t3.commstatus=1 and t3.runstatus in (0,1) " +
                " and t3.acqtime between to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')-1 and to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss') " +
                " and t3.deviceId=" + deviceId +
                " )";

            TimeEffResponseData timeEffResponseData = null;
            CommResponseData commResponseData = null;

            String lastRunTime = "";
            String lastCommTime = "";

            int commStatus = 0;
            float commTime = 0;
            float commTimeEfficiency = 0;
            String commRange = "";

            int runStatus = 0;
            float runTime = 0;
            float runTimeEfficiency = 0;
            String runRange = "";

            List < ? > labelInfoQueryList = commonDataService.findCallSql(labelInfoSql);
            String labelInfo = "";
            ReportTemplate.Template template = null;

            //继承表头信息
            for (int j = 0; j < labelInfoQueryList.size(); j++) {
                Object[] labelInfoObj = (Object[]) labelInfoQueryList.get(j);
                if (StringManagerUtils.stringToInteger(labelInfoObj[0].toString()) == deviceId) {
                    labelInfo = labelInfoObj[1] + "";
                    break;
                }
            }

            String updateSql = "update tbl_pcptimingcalculationdata t set t.headerlabelinfo='" + labelInfo + "'";

            try {
                commonDataService.getBaseDao().initDeviceTimingReportDate(deviceId, timeStr, date, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //报表继承可编辑数据
            if (StringManagerUtils.isNotNull(templateCode)) {
                template = MemoryDataManagerTask.getSingleWellDailyReportTemplateByCode(templateCode);
            }
            if (template != null) {
                if (template.getEditable() != null && template.getEditable().size() > 0) {
                    String reportItemSql = "select t.itemname,t.itemcode,t.sort,t.datatype " +
                        " from TBL_REPORT_ITEMS2UNIT_CONF t " +
                        " where t.unitid=" + reportUnitId +
                        " and t.datasource='计算'" +
                        " and t.sort>=0" +
                        " and t.reporttype=2" +
                        " order by t.sort";
                    List < ReportUnitItem > reportItemList = new ArrayList < ReportUnitItem > ();
                    List < ? > reportItemQuertList = commonDataService.findCallSql(reportItemSql);

                    for (int k = 0; k < reportItemQuertList.size(); k++) {
                        Object[] reportItemObj = (Object[]) reportItemQuertList.get(k);
                        ReportUnitItem reportUnitItem = new ReportUnitItem();
                        reportUnitItem.setItemName(reportItemObj[0] + "");
                        reportUnitItem.setItemCode(reportItemObj[1] + "");
                        reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2] + ""));
                        reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3] + ""));


                        for (int l = 0; l < template.getEditable().size(); l++) {
                            ReportTemplate.Editable editable = template.getEditable().get(l);
                            if (editable.getStartRow() >= template.getHeader().size() && reportUnitItem.getSort() - 1 >= editable.getStartColumn() && reportUnitItem.getSort() - 1 <= editable.getEndColumn()) { //索引起始不同
                                reportItemList.add(reportUnitItem);
                                break;
                            }
                        }
                    }
                    if (reportItemList.size() > 0) {
                        StringBuffer updateColBuff = new StringBuffer();
                        for (int m = 0; m < reportItemList.size(); m++) {
                            updateColBuff.append(reportItemList.get(m).getItemCode() + ",");
                        }
                        if (updateColBuff.toString().endsWith(",")) {
                            updateColBuff.deleteCharAt(updateColBuff.length() - 1);
                        }

                        String updateEditDataSql = "update tbl_pcptimingcalculationdata t set (" + updateColBuff + ")=" +
                            " (select " + updateColBuff + " from tbl_pcptimingcalculationdata t2 " +
                            " where t2.deviceId= " + deviceId +
                            " and t2.id=" +
                            " (select v2.id from" +
                            " (select v.id,rownum r from " +
                            " (select t3.id from tbl_pcptimingcalculationdata t3 " +
                            " where t3.deviceId=" + deviceId + " and t3.caltime<to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss') " +
                            " order by t3.caltime desc) v " +
                            " ) v2" +
                            " where r=1)" +
                            ") " +
                            " where t.deviceId=" + deviceId +
                            " and t.caltime=to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss') ";
                        try {
                            int r = commonDataService.getBaseDao().updateOrDeleteBySql(updateEditDataSql);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            List < ? > historyCommStatusQueryList = commonDataService.findCallSql(historyCommStatusSql);
            if (historyCommStatusQueryList.size() > 0) {
                Object[] historyCommStatusObj = (Object[]) historyCommStatusQueryList.get(0);
                lastCommTime = historyCommStatusObj[3] + "";
                commStatus = StringManagerUtils.stringToInteger(historyCommStatusObj[4] + "");
                commTimeEfficiency = StringManagerUtils.stringToFloat(historyCommStatusObj[5] + "");
                commTime = StringManagerUtils.stringToFloat(historyCommStatusObj[6] + "");
                commRange = StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(historyCommStatusObj[7]));
            }
            String commTotalRequestData = "{" +
                "\"AKString\":\"\"," +
                "\"WellName\":\"" + deviceName + "\"," +
                "\"OffsetHour\":" + offsetHour + "," +
                "\"Last\":{" +
                "\"AcqTime\": \"" + lastCommTime + "\"," +
                "\"CommStatus\": " + (commStatus >= 1) + "," +
                "\"CommEfficiency\": {" +
                "\"Efficiency\": " + commTimeEfficiency + "," +
                "\"Time\": " + commTime + "," +
                "\"Range\": " + commRange + "" +
                "}" +
                "}," +
                "\"Current\": {" +
                "\"AcqTime\":\"" + timeStr + "\"," +
                "\"CommStatus\":" + (commStatus >= 1) + "" +
                "}" +
                "}";
            commResponseData = CalculateUtils.commCalculate(commTotalRequestData);

            updateSql += ",CommStatus=" + commStatus;
            if (commResponseData != null && commResponseData.getResultStatus() == 1) {
                if (timeStr.equalsIgnoreCase(range.getEndTime()) && commResponseData.getDaily() != null && StringManagerUtils.isNotNull(commResponseData.getDaily().getDate())) {
                    commTime = commResponseData.getDaily().getCommEfficiency().getTime();
                    commTimeEfficiency = commResponseData.getDaily().getCommEfficiency().getEfficiency();
                    commRange = commResponseData.getDaily().getCommEfficiency().getRangeString();
                } else {
                    commTime = commResponseData.getCurrent().getCommEfficiency().getTime();
                    commTimeEfficiency = commResponseData.getCurrent().getCommEfficiency().getEfficiency();
                    commRange = commResponseData.getCurrent().getCommEfficiency().getRangeString();
                }
                updateSql += ",commTimeEfficiency=" + commTimeEfficiency + ",commTime=" + commTime;
            }

            List < ? > historyRunStatusQueryList = commonDataService.findCallSql(historyRunStatusSql);
            if (historyRunStatusQueryList.size() > 0) {
                Object[] historyRunStatuObj = (Object[]) historyRunStatusQueryList.get(0);
                lastRunTime = historyRunStatuObj[3] + "";
                runStatus = StringManagerUtils.stringToInteger(historyRunStatuObj[4] + "");
                runTimeEfficiency = StringManagerUtils.stringToFloat(historyRunStatuObj[5] + "");
                runTime = StringManagerUtils.stringToFloat(historyRunStatuObj[6] + "");
                runRange = StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(historyRunStatuObj[7]));
            }
            String runTotalRequestData = "{" +
                "\"AKString\":\"\"," +
                "\"WellName\":\"" + deviceName + "\"," +
                "\"OffsetHour\":" + offsetHour + "," +
                "\"Last\":{" +
                "\"AcqTime\": \"" + lastRunTime + "\"," +
                "\"RunStatus\": " + (runStatus >= 1) + "," +
                "\"RunEfficiency\": {" +
                "\"Efficiency\": " + runTimeEfficiency + "," +
                "\"Time\": " + runTime + "," +
                "\"Range\": " + runRange + "" +
                "}" +
                "}," +
                "\"Current\": {" +
                "\"AcqTime\":\"" + timeStr + "\"," +
                "\"RunStatus\":" + (runStatus >= 1) + "" +
                "}" +
                "}";
            timeEffResponseData = CalculateUtils.runCalculate(runTotalRequestData);
            updateSql += ",runStatus=" + runStatus;
            if (timeEffResponseData != null && timeEffResponseData.getResultStatus() == 1) {
                if (timeStr.equalsIgnoreCase(range.getEndTime()) && timeEffResponseData.getDaily() != null && StringManagerUtils.isNotNull(timeEffResponseData.getDaily().getDate())) {
                    runTime = timeEffResponseData.getDaily().getRunEfficiency().getTime();
                    runTimeEfficiency = timeEffResponseData.getDaily().getRunEfficiency().getEfficiency();
                    runRange = timeEffResponseData.getDaily().getRunEfficiency().getRangeString();
                } else {
                    runTime = timeEffResponseData.getCurrent().getRunEfficiency().getTime();
                    runTimeEfficiency = timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency();
                    runRange = timeEffResponseData.getCurrent().getRunEfficiency().getRangeString();
                }
                updateSql += ",runTimeEfficiency=" + runTimeEfficiency + ",runTime=" + runTime;
            }

            List < String > acqTimeList = new ArrayList < String > ();
            List < Integer > commStatusList = new ArrayList < Integer > ();
            List < Integer > runStatusList = new ArrayList < Integer > ();

            List < Float > rpmList = new ArrayList < Float > ();

            List < Float > theoreticalProductionList = new ArrayList < Float > ();
            List < Float > liquidVolumetricProductionList = new ArrayList < Float > ();
            List < Float > oilVolumetricProductionList = new ArrayList < Float > ();
            List < Float > waterVolumetricProductionList = new ArrayList < Float > ();
            List < Float > volumeWaterCutList = new ArrayList < Float > ();

            List < Float > liquidWeightProductionList = new ArrayList < Float > ();
            List < Float > oilWeightProductionList = new ArrayList < Float > ();
            List < Float > waterWeightProductionList = new ArrayList < Float > ();
            List < Float > weightWaterCutList = new ArrayList < Float > ();

            List < Float > pumpEffList = new ArrayList < Float > ();
            List < Float > pumpEff1List = new ArrayList < Float > ();
            List < Float > pumpEff2List = new ArrayList < Float > ();

            List < Float > systemEfficiencyList = new ArrayList < Float > ();
            List < Float > energyPer100mLiftList = new ArrayList < Float > ();

            List < Float > pumpSettingDepthList = new ArrayList < Float > ();
            List < Float > producingfluidLevelList = new ArrayList < Float > ();
            List < Float > submergenceList = new ArrayList < Float > ();

            List < Float > tubingPressureList = new ArrayList < Float > ();
            List < Float > casingPressureList = new ArrayList < Float > ();

            List < ? > singleresultlist = commonDataService.findCallSql(rpmSql);
            for (int j = 0; j < singleresultlist.size(); j++) {
                Object[] resuleObj = (Object[]) singleresultlist.get(j);
                String productionData = resuleObj[10].toString();
                type = new TypeToken < PCPCalculateRequestData > () {}.getType();
                PCPCalculateRequestData pcpProductionData = gson.fromJson(productionData, type);

                acqTimeList.add(resuleObj[1] + "");
                commStatusList.add(commStatus >= 1 ? 1 : 0);
                runStatusList.add(runStatus >= 1 ? 1 : 0);
                rpmList.add(StringManagerUtils.stringToFloat(resuleObj[2] + ""));

                theoreticalProductionList.add(StringManagerUtils.stringToFloat(resuleObj[3] + ""));
                liquidVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[4] + ""));
                oilVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[5] + ""));
                waterVolumetricProductionList.add(StringManagerUtils.stringToFloat(resuleObj[6] + ""));

                if (pcpProductionData != null && pcpProductionData.getProduction() != null) {
                    volumeWaterCutList.add(pcpProductionData.getProduction().getWaterCut());
                } else {
                    volumeWaterCutList.add(0.0f);
                }


                liquidWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[7] + ""));
                oilWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[8] + ""));
                waterWeightProductionList.add(StringManagerUtils.stringToFloat(resuleObj[9] + ""));
                if (pcpProductionData != null && pcpProductionData.getProduction() != null) {
                    weightWaterCutList.add(pcpProductionData.getProduction().getWeightWaterCut());
                } else {
                    weightWaterCutList.add(0.0f);
                }

                pumpEffList.add(StringManagerUtils.stringToFloat(resuleObj[11] + ""));
                pumpEff1List.add(StringManagerUtils.stringToFloat(resuleObj[12] + ""));
                pumpEff2List.add(StringManagerUtils.stringToFloat(resuleObj[13] + ""));

                systemEfficiencyList.add(StringManagerUtils.stringToFloat(resuleObj[14] + ""));
                energyPer100mLiftList.add(StringManagerUtils.stringToFloat(resuleObj[15] + ""));

                if (pcpProductionData != null && pcpProductionData.getProduction() != null) {
                    tubingPressureList.add(pcpProductionData.getProduction().getTubingPressure());
                    casingPressureList.add(pcpProductionData.getProduction().getCasingPressure());
                    pumpSettingDepthList.add(pcpProductionData.getProduction().getPumpSettingDepth());
                    producingfluidLevelList.add(pcpProductionData.getProduction().getProducingfluidLevel());
                } else {
                    tubingPressureList.add(0.0f);
                    casingPressureList.add(0.0f);
                    pumpSettingDepthList.add(0.0f);
                    producingfluidLevelList.add(0.0f);
                }
                submergenceList.add(StringManagerUtils.stringToFloat(resuleObj[16] + ""));
            }

            StringBuffer dataSbf = new StringBuffer();
            dataSbf.append("{\"AKString\":\"\",");
            dataSbf.append("\"WellName\":\"" + deviceId + "\",");
            dataSbf.append("\"CurrentCommStatus\":" + (commStatus >= 1 ? 1 : 0) + ",");
            dataSbf.append("\"CurrentRunStatus\":" + (runStatus >= 1 ? 1 : 0) + ",");
            dataSbf.append("\"Date\":\"" + date + "\",");
            dataSbf.append("\"OffsetHour\":" + offsetHour + ",");
            dataSbf.append("\"AcqTime\":[" + StringManagerUtils.joinStringArr(acqTimeList, ",") + "],");
            dataSbf.append("\"CommStatus\":[" + StringUtils.join(commStatusList, ",") + "],");
            dataSbf.append("\"CommTime\":" + commTime + ",");
            dataSbf.append("\"CommTimeEfficiency\":" + commTimeEfficiency + ",");
            dataSbf.append("\"CommRange\":\"" + commRange + "\",");
            dataSbf.append("\"RunStatus\":[" + StringUtils.join(runStatusList, ",") + "],");
            dataSbf.append("\"RunTime\":" + runTime + ",");
            dataSbf.append("\"RunTimeEfficiency\":" + runTimeEfficiency + ",");
            dataSbf.append("\"RunRange\":\"" + runRange + "\",");
            dataSbf.append("\"RPM\":[" + StringUtils.join(rpmList, ",") + "],");
            dataSbf.append("\"PumpSettingDepth\":[" + StringUtils.join(pumpSettingDepthList, ",") + "],");
            dataSbf.append("\"ProducingfluidLevel\":[" + StringUtils.join(producingfluidLevelList, ",") + "],");
            dataSbf.append("\"Submergence\":[" + StringUtils.join(submergenceList, ",") + "],");
            dataSbf.append("\"TubingPressure\":[" + StringUtils.join(tubingPressureList, ",") + "],");
            dataSbf.append("\"CasingPressure\":[" + StringUtils.join(casingPressureList, ",") + "],");
            dataSbf.append("\"TheoreticalProduction\":[" + StringUtils.join(theoreticalProductionList, ",") + "],");
            dataSbf.append("\"LiquidVolumetricProduction\":[" + StringUtils.join(liquidVolumetricProductionList, ",") + "],");
            dataSbf.append("\"OilVolumetricProduction\":[" + StringUtils.join(oilVolumetricProductionList, ",") + "],");
            dataSbf.append("\"WaterVolumetricProduction\":[" + StringUtils.join(waterVolumetricProductionList, ",") + "],");
            dataSbf.append("\"VolumeWaterCut\":[" + StringUtils.join(volumeWaterCutList, ",") + "],");
            dataSbf.append("\"LiquidWeightProduction\":[" + StringUtils.join(liquidWeightProductionList, ",") + "],");
            dataSbf.append("\"OilWeightProduction\":[" + StringUtils.join(oilWeightProductionList, ",") + "],");
            dataSbf.append("\"WaterWeightProduction\":[" + StringUtils.join(waterWeightProductionList, ",") + "],");
            dataSbf.append("\"WeightWaterCut\":[" + StringUtils.join(weightWaterCutList, ",") + "],");
            dataSbf.append("\"SystemEfficiency\":[" + StringUtils.join(systemEfficiencyList, ",") + "],");
            dataSbf.append("\"EnergyPer100mLift\":[" + StringUtils.join(energyPer100mLiftList, ",") + "],");
            dataSbf.append("\"PumpEff\":[" + StringUtils.join(pumpEffList, ",") + "],");
            dataSbf.append("\"PumpEff1\":[" + StringUtils.join(pumpEff1List, ",") + "],");
            dataSbf.append("\"PumpEff2\":[" + StringUtils.join(pumpEff2List, ",") + "]");
            dataSbf.append("}");

            TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(dataSbf.toString(), new TypeToken < TotalAnalysisRequestData > () {}.getType());
            TotalAnalysisResponseData totalAnalysisResponseData = CalculateUtils.totalCalculate(dataSbf.toString());

            updateSql += " where t.deviceId=" + deviceId + " and t.caltime=to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')";
            try {
                int r = commonDataService.getBaseDao().updateOrDeleteBySql(updateSql);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (commResponseData != null && commResponseData.getResultStatus() == 1) {
                List < String > clobCont = new ArrayList < String > ();
                String updateHisRangeClobSql = "update tbl_pcptimingcalculationdata t set t.commrange=?";
                clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
                if (timeEffResponseData != null && timeEffResponseData.getResultStatus() == 1) {
                    updateHisRangeClobSql += ", t.runrange=?";
                    clobCont.add(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString());
                }
                updateHisRangeClobSql += " where t.deviceId=" + deviceId + " and t.caltime=" + "to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')";
                try {
                    int r = commonDataService.getBaseDao().executeSqlUpdateClob(updateHisRangeClobSql, clobCont);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (totalAnalysisResponseData != null && totalAnalysisResponseData.getResultStatus() == 1) {
                int recordCount = totalAnalysisRequestData.getAcqTime() != null ? totalAnalysisRequestData.getAcqTime().size() : 0;
                try {
                    commonDataService.getBaseDao().saveRPMTimingTotalCalculateData(totalAnalysisResponseData, totalAnalysisRequestData, timeStr, recordCount);
                } catch (SQLException | ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
        	Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle();
    		Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
    		
    		DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId+"");
			 AcqInstanceOwnItem acqInstanceOwnItem=null;
			 ModbusProtocolConfig.Protocol protocol=null;
			 if(deviceInfo!=null){
				 acqInstanceOwnItem=MemoryDataManagerTask.getAcqInstanceOwnItemByCode(deviceInfo.getInstanceCode());
				 if(acqInstanceOwnItem!=null){
					 protocol=MemoryDataManagerTask.getProtocolByName(acqInstanceOwnItem.getProtocol());
				 }
			 }
    		
        	String labelInfoSql = "select t.deviceId, t.headerlabelinfo from tbl_timingcalculationdata t " +
                " where t.id=(" +
                " select v2.id from " +
                " ( select v.id,rownum r from " +
                " (select t2.id from tbl_timingcalculationdata t2 " +
                "  where t2.deviceId=" + deviceId + " and t2.headerLabelInfo is not null order by t2.caltime desc) v ) v2" +
                " where r=1)";
            String historyCommStatusSql = "select t.id,t.deviceId,t2.deviceName,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime," +
                "t.commstatus,t.commtimeefficiency,t.commtime,t.commrange" +
                " from tbl_acqdata_hist t,tbl_device t2 " +
                " where t.deviceId=t2.id " +
                " and t.id=(" +
                " select max(t3.id) from  tbl_acqdata_hist t3  " +
                " where t3.acqtime between to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')-1 and to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')" +
                " and t3.deviceId=" + deviceId + " and t3.checksign=1 "+
                " )";

            String historyRunStatusSql = "select t.id,t.deviceId,t2.deviceName,to_char(t.acqtime,'yyyy-mm-dd hh24:mi:ss') as acqTime," +
                " t.runstatus,t.runtimeefficiency,t.runtime,t.runrange" +
                " from tbl_acqdata_hist t,tbl_device t2 " +
                " where t.deviceId=t2.id " +
                " and t.id=(" +
                " select max(t3.id) from  tbl_acqdata_hist t3  " +
                " where t3.commstatus=1 and t3.runstatus in (0,1) " +
                " and t3.acqtime between to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')-1 and to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss') " +
                " and t3.deviceId=" + deviceId +" and t3.checksign=1 "+
                " )";

            TimeEffResponseData timeEffResponseData = null;
            CommResponseData commResponseData = null;

            String lastRunTime = "";
            String lastCommTime = "";

            int commStatus = 0;
            float commTime = 0;
            float commTimeEfficiency = 0;
            String commRange = "";

            int runStatus = 0;
            float runTime = 0;
            float runTimeEfficiency = 0;
            String runRange = "";

            List < ? > labelInfoQueryList = commonDataService.findCallSql(labelInfoSql);
            String labelInfo = "";
            ReportTemplate.Template template = null;

            //继承表头信息
            for (int j = 0; j < labelInfoQueryList.size(); j++) {
                Object[] labelInfoObj = (Object[]) labelInfoQueryList.get(j);
                if (StringManagerUtils.stringToInteger(labelInfoObj[0].toString()) == deviceId) {
                    labelInfo = labelInfoObj[1] + "";
                    break;
                }
            }

            String updateSql = "update tbl_timingcalculationdata t set t.headerlabelinfo='" + labelInfo + "'";

            try {
                commonDataService.getBaseDao().initDeviceTimingReportDate(deviceId, timeStr, date, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //报表继承可编辑数据
            if (StringManagerUtils.isNotNull(templateCode)) {
                template = MemoryDataManagerTask.getSingleWellDailyReportTemplateByCode(templateCode);
            }
            if (template != null) {
                if (template.getEditable() != null && template.getEditable().size() > 0) {
                    String reportItemSql = "select t.itemname,t.itemcode,t.sort,t.datatype " +
                        " from TBL_REPORT_ITEMS2UNIT_CONF t " +
                        " where t.unitid=" + reportUnitId +
                        " and t.datasource<>'计算'" +
                        " and t.sort>=0" +
                        " and t.reporttype=2" +
                        " order by t.sort";
                    List < ReportUnitItem > reportItemList = new ArrayList < ReportUnitItem > ();
                    List < ? > reportItemQuertList = commonDataService.findCallSql(reportItemSql);

                    for (int k = 0; k < reportItemQuertList.size(); k++) {
                        Object[] reportItemObj = (Object[]) reportItemQuertList.get(k);
                        ReportUnitItem reportUnitItem = new ReportUnitItem();
                        reportUnitItem.setItemName(reportItemObj[0] + "");
                        reportUnitItem.setItemCode(reportItemObj[1] + "");
                        reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2] + ""));
                        reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3] + ""));


                        for (int l = 0; l < template.getEditable().size(); l++) {
                            ReportTemplate.Editable editable = template.getEditable().get(l);
                            if (editable.getStartRow() >= template.getHeader().size() && reportUnitItem.getSort() - 1 >= editable.getStartColumn() && reportUnitItem.getSort() - 1 <= editable.getEndColumn()) { //索引起始不同
                                reportItemList.add(reportUnitItem);
                                break;
                            }
                        }
                    }
                    if (reportItemList.size() > 0) {
                        StringBuffer updateColBuff = new StringBuffer();
                        for (int m = 0; m < reportItemList.size(); m++) {
                            updateColBuff.append(reportItemList.get(m).getItemCode() + ",");
                        }
                        if (updateColBuff.toString().endsWith(",")) {
                            updateColBuff.deleteCharAt(updateColBuff.length() - 1);
                        }

                        String updateEditDataSql = "update tbl_timingcalculationdata t set (" + updateColBuff + ")=" +
                            " (select " + updateColBuff + " from tbl_timingcalculationdata t2 " +
                            " where t2.deviceId= " + deviceId +
                            " and t2.id=" +
                            " (select v2.id from" +
                            " (select v.id,rownum r from " +
                            " (select t3.id from tbl_timingcalculationdata t3 " +
                            " where t3.deviceId=" + deviceId + " and t3.caltime<to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss') " +
                            " order by t3.caltime desc) v " +
                            " ) v2" +
                            " where r=1)" +
                            ") " +
                            " where t.deviceId=" + deviceId +
                            " and t.caltime=to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss') ";
                        try {
                            int r = commonDataService.getBaseDao().updateOrDeleteBySql(updateEditDataSql);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            List < ? > historyCommStatusQueryList = commonDataService.findCallSql(historyCommStatusSql);
            if (historyCommStatusQueryList.size() > 0) {
                Object[] historyCommStatusObj = (Object[]) historyCommStatusQueryList.get(0);
                lastCommTime = historyCommStatusObj[3] + "";
                commStatus = StringManagerUtils.stringToInteger(historyCommStatusObj[4] + "");
                commTimeEfficiency = StringManagerUtils.stringToFloat(historyCommStatusObj[5] + "");
                commTime = StringManagerUtils.stringToFloat(historyCommStatusObj[6] + "");
                commRange = StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(historyCommStatusObj[7]));
            }
            String commTotalRequestData = "{" +
                "\"AKString\":\"\"," +
                "\"WellName\":\"" + deviceName + "\"," +
                "\"OffsetHour\":" + offsetHour + "," +
                "\"Last\":{" +
                "\"AcqTime\": \"" + lastCommTime + "\"," +
                "\"CommStatus\": " + (commStatus >= 1) + "," +
                "\"CommEfficiency\": {" +
                "\"Efficiency\": " + commTimeEfficiency + "," +
                "\"Time\": " + commTime + "," +
                "\"Range\": " + commRange + "" +
                "}" +
                "}," +
                "\"Current\": {" +
                "\"AcqTime\":\"" + timeStr + "\"," +
                "\"CommStatus\":" + (commStatus >= 1) + "" +
                "}" +
                "}";
            commResponseData = CalculateUtils.commCalculate(commTotalRequestData);

            updateSql += ",CommStatus=" + commStatus;
            if (commResponseData != null && commResponseData.getResultStatus() == 1) {
                if (timeStr.equalsIgnoreCase(range.getEndTime()) && commResponseData.getDaily() != null && StringManagerUtils.isNotNull(commResponseData.getDaily().getDate())) {
                    commTime = commResponseData.getDaily().getCommEfficiency().getTime();
                    commTimeEfficiency = commResponseData.getDaily().getCommEfficiency().getEfficiency();
                    commRange = commResponseData.getDaily().getCommEfficiency().getRangeString();
                } else {
                    commTime = commResponseData.getCurrent().getCommEfficiency().getTime();
                    commTimeEfficiency = commResponseData.getCurrent().getCommEfficiency().getEfficiency();
                    commRange = commResponseData.getCurrent().getCommEfficiency().getRangeString();
                }
                updateSql += ",commTimeEfficiency=" + commTimeEfficiency + ",commTime=" + commTime;
            }

            List < ? > historyRunStatusQueryList = commonDataService.findCallSql(historyRunStatusSql);
            if (historyRunStatusQueryList.size() > 0) {
                Object[] historyRunStatuObj = (Object[]) historyRunStatusQueryList.get(0);
                lastRunTime = historyRunStatuObj[3] + "";
                runStatus = StringManagerUtils.stringToInteger(historyRunStatuObj[4] + "");
                runTimeEfficiency = StringManagerUtils.stringToFloat(historyRunStatuObj[5] + "");
                runTime = StringManagerUtils.stringToFloat(historyRunStatuObj[6] + "");
                runRange = StringManagerUtils.getWellRuningRangeJson(StringManagerUtils.CLOBObjectToString(historyRunStatuObj[7]));
            }
            String runTotalRequestData = "{" +
                "\"AKString\":\"\"," +
                "\"WellName\":\"" + deviceName + "\"," +
                "\"OffsetHour\":" + offsetHour + "," +
                "\"Last\":{" +
                "\"AcqTime\": \"" + lastRunTime + "\"," +
                "\"RunStatus\": " + (runStatus >= 1) + "," +
                "\"RunEfficiency\": {" +
                "\"Efficiency\": " + runTimeEfficiency + "," +
                "\"Time\": " + runTime + "," +
                "\"Range\": " + runRange + "" +
                "}" +
                "}," +
                "\"Current\": {" +
                "\"AcqTime\":\"" + timeStr + "\"," +
                "\"RunStatus\":" + (runStatus >= 1) + "" +
                "}" +
                "}";
            timeEffResponseData = CalculateUtils.runCalculate(runTotalRequestData);
            updateSql += ",runStatus=" + runStatus;
            if (timeEffResponseData != null && timeEffResponseData.getResultStatus() == 1) {
                if (timeStr.equalsIgnoreCase(range.getEndTime()) && timeEffResponseData.getDaily() != null && StringManagerUtils.isNotNull(timeEffResponseData.getDaily().getDate())) {
                    runTime = timeEffResponseData.getDaily().getRunEfficiency().getTime();
                    runTimeEfficiency = timeEffResponseData.getDaily().getRunEfficiency().getEfficiency();
                    runRange = timeEffResponseData.getDaily().getRunEfficiency().getRangeString();
                } else {
                    runTime = timeEffResponseData.getCurrent().getRunEfficiency().getTime();
                    runTimeEfficiency = timeEffResponseData.getCurrent().getRunEfficiency().getEfficiency();
                    runRange = timeEffResponseData.getCurrent().getRunEfficiency().getRangeString();
                }
                updateSql += ",runTimeEfficiency=" + runTimeEfficiency + ",runTime=" + runTime;
            }


            String sql = "select t.deviceId,to_char(t.acqTime,'yyyy-mm-dd hh24:mi:ss') as acqTime,t.acqdata";

            String newestDailyTotalDataSql = "select t.id,t.deviceid,t.acqtime,t.itemcolumn,t.itemname,t.totalvalue,t.todayvalue " +
                " from tbl_dailytotalcalculate_hist t," +
                " (select deviceid,max(acqtime) as acqtime,itemcolumn  " +
                "  from tbl_dailytotalcalculate_hist " +
                "  where acqtime between to_date('" + range.getStartTime() + "','yyyy-mm-dd hh24:mi:ss') and to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')" +
                "  group by deviceid,itemcolumn) v " +
                " where t.deviceid=v.deviceid and t.acqtime=v.acqtime and t.itemcolumn=v.itemcolumn" +
                " and t.acqtime between to_date('" + range.getStartTime() + "','yyyy-mm-dd hh24:mi:ss') and to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss') ";
            sql += " from tbl_acqdata_hist t " +
                " where t.acqtime between to_date('" + range.getStartTime() + "','yyyy-mm-dd hh24:mi:ss') and to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss') "
                + "  and t.checksign=1";
            if (StringManagerUtils.isNotNull(deviceId + "")) {
                sql += " and t.deviceid=" + deviceId;
                newestDailyTotalDataSql += " and t.deviceid=" + deviceId;
            }

            sql += "order by t.deviceid,t.acqTime";
            newestDailyTotalDataSql += " order by t.deviceid";
            List < ? > totalList = commonDataService.findCallSql(sql);
            List < ? > newestDailyTotalDataList = commonDataService.findCallSql(newestDailyTotalDataSql);


            Map < String, List < KeyValue >> deviceAcqDataMap = new LinkedHashMap < > ();
            for (int i = 0; i < totalList.size(); i++) {
                Object[] obj = (Object[]) totalList.get(i);
                int deviceId = StringManagerUtils.stringToInteger(obj[0] + "");
                String acqTime = obj[1] + "";
                String acqData = StringManagerUtils.CLOBObjectToString(obj[2]);

                type = new TypeToken < List < KeyValue >> () {}.getType();
                List <KeyValue> acqDataList = gson.fromJson(acqData, type);
                if(acqDataList!=null){
                	deviceAcqDataMap.put(acqTime, acqDataList);
                }
            }

            List < KeyValue > deviceTotalDataList = new ArrayList < > ();
            Map < String, List < String >> itemDataMap = new LinkedHashMap < > ();

            Iterator < Map.Entry < String, List < KeyValue >>> deviceAcqDataIterator = deviceAcqDataMap.entrySet().iterator();
            while (deviceAcqDataIterator.hasNext()) {
                Map.Entry < String, List < KeyValue >> deviceAcqDataEntry = deviceAcqDataIterator.next();
                String acqTime = deviceAcqDataEntry.getKey();
                List < KeyValue > deviceAcqDataList = deviceAcqDataEntry.getValue();

                if (deviceAcqDataList != null) {
                    for (KeyValue keyValue: deviceAcqDataList) {
                        if (itemDataMap.containsKey(keyValue.getKey())) {
                            List < String > itemDataList = itemDataMap.get(keyValue.getKey());
                            itemDataList.add(keyValue.getValue());
                            itemDataMap.put(keyValue.getKey(), itemDataList);
                        } else {
                            List < String > itemDataList = new ArrayList < > ();
                            itemDataList.add(keyValue.getValue());
                            itemDataMap.put(keyValue.getKey(), itemDataList);
                        }
                    }
                }
            }

            if (itemDataMap.size() > 0) {
                Iterator < Map.Entry < String, List < String >>> itemDataMapIterator = itemDataMap.entrySet().iterator();
                while (itemDataMapIterator.hasNext()) {
                    Map.Entry < String, List < String >> itemDataEntry = itemDataMapIterator.next();
                    String itemCode = itemDataEntry.getKey();
                    
                    ModbusProtocolConfig.Items item=null;
					DataMapping dataMapping=null;
					if(loadProtocolMappingColumnMap!=null){
						dataMapping=loadProtocolMappingColumnMap.get(itemCode);
						if(dataMapping!=null){
							item=MemoryDataManagerTask.getProtocolItem(protocol,  dataMapping.getName());
						}
					}
                    
                    List < String > itemDataList = itemDataEntry.getValue();
                    String maxValue = " ", minValue = " ", avgValue = " ", newestValue = " ", oldestValue = " ", dailyTotalValue = " ";
                    String tatalValue="";

                    if (itemDataList != null && itemDataList.size() > 0) {
                        oldestValue = itemDataList.get(0);
                        newestValue = itemDataList.get(itemDataList.size() - 1);

                        maxValue = itemDataList.get(0);
                        minValue = itemDataList.get(0);

                        float sumValue = 0;
                        int count = 0;
                        for (String itemDataStr: itemDataList) {
                            if (StringManagerUtils.isNotNull(itemDataStr)) {
                                float itemData = StringManagerUtils.stringToFloat(itemDataStr);
                                sumValue += itemData;
                                count++;
                                if (StringManagerUtils.stringToFloat(maxValue) < itemData) {
                                    maxValue = itemDataStr;
                                }
                                if (StringManagerUtils.stringToFloat(minValue) > itemData) {
                                    minValue = itemDataStr;
                                }
                            }
                        }
                        if (count > 0) {
                            avgValue = StringManagerUtils.stringToFloat((sumValue / count) + "", 3) + "";
                        }
                    }

                    String totalColumn = (itemCode + "_total").toUpperCase();
                    for (Object newestDailyTotalData: newestDailyTotalDataList) {
                        Object[] newestDailyTotalDataObj = (Object[]) newestDailyTotalData;
                        if (deviceId == StringManagerUtils.stringToInteger(newestDailyTotalDataObj[1] + "") && totalColumn.equalsIgnoreCase(newestDailyTotalDataObj[3] + "")) {
                            dailyTotalValue = newestDailyTotalDataObj[6] + "";
                            break;
                        }
                    }

                    if(item!=null && item.getQuantity()==1 
							&& ("int".equalsIgnoreCase(item.getIFDataType()) || "float".equalsIgnoreCase(item.getIFDataType()) || "float32".equalsIgnoreCase(item.getIFDataType()) || "float64".equalsIgnoreCase(item.getIFDataType())  )
							){
						tatalValue=(maxValue+";"+minValue+";"+avgValue+";"+oldestValue+";"+newestValue+";"+dailyTotalValue).replaceAll("null", "");
					}else{
						tatalValue=newestValue;
					}

                    KeyValue keyValue = new KeyValue(itemCode, tatalValue);
                    deviceTotalDataList.add(keyValue);
                }
            }

            updateSql += " where t.deviceId=" + deviceId + " and t.caltime=to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')";
            try {
                int r = commonDataService.getBaseDao().updateOrDeleteBySql(updateSql);
            } catch (Exception e) {
                e.printStackTrace();
            }


            String updateRangeClobSql = "update tbl_timingcalculationdata t set t.calData=?";
            List < String > clobCont = new ArrayList < String > ();
            clobCont.add(new Gson().toJson(deviceTotalDataList));
            if (commResponseData != null && commResponseData.getResultStatus() == 1) {
                updateRangeClobSql += ", t.commrange=?";
                clobCont.add(commResponseData.getCurrent().getCommEfficiency().getRangeString());
                if (timeEffResponseData != null && timeEffResponseData.getResultStatus() == 1) {
                    updateRangeClobSql += ", t.runrange=?";
                    clobCont.add(timeEffResponseData.getCurrent().getRunEfficiency().getRangeString());
                }
                updateRangeClobSql += " where t.deviceid=" + deviceId + " and t.caltime=" + "to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')";
                commonDataService.getBaseDao().executeSqlUpdateClob(updateRangeClobSql, clobCont);
            }
        }

        long calculateEndTime = System.nanoTime();
        StringManagerUtils.printLog("定时汇总计算：" + (calculateType == 1 ? "抽油机井" : (calculateType == 2 ? "螺杆泵井" : "")) + deviceName + ",timeStr=" + timeStr + ",threadId=" + threadId + ",总耗时:" + StringManagerUtils.getTimeDiff(calculateStartTime, calculateEndTime));
    }

    public int getThreadId() {
        return threadId;
    }


    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }


    public int getDeviceId() {
        return deviceId;
    }


    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }


    public String getDeviceName() {
        return deviceName;
    }


    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public String getTimeStr() {
        return timeStr;
    }


    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }


    public String getTemplateCode() {
        return templateCode;
    }


    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }


    public String getReportUnitId() {
        return reportUnitId;
    }


    public void setReportUnitId(String reportUnitId) {
        this.reportUnitId = reportUnitId;
    }


    public CommonDataService getCommonDataService() {
        return commonDataService;
    }


    public void setCommonDataService(CommonDataService commonDataService) {
        this.commonDataService = commonDataService;
    }

    public int getCalculateType() {
        return calculateType;
    }

    public void setCalculateType(int calculateType) {
        this.calculateType = calculateType;
    }
}
