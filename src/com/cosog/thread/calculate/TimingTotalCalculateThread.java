package com.cosog.thread.calculate;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cosog.model.DataMapping;
import com.cosog.model.KeyValue;
import com.cosog.model.RealtimeTotalInfo;
import com.cosog.model.ReportTemplate;
import com.cosog.model.ReportUnitItem;
import com.cosog.model.calculate.AcqInstanceOwnItem;
import com.cosog.model.calculate.CommResponseData;
import com.cosog.model.calculate.DeviceInfo;
import com.cosog.model.calculate.EnergyCalculateResponseData;
import com.cosog.model.calculate.PCPCalculateRequestData;
import com.cosog.model.calculate.PCPCalculateResponseData;
import com.cosog.model.calculate.PCPDeviceTodayData;
import com.cosog.model.calculate.SRPCalculateRequestData;
import com.cosog.model.calculate.SRPCalculateResponseData;
import com.cosog.model.calculate.SRPDeviceTodayData;
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
        long calculateStartTime = System.nanoTime();
        long time1 = 0, time2 = 0;

        int offsetHour = Config.getInstance().configFile.getAp().getReport().getOffsetHour();
        String date = timeStr.split(" ")[0];
        if (!StringManagerUtils.timeMatchDate(timeStr, date, offsetHour)) {
            date = StringManagerUtils.addDay(StringManagerUtils.stringToDate(date), -1);
        }
        CommResponseData.Range range = StringManagerUtils.getTimeRange(date, offsetHour);
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken <TotalAnalysisRequestData> () {}.getType();

        if (calculateType == 1) {
        	DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId+"");
        	SRPDeviceTodayData deviceTodayData=MemoryDataManagerTask.getSRPDeviceTodayDataById(deviceInfo.getId());
        	
        	String lastRunTime = deviceInfo.getRunStatusAcqTime();
        	String lastCommTime = deviceInfo.getOnLineAcqTime();

        	int commStatus = deviceInfo.getCommStatus();
        	float commTime = deviceInfo.getCommTime();
        	float commTimeEfficiency = deviceInfo.getCommEff();
        	String commRange = deviceInfo.getCommRange();

        	int runStatus = deviceInfo.getRunStatus();
        	float runTime = deviceInfo.getRunTime();
        	float runTimeEfficiency = deviceInfo.getRunEff();
        	String runRange = deviceInfo.getRunRange();

            String labelInfoSql = "select t.deviceId, t.headerlabelinfo from tbl_srptimingcalculationdata t " +
                " where t.id=(" +
                " select v2.id from " +
                " ( select v.id,rownum r from " +
                " (select t2.id from tbl_srptimingcalculationdata t2 " +
                "  where t2.deviceId=" + deviceId + " and t2.headerLabelInfo is not null order by t2.caltime desc) v ) v2" +
                " where r=1)";
            TimeEffResponseData timeEffResponseData = null;
            CommResponseData commResponseData = null;

            List <?> labelInfoQueryList = commonDataService.findCallSql(labelInfoSql);
            String labelInfo = "";
            ReportTemplate.Template template = null;

            //继承表头信息
            for (int j = 0; j <labelInfoQueryList.size(); j++) {
                Object[] labelInfoObj = (Object[]) labelInfoQueryList.get(j);
                if (deviceId == StringManagerUtils.stringToInteger(labelInfoObj[0].toString())) {
                    labelInfo = labelInfoObj[1] + "";
                    break;
                }
            }
            String updateSql = "update tbl_srptimingcalculationdata t set t.headerlabelinfo='" + labelInfo + "'";
            try {
                commonDataService.getBaseDao().initDeviceTimingReportDate(deviceId, timeStr, date, calculateType);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //报表继承可编辑数据
            if (StringManagerUtils.isNotNull(templateCode)) {
                template = MemoryDataManagerTask.getSingleWellDailyReportTemplateByCode(templateCode);
            }
            if (template != null) {
                if (template.getEditable() != null && template.getEditable().size()> 0) {
                    String reportItemSql = "select t.itemname,t.itemcode,t.sort,t.datatype " +
                        " from TBL_REPORT_ITEMS2UNIT_CONF t " +
                        " where t.unitid=" + reportUnitId +
                        " and t.datasource='计算'" +
                        " and t.sort>=0" +
                        " and t.reporttype=2" +
                        " order by t.sort";
                    List <ReportUnitItem> reportItemList = new ArrayList <ReportUnitItem> ();
                    List <?> reportItemQuertList = commonDataService.findCallSql(reportItemSql);
                    for (int k = 0; reportItemQuertList != null && k <reportItemQuertList.size(); k++) {
                        Object[] reportItemObj = (Object[]) reportItemQuertList.get(k);
                        ReportUnitItem reportUnitItem = new ReportUnitItem();
                        reportUnitItem.setItemName(reportItemObj[0] + "");
                        reportUnitItem.setItemCode(reportItemObj[1] + "");
                        reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2] + ""));
                        reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3] + ""));


                        for (int l = 0; l <template.getEditable().size(); l++) {
                            ReportTemplate.Editable editable = template.getEditable().get(l);
                            if (editable.getStartRow()>= template.getHeader().size() && reportUnitItem.getSort() - 1>= editable.getStartColumn() && reportUnitItem.getSort() - 1 <= editable.getEndColumn()) { //索引起始不同
                                reportItemList.add(reportUnitItem);
                                break;
                            }
                        }
                    }
                    if (reportItemList.size()> 0) {
                        StringBuffer updateColBuff = new StringBuffer();
                        for (int m = 0; m <reportItemList.size(); m++) {
                            updateColBuff.append(reportItemList.get(m).getItemCode() + ",");
                        }
                        if (updateColBuff.toString().endsWith(",")) {
                            updateColBuff.deleteCharAt(updateColBuff.length() - 1);
                        }

                        String updateEditDataSql = "update tbl_srptimingcalculationdata t set (" + updateColBuff + ")=" +
                            " (select " + updateColBuff + " from tbl_srptimingcalculationdata t2 " +
                            " where t2.deviceId= " + deviceId +
                            " and t2.id=" +
                            " (select v2.id from" +
                            " (select v.id,rownum r from " +
                            " (select t3.id from tbl_srptimingcalculationdata t3 " +
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
            String commTotalRequestData = "{" +
                "\"AKString\":\"\"," +
                "\"WellName\":\"" + deviceName + "\"," +
                "\"OffsetHour\":" + offsetHour + "," +
                "\"Last\":{" +
                "\"AcqTime\": \"" + lastCommTime + "\"," +
                "\"CommStatus\": " + (commStatus>= 1) + "," +
                "\"CommEfficiency\": {" +
                "\"Efficiency\": " + commTimeEfficiency + "," +
                "\"Time\": " + commTime + "," +
                "\"Range\": " + StringManagerUtils.getWellRuningRangeJson(commRange) + "" +
                "}" +
                "}," +
                "\"Current\": {" +
                "\"AcqTime\":\"" + timeStr + "\"," +
                "\"CommStatus\":" + (commStatus>= 1) + "" +
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
            
            String runTotalRequestData = "{" +
                "\"AKString\":\"\"," +
                "\"WellName\":\"" + deviceName + "\"," +
                "\"OffsetHour\":" + offsetHour + "," +
                "\"Last\":{" +
                "\"AcqTime\": \"" + lastRunTime + "\"," +
                "\"RunStatus\": " + (runStatus>= 1) + "," +
                "\"RunEfficiency\": {" +
                "\"Efficiency\": " + runTimeEfficiency + "," +
                "\"Time\": " + runTime + "," +
                "\"Range\": " + StringManagerUtils.getWellRuningRangeJson(runRange) + "" +
                "}" +
                "}," +
                "\"Current\": {" +
                "\"AcqTime\":\"" + timeStr + "\"," +
                "\"RunStatus\":" + (runStatus>= 1) + "" +
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

            List <String> acqTimeList = new ArrayList <String> ();
            List <Integer> commStatusList = new ArrayList <Integer> ();
            List <Integer> runStatusList = new ArrayList <Integer> ();

            List <Float> rpmList = new ArrayList <Float> ();

            List <Integer> ResultCodeList = new ArrayList <Integer> ();
            List <Float> strokeList = new ArrayList <Float> ();
            List <Float> spmList = new ArrayList <Float> ();

            List <Float> FMaxList = new ArrayList <Float> ();
            List <Float> FMinList = new ArrayList <Float> ();

            List <Float> fullnessCoefficientList = new ArrayList <Float> ();

            List <Float> theoreticalProductionList = new ArrayList <Float> ();
            List <Float> liquidVolumetricProductionList = new ArrayList <Float> ();
            List <Float> oilVolumetricProductionList = new ArrayList <Float> ();
            List <Float> waterVolumetricProductionList = new ArrayList <Float> ();
            List <Float> volumeWaterCutList = new ArrayList <Float> ();

            List <Float> liquidWeightProductionList = new ArrayList <Float> ();
            List <Float> oilWeightProductionList = new ArrayList <Float> ();
            List <Float> waterWeightProductionList = new ArrayList <Float> ();
            List <Float> weightWaterCutList = new ArrayList <Float> ();

            List <Float> pumpEffList = new ArrayList <Float> ();
            List <Float> pumpEff1List = new ArrayList <Float> ();
            List <Float> pumpEff2List = new ArrayList <Float> ();
            List <Float> pumpEff3List = new ArrayList <Float> ();
            List <Float> pumpEff4List = new ArrayList <Float> ();


            List <Float> wattDegreeBalanceList = new ArrayList <Float> ();
            List <Float> iDegreeBalanceList = new ArrayList <Float> ();
            List <Float> deltaRadiusList = new ArrayList <Float> ();

            List <Float> surfaceSystemEfficiencyList = new ArrayList <Float> ();
            List <Float> wellDownSystemEfficiencyList = new ArrayList <Float> ();
            List <Float> systemEfficiencyList = new ArrayList <Float> ();
            List <Float> energyPer100mLiftList = new ArrayList <Float> ();

            List <Float> pumpSettingDepthList = new ArrayList <Float> ();
            List <Float> producingfluidLevelList = new ArrayList <Float> ();
            List <Float> calcProducingfluidLevelList = new ArrayList <Float> ();
            List <Float> levelDifferenceValueList = new ArrayList <Float> ();
            List <Float> submergenceList = new ArrayList <Float> ();

            List <Float> tubingPressureList = new ArrayList <Float> ();
            List <Float> casingPressureList = new ArrayList <Float> ();

            if(deviceTodayData!=null && deviceTodayData.getSRPCalculateList()!=null && deviceTodayData.getSRPCalculateList().size()>0){
            	for (int i = 0; i <deviceTodayData.getSRPCalculateList().size(); i++) {
            		SRPCalculateResponseData responseData =deviceTodayData.getSRPCalculateList().get(i);
            		if(responseData!=null && StringManagerUtils.timeMatchDate(responseData.getFESDiagram().getAcqTime(), date, offsetHour)){
                        acqTimeList.add(responseData.getFESDiagram().getAcqTime());
                        commStatusList.add(commStatus>= 1 ? 1 : 0);
                        runStatusList.add(runStatus>= 1 ? 1 : 0);

                        ResultCodeList.add(responseData.getCalculationStatus().getResultCode());
                        strokeList.add(responseData.getFESDiagram().getStroke());
                        spmList.add(responseData.getFESDiagram().getSPM());
                        FMaxList.add(responseData.getFESDiagram().getFMax().get(0));
                        FMinList.add(responseData.getFESDiagram().getFMin().get(0));
                        fullnessCoefficientList.add(responseData.getFESDiagram().getFullnessCoefficient());

                        theoreticalProductionList.add(responseData.getProduction().getTheoreticalProduction());
                        liquidVolumetricProductionList.add(responseData.getProduction().getLiquidVolumetricProduction());
                        oilVolumetricProductionList.add(responseData.getProduction().getOilVolumetricProduction());
                        waterVolumetricProductionList.add(responseData.getProduction().getWaterVolumetricProduction());
                        volumeWaterCutList.add(responseData.getProduction().getWaterCut());

                        liquidWeightProductionList.add(responseData.getProduction().getLiquidWeightProduction());
                        oilWeightProductionList.add(responseData.getProduction().getOilWeightProduction());
                        waterWeightProductionList.add(responseData.getProduction().getWaterWeightProduction());
                        weightWaterCutList.add(responseData.getProduction().getWeightWaterCut());

                        tubingPressureList.add(responseData.getProduction().getTubingPressure());
                        casingPressureList.add(responseData.getProduction().getCasingPressure());
                        pumpSettingDepthList.add(responseData.getProduction().getPumpSettingDepth());
                        producingfluidLevelList.add(responseData.getProduction().getProducingfluidLevel());


                        pumpEffList.add(responseData.getPumpEfficiency().getPumpEff());
                        pumpEff1List.add(responseData.getPumpEfficiency().getPumpEff1());
                        pumpEff2List.add(responseData.getPumpEfficiency().getPumpEff2());
                        pumpEff3List.add(responseData.getPumpEfficiency().getPumpEff3());
                        pumpEff4List.add(responseData.getPumpEfficiency().getPumpEff4());

                        wattDegreeBalanceList.add(responseData.getFESDiagram().getWattDegreeBalance());
                        iDegreeBalanceList.add(responseData.getFESDiagram().getIDegreeBalance());
                        deltaRadiusList.add(responseData.getFESDiagram().getDeltaRadius());

                        surfaceSystemEfficiencyList.add(responseData.getSystemEfficiency().getSurfaceSystemEfficiency());
                        wellDownSystemEfficiencyList.add(responseData.getSystemEfficiency().getWellDownSystemEfficiency());
                        systemEfficiencyList.add(responseData.getSystemEfficiency().getSystemEfficiency());
                        energyPer100mLiftList.add(responseData.getSystemEfficiency().getEnergyPer100mLift());

                        calcProducingfluidLevelList.add(responseData.getProduction().getCalcProducingfluidLevel());
                        levelDifferenceValueList.add(responseData.getProduction().getLevelDifferenceValue());
                        submergenceList.add(responseData.getProduction().getSubmergence());
            		}
            	}
            }
            
            StringBuffer dataSbf = new StringBuffer();
            dataSbf.append("{\"AKString\":\"\",");
            dataSbf.append("\"WellName\":\"" + deviceId + "\",");
            dataSbf.append("\"CurrentCommStatus\":" + (commStatus>= 1 ? 1 : 0) + ",");
            dataSbf.append("\"CurrentRunStatus\":" + (runStatus>= 1 ? 1 : 0) + ",");
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

            TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(dataSbf.toString(), new TypeToken <TotalAnalysisRequestData> () {}.getType());
            TotalAnalysisResponseData totalAnalysisResponseData = CalculateUtils.totalCalculate(dataSbf.toString());
            updateSql += " where t.deviceId=" + deviceId + " and t.caltime=to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')";
            try {
                int r = commonDataService.getBaseDao().updateOrDeleteBySql(updateSql);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (commResponseData != null && commResponseData.getResultStatus() == 1) {
                List <String> clobCont = new ArrayList <String> ();
                String updateHisRangeClobSql = "update tbl_srptimingcalculationdata t set t.commrange=?";
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
                    commonDataService.getBaseDao().saveFSDiagramTimingTotalCalculationData(totalAnalysisResponseData, totalAnalysisRequestData, timeStr, recordCount);
                } catch (SQLException | ParseException e) {
                    e.printStackTrace();
                }
            }
        } else if (calculateType == 2) {
        	DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId+"");
        	PCPDeviceTodayData deviceTodayData=MemoryDataManagerTask.getPCPDeviceTodayDataById(deviceInfo.getId());
        	
        	String lastRunTime = deviceInfo.getRunStatusAcqTime();
        	String lastCommTime = deviceInfo.getOnLineAcqTime();

        	int commStatus = deviceInfo.getCommStatus();
        	float commTime = deviceInfo.getCommTime();
        	float commTimeEfficiency = deviceInfo.getCommEff();
        	String commRange = deviceInfo.getCommRange();

        	int runStatus = deviceInfo.getRunStatus();
        	float runTime = deviceInfo.getRunTime();
        	float runTimeEfficiency = deviceInfo.getRunEff();
        	String runRange = deviceInfo.getRunRange();
        	
            String labelInfoSql = "select t.deviceId, t.headerlabelinfo from tbl_pcptimingcalculationdata t " +
                " where t.id=(" +
                " select v2.id from " +
                " ( select v.id,rownum r from " +
                " (select t2.id from tbl_pcptimingcalculationdata t2 " +
                "  where t2.deviceId=" + deviceId + " and t2.headerLabelInfo is not null order by t2.caltime desc) v ) v2" +
                " where r=1)";

            TimeEffResponseData timeEffResponseData = null;
            CommResponseData commResponseData = null;

            List <?> labelInfoQueryList = commonDataService.findCallSql(labelInfoSql);
            String labelInfo = "";
            ReportTemplate.Template template = null;

            //继承表头信息
            for (int j = 0; j <labelInfoQueryList.size(); j++) {
                Object[] labelInfoObj = (Object[]) labelInfoQueryList.get(j);
                if (StringManagerUtils.stringToInteger(labelInfoObj[0].toString()) == deviceId) {
                    labelInfo = labelInfoObj[1] + "";
                    break;
                }
            }

            String updateSql = "update tbl_pcptimingcalculationdata t set t.headerlabelinfo='" + labelInfo + "'";

            try {
                commonDataService.getBaseDao().initDeviceTimingReportDate(deviceId, timeStr, date, calculateType);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //报表继承可编辑数据
            if (StringManagerUtils.isNotNull(templateCode)) {
                template = MemoryDataManagerTask.getSingleWellDailyReportTemplateByCode(templateCode);
            }
            if (template != null) {
                if (template.getEditable() != null && template.getEditable().size()> 0) {
                    String reportItemSql = "select t.itemname,t.itemcode,t.sort,t.datatype " +
                        " from TBL_REPORT_ITEMS2UNIT_CONF t " +
                        " where t.unitid=" + reportUnitId +
                        " and t.datasource='计算'" +
                        " and t.sort>=0" +
                        " and t.reporttype=2" +
                        " order by t.sort";
                    List <ReportUnitItem> reportItemList = new ArrayList <ReportUnitItem> ();
                    List <?> reportItemQuertList = commonDataService.findCallSql(reportItemSql);

                    for (int k = 0; k <reportItemQuertList.size(); k++) {
                        Object[] reportItemObj = (Object[]) reportItemQuertList.get(k);
                        ReportUnitItem reportUnitItem = new ReportUnitItem();
                        reportUnitItem.setItemName(reportItemObj[0] + "");
                        reportUnitItem.setItemCode(reportItemObj[1] + "");
                        reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2] + ""));
                        reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3] + ""));


                        for (int l = 0; l <template.getEditable().size(); l++) {
                            ReportTemplate.Editable editable = template.getEditable().get(l);
                            if (editable.getStartRow()>= template.getHeader().size() && reportUnitItem.getSort() - 1>= editable.getStartColumn() && reportUnitItem.getSort() - 1 <= editable.getEndColumn()) { //索引起始不同
                                reportItemList.add(reportUnitItem);
                                break;
                            }
                        }
                    }
                    if (reportItemList.size()> 0) {
                        StringBuffer updateColBuff = new StringBuffer();
                        for (int m = 0; m <reportItemList.size(); m++) {
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
            String commTotalRequestData = "{" +
                "\"AKString\":\"\"," +
                "\"WellName\":\"" + deviceName + "\"," +
                "\"OffsetHour\":" + offsetHour + "," +
                "\"Last\":{" +
                "\"AcqTime\": \"" + lastCommTime + "\"," +
                "\"CommStatus\": " + (commStatus>= 1) + "," +
                "\"CommEfficiency\": {" +
                "\"Efficiency\": " + commTimeEfficiency + "," +
                "\"Time\": " + commTime + "," +
                "\"Range\": " + StringManagerUtils.getWellRuningRangeJson(commRange) + "" +
                "}" +
                "}," +
                "\"Current\": {" +
                "\"AcqTime\":\"" + timeStr + "\"," +
                "\"CommStatus\":" + (commStatus>= 1) + "" +
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
            String runTotalRequestData = "{" +
                "\"AKString\":\"\"," +
                "\"WellName\":\"" + deviceName + "\"," +
                "\"OffsetHour\":" + offsetHour + "," +
                "\"Last\":{" +
                "\"AcqTime\": \"" + lastRunTime + "\"," +
                "\"RunStatus\": " + (runStatus>= 1) + "," +
                "\"RunEfficiency\": {" +
                "\"Efficiency\": " + runTimeEfficiency + "," +
                "\"Time\": " + runTime + "," +
                "\"Range\": " + StringManagerUtils.getWellRuningRangeJson(runRange) + "" +
                "}" +
                "}," +
                "\"Current\": {" +
                "\"AcqTime\":\"" + timeStr + "\"," +
                "\"RunStatus\":" + (runStatus>= 1) + "" +
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

            List <String> acqTimeList = new ArrayList <String> ();
            List <Integer> commStatusList = new ArrayList <Integer> ();
            List <Integer> runStatusList = new ArrayList <Integer> ();

            List <Float> rpmList = new ArrayList <Float> ();

            List <Float> theoreticalProductionList = new ArrayList <Float> ();
            List <Float> liquidVolumetricProductionList = new ArrayList <Float> ();
            List <Float> oilVolumetricProductionList = new ArrayList <Float> ();
            List <Float> waterVolumetricProductionList = new ArrayList <Float> ();
            List <Float> volumeWaterCutList = new ArrayList <Float> ();

            List <Float> liquidWeightProductionList = new ArrayList <Float> ();
            List <Float> oilWeightProductionList = new ArrayList <Float> ();
            List <Float> waterWeightProductionList = new ArrayList <Float> ();
            List <Float> weightWaterCutList = new ArrayList <Float> ();

            List <Float> pumpEffList = new ArrayList <Float> ();
            List <Float> pumpEff1List = new ArrayList <Float> ();
            List <Float> pumpEff2List = new ArrayList <Float> ();

            List <Float> systemEfficiencyList = new ArrayList <Float> ();
            List <Float> energyPer100mLiftList = new ArrayList <Float> ();

            List <Float> pumpSettingDepthList = new ArrayList <Float> ();
            List <Float> producingfluidLevelList = new ArrayList <Float> ();
            List <Float> submergenceList = new ArrayList <Float> ();

            List <Float> tubingPressureList = new ArrayList <Float> ();
            List <Float> casingPressureList = new ArrayList <Float> ();

            if(deviceTodayData!=null && deviceTodayData.getPCPCalculateList()!=null && deviceTodayData.getPCPCalculateList().size()>0){
            	for (int i = 0; i <deviceTodayData.getPCPCalculateList().size(); i++) {
            		PCPCalculateResponseData responseData =deviceTodayData.getPCPCalculateList().get(i);
            		if(responseData!=null && StringManagerUtils.timeMatchDate(responseData.getAcqTime(), date, offsetHour)){
            			acqTimeList.add(responseData.getAcqTime());
                        commStatusList.add(commStatus>= 1 ? 1 : 0);
                        runStatusList.add(runStatus>= 1 ? 1 : 0);
                        rpmList.add(responseData.getRPM());

                        theoreticalProductionList.add(responseData.getProduction().getTheoreticalProduction());
                        liquidVolumetricProductionList.add(responseData.getProduction().getLiquidVolumetricProduction());
                        oilVolumetricProductionList.add(responseData.getProduction().getOilVolumetricProduction());
                        waterVolumetricProductionList.add(responseData.getProduction().getWaterVolumetricProduction());
                        volumeWaterCutList.add(responseData.getProduction().getWaterCut());

                        liquidWeightProductionList.add(responseData.getProduction().getLiquidWeightProduction());
                        oilWeightProductionList.add(responseData.getProduction().getOilWeightProduction());
                        waterWeightProductionList.add(responseData.getProduction().getWaterWeightProduction());
                        weightWaterCutList.add(responseData.getProduction().getWeightWaterCut());

                        pumpEffList.add(responseData.getPumpEfficiency().getPumpEff());
                        pumpEff1List.add(responseData.getPumpEfficiency().getPumpEff1());
                        pumpEff2List.add(responseData.getPumpEfficiency().getPumpEff2());

                        systemEfficiencyList.add(responseData.getSystemEfficiency().getSystemEfficiency());
                        energyPer100mLiftList.add(responseData.getSystemEfficiency().getEnergyPer100mLift());

                        tubingPressureList.add(responseData.getProduction().getTubingPressure());
                        casingPressureList.add(responseData.getProduction().getCasingPressure());
                        pumpSettingDepthList.add(responseData.getProduction().getPumpSettingDepth());
                        producingfluidLevelList.add(responseData.getProduction().getProducingfluidLevel());
                        submergenceList.add(responseData.getProduction().getSubmergence());
            		}
                }
            }

            StringBuffer dataSbf = new StringBuffer();
            dataSbf.append("{\"AKString\":\"\",");
            dataSbf.append("\"WellName\":\"" + deviceId + "\",");
            dataSbf.append("\"CurrentCommStatus\":" + (commStatus>= 1 ? 1 : 0) + ",");
            dataSbf.append("\"CurrentRunStatus\":" + (runStatus>= 1 ? 1 : 0) + ",");
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

            TotalAnalysisRequestData totalAnalysisRequestData = gson.fromJson(dataSbf.toString(), new TypeToken <TotalAnalysisRequestData> () {}.getType());
            TotalAnalysisResponseData totalAnalysisResponseData = CalculateUtils.totalCalculate(dataSbf.toString());

            updateSql += " where t.deviceId=" + deviceId + " and t.caltime=to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')";
            try {
                int r = commonDataService.getBaseDao().updateOrDeleteBySql(updateSql);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (commResponseData != null && commResponseData.getResultStatus() == 1) {
                List <String> clobCont = new ArrayList <String> ();
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
        }else{
        	Map<String,DataMapping> loadProtocolMappingColumnByTitleMap=MemoryDataManagerTask.getProtocolMappingColumnByTitle(0);
    		Map<String,DataMapping> loadProtocolMappingColumnMap=MemoryDataManagerTask.getProtocolMappingColumn();
    		
    		DeviceInfo deviceInfo=MemoryDataManagerTask.getDeviceInfo(deviceId+"");
    		RealtimeTotalInfo realtimeTotalInfo=MemoryDataManagerTask.getDeviceRealtimeTotalDataById(deviceInfo.getId()+"");

			String labelInfoSql = "select t.deviceId, t.headerlabelinfo from tbl_timingcalculationdata t " +
	                " where t.id=(" +
	                " select v2.id from " +
	                " ( select v.id,rownum r from " +
	                " (select t2.id from tbl_timingcalculationdata t2 " +
	                "  where t2.deviceId=" + deviceId + " and t2.headerLabelInfo is not null order by t2.caltime desc) v ) v2" +
	                " where r=1)";
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

            List <?> labelInfoQueryList = commonDataService.findCallSql(labelInfoSql);
            String labelInfo = "";
            ReportTemplate.Template template = null;

            //继承表头信息
            for (int j = 0; j <labelInfoQueryList.size(); j++) {
                Object[] labelInfoObj = (Object[]) labelInfoQueryList.get(j);
                if (StringManagerUtils.stringToInteger(labelInfoObj[0].toString()) == deviceId) {
                    labelInfo = labelInfoObj[1] + "";
                    break;
                }
            }

            String updateSql = "update tbl_timingcalculationdata t set t.headerlabelinfo='" + labelInfo + "'";

            try {
                commonDataService.getBaseDao().initDeviceTimingReportDate(deviceId, timeStr, date, calculateType);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
          //报表继承可编辑数据
            if (StringManagerUtils.isNotNull(templateCode)) {
                template = MemoryDataManagerTask.getSingleWellDailyReportTemplateByCode(templateCode);
            }
            if (template != null) {
                if (template.getEditable() != null && template.getEditable().size()> 0) {
                    String reportItemSql = "select t.itemname,t.itemcode,t.sort,t.datatype " +
                        " from TBL_REPORT_ITEMS2UNIT_CONF t " +
                        " where t.unitid=" + reportUnitId +
                        " and t.datasource<>'计算'" +
                        " and t.sort>=0" +
                        " and t.reporttype=2" +
                        " order by t.sort";
                    List <ReportUnitItem> reportItemList = new ArrayList <ReportUnitItem> ();
                    List <?> reportItemQuertList = commonDataService.findCallSql(reportItemSql);

                    for (int k = 0; k <reportItemQuertList.size(); k++) {
                        Object[] reportItemObj = (Object[]) reportItemQuertList.get(k);
                        ReportUnitItem reportUnitItem = new ReportUnitItem();
                        reportUnitItem.setItemName(reportItemObj[0] + "");
                        reportUnitItem.setItemCode(reportItemObj[1] + "");
                        reportUnitItem.setSort(StringManagerUtils.stringToInteger(reportItemObj[2] + ""));
                        reportUnitItem.setDataType(StringManagerUtils.stringToInteger(reportItemObj[3] + ""));


                        for (int l = 0; l <template.getEditable().size(); l++) {
                            ReportTemplate.Editable editable = template.getEditable().get(l);
                            if (editable.getStartRow()>= template.getHeader().size() && reportUnitItem.getSort() - 1>= editable.getStartColumn() && reportUnitItem.getSort() - 1 <= editable.getEndColumn()) { //索引起始不同
                                reportItemList.add(reportUnitItem);
                                break;
                            }
                        }
                    }
                    if (reportItemList.size()> 0) {
                        StringBuffer updateColBuff = new StringBuffer();
                        for (int m = 0; m <reportItemList.size(); m++) {
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
            
            
            //通信计算
            if(realtimeTotalInfo!=null){
            	lastCommTime = realtimeTotalInfo.getAcqTime();
                commStatus = realtimeTotalInfo.getCommStatus();
                commTimeEfficiency = realtimeTotalInfo.getCommEff();
                commTime = realtimeTotalInfo.getCommTime();
                commRange = realtimeTotalInfo.getCommRange();
                lastRunTime = realtimeTotalInfo.getAcqTime();
                runStatus = realtimeTotalInfo.getRunStatus();
                runTimeEfficiency = realtimeTotalInfo.getRunEff();
                runTime = realtimeTotalInfo.getRunTime();
                runRange = realtimeTotalInfo.getRunRange();
            }else{
            	lastCommTime = deviceInfo.getAcqTime();
                commStatus = deviceInfo.getCommStatus();
                commTimeEfficiency = deviceInfo.getCommEff();
                commTime = deviceInfo.getCommTime();
                commRange = deviceInfo.getCommRange();
                lastRunTime = deviceInfo.getAcqTime();
                runStatus = deviceInfo.getRunStatus();
                runTimeEfficiency = deviceInfo.getRunEff();
                runTime = deviceInfo.getRunTime();
                runRange = deviceInfo.getRunRange();
            }
            
            String commTotalRequestData = "{" +
                "\"AKString\":\"\"," +
                "\"WellName\":\"" + deviceName + "\"," +
                "\"OffsetHour\":" + offsetHour + "," +
                "\"Last\":{" +
                "\"AcqTime\": \"" + lastCommTime + "\"," +
                "\"CommStatus\": " + (commStatus>= 1) + "," +
                "\"CommEfficiency\": {" +
                "\"Efficiency\": " + commTimeEfficiency + "," +
                "\"Time\": " + commTime + "," +
                "\"Range\": " + StringManagerUtils.getWellRuningRangeJson(commRange) + "" +
                "}" +
                "}," +
                "\"Current\": {" +
                "\"AcqTime\":\"" + timeStr + "\"," +
                "\"CommStatus\":" + (commStatus>= 1) + "" +
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
           
            //时率计算
            String runTotalRequestData = "{" +
                "\"AKString\":\"\"," +
                "\"WellName\":\"" + deviceName + "\"," +
                "\"OffsetHour\":" + offsetHour + "," +
                "\"Last\":{" +
                "\"AcqTime\": \"" + lastRunTime + "\"," +
                "\"RunStatus\": " + (runStatus>= 1) + "," +
                "\"RunEfficiency\": {" +
                "\"Efficiency\": " + runTimeEfficiency + "," +
                "\"Time\": " + runTime + "," +
                "\"Range\": " + StringManagerUtils.getWellRuningRangeJson(runRange) + "" +
                "}" +
                "}," +
                "\"Current\": {" +
                "\"AcqTime\":\"" + timeStr + "\"," +
                "\"RunStatus\":" + (runStatus>= 1) + "" +
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
            
            updateSql += " where t.deviceId=" + deviceId + " and t.caltime=to_date('" + timeStr + "','yyyy-mm-dd hh24:mi:ss')";
            try {
            	int r = commonDataService.getBaseDao().updateOrDeleteBySql(updateSql);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //处理计算数据
            String totalCalData="";
            if(realtimeTotalInfo!=null && realtimeTotalInfo.getTotalItemMap()!=null){
            	List<KeyValue> deviceTotalDataList=new ArrayList<>();
				Map<String, RealtimeTotalInfo.TotalItem> totalItemMap=realtimeTotalInfo.getTotalItemMap();
				Iterator<Map.Entry<String, RealtimeTotalInfo.TotalItem>> totalItemMapIterator = totalItemMap.entrySet().iterator();
				while (totalItemMapIterator.hasNext()) {
					Map.Entry<String, RealtimeTotalInfo.TotalItem> entry = totalItemMapIterator.next();
					 String itemCode=entry.getKey();
					 String tatalValue="";
					 RealtimeTotalInfo.TotalItem totalItem=entry.getValue();
					 tatalValue=(totalItem.getTotalStatus().getMaxValue()+";"
					 +totalItem.getTotalStatus().getMinValue()+";"
					 +totalItem.getTotalStatus().getAvgValue()+";"
					 +totalItem.getTotalStatus().getOldestValue()+";"
					 +totalItem.getTotalStatus().getNewestValue()+";"
					 +(totalItem.getTotalStatus().getDailyTotalSign()?totalItem.getTotalStatus().getDailyTotalValue():" ")).replaceAll("null", "");
					 
					 KeyValue keyValue=new KeyValue(itemCode,tatalValue);
					 deviceTotalDataList.add(keyValue);
				}
				totalCalData=new Gson().toJson(deviceTotalDataList);
            }
            
            String updateRangeClobSql = "update tbl_timingcalculationdata t set t.calData=?";
            List <String> clobCont = new ArrayList <String> ();
            clobCont.add(totalCalData);
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
