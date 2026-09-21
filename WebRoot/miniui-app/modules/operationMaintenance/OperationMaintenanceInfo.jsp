<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="java.util.*,com.cosog.model.User,com.cosog.utils.ConfigFile,com.google.gson.Gson" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
User userLogin = (User)session.getAttribute("userLogin");
String userLoginNo = userLogin != null ? userLogin.getUserNo() + "" : "";
String loginUserLanguage = userLogin != null ? userLogin.getLanguageName() + "" : "zh_CN";

String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>运维配置</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <script src="js/operationMaintenanceInfo.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
    <style>
        html, body {
            margin: 0; padding: 0; width: 100%; height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #f0f2f5;
        }
        .om-container {
            width: 100%; height: 100%;
            display: flex; flex-direction: column;
            background: #fff;
            overflow: hidden;
        }
        .om-container .mini-tabs-body,
        .om-container .mini-tab-body {
            overflow: hidden !important;
            padding: 0 !important;
        }
        .om-container .mini-panel-body {
            overflow: hidden !important;
        }
        .mini-panel { border: 0 !important; }
        .mini-panel-border { border: 0 !important; }
        .mini-panel-header { border-bottom: 1px solid #e8e8e8 !important; }
        .mini-panel-body { border: 0 !important; padding: 0 !important; }
        .mini-panel-toolbar {
            background: #fafafa !important;
            border-bottom: 1px solid #e8e8e8 !important;
            padding: 4px 8px !important;
            box-sizing: border-box !important;
        }
        .mini-panel-toolbar > div { background: transparent !important; border: 0 !important; }

        .om-tab-content {
            width: 100%; height: 100%;
            overflow-y: auto;
            overflow-x: hidden;
            box-sizing: border-box;
            padding: 4px 0 8px 0;
        }

        /* ============ FieldSet 样式 ============ */
        .mini-fieldset {
            border: 1px solid #e0e0e0;
            border-radius: 3px;
            padding: 2px 6px 4px 6px;
            margin: 4px 6px;
            position: relative;
            background: #fff;
        }
        .mini-fieldset > legend {
            padding: 0 6px;
            font-weight: bold;
            color: #2d6a9f;
            font-size: 13px;
            line-height: 14px;
        }

        /* ============ 三列表格布局 ============ */
        .om-form-table {
            width: 100%;
            border-collapse: collapse;
            table-layout: fixed;
        }
        .om-form-table td {
            padding: 1px 4px;
            vertical-align: middle;
            font-size: 12px;
            color: #333;
            overflow: hidden;
        }
        .om-form-table td.label {
            text-align: right;
            white-space: nowrap;
            text-overflow: ellipsis;
        }
        .om-form-table td.input-cell {
            text-align: left;
        }
        /* 输入控件撑满 td */
        .om-form-table td.input-cell > .mini-textbox,
        .om-form-table td.input-cell > .mini-combobox,
        .om-form-table td.input-cell > .mini-spinner,
        .om-form-table td.input-cell > .mini-datepicker,
        .om-form-table td.input-cell > .mini-textarea {
            width: 200px !important;
    		max-width: 100%;
        }
        
        .om-form-table td.input-cell > .mini-radiobuttonlist {
    		max-width: 100%;
		}
		
		#om_oem_projectProfile {
    		width: 500px !important;
    		max-width: 100%;
		}

        .om-toolbar-sep {
            display: inline-block;
            width: 1px; height: 16px; background: #ddd;
            margin: 0 6px; vertical-align: middle;
        }
        .mini-splitter-border { border: 0 !important; }
        .mini-splitter-pane { padding: 0 !important; border: 0 !important; }
        .mini-splitter-handler { background: transparent !important; border: 1px solid #e8e8e8 !important; }
        .mini-tabs-buttons-left { background: #fafafa; }
        .hot-container { width: 100%; height: 100%; }
        .empty-msg { color: #999; font-size: 13px; text-align: center; padding: 20px; }
    </style>
</head>
<body>

<div class="om-container">
    <div id="omMainTabs" class="mini-tabs" style="width:100%;height:100%;" tabPosition="top"
         activeIndex="0" onactivechanged="onOmTabChanged">

        <!-- ============ 1. 自动化运维管理 ============ -->
        <div id="omBasicTab" title="" name="basic">
            <div id="omBasicPanel" class="mini-panel" style="width:100%;height:100%;"
                 showHeader="false" showToolbar="true" showCloseButton="false"
                 bodyStyle="padding:0;">
                <div property="toolbar">
                    <table style="width:100%;border-collapse:collapse;">
                        <tr>
                            <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                <button id="omBasicRefreshBtn" class="mini-button" iconCls="note-refresh" plain="true"
                                        onclick="onOmBasicRefresh()"></button>
                            </td>
                            <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                <button id="omBasicSaveBtn" class="mini-button" iconCls="save" plain="true"
                                        onclick="onOmBasicSave()"></button>
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="om-tab-content">
                    <!-- ============ FieldSet：基本信息 ============ -->
                    <fieldset class="mini-fieldset">
                        <legend id="omLegendBasicInfo"></legend>
                        <table class="om-form-table">
                            <colgroup>
                                <col style="width:11%;"/>
                                <col style="width:22.33%;"/>
                                <col style="width:11%;"/>
                                <col style="width:22.33%;"/>
                                <col style="width:11%;"/>
                                <col style="width:22.34%;"/>
                            </colgroup>
                            <tr>
                                <td class="label" id="omLblLoginLanguage"></td>
                                <td class="input-cell">
                                    <input id="om_loginLanguage" class="mini-combobox"
                                           valueField="boxkey" textField="boxval" allowInput="false" />
                                </td>
                                <td class="label" id="omLblShowLogo"></td>
                                <td class="input-cell">
                                    <input id="om_showLogo" class="mini-checkbox" />
                                </td>
                                <td class="label" id="omLblPrintExceptionLog"></td>
                                <td class="input-cell">
                                    <input id="om_printExceptionLog" class="mini-checkbox" />
                                </td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblTimeEfficiencyUnit"></td>
                                <td class="input-cell">
                                    <input id="om_timeEfficiencyUnit" class="mini-radiobuttonlist"
                                           repeatLayout="flow" repeatDirection="horizontal" />
                                </td>
                                <td class="label" id="omLblResourceMonitoringSaveData"></td>
                                <td class="input-cell">
                                    <input id="om_resourceMonitoringSaveData" class="mini-spinner"
                                           minValue="1" maxValue="9999999999" />
                                </td>
                                <td class="label" id="omLblPrintLog"></td>
                                <td class="input-cell">
                                    <input id="om_printLog" class="mini-checkbox" />
                                </td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblSimulateAcqEnable"></td>
                                <td class="input-cell">
                                    <input id="om_simulateAcqEnable" class="mini-checkbox" onvaluechanged="onOmSimulateAcqChange" />
                                </td>
                                <td class="label" id="omLblSendCycle"></td>
                                <td class="input-cell">
                                    <input id="om_sendCycle" class="mini-spinner" minValue="1" maxValue="9999999999" />
                                </td>
                                <td class="label" id="omLblPrintAdLog"></td>
                                <td class="input-cell">
                                    <input id="om_printAdLog" class="mini-checkbox" />
                                </td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblExportLimit"></td>
                                <td class="input-cell">
                                    <input id="om_exportLimit" class="mini-spinner" minValue="1" maxValue="1000000" />
                                </td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </table>
                    </fieldset>

                    <!-- ============ FieldSet：历史数据维护 ============ -->
                    <fieldset class="mini-fieldset">
                        <legend id="omLegendHistoricalData"></legend>
                        <table class="om-form-table">
                            <colgroup>
                                <col style="width:11%;"/>
                                <col style="width:22.33%;"/>
                                <col style="width:11%;"/>
                                <col style="width:22.33%;"/>
                                <col style="width:11%;"/>
                                <col style="width:22.34%;"/>
                            </colgroup>
                            <tr>
                                <td class="label" id="omLblDbCycle"></td>
                                <td class="input-cell">
                                    <input id="om_databaseMaintenanceCycle" class="mini-spinner" minValue="0" maxValue="9999" onvaluechanged="onOmDbCycleChange" />
                                </td>
                                <td class="label" id="omLblDbStartTime"></td>
                                <td class="input-cell">
                                    <input id="om_databaseMaintenanceStartTime" class="mini-textbox" />
                                </td>
                                <td class="label" id="omLblDbEndTime"></td>
                                <td class="input-cell">
                                    <input id="om_databaseMaintenanceEndTime" class="mini-textbox" />
                                </td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblAcqdataHistEnabled"></td>
                                <td class="input-cell">
                                    <input id="om_acqdata_hist_enabled" class="mini-checkbox" />
                                </td>
                                <td class="label" id="omLblAcqdataHistRetention"></td>
                                <td class="input-cell">
                                    <input id="om_acqdata_hist_retentionTime" class="mini-spinner" minValue="1" maxValue="99999" />
                                </td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblAcqrawdataEnabled"></td>
                                <td class="input-cell">
                                    <input id="om_acqrawdata_enabled" class="mini-checkbox" />
                                </td>
                                <td class="label" id="omLblAcqrawdataRetention"></td>
                                <td class="input-cell">
                                    <input id="om_acqrawdata_retentionTime" class="mini-spinner" minValue="1" maxValue="99999" />
                                </td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblAlarminfoHistEnabled"></td>
                                <td class="input-cell">
                                    <input id="om_alarminfo_hist_enabled" class="mini-checkbox" />
                                </td>
                                <td class="label" id="omLblAlarminfoHistRetention"></td>
                                <td class="input-cell">
                                    <input id="om_alarminfo_hist_retentionTime" class="mini-spinner" minValue="1" maxValue="99999" />
                                </td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblDailyTotalEnabled"></td>
                                <td class="input-cell">
                                    <input id="om_dailytotalcalculate_hist_enabled" class="mini-checkbox" />
                                </td>
                                <td class="label" id="omLblDailyTotalRetention"></td>
                                <td class="input-cell">
                                    <input id="om_dailytotalcalculate_hist_retentionTime" class="mini-spinner" minValue="1" maxValue="99999" />
                                </td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblDailyCalcEnabled"></td>
                                <td class="input-cell">
                                    <input id="om_dailycalculationdata_enabled" class="mini-checkbox" />
                                </td>
                                <td class="label" id="omLblDailyCalcRetention"></td>
                                <td class="input-cell">
                                    <input id="om_dailycalculationdata_retentionTime" class="mini-spinner" minValue="1" maxValue="99999" />
                                </td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblTimingCalcEnabled"></td>
                                <td class="input-cell">
                                    <input id="om_timingcalculationdata_enabled" class="mini-checkbox" />
                                </td>
                                <td class="label" id="omLblTimingCalcRetention"></td>
                                <td class="input-cell">
                                    <input id="om_timingcalculationdata_retentionTime" class="mini-spinner" minValue="1" maxValue="99999" />
                                </td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblTimingRecordEnabled"></td>
                                <td class="input-cell">
                                    <input id="om_timingrecorddata_enabled" class="mini-checkbox" />
                                </td>
                                <td class="label" id="omLblTimingRecordRetention"></td>
                                <td class="input-cell">
                                    <input id="om_timingrecorddata_retentionTime" class="mini-spinner" minValue="1" maxValue="99999" />
                                </td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblAcqdataVacuateEnabled"></td>
                                <td class="input-cell">
                                    <input id="om_acqdata_vacuate_enabled" class="mini-checkbox" />
                                </td>
                                <td class="label" id="omLblAcqdataVacuateRetention"></td>
                                <td class="input-cell">
                                    <input id="om_acqdata_vacuate_retentionTime" class="mini-spinner" minValue="1" maxValue="99999" />
                                </td>
                                <td></td>
                                <td></td>
                            </tr>
                        </table>
                    </fieldset>

                    <!-- ============ FieldSet：数据抽稀 ============ -->
                    <fieldset class="mini-fieldset">
                        <legend id="omLegendDataVacuate"></legend>
                        <table class="om-form-table">
                            <colgroup>
                                <col style="width:11%;"/>
                                <col style="width:22.33%;"/>
                                <col style="width:11%;"/>
                                <col style="width:22.33%;"/>
                                <col style="width:11%;"/>
                                <col style="width:22.34%;"/>
                            </colgroup>
                            <tr>
                                <td class="label" id="omLblVacuateRecord"></td>
                                <td class="input-cell">
                                    <input id="om_vacuateRecord" class="mini-spinner" minValue="1" maxValue="9999999999" />
                                </td>
                                <td class="label" id="omLblVacuateSaveInterval"></td>
                                <td class="input-cell">
                                    <input id="om_vacuateSaveInterval" class="mini-spinner" minValue="1" maxValue="9999999999" />
                                </td>
                                <td class="label" id="omLblVacuateThreshold"></td>
                                <td class="input-cell">
                                    <input id="om_vacuateThreshold" class="mini-spinner" minValue="1" maxValue="9999999999" />
                                </td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblVacuateSaveIntervalWaveRange"></td>
                                <td class="input-cell">
                                    <input id="om_vacuateSaveIntervalWaveRange" class="mini-spinner" minValue="0" maxValue="9999999999" />
                                </td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </table>
                    </fieldset>

                    <!-- ============ FieldSet：报表配置 ============ -->
                    <fieldset class="mini-fieldset">
                        <legend id="omLegendReportConfig"></legend>
                        <table class="om-form-table">
                            <colgroup>
                                <col style="width:11%;"/>
                                <col style="width:22.33%;"/>
                                <col style="width:11%;"/>
                                <col style="width:22.33%;"/>
                                <col style="width:11%;"/>
                                <col style="width:22.34%;"/>
                            </colgroup>
                            <tr>
                                <td class="label" id="omLblReportOffsetHour"></td>
                                <td class="input-cell">
                                    <input id="om_reportOffsetHour" class="mini-spinner" minValue="0" maxValue="12" />
                                </td>
                                <td class="label" id="omLblReportInterval"></td>
                                <td class="input-cell">
                                    <input id="om_reportInterval" class="mini-spinner" minValue="1" maxValue="12" />
                                </td>
                                <td></td>
                                <td></td>
                            </tr>
                        </table>
                    </fieldset>
                </div>
            </div>
        </div>

        <!-- ============ 2. 备份与恢复 ============ -->
        <div id="omBackupTab" title="" name="backup">
            <div id="omBackupTabs" class="mini-tabs" style="width:100%;height:100%;" tabPosition="left"
                 activeIndex="0" onactivechanged="onOmBackupTabChanged">

                <!-- 导出数据 -->
                <div id="omExportTab" title="" name="export">
                    <div id="omExportPanel" class="mini-panel" style="width:100%;height:100%;"
                         showHeader="false" showToolbar="true" showCloseButton="false"
                         bodyStyle="padding:0;">
                        <div property="toolbar">
                            <table style="width:100%;border-collapse:collapse;">
                                <tr>
                                    <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                        <button id="omBackupSelectAllBtn" class="mini-button" plain="true"
                                                onclick="onOmBackupSelectAll()"></button>
                                        <button id="omBackupDeselectAllBtn" class="mini-button" plain="true"
                                                onclick="onOmBackupDeselectAll()"></button>
                                    </td>
                                    <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                        <button id="omOneKeyBackupBtn" class="mini-button" iconCls="export" plain="true"
                                                onclick="onOmOneKeyBackup()"></button>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div id="BatchExportModuleGridPanel_Id" class="mini-datagrid"
     						style="width:100%;height:100%;"
     						idField="code"
     						allowResize="false"
     						allowAlternating="true"
     						showPager="false"
     						pageSize="100"
     						showPageInfo="false"
     						multiSelect="true"
     						allowCellEdit="false"
     						allowCellSelect="false"
     						showEmptyText="true"
     						dataField="totalRoot"
     						totalField="totalCount"
     						onbeforeload="onOmExportGridBeforeLoad"
     						onload="onOmExportGridLoad">
    						<div property="columns"></div>
    						<div property="emptyText" class="empty-msg"></div>
						</div>
                    </div>
                </div>

                <!-- 导入数据 -->
                <div id="omImportTab" title="" name="import">
                    <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">
                        <div size="22%" showCollapseButton="false" minSize="180">
                            <div id="BatchImportModulePanel_Id" class="mini-panel"
     							style="width:100%;height:100%;"
     							showHeader="true" showToolbar="false" showCloseButton="false"
     							bodyStyle="padding:0;overflow:hidden;">
    							<input id="BatchExportModuleDataListSelectRow_Id" class="mini-hidden" />
    							<input id="BatchExportModuleDataListSelectCode_Id" class="mini-hidden" />
    							<div id="BatchImportModuleGridPanel_Id" class="mini-datagrid"
         							style="width:100%;height:100%;"
         							idField="code"
         							allowResize="false"
         							allowAlternating="true"
         							showPager="false"
         							pageSize="100"
         							showPageInfo="false"
         							multiSelect="false"
         							allowCellEdit="false"
         							allowCellSelect="false"
         							showEmptyText="true"
         							dataField="totalRoot"
         							totalField="totalCount"
         							onbeforeload="onOmImportGridBeforeLoad"
         							onload="onOmImportGridLoad"
         							onselect="onOmImportGridSelect">
        						<div property="columns"></div>
        						<div property="emptyText" class="empty-msg"></div>
    						</div>
							</div>
                        </div>

                        <div size="78%" showCollapseButton="true" collapseDirection="right" minSize="300">
                            <div id="OperationMaintenanceDataImportPanel_Id" class="mini-panel"
                                 style="width:100%;height:100%;"
                                 showHeader="true" showToolbar="true" showCloseButton="false"
                                 bodyStyle="padding:0;">
                                <div property="toolbar">
                                    <table style="width:100%;border-collapse:collapse;">
                                        <tr>
                                            <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                                <button id="omImportPrevBtn" class="mini-button" iconCls="forward" plain="true"
                                                        onclick="onOmImportPrev()"></button>
                                                <span class="om-toolbar-sep"></span>
                                                <form id="omImportForm" style="display:inline;"
                                                      action="<%=path%>/moduleManagerController/uploadImportedModuleFile"
                                                      method="post" enctype="multipart/form-data" target="omUploadFrame">
                                                    <input id="omImportFile" class="mini-htmlfile" name="file"
                                                           style="width:300px;" limitType="*.json" onfileselect="onOmImportFileSelect" />
                                                    <iframe name="omUploadFrame" style="display:none;"></iframe>
                                                </form>
                                            </td>
                                            <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                                <button id="omImportSaveBtn" class="mini-button" iconCls="save" plain="true"
                                                        onclick="onOmImportSave()"></button>
                                                <button id="omImportNextBtn" class="mini-button" iconCls="backwards" plain="true"
                                                        onclick="onOmImportNext()"></button>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <div id="OperationMaintenanceDataImportContentDiv_Id" style="width:100%;height:100%;"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- ============ 3. OEM配置 ============ -->
        <div id="omOemTab" title="" name="oem" visible="false">
            <div id="omOemPanel" class="mini-panel" style="width:100%;height:100%;"
                 showHeader="false" showToolbar="true" showCloseButton="false"
                 bodyStyle="padding:0;">
                <div property="toolbar">
                    <table style="width:100%;border-collapse:collapse;">
                        <tr>
                            <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                <button id="omOemSaveBtn" class="mini-button" iconCls="save" plain="true"
                                        onclick="onOmOemSave()"></button>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="om-tab-content">
                    <fieldset class="mini-fieldset">
                        <legend id="omLegendProjectInfo"></legend>
                        <table class="om-form-table">
                            <colgroup>
                                <col style="width:11%;"/>
                                <col style="width:89%;"/>
                            </colgroup>
                            <tr>
                                <td class="label" id="omLblProjectName"></td>
                                <td class="input-cell">
                                    <input id="om_oem_projectName" class="mini-textbox" required="true" />
                                </td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblProjectProfile"></td>
                                <td class="input-cell">
                                    <input id="om_oem_projectProfile" class="mini-textarea" required="true" />
                                </td>
                            </tr>
                        </table>
                    </fieldset>

                    <fieldset class="mini-fieldset">
                        <legend id="omLegendBackgroundIcon"></legend>
                        <table class="om-form-table">
                            <colgroup>
                                <col style="width:11%;"/>
                                <col style="width:89%;"/>
                            </colgroup>
                            <tr>
                                <td class="label" id="omLblProjectLogo"></td>
                                <td class="input-cell">
                                    <input id="om_oem_projectLogo" class="mini-textbox" style="width:70%;" />
                                    <button class="mini-button" iconCls="upload" onclick="onOmUpload('logo')"></button>
                                </td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblProjectFavicon"></td>
                                <td class="input-cell">
                                    <input id="om_oem_projectFavicon" class="mini-textbox" style="width:70%;" />
                                    <button class="mini-button" iconCls="upload" onclick="onOmUpload('favicon')"></button>
                                </td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblLoginBackgroundImage"></td>
                                <td class="input-cell">
                                    <input id="om_oem_loginBackgroundImage" class="mini-textbox" style="width:70%;" />
                                    <button class="mini-button" iconCls="upload" onclick="onOmUpload('loginBg')"></button>
                                </td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblHelpButtonIcon"></td>
                                <td class="input-cell">
                                    <input id="om_oem_helpButtonIcon" class="mini-textbox" style="width:70%;" />
                                    <button class="mini-button" iconCls="upload" onclick="onOmUpload('help')"></button>
                                </td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblExitButtonIcon"></td>
                                <td class="input-cell">
                                    <input id="om_oem_exitButtonIcon" class="mini-textbox" style="width:70%;" />
                                    <button class="mini-button" iconCls="upload" onclick="onOmUpload('exit')"></button>
                                </td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblSwitchButtonIcon"></td>
                                <td class="input-cell">
                                    <input id="om_oem_switchButtonIcon" class="mini-textbox" style="width:70%;" />
                                    <button class="mini-button" iconCls="upload" onclick="onOmUpload('switch')"></button>
                                </td>
                            </tr>
                            <tr>
                                <td class="label" id="omLblSwitchDisabledButtonIcon"></td>
                                <td class="input-cell">
                                    <input id="om_oem_switchDisabledButtonIcon" class="mini-textbox" style="width:70%;" />
                                    <button class="mini-button" iconCls="upload" onclick="onOmUpload('switchDisabled')"></button>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </div>
            </div>
        </div>

        <!-- ============ 4. 标签管理 ============ -->
        <div id="omTabManagerTab" title="" name="tabManager">
            <div id="omTabManagerTabs" class="mini-tabs" style="width:100%;height:100%;" tabPosition="left"
                 activeIndex="0" onactivechanged="onOmTabManagerTabChanged">

                <!-- 项目标签 -->
                <div id="omProjectTagTab" title="" name="projectTag">
                    <div id="omProjectTagPanel" class="mini-panel" style="width:100%;height:100%;"
                         showHeader="false" showToolbar="true" showCloseButton="false"
                         bodyStyle="padding:0;">
                        <div property="toolbar">
                            <table style="width:100%;border-collapse:collapse;">
                                <tr>
                                    <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                        <button id="omProjectTagRefreshBtn" class="mini-button" iconCls="note-refresh" plain="true"
                                                onclick="onOmProjectTagRefresh()"></button>
                                    </td>
                                    <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                        <button id="omProjectTagSaveBtn" class="mini-button" iconCls="save" plain="true"
                                                onclick="onOmProjectTagSave()"></button>
                                    </td>
                                </tr>
                            </table>
                        </div>

                        <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">
                            <div size="45%" showCollapseButton="false" minSize="250">
                                <div id="omDeviceTypeListPanel" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="false" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div id="deviceTypeMaintenanceTreeGridView_Id" class="mini-treegrid"
                                         style="width:100%;height:100%;"
                                         showTreeIcon="true"
                                         treeColumn="taskname"
                                         idField="deviceTypeId"
                                         textField="text"
                                         parentField="parentId"
                                         dataField="children"
                                         resultAsTree="false"
                                         allowResize="false"
                                         allowCellEdit="true"
                                         allowCellSelect="true"
                                         showEmptyText="true"
                                         autoLoad="false"
                                         onload="onOmDeviceTypeTreeLoad"
                                         onnodeselect="onOmDeviceTypeNodeSelect">
                                        <div property="columns"></div>
                                        <div property="emptyText" class="empty-msg"></div>
                                    </div>
                                </div>
                            </div>
                            <div size="55%" showCollapseButton="true" collapseDirection="right" minSize="300">
                                <div id="omDeviceTypeContentConfigPanel" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="false" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div class="om-tab-content">
                                        <fieldset class="mini-fieldset">
                                            <legend id="omLegendRealtimeMonitoring"></legend>
                                            <table class="om-form-table">
                                                <colgroup>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.33%;"/>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.33%;"/>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.34%;"/>
                                                </colgroup>
                                                <tr>
                                                    <td class="label" id="omLblFESDiagramStatPie"></td>
                                                    <td class="input-cell"><input id="om_realtime_FESDiagramStatPie" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblCommStatusStatPie"></td>
                                                    <td class="input-cell"><input id="om_realtime_CommStatusStatPie" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblRunStatusStatPie"></td>
                                                    <td class="input-cell"><input id="om_realtime_RunStatusStatPie" class="mini-checkbox" /></td>
                                                </tr>
                                                <tr>
                                                    <td class="label" id="omLblNumStatusStatPie"></td>
                                                    <td class="input-cell"><input id="om_realtime_NumStatusStatPie" class="mini-checkbox" /></td>
                                                    <td></td><td></td><td></td><td></td>
                                                </tr>
                                            </table>
                                        </fieldset>

                                        <fieldset class="mini-fieldset">
                                            <legend id="omLegendHistoryQuery"></legend>
                                            <table class="om-form-table">
                                                <colgroup>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.33%;"/>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.33%;"/>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.34%;"/>
                                                </colgroup>
                                                <tr>
                                                    <td class="label" id="omLblHistoryFESDiagramStatPie"></td>
                                                    <td class="input-cell"><input id="om_history_FESDiagramStatPie" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblHistoryCommStatusStatPie"></td>
                                                    <td class="input-cell"><input id="om_history_CommStatusStatPie" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblHistoryRunStatusStatPie"></td>
                                                    <td class="input-cell"><input id="om_history_RunStatusStatPie" class="mini-checkbox" /></td>
                                                </tr>
                                                <tr>
                                                    <td class="label" id="omLblHistoryNumStatusStatPie"></td>
                                                    <td class="input-cell"><input id="om_history_NumStatusStatPie" class="mini-checkbox" /></td>
                                                    <td></td><td></td><td></td><td></td>
                                                </tr>
                                            </table>
                                        </fieldset>

                                        <fieldset class="mini-fieldset">
                                            <legend id="omLegendAlarmQuery"></legend>
                                            <table class="om-form-table">
                                                <colgroup>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.33%;"/>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.33%;"/>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.34%;"/>
                                                </colgroup>
                                                <tr>
                                                    <td class="label" id="omLblAlarmFESDiagramResultAlarm"></td>
                                                    <td class="input-cell"><input id="om_alarm_FESDiagramResultAlarm" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblAlarmRunStatusAlarm"></td>
                                                    <td class="input-cell"><input id="om_alarm_RunStatusAlarm" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblAlarmCommStatusAlarm"></td>
                                                    <td class="input-cell"><input id="om_alarm_CommStatusAlarm" class="mini-checkbox" /></td>
                                                </tr>
                                                <tr>
                                                    <td class="label" id="omLblAlarmNumericValueAlarm"></td>
                                                    <td class="input-cell"><input id="om_alarm_NumericValueAlarm" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblAlarmEnumValueAlarm"></td>
                                                    <td class="input-cell"><input id="om_alarm_EnumValueAlarm" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblAlarmSwitchingValueAlarm"></td>
                                                    <td class="input-cell"><input id="om_alarm_SwitchingValueAlarm" class="mini-checkbox" /></td>
                                                </tr>
                                            </table>
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 设备标签 -->
                <div id="omDeviceTagTab" title="" name="deviceTag">
                    <div id="omDeviceTagPanel" class="mini-panel" style="width:100%;height:100%;"
                         showHeader="false" showToolbar="true" showCloseButton="false"
                         bodyStyle="padding:0;">
                        <div property="toolbar">
                            <table style="width:100%;border-collapse:collapse;">
                                <tr>
                                    <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                        <button id="omDeviceTagRefreshBtn" class="mini-button" iconCls="note-refresh" plain="true"
                                                onclick="onOmDeviceTagRefresh()"></button>
                                    </td>
                                    <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                        <button id="omDeviceTagAddBtn" class="mini-button" iconCls="add" plain="true"
                                                onclick="onOmDeviceTagAdd()"></button>
                                        <button id="omDeviceTagDelBtn" class="mini-button" iconCls="delete" plain="true"
                                                onclick="onOmDeviceTagDel()"></button>
                                        <button id="omDeviceTagSaveBtn" class="mini-button" iconCls="save" plain="true"
                                                onclick="onOmDeviceTagSave()"></button>
                                    </td>
                                </tr>
                            </table>
                        </div>

                        <div class="mini-splitter" style="width:100%;height:100%;" vertical="false">
                            <div size="45%" showCollapseButton="false" minSize="250">
                                <div id="omDeviceTabManagerPanel" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="false" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div id="operationMaintenanceDeviceTabManagerGridView_Id" class="mini-treegrid"
                                         style="width:100%;height:100%;"
                                         showTreeIcon="true"
                                         treeColumn="taskname"
                                         idField="instanceId"
                                         textField="text"
                                         parentField="pid"
                                         dataField="children"
                                         resultAsTree="false"
                                         allowResize="false"
                                         allowCellEdit="true"
                                         allowCellSelect="true"
                                         multiSelect="true"
                                         showEmptyText="true"
                                         autoLoad="false"
                                         onload="onOmDeviceTabTreeLoad"
                                         onnodeselect="onOmDeviceTabNodeSelect">
                                        <div property="columns"></div>
                                        <div property="emptyText" class="empty-msg"></div>
                                    </div>
                                </div>
                            </div>
                            <div size="55%" showCollapseButton="true" collapseDirection="right" minSize="300">
                                <div id="omDeviceTabContentConfigPanel" class="mini-panel"
                                     style="width:100%;height:100%;"
                                     showHeader="true" showToolbar="false" showCloseButton="false"
                                     bodyStyle="padding:0;overflow:hidden;">
                                    <div class="om-tab-content">
                                        <fieldset class="mini-fieldset">
                                            <legend id="omLegendRealtimeMonitoring2"></legend>
                                            <table class="om-form-table">
                                                <colgroup>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.33%;"/>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.33%;"/>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.34%;"/>
                                                </colgroup>
                                                <tr>
                                                    <td class="label" id="omLblWellboreAnalysis"></td>
                                                    <td class="input-cell"><input id="om_realtime_WellboreAnalysis" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblSurfaceAnalysis"></td>
                                                    <td class="input-cell"><input id="om_realtime_SurfaceAnalysis" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblTrendCurve"></td>
                                                    <td class="input-cell"><input id="om_realtime_TrendCurve" class="mini-checkbox" /></td>
                                                </tr>
                                                <tr>
                                                    <td class="label" id="omLblDynamicData"></td>
                                                    <td class="input-cell"><input id="om_realtime_DynamicData" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblDeviceControl"></td>
                                                    <td class="input-cell"><input id="om_realtime_DeviceControl" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblDeviceInformation"></td>
                                                    <td class="input-cell"><input id="om_realtime_DeviceInformation" class="mini-checkbox" /></td>
                                                </tr>
                                                <tr>
                                                    <td class="label" id="omLblRodStressChartMax"></td>
                                                    <td class="input-cell"><input id="om_realtime_RodStressMax" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblRodStressChartRange"></td>
                                                    <td class="input-cell"><input id="om_realtime_RodStressRange" class="mini-checkbox" /></td>
                                                    <td></td><td></td>
                                                </tr>
                                            </table>
                                        </fieldset>

                                        <fieldset class="mini-fieldset">
                                            <legend id="omLegendHistoryQuery2"></legend>
                                            <table class="om-form-table">
                                                <colgroup>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.33%;"/>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.33%;"/>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.34%;"/>
                                                </colgroup>
                                                <tr>
                                                    <td class="label" id="omLblHistoryTrendCurve"></td>
                                                    <td class="input-cell"><input id="om_history_TrendCurve" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblHistoryTiledDiagram"></td>
                                                    <td class="input-cell"><input id="om_history_TiledDiagram" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblHistoryDiagramOverlay"></td>
                                                    <td class="input-cell"><input id="om_history_DiagramOverlay" class="mini-checkbox" /></td>
                                                </tr>
                                            </table>
                                        </fieldset>

                                        <fieldset class="mini-fieldset">
                                            <legend id="omLegendPrimaryDevice"></legend>
                                            <table class="om-form-table">
                                                <colgroup>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.33%;"/>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.33%;"/>
                                                    <col style="width:20%;"/>
                                                    <col style="width:13.34%;"/>
                                                </colgroup>
                                                <tr>
                                                    <td class="label" id="omLblAdditionalInformation"></td>
                                                    <td class="input-cell"><input id="om_pd_AdditionalInformation" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblAuxiliaryDevice"></td>
                                                    <td class="input-cell"><input id="om_pd_AuxiliaryDevice" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblVideoConfig"></td>
                                                    <td class="input-cell"><input id="om_pd_VideoConfig" class="mini-checkbox" /></td>
                                                </tr>
                                                <tr>
                                                    <td class="label" id="omLblCalculateDataConfig"></td>
                                                    <td class="input-cell"><input id="om_pd_CalculateDataConfig" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblFSDiagramConstruction"></td>
                                                    <td class="input-cell"><input id="om_pd_FSDiagramConstruction" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblSystemParameterConfiguration"></td>
                                                    <td class="input-cell"><input id="om_pd_SystemParameterConfig" class="mini-checkbox" /></td>
                                                </tr>
                                                <tr>
                                                    <td class="label" id="omLblIntelligentFrequencyConversion"></td>
                                                    <td class="input-cell"><input id="om_pd_IntelligentFrequencyConversion" class="mini-checkbox" /></td>
                                                    <td class="label" id="omLblInterlockProtection"></td>
                                                    <td class="input-cell"><input id="om_pd_InterlockProtection" class="mini-checkbox" /></td>
                                                    <td></td><td></td>
                                                </tr>
                                            </table>
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- ============ 5. 内存曲线 ============ -->
        <div id="omMonitorCurveTab" title="" name="monitorCurve" visible="false">
            <div id="omMonitorCurvePanel" class="mini-panel" style="width:100%;height:100%;"
                 showHeader="false" showToolbar="true" showCloseButton="false"
                 bodyStyle="padding:0;">
                <div property="toolbar">
                    <table style="width:100%;border-collapse:collapse;">
                        <tr>
                            <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                <button id="omCurveRefreshBtn" class="mini-button" iconCls="note-refresh" plain="true"
                                        onclick="onOmCurveRefresh()"></button>
                                <span class="om-toolbar-sep"></span>
                                <span id="omLblCurveRange" style="font-size:12px;color:#333;"></span>
                                <input id="om_curveStartDate" class="mini-datepicker" format="yyyy-MM-dd" style="width:130px;" />
                                <input id="om_curveStartHour" class="mini-spinner" minValue="0" maxValue="23" style="width:60px;" />
                                <input id="om_curveStartMinute" class="mini-spinner" minValue="0" maxValue="59" style="width:60px;" />
                                <span class="om-toolbar-sep"></span>
                                <span id="omLblCurveTo" style="font-size:12px;color:#333;"></span>
                                <input id="om_curveEndDate" class="mini-datepicker" format="yyyy-MM-dd" style="width:130px;" />
                                <input id="om_curveEndHour" class="mini-spinner" minValue="0" maxValue="23" style="width:60px;" />
                                <input id="om_curveEndMinute" class="mini-spinner" minValue="0" maxValue="59" style="width:60px;" />
                                <span class="om-toolbar-sep"></span>
                                <button id="omCurveSearchBtn" class="mini-button" iconCls="search" plain="true"
                                        onclick="onOmCurveSearch()"></button>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="OperationMaintenanceMonitorCurveDiv_Id" class="hot-container"></div>
            </div>
        </div>

        <!-- ============ 6. 下位机程序升级 ============ -->
        <div id="omLowerComputerTab" title="" name="lowerComputer">
            <div id="omLowerComputerPanel" class="mini-panel" style="width:100%;height:100%;"
                 showHeader="false" showToolbar="true" showCloseButton="false"
                 bodyStyle="padding:0;">
                <div property="toolbar">
                    <table style="width:100%;border-collapse:collapse;">
                        <tr>
                            <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                                <button id="omLowerComputerRefreshBtn" class="mini-button" iconCls="note-refresh" plain="true"
                                        onclick="onOmLowerComputerRefresh()"></button>
                                <span class="om-toolbar-sep"></span>
                                <span id="omLblLowerComputerDeviceName" style="font-size:12px;color:#333;"></span>
                                <input id="lowerComputerProgramUpgradeDeviceListComb_Id" class="mini-combobox"
                                       style="width:180px;"
                                       valueField="boxkey" textField="boxval" allowInput="true"
                                       onvaluechanged="onOmLowerComputerDeviceChange" />
                            </td>
                            <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                                <button id="omLowerComputerSelectAllBtn" class="mini-button" iconCls="check" plain="true"
                                        onclick="onOmLowerComputerSelectAll()"></button>
                                <button id="omLowerComputerDeselectAllBtn" class="mini-button" iconCls="close" plain="true"
                                        onclick="onOmLowerComputerDeselectAll()"></button>
                                <span class="om-toolbar-sep"></span>
                                <button id="omBoxUpgradeBtn" class="mini-button" iconCls="downlink" plain="true"
                                        onclick="onOmBoxUpgrade()"></button>
                                <button id="omAcUpgradeBtn" class="mini-button" iconCls="downlink" plain="true"
                                        onclick="onOmAcUpgrade()"></button>
                                <button id="omLowerComputerUplinkBtn" class="mini-button" iconCls="uplink" plain="true"
                                        onclick="onOmLowerComputerUplink()"></button>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="OperationMaintenanceLowerComputerProgramUpgradeDiv_Id" class="hot-container"></div>
            </div>
        </div>

    </div>
</div>

<script>
    var context = '<%=path%>';
    var user_ = '<%=userLoginNo%>';
    var loginUserLanguage = '<%=loginUserLanguage%>';

    $(document).ready(function () {
        mini.parse();
        setTimeout(function () {
            initOperationMaintenancePage();
        }, 100);
    });
</script>
</body>
</html>