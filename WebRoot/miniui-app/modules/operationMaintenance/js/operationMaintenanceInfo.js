// ================================================================
// 运维配置模块 - operationMaintenanceInfo.js
// ================================================================

// ---------- 全局状态 ----------
var _omModuleRight = {
    viewFlag: 0,
    editFlag: 0,
    controlFlag: 0
};
var isInitializing = true;
var _selectedDeviceTypeId = 0;
var _selectedDeviceTabInstanceId = 0;
//全局 handsontable helper
var lowerComputerProgramUpgradeHandsontableHelper = null;
var operationMaintenanceMonitorCurveChart = null;

var _DM_DEVICE_COMBO_PAGE_SIZE =
    (typeof defaultWellComboxSize !== 'undefined' && defaultWellComboxSize > 0) ?
    defaultWellComboxSize :
    9999;

//checkbox id → 后端字段路径 映射
var _OM_DEVICE_TAB_CONFIG_FIELDS = [
    // 实时监控模块（普通 checkbox）
    {
        id: 'om_realtime_WellboreAnalysis',
        group: 'DeviceRealTimeMonitoring',
        field: 'WellboreAnalysis'
    },
    {
        id: 'om_realtime_SurfaceAnalysis',
        group: 'DeviceRealTimeMonitoring',
        field: 'SurfaceAnalysis'
    },
    {
        id: 'om_realtime_TrendCurve',
        group: 'DeviceRealTimeMonitoring',
        field: 'TrendCurve'
    },
    {
        id: 'om_realtime_DynamicData',
        group: 'DeviceRealTimeMonitoring',
        field: 'DynamicData'
    },
    {
        id: 'om_realtime_DeviceControl',
        group: 'DeviceRealTimeMonitoring',
        field: 'DeviceControl'
    },
    {
        id: 'om_realtime_DeviceInformation',
        group: 'DeviceRealTimeMonitoring',
        field: 'DeviceInformation'
    },
    // ★ 杆柱应力图显示内容由 checkboxlist 单独处理，此处不再列入

    // 历史查询模块
    {
        id: 'om_history_TrendCurve',
        group: 'DeviceHistoryQuery',
        field: 'TrendCurve'
    },
    {
        id: 'om_history_TiledDiagram',
        group: 'DeviceHistoryQuery',
        field: 'TiledDiagram'
    },
    {
        id: 'om_history_DiagramOverlay',
        group: 'DeviceHistoryQuery',
        field: 'DiagramOverlay'
    },

    // 主设备模块
    {
        id: 'om_pd_AdditionalInformation',
        group: 'PrimaryDevice',
        field: 'AdditionalInformation'
    },
    {
        id: 'om_pd_AuxiliaryDevice',
        group: 'PrimaryDevice',
        field: 'AuxiliaryDevice'
    },
    {
        id: 'om_pd_VideoConfig',
        group: 'PrimaryDevice',
        field: 'VideoConfig'
    },
    {
        id: 'om_pd_CalculateDataConfig',
        group: 'PrimaryDevice',
        field: 'CalculateDataConfig'
    },
    {
        id: 'om_pd_FSDiagramConstruction',
        group: 'PrimaryDevice',
        field: 'FSDiagramConstruction'
    },
    {
        id: 'om_pd_SystemParameterConfig',
        group: 'PrimaryDevice',
        field: 'SystemParameterConfig'
    },
    {
        id: 'om_pd_IntelligentFrequencyConversion',
        group: 'PrimaryDevice',
        field: 'IntelligentFrequencyConversion'
    },
    {
        id: 'om_pd_InterlockProtection',
        group: 'PrimaryDevice',
        field: 'InterlockProtection'
    }
];

// ★ 杆柱应力图显示内容的两个选项 key
var _OM_ROD_STRESS_KEY_MAX = 'max';
var _OM_ROD_STRESS_KEY_RANGE = 'range';

// ================================================================
// 页面初始化
// ================================================================
function initOperationMaintenancePage() {
    _omModuleRight = getRoleModuleRight(
        context + '/roleManagerController/getRoleModuleRight',
        'OperationMaintenance'
    );
    if (!_omModuleRight) {
        _omModuleRight = {};
    }

    _omModuleRight.viewFlag = parseInt(_omModuleRight.viewFlag) || 0;
    _omModuleRight.editFlag = parseInt(_omModuleRight.editFlag) || 0;
    _omModuleRight.controlFlag = parseInt(_omModuleRight.controlFlag) || 0;

    initOmTabTitles();
    initOmPanelTitles();
    initOmFieldSetLegends();
    initOmLabels();
    initOmButtons();
    initOmCombosEmptyText();
    initOmControlsData();
    initOmDeviceTagPage();
    updateOmBtnStatus();

    initOmMessageListener();

    loadOmLoginLanguageList(function () {
        loadOemOperationConfigInfo();
    });

    setTimeout(function () {
        isInitializing = false;
    }, 100);
}

//================================================================
//监听主界面消息
//================================================================
function initOmMessageListener() {
    window.addEventListener('message', function (event) {
        var message = event.data;
        if (!message || !message.action) return;
        switch (message.action) {
            case 'refresh':
                var mainTabs = mini.get('omMainTabs');
                if (!mainTabs) return;
                var activeTab = mainTabs.getActiveTab();
                if (!activeTab) return;

                if (activeTab.name === 'lowerComputer') {
                    onOmLowerComputerRefresh();
                }
                break;
        }
    });
}

// ================================================================
// 加载登录界面语言下拉框数据
// ================================================================
function loadOmLoginLanguageList(callback) {
    var combo = mini.get('om_loginLanguage');
    if (!combo) {
        if (callback) callback();
        return;
    }
    $.ajax({
        url: context + '/userManagerController/loadLanguageNameList',
        type: 'POST',
        dataType: 'json',
        success: function (result) {
            var list = [];
            var arr = (result && result.list) ? result.list : (Array.isArray(result) ? result : []);
            if (arr && arr.length) {
                for (var i = 0; i < arr.length; i++) {
                    list.push({
                        boxkey: arr[i].boxkey,
                        boxval: arr[i].boxval
                    });
                }
            }
            combo.setData(list);
            if (callback) callback();
        },
        error: function () {
            if (callback) callback();
        }
    });
}

// ================================================================
// 加载自动化运维管理配置
// ================================================================
function loadOemOperationConfigInfo() {
    var maskEl = 'omBasicPanel';
    mini.mask({
        el: maskEl,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });

    $.ajax({
        url: context + '/operationMaintenanceController/loadOemConfigInfo',
        type: 'POST',
        dataType: 'json',
        success: function (data) {
            mini.unmask(maskEl);
            if (data && data.success) {
                initOemOperationConfigInfo(data.configFile);
            } else {
                mini.alert(_loginUserLanguageResource.requestFailed, _loginUserLanguageResource.tip);
            }
        },
        error: function () {
            mini.unmask(maskEl);
            mini.alert(_loginUserLanguageResource.requestFailed, _loginUserLanguageResource.tip);
        }
    });
}

// ================================================================
// 回填自动化运维管理配置
// ================================================================
function initOemOperationConfigInfo(configFile) {
    if (!configFile) return;

    var others = configFile.others || {};
    var dbMain = configFile.databaseMaintenance || {};
    var tableConfig = dbMain.tableConfig || {};
    var dataVacuate = configFile.dataVacuate || {};
    var report = configFile.report || {};

    var combo = mini.get('om_loginLanguage');
    if (combo) {
        combo.setValue(others.loginLanguage);
    }

    setOmCheckboxValue('om_showLogo', others.showLogo);
    setOmCheckboxValue('om_printLog', others.printLog);
    setOmCheckboxValue('om_printAdLog', others.printAdLog);
    setOmCheckboxValue('om_printExceptionLog', others.printExceptionLog);
    setOmCheckboxValue('om_simulateAcqEnable', others.simulateAcqEnable);

    var rb = mini.get('om_timeEfficiencyUnit');
    if (rb) {
        rb.setValue(others.timeEfficiencyUnit == 1 ? 1 : 2);
    }

    setOmControlValue('om_resourceMonitoringSaveData', others.resourceMonitoringSaveData);
    setOmControlValue('om_exportLimit', others.exportLimit);
    setOmControlValue('om_sendCycle', others.sendCycle);

    setOmControlValue('om_databaseMaintenanceCycle', dbMain.cycle);
    setOmControlValue('om_databaseMaintenanceStartTime', dbMain.startTime);
    setOmControlValue('om_databaseMaintenanceEndTime', dbMain.endTime);

    setOmCheckboxValue('om_acqdata_hist_enabled', tableConfig.acqdata_hist && tableConfig.acqdata_hist.enabled);
    setOmControlValue('om_acqdata_hist_retentionTime', tableConfig.acqdata_hist && tableConfig.acqdata_hist.retentionTime);

    setOmCheckboxValue('om_acqrawdata_enabled', tableConfig.acqrawdata && tableConfig.acqrawdata.enabled);
    setOmControlValue('om_acqrawdata_retentionTime', tableConfig.acqrawdata && tableConfig.acqrawdata.retentionTime);

    setOmCheckboxValue('om_alarminfo_hist_enabled', tableConfig.alarminfo_hist && tableConfig.alarminfo_hist.enabled);
    setOmControlValue('om_alarminfo_hist_retentionTime', tableConfig.alarminfo_hist && tableConfig.alarminfo_hist.retentionTime);

    setOmCheckboxValue('om_dailytotalcalculate_hist_enabled', tableConfig.dailytotalcalculate_hist && tableConfig.dailytotalcalculate_hist.enabled);
    setOmControlValue('om_dailytotalcalculate_hist_retentionTime', tableConfig.dailytotalcalculate_hist && tableConfig.dailytotalcalculate_hist.retentionTime);

    setOmCheckboxValue('om_dailycalculationdata_enabled', tableConfig.dailycalculationdata && tableConfig.dailycalculationdata.enabled);
    setOmControlValue('om_dailycalculationdata_retentionTime', tableConfig.dailycalculationdata && tableConfig.dailycalculationdata.retentionTime);

    setOmCheckboxValue('om_timingcalculationdata_enabled', tableConfig.timingcalculationdata && tableConfig.timingcalculationdata.enabled);
    setOmControlValue('om_timingcalculationdata_retentionTime', tableConfig.timingcalculationdata && tableConfig.timingcalculationdata.retentionTime);

    setOmCheckboxValue('om_timingrecorddata_enabled', tableConfig.timingrecorddata && tableConfig.timingrecorddata.enabled);
    setOmControlValue('om_timingrecorddata_retentionTime', tableConfig.timingrecorddata && tableConfig.timingrecorddata.retentionTime);

    setOmCheckboxValue('om_acqdata_vacuate_enabled', tableConfig.acqdata_vacuate && tableConfig.acqdata_vacuate.enabled);
    setOmControlValue('om_acqdata_vacuate_retentionTime', tableConfig.acqdata_vacuate && tableConfig.acqdata_vacuate.retentionTime);

    setOmControlValue('om_vacuateRecord', dataVacuate.vacuateRecord);
    setOmControlValue('om_vacuateSaveInterval', dataVacuate.saveInterval);
    setOmControlValue('om_vacuateSaveIntervalWaveRange', dataVacuate.saveIntervalWaveRange);
    setOmControlValue('om_vacuateThreshold', dataVacuate.vacuateThreshold);

    setOmControlValue('om_reportOffsetHour', report.offsetHour);
    setOmControlValue('om_reportInterval', report.interval);

    setOmControlEnabled('om_sendCycle', !!others.simulateAcqEnable);

    var dbMaintenanceEnable = (parseFloat(dbMain.cycle) > 0);
    var dbRelatedIds = getOmDbRelatedIds();
    for (var i = 0; i < dbRelatedIds.length; i++) {
        setOmControlEnabled(dbRelatedIds[i], dbMaintenanceEnable);
    }

    var saveBtn = mini.get('omBasicSaveBtn');
    if (saveBtn) saveBtn.setEnabled(_omModuleRight.editFlag == 1);
}

function getOmDbRelatedIds() {
    return [
        'om_databaseMaintenanceStartTime',
        'om_databaseMaintenanceEndTime',
        'om_acqdata_hist_enabled',
        'om_acqrawdata_enabled',
        'om_alarminfo_hist_enabled',
        'om_dailytotalcalculate_hist_enabled',
        'om_dailycalculationdata_enabled',
        'om_timingcalculationdata_enabled',
        'om_timingrecorddata_enabled',
        'om_acqdata_vacuate_enabled',
        'om_acqdata_hist_retentionTime',
        'om_acqrawdata_retentionTime',
        'om_alarminfo_hist_retentionTime',
        'om_dailytotalcalculate_hist_retentionTime',
        'om_dailycalculationdata_retentionTime',
        'om_timingcalculationdata_retentionTime',
        'om_timingrecorddata_retentionTime',
        'om_acqdata_vacuate_retentionTime'
    ];
}

// ================================================================
// 辅助函数
// ================================================================
function setOmCheckboxValue(id, value) {
    var ctrl = mini.get(id);
    if (!ctrl) return;
    var boolVal = (value === true || value === 1 || value === '1' || value === 'true');
    ctrl.setValue(boolVal);
}

function setOmControlValue(id, value) {
    var ctrl = mini.get(id);
    if (!ctrl) return;
    if (value === undefined || value === null) {
        ctrl.setValue('');
        return;
    }
    ctrl.setValue(value);
}

function setOmControlEnabled(id, enabled) {
    var ctrl = mini.get(id);
    if (!ctrl) return;
    ctrl.setEnabled(!!enabled);
}

// ================================================================
// 初始化 Tab 标题（国际化）
// ================================================================
function initOmTabTitles() {
    var R = _loginUserLanguageResource;

    var mainTabs = mini.get('omMainTabs');
    if (mainTabs) {
        var tabs = mainTabs.getTabs();
        if (tabs && tabs.length >= 6) {
            mainTabs.updateTab(tabs[0], {
                title: R.automatedOperationsManagement
            });
            mainTabs.updateTab(tabs[1], {
                title: R.backupAndRecovery
            });
            mainTabs.updateTab(tabs[2], {
                title: R.oemConfig
            });
            mainTabs.updateTab(tabs[3], {
                title: R.tagManagement
            });
            mainTabs.updateTab(tabs[4], {
                title: R.memoryCurve
            });
            mainTabs.updateTab(tabs[5], {
                title: R.lowerComputerProgramUpgrade
            });
        }
    }

    var backupTabs = mini.get('omBackupTabs');
    if (backupTabs) {
        var bTabs = backupTabs.getTabs();
        if (bTabs && bTabs.length >= 2) {
            backupTabs.updateTab(bTabs[0], {
                title: R.exportData
            });
            backupTabs.updateTab(bTabs[1], {
                title: R.importData
            });
        }
    }

    var tmTabs = mini.get('omTabManagerTabs');
    if (tmTabs) {
        var tTabs = tmTabs.getTabs();
        if (tTabs && tTabs.length >= 2) {
            tmTabs.updateTab(tTabs[0], {
                title: R.projectTag
            });
            tmTabs.updateTab(tTabs[1], {
                title: R.deviceTag
            });
        }
    }
}

// ================================================================
// 初始化各面板标题
// ================================================================
function initOmPanelTitles() {
    var R = _loginUserLanguageResource;

    var importListPanel = mini.get('BatchImportModulePanel_Id');
    if (importListPanel) importListPanel.setTitle(R.featureList);

    var importPanel = mini.get('OperationMaintenanceDataImportPanel_Id');
    if (importPanel) importPanel.setTitle(R.backupDataImport);

    var deviceTypePanel = mini.get('omDeviceTypeListPanel');
    if (deviceTypePanel) deviceTypePanel.setTitle(R.deviceTypeList);

    var contentConfigPanel = mini.get('omDeviceTypeContentConfigPanel');
    if (contentConfigPanel) contentConfigPanel.setTitle(R.displayContentConfig);

    var instanceListPanel = mini.get('omDeviceTabManagerPanel');
    if (instanceListPanel) instanceListPanel.setTitle(R.instanceList);

    var deviceTabContentPanel = mini.get('omDeviceTabContentConfigPanel');
    if (deviceTabContentPanel) deviceTabContentPanel.setTitle(R.displayContentConfig);
}

// ================================================================
// 初始化 FieldSet Legend
// ================================================================
function initOmFieldSetLegends() {
    var R = _loginUserLanguageResource;
    var legendMap = {
        'omLegendBasicInfo': 'basicInformation',
        'omLegendHistoricalData': 'historicalDataMaintenance',
        'omLegendDataVacuate': 'dataSparseness',
        'omLegendReportConfig': 'reportConfig',
        'omLegendProjectInfo': '项目名称及简介',
        'omLegendBackgroundIcon': '背景及图标',
        'omLegendRealtimeMonitoring': 'realtimeMonitoringModule',
        'omLegendHistoryQuery': 'historyQueryModule',
        'omLegendAlarmQuery': 'alarmQueryModule',
        'omLegendRealtimeMonitoring2': 'realtimeMonitoringModule',
        'omLegendHistoryQuery2': 'historyQueryModule',
        'omLegendPrimaryDevice': 'primaryDeviceModule'
    };
    for (var id in legendMap) {
        var el = document.getElementById(id);
        if (!el) continue;
        var key = legendMap[id];
        if (R[key]) {
            el.textContent = R[key];
        } else {
            el.textContent = '';
        }
    }
}

function initOmDeviceTagPage() {
    var R = _loginUserLanguageResource;

    // ★ 杆柱应力图显示内容：checkboxlist 的两个选项
    var chartList = mini.get('om_realtime_RodStressChart');
    if (chartList) {
        chartList.setData([
            {
                id: _OM_ROD_STRESS_KEY_MAX,
                text: R.maxRodStress
            },
            {
                id: _OM_ROD_STRESS_KEY_RANGE,
                text: R.rodStressRange
            }
        ]);
    }
}

// ================================================================
// 初始化标签
// ================================================================
function initOmLabels() {
    var R = _loginUserLanguageResource;
    var labelMap = {
        'omLblLoginLanguage': 'loginInterfaceLanguage',
        'omLblTimeEfficiencyUnit': 'timeEfficiencyUnit',
        'omLblSimulateAcqEnable': 'sendSimulationData',
        'omLblExportLimit': 'exportDataLimits',
        'omLblShowLogo': 'displayTheLogo',
        'omLblSendCycle': 'simulateDataSendingCycles',
        'omLblResourceMonitoringSaveData': 'resourceMonitoringLimit',
        'omLblPrintExceptionLog': 'printExceptionLogs',
        'omLblPrintLog': 'printLogs',
        'omLblPrintAdLog': 'printAdLogs',
        'omLblDbCycle': 'executionCycle',
        'omLblDbStartTime': 'executionTime',
        'omLblDbEndTime': 'endTime',
        'omLblAcqdataHistEnabled': 'historicalDataTable',
        'omLblAcqdataHistRetention': 'dataRetentionTime',
        'omLblAcqrawdataEnabled': 'sourceDataTable',
        'omLblAcqrawdataRetention': 'dataRetentionTime',
        'omLblAlarminfoHistEnabled': 'alarmHistoryTable',
        'omLblAlarminfoHistRetention': 'dataRetentionTime',
        'omLblDailyTotalEnabled': 'dailyTotalCalculateTable',
        'omLblDailyTotalRetention': 'dataRetentionTime',
        'omLblDailyCalcEnabled': 'dailyCalculationTable',
        'omLblDailyCalcRetention': 'dataRetentionTime',
        'omLblTimingCalcEnabled': 'timingCalculationTable',
        'omLblTimingCalcRetention': 'dataRetentionTime',
        'omLblTimingRecordEnabled': 'timingRecordTable',
        'omLblTimingRecordRetention': 'dataRetentionTime',
        'omLblAcqdataVacuateEnabled': 'acqdataVacuateTable',
        'omLblAcqdataVacuateRetention': 'dataRetentionTime',
        'omLblVacuateRecord': 'sparseRecordCount',
        'omLblVacuateSaveIntervalWaveRange': 'vacuateSaveIntervalWaveRange',
        'omLblVacuateSaveInterval': 'vacuateSaveInterval',
        'omLblVacuateThreshold': 'vacuateThreshold',
        'omLblReportOffsetHour': 'offsetTime',
        'omLblReportInterval': 'deviceHourlyReportInterval',
        'omLblProjectName': '项目名称',
        'omLblProjectProfile': '项目简介',
        'omLblProjectLogo': '项目logo',
        'omLblProjectFavicon': '网页logo',
        'omLblLoginBackgroundImage': '登录界面背景图',
        'omLblHelpButtonIcon': '帮助按钮图标',
        'omLblExitButtonIcon': '退出按钮图标',
        'omLblSwitchButtonIcon': '语言切换按钮图标',
        'omLblSwitchDisabledButtonIcon': '禁用语言切换按钮图标',
        'omLblFESDiagramStatPie': 'FESDiagramStatPie',
        'omLblCommStatusStatPie': 'CommStatusStatPie',
        'omLblRunStatusStatPie': 'RunStatusStatPie',
        'omLblNumStatusStatPie': 'NumStatusStatPie',
        'omLblHistoryFESDiagramStatPie': 'FESDiagramStatPie',
        'omLblHistoryCommStatusStatPie': 'CommStatusStatPie',
        'omLblHistoryRunStatusStatPie': 'RunStatusStatPie',
        'omLblHistoryNumStatusStatPie': 'NumStatusStatPie',
        'omLblAlarmFESDiagramResultAlarm': 'FESDiagramResultAlarm',
        'omLblAlarmRunStatusAlarm': 'RunStatusAlarm',
        'omLblAlarmCommStatusAlarm': 'CommStatusAlarm',
        'omLblAlarmNumericValueAlarm': 'NumericValueAlarm',
        'omLblAlarmEnumValueAlarm': 'EnumValueAlarm',
        'omLblAlarmSwitchingValueAlarm': 'SwitchingValueAlarm',
        'omLblWellboreAnalysis': 'wellboreAnalysis',
        'omLblSurfaceAnalysis': 'surfaceAnalysis',
        'omLblTrendCurve': 'trendCurve',
        'omLblDynamicData': 'dynamicData',
        'omLblDeviceControl': 'deviceControl',
        'omLblDeviceInformation': 'deviceInformation',
        'omLblRodStressChartDisplay': 'rodStressChartDisplayContent',
        'omLblRodStressChartMax': 'maxRodStress',
        'omLblRodStressChartRange': 'rodStressRange',
        'omLblHistoryTrendCurve': 'trendCurve',
        'omLblHistoryTiledDiagram': 'tiledDiagram',
        'omLblHistoryDiagramOverlay': 'diagramOverlay',
        'omLblAdditionalInformation': 'additionalInformation',
        'omLblAuxiliaryDevice': 'auxiliaryDevice',
        'omLblVideoConfig': 'videoConfig',
        'omLblCalculateDataConfig': 'calculateDataConfig',
        'omLblFSDiagramConstruction': 'fsDiagramConstruction',
        'omLblSystemParameterConfiguration': 'systemParameterConfiguration',
        'omLblIntelligentFrequencyConversion': 'intelligentFrequencyConversion',
        'omLblInterlockProtection': 'interlockProtection',
        'omLblCurveRange': 'range',
        'omLblCurveTo': 'timeTo',
        'omLblLowerComputerDeviceName': 'deviceName'
    };
    for (var id in labelMap) {
        var el = document.getElementById(id);
        if (!el) continue;
        var key = labelMap[id];
        if (R[key]) {
            if (id == 'omLblSendCycle') {
                el.textContent = R[key] + '(s)：';
            } else if (id == 'omLblDbCycle' ||
                id == 'omLblAcqdataHistRetention' ||
                id == 'omLblAcqrawdataRetention' ||
                id == 'omLblAlarminfoHistRetention' ||
                id == 'omLblDailyTotalRetention' ||
                id == 'omLblDailyCalcRetention' ||
                id == 'omLblTimingCalcRetention' ||
                id == 'omLblTimingRecordRetention' ||
                id == 'omLblAcqdataVacuateRetention') {
                el.textContent = R[key] + '(' + R.day + '): ';
            } else if (id == 'omLblVacuateSaveInterval' || id == 'omLblVacuateSaveIntervalWaveRange') {
                el.textContent = R[key] + '(' + R.minute + '): ';
            } else if (id == 'omLblVacuateThreshold') {
                el.textContent = R[key] + '(' + R.recordsGreaterThan + '): ';
            } else if (id == 'omLblReportOffsetHour' || id == 'omLblReportInterval') {
                el.textContent = R[key] + '(' + R.hour + '): ';
            } else {
                el.textContent = R[key] + '：';
            }
        } else {
            el.textContent = '：';
        }
    }
}

// ================================================================
// 初始化按钮文本
// ================================================================
function initOmButtons() {
    var R = _loginUserLanguageResource;
    var btnMap = {
        'omBasicRefreshBtn': 'refresh',
        'omBasicSaveBtn': 'save',
        'omBackupSelectAllBtn': 'selectAll',
        'omBackupDeselectAllBtn': 'deselectAll',
        'omOneKeyBackupBtn': 'exportData',
        'omImportPrevBtn': 'previousStep',
        'omImportSaveBtn': 'save',
        'omImportNextBtn': 'nextStep',
        'omOemSaveBtn': 'save',
        'omProjectTagRefreshBtn': 'refresh',
        'omProjectTagSaveBtn': 'save',
        'omDeviceTagRefreshBtn': 'refresh',
        'omDeviceTagAddBtn': 'add',
        'omDeviceTagDelBtn': 'deleteData',
        'omDeviceTagSaveBtn': 'save',
        'omCurveRefreshBtn': 'refresh',
        'omCurveSearchBtn': 'search',
        'omLowerComputerRefreshBtn': 'refresh',
        'omLowerComputerSelectAllBtn': 'selectAll',
        'omLowerComputerDeselectAllBtn': 'deselectAll',
        'omBoxUpgradeBtn': 'boxProgramUpgrade',
        'omAcUpgradeBtn': 'acProgramUpgrade',
        'omLowerComputerUplinkBtn': 'statusDetection'
    };
    for (var id in btnMap) {
        var btn = mini.get(id);
        if (btn) btn.setText(R[btnMap[id]]);
    }
}

// ================================================================
// 初始化下拉框空文本
// ================================================================
function initOmCombosEmptyText() {
    var R = _loginUserLanguageResource;

    var loginLangCombo = mini.get('om_loginLanguage');
    if (loginLangCombo) loginLangCombo.setEmptyText('--' + R.selectLanguage + '--');

    var deviceCombo = mini.get('lowerComputerProgramUpgradeDeviceListComb_Id');
    if (deviceCombo) deviceCombo.setEmptyText('--' + R.all + '--');
}

// ================================================================
// 初始化控件数据
// ================================================================
function initOmControlsData() {
    var R = _loginUserLanguageResource;

    var unitRb = mini.get('om_timeEfficiencyUnit');
    if (unitRb) {
        unitRb.setData([
            {
                id: 1,
                text: R.decimals
            },
            {
                id: 2,
                text: R.percent
            }
        ]);
        unitRb.setValue(2);
    }
}

// ================================================================
// 权限控制
// ================================================================
function updateOmBtnStatus() {
    var editFlag = (_omModuleRight.editFlag == 1);

    var btnIds = [
        'omBasicSaveBtn',
        'omBackupSelectAllBtn', 'omBackupDeselectAllBtn', 'omOneKeyBackupBtn',
        'omImportPrevBtn', 'omImportSaveBtn', 'omImportNextBtn',
        'omOemSaveBtn',
        'omProjectTagSaveBtn',
        'omDeviceTagAddBtn', 'omDeviceTagDelBtn', 'omDeviceTagSaveBtn',
        'omBoxUpgradeBtn', 'omAcUpgradeBtn', 'omLowerComputerUplinkBtn'
    ];
    for (var i = 0; i < btnIds.length; i++) {
        var btn = mini.get(btnIds[i]);
        if (btn) btn.setEnabled(editFlag);
    }
}

//================================================================
//主 Tab 切换事件
//================================================================
function onOmTabChanged(e) {
    if (isInitializing) return;
    var tab = e.tab;
    if (!tab) return;

    if (tab.name === 'backup') {
        checkAndLoadBackupTab();
    } else if (tab.name === 'tabManager') {
        checkAndLoadTabManagerTab();
    } else if (tab.name === 'lowerComputer') {
        onOmLowerComputerRefresh();
    } else if (tab.name === 'monitorCurve') {
        onOmCurveRefresh();
    }
}

function onOmBackupTabChanged(e) {
    if (isInitializing) return;
    var tab = e.tab;
    if (!tab) return;
    console.log('[运维配置] 备份 Tab 切换：', tab.name);

    if (tab.name === 'export') {
        loadOmExportGrid();
    } else if (tab.name === 'import') {
        loadOmImportGrid();
    }
}

//================================================================
//标签管理主 Tab 切换时：加载当前激活的子 Tab 对应数据
//================================================================
function checkAndLoadTabManagerTab() {
    var tmTabs = mini.get('omTabManagerTabs');
    if (!tmTabs) return;
    var activeTab = tmTabs.getActiveTab();
    if (!activeTab) return;

    if (activeTab.name === 'projectTag') {
        loadOmDeviceTypeTree();
    } else if (activeTab.name === 'deviceTag') {
        loadOmDeviceTabList();
    }
}

function checkAndLoadBackupTab() {
    var backupTabs = mini.get('omBackupTabs');
    if (!backupTabs) return;
    var activeTab = backupTabs.getActiveTab();
    if (!activeTab) return;
    if (activeTab.name === 'export') {
        loadOmExportGrid();
    } else if (activeTab.name === 'import') {
        loadOmImportGrid();
    }
}

function onOmTabManagerTabChanged(e) {
    if (isInitializing) return;
    var tab = e.tab;
    if (!tab) return;
    console.log('[运维配置] 标签管理 Tab 切换：', tab.name);

    if (tab.name === 'projectTag') {
        loadOmDeviceTypeTree();
    } else if (tab.name === 'deviceTag') {
        loadOmDeviceTabList();
    }
}

// ================================================================
// 自动化运维管理 - 事件
// ================================================================
function onOmBasicRefresh() {
    loadOemOperationConfigInfo();
}

function onOmDbCycleChange(e) {
    var cycle = parseFloat(e.value);
    var dbMaintenanceEnable = cycle > 0;
    var dbRelatedIds = getOmDbRelatedIds();
    for (var i = 0; i < dbRelatedIds.length; i++) {
        setOmControlEnabled(dbRelatedIds[i], dbMaintenanceEnable);
    }
}

function onOmSimulateAcqChange(e) {
    setOmControlEnabled('om_sendCycle', !!e.value);
}

function onOmBasicSave() {
    if (parseInt(_omModuleRight.editFlag) != 1) return;

    var R = _loginUserLanguageResource;

    var configFile = {};
    configFile.others = {
        loginLanguage: getOmValue('om_loginLanguage'),
        showLogo: getOmCheckboxValue('om_showLogo'),
        printLog: getOmCheckboxValue('om_printLog'),
        printAdLog: getOmCheckboxValue('om_printAdLog'),
        printExceptionLog: getOmCheckboxValue('om_printExceptionLog'),
        timeEfficiencyUnit: getOmValue('om_timeEfficiencyUnit') == 1 ? 1 : 2,
        resourceMonitoringSaveData: getOmValue('om_resourceMonitoringSaveData'),
        exportLimit: getOmValue('om_exportLimit'),
        simulateAcqEnable: getOmCheckboxValue('om_simulateAcqEnable'),
        sendCycle: getOmValue('om_sendCycle')
    };

    configFile.databaseMaintenance = {
        cycle: getOmValue('om_databaseMaintenanceCycle'),
        startTime: getOmValue('om_databaseMaintenanceStartTime'),
        endTime: getOmValue('om_databaseMaintenanceEndTime'),
        tableConfig: {
            acqdata_hist: {
                enabled: getOmCheckboxValue('om_acqdata_hist_enabled'),
                retentionTime: getOmValue('om_acqdata_hist_retentionTime')
            },
            acqrawdata: {
                enabled: getOmCheckboxValue('om_acqrawdata_enabled'),
                retentionTime: getOmValue('om_acqrawdata_retentionTime')
            },
            alarminfo_hist: {
                enabled: getOmCheckboxValue('om_alarminfo_hist_enabled'),
                retentionTime: getOmValue('om_alarminfo_hist_retentionTime')
            },
            dailytotalcalculate_hist: {
                enabled: getOmCheckboxValue('om_dailytotalcalculate_hist_enabled'),
                retentionTime: getOmValue('om_dailytotalcalculate_hist_retentionTime')
            },
            dailycalculationdata: {
                enabled: getOmCheckboxValue('om_dailycalculationdata_enabled'),
                retentionTime: getOmValue('om_dailycalculationdata_retentionTime')
            },
            timingcalculationdata: {
                enabled: getOmCheckboxValue('om_timingcalculationdata_enabled'),
                retentionTime: getOmValue('om_timingcalculationdata_retentionTime')
            },
            timingrecorddata: {
                enabled: getOmCheckboxValue('om_timingrecorddata_enabled'),
                retentionTime: getOmValue('om_timingrecorddata_retentionTime')
            },
            acqdata_vacuate: {
                enabled: getOmCheckboxValue('om_acqdata_vacuate_enabled'),
                retentionTime: getOmValue('om_acqdata_vacuate_retentionTime')
            }
        }
    };

    configFile.dataVacuate = {
        vacuateRecord: getOmValue('om_vacuateRecord'),
        saveInterval: getOmValue('om_vacuateSaveInterval'),
        saveIntervalWaveRange: getOmValue('om_vacuateSaveIntervalWaveRange'),
        vacuateThreshold: getOmValue('om_vacuateThreshold')
    };

    configFile.report = {
        offsetHour: getOmValue('om_reportOffsetHour'),
        interval: getOmValue('om_reportInterval')
    };

    var maskEl = 'omBasicPanel';
    mini.mask({
        el: maskEl,
        cls: 'mini-mask-loading',
        html: R.submittingData
    });

    $.ajax({
        url: context + '/operationMaintenanceController/updateOemConfigInfo',
        type: 'POST',
        data: {
            configFile: JSON.stringify(configFile)
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask(maskEl);
            if (result && result.msg === true) {
                mini.alert('<font color="blue">' + R.savedSuccessfully + '</font>', R.tip, function () {
                    loadOemOperationConfigInfo();
                });
            } else {
                mini.alert('<font color="red">' + R.saveFailed + '</font>', R.tip);
            }
        },
        error: function () {
            mini.unmask(maskEl);
            mini.alert('【<font color="red">' + R.exceptionThrow + '</font>】：' + R.contactAdmin, R.tip);
        }
    });
}

function getOmValue(id) {
    var ctrl = mini.get(id);
    if (!ctrl) return '';
    var v = ctrl.getValue();
    return (v === undefined || v === null) ? '' : v;
}

function getOmCheckboxValue(id) {
    var ctrl = mini.get(id);
    if (!ctrl) return false;
    return !!ctrl.getValue();
}

// ================================================================
// 备份与恢复 — 导出数据表格
// ================================================================
function loadOmExportGrid() {
    var grid = mini.get('BatchExportModuleGridPanel_Id');
    if (!grid) return;
    if (!grid.getUrl()) {
        grid.setUrl(context + '/operationMaintenanceController/batchExportModuleList');
    }
    grid.load();
}

function onOmExportGridBeforeLoad(e) {
    // 如有额外参数可在此添加
}

function onOmExportGridLoad(e) {
    var grid = e.sender;

    if (!grid._columnsCreated) {
        var columns = [];
        if (_omModuleRight.editFlag == 1) {
            columns.push({
                type: 'checkcolumn',
                width: 40,
                header: '',
                headerAlign: 'center',
                align: 'center'
            });
        }
        columns.push({
            type: 'indexcolumn',
            width: 50,
            header: _loginUserLanguageResource.idx,
            headerAlign: 'center',
            align: 'center'
        });
        columns.push({
            field: 'text',
            header: _loginUserLanguageResource.moduleName,
            headerAlign: 'center',
            align: 'center',
            width: 'auto'
        });
        grid.setColumns(columns);
        grid._columnsCreated = true;
    }
}

// ================================================================
// 导出数据 — 全选 / 取消全选 / 一键导出
// ================================================================
function onOmBackupSelectAll() {
    var grid = mini.get('BatchExportModuleGridPanel_Id');
    if (!grid) return;
    grid.selectAll(false);
}

function onOmBackupDeselectAll() {
    var grid = mini.get('BatchExportModuleGridPanel_Id');
    if (!grid) return;
    grid.deselectAll(false);
}

function onOmOneKeyBackup() {
    var grid = mini.get('BatchExportModuleGridPanel_Id');
    if (!grid) return;

    var selectedRows = grid.getSelecteds();
    var selectedCodes = [];
    for (var i = 0; i < selectedRows.length; i++) {
        var row = selectedRows[i];
        if (!row.disabled && row.code) {
            selectedCodes.push(row.code);
        }
    }

    if (selectedCodes.length === 0) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }

    for (var j = 0; j < selectedCodes.length; j++) {
        (function (code, index) {
            setTimeout(function () {
                doExportByCode(code);
            }, 1000 * index);
        })(selectedCodes[j], j);
    }
}

function doExportByCode(code) {
    var config = getOmExportConfig(code);
    if (!config) {
        console.warn('[运维配置] 未知的导出模块代码:', code);
        return;
    }
    commonExport(config.url, config.fileName, config.keyPrefix);
}

function getOmExportConfig(code) {
    var R = _loginUserLanguageResource;
    var map = {
        'MODULE': {
            url: context + '/moduleManagerController/exportModuleCompleteData',
            fileName: R.moduleExportFileName,
            keyPrefix: 'exportModuleCompleteData'
        },
        'DATADICTIONARY': {
            url: context + '/systemdataInfoController/exportDataDictionaryCompleteData',
            fileName: R.dataDictionaryExportFileName,
            keyPrefix: 'exportDataDictionaryCompleteData'
        },
        'ORGANIZATION': {
            url: context + '/orgManagerController/exportOrganizationCompleteData',
            fileName: R.organizationExportFileName,
            keyPrefix: 'exportOrganizationCompleteData'
        },
        'ROLE': {
            url: context + '/roleManagerController/exportRoleCompleteData',
            fileName: R.roleExportFileName,
            keyPrefix: 'exportRoleCompleteData'
        },
        'USER': {
            url: context + '/userManagerController/exportUserCompleteData',
            fileName: R.userExportFileName,
            keyPrefix: 'exportUserCompleteData'
        },
        'AUXILIARYDEVICE': {
            url: context + '/wellInformationManagerController/exportAuxiliaryDeviceCompleteData',
            fileName: R.auxiliaryDdeviceExportFileName,
            keyPrefix: 'exportAuxiliaryDeviceBackupData'
        },
        'PRIMARYDEVICE': {
            url: context + '/wellInformationManagerController/exportDeviceCompleteData',
            fileName: R.primaryDdeviceExportFileName,
            keyPrefix: 'exportPrimaryDeviceBackupData'
        },
        'PROTOCOL': {
            url: context + '/acquisitionUnitManagerController/exportAllProtocolData',
            fileName: R.exportProtocol,
            keyPrefix: 'exportProtocolBackupData'
        },
        'ACQUNIT': {
            url: context + '/acquisitionUnitManagerController/exportAllProtocolAcqUnitData',
            fileName: R.exportAcqUnit,
            keyPrefix: 'exportAllProtocolAcqUnitData'
        },
        'DISPLAYUNIT': {
            url: context + '/acquisitionUnitManagerController/exportAllProtocolDisplayUnitData',
            fileName: R.exportDisplayUnit,
            keyPrefix: 'exportAllProtocolDisplayUnitData'
        },
        'ALARMUNIT': {
            url: context + '/acquisitionUnitManagerController/exportAllProtocolAlarmUnitData',
            fileName: R.exportAlarmUnit,
            keyPrefix: 'exportAllProtocolAlarmUnitData'
        },
        'REPORTUNIT': {
            url: context + '/acquisitionUnitManagerController/exportAllReportUnitData',
            fileName: R.exportReportUnit,
            keyPrefix: 'exportAllReportUnitData'
        },
        'ACQINSTANCE': {
            url: context + '/acquisitionUnitManagerController/exportAllProtocolAcqInstanceData',
            fileName: R.exportAcqInstance,
            keyPrefix: 'exportAllProtocolAcqInstanceData'
        },
        'DISPLAYINSTANCE': {
            url: context + '/acquisitionUnitManagerController/exportAllProtocolDisplayInstanceData',
            fileName: R.exportDisplayInstance,
            keyPrefix: 'exportAllProtocolDisplayInstanceData'
        },
        'ALARMINSTANCE': {
            url: context + '/acquisitionUnitManagerController/exportAllProtocolAlarmInstanceData',
            fileName: R.exportAlarmInstance,
            keyPrefix: 'exportAllProtocolAlarmInstanceData'
        },
        'REPORTINSTANCE': {
            url: context + '/acquisitionUnitManagerController/exportAllReportInstanceData',
            fileName: R.exportReportInstance,
            keyPrefix: 'exportReportInstanceBackupData'
        }
    };
    return map[String(code).toUpperCase()] || null;
}

function commonExport(url, fileName, keyPrefix) {
    var timestamp = new Date().getTime();
    var key = keyPrefix + '_' + timestamp;
    var maskPanelId = 'BatchExportModuleGridPanel_Id';

    var param = "&recordCount=10000" +
        "&fileName=" + URLencode(URLencode(fileName)) +
        '&key=' + key;

    exportDataMask(key, maskPanelId, _loginUserLanguageResource.loadingData);
    downloadFile(url + '?flag=true' + param);
}

// ================================================================
// 导入数据 — 功能列表
// ================================================================
function loadOmImportGrid() {
    var grid = mini.get('BatchImportModuleGridPanel_Id');
    if (!grid) return;
    if (!grid.getUrl()) {
        grid.setUrl(context + '/operationMaintenanceController/batchExportModuleList');
    }
    grid.load();
}

function onOmImportGridBeforeLoad(e) {
    // 无额外参数
}

function onOmImportGridLoad(e) {
    var grid = e.sender;
    if (!grid._columnsCreated) {
        var columns = [
            {
                type: 'indexcolumn',
                width: 50,
                header: _loginUserLanguageResource.idx,
                headerAlign: 'center',
                align: 'center'
            },
            {
                field: 'text',
                header: _loginUserLanguageResource.data,
                headerAlign: 'center',
                align: 'center',
                width: 'auto',
                renderer: function (e) {
                    var value = e.value;
                    if (e.record.disabled) {
                        return '<span style="color:gray;">' + (value || '') + '</span>';
                    }
                    if (value) {
                        return '<span title="' + String(value).replace(/"/g, '&quot;') + '">' +
                            String(value).replace(/"/g, '&quot;') + '</span>';
                    }
                    return '';
                }
            }
        ];
        grid.setColumns(columns);
        grid._columnsCreated = true;
    }

    var data = grid.getData();
    if (data.length > 0) {
        grid.deselectAll();
        grid.select(data[0]);
    } else {
        updateOmImportUI(null, -1);
    }
}

function onOmImportGridSelect(e) {
    var grid = e.sender;
    var record = e.record;
    if (!record) return;
    var index = grid.indexOf(record);
    updateOmImportUI(record, index);
}

function updateOmImportUI(record, index) {
    var grid = mini.get('BatchImportModuleGridPanel_Id');
    var total = grid ? grid.getData().length : 0;

    var rowHidden = mini.get('BatchExportModuleDataListSelectRow_Id');
    var codeHidden = mini.get('BatchExportModuleDataListSelectCode_Id');
    var importPanel = mini.get('OperationMaintenanceDataImportPanel_Id');
    var prevBtn = mini.get('omImportPrevBtn');
    var nextBtn = mini.get('omImportNextBtn');
    var saveBtn = mini.get('omImportSaveBtn');
    var $form = $('#omImportForm');
    var tipLabel = document.getElementById('OperationMaintenanceImportDataTipLabel_Id');

    if (record) {
        var titleText = '【<font color="red">' + (record.text || '') + '</font>】' +
            _loginUserLanguageResource.importData + '&nbsp;';
        if (importPanel) importPanel.setTitle(titleText);
    } else {
        if (importPanel) importPanel.setTitle(_loginUserLanguageResource.importData);
    }

    if (index === 0) {
        if (prevBtn) prevBtn.setEnabled(false);
        if (nextBtn) nextBtn.setEnabled(total > 1);
    } else if (index === total - 1) {
        if (prevBtn) prevBtn.setEnabled(total > 1);
        if (nextBtn) nextBtn.setEnabled(false);
    } else if (index >= 0) {
        if (prevBtn) prevBtn.setEnabled(true);
        if (nextBtn) nextBtn.setEnabled(true);
    } else {
        if (prevBtn) prevBtn.setEnabled(false);
        if (nextBtn) nextBtn.setEnabled(false);
    }

    if (saveBtn) saveBtn.setEnabled(false);

    // 切换模块时销毁旧预览 + 清空文件输入
    _omImportPreview.destroy();
    resetOmImportFile();

    if (rowHidden) rowHidden.setValue(index);
    if (codeHidden) codeHidden.setValue(record ? record.code : '');

    if (record) {
        importedFilePermissionVerification(record.code, function (verification) {
            if (verification.sign) {
                if (tipLabel) {
                    tipLabel.innerHTML = '';
                    tipLabel.style.display = 'none';
                }
                $form.prop('disabled', false);
                if (record.disabled) {
                    $form.prop('disabled', true);
                }
            } else {
                if (tipLabel) {
                    tipLabel.innerHTML = '【<font color="red">' + verification.info + '</font>】';
                    tipLabel.style.display = '';
                }
                $form.prop('disabled', true);
            }
            if (record.disabled) {
                $form.prop('disabled', true);
            }
        });
    } else {
        if (tipLabel) {
            tipLabel.innerHTML = '';
            tipLabel.style.display = 'none';
        }
        $form.prop('disabled', true);
    }
}

function importedFilePermissionVerification(code, callback) {
    $.ajax({
        url: context + '/operationMaintenanceController/importedFilePermissionVerification',
        type: 'POST',
        data: {
            code: code
        },
        dataType: 'json',
        success: function (result) {
            var sign = result && result.success && result.msg;
            callback({
                sign: sign,
                info: result.msg || ''
            });
        },
        error: function () {
            callback({
                sign: false,
                info: _loginUserLanguageResource.requestFailed
            });
        }
    });
}

function onOmImportPrev() {
    var grid = mini.get('BatchImportModuleGridPanel_Id');
    if (!grid) return;
    var selected = grid.getSelected();
    if (!selected) return;
    var index = grid.indexOf(selected);
    if (index > 0) {
        grid.select(grid.getData()[index - 1]);
    }
}

function onOmImportNext() {
    var grid = mini.get('BatchImportModuleGridPanel_Id');
    if (!grid) return;
    var selected = grid.getSelected();
    if (!selected) return;
    var index = grid.indexOf(selected);
    var data = grid.getData();
    if (index < data.length - 1) {
        grid.select(data[index + 1]);
    }
}

// ================================================================
// 导入模块配置
// ================================================================
function getOmImportConfigMap() {
    var R = _loginUserLanguageResource;

    // 树形列工厂（关键：必须有 name: 'taskname'）
    function treeTextCol(header, width) {
        return {
            field: 'text',
            name: 'taskname',
            header: header,
            headerAlign: 'left',
            align: 'left',
            width: width || '30%',
            editor: null
        };
    }

    // 冲突信息列
    function collisionCol(width) {
        return {
            field: 'msg',
            header: R.collisionInfo,
            headerAlign: 'left',
            align: 'left',
            width: width || '70%',
            renderer: function (e) {
                return adviceOmImportCollisionColor(e.value, e.record);
            }
        };
    }

    // 序号列
    var indexCol = {
        type: 'indexcolumn',
        width: 50,
        header: R.idx,
        headerAlign: 'center',
        align: 'center'
    };

    // TreeGrid 通用默认配置
    var treeDefaults = {
        previewType: 'treegrid',
        treeColumn: 'taskname',
        textField: 'text',
        dataField: 'children',
        showHGridLines: false,
        showVGridLines: false
    };

    // 合并默认 + 具体配置
    function treeConfig(base) {
        var cfg = {};
        for (var k in treeDefaults) cfg[k] = treeDefaults[k];
        for (var k2 in base) cfg[k2] = base[k2];
        return cfg;
    }

    // DataGrid 通用默认配置
    var gridDefaults = {
        previewType: 'datagrid',
        idField: 'id',
        dataField: 'totalRoot',
        totalField: 'totalCount',
        showHGridLines: true,
        showVGridLines: true
    };

    function gridConfig(base) {
        var cfg = {};
        for (var k in gridDefaults) cfg[k] = gridDefaults[k];
        for (var k2 in base) cfg[k2] = base[k2];
        return cfg;
    }

    return {

        // ============================================================
        // 模块
        // ============================================================
        'MODULE': treeConfig({
            uploadUrl: '/moduleManagerController/uploadImportedModuleFile',
            previewUrl: '/moduleManagerController/getUploadedModuleTreeData',
            saveUrl: '/moduleManagerController/saveAllImportedModule',
            idField: 'mdId',
            parentField: 'mdParentid',
            columns: [
                treeTextCol(R.moduleName, '30%'),
                collisionCol('70%')
            ]
        }),

        // ============================================================
        // 数据字典
        // ============================================================
        'DATADICTIONARY': gridConfig({
            uploadUrl: '/systemdataInfoController/uploadImportedDataDictionaryFile',
            previewUrl: '/systemdataInfoController/getUploadedDataDictionaryTreeData',
            saveUrl: '/systemdataInfoController/saveAllImportedDataDictionary',
            columns: [
                indexCol,
                {
                    field: 'name',
                    header: R.dataModuleName,
                    headerAlign: 'center',
                    align: 'center',
                    width: '20%'
                },
                {
                    field: 'code',
                    header: R.dataModuleCode,
                    headerAlign: 'center',
                    align: 'center',
                    width: '30%'
                },
                collisionCol('50%')
            ]
        }),

        // ============================================================
        // 组织机构
        // ============================================================
        'ORGANIZATION': treeConfig({
            uploadUrl: '/orgManagerController/uploadImportedOrganizationFile',
            previewUrl: '/orgManagerController/getUploadedOrganizationTreeData',
            saveUrl: '/orgManagerController/saveAllImportedOrganization',
            idField: 'orgId',
            parentField: 'orgParentid',
            columns: [
                treeTextCol(R.orgName, '30%'),
                {
                    field: 'orgSeq',
                    header: R.sequenceNumber,
                    headerAlign: 'left',
                    align: 'left',
                    width: '20%'
                },
                collisionCol('50%')
            ]
        }),

        // ============================================================
        // 角色
        // ============================================================
        'ROLE': gridConfig({
            uploadUrl: '/roleManagerController/uploadImportedRoleFile',
            previewUrl: '/roleManagerController/getUploadedRoleTreeData',
            saveUrl: '/roleManagerController/saveAllImportedRole',
            columns: [
                indexCol,
                {
                    field: 'roleName_' + loginUserLanguage,
                    header: R.roleName,
                    headerAlign: 'center',
                    align: 'center',
                    width: 150
                },
                {
                    field: 'roleLevel',
                    header: R.roleLevel,
                    headerAlign: 'center',
                    align: 'center',
                    type: 'spinner',
                    width: 100
                },
                {
                    field: 'showLevel',
                    header: R.dataShowLevel,
                    headerAlign: 'center',
                    align: 'center',
                    type: 'spinner',
                    width: 120
                },
                {
                    field: 'roleVideoKeyEditName',
                    header: R.roleVideoKeyEdit,
                    headerAlign: 'center',
                    align: 'center',
                    trueValue: true,
                    falseValue: false,
                    type: 'checkboxcolumn',
                    width: 130
                },
                {
                    field: 'roleLanguageEditName',
                    header: R.roleLanguageEdit,
                    headerAlign: 'center',
                    align: 'center',
                    trueValue: true,
                    falseValue: false,
                    type: 'checkboxcolumn',
                    width: 130
                },
                {
                    field: 'remark_' + loginUserLanguage,
                    header: R.roleRemark,
                    headerAlign: 'center',
                    align: 'center',
                    width: 200
                },
                collisionCol('auto')
            ]
        }),

        // ============================================================
        // 用户
        // ============================================================
        'USER': gridConfig({
            uploadUrl: '/userManagerController/uploadImportedUserFile',
            previewUrl: '/userManagerController/getUploadedUserTreeData',
            saveUrl: '/userManagerController/saveAllImportedUser',
            columns: [
                indexCol,
                {
                    field: 'userName',
                    header: R.userName,
                    headerAlign: 'center',
                    align: 'center',
                    width: 150
                },
                {
                    field: 'userId',
                    header: R.userAccount,
                    headerAlign: 'center',
                    align: 'center',
                    width: 150
                },
                {
                    field: 'userTypeName',
                    header: R.role,
                    headerAlign: 'center',
                    align: 'center',
                    width: 120
                },
                {
                    field: 'userPhone',
                    header: R.phone,
                    headerAlign: 'center',
                    align: 'center',
                    width: 130
                },
                {
                    field: 'userInEmail',
                    header: R.email,
                    headerAlign: 'center',
                    align: 'center',
                    width: 200
                },
                {
                    field: 'userQuickLoginName',
                    header: R.userQuickLogin,
                    headerAlign: 'center',
                    align: 'center',
                    trueValue: true,
                    falseValue: false,
                    type: 'checkboxcolumn',
                    width: 100
                },
                {
                    field: 'receiveSMSName',
                    header: R.receiveSMS,
                    headerAlign: 'center',
                    align: 'center',
                    trueValue: true,
                    falseValue: false,
                    type: 'checkboxcolumn',
                    width: 100
                },
                {
                    field: 'receiveMailName',
                    header: R.receiveMail,
                    headerAlign: 'center',
                    align: 'center',
                    trueValue: true,
                    falseValue: false,
                    type: 'checkboxcolumn',
                    width: 100
                },
                {
                    field: 'userEnableName',
                    header: R.status,
                    headerAlign: 'center',
                    align: 'center',
                    trueValue: true,
                    falseValue: false,
                    type: 'checkboxcolumn',
                    width: 100
                },
                collisionCol(200)
            ]
        }),

        // ============================================================
        // 辅助设备
        // ============================================================
        'AUXILIARYDEVICE': gridConfig({
            uploadUrl: '/wellInformationManagerController/uploadAuxiliaryDeviceBackupData',
            previewUrl: '/wellInformationManagerController/getUploadedAuxiliaryDeviceTreeData',
            saveUrl: '/wellInformationManagerController/saveAuxiliaryDeviceBackupData',
            columns: [
                indexCol,
                {
                    field: 'name',
                    header: R.deviceName,
                    headerAlign: 'center',
                    align: 'center',
                    width: 150
                },
                {
                    field: 'manufacturer',
                    header: R.manufacturer,
                    headerAlign: 'center',
                    align: 'center',
                    width: 150
                },
                {
                    field: 'model',
                    header: R.model,
                    headerAlign: 'center',
                    align: 'center',
                    width: 150
                },
                {
                    field: 'remark',
                    header: R.remark,
                    headerAlign: 'center',
                    align: 'center',
                    width: 200
                },
                {
                    field: 'sort',
                    header: R.sequenceNumber,
                    headerAlign: 'center',
                    align: 'center',
                    width: 80
                },
                collisionCol('auto')
            ]
        }),

        // ============================================================
        // 主设备
        // ============================================================
        'PRIMARYDEVICE': gridConfig({
            uploadUrl: '/wellInformationManagerController/uploadPrimaryDeviceBackupData',
            previewUrl: '/wellInformationManagerController/getUploadedPrimaryDeviceTreeData',
            saveUrl: '/wellInformationManagerController/savePrimaryDeviceBackupData',
            columns: [
                indexCol,
                {
                    field: 'deviceName',
                    header: R.deviceName,
                    headerAlign: 'center',
                    align: 'center',
                    width: 140
                },
                {
                    field: 'instanceName',
                    header: R.acqInstance,
                    headerAlign: 'center',
                    align: 'center',
                    width: 130
                },
                {
                    field: 'displayInstanceName',
                    header: R.displayInstance,
                    headerAlign: 'center',
                    align: 'center',
                    width: 130
                },
                {
                    field: 'alarmInstanceName',
                    header: R.alarmInstance,
                    headerAlign: 'center',
                    align: 'center',
                    width: 130
                },
                {
                    field: 'reportInstanceName',
                    header: R.reportInstance,
                    headerAlign: 'center',
                    align: 'center',
                    width: 130
                },
                {
                    field: 'tcpType',
                    header: R.deviceTcpType,
                    headerAlign: 'center',
                    align: 'center',
                    width: 100
                },
                {
                    field: 'signInId',
                    header: R.signInId,
                    headerAlign: 'center',
                    align: 'center',
                    width: 100
                },
                {
                    field: 'ipPort',
                    header: R.ipPort,
                    headerAlign: 'center',
                    align: 'center',
                    width: 130
                },
                {
                    field: 'slave',
                    header: R.slave,
                    headerAlign: 'center',
                    align: 'center',
                    width: 60
                },
                {
                    field: 'peakDelay',
                    header: R.peakDelay,
                    headerAlign: 'center',
                    align: 'center',
                    width: 80
                },
                {
                    field: 'sortNum',
                    header: R.sequenceNumber,
                    headerAlign: 'center',
                    align: 'center',
                    width: 80
                },
                collisionCol(300)
            ]
        }),

        // ============================================================
        // 协议 / 采集单元 / 显示单元 / 报警单元 / 报表单元
        // 采集实例 / 显示实例 / 报警实例 / 报表实例
        // ============================================================
        'PROTOCOL': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedProtocolFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedProtocolTreeData',
            saveUrl: '/acquisitionUnitManagerController/saveProtocolBackupData',
            idField: 'id',
            parentField: 'pid',
            columns: [treeTextCol(R.protocolList, '30%'), collisionCol('70%')]
        }),
        'ACQUNIT': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedAcqUnitFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedAcqUnitTreeData',
            saveUrl: '/acquisitionUnitManagerController/saveAcqUnitBackupData',
            idField: 'id',
            parentField: 'pid',
            columns: [treeTextCol(R.unitList, '30%'), collisionCol('70%')]
        }),
        'DISPLAYUNIT': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedDisplayUnitFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedDisplayUnitTreeData',
            saveUrl: '/acquisitionUnitManagerController/saveDisplayUnitBackupData',
            idField: 'id',
            parentField: 'pid',
            columns: [treeTextCol(R.unitList, '30%'), collisionCol('70%')]
        }),
        'ALARMUNIT': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedAlarmUnitFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedAlarmUnitTreeData',
            saveUrl: '/acquisitionUnitManagerController/saveAlarmUnitBackupData',
            idField: 'id',
            parentField: 'pid',
            columns: [treeTextCol(R.unitList, '30%'), collisionCol('70%')]
        }),
        'REPORTUNIT': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedReportUnitFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedReportUnitTreeData',
            saveUrl: '/acquisitionUnitManagerController/saveReportUnitBackupData',
            idField: 'id',
            parentField: 'pid',
            columns: [treeTextCol(R.unitList, '30%'), collisionCol('70%')]
        }),
        'ACQINSTANCE': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedAcqInstanceFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedAcqInstanceTreeData',
            saveUrl: '/acquisitionUnitManagerController/saveAcqInstanceBackupData',
            idField: 'id',
            parentField: 'pid',
            columns: [treeTextCol(R.instanceList, '30%'), collisionCol('70%')]
        }),
        'DISPLAYINSTANCE': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedDisplayInstanceFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedDisplayInstanceTreeData',
            saveUrl: '/acquisitionUnitManagerController/saveDisplayInstanceBackupData',
            idField: 'id',
            parentField: 'pid',
            columns: [treeTextCol(R.instanceList, '30%'), collisionCol('70%')]
        }),
        'ALARMINSTANCE': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedAlarmInstanceFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedAlarmInstanceTreeData',
            saveUrl: '/acquisitionUnitManagerController/saveAlarmInstanceBackupData',
            idField: 'id',
            parentField: 'pid',
            columns: [treeTextCol(R.instanceList, '30%'), collisionCol('70%')]
        }),
        'REPORTINSTANCE': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedReportInstanceFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedReportInstanceTreeData',
            saveUrl: '/acquisitionUnitManagerController/saveReportInstanceBackupData',
            idField: 'id',
            parentField: 'pid',
            columns: [treeTextCol(R.instanceList, '30%'), collisionCol('70%')]
        })
    };
}

// 冲突信息颜色渲染
function adviceOmImportCollisionColor(val, record) {
    var saveSign = record ? record.saveSign : 0;
    var color = (saveSign == 0) ? '#000000' : '#DC2828';
    if (val) {
        return '<span style="color:' + color + ';" title="' +
            String(val).replace(/"/g, '&quot;') + '">' +
            String(val).replace(/"/g, '&quot;') + '</span>';
    }
    return '';
}

// ================================================================
// 导入数据 - 保存
// ================================================================
function onOmImportSave() {
    var code = getOmValue('BatchExportModuleDataListSelectCode_Id');
    if (!code) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }
    var config = getOmImportConfigMap()[String(code).toUpperCase()];
    if (!config) return;

    var counts = getOmImportConflictCounts();
    if (counts.overlay > 0 || counts.collision > 0) {
        var info = _loginUserLanguageResource.collisionInfo4;
        mini.confirm(info, _loginUserLanguageResource.tip, function (action) {
            if (action === 'ok') {
                doSaveOmImport(config.saveUrl, code);
            }
        });
    } else {
        doSaveOmImport(config.saveUrl, code);
    }
}

function doSaveOmImport(saveUrl, code) {
    var panelId = 'OperationMaintenanceDataImportPanel_Id';
    mini.mask({
        el: panelId,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.submittingData
    });

    $.ajax({
        url: context + saveUrl,
        type: 'POST',
        dataType: 'json',
        success: function (result) {
            mini.unmask(panelId);
            if (result && result.success === true) {
                mini.alert(_loginUserLanguageResource.savedSuccessfully, _loginUserLanguageResource.tip);
                mini.get('omImportSaveBtn').setEnabled(false);
                _omImportPreview.reload();

                // ★ 根据导入的数据类型，通知主界面刷新对应的树
                notifyMainAfterImport(code);
            } else {
                mini.alert('<font color=red>' + _loginUserLanguageResource.saveFailed + '</font>', _loginUserLanguageResource.tip);
            }
        },
        error: function () {
            mini.unmask(panelId);
            mini.alert('【<font color=red>' + _loginUserLanguageResource.exceptionThrow + '</font>】：' + _loginUserLanguageResource.contactAdmin, _loginUserLanguageResource.tip);
        }
    });
}

//================================================================
//导入成功后通知主界面刷新对应树
//- 模块（MODULE）      → 刷新功能菜单树
//- 组织（ORGANIZATION）→ 刷新组织树
//其他类型（角色/用户/设备/协议...）不需要通知主界面
//================================================================
function notifyMainAfterImport(code) {
    if (!window.parent || window.parent === window) return;

    var upperCode = String(code || '').toUpperCase();

    if (upperCode === 'MODULE') {
        // 刷新主界面顶部功能菜单树
        window.parent.postMessage({
            action: 'refreshMainMenuTree'
        }, window.location.origin);

    } else if (upperCode === 'ORGANIZATION') {
        // 刷新主界面左侧组织树
        window.parent.postMessage({
            action: 'refreshMainOrgTree',
            type: 'import'
        }, window.location.origin);
    }
}

// ================================================================
// 导入数据 - 文件选择后立即上传
// ================================================================
function onOmImportFileSelect(e) {
    var code = getOmValue('BatchExportModuleDataListSelectCode_Id');
    if (!code) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }
    var config = getOmImportConfigMap()[String(code).toUpperCase()];
    if (!config) {
        mini.alert(_loginUserLanguageResource.requestFailed, _loginUserLanguageResource.tip);
        return;
    }
    uploadOmImportFile(config);
}

function uploadOmImportFile(config) {
    var form = document.getElementById('omImportForm');
    if (!form) return;

    form.action = context + config.uploadUrl;

    var iframe = document.getElementsByName('omUploadFrame')[0];
    if (!iframe) return;

    var maskEl = 'OperationMaintenanceDataImportPanel_Id';
    mini.mask({
        el: maskEl,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.uploadingFile
    });

    iframe.onload = function () {
        mini.unmask(maskEl);
        try {
            var responseText = iframe.contentWindow.document.body.innerText;
            var result = JSON.parse(responseText);
            if (result && result.flag === true) {
                mini.alert(_loginUserLanguageResource.loadSuccessfully, _loginUserLanguageResource.tip);
                var saveBtn = mini.get('omImportSaveBtn');
                if (saveBtn) saveBtn.setEnabled(true);
                loadOmImportPreview(config);
            } else {
                mini.alert(_loginUserLanguageResource.uploadDataError, _loginUserLanguageResource.tip);
                var saveBtn2 = mini.get('omImportSaveBtn');
                if (saveBtn2) saveBtn2.setEnabled(false);
            }
        } catch (ex) {
            console.log(ex);
            mini.alert(_loginUserLanguageResource.uploadFail, _loginUserLanguageResource.tip);
        }
        iframe.onload = null;
        resetOmImportFile();
    };

    form.submit();
}

// 清空 mini-htmlfile 内部的原生 input[type=file]
function resetOmImportFile() {
    var form = document.getElementById('omImportForm');
    if (form) {
        form.reset();
    }
}

// ================================================================
// 预览表格统一封装
//   规范：
//     1. new mini.Xxx() → set(options) → render(host)
//     2. DOM 容器 id 与 MiniUI 组件 id 分离
//     3. 每次创建先销毁旧组件
//     4. 渲染时从 config 读全所有属性（idField / parentField / textField /
//        dataField / treeColumn / showHGridLines / showVGridLines ...）
// ================================================================
var _omImportPreview = (function () {

    var CONTAINER_ID = 'OperationMaintenanceDataImportContentDiv_Id';
    var HOST_ID = '_omImportPreviewHost_';
    var TREE_ID = 'OmImportPreviewTreeGrid_Id';
    var GRID_ID = 'OmImportPreviewDataGrid_Id';

    // 销毁旧组件 + 清空容器
    function _destroy() {
        var tree = mini.get(TREE_ID);
        if (tree) {
            try {
                tree.destroy();
            } catch (e) {}
        }
        var grid = mini.get(GRID_ID);
        if (grid) {
            try {
                grid.destroy();
            } catch (e) {}
        }
        var container = document.getElementById(CONTAINER_ID);
        if (container) container.innerHTML = '';
    }

    // 创建 DOM 宿主容器
    function _createHost() {
        var container = document.getElementById(CONTAINER_ID);
        if (!container) return null;
        var host = document.createElement('div');
        host.id = HOST_ID;
        host.style.width = '100%';
        host.style.height = '100%';
        container.appendChild(host);
        return host;
    }

    // 渲染 TreeGrid（从 config 读全属性）
    function _renderTreeGrid(config, host) {
        var grid = new mini.TreeGrid();

        grid.set({
            id: TREE_ID,
            style: 'width:100%;height:100%;',

            // ★ 树形相关全部从 config 读
            idField: config.idField || 'id',
            parentField: config.parentField || 'pid',
            textField: config.textField || 'text',
            dataField: config.dataField || 'children',
            treeColumn: config.treeColumn || 'taskname',

            resultAsTree: true,
            showTreeIcon: true,

            // ★ 网格线开关从 config 读
            showHGridLines: (config.showHGridLines !== undefined) ? config.showHGridLines : false,
            showVGridLines: (config.showVGridLines !== undefined) ? config.showVGridLines : false,

            allowResize: false,
            showPager: false,
            showEmptyText: true,
            autoLoad: false,

            columns: config.columns,
            url: context + config.previewUrl
        });

        grid.render(host);
        grid.load();
        return grid;
    }

    // 渲染 DataGrid（从 config 读全属性）
    function _renderDataGrid(config, host) {
        var grid = new mini.DataGrid();

        grid.set({
            id: GRID_ID,
            style: 'width:100%;height:100%;',

            idField: config.idField || 'id',
            dataField: config.dataField || 'totalRoot',
            totalField: config.totalField || 'totalCount',

            showHGridLines: (config.showHGridLines !== undefined) ? config.showHGridLines : false,
            showVGridLines: (config.showVGridLines !== undefined) ? config.showVGridLines : false,

            allowResize: false,
            allowAlternating: true,
            showPager: false,
            pageSize: 100,
            showPageInfo: false,
            multiSelect: false,
            allowCellEdit: false,
            allowCellSelect: false,
            showEmptyText: true,

            columns: config.columns,
            url: context + config.previewUrl
        });

        grid.render(host);
        grid.load();
        return grid;
    }

    return {
        show: function (config) {
            if (!config) return null;
            _destroy();
            var host = _createHost();
            if (!host) return null;
            if (config.previewType === 'treegrid') {
                return _renderTreeGrid(config, host);
            }
            return _renderDataGrid(config, host);
        },

        destroy: _destroy,

        getCurrent: function () {
            return mini.get(TREE_ID) || mini.get(GRID_ID);
        },

        reload: function () {
            var grid = this.getCurrent();
            if (grid && grid.load) grid.load();
        },

        getConflicts: function () {
            var counts = {
                overlay: 0,
                collision: 0
            };
            var grid = this.getCurrent();
            if (!grid) return counts;
            var rows = grid.getData() || [];
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].saveSign == 1) counts.overlay++;
                else if (rows[i].saveSign == 2) counts.collision++;
            }
            return counts;
        }
    };
})();

// 加载预导入数据预览（薄壳）
function loadOmImportPreview(config) {
    _omImportPreview.show(config);
}

// 获取预览中的冲突统计（薄壳）
function getOmImportConflictCounts() {
    return _omImportPreview.getConflicts();
}

//---------- 加载设备类型树 ----------
function loadOmDeviceTypeTree() {
    var tree = mini.get('deviceTypeMaintenanceTreeGridView_Id');
    if (!tree) return;
    if (!tree.getUrl()) {
        tree.setUrl(context + '/operationMaintenanceController/constructDeviceTypeTreeData');
    }
    tree.load();
}
//左侧树：加载前
function onOmDeviceTypeTreeBeforeLoad(e) {}

//左侧树：加载完成
function onOmDeviceTypeTreeLoad(e) {
    var tree = e.sender;
    var result = e.result || {};

    // ★ 缓存列显隐标志（对照 ExtJS 的 showChineseName 等）
    tree._showChineseName = (result.showChineseName === undefined) ? true : !!result.showChineseName;
    tree._showEnglishName = (result.showEnglishName === undefined) ? true : !!result.showEnglishName;
    tree._showRussianName = (result.showRussianName === undefined) ? true : !!result.showRussianName;

    // 动态创建列（只创建一次）
    if (!tree._columnsCreated) {
        createOmDeviceTypeTreeColumns(tree);
        tree._columnsCreated = true;
    }

    // 展开全部
    tree.expandAll();

    // ★ 恢复选中（对照原 ExtJS 的 selectedRow 逻辑）
    var selectNode = tree.getSelectedNode();
    if (!selectNode) {
        var targetNode = null;
        if (_selectedDeviceTypeId) {
            targetNode = tree.getNode(_selectedDeviceTypeId);
        }
        if (!targetNode) {
            targetNode = findFirstLeafDeviceTypeNode(tree, tree.getRootNode());
        }
        if (targetNode) {
            setTimeout(function () {
                tree.selectNode(targetNode);
            }, 50);
        }
    }
}

// ================================================================
// 左侧树：动态创建列
// ================================================================
function createOmDeviceTypeTreeColumns(tree) {
    var R = _loginUserLanguageResource;
    var editFlag = (_omModuleRight.editFlag == 1);

    var showChineseName = (tree._showChineseName !== false);
    var showEnglishName = (tree._showEnglishName !== false);
    var showRussianName = (tree._showRussianName !== false);

    var currentLang = (loginUserLanguage || '').toUpperCase();

    // 语言列编辑器：当前语言必填，其他语言可空（对照原 ExtJS）
    function langTextEditor(lang) {
        if (!editFlag) return null;
        return {
            type: 'textbox',
            allowBlank: (currentLang === lang) ? false : true
        };
    }
    var sortEditor = editFlag ?
        {
            type: 'spinner',
            minValue: 1,
            maxValue: 9999999999,
            allowBlank: true
        } :
        null;

    var columns = [
        {
            field: 'text',
            name: 'taskname',
            header: R.deviceType,
            headerAlign: 'left',
            align: 'left',
            width: '30%',
            editor: null
        },
        {
            field: 'text_zh_CN',
            header: R.language_zh_CN,
            headerAlign: 'left',
            align: 'left',
            width: '15%',
            visible: showChineseName,
            editor: langTextEditor('ZH_CN')
        },
        {
            field: 'text_en',
            header: R.language_en,
            headerAlign: 'left',
            align: 'left',
            width: '15%',
            visible: showEnglishName,
            editor: langTextEditor('EN')
        },
        {
            field: 'text_ru',
            header: R.language_ru,
            headerAlign: 'left',
            align: 'left',
            width: '15%',
            visible: showRussianName,
            editor: langTextEditor('RU')
        },
        {
            field: 'sortNum',
            header: R.sequenceNumber,
            headerAlign: 'center',
            align: 'center',
            width: 80,
            editor: sortEditor
        },
        {
            field: 'deviceTypeEnable',
            header: R.enable,
            type: 'checkboxcolumn',
            trueValue: true,
            falseValue: false,
            headerAlign: 'center',
            align: 'center',
            width: 80,
            editable: editFlag
        },
        {
            field: 'deviceTypeId',
            visible: false
        },
        {
            field: 'parentNodeId',
            visible: false
        }
    ];

    tree.setColumns(columns);
    tree.setAllowCellEdit(editFlag);
    tree.setAllowCellSelect(editFlag);
}

//================================================================
//左侧树：找第一个叶子节点
//================================================================
function findFirstLeafDeviceTypeNode(tree, node) {
    if (!node) return null;
    if (tree.isLeaf(node)) return node;
    if (node.children && node.children.length > 0) {
        for (var i = 0; i < node.children.length; i++) {
            var result = findFirstLeafDeviceTypeNode(tree, node.children[i]);
            if (result) return result;
        }
    }
    return null;
}

//================================================================
//左侧树：单元格开始编辑前校验
//================================================================
function onOmDeviceTypeCellBeginEdit(e) {
    if (_omModuleRight.editFlag != 1) {
        e.cancel = true;
    }
}

//================================================================
//左侧树：节点选中 → 加载右侧功能列表配置
//================================================================
function onOmDeviceTypeNodeSelect(e) {
    var node = e.node;
    if (!node) return;

    _selectedDeviceTypeId = node.deviceTypeId;
    loadOmProjectTabConfigTree();
}

//================================================================
//加载右侧功能列表配置树
//================================================================
function loadOmProjectTabConfigTree() {
    var tree = mini.get('projectTabConfigTreeGridView_Id');
    if (!tree) return;
    if (!tree.getUrl()) {
        tree.setUrl(context + '/operationMaintenanceController/loadDeviceTypeContentConfigTreeData');
    }
    tree.load();
}

//右侧树：加载前（附加 deviceTypeId，对照原 ExtJS 的 beforeload）
function onProjectTabConfigTreeBeforeLoad(e) {
    var params = e.params || {};

    // 优先用左侧树当前选中节点的 deviceTypeId
    var deviceTypeTree = mini.get('deviceTypeMaintenanceTreeGridView_Id');
    var selectedNode = deviceTypeTree ? deviceTypeTree.getSelectedNode() : null;

    params.deviceTypeId = selectedNode ? (selectedNode.deviceTypeId || 0) : 0;
    e.params = params;
}

//右侧树：加载完成
function onProjectTabConfigTreeLoad(e) {
    var tree = e.sender;
    tree.expandAll();
}

//右侧树：节点勾选前校验
function onProjectTabConfigTreeBeforeNodeCheck(e) {
    if (_omModuleRight.editFlag != 1) {
        e.cancel = true;
    }
}

function onProjectTabConfigTreeDrawnode(e) {
    if (!e.isLeaf) e.showCheckBox = false;
}


//================================================================
//项目标签：刷新
//================================================================
function onOmProjectTagRefresh() {
    loadOmDeviceTypeTree();
}
//================================================================
//项目标签：保存
//================================================================
function onOmProjectTagSave() {
    if (_omModuleRight.editFlag != 1) return;

    var deviceTypeTree = mini.get('deviceTypeMaintenanceTreeGridView_Id');
    if (!deviceTypeTree) return;

    // 先提交正在编辑的单元格
    deviceTypeTree.commitEdit();

    // ---------- 1. 收集左侧设备类型修改记录 ----------
    var modifiedRecords = deviceTypeTree.getChanges('modified', false);
    var modifiedDeviceTypes = [];
    if (modifiedRecords && modifiedRecords.length > 0) {
        for (var i = 0; i < modifiedRecords.length; i++) {
            var rec = modifiedRecords[i];
            modifiedDeviceTypes.push({
                id: rec.deviceTypeId,
                name_zh_CN: rec.text_zh_CN,
                name_en: rec.text_en,
                name_ru: rec.text_ru,
                parentId: rec.parentNodeId, // ★ 对照后端字段
                sortNum: (isNotVal(rec.sortNum) && isNumber(rec.sortNum)) ? rec.sortNum : null,
                status: isTrueVal(rec.deviceTypeEnable) ? 1 : 0
            });
        }
    }

    // ---------- 2. 收集右侧功能列表勾选的配置项 ----------
    var configTree = mini.get('projectTabConfigTreeGridView_Id');
    var contentConfig = {
        DeviceRealTimeMonitoring: {
            FESDiagramStatPie: false,
            CommStatusStatPie: false,
            RunStatusStatPie: false,
            NumStatusStatPie: false
        },
        DeviceHistoryQuery: {
            FESDiagramStatPie: false,
            CommStatusStatPie: false,
            RunStatusStatPie: false,
            NumStatusStatPie: false
        },
        AlarmQuery: {
            FESDiagramResultAlarm: false,
            RunStatusAlarm: false,
            CommStatusAlarm: false,
            NumericValueAlarm: false,
            EnumValueAlarm: false,
            SwitchingValueAlarm: false
        }
    };

    if (configTree) {
        var checkedNodes = configTree.getCheckedNodes() || [];
        for (var k = 0; k < checkedNodes.length; k++) {
            var code = (checkedNodes[k].code || '').toUpperCase();
            switch (code) {
                case 'REALTIMEMONITORINGMODULE_FESRESULTSTATISTICSPIECHART':
                    contentConfig.DeviceRealTimeMonitoring.FESDiagramStatPie = true;
                    break;
                case 'REALTIMEMONITORINGMODULE_COMMSTATUSSTATISTICSPIECHART':
                    contentConfig.DeviceRealTimeMonitoring.CommStatusStatPie = true;
                    break;
                case 'REALTIMEMONITORINGMODULE_RUNSTATUSSTATISTICSPIECHART':
                    contentConfig.DeviceRealTimeMonitoring.RunStatusStatPie = true;
                    break;
                case 'REALTIMEMONITORINGMODULE_NUMSTATUSSTATISTICSPIECHART':
                    contentConfig.DeviceRealTimeMonitoring.NumStatusStatPie = true;
                    break;

                case 'HISTORYQUERYMODULE_FESRESULTSTATISTICSPIECHART':
                    contentConfig.DeviceHistoryQuery.FESDiagramStatPie = true;
                    break;
                case 'HISTORYQUERYMODULE_COMMSTATUSSTATISTICSPIECHART':
                    contentConfig.DeviceHistoryQuery.CommStatusStatPie = true;
                    break;
                case 'HISTORYQUERYMODULE_RUNSTATUSSTATISTICSPIECHART':
                    contentConfig.DeviceHistoryQuery.RunStatusStatPie = true;
                    break;
                case 'HISTORYQUERYMODULE_NUMSTATUSSTATISTICSPIECHART':
                    contentConfig.DeviceHistoryQuery.NumStatusStatPie = true;
                    break;

                case 'ALARMQUERYMODULE_NUMERICVALUEALARM':
                    contentConfig.AlarmQuery.NumericValueAlarm = true;
                    break;
                case 'ALARMQUERYMODULE_ENUMVALUEALARM':
                    contentConfig.AlarmQuery.EnumValueAlarm = true;
                    break;
                case 'ALARMQUERYMODULE_SWITCHINGVALUEALARM':
                    contentConfig.AlarmQuery.SwitchingValueAlarm = true;
                    break;
                case 'ALARMQUERYMODULE_COMMSTATUSALARM':
                    contentConfig.AlarmQuery.CommStatusAlarm = true;
                    break;
                case 'ALARMQUERYMODULE_RUNSTATUSALARM':
                    contentConfig.AlarmQuery.RunStatusAlarm = true;
                    break;
                case 'ALARMQUERYMODULE_FESDIAGRAMRESULTALARM':
                    contentConfig.AlarmQuery.FESDiagramResultAlarm = true;
                    break;
            }
        }
    }

    // ---------- 3. 提交 ----------
    var maskEl = 'omProjectTagPanel';
    mini.mask({
        el: maskEl,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.submittingData
    });

    $.ajax({
        url: context + '/operationMaintenanceController/saveDeviceTypeMaintenanceData',
        type: 'POST',
        data: {
            data: JSON.stringify(modifiedDeviceTypes),
            selectDeviceTypeId: _selectedDeviceTypeId,
            contentConfig: JSON.stringify(contentConfig)
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask(maskEl);
            if (result && result.success === true) {
                mini.alert(_loginUserLanguageResource.savedSuccessfully, _loginUserLanguageResource.tip);
                deviceTypeTree.accept();

                // ★ 保存成功后重新加载左侧树（onload 会自动恢复选中）
                loadOmDeviceTypeTree();
            } else {
                mini.alert('<font color="red">' + _loginUserLanguageResource.saveFailed + '</font>',
                    _loginUserLanguageResource.tip);
            }
        },
        error: function () {
            mini.unmask(maskEl);
            mini.alert('【<font color="red">' + _loginUserLanguageResource.exceptionThrow + '</font>】：' +
                _loginUserLanguageResource.contactAdmin, _loginUserLanguageResource.tip);
        }
    });
}
//---------- 加载左侧实例列表 ----------
function loadOmDeviceTabList() {
    var grid = mini.get('operationMaintenanceDeviceTabManagerGridView_Id');
    if (!grid) return;
    if (!grid.getUrl()) {
        grid.setUrl(context + '/operationMaintenanceController/getDeviceTabManagerData');
    }
    grid.load();
}

// 左侧列表：加载前
function onOmDeviceTabGridBeforeLoad(e) {
    // 无附加参数
}

// 左侧列表：加载完成
function onOmDeviceTabGridLoad(e) {
    var grid = e.sender;
    var result = e.result || {};

    // 动态创建列（只创建一次）
    if (!grid._columnsCreated) {
        createOmDeviceTabGridColumns(grid, result);
        grid._columnsCreated = true;
    }

    // 默认选中第一行
    var data = grid.getData();

    if (data && data.length > 0) {
        var selected = grid.getSelected();
        if (!selected) {
            grid.select(0);
        }
    } else {
        // 无数据时清空右侧
        clearOmDeviceTabConfigPanel();
    }
}

// 左侧列表：动态创建列
function createOmDeviceTabGridColumns(grid, result) {
    var R = _loginUserLanguageResource;
    var editFlag = (_omModuleRight.editFlag == 1);

    // 计算类型下拉数据源
    var calcTypeData = [
        {
            id: R.nothing,
            text: R.nothing
        },
        {
            id: R.SRPCalculate,
            text: R.SRPCalculate
        },
        {
            id: R.PCPCalculate,
            text: R.PCPCalculate
        }
    ];

    var textEditor = editFlag ? {
        type: 'textbox',
        allowBlank: true
    } : null;
    var sortEditor = editFlag ? {
        type: 'spinner',
        minValue: 1,
        maxValue: 9999999999
    } : null;
    var calcEditor = editFlag ? {
        type: 'combobox',
        data: calcTypeData,
        valueField: 'id',
        textField: 'text',
        allowInput: false,
        allowBlank: false
    } : null;

    var columns = [];

    // 复选框列
    if (editFlag) {
        columns.push({
            type: 'checkcolumn',
            width: 40,
            header: '',
            headerAlign: 'center',
            align: 'center'
        });
    }

    // 序号
    columns.push({
        type: 'indexcolumn',
        width: 50,
        header: R.idx,
        headerAlign: 'center',
        align: 'center'
    });

    // 中文
    columns.push({
        field: 'name_zh_CN',
        header: R.language_zh_CN,
        headerAlign: 'center',
        align: 'left',
        width: 150,
        editor: textEditor,
        renderer: function (e) {
            return renderOmDeviceTabCell(e.value);
        }
    });

    // 英文
    columns.push({
        field: 'name_en',
        header: R.language_en,
        headerAlign: 'center',
        align: 'left',
        width: 150,
        editor: textEditor,
        renderer: function (e) {
            return renderOmDeviceTabCell(e.value);
        }
    });

    // 俄文
    columns.push({
        field: 'name_ru',
        header: R.language_ru,
        headerAlign: 'center',
        align: 'left',
        width: 150,
        editor: textEditor,
        renderer: function (e) {
            return renderOmDeviceTabCell(e.value);
        }
    });

    // 计算类型
    columns.push({
        field: 'calculateType',
        header: R.calculationType,
        headerAlign: 'center',
        align: 'center',
        width: 100,
        editor: calcEditor
    });

    // 排序
    columns.push({
        field: 'sort',
        header: R.sequenceNumber,
        headerAlign: 'center',
        align: 'center',
        width: 80,
        editor: sortEditor
    });

    // 隐藏字段
    columns.push({
        field: 'instanceId',
        visible: false
    });

    grid.setColumns(columns);
    grid.setAllowCellEdit(editFlag);
    grid.setAllowCellSelect(editFlag);
}

// 单元格渲染（超长截断 + 提示）
function renderOmDeviceTabCell(val) {
    if (!val) return '';
    var s = String(val).replace(/"/g, '&quot;');
    return '<span title="' + s + '">' + s + '</span>';
}

// 左侧列表：单元格开始编辑前校验
function onOmDeviceTabCellBeginEdit(e) {
    if (_omModuleRight.editFlag != 1) {
        e.cancel = true;
    }
}

// 左侧列表：选中行 → 加载右侧配置
function onOmDeviceTabGridSelectionChanged(e) {
    var grid = e.sender;
    var rows = grid.getSelecteds() || [];
    if (rows.length === 0) {
        _selectedDeviceTabInstanceId = 0;
        clearOmDeviceTabConfigPanel();
        return;
    }
    // 多选时取第一行加载右侧配置
    var row = rows[0];
    _selectedDeviceTabInstanceId = row.instanceId;

    // 根据权限控制右侧勾选
    loadOmDeviceTabInstanceConfig(row.instanceId);
}

// ---------- 加载右侧配置 ----------
function loadOmDeviceTabInstanceConfig(instanceId) {
    if (!instanceId) {
        clearOmDeviceTabConfigPanel();
        return;
    }

    // 先清空
    clearOmDeviceTabConfigPanel();

    var maskEl = 'omDeviceTabContentConfigPanel';
    mini.mask({
        el: maskEl,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });

    $.ajax({
        url: context + '/operationMaintenanceController/loadDeviceTabManagerInstance',
        type: 'POST',
        data: {
            instanceId: instanceId
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask(maskEl);
            if (result && result.success === true) {
                applyOmDeviceTabInstanceConfig(result.config || {});
            }
        },
        error: function () {
            mini.unmask(maskEl);
        }
    });
}

//清空右侧配置
function clearOmDeviceTabConfigPanel() {
    for (var i = 0; i < _OM_DEVICE_TAB_CONFIG_FIELDS.length; i++) {
        var ctrl = mini.get(_OM_DEVICE_TAB_CONFIG_FIELDS[i].id);
        if (ctrl) ctrl.setValue(false);
    }
    // ★ 清空 checkboxlist
    var chartList = mini.get('om_realtime_RodStressChart');
    if (chartList) chartList.setValue([]);
}

// 回填右侧配置
function applyOmDeviceTabInstanceConfig(config) {
    if (!config) return;

    // ---------- 普通 checkbox ----------
    for (var i = 0; i < _OM_DEVICE_TAB_CONFIG_FIELDS.length; i++) {
        var item = _OM_DEVICE_TAB_CONFIG_FIELDS[i];
        var ctrl = mini.get(item.id);
        if (!ctrl) continue;

        var group = config[item.group] || {};
        var val = group[item.field];
        ctrl.setValue(isTrueVal(val));
    }

    // ---------- ★ 杆柱应力图显示内容（checkboxlist） ----------
    var chartList = mini.get('om_realtime_RodStressChart');
    if (chartList) {
        var rt = config.DeviceRealTimeMonitoring || {};
        var arr = [];
        if (isTrueVal(rt.RodStressChart_MaxRodStress)) {
            arr.push(_OM_ROD_STRESS_KEY_MAX);
        }
        if (isTrueVal(rt.RodStressChart_RodStressRange)) {
            arr.push(_OM_ROD_STRESS_KEY_RANGE);
        }
        chartList.setValue(arr);
    }
}

// ---------- 刷新 ----------
function onOmDeviceTagRefresh() {
    _selectedDeviceTabInstanceId = 0;
    loadOmDeviceTabList();
}

// ---------- 添加 ----------
function onOmDeviceTagAdd() {
    if (_omModuleRight.editFlag != 1) return;

    mini.open({
        title: _loginUserLanguageResource.addDeviceTagInstance || _loginUserLanguageResource.deviceTag,
        url: context + '/miniui-app/modules/operationMaintenance/deviceTabManagerAddWindow.jsp',
        width: 480,
        height: 320,
        modal: true,
        allowResize: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;

            // ★ 传参（设备标签添加目前无需上下文参数，保留接口）
            contentWindow.setData({
                // 无参可留空
            });

            // ★ 子窗口回调：刷新实例列表
            contentWindow.parent._parentRefreshDeviceTabList = function () {
                loadOmDeviceTabList();
            };
        }
    });
}

// ---------- 删除 ----------
function onOmDeviceTagDel() {
    if (_omModuleRight.editFlag != 1) return;

    var grid = mini.get('operationMaintenanceDeviceTabManagerGridView_Id');
    if (!grid) return;

    var rows = grid.getSelecteds() || [];
    if (rows.length === 0) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }

    var instanceIds = [];
    var instanceNames = [];
    for (var i = 0; i < rows.length; i++) {
        instanceIds.push(rows[i].instanceId);
        instanceNames.push(rows[i].name_zh_CN || rows[i].name_en || rows[i].name_ru || '');
    }

    var deleteInfo;
    if (instanceIds.length === 1) {
        deleteInfo = _loginUserLanguageResource.deviceTagInstance + '：' +
            '<font color="red">' + instanceNames[0] + '</font><br/>' +
            _loginUserLanguageResource.confirmDelete;
    } else {
        deleteInfo = _loginUserLanguageResource.sparseRecordCount + '：' +
            '<font color="red">' + instanceIds.length + '</font><br/>' +
            _loginUserLanguageResource.confirmDelete;
    }

    mini.confirm(deleteInfo, _loginUserLanguageResource.tip, function (action) {
        if (action !== 'ok') return;

        mini.mask({
            el: document.body,
            html: _loginUserLanguageResource.submittingData
        });

        $.ajax({
            url: context + '/operationMaintenanceController/deleteDeviceTabManagerInstance',
            type: 'POST',
            data: {
                instanceIds: instanceIds.join(',')
            },
            dataType: 'json',
            success: function (result) {
                mini.unmask(document.body);
                if (result && result.success === true) {
                    mini.alert(_loginUserLanguageResource.deleteSuccessfully, _loginUserLanguageResource.tip);
                    _selectedDeviceTabInstanceId = 0;
                    loadOmDeviceTabList();
                } else {
                    mini.alert('<font color="red">' + _loginUserLanguageResource.deleteFailed + '</font>',
                        _loginUserLanguageResource.tip);
                }
            },
            error: function () {
                mini.unmask(document.body);
                mini.alert('【<font color="red">' + _loginUserLanguageResource.exceptionThrow + '</font>】：' +
                    _loginUserLanguageResource.contactAdmin, _loginUserLanguageResource.tip);
            }
        });
    });
}

// ---------- 保存 ----------
function onOmDeviceTagSave() {
    if (_omModuleRight.editFlag != 1) return;

    var grid = mini.get('operationMaintenanceDeviceTabManagerGridView_Id');
    if (!grid) return;

    // 提交正在编辑的单元格
    grid.commitEdit();

    // 1. 收集列表修改记录
    var modifiedRecords = grid.getChanges('modified', false);
    var modifiedTabDisplayInstance = [];
    if (modifiedRecords && modifiedRecords.length > 0) {
        for (var i = 0; i < modifiedRecords.length; i++) {
            var rec = modifiedRecords[i];

            var calculateType = 0;
            if (rec.calculateType == _loginUserLanguageResource.SRPCalculate) {
                calculateType = 1;
            } else if (rec.calculateType == _loginUserLanguageResource.PCPCalculate) {
                calculateType = 2;
            }


            modifiedTabDisplayInstance.push({
                id: rec.instanceId,
                name_zh_CN: rec.name_zh_CN,
                name_en: rec.name_en,
                name_ru: rec.name_ru,
                sort: (isNotVal(rec.sort) && isNumber(rec.sort)) ? rec.sort : null,
                calculateType: calculateType
            });
        }
    }

    // 2. 收集右侧勾选配置
    var instanceConfig = {
        DeviceRealTimeMonitoring: {},
        DeviceHistoryQuery: {},
        PrimaryDevice: {}
    };
    // ---------- 普通 checkbox ----------
    for (var k = 0; k < _OM_DEVICE_TAB_CONFIG_FIELDS.length; k++) {
        var item = _OM_DEVICE_TAB_CONFIG_FIELDS[k];
        var ctrl = mini.get(item.id);
        var val = ctrl ? !!ctrl.getValue() : false;
        if (!instanceConfig[item.group]) instanceConfig[item.group] = {};
        instanceConfig[item.group][item.field] = val;
    }

    // ---------- ★ 杆柱应力图显示内容 ----------
    var chartList = mini.get('om_realtime_RodStressChart');
    var chartVals = chartList ? (chartList.getValue() || []) : [];
    instanceConfig.DeviceRealTimeMonitoring.RodStressChart_MaxRodStress =
        chartVals.indexOf(_OM_ROD_STRESS_KEY_MAX) > -1;
    instanceConfig.DeviceRealTimeMonitoring.RodStressChart_RodStressRange =
        chartVals.indexOf(_OM_ROD_STRESS_KEY_RANGE) > -1;

    // 3. 无变更 & 无选中 → 提示
    if (modifiedTabDisplayInstance.length === 0 && !_selectedDeviceTabInstanceId) {
        mini.alert(_loginUserLanguageResource.noDataChange, _loginUserLanguageResource.tip);
        return;
    }

    var maskEl = 'omDeviceTagPanel';
    mini.mask({
        el: maskEl,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.submittingData
    });

    $.ajax({
        url: context + '/operationMaintenanceController/saveDeviceTabManagerInstance',
        type: 'POST',
        data: {
            data: JSON.stringify(modifiedTabDisplayInstance),
            selectInstanceId: _selectedDeviceTabInstanceId,
            instanceConfig: JSON.stringify(instanceConfig)
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask(maskEl);
            if (result && result.success === true) {
                mini.alert(_loginUserLanguageResource.savedSuccessfully, _loginUserLanguageResource.tip);
                grid.accept();
                // 保持当前选中
                var keepId = _selectedDeviceTabInstanceId;
                loadOmDeviceTabList();
                // 数据加载后恢复选中
                setTimeout(function () {
                    if (keepId) {
                        var data = grid.getData() || [];
                        for (var m = 0; m < data.length; m++) {
                            if (String(data[m].instanceId) === String(keepId)) {
                                grid.select(data[m]);
                                break;
                            }
                        }
                    }
                }, 300);
            } else {
                mini.alert('<font color="red">' + _loginUserLanguageResource.saveFailed + '</font>',
                    _loginUserLanguageResource.tip);
            }
        },
        error: function () {
            mini.unmask(maskEl);
            mini.alert('【<font color="red">' + _loginUserLanguageResource.exceptionThrow + '</font>】：' +
                _loginUserLanguageResource.contactAdmin, _loginUserLanguageResource.tip);
        }
    });
}

// ================================================================
// 事件占位
// ================================================================
function onOmOemSave() {

}

function onOmUpload(type) {

}

//================================================================
//内存曲线模块
//================================================================

function onOmCurveRefresh() {
    var startDateCtrl = mini.get('om_curveStartDate');
    var endDateCtrl = mini.get('om_curveEndDate');
    if (startDateCtrl) startDateCtrl.setValue(null);
    if (endDateCtrl) endDateCtrl.setValue(null);
    getOperationMaintenanceMonitorCurveData();
}

function onOmCurveSearch() {
    getOperationMaintenanceMonitorCurveData();
}

//格式化日期为 yyyy-MM-dd HH:mm:ss
function formatOmCurveDate(date) {
    if (!date) return '';
    var y = date.getFullYear();
    var m = (date.getMonth() + 1).toString().padStart(2, '0');
    var d = date.getDate().toString().padStart(2, '0');
    var hh = date.getHours().toString().padStart(2, '0');
    var mm = date.getMinutes().toString().padStart(2, '0');
    var ss = date.getSeconds().toString().padStart(2, '0');
    return y + '-' + m + '-' + d + ' ' + hh + ':' + mm + ':' + ss;
}

function getOperationMaintenanceMonitorCurveData() {
    var panelId = 'omMonitorCurvePanel';
    var startDateCtrl = mini.get('om_curveStartDate');
    var endDateCtrl = mini.get('om_curveEndDate');

    var startDate = startDateCtrl ? startDateCtrl.getValue() : null;
    var endDate = endDateCtrl ? endDateCtrl.getValue() : null;

    var startDateStr = startDate ? formatOmCurveDate(startDate) : '';
    var endDateStr = endDate ? formatOmCurveDate(endDate) : '';

    mini.mask({
        el: panelId,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });

    $.ajax({
        url: context + '/operationMaintenanceController/getOperationMaintenanceMonitorCurveData',
        type: 'POST',
        data: {
            startDate: startDateStr,
            endDate: endDateStr
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask(panelId);

            // 回填后端返回的默认日期
            if (startDateCtrl && !startDateCtrl.getValue() && result.startDate) {
                startDateCtrl.setValue(new Date(result.startDate.replace(/-/g, '/')));
            }
            if (endDateCtrl && !endDateCtrl.getValue() && result.endDate) {
                endDateCtrl.setValue(new Date(result.endDate.replace(/-/g, '/')));
            }

            var data = result.list || [];
            var timeFormat = '%m-%d';
            if (data.length > 0 && result.minAcqTime.split(' ')[0] == result.maxAcqTime.split(' ')[0]) {
                timeFormat = '%H:%M';
            }

            var defaultColors = ["#7cb5ec", "#434348", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#e4d354", "#2b908f", "#f45b5b", "#91e8e1"];
            var tickInterval = Math.floor(data.length / 10) + 1;
            if (tickInterval < 100) tickInterval = 100;

            var title = _loginUserLanguageResource.memoryCurve;
            var subtitle = result.startDate + '~' + result.endDate;
            var xTitle = _loginUserLanguageResource.acqTime;
            var legendName = result.curveItems || [];
            var legendCode = result.curveItemCodes || [];

            var color = defaultColors;
            var yAxis = [];
            var series = [];

            for (var i = 0; i < legendName.length; i++) {
                var maxValue = null;
                var minValue = null;
                var allPositive = true;
                var allNegative = true;

                var singleSeries = {
                    name: legendName[i],
                    code: legendCode[i],
                    type: 'spline',
                    lineWidth: 3,
                    dashStyle: 'Solid',
                    marker: {
                        enabled: false
                    },
                    yAxis: i,
                    data: []
                };
                for (var j = 0; j < data.length; j++) {
                    var pointData = [];
                    pointData.push(Date.parse(data[j].acqTime.replace(/-/g, '/')));
                    pointData.push(data[j].data[i]);

                    if (parseFloat(data[j].data[i]) < 0) {
                        allPositive = false;
                    } else if (parseFloat(data[j].data[i]) >= 0) {
                        allNegative = false;
                    }
                    singleSeries.data.push(pointData);
                }
                series.push(singleSeries);

                var opposite = false;
                if (allNegative) {
                    maxValue = 0;
                } else if (allPositive) {
                    minValue = 0;
                }

                var singleAxis = {
                    max: maxValue,
                    min: minValue,
                    code: legendCode[i],
                    title: {
                        text: legendName[i],
                        style: {
                            color: color[i]
                        }
                    },
                    labels: {
                        style: {
                            color: color[i]
                        }
                    },
                    lineWidth: 1,
                    tickWidth: 1,
                    tickLength: 5,
                    opposite: opposite
                };
                yAxis.push(singleAxis);
            }

            initOperationMaintenanceMonitorCurveChartFn(series, tickInterval, 'OperationMaintenanceMonitorCurveDiv_Id', title, subtitle, '', yAxis, color, true, timeFormat);
        },
        error: function () {
            mini.unmask(panelId);
            mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
        }
    });
}

function initOperationMaintenanceMonitorCurveChartFn(series, tickInterval, divId, title, subtitle, xtitle, yAxis, color, legend, timeFormat) {
    if ($("#" + divId) != undefined && $("#" + divId)[0] != undefined) {
        if (operationMaintenanceMonitorCurveChart) {
            operationMaintenanceMonitorCurveChart.destroy();
        }

        var isZooming = false;
        var zoomTimer = null;
        var panelId = "omMonitorCurvePanel";
        var chartTitleFontSize = '16px'; // 如果没有全局定义，给定一个固定值

        operationMaintenanceMonitorCurveChart = new Highcharts.Chart({
            chart: {
                renderTo: divId,
                type: 'spline',
                shadow: false,
                borderWidth: 0,
                zoomType: 'xy',
                zooming: {
                    mouseWheel: {
                        enabled: false
                    }
                },
                events: {
                    redraw: function () {
                        if (isZooming) {
                            setTimeout(function () {
                                mini.unmask(panelId);
                                isZooming = false;
                                if (zoomTimer) clearTimeout(zoomTimer);
                            }, 300);
                        }
                    }
                }
            },
            time: {
                timezoneOffset: new Date().getTimezoneOffset()
            },
            credits: {
                enabled: false
            },
            title: {
                text: title,
                style: {
                    fontSize: chartTitleFontSize
                }
            },
            subtitle: {
                text: subtitle
            },
            colors: color,
            xAxis: {
                type: 'datetime',
                title: {
                    text: xtitle
                },
                tickPixelInterval: tickInterval,
                labels: {
                    formatter: function () {
                        return this.axis.chart.time.dateFormat(timeFormat, this.value);
                    },
                    autoRotation: true,
                    rotation: -45
                },
                events: {
                    setExtremes: function (e) {
                        var currentMin = this.min;
                        var currentMax = this.max;
                        var newMin = e.min;
                        var newMax = e.max;
                        if (currentMin === newMin && currentMax === newMax) return;
                        if (!isZooming) {
                            isZooming = true;
                            mini.mask({
                                el: panelId,
                                cls: 'mini-mask-loading',
                                html: _loginUserLanguageResource.loadingData
                            });
                        }
                        if (zoomTimer) clearTimeout(zoomTimer);
                        zoomTimer = setTimeout(function () {
                            if (isZooming) {
                                mini.unmask(panelId);
                                isZooming = false;
                            }
                        }, 5000);
                    }
                }
            },
            yAxis: yAxis,
            tooltip: {
                crosshairs: true,
                shared: true,
                style: {
                    color: '#333333',
                    fontSize: '12px',
                    padding: '8px'
                },
                dateTimeLabelFormats: {
                    millisecond: '%Y-%m-%d %H:%M:%S.%L',
                    second: '%Y-%m-%d %H:%M:%S',
                    minute: '%Y-%m-%d %H:%M',
                    hour: '%Y-%m-%d %H',
                    day: '%Y-%m-%d',
                    week: '%m-%d',
                    month: '%Y-%m',
                    year: '%Y'
                }
            },
            plotOptions: {
                spline: {
                    fillOpacity: 0.3,
                    shadow: true,
                    events: {
                        legendItemClick: function (e) {}
                    }
                }
            },
            legend: {
                layout: 'horizontal',
                align: 'center',
                verticalAlign: 'bottom',
                enabled: legend,
                borderWidth: 0,
                itemHiddenStyle: {
                    textDecoration: 'none'
                }
            },
            series: series,
            exporting: {
                fallbackToExportServer: false,
                filename: title + '-' + subtitle,
                sourceWidth: $("#" + divId).width(),
                sourceHeight: $("#" + divId).height(),
                scale: 1,
                error: function (err) {
                    console.error('导出失败:', err);
                },
                buttons: {
                    contextButton: {
                        menuItems: [
                         'viewFullscreen',
                         'printChart',
                         'separator',
                         'downloadPNG',
                         'downloadJPEG',
                         'downloadSVG',
                         'separator',
                         'downloadCSV',
                         'downloadXLS'
                     ]
                    }
                }
            }
        });
    }
}

//================================================================
//下位机程序升级
//================================================================

//================================================================
//加载设备列表
//================================================================
function loadLowerComputerProgramUpgradeDeviceList() {
    // 1. 销毁旧的
    if (lowerComputerProgramUpgradeHandsontableHelper != null) {
        if (lowerComputerProgramUpgradeHandsontableHelper.hot != undefined) {
            lowerComputerProgramUpgradeHandsontableHelper.hot.destroy();
        }
        lowerComputerProgramUpgradeHandsontableHelper = null;
    }

    // 2. 取参数
    var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
    var orgId = leftOrgId ? leftOrgId.getValue() : '';

    var deviceName = '';
    var comb = mini.get('lowerComputerProgramUpgradeDeviceListComb_Id');
    if (comb) deviceName = comb.getValue() || '';

    // 3. 遮罩
    var panelId = 'omLowerComputerPanel';
    mini.mask({
        el: panelId,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });

    // 4. 请求
    $.ajax({
        url: context + '/operationMaintenanceController/loadLowerComputerProgramUpgradeDeviceList',
        type: 'POST',
        data: {
            orgId: orgId,
            deviceName: deviceName
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask(panelId);

            var list = (result && result.totalRoot) ? result.totalRoot : [];

            if (lowerComputerProgramUpgradeHandsontableHelper == null ||
                lowerComputerProgramUpgradeHandsontableHelper.hot == undefined) {

                // 首次创建
                lowerComputerProgramUpgradeHandsontableHelper =
                    LowerComputerProgramUpgradeHandsontableHelper.createNew(
                        'OperationMaintenanceLowerComputerProgramUpgradeDiv_Id');

                // 表头（两行，分组显示）
                var colHeaders = [
                 ['', '', '', '', '',
                        {
                            label: _loginUserLanguageResource.boxProgram,
                            colspan: 5
                        },
                        {
                            label: _loginUserLanguageResource.acProgram,
                            colspan: 5
                        },
                  '', ''],
                 ['',
                  _loginUserLanguageResource.idx,
                  _loginUserLanguageResource.deviceName,
                  _loginUserLanguageResource.uplink,
                  _loginUserLanguageResource.RPCStatus,
                  _loginUserLanguageResource.boxProgramVersion,
                  _loginUserLanguageResource.updateStatus,
                  _loginUserLanguageResource.updateTime,
                  _loginUserLanguageResource.downlinkStatus,
                  _loginUserLanguageResource.downlink,
                  _loginUserLanguageResource.boxProgramVersion,
                  _loginUserLanguageResource.updateStatus,
                  _loginUserLanguageResource.updateTime,
                  _loginUserLanguageResource.downlinkStatus,
                  _loginUserLanguageResource.downlink,
                  'lowerComputerDeviceId',
                  'deviceId']
             ];

                // 列定义
                var columns = [
                    {
                        data: 'checked',
                        type: 'checkbox'
                    },
                    {
                        data: 'id'
                    },
                    {
                        data: 'deviceName'
                    },
                    {
                        data: 'uplink',
                        renderer: createLowerComputerProgramUpgradeButtonRenderer(
                            _loginUserLanguageResource.statusDetection,
                            function (instance, td, row, col, prop, value, cellProperties) {
                                lowerComputerProgramVersionDataUplink(row, '');
                            },
                            '#409eff',
                            0
                        ),
                        readOnly: true
                 },
                    {
                        data: 'RPCStatus'
                    },
                    {
                        data: 'boxVersion'
                    },
                    {
                        data: 'boxUpdateStatus'
                    },
                    {
                        data: 'boxUpdateTime'
                    },
                    {
                        data: 'boxDownlinkStatus'
                    },
                    {
                        data: 'uplink',
                        renderer: createLowerComputerProgramUpgradeButtonRenderer(
                            _loginUserLanguageResource.programDownlink,
                            function (instance, td, row, col, prop, value, cellProperties) {
                                lowerComputerProgramUpgrade(row, 'box')
                                    .then(function (result) {
                                        console.log('升级完成，结果：', result);
                                    })
                                    .catch(function (error) {
                                        console.error('升级失败：', error);
                                    });
                            },
                            '#67c23a',
                            1
                        ),
                        readOnly: true
                 },
                    {
                        data: 'acVersion'
                    },
                    {
                        data: 'acUpdateStatus'
                    },
                    {
                        data: 'acUpdateTime'
                    },
                    {
                        data: 'acDownlinkStatus'
                    },
                    {
                        data: 'uplink',
                        renderer: createLowerComputerProgramUpgradeButtonRenderer(
                            _loginUserLanguageResource.programDownlink,
                            function (instance, td, row, col, prop, value, cellProperties) {
                                lowerComputerProgramUpgrade(row, 'ac')
                                    .then(function (result) {
                                        console.log('升级完成，结果：', result);
                                    })
                                    .catch(function (error) {
                                        console.error('升级失败：', error);
                                    });
                            },
                            '#e6a23c',
                            2
                        ),
                        readOnly: true
                 },
                    {
                        data: 'lowerComputerDeviceId'
                    },
                    {
                        data: 'deviceId'
                    }
             ];

                lowerComputerProgramUpgradeHandsontableHelper.colHeaders = colHeaders;
                lowerComputerProgramUpgradeHandsontableHelper.columns = columns;

                if (list.length == 0) {
                    lowerComputerProgramUpgradeHandsontableHelper.hiddenRows = [0];
                    lowerComputerProgramUpgradeHandsontableHelper.createTable([{}]);
                } else {
                    lowerComputerProgramUpgradeHandsontableHelper.hiddenRows = [];
                    lowerComputerProgramUpgradeHandsontableHelper.createTable(list);
                }
            } else {
                // 复用已有 hot
                lowerComputerProgramUpgradeHandsontableHelper.hot.deselectCell();
                if (list.length == 0) {
                    lowerComputerProgramUpgradeHandsontableHelper.hiddenRows = [0];
                    lowerComputerProgramUpgradeHandsontableHelper.createTable([{}]);
                } else {
                    lowerComputerProgramUpgradeHandsontableHelper.hiddenRows = [];
                    lowerComputerProgramUpgradeHandsontableHelper.createTable(list);
                }
            }

            // 选中第一行
            if (list.length > 0) {
                lowerComputerProgramUpgradeHandsontableHelper.hot.selectCell(0, 'deviceName');
            }
        },
        error: function () {
            mini.unmask(panelId);
            mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
        }
    });
}

//================================================================
//刷新按钮
//================================================================
function onOmLowerComputerRefresh() {
    var comb = mini.get('lowerComputerProgramUpgradeDeviceListComb_Id');
    if (comb) comb.setValue('');
    loadLowerComputerProgramUpgradeDeviceList();
}

//================================================================
//全选
//================================================================
function onOmLowerComputerSelectAll() {
    lowerComputerProgramUpgradeSelectAll(true);
}

//================================================================
//取消全选
//================================================================
function onOmLowerComputerDeselectAll() {
    lowerComputerProgramUpgradeSelectAll(false);
}

//================================================================
//全选/取消全选的底层实现
//================================================================
function lowerComputerProgramUpgradeSelectAll(selected) {
    var helper = lowerComputerProgramUpgradeHandsontableHelper;
    if (helper == undefined || helper.hot == undefined) return;

    var rowCount = helper.hot.countRows();
    var updateData = [];
    for (var i = 0; i < rowCount; i++) {
        updateData.push([i, 'checked', selected]);
    }
    helper.hot.setDataAtRowProp(updateData);
}

//================================================================
//批量盒体程序升级
//================================================================
function onOmBoxUpgrade() {
    if (_omModuleRight.editFlag != 1) return;

    var helper = lowerComputerProgramUpgradeHandsontableHelper;
    if (helper == null || helper.hot == undefined) return;

    // 收集勾选行
    var selectList = [];
    var selectDeviceNameList = [];
    var checkedArr = helper.hot.getDataAtProp('checked');
    for (var i = 0; i < checkedArr.length; i++) {
        if (checkedArr[i]) {
            selectList.push(i);
            selectDeviceNameList.push(helper.hot.getDataAtRowProp(i, 'deviceName'));
        }
    }

    if (selectList.length == 0) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }

    // 确认框
    var programType = _loginUserLanguageResource.boxProgram;
    var deviceName = selectDeviceNameList.join(',');
    var confirmInfo = programType + '</br>' +
        _loginUserLanguageResource.deviceName + ':<font color="red">' + deviceName + '</font></br>' +
        _loginUserLanguageResource.confirmDownlink;

    mini.confirm(confirmInfo, _loginUserLanguageResource.tip, function (action) {
        if (action !== 'ok') return;

        // 全部设等待状态
        for (var j = 0; j < selectList.length; j++) {
            helper.hot.setDataAtRowProp(selectList[j], 'boxDownlinkStatus',
                _loginUserLanguageResource.waitingForDownlink);
        }

        // 顺序批量升级
        lowerComputerProgramBatchUpgrade(selectList, 'box');
    });
}

//================================================================
//批量交流程序升级
//================================================================
function onOmAcUpgrade() {
    if (_omModuleRight.editFlag != 1) return;

    var helper = lowerComputerProgramUpgradeHandsontableHelper;
    if (helper == null || helper.hot == undefined) return;

    var selectList = [];
    var selectDeviceNameList = [];
    var checkedArr = helper.hot.getDataAtProp('checked');
    for (var i = 0; i < checkedArr.length; i++) {
        if (checkedArr[i]) {
            selectList.push(i);
            selectDeviceNameList.push(helper.hot.getDataAtRowProp(i, 'deviceName'));
        }
    }

    if (selectList.length == 0) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }

    var programType = _loginUserLanguageResource.acProgram;
    var deviceName = selectDeviceNameList.join(',');
    var confirmInfo = programType + '</br>' +
        _loginUserLanguageResource.deviceName + ':<font color="red">' + deviceName + '</font></br>' +
        _loginUserLanguageResource.confirmDownlink;

    mini.confirm(confirmInfo, _loginUserLanguageResource.tip, function (action) {
        if (action !== 'ok') return;

        for (var j = 0; j < selectList.length; j++) {
            helper.hot.setDataAtRowProp(selectList[j], 'acDownlinkStatus',
                _loginUserLanguageResource.waitingForDownlink);
        }

        lowerComputerProgramBatchUpgrade(selectList, 'ac');
    });
}

//================================================================
//批量状态检测（上行）
//================================================================
function onOmLowerComputerUplink() {
    if (_omModuleRight.editFlag != 1) return;

    var helper = lowerComputerProgramUpgradeHandsontableHelper;
    if (helper == null || helper.hot == undefined) return;

    var deviceIdList = [];
    var checkedArr = helper.hot.getDataAtProp('checked');
    for (var i = 0; i < checkedArr.length; i++) {
        if (checkedArr[i]) {
            deviceIdList.push(helper.hot.getDataAtRowProp(i, 'deviceId'));
        }
    }

    lowerComputerProgramVersionDataBatchUplink(deviceIdList, '');
}

//================================================================
//批量下行（顺序执行）
//原逻辑：用 Promise 链，一个 resolve 后才执行下一个
//================================================================
function lowerComputerProgramBatchUpgrade(selectList, name) {
    var i = 0;

    function next() {
        if (i >= selectList.length) {
            console.log('所有升级任务执行完毕');
            return;
        }
        lowerComputerProgramUpgrade(selectList[i], name)
            .then(function (result) {
                console.log('第 ' + (i + 1) + ' 次升级完成，结果：', result);
                i++;
                next();
            })
            .catch(function (error) {
                console.error('第 ' + (i + 1) + ' 次升级失败：', error);
                i++;
                next(); // 继续下一次
            });
    }
    next();
}

//================================================================
//单行下行（返回 Promise，供批量和按钮渲染器共用）
//================================================================
function lowerComputerProgramUpgrade(row, name) {
    return new Promise(function (resolve, reject) {
        var helper = lowerComputerProgramUpgradeHandsontableHelper;
        var panelId = 'omLowerComputerPanel';

        mini.mask({
            el: panelId,
            cls: 'mini-mask-loading',
            html: _loginUserLanguageResource.downlinking + '...'
        });

        var deviceId = helper.hot.getDataAtRowProp(row, 'deviceId');
        var plugin = helper.hot.getPlugin('hiddenColumns');

        if (name == 'box') {
            helper.hot.setDataAtRowProp(row, 'boxDownlinkStatus',
                _loginUserLanguageResource.downlinking + '...');
            plugin.showColumns([8]);
        } else if (name == 'ac') {
            helper.hot.setDataAtRowProp(row, 'acDownlinkStatus',
                _loginUserLanguageResource.downlinking + '...');
            plugin.showColumns([13]);
        }
        helper.hot.render();

        $.ajax({
            url: context + '/wellInformationManagerController/lowerComputerProgramUpgrade',
            type: 'POST',
            timeout: 10 * 60 * 1000, // 10 分钟超时
            data: {
                deviceId: deviceId,
                name: name
            },
            dataType: 'json',
            success: function (result) {
                mini.unmask(panelId);

                if (result.flag == false) {
                    // 会话过期
                    if (name == 'box') {
                        helper.hot.setDataAtRowProp(row, 'boxDownlinkStatus',
                            _loginUserLanguageResource.sessionExpired);
                    } else if (name == 'ac') {
                        helper.hot.setDataAtRowProp(row, 'acDownlinkStatus',
                            _loginUserLanguageResource.sessionExpired);
                    }
                    helper.hot.render();
                    reject(new Error(_loginUserLanguageResource.sessionExpired));
                    return;
                } else if (result.flag == true && result.error == false) {
                    // 升级成功（无 error）
                    if (name == 'box') {
                        helper.hot.setDataAtRowProp(row, 'boxDownlinkStatus', result.msg);
                    } else if (name == 'ac') {
                        helper.hot.setDataAtRowProp(row, 'acDownlinkStatus', result.msg);
                    }
                    helper.hot.render();
                } else if (result.flag == true && result.error == true) {
                    // 升级失败（带错误信息 + 更新时间）
                    if (name == 'box') {
                        helper.hot.setDataAtRowProp(row, 'boxDownlinkStatus', result.msg);
                        helper.hot.setDataAtRowProp(row, 'boxUpdateStatus', result.msg);
                        helper.hot.setDataAtRowProp(row, 'boxUpdateTime', result.updateTime);
                    } else if (name == 'ac') {
                        helper.hot.setDataAtRowProp(row, 'acDownlinkStatus', result.msg);
                        helper.hot.setDataAtRowProp(row, 'acUpdateStatus', result.msg);
                        helper.hot.setDataAtRowProp(row, 'acUpdateTime', result.updateTime);
                    }
                    helper.hot.render();
                }
                resolve(result);
            },
            error: function () {
                mini.unmask(panelId);
                mini.alert('【<font color="red">' + _loginUserLanguageResource.exceptionThrow + '</font>】:' +
                    _loginUserLanguageResource.contactAdmin, _loginUserLanguageResource.tip);
                reject(new Error('请求失败'));
            }
        });
    });
}

//================================================================
//单行状态检测（上行）
//name: '' → 检测 box + ac
//      'box' → 只检测 box
//      'ac'  → 只检测 ac
//================================================================
function lowerComputerProgramVersionDataUplink(row, name) {
    var helper = lowerComputerProgramUpgradeHandsontableHelper;
    var deviceId = helper.hot.getDataAtRowProp(row, 'deviceId');
    var panelId = 'omLowerComputerPanel';

    mini.mask({
        el: panelId,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.commandSending + '...'
    });

    $.ajax({
        url: context + '/wellInformationManagerController/lowerComputerProgramVersionDataUplink',
        type: 'POST',
        data: {
            deviceId: deviceId,
            name: name
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask(panelId);

            if (result.flag == false) {
                mini.alert(_loginUserLanguageResource.sessionExpired, _loginUserLanguageResource.tip,
                    function () {
                        window.location.href = context + '/login';
                    });
            } else if (result.flag == true && result.error == false) {
                helper.hot.setDataAtRowProp(row, 'lowerComputerDeviceId', result.msg);
                var plugin = helper.hot.getPlugin('hiddenColumns');
                if (name == 'box') {
                    helper.hot.setDataAtRowProp(row, 'boxVersion', result.msg);
                    plugin.showColumns([5]);
                } else if (name == 'ac') {
                    helper.hot.setDataAtRowProp(row, 'acVersion', result.msg);
                    plugin.showColumns([10]);
                } else if (name == '') {
                    helper.hot.setDataAtRowProp(row, 'boxVersion', result.msg);
                    helper.hot.setDataAtRowProp(row, 'acVersion', result.msg);
                    plugin.showColumns([5, 10]);
                }
                helper.hot.setDataAtRowProp(row, 'RPCStatus', _loginUserLanguageResource.unknown);
                helper.hot.render();
            } else if (result.flag == true && result.error == true) {
                helper.hot.setDataAtRowProp(row, 'lowerComputerDeviceId', result.lowerComputerDeviceId);
                var plugin2 = helper.hot.getPlugin('hiddenColumns');
                if (name == 'box') {
                    helper.hot.setDataAtRowProp(row, 'boxVersion', result.boxVersion);
                    plugin2.showColumns([5]);
                } else if (name == 'ac') {
                    helper.hot.setDataAtRowProp(row, 'acVersion', result.acVersion);
                    plugin2.showColumns([10]);
                } else if (name == '') {
                    helper.hot.setDataAtRowProp(row, 'boxVersion', result.boxVersion);
                    helper.hot.setDataAtRowProp(row, 'acVersion', result.acVersion);
                    plugin2.showColumns([5, 10]);
                }
                helper.hot.setDataAtRowProp(row, 'RPCStatus', result.RPCStatus);
                helper.hot.render();
            }
        },
        error: function () {
            mini.unmask(panelId);
            mini.alert('【<font color="red">' + _loginUserLanguageResource.exceptionThrow + '</font>】:' +
                _loginUserLanguageResource.contactAdmin, _loginUserLanguageResource.tip);
        }
    });
}

//================================================================
//批量状态检测（上行，一次请求所有 deviceIds）
//后台返回 uplinkData 数组，前端遍历更新对应行
//================================================================
function lowerComputerProgramVersionDataBatchUplink(deviceIdList, name) {
    if (!deviceIdList || deviceIdList.length == 0) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }

    var helper = lowerComputerProgramUpgradeHandsontableHelper;
    var panelId = 'omLowerComputerPanel';

    mini.mask({
        el: panelId,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.commandSending + '...'
    });

    $.ajax({
        url: context + '/wellInformationManagerController/lowerComputerProgramVersionDataBatchUplink',
        type: 'POST',
        data: {
            deviceIds: deviceIdList.join(','),
            name: name
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask(panelId);

            if (result.flag == false) {
                mini.alert(_loginUserLanguageResource.sessionExpired, _loginUserLanguageResource.tip,
                    function () {
                        window.location.href = context + '/login';
                    });
            } else if (result.flag == true && result.error == false) {
                mini.alert(result.msg, _loginUserLanguageResource.tip);
            } else if (result.flag == true && result.error == true) {
                // 遍历表格所有行，用 uplinkData 匹配更新
                var deviceIdArr = helper.hot.getDataAtProp('deviceId');
                for (var i = 0; i < deviceIdArr.length; i++) {
                    for (var j = 0; j < result.uplinkData.length; j++) {
                        if (result.uplinkData[j].deviceId == deviceIdArr[i]) {
                            helper.hot.setDataAtRowProp(i, 'lowerComputerDeviceId',
                                result.uplinkData[j].lowerComputerDeviceId);
                            helper.hot.setDataAtRowProp(i, 'RPCStatus',
                                result.uplinkData[j].RPCStatus);
                            if (name == 'box') {
                                helper.hot.setDataAtRowProp(i, 'boxVersion',
                                    result.uplinkData[j].boxVersion);
                            } else if (name == 'ac') {
                                helper.hot.setDataAtRowProp(i, 'acVersion',
                                    result.uplinkData[j].acVersion);
                            } else if (name == '') {
                                helper.hot.setDataAtRowProp(i, 'boxVersion',
                                    result.uplinkData[j].boxVersion);
                                helper.hot.setDataAtRowProp(i, 'acVersion',
                                    result.uplinkData[j].acVersion);
                            }
                            break;
                        }
                    }
                }

                // 显示隐藏的版本列
                var plugin = helper.hot.getPlugin('hiddenColumns');
                if (name == 'box') {
                    plugin.showColumns([5]);
                } else if (name == 'ac') {
                    plugin.showColumns([10]);
                } else if (name == '') {
                    plugin.showColumns([5, 10]);
                }
                helper.hot.render();
            }
        },
        error: function () {
            mini.unmask(panelId);
            mini.alert('【<font color="red">' + _loginUserLanguageResource.exceptionThrow + '</font>】:' +
                _loginUserLanguageResource.contactAdmin, _loginUserLanguageResource.tip);
        }
    });
}

//================================================================
//通用按钮渲染器工厂（对照原 createLowerComputerProgramUpgradeButtonRenderer）
//type: 0=状态检测(不弹确认框) 1=盒体下装(弹确认框) 2=交流下装(弹确认框)
//================================================================
function createLowerComputerProgramUpgradeButtonRenderer(buttonText, clickHandler, bgColor, type, hoverColor) {
    if (!hoverColor) {
        hoverColor = bgColor === '#409eff' ? '#66b1ff' :
            bgColor === '#67c23a' ? '#85ce61' :
            bgColor === '#e6a23c' ? '#ebb563' : '#909399';
    }
    return function (instance, td, row, col, prop, value, cellProperties) {
        td.innerHTML = '';
        var container = document.createElement('div');
        container.style.display = 'flex';
        container.style.justifyContent = 'center';
        container.style.gap = '8px';

        var btn = document.createElement('button');
        btn.textContent = buttonText;
        btn.style.padding = '2px 14px';
        btn.style.fontSize = '12px';
        btn.style.fontWeight = '500';
        btn.style.border = 'none';
        btn.style.borderRadius = '20px';
        btn.style.cursor = 'pointer';
        btn.style.backgroundColor = bgColor;
        btn.style.color = 'white';
        btn.style.transition = 'all 0.2s';
        btn.style.boxShadow = '0 1px 2px rgba(0,0,0,0.1)';

        btn.addEventListener('mouseenter', function () {
            btn.style.backgroundColor = hoverColor;
            btn.style.transform = 'translateY(-1px)';
        });
        btn.addEventListener('mouseleave', function () {
            btn.style.backgroundColor = bgColor;
            btn.style.transform = 'translateY(0)';
        });

        btn.onclick = function (e) {
            if (type == 1 || type == 2) {
                // 弹确认框
                var deviceName = instance.getDataAtRowProp(row, 'deviceName');
                var programType = '';
                if (type == 1) {
                    programType = _loginUserLanguageResource.boxProgram;
                } else if (type == 2) {
                    programType = _loginUserLanguageResource.acProgram;
                }

                var confirmInfo = programType + '</br>' +
                    _loginUserLanguageResource.deviceName + ':<font color="red">' + deviceName + '</font></br>' +
                    _loginUserLanguageResource.confirmDownlink;

                mini.confirm(confirmInfo, _loginUserLanguageResource.tip, function (action) {
                    if (action === 'ok') {
                        e.stopPropagation();
                        clickHandler(instance, td, row, col, prop, value, cellProperties);
                    }
                });
            } else {
                e.stopPropagation();
                clickHandler(instance, td, row, col, prop, value, cellProperties);
            }
        };

        container.appendChild(btn);
        td.appendChild(container);
        return td;
    };
}

//================================================================
//Handsontable 封装（对照原 LowerComputerProgramUpgradeHandsontableHelper）
//================================================================
var LowerComputerProgramUpgradeHandsontableHelper = {
    createNew: function (divid) {
        var helper = {};
        helper.hot = '';
        helper.divid = divid;
        helper.colHeaders = [];
        helper.columns = [];

        // 下装状态单元格样式
        helper.addDownlinkStatusCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (value == _loginUserLanguageResource.downlinking + '...') {
                td.style.backgroundColor = '#13f500';
            } else if (value == _loginUserLanguageResource.waitingForDownlink) {
                td.style.backgroundColor = '#f09614';
            }
            td.style.whiteSpace = 'nowrap';
            td.style.overflow = 'hidden';
            td.style.textOverflow = 'ellipsis';
        };

        // 上行状态单元格样式
        helper.addUplinkStatusCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            if (helper.hot != undefined && helper.hot != '') {
                var itemValue = helper.hot.getDataAtRowProp(row, 'itemValue');
                if (value) {
                    if (value === _loginUserLanguageResource.uplinkFailed) {
                        td.style.backgroundColor = 'rgb(245, 245, 245)';
                    } else if (value === _loginUserLanguageResource.noUplink) {
                        td.style.backgroundColor = 'rgb(245, 245, 245)';
                    } else {
                        if (isNumber(itemValue) && isNumber(value)) {
                            if (parseFloat(itemValue) == parseFloat(value)) {
                                td.style.backgroundColor = 'rgb(245, 245, 245)';
                            } else {
                                td.style.backgroundColor = '#f09614';
                            }
                        } else {
                            if (itemValue == value) {
                                td.style.backgroundColor = 'rgb(245, 245, 245)';
                            } else {
                                td.style.backgroundColor = '#f09614';
                            }
                        }
                    }
                } else {
                    td.innerHTML = '';
                    td.style.backgroundColor = 'rgb(245, 245, 245)';
                }
            } else {
                td.style.backgroundColor = 'rgb(245, 245, 245)';
            }
            td.style.whiteSpace = 'nowrap';
            td.style.overflow = 'hidden';
            td.style.textOverflow = 'ellipsis';
        };

        // 创建表格
        helper.createTable = function (data) {
            $('#' + helper.divid).empty();
            var hotElement = document.querySelector('#' + helper.divid);

            helper.hot = new Handsontable(hotElement, {
                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                theme: 'ht-theme-classic',
                data: data,
                hiddenColumns: {
                    columns: [5, 8, 10, 13, 15, 16],
                    indicators: false,
                    copyPasteEnabled: false
                },
                hiddenRows: {
                    rows: [],
                    indicators: false,
                    copyPasteEnabled: false
                },
                colWidths: [30, 30, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100],
                columns: helper.columns,
                stretchH: 'all',
                autoWrapRow: true,
                rowHeaders: false,
                nestedHeaders: helper.colHeaders,
                columnHeaderHeight: 28,
                columnSorting: true,
                sortIndicator: true,
                manualColumnResize: true,
                manualRowResize: true,
                filters: true,
                renderAllRows: true,
                search: true,
                outsideClickDeselects: false,
                contextMenu: {
                    items: {
                        'copy': {
                            name: _loginUserLanguageResource.contextMenu_copy
                        },
                        'cut': {
                            name: _loginUserLanguageResource.contextMenu_cut
                        }
                    }
                },
                cells: function (row, col, prop) {
                    var cellProperties = {};
                    if (_omModuleRight.editFlag == 1) {
                        if (prop != 'checked') {
                            cellProperties.editor = false;
                        }
                    } else {
                        cellProperties.editor = false;
                    }
                    if (prop == 'boxDownlinkStatus' || prop == 'acDownlinkStatus') {
                        cellProperties.renderer = helper.addDownlinkStatusCellStyle;
                    }
                    return cellProperties;
                },
                afterSelectionEnd: function (row, column, row2, column2, preventScrolling, selectionLayerLevel) {
                    if (row < 0 && row2 < 0) return;
                    if (row < 0) row = 0;
                    if (row2 < 0) row2 = 0;
                    var startRow = row > row2 ? row2 : row;
                    var recordDeviceId = helper.hot.getDataAtRowProp(startRow, 'deviceId');
                    var globalCtrl = mini.get('selectedDeviceId_global');
                    if (globalCtrl) globalCtrl.setValue(recordDeviceId);
                }
            });
        };
        return helper;
    }
};


function onDmDeviceComboBeforeLoad(e) {
    var params = e.params || {};

    // 分页参数
    var pageIndex = params.pageIndex || 0;
    var pageSize = params.pageSize || _DM_DEVICE_COMBO_PAGE_SIZE;
    params.start = pageIndex * pageSize;
    params.limit = pageSize;

    var leftOrgId = '';
    var parentOrgCtrl = (window.parent && window.parent.mini) ? window.parent.mini.get('leftOrg_Id') : null;
    if (parentOrgCtrl) {
        leftOrgId = parentOrgCtrl.getValue() || '';
    }
    params.orgId = leftOrgId;
    var combo = e.sender || mini.get('dmDeviceCombo');
    params.deviceName = combo ? (combo.getValue() || '') : '';

    e.params = params;
}


//================================================================
//展开下拉框时：重新加载数据
//================================================================
function onDmDeviceComboShowPopup(e) {
    var combo = e.sender;

    // 数据少于等于 1 条时先隐藏 popup，避免展开空框再关闭的闪烁
    var data = combo.getData();
    var hidePopup = false;
    if (!data || data.length <= 1) {
        combo.hidePopup();
        hidePopup = true;
    }

    combo.load(combo.url);

    if (hidePopup) {
        combo.showPopup();
    }
}

function onDmDeviceComboChange(e) {
    loadLowerComputerProgramUpgradeDeviceList();
}
