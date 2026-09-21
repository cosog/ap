// ================================================================
// 运维配置模块 - operationMaintenanceInfo.js
// ================================================================

// ---------- 全局状态 ----------
var _omModuleRight = { viewFlag: 0, editFlag: 0, controlFlag: 0 };
var isInitializing = true;

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

    _omModuleRight.viewFlag    = parseInt(_omModuleRight.viewFlag)    || 0;
    _omModuleRight.editFlag    = parseInt(_omModuleRight.editFlag)    || 0;
    _omModuleRight.controlFlag = parseInt(_omModuleRight.controlFlag) || 0;

    initOmTabTitles();
    initOmPanelTitles();
    initOmFieldSetLegends();
    initOmLabels();
    initOmButtons();
    initOmCombosEmptyText();
    initOmControlsData();
    updateOmBtnStatus();

    loadOmLoginLanguageList(function () {
        loadOemOperationConfigInfo();
    });

    setTimeout(function () {
        isInitializing = false;
    }, 100);
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
                    list.push({ boxkey: arr[i].boxkey, boxval: arr[i].boxval });
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
    mini.mask({ el: maskEl, cls: 'mini-mask-loading', html: _loginUserLanguageResource.loadingData });

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

    var others       = configFile.others || {};
    var dbMain       = configFile.databaseMaintenance || {};
    var tableConfig  = dbMain.tableConfig || {};
    var dataVacuate  = configFile.dataVacuate || {};
    var report       = configFile.report || {};

    var combo = mini.get('om_loginLanguage');
    if (combo) {
        combo.setValue(others.loginLanguage);
    }

    setOmCheckboxValue('om_showLogo',            others.showLogo);
    setOmCheckboxValue('om_printLog',            others.printLog);
    setOmCheckboxValue('om_printAdLog',          others.printAdLog);
    setOmCheckboxValue('om_printExceptionLog',   others.printExceptionLog);
    setOmCheckboxValue('om_simulateAcqEnable',   others.simulateAcqEnable);

    var rb = mini.get('om_timeEfficiencyUnit');
    if (rb) {
        rb.setValue(others.timeEfficiencyUnit == 1 ? 1 : 2);
    }

    setOmControlValue('om_resourceMonitoringSaveData', others.resourceMonitoringSaveData);
    setOmControlValue('om_exportLimit',                others.exportLimit);
    setOmControlValue('om_sendCycle',                  others.sendCycle);

    setOmControlValue('om_databaseMaintenanceCycle',     dbMain.cycle);
    setOmControlValue('om_databaseMaintenanceStartTime', dbMain.startTime);
    setOmControlValue('om_databaseMaintenanceEndTime',   dbMain.endTime);

    setOmCheckboxValue('om_acqdata_hist_enabled',           tableConfig.acqdata_hist && tableConfig.acqdata_hist.enabled);
    setOmControlValue ('om_acqdata_hist_retentionTime',     tableConfig.acqdata_hist && tableConfig.acqdata_hist.retentionTime);

    setOmCheckboxValue('om_acqrawdata_enabled',             tableConfig.acqrawdata && tableConfig.acqrawdata.enabled);
    setOmControlValue ('om_acqrawdata_retentionTime',       tableConfig.acqrawdata && tableConfig.acqrawdata.retentionTime);

    setOmCheckboxValue('om_alarminfo_hist_enabled',         tableConfig.alarminfo_hist && tableConfig.alarminfo_hist.enabled);
    setOmControlValue ('om_alarminfo_hist_retentionTime',   tableConfig.alarminfo_hist && tableConfig.alarminfo_hist.retentionTime);

    setOmCheckboxValue('om_dailytotalcalculate_hist_enabled',        tableConfig.dailytotalcalculate_hist && tableConfig.dailytotalcalculate_hist.enabled);
    setOmControlValue ('om_dailytotalcalculate_hist_retentionTime',  tableConfig.dailytotalcalculate_hist && tableConfig.dailytotalcalculate_hist.retentionTime);

    setOmCheckboxValue('om_dailycalculationdata_enabled',        tableConfig.dailycalculationdata && tableConfig.dailycalculationdata.enabled);
    setOmControlValue ('om_dailycalculationdata_retentionTime',  tableConfig.dailycalculationdata && tableConfig.dailycalculationdata.retentionTime);

    setOmCheckboxValue('om_timingcalculationdata_enabled',        tableConfig.timingcalculationdata && tableConfig.timingcalculationdata.enabled);
    setOmControlValue ('om_timingcalculationdata_retentionTime',  tableConfig.timingcalculationdata && tableConfig.timingcalculationdata.retentionTime);

    setOmCheckboxValue('om_timingrecorddata_enabled',        tableConfig.timingrecorddata && tableConfig.timingrecorddata.enabled);
    setOmControlValue ('om_timingrecorddata_retentionTime',  tableConfig.timingrecorddata && tableConfig.timingrecorddata.retentionTime);

    setOmCheckboxValue('om_acqdata_vacuate_enabled',        tableConfig.acqdata_vacuate && tableConfig.acqdata_vacuate.enabled);
    setOmControlValue ('om_acqdata_vacuate_retentionTime',  tableConfig.acqdata_vacuate && tableConfig.acqdata_vacuate.retentionTime);

    setOmControlValue('om_vacuateRecord',                  dataVacuate.vacuateRecord);
    setOmControlValue('om_vacuateSaveInterval',            dataVacuate.saveInterval);
    setOmControlValue('om_vacuateSaveIntervalWaveRange',   dataVacuate.saveIntervalWaveRange);
    setOmControlValue('om_vacuateThreshold',               dataVacuate.vacuateThreshold);

    setOmControlValue('om_reportOffsetHour', report.offsetHour);
    setOmControlValue('om_reportInterval',   report.interval);

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
            mainTabs.updateTab(tabs[0], { title: R.automatedOperationsManagement });
            mainTabs.updateTab(tabs[1], { title: R.backupAndRecovery });
            mainTabs.updateTab(tabs[2], { title: R.oemConfig });
            mainTabs.updateTab(tabs[3], { title: R.tagManagement });
            mainTabs.updateTab(tabs[4], { title: R.memoryCurve });
            mainTabs.updateTab(tabs[5], { title: R.lowerComputerProgramUpgrade });
        }
    }

    var backupTabs = mini.get('omBackupTabs');
    if (backupTabs) {
        var bTabs = backupTabs.getTabs();
        if (bTabs && bTabs.length >= 2) {
            backupTabs.updateTab(bTabs[0], { title: R.exportData });
            backupTabs.updateTab(bTabs[1], { title: R.importData });
        }
    }

    var tmTabs = mini.get('omTabManagerTabs');
    if (tmTabs) {
        var tTabs = tmTabs.getTabs();
        if (tTabs && tTabs.length >= 2) {
            tmTabs.updateTab(tTabs[0], { title: R.projectTag });
            tmTabs.updateTab(tTabs[1], { title: R.deviceTag });
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
        'omLegendBasicInfo':            'basicInformation',
        'omLegendHistoricalData':       'historicalDataMaintenance',
        'omLegendDataVacuate':          'dataSparseness',
        'omLegendReportConfig':         'reportConfig',
        'omLegendProjectInfo':          '项目名称及简介',
        'omLegendBackgroundIcon':       '背景及图标',
        'omLegendRealtimeMonitoring':   'realtimeMonitoringModule',
        'omLegendHistoryQuery':         'historyQueryModule',
        'omLegendAlarmQuery':           'alarmQueryModule',
        'omLegendRealtimeMonitoring2':  'realtimeMonitoringModule',
        'omLegendHistoryQuery2':        'historyQueryModule',
        'omLegendPrimaryDevice':        'primaryDeviceModule'
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

// ================================================================
// 初始化标签
// ================================================================
function initOmLabels() {
    var R = _loginUserLanguageResource;
    var labelMap = {
        'omLblLoginLanguage':                   'loginInterfaceLanguage',
        'omLblTimeEfficiencyUnit':              'timeEfficiencyUnit',
        'omLblSimulateAcqEnable':               'sendSimulationData',
        'omLblExportLimit':                     'exportDataLimits',
        'omLblShowLogo':                        'displayTheLogo',
        'omLblSendCycle':                       'simulateDataSendingCycles',
        'omLblResourceMonitoringSaveData':      'resourceMonitoringLimit',
        'omLblPrintExceptionLog':               'printExceptionLogs',
        'omLblPrintLog':                        'printLogs',
        'omLblPrintAdLog':                      'printAdLogs',
        'omLblDbCycle':                         'executionCycle',
        'omLblDbStartTime':                     'executionTime',
        'omLblDbEndTime':                       'endTime',
        'omLblAcqdataHistEnabled':              'historicalDataTable',
        'omLblAcqdataHistRetention':            'dataRetentionTime',
        'omLblAcqrawdataEnabled':               'sourceDataTable',
        'omLblAcqrawdataRetention':             'dataRetentionTime',
        'omLblAlarminfoHistEnabled':            'alarmHistoryTable',
        'omLblAlarminfoHistRetention':          'dataRetentionTime',
        'omLblDailyTotalEnabled':               'dailyTotalCalculateTable',
        'omLblDailyTotalRetention':             'dataRetentionTime',
        'omLblDailyCalcEnabled':                'dailyCalculationTable',
        'omLblDailyCalcRetention':              'dataRetentionTime',
        'omLblTimingCalcEnabled':               'timingCalculationTable',
        'omLblTimingCalcRetention':             'dataRetentionTime',
        'omLblTimingRecordEnabled':             'timingRecordTable',
        'omLblTimingRecordRetention':           'dataRetentionTime',
        'omLblAcqdataVacuateEnabled':           'acqdataVacuateTable',
        'omLblAcqdataVacuateRetention':         'dataRetentionTime',
        'omLblVacuateRecord':                   'sparseRecordCount',
        'omLblVacuateSaveIntervalWaveRange':    'vacuateSaveIntervalWaveRange',
        'omLblVacuateSaveInterval':             'vacuateSaveInterval',
        'omLblVacuateThreshold':                'vacuateThreshold',
        'omLblReportOffsetHour':                'offsetTime',
        'omLblReportInterval':                  'deviceHourlyReportInterval',
        'omLblProjectName':                     '项目名称',
        'omLblProjectProfile':                  '项目简介',
        'omLblProjectLogo':                     '项目logo',
        'omLblProjectFavicon':                  '网页logo',
        'omLblLoginBackgroundImage':            '登录界面背景图',
        'omLblHelpButtonIcon':                  '帮助按钮图标',
        'omLblExitButtonIcon':                  '退出按钮图标',
        'omLblSwitchButtonIcon':                '语言切换按钮图标',
        'omLblSwitchDisabledButtonIcon':        '禁用语言切换按钮图标',
        'omLblFESDiagramStatPie':               'FESDiagramStatPie',
        'omLblCommStatusStatPie':               'CommStatusStatPie',
        'omLblRunStatusStatPie':                'RunStatusStatPie',
        'omLblNumStatusStatPie':                'NumStatusStatPie',
        'omLblHistoryFESDiagramStatPie':        'FESDiagramStatPie',
        'omLblHistoryCommStatusStatPie':        'CommStatusStatPie',
        'omLblHistoryRunStatusStatPie':         'RunStatusStatPie',
        'omLblHistoryNumStatusStatPie':         'NumStatusStatPie',
        'omLblAlarmFESDiagramResultAlarm':      'FESDiagramResultAlarm',
        'omLblAlarmRunStatusAlarm':             'RunStatusAlarm',
        'omLblAlarmCommStatusAlarm':            'CommStatusAlarm',
        'omLblAlarmNumericValueAlarm':          'NumericValueAlarm',
        'omLblAlarmEnumValueAlarm':             'EnumValueAlarm',
        'omLblAlarmSwitchingValueAlarm':        'SwitchingValueAlarm',
        'omLblWellboreAnalysis':                'wellboreAnalysis',
        'omLblSurfaceAnalysis':                 'surfaceAnalysis',
        'omLblTrendCurve':                      'trendCurve',
        'omLblDynamicData':                     'dynamicData',
        'omLblDeviceControl':                   'deviceControl',
        'omLblDeviceInformation':               'deviceInformation',
        'omLblRodStressChartMax':               'maxRodStress',
        'omLblRodStressChartRange':             'rodStressRange',
        'omLblHistoryTrendCurve':               'trendCurve',
        'omLblHistoryTiledDiagram':             'tiledDiagram',
        'omLblHistoryDiagramOverlay':           'diagramOverlay',
        'omLblAdditionalInformation':           'additionalInformation',
        'omLblAuxiliaryDevice':                 'auxiliaryDevice',
        'omLblVideoConfig':                     'videoConfig',
        'omLblCalculateDataConfig':             'calculateDataConfig',
        'omLblFSDiagramConstruction':           'fsDiagramConstruction',
        'omLblSystemParameterConfiguration':    'systemParameterConfiguration',
        'omLblIntelligentFrequencyConversion':  'intelligentFrequencyConversion',
        'omLblInterlockProtection':             'interlockProtection',
        'omLblCurveRange':                      'range',
        'omLblCurveTo':                         'timeTo',
        'omLblLowerComputerDeviceName':         'deviceName'
    };
    for (var id in labelMap) {
        var el = document.getElementById(id);
        if (!el) continue;
        var key = labelMap[id];
        if (R[key]) {
            if (id == 'omLblSendCycle') {
                el.textContent = R[key] + '(s)：';
            } else if (id == 'omLblDbCycle'
                || id == 'omLblAcqdataHistRetention'
                || id == 'omLblAcqrawdataRetention'
                || id == 'omLblAlarminfoHistRetention'
                || id == 'omLblDailyTotalRetention'
                || id == 'omLblDailyCalcRetention'
                || id == 'omLblTimingCalcRetention'
                || id == 'omLblTimingRecordRetention'
                || id == 'omLblAcqdataVacuateRetention') {
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
        'omBasicRefreshBtn':              'refresh',
        'omBasicSaveBtn':                 'save',
        'omBackupSelectAllBtn':           'selectAll',
        'omBackupDeselectAllBtn':         'deselectAll',
        'omOneKeyBackupBtn':              'exportData',
        'omImportPrevBtn':                'previousStep',
        'omImportSaveBtn':                'save',
        'omImportNextBtn':                'nextStep',
        'omOemSaveBtn':                   'save',
        'omProjectTagRefreshBtn':         'refresh',
        'omProjectTagSaveBtn':            'save',
        'omDeviceTagRefreshBtn':          'refresh',
        'omDeviceTagAddBtn':              'add',
        'omDeviceTagDelBtn':              'deleteData',
        'omDeviceTagSaveBtn':             'save',
        'omCurveRefreshBtn':              'refresh',
        'omCurveSearchBtn':               'search',
        'omLowerComputerRefreshBtn':      'refresh',
        'omLowerComputerSelectAllBtn':    'selectAll',
        'omLowerComputerDeselectAllBtn':  'deselectAll',
        'omBoxUpgradeBtn':                'boxProgramUpgrade',
        'omAcUpgradeBtn':                 'acProgramUpgrade',
        'omLowerComputerUplinkBtn':       'statusDetection'
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
            { id: 1, text: R.decimals },
            { id: 2, text: R.percent }
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

// ================================================================
// 主 Tab 切换事件
// ================================================================
function onOmTabChanged(e) {
    if (isInitializing) return;
    var tab = e.tab;
    if (!tab) return;
    console.log('[运维配置] 主 Tab 切换：', tab.name);

    if (tab.name === 'backup') {
        checkAndLoadBackupTab();
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
        loginLanguage:              getOmValue('om_loginLanguage'),
        showLogo:                   getOmCheckboxValue('om_showLogo'),
        printLog:                   getOmCheckboxValue('om_printLog'),
        printAdLog:                 getOmCheckboxValue('om_printAdLog'),
        printExceptionLog:          getOmCheckboxValue('om_printExceptionLog'),
        timeEfficiencyUnit:         getOmValue('om_timeEfficiencyUnit') == 1 ? 1 : 2,
        resourceMonitoringSaveData: getOmValue('om_resourceMonitoringSaveData'),
        exportLimit:                getOmValue('om_exportLimit'),
        simulateAcqEnable:          getOmCheckboxValue('om_simulateAcqEnable'),
        sendCycle:                  getOmValue('om_sendCycle')
    };

    configFile.databaseMaintenance = {
        cycle:     getOmValue('om_databaseMaintenanceCycle'),
        startTime: getOmValue('om_databaseMaintenanceStartTime'),
        endTime:   getOmValue('om_databaseMaintenanceEndTime'),
        tableConfig: {
            acqdata_hist:               { enabled: getOmCheckboxValue('om_acqdata_hist_enabled'),           retentionTime: getOmValue('om_acqdata_hist_retentionTime') },
            acqrawdata:                 { enabled: getOmCheckboxValue('om_acqrawdata_enabled'),             retentionTime: getOmValue('om_acqrawdata_retentionTime') },
            alarminfo_hist:             { enabled: getOmCheckboxValue('om_alarminfo_hist_enabled'),         retentionTime: getOmValue('om_alarminfo_hist_retentionTime') },
            dailytotalcalculate_hist:   { enabled: getOmCheckboxValue('om_dailytotalcalculate_hist_enabled'), retentionTime: getOmValue('om_dailytotalcalculate_hist_retentionTime') },
            dailycalculationdata:       { enabled: getOmCheckboxValue('om_dailycalculationdata_enabled'),   retentionTime: getOmValue('om_dailycalculationdata_retentionTime') },
            timingcalculationdata:      { enabled: getOmCheckboxValue('om_timingcalculationdata_enabled'),  retentionTime: getOmValue('om_timingcalculationdata_retentionTime') },
            timingrecorddata:           { enabled: getOmCheckboxValue('om_timingrecorddata_enabled'),       retentionTime: getOmValue('om_timingrecorddata_retentionTime') },
            acqdata_vacuate:            { enabled: getOmCheckboxValue('om_acqdata_vacuate_enabled'),        retentionTime: getOmValue('om_acqdata_vacuate_retentionTime') }
        }
    };

    configFile.dataVacuate = {
        vacuateRecord:                getOmValue('om_vacuateRecord'),
        saveInterval:                 getOmValue('om_vacuateSaveInterval'),
        saveIntervalWaveRange:        getOmValue('om_vacuateSaveIntervalWaveRange'),
        vacuateThreshold:             getOmValue('om_vacuateThreshold')
    };

    configFile.report = {
        offsetHour: getOmValue('om_reportOffsetHour'),
        interval:   getOmValue('om_reportInterval')
    };

    var maskEl = 'omBasicPanel';
    mini.mask({ el: maskEl, cls: 'mini-mask-loading', html: R.submittingData });

    $.ajax({
        url: context + '/operationMaintenanceController/updateOemConfigInfo',
        type: 'POST',
        data: { configFile: JSON.stringify(configFile) },
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
        'MODULE':          { url: context + '/moduleManagerController/exportModuleCompleteData',                     fileName: R.moduleExportFileName,           keyPrefix: 'exportModuleCompleteData' },
        'DATADICTIONARY':  { url: context + '/systemdataInfoController/exportDataDictionaryCompleteData',           fileName: R.dataDictionaryExportFileName,   keyPrefix: 'exportDataDictionaryCompleteData' },
        'ORGANIZATION':    { url: context + '/orgManagerController/exportOrganizationCompleteData',                 fileName: R.organizationExportFileName,     keyPrefix: 'exportOrganizationCompleteData' },
        'ROLE':            { url: context + '/roleManagerController/exportRoleCompleteData',                        fileName: R.roleExportFileName,             keyPrefix: 'exportRoleCompleteData' },
        'USER':            { url: context + '/userManagerController/exportUserCompleteData',                        fileName: R.userExportFileName,             keyPrefix: 'exportUserCompleteData' },
        'AUXILIARYDEVICE': { url: context + '/wellInformationManagerController/exportAuxiliaryDeviceCompleteData',  fileName: R.auxiliaryDdeviceExportFileName, keyPrefix: 'exportAuxiliaryDeviceBackupData' },
        'PRIMARYDEVICE':   { url: context + '/wellInformationManagerController/exportDeviceCompleteData',           fileName: R.primaryDdeviceExportFileName,   keyPrefix: 'exportPrimaryDeviceBackupData' },
        'PROTOCOL':        { url: context + '/acquisitionUnitManagerController/exportAllProtocolData',              fileName: R.exportProtocol,                 keyPrefix: 'exportProtocolBackupData' },
        'ACQUNIT':         { url: context + '/acquisitionUnitManagerController/exportAllProtocolAcqUnitData',       fileName: R.exportAcqUnit,                  keyPrefix: 'exportAllProtocolAcqUnitData' },
        'DISPLAYUNIT':     { url: context + '/acquisitionUnitManagerController/exportAllProtocolDisplayUnitData',   fileName: R.exportDisplayUnit,              keyPrefix: 'exportAllProtocolDisplayUnitData' },
        'ALARMUNIT':       { url: context + '/acquisitionUnitManagerController/exportAllProtocolAlarmUnitData',     fileName: R.exportAlarmUnit,                keyPrefix: 'exportAllProtocolAlarmUnitData' },
        'REPORTUNIT':      { url: context + '/acquisitionUnitManagerController/exportAllReportUnitData',            fileName: R.exportReportUnit,               keyPrefix: 'exportAllReportUnitData' },
        'ACQINSTANCE':     { url: context + '/acquisitionUnitManagerController/exportAllProtocolAcqInstanceData',   fileName: R.exportAcqInstance,              keyPrefix: 'exportAllProtocolAcqInstanceData' },
        'DISPLAYINSTANCE': { url: context + '/acquisitionUnitManagerController/exportAllProtocolDisplayInstanceData', fileName: R.exportDisplayInstance,        keyPrefix: 'exportAllProtocolDisplayInstanceData' },
        'ALARMINSTANCE':   { url: context + '/acquisitionUnitManagerController/exportAllProtocolAlarmInstanceData', fileName: R.exportAlarmInstance,            keyPrefix: 'exportAllProtocolAlarmInstanceData' },
        'REPORTINSTANCE':  { url: context + '/acquisitionUnitManagerController/exportAllReportInstanceData',        fileName: R.exportReportInstance,           keyPrefix: 'exportReportInstanceBackupData' }
    };
    return map[String(code).toUpperCase()] || null;
}

function commonExport(url, fileName, keyPrefix) {
    var timestamp = new Date().getTime();
    var key = keyPrefix + '_' + timestamp;
    var maskPanelId = 'BatchExportModuleGridPanel_Id';

    var param = "&recordCount=10000"
              + "&fileName=" + URLencode(URLencode(fileName))
              + '&key=' + key;

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
                        return '<span title="' + String(value).replace(/"/g, '&quot;') + '">'
                            + String(value).replace(/"/g, '&quot;') + '</span>';
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
        var titleText = '【<font color="red">' + (record.text || '') + '</font>】'
            + _loginUserLanguageResource.importData + '&nbsp;';
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
        data: { code: code },
        dataType: 'json',
        success: function (result) {
            var sign = result && result.success && result.msg;
            callback({ sign: sign, info: result.msg || '' });
        },
        error: function () {
            callback({ sign: false, info: _loginUserLanguageResource.requestFailed });
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
        type: 'indexcolumn', width: 50,
        header: R.idx, headerAlign: 'center', align: 'center'
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
            saveUrl:    '/moduleManagerController/saveAllImportedModule',
            idField:    'mdId',
            parentField:'mdParentid',
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
            saveUrl:    '/systemdataInfoController/saveAllImportedDataDictionary',
            columns: [
                indexCol,
                { field: 'name', header: R.dataModuleName, headerAlign: 'center', align: 'center', width: '20%' },
                { field: 'code', header: R.dataModuleCode, headerAlign: 'center', align: 'center', width: '30%' },
                collisionCol('50%')
            ]
        }),

        // ============================================================
        // 组织机构
        // ============================================================
        'ORGANIZATION': treeConfig({
            uploadUrl: '/orgManagerController/uploadImportedOrganizationFile',
            previewUrl: '/orgManagerController/getUploadedOrganizationTreeData',
            saveUrl:    '/orgManagerController/saveAllImportedOrganization',
            idField:    'orgId',
            parentField:'orgParentid',
            columns: [
                treeTextCol(R.orgName, '30%'),
                { field: 'orgSeq', header: R.sequenceNumber, headerAlign: 'left', align: 'left', width: '20%' },
                collisionCol('50%')
            ]
        }),

        // ============================================================
        // 角色
        // ============================================================
        'ROLE': gridConfig({
            uploadUrl: '/roleManagerController/uploadImportedRoleFile',
            previewUrl: '/roleManagerController/getUploadedRoleTreeData',
            saveUrl:    '/roleManagerController/saveAllImportedRole',
            columns: [
                indexCol,
                { field: 'roleName_'+loginUserLanguage, header: R.roleName, headerAlign: 'center', align: 'center', width: 150 },
                { field: 'roleLevel', header: R.roleLevel, headerAlign: 'center', align: 'center', type: 'spinner',width: 100 },
                { field: 'showLevel', header: R.dataShowLevel, headerAlign: 'center', align: 'center',type: 'spinner', width: 120 },
                { field: 'roleVideoKeyEditName', header: R.roleVideoKeyEdit, headerAlign: 'center', align: 'center', trueValue: true,falseValue: false,type: 'checkboxcolumn',width: 130},
                { field: 'roleLanguageEditName', header: R.roleLanguageEdit, headerAlign: 'center', align: 'center', trueValue: true,falseValue: false,type: 'checkboxcolumn',width: 130},
                { field: 'remark_'+loginUserLanguage, header: R.roleRemark, headerAlign: 'center', align: 'center', width: 200 },
                collisionCol('auto')
            ]
        }),

        // ============================================================
        // 用户
        // ============================================================
        'USER': gridConfig({
            uploadUrl: '/userManagerController/uploadImportedUserFile',
            previewUrl: '/userManagerController/getUploadedUserTreeData',
            saveUrl:    '/userManagerController/saveAllImportedUser',
            columns: [
                indexCol,
                { field: 'userName',     header: R.userName,     headerAlign: 'center', align: 'center', width: 150 },
                { field: 'userId',       header: R.userAccount,  headerAlign: 'center', align: 'center', width: 150 },
                { field: 'userTypeName', header: R.role,         headerAlign: 'center', align: 'center', width: 120 },
                { field: 'userPhone',    header: R.phone,        headerAlign: 'center', align: 'center', width: 130 },
                { field: 'userInEmail',  header: R.email,        headerAlign: 'center', align: 'center', width: 200 },
                { field: 'userQuickLoginName', header: R.userQuickLogin, headerAlign: 'center', align: 'center', width: 100,
                  renderer: function(e){ return e.value ? '✓' : ''; } },
                { field: 'receiveSMSName',  header: R.receiveSMS,  headerAlign: 'center', align: 'center', width: 100,
                  renderer: function(e){ return e.value ? '✓' : ''; } },
                { field: 'receiveMailName', header: R.receiveMail, headerAlign: 'center', align: 'center', width: 100,
                  renderer: function(e){ return e.value ? '✓' : ''; } },
                { field: 'userEnableName',  header: R.status,      headerAlign: 'center', align: 'center', width: 80,
                  renderer: function(e){ return e.value ? '✓' : ''; } },
                collisionCol('auto')
            ]
        }),

        // ============================================================
        // 辅助设备
        // ============================================================
        'AUXILIARYDEVICE': gridConfig({
            uploadUrl: '/wellInformationManagerController/uploadAuxiliaryDeviceBackupData',
            previewUrl: '/wellInformationManagerController/getUploadedAuxiliaryDeviceTreeData',
            saveUrl:    '/wellInformationManagerController/saveAuxiliaryDeviceBackupData',
            columns: [
                indexCol,
                { field: 'name',         header: R.deviceName,    headerAlign: 'center', align: 'center', width: 150 },
                { field: 'manufacturer', header: R.manufacturer,  headerAlign: 'center', align: 'center', width: 150 },
                { field: 'model',        header: R.model,         headerAlign: 'center', align: 'center', width: 150 },
                { field: 'remark',       header: R.remark,        headerAlign: 'center', align: 'center', width: 200 },
                { field: 'sort',         header: R.sequenceNumber, headerAlign: 'center', align: 'center', width: 80 },
                collisionCol('auto')
            ]
        }),

        // ============================================================
        // 主设备
        // ============================================================
        'PRIMARYDEVICE': gridConfig({
            uploadUrl: '/wellInformationManagerController/uploadPrimaryDeviceBackupData',
            previewUrl: '/wellInformationManagerController/getUploadedPrimaryDeviceTreeData',
            saveUrl:    '/wellInformationManagerController/savePrimaryDeviceBackupData',
            columns: [
                indexCol,
                { field: 'deviceName',         header: R.deviceName,       headerAlign: 'center', align: 'center', width: 140 },
                { field: 'instanceName',       header: R.acqInstance,      headerAlign: 'center', align: 'center', width: 130 },
                { field: 'displayInstanceName',header: R.displayInstance,  headerAlign: 'center', align: 'center', width: 130 },
                { field: 'alarmInstanceName',  header: R.alarmInstance,    headerAlign: 'center', align: 'center', width: 130 },
                { field: 'reportInstanceName', header: R.reportInstance,   headerAlign: 'center', align: 'center', width: 130 },
                { field: 'tcpType',            header: R.deviceTcpType,    headerAlign: 'center', align: 'center', width: 100 },
                { field: 'signInId',           header: R.signInId,         headerAlign: 'center', align: 'center', width: 100 },
                { field: 'ipPort',             header: R.ipPort,           headerAlign: 'center', align: 'center', width: 130 },
                { field: 'slave',              header: R.slave,            headerAlign: 'center', align: 'center', width: 60 },
                { field: 'peakDelay',          header: R.peakDelay,        headerAlign: 'center', align: 'center', width: 80 },
                { field: 'sortNum',            header: R.sequenceNumber,   headerAlign: 'center', align: 'center', width: 80 },
                collisionCol('auto')
            ]
        }),

        // ============================================================
        // 协议 / 采集单元 / 显示单元 / 报警单元 / 报表单元
        // 采集实例 / 显示实例 / 报警实例 / 报表实例
        // ============================================================
        'PROTOCOL': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedProtocolFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedProtocolTreeData',
            saveUrl:    '/acquisitionUnitManagerController/saveProtocolBackupData',
            idField:    'id',
            parentField:'pid',
            columns: [ treeTextCol('预导入协议', '30%'), collisionCol('70%') ]
        }),
        'ACQUNIT': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedAcqUnitFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedAcqUnitTreeData',
            saveUrl:    '/acquisitionUnitManagerController/saveAcqUnitBackupData',
            idField:    'id',
            parentField:'pid',
            columns: [ treeTextCol('预导入单元', '30%'), collisionCol('70%') ]
        }),
        'DISPLAYUNIT': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedDisplayUnitFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedDisplayUnitTreeData',
            saveUrl:    '/acquisitionUnitManagerController/saveDisplayUnitBackupData',
            idField:    'id',
            parentField:'pid',
            columns: [ treeTextCol('预导入单元', '30%'), collisionCol('70%') ]
        }),
        'ALARMUNIT': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedAlarmUnitFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedAlarmUnitTreeData',
            saveUrl:    '/acquisitionUnitManagerController/saveAlarmUnitBackupData',
            idField:    'id',
            parentField:'pid',
            columns: [ treeTextCol('预导入单元', '30%'), collisionCol('70%') ]
        }),
        'REPORTUNIT': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedReportUnitFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedReportUnitTreeData',
            saveUrl:    '/acquisitionUnitManagerController/saveReportUnitBackupData',
            idField:    'id',
            parentField:'pid',
            columns: [ treeTextCol('预导入单元', '30%'), collisionCol('70%') ]
        }),
        'ACQINSTANCE': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedAcqInstanceFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedAcqInstanceTreeData',
            saveUrl:    '/acquisitionUnitManagerController/saveAcqInstanceBackupData',
            idField:    'id',
            parentField:'pid',
            columns: [ treeTextCol('预导入实例', '30%'), collisionCol('70%') ]
        }),
        'DISPLAYINSTANCE': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedDisplayInstanceFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedDisplayInstanceTreeData',
            saveUrl:    '/acquisitionUnitManagerController/saveDisplayInstanceBackupData',
            idField:    'id',
            parentField:'pid',
            columns: [ treeTextCol('预导入实例', '30%'), collisionCol('70%') ]
        }),
        'ALARMINSTANCE': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedAlarmInstanceFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedAlarmInstanceTreeData',
            saveUrl:    '/acquisitionUnitManagerController/saveAlarmInstanceBackupData',
            idField:    'id',
            parentField:'pid',
            columns: [ treeTextCol('预导入实例', '30%'), collisionCol('70%') ]
        }),
        'REPORTINSTANCE': treeConfig({
            uploadUrl: '/acquisitionUnitManagerController/uploadImportedReportInstanceFile',
            previewUrl: '/acquisitionUnitManagerController/getUploadedReportInstanceTreeData',
            saveUrl:    '/acquisitionUnitManagerController/saveReportInstanceBackupData',
            idField:    'id',
            parentField:'pid',
            columns: [ treeTextCol('预导入实例', '30%'), collisionCol('70%') ]
        })
    };
}

// 冲突信息颜色渲染
function adviceOmImportCollisionColor(val, record) {
    var saveSign = record ? record.saveSign : 0;
    var color = (saveSign == 0) ? '#000000' : '#DC2828';
    if (val) {
        return '<span style="color:' + color + ';" title="'
            + String(val).replace(/"/g, '&quot;') + '">'
            + String(val).replace(/"/g, '&quot;') + '</span>';
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
                doSaveOmImport(config.saveUrl);
            }
        });
    } else {
        doSaveOmImport(config.saveUrl);
    }
}

function doSaveOmImport(saveUrl) {
    var panelId = 'OperationMaintenanceDataImportPanel_Id';
    mini.mask({ el: panelId, cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });

    $.ajax({
        url: context + saveUrl,
        type: 'POST',
        dataType: 'json',
        success: function (result) {
            mini.unmask(panelId);
            if (result && result.success === true) {
                mini.alert(_loginUserLanguageResource.savedSuccessfully, _loginUserLanguageResource.tip);
                mini.get('omImportSaveBtn').setEnabled(false);
                _omImportPreview.destroy();
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
    mini.mask({ el: maskEl, cls: 'mini-mask-loading', html: _loginUserLanguageResource.uploadingFile });

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
    var HOST_ID      = '_omImportPreviewHost_';
    var TREE_ID      = 'OmImportPreviewTreeGrid_Id';
    var GRID_ID      = 'OmImportPreviewDataGrid_Id';

    // 销毁旧组件 + 清空容器
    function _destroy() {
        var tree = mini.get(TREE_ID);
        if (tree) { try { tree.destroy(); } catch (e) {} }
        var grid = mini.get(GRID_ID);
        if (grid) { try { grid.destroy(); } catch (e) {} }
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
            id:           TREE_ID,
            style:        'width:100%;height:100%;',

            // ★ 树形相关全部从 config 读
            idField:      config.idField      || 'id',
            parentField:  config.parentField  || 'pid',
            textField:    config.textField    || 'text',
            dataField:    config.dataField    || 'children',
            treeColumn:   config.treeColumn   || 'taskname',

            resultAsTree: true,
            showTreeIcon: true,

            // ★ 网格线开关从 config 读
            showHGridLines: (config.showHGridLines !== undefined) ? config.showHGridLines : false,
            showVGridLines: (config.showVGridLines !== undefined) ? config.showVGridLines : false,

            allowResize:  false,
            showPager:    false,
            showEmptyText:true,
            autoLoad:     false,

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
            id:           GRID_ID,
            style:        'width:100%;height:100%;',

            idField:      config.idField    || 'id',
            dataField:    config.dataField  || 'totalRoot',
            totalField:   config.totalField || 'totalCount',

            showHGridLines: (config.showHGridLines !== undefined) ? config.showHGridLines : false,
            showVGridLines: (config.showVGridLines !== undefined) ? config.showVGridLines : false,

            allowResize:  false,
            allowAlternating: true,
            showPager:    false,
            pageSize:     100,
            showPageInfo: false,
            multiSelect:  false,
            allowCellEdit:false,
            allowCellSelect:false,
            showEmptyText:true,

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

        getConflicts: function () {
            var counts = { overlay: 0, collision: 0 };
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

// ================================================================
// 事件占位（后续逐步实现）
// ================================================================
function onOmOemSave()           { console.log('[运维配置] OEM-保存'); }
function onOmUpload(type)        { console.log('[运维配置] OEM-上传', type); }
function onOmProjectTagRefresh() { console.log('[运维配置] 项目标签-刷新'); }
function onOmProjectTagSave()    { console.log('[运维配置] 项目标签-保存'); }
function onOmDeviceTypeTreeLoad(e) { console.log('[运维配置] 设备类型树加载'); }
function onOmDeviceTypeNodeSelect(e) { console.log('[运维配置] 设备类型节点选择', e.node); }
function onOmDeviceTagRefresh()  { console.log('[运维配置] 设备标签-刷新'); }
function onOmDeviceTagAdd()      { console.log('[运维配置] 设备标签-添加'); }
function onOmDeviceTagDel()      { console.log('[运维配置] 设备标签-删除'); }
function onOmDeviceTagSave()     { console.log('[运维配置] 设备标签-保存'); }
function onOmDeviceTabTreeLoad(e) { console.log('[运维配置] 设备标签树加载'); }
function onOmDeviceTabNodeSelect(e) { console.log('[运维配置] 设备标签节点选择', e.node); }
function onOmCurveRefresh()      { console.log('[运维配置] 曲线-刷新'); }
function onOmCurveSearch()       { console.log('[运维配置] 曲线-搜索'); }
function onOmLowerComputerRefresh() { console.log('[运维配置] 下位机-刷新'); }
function onOmLowerComputerDeviceChange(e) { console.log('[运维配置] 下位机-设备切换', e.value); }
function onOmLowerComputerSelectAll()    { console.log('[运维配置] 下位机-全选'); }
function onOmLowerComputerDeselectAll()  { console.log('[运维配置] 下位机-取消全选'); }
function onOmBoxUpgrade()        { console.log('[运维配置] 下位机-盒体程序升级'); }
function onOmAcUpgrade()         { console.log('[运维配置] 下位机-交流程序升级'); }
function onOmLowerComputerUplink() { console.log('[运维配置] 下位机-状态检测'); }