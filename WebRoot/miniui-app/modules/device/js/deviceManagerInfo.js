// ================================================================
// 主设备模块 - deviceManagerInfo.js
// ================================================================

// ---------- 全局 Handsontable 对象 ----------
var deviceInfoHandsontableHelper = null;
var productionHandsontableHelper = null;
var pumpingInfoHandsontableHelper = null;
var devicePumpingUnitPRTFHandsontableHelper = null;
var devicePumpingUnitDetailedInformationHandsontableHelper = null;
var videoInfoHandsontableHelper = null;

var deviceAuxiliaryDeviceInfoHandsontableHelper = null;
var deviceAdditionalInfoHandsontableHelper = null;

var fsDiagramConstructionHandsontableHelper = null;
var deviceSystemParameterHandsontableHelper = null;

var deviceIntelligentFrequencyConversionHandsontableHelper = null;
var deviceInterlockProtectionHandsontableHelper = null;

// ---------- 全局状态 ----------
var _dmTabInfo = null;
var _dmLevel1Data = [];
var _dmLevel2Data = [];
var _dmCurrentLevel1 = null;
var _dmCurrentLevel2 = null;
var _dmModuleRight = { viewFlag: 0, editFlag: 0, controlFlag: 0 };

// ---------- 设备列表相关状态 ----------
var _dmDeviceSelectRow    = 0;
var _dmDeviceSelectEndRow = 0;
var _dmSelectedDeviceId   = 0;
var _dmDeviceTotalCount   = 0;

// ---------- 当前设备上下文（右侧各表加载时使用） ----------
var _dmCurrentDeviceName           = '';
var _dmCurrentApplicationScenarios = 0;
var _dmCurrentCalculateType        = 0;

var isInitializing = true;

// ================================================================
// 页面初始化
// ================================================================
function initDeviceManagerPage() {
    try {
        if (window.parent && window.parent.tabInfo) {
            _dmTabInfo = window.parent.tabInfo;
        }
    } catch (e) {
        console.warn('无法获取 tabInfo', e);
    }

    _dmModuleRight = getRoleModuleRight(context + '/roleManagerController/getRoleModuleRight', 'WellInformation') || { viewFlag: 0, editFlag: 0, controlFlag: 0 };
    _dmModuleRight.viewFlag    = parseInt(_dmModuleRight.viewFlag)    || 0;
    _dmModuleRight.editFlag    = parseInt(_dmModuleRight.editFlag)    || 0;
    _dmModuleRight.controlFlag = parseInt(_dmModuleRight.controlFlag) || 0;

    initDeviceManagerI18n();
    initDeviceManagerMessageListener();
    updateDmBtnStatus();
    buildDmLevel1Tabs();
    initDmCombos();
    
    isInitializing = false;
}

// ================================================================
// 国际化
// ================================================================
function initDeviceManagerI18n() {
    var R = _loginUserLanguageResource;

    var refreshBtn = mini.get('dmRefreshBtn');
    if (refreshBtn) refreshBtn.setText(R.refresh);
    var queryBtn = mini.get('dmQueryBtn');
    if (queryBtn) queryBtn.setText(R.search);

    var deviceNameLabel = document.getElementById('dmDeviceNameLabel');
    if (deviceNameLabel) deviceNameLabel.textContent = R.deviceName + '：';
    var signInIdLabel = document.getElementById('dmSignInIdLabel');
    if (signInIdLabel) signInIdLabel.textContent = R.signInId + '：';

    var totalLabel = document.getElementById('dmDeviceTotalCountLabel');
    if (totalLabel) totalLabel.textContent = R.totalCount + '：0';

    var deviceCombo = mini.get('dmDeviceCombo');
    if (deviceCombo) deviceCombo.setEmptyText('--' + R.all + '--');
    var signInIdCombo = mini.get('dmSignInIdCombo');
    if (signInIdCombo) signInIdCombo.setEmptyText('--' + R.all + '--');

    var btnMap = {
        'dmAddDeviceBtn':                     'addDevice',
        'dmDelDeviceBtn':                     'deleteDevice',
        'dmSaveDeviceBtn':                    'save',
        'dmBatchAddDeviceBtn':                'batchAdd',
        'dmDeviceOrgChangeBtn':               'deviceOrgChange',
        'dmExportDeviceBtn':                  'exportData',

        'dmWellboreDataDownlinkBtn':          'downlink',
        'dmWellboreDataUplinkBtn':            'uplink',
        'dmPumpingUnitDataDownlinkBtn':       'downlink',
        'dmPumpingUnitDataUplinkBtn':         'uplink',
        'dmFSDiagramConstructionDownlinkBtn': 'downlink',
        'dmFSDiagramConstructionUplinkBtn':   'uplink',
        'dmSystemParameterDownlinkBtn':       'downlink',
        'dmSystemParameterUplinkBtn':         'uplink',
        'dmIntelligentFrequencyDownlinkBtn':  'downlink',
        'dmIntelligentFrequencyUplinkBtn':    'uplink',
        'dmInterlockProtectionDownlinkBtn':   'downlink',
        'dmInterlockProtectionUplinkBtn':     'uplink',

        'dmEditVideoKeyBtn':                  'editVideoKey'
    };
    for (var id in btnMap) {
        var btn = mini.get(id);
        if (btn) btn.setText(R[btnMap[id]]);
    }

    var additionalTabs = mini.get('deviceAdditionalTabs');
    if (additionalTabs) {
        var tabs = additionalTabs.getTabs();
        if (tabs && tabs.length >= 8) {
            additionalTabs.updateTab(tabs[0], { title: R.additionalInformation });
            additionalTabs.updateTab(tabs[1], { title: R.auxiliaryDevice });
            additionalTabs.updateTab(tabs[2], { title: R.calculateDataConfig });
            additionalTabs.updateTab(tabs[3], { title: R.videoConfig });
            additionalTabs.updateTab(tabs[4], { title: R.fsDiagramConstruction });
            additionalTabs.updateTab(tabs[5], { title: R.systemParameterConfiguration });
            additionalTabs.updateTab(tabs[6], { title: R.intelligentFrequencyConversion });
            additionalTabs.updateTab(tabs[7], { title: R.interlockProtection });
        }
    }

    var calculateDataTabs = mini.get('deviceCalculateDataTabs');
    if (calculateDataTabs) {
        var cdTabs = calculateDataTabs.getTabs();
        if (cdTabs && cdTabs.length >= 2) {
            calculateDataTabs.updateTab(cdTabs[0], { title: R.wellboreData });
            calculateDataTabs.updateTab(cdTabs[1], { title: R.pumpingUnitData });
        }
    }

    var pumpingUnitDataPanel = mini.get('pumpingUnitDataPanel');
    if (pumpingUnitDataPanel) pumpingUnitDataPanel.setTitle(R.pumpingUnitData);
    var pumpingUnitDetailPanel = mini.get('pumpingUnitDetailPanel');
    if (pumpingUnitDetailPanel) pumpingUnitDetailPanel.setTitle(R.detailedInformation);
    var pumpingUnitPRTFPanel = mini.get('pumpingUnitPRTFPanel');
    if (pumpingUnitPRTFPanel) pumpingUnitPRTFPanel.setTitle(R.pumpingUnitPRTF);
}

// ================================================================
// 监听主界面消息
// ================================================================
function initDeviceManagerMessageListener() {
    window.addEventListener('message', function (event) {
        var message = event.data;
        if (!message || !message.action) return;
        switch (message.action) {
            case 'refresh':
                onDmRefreshDeviceList();
                break;
        }
    });
}

// ================================================================
// 一级标签
// ================================================================
function buildDmLevel1Tabs() {
    var container = document.getElementById('level1Footer');
    if (!container) return;
    container.innerHTML = '';

    if (!_dmTabInfo || !_dmTabInfo.children || _dmTabInfo.children.length === 0) {
        return;
    }

    _dmLevel1Data = _dmTabInfo.children;
    for (var i = 0; i < _dmLevel1Data.length; i++) {
        (function (idx) {
            var item = _dmLevel1Data[idx];
            var span = document.createElement('span');
            span.className = 'tab-item' + (idx === 0 ? ' active' : '');
            span.textContent = item.text;
            span.onclick = function () { selectDmLevel1(idx); };
            container.appendChild(span);
        })(i);
    }

    if (_dmLevel1Data.length > 0) {
        selectDmLevel1(0);
    }
}

function selectDmLevel1(index) {
    if (index < 0 || index >= _dmLevel1Data.length) return;
    _dmCurrentLevel1 = _dmLevel1Data[index];

    var container = document.getElementById('level1Footer');
    var tabs = container.querySelectorAll('.tab-item');
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
    }

    buildDmLevel2Tabs(_dmCurrentLevel1);
}

// ================================================================
// 二级标签
// ================================================================
function buildDmLevel2Tabs(level1Item) {
    var sidebar = document.getElementById('level2Sidebar');
    if (!sidebar) return;
    sidebar.innerHTML = '';

    var children = level1Item.children || [];

    if (!children || children.length === 0) {
        sidebar.classList.add('hidden');
        _dmLevel2Data = [];
        _dmCurrentLevel2 = null;

        applyCurrentDeviceType(level1Item.deviceTypeId, level1Item.text);
        return;
    }

    sidebar.classList.remove('hidden');
    _dmLevel2Data = children;

    var allIds = [];
    for (var i = 0; i < children.length; i++) {
        allIds.push(children[i].deviceTypeId);
    }
    var allTab = {
        text: _loginUserLanguageResource.all,
        deviceTypeId: allIds.join(','),
        isAll: true
    };

    var allTabs = [allTab].concat(children);
    _dmCurrentLevel2 = allTabs[0];

    for (var i = 0; i < allTabs.length; i++) {
        (function (idx, tabItem) {
            var div = document.createElement('div');
            div.className = 'tab-item' + (idx === 0 ? ' active' : '');
            div.textContent = tabItem.text;
            div.title = tabItem.text;
            div.onclick = function () { selectDmLevel2(idx, allTabs); };
            sidebar.appendChild(div);
        })(i, allTabs[i]);
    }

    applyCurrentDeviceType(allTabs[0].deviceTypeId, allTabs[0].text);
}

function selectDmLevel2(index, allTabs) {
    if (index < 0 || index >= allTabs.length) return;
    _dmCurrentLevel2 = allTabs[index];

    var sidebar = document.getElementById('level2Sidebar');
    var tabs = sidebar.querySelectorAll('.tab-item');
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
    }

    applyCurrentDeviceType(_dmCurrentLevel2.deviceTypeId, _dmCurrentLevel2.text);
}

// ================================================================
// 应用当前设备类型
// ================================================================
function applyCurrentDeviceType(deviceTypeId, deviceTypeName) {
    var hidden = document.getElementById('dmCurrentDeviceTypeId');
    if (!hidden) {
        hidden = document.createElement('input');
        hidden.type = 'hidden';
        hidden.id = 'dmCurrentDeviceTypeId';
        document.body.appendChild(hidden);
    }
    hidden.value = deviceTypeId || '';

    // 重置右侧设备上下文
    _dmSelectedDeviceId            = 0;
    _dmCurrentDeviceName           = '';
    _dmCurrentApplicationScenarios = 0;
    _dmCurrentCalculateType        = 0;

    // 加载设备列表
    CreateAndLoadDeviceInfoTable(true);
}

// ================================================================
// 工具函数
// ================================================================
function getCurrentDeviceType() {
    if (_dmCurrentLevel2 && _dmCurrentLevel2.deviceTypeId) {
        return _dmCurrentLevel2.deviceTypeId;
    }
    if (_dmCurrentLevel1 && _dmCurrentLevel1.deviceTypeId) {
        return _dmCurrentLevel1.deviceTypeId;
    }
    return '';
}

function getCurrentFirstDeviceType() {
    if (_dmCurrentLevel1 && _dmCurrentLevel1.deviceTypeId) {
        return _dmCurrentLevel1.deviceTypeId;
    }
    return '';
}

function getCurrentDeviceTypeName() {
    if (_dmCurrentLevel2 && _dmCurrentLevel2.text) return _dmCurrentLevel2.text;
    if (_dmCurrentLevel1 && _dmCurrentLevel1.text) return _dmCurrentLevel1.text;
    return '';
}

// ================================================================
// 权限控制
// ================================================================
function updateDmBtnStatus() {
    var editFlag = (_dmModuleRight.editFlag == 1);
    var btnIds = [
        'dmAddDeviceBtn', 'dmDelDeviceBtn', 'dmSaveDeviceBtn',
        'dmBatchAddDeviceBtn', 'dmDeviceOrgChangeBtn', 'dmExportDeviceBtn',

        'dmWellboreDataDownlinkBtn', 'dmWellboreDataUplinkBtn',
        'dmPumpingUnitDataDownlinkBtn', 'dmPumpingUnitDataUplinkBtn',
        'dmFSDiagramConstructionDownlinkBtn', 'dmFSDiagramConstructionUplinkBtn',
        'dmSystemParameterDownlinkBtn', 'dmSystemParameterUplinkBtn',
        'dmIntelligentFrequencyDownlinkBtn', 'dmIntelligentFrequencyUplinkBtn',
        'dmInterlockProtectionDownlinkBtn', 'dmInterlockProtectionUplinkBtn'
    ];

    for (var i = 0; i < btnIds.length; i++) {
        var btn = mini.get(btnIds[i]);
        if (btn) btn.setEnabled(editFlag);
    }
    var editVideoKeyBtn = mini.get('dmEditVideoKeyBtn');
    if (editVideoKeyBtn) editVideoKeyBtn.setEnabled(editFlag && loginUserRoleVideoKeyEdit == 1);
}

// ================================================================
// 初始化下拉框
// ================================================================
function initDmCombos() {
    var deviceCombo = mini.get('dmDeviceCombo');
    if (deviceCombo) deviceCombo.setEmptyText('--' + _loginUserLanguageResource.all + '--');
    var opCombo = mini.get('dmSignInIdCombo');
    if (opCombo) opCombo.setEmptyText('--' + _loginUserLanguageResource.all + '--');
}

function onDmDeviceComboBeforeLoad(e) {
    var params = e.params || {};
    var pageIndex = params.pageIndex || 0;
    var pageSize = params.pageSize || (typeof defaultWellComboxSize !== 'undefined' ? defaultWellComboxSize : 50);
    params.start = pageIndex * pageSize;
    params.limit = pageSize;

    var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
    params.orgId = leftOrgId ? leftOrgId.getValue() : '';
    params.deviceType = getCurrentDeviceType();

    var combo = mini.get('dmDeviceCombo');
    params.deviceName = combo ? (combo.getValue() || '') : '';

    e.params = params;
}

function onDmDeviceComboShowPopup(e) {
    var combo = e.sender;
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
    onDmQueryDeviceList();
}

function onDmSignInIdComboBeforeLoad(e) {
    var params = e.params || {};
    var pageIndex = params.pageIndex || 0;
    var pageSize = params.pageSize || (typeof defaultWellComboxSize !== 'undefined' ? defaultWellComboxSize : 50);
    params.start = pageIndex * pageSize;
    params.limit = pageSize;

    var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
    params.orgId = leftOrgId ? leftOrgId.getValue() : '';
    params.deviceType = getCurrentDeviceType();

    var deviceCombo = mini.get('dmDeviceCombo');
    params.deviceName = deviceCombo ? (deviceCombo.getValue() || '') : '';

    var signInIdCombo = mini.get('dmSignInIdCombo');
    params.signInId = signInIdCombo ? (signInIdCombo.getValue() || '') : '';

    e.params = params;
}

function onDmSignInIdComboShowPopup(e) {
    var combo = e.sender;
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

function onDmSignInIdComboChange(e) {
    onDmQueryDeviceList();
}

// ================================================================
// 按钮事件
// ================================================================
function onDmRefreshDeviceList() {
    var deviceCombo = mini.get('dmDeviceCombo');
    if (deviceCombo) deviceCombo.setValue('');
    var signInIdCombo = mini.get('dmSignInIdCombo');
    if (signInIdCombo) signInIdCombo.setValue('');

    CreateAndLoadDeviceInfoTable(true);
}

function onDmQueryDeviceList() {
    CreateAndLoadDeviceInfoTable(false);
}

function onDmAddDevice()         { /* TODO */ }
function onDmDelDevice()         { /* TODO */ }
function onDmSaveDevice()        { /* TODO */ }
function onDmBatchAddDevice()    { /* TODO */ }
function onDmDeviceOrgChange()   { /* TODO */ }
function onDmExportDevice()      { /* TODO */ }
function onDmEditVideoKey()      { /* TODO */ }

// ================================================================
// 主 tab 加载分发
// ================================================================
function dispatchTabLoad(tabName) {
    if (!tabName) return;

    if (tabName === 'calculateData') {
        updateCalculateDataSubTabs(_dmCurrentCalculateType);
    } else {
        loadAdditionalInfoByTabName(tabName);
    }
}

function onDmAdditionalTabChanged(e) {
	if(isInitializing) return;
    var tab = e.tab;
    if (!tab) return;
    dispatchTabLoad(tab.name);
}

function onDmCalculateDataTabChanged(e) {
	if(isInitializing) return;
    var tab = e.tab;
    if (!tab) return;
    loadCalculateDataByTabName(tab.name);
}

function onDmProductionDataDownlink()    { /* TODO */ }
function onDmProductionDataUplink()      { /* TODO */ }
function onDmPumpingUnitDataDownlink()   { /* TODO */ }
function onDmPumpingUnitDataUplink()     { /* TODO */ }
function onDmFSDiagramConstructionDataDownlink() { /* TODO */ }
function onDmFSDiagramConstructionDataUplink()   { /* TODO */ }
function onDmSystemParameterDataDownlink()       { /* TODO */ }
function onDmSystemParameterDataUplink()         { /* TODO */ }
function onDmIntelligentFrequencyConversionDataDownlink() { /* TODO */ }
function onDmIntelligentFrequencyConversionDataUplink()   { /* TODO */ }
function onDmInterlockProtectionDataDownlink()   { /* TODO */ }
function onDmInterlockProtectionDataUplink()     { /* TODO */ }

// ================================================================
// 加载附加信息标签数据（占位）
// ================================================================
function loadAdditionalInfoByTabName(tabName) {
    if (!tabName) return;

    var deviceId      = _dmSelectedDeviceId;
    var deviceName    = _dmCurrentDeviceName;
    var appScenarios  = _dmCurrentApplicationScenarios;
    var calculateType = _dmCurrentCalculateType;

    console.log('[主设备] 加载附加信息标签：', tabName,
                'deviceId=', deviceId, 'deviceName=', deviceName);

    if (tabName === 'calculateData') {
        var calculateDataTabs = mini.get('deviceCalculateDataTabs');
        if (calculateDataTabs) {
            var activeSubTab = calculateDataTabs.getActiveTab();
            if (activeSubTab) {
                loadCalculateDataByTabName(activeSubTab.name);
            }
        }
    } else if (tabName === 'additionalInfo') {
        // TODO: CreateAndLoadDeviceAdditionalInfoTable(deviceId, deviceName, true)
    } else if (tabName === 'auxiliaryDevice') {
        // TODO: CreateAndLoadDeviceAuxiliaryDeviceInfoTable(deviceId, deviceName, calculateType, true)
    } else if (tabName === 'videoInfo') {
        // TODO: CreateAndLoadVideoInfoTable(deviceId, deviceName, true)
    } else if (tabName === 'fsDiagramConstruction') {
        // TODO: CreateAndLoadFSDiagramConstructionDataTable(deviceId, deviceName, appScenarios, true)
    } else if (tabName === 'systemParameter') {
        // TODO: CreateAndLoadDeviceSystemParameterTable(deviceId, deviceName, appScenarios, true)
    } else if (tabName === 'intelligentFrequencyConversion') {
        // TODO: CreateAndLoadDeviceIntelligentFrequencyConversionTable(deviceId, deviceName, appScenarios, true)
    } else if (tabName === 'interlockProtection') {
        // TODO: CreateAndLoadDeviceInterlockProtectionTable(deviceId, deviceName, appScenarios, true)
    }
}

// ================================================================
// 加载生产数据配置子标签数据（占位）
// ================================================================
function loadCalculateDataByTabName(tabName) {
    if (!tabName) return;

    var deviceId      = _dmSelectedDeviceId;
    var deviceName    = _dmCurrentDeviceName;
    var appScenarios  = _dmCurrentApplicationScenarios;
    var calculateType = _dmCurrentCalculateType;

    console.log('[主设备] 加载生产数据标签：', tabName,
                'deviceId=', deviceId, 'deviceName=', deviceName,
                'appScenarios=', appScenarios, 'calculateType=', calculateType);

    if (tabName === 'wellboreData') {
        // TODO: CreateAndLoadProductionDataTable(deviceId, deviceName, appScenarios, true)
    } else if (tabName === 'pumpingUnitData') {
        // TODO:
        //   CreateAndLoadPumpingInfoTable(deviceId, deviceName, appScenarios, true);
        //   CreatePumpingUnitDetailedInformationTable();
        //   CreateAndLoadDevicePumpingUnitPTFTable();
    }
}

// ================================================================
// 加载设备列表
// ================================================================
function CreateAndLoadDeviceInfoTable(isNew) {
    if (isNew && deviceInfoHandsontableHelper != null) {
        if (deviceInfoHandsontableHelper.hot != undefined) {
            deviceInfoHandsontableHelper.hot.destroy();
        }
        deviceInfoHandsontableHelper = null;
    }

    var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
    var deviceType = getCurrentDeviceType();
    var dictDeviceType = deviceType;
    if (dictDeviceType && dictDeviceType.indexOf(',') > -1) {
        dictDeviceType = getCurrentFirstDeviceType();
    }

    var deviceCombo = mini.get('dmDeviceCombo');
    var deviceName = deviceCombo ? (deviceCombo.getValue() || '') : '';
    var signInIdCombo = mini.get('dmSignInIdCombo');
    var signInId = signInIdCombo ? (signInIdCombo.getValue() || '') : '';

    var maskEl = 'DeviceTablePanel_id';
    mini.mask({
        el: maskEl,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });

    $.ajax({
        url: context + '/wellInformationManagerController/doWellInformationShow',
        type: 'POST',
        data: {
            deviceName: deviceName,
            signInId: signInId,
            deviceType: deviceType,
            dictDeviceType: dictDeviceType,
            recordCount: 50,
            orgId: leftOrgId,
            page: 1,
            limit: 10000
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask(maskEl);

            var fixedColumnsStart = 0;

            if (deviceInfoHandsontableHelper == null
                || deviceInfoHandsontableHelper.hot == null
                || deviceInfoHandsontableHelper.hot == undefined) {

                deviceInfoHandsontableHelper = DeviceInfoHandsontableHelper.createNew('DeviceTableDiv_id');
                deviceInfoHandsontableHelper.dataLength = result.totalCount;

                var colHeaders = [];
                var columns = [];

                for (var i = 0; i < result.columns.length; i++) {
                    var col = result.columns[i];
                    colHeaders.push(col.header);

                    if (col.dataIndex.toUpperCase() === 'ID'
                        || col.dataIndex.toUpperCase() === 'DEVICENAME') {
                        fixedColumnsStart++;
                    }

                    columns.push(buildDeviceColumn(col, result));
                }

                deviceInfoHandsontableHelper.colHeaders = colHeaders;
                deviceInfoHandsontableHelper.columns = columns;
                deviceInfoHandsontableHelper.fixedColumnsStart = fixedColumnsStart;

                if (result.totalRoot.length == 0) {
                    deviceInfoHandsontableHelper.hiddenRows = [0];
                    deviceInfoHandsontableHelper.createTable([{}]);
                } else {
                    deviceInfoHandsontableHelper.hiddenRows = [];
                    deviceInfoHandsontableHelper.createTable(result.totalRoot);
                }
            } else {
                deviceInfoHandsontableHelper.hot.deselectCell();
                deviceInfoHandsontableHelper.dataLength = result.totalCount;
                if (result.totalRoot.length == 0) {
                    deviceInfoHandsontableHelper.hiddenRows = [0];
                    deviceInfoHandsontableHelper.hot.loadData([{}]);
                } else {
                    deviceInfoHandsontableHelper.hiddenRows = [];
                    deviceInfoHandsontableHelper.hot.loadData(result.totalRoot);
                }
            }

            if (deviceInfoHandsontableHelper.hiddenRows.length > 0) {
                var plugin = deviceInfoHandsontableHelper.hot.getPlugin('hiddenRows');
                plugin.hideRows(deviceInfoHandsontableHelper.hiddenRows);
            }

            // ---------- 默认选中 ----------
            if (result.totalRoot.length == 0) {
                _dmDeviceSelectRow = '';
                _dmDeviceSelectEndRow = '';
                deviceInfoHandsontableHelper.hot.selectCell(0, 'deviceName');

                // 更新上下文（空）
                _dmSelectedDeviceId            = 0;
                _dmCurrentDeviceName           = '';
                _dmCurrentApplicationScenarios = 0;
                _dmCurrentCalculateType        = 0;

                updateDeviceAdditionalInfoTabs({});
            } else {
                var selectRow = 0;
                for (var i = 0; i < result.totalRoot.length; i++) {
                    if (result.totalRoot[i].id == _dmSelectedDeviceId) {
                        selectRow = i;
                        break;
                    }
                }
                _dmDeviceSelectRow = selectRow;
                deviceInfoHandsontableHelper.hot.selectCell(selectRow, 'deviceName');

                var recordId = deviceInfoHandsontableHelper.hot.getDataAtRowProp(selectRow, 'id');
                var devName = deviceInfoHandsontableHelper.hot.getDataAtRowProp(selectRow, 'deviceName');

                var deviceTabInstance = deviceInfoHandsontableHelper.hot.getDataAtRowProp(selectRow, 'deviceTabInstance');
                var deviceTabInstanceInfo = getDeviceTabInstanceInfo(deviceTabInstance);
                var calculateType = deviceTabInstanceInfo.calculateType || 0;

                var applicationScenarios = getApplicationScenariosValue(
                    deviceInfoHandsontableHelper.hot.getDataAtRowProp(selectRow, 'applicationScenariosName'));

                // ★ 先更新上下文
                _dmSelectedDeviceId            = recordId;
                _dmCurrentDeviceName           = devName;
                _dmCurrentApplicationScenarios = applicationScenarios;
                _dmCurrentCalculateType        = calculateType;

                // ★ 再更新 tab（内部触发一次加载）
                updateDeviceAdditionalInfoTabs(deviceTabInstanceInfo);
            }

            _dmDeviceTotalCount = result.totalCount;
            var totalLabel = document.getElementById('dmDeviceTotalCountLabel');
            if (totalLabel) {
                totalLabel.textContent = _loginUserLanguageResource.totalCount + '：' + result.totalCount;
            }

            deviceInfoHandsontableHelper.initSignInIdAndSlaveMap(result.totalRoot);
            deviceInfoHandsontableHelper.hot.render();
        },
        error: function () {
            mini.unmask(maskEl);
            mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
        }
    });
}

// ================================================================
// 根据后端返回的列元数据构建 Handsontable column
// ================================================================
function buildDeviceColumn(col, result) {
    var di = col.dataIndex.toUpperCase();

    if (di === 'ID') {
        return { data: col.dataIndex };
    }
    if (di === 'ORGNAME') {
        return {
            data: col.dataIndex,
            allowInvalid: true,
            validator: function (val, callback) {
                return handsontableDataCheck_Org(val, callback, this.row, this.col, deviceInfoHandsontableHelper);
            }
        };
    }
    if (di === 'LIFTINGTYPENAME') {
        var source = (typeof pcpHidden !== 'undefined' && pcpHidden)
            ? [_loginUserLanguageResource.SRPCalculate]
            : ['抽油机井', '螺杆泵井'];
        return { data: col.dataIndex, type: 'dropdown', strict: true, allowInvalid: false, source: source };
    }
    if (di === 'DEVICETYPENAME') {
        var arr = [];
        if (result.deviceTypeDropdownData) {
            for (var j = 0; j < result.deviceTypeDropdownData.length; j++) {
                arr.push(result.deviceTypeDropdownData[j]);
            }
        }
        return { data: col.dataIndex, type: 'dropdown', strict: true, allowInvalid: false, source: arr };
    }
    if (di === 'DEVICETABINSTANCE') {
        var arr = [];
        if (result.tabInstanceDropdownData) {
            for (var j = 0; j < result.tabInstanceDropdownData.length; j++) {
                arr.push(result.tabInstanceDropdownData[j]);
            }
        }
        return { data: col.dataIndex, type: 'dropdown', strict: true, allowInvalid: false, source: arr };
    }
    if (di === 'INSTANCENAME') {
        var arr = [];
        if (result.instanceDropdownData) {
            for (var j = 0; j < result.instanceDropdownData.length; j++) {
                arr.push(result.instanceDropdownData[j]);
            }
        }
        return { data: col.dataIndex, type: 'dropdown', strict: true, allowInvalid: false, source: arr };
    }
    if (di === 'DISPLAYINSTANCENAME') {
        var arr = [];
        if (result.displayInstanceDropdownData) {
            for (var j = 0; j < result.displayInstanceDropdownData.length; j++) {
                arr.push(result.displayInstanceDropdownData[j]);
            }
        }
        return { data: col.dataIndex, type: 'dropdown', strict: true, allowInvalid: false, source: arr };
    }
    if (di === 'REPORTINSTANCENAME') {
        var arr = [];
        if (result.reportInstanceDropdownData) {
            for (var j = 0; j < result.reportInstanceDropdownData.length; j++) {
                arr.push(result.reportInstanceDropdownData[j]);
            }
        }
        return { data: col.dataIndex, type: 'dropdown', strict: true, allowInvalid: false, source: arr };
    }
    if (di === 'ALARMINSTANCENAME') {
        var arr = [];
        if (result.alarmInstanceDropdownData) {
            for (var j = 0; j < result.alarmInstanceDropdownData.length; j++) {
                arr.push(result.alarmInstanceDropdownData[j]);
            }
        }
        return { data: col.dataIndex, type: 'dropdown', strict: true, allowInvalid: false, source: arr };
    }
    if (di === 'APPLICATIONSCENARIOSNAME') {
        var arr = [];
        if (result.applicationScenariosDropdownData) {
            for (var j = 0; j < result.applicationScenariosDropdownData.length; j++) {
                arr.push(result.applicationScenariosDropdownData[j]);
            }
        }
        return { data: col.dataIndex, type: 'dropdown', strict: true, allowInvalid: false, source: arr };
    }
    if (di === 'STATUSNAME') {
        return {
            data: col.dataIndex, type: 'dropdown', strict: true, allowInvalid: false,
            source: [_loginUserLanguageResource.enable, _loginUserLanguageResource.disable]
        };
    }
    if (di === 'TCPTYPE') {
        return {
            data: col.dataIndex, type: 'dropdown', strict: true, allowInvalid: false,
            source: ['', 'TCP Client', 'TCP Server']
        };
    }
    if (di === 'SORTNUM' || di === 'SLAVE' || di === 'PEAKDELAY') {
        return {
            data: col.dataIndex, type: 'text', allowInvalid: true,
            validator: function (val, callback) {
                return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, deviceInfoHandsontableHelper);
            }
        };
    }
    if (di === 'IPPORT') {
        return {
            data: col.dataIndex, type: 'text', allowInvalid: true,
            validator: function (val, callback) {
                return handsontableDataCheck_IpPort_Nullable(val, callback, this.row, this.col, deviceInfoHandsontableHelper);
            }
        };
    }
    if (di === 'COMMISSIONINGDATE') {
        return {
            data: col.dataIndex, type: 'intl-date',
            dateFormat: { year: 'numeric', month: '2-digit', day: '2-digit' },
            locale: 'sv-SE'
        };
    }
    if (di === 'CALCULATETYPENAME') {
        return {
            data: col.dataIndex, type: 'dropdown', strict: true, allowInvalid: false,
            source: [
                _loginUserLanguageResource.nothing,
                _loginUserLanguageResource.SRPCalculate,
                _loginUserLanguageResource.PCPCalculate
            ]
        };
    }
    return { data: col.dataIndex };
}

// ================================================================
// 工具：根据名称获取应用场景数值
// ================================================================
function getApplicationScenariosValue(name) {
    var R = _loginUserLanguageResource;
    if (name == R.applicationScenarios1) return 1;
    if (name == R.applicationScenarios2) return 2;
    return 0;
}

// ================================================================
// 根据所选设备的实例配置，控制右侧面板和标签的显示 / 激活
// ================================================================
function updateDeviceAdditionalInfoTabs(deviceTabInstanceInfo) {
    var additionalTabs = mini.get('deviceAdditionalTabs');
    var splitter = mini.get('dmMainSplitter');
    if (!additionalTabs || !splitter) return;

    var tabs = additionalTabs.getTabs();
    if (!tabs || tabs.length < 8) return;

    // ---------- 1) 解析配置 ----------
    var calculateType = (deviceTabInstanceInfo.calculateType == undefined) ? 0 : deviceTabInstanceInfo.calculateType;
    var cfg = deviceTabInstanceInfo.config;

    var showAdditionalInformation = false;
    var showAuxiliaryDevice = false;
    var showCalculateDataConfig = false;
    var showVideoConfig = false;
    var showFSDiagramConstruction = false;
    var showSystemParameterConfig = false;
    var showIntelligentFrequencyConversion = false;
    var showInterlockProtection = false;

    if (cfg != undefined && cfg.PrimaryDevice != undefined) {
        var pd = cfg.PrimaryDevice;
        showAdditionalInformation = (pd.AdditionalInformation != undefined) ? pd.AdditionalInformation : false;
        showAuxiliaryDevice = (pd.AuxiliaryDevice != undefined) ? pd.AuxiliaryDevice : false;
        showVideoConfig = (pd.VideoConfig != undefined) ? pd.VideoConfig : false;
        showCalculateDataConfig = (pd.CalculateDataConfig != undefined) ? pd.CalculateDataConfig : false;
        showFSDiagramConstruction = (pd.FSDiagramConstruction != undefined) ? pd.FSDiagramConstruction : false;
        showSystemParameterConfig = (pd.SystemParameterConfig != undefined) ? pd.SystemParameterConfig : false;
        showIntelligentFrequencyConversion = (pd.IntelligentFrequencyConversion != undefined) ? pd.IntelligentFrequencyConversion : false;
        showInterlockProtection = (pd.InterlockProtection != undefined) ? pd.InterlockProtection : false;
    }

    if (calculateType == 0) {
        showCalculateDataConfig = false;
        showFSDiagramConstruction = false;
    } else if (calculateType == 2) {
        showFSDiagramConstruction = false;
    }

    // ---------- 2) 是否要显示整个右侧 ----------
    var anyVisible = showAdditionalInformation
        || showAuxiliaryDevice
        || showCalculateDataConfig
        || showVideoConfig
        || showFSDiagramConstruction
        || showSystemParameterConfig
        || showIntelligentFrequencyConversion
        || showInterlockProtection;

    if (!anyVisible) {
        splitter.hidePane(2);
        return;
    }

    splitter.showPane(2);

    // ---------- 3) 更新每个 tab 的可见性 ----------
    var visibleMap = {
        'additionalInfo': showAdditionalInformation,
        'auxiliaryDevice': showAuxiliaryDevice,
        'calculateData': showCalculateDataConfig,
        'videoInfo': showVideoConfig,
        'fsDiagramConstruction': showFSDiagramConstruction,
        'systemParameter': showSystemParameterConfig,
        'intelligentFrequencyConversion': showIntelligentFrequencyConversion,
        'interlockProtection': showInterlockProtection
    };

    additionalTabs.updateTab(tabs[0], { visible: showAdditionalInformation });
    additionalTabs.updateTab(tabs[1], { visible: showAuxiliaryDevice });
    additionalTabs.updateTab(tabs[2], { visible: showCalculateDataConfig });
    additionalTabs.updateTab(tabs[3], { visible: showVideoConfig });
    additionalTabs.updateTab(tabs[4], { visible: showFSDiagramConstruction });
    additionalTabs.updateTab(tabs[5], { visible: showSystemParameterConfig });
    additionalTabs.updateTab(tabs[6], { visible: showIntelligentFrequencyConversion });
    additionalTabs.updateTab(tabs[7], { visible: showInterlockProtection });

    // ---------- 4) 决定激活的标签 ----------
    var currentActive = additionalTabs.getActiveTab();
    var currentName = currentActive ? currentActive.name : '';

    if (currentName && visibleMap[currentName]) {
        dispatchTabLoad(currentName);
    } else {
        for (var i = 0; i < tabs.length; i++) {
            if (visibleMap[tabs[i].name]) {
                additionalTabs.activeTab(tabs[i]);
                break;
            }
        }
    }
}

// ================================================================
// 生产数据配置内部子标签
// ================================================================
function updateCalculateDataSubTabs(calculateType) {
    var calculateDataTabs = mini.get('deviceCalculateDataTabs');
    if (!calculateDataTabs) return;
    var cdTabs = calculateDataTabs.getTabs();
    if (!cdTabs || cdTabs.length < 2) return;

    calculateDataTabs.updateTab(cdTabs[0], { visible: true });
    var showPumping = (calculateType == 1);
    calculateDataTabs.updateTab(cdTabs[1], { visible: showPumping });

    var activeSubTab = calculateDataTabs.getActiveTab();
    var activeSubName = activeSubTab ? activeSubTab.name : '';

    if (activeSubName === 'pumpingUnitData' && !showPumping) {
        calculateDataTabs.activeTab(cdTabs[0]);
        return;
    }

    var activeVisible = false;
    if (activeSubName) {
        for (var i = 0; i < cdTabs.length; i++) {
            if (cdTabs[i].name === activeSubName && cdTabs[i].visible !== false) {
                activeVisible = true;
                break;
            }
        }
    }
    if (!activeVisible) {
        for (var j = 0; j < cdTabs.length; j++) {
            if (cdTabs[j].visible !== false) {
                calculateDataTabs.activeTab(cdTabs[j]);
                return;
            }
        }
    }

    // 子 tab 未变化 → 主动加载当前激活子 tab 的数据
    loadCalculateDataByTabName(activeSubName);
}

// ================================================================
// 辅助：获取设备 tab 实例配置
// ================================================================
function getDeviceTabInstanceInfo(name) {
    var r = {};
    $.ajax({
        url: context + '/operationMaintenanceController/getDeviceTabInstanceConfig',
        type: 'POST',
        async: false,
        data: { name: name },
        dataType: 'json',
        success: function (resp) { r = resp || {}; }
    });
    return r;
}

// ================================================================
// DeviceInfoHandsontableHelper
// ================================================================
var DeviceInfoHandsontableHelper = {
    createNew: function (divid) {
        var helper = {};
        helper.hot = '';
        helper.divid = divid;
        helper.validresult = true;
        helper.colHeaders = [];
        helper.columns = [];
        helper.dataLength = 0;
        helper.hiddenRows = [];
        helper.AllData = {};
        helper.updatelist = [];
        helper.delidslist = [];
        helper.insertlist = [];
        helper.editWellNameList = [];
        helper.fixedColumnsStart = 0;
        helper.signInIdAndSlaveMap = new Map();

        helper.initSignInIdAndSlaveMap = function (data) {
            helper.signInIdAndSlaveMap.clear();
            if (!data) return;
            data.forEach(function (row, index) {
                var signInId = row.signInId;
                var slave = row.slave;
                if ((row.signInId == undefined || !signInId)
                    || (row.slave == undefined || !slave)) return;
                var value = signInId + '_' + slave;
                if (!helper.signInIdAndSlaveMap.has(value)) {
                    helper.signInIdAndSlaveMap.set(value, [index]);
                } else {
                    helper.signInIdAndSlaveMap.get(value).push(index);
                }
            });
        };

        helper.getDuplicateCount = function () {
            var c = 0;
            helper.signInIdAndSlaveMap.forEach(function (indexes) {
                if (indexes.length > 1) c += indexes.length;
            });
            return c;
        };

        helper.getDuplicateRowList = function () {
            var list = [];
            helper.signInIdAndSlaveMap.forEach(function (indexes) {
                if (indexes.length > 1) {
                    for (var i = 0; i < indexes.length; i++) list.push(indexes[i]);
                }
            });
            return list;
        };

        helper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
            Handsontable.renderers.TextRenderer.apply(this, arguments);
            td.style.backgroundColor = 'rgb(242, 242, 242)';
        };

        helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
            if (cellProperties.type == 'checkbox') {
                Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
            } else if (cellProperties.type == 'dropdown') {
                Handsontable.renderers.DropdownRenderer.apply(this, arguments);
                td.style.whiteSpace = 'nowrap';
                td.style.overflow = 'hidden';
                td.style.textOverflow = 'ellipsis';
            } else {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.whiteSpace = 'nowrap';
                td.style.overflow = 'hidden';
                td.style.textOverflow = 'ellipsis';
            }

            if (prop === 'instanceName' && !isNotVal(value)) {
                td.style.backgroundColor = '#FF4C42';
            } else if (prop === 'tcpType' && !isNotVal(value)) {
                td.style.backgroundColor = '#FF4C42';
            } else if (prop === 'deviceTabInstance' && !isNotVal(value)) {
                td.style.backgroundColor = '#FF4C42';
            }

            if (isNotVal(helper.hot)) {
                if (prop === 'signInId') {
                    var slave = helper.hot.getDataAtRowProp(row, 'slave');
                    var tcpType = helper.hot.getDataAtRowProp(row, 'tcpType');
                    if (!isNotVal(tcpType) || tcpType.toUpperCase() == 'TCP SERVER') {
                        td.style.backgroundColor = 'rgb(245, 245, 245)';
                    }
                    if (isNotVal(value) && isNotVal(slave)) {
                        var checkValue = value + '_' + slave;
                        if (helper.signInIdAndSlaveMap.has(checkValue)) {
                            var rows = helper.signInIdAndSlaveMap.get(checkValue);
                            if (rows && rows.length > 1 && rows.indexOf(row) > -1) {
                                td.style.backgroundColor = '#FF4C42';
                            }
                        }
                    }
                } else if (prop === 'slave') {
                    var signInId = helper.hot.getDataAtRowProp(row, 'signInId');
                    if (isNotVal(value) && isNotVal(signInId)) {
                        var checkValue = signInId + '_' + value;
                        if (helper.signInIdAndSlaveMap.has(checkValue)) {
                            var rows = helper.signInIdAndSlaveMap.get(checkValue);
                            if (rows && rows.length > 1 && rows.indexOf(row) > -1) {
                                td.style.backgroundColor = '#FF4C42';
                            }
                        }
                    }
                } else if (prop === 'ipPort') {
                    var tcpType = helper.hot.getDataAtRowProp(row, 'tcpType');
                    if (!isNotVal(tcpType) || tcpType.toUpperCase() == 'TCP CLIENT') {
                        td.style.backgroundColor = 'rgb(245, 245, 245)';
                    }
                }
            }
        };

        helper.createTable = function (data) {
            $('#' + helper.divid).empty();
            var hotElement = document.querySelector('#' + helper.divid);
            helper.hot = new Handsontable(hotElement, {
                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                theme: 'ht-theme-classic',
                data: data,
                hiddenColumns: {
                    columns: [0],
                    indicators: false,
                    copyPasteEnabled: false
                },
                hiddenRows: {
                    rows: [],
                    indicators: false,
                    copyPasteEnabled: false
                },
                columns: helper.columns,
                fixedColumnsStart: helper.fixedColumnsStart,
                stretchH: 'all',
                rowHeaders: true,
                colHeaders: helper.colHeaders,
                columnSorting: true,
                allowInsertRow: false,
                sortIndicator: true,
                manualColumnResize: true,
                manualRowResize: true,
                filters: true,
                renderAllRows: true,
                search: true,
                outsideClickDeselects: false,
                contextMenu: {
                    items: {
                        "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                        "cut":  { name: _loginUserLanguageResource.contextMenu_cut }
                    }
                },
                cells: function (row, col, prop) {
                    var cellProperties = {};
                    var editFlag = parseInt(_dmModuleRight.editFlag);

                    if (editFlag == 1) {
                        if (helper.dataLength == 0) {
                            cellProperties.editor = false;
                        } else {
                            var upperProp = prop.toUpperCase();
                            if (upperProp === 'ALLPATH' || upperProp === 'PRODUCTIONDATAUPDATETIME') {
                                cellProperties.editor = false;
                            } else if (upperProp === 'DEVICETYPENAME') {
                                var deviceTypes = getCurrentDeviceType();
                                if (isNumber(deviceTypes)) cellProperties.editor = false;
                            } else if (upperProp === 'SIGNINID' || upperProp === 'IPPORT') {
                                var tcpType = this.instance.getDataAtRowProp(row, 'tcpType');
                                if (tcpType == '' || tcpType == null) {
                                    cellProperties.editor = this.type || 'text';
                                } else if (upperProp === 'SIGNINID') {
                                    if (tcpType.toUpperCase() === 'TCP CLIENT' || tcpType.toUpperCase() === 'TCPCLIENT') {
                                        cellProperties.editor = this.type || 'text';
                                    } else {
                                        cellProperties.editor = false;
                                    }
                                } else {
                                    if (tcpType.toUpperCase() === 'TCP SERVER' || tcpType.toUpperCase() === 'TCPSERVER') {
                                        cellProperties.editor = this.type || 'text';
                                    } else {
                                        cellProperties.editor = false;
                                    }
                                }
                            }
                            cellProperties.renderer = helper.addCellStyle;
                        }
                    } else {
                        cellProperties.editor = false;
                        cellProperties.renderer = helper.addCellStyle;
                    }
                    return cellProperties;
                },

                afterSelectionEnd: function (row, column, row2, column2, preventScrolling, selectionLayerLevel) {
                    if (row < 0 && row2 < 0) {
                        _dmDeviceSelectRow = '';
                        _dmDeviceSelectEndRow = '';

                        _dmSelectedDeviceId            = 0;
                        _dmCurrentDeviceName           = '';
                        _dmCurrentApplicationScenarios = 0;
                        _dmCurrentCalculateType        = 0;

                        updateDeviceAdditionalInfoTabs({});
                    } else {
                        if (row < 0) row = 0;
                        if (row2 < 0) row2 = 0;
                        var startRow = row;
                        var endRow = row2;
                        if (row > row2) {
                            startRow = row2;
                            endRow = row;
                        }
                        var selectedRow = _dmDeviceSelectRow;
                        if (selectedRow != startRow) {
                            _dmDeviceSelectRow = startRow;
                            _dmDeviceSelectEndRow = endRow;

                            var recordId = helper.hot.getDataAtRowProp(startRow, 'id');
                            var deviceName = helper.hot.getDataAtRowProp(startRow, 'deviceName');
                            var deviceTabInstance = helper.hot.getDataAtRowProp(startRow, 'deviceTabInstance');
                            var deviceTabInstanceInfo = getDeviceTabInstanceInfo(deviceTabInstance);
                            var calculateType = deviceTabInstanceInfo.calculateType || 0;
                            var applicationScenarios = getApplicationScenariosValue(
                                helper.hot.getDataAtRowProp(startRow, 'applicationScenariosName'));

                            // ★ 先更新上下文
                            _dmSelectedDeviceId            = recordId;
                            _dmCurrentDeviceName           = deviceName;
                            _dmCurrentApplicationScenarios = applicationScenarios;
                            _dmCurrentCalculateType        = calculateType;

                            // ★ 再更新 tab（内部触发一次加载）
                            updateDeviceAdditionalInfoTabs(deviceTabInstanceInfo);
                        } else {
                            _dmDeviceSelectRow = startRow;
                            _dmDeviceSelectEndRow = endRow;
                        }
                    }
                },

                afterChange: function (changes, source) {
                    if (changes == null) return;
                    for (var i = 0; i < changes.length; i++) {
                        var row = changes[i][0];
                        var prop = changes[i][1];
                        var oldValue = changes[i][2];
                        var newValue = changes[i][3];
                        if (oldValue == newValue) continue;

                        var rowdata = helper.hot.getDataAtRow(row);
                        var recordId = helper.hot.getDataAtRowProp(row, 'id');

                        if (recordId != null && recordId > 0) {
                            var data = {};
                            for (var j = 0; j < helper.columns.length; j++) {
                                data[helper.columns[j].data] = rowdata[j];
                            }
                            helper.updateExpressCount(data);
                        }

                        if (prop === 'signInId' || prop === 'slave') {
                            var oldV = oldValue;
                            var newV = newValue;
                            var keyField = (prop === 'signInId') ? 'slave' : 'signInId';
                            var keyValue = helper.hot.getDataAtRowProp(row, keyField);

                            if (oldV && helper.signInIdAndSlaveMap.has(
                                (prop === 'signInId' ? oldV : keyValue) + '_' + (prop === 'signInId' ? keyValue : oldV))) {
                                var oldKey = (prop === 'signInId' ? oldV : keyValue) + '_' + (prop === 'signInId' ? keyValue : oldV);
                                var rows = helper.signInIdAndSlaveMap.get(oldKey);
                                var idx = rows.indexOf(row);
                                if (idx > -1) {
                                    rows.splice(idx, 1);
                                    if (rows.length === 0) helper.signInIdAndSlaveMap.delete(oldKey);
                                }
                            }

                            if (newV) {
                                var newKey = (prop === 'signInId' ? newV : keyValue) + '_' + (prop === 'signInId' ? keyValue : newV);
                                if (!helper.signInIdAndSlaveMap.has(newKey)) {
                                    helper.signInIdAndSlaveMap.set(newKey, [row]);
                                } else {
                                    var rows2 = helper.signInIdAndSlaveMap.get(newKey);
                                    if (rows2.indexOf(row) === -1) rows2.push(row);
                                }
                            }
                            if (oldV !== newV) helper.hot.render();
                        } else if (prop === 'applicationScenariosName') {
                            // TODO: 生产数据表联动
                        } else if (prop === 'deviceTabInstance') {
                            var deviceTabInstanceInfo = getDeviceTabInstanceInfo(newValue);
                            _dmCurrentCalculateType = deviceTabInstanceInfo.calculateType || 0;
                            updateDeviceAdditionalInfoTabs(deviceTabInstanceInfo);
                        } else if (prop === 'instanceName') {
                            helper.hot.setDataAtRowProp(row, 'displayInstanceName', '');
                            helper.hot.setDataAtRowProp(row, 'alarmInstanceName', '');
                        }
                    }
                },

                afterBeginEditing: function (row, col) {
                    var prop = helper.columns[col].data.toUpperCase();
                    if (prop === 'DISPLAYINSTANCENAME') {
                        var acqInstanceName = helper.hot.getDataAtRowProp(row, 'instanceName');
                        var info = getInstanceUnitAndProtocol(acqInstanceName, 1, 0);
                        helper.hot.setCellMeta(row, col, 'source', info.displayInstanceList || []);
                    } else if (prop === 'ALARMINSTANCENAME') {
                        var acqInstanceName = helper.hot.getDataAtRowProp(row, 'instanceName');
                        var info = getInstanceUnitAndProtocol(acqInstanceName, 1, 0);
                        helper.hot.setCellMeta(row, col, 'source', info.alarmInstanceList || []);
                    }
                },

                afterOnCellMouseOver: function (event, coords, TD) {
                    if (coords.col >= 0 && coords.row >= 0 && helper.hot) {
                        var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                        if (isNotVal(rawValue)) {
                            TD.title = String(rawValue);
                        }
                    }
                }
            });
        };

        helper.insertExpressCount = function () {
            var ids = helper.hot.getDataAtCol(0);
            for (var i = 0; i < ids.length; i++) {
                if (ids[i] == null || ids[i] < 0) {
                    var rowdata = helper.hot.getDataAtRow(i);
                    if (rowdata != null) {
                        var data = {};
                        for (var j = 0; j < helper.columns.length; j++) {
                            data[helper.columns[j].data] = rowdata[j];
                        }
                        helper.insertlist.push(data);
                    }
                }
            }
            if (helper.insertlist.length != 0) {
                helper.AllData.insertlist = helper.insertlist;
            }
        };

        helper.delExpressCount = function (ids) {
            $.each(ids, function (index, id) {
                if (id != null) helper.delidslist.push(id);
            });
            helper.AllData.delidslist = helper.delidslist;
        };

        helper.updateExpressCount = function (data) {
            if (JSON.stringify(data) != "{}") {
                var flag = true;
                $.each(helper.updatelist, function (index, node) {
                    if (node.id == data.id) {
                        flag = false;
                        helper.updatelist[index] = data;
                    }
                });
                if (flag) helper.updatelist.push(data);
                helper.AllData.updatelist = helper.updatelist;
            }
        };

        helper.screening = function () {
            if (helper.updatelist.length != 0 && helper.delidslist.length != 0) {
                for (var i = 0; i < helper.delidslist.length; i++) {
                    for (var j = 0; j < helper.updatelist.length; j++) {
                        if (helper.updatelist[j].id == helper.delidslist[i]) {
                            helper.updatelist.splice(j, 1);
                        }
                    }
                }
                helper.AllData.updatelist = helper.updatelist;
            }
        };

        helper.clearContainer = function () {
            helper.AllData = {};
            helper.updatelist = [];
            helper.delidslist = [];
            helper.insertlist = [];
            helper.editWellNameList = [];
        };

        helper.saveData = function () {
            // TODO: 保存设备数据
        };

        return helper;
    }
};

// ================================================================
// 辅助：获取实例/单元/协议信息
// ================================================================
function getInstanceUnitAndProtocol(instance, condition, type) {
    var info = {
        protocolCode: '',
        acqUnitId: '',
        displayUnitId: '',
        alarmUnitId: '',
        displayInstanceList: [],
        alarmInstanceList: []
    };
    $.ajax({
        url: context + '/wellInformationManagerController/getInstanceUnitAndProtocol',
        type: 'POST',
        async: false,
        data: { instance: instance, condition: condition, type: type },
        dataType: 'json',
        success: function (result) {
            info.protocolCode = result.protocolCode;
            info.acqUnitId = result.acqUnitId;
            info.displayUnitId = result.displayUnitId;
            info.alarmUnitId = result.alarmUnitId;
            info.displayInstanceList = result.displayInstanceList || [];
            info.alarmInstanceList = result.alarmInstanceList || [];
        }
    });
    return info;
}