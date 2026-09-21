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

function onDmAddDevice() {
    var deviceType = getCurrentDeviceType();
    var dictDeviceType = deviceType;
    if (dictDeviceType && dictDeviceType.indexOf(',') > -1) {
        dictDeviceType = getCurrentFirstDeviceType();
    }

    // 组织信息
    var orgId = window.parent && window.parent.getSelectOrgNodeId ? window.parent.getSelectOrgNodeId() : '';
    var orgName = window.parent && window.parent.getSelectOrgNodePath ? window.parent.getSelectOrgNodePath() : '';

    // IoT 开关（父窗口的全局变量）
    var iotEnable = false;
    try {
        if (window.parent && window.parent.IoTConfig) iotEnable = true;
    } catch (e) {}

    var winHeight=650;
    //if(!iotEnable){
    //	winHeight=400;
    //}
    mini.open({
        title: _loginUserLanguageResource.addDevice,
        url: context + '/miniui-app/modules/device/deviceAddWindow.jsp',
        width: 500,
        height: winHeight,
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;

            contentWindow.setData({
                orgId: orgId,
                orgName: orgName,
                deviceType: deviceType,
                dictDeviceType: dictDeviceType,
                iotEnable: iotEnable,
                editMode: false
            });

            contentWindow._parentRefreshDeviceList = function () {
                CreateAndLoadDeviceInfoTable(false);
            };
        }
    });
}
function onDmDelDevice() {
    if (parseInt(_dmModuleRight.editFlag) != 1) return;

    var startRow = _dmDeviceSelectRow;
    var endRow   = _dmDeviceSelectEndRow;

    if (startRow === '' || endRow === ''
        || startRow == undefined || endRow == undefined) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }

    startRow = parseInt(startRow);
    endRow = parseInt(endRow);
    if (startRow > endRow) {
        var t = startRow;
        startRow = endRow;
        endRow = t;
    }

    // 收集设备 ID 和名称
    var delidslist = [];
    var delDeviceNameList = [];
    for (var i = startRow; i <= endRow; i++) {
        var deviceId   = deviceInfoHandsontableHelper.hot.getDataAtRowProp(i, 'id');
        var deviceName = deviceInfoHandsontableHelper.hot.getDataAtRowProp(i, 'deviceName');
        if (deviceId != null && parseInt(deviceId) > 0) {
            delidslist.push(deviceId);
            delDeviceNameList.push(deviceName);
        }
    }

    if (delidslist.length === 0) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }

    var deleteInfo = _loginUserLanguageResource.confirmDelete;
    if (delidslist.length === 1) {
        deleteInfo = _loginUserLanguageResource.deviceName
            + ":<font color=red>" + delDeviceNameList[0] + "</font>"
            + "</br>" + _loginUserLanguageResource.confirmDelete;
    } else {
        deleteInfo = _loginUserLanguageResource.sparseRecordCount
            + ":<font color=red>" + delidslist.length + "</font>"
            + "</br>" + _loginUserLanguageResource.confirmDelete;
    }

    mini.confirm(deleteInfo, _loginUserLanguageResource.tip, function (action) {
        if (action !== 'ok') return;

        var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
        var deviceType = getCurrentDeviceType();

        var saveData = {
            updatelist: [],
            insertlist: [],
            delidslist: delidslist
        };

        $.ajax({
            url: context + '/wellInformationManagerController/saveWellHandsontableData',
            type: 'POST',
            data: {
                data: JSON.stringify(saveData),
                orgId: leftOrgId,
                deviceType: deviceType
            },
            dataType: 'json',
            success: function (result) {
                if (result.success === true || result.flag === true) {
                    mini.alert(_loginUserLanguageResource.deleteSuccessfully, _loginUserLanguageResource.tip);
                    deviceInfoHandsontableHelper.clearContainer();
                    _dmDeviceSelectRow = 0;
                    _dmDeviceSelectEndRow = 0;
                    CreateAndLoadDeviceInfoTable(true);
                } else {
                    mini.alert('<font color=red>'
                        + _loginUserLanguageResource.deleteFailed
                        + '</font>', _loginUserLanguageResource.tip);
                }
            },
            error: function () {
                mini.alert(_loginUserLanguageResource.requestFailed, _loginUserLanguageResource.tip);
                deviceInfoHandsontableHelper.clearContainer();
            }
        });
    });
}
function onDmSaveDevice() {
    if (parseInt(_dmModuleRight.editFlag) != 1) return;
    if (!deviceInfoHandsontableHelper) return;

    deviceInfoHandsontableHelper.saveData();
}
function onDmBatchAddDevice() {
    var deviceType = getCurrentDeviceType();
    var dictDeviceType = deviceType;
    if (dictDeviceType && dictDeviceType.indexOf(',') > -1) {
        dictDeviceType = getCurrentFirstDeviceType();
    }

    // 组织信息
    var orgId = window.parent && window.parent.getSelectOrgNodeId ? window.parent.getSelectOrgNodeId() : '';
    var orgName = window.parent && window.parent.getSelectOrgNodePath ? window.parent.getSelectOrgNodePath() : '';

    mini.open({
        title: _loginUserLanguageResource.batchAdd,
        url: context + '/miniui-app/modules/device/batchAddDeviceWindow.jsp',
        width: 1300,
        height: 600,
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function () {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;

            // 直接通过 setData 传递参数，不再依赖隐藏控件
            contentWindow.setData({
                orgId: orgId,
                orgName: orgName,
                deviceType: deviceType,
                dictDeviceType: dictDeviceType
            });

            // 刷新主列表回调
            contentWindow._parentRefreshDeviceList = function () {
                CreateAndLoadDeviceInfoTable(true);
            };

            // ★ 新增：由主页面打开冲突窗口的回调
            contentWindow._parentOpenCollisionWindow = function (rdata, deviceType, orgId) {
            	openBatchAddDeviceCollisionDataWindow(rdata, deviceType, orgId);
            };
        }
    });
}
function openBatchAddDeviceCollisionDataWindow(rdata, deviceType, orgId){
	mini.open({
        title: _loginUserLanguageResource.exceptionData,
        url: context + '/miniui-app/modules/device/batchAddDeviceCollisionDataWindow.jsp',
        width: 1400,
        height: 600,
        modal: true,
        allowResize: true,
        onload: function () {
            var collisionIframe = this.getIFrameEl();
            var collisionContentWindow = collisionIframe.contentWindow;
            // 直接将数据传入冲突窗口
            collisionContentWindow.setData({
                result: rdata,
                deviceType: deviceType,
                orgId: orgId
            });
            collisionContentWindow._parentRefreshDeviceList = function () {
                CreateAndLoadDeviceInfoTable(true);
            };
        }
    });
}

//================================================================
//设备隶属迁移
//================================================================
function onDmDeviceOrgChange() {
 var deviceType = getCurrentDeviceType();
 var dictDeviceType = deviceType;
 if (dictDeviceType && dictDeviceType.indexOf(',') > -1) {
     dictDeviceType = getCurrentFirstDeviceType();
 }

 // 组织信息
 var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
 var orgName = window.parent && window.parent.getSelectOrgNodePath ? window.parent.getSelectOrgNodePath() : '';

 mini.open({
     title: _loginUserLanguageResource.deviceOrgChange,
     url: context + '/miniui-app/modules/device/deviceOrgChangeWindow.jsp',
     width: 900,
     height: 600,
     modal: true,
     allowResize: true,
     maxable: true,
     onload: function () {
         var iframe = this.getIFrameEl();
         var contentWindow = iframe.contentWindow;

         contentWindow.setData({
             orgId: orgId,
             orgName: orgName,
             deviceType: deviceType,
             dictDeviceType: dictDeviceType
         });

         // 迁移成功后刷新主列表
         contentWindow._parentRefreshDeviceList = function () {
             CreateAndLoadDeviceInfoTable(true);
         };
     }
 });
}
//================================================================
//设备完整数据导出
//================================================================
function onDmExportDevice() {
 if (parseInt(_dmModuleRight.editFlag) != 1) return;

 var url = context + '/wellInformationManagerController/exportDeviceCompleteData';

 var timestamp = new Date().getTime();
 var key = 'exportDeviceCompleteData' + '_' + timestamp;
 var maskPanelId = 'deviceManagerPanel';   // ★ 用 panel id 做遮罩容器

 var param = '&recordCount=10000'
     + '&fileName=' + URLencode(URLencode(_loginUserLanguageResource.primaryDdeviceExportFileName))
     + '&key=' + key;

 exportDataMask(key, maskPanelId, _loginUserLanguageResource.loadingData);
 downloadFile(url + '?flag=true' + param);
}
//================================================================
//视频密钥编辑
//================================================================
function onDmEditVideoKey() {
 // 权限判断
 if (parseInt(_dmModuleRight.editFlag) != 1) return;
 if (parseInt(loginUserRoleVideoKeyEdit) != 1) return;

 // 组织信息
 var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
 var orgName = window.parent && window.parent.getSelectOrgNodePath ? window.parent.getSelectOrgNodePath() : '';
 var selectedOrgId = window.parent && window.parent.getSelectOrgNodeId ? window.parent.getSelectOrgNodeId() : '';

 mini.open({
     title: _loginUserLanguageResource.editVideoKey,
     url: context + '/miniui-app/modules/device/videoKeyInfoWindow.jsp',
     width: 900,
     height: 600,
     modal: true,
     allowResize: true,
     maxable: true,
     onload: function () {
         var iframe = this.getIFrameEl();
         var contentWindow = iframe.contentWindow;

         contentWindow.setData({
             orgId: orgId,
             selectedOrgId: selectedOrgId,
             orgName: orgName
         });
     },
     ondestroy: function(action) {
    	 CreateAndLoadVideoInfoTable(_dmSelectedDeviceId, _dmCurrentDeviceName, true);
     }
 });
}

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

//================================================================
//生产数据配置 - 井筒数据 下行
//================================================================
function onDmProductionDataDownlink() {
 var deviceId = _dmSelectedDeviceId;
 var deviceName = _dmCurrentDeviceName;
 if (!deviceId || deviceId <= 0) {
     mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
     return;
 }

 var appScenName = deviceInfoHandsontableHelper.hot.getDataAtRowProp(_dmDeviceSelectRow, 'applicationScenariosName');
 var applicationScenarios = getApplicationScenariosValue(appScenName);
 var deviceCalculateDataType = _dmCurrentCalculateType;
 var FESDiagramSrcName = '';
 var manualInterventionResultName = _loginUserLanguageResource.noIntervention;
 var deviceProductionData = {};
 var pumpingUnitInfo = {};

 if (deviceCalculateDataType == 1) {
     // 功图计算数据组装
     if (productionHandsontableHelper != null && productionHandsontableHelper.hot != undefined) {
         var data = productionHandsontableHelper.hot.getData();
         deviceProductionData.FluidPVT = {};
         if (applicationScenarios == 1 && isNumber(parseFloat(data[0][2]))) deviceProductionData.FluidPVT.CrudeOilDensity = parseFloat(data[0][2]);
         if (isNumber(parseFloat(data[1][2]))) deviceProductionData.FluidPVT.WaterDensity = parseFloat(data[1][2]);
         if (isNumber(parseFloat(data[2][2]))) deviceProductionData.FluidPVT.NaturalGasRelativeDensity = parseFloat(data[2][2]);
         if (applicationScenarios == 1 && isNumber(parseFloat(data[3][2]))) deviceProductionData.FluidPVT.SaturationPressure = parseFloat(data[3][2]);

         deviceProductionData.Reservoir = {};
         if (isNumber(parseFloat(data[4][2]))) deviceProductionData.Reservoir.Depth = parseFloat(data[4][2]);
         if (isNumber(parseFloat(data[5][2]))) deviceProductionData.Reservoir.Temperature = parseFloat(data[5][2]);

         deviceProductionData.Production = {};
         if (isNumber(parseFloat(data[6][2]))) deviceProductionData.Production.TubingPressure = parseFloat(data[6][2]);
         if (isNumber(parseFloat(data[7][2]))) deviceProductionData.Production.CasingPressure = parseFloat(data[7][2]);
         if (isNumber(parseFloat(data[8][2]))) deviceProductionData.Production.WellHeadTemperature = parseFloat(data[8][2]);
         if (applicationScenarios == 0) deviceProductionData.Production.WaterCut = 100;
         else if (isNumber(parseFloat(data[9][2]))) deviceProductionData.Production.WaterCut = parseFloat(data[9][2]);
         if (isNumber(parseFloat(data[10][2]))) deviceProductionData.Production.ProductionGasOilRatio = parseFloat(data[10][2]);
         if (isNumber(parseFloat(data[11][2]))) deviceProductionData.Production.ProducingfluidLevel = parseFloat(data[11][2]);
         if (isNumber(parseFloat(data[12][2]))) deviceProductionData.Production.PumpSettingDepth = parseFloat(data[12][2]);

         deviceProductionData.Pump = { PumpType: 'T' };
         var BarrelType = data[13][2];
         if (BarrelType == _loginUserLanguageResource.barrelType_L) BarrelType = 'L';
         else if (BarrelType == _loginUserLanguageResource.barrelType_H) BarrelType = 'H';
         if (isNotVal(BarrelType)) deviceProductionData.Pump.BarrelType = BarrelType;
         if (isNumber(parseInt(data[14][2]))) deviceProductionData.Pump.PumpGrade = parseInt(data[14][2]);
         if (isNumber(parseFloat(data[15][2]))) deviceProductionData.Pump.PumpBoreDiameter = parseFloat(data[15][2]) * 0.001;
         if (isNumber(parseFloat(data[16][2]))) deviceProductionData.Pump.PumpBoreDiameter2 = parseFloat(data[16][2]) * 0.001;
         if (isNumber(parseFloat(data[17][2]))) deviceProductionData.Pump.PlungerLength = parseFloat(data[17][2]);

         deviceProductionData.TubingString = { EveryTubing: [] };
         var tubing = {};
         if (isNumber(parseFloat(data[18][2]))) tubing.InsideDiameter = parseFloat(data[18][2]) * 0.001;
         deviceProductionData.TubingString.EveryTubing.push(tubing);

         deviceProductionData.CasingString = { EveryCasing: [] };
         var casing = {};
         if (isNumber(parseFloat(data[19][2]))) casing.InsideDiameter = parseFloat(data[19][2]) * 0.001;
         deviceProductionData.CasingString.EveryCasing.push(casing);

         deviceProductionData.RodString = { EveryRod: [] };
         var rodStart = [20, 25, 30, 35];
         for (var i = 0; i < rodStart.length; i++) {
             var idx = rodStart[i];
             if (isNotVal(data[idx][2]) && isNotVal(data[idx+1][2]) && isNumber(parseFloat(data[idx+2][2])) && (data[idx+3][2] == '' || isNumber(parseFloat(data[idx+3][2]))) && isNumber(parseFloat(data[idx+4][2]))) {
                 var rod = {};
                 if (data[idx][2] == _loginUserLanguageResource.rodStringTypeValue1) rod.Type = 1;
                 else if (data[idx][2] == _loginUserLanguageResource.rodStringTypeValue2) rod.Type = 2;
                 else if (data[idx][2] == _loginUserLanguageResource.rodStringTypeValue3) rod.Type = 3;
                 rod.Grade = data[idx+1][2];
                 rod.OutsideDiameter = parseFloat(data[idx+2][2]) * 0.001;
                 if (isNumber(parseFloat(data[idx+3][2]))) rod.InsideDiameter = parseFloat(data[idx+3][2]) * 0.001;
                 rod.Length = parseFloat(data[idx+4][2]);
                 deviceProductionData.RodString.EveryRod.push(rod);
             }
         }

         deviceProductionData.ManualIntervention = {};
         manualInterventionResultName = isNotVal(data[40][2]) ? data[40][2] : '';
         if (isNumber(parseFloat(data[41][2]))) deviceProductionData.ManualIntervention.NetGrossRatio = parseFloat(data[41][2]);
         if (isNumber(parseFloat(data[42][2]))) deviceProductionData.ManualIntervention.NetGrossValue = parseFloat(data[42][2]);
         if (isNumber(parseFloat(data[43][2]))) deviceProductionData.ManualIntervention.LevelCorrectValue = parseFloat(data[43][2]);
         deviceProductionData.FESDiagram = { Src: 0 };
         FESDiagramSrcName = isNotVal(data[44][2]) ? data[44][2] : '';
     }

     pumpingUnitInfo.Balance = { EveryBalance: [] };
     pumpingUnitInfo.Stroke = "";
     if (pumpingInfoHandsontableHelper != null && pumpingInfoHandsontableHelper.hot != undefined) {
         var pumpData = pumpingInfoHandsontableHelper.hot.getData();
         pumpingUnitInfo.Stroke = pumpData[3][2];
         for (var i = 6; i < pumpData.length; i++) {
             if (isNotVal(pumpData[i][1]) || isNotVal(pumpData[i][2])) {
                 pumpingUnitInfo.Balance.EveryBalance.push({ Position: pumpData[i][1], Weight: pumpData[i][2] });
             }
         }
     }
 } else if (deviceCalculateDataType == 2) {
     // 转速计产数据组装
     if (productionHandsontableHelper != null && productionHandsontableHelper.hot != undefined) {
         var data = productionHandsontableHelper.hot.getData();
         deviceProductionData.FluidPVT = {};
         if (applicationScenarios == 1 && isNumber(parseFloat(data[0][2]))) deviceProductionData.FluidPVT.CrudeOilDensity = parseFloat(data[0][2]);
         if (isNumber(parseFloat(data[1][2]))) deviceProductionData.FluidPVT.WaterDensity = parseFloat(data[1][2]);
         if (isNumber(parseFloat(data[2][2]))) deviceProductionData.FluidPVT.NaturalGasRelativeDensity = parseFloat(data[2][2]);
         if (applicationScenarios == 1 && isNumber(parseFloat(data[3][2]))) deviceProductionData.FluidPVT.SaturationPressure = parseFloat(data[3][2]);

         deviceProductionData.Reservoir = {};
         if (isNumber(parseFloat(data[4][2]))) deviceProductionData.Reservoir.Depth = parseFloat(data[4][2]);
         if (isNumber(parseFloat(data[5][2]))) deviceProductionData.Reservoir.Temperature = parseFloat(data[5][2]);

         deviceProductionData.Production = {};
         if (isNumber(parseFloat(data[6][2]))) deviceProductionData.Production.TubingPressure = parseFloat(data[6][2]);
         if (isNumber(parseFloat(data[7][2]))) deviceProductionData.Production.CasingPressure = parseFloat(data[7][2]);
         if (isNumber(parseFloat(data[8][2]))) deviceProductionData.Production.WellHeadTemperature = parseFloat(data[8][2]);
         if (applicationScenarios == 0) deviceProductionData.Production.WaterCut = 100;
         else if (isNumber(parseFloat(data[9][2]))) deviceProductionData.Production.WaterCut = parseFloat(data[9][2]);
         if (applicationScenarios == 1 && isNumber(parseFloat(data[10][2]))) deviceProductionData.Production.ProductionGasOilRatio = parseFloat(data[10][2]);
         if (isNumber(parseFloat(data[11][2]))) deviceProductionData.Production.ProducingfluidLevel = parseFloat(data[11][2]);
         if (isNumber(parseFloat(data[12][2]))) deviceProductionData.Production.PumpSettingDepth = parseFloat(data[12][2]);

         deviceProductionData.Pump = {};
         if (isNumber(parseFloat(data[13][2]))) deviceProductionData.Pump.BarrelLength = parseFloat(data[13][2]);
         if (isNumber(parseFloat(data[14][2]))) deviceProductionData.Pump.BarrelSeries = parseFloat(data[14][2]);
         if (isNumber(parseFloat(data[15][2]))) deviceProductionData.Pump.RotorDiameter = parseFloat(data[15][2]) * 0.001;
         if (isNumber(parseFloat(data[16][2]))) deviceProductionData.Pump.QPR = parseFloat(data[16][2]);

         deviceProductionData.TubingString = { EveryTubing: [] };
         var tubing2 = {};
         if (isNumber(parseFloat(data[17][2]))) tubing2.InsideDiameter = parseFloat(data[17][2]) * 0.001;
         deviceProductionData.TubingString.EveryTubing.push(tubing2);

         deviceProductionData.CasingString = { EveryCasing: [] };
         var casing2 = {};
         if (isNumber(parseFloat(data[18][2]))) casing2.InsideDiameter = parseFloat(data[18][2]) * 0.001;
         deviceProductionData.CasingString.EveryCasing.push(casing2);

         deviceProductionData.RodString = { EveryRod: [] };
         var rodStart2 = [19, 24, 29, 34];
         for (var i = 0; i < rodStart2.length; i++) {
             var idx = rodStart2[i];
             if (isNotVal(data[idx][2]) && isNotVal(data[idx+1][2]) && isNumber(parseFloat(data[idx+2][2])) && (data[idx+3][2] == '' || isNumber(parseFloat(data[idx+3][2]))) && isNumber(parseFloat(data[idx+4][2]))) {
                 var rod2 = {};
                 if (data[idx][2] == _loginUserLanguageResource.rodStringTypeValue1) rod2.Type = 1;
                 else if (data[idx][2] == _loginUserLanguageResource.rodStringTypeValue2) rod2.Type = 2;
                 else if (data[idx][2] == _loginUserLanguageResource.rodStringTypeValue3) rod2.Type = 3;
                 rod2.Grade = data[idx+1][2];
                 rod2.OutsideDiameter = parseFloat(data[idx+2][2]) * 0.001;
                 if (isNumber(parseFloat(data[idx+3][2]))) rod2.InsideDiameter = parseFloat(data[idx+3][2]) * 0.001;
                 rod2.Length = parseFloat(data[idx+4][2]);
                 deviceProductionData.RodString.EveryRod.push(rod2);
             }
         }

         deviceProductionData.ManualIntervention = {};
         if (isNumber(parseFloat(data[39][2]))) deviceProductionData.ManualIntervention.NetGrossRatio = parseFloat(data[39][2]);
         if (isNumber(parseFloat(data[40][2]))) deviceProductionData.ManualIntervention.NetGrossValue = parseFloat(data[40][2]);
     }
 }

 var confirmInfo = _loginUserLanguageResource.wellboreData + '</br>' +
     _loginUserLanguageResource.deviceName + ":<font color=red>" + deviceName + "</font></br>" +
     _loginUserLanguageResource.confirmDownlink;

 mini.confirm(confirmInfo, _loginUserLanguageResource.tip, function (action) {
     if (action == "ok") {
         mini.mask({ el: 'wellboreDataPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.commandSending + '...' });
         $.ajax({
             url: context + '/wellInformationManagerController/deviceProductionDataDownlink',
             type: "POST",
             data: {
                 deviceId: deviceId,
                 deviceName: deviceName,
                 deviceCalculateDataType: deviceCalculateDataType,
                 productionData: JSON.stringify(deviceProductionData),
                 pumpingUnitInfo: JSON.stringify(pumpingUnitInfo),
                 manualInterventionResultName: manualInterventionResultName,
                 FESDiagramSrcName: FESDiagramSrcName,
                 applicationScenarios: applicationScenarios
             },
             dataType: 'json',
             success: function (result) {
                 mini.unmask('wellboreDataPanel');
                 if (result.flag === false) {
                     mini.alert("<font color=red>" + _loginUserLanguageResource.sessionExpired + "</font>", _loginUserLanguageResource.tip, function () {
                         window.location.href = context + "/login";
                     });
                 } else if (result.flag === true && result.error === false) {
                     mini.alert("<font color=red>" + result.msg + "</font>", _loginUserLanguageResource.tip);
                 } else if (result.flag === true && result.error === true) {
                     // 显示下行状态列
                     var plugin = productionHandsontableHelper.hot.getPlugin('hiddenColumns');
                     plugin.showColumns([4]);
                     plugin.hideColumns([5]);
                     productionHandsontableHelper.hot.render();

                     var codeColumnValue = productionHandsontableHelper.hot.getDataAtProp('itemCode');
                     for (var i = 0; i < codeColumnValue.length; i++) {
                         for (var j = 0; j < result.downStatusList.length; j++) {
                             if (codeColumnValue[i] && result.downStatusList[j].key.toUpperCase() == codeColumnValue[i].toUpperCase()) {
                                 productionHandsontableHelper.hot.setDataAtRowProp(i, 'downlinkStatus', result.downStatusList[j].status);
                                 break;
                             }
                         }
                     }
                 }
             },
             error: function () {
                 mini.unmask('wellboreDataPanel');
                 mini.alert("【<font color=red>" + _loginUserLanguageResource.exceptionThrow + "</font>】:" + _loginUserLanguageResource.contactAdmin, _loginUserLanguageResource.tip);
             }
         });
     }
 });
}

//================================================================
//生产数据配置 - 井筒数据 上行
//================================================================
function onDmProductionDataUplink() {
 var deviceId = _dmSelectedDeviceId;
 if (!deviceId || deviceId <= 0) {
     mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
     return;
 }
 var deviceCalculateDataType = _dmCurrentCalculateType;

 if (deviceCalculateDataType == 1 || deviceCalculateDataType == 2) {
     mini.mask({ el: 'wellboreDataPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.commandSending + '...' });
     $.ajax({
         url: context + '/wellInformationManagerController/deviceProductionDataUplink',
         type: "POST",
         data: { deviceId: deviceId, deviceName: _dmCurrentDeviceName, deviceCalculateDataType: deviceCalculateDataType },
         dataType: 'json',
         success: function (result) {
             mini.unmask('wellboreDataPanel');
             if (result.flag === false) {
                 mini.alert("<font color=red>" + _loginUserLanguageResource.sessionExpired + "</font>", _loginUserLanguageResource.tip, function () { window.location.href = context + "/login"; });
             } else if (result.flag === true && result.error === false) {
                 mini.alert("<font color=red>" + result.msg + "</font>", _loginUserLanguageResource.tip);
             } else if (result.flag === true && result.error === true) {
                 var plugin = productionHandsontableHelper.hot.getPlugin('hiddenColumns');
                 plugin.showColumns([5]);
                 plugin.hideColumns([4]);
                 productionHandsontableHelper.hot.render();

                 var codeColumnValue = productionHandsontableHelper.hot.getDataAtProp('itemCode');
                 for (var i = 0; i < codeColumnValue.length; i++) {
                     for (var j = 0; j < result.downStatusList.length; j++) {
                         if (codeColumnValue[i] && result.downStatusList[j].key.toUpperCase() == codeColumnValue[i].toUpperCase()) {
                             productionHandsontableHelper.hot.setDataAtRowProp(i, 'uplinkStatus', result.downStatusList[j].status);
                             break;
                         }
                     }
                 }
             }
         },
         error: function () {
             mini.unmask('wellboreDataPanel');
             mini.alert("【<font color=red>" + _loginUserLanguageResource.exceptionThrow + "</font>】:" + _loginUserLanguageResource.contactAdmin, _loginUserLanguageResource.tip);
         }
     });
 }
}
//================================================================
//抽油机数据 下行
//================================================================
function onDmPumpingUnitDataDownlink() {
 var deviceId = _dmSelectedDeviceId;
 if (!deviceId || deviceId <= 0) { mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip); return; }

 var balanceInfo = {}, manufacturer = '', model = '', stroke = "";
 if (pumpingInfoHandsontableHelper != null && pumpingInfoHandsontableHelper.hot != undefined) {
     manufacturer = pumpingInfoHandsontableHelper.hot.getDataAtRowProp(1, 'itemValue2');
     model = pumpingInfoHandsontableHelper.hot.getDataAtRowProp(2, 'itemValue2');
     stroke = pumpingInfoHandsontableHelper.hot.getDataAtRowProp(3, 'itemValue2');
     var data = pumpingInfoHandsontableHelper.hot.getData();
     balanceInfo.EveryBalance = [];
     for (var i = 6; i < data.length; i++) {
         if (isNotVal(data[i][1]) || isNotVal(data[i][2])) {
             balanceInfo.EveryBalance.push({ Position: data[i][1], Weight: data[i][2] });
         }
     }
 }

 var confirmInfo = _loginUserLanguageResource.pumpingUnitData + '</br>' + _loginUserLanguageResource.deviceName + ":<font color=red>" + _dmCurrentDeviceName + "</font></br>" + _loginUserLanguageResource.confirmDownlink;
 mini.confirm(confirmInfo, _loginUserLanguageResource.tip, function (action) {
     if (action == "ok") {
         mini.mask({ el: 'pumpingUnitDataPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.commandSending + '...' });
         $.ajax({
             url: context + '/wellInformationManagerController/devicePumpingUnitDataDownlink',
             type: "POST",
             data: { deviceId: deviceId, deviceName: _dmCurrentDeviceName, manufacturer: manufacturer, model: model, stroke: stroke, balanceInfo: JSON.stringify(balanceInfo) },
             dataType: 'json',
             success: function (result) {
                 mini.unmask('pumpingUnitDataPanel');
                 if (result.flag === false) {
                     mini.alert("<font color=red>" + _loginUserLanguageResource.sessionExpired + "</font>", _loginUserLanguageResource.tip, function () { window.location.href = context + "/login"; });
                 } else if (result.flag === true && result.error === false) {
                     mini.alert("<font color=red>" + result.msg + "</font>", _loginUserLanguageResource.tip);
                 } else if (result.flag === true && result.error === true) {
                     // 1. 抽油机基础数据
                     var plugin = pumpingInfoHandsontableHelper.hot.getPlugin('hiddenColumns');
                     plugin.showColumns([4]); plugin.hideColumns([5]); pumpingInfoHandsontableHelper.hot.render();
                     var codeColumnValue = pumpingInfoHandsontableHelper.hot.getDataAtProp('itemCode');
                     for (var i = 0; i < codeColumnValue.length; i++) {
                         for (var j = 0; j < result.downStatusList.length; j++) {
                             if (codeColumnValue[i] && result.downStatusList[j].key.toUpperCase() == codeColumnValue[i].toUpperCase()) {
                                 pumpingInfoHandsontableHelper.hot.setDataAtRowProp(i, 'downlinkStatus', result.downStatusList[j].status); break;
                             }
                         }
                     }
                     // 2. 抽油机详细信息
                     plugin = devicePumpingUnitDetailedInformationHandsontableHelper.hot.getPlugin('hiddenColumns');
                     plugin.showColumns([4]); plugin.hideColumns([5]); devicePumpingUnitDetailedInformationHandsontableHelper.hot.render();
                     codeColumnValue = devicePumpingUnitDetailedInformationHandsontableHelper.hot.getDataAtProp('itemCode');
                     for (var i = 0; i < codeColumnValue.length; i++) {
                         for (var j = 0; j < result.downStatusList.length; j++) {
                             if (codeColumnValue[i] && result.downStatusList[j].key.toUpperCase() == codeColumnValue[i].toUpperCase()) {
                                 devicePumpingUnitDetailedInformationHandsontableHelper.hot.setDataAtRowProp(i, 'downlinkStatus', result.downStatusList[j].status); break;
                             }
                         }
                     }
                     // 3. PRTF数据
                     plugin = devicePumpingUnitPRTFHandsontableHelper.hot.getPlugin('hiddenColumns');
                     plugin.showColumns([1, 4, 7]); plugin.hideColumns([2, 5, 8]); devicePumpingUnitPRTFHandsontableHelper.hot.render();
                     var rowCount = devicePumpingUnitPRTFHandsontableHelper.hot.countRows();
                     for (var j = 0; j < result.downStatusList.length; j++) {
                         var key = result.downStatusList[j].key.toUpperCase();
                         var status = result.downStatusList[j].status;
                         if (key == 'CRANKANGLE') {
                             var updateData = []; for (var i = 0; i < rowCount; i++) updateData.push([i, 'CrankAngleDownlinkStatus', status]);
                             devicePumpingUnitPRTFHandsontableHelper.hot.setDataAtRowProp(updateData);
                         } else if (key == 'PR') {
                             var updateData = []; for (var i = 0; i < rowCount; i++) updateData.push([i, 'PRDownlinkStatus', status]);
                             devicePumpingUnitPRTFHandsontableHelper.hot.setDataAtRowProp(updateData);
                         } else if (key == 'TF') {
                             var updateData = []; for (var i = 0; i < rowCount; i++) updateData.push([i, 'TFDownlinkStatus', status]);
                             devicePumpingUnitPRTFHandsontableHelper.hot.setDataAtRowProp(updateData);
                         }
                     }
                 }
             },
             error: function () { mini.unmask('pumpingUnitDataPanel'); mini.alert(_loginUserLanguageResource.exceptionThrow, _loginUserLanguageResource.tip); }
         });
     }
 });
}

//================================================================
//抽油机数据 上行
//================================================================
function onDmPumpingUnitDataUplink() {
 var deviceId = _dmSelectedDeviceId;
 if (!deviceId || deviceId <= 0) { mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip); return; }

 mini.mask({ el: 'pumpingUnitDataPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.commandSending + '...' });
 $.ajax({
     url: context + '/wellInformationManagerController/devicePumpingUnitDataUplink',
     type: "POST",
     data: { deviceId: deviceId },
     dataType: 'json',
     success: function (result) {
         mini.unmask('pumpingUnitDataPanel');
         if (result.flag === false) {
             mini.alert("<font color=red>" + _loginUserLanguageResource.sessionExpired + "</font>", _loginUserLanguageResource.tip, function () { window.location.href = context + "/login"; });
         } else if (result.flag === true && result.error === false) {
             mini.alert("<font color=red>" + result.msg + "</font>", _loginUserLanguageResource.tip);
         } else if (result.flag === true && result.error === true) {
             // 1. 基础数据
             var plugin = pumpingInfoHandsontableHelper.hot.getPlugin('hiddenColumns');
             plugin.showColumns([5]); plugin.hideColumns([4]); pumpingInfoHandsontableHelper.hot.render();
             var codeColumnValue = pumpingInfoHandsontableHelper.hot.getDataAtProp('itemCode');
             for (var i = 0; i < codeColumnValue.length; i++) {
                 for (var j = 0; j < result.downStatusList.length; j++) {
                     if (codeColumnValue[i] && result.downStatusList[j].key.toUpperCase() == codeColumnValue[i].toUpperCase()) {
                         pumpingInfoHandsontableHelper.hot.setDataAtRowProp(i, 'uplinkStatus', result.downStatusList[j].status); break;
                     }
                 }
             }
             // 2. 详细信息
             plugin = devicePumpingUnitDetailedInformationHandsontableHelper.hot.getPlugin('hiddenColumns');
             plugin.showColumns([5]); plugin.hideColumns([4]); devicePumpingUnitDetailedInformationHandsontableHelper.hot.render();
             codeColumnValue = devicePumpingUnitDetailedInformationHandsontableHelper.hot.getDataAtProp('itemCode');
             for (var i = 0; i < codeColumnValue.length; i++) {
                 for (var j = 0; j < result.downStatusList.length; j++) {
                     if (codeColumnValue[i] && result.downStatusList[j].key.toUpperCase() == codeColumnValue[i].toUpperCase()) {
                         devicePumpingUnitDetailedInformationHandsontableHelper.hot.setDataAtRowProp(i, 'uplinkStatus', result.downStatusList[j].status); break;
                     }
                 }
             }
             // 3. PRTF 数据
             plugin = devicePumpingUnitPRTFHandsontableHelper.hot.getPlugin('hiddenColumns');
             plugin.showColumns([2, 5, 8]); plugin.hideColumns([1, 4, 7]); devicePumpingUnitPRTFHandsontableHelper.hot.render();
             var rowCount = devicePumpingUnitPRTFHandsontableHelper.hot.countRows();
             for (var j = 0; j < result.downStatusList.length; j++) {
                 var key = result.downStatusList[j].key.toUpperCase();
                 var status = result.downStatusList[j].status;
                 var statusArr = (status != _loginUserLanguageResource.uplinkFailed && isNotVal(status)) ? status.split(',') : [];
                 if (key == 'CRANKANGLE') {
                     var upData = []; for (var i = 0; i < rowCount; i++) upData.push([i, 'CrankAngleUplinkStatus', i < statusArr.length ? statusArr[i] : status]);
                     devicePumpingUnitPRTFHandsontableHelper.hot.setDataAtRowProp(upData);
                 } else if (key == 'PR') {
                     var upData = []; for (var i = 0; i < rowCount; i++) upData.push([i, 'PRUplinkStatus', i < statusArr.length ? statusArr[i] : status]);
                     devicePumpingUnitPRTFHandsontableHelper.hot.setDataAtRowProp(upData);
                 } else if (key == 'TF') {
                     var upData = []; for (var i = 0; i < rowCount; i++) upData.push([i, 'TFUplinkStatus', i < statusArr.length ? statusArr[i] : status]);
                     devicePumpingUnitPRTFHandsontableHelper.hot.setDataAtRowProp(upData);
                 }
             }
         }
     },
     error: function () { mini.unmask('pumpingUnitDataPanel'); mini.alert(_loginUserLanguageResource.exceptionThrow, _loginUserLanguageResource.tip); }
 });
}
//================================================================
//功图构建 下行
//================================================================
function onDmFSDiagramConstructionDataDownlink() {
 var deviceId = _dmSelectedDeviceId;
 if (!deviceId || deviceId <= 0) { mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip); return; }
 var FSDiagramConstructionData = {};
 if (fsDiagramConstructionHandsontableHelper != null && fsDiagramConstructionHandsontableHelper.hot != undefined) {
     var rowCount = fsDiagramConstructionHandsontableHelper.hot.countRows();
     for (var i = 0; i < rowCount; i++) {
         var itemCode = fsDiagramConstructionHandsontableHelper.hot.getDataAtRowProp(i, 'itemCode');
         var itemValue = fsDiagramConstructionHandsontableHelper.hot.getDataAtRowProp(i, 'itemValue');
         if (!itemCode) continue;
         var upperCode = itemCode.toUpperCase();
         if (upperCode == "BOARDDATASOURCE") {
             if (itemValue == _loginUserLanguageResource.boardDataSource1) FSDiagramConstructionData.boardDataSource = 1;
             else if (itemValue == _loginUserLanguageResource.boardDataSource2) FSDiagramConstructionData.boardDataSource = 2;
             else if (itemValue == _loginUserLanguageResource.boardDataSource3) FSDiagramConstructionData.boardDataSource = 3;
         } else if (upperCode == "CRANKDINITANGLE" && isNumber(parseFloat(itemValue))) FSDiagramConstructionData.CrankDIInitAngle = parseFloat(itemValue);
         else if (upperCode == "INTERPOLATIONCNT" && isNumber(parseFloat(itemValue))) FSDiagramConstructionData.InterpolationCNT = parseFloat(itemValue);
         else if (upperCode == "SURFACESYSTEMEFFICIENCY" && isNumber(parseFloat(itemValue))) FSDiagramConstructionData.SurfaceSystemEfficiency = parseFloat(itemValue);
         else if (upperCode == "WATTTIMES" && isNumber(parseFloat(itemValue))) FSDiagramConstructionData.WattTimes = parseFloat(itemValue);
         else if (upperCode == "ITIMES" && isNumber(parseFloat(itemValue))) FSDiagramConstructionData.ITimes = parseFloat(itemValue);
         else if (upperCode == "FSDIAGRAMTIMES" && isNumber(parseFloat(itemValue))) FSDiagramConstructionData.FSDiagramTimes = parseFloat(itemValue);
         else if (upperCode == "FSDIAGRAMLEFTTIMES" && isNumber(parseFloat(itemValue))) FSDiagramConstructionData.FSDiagramLeftTimes = parseFloat(itemValue);
         else if (upperCode == "FSDIAGRAMRIGHTTIMES" && isNumber(parseFloat(itemValue))) FSDiagramConstructionData.FSDiagramRightTimes = parseFloat(itemValue);
         else if (upperCode == "LEFTPERCENT" && isNumber(parseFloat(itemValue))) FSDiagramConstructionData.LeftPercent = parseFloat(itemValue);
         else if (upperCode == "RIGHTPERCENT" && isNumber(parseFloat(itemValue))) FSDiagramConstructionData.RightPercent = parseFloat(itemValue);
         else if (upperCode == "POSITIVEXWATT" && isNumber(parseFloat(itemValue))) FSDiagramConstructionData.PositiveXWatt = parseFloat(itemValue);
         else if (upperCode == "NEGATIVEXWATT" && isNumber(parseFloat(itemValue))) FSDiagramConstructionData.NegativeXWatt = parseFloat(itemValue);
         else if (upperCode == "PRTFSRC") {
             if (itemValue == _loginUserLanguageResource.PRTFSrc1) FSDiagramConstructionData.PRTFSrc = 1;
             else if (itemValue == _loginUserLanguageResource.PRTFSrc2) FSDiagramConstructionData.PRTFSrc = 2;
         }
     }
 }
 var confirmInfo = _loginUserLanguageResource.fsDiagramConstruction + '</br>' + _loginUserLanguageResource.deviceName + ":<font color=red>" + _dmCurrentDeviceName + "</font></br>" + _loginUserLanguageResource.confirmDownlink;
 mini.confirm(confirmInfo, _loginUserLanguageResource.tip, function (action) {
     if (action == "ok") {
         mini.mask({ el: 'fsDiagramConstructionPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.commandSending + '...' });
         $.ajax({
             url: context + '/wellInformationManagerController/deviceFSDiagramConstructionDataDownlink',
             type: "POST",
             data: { deviceId: deviceId, deviceName: _dmCurrentDeviceName, data: JSON.stringify(FSDiagramConstructionData) },
             dataType: 'json',
             success: function (result) {
                 mini.unmask('fsDiagramConstructionPanel');
                 if (result.flag === false) mini.alert("<font color=red>" + _loginUserLanguageResource.sessionExpired + "</font>", _loginUserLanguageResource.tip, function () { window.location.href = context + "/login"; });
                 else if (result.flag === true && result.error === false) mini.alert("<font color=red>" + result.msg + "</font>", _loginUserLanguageResource.tip);
                 else if (result.flag === true && result.error === true) {
                     var plugin = fsDiagramConstructionHandsontableHelper.hot.getPlugin('hiddenColumns');
                     plugin.showColumns([4]); plugin.hideColumns([5]); fsDiagramConstructionHandsontableHelper.hot.render();
                     var codeColumnValue = fsDiagramConstructionHandsontableHelper.hot.getDataAtProp('itemCode');
                     for (var i = 0; i < codeColumnValue.length; i++) {
                         for (var j = 0; j < result.downStatusList.length; j++) {
                             if (codeColumnValue[i] && result.downStatusList[j].key.toUpperCase() == codeColumnValue[i].toUpperCase()) {
                                 fsDiagramConstructionHandsontableHelper.hot.setDataAtRowProp(i, 'downlinkStatus', result.downStatusList[j].status); break;
                             }
                         }
                     }
                 }
             },
             error: function () { mini.unmask('fsDiagramConstructionPanel'); mini.alert(_loginUserLanguageResource.exceptionThrow, _loginUserLanguageResource.tip); }
         });
     }
 });
}

//功图构建 上行
function onDmFSDiagramConstructionDataUplink() {
 var deviceId = _dmSelectedDeviceId;
 if (!deviceId || deviceId <= 0) { mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip); return; }
 mini.mask({ el: 'fsDiagramConstructionPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.commandSending + '...' });
 $.ajax({
     url: context + '/wellInformationManagerController/deviceFSDiagramConstructionDataUplink',
     type: "POST", data: { deviceId: deviceId }, dataType: 'json',
     success: function (result) {
         mini.unmask('fsDiagramConstructionPanel');
         if (result.flag === false) mini.alert("<font color=red>" + _loginUserLanguageResource.sessionExpired + "</font>", _loginUserLanguageResource.tip, function () { window.location.href = context + "/login"; });
         else if (result.flag === true && result.error === false) mini.alert("<font color=red>" + result.msg + "</font>", _loginUserLanguageResource.tip);
         else if (result.flag === true && result.error === true) {
             var plugin = fsDiagramConstructionHandsontableHelper.hot.getPlugin('hiddenColumns');
             plugin.showColumns([5]); plugin.hideColumns([4]); fsDiagramConstructionHandsontableHelper.hot.render();
             var codeColumnValue = fsDiagramConstructionHandsontableHelper.hot.getDataAtProp('itemCode');
             for (var i = 0; i < codeColumnValue.length; i++) {
                 for (var j = 0; j < result.downStatusList.length; j++) {
                     if (codeColumnValue[i] && result.downStatusList[j].key.toUpperCase() == codeColumnValue[i].toUpperCase()) {
                         fsDiagramConstructionHandsontableHelper.hot.setDataAtRowProp(i, 'uplinkStatus', result.downStatusList[j].status); break;
                     }
                 }
             }
         }
     },
     error: function () { mini.unmask('fsDiagramConstructionPanel'); mini.alert(_loginUserLanguageResource.exceptionThrow, _loginUserLanguageResource.tip); }
 });
}
//================================================================
//系统参数配置 上行/下行
//================================================================
function onDmSystemParameterDataDownlink() {
 var deviceId = _dmSelectedDeviceId;
 if (!deviceId || deviceId <= 0) { mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip); return; }
 var confirmInfo = _loginUserLanguageResource.systemParameterConfiguration + '</br>' + _loginUserLanguageResource.deviceName + ":<font color=red>" + _dmCurrentDeviceName + "</font></br>" + _loginUserLanguageResource.confirmDownlink;
 mini.confirm(confirmInfo, _loginUserLanguageResource.tip, function (action) {
     if (action == "ok") {
         mini.mask({ el: 'systemParameterPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.commandSending + '...' });
         $.ajax({
             url: context + '/wellInformationManagerController/deviceSystemParameterDataDownlink',
             type: "POST", data: { deviceId: deviceId, deviceName: _dmCurrentDeviceName }, dataType: 'json',
             success: function (result) {
                 mini.unmask('systemParameterPanel');
                 if (result.flag === false) mini.alert("<font color=red>" + _loginUserLanguageResource.sessionExpired + "</font>", _loginUserLanguageResource.tip, function () { window.location.href = context + "/login"; });
                 else if (result.flag === true && result.error === false) mini.alert("<font color=red>" + result.msg + "</font>", _loginUserLanguageResource.tip);
                 else if (result.flag === true && result.error === true) {
                     var plugin = deviceSystemParameterHandsontableHelper.hot.getPlugin('hiddenColumns');
                     plugin.showColumns([4]); plugin.hideColumns([5]); deviceSystemParameterHandsontableHelper.hot.render();
                     var codeColumnValue = deviceSystemParameterHandsontableHelper.hot.getDataAtProp('itemCode');
                     for (var i = 0; i < codeColumnValue.length; i++) {
                         for (var j = 0; j < result.downStatusList.length; j++) {
                             if (codeColumnValue[i] && result.downStatusList[j].key.toUpperCase() == codeColumnValue[i].toUpperCase()) {
                                 deviceSystemParameterHandsontableHelper.hot.setDataAtRowProp(i, 'downlinkStatus', result.downStatusList[j].status); break;
                             }
                         }
                     }
                 }
             },
             error: function () { mini.unmask('systemParameterPanel'); mini.alert(_loginUserLanguageResource.exceptionThrow, _loginUserLanguageResource.tip); }
         });
     }
 });
}

function onDmSystemParameterDataUplink() {
 var deviceId = _dmSelectedDeviceId;
 if (!deviceId || deviceId <= 0) { mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip); return; }
 mini.mask({ el: 'systemParameterPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.commandSending + '...' });
 $.ajax({
     url: context + '/wellInformationManagerController/deviceSystemParameterDataUplink',
     type: "POST", data: { deviceId: deviceId }, dataType: 'json',
     success: function (result) {
         mini.unmask('systemParameterPanel');
         if (result.flag === false) mini.alert("<font color=red>" + _loginUserLanguageResource.sessionExpired + "</font>", _loginUserLanguageResource.tip, function () { window.location.href = context + "/login"; });
         else if (result.flag === true && result.error === false) mini.alert("<font color=red>" + result.msg + "</font>", _loginUserLanguageResource.tip);
         else if (result.flag === true && result.error === true) {
             var plugin = deviceSystemParameterHandsontableHelper.hot.getPlugin('hiddenColumns');
             plugin.showColumns([5]); plugin.hideColumns([4]); deviceSystemParameterHandsontableHelper.hot.render();
             var codeColumnValue = deviceSystemParameterHandsontableHelper.hot.getDataAtProp('itemCode');
             for (var i = 0; i < codeColumnValue.length; i++) {
                 for (var j = 0; j < result.downStatusList.length; j++) {
                     if (codeColumnValue[i] && result.downStatusList[j].key.toUpperCase() == codeColumnValue[i].toUpperCase()) {
                         deviceSystemParameterHandsontableHelper.hot.setDataAtRowProp(i, 'uplinkStatus', result.downStatusList[j].status); break;
                     }
                 }
             }
         }
     },
     error: function () { mini.unmask('systemParameterPanel'); mini.alert(_loginUserLanguageResource.exceptionThrow, _loginUserLanguageResource.tip); }
 });
}
//================================================================
//智能变频 上行/下行
//================================================================
function onDmIntelligentFrequencyConversionDataDownlink() {
 var deviceId = _dmSelectedDeviceId;
 if (!deviceId || deviceId <= 0) { mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip); return; }
 var frequencyConversionData = {};
 if (deviceIntelligentFrequencyConversionHandsontableHelper != null && deviceIntelligentFrequencyConversionHandsontableHelper.hot != undefined) {
     var data = deviceIntelligentFrequencyConversionHandsontableHelper.hot.getData();
     frequencyConversionData.FullnessCoefficientModel = { Enable: data[0][4] ? 1 : 0 };
     frequencyConversionData.FullnessCoefficientModel.FrequencyUpscaling = {};
     if(isNumber(parseFloat(data[1][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyUpscaling.FullnessCoefficientLimit=parseFloat(data[1][4]);
     if(isNumber(parseFloat(data[2][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyUpscaling.FrequencyUpperLimit=parseFloat(data[2][4]);
     if(isNumber(parseFloat(data[3][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyUpscaling.StepSize=parseFloat(data[3][4]);
     if(isNumber(parseFloat(data[4][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyUpscaling.StabilityDuration=parseFloat(data[4][4]);
     frequencyConversionData.FullnessCoefficientModel.FrequencyReduction = {};
     if(isNumber(parseFloat(data[5][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyReduction.FullnessCoefficientLimit=parseFloat(data[5][4]);
     if(isNumber(parseFloat(data[6][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyReduction.FrequencyLowerLimit=parseFloat(data[6][4]);
     if(isNumber(parseFloat(data[7][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyReduction.StepSize=parseFloat(data[7][4]);
     if(isNumber(parseFloat(data[8][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyReduction.StabilityDuration=parseFloat(data[8][4]);
     frequencyConversionData.RodStressModel = { Enable: data[9][4] ? 1 : 0 };
     if(isNumber(parseFloat(data[10][4]))) frequencyConversionData.RodStressModel.MaxRodStressRatio=parseFloat(data[10][4]);
     if(isNumber(parseFloat(data[11][4]))) frequencyConversionData.RodStressModel.RodStressRangeRatio=parseFloat(data[11][4]);
     if(isNumber(parseFloat(data[12][4]))) frequencyConversionData.RodStressModel.FrequencyLowerLimit=parseFloat(data[12][4]);
     if(isNumber(parseFloat(data[13][4]))) frequencyConversionData.RodStressModel.StepSize=parseFloat(data[13][4]);
     frequencyConversionData.FSDiagramWorkTypeEnable = {};
     var codes = [1201,1202,1203,1204,1205,1206,1207,1208,1209,1210,1212,1213,1214,1215,1216,1217,1218,1219,1220,1221,1222,1223,1224,1225,1226,1227,1230,1232];
     for(var k=0; k<codes.length; k++){
         var rowIndex = 14 + k;
         if(rowIndex < data.length) frequencyConversionData.FSDiagramWorkTypeEnable['FSDiagramWorkType'+codes[k]] = data[rowIndex][4] ? 1 : 0;
     }
 }
 var confirmInfo = _loginUserLanguageResource.intelligentFrequencyConversion + '</br>' + _loginUserLanguageResource.deviceName + ":<font color=red>" + _dmCurrentDeviceName + "</font></br>" + _loginUserLanguageResource.confirmDownlink;
 mini.confirm(confirmInfo, _loginUserLanguageResource.tip, function (action) {
     if (action == "ok") {
         mini.mask({ el: 'intelligentFrequencyConversionPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.commandSending + '...' });
         $.ajax({
             url: context + '/wellInformationManagerController/deviceIntelligentFrequencyConversionDataDownlink',
             type: "POST", data: { deviceId: deviceId, deviceName: _dmCurrentDeviceName, data: JSON.stringify(frequencyConversionData) }, dataType: 'json',
             success: function (result) {
                 mini.unmask('intelligentFrequencyConversionPanel');
                 if (result.flag === false) mini.alert("<font color=red>" + _loginUserLanguageResource.sessionExpired + "</font>", _loginUserLanguageResource.tip, function () { window.location.href = context + "/login"; });
                 else if (result.flag === true && result.error === false) mini.alert("<font color=red>" + result.msg + "</font>", _loginUserLanguageResource.tip);
                 else if (result.flag === true && result.error === true) {
                     var plugin = deviceIntelligentFrequencyConversionHandsontableHelper.hot.getPlugin('hiddenColumns');
                     plugin.showColumns([6]); plugin.hideColumns([7]); deviceIntelligentFrequencyConversionHandsontableHelper.hot.render();
                     var codeColumnValue = deviceIntelligentFrequencyConversionHandsontableHelper.hot.getDataAtProp('itemCode');
                     for (var i = 0; i < codeColumnValue.length; i++) {
                         for (var j = 0; j < result.downStatusList.length; j++) {
                             if (codeColumnValue[i] && result.downStatusList[j].key.toUpperCase() == codeColumnValue[i].toUpperCase()) {
                                 deviceIntelligentFrequencyConversionHandsontableHelper.hot.setDataAtRowProp(i, 'downlinkStatus', result.downStatusList[j].status); break;
                             }
                         }
                     }
                 }
             },
             error: function () { mini.unmask('intelligentFrequencyConversionPanel'); mini.alert(_loginUserLanguageResource.exceptionThrow, _loginUserLanguageResource.tip); }
         });
     }
 });
}

function onDmIntelligentFrequencyConversionDataUplink() {
 var deviceId = _dmSelectedDeviceId;
 if (!deviceId || deviceId <= 0) { mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip); return; }
 mini.mask({ el: 'intelligentFrequencyConversionPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.commandSending + '...' });
 $.ajax({
     url: context + '/wellInformationManagerController/deviceIntelligentFrequencyConversionDataUplink',
     type: "POST", data: { deviceId: deviceId }, dataType: 'json',
     success: function (result) {
         mini.unmask('intelligentFrequencyConversionPanel');
         if (result.flag === false) mini.alert("<font color=red>" + _loginUserLanguageResource.sessionExpired + "</font>", _loginUserLanguageResource.tip, function () { window.location.href = context + "/login"; });
         else if (result.flag === true && result.error === false) mini.alert("<font color=red>" + result.msg + "</font>", _loginUserLanguageResource.tip);
         else if (result.flag === true && result.error === true) {
             var plugin = deviceIntelligentFrequencyConversionHandsontableHelper.hot.getPlugin('hiddenColumns');
             plugin.showColumns([7]); plugin.hideColumns([6]);
             
             var codeColumnValue = deviceIntelligentFrequencyConversionHandsontableHelper.hot.getDataAtProp('itemCode');
             for(var i=0;i<codeColumnValue.length;i++){
                 for(var j=0;j<result.downStatusList.length;j++){
                     if(result.downStatusList[j].key.toUpperCase()==codeColumnValue[i].toUpperCase()){
                         var cellProperties = deviceIntelligentFrequencyConversionHandsontableHelper.hot.getCellMeta(i, 4);
                         var uplinkStatusCellMeta = deviceIntelligentFrequencyConversionHandsontableHelper.hot.getCellMeta(i, 7); 
                         if(cellProperties.type === 'checkbox' && result.downStatusList[j].status != _loginUserLanguageResource.noUplink && result.downStatusList[j].status != _loginUserLanguageResource.uplinkFailed){
                             if(uplinkStatusCellMeta.type != 'checkbox') deviceIntelligentFrequencyConversionHandsontableHelper.hot.setCellMeta(i, 7, 'type', 'checkbox');
                         } else {
                             if(uplinkStatusCellMeta.type != 'text') deviceIntelligentFrequencyConversionHandsontableHelper.hot.setCellMeta(i, 7, 'type', 'text');
                         }
                         break;
                     }
                 }
             }
             deviceIntelligentFrequencyConversionHandsontableHelper.hot.render();
             
             for(var i=0;i<codeColumnValue.length;i++){
                 for(var j=0;j<result.downStatusList.length;j++){
                     if(result.downStatusList[j].key.toUpperCase()==codeColumnValue[i].toUpperCase()){
                         var newStatus = result.downStatusList[j].status;
                         var targetType = deviceIntelligentFrequencyConversionHandsontableHelper.hot.getCellMeta(i, 7).type;
                         if (targetType === 'checkbox' && typeof newStatus !== 'boolean') {
                             newStatus = (newStatus === true || newStatus === 'true' || newStatus == 1);
                         }
                         deviceIntelligentFrequencyConversionHandsontableHelper.hot.setDataAtRowProp(i, 'uplinkStatus', newStatus);
                         break;
                     }
                 }
             }
             deviceIntelligentFrequencyConversionHandsontableHelper.hot.render();
         }
     },
     error: function () { mini.unmask('intelligentFrequencyConversionPanel'); mini.alert(_loginUserLanguageResource.exceptionThrow, _loginUserLanguageResource.tip); }
 });
}
//================================================================
//联锁保护 上行/下行
//================================================================
function onDmInterlockProtectionDataDownlink() {
 var deviceId = _dmSelectedDeviceId;
 if (!deviceId || deviceId <= 0) { mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip); return; }
 var interlockProtectionData = {};
 if (deviceInterlockProtectionHandsontableHelper != null && deviceInterlockProtectionHandsontableHelper.hot != undefined) {
     var data = deviceInterlockProtectionHandsontableHelper.hot.getData();
     interlockProtectionData.Enable = data[0][3] ? 1 : 0;
     interlockProtectionData.FSDiagramWorkTypeEnable = {};
     var codes = [1201,1202,1203,1204,1205,1206,1207,1208,1209,1210,1212,1213,1214,1215,1216,1217,1218,1219,1220,1221,1222,1223,1224,1225,1226,1227,1230,1232];
     for(var k=0; k<codes.length; k++){
         var rowIndex = 1 + k;
         if(rowIndex < data.length) interlockProtectionData.FSDiagramWorkTypeEnable['FSDiagramWorkType'+codes[k]] = data[rowIndex][3] ? 1 : 0;
     }
 }
 var confirmInfo = _loginUserLanguageResource.interlockProtection + '</br>' + _loginUserLanguageResource.deviceName + ":<font color=red>" + _dmCurrentDeviceName + "</font></br>" + _loginUserLanguageResource.confirmDownlink;
 mini.confirm(confirmInfo, _loginUserLanguageResource.tip, function (action) {
     if (action == "ok") {
         mini.mask({ el: 'interlockProtectionPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.commandSending + '...' });
         $.ajax({
             url: context + '/wellInformationManagerController/deviceInterlockProtectionDataDownlink',
             type: "POST", data: { deviceId: deviceId, deviceName: _dmCurrentDeviceName, data: JSON.stringify(interlockProtectionData) }, dataType: 'json',
             success: function (result) {
                 mini.unmask('interlockProtectionPanel');
                 if (result.flag === false) mini.alert("<font color=red>" + _loginUserLanguageResource.sessionExpired + "</font>", _loginUserLanguageResource.tip, function () { window.location.href = context + "/login"; });
                 else if (result.flag === true && result.error === false) mini.alert("<font color=red>" + result.msg + "</font>", _loginUserLanguageResource.tip);
                 else if (result.flag === true && result.error === true) {
                     var plugin = deviceInterlockProtectionHandsontableHelper.hot.getPlugin('hiddenColumns');
                     plugin.showColumns([5]); plugin.hideColumns([6]); deviceInterlockProtectionHandsontableHelper.hot.render();
                     var codeColumnValue = deviceInterlockProtectionHandsontableHelper.hot.getDataAtProp('itemCode');
                     for (var i = 0; i < codeColumnValue.length; i++) {
                         for (var j = 0; j < result.downStatusList.length; j++) {
                             if (codeColumnValue[i] && result.downStatusList[j].key.toUpperCase() == codeColumnValue[i].toUpperCase()) {
                                 deviceInterlockProtectionHandsontableHelper.hot.setDataAtRowProp(i, 'downlinkStatus', result.downStatusList[j].status); break;
                             }
                         }
                     }
                 }
             },
             error: function () { mini.unmask('interlockProtectionPanel'); mini.alert(_loginUserLanguageResource.exceptionThrow, _loginUserLanguageResource.tip); }
         });
     }
 });
}

function onDmInterlockProtectionDataUplink() {
 var deviceId = _dmSelectedDeviceId;
 if (!deviceId || deviceId <= 0) { mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip); return; }
 mini.mask({ el: 'interlockProtectionPanel', cls: 'mini-mask-loading', html: _loginUserLanguageResource.commandSending + '...' });
 $.ajax({
     url: context + '/wellInformationManagerController/deviceInterlockProtectionDataUplink',
     type: "POST", data: { deviceId: deviceId }, dataType: 'json',
     success: function (result) {
         mini.unmask('interlockProtectionPanel');
         if (result.flag === false) mini.alert("<font color=red>" + _loginUserLanguageResource.sessionExpired + "</font>", _loginUserLanguageResource.tip, function () { window.location.href = context + "/login"; });
         else if (result.flag === true && result.error === false) mini.alert("<font color=red>" + result.msg + "</font>", _loginUserLanguageResource.tip);
         else if (result.flag === true && result.error === true) {
             var plugin = deviceInterlockProtectionHandsontableHelper.hot.getPlugin('hiddenColumns');
             plugin.showColumns([6]); plugin.hideColumns([5]);
             
             var codeColumnValue = deviceInterlockProtectionHandsontableHelper.hot.getDataAtProp('itemCode');
             for(var i=0;i<codeColumnValue.length;i++){
                 for(var j=0;j<result.downStatusList.length;j++){
                     if(result.downStatusList[j].key.toUpperCase()==codeColumnValue[i].toUpperCase()){
                         var cellProperties = deviceInterlockProtectionHandsontableHelper.hot.getCellMeta(i, 3);
                         var uplinkStatusCellMeta = deviceInterlockProtectionHandsontableHelper.hot.getCellMeta(i, 6); 
                         if(cellProperties.type === 'checkbox' && result.downStatusList[j].status != _loginUserLanguageResource.noUplink && result.downStatusList[j].status != _loginUserLanguageResource.uplinkFailed){
                             if(uplinkStatusCellMeta.type != 'checkbox') deviceInterlockProtectionHandsontableHelper.hot.setCellMeta(i, 6, 'type', 'checkbox');
                         } else {
                             if(uplinkStatusCellMeta.type != 'text') deviceInterlockProtectionHandsontableHelper.hot.setCellMeta(i, 6, 'type', 'text');
                         }
                         break;
                     }
                 }
             }
             deviceInterlockProtectionHandsontableHelper.hot.render();
             
             for(var i=0;i<codeColumnValue.length;i++){
                 for(var j=0;j<result.downStatusList.length;j++){
                     if(result.downStatusList[j].key.toUpperCase()==codeColumnValue[i].toUpperCase()){
                         var newStatus = result.downStatusList[j].status;
                         var targetType = deviceInterlockProtectionHandsontableHelper.hot.getCellMeta(i, 6).type;
                         if (targetType === 'checkbox' && typeof newStatus !== 'boolean') {
                             newStatus = (newStatus === true || newStatus === 'true' || newStatus == 1);
                         }
                         deviceInterlockProtectionHandsontableHelper.hot.setDataAtRowProp(i, 'uplinkStatus', newStatus);
                         break;
                     }
                 }
             }
             deviceInterlockProtectionHandsontableHelper.hot.render();
         }
     },
     error: function () { mini.unmask('interlockProtectionPanel'); mini.alert(_loginUserLanguageResource.exceptionThrow, _loginUserLanguageResource.tip); }
 });
}

//================================================================
//加载附加信息标签数据
//================================================================
function loadAdditionalInfoByTabName(tabName) {
	 if (!tabName) return;

	 var deviceId   = _dmSelectedDeviceId;
	 var deviceName = _dmCurrentDeviceName;
	 var appScen    = _dmCurrentApplicationScenarios;
	 var calcType   = _dmCurrentCalculateType;

	 if (tabName === 'calculateData') {
	     var calculateDataTabs = mini.get('deviceCalculateDataTabs');
	     if (calculateDataTabs) {
	         var activeSubTab = calculateDataTabs.getActiveTab();
	         if (activeSubTab) {
	             loadCalculateDataByTabName(activeSubTab.name);
	         }
	     }
	 } else if (tabName === 'additionalInfo') {
	     CreateAndLoadDeviceAdditionalInfoTable(deviceId, deviceName, true);
	 } else if (tabName === 'auxiliaryDevice') {
	     CreateAndLoadDeviceAuxiliaryDeviceInfoTable(deviceId, deviceName, calcType, true);
	 } else if (tabName === 'videoInfo') {
	     CreateAndLoadVideoInfoTable(deviceId, deviceName, true);
	 } else if (tabName === 'fsDiagramConstruction') {
	     CreateAndLoadFSDiagramConstructionDataTable(deviceId, deviceName, appScen, true);
	 } else if (tabName === 'systemParameter') {
	     CreateAndLoadDeviceSystemParameterTable(deviceId, deviceName, appScen, true);
	 } else if (tabName === 'intelligentFrequencyConversion') {
	     CreateAndLoadDeviceIntelligentFrequencyConversionTable(deviceId, deviceName, appScen, true);
	 } else if (tabName === 'interlockProtection') {
	     CreateAndLoadDeviceInterlockProtectionTable(deviceId, deviceName, appScen, true);
	 }
}

//================================================================
//加载生产数据配置子标签数据
//================================================================
function loadCalculateDataByTabName(tabName) {
 if (!tabName) return;

 var deviceId   = _dmSelectedDeviceId;
 var deviceName = _dmCurrentDeviceName;
 var appScen    = _dmCurrentApplicationScenarios;

 if (tabName === 'wellboreData') {
     // ★ 井筒数据：根据 _dmCurrentCalculateType 决定表结构
     CreateAndLoadProductionDataTable(deviceId, deviceName, appScen, true);
 } else if (tabName === 'pumpingUnitData') {
     CreateAndLoadPumpingInfoTable(deviceId, deviceName, appScen, true);
     CreatePumpingUnitDetailedInformationTable();
     CreateAndLoadDevicePumpingUnitPTFTable();
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
                            var applicationScenarios = getApplicationScenariosValue(helper.hot.getDataAtRowProp(startRow, 'applicationScenariosName'));

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
        	var deviceInfoHandsontableData = helper.hot.getData();
            if (!deviceInfoHandsontableData || deviceInfoHandsontableData.length === 0) {
                mini.alert(_loginUserLanguageResource.noDataChange, _loginUserLanguageResource.tip);
                return;
            }

            var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
            var deviceType = getCurrentDeviceType();

            // 收集新增
            helper.insertExpressCount();

            // 当前选中行
            var selectRow = _dmDeviceSelectRow;
            if (selectRow === '' || selectRow == undefined) selectRow = 0;

            var deviceId = helper.hot.getDataAtRowProp(selectRow, 'id');
            var appScenName = helper.hot.getDataAtRowProp(selectRow, 'applicationScenariosName');
            var applicationScenarios = getApplicationScenariosValue(appScenName);

            // ★ 获取当前附加信息Tab类型
            var additionalInformationType = getCurrentAdditionalInfoType();

            // ★ 收集附加信息数据 (替换原有的占位代码)
            var deviceAdditionalInformationData = collectAdditionalInformationData(
                additionalInformationType, 
                deviceId, 
                applicationScenarios
            );

            // 前端重复校验
            var dupList = helper.getDuplicateRowList();
            if (dupList.length > 0) {
                mini.alert(_loginUserLanguageResource.duplicateInfo, _loginUserLanguageResource.tip);
                return;
            }

            // 是否没有变化
            /*
            var hasChange = (helper.updatelist.length > 0
                || helper.insertlist.length > 0
                || helper.delidslist.length > 0);
            if (!hasChange) {
                mini.alert(_loginUserLanguageResource.noDataChange, _loginUserLanguageResource.tip);
                return;
            }*/

            var maskEl = 'DeviceTablePanel_id';
            mini.mask({ el: maskEl, cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });

            $.ajax({
                url: context + '/wellInformationManagerController/saveWellHandsontableData',
                type: 'POST',
                data: {
                    deviceId: deviceId,
                    data: JSON.stringify(helper.AllData),
                    deviceAdditionalInformationData: JSON.stringify(deviceAdditionalInformationData), // ★ 传递附加信息
                    orgId: leftOrgId,
                    deviceType: deviceType
                },
                dataType: 'json',
                success: function (rdata) {
                    mini.unmask(maskEl);

                    if (rdata && rdata.success) {
                        var saveInfo = _loginUserLanguageResource.savedSuccessfully;
                        if (rdata.collisionCount > 0) {
                            saveInfo = _loginUserLanguageResource.savedSuccessfully
                                + ':' + rdata.successCount + ','
                                + _loginUserLanguageResource.saveFailed
                                + ':<font color="red">' + rdata.collisionCount + '</font>';
                            for (var i = 0; i < rdata.list.length; i++) {
                                saveInfo += '<br/><font color="red"> ' + rdata.list[i] + '</font>';
                            }
                        }
                        mini.alert(saveInfo, _loginUserLanguageResource.tip);

                        if (rdata.successCount > 0) {
                            helper.clearContainer();
                            CreateAndLoadDeviceInfoTable(true);
                        }
                    } else {
                        mini.alert('<font color=red>'
                            + _loginUserLanguageResource.saveFailed
                            + '</font>', _loginUserLanguageResource.tip);
                    }
                },
                error: function () {
                    mini.unmask(maskEl);
                    mini.alert(_loginUserLanguageResource.requestFailed, _loginUserLanguageResource.tip);
                    helper.clearContainer();
                }
            });
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

//================================================================
//附加信息表
//================================================================
function CreateAndLoadDeviceAdditionalInfoTable(deviceId, deviceName, isNew) {
	if (deviceAdditionalInfoHandsontableHelper != null) {
	     if (deviceAdditionalInfoHandsontableHelper.hot != undefined) {
	         deviceAdditionalInfoHandsontableHelper.hot.destroy();
	     }
	     deviceAdditionalInfoHandsontableHelper = null;
	 }
	
	
	 var maskEl = 'DeviceAdditionalInfoTableDiv_id';
	 mini.mask({
	        el: maskEl,
	        cls: 'mini-mask-loading',
	        html: _loginUserLanguageResource.loadingData
	    });
	 $.ajax({
	     method: 'POST',
	     url: context + '/wellInformationManagerController/getDeviceAdditionalInfo',
	     data: {
	         deviceId: deviceId,
	         deviceType: getCurrentDeviceType()
	     },
	     success: function (result) {
	    	 mini.unmask(maskEl);
	         var R = _loginUserLanguageResource;
	         if (deviceAdditionalInfoHandsontableHelper == null || deviceAdditionalInfoHandsontableHelper.hot == undefined) {
	             deviceAdditionalInfoHandsontableHelper = DeviceAdditionalInfoHandsontableHelper.createNew("DeviceAdditionalInfoTableDiv_id");
	             var colHeaders = [R.idx, R.variable, R.value, R.unit, R.deviceOverview, R.columnSort];
	             var columns = [
	                 { data: 'id' },
	                 { data: 'itemName' },
	                 { data: 'itemValue' },
	                 { data: 'itemUnit' },
	                 { data: 'overview', type: 'checkbox' },
	                 {
	                     data: 'overviewSort', type: 'text', allowInvalid: true,
	                     validator: function (val, callback) {
	                         return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, deviceAdditionalInfoHandsontableHelper);
	                     }
	                 }
	             ];
	             deviceAdditionalInfoHandsontableHelper.colHeaders = colHeaders;
	             deviceAdditionalInfoHandsontableHelper.columns = columns;

	             if (result.totalRoot.length == 0) {
	                 var emptyArr = [];
	                 for (var i = 0; i < 20; i++) emptyArr.push({});
	                 deviceAdditionalInfoHandsontableHelper.createTable(emptyArr);
	             } else {
	                 deviceAdditionalInfoHandsontableHelper.createTable(result.totalRoot);
	             }
	         } else {
	             if (result.totalRoot.length == 0) {
	                 var emptyArr = [];
	                 for (var i = 0; i < 20; i++) emptyArr.push({});
	                 deviceAdditionalInfoHandsontableHelper.hot.loadData(emptyArr);
	             } else {
	                 deviceAdditionalInfoHandsontableHelper.hot.loadData(result.totalRoot);
	             }
	         }
	     },
	     failure: function () {
	    	 mini.unmask(maskEl);
	         mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
	     }
	 });
}

var DeviceAdditionalInfoHandsontableHelper = {
		createNew: function (divid) {
		     var helper = {};
		     helper.hot = '';
		     helper.divid = divid;
		     helper.colHeaders = [];
		     helper.columns = [];

		     helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
		         Handsontable.renderers.TextRenderer.apply(this, arguments);
		         td.style.whiteSpace = 'nowrap';
		         td.style.overflow = 'hidden';
		         td.style.textOverflow = 'ellipsis';
		     };

		     helper.createTable = function (data) {
		         $('#' + helper.divid).empty();
		         var hotElement = document.querySelector('#' + helper.divid);
		         helper.hot = new Handsontable(hotElement, {
		             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
		             theme: 'ht-theme-classic',
		             data: data,
		             hiddenColumns: { columns: [0, 4, 5], indicators: false },
		             columns: helper.columns,
		             stretchH: 'all',
		             rowHeaders: true,
		             colHeaders: helper.colHeaders,
		             columnSorting: true,
		             copyable: true,
		             copyPaste: true,
		             contextMenu: {
		                 items: {
		                     "row_above": { name: _loginUserLanguageResource.contextMenu_insertRowAbove },
		                     "row_below": { name: _loginUserLanguageResource.contextMenu_insertRowBelow },
		                     "col_left":  { name: _loginUserLanguageResource.contextMenu_insertColumnLeft },
		                     "col_right": { name: _loginUserLanguageResource.contextMenu_insertColumnRight },
		                     "remove_row": { name: _loginUserLanguageResource.contextMenu_removeRow },
		                     "remove_col": { name: _loginUserLanguageResource.contextMenu_removeColumn },
		                     "merge_cell": { name: _loginUserLanguageResource.contextMenu_mergeCell },
		                     "copy": { name: _loginUserLanguageResource.contextMenu_copy },
		                     "cut":  { name: _loginUserLanguageResource.contextMenu_cut }
		                 }
		             },
		             sortIndicator: true,
		             manualColumnResize: true,
		             manualRowResize: true,
		             filters: true,
		             renderAllRows: true,
		             search: true,
		             cells: function (row, col, prop) {
		                 var cellProperties = {};
		                 if (parseInt(_dmModuleRight.editFlag) != 1) {
		                     cellProperties.editor = false;
		                 }
		                 if (prop !== 'overview') {
		                     cellProperties.renderer = helper.addCellStyle;
		                 }
		                 return cellProperties;
		             },
		             afterOnCellMouseOver: function (event, coords, TD) {
		                 if (coords.col >= 0 && coords.row >= 0 && helper.hot) {
		                     var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
		                     if (isNotVal(rawValue)) TD.title = String(rawValue);
		                 }
		             }
		         });
		     };
		     return helper;
		 }
};

//================================================================
//辅件设备表
//互斥规则：specificType == 1（抽油机）只能选中一个
//================================================================
function CreateAndLoadDeviceAuxiliaryDeviceInfoTable(deviceId, deviceName, calculateType, isNew) {
 if (deviceAuxiliaryDeviceInfoHandsontableHelper != null) {
     if (deviceAuxiliaryDeviceInfoHandsontableHelper.hot != undefined) {
         deviceAuxiliaryDeviceInfoHandsontableHelper.hot.destroy();
     }
     deviceAuxiliaryDeviceInfoHandsontableHelper = null;
 }

 var maskEl = 'DeviceAuxiliaryDeviceTableDiv_id';
 mini.mask({
     el: maskEl,
     cls: 'mini-mask-loading',
     html: _loginUserLanguageResource.loadingData
 });

 $.ajax({
     method: 'POST',
     url: context + '/wellInformationManagerController/getAuxiliaryDevice',
     data: {
         deviceId: deviceId,
         deviceType: getCurrentFirstDeviceType(),
         calculateType: calculateType
     },
     success: function (result) {
    	 mini.unmask(maskEl);
         var R = _loginUserLanguageResource;

         if (deviceAuxiliaryDeviceInfoHandsontableHelper == null || deviceAuxiliaryDeviceInfoHandsontableHelper.hot == undefined) {
             deviceAuxiliaryDeviceInfoHandsontableHelper = DeviceAuxiliaryDeviceInfoHandsontableHelper.createNew("DeviceAuxiliaryDeviceTableDiv_id");
             var colHeaders = ['', R.idx, R.deviceName, R.manufacturer, R.model, R.type, R.type, 'ID'];
             var columns = [
                 { data: 'checked', type: 'checkbox' },
                 { data: 'id' },
                 { data: 'name' },
                 { data: 'manufacturer' },
                 { data: 'model' },
                 { data: 'specificTypeName' },
                 { data: 'specificType' },
                 { data: 'realId' }
             ];
             deviceAuxiliaryDeviceInfoHandsontableHelper.colHeaders = colHeaders;
             deviceAuxiliaryDeviceInfoHandsontableHelper.columns = columns;
             deviceAuxiliaryDeviceInfoHandsontableHelper.createTable(result.totalRoot);
         } else {
             deviceAuxiliaryDeviceInfoHandsontableHelper.hot.loadData(result.totalRoot);
         }
     },
     failure: function () {
    	 mini.unmask(maskEl);
         mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
     }
 });
}

var DeviceAuxiliaryDeviceInfoHandsontableHelper = {
 createNew: function (divid) {
     var helper = {};
     helper.hot = '';
     helper.divid = divid;
     helper.validresult = true;
     helper.colHeaders = [];
     helper.columns = [];
     helper.AllData = [];

     helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.createTable = function (data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             hiddenColumns: { columns: [6, 7], indicators: false },
             colWidths: [20, 30, 60, 60, 60, 60],
             columns: helper.columns,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: true,
             sortIndicator: true,
             manualColumnResize: true,
             manualRowResize: true,
             filters: true,
             renderAllRows: true,
             search: true,
             cells: function (row, col, prop) {
                 var cellProperties = {};
                 var visualColIndex = this.instance.toVisualColumn(col);
                 if (parseInt(_dmModuleRight.editFlag) == 1) {
                     if (visualColIndex > 0) {
                         cellProperties.editor = false;
                     }
                 } else {
                     cellProperties.editor = false;
                 }
                 if (visualColIndex > 0) {
                     cellProperties.renderer = helper.addCellStyle;
                 }
                 return cellProperties;
             },
             beforeChange: function (changes, source) {
                 if (!changes) return true;
                 if (parseInt(_dmModuleRight.editFlag) === 0) {
                     for (var i = 0; i < changes.length; i++) {
                         if (changes[i][1] === 'checked') return false;
                     }
                 }
                 return true;
             },
             // ★ 互斥逻辑：仅选中一个抽油机
             afterSelectionEnd: function (row, column, row2, column2, preventScrolling, selectionLayerLevel) {
                 var editFlag = parseInt(_dmModuleRight.editFlag);
                 if (editFlag == 1) {
                     if (row == row2 && column == column2 && column == 0) {
                         var selectedRow = row;
                         var checkboxColData = helper.hot.getDataAtCol(0);
                         var specificTypeData = helper.hot.getDataAtCol(6);
                         var rowdata = helper.hot.getDataAtRow(selectedRow);

                         // 若当前是抽油机(specificType==1)，清除其它抽油机选中
                         if (rowdata[6] == 1) {
                             for (var i = 0; i < checkboxColData.length; i++) {
                                 if (i != selectedRow && checkboxColData[i] && specificTypeData[i] == 1) {
                                     helper.hot.setDataAtCell(i, 0, false);
                                 }
                             }
                         }

                         // 切换当前行的选中状态
                         if (rowdata[0]) {
                             helper.hot.setDataAtCell(selectedRow, 0, false);
                         } else {
                             helper.hot.setDataAtCell(selectedRow, 0, true);
                         }
                     }
                 }
             },
             afterOnCellMouseOver: function (event, coords, TD) {
                 if (coords.col > 0 && coords.row >= 0 && helper.hot) {
                     var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                     if (isNotVal(rawValue)) TD.title = String(rawValue);
                 }
             }
         });
     };
     return helper;
 }
};

//================================================================
//井筒数据表（生产数据配置 - 井筒数据）
//★ deviceCalculateDataType 决定表结构：
//  1 = 功图计算（抽油机）：45 行，含 barrelType / pumpGrade / rodString / manualIntervention / FESDiagram
//  2 = 转速计产（螺杆泵）：41 行，含 barrelLength / QPR 等
//  0 = 无（不加载）
//================================================================
function CreateAndLoadProductionDataTable(deviceId, deviceName, applicationScenarios, isNew) {
 var deviceCalculateDataType = _dmCurrentCalculateType;

 if (productionHandsontableHelper != null) {
     if (productionHandsontableHelper.hot != undefined) {
         productionHandsontableHelper.hot.destroy();
     }
     productionHandsontableHelper = null;
 }

 if (deviceCalculateDataType != 0) {
	 var maskEl = 'wellboreDataPanel';
	    mini.mask({
	        el: maskEl,
	        cls: 'mini-mask-loading',
	        html: _loginUserLanguageResource.loadingData
	    });

     $.ajax({
         method: 'POST',
         url: context + '/wellInformationManagerController/getDeviceProductionDataInfo',
         data: {
             deviceId: deviceId,
             deviceCalculateDataType: deviceCalculateDataType,
             deviceType: getCurrentDeviceType()
         },
         success: function (result) {
        	 mini.unmask(maskEl);
             var R = _loginUserLanguageResource;

             if (productionHandsontableHelper == null || productionHandsontableHelper.hot == undefined) {
                 productionHandsontableHelper = ProductionHandsontableHelper.createNew("DeviceProductionDataTableDiv_id");
                 productionHandsontableHelper.resultList = result.resultNameList;
                 productionHandsontableHelper.FESdiagramSrcList = result.FESdiagramSrcList;

                 var colHeaders = [R.idx, R.variable, R.value, '', R.downlinkStatus, R.uplinkStatus];
                 var columns = [
                     { data: 'id' },
                     { data: 'itemName' },
                     {
                         data: 'itemValue', type: 'text', allowInvalid: true,
                         validator: function (val, callback) {
                             return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, productionHandsontableHelper);
                         }
                     },
                     { data: 'itemCode' },
                     { data: 'downlinkStatus' },
                     { data: 'uplinkStatus' }
                 ];
                 productionHandsontableHelper.colHeaders = colHeaders;
                 productionHandsontableHelper.columns = columns;
                 productionHandsontableHelper.deviceCalculateDataType = deviceCalculateDataType;

                 if (result.totalRoot.length == 0) {
                     // ★ 根据类型给空表不同的行数
                     var emptyArr = [];
                     var len = (deviceCalculateDataType == 1) ? 45 : 41;
                     for (var i = 0; i < len; i++) emptyArr.push({});
                     productionHandsontableHelper.createTable(emptyArr);
                 } else {
                     productionHandsontableHelper.pumpGrade = result.totalRoot[13].itemValue;
                     productionHandsontableHelper.createTable(result.totalRoot);
                 }
             } else {
                 productionHandsontableHelper.deviceCalculateDataType = deviceCalculateDataType;
                 productionHandsontableHelper.resultList = result.resultNameList;
                 productionHandsontableHelper.FESdiagramSrcList = result.FESdiagramSrcList;

                 if (result.totalRoot.length == 0) {
                     var emptyArr = [];
                     var len = (deviceCalculateDataType == 1) ? 45 : 41;
                     for (var i = 0; i < len; i++) emptyArr.push({});
                     productionHandsontableHelper.hot.loadData(emptyArr);
                 } else {
                     productionHandsontableHelper.hot.loadData(result.totalRoot);
                 }
             }

             // 根据 applicationScenarios 隐藏部分行并调整行标题
             var hiddenRows = [0, 3, 9, 10];
             var plugin = productionHandsontableHelper.hot.getPlugin('hiddenRows');
             if (applicationScenarios == 0) {
                 plugin.hideRows(hiddenRows);
                 productionHandsontableHelper.hot.setDataAtCell(4, 1, R.coalSeamDepth + '(m)');
                 productionHandsontableHelper.hot.setDataAtCell(5, 1, R.coalSeamTemperature + '(℃)');
                 productionHandsontableHelper.hot.setDataAtCell(6, 1, R.tubingPressure_cbm + '(MPa)');
             } else {
                 plugin.showRows(hiddenRows);
                 productionHandsontableHelper.hot.setDataAtCell(4, 1, R.reservoirDepth + '(m)');
                 productionHandsontableHelper.hot.setDataAtCell(5, 1, R.reservoirTemperature + '(℃)');
                 productionHandsontableHelper.hot.setDataAtCell(6, 1, R.tubingPressure + '(MPa)');
             }
             productionHandsontableHelper.hot.render();
         },
         failure: function () {
        	 mini.unmask(maskEl);
             mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
         }
     });
 }
}

var ProductionHandsontableHelper = {
 createNew: function (divid) {
     var helper = {};
     helper.hot = '';
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];
     helper.resultList = [];
     helper.FESdiagramSrcList = [];
     helper.pumpGrade = '';
     helper.deviceCalculateDataType = '';

     helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.backgroundColor = 'rgb(245, 245, 245)';
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.addUplinkStatusCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         var R = _loginUserLanguageResource;
         if (isNotVal(helper.hot)) {
             var itemValue = helper.hot.getDataAtRowProp(row, 'itemValue');
             if (isNotVal(value)) {
                 if (value === R.uplinkFailed || value === R.noUplink) {
                     td.style.backgroundColor = 'rgb(245, 245, 245)';
                 } else {
                     var same = false;
                     if (isNumber(itemValue) && isNumber(value)) {
                         same = parseFloat(itemValue) === parseFloat(value);
                     } else {
                         same = itemValue === value;
                     }
                     td.style.backgroundColor = same ? 'rgb(245, 245, 245)' : '#f09614';
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

     helper.createTable = function (data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             colWidths: [50, 100, 100],
             hiddenColumns: { columns: [0, 3, 4, 5], indicators: false, copyPasteEnabled: false },
             hiddenRows: { rows: [], indicators: false, copyPasteEnabled: false },
             columns: helper.columns,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: true,
             sortIndicator: true,
             manualColumnResize: true,
             manualRowResize: true,
             filters: true,
             renderAllRows: true,
             search: true,
             contextMenu: {
                 items: {
                     "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                     "cut":  { name: _loginUserLanguageResource.contextMenu_cut }
                 }
             },
             cells: function (row, col, prop) {
                 var cellProperties = {};
                 var visualColIndex = this.instance.toVisualColumn(col);
                 var visualRowIndex = this.instance.toVisualRow(row);
                 var editFlag = parseInt(_dmModuleRight.editFlag);
                 var calc = helper.deviceCalculateDataType;
                 var R = _loginUserLanguageResource;

                 if (editFlag == 1) {
                     if (visualColIndex != 2 && visualColIndex != 5) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addCellStyle;
                     } else if (visualColIndex == 5) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addUplinkStatusCellStyle;
                     }

                     // ---------- 功图计算类型 ----------
                     if (calc == 1) {
                         if (visualColIndex === 2 && visualRowIndex === 13) {
                             this.type = 'dropdown';
                             this.source = ['', R.barrelType_H, R.barrelType_L];
                             this.strict = true;
                             this.allowInvalid = false;
                         }
                         if (visualColIndex === 2 && visualRowIndex === 14) {
                             var barrelType = isNotVal(helper.hot)
                                 ? helper.hot.getDataAtCell(13, 2)
                                 : helper.pumpGrade;
                             var pumpGradeList = ['', '1', '2', '3', '4', '5'];
                             if (barrelType === R.barrelType_L) {
                                 pumpGradeList = ['', '1', '2', '3'];
                             }
                             this.type = 'dropdown';
                             this.source = pumpGradeList;
                             this.strict = true;
                             this.allowInvalid = false;
                         }
                         if (visualColIndex === 2 && (visualRowIndex === 20 || visualRowIndex === 25 || visualRowIndex === 30 || visualRowIndex === 35)) {
                             this.type = 'dropdown';
                             this.source = ['', R.rodStringTypeValue1, R.rodStringTypeValue2, R.rodStringTypeValue3];
                             this.strict = true;
                             this.allowInvalid = false;
                         }
                         if (visualColIndex === 2 && (visualRowIndex === 21 || visualRowIndex === 26 || visualRowIndex === 31 || visualRowIndex === 36)) {
                             this.type = 'dropdown';
                             this.source = ['', 'A', 'B', 'C', 'K', 'D', 'KD', 'HL', 'HY'];
                             this.strict = true;
                             this.allowInvalid = false;
                         }
                         if (visualColIndex === 2 && visualRowIndex === 40) {
                             this.type = 'dropdown';
                             this.source = helper.resultList;
                             this.strict = true;
                             this.allowInvalid = false;
                         }
                         if (visualColIndex === 2 && visualRowIndex === 44) {
                             this.type = 'dropdown';
                             this.source = helper.FESdiagramSrcList;
                             this.strict = true;
                             this.allowInvalid = false;
                         }
                     } else if (calc == 2) {
                         // ---------- 转速计产类型 ----------
                         if (visualColIndex === 2 && (visualRowIndex === 19 || visualRowIndex === 24 || visualRowIndex === 29 || visualRowIndex === 34)) {
                             this.type = 'dropdown';
                             this.source = ['', R.rodStringTypeValue1, R.rodStringTypeValue2, R.rodStringTypeValue3];
                             this.strict = true;
                             this.allowInvalid = false;
                         }
                         if (visualColIndex === 2 && (visualRowIndex === 20 || visualRowIndex === 25 || visualRowIndex === 30 || visualRowIndex === 35)) {
                             this.type = 'dropdown';
                             this.source = ['', 'A', 'B', 'C', 'K', 'D', 'KD', 'HL', 'HY'];
                             this.strict = true;
                             this.allowInvalid = false;
                         }
                     }
                 } else {
                     cellProperties.editor = false;
                     cellProperties.renderer = (visualColIndex != 2)
                         ? helper.addCellStyle
                         : helper.addCellStyle;
                 }
                 return cellProperties;
             },
             afterOnCellMouseOver: function (event, coords, TD) {
                 if (coords.col >= 0 && coords.row >= 0 && helper.hot) {
                     var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                     if (isNotVal(rawValue)) TD.title = String(rawValue);
                 }
             }
         });
     };
     return helper;
 }
};

//================================================================
//抽油机数据表
//================================================================
function CreateAndLoadPumpingInfoTable(deviceId, deviceName, applicationScenarios, isNew) {
 if (pumpingInfoHandsontableHelper != null) {
     if (pumpingInfoHandsontableHelper.hot != undefined) {
         pumpingInfoHandsontableHelper.hot.destroy();
     }
     pumpingInfoHandsontableHelper = null;
 }
 
 var maskEl = 'pumpingUnitDataPanel';
 mini.mask({
     el: maskEl,
     cls: 'mini-mask-loading',
     html: _loginUserLanguageResource.loadingData
 });

 $.ajax({
     method: 'POST',
     async: false,
     url: context + '/wellInformationManagerController/getDevicePumpingInfo',
     data: {
         deviceId: deviceId,
         deviceType: getCurrentDeviceType(),
         auxiliaryDeviceType: getCurrentFirstDeviceType()
     },
     success: function (result) {
         mini.unmask(maskEl);
         var R = _loginUserLanguageResource;

         if (pumpingInfoHandsontableHelper == null || pumpingInfoHandsontableHelper.hot == undefined) {
             pumpingInfoHandsontableHelper = PumpingInfoHandsontableHelper.createNew("PumpingInfoTableDiv_id");
             pumpingInfoHandsontableHelper.strokeList = result.strokeArrStr;
             pumpingInfoHandsontableHelper.balanceWeightList = result.balanceInfoArrStr;
             pumpingInfoHandsontableHelper.pumpingUnitList = result.pumpingUnitList;

             var colHeaders = [R.idx, R.variable, R.value, '', R.downlinkStatus, R.uplinkStatus];
             var columns = [
                 { data: 'id' },
                 {
                     data: 'itemValue1', type: 'text', allowInvalid: true,
                     validator: function (val, callback) {
                         return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, pumpingInfoHandsontableHelper);
                     }
                 },
                 {
                     data: 'itemValue2', type: 'text', allowInvalid: true,
                     validator: function (val, callback) {
                         return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, pumpingInfoHandsontableHelper);
                     }
                 },
                 { data: 'itemCode' },
                 { data: 'downlinkStatus' },
                 { data: 'uplinkStatus' }
             ];
             pumpingInfoHandsontableHelper.colHeaders = colHeaders;
             pumpingInfoHandsontableHelper.columns = columns;

             if (result.totalRoot.length == 0) {
                 var emptyArr = [];
                 for (var i = 0; i < 9; i++) emptyArr.push({});
                 pumpingInfoHandsontableHelper.createTable(emptyArr);
             } else {
                 pumpingInfoHandsontableHelper.createTable(result.totalRoot);
             }
         } else {
             pumpingInfoHandsontableHelper.strokeList = result.strokeArrStr;
             pumpingInfoHandsontableHelper.balanceWeightList = result.balanceInfoArrStr;
             pumpingInfoHandsontableHelper.pumpingUnitList = result.pumpingUnitList;
             if (result.totalRoot.length == 0) {
                 var emptyArr = [];
                 for (var i = 0; i < 9; i++) emptyArr.push({});
                 pumpingInfoHandsontableHelper.hot.loadData(emptyArr);
             } else {
                 pumpingInfoHandsontableHelper.hot.loadData(result.totalRoot);
             }
         }
     },
     failure: function () {
         mini.unmask(maskEl);
         mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
     }
 });
}

var PumpingInfoHandsontableHelper = {
 createNew: function (divid) {
     var helper = {};
     helper.hot = '';
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];
     helper.strokeList = [];
     helper.balanceWeightList = [];
     helper.pumpingUnitList = [];

     helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.backgroundColor = 'rgb(245, 245, 245)';
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.addUplinkStatusCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         var R = _loginUserLanguageResource;
         if (isNotVal(helper.hot)) {
             if (row == 3 && prop.toUpperCase() === 'UPLINKSTATUS') {
                 var itemValue = helper.hot.getDataAtRowProp(row, 'itemValue2');
                 if (isNotVal(value)) {
                     if (value === R.uplinkFailed || value === R.noUplink) {
                         td.style.backgroundColor = 'rgb(245, 245, 245)';
                     } else {
                         var same = (isNumber(itemValue) && isNumber(value))
                             ? parseFloat(itemValue) === parseFloat(value)
                             : itemValue === value;
                         td.style.backgroundColor = same ? 'rgb(245, 245, 245)' : '#f09614';
                     }
                 } else {
                     td.innerHTML = '';
                     td.style.backgroundColor = 'rgb(245, 245, 245)';
                 }
             } else if (row >= 6 && row <= 13 && prop.toUpperCase() === 'UPLINKSTATUS') {
                 var itemValue1 = helper.hot.getDataAtRowProp(row, 'itemValue1');
                 var itemValue2 = helper.hot.getDataAtRowProp(row, 'itemValue2');
                 if ((isNotVal(itemValue1) || isNotVal(itemValue2)) && isNotVal(value)) {
                     if (value == R.uplinkFailed || value == R.uplinkFailed + '/' + R.uplinkFailed) {
                         td.style.backgroundColor = 'rgb(245, 245, 245)';
                     } else if (value == R.noUplink || value == R.noUplink + '/' + R.noUplink) {
                         td.style.backgroundColor = 'rgb(245, 245, 245)';
                     } else {
                         var arr = value.split('/');
                         var same = arr.length == 2
                             && parseFloat(itemValue1) === parseFloat(arr[0])
                             && parseFloat(itemValue2) === parseFloat(arr[1]);
                         td.style.backgroundColor = same ? 'rgb(245, 245, 245)' : '#f09614';
                     }
                 } else {
                     td.innerHTML = '';
                     td.style.backgroundColor = 'rgb(245, 245, 245)';
                 }
             } else {
                 td.style.backgroundColor = 'rgb(245, 245, 245)';
             }
         } else {
             td.style.backgroundColor = 'rgb(245, 245, 245)';
         }
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.createTable = function (data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             hiddenColumns: { columns: [0, 3, 4, 5], indicators: false, copyPasteEnabled: false },
             hiddenRows: { rows: [0], indicators: false, copyPasteEnabled: false },
             columns: helper.columns,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: true,
             sortIndicator: true,
             manualColumnResize: true,
             manualRowResize: true,
             filters: true,
             renderAllRows: true,
             search: true,
             nestedHeaders: [[
                 { label: _loginUserLanguageResource.idx },
                 { label: _loginUserLanguageResource.variable },
                 { label: _loginUserLanguageResource.value },
                 { label: '' },
                 { label: _loginUserLanguageResource.downlinkStatus },
                 { label: _loginUserLanguageResource.uplinkStatus }
             ]],
             mergeCells: [{ row: 4, col: 1, rowspan: 1, colspan: 2 }],
             contextMenu: {
                 items: {
                     "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                     "cut":  { name: _loginUserLanguageResource.contextMenu_cut }
                 }
             },
             cells: function (row, col, prop) {
                 var cellProperties = {};
                 var visualColIndex = this.instance.toVisualColumn(col);
                 var visualRowIndex = this.instance.toVisualRow(row);
                 var editFlag = parseInt(_dmModuleRight.editFlag);

                 if (editFlag == 1) {
                     if (visualColIndex >= 3 || visualColIndex == 0) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addCellStyle;
                     }
                     if ((visualColIndex <= 1 && visualRowIndex <= 2) || (visualRowIndex >= 4 && visualRowIndex <= 5) || (visualRowIndex == 3 && visualColIndex == 1)) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addCellStyle;
                     }
                     if ((visualRowIndex == 3 || (row >= 6 && row <= 13)) && prop.toUpperCase() === 'UPLINKSTATUS') {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addUplinkStatusCellStyle;
                     }
                     // 厂家
                     if (visualColIndex === 2 && visualRowIndex === 1) {
                         var listData = [];
                         for (var i = 0; i < helper.pumpingUnitList.length; i++) {
                             listData.push(helper.pumpingUnitList[i].manufacturer);
                         }
                         this.type = 'dropdown';
                         this.source = listData;
                         this.strict = true;
                         this.allowInvalid = false;
                     }
                     // 型号（依赖厂家）
                     if (visualColIndex === 2 && visualRowIndex === 2) {
                         var listData = [];
                         if (isNotVal(helper.hot)) {
                             var manufacturer = helper.hot.getDataAtRowProp(1, 'itemValue2');
                             if (isNotVal(manufacturer)) {
                                 for (var i = 0; i < helper.pumpingUnitList.length; i++) {
                                     if (manufacturer === helper.pumpingUnitList[i].manufacturer) {
                                         for (var j = 0; j < helper.pumpingUnitList[i].modelList.length; j++) {
                                             listData.push(helper.pumpingUnitList[i].modelList[j].model);
                                         }
                                         break;
                                     }
                                 }
                             }
                         }
                         this.type = 'dropdown';
                         this.source = listData;
                         this.strict = true;
                         this.allowInvalid = false;
                     }
                     // 冲程（依赖厂家+型号）
                     if (visualColIndex === 2 && visualRowIndex === 3) {
                         var listData = [];
                         if (isNotVal(helper.hot)) {
                             var manufacturer = helper.hot.getDataAtRowProp(1, 'itemValue2');
                             var model = helper.hot.getDataAtRowProp(2, 'itemValue2');
                             if (isNotVal(manufacturer) && isNotVal(model)) {
                                 for (var i = 0; i < helper.pumpingUnitList.length; i++) {
                                     if (manufacturer === helper.pumpingUnitList[i].manufacturer) {
                                         for (var j = 0; j < helper.pumpingUnitList[i].modelList.length; j++) {
                                             if (model === helper.pumpingUnitList[i].modelList[j].model) {
                                                 listData = helper.pumpingUnitList[i].modelList[j].stroke;
                                                 break;
                                             }
                                         }
                                         break;
                                     }
                                 }
                             }
                         }
                         this.type = 'dropdown';
                         this.source = listData;
                         this.strict = true;
                         this.allowInvalid = false;
                     }
                     // 平衡块
                     if (visualColIndex === 2 && visualRowIndex >= 6 && visualRowIndex <= 13) {
                         var listData = [];
                         if (isNotVal(helper.hot)) {
                             var manufacturer = helper.hot.getDataAtRowProp(1, 'itemValue2');
                             var model = helper.hot.getDataAtRowProp(2, 'itemValue2');
                             if (isNotVal(manufacturer) && isNotVal(model)) {
                                 for (var i = 0; i < helper.pumpingUnitList.length; i++) {
                                     if (manufacturer === helper.pumpingUnitList[i].manufacturer) {
                                         for (var j = 0; j < helper.pumpingUnitList[i].modelList.length; j++) {
                                             if (model === helper.pumpingUnitList[i].modelList[j].model) {
                                                 listData = helper.pumpingUnitList[i].modelList[j].balanceWeight;
                                                 break;
                                             }
                                         }
                                         break;
                                     }
                                 }
                             }
                         }
                         this.type = 'dropdown';
                         this.source = listData;
                         this.strict = true;
                         this.allowInvalid = true;
                     }
                 } else {
                     cellProperties.editor = false;
                     if ((visualColIndex <= 1 && visualRowIndex <= 2) || (visualRowIndex >= 4 && visualRowIndex <= 5) || (visualRowIndex == 3 && visualColIndex == 1)) {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 }
                 return cellProperties;
             },
             afterChange: function (changes, source) {
                 if (changes != null && source.toUpperCase() == 'EDIT') {
                     for (var i = 0; i < changes.length; i++) {
                         var index = changes[i][0];
                         var prop = changes[i][1];
                         var oldValue = changes[i][2];
                         var newValue = changes[i][3];
                         if (oldValue != newValue) {
                             if (index == 1 && prop.toUpperCase() == 'ITEMVALUE2') {
                                 helper.hot.setDataAtRowProp(2, 'itemValue2', '');
                                 helper.hot.setDataAtRowProp(3, 'itemValue2', '');
                                 for (var r = 6; r <= 13; r++) helper.hot.setDataAtRowProp(r, 'itemValue2', '');
                                 CreatePumpingUnitDetailedInformationTable();
                                 CreateAndLoadDevicePumpingUnitPTFTable();
                             } else if (index == 2 && prop.toUpperCase() == 'ITEMVALUE2') {
                                 helper.hot.setDataAtRowProp(3, 'itemValue2', '');
                                 for (var r = 6; r <= 13; r++) helper.hot.setDataAtRowProp(r, 'itemValue2', '');
                                 CreatePumpingUnitDetailedInformationTable();
                                 CreateAndLoadDevicePumpingUnitPTFTable();
                             } else if (index == 3 && prop.toUpperCase() == 'ITEMVALUE2') {
                                 CreateAndLoadDevicePumpingUnitPTFTable();
                             }
                         }
                     }
                 }
             },
             afterOnCellMouseOver: function (event, coords, TD) {
                 if (coords.col >= 0 && coords.row >= 0 && helper.hot) {
                     var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                     if (isNotVal(rawValue)) TD.title = String(rawValue);
                 }
             }
         });
     };
     return helper;
 }
};

//================================================================
//抽油机详细信息表
//================================================================
function CreatePumpingUnitDetailedInformationTable() {
 if (devicePumpingUnitDetailedInformationHandsontableHelper != null) {
     if (devicePumpingUnitDetailedInformationHandsontableHelper.hot != undefined) {
         devicePumpingUnitDetailedInformationHandsontableHelper.hot.destroy();
     }
     devicePumpingUnitDetailedInformationHandsontableHelper = null;
 }
 
 var maskEl = 'pumpingUnitDetailPanel';
 mini.mask({
     el: maskEl,
     cls: 'mini-mask-loading',
     html: _loginUserLanguageResource.loadingData
 });

 var manufacturer = '';
 var model = '';
 if (isNotVal(pumpingInfoHandsontableHelper) && isNotVal(pumpingInfoHandsontableHelper.hot)) {
     manufacturer = pumpingInfoHandsontableHelper.hot.getDataAtRowProp(1, 'itemValue2');
     model = pumpingInfoHandsontableHelper.hot.getDataAtRowProp(2, 'itemValue2');
 }

 $.ajax({
     method: 'POST',
     url: context + '/wellInformationManagerController/getPumpingUnitDetailsInfo',
     data: { manufacturer: manufacturer, model: model },
     success: function (result) {
         mini.unmask(maskEl);
         var R = _loginUserLanguageResource;

         if (devicePumpingUnitDetailedInformationHandsontableHelper == null || devicePumpingUnitDetailedInformationHandsontableHelper.hot == undefined) {
             devicePumpingUnitDetailedInformationHandsontableHelper = DevicePumpingUnitDetailedInformationHandsontableHelper.createNew("DevicePumpingUnitDetailedInformationTableDiv_id");
             var colHeaders = [R.idx, R.variable, R.value, '', R.downlinkStatus, R.uplinkStatus];
             var columns = [
                 { data: 'id' },
                 { data: 'itemName' },
                 { data: 'itemValue' },
                 { data: 'itemCode' },
                 { data: 'downlinkStatus' },
                 { data: 'uplinkStatus' }
             ];
             devicePumpingUnitDetailedInformationHandsontableHelper.colHeaders = colHeaders;
             devicePumpingUnitDetailedInformationHandsontableHelper.columns = columns;
             if (result.totalRoot.length == 0) {
                 var emptyArr = [];
                 for (var i = 0; i < 20; i++) emptyArr.push({});
                 devicePumpingUnitDetailedInformationHandsontableHelper.createTable(emptyArr);
             } else {
                 devicePumpingUnitDetailedInformationHandsontableHelper.createTable(result.totalRoot);
             }
         } else {
             if (result.totalRoot.length == 0) {
                 var emptyArr = [];
                 for (var i = 0; i < 20; i++) emptyArr.push({});
                 devicePumpingUnitDetailedInformationHandsontableHelper.hot.loadData(emptyArr);
             } else {
                 devicePumpingUnitDetailedInformationHandsontableHelper.hot.loadData(result.totalRoot);
             }
         }
     },
     failure: function () {
         mini.unmask(maskEl);
         mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
     }
 });
}

var DevicePumpingUnitDetailedInformationHandsontableHelper = {
 createNew: function (divid) {
     var helper = {};
     helper.hot = '';
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];

     helper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.backgroundColor = 'rgb(245, 245, 245)';
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.addUplinkStatusCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         var R = _loginUserLanguageResource;
         if (isNotVal(helper.hot)) {
             var itemValue = helper.hot.getDataAtRowProp(row, 'itemValue');
             if (isNotVal(value)) {
                 if (value === R.uplinkFailed || value === R.noUplink) {
                     td.style.backgroundColor = 'rgb(245, 245, 245)';
                 } else {
                     var same = (isNumber(itemValue) && isNumber(value))
                         ? parseFloat(itemValue) === parseFloat(value)
                         : itemValue === value;
                     td.style.backgroundColor = same ? 'rgb(245, 245, 245)' : '#f09614';
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

     helper.createTable = function (data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             hiddenColumns: { columns: [0, 3, 4, 5], indicators: false },
             columns: helper.columns,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: true,
             colHeaders: helper.colHeaders,
             columnSorting: true,
             sortIndicator: true,
             manualColumnResize: true,
             manualRowResize: true,
             filters: true,
             renderAllRows: true,
             search: true,
             cells: function (row, col, prop) {
                 var cellProperties = {};
                 cellProperties.editor = false;
                 if (prop.toUpperCase() === 'UPLINKSTATUS') {
                     cellProperties.renderer = helper.addUplinkStatusCellStyle;
                 } else {
                     cellProperties.renderer = helper.addBoldBg;
                 }
                 return cellProperties;
             }
         });
     };
     return helper;
 }
};

//================================================================
//抽油机 PRTF 表
//================================================================
function CreateAndLoadDevicePumpingUnitPTFTable() {
 if (devicePumpingUnitPRTFHandsontableHelper != null) {
     if (devicePumpingUnitPRTFHandsontableHelper.hot != undefined) {
         devicePumpingUnitPRTFHandsontableHelper.hot.destroy();
     }
     devicePumpingUnitPRTFHandsontableHelper = null;
 }
 
 var maskEl = 'pumpingUnitPRTFPanel';
 mini.mask({
     el: maskEl,
     cls: 'mini-mask-loading',
     html: _loginUserLanguageResource.loadingData
 });

 var manufacturer = '';
 var model = '';
 var stroke = '';
 if (isNotVal(pumpingInfoHandsontableHelper) && isNotVal(pumpingInfoHandsontableHelper.hot)) {
     manufacturer = pumpingInfoHandsontableHelper.hot.getDataAtRowProp(1, 'itemValue2');
     model = pumpingInfoHandsontableHelper.hot.getDataAtRowProp(2, 'itemValue2');
     stroke = pumpingInfoHandsontableHelper.hot.getDataAtRowProp(3, 'itemValue2');
 }

 $.ajax({
     method: 'POST',
     url: context + '/wellInformationManagerController/getDevicePumpingPRTFData',
     data: { manufacturer: manufacturer, model: model, stroke: stroke },
     success: function (result) {
         mini.unmask(maskEl);
         var R = _loginUserLanguageResource;

         if (devicePumpingUnitPRTFHandsontableHelper == null || devicePumpingUnitPRTFHandsontableHelper.hot == null || devicePumpingUnitPRTFHandsontableHelper.hot == undefined) {
             devicePumpingUnitPRTFHandsontableHelper = DevicePumpingUnitPRTFHandsontableHelper.createNew("DevicePumpingUnitPRTFTableDiv_id");
             var colHeaders = [
                 R.crankAngle + '(°)', R.downlinkStatus, R.uplinkStatus,
                 R.pumpingUnitPR + '(%)', R.downlinkStatus, R.uplinkStatus,
                 R.pumpingUnitTF + '(m)', R.downlinkStatus, R.uplinkStatus
             ];
             var columns = [
                 { data: 'CrankAngle' },
                 { data: 'CrankAngleDownlinkStatus' },
                 { data: 'CrankAngleUplinkStatus' },
                 { data: 'PR' },
                 { data: 'PRDownlinkStatus' },
                 { data: 'PRUplinkStatus' },
                 { data: 'TF' },
                 { data: 'TFDownlinkStatus' },
                 { data: 'TFUplinkStatus' }
             ];
             devicePumpingUnitPRTFHandsontableHelper.colHeaders = colHeaders;
             devicePumpingUnitPRTFHandsontableHelper.columns = columns;

             if (result.totalRoot == 0) {
                 var emptyArr = [];
                 for (var i = 0; i < 30; i++) emptyArr.push({});
                 devicePumpingUnitPRTFHandsontableHelper.createTable(emptyArr);
             } else {
                 devicePumpingUnitPRTFHandsontableHelper.createTable(result.totalRoot);
             }
         } else {
             if (result.totalRoot == 0) {
                 var emptyArr = [];
                 for (var i = 0; i < 30; i++) emptyArr.push({});
                 devicePumpingUnitPRTFHandsontableHelper.hot.loadData(emptyArr);
             } else {
                 devicePumpingUnitPRTFHandsontableHelper.hot.loadData(result.totalRoot);
             }
         }
     },
     failure: function () {
         mini.unmask(maskEl);
         mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
     }
 });
}

var DevicePumpingUnitPRTFHandsontableHelper = {
 createNew: function (divid) {
     var helper = {};
     helper.hot = '';
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];

     helper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.backgroundColor = 'rgb(245, 245, 245)';
     };

     helper.addUplinkStatusCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         var R = _loginUserLanguageResource;
         if (isNotVal(helper.hot)) {
             var p = prop.toUpperCase();
             if (p === 'CRANKANGLEUPLINKSTATUS' || p === 'PRUPLINKSTATUS' || p === 'TFUPLINKSTATUS') {
                 var itemValueCol = '';
                 if (p === 'CRANKANGLEUPLINKSTATUS') itemValueCol = 'CrankAngle';
                 else if (p === 'PRUPLINKSTATUS') itemValueCol = 'PR';
                 else if (p === 'TFUPLINKSTATUS') itemValueCol = 'TF';

                 var itemValue = helper.hot.getDataAtRowProp(row, itemValueCol);
                 if (isNotVal(value)) {
                     if (value === R.uplinkFailed || value === R.noUplink) {
                         td.style.backgroundColor = 'rgb(245, 245, 245)';
                     } else {
                         var same = (isNumber(itemValue) && isNumber(value))
                             ? parseFloat(itemValue) === parseFloat(value)
                             : itemValue === value;
                         td.style.backgroundColor = same ? 'rgb(245, 245, 245)' : '#f09614';
                     }
                 } else {
                     td.innerHTML = '';
                     td.style.backgroundColor = 'rgb(245, 245, 245)';
                 }
             }
         } else {
             td.style.backgroundColor = 'rgb(245, 245, 245)';
         }
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.createTable = function (data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             hiddenColumns: { columns: [1, 2, 4, 5, 7, 8], indicators: false, copyPasteEnabled: false },
             columns: helper.columns,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: true,
             colHeaders: helper.colHeaders,
             columnSorting: true,
             sortIndicator: true,
             manualColumnResize: true,
             manualRowResize: true,
             filters: true,
             renderAllRows: true,
             search: true,
             cells: function (row, col, prop) {
                 var cellProperties = {};
                 cellProperties.editor = false;
                 var p = prop.toUpperCase();
                 if (p === 'CRANKANGLEUPLINKSTATUS' || p === 'PRUPLINKSTATUS' || p === 'TFUPLINKSTATUS') {
                     cellProperties.renderer = helper.addUplinkStatusCellStyle;
                 } else {
                     cellProperties.renderer = helper.addBoldBg;
                 }
                 return cellProperties;
             }
         });
     };
     return helper;
 }
};

//================================================================
//视频配置
//================================================================
function CreateAndLoadVideoInfoTable(deviceId, deviceName, isNew) {
 if (videoInfoHandsontableHelper != null && videoInfoHandsontableHelper.hot != undefined) {
     videoInfoHandsontableHelper.hot.destroy();
     videoInfoHandsontableHelper = null;
 }
 videoInfoHandsontableHelper = null;

 var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';

 var maskEl = 'videoInfoPanel';
 mini.mask({
     el: maskEl,
     cls: 'mini-mask-loading',
     html: _loginUserLanguageResource.loadingData
 });
 $.ajax({
     method: 'POST',
     url: context + '/wellInformationManagerController/getDeviceVideoInfo',
     data: { deviceId: deviceId, deviceType: getCurrentDeviceType(), orgId: leftOrgId },
     success: function (result) {
         mini.unmask(maskEl);
         if (videoInfoHandsontableHelper == null || videoInfoHandsontableHelper.hot == undefined) {
             videoInfoHandsontableHelper = VideoInfoHandsontableHelper.createNew("VideoInfoTableDiv_id");

             var colHeaders = [];
             var columns = [];
             var colWidths = [];

             for (var i = 0; i < result.columns.length; i++) {
                 colHeaders.push(result.columns[i].header);
                 colWidths.push(result.columns[i].flex);

                 if (result.columns[i].dataIndex.toUpperCase() === 'VIDEOKEY') {
                     var source = [];
                     for (var j = 0; j < result.videoKeyList.length; j++) source.push(result.videoKeyList[j]);
                     columns.push({ data: result.columns[i].dataIndex, type: 'dropdown', strict: true, allowInvalid: false, source: source });
                 } else {
                     columns.push({ data: result.columns[i].dataIndex });
                 }
             }
             videoInfoHandsontableHelper.colHeaders = colHeaders;
             videoInfoHandsontableHelper.columns = columns;
             videoInfoHandsontableHelper.colWidths = colWidths;

             if (result.totalRoot.length == 0) {
                 videoInfoHandsontableHelper.createTable([{},{},{},{}]);
             } else {
                 videoInfoHandsontableHelper.createTable(result.totalRoot);
             }
         } else {
             if (result.totalRoot.length == 0) {
                 videoInfoHandsontableHelper.hot.loadData([{},{},{},{}]);
             } else {
                 videoInfoHandsontableHelper.hot.loadData(result.totalRoot);
             }
         }
     },
     failure: function () {
         mini.unmask(maskEl);
         mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
     }
 });
}

var VideoInfoHandsontableHelper = {
 createNew: function (divid) {
     var helper = {};
     helper.hot = '';
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];
     helper.colWidths = [];

     helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         if (cellProperties.type == 'dropdown') {
             Handsontable.renderers.DropdownRenderer.apply(this, arguments);
         } else {
             Handsontable.renderers.TextRenderer.apply(this, arguments);
         }
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
         if (cellProperties.type == 'dropdown') {
             Handsontable.renderers.DropdownRenderer.apply(this, arguments);
         } else {
             Handsontable.renderers.TextRenderer.apply(this, arguments);
         }
         td.style.backgroundColor = 'rgb(245, 245, 245)';
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.createTable = function (data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             width: '100%',      // ★ 用百分比
             height: 'auto',
             hiddenColumns: { columns: [0], indicators: false, copyPasteEnabled: false },
             colWidths: helper.colWidths,
             columns: helper.columns,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: true,
             sortIndicator: true,
             manualColumnResize: true,
             manualRowResize: true,
             filters: true,
             renderAllRows: true,
             search: true,
             contextMenu: {
                 items: {
                     "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                     "cut":  { name: _loginUserLanguageResource.contextMenu_cut }
                 }
             },
             cells: function (row, col, prop) {
                 var cellProperties = {};
                 var visualColIndex = this.instance.toVisualColumn(col);
                 var editFlag = parseInt(_dmModuleRight.editFlag);
                 if (editFlag == 1) {
                     if (visualColIndex < 2) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addReadOnlyBg;
                     } else {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 } else {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                 }
                 return cellProperties;
             },
             afterOnCellMouseOver: function (event, coords, TD) {
                 if (coords.col >= 0 && coords.row >= 0 && helper.hot) {
                     var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                     if (isNotVal(rawValue)) TD.title = String(rawValue);
                 }
             }
         });
     };
     return helper;
 }
};

//================================================================
//功图构建
//================================================================
function CreateAndLoadFSDiagramConstructionDataTable(deviceId, deviceName, applicationScenarios, isNew) {
 if (fsDiagramConstructionHandsontableHelper != null) {
     if (fsDiagramConstructionHandsontableHelper.hot != undefined) {
         fsDiagramConstructionHandsontableHelper.hot.destroy();
     }
     fsDiagramConstructionHandsontableHelper = null;
 }
 var maskEl = 'fsDiagramConstructionPanel';
 mini.mask({
     el: maskEl,
     cls: 'mini-mask-loading',
     html: _loginUserLanguageResource.loadingData
 });
 $.ajax({
     method: 'POST',
     url: context + '/wellInformationManagerController/getFSDiagramConstructionDataInfo',
     data: { deviceId: deviceId },
     success: function (result) {
         mini.unmask(maskEl);
         var R = _loginUserLanguageResource;

         if (fsDiagramConstructionHandsontableHelper == null || fsDiagramConstructionHandsontableHelper.hot == undefined) {
             fsDiagramConstructionHandsontableHelper = FSDiagramConstructionHandsontableHelper.createNew("DeviceFSDiagramConstructionInfoTableDiv_id");
             var colHeaders = [R.idx, R.variable, R.value, '', R.downlinkStatus, R.uplinkStatus];
             var columns = [
                 { data: 'id' },
                 { data: 'itemName' },
                 {
                     data: 'itemValue', type: 'text', allowInvalid: true,
                     validator: function (val, callback) {
                         return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, fsDiagramConstructionHandsontableHelper);
                     }
                 },
                 { data: 'itemCode' },
                 { data: 'downlinkStatus' },
                 { data: 'uplinkStatus' }
             ];
             fsDiagramConstructionHandsontableHelper.colHeaders = colHeaders;
             fsDiagramConstructionHandsontableHelper.columns = columns;

             if (result.totalRoot.length == 0) {
                 var emptyArr = [];
                 for (var i = 0; i < 14; i++) emptyArr.push({});
                 fsDiagramConstructionHandsontableHelper.createTable(emptyArr);
             } else {
                 fsDiagramConstructionHandsontableHelper.createTable(result.totalRoot);
             }
         } else {
             if (result.totalRoot.length == 0) {
                 var emptyArr = [];
                 for (var i = 0; i < 14; i++) emptyArr.push({});
                 fsDiagramConstructionHandsontableHelper.hot.loadData(emptyArr);
             } else {
                 fsDiagramConstructionHandsontableHelper.hot.loadData(result.totalRoot);
             }
         }
     },
     failure: function () {
         mini.unmask(maskEl);
         mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
     }
 });
}

var FSDiagramConstructionHandsontableHelper = {
 createNew: function (divid) {
     var helper = {};
     helper.hot = '';
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];

     helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.backgroundColor = 'rgb(245, 245, 245)';
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.addUplinkStatusCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         var R = _loginUserLanguageResource;
         if (isNotVal(helper.hot)) {
             var itemValue = helper.hot.getDataAtRowProp(row, 'itemValue');
             if (isNotVal(value)) {
                 if (value === R.uplinkFailed || value === R.noUplink) {
                     td.style.backgroundColor = 'rgb(245, 245, 245)';
                 } else {
                     var same = (isNumber(itemValue) && isNumber(value))
                         ? parseFloat(itemValue) === parseFloat(value)
                         : itemValue === value;
                     td.style.backgroundColor = same ? 'rgb(245, 245, 245)' : '#f09614';
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

     helper.createTable = function (data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             width: '100%',      // ★ 用百分比
             height: 'auto',
             colWidths: [50, 100, 100],
             hiddenColumns: { columns: [0, 3, 4, 5], indicators: false, copyPasteEnabled: false },
             hiddenRows: { rows: [], indicators: false, copyPasteEnabled: false },
             columns: helper.columns,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: true,
             sortIndicator: true,
             manualColumnResize: true,
             manualRowResize: true,
             filters: true,
             renderAllRows: true,
             search: true,
             contextMenu: {
                 items: {
                     "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                     "cut":  { name: _loginUserLanguageResource.contextMenu_cut }
                 }
             },
             cells: function (row, col, prop) {
                 var cellProperties = {};
                 var visualColIndex = this.instance.toVisualColumn(col);
                 var visualRowIndex = this.instance.toVisualRow(row);
                 var editFlag = parseInt(_dmModuleRight.editFlag);
                 var R = _loginUserLanguageResource;

                 if (editFlag == 1) {
                     if (visualColIndex != 2 && visualColIndex != 5) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addCellStyle;
                     } else if (visualColIndex == 5) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addUplinkStatusCellStyle;
                     }
                     if (visualColIndex === 2 && visualRowIndex === 0) {
                         this.type = 'dropdown';
                         this.source = [R.boardDataSource1, R.boardDataSource2, R.boardDataSource3];
                         this.strict = true;
                         this.allowInvalid = false;
                     }
                     if (visualColIndex === 2 && visualRowIndex === 13) {
                         this.type = 'dropdown';
                         this.source = [R.PRTFSrc1, R.PRTFSrc2];
                         this.strict = true;
                         this.allowInvalid = false;
                     }
                 } else {
                     cellProperties.editor = false;
                     if (visualColIndex != 2) {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 }
                 return cellProperties;
             },
             afterOnCellMouseOver: function (event, coords, TD) {
                 if (coords.col >= 0 && coords.row >= 0 && helper.hot) {
                     var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                     if (isNotVal(rawValue)) TD.title = String(rawValue);
                 }
             }
         });
     };
     return helper;
 }
};

//================================================================
//系统参数配置
//================================================================
function CreateAndLoadDeviceSystemParameterTable(deviceId, deviceName, applicationScenarios, isNew) {
 if (deviceSystemParameterHandsontableHelper != null) {
     if (deviceSystemParameterHandsontableHelper.hot != undefined) {
         deviceSystemParameterHandsontableHelper.hot.destroy();
     }
     deviceSystemParameterHandsontableHelper = null;
 }
 
 var maskEl = 'systemParameterPanel';
 mini.mask({
     el: maskEl,
     cls: 'mini-mask-loading',
     html: _loginUserLanguageResource.loadingData
 });

 $.ajax({
     method: 'POST',
     url: context + '/wellInformationManagerController/getSystemParameterInfo',
     data: { deviceId: deviceId },
     success: function (result) {
         mini.unmask(maskEl);
         var R = _loginUserLanguageResource;

         if (deviceSystemParameterHandsontableHelper == null || deviceSystemParameterHandsontableHelper.hot == undefined) {
             deviceSystemParameterHandsontableHelper = DeviceSystemParameterHandsontableHelper.createNew("DeviceSystemParameterConfigurationInfoTableDiv_id");
             var colHeaders = [R.idx, R.variable, R.value, '', R.downlinkStatus, R.uplinkStatus];
             var columns = [
                 { data: 'id' },
                 { data: 'itemName' },
                 {
                     data: 'itemValue', type: 'text', allowInvalid: true,
                     validator: function (val, callback) {
                         return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, deviceSystemParameterHandsontableHelper);
                     }
                 },
                 { data: 'itemCode' },
                 { data: 'downlinkStatus' },
                 { data: 'uplinkStatus' }
             ];
             deviceSystemParameterHandsontableHelper.colHeaders = colHeaders;
             deviceSystemParameterHandsontableHelper.columns = columns;

             if (result.totalRoot.length == 0) {
                 var emptyArr = [];
                 for (var i = 0; i < 40; i++) emptyArr.push({});
                 deviceSystemParameterHandsontableHelper.createTable(emptyArr);
             } else {
                 deviceSystemParameterHandsontableHelper.createTable(result.totalRoot);
             }
         } else {
             if (result.totalRoot.length == 0) {
                 var emptyArr = [];
                 for (var i = 0; i < 40; i++) emptyArr.push({});
                 deviceSystemParameterHandsontableHelper.hot.loadData(emptyArr);
             } else {
                 deviceSystemParameterHandsontableHelper.hot.loadData(result.totalRoot);
             }
         }
     },
     failure: function () {
         mini.unmask(maskEl);
         mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
     }
 });
}

var DeviceSystemParameterHandsontableHelper = {
 createNew: function (divid) {
     var helper = {};
     helper.hot = '';
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];

     helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.backgroundColor = 'rgb(245, 245, 245)';
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.addUplinkStatusCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         var R = _loginUserLanguageResource;
         if (isNotVal(helper.hot)) {
             var itemValue = helper.hot.getDataAtRowProp(row, 'itemValue');
             if (isNotVal(value)) {
                 if (value === R.uplinkFailed || value === R.noUplink) {
                     td.style.backgroundColor = 'rgb(245, 245, 245)';
                 } else {
                     var same = (isNumber(itemValue) && isNumber(value))
                         ? parseFloat(itemValue) === parseFloat(value)
                         : itemValue === value;
                     td.style.backgroundColor = same ? 'rgb(245, 245, 245)' : '#f09614';
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

     helper.createTable = function (data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             width: '100%',      // ★ 用百分比
             height: 'auto',
             colWidths: [50, 100, 100],
             hiddenColumns: { columns: [0, 2, 3, 4, 5], indicators: false, copyPasteEnabled: false },
             columns: helper.columns,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: true,
             sortIndicator: true,
             manualColumnResize: true,
             manualRowResize: true,
             filters: true,
             renderAllRows: true,
             search: true,
             contextMenu: {
                 items: {
                     "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                     "cut":  { name: _loginUserLanguageResource.contextMenu_cut }
                 }
             },
             cells: function (row, col, prop) {
                 var cellProperties = {};
                 var visualColIndex = this.instance.toVisualColumn(col);
                 var editFlag = parseInt(_dmModuleRight.editFlag);
                 if (editFlag == 1) {
                     if (visualColIndex != 2) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 } else {
                     cellProperties.editor = false;
                     if (visualColIndex != 2 && visualColIndex != 5) {
                         cellProperties.renderer = helper.addCellStyle;
                     } else if (visualColIndex == 5) {
                         cellProperties.renderer = helper.addUplinkStatusCellStyle;
                     }
                 }
                 return cellProperties;
             }
         });
     };
     return helper;
 }
};

//================================================================
//智能变频
//================================================================
function CreateAndLoadDeviceIntelligentFrequencyConversionTable(deviceId, deviceName, applicationScenarios, isNew) {
 if (deviceIntelligentFrequencyConversionHandsontableHelper != null) {
     if (deviceIntelligentFrequencyConversionHandsontableHelper.hot != undefined) {
         deviceIntelligentFrequencyConversionHandsontableHelper.hot.destroy();
     }
     deviceIntelligentFrequencyConversionHandsontableHelper = null;
 }
 
 var maskEl = 'intelligentFrequencyConversionPanel';
 mini.mask({
     el: maskEl,
     cls: 'mini-mask-loading',
     html: _loginUserLanguageResource.loadingData
 });

 $.ajax({
     method: 'POST',
     url: context + '/wellInformationManagerController/getIntelligentFrequencyConversionInfo',
     data: { deviceId: deviceId },
     success: function (result) {
         mini.unmask(maskEl);
         var R = _loginUserLanguageResource;

         if (deviceIntelligentFrequencyConversionHandsontableHelper == null || deviceIntelligentFrequencyConversionHandsontableHelper.hot == undefined) {
             deviceIntelligentFrequencyConversionHandsontableHelper = DeviceIntelligentFrequencyConversionHandsontableHelper.createNew("DeviceIntelligentFrequencyConversionInfoTableDiv_id");
             var colHeaders = [[
                 R.idx,
                 { label: R.variable, colspan: 3 },
                 R.value, '', R.downlinkStatus, R.uplinkStatus
             ]];
             var columns = [
                 { data: 'id' },
                 { data: 'itemClasses' },
                 { data: 'itemClasses2' },
                 { data: 'itemName' },
                 {
                     data: 'itemValue', type: 'text', allowInvalid: true,
                     validator: function (val, callback) {
                         return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, deviceIntelligentFrequencyConversionHandsontableHelper);
                     }
                 },
                 { data: 'itemCode' },
                 { data: 'downlinkStatus' },
                 { data: 'uplinkStatus' }
             ];
             deviceIntelligentFrequencyConversionHandsontableHelper.colHeaders = colHeaders;
             deviceIntelligentFrequencyConversionHandsontableHelper.columns = columns;

             if (result.totalRoot.length == 0) {
                 var emptyArr = [];
                 for (var i = 0; i < 42; i++) emptyArr.push({});
                 deviceIntelligentFrequencyConversionHandsontableHelper.createTable(emptyArr);
             } else {
                 deviceIntelligentFrequencyConversionHandsontableHelper.createTable(result.totalRoot);
             }
         } else {
             if (result.totalRoot.length == 0) {
                 var emptyArr = [];
                 for (var i = 0; i < 42; i++) emptyArr.push({});
                 deviceIntelligentFrequencyConversionHandsontableHelper.hot.loadData(emptyArr);
             } else {
                 deviceIntelligentFrequencyConversionHandsontableHelper.hot.loadData(result.totalRoot);
             }
         }
     },
     failure: function () {
         mini.unmask(maskEl);
         mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
     }
 });
}

var DeviceIntelligentFrequencyConversionHandsontableHelper = {
 createNew: function (divid) {
     var helper = {};
     helper.hot = '';
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];

     helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.backgroundColor = 'rgb(245, 245, 245)';
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.addUplinkStatusCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         if (cellProperties.type === 'checkbox') {
             Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
         } else {
             Handsontable.renderers.TextRenderer.apply(this, arguments);
         }
         var R = _loginUserLanguageResource;
         if (isNotVal(instance)) {
             var itemValue = instance.getDataAtRowProp(row, 'itemValue');
             if (isNotVal(value + '')) {
                 if (value === R.uplinkFailed || value === R.noUplink) {
                     td.style.backgroundColor = 'rgb(245, 245, 245)';
                 } else {
                     var same = (isNumber(itemValue) && isNumber(value))
                         ? parseFloat(itemValue) === parseFloat(value)
                         : itemValue === value;
                     td.style.backgroundColor = same ? 'rgb(245, 245, 245)' : '#f09614';
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

     helper.createTable = function (data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);

         // 合并单元格：与 ExtJS 一致
         var mergeCells = [
             { row: 0, col: 1, rowspan: 9, colspan: 1 },
             { row: 9, col: 1, rowspan: 5, colspan: 1 },
             { row: 14, col: 1, rowspan: 28, colspan: 1 },
             { row: 0, col: 2, rowspan: 1, colspan: 2 },
             { row: 1, col: 2, rowspan: 4, colspan: 1 },
             { row: 5, col: 2, rowspan: 4, colspan: 1 },
             { row: 9, col: 2, rowspan: 1, colspan: 2 },
             { row: 10, col: 2, rowspan: 4, colspan: 1 }
         ];
         for (var r = 14; r <= 41; r++) {
             mergeCells.push({ row: r, col: 2, rowspan: 1, colspan: 2 });
         }

         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             colWidths: [50, 70, 70, 100, 50],
             hiddenColumns: { columns: [0, 5, 6, 7], indicators: false, copyPasteEnabled: false },
             hiddenRows: { rows: [], indicators: false, copyPasteEnabled: false },
             columns: helper.columns,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             nestedHeaders: helper.colHeaders,
             columnSorting: false,
             sortIndicator: false,
             manualColumnResize: true,
             manualRowResize: true,
             filters: true,
             renderAllRows: true,
             search: true,
             contextMenu: {
                 items: {
                     "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                     "cut":  { name: _loginUserLanguageResource.contextMenu_cut }
                 }
             },
             mergeCells: mergeCells,
             cells: function (row, col, prop) {
                 var cellProperties = {};
                 var visualColIndex = this.instance.toVisualColumn(col);
                 var visualRowIndex = this.instance.toVisualRow(row);
                 var editFlag = parseInt(_dmModuleRight.editFlag);

                 if (editFlag == 1) {
                     if (visualColIndex != 4 && visualColIndex != 7) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addCellStyle;
                     } else if (visualColIndex == 7) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addUplinkStatusCellStyle;
                     }
                 } else {
                     cellProperties.editor = false;
                     if (visualColIndex != 4) {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 }

                 // 主区域为 checkbox 行
                 if (visualColIndex === 4 && (visualRowIndex === 0 || visualRowIndex === 9 || visualRowIndex >= 14)) {
                     this.type = 'checkbox';
                 }
                 return cellProperties;
             },
             beforeChange: function (changes, source) {
                 if (!changes) return true;
                 if (parseInt(_dmModuleRight.editFlag) === 0) return false;
                 return true;
             },
             afterOnCellMouseOver: function (event, coords, TD) {
                 if (coords.col >= 0 && coords.row >= 0 && helper.hot) {
                     var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                     if (isNotVal(rawValue)) TD.title = String(rawValue);
                 }
             }
         });
     };
     return helper;
 }
};

//================================================================
//联锁保护
//================================================================
function CreateAndLoadDeviceInterlockProtectionTable(deviceId, deviceName, applicationScenarios, isNew) {
 if (deviceInterlockProtectionHandsontableHelper != null) {
     if (deviceInterlockProtectionHandsontableHelper.hot != undefined) {
         deviceInterlockProtectionHandsontableHelper.hot.destroy();
     }
     deviceInterlockProtectionHandsontableHelper = null;
 }
 
 var maskEl = 'interlockProtectionPanel';
 mini.mask({
     el: maskEl,
     cls: 'mini-mask-loading',
     html: _loginUserLanguageResource.loadingData
 });

 $.ajax({
     method: 'POST',
     url: context + '/wellInformationManagerController/getInterlockProtectionInfo',
     data: { deviceId: deviceId },
     success: function (result) {
         mini.unmask(maskEl);
         var R = _loginUserLanguageResource;

         if (deviceInterlockProtectionHandsontableHelper == null || deviceInterlockProtectionHandsontableHelper.hot == undefined) {
             deviceInterlockProtectionHandsontableHelper = DeviceInterlockProtectionHandsontableHelper.createNew("DeviceInterlockProtectionInfoTableDiv_id");
             var colHeaders = [R.idx, R.name, R.variable, R.value, '', R.downlinkStatus, R.uplinkStatus];
             var columns = [
                 { data: 'id' },
                 { data: 'itemClasses' },
                 { data: 'itemName' },
                 {
                     data: 'itemValue', type: 'text', allowInvalid: true,
                     validator: function (val, callback) {
                         return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, deviceInterlockProtectionHandsontableHelper);
                     }
                 },
                 { data: 'itemCode' },
                 { data: 'downlinkStatus' },
                 { data: 'uplinkStatus' }
             ];
             deviceInterlockProtectionHandsontableHelper.colHeaders = colHeaders;
             deviceInterlockProtectionHandsontableHelper.columns = columns;

             if (result.totalRoot.length == 0) {
                 var emptyArr = [];
                 for (var i = 0; i < 29; i++) emptyArr.push({});
                 deviceInterlockProtectionHandsontableHelper.createTable(emptyArr);
             } else {
                 deviceInterlockProtectionHandsontableHelper.createTable(result.totalRoot);
             }
         } else {
             if (result.totalRoot.length == 0) {
                 var emptyArr = [];
                 for (var i = 0; i < 29; i++) emptyArr.push({});
                 deviceInterlockProtectionHandsontableHelper.hot.loadData(emptyArr);
             } else {
                 deviceInterlockProtectionHandsontableHelper.hot.loadData(result.totalRoot);
             }
         }
     },
     failure: function () {
         mini.unmask(maskEl);
         mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
     }
 });
}

var DeviceInterlockProtectionHandsontableHelper = {
 createNew: function (divid) {
     var helper = {};
     helper.hot = '';
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];

     helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.backgroundColor = 'rgb(245, 245, 245)';
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.addUplinkStatusCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
         if (cellProperties.type === 'checkbox') {
             Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
         } else {
             Handsontable.renderers.TextRenderer.apply(this, arguments);
         }
         var R = _loginUserLanguageResource;
         if (isNotVal(instance)) {
             var itemValue = instance.getDataAtRowProp(row, 'itemValue');
             if (isNotVal(value + '')) {
                 if (value === R.uplinkFailed || value === R.noUplink) {
                     td.style.backgroundColor = 'rgb(245, 245, 245)';
                 } else {
                     var same = (isNumber(itemValue) && isNumber(value))
                         ? parseFloat(itemValue) === parseFloat(value)
                         : itemValue === value;
                     td.style.backgroundColor = same ? 'rgb(245, 245, 245)' : '#f09614';
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

     helper.createTable = function (data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             colWidths: [50, 100, 100, 50],
             hiddenColumns: { columns: [0, 4, 5, 6], indicators: false, copyPasteEnabled: false },
             hiddenRows: { rows: [], indicators: false, copyPasteEnabled: false },
             columns: helper.columns,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: false,
             sortIndicator: false,
             manualColumnResize: true,
             manualRowResize: true,
             filters: true,
             renderAllRows: true,
             search: true,
             contextMenu: {
                 items: {
                     "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                     "cut":  { name: _loginUserLanguageResource.contextMenu_cut }
                 }
             },
             mergeCells: [
                 { row: 0, col: 1, rowspan: 1, colspan: 2 },
                 { row: 1, col: 1, rowspan: 28, colspan: 1 }
             ],
             cells: function (row, col, prop) {
                 var cellProperties = {};
                 var visualColIndex = this.instance.toVisualColumn(col);
                 var editFlag = parseInt(_dmModuleRight.editFlag);

                 if (editFlag == 1) {
                     if (visualColIndex != 3 && visualColIndex != 6) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addCellStyle;
                     } else if (visualColIndex == 6) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addUplinkStatusCellStyle;
                     }
                 } else {
                     cellProperties.editor = false;
                     if (visualColIndex != 3) {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 }

                 if (visualColIndex === 3) {
                     this.type = 'checkbox';
                 }
                 return cellProperties;
             },
             beforeChange: function (changes, source) {
                 if (!changes) return true;
                 if (parseInt(_dmModuleRight.editFlag) === 0) return false;
                 return true;
             },
             afterOnCellMouseOver: function (event, coords, TD) {
                 if (coords.col >= 0 && coords.row >= 0 && helper.hot) {
                     var cellProperties = helper.hot.getCellMeta(coords.row, coords.col);
                     if (cellProperties.type == 'text') {
                         var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                         if (isNotVal(rawValue)) TD.title = String(rawValue);
                     }
                 }
             }
         });
     };
     return helper;
 }
};

//================================================================
//获取当前激活的附加信息 Tab 类型
//================================================================
function getCurrentAdditionalInfoType() {
	var additionalTabs = mini.get('deviceAdditionalTabs');
	if (!additionalTabs) return -1;
	var activeTab = additionalTabs.getActiveTab();
	if (!activeTab) return -1;

	var name = activeTab.name;
	if (name === 'additionalInfo') return 0;
	if (name === 'auxiliaryDevice') return 1;
	if (name === 'videoInfo') return 2;
	if (name === 'fsDiagramConstruction') return 4;
	if (name === 'intelligentFrequencyConversion') return 5;
	if (name === 'systemParameter') return 7;
	if (name === 'interlockProtection') return 8;

	if (name === 'calculateData') {
	   var calculateDataTabs = mini.get('deviceCalculateDataTabs');
	   if (calculateDataTabs) {
	       var activeSubTab = calculateDataTabs.getActiveTab();
	       if (activeSubTab) {
	           if (activeSubTab.name === 'wellboreData') return 31;
	           if (activeSubTab.name === 'pumpingUnitData') return 32;
	       }
	   }
	}
	return -1;
}

//================================================================
//收集附加信息数据
//================================================================
function collectAdditionalInformationData(additionalInformationType, deviceId, applicationScenarios) {
var deviceAdditionalInformationData = {
   deviceId: deviceId,
   type: additionalInformationType,
   data: ""
};

if (additionalInformationType === 0) { // 附加信息
   var additionalInfoList = [];
   if (deviceAdditionalInfoHandsontableHelper != null && deviceAdditionalInfoHandsontableHelper.hot != undefined) {
       var additionalInfoData = deviceAdditionalInfoHandsontableHelper.hot.getData();
       for (var i = 0; i < additionalInfoData.length; i++) {
           if (isNotVal(additionalInfoData[i][1])) {
               var additionalInfo = {};
               additionalInfo.itemName = additionalInfoData[i][1];
               additionalInfo.itemValue = isNotVal(additionalInfoData[i][2]) ? additionalInfoData[i][2] : "";
               additionalInfo.itemUnit = isNotVal(additionalInfoData[i][3]) ? additionalInfoData[i][3] : "";
               additionalInfo.overview = additionalInfoData[i][4] ? 1 : 0;
               additionalInfo.overviewSort = isNumber(additionalInfoData[i][5]) ? additionalInfoData[i][5] : "";
               additionalInfoList.push(additionalInfo);
           }
       }
   }
   if (additionalInfoList.length > 0) {
       deviceAdditionalInformationData.data = JSON.stringify(additionalInfoList);
   }
} 
else if (additionalInformationType === 1) { // 辅件设备
   var auxiliaryDevice = [];
   if (deviceAuxiliaryDeviceInfoHandsontableHelper != null && deviceAuxiliaryDeviceInfoHandsontableHelper.hot != undefined) {
       var auxiliaryDeviceData = deviceAuxiliaryDeviceInfoHandsontableHelper.hot.getData();
       for (var i = 0; i < auxiliaryDeviceData.length; i++) {
           if (auxiliaryDeviceData[i][0]) { // checked
               auxiliaryDevice.push(auxiliaryDeviceData[i][7]); // realId
           }
       }
   }
   if (auxiliaryDevice.length > 0) {
       deviceAdditionalInformationData.data = JSON.stringify(auxiliaryDevice);
   }
} 
else if (additionalInformationType === 2) { // 视频配置
   var videoUrl1 = '', videoUrl2 = '', videoKeyName1 = '', videoKeyName2 = '';
   if (videoInfoHandsontableHelper != null && videoInfoHandsontableHelper.hot != undefined) {
       var videoInfoData = videoInfoHandsontableHelper.hot.getData();
       if (videoInfoData.length > 0) {
           videoUrl1 = videoInfoData[0][2]; videoKeyName1 = videoInfoData[0][3];
       }
       if (videoInfoData.length > 1) {
           videoUrl2 = videoInfoData[1][2]; videoKeyName2 = videoInfoData[1][3];
       }
   }
   var videoInfoList = [videoUrl1, videoUrl2, videoKeyName1, videoKeyName2];
   deviceAdditionalInformationData.data = JSON.stringify(videoInfoList);
} 
else if (additionalInformationType === 31) { // 生产数据配置 (井筒数据)
   var deviceCalculateDataType = _dmCurrentCalculateType || 0;
   var productionInfoList = [];
   productionInfoList.push(deviceCalculateDataType);

   if (deviceCalculateDataType == 1 || deviceCalculateDataType == 2) {
       var deviceProductionData = {};
       var manualInterventionResultName = _loginUserLanguageResource.noIntervention;
       var FESDiagramSrcName = '';
       
       if (productionHandsontableHelper != null && productionHandsontableHelper.hot != undefined) {
           var productionHandsontableData = productionHandsontableHelper.hot.getData();
           
           if (deviceCalculateDataType == 1) {
               // ★ 指定为功图计算 (SRP)
               deviceProductionData.FluidPVT = {};
               if (applicationScenarios == 1 && isNumber(parseFloat(productionHandsontableData[0][2]))) {
                   deviceProductionData.FluidPVT.CrudeOilDensity = parseFloat(productionHandsontableData[0][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[1][2]))) {
                   deviceProductionData.FluidPVT.WaterDensity = parseFloat(productionHandsontableData[1][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[2][2]))) {
                   deviceProductionData.FluidPVT.NaturalGasRelativeDensity = parseFloat(productionHandsontableData[2][2]);
               }
               if (applicationScenarios == 1 && isNumber(parseFloat(productionHandsontableData[3][2]))) {
                   deviceProductionData.FluidPVT.SaturationPressure = parseFloat(productionHandsontableData[3][2]);
               }

               deviceProductionData.Reservoir = {};
               if (isNumber(parseFloat(productionHandsontableData[4][2]))) {
                   deviceProductionData.Reservoir.Depth = parseFloat(productionHandsontableData[4][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[5][2]))) {
                   deviceProductionData.Reservoir.Temperature = parseFloat(productionHandsontableData[5][2]);
               }

               deviceProductionData.Production = {};
               if (isNumber(parseFloat(productionHandsontableData[6][2]))) {
                   deviceProductionData.Production.TubingPressure = parseFloat(productionHandsontableData[6][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[7][2]))) {
                   deviceProductionData.Production.CasingPressure = parseFloat(productionHandsontableData[7][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[8][2]))) {
                   deviceProductionData.Production.WellHeadTemperature = parseFloat(productionHandsontableData[8][2]);
               }
               if (applicationScenarios == 0) {
                   deviceProductionData.Production.WaterCut = 100;
               } else if (applicationScenarios == 1 && isNumber(parseFloat(productionHandsontableData[9][2]))) {
                   deviceProductionData.Production.WaterCut = parseFloat(productionHandsontableData[9][2]);
               } else if (applicationScenarios == 2 && isNumber(parseFloat(productionHandsontableData[9][2]))) {
                   deviceProductionData.Production.WaterCut = parseFloat(productionHandsontableData[9][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[10][2]))) {
                   deviceProductionData.Production.ProductionGasOilRatio = parseFloat(productionHandsontableData[10][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[11][2]))) {
                   deviceProductionData.Production.ProducingfluidLevel = parseFloat(productionHandsontableData[11][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[12][2]))) {
                   deviceProductionData.Production.PumpSettingDepth = parseFloat(productionHandsontableData[12][2]);
               }

               deviceProductionData.Pump = {};
               deviceProductionData.Pump.PumpType = 'T';
               var BarrelType = productionHandsontableData[13][2];
               if (productionHandsontableData[13][2] == _loginUserLanguageResource.barrelType_L) {
                   BarrelType = 'L';
               } else if (productionHandsontableData[13][2] == _loginUserLanguageResource.barrelType_H) {
                   BarrelType = 'H';
               }
               if (isNotVal(BarrelType)) {
                   deviceProductionData.Pump.BarrelType = BarrelType;
               }
               if (isNumber(parseInt(productionHandsontableData[14][2]))) {
                   deviceProductionData.Pump.PumpGrade = parseInt(productionHandsontableData[14][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[15][2]))) {
                   deviceProductionData.Pump.PumpBoreDiameter = parseFloat(productionHandsontableData[15][2]) * 0.001;
               }
               if (isNumber(parseFloat(productionHandsontableData[16][2]))) {
                   deviceProductionData.Pump.PumpBoreDiameter2 = parseFloat(productionHandsontableData[16][2]) * 0.001;
               }
               if (isNumber(parseFloat(productionHandsontableData[17][2]))) {
                   deviceProductionData.Pump.PlungerLength = parseFloat(productionHandsontableData[17][2]);
               }

               deviceProductionData.TubingString = {};
               deviceProductionData.TubingString.EveryTubing = [];
               var EveryTubing = {};
               if (isNumber(parseFloat(productionHandsontableData[18][2]))) {
                   EveryTubing.InsideDiameter = parseFloat(productionHandsontableData[18][2]) * 0.001;
               }
               deviceProductionData.TubingString.EveryTubing.push(EveryTubing);

               deviceProductionData.CasingString = {};
               deviceProductionData.CasingString.EveryCasing = [];
               var EveryCasing = {};
               if (isNumber(parseFloat(productionHandsontableData[19][2]))) {
                   EveryCasing.InsideDiameter = parseFloat(productionHandsontableData[19][2]) * 0.001;
               }
               deviceProductionData.CasingString.EveryCasing.push(EveryCasing);

               deviceProductionData.RodString = {};
               deviceProductionData.RodString.EveryRod = [];

               // 4组抽油杆解析
               var rodStartIndexes = [20, 25, 30, 35];
               for (var r = 0; r < 4; r++) {
                   var idx = rodStartIndexes[r];
                   if (isNotVal(productionHandsontableData[idx][2]) &&
                       isNotVal(productionHandsontableData[idx+1][2]) &&
                       isNumber(parseFloat(productionHandsontableData[idx+2][2])) &&
                       (productionHandsontableData[idx+3][2] == '' || isNumber(parseFloat(productionHandsontableData[idx+3][2]))) &&
                       isNumber(parseFloat(productionHandsontableData[idx+4][2]))) {
                       
                       var Rod = {};
                       var typeVal = productionHandsontableData[idx][2];
                       if (typeVal == _loginUserLanguageResource.rodStringTypeValue1) Rod.Type = 1;
                       else if (typeVal == _loginUserLanguageResource.rodStringTypeValue2) Rod.Type = 2;
                       else if (typeVal == _loginUserLanguageResource.rodStringTypeValue3) Rod.Type = 3;

                       Rod.Grade = productionHandsontableData[idx+1][2];
                       Rod.OutsideDiameter = parseFloat(productionHandsontableData[idx+2][2]) * 0.001;
                       if (isNumber(parseFloat(productionHandsontableData[idx+3][2]))) {
                           Rod.InsideDiameter = parseFloat(productionHandsontableData[idx+3][2]) * 0.001;
                       }
                       Rod.Length = parseFloat(productionHandsontableData[idx+4][2]);
                       deviceProductionData.RodString.EveryRod.push(Rod);
                   }
               }

               deviceProductionData.ManualIntervention = {};
               manualInterventionResultName = isNotVal(productionHandsontableData[40][2]) ? productionHandsontableData[40][2] : '';
               if (isNumber(parseFloat(productionHandsontableData[41][2]))) {
                   deviceProductionData.ManualIntervention.NetGrossRatio = parseFloat(productionHandsontableData[41][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[42][2]))) {
                   deviceProductionData.ManualIntervention.NetGrossValue = parseFloat(productionHandsontableData[42][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[43][2]))) {
                   deviceProductionData.ManualIntervention.LevelCorrectValue = parseFloat(productionHandsontableData[43][2]);
               }

               deviceProductionData.FESDiagram = {};
               deviceProductionData.FESDiagram.Src = 0;
               FESDiagramSrcName = isNotVal(productionHandsontableData[44][2]) ? productionHandsontableData[44][2] : '';

           } else if (deviceCalculateDataType == 2) {
               // ★ 指定为转速计产 (PCP)
               deviceProductionData.FluidPVT = {};
               if (applicationScenarios == 1 && isNumber(parseFloat(productionHandsontableData[0][2]))) {
                   deviceProductionData.FluidPVT.CrudeOilDensity = parseFloat(productionHandsontableData[0][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[1][2]))) {
                   deviceProductionData.FluidPVT.WaterDensity = parseFloat(productionHandsontableData[1][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[2][2]))) {
                   deviceProductionData.FluidPVT.NaturalGasRelativeDensity = parseFloat(productionHandsontableData[2][2]);
               }
               if (applicationScenarios == 1 && isNumber(parseFloat(productionHandsontableData[3][2]))) {
                   deviceProductionData.FluidPVT.SaturationPressure = parseFloat(productionHandsontableData[3][2]);
               }

               deviceProductionData.Reservoir = {};
               if (isNumber(parseFloat(productionHandsontableData[4][2]))) {
                   deviceProductionData.Reservoir.Depth = parseFloat(productionHandsontableData[4][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[5][2]))) {
                   deviceProductionData.Reservoir.Temperature = parseFloat(productionHandsontableData[5][2]);
               }

               deviceProductionData.Production = {};
               if (isNumber(parseFloat(productionHandsontableData[6][2]))) {
                   deviceProductionData.Production.TubingPressure = parseFloat(productionHandsontableData[6][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[7][2]))) {
                   deviceProductionData.Production.CasingPressure = parseFloat(productionHandsontableData[7][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[8][2]))) {
                   deviceProductionData.Production.WellHeadTemperature = parseFloat(productionHandsontableData[8][2]);
               }
               if (applicationScenarios == 0) {
                   deviceProductionData.Production.WaterCut = 100;
               } else if (applicationScenarios == 1 && isNumber(parseFloat(productionHandsontableData[9][2]))) {
                   deviceProductionData.Production.WaterCut = parseFloat(productionHandsontableData[9][2]);
               } else if (applicationScenarios == 2 && isNumber(parseFloat(productionHandsontableData[9][2]))) {
                   deviceProductionData.Production.WaterCut = 100;
               }
               if (applicationScenarios == 1 && isNumber(parseFloat(productionHandsontableData[10][2]))) {
                   deviceProductionData.Production.ProductionGasOilRatio = parseFloat(productionHandsontableData[10][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[11][2]))) {
                   deviceProductionData.Production.ProducingfluidLevel = parseFloat(productionHandsontableData[11][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[12][2]))) {
                   deviceProductionData.Production.PumpSettingDepth = parseFloat(productionHandsontableData[12][2]);
               }

               deviceProductionData.Pump = {};
               if (isNumber(parseFloat(productionHandsontableData[13][2]))) {
                   deviceProductionData.Pump.BarrelLength = parseFloat(productionHandsontableData[13][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[14][2]))) {
                   deviceProductionData.Pump.BarrelSeries = parseFloat(productionHandsontableData[14][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[15][2]))) {
                   deviceProductionData.Pump.RotorDiameter = parseFloat(productionHandsontableData[15][2]) * 0.001;
               }
               if (isNumber(parseFloat(productionHandsontableData[16][2]))) {
                   deviceProductionData.Pump.QPR = parseFloat(productionHandsontableData[16][2]);
               }

               deviceProductionData.TubingString = {};
               deviceProductionData.TubingString.EveryTubing = [];
               var EveryTubing2 = {};
               if (isNumber(parseFloat(productionHandsontableData[17][2]))) {
                   EveryTubing2.InsideDiameter = parseFloat(productionHandsontableData[17][2]) * 0.001;
               }
               deviceProductionData.TubingString.EveryTubing.push(EveryTubing2);

               deviceProductionData.CasingString = {};
               deviceProductionData.CasingString.EveryCasing = [];
               var EveryCasing2 = {};
               if (isNumber(parseFloat(productionHandsontableData[18][2]))) {
                   EveryCasing2.InsideDiameter = parseFloat(productionHandsontableData[18][2]) * 0.001;
               }
               deviceProductionData.CasingString.EveryCasing.push(EveryCasing2);

               deviceProductionData.RodString = {};
               deviceProductionData.RodString.EveryRod = [];

               var rodStartIndexes2 = [19, 24, 29, 34];
               for (var r = 0; r < 4; r++) {
                   var idx = rodStartIndexes2[r];
                   if (isNotVal(productionHandsontableData[idx][2]) &&
                       isNotVal(productionHandsontableData[idx+1][2]) &&
                       isNumber(parseFloat(productionHandsontableData[idx+2][2])) &&
                       (productionHandsontableData[idx+3][2] == '' || isNumber(parseFloat(productionHandsontableData[idx+3][2]))) &&
                       isNumber(parseFloat(productionHandsontableData[idx+4][2]))) {
                       
                       var Rod2 = {};
                       var typeVal2 = productionHandsontableData[idx][2];
                       if (typeVal2 == _loginUserLanguageResource.rodStringTypeValue1) Rod2.Type = 1;
                       else if (typeVal2 == _loginUserLanguageResource.rodStringTypeValue2) Rod2.Type = 2;
                       else if (typeVal2 == _loginUserLanguageResource.rodStringTypeValue3) Rod2.Type = 3;

                       Rod2.Grade = productionHandsontableData[idx+1][2];
                       Rod2.OutsideDiameter = parseFloat(productionHandsontableData[idx+2][2]) * 0.001;
                       if (isNumber(parseFloat(productionHandsontableData[idx+3][2]))) {
                           Rod2.InsideDiameter = parseFloat(productionHandsontableData[idx+3][2]) * 0.001;
                       }
                       Rod2.Length = parseFloat(productionHandsontableData[idx+4][2]);
                       deviceProductionData.RodString.EveryRod.push(Rod2);
                   }
               }

               deviceProductionData.ManualIntervention = {};
               if (isNumber(parseFloat(productionHandsontableData[39][2]))) {
                   deviceProductionData.ManualIntervention.NetGrossRatio = parseFloat(productionHandsontableData[39][2]);
               }
               if (isNumber(parseFloat(productionHandsontableData[40][2]))) {
                   deviceProductionData.ManualIntervention.NetGrossValue = parseFloat(productionHandsontableData[40][2]);
               }
           }
       }
       
       if (deviceCalculateDataType == 1) {
           productionInfoList.push(JSON.stringify(deviceProductionData));
           productionInfoList.push(manualInterventionResultName);
           productionInfoList.push(applicationScenarios);
           productionInfoList.push(FESDiagramSrcName);
       } else {
           productionInfoList.push(JSON.stringify(deviceProductionData));
           productionInfoList.push(applicationScenarios);
       }
   } else {
       productionInfoList.push(applicationScenarios);
   }
   deviceAdditionalInformationData.data = JSON.stringify(productionInfoList);
} 
else if (additionalInformationType === 32) { // 生产数据配置 (抽油机数据)
   var manufacturer = '', model = '', stroke = '', balanceInfo = {};
   if (pumpingInfoHandsontableHelper != null && pumpingInfoHandsontableHelper.hot != undefined) {
       manufacturer = pumpingInfoHandsontableHelper.hot.getDataAtRowProp(1, 'itemValue2');
       model = pumpingInfoHandsontableHelper.hot.getDataAtRowProp(2, 'itemValue2');
       stroke = pumpingInfoHandsontableHelper.hot.getDataAtRowProp(3, 'itemValue2');
       
       var pumpingData = pumpingInfoHandsontableHelper.hot.getData();
       balanceInfo.EveryBalance = [];
       for (var i = 6; i < pumpingData.length; i++) {
           if (isNotVal(pumpingData[i][1]) || isNotVal(pumpingData[i][2])) {
               var EveryBalance = {};
               if (isNotVal(pumpingData[i][1])) EveryBalance.Position = pumpingData[i][1];
               if (isNotVal(pumpingData[i][2])) EveryBalance.Weight = pumpingData[i][2];
               balanceInfo.EveryBalance.push(EveryBalance);
           }
       }
   }
   var productionInfoList32 = [manufacturer, model, stroke, JSON.stringify(balanceInfo)];
   deviceAdditionalInformationData.data = JSON.stringify(productionInfoList32);
} 
else if (additionalInformationType === 4) { // 功图构建
   var FSDiagramConstructionData = {};
   if (fsDiagramConstructionHandsontableHelper != null && fsDiagramConstructionHandsontableHelper.hot != undefined) {
       var rowCount = fsDiagramConstructionHandsontableHelper.hot.countRows();
       for (var i = 0; i < rowCount; i++) {
           var itemCode = fsDiagramConstructionHandsontableHelper.hot.getDataAtRowProp(i, 'itemCode');
           var itemValue = fsDiagramConstructionHandsontableHelper.hot.getDataAtRowProp(i, 'itemValue');
           if(itemCode != null){
               var upperCode = itemCode.toUpperCase();
               if(upperCode == "BOARDDATASOURCE"){
                   if(itemValue==_loginUserLanguageResource.boardDataSource1) FSDiagramConstructionData.boardDataSource=1;
                   else if(itemValue==_loginUserLanguageResource.boardDataSource2) FSDiagramConstructionData.boardDataSource=2;
                   else if(itemValue==_loginUserLanguageResource.boardDataSource3) FSDiagramConstructionData.boardDataSource=3;
               } else if(upperCode == "CRANKDINITANGLE" && isNumber(parseFloat(itemValue))){
                   FSDiagramConstructionData.CrankDIInitAngle=parseFloat(itemValue);
               } else if(upperCode == "INTERPOLATIONCNT" && isNumber(parseFloat(itemValue))){
                   FSDiagramConstructionData.InterpolationCNT=parseFloat(itemValue);
               } else if(upperCode == "SURFACESYSTEMEFFICIENCY" && isNumber(parseFloat(itemValue))){
                   FSDiagramConstructionData.SurfaceSystemEfficiency=parseFloat(itemValue);
               } else if(upperCode == "WATTTIMES" && isNumber(parseFloat(itemValue))){
                   FSDiagramConstructionData.WattTimes=parseFloat(itemValue);
               } else if(upperCode == "ITIMES" && isNumber(parseFloat(itemValue))){
                   FSDiagramConstructionData.ITimes=parseFloat(itemValue);
               } else if(upperCode == "FSDIAGRAMTIMES" && isNumber(parseFloat(itemValue))){
                   FSDiagramConstructionData.FSDiagramTimes=parseFloat(itemValue);
               } else if(upperCode == "FSDIAGRAMLEFTTIMES" && isNumber(parseFloat(itemValue))){
                   FSDiagramConstructionData.FSDiagramLeftTimes=parseFloat(itemValue);
               } else if(upperCode == "FSDIAGRAMRIGHTTIMES" && isNumber(parseFloat(itemValue))){
                   FSDiagramConstructionData.FSDiagramRightTimes=parseFloat(itemValue);
               } else if(upperCode == "LEFTPERCENT" && isNumber(parseFloat(itemValue))){
                   FSDiagramConstructionData.LeftPercent=parseFloat(itemValue);
               } else if(upperCode == "RIGHTPERCENT" && isNumber(parseFloat(itemValue))){
                   FSDiagramConstructionData.RightPercent=parseFloat(itemValue);
               } else if(upperCode == "POSITIVEXWATT" && isNumber(parseFloat(itemValue))){
                   FSDiagramConstructionData.PositiveXWatt=parseFloat(itemValue);
               } else if(upperCode == "NEGATIVEXWATT" && isNumber(parseFloat(itemValue))){
                   FSDiagramConstructionData.NegativeXWatt=parseFloat(itemValue);
               } else if(upperCode == "PRTFSRC"){
                   if(itemValue==_loginUserLanguageResource.PRTFSrc1) FSDiagramConstructionData.PRTFSrc=1;
                   else if(itemValue==_loginUserLanguageResource.PRTFSrc2) FSDiagramConstructionData.PRTFSrc=2;
               }
           }
       }
   }
   deviceAdditionalInformationData.data = JSON.stringify(FSDiagramConstructionData);
} 
else if (additionalInformationType === 5) { // 智能变频
   var frequencyConversionData = {};
   if (deviceIntelligentFrequencyConversionHandsontableHelper != null && deviceIntelligentFrequencyConversionHandsontableHelper.hot != undefined) {
       var freqData = deviceIntelligentFrequencyConversionHandsontableHelper.hot.getData();
       
       frequencyConversionData.FullnessCoefficientModel = {};
       frequencyConversionData.FullnessCoefficientModel.Enable = freqData[0][4] ? 1 : 0;
       frequencyConversionData.FullnessCoefficientModel.FrequencyUpscaling = {};
       if(isNumber(parseFloat(freqData[1][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyUpscaling.FullnessCoefficientLimit=parseFloat(freqData[1][4]);
       if(isNumber(parseFloat(freqData[2][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyUpscaling.FrequencyUpperLimit=parseFloat(freqData[2][4]);
       if(isNumber(parseFloat(freqData[3][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyUpscaling.StepSize=parseFloat(freqData[3][4]);
       if(isNumber(parseFloat(freqData[4][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyUpscaling.StabilityDuration=parseFloat(freqData[4][4]);
       
       frequencyConversionData.FullnessCoefficientModel.FrequencyReduction = {};
       if(isNumber(parseFloat(freqData[5][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyReduction.FullnessCoefficientLimit=parseFloat(freqData[5][4]);
       if(isNumber(parseFloat(freqData[6][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyReduction.FrequencyLowerLimit=parseFloat(freqData[6][4]);
       if(isNumber(parseFloat(freqData[7][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyReduction.StepSize=parseFloat(freqData[7][4]);
       if(isNumber(parseFloat(freqData[8][4]))) frequencyConversionData.FullnessCoefficientModel.FrequencyReduction.StabilityDuration=parseFloat(freqData[8][4]);
       
       frequencyConversionData.RodStressModel = {};
       frequencyConversionData.RodStressModel.Enable = freqData[9][4] ? 1 : 0;
       if(isNumber(parseFloat(freqData[10][4]))) frequencyConversionData.RodStressModel.MaxRodStressRatio=parseFloat(freqData[10][4]);
       if(isNumber(parseFloat(freqData[11][4]))) frequencyConversionData.RodStressModel.RodStressRangeRatio=parseFloat(freqData[11][4]);
       if(isNumber(parseFloat(freqData[12][4]))) frequencyConversionData.RodStressModel.FrequencyLowerLimit=parseFloat(freqData[12][4]);
       if(isNumber(parseFloat(freqData[13][4]))) frequencyConversionData.RodStressModel.StepSize=parseFloat(freqData[13][4]);
       
       frequencyConversionData.FSDiagramWorkTypeEnable = {};
       var codes = [1201,1202,1203,1204,1205,1206,1207,1208,1209,1210,1212,1213,1214,1215,1216,1217,1218,1219,1220,1221,1222,1223,1224,1225,1226,1227,1230,1232];
       for(var k=0; k<codes.length; k++){
           var rowIndex = 14 + k;
           if(rowIndex < freqData.length){
               frequencyConversionData.FSDiagramWorkTypeEnable['FSDiagramWorkType'+codes[k]] = freqData[rowIndex][4] ? 1 : 0;
           }
       }
   }
   deviceAdditionalInformationData.data = JSON.stringify(frequencyConversionData);
} 
else if (additionalInformationType === 8) { // 联锁保护
   var interlockProtectionData = {};
   if (deviceInterlockProtectionHandsontableHelper != null && deviceInterlockProtectionHandsontableHelper.hot != undefined) {
       var interlockData = deviceInterlockProtectionHandsontableHelper.hot.getData();
       interlockProtectionData.Enable = interlockData[0][3] ? 1 : 0;
       
       interlockProtectionData.FSDiagramWorkTypeEnable = {};
       var codes = [1201,1202,1203,1204,1205,1206,1207,1208,1209,1210,1212,1213,1214,1215,1216,1217,1218,1219,1220,1221,1222,1223,1224,1225,1226,1227,1230,1232];
       for(var k=0; k<codes.length; k++){
           var rowIndex = 1 + k;
           if(rowIndex < interlockData.length){
               interlockProtectionData.FSDiagramWorkTypeEnable['FSDiagramWorkType'+codes[k]] = interlockData[rowIndex][3] ? 1 : 0;
           }
       }
   }
   deviceAdditionalInformationData.data = JSON.stringify(interlockProtectionData);
}

return deviceAdditionalInformationData;
}