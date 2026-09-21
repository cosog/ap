// ================================================================
// 辅件设备模块 - auxiliaryDeviceManagerInfo.js
// ================================================================

// ---------- 全局 Handsontable 对象 ----------
var auxiliaryDeviceInfoHandsontableHelper = null;
var auxiliaryDeviceDetailsHandsontableHelper = null;
var auxiliaryDevicePRTFHandsontableHelper = null;

// ---------- 全局状态 ----------
var _adTabInfo = null;
var _adLevel1Data = [];
var _adCurrentLevel1 = null;
var _adModuleRight = { viewFlag: 0, editFlag: 0, controlFlag: 0 };

// ---------- 设备列表相关状态 ----------
var _adDeviceSelectRow    = 0;
var _adDeviceSelectEndRow = 0;
var _adSelectedDeviceId   = 0;
var _adDeviceTotalCount   = 0;

// ---------- 当前设备上下文（右侧各表加载时使用） ----------
var _adCurrentDeviceName      = '';
var _adCurrentSpecificType    = 0;

var isInitializing = true;
//---------- PRTF 冲程初始化标志（避免 setValue 触发重复加载） ----------
var _adPRTFStrokeInitializing = false;
// ---------- 类型初始化标志（避免 setValue 触发重复加载） ----------
var _adSpecificTypeInitializing = false;

// ================================================================
// 页面初始化
// ================================================================
function initAuxiliaryDeviceManagerPage() {
    try {
        if (window.parent && window.parent.tabInfo) {
            _adTabInfo = window.parent.tabInfo;
        }
    } catch (e) {
        console.warn('无法获取 tabInfo', e);
    }

    // 模块权限
    _adModuleRight = getRoleModuleRight(
        context + '/roleManagerController/getRoleModuleRight',
        'AuxiliaryDeviceManager'
    ) || { viewFlag: 0, editFlag: 0, controlFlag: 0 };

    _adModuleRight.viewFlag    = parseInt(_adModuleRight.viewFlag)    || 0;
    _adModuleRight.editFlag    = parseInt(_adModuleRight.editFlag)    || 0;
    _adModuleRight.controlFlag = parseInt(_adModuleRight.controlFlag) || 0;
    

    initAdI18n();
    updateAdBtnStatus();
    initAdSpecificType();
    buildAdLevel1Tabs();
    
  //initAdMessageListener();
    onAdRefreshDeviceList();
    
    isInitializing = false;
}

// ================================================================
// 监听主界面消息
// ================================================================
function initAdMessageListener() {
    window.addEventListener('message', function (event) {
        var message = event.data;
        if (!message || !message.action) return;
        switch (message.action) {
            case 'refresh':
                onAdRefreshDeviceList();
                break;
        }
    });
}

// ================================================================
// 国际化
// ================================================================
function initAdI18n() {
    var R = _loginUserLanguageResource;

    // 面板标题
    var deviceListPanel = mini.get('auxiliaryDeviceListPanel');
    if (deviceListPanel) deviceListPanel.setTitle(R.deviceList);

    var detailsPanel = mini.get('auxiliaryDeviceDetailsPanel');
    if (detailsPanel) detailsPanel.setTitle(R.detailedInformation);

    var prtfPanel = mini.get('auxiliaryDevicePRTFPanel');
    if (prtfPanel) prtfPanel.setTitle(R.pumpingUnitPRTF);

    // 工具条标签
    var typeLabel = document.getElementById('adTypeLabel');
    if (typeLabel) typeLabel.textContent = R.type + '：';

    var strokeLabel = document.getElementById('adStrokeLabel');
    if (strokeLabel) strokeLabel.textContent = R.stroke + '：';

    var totalLabel = document.getElementById('adDeviceTotalCountLabel');
    if (totalLabel) totalLabel.textContent = R.totalCount + '：0';

    // 按钮文本
    var btnMap = {
        'adRefreshBtn':        'refresh',
        'adAddDeviceBtn':      'addDevice',
        'adDelDeviceBtn':      'deleteDevice',
        'adSaveDeviceBtn':     'save',
        'adBatchAddDeviceBtn': 'batchAdd',
        'adExportDeviceBtn':   'exportData',
        'adSavePRTFBtn':       'save'
    };
    for (var id in btnMap) {
        var btn = mini.get(id);
        if (btn) btn.setText(R[btnMap[id]]);
    }

    // 冲程下拉框
    var strokeCombo = mini.get('AuxiliaryDevicePumpingUnitPRTFStrokeComb_Id');
    if (strokeCombo) strokeCombo.setEmptyText('--' + R.all + '--');
}

// ================================================================
// 初始化类型单选（抽油机 / 无）
// ================================================================
function initAdSpecificType() {
    var R = _loginUserLanguageResource;
    var rb = mini.get('AuxiliaryDeviceSpecificType_Id');
    if (!rb) return;

    rb.setData([
        { id: 1, text: R.pumping },
        { id: 0, text: R.nothing }
    ]);
    rb.setValue(0);
}

// ================================================================
// 权限控制
// ================================================================
function updateAdBtnStatus() {
    var editFlag = (_adModuleRight.editFlag == 1);

    var btnIds = [
        'adAddDeviceBtn',
        'adDelDeviceBtn',
        'adSaveDeviceBtn',
        'adBatchAddDeviceBtn',
        'adExportDeviceBtn',
        'adSavePRTFBtn'
    ];

    for (var i = 0; i < btnIds.length; i++) {
        var btn = mini.get(btnIds[i]);
        if (btn) btn.setEnabled(editFlag);
    }
}

// ================================================================
// 一级标签（底部）
// ================================================================
function buildAdLevel1Tabs() {
    var container = document.getElementById('level1Footer');
    if (!container) return;
    container.innerHTML = '';

    if (!_adTabInfo || !_adTabInfo.children || _adTabInfo.children.length === 0) {
        var placeholder = document.createElement('span');
        placeholder.className = 'tab-item active';
        placeholder.textContent = '--';
        container.appendChild(placeholder);
        return;
    }

    // 只取 parentId==1 的一级标签
    _adLevel1Data = [];
    for (var i = 0; i < _adTabInfo.children.length; i++) {
        var item = _adTabInfo.children[i];
        if (item.parentId == 1) {
            _adLevel1Data.push(item);
        }
    }

    if (_adLevel1Data.length === 0) {
        var placeholder2 = document.createElement('span');
        placeholder2.className = 'tab-item active';
        placeholder2.textContent = '--';
        container.appendChild(placeholder2);
        return;
    }

    for (var j = 0; j < _adLevel1Data.length; j++) {
        (function (idx) {
            var tab = _adLevel1Data[idx];
            var span = document.createElement('span');
            span.className = 'tab-item' + (idx === 0 ? ' active' : '');
            span.textContent = tab.text;
            span.onclick = function () { selectAdLevel1(idx); };
            container.appendChild(span);
        })(j);
    }

    // 默认选中第一个
    selectAdLevel1(0);
}

function selectAdLevel1(index) {
    if (index < 0 || index >= _adLevel1Data.length) return;
    _adCurrentLevel1 = _adLevel1Data[index];

    var container = document.getElementById('level1Footer');
    var tabs = container.querySelectorAll('.tab-item');
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
    }

    // 记录当前设备类型
    var hidden = document.getElementById('adCurrentDeviceTypeId');
    if (!hidden) {
        hidden = document.createElement('input');
        hidden.type = 'hidden';
        hidden.id = 'adCurrentDeviceTypeId';
        document.body.appendChild(hidden);
    }
    hidden.value = _adCurrentLevel1.deviceTypeId || '';

    // ★ 重置右侧设备上下文
    _adSelectedDeviceId        = 0;
    _adCurrentDeviceName       = '';
    _adCurrentSpecificType     = 0;
    _adDeviceSelectRow         = '';
    _adDeviceSelectEndRow      = '';

    // ★ 加载设备列表
    CreateAndLoadAuxiliaryDeviceInfoTable(true);
}

// ================================================================
// 获取当前设备类型
// ================================================================
function getAdCurrentDeviceType() {
    if (_adCurrentLevel1 && _adCurrentLevel1.deviceTypeId) {
        return _adCurrentLevel1.deviceTypeId;
    }
    return '';
}

// ================================================================
// 刷新设备列表
// ================================================================
function onAdRefreshDeviceList() {
    CreateAndLoadAuxiliaryDeviceInfoTable(true);
}

// ================================================================
// 加载辅件设备列表
// ================================================================
function CreateAndLoadAuxiliaryDeviceInfoTable(isNew) {
    if (isNew && auxiliaryDeviceInfoHandsontableHelper != null) {
        if (auxiliaryDeviceInfoHandsontableHelper.hot != undefined) {
            auxiliaryDeviceInfoHandsontableHelper.hot.destroy();
        }
        auxiliaryDeviceInfoHandsontableHelper = null;
    }

    var deviceType = getAdCurrentDeviceType();

    var maskEl = 'AuxiliaryDeviceTableDiv_id';
    mini.mask({
        el: maskEl,
        cls: 'mini-mask-loading',
        html: _loginUserLanguageResource.loadingData
    });

    $.ajax({
        url: context + '/wellInformationManagerController/doAuxiliaryDeviceShow',
        type: 'POST',
        data: {
            deviceType: deviceType,
            recordCount: 50,
            page: 1,
            limit: 10000
        },
        dataType: 'json',
        success: function (result) {
            mini.unmask(maskEl);

            if (auxiliaryDeviceInfoHandsontableHelper == null
                || auxiliaryDeviceInfoHandsontableHelper.hot == null
                || auxiliaryDeviceInfoHandsontableHelper.hot == undefined) {

                auxiliaryDeviceInfoHandsontableHelper = AuxiliaryDeviceInfoHandsontableHelper.createNew('AuxiliaryDeviceTableDiv_id');

                var R = _loginUserLanguageResource;
                var colHeaders = [R.idx, R.type, R.deviceName, R.manufacturer, R.model, R.remark, R.sequenceNumber];
                var columns = [
                    { data: 'id' },
                    { data: 'specificType' },
                    { data: 'name' },
                    { data: 'manufacturer' },
                    { data: 'model' },
                    { data: 'remark' },
                    {
                        data: 'sort', type: 'text', allowInvalid: true,
                        validator: function (val, callback) {
                            return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, auxiliaryDeviceInfoHandsontableHelper);
                        }
                    }
                ];
                auxiliaryDeviceInfoHandsontableHelper.colHeaders = colHeaders;
                auxiliaryDeviceInfoHandsontableHelper.columns = columns;

                auxiliaryDeviceInfoHandsontableHelper.createTable(result.totalRoot || []);
            } else {
                auxiliaryDeviceInfoHandsontableHelper.hot.loadData(result.totalRoot || []);
            }

            // ---------- 默认选中 ----------
            if (!result.totalRoot || result.totalRoot.length == 0) {
                _adDeviceSelectRow    = '';
                _adDeviceSelectEndRow = '';
                _adSelectedDeviceId   = 0;
                _adCurrentDeviceName  = '';
                _adCurrentSpecificType = 0;

                auxiliaryDeviceInfoHandsontableHelper.hot.selectCell(0, 'name');
                // ★ 清空右侧
                _adSelectedDeviceId    = 0;
                _adCurrentDeviceName   = '';
                _adCurrentSpecificType = 0;
                CreateAndLoadAuxiliaryDeviceDetailsTable(0, 0, '');
            } else {
                _adDeviceSelectRow    = 0;
                _adDeviceSelectEndRow = 0;

                auxiliaryDeviceInfoHandsontableHelper.hot.selectCell(0, 'name');

                var recordId     = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRowProp(0, 'id');
                var name         = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRowProp(0, 'name');
                var specificType = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRowProp(0, 'specificType');

                _adSelectedDeviceId    = recordId;
                _adCurrentDeviceName   = name;
                _adCurrentSpecificType = specificType;

                // ★ 加载右侧详细信息 / PRTF
                CreateAndLoadAuxiliaryDeviceDetailsTable(recordId, specificType, name);
            }

            // ---------- 总数 ----------
            _adDeviceTotalCount = result.totalCount;
            var totalLabel = document.getElementById('adDeviceTotalCountLabel');
            if (totalLabel) {
                totalLabel.textContent = _loginUserLanguageResource.totalCount + '：' + result.totalCount;
            }

            auxiliaryDeviceInfoHandsontableHelper.hot.render();
        },
        error: function () {
            mini.unmask(maskEl);
            mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
        }
    });
}

// ================================================================
// AuxiliaryDeviceInfoHandsontableHelper
// ================================================================
var AuxiliaryDeviceInfoHandsontableHelper = {
    createNew: function (divid) {
        var helper = {};
        helper.hot = '';
        helper.divid = divid;
        helper.validresult = true;
        helper.colHeaders = [];
        helper.columns = [];
        helper.AllData = {};
        helper.updatelist = [];
        helper.delidslist = [];
        helper.insertlist = [];
        helper.editNameList = [];

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
                hiddenColumns: { columns: [0, 1], indicators: false },
                columns: helper.columns,
                stretchH: 'all',
                autoWrapRow: true,
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
                    var editFlag = parseInt(_adModuleRight.editFlag);
                    if (editFlag != 1) {
                        cellProperties.editor = false;
                    }
                    cellProperties.renderer = helper.addCellStyle;
                    return cellProperties;
                },
                afterSelectionEnd: function (row, column, row2, column2, preventScrolling, selectionLayerLevel) {
                    if (row < 0 && row2 < 0) {
                        _adDeviceSelectRow    = '';
                        _adDeviceSelectEndRow = '';
                        _adSelectedDeviceId   = 0;
                        _adCurrentDeviceName  = '';
                        _adCurrentSpecificType = 0;
                        // ★ 清空右侧
                        CreateAndLoadAuxiliaryDeviceDetailsTable(0, 0, '');
                    } else {
                        if (row < 0) row = 0;
                        if (row2 < 0) row2 = 0;
                        var startRow = row;
                        var endRow = row2;
                        if (row > row2) {
                            startRow = row2;
                            endRow = row;
                        }

                        _adDeviceSelectRow    = startRow;
                        _adDeviceSelectEndRow = endRow;

                        var recordId     = helper.hot.getDataAtRowProp(startRow, 'id');
                        var name         = helper.hot.getDataAtRowProp(startRow, 'name');
                        var specificType = helper.hot.getDataAtRowProp(startRow, 'specificType');

                        _adSelectedDeviceId    = recordId;
                        _adCurrentDeviceName   = name;
                        _adCurrentSpecificType = specificType;

                        // ★ 加载右侧详细信息 / PRTF
                        CreateAndLoadAuxiliaryDeviceDetailsTable(recordId, specificType, name);
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

                        if ("edit" == source && prop == "name") {
                            var data = '{"oldName":"' + oldValue + '","newName":"' + newValue + '"}';
                            helper.editNameList.push(JSON.parse(data));
                        }

                        if (recordId != null && recordId > 0) {
                            var record = {};
                            for (var j = 0; j < helper.columns.length; j++) {
                                record[helper.columns[j].data] = rowdata[j];
                            }
                            helper.updateExpressCount(record);
                        }
                    }
                },
                beforeRemoveRow: function (index, amount) {
                    var ids = [];
                    if (amount != 0) {
                        for (var i = index; i < amount + index; i++) {
                            var rowdata = helper.hot.getDataAtRow(i);
                            ids.push(rowdata[0]);
                        }
                        helper.delExpressCount(ids);
                        helper.screening();
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
            for (var i = 0; i < ids.length; i++) {
                if (ids[i] != null) helper.delidslist.push(ids[i]);
            }
            helper.AllData.delidslist = helper.delidslist;
        };

        helper.updateExpressCount = function (data) {
            if (JSON.stringify(data) != "{}") {
                var flag = true;
                for (var i = 0; i < helper.updatelist.length; i++) {
                    if (helper.updatelist[i].id == data.id) {
                        flag = false;
                        helper.updatelist[i] = data;
                        break;
                    }
                }
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
        
        helper.saveData = function () {
            var auxiliaryDeviceInfoHandsontableData = helper.hot.getData();
            if (auxiliaryDeviceInfoHandsontableData.length == 0) {
                mini.alert(_loginUserLanguageResource.noDataChange, _loginUserLanguageResource.tip);
                return;
            }

            helper.insertExpressCount();

            var auxiliaryDeviceSpecificType = 0;
            var rb = mini.get('AuxiliaryDeviceSpecificType_Id');
            if (rb) {
                auxiliaryDeviceSpecificType = parseInt(rb.getValue()) || 0;
            }

            var DeviceSelectRow = _adDeviceSelectRow;
            if (DeviceSelectRow === '' || DeviceSelectRow == undefined) DeviceSelectRow = 0;
            var rowdata = helper.hot.getDataAtRow(DeviceSelectRow);

            var deviceId = rowdata ? rowdata[0] : 0;
            var manufacturer = rowdata ? rowdata[3] : '';
            var model = rowdata ? rowdata[4] : '';

            var auxiliaryDeviceDetailsSaveData = {};
            auxiliaryDeviceDetailsSaveData.deviceId = deviceId;
            auxiliaryDeviceDetailsSaveData.auxiliaryDeviceSpecificType = auxiliaryDeviceSpecificType;
            auxiliaryDeviceDetailsSaveData.auxiliaryDeviceDetailsList = [];

            if (auxiliaryDeviceDetailsHandsontableHelper != null
                && auxiliaryDeviceDetailsHandsontableHelper.hot != undefined) {
                var detailsData = auxiliaryDeviceDetailsHandsontableHelper.hot.getData();
                for (var i = 0; i < detailsData.length; i++) {
                    if (isNotVal(detailsData[i][1])) {
                        var detail = {};
                        detail.deviceId = deviceId;

                        var itemName = detailsData[i][1];
                        var itemValue = isNotVal(detailsData[i][2]) ? detailsData[i][2] : "";
                        var itemUnit = isNotVal(detailsData[i][3]) ? detailsData[i][3] : "";
                        var itemCode = isNotVal(detailsData[i][4]) ? detailsData[i][4] : "";

                        if (auxiliaryDeviceSpecificType == 1 && itemCode.toUpperCase() == 'structureType'.toUpperCase()) {
                            if (itemValue == _loginUserLanguageResource.pumpingUnitStructureType1) itemValue = 1;
                            else if (itemValue == _loginUserLanguageResource.pumpingUnitStructureType2) itemValue = 2;
                            else if (itemValue == _loginUserLanguageResource.pumpingUnitStructureType3) itemValue = 3;
                        } else if (auxiliaryDeviceSpecificType == 1 && itemCode.toUpperCase() == 'crankRotationDirection'.toUpperCase()) {
                            if (itemValue == _loginUserLanguageResource.clockwise) itemValue = 'Clockwise';
                            else if (itemValue == _loginUserLanguageResource.anticlockwise) itemValue = 'Anticlockwise';
                            else itemValue = '';
                        }

                        detail.itemName = itemName;
                        detail.itemValue = itemValue;
                        detail.itemUnit = itemUnit;
                        detail.itemCode = itemCode;
                        auxiliaryDeviceDetailsSaveData.auxiliaryDeviceDetailsList.push(detail);
                    }
                }
            }

            var deviceType = getAdCurrentDeviceType();
            var maskEl = 'AuxiliaryDeviceTableDiv_id';
            mini.mask({ el: maskEl, cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });

            $.ajax({
                url: context + '/wellInformationManagerController/saveAuxiliaryDeviceHandsontableData',
                type: 'POST',
                data: {
                    deviceId: deviceId,
                    auxiliaryDeviceSpecificType: auxiliaryDeviceSpecificType,
                    data: JSON.stringify(helper.AllData),
                    deviceType: deviceType,
                    auxiliaryDeviceDetailsSaveData: JSON.stringify(auxiliaryDeviceDetailsSaveData)
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
                        helper.clearContainer();
                        CreateAndLoadAuxiliaryDeviceInfoTable(true);
                    } else {
                        mini.alert('<font color=red>' + _loginUserLanguageResource.saveFailed + '</font>', _loginUserLanguageResource.tip);
                    }
                },
                error: function () {
                    mini.unmask(maskEl);
                    mini.alert(_loginUserLanguageResource.requestFailed, _loginUserLanguageResource.tip);
                    helper.clearContainer();
                }
            });
        };

        helper.clearContainer = function () {
            helper.AllData = {};
            helper.updatelist = [];
            helper.delidslist = [];
            helper.insertlist = [];
            helper.editNameList = [];
        };

        return helper;
    }
};

// ================================================================
// 事件占位（后续实现）
// ================================================================
function onAdAddDevice() {
    if (parseInt(_adModuleRight.editFlag) != 1) return;

    var deviceType = getAdCurrentDeviceType();

    mini.open({
        title: _loginUserLanguageResource.addDevice,
        url: context + '/miniui-app/modules/auxiliarydevice/auxiliaryDeviceAddWindow.jsp',
        width: 400,
        height: 380,
        modal: true,
        allowResize: true,
        maxable: false,
        onload: function () {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;

            contentWindow.setData({
                deviceType: deviceType
            });

            contentWindow._parentRefreshDeviceList = function () {
                CreateAndLoadAuxiliaryDeviceInfoTable(true);
            };
        }
    });
}

//================================================================
//删除辅件设备
//================================================================
function onAdDelDevice() {
    if (parseInt(_adModuleRight.editFlag) != 1) return;

    var startRow = _adDeviceSelectRow;
    var endRow   = _adDeviceSelectEndRow;

    if (startRow === '' || endRow === ''
        || startRow == undefined || endRow == undefined) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }

    startRow = parseInt(startRow);
    endRow = parseInt(endRow);
    if (startRow > endRow) {
        var t = startRow; startRow = endRow; endRow = t;
    }

    var delidslist = [];
    var delDeviceNameList = [];
    var delManufacturerList = [];
    var delModelList = [];

    for (var i = startRow; i <= endRow; i++) {
        var deviceId     = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRowProp(i, 'id');
        var name         = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRowProp(i, 'name');
        var manufacturer = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRowProp(i, 'manufacturer');
        var model        = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRowProp(i, 'model');
        if (deviceId != null && parseInt(deviceId) > 0) {
            delidslist.push(deviceId);
            delDeviceNameList.push(name);
            delManufacturerList.push(manufacturer);
            delModelList.push(model);
        }
    }

    if (delidslist.length === 0) {
        mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
        return;
    }

    var deleteInfo = _loginUserLanguageResource.confirmDelete;
    if (delidslist.length === 1) {
        deleteInfo =
            _loginUserLanguageResource.deviceName + ":<font color=red>" + delDeviceNameList[0] + "</font>" +
            "</br>" + _loginUserLanguageResource.manufacturer + ":<font color=red>" + delManufacturerList[0] + "</font>" +
            "</br>" + _loginUserLanguageResource.model + ":<font color=red>" + delModelList[0] + "</font>" +
            "</br>" + _loginUserLanguageResource.confirmDelete;
    } else {
        deleteInfo =
            _loginUserLanguageResource.sparseRecordCount + ":<font color=red>" + delidslist.length + "</font>" +
            "</br>" + _loginUserLanguageResource.confirmDelete;
    }

    mini.confirm(deleteInfo, _loginUserLanguageResource.tip, function (action) {
        if (action !== 'ok') return;

        var saveData = {
            updatelist: [],
            insertlist: [],
            delidslist: delidslist
        };

        var deviceType = getAdCurrentDeviceType();

        mini.mask({ el: 'AuxiliaryDeviceTableDiv_id', cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });

        $.ajax({
            url: context + '/wellInformationManagerController/saveAuxiliaryDeviceHandsontableData',
            type: 'POST',
            data: {
                data: JSON.stringify(saveData),
                deviceType: deviceType
            },
            dataType: 'json',
            success: function (rdata) {
                mini.unmask('AuxiliaryDeviceTableDiv_id');
                if (rdata && rdata.success) {
                    mini.alert(_loginUserLanguageResource.deleteSuccessfully, _loginUserLanguageResource.tip);
                    auxiliaryDeviceInfoHandsontableHelper.clearContainer();
                    _adDeviceSelectRow = '';
                    _adDeviceSelectEndRow = '';
                    CreateAndLoadAuxiliaryDeviceInfoTable(true);
                } else {
                    mini.alert('<font color=red>' + _loginUserLanguageResource.saveFailed + '</font>', _loginUserLanguageResource.tip);
                }
            },
            error: function () {
                mini.unmask('AuxiliaryDeviceTableDiv_id');
                mini.alert(_loginUserLanguageResource.requestFailed, _loginUserLanguageResource.tip);
                auxiliaryDeviceInfoHandsontableHelper.clearContainer();
            }
        });
    });
}

//================================================================
//保存（设备列表 + 详细信息）
//================================================================
function onAdSaveDevice() {
 if (parseInt(_adModuleRight.editFlag) != 1) return;
 if (!auxiliaryDeviceInfoHandsontableHelper) return;

 auxiliaryDeviceInfoHandsontableHelper.saveData();

 onAdSavePRTF();
}

//================================================================
//保存 PRTF
//================================================================
function onAdSavePRTF() {
	if (parseInt(_adModuleRight.editFlag) != 1) return;
	if (auxiliaryDevicePRTFHandsontableHelper != null
	    && auxiliaryDevicePRTFHandsontableHelper.hot != null
	    && auxiliaryDevicePRTFHandsontableHelper.hot != undefined) {
	    auxiliaryDevicePRTFHandsontableHelper.saveData();
	}
}

//================================================================
//批量添加辅件设备
//================================================================
function onAdBatchAddDevice() {
 if (parseInt(_adModuleRight.editFlag) != 1) return;

 var deviceType = getAdCurrentDeviceType();
 var dictDeviceType = deviceType;
 if (dictDeviceType && dictDeviceType.indexOf(',') > -1) {
     dictDeviceType = dictDeviceType.split(',')[0];
 }

 // 组织信息
 var orgId = window.parent && window.parent.getSelectOrgNodeId ? window.parent.getSelectOrgNodeId() : '';
 var orgName = window.parent && window.parent.getSelectOrgNodePath ? window.parent.getSelectOrgNodePath() : '';

 mini.open({
     title: _loginUserLanguageResource.batchAdd,
     url: context + '/miniui-app/modules/auxiliarydevice/batchAddAuxiliaryDeviceWindow.jsp',
     width: 1200,
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

         // 刷新主列表回调
         contentWindow._parentRefreshDeviceList = function () {
             CreateAndLoadAuxiliaryDeviceInfoTable(true);
         };

         // 由主页面打开冲突窗口
         contentWindow._parentOpenCollisionWindow = function (rdata, deviceType, orgId) {
             openBatchAddAuxiliaryDeviceCollisionWindow(rdata, deviceType, orgId);
         };
     }
 });
}

//================================================================
//打开批量添加辅件设备的覆盖数据窗口
//================================================================
function openBatchAddAuxiliaryDeviceCollisionWindow(rdata, deviceType, orgId) {
 mini.open({
     title: _loginUserLanguageResource.exceptionData,
     url: context + '/miniui-app/modules/auxiliarydevice/batchAddAuxiliaryDeviceCollisionDataWindow.jsp',
     width: 1400,
     height: 600,
     modal: true,
     allowResize: true,
     onload: function () {
         var iframe = this.getIFrameEl();
         var contentWindow = iframe.contentWindow;

         contentWindow.setData({
             result: rdata,
             deviceType: deviceType,
             orgId: orgId
         });

         contentWindow._parentRefreshDeviceList = function () {
             CreateAndLoadAuxiliaryDeviceInfoTable(true);
         };
     }
 });
}

//================================================================
//辅件设备完整数据导出
//================================================================
function onAdExportDevice() {
 if (parseInt(_adModuleRight.editFlag) != 1) return;

 // 组织 ID（父窗口）
 var leftOrgId = window.parent && window.parent.mini
     ? window.parent.mini.get('leftOrg_Id').getValue()
     : '';

 // 当前设备类型
 var deviceType = getAdCurrentDeviceType();

 // 国际化文件名
 var fileName = _loginUserLanguageResource.auxiliaryDdeviceExportFileName;

 var url = context + '/wellInformationManagerController/exportAuxiliaryDeviceCompleteData';

 var timestamp = new Date().getTime();
 var key = 'exportAuxiliaryDeviceCompleteData' + deviceType + '_' + timestamp;
 var maskPanelId = 'auxiliaryDevicePanel';

 var param = '&orgId=' + leftOrgId
     + '&deviceType=' + deviceType
     + '&recordCount=10000'
     + '&fileName=' + URLencode(URLencode(fileName))
     + '&key=' + key;

 exportDataMask(key, maskPanelId, _loginUserLanguageResource.loadingData);
 downloadFile(url + '?flag=true' + param);
}

// ================================================================
// 类型切换（抽油机 / 无）
// ================================================================
function onAdSpecificTypeChanged(e) {
	if (_adSpecificTypeInitializing) return;

    var v = parseInt(e.value) || 0;

    // 显示/隐藏 PRTF 面板
    var splitter = mini.get('adRightSplitter');
    if (v === 1) {
        if (splitter) splitter.showPane(2);
    } else {
        if (splitter) splitter.hidePane(2);
    }

    // 从设备列表当前选中行取 deviceId / name
    var deviceId = _adSelectedDeviceId;
    var name = _adCurrentDeviceName;
    if (!deviceId || deviceId <= 0) {
        if (auxiliaryDeviceInfoHandsontableHelper && auxiliaryDeviceInfoHandsontableHelper.hot) {
            var selectRow = _adDeviceSelectRow;
            if (selectRow !== '' && selectRow != undefined) {
                deviceId = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRowProp(selectRow, 'id');
                name     = auxiliaryDeviceInfoHandsontableHelper.hot.getDataAtRowProp(selectRow, 'name');
            }
        }
    }
    if (!deviceId || deviceId <= 0) return;

    // 加载详情
    CreateAuxiliaryDeviceDetailsTable(deviceId, name);

    // 清空冲程下拉框（加标志位避免触发 onvaluechanged）
    var combo = mini.get('AuxiliaryDevicePumpingUnitPRTFStrokeComb_Id');
    if (combo) {
        _adPRTFStrokeInitializing = true;
        combo.setData([]);
        combo.setValue('');
        _adPRTFStrokeInitializing = false;
    }

    // 加载 PRTF
    CreateAndLoadPumpingUnitPTFTable(deviceId, name);
}

// ================================================================
// 冲程下拉框切换
// ================================================================
function onAdPRTFStrokeChanged(e) {
	if (_adPRTFStrokeInitializing) return;

    var deviceId = _adSelectedDeviceId;
    var name     = _adCurrentDeviceName;
    if (!deviceId || deviceId <= 0) return;

    CreateAndLoadPumpingUnitPTFTable(deviceId, name);
}

//================================================================
//中间层：根据设备类型加载详情 / PRTF
//若当前 radiogroup 值与设备 specificType 不同 → 触发 radiogroup change
//   → onAdSpecificTypeChanged 里会加载详情 + PRTF
//若相同 → 直接调用详情 + PRTF
//================================================================
function CreateAndLoadAuxiliaryDeviceDetailsTable(deviceId, specificType, name) {
 var rb = mini.get('AuxiliaryDeviceSpecificType_Id');
 var currentType = rb ? (parseInt(rb.getValue()) || 0) : 0;
 var targetType  = parseInt(specificType) || 0;

 if (targetType != currentType) {
     // setValue 会触发 onvaluechanged → 里面会加载详情 + PRTF
     if (rb) {
         _adSpecificTypeInitializing = true;
         rb.setValue(targetType);
         _adSpecificTypeInitializing = false;
     }
     // 由于加了标志位屏蔽了 onvaluechanged，这里需要主动加载
     var splitter = mini.get('adRightSplitter');
     if (targetType === 1) {
         if (splitter) splitter.showPane(2);
     } else {
         if (splitter) splitter.hidePane(2);
     }

     if (deviceId > 0) {
         CreateAuxiliaryDeviceDetailsTable(deviceId, name);
     }
     var combo = mini.get('AuxiliaryDevicePumpingUnitPRTFStrokeComb_Id');
     if (combo) {
         _adPRTFStrokeInitializing = true;
         combo.setData([]);
         combo.setValue('');
         _adPRTFStrokeInitializing = false;
     }
     if (deviceId > 0) {
         CreateAndLoadPumpingUnitPTFTable(deviceId, name);
     }
 } else {
     // 类型相同，直接加载
     if (deviceId > 0) {
         CreateAuxiliaryDeviceDetailsTable(deviceId, name);
     }
     var combo2 = mini.get('AuxiliaryDevicePumpingUnitPRTFStrokeComb_Id');
     if (combo2) {
         _adPRTFStrokeInitializing = true;
         combo2.setData([]);
         combo2.setValue('');
         _adPRTFStrokeInitializing = false;
     }
     if (deviceId > 0) {
         CreateAndLoadPumpingUnitPTFTable(deviceId, name);
     }
 }
}

//================================================================
//详细信息表
//================================================================
function CreateAuxiliaryDeviceDetailsTable(deviceId, name) {
 // 销毁旧表
 if (auxiliaryDeviceDetailsHandsontableHelper != null) {
     if (auxiliaryDeviceDetailsHandsontableHelper.hot != undefined) {
         auxiliaryDeviceDetailsHandsontableHelper.hot.destroy();
     }
     auxiliaryDeviceDetailsHandsontableHelper = null;
 }

 // 面板标题（带设备名）
 var showInfo = _loginUserLanguageResource.detailedInformation;
 if (isNotVal(name)) {
     showInfo = '【<font color=red>' + name + '</font>】' + showInfo;
 }
 var detailsPanel = mini.get('auxiliaryDeviceDetailsPanel');
 if (detailsPanel) detailsPanel.setTitle(showInfo);

 // 取当前类型
 var rb = mini.get('AuxiliaryDeviceSpecificType_Id');
 var auxiliaryDeviceSpecificType = rb ? (parseInt(rb.getValue()) || 0) : 0;

 var maskEl = 'auxiliaryDeviceDetailsPanel';
 mini.mask({
     el: maskEl,
     cls: 'mini-mask-loading',
     html: _loginUserLanguageResource.loadingData
 });

 $.ajax({
     method: 'POST',
     url: context + '/wellInformationManagerController/getAuxiliaryDeviceDetailsInfo',
     data: {
         deviceId: deviceId,
         auxiliaryDeviceSpecificType: auxiliaryDeviceSpecificType
     },
     dataType: 'json',
     success: function (result) {
         mini.unmask(maskEl);

         if (auxiliaryDeviceDetailsHandsontableHelper == null || auxiliaryDeviceDetailsHandsontableHelper.hot == undefined) {
             auxiliaryDeviceDetailsHandsontableHelper = AuxiliaryDeviceDetailsHandsontableHelper.createNew("AuxiliaryDeviceDetailsTableDiv_id");

             var R = _loginUserLanguageResource;
             var colHeaders = [R.idx, R.variable, R.value, R.unit, 'itemCode'];
             var columns = [
                 { data: 'id' },
                 { data: 'itemName' },
                 { data: 'itemValue' },
                 { data: 'itemUnit' },
                 { data: 'itemCode' }
             ];
             auxiliaryDeviceDetailsHandsontableHelper.colHeaders = colHeaders;
             auxiliaryDeviceDetailsHandsontableHelper.columns = columns;

             if (result.totalRoot.length == 0) {
                 var emptyArr = [];
                 for (var i = 0; i < 20; i++) emptyArr.push({});
                 auxiliaryDeviceDetailsHandsontableHelper.createTable(emptyArr);
             } else {
                 auxiliaryDeviceDetailsHandsontableHelper.createTable(result.totalRoot);
             }
         } else {
             if (result.totalRoot.length == 0) {
                 var emptyArr2 = [];
                 for (var j = 0; j < 20; j++) emptyArr2.push({});
                 auxiliaryDeviceDetailsHandsontableHelper.hot.loadData(emptyArr2);
             } else {
                 auxiliaryDeviceDetailsHandsontableHelper.hot.loadData(result.totalRoot);
             }
         }
     },
     error: function () {
         mini.unmask(maskEl);
         mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
     }
 });
}

var AuxiliaryDeviceDetailsHandsontableHelper = {
 createNew: function (divid) {
     var helper = {};
     helper.hot = '';
     helper.divid = divid;
     helper.colHeaders = [];
     helper.columns = [];

     helper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.backgroundColor = 'rgb(242, 242, 242)';
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.backgroundColor = 'rgb(245, 245, 245)';
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
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
     };

     helper.createTable = function (data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             width: '100%',
             height: '100%',
             hiddenColumns: { columns: [0, 4], indicators: false },
             columns: helper.columns,
             stretchH: 'all',
             autoWrapRow: true,
             rowHeaders: true,
             colHeaders: helper.colHeaders,
             columnSorting: true,
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
                 var visualRowIndex = this.instance.toVisualRow(row);
                 var visualColIndex = this.instance.toVisualColumn(col);
                 var editFlag = parseInt(_adModuleRight.editFlag);

                 if (editFlag != 1) {
                     // 非编辑模式：除第 2 列外全部只读 + 灰色背景
                     cellProperties.editor = false;
                     if (visualColIndex != 2) {
                         cellProperties.renderer = helper.addBoldBg;
                     } else {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 } else {
                     // 编辑模式
                     var rb = mini.get('AuxiliaryDeviceSpecificType_Id');
                     var auxiliaryDeviceSpecificType = rb ? (parseInt(rb.getValue()) || 0) : 0;

                     if (auxiliaryDeviceSpecificType == 1) {
                         // 抽油机类型
                         if (visualColIndex != 2) {
                             cellProperties.editor = false;
                             cellProperties.renderer = helper.addBoldBg;
                         } else {
                             // 结构类型
                             if (visualRowIndex === 0) {
                                 this.type = 'dropdown';
                                 this.source = [
                                     _loginUserLanguageResource.pumpingUnitStructureType1,
                                     _loginUserLanguageResource.pumpingUnitStructureType2,
                                     _loginUserLanguageResource.pumpingUnitStructureType3
                                 ];
                                 this.strict = true;
                                 this.allowInvalid = false;
                             }
                             // 曲柄旋转方向
                             else if (visualRowIndex === 2) {
                                 this.type = 'dropdown';
                                 this.source = [
                                     _loginUserLanguageResource.clockwise,
                                     _loginUserLanguageResource.anticlockwise
                                 ];
                                 this.strict = true;
                                 this.allowInvalid = false;
                             }
                             cellProperties.renderer = helper.addCellStyle;
                         }
                     } else {
                         // 无类型
                         //cellProperties.editor = false;
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 }
                 return cellProperties;
             },
             afterChange: function (changes, source) {
                 if (!changes) return;
                 for (var i = 0; i < changes.length; i++) {
                     var row = changes[i][0];
                     var prop = changes[i][1];
                     var newValue = changes[i][3];
                     var itemCode = helper.hot.getDataAtRowProp(row, 'itemCode');
                     if (prop === 'itemValue' && itemCode == 'stroke') {
                         var combo = mini.get('AuxiliaryDevicePumpingUnitPRTFStrokeComb_Id');
                         if (combo) {
                             var strokeDataArr = (newValue || '').split(',');
                             var strokeCombData = [];
                             for (var k = 0; k < strokeDataArr.length; k++) {
                                 if (strokeDataArr[k]) {
                                     strokeCombData.push({ boxkey: strokeDataArr[k], boxval: strokeDataArr[k] });
                                 }
                             }
                             combo.setData(strokeCombData);
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
//PRTF 表
//================================================================
function CreateAndLoadPumpingUnitPTFTable(deviceId, deviceName) {
 if (auxiliaryDevicePRTFHandsontableHelper != null) {
     if (auxiliaryDevicePRTFHandsontableHelper.hot != undefined) {
         auxiliaryDevicePRTFHandsontableHelper.hot.destroy();
     }
     auxiliaryDevicePRTFHandsontableHelper = null;
 }

 var rb = mini.get('AuxiliaryDeviceSpecificType_Id');
 var auxiliaryDeviceSpecificType = rb ? (parseInt(rb.getValue()) || 0) : 0;

 var splitter = mini.get('adRightSplitter');

 if (auxiliaryDeviceSpecificType == 0) {
     if (splitter) splitter.hidePane(2);
     return;
 } else if (auxiliaryDeviceSpecificType == 1) {
     if (splitter) splitter.showPane(2);
 }

 // 更新 PRTF 面板标题
 var showInfo = _loginUserLanguageResource.pumpingUnitPRTF;
 if (isNotVal(deviceName)) {
     showInfo = '【<font color=red>' + deviceName + '</font>】' + showInfo;
 }
 var prtfPanel = mini.get('auxiliaryDevicePRTFPanel');
 if (prtfPanel) prtfPanel.setTitle(showInfo);

 // 取当前 stroke
 var combo = mini.get('AuxiliaryDevicePumpingUnitPRTFStrokeComb_Id');
 var stroke = combo ? (combo.getValue() || '') : '';

 var maskEl = 'AuxiliaryDevicePumpingUnitPRTFTableDiv_id';
 mini.mask({
     el: maskEl,
     cls: 'mini-mask-loading',
     html: _loginUserLanguageResource.loadingData
 });

 $.ajax({
     method: 'POST',
     url: context + '/wellInformationManagerController/getPumpingPRTFData',
     data: { deviceId: deviceId, stroke: stroke },
     dataType: 'json',
     success: function (result) {
         mini.unmask(maskEl);

         // 加载冲程下拉框数据
         var combo2 = mini.get('AuxiliaryDevicePumpingUnitPRTFStrokeComb_Id');
         if (!isNotVal(stroke) && combo2) {
             var strokeList = result.strokeList || [];
             var comboData = [];
             for (var i = 0; i < strokeList.length; i++) {
                 var s = strokeList[i];
                 var key = (s.length) ? s[0] : s;
                 comboData.push({ boxkey: key, boxval: key });
             }
             _adPRTFStrokeInitializing = true;
             combo2.setData(comboData);
             if (comboData.length > 0) {
                 var currentValue = combo2.getValue();
                 if (!isNotVal(currentValue)) {
                     combo2.setValue(comboData[0].boxkey);
                 }
             }
             _adPRTFStrokeInitializing = false;
         } else if (combo2) {
             // 保持已有列表，只是清除当前选择
             _adPRTFStrokeInitializing = true;
             var currentValue2 = combo2.getValue();
             if (!isNotVal(currentValue2) && result.strokeList && result.strokeList.length > 0) {
                 combo2.setValue(comboData2[0].boxkey);
             }
             _adPRTFStrokeInitializing = false;
         }

         var R = _loginUserLanguageResource;

         if (auxiliaryDevicePRTFHandsontableHelper == null || auxiliaryDevicePRTFHandsontableHelper.hot == undefined) {
             auxiliaryDevicePRTFHandsontableHelper = PumpingUnitPRTFHandsontableHelper.createNew("AuxiliaryDevicePumpingUnitPRTFTableDiv_id");

             var colHeaders = [
                 R.crankAngle + '(°)',
                 R.pumpingUnitPR + '(%)',
                 R.pumpingUnitTF + '(m)'
             ];
             var columns = [
                 {
                     data: 'CrankAngle', type: 'text', allowInvalid: true,
                     validator: function (val, callback) {
                         return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, auxiliaryDevicePRTFHandsontableHelper);
                     }
                 },
                 {
                     data: 'PR', type: 'text', allowInvalid: true,
                     validator: function (val, callback) {
                         return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, auxiliaryDevicePRTFHandsontableHelper);
                     }
                 },
                 {
                     data: 'TF', type: 'text', allowInvalid: true,
                     validator: function (val, callback) {
                         return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, auxiliaryDevicePRTFHandsontableHelper);
                     }
                 }
             ];
             auxiliaryDevicePRTFHandsontableHelper.colHeaders = colHeaders;
             auxiliaryDevicePRTFHandsontableHelper.columns = columns;

             if (!result.totalRoot || result.totalRoot == 0 || result.totalRoot.length == 0) {
                 var emptyArr = [];
                 for (var m = 0; m < 30; m++) emptyArr.push({});
                 auxiliaryDevicePRTFHandsontableHelper.createTable(emptyArr);
             } else {
                 auxiliaryDevicePRTFHandsontableHelper.createTable(result.totalRoot);
             }
         } else {
             if (!result.totalRoot || result.totalRoot == 0 || result.totalRoot.length == 0) {
                 var emptyArr2 = [];
                 for (var n = 0; n < 30; n++) emptyArr2.push({});
                 auxiliaryDevicePRTFHandsontableHelper.hot.loadData(emptyArr2);
             } else {
                 auxiliaryDevicePRTFHandsontableHelper.hot.loadData(result.totalRoot);
             }
         }
     },
     error: function () {
         mini.unmask(maskEl);
         mini.alert(_loginUserLanguageResource.ajaxError, _loginUserLanguageResource.error);
     }
 });
}

var PumpingUnitPRTFHandsontableHelper = {
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
             width: '100%',
             height: '100%',
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
             contextMenu: {
                 items: {
                     "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                     "cut":  { name: _loginUserLanguageResource.contextMenu_cut }
                 }
             },
             cells: function (row, col, prop) {
                 var cellProperties = {};
                 var editFlag = parseInt(_adModuleRight.editFlag);
                 if (editFlag != 1) {
                     cellProperties.editor = false;
                 }
                 cellProperties.renderer = helper.addCellStyle;
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

     // 保存 PRTF
     helper.saveData = function () {
         var selectedDeviceId = _adSelectedDeviceId;
         var combo = mini.get('AuxiliaryDevicePumpingUnitPRTFStrokeComb_Id');
         var stroke = combo ? (combo.getValue() || '') : '';

         if(!isNotVal(stroke)){
        	 return;
         }
         var strokePRTFData = {};
         strokePRTFData.Stroke = stroke;
         strokePRTFData.PRTF = [];

         if (helper.hot) {
             var PRTFData = helper.hot.getData();
             for (var i = 0; i < PRTFData.length; i++) {
                 var CrankAngle = helper.hot.getDataAtRowProp(i, 'CrankAngle');
                 var PR = helper.hot.getDataAtRowProp(i, 'PR');
                 var TF = helper.hot.getDataAtRowProp(i, 'TF');
                 if (isNumber(CrankAngle) && isNumber(PR) && isNumber(TF)) {
                     var PRTF = {};
                     PRTF.CrankAngle = parseFloat(CrankAngle);
                     PRTF.PR = parseFloat(PR);
                     PRTF.TF = parseFloat(TF);
                     strokePRTFData.PRTF.push(PRTF);
                 }
             }
         }

         if (!selectedDeviceId || selectedDeviceId <= 0) {
             mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
             return;
         }

         mini.mask({ el: 'AuxiliaryDevicePumpingUnitPRTFTableDiv_id', cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });

         $.ajax({
             method: 'POST',
             url: context + '/wellInformationManagerController/savePumpingPRTFData',
             data: {
                 data: JSON.stringify(strokePRTFData),
                 deviceId: selectedDeviceId
             },
             dataType: 'json',
             success: function (rdata) {
                 mini.unmask('AuxiliaryDevicePumpingUnitPRTFTableDiv_id');
                 if (rdata && rdata.success) {
                     mini.alert(_loginUserLanguageResource.savedSuccessfully, _loginUserLanguageResource.tip);
                 } else {
                     mini.alert(_loginUserLanguageResource.saveFailed, _loginUserLanguageResource.tip);
                 }
             },
             error: function () {
                 mini.unmask('AuxiliaryDevicePumpingUnitPRTFTableDiv_id');
                 mini.alert(_loginUserLanguageResource.requestFailed, _loginUserLanguageResource.tip);
             }
         });
     };

     helper.clearContainer = function () {
     };

     return helper;
 }
};
