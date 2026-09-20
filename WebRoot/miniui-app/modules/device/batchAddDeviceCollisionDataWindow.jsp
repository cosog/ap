<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#f5f5f5; }
        /* 统一处理 mini-panel 内部容器的高度撑满，保证 Handsontable 渲染正常 */
        .mini-panel-body { padding:0 !important; overflow:hidden; }
    </style>
</head>
<body>

<!-- 全局变量接收参数 -->
<script>
    var deviceType = '';
    var orgId = '';
    var dictDeviceType = '';
</script>

<!-- 整体采用 Splitter 垂直布局 -->
<div class="mini-splitter" style="width:100%;height:100%;" vertical="true">

    <!-- 顶部：全局工具栏（封装在一个 mini-panel 中） -->
    <div size="45px" showCollapseButton="false" id="toolbarPane">
        <div id="toolbarPanel" class="mini-panel" style="width:100%;height:100%;" 
             showHeader="false" showToolbar="true" showCollapseButton="false" showCloseButton="false" 
             bodyStyle="padding:0;background:#fafafa;">
            <div property="toolbar">
                <table style="width:100%;border-collapse:collapse;">
                    <tr>
                        <td style="padding:0;vertical-align:middle;white-space:nowrap;">
                            <span id="batchAddDeviceCollisionInfoLabel_Id"></span>
                        </td>
                        <td style="padding:0;vertical-align:middle;text-align:right;white-space:nowrap;">
                            <button id="btnCollisionSave" class="mini-button" iconCls="save" onclick="onCollisionSave()"></button>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <!-- 上半部分：数据冲突 -->
    <div size="50%" showCollapseButton="false" minSize="200" id="collisionPane">
        <div id="collisionPanel" class="mini-panel" style="width:100%;height:100%;" 
             showHeader="true" showToolbar="false" showCollapseButton="false" showCloseButton="false" 
             bodyStyle="padding:0;overflow:hidden;">
            <div id="BatchAddDeviceCollisionDataTableDiv_Id" style="width:100%;height:100%;"></div>
        </div>
    </div>

    <!-- 下半部分：数据覆盖 -->
    <div size="50%" showCollapseButton="true" collapseDirection="bottom" minSize="200" id="overlayPane">
        <div id="overlayPanel" class="mini-panel" style="width:100%;height:100%;" 
             showHeader="true" showToolbar="false" showCollapseButton="false" showCloseButton="false" 
             bodyStyle="padding:0;overflow:hidden;">
            <div id="BatchAddDeviceOverlayDataTableDiv_Id" style="width:100%;height:100%;"></div>
        </div>
    </div>

</div>

<script>
    var context = '<%=context%>';
    var batchAddDeviceCollisionDataHandsontableHelper = null;
    var batchAddDeviceOverlayDataHandsontableHelper = null;

    // ================================================================
    // 初始化
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        document.title = R.dataCollision;
        mini.get('btnCollisionSave').setText(R.save);
        
        // 动态设置 Panel 的 Title
        mini.get('collisionPanel').setTitle(R.dataCollision);
        mini.get('overlayPanel').setTitle('已有记录(<font color=red>继续保存，表中数据将覆盖已有记录</font>)');
    }

    // ================================================================
    // 父窗口调用：接收数据
    // ================================================================
    function setData(data) {
        if (!data) return;
        
        // 直接通过参数赋值给全局变量
        deviceType = data.deviceType || '';
        orgId = data.orgId || '';
        dictDeviceType = data.dictDeviceType || deviceType;
        var result = data.result;

        var collisionCount = result.collisionCount || 0;
        var overlayCount = result.overlayCount || 0;
        var overCount = result.overCount || 0;

        var info = "";
        if (collisionCount > 0) info += "冲突井数：<font color=red>" + collisionCount + "</font> ";
        if (overlayCount > 0) info += "覆盖井数：<font color=red>" + overlayCount + "</font> ";
        if (overCount > 0) info += "超限井数：<font color=red>" + overCount + "</font>";
        document.getElementById('batchAddDeviceCollisionInfoLabel_Id').innerHTML = info;

        // 动态调整 Splitter 面板的可见性和高度
        var splitter = mini.get('collisionPane').ownerCt; // 获取父级 splitter
        
        if (collisionCount == 0) {
            mini.get('collisionPanel').setVisible(false);
            // 如果冲突为0，让覆盖面板占满剩余空间
            splitter.updatePane(1, { size: '100%' });
        } else {
            mini.get('collisionPanel').setVisible(true);
            CreateAndLoadBatchAddDeviceCollisionDataTable(result);
        }

        if (overlayCount == 0) {
            mini.get('overlayPanel').setVisible(false);
            // 如果覆盖为0，让冲突面板占满剩余空间
            splitter.updatePane(0, { size: '100%' });
        } else {
            mini.get('overlayPanel').setVisible(true);
            CreateAndLoadBatchAddDeviceOverlayDataTable(result);
        }
        
        // 强制 MiniUI 重新计算布局
        setTimeout(function() { mini.layout(); }, 50);
    }

    // ================================================================
    // 加载冲突表格
    // ================================================================
    function CreateAndLoadBatchAddDeviceCollisionDataTable(result) {
        if (batchAddDeviceCollisionDataHandsontableHelper == null || batchAddDeviceCollisionDataHandsontableHelper.hot == undefined) {
            batchAddDeviceCollisionDataHandsontableHelper = BatchAddDeviceCollisionDataHandsontableHelper.createNew("BatchAddDeviceCollisionDataTableDiv_Id");
            buildColumnsAndCreateTable(batchAddDeviceCollisionDataHandsontableHelper, result, result.collisionList);
        } else {
            batchAddDeviceCollisionDataHandsontableHelper.hot.loadData(result.collisionList);
        }
    }

    // ================================================================
    // 加载覆盖表格
    // ================================================================
    function CreateAndLoadBatchAddDeviceOverlayDataTable(result) {
        if (batchAddDeviceOverlayDataHandsontableHelper == null || batchAddDeviceOverlayDataHandsontableHelper.hot == undefined) {
            batchAddDeviceOverlayDataHandsontableHelper = BatchAddDeviceOverlayDataHandsontableHelper.createNew("BatchAddDeviceOverlayDataTableDiv_Id");
            buildColumnsAndCreateTable(batchAddDeviceOverlayDataHandsontableHelper, result, result.overlayList);
        } else {
            batchAddDeviceOverlayDataHandsontableHelper.hot.loadData(result.overlayList);
        }
    }

    // ================================================================
    // 统一构建列配置并创建表（复用逻辑）
    // ================================================================
    function buildColumnsAndCreateTable(helper, result, dataList) {
        var colHeaders = [];
        var columns = [];
        for (var i = 0; i < result.columns.length; i++) {
            var col = result.columns[i];
            var dataIndex = col.dataIndex;
            colHeaders.push(col.header);
            
            var colConfig = { data: dataIndex };
            
            if (dataIndex.toUpperCase() === "ORGNAME") {
                colConfig.allowInvalid = true;
                colConfig.validator = function (val, callback) {
                    return handsontableDataCheck_Org(val, callback, this.row, this.col, helper);
                };
            } else if (dataIndex.toUpperCase() === "LIFTINGTYPENAME") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = (typeof pcpHidden !== 'undefined' && pcpHidden) ? ['抽油机井'] : ['抽油机井', '螺杆泵井'];
            } else if (dataIndex.toUpperCase() === "DEVICETYPENAME") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = result.deviceTypeDropdownData || [];
            } else if (dataIndex.toUpperCase() === "INSTANCENAME") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = result.instanceDropdownData || [];
            } else if (dataIndex.toUpperCase() === "DISPLAYINSTANCENAME") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = result.displayInstanceDropdownData || [];
            } else if (dataIndex.toUpperCase() === "REPORTINSTANCENAME") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = result.reportInstanceDropdownData || [];
            } else if (dataIndex.toUpperCase() === "ALARMINSTANCENAME") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = result.alarmInstanceDropdownData || [];
            } else if (dataIndex.toUpperCase() === "APPLICATIONSCENARIOSNAME") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = result.applicationScenariosDropdownData || [];
            } else if (dataIndex.toUpperCase() === "MANUALINTERVENTIONRESULTNAME") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = result.resultNameDropdownData || [];
            } else if (dataIndex.toUpperCase() === "STATUSNAME") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = [_loginUserLanguageResource.enable, _loginUserLanguageResource.disable];
            } else if (dataIndex.toUpperCase() === "TCPTYPE") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = ['TCP Server', 'TCP Client'];
            } else if (dataIndex.toUpperCase() === "PUMPTYPE") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = ['杆式泵', '管式泵'];
            } else if (dataIndex.toUpperCase() === "BARRELTYPE") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = [_loginUserLanguageResource.barrelType_H, _loginUserLanguageResource.barrelType_L];
            } else if (dataIndex.toUpperCase() === "PUMPGRADE") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = ['1', '2', '3', '4', '5'];
            } else if (dataIndex.toUpperCase() === "RODGRADE1" || dataIndex.toUpperCase() === "RODGRADE2" ||
                       dataIndex.toUpperCase() === "RODGRADE3" || dataIndex.toUpperCase() === "RODGRADE4") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = ['A', 'B', 'C', 'D', 'K', 'KD', 'HL', 'HY'];
            } else if (dataIndex.toUpperCase() === "CRANKROTATIONDIRECTION") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = ['顺时针', '逆时针'];
            } else if (dataIndex.toUpperCase() === "MANUFACTURER") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                var mList = [];
                if (result.pumpingModelInfo && result.pumpingModelInfo.manufacturerList) {
                    for (var m = 0; m < result.pumpingModelInfo.manufacturerList.length; m++) {
                        mList.push(result.pumpingModelInfo.manufacturerList[m].manufacturer);
                    }
                }
                colConfig.source = mList;
            } else if (dataIndex.toUpperCase() === "MODEL") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = [''];
            } else if (dataIndex.toUpperCase() === "STROKE") {
                colConfig.type = 'dropdown'; colConfig.strict = true; colConfig.allowInvalid = false;
                colConfig.source = [''];
            } else if (dataIndex.toUpperCase() === "IPPORT") {
                colConfig.type = 'text'; colConfig.allowInvalid = true;
                colConfig.validator = function (val, callback) {
                    return handsontableDataCheck_IpPort_Nullable(val, callback, this.row, this.col, helper);
                };
            } else if (dataIndex.toUpperCase() === "COMMISSIONINGDATE") {
                colConfig.type = 'intl-date';
                colConfig.dateFormat = { year: 'numeric', month: '2-digit', day: '2-digit' };
                colConfig.locale = 'sv-SE';
            } else {
                var upperData = dataIndex.toUpperCase();
                if (upperData !== "WELLNAME" && upperData !== "SIGNINID" &&
                    upperData !== "VIDEOURL1" && upperData !== "VIDEOKEYNAME1" &&
                    upperData !== "VIDEOURL2" && upperData !== "VIDEOKEYNAME2") {
                    colConfig.type = 'text'; colConfig.allowInvalid = true;
                    colConfig.validator = function (val, callback) {
                        return handsontableDataCheck_Num_Nullable(val, callback, this.row, this.col, helper);
                    };
                }
            }
            columns.push(colConfig);
        }
        
        colHeaders.push(_loginUserLanguageResource.collisionInfo);
        columns.push({ data: 'dataInfo' });

        helper.colHeaders = colHeaders;
        helper.columns = columns;
        
        if (parseInt(deviceType) < 200) {
            helper.pumpingModelInfo = result.pumpingModelInfo || {};
        }
        
        helper.createTable(dataList || []);
    }

    // ================================================================
    // 保存（强制覆盖）
    // ================================================================
    function onCollisionSave() {
        var isCheckout = 0;
        var saveDate = { updatelist: [] };

        // 收集冲突数据
        if (batchAddDeviceCollisionDataHandsontableHelper != null && batchAddDeviceCollisionDataHandsontableHelper.hot != undefined) {
            var data = batchAddDeviceCollisionDataHandsontableHelper.hot.getData();
            for (var i = 0; i < data.length; i++) {
                if (isNotVal(data[i][1])) {
                    var record = {};
                    for (var j = 0; j < batchAddDeviceCollisionDataHandsontableHelper.columns.length; j++) {
                        record[batchAddDeviceCollisionDataHandsontableHelper.columns[j].data] = data[i][j];
                    }
                    record.id = i;
                    saveDate.updatelist.push(record);
                }
            }
        }

        // 收集覆盖数据
        if (batchAddDeviceOverlayDataHandsontableHelper != null && batchAddDeviceOverlayDataHandsontableHelper.hot != undefined) {
            var data = batchAddDeviceOverlayDataHandsontableHelper.hot.getData();
            for (var i = 0; i < data.length; i++) {
                if (isNotVal(data[i][1])) {
                    var record = {};
                    for (var j = 0; j < batchAddDeviceOverlayDataHandsontableHelper.columns.length; j++) {
                        record[batchAddDeviceOverlayDataHandsontableHelper.columns[j].data] = data[i][j];
                    }
                    record.id = i;
                    saveDate.updatelist.push(record);
                }
            }
        }

        mini.mask({ el: document.body, cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });

        $.ajax({
            url: context + '/wellInformationManagerController/batchAddDevice',
            type: 'POST',
            data: { data: JSON.stringify(saveDate), orgId: orgId, deviceType: deviceType, isCheckout: isCheckout },
            dataType: 'json',
            success: function (rdata) {
                mini.unmask(document.body);
                
                if (rdata.success && rdata.collisionCount == 0 && rdata.overlayCount == 0) {
                    window.CloseOwnerWindow('ok');
                    if (window._parentRefreshDeviceList) {
                        window._parentRefreshDeviceList(true);
                    }
                    if (rdata.overCount > 0) {
                        mini.alert("<font color=red>" + rdata.overCount + "</font>" + _loginUserLanguageResource.saveFailed, _loginUserLanguageResource.tip);
                    } else {
                        mini.alert(_loginUserLanguageResource.savedSuccessfully, _loginUserLanguageResource.tip);
                    }
                } else if (rdata.success && (rdata.collisionCount > 0 || rdata.overlayCount > 0)) {
                    // 如果再次出现冲突，就地刷新当前冲突窗口的数据
                    var collisionCount = rdata.collisionCount || 0;
                    var overlayCount = rdata.overlayCount || 0;
                    var overCount = rdata.overCount || 0;
                    
                    var info = "";
                    if (collisionCount > 0) info += "冲突井数：<font color=red>" + collisionCount + "</font> ";
                    if (overlayCount > 0) info += "覆盖井数：<font color=red>" + overlayCount + "</font> ";
                    if (overCount > 0) info += "超限井数：<font color=red>" + overCount + "</font>";
                    document.getElementById('batchAddDeviceCollisionInfoLabel_Id').innerHTML = info;

                    if (collisionCount == 0) {
                        mini.get('collisionPanel').setVisible(false);
                    } else {
                        CreateAndLoadBatchAddDeviceCollisionDataTable(rdata);
                    }

                    if (overlayCount == 0) {
                        mini.get('overlayPanel').setVisible(false);
                    } else {
                        CreateAndLoadBatchAddDeviceOverlayDataTable(rdata);
                    }
                    
                    setTimeout(function() { mini.layout(); }, 50);
                    mini.alert(info, _loginUserLanguageResource.tip);
                } else {
                    mini.alert("<font color=red>" + _loginUserLanguageResource.saveFailed + "</font>", _loginUserLanguageResource.tip);
                }
            },
            error: function () {
                mini.unmask(document.body);
                mini.alert(_loginUserLanguageResource.requestFailed, _loginUserLanguageResource.tip);
            }
        });
    }

    // ================================================================
    // 冲突表格 Helper
    // ================================================================
    var BatchAddDeviceCollisionDataHandsontableHelper = {
        createNew: function (divid) {
            var helper = {};
            helper.hot = ''; helper.divid = divid; helper.colHeaders = []; helper.columns = [];
            helper.pumpingModelInfo = {};
            helper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.color = '#ff0000'; td.style.whiteSpace = 'nowrap'; td.style.overflow = 'hidden'; td.style.textOverflow = 'ellipsis';
            };
            helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.whiteSpace = 'nowrap'; td.style.overflow = 'hidden'; td.style.textOverflow = 'ellipsis';
            };
            helper.createTable = function (data) {
                $('#' + helper.divid).empty();
                var hotElement = document.querySelector('#' + helper.divid);
                helper.hot = new Handsontable(hotElement, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b', theme: 'ht-theme-classic', data: data,
                    hiddenColumns: { columns: [0], indicators: false, copyPasteEnabled: false },
                    columns: helper.columns, stretchH: 'all', autoWrapRow: true, rowHeaders: true, colHeaders: helper.colHeaders,
                    columnSorting: true, allowInsertRow: false,
                    contextMenu: { items: { "copy": { name: _loginUserLanguageResource.contextMenu_copy }, "cut": { name: _loginUserLanguageResource.contextMenu_cut } } },
                    sortIndicator: true, manualColumnResize: true, manualRowResize: true, filters: true, renderAllRows: true, search: true,
                    cells: function (row, col, prop) {
                        var cellProperties = {};
                        var visualColIndex = this.instance.toVisualColumn(col);
                        if (helper.columns[visualColIndex].data.toUpperCase() == 'DATAINFO') {
                            cellProperties.editor = false; cellProperties.renderer = helper.addBoldBg;
                        } else {
                            if (helper.hot != undefined && helper.hot.getDataAtCell != undefined) {
                                var columns = helper.columns;
                                var pumpingManufacturerColIndex = -1, pumpingModelColIndex = -1, pumpingStrokeColIndex = -1, barrelTypeColIndex = -1, pumpGradeColIndex = -1;
                                for(var i=0;i<columns.length;i++){
                                    if(columns[i].data.toUpperCase() === "MODEL") pumpingModelColIndex=i;
                                    else if(columns[i].data.toUpperCase() === "MANUFACTURER") pumpingManufacturerColIndex=i;
                                    else if(columns[i].data.toUpperCase() === "STROKE") pumpingStrokeColIndex=i;
                                    else if(columns[i].data.toUpperCase() === "BARRELTYPE") barrelTypeColIndex=i;
                                    else if(columns[i].data.toUpperCase() === "PUMPGRADE") pumpGradeColIndex=i;
                                }
                                if(visualColIndex==pumpGradeColIndex && barrelTypeColIndex>0){
                                    var barrelType=helper.hot.getDataAtCell(row,barrelTypeColIndex);
                                    if(barrelType==_loginUserLanguageResource.barrelType_H) this.source = ['1','2','3','4','5'];
                                    else if(barrelType==_loginUserLanguageResource.barrelType_L) this.source = ['1','2','3'];
                                    else if(barrelType=='') this.source = ['1','2','3','4','5'];
                                }
                                if((visualColIndex==pumpingModelColIndex || visualColIndex==pumpingStrokeColIndex) && pumpingManufacturerColIndex>=0){
                                    var pumpingModelInfo=helper.pumpingModelInfo;
                                    if(visualColIndex==pumpingModelColIndex){
                                        var pumpingManufacturer=helper.hot.getDataAtCell(row,pumpingManufacturerColIndex);
                                        for(var i=0;i<pumpingModelInfo.manufacturerList.length;i++){
                                            if(pumpingManufacturer==pumpingModelInfo.manufacturerList[i].manufacturer){
                                                var modelList=[];
                                                for(var j=0;j<pumpingModelInfo.manufacturerList[i].modelList.length;j++) modelList.push(pumpingModelInfo.manufacturerList[i].modelList[j].model);
                                                this.source = modelList; break;
                                            }
                                        }
                                    } else if(visualColIndex==pumpingStrokeColIndex){
                                        var pumpingManufacturer=helper.hot.getDataAtCell(row,pumpingManufacturerColIndex);
                                        var pumpingModel=helper.hot.getDataAtCell(row,pumpingModelColIndex);
                                        for(var i=0;i<pumpingModelInfo.manufacturerList.length;i++){
                                            if(pumpingManufacturer==pumpingModelInfo.manufacturerList[i].manufacturer){
                                                for(var j=0;j<pumpingModelInfo.manufacturerList[i].modelList.length;j++){
                                                    if(pumpingModel==pumpingModelInfo.manufacturerList[i].modelList[j].model){
                                                        this.source = pumpingModelInfo.manufacturerList[i].modelList[j].stroke; break;
                                                    }
                                                }
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if(helper.columns[visualColIndex].type == undefined || helper.columns[visualColIndex].type!='dropdown'){
                                cellProperties.renderer = helper.addCellStyle;
                            }
                        }
                        return cellProperties;
                    },
                    afterOnCellMouseOver: function (event, coords, TD) {
                        if (coords.col >= 0 && coords.row >= 0 && helper.hot != undefined) {
                            var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                            if (isNotVal(rawValue)) TD.title = String(rawValue);
                        }
                    }
                });
            };
            return helper;
        }
    };

    // ================================================================
    // 覆盖表格 Helper
    // ================================================================
    var BatchAddDeviceOverlayDataHandsontableHelper = {
        createNew: function (divid) {
            var helper = {};
            helper.hot = ''; helper.divid = divid; helper.colHeaders = []; helper.columns = [];
            helper.pumpingModelInfo = {};
            helper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.color = '#ff0000'; td.style.whiteSpace = 'nowrap'; td.style.overflow = 'hidden'; td.style.textOverflow = 'ellipsis';
            };
            helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.whiteSpace = 'nowrap'; td.style.overflow = 'hidden'; td.style.textOverflow = 'ellipsis';
            };
            helper.createTable = function (data) {
                $('#' + helper.divid).empty();
                var hotElement = document.querySelector('#' + helper.divid);
                helper.hot = new Handsontable(hotElement, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b', theme: 'ht-theme-classic', data: data,
                    hiddenColumns: { columns: [0], indicators: false, copyPasteEnabled: false },
                    columns: helper.columns, stretchH: 'all', autoWrapRow: true, rowHeaders: true, colHeaders: helper.colHeaders,
                    columnSorting: true, allowInsertRow: false,
                    contextMenu: { items: { "copy": { name: _loginUserLanguageResource.contextMenu_copy }, "cut": { name: _loginUserLanguageResource.contextMenu_cut } } },
                    sortIndicator: true, manualColumnResize: true, manualRowResize: true, filters: true, renderAllRows: true, search: true,
                    cells: function (row, col, prop) {
                        var cellProperties = {};
                        var visualColIndex = this.instance.toVisualColumn(col);
                        if (helper.columns[visualColIndex].data.toUpperCase() == 'DATAINFO') {
                            cellProperties.editor = false; cellProperties.renderer = helper.addBoldBg;
                        } else {
                            if(helper.columns[visualColIndex].type == undefined || helper.columns[visualColIndex].type!='dropdown'){
                                cellProperties.renderer = helper.addCellStyle;
                            }
                        }
                        return cellProperties;
                    },
                    afterOnCellMouseOver: function (event, coords, TD) {
                        if (coords.col >= 0 && coords.row >= 0 && helper.hot != undefined) {
                            var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                            if (isNotVal(rawValue)) TD.title = String(rawValue);
                        }
                    }
                });
            };
            return helper;
        }
    };

    $(document).ready(function () {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>