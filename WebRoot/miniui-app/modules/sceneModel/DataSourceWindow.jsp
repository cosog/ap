<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#fff; font-family:"Microsoft YaHei", Arial, sans-serif; }
        .mini-panel-border { border: 0 !important; }
        .mini-panel-header { border-bottom: 1px solid #e8e8e8 !important; background: #fafafa !important; }
        .mini-panel-body { padding: 0 !important; }
        .mini-splitter-border { border: 0 !important; }
        .mini-splitter-pane { padding: 0 !important; }
        .mini-splitter-handler { background: transparent !important; border-left: 1px solid #e8e8e8 !important; }
        
        .footer { padding: 8px 10px; border-top: 1px solid #e8e8e8; background: #fafafa; text-align: right; }
    </style>
</head>
<body>

<div style="width:100%;height:100%;display:flex;flex-direction:column;">
    <div style="flex:1;overflow:hidden;">
        <div id="dsSplitter" class="mini-splitter" style="width:100%;height:100%;" vertical="false">
            <!-- 左侧：设备列表 -->
            <div size="40%" showCollapseButton="false">
                <div id="devicePanel" class="mini-panel" style="width:100%;height:100%;" showHeader="true" showToolbar="false" showCloseButton="false" showCollapseButton="false" bodyStyle="padding:0;">
                    <!-- ★ 去掉静态列，添加事件直接在 HTML 处定义 -->
                    <div id="dsDeviceGrid" class="mini-datagrid" style="width:100%;height:100%;" 
                         allowAlternate="true" showPager="false" allowCellEdit="false" allowCellSelect="false"
                         dataField="totalRoot" totalField="totalCount"
                         onload="onDeviceGridLoad" onselectionchanged="onDeviceSelectionChanged">
                        <div property="columns"></div>
                    </div>
                </div>
            </div>
            <!-- 右侧：数据列 -->
            <div size="60%" showCollapseButton="false">
                <div id="fieldPanel" class="mini-panel" style="width:100%;height:100%;" showHeader="true" showToolbar="false" showCloseButton="false" showCollapseButton="false" bodyStyle="padding:0;">
                    <!-- ★ 去掉静态列，添加双击事件直接确定 -->
                    <div id="dsFieldGrid" class="mini-datagrid" style="width:100%;height:100%;" 
                         allowAlternate="true" showPager="false" allowCellEdit="false" allowCellSelect="false"
                         dataField="totalRoot" totalField="totalCount"
                         ondblclick="onFieldDblClick">
                        <div property="columns"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer">
        <button id="btnConfirm" class="mini-button" iconCls="save" onclick="onConfirm()">确定</button>
        <button id="btnCancel" class="mini-button" iconCls="cancel" onclick="onCancel()">取消</button>
    </div>
</div>

<script>
    var context = '<%=context%>';
    var targetOrgId = '';
    var currentBinding = null;
    var onConfirmCallback = null;

    $(document).ready(function() {
        mini.parse();
        initI18n();
        initGrids();
    });

    function initI18n() {
        document.title = _loginUserLanguageResource.configureDataSource || '配置数据源';
        mini.get('devicePanel').setTitle(_loginUserLanguageResource.deviceList || '设备列表');
        mini.get('fieldPanel').setTitle(_loginUserLanguageResource.dataColumn || '数据列');
        mini.get('btnConfirm').setText(_loginUserLanguageResource.confirm || '确定');
        mini.get('btnCancel').setText(_loginUserLanguageResource.cancel || '取消');
    }

    function initGrids() {
        var devGrid = mini.get('dsDeviceGrid');
        var fieldGrid = mini.get('dsFieldGrid');
        
        devGrid.setUrl(context + '/sceneModelController/getDeviceList');
        fieldGrid.setUrl(context + '/sceneModelController/getDeviceFields');
    }

    // 父页面传参
    function setData(data) {
        targetOrgId = data.targetOrgId || '';
        currentBinding = data.currentBinding || {};
        onConfirmCallback = data.onConfirm;

        var devGrid = mini.get('dsDeviceGrid');
        // 触发请求，带上 orgId
        devGrid.load({ orgId: targetOrgId });
    }

    // ============================================================
    // 设备列表加载完成
    // ============================================================
    function onDeviceGridLoad(e) {
        var devGrid = e.sender;
        
        // 动态创建设备列表列
        if (!devGrid._columnsCreated) {
            createDeviceColumns(devGrid);
            devGrid._columnsCreated = true;
        }
        
        // 处理默认选中
        var data = devGrid.getData();
        if (data.length > 0) {
            var target = data[0];
            // 如果有当前绑定的设备，优先选中
            if (currentBinding && currentBinding.deviceId) {
                for (var i = 0; i < data.length; i++) {
                    if (String(data[i].deviceId) === String(currentBinding.deviceId)) {
                        target = data[i]; break;
                    }
                }
            }
            devGrid.select(target);
            var selectRow = devGrid.indexOf(target);
            setTimeout(function () {
                if (selectRow >= 0) {
                    devGrid.scrollIntoView(selectRow);
                }
            }, 150);
        }
    }

    // ============================================================
    // 设备选中状态变化，加载右侧字段
    // ============================================================
    function onDeviceSelectionChanged(e) {
        var devGrid = e.sender;
        var row = devGrid.getSelected();
        if (row) {
            loadFields(row);
        } else {
            mini.get('dsFieldGrid').setData([]);
        }
    }

    // ============================================================
    // 加载字段列表
    // ============================================================
    function loadFields(device) {
        var fieldGrid = mini.get('dsFieldGrid');
        // 加载字段数据，带上设备参数
        fieldGrid.load({
            deviceId: device.deviceId,
            calculateType: device.calculateType,
            protocolCode: device.protocolCode
        }, function() {
            // 动态创建字段列表列
            if (!fieldGrid._columnsCreated) {
                createFieldColumns(fieldGrid);
                fieldGrid._columnsCreated = true;
            }
            
            // 处理默认选中
            var data = fieldGrid.getData();
            if (data.length > 0) {
                var targetField = data[0];
                // 如果有当前绑定的字段，优先选中 (用 itemCode 匹配)
                if (currentBinding && currentBinding.fieldName) {
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].itemCode === currentBinding.fieldName) {
                            targetField = data[i]; 
                            break;
                        }
                    }
                }
                fieldGrid.select(targetField);
                var selectRow = fieldGrid.indexOf(targetField);
                setTimeout(function () {
                    if (selectRow >= 0) {
                        fieldGrid.scrollIntoView(selectRow);
                    }
                }, 150);
            }
        });
    }

    // ============================================================
    // 动态创建列
    // ============================================================
    function createDeviceColumns(grid) {
        var R = _loginUserLanguageResource;
        var columns = [
        	{ type: 'checkcolumn', width: 40, header: '', headerAlign: 'center', align: 'center' },
        	{ type: 'indexcolumn', width: 45, header: R.idx || '序号', headerAlign: 'center', align: 'center' },
            { field: 'deviceName', width: 100, header: R.deviceName || '设备名称', headerAlign: 'center', align: 'left' }
        ];
        grid.setColumns(columns);
    }

    function createFieldColumns(grid) {
        var R = _loginUserLanguageResource;
        var columns = [
        	{ type: 'checkcolumn', width: 40, header: '', headerAlign: 'center', align: 'center' },
        	{ type: 'indexcolumn', width: 45, header: R.idx || '序号', headerAlign: 'center', align: 'center' },
            { field: 'itemName', width: 150, header: R.dataColumn || '字段名称', headerAlign: 'center', align: 'left' },
            { field: 'unit', width: 80, header: R.unit || '单位', headerAlign: 'center', align: 'center' },
            { field: 'dataSource', width: 100, header: R.columnDataSource || '数据源', headerAlign: 'center', align: 'left' }
        ];
        grid.setColumns(columns);
    }

    // ============================================================
    // 交互与提交
    // ============================================================
    // 双击字段列表直接确定
    function onFieldDblClick(e) {
        var fieldGrid = mini.get('dsFieldGrid');
        var row = fieldGrid.getSelected();
        if (row) onConfirm();
    }

    function onConfirm() {
        var dev = mini.get('dsDeviceGrid').getSelected();
        var field = mini.get('dsFieldGrid').getSelected();
        if (!dev) { mini.alert(_loginUserLanguageResource.selectDevice || '请选择设备'); return; }
        if (!field) { mini.alert(_loginUserLanguageResource.selectField || '请选择数据列'); return; }

        var binding = {
            deviceId: dev.deviceId,
            deviceName: dev.deviceName,
            fieldName: field.itemCode,
            fieldLabel: field.itemName,
            unit: field.unit || '',
            dataSource: field.dataSourceValue || ''
        };

        if (onConfirmCallback) onConfirmCallback(binding);
        window.CloseOwnerWindow('ok');
    }

    function onCancel() {
        window.CloseOwnerWindow('cancel');
    }
</script>
</body>
</html>