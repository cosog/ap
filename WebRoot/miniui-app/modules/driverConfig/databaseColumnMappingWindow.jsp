<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>字段映射表</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html,
        body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }

        .main-container {
            position: relative;
            width: 100%;
            height: 100%;
        }

        .mini-splitter {
            width: 100%;
            height: 100%;
            border: none;
        }

        .left-tree-area {
            padding: 4px;
            height: 100%;
            background: #fafafa;
        }

        .right-tab-area {
            padding: 4px;
            height: 100%;
            background: #fff;
        }

        .mini-toolbar {
            padding: 4px 8px;
            border-bottom: 1px solid #e8e8e8;
            background: #fafafa;
            display: flex;
            align-items: center;
        }

        .handsontable-container {
            width: 100%;
            height: 100%;
        }

        /* 运行状态布局：由 splitter 直接控制，无需额外样式 */
        .tab-content-wrapper {
            height: 100%;
            display: flex;
            flex-direction: column;
        }

        .tab-body {
            flex: 1;
            overflow: hidden;
            padding: 0;
        }

        .tab-body .mini-splitter {
            width: 100%;
            height: 100%;
        }

        /* 面板内表格铺满 */
        .panel-grid {
            width: 100%;
            height: 100%;
        }

    </style>
</head>

<body>
    <div class="main-container">
        <div class="mini-splitter" vertical="false" style="width:100%;height:100%;">
            <!-- 左侧协议树 -->
            <div size="20%" showCollapseButton="true" collapseDirection="left" minSize="150">
                <div class="left-tree-area">
                    <div id="protocolTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" expandOnNodeClick="false" idField="id" textField="text" parentField="pid" resultAsTree="true" allowAlternating="true" onbeforeload="onTreeBeforeLoad" onload="onTreeLoad" onnodeselect="onTreeSelect">
                        <div property="emptyText" class="empty-msg">No Protocol</div>
                    </div>
                </div>
            </div>
            <!-- 右侧 Tab 区域 -->
            <div size="80%" showCollapseButton="false">
                <div class="right-tab-area">
                    <div id="mainTabs" class="mini-tabs" style="width:100%;height:100%;" activeIndex="0" onactivechanged="onTabChanged">
                        <!-- Tab1: 运行状态字段关联 -->
                        <div title="运行状态字段关联" name="runstatus" style="height:100%;">
                            <div class="tab-content-wrapper">
                                <div class="mini-toolbar">
                                    <span style="flex:1;"></span>
                                    <span id="runStatusInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                    <button id="runStatusSaveBtn" class="mini-button" iconCls="save" onclick="onRunStatusSave()">保存</button>
                                </div>
                                <div class="tab-body">
                                    <!-- 左右 splitter -->
                                    <div id="runStatusSplitter" class="mini-splitter" vertical="false" style="width:100%;height:100%;">
                                        <!-- 左侧：运行状态列选择 -->
                                        <div size="50%" showCollapseButton="false">
                                            <div id="runStatusItemsPanel" class="mini-panel" title="" style="width:100%;height:100%;" showCollapseButton="false" showCloseButton="false" allowResize="false" bodyStyle="padding:0;">
                                                <div id="runStatusItemsGrid" class="mini-datagrid" style="height:100%;" showPager="false" allowAlternating="true" url="" dataField="totalRoot" totalField="totalCount" onbeforeload="onRunStatusItemsBeforeLoad" onload="onRunStatusItemsLoad" onselectionchanged="onRunStatusItemSelect">
                                                </div>
                                            </div>
                                        </div>
                                        <!-- 右侧：上下 splitter -->
                                        <div size="50%" showCollapseButton="false">
                                            <div id="runConditionSplitter" class="mini-splitter" vertical="true" style="width:100%;height:100%;">
                                                <!-- 上：运行条件配置 -->
                                                <div size="50%" showCollapseButton="false">
                                                    <div id="runConditionPanel" class="mini-panel" title="" style="width:100%;height:100%;" showCollapseButton="false" showCloseButton="false" allowResize="false" bodyStyle="padding:0;">
                                                        <div id="runConditionGrid" class="mini-datagrid" style="height:100%;" showPager="false" multiSelect="true" allowCellSelect="true" allowRowSelect="true" url="" dataField="totalRoot" totalField="totalCount" onbeforeload="onRunConditionBeforeLoad" onload="onRunConditionLoad" onselectionchanged="onConditionGridSelectionChanged">
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- 下：停止条件配置 -->
                                                <div size="50%" showCollapseButton="false">
                                                    <div id="stopConditionPanel" class="mini-panel" title="" style="width:100%;height:100%;" showCollapseButton="false" showCloseButton="false" allowResize="false" bodyStyle="padding:0;">
                                                        <div id="stopConditionGrid" class="mini-datagrid" style="height:100%;" showPager="false" multiSelect="true" allowCellSelect="true" allowRowSelect="true" url="" dataField="totalRoot" totalField="totalCount" onbeforeload="onStopConditionBeforeLoad" onload="onStopConditionLoad" onselectionchanged="onConditionGridSelectionChanged">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Tab2: 计算字段关联 -->
                        <div title="计算字段关联" name="calc" style="height:100%;">
                            <div class="tab-content-wrapper">
                                <div class="mini-toolbar">
                                    <span style="flex:1;"></span>
                                    <span id="calcInfoLabel" style="color:#2d6a9f;font-size:13px;"></span>
                                    <button id="calcSaveBtn" class="mini-button" iconCls="save" onclick="onCalcSave()">保存</button>
                                </div>
                                <div class="tab-body" style="weight:100%;height:100%;">
                                    <div id="calcHandsontableContainer" class="handsontable-container" style="weight:100%;height:100%;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        var context = '<%=context%>';

        var currentProtocolNode = null;
        var deviceTypeIds = '';
        var editFlag = false;

        var calcHandsontableHelper = null;

        // ================================================================
        // 1. 国际化
        // ================================================================
        function initI18n() {
            var tabs = mini.get('mainTabs');
            if (tabs) {
                var tabsData = tabs.getTabs();
                if (tabsData && tabsData.length >= 2) {
                    tabs.updateTab(tabsData[0], {
                        title: _loginUserLanguageResource.operatingStatusFieldMapping
                    });
                    tabs.updateTab(tabsData[1], {
                        title: _loginUserLanguageResource.calculatedFieldMapping
                    });
                }
            }
            mini.get('runStatusSaveBtn').setText(_loginUserLanguageResource.save);
            mini.get('calcSaveBtn').setText(_loginUserLanguageResource.save);
            document.title = _loginUserLanguageResource.fieldMappingTable;
            var tree = mini.get('protocolTree');
            if (tree) tree.setEmptyText(_loginUserLanguageResource.emptyMsg);

            var leftPanel = mini.get('runStatusItemsPanel');
            if (leftPanel) leftPanel.setTitle(_loginUserLanguageResource.runStatusColumnSelect);

            var runPanel = mini.get('runConditionPanel');
            if (runPanel) runPanel.setTitle(_loginUserLanguageResource.runConditionConfig);

            var stopPanel = mini.get('stopConditionPanel');
            if (stopPanel) stopPanel.setTitle(_loginUserLanguageResource.stopConditionConfig);
        }

        // ================================================================
        // 2. 接收父窗口参数
        // ================================================================
        function setData(data) {
            if (data && data.deviceTypeIds) {
                deviceTypeIds = data.deviceTypeIds;
                editFlag = data.editFlag;
            }
            var tree = mini.get('protocolTree');
            if (tree) {
                tree.load(context + '/acquisitionUnitManagerController/modbusProtocolAddrMappingTreeData');
            }
        }

        // ================================================================
        // 3. 树事件（静态定义）
        // ================================================================
        function onTreeBeforeLoad(e) {
            var params = e.params || {};
            params.deviceTypeIds = deviceTypeIds;
            e.params = params;
        }

        function onTreeLoad(e) {
            var tree = e.sender;
            var root = tree.getRootNode();
            if (!root) return;
            var protocolNodes = [];

            function collect(node) {
                if (node.children && node.children.length > 0) {
                    for (var i = 0; i < node.children.length; i++) collect(node.children[i]);
                } else {
                    if (node.classes == 1) protocolNodes.push(node);
                }
            }
            collect(root);
            if (protocolNodes.length > 0) {
                tree.selectNode(protocolNodes[0]);
            }
        }

        function onTreeSelect(e) {
            var node = e.node;
            if (node && node.classes === 1) {
                currentProtocolNode = node;
                // 根据当前激活的Tab加载数据
                var tabs = mini.get('mainTabs');
                var active = tabs.getActiveTab();
                if (active) {
                    if (active.name === 'runstatus') {
                        loadRunStatusItems(node);
                    } else if (active.name === 'calc') {
                        loadCalcConfigData(node);
                    }
                }
            }
        }

        // ================================================================
        // 4. Tab 切换
        // ================================================================
        function onTabChanged(e) {
            if (!currentProtocolNode) return;
            var tabs = mini.get('mainTabs');
            var active = tabs.getActiveTab();
            if (active) {
                if (active.name === 'runstatus') {
                    loadRunStatusItems(currentProtocolNode);
                } else if (active.name === 'calc') {
                    loadCalcConfigData(currentProtocolNode);
                }
            }
        }

        function loadRunStatusItems(node) {
            var grid = mini.get('runStatusItemsGrid');
            if (!grid) return;
            // 设置 URL（如果还未设置）
            if (!grid.getUrl()) {
                grid.setUrl(context + '/acquisitionUnitManagerController/getProtocolRunStatusItems');
            }
            // 加载，参数在 onbeforeload 中自动附加
            grid.load();
        }

        function onRunStatusItemsBeforeLoad(e) {
            var grid = e.sender;
            var params = e.params || {};
            // 从当前选中的协议节点获取参数
            if (currentProtocolNode) {
                params.classes = currentProtocolNode.classes || 1;
                params.deviceType = currentProtocolNode.deviceType || 0;
                params.protocolCode = currentProtocolNode.code || '';
            }
            e.params = params;
        }

        function onRunStatusItemsLoad(e) {
            var grid = e.sender;
            var result = e.result;
            if (!result || !result.success) return;

            // 动态列
            if (result.columns && result.columns.length > 0) {
                var columns = result.columns;
                var miniColumns = [];
                for (var i = 0; i < columns.length; i++) {
                    var col = columns[i];
                    var miniCol = {
                        header: col.header,
                        headerAlign: 'center',
                        align: 'center'
                    };

                    if (col.dataIndex === 'id') {
                        miniCol.type = 'indexcolumn';
                        miniCol.width = 50;
                    } else {
                        //miniCol.flex = 1;
                        //miniCol.width = 200;
                        miniCol.field = col.dataIndex;
                        miniCol.width = "100%";
                    }
                    miniColumns.push(miniCol);
                }
                grid.setColumns(miniColumns);
            }

            // 选中已配置的行
            if (result.configedRunStatusIndex !== undefined && result.configedRunStatusIndex >= 0) {
                grid.select(result.configedRunStatusIndex);
                setTimeout(function() {
                    grid.scrollIntoView(result.configedRunStatusIndex);
                }, 150);
            } else {
                // 清空右侧两个 Grid
                var runGrid = mini.get('runConditionGrid');
                runGrid.setData([]);
                var stopGrid = mini.get('stopConditionGrid');
                stopGrid.setData([]);
            }
        }

        function onRunStatusItemSelect(e) {
            var grid = e.sender;
            var row = grid.getSelected();
            if (!row) {
                // 清空右侧两个 Grid
                var runGrid = mini.get('runConditionGrid');
                runGrid.setData([]);
                var stopGrid = mini.get('stopConditionGrid');
                stopGrid.setData([]);
                return;
            }
            var runGrid = mini.get('runConditionGrid');
            var stopGrid = mini.get('stopConditionGrid');
            if (!runGrid.getUrl()) {
                runGrid.setUrl(context + '/acquisitionUnitManagerController/getProtocolRunStatusItemsMeaning');
            }
            if (!stopGrid.getUrl()) {
                stopGrid.setUrl(context + '/acquisitionUnitManagerController/getProtocolRunStatusItemsMeaning');
            }
            runGrid.load();
            stopGrid.load();
        }

        function onRunConditionBeforeLoad(e) {
            var params = e.params || {};
            // 获取当前选中的运行状态行
            var itemsGrid = mini.get('runStatusItemsGrid');
            var row = itemsGrid.getSelected();
            if (row) {
                params.status = 1;
                params.deviceType = row.deviceType;
                params.protocolCode = row.protocolCode;
                params.itemName = row.itemName;
                params.itemColumn = row.itemColumn;
                params.bitIndex = row.bitIndex || '';
                params.resolutionMode = row.resolutionMode;
            }
            e.params = params;
        }

        function onRunConditionLoad(e) {
            var grid = e.sender;
            var result = e.result;
            if (!result || !result.success) return;

            var resolutionMode = result.resolutionMode;
            var columns = [];
            var multiSelect = true;

            if (resolutionMode === 1 || resolutionMode === 0) {
                // 枚举/开关量：多选，显示 值 + 含义，不可编辑
                columns = [{
                        type: 'checkcolumn',
                        width: 40
                    },
                    {
                        type: 'indexcolumn',
                        header: _loginUserLanguageResource.idx,
                        align: 'center',
                        headerAlign: 'center',
                        width: 50
                    },
                    {
                        field: 'value',
                        header: _loginUserLanguageResource.value,
                        align: 'center',
                        headerAlign: 'center',
                        width: 80
                    },
                    {
                        field: 'meaning',
                        header: _loginUserLanguageResource.meaning,
                        align: 'center',
                        headerAlign: 'center',
                        flex: 1
                    }
                ];
                multiSelect = true;
                grid.setAllowCellEdit(false);
            } else {
                // 数值型：单选，显示 条件 + 值，值可编辑
                columns = [{
                        type: 'checkcolumn',
                        width: 40
                    },
                    {
                        type: 'indexcolumn',
                        header: _loginUserLanguageResource.idx,
                        align: 'center',
                        headerAlign: 'center',
                        width: 50
                    },
                    {
                        field: 'condition',
                        header: _loginUserLanguageResource.alarmLogic,
                        align: 'center',
                        headerAlign: 'center',
                        width: 100
                    },
                    {
                        field: 'value',
                        header: _loginUserLanguageResource.value,
                        align: 'center',
                        headerAlign: 'center',
                        flex: 1,
                        allowCellEdit: true,
                        editor: {
                            type: 'textbox'
                        } // 允许编辑
                    }
                ];
                multiSelect = false;
                grid.setAllowCellEdit(true);
            }

            grid.setColumns(columns);
            grid.setMultiSelect(multiSelect);
            grid.doLayout();

            // 选中已配置的行
            grid.deselectAll();
            if (result.runValueIndex.length > 0) {
                grid.selects(result.runValueIndex);

                setTimeout(function() {
                    grid.scrollIntoView(result.runValueIndex[result.runValueIndex.length - 1]);
                }, 150);
                //for (var i = 0; i < result.runValueIndex.length; i++) {
                //    grid.select(result.runValueIndex[i]);
                //}
            }
        }

        function onStopConditionBeforeLoad(e) {
            var params = e.params || {};
            var itemsGrid = mini.get('runStatusItemsGrid');
            var row = itemsGrid.getSelected();
            if (row) {
                params.status = 0;
                params.deviceType = row.deviceType;
                params.protocolCode = row.protocolCode;
                params.itemName = row.itemName;
                params.itemColumn = row.itemColumn;
                params.bitIndex = row.bitIndex || '';
                params.resolutionMode = row.resolutionMode;
            }
            e.params = params;
        }

        function onStopConditionLoad(e) {
            var grid = e.sender;
            var result = e.result;
            if (!result || !result.success) return;

            var resolutionMode = result.resolutionMode;
            var columns = [];
            var multiSelect = true;

            if (resolutionMode === 1 || resolutionMode === 0) {
                // 枚举/开关量：多选，显示 值 + 含义，不可编辑
                columns = [{
                        type: 'checkcolumn',
                        width: 40
                    },
                    {
                        type: 'indexcolumn',
                        header: _loginUserLanguageResource.idx,
                        align: 'center',
                        headerAlign: 'center',
                        width: 50
                    },
                    {
                        field: 'value',
                        header: _loginUserLanguageResource.value,
                        align: 'center',
                        headerAlign: 'center',
                        width: 80
                    },
                    {
                        field: 'meaning',
                        header: _loginUserLanguageResource.meaning,
                        align: 'center',
                        headerAlign: 'center',
                        flex: 1
                    }
                ];
                multiSelect = true;
                grid.setAllowCellEdit(false);
            } else {
                // 数值型：单选，显示 条件 + 值，值可编辑
                columns = [{
                        type: 'checkcolumn',
                        width: 40
                    },
                    {
                        type: 'indexcolumn',
                        header: _loginUserLanguageResource.idx,
                        align: 'center',
                        headerAlign: 'center',
                        width: 50
                    },
                    {
                        field: 'condition',
                        header: _loginUserLanguageResource.alarmLogic,
                        align: 'center',
                        headerAlign: 'center',
                        width: 100
                    },
                    {
                        field: 'value',
                        header: _loginUserLanguageResource.value,
                        align: 'center',
                        headerAlign: 'center',
                        flex: 1,
                        allowCellEdit: true,
                        editor: {
                            type: 'textbox'
                        } // 允许编辑
                    }
                ];
                multiSelect = false;
                grid.setAllowCellEdit(true);
            }

            grid.setColumns(columns);
            grid.setMultiSelect(multiSelect);
            grid.doLayout();

            // 选中已配置的行
            grid.deselectAll();
            if (result.stopValueIndex != undefined && result.stopValueIndex.length > 0) {
                grid.selects(result.stopValueIndex);
                setTimeout(function() {
                    grid.scrollIntoView(result.stopValueIndex[result.stopValueIndex.length - 1]);
                }, 150);
                //for (var i = 0; i < result.stopValueIndex.length; i++) {
                //    grid.select(result.stopValueIndex[i]);
                //}
            }
        }

        function onConditionGridSelectionChanged(e) {
            var grid = e.sender;
            var leftGrid = mini.get('runStatusItemsGrid');
            var leftRow = leftGrid.getSelected();
            if (!leftRow) return;
            var resolutionMode = leftRow.resolutionMode;
            if (resolutionMode !== 1 && resolutionMode !== 0) return;

            var otherGrid = (grid.id === 'runConditionGrid') ? mini.get('stopConditionGrid') : mini.get('runConditionGrid');
            if (!otherGrid) return;

            // 获取当前选中的行索引
            var selectedRows = grid.getSelecteds();
            var selectedIndexes = [];
            for (var i = 0; i < selectedRows.length; i++) {
                selectedIndexes.push(grid.indexOf(selectedRows[i]));
            }

            // 取消另一个表格中相同索引的选中
            if (selectedIndexes.length > 0) {
                otherGrid.deselects(selectedIndexes);
            }
        }

        function onRunConditionGridSelect(e) {
            var grid = e.sender;
            var record = e.record;
            var index = grid.indexOf(record);
            console.log('onRunConditionGridSelect');

            // 从左侧表格获取当前选中的运行状态项
            var leftGrid = mini.get('runStatusItemsGrid');
            var leftRow = leftGrid.getSelected();
            if (!leftRow) return;
            var resolutionMode = leftRow.resolutionMode;

            // 仅当开关量或枚举量时执行互斥
            if (resolutionMode === 1 || resolutionMode === 0) {
                var otherGrid = mini.get('stopConditionGrid');
                if (!otherGrid) return;
                //otherGrid.deselect(index);
            }
        }

        function onStopConditionGridSelect(e) {
            var grid = e.sender;
            var record = e.record;
            var index = grid.indexOf(record);
            console.log('onStopConditionGridSelect');

            // 从左侧表格获取当前选中的运行状态项
            var leftGrid = mini.get('runStatusItemsGrid');
            var leftRow = leftGrid.getSelected();
            if (!leftRow) return;
            var resolutionMode = leftRow.resolutionMode;

            // 仅当开关量或枚举量时执行互斥
            if (resolutionMode === 1 || resolutionMode === 0) {
                var otherGrid = mini.get('runConditionGrid');
                if (!otherGrid) return;
                //otherGrid.deselect(index);
            }
        }

        function onRunStatusSave() {
            // 1. 获取左侧选中的运行状态项
            var leftGrid = mini.get('runStatusItemsGrid');
            var leftRow = leftGrid.getSelected();
            if (!leftRow) {
                return;
            }

            // 提取参数
            var protocolCode = leftRow.protocolCode || '';
            var protocolName = leftRow.protocolName || '';
            var itemName = leftRow.itemName || '';
            var itemColumn = leftRow.itemColumn || '';
            var bitIndex = leftRow.bitIndex || '';
            var deviceType = leftRow.deviceType || 0;
            var resolutionMode = leftRow.resolutionMode || 0;

            // 2. 获取右侧两个表格的选中行
            var runGrid = mini.get('runConditionGrid');
            var stopGrid = mini.get('stopConditionGrid');
            var runSelecteds = runGrid.getSelecteds();
            var stopSelecteds = stopGrid.getSelecteds();

            var runValue = '';
            var stopValue = '';
            var runCondition = '';
            var stopCondition = '';

            if (resolutionMode === 1 || resolutionMode === 0) {
                // 开关量/枚举量：收集 value
                if (runSelecteds.length > 0) {
                    var runValues = [];
                    for (var i = 0; i < runSelecteds.length; i++) {
                        runValues.push(runSelecteds[i].value);
                    }
                    runValue = runValues.join(',');
                }
                if (stopSelecteds.length > 0) {
                    var stopValues = [];
                    for (var i = 0; i < stopSelecteds.length; i++) {
                        stopValues.push(stopSelecteds[i].value);
                    }
                    stopValue = stopValues.join(',');
                }
            } else if (resolutionMode === 2) {
                // 数值量：收集 condition + value
                if (runSelecteds.length > 0) {
                    var runConditions = [];
                    for (var i = 0; i < runSelecteds.length; i++) {
                        var selectId = runSelecteds[i].id;
                        var val = runSelecteds[i].value;
                        if (val !== undefined && val !== null && val !== '') {
                            var sign = '';
                            if (selectId === 1) sign = '>';
                            else if (selectId === 2) sign = '>=';
                            else if (selectId === 3) sign = '<=';
                            else if (selectId === 4) sign = '<';
                            runConditions.push(sign + ',' + val);
                        }
                    }
                    if (runConditions.length > 0) {
                        runCondition = runConditions.join(';');
                    }
                }
                if (stopSelecteds.length > 0) {
                    var stopConditions = [];
                    for (var i = 0; i < stopSelecteds.length; i++) {
                        var selectId = stopSelecteds[i].id;
                        var val = stopSelecteds[i].value;
                        if (val !== undefined && val !== null && val !== '') {
                            var sign = '';
                            if (selectId === 1) sign = '>';
                            else if (selectId === 2) sign = '>=';
                            else if (selectId === 3) sign = '<=';
                            else if (selectId === 4) sign = '<';
                            else sign = cond;
                            stopConditions.push(sign + ',' + val);
                        }
                    }
                    if (stopConditions.length > 0) {
                        stopCondition = stopConditions.join(';');
                    }
                }
            }

            // 3. 提交保存
            var loading = mini.loading(_loginUserLanguageResource.updateWait, _loginUserLanguageResource.tip);

            $.ajax({
                type: 'POST',
                url: context + '/acquisitionUnitManagerController/saveProtocolRunStatusConfig',
                data: {
                    protocolCode: protocolCode,
                    protocolName: protocolName,
                    resolutionMode: resolutionMode,
                    itemName: itemName,
                    itemColumn: itemColumn,
                    bitIndex: bitIndex,
                    deviceType: deviceType,
                    runValue: runValue,
                    stopValue: stopValue,
                    runCondition: runCondition,
                    stopCondition: stopCondition
                },
                dataType: 'json',
                success: function(resp) {
                    mini.hideMessageBox(loading);
                    if (resp.success) {
                        mini.alert(_loginUserLanguageResource.savedSuccessfully);
                        // 刷新右侧两个表格，显示最新配置
                        var runGrid = mini.get('runConditionGrid');
                        var stopGrid = mini.get('stopConditionGrid');
                        // 确保 URL 已设置（若未设置，则设置）
                        if (!runGrid.getUrl()) {
                            runGrid.setUrl(context + '/acquisitionUnitManagerController/getProtocolRunStatusItemsMeaning');
                        }
                        if (!stopGrid.getUrl()) {
                            stopGrid.setUrl(context + '/acquisitionUnitManagerController/getProtocolRunStatusItemsMeaning');
                        }
                        // 重新加载，参数由 beforeload 自动附加
                        runGrid.load();
                        stopGrid.load();
                    } else {
                        mini.alert('<font color="red">' + (_loginUserLanguageResource.saveFailed) + '</font>');
                    }
                },
                error: function() {
                    mini.hideMessageBox(loading);
                    mini.alert(_loginUserLanguageResource.requestFailed);
                }
            });
        }


        function loadCalcConfigData(node) {
            if (!node) return;
            var protocolCode = node.code;
            var deviceType = node.deviceType;
            var classes = node.classes;
            var protocolName = node.text;

            var container = document.getElementById('calcHandsontableContainer');
            if (!container) return;

            var loading = mini.loading(_loginUserLanguageResource.loadingData, _loginUserLanguageResource.tip);
            $.ajax({
                type: 'POST',
                url: context + '/acquisitionUnitManagerController/getDatabaseColumnMappingTable',
                data: {
                    classes: classes,
                    deviceType: deviceType,
                    protocolCode: protocolCode
                },
                dataType: 'json',
                success: function(result) {
                    mini.hideMessageBox(loading);
                    if (!result.success) {
                        mini.alert(result.message || _loginUserLanguageResource.requestFailed);
                        return;
                    }
                    var data = result.totalRoot || [];
                    var calColumnList = result.calColumnNameList || [];

                    // 销毁已有实例
                    if (calcHandsontableHelper && calcHandsontableHelper.hot) {
                        calcHandsontableHelper.hot.destroy();
                        calcHandsontableHelper = null;
                    }

                    // 创建新实例（使用 createNew）
                    calcHandsontableHelper = CalcHandsontableHelper.createNew(
                        'calcHandsontableContainer',
                        data,
                        calColumnList,
                        protocolCode
                    );
                    calcHandsontableHelper.createTable(data);
                },
                error: function() {
                    mini.hideMessageBox(loading);
                    mini.alert(_loginUserLanguageResource.requestFailed);
                }
            });
        }

        var CalcHandsontableHelper = {
            createNew: function(divid, data, calColumnList, protocolCode) {
                var helper = {};
                helper.hot = null;
                helper.divid = divid;
                helper.protocolCode = protocolCode;
                helper.validresult = true;
                helper.colHeaders = [];
                helper.columns = [];
                helper.AllData = {
                    updatelist: [],
                    delidslist: [],
                    insertlist: []
                };
                helper.updatelist = [];
                helper.delidslist = [];
                helper.insertlist = [];
                helper.calColumnDropdown = calColumnList || [];

                // 列定义（与原 ExtJS 完全一致）
                helper.columnsDef = [{
                        data: 'id'
                    },
                    {
                        data: 'itemName'
                    },
                    {
                        data: 'itemColumn'
                    },
                    {
                        data: 'calColumnName'
                    },
                    {
                        data: 'calculateEnable',
                        type: 'checkbox'
                    }
                ];

                // 列头（国际化）
                helper.colHeaders = [
                    _loginUserLanguageResource.idx,
                    _loginUserLanguageResource.protocolFieldName,
                    _loginUserLanguageResource.dataColumn,
                    _loginUserLanguageResource.calculatedFieldName,
                    _loginUserLanguageResource.enable
                ];

                // 只读列样式
                helper.addCellStyle = function(instance, td, row, col, prop, value, cellProperties) {
                    Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.backgroundColor = 'rgb(245, 245, 245)';
                    td.style.whiteSpace = 'nowrap';
                    td.style.overflow = 'hidden';
                    td.style.textOverflow = 'ellipsis';
                };

                // 普通单元格样式
                helper.addNormalStyle = function(instance, td, row, col, prop, value, cellProperties) {
                    Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.whiteSpace = 'nowrap';
                    td.style.overflow = 'hidden';
                    td.style.textOverflow = 'ellipsis';
                };

                // 创建表格
                helper.createTable = function(data) {
                    var container = document.getElementById(helper.divid);
                    if (!container) return;

                    // 清空容器
                    container.innerHTML = '';

                    // 如果数据为空，填充 30 行空对象（与原 ExtJS 一致）
                    if (!data || data.length === 0) {
                        data = [];
                        for (var i = 0; i < 30; i++) {
                            data.push({});
                        }
                    }

                    var hotElement = document.querySelector('#' + helper.divid);
                    helper.hot = new Handsontable(hotElement, {
                        licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                        theme: 'ht-theme-classic',
                        data: data,
                        hiddenColumns: {
                            columns: [0, 2], // 隐藏 id 和 itemColumn
                            indicators: false,
                            copyPasteEnabled: false
                        },
                        colWidths: [50, 200, 150, 200, 80],
                        columns: helper.columnsDef,
                        stretchH: 'all',
                        rowHeaders: true,
                        colHeaders: helper.colHeaders,
                        columnSorting: true,
                        sortIndicator: true,
                        manualColumnResize: true,
                        manualRowResize: true,
                        filters: true,
                        renderAllRows: true,
                        search: true,
                        allowInsertRow: false,
                        contextMenu: {
                            items: {
                                "copy": {
                                    name: _loginUserLanguageResource.contextMenu_copy
                                },
                                "cut": {
                                    name: _loginUserLanguageResource.contextMenu_cut
                                }
                            }
                        },
                        cells: function(row, col, prop) {
                            var cellProperties = {};
                            var visualColIndex = this.instance.toVisualColumn(col);
                            if (visualColIndex <= 2) {
                                // 前3列只读（id, itemName, itemColumn）
                                cellProperties.editor = false;
                                cellProperties.renderer = helper.addCellStyle;
                            } else if (visualColIndex === 3) {
                                // 计算字段名：下拉选择
                                this.type = 'dropdown';
                                this.source = helper.calColumnDropdown;
                                this.strict = true;
                                this.allowInvalid = false;
                                if (!editFlag) cellProperties.editor = false;
                            }
                            return cellProperties;
                        },
                        afterChange: function(changes, source) {
                            if (!changes) return;
                            for (var i = 0; i < changes.length; i++) {
                                var row = changes[i][0];
                                var prop = changes[i][1];
                                var oldVal = changes[i][2];
                                var newVal = changes[i][3];
                                if (oldVal === newVal) continue;
                                var rowData = helper.hot.getDataAtRow(row);
                                var id = rowData[0];
                                // 仅当 id 存在且有效时收集更新
                                if (id && id > 0) {
                                    var found = false;
                                    // 在 updatelist 中查找并更新
                                    for (var j = 0; j < helper.updatelist.length; j++) {
                                        if (helper.updatelist[j].id === id) {
                                            var record = {};
                                            for (var k = 0; k < helper.columnsDef.length; k++) {
                                                var field = helper.columnsDef[k].data;
                                                record[field] = rowData[k];
                                            }
                                            helper.updatelist[j] = record;
                                            found = true;
                                            break;
                                        }
                                    }
                                    if (!found) {
                                        var record = {};
                                        for (var k = 0; k < helper.columnsDef.length; k++) {
                                            var field = helper.columnsDef[k].data;
                                            record[field] = rowData[k];
                                        }
                                        helper.updatelist.push(record);
                                    }
                                    helper.AllData.updatelist = helper.updatelist;
                                }
                            }
                        },
                        beforeRemoveRow: function(index, amount) {
                            var ids = [];
                            for (var i = index; i < index + amount; i++) {
                                var rowData = helper.hot.getDataAtRow(i);
                                if (rowData && rowData[0] && rowData[0] > 0) {
                                    ids.push(rowData[0]);
                                }
                            }
                            if (ids.length > 0) {
                                helper.delidslist = helper.delidslist.concat(ids);
                                helper.AllData.delidslist = helper.delidslist;
                            }
                        },
                        afterOnCellMouseOver: function(event, coords, TD) {

                        }
                    });
                };

                // 插入数据的获取（与原 ExtJS 一致）
                helper.insertExpressCount = function() {
                    var idsdata = helper.hot.getDataAtCol(0);
                    for (var i = 0; i < idsdata.length; i++) {
                        if (idsdata[i] == null || idsdata[i] < 0) {
                            var rowdata = helper.hot.getDataAtRow(i);
                            if (rowdata != null) {
                                var data = {};
                                for (var j = 0; j < helper.columnsDef.length; j++) {
                                    var field = helper.columnsDef[j].data;
                                    data[field] = rowdata[j];
                                }
                                helper.insertlist.push(data);
                            }
                        }
                    }
                    if (helper.insertlist.length != 0) {
                        helper.AllData.insertlist = helper.insertlist;
                    }
                };

                // 保存数据
                helper.saveData = function() {
                    helper.insertExpressCount();

                    if (helper.AllData.updatelist.length==0 && helper.AllData.insertlist.length==0 && helper.AllData.delidslist.length==0) {
                        mini.alert(_loginUserLanguageResource.noDataChange);
                        return;
                    }

                    // 3. 如果不合法，提示错误并返回
                    if (!helper.validresult) {
                        mini.alert(_loginUserLanguageResource.invalidDataType);
                        return;
                    }

                    // 4. 获取当前选中的协议节点（从左侧树获取）
                    var protocolTree = mini.get('protocolTree');
                    if (!protocolTree) {
                        return;
                    }
                    var selectedNode = protocolTree.getSelectedNode();
                    if (!selectedNode) {
                        return;
                    }
                    var classes = selectedNode.classes;
                    var protocolType = selectedNode.deviceType;
                    var protocolCode = "";
                    if (classes == 1) {
                        protocolCode = selectedNode.code;
                    }

                    // 5. 构造请求参数
                    var requestData = {
                        data: JSON.stringify(helper.AllData),
                        protocolType: protocolType,
                        protocolCode: helper.protocolCode
                    };

                    // 6. 显示遮罩并提交
                    var loading = mini.loading(_loginUserLanguageResource.updateWait, _loginUserLanguageResource.tip);
                    $.ajax({
                        type: 'POST',
                        url: context + '/acquisitionUnitManagerController/saveDatabaseColumnMappingTable',
                        data: requestData,
                        dataType: 'json',
                        success: function(response) {
                            mini.hideMessageBox(loading);
                            if (response.success) {
                                mini.alert(_loginUserLanguageResource.savedSuccessfully);
                                helper.clearContainer();
                                if (window.currentProtocolNode) {
                                    loadCalcConfigData(window.currentProtocolNode);
                                }
                            } else {
                                mini.alert(_loginUserLanguageResource.saveFailed);
                            }
                        },
                        error: function() {
                            mini.hideMessageBox(loading);
                            mini.alert(_loginUserLanguageResource.requestFailed);
                            // 清空临时数据
                            helper.clearContainer();
                        }
                    });
                };


                // 清空容器
                helper.clearContainer = function() {
                    helper.AllData = {
                        updatelist: [],
                        delidslist: [],
                        insertlist: []
                    };
                    helper.updatelist = [];
                    helper.delidslist = [];
                    helper.insertlist = [];
                };

                // 删除收集
                helper.delExpressCount = function(ids) {
                    for (var i = 0; i < ids.length; i++) {
                        if (ids[i] != null) {
                            helper.delidslist.push(ids[i]);
                        }
                    }
                    helper.AllData.delidslist = helper.delidslist;
                };

                // 筛选（删除后清除更新列表中的对应项）
                helper.screening = function() {
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

                // 更新收集
                helper.updateExpressCount = function(data) {
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

                return helper;
            }
        };

        function onCalcSave() {
            if (calcHandsontableHelper) {
                calcHandsontableHelper.saveData();
            }
        }

        // ================================================================
        // 6. 页面初始化
        // ================================================================
        $(document).ready(function() {
            mini.parse();
            initI18n();
        });

    </script>
</body>

</html>
