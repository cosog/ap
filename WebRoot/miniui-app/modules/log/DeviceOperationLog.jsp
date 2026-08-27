<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String moduleId = request.getParameter("moduleId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>设备操作日志</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        /* ===== 全局基础样式 ===== */
        html, body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #f0f2f5;
        }
        .alarm-container {
            width: 100%;
            height: 100%;
            display: flex;
            flex-direction: column;
            background: #fff;
        }
        /* 底部一级标签 */
        .level1-footer {
            flex-shrink: 0;
            background: #fafafa;
            border-top: 1px solid #e0e0e0;
            padding: 0 10px;
            display: flex;
            align-items: center;
            gap: 2px;
            height: 36px;
            overflow-x: auto;
            order: 2;
        }
        .level1-footer .tab-item {
            padding: 4px 16px;
            font-size: 13px;
            cursor: pointer;
            color: #666;
            background: transparent;
            border-bottom: 2px solid transparent;
            transition: all 0.2s;
            user-select: none;
            white-space: nowrap;
        }
        .level1-footer .tab-item:hover { color: #333; }
        .level1-footer .tab-item.active {
            color: #2d6a9f;
            font-weight: bold;
            border-bottom-color: #2d6a9f;
        }
        /* 左侧二级标签 */
        .level2-sidebar {
            flex-shrink: 0;
            width: 32px;
            background: #f5f7fa;
            border-right: 1px solid #e8e8e8;
            overflow: auto;
            padding: 8px 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: flex-start;
        }
        .level2-sidebar .tab-item {
            padding: 10px 2px;
            font-size: 12px;
            cursor: pointer;
            color: #555;
            background: transparent;
            border-left: 3px solid transparent;
            transition: all 0.15s;
            user-select: none;
            text-align: center;
            writing-mode: vertical-rl;
            letter-spacing: 2px;
            width: 100%;
            flex-shrink: 0;
            min-height: 36px;
            line-height: 1.4;
            box-sizing: border-box;
        }
        .level2-sidebar .tab-item:hover { background: #e8ecf0; color: #333; }
        .level2-sidebar .tab-item.active {
            background: #e6f7ff;
            color: #1890ff;
            font-weight: bold;
            border-left-color: #1890ff;
        }
        .level2-sidebar .no-child-tip {
            padding: 12px 0;
            color: #999;
            font-size: 12px;
            text-align: center;
            writing-mode: vertical-rl;
            letter-spacing: 2px;
        }
        /* 主区域 */
        .device-log-area {
            flex: 1;
            overflow: hidden;
            background: #fff;
            border-radius: 4px;
            box-shadow: 0 1px 4px rgba(0,0,0,0.06);
            display: flex;
            flex-direction: column;
        }
        .mini-toolbar .separator {
            width: 1px;
            height: 20px;
            background: #ddd;
            margin: 0 4px;
        }
        .loading-placeholder {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100%;
            color: #999;
            font-size: 13px;
            flex-direction: column;
        }
        .loading-placeholder.error { color: #ff4d4f; }
    </style>
</head>
<body>
<div class="alarm-container">
    <!-- 主区域 -->
    <div style="display:flex; flex:1; overflow:hidden; order:0;">
        <!-- 二级标签 -->
        <div class="level2-sidebar" id="level2Sidebar">
            <div class="no-child-tip" id="noChildTip">选择一级</div>
        </div>
        <!-- 日志主体 -->
        <div id="logPanel" style="flex:1; overflow:hidden; display:flex; flex-direction:column; background:#f0f2f5; padding:4px;">
            <div class="device-log-area">
                <!-- 工具栏 -->
                <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8; padding:4px 8px; display:flex; align-items:center; flex-wrap:wrap; gap:6px; flex-shrink:0;">
                    <button id="refreshBtn" class="mini-button" iconCls="note-refresh" onclick="refreshData()">刷新</button>
                    <span class="separator"></span>
                    <span id="deviceLabel" style="font-size:12px;color:#333;">设备：</span>
                    <input id="deviceCombo" class="mini-combobox" style="width:150px;" emptyText="-- 全部 --" url="<%=path%>/wellInformationManagerController/loadWellComboxList" onbeforeload="onDeviceComboBeforeLoad" onshowpopup="onDeviceComboShowPopup" onload="onDeviceComboLoad" dataField="list" valueField="boxkey" textField="boxval" />
                    <span class="separator"></span>
                    <span id="operationLabel" style="font-size:12px;color:#333;">操作：</span>
                    <input id="operationCombo" class="mini-combobox" style="width:120px;" emptyText="-- 全部 --" url="<%=path%>/wellInformationManagerController/loadCodeComboxList" onbeforeload="onOperationComboBeforeLoad" dataField="list" valueField="boxkey" textField="boxval" />
                    <span class="separator"></span>
                    <span id="startTimeLabel" style="font-size:12px;color:#333;">开始：</span>
                    <input id="startDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showClearButton="false" allowInput="false" />
                    <span id="endTimeLabel" style="font-size:12px;color:#333;">结束：</span>
                    <input id="endDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showClearButton="false" allowInput="false" />
                    <span class="separator"></span>
                    <button id="queryBtn" class="mini-button" iconCls="search" onclick="doQuery()">查询</button>
                    <span class="separator"></span>
                    <button id="exportBtn" class="mini-button" iconCls="export" onclick="doExport()">导出</button>
                    <input id="logColumnStr" type="hidden" value="" />
                </div>
                <!-- 表格 -->
                <div style="flex:1; overflow:hidden;">
                    <div id="logGrid" class="mini-datagrid" style="width:100%; height:100%;"
                         idField="id" pageSize="100" allowResize="true" allowAlternating="true"
                         url="<%=path%>/logQueryController/getDeviceOperationLogData"
                         dataField="totalRoot" totalField="totalCount"
                         onbeforeload="onGridBeforeLoad" onload="onGridLoad">
                        <div property="columns"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 底部一级标签 -->
    <div class="level1-footer" id="level1Footer"></div>
</div>

<script>
    // ================================================================
    // 0. 全局变量
    // ================================================================
    var context = '<%=path%>';
    var tabInfo = null;
    try {
        if (window.parent && window.parent.tabInfo) tabInfo = window.parent.tabInfo;
    } catch(e) { console.warn('无法获取 tabInfo', e); }
    var currentLevel1 = null, currentLevel2 = null;
    var level1Data = [], level2Data = [];
    var grid = null;

    // ================================================================
    // 1. 构建一级标签
    // ================================================================
    function buildLevel1Tabs() {
        var container = document.getElementById('level1Footer');
        if (!container) return;
        container.innerHTML = '';
        if (!tabInfo || !tabInfo.children || tabInfo.children.length === 0) {
            container.innerHTML = '<span class="loading-tip">' + _loginUserLanguageResource.emptyMsg + '</span>';
            return;
        }
        level1Data = tabInfo.children;
        for (var i = 0; i < level1Data.length; i++) {
            var item = level1Data[i];
            var span = document.createElement('span');
            span.className = 'tab-item' + (i === 0 ? ' active' : '');
            span.dataset.index = i;
            span.dataset.deviceTypeId = item.deviceTypeId;
            span.textContent = item.text;
            span.onclick = function() { selectLevel1(parseInt(this.dataset.index)); };
            container.appendChild(span);
        }
        if (level1Data.length > 0) selectLevel1(0);
    }

    function selectLevel1(index) {
        if (index < 0 || index >= level1Data.length) return;
        var item = level1Data[index];
        currentLevel1 = item;
        var container = document.getElementById('level1Footer');
        var tabs = container.querySelectorAll('.tab-item');
        for (var i = 0; i < tabs.length; i++) {
            tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
        }
        buildLevel2Tabs(item);
    }

    // ================================================================
    // 2. 构建二级标签
    // ================================================================
    function buildLevel2Tabs(parentItem) {
        var container = document.getElementById('level2Sidebar');
        if (!container) return;
        container.innerHTML = '';
        var children = parentItem.children || [];
        if (!children || children.length === 0) {
            container.innerHTML = '<div class="no-child-tip" id="noChildTip">' + _loginUserLanguageResource.emptyMsg + '</div>';
            currentLevel2 = null;
            return;
        }
        level2Data = children;
        var allIds = [];
        for (var i = 0; i < children.length; i++) allIds.push(children[i].deviceTypeId);
        var allTabs = [{ text: _loginUserLanguageResource.all, deviceTypeId: allIds.join(','), isAll: true }];
        for (var i = 0; i < children.length; i++) allTabs.push(children[i]);
        for (var i = 0; i < allTabs.length; i++) {
            var item = allTabs[i];
            var div = document.createElement('div');
            div.className = 'tab-item' + (i === 0 ? ' active' : '');
            div.dataset.index = i;
            div.dataset.deviceTypeId = item.deviceTypeId;
            div.dataset.isAll = item.isAll || false;
            div.textContent = item.text;
            div.title = item.text;
            div.onclick = function() { selectLevel2(parseInt(this.dataset.index)); };
            container.appendChild(div);
        }
        if (allTabs.length > 0) {
            currentLevel2 = allTabs[0];
            loadData(currentLevel2);
        }
    }

    function selectLevel2(index) {
        var container = document.getElementById('level2Sidebar');
        var tabs = container.querySelectorAll('.tab-item');
        var allTabs = [{ text: _loginUserLanguageResource.all, deviceTypeId: '', isAll: true }];
        for (var i = 0; i < level2Data.length; i++) allTabs.push(level2Data[i]);
        var allIds = [];
        for (var i = 0; i < level2Data.length; i++) allIds.push(level2Data[i].deviceTypeId);
        allTabs[0].deviceTypeId = allIds.join(',');
        if (index < 0 || index >= allTabs.length) return;
        for (var i = 0; i < tabs.length; i++) {
            tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
        }
        currentLevel2 = allTabs[index];
        loadData(currentLevel2);
    }

    // ================================================================
    // 3. 加载数据
    // ================================================================
    function loadData(level2Item) {
        if (!level2Item) return;
        if (!grid) grid = mini.get('logGrid');
        if (grid) {
            // 重置分页到第一页
            grid.setPageIndex(0);
            grid.load();
        }
    }

    function refreshData() {
    	resetParams();
    	doQuery();
    }

    function doQuery() {
        if (grid) grid.load();
    }
    
    function onOperationComboBeforeLoad(e) {
        var params = e.params || {};
        params.itemCode = 'action';
        e.params = params;
    }
    
    window.onDeviceComboBeforeLoad = function(e) {
        var params = e.params || {};

        var pageIndex = params.pageIndex || 0;
        var pageSize = params.pageSize || defaultWellComboxSize;
        params.start = pageIndex * pageSize;
        params.limit = pageSize;

        var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
        params.orgId = leftOrgId ? leftOrgId.getValue() : '';
        params.deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        var combo = mini.get('deviceCombo');
        params.deviceName = combo ? combo.getValue() : '';
    }


    window.onDeviceComboShowPopup = function(e) {
        var combo = e.sender;
        // 如果当前没有数据或数据为空，加载
        var data = combo.getData();
        if (!data || data.length <= 1) {
            // 先隐藏下拉，防止显示空
            combo.hidePopup();
        }
        combo.load(combo.url);
    };

    window.onDeviceComboLoad = function(e) {
        var combo = e.sender;

    };

    // ================================================================
    // 4. Grid 事件
    // ================================================================
    function onGridBeforeLoad(e) {
        var params = e.params || {};
        var pageIndex = params.pageIndex || 0;
        var pageSize = params.pageSize || 100;
        params.start = pageIndex * pageSize;
        params.limit = pageSize;

        var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
        params.orgId = leftOrgId ? leftOrgId.getValue() : '';

        var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        // 如果是全部，可能为逗号分隔字符串，后端需处理
        params.deviceType = deviceType;
        // 设备名称
        var deviceCombo = mini.get('deviceCombo');
        params.deviceName = deviceCombo ? deviceCombo.getValue() : '';
        // 操作类型
        var opCombo = mini.get('operationCombo');
        params.operationType = opCombo ? opCombo.getValue() : '';
        // 时间
        var startDate = mini.get('startDate');
        var endDate = mini.get('endDate');
        params.startDate = startDate ? startDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
        params.endDate = endDate ? endDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';

        e.params = params;
    }

    function onGridLoad(e) {
        var grid = e.sender;
        var result = e.result;
        if (result && result.columns) {
            var columns = buildColumns(result.columns);
            document.getElementById('logColumnStr').value = JSON.stringify(result.columns);
            grid.setColumns(columns);
        }
        // 如果后端返回了起始时间，自动填充查询条件（可选）
        if (result && result.start_date) {
            var startDate = mini.get('startDate');
            var endDate = mini.get('endDate');
            if (startDate && !startDate.getValue()) startDate.setValue(result.start_date);
            if (endDate && !endDate.getValue()) endDate.setValue(result.end_date);
        }
    }

    function buildColumns(colsData) {
        var cols = [];
        for (var i = 0; i < colsData.length; i++) {
            var col = colsData[i];
            var column = {
                field: col.dataIndex,
                header: col.header,
                headerAlign: 'center',
                align: 'center'
            };
            if(col.width){
            	column.width=col.width;
            }
            if(col.flex){
            	column.flex=col.flex;
            }
            if (col.dataIndex === 'id') {
                column.type = 'indexcolumn';
                column.width = 50;
                column.header = _loginUserLanguageResource.idx;
                delete column.field;
            } else if (col.dataIndex === 'createTime' || col.dataIndex === 'acqTime') {
                column.dateFormat = 'yyyy-MM-dd HH:mm:ss';
                column.width = 150;
            }
            // 如果需要锁定列，可设置 locked: true
            if (col.locked) {
                column.locked = true;
            }
            cols.push(column);
        }
        return cols;
    }

    // ================================================================
    // 5. 导出功能
    // ================================================================
    function doExport() {
        var grid = mini.get('logGrid');
        if (!grid) return;
        var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
        var deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        var dictDeviceType = deviceType;
        if (deviceType && deviceType.indexOf(',') > -1) {
            // 如果有多级，取第一个作为字典类型（具体逻辑可调整）
            dictDeviceType = currentLevel1 ? currentLevel1.deviceTypeId : deviceType;
        }
        var deviceName = mini.get('deviceCombo') ? mini.get('deviceCombo').getValue() : '';
        var operationType = mini.get('operationCombo') ? mini.get('operationCombo').getValue() : '';
        var startDate = mini.get('startDate') ? mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss') : '';
        var endDate = mini.get('endDate') ? mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss') : '';

        var fileName = _loginUserLanguageResource.deviceOperationLog;
        var title = fileName;
        var columnStr = document.getElementById('logColumnStr').value;

        // 构造导出参数
        var fields = '', heads = '';
        try {
            var columns = JSON.parse(columnStr);
            var lockedfields = '', lockedheads = '', unlockedfields = '', unlockedheads = '';
            columns.forEach(function(col) {
                if (col.hidden || col.dataIndex === 'id') return;
                var dataIndex = col.dataIndex || col.field;
                var header = col.header || col.text || col.title || dataIndex;
                if (col.locked) {
                    lockedfields += dataIndex + ',';
                    lockedheads += header + ',';
                } else {
                    unlockedfields += dataIndex + ',';
                    unlockedheads += header + ',';
                }
            });
            if (lockedfields) {
                lockedfields = lockedfields.slice(0, -1);
                lockedheads = lockedheads.slice(0, -1);
            }
            if (unlockedfields) {
                unlockedfields = unlockedfields.slice(0, -1);
                unlockedheads = unlockedheads.slice(0, -1);
            }
            fields = 'id' + (lockedfields ? ',' + lockedfields : '') + (unlockedfields ? ',' + unlockedfields : '');
            heads = (_loginUserLanguageResource.idx) + (lockedheads ? ',' + lockedheads : '') + (unlockedheads ? ',' + unlockedheads : '');
        } catch(e) {
            mini.alert(_loginUserLanguageResource.operationFailed);
            return;
        }

        var key = 'exportDeviceLog_' + Date.now();
        var url = context + '/logQueryController/exportDeviceOperationLogExcelData';
        var param = '&fields=' + fields + '&heads=' + URLencode(URLencode(heads)) +
            '&orgId=' + orgId +
            '&deviceType=' + deviceType +
            '&dictDeviceType=' + dictDeviceType +
            '&deviceName=' + URLencode(URLencode(deviceName)) +
            '&operationType=' + operationType +
            '&startDate=' + startDate +
            '&endDate=' + endDate +
            '&fileName=' + URLencode(URLencode(fileName)) +
            '&title=' + URLencode(URLencode(title)) +
            '&key=' + key;

        exportDataMask(key, 'logPanel', _loginUserLanguageResource.loadingData);
        openExcelWindow(url + '?flag=true' + param);
    }

    // ================================================================
    // 6. 国际化初始化
    // ================================================================
    function initI18n() {
        // 按钮
        var queryBtn = mini.get('queryBtn');
        if (queryBtn) queryBtn.setText(_loginUserLanguageResource.search);
        var exportBtn = mini.get('exportBtn');
        if (exportBtn) exportBtn.setText(_loginUserLanguageResource.exportData);
        var refreshBtn = mini.get('refreshBtn');
        if (refreshBtn) refreshBtn.setText(_loginUserLanguageResource.refresh);

        // 下拉框空文本
        var deviceCombo = mini.get('deviceCombo');
        if (deviceCombo) deviceCombo.setEmptyText('--' + _loginUserLanguageResource.all + '--');
        var opCombo = mini.get('operationCombo');
        if (opCombo) opCombo.setEmptyText('--' + _loginUserLanguageResource.all + '--');

        // 标签
        document.getElementById('deviceLabel').textContent = _loginUserLanguageResource.deviceName + '：';
        document.getElementById('operationLabel').textContent = _loginUserLanguageResource.operation + '：';
        document.getElementById('startTimeLabel').textContent = _loginUserLanguageResource.range + '：';
        document.getElementById('endTimeLabel').textContent = _loginUserLanguageResource.timeTo + '：';

        // 二级标签占位
        var noChildTip = document.getElementById('noChildTip');
        if (noChildTip) noChildTip.textContent = _loginUserLanguageResource.selectLevel1;
    }
    
    function resetParams(){
    	mini.get('deviceCombo').setValue('');
        mini.get('operationCombo').setValue('');
        mini.get('startDate').setValue('');
        mini.get('endDate').setValue('');
    }

    // ================================================================
    // 7. 页面初始化
    // ================================================================
    $(document).ready(function() {
        mini.parse();
        initI18n();
        
        var grid = mini.get('logGrid');
        if (grid && typeof _defaultPageSize !== 'undefined' && _defaultPageSize) {
            grid.setPageSize(parseInt(_defaultPageSize, 10));
        }
        
        buildLevel1Tabs();
        
        window.addEventListener('message', function(event) {
            var message = event.data;
            if (!message || !message.action) return;
            if (message.action === 'refresh') {
                refreshData();
            }
        });
        console.log('报警查询模块加载完成');
        console.log('设备操作日志模块加载完成');
    });

    // 暴露全局函数
    window.selectLevel1 = selectLevel1;
    window.selectLevel2 = selectLevel2;
    window.refreshData = refreshData;
    window.doQuery = doQuery;
    window.doExport = doExport;
    window.onGridBeforeLoad = onGridBeforeLoad;
    window.onGridLoad = onGridLoad;
</script>
</body>
</html>