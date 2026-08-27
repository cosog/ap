<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String moduleId = request.getParameter("moduleId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>系统日志</title>
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
        /* 主区域 */
        .system-log-area {
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
    <!-- 主区域（无标签） -->
    <div id="mainPanel" style="flex:1; display:flex; flex-direction:column; background:#f0f2f5; padding:4px;">
        <div class="system-log-area">
            <!-- 工具栏 -->
            <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8; padding:4px 8px; display:flex; align-items:center; flex-wrap:wrap; gap:6px; flex-shrink:0;">
                <button id="refreshBtn" class="mini-button" iconCls="note-refresh" onclick="refreshData()">刷新</button>
                <span class="separator"></span>
                <span id="userLabel" style="font-size:12px;color:#333;">用户：</span>
                <input id="userCombo" class="mini-combobox" style="width:150px;" emptyText="-- 全部 --" url="<%=path%>/userManagerController/loadUserComboxList" onbeforeload="onUserComboBeforeLoad" onshowpopup="onUserComboShowPopup" dataField="list" valueField="boxkey" textField="boxval" />
                <span class="separator"></span>
                <span id="operationLabel" style="font-size:12px;color:#333;">操作：</span>
                <input id="operationCombo" class="mini-combobox" style="width:120px;" emptyText="-- 全部 --" url="<%=path%>/logQueryController/loadSystemLogActionComboxList" dataField="list" valueField="boxkey" textField="boxval" />
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
                     url="<%=path%>/logQueryController/getSystemLogData"
                     dataField="totalRoot" totalField="totalCount"
                     onbeforeload="onGridBeforeLoad" onload="onGridLoad">
                    <div property="columns"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // ================================================================
    // 0. 全局变量
    // ================================================================
    var context = '<%=path%>';
    var grid = null;

    // ================================================================
    // 1. 下拉加载前传参
    // ================================================================
    window.onUserComboBeforeLoad = function(e) {
        var params = e.params || {};
        var pageIndex = params.pageIndex || 0;
        var pageSize = params.pageSize || (typeof defaultWellComboxSize !== 'undefined' ? defaultWellComboxSize : 20);
        params.start = pageIndex * pageSize;
        params.limit = pageSize;

        var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
        params.orgId = leftOrgId ? leftOrgId.getValue() : '';
        // 获取输入框当前值（用于模糊搜索）
        var combo = mini.get('userCombo');
        params.userId = combo ? combo.getValue() : '';
        e.params = params;
    };
    
    window.onUserComboShowPopup = function(e) {
        var combo = e.sender;
        // 如果当前没有数据或数据为空，加载
        var data = combo.getData();
        if (!data || data.length <= 1) {
            // 先隐藏下拉，防止显示空
            combo.hidePopup();
        }
        combo.load(combo.url);
    };

    // 操作类型下拉无需额外参数，但如果有需要可留空

    // ================================================================
    // 2. Grid 事件
    // ================================================================
    function onGridBeforeLoad(e) {
        var params = e.params || {};
        var pageIndex = params.pageIndex || 0;
        var pageSize = params.pageSize || 100;
        params.start = pageIndex * pageSize;
        params.limit = pageSize;

        var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
        params.orgId = leftOrgId ? leftOrgId.getValue() : '';

        var userCombo = mini.get('userCombo');
        params.selectUserId = userCombo ? userCombo.getValue() : '';

        var opCombo = mini.get('operationCombo');
        params.operationType = opCombo ? opCombo.getValue() : '';

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
            console.log(JSON.stringify(columns));
            document.getElementById('logColumnStr').value = JSON.stringify(result.columns);
            grid.setColumns(columns);
        }
        // 自动填充时间范围（若后端返回）
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
                delete column.flex;
                column.width = 150;
            }
            if (col.locked) {
                column.locked = true;
            }
            cols.push(column);
        }
        return cols;
    }

    // ================================================================
    // 3. 数据加载与查询
    // ================================================================
    function refreshData() {
    	resetParams();
    	doQuery();
    }

    function doQuery() {
    	if (grid) grid.load();
    }

    // ================================================================
    // 4. 导出功能
    // ================================================================
    function doExport() {
        var grid = mini.get('logGrid');
        if (!grid) return;
        var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
        var selectUserId = mini.get('userCombo') ? mini.get('userCombo').getValue() : '';
        var operationType = mini.get('operationCombo') ? mini.get('operationCombo').getValue() : '';
        var startDate = mini.get('startDate') ? mini.get('startDate').getFormValue('yyyy-MM-dd HH:mm:ss') : '';
        var endDate = mini.get('endDate') ? mini.get('endDate').getFormValue('yyyy-MM-dd HH:mm:ss') : '';

        var fileName = _loginUserLanguageResource.systemLog;
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

        var key = 'exportSystemLog_' + Date.now();
        var url = context + '/logQueryController/exportSystemLogExcelData';
        var param = '&fields=' + fields + '&heads=' + URLencode(URLencode(heads)) +
            '&orgId=' + orgId +
            '&selectUserId=' + URLencode(URLencode(selectUserId)) +
            '&operationType=' + URLencode(URLencode(operationType)) +
            '&startDate=' + startDate +
            '&endDate=' + endDate +
            '&fileName=' + URLencode(URLencode(fileName)) +
            '&title=' + URLencode(URLencode(title)) +
            '&key=' + key;

        exportDataMask(key, 'mainPanel', _loginUserLanguageResource.loadingData);
        openExcelWindow(url + '?flag=true' + param);
    }

    // ================================================================
    // 5. 国际化初始化
    // ================================================================
    function initI18n() {
        var queryBtn = mini.get('queryBtn');
        if (queryBtn) queryBtn.setText(_loginUserLanguageResource.search);
        var exportBtn = mini.get('exportBtn');
        if (exportBtn) exportBtn.setText(_loginUserLanguageResource.exportData);
        var refreshBtn = mini.get('refreshBtn');
        if (refreshBtn) refreshBtn.setText(_loginUserLanguageResource.refresh);

        var userCombo = mini.get('userCombo');
        if (userCombo) userCombo.setEmptyText('--' + _loginUserLanguageResource.all + '--');
        var opCombo = mini.get('operationCombo');
        if (opCombo) opCombo.setEmptyText('--' + _loginUserLanguageResource.all + '--');

        document.getElementById('userLabel').textContent = _loginUserLanguageResource.user + '：';
        document.getElementById('operationLabel').textContent = _loginUserLanguageResource.operation + '：';
        document.getElementById('startTimeLabel').textContent = _loginUserLanguageResource.range + '：';
        document.getElementById('endTimeLabel').textContent = _loginUserLanguageResource.timeTo + '：';
    }

    function resetParams(){
    	mini.get('userCombo').setValue('');
        mini.get('operationCombo').setValue('');
        mini.get('startDate').setValue('');
        mini.get('endDate').setValue('');
    }
    // ================================================================
    // 6. 页面初始化
    // ================================================================
    $(document).ready(function() {
        mini.parse();
        initI18n();

        grid = mini.get('logGrid');
        if (grid && typeof _defaultPageSize !== 'undefined' && _defaultPageSize) {
            grid.setPageSize(parseInt(_defaultPageSize, 10));
        }

        // 初始加载
        if (grid) grid.load();

        // 监听父页面刷新消息（可选）
        window.addEventListener('message', function(event) {
            var message = event.data;
            if (!message || !message.action) return;
            if (message.action === 'refresh') {
                refreshData();
            }
        });

        console.log('系统日志模块加载完成');
    });

    // 暴露全局函数
    window.refreshData = refreshData;
    window.doQuery = doQuery;
    window.doExport = doExport;
    window.onGridBeforeLoad = onGridBeforeLoad;
    window.onGridLoad = onGridLoad;
</script>
</body>
</html>