<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String moduleId = request.getParameter("moduleId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>数据维护</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        /* ===== 全局基础样式（完全复用报警查询） ===== */
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

        /* ===== 左侧面板（设备列表） ===== */
        .left-panel {
            display: flex;
            flex-direction: column;
            height: 100%;
            background: #f0f2f5;
            padding: 4px;
        }
        .device-overview-area {
            flex: 1;
            overflow: hidden;
            background: #fff;
            border-radius: 4px;
            box-shadow: 0 1px 4px rgba(0,0,0,0.06);
            display: flex;
            flex-direction: column;
        }
        .device-overview-area .mini-datagrid {
            flex: 1;
            width: 100%;
            height: 100%;
        }

        /* ===== 右侧面板 ===== */
        .right-panel {
            display: flex;
            flex-direction: column;
            height: 100%;
            background: #f0f2f5;
            padding: 4px;
        }
        .right-panel .mini-tabs {
            flex: 1;
            width: 100%;
            height: 100%;
        }
        .right-panel .mini-tabs-body,
        .right-panel .mini-tab-body {
            height: 100% !important;
            padding: 0 !important;
            margin: 0 !important;
            overflow: hidden !important;
        }

        /* ===== 子Tab内容容器 ===== */
        .sub-tab-container {
            width: 100%;
            height: 100%;
            overflow: hidden;
            display: flex;
            flex-direction: column;
            background: #fff;
        }
        .sub-tab-container .mini-toolbar {
            flex-shrink: 0;
            border-bottom: 1px solid #e8e8e8;
            padding: 4px 8px;
            display: flex;
            align-items: center;
            flex-wrap: wrap;
            gap: 4px;
            background: #fafafa;
        }
        .sub-tab-container .mini-datagrid {
            flex: 1;
            width: 100%;
            height: 100%;
        }

        .mini-toolbar .separator {
            width: 1px;
            height: 20px;
            background: #ddd;
            margin: 0 4px;
        }
        .empty-msg { color: #999; font-size: 13px; text-align: center; padding: 20px; }
    </style>
</head>
<body>
<div class="alarm-container">
    <!-- 主区域：flex行，一级标签在底部所以order:0 -->
    <div style="display:flex; flex:1; overflow:hidden; order:0;">
        <!-- 二级标签（左侧） -->
        <div class="level2-sidebar" id="level2Sidebar">
            <div class="no-child-tip" id="noChildTip">选择一级</div>
        </div>

        <!-- 主体内容 -->
        <div id="dataMaintainingPanel" style="flex:1; overflow:hidden;">
            <div class="mini-splitter" style="width:100%; height:100%;" vertical="false">
                <!-- 左侧：设备列表 -->
                <div size="30%" showCollapseButton="true" minSize="200" collapseDirection="left">
                    <div class="left-panel" style="height:100%; background:#f0f2f5; padding:4px; display:flex; flex-direction:column; overflow:hidden;">
                        <div class="device-overview-area" style="height:100%;">
                            <div class="mini-toolbar" style="border:0;border-bottom:1px solid #e8e8e8;padding:4px 8px;display:flex;align-items:center;gap:6px;flex-shrink:0;">
                                <button id="refreshDeviceBtn" class="mini-button" iconCls="note-refresh">刷新</button>
                                <span class="separator"></span>
                                <input id="deviceListCombo" class="mini-combobox" style="width:140px;" emptyText="-- 全部 --"  url="<%=path%>/wellInformationManagerController/loadWellComboxList" onbeforeload="onDeviceComboBeforeLoad" onshowpopup="onDeviceComboShowPopup" onload="onDeviceComboLoad" dataField="list" totalField="totals" valueField="boxkey" textField="boxval" onvaluechanged="onDeviceChange" />
                            </div>
                            <div style="flex:1;overflow:hidden;">
                                <div id="deviceGrid" class="mini-datagrid" style="width:100%;height:100%;" idField="id" pageSize="100" allowResize="true" allowAlternating="true" 
                                	url="<%=path%>/calculateManagerController/getDeviceList" dataField="totalRoot" totalField="totalCount" 
                                	onselectionchanged="onDeviceListRowSelect" onload="onDeviceGridLoad" 
                                	onbeforeload="onDeviceGridBeforeLoad">
                                	<div property="columns"><!-- 动态生成 --></div>
                                	<div property="emptyText" class="empty-msg">暂无设备数据</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 右侧：功能Tabs -->
                <div size="70%" showCollapseButton="false" minSize="300" >
                    <div class="right-panel" style="height:100%; background:#f0f2f5; padding:4px; display:flex; flex-direction:column; overflow:hidden;">
                        <div class="mini-fit">
                            <div id="mainTabs" class="mini-tabs" style="height:100%; width:100%; overflow:hidden;" activeIndex="0" tabPosition="bottom" onactivechanged="onMainTabActiveChanged">
                                <!-- ====== 1. 采集数据 ====== -->
                                <div title="采集数据" name="acquisition" style="height:100%;">
                                    <div class="mini-fit">
                                        <div id="acqSubTabs" class="mini-tabs" style="height:100%; width:100%; overflow:hidden;" activeIndex="0" tabPosition="left" onactivechanged="onAcqSubTabActiveChanged">
                                            <!-- 实时数据 -->
                                            <div title="实时数据" name="realtime" style="height:100%;">
                                                <div class="sub-tab-container">
                                                    <div class="mini-toolbar">
                                                        <input id="acqRealtimeStartDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                                        <span style="margin-left:8px;font-size:12px;color:#333;" id="acqRealtimeToLabel">至：</span>
                                                        <input id="acqRealtimeEndDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                                        <span class="separator"></span>
                                                        <button id="acqRealtimeQueryBtn" class="mini-button" iconCls="search" onclick="onAcqRealtimeQuery()">查询</button>
                                                        <span style="flex:1;"></span>
                                                        <button id="acqRealtimeDeleteBtn" class="mini-button" iconCls="delete" onclick="deleteRealtimeData()">删除</button>
                                                    </div>
                                                    <div id="realtimeAcqGrid" class="mini-datagrid"
                                                    	style="width:100%; height:100%;"
                                                        idField="id" pageSize="100" allowResize="true" allowAlternating="true" multiSelect="true"
                                                        dataField="totalRoot" totalField="totalCount" 
                                                        showPager="true" showPageInfo="true" 
                                                        onbeforeload="onRealtimeGridBeforeLoad"
     													onload="onRealtimeGridLoad" >
                                                        <div property="columns"></div>
                                                        <div property="emptyText" class="empty-msg">暂无实时数据</div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- 历史数据 -->
                                            <div title="历史数据" name="history" style="height:100%;">
                                                <div class="sub-tab-container">
                                                    <div class="mini-toolbar">
                                                        <input id="acqHistoryStartDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                                        <span style="margin-left:8px;font-size:12px;color:#333;" id="acqHistoryToLabel">至：</span>
                                                        <input id="acqHistoryEndDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                                        <span class="separator"></span>
                                                        <button id="acqHistoryQueryBtn" class="mini-button" iconCls="search" onclick="onAcqHistoryQuery()">查询</button>
                                                        <span style="flex:1;"></span>
                                                        <button id="acqHistoryDeleteBtn" class="mini-button" iconCls="delete" onclick="deleteHistoryData()">删除</button>
                                                    </div>
                                                    <div id="historyAcqGrid" class="mini-datagrid"
                                                         style="width:100%; height:100%;"
                                                         idField="id" pageSize="100" allowResize="true" allowAlternating="true" multiSelect="true"
                                                         dataField="totalRoot" totalField="totalCount" 
                                                         showPager="true" showPageInfo="true" 
                                                         onbeforeload="onHistoryGridBeforeLoad"
     													 onload="onHistoryGridLoad" >
                                                        <div property="columns"></div>
                                                        <div property="emptyText" class="empty-msg">暂无历史数据</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- ====== 2. 功图计算 ====== -->
                                <div title="功图计算" name="srp" style="height:100%;" visible="false" >
                                    <div class="mini-fit">
                                        <div id="srpSubTabs" class="mini-tabs" style="height:100%; width:100%; overflow:hidden;" activeIndex="0" tabPosition="left" onactivechanged="onSrpSubTabActiveChanged">
                                            <!-- 单条记录 -->
                                            <div title="单条记录" name="single" style="height:100%;">
                                                <div class="sub-tab-container">
                                                    <div class="mini-toolbar">
                                                        <span id="srpSingleTimeTypeLabel">时间类型：</span>
                                                        <input id="srpSingleTimeType" class="mini-combobox" style="width:120px;" data="[{'id':0,'text':'功图采集时间'},{'id':1,'text':'云端采集时间'}]" value="0" />
                                                        <span class="separator"></span>
                                                        <input id="srpSingleStartDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                                        <span style="margin-left:8px;font-size:12px;color:#333;" id="srpSingleToLabel">至：</span>
                                                        <input id="srpSingleEndDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                                        <span class="separator"></span>
                                                        <span id="srpSingleStatusLabel">计算状态：</span>
                                                        <input id="srpSingleStatusCombo" class="mini-combobox" style="width:100px;" emptyText="--全部--" />
                                                        <span class="separator"></span>
                                                        <span id="srpSingleResultLabel">工况：</span>
                                                        <input id="srpSingleResultCombo" class="mini-combobox" style="width:120px;" emptyText="--全部--" />
                                                        <span class="separator"></span>
                                                        <button id="srpSingleQueryBtn" class="mini-button" iconCls="search">查询</button>
                                                    </div>
                                                    <div class="mini-toolbar">
                                                        <span style="flex:1;"></span>
                                                        <button id="srpSingleEditBtn" class="mini-button" iconCls="save">修改历史数据计算</button>
                                                        <button id="srpSingleLinkBtn" class="mini-button" iconCls="save">关联生产数据计算</button>
                                                        <button id="srpSingleExportBtn" class="mini-button" iconCls="export">导出请求数据</button>
                                                        <button id="srpSingleDeleteBtn" class="mini-button" iconCls="delete">删除</button>
                                                    </div>
                                                    <div id="srpSingleGrid" class="mini-datagrid"
                                                         style="width:100%; height:100%;"
                                                         idField="recordId" pageSize="100" allowResize="true" allowAlternating="true"
                                                         showPager="true" showPageInfo="true"
                                                         allowCellEdit="true" allowCellSelect="true">
                                                        <div property="columns">
                                                            <div type="checkcolumn" width="30"></div>
                                                            <div field="deviceName" width="120" header="设备名称"></div>
                                                            <div field="acqTime" width="150" dateFormat="yyyy-MM-dd HH:mm:ss" header="采集时间"></div>
                                                        </div>
                                                        <div property="emptyText" class="empty-msg">暂无单条记录</div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- 汇总记录 -->
                                            <div title="汇总记录" name="total" style="height:100%;">
                                                <div class="sub-tab-container">
                                                    <div class="mini-toolbar">
                                                        <input id="srpTotalStartDate" class="mini-datepicker" style="width:100px;" format="yyyy-MM-dd" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                                        <span style="margin-left:8px;font-size:12px;color:#333;" id="srpTotalToLabel">至：</span>
                                                        <input id="srpTotalEndDate" class="mini-datepicker" style="width:100px;" format="yyyy-MM-dd" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                                        <span style="flex:1;"></span>
                                                        <button id="srpTotalQueryBtn" class="mini-button" iconCls="search">查询</button>
                                                        <button id="srpTotalReCalcBtn" class="mini-button" iconCls="edit" hidden>重新汇总</button>
                                                        <button id="srpTotalExportBtn" class="mini-button" iconCls="export" hidden>导出请求数据</button>
                                                    </div>
                                                    <div id="srpTotalGrid" class="mini-datagrid"
                                                         style="width:100%; height:100%;"
                                                         idField="id" pageSize="100" allowResize="true" allowAlternating="true"
                                                         showPager="true" showPageInfo="true">
                                                        <div property="columns">
                                                            <div type="checkcolumn" width="30"></div>
                                                            <div field="deviceName" width="120" header="设备名称"></div>
                                                            <div field="calDate" width="150" dateFormat="yyyy-MM-dd" header="计算日期"></div>
                                                        </div>
                                                        <div property="emptyText" class="empty-msg">暂无汇总记录</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- ====== 3. 转速计产 ====== -->
                                <div id="pcpCalculateMaintainingPanel" title="转速计产" name="pcp" style="height:100%;" visible="false" >
                                    <div class="mini-fit">
                                        <div id="pcpSubTabs" class="mini-tabs" style="height:100%; width:100%; overflow:hidden;" activeIndex="0" tabPosition="left" onactivechanged="onPcpSubTabActiveChanged">
                                            <!-- 单条记录 -->
                                            <div title="单条记录" name="single" style="height:100%;">
                                                <div class="sub-tab-container">
                                                    <div class="mini-toolbar">
                                                        <input id="pcpSingleStartDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                                        <span style="margin-left:8px;font-size:12px;color:#333;" id="pcpSingleToLabel">至：</span>
                                                        <input id="pcpSingleEndDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                                        <span class="separator"></span>
                                                        <span id="pcpSingleStatusLabel">计算状态：</span>
                                                        <input id="pcpSingleStatusCombo" class="mini-combobox" style="width:100px;" emptyText="--全部--" />
                                                        <span class="separator"></span>
                                                        <button id="pcpSingleQueryBtn" class="mini-button" iconCls="search">查询</button>
                                                    </div>
                                                    <div class="mini-toolbar">
                                                        <span style="flex:1;"></span>
                                                        <button id="pcpSingleEditBtn" class="mini-button" iconCls="save">修改历史数据计算</button>
                                                        <button id="pcpSingleLinkBtn" class="mini-button" iconCls="save">关联生产数据计算</button>
                                                        <button id="pcpSingleExportBtn" class="mini-button" iconCls="export">导出请求数据</button>
                                                        <button id="pcpSingleDeleteBtn" class="mini-button" iconCls="delete">删除</button>
                                                    </div>
                                                    <div id="pcpSingleGrid" class="mini-datagrid"
                                                         style="width:100%; height:100%;"
                                                         idField="recordId" pageSize="100" allowResize="true" allowAlternating="true"
                                                         showPager="true" showPageInfo="true"
                                                         allowCellEdit="true" allowCellSelect="true">
                                                        <div property="columns">
                                                            <div type="checkcolumn" width="30"></div>
                                                            <div field="deviceName" width="120" header="设备名称"></div>
                                                            <div field="acqTime" width="150" dateFormat="yyyy-MM-dd HH:mm:ss" header="采集时间"></div>
                                                        </div>
                                                        <div property="emptyText" class="empty-msg">暂无单条记录</div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- 汇总记录 -->
                                            <div title="汇总记录" name="total" style="height:100%;">
                                                <div class="sub-tab-container">
                                                    <div class="mini-toolbar">
                                                        <input id="pcpTotalStartDate" class="mini-datepicker" style="width:100px;" format="yyyy-MM-dd" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                                        <span style="margin-left:8px;font-size:12px;color:#333;" id="pcpTotalToLabel">至：</span>
                                                        <input id="pcpTotalEndDate" class="mini-datepicker" style="width:100px;" format="yyyy-MM-dd" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                                        <span style="flex:1;"></span>
                                                        <button id="pcpTotalQueryBtn" class="mini-button" iconCls="search">查询</button>
                                                        <button id="pcpTotalReCalcBtn" class="mini-button" iconCls="edit" hidden>重新汇总</button>
                                                        <button id="pcpTotalExportBtn" class="mini-button" iconCls="export" hidden>导出请求数据</button>
                                                    </div>
                                                    <div id="pcpTotalGrid" class="mini-datagrid"
                                                         style="width:100%; height:100%;"
                                                         idField="id" pageSize="100" allowResize="true" allowAlternating="true"
                                                         showPager="true" showPageInfo="true">
                                                        <div property="columns">
                                                            <div type="checkcolumn" width="30"></div>
                                                            <div field="deviceName" width="120" header="设备名称"></div>
                                                            <div field="calDate" width="150" dateFormat="yyyy-MM-dd" header="计算日期"></div>
                                                        </div>
                                                        <div property="emptyText" class="empty-msg">暂无汇总记录</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
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
    // 一级/二级标签构建（完全复用报警查询逻辑，不加载数据）
    // ================================================================
    var context = '<%=path%>';
    var tabInfo = null;
    try {
        if (window.parent && window.parent.tabInfo) tabInfo = window.parent.tabInfo;
    } catch(e) { console.warn('无法获取 tabInfo', e); }

    var currentLevel1 = null, currentLevel2 = null;
    var level1Data = [], level2Data = [];
    
 // 全局变量
    var currentDeviceId = 0;
    var currentDeviceName = '';
    var currentApplicationScenarios = 0;
    var currentDeviceCalculateType = 0;

    function buildLevel1Tabs() {
        var container = document.getElementById('level1Footer');
        if (!container) return;
        container.innerHTML = '';
        if (!tabInfo || !tabInfo.children || tabInfo.children.length === 0) {
            container.innerHTML = '<span class="loading-tip">' + (_loginUserLanguageResource.emptyMsg || '无设备类型') + '</span>';
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

    function buildLevel2Tabs(parentItem) {
        var container = document.getElementById('level2Sidebar');
        if (!container) return;
        container.innerHTML = '';
        var children = parentItem.children || [];
        if (!children || children.length === 0) {
            container.innerHTML = '<div class="no-child-tip">' + (_loginUserLanguageResource.emptyMsg || '无子类型') + '</div>';
            currentLevel2 = null;
            return;
        }
        level2Data = children;
        var allIds = [];
        for (var i = 0; i < children.length; i++) allIds.push(children[i].deviceTypeId);
        var allTabs = [{ text: _loginUserLanguageResource.all || '全部', deviceTypeId: allIds.join(','), isAll: true }];
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
            mini.get('deviceListCombo').setValue('');
            refreshData();
        }
    }

    function selectLevel2(index) {
        var container = document.getElementById('level2Sidebar');
        var tabs = container.querySelectorAll('.tab-item');
        var allTabs = [{ text: _loginUserLanguageResource.all || '全部', deviceTypeId: '', isAll: true }];
        for (var i = 0; i < level2Data.length; i++) allTabs.push(level2Data[i]);
        var allIds = [];
        for (var i = 0; i < level2Data.length; i++) allIds.push(level2Data[i].deviceTypeId);
        allTabs[0].deviceTypeId = allIds.join(',');
        if (index < 0 || index >= allTabs.length) return;
        for (var i = 0; i < tabs.length; i++) {
            tabs[i].className = 'tab-item' + (i === index ? ' active' : '');
        }
        currentLevel2 = allTabs[index];
        refreshData();
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
        var combo = mini.get('deviceListCombo');
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
    
    function onDeviceChange() { 
    	refreshData(); 
    }

    function refreshData(){
    	var grid = mini.get('deviceGrid');
        if (grid) grid.load();
    }
    
    function onDeviceGridBeforeLoad(e) {
        var params = e.params || {};
        var pageIndex = params.pageIndex || 0;
        var pageSize = params.pageSize || 20;
        params.start = pageIndex * pageSize;
        params.limit = pageSize;
        var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
        params.orgId = leftOrgId ? leftOrgId.getValue() : '';
        params.deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
        var combo = mini.get('deviceListCombo');
        params.deviceName = combo ? combo.getValue() : '';
    }
    function onDeviceGridLoad(e) {
        var grid = e.sender, result = e.result;
        if (result && result.columns) {
            var columns = buildDeviceListColumns(result.columns);
            setTimeout(function() {
                grid.setColumns(columns);
                grid.doLayout();
            }, 50);
        }
        
        var data = grid.getData();
        if (data && data.length > 0){
        	grid.select(0);
        }else{
        	currentDeviceId=0;
            currentDeviceName = '';
            currentDeviceCalculateType=0;
        	updateDataTypeTabs(0);
        	clearAllGrids();
        }
    }
    function buildDeviceListColumns(colsData) {
        var cols = [];
        for (var i = 0; i < colsData.length; i++) {
            var col = colsData[i];
            var column = {
                field: col.dataIndex,
                header: col.header,
                headerAlign: 'center',
                align: 'center',
                width: col.width || 100
            };
            if (col.dataIndex === 'id') {
                column.type = 'indexcolumn';
                column.width = 40;
                column.header = _loginUserLanguageResource.idx;
                delete column.field;
            } else if (col.dataIndex === 'deviceName') {
                column.width = 140;
                column.locked = true;
            } else if (col.dataIndex === 'acqTime' || col.dataIndex === 'alarmTime') {
                column.dateFormat = 'yyyy-MM-dd HH:mm:ss';
                column.width = 150;
            }
            cols.push(column);
        }
        return cols;
    }
    function onDeviceListRowSelect(e) {
    	
    	var selected = e.selected;
        if (selected) {
            currentDeviceId = selected.id;
            currentDeviceName = selected.deviceName || '';
            currentDeviceCalculateType = selected.calculateType;
            updateDataTypeTabs(selected.calculateType);
         	// 重置所有查询参数
            resetAllQueryParams();
            // 加载当前激活标签的数据
            refreshCurrentTabData();
        }else{
        	currentDeviceId = 0;
            currentDeviceName = '';
            currentDeviceCalculateType = 0;
        	updateDataTypeTabs(0);
        	clearAllGrids();
        }
    }
    
    //动态更新数据类型标签
    function updateDataTypeTabs(calculateType) {
    	calculateType=parseInt(calculateType);
        var mainTabs = mini.get('mainTabs');
        if (!mainTabs) return;
        
        var activeTab = mainTabs.getActiveTab();
        var tabs = mainTabs.getTabs();
        if (!tabs || tabs.length < 3) return;
        
        var acquisitionTab = tabs[0];
        var srpTab = tabs[1];
        var pcpTab = tabs[2];
        
        // 根据计算类型设置可见性
        // calculateType: 0-仅采集, 1-采集+功图, 2-采集+转速
        var showSRP = (calculateType === 1);
        var showPCP = (calculateType === 2);
        
        mainTabs.updateTab(srpTab, { visible: showSRP });
        mainTabs.updateTab(pcpTab, { visible: showPCP });
        // 采集数据始终可见
        mainTabs.updateTab(acquisitionTab, { visible: true });
        
        // 如果当前激活的Tab被隐藏，切换到采集数据
        if (activeTab) {
            var isActiveVisible = true;
            if (activeTab === srpTab && !showSRP) isActiveVisible = false;
            if (activeTab === pcpTab && !showPCP) isActiveVisible = false;
            if (!isActiveVisible) {
                mainTabs.activeTab(acquisitionTab);
            }
        }else{
        	mainTabs.activeTab(acquisitionTab);
        }
    }
    
 // 重置所有子标签的查询参数（采集数据、功图计算、转速计产）
    function resetAllQueryParams() {
        // 采集数据 - 实时
        var realtimeStart = mini.get('acqRealtimeStartDate');
        var realtimeEnd = mini.get('acqRealtimeEndDate');
        if (realtimeStart) realtimeStart.setValue('');
        if (realtimeEnd) realtimeEnd.setValue('');
        
        // 采集数据 - 历史
        var historyStart = mini.get('acqHistoryStartDate');
        var historyEnd = mini.get('acqHistoryEndDate');
        if (historyStart) historyStart.setValue('');
        if (historyEnd) historyEnd.setValue('');
        
        // 功图计算 - 单条
        var srpSingleStart = mini.get('srpSingleStartDate');
        var srpSingleEnd = mini.get('srpSingleEndDate');
        if (srpSingleStart) srpSingleStart.setValue('');
        if (srpSingleEnd) srpSingleEnd.setValue('');
        var srpStatus = mini.get('srpSingleStatusCombo');
        if (srpStatus) srpStatus.setValue('');
        var srpResult = mini.get('srpSingleResultCombo');
        if (srpResult) srpResult.setValue('');
        
        // 功图计算 - 汇总
        var srpTotalStart = mini.get('srpTotalStartDate');
        var srpTotalEnd = mini.get('srpTotalEndDate');
        if (srpTotalStart) srpTotalStart.setValue('');
        if (srpTotalEnd) srpTotalEnd.setValue('');
        
        // 转速计产 - 单条
        var pcpSingleStart = mini.get('pcpSingleStartDate');
        var pcpSingleEnd = mini.get('pcpSingleEndDate');
        if (pcpSingleStart) pcpSingleStart.setValue('');
        if (pcpSingleEnd) pcpSingleEnd.setValue('');
        var pcpStatus = mini.get('pcpSingleStatusCombo');
        if (pcpStatus) pcpStatus.setValue('');
        
        // 转速计产 - 汇总
        var pcpTotalStart = mini.get('pcpTotalStartDate');
        var pcpTotalEnd = mini.get('pcpTotalEndDate');
        if (pcpTotalStart) pcpTotalStart.setValue('');
        if (pcpTotalEnd) pcpTotalEnd.setValue('');
    }
 
 	// 获取当前激活的父Tab和子Tab
    function getActiveTabs() {
        var mainTabs = mini.get('mainTabs');
        if (!mainTabs) return null;
        var activeMain = mainTabs.getActiveTab();
        if (!activeMain) return null;
        
        var activeMainName = activeMain.name; // acquisition, srp, pcp
        var subTabs = null;
        if (activeMainName === 'acquisition') {
            subTabs = mini.get('acqSubTabs');
        } else if (activeMainName === 'srp') {
            subTabs = mini.get('srpSubTabs');
        } else if (activeMainName === 'pcp') {
            subTabs = mini.get('pcpSubTabs');
        }
        if (!subTabs) return null;
        var activeSub = subTabs.getActiveTab();
        if (!activeSub) return null;
        
        return {
            mainName: activeMainName,
            subName: activeSub.name // realtime, history, single, total
        };
    }
 	
 	// 主Tab切换事件
    function onMainTabActiveChanged(e) {
        // 切换主Tab时，刷新当前子Tab的数据
        refreshCurrentTabData();
    }

    // 采集数据子Tab切换事件
    function onAcqSubTabActiveChanged(e) {
        refreshCurrentTabData();
    }

    // 功图计算子Tab切换事件
    function onSrpSubTabActiveChanged(e) {
        refreshCurrentTabData();
    }

    // 转速计产子Tab切换事件
    function onPcpSubTabActiveChanged(e) {
        refreshCurrentTabData();
    }
 	
 	// 根据当前激活的Tab刷新对应的数据
    function refreshCurrentTabData() {
        var active = getActiveTabs();
        if (!active) return;
        if (currentDeviceId === 0) {
            // 没有选中设备，清空表格
            clearAllGrids();
            return;
        }
        
        if (active.mainName === 'acquisition') {
            if (active.subName === 'realtime') {
                loadRealtimeData();
            } else if (active.subName === 'history') {
                loadHistoryData();
            }
        } else if (active.mainName === 'srp') {
            if (active.subName === 'single') {
                loadSRPSingleData();
            } else if (active.subName === 'total') {
                loadSRPTotalData();
            }
        } else if (active.mainName === 'pcp') {
            if (active.subName === 'single') {
                loadPCPSingleData();
            } else if (active.subName === 'total') {
                loadPCPTotalData();
            }
        }
    }
 
 // 清空所有表格数据
    function clearAllGrids() {
        var gridIds = ['realtimeAcqGrid', 'historyAcqGrid', 'srpSingleGrid', 'srpTotalGrid', 'pcpSingleGrid', 'pcpTotalGrid'];
        gridIds.forEach(function(id) {
            var grid = mini.get(id);
            if (grid) grid.setData([]);
        });
    }
 
 // ================================================================
 // 实时数据加载
 // ================================================================

 function loadRealtimeData() {
     var grid = mini.get('realtimeAcqGrid');
     if (!grid) return;
     
     // 设置URL
     grid.setUrl(context + '/calculateManagerController/getRealtimeAcquisitionData');
     
     // 构造参数（在onbeforeload中动态设置）
     // 重新加载
     grid.load();
 }

 // 实时数据查询按钮
 function onAcqRealtimeQuery() {
     loadRealtimeData();
 }

 // 实时数据删除
 function deleteRealtimeData() {
     var grid = mini.get('realtimeAcqGrid');
     if (!grid) return;
     var rows = grid.getSelecteds();
     if (rows.length === 0) {
         mini.alert(_loginUserLanguageResource.checkOne);
         return;
     }
     var acqTimeList = [];
     for (var i = 0; i < rows.length; i++) {
         acqTimeList.push(formatDate(rows[i].acqTime, 'yyyy-MM-dd HH:mm:ss'));
     }
     // 确认删除
     mini.confirm(_loginUserLanguageResource.confirmDelete,_loginUserLanguageResource.tip, function(action) {
         if (action === 'ok') {
             $.ajax({
                 url: context + '/calculateManagerController/deleteRealtimeAcquisitionData',
                 type: 'POST',
                 data: {
                     deviceId: currentDeviceId,
                     calculateType: currentDeviceCalculateType,
                     acqTimeList: acqTimeList.join(',')
                 },
                 dataType: 'json',
                 success: function(result) {
                     if (result.success) {
                         mini.alert(_loginUserLanguageResource.deleteSuccessfully);
                         loadRealtimeData();
                     } else {
                         mini.alert(result.msg || _loginUserLanguageResource.deleteFailed);
                     }
                 },
                 error: function() {
                     mini.alert(_loginUserLanguageResource.requestFailed);
                 }
             });
         }
     });
 }

 // ================================================================
 // 实时/历史 Grid 事件
 // ================================================================

 function onRealtimeGridBeforeLoad(e) {
     var params = e.params || {};
     // 分页参数
     var pageIndex = params.pageIndex || 0;
     var pageSize = params.pageSize || 100;
     params.start = pageIndex * pageSize;
     params.limit = pageSize;
     
     var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
     params.orgId = leftOrgId ? leftOrgId.getValue() : '';
     params.deviceId = currentDeviceId;
     params.deviceName = currentDeviceName;
     params.deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
     params.dictDeviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0'; // 简化
     params.timeType = 1; // 固定为1（云端采集时间）
     params.calculateType = currentDeviceCalculateType;
     
     // 实时数据日期参数（可选）
     var startDate = mini.get('acqRealtimeStartDate');
     var endDate = mini.get('acqRealtimeEndDate');
     params.startDate = startDate ? startDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
     params.endDate = endDate ? endDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
     
     e.params = params;
 }

 function onRealtimeGridLoad(e) {
     var grid = e.sender;
     var result = e.result;
     if (result && result.columns) {
    	 if (result && result.start_date) {
             var startDate = mini.get('acqRealtimeStartDate');
             var endDate = mini.get('acqRealtimeEndDate');
             if (startDate && !startDate.getValue()) startDate.setValue(result.start_date);
             if (endDate && !endDate.getValue()) endDate.setValue(result.end_date);
         }
    	 
         var columns = buildAcquisitionColumns(result.columns);
         grid.setColumns(columns);
         grid.doLayout();
         
         var rows = grid.getSelecteds();
         if(rows.length>0){
        	 grid.deselectAll(false);//是否激发选择事件
         }
     }
     // 更新记录数显示（如果有）
 }

 function buildAcquisitionColumns(colsData) {
     var cols = [];
     cols.push({
         type: "checkcolumn",
         width: 40,
         header: "",
         headerAlign: "center",
         align: "center"
     });
     // 第一列复选框已经在HTML中定义，所以从第二列开始
     for (var i = 0; i < colsData.length; i++) {
         var col = colsData[i];
         var column = {
             field: col.dataIndex,
             header: col.header,
             headerAlign: 'center',
             align: 'center',
             width: col.width || 100
         };
         if (col.dataIndex === 'id') {
             column.type = 'indexcolumn';
             column.width = 50;
             column.header = _loginUserLanguageResource.idx;
             delete column.field;
         }else if (col.dataIndex === 'acqTime') {
             column.dateFormat = 'yyyy-MM-dd HH:mm:ss';
             column.width = 150;
         } else if (col.dataIndex === 'deviceName') {
             column.width = 120;
         }
         cols.push(column);
     }
     return cols;
 }

 // ================================================================
 // 历史数据加载（类似，稍后实现）
 // ================================================================

 function loadHistoryData() {
     var grid = mini.get('historyAcqGrid');
     if (!grid) return;
     grid.setUrl(context + '/calculateManagerController/getHistoryAcquisitionData');
     grid.load();
 }

 function onHistoryGridBeforeLoad(e) {
     var params = e.params || {};
     var pageIndex = params.pageIndex || 0;
     var pageSize = params.pageSize || 100;
     params.start = pageIndex * pageSize;
     params.limit = pageSize;
     
     var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
     params.orgId = leftOrgId ? leftOrgId.getValue() : '';
     params.deviceId = currentDeviceId;
     params.deviceName = currentDeviceName;
     params.deviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
     params.dictDeviceType = currentLevel2 ? currentLevel2.deviceTypeId : '0';
     params.timeType = 1;
     params.calculateType = currentDeviceCalculateType;
     
     var startDate = mini.get('acqHistoryStartDate');
     var endDate = mini.get('acqHistoryEndDate');
     params.startDate = startDate ? startDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
     params.endDate = endDate ? endDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
     
     e.params = params;
 }

 function onHistoryGridLoad(e) {
     var grid = e.sender;
     var result = e.result;
     if (result && result.columns) {
    	 if (result && result.start_date) {
             var startDate = mini.get('acqHistoryStartDate');
             var endDate = mini.get('acqHistoryEndDate');
             if (startDate && !startDate.getValue()) startDate.setValue(result.start_date);
             if (endDate && !endDate.getValue()) endDate.setValue(result.end_date);
         }
    	 
         var columns = buildAcquisitionColumns(result.columns);
         grid.setColumns(columns);
         grid.doLayout();
         
         var rows = grid.getSelecteds();
         if(rows.length>0){
        	 grid.deselectAll(false);//是否激发选择事件
         }
     }
 }

 // 历史数据查询
 function onAcqHistoryQuery() {
     loadHistoryData();
 }

 // 历史数据删除
 function deleteHistoryData() {
     var grid = mini.get('historyAcqGrid');
     if (!grid) return;
     var rows = grid.getSelecteds();
     if (rows.length === 0) {
         mini.alert(_loginUserLanguageResource.checkOne);
         return;
     }
     var recordIds = [];
     for (var i = 0; i < rows.length; i++) {
         recordIds.push(rows[i].recordId);
     }
     mini.confirm(_loginUserLanguageResource.confirmDelete,_loginUserLanguageResource.tip, function(action) {
         if (action === 'ok') {
             $.ajax({
                 url: context + '/calculateManagerController/deleteHistoryAcquisitionData',
                 type: 'POST',
                 data: {
                     deviceId: currentDeviceId,
                     calculateType: currentDeviceCalculateType,
                     recordIds: recordIds.join(',')
                 },
                 dataType: 'json',
                 success: function(result) {
                     if (result.success) {
                         mini.alert(_loginUserLanguageResource.deleteSuccessfully);
                         loadHistoryData();
                     } else {
                         mini.alert(result.msg || _loginUserLanguageResource.deleteFailed);
                     }
                 },
                 error: function() {
                     mini.alert(_loginUserLanguageResource.requestFailed);
                 }
             });
         }
     });
 }
 
 	function loadSRPSingleData() {
	    // TODO
	    console.log('loadSRPSingleData');
	}
	function loadSRPTotalData() {
	    // TODO
	    console.log('loadSRPTotalData');
	}
	function loadPCPSingleData() {
	    // TODO
	    console.log('loadPCPSingleData');
	}
	function loadPCPTotalData() {
	    // TODO
	    console.log('loadPCPTotalData');
	}
    
    // ================================================================
    // 国际化初始化
    // ================================================================
    function initI18n() {
        // ---------- 按钮 ----------
        var btnMap = {
            'refreshDeviceBtn': 'refresh',
            'acqRealtimeQueryBtn': 'search',
            'acqRealtimeDeleteBtn': 'deleteData',
            'acqHistoryQueryBtn': 'search',
            'acqHistoryDeleteBtn': 'deleteData',
            'srpSingleQueryBtn': 'search',
            'srpSingleEditBtn': 'editHistoryDataCalculate',
            'srpSingleLinkBtn': 'productionDataCorrelationCalculation',
            'srpSingleExportBtn': 'exportRequestData',
            'srpSingleDeleteBtn': 'deleteData',
            'srpTotalQueryBtn': 'search',
            'srpTotalReCalcBtn': 'reTotalCalculate',
            'srpTotalExportBtn': 'exportRequestData',
            'pcpSingleQueryBtn': 'search',
            'pcpSingleEditBtn': 'editHistoryDataCalculate',
            'pcpSingleLinkBtn': 'productionDataCorrelationCalculation',
            'pcpSingleExportBtn': 'exportRequestData',
            'pcpSingleDeleteBtn': 'deleteData',
            'pcpTotalQueryBtn': 'search',
            'pcpTotalReCalcBtn': 'reTotalCalculate',
            'pcpTotalExportBtn': 'exportRequestData'
        };
        for (var id in btnMap) {
            var btn = mini.get(id);
            if (btn) btn.setText(_loginUserLanguageResource[btnMap[id]] || btnMap[id]);
        }

        // ---------- 下拉框占位文本 ----------
        var comboMap = {
            'deviceListCombo': 'all',
            'srpSingleStatusCombo': 'all',
            'srpSingleResultCombo': 'all',
            'pcpSingleStatusCombo': 'all'
        };
        for (var id in comboMap) {
            var combo = mini.get(id);
            if (combo) combo.setEmptyText('--' + _loginUserLanguageResource[comboMap[id]] + '--');
        }

        // ---------- 时间类型下拉静态数据 ----------
        var timeType = mini.get('srpSingleTimeType');
        if (timeType) {
            timeType.setData([
                { id: 0, text: _loginUserLanguageResource.FESDiagramAcqtime},
                { id: 1, text: _loginUserLanguageResource.cloudAcqtime}
            ]);
            timeType.setValue(0);
        }

        // ---------- 标签文本 ----------
        var labelMap = {
            'deviceCountLabel': 'deviceCount',
            'acqRealtimeToLabel': 'timeTo',
            'acqHistoryToLabel': 'timeTo',
            'srpSingleTimeTypeLabel': 'timeType',
            'srpSingleToLabel': 'timeTo',
            'srpSingleStatusLabel': 'resultStatus',
            'srpSingleResultLabel': 'FSDiagramWorkType',
            'srpTotalToLabel': 'timeTo',
            'pcpSingleToLabel': 'timeTo',
            'pcpSingleStatusLabel': 'resultStatus',
            'pcpTotalToLabel': 'timeTo',
            'noChildTip': 'selectLevel1'
        };
        for (var id in labelMap) {
            var el = document.getElementById(id);
            if (el) {
                var key = labelMap[id];
                if (key === 'deviceCount') {
                    // 特殊处理：保留"设备数："和数字
                    el.innerHTML = _loginUserLanguageResource.deviceCount + '：<span id="deviceCount">0</span>';
                } else if (key === 'recordCount') {
                    // 保留"记录数："和数字
                    el.innerHTML = _loginUserLanguageResource.recordCount + '：<span id="' + (id === 'acqRealtimeCountLabel' ? 'acqRealtimeCount' : 'acqHistoryCount') + '">0</span>';
                } else if (key === 'selectLevel1') {
                    el.textContent = _loginUserLanguageResource.selectLevel1 || '选择一级';
                } else {
                    el.textContent = (_loginUserLanguageResource[key] || key) + '：';
                }
            }
        }

        // ---------- 表格列头（通过表格的 setColumns 动态设置，但目前无数据，暂不处理） ----------
        // 表格 emptyText 在 data 中已静态设置，但可考虑国际化，不过目前空数据无影响
        // 如果需要，可以在 onGridLoad 中动态设置列头，但这里仅为布局，暂不处理

        // ---------- 主Tabs标题 ----------
        var mainTabs = mini.get('mainTabs');
        if (mainTabs) {
            var tabs = mainTabs.getTabs();
            if (tabs && tabs.length >= 3) {
                if (tabs[0]) mainTabs.updateTab(tabs[0], { title: _loginUserLanguageResource.acquisitionData});
                if (tabs[1]) mainTabs.updateTab(tabs[1], { title: _loginUserLanguageResource.SRPCalculate});
                if (tabs[2]) mainTabs.updateTab(tabs[2], { title: _loginUserLanguageResource.PCPCalculate});
            }
        }

        // ---------- 子Tabs标题 ----------
        var acqSub = mini.get('acqSubTabs');
        if (acqSub) {
            var tabs = acqSub.getTabs();
            if (tabs && tabs.length >= 2) {
                if (tabs[0]) acqSub.updateTab(tabs[0], { title: _loginUserLanguageResource.realtimeMonitoring});
                if (tabs[1]) acqSub.updateTab(tabs[1], { title: _loginUserLanguageResource.historyQuery });
            }
        }
        var srpSub = mini.get('srpSubTabs');
        if (srpSub) {
            var tabs = srpSub.getTabs();
            if (tabs && tabs.length >= 2) {
                if (tabs[0]) srpSub.updateTab(tabs[0], { title: _loginUserLanguageResource.singleRecord });
                if (tabs[1]) srpSub.updateTab(tabs[1], { title: _loginUserLanguageResource.recordTotal });
            }
        }
        var pcpSub = mini.get('pcpSubTabs');
        if (pcpSub) {
            var tabs = pcpSub.getTabs();
            if (tabs && tabs.length >= 2) {
                if (tabs[0]) pcpSub.updateTab(tabs[0], { title: _loginUserLanguageResource.singleRecord });
                if (tabs[1]) pcpSub.updateTab(tabs[1], { title: _loginUserLanguageResource.recordTotal });
            }
        }

        // ---------- 设备列表表格列头 ----------
        var deviceGrid = mini.get('deviceGrid');
        if (deviceGrid) {
            var cols = deviceGrid.getColumns();
            if (cols && cols.length >= 3) {
                // cols[1] 是设备名称，cols[2] 是计算类型
                if (cols[1]) deviceGrid.updateColumn(cols[1], { header: _loginUserLanguageResource.deviceName});
                if (cols[2]) deviceGrid.updateColumn(cols[2], { header: _loginUserLanguageResource.calculateType});
            }
            // emptyText 也可以设置
            deviceGrid.setEmptyText(_loginUserLanguageResource.emptyMsg);
        }

        // 其他表格的列头暂不处理，因无数据加载，保持默认即可。
        // 如果后续加载数据，动态列会由后端返回，届时可在 onGridLoad 中设置。
    }

    // ================================================================
    // 页面初始化
    // ================================================================
    $(document).ready(function() {
        mini.parse();
        initI18n();
        buildLevel1Tabs();
        
        
        window.addEventListener('message', function(event) {
            var message = event.data;
            if (!message || !message.action) return;
            if (message.action === 'refresh') {
                mini.get('deviceListCombo').setValue('');
                if (typeof refreshData === 'function') refreshData();
            }
        });
        
        console.log('数据维护模块界面加载完成（仅布局，已国际化）');
    });

    // 暴露全局函数供标签点击
    window.selectLevel1 = selectLevel1;
    window.selectLevel2 = selectLevel2;
</script>
</body>
</html>