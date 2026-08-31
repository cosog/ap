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
                                <button id="refreshDeviceBtn" class="mini-button" iconCls="note-refresh" onclick="refreshDeviceBtnClick()">刷新</button>
                                <span class="separator"></span>
                                <input id="deviceListCombo" class="mini-combobox" style="width:140px;" emptyText="-- 全部 --"  url="<%=path%>/wellInformationManagerController/loadWellComboxList" onbeforeload="onDeviceComboBeforeLoad" onshowpopup="onDeviceComboShowPopup" onload="onDeviceComboLoad" dataField="list" totalField="totals" valueField="boxkey" textField="boxval" onvaluechanged="onDeviceCombChange" />
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
                                                        <input id="acqRealtimeStartDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" enabled="false" />
                                                        <span style="margin-left:8px;font-size:12px;color:#333;" id="acqRealtimeToLabel">至：</span>
                                                        <input id="acqRealtimeEndDate" class="mini-datepicker" style="width:150px;" format="yyyy-MM-dd H:mm:ss" timeFormat="H:mm" showTime="true" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" enabled="false" />
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
                                                        <input id="srpSingleStatusCombo" class="mini-combobox" style="width:100px;" emptyText="--全部--" 
    														valueField="boxkey" textField="boxval" 
    														dataField="list" 
    														reloadOnOpen="true" 
    														autoLoad="false" 
    														onbeforeload="onSRPStatusComboBeforeLoad" 
    														onshowpopup="onSRPStatusComboShowPopup" />
                                                        <span class="separator"></span>
                                                        <span id="srpSingleResultLabel">工况：</span>
                                                        <input id="srpSingleResultCombo" class="mini-combobox" style="width:120px;" emptyText="--全部--" 
    														valueField="boxkey" textField="boxval" 
    														dataField="list" 
    														reloadOnOpen="true" 
    														autoLoad="false" 
   	 														onbeforeload="onSRPResultComboBeforeLoad"
   	 														onload="onSRPResultComboLoad"
   	 														onshowpopup="onSRPResultComboShowPopup" />
                                                        <span class="separator"></span>
                                                        <button id="srpSingleQueryBtn" class="mini-button" iconCls="search" onclick="loadSRPSingleData()">查询</button>
                                                    </div>
                                                    <div class="mini-toolbar">
                                                        <span style="flex:1;"></span>
                                                        <button id="srpSingleEditBtn" class="mini-button" iconCls="save" onclick="saveSRPSingleData()">修改历史数据计算</button>
                                                        <button id="srpSingleLinkBtn" class="mini-button" iconCls="save" onclick="linkSRPSingleData()">关联生产数据计算</button>
                                                        <button id="srpSingleExportBtn" class="mini-button" iconCls="export" onclick="exportSRPSingleData()">导出请求数据</button>
                                                        <button id="srpSingleDeleteBtn" class="mini-button" iconCls="delete" onclick="deleteSRPSingleData()">删除</button>
                                                    </div>
                                                    <div id="srpSingleGrid" class="mini-datagrid" style="width:100%; height:100%;"
     													idField="id" pageSize="100" allowResize="true" allowAlternating="true"
     													showPager="true" showPageInfo="true"
     													multiSelect="true"
     													allowCellEdit="true" allowCellSelect="true" cellEditAction="celldblclick"
     													dataField="totalRoot" totalField="totalCount"
     													onbeforeload="onSrpSingleGridBeforeLoad"
     													onload="onSrpSingleGridLoad"
     													oncellvalidation="onSrpSingleCellValidation"
     													oncellcommitedit="onSrpSingleCellCommitEdit"
     													oncellbeginedit="onSrpSingleCellBeginEdit" >
    													<div property="columns"></div>
    													<div property="emptyText" class="empty-msg">暂无单条记录</div>
													</div>
                                                </div>
                                            </div>
                                            <!-- 汇总记录 -->
                                            <div id="srpTotalPanel" title="汇总记录" name="total" style="height:100%;">
                                                <div class="sub-tab-container">
                                                    <div class="mini-toolbar">
                                                        <input id="srpTotalStartDate" class="mini-datepicker" style="width:100px;" format="yyyy-MM-dd" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                                        <span style="margin-left:8px;font-size:12px;color:#333;" id="srpTotalToLabel">至：</span>
                                                        <input id="srpTotalEndDate" class="mini-datepicker" style="width:100px;" format="yyyy-MM-dd" showOkButton="true" showTodayButton="true" showClearButton="false" allowInput="false" />
                                                        <span style="flex:1;"></span>
                                                        <button id="srpTotalQueryBtn" class="mini-button" iconCls="search" onclick="loadSRPTotalData()">查询</button>
                                                        <button id="srpTotalReCalcBtn" class="mini-button" iconCls="edit" onclick="reTotalSRP()">重新汇总</button>
                                                        <button id="srpTotalExportBtn" class="mini-button" iconCls="export" onclick="exportSRPTotalData()">导出请求数据</button>
                                                    </div>
                                                    <div id="srpTotalGrid" class="mini-datagrid" style="width:100%; height:100%;"
     													idField="id" pageSize="100" allowResize="true" allowAlternating="true"
     													showPager="true" showPageInfo="true"
     													multiSelect="true"
     													dataField="totalRoot" totalField="totalCount"
     													onbeforeload="onSrpTotalGridBeforeLoad"
     													onload="onSrpTotalGridLoad">
    													<div property="columns"></div>
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
                                                        <input id="pcpSingleStatusCombo" class="mini-combobox" style="width:100px;" emptyText="--全部--" 
    														valueField="boxkey" textField="boxval" 
    														dataField="list" 
    														reloadOnOpen="true" 
    														autoLoad="false" 
    														onbeforeload="onPCPStatusComboBeforeLoad" 
    														onshowpopup="onPCPStatusComboShowPopup" />
                                                        <span class="separator"></span>
                                                        <button id="pcpSingleQueryBtn" class="mini-button" iconCls="search" onclick="loadPCPSingleData()">查询</button>
                                                    </div>
                                                    <div class="mini-toolbar">
                                                        <span style="flex:1;"></span>
                                                        <button id="pcpSingleEditBtn" class="mini-button" iconCls="save" onclick="savePCPSingleData()">修改历史数据计算</button>
                                                        <button id="pcpSingleLinkBtn" class="mini-button" iconCls="save" onclick="linkPCPSingleData()">关联生产数据计算</button>
                                                        <button id="pcpSingleExportBtn" class="mini-button" iconCls="export" onclick="exportPCPSingleData()">导出请求数据</button>
                                                        <button id="pcpSingleDeleteBtn" class="mini-button" iconCls="delete" onclick="deletePCPSingleData()">删除</button>
                                                    </div>
                                                    <div id="pcpSingleGrid" class="mini-datagrid" 
     													style="width:100%; height:100%;"
     													idField="recordId" pageSize="100" allowResize="true" allowAlternating="true"
     													showPager="true" showPageInfo="true"
     													multiSelect="true"
     													allowCellEdit="true" allowCellSelect="true" cellEditAction="celldblclick"
     													dataField="totalRoot" totalField="totalCount"
     													onbeforeload="onPcpSingleGridBeforeLoad"
     													onload="onPcpSingleGridLoad"
     													oncellcommitedit="onPcpSingleCellCommitEdit"
     													oncellbeginedit="onPcpSingleCellBeginEdit">
    													<div property="columns"><!-- 由 onload 动态生成 --></div>
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
                                                        <button id="pcpTotalQueryBtn" class="mini-button" iconCls="search" onclick="loadPCPTotalData()">查询</button>
                                                        <button id="pcpTotalReCalcBtn" class="mini-button" iconCls="edit" onclick="reTotalPCP()">重新汇总</button>
                                                        <button id="pcpTotalExportBtn" class="mini-button" iconCls="export" onclick="exportPCPTotalData()">导出请求数据</button>
                                                    </div>
                                                    <div id="pcpTotalGrid" class="mini-datagrid" 
     													style="width:100%; height:100%;"
     													idField="id" pageSize="100" allowResize="true" allowAlternating="true"
     													showPager="true" showPageInfo="true"
     													multiSelect="true"
     													dataField="totalRoot" totalField="totalCount"
     													onbeforeload="onPcpTotalGridBeforeLoad"
     													onload="onPcpTotalGridLoad">
    													<div property="columns"><!-- 由 onload 动态生成 --></div>
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
        var hidePopup=false;
        if (!data || data.length <= 1) {
            // 先隐藏下拉，防止显示空
            combo.hidePopup();
            hidePopup=true;
        }
        combo.load(combo.url);
        if(hidePopup){
       	 	combo.showPopup();
        }
    };

    window.onDeviceComboLoad = function(e) {
        var combo = e.sender;

    };
    
    function onDeviceCombChange() { 
    	refreshData(); 
    }

    function refreshData(){
    	var grid = mini.get('deviceGrid');
        if (grid) grid.load();
    }
    
    function refreshDeviceBtnClick(){
    	mini.get('deviceListCombo').setValue('');
        refreshData();
    };
    
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
            currentApplicationScenarios=0;
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
            currentApplicationScenarios = selected.applicationScenarios;
            updateDataTypeTabs(selected.calculateType);
         	// 重置所有查询参数
            resetAllQueryParams();
            // 加载当前激活标签的数据
            refreshCurrentTabData();
        }else{
        	currentDeviceId = 0;
            currentDeviceName = '';
            currentDeviceCalculateType = 0;
            currentApplicationScenarios = 0;
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
     params.dictDeviceType = params.deviceType;
   	 if (params.dictDeviceType && params.dictDeviceType.indexOf(',') > -1) {
    	params.dictDeviceType = currentLevel1 ? currentLevel1.deviceTypeId : deviceType;
     }
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
     params.dictDeviceType = params.deviceType;
     if (params.dictDeviceType && params.dictDeviceType.indexOf(',') > -1) {
    	 params.dictDeviceType = currentLevel1 ? currentLevel1.deviceTypeId : deviceType;
     }
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
 	
//计算状态下拉加载前事件
 function onSRPStatusComboBeforeLoad(e) {
	// 如果没有选中设备，取消加载并清空数据
	    if (!currentDeviceId || currentDeviceId === 0) {
	        var combo = e.sender;
	        combo.setData([]);  // 清空数据
	        combo.setValue(''); // 清空选中值
	        e.cancel = true;    // 阻止本次加载
	        return;
	    }
	
     var params = e.params || {};
     
     // 获取组织ID
     var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
     params.orgId = leftOrgId ? leftOrgId.getValue() : '';
     
     // 当前选中的设备
     params.deviceId = currentDeviceId || 0;
     params.deviceName = currentDeviceName || '';
     
     // 日期参数
     var startDate = mini.get('srpSingleStartDate');
     var endDate = mini.get('srpSingleEndDate');
     params.startDate = startDate ? startDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
     params.endDate = endDate ? endDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
     
     // 时间类型
     var timeType = mini.get('srpSingleTimeType');
     params.timeType = timeType ? timeType.getValue() : 0;
     
     // 计算类型固定为1（功图）
     params.calculateType = 1;
     
     e.params = params;
 }
 
 onSRPStatusComboShowPopup = function(e) {
     var combo = e.sender;
     // 如果当前没有数据或数据为空，加载
     var data = combo.getData();
     var hidePopup=false;
     if (!data || data.length <= 1) {
         // 先隐藏下拉，防止显示空
         combo.hidePopup();
         hidePopup=true;
     }
     combo.load(context + '/calculateManagerController/getCalculateStatusList');
     if(hidePopup){
    	 combo.showPopup();
     }
 };

 // 工况下拉加载前事件
 function onSRPResultComboBeforeLoad(e) {
	// 如果没有选中设备，取消加载并清空数据
	    if (!currentDeviceId || currentDeviceId === 0) {
	        var combo = e.sender;
	        combo.setData([]);  // 清空数据
	        combo.setValue(''); // 清空选中值
	        e.cancel = true;    // 阻止本次加载
	        return;
	    }
	 
     var params = e.params || {};
     
     var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
     params.orgId = leftOrgId ? leftOrgId.getValue() : '';
     
     params.deviceId = currentDeviceId || 0;
     params.deviceName = currentDeviceName || '';
     
     var startDate = mini.get('srpSingleStartDate');
     var endDate = mini.get('srpSingleEndDate');
     params.startDate = startDate ? startDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
     params.endDate = endDate ? endDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
     
     var timeType = mini.get('srpSingleTimeType');
     params.timeType = timeType ? timeType.getValue() : 0;
     params.calculateType = 1;
     
     e.params = params;
 }
 
 onSRPResultComboLoad = function(e) {
     var combo = e.sender;
 };
 
 onSRPResultComboShowPopup = function(e) {
     var combo = e.sender;
     // 如果当前没有数据或数据为空，加载
     var data = combo.getData();
     var hidePopup=false;
     if (!data || data.length <= 1) {
         // 先隐藏下拉，防止显示空
         combo.hidePopup();
         hidePopup=true;
     }
     combo.load(context + '/calculateManagerController/getResultNameList');
     if(hidePopup){
    	 combo.showPopup();
     }
 };
 
//PCP 计算状态下拉加载前事件
 function onPCPStatusComboBeforeLoad(e) {
     var params = e.params || {};
     
     // 获取组织ID
     var leftOrgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id') : null;
     params.orgId = leftOrgId ? leftOrgId.getValue() : '';
     
     // 当前选中的设备
     params.deviceId = currentDeviceId || 0;
     params.deviceName = currentDeviceName || '';
     
     // 日期参数（PCP 单条日期控件）
     var startDate = mini.get('pcpSingleStartDate');
     var endDate = mini.get('pcpSingleEndDate');
     params.startDate = startDate ? startDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
     params.endDate = endDate ? endDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
     
     // 注意：PCP 没有 timeType，可省略或传默认值
     // params.timeType = 0;
     
     // 计算类型固定为 2（转速计产）
     params.calculateType = 2;
     
     e.params = params;
 }
 
 onPCPStatusComboShowPopup = function(e) {
     var combo = e.sender;
     // 如果当前没有数据或数据为空，加载
     var data = combo.getData();
     var hidePopup=false;
     if (!data || data.length <= 1) {
         // 先隐藏下拉，防止显示空
         combo.hidePopup();
         hidePopup=true;
     }
     combo.load(context + '/calculateManagerController/getCalculateStatusList');
     if(hidePopup){
    	 combo.showPopup();
     }
 };
 	
 	var srpEditData = { updatelist: [], insertlist: [], delidslist: [] }; // 编辑缓存
 	function loadSRPSingleData() {
 		var grid = mini.get('srpSingleGrid');
 	    if (!grid) return;
 	    grid.setUrl(context + '/calculateManagerController/getCalculateResultData');
 	    grid.load();
	}
 	function onSrpSingleGridBeforeLoad(e) {
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
 	    params.dictDeviceType = params.deviceType;
      	if (params.dictDeviceType && params.dictDeviceType.indexOf(',') > -1) {
      		params.dictDeviceType = currentLevel1 ? currentLevel1.deviceTypeId : deviceType;
      	}
 	    
 	    // 查询参数
 	    var timeType = mini.get('srpSingleTimeType') ? mini.get('srpSingleTimeType').getValue() : 0;
 	    params.timeType = timeType;
 	    
 	    var startDate = mini.get('srpSingleStartDate');
 	    var endDate = mini.get('srpSingleEndDate');
 	    params.startDate = startDate ? startDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
 	    params.endDate = endDate ? endDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
 	    
 	    var statusCombo = mini.get('srpSingleStatusCombo');
 	    params.calculateSign = statusCombo ? statusCombo.getValue() : '';
 	    
 	    var resultCombo = mini.get('srpSingleResultCombo');
 	    params.resultCode = resultCombo ? resultCombo.getValue() : '';
 	    
 	    params.calculateType = 1; // SRP
 	    params.applicationScenarios = currentApplicationScenarios || 0;
 	    
 	    e.params = params;
 	}
 	function onSrpSingleGridLoad(e) {
 	    var grid = e.sender;
 	    var result = e.result;
 	    if (result && result.columns) {
 	        // 自动填充时间
 	        if (result.start_date) {
 	            var startDate = mini.get('srpSingleStartDate');
 	            var endDate = mini.get('srpSingleEndDate');
 	            if (startDate && !startDate.getValue()) startDate.setValue(result.start_date);
 	            if (endDate && !endDate.getValue()) endDate.setValue(result.end_date);
 	        }
 	        
 	        // 构建动态列（可编辑）
 	        var columns = buildSrpSingleColumns(result.columns, result.applicationScenarios,result);
 	        grid.setColumns(columns);
 	        grid.doLayout();
 	        
 	        // 清除选中
 	        grid.deselectAll(false);
 	    }
 	    // 清空编辑缓存（重新加载后丢弃未保存的修改）
 	    srpEditData = { updatelist: [], insertlist: [], delidslist: [] };
 	}
 	function buildSrpSingleColumns(colsData, applicationScenarios,result) {
 	    var cols = [];
 	   	cols.push({
 	         type: "checkcolumn",
 	         width: 40,
 	         header: "",
 	         headerAlign: "center",
 	         align: "center"
 	     });
 	    // 不可可编辑字段列表
 	   var diseditableFields = [
	        'id', 'deviceName', 'acqTime', 'FESDiagramAcqtime',
	        'resultStatus', 'resultName', 
	        'liquidWeightProduction', 'oilWeightProduction','waterWeightProduction', 
	        'liquidVolumetricProduction', 'oilVolumetricProduction', 'waterVolumetricProduction'
	    ];
 	    // 如果应用场景为0（煤层气），部分字段可能需要跳过（原有逻辑有过滤，但前端只负责显示，后端会处理）
 	    
 	    for (var i = 0; i < colsData.length; i++) {
 	        var col = colsData[i];
 	        
 	        var column = {
 	            field: col.dataIndex,
 	            header: col.header,
 	            headerAlign: 'center',
 	            align: 'center',
 	            width: col.width || 100,
 	            allowSort: false
 	        };
 	       if (col.dataIndex === 'acqTime' || col.dataIndex === 'FESDiagramAcqtime') {
                column.dateFormat = 'yyyy-MM-dd HH:mm:ss';
                column.width = 150;
            }
 	        
 	       	if (col.dataIndex === 'id') {
 	             column.type = 'indexcolumn';
 	             column.width = 50;
 	             column.header = _loginUserLanguageResource.idx;
 	             delete column.field;
 	         }else if (diseditableFields.indexOf(col.dataIndex) !== -1) {
 	            // 只读
 	            column.editor = null;
 	        } else {
 	            // 可编辑
 	            column.allowCellEdit = true;
 	            // 根据字段类型设置编辑器
 	            var editor = null;
 	            var source = [];
 	            if (col.dataIndex === 'manualInterventionResult') {
 	            	for (var j = 0; j < result.resultNameList.length; j++) {
 	            		source.push({id: result.resultNameList[j],text: result.resultNameList[j]});
 	                }
	                editor = { type: 'combobox', data: source, valueField: 'id', textField: 'text', allowInput: false };
	            }else if (col.dataIndex === 'anchoringStateName') {
 	               	source.push({id: '锚定',text: '锚定'});
 	               	source.push({id: '未锚定',text: '未锚定'});
 	                editor = { type: 'combobox', data: source, valueField: 'id', textField: 'text', allowInput: false };
 	            } else if (col.dataIndex === 'barrelTypeName') {
 	               	source.push({id: _loginUserLanguageResource.barrelType_H,text: _loginUserLanguageResource.barrelType_H});
 	               	source.push({id: _loginUserLanguageResource.barrelType_L,text: _loginUserLanguageResource.barrelType_L});
 	                editor = { type: 'combobox', data: source, valueField: 'id', textField: 'text', allowInput: false };
 	            } else if (col.dataIndex === 'pumpTypeName') {
 	               	source.push({id: '杆式泵',text: '杆式泵'});
	               	source.push({id: '管式泵',text: '管式泵'});
 	                editor = { type: 'combobox', data: source, valueField: 'id', textField: 'text', allowInput: false };
 	            } else if (col.dataIndex === 'pumpGrade') {
 	                // 根据barrelTypeName动态变化（在cellbeginedit中处理）
 	                source.push({id: '1',text: '1'});
 	               	source.push({id: '2',text: '2'});
 	              	source.push({id: '3',text: '3'});
 	             	source.push({id: '4',text: '4'});
 	            	source.push({id: '5',text: '5'});
 	                editor = { type: 'combobox', data: source, valueField: 'id', textField: 'text', allowInput: false };
 	            } else if (col.dataIndex.indexOf('rodGrade') !== -1) {
 	               	source.push({id: '',text: ''});
	               	source.push({id: 'A',text: 'A'});
	              	source.push({id: 'B',text: 'B'});
	             	source.push({id: 'C',text: 'C'});
	            	source.push({id: 'D',text: 'D'});
	            	source.push({id: 'K',text: 'K'});
	            	source.push({id: 'KD',text: 'KD'});
	            	source.push({id: 'HL',text: 'HL'});
	            	source.push({id: 'HY',text: 'HY'});
 	                editor = { type: 'combobox', data: source, valueField: 'id', textField: 'text', allowInput: false };
 	            } else if (col.dataIndex.indexOf('rodTypeName') !== -1) {
 	               	source.push({id: '',text: ''});
	               	source.push({id: _loginUserLanguageResource.rodStringTypeValue1,text: _loginUserLanguageResource.rodStringTypeValue1});
	              	source.push({id: _loginUserLanguageResource.rodStringTypeValue2,text: _loginUserLanguageResource.rodStringTypeValue2});
	             	source.push({id: _loginUserLanguageResource.rodStringTypeValue3,text: _loginUserLanguageResource.rodStringTypeValue3});
 	                editor = { type: 'combobox', data: source, valueField: 'id', textField: 'text', allowInput: false };
 	            } else {
 	                // 数值文本
 	                editor = { type: 'textbox' };
 	            }
 	            column.editor = editor;
 	        }
 	        
 	        // 特殊：如果应用场景为0（煤层气），可能替换列名（已在后端处理，前端无需重复）
 	        cols.push(column);
 	    }
 	    return cols;
 	}
 	// 单元格开始编辑事件 - 动态修改 pumpGrade 下拉选项
 	function onSrpSingleCellBeginEdit(e) {
 	    var field = e.field;
 	    if (field !== 'pumpGrade') return; // 仅处理泵级别列
 	    
 	    var grid = e.sender;
 	    var rowIndex = e.rowIndex;
 	    var record = grid.getRow(rowIndex);
 	    if (!record) return;
 	    
 	    // 获取 barrelTypeName 字段的值
 	    var barrelType = record.barrelTypeName || '';
 	    var editor = e.editor;
 	    if (!editor) return;
 	    
 	    // 根据 barrelType 决定可选项
 	    var options = [];
 	    // 获取国际化值
 	    var barrelTypeH = _loginUserLanguageResource.barrelType_H;
 	    var barrelTypeL = _loginUserLanguageResource.barrelType_L;
 	    
 	    if (barrelType === barrelTypeH) {
 	        options = ['1', '2', '3', '4', '5'];
 	    } else if (barrelType === barrelTypeL) {
 	        options = ['1', '2', '3'];
 	    } else {
 	        // 默认全部
 	        options = ['1', '2', '3', '4', '5'];
 	    }
 	    
 	    // 构建 source 数据（MiniUI combobox 的 data 格式为 [{id:..., text:...}]）
 	    var sourceData = [];
 	    for (var i = 0; i < options.length; i++) {
 	        sourceData.push({ id: options[i], text: options[i] });
 	    }
 	    editor.setData(sourceData);
 	    
 	    // 如果当前值不在新的选项列表中，清空
 	    var currentValue = record.pumpGrade;
 	    if (currentValue && options.indexOf(currentValue) === -1) {
 	        // 可以提示或清空，此处不清空，让用户手动选择
 	    }
 	}
 	// 单元格编辑校验（示例）
 	function onSrpSingleCellValidation(e) {
 	    var field = e.field;
 	    var value = e.value;
 	    if (field === 'pumpGrade') {
 	        if (value && !/^[1-5]$/.test(value)) {
 	            e.isValid = false;
 	            e.errorText = '请输入1-5的数字';
 	        }
 	    }
 	    // 可添加其他校验
 	}
 	// 单元格编辑提交（收集变更）
 	function onSrpSingleCellCommitEdit(e) {
 	    var record = e.record;
 	    var field = e.field;
 	    var value = e.value;
 	    var oldValue = e.oldValue;
 	    
 	    if (value !== oldValue) {
 	        var recordId = record.recordId;
 	        if (recordId && recordId > 0) {
 	            // 更新
 	            var found = false;
 	            for (var i = 0; i < srpEditData.updatelist.length; i++) {
 	                if (srpEditData.updatelist[i].recordId === recordId) {
 	                    srpEditData.updatelist[i][field] = value;
 	                    found = true;
 	                    break;
 	                }
 	            }
 	            if (!found) {
 	            	var updateRecord = mini.clone(record);   // 深拷贝当前记录
 	               	// 或者显式确保新值
 	               	updateRecord[field] = value;
 	               	srpEditData.updatelist.push(updateRecord);
 	            }
 	        }
 	        // 新增行暂不处理（insert）
 	    }
 	}
 // 保存编辑数据（修改历史数据计算）
 	function saveSRPSingleData() {
 	    // 检查是否有修改
 	    var hasChange = (srpEditData.updatelist.length > 0 || srpEditData.insertlist.length > 0 || srpEditData.delidslist.length > 0);
 	    if (!hasChange) {
 	        mini.alert(_loginUserLanguageResource.noDataChange || '无数据变更');
 	        return;
 	    }
 	    
 	    var saveData = {
 	        updatelist: srpEditData.updatelist,
 	        insertlist: srpEditData.insertlist,
 	        delidslist: srpEditData.delidslist
 	    };
 	    
 	    mini.confirm(_loginUserLanguageResource.confirmSave || '确认保存？', _loginUserLanguageResource.tip, function(action) {
 	        if (action === 'ok') {
 	            $.ajax({
 	                url: context + '/calculateManagerController/saveRecalculateData',
 	                type: 'POST',
 	                data: {
 	                    data: JSON.stringify(saveData),
 	                    deviceType: 0, // SRP deviceType=0
 	                    applicationScenarios: currentApplicationScenarios || 0,
 	                    calculateType: 1 // SRP
 	                },
 	                dataType: 'json',
 	                success: function(result) {
 	                    if (result.success) {
 	                        mini.alert(_loginUserLanguageResource.calculateMaintainingEditSuccessInfo || '保存成功');
 	                        // 清空缓存并刷新
 	                        srpEditData = { updatelist: [], insertlist: [], delidslist: [] };
 	                        loadSRPSingleData();
 	                    } else {
 	                        mini.alert(result.msg || _loginUserLanguageResource.saveFailed);
 	                    }
 	                },
 	                error: function() {
 	                    mini.alert(_loginUserLanguageResource.requestFailed);
 	                }
 	            });
 	        }
 	    });
 	}
 // 关联生产数据计算
 	function linkSRPSingleData() {
 	    // 收集当前查询条件
 	    var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
 	    var deviceName = currentDeviceName;
 	    var startDate = mini.get('srpSingleStartDate') ? mini.get('srpSingleStartDate').getFormValue('yyyy-MM-dd HH:mm:ss') : '';
 	    var endDate = mini.get('srpSingleEndDate') ? mini.get('srpSingleEndDate').getFormValue('yyyy-MM-dd HH:mm:ss') : '';
 	    var calculateSign = mini.get('srpSingleStatusCombo') ? mini.get('srpSingleStatusCombo').getValue() : '';
 	    var calculateType = 1;
 	    var deviceType = 0;
 	    
 	    var confirmMsg = _loginUserLanguageResource.takeEffectScope + ':' + (deviceName || _loginUserLanguageResource.allSRPCalculateWell) + ' ' + 
 	                    (startDate ? startDate + '~' + endDate : '') + ' <br/><font color=red>' + 
 	                    (_loginUserLanguageResourceFirstLower ? _loginUserLanguageResourceFirstLower.calculateMaintainingConfirm : '确认执行？') + '</font>';
 	    
 	    mini.confirm(confirmMsg, _loginUserLanguageResource.tip, function(action) {
 	        if (action === 'ok') {
 	            $.ajax({
 	                url: context + '/calculateManagerController/recalculateByProductionData',
 	                type: 'POST',
 	                data: {
 	                    orgId: orgId,
 	                   	deviceId:currentDeviceId,
 	                    deviceName: deviceName,
 	                    startDate: startDate,
 	                    endDate: endDate,
 	                    calculateSign: calculateSign,
 	                    calculateType: calculateType,
 	                    deviceType: deviceType
 	                },
 	                dataType: 'json',
 	                success: function(result) {
 	                    if (result.success) {
 	                        mini.alert(_loginUserLanguageResource.calculateMaintainingEditSuccessInfo || '关联计算成功');
 	                        srpEditData = { updatelist: [], insertlist: [], delidslist: [] };
 	                        loadSRPSingleData();
 	                    } else {
 	                        mini.alert(result.msg || _loginUserLanguageResource.operationFailed);
 	                    }
 	                },
 	                error: function() {
 	                    mini.alert(_loginUserLanguageResource.requestFailed);
 	                }
 	            });
 	        }
 	    });
 	}
 // 导出请求数据（单条）
 	function exportSRPSingleData() {
 	    var grid = mini.get('srpSingleGrid');
 	    if (!grid) return;
 	    var rows = grid.getSelecteds();
 	    if (rows.length === 0) {
 	        mini.alert(_loginUserLanguageResource.noSelectionRecord);
 	        return;
 	    }
 	    for (var i = 0; i < rows.length; i++) {
 	        var record = rows[i];
 	        var url = context + '/calculateManagerController/exportCalculateRequestData?recordId=' + record.recordId +
 	                  '&deviceName=' + encodeURIComponent(encodeURIComponent(record.deviceName)) +
 	                  '&acqTime=' + formatDate(record.acqTime, 'yyyy-MM-dd HH:mm:ss') +
 	                  '&calculateType=1';
 	        downloadFile(url);
 	    }
 	}
 // 删除单条记录
 	function deleteSRPSingleData() {
 	    var grid = mini.get('srpSingleGrid');
 	    if (!grid) return;
 	    var rows = grid.getSelecteds();
 	    if (rows.length === 0) {
 	        mini.alert(_loginUserLanguageResource.noSelectionRecord);
 	        return;
 	    }
 	    var recordIds = [];
 	    var acqTimes = [];
 	    var fesAcqTimes = [];
 	    for (var i = 0; i < rows.length; i++) {
 	        recordIds.push(rows[i].recordId);
 	        acqTimes.push(formatDate(rows[i].acqTime, 'yyyy-MM-dd HH:mm:ss'));
 	        fesAcqTimes.push(formatDate(rows[i].FESDiagramAcqtime, 'yyyy-MM-dd HH:mm:ss'));
 	    }
 	    // 构建删除确认信息
 	    var deleteInfo = _loginUserLanguageResource.confirmDelete;
 	    if (recordIds.length === 1) {
 	        deleteInfo = _loginUserLanguageResource.deviceName + ':<font color=red>' + (rows[0].deviceName || '') + '</font>' +
 	                     '</br>' + _loginUserLanguageResource.cloudAcqtime + ':<font color=red>' + (acqTimes[0] || '') + '</font>' +
 	                     '</br>' + _loginUserLanguageResource.FESDiagramAcqtime + ':<font color=red>' + (fesAcqTimes[0] || '') + '</font>' +
 	                     '</br>' + _loginUserLanguageResource.confirmDelete;
 	    } else {
 	        deleteInfo = _loginUserLanguageResource.deviceName + ':<font color=red>' + (rows[0].deviceName || '') + '</font>' +
 	                     '</br>' + _loginUserLanguageResource.sparseRecordCount + ':<font color=red>' + recordIds.length + '</font>' +
 	                     '</br>' + _loginUserLanguageResource.confirmDelete;
 	    }
 	    
 	    mini.confirm(deleteInfo, _loginUserLanguageResource.tip, function(action) {
 	        if (action === 'ok') {
 	            $.ajax({
 	                url: context + '/calculateManagerController/deleteCalculateData',
 	                type: 'POST',
 	                data: {
 	                    deviceId: currentDeviceId,
 	                    calculateType: 1,
 	                    recordIds: recordIds.join(',')
 	                },
 	                dataType: 'json',
 	                success: function(result) {
 	                    if (result.success) {
 	                        mini.alert(_loginUserLanguageResource.deleteSuccessfully);
 	                        // 清除选中并刷新
 	                        srpEditData = { updatelist: [], insertlist: [], delidslist: [] };
 	                        loadSRPSingleData();
 	                    } else {
 	                        mini.alert(result.msg || _loginUserLanguageResource.saveFailed);
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
 	// 功图计算（SRP）汇总记录
 	// ================================================================
	function loadSRPTotalData() {
		var grid = mini.get('srpTotalGrid');
	    if (!grid) return;
	    grid.setUrl(context + '/calculateManagerController/getTotalCalculateResultData');
	    grid.load();
	}
	function onSrpTotalGridBeforeLoad(e) {
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
	    params.dictDeviceType = params.deviceType;
	   	if (params.dictDeviceType && params.dictDeviceType.indexOf(',') > -1) {
	    	params.dictDeviceType = currentLevel1 ? currentLevel1.deviceTypeId : deviceType;
	    }
	    
	    var startDate = mini.get('srpTotalStartDate');
	    var endDate = mini.get('srpTotalEndDate');
	    params.startDate = startDate ? startDate.getFormValue('yyyy-MM-dd') : '';
	    params.endDate = endDate ? endDate.getFormValue('yyyy-MM-dd') : '';
	    params.calculateType = 3; // SRP汇总
	    
	    e.params = params;
	}
	function onSrpTotalGridLoad(e) {
	    var grid = e.sender;
	    var result = e.result;
	    if (result && result.columns) {
	        if (result.startDate) {
	            var startDate = mini.get('srpTotalStartDate');
	            var endDate = mini.get('srpTotalEndDate');
	            if (startDate && !startDate.getValue()) startDate.setValue(result.startDate);
	            if (endDate && !endDate.getValue()) endDate.setValue(result.endDate);
	        }
	        var columns = buildTotalColumns(result.columns);
	        grid.setColumns(columns);
	        grid.doLayout();
	        grid.deselectAll(false);
	    }
	}
	function buildTotalColumns(colsData) {
	    var cols = [];
	    cols.push({
	         type: "checkcolumn",
	         width: 40,
	         header: "",
	         headerAlign: "center",
	         align: "center"
	     });
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
	             //delete column.field;
	         }else if (col.dataIndex === 'calDate') {
	            column.dateFormat = 'yyyy-MM-dd';
	        }
	        cols.push(column);
	    }
	    return cols;
	}
	// 重新汇总计算
	function reTotalSRP() {
	    var grid = mini.get('srpTotalGrid');
	    if (!grid) return;
	    var rows = grid.getSelecteds();
	    if (rows.length === 0) {
	        mini.alert(_loginUserLanguageResource.noSelectionRecord);
	        return;
	    }
	    var reCalculateData = '';
	    for (var i = 0; i < rows.length; i++) {
	        var r = rows[i];
	        reCalculateData += r.id + ',' + r.deviceId + ',' + r.deviceName + ',' + r.calDate + ';';
	    }
	    reCalculateData = reCalculateData.substring(0, reCalculateData.length - 1);
	    
	    mini.confirm(_loginUserLanguageResource.confirmOperation, _loginUserLanguageResource.tip, function(action) {
	        if (action === 'ok') {
	        	var mask = mini.mask({
	                el: 'srpTotalPanel',
	                cls: 'mini-mask-loading',
	                html: _loginUserLanguageResource.recalculating+'...'
	            });
	            $.ajax({
	                url: context + '/calculateManagerController/reTotalCalculate',
	                type: 'POST',
	                data: {
	                    deviceType: 0,
	                    reCalculateDate: reCalculateData
	                },
	                dataType: 'json',
	                success: function(result) {
	                	mini.unmask('srpTotalPanel');
	                    if (result.success) {
	                        mini.alert(_loginUserLanguageResource.recalculationComplete);
	                        loadSRPTotalData();
	                    } else {
	                        mini.alert(result.msg || _loginUserLanguageResource.operationFailed);
	                    }
	                },
	                error: function() {
	                	mini.unmask('srpTotalPanel');
	                    mini.alert(_loginUserLanguageResource.requestFailed);
	                }
	            });
	        }
	    });
	}
	// 导出汇总请求数据
	function exportSRPTotalData() {
	    var grid = mini.get('srpTotalGrid');
	    if (!grid) return;
	    var rows = grid.getSelecteds();
	    if (rows.length === 0) {
	        mini.alert(_loginUserLanguageResource.noSelectionRecord);
	        return;
	    }
	    for (var i = 0; i < rows.length; i++) {
	        var r = rows[i];
	        alert(r.calDate);
	        var url = context + '/calculateManagerController/exportTotalCalculateRequestData?recordId=' + r.id +
	                  '&deviceId=' + r.deviceId +
	                  '&deviceName=' + encodeURIComponent(encodeURIComponent(r.deviceName)) +
	                  '&calDate=' + encodeURIComponent(r.calDate) +
	                  '&calculeteType=0';
	        downloadFile(url);
	    }
	}
	
	// ================================================================
	// 转速计产（PCP）单条记录
	// ================================================================
	var pcpEditData = { updatelist: [], insertlist: [], delidslist: [] }; // 编辑缓存
	function loadPCPSingleData() {
		var grid = mini.get('pcpSingleGrid');
	    if (!grid) return;
	    grid.setUrl(context + '/calculateManagerController/getCalculateResultData');
	    grid.load();
	}
	function onPcpSingleGridBeforeLoad(e) {
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
	    params.dictDeviceType = params.deviceType;
	    if (params.dictDeviceType && params.dictDeviceType.indexOf(',') > -1) {
	        params.dictDeviceType = currentLevel1 ? currentLevel1.deviceTypeId : deviceType;
	    }

	    // 查询参数（PCP 可能没有 timeType，只有日期）
	    var startDate = mini.get('pcpSingleStartDate');
	    var endDate = mini.get('pcpSingleEndDate');
	    params.startDate = startDate ? startDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';
	    params.endDate = endDate ? endDate.getFormValue('yyyy-MM-dd HH:mm:ss') : '';

	    var statusCombo = mini.get('pcpSingleStatusCombo');
	    params.calculateSign = statusCombo ? statusCombo.getValue() : '';

	    // 固定为 PCP
	    params.calculateType = 2;          // 转速计产
	    params.applicationScenarios = currentApplicationScenarios || 0;

	    e.params = params;
	}
	function onPcpSingleGridLoad(e) {
	    var grid = e.sender;
	    var result = e.result;
	    if (result && result.columns) {
	        if (result.start_date) {
	            var startDate = mini.get('pcpSingleStartDate');
	            var endDate = mini.get('pcpSingleEndDate');
	            if (startDate && !startDate.getValue()) startDate.setValue(result.start_date);
	            if (endDate && !endDate.getValue()) endDate.setValue(result.end_date);
	        }

	        // 构建动态列（可编辑）
	        var columns = buildPcpSingleColumns(result.columns);
	        grid.setColumns(columns);
	        grid.doLayout();

	        grid.deselectAll(false);
	    }
	    // 清空编辑缓存
	    pcpEditData = { updatelist: [], insertlist: [], delidslist: [] };
	}
	function buildPcpSingleColumns(colsData) {
	    var cols = [];
	    cols.push({
	        type: "checkcolumn",
	        width: 40,
	        header: "",
	        headerAlign: "center",
	        align: "center"
	    });

	 	// 不可可编辑字段列表
	    var diseditableFields = ['id', 'recordId', 
	    	'deviceName', 'acqTime', 'resultStatus', 
	    	'liquidWeightProduction', 'oilWeightProduction','waterWeightProduction', 
	    	'liquidVolumetricProduction', 'oilVolumetricProduction', 'waterVolumetricProduction',
	    	'rpm'];

	    for (var i = 0; i < colsData.length; i++) {
	        var col = colsData[i];
	        var column = {
	            field: col.dataIndex,
	            header: col.header,
	            headerAlign: 'center',
	            align: 'center',
	            width: col.width || 100,
	            allowSort: false
	        };

	        if (col.dataIndex === 'acqTime') {
	            column.dateFormat = 'yyyy-MM-dd HH:mm:ss';
	            column.width = 150;
	        }

	        if (col.dataIndex === 'id' || col.dataIndex === 'recordId') {
	            column.type = 'indexcolumn';
	            column.width = 50;
	            column.header = _loginUserLanguageResource.idx;
	            delete column.field;
	        } else if (diseditableFields.indexOf(col.dataIndex) !== -1) {
	            // 只读
	            column.editor = null;
	        } else {
	        	// 可编辑
 	            column.allowCellEdit = true;
 	            // 根据字段类型设置编辑器
 	            var editor = null;
 	            var source = [];
 	            if (col.dataIndex === 'anchoringStateName') {
 	               	source.push({id: '锚定',text: '锚定'});
 	               	source.push({id: '未锚定',text: '未锚定'});
 	                editor = { type: 'combobox', data: source, valueField: 'id', textField: 'text', allowInput: false };
 	            } else if (col.dataIndex === 'barrelTypeName') {
 	               	source.push({id: _loginUserLanguageResource.barrelType_H,text: _loginUserLanguageResource.barrelType_H});
 	               	source.push({id: _loginUserLanguageResource.barrelType_L,text: _loginUserLanguageResource.barrelType_L});
 	                editor = { type: 'combobox', data: source, valueField: 'id', textField: 'text', allowInput: false };
 	            } else if (col.dataIndex === 'pumpTypeName') {
 	               	source.push({id: '杆式泵',text: '杆式泵'});
	               	source.push({id: '管式泵',text: '管式泵'});
 	                editor = { type: 'combobox', data: source, valueField: 'id', textField: 'text', allowInput: false };
 	            } else if (col.dataIndex === 'pumpGrade') {
 	                // 根据barrelTypeName动态变化（在cellbeginedit中处理）
 	                source.push({id: '1',text: '1'});
 	               	source.push({id: '2',text: '2'});
 	              	source.push({id: '3',text: '3'});
 	             	source.push({id: '4',text: '4'});
 	            	source.push({id: '5',text: '5'});
 	                editor = { type: 'combobox', data: source, valueField: 'id', textField: 'text', allowInput: false };
 	            } else if (col.dataIndex.indexOf('rodGrade') !== -1) {
 	               	source.push({id: '',text: ''});
	               	source.push({id: 'A',text: 'A'});
	              	source.push({id: 'B',text: 'B'});
	             	source.push({id: 'C',text: 'C'});
	            	source.push({id: 'D',text: 'D'});
	            	source.push({id: 'K',text: 'K'});
	            	source.push({id: 'KD',text: 'KD'});
	            	source.push({id: 'HL',text: 'HL'});
	            	source.push({id: 'HY',text: 'HY'});
 	                editor = { type: 'combobox', data: source, valueField: 'id', textField: 'text', allowInput: false };
 	            } else if (col.dataIndex.indexOf('rodTypeName') !== -1) {
 	               	source.push({id: '',text: ''});
	               	source.push({id: _loginUserLanguageResource.rodStringTypeValue1,text: _loginUserLanguageResource.rodStringTypeValue1});
	              	source.push({id: _loginUserLanguageResource.rodStringTypeValue2,text: _loginUserLanguageResource.rodStringTypeValue2});
	             	source.push({id: _loginUserLanguageResource.rodStringTypeValue3,text: _loginUserLanguageResource.rodStringTypeValue3});
 	                editor = { type: 'combobox', data: source, valueField: 'id', textField: 'text', allowInput: false };
 	            } else {
 	                // 数值文本
 	                editor = { type: 'textbox' };
 	            }
 	           	column.editor = editor;
	        }

	        cols.push(column);
	    }
	    return cols;
	}
	// 单元格开始编辑事件 - 动态修改 pumpGrade 下拉选项
 	function onPcpSingleCellBeginEdit(e) {
 	    var field = e.field;
 	    if (field !== 'pumpGrade') return; // 仅处理泵级别列
 	    
 	    var grid = e.sender;
 	    var rowIndex = e.rowIndex;
 	    var record = grid.getRow(rowIndex);
 	    if (!record) return;
 	    
 	    // 获取 barrelTypeName 字段的值
 	    var barrelType = record.barrelTypeName || '';
 	    var editor = e.editor;
 	    if (!editor) return;
 	    
 	    // 根据 barrelType 决定可选项
 	    var options = [];
 	    // 获取国际化值
 	    var barrelTypeH = _loginUserLanguageResource.barrelType_H;
 	    var barrelTypeL = _loginUserLanguageResource.barrelType_L;
 	    
 	    if (barrelType === barrelTypeH) {
 	        options = ['1', '2', '3', '4', '5'];
 	    } else if (barrelType === barrelTypeL) {
 	        options = ['1', '2', '3'];
 	    } else {
 	        // 默认全部
 	        options = ['1', '2', '3', '4', '5'];
 	    }
 	    
 	    // 构建 source 数据（MiniUI combobox 的 data 格式为 [{id:..., text:...}]）
 	    var sourceData = [];
 	    for (var i = 0; i < options.length; i++) {
 	        sourceData.push({ id: options[i], text: options[i] });
 	    }
 	    editor.setData(sourceData);
 	    
 	    // 如果当前值不在新的选项列表中，清空
 	    var currentValue = record.pumpGrade;
 	    if (currentValue && options.indexOf(currentValue) === -1) {
 	        // 可以提示或清空，此处不清空，让用户手动选择
 	    }
 	}
	// 单元格编辑提交（收集变更）
	function onPcpSingleCellCommitEdit(e) {
	    var record = e.record;
	    var field = e.field;
	    var value = e.value;
	    var oldValue = e.oldValue;

	    if (value !== oldValue) {
	        var recordId = record.recordId || record.id;   // 注意主键字段名
	        if (recordId && recordId > 0) {
	            var found = false;
	            for (var i = 0; i < pcpEditData.updatelist.length; i++) {
	                if (pcpEditData.updatelist[i].recordId === recordId) {
	                    pcpEditData.updatelist[i][field] = value;
	                    found = true;
	                    break;
	                }
	            }
	            if (!found) {
	                var updateRecord = mini.clone(record);
	                updateRecord[field] = value;
	                pcpEditData.updatelist.push(updateRecord);
	            }
	        }
	    }
	}
	// 保存编辑（修改历史数据计算）
	function savePCPSingleData() {
	    var hasChange = (pcpEditData.updatelist.length > 0 || pcpEditData.insertlist.length > 0 || pcpEditData.delidslist.length > 0);
	    if (!hasChange) {
	        mini.alert(_loginUserLanguageResource.noDataChange || '无数据变更');
	        return;
	    }

	    var saveData = {
	        updatelist: pcpEditData.updatelist,
	        insertlist: pcpEditData.insertlist,
	        delidslist: pcpEditData.delidslist
	    };

	    mini.confirm(_loginUserLanguageResource.confirmSave || '确认保存？', _loginUserLanguageResource.tip, function(action) {
	        if (action === 'ok') {
	            $.ajax({
	                url: context + '/calculateManagerController/saveRecalculateData',
	                type: 'POST',
	                data: {
	                    data: JSON.stringify(saveData),
	                    deviceType: 0,          // 设备类型，默认0
	                    applicationScenarios: currentApplicationScenarios || 0,
	                    calculateType: 2         // PCP
	                },
	                dataType: 'json',
	                success: function(result) {
	                    if (result.success) {
	                        mini.alert(_loginUserLanguageResource.calculateMaintainingEditSuccessInfo || '保存成功');
	                        pcpEditData = { updatelist: [], insertlist: [], delidslist: [] };
	                        loadPCPSingleData();
	                    } else {
	                        mini.alert(result.msg || _loginUserLanguageResource.saveFailed);
	                    }
	                },
	                error: function() {
	                    mini.alert(_loginUserLanguageResource.requestFailed);
	                }
	            });
	        }
	    });
	}
	// 关联生产数据计算
	function linkPCPSingleData() {
	    var orgId = window.parent && window.parent.mini ? window.parent.mini.get('leftOrg_Id').getValue() : '';
	    var deviceName = currentDeviceName;
	    var startDate = mini.get('pcpSingleStartDate') ? mini.get('pcpSingleStartDate').getFormValue('yyyy-MM-dd HH:mm:ss') : '';
	    var endDate = mini.get('pcpSingleEndDate') ? mini.get('pcpSingleEndDate').getFormValue('yyyy-MM-dd HH:mm:ss') : '';
	    var calculateSign = mini.get('pcpSingleStatusCombo') ? mini.get('pcpSingleStatusCombo').getValue() : '';

	    var confirmMsg = _loginUserLanguageResource.takeEffectScope + ':' + (deviceName || _loginUserLanguageResource.allPCPCalculateWell) + ' ' + 
	                    (startDate ? startDate + '~' + endDate : '') + ' <br/><font color=red>' + 
	                    (_loginUserLanguageResourceFirstLower ? _loginUserLanguageResourceFirstLower.calculateMaintainingConfirm : '确认执行？') + '</font>';

	    mini.confirm(confirmMsg, _loginUserLanguageResource.tip, function(action) {
	        if (action === 'ok') {
	            $.ajax({
	                url: context + '/calculateManagerController/recalculateByProductionData',
	                type: 'POST',
	                data: {
	                    orgId: orgId,
	                    deviceId: currentDeviceId,
	                    deviceName: deviceName,
	                    startDate: startDate,
	                    endDate: endDate,
	                    calculateSign: calculateSign,
	                    calculateType: 2,      // PCP
	                    deviceType: 0
	                },
	                dataType: 'json',
	                success: function(result) {
	                    if (result.success) {
	                        mini.alert(_loginUserLanguageResource.calculateMaintainingEditSuccessInfo || '关联计算成功');
	                        pcpEditData = { updatelist: [], insertlist: [], delidslist: [] };
	                        loadPCPSingleData();
	                    } else {
	                        mini.alert(result.msg || _loginUserLanguageResource.operationFailed);
	                    }
	                },
	                error: function() {
	                    mini.alert(_loginUserLanguageResource.requestFailed);
	                }
	            });
	        }
	    });
	}
	// 导出请求数据（单条）
	function exportPCPSingleData() {
	    var grid = mini.get('pcpSingleGrid');
	    if (!grid) return;
	    var rows = grid.getSelecteds();
	    if (rows.length === 0) {
	        mini.alert(_loginUserLanguageResource.noSelectionRecord);
	        return;
	    }
	    for (var i = 0; i < rows.length; i++) {
	        var record = rows[i];
	        var url = context + '/calculateManagerController/exportCalculateRequestData?recordId=' + record.recordId +
	                  '&deviceName=' + encodeURIComponent(encodeURIComponent(record.deviceName)) +
	                  '&acqTime=' + formatDate(record.acqTime, 'yyyy-MM-dd HH:mm:ss') +
	                  '&calculateType=2';
	        downloadFile(url);
	    }
	}
	// 删除单条记录
	function deletePCPSingleData() {
	    var grid = mini.get('pcpSingleGrid');
	    if (!grid) return;
	    var rows = grid.getSelecteds();
	    if (rows.length === 0) {
	        mini.alert(_loginUserLanguageResource.noSelectionRecord);
	        return;
	    }
	    var recordIds = [];
	    var acqTimes = [];
	    for (var i = 0; i < rows.length; i++) {
	        recordIds.push(rows[i].recordId);
	        acqTimes.push(formatDate(rows[i].acqTime, 'yyyy-MM-dd HH:mm:ss'));
	    }

	    var deleteInfo = _loginUserLanguageResource.confirmDelete;
	    if (recordIds.length === 1) {
	        deleteInfo = _loginUserLanguageResource.deviceName + ':<font color=red>' + (rows[0].deviceName || '') + '</font>' +
	                     '</br>' + _loginUserLanguageResource.cloudAcqtime + ':<font color=red>' + (acqTimes[0] || '') + '</font>' +
	                     '</br>' + _loginUserLanguageResource.confirmDelete;
	    } else {
	        deleteInfo = _loginUserLanguageResource.deviceName + ':<font color=red>' + (rows[0].deviceName || '') + '</font>' +
	                     '</br>' + _loginUserLanguageResource.sparseRecordCount + ':<font color=red>' + recordIds.length + '</font>' +
	                     '</br>' + _loginUserLanguageResource.confirmDelete;
	    }

	    mini.confirm(deleteInfo, _loginUserLanguageResource.tip, function(action) {
	        if (action === 'ok') {
	            $.ajax({
	                url: context + '/calculateManagerController/deleteCalculateData',
	                type: 'POST',
	                data: {
	                    deviceId: currentDeviceId,
	                    calculateType: 2,
	                    recordIds: recordIds.join(',')
	                },
	                dataType: 'json',
	                success: function(result) {
	                    if (result.success) {
	                        mini.alert(_loginUserLanguageResource.deleteSuccessfully);
	                        pcpEditData = { updatelist: [], insertlist: [], delidslist: [] };
	                        loadPCPSingleData();
	                    } else {
	                        mini.alert(result.msg || _loginUserLanguageResource.saveFailed);
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
	// 转速计产（PCP）汇总记录
	// ================================================================
	function loadPCPTotalData() {
    	var grid = mini.get('pcpTotalGrid');
    	if (!grid) return;
    	grid.setUrl(context + '/calculateManagerController/getTotalCalculateResultData');
    	grid.load();
	}
	function onPcpTotalGridBeforeLoad(e) {
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
	    params.dictDeviceType = params.deviceType;
	    if (params.dictDeviceType && params.dictDeviceType.indexOf(',') > -1) {
	        params.dictDeviceType = currentLevel1 ? currentLevel1.deviceTypeId : deviceType;
	    }

	    var startDate = mini.get('pcpTotalStartDate');
	    var endDate = mini.get('pcpTotalEndDate');
	    params.startDate = startDate ? startDate.getFormValue('yyyy-MM-dd') : '';
	    params.endDate = endDate ? endDate.getFormValue('yyyy-MM-dd') : '';
	    params.calculateType = 4;   // PCP 汇总（假设是4，与SRP汇总的3区分）

	    e.params = params;
	}
	function onPcpTotalGridLoad(e) {
	    var grid = e.sender;
	    var result = e.result;
	    if (result && result.columns) {
	        if (result.startDate) {
	            var startDate = mini.get('pcpTotalStartDate');
	            var endDate = mini.get('pcpTotalEndDate');
	            if (startDate && !startDate.getValue()) startDate.setValue(result.startDate);
	            if (endDate && !endDate.getValue()) endDate.setValue(result.endDate);
	        }
	        var columns = buildTotalColumns(result.columns); // 复用通用的汇总列构建
	        grid.setColumns(columns);
	        grid.doLayout();
	        grid.deselectAll(false);
	    }
	}
	// 重新汇总计算（PCP）
	function reTotalPCP() {
	    var grid = mini.get('pcpTotalGrid');
	    if (!grid) return;
	    var rows = grid.getSelecteds();
	    if (rows.length === 0) {
	        mini.alert(_loginUserLanguageResource.noSelectionRecord);
	        return;
	    }
	    var reCalculateData = '';
	    for (var i = 0; i < rows.length; i++) {
	        var r = rows[i];
	        reCalculateData += r.id + ',' + r.deviceId + ',' + r.deviceName + ',' + r.calDate + ';';
	    }
	    reCalculateData = reCalculateData.substring(0, reCalculateData.length - 1);

	    mini.confirm(_loginUserLanguageResource.confirmOperation, _loginUserLanguageResource.tip, function(action) {
	        if (action === 'ok') {
	            var mask = mini.mask({
	                el: 'pcpTotalPanel',
	                cls: 'mini-mask-loading',
	                html: _loginUserLanguageResource.recalculating + '...'
	            });
	            $.ajax({
	                url: context + '/calculateManagerController/reTotalCalculate',
	                type: 'POST',
	                data: {
	                    deviceType: 1,
	                    reCalculateDate: reCalculateData,
	                    calculateType: 2    // PCP
	                },
	                dataType: 'json',
	                success: function(result) {
	                    mini.unmask('pcpTotalPanel');
	                    if (result.success) {
	                        mini.alert(_loginUserLanguageResource.recalculationComplete);
	                        loadPCPTotalData();
	                    } else {
	                        mini.alert(result.msg || _loginUserLanguageResource.operationFailed);
	                    }
	                },
	                error: function() {
	                    mini.unmask('pcpTotalPanel');
	                    mini.alert(_loginUserLanguageResource.requestFailed);
	                }
	            });
	        }
	    });
	}
	// 导出汇总请求数据（PCP）
	function exportPCPTotalData() {
	    var grid = mini.get('pcpTotalGrid');
	    if (!grid) return;
	    var rows = grid.getSelecteds();
	    if (rows.length === 0) {
	        mini.alert(_loginUserLanguageResource.noSelectionRecord);
	        return;
	    }
	    for (var i = 0; i < rows.length; i++) {
	        var r = rows[i];
	        var url = context + '/calculateManagerController/exportTotalCalculateRequestData?recordId=' + r.id +
	                  '&deviceId=' + r.deviceId +
	                  '&deviceName=' + encodeURIComponent(encodeURIComponent(r.deviceName)) +
	                  '&calDate=' + encodeURIComponent(r.calDate) +
	                  '&calculeteType=1';   // PCP
	        downloadFile(url);
	    }
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