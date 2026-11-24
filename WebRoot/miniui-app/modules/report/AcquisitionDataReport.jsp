<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <script src="js/acquisitionDataReport.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
    <style>
        html, body {
            margin: 0; padding: 0;
            width: 100%; height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #f0f2f5;
        }
        .acquisition-report-container {
            width: 100%; height: 100%;
            display: flex; flex-direction: column;
            background: #fff;
        }
        /* ============ 主区域 ============ */
        .report-main-area {
            flex: 1;
            display: flex;
            overflow: hidden;
        }
        /* ============ 左侧二级标签 ============ */
        .level2-sidebar {
            flex-shrink: 0;
            width: 32px;
            background: #f5f7fa;
            border-right: 1px solid #e8e8e8;
            overflow-y: auto;
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
        /* ============ 内容区 ============ */
        .report-content-area {
            flex: 1;
            display: flex;
            flex-direction: column;
            overflow: hidden;
        }
        /* 单设备报表 / 区域报表切换 */
        .report-type-tabs {
            flex-shrink: 0;
            display: flex;
            background: #fafafa;
            border-bottom: 1px solid #e8e8e8;
            padding: 0 8px;
        }
        .report-type-tabs .report-tab {
            padding: 8px 18px;
            font-size: 13px;
            cursor: pointer;
            color: #666;
            border-bottom: 2px solid transparent;
            user-select: none;
            transition: all 0.2s;
        }
        .report-type-tabs .report-tab:hover { color: #333; }
        .report-type-tabs .report-tab.active {
            color: #1890ff;
            font-weight: bold;
            border-bottom-color: #1890ff;
        }
        .report-type-body {
            flex: 1;
            overflow: hidden;
        }
        /* ============ 底部一级标签 ============ */
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
            color: #1890ff;
            font-weight: bold;
            border-bottom-color: #1890ff;
        }
        /* ============ 内部工具栏 / 内容 ============ */
        .report-inner-container {
            display: flex; flex-direction: column;
            width: 100%; height: 100%;
        }
        .report-toolbar-area {
            flex-shrink: 0;
            background: #fafafa;
            border-bottom: 1px solid #e8e8e8;
            padding: 4px 8px;
            display: flex;
            align-items: center;
            flex-wrap: wrap;
            gap: 4px;
        }
        .report-toolbar-area .toolbar-label {
            font-size: 12px; color: #333;
            margin: 0 2px 0 8px;
        }
        .report-toolbar-area .toolbar-sep {
            display: inline-block;
            width: 1px; height: 20px;
            background: #ddd;
            margin: 0 4px;
        }
        .report-toolbar-spacer {
            flex: 1;
        }
        .report-body-area { flex: 1; overflow: hidden; }
        /* ============ 数据表 ============ */
        .report-data-table {
            width: 100%;
            border-collapse: collapse;
            font-size: 12px;
        }
        .report-data-table th,
        .report-data-table td {
            border: 1px solid #ddd;
            padding: 4px 6px;
            text-align: center;
            white-space: nowrap;
        }
        .report-data-table th {
            background: #f5f5f5;
            font-weight: bold;
            position: sticky;
            top: 0;
            z-index: 1;
        }
        .report-data-table tr:nth-child(even) td { background: #fafafa; }
        .report-empty-msg {
            text-align: center;
            padding: 40px;
            color: #999;
            font-size: 13px;
        }
        .report-empty-msg.error { color: #ff4d4f; }
    </style>
    <script>
        var context = '<%=context%>';
    </script>
</head>
<body>
<div class="acquisition-report-container">
    <div class="report-main-area">
        <div class="level2-sidebar" id="level2Sidebar"></div>
        <div class="report-content-area" id="reportContentArea"></div>
    </div>
    <div class="level1-footer" id="level1Footer"></div>
</div>
<script>
    $(document).ready(function () {
        mini.parse();
        initAcquisitionDataReportPage();
    });
</script>
</body>
</html>