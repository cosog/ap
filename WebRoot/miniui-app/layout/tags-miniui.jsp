<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
String browserLang = (String)session.getAttribute("browserLang");
if(browserLang == null) browserLang = "zh_CN";
%>
<!-- ============================================================== -->
<!--  MiniUI 样式                                                    -->
<!--  使用 metro-blue（扁平现代风格）                                -->
<!-- ============================================================== -->

<!-- metro-blue 主题 -->
<link rel="stylesheet" href="<%=path%>/scripts/miniui/themes/metro-blue/skin.css?timestamp=<%=otherStaticResourceTimestamp%>" type="text/css"/>

<!-- ============================================================== -->
<!--  JavaScript 库                                                  -->
<!-- ============================================================== -->

<!-- jQuery -->
<script src="<%=path%>/scripts/jquery/jquery-3.6.1.min.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>

<!-- MiniUI 核心 -->
<script src="<%=path%>/scripts/miniui/miniui.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<script src="<%=path%>/scripts/miniui/locale/zh_CN.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>

<!-- MiniUI 公共配置 -->
<script src="<%=path%>/miniui-app/common/miniui-common.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>

<!-- Highcharts -->
<script src="<%=path%>/scripts/highcharts/highstock.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<script src="<%=path%>/scripts/highcharts/highcharts-more.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<script src="<%=path%>/scripts/highcharts/exporting.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<script src="<%=path%>/scripts/highcharts/export-data.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<script src="<%=path%>/scripts/highcharts/offline-exporting.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<script src="<%=path%>/scripts/highcharts/drilldown.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<% if(!"en".equalsIgnoreCase(browserLang)) { %>
<script src="<%=path%>/scripts/highcharts/highcharts-<%=browserLang%>.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<% } %>

<!-- WebSocket -->
<script src="<%=path%>/scripts/reconnecting-websocket/reconnecting-websocket.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>

<!-- MiniUI 版通用工具 -->
<script src="<%=path%>/miniui-app/common/miniui-commutils.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>