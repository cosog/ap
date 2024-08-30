<%@ page language="java" import="java.util.*,java.net.*,java.io.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath(); 
String extBasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String bannerCSS=(String)session.getAttribute("bannerCSS");
bannerCSS=bannerCSS.substring(bannerCSS.indexOf("/"),bannerCSS.length());

boolean showVideo=(boolean)session.getAttribute("showVideo");

String oemStaticResourceTimestamp=(String)session.getAttribute("oemStaticResourceTimestamp");
String otherStaticResourceTimestamp=(String)session.getAttribute("otherStaticResourceTimestamp");

%>
<link rel="stylesheet" href="<%=path+bannerCSS%>?timestamp=<%=oemStaticResourceTimestamp%>>" type="text/css"/>
<link rel="stylesheet" href="<%=path%>/styles/style.css?timestamp=<%=otherStaticResourceTimestamp%>" type="text/css"/>
<link rel="stylesheet" href="<%=path%>/styles/icon.css?timestamp=<%=otherStaticResourceTimestamp%>" type="text/css"/>
<link rel="stylesheet" href="<%=path%>/scripts/extjs/resources/classic/ext-theme-classic/theme-classic-all.css?timestamp=<%=otherStaticResourceTimestamp%>"  type="text/css"/>
<link rel="stylesheet" href="<%=path%>/scripts/extjs/ux/classic/resources/ux-all.css?timestamp=<%=otherStaticResourceTimestamp%>"  type="text/css"/>
<link rel="stylesheet" href="<%=path%>/scripts/extjs/ux/rowEditor/RowEditing.css?timestamp=<%=otherStaticResourceTimestamp%>"  type="text/css"/>
<link rel="stylesheet" href="<%=path%>/scripts/handsontable/css/handsontable.full.min.css?timestamp=<%=otherStaticResourceTimestamp%>" type="text/css"/>
<link rel="stylesheet" href="<%=path%>/scripts/handsontable/css/table.css?timestamp=<%=otherStaticResourceTimestamp%>" type="text/css"/>
<!-- 样式区 （结束） --> 
<!-- 定义Extjs常量 -->
<script type="text/javascript">
	//域路径
	var ctx = '<%=path%>';
	    context='<%=path%>'; 
	//域名访问路径
	var extBasePath = '<%=extBasePath%>';
	//默认后台分页数据量
	var defaultPageRecordNum = 25;
</script>
<!-- Extjs 核心脚本-->
<script type="text/javascript" src="<%=path%>/scripts/extjs/ext-all.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<!--
<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/date/Month.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/date/DateTimePicker.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/date/DateTimeField.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
-->
<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/rowEditor/RowEditing.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/rowEditor/CellEditing.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<!--<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/message.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>-->
<!-- Extjs 中文脚本 -->
<script type="text/javascript" src="<%=path%>/scripts/extjs/locale/locale-${browserLang}.js?timestamp=<%=otherStaticResourceTimestamp%>"></script> 
<script type="text/javascript" src="<%=path%>/app/locale.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>

<!--
<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/ux.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/form/PageNumberToolbar.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
-->

<script type="text/javascript" src="<%=path%>/app/app.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<script type="text/javascript" src="<%=path%>/app/CommUtils.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<script type="text/javascript" src="<%=path%>/scripts/jquery/jquery-3.6.1.min.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>

<!--<script src="https://open.ys7.com/sdk/js/1.3/ezuikit.js"></script>-->
<%if(showVideo){ %>
<script type="text/javascript" src="<%=path%>/scripts/UIKit/ezuikit.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<%} %>

<script type="text/javascript" src="<%=path%>/app/ajaxfilter.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<!-- highcharts -->
<script type="text/javascript" src="<%=path%>/scripts/highcharts/highstock.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<script type="text/javascript" src="<%=path%>/scripts/highcharts/grid.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<script type="text/javascript" src="<%=path%>/scripts/highcharts/highcharts-more.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<!--<script type="text/javascript" src="<%=path%>/scripts/highcharts/highcharts-3d.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>-->
<script type="text/javascript" src="<%=path%>/scripts/highcharts/exporting.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<!--<script type="text/javascript" src="<%=path%>/scripts/highcharts/offline-exporting.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>-->
<script type="text/javascript" src="<%=path%>/scripts/highcharts/highcharts-zh_CN.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>


<!-- handsontable -->
<script type="text/javascript" src="<%=path%>/scripts/handsontable/js/handsontable.full.min.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>

<!-- reconnecting-websocket -->
<script type="text/javascript" src="<%=path%>/scripts/reconnecting-websocket/reconnecting-websocket.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
<!-- JavaScript （结束） -->