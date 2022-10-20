<%@ page language="java" import="java.util.*,java.net.*,java.io.*" pageEncoding="UTF-8"%>
<%!
boolean isConnectInt(){
	
	boolean isConnection=false;
	HttpURLConnection http=null; 
	String youURL= "http://www.baidu.com";//输入一个网站，判断能否连接上。 
	try { 
			URL url = new URL(youURL); 
			http = (HttpURLConnection) url.openConnection(); 
	} 
	catch (IOException ex) { 
			ex.printStackTrace(); 
			//System.out.println( "建立网络连接发生错误: " + ex.getMessage()); 
			return false; 
	} 
	try{
		if (http.getResponseCode() != 200) { 
			   return false; 
				//System.out.println( "网络连接失败 "); 
		}else{
			   //System.out.println( "网络连接成功 "); 
			   return true;
		}
	}catch(IOException ex){
		return false; 
	}
	
}
%>
<%
String path = request.getContextPath(); 
String extBasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//boolean isConnection=isConnectInt();
boolean isConnection=false;
%>
<link rel="stylesheet" href="<%=path%>/styles/banner.css?timestamp=202210201830" type="text/css"/>
<link rel="stylesheet" href="<%=path%>/styles/style.css?timestamp=202210201830" type="text/css"/>
<link rel="stylesheet" href="<%=path%>/styles/icon.css?timestamp=202210201830" type="text/css"/>
<link rel="stylesheet" href="<%=path%>/scripts/extjs/resources/classic/ext-theme-classic/theme-classic-all.css?timestamp=202210201830"  type="text/css"/>
<link rel="stylesheet" href="<%=path%>/scripts/extjs/ux/classic/resources/ux-all.css?timestamp=202210201830"  type="text/css"/>
<link rel="stylesheet" href="<%=path%>/scripts/extjs/ux/rowEditor/RowEditing.css?timestamp=202210201830"  type="text/css"/>
<link rel="stylesheet" href="<%=path%>/scripts/customMap/css/wellmap.css?timestamp=202210201830" type="text/css"/>
<link rel="stylesheet" href="<%=path%>/scripts/handsontable/css/handsontable.full.min.css?timestamp=202210201830" type="text/css"/>
<link rel="stylesheet" href="<%=path%>/scripts/handsontable/css/table.css?timestamp=202210201830" type="text/css"/>
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
	
	var isConnectionInternet=<%=isConnection%>;
</script>
<!-- Extjs 核心脚本-->
<script type="text/javascript" src="<%=path%>/scripts/extjs/ext-all.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/date/Month.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/date/DateTimePicker.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/date/DateTimeField.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/rowEditor/RowEditing.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/rowEditor/CellEditing.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/message.js?timestamp=202210201830"></script>
<!-- Extjs 中文脚本 -->
<script type="text/javascript" src="<%=path%>/scripts/extjs/locale/locale-${browserLang}.js?timestamp=202210201830"></script> 
<script type="text/javascript" src="<%=path%>/app/locale.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/form/PageNumberToolbar.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/app/app.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/app/CommUtils.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/scripts/jquery/jquery-3.5.1.min.js?timestamp=202210201830"></script>

<!--<script src="https://open.ys7.com/sdk/js/1.3/ezuikit.js"></script>-->
<script type="text/javascript" src="<%=path%>/scripts/UIKit/ezuikit.js?timestamp=202210201830"></script>

<script type="text/javascript" src="<%=path%>/app/ajaxfilter.js?timestamp=202210201830"></script>
<!-- highcharts -->
<script type="text/javascript" src="<%=path%>/scripts/highcharts/highstock.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/scripts/highcharts/grid.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/scripts/highcharts/highcharts-more.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/scripts/highcharts/highcharts-3d.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/scripts/highcharts/exporting.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/scripts/highcharts/offline-exporting.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/scripts/highcharts/highcharts-zh_CN.js?timestamp=202210201830"></script>

<script type="text/javascript" src="<%=path%>/scripts/extjs/ux/ux.js?timestamp=202210201830"></script>
<script type="text/javascript" src="<%=path%>/scripts/customMap/js/wellmap.js?timestamp=202210201830"></script>

<!-- handsontable -->
<script type="text/javascript" src="<%=path%>/scripts/handsontable/js/handsontable.full.min.js?timestamp=202210201830"></script>

<!-- reconnecting-websocket -->
<script type="text/javascript" src="<%=path%>/scripts/reconnecting-websocket/reconnecting-websocket.js?timestamp=202210201830"></script>
<!-- JavaScript （结束） -->