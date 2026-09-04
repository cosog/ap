<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>选择字段</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body {
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
        .mini-toolbar {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 36px;
            padding: 4px 10px;
            border-bottom: 1px solid #e8e8e8;
            background: #fff;
            display: flex;
            align-items: center;
            z-index: 10;
            box-sizing: border-box;
        }
        /* 外层包裹：绝对定位，负责滚动 */
        .grid-wrapper {
            position: absolute;
            top: 36px;
            left: 0;
            right: 0;
            bottom: 0;
            overflow: hidden;    /* 改为 hidden，禁止父级滚动 */
            padding: 4px;
            box-sizing: border-box;
        }
        
    </style>
</head>
<body>
<div class="main-container">
    <!-- 工具条 -->
    <div class="mini-toolbar">
        <span style="flex:1;"></span>
        <button id="btnSave" class="mini-button" iconCls="save" onclick="onSave()">Save</button>
    </div>
    <!-- 外层滚动容器 -->
    <div class="grid-wrapper">
        <div id="fieldGrid" class="mini-datagrid"  style="width:100%;height:100%;"
             allowResize="false"
             showPager="false" 
             multiSelect="false" 
             allowCellSelect="false" 
             allowRowSelect="true"
             dataField="totalRoot" 
             totalField="totalCount" 
             onbeforeload="onBeforeLoad"
             onload="onLoad"
             onrowclick="onRowClick">
            <div property="columns">
                <div field="id" width="0" visible="false"></div>
            </div>
        </div>
    </div>
</div>

<script>
    var context = '<%=context%>';
    var protocolCode = '';
    var selectedRow = -1;
    var selectedCol = -1;
    var fieldType = 0;
    var currentValue = '';

    function initI18n() {
        document.title = _loginUserLanguageResource.config;
        var btnSave = mini.get('btnSave');
        if (btnSave) btnSave.setText(_loginUserLanguageResource.save);
    }

    function setData(data) {
        protocolCode = data.protocolCode;
        selectedRow = data.row;
        selectedCol = data.col;
        fieldType = data.fieldType;
        currentValue = data.currentValue;

        var grid = mini.get('fieldGrid');
        if (!grid) return;
        if (!grid.getUrl()) {
            grid.setUrl(context + '/acquisitionUnitManagerController/getProtocolExtendedFieldItems');
        }
        grid.load();
    }

    function onBeforeLoad(e) {
        var params = e.params || {};
        params.protocolCode = protocolCode;
        params.protocolExtendedFieldType = fieldType;
        e.params = params;
    }

    function onLoad(e) {
        var grid = e.sender;
        var result = e.result;

        if (result.columns && result.columns.length > 0) {
            var miniColumns = [];
            miniColumns.push({
                type: "checkcolumn",
                width: 40,
                header: "",
                headerAlign: "center",
                align: "center"
            });
            for (var i = 0; i < result.columns.length; i++) {
                var col = result.columns[i];
                var miniCol = {
                    field: col.dataIndex,
                    header: col.header,
                    headerAlign: 'center',
                    align: 'center'
                };
                if (col.flex) miniCol.flex = col.flex;
                else if (col.width) miniCol.width = col.width;
                else miniCol.width = 100;
                if (col.dataIndex === 'id' && col.xtype === 'rownumberer') {
                    miniCol.type = 'indexcolumn';
                    miniCol.width = col.width || 40;
                    delete miniCol.field;
                }
                miniColumns.push(miniCol);
            }
            grid.setColumns(miniColumns);
        }
        if (currentValue) {
        	var rows = grid.data;
            var selectRow=-1;
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].itemName === currentValue) {
                	selectRow=i;
                    break;
                }
            }
            if(selectRow>=0){
            	grid.select(selectRow);
                setTimeout(function() {
                	grid.scrollIntoView(selectRow);
                }, 150);
            }
        }
    }

    function onRowClick(e) {}
    
    function onSave() {
        var grid = mini.get('fieldGrid');
        var row = grid.getSelected();
        if (!row) {
            mini.alert(_loginUserLanguageResource.checkOne);
            return;
        }
        var selectedItem = row.itemName;
        if (window.parent && window.parent.setExtendedFieldValue) {
            window.parent.setExtendedFieldValue(selectedRow, selectedCol, selectedItem);
        }
        window.CloseOwnerWindow('ok');
    }

    $(document).ready(function () {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>