<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String otherStaticResourceTimestamp = (String)session.getAttribute("otherStaticResourceTimestamp");
if(otherStaticResourceTimestamp == null) otherStaticResourceTimestamp = "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>曲线配置</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <link rel="stylesheet" href="<%=path%>/scripts/miniui/third-party/spectrum/spectrum.css?timestamp=<%=otherStaticResourceTimestamp%>" />
    <script src="<%=path%>/scripts/miniui/third-party/spectrum/spectrum.js?timestamp=<%=otherStaticResourceTimestamp%>"></script>
    <style>
        html, body { height:100%; margin:0; padding:0; overflow:hidden; }
        .form-row { margin-bottom:12px; display:flex; align-items:center; }
        .form-label { width:80px; text-align:right; padding-right:8px; font-size:13px; flex-shrink:0; white-space:nowrap; }
        .form-control { flex:1; }
        .color-preview { display:inline-block; width:24px; height:24px; border:1px solid #ccc; border-radius:4px; margin-left:5px; cursor:pointer; vertical-align:middle; }
        .btn-row { text-align:center; padding:10px 0; border-top:1px solid #e8e8e8; background:#fff; flex-shrink:0; }
        .btn-row .mini-button { margin:0 10px; width:80px; }
        .sp-container { z-index:10002 !important; }
        .group-tab-content { height:100%; display:flex; flex-direction:column; padding:0; box-sizing:border-box; }
        .group-tab-content .mini-splitter { flex:1; width:100%; }
        .group-tab-content .grid-wrapper { height:100%; padding:4px; box-sizing:border-box; background:#fafafa; display:flex; flex-direction:column; }
        .group-tab-content .grid-wrapper .mini-datagrid { flex:1; width:100%; }
        /* 属性页容器：flex 列，高度100% */
        .property-tab-content {
            height:100%;
            display:flex;
            flex-direction:column;
            background:#fff;
        }
        .property-tab-content .scroll-content {
            flex:1;
            overflow:auto;
            padding:15px 20px;
        }
        .mini-tabs-body { padding:0 !important; }
        .mini-tab-body { padding:0 !important; }
        .grid-title-bar {
            border-bottom:1px solid #e8e8e8;
            padding:2px 8px;
            background:#f5f5f5;
            flex-shrink:0;
            font-weight:bold;
            font-size:13px;
            display:flex;
            align-items:center;
        }
        .mini-button { font-size: 13px; }
    </style>
</head>
<body>
    <!-- 隐藏域 -->
    <input id="curveConfigRow" class="mini-hidden" />
    <input id="curveConfigCol" class="mini-hidden" />
    <input id="curveConfigTableType" class="mini-hidden" value="0" />
    <input id="curveConfigCurveType" class="mini-hidden" />

    <!-- ==================== Tabs ==================== -->
    <div id="curveConfigTabs" class="mini-tabs" style="width:100%;height:100%;" activeIndex="1">
        <!-- ======== 曲线组 Tab ======== -->
        <div id="groupTab" name="groupTab" style="height:100%;">
            <!-- 内容不变（略）... -->
            <div class="group-tab-content">
                <div class="mini-toolbar" style="border-bottom:1px solid #e8e8e8;padding:2px 4px;flex-shrink:0;background:#fafafa;display:flex;align-items:center;">
                    <span style="flex:1;"></span>
                    <button id="btnSaveGroup" class="mini-button" iconCls="save" onclick="saveCurveGroup()">保存</button>
                </div>
                <div class="mini-splitter" vertical="true" style="flex:1;width:100%;">
                    <div size="50%" showCollapseButton="false">
                        <div class="grid-wrapper">
                            <div class="grid-title-bar"><span id="realtimeTitle">实时曲线组</span></div>
                            <div id="realtimeCurveGroupGrid" class="mini-datagrid" style="height:100%;width:100%;"
                                 idField="groupId" dataField="totalRoot" totalField="totalCount"
                                 allowCellEdit="true" allowCellSelect="true" showPager="false"
                                 url="<%=path%>/acquisitionUnitManagerController/getCurveGroupData"
                                 onbeforeload="onRealtimeGridBeforeLoad" onload="onRealtimeGridLoad"
                                 oncellvalidation="onCellValidation">
                            </div>
                        </div>
                    </div>
                    <div size="50%" showCollapseButton="false">
                        <div class="grid-wrapper">
                            <div class="grid-title-bar"><span id="historyTitle">历史曲线组</span></div>
                            <div id="historyCurveGroupGrid" class="mini-datagrid" style="height:100%;width:100%;"
                                 idField="groupId" dataField="totalRoot" totalField="totalCount"
                                 allowCellEdit="true" allowCellSelect="true" showPager="false"
                                 url="<%=path%>/acquisitionUnitManagerController/getCurveGroupData"
                                 onbeforeload="onHistoryGridBeforeLoad" onload="onHistoryGridLoad"
                                 oncellvalidation="onCellValidation">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- ======== ★★★ 曲线属性 Tab（修改部分） ★★★ ======== -->
        <div id="propertyTab" name="propertyTab" style="height:100%;">
            <div class="property-tab-content">
                <!-- 可滚动的内容区域 -->
                <div class="scroll-content">
                    <div class="form-row">
                        <span class="form-label"><font color="red">*</font><span id="lblCurveGroup">曲线组</span>：</span>
                        <div class="form-control">
                            <input id="curveGroupComb" class="mini-combobox" style="width:100%;"
                                   dataField="list" totalField="totals" valueField="boxkey" textField="boxval"
                                   required="true" allowInput="true" />
                        </div>
                    </div>
                    <div class="form-row">
                        <span class="form-label"><font color="red">*</font><span id="lblSort">排序</span>：</span>
                        <div class="form-control">
                            <input id="curveConfigSort" class="mini-spinner" style="width:100%;" minValue="1" value="1" required="true" />
                        </div>
                    </div>
                    <div class="form-row">
                        <span class="form-label"><font color="red">*</font><span id="lblLineWidth">线宽</span>：</span>
                        <div class="form-control">
                            <input id="curveConfigLineWidth" class="mini-spinner" style="width:100%;" minValue="1" value="3" required="true" />
                        </div>
                    </div>
                    <div class="form-row">
                        <span class="form-label"><span id="lblDashStyle">线型</span>：</span>
                        <div class="form-control">
                            <input id="curveConfigDashStyle" class="mini-combobox" style="width:100%;"
                                   valueField="value" textField="text"
                                   data='[{value:"Solid",text:"Solid"},{value:"ShortDash",text:"ShortDash"},{value:"ShortDot",text:"ShortDot"},{value:"ShortDashDot",text:"ShortDashDot"},{value:"ShortDashDotDot",text:"ShortDashDotDot"},{value:"Dot",text:"Dot"},{value:"Dash",text:"Dash"},{value:"LongDash",text:"LongDash"},{value:"DashDot",text:"DashDot"},{value:"LongDashDot",text:"LongDashDot"},{value:"LongDashDotDot",text:"LongDashDotDot"}]'
                                   value="Solid" allowInput="false" />
                        </div>
                    </div>
                    <div class="form-row">
                        <span class="form-label"><span id="lblYAxis">Y轴位置</span>：</span>
                        <div class="form-control">
                            <input id="curveConfigYAxisOpposite" class="mini-combobox" style="width:100%;"
                                   valueField="value" textField="text"
                                   data='[{value:false,text:"左侧"},{value:true,text:"右侧"}]'
                                   value="false" allowInput="false" />
                        </div>
                    </div>
                    <div class="form-row">
                        <span class="form-label"><font color="red">*</font><span id="lblColor">颜色</span>：</span>
                        <div class="form-control">
                            <div style="display:flex;align-items:center;">
                                <input id="curveConfigColor" class="mini-buttonedit" style="width:160px;"
                                       onbuttonclick="onColorButtonClick" required="true" />
                                <span id="colorPreview" class="color-preview" style="background-color:#ff0000;" onclick="onColorButtonClick()"></span>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 固定在底部的按钮行 -->
                <div class="btn-row">
                    <button id="btnSaveProp" class="mini-button" onclick="onSaveCurveConfig()">保存</button>
                    <button id="btnCancelProp" class="mini-button" onclick="onCancelCurveConfig()">取消</button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        var context = '<%=path%>';
        var lang = _loginUserLanguageResource;
        var currentColor = 'ff0000';
        var _curveType = 1;
        var curveConfig={};

        function getGridColumns() {
            return [
                { type: "indexcolumn", width: 50, headerAlign: "center", header: _loginUserLanguageResource.idx },
                {
                    field: "name",
                    width: '50%',
                    headerAlign: "center",
                    align: "center",
                    header: _loginUserLanguageResource.groupName,
                    editor: { type: "textbox", required: true }
                },
                {
                    field: "sort",
                    width: '50%',
                    headerAlign: "center",
                    align: "center",
                    header: _loginUserLanguageResource.sort,
                    editor: { type: "spinner", minValue: 1, required: true }
                }
            ];
        }

        function onTypeRenderer(e) {
            if (e.value == 1) return _loginUserLanguageResource.realtimeMonitoring;
            if (e.value == 2) return _loginUserLanguageResource.historyQuery;
            return '';
        }

        function onCellValidation(e) {
            if (e.field == 'name') {
                var grid = e.sender;
                var rows = grid.getData();
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].groupId != e.record.groupId && rows[i].name == e.value) {
                        e.isValid = false;
                        e.errorText = _loginUserLanguageResource.curveGroup + '已存在，请重新输入';
                        return;
                    }
                }
            }
        }

        function onRealtimeGridLoad(e) { e.sender.setColumns(getGridColumns()); }
        function onHistoryGridLoad(e) { e.sender.setColumns(getGridColumns()); }

        function onRealtimeGridBeforeLoad(e) { e.params = e.params || {}; e.params.type = 1; }
        function onHistoryGridBeforeLoad(e) { e.params = e.params || {}; e.params.type = 2; }

        function onCurveGroupBeforeLoad(e) {
            e.params = e.params || {};
            e.params.type = _curveType;
        }
        function onCurveGroupShowPopup(e) {
            //var comb = e.sender;
            //comb.load(comb.url, { type: _curveType });
        }

        function setData(data) {
            if (!data) return;
            mini.get('curveConfigRow').setValue(data.row);
            mini.get('curveConfigCol').setValue(data.col);
            mini.get('curveConfigTableType').setValue(data.tableType || 0);
            mini.get('curveConfigCurveType').setValue(data.curveType);
            _curveType = data.curveType || 1;

            curveConfig = data.config || {};
            loadCurveGroupList();
            
            mini.get('curveConfigSort').setValue(curveConfig.sort || 1);
            mini.get('curveConfigLineWidth').setValue(curveConfig.lineWidth || 3);
            mini.get('curveConfigDashStyle').setValue(curveConfig.dashStyle || 'Solid');
            mini.get('curveConfigYAxisOpposite').setValue(curveConfig.yAxisOpposite || false);
            currentColor = curveConfig.color || 'ff0000';
            var colorBtn = mini.get('curveConfigColor');
            colorBtn.setValue(currentColor);
            colorBtn.setText(currentColor);
            document.getElementById('colorPreview').style.backgroundColor = '#' + currentColor;

            mini.get('realtimeCurveGroupGrid').load();
            mini.get('historyCurveGroupGrid').load();
        }
        
        function loadCurveGroupList() {
            var combo = mini.get('curveGroupComb');
            if (!combo) return;
            // 加载协议下拉列表
            $.ajax({
                url: context + '/acquisitionUnitManagerController/getCurveGroupCombList',
                type: 'POST',
                data: {
                    type: _curveType
                },
                dataType: 'json',
                success: function(result) {
                    var list = result.list || [];
                    combo.setData(list);
                    if (curveConfig.groupId != undefined && curveConfig.groupId > 0) {
                    	combo.setValue(curveConfig.groupId);
                    	combo.setText(curveConfig.groupName);
                    } else {
                    	combo.setValue(-1);
                    	combo.setText(_loginUserLanguageResource.nothing);
                    }
                },
                error: function() {
                }
            });
        }

        function saveCurveGroup() {
            var realGrid = mini.get('realtimeCurveGroupGrid');
            var histGrid = mini.get('historyCurveGroupGrid');
            realGrid.validate();
            histGrid.validate();
            if (!realGrid.isValid() || !histGrid.isValid()) {
                mini.alert(_loginUserLanguageResource.pleaseCompleteForm);
                return;
            }

            var modified = [];
            var data1 = realGrid.getData();
            for (var i = 0; i < data1.length; i++) {
                if (data1[i]._state == 'modified' || data1[i]._state == 'added') {
                    modified.push({ id: data1[i].groupId, name: data1[i].name, sort: data1[i].sort, type: 1 });
                }
            }
            var data2 = histGrid.getData();
            for (var i = 0; i < data2.length; i++) {
                if (data2[i]._state == 'modified' || data2[i]._state == 'added') {
                    modified.push({ id: data2[i].groupId, name: data2[i].name, sort: data2[i].sort, type: 2 });
                }
            }
            if (modified.length === 0) {
                mini.alert('无数据变更');
                return;
            }

            $.ajax({
                url: '<%=path%>/acquisitionUnitManagerController/saveCurveGroupData',
                type: 'POST',
                data: { data: JSON.stringify(modified) },
                dataType: 'json',
                success: function(res) {
                    if (res.success) {
                        mini.alert('保存成功');
                        realGrid.reload();
                        histGrid.reload();
                        mini.get('curveGroupComb').load(context + '/acquisitionUnitManagerController/getCurveGroupCombList');
                    } else {
                        mini.alert('保存失败');
                    }
                },
                error: function() { mini.alert('请求失败'); }
            });
        }

        function onColorButtonClick() {
            var win = new mini.Window();
            win.set({
                id:'colorPickerWindow',
                title: _loginUserLanguageResource.selectColor,
                width: 420,
                height: 340,
                modal: true,
                showHeader: true,
                allowResize: false,
                showFooter: false,
                ondestroy: function() { try { $('#colorPickerSpectrum').spectrum('destroy'); } catch(e) {} }
            });
            win.setBody(
                '<div style="padding:20px;text-align:center;">' +
                    '<input type="text" id="colorPickerSpectrum" style="width:300px;" />' +
                    '<div style="margin-top:15px;">' +
                        '<button class="mini-button" onclick="onColorConfirm()" style="width:80px;">' + _loginUserLanguageResource.confirm + '</button>' +
                        '<button class="mini-button" onclick="onColorCancel()" style="width:80px;margin-left:10px;">' + _loginUserLanguageResource.cancel + '</button>' +
                    '</div>' +
                '</div>'
            );
            win.show();
            mini.parse(win.getBodyEl());

            var picker = document.getElementById('colorPickerSpectrum');
            $(picker).spectrum({
                color: '#' + currentColor,
                showAlpha: true,
                showInput: true,
                showInitial: true,
                showPalette: true,
                showButtons: true,
                cancelText: _loginUserLanguageResource.cancel,
                chooseText: _loginUserLanguageResource.confirm,
                clickoutFiresChange: false,
                appendTo: 'body',
                preferredFormat: 'hex',
                palette: [
                    ['#000','#444','#666','#999','#ccc','#eee','#f3f3f3','#fff'],
                    ['#f00','#f90','#ff0','#0f0','#0ff','#00f','#90f','#f0f'],
                    ['#f4cccc','#fce5cd','#fff2cc','#d9ead3','#d0e0e3','#cfe2f3','#d9d2e9','#ead1dc'],
                    ['#ea9999','#f9cb9c','#ffe599','#b6d7a8','#a2c4c9','#9fc5e8','#b4a7d6','#d5a6bd'],
                    ['#e06666','#f6b26b','#ffd966','#93c47d','#76a5af','#6fa8dc','#8e7cc3','#c27ba0'],
                    ['#c00','#e69138','#f1c232','#6aa84f','#45818e','#3d85c6','#674ea7','#a64d79'],
                    ['#900','#b45f06','#bf9000','#38761d','#134f5c','#0b5394','#351c75','#741b47'],
                    ['#600','#783f04','#7f6000','#274e13','#0c343d','#073763','#20124d','#4c1130']
                ],
                change: function(color) {
                    if (color) {
                        var hex = color.toHexString().replace('#', '');
                        currentColor = hex;
                        var colorBtn = mini.get('curveConfigColor');
                        colorBtn.setValue(hex);
                        colorBtn.setText(hex);
                        document.getElementById('colorPreview').style.backgroundColor = '#' + hex;
                    }
                }
            });
            $(picker).spectrum('show');
        }

        function onColorConfirm() {
            var win = mini.get('colorPickerWindow');
            if (win) win.destroy();
        }

        function onColorCancel() {
            var win = mini.get('colorPickerWindow');
            if (win) win.destroy();
        }

        function onSaveCurveConfig() {
            var groupId = mini.get('curveGroupComb').getValue();
            var groupName = mini.get('curveGroupComb').getText();
            var sort = mini.get('curveConfigSort').getValue();
            var lineWidth = mini.get('curveConfigLineWidth').getValue();
            var dashStyle = mini.get('curveConfigDashStyle').getValue();
            var yAxisOpposite = mini.get('curveConfigYAxisOpposite').getValue();
            var color = currentColor;

            var config = {
                groupId: groupId,
                groupName: groupName,
                sort: parseInt(sort),
                lineWidth: parseInt(lineWidth),
                dashStyle: dashStyle,
                yAxisOpposite: yAxisOpposite,
                color: color
            };

            var row = mini.get('curveConfigRow').getValue();
            var col = mini.get('curveConfigCol').getValue();
            var tableType = mini.get('curveConfigTableType').getValue();

            if (window._updateCurveConfig) {
                window._updateCurveConfig(row, col, tableType, config);
            }
            CloseWindow('ok');
        }

        function onCancelCurveConfig() { CloseWindow('cancel'); }
        function CloseWindow(action) {
            if (window.CloseOwnerWindow) window.CloseOwnerWindow(action);
            else window.close();
        }
        
        $(document).ready(function() {
            mini.parse();
            
            var tabs = mini.get('curveConfigTabs');
            tabs.updateTab(tabs.getTab(0), { title: _loginUserLanguageResource.curveGroup });
            tabs.updateTab(tabs.getTab(1), { title: _loginUserLanguageResource.curveProperty });

            document.getElementById('realtimeTitle').innerText = _loginUserLanguageResource.realtimeMonitoring;
            document.getElementById('historyTitle').innerText = _loginUserLanguageResource.historyQuery;

            document.getElementById('lblCurveGroup').innerText = _loginUserLanguageResource.curveGroup;
            document.getElementById('lblSort').innerText = _loginUserLanguageResource.curveSort;
            document.getElementById('lblLineWidth').innerText = _loginUserLanguageResource.lineWidth;
            document.getElementById('lblDashStyle').innerText = _loginUserLanguageResource.lineDash;
            document.getElementById('lblYAxis').innerText = _loginUserLanguageResource.yAxisPosition;
            document.getElementById('lblColor').innerText = _loginUserLanguageResource.curveColor;

            var btnSaveGroup = mini.get('btnSaveGroup');
            if (btnSaveGroup) btnSaveGroup.setText(_loginUserLanguageResource.save);

            var btnSaveProp = mini.get('btnSaveProp');
            if (btnSaveProp) btnSaveProp.setText(_loginUserLanguageResource.save);

            var btnCancelProp = mini.get('btnCancelProp');
            if (btnCancelProp) btnCancelProp.setText(_loginUserLanguageResource.cancel);

            mini.get('curveConfigYAxisOpposite').setData([
                {value: false, text: _loginUserLanguageResource.left},
                {value: true, text: _loginUserLanguageResource.right}
            ]);
        });
    </script>
</body>
</html>