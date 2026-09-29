<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>设备控制</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body {
            margin: 0; padding: 0; width: 100%; height: 100%;
            overflow: hidden;
            font-family: "Microsoft YaHei", Arial, sans-serif;
            background: #fff;
        }
        .control-wrapper {
            width: 100%; height: 100%;
            display: flex; flex-direction: column;
        }
        .control-toolbar {
            flex-shrink: 0;
            padding: 4px 8px;
            background: #f5f5f5;
            border-bottom: 1px solid #ddd;
            display: flex; align-items: center; gap: 6px;
        }
        .control-toolbar .label {
            font-size: 12px; color: #333;
        }
        .control-body {
            flex: 1;
            overflow: hidden;
            min-height: 0;
            background: #fff;
        }
        .control-body .mini-datagrid {
            width: 100%; height: 100%;
        }
    </style>
</head>
<body>

    <div class="control-wrapper">
        <div class="control-toolbar">
            <span class="label" id="controlItemLabel"></span>
            <span style="flex:1;"></span>
            <button id="uplinkBtn" class="mini-button" plain="true" onclick="performUplink()"></button>
            <button id="downlinkBtn" class="mini-button" plain="true" onclick="performGlobalDownlink()"></button>
        </div>
        <div class="control-body">
            <div id="deviceControlValueGrid" class="mini-datagrid"
                 style="width:100%;height:100%;"
                 showPager="false" showColumns="true"
                 allowCellSelect="true" allowCellEdit="true"
                 allowCellWrap="false" allowResize="true"
                 virtualScroll="false" allowAlternating="true"
                 url="<%=path%>/realTimeMonitoringController/getDeviceControlValueList"
                 dataField="totalRoot" totalField="totalCount"
                 onbeforeload="onControlGridBeforeLoad"
                 onload="onControlGridLoad"
                 oncellvalidation="onControlCellValidation">
                <div property="columns">
                    <div type="indexcolumn" width="50" headerAlign="center" align="center"></div>
                    <div field="uplinkStatus" name="uplinkStatus"
                         width="200" headerAlign="center" align="center"></div>
                    <div field="uplink" name="uplink"
                         width="120" headerAlign="center" align="center"
                         renderer="onUplinkColumnRenderer"></div>
                    <div field="value" name="value"
                         width="200" headerAlign="center" align="center"
                         editor="{type:'textbox'}"></div>
                    <div field="downlink" name="downlink"
                         width="120" headerAlign="center" align="center"
                         renderer="onDownlinkColumnRenderer"></div>
                </div>
            </div>
        </div>
    </div>

    <script>
        var context = '<%=path%>';
        var _params = null;

        // ================================================================
        // 1. 接收父页面参数
        // ================================================================
        function setData(params) {
            _params = params || {};
            initI18n();
            // 触发加载（会走 onControlGridBeforeLoad）
            var grid = mini.get('deviceControlValueGrid');
            if (grid) grid.load();
        }

        // ================================================================
        // 2. 国际化
        // ================================================================
        function initI18n() {
            var R = _loginUserLanguageResource;

            var itemName = _params.itemName || '';
            var unit = _params.unit ? (' (' + _params.unit + ')') : '';
            document.getElementById('controlItemLabel').textContent = itemName + unit;

            mini.get('uplinkBtn').setText(R.uplink);
            mini.get('downlinkBtn').setText(R.downlink);

            var grid = mini.get('deviceControlValueGrid');
            if (grid) {
                grid.updateColumn('uplinkStatus', { header: R.uplinkValue });
                grid.updateColumn('uplink',       { header: R.uplink });
                grid.updateColumn('value',        { header: R.downlinkValue });
                grid.updateColumn('downlink',     { header: R.downlink });
            }
        }

        // ================================================================
        // 3. 加载前事件：传参
        // ================================================================
        function onControlGridBeforeLoad(e) {
            var params = e.params || {};
            params.deviceId    = _params.deviceId;
            params.deviceName  = _params.deviceName;
            params.deviceType  = _params.deviceType;
            params.controlType = _params.controlType;
            e.params = params;
        }

        // ================================================================
        // 4. 加载后事件：根据行数控制列/按钮显隐
        // ================================================================
        function onControlGridLoad(e) {
            var grid = e.sender;
            var result = e.result || {};
            var data = result.totalRoot || [];
            var isSingleRow = (data.length === 1);

            // 列显隐：单行时显示"上行/下行"按钮列；多行时隐藏（走工具栏批量操作）
            grid.updateColumn('uplink',   { visible: isSingleRow });
            grid.updateColumn('downlink', { visible: isSingleRow });

            // 工具栏按钮显隐
            var uplinkBtn = mini.get('uplinkBtn');
            var downlinkBtn = mini.get('downlinkBtn');
            if (isSingleRow) {
                if (uplinkBtn) uplinkBtn.hide();
                if (downlinkBtn) downlinkBtn.hide();
            } else {
                if (uplinkBtn) uplinkBtn.show();
                if (downlinkBtn) downlinkBtn.show();
            }
        }

        // ================================================================
        // 5. 单元格校验
        // ================================================================
        function onControlCellValidation(e) {
            if (e.column.field === 'value') {
                var storeDataType = _params.storeDataType || '';
                if (storeDataType.toUpperCase() !== 'BCD'
                    && storeDataType.toUpperCase() !== 'STRING') {
                    if (e.value !== null && e.value !== undefined && e.value !== ''
                        && isNaN(e.value)) {
                        e.isValid = false;
                        e.errorText = _loginUserLanguageResource.dataFormattingError;
                    }
                }
            }
        }

        // ================================================================
        // 6. 列渲染器
        // ================================================================
        function onUplinkColumnRenderer(e) {
            return '<button style="height:24px; line-height:24px; padding:0 14px; font-size:12px; font-weight:500; cursor:pointer; border:none; border-radius:20px; background:#409eff; color:#fff; box-shadow:0 1px 2px rgba(0,0,0,0.1); white-space:nowrap;" '
                + 'onmouseover="this.style.background=\'#66b1ff\'" '
                + 'onmouseout="this.style.background=\'#409eff\'" '
                + 'onclick="performUplink()">'
                + _loginUserLanguageResource.uplink + '</button>';
        }

        function onDownlinkColumnRenderer(e) {
            var rowIndex = e.rowIndex;
            return '<button style="height:24px; line-height:24px; padding:0 14px; font-size:12px; font-weight:500; cursor:pointer; border:none; border-radius:20px; background:#67c23a; color:#fff; box-shadow:0 1px 2px rgba(0,0,0,0.1); white-space:nowrap;" '
                + 'onmouseover="this.style.background=\'#85ce61\'" '
                + 'onmouseout="this.style.background=\'#67c23a\'" '
                + 'onclick="performRowDownlink(' + rowIndex + ')">'
                + _loginUserLanguageResource.downlink + '</button>';
        }

        // ================================================================
        // 7. 上行
        // ================================================================
        function performUplink() {
            var grid = mini.get('deviceControlValueGrid');
            if (!grid) return;
            var data = grid.getData();
            if (data.length === 0) return;

            mini.mask({
                el: document.body,
                cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.commandSending + '...'
            });

            $.ajax({
                url: context + '/wellInformationManagerController/deviceDataUplink',
                type: 'POST',
                data: {
                    deviceId: _params.deviceId,
                    deviceName: _params.deviceName,
                    controlType: _params.controlType
                },
                dataType: 'json',
                timeout: 10000,
                success: function (result) {
                    mini.unmask(document.body);
                    if (result.flag == false) {
                        mini.alert({
                            title: _loginUserLanguageResource.tip,
                            message: '<font color="red">'
                                + _loginUserLanguageResource.sessionExpired + '</font>',
                            callback: function () {
                                window.top.location.href = context + "/login";
                            }
                        });
                        return;
                    }
                    if (result.flag == true && result.error == false) {
                        mini.alert(result.msg);
                    } else if (result.flag == true && result.error == true) {
                        var uplinkData = result.data ? result.data.split(',') : [];
                        for (var i = 0; i < Math.min(data.length, uplinkData.length); i++) {
                            grid.updateRow(grid.getAt(i), { uplinkStatus: uplinkData[i] });
                        }
                        grid.accept();
                    }
                },
                error: function () {
                    mini.unmask(document.body);
                    grid.accept();
                    mini.alert(_loginUserLanguageResource.requestFailed);
                }
            });
        }

        // ================================================================
        // 8. 单行下行
        // ================================================================
        function performRowDownlink(rowIndex) {
            var grid = mini.get('deviceControlValueGrid');
            if (!grid) return;
            var record = grid.getAt(rowIndex);
            if (!record) return;

            var value = record.value;
            var storeDataType = _params.storeDataType || '';

            if (storeDataType.toUpperCase() !== 'BCD'
                && storeDataType.toUpperCase() !== 'STRING') {
                if (value !== null && value !== undefined && value !== ''
                    && isNaN(value)) {
                    mini.alert(_loginUserLanguageResource.dataFormattingError);
                    return;
                }
            }

            var deviceNameShow = _params.deviceName || '';
            var unitShow = _params.unit ? (' (' + _params.unit + ')') : '';
            var tipInfo = _loginUserLanguageResource.deviceName
                + ":<font color=red>" + deviceNameShow + "</font>";
            tipInfo += "</br>" + (_params.itemName || '') + unitShow
                + ":<font color=red>" + value + "</font>";
            tipInfo += "</br>" + (_loginUserLanguageResource.confirmOperation);

            mini.confirm(tipInfo, _loginUserLanguageResource.tip, function (action) {
                if (action == 'ok') {
                    sendBatchControl([value]);
                }
            });
        }

        // ================================================================
        // 9. 全局下行
        // ================================================================
        function performGlobalDownlink() {
            var grid = mini.get('deviceControlValueGrid');
            if (!grid) return;
            var data = grid.getData();
            if (data.length === 0) return;

            var storeDataType = _params.storeDataType || '';
            var values = [];
            var isValid = true;
            for (var i = 0; i < data.length; i++) {
                var v = data[i].value;
                if (storeDataType.toUpperCase() !== 'BCD'
                    && storeDataType.toUpperCase() !== 'STRING') {
                    if (v !== null && v !== undefined && v !== '' && isNaN(v)) {
                        isValid = false;
                        break;
                    }
                }
                values.push(v !== undefined && v !== null ? v : '');
            }
            if (!isValid) {
                mini.alert(_loginUserLanguageResource.dataFormattingError);
                return;
            }

            var deviceNameShow = _params.deviceName || '';
            var unitShow = _params.unit ? (' (' + _params.unit + ')') : '';
            var valueStr = values.join(',');
            var tipInfo = _loginUserLanguageResource.deviceName
                + ":<font color=red>" + deviceNameShow + "</font>";
            tipInfo += "</br>" + (_params.itemName || '') + unitShow
                + ":<font color=red>" + valueStr + "</font>";
            tipInfo += "</br>" + (_loginUserLanguageResource.confirmOperation);

            mini.confirm(tipInfo, _loginUserLanguageResource.tip, function (action) {
                if (action == 'ok') {
                    sendBatchControl(values);
                }
            });
        }

        // ================================================================
        // 10. 发送批量控制指令
        // ================================================================
        function sendBatchControl(values) {
            var controlValue = values.join(',');
            mini.mask({
                el: document.body,
                cls: 'mini-mask-loading',
                html: _loginUserLanguageResource.commandSending + '...'
            });

            $.ajax({
                url: context + '/realTimeMonitoringController/deviceControlOperationWhitoutPass',
                type: 'POST',
                data: {
                    deviceId: _params.deviceId,
                    deviceName: _params.deviceName,
                    deviceType: _params.deviceType,
                    controlType: _params.controlType,
                    controlValue: controlValue,
                    storeDataType: _params.storeDataType,
                    quantity: _params.quantity
                },
                dataType: 'json',
                timeout: 10000,
                success: function (result) {
                    mini.unmask(document.body);
                    mini.alert(result.msg);
                },
                error: function () {
                    mini.unmask(document.body);
                    mini.alert(_loginUserLanguageResource.exceptionThrow);
                }
            });
        }

        // ================================================================
        // 11. 初始化
        // ================================================================
        $(document).ready(function () {
            mini.parse();
        });
    </script>
</body>
</html>