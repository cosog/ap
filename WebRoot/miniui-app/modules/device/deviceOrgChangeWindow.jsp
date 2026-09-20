<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        html, body { margin:0; padding:0; width:100%; height:100%; overflow:hidden; background:#fff; }
        /* 统一规范：让 mini-panel 的 body 撑满，使内部组件能拿到 100% 高度 */
        .mini-panel-body { padding:0 !important; overflow:hidden; }
        .mini-splitter-border { border:0 !important; }
        .mini-splitter-pane { padding:0 !important; border:0 !important; }
        .mini-splitter-handler { background:transparent !important; border:1px solid #e8e8e8 !important; }
    </style>
</head>
<body>

<!-- 全局变量接收参数 -->
<script>
    var orgId = '';
    var orgName = '';
    var deviceType = '';
    var dictDeviceType = '';
</script>

<!-- 整体 Splitter：左 75% 设备列表，右 25% 目标组织 / 目标设备类型 -->
<div class="mini-splitter" style="width:100%;height:100%;" vertical="false">

    <!-- ============ 左：设备列表 ============ -->
    <div size="75%" showCollapseButton="false" minSize="400">
        <div id="deviceListPanel" class="mini-panel" style="width:100%;height:100%;"
             showHeader="true" showToolbar="true" showCollapseButton="false" showCloseButton="false"
             bodyStyle="padding:0;overflow:hidden;">

            <!-- 工具条：设备名称下拉 -->
            <div property="toolbar" style="padding:4px 8px;background:#fafafa;border-bottom:1px solid #e8e8e8;">
                <span id="lblDeviceName" style="margin-right:4px;"></span>
                <input id="deviceNameCombo" class="mini-combobox" style="width:220px;"
                       valueField="boxkey" textField="boxval" allowInput="true"
                       dataField="list"
                       onbeforeload="onDeviceNameComboBeforeLoad"
                       onvaluechanged="onDeviceNameChange" />
            </div>

            <!-- 设备列表 -->
            <div id="deviceListGrid" class="mini-datagrid" style="width:100%;height:100%;"
                 idField="id"
                 multiSelect="true"
                 showPager="false"
                 pageSize="100"
                 allowAlternate="true"
                 showEmptyText="true"
                 dataField="totalRoot"
                 totalField="totalCount"
                 onbeforeload="onDeviceGridBeforeLoad">
                <div property="columns"></div>
            </div>
        </div>
    </div>

    <!-- ============ 右：目标组织 + 目标设备类型 ============ -->
    <div size="25%" showCollapseButton="true" collapseDirection="right" minSize="250">
        <div class="mini-splitter" style="width:100%;height:100%;" vertical="true">

            <!-- 上：目标组织 -->
            <div size="50%" showCollapseButton="false" minSize="150">
                <div id="targetOrgPanel" class="mini-panel" style="width:100%;height:100%;"
                     showHeader="true" showToolbar="true" showCollapseButton="false" showCloseButton="false"
                     bodyStyle="padding:0;overflow:hidden;">
                    <div property="toolbar" style="padding:4px 8px;background:#fafafa;border-bottom:1px solid #e8e8e8;">
                        <div style="text-align:right;">
                            <button id="btnChangeOrg" class="mini-button" iconCls="move" onclick="onChangeOrg()"></button>
                        </div>
                    </div>
                    <div id="orgTree" class="mini-tree" style="width:100%;height:100%;"
                         showTreeIcon="true"
                         textField="text"
                         idField="orgId"
                         parentField="orgParent"
                         dataField="children"
                         resultAsTree="true"
                         expandOnLoad="true"
                         showCheckBox="false"
                         autoLoad="false"
                         onbeforeload="onOrgTreeBeforeLoad">
                    </div>
                </div>
            </div>

            <!-- 下：目标设备类型 -->
            <div size="50%" showCollapseButton="true" collapseDirection="bottom" minSize="150">
                <div id="deviceTypePanel" class="mini-panel" style="width:100%;height:100%;"
                     showHeader="true" showToolbar="true" showCollapseButton="false" showCloseButton="false"
                     bodyStyle="padding:0;overflow:hidden;">
                    <div property="toolbar" style="padding:4px 8px;background:#fafafa;border-bottom:1px solid #e8e8e8;">
                        <div style="text-align:right;">
                            <button id="btnChangeType" class="mini-button" iconCls="move" onclick="onChangeType()"></button>
                        </div>
                    </div>
                    <div id="deviceTypeTree" class="mini-tree" style="width:100%;height:100%;"
                         showTreeIcon="true"
                         textField="text"
                         idField="deviceTypeId"
                         parentField="parentId"
                         resultAsTree="true"
                         expandOnLoad="true"
                         autoLoad="false"
                         showCheckBox="false">
                    </div>
                </div>
            </div>

        </div>
    </div>

</div>

<script>
    var context = '<%=context%>';

    // ================================================================
    // 初始化（国际化 + 控件初始化）
    // ================================================================
    function initI18n() {
        var R = _loginUserLanguageResource;
        document.title = R.deviceOrgChange;

        // 顶部文字
        document.getElementById('lblDeviceName').textContent = R.deviceName + '：';

        // 按钮文字
        mini.get('btnChangeOrg').setText(R.changeOwner);
        mini.get('btnChangeType').setText(R.changeOwner);

        // 面板标题
        mini.get('deviceListPanel').setTitle(R.deviceList);
        mini.get('targetOrgPanel').setTitle(R.targetOrg);
        mini.get('deviceTypePanel').setTitle(R.deviceType);

        // 下拉空提示
        mini.get('deviceNameCombo').setEmptyText('--' + R.all + '--');

        // 设备列表列
        var grid = mini.get('deviceListGrid');
        grid.set({
            columns: [
                { type: 'indexcolumn', header: R.idx, width: 50, headerAlign: 'center', align: 'center' },
                { field: 'deviceName',     header: R.name,             width: '30%', headerAlign: 'center', align: 'left' },
                { field: 'orgName',        header: R.orgName,          width: '40%', headerAlign: 'center', align: 'left' },
                { field: 'deviceTypeName', header: R.owningDeviceType, width: '30%', headerAlign: 'center', align: 'left' }
            ]
        });
        grid.setEmptyText(R.emptyMsg);

        // 树空提示
        mini.get('orgTree').setEmptyText(R.emptyMsg);
        mini.get('deviceTypeTree').setEmptyText(R.emptyMsg);
    }

    // ================================================================
    // 父窗口调用：接收参数并加载各列表
    // ================================================================
    function setData(data) {
        if (!data) data = {};

        orgId             = data.orgId || '';
        orgName           = data.orgName || '';
        deviceType        = data.deviceType || '';
        dictDeviceType    = data.dictDeviceType || deviceType;

        // 加载设备名称下拉（会触发 onDeviceNameComboBeforeLoad）
        var combo = mini.get('deviceNameCombo');
        combo.load(context + '/wellInformationManagerController/loadWellComboxList');

        // 加载设备列表（会触发 onDeviceGridBeforeLoad）
        loadDeviceList();

        // 加载组织树（会触发 onOrgTreeBeforeLoad）
        loadOrgTree();

        // 加载设备类型树（无参）
        loadDeviceTypeTree();

        // 让 MiniUI 重新布局
        mini.layout();
    }

    // ================================================================
    // ★ 设备名称下拉 - 传参
    // ================================================================
    function onDeviceNameComboBeforeLoad(e) {
    	var params = e.params || {};
        var pageIndex = params.pageIndex || 0;
        var pageSize = params.pageSize || (typeof defaultWellComboxSize !== 'undefined' ? defaultWellComboxSize : 50);
        params.start = pageIndex * pageSize;
        params.limit = pageSize;
        
        params.orgId      = orgId;
        params.deviceType = deviceType;
        params.deviceName = mini.get('deviceNameCombo').getValue() || '';
        e.params = params;
    }

    function onDeviceNameChange(e) {
        // 切换设备名称 → 重新加载设备列表
        loadDeviceList();
    }

    // ================================================================
    // ★ 设备列表 - 传参
    // ================================================================
    function loadDeviceList() {
        var grid = mini.get('deviceListGrid');
        grid.setUrl(context + '/wellInformationManagerController/getDeviceOrgChangeDeviceList');
        grid.load();
    }

    function onDeviceGridBeforeLoad(e) {
        var params = e.params || {};
        params.orgId      = orgId;
        params.deviceType = deviceType;
        params.deviceName = mini.get('deviceNameCombo').getValue() || '';
        e.params = params;
    }

    // ================================================================
    // ★ 组织树 - 传参
    // ================================================================
    function loadOrgTree() {
        var tree = mini.get('orgTree');
        tree.setUrl(context + '/orgManagerController/constructOrgTreeGridTree');
        tree.load();
    }

    function onOrgTreeBeforeLoad(e) {
        var params = e.params || {};
        //params.orgId = orgId;
        e.params = params;
    }

    // ================================================================
    // ★ 设备类型树 - 无参
    // ================================================================
    function loadDeviceTypeTree() {
        var tree = mini.get('deviceTypeTree');
        tree.setUrl(context + '/roleManagerController/constructProtocolConfigTabTreeGridTreeWithoutRoot');
        tree.load();
    }

    // ================================================================
    // 迁移到目标组织
    // ================================================================
    function onChangeOrg() {
        var grid = mini.get('deviceListGrid');
        var selectedDevices = grid.getSelecteds();
        if (!selectedDevices || selectedDevices.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
            return;
        }

        var tree = mini.get('orgTree');
        var selectedOrgNode = tree.getSelected();
        if (!selectedOrgNode) {
            mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
            return;
        }

        var selectedOrgId   = selectedOrgNode.orgId;
        var selectedOrgName = selectedOrgNode.text;

        var deviceIds = [];
        for (var i = 0; i < selectedDevices.length; i++) {
            deviceIds.push(selectedDevices[i].id);
        }

        var maskEl = 'deviceListPanel';
        mini.mask({ el: maskEl, cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });

        $.ajax({
            url: context + '/wellInformationManagerController/changeDeviceOrg',
            type: 'POST',
            data: {
                selectedDeviceId: deviceIds.join(','),
                selectedOrgId:    selectedOrgId,
                selectedOrgName:  selectedOrgName,
                deviceType:       deviceType
            },
            dataType: 'json',
            success: function (result) {
                mini.unmask(maskEl);
                if (result.success === true) {
                    mini.alert(_loginUserLanguageResource.migrationSuccessful, _loginUserLanguageResource.tip, function () {
                        loadDeviceList();
                        if (window._parentRefreshDeviceList) {
                            window._parentRefreshDeviceList(true);
                        }
                    });
                } else {
                    mini.alert('<font color=red>' + _loginUserLanguageResource.migrationFailed + '</font>', _loginUserLanguageResource.tip);
                }
            },
            error: function () {
                mini.unmask(maskEl);
                mini.alert('【<font color=red>' + _loginUserLanguageResource.exceptionThrow + '</font>】' + _loginUserLanguageResource.contactAdmin, _loginUserLanguageResource.tip);
            }
        });
    }

    // ================================================================
    // 迁移到目标设备类型
    // ================================================================
    function onChangeType() {
        var grid = mini.get('deviceListGrid');
        var selectedDevices = grid.getSelecteds();
        if (!selectedDevices || selectedDevices.length === 0) {
            mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
            return;
        }

        var tree = mini.get('deviceTypeTree');
        var selectedTypeNode = tree.getSelected();
        if (!selectedTypeNode) {
            mini.alert(_loginUserLanguageResource.checkOne, _loginUserLanguageResource.tip);
            return;
        }

        // 判断是否叶子节点
        var isLeaf = true;
        if (selectedTypeNode.children && selectedTypeNode.children.length > 0) {
            isLeaf = false;
        }
        if (selectedTypeNode.isLeaf === false) {
            isLeaf = false;
        }
        if (!isLeaf) {
            mini.alert(_loginUserLanguageResource.selectLeafNode, _loginUserLanguageResource.tip);
            return;
        }

        var selectedDeviceTypeId   = selectedTypeNode.deviceTypeId;
        var selectedDeviceTypeName = selectedTypeNode.text;

        var deviceIds = [];
        for (var i = 0; i < selectedDevices.length; i++) {
            deviceIds.push(selectedDevices[i].id);
        }

        var maskEl = 'deviceListPanel';
        mini.mask({ el: maskEl, cls: 'mini-mask-loading', html: _loginUserLanguageResource.submittingData });

        $.ajax({
            url: context + '/wellInformationManagerController/changeDeviceType',
            type: 'POST',
            data: {
                selectedDeviceId:       deviceIds.join(','),
                selectedDeviceTypeId:   selectedDeviceTypeId,
                selectedDeviceTypeName: selectedDeviceTypeName,
                deviceType:             deviceType
            },
            dataType: 'json',
            success: function (result) {
                mini.unmask(maskEl);
                if (result.success === true) {
                    mini.alert(_loginUserLanguageResource.migrationSuccessful, _loginUserLanguageResource.tip, function () {
                        loadDeviceList();
                        if (window._parentRefreshDeviceList) {
                            window._parentRefreshDeviceList(true);
                        }
                    });
                } else {
                    mini.alert('<font color=red>' + _loginUserLanguageResource.migrationFailed + '</font>', _loginUserLanguageResource.tip);
                }
            },
            error: function () {
                mini.unmask(maskEl);
                mini.alert('【<font color=red>' + _loginUserLanguageResource.exceptionThrow + '</font>】' + _loginUserLanguageResource.contactAdmin, _loginUserLanguageResource.tip);
            }
        });
    }

    $(document).ready(function () {
        mini.parse();
        initI18n();
    });
</script>
</body>
</html>