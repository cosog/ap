<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String context = path;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加采集组</title>
    <jsp:include page="../../layout/tags-miniui.jsp" flush="true" />
    <style>
        body { padding: 10px; background: #f5f5f5; }
        .mini-form { width: 100%; }
        .form-table { width: 100%; border-collapse: collapse; }
        .form-table td { padding: 6px 8px; }
        .label { text-align: right; width: 120px; font-weight: bold; }
        .mini-textbox, .mini-combobox, .mini-spinner, .mini-textarea { width: 100%; }
        .mini-textarea { min-height: 60px; }
        .tip-area { color: red; padding: 5px 0; display: none; }
        .hidden-field { display: none; }
    </style>
</head>
<body>
<div style="padding:10px;">
    <form id="groupForm" class="mini-form">
        <table class="form-table">
            <tr>
                <td class="label"><span style="color:red;">*</span>协议：</td>
                <td>
                    <input id="protocolComb" class="mini-combobox" 
                           style="width:100%;" 
                           textField="boxval" 
                           valueField="boxkey" 
                           required="true" 
                           allowInput="false" 
                           showNullItem="true"
                           nullItemText="-- 请选择协议 --" />
                </td>
            </tr>
            <tr>
                <td class="label"><span style="color:red;">*</span>采集单元：</td>
                <td>
                    <input id="unitComb" class="mini-combobox" 
                           style="width:100%;" 
                           textField="boxval" 
                           valueField="boxkey" 
                           required="true" 
                           allowInput="false" 
                           showNullItem="true"
                           nullItemText="-- 请选择采集单元 --" />
                </td>
            </tr>
            <tr>
                <td class="label"><span style="color:red;">*</span>组名称：</td>
                <td><input id="groupName" class="mini-textbox" required="true" vtype="string" style="width:100%;" onblur="checkGroupName()" /></td>
            </tr>
            <tr>
                <td class="label">组类型：</td>
                <td>
                    <input id="groupType" class="mini-textbox" readonly="readonly" style="width:100%;" />
                    <input id="groupTypeValue" class="mini-hidden" value="0" />
                </td>
            </tr>
            <tr id="timingIntervalRow">
                <td class="label">定时采集间隔(s)：</td>
                <td><input id="timingInterval" class="mini-textbox" style="width:100%;" /></td>
            </tr>
            <tr id="savingIntervalRow">
                <td class="label">存储间隔(s)：</td>
                <td><input id="savingInterval" class="mini-textbox" style="width:100%;" /></td>
            </tr>
            <tr>
                <td class="label">备注：</td>
                <td><input id="remark" class="mini-textarea" style="width:100%;height:60px;" /></td>
            </tr>
        </table>
        <div style="text-align:center;padding-top:20px;">
            <a class="mini-button" onclick="onSave()" style="width:80px;" id="btnSave">保存</a>
            <a class="mini-button" onclick="onCancel()" style="width:80px;margin-left:10px;" id="btnCancel">取消</a>
        </div>
    </form>
</div>

<script>
    var context = '<%=context%>';
    var deviceTypeIds = '';
    var protocolList = '';
    var unitList = '';
    var groupType = 0; // 0:采集组, 1:控制组
    var _groupNameValid = false;

    // 延迟初始化数据
    var _pendingData = null;

    function setData(data) {
        // 保存数据，等待 DOM 就绪后处理
        _pendingData = data;
        if (document.readyState === 'complete') {
            processSetData();
        } else {
            // 如果 DOM 未完全加载，等待 ready
            $(document).ready(function() {
                processSetData();
            });
        }
    }

    function processSetData() {
        if (!_pendingData) return;
        var data = _pendingData;
        _pendingData = null;

        deviceTypeIds = data.deviceTypeIds || '';
        protocolList = data.protocolList || '';
        unitList = data.unitList || '';
        groupType = data.type || 0;

        // 设置组类型显示
        var typeText = (groupType === 0) ? _loginUserLanguageResource.acqGroup : _loginUserLanguageResource.controlGroup;
        var groupTypeCmp = mini.get('groupType');
        if (groupTypeCmp) groupTypeCmp.setValue(typeText);
        var groupTypeValueCmp = mini.get('groupTypeValue');
        if (groupTypeValueCmp) groupTypeValueCmp.setValue(groupType);

        // 控制间隔字段显示/隐藏
        if (groupType === 1) {
            document.getElementById('timingIntervalRow').style.display = 'none';
            document.getElementById('savingIntervalRow').style.display = 'none';
        } else {
            document.getElementById('timingIntervalRow').style.display = '';
            document.getElementById('savingIntervalRow').style.display = '';
        }

        // 加载协议和单元下拉
        loadProtocolList();
        loadUnitList();

        // 国际化标题
        var title = (groupType === 0) ? _loginUserLanguageResource.addAcqGroup : _loginUserLanguageResource.addCtrlGroup;
        document.title = title;
    }

    function loadProtocolList() {
        var combo = mini.get('protocolComb');
        if (!combo) return;
        $.ajax({
            url: context + '/acquisitionUnitManagerController/getModbusProtoclCombList',
            type: 'POST',
            data: {
                deviceTypeIds: deviceTypeIds,
                protocol: protocolList
            },
            dataType: 'json',
            success: function(result) {
                var list = result.list || [];
                combo.setData(list);
                
            },
            error: function() {
                // 静默失败
            }
        });
    }

    function loadUnitList() {
        var combo = mini.get('unitComb');
        if (!combo) return;
        var protocol = mini.get('protocolComb').getValue();
        if (!protocol) {
            combo.setData([]);
            return;
        }
        $.ajax({
            url: context + '/acquisitionUnitManagerController/getAcquisitionUnitCombList',
            type: 'POST',
            data: {
                protocol: protocol,
                deviceTypeIds: deviceTypeIds,
                selectedProtocol: protocolList
            },
            dataType: 'json',
            success: function(result) {
                var list = result.list || [];
                combo.setData(list);
            },
            error: function() {
                // 静默失败
            }
        });
    }

    function checkGroupName() {
        var nameInput = mini.get('groupName');
        var name = nameInput.getValue();
        var unitId = mini.get('unitComb').getValue();
        if (!name || name.trim() === '' || !unitId) {
            _groupNameValid = false;
            return;
        }
        $.ajax({
            url: context + '/acquisitionUnitManagerController/judgeAcqGroupExistOrNot',
            type: 'POST',
            data: {
                unitId: unitId,
                groupName: name
            },
            dataType: 'json',
            success: function(resp) {
                if (resp.msg === 1) {
                    mini.confirm(
                        '<font color="red">' + (_loginUserLanguageResource.groupCollisionInfo) + '，' + (_loginUserLanguageResource.pleaseConfirm) + '</font>',
                        _loginUserLanguageResource.confirm,
                        function(action) {
                            if (action === 'ok') {
                                nameInput.focus();
                                nameInput.selectText();
                            }
                        }
                    );
                    _groupNameValid = false;
                } else {
                    _groupNameValid = true;
                }
            },
            error: function() {
                _groupNameValid = false;
            }
        });
    }

    function onSave() {
        var form = new mini.Form('#groupForm');
        form.validate();
        if (!form.isValid()) {
            mini.alert(_loginUserLanguageResource.required);
            return;
        }
        if (!_groupNameValid) {
            mini.get('groupName').focus();
            return;
        }

        var protocol = mini.get('protocolComb').getValue();
        var acqUnit = mini.get('unitComb').getValue();
        var groupName = mini.get('groupName').getValue();
        var type = mini.get('groupTypeValue').getValue();
        var timingInterval = mini.get('timingInterval').getValue() || '';
        var savingInterval = mini.get('savingInterval').getValue() || '';
        var remark = mini.get('remark').getValue() || '';
        var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.submittingData });
        $.ajax({
            url: context + '/acquisitionUnitManagerController/doAcquisitionGroupAdd',
            type: 'POST',
            data: {
            	protocol: protocol,
            	acqUnit: acqUnit,
            	groupName: groupName,
                groupCode:'',
                type: type,
                groupTimingInterval: timingInterval,
                groupSavingInterval: savingInterval,
                remark: remark
            },
            dataType: 'json',
            success: function(resp) {
                mini.unmask(document.body);
                if (resp.msg === true) {
                    window._newAcqUnitObjectName = groupName;
                    window._newAcqUnitObjectClasses = 3;
                    
                    if (window._parentSetNewObject) {
                        window._parentSetNewObject(groupName,3);
                    }
                    if (window._parentRefreshUnitTree) {
                        window._parentRefreshUnitTree();
                    }
                    
                    mini.alert(_loginUserLanguageResource.addedSuccessfully, function() {
                        window.CloseOwnerWindow('ok');
                    });
                } else {
                    mini.alert('<font color="red">' + (_loginUserLanguageResource.addFailure) + '</font>');
                }
            },
            error: function() {
                mini.unmask(document.body);
                mini.alert((_loginUserLanguageResource.exceptionThrow) + ': ' + (_loginUserLanguageResource.contactAdmin));
            }
        });
    }

    function onCancel() {
        window.CloseOwnerWindow('cancel');
    }

    $(document).ready(function() {
        mini.parse();

        // 协议变更时重新加载单元列表
        var protocolComb = mini.get('protocolComb');
        if (protocolComb) {
            protocolComb.on('valuechanged', function(e) {
                loadUnitList();
            });
        }

        // 国际化按钮和标签
        var btnSave = mini.get('btnSave');
        if (btnSave) btnSave.setText(_loginUserLanguageResource.save);
        var btnCancel = mini.get('btnCancel');
        if (btnCancel) btnCancel.setText(_loginUserLanguageResource.cancel);

        var labels = document.querySelectorAll('.label');
        if (labels.length >= 7) {
            labels[0].innerHTML = (_loginUserLanguageResource.protocolName) + '<span style="color:red;">*</span>';
            labels[1].innerHTML = (_loginUserLanguageResource.unitName) + '<span style="color:red;">*</span>';
            labels[2].innerHTML = (_loginUserLanguageResource.groupName) + '<span style="color:red;">*</span>';
            labels[3].innerHTML = _loginUserLanguageResource.groupType;
            labels[4].innerHTML = _loginUserLanguageResource.groupTimingInterval+'(s)';
            labels[5].innerHTML = _loginUserLanguageResource.groupSavingInterval+'(s)';
            labels[6].innerHTML = _loginUserLanguageResource.groupDescription;
        }

        // 如果有未处理的数据，立即处理
        if (_pendingData) {
            processSetData();
        }
    });
</script>
</body>
</html>