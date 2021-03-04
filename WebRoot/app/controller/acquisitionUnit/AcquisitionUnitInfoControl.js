Ext.define('AP.controller.acquisitionUnit.AcquisitionUnitInfoControl', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'acquisitionUnitInfoView',
        selector: 'acquisitionUnitInfoView'
   }],
    init: function () {
        this.control({
            
        })
    }
});

function addAcquisitionUnitInfo() {
    var AcquisitionUnitInfoWindow = Ext.create("AP.view.acquisitionUnit.AcquisitionUnitInfoWindow", {
        title: '创建采集单元'
    });
    AcquisitionUnitInfoWindow.show();
    Ext.getCmp("addFormAcquisitionUnit_Id").show();
    Ext.getCmp("updateFormaAquisitionUnit_Id").hide();
    return false;
};

function addAcquisitionGroupInfo() {
    var AcquisitionGroupInfoWindow = Ext.create("AP.view.acquisitionUnit.AcquisitionGroupInfoWindow", {
        title: '创建采集组'
    });
    AcquisitionGroupInfoWindow.show();
    Ext.getCmp("addFormAcquisitionGroup_Id").show();
    Ext.getCmp("updateFormaAquisitionGroup_Id").hide();
    return false;
};

//窗体创建按钮事件
var SaveAcquisitionUnitSubmitBtnForm = function () {
    var saveAcquisitionUnitWinForm = Ext.getCmp("acquisitionUnit_editWin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (saveAcquisitionUnitWinForm.getForm().isValid()) {
        saveAcquisitionUnitWinForm.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doAcquisitionUnitAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.sendServer,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('acquisitionUnit_editWin_Id').close();
                Ext.getCmp("AcquisitionUnitInfoGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.success + "</font>】，" + cosog.string.dataInfo + "");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts, "<font color=red>SORRY！</font>" + cosog.string.failInfo + "。");

                }
            },
            failure: function () {
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font> 】：" + cosog.string.contactadmin + "！");
            }
        });
    } else {
        Ext.Msg.alert(cosog.string.ts, "<font color=red>SORRY！" + cosog.string.validdata + ".</font>。");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

//采集组窗体创建按钮事件
var SaveAcquisitionGroupSubmitBtnForm = function () {
    var saveAcquisitionGroupWinForm = Ext.getCmp("acquisitionGroup_editWin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (saveAcquisitionGroupWinForm.getForm().isValid()) {
        saveAcquisitionGroupWinForm.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doAcquisitionGroupAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.sendServer,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('acquisitionGroup_editWin_Id').close();
                Ext.getCmp("AcquisitionGroupInfoGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.success + "</font>】，" + cosog.string.dataInfo + "");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts, "<font color=red>SORRY！</font>" + cosog.string.failInfo + "。");

                }
            },
            failure: function () {
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font> 】：" + cosog.string.contactadmin + "！");
            }
        });
    } else {
        Ext.Msg.alert(cosog.string.ts, "<font color=red>SORRY！" + cosog.string.validdata + ".</font>。");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

//窗体上的修改按钮事件
function UpdateAcquisitionUnitDataInfoSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext.getCmp("acquisitionUnit_editWin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("acquisitionUnit_editWin_Id").el.mask(cosog.string.updatewait).show();
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doAcquisitionUnitEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("acquisitionUnit_editWin_Id").getEl().unmask();
                Ext.getCmp('acquisitionUnit_editWin_Id').close();
                Ext.getCmp("AcquisitionUnitInfoGridPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts,
                        "<font color=red>SORRY！</font>" + cosog.string.updatefail + "。");
                }
            },
            failure: function () {
                Ext.getCmp("acquisitionUnit_editWin_Id").getEl().unmask();
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });
    }
    return false;
};

function modifyAcquisitionUnitInfo() {
	var AcquisitionUnit_panel = Ext.getCmp("AcquisitionUnitInfoGridPanel_Id");
    var AcquisitionUnit_model = AcquisitionUnit_panel.getSelectionModel();
    var _record = AcquisitionUnit_model.getSelection();
    if (_record.length>0) {
    	var AcquisitionUnitInfoWindow = Ext.create("AP.view.acquisitionUnit.AcquisitionUnitInfoWindow", {
            title: '创建采集类型'
        });
        AcquisitionUnitInfoWindow.show();
        Ext.getCmp("addFormAcquisitionUnit_Id").hide();
        Ext.getCmp("updateFormaAquisitionUnit_Id").show();
        SelectAcquisitionUnitDataAttrInfoGridPanel();
    }else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
    return false;
}

//复值
SelectAcquisitionUnitDataAttrInfoGridPanel = function () {
    var dataattr_row = Ext.getCmp("AcquisitionUnitInfoGridPanel_Id").getSelectionModel().getSelection();
    var id = dataattr_row[0].data.id;
    var unitName = dataattr_row[0].data.unitName;
    var unitCode = dataattr_row[0].data.unitCode;
    var remark = dataattr_row[0].data.remark;
    Ext.getCmp('formAcquisitionUnitJlbh_Id').setValue(id);
    Ext.getCmp('formAcquisitionUnitName_Id').setValue(unitName);
    Ext.getCmp('formAcquisitionUnitCode_Id').setValue(unitCode);
    Ext.getCmp('acquisitionUnitRemark_Id').setValue(remark);
};

function delAcquisitionUnitInfo() {
    var gridPanel = Ext.getCmp("AcquisitionUnitInfoGridPanel_Id");
    var selectionModel = gridPanel.getSelectionModel();
    var _record = selectionModel.getSelection();
    var delUrl = context + '/acquisitionUnitManagerController/doAcquisitionUnitBulkDelete'
    if (_record.length>0) {
    	Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm(cosog.string.yesdel, cosog.string.yesdeldata, function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("AcquisitionUnitInfoGridPanel_Id", _record,"id", delUrl);
            }
        });
        
    } else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
}

function checkSelectedAcquisitionItemsCombox(node, root) {
    if (null != root && root != "") {
        var chlidArray = node;
        if (!Ext.isEmpty(chlidArray)) {
            Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
                var x_node_seId = chlidArray[index].data.id;

                Ext.Array.each(root, function (name, index,countriesItSelf) {
                    var menuselectid = root[index].itemId;

                    // 处理已选择的节点
                    if (x_node_seId == menuselectid) {
                        childArrNode.set('checked', true);
                        childArrNode.expand('true');
                    }
                });
                // 递归
                if (childArrNode.childNodes != null) {
                	checkSelectedAcquisitionItemsCombox(childArrNode.childNodes, root);
                }
            });
        }
    }
    return false;
};

function checkSelectedAcquisitionGroupsCombox(node, root) {
    if (null != root && root != "") {
        var chlidArray = node;
        if (!Ext.isEmpty(chlidArray)) {
            Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
                var x_node_seId = chlidArray[index].data.id;

                Ext.Array.each(root, function (name, index,countriesItSelf) {
                    var menuselectid = root[index].groupId;

                    // 处理已选择的节点
                    if (x_node_seId == menuselectid) {
                        childArrNode.set('checked', true);
                        childArrNode.expand('true');
                    }
                });
                // 递归
                if (childArrNode.childNodes != null) {
                	checkSelectedAcquisitionGroupsCombox(childArrNode.childNodes, root);
                }
            });
        }
    }
    return false;
};

showAcquisitionGroupOwnItems = function (store_) {
    var selectedAcquisitionGroupCode = Ext.getCmp("selectedAcquisitionGroupCode_Id").getValue();
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/acquisitionUnitManagerController/showAcquisitionGroupOwnItems?groupId=' + selectedAcquisitionGroupCode,
        success: function (response, opts) {
            // 处理后
            var items = Ext.decode(response.responseText);
            if (null != items && items != "") {
                var getNode = store_.root.childNodes;
                checkSelectedAcquisitionItemsCombox(getNode, items);
            }
        },
        failure: function (response, opts) {
            Ext.Msg.alert("信息提示", "后台获取数据失败！");
        }
    });
    return false;
}

showAcquisitionUnitOwnGroups = function (store_) {
    var selectedAcquisitionUnitId = Ext.getCmp("selectedAcquisitionUnitCode_Id").getValue();
    var panel = Ext.getCmp("AcquisitionGroupInfoGridPanel_Id");
    panel.getSelectionModel().deselectAll(true);
    if(selectedAcquisitionUnitId!==""){
    	Ext.Ajax.request({
            method: 'POST',
            url: context + '/acquisitionUnitManagerController/showAcquisitionUnitOwnGroups?unitId=' + selectedAcquisitionUnitId,
            success: function (response, opts) {
                // 处理后
                var groups = Ext.decode(response.responseText);
//                var panel = Ext.getCmp("AcquisitionGroupInfoGridPanel_Id");
                var model = store_.getRange(0,store_.getCount());
                for(var i=0;i<groups.length;i++){
                	for(var j=0;j<model.length;j++){
                		if(groups[i].groupId==model[j].data.id){
                			panel.getSelectionModel().select(j, true);
                			break;
                		}
                	}
                }
            },
            failure: function (response, opts) {
                Ext.Msg.alert("信息提示", "后台获取数据失败！");
            }
        });
    }
    return false;
}

//为当前采集组安排采集项
var grantAcquisitionItemsPermission = function () {
    var treeGridPanel = Ext.getCmp("acquisitionItemsTreeGridPanel_Id");
    _record = treeGridPanel.getChecked();
    var addUrl = context + '/acquisitionUnitManagerController/grantAcquisitionItemsPermission'
        // 添加条件
    var addjson = [];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    var groupId = Ext.getCmp("selectedAcquisitionGroupCode_Id").getValue();
    if (!isNotVal(groupId)) {
        Ext.Msg.alert(cosog.string.ts, '请先选择一个采集组!');
        return false
    }
    if (_record.length > 0) {
        Ext.Array.each(_record, function (name, index, countriesItSelf) {
            var group_item_id = _record[index].get('id')
            addjson.push(group_item_id);
            var matrix_value = "";
            matrix_value = '0,0,0,';
            if (matrix_value != "" || matrix_value != null) {
                matrix_value = matrix_value.substring(0,matrix_value.length - 1);
            }
            matrixData += group_item_id + ":" + matrix_value + "|";

        });

        matrixData = matrixData.substring(0, matrixData.length - 1);
        var addparamsId = "" + addjson.join(",");
        var matrixCodes_ = "" + matrixData;

        // AJAX提交方式
        Ext.Ajax.request({
            url: addUrl,
            method: "POST",
            // 提交参数
            params: {
                paramsId: addparamsId,
                groupId: groupId,
                matrixCodes: matrixCodes_
            },
            success: function (response) {
                var result = Ext.JSON.decode(response.responseText);
                if (result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + '成功安排了' + "</font>】" + _record.length + "" + '个采集项' + "。");
                }
                if (result.msg == false) {
                    Ext.Msg.alert('info', "<font color=red>SORRY！" + '采集项安排失败' + "。</font>");
                }
                // 刷新Grid
                Ext.getCmp("acquisitionItemsTreeGridPanel_Id").getStore().load();
            },
            failure: function () {
                Ext.Msg.alert("warn", "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });

    } else {
        Ext.Msg.alert(cosog.string.ts, '<font color=blue>' + '请先选择一个采集组!' + '！</font>');
    }
    return false;
}

//为当前采集单元安排采集组
var grantAcquisitionGroupsPermission = function () {
    var gridPanel = Ext.getCmp("AcquisitionGroupInfoGridPanel_Id");
    _record = gridPanel.getSelectionModel().getSelection();
    var addUrl = context + '/acquisitionUnitManagerController/grantAcquisitionGroupsPermission'
        // 添加条件
    var addjson = [];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    var unitId = Ext.getCmp("selectedAcquisitionUnitCode_Id").getValue();
    if (!isNotVal(unitId)) {
        Ext.Msg.alert(cosog.string.ts, '请先选择一个采集单元!');
        return false
    }
    if (_record.length > 0) {
        Ext.Array.each(_record, function (name, index, countriesItSelf) {
            var unit_group_id = _record[index].get('id')
            addjson.push(unit_group_id);
            var matrix_value = "";
            matrix_value = '0,0,0,';
            if (matrix_value != "" || matrix_value != null) {
                matrix_value = matrix_value.substring(0,matrix_value.length - 1);
            }
            matrixData += unit_group_id + ":" + matrix_value + "|";

        });

        matrixData = matrixData.substring(0, matrixData.length - 1);
        var addparamsId = "" + addjson.join(",");
        var matrixCodes_ = "" + matrixData;

        // AJAX提交方式
        Ext.Ajax.request({
            url: addUrl,
            method: "POST",
            // 提交参数
            params: {
                paramsId: addparamsId,
                unitId: unitId,
                matrixCodes: matrixCodes_
            },
            success: function (response) {
                var result = Ext.JSON.decode(response.responseText);
                if (result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + '成功安排了' + "</font>】" + _record.length + "" + '个采集组' + "。");
                }
                if (result.msg == false) {
                    Ext.Msg.alert('info', "<font color=red>SORRY！" + '采集组安排失败' + "。</font>");
                }
                // 刷新Grid
                Ext.getCmp("AcquisitionGroupInfoGridPanel_Id").getStore().load();
            },
            failure: function () {
                Ext.Msg.alert("warn", "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });

    } else {
        Ext.Msg.alert(cosog.string.ts, '<font color=blue>' + '请先选择一个采集单元!' + '！</font>');
    }
    return false;
}