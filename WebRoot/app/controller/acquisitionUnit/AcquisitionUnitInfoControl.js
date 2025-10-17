Ext.define('AP.controller.acquisitionUnit.AcquisitionUnitInfoControl', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'scadaConfigInfoView',
        selector: 'scadaConfigInfoView'
   }],
    init: function () {
        this.control({

        })
    }
});

function addModbusProtocolAddrMappingConfigData() {
	var selectedDeviceTypeName="";
	var selectedDeviceTypeId="";
	var tabTreeStore = Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getStore();
	var count=tabTreeStore.getCount();
	var tabTreeSelection = Ext.getCmp("ProtocolConfigTabTreeGridView_Id").getSelectionModel().getSelection();
	var rec=null;
	if (tabTreeSelection.length > 0) {
		rec=tabTreeSelection[0];
		selectedDeviceTypeName=foreachAndSearchTabAbsolutePath(tabTreeStore.data.items,tabTreeSelection[0].data.deviceTypeId);
		selectedDeviceTypeId=tabTreeSelection[0].data.deviceTypeId;
	} else {
		if(count>0){
			rec=tabTreeStore.getAt(0);
			selectedDeviceTypeName=tabTreeStore.getAt(0).data.text;
			selectedDeviceTypeId=tabTreeStore.getAt(0).data.deviceTypeId;
		}
	}
	
	if(selectedDeviceTypeId!=""){
		var window = Ext.create("AP.view.acquisitionUnit.ModbusProtocolInfoWindow", {
	        title: loginUserLanguageResource.addProtoco
	    });
	    window.show();
	    
	    Ext.getCmp("protocolWinTabLabel_Id").setHtml(loginUserLanguageResource.owningDeviceType+":<font color=red>"+selectedDeviceTypeName+"</font>,"+loginUserLanguageResourceFirstLower.pleaseConfirm+"<br/>&nbsp;");
	    Ext.getCmp("protocolWinTabLabel_Id").show();
	    
	    Ext.getCmp('modbusProtocolDeviceType_Id').setValue(selectedDeviceTypeId);
	    
	    Ext.getCmp("addFormModbusProtocol_Id").show();
	    Ext.getCmp("updateFormaModbusProtocol_Id").hide();
	    
	    Ext.getCmp("protocolLanguage_Id1").setValue(loginUserLanguageValue);
	}else{
		Ext.MessageBox.alert(loginUserLanguageResource.message,loginUserLanguageResource.selectDeviceType);
	}
	
	
	
	
    return false;
};

//协议配置窗体创建按钮事件
var saveModbusProtocolSubmitBtnForm = function () {
    var winForm = Ext.getCmp("modbusProtocol_editWin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (winForm.getForm().isValid()) {
        winForm.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doModbusProtocolAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.sendServer,
            waitTitle: loginUserLanguageResource.wait,
            success: function (response, action) {
                if (action.result.msg == true) {
                	var addProtocolName = Ext.getCmp('formModbusProtocolName_Id').getValue();
                	Ext.getCmp('ModbusProtocolAddNewProtocolName_Id').setValue(addProtocolName);
                	Ext.getCmp('modbusProtocol_editWin_Id').close();
                	Ext.getCmp("ModbusProtocolAddrMappingConfigTreeGridPanel_Id").getStore().load();
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.addFailure + "</font>");

                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};




function addAcquisitionUnitInfo() {
    var AcquisitionUnitInfoWindow = Ext.create("AP.view.acquisitionUnit.AcquisitionUnitInfoWindow", {
        title: loginUserLanguageResource.addAcqUnit
    });
    AcquisitionUnitInfoWindow.show();
    Ext.getCmp("addFormAcquisitionUnit_Id").show();
    Ext.getCmp("updateFormaAquisitionUnit_Id").hide();
    return false;
};

function addAcquisitionGroupInfo() {
    var AcquisitionGroupInfoWindow = Ext.create("AP.view.acquisitionUnit.AcquisitionGroupInfoWindow", {
        title: loginUserLanguageResource.addAcqGroup
    });
    AcquisitionGroupInfoWindow.show();
    Ext.getCmp("addFormAcquisitionGroup_Id").show();
    Ext.getCmp("updateFormaAquisitionGroup_Id").hide();
    return false;
};

//采控组窗体创建按钮事件
var SaveAcquisitionGroupSubmitBtnForm = function () {
    var saveAcquisitionGroupWinForm = Ext.getCmp("acquisitionGroup_editWin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (saveAcquisitionGroupWinForm.getForm().isValid()) {
        saveAcquisitionGroupWinForm.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doAcquisitionGroupAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.sendServer,
            waitTitle: loginUserLanguageResource.wait,
            success: function (response, action) {
                Ext.getCmp('acquisitionGroup_editWin_Id').close();
                Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>】");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>SORRY！</font>" + loginUserLanguageResource.addFailure);

                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

//窗体上的修改按钮事件
function UpdateAcquisitionGroupDataInfoSubmitBtnForm() {
    var getGroupUpdateDataInfoSubmitBtnFormId = Ext.getCmp("acquisitionGroup_editWin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (getGroupUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("acquisitionGroup_editWin_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
        getGroupUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doAcquisitionGroupEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("acquisitionGroup_editWin_Id").getEl().unmask();
                Ext.getCmp('acquisitionGroup_editWin_Id').close();
                Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=blue>" + loginUserLanguageResource.updateSuccessfully + "</font>】");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip,
                        "<font color=red>SORRY！</font>" + loginUserLanguageResource.updateFailure);
                }
            },
            failure: function () {
                Ext.getCmp("acquisitionGroup_editWin_Id").getEl().unmask();
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    }
    return false;
};

function modifyAcquisitionGroupInfo() {
    var gridPanel = Ext.getCmp("AcquisitionGroupInfoGridPanel_Id");
    var selectedModel = gridPanel.getSelectionModel();
    var _record = selectedModel.getSelection();
    if (_record.length > 0) {
        var editWindow = Ext.create("AP.view.acquisitionUnit.AcquisitionGroupInfoWindow", {
            title: '编辑采控组'
        });
        editWindow.show();
        Ext.getCmp("addFormAcquisitionGroup_Id").hide();
        Ext.getCmp("updateFormaAquisitionGroup_Id").show();
        SelectAcquisitionGroupDataAttrInfoGridPanel();
    } else {
        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
    }
    return false;
}

//复值
SelectAcquisitionGroupDataAttrInfoGridPanel = function () {
    var dataattr_row = Ext.getCmp("AcquisitionGroupInfoGridPanel_Id").getSelectionModel().getSelection();
    var id = dataattr_row[0].data.id;
    var groupName = dataattr_row[0].data.groupName;
    var groupCode = dataattr_row[0].data.groupCode;
    var groupTimingInterval = dataattr_row[0].data.groupTimingInterval;
    var groupSavingInterval = dataattr_row[0].data.groupSavingInterval;
    var remark = dataattr_row[0].data.remark;
    Ext.getCmp('formAcquisitionGroupJlbh_Id').setValue(id);
    Ext.getCmp('formAcquisitionGroupName_Id').setValue(groupName);
    Ext.getCmp('formAcquisitionGroupCode_Id').setValue(groupCode);
    Ext.getCmp('formAcquisitionGroupGroupTimingInterval_Id').setValue(groupTimingInterval);
    Ext.getCmp('formAcquisitionGroupGroupSavingInterval_Id').setValue(groupSavingInterval);
    Ext.getCmp('acquisitionGroupRemark_Id').setValue(remark);
};

function delAcquisitionGroupInfo() {
    var gridPanel = Ext.getCmp("AcquisitionGroupInfoGridPanel_Id");
    var selectionModel = gridPanel.getSelectionModel();
    var _record = selectionModel.getSelection();
    var delUrl = context + '/acquisitionUnitManagerController/doAcquisitionGroupBulkDelete'
    if (_record.length > 0) {
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.cancel;
        Ext.Msg.confirm(loginUserLanguageResource.confirmDelete, loginUserLanguageResource.confirmDeleteData, function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("AcquisitionGroupInfoGridPanel_Id", _record, "id", delUrl);
            }
        });

    } else {
        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
    }
}

//窗体创建按钮事件
var SaveAcquisitionUnitSubmitBtnForm = function () {
    var saveAcquisitionUnitWinForm = Ext.getCmp("acquisitionUnit_editWin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (saveAcquisitionUnitWinForm.getForm().isValid()) {
        saveAcquisitionUnitWinForm.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doAcquisitionUnitAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.sendServer,
            waitTitle: loginUserLanguageResource.wait,
            success: function (response, action) {
                Ext.getCmp('acquisitionUnit_editWin_Id').close();
                Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>】");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>SORRY！</font>" + loginUserLanguageResource.addFailure);

                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

//窗体上的修改按钮事件
function UpdateAcquisitionUnitDataInfoSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext.getCmp("acquisitionUnit_editWin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("acquisitionUnit_editWin_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doAcquisitionUnitEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("acquisitionUnit_editWin_Id").getEl().unmask();
                Ext.getCmp('acquisitionUnit_editWin_Id').close();
                Ext.getCmp("AcquisitionUnitInfoGridPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.updateSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip,
                        "<font color=red>SORRY！</font>" + loginUserLanguageResource.updateFailure);
                }
            },
            failure: function () {
                Ext.getCmp("acquisitionUnit_editWin_Id").getEl().unmask();
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    }
    return false;
};

function modifyAcquisitionUnitInfo() {
    var AcquisitionUnit_panel = Ext.getCmp("AcquisitionUnitInfoGridPanel_Id");
    var AcquisitionUnit_model = AcquisitionUnit_panel.getSelectionModel();
    var _record = AcquisitionUnit_model.getSelection();
    if (_record.length > 0) {
        var AcquisitionUnitInfoWindow = Ext.create("AP.view.acquisitionUnit.AcquisitionUnitInfoWindow", {
            title: '编辑采控单元'
        });
        AcquisitionUnitInfoWindow.show();
        Ext.getCmp("addFormAcquisitionUnit_Id").hide();
        Ext.getCmp("updateFormaAquisitionUnit_Id").show();
        SelectAcquisitionUnitDataAttrInfoGridPanel();
    } else {
        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
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
    if (_record.length > 0) {
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.cancel;
        Ext.Msg.confirm(loginUserLanguageResource.confirmDelete, loginUserLanguageResource.confirmDeleteData, function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("AcquisitionUnitInfoGridPanel_Id", _record, "id", delUrl);
            }
        });

    } else {
        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
    }
}

function checkSelectedAcquisitionItemsCombox(node, root) {
    if (null != root && root != "") {
        var chlidArray = node;
        if (!Ext.isEmpty(chlidArray)) {
            Ext.Array.each(chlidArray, function (childArrNode, index, fog) {
                var x_node_seId = chlidArray[index].data.id;

                Ext.Array.each(root, function (name, index, countriesItSelf) {
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

                Ext.Array.each(root, function (name, index, countriesItSelf) {
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

showAcquisitionGroupOwnItems = function (selectedAcquisitionGroupCode) {
    Ext.Ajax.request({
        method: 'POST',
        url: context + '/acquisitionUnitManagerController/showAcquisitionGroupOwnItems?groupCode=' + selectedAcquisitionGroupCode,
        success: function (response, opts) {
            // 处理后
            var items = Ext.decode(response.responseText);
            if (protocolConfigItemsHandsontableHelper != null) {
                var driverConfigItemsData = protocolConfigItemsHandsontableHelper.hot.getData();
                for (var i = 0; i < driverConfigItemsData.length; i++) {
                	if(driverConfigItemsData[i][0]){
                		protocolConfigItemsHandsontableHelper.hot.setDataAtCell(i, 0, false);
                	}
                }
                for (var i = 0; i < items.length; i++) {
                    for (var j = 0; j < driverConfigItemsData.length; j++) {
                        if (items[i].itemName === driverConfigItemsData[j][2]) {
                            protocolConfigItemsHandsontableHelper.hot.setDataAtCell(j, 0, true);
                            break;
                        }
                    }
                }
            }
        },
        failure: function (response, opts) {
            Ext.Msg.alert(loginUserLanguageResource.Infotip, loginUserLanguageResource.dataQueryFailure);
        }
    });
    return false;
}

showAcquisitionUnitOwnGroups = function (selectedAcquisitionUnitId) {
	Ext.Ajax.request({
        method: 'POST',
        url: context + '/acquisitionUnitManagerController/showAcquisitionUnitOwnGroups?unitId=' + selectedAcquisitionUnitId,
        success: function (response, opts) {
        	var items = Ext.decode(response.responseText);
            if (acquisitionGroupConfigHandsontableHelper != null) {
                var acquisitionGroupData = acquisitionGroupConfigHandsontableHelper.hot.getData();
                for (var i = 0; i < acquisitionGroupData.length; i++) {
                	if(acquisitionGroupData[i][0]){
                		acquisitionGroupConfigHandsontableHelper.hot.setDataAtCell(i, 0, false);
                	}
                }
                for (var i = 0; i < items.length; i++) {
                    for (var j =0; j<acquisitionGroupData.length; j++) {
                        if (items[i].groupCode === acquisitionGroupData[j][3]) {
                        	acquisitionGroupConfigHandsontableHelper.hot.setDataAtCell(j, 0, true);
                            break;
                        }
                    }
                }
                
                acquisitionGroupData = acquisitionGroupConfigHandsontableHelper.hot.getData();
                for (var i = 0; i < acquisitionGroupData.length; i++) {
                	if(acquisitionGroupData[i][0]){
                		var rowdata = acquisitionGroupConfigHandsontableHelper.hot.getDataAtRow(i);
                		Ext.getCmp("selectedAcquisitionGroupCode_Id").setValue(rowdata[3]);
                		showAcquisitionGroupOwnItems(rowdata[3]);
                		break;
                	}
                }
            }
        },
        failure: function (response, opts) {
            Ext.Msg.alert(loginUserLanguageResource.Infotip, loginUserLanguageResource.dataQueryFailure);
        }
    });
    return false;
}

//为当前采控组安排采控项
var grantAcquisitionItemsPermission = function (groupType) {
    if (protocolAcqUnitConfigItemsHandsontableHelper == null) {
        return false;
    }
    var driverConfigItemsData = protocolAcqUnitConfigItemsHandsontableHelper.hot.getData();
    var addUrl = context + '/acquisitionUnitManagerController/grantAcquisitionItemsPermission'
    // 添加条件
    var addjson = [];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
   
    var ScadaDriverModbusConfigSelectRow= Ext.getCmp("ModbusProtocolAcqGroupConfigSelectRow_Id").getValue();
    var selectedItem=Ext.getCmp("ModbusProtocolAcqGroupConfigTreeGridPanel_Id").getStore().getAt(ScadaDriverModbusConfigSelectRow);
    
    var groupId = selectedItem.data.id;
    var groupCode = selectedItem.data.code;
    var protocol=selectedItem.data.protocol;
    if (!isNotVal(groupCode)) {
//        Ext.Msg.alert(loginUserLanguageResource.tip, '请先选择一个采控组!');
        return false
    }
    if (driverConfigItemsData.length > 0) {
        Ext.Array.each(driverConfigItemsData, function (name, index, countriesItSelf) {
            if ((driverConfigItemsData[index][0]+'')==='true') {
            	var itemName = driverConfigItemsData[index][2];
            	var itemAddr = driverConfigItemsData[index][3];
            	var resolutionMode = driverConfigItemsData[index][6];
            	var bitIndex=driverConfigItemsData[index][7];
            	var dailyTotalCalculate=0;
            	var dailyTotalCalculateName='';
            	if(driverConfigItemsData[index][8]){
            		dailyTotalCalculate=1;
            		dailyTotalCalculateName=driverConfigItemsData[index][9];
            		if(!isNotVal(dailyTotalCalculateName)){
            			dailyTotalCalculateName=itemName.replace('累计','').replace('累积','')+'日累计';
            		}
            	}
                
                addjson.push(itemName);
                var matrix_value = "";
                matrix_value = '0,0,0,';
                if (matrix_value != "" || matrix_value != null) {
                    matrix_value = matrix_value.substring(0, matrix_value.length - 1);
                }
                matrixData += itemName + ":"
                + itemAddr+ ":"
                + resolutionMode+ ":"
                + bitIndex +":"
                + matrix_value +":"
                + dailyTotalCalculateName+ ":"
                + dailyTotalCalculate+ "|";
            }
        });
        
        if(matrixData!=""){
        	matrixData = matrixData.substring(0, matrixData.length - 1);
        }
        
        var addparams = "" + addjson.join(",");
        var matrixCodes_ = "" + matrixData;
        Ext.Ajax.request({
            url: addUrl,
            method: "POST",
            async :  false,
            params: {
                params: addparams,
                protocol: protocol,
                groupId: groupId,
                groupCode: groupCode,
                matrixCodes: matrixCodes_
            },
            success: function (response) {
                var result = Ext.JSON.decode(response.responseText);
                if (result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
                }
                if (result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveFailure);
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    
    } else {
        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
    }
    return false;
}

//为当前采控单元安排采控组
var grantAcquisitionGroupsPermission = function () {
	if (acquisitionGroupConfigHandsontableHelper == null) {
        return false;
    }
    var acquisitionData = acquisitionGroupConfigHandsontableHelper.hot.getData();
    var addUrl = context + '/acquisitionUnitManagerController/grantAcquisitionGroupsPermission'
    // 添加条件
    var addjson = [];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    var unitId = Ext.getCmp("selectedAcquisitionUnitId_Id").getValue();
    if (!isNotVal(unitId)) {
//        Ext.Msg.alert(loginUserLanguageResource.tip, '请先选择一个采控单元!');
        return false
    }
    if (acquisitionData.length > 0) {
        Ext.Array.each(acquisitionData, function (name, index, countriesItSelf) {
        	if (acquisitionData[index][0]) {
                var groupId = acquisitionData[index][1];
                addjson.push(groupId);
                var matrix_value = "";
                matrix_value = '0,0,0,';
                if (matrix_value != "" || matrix_value != null) {
                    matrix_value = matrix_value.substring(0, matrix_value.length - 1);
                }
                matrixData += groupId + ":" + matrix_value + "|";
            }
        });
        matrixData = matrixData.substring(0, matrixData.length - 1);
        var addparamsId = "" + addjson.join(",");
        var matrixCodes_ = "" + matrixData;
        Ext.Ajax.request({
            url: addUrl,
            method: "POST",
            params: {
                paramsId: addparamsId,
                unitId: unitId,
                matrixCodes: matrixCodes_
            },
            success: function (response) {
                var result = Ext.JSON.decode(response.responseText);
                if (result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=blue>" + '成功安排了' + "</font>】" + addjson.length + "" + '个采控组' + "。");
                }
                if (result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>SORRY！" + '采控组安排失败' + "。</font>");
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
        Ext.Msg.alert(loginUserLanguageResource.tip, '<font color=blue>' + loginUserLanguageResource.checkOne + '！</font>');
    }
    return false;
};
function addModbusProtocolInstanceConfigData() {
    var window = Ext.create("AP.view.acquisitionUnit.ModbusProtocolInstanceInfoWindow", {
        title: loginUserLanguageResource.addAcqInstance
    });
    window.show();
    Ext.getCmp("addFormModbusProtocolInstance_Id").show();
    Ext.getCmp("updateFormaModbusProtocolInstance_Id").hide();
    return false;
};

//协议实例配置窗体创建按钮事件
var saveModbusProtocolInstanceSubmitBtnForm = function () {
    var winForm = Ext.getCmp("modbusProtocolInstanceInfoWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (winForm.getForm().isValid()) {
        winForm.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doModbusProtocolInstanceAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.sendServer,
            waitTitle: loginUserLanguageResource.wait,
            success: function (response, action) {
                Ext.getCmp('modbusProtocolInstanceInfoWindow_Id').close();
                Ext.getCmp("ModbusProtocolInstanceConfigTreeGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.addFailure + "</font>");

                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

function addAlarmUnitInfo() {
    var window = Ext.create("AP.view.acquisitionUnit.AlarmUnitInfoWindow", {
        title: loginUserLanguageResource.addAlarmUnit
    });
    window.show();
    Ext.getCmp("addFormAlarmUnit_Id").show();
    Ext.getCmp("updateFormaAquisitionUnit_Id").hide();
    return false;
};

//窗体创建按钮事件
var SaveAlarmUnitSubmitBtnForm = function () {
    var winForm = Ext.getCmp("alarmUnit_editWin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (winForm.getForm().isValid()) {
        winForm.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doAlarmUnitAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.sendServer,
            waitTitle: loginUserLanguageResource.wait,
            success: function (response, action) {
                Ext.getCmp('alarmUnit_editWin_Id').close();
                Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>】");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>SORRY！</font>" + loginUserLanguageResource.addFailure);

                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

function addDisplayUnitInfo() {
    var DisplayUnitInfoWindow = Ext.create("AP.view.acquisitionUnit.DisplayUnitInfoWindow", {
        title: loginUserLanguageResource.addDisplayUnit
    });
    DisplayUnitInfoWindow.show();
    Ext.getCmp("addFormDisplayUnit_Id").show();
    Ext.getCmp("updateFormaAquisitionUnit_Id").hide();
    return false;
};

//窗体创建按钮事件
var SaveDisplayUnitSubmitBtnForm = function () {
    var winForm = Ext.getCmp("displayUnit_editWin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (winForm.getForm().isValid()) {
    	winForm.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doDisplayUnitAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.sendServer,
            waitTitle: loginUserLanguageResource.wait,
            success: function (response, action) {
                Ext.getCmp('displayUnit_editWin_Id').close();
                Ext.getCmp("ModbusProtocolDisplayUnitConfigTreeGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>】");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>SORRY！</font>" + loginUserLanguageResource.addFailure);

                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

function addModbusProtocolDisplayInstanceConfigData() {
    var window = Ext.create("AP.view.acquisitionUnit.ModbusProtocolDisplayInstanceInfoWindow", {
        title: loginUserLanguageResource.addAcqInstance
    });
    window.show();
    Ext.getCmp("addFormModbusProtocolDisplayInstance_Id").show();
    Ext.getCmp("updateFormaModbusProtocolDisplayInstance_Id").hide();
    return false;
};

function addReportUnitInfo() {
    var window = Ext.create("AP.view.acquisitionUnit.ModbusProtocolReportUnitInfoWindow", {
        title: loginUserLanguageResource.addReportUnit
    });
    window.show();
    Ext.getCmp("addFormModbusProtocolReportUnit_Id").show();
    Ext.getCmp("updateFormaModbusProtocolReportUnit_Id").hide();
    return false;
};

function addModbusProtocolReportInstanceConfigData() {
    var window = Ext.create("AP.view.acquisitionUnit.ModbusProtocolReportInstanceInfoWindow", {
        title: loginUserLanguageResource.addReportInstance
    });
    window.show();
    Ext.getCmp("addFormModbusProtocolReportInstance_Id").show();
    Ext.getCmp("updateFormaModbusProtocolReportInstance_Id").hide();
    return false;
};

var saveModbusProtocolReportUnitSubmitBtnForm = function () {
    var winForm = Ext.getCmp("modbusProtocolReportUnitInfoWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (winForm.getForm().isValid()) {
        winForm.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doModbusProtocolReportUnitAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.sendServer,
            waitTitle: loginUserLanguageResource.wait,
            success: function (response, action) {
                Ext.getCmp('modbusProtocolReportUnitInfoWindow_Id').close();
                Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.addFailure + "</font>");

                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

//协议实例配置窗体创建按钮事件
var saveModbusProtocolDisplayInstanceSubmitBtnForm = function () {
    var winForm = Ext.getCmp("modbusProtocolDisplayInstanceInfoWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (winForm.getForm().isValid()) {
        winForm.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doModbusProtocolDisplayInstanceAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.sendServer,
            waitTitle: loginUserLanguageResource.wait,
            success: function (response, action) {
                Ext.getCmp('modbusProtocolDisplayInstanceInfoWindow_Id').close();
                Ext.getCmp("ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.addFailure + "</font>");

                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

var saveModbusProtocolReportInstanceSubmitBtnForm = function () {
    var winForm = Ext.getCmp("modbusProtocolReportInstanceInfoWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (winForm.getForm().isValid()) {
        winForm.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doModbusProtocolReportInstanceAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.sendServer,
            waitTitle: loginUserLanguageResource.wait,
            success: function (response, action) {
                Ext.getCmp('modbusProtocolReportInstanceInfoWindow_Id').close();
                Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.addFailure + "</font>");

                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

function addModbusProtocolAlarmInstanceConfigData() {
    var window = Ext.create("AP.view.acquisitionUnit.ModbusProtocolAlarmInstanceInfoWindow", {
        title: loginUserLanguageResource.addAcqInstance
    });
    window.show();
    Ext.getCmp("addFormModbusProtocolAlarmInstance_Id").show();
    Ext.getCmp("updateFormaModbusProtocolAlarmInstance_Id").hide();
    return false;
};

//协议实例配置窗体创建按钮事件
var saveModbusProtocolAlarmInstanceSubmitBtnForm = function () {
    var winForm = Ext.getCmp("modbusProtocolAlarmInstanceInfoWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (winForm.getForm().isValid()) {
        winForm.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doModbusProtocolAlarmInstanceAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.sendServer,
            waitTitle: loginUserLanguageResource.wait,
            success: function (response, action) {
                Ext.getCmp('modbusProtocolAlarmInstanceInfoWindow_Id').close();
                Ext.getCmp("ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.addFailure + "</font>");

                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

function addModbusProtocolSMSInstanceConfigData() {
    var window = Ext.create("AP.view.acquisitionUnit.ModbusProtocolSMSInstanceInfoWindow", {
        title: loginUserLanguageResource.addAcqInstance
    });
    window.show();
    Ext.getCmp("addFormModbusprotocolSMSInstance_Id").show();
    Ext.getCmp("updateFormaModbusprotocolSMSInstance_Id").hide();
    return false;
};

function modifyModbusProtocolSMSInstanceConfigData() {
    var gridPanel = Ext.getCmp("ModbusProtocolSMSInstanceGridPanel_Id");
    var selectedModel = gridPanel.getSelectionModel();
    var _record = selectedModel.getSelection();
    if (_record.length > 0) {
        var editWindow = Ext.create("AP.view.acquisitionUnit.ModbusProtocolSMSInstanceInfoWindow", {
            title: '编辑短信实例'
        });
        editWindow.show();
        Ext.getCmp("addFormModbusprotocolSMSInstance_Id").hide();
        Ext.getCmp("updateFormaModbusprotocolSMSInstance_Id").show();
        SelectModbusProtocolSMSInstanceGridPanel();
    } else {
        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
    }
    return false;
}

SelectModbusProtocolSMSInstanceGridPanel = function () {
    var dataattr_row = Ext.getCmp("ModbusProtocolSMSInstanceGridPanel_Id").getSelectionModel().getSelection();
    var id = dataattr_row[0].data.id;
    var name = dataattr_row[0].data.name;
    var code = dataattr_row[0].data.code;
    var acqProtocolType = dataattr_row[0].data.acqProtocolType;
    var ctrlProtocolType = dataattr_row[0].data.ctrlProtocolType;
    var sort = dataattr_row[0].data.sort;
    Ext.getCmp('formModbusprotocolSMSInstance_Id').setValue(id);
    Ext.getCmp('formModbusprotocolSMSInstanceName_Id').setValue(name);
    Ext.getCmp('formModbusprotocolSMSInstanceCode_Id').setValue(code);
    Ext.getCmp('modbusSMSInstanceAcqProtocolType_Id').setValue(acqProtocolType);
    Ext.getCmp('modbusInstanceAcqProtocolTypeComb_Id').setValue(acqProtocolType);
    Ext.getCmp('modbusInstanceAcqProtocolTypeComb_Id').setRawValue(acqProtocolType);
    
    Ext.getCmp('modbusSMSInstanceCtrlProtocolType_Id').setValue(ctrlProtocolType);
    Ext.getCmp('modbusInstanceCtrlProtocolTypeComb_Id').setValue(ctrlProtocolType);
    Ext.getCmp('modbusInstanceCtrlProtocolTypeComb_Id').setRawValue(ctrlProtocolType);
    
    Ext.getCmp('modbusProtocolSMSInstanceSort_Id').setValue(sort);
};

//短信实例配置窗体创建按钮事件
var saveModbusProtocolSMSInstanceSubmitBtnForm = function () {
    var winForm = Ext.getCmp("modbusProtocolSMSInstanceInfoWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (winForm.getForm().isValid()) {
        winForm.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doModbusProtocolSMSInstanceAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.sendServer,
            waitTitle: loginUserLanguageResource.wait,
            success: function (response, action) {
                Ext.getCmp('modbusProtocolSMSInstanceInfoWindow_Id').close();
                Ext.getCmp("ModbusProtocolSMSInstanceGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.addFailure + "</font>");
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

function UpdateModbusProtocolSMSInstanceSubmitBtnForm() {
    var winForm = Ext.getCmp("modbusProtocolSMSInstanceInfoWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (winForm.getForm().isValid()) {
        winForm.getForm().submit({
            url: context + '/acquisitionUnitManagerController/doModbusProtocolSMSInstanceEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
            	Ext.getCmp('modbusProtocolSMSInstanceInfoWindow_Id').close();
                Ext.getCmp("ModbusProtocolSMSInstanceGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.updateSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>" + loginUserLanguageResource.addFailure + "</font>");
                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    }
    return false;
};

function delModbusProtocolSMSInstanceInfo() {
    var gridPanel = Ext.getCmp("ModbusProtocolSMSInstanceGridPanel_Id");
    var _model = gridPanel.getSelectionModel();
    var _record = _model.getSelection();
    var delUrl = context + '/acquisitionUnitManagerController/doModbusProtocolSMSInstanceDelete'
    if (_record.length>0) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.cancel;
        Ext.Msg.confirm(loginUserLanguageResource.confirmDelete, loginUserLanguageResource.confirmDeleteData, function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("ModbusProtocolSMSInstanceGridPanel_Id", _record,"id", delUrl);
            }
        });
    } else {
        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
    }
    return false;
}
