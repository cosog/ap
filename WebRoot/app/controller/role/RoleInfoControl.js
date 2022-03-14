Ext.define('AP.controller.role.RoleInfoControl', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'roleInfoGridPanel',
        selector: 'roleInfoGridPanel'
   }],
    init: function () {
        this.control({
            'roleInfoGridPanel > toolbar button[action=addroleAction]': {
                click: addroleInfo
            },
            'roleInfoGridPanel > toolbar button[action=delroleAction]': {
                click: delroleInfo
            },
            'roleInfoGridPanel > toolbar button[action=editroleInfoAction]': {
                click: modifyroleInfo
            },
            'roleInfoGridPanel': {
                itemdblclick: modifyroleInfo
            }
        })
    }
});

// 窗体创建按钮事件
var SaveroleDataInfoSubmitBtnForm = function () {
    var saveDataAttrInfoWinFormId = Ext.getCmp("role_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (saveDataAttrInfoWinFormId.getForm().isValid()) {
        saveDataAttrInfoWinFormId.getForm().submit({
            url: context + '/roleManagerController/doRoleAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.sendServer,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('role_addwin_Id').close();
                Ext.getCmp("RoleInfoGridPanel_Id").getStore().load();
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
    	Ext.Msg.alert(cosog.string.ts, "<font color=red>*为必填项，请检查数据有效性.</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

// 窗体上的修改按钮事件
function UpdateroleDataInfoSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext.getCmp("role_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("role_addwin_Id").el.mask(cosog.string.updatewait).show();
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/roleManagerController/doRoleEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("role_addwin_Id").getEl().unmask();
                Ext.getCmp('role_addwin_Id').close();
                Ext.getCmp("RoleInfoGridPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts,
                        "<font color=red>SORRY！</font>" + cosog.string.updatefail + "。");
                }
            },
            failure: function () {
                Ext.getCmp("role_addwin_Id").getEl().unmask();
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });
    } else {
    	Ext.Msg.alert(cosog.string.ts, "<font color=red>*为必填项，请检查数据有效性.</font>");
    }
    return false;
};

// 复值
SelectRoleDataAttrInfoGridPanel = function () {
    var dataattr_row = Ext.getCmp("RoleInfoGridPanel_Id").getSelectionModel().getSelection();
    var roleId = dataattr_row[0].data.roleId;
    var roleName = dataattr_row[0].data.roleName;
    var roleLevel=dataattr_row[0].data.roleLevel;
    var roleFlag = dataattr_row[0].data.roleFlag;
    var roleFlagName = dataattr_row[0].data.roleFlagName;
    var showLevel=dataattr_row[0].data.showLevel;
    var remark=dataattr_row[0].data.remark;
    
    var currentUserRoleLevel=parseInt(Ext.getCmp("currentUserRoleLevel_Id").getValue());
    var currentUserRoleShowLevel=parseInt(Ext.getCmp("currentUserRoleShowLevel_Id").getValue());
    var currentUserRoleFlag=parseInt(Ext.getCmp("currentUserRoleFlag_Id").getValue());
    
    Ext.getCmp('role_Id').setValue(roleId);
    Ext.getCmp('role_Name_Id').setValue(roleName);
    Ext.getCmp('roleLevel_Id').setValue(roleLevel);
    Ext.getCmp('roleFlag_Id').setValue(roleFlag);
    Ext.getCmp('roleFlagComboxfield_Id').setValue(roleFlag);
    Ext.getCmp('roleFlagComboxfield_Id').setRawValue(roleFlagName);
    Ext.getCmp('roleShowLevel_Id').setValue(showLevel);
    Ext.getCmp('roleRemark_Id').setValue(remark);
    
    if(currentUserRoleFlag==0){
//    	Ext.getCmp('roleFlagComboxfield_Id').setReadOnly(true);
    	Ext.getCmp('roleFlagComboxfield_Id').disable();
    }
    
    if(currentUserRoleShowLevel>=showLevel){
//    	Ext.getCmp('roleShowLevel_Id').setReadOnly(true);
    	Ext.getCmp('roleShowLevel_Id').disable();
    }else{
    	Ext.getCmp("roleShowLevel_Id").setMinValue(currentUserRoleShowLevel+1);
    }
    
    if(currentUserRoleLevel>=roleLevel){
//    	Ext.getCmp('roleLevel_Id').setReadOnly(true);
    	Ext.getCmp('roleLevel_Id').disable();
    }else{
        Ext.getCmp("roleLevel_Id").setMinValue(currentUserRoleLevel+1);
    }
};

function addroleInfo() {
    var roleInfoWindow = Ext.create("AP.view.role.RoleInfoWindow", {
        title: cosog.string.addRole
    });
    roleInfoWindow.show();
    
    var currentUserRoleLevel=parseInt(Ext.getCmp("currentUserRoleLevel_Id").getValue());
    var currentUserRoleShowLevel=parseInt(Ext.getCmp("currentUserRoleShowLevel_Id").getValue());
    var currentUserRoleFlag=parseInt(Ext.getCmp("currentUserRoleFlag_Id").getValue());
    
    
    Ext.getCmp("roleLevel_Id").setMinValue(currentUserRoleLevel+1);
    Ext.getCmp("roleLevel_Id").setValue(currentUserRoleLevel+1);
    
    Ext.getCmp("roleShowLevel_Id").setMinValue(currentUserRoleShowLevel+1);
    Ext.getCmp("roleShowLevel_Id").setValue(currentUserRoleShowLevel+1);
    
    if(currentUserRoleFlag==0){
    	Ext.getCmp("roleFlag_Id").setValue(0);
    	Ext.getCmp("roleFlagComboxfield_Id").setValue(0);
    	Ext.getCmp("roleFlagComboxfield_Id").setRawValue('否');
    	Ext.getCmp("roleFlagComboxfield_Id").disable();
    }
    
    Ext.getCmp("addFormrole_Id").show();
    Ext.getCmp("updateFormrole_Id").hide();
    return false;
    // Ext.Msg.alert("title", "add role Info!");
};

//del to action
function delroleInfo() {
    var role_panel = Ext.getCmp("RoleInfoGridPanel_Id");
    var role_model = role_panel.getSelectionModel();
    var _record = role_model.getSelection();
    // var _record = sm.getSelected();
    var delUrl = context + '/roleManagerController/doRoleBulkDelete'
    if (_record.length>0) {
        // 提示是否删除数据
    	var roleId=_record[0].data.roleId;
    	if(parseInt(roleId)>1){
    		Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
            Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
            Ext.Msg.confirm(cosog.string.yesdel, cosog.string.yesdeldata, function (btn) {
                if (btn == "yes") {
                    ExtDel_ObjectInfo("RoleInfoGridPanel_Id", _record,"roleId", delUrl);
                }
            });
    	}else{
    		Ext.Msg.alert(cosog.string.deleteCommand, '该角色不可删除!');
    	}
        
    } else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
}

function modifyroleInfo() {
	var role_panel = Ext.getCmp("RoleInfoGridPanel_Id");
    var role_model = role_panel.getSelectionModel();
    var _record = role_model.getSelection();
    if (_record.length>0) {
    	var roleUpdateInfoWindow = Ext.create("AP.view.role.RoleInfoWindow", {
            title: cosog.string.editRole
        });
        roleUpdateInfoWindow.show();
        Ext.getCmp("addFormrole_Id").hide();
        Ext.getCmp("updateFormrole_Id").show();
        SelectRoleDataAttrInfoGridPanel();
    }else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
    return false;
}

var grantRolePermission = function () {//授予角色模块权限
    var rightmodule_panel = Ext.getCmp("RightModuleTreeInfoGridPanel_Id");
    //var rightmodule_model = rightmodule_panel.getSelectionModel();
    var _record;
    var roleCode="";
    var roleId="";
    var roleLevel="";
    if(Ext.getCmp("RoleInfoGridPanel_Id")!=undefined){
    	var _record = Ext.getCmp("RoleInfoGridPanel_Id").getSelectionModel().getSelection();
        if(_record.length>0){
        	roleCode=_record[0].data.roleCode;
        	roleId=_record[0].data.roleId;
        	roleLevel=_record[0].data.roleLevel;
        }
    }
    if(parseInt(roleLevel)==1){//如果是超级管理员，授予所有模块权限
    	_record = rightmodule_panel.store.data.items;
    }else{
    	_record = rightmodule_panel.getChecked();
    }
    var addUrl = context + '/moduleShowRightManagerController/doModuleSaveOrUpdate'
        // 添加条件
    var addjson = [];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    var roleCode = Ext.getCmp("RightBottomRoleCodes_Id").getValue();
    var RightOldModuleIds_Id = Ext.getCmp("RightOldModuleIds_Id").getValue();
    if (!isNotVal(roleCode)) {
        Ext.Msg.alert(cosog.string.ts, cosog.string.pleaseChooseRole);
        return false
    }
    if (_record.length > 0) {
        Ext.Array.each(_record, function (name, index, countriesItSelf) {
            var md_ids = _record[index].get('mdId')
            addjson.push(md_ids);
            var matrix_value = "";
            /*for (var i = 0; i < 3; i++) {
						var matrix_ = Ext.getDom(md_ids + "&" + i);
						if (matrix_.checked) {
							matrix_value += "1,";
						} else {
							matrix_value += "0,";
						}

					}*/
            matrix_value = '0,0,0,';
            if (matrix_value != "" || matrix_value != null) {
                matrix_value = matrix_value.substring(0, matrix_value.length - 1);
            }
            matrixData += md_ids + ":" + matrix_value + "|";

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
                oldModuleIds: RightOldModuleIds_Id,
                roleId: roleId,
                matrixCodes: matrixCodes_
            },
            success: function (response) {
                var result = Ext.JSON.decode(response.responseText);
                if (result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucGrant + "</font>】" + _record.length + "" + cosog.string.jgModule + "。");
                }
                if (result.msg == false) {
                    Ext.Msg.alert('info', "<font color=red>SORRY！" + cosog.string.grandFail + "。</font>");
                }
                // 刷新Grid
                Ext.getCmp("RightModuleTreeInfoGridPanel_Id").getStore().load();
            },
            failure: function () {
                Ext.Msg.alert("warn", "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });

    } else {
        Ext.Msg.alert(cosog.string.ts, '<font color=blue>' + cosog.string.chooseGrantModule + '！</font>');
    }
    return false;
}