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
    var rightmodule_panel = Ext.getCmp("RoleInfoWindowRightModuleTreeInfoGridPanel_Id");
    var _record;
    var roleLevel=Ext.getCmp("roleLevel_Id").getValue();
    _record = rightmodule_panel.store.data.items;
    
    var righttab_panel = Ext.getCmp("RoleInfoWindowRightTabTreeInfoGridPanel_Id");
    var _tabRecord;
    if(parseInt(roleLevel)==1){//如果是超级管理员，授予所有模块权限
    	_tabRecord = righttab_panel.store.data.items;
    }else{
    	_tabRecord = righttab_panel.getChecked();
    }
    
    var rightLanguageTreeInfoGridPanel = Ext.getCmp("RoleInfoWindowRightLanguageTreeInfoGridPanel_Id");
    var selectedLanguages;
    if(parseInt(roleLevel)==1){//如果是超级管理员，授予所有模块权限
    	selectedLanguages = rightLanguageTreeInfoGridPanel.store.data.items;
    }else{
    	selectedLanguages = rightLanguageTreeInfoGridPanel.getChecked();
    }
    
    var addModule = [];
    var matrixData = "";
    Ext.Array.each(_record, function (name, index, countriesItSelf) {
        var checked=_record[index].get('viewFlagName');
        if(checked){
        	var md_ids = _record[index].get('mdId');
            var viewFlagName=_record[index].get('viewFlagName')?1:0;
            var editFlagName=_record[index].get('editFlagName')?1:0;
            var controlFlagName=_record[index].get('controlFlagName')?1:0;
            
            addModule.push(md_ids);
            var matrix_value = viewFlagName+","+editFlagName+","+controlFlagName;
            matrixData += md_ids + ":" + matrix_value + "|";
        }
    });
    if(addModule.length>0){
    	matrixData = matrixData.substring(0, matrixData.length - 1);
    }
    var matrixCodes_ = "" + matrixData;
    
    var addDeviceType = [];
    Ext.Array.each(_tabRecord, function (name, index, countriesItSelf) {
        var deviceTypeId = _tabRecord[index].get('deviceTypeId')
        addDeviceType.push(deviceTypeId);
    });
    
    var addLanguage = [];
    Ext.Array.each(selectedLanguages, function (name, index, countriesItSelf) {
        var languageId = selectedLanguages[index].get('languageId')
        addLanguage.push(languageId);
    });
    
    if(addModule.length==0){
    	Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
        return false;
    }
    if(addDeviceType.length==0){
    	Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
        return false;
    }
    
    if(addLanguage.length==0){
    	Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
        return false;
    }
    
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (saveDataAttrInfoWinFormId.getForm().isValid()) {
        saveDataAttrInfoWinFormId.getForm().submit({
            url: context + '/roleManagerController/doRoleAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.sendServer,
            waitTitle: loginUserLanguageResource.wait,
            success: function (response, action) {
                Ext.getCmp('role_addwin_Id').close();
                
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>");
                    Ext.getCmp("addRoleFlag_Id").setValue(1);
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>SORRY！</font>" + loginUserLanguageResource.addFailure);
                    Ext.getCmp("addRoleFlag_Id").setValue(0);
                }
                Ext.getCmp("RoleInfoGridPanel_Id").getStore().load();
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            },
            params: {
            	addModuleIds: addModule.join(","),
            	matrixCodes: matrixCodes_,
            	addDeviceTypeIds: addDeviceType.join(","),
            	addLanguageIds: addLanguage.join(",")
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

// 窗体上的修改按钮事件
function UpdateroleDataInfoSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext.getCmp("role_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("role_addwin_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/roleManagerController/doRoleEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("role_addwin_Id").getEl().unmask();
                Ext.getCmp('role_addwin_Id').close();
                Ext.getCmp("RoleInfoGridPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.updateSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip,
                        "<font color=red>SORRY！</font>" + loginUserLanguageResource.updateFailure);
                }
            },
            failure: function () {
                Ext.getCmp("role_addwin_Id").getEl().unmask();
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    return false;
};

// 复值
SelectRoleDataAttrInfoGridPanel = function () {
    var dataattr_row = Ext.getCmp("RoleInfoGridPanel_Id").getSelectionModel().getSelection();
    var roleId = dataattr_row[0].data.roleId;
    var roleName = dataattr_row[0].data.roleName;
    var roleLevel=dataattr_row[0].data.roleLevel;
    var showLevel=dataattr_row[0].data.showLevel;
    var remark=dataattr_row[0].data.remark;
    
    var currentUserRoleLevel=parseInt(Ext.getCmp("currentUserRoleLevel_Id").getValue());
    var currentUserRoleShowLevel=parseInt(Ext.getCmp("currentUserRoleShowLevel_Id").getValue());
    
    Ext.getCmp('role_Id').setValue(roleId);
    Ext.getCmp('role_Name_Id').setValue(roleName);
    Ext.getCmp('roleLevel_Id').setValue(roleLevel);
    Ext.getCmp('roleShowLevel_Id').setValue(showLevel);
    Ext.getCmp('roleRemark_Id').setValue(remark);
    
    
    if(currentUserRoleShowLevel>=showLevel){
    	Ext.getCmp('roleShowLevel_Id').disable();
    }else{
    	Ext.getCmp("roleShowLevel_Id").setMinValue(currentUserRoleShowLevel+1);
    }
    
    if(currentUserRoleLevel>=roleLevel){
    	Ext.getCmp('roleLevel_Id').disable();
    }else{
        Ext.getCmp("roleLevel_Id").setMinValue(currentUserRoleLevel+1);
    }
};

function addroleInfo() {
    var roleInfoWindow = Ext.create("AP.view.role.RoleInfoWindow", {
        title: loginUserLanguageResource.addRole
    });
    roleInfoWindow.show();
    
    var currentUserRoleLevel=parseInt(Ext.getCmp("currentUserRoleLevel_Id").getValue());
    var currentUserRoleShowLevel=parseInt(Ext.getCmp("currentUserRoleShowLevel_Id").getValue());
    var currentUserRoleVideoKeyEdit=parseInt(Ext.getCmp("currentUserRoleVideoKeyEdit_Id").getValue());
    
    Ext.getCmp("roleLevel_Id").setMinValue(currentUserRoleLevel+1);
    Ext.getCmp("roleLevel_Id").setValue(currentUserRoleLevel+1);
    
    Ext.getCmp("roleShowLevel_Id").setMinValue(currentUserRoleShowLevel+1);
    Ext.getCmp("roleShowLevel_Id").setValue(currentUserRoleShowLevel+1);
    
    
    if(currentUserRoleVideoKeyEdit==0){
    	Ext.getCmp("roleVideoKeyEdit_Id").setValue(0);
    	Ext.getCmp("roleVideoKeyEditComboxfield_Id").setValue(0);
    	Ext.getCmp("roleVideoKeyEditComboxfield_Id").setRawValue(loginUserLanguageResource.no);
    	Ext.getCmp("roleVideoKeyEditComboxfield_Id").disable();
    }
    
    Ext.getCmp("addFormrole_Id").show();
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
    		Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
            Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.cancel;
            Ext.Msg.confirm(loginUserLanguageResource.confirmDelete, loginUserLanguageResource.confirmDeleteData, function (btn) {
                if (btn == "yes") {
                    ExtDel_ObjectInfo("RoleInfoGridPanel_Id", _record,"roleId", delUrl);
                }
            });
    	}else{
    		Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.cannotDeleteLoginUserRole);
    	}
        
    } else {
        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
    }
}

function modifyroleInfo() {
	var role_panel = Ext.getCmp("RoleInfoGridPanel_Id");
    var role_model = role_panel.getSelectionModel();
    var _record = role_model.getSelection();
    if (_record.length>0) {
    	var roleUpdateInfoWindow = Ext.create("AP.view.role.RoleInfoWindow", {
            title: loginUserLanguageResource.editRole
        });
        roleUpdateInfoWindow.show();
        Ext.getCmp("addFormrole_Id").hide();
        Ext.getCmp("updateFormrole_Id").show();
        SelectRoleDataAttrInfoGridPanel();
    }else {
        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
    }
    return false;
}

var grantRolePermission = function () {//授予角色模块权限
    var rightmodule_panel = Ext.getCmp("RightModuleTreeInfoGridPanel_Id");
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
    _record = rightmodule_panel.store.data.items;
    var addUrl = context + '/moduleShowRightManagerController/doModuleSaveOrUpdate'
    var addjson = [];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    var roleCode = Ext.getCmp("RightBottomRoleCodes_Id").getValue();
    var RightOldModuleIds_Id = Ext.getCmp("RightOldModuleIds_Id").getValue();
    if (!isNotVal(roleCode)) {
        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.pleaseChooseRole);
        return false
    }
    Ext.Array.each(_record, function (name, index, countriesItSelf) {
        var checked=_record[index].get('viewFlagName');
        if(checked){
        	var md_ids = _record[index].get('mdId');
            var viewFlagName=_record[index].get('viewFlagName')?1:0;
            var editFlagName=_record[index].get('editFlagName')?1:0;
            var controlFlagName=_record[index].get('controlFlagName')?1:0;
            
            addjson.push(md_ids);
            var matrix_value = viewFlagName+","+editFlagName+","+controlFlagName;
            matrixData += md_ids + ":" + matrix_value + "|";
        }
    });
    if(addjson.length>0){
    	matrixData = matrixData.substring(0, matrixData.length - 1);
    }
    var addparamsId = "" + addjson.join(",");
    var matrixCodes_ = "" + matrixData;
    Ext.Ajax.request({
        url: addUrl,
        method: "POST",
        params: {
            paramsId: addparamsId,
            oldModuleIds: RightOldModuleIds_Id,
            roleId: roleId,
            matrixCodes: matrixCodes_
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (result.msg == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=blue>" + loginUserLanguageResource.grantSuccess + "</font>】" + addjson.length);
            }
            if (result.msg == false) {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>SORRY！" + loginUserLanguageResource.grantFailure + "</font>");
            }
            Ext.getCmp("RoleInfoGridPanel_Id").getStore().load();
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
        }
    });
    return false;
}

var grantRoleTabPermission = function () {//授予角色模块权限
    var righttab_panel = Ext.getCmp("RightTabTreeInfoGridPanel_Id");
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
    	_record = righttab_panel.store.data.items;
    }else{
    	_record = righttab_panel.getChecked();
    }
    var addUrl = context + '/moduleShowRightManagerController/doRoleDeviceTypeSaveOrUpdate'
        // 添加条件
    var addjson = [];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    var roleCode = Ext.getCmp("RightBottomRoleCodes_Id").getValue();
    var RightOldModuleIds_Id = Ext.getCmp("RightOldModuleIds_Id").getValue();
    if (!isNotVal(roleCode)) {
        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.pleaseChooseRole);
        return false
    }
    
    if (_record.length==0) {
        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
        return false
    }

    Ext.Array.each(_record, function (name, index, countriesItSelf) {
        var tab_ids = _record[index].get('deviceTypeId')
        addjson.push(tab_ids);
        var matrix_value = "";
        matrix_value = '0,0,0,';
        if (matrix_value != "" || matrix_value != null) {
            matrix_value = matrix_value.substring(0, matrix_value.length - 1);
        }
        matrixData += tab_ids + ":" + matrix_value + "|";

    });
    if(matrixData.length>0){
    	matrixData = matrixData.substring(0, matrixData.length - 1);
    }
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
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=blue>" + loginUserLanguageResource.grantSuccess + "</font>】" + _record.length);
            }
            if (result.msg == false) {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>SORRY！" + loginUserLanguageResource.grantFailure + "</font>");
            }
            // 刷新Grid
            Ext.getCmp("RoleInfoGridPanel_Id").getStore().load();
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
        }
    });
    return false;
}

var grantRoleLanguagePermission = function () {//授予角色模块权限
    var rightLanguageGridPanel = Ext.getCmp("RightLanguageTreeInfoGridPanel_Id");
    var _record;
    var roleCode="";
    var roleId="";
    var roleLevel="";
    if(Ext.getCmp("RoleInfoGridPanel_Id")!=undefined){
    	var selectedRole = Ext.getCmp("RoleInfoGridPanel_Id").getSelectionModel().getSelection();
        if(selectedRole.length>0){
        	roleCode=selectedRole[0].data.roleCode;
        	roleId=selectedRole[0].data.roleId;
        	roleLevel=selectedRole[0].data.roleLevel;
        }
    }
    if(parseInt(roleLevel)==1){//如果是超级管理员，授予所有模块权限
    	_record = rightLanguageGridPanel.store.data.items;
    }else{
    	_record = rightLanguageGridPanel.getChecked();
    }
    
    var addUrl = context + '/moduleShowRightManagerController/doRoleLanguageSaveOrUpdate'
        // 添加条件
    var addjson = [];
    var matrixData = "";
    var matrixDataArr = "";
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    
    if (!isNotVal(roleId)) {
        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.pleaseChooseRole);
        return false
    }
    
    if (_record.length==0) {
        Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.checkOne);
        return false
    }

    Ext.Array.each(_record, function (name, index, countriesItSelf) {
        var languageId = _record[index].get('languageId')
        addjson.push(languageId);
        var matrix_value = "";
        matrix_value = '0,0,0,';
        if (matrix_value != "" || matrix_value != null) {
            matrix_value = matrix_value.substring(0, matrix_value.length - 1);
        }
        matrixData += languageId + ":" + matrix_value + "|";

    });
    if(matrixData.length>0){
    	matrixData = matrixData.substring(0, matrixData.length - 1);
    }
    
    var addparamsId = "" + addjson.join(",");
    var matrixCodes_ = "" + matrixData;

    // AJAX提交方式
    Ext.Ajax.request({
        url: addUrl,
        method: "POST",
        // 提交参数
        params: {
            paramsId: addparamsId,
            roleId: roleId,
            matrixCodes: matrixCodes_
        },
        success: function (response) {
            var result = Ext.JSON.decode(response.responseText);
            if (result.msg == true) {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=blue>" + loginUserLanguageResource.grantSuccess + "</font>】" + _record.length);
            }
            if (result.msg == false) {
                Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>SORRY！" + loginUserLanguageResource.grantFailure + "</font>");
            }
            // 刷新Grid
            Ext.getCmp("RoleInfoGridPanel_Id").getStore().load();
        },
        failure: function () {
            Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
        }
    });
    return false;
}

function delRoleInfoByGridBtn(record) {
	var currentId=Ext.getCmp("currentUserRoleId_Id").getValue();
    if (parseInt(record.data.roleId)!=parseInt(currentId)){
    	var deleteRoleId=[];
    	deleteRoleId.push(record.data.roleId);
    	Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.cancel;
        Ext.Msg.confirm(loginUserLanguageResource.confirmDelete, loginUserLanguageResource.confirmDeleteData, function (btn) {
            if (btn == "yes") {
            	Ext.Ajax.request({
          			url : context + '/roleManagerController/doRoleBulkDelete',
          			method : "POST",
          			params : {
          				paramsId : deleteRoleId.join(",")
          			},
          			success : function(response) {
          				var result = Ext.JSON.decode(response.responseText);
          				if (result.flag == true) {
          					Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.deleteSuccessfully);
          				}
          				if (result.flag == false) {
          					Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.deleteFailure+"</font>");
          				}
          				Ext.getCmp("RoleInfoGridPanel_Id").getStore().load();
          			},
          			failure : function() {
          				Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
          			}
          		});
            }
        });

    } else {
    	Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.cannotDeleteLoginUserRole);
    }
}

function updateRoleInfoByGridBtn(record) {

    var roleId=record.get("roleId");
    var roleName=record.get("roleName");
    var roleLevel=record.get("roleLevel");
//    var roleFlagName=record.get("roleFlagName");
//    var roleReportEditName=record.get("roleReportEditName");
    var roleVideoKeyEditName=record.get("roleVideoKeyEditName");
    var roleLanguageEditName=record.get("roleLanguageEditName");
    var showLevel=record.get("showLevel");
    var remark=record.get("remark");
    Ext.Ajax.request({
		url : context + '/roleManagerController/updateRoleInfo',
		method : "POST",
		params : {
			roleId:roleId,
			roleName:roleName,
			roleLevel:roleLevel,
//			roleFlagName:roleFlagName,
//			roleReportEditName:roleReportEditName,
			roleVideoKeyEditName:roleVideoKeyEditName,
			roleLanguageEditName:roleLanguageEditName,
			showLevel:showLevel,
			remark:remark
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			if (result.success==true && result.flag == true) {
				Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
			}else if (result.success==true && result.flag == false) {
				Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
			}else {
				Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
			}
			Ext.getCmp("RoleInfoGridPanel_Id").getStore().load();
		},
		failure : function() {
			Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
		}
	});
}