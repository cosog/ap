Ext.define('AP.controller.module.ModuleInfoControl', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'moduleInfoTreeGridView',
        selector: 'moduleInfoTreeGridView'
   }],
    init: function () {
        this.control({
            'moduleInfoTreeGridView > toolbar button[action=addmoduleAction]': {
                click: addmoduleInfo
            },
            'moduleInfoTreeGridView > toolbar button[action=delmoduleAction]': {
                click: delmoduleInfo
            },
            'moduleInfoTreeGridView > toolbar button[action=editmoduleInfoAction]': {
                click: modifymoduleInfo
            },
            'moduleInfoTreeGridView': {
                itemdblclick: modifymoduleInfo
            }
        })
    }
});

// 窗体创建按钮事件
var SavemoduleDataInfoSubmitBtnForm = function () {
    var saveDataAttrInfoWinFormId = Ext.getCmp("module_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (saveDataAttrInfoWinFormId.getForm().isValid()) {
        saveDataAttrInfoWinFormId.getForm().submit({
            url: context + '/moduleManagerController/doModuleAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.sendServer,
            waitTitle: loginUserLanguageResource.wait,
            success: function (response, action) {
                Ext.getCmp('module_addwin_Id').close();
                Ext.getCmp("moduleInfoTreeGridView_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>SORRY！</font>" + loginUserLanguageResource.addFailure);

                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    }else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

// 窗体上的修改按钮事件
function UpdatemoduleDataInfoSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext.getCmp("module_addwin_Id")
        .down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("module_addwin_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/moduleManagerController/doModuleEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("module_addwin_Id").getEl().unmask();
                Ext.getCmp('module_addwin_Id').close();
                Ext.getCmp("moduleInfoTreeGridView_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>" + loginUserLanguageResource.updateSuccessfully + "</font>");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.tip,
                        "<font color=red>SORRY！</font>" + loginUserLanguageResource.updateFailure);
                }
            },
            failure: function () {
                Ext.getCmp("module_addwin_Id").getEl().unmask();
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    }else {
    	Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.required+"</font>");
    }
    return false;
};

// 复值
SelectmoduleDataAttrInfoGridPanel = function () {
    var dataattr_row = Ext.getCmp("moduleInfoTreeGridView_Id").getSelectionModel().getSelection();
    var mdId = dataattr_row[0].data.mdId;
    
    var mdName_zh_CN = dataattr_row[0].data.mdName_zh_CN;
    var mdName_en = dataattr_row[0].data.mdName_en;
    var mdName_ru = dataattr_row[0].data.mdName_ru;
    
    var mdShowname_zh_CN = dataattr_row[0].data.mdShowname_zh_CN;
    var mdShowname_en = dataattr_row[0].data.mdShowname_en;
    var mdShowname_ru = dataattr_row[0].data.mdShowname_ru;
    
    var mdCode = dataattr_row[0].data.mdCode;
    var mdUrl = dataattr_row[0].data.mdUrl;
    var mdControl = dataattr_row[0].data.mdControl;
    var mdIcon = dataattr_row[0].data.mdIcon;
    var mdType = dataattr_row[0].data.mdType;
    var mdTypeName = dataattr_row[0].data.mdTypeName;
    var mdSeq = dataattr_row[0].data.mdSeq;
    var mdParentid = dataattr_row[0].data.mdParentid;
    var moduleParentName = dataattr_row[0].parentNode.data.text;
    Ext.getCmp('md_Id').setValue(mdId);
    var parent_module_Id1 = Ext.getCmp('mdName_Parent_Id1');
    var parent_module_Id = Ext.getCmp('mdName_Parent_Id');
    if (mdParentid == 0) {
        moduleParentName = loginUserLanguageResource.rootNode;
    }
    parent_module_Id1.setValue(mdParentid);
    parent_module_Id1.setRawValue(moduleParentName);
    parent_module_Id.setValue(mdParentid);
    
    Ext.getCmp('mdName_zh_CN_Id').setValue(mdName_zh_CN);
    Ext.getCmp('mdName_en_Id').setValue(mdName_en);
    Ext.getCmp('mdName_ru_Id').setValue(mdName_ru);
    
    Ext.getCmp('mdShowname_zh_CN_Id').setValue(mdShowname_zh_CN);
    Ext.getCmp('mdShowname_en_Id').setValue(mdShowname_en);
    Ext.getCmp('mdShowname_ru_Id').setValue(mdShowname_ru);
    
    Ext.getCmp('mdCode_Id').setValue(mdCode);
    Ext.getCmp('mdUrl_Id').setValue(mdUrl);
    Ext.getCmp('mdControl_Id').setValue(mdControl);
    Ext.getCmp('mdIcon_Id').setValue(mdIcon);
    Ext.getCmp('mdType_Id').setValue(mdType);
    Ext.getCmp("mdType_Id").setRawValue(mdTypeName);
    Ext.getCmp('mdSeq_Id').setValue(mdSeq);

};
// open win
function addmoduleInfo() {
	var ModuleManagementModuleEditFlag=parseInt(Ext.getCmp("ModuleManagementModuleEditFlag").getValue());
    if(ModuleManagementModuleEditFlag==1){
    	var win_Obj = Ext.getCmp("module_addwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var moduleInfoWindow = Ext.create("AP.view.module.ModuleInfoWindow", {
            title: loginUserLanguageResource.addModule
        });
        moduleInfoWindow.show();
        Ext.getCmp("addFormmodule_Id").show();
        Ext.getCmp("updateFormmodule_Id").hide();
    }
}
    // del to action
function delmoduleInfo() {
	var ModuleManagementModuleEditFlag=parseInt(Ext.getCmp("ModuleManagementModuleEditFlag").getValue());
    if(ModuleManagementModuleEditFlag==1){
    	var module_panel = Ext.getCmp("moduleInfoTreeGridView_Id");
        var module_model = module_panel.getSelectionModel();
        var _record = module_model.getSelection();
        var delUrl = context + '/moduleManagerController/doModuleBulkDelete'
        if (_record.length>0) {
            // 提示是否删除数据
            Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
            Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.cancel;
            Ext.Msg.confirm(loginUserLanguageResource.confirmDelete, loginUserLanguageResource.confirmDeleteData, function (btn) {
                if (btn == "yes") {
                    ExtDel_ObjectInfo("moduleInfoTreeGridView_Id", _record,"mdId", delUrl);
                }
            });
        } else {
            Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
        }
    }
}

function modifymoduleInfo() {
	var ModuleManagementModuleEditFlag=parseInt(Ext.getCmp("ModuleManagementModuleEditFlag").getValue());
    if(ModuleManagementModuleEditFlag==1){
    	var module_panel = Ext.getCmp("moduleInfoTreeGridView_Id");
        var module_model = module_panel.getSelectionModel();
        var _record = module_model.getSelection();
        if (_record.length>0) {
        	var win_Obj = Ext.getCmp("module_addwin_Id")
            if (win_Obj != undefined) {
                win_Obj.destroy();
            }
            var moduleUpdateInfoWindow = Ext.create(
                "AP.view.module.ModuleInfoWindow", {
                    title: loginUserLanguageResource.editModule
                });
            moduleUpdateInfoWindow.show();
            Ext.getCmp("addFormmodule_Id").hide();
            Ext.getCmp("updateFormmodule_Id").show();
            SelectmoduleDataAttrInfoGridPanel();
        }else {
            Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
        }
    }
}