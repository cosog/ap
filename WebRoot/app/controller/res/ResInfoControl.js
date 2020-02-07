Ext.define('AP.controller.res.ResInfoControl', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'resHeadInfoGridPanelView',
        selector: 'resHeadInfoGridPanelView'
   }],
    init: function () {
        this.control({
            'resHeadInfoGridPanelView > toolbar button[action=addresAction]': {
                click: addresInfo
            },
            'resHeadInfoGridPanelView > toolbar button[action=delresAction]': {
                click: delresInfo
            },
            'resHeadInfoGridPanelView > toolbar button[action=editresInfoAction]': {
                click: modifyresInfo
            },
            'resHeadInfoGridPanelView': {
                itemdblclick: modifyresInfo
            }
        })
    }
});
// 窗体创建按钮事件
var SaveresDataInfoSubmitBtnForm = function () {
    var saveDataAttrInfoWinFormId = Ext.getCmp("res_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (saveDataAttrInfoWinFormId.getForm().isValid()) {
        saveDataAttrInfoWinFormId.getForm().submit({
            url: context + '/resManagerController/doResAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.sendServer,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('res_addwin_Id').close();
                Ext.getCmp("ResHeadInfoGridPanel_Id").getStore().load();
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

// 窗体上的修改按钮事件
function UpdateresDataInfoSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext.getCmp("res_addwin_Id")
        .down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("res_addwin_Id").el.mask(cosog.string.updatewait).show();
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/resManagerController/doResEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("res_addwin_Id").getEl().unmask();
                Ext.getCmp('res_addwin_Id').close();
                Ext.getCmp("ResHeadInfoGridPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts,
                        "<font color=red>SORRY！</font>" + cosog.string.updatefail + "。");
                }
            },
            failure: function () {
                Ext.getCmp("res_addwin_Id").getEl().unmask();
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });
    }
    return false;
};

// 复值
SelectresDataAttrInfoGridPanel = function () {
    var dataattr_row = Ext.getCmp("ResHeadInfoGridPanel_Id").getSelectionModel()
        .getSelection();
    var resId = dataattr_row[0].data.resId;
    var resName = dataattr_row[0].data.text;
    var resMemo = dataattr_row[0].data.resMemo;
    var resLevel = dataattr_row[0].data.resLevel;
    var resCode = dataattr_row[0].data.resCode;
    var resParent = dataattr_row[0].data.resParent;
    var resParentName = dataattr_row[0].parentNode.data.text;
    if (resParent == '0') {
        resParentName = cosog.string.root;
    }
    var resSeq = dataattr_row[0].data.resSeq;
    Ext.getCmp('resres_Id').setValue(resId);
    Ext.getCmp('resLevel_Id').setValue(resLevel);
    Ext.getCmp('resLevel_Id1').setValue(resLevel);
    var levelText = judgeresType(resLevel);
    Ext.getCmp("resLevel_Id1").setRawValue(levelText);
    var resName_Parent_Id = Ext.getCmp('resName_Parent_Id1');
    resName_Parent_Id.setValue(resParent);
    resName_Parent_Id.setRawValue(resParentName);
    Ext.getCmp('resName_Parent_Id').setValue(resParent);
    Ext.getCmp('resName_Id').setValue(resName);
    Ext.getCmp('resCode_Id').setValue(resCode);
    Ext.getCmp('resMemo_Id').setValue(resMemo);
    Ext.getCmp('resSeq_Id').setValue(resSeq);

};
var judgeresType = function (value) {
        var rawValue = cosog.string.resOne;
        if (value == '1') {
            rawValue = cosog.string.resOne;
        } else if (value == '2') {
            rawValue = cosog.string.resTwo;
        } else if (value == '3') {
            rawValue = cosog.string.resThree;
        } else if (value == '4') {
            rawValue = cosog.string.resFour;
        } else if (value == "5") {
            rawValue = cosog.string.resFive;
        }
        return rawValue;
    }
    // open win
function addresInfo() {
        var win_Obj = Ext.getCmp("res_addwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var resInfoWindow = Ext.create("AP.view.res.ResInfoWindow", {
            title: cosog.string.addRes
        });
        resInfoWindow.show();
        var resName_Parent_Id = Ext.getCmp('resName_Parent_Id1').getValue();
        if (resName_Parent_Id == null) {
            resName_Parent_Id = "0";
        }
        Ext.getCmp("addresForm_Id").getForm().reset();
        Ext.getCmp("addFormres_Id").show();
        Ext.getCmp("updateFormres_Id").hide();

        // Ext.Msg.alert("title", "add res Info!");
    }
    // del to action
function delresInfo() {
    var res_panel = Ext.getCmp("ResHeadInfoGridPanel_Id");
    var res_model = res_panel.getSelectionModel();
    var _record = res_model.getSelection();
    // var _record = sm.getSelected();
    var delUrl = context + '/resManagerController/doResBulkDelete'
    if (_record.length>0) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm(cosog.string.yesdel, cosog.string.yesdeldata, function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("ResHeadInfoGridPanel_Id", _record,
                    "resId", delUrl);
            }
        });
    } else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
    // Ext.Msg.alert("title", "delete res Info!");
}

function modifyresInfo() {
	var res_panel = Ext.getCmp("ResHeadInfoGridPanel_Id");
    var res_model = res_panel.getSelectionModel();
    var _record = res_model.getSelection();
    if (_record.length>0) {
    	var win_Obj = Ext.getCmp("res_addwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var resUpdateInfoWindow = Ext.create("AP.view.res.ResInfoWindow", {
            title: cosog.string.editRes
        });
        resUpdateInfoWindow.show();
        Ext.getCmp("addFormres_Id").hide();
        Ext.getCmp("updateFormres_Id").show();
        SelectresDataAttrInfoGridPanel();
    }else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
}