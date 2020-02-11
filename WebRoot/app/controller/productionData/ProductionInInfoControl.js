Ext.define('AP.controller.productionData.ProductionInInfoControl', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'productionInInfoGridPanel',
        selector: 'productionInInfoGridPanel'
   }],
    init: function () {
        this.control({
            'productionInInfoGridPanel > toolbar button[action=addProductionInAction]': {
                click: addProductionInInfo
            },
            'productionInInfoGridPanel > toolbar button[action=delProductionInAction]': {
                click: delProductionInInfo
            },
            'productionInInfoGridPanel > toolbar button[action=editProductionInInfoAction]': {
                click: modifyProductionInInfo
            },
            'productionInInfoGridPanel': {
                itemdblclick: modifyProductionInInfo
            }
        })
    }
});
// 窗体创建按钮事件
var SaveProductionInDataInfoSubmitBtnForm = function () {
    var saveDataAttrInfoWinFormId = Ext.getCmp("ProductionIn_addwin_Id")
        .down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (saveDataAttrInfoWinFormId.getForm().isValid()) {
        saveDataAttrInfoWinFormId.getForm().submit({
            url: context + '/productionInDataController/doProductionInAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.sendServer,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('ProductionIn_addwin_Id').close();
                Ext.getCmp("ProductionInInfoGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.success + "</font>】，" + cosog.string.datainfo + "");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts, "<font color=red>SORRY！</font>" + cosog.string.failInfo + "。");

                }
            },
            failure: function () {
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font> 】：" + cosog.string.contactadmin + "！");
            }
        });
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

// 窗体上的修改按钮事件
function UpdateProductionInDataInfoSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext.getCmp("ProductionIn_addwin_Id")
        .down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("ProductionIn_addwin_Id").el.mask(cosog.string.updatewait).show();
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/productionInDataController/doProductionInEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("ProductionIn_addwin_Id").getEl().unmask();
                Ext.getCmp('ProductionIn_addwin_Id').close();
                Ext.getCmp("ProductionInInfoGridPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts,
                        "<font color=red>SORRY！</font>" + cosog.string.updatefail + "。");
                }
            },
            failure: function () {
                Ext.getCmp("ProductionIn_addwin_Id").getEl().unmask();
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });
    }
    return false;
};

// open win
function addProductionInInfo() {
        var win_Obj = Ext.getCmp("ProductionIn_addwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }

        var ProductionInInfoWindow = Ext.create(
            "AP.view.productionData.ProductionInInfoWindow", {
                title: cosog.string.editproductionIn
            });
        ProductionInInfoWindow.show();
        Ext.getCmp("addFormProductionIn_Id").show();
        Ext.getCmp("updateFormProductionIn_Id").hide();
    }
    // del to action
function delProductionInInfo() {
    var ProductionIn_panel = Ext.getCmp("ProductionInInfoGridPanel_Id");
    var ProductionIn_model = ProductionIn_panel.getSelectionModel();
    var _record = ProductionIn_model.getSelection();
    // var _record = sm.getSelected();
    var delUrl = context + '/productionInDataController/doProductionInBulkDelete'
    if (_record) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm(cosog.string.yesdel, cosog.string.yesdeldata, function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("ProductionInInfoGridPanel_Id",
                    _record, "jlbh", delUrl);
            }
        });
    } else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
    // Ext.Msg.alert("title", "delete ProductionIn Info!");
}