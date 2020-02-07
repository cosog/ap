Ext.define('AP.controller.rfid.RfidInfoControl', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'rfidInfoGridPanel',
        selector: 'rfidInfoGridPanel'
   }],
    init: function () {
        this.control({
            'rfidInfoGridPanel > toolbar button[action=addrfidAction]': {
                click: addrfidInfo
            },
            'rfidInfoGridPanel > toolbar button[action=delrfidAction]': {
                click: delrfidInfo
            },
            'rfidInfoGridPanel > toolbar button[action=editrfidInfoAction]': {
                click: modifyrfidInfo
            },
            'rfidInfoGridPanel': {
                itemdblclick: modifyrfidInfo
            }
        })
    }
});

// 窗体创建按钮事件
var SaverfidDataInfoSubmitBtnForm = function () {
    var saveDataAttrInfoWinFormId = Ext.getCmp("rfid_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (saveDataAttrInfoWinFormId.getForm().isValid()) {
        saveDataAttrInfoWinFormId.getForm().submit({
            url: context + '/rfidInfoController/dorfidAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: '正在向服务器提交数据',
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('rfid_addwin_Id').close();
                Ext.getCmp("rfidInfoGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert('提示', "【<font color=blue>成功创建</font>】，数据信息");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert('提示', "<font color=red>SORRY！</font>创建失败。");

                }
            },
            failure: function () {
                Ext.Msg.alert("提示", "【<font color=red>异常抛出</font> 】：请与管理员联系！");
            }
        });
    } else {
        Ext.Msg.alert('提示', "<font color=red>SORRY！请先检查数据有效性,再次提交.</font>。");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

// 窗体上的修改按钮事件
function UpdaterfidDataInfoSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext.getCmp("rfid_addwin_Id")
        .down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("rfid_addwin_Id").el.mask('正在更新数据，请稍后...').show();
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/rfidInfoController/doRfidEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("rfid_addwin_Id").getEl().unmask();
                Ext.getCmp('rfid_addwin_Id').close();
                Ext.getCmp("rfidInfoGridPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert('提示', "【<font color=blue>成功更新</font>】，数据信息。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert('提示',
                        "<font color=red>SORRY！</font>信息更新失败。");
                }
            },
            failure: function () {
                Ext.getCmp("rfid_addwin_Id").getEl().unmask();
                Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
            }
        });
    }
    return false;
};

// 复值
SelectrfidDataAttrInfoGridPanel = function () {
    var dataattr_row = Ext.getCmp("rfidInfoGridPanel_Id").getSelectionModel()
        .getSelection();
    var jlbh = dataattr_row[0].data.jlbh;
    var rfidkh = dataattr_row[0].data.rfidkh;
    var ygbh = dataattr_row[0].data.ygbh;
    var ygxm = dataattr_row[0].data.ygxm;
    var xb = dataattr_row[0].data.xb;
    var bmbh = dataattr_row[0].data.bmbh;
    var bmmc = dataattr_row[0].data.bmmc;
    Ext.getCmp('jlbh_Id').setValue(jlbh);
    Ext.getCmp('rfidkh_Id').setValue(rfidkh);
    Ext.getCmp('ygbh_Id').setValue(ygbh);
    Ext.getCmp('ygxm_Id').setValue(ygxm);
    Ext.getCmp('xb_Id').setValue(xb);
    Ext.getCmp('bmbh_Id').setValue(bmbh);
    Ext.getCmp('bmmc_Id').setValue(bmmc);
};

// open win
function addrfidInfo() {
        var rfidInfoWindow = Ext.create("AP.view.rfid.RfidInfoWindow", {
            title: "创建RFID基本信息"
        });
        rfidInfoWindow.show();
        Ext.getCmp("addFormrfid_Id").show();
        Ext.getCmp("updateFormrfid_Id").hide();
        return false;
        // Ext.Msg.alert("title", "add rfid Info!");
    }
    // del to action
function delrfidInfo() {
    var rfid_panel = Ext.getCmp("rfidInfoGridPanel_Id");
    var rfid_model = rfid_panel.getSelectionModel();
    var _record = rfid_model.getSelection();
    // var _record = sm.getSelected();
    var delUrl = context + '/rfidInfoController/doRfidBulkDelete'
    if (_record) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm("是否要删除？", "是否要删除这些被选择的数据？", function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("rfidInfoGridPanel_Id", _record,
                    "jlbh", delUrl);
            }
        });
    } else {
        Ext.Msg.alert('删除操作', '您必须选择一行数据以便删除！');
    }
}

modifyrfidInfo = function () {
    var rfidUpdateInfoWindow = Ext.create("AP.view.rfid.RfidInfoWindow", {
        title: "修改RFID基本信息"
    });
    rfidUpdateInfoWindow.show();
    Ext.getCmp("addFormrfid_Id").hide();
    Ext.getCmp("updateFormrfid_Id").show();
    SelectrfidDataAttrInfoGridPanel();
    return false;

}