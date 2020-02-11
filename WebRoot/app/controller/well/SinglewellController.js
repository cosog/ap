Ext.define('AP.controller.well.SinglewellController', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'singlewellPanel',
        selector: 'singlewellPanel'
   }],
    init: function () {
        this.control({
            'singlewellPanel > toolbar button[action=addreSinglewellAction]': {
                click: addSinglewell
            },
            'singlewellPanel > toolbar button[action=editreSinglewellAction]': {
                click: modifySinglewell
            },
            'singlewellPanel > toolbar button[action=delreSinglewellAction]': {
                click: delectSinglewell
            },
            'singlewellPanel': {
                itemdblclick: modifySinglewell
            }
        })
    }
});

// 窗体创建按钮事件
var SaveSinglewellSubmitBtnForm = function () {
    var SaveSinglewellWindow = Ext.getCmp("singlewellWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (SaveSinglewellWindow.getForm().isValid()) {
        SaveSinglewellWindow.getForm().submit({
            url: context + '/outputwellproductionController/addOutputwellproduction',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: '正在向服务器提交数据',
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('singlewellWindow_Id').close();
                Ext.getCmp("singlewellPanel_Id").getStore().load();
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
function UpdateSinglewellSubmitBtnForm() {
    var UpdateSinglewellgWindowDataSubmitBtnFormId = Ext
        .getCmp("singlewellWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (UpdateSinglewellgWindowDataSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("singlewellWindow_Id").el.mask('正在更新数据，请稍后...').show();
        UpdateSinglewellgWindowDataSubmitBtnFormId.getForm().submit({
            url: context + '/outputwellproductionController/updateOutputwellproduction',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("singlewellWindow_Id").getEl().unmask();
                Ext.getCmp('singlewellWindow_Id').close();
                Ext.getCmp("singlewellPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert('提示', "【<font color=blue>成功更新</font>】，数据信息。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert('提示',
                        "<font color=red>SORRY！</font>信息更新失败。");
                }
            },
            failure: function () {
                Ext.getCmp("singlewellWindow_Id").getEl().unmask();
                Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
            }
        });
    }
    return false;
};

// open win
function addSinglewell() {
        var win_Obj = Ext.getCmp("singlewellWindow_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var WellInfoWindow = Ext.create("AP.view.well.SinglewellWindow", {
            title: "创建单井实测产量数据"
        });
        WellInfoWindow.show();
        Ext.getCmp("addFromSinglewellWindowBtn_Id").show();
        Ext.getCmp("updateFromSinglewellWindowBtn_Id").hide()
            // Ext.Msg.alert("title", "add WellInfoWindow Info!");
    }
    // del to action
function delectSinglewell() {
    var singlewellPanel_panel = Ext.getCmp("singlewellPanel_Id");
    var singlewellPanel_model = singlewellPanel_panel.getSelectionModel();
    var _record = singlewellPanel_model.getSelection();
    // var _record = sm.getSelected();
    var delUrl = context + '/outputwellproductionController/deleteOutputwellproduction'
    if (_record) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm("是否要删除？", "是否要删除这些被选择的数据？", function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("singlewellPanel_Id", _record, "jlbh", delUrl); // 第一个参数为面板的Id，选择的记录对象，当前删除对象的Id名称，url
            }
        });
    } else {
        Ext.Msg.alert('删除操作', '您必须选择一行数据以便删除！');
    }
    // Ext.Msg.alert("title", "delete reservoirProperty Info!");
}