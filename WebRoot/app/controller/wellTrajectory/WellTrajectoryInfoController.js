Ext.define('AP.controller.wellTrajectory.WellTrajectoryInfoController', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'welltrajectoryInfoPanel',
        selector: 'welltrajectoryInfoPanel'
   }],
    init: function () {
        this.control({
            'welltrajectoryInfoPanel > toolbar button[action=addrewelltrajectoryAction]': {
                click: addwelltrajectory
            },
            'welltrajectoryInfoPanel > toolbar button[action=editrewelltrajectoryInfoAction]': {
                click: modifywelltrajectory
            },
            'welltrajectoryInfoPanel > toolbar button[action=delrewelltrajectoryAction]': {
                click: delectwelltrajectory
            }
        })
    }
});

// 窗体创建按钮事件
var SavewelltrajectoryWindowSubmitBtnForm = function () {
    var SavewelltrajectoryWindow = Ext.getCmp("welltrajectoryWindow_addwin_Id")
        .down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (SavewelltrajectoryWindow.getForm().isValid()) {
        SavewelltrajectoryWindow.getForm().submit({
            url: context + '/wellTrajectoryController/doWellTrajectoryAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.sendServer,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('welltrajectoryWindow_addwin_Id').close();
                Ext.getCmp("welltrajectoryPanel_Id").getStore().load();
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
function UpdatewelltrajectoryWindowSubmitBtnForm() {
    var getUpdateDataSubmitBtnFormId = Ext
        .getCmp("welltrajectoryWindow_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (getUpdateDataSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("welltrajectoryWindow_addwin_Id").el.mask(cosog.string.updatewait)
            .show();
        getUpdateDataSubmitBtnFormId.getForm().submit({
            url: context + '/wellTrajectoryController/doWellTrajectoryEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("welltrajectoryWindow_addwin_Id").getEl().unmask();
                Ext.getCmp('welltrajectoryWindow_addwin_Id').close();
                Ext.getCmp("welltrajectoryPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.datainfo + "。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts,
                        "<font color=red>SORRY！</font>" + cosog.string.updatefail + "。");
                }
            },
            failure: function () {
                Ext.getCmp("welltrajectoryWindow_addwin_Id").getEl().unmask();
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });
    }
    return false;
};

// open win
function addwelltrajectory() {
        var win_Obj = Ext.getCmp("welltrajectoryWindow_addwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var welltrajectoryWindow = Ext.create(
            "AP.view.wellTrajectory.WellTrajectoryInfoWindow", {
                title: cosog.string.addWellTraject
            });
        welltrajectoryWindow.show();
        var save_btn = Ext.getCmp("addFromwelltrajectoryWindowBtn_Id");
        save_btn.show();
        var jh_ = Ext.getCmp("WellTrajectoryadd_Id").getValue();
        var wellTrajectoryaddjh_Id = Ext.getCmp("wellTrajectoryaddjh_Id");
        wellTrajectoryaddjh_Id.setValue(jh_);
        wellTrajectoryaddjh_Id.setRawValue(jh_);
        var save_update_Btn = Ext.getCmp("updateFromwelltrajectoryWindowBtn_Id");
        save_update_Btn.hide()
            // Ext.Msg.alert("title", "add welltrajectoryWindow Info!");
    }
    // del to action
function delectwelltrajectory() {
    var wellPanel_panel = Ext.getCmp("welltrajectoryPanel_Id");
    var wellPanel_model = wellPanel_panel.getSelectionModel();
    var _record = wellPanel_model.getSelection();
    if (_record.length>0) {
        // 提示是否删除数据
    	var jbh_ = _record[0].get('jbh');
    	var delUrl = context + '/wellTrajectoryController/doWellTrajectoryBulkDelete?jbh=' + jbh_;
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm(cosog.string.yesdel, cosog.string.yesdeldata, function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("welltrajectoryPanel_Id", _record,
                    "jlbh", delUrl); // 第一个参数为面板的Id，选择的记录对象，当前删除对象的Id名称，url
            }
        });
    } else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
}

function modifywelltrajectory() {
	var wellPanel_panel = Ext.getCmp("welltrajectoryPanel_Id");
    var wellPanel_model = wellPanel_panel.getSelectionModel();
    var _record = wellPanel_model.getSelection();
    if (_record.length>0) {
    	var win_Obj = Ext.getCmp("welltrajectoryWindow_addwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var welltrajectoryWindow = Ext.create(
            "AP.view.wellTrajectory.WellTrajectoryInfoWindow", {
                title: cosog.string.addWellTraject
            });
        welltrajectoryWindow.show();
        Ext.getCmp("addFromwelltrajectoryWindowBtn_Id").hide();
        Ext.getCmp("updateFromwelltrajectoryWindowBtn_Id").show();
        SelectwelltrajectoryInfoPanel();
    } else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
}
    // 复值
SelectwelltrajectoryInfoPanel = function () {
    var dataattr_row = Ext.getCmp("welltrajectoryPanel_Id").getSelectionModel()
        .getSelection();
    var jlbh = dataattr_row[0].data.jlbh;
    var jh = dataattr_row[0].data.jh;
    var clsd = dataattr_row[0].data.clsd;
    var czsd = dataattr_row[0].data.czsd;
    var jxj = dataattr_row[0].data.jxj;
    var jbh = dataattr_row[0].data.jbh;
    var fwj = dataattr_row[0].data.fwj;
    Ext.getCmp('jlbh_Id').setValue(jlbh);
    Ext.getCmp('wellTrajectoryaddjh_Id').setValue(jh);
    Ext.getCmp('wellTrajectoryaddjh_Id').setRawValue(jh);
    Ext.getCmp('jbh_Id').setValue(jbh);
    Ext.getCmp('clsd_Id').setValue(clsd);
    Ext.getCmp('czsd_Id').setValue(czsd);
    Ext.getCmp('jxj_Id').setValue(jxj);
    Ext.getCmp('fwj_Id').setValue(fwj);
};