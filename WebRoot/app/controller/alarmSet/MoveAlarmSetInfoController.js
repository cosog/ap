Ext.define('AP.controller.alarmSet.MoveAlarmSetInfoController', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'moveAlarmSetInfoGridPanel',
        selector: 'moveAlarmSetInfoGridPanel'
   }],
    init: function () {
        this.control({
            'moveAlarmSetInfoGridPanel > toolbar button[action=addMoveAlarmSetAction]': {
                click: addMoveAlarmSet
            },
            'moveAlarmSetInfoGridPanel > toolbar button[action=editMoveAlarmSetInfoAction]': {
                click: modifyMoveAlarmSet
            },
            'moveAlarmSetInfoGridPanel > toolbar button[action=delMoveAlarmSetAction]': {
                click: delectMoveAlarmSet
            },
            'moveAlarmSetInfoGridPanel': {
                itemdblclick: modifyMoveAlarmSet
            }
        })
    }
});

// 窗体创建按钮事件
var SaveMoveAlarmSetSubmitBtnForm = function () {
    var SaveMoveAlarmSetWindow = Ext.getCmp("MoveAlarmSetWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (SaveMoveAlarmSetWindow.getForm().isValid()) {
        SaveMoveAlarmSetWindow.getForm().submit({
            url: context + '/MoveAlarmSetController/addMoveAlarmSet',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: '正在向服务器提交数据',
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('MoveAlarmSetWindow_Id').close();
                Ext.getCmp("MoveAlarmSet_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>成功创建</font>】，" + cosog.string.dataInfo + "");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts, "<font color=red>SORRY！</font>创建失败。");

                }
            },
            failure: function () {
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + "</font> 】：" + cosog.string.contactadmin + "！");
            }
        });
    } else {
        Ext.Msg.alert(cosog.string.ts, "<font color=red>SORRY！请先检查数据有效性,再次提交.</font>。");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

// 窗体上的修改按钮事件
function UpdateMoveAlarmSetSubmitBtnForm() {
    var UpdateMoveAlarmSetgWindowDataSubmitBtnFormId = Ext
        .getCmp("MoveAlarmSetInfoWindowwin_Id").down('form');
    if (UpdateMoveAlarmSetgWindowDataSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("MoveAlarmSetInfoWindowwin_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
        UpdateMoveAlarmSetgWindowDataSubmitBtnFormId.getForm().submit({
            url: context + '/moveAlarmSetManagerController/doMoveAlarmsSetEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("MoveAlarmSetInfoWindowwin_Id").getEl().unmask();
                Ext.getCmp('MoveAlarmSetInfoWindowwin_Id').close();
                Ext.getCmp("MoveAlarmSetInfoGridPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + loginUserLanguageResource.updateSuccessfully + "</font>】，" + cosog.string.dataInfo + "。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts,
                        "<font color=red>SORRY！</font>" + loginUserLanguageResource.updateFailure);
                }
            },
            failure: function () {
                Ext.getCmp("MoveAlarmSetInfoWindowwin_Id").getEl().unmask();
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });
    }
    return false;
};

// open win
function addMoveAlarmSet() {
        var win_Obj = Ext.getCmp("MoveAlarmSetWindow_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var MoveAlarmSetWindow = Ext.create("AP.view.device.MoveAlarmSetWindow", {
            title: "创建设备管理-抽油泵"
        });
        MoveAlarmSetWindow.show();
        Ext.getCmp("addMoveAlarmSetLabelClassBtn_win_Id").show();
        Ext.getCmp("editMoveAlarmSetLabelClassBtn_win_Id").hide()
            // Ext.Msg.alert("title", "add WellInfoWindow Info!");
    }
    // del to action
function delectMoveAlarmSet() {
    var MoveAlarmSet_panel = Ext.getCmp("MoveAlarmSet_Id");
    var MoveAlarmSet_model = MoveAlarmSet_panel.getSelectionModel();
    var _record = MoveAlarmSet_model.getSelection();
    // var _record = sm.getSelected();
    var delUrl = context + '/MoveAlarmSetController/delectMoveAlarmSet'
    if (_record) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "确定";
        Ext.MessageBox.msgButtons['no'].text = "取消";
        Ext.Msg.confirm("是否要删除？", "是否要删除这些被选择的数据？", function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("MoveAlarmSet_Id", _record, "jlbh", delUrl); // 第一个参数为面板的Id，选择的记录对象，当前删除对象的Id名称，url
            }
        });
    } else {
        Ext.Msg.alert('删除操作', '您必须选择一行数据以便删除！');
    }
    // Ext.Msg.alert("title", "delete reservoirProperty Info!");
}

function modifyMoveAlarmSet() {
        var win_Obj = Ext.getCmp("MoveAlarmSetInfoWindowwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var wellInfoWindow = Ext.create("AP.view.alarmSet.MoveAlarmSetInfoWindow", {
            title: cosog.string.editWaveAlarm
        });
        wellInfoWindow.show();
        Ext.getCmp("addFormMoveAlarmSet_Id").hide();
        Ext.getCmp("updateFormMoveAlarmSet_Id").show();
        SelectMoveAlarmSet();
        return false;
    }
    // 复值
SelectMoveAlarmSet = function () {
    var dataattr_row = Ext.getCmp("MoveAlarmSetInfoGridPanel_Id").getSelectionModel().getSelection();
    var jlbh = dataattr_row[0].data.jlbh;
    var bdmc = dataattr_row[0].data.bdmc;
    var bdfw = dataattr_row[0].data.bdfw;
    var bdbjlxmc = dataattr_row[0].data.bdbjlxmc;
    var bdbjlx = dataattr_row[0].data.bdbjlx;
    var bdbjjbmc = dataattr_row[0].data.bdbjjbmc;
    var bdbjjb = dataattr_row[0].data.bdbjjb;
    var ssgldwmc = dataattr_row[0].data.ssgldwmc;
    var ssgldw = dataattr_row[0].data.ssgldw;
    var bjbz = dataattr_row[0].data.bjbz;
    var bz = dataattr_row[0].data.bz;
    Ext.getCmp('jlbh_Id').setValue(jlbh);
    Ext.getCmp('bdmc_Id').setValue(bdmc);
    Ext.getCmp('bdfw_Id').setValue(bdfw);
    Ext.getCmp('bdbjlx_Id').setValue(bdbjlx);
    Ext.getCmp('bdbjlx_Id').setRawValue(bdbjlxmc);
    Ext.getCmp('bdbjjb_Id').setValue(bdbjjb);
    Ext.getCmp('bdbjjb_Id').setRawValue(bdbjjbmc);
    Ext.getCmp('bdssgldw_Id').setValue(ssgldw);
    Ext.getCmp('bdssgldw_Id').setRawValue(ssgldwmc);
    Ext.getCmp('bjbz_Id').setValue(bjbz);
    Ext.getCmp("bdbjlx_Id").readOnly = true;
    if (bjbz == 0) {
        Ext.getCmp('bjbz_Id').setRawValue(cosog.string.normal);
    } else {
        Ext.getCmp('bjbz_Id').setRawValue(cosog.string.alarm);
    }
    Ext.getCmp('bz_Id').setValue(bz);
};