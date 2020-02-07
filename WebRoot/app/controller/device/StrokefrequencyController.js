Ext.define('AP.controller.device.StrokefrequencyController', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'strokefrequencyPanel',
        selector: 'strokefrequencyPanel'
   }],
    init: function () {
        this.control({
            'strokefrequencyPanel > toolbar button[action=addStrokefrequencyAction]': {
                click: addStrokefrequency
            },
            'strokefrequencyPanel > toolbar button[action=editStrokefrequencyAction]': {
                click: modifyStrokefrequency
            },
            'strokefrequencyPanel > toolbar button[action=delStrokefrequencyAction]': {
                click: deleteStrokefrequency
            }
        })
    }
});

// 窗体创建按钮事件
var SaveStrokefrequencySubmitBtnForm = function () {
    var SaveStrokefrequencyWindow = Ext.getCmp("strokefrequencyWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (SaveStrokefrequencyWindow.getForm().isValid()) {
        SaveStrokefrequencyWindow.getForm().submit({
            url: context + '/strokefrequencyController/addStrokefrequency',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: '正在向服务器提交数据',
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('strokefrequencyWindow_Id').close();
                Ext.getCmp("strokefrequencyPanel_Id").getStore().load();
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
function UpdateStrokefrequencySubmitBtnForm() {
    var UpdateStrokefrequencySubmitBtnFormId = Ext
        .getCmp("strokefrequencyWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (UpdateStrokefrequencySubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("strokefrequencyWindow_Id").el.mask('正在更新数据，请稍后...').show();
        UpdateStrokefrequencySubmitBtnFormId.getForm().submit({
            url: context + '/strokefrequencyController/updateStrokefrequency',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("strokefrequencyWindow_Id").getEl().unmask();
                Ext.getCmp('strokefrequencyWindow_Id').close();
                Ext.getCmp("strokefrequencyPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert('提示', "【<font color=blue>成功更新</font>】，数据信息。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert('提示',
                        "<font color=red>SORRY！</font>信息更新失败。");
                }
            },
            failure: function () {
                Ext.getCmp("strokefrequencyWindow_Id").getEl().unmask();
                Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
            }
        });
    }
    return false;
};

// open win
function addStrokefrequency() {
        var win_Obj = Ext.getCmp("strokefrequencyWindow_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var StrokeWindow = Ext.create("AP.view.device.StrokefrequencyWindow", {
            title: "创建设备管理-抽油机冲次"
        });
        StrokeWindow.show();
        Ext.getCmp("addStrokefrequencyLabelClassBtn_win_Id").show();
        Ext.getCmp("editStrokefrequencyLabelClassBtn_win_Id").hide()
    }
    // del to action
function deleteStrokefrequency() {
    var StrokePanel_panel = Ext.getCmp("strokefrequencyPanel_Id");
    var StrokePanel_model = StrokePanel_panel.getSelectionModel();
    var _record = StrokePanel_model.getSelection();
    // var _record = sm.getSelected();
    var delUrl = context + '/strokefrequencyController/deleteStrokefrequency'
    if (_record) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm("是否要删除？", "是否要删除这些被选择的数据？", function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("strokefrequencyPanel_Id", _record, "jlbh", delUrl); // 第一个参数为面板的Id，选择的记录对象，当前删除对象的Id名称，url
            }
        });
    } else {
        Ext.Msg.alert('删除操作', '您必须选择一行数据以便删除！');
    }
    // Ext.Msg.alert("title", "delete reservoirProperty Info!");
}

function modifyStrokefrequency() {
        var win_Obj = Ext.getCmp("strokefrequencyWindow_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var wellInfoWindow = Ext.create("AP.view.device.StrokefrequencyWindow", {
            title: "修改设备管理-抽油机冲次"
        });
        wellInfoWindow.show();
        Ext.getCmp("addStrokefrequencyLabelClassBtn_win_Id").hide();
        Ext.getCmp("editStrokefrequencyLabelClassBtn_win_Id").show();
        SelectStrokefrequency();

    }
    // 复值
SelectStrokefrequency = function () {
    var dataattr_row = Ext.getCmp("strokefrequencyPanel_Id").getSelectionModel()
        .getSelection();
    var jlbh = dataattr_row[0].data.jlbh;
    var cyjbh = dataattr_row[0].data.cyjbh;
    var sccj = dataattr_row[0].data.sccj;
    var cyjxh = dataattr_row[0].data.cyjxh;
    var cc1 = dataattr_row[0].data.cc1;
    var djpdlzj = dataattr_row[0].data.djpdlzj;

    Ext.getCmp('jlbh_Id').setValue(jlbh);
    //Ext.getCmp('scc1j_Id').setValue(sccj);
    Ext.getCmp('cyjbh_Id').setValue(cyjbh);
    var sccj_Id = Ext.getCmp('sccj_Id');
    sccj_Id.setValue(cyjxh);
    sccj_Id.setRawValue(sccj);
    var cyjxh_Id = Ext.getCmp('cyjxh_Id');
    cyjxh_Id.setValue(cyjbh);
    cyjxh_Id.setRawValue(cyjxh);
    Ext.getCmp('cc1_Id').setValue(cc1);
    Ext.getCmp('djpdlzj_Id').setValue(djpdlzj);

};