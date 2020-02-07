Ext.define('AP.controller.device.StrokeController', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'strokePanel',
        selector: 'strokePanel'
   }],
    init: function () {
        this.control({
            'strokePanel > toolbar button[action=addStrokePanelAction]': {
                click: addStroke
            },
            'strokePanel > toolbar button[action=editStrokePanelAction]': {
                click: modifyStroke
            },
            'strokePanel > toolbar button[action=delStrokePanelAction]': {
                click: deleteStroke
            }
        })
    }
});

// 窗体创建按钮事件
var SaveStrokeSubmitBtnForm = function () {
    var SaveStrokeWindow = Ext.getCmp("strokeWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (SaveStrokeWindow.getForm().isValid()) {
        SaveStrokeWindow.getForm().submit({
            url: context + '/strokeController/addstroke',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: '正在向服务器提交数据',
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('strokeWindow_Id').close();
                Ext.getCmp("strokePanel_Id").getStore().load();
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
        Ext.Msg.alert('info', "<font color=red>SORRY！请先检查数据有效性,再次提交.</font>。");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

// 窗体上的修改按钮事件
function UpdateStrokeSubmitBtnForm() {
    var UpdateStrokeSubmitBtnFormId = Ext
        .getCmp("strokeWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (UpdateStrokeSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("strokeWindow_Id").el.mask('正在更新数据，请稍后...').show();
        UpdateStrokeSubmitBtnFormId.getForm().submit({
            url: context + '/strokeController/updatestroke',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("strokeWindow_Id").getEl().unmask();
                Ext.getCmp('strokeWindow_Id').close();
                Ext.getCmp("strokePanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert('提示', "【<font color=blue>成功更新</font>】，数据信息。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert('提示',
                        "<font color=red>SORRY！</font>信息更新失败。");
                }
            },
            failure: function () {
                Ext.getCmp("strokeWindow_Id").getEl().unmask();
                Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
            }
        });
    }
    return false;
};

// open win
function addStroke() {
        var win_Obj = Ext.getCmp("strokeWindow_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var StrokeWindow = Ext.create("AP.view.device.StrokeWindow", {
            title: "创建设备管理-抽油机冲程"
        });
        StrokeWindow.show();
        Ext.getCmp("addStrokeLabelClassBtn_win_Id").show();
        Ext.getCmp("editStrokeLabelClassBtn_win_Id").hide()
            // Ext.Msg.alert("title", "add WellInfoWindow Info!");
    }
    // del to action
function deleteStroke() {
    var StrokePanel_panel = Ext.getCmp("strokePanel_Id");
    var StrokePanel_model = StrokePanel_panel.getSelectionModel();
    var _record = StrokePanel_model.getSelection();
    // var _record = sm.getSelected();
    var delUrl = context + '/strokeController/deletestroke'
    if (_record) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm("是否要删除？", "是否要删除这些被选择的数据？", function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("strokePanel_Id", _record, "jlbh", delUrl); // 第一个参数为面板的Id，选择的记录对象，当前删除对象的Id名称，url
            }
        });
    } else {
        Ext.Msg.alert('删除操作', '您必须选择一行数据以便删除！');
    }
    // Ext.Msg.alert("title", "delete reservoirProperty Info!");
}

function modifyStroke() {
        var win_Obj = Ext.getCmp("strokeWindow_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var wellInfoWindow = Ext.create("AP.view.device.StrokeWindow", {
            title: "修改设备管理-抽油机冲程"
        });
        wellInfoWindow.show();
        Ext.getCmp("addStrokeLabelClassBtn_win_Id").hide();
        Ext.getCmp("editStrokeLabelClassBtn_win_Id").show();
        //Ext.getCmp('cjsj_Id').setValue(new Date());
        SelectStrokePanel();

    }
    // 复值
SelectStrokePanel = function () {
    var dataattr_row = Ext.getCmp("strokePanel_Id").getSelectionModel()
        .getSelection();
    var jlbh = dataattr_row[0].data.jlbh;
    var cyjbh = dataattr_row[0].data.cyjbh;
    var sccj = dataattr_row[0].data.sccj;
    var cyjxh = dataattr_row[0].data.cyjxh;
    var cc = dataattr_row[0].data.cc;
    var qbkj = dataattr_row[0].data.qbkj;
    var wzjnjys = dataattr_row[0].data.wzjnjys;

    Ext.getCmp('jlbh_Id').setValue(jlbh);
    //Ext.getCmp('sccj_Id').setValue(sccj);
    Ext.getCmp('cyjbh_Id').setValue(cyjbh);
    var sccj_Id = Ext.getCmp('sccj_Id');
    sccj_Id.setValue(cyjxh);
    sccj_Id.setRawValue(sccj);
    var cyjxh_Id = Ext.getCmp('cyjxh_Id');
    cyjxh_Id.setValue(cyjbh);
    cyjxh_Id.setRawValue(cyjxh);
    Ext.getCmp('cc_Id').setValue(cc);
    Ext.getCmp('qbkj_Id').setValue(qbkj);
    Ext.getCmp('wzjnjys_Id').setValue(wzjnjys);

};