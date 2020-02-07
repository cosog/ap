Ext.define('AP.controller.device.PumpController', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'pumpPanel',
        selector: 'pumpPanel'
   }],
    init: function () {
        this.control({
            'pumpPanel > toolbar button[action=addpumpPanelAction]': {
                click: addPump
            },
            'pumpPanel > toolbar button[action=editpumpPanelAction]': {
                click: modifyPump
            },
            'pumpPanel > toolbar button[action=delpumpPanelAction]': {
                click: delectPump
            },
            'pumpPanel > toolbar button[action=savePumpGridDataAction]': {
                click: savePumpGridDataInfo
            },
            'pumpPanel': {
                itemdblclick: modifyPump
            }
        })
    }
});

// 窗体创建按钮事件
var SavePumpPanelSubmitBtnForm = function () {
    var SavePumpWindow = Ext.getCmp("pumpWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (SavePumpWindow.getForm().isValid()) {
        SavePumpWindow.getForm().submit({
            url: context + '/pumpController/addPump',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.sendServer,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('pumpWindow_Id').close();
                Ext.getCmp("pumpPanel_Id").getStore().load();
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
        Ext.Msg.alert(cosog.string.ts, "<font color=red>SORRY！" + cosog.string.validdata + "</font>。");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

// 窗体上的修改按钮事件
function UpdatePumpPanelSubmitBtnForm() {
    var UpdatePumpgWindowDataSubmitBtnFormId = Ext
        .getCmp("pumpWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (UpdatePumpgWindowDataSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("pumpWindow_Id").el.mask(cosog.string.updatewait).show();
        UpdatePumpgWindowDataSubmitBtnFormId.getForm().submit({
            url: context + '/pumpController/updatePump',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("pumpWindow_Id").getEl().unmask();
                Ext.getCmp('pumpWindow_Id').close();
                Ext.getCmp("pumpPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts,
                        "<font color=red>SORRY！</font>" + cosog.string.updatefail + "。");
                }
            },
            failure: function () {
                Ext.getCmp("pumpWindow_Id").getEl().unmask();
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });
    }
    return false;
};

// open win
function addPump() {
        var win_Obj = Ext.getCmp("pumpWindow_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var pumpWindow = Ext.create("AP.view.device.PumpWindow", {
            title: cosog.string.addPump
        });
        pumpWindow.show();
        Ext.getCmp("addpumpPanelLabelClassBtn_win_Id").show();
        Ext.getCmp("editpumpPanelLabelClassBtn_win_Id").hide()
            // Ext.Msg.alert("title", "add WellInfoWindow Info!");
    }
    // del to action
function delectPump() {
    var pumpPanel_panel = Ext.getCmp("pumpPanel_Id");
    var pumpPanel_model = pumpPanel_panel.getSelectionModel();
    var _record = pumpPanel_model.getSelection();
    // var _record = sm.getSelected();
    var delUrl = context + '/pumpController/delectPump'
    if (_record.length>0) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm(cosog.string.yesdel, cosog.string.yesdeldata, function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("pumpPanel_Id", _record, "jlbh", delUrl); // 第一个参数为面板的Id，选择的记录对象，当前删除对象的Id名称，url
            }
        });
    } else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
    // Ext.Msg.alert("title", "delete reservoirProperty Info!");
}

function modifyPump() {
	var pumpPanel_panel = Ext.getCmp("pumpPanel_Id");
    var pumpPanel_model = pumpPanel_panel.getSelectionModel();
    var _record = pumpPanel_model.getSelection();
    if (_record.length>0) {
    	var win_Obj = Ext.getCmp("pumpWindow_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var wellInfoWindow = Ext.create("AP.view.device.PumpWindow", {
            title: cosog.string.editPump
        });
        wellInfoWindow.show();
        Ext.getCmp("addpumpPanelLabelClassBtn_win_Id").hide();
        Ext.getCmp("editpumpPanelLabelClassBtn_win_Id").show();
        //Ext.getCmp('cjsj_Id').setValue(new Date());
        SelectpumpPanel();
    }else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
}
    // 复值
SelectpumpPanel = function () {
    var dataattr_row = Ext.getCmp("pumpPanel_Id").getSelectionModel().getSelection();
    var jlbh = dataattr_row[0].data.jlbh;
    var sccj = dataattr_row[0].data.sccj;
    var cybxh = dataattr_row[0].data.cybxh;
    var blx = dataattr_row[0].data.blx;
    var bjb = dataattr_row[0].data.bjb;
    var blxName = dataattr_row[0].data.blxName;
    var bjbName = dataattr_row[0].data.bjbName;
    var bj = dataattr_row[0].data.bj;
    var zsc = dataattr_row[0].data.zsc;

    Ext.getCmp('jlbh_Id').setValue(jlbh);
    Ext.getCmp('sccj_Id').setValue(sccj);
    Ext.getCmp('cybxh_Id').setValue(cybxh);
    Ext.getCmp('blx_Id').setValue(blx);
    Ext.getCmp('blx_Id1').setValue(blx);
    Ext.getCmp('blx_Id1').setRawValue(blxName);
    Ext.getCmp('bjb_Id').setValue(bjb);
    Ext.getCmp('bjb_Id1').setValue(bjb);

    Ext.getCmp('bjb_Id1').setRawValue(bjbName);
    Ext.getCmp('bj_Id').setValue(bj);
    Ext.getCmp('bj_Id').setRawValue(bj);
    Ext.getCmp('zsc_Id').setValue(zsc);

};

//保存表格变化数据
function savePumpGridDataInfo() {
	var gridpanel = Ext.getCmp("pumpPanel_Id");
	var store=Ext.getCmp("pumpPanel_Id").getStore();
	var m = store.getUpdatedRecords();
	var total=store.getCount();
	var jsonArray = [];
	for(var i=0;i<total;i++){
		var model=store.getAt(i);
		if(model.dirty){
			jsonArray.push(model.data);
		}
	}
    if (jsonArray.length>0) {
//    	Ext.each(m,function(item){
//    	jsonArray.push(item.data);
//    	});
    	Ext.Ajax.request({
    		method:'POST',
    		url:context + '/pumpController/savePumpEditerGridData',
    		success:function(response) {
    			Ext.MessageBox.alert("信息","更新成功",function(){
    				gridpanel.getStore().load();
    				gridpanel.getStore().modified = [];
    			});
    		},
    		failure:function(){
    			Ext.MessageBox.alert("错误","与后台联系的时候出了问题");
    		},
    		params: {
            	data: JSON.stringify(jsonArray)
            }
    	});   
    }else {
        //Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.noDataChange);
    }
    
    return false;
};