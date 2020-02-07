Ext.define('AP.controller.reservoirProperty.ReservoirPropertyInfoControl', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'reservoirPropertyInfoGridPanel',
        selector: 'reservoirPropertyInfoGridPanel'
   }],
    init: function () {
        this.control({
            'reservoirPropertyInfoGridPanel > toolbar button[action=addreservoirPropertyAction]': {
                click: addreservoirPropertyInfo
            },
            'reservoirPropertyInfoGridPanel > toolbar button[action=delreservoirPropertyAction]': {
                click: delreservoirPropertyInfo
            },
            'reservoirPropertyInfoGridPanel > toolbar button[action=editreservoirPropertyInfoAction]': {
                click: modifyreservoirPropertyInfo
            },
            'reservoirPropertyInfoGridPanel > toolbar button[action=saveReservoirPropertyGridDataAction]': {
                click: saveReservoirPropertyGridDataInfo
            },
            'reservoirPropertyInfoGridPanel': {
                itemdblclick: modifyreservoirPropertyInfo
            }
        })
    }
});

// 窗体创建按钮事件
var SavereservoirPropertyDataInfoSubmitBtnForm = function () {
    var saveDataAttrInfoWinFormId = Ext.getCmp("reservoirProperty_addwin_Id")
        .down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (saveDataAttrInfoWinFormId.getForm().isValid()) {
        saveDataAttrInfoWinFormId.getForm().submit({
            url: context + '/reservoirPropertyManagerController/doReservoirPropertyAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.sendServer,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('reservoirProperty_addwin_Id').close();
                Ext.getCmp("ReservoirPropertyInfoGridPanel_Id").getStore().load();
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
function UpdatereservoirPropertyDataInfoSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext
        .getCmp("reservoirProperty_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("reservoirProperty_addwin_Id").el.mask(cosog.string.updatewait)
            .show();
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/reservoirPropertyManagerController/doReservoirPropertyEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("reservoirProperty_addwin_Id").getEl().unmask();
                Ext.getCmp('reservoirProperty_addwin_Id').close();
                Ext.getCmp("ReservoirPropertyInfoGridPanel_Id").getStore()
                    .load();

                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts,
                        "<font color=red>SORRY！</font>" + cosog.string.updatefail + "。");
                }
            },
            failure: function () {
                Ext.getCmp("reservoirProperty_addwin_Id").getEl().unmask();
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });
    }
    return false;
};

// 复值
SelectreservoirPropertyDataAttrInfoGridPanel = function () {
    var dataattr_row = Ext.getCmp("ReservoirPropertyInfoGridPanel_Id")
        .getSelectionModel().getSelection();
    var jlbh = dataattr_row[0].data.jlbh;
    var yqcbh = dataattr_row[0].data.yqcbh;
    var yymd = dataattr_row[0].data.yymd;
    var resName = dataattr_row[0].data.resName;
    var smd = dataattr_row[0].data.smd;
    var trqxdmd = dataattr_row[0].data.trqxdmd;
    //var ysrjqyb = dataattr_row[0].data.ysrjqyb;
    var bhyl = dataattr_row[0].data.bhyl;
    //var dmtqyynd = dataattr_row[0].data.dmtqyynd;
    var yqcyl = dataattr_row[0].data.yqcyl;
    var yqczbsd = dataattr_row[0].data.yqczbsd;
    var yqczbwd = dataattr_row[0].data.yqczbwd;
    Ext.getCmp('jlbh_Id').setValue(jlbh);
    Ext.getCmp('yqcbh_Id').setValue(yqcbh);
    Ext.getCmp('yqcbh_Id1').setValue(yqcbh);
    Ext.getCmp('yqcbh_Id1').setRawValue(resName);
    Ext.getCmp('yymd_Id').setValue(yymd);
    Ext.getCmp('smd_Id').setValue(smd);
    Ext.getCmp('trqxdmd_Id').setValue(trqxdmd);
    //Ext.getCmp('ysrjqyb_Id').setValue(ysrjqyb);
    Ext.getCmp('bhyl_Id').setValue(bhyl);
    //Ext.getCmp('dmtqyynd_Id').setValue(dmtqyynd);

    Ext.getCmp('yqcyl_Id').setValue(yqcyl);
    Ext.getCmp('yqczbsd_Id').setValue(yqczbsd);
    Ext.getCmp('yqczbwd_Id').setValue(yqczbwd);
};

// open win
function addreservoirPropertyInfo() {
        var win_Obj = Ext.getCmp("reservoirProperty_addwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var reservoirPropertyInfoWindow = Ext.create(
            "AP.view.reservoirProperty.ReservoirPropertyInfoWindow", {
                title: cosog.string.addRespro
            });
        reservoirPropertyInfoWindow.show();
        Ext.getCmp("addFormreservoirProperty_Id").show();
        Ext.getCmp("updateFormreservoirProperty_Id").hide();

        // Ext.Msg.alert("title", "add reservoirProperty Info!");
    }
    // del to action
function delreservoirPropertyInfo() {
    var reservoirProperty_panel = Ext.getCmp("ReservoirPropertyInfoGridPanel_Id");
    var reservoirProperty_model = reservoirProperty_panel.getSelectionModel();
    var _record = reservoirProperty_model.getSelection();
    // var _record = sm.getSelected();
    var delUrl = context + '/reservoirPropertyManagerController/doReservoirPropertyBulkDelete'
    if (_record.length>0) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm(cosog.string.yesdel, cosog.string.yesdeldata, function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("ReservoirPropertyInfoGridPanel_Id",
                    _record, "jlbh", delUrl); //第一个参数为面板的Id，选择的记录对象，当前删除对象的Id名称，url
            }
        });
    } else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
    // Ext.Msg.alert("title", "delete reservoirProperty Info!");
}

function modifyreservoirPropertyInfo() {
	var reservoirProperty_panel = Ext.getCmp("ReservoirPropertyInfoGridPanel_Id");
    var reservoirProperty_model = reservoirProperty_panel.getSelectionModel();
    var _record = reservoirProperty_model.getSelection();
    if (_record.length>0) {
    	var win_Obj = Ext.getCmp("reservoirProperty_addwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var reservoirPropertyUpdateInfoWindow = Ext.create(
            "AP.view.reservoirProperty.ReservoirPropertyInfoWindow", {
                title: cosog.string.editRespro
            });
        reservoirPropertyUpdateInfoWindow.show();
        Ext.getCmp("addFormreservoirProperty_Id").hide();
        Ext.getCmp("updateFormreservoirProperty_Id").show();
        SelectreservoirPropertyDataAttrInfoGridPanel();
    }else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
};

//保存表格变化数据
function saveReservoirPropertyGridDataInfo() {
	var gridpanel = Ext.getCmp("ReservoirPropertyInfoGridPanel_Id");
	var store=Ext.getCmp("ReservoirPropertyInfoGridPanel_Id").getStore();
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
    		url:context + '/reservoirPropertyManagerController/saveReservoirPropertyGridData',
    		success:function(response) {
    			Ext.MessageBox.alert("信息","更新成功",function(){
    				gridpanel.getStore().load();
    				gridpanel.getStore().modified = []; 
    				if(mapHelperWell!=null){
    					mapHelperWell.clearOverlays();
    					SaveBackMapData(mapHelperWell,"well",m_BackDefaultZoomLevel);
    				}
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