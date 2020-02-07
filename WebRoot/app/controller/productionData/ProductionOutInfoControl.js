Ext.define('AP.controller.productionData.ProductionOutInfoControl', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'productionOutWellInfoPanel',
        selector: 'productionOutWellInfoPanel'
   }],
    init: function () {
        this.control({
            'productionOutWellInfoPanel > toolbar button[action=addProductionOutAction]': {
                click: addProductionOutInfo
            },
            'productionOutWellInfoPanel > toolbar button[action=delProductionOutAction]': {
                click: delProductionOutInfo
            },
            'productionOutWellInfoPanel > toolbar button[action=editProductionOutInfoAction]': {
                click: modifyProductionOutInfo
            },
            'productionOutWellInfoPanel > toolbar button[action=saveProductionOutGridDataAction]': {
                click: saveProductionOutGridDataInfo
            },
            'productionOutWellInfoPanel': {
                itemdblclick: modifyProductionOutInfo
            }
        })
    }
});
// 窗体创建按钮事件
var SaveProductionOutDataInfoSubmitBtnForm = function () {
    var saveDataAttrInfoWinFormId = Ext.getCmp("ProductionOut_addwin_Id")
        .down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (saveDataAttrInfoWinFormId.getForm().isValid()) {
        saveDataAttrInfoWinFormId.getForm().submit({
            url: context + '/productionDataController/doProductionOutAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: cosog.string.sendServer,
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('ProductionOut_addwin_Id').close();
                Ext.getCmp("ProductionOutInfoGridPanel_Id").getStore().load();
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
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

// 窗体上的修改按钮事件
function UpdateProductionOutDataInfoSubmitBtnForm() {
    var getUpdateDataInfoSubmitBtnFormId = Ext
        .getCmp("ProductionOut_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (getUpdateDataInfoSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("ProductionOut_addwin_Id").el.mask(cosog.string.updatewait).show();
        getUpdateDataInfoSubmitBtnFormId.getForm().submit({
            url: context + '/productionDataController/doProductionOutEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("ProductionOut_addwin_Id").getEl().unmask();
                Ext.getCmp('ProductionOut_addwin_Id').close();
                Ext.getCmp("ProductionOutInfoGridPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert(cosog.string.ts, "【<font color=blue>" + cosog.string.sucupate + "</font>】，" + cosog.string.dataInfo + "。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(cosog.string.ts,
                        "<font color=red>SORRY！</font>" + cosog.string.updatefail + "。");
                }
            },
            failure: function () {
                Ext.getCmp("ProductionOut_addwin_Id").getEl().unmask();
                Ext.Msg.alert(cosog.string.ts, "【<font color=red>" + cosog.string.execption + " </font>】：" + cosog.string.contactadmin + "！");
            }
        });
    }
    return false;
};

// 复值
SelectProductionOutDataAttrInfoGridPanel = function () {
    var dataattr_row = Ext.getCmp("ProductionOutInfoGridPanel_Id").getSelectionModel().getSelection();
    var jlbh = dataattr_row[0].data.jlbh;
    var jbh = dataattr_row[0].data.jbh;
    var jh = dataattr_row[0].data.jh;
    var jslx = dataattr_row[0].data.jslx;
    var jslxName = dataattr_row[0].data.jslxName;
    var qtlx = dataattr_row[0].data.qtlx;
    var qtlxName = dataattr_row[0].data.qtlxName;
    var sfpfcl = dataattr_row[0].data.sfpfcl;
    var sfpfclName = dataattr_row[0].data.sfpfclName;
    var ccjzt = dataattr_row[0].data.ccjzt;
    var ccjztName = dataattr_row[0].data.ccjztName;
    var hsl = dataattr_row[0].data.hsl;
    var yy = dataattr_row[0].data.yy;
    var ty = dataattr_row[0].data.ty;
    var hy = dataattr_row[0].data.hy;
    var dym = dataattr_row[0].data.dym;
    var bg = dataattr_row[0].data.bg;
    var jklw = dataattr_row[0].data.jklw;
    var scqyb = dataattr_row[0].data.scqyb;
    var sccj = dataattr_row[0].data.sccj;
    var cybxh = dataattr_row[0].data.cybxh;
    var ygnj = dataattr_row[0].data.ygnj;
    var yctgnj = dataattr_row[0].data.yctgnj;
    var yjgj = dataattr_row[0].data.yjgj;
    var yjgjb = dataattr_row[0].data.yjgjb;
    var yjgjbName = dataattr_row[0].data.yjgjbName;
    var yjgcd = dataattr_row[0].data.yjgcd;
    var ejgj = dataattr_row[0].data.ejgj;
    var ejgjb = dataattr_row[0].data.ejgjb;
    var ejgjbName = dataattr_row[0].data.ejgjbName;
    var ejgcd = dataattr_row[0].data.ejgcd;
    var sjgj = dataattr_row[0].data.sjgj;
    var sjgjb = dataattr_row[0].data.sjgjb;
    var sjgjbName = dataattr_row[0].data.sjgjbName;
    var sjgcd = dataattr_row[0].data.sjgcd;
    var rcql = dataattr_row[0].data.rcql;
    var mdzt = dataattr_row[0].data.mdzt;
    var mdztName = dataattr_row[0].data.mdztName;
    var jmb = dataattr_row[0].data.jmb;
    var bzgtbh = dataattr_row[0].data.bzgtbh;
    var bzdntbh = dataattr_row[0].data.bzdntbh;
    Ext.getCmp('jlbh_Id').setValue(jlbh);
    Ext.getCmp('jh_Id').setValue(jbh);
    var jh_Id1 = Ext.getCmp('jh_Id1');
    var data = jh_Id1.store.data.items;
    var json = "[{boxval:'" + jh + "',boxkey:" + jbh + "}];"
    if (data.length == 0) {
        jh_Id1.getStore().insert(0, json);
    }
    jh_Id1.setValue(jbh);
    jh_Id1.setRawValue(jh);
    Ext.getCmp('jslx_Id').setValue(jslx);
    Ext.getCmp('jslx_Id1').setValue(jslx);
    Ext.getCmp('jslx_Id1').setRawValue(jslxName);
    Ext.getCmp('qtlx_Id').setValue(qtlx);
    Ext.getCmp('qtlx_Id1').setValue(qtlx);
    Ext.getCmp('qtlx_Id1').setRawValue(qtlxName);
    Ext.getCmp('sfpfcl_Id').setValue(sfpfcl);
    Ext.getCmp('sfpfcl_Id1').setValue(sfpfcl);
    Ext.getCmp('sfpfcl_Id1').setRawValue(sfpfclName);
    Ext.getCmp('ccjzt_Id').setValue(ccjzt);
    Ext.getCmp('ccjzt_Id1').setValue(ccjzt);
    Ext.getCmp('ccjzt_Id1').setRawValue(ccjztName);
    Ext.getCmp('hsl_Id').setValue(hsl);
    Ext.getCmp('yy_Id').setValue(yy);
    Ext.getCmp('ty_Id').setValue(ty);
    Ext.getCmp('hy_Id').setValue(hy);
    Ext.getCmp('dym_Id').setValue(dym);
    Ext.getCmp('bg_Id').setValue(bg);
    Ext.getCmp('jklw_Id').setValue(jklw);
    Ext.getCmp('scqyb_Id').setValue(scqyb);
    Ext.getCmp('sccj_Id').setValue(sccj);
    Ext.getCmp('sccj_Id').setRawValue(sccj);
    Ext.getCmp('cybxh_Id').setValue(cybxh);
    Ext.getCmp('cybxh_Id').setRawValue(cybxh);
    Ext.getCmp('ygnj_Id').setValue(ygnj);
    Ext.getCmp('yctgnj_Id').setValue(yctgnj);
    Ext.getCmp('yjgj_Id').setValue(yjgj);
    Ext.getCmp('yjgj_Id1').setValue(yjgj);
    Ext.getCmp('yjgjb_Id').setValue(yjgjb);
    Ext.getCmp('yjgjb_Id1').setValue(yjgjb);
    Ext.getCmp('yjgjb_Id1').setValue(yjgjbName);
    Ext.getCmp('yjgcd_Id').setValue(yjgcd);
    Ext.getCmp('ejgj_Id').setValue(ejgj);
    Ext.getCmp('ejgj_Id1').setValue(ejgj);
    Ext.getCmp('ejgjb_Id').setValue(ejgjb);
    Ext.getCmp('ejgjb_Id1').setValue(ejgjb);
    Ext.getCmp('ejgjb_Id1').setValue(ejgjbName);
    Ext.getCmp('ejgcd_Id').setValue(ejgcd);
    Ext.getCmp('sjgj_Id').setValue(sjgj);
    Ext.getCmp('sjgj_Id1').setValue(sjgj);
    Ext.getCmp('sjgjb_Id').setValue(sjgjb);
    Ext.getCmp('sjgjb_Id1').setValue(sjgjb);
    Ext.getCmp('sjgjb_Id1').setValue(sjgjbName);
    Ext.getCmp('sjgcd_Id').setValue(sjgcd);
    if (!isNotVal(rcql)) {
        rcql = 0.0;
    }
    Ext.getCmp('rcql_Id').setValue(rcql);
    Ext.getCmp('mdzt_Id').setValue(mdzt);
    Ext.getCmp('mdzt_Id1').setValue(mdzt);
    Ext.getCmp('mdzt_Id1').setValue(mdztName);
    Ext.getCmp('jmb_Id').setValue(jmb);
    //Ext.getCmp('bzgtbh_Id').setValue(bzgtbh);
    //Ext.getCmp('bzdntbh_Id').setValue(bzdntbh);
    return false;
};

// open win
function addProductionOutInfo() {
        var win_Obj = Ext.getCmp("ProductionOut_addwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }

        var ProductionOutInfoWindow = Ext.create(
            "AP.view.productionData.ProductionOutInfoWindow", {
                title: cosog.string.addProductionOut
            });
        ProductionOutInfoWindow.show();

        Ext.getCmp("addFormProductionOut_Id").show();
        Ext.getCmp("updateFormProductionOut_Id").hide();
        return false;
    }
    // del to action
function delProductionOutInfo() {
    var ProductionOut_panel = Ext.getCmp("ProductionOutInfoGridPanel_Id");
    var ProductionOut_model = ProductionOut_panel.getSelectionModel();
    var _record = ProductionOut_model.getSelection();
    var delUrl = context + '/productionDataController/doProductionOutBulkDelete'
    if (_record.length>0) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm(cosog.string.yesdel, cosog.string.yesdeldata, function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("ProductionOutInfoGridPanel_Id",
                    _record, "jlbh", delUrl);
            }
        });
    } else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
    // Ext.Msg.alert("title", "delete ProductionOut Info!");
}

modifyProductionOutInfo = function () {
	var ProductionOut_panel = Ext.getCmp("ProductionOutInfoGridPanel_Id");
    var ProductionOut_model = ProductionOut_panel.getSelectionModel();
    var _record = ProductionOut_model.getSelection();
    if (_record.length>0) {
    	var win_Obj = Ext.getCmp("ProductionOut_addwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var ProductionOutUpdateInfoWindow = Ext.create(
            "AP.view.productionData.ProductionOutInfoWindow", {
                title: cosog.string.editProductionOut
            }).show();
        Ext.getCmp("jh_Id1").readOnly = true;
        SelectProductionOutDataAttrInfoGridPanel();
        Ext.getCmp("addFormProductionOut_Id").hide();
        Ext.getCmp("updateFormProductionOut_Id").show();
    } else {
        Ext.Msg.alert(cosog.string.deleteCommand, cosog.string.checkOne);
    }
};

//保存表格变化数据
function saveProductionOutGridDataInfo() {
	var ProductionOutInfoGridPanel = Ext.getCmp("ProductionOutInfoGridPanel_Id");
	var store=Ext.getCmp("ProductionOutInfoGridPanel_Id").getStore();
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
    		url:context + '/productionDataController/saveProductionDataEditerGridData',
    		success:function(response) {
    			Ext.MessageBox.alert("信息","更新成功",function(){
    				ProductionOutInfoGridPanel.getStore().load();
    				ProductionOutInfoGridPanel.getStore().modified = []; 
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
