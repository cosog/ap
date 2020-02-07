Ext.define('AP.controller.device.PumpingunitController', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'pumpingunitPanel',
        selector: 'pumpingunitPanel'
   }],
    init: function () {
        this.control({
            'pumpingunitPanel > toolbar button[action=addPumpingunitPanelAction]': {
                click: addPumpingunit
            },
            'pumpingunitPanel > toolbar button[action=editPumpingunitPanelAction]': {
                click: modifyPumpingunit
            },
            'pumpingunitPanel > toolbar button[action=delPumpingunitPanelAction]': {
                click: delectPumpingunit
            },
            'pumpingunitPanel > toolbar button[action=savePumpingUnitGridDataAction]': {
                click: savePumpingUnitGridData
            }
        })
    }
});

// 窗体创建按钮事件
var SavePumpingunitSubmitBtnForm = function () {
    var SavePumpingunitWindow = Ext.getCmp("pumpingunitWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (SavePumpingunitWindow.getForm().isValid()) {
        SavePumpingunitWindow.getForm().submit({
            url: context + '/pumpingunitController/addPumpingunit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: '正在向服务器提交数据',
            waitTitle: 'Please Wait...',
            success: function (response, action) {
                Ext.getCmp('pumpingunitWindow_Id').close();
                Ext.getCmp("pumpingunitPanel_Id").getStore().load();
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
function UpdatePumpingunitSubmitBtnForm() {
    var UpdatePumpingunitSubmitBtnFormId = Ext
        .getCmp("pumpingunitWindow_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
    if (UpdatePumpingunitSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("pumpingunitWindow_Id").el.mask('正在更新数据，请稍后...').show();
        UpdatePumpingunitSubmitBtnFormId.getForm().submit({
            url: context + '/pumpingunitController/updatePumpingunit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("pumpingunitWindow_Id").getEl().unmask();
                Ext.getCmp('pumpingunitWindow_Id').close();
                Ext.getCmp("pumpingunitPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert('提示', "【<font color=blue>成功更新</font>】，数据信息。");
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert('提示',
                        "<font color=red>SORRY！</font>信息更新失败。");
                }
            },
            failure: function () {
                Ext.getCmp("pumpingunitWindow_Id").getEl().unmask();
                Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
            }
        });
    }
    return false;
};

// open win
function addPumpingunit() {
        var win_Obj = Ext.getCmp("pumpingunitWindow_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var pumpWindow = Ext.create("AP.view.device.PumpingunitWindow", {
            title: "创建设备管理-抽油机"
        });
        pumpWindow.show();
        Ext.getCmp("addPumpingunitLabelClassBtn_win_Id").show();
        Ext.getCmp("editPumpingunitLabelClassBtn_win_Id").hide()
            // Ext.Msg.alert("title", "add WellInfoWindow Info!");
    }
    // del to action
function delectPumpingunit() {
    var pumpPanel_panel = Ext.getCmp("pumpingunitGrid_Id");
    var pumpPanel_model = pumpPanel_panel.getSelectionModel();
    var _record = pumpPanel_model.getSelection();
    var delUrl = context + '/pumpingunitController/deletePumpingunit'
    if (_record) {
        // 提示是否删除数据
        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
        Ext.Msg.confirm("是否要删除？", "是否要删除这些被选择的数据？", function (btn) {
            if (btn == "yes") {
                ExtDel_ObjectInfo("pumpingunitGrid_Id", _record, "id", delUrl); // 第一个参数为面板的Id，选择的记录对象，当前删除对象的Id名称，url
            }
        });
    } else {
        Ext.Msg.alert('删除操作', '您必须选择一行数据以便删除！');
    }
}

function modifyPumpingunit() {
        var win_Obj = Ext.getCmp("pumpingunitWindow_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var wellInfoWindow = Ext.create("AP.view.device.PumpingunitWindow", {
            title: "修改设备管理-抽油机"
        });
        wellInfoWindow.show();
        Ext.getCmp("addPumpingunitLabelClassBtn_win_Id").hide();
        Ext.getCmp("editPumpingunitLabelClassBtn_win_Id").show();
        SelectpumpingunitPanel();

    }
    // 复值
SelectpumpingunitPanel = function () {
    var dataattr_row = Ext.getCmp("pumpingunitPanel_Id").getSelectionModel().getSelection();
    var jlbh = dataattr_row[0].data.jlbh;
    var sccj = dataattr_row[0].data.sccj;
    var cyjxh = dataattr_row[0].data.cyjxh;
    var xdedzh = dataattr_row[0].data.xdedzh;
    var jsqednj = dataattr_row[0].data.jsqednj;
    var lgc = dataattr_row[0].data.lgc;
    var qbc = dataattr_row[0].data.qbc;
    var hbc = dataattr_row[0].data.hbc;
    var dkqbzl = dataattr_row[0].data.dkqbzl;
    var qbzxbj = dataattr_row[0].data.qbzxbj;
    var xdjl = dataattr_row[0].data.xdjl;
    var hg = dataattr_row[0].data.hg;
    var jgbphz = dataattr_row[0].data.jgbphz;
    var dkphkzl = dataattr_row[0].data.dkphkzl;
    var qbpzj = dataattr_row[0].data.qbpzj;
    var jsxcdb = dataattr_row[0].data.jsxcdb;
    var jsxpdlzj = dataattr_row[0].data.jsxpdlzj;
    var xzfx = dataattr_row[0].data.xzfx;
    var zdtzjl = dataattr_row[0].data.zdtzjl;
    var njysjsxz = dataattr_row[0].data.njysjsxz;
    var phkzdks = dataattr_row[0].data.phkzdks;
    var cyjlx = dataattr_row[0].data.cyjlx;
    var xpglr = dataattr_row[0].data.xpglr;
    var xpgldkzl = dataattr_row[0].data.xpgldkzl;
    var xpglzdks = dataattr_row[0].data.xpglzdks;
    var pdxl = dataattr_row[0].data.pdxl;
    var jsxxl = dataattr_row[0].data.jsxxl;
    var slgxl = dataattr_row[0].data.slgxl;




    Ext.getCmp('jlbh_Id').setValue(jlbh);
    Ext.getCmp('sccj_Id').setValue(sccj);
    Ext.getCmp('cyjxh_Id').setValue(cyjxh);
    Ext.getCmp('xdedzh_Id').setValue(xdedzh);
    Ext.getCmp('jsqednj_Id').setValue(jsqednj);
    Ext.getCmp('lgc_Id').setValue(lgc);
    Ext.getCmp('qbc_Id').setValue(qbc);
    Ext.getCmp('hbc_Id').setValue(hbc);
    Ext.getCmp('dkqbzl_Id').setValue(dkqbzl);
    Ext.getCmp('qbzxbj_Id').setValue(qbzxbj);
    Ext.getCmp('xdjl_Id').setValue(xdjl);
    Ext.getCmp('hg_Id').setValue(hg);
    Ext.getCmp('jgbphz_Id').setValue(jgbphz);
    Ext.getCmp('dkphkzl_Id').setValue(dkphkzl);
    Ext.getCmp('qbpzj_Id').setValue(qbpzj);
    Ext.getCmp('jsxcdb_Id').setValue(jsxcdb);
    Ext.getCmp('jsxpdlzj_Id').setValue(jsxpdlzj);
    Ext.getCmp('xzfx_Id').setValue(xzfx);
    Ext.getCmp('xzfx_Id1').setValue(xzfx);
    var xcfxRaw = "顺时针";
    if (xzfx == 2) {
        xcfxRaw = "逆时针";
    }
    Ext.getCmp('xzfx_Id1').setRawValue(xcfxRaw);
    Ext.getCmp('zdtzjl_Id').setValue(zdtzjl);
    Ext.getCmp('njysjsxz_Id').setValue(njysjsxz);
    Ext.getCmp('njysjsxz_Id1').setValue(njysjsxz);
    var njysjsxzRaw = "几何尺寸计算";
    if (njysjsxz == 2) {
        njysjsxzRaw = "厂家说明书输入";
    }
    Ext.getCmp('njysjsxz_Id1').setRawValue(njysjsxzRaw);
    Ext.getCmp('phkzdks_Id').setValue(phkzdks);
    Ext.getCmp('cyjlx_Id').setValue(cyjlx);
    Ext.getCmp('cyjlx_Id1').setValue(cyjlx);
    var cyjlxRaw = "常规抽油机";
    if (cyjlx == 102) {
        cyjlxRaw = "异相型抽油机";
    } else if (cyjlx == 103) {
        cyjlxRaw = "双驴头抽油机";
    } else if (cyjlx == 104) {
        cyjlxRaw = "下偏杠铃抽油机";
    } else if (cyjlx == 105) {
        cyjlxRaw = "调径变矩抽油机";
    } else if (cyjlx == 106) {
        cyjlxRaw = "立式皮带机";
    } else if (cyjlx == 107) {
        cyjlxRaw = "立式链条机";
    } else if (cyjlx == 108) {
        cyjlxRaw = "直线驱抽油机";
    }
    Ext.getCmp('cyjlx_Id1').setRawValue(cyjlxRaw);
    Ext.getCmp('xpglr_Id').setValue(xpglr);
    Ext.getCmp('xpgldkzl_Id').setValue(xpgldkzl);
    Ext.getCmp('xpglzdks_Id').setValue(xpglzdks);
    Ext.getCmp('pdxl_Id').setValue(pdxl);
    Ext.getCmp('jsxxl_Id').setValue(jsxxl);
    Ext.getCmp('slgxl_Id').setValue(slgxl);
};

//保存表格变化数据
function savePumpingUnitGridData() {
	var gridpanel = Ext.getCmp("pumpingunitGrid_Id");
	var store=Ext.getCmp("pumpingunitGrid_Id").getStore();
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
    	Ext.Ajax.request({
    		method:'POST',
    		url:context + '/pumpingunitController/savePumpingUnitEditerGridData',
    		success:function(response) {
    			Ext.MessageBox.alert("信息","更新成功",function(){
    				gridpanel.getStore().load();
    				gridpanel.getStore().modified = []; 
    			});
    		},
    		failure:function(){
    			Ext.MessageBox.alert("错误","保存失败");
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