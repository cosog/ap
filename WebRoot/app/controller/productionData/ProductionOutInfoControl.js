Ext.define('AP.controller.productionData.ProductionOutInfoControl', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'productionOutWellInfoPanel',
        selector: 'productionOutWellInfoPanel'
   }],
    init: function () {
        this.control({
            'productionOutWellInfoPanel > toolbar button[action=saveProductionOutGridDataAction]': {
                click: saveProductionOutGridDataInfo
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
