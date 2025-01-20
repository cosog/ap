Ext.define('AP.controller.well.WellInfoController', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'wellInfoPanel',
        selector: 'wellInfoPanel'
   }],
    init: function () {
        this.control({
            'wellInfoPanel > toolbar button[action=addreWellInfoAction]': {
                click: addwellInfo
            },
            'wellInfoPanel > toolbar button[action=editreWellInfoInfoAction]': {
                click: modifywellInfo
            },
            'wellInfoPanel > toolbar button[action=delreWellInfoAction]': {
                click: delectwellInfo
            },
            'wellInfoPanel > toolbar button[action=saveWellEditerGridDataAction]': {
                click: saveWellEditerGridDataInfo
            },
            'wellInfoPanel': {
                itemdblclick: modifywellInfo
            }
        })
    }
});

// 窗体创建按钮事件
var SavewellInfoSubmitBtnForm = function () {
    var SavewellInfoWindow = Ext.getCmp("wellInfo_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (SavewellInfoWindow.getForm().isValid()) {
        SavewellInfoWindow.getForm().submit({
            url: context + '/wellInformationManagerController/doWellInformationAdd',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            waitMsg: loginUserLanguageResource.sendServer,
            waitTitle: loginUserLanguageResource.wait,
            success: function (response, action) {
                Ext.getCmp('wellInfo_addwin_Id').close();
                Ext.getCmp("wellPanel_Id").getStore().load();
                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.addSuccessfully, "<font color=blue>" + loginUserLanguageResource.addSuccessfully + "</font>");
                    if(mapHelperWell!=null){
    					mapHelperWell.clearOverlays();
    					SaveBackMapData(mapHelperWell,"well",m_BackDefaultZoomLevel);
    				}
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.addSuccessfully, "<font color=red>SORRY！</font>" + loginUserLanguageResource.addFailure);

                }
            },
            failure: function () {
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + "</font> 】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    } else {
        Ext.Msg.alert(loginUserLanguageResource.addSuccessfully, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>");
    }
    // 设置返回值 false : 让Extjs4 自动回调 success函数
    return false;
};

// 窗体上的修改按钮事件
function UpdatewellInfoSubmitBtnForm() {
    var getUpdateDataSubmitBtnFormId = Ext.getCmp("wellInfo_addwin_Id").down('form');
    Ext.MessageBox.msgButtons['ok'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;"+loginUserLanguageResource.confirm;
    if (getUpdateDataSubmitBtnFormId.getForm().isValid()) {
        Ext.getCmp("wellInfo_addwin_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
        getUpdateDataSubmitBtnFormId.getForm().submit({
            url: context + '/wellInformationManagerController/doWellInformationEdit',
            clientValidation: true, // 进行客户端验证
            method: "POST",
            success: function (response, action) {
                Ext.getCmp("wellInfo_addwin_Id").getEl().unmask();
                Ext.getCmp('wellInfo_addwin_Id').close();
                Ext.getCmp("wellPanel_Id").getStore().load();

                if (action.result.msg == true) {
                    Ext.Msg.alert(loginUserLanguageResource.addSuccessfully, "<font color=blue>" + loginUserLanguageResource.updateSuccessfully + "</font>");
                    if(mapHelperWell!=null){
    					mapHelperWell.clearOverlays();
    					SaveBackMapData(mapHelperWell,"well",m_BackDefaultZoomLevel);
    				}
                }
                if (action.result.msg == false) {
                    Ext.Msg.alert(loginUserLanguageResource.addSuccessfully,
                        "<font color=red>SORRY！</font>" + loginUserLanguageResource.updateFailure);
                }
            },
            failure: function () {
                Ext.getCmp("wellInfo_addwin_Id").getEl().unmask();
                Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>" + loginUserLanguageResource.exceptionThrow + " </font>】:" + loginUserLanguageResource.contactAdmin);
            }
        });
    }
    return false;
};

// open win
function addwellInfo() {
        var win_Obj = Ext.getCmp("wellInfo_addwin_Id")
        if (win_Obj != undefined) {
            win_Obj.destroy();
        }
        var WellInfoWindow = Ext.create("AP.view.well.WellInfoWindow", {
            title: loginUserLanguageResource.addDevie
        });
        WellInfoWindow.show();
        var save_btn = Ext.getCmp("addFromwellBtn_Id");
        save_btn.show();
        var save_update_Btn = Ext.getCmp("updateFromwellBtn_Id");
        save_update_Btn.hide()
            // Ext.Msg.alert("title", "add WellInfoWindow Info!");
    }
    // del to action
function delectwellInfo() {
    var wellPanel_panel = Ext.getCmp("wellPanel_Id");
}

function modifywellInfo() {
		var wellPanel_panel = Ext.getCmp("wellPanel_Id");
		var wellPanel_model = wellPanel_panel.getSelectionModel();
		var _record = wellPanel_model.getSelection();
		if(_record.length>0){
			var win_Obj = Ext.getCmp("wellInfo_addwin_Id")
	        if (win_Obj != undefined) {
	            win_Obj.destroy();
	        }
	        var wellInfoWindow = Ext.create("AP.view.well.WellInfoWindow", {
	            title: loginUserLanguageResource.editDevie
	        });
	        wellInfoWindow.show();
	        Ext.getCmp("addFromwellBtn_Id").hide();
	        Ext.getCmp("updateFromwellBtn_Id").show();
	        SelectwellInfoPanel();
	        //setTimeout('SelectwellInfoPanel()',1000);
		}else{
	        Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.checkOne);
	    }
        
    }

//保存表格变化数据
function saveWellEditerGridDataInfo() {
	var well_panel = Ext.getCmp("wellPanel_Id");
	var store=Ext.getCmp("wellPanel_Id").getStore();
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
    		url:context + '/wellInformationManagerController/saveWellEditerGridData',
    		success:function(response) {
    			Ext.MessageBox.alert(loginUserLanguageResource.message,"更新成功",function(){
    				well_panel.getStore().load();
    				well_panel.getStore().modified = []; 
    				if(mapHelperWell!=null){
    					mapHelperWell.clearOverlays();
    					SaveBackMapData(mapHelperWell,"well",m_BackDefaultZoomLevel);
    				}else{
    					updateWellMapDataAndShowMap();
    				}
    			});
    		},
    		failure:function(){
    			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
    		},
    		params: {
            	data: JSON.stringify(jsonArray)
            }
    	});   
    }else {
        //Ext.Msg.alert(loginUserLanguageResource.message, loginUserLanguageResource.noDataChange);
    }
    
    return false;
};

