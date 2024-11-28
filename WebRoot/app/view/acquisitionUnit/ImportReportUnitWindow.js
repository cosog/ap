var importReportUnitSingleWellDailyReportTemplateHandsontableHelper=null;
var importReportUnitSingleWellDailyReportContentHandsontableHelper=null;

var importReportUnitSingleWellRangeReportTemplateHandsontableHelper=null;
var importReportUnitSingleWellRangeReportContentHandsontableHelper=null;

var importReportUnitProductionTemplateHandsontableHelper=null;
var importReportUnitProductionReportContentHandsontableHelper=null;

Ext.define("AP.view.acquisitionUnit.ImportReportUnitWindow", {
    extend: 'Ext.window.Window',
    id: 'ImportReportUnitWindow_Id',
    alias: 'widget.ImportReportUnitWindow',
    layout: 'fit',
    title: loginUserLanguageResource.importData,
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader: true, //True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
    //    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 1500,
    minWidth: 1500,
    height: 700,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
            tbar: [{
                xtype: 'form',
                id: 'ReportUnitImportForm_Id',
                width: 300,
                bodyPadding: 0,
                frame: true,
                items: [{
                    xtype: 'filefield',
                    id: 'ReportUnitImportFilefield_Id',
                    name: 'file',
                    fieldLabel: '上传文件',
                    labelWidth: 60,
                    width: '100%',
                    msgTarget: 'side',
                    allowBlank: true,
                    anchor: '100%',
                    draggable: true,
                    buttonText: '请选择上传文件',
                    accept: '.json',
                    listeners: {
                        change: function (cmp) {
                            submitImportedReportUnitFile();
                        }
                    }
        	    }, {
                    id: 'ImportReportUnitSelectItemType_Id',
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }, {
                    id: 'ImportReportUnitSelectItemId_Id',
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		}, {
                xtype: 'label',
                id: 'ImportReportUnitWinTabLabel_Id',
                hidden: true,
                html: ''
            }, {
                xtype: "hidden",
                id: 'ImportReportUnitWinDeviceType_Id',
                value: '0'
			}, '->', {
                xtype: 'button',
                text: '全部保存',
                iconCls: 'save',
                handler: function (v, o) {
                    var treeStore = Ext.getCmp("ImportReportUnitContentTreeGridPanel_Id").getStore();
                    var count = treeStore.getCount();
                    var overlayCount = 0;
                    var collisionCount = 0;
                    for (var i = 0; i < count; i++) {
                        if (treeStore.getAt(i).data.classes == 1 && treeStore.getAt(i).data.saveSign == 1) {
                            overlayCount++;
                        } else if (treeStore.getAt(i).data.classes == 1 && treeStore.getAt(i).data.saveSign == 2) {
                            collisionCount++;
                        }
                    }
                    if (overlayCount > 0 || collisionCount > 0) {
                        var info = "";
                        if (overlayCount > 0) {
                            info += overlayCount + "个单元已存在";
                            if (collisionCount > 0) {
                                info += "，";
                            }
                        }
                        if (collisionCount > 0) {
                            info += overlayCount + "个单元无权限修改";
                        }
                        info += "！是否执行全部保存？";

                        Ext.Msg.confirm(loginUserLanguageResource.tip, info, function (btn) {
                            if (btn == "yes") {
                                saveAllImportedReportUnit();
                            }
                        });
                    } else {
                        saveAllImportedReportUnit();
                    }
                }
    	    }],
            layout: 'border',
            items: [{
                region: 'west',
                width: '25%',
                title: loginUserLanguageResource.uploadUnitList,
                layout: 'fit',
                split: true,
                collapsible: true,
                id: "importReportUnitTreePanel_Id"
            }, {
                region: 'center',
                xtype: 'tabpanel',
                id: "importReportUnitReportTemplateTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'top',
                items: [{
                    title: loginUserLanguageResource.singleDeviceReport,
                    id: 'importReportUnitSingleWellReportTemplatePanel_Id',
                    xtype: 'tabpanel',
                    activeTab: 0,
                    border: false,
                    tabPosition: 'top',
                    items: [{
                        title: loginUserLanguageResource.hourlyReport,
                        id: 'importReportUnitSingleWellDailyReportTemplatePanel_Id',
                        layout: "border",
                        border: false,
                        items: [{
                            region: 'center',
                            layout: "border",
                            border: false,
                            items: [{
                                region: 'center',
                                title: loginUserLanguageResource.deviceHourlyReportTemplate,
                                id: "importReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id",
                                layout: 'fit',
                                border: false,
                                html: '<div class="importReportUnitSingleWellDailyReportTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importReportUnitSingleWellDailyReportTemplateTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	if (importReportUnitSingleWellDailyReportTemplateHandsontableHelper != null && importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hot != null && importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hot != undefined) {
                                    		var newWidth=width;
                                    		var newHeight=height;
                                    		var header=thisPanel.getHeader();
                                    		if(header){
                                    			newHeight=newHeight-header.lastBox.height-2;
                                    		}
                                    		importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hot.updateSettings({
                                    			width:newWidth,
                                    			height:newHeight
                                    		});
                                        }
                                    }
                                }
                        	}, {
                                region: 'south',
                                height: '50%',
                                title: loginUserLanguageResource.deviceHourlyReportContentConfig,
                                collapsible: true,
                                split: true,
                                layout: 'fit',
                                border: false,
                                id: "importReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html: '<div class="importReportUnitSingleWellDailyReportContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importReportUnitSingleWellDailyReportContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	if (importReportUnitSingleWellDailyReportContentHandsontableHelper != null && importReportUnitSingleWellDailyReportContentHandsontableHelper.hot != null && importReportUnitSingleWellDailyReportContentHandsontableHelper.hot != undefined) {
                                    		var newWidth=width;
                                    		var newHeight=height;
                                    		var header=thisPanel.getHeader();
                                    		if(header){
                                    			newHeight=newHeight-header.lastBox.height-2;
                                    		}
                                    		importReportUnitSingleWellDailyReportContentHandsontableHelper.hot.updateSettings({
                                    			width:newWidth,
                                    			height:newHeight
                                    		});
                                        }
                                    }
                                }
                        	}]
                    	}]
                    }, {
                        title: loginUserLanguageResource.dailyReport,
                        id: 'importReportUnitSingleWellRangeReportTemplatePanel_Id',
                        layout: "border",
                        border: false,
                        items: [{
                            region: 'center',
                            layout: "border",
                            border: false,
                            items: [{
                                region: 'center',
                                title: loginUserLanguageResource.deviceDailyReportTemplate,
                                id: "importReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id",
                                layout: 'fit',
                                border: false,
                                html: '<div class="importReportUnitSingleWellRangeReportTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importReportUnitSingleWellRangeReportTemplateTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	if (importReportUnitSingleWellRangeReportTemplateHandsontableHelper != null && importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hot != null && importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hot != undefined) {
                                    		var newWidth=width;
                                    		var newHeight=height;
                                    		var header=thisPanel.getHeader();
                                    		if(header){
                                    			newHeight=newHeight-header.lastBox.height-2;
                                    		}
                                    		importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hot.updateSettings({
                                    			width:newWidth,
                                    			height:newHeight
                                    		});
                                        }
                                    }
                                }
                        	}, {
                                region: 'south',
                                height: '60%',
                                title: loginUserLanguageResource.deviceDailyReportContentConfig,
                                collapsible: true,
                                split: true,
                                layout: 'fit',
                                border: false,
                                id: "importReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html: '<div class="importReportUnitSingleWellRangeReportContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importReportUnitSingleWellRangeReportContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	if (importReportUnitSingleWellRangeReportContentHandsontableHelper != null && importReportUnitSingleWellRangeReportContentHandsontableHelper.hot != null && importReportUnitSingleWellRangeReportContentHandsontableHelper.hot != undefined) {
                                    		var newWidth=width;
                                    		var newHeight=height;
                                    		var header=thisPanel.getHeader();
                                    		if(header){
                                    			newHeight=newHeight-header.lastBox.height-2;
                                    		}
                                    		importReportUnitSingleWellRangeReportContentHandsontableHelper.hot.updateSettings({
                                    			width:newWidth,
                                    			height:newHeight
                                    		});
                                        }
                                    }
                                }
                        	}]
                    	}]
                    }],
                    listeners: {
                        tabchange: function (tabPanel, newCard, oldCard, obj) {
                        	var treeSelection= Ext.getCmp("ImportReportUnitContentTreeGridPanel_Id").getSelectionModel().getSelection();
                        	if(treeSelection.length>0){
                        		var record=treeSelection[0];
                        		var unitName='';
                            	var reportType=0;
                            	if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			unitName=record.data.children[0].text;
                            		}
                            	}else if(record.data.classes==1){
                            		unitName=record.data.text;
                            	}
                				
            					if(newCard.id=='importReportUnitSingleWellDailyReportTemplatePanel_Id'){
            						reportType=2;
            					}else if(newCard.id=='importReportUnitSingleWellRangeReportTemplatePanel_Id'){
            						reportType=0;
            					}
                            	
                				if(reportType==0){
                        			CreateImportReportUnitSingleWellRangeReportTemplateInfoTable(unitName);
                        			CreateImportReportUnitSingleWellRangeTotalItemsInfoTable(unitName);
                            	}else if(reportType==2){
                        			CreateImportReportUnitSingleWellDailyReportTemplateInfoTable(unitName);
                        			CreateImportReportUnitSingleWellDailyTotalItemsInfoTable(unitName);
                            	}else{
                            		CreateImportReportUnitProductionReportTemplateInfoTable(unitName);
                            		CreateImportReportUnitProductionTotalItemsInfoTable(unitName);
                            	}
                        	}
                        }
                    }
                }, {
                    title: loginUserLanguageResource.areaReport,
                    id: 'importReportUnitProductionReportTemplatePanel_Id',
                    xtype: 'tabpanel',
                    activeTab: 0,
                    border: false,
                    tabPosition: 'top',
                    items: [{
                        title: loginUserLanguageResource.dailyReport,
                        id: 'importReportUnitProductionRangeReportTemplatePanel_Id',
                        layout: "border",
                        border: false,
                        items: [{
                            region: 'center',
                            layout: "border",
                            border: false,
                            items: [{
                                region: 'center',
                                title: '区间日报模板',
                                id: "importReportUnitProductionTemplateTableInfoPanel_Id",
                                layout: 'fit',
                                border: false,
                                html: '<div class="importReportUnitProductionTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importReportUnitProductionTemplateTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	if (importReportUnitProductionTemplateHandsontableHelper != null && importReportUnitProductionTemplateHandsontableHelper.hot != null && importReportUnitProductionTemplateHandsontableHelper.hot != undefined) {
                                    		var newWidth=width;
                                    		var newHeight=height;
                                    		var header=thisPanel.getHeader();
                                    		if(header){
                                    			newHeight=newHeight-header.lastBox.height-2;
                                    		}
                                    		importReportUnitProductionTemplateHandsontableHelper.hot.updateSettings({
                                    			width:newWidth,
                                    			height:newHeight
                                    		});
                                        }
                                    }
                                }
                        	}, {
                                region: 'south',
                                height: '50%',
                                title: loginUserLanguageResource.areaDailyReportContentConfig,
                                collapsible: true,
                                split: true,
                                layout: 'fit',
                                border: false,
                                id: "importReportUnitProductionReportContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html: '<div class="importReportUnitProductionReportContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importReportUnitProductionReportContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	if (importReportUnitProductionReportContentHandsontableHelper != null && importReportUnitProductionReportContentHandsontableHelper.hot != null && importReportUnitProductionReportContentHandsontableHelper.hot != undefined) {
                                    		var newWidth=width;
                                    		var newHeight=height;
                                    		var header=thisPanel.getHeader();
                                    		if(header){
                                    			newHeight=newHeight-header.lastBox.height-2;
                                    		}
                                    		importReportUnitProductionReportContentHandsontableHelper.hot.updateSettings({
                                    			width:newWidth,
                                    			height:newHeight
                                    		});
                                        }
                                    }
                                }
                        	}]
                    	}]
                    }],
                    listeners: {
                        tabchange: function (tabPanel, newCard, oldCard, obj) {

                        }
                    }
                }],
                listeners: {
                    tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	var treeSelection= Ext.getCmp("ImportReportUnitContentTreeGridPanel_Id").getSelectionModel().getSelection();
                    	if(treeSelection.length>0){
                    		var record=treeSelection[0];
                    		
                    		var unitName='';
                        	var reportType=0;
                        	if(record.data.classes==0){
                        		if(isNotVal(record.data.children) && record.data.children.length>0){
                        			unitName=record.data.children[0].text;
                        		}
                        	}else if(record.data.classes==1){
                        		unitName=record.data.text;
                        	}
                        	
            				if(newCard.id=="importReportUnitSingleWellReportTemplatePanel_Id"){
            					var singleWellReportActiveId=Ext.getCmp("importReportUnitSingleWellReportTemplatePanel_Id").getActiveTab().id;
            					if(singleWellReportActiveId=='importReportUnitSingleWellDailyReportTemplatePanel_Id'){
            						reportType=2;
            					}else if(singleWellReportActiveId=='importReportUnitSingleWellRangeReportTemplatePanel_Id'){
            						reportType=0;
            					}
            				}else if(newCard.id=="importReportUnitProductionReportTemplatePanel_Id"){
            					reportType=1;
            				}
                        	
            				if(reportType==0){
                    			CreateImportReportUnitSingleWellRangeReportTemplateInfoTable(unitName);
                    			CreateImportReportUnitSingleWellRangeTotalItemsInfoTable(unitName);
                        	}else if(reportType==2){
                    			CreateImportReportUnitSingleWellDailyReportTemplateInfoTable(unitName);
                    			CreateImportReportUnitSingleWellDailyTotalItemsInfoTable(unitName);
                        	}else{
                        		CreateImportReportUnitProductionReportTemplateInfoTable(unitName);
                        		CreateImportReportUnitProductionTotalItemsInfoTable(unitName);
                        	}
                    	}
                    }
                }
            }],
            listeners: {
                beforeclose: function (panel, eOpts) {
                	clearImportReportUnitHandsontable();
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function clearImportReportUnitHandsontable(){
	if(importReportUnitSingleWellDailyReportTemplateHandsontableHelper!=null){
		if(importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hot!=undefined){
			importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hot.destroy();
		}
		importReportUnitSingleWellDailyReportTemplateHandsontableHelper=null;
	}
	if(importReportUnitSingleWellDailyReportContentHandsontableHelper!=null){
		if(importReportUnitSingleWellDailyReportContentHandsontableHelper.hot!=undefined){
			importReportUnitSingleWellDailyReportContentHandsontableHelper.hot.destroy();
		}
		importReportUnitSingleWellDailyReportContentHandsontableHelper=null;
	}
	if(importReportUnitSingleWellRangeReportTemplateHandsontableHelper!=null){
		if(importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hot!=undefined){
			importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hot.destroy();
		}
		importReportUnitSingleWellRangeReportTemplateHandsontableHelper=null;
	}
	if(importReportUnitSingleWellRangeReportContentHandsontableHelper!=null){
		if(importReportUnitSingleWellRangeReportContentHandsontableHelper.hot!=undefined){
			importReportUnitSingleWellRangeReportContentHandsontableHelper.hot.destroy();
		}
		importReportUnitSingleWellRangeReportContentHandsontableHelper=null;
	}
	if(importReportUnitProductionTemplateHandsontableHelper!=null){
		if(importReportUnitProductionTemplateHandsontableHelper.hot!=undefined){
			importReportUnitProductionTemplateHandsontableHelper.hot.destroy();
		}
		importReportUnitProductionTemplateHandsontableHelper=null;
	}
	if(importReportUnitProductionReportContentHandsontableHelper!=null){
		if(importReportUnitProductionReportContentHandsontableHelper.hot!=undefined){
			importReportUnitProductionReportContentHandsontableHelper.hot.destroy();
		}
		importReportUnitProductionReportContentHandsontableHelper=null;
	}
}

function submitImportedReportUnitFile() {
	clearImportReportUnitHandsontable();
	var form = Ext.getCmp("ReportUnitImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedReportUnitFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: '文件上传中...',
            success: function(response, action) {
            	var result = action.result;
            	if (result.flag == true) {
            		Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
            	}else{
            		Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
            	}
            	
            	
            	var importReportUnitContentTreeGridPanel = Ext.getCmp("ImportReportUnitContentTreeGridPanel_Id");
            	if (isNotVal(importReportUnitContentTreeGridPanel)) {
            		importReportUnitContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportReportUnitContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
			}
        });
    }
    return false;
};


adviceImportReportUnitCollisionInfoColor = function(val,o,p,e) {
	var saveSign=p.data.saveSign;
	var tipval=val;
	var backgroundColor='#FFFFFF';
 	var color='#DC2828';
 	if(saveSign==0){
 		color='#000000';
 	}
 	var opacity=0;
 	var rgba=color16ToRgba(backgroundColor,opacity);
 	o.style='background-color:'+rgba+';color:'+color+';';
 	if(isNotVal(tipval)){
 		return '<span data-qtip="'+tipval+'" data-dismissDelay=10000>'+val+'</span>';
 	}
}

iconImportSingleReportUnitAction = function(value, e, record) {
	var resultstring='';
	var unitName=record.data.text;

	if( record.data.classes==1 && record.data.saveSign!=2 ){
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedReportUnit(\""+unitName+"\")>"+loginUserLanguageResource.save+"...</a>";
	}
	return resultstring;
}

function saveSingelImportedReportUnit(unitName){
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveSingelImportedReportUnit',
		method : "POST",
		params : {
			unitName : unitName
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			if (result.success==true) {
				Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
			}else{
				Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
			}
			Ext.getCmp("ImportReportUnitContentTreeGridPanel_Id").getStore().load();

			var treeGridPanel = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id");
            if (isNotVal(treeGridPanel)) {
                treeGridPanel.getStore().load();
            } else {
                Ext.create('AP.store.acquisitionUnit.ModbusProtocolReportUnitTreeInfoStore');
            }
		},
		failure : function() {
			Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
		}
	});
}

function saveAllImportedReportUnit(){
	var unitNameList=[];
	var treeStore = Ext.getCmp("ImportReportUnitContentTreeGridPanel_Id").getStore();
	var count=treeStore.getCount();
	for(var i=0;i<count;i++){
		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign!=2){
			unitNameList.push(treeStore.getAt(i).data.text);
		}
	}
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveAllImportedReportUnit',
		method : "POST",
		params : {
			unitName : unitNameList.join(",")
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			if (result.success==true) {
				Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
			}else{
				Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
			}
			Ext.getCmp("ImportReportUnitContentTreeGridPanel_Id").getStore().load();

			var treeGridPanel = Ext.getCmp("ModbusProtocolReportUnitConfigTreeGridPanel_Id");
            if (isNotVal(treeGridPanel)) {
                treeGridPanel.getStore().load();
            } else {
                Ext.create('AP.store.acquisitionUnit.ModbusProtocolReportUnitTreeInfoStore');
            }
		},
		failure : function() {
			Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
		}
	});
}

function CreateImportReportUnitSingleWellDailyReportTemplateInfoTable(unitName){
	Ext.getCmp("importReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportReportUnitTemplateData',
		success:function(response) {
			Ext.getCmp("importReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("importReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id").setTitle(unitName+'/'+loginUserLanguageResource.deviceHourlyReportTemplate);
			var result =  Ext.JSON.decode(response.responseText);
			
			if(importReportUnitSingleWellDailyReportTemplateHandsontableHelper!=null){
				if(importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hot!=undefined){
					importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hot.destroy();
				}
				importReportUnitSingleWellDailyReportTemplateHandsontableHelper=null;
			}
			
			if(importReportUnitSingleWellDailyReportTemplateHandsontableHelper==null || importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hot==undefined){
				importReportUnitSingleWellDailyReportTemplateHandsontableHelper = ImportReportUnitSingleWellDailyReportTemplateHandsontableHelper.createNew("importReportUnitSingleWellDailyReportTemplateTableInfoDiv_id","importReportUnitSingleWellDailyReportTemplateTableInfoContainer",result);
				importReportUnitSingleWellDailyReportTemplateHandsontableHelper.createTable();
			}
		},
		failure:function(){
			Ext.getCmp("importReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			reportType:2,
			unitName:unitName
        }
	});
}

var ImportReportUnitSingleWellDailyReportTemplateHandsontableHelper = {
	    createNew: function (divid, containerid,templateData) {
	        var importReportUnitSingleWellDailyReportTemplateHandsontableHelper = {};
	        importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData=templateData;
	        importReportUnitSingleWellDailyReportTemplateHandsontableHelper.get_data = {};
	        importReportUnitSingleWellDailyReportTemplateHandsontableHelper.data=[];
	        importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hot = '';
	        importReportUnitSingleWellDailyReportTemplateHandsontableHelper.container = document.getElementById(divid);
	        
	        importReportUnitSingleWellDailyReportTemplateHandsontableHelper.initData=function(){
	        	importReportUnitSingleWellDailyReportTemplateHandsontableHelper.data=[];
	        	for(var i=0;i<importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header.length;i++){
		        	importReportUnitSingleWellDailyReportTemplateHandsontableHelper.data.push(importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].title);
		        }
	        }
	        
	        importReportUnitSingleWellDailyReportTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(importReportUnitSingleWellDailyReportTemplateHandsontableHelper!=null && importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hot!=null){
	        		for(var i=0;i<importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        	}
	        }
	        
	        
	        importReportUnitSingleWellDailyReportTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			importReportUnitSingleWellDailyReportTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			importReportUnitSingleWellDailyReportTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        importReportUnitSingleWellDailyReportTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        importReportUnitSingleWellDailyReportTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        importReportUnitSingleWellDailyReportTemplateHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        importReportUnitSingleWellDailyReportTemplateHandsontableHelper.createTable = function () {
	            importReportUnitSingleWellDailyReportTemplateHandsontableHelper.container.innerHTML = "";
	            importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hot = new Handsontable(importReportUnitSingleWellDailyReportTemplateHandsontableHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: importReportUnitSingleWellDailyReportTemplateHandsontableHelper.data,
	                fixedRowsTop:importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.rowHeights,
					colWidths: importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.columnWidths,
//					rowHeaders: true, //显示行头
					rowHeaders(index) {
					    return 'Row ' + (index + 1);
					},
//					colHeaders: true, //显示列头
					colHeaders(index) {
					    return 'Col ' + (index + 1);
					},
					stretchH: 'all',
					columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                mergeCells: importReportUnitSingleWellDailyReportTemplateHandsontableHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    cellProperties.renderer = importReportUnitSingleWellDailyReportTemplateHandsontableHelper.addStyle;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importReportUnitSingleWellDailyReportTemplateHandsontableHelper!=null
	                		&& importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hot!=''
	                		&& importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hot!=undefined 
	                		&& importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importReportUnitSingleWellDailyReportTemplateHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	            });
	        }
	        importReportUnitSingleWellDailyReportTemplateHandsontableHelper.getData = function (data) {
	        	
	        }

	        var init = function () {
	        	importReportUnitSingleWellDailyReportTemplateHandsontableHelper.initData();
	        }

	        init();
	        return importReportUnitSingleWellDailyReportTemplateHandsontableHelper;
	    }
	};

function CreateImportReportUnitSingleWellDailyTotalItemsInfoTable(unitName){
	Ext.getCmp("importReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportReportUnitItemsConfigData',
		success:function(response) {
			Ext.getCmp("importReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(isNotVal(unitName)){
				Ext.getCmp("importReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").setTitle(unitName+'/'+loginUserLanguageResource.deviceHourlyReportContent);
			}else{
				Ext.getCmp("importReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.deviceHourlyReportContent);
			}
			if(importReportUnitSingleWellDailyReportContentHandsontableHelper==null || importReportUnitSingleWellDailyReportContentHandsontableHelper.hot==undefined){
				importReportUnitSingleWellDailyReportContentHandsontableHelper = ImportReportUnitSingleWellDailyReportContentHandsontableHelper.createNew("importReportUnitSingleWellDailyReportContentConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','数据来源','统计方式','显示级别','数据顺序','小数位数','"+loginUserLanguageResource.reportCurve+"','','','']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
					 	+"{data:'dataSource'}," 
					 	+"{data:'totalType'}," 
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importReportUnitSingleWellDailyReportContentHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importReportUnitSingleWellDailyReportContentHandsontableHelper);}}," 
						+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importReportUnitSingleWellDailyReportContentHandsontableHelper);}}," 
						+"{data:'reportCurveConfShowValue'},"
						+"{data:'reportCurveConf'},"
						+"{data:'code'},"
						+"{data:'dataType'}"
						+"]";
				importReportUnitSingleWellDailyReportContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importReportUnitSingleWellDailyReportContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				importReportUnitSingleWellDailyReportContentHandsontableHelper.createTable(result.totalRoot);
			}else{
				importReportUnitSingleWellDailyReportContentHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("importReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			unitName:unitName,
			reportType:2
        }
	});
};

var ImportReportUnitSingleWellDailyReportContentHandsontableHelper = {
		createNew: function (divid) {
	        var importReportUnitSingleWellDailyReportContentHandsontableHelper = {};
	        importReportUnitSingleWellDailyReportContentHandsontableHelper.hot1 = '';
	        importReportUnitSingleWellDailyReportContentHandsontableHelper.divid = divid;
	        importReportUnitSingleWellDailyReportContentHandsontableHelper.validresult=true;//数据校验
	        importReportUnitSingleWellDailyReportContentHandsontableHelper.colHeaders=[];
	        importReportUnitSingleWellDailyReportContentHandsontableHelper.columns=[];
	        importReportUnitSingleWellDailyReportContentHandsontableHelper.AllData=[];
	        
	        importReportUnitSingleWellDailyReportContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==3){
	            		td.style.backgroundColor = '#'+arr[2];
	            	}
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importReportUnitSingleWellDailyReportContentHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importReportUnitSingleWellDailyReportContentHandsontableHelper.createTable = function (data) {
	        	$('#'+importReportUnitSingleWellDailyReportContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importReportUnitSingleWellDailyReportContentHandsontableHelper.divid);
	        	importReportUnitSingleWellDailyReportContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [9,10,11],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [30,140,80,60,60,60,60,85,85,85],
	                columns:importReportUnitSingleWellDailyReportContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importReportUnitSingleWellDailyReportContentHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    if(visualColIndex==8){
		                	cellProperties.renderer = importReportUnitSingleWellDailyReportContentHandsontableHelper.addCurveBg;
		                }else{
		                	if(importReportUnitSingleWellDailyReportContentHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    	            	&& importReportUnitSingleWellDailyReportContentHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		                    	cellProperties.renderer = importReportUnitSingleWellDailyReportContentHandsontableHelper.addCellStyle;
		    	            }
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importReportUnitSingleWellDailyReportContentHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importReportUnitSingleWellDailyReportContentHandsontableHelper!=null
	                		&& importReportUnitSingleWellDailyReportContentHandsontableHelper.hot!=''
	                		&& importReportUnitSingleWellDailyReportContentHandsontableHelper.hot!=undefined 
	                		&& importReportUnitSingleWellDailyReportContentHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importReportUnitSingleWellDailyReportContentHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	        //保存数据
	        importReportUnitSingleWellDailyReportContentHandsontableHelper.saveData = function () {}
	        importReportUnitSingleWellDailyReportContentHandsontableHelper.clearContainer = function () {
	        	importReportUnitSingleWellDailyReportContentHandsontableHelper.AllData = [];
	        }
	        return importReportUnitSingleWellDailyReportContentHandsontableHelper;
	    }
};

function CreateImportReportUnitSingleWellRangeReportTemplateInfoTable(unitName){
	Ext.getCmp("importReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportReportUnitTemplateData',
		success:function(response) {
			Ext.getCmp("importReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("importReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id").setTitle(unitName+'/'+loginUserLanguageResource.deviceDailyReportTemplate);
			var result =  Ext.JSON.decode(response.responseText);
			
			if(importReportUnitSingleWellRangeReportTemplateHandsontableHelper!=null){
				if(importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hot!=undefined){
					importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hot.destroy();
				}
				importReportUnitSingleWellRangeReportTemplateHandsontableHelper=null;
			}
			
			if(importReportUnitSingleWellRangeReportTemplateHandsontableHelper==null || importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hot==undefined){
				importReportUnitSingleWellRangeReportTemplateHandsontableHelper = ImportReportUnitSingleWellRangeReportTemplateHandsontableHelper.createNew("importReportUnitSingleWellRangeReportTemplateTableInfoDiv_id","importReportUnitSingleWellRangeReportTemplateTableInfoContainer",result);
				importReportUnitSingleWellRangeReportTemplateHandsontableHelper.createTable();
			}
		},
		failure:function(){
			Ext.getCmp("importReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			reportType:0,
			unitName:unitName
        }
	});
}

var ImportReportUnitSingleWellRangeReportTemplateHandsontableHelper = {
	    createNew: function (divid, containerid,templateData) {
	        var importReportUnitSingleWellRangeReportTemplateHandsontableHelper = {};
	        importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData=templateData;
	        importReportUnitSingleWellRangeReportTemplateHandsontableHelper.get_data = {};
	        importReportUnitSingleWellRangeReportTemplateHandsontableHelper.data=[];
	        importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hot = '';
	        importReportUnitSingleWellRangeReportTemplateHandsontableHelper.container = document.getElementById(divid);
	        
	        
	        importReportUnitSingleWellRangeReportTemplateHandsontableHelper.initData=function(){
	        	importReportUnitSingleWellRangeReportTemplateHandsontableHelper.data=[];
	        	for(var i=0;i<importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header.length;i++){
		        	importReportUnitSingleWellRangeReportTemplateHandsontableHelper.data.push(importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].title);
		        }
	        }
	        
	        importReportUnitSingleWellRangeReportTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(importReportUnitSingleWellRangeReportTemplateHandsontableHelper!=null && importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hot!=null){
	        		for(var i=0;i<importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        	}
	        }
	        
	        
	        importReportUnitSingleWellRangeReportTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			importReportUnitSingleWellRangeReportTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			importReportUnitSingleWellRangeReportTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        importReportUnitSingleWellRangeReportTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        importReportUnitSingleWellRangeReportTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        importReportUnitSingleWellRangeReportTemplateHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        importReportUnitSingleWellRangeReportTemplateHandsontableHelper.createTable = function () {
	            importReportUnitSingleWellRangeReportTemplateHandsontableHelper.container.innerHTML = "";
	            importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hot = new Handsontable(importReportUnitSingleWellRangeReportTemplateHandsontableHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: importReportUnitSingleWellRangeReportTemplateHandsontableHelper.data,
	                fixedRowsTop:importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.rowHeights,
					colWidths: importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.columnWidths,
//					rowHeaders: true, //显示行头
					rowHeaders(index) {
					    return 'Row ' + (index + 1);
					},
//					colHeaders: true, //显示列头
					colHeaders(index) {
					    return 'Col ' + (index + 1);
					},
					stretchH: 'all',
					columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                mergeCells: importReportUnitSingleWellRangeReportTemplateHandsontableHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    cellProperties.renderer = importReportUnitSingleWellRangeReportTemplateHandsontableHelper.addStyle;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importReportUnitSingleWellRangeReportTemplateHandsontableHelper!=null
	                		&& importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hot!=''
	                		&& importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hot!=undefined 
	                		&& importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importReportUnitSingleWellRangeReportTemplateHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	            });
	        }
	        importReportUnitSingleWellRangeReportTemplateHandsontableHelper.getData = function (data) {
	        	
	        }

	        var init = function () {
	        	importReportUnitSingleWellRangeReportTemplateHandsontableHelper.initData();
	        }

	        init();
	        return importReportUnitSingleWellRangeReportTemplateHandsontableHelper;
	    }
	};

function CreateImportReportUnitSingleWellRangeTotalItemsInfoTable(unitName){
	Ext.getCmp("importReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportReportUnitItemsConfigData',
		success:function(response) {
			Ext.getCmp("importReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(isNotVal(unitName)){
				Ext.getCmp("importReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id").setTitle(unitName+'/'+loginUserLanguageResource.deviceDailyReportContent);
			}else{
				Ext.getCmp("importReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.deviceDailyReportContent);
			}
			if(importReportUnitSingleWellRangeReportContentHandsontableHelper==null || importReportUnitSingleWellRangeReportContentHandsontableHelper.hot==undefined){
				importReportUnitSingleWellRangeReportContentHandsontableHelper = ImportReportUnitSingleWellRangeReportContentHandsontableHelper.createNew("importReportUnitSingleWellRangeReportContentConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','数据来源','统计方式','显示级别','数据顺序','小数位数','"+loginUserLanguageResource.reportCurve+"','','','']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
					 	+"{data:'dataSource'}," 
					 	+"{data:'totalType' }," 
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importReportUnitSingleWellRangeReportContentHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importReportUnitSingleWellRangeReportContentHandsontableHelper);}}," 
						+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,importReportUnitSingleWellRangeReportContentHandsontableHelper);}}," 
						+"{data:'reportCurveConfShowValue'},"
						+"{data:'reportCurveConf'},"
						+"{data:'code'},"
						+"{data:'dataType'}"
						+"]";
				importReportUnitSingleWellRangeReportContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importReportUnitSingleWellRangeReportContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				importReportUnitSingleWellRangeReportContentHandsontableHelper.createTable(result.totalRoot);
			}else{
				importReportUnitSingleWellRangeReportContentHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("importReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			unitName:unitName,
			reportType:0
        }
	});
};

var ImportReportUnitSingleWellRangeReportContentHandsontableHelper = {
		createNew: function (divid) {
	        var importReportUnitSingleWellRangeReportContentHandsontableHelper = {};
	        importReportUnitSingleWellRangeReportContentHandsontableHelper.hot1 = '';
	        importReportUnitSingleWellRangeReportContentHandsontableHelper.divid = divid;
	        importReportUnitSingleWellRangeReportContentHandsontableHelper.validresult=true;//数据校验
	        importReportUnitSingleWellRangeReportContentHandsontableHelper.colHeaders=[];
	        importReportUnitSingleWellRangeReportContentHandsontableHelper.columns=[];
	        importReportUnitSingleWellRangeReportContentHandsontableHelper.AllData=[];
	        
	        importReportUnitSingleWellRangeReportContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==3){
	            		td.style.backgroundColor = '#'+arr[2];
	            	}
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importReportUnitSingleWellRangeReportContentHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importReportUnitSingleWellRangeReportContentHandsontableHelper.createTable = function (data) {
	        	$('#'+importReportUnitSingleWellRangeReportContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importReportUnitSingleWellRangeReportContentHandsontableHelper.divid);
	        	importReportUnitSingleWellRangeReportContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [9,10,11],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [30,140,80,60,60,60,60,85,85,85],
	                columns:importReportUnitSingleWellRangeReportContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importReportUnitSingleWellRangeReportContentHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    if(visualColIndex==8){
		                	cellProperties.renderer = importReportUnitSingleWellRangeReportContentHandsontableHelper.addCurveBg;
		                }else{
		                	cellProperties.renderer = importReportUnitSingleWellRangeReportContentHandsontableHelper.addCellStyle;
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importReportUnitSingleWellRangeReportContentHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importReportUnitSingleWellRangeReportContentHandsontableHelper!=null
	                		&& importReportUnitSingleWellRangeReportContentHandsontableHelper.hot!=''
	                		&& importReportUnitSingleWellRangeReportContentHandsontableHelper.hot!=undefined 
	                		&& importReportUnitSingleWellRangeReportContentHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importReportUnitSingleWellRangeReportContentHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	        //保存数据
	        importReportUnitSingleWellRangeReportContentHandsontableHelper.saveData = function () {}
	        importReportUnitSingleWellRangeReportContentHandsontableHelper.clearContainer = function () {
	        	importReportUnitSingleWellRangeReportContentHandsontableHelper.AllData = [];
	        }
	        return importReportUnitSingleWellRangeReportContentHandsontableHelper;
	    }
};

function CreateImportReportUnitProductionReportTemplateInfoTable(unitName){
	Ext.getCmp("importReportUnitProductionTemplateTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportReportUnitTemplateData',
		success:function(response) {
			Ext.getCmp("importReportUnitProductionTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("importReportUnitProductionTemplateTableInfoPanel_Id").setTitle(unitName+'/'+loginUserLanguageResource.areaDailyReportTemplate);
			var result =  Ext.JSON.decode(response.responseText);
			
			if(importReportUnitProductionTemplateHandsontableHelper!=null){
				if(importReportUnitProductionTemplateHandsontableHelper.hot!=undefined){
					importReportUnitProductionTemplateHandsontableHelper.hot.destroy();
				}
				importReportUnitProductionTemplateHandsontableHelper=null;
			}
			if(importReportUnitProductionTemplateHandsontableHelper==null || importReportUnitProductionTemplateHandsontableHelper.hot==undefined){
				importReportUnitProductionTemplateHandsontableHelper = ImportReportUnitProductionTemplateHandsontableHelper.createNew("importReportUnitProductionTemplateTableInfoDiv_id","importReportUnitProductionTemplateTableInfoContainer",result);
				importReportUnitProductionTemplateHandsontableHelper.createTable();
			}
		},
		failure:function(){
			Ext.getCmp("importReportUnitProductionTemplateTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			reportType:1,
			unitName:unitName
        }
	});
}

var ImportReportUnitProductionTemplateHandsontableHelper = {
	    createNew: function (divid, containerid,templateData) {
	        var importReportUnitProductionTemplateHandsontableHelper = {};
	        importReportUnitProductionTemplateHandsontableHelper.templateData=templateData;
	        importReportUnitProductionTemplateHandsontableHelper.get_data = {};
	        importReportUnitProductionTemplateHandsontableHelper.data=[];
	        importReportUnitProductionTemplateHandsontableHelper.hot = '';
	        importReportUnitProductionTemplateHandsontableHelper.container = document.getElementById(divid);
	        
	        
	        importReportUnitProductionTemplateHandsontableHelper.initData=function(){
	        	importReportUnitProductionTemplateHandsontableHelper.data=[];
	        	for(var i=0;i<importReportUnitProductionTemplateHandsontableHelper.templateData.header.length;i++){
		        	importReportUnitProductionTemplateHandsontableHelper.data.push(importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].title);
		        }
	        }
	        
	        importReportUnitProductionTemplateHandsontableHelper.addStyle = function (instance, td, row, col, prop, value, cellProperties) {
	        	Handsontable.renderers.TextRenderer.apply(this, arguments);
	        	if(importReportUnitProductionTemplateHandsontableHelper!=null && importReportUnitProductionTemplateHandsontableHelper.hot!=null){
	        		for(var i=0;i<importReportUnitProductionTemplateHandsontableHelper.templateData.header.length;i++){
		        		if(row==i){
		        			if(isNotVal(importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].tdStyle)){
		        				if(isNotVal(importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight)){
		        					td.style.fontWeight = importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.fontWeight;
		        				}
		        				if(isNotVal(importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize)){
		        					td.style.fontSize = importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.fontSize;
		        				}
		        				if(isNotVal(importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily)){
		        					td.style.fontFamily = importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.fontFamily;
		        				}
		        				if(isNotVal(importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.height)){
		        					td.style.height = importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.height;
		        				}
		        				if(isNotVal(importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.color)){
		        					td.style.color = importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.color;
		        				}
		        				if(isNotVal(importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor)){
		        					td.style.backgroundColor = importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.backgroundColor;
		        				}
		        				if(isNotVal(importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign)){
		        					td.style.textAlign = importReportUnitProductionTemplateHandsontableHelper.templateData.header[i].tdStyle.textAlign;
		        				}
		        			}
		        			break;
		        		}
		        	}
	        	}
	        }
	        
	        
	        importReportUnitProductionTemplateHandsontableHelper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(242, 242, 242)';
	            if (row <= 2&&row>=1) {
	                td.style.fontWeight = 'bold';
					td.style.fontSize = '13px';
					td.style.color = 'rgb(0, 0, 51)';
					td.style.fontFamily = 'SimSun';//SimHei-黑体 SimSun-宋体
	            }
	        }
			
			importReportUnitProductionTemplateHandsontableHelper.addSizeBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if (row < 1) {
	                td.style.fontWeight = 'bold';
			        td.style.fontSize = '25px';
			        td.style.fontFamily = 'SimSun';
			        td.style.height = '50px';   
			    }      
	        }
			
			importReportUnitProductionTemplateHandsontableHelper.addColBg = function (instance, td, row, col, prop, value, cellProperties) {
	             Handsontable.renderers.TextRenderer.apply(this, arguments);
	             td.style.backgroundColor = 'rgb(242, 242, 242)';
		         if(row < 3){
	                 td.style.fontWeight = 'bold';
			         td.style.fontSize = '5px';
			         td.style.fontFamily = 'SimHei';
	            }      
	        }
			

	        importReportUnitProductionTemplateHandsontableHelper.addBgBlue = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(183, 222, 232)';
	        }

	        importReportUnitProductionTemplateHandsontableHelper.addBgGreen = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.backgroundColor = 'rgb(216, 228, 188)';
	        }
	        
	        importReportUnitProductionTemplateHandsontableHelper.hiddenColumn = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.display = 'none';
	        }

	        // 实现标题居中
	        importReportUnitProductionTemplateHandsontableHelper.titleCenter = function () {
	            $(containerid).width($($('.wtHider')[0]).width());
	        }

	        importReportUnitProductionTemplateHandsontableHelper.createTable = function () {
	            importReportUnitProductionTemplateHandsontableHelper.container.innerHTML = "";
	            importReportUnitProductionTemplateHandsontableHelper.hot = new Handsontable(importReportUnitProductionTemplateHandsontableHelper.container, {
	            	licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	            	data: importReportUnitProductionTemplateHandsontableHelper.data,
	                fixedRowsTop:importReportUnitProductionTemplateHandsontableHelper.templateData.fixedRowsTop, 
	                fixedRowsBottom: importReportUnitProductionTemplateHandsontableHelper.templateData.fixedRowsBottom,
//	                fixedColumnsLeft:1, //固定左侧多少列不能水平滚动
	                rowHeaders: false,
	                colHeaders: false,
					rowHeights: importReportUnitProductionTemplateHandsontableHelper.templateData.rowHeights,
					colWidths: importReportUnitProductionTemplateHandsontableHelper.templateData.columnWidths,
//					rowHeaders: true, //显示行头
					rowHeaders(index) {
					    return 'Row ' + (index + 1);
					},
//					colHeaders: true, //显示列头
					colHeaders(index) {
					    return 'Col ' + (index + 1);
					},
					stretchH: 'all',
					columnSorting: true, //允许排序
	                allowInsertRow:false,
	                sortIndicator: true,
	                manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                mergeCells: importReportUnitProductionTemplateHandsontableHelper.templateData.mergeCells,
	                cells: function (row, col, prop) {
	                    var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    cellProperties.renderer = importReportUnitProductionTemplateHandsontableHelper.addStyle;
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importReportUnitProductionTemplateHandsontableHelper!=null
	                		&& importReportUnitProductionTemplateHandsontableHelper.hot!=''
	                		&& importReportUnitProductionTemplateHandsontableHelper.hot!=undefined 
	                		&& importReportUnitProductionTemplateHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importReportUnitProductionTemplateHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	            });
	        }
	        importReportUnitProductionTemplateHandsontableHelper.getData = function (data) {
	        	
	        }

	        var init = function () {
	        	importReportUnitProductionTemplateHandsontableHelper.initData();
	        }

	        init();
	        return importReportUnitProductionTemplateHandsontableHelper;
	    }
	};

function CreateImportReportUnitProductionTotalItemsInfoTable(unitName){
	Ext.getCmp("importReportUnitProductionReportContentConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportReportUnitItemsConfigData',
		success:function(response) {
			Ext.getCmp("importReportUnitProductionReportContentConfigTableInfoPanel_Id").getEl().unmask();
			var result =  Ext.JSON.decode(response.responseText);
			if(isNotVal(unitName)){
				Ext.getCmp("importReportUnitProductionReportContentConfigTableInfoPanel_Id").setTitle(unitName+'/'+loginUserLanguageResource.areaDailyReportContent);
			}else{
				Ext.getCmp("importReportUnitProductionReportContentConfigTableInfoPanel_Id").setTitle(loginUserLanguageResource.areaDailyReportContent);
			}
			if(importReportUnitProductionReportContentHandsontableHelper==null || importReportUnitProductionReportContentHandsontableHelper.hot==undefined){
				importReportUnitProductionReportContentHandsontableHelper = ImportReportUnitProductionReportContentHandsontableHelper.createNew("importReportUnitProductionReportContentConfigTableInfoDiv_id");
				var colHeaders="['序号','名称','单位','数据来源','统计方式','显示级别','数据顺序','小数位数','求和','求平均','"+loginUserLanguageResource.reportCurve+"','曲线统计类型','','','']";
				var columns="["
						+"{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'unit'},"
					 	+"{data:'dataSource'}," 
					 	+"{data:'totalType'}," 
						+"{data:'showLevel',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}}," 
						+"{data:'sort',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}}," 
						
						+"{data:'prec',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num_Nullable(val, callback,this.row, this.col,productionReportTemplateContentHandsontableHelper);}}," 
						
						+"{data:'sumSign',type:'checkbox'}," 
						+"{data:'averageSign',type:'checkbox'}," 
						
						+"{data:'reportCurveConfShowValue'},"
						
						+"{data:'curveStatType',type:'dropdown',strict:true,allowInvalid:false,source:['合计', '平均']},"
						
						+"{data:'reportCurveConf'},"
						
						+"{data:'code'},"
						+"{data:'dataType'}"
						+"]";
				importReportUnitProductionReportContentHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importReportUnitProductionReportContentHandsontableHelper.columns=Ext.JSON.decode(columns);
				importReportUnitProductionReportContentHandsontableHelper.createTable(result.totalRoot);
			}else{
				importReportUnitProductionReportContentHandsontableHelper.hot.loadData(result.totalRoot);
			}
		},
		failure:function(){
			Ext.getCmp("importReportUnitProductionReportContentConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			unitName:unitName,
			reportType:1
        }
	});
};

var ImportReportUnitProductionReportContentHandsontableHelper = {
		createNew: function (divid) {
	        var importReportUnitProductionReportContentHandsontableHelper = {};
	        importReportUnitProductionReportContentHandsontableHelper.hot1 = '';
	        importReportUnitProductionReportContentHandsontableHelper.divid = divid;
	        importReportUnitProductionReportContentHandsontableHelper.validresult=true;//数据校验
	        importReportUnitProductionReportContentHandsontableHelper.colHeaders=[];
	        importReportUnitProductionReportContentHandsontableHelper.columns=[];
	        importReportUnitProductionReportContentHandsontableHelper.AllData=[];
	        
	        importReportUnitProductionReportContentHandsontableHelper.addCurveBg = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            if(value!=null){
	            	var arr=value.split(';');
	            	if(arr.length==3){
	            		td.style.backgroundColor = '#'+arr[2];
	            	}
	            }
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importReportUnitProductionReportContentHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importReportUnitProductionReportContentHandsontableHelper.createTable = function (data) {
	        	$('#'+importReportUnitProductionReportContentHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importReportUnitProductionReportContentHandsontableHelper.divid);
	        	importReportUnitProductionReportContentHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		hiddenColumns: {
	                    columns: [12,13,14],
	                    indicators: false,
	                    copyPasteEnabled: false
	                },
	                colWidths: [30,140,80,60,60,60,60,60,30,45,85,85,70],
	                columns:importReportUnitProductionReportContentHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importReportUnitProductionReportContentHandsontableHelper.colHeaders,//显示列头
	                columnSorting: true,//允许排序
	                sortIndicator: true,
	                manualColumnResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                manualRowResize:true,//当值为true时，允许拖动，当为false时禁止拖动
	                filters: true,
	                renderAllRows: true,
	                search: true,
	                cells: function (row, col, prop) {
	                	var cellProperties = {};
	                    var visualRowIndex = this.instance.toVisualRow(row);
	                    var visualColIndex = this.instance.toVisualColumn(col);
	                    cellProperties.readOnly = true;
	                    if(visualColIndex==10){
		                	cellProperties.renderer = importReportUnitProductionReportContentHandsontableHelper.addCurveBg;
		                }else{
		                	if(importReportUnitProductionReportContentHandsontableHelper.columns[visualColIndex].type!='dropdown' 
		    	            	&& importReportUnitProductionReportContentHandsontableHelper.columns[visualColIndex].type!='checkbox'){
		                    	cellProperties.renderer = importReportUnitProductionReportContentHandsontableHelper.addCellStyle;
		    	            }
		                }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(importReportUnitProductionReportContentHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importReportUnitProductionReportContentHandsontableHelper!=null
	                		&& importReportUnitProductionReportContentHandsontableHelper.hot!=''
	                		&& importReportUnitProductionReportContentHandsontableHelper.hot!=undefined 
	                		&& importReportUnitProductionReportContentHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importReportUnitProductionReportContentHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
	                		if(isNotVal(rawValue)){
                				var showValue=rawValue;
            					var rowChar=90;
            					var maxWidth=rowChar*10;
            					if(rawValue.length>rowChar){
            						showValue='';
            						let arr = [];
            						let index = 0;
            						while(index<rawValue.length){
            							arr.push(rawValue.slice(index,index +=rowChar));
            						}
            						for(var i=0;i<arr.length;i++){
            							showValue+=arr[i];
            							if(i<arr.length-1){
            								showValue+='<br>';
            							}
            						}
            					}
                				if(!isNotVal(TD.tip)){
                					var height=28;
                					TD.tip = Ext.create('Ext.tip.ToolTip', {
		                			    target: event.target,
		                			    maxWidth:maxWidth,
		                			    html: showValue,
		                			    listeners: {
		                			    	hide: function (thisTip, eOpts) {
		                                	},
		                                	close: function (thisTip, eOpts) {
		                                	}
		                                }
		                			});
                				}else{
                					TD.tip.setHtml(showValue);
                				}
                			}
	                	}
	                }
	        	});
	        }
	        //保存数据
	        importReportUnitProductionReportContentHandsontableHelper.saveData = function () {}
	        importReportUnitProductionReportContentHandsontableHelper.clearContainer = function () {
	        	importReportUnitProductionReportContentHandsontableHelper.AllData = [];
	        }
	        return importReportUnitProductionReportContentHandsontableHelper;
	    }
};