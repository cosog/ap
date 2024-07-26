Ext.define("AP.view.acquisitionUnit.ImportReportUnitWindow", {
    extend: 'Ext.window.Window',
    id: 'ImportReportUnitWindow_Id',
    alias: 'widget.ImportReportUnitWindow',
    layout: 'fit',
    title: '报表单元导入',
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

                        Ext.Msg.confirm('提示', info, function (btn) {
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
                title: '上传单元列表',
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
                    title: '单井报表',
                    id: 'importReportUnitSingleWellReportTemplatePanel_Id',
                    xtype: 'tabpanel',
                    activeTab: 0,
                    border: false,
                    tabPosition: 'top',
                    items: [{
                        title: '班报表',
                        id: 'importReportUnitSingleWellDailyReportTemplatePanel_Id',
                        layout: "border",
                        border: false,
                        items: [{
                            region: 'west',
                            width: '16%',
                            layout: 'fit',
                            border: false,
                            id: 'importReportUnitSingleWellDailyReportTemplateListPanel_Id',
                            title: '报表模板列表',
                            header: false,
                            split: true,
                            collapsible: true
                    	}, {
                            region: 'center',
                            layout: "border",
                            border: false,
                            items: [{
                                region: 'center',
                                title: '单井班报表模板',
                                id: "importReportUnitSingleWellDailyReportTemplateTableInfoPanel_Id",
                                layout: 'fit',
                                border: false,
                                html: '<div class="importReportUnitSingleWellDailyReportTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importReportUnitSingleWellDailyReportTemplateTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	
                                    }
                                }
                        	}, {
                                region: 'south',
                                height: '50%',
                                title: '单井班报表内容配置',
                                collapsible: true,
                                split: true,
                                layout: 'fit',
                                border: false,
                                id: "importReportUnitSingleWellDailyReportContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html: '<div class="importReportUnitSingleWellDailyReportContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importReportUnitSingleWellDailyReportContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	
                                    }
                                }
                        	}]
                    	}]
                    }, {
                        title: '日报表',
                        id: 'importReportUnitSingleWellRangeReportTemplatePanel_Id',
                        layout: "border",
                        border: false,
                        items: [{
                            region: 'west',
                            width: '16%',
                            layout: 'fit',
                            border: false,
                            id: 'importReportUnitSingleWellRangeReportTemplateListPanel_Id',
                            title: '报表模板列表',
                            header: false,
                            split: true,
                            collapsible: true
                    	}, {
                            region: 'center',
                            layout: "border",
                            border: false,
                            items: [{
                                region: 'center',
                                title: '单井日报表模板',
                                id: "importReportUnitSingleWellRangeReportTemplateTableInfoPanel_Id",
                                layout: 'fit',
                                border: false,
                                html: '<div class="importReportUnitSingleWellRangeReportTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importReportUnitSingleWellRangeReportTemplateTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	
                                    }
                                }
                        	}, {
                                region: 'south',
                                height: '60%',
                                title: '单井日报表内容配置',
                                collapsible: true,
                                split: true,
                                layout: 'fit',
                                border: false,
                                id: "importReportUnitSingleWellRangeReportContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html: '<div class="importReportUnitSingleWellRangeReportContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importReportUnitSingleWellRangeReportContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	
                                    }
                                }
                        	}]
                    	}]
                    }],
                    listeners: {
                        tabchange: function (tabPanel, newCard, oldCard, obj) {
                        	
                        }
                    }
                }, {
                    title: '区域报表',
                    id: 'importReportUnitProductionReportTemplatePanel_Id',
                    xtype: 'tabpanel',
                    activeTab: 0,
                    border: false,
                    tabPosition: 'top',
                    items: [{
                        title: '日报表',
                        id: 'importReportUnitProductionRangeReportTemplatePanel_Id',
                        layout: "border",
                        border: false,
                        items: [{
                            region: 'west',
                            width: '16%',
                            layout: 'fit',
                            border: false,
                            id: 'importReportUnitProductionReportTemplateListPanel_Id',
                            title: '报表模板列表',
                            header: false,
                            split: true,
                            collapsible: true
                    	}, {
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
                                    	
                                    }
                                }
                        	}, {
                                region: 'south',
                                height: '50%',
                                title: '单井日报表内容配置',
                                collapsible: true,
                                split: true,
                                layout: 'fit',
                                border: false,
                                id: "ModbusProtocolProductionReportUnitContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html: '<div class="ModbusProtocolProductionReportUnitContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ModbusProtocolProductionReportUnitContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	
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
            		Ext.Msg.alert("提示", "加载成功 ");
            	}else{
            		Ext.Msg.alert("提示", "上传数据格式有误！ ");
            	}
            	
            	
            	var importReportUnitContentTreeGridPanel = Ext.getCmp("ImportReportUnitContentTreeGridPanel_Id");
            	if (isNotVal(importReportUnitContentTreeGridPanel)) {
            		importReportUnitContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportReportUnitContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert("提示", "【<font color=red>上传失败 </font>】");
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
		"onclick=saveSingelImportedReportUnit(\""+unitName+"\")>保存...</a>";
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
				Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
			}else{
				Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
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
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
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
				Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
			}else{
				Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
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
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
		}
	});
}