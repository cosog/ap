Ext.define("AP.view.acquisitionUnit.ImportAlarmUnitWindow", {
    extend: 'Ext.window.Window',
    id: 'ImportAlarmUnitWindow_Id',
    alias: 'widget.ImportAlarmUnitWindow',
    layout: 'fit',
    title: '报警单元导入',
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
                id: 'AlarmUnitImportForm_Id',
                width: 300,
                bodyPadding: 0,
                frame: true,
                items: [{
                    xtype: 'filefield',
                    id: 'AlarmUnitImportFilefield_Id',
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
                            submitImportedAlarmUnitFile();
                        }
                    }
        	    }, {
                    id: 'ImportAlarmUnitSelectItemType_Id',
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }, {
                    id: 'ImportAlarmUnitSelectItemId_Id',
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		}, {
                xtype: 'label',
                id: 'ImportAlarmUnitWinTabLabel_Id',
                hidden: true,
                html: ''
            }, {
                xtype: "hidden",
                id: 'ImportAlarmUnitWinDeviceType_Id',
                value: '0'
			}, '->', {
                xtype: 'button',
                text: '全部保存',
                iconCls: 'save',
                handler: function (v, o) {
                    var treeStore = Ext.getCmp("ImportAlarmUnitContentTreeGridPanel_Id").getStore();
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
                                saveAllImportedAlarmUnit();
                            }
                        });
                    } else {
                        saveAllImportedAlarmUnit();
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
                id: "importAlarmUnitTreePanel_Id"
            }, {
                border: true,
                region: 'center',
                title: '报警项',
                xtype: 'tabpanel',
                id: "importAlarmUnitItemsConfigTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'top',
                items: [{
                    title: '数据量',
                    region: 'center',
                    layout: "border",
                    id: "importAlarmUnitNumItemsConfigTableInfoPanel_Id",
                    items: [{
                        region: 'center',
                        title: '采集项配置',
                        layout: 'fit',
                        id: 'importAlarmUnitItemsConfigTableInfoPanel_id',
                        html: '<div class="importAlarmUnitItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmUnitItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	
                            }
                        }
              	}, {
                        region: 'south',
                        height: '50%',
                        title: '计算项配置',
                        collapsible: true,
                        split: true,
                        layout: 'fit',
                        id: 'importAlarmUnitCalNumItemsConfigTableInfoPanel_id',
                        html: '<div class="importAlarmUnitCalNumItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmUnitCalNumItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	
                            }
                        }
              	}]
              }, {
                    title: '开关量',
                    id: "importAlarmUnitSwitchItemsConfigTableInfoPanel_Id",
                    layout: "border",
                    border: true,
                    items: [{
                        region: 'west',
                        width: '25%',
                        collapsible: true,
                        split: true,
                        id: 'importAlarmUnitSwitchItemsPanel_Id',
                        title: '开关量列表'
                  }, {
                        region: 'center',
                        title: '开关量报警项配置',
                        layout: 'fit',
                        id: 'importAlarmUnitSwitchItemsConfigHandsontablePanel_id',
                        html: '<div class="importAlarmUnitSwitchItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmUnitSwitchItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	
                            }
                        }
                  }]
              }, {
                    title: '枚举量',
                    id: "importAlarmUnitEnumItemsConfigTableInfoPanel_Id",
                    layout: "border",
                    border: true,
                    items: [{
                        region: 'west',
                        width: '25%',
                        collapsible: true,
                        split: true,
                        id: 'importAlarmUnitEnumItemsPanel_Id',
                        title: '枚举量列表'
                  }, {
                        region: 'center',
                        title: '枚举量报警项配置',
                        layout: 'fit',
                        id: 'importAlarmUnitEnumItemsConfigHandsontablePanel_id',
                        html: '<div class="importAlarmUnitEnumItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmUnitEnumItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	
                            }
                        }
                  }]
              }, {
                    title: '工况诊断',
                    id: "importAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id",
                    layout: 'fit',
                    html: '<div class="importAlarmUnitFESDiagramConditionsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmUnitFESDiagramConditionsConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	
                        }
                    }
              }, {
                    title: '运行状态',
                    id: "importAlarmUnitRunStatusConfigTableInfoPanel_Id",
                    layout: 'fit',
                    html: '<div class="importAlarmUnitRunStatusItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmUnitRunStatusItemsConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	
                        }
                    }
              }, {
                    title: '通信状态',
                    id: "importAlarmUnitCommStatusConfigTableInfoPanel_Id",
                    layout: 'fit',
                    html: '<div class="importAlarmUnitCommStatusItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmUnitCommStatusItemsConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	
                        }
                    }
              }],
                listeners: {
                    tabchange: function (tabPanel, newCard, oldCard, obj) {
                        if (newCard.id == "importAlarmUnitNumItemsConfigTableInfoPanel_Id") {
//                            var selectRow = Ext.getCmp("importAlarmUnitConfigSelectRow_Id").getValue();
//                            var selectedItem = Ext.getCmp("importAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
//                            if (selectedItem.data.classes == 0) {
//                                if (isNotVal(selectedItem.data.children) && selectedItem.data.children.length > 0) {
//                                    CreateProtocolAlarmUnitNumItemsConfigInfoTable(selectedItem.data.children[0].text, selectedItem.data.children[0].classes, selectedItem.data.children[0].code);
//                                    CreateProtocolAlarmUnitCalNumItemsConfigInfoTable(selectedItem.data.deviceType, selectedItem.data.children[0].classes, selectedItem.data.children[0].code);
//                                }
//                            } else if (selectedItem.data.classes == 1) {
//                                CreateProtocolAlarmUnitNumItemsConfigInfoTable(selectedItem.data.text, selectedItem.data.classes, selectedItem.data.code);
//                                CreateProtocolAlarmUnitCalNumItemsConfigInfoTable(selectedItem.data.deviceType, selectedItem.data.classes, selectedItem.data.code);
//                            } else if (selectedItem.data.classes == 2 || selectedItem.data.classes == 3) {
//                                CreateProtocolAlarmUnitNumItemsConfigInfoTable(selectedItem.data.protocol, selectedItem.data.classes, selectedItem.data.code);
//                                CreateProtocolAlarmUnitCalNumItemsConfigInfoTable(selectedItem.data.deviceType, selectedItem.data.classes, selectedItem.data.code);
//                            }
                        } else if (newCard.id == "importAlarmUnitSwitchItemsConfigTableInfoPanel_Id") {
//                            var treePanel = Ext.getCmp("importAlarmUnitSwitchItemsGridPanel_Id");
//                            if (isNotVal(treePanel)) {
//                                treePanel.getStore().load();
//                            } else {
//                                Ext.create('AP.store.acquisitionUnit.importAlarmUnitSwitchItemsStore');
//                            }
                        } else if (newCard.id == "importAlarmUnitEnumItemsConfigTableInfoPanel_Id") {
//                            var gridPanel = Ext.getCmp("importAlarmUnitEnumItemsGridPanel_Id");
//                            if (isNotVal(gridPanel)) {
//                                gridPanel.getStore().load();
//                            } else {
//                                Ext.create('AP.store.acquisitionUnit.importAlarmUnitEnumItemsStore');
//                            }
                        } else if (newCard.id == "importAlarmUnitCommStatusConfigTableInfoPanel_Id") {
//                            var selectRow = Ext.getCmp("importAlarmUnitConfigSelectRow_Id").getValue();
//                            var selectedItem = Ext.getCmp("importAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
//                            if (selectedItem.data.classes == 0) {
//                                if (isNotVal(selectedItem.data.children) && selectedItem.data.children.length > 0) {
//                                    CreateProtocolAlarmUnitCommStatusItemsConfigInfoTable(selectedItem.data.children[0].text, selectedItem.data.children[0].classes, selectedItem.data.children[0].code);
//                                }
//                            } else if (selectedItem.data.classes == 1) {
//                                CreateProtocolAlarmUnitCommStatusItemsConfigInfoTable(selectedItem.data.text, selectedItem.data.classes, selectedItem.data.code);
//                            } else if (selectedItem.data.classes == 2 || selectedItem.data.classes == 3) {
//                                CreateProtocolAlarmUnitCommStatusItemsConfigInfoTable(selectedItem.data.protocol, selectedItem.data.classes, selectedItem.data.code);
//                            }
                        } else if (newCard.id == "importAlarmUnitRunStatusConfigTableInfoPanel_Id") {
//                            var selectRow = Ext.getCmp("importAlarmUnitConfigSelectRow_Id").getValue();
//                            var selectedItem = Ext.getCmp("importAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
//                            if (selectedItem.data.classes == 0) {
//                                if (isNotVal(selectedItem.data.children) && selectedItem.data.children.length > 0) {
//                                    CreateProtocolAlarmUnitRunStatusItemsConfigInfoTable(selectedItem.data.children[0].text, selectedItem.data.children[0].classes, selectedItem.data.children[0].code);
//                                }
//                            } else if (selectedItem.data.classes == 1) {
//                                CreateProtocolAlarmUnitRunStatusItemsConfigInfoTable(selectedItem.data.text, selectedItem.data.classes, selectedItem.data.code);
//                            } else if (selectedItem.data.classes == 2 || selectedItem.data.classes == 3) {
//                                CreateProtocolAlarmUnitRunStatusItemsConfigInfoTable(selectedItem.data.protocol, selectedItem.data.classes, selectedItem.data.code);
//                            }
                        } else if (newCard.id == "importAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id") {
//                            var selectRow = Ext.getCmp("importAlarmUnitConfigSelectRow_Id").getValue();
//                            var selectedItem = Ext.getCmp("importAlarmUnitConfigTreeGridPanel_Id").getStore().getAt(selectRow);
//                            if (selectedItem.data.classes == 0) {
//                                if (isNotVal(selectedItem.data.children) && selectedItem.data.children.length > 0) {
//                                    CreateProtocolAlarmUnitFESDiagramConditionsConfigInfoTable(selectedItem.data.children[0].text, selectedItem.data.children[0].classes, selectedItem.data.children[0].code);
//                                }
//                            } else if (selectedItem.data.classes == 1) {
//                                CreateProtocolAlarmUnitFESDiagramConditionsConfigInfoTable(selectedItem.data.text, selectedItem.data.classes, selectedItem.data.code);
//                            } else if (selectedItem.data.classes == 2 || selectedItem.data.classes == 3) {
//                                CreateProtocolAlarmUnitFESDiagramConditionsConfigInfoTable(selectedItem.data.protocol, selectedItem.data.classes, selectedItem.data.code);
//                            }
                        }
                    }
                }
            }],
            listeners: {
                beforeclose: function (panel, eOpts) {
                	clearImportAlarmUnitHandsontable();
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function clearImportAlarmUnitHandsontable(){
	
}

function submitImportedAlarmUnitFile() {
	clearImportAlarmUnitHandsontable();
	var form = Ext.getCmp("AlarmUnitImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedAlarmUnitFile',
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
            	
            	
            	var importAlarmUnitContentTreeGridPanel = Ext.getCmp("ImportAlarmUnitContentTreeGridPanel_Id");
            	if (isNotVal(importAlarmUnitContentTreeGridPanel)) {
            		importAlarmUnitContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportAlarmUnitContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert("提示", "【<font color=red>上传失败 </font>】");
			}
        });
    }
    return false;
};


adviceImportAlarmUnitCollisionInfoColor = function(val,o,p,e) {
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

iconImportSingleAlarmUnitAction = function(value, e, record) {
	var resultstring='';
	var unitName=record.data.text;
	var protocolName=record.data.protocol;

	if( record.data.classes==1 && record.data.saveSign!=2 ){
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedAlarmUnit(\""+unitName+"\",\""+protocolName+"\")>保存...</a>";
	}
	return resultstring;
}

function saveSingelImportedAlarmUnit(unitName,protocolName){
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveSingelImportedAlarmUnit',
		method : "POST",
		params : {
			unitName : unitName,
			protocolName : protocolName
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			if (result.success==true) {
				Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
			}else{
				Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
			}
			Ext.getCmp("ImportAlarmUnitContentTreeGridPanel_Id").getStore().load();

			var treeGridPanel = Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id");
            if (isNotVal(treeGridPanel)) {
            	treeGridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitTreeInfoStore');
            }
		},
		failure : function() {
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
		}
	});
}

function saveAllImportedAlarmUnit(){
	var unitNameList=[];
	var treeStore = Ext.getCmp("ImportAlarmUnitContentTreeGridPanel_Id").getStore();
	var count=treeStore.getCount();
	for(var i=0;i<count;i++){
		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign!=2){
			unitNameList.push(treeStore.getAt(i).data.text);
		}
	}
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveAllImportedAlarmUnit',
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
			Ext.getCmp("ImportAlarmUnitContentTreeGridPanel_Id").getStore().load();

			var treeGridPanel = Ext.getCmp("ModbusProtocolAlarmUnitConfigTreeGridPanel_Id");
            if (isNotVal(treeGridPanel)) {
            	treeGridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitTreeInfoStore');
            }
		},
		failure : function() {
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
		}
	});
}