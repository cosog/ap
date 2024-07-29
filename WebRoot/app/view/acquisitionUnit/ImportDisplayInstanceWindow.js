Ext.define("AP.view.acquisitionUnit.ImportDisplayInstanceWindow", {
    extend: 'Ext.window.Window',
    id: 'ImportDisplayInstanceWindow_Id',
    alias: 'widget.ImportDisplayInstanceWindow',
    layout: 'fit',
    title: '显示实例导入',
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
                id: 'DisplayInstanceImportForm_Id',
                width: 300,
                bodyPadding: 0,
                frame: true,
                items: [{
                    xtype: 'filefield',
                    id: 'DisplayInstanceImportFilefield_Id',
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
                            submitImportedDisplayInstanceFile();
                        }
                    }
        	    }, {
                    id: 'ImportDisplayInstanceSelectItemType_Id',
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }, {
                    id: 'ImportDisplayInstanceSelectItemId_Id',
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		}, {
                xtype: 'label',
                id: 'ImportDisplayInstanceWinTabLabel_Id',
                hidden: true,
                html: ''
            }, {
                xtype: "hidden",
                id: 'ImportDisplayInstanceWinDeviceType_Id',
                value: '0'
			}, '->', {
                xtype: 'button',
                text: '全部保存',
                iconCls: 'save',
                handler: function (v, o) {
                    var treeStore = Ext.getCmp("ImportDisplayInstanceContentTreeGridPanel_Id").getStore();
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
                            info += overlayCount + "个实例已存在";
                            if (collisionCount > 0) {
                                info += "，";
                            }
                        }
                        if (collisionCount > 0) {
                            info += overlayCount + "个实例无权限修改";
                        }
                        info += "！是否执行全部保存？";

                        Ext.Msg.confirm('提示', info, function (btn) {
                            if (btn == "yes") {
                                saveAllImportedDisplayInstance();
                            }
                        });
                    } else {
                        saveAllImportedDisplayInstance();
                    }
                }
    	    }],
            layout: 'border',
            items: [{
                region: 'west',
                width: '25%',
                title: '上传实例列表',
                layout: 'fit',
                split: true,
                collapsible: true,
                id: "importDisplayInstanceTreePanel_Id"
            }, {
            	border: true,
            	region: 'center',
            	layout: "border",
            	items: [{
            		region: 'center',
            		layout: "border",
            		items: [{
                		region: 'center',
                		title:'采集项配置',
                		id:"importDisplayInstanceAcqItemsConfigTableInfoPanel_Id",
                        layout: 'fit',
                        html:'<div class="importDisplayInstanceAcqItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayInstanceAcqItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	
                            }
                        }
                	},{
                		region: 'south',
                    	height:'50%',
                    	title:'控制项配置',
                		id:"importDisplayInstanceCtrlItemsConfigTableInfoPanel_Id",
                        layout: 'fit',
                        collapsible: true,
                        split: true,
                        html:'<div class="importDisplayInstanceCtrlItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayInstanceCtrlItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	
                            }
                        }
                	}]
            	},{
            		region: 'east',
            		width:'50%',
            		layout: "border",
            		header: false,
            		split: true,
                    collapsible: true,
                	items: [{
                		region: 'center',
                    	layout: 'fit',
                    	title:'录入项配置',
                		id:"importDisplayInstanceInputItemsConfigTableInfoPanel_Id",
                        html:'<div class="importDisplayInstanceInputItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayInstanceInputItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	
                            }
                        }
                	},{
                		region: 'south',
                    	height:'50%',
                    	layout: 'fit',
                        collapsible: true,
                        split: true,
                    	title:'计算项配置',
                    	id:"importDisplayInstanceCalItemsConfigTableInfoPanel_Id",
                        html:'<div class="importDisplayInstanceCalItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importDisplayInstanceCalItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	
                            }
                        }
                	}]
            	}] 
            }],
            listeners: {
                beforeclose: function (panel, eOpts) {
                	clearImportDisplayInstanceHandsontable();
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function clearImportDisplayInstanceHandsontable(){
	
}

function submitImportedDisplayInstanceFile() {
	clearImportDisplayInstanceHandsontable();
	var form = Ext.getCmp("DisplayInstanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedDisplayInstanceFile',
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
            	
            	
            	var importDisplayInstanceContentTreeGridPanel = Ext.getCmp("ImportDisplayInstanceContentTreeGridPanel_Id");
            	if (isNotVal(importDisplayInstanceContentTreeGridPanel)) {
            		importDisplayInstanceContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportDisplayInstanceContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert("提示", "【<font color=red>上传失败 </font>】");
			}
        });
    }
    return false;
};


adviceImportDisplayInstanceCollisionInfoColor = function(val,o,p,e) {
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

iconImportSingleDisplayInstanceAction = function(value, e, record) {
	var resultstring='';
	var instanceName=record.data.text;
	var displayUnitName=record.data.displayUnitName;
	var acqUnitName=record.data.acqUnitName;
	var protocolName=record.data.protocol;

	if( record.data.classes==1 && record.data.saveSign!=2 ){
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedAcqInstance(\""+instanceName+"\",\""+displayUnitName+"\,\""+acqUnitName+"\,\""+protocolName+"\")>保存...</a>";
	}
	return resultstring;
}

function saveSingelImportedDisplayInstance(instanceName,displayUnitName,acqUnitName,protocolName){
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveSingelImportedDisplayInstance',
		method : "POST",
		params : {
			instanceName : instanceName,
			displayUnitName : displayUnitName,
			acqUnitName:acqUnitName,
			protocolName : protocolName
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			if (result.success==true) {
				Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
			}else{
				Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
			}
			Ext.getCmp("ImportDisplayInstanceContentTreeGridPanel_Id").getStore().load();

			var treeGridPanel = Ext.getCmp("ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id");
            if (isNotVal(treeGridPanel)) {
            	treeGridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceTreeInfoStore');
            }
		},
		failure : function() {
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
		}
	});
}

function saveAllImportedDisplayInstance(){
	var unitNameList=[];
	var treeStore = Ext.getCmp("ImportDisplayInstanceContentTreeGridPanel_Id").getStore();
	var count=treeStore.getCount();
	for(var i=0;i<count;i++){
		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign!=2){
			unitNameList.push(treeStore.getAt(i).data.text);
		}
	}
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveAllImportedDisplayInstance',
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
			Ext.getCmp("ImportDisplayInstanceContentTreeGridPanel_Id").getStore().load();

			var treeGridPanel = Ext.getCmp("ModbusProtocolDisplayInstanceConfigTreeGridPanel_Id");
            if (isNotVal(treeGridPanel)) {
            	treeGridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceTreeInfoStore');
            }
		},
		failure : function() {
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
		}
	});
}