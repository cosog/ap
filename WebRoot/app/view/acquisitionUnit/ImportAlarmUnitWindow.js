var importAlarmUnitConfigNumItemsHandsontableHelper=null;
var importAlarmUnitConfigSwitchItemsHandsontableHelper=null
var importAlarmUnitConfigEnumItemsHandsontableHelper=null;

var importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper=null;
var importAlarmUnitConfigRunStatusItemsHandsontableHelper=null;
var importAlarmUnitConfigCommStatusItemsHandsontableHelper=null;

var importAlarmUnitConfigRightTabPanelItems=[{
    title: loginUserLanguageResource.numericValue,
    region: 'center',
    layout: "border",
    id: "importAlarmUnitNumItemsConfigTableInfoPanel_Id",
    iconCls: 'check3',
    items: [{
        region: 'center',
        title: loginUserLanguageResource.acquisitionItemConfig,
        layout: 'fit',
        id: 'importAlarmUnitNumItemsConfigTableInfoPanel_id',
        html: '<div class="importAlarmUnitNumItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmUnitNumItemsConfigTableInfoDiv_id"></div></div>',
        listeners: {
            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
             	if(importAlarmUnitConfigNumItemsHandsontableHelper!=null && importAlarmUnitConfigNumItemsHandsontableHelper.hot!=undefined){
             		var newWidth=width;
            		var newHeight=height;
            		var header=thisPanel.getHeader();
            		if(header){
            			newHeight=newHeight-header.lastBox.height-2;
            		}
            		importAlarmUnitConfigNumItemsHandsontableHelper.hot.updateSettings({
            			width:newWidth,
            			height:newHeight
            		});
             	}
             }
        }
	}]
}, {
    title: loginUserLanguageResource.switchingValue,
    id: "importAlarmUnitSwitchItemsConfigTableInfoPanel_Id",
    layout: "border",
    border: true,
    items: [{
        region: 'center',
        title: loginUserLanguageResource.alarmItemConfig,
        layout: 'fit',
        id: 'importAlarmUnitSwitchItemsConfigHandsontablePanel_id',
        html: '<div class="importAlarmUnitSwitchItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmUnitSwitchItemsConfigTableInfoDiv_id"></div></div>',
        listeners: {
            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
             	if(importAlarmUnitConfigSwitchItemsHandsontableHelper!=null && importAlarmUnitConfigSwitchItemsHandsontableHelper.hot!=undefined){
             		var newWidth=width;
            		var newHeight=height;
            		var header=thisPanel.getHeader();
            		if(header){
            			newHeight=newHeight-header.lastBox.height-2;
            		}
            		importAlarmUnitConfigSwitchItemsHandsontableHelper.hot.updateSettings({
            			width:newWidth,
            			height:newHeight
            		});
             	}
             }
        }
  }]
}, {
    title: loginUserLanguageResource.enumValue,
    id: "importAlarmUnitEnumItemsConfigTableInfoPanel_Id",
    layout: "border",
    border: true,
    items: [{
        region: 'center',
        title: loginUserLanguageResource.alarmItemConfig,
        layout: 'fit',
        id: 'importAlarmUnitEnumItemsConfigHandsontablePanel_id',
        html: '<div class="importAlarmUnitEnumItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmUnitEnumItemsConfigTableInfoDiv_id"></div></div>',
        listeners: {
            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
             	if(importAlarmUnitConfigEnumItemsHandsontableHelper!=null && importAlarmUnitConfigEnumItemsHandsontableHelper.hot!=undefined){
             		var newWidth=width;
            		var newHeight=height;
            		var header=thisPanel.getHeader();
            		if(header){
            			newHeight=newHeight-header.lastBox.height-2;
            		}
            		importAlarmUnitConfigEnumItemsHandsontableHelper.hot.updateSettings({
            			width:newWidth,
            			height:newHeight
            		});
             	}
             }
        }
  }]
}, {
    title: loginUserLanguageResource.workType,
    id: "importAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id",
    layout: 'fit',
    html: '<div class="importAlarmUnitFESDiagramConditionsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmUnitFESDiagramConditionsConfigTableInfoDiv_id"></div></div>',
    listeners: {
        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
         	if(importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper!=null && importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.hot!=undefined){
         		var newWidth=width;
        		var newHeight=height;
        		var header=thisPanel.getHeader();
        		if(header){
        			newHeight=newHeight-header.lastBox.height-2;
        		}
        		importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.hot.updateSettings({
        			width:newWidth,
        			height:newHeight
        		});
         	}
         }
    }
}, {
    title: loginUserLanguageResource.runStatus,
    id: "importAlarmUnitRunStatusConfigTableInfoPanel_Id",
    layout: 'fit',
    html: '<div class="importAlarmUnitRunStatusItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmUnitRunStatusItemsConfigTableInfoDiv_id"></div></div>',
    listeners: {
        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
         	if(importAlarmUnitConfigRunStatusItemsHandsontableHelper!=null && importAlarmUnitConfigRunStatusItemsHandsontableHelper.hot!=undefined){
         		var newWidth=width;
        		var newHeight=height;
        		var header=thisPanel.getHeader();
        		if(header){
        			newHeight=newHeight-header.lastBox.height-2;
        		}
        		importAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.updateSettings({
        			width:newWidth,
        			height:newHeight
        		});
         	}
         }
    }
}, {
    title: loginUserLanguageResource.commStatus,
    id: "importAlarmUnitCommStatusConfigTableInfoPanel_Id",
    layout: 'fit',
    html: '<div class="importAlarmUnitCommStatusItemsConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmUnitCommStatusItemsConfigTableInfoDiv_id"></div></div>',
    listeners: {
        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
         	if(importAlarmUnitConfigCommStatusItemsHandsontableHelper!=null && importAlarmUnitConfigCommStatusItemsHandsontableHelper.hot!=undefined){
         		var newWidth=width;
        		var newHeight=height;
        		var header=thisPanel.getHeader();
        		if(header){
        			newHeight=newHeight-header.lastBox.height-2;
        		}
        		importAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.updateSettings({
        			width:newWidth,
        			height:newHeight
        		});
         	}
         }
    }
}];
Ext.define("AP.view.acquisitionUnit.ImportAlarmUnitWindow", {
    extend: 'Ext.window.Window',
    id: 'ImportAlarmUnitWindow_Id',
    alias: 'widget.ImportAlarmUnitWindow',
    layout: 'fit',
    title: loginUserLanguageResource.importAlarmUnit,
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
                    fieldLabel: loginUserLanguageResource.uploadFile,
                    labelWidth: 60,
                    width: '100%',
                    msgTarget: 'side',
                    allowBlank: true,
                    anchor: '100%',
                    draggable: true,
                    buttonText: loginUserLanguageResource.selectUploadFile,
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
                text: loginUserLanguageResource.saveAll,
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

                        Ext.Msg.confirm(loginUserLanguageResource.tip, info, function (btn) {
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
                title: loginUserLanguageResource.uploadUnitList,
                layout: 'fit',
                split: true,
                collapsible: true,
                id: "importAlarmUnitTreePanel_Id"
            }, {
                border: true,
                region: 'center',
                xtype: 'tabpanel',
                id: "importAlarmUnitItemsConfigTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'top',
                header:false,
                items: importAlarmUnitConfigRightTabPanelItems,
                listeners: {
                	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
            			oldCard.setIconCls(null);
            			newCard.setIconCls('check3');
            		},
                    tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	var selection=Ext.getCmp("ImportAlarmUnitContentTreeGridPanel_Id").getSelectionModel().getSelection();
                    	if(selection.length>0){
                    		var record = selection[0];
                    		if(record.data.classes!=1){
                        		clearImportAlarmUnitHandsontable();
                        	}else{
                        		var activeId = newCard.id;
                            	if(activeId=="importAlarmUnitNumItemsConfigTableInfoPanel_Id"){
                            		CreateImportAlarmUnitNumItemsConfigInfoTable(record.data.protocol,record.data.text,record.data.calculateType);
                            	}else if(activeId=="importAlarmUnitSwitchItemsConfigTableInfoPanel_Id"){
                            		CreateImportAlarmUnitSwitchItemsConfigInfoTable(record.data.protocol,record.data.text,record.data.calculateType);
                            	}else if(activeId=="importAlarmUnitEnumItemsConfigTableInfoPanel_Id"){
                            		CreateImportAlarmUnitEnumItemsConfigInfoTable(record.data.protocol,record.data.text,record.data.calculateType);
                            	}else if(activeId=="importAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id"){
                            		CreateImportAlarmUnitFESDiagramResultItemsConfigInfoTable(record.data.protocol,record.data.text,record.data.calculateType);
                            	}else if(activeId=="importAlarmUnitRunStatusConfigTableInfoPanel_Id"){
                            		CreateImportAlarmUnitRunStatusItemsConfigInfoTable(record.data.protocol,record.data.text,record.data.calculateType);
                            	}else if(activeId=="importAlarmUnitCommStatusConfigTableInfoPanel_Id"){
                            		CreateImportAlarmUnitCommStatusItemsConfigInfoTable(record.data.protocol,record.data.text,record.data.calculateType);
                            	}
                        	}
                    	}else{
                    		clearImportAlarmUnitHandsontable();
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
	if(importAlarmUnitConfigNumItemsHandsontableHelper!=null){
		if(importAlarmUnitConfigNumItemsHandsontableHelper.hot!=undefined){
			importAlarmUnitConfigNumItemsHandsontableHelper.hot.destroy();
		}
		importAlarmUnitConfigNumItemsHandsontableHelper=null;
	}
	
	if(importAlarmUnitConfigSwitchItemsHandsontableHelper!=null){
		if(importAlarmUnitConfigSwitchItemsHandsontableHelper.hot!=undefined){
			importAlarmUnitConfigSwitchItemsHandsontableHelper.hot.destroy();
		}
		importAlarmUnitConfigSwitchItemsHandsontableHelper=null;
	}
	
	if(importAlarmUnitConfigEnumItemsHandsontableHelper!=null){
		if(importAlarmUnitConfigEnumItemsHandsontableHelper.hot!=undefined){
			importAlarmUnitConfigEnumItemsHandsontableHelper.hot.destroy();
		}
		importAlarmUnitConfigEnumItemsHandsontableHelper=null;
	}
	
	if(importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper!=null){
		if(importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.hot!=undefined){
			importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.hot.destroy();
		}
		importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper=null;
	}
	
	if(importAlarmUnitConfigRunStatusItemsHandsontableHelper!=null){
		if(importAlarmUnitConfigRunStatusItemsHandsontableHelper.hot!=undefined){
			importAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.destroy();
		}
		importAlarmUnitConfigRunStatusItemsHandsontableHelper=null;
	}
	
	if(importAlarmUnitConfigCommStatusItemsHandsontableHelper!=null){
		if(importAlarmUnitConfigCommStatusItemsHandsontableHelper.hot!=undefined){
			importAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.destroy();
		}
		importAlarmUnitConfigCommStatusItemsHandsontableHelper=null;
	}
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
            		Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
            	}else{
            		Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
            	}
            	
            	
            	var importAlarmUnitContentTreeGridPanel = Ext.getCmp("ImportAlarmUnitContentTreeGridPanel_Id");
            	if (isNotVal(importAlarmUnitContentTreeGridPanel)) {
            		importAlarmUnitContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportAlarmUnitContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
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
	if( record.data.classes==1 && record.data.saveSign!=2 ){
		var unitName=record.data.text;
		var protocolName=record.data.protocol;
		var saveSign=record.data.saveSign;
		var msg=record.data.msg;
		
		unitName = encodeURIComponent(unitName || '');
		protocolName = encodeURIComponent(protocolName || '');
		saveSign = encodeURIComponent(saveSign || '');
		msg = encodeURIComponent(msg || '');
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedAlarmUnit('"+unitName+"','"+protocolName+"','"+saveSign+"','"+msg+"')>"+loginUserLanguageResource.save+"...</a>";
	}
	return resultstring;
}

function saveSingelImportedAlarmUnit(unitName,protocolName,saveSign,msg){
	unitName = decodeURIComponent(unitName);
	protocolName = decodeURIComponent(protocolName);
	saveSign = decodeURIComponent(saveSign);
	msg = decodeURIComponent(msg);
	if(parseInt(saveSign)>0){
		Ext.Msg.confirm(loginUserLanguageResource.tip, msg,function (btn) {
			if (btn == "yes") {
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
							Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
						}else{
							Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
						}
						Ext.getCmp("ImportAlarmUnitContentTreeGridPanel_Id").getStore().load();

			    		var treePanel=Ext.getCmp("AlarmUnitProtocolTreeGridPanel_Id");
			    		if(isNotVal(treePanel)){
			    			treePanel.getStore().load();
			    		}else{
			    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitProtocolTreeInfoStore');
			    		}
					},
					failure : function() {
						Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
					}
				});
			}
		});
	}else{
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
					Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
				}else{
					Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
				}
				Ext.getCmp("ImportAlarmUnitContentTreeGridPanel_Id").getStore().load();

	    		var treePanel=Ext.getCmp("AlarmUnitProtocolTreeGridPanel_Id");
	    		if(isNotVal(treePanel)){
	    			treePanel.getStore().load();
	    		}else{
	    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitProtocolTreeInfoStore');
	    		}
			},
			failure : function() {
				Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
			}
		});
	}
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
				Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
			}else{
				Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
			}
			Ext.getCmp("ImportAlarmUnitContentTreeGridPanel_Id").getStore().load();

    		var treePanel=Ext.getCmp("AlarmUnitProtocolTreeGridPanel_Id");
    		if(isNotVal(treePanel)){
    			treePanel.getStore().load();
    		}else{
    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmUnitProtocolTreeInfoStore');
    		}
		},
		failure : function() {
			Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
		}
	});
}

function CreateImportAlarmUnitNumItemsConfigInfoTable(protocolName,unitName,calculateType){
	Ext.getCmp("importAlarmUnitNumItemsConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportAlarmUnitItemsData',
		success:function(response) {
			Ext.getCmp("importAlarmUnitNumItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("importAlarmUnitItemsConfigTabPanel_Id").setTitle(unitName+"/"+loginUserLanguageResource.numericValue);
			
			var result =  Ext.JSON.decode(response.responseText);
			if(importAlarmUnitConfigNumItemsHandsontableHelper==null || importAlarmUnitConfigNumItemsHandsontableHelper.hot==undefined){
				importAlarmUnitConfigNumItemsHandsontableHelper = ImportAlarmUnitConfigNumItemsHandsontableHelper.createNew("importAlarmUnitNumItemsConfigTableInfoDiv_id");
				var colHeaders=[loginUserLanguageResource.idx,loginUserLanguageResource.name,
					loginUserLanguageResource.unit,loginUserLanguageResource.dataSource,
					loginUserLanguageResource.upperLimit,loginUserLanguageResource.lowerLimit,loginUserLanguageResource.hystersis,
					loginUserLanguageResource.delay+"(s)",
					loginUserLanguageResource.retriggerTime+"(s)",
					loginUserLanguageResource.alarmLevel,loginUserLanguageResource.alarmSign,
					loginUserLanguageResource.isSendMessage,loginUserLanguageResource.isSendEmail];
				var columns="[{data:'id'}," 
					+"{data:'title'},"
					+"{data:'unit'},"
					+"{data:'dataSource'},"
					+"{data:'upperLimit',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigNumItemsHandsontableHelper);}},"
					+"{data:'lowerLimit',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigNumItemsHandsontableHelper);}}," 
					
					+"{data:'hystersis',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigNumItemsHandsontableHelper);}}," 
					+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigNumItemsHandsontableHelper);}}," 
					+"{data:'retriggerTime',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigNumItemsHandsontableHelper);}}," 
					
					+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.normal+"','"+loginUserLanguageResource.alarmLevel1+"','"+loginUserLanguageResource.alarmLevel2+"','"+loginUserLanguageResource.alarmLevel3+"']}," 
					+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"','"+loginUserLanguageResource.disable+"']}," 
					+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.yes+"','"+loginUserLanguageResource.no+"']}," 
					+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.yes+"','"+loginUserLanguageResource.no+"']}" 
					+"]";
				importAlarmUnitConfigNumItemsHandsontableHelper.colHeaders=colHeaders;
				importAlarmUnitConfigNumItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importAlarmUnitConfigNumItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmUnitConfigNumItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importAlarmUnitConfigNumItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmUnitConfigNumItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importAlarmUnitNumItemsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			unitName:unitName,
			alarmType:2,
			calculateType:calculateType
        }
	});
};

var ImportAlarmUnitConfigNumItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importAlarmUnitConfigNumItemsHandsontableHelper = {};
	        importAlarmUnitConfigNumItemsHandsontableHelper.hot1 = '';
	        importAlarmUnitConfigNumItemsHandsontableHelper.divid = divid;
	        importAlarmUnitConfigNumItemsHandsontableHelper.validresult=true;//数据校验
	        importAlarmUnitConfigNumItemsHandsontableHelper.colHeaders=[];
	        importAlarmUnitConfigNumItemsHandsontableHelper.columns=[];
	        importAlarmUnitConfigNumItemsHandsontableHelper.AllData=[];
	        
	        importAlarmUnitConfigNumItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importAlarmUnitConfigNumItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importAlarmUnitConfigNumItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAlarmUnitConfigNumItemsHandsontableHelper.divid);
	        	importAlarmUnitConfigNumItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,120,80,80,80,80,80,100,100,120,120,120,120],
	                columns:importAlarmUnitConfigNumItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importAlarmUnitConfigNumItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(importAlarmUnitConfigNumItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importAlarmUnitConfigNumItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importAlarmUnitConfigNumItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 
	                		&& importAlarmUnitConfigNumItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importAlarmUnitConfigNumItemsHandsontableHelper!=null
	                		&& importAlarmUnitConfigNumItemsHandsontableHelper.hot!=''
	                		&& importAlarmUnitConfigNumItemsHandsontableHelper.hot!=undefined 
	                		&& importAlarmUnitConfigNumItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importAlarmUnitConfigNumItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importAlarmUnitConfigNumItemsHandsontableHelper;
	    }
};

function CreateImportAlarmUnitSwitchItemsConfigInfoTable(protocolName,unitName,calculateType){
	Ext.getCmp("importAlarmUnitSwitchItemsConfigHandsontablePanel_id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportAlarmUnitItemsData',
		success:function(response) {
			Ext.getCmp("importAlarmUnitSwitchItemsConfigHandsontablePanel_id").getEl().unmask();
			Ext.getCmp("importAlarmUnitItemsConfigTabPanel_Id").setTitle(unitName+"/"+loginUserLanguageResource.switchingValue);
			var result =  Ext.JSON.decode(response.responseText);
			if(importAlarmUnitConfigSwitchItemsHandsontableHelper==null || importAlarmUnitConfigSwitchItemsHandsontableHelper.hot==undefined){
				importAlarmUnitConfigSwitchItemsHandsontableHelper = ImportAlarmUnitConfigSwitchItemsHandsontableHelper.createNew("importAlarmUnitSwitchItemsConfigTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.bit+"','"+loginUserLanguageResource.meaning+"','"+loginUserLanguageResource.switchItemAlarmValue+"','"+loginUserLanguageResource.delay+"(s)','"+loginUserLanguageResource.retriggerTime+"(s)','"+loginUserLanguageResource.alarmLevel+"','"+loginUserLanguageResource.alarmSign+"','"+loginUserLanguageResource.isSendMessage+"','"+loginUserLanguageResource.isSendEmail+"']";
				var columns="[{data:'id'}," 
					+"{data:'bitIndex',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigSwitchItemsHandsontableHelper);}}," 
					+"{data:'meaning'},"
					+"{data:'value',type:'dropdown',strict:true,allowInvalid:false,source:['开','关']},"
					+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigSwitchItemsHandsontableHelper);}}," 
					+"{data:'retriggerTime',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigSwitchItemsHandsontableHelper);}}," 
					+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.normal+"','"+loginUserLanguageResource.alarmLevel1+"','"+loginUserLanguageResource.alarmLevel2+"','"+loginUserLanguageResource.alarmLevel3+"']}," 
					+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"','"+loginUserLanguageResource.disable+"']}," 
					+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.yes+"','"+loginUserLanguageResource.no+"']}," 
					+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.yes+"','"+loginUserLanguageResource.no+"']}" 
					+"]";
				importAlarmUnitConfigSwitchItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importAlarmUnitConfigSwitchItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importAlarmUnitConfigSwitchItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmUnitConfigSwitchItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importAlarmUnitConfigSwitchItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmUnitConfigSwitchItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importAlarmUnitSwitchItemsConfigHandsontablePanel_id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			unitName:unitName,
			alarmType:0,
			calculateType:calculateType
        }
	});
};

var ImportAlarmUnitConfigSwitchItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importAlarmUnitConfigSwitchItemsHandsontableHelper = {};
	        importAlarmUnitConfigSwitchItemsHandsontableHelper.hot1 = '';
	        importAlarmUnitConfigSwitchItemsHandsontableHelper.divid = divid;
	        importAlarmUnitConfigSwitchItemsHandsontableHelper.validresult=true;//数据校验
	        importAlarmUnitConfigSwitchItemsHandsontableHelper.colHeaders=[];
	        importAlarmUnitConfigSwitchItemsHandsontableHelper.columns=[];
	        importAlarmUnitConfigSwitchItemsHandsontableHelper.AllData=[];
	        
	        importAlarmUnitConfigSwitchItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importAlarmUnitConfigSwitchItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importAlarmUnitConfigSwitchItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAlarmUnitConfigSwitchItemsHandsontableHelper.divid);
	        	importAlarmUnitConfigSwitchItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,50,120,80,100,100,120,120,120,120],
	                columns:importAlarmUnitConfigSwitchItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importAlarmUnitConfigSwitchItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(importAlarmUnitConfigSwitchItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importAlarmUnitConfigSwitchItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importAlarmUnitConfigSwitchItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 
			                	&& importAlarmUnitConfigSwitchItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importAlarmUnitConfigSwitchItemsHandsontableHelper!=null
	                		&& importAlarmUnitConfigSwitchItemsHandsontableHelper.hot!=''
	                		&& importAlarmUnitConfigSwitchItemsHandsontableHelper.hot!=undefined 
	                		&& importAlarmUnitConfigSwitchItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importAlarmUnitConfigSwitchItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importAlarmUnitConfigSwitchItemsHandsontableHelper;
	    }
};

function CreateImportAlarmUnitEnumItemsConfigInfoTable(protocolName,unitName,calculateType){
	Ext.getCmp("importAlarmUnitEnumItemsConfigHandsontablePanel_id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportAlarmUnitItemsData',
		success:function(response) {
			Ext.getCmp("importAlarmUnitEnumItemsConfigHandsontablePanel_id").getEl().unmask();
			Ext.getCmp("importAlarmUnitItemsConfigTabPanel_Id").setTitle(unitName+"/"+loginUserLanguageResource.enumValue);
			var result =  Ext.JSON.decode(response.responseText);
			if(importAlarmUnitConfigEnumItemsHandsontableHelper==null || importAlarmUnitConfigEnumItemsHandsontableHelper.hot==undefined){
				importAlarmUnitConfigEnumItemsHandsontableHelper = ImportAlarmUnitConfigEnumItemsHandsontableHelper.createNew("importAlarmUnitEnumItemsConfigTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.value+"','"+loginUserLanguageResource.meaning+"','"+loginUserLanguageResource.delay+"(s)','"+loginUserLanguageResource.retriggerTime+"(s)','"+loginUserLanguageResource.alarmLevel+"','"+loginUserLanguageResource.alarmSign+"','"+loginUserLanguageResource.isSendMessage+"','"+loginUserLanguageResource.isSendEmail+"']";
				var columns="[{data:'id'}," 
					+"{data:'value',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigEnumItemsHandsontableHelper);}}," 
					+"{data:'meaning'},"
					+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigEnumItemsHandsontableHelper);}}," 
					+"{data:'retriggerTime',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigEnumItemsHandsontableHelper);}}," 
					+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.normal+"','"+loginUserLanguageResource.alarmLevel1+"','"+loginUserLanguageResource.alarmLevel2+"','"+loginUserLanguageResource.alarmLevel3+"']}," 
					+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"','"+loginUserLanguageResource.disable+"']}," 
					+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.yes+"','"+loginUserLanguageResource.no+"']}," 
					+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.yes+"','"+loginUserLanguageResource.no+"']}" 
					+"]";
				importAlarmUnitConfigEnumItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importAlarmUnitConfigEnumItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importAlarmUnitConfigEnumItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmUnitConfigEnumItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importAlarmUnitConfigEnumItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmUnitConfigEnumItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importAlarmUnitEnumItemsConfigHandsontablePanel_id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			unitName:unitName,
			alarmType:1,
			calculateType:calculateType
        }
	});
};

var ImportAlarmUnitConfigEnumItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importAlarmUnitConfigEnumItemsHandsontableHelper = {};
	        importAlarmUnitConfigEnumItemsHandsontableHelper.hot1 = '';
	        importAlarmUnitConfigEnumItemsHandsontableHelper.divid = divid;
	        importAlarmUnitConfigEnumItemsHandsontableHelper.validresult=true;//数据校验
	        importAlarmUnitConfigEnumItemsHandsontableHelper.colHeaders=[];
	        importAlarmUnitConfigEnumItemsHandsontableHelper.columns=[];
	        importAlarmUnitConfigEnumItemsHandsontableHelper.AllData=[];
	        
	        importAlarmUnitConfigEnumItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importAlarmUnitConfigEnumItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importAlarmUnitConfigEnumItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAlarmUnitConfigEnumItemsHandsontableHelper.divid);
	        	importAlarmUnitConfigEnumItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,50,120,100,100,120,120,120,120],
	                columns:importAlarmUnitConfigEnumItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importAlarmUnitConfigEnumItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(importAlarmUnitConfigEnumItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importAlarmUnitConfigEnumItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importAlarmUnitConfigEnumItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 
			                	&& importAlarmUnitConfigEnumItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importAlarmUnitConfigEnumItemsHandsontableHelper!=null
	                		&& importAlarmUnitConfigEnumItemsHandsontableHelper.hot!=''
	                		&& importAlarmUnitConfigEnumItemsHandsontableHelper.hot!=undefined 
	                		&& importAlarmUnitConfigEnumItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importAlarmUnitConfigEnumItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importAlarmUnitConfigEnumItemsHandsontableHelper;
	    }
};

function CreateImportAlarmUnitFESDiagramResultItemsConfigInfoTable(protocolName,unitName,calculateType){
	Ext.getCmp("importAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportAlarmUnitItemsData',
		success:function(response) {
			Ext.getCmp("importAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("importAlarmUnitItemsConfigTabPanel_Id").setTitle(unitName+"/"+loginUserLanguageResource.workType);
			var result =  Ext.JSON.decode(response.responseText);
			if(importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper==null || importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.hot==undefined){
				importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper = ImportAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.createNew("importAlarmUnitFESDiagramConditionsConfigTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.delay+"(s)','"+loginUserLanguageResource.retriggerTime+"(s)','"+loginUserLanguageResource.alarmLevel+"','"+loginUserLanguageResource.alarmSign+"','"+loginUserLanguageResource.isSendMessage+"','"+loginUserLanguageResource.isSendEmail+"']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper);}},"
					 	+"{data:'retriggerTime',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper);}}," 
					 	+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.normal+"','"+loginUserLanguageResource.alarmLevel1+"','"+loginUserLanguageResource.alarmLevel2+"','"+loginUserLanguageResource.alarmLevel3+"']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"','"+loginUserLanguageResource.disable+"']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.yes+"','"+loginUserLanguageResource.no+"']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.yes+"','"+loginUserLanguageResource.no+"']}" 
						+"]";
				importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importAlarmUnitFESDiagramConditionsConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			unitName:unitName,
			alarmType:4,
			calculateType:calculateType
        }
	});
};

var ImportAlarmUnitConfigFESDiagramResultItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper = {};
	        importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.hot1 = '';
	        importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.divid = divid;
	        importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.validresult=true;//数据校验
	        importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.colHeaders=[];
	        importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.columns=[];
	        importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.AllData=[];
	        
	        importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.divid);
	        	importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,80,100,100,120,120,120,120],
	                columns:importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 
			                	&& importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper!=null
	                		&& importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.hot!=''
	                		&& importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.hot!=undefined 
	                		&& importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importAlarmUnitConfigFESDiagramResultItemsHandsontableHelper;
	    }
};

function CreateImportAlarmUnitRunStatusItemsConfigInfoTable(protocolName,unitName,calculateType){
	Ext.getCmp("importAlarmUnitRunStatusConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportAlarmUnitItemsData',
		success:function(response) {
			Ext.getCmp("importAlarmUnitRunStatusConfigTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("importAlarmUnitItemsConfigTabPanel_Id").setTitle(unitName+"/"+loginUserLanguageResource.runStatus);
			var result =  Ext.JSON.decode(response.responseText);
			if(importAlarmUnitConfigRunStatusItemsHandsontableHelper==null || importAlarmUnitConfigRunStatusItemsHandsontableHelper.hot==undefined){
				importAlarmUnitConfigRunStatusItemsHandsontableHelper = ImportAlarmUnitConfigRunStatusItemsHandsontableHelper.createNew("importAlarmUnitRunStatusItemsConfigTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.delay+"(s)','"+loginUserLanguageResource.retriggerTime+"(s)','"+loginUserLanguageResource.alarmLevel+"','"+loginUserLanguageResource.alarmSign+"','"+loginUserLanguageResource.isSendMessage+"','"+loginUserLanguageResource.isSendEmail+"']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigRunStatusItemsHandsontableHelper);}},"
					 	+"{data:'retriggerTime',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigRunStatusItemsHandsontableHelper);}}," 
					 	+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.normal+"','"+loginUserLanguageResource.alarmLevel1+"','"+loginUserLanguageResource.alarmLevel2+"','"+loginUserLanguageResource.alarmLevel3+"']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"','"+loginUserLanguageResource.disable+"']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.yes+"','"+loginUserLanguageResource.no+"']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.yes+"','"+loginUserLanguageResource.no+"']}"
						+"]";
				importAlarmUnitConfigRunStatusItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importAlarmUnitConfigRunStatusItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importAlarmUnitConfigRunStatusItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmUnitConfigRunStatusItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importAlarmUnitRunStatusConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			unitName:unitName,
			alarmType:6,
			calculateType:calculateType
        }
	});
};

var ImportAlarmUnitConfigRunStatusItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importAlarmUnitConfigRunStatusItemsHandsontableHelper = {};
	        importAlarmUnitConfigRunStatusItemsHandsontableHelper.hot1 = '';
	        importAlarmUnitConfigRunStatusItemsHandsontableHelper.divid = divid;
	        importAlarmUnitConfigRunStatusItemsHandsontableHelper.validresult=true;//数据校验
	        importAlarmUnitConfigRunStatusItemsHandsontableHelper.colHeaders=[];
	        importAlarmUnitConfigRunStatusItemsHandsontableHelper.columns=[];
	        importAlarmUnitConfigRunStatusItemsHandsontableHelper.AllData=[];
	        
	        importAlarmUnitConfigRunStatusItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importAlarmUnitConfigRunStatusItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importAlarmUnitConfigRunStatusItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAlarmUnitConfigRunStatusItemsHandsontableHelper.divid);
	        	importAlarmUnitConfigRunStatusItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,80,100,100,120,120,120,120],
	                columns:importAlarmUnitConfigRunStatusItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importAlarmUnitConfigRunStatusItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(importAlarmUnitConfigRunStatusItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importAlarmUnitConfigRunStatusItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importAlarmUnitConfigRunStatusItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 
			                	&& importAlarmUnitConfigRunStatusItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importAlarmUnitConfigRunStatusItemsHandsontableHelper!=null
	                		&& importAlarmUnitConfigRunStatusItemsHandsontableHelper.hot!=''
	                		&& importAlarmUnitConfigRunStatusItemsHandsontableHelper.hot!=undefined 
	                		&& importAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importAlarmUnitConfigRunStatusItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importAlarmUnitConfigRunStatusItemsHandsontableHelper;
	    }
};

function CreateImportAlarmUnitCommStatusItemsConfigInfoTable(protocolName,unitName,calculateType){
	Ext.getCmp("importAlarmUnitCommStatusConfigTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportAlarmUnitItemsData',
		success:function(response) {
			Ext.getCmp("importAlarmUnitCommStatusConfigTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("importAlarmUnitItemsConfigTabPanel_Id").setTitle(unitName+"/"+loginUserLanguageResource.commStatus);
			var result =  Ext.JSON.decode(response.responseText);
			if(importAlarmUnitConfigCommStatusItemsHandsontableHelper==null || importAlarmUnitConfigCommStatusItemsHandsontableHelper.hot==undefined){
				importAlarmUnitConfigCommStatusItemsHandsontableHelper = ImportAlarmUnitConfigCommStatusItemsHandsontableHelper.createNew("importAlarmUnitCommStatusItemsConfigTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.delay+"(s)','"+loginUserLanguageResource.retriggerTime+"(s)','"+loginUserLanguageResource.alarmLevel+"','"+loginUserLanguageResource.alarmSign+"','"+loginUserLanguageResource.isSendMessage+"','"+loginUserLanguageResource.isSendEmail+"']";
				var columns="[{data:'id'}," 
						+"{data:'title'},"
					 	+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigCommStatusItemsHandsontableHelper);}},"
					 	+"{data:'retriggerTime',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigCommStatusItemsHandsontableHelper);}}," 
					 	+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.normal+"','"+loginUserLanguageResource.alarmLevel1+"','"+loginUserLanguageResource.alarmLevel2+"','"+loginUserLanguageResource.alarmLevel3+"']}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"','"+loginUserLanguageResource.disable+"']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.yes+"','"+loginUserLanguageResource.no+"']}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.yes+"','"+loginUserLanguageResource.no+"']}" 
						+"]";
				importAlarmUnitConfigCommStatusItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importAlarmUnitConfigCommStatusItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importAlarmUnitConfigCommStatusItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmUnitConfigCommStatusItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importAlarmUnitCommStatusConfigTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			unitName:unitName,
			alarmType:3,
			calculateType
        }
	});
};

var ImportAlarmUnitConfigCommStatusItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importAlarmUnitConfigCommStatusItemsHandsontableHelper = {};
	        importAlarmUnitConfigCommStatusItemsHandsontableHelper.hot1 = '';
	        importAlarmUnitConfigCommStatusItemsHandsontableHelper.divid = divid;
	        importAlarmUnitConfigCommStatusItemsHandsontableHelper.validresult=true;//数据校验
	        importAlarmUnitConfigCommStatusItemsHandsontableHelper.colHeaders=[];
	        importAlarmUnitConfigCommStatusItemsHandsontableHelper.columns=[];
	        importAlarmUnitConfigCommStatusItemsHandsontableHelper.AllData=[];
	        
	        importAlarmUnitConfigCommStatusItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importAlarmUnitConfigCommStatusItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importAlarmUnitConfigCommStatusItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAlarmUnitConfigCommStatusItemsHandsontableHelper.divid);
	        	importAlarmUnitConfigCommStatusItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,80,100,100,120,120,120,120],
	                columns:importAlarmUnitConfigCommStatusItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importAlarmUnitConfigCommStatusItemsHandsontableHelper.colHeaders,//显示列头
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
	                    if(importAlarmUnitConfigCommStatusItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importAlarmUnitConfigCommStatusItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importAlarmUnitConfigCommStatusItemsHandsontableHelper.addCellStyle;
	    	            }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 
			                	&& importAlarmUnitConfigCommStatusItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importAlarmUnitConfigCommStatusItemsHandsontableHelper!=null
	                		&& importAlarmUnitConfigCommStatusItemsHandsontableHelper.hot!=''
	                		&& importAlarmUnitConfigCommStatusItemsHandsontableHelper.hot!=undefined 
	                		&& importAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importAlarmUnitConfigCommStatusItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importAlarmUnitConfigCommStatusItemsHandsontableHelper;
	    }
};