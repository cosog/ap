var importAlarmInstanceConfigNumItemsHandsontableHelper=null;
var importAlarmInstanceConfigCalNumItemsHandsontableHelper=null;
var importAlarmInstanceConfigSwitchItemsHandsontableHelper=null
var importAlarmInstanceConfigEnumItemsHandsontableHelper=null;

var importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper=null;
var importAlarmInstanceConfigRunStatusItemsHandsontableHelper=null;
var importAlarmInstanceConfigCommStatusItemsHandsontableHelper=null;

Ext.define("AP.view.acquisitionUnit.ImportAlarmInstanceWindow", {
    extend: 'Ext.window.Window',
    id:'ImportAlarmInstanceWindow_Id',
    alias: 'widget.ImportAlarmInstanceWindow',
    layout: 'fit',
    title:'报警实例导入',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
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
        		xtype:'form',
        		id:'AlarmInstanceImportForm_Id',
        		width: 300,
        	    bodyPadding: 0,
        	    frame: true,
        	    items: [{
        	    	xtype: 'filefield',
                	id:'AlarmInstanceImportFilefield_Id',
                    name: 'file',
                    fieldLabel: loginUserLanguageResource.uploadFile,
                    labelWidth: 60,
                    width:'100%',
                    msgTarget: 'side',
                    allowBlank: true,
                    anchor: '100%',
                    draggable:true,
                    buttonText: loginUserLanguageResource.selectUploadFile,
                    accept:'.json',
                    listeners:{
                        change:function(cmp){
                        	submitImportedAlarmInstanceFile();
                        }
                    }
        	    },{
                    id: 'ImportAlarmInstanceSelectItemType_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                },{
                    id: 'ImportAlarmInstanceSelectItemId_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		},{
            	xtype: 'label',
            	id: 'ImportAlarmInstanceWinTabLabel_Id',
            	hidden:true,
            	html: ''
            },{
				xtype : "hidden",
				id : 'ImportAlarmInstanceWinDeviceType_Id',
				value:'0'
			},'->',{
    	    	xtype: 'button',
                text: loginUserLanguageResource.saveAll,
                iconCls: 'save',
                handler: function (v, o) {
                	var treeStore = Ext.getCmp("ImportAlarmInstanceContentTreeGridPanel_Id").getStore();
                	var count=treeStore.getCount();
                	var overlayCount=0;
            		var collisionCount=0; 
                	for(var i=0;i<count;i++){
                		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign==1){
                			overlayCount++;
                		}else if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign==2){
                			collisionCount++;
                		}
                	}
                	if(overlayCount>0 || collisionCount>0){
                		var info="";
                		if(overlayCount>0){
                			info+=overlayCount+"个实例已存在";
                			if(collisionCount>0){
                				info+="，";
                			}
                		}
                		if(collisionCount>0){
                			info+=overlayCount+"个实例无权限修改";
                		}
                		info+="！是否执行全部保存？";
                		
                		Ext.Msg.confirm(loginUserLanguageResource.tip, info, function (btn) {
                            if (btn == "yes") {
                            	saveAllImportedAlarmInstance();
                            }
                        });
                	}else{
                		saveAllImportedAlarmInstance();
                	}
                }
    	    }],
            layout: 'border',
            items: [{
            	region: 'west',
            	width:'25%',
            	title:loginUserLanguageResource.instanceList,
            	layout: 'fit',
            	split: true,
                collapsible: true,
            	id:"importAlarmInstanceTreePanel_Id"
            },{
            	border: true,
            	region: 'center',
                title:'报警项',
                xtype: 'tabpanel',
                id:"importAlarmInstanceItemsConfigTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'top',
                items: [{
                	title:loginUserLanguageResource.numericValue,
                	iconCls: 'check3',
                	id:"importAlarmInstanceNumItemsTableInfoPanel_Id",
                	region: 'center',
            		layout: "border",
            		items: [{
                		region: 'center',
                		title:loginUserLanguageResource.acquisitionItem,
                		layout: 'fit',
                		id:'importAlarmInstanceNumItemsConfigTableInfoPanel_id',
                        html:'<div class="importAlarmInstanceNumItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmInstanceNumItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                        	resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                             	if(importAlarmInstanceConfigNumItemsHandsontableHelper!=null && importAlarmInstanceConfigNumItemsHandsontableHelper.hot!=undefined){
                             		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		importAlarmInstanceConfigNumItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                             	}
                             }
                        }
                	},{
                		region: 'south',
                    	height:'50%',
                    	title:loginUserLanguageResource.calculateItem,
                    	collapsible: true,
                        split: true,
                        layout: 'fit',
                        id:'importAlarmInstanceCalNumItemsConfigTableInfoPanel_id',
                        html:'<div class="importAlarmInstanceCalNumItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmInstanceCalNumItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                             	if(importAlarmInstanceConfigCalNumItemsHandsontableHelper!=null && importAlarmInstanceConfigCalNumItemsHandsontableHelper.hot!=undefined){
                             		var newWidth=width;
                            		var newHeight=height;
                            		var header=thisPanel.getHeader();
                            		if(header){
                            			newHeight=newHeight-header.lastBox.height-2;
                            		}
                            		importAlarmInstanceConfigCalNumItemsHandsontableHelper.hot.updateSettings({
                            			width:newWidth,
                            			height:newHeight
                            		});
                             	}
                             }
                        }
                	}]
                },{
                	title:loginUserLanguageResource.switchingValue,
                	id:"importAlarmInstanceSwitchItemsTableInfoPanel_Id",
                    layout: 'fit',
                    html:'<div class="importAlarmInstanceSwitchItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmInstanceSwitchItemsConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                         	if(importAlarmInstanceConfigSwitchItemsHandsontableHelper!=null && importAlarmInstanceConfigSwitchItemsHandsontableHelper.hot!=undefined){
                         		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		importAlarmInstanceConfigSwitchItemsHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                         	}
                         }
                    }
                },{
                	title:loginUserLanguageResource.enumValue,
                	id:"importAlarmInstanceEnumItemsTableInfoPanel_Id",
                    layout: 'fit',
                    html:'<div class="importAlarmInstanceEnumItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmInstanceEnumItemsConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                         	if(importAlarmInstanceConfigEnumItemsHandsontableHelper!=null && importAlarmInstanceConfigEnumItemsHandsontableHelper.hot!=undefined){
                         		var newWidth=width;
                        		var newHeight=height;
                        		var header=thisPanel.getHeader();
                        		if(header){
                        			newHeight=newHeight-header.lastBox.height-2;
                        		}
                        		importAlarmInstanceConfigEnumItemsHandsontableHelper.hot.updateSettings({
                        			width:newWidth,
                        			height:newHeight
                        		});
                         	}
                         }
                    }
                },{
                	title:loginUserLanguageResource.workType,
                	id:"importAlarmInstanceFESDiagramResultItemsTableInfoPanel_Id",
                	 layout: 'fit',
                     html:'<div class="importAlarmInstanceFESDiagramResultItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmInstanceFESDiagramResultItemsConfigTableInfoDiv_id"></div></div>',
                     listeners: {
                         resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                          	if(importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper!=null && importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot!=undefined){
                          		var newWidth=width;
                         		var newHeight=height;
                         		var header=thisPanel.getHeader();
                         		if(header){
                         			newHeight=newHeight-header.lastBox.height-2;
                         		}
                         		importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.updateSettings({
                         			width:newWidth,
                         			height:newHeight
                         		});
                          	}
                          }
                     }
                },{
                	title:loginUserLanguageResource.runStatus,
                	id:"importAlarmInstanceRunStatusItemsTableInfoPanel_Id",
                	 layout: 'fit',
                     html:'<div class="importAlarmInstanceRunStatusItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmInstanceRunStatusItemsConfigTableInfoDiv_id"></div></div>',
                     listeners: {
                         resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                           	if(importAlarmInstanceConfigRunStatusItemsHandsontableHelper!=null && importAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot!=undefined){
                           		var newWidth=width;
                          		var newHeight=height;
                          		var header=thisPanel.getHeader();
                          		if(header){
                          			newHeight=newHeight-header.lastBox.height-2;
                          		}
                          		importAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot.updateSettings({
                          			width:newWidth,
                          			height:newHeight
                          		});
                           	 }
                          }
                      }
                },{
                	title:loginUserLanguageResource.commStatus,
                	id:"importAlarmInstanceCommStatusItemsTableInfoPanel_Id",
                	 layout: 'fit',
                     html:'<div class="importAlarmInstanceCommStatusItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmInstanceCommStatusItemsConfigTableInfoDiv_id"></div></div>',
                     listeners: {
                         resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	if(importAlarmInstanceConfigCommStatusItemsHandsontableHelper!=null && importAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot!=undefined){
                            		var newWidth=width;
                           		var newHeight=height;
                           		var header=thisPanel.getHeader();
                           		if(header){
                           			newHeight=newHeight-header.lastBox.height-2;
                           		}
                           		importAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot.updateSettings({
                           			width:newWidth,
                           			height:newHeight
                           		});
                            }
                         }
                     }
                }],
                listeners: {
                	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				oldCard.setIconCls(null);
        				newCard.setIconCls('check3');
        			},
        			tabchange: function (tabPanel, newCard, oldCard, obj) {
                    	var selection=Ext.getCmp("ImportAlarmInstanceContentTreeGridPanel_Id").getSelectionModel().getSelection();
                    	if(selection.length>0){
                    		var record = selection[0];
                        	var activeId = newCard.id;
                        	if(activeId=="importAlarmInstanceNumItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmInstanceNumItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].unitName,record.data.children[0].text);
                            			CreateImportAlarmInstanceCalNumItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].unitName,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmInstanceNumItemsConfigInfoTable('','','');
                            			CreateImportAlarmInstanceCalNumItemsConfigInfoTable('','','');
                            		}
                            	}else{
                            		CreateImportAlarmInstanceNumItemsConfigInfoTable(record.data.protocol,record.data.unitName,record.data.text);
                            		CreateImportAlarmInstanceCalNumItemsConfigInfoTable(record.data.protocol,record.data.unitName,record.data.text);
                            	}
                        	}else if(activeId=="importAlarmInstanceSwitchItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmInstanceSwitchItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].unitName,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmInstanceSwitchItemsConfigInfoTable('','','');
                            		}
                            	}else{
                            		CreateImportAlarmInstanceSwitchItemsConfigInfoTable(record.data.protocol,record.data.unitName,record.data.text);
                            	}
                        	}else if(activeId=="importAlarmInstanceEnumItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmInstanceEnumItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].unitName,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmInstanceEnumItemsConfigInfoTable('','','');
                            		}
                            	}else{
                            		CreateImportAlarmInstanceEnumItemsConfigInfoTable(record.data.protocol,record.data.unitName,record.data.text);
                            	}
                        	}else if(activeId=="importAlarmInstanceFESDiagramResultItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmInstanceFESDiagramResultItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].unitName,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmInstanceFESDiagramResultItemsConfigInfoTable('','','');
                            		}
                            	}else{
                            		CreateImportAlarmInstanceFESDiagramResultItemsConfigInfoTable(record.data.protocol,record.data.unitName,record.data.text);
                            	}
                        	}else if(activeId=="importAlarmInstanceRunStatusItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmInstanceRunStatusItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].unitName,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmInstanceRunStatusItemsConfigInfoTable('','','');
                            		}
                            	}else{
                            		CreateImportAlarmInstanceRunStatusItemsConfigInfoTable(record.data.protocol,record.data.unitName,record.data.text);
                            	}
                        	}else if(activeId=="importAlarmInstanceCommStatusItemsTableInfoPanel_Id"){
                        		if(record.data.classes==0){
                            		if(isNotVal(record.data.children) && record.data.children.length>0){
                            			CreateImportAlarmInstanceCommStatusItemsConfigInfoTable(record.data.children[0].protocol,record.data.children[0].unitName,record.data.children[0].text);
                            		}else{
                            			CreateImportAlarmInstanceCommStatusItemsConfigInfoTable('','','');
                            		}
                            	}else{
                            		CreateImportAlarmInstanceCommStatusItemsConfigInfoTable(record.data.protocol,record.data.unitName,record.data.text);
                            	}
                        	}
                    	}
                    }
                }
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	clearImportAlarmInstanceHandsontable();
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function clearImportAlarmInstanceHandsontable(){
	if(importAlarmInstanceConfigNumItemsHandsontableHelper!=null){
		if(importAlarmInstanceConfigNumItemsHandsontableHelper.hot!=undefined){
			importAlarmInstanceConfigNumItemsHandsontableHelper.hot.destroy();
		}
		importAlarmInstanceConfigNumItemsHandsontableHelper=null;
	}
	
	if(importAlarmInstanceConfigCalNumItemsHandsontableHelper!=null){
		if(importAlarmInstanceConfigCalNumItemsHandsontableHelper.hot!=undefined){
			importAlarmInstanceConfigCalNumItemsHandsontableHelper.hot.destroy();
		}
		importAlarmInstanceConfigCalNumItemsHandsontableHelper=null;
	}
	
	if(importAlarmInstanceConfigSwitchItemsHandsontableHelper!=null){
		if(importAlarmInstanceConfigSwitchItemsHandsontableHelper.hot!=undefined){
			importAlarmInstanceConfigSwitchItemsHandsontableHelper.hot.destroy();
		}
		importAlarmInstanceConfigSwitchItemsHandsontableHelper=null;
	}
	
	if(importAlarmInstanceConfigEnumItemsHandsontableHelper!=null){
		if(importAlarmInstanceConfigEnumItemsHandsontableHelper.hot!=undefined){
			importAlarmInstanceConfigEnumItemsHandsontableHelper.hot.destroy();
		}
		importAlarmInstanceConfigEnumItemsHandsontableHelper=null;
	}
	
	if(importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper!=null){
		if(importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot!=undefined){
			importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.destroy();
		}
		importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper=null;
	}
	
	if(importAlarmInstanceConfigRunStatusItemsHandsontableHelper!=null){
		if(importAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot!=undefined){
			importAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot.destroy();
		}
		importAlarmInstanceConfigRunStatusItemsHandsontableHelper=null;
	}
	
	if(importAlarmInstanceConfigCommStatusItemsHandsontableHelper!=null){
		if(importAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot!=undefined){
			importAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot.destroy();
		}
		importAlarmInstanceConfigCommStatusItemsHandsontableHelper=null;
	}
}

function submitImportedAlarmInstanceFile() {
	clearImportAlarmInstanceHandsontable();
	var form = Ext.getCmp("AlarmInstanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedAlarmInstanceFile',
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
            	
            	var importAlarmInstanceContentTreeGridPanel = Ext.getCmp("ImportAlarmInstanceContentTreeGridPanel_Id");
            	if (isNotVal(importAlarmInstanceContentTreeGridPanel)) {
            		importAlarmInstanceContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportAlarmInstanceContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
			}
        });
    }
    return false;
};

adviceImportAlarmInstanceCollisionInfoColor = function(val,o,p,e) {
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

function saveSingelImportedAlarmInstance(instanceName,unitName,protocolName){
	instanceName = decodeURIComponent(instanceName);
	unitName = decodeURIComponent(unitName);
	protocolName = decodeURIComponent(protocolName);
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveSingelImportedAlarmInstance',
		method : "POST",
		params : {
			instanceName : instanceName,
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
			Ext.getCmp("ImportAlarmInstanceContentTreeGridPanel_Id").getStore().load();

    		var treePanel=Ext.getCmp("AlarmInstanceProtocolTreeGridPanel_Id");
    		if(isNotVal(treePanel)){
    			treePanel.getStore().load();
    		}else{
    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmInstanceProtocolTreeInfoStore');
    		}
		},
		failure : function() {
			Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
		}
	});
}

function saveAllImportedAlarmInstance(){
	var unitNameList=[];
	var treeStore = Ext.getCmp("ImportAlarmInstanceContentTreeGridPanel_Id").getStore();
	var count=treeStore.getCount();
	for(var i=0;i<count;i++){
		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign!=2){
			unitNameList.push(treeStore.getAt(i).data.text);
		}
	}
	if(unitNameList.length>0){
		Ext.Ajax.request({
			url : context + '/acquisitionUnitManagerController/saveAllImportedAlarmInstance',
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
				Ext.getCmp("ImportAlarmInstanceContentTreeGridPanel_Id").getStore().load();
				
        		var treePanel=Ext.getCmp("AlarmInstanceProtocolTreeGridPanel_Id");
        		if(isNotVal(treePanel)){
        			treePanel.getStore().load();
        		}else{
        			Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmInstanceProtocolTreeInfoStore');
        		}
			},
			failure : function() {
				Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
			}
		});
	}else{
		Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=blue>"+loginUserLanguageResource.noDataCanBeSaved+"</font>");
	}
}

iconImportSingleAlarmInstanceAction = function(value, e, record) {
	var resultstring='';
	var instanceName=record.data.text;
	var unitName=record.data.unitName;
	var protocolName=record.data.protocol;
	
	instanceName = encodeURIComponent(instanceName || '');
	unitName = encodeURIComponent(unitName || '');
	protocolName = encodeURIComponent(protocolName || '');

	if( record.data.classes==1 && record.data.saveSign!=2 ){
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedAlarmInstance('"+instanceName+"','"+unitName+"','"+protocolName+"')>"+loginUserLanguageResource.save+"...</a>";
	}
	return resultstring;
}

function CreateImportAlarmInstanceNumItemsConfigInfoTable(protocolName,unitName,instanceName){
	Ext.getCmp("importAlarmInstanceNumItemsConfigTableInfoPanel_id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportAlarmInstanceItemsData',
		success:function(response) {
			Ext.getCmp("importAlarmInstanceNumItemsConfigTableInfoPanel_id").getEl().unmask();
			Ext.getCmp("importAlarmInstanceItemsConfigTabPanel_Id").setTitle(instanceName+"/"+loginUserLanguageResource.numericValue);
			
			var result =  Ext.JSON.decode(response.responseText);
			if(importAlarmInstanceConfigNumItemsHandsontableHelper==null || importAlarmInstanceConfigNumItemsHandsontableHelper.hot==undefined){
				importAlarmInstanceConfigNumItemsHandsontableHelper = ImportAlarmInstanceConfigNumItemsHandsontableHelper.createNew("importAlarmInstanceNumItemsConfigTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.address+"','"+loginUserLanguageResource.upperLimit+"','"+loginUserLanguageResource.lowerLimit+"','"+loginUserLanguageResource.hystersis+"','"+loginUserLanguageResource.hystersis+"(s)','"+loginUserLanguageResource.alarmLevel+"','"+loginUserLanguageResource.alarmSign+"','"+loginUserLanguageResource.isSendMessage+"','"+loginUserLanguageResource.isSendEmail+"']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr'},"
						+"{data:'upperLimit'},"
						+"{data:'lowerLimit'}," 
						+"{data:'hystersis'}," 
						+"{data:'delay'}," 
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.normal+"',"+loginUserLanguageResource.alarmLevel1+","+loginUserLanguageResource.alarmLevel2+","+loginUserLanguageResource.alarmLevel3+"]}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"','"+loginUserLanguageResource.disable+"']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:["+loginUserLanguageResource.yes+","+loginUserLanguageResource.no+"]}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:["+loginUserLanguageResource.yes+","+loginUserLanguageResource.no+"]}" 
						+"]";
				importAlarmInstanceConfigNumItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importAlarmInstanceConfigNumItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importAlarmInstanceConfigNumItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmInstanceConfigNumItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importAlarmInstanceConfigNumItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmInstanceConfigNumItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importAlarmInstanceNumItemsConfigTableInfoPanel_id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			unitName:unitName,
			instanceName:instanceName,
			alarmType:2
        }
	});
};

var ImportAlarmInstanceConfigNumItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importAlarmInstanceConfigNumItemsHandsontableHelper = {};
	        importAlarmInstanceConfigNumItemsHandsontableHelper.hot1 = '';
	        importAlarmInstanceConfigNumItemsHandsontableHelper.divid = divid;
	        importAlarmInstanceConfigNumItemsHandsontableHelper.validresult=true;//数据校验
	        importAlarmInstanceConfigNumItemsHandsontableHelper.colHeaders=[];
	        importAlarmInstanceConfigNumItemsHandsontableHelper.columns=[];
	        importAlarmInstanceConfigNumItemsHandsontableHelper.AllData=[];
	        
	        importAlarmInstanceConfigNumItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importAlarmInstanceConfigNumItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importAlarmInstanceConfigNumItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAlarmInstanceConfigNumItemsHandsontableHelper.divid);
	        	importAlarmInstanceConfigNumItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,120,80,80,80,80,80,80,80,80,80],
	                columns:importAlarmInstanceConfigNumItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importAlarmInstanceConfigNumItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(importAlarmInstanceConfigNumItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importAlarmInstanceConfigNumItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importAlarmInstanceConfigNumItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 
	                		&& importAlarmInstanceConfigNumItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importAlarmInstanceConfigNumItemsHandsontableHelper!=null
	                		&& importAlarmInstanceConfigNumItemsHandsontableHelper.hot!=''
	                		&& importAlarmInstanceConfigNumItemsHandsontableHelper.hot!=undefined 
	                		&& importAlarmInstanceConfigNumItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importAlarmInstanceConfigNumItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importAlarmInstanceConfigNumItemsHandsontableHelper;
	    }
};

function CreateImportAlarmInstanceCalNumItemsConfigInfoTable(protocolName,unitName,instanceName){
	Ext.getCmp("importAlarmInstanceCalNumItemsConfigTableInfoPanel_id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportAlarmInstanceItemsData',
		success:function(response) {
			Ext.getCmp("importAlarmInstanceCalNumItemsConfigTableInfoPanel_id").getEl().unmask();
			Ext.getCmp("importAlarmInstanceItemsConfigTabPanel_Id").setTitle(instanceName+"/"+loginUserLanguageResource.numericValue);
			
			var result =  Ext.JSON.decode(response.responseText);
			if(importAlarmInstanceConfigCalNumItemsHandsontableHelper==null || importAlarmUnitConfigCalNumItemsHandsontableHelper.hot==undefined){
				importAlarmUnitConfigCalNumItemsHandsontableHelper = ImportAlarmInstanceConfigCalNumItemsHandsontableHelper.createNew("importAlarmInstanceCalNumItemsConfigTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.unit+"','"+loginUserLanguageResource.upperLimit+"','"+loginUserLanguageResource.lowerLimit+"','"+loginUserLanguageResource.hystersis+"','"+loginUserLanguageResource.hystersis+"(s)','"+loginUserLanguageResource.alarmLevel+"','"+loginUserLanguageResource.alarmSign+"','"+loginUserLanguageResource.isSendMessage+"','"+loginUserLanguageResource.isSendEmail+"']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'unit'},"
						+"{data:'upperLimit'},"
						+"{data:'lowerLimit'}," 
						+"{data:'hystersis'}," 
						+"{data:'delay'}," 
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.normal+"',"+loginUserLanguageResource.alarmLevel1+","+loginUserLanguageResource.alarmLevel2+","+loginUserLanguageResource.alarmLevel3+"]}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"','"+loginUserLanguageResource.disable+"']},"
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:["+loginUserLanguageResource.yes+","+loginUserLanguageResource.no+"]}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:["+loginUserLanguageResource.yes+","+loginUserLanguageResource.no+"]}" 
						+"]";
				importAlarmUnitConfigCalNumItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importAlarmUnitConfigCalNumItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importAlarmUnitConfigCalNumItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmUnitConfigCalNumItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importAlarmUnitConfigCalNumItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmUnitConfigCalNumItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importAlarmInstanceCalNumItemsConfigTableInfoPanel_id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			unitName:unitName,
			instanceName:instanceName,
			alarmType:5
        }
	});
};

var ImportAlarmInstanceConfigCalNumItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importAlarmUnitConfigCalNumItemsHandsontableHelper = {};
	        importAlarmUnitConfigCalNumItemsHandsontableHelper.hot1 = '';
	        importAlarmUnitConfigCalNumItemsHandsontableHelper.divid = divid;
	        importAlarmUnitConfigCalNumItemsHandsontableHelper.validresult=true;//数据校验
	        importAlarmUnitConfigCalNumItemsHandsontableHelper.colHeaders=[];
	        importAlarmUnitConfigCalNumItemsHandsontableHelper.columns=[];
	        importAlarmUnitConfigCalNumItemsHandsontableHelper.AllData=[];
	        
	        importAlarmUnitConfigCalNumItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importAlarmUnitConfigCalNumItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importAlarmUnitConfigCalNumItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAlarmUnitConfigCalNumItemsHandsontableHelper.divid);
	        	importAlarmUnitConfigCalNumItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,120,80,80,80,80,80,80,80,80,80],
	                columns:importAlarmUnitConfigCalNumItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importAlarmUnitConfigCalNumItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(importAlarmUnitConfigCalNumItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importAlarmUnitConfigCalNumItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importAlarmUnitConfigCalNumItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 
	                		&& importAlarmUnitConfigCalNumItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importAlarmUnitConfigCalNumItemsHandsontableHelper!=null
	                		&& importAlarmUnitConfigCalNumItemsHandsontableHelper.hot!=''
	                		&& importAlarmUnitConfigCalNumItemsHandsontableHelper.hot!=undefined 
	                		&& importAlarmUnitConfigCalNumItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importAlarmUnitConfigCalNumItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importAlarmUnitConfigCalNumItemsHandsontableHelper;
	    }
};

function CreateImportAlarmInstanceSwitchItemsConfigInfoTable(protocolName,unitName,instanceName){
	Ext.getCmp("importAlarmInstanceSwitchItemsTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportAlarmInstanceItemsData',
		success:function(response) {
			Ext.getCmp("importAlarmInstanceSwitchItemsTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("importAlarmInstanceItemsConfigTabPanel_Id").setTitle(instanceName+"/"+loginUserLanguageResource.switchingValue);
			var result =  Ext.JSON.decode(response.responseText);
			if(importAlarmInstanceConfigSwitchItemsHandsontableHelper==null || importAlarmInstanceConfigSwitchItemsHandsontableHelper.hot==undefined){
				importAlarmInstanceConfigSwitchItemsHandsontableHelper = ImportAlarmInstanceConfigSwitchItemsHandsontableHelper.createNew("importAlarmInstanceSwitchItemsConfigTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.address+"','"+loginUserLanguageResource.bit+"','"+loginUserLanguageResource.meaning+"','"+loginUserLanguageResource.switchItemAlarmValue+"','"+loginUserLanguageResource.hystersis+"(s)','"+loginUserLanguageResource.alarmLevel+"','"+loginUserLanguageResource.alarmSign+"','"+loginUserLanguageResource.isSendMessage+"','"+loginUserLanguageResource.isSendEmail+"']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr'},"
						+"{data:'bitIndex'},"
						+"{data:'meaning'}," 
						+"{data:'value'}," 
						+"{data:'delay'}," 
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.normal+"',"+loginUserLanguageResource.alarmLevel1+","+loginUserLanguageResource.alarmLevel2+","+loginUserLanguageResource.alarmLevel3+"]}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"','"+loginUserLanguageResource.disable+"']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:["+loginUserLanguageResource.yes+","+loginUserLanguageResource.no+"]}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:["+loginUserLanguageResource.yes+","+loginUserLanguageResource.no+"]}" 
						+"]";
				importAlarmInstanceConfigSwitchItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importAlarmInstanceConfigSwitchItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importAlarmInstanceConfigSwitchItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmInstanceConfigSwitchItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importAlarmInstanceConfigSwitchItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmInstanceConfigSwitchItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importAlarmInstanceSwitchItemsTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			unitName:unitName,
			instanceName:instanceName,
			alarmType:0
        }
	});
};

var ImportAlarmInstanceConfigSwitchItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importAlarmInstanceConfigSwitchItemsHandsontableHelper = {};
	        importAlarmInstanceConfigSwitchItemsHandsontableHelper.hot1 = '';
	        importAlarmInstanceConfigSwitchItemsHandsontableHelper.divid = divid;
	        importAlarmInstanceConfigSwitchItemsHandsontableHelper.validresult=true;//数据校验
	        importAlarmInstanceConfigSwitchItemsHandsontableHelper.colHeaders=[];
	        importAlarmInstanceConfigSwitchItemsHandsontableHelper.columns=[];
	        importAlarmInstanceConfigSwitchItemsHandsontableHelper.AllData=[];
	        
	        importAlarmInstanceConfigSwitchItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importAlarmInstanceConfigSwitchItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importAlarmInstanceConfigSwitchItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAlarmInstanceConfigSwitchItemsHandsontableHelper.divid);
	        	importAlarmInstanceConfigSwitchItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,120,80,80,80,80,80,80,80,80,80],
	                columns:importAlarmInstanceConfigSwitchItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importAlarmInstanceConfigSwitchItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(importAlarmInstanceConfigSwitchItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importAlarmInstanceConfigSwitchItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importAlarmInstanceConfigSwitchItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 
	                		&& importAlarmInstanceConfigSwitchItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importAlarmInstanceConfigSwitchItemsHandsontableHelper!=null
	                		&& importAlarmInstanceConfigSwitchItemsHandsontableHelper.hot!=''
	                		&& importAlarmInstanceConfigSwitchItemsHandsontableHelper.hot!=undefined 
	                		&& importAlarmInstanceConfigSwitchItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importAlarmInstanceConfigSwitchItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importAlarmInstanceConfigSwitchItemsHandsontableHelper;
	    }
};

function CreateImportAlarmInstanceEnumItemsConfigInfoTable(protocolName,unitName,instanceName){
	Ext.getCmp("importAlarmInstanceEnumItemsTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportAlarmInstanceItemsData',
		success:function(response) {
			Ext.getCmp("importAlarmInstanceEnumItemsTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("importAlarmInstanceItemsConfigTabPanel_Id").setTitle(instanceName+"/"+loginUserLanguageResource.enumValue);
			var result =  Ext.JSON.decode(response.responseText);
			if(importAlarmInstanceConfigEnumItemsHandsontableHelper==null || importAlarmInstanceConfigEnumItemsHandsontableHelper.hot==undefined){
				importAlarmInstanceConfigEnumItemsHandsontableHelper = ImportAlarmInstanceConfigEnumItemsHandsontableHelper.createNew("importAlarmInstanceEnumItemsConfigTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.address+"','"+loginUserLanguageResource.value+"','"+loginUserLanguageResource.meaning+"','"+loginUserLanguageResource.hystersis+"(s)','"+loginUserLanguageResource.alarmLevel+"','"+loginUserLanguageResource.alarmSign+"','"+loginUserLanguageResource.isSendMessage+"','"+loginUserLanguageResource.isSendEmail+"']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'addr'},"
					 	+"{data:'value'}," 
						+"{data:'meaning'},"
						+"{data:'delay'}," 
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.normal+"',"+loginUserLanguageResource.alarmLevel1+","+loginUserLanguageResource.alarmLevel2+","+loginUserLanguageResource.alarmLevel3+"]}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"','"+loginUserLanguageResource.disable+"']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:["+loginUserLanguageResource.yes+","+loginUserLanguageResource.no+"]}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:["+loginUserLanguageResource.yes+","+loginUserLanguageResource.no+"]}" 
						+"]";
				importAlarmInstanceConfigEnumItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importAlarmInstanceConfigEnumItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importAlarmInstanceConfigEnumItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmInstanceConfigEnumItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importAlarmInstanceConfigEnumItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmInstanceConfigEnumItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importAlarmInstanceEnumItemsTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			unitName:unitName,
			instanceName:instanceName,
			alarmType:1
        }
	});
};

var ImportAlarmInstanceConfigEnumItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importAlarmInstanceConfigEnumItemsHandsontableHelper = {};
	        importAlarmInstanceConfigEnumItemsHandsontableHelper.hot1 = '';
	        importAlarmInstanceConfigEnumItemsHandsontableHelper.divid = divid;
	        importAlarmInstanceConfigEnumItemsHandsontableHelper.validresult=true;//数据校验
	        importAlarmInstanceConfigEnumItemsHandsontableHelper.colHeaders=[];
	        importAlarmInstanceConfigEnumItemsHandsontableHelper.columns=[];
	        importAlarmInstanceConfigEnumItemsHandsontableHelper.AllData=[];
	        
	        importAlarmInstanceConfigEnumItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importAlarmInstanceConfigEnumItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importAlarmInstanceConfigEnumItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAlarmInstanceConfigEnumItemsHandsontableHelper.divid);
	        	importAlarmInstanceConfigEnumItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,120,80,80,80,80,80,80,80,80],
	                columns:importAlarmInstanceConfigEnumItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importAlarmInstanceConfigEnumItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(importAlarmInstanceConfigEnumItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importAlarmInstanceConfigEnumItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importAlarmInstanceConfigEnumItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 
	                		&& importAlarmInstanceConfigEnumItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importAlarmInstanceConfigEnumItemsHandsontableHelper!=null
	                		&& importAlarmInstanceConfigEnumItemsHandsontableHelper.hot!=''
	                		&& importAlarmInstanceConfigEnumItemsHandsontableHelper.hot!=undefined 
	                		&& importAlarmInstanceConfigEnumItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importAlarmInstanceConfigEnumItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importAlarmInstanceConfigEnumItemsHandsontableHelper;
	    }
};

function CreateImportAlarmInstanceFESDiagramResultItemsConfigInfoTable(protocolName,unitName,instanceName){
	Ext.getCmp("importAlarmInstanceFESDiagramResultItemsTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportAlarmInstanceItemsData',
		success:function(response) {
			Ext.getCmp("importAlarmInstanceFESDiagramResultItemsTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("importAlarmInstanceItemsConfigTabPanel_Id").setTitle(instanceName+"/"+loginUserLanguageResource.workType);
			var result =  Ext.JSON.decode(response.responseText);
			if(importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper==null || importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot==undefined){
				importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper = ImportAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.createNew("importAlarmInstanceFESDiagramResultItemsConfigTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.hystersis+"(s)','"+loginUserLanguageResource.alarmLevel+"','"+loginUserLanguageResource.alarmSign+"','"+loginUserLanguageResource.isSendMessage+"','"+loginUserLanguageResource.isSendEmail+"']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper);}},"
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.normal+"',"+loginUserLanguageResource.alarmLevel1+","+loginUserLanguageResource.alarmLevel2+","+loginUserLanguageResource.alarmLevel3+"]}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"','"+loginUserLanguageResource.disable+"']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:["+loginUserLanguageResource.yes+","+loginUserLanguageResource.no+"]}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:["+loginUserLanguageResource.yes+","+loginUserLanguageResource.no+"]}" 
						+"]";
				importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importAlarmInstanceFESDiagramResultItemsTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			unitName:unitName,
			instanceName:instanceName,
			alarmType:4
        }
	});
};

var ImportAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper = {};
	        importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot1 = '';
	        importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.divid = divid;
	        importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.validresult=true;//数据校验
	        importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.colHeaders=[];
	        importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.columns=[];
	        importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.AllData=[];
	        
	        importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.divid);
	        	importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,80,80,80,80,80,80],
	                columns:importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 
	                		&& importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper!=null
	                		&& importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot!=''
	                		&& importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot!=undefined 
	                		&& importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importAlarmInstanceConfigFESDiagramResultItemsHandsontableHelper;
	    }
};

function CreateImportAlarmInstanceRunStatusItemsConfigInfoTable(protocolName,unitName,instanceName){
	Ext.getCmp("importAlarmInstanceRunStatusItemsTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportAlarmInstanceItemsData',
		success:function(response) {
			Ext.getCmp("importAlarmInstanceRunStatusItemsTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("importAlarmInstanceItemsConfigTabPanel_Id").setTitle(instanceName+"/"+loginUserLanguageResource.runStatus);
			var result =  Ext.JSON.decode(response.responseText);
			if(importAlarmInstanceConfigRunStatusItemsHandsontableHelper==null || importAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot==undefined){
				importAlarmInstanceConfigRunStatusItemsHandsontableHelper = ImportAlarmInstanceConfigRunStatusItemsHandsontableHelper.createNew("importAlarmInstanceRunStatusItemsConfigTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.hystersis+"(s)','"+loginUserLanguageResource.alarmLevel+"','"+loginUserLanguageResource.alarmSign+"','"+loginUserLanguageResource.isSendMessage+"','"+loginUserLanguageResource.isSendEmail+"']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper);}},"
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.normal+"',"+loginUserLanguageResource.alarmLevel1+","+loginUserLanguageResource.alarmLevel2+","+loginUserLanguageResource.alarmLevel3+"]}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"','"+loginUserLanguageResource.disable+"']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:["+loginUserLanguageResource.yes+","+loginUserLanguageResource.no+"]}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:["+loginUserLanguageResource.yes+","+loginUserLanguageResource.no+"]}" 
						+"]";
				importAlarmInstanceConfigRunStatusItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importAlarmInstanceConfigRunStatusItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importAlarmInstanceConfigRunStatusItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmInstanceConfigRunStatusItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importAlarmInstanceRunStatusItemsTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			unitName:unitName,
			instanceName:instanceName,
			alarmType:6
        }
	});
};

var ImportAlarmInstanceConfigRunStatusItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importAlarmInstanceConfigRunStatusItemsHandsontableHelper = {};
	        importAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot1 = '';
	        importAlarmInstanceConfigRunStatusItemsHandsontableHelper.divid = divid;
	        importAlarmInstanceConfigRunStatusItemsHandsontableHelper.validresult=true;//数据校验
	        importAlarmInstanceConfigRunStatusItemsHandsontableHelper.colHeaders=[];
	        importAlarmInstanceConfigRunStatusItemsHandsontableHelper.columns=[];
	        importAlarmInstanceConfigRunStatusItemsHandsontableHelper.AllData=[];
	        
	        importAlarmInstanceConfigRunStatusItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importAlarmInstanceConfigRunStatusItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importAlarmInstanceConfigRunStatusItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAlarmInstanceConfigRunStatusItemsHandsontableHelper.divid);
	        	importAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,80,80,80,80,80,80],
	                columns:importAlarmInstanceConfigRunStatusItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importAlarmInstanceConfigRunStatusItemsHandsontableHelper.colHeaders,//显示列头
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
	                    
	                    if(importAlarmInstanceConfigRunStatusItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importAlarmInstanceConfigRunStatusItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importAlarmInstanceConfigRunStatusItemsHandsontableHelper.addCellStyle;
	    	            }
	                    
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 
	                		&& importAlarmInstanceConfigRunStatusItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importAlarmInstanceConfigRunStatusItemsHandsontableHelper!=null
	                		&& importAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot!=''
	                		&& importAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot!=undefined 
	                		&& importAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importAlarmInstanceConfigRunStatusItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importAlarmInstanceConfigRunStatusItemsHandsontableHelper;
	    }
};

function CreateImportAlarmInstanceCommStatusItemsConfigInfoTable(protocolName,unitName,instanceName){
	Ext.getCmp("importAlarmInstanceCommStatusItemsTableInfoPanel_Id").el.mask(loginUserLanguageResource.updateWait+'...').show();
	Ext.Ajax.request({
		method:'POST',
		url:context + '/acquisitionUnitManagerController/getImportAlarmInstanceItemsData',
		success:function(response) {
			Ext.getCmp("importAlarmInstanceCommStatusItemsTableInfoPanel_Id").getEl().unmask();
			Ext.getCmp("importAlarmInstanceItemsConfigTabPanel_Id").setTitle(instanceName+"/"+loginUserLanguageResource.commStatus);
			var result =  Ext.JSON.decode(response.responseText);
			if(importAlarmInstanceConfigCommStatusItemsHandsontableHelper==null || importAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot==undefined){
				importAlarmInstanceConfigCommStatusItemsHandsontableHelper = ImportAlarmInstanceConfigCommStatusItemsHandsontableHelper.createNew("importAlarmInstanceCommStatusItemsConfigTableInfoDiv_id");
				var colHeaders="['"+loginUserLanguageResource.idx+"','"+loginUserLanguageResource.name+"','"+loginUserLanguageResource.hystersis+"(s)','"+loginUserLanguageResource.alarmLevel+"','"+loginUserLanguageResource.alarmSign+"','"+loginUserLanguageResource.isSendMessage+"','"+loginUserLanguageResource.isSendEmail+"']";
				var columns="[{data:'id'},{data:'title'},"
					 	+"{data:'delay',type:'text',allowInvalid: true, validator: function(val, callback){return handsontableDataCheck_Num(val, callback,this.row, this.col,protocolAlarmUnitConfigFESDiagramConditionsItemsHandsontableHelper);}},"
						+"{data:'alarmLevel',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.normal+"',"+loginUserLanguageResource.alarmLevel1+","+loginUserLanguageResource.alarmLevel2+","+loginUserLanguageResource.alarmLevel3+"]}," 
						+"{data:'alarmSign',type:'dropdown',strict:true,allowInvalid:false,source:['"+loginUserLanguageResource.enable+"','"+loginUserLanguageResource.disable+"']}," 
						+"{data:'isSendMessage',type:'dropdown',strict:true,allowInvalid:false,source:["+loginUserLanguageResource.yes+","+loginUserLanguageResource.no+"]}," 
						+"{data:'isSendMail',type:'dropdown',strict:true,allowInvalid:false,source:["+loginUserLanguageResource.yes+","+loginUserLanguageResource.no+"]}" 
						+"]";
				importAlarmInstanceConfigCommStatusItemsHandsontableHelper.colHeaders=Ext.JSON.decode(colHeaders);
				importAlarmInstanceConfigCommStatusItemsHandsontableHelper.columns=Ext.JSON.decode(columns);
				if(result.totalRoot.length==0){
					importAlarmInstanceConfigCommStatusItemsHandsontableHelper.createTable([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmInstanceConfigCommStatusItemsHandsontableHelper.createTable(result.totalRoot);
				}
			}else{
				if(result.totalRoot.length==0){
					importAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot.loadData([{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}]);
				}else{
					importAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot.loadData(result.totalRoot);
				}
			}
		},
		failure:function(){
			Ext.getCmp("importAlarmInstanceCommStatusItemsTableInfoPanel_Id").getEl().unmask();
			Ext.MessageBox.alert(loginUserLanguageResource.error,loginUserLanguageResource.errorInfo);
		},
		params: {
			protocolName:protocolName,
			unitName:unitName,
			instanceName:instanceName,
			alarmType:3
        }
	});
};

var ImportAlarmInstanceConfigCommStatusItemsHandsontableHelper = {
		createNew: function (divid) {
	        var importAlarmInstanceConfigCommStatusItemsHandsontableHelper = {};
	        importAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot1 = '';
	        importAlarmInstanceConfigCommStatusItemsHandsontableHelper.divid = divid;
	        importAlarmInstanceConfigCommStatusItemsHandsontableHelper.validresult=true;//数据校验
	        importAlarmInstanceConfigCommStatusItemsHandsontableHelper.colHeaders=[];
	        importAlarmInstanceConfigCommStatusItemsHandsontableHelper.columns=[];
	        importAlarmInstanceConfigCommStatusItemsHandsontableHelper.AllData=[];
	        
	        importAlarmInstanceConfigCommStatusItemsHandsontableHelper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
	            Handsontable.renderers.TextRenderer.apply(this, arguments);
	            td.style.whiteSpace='nowrap'; //文本不换行
            	td.style.overflow='hidden';//超出部分隐藏
            	td.style.textOverflow='ellipsis';//使用省略号表示溢出的文本
	        }
	        
	        importAlarmInstanceConfigCommStatusItemsHandsontableHelper.createTable = function (data) {
	        	$('#'+importAlarmInstanceConfigCommStatusItemsHandsontableHelper.divid).empty();
	        	var hotElement = document.querySelector('#'+importAlarmInstanceConfigCommStatusItemsHandsontableHelper.divid);
	        	importAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot = new Handsontable(hotElement, {
	        		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
	        		data: data,
	        		colWidths: [50,80,80,80,80,80,80],
	                columns:importAlarmInstanceConfigCommStatusItemsHandsontableHelper.columns,
	                stretchH: 'all',//延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
	                autoWrapRow: true,
	                rowHeaders: false,//显示行头
	                colHeaders:importAlarmInstanceConfigCommStatusItemsHandsontableHelper.colHeaders,//显示列头
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
	                    if(importAlarmInstanceConfigCommStatusItemsHandsontableHelper.columns[visualColIndex].type!='dropdown' 
	    	            	&& importAlarmInstanceConfigCommStatusItemsHandsontableHelper.columns[visualColIndex].type!='checkbox'){
	                    	cellProperties.renderer = importAlarmInstanceConfigCommStatusItemsHandsontableHelper.addCellStyle;
	    	            }
	                    return cellProperties;
	                },
	                afterOnCellMouseOver: function(event, coords, TD){
	                	if(coords.col>=0 && coords.row>=0 
	                		&& importAlarmInstanceConfigCommStatusItemsHandsontableHelper.columns[coords.col].type!='checkbox' 
	                		&& importAlarmInstanceConfigCommStatusItemsHandsontableHelper!=null
	                		&& importAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot!=''
	                		&& importAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot!=undefined 
	                		&& importAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot.getDataAtCell!=undefined){
	                		var rawValue=importAlarmInstanceConfigCommStatusItemsHandsontableHelper.hot.getDataAtCell(coords.row,coords.col);
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
	        return importAlarmInstanceConfigCommStatusItemsHandsontableHelper;
	    }
};