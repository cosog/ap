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
    width: 500,
    minWidth: 500,
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
                text: loginUserLanguageResource.saveAll,
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

                        Ext.Msg.confirm(loginUserLanguageResource.tip, info, function (btn) {
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
                region: 'center',
                title: loginUserLanguageResource.instanceList,
                layout: 'fit',
                split: true,
                collapsible: true,
                id: "importDisplayInstanceTreePanel_Id"
            }],
            listeners: {
                beforeclose: function (panel, eOpts) {
                	
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function submitImportedDisplayInstanceFile() {
	var form = Ext.getCmp("DisplayInstanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedDisplayInstanceFile',
            timeout: 1000*60*10,
            method:'post',
            waitMsg: loginUserLanguageResource.uploadingFile+'...',
            success: function(response, action) {
            	var result = action.result;
            	if (result.flag == true) {
            		Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.loadSuccessfully);
            	}else{
            		Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.uploadDataError);
            	}
            	
            	
            	var importDisplayInstanceContentTreeGridPanel = Ext.getCmp("ImportDisplayInstanceContentTreeGridPanel_Id");
            	if (isNotVal(importDisplayInstanceContentTreeGridPanel)) {
            		importDisplayInstanceContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportDisplayInstanceContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.uploadFail+"</font>】");
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
	if( record.data.classes==1 && record.data.saveSign!=2 ){
		var instanceName=record.data.text;
		var displayUnitName=record.data.displayUnitName;
		var acqUnitName=record.data.acqUnitName;
		var protocolName=record.data.protocol;
		var protocolDeviceType=record.data.protocolDeviceType;
		var saveSign=record.data.saveSign;
		var msg=record.data.msg;

		instanceName = encodeURIComponent(instanceName || '');
		displayUnitName = encodeURIComponent(displayUnitName || '');
		acqUnitName = encodeURIComponent(acqUnitName || '');
		protocolName = encodeURIComponent(protocolName || '');
		saveSign = encodeURIComponent(saveSign || '');
		msg = encodeURIComponent(msg || '');
		
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedDisplayInstance('"+instanceName+"','"+displayUnitName+"','"+acqUnitName+"','"+protocolName+"','"+protocolDeviceType+"','"+saveSign+"','"+msg+"')>"+loginUserLanguageResource.save+"...</a>";
	}
	return resultstring;
}

function saveSingelImportedDisplayInstance(instanceName,displayUnitName,acqUnitName,protocolName,protocolDeviceType,saveSign,msg){
	instanceName = decodeURIComponent(instanceName);
	displayUnitName = decodeURIComponent(displayUnitName);
	acqUnitName = decodeURIComponent(acqUnitName);
	protocolName = decodeURIComponent(protocolName);
	protocolDeviceType= decodeURIComponent(protocolDeviceType);
	saveSign = decodeURIComponent(saveSign);
	msg = decodeURIComponent(msg);
	if(parseInt(saveSign)>0){
		Ext.Msg.confirm(loginUserLanguageResource.tip, msg,function (btn) {
			if (btn == "yes") {
				Ext.Ajax.request({
					url : context + '/acquisitionUnitManagerController/saveSingelImportedDisplayInstance',
					method : "POST",
					params : {
						instanceName : instanceName,
						displayUnitName : displayUnitName,
						acqUnitName:acqUnitName,
						protocolName : protocolName,
						protocolDeviceType:protocolDeviceType
					},
					success : function(response) {
						var result = Ext.JSON.decode(response.responseText);
						if (result.success==true) {
							Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
						}else{
							Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
						}
						Ext.getCmp("ImportDisplayInstanceContentTreeGridPanel_Id").getStore().load();

			    		var treePanel=Ext.getCmp("DisplayInstanceProtocolTreeGridPanel_Id");
			    		if(isNotVal(treePanel)){
			    			treePanel.getStore().load();
			    		}else{
			    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceProtocolTreeInfoStore');
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
			url : context + '/acquisitionUnitManagerController/saveSingelImportedDisplayInstance',
			method : "POST",
			params : {
				instanceName : instanceName,
				displayUnitName : displayUnitName,
				acqUnitName:acqUnitName,
				protocolName : protocolName,
				protocolDeviceType:protocolDeviceType
			},
			success : function(response) {
				var result = Ext.JSON.decode(response.responseText);
				if (result.success==true) {
					Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
				}else{
					Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
				}
				Ext.getCmp("ImportDisplayInstanceContentTreeGridPanel_Id").getStore().load();

	    		var treePanel=Ext.getCmp("DisplayInstanceProtocolTreeGridPanel_Id");
	    		if(isNotVal(treePanel)){
	    			treePanel.getStore().load();
	    		}else{
	    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceProtocolTreeInfoStore');
	    		}
			},
			failure : function() {
				Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
			}
		});
	}
	
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
				Ext.Msg.alert(loginUserLanguageResource.tip, loginUserLanguageResource.saveSuccessfully);
			}else{
				Ext.Msg.alert(loginUserLanguageResource.tip, "<font color=red>"+loginUserLanguageResource.saveFailure+"</font>");
			}
			Ext.getCmp("ImportDisplayInstanceContentTreeGridPanel_Id").getStore().load();

    		var treePanel=Ext.getCmp("DisplayInstanceProtocolTreeGridPanel_Id");
    		if(isNotVal(treePanel)){
    			treePanel.getStore().load();
    		}else{
    			Ext.create('AP.store.acquisitionUnit.ModbusProtocolDisplayInstanceProtocolTreeInfoStore');
    		}
		},
		failure : function() {
			Ext.Msg.alert(loginUserLanguageResource.tip, "【<font color=red>"+loginUserLanguageResource.exceptionThrow+"</font>】"+loginUserLanguageResource.contactAdmin);
		}
	});
}