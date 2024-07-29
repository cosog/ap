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
                    fieldLabel: '上传文件',
                    labelWidth: 60,
                    width:'100%',
                    msgTarget: 'side',
                    allowBlank: true,
                    anchor: '100%',
                    draggable:true,
                    buttonText: '请选择上传文件',
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
                text: '全部保存',
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
                		
                		Ext.Msg.confirm('提示', info, function (btn) {
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
            	title:'上传实例列表',
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
                	title:'数据量',
                	iconCls: 'check3',
                	id:"importAlarmInstanceNumItemsTableInfoPanel_Id",
                	region: 'center',
            		layout: "border",
            		items: [{
                		region: 'center',
                		title:'采集项',
                		layout: 'fit',
                		id:'importAlarmInstanceNumItemsConfigTableInfoPanel_id',
                        html:'<div class="importAlarmInstanceNumItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmInstanceNumItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                        	
                        }
                	},{
                		region: 'south',
                    	height:'50%',
                    	title:'计算项',
                    	collapsible: true,
                        split: true,
                        layout: 'fit',
                        id:'importAlarmInstanceCalNumItemsConfigTableInfoPanel_id',
                        html:'<div class="importAlarmInstanceCalNumItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmInstanceCalNumItemsConfigTableInfoDiv_id"></div></div>',
                        listeners: {
                            resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                            	
                            }
                        }
                	}]
                },{
                	title:'开关量',
                	id:"importAlarmInstanceSwitchItemsTableInfoPanel_Id",
                    layout: 'fit',
                    html:'<div class="importAlarmInstanceSwitchItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmInstanceSwitchItemsConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                        resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	
                        }
                    }
                },{
                	title:'枚举量',
                	id:"importAlarmInstanceEnumItemsTableInfoPanel_Id",
                    layout: 'fit',
                    html:'<div class="importAlarmInstanceEnumItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmInstanceEnumItemsConfigTableInfoDiv_id"></div></div>',
                    listeners: {
                    	
                    }
                },{
                	title:'工况诊断',
                	id:"importAlarmInstanceFESDiagramResultItemsTableInfoPanel_Id",
                	 layout: 'fit',
                     html:'<div class="importAlarmInstanceFESDiagramResultItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmInstanceFESDiagramResultItemsConfigTableInfoDiv_id"></div></div>',
                     listeners: {
                         resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	 
                         }
                     }
                },{
                	title:'运行状态',
                	id:"importAlarmInstanceRunStatusItemsTableInfoPanel_Id",
                	 layout: 'fit',
                     html:'<div class="importAlarmInstanceRunStatusItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmInstanceRunStatusItemsConfigTableInfoDiv_id"></div></div>',
                     listeners: {
                    	 
                     }
                },{
                	title:'通信状态',
                	id:"importAlarmInstanceCommStatusItemsTableInfoPanel_Id",
                	 layout: 'fit',
                     html:'<div class="importAlarmInstanceCommStatusItemsTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importAlarmInstanceCommStatusItemsConfigTableInfoDiv_id"></div></div>',
                     listeners: {
                         resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                        	 
                         }
                     }
                }],
                listeners: {
                	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				oldCard.setIconCls(null);
        				newCard.setIconCls('check3');
        			},
        			tabchange: function (tabPanel, newCard, oldCard, obj) {
        				
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
            		Ext.Msg.alert("提示", "加载成功 ");
            	}else{
            		Ext.Msg.alert("提示", "上传数据格式有误！ ");
            	}
            	
            	var importAlarmInstanceContentTreeGridPanel = Ext.getCmp("ImportAlarmInstanceContentTreeGridPanel_Id");
            	if (isNotVal(importAlarmInstanceContentTreeGridPanel)) {
            		importAlarmInstanceContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportAlarmInstanceContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert("提示", "【<font color=red>上传失败 </font>】");
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
				Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
			}else{
				Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
			}
			Ext.getCmp("ImportAlarmInstanceContentTreeGridPanel_Id").getStore().load();

			var treeGridPanel = Ext.getCmp("ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id");
            if (isNotVal(treeGridPanel)) {
            	treeGridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmInstanceTreeInfoStore');
            }
		},
		failure : function() {
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
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
					Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
				}else{
					Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
				}
				Ext.getCmp("ImportAlarmInstanceContentTreeGridPanel_Id").getStore().load();
				
				var treeGridPanel = Ext.getCmp("ModbusProtocolAlarmInstanceConfigTreeGridPanel_Id");
                if (isNotVal(treeGridPanel)) {
                	treeGridPanel.getStore().load();
                }else{
                	Ext.create('AP.store.acquisitionUnit.ModbusProtocolAlarmInstanceTreeInfoStore');
                }
			},
			failure : function() {
				Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
			}
		});
	}else{
		Ext.Msg.alert('提示', "<font color=blue>没有可保存的实例。</font>");
	}
}

iconImportSingleAlarmInstanceAction = function(value, e, record) {
	var resultstring='';
	var instanceName=record.data.text;
	var unitName=record.data.unitName;
	var protocolName=record.data.protocol;

	if( record.data.classes==1 && record.data.saveSign!=2 ){
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedAlarmInstance(\""+instanceName+"\",\""+unitName+"\,\""+protocolName+"\")>保存...</a>";
	}
	return resultstring;
}