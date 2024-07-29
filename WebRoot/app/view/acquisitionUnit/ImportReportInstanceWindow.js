Ext.define("AP.view.acquisitionUnit.ImportReportInstanceWindow", {
    extend: 'Ext.window.Window',
    id:'ImportReportInstanceWindow_Id',
    alias: 'widget.ImportReportInstanceWindow',
    layout: 'fit',
    title:'报表实例导入',
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
        		id:'ReportInstanceImportForm_Id',
        		width: 300,
        	    bodyPadding: 0,
        	    frame: true,
        	    items: [{
        	    	xtype: 'filefield',
                	id:'ReportInstanceImportFilefield_Id',
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
                        	submitImportedReportInstanceFile();
                        }
                    }
        	    },{
                    id: 'ImportReportInstanceSelectItemType_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                },{
                    id: 'ImportReportInstanceSelectItemId_Id', 
                    xtype: 'textfield',
                    value: '-99',
                    hidden: true
                }]
    		},{
            	xtype: 'label',
            	id: 'ImportReportInstanceWinTabLabel_Id',
            	hidden:true,
            	html: ''
            },{
				xtype : "hidden",
				id : 'ImportReportInstanceWinDeviceType_Id',
				value:'0'
			},'->',{
    	    	xtype: 'button',
                text: '全部保存',
                iconCls: 'save',
                handler: function (v, o) {
                	var treeStore = Ext.getCmp("ImportReportInstanceContentTreeGridPanel_Id").getStore();
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
                            	saveAllImportedReportInstance();
                            }
                        });
                	}else{
                		saveAllImportedReportInstance();
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
            	id:"importReportInstanceTreePanel_Id"
            },{
            	region: 'center',
            	xtype: 'tabpanel',
            	id:"importReportInstanceReportTemplateTabPanel_Id",
                activeTab: 0,
                border: false,
                tabPosition: 'top',
                items: [{
                	title:'单井报表',
                	iconCls: 'check3',
                	id:'importReportInstanceSingleWellReportTemplatePanel_Id',
                	xtype: 'tabpanel',
                	activeTab: 0,
                    border: false,
                    tabPosition: 'top',
                    items: [{
                    	id:'importReportInstanceSingleWellDailyReportTemplatePanel_Id',
                    	title:'班报表',
                    	iconCls: 'check3',
                    	border: false,
                    	region: 'center',
                    	layout: "border",
                    	items: [{
                    		region: 'center',
                    		layout: "border",
                    		items: [{
                        		region: 'center',
                        		title:'单井班报表模板',
                        		id:"ReportInstanceSingleWellDailyReportTemplateTableInfoPanel_Id",
                                layout: 'fit',
                                border: false,
                                html:'<div class="ReportInstanceSingleWellDailyReportTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportInstanceSingleWellDailyReportTemplateTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	
                                    }
                                }
                        	},{
                        		region: 'south',
                            	height:'50%',
                            	title:'单井班报表内容',
                            	border: false,
                            	collapsible: true,
                                split: true,
                            	layout: 'fit',
                            	id:"ReportInstanceSingleWellDailyReportContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html:'<div class="ReportInstanceSingleWellDailyReportContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportInstanceSingleWellDailyReportContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	
                                    }
                                }
                        	}]
                    	}]
                    },{
                    	id:'importReportInstanceSingleWellRangeReportTemplatePanel_Id',
                    	title:'日报表',
                    	border: false,
                    	region: 'center',
                    	layout: "border",
                    	items: [{
                    		region: 'center',
                    		layout: "border",
                    		items: [{
                        		region: 'center',
                        		title:'单井日报表模板',
                        		id:"ReportInstanceSingleWellRangeReportTemplateTableInfoPanel_Id",
                                layout: 'fit',
                                border: false,
                                html:'<div class="ReportInstanceSingleWellRangeReportTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportInstanceSingleWellRangeReportTemplateTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	
                                    }
                                }
                        	},{
                        		region: 'south',
                            	height:'50%',
                            	title:'单井日报表内容',
                            	border: false,
                            	collapsible: true,
                                split: true,
                            	layout: 'fit',
                            	id:"ReportInstanceSingleWellRangeReportContentConfigTableInfoPanel_Id",
                                layout: 'fit',
                                html:'<div class="ReportInstanceSingleWellRangeReportContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ReportInstanceSingleWellRangeReportContentConfigTableInfoDiv_id"></div></div>',
                                listeners: {
                                    resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                    	
                                    }
                                }
                        	}]
                    	}]
                    }],
                    listeners: {
                    	beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
            				oldCard.setIconCls(null);
            				newCard.setIconCls('check3');
            			},
            			tabchange: function (tabPanel, newCard, oldCard, obj) {
            				
            			}
                    }
                },{
                	title:'区域报表',
                	id:'importReportInstanceProductionReportTemplatePanel_Id',
                	xtype: 'tabpanel',
                	activeTab: 0,
                    border: false,
                    tabPosition: 'top',
                    items: [{
                    	id:'importReportInstanceProductionRangeReportTemplatePanel_Id',
                    	title:'日报表',
                    	iconCls: 'check3',
                    	border: false,
                		layout: "border",
                		items: [{
                    		region: 'center',
                    		title:'区域日报模板',
                    		id:"importReportInstanceProductionTemplateTableInfoPanel_Id",
                            layout: 'fit',
                            border: false,
                            html:'<div class="importReportInstanceProductionTemplateTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="importReportInstanceProductionTemplateTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	
                                }
                            }
                    	},{
                    		region: 'south',
                        	height:'50%',
                        	title:'区域日报内容',
                        	border: false,
                        	collapsible: true,
                            split: true,
                        	layout: 'fit',
                        	id:"ProductionReportInstanceContentConfigTableInfoPanel_Id",
                            layout: 'fit',
                            html:'<div class="ProductionReportInstanceContentConfigTableInfoContainer" style="width:100%;height:100%;"><div class="con" id="ProductionReportInstanceContentConfigTableInfoDiv_id"></div></div>',
                            listeners: {
                                resize: function (thisPanel, width, height, oldWidth, oldHeight, eOpts) {
                                	
                                }
                            }
                    	}]
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
                	clearImportReportInstanceHandsontable();
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function clearImportReportInstanceHandsontable(){
	
}

function submitImportedReportInstanceFile() {
	clearImportReportInstanceHandsontable();
	var form = Ext.getCmp("ReportInstanceImportForm_Id");
    if(form.isValid()) {
        form.submit({
            url: context + '/acquisitionUnitManagerController/uploadImportedReportInstanceFile',
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
            	
            	var importReportInstanceContentTreeGridPanel = Ext.getCmp("ImportReportInstanceContentTreeGridPanel_Id");
            	if (isNotVal(importReportInstanceContentTreeGridPanel)) {
            		importReportInstanceContentTreeGridPanel.getStore().load();
            	}else{
            		Ext.create('AP.store.acquisitionUnit.ImportReportInstanceContentTreeInfoStore');
            	}
            },
            failure : function() {
            	Ext.Msg.alert("提示", "【<font color=red>上传失败 </font>】");
			}
        });
    }
    return false;
};

adviceImportReportInstanceCollisionInfoColor = function(val,o,p,e) {
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

function saveSingelImportedReportInstance(instanceName,unitName){
	Ext.Ajax.request({
		url : context + '/acquisitionUnitManagerController/saveSingelImportedReportInstance',
		method : "POST",
		params : {
			instanceName : instanceName,
			unitName : unitName
		},
		success : function(response) {
			var result = Ext.JSON.decode(response.responseText);
			if (result.success==true) {
				Ext.Msg.alert('提示', "<font color=blue>保存成功。</font>");
			}else{
				Ext.Msg.alert('提示', "<font color=red>保存失败。</font>");
			}
			Ext.getCmp("ImportReportInstanceContentTreeGridPanel_Id").getStore().load();

			var treeGridPanel = Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id");
            if (isNotVal(treeGridPanel)) {
            	treeGridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.acquisitionUnit.ModbusProtocolReportInstanceTreeInfoStore');
            }
		},
		failure : function() {
			Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
		}
	});
}

function saveAllImportedReportInstance(){
	var unitNameList=[];
	var treeStore = Ext.getCmp("ImportReportInstanceContentTreeGridPanel_Id").getStore();
	var count=treeStore.getCount();
	for(var i=0;i<count;i++){
		if(treeStore.getAt(i).data.classes==1 && treeStore.getAt(i).data.saveSign!=2){
			unitNameList.push(treeStore.getAt(i).data.text);
		}
	}
	if(unitNameList.length>0){
		Ext.Ajax.request({
			url : context + '/acquisitionUnitManagerController/saveAllImportedReportInstance',
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
				Ext.getCmp("ImportReportInstanceContentTreeGridPanel_Id").getStore().load();
				
				var treeGridPanel = Ext.getCmp("ModbusProtocolReportInstanceConfigTreeGridPanel_Id");
                if (isNotVal(treeGridPanel)) {
                	treeGridPanel.getStore().load();
                }else{
                	Ext.create('AP.store.acquisitionUnit.ModbusProtocolReportInstanceTreeInfoStore');
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

iconImportSingleReportInstanceAction = function(value, e, record) {
	var resultstring='';
	var instanceName=record.data.text;
	var unitName=record.data.unitName;

	if( record.data.classes==1 && record.data.saveSign!=2 ){
		resultstring="<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" " +
		"onclick=saveSingelImportedReportInstance(\""+instanceName+"\",\""+unitName+"\")>保存...</a>";
	}
	return resultstring;
}