Ext.define("AP.view.well.UpstreamAndDownstreamInteractionInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.upstreamAndDownstreamInteractionInfoView',
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var rpcCombStore = new Ext.data.JsonStore({
        	pageSize:defaultWellComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
            	url: context + '/wellInformationManagerController/loadWellComboxList',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                	var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var wellName = Ext.getCmp('UpstreamAndDownstreamInteractionRPCDeviceListComb_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: 0,
                        wellName: wellName
                    };
                    Ext.apply(store.proxy.extraParams,new_params);
                }
            }
        });
        
        var rpcDeviceCombo = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: '井名',
                    id: "UpstreamAndDownstreamInteractionRPCDeviceListComb_Id",
                    labelWidth: 35,
                    width: 145,
                    labelAlign: 'left',
                    queryMode: 'remote',
                    typeAhead: true,
                    store: rpcCombStore,
                    autoSelect: false,
                    editable: true,
                    triggerAction: 'all',
                    displayField: "boxval",
                    valueField: "boxkey",
                    pageSize:comboxPagingStatus,
                    minChars:0,
                    emptyText: cosog.string.all,
                    blankText: cosog.string.all,
                    listeners: {
                        expand: function (sm, selections) {
                            rpcDeviceCombo.getStore().loadPage(1); // 加载井下拉框的store
                        },
                        select: function (combo, record, index) {
                        	Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getStore().loadPage(1);
                        }
                    }
                });
        Ext.applyIf(me, {
            items: [{
                border: false,
                layout: 'border',
                items: [{
                	region: 'center',
                	border: false,
                    layout: 'border',
                    items: [{
                    	region: 'center',
                    	title:'设备列表',
                    	layout: 'fit',
                    	border: false,
                        id:'UpstreamAndDownstreamInteractionDeviceListPanel_Id'
                    }],
                    tbar:[{
                    	id: 'UpstreamAndDownstreamInteractionDeviceListSelectRow_Id',
                    	xtype: 'textfield',
                        value: -1,
                        hidden: true
                     },rpcDeviceCombo]
                }, {
                	region: 'east',
                    width: '65%',
                    header: false,
                    collapsible: true,
                    split: true,
                    xtype: 'tabpanel',
                    id:"UpstreamAndDownstreamInteractionConfigTabpanel_Id",
                    activeTab: 0,
            		border: false,
            		tabPosition: 'bottom',
            		items: [{
            			title: '下行数据',
            			id:"UpstreamAndDownstreamInteractionConfigPanel1_Id",
            			layout: 'border',
            			tbar:[{
                             xtype: 'radiogroup',
                             fieldLabel: '操作',
                             labelWidth: 30,
                             id: 'UpstreamAndDownstreamInteractionOperation_Id',
                             cls: 'x-check-group-alt',
                             name: 'operation',
                             items: [
                                 {boxLabel: '模型下行',width: 70, inputValue: 1, checked: true},
                                 {boxLabel: '配置下行',width: 70, inputValue: 2},
                                 {boxLabel: '时钟下行',width: 70, inputValue: 3},
                                 {boxLabel: '看门狗重启',width: 85, inputValue: 4},
                                 {boxLabel: '上死点停抽',width: 85, inputValue: 5},
                                 {boxLabel: '下死点停抽',width: 85, inputValue: 6},
                                 {boxLabel: '即时停抽',width: 70, inputValue: 7}
                             ],
                             listeners: {
                            	 change: function (radiogroup, newValue, oldValue, eOpts) {
                            		 if(newValue.operation==1){
                            			 Ext.getCmp("UpstreamAndDownstreamInteractionSyncBtn_Id").show();
                            		 }else{
                            			 Ext.getCmp("UpstreamAndDownstreamInteractionSyncBtn_Id").hide();
                            		 }
                            		 if(newValue.operation==3){
                            			 Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").setText("时钟同步");
                            		 }else{
                            			 Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").setText("发送");
                            		 }
                            		 var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
                            		 if(_record.length>0){
                            			 var upCommStatus = _record[0].data.upCommStatus;
                            			 var downCommStatus = _record[0].data.downCommStatus;
                            			 
                            			 if(parseInt(downCommStatus)==0){
                            				 Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").disable();
                            				 Ext.getCmp('UpstreamAndDownstreamInteractionConfigDataTextArea_Id').setValue('');
                            			 }else if(parseInt(downCommStatus)==1){
                            				 Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").enable();
                            				 requestConfigData();
                            			 }
                            		 }
                            	 }
                             }
                         },'->',{
                             xtype: 'button',
                             text: '模型同步',
                             iconCls: 'sync',
                             id:'UpstreamAndDownstreamInteractionSyncBtn_Id',
                             pressed: false,
                             hidden: false,
                             handler: function (v, o) {
                             	syncModelData();
                             }
                         },{
                             xtype: 'button',
                             text: '发送',
                             iconCls: 'send',
                             id:'UpstreamAndDownstreamInteractionSendBtn_Id',
                             pressed: false,
                             hidden:false,
                             handler: function (v, o) {
                             	producerMsg();
                             }
                         }],
                        items: [{
                        	region: 'center',
                        	title:'下行数据',
                        	xtype:'form',
                    		layout: 'auto',
                            border: false,
                            autoScroll:true,
                            scrollable: true,
                            items: [{
                            	xtype:'textareafield',
                            	id:'UpstreamAndDownstreamInteractionConfigDataTextArea_Id',
                            	margin: '0 0 0 0',
                            	padding:0,
                            	grow:false,//自动增长
                            	border: false,
                            	width:'100%',
                                height: '100%',
                                anchor: '100%',
                                emptyText: '在此输入下行数据...',
                                autoScroll:true,
                                scrollable: true,
                                readOnly:false
                            }]
                        },{
                        	region: 'east',
                            width: '50%',
                            header: true,
                            collapsible: true,
                            split: true,
                            border: false,
                            layout: 'fit',
                            title:'帮助',
                            id:'UpstreamAndDownstreamInteractionConfigHelpDocPanel_Id',
                			autoScroll: true,
                			html:''
                        }]
            		},{
            			title: '含水仪数据',
            			id:"UpstreamAndDownstreamInteractionConfigPanel2_Id",
            			layout: 'border',
            			tbar:[{
                            id: 'UpstreamAndDownstreamInteractionWaterCutRawDataColumnStr_Id',
                            xtype: 'textfield',
                            value: '',
                            hidden: true
                        },'->',{
                            xtype: 'button',
                            text: cosog.string.exportExcel,
                            id:'UpstreamAndDownstreamInteractionExportWaterCutBtn_Id',
                            iconCls: 'export',
                            hidden: false,
                            handler: function (v, o) {
                                var fields = "";
                                var heads = "";
                                var lockedheads = "";
                                var unlockedheads = "";
                                var lockedfields = "";
                                var unlockedfields = "";
                                var wellName ='';
                        		var wellId = '';
                        		var signinId ='';
                        		var slave = '';
                            	var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
                            	if(_record.length>0){
                            		var wellName = _record[0].data.wellName;
                            		var wellId = _record[0].data.id;
                            		var signinId = _record[0].data.signinId;
                            		var slave = _record[0].data.slave;
                            	}
                                var url = context + '/wellInformationManagerController/exportWaterCutRawData';
                                
                                var columnStr=Ext.getCmp("UpstreamAndDownstreamInteractionWaterCutRawDataColumnStr_Id").getValue();
                                var columns_ = Ext.JSON.decode(columnStr);
                                
                                Ext.Array.each(columns_, function (name, index, countriesItSelf) {
                                    var column = columns_[index];
                                    if (index > 0 && !column.hidden) {
                                    	if(column.locked){
                                    		lockedfields += column.dataIndex + ",";
                                    		lockedheads += column.text + ",";
                                    	}else{
                                    		unlockedfields += column.dataIndex + ",";
                                    		unlockedheads += column.text + ",";
                                    	}
                                        
                                    }
                                });
                                if (isNotVal(lockedfields)) {
                                	lockedfields = lockedfields.substring(0, lockedfields.length - 1);
                                	lockedheads = lockedheads.substring(0, lockedheads.length - 1);
                                }
                                if (isNotVal(unlockedfields)) {
                                	unlockedfields = unlockedfields.substring(0, unlockedfields.length - 1);
                                	unlockedheads = unlockedheads.substring(0, unlockedheads.length - 1);
                                }
                                fields = "id";
                                if(isNotVal(lockedfields)){
                                	fields+=","+lockedfields;
                                }
                                if(isNotVal(unlockedfields)){
                                	fields+=","+unlockedfields;
                                }
                                
                                heads = "序号";
                                if(isNotVal(lockedheads)){
                                	heads+=","+lockedheads;
                                }
                                if(isNotVal(unlockedheads)){
                                	heads+=","+unlockedheads;
                                }

                                var param = "&fields=" + fields 
                                + "&heads=" + URLencode(URLencode(heads)) 
                                + "&signinId=" + signinId 
                                + "&slave=" + slave
                                + "&fileName=" + URLencode(URLencode(wellName+"含水数据")) + "&title=" + URLencode(URLencode(wellName+"含水数据"));
                                openExcelWindow(url + '?flag=true' + param);
                            }
                        }, '-',{
                             xtype: 'button',
                             text: '读含水',
                             iconCls: 'send',
                             id:'UpstreamAndDownstreamInteractionReadWaterCutBtn_Id',
                             pressed: false,
                             hidden:false,
                             handler: function (v, o) {
                            	var gridPanel = Ext.getCmp("UpstreamAndDownstreamInteractionWaterCutRawDataGridPanel_Id");
                     			if (isNotVal(gridPanel)) {
                     				gridPanel.getStore().load();
                     			}else{
                     				Ext.create('AP.store.well.WaterCutRawDataStore');
                     			}
                             }
                         }],
                         items: [{
                         	region: 'center',
                         	title:'含水仪数据',
                         	layout: 'fit',
                         	border: false,
                            id:'UpstreamAndDownstreamInteractionWaterCutRawDataPanel_Id'
                         }],
            		}],
            		listeners: {
            			tabchange: function (tabPanel, newCard,oldCard, obj) {
            				var upCommStatus = 0;
               			 	var downCommStatus = 0;
            				var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
            				if(_record.length>0){
            					upCommStatus = _record[0].data.upCommStatus;
                   			 	downCommStatus = _record[0].data.downCommStatus;
            				}
            				
            				if(newCard.id=="UpstreamAndDownstreamInteractionConfigPanel1_Id"){
            					if(parseInt(downCommStatus)==0){
               			 			Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").disable();
               			 			Ext.getCmp('UpstreamAndDownstreamInteractionConfigDataTextArea_Id').setValue('');
               			 		}else if(parseInt(downCommStatus)==1){
               			 			Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").enable();
               			 			requestConfigData();
               			 		}
        					}else if(newCard.id=="UpstreamAndDownstreamInteractionConfigPanel2_Id"){
        						Ext.getCmp("UpstreamAndDownstreamInteractionWaterCutRawDataPanel_Id").removeAll();
        						Ext.getCmp("UpstreamAndDownstreamInteractionExportWaterCutBtn_Id").disable();
               			 		if(parseInt(downCommStatus)==0){
               			 			Ext.getCmp("UpstreamAndDownstreamInteractionReadWaterCutBtn_Id").disable();
               			 		}else if(parseInt(downCommStatus)==1){
               			 			Ext.getCmp("UpstreamAndDownstreamInteractionReadWaterCutBtn_Id").enable();
               			 		}
        					}
        				}
            		}
                }],
                listeners: {
                	afterrender: function ( panel, eOpts) {
                		//加载帮助文档
        				Ext.Ajax.request({
        		    		method:'POST',
        		    		url:context + '/wellInformationManagerController/getHelpDocHtml',
        		    		success:function(response) {
        		    			var p =Ext.getCmp("UpstreamAndDownstreamInteractionConfigHelpDocPanel_Id");
        		    			p.body.update(response.responseText);
        		    		},
        		    		failure:function(){
        		    			Ext.MessageBox.alert("信息","帮助文档加载失败");
        		    		},
        		    		params: {
        		            }
        		    	}); 
                	}
                }
            }]
        });
        me.callParent(arguments);
    }
});

function createUpstreamAndDownstreamInteractionDeviceListColumn(columnInfo) {
    var myArr = columnInfo;

    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        var flex_ = "";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        if (isNotVal(attr.flex)) {
        	flex_ = ",flex:" + attr.flex;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_+flex_;
        if (attr.dataIndex.toUpperCase() == 'id'.toUpperCase()) {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:false";
        }
        else if (attr.dataIndex.toUpperCase()=='wellName'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
        }
        else if (attr.dataIndex.toUpperCase()=='commStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceCommStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase()=='upCommStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceUpCommStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase()=='downCommStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceDownCommStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRunStatusColor(value,o,p,e);}";
        }
        else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
            //        	myColumns += hidden_ + lock_ + width_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "'";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};

function syncModelData(){
    var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
	if(_record.length>0){
		
		var wellName = _record[0].data.wellName;
		var wellId = _record[0].data.id;
		var signinId = _record[0].data.signinId;
		var slave = _record[0].data.slave;

    	Ext.Ajax.request({
    		method:'POST',
    		url:context + '/wellInformationManagerController/getDeviceModelData',
    		success:function(response) {
    			Ext.getCmp('UpstreamAndDownstreamInteractionConfigDataTextArea_Id').setValue(response.responseText);
    		},
    		failure:function(){
    			Ext.MessageBox.alert("信息","请求失败");
    		},
    		params: {
    			wellId:wellId,
    			signinId:signinId,
    			slave:slave
            }
    	}); 
	}
}

function producerMsg(){
    var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
	if(_record.length>0){
		var type = Ext.getCmp("UpstreamAndDownstreamInteractionOperation_Id").getValue().operation;
		var operationName ='';
		if(parseInt(type)==1){
			operationName='模型下行';
		}else if(parseInt(type)==2){
			operationName='配置下行';
		}else if(parseInt(type)==3){
			operationName='时钟下行';
		}else if(parseInt(type)==4){
			operationName='看门狗重启下行';
		}else if(parseInt(type)==5){
			operationName='上死点停抽下行';
		}else if(parseInt(type)==6){
			operationName='下死点停抽下行';
		}
		var wellName = _record[0].data.wellName;
		var wellId = _record[0].data.id;
		var signinId = _record[0].data.signinId;
		var slave = _record[0].data.slave;
		var data=Ext.getCmp('UpstreamAndDownstreamInteractionConfigDataTextArea_Id').getValue();
		var operaName="是否对设备<font color=red>"+wellName+"</font>执行<font color=red>"+operationName+"</font>操作！";
		Ext.Msg.confirm("操作确认", operaName, function (btn) {
            if (btn == "yes") {
            	Ext.Ajax.request({
            		method:'POST',
            		url:context + '/wellInformationManagerController/downstreamRPCData',
            		success:function(response) {
            			rdata=Ext.JSON.decode(response.responseText);
            			if (rdata.success && rdata.msg==1 ) {
                        	Ext.MessageBox.alert("信息","下行成功。");
                        } else if (rdata.success && rdata.msg==0 ) {
                        	Ext.MessageBox.alert("信息","<font color=red>下行失败<font color=red>!");
                        }else if (!rdata.success){
                        	Ext.MessageBox.alert("信息","<font color=red>发送失败<font color=red>!");
                        }
            		},
            		failure:function(){
            			Ext.MessageBox.alert("信息","请求失败");
            		},
            		params: {
            			type: type,
            			wellId:wellId,
            			signinId:signinId,
            			slave:slave,
            			data,data
                    }
            	}); 
            }
        });
	}
}

function requestConfigData(){
	Ext.getCmp('UpstreamAndDownstreamInteractionConfigDataTextArea_Id').setValue('');
	var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
	if(_record.length>0){
		var type = Ext.getCmp("UpstreamAndDownstreamInteractionOperation_Id").getValue().operation;
		var wellName = _record[0].data.wellName;
		var wellId = _record[0].data.id;
		var signinId = _record[0].data.signinId;
		var slave = _record[0].data.slave;
		Ext.Ajax.request({
    		method:'POST',
    		url:context + '/wellInformationManagerController/requestConfigData',
    		success:function(response) {
    			if (isNotVal(response.responseText)) {
    				rdata=Ext.JSON.decode(response.responseText);
    				if(rdata.ResultStatus==1){
    					Ext.getCmp('UpstreamAndDownstreamInteractionConfigDataTextArea_Id').setValue(jsonFormat(JSON.stringify(rdata.Message)));
    				}
    			}
    		},
    		failure:function(){
    			Ext.MessageBox.alert("信息","请求失败");
    		},
    		params: {
    			type: type,
    			wellId:wellId,
    			signinId:signinId,
    			slave:slave
            }
    	});
	}
}
