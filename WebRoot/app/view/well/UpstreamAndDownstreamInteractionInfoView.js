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
                        	Ext.getCmp("RPCRealTimeMonitoringListGridPanel_Id").getStore().loadPage(1);
                        }
                    }
                });
        Ext.applyIf(me, {
            items: [{
                border: false,
                layout: 'border',
                tbar:[{
                	id: 'UpstreamAndDownstreamInteractionDeviceListSelectRow_Id',
                	xtype: 'textfield',
                    value: -1,
                    hidden: true
                 },rpcDeviceCombo,'-',{
                     xtype: 'radiogroup',
                     fieldLabel: '操作',
                     labelWidth: 30,
                     id: 'UpstreamAndDownstreamInteractionOperation_Id',
                     cls: 'x-check-group-alt',
                     name: 'operation',
                     items: [
                         {boxLabel: '模型下行',labelWidth: 30, inputValue: 1, checked: true},
                         {boxLabel: '配置下行',labelWidth: 30, inputValue: 2}
                     ],
                     listeners: {
                    	 change: function (radiogroup, newValue, oldValue, eOpts) {
                    		 if(newValue.operation==1){
                    			 Ext.getCmp("UpstreamAndDownstreamInteractionSyncBtn_Id").show();
                    		 }else{
                    			 Ext.getCmp("UpstreamAndDownstreamInteractionSyncBtn_Id").hide();
                    		 }
                    		 
                    		 var _record = Ext.getCmp("UpstreamAndDownstreamInteractionDeviceListGridPanel_Id").getSelectionModel().getSelection();
                    		 if(_record.length>0){
                    			 var upCommStatus = _record[0].data.upCommStatus;
                    			 var downCommStatus = _record[0].data.downCommStatus;
                    			 if(parseInt(upCommStatus)==0 || parseInt(downCommStatus)==0){
                    				 Ext.getCmp("UpstreamAndDownstreamInteractionSendBtn_Id").disable();
                    				 Ext.getCmp('UpstreamAndDownstreamInteractionConfigDataTextArea_Id').setValue('');
                    			 }else if(parseInt(upCommStatus)==1 && parseInt(downCommStatus)==1){
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
                	title:'设备列表',
                    id:'UpstreamAndDownstreamInteractionDeviceListPanel_Id',
                    border: false,
                    layout: 'fit'
                }, {
                	region: 'east',
                    width: '65%',
                    layout: 'border',
                    items: [{
                    	region: 'center',
                    	title:'下行数据',
                    	xtype:'form',
                		layout: 'auto',
                        border: false,
                        collapsible: false,
                        split: true,
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
                        border: false,
                        layout: 'fit',
                        title:'帮助',
                        id:'UpstreamAndDownstreamInteractionConfigHelpDocPanel_Id',
            			autoScroll: true,
            			html:''
                    }]
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
