Ext.define('AP.view.alarmQuery.CommunicationAlarmInfoPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.communicationAlarmInfoPanel',
    layout: "fit",
    id: "CommunicationAlarmInfoView_Id",
    border: false,
    //forceFit : true,
    initComponent: function () {
        var deviceCombStore = new Ext.data.JsonStore({
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
                    var wellName = Ext.getCmp('CommunicationAlarmDeviceListComb_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: 0,
                        wellName: wellName
                    };
                    Ext.apply(store.proxy.extraParams,new_params);
                }
            }
        });
        
        var deviceCombo = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: '井名',
                    id: "CommunicationAlarmDeviceListComb_Id",
                    labelWidth: 35,
                    width: 145,
                    labelAlign: 'left',
                    queryMode: 'remote',
                    typeAhead: true,
                    store: deviceCombStore,
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
                            deviceCombo.getStore().loadPage(1); // 加载井下拉框的store
                        },
                        select: function (combo, record, index) {
                        	Ext.getCmp("CommunicationAlarmOverviewSelectRow_Id").setValue(0);
                        	Ext.getCmp("CommunicationAlarmOverviewGridPanel_Id").getStore().loadPage(1);
                        }
                    }
                });
    	Ext.apply(this, {
    		layout: 'border',
    		tbar: [{
                id: 'CommunicationAlarmOverviewColumnStr_Id',
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'CommunicationAlarmDetailsColumnStr_Id',
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                id: 'CommunicationAlarmOverviewSelectRow_Id',
                xtype: 'textfield',
                value: 0,
                hidden: true
            },{
                xtype: 'button',
                text: cosog.string.refresh,
                iconCls: 'note-refresh',
                hidden:false,
                handler: function (v, o) {
                	Ext.getCmp('CommunicationAlarmQueryStartDate_Id').setValue('');
                	Ext.getCmp('CommunicationAlarmQueryStartTime_Hour_Id').setValue('');
                	Ext.getCmp('CommunicationAlarmQueryStartTime_Minute_Id').setValue('');
                	Ext.getCmp('CommunicationAlarmQueryStartTime_Second_Id').setValue('');
                	Ext.getCmp('CommunicationAlarmQueryEndDate_Id').setValue('');
                	Ext.getCmp('CommunicationAlarmQueryEndTime_Hour_Id').setValue('');
                	Ext.getCmp('CommunicationAlarmQueryEndTime_Minute_Id').setValue('');
                	Ext.getCmp('CommunicationAlarmQueryEndTime_Second_Id').setValue('');
                	var gridPanel = Ext.getCmp("CommunicationAlarmOverviewGridPanel_Id");
    				if (isNotVal(gridPanel)) {
    					gridPanel.getStore().loadPage(1);
    				}else{
    					Ext.create('AP.store.alarmQuery.CommunicationAlarmOverviewStore');
    				}
                }
    		},'-',deviceCombo,'-',{
            	xtype : "combobox",
				fieldLabel : '报警级别',
				id : 'CommunicationAlarmLevelComb_Id',
				labelWidth: 55,
                width: 135,
                labelAlign: 'left',
				triggerAction : 'all',
				displayField: "boxval",
                valueField: "boxkey",
				selectOnFocus : true,
			    forceSelection : true,
			    value:'',
			    allowBlank: false,
				editable : false,
				emptyText: cosog.string.all,
                blankText: cosog.string.all,
				store : new Ext.data.SimpleStore({
							fields : ['boxkey', 'boxval'],
							data : [['', '选择全部'],[100, '一级报警'],[200, '二级报警'],[300, '三级报警']]
						}),
				queryMode : 'local',
				listeners : {
					select:function(v,o){
						Ext.getCmp("CommunicationAlarmOverviewSelectRow_Id").setValue(0);
						Ext.getCmp("CommunicationAlarmOverviewGridPanel_Id").getStore().loadPage(1);
					}
				}
            },
//            '-',
            {
            	xtype : "combobox",
				fieldLabel : '是否发送短信',
				id : 'CommunicationAlarmIsSendMessageComb_Id',
				hidden: true,
				labelWidth: 80,
                width: 190,
                labelAlign: 'left',
				triggerAction : 'all',
				displayField: "boxval",
                valueField: "boxkey",
				selectOnFocus : true,
			    forceSelection : true,
			    value:'',
			    allowBlank: false,
				editable : false,
				emptyText: cosog.string.all,
                blankText: cosog.string.all,
				store : new Ext.data.SimpleStore({
							fields : ['boxkey', 'boxval'],
							data : [['', '选择全部'],[1, '是'],[0, '否']]
						}),
				queryMode : 'local',
				listeners : {
					select:function(v,o){
						Ext.getCmp("CommunicationAlarmOverviewGridPanel_Id").getStore().loadPage(1);
					}
				}
            },'-',{
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: '区间',
                labelWidth: 30,
                width: 125,
                format: 'Y-m-d ',
                value: '',
                id: 'CommunicationAlarmQueryStartDate_Id',
                listeners: {
                	select: function (combo, record, index) {
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'CommunicationAlarmQueryStartTime_Hour_Id',
                fieldLabel: '时',
                labelWidth: 15,
                width: 60,
                minValue: 0,
                maxValue: 23,
                value:'',
                msgTarget: 'none',
                regex:/^(2[0-3]|[0-1]?\d|\*|-|\/)$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                			field.focus(true, 100);
                		}
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'CommunicationAlarmQueryStartTime_Minute_Id',
                fieldLabel: '分',
                labelWidth: 15,
                width: 60,
                minValue: 0,
                maxValue: 59,
                value:'',
                msgTarget: 'none',
                regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                			field.focus(true, 100);
                		}
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'CommunicationAlarmQueryStartTime_Second_Id',
                fieldLabel: '秒',
                labelWidth: 15,
                width: 60,
                minValue: 0,
                maxValue: 59,
                value:'',
                msgTarget: 'none',
                regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                			field.focus(true, 100);
                		}
                    }
                }
            },{
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: '至',
                labelWidth: 15,
                width: 110,
                format: 'Y-m-d ',
                value: '',
                id: 'CommunicationAlarmQueryEndDate_Id',
                listeners: {
                	select: function (combo, record, index) {
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'CommunicationAlarmQueryEndTime_Hour_Id',
                fieldLabel: '时',
                labelWidth: 15,
                width: 60,
                minValue: 0,
                maxValue: 23,
                value:'',
                msgTarget: 'none',
                regex:/^(2[0-3]|[0-1]?\d|\*|-|\/)$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                			field.focus(true, 100);
                		}
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'CommunicationAlarmQueryEndTime_Minute_Id',
                fieldLabel: '分',
                labelWidth: 15,
                width: 60,
                minValue: 0,
                maxValue: 59,
                value:'',
                msgTarget: 'none',
                regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                			field.focus(true, 100);
                		}
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'CommunicationAlarmQueryEndTime_Second_Id',
                fieldLabel: '秒',
                labelWidth: 15,
                width: 60,
                minValue: 0,
                maxValue: 59,
                value:'',
                msgTarget: 'none',
                regex:/^[1-5]?\d([\/-][1-5]?\d)?$/,
                listeners: {
                	blur: function (field, event, eOpts) {
                		var r = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                		var flag=r.test(field.value);
                		if(!flag){
                			Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                			field.focus(true, 100);
                		}
                    }
                }
            },'-',{
                xtype: 'button',
                text: cosog.string.search,
                iconCls: 'search',
                handler: function () {
                	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                	var startTime_Hour=Ext.getCmp('CommunicationAlarmQueryStartTime_Hour_Id').getValue();
                	if(!r.test(startTime_Hour)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                		Ext.getCmp('CommunicationAlarmQueryStartTime_Hour_Id').focus(true, 100);
                		return;
                	}
                	var startTime_Minute=Ext.getCmp('CommunicationAlarmQueryStartTime_Minute_Id').getValue();
                	if(!r2.test(startTime_Minute)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                		Ext.getCmp('CommunicationAlarmQueryStartTime_Minute_Id').focus(true, 100);
                		return;
                	}
                	var startTime_Second=Ext.getCmp('CommunicationAlarmQueryStartTime_Second_Id').getValue();
                	if(!r2.test(startTime_Second)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                		Ext.getCmp('CommunicationAlarmQueryStartTime_Second_Id').focus(true, 100);
                		return;
                	}
                	
                	var endTime_Hour=Ext.getCmp('CommunicationAlarmQueryEndTime_Hour_Id').getValue();
                	if(!r.test(endTime_Hour)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                		Ext.getCmp('CommunicationAlarmQueryEndTime_Hour_Id').focus(true, 100);
                		return;
                	}
                	var endTime_Minute=Ext.getCmp('CommunicationAlarmQueryEndTime_Minute_Id').getValue();
                	if(!r2.test(endTime_Minute)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                		Ext.getCmp('CommunicationAlarmQueryEndTime_Minute_Id').focus(true, 100);
                		return;
                	}
                	var endTime_Second=Ext.getCmp('CommunicationAlarmQueryEndTime_Second_Id').getValue();
                	if(!r2.test(endTime_Second)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                		Ext.getCmp('CommunicationAlarmQueryEndTime_Second_Id').focus(true, 100);
                		return;
                	}
                	var gridPanel = Ext.getCmp("CommunicationAlarmGridPanel_Id");
                	if (isNotVal(gridPanel)) {
                		gridPanel.getStore().loadPage(1);
                	}
                }
            },'-',{
                xtype: 'button',
                text: '导出设备列表',
                iconCls: 'export',
                hidden:false,
                handler: function (v, o) {
                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                	var deviceType=0;
                	var deviceName=Ext.getCmp('CommunicationAlarmDeviceListComb_Id').getValue();
                	var isSendMessage=Ext.getCmp('CommunicationAlarmIsSendMessageComb_Id').getValue();
                	var alarmType=3;
               	 	var alarmLevel='';
               	 	
               	 	var fileName='抽油机井通信报警设备列表';
               	 	var title='抽油机井通信报警设备列表';
               	 	var columnStr=Ext.getCmp("CommunicationAlarmOverviewColumnStr_Id").getValue();
               	 	exportAlarmOverviewDataExcel(orgId,deviceType,deviceName,alarmType,alarmLevel,isSendMessage,fileName,title,columnStr);
                }
            },'-', {
                xtype: 'button',
                text: '导出报警数据',
                iconCls: 'export',
                hidden:false,
                handler: function (v, o) {
                	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                	var startTime_Hour=Ext.getCmp('CommunicationAlarmQueryStartTime_Hour_Id').getValue();
                	if(!r.test(startTime_Hour)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                		Ext.getCmp('CommunicationAlarmQueryStartTime_Hour_Id').focus(true, 100);
                		return;
                	}
                	var startTime_Minute=Ext.getCmp('CommunicationAlarmQueryStartTime_Minute_Id').getValue();
                	if(!r2.test(startTime_Minute)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                		Ext.getCmp('CommunicationAlarmQueryStartTime_Minute_Id').focus(true, 100);
                		return;
                	}
                	var startTime_Second=Ext.getCmp('CommunicationAlarmQueryStartTime_Second_Id').getValue();
                	if(!r2.test(startTime_Second)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                		Ext.getCmp('CommunicationAlarmQueryStartTime_Second_Id').focus(true, 100);
                		return;
                	}
                	
                	var endTime_Hour=Ext.getCmp('CommunicationAlarmQueryEndTime_Hour_Id').getValue();
                	if(!r.test(endTime_Hour)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                		Ext.getCmp('CommunicationAlarmQueryEndTime_Hour_Id').focus(true, 100);
                		return;
                	}
                	var endTime_Minute=Ext.getCmp('CommunicationAlarmQueryEndTime_Minute_Id').getValue();
                	if(!r2.test(endTime_Minute)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                		Ext.getCmp('CommunicationAlarmQueryEndTime_Minute_Id').focus(true, 100);
                		return;
                	}
                	var endTime_Second=Ext.getCmp('CommunicationAlarmQueryEndTime_Second_Id').getValue();
                	if(!r2.test(endTime_Second)){
                		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                		Ext.getCmp('CommunicationAlarmQueryEndTime_Second_Id').focus(true, 100);
                		return;
                	}
                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                	var deviceType=0;
                	var deviceName='';
                	var deviceId=0;
                	if(Ext.getCmp("CommunicationAlarmOverviewGridPanel_Id").getSelectionModel().getSelection().length>0){
                		deviceName=Ext.getCmp("CommunicationAlarmOverviewGridPanel_Id").getSelectionModel().getSelection()[0].data.wellName;
                    	deviceId=  Ext.getCmp("CommunicationAlarmOverviewGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
                	}
                	var isSendMessage=Ext.getCmp('CommunicationAlarmIsSendMessageComb_Id').getValue();
                	var startDate=Ext.getCmp('CommunicationAlarmQueryStartDate_Id').rawValue;
                    var endDate=Ext.getCmp('CommunicationAlarmQueryEndDate_Id').rawValue;
               	 	var alarmType=3;
               	 	var alarmLevel=Ext.getCmp('CommunicationAlarmLevelComb_Id').getValue();
               	 	
               	 	var fileName='抽油机井'+deviceName+'通信报警数据';
               	 	var title='抽油机井'+deviceName+'通信报警数据';
               	 	var columnStr=Ext.getCmp("CommunicationAlarmDetailsColumnStr_Id").getValue();
               	 	exportAlarmDataExcel(orgId,deviceType,deviceId,deviceName,getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),alarmType,alarmLevel,isSendMessage,fileName,title,columnStr);
                }
            }],
    		items: [{
            	region: 'west',
            	width: '30%',
    			title: '设备列表',
    			id: 'CommunicationAlarmOverviewPanel_Id',
    			autoScroll: false,
                scrollable: false,
                split: true,
                collapsible: true,
    			layout: 'fit'
    		},{
    			region: 'center',
    			title: '报警数据',
    			id: 'CommunicationAlarmDetailsPanel_Id',
                autoScroll: false,
                layout: 'fit'
    		}]
        });
        this.callParent(arguments);
    }
});