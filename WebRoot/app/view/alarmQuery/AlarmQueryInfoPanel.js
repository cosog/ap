var AlarmQuerySecondTabPanelItems=[{
	title: '工况诊断报警',
	id:'FESDiagramResultAlarmInfoTabPanel_Id',
	hidden:onlyMonitor,
	iconCls: onlyMonitor?null:'check3',
	layout: 'border',
	items: [{
    	region: 'west',
    	width: '30%',
		title: '设备列表',
		id: 'FESDiagramResultAlarmOverviewPanel_Id',
		autoScroll: false,
        scrollable: false,
        split: true,
        collapsible: true,
		layout: 'fit'
	},{
		region: 'center',
		title: '报警数据',
		id: 'FESDiagramResultAlarmDetailsPanel_Id',
        autoScroll: false,
        layout: 'fit'
	}]
},{
//	title: '<div style="color:#6C6262;font-size:11px;font-family:SimSun">运行状态报警</div>',
	title: '运行状态报警',
	id:'RunStatusAlarmInfoTabPanel_Id',
	layout: "border",
	iconCls: onlyMonitor?'check3':null,
	items: [{
		region: 'west',
    	width: '30%',
		title: '设备列表',
		id: 'RunStatusAlarmOverviewPanel_Id',
		autoScroll: false,
        scrollable: false,
        split: true,
        collapsible: true,
		layout: 'fit'
	},{
        region: 'center',
		title: '报警数据',
		id: 'RunStatusAlarmDetailsPanel_Id',
        autoScroll: false,
        layout: 'fit'
	}]
},{
//	title: '<div style="color:#6C6262;font-size:11px;font-family:SimSun">通信状态报警</div>',
	title: '通信状态报警',
	id:'CommunicationAlarmInfoTabPanel_Id',
	layout: "border",
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
},{
//	title: '<div style="color:#6C6262;font-size:11px;font-family:SimSun">数据量报警</div>',
	title: '数据量报警',
	id:'NumericValueAlarmInfoTabPanel_Id',
	layout: "border",
	items: [{
    	region: 'west',
    	width: '30%',
		title: '设备列表',
		id: 'NumericValueAlarmOverviewPanel_Id',
		autoScroll: false,
        scrollable: false,
        split: true,
        collapsible: true,
		layout: 'fit'
	},{
		region: 'center',
		title: '报警数据',
		id: 'NumericValueAlarmDetailsPanel_Id',
        autoScroll: false,
        layout: 'fit'
	}]
},{
//	title: '<div style="color:#6C6262;font-size:11px;font-family:SimSun">枚举量报警</div>',
	title: '枚举量报警',
	id:'EnumValueAlarmInfoTabPanel_Id',
//	items: [EnumValueAlarmInfoPanel],
	layout: "border",
	items: [{
    	region: 'west',
    	width: '30%',
		title: '设备列表',
		id: 'EnumValueAlarmOverviewPanel_Id',
		autoScroll: false,
        scrollable: false,
        split: true,
        collapsible: true,
		layout: 'fit'
	},{
		region: 'center',
		title: '报警数据',
		id: 'EnumValueAlarmDetailsPanel_Id',
        autoScroll: false,
        layout: 'fit'
	}]
},{
//	title: '<div style="color:#6C6262;font-size:11px;font-family:SimSun">开关量报警</div>',
	title: '开关量报警',
	id:'SwitchingValueAlarmInfoTabPanel_Id',
//	items: [SwitchingValueAlarmInfoPanel],
	layout: "border",
	items: [{
    	region: 'west',
    	width: '30%',
		title: '设备列表',
		id: 'SwitchingValueAlarmOverviewPanel_Id',
		autoScroll: false,
        scrollable: false,
        split: true,
        collapsible: true,
		layout: 'fit'
	},{
		region: 'center',
		title: '报警数据',
		id: 'SwitchingValueAlarmDetailsPanel_Id',
        autoScroll: false,
        layout: 'fit'
	}]
}];
Ext.define("AP.view.alarmQuery.AlarmQueryInfoPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.alarmQueryInfoPanel', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        
        
        
//        var FESDiagramResultAlarmInfoPanel = Ext.create('AP.view.alarmQuery.FESDiagramResultAlarmInfoPanel');
//        var RunStatusAlarmInfoPanel = Ext.create('AP.view.alarmQuery.RunStatusAlarmInfoPanel');
//        var CommunicationAlarmInfoPanel = Ext.create('AP.view.alarmQuery.CommunicationAlarmInfoPanel');
//        var NumericValueAlarmInfoPanel = Ext.create('AP.view.alarmQuery.NumericValueAlarmInfoPanel');
//        var EnumValueAlarmInfoPanel = Ext.create('AP.view.alarmQuery.EnumValueAlarmInfoPanel');
//        var SwitchingValueAlarmInfoPanel = Ext.create('AP.view.alarmQuery.SwitchingValueAlarmInfoPanel');
        
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
                    var deviceName = Ext.getCmp('AlarmDeviceListComb_Id').getValue();
                    var deviceType = getDeviceTypeFromTabId("AlarmQueryRootTabPanel");
                    var new_params = {
                        orgId: leftOrg_Id,
                        deviceType: deviceType,
                        deviceName: deviceName
                    };
                    Ext.apply(store.proxy.extraParams,new_params);
                }
            }
        });
        
        var deviceCombo = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: deviceShowName,
                    id: "AlarmDeviceListComb_Id",
                    labelWidth: 8*deviceShowNameLength,
                    width: (8*deviceShowNameLength+110),
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
                        	Ext.getCmp("AlarmOverviewSelectRow_Id").setValue(0);
                        	Ext.getCmp("AlarmOverviewGridPanel_Id").getStore().loadPage(1);
                        }
                    }
                });
        Ext.apply(me, {
        	items: [{
        		xtype: 'tabpanel',
        		id:"AlarmQuerySecondTabPanel",
        		activeTab: onlyMonitor?1:0,
        		border: false,
//        		tabPosition: 'left',
        		tabPosition: 'top',
        		tbar: [{
                    id: 'AlarmOverviewColumnStr_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'AlarmDetailsColumnStr_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'AlarmOverviewSelectRow_Id',
                    xtype: 'textfield',
                    value: 0,
                    hidden: true
                },{
                    xtype: 'button',
                    text: cosog.string.refresh,
                    iconCls: 'note-refresh',
                    hidden:false,
                    handler: function (v, o) {
                    	var gridPanel = Ext.getCmp("AlarmOverviewGridPanel_Id");
        				if (isNotVal(gridPanel)) {
        					gridPanel.getStore().loadPage(1);
        				}else{
        					Ext.create('AP.store.alarmQuery.AlarmOverviewStore');
        				}
                    }
        		},'-',deviceCombo,'-',{
                	xtype : "combobox",
    				fieldLabel : '报警级别',
    				id : 'AlarmLevelComb_Id',
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
    						Ext.getCmp("AlarmOverviewSelectRow_Id").setValue(0);
    						Ext.getCmp("AlarmOverviewGridPanel_Id").getStore().loadPage(1);
    					}
    				}
                },
//                '-',
                {
                	xtype : "combobox",
    				fieldLabel : '是否发送短信',
    				id : 'AlarmIsSendMessageComb_Id',
    				hidden: true,
    				labelWidth: 80,
                    width: 160,
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
    						Ext.getCmp("AlarmOverviewGridPanel_Id").getStore().loadPage(1);
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
                    id: 'AlarmQueryStartDate_Id',
                    listeners: {
                    	select: function (combo, record, index) {
                        }
                    }
                },{
                	xtype: 'numberfield',
                	id: 'AlarmQueryStartTime_Hour_Id',
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
                	id: 'AlarmQueryStartTime_Minute_Id',
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
                	id: 'AlarmQueryStartTime_Second_Id',
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
                    id: 'AlarmQueryEndDate_Id',
                    listeners: {
                    	select: function (combo, record, index) {
                        }
                    }
                },{
                	xtype: 'numberfield',
                	id: 'AlarmQueryEndTime_Hour_Id',
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
                	id: 'AlarmQueryEndTime_Minute_Id',
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
                	id: 'AlarmQueryEndTime_Second_Id',
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
                    	var startTime_Hour=Ext.getCmp('AlarmQueryStartTime_Hour_Id').getValue();
                    	if(!r.test(startTime_Hour)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                    		Ext.getCmp('AlarmQueryStartTime_Hour_Id').focus(true, 100);
                    		return;
                    	}
                    	var startTime_Minute=Ext.getCmp('AlarmQueryStartTime_Minute_Id').getValue();
                    	if(!r2.test(startTime_Minute)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                    		Ext.getCmp('AlarmQueryStartTime_Minute_Id').focus(true, 100);
                    		return;
                    	}
                    	var startTime_Second=Ext.getCmp('AlarmQueryStartTime_Second_Id').getValue();
                    	if(!r2.test(startTime_Second)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                    		Ext.getCmp('AlarmQueryStartTime_Second_Id').focus(true, 100);
                    		return;
                    	}
                    	
                    	var endTime_Hour=Ext.getCmp('AlarmQueryEndTime_Hour_Id').getValue();
                    	if(!r.test(endTime_Hour)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                    		Ext.getCmp('AlarmQueryEndTime_Hour_Id').focus(true, 100);
                    		return;
                    	}
                    	var endTime_Minute=Ext.getCmp('AlarmQueryEndTime_Minute_Id').getValue();
                    	if(!r2.test(endTime_Minute)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                    		Ext.getCmp('AlarmQueryEndTime_Minute_Id').focus(true, 100);
                    		return;
                    	}
                    	var endTime_Second=Ext.getCmp('AlarmQueryEndTime_Second_Id').getValue();
                    	if(!r2.test(endTime_Second)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                    		Ext.getCmp('AlarmQueryEndTime_Second_Id').focus(true, 100);
                    		return;
                    	}
                    	var gridPanel = Ext.getCmp("AlarmGridPanel_Id");
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
                    	var deviceType=getDeviceTypeFromTabId("AlarmQueryRootTabPanel");
                    	var deviceTypeName=getTabPanelActiveName("AlarmQueryRootTabPanel");
                    	var deviceName=Ext.getCmp('AlarmDeviceListComb_Id').getValue();
                    	var alarmLevel=Ext.getCmp('AlarmLevelComb_Id').getValue();
                    	var isSendMessage=Ext.getCmp('AlarmIsSendMessageComb_Id').getValue();
                    	var alarmType=getAlarmTypeFromTabActive();
                    	var alarmTypeName=getAlarmTypeNameFromTabActive();
                   	 	
                   	 	var fileName=deviceTypeName+alarmTypeName+'设备列表';
                   	 	var title=deviceTypeName+alarmTypeName+'设备列表';
                   	 	var columnStr=Ext.getCmp("AlarmOverviewColumnStr_Id").getValue();
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
                    	var startTime_Hour=Ext.getCmp('AlarmQueryStartTime_Hour_Id').getValue();
                    	if(!r.test(startTime_Hour)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                    		Ext.getCmp('AlarmQueryStartTime_Hour_Id').focus(true, 100);
                    		return;
                    	}
                    	var startTime_Minute=Ext.getCmp('AlarmQueryStartTime_Minute_Id').getValue();
                    	if(!r2.test(startTime_Minute)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                    		Ext.getCmp('AlarmQueryStartTime_Minute_Id').focus(true, 100);
                    		return;
                    	}
                    	var startTime_Second=Ext.getCmp('AlarmQueryStartTime_Second_Id').getValue();
                    	if(!r2.test(startTime_Second)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                    		Ext.getCmp('AlarmQueryStartTime_Second_Id').focus(true, 100);
                    		return;
                    	}
                    	
                    	var endTime_Hour=Ext.getCmp('AlarmQueryEndTime_Hour_Id').getValue();
                    	if(!r.test(endTime_Hour)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>小时为0~23之间的整数。");
                    		Ext.getCmp('AlarmQueryEndTime_Hour_Id').focus(true, 100);
                    		return;
                    	}
                    	var endTime_Minute=Ext.getCmp('AlarmQueryEndTime_Minute_Id').getValue();
                    	if(!r2.test(endTime_Minute)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>分钟为0~59之间的整数。");
                    		Ext.getCmp('AlarmQueryEndTime_Minute_Id').focus(true, 100);
                    		return;
                    	}
                    	var endTime_Second=Ext.getCmp('AlarmQueryEndTime_Second_Id').getValue();
                    	if(!r2.test(endTime_Second)){
                    		Ext.Msg.alert('消息', "<font color=red>数值无效！</font>秒为0~59之间的整数。");
                    		Ext.getCmp('AlarmQueryEndTime_Second_Id').focus(true, 100);
                    		return;
                    	}
                    	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                    	var deviceType=getDeviceTypeFromTabId("AlarmQueryRootTabPanel");
                    	var deviceTypeName=getTabPanelActiveName("AlarmQueryRootTabPanel");
                    	var deviceName='';
                    	var deviceId=0;
                    	if(Ext.getCmp("AlarmOverviewGridPanel_Id").getSelectionModel().getSelection().length>0){
                    		deviceName=Ext.getCmp("AlarmOverviewGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
                        	deviceId=  Ext.getCmp("AlarmOverviewGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
                    	}
                    	var alarmLevel=Ext.getCmp('AlarmLevelComb_Id').getValue();
                    	var isSendMessage=Ext.getCmp('AlarmIsSendMessageComb_Id').getValue();
                    	var startDate=Ext.getCmp('AlarmQueryStartDate_Id').rawValue;
                        var endDate=Ext.getCmp('AlarmQueryEndDate_Id').rawValue;
                        var alarmType=getAlarmTypeFromTabActive();
                    	var alarmTypeName=getAlarmTypeNameFromTabActive();
                   	 	
                   	 	var fileName=deviceTypeName+deviceName+alarmTypeName+'数据';
                   	 	var title=deviceTypeName+deviceName+alarmTypeName+'数据';
                   	 	var columnStr=Ext.getCmp("AlarmDetailsColumnStr_Id").getValue();
                   	 	exportAlarmDataExcel(orgId,deviceType,deviceId,deviceName,getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),alarmType,alarmLevel,isSendMessage,fileName,title,columnStr);
                    }
                }],
        		items: AlarmQuerySecondTabPanelItems,
        		listeners: {
        			beforetabchange ( tabPanel, newCard, oldCard, eOpts ) {
        				oldCard.setIconCls(null);
        				newCard.setIconCls('check3');
        				var alarmOverViewPanelId='';
        				var alarmDetailsPanelId='';
        				if(oldCard.id=="FESDiagramResultAlarmInfoTabPanel_Id"){
        					alarmOverViewPanelId='FESDiagramResultAlarmOverviewPanel_Id';
        					alarmDetailsPanelId='FESDiagramResultAlarmDetailsPanel_Id';
            			}else if(oldCard.id=="RunStatusAlarmInfoTabPanel_Id"){
            				alarmOverViewPanelId='RunStatusAlarmOverviewPanel_Id';
            				alarmDetailsPanelId='RunStatusAlarmDetailsPanel_Id';
            			}else if(oldCard.id=="CommunicationAlarmInfoTabPanel_Id"){
            				alarmOverViewPanelId='CommunicationAlarmOverviewPanel_Id';
            				alarmDetailsPanelId='CommunicationAlarmDetailsPanel_Id';
            			}else if(oldCard.id=="NumericValueAlarmInfoTabPanel_Id"){
            				alarmOverViewPanelId='NumericValueAlarmOverviewPanel_Id';
            				alarmDetailsPanelId='NumericValueAlarmDetailsPanel_Id';
            			}else if(oldCard.id=="EnumValueAlarmInfoTabPanel_Id"){
            				alarmOverViewPanelId='EnumValueAlarmOverviewPanel_Id';
            				alarmDetailsPanelId='EnumValueAlarmDetailsPanel_Id';
            			}else if(oldCard.id=="SwitchingValueAlarmInfoTabPanel_Id"){
            				alarmOverViewPanelId='SwitchingValueAlarmOverviewPanel_Id';
            				alarmDetailsPanelId='SwitchingValueAlarmDetailsPanel_Id';
            			}
        				Ext.getCmp(alarmOverViewPanelId).removeAll();
        				Ext.getCmp(alarmDetailsPanelId).removeAll();
	        		},
	        		tabchange: function (tabPanel, newCard,oldCard, obj) {
	        			Ext.getCmp("AlarmOverviewSelectRow_Id").setValue(0);
	        			Ext.getCmp("AlarmDeviceListComb_Id").setValue('');
	        			Ext.getCmp("AlarmDeviceListComb_Id").setRawValue('');
	        			Ext.getCmp("AlarmLevelComb_Id").setValue('');
	        			Ext.getCmp("AlarmLevelComb_Id").setRawValue('');
	        			var gridPanel = Ext.getCmp("AlarmOverviewGridPanel_Id");
	        			if (isNotVal(gridPanel)) {
	        				gridPanel.getStore().loadPage(1);
	        			}else{
	        				Ext.create('AP.store.alarmQuery.AlarmOverviewStore');
	        			}
	        		}
        		}
            	}]
        });
        me.callParent(arguments);
    }
});