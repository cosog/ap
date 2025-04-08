Ext.define("AP.view.realTimeMonitoring.ResourceProbeHistoryCurveWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.resourceProbeHistoryCurveWindow',
    layout: 'fit',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 1200,
    minWidth: 900,
    height: 450,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar:[
        		{
                    xtype: 'datefield',
                    anchor: '100%',
                    fieldLabel: loginUserLanguageResource.range,
                    labelWidth: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage)+100,
                    format: 'Y-m-d ',
                    id: 'ResourceProbeHistoryCurve_from_date_Id',
                    value: 'new',
                    listeners: {
                    	select: function (combo, record, index) {
//                    		Ext.create("AP.store.realTimeMonitoring.ResourceProbeHistoryCurveStore");
                        }
                    }
                },{
                	xtype: 'numberfield',
                	id: 'ResourceProbeHistoryCurveStartTime_Hour_Id',
                	fieldLabel: loginUserLanguageResource.hour,
                    labelWidth: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage)+45,
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
                    			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                    			field.focus(true, 100);
                    		}
                        }
                    }
                },{
                	xtype: 'numberfield',
                	id: 'ResourceProbeHistoryCurveStartTime_Minute_Id',
                	fieldLabel: loginUserLanguageResource.minute,
                    labelWidth: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage)+45,
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
                    			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                    			field.focus(true, 100);
                    		}
                        }
                    }
                },{
                	xtype: 'numberfield',
                	id: 'ResourceProbeHistoryCurveStartTime_Second_Id',
                	fieldLabel: loginUserLanguageResource.second,
                    labelWidth: getLabelWidth(loginUserLanguageResource.second,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.second,loginUserLanguage)+45,
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
                    			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                    			field.focus(true, 100);
                    		}
                        }
                    }
                }, {
                    xtype: 'datefield',
                    anchor: '100%',
                    fieldLabel: loginUserLanguageResource.timeTo,
                    labelWidth: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage)+95,
                    format: 'Y-m-d',
                    id: 'ResourceProbeHistoryCurve_end_date_Id',
                    value: '',
                    listeners: {
                    	select: function (combo, record, index) {
//                    		Ext.create("AP.store.realTimeMonitoring.ResourceProbeHistoryCurveStore");
                        }
                    }
                },{
                	xtype: 'numberfield',
                	id: 'ResourceProbeHistoryCurveEndTime_Hour_Id',
                	fieldLabel: loginUserLanguageResource.hour,
                    labelWidth: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.hour,loginUserLanguage)+45,
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
                    			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                    			field.focus(true, 100);
                    		}
                        }
                    }
                },{
                	xtype: 'numberfield',
                	id: 'ResourceProbeHistoryCurveEndTime_Minute_Id',
                	fieldLabel: loginUserLanguageResource.minute,
                    labelWidth: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.minute,loginUserLanguage)+45,
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
                    			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                    			field.focus(true, 100);
                    		}
                        }
                    }
                },{
                	xtype: 'numberfield',
                	id: 'ResourceProbeHistoryCurveEndTime_Second_Id',
                	fieldLabel: loginUserLanguageResource.second,
                    labelWidth: getLabelWidth(loginUserLanguageResource.second,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.second,loginUserLanguage)+45,
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
                    			Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                    			field.focus(true, 100);
                    		}
                        }
                    }
                },'-',{
                    xtype: 'button',
                    text: loginUserLanguageResource.search,
                    iconCls: 'search',
                    handler: function () {
                    	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                    	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                    	var startTime_Hour=Ext.getCmp('ResourceProbeHistoryCurveStartTime_Hour_Id').getValue();
                    	if(!r.test(startTime_Hour)){
                    		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                    		Ext.getCmp('ResourceProbeHistoryCurveStartTime_Hour_Id').focus(true, 100);
                    		return;
                    	}
                    	var startTime_Minute=Ext.getCmp('ResourceProbeHistoryCurveStartTime_Minute_Id').getValue();
                    	if(!r2.test(startTime_Minute)){
                    		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                    		Ext.getCmp('ResourceProbeHistoryCurveStartTime_Minute_Id').focus(true, 100);
                    		return;
                    	}
                    	var startTime_Second=Ext.getCmp('ResourceProbeHistoryCurveStartTime_Second_Id').getValue();
                    	if(!r2.test(startTime_Second)){
                    		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                    		Ext.getCmp('ResourceProbeHistoryCurveStartTime_Second_Id').focus(true, 100);
                    		return;
                    	}
                    	
                    	var endTime_Hour=Ext.getCmp('ResourceProbeHistoryCurveEndTime_Hour_Id').getValue();
                    	if(!r.test(endTime_Hour)){
                    		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                    		Ext.getCmp('ResourceProbeHistoryCurveEndTime_Hour_Id').focus(true, 100);
                    		return;
                    	}
                    	var endTime_Minute=Ext.getCmp('ResourceProbeHistoryCurveEndTime_Minute_Id').getValue();
                    	if(!r2.test(endTime_Minute)){
                    		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                    		Ext.getCmp('ResourceProbeHistoryCurveEndTime_Minute_Id').focus(true, 100);
                    		return;
                    	}
                    	var endTime_Second=Ext.getCmp('ResourceProbeHistoryCurveEndTime_Second_Id').getValue();
                    	if(!r2.test(endTime_Second)){
                    		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
                    		Ext.getCmp('ResourceProbeHistoryCurveEndTime_Second_Id').focus(true, 100);
                    		return;
                    	}
                    	
                    	Ext.create("AP.store.realTimeMonitoring.ResourceProbeHistoryCurveStore");
                    }
                },'->',{
                	xtype: 'label',
                	hidden:false,
                	html: loginUserLanguageResource.resourcesMonitoringHistoryCountTip+':<font color=red>'+resourceMonitoringSaveData+'</font>'
                }
        	],
        	html: '<div id="ResourceProbeHistoryCurveDiv_Id" style="width:100%;height:100%;"></div>',
            listeners: {
                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    if ($("#ResourceProbeHistoryCurveDiv_Id").highcharts() != undefined) {
                    	highchartsResize("ResourceProbeHistoryCurveDiv_Id");
                    }else{
                    	Ext.create("AP.store.realTimeMonitoring.ResourceProbeHistoryCurveStore");
                    }
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});