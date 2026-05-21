Ext.define("AP.view.historyQuery.ItemHistoryDataWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.itemHistoryDataWindow',
    layout: 'fit',
    id:'ItemHistoryDataWindow_Id',
    title: loginUserLanguageResource.historyData,
    iframe: true,
    closeAction: 'destroy',
    width: 700,
    minWidth: 700,
    height: 600,
    shadow: 'sides',
    resizable: true,
    collapsible: true,
    constrain: true,
    maximizable: false,
    plain: true,
    modal: true,
    border: false,
    draggable: true, // 是否可拖曳
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	layout: 'border',
        	items: [{
        		region: 'center',
            	id:'ItemHistoryDataPanel_Id',
        		layout: 'fit',
        		autoScroll: false,
        		tbar:[{
                    id: 'HistoryDataItemName_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'HistoryDataItemCode_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'HistoryDataItemType_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'HistoryDataItemResolutionMode_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    id: 'HistoryDataItemBitIndex_Id',
                    xtype: 'textfield',
                    value: '',
                    hidden: true
                },{
                    xtype: 'datefield',
                    anchor: '100%',
                    fieldLabel: loginUserLanguageResource.range,
                    labelWidth: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage)+100,
                    format: 'Y-m-d ',
                    value: '',
                    id: 'ItemHistoryDataStartDate_Id',
                    listeners: {
                    	select: function (combo, record, index) {
                    		
                    	}
                    }
                },{
                	xtype: 'numberfield',
                	id: 'ItemHistoryDataStartTime_Hour_Id',
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
                	id: 'ItemHistoryDataStartTime_Minute_Id',
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
                    xtype: 'datefield',
                    anchor: '100%',
                    fieldLabel: loginUserLanguageResource.timeTo,
                    labelWidth: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage),
                    width: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage)+95,
                    format: 'Y-m-d ',
                    value: '',
                    id: 'ItemHistoryDataEndDate_Id',
                    listeners: {
                    	select: function (combo, record, index) {
                    		
                    	}
                    }
                },{
                	xtype: 'numberfield',
                	id: 'ItemHistoryDataEndTime_Hour_Id',
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
                	id: 'ItemHistoryDataEndTime_Minute_Id',
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
                    xtype: 'button',
                    text: loginUserLanguageResource.search,
                    iconCls: 'search',
                    handler: function () {
                    	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                    	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                    	var startTime_Hour=Ext.getCmp('ItemHistoryDataStartTime_Hour_Id').getValue();
                    	if(!r.test(startTime_Hour)){
                    		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                    		Ext.getCmp('ItemHistoryDataStartTime_Hour_Id').focus(true, 100);
                    		return;
                    	}
                    	var startTime_Minute=Ext.getCmp('ItemHistoryDataStartTime_Minute_Id').getValue();
                    	if(!r2.test(startTime_Minute)){
                    		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                    		Ext.getCmp('ItemHistoryDataStartTime_Minute_Id').focus(true, 100);
                    		return;
                    	}
                    	
                    	var endTime_Hour=Ext.getCmp('ItemHistoryDataEndTime_Hour_Id').getValue();
                    	if(!r.test(endTime_Hour)){
                    		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                    		Ext.getCmp('ItemHistoryDataEndTime_Hour_Id').focus(true, 100);
                    		return;
                    	}
                    	var endTime_Minute=Ext.getCmp('ItemHistoryDataEndTime_Minute_Id').getValue();
                    	if(!r2.test(endTime_Minute)){
                    		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                    		Ext.getCmp('ItemHistoryDataEndTime_Minute_Id').focus(true, 100);
                    		return;
                    	}
                    }
                },'->',{
                    id: 'ItemHistoryDataCount_Id',
                    xtype: 'component',
                    tpl: loginUserLanguageResource.totalCount + ':{totalCount}',
                    hidden: false,
                    style: 'margin-right:15px'
                }]
        	}]
        });
        me.callParent(arguments);
    }
});