Ext.define("AP.view.historyQuery.ItemHistoryDataWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.itemHistoryDataWindow',
    layout: 'fit',
    id:'ItemHistoryDataWindow_Id',
    title: loginUserLanguageResource.historyData,
    iframe: true,
    closeAction: 'destroy',
    width: '50%',
    height: '80%',
    shadow: 'sides',
    resizable: true,
    collapsible: true,
    constrain: true,
    maximizable: true,
    minimizable: true,
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
                    	
                    	var gridPanel = Ext.getCmp("ItemHistoryQueryDataGridPanel_Id");
                        if(isNotVal(gridPanel)){
                        	gridPanel.getStore().loadPage(1);
                        }else{
                        	Ext.create('AP.store.historyQuery.ItemHistoryDataStore');
                        }
                    }
                },'->',{
                    xtype: 'button',
                    text: loginUserLanguageResource.exportData,
                    iconCls: 'export',
                    hidden:false,
                    handler: function (v, o) {
                    	exportItemHistoryDataTable();
                    }
               },'-',{
                    id: 'ItemHistoryDataCount_Id',
                    xtype: 'component',
                    tpl: loginUserLanguageResource.totalCount + ':{count}',
                    hidden: false,
                    style: 'margin-right:15px'
                }]
        	}]
        });
        me.callParent(arguments);
    }
});

function exportItemHistoryDataTable(){
	var orgId = Ext.getCmp('leftOrg_Id')!=undefined?Ext.getCmp('leftOrg_Id').getValue():'0';
	var deviceName='';
	var deviceId=0;
	var deviceType=getDeviceTypeFromTabId("HistoryQueryRootTabPanel");
	var calculateType=0;
	if(Ext.getCmp("HistoryQueryDeviceListGridPanel_Id")!=undefined && Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection().length>0){
		deviceName = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.deviceName;
		deviceId = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.id;
		calculateType = Ext.getCmp("HistoryQueryDeviceListGridPanel_Id").getSelectionModel().getSelection()[0].data.calculateType;
	}
	var startDate=Ext.getCmp('ItemHistoryDataStartDate_Id')!=undefined?Ext.getCmp('ItemHistoryDataStartDate_Id').rawValue:'';
	var startTime_Hour=Ext.getCmp('ItemHistoryDataStartTime_Hour_Id')!=undefined?Ext.getCmp('ItemHistoryDataStartTime_Hour_Id').getValue():'';
	var startTime_Minute=Ext.getCmp('ItemHistoryDataStartTime_Minute_Id')!=undefined?Ext.getCmp('ItemHistoryDataStartTime_Minute_Id').getValue():'';
	var startTime_Second=0;

    var endDate=Ext.getCmp('ItemHistoryDataEndDate_Id')!=undefined?Ext.getCmp('ItemHistoryDataEndDate_Id').rawValue:'';
    var endTime_Hour=Ext.getCmp('ItemHistoryDataEndTime_Hour_Id')!=undefined?Ext.getCmp('ItemHistoryDataEndTime_Hour_Id').getValue():'';
	var endTime_Minute=Ext.getCmp('ItemHistoryDataEndTime_Minute_Id')!=undefined?Ext.getCmp('ItemHistoryDataEndTime_Minute_Id').getValue():'';
	var endTime_Second=0;
	
	var itemName=Ext.getCmp("HistoryDataItemName_Id").getValue();
	var itemCode=Ext.getCmp("HistoryDataItemCode_Id").getValue();
	var itemType=Ext.getCmp("HistoryDataItemType_Id").getValue();
	var itemResolutionMode=Ext.getCmp("HistoryDataItemResolutionMode_Id").getValue();
	var itemBitIndex=Ext.getCmp("HistoryDataItemBitIndex_Id").getValue();
	
	var timestamp=new Date().getTime();
	var key='exportItemHistoryData_'+deviceId+'_'+itemCode+'_'+timestamp;
	var maskPanelId='ItemHistoryDataPanel_Id';
	var url = context + '/historyQueryController/exportItemHistoryData';
    var param = "&deviceId=" + deviceId
    + "&deviceName=" + URLencode(URLencode(deviceName))
    + '&calculateType='+calculateType
    + "&itemName=" + URLencode(URLencode(itemName))
    + '&itemCode='+itemCode
    + '&itemType='+itemType
    + '&itemResolutionMode='+itemResolutionMode
    + '&itemBitIndex='+itemBitIndex
    + '&startDate='+getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second)
    + '&endDate='+getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second)
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loadingData);
    openExcelWindow(url + '?flag=true' + param);
}