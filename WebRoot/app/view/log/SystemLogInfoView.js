Ext.define('AP.view.log.SystemLogInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.systemLogInfoView',
    layout: "fit",
    id: "SystemLogView_Id",
    border: false,
    //forceFit : true,
    initComponent: function () {
    	var userCombStore = new Ext.data.JsonStore({
            pageSize: defaultWellComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/userManagerController/loadUserComboxList',
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
                    var userId = Ext.getCmp('systemLogUserListComb_Id').getValue();
                    var new_params = {
                        orgId: leftOrg_Id,
                        userId: userId
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
    	
    	var userCombo = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: loginUserLanguageResource.user,
                    id: "systemLogUserListComb_Id",
                    labelWidth: getLabelWidth(loginUserLanguageResource.user,loginUserLanguage),
                    width: (getLabelWidth(loginUserLanguageResource.user,loginUserLanguage)+110),
                    labelAlign: 'left',
                    queryMode: 'remote',
                    typeAhead: true,
                    store: userCombStore,
                    autoSelect: false,
                    editable: true,
                    triggerAction: 'all',
                    displayField: "boxval",
                    valueField: "boxkey",
                    pageSize: comboxPagingStatus,
                    minChars: 0,
                    emptyText: '--'+loginUserLanguageResource.all+'--',
                    blankText: '--'+loginUserLanguageResource.all+'--',
                    listeners: {
                        expand: function (sm, selections) {
                        	userCombo.getStore().loadPage(1); // 加载井下拉框的store
                        },
                        select: function (combo, record, index) {
                        	
                        }
                    }
                });
    	var actionCombStore = new Ext.data.JsonStore({
            pageSize: defaultWellComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/logQueryController/loadSystemLogActionComboxList',
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
                    var new_params = {
                    		
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
    	
    	var actionCombo = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: loginUserLanguageResource.operation,
                    labelWidth: getLabelWidth(loginUserLanguageResource.operation,loginUserLanguage),
                    width: (getLabelWidth(loginUserLanguageResource.operation,loginUserLanguage)+110),
                    id: "systemLogActionListComb_Id",
                    labelAlign: 'left',
                    queryMode: 'remote',
                    typeAhead: true,
                    store: actionCombStore,
                    autoSelect: false,
                    editable: true,
                    triggerAction: 'all',
                    displayField: "boxval",
                    valueField: "boxkey",
                    pageSize: comboxPagingStatus,
                    minChars: 0,
                    emptyText: '--'+loginUserLanguageResource.all+'--',
                    blankText: '--'+loginUserLanguageResource.all+'--',
                    listeners: {
                        expand: function (sm, selections) {
                        	actionCombo.getStore().loadPage(1); // 加载井下拉框的store
                        },
                        select: function (combo, record, index) {
                        	
                        }
                    }
                });
    	Ext.apply(this, {
    		layout: "border",
    		items:[{
    			region: 'center',
    			layout: 'fit',
    			id:"SystemLogPanel_Id"
    		}],
    		tbar: [{
                id: 'SystemLogColumnStr_Id',
                xtype: 'textfield',
                value: '',
                hidden: true
            },{
                xtype: 'button',
                text: loginUserLanguageResource.refresh,
                iconCls: 'note-refresh',
                hidden:false,
                handler: function (v, o) {
                	Ext.getCmp('SystemLogQueryStartDate_Id').setValue('');
                	Ext.getCmp('SystemLogQueryStartDate_Id').setRawValue('');
                	Ext.getCmp('SystemLogQueryStartTime_Hour_Id').setValue('');
                	Ext.getCmp('SystemLogQueryStartTime_Minute_Id').setValue('');
//                	Ext.getCmp('SystemLogQueryStartTime_Second_Id').setValue('');
                    Ext.getCmp('SystemLogQueryEndDate_Id').setValue('');
                    Ext.getCmp('SystemLogQueryEndDate_Id').setRawValue('');
                    Ext.getCmp('SystemLogQueryEndTime_Hour_Id').setValue('');
                	Ext.getCmp('SystemLogQueryEndTime_Minute_Id').setValue('');
//                	Ext.getCmp('SystemLogQueryEndTime_Second_Id').setValue('');
                	
                	Ext.getCmp('systemLogUserListComb_Id').setValue("");
        			Ext.getCmp('systemLogUserListComb_Id').setRawValue("");
        			
        			Ext.getCmp('systemLogActionListComb_Id').setValue("");
        			Ext.getCmp('systemLogActionListComb_Id').setRawValue("");
        			
                	var gridPanel = Ext.getCmp("SystemLogGridPanel_Id");
                	if (isNotVal(gridPanel)) {
                		gridPanel.getStore().loadPage(1);
                	}
                }
    		},'-',userCombo,'-',actionCombo,'-',{
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: loginUserLanguageResource.range,
                labelWidth: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.range,loginUserLanguage)+100,
                format: 'Y-m-d ',
                value: '',
                id: 'SystemLogQueryStartDate_Id',
                listeners: {
                	select: function (combo, record, index) {
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'SystemLogQueryStartTime_Hour_Id',
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
            	id: 'SystemLogQueryStartTime_Minute_Id',
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
            	id: 'SystemLogQueryStartTime_Second_Id',
            	hidden: true,
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
            },{
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: loginUserLanguageResource.timeTo,
                labelWidth: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage),
                width: getLabelWidth(loginUserLanguageResource.timeTo,loginUserLanguage)+95,
                format: 'Y-m-d ',
                value: '',
                id: 'SystemLogQueryEndDate_Id',
                listeners: {
                	select: function (combo, record, index) {
                    }
                }
            },{
            	xtype: 'numberfield',
            	id: 'SystemLogQueryEndTime_Hour_Id',
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
            	id: 'SystemLogQueryEndTime_Minute_Id',
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
            	id: 'SystemLogQueryEndTime_Second_Id',
            	hidden: true,
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
                	var startTime_Hour=Ext.getCmp('SystemLogQueryStartTime_Hour_Id').getValue();
                	if(!r.test(startTime_Hour)){
                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                		Ext.getCmp('SystemLogQueryStartTime_Hour_Id').focus(true, 100);
                		return;
                	}
                	var startTime_Minute=Ext.getCmp('SystemLogQueryStartTime_Minute_Id').getValue();
                	if(!r2.test(startTime_Minute)){
                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                		Ext.getCmp('SystemLogQueryStartTime_Minute_Id').focus(true, 100);
                		return;
                	}
//                	var startTime_Second=Ext.getCmp('SystemLogQueryStartTime_Second_Id').getValue();
//                	if(!r2.test(startTime_Second)){
//                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
//                		Ext.getCmp('SystemLogQueryStartTime_Second_Id').focus(true, 100);
//                		return;
//                	}
                	var startTime_Second=0;
                	
                	var endTime_Hour=Ext.getCmp('SystemLogQueryEndTime_Hour_Id').getValue();
                	if(!r.test(endTime_Hour)){
                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                		Ext.getCmp('SystemLogQueryEndTime_Hour_Id').focus(true, 100);
                		return;
                	}
                	var endTime_Minute=Ext.getCmp('SystemLogQueryEndTime_Minute_Id').getValue();
                	if(!r2.test(endTime_Minute)){
                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                		Ext.getCmp('SystemLogQueryEndTime_Minute_Id').focus(true, 100);
                		return;
                	}
//                	var endTime_Second=Ext.getCmp('SystemLogQueryEndTime_Second_Id').getValue();
//                	if(!r2.test(endTime_Second)){
//                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
//                		Ext.getCmp('SystemLogQueryEndTime_Second_Id').focus(true, 100);
//                		return;
//                	}
                	var endTime_Second=0;
                	var gridPanel = Ext.getCmp("SystemLogGridPanel_Id");
                	if (isNotVal(gridPanel)) {
                		gridPanel.getStore().loadPage(1);
                	}
                }
            },'-', {
                xtype: 'button',
                text: loginUserLanguageResource.exportData,
                iconCls: 'export',
                hidden:false,
                handler: function (v, o) {
                	var r = /^(2[0-3]|[0-1]?\d|\*|-|\/)$/;
                	var r2 = /^[1-5]?\d([\/-][1-5]?\d)?$/;
                	var startTime_Hour=Ext.getCmp('SystemLogQueryStartTime_Hour_Id').getValue();
                	if(!r.test(startTime_Hour)){
                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                		Ext.getCmp('SystemLogQueryStartTime_Hour_Id').focus(true, 100);
                		return;
                	}
                	var startTime_Minute=Ext.getCmp('SystemLogQueryStartTime_Minute_Id').getValue();
                	if(!r2.test(startTime_Minute)){
                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                		Ext.getCmp('SystemLogQueryStartTime_Minute_Id').focus(true, 100);
                		return;
                	}
//                	var startTime_Second=Ext.getCmp('SystemLogQueryStartTime_Second_Id').getValue();
//                	if(!r2.test(startTime_Second)){
//                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
//                		Ext.getCmp('SystemLogQueryStartTime_Second_Id').focus(true, 100);
//                		return;
//                	}
                	var startTime_Second=0;
                	
                	var endTime_Hour=Ext.getCmp('SystemLogQueryEndTime_Hour_Id').getValue();
                	if(!r.test(endTime_Hour)){
                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.hourlyValidData);
                		Ext.getCmp('SystemLogQueryEndTime_Hour_Id').focus(true, 100);
                		return;
                	}
                	var endTime_Minute=Ext.getCmp('SystemLogQueryEndTime_Minute_Id').getValue();
                	if(!r2.test(endTime_Minute)){
                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.minuteValidData);
                		Ext.getCmp('SystemLogQueryEndTime_Minute_Id').focus(true, 100);
                		return;
                	}
//                	var endTime_Second=Ext.getCmp('SystemLogQueryEndTime_Second_Id').getValue();
//                	if(!r2.test(endTime_Second)){
//                		Ext.Msg.alert(loginUserLanguageResource.message, "<font color=red>"+loginUserLanguageResource.invalidData+"</font>"+loginUserLanguageResource.secondValidData);
//                		Ext.getCmp('SystemLogQueryEndTime_Second_Id').focus(true, 100);
//                		return;
//                	}
                	var endTime_Second=0;
                	
                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                	var startDate=Ext.getCmp('SystemLogQueryStartDate_Id').rawValue;
                    var endDate=Ext.getCmp('SystemLogQueryEndDate_Id').rawValue;
                    
                    var selectUserId=Ext.getCmp('systemLogUserListComb_Id').getValue();
                	var operationType=Ext.getCmp('systemLogActionListComb_Id').getValue();
               	 	
               	 	var fileName=loginUserLanguageResource.systemLog;
               	 	var title=loginUserLanguageResource.systemLog;
               	 	var columnStr=Ext.getCmp("SystemLogColumnStr_Id").getValue();
               	 	exportSystemLogExcel(orgId,getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second),selectUserId,operationType,fileName,title,columnStr);
                }
            }]
        });
        this.callParent(arguments);
    }

});

function createSystemLogColumn(columnInfo) {
    var myArr = columnInfo;

    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var flex_ = "";
        var lock_ = "";
        var hidden_ = "";
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
        else if (attr.dataIndex.toUpperCase()=='runStatusName'.toUpperCase()) {
            myColumns += ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceRunStatusColor(value,o,p,e);}";
        }
        else if (attr.dataIndex.toUpperCase() == 'createTime'.toUpperCase()) {
            myColumns += ",sortable : false,locked:false,dataIndex:'" + attr.dataIndex + "',renderer:function(value,o,p,e){return adviceTimeFormat(value,o,p,e);}";
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

function exportSystemLogExcel(orgId,startDate,endDate,selectUserId,operationType,fileName,title,columnStr) {
	var timestamp=new Date().getTime();
	var key='exportSystemLogExcelData'+timestamp;
	
	var maskPanelId='SystemLogPanel_Id';
	
	var url = context + '/logQueryController/exportSystemLogExcelData';
    var fields = "";
    var heads = "";
    var lockedheads = "";
    var unlockedheads = "";
    var lockedfields = "";
    var unlockedfields = "";
    var columns_ = Ext.JSON.decode(columnStr);
    
    fields = "id";
    heads = loginUserLanguageResource.idx;
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
    	fields+=","+lockedfields;
    	heads+= "," + lockedheads;
    }
    if (isNotVal(unlockedfields)) {
    	unlockedfields = unlockedfields.substring(0, unlockedfields.length - 1);
    	unlockedheads = unlockedheads.substring(0, unlockedheads.length - 1);
    	fields+=","+unlockedfields;
    	heads+= "," + unlockedheads;
    }
    
    var param = "&fields=" + fields 
    + "&heads=" + URLencode(URLencode(heads)) 
    + "&orgId=" + orgId
    + "&startDate=" + startDate
    + "&endDate=" + endDate
    + "&selectUserId=" + URLencode(URLencode(selectUserId)) 
    + "&operationType=" + URLencode(URLencode(operationType)) 
    + "&fileName=" + URLencode(URLencode(fileName)) 
    + "&title=" + URLencode(URLencode(title))
    + '&key='+key;
    exportDataMask(key,maskPanelId,loginUserLanguageResource.loading);
    openExcelWindow(url + '?flag=true' + param);
};